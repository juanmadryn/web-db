package com.salmonllc.servlets;

import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.remote.RemoteProxy;
import com.salmonllc.remote.server.HttpRemoteReflectionSecurityManager;
import com.salmonllc.remote.server.RemoteReflectionSecurityManager;
import com.salmonllc.remote.server.RemoteReflectionSecurityPolicy;
import com.salmonllc.util.ApplicationContext;
import com.salmonllc.util.MessageLog;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 14, 2004
 * Time: 9:57:36 AM
 * To change this template use Options | File Templates.
 */
/**
 * Created by IntelliJ IDEA. User: Fred Cahill Date: Sep 14, 2004 Time: 9:57:36 AM To change this template use Options | File Templates.
 */
public class RemoteReflector extends HttpServlet {

    private final static String REMOTEREFLECTIONPOLICY="$REMOTEREFLECTIONPOLICY$";
    public static RemoteReflectionSecurityPolicy _rrsp=new RemoteReflectionSecurityManager();
    /**
     * Initializes the servlet
     */
    public void init(ServletConfig s) throws ServletException {
        super.init(s);
    }

    /**
     * This method was created in VisualAge.
     * @param res javax.servlet.http.HttpServletResponse
     */
    private void setStatus(HttpServletResponse res, int stat) {
        res.setIntHeader("RemoteReflectorResponse", stat);
    }


    private Object getSessionObject(HttpSession sess, String sessionkey) throws Exception {
        if (sessionkey == null)
            return null;

        return sess.getAttribute(sessionkey);

    }

    private Object setSessionObject(HttpSession sess, String sessionkey,Object obj) throws Exception {
        if (sessionkey == null)
            return null;

        sess.setAttribute(sessionkey,obj);
        return obj;
    }


    /**
     * This method handles events from the applet.
     */
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        JspServlet.setUpApplicationContext(getServletContext(),req);
        Props p = Props.getProps(ApplicationContext.getContext().getAppID(),null);
        OutputStream out = null;
        ObjectInputStream in = null;
        boolean bInstantiate=false;

        try {
            in = new ObjectInputStream(req.getInputStream());
            out = res.getOutputStream();

            bInstantiate = ((Boolean)in.readObject()).booleanValue();
            String sSessionKey = (String)in.readObject();
            String sMethod = (String) in.readObject();
            Class[] caParms = (Class[]) in.readObject();
            Object[] oaParms = (Object[]) in.readObject();

            if (sSessionKey == null) {
                setStatus(res, RemoteProxy.REMOTE_STATUS_BAD_REQUEST);
                return;
            }
            MessageLog.writeInfoMessage("Method:" + sMethod + " Session Key:" + sSessionKey, this);


            HttpSession sess = req.getSession(false);
            if (sess == null) {
                sess = req.getSession(true);
            }

            RemoteReflectionSecurityPolicy rrsp=(RemoteReflectionSecurityPolicy)sess.getAttribute(REMOTEREFLECTIONPOLICY);
            String managerClass=p.getProperty(Props.SYS_REMOTEREFLECTOR_SECURITY_MANAGER);
            if (managerClass != null && rrsp==null) {
           		Class c = Class.forName(managerClass);	
           		rrsp = (RemoteReflectionSecurityManager)c.newInstance();
           		sess.setAttribute(REMOTEREFLECTIONPOLICY,rrsp);
            }	
            if (rrsp==null)
                rrsp=_rrsp;
            if (rrsp != null && rrsp instanceof HttpRemoteReflectionSecurityManager) {
            	((HttpRemoteReflectionSecurityManager) rrsp).setReq(req);
            	((HttpRemoteReflectionSecurityManager) rrsp).setSess(sess);
            }	
            if (bInstantiate) {
              Class c=Class.forName(sMethod);
              if (!rrsp.isInstantiationAllowed(c)) {
                  setStatus(res, RemoteProxy.REMOTE_STATUS_ACCESS_DENIED);
                  return;
              }
              Constructor constructor=c.getConstructor(caParms);
              if (constructor!=null) {
                Object oSessionObject=constructor.newInstance(oaParms);
                setSessionObject(sess,sSessionKey,oSessionObject);
                if (oSessionObject instanceof Serializable) {
                    ObjectOutputStream o = new ObjectOutputStream(out);
                    o.writeObject(oSessionObject);
                    o.close();
                }
                else {
                    ObjectOutputStream o = new ObjectOutputStream(out);
                    o.writeObject(sSessionKey);
                    o.close();
                }
                setStatus(res,RemoteProxy.REMOTE_STATUS_OK);
              }
              
              else {
                  setStatus(res,RemoteProxy.REMOTE_STATUS_CONSTRUCTOR_NOT_FOUND);
              }
            }
            else {
                Object oSessionObject=getSessionObject(sess,sSessionKey);
                if (oSessionObject!=null) {
                  if (!rrsp.isMethodCallAllowed(oSessionObject,sMethod)) {
                      setStatus(res, RemoteProxy.REMOTE_STATUS_ACCESS_DENIED);
                      return;
                  }
                  Method m=oSessionObject.getClass().getMethod(sMethod,caParms);
                  if (m!=null) {
                     Object result=m.invoke(oSessionObject,oaParms);
                      if (result!=null) {
                          ObjectOutputStream o = new ObjectOutputStream(out);
                          o.writeObject(result);
                          o.close();
                      }
                      setStatus(res,RemoteProxy.REMOTE_STATUS_OK);
                  }
                  else {
                      setStatus(res, RemoteProxy.REMOTE_STATUS_METHOD_NOT_FOUND);
                  }
                }
                else {
                    setStatus(res, RemoteProxy.REMOTE_STATUS_OBJECT_NOT_FOUND);
                }
            }
            in.close();
        }
        catch (ClassNotFoundException cnfe) {
            MessageLog.writeErrorMessage("doGet", cnfe, this);
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(cnfe);
            o.close();
            setStatus(res, RemoteProxy.REMOTE_STATUS_CLASS_NOT_FOUND);
        }
        catch (NoSuchMethodException nsme) {
            MessageLog.writeErrorMessage("doGet", nsme, this);
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(nsme);
            o.close();
            setStatus(res, RemoteProxy.REMOTE_STATUS_METHOD_NOT_FOUND);
        }
        catch (IllegalAccessException iae) {
            MessageLog.writeErrorMessage("doGet", iae, this);
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(iae);
            o.close();
            setStatus(res, RemoteProxy.REMOTE_STATUS_ACCESS_DENIED);
        }
        catch (InvocationTargetException ite) {
            MessageLog.writeErrorMessage("doGet", ite, this);
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(ite);
            o.close();
            setStatus(res, RemoteProxy.REMOTE_STATUS_ACCESS_DENIED);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("doGet", e, this);
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(e);
            o.close();
            setStatus(res, RemoteProxy.REMOTE_STATUS_EXCEPTION_OCCURED);
        }

    }

    /**
    * Sets the Global Security Policy to use for this servlet.
    * @param rrsp RemoteReflectionSecurityPolicy
    */
    public static void setSecurityPolicy(RemoteReflectionSecurityPolicy rrsp) {
        _rrsp=rrsp;
    }


    /**
    * Sets a session based Security Policy to use for this servlet for this session.
    * @param sess HttpSession   The session for which this security policy applies.
    * @param rrsp RemoteReflectionSecurityPolicy
    */
    public static void setSecurityPolicy(HttpSession sess,RemoteReflectionSecurityPolicy rrsp) {
        sess.setAttribute(REMOTEREFLECTIONPOLICY,rrsp);
    }
    
    /**
     * Returns true if a security policy is set for this session
     */
    public static boolean isSecurityPolicySet(HttpSession sess) {
    	return (sess.getAttribute(REMOTEREFLECTIONPOLICY) != null);
    }	
}
