//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspServlet.java $
//$Author: Srufle $
//$Revision: 47 $
//$Modtime: 11/15/04 1:18p $
/////////////////////////

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.HttpJspPage;
import javax.servlet.jsp.JspFactory;

import com.salmonllc.jsp.tags.PageTag;
import com.salmonllc.jsp.tags.TagContext;
import com.salmonllc.properties.Props;
import com.salmonllc.util.ApplicationContext;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;
import com.salmonllc.html.HttpServletResponseDummy;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.ServletServiceListener;

/**
 * This servlet should be used as the parent of each generated JSP servlet used by the framework<BR>
 * by using the JSP Directive &lt;@page extends=&quot;com.salmonllc.jsp.JspServlet&quot;&gt;<br>
 * Using the servlet will make your pages thread safe, compile response time stats on the server and make processing of salmon tags more efficient<br>
 */
public abstract class JspServlet implements Servlet, HttpJspPage {
    protected ServletConfig _servletConfig;
    private static int _count;
    private static long _time;
    private static long _serverStarted = -1;
    private static boolean _replaceFactory;
    private static boolean _replaceFactoryInit;
    public static Hashtable _controllerCache = new Hashtable();
    public static boolean _cacheControllers = true;
    public static Hashtable _timers = new Hashtable();
    public static int _reqCount = 0;
    private static Vector _listeners;

    public static final String SALMON_SERVLET_KEY = "&FROMSALMONSERVLET&";
    public static final String SALMON_CREATE_PAGE_HEADER = "SalmonCreatePage";
    public static final String SALMON_CONTEXT_TOKEN = "SalmonContextToken";

    public class JspServletObjectInputStream extends ObjectInputStream {
        ClassLoader _loader;

        public JspServletObjectInputStream(InputStream in, ClassLoader loader) throws IOException {
            super(in);
            _loader = loader;
        }

        protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            String name = desc.getName();
            try {
                return Class.forName(name, false, _loader);
            } catch (ClassNotFoundException ex) {
                return super.resolveClass(desc);
            }
        }

    }

    public static class Timer {
        public int count;
        public long totalTime;
    }

    public abstract void _jspService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * Adds a hit to a particular activity. The average time for each named time can
     * be seen in the connection monitor servlet
     */
    public static synchronized void recordTimerActivity(String name, long time, Object o, boolean writeToMessageLog) {
        Timer t = (Timer) _timers.get(name);
        if (t == null) {
            t = new Timer();
            _timers.put(name, t);
        }
        t.count++;
        t.totalTime += time;
        if (writeToMessageLog)
            MessageLog.writeDebugMessage("Timer:" + name + " elapsed time (ms):" + time, o);
    }

    /**
     * Adds a hit to a particular activity. The average time for each named time can
     * be seen in the connection monitor servlet
     */
    public static synchronized void recordTimerActivity(String name, long time, Object o) {
        recordTimerActivity(name, time, o, false);
    }

    /**
     * Resets all page hit stats and timers
     */
    public static synchronized void resetAllTimers() {
        _count = 0;
        _time = 0;
        _serverStarted = -1;
        _timers.clear();
    }

    /**
     * Returns a hash table of timers recorded in the system
     * @return
     */
    public static Hashtable getTimersHashtable() {
        return _timers;
    }
    /**
     * Adds a page hit to the hit counter
     */
    public static void addPageHit(long time) {
        _count++;
        _time += time;
        if (_serverStarted == -1)
            _serverStarted = System.currentTimeMillis();
    }

    public final void destroy() {
        jspDestroy();
    }

    /**
     * Returns the average time per page hit in milliseconds.
     */
    public static long getAverageHitTime() {
        if (_count == 0)
            return 0L;
        return _time / _count;
    }

    /**
     * Returns the time that the first page was requested from the server.
     */
    public static long getFirstPageHitTime() {
        return _serverStarted;
    }

    /**
     * Returns the number of page hits since the server was started.
     */
    public static int getPageHits() {
        return _count;
    }

    /**
     * Returns the time in milliseconds since the first page hit on the server
     */
    public long getServerStartTime() {
        return 0;
    }

    /**
     * Returns true if the controller cache is being used
     */
    public static boolean isControllerCacheActive() {
        return _cacheControllers;
    }

    /**
     * Returns the number of controllers in the cache
     */
    public static int getControllerCacheCount() {
        if (!_cacheControllers)
            return 0;
        return _controllerCache.size();
    }

    /**
     * Returns the number of bytes used by the controller cache
     */
    public static int getControllerCacheBytes() {
        if (!_cacheControllers)
            return 0;

        int count = 0;
        Enumeration e = _controllerCache.elements();
        while (e.hasMoreElements())
            count += ((byte[]) e.nextElement()).length;
        return count;
    }

    public ServletConfig getServletConfig() {
        return _servletConfig;
    }

    public String getServletInfo() {
        return "Salmon LLC JSP Base Servlet " + getClass().getName();
    }

    /**
     * Returns the total time per page hit in milliseconds.
     */
    public static long getTotalHitTime() {
        return _time;
    }

    public final void init(ServletConfig config) throws ServletException {
        _servletConfig = config;
        jspInit();
    }

    public void jspDestroy() {
        JspFactory fact = JspFactory.getDefaultFactory();
        if (fact instanceof com.salmonllc.jsp.engine.JspFactoryImpl)
            ((com.salmonllc.jsp.engine.JspFactoryImpl) fact).resetFactory();
    }

    public void jspInit() {

    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        setUpApplicationContext(getServletConfig().getServletContext(),(HttpServletRequest)request);
        JspController cont = null;
        boolean sessionKeepAlive=true;
        try {
            long time = System.currentTimeMillis();
            request.setAttribute(SALMON_SERVLET_KEY, this);
            sessionKeepAlive=request.getParameter("sessionKeepAlive") != null;

            if (!_replaceFactoryInit) {
                Props p = Props.getSystemProps();
                _replaceFactoryInit = true;
                _replaceFactory = p.getBooleanProperty(Props.SYS_REPLACE_JSP_FACTORY, true);
                _cacheControllers = p.getBooleanProperty(Props.SYS_CACHE_CONTROLLERS, false);
                MessageLog.writeInfoMessage("***JspServlet initialized with properties: " + Props.SYS_REPLACE_JSP_FACTORY + "=" + _replaceFactory + ", " + Props.SYS_CACHE_CONTROLLERS + "=" + _cacheControllers + ". To reset, change the System.properties file and restart the server.***", this);
            }

//			if (_replaceFactory) {
//				JspFactory fact = JspFactory.getDefaultFactory();
//				if (fact == null || !fact.getClass().getName().equals("com.salmonllc.jsp.engine.JspFactoryImpl"))
//					JspFactory.setDefaultFactory(new com.salmonllc.jsp.engine.JspFactoryImpl(fact));
//			}

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;



            if (sessionKeepAlive)
                com.salmonllc.util.MessageLog.writeInfoMessage("JspServlet.service() keepAlive - URI=" + req.getRequestURI(), Props.LOG_LEVEL_10, this);
            else {
                notifyListeners((HttpServletRequest)request,(HttpServletResponse)response,true);
                com.salmonllc.util.MessageLog.writeInfoMessage("JspServlet.service() start - URI=" + req.getRequestURI(), Props.LOG_LEVEL_10, this);
            }

            String sessID = req.getParameter(PageTag.getSessionIdentifier());
            HttpSession sess = PageTag.getSession(sessID);
            boolean sessValid = true;
            if (sess == null) {
                sessID = req.getRequestedSessionId();
                sessValid = req.isRequestedSessionIdValid();
                if (! sessValid && sessionKeepAlive)
                    return;
                sess = req.getSession(true);
            }

            boolean onSession = false;
            boolean sessExp = false;
            if (sessID != null && !sessValid)
                sess.setAttribute("AppServer_SessExp", new Boolean(true));

            boolean createPage = (req.getHeader(SALMON_CREATE_PAGE_HEADER) != null);
            if (_replaceFactory) {
                Object sessToken = sess.getAttribute("AppServer_SessionToken");
                if (sessToken == null) {
                    sess.setAttribute("AppServer_SessionToken", new String("tok"));
                    sessToken = sess.getAttribute("AppServer_SessionToken");
                }
                synchronized (sessToken) {
                    String sessName = "$jsp$" + com.salmonllc.jsp.tags.PageTag.generateSessionName(req);
                    loadCachedController(sessName, sess);
                    onSession = sess.getAttribute(sessName) != null;

                    if (!onSession)
						_jspService(req, new HttpServletResponseDummy(res,null));

                    cont = (JspController) sess.getAttribute(sessName);
                    generateExpireResponseHeaders(res,cont.getAddExpireHeaders());

                    cacheController(sessName, cont);
                    cont.setSessionExpired(sessExp);
                    cont.setDoPostRedirected(false);
                    _jspService(req, res);
                }
            } else {
                String sessName = "$jsp$" + com.salmonllc.jsp.tags.PageTag.generateSessionName(req);
                String token = sessName + "$pageToken$";
                try {
                    if (!createPage) {

                        if (sess.getAttribute(token) != null) {

                            /*
                            * srufle : Jun 25, 2004 4 : 26 : 38 PM
                            * This was put in to solve a thread deadlocking issue is was encountering
                            *
                            * Possible enhancements include
                            * - making vars get their values from system parameters
                            */
                            int index = 0;
                            int indexLimit = 1024;
                            int sleepCount = 0;
                            int sleepCountLimit = 4096;
                            int sleepTime = 10;
                            while (sess.getAttribute(token) != null)
                            {
                                index++ ;
                                Thread.yield();

                                if (index >= (indexLimit))
                                {
                                    Thread.sleep(sleepTime);
                                    index=0;
                                    sleepCount++;
                                    if ( sleepCount >= sleepCountLimit)
                                    {
                                        throw (new ServletException("Thread Locked:Throwing to unlock"));
                                    }
                                }
                            }
                        }
                        sess.setAttribute(token, token);
                    }

                    loadCachedController(sessName, sess);
                    onSession = sess.getAttribute(sessName) != null;
                    if (!onSession && !createPage) {
                        createPage(req, res, sess, getPageURL(req, res), sess.getId());
                        cont = (JspController) sess.getAttribute(sessName);

                        cacheController(sessName, cont);
                    } else
                        cont = (JspController) sess.getAttribute(sessName);

                    if (cont != null) {
                        generateExpireResponseHeaders(res,cont.getAddExpireHeaders());
                        cont.setSessionExpired(sessExp);
                        cont.setDoPostRedirected(false);
                        synchronized (cont) {
                            _jspService(req, res);
                        }
                    } else {
                        String contextToken = req.getHeader(SALMON_CONTEXT_TOKEN);
                        _jspService(req, res);
                        if (contextToken != null) {
                            TagContext t = (TagContext) req.getAttribute(TagContext.TAG_CONTEXT_REQ_KEY);
                            if (t != null)
                                sess.setAttribute(contextToken, t);
                        }

                    }
                } catch (Exception e) {
                    if (cont == null || cont.getPortletException() == null) {
                       
                        if ( e instanceof SocketException){
                        	// ignore java.net.SocketException
                            MessageLog.writeInfoMessage("SocketException would have been thrown", this);
                        }else{
                        	MessageLog.writeErrorMessage("service", e, this);
                            throw (new ServletException(e.getMessage()));
                        }
                    }

                } finally {
                    if (!createPage)
                        sess.removeAttribute(token);
                }
            }

            if (! sessionKeepAlive) {
                time = (System.currentTimeMillis() - time);

                if (!createPage)
                    addPageHit(time);

                if (Props.getSystemProps().getBooleanProperty(Props.SYS_RECORD_PAGE_TIMERS))
                    recordTimerActivity(req.getRequestURI(), time, this, false);
                com.salmonllc.util.MessageLog.writeInfoMessage("JspServlet.service() end - URI=" + req.getRequestURI() + " Time=" + time + " Init=" + (!onSession), Props.LOG_LEVEL_10, this);
            }
        } catch (java.net.SocketException e) {
        	 // ignore java.net.SocketException
            MessageLog.writeInfoMessage("SocketException would have been thrown", this);
        } catch (ServletException e) {
            if (cont == null || cont.getPortletException() == null) {
                com.salmonllc.util.MessageLog.writeErrorMessage("JspServlet.service()", e, this);
                throw (e);
            }
        } catch (IOException e) {
            com.salmonllc.util.MessageLog.writeErrorMessage("JspServlet.service()", e, this);
            throw (e);
        } catch (Exception e) {
            com.salmonllc.util.MessageLog.writeErrorMessage("JspServlet.service()", e, this);
            throw (new ServletException(e));
        }   
        finally {
            try {
            	if (!sessionKeepAlive)
            		notifyListeners((HttpServletRequest)request,(HttpServletResponse)response,false);
			} catch (Exception e) {
	            com.salmonllc.util.MessageLog.writeErrorMessage("JspServlet.service()", e, this);
	            throw (new ServletException(e));
	        }  
        }

    }

    private void generateExpireResponseHeaders(HttpServletResponse res, boolean expireResponse) {
        if (expireResponse) {
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.setHeader("Cache-Control", "no-cache");
        }
    }

    private void createPage(HttpServletRequest req, HttpServletResponse res, HttpSession sess, String url, String sessID) {
        try {
            String contextToken = "$contextToken$" + _reqCount++;
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(false);
            HttpURLConnection.setFollowRedirects(false);
            Enumeration e = req.getHeaderNames();
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                String value = req.getHeader(name);
                conn.setRequestProperty(name, value);
            }
            conn.setRequestProperty("Cookie", "sesessionid=" + sessID + ";session=" + sessID + ";sessionid=" + sessID + ";JSESSIONID=" + sessID);
            conn.setRequestProperty(SALMON_CREATE_PAGE_HEADER, "true");
            conn.setRequestProperty(SALMON_CONTEXT_TOKEN, contextToken);
            InputStream in = conn.getInputStream();
            while (in.read() > -1);
            in.close();
            conn.disconnect();
            Cookie cookie = new Cookie("JSESSIONID", sessID);
            cookie.setMaxAge(-1);
            res.addCookie(cookie);
            TagContext tc = (TagContext) sess.getAttribute(contextToken);
            if (tc != null) {
                sess.removeAttribute(contextToken);
                req.setAttribute(TagContext.TAG_CONTEXT_REQ_KEY, tc);
            }
        } catch (Exception e) {
            MessageLog.writeErrorMessage("Error creating page", e, this);
        }
    }

    private String getPageURL(HttpServletRequest req, HttpServletResponse res) {
        String url = "";
        url = URLGenerator.generateLocalHostServerURL(req);
        String baseURL = URLGenerator.generateServletBaseURL(req);
        if (baseURL != null && baseURL.length() > 0)
            baseURL += "/";
        else
            baseURL = "";
        String pageName = req.getRequestURI();
        int pos = pageName.lastIndexOf("/");
        if (pos > -1)
            pageName = pageName.substring(pos + 1);
        url += "/" + baseURL + pageName;
        String path = req.getPathInfo();
        if (path != null) {
            int ndx = path.lastIndexOf("/" + url + "/");
            if (ndx > 0 && !path.endsWith(url)) {
                ndx += url.length() + 2;
                pos = ndx + url.length() + 2;
                url = "../" + url + "/" + path.substring(ndx);
                while ((pos = path.indexOf("/", pos)) != -1) {
                    url = "../" + url;
                    pos = pos + 1;
                }
            }
        }
        String queryString = req.getQueryString();
        if (queryString != null)
            url += "?" + queryString;
        return res.encodeURL(url);
    }

    private void cacheController(String key, JspController cont) {
        try {
            if (!_cacheControllers)
                return;

            if (cont == null)
                return;

            synchronized (_controllerCache) {
                if (_controllerCache.containsKey(key))
                    return;

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream p = new ObjectOutputStream(out);
                p.writeObject(cont);
                p.flush();
                byte b[] = out.toByteArray();
                _controllerCache.put(key, b);
                out.close();
            }
        } catch (IOException e) {
            MessageLog.writeErrorMessage("Error caching controller:" + cont.getClass().getName(), e, this);
        }
 
    }

    private void loadCachedController(String key, HttpSession sess) {
        if (!_cacheControllers)
            return;

        if (sess.getAttribute(key) != null)
            return;

        byte[] b = (byte[]) _controllerCache.get(key);
        if (b == null)
            return;

        try {
            JspServletObjectInputStream in = new JspServletObjectInputStream(new ByteArrayInputStream(b), Thread.currentThread().getContextClassLoader());
            Object o = in.readObject();
            sess.setAttribute(key, o);
            in.close();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("Error reading cached controller for:" + key, e, this);
        }
    }
    public static void setUpApplicationContext(ServletContext sContext, HttpServletRequest req) {
        ApplicationContext context=ApplicationContext.getContext();
        if (context == null) {
            context=new ApplicationContext();
            ApplicationContext.setContext(context);
        }
        String webAppName = URLGenerator.generateServletBaseURL(req);
        int pos = webAppName.indexOf("/");
        if (pos > -1)
            webAppName=webAppName.substring(0, pos);
        context.setAppDocumentRoot(sContext.getRealPath("/"));
        if (webAppName==null)
            webAppName="";
        context.setAppID(webAppName);
        context.setLogger(MessageLog.getLoggerForApplication(webAppName));
    }
    public static void setUpApplicationContext(ServletContext sContext) {
        ApplicationContext context=ApplicationContext.getContext();
        if (context == null) {
            context=new ApplicationContext();
            ApplicationContext.setContext(context);
        }
        context.setAppDocumentRoot(sContext.getRealPath("/"));
        String webAppName;
        try {
            webAppName = sContext.getResource("/").toString();
            if (webAppName.endsWith("/"))
                webAppName=webAppName.substring(0,webAppName.length() - 1);
            int pos = webAppName.lastIndexOf("/");
            if (pos > -1)
                webAppName=webAppName.substring(pos + 1);
            if (webAppName==null)
                webAppName="";
            context.setAppID(webAppName);
            context.setLogger(MessageLog.getLoggerForApplication(webAppName));

        } catch (MalformedURLException e) {
            MessageLog.writeErrorMessage ("setUpApplicationContext", e, null);
        }
    }
    
	/**
	 * Adds a new listerner to this page to handle custom page events events.
	 */
	public static void addServiceListener(ServletServiceListener l) {
		if (_listeners == null)
			_listeners = new Vector();

		for (int i = 0; i < _listeners.size(); i++) {
			if (_listeners.elementAt(i) == l)
				return;
		}

		_listeners.addElement(l);
	}
	
	private void notifyListeners(HttpServletRequest req, HttpServletResponse res, boolean pre) throws Exception {
		if (_listeners == null)
			return;
		for (int i = 0; i < _listeners.size(); i++) {
			if (pre)
				((ServletServiceListener) _listeners.elementAt(i)).serviceStarted(this,req,res);
			else
				((ServletServiceListener) _listeners.elementAt(i)).serviceEnded(this,req,res);
		}
		
    }
}
