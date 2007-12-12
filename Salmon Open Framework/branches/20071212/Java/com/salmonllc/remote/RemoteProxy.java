package com.salmonllc.remote;

import com.salmonllc.sql.Base64Encoder;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 14, 2004
 * Time: 10:05:19 AM
 * To change this template use Options | File Templates.
 */
/**
 * Created by IntelliJ IDEA. User: Fred Cahill Date: Sep 14, 2004 Time: 10:05:19 AM To change this template use Options | File Templates.
 */
public class RemoteProxy implements Serializable {

    public static final int REMOTE_STATUS_OK = 0;
    public static final int REMOTE_STATUS_BAD_REQUEST = 1;
    public static final int REMOTE_STATUS_METHOD_NOT_FOUND = 2;
    public static final int REMOTE_STATUS_OBJECT_NOT_FOUND = 3;
    public static final int REMOTE_STATUS_ACCESS_DENIED = 4;
    public static final int REMOTE_STATUS_EXCEPTION_OCCURED = 5;
    public static final int REMOTE_STATUS_CONSTRUCTOR_NOT_FOUND = 6;
    public static final int REMOTE_STATUS_CLASS_NOT_FOUND = 7;

    private String _url;
    private RemoteReflection _rr;
    private String _proxyUserID;
    private String _proxyPw;
    private ObjectOutputStream _out;
    private String _sessionId;
    private String _user;
    private String _pass;


    private class SessionKey implements RemoteReflection {
        private String _sessionkey;
        public SessionKey(String sSessionKey) {
            _sessionkey=sSessionKey;
        }
        public String getSessionKey() {
            return null;
        }
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param rr An instance of an object that implements RemoteReflection interface, needed for the session key associated with the object.
     * @param sessionId The server side session id to use.
     */
    public RemoteProxy(String sUrl,RemoteReflection rr,String sessionId) {
       _sessionId=sessionId;
       _url=sUrl;
       _rr=rr;
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param rr An instance of an object that implements RemoteReflection interface, needed for the session key associated with the object.
     * @param sSession The server side session id to use.
     * @param userID The user id for authorization to the servlet.
     * @param passWord The password for authorization to the servlet.
     */
    public RemoteProxy(String sUrl,RemoteReflection rr,String sSession,String userID, String passWord) {
        this(sUrl,rr,sSession);
       _user=userID;
       _pass=passWord;
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param rr An instance of an object that implements RemoteReflection interface, needed for the session key associated with the object.
     * @param sSession The server side session id to use.
     * @param userID The user id for authorization to the servlet.
     * @param passWord The password for authorization to the servlet.
     * @param proxyHost The host name of a proxy server to use
     * @param proxyPort The port number of a proxy server to use
     * @param proxyUser The user id to get through the proxy server
     * @param proxyPassword The password to get through the proxy server
     */
    public RemoteProxy(String sUrl,RemoteReflection rr,String sSession,String userID, String passWord,String proxyHost,String proxyPort,String proxyUser,String proxyPassword) {
        this(sUrl,rr,sSession,userID,passWord);
        if (proxyHost != null) {
            Properties p = System.getProperties();
            p.put("proxySet", "true");
            p.put("proxyHost", proxyHost);
            p.put("proxyPort", proxyPort);
        }
       _proxyUserID=proxyUser;
       _proxyPw=proxyPassword;
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param sessionkey the session key associated with the object.
     * @param sessionId The server side session id to use.
     */
    public RemoteProxy(String sUrl,String sessionkey,String sessionId) {
       SessionKey sk=new SessionKey(sessionkey);
       _sessionId=sessionId;
       _url=sUrl;
       _rr=sk;
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param sessionkey the session key associated with the object.
     * @param sSession The server side session id to use.
     * @param userID The user id for authorization to the servlet.
     * @param passWord The password for authorization to the servlet.
     */
    public RemoteProxy(String sUrl,String sessionkey,String sSession,String userID, String passWord) {
        this(sUrl,sessionkey,sSession);
       _user=userID;
       _pass=passWord;
    }


    /**
     * Creates a remote proxy to an object held on the session. This class is intended to be use by applets served up by framework html pages that need to instantiate and invoke methods of objects on the server side. The following classes need to be in the applets jar file in order for the RemoteProxy to work correctly:RemoteProxy, RemoteReflection, RemoteReflectionException
     * @param sUrl The url of the RemoteReflector Servlet. Usual URL is http://hostname/Reflect
     * @param sessionkey the session key associated with the object.
     * @param sSession The server side session id to use.
     * @param userID The user id for authorization to the servlet.
     * @param passWord The password for authorization to the servlet.
     * @param proxyHost The host name of a proxy server to use
     * @param proxyPort The port number of a proxy server to use
     * @param proxyUser The user id to get through the proxy server
     * @param proxyPassword The password to get through the proxy server
     */
    public RemoteProxy(String sUrl,String sessionkey,String sSession,String userID, String passWord,String proxyHost,String proxyPort,String proxyUser,String proxyPassword) {
        this(sUrl,sessionkey,sSession,userID,passWord);
        if (proxyHost != null) {
            Properties p = System.getProperties();
            p.put("proxySet", "true");
            p.put("proxyHost", proxyHost);
            p.put("proxyPort", proxyPort);
        }
       _proxyUserID=proxyUser;
       _proxyPw=proxyPassword;
    }



    private int getResponse(URLConnection conn) {
        return conn.getHeaderFieldInt("RemoteReflectorResponse", 0);
    }

    private void handleError(String sMethod,int ret, Throwable t) throws NoSuchMethodException,RemoteReflectionException, IllegalAccessException,InvocationTargetException {
        if (ret == REMOTE_STATUS_CLASS_NOT_FOUND) {
            throw new NoSuchMethodException("Remote Class not found: "+(t!=null?t.getMessage():sMethod));
        } else if (ret == REMOTE_STATUS_CONSTRUCTOR_NOT_FOUND) {
            throw new NoSuchMethodException("Remote Constructor not found: "+(t!=null?t.getMessage():sMethod));
        } else if (ret == REMOTE_STATUS_METHOD_NOT_FOUND) {
            throw new NoSuchMethodException("Remote Method not found: "+(t!=null?t.getMessage():sMethod));
        } else if (ret == REMOTE_STATUS_BAD_REQUEST) {
            throw new RemoteReflectionException("Bad request to Remote Reflector: "+(t!=null?t.getMessage():sMethod),t);
        } else if (ret == REMOTE_STATUS_ACCESS_DENIED) {
            if (t != null) {
                if (t instanceof IllegalAccessException) {
                    throw new IllegalAccessException("Remote Illegal Access: "+(t!=null?t.getMessage():sMethod));
                }
                else {
                    throw new InvocationTargetException(((InvocationTargetException)t).getTargetException(),"Remote Invocation Target: "+(t!=null?t.getMessage():sMethod));
                }
            }
            else
                throw new RemoteReflectionException("Remote Illegal Access.");
        } else if (ret == REMOTE_STATUS_OBJECT_NOT_FOUND) {
            throw new RemoteReflectionException("Remote Object not found: "+(t!=null?t.getMessage():sMethod), t);
        }

    }

    /**
     * Invokes the specified method on the object held on the session represented by the RemoteReflection Instance.
     * @param sMethod The method to execute on the object on the server.
     * @return Object The result of the method call if there is one.
     */
    public Object invokeMethod (String sMethod) throws RemoteReflectionException {
        return invokeMethod(sMethod,null,null);
    }


    /**
     * Invokes the specified method on the object held on the session represented by the RemoteReflection Instance.
     * @param sMethod The method to execute on the object on the server.
     * @param oaParms The parameters to pass to the method.
     * @return Object The result of the method call if there is one.
     */
    public Object invokeMethod (String sMethod,Serializable[] oaParms) throws RemoteReflectionException {
        Class[] caParms=oaParms==null?null:new Class[oaParms.length];
        if (caParms!=null) {
            for (int i=0;i<caParms.length;i++) {
               caParms[i]=oaParms[i].getClass();
            }
        }
        return invokeMethod(sMethod,caParms,oaParms);
    }

    /**
     * Invokes the specified method on the object held on the session represented by the RemoteReflection Instance.
     * @param sMethod The method to execute on the object on the server.
     * @param caParms The class type of the parameters to pass to the method.
     * @param oaParms The parameters to pass to the method.
     * @return Object The result of the method call if there is one.
     */
    public Object invokeMethod (String sMethod,Class[] caParms,Serializable[] oaParms) throws RemoteReflectionException {
        Object obj=null;
        if (sMethod==null) {
            throw new RemoteReflectionException("Method Name Required.");
        }
        try {
            URLConnection conn = openConnection(sMethod,caParms,oaParms,false);
            int result = getResponse(conn);
            if (result == REMOTE_STATUS_OK) {

                try {
                    ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                    obj = in.readObject();
                    in.close();
                }
                catch(EOFException eofe) {
                    ;
                }
                _out.close();

            } else {
                if (result == REMOTE_STATUS_BAD_REQUEST) {
                    try {
                        ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                        obj = in.readObject();
                        in.close();
                        if (obj != null && obj instanceof Exception)
                            throw((Exception)obj);
                        return obj;
                    }
                    catch(EOFException eofe) {
                        ;
                    }
                }
                handleError(sMethod,result, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteReflectionException(ex.toString(), ex);
        }
        return obj;
    }


    /**
     * Instantiates an instance of the specified class on the server and places it on the session such that it can be referred to by RemoteReflection instance.
     * @param sClass The class to instantiate on the server.
     * @return Object The instantiated object if serializable or RemoteReflection instance refering to it.
     */
    public Object instantiate(String sClass) throws RemoteReflectionException {
        return instantiate(sClass,null,null);
    }


    /**
     * Instantiates an instance of the specified class on the server and places it on the session such that it can be referred to by RemoteReflection instance.
     * @param sClass The class to instantiate on the server.
     * @return Object The instantiated object if serializable or RemoteReflection instance refering to it.
     */
    public Object instantiate(String sClass,Serializable[] oaParms) throws RemoteReflectionException {
        Class[] caParms=oaParms==null?null:new Class[oaParms.length];
        if (caParms!=null) {
            for (int i=0;i<caParms.length;i++) {
               caParms[i]=oaParms[i].getClass();
            }
        }
        return instantiate(sClass,caParms,oaParms);
    }

    /**
     * Instantiates an instance of the specified class on the server and places it on the session such that it can be referred to by RemoteReflection instance.
     * @param sClass The class to instantiate on the server.
     * @param caParms The class type of the parameters to pass to the method.
     * @param oaParms The parameters to pass to the method.
     * @return Object The instantiated object if serializable or RemoteReflection instance refering to it.
     */
    public Object instantiate(String sClass,Class[] caParms,Serializable[] oaParms) throws RemoteReflectionException {
        Object obj=null;
        if (sClass==null) {
            throw new RemoteReflectionException("Class Name Required.");
        }
        try {
            URLConnection conn = openConnection(sClass,caParms,oaParms,true);
            int result = getResponse(conn);
            if (result == REMOTE_STATUS_OK) {

                try {
                    ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                    obj = in.readObject();
                    in.close();
                    if (obj.equals(_rr.getSessionKey())) {
                        obj=_rr;
                    }
                }
                catch(EOFException eofe) {
                    ;
                }
                _out.close();

            } else {
                if (result == REMOTE_STATUS_BAD_REQUEST) {
                    try {
                        ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                        obj = in.readObject();
                        in.close();
                        if (obj != null && obj instanceof Exception)
                            throw((Exception)obj);
                        return obj;
                    }
                    catch(EOFException eofe) {
                        ;
                    }
                }
                handleError(sClass,result, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteReflectionException(ex.toString(), ex);
        }
        return obj;
    }


    private URLConnection openConnection(String method,Class[] caParms,Object[] oaParms,boolean bInstantiate) throws Exception {

        String session = _sessionId;
        String sessionKey = _rr.getSessionKey();
        if (session == null && sessionKey != null) {
            int pos = sessionKey.lastIndexOf("-");
            if (pos > -1)
                session = sessionKey.substring(pos + 1);
        }


        URL u = new URL(_url);
        URLConnection conn = u.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);


        conn.setRequestProperty("Cookie", "sesessionid=" + session + ";session=" + session + ";sessionid=" + session + ";JSESSIONID=" + session + ";jsessionid=" + session);
        if (_proxyUserID != null) {
            String authString = _proxyUserID + ":" + _proxyPw;
            String auth = "Basic " + new Base64Encoder().encode(authString);
            conn.setRequestProperty("Proxy-Authorization", auth);
        }

        if (_user!=null) {
            String sEncodedUserPassword=null;
            sEncodedUserPassword="Basic "+new sun.misc.BASE64Encoder().encode((_user+":"+_pass).getBytes());
            conn.setRequestProperty("Authorization",sEncodedUserPassword);
        }


		_out = new ObjectOutputStream(conn.getOutputStream());
        _out.writeObject(new Boolean(bInstantiate));
        _out.writeObject(sessionKey);
        _out.writeObject(method);
        _out.writeObject(caParms);
        _out.writeObject(oaParms);
        return conn;
    }

    /**
     * Returns the RemoteReflection Instance.
     * @return RemoteReflection The RemoteReflection instance passed in the constructor.
     */
    public RemoteReflection getRemoteReflection() {
        return _rr;
    }

}
