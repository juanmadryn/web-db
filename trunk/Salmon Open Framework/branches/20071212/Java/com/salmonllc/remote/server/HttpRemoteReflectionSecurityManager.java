package com.salmonllc.remote.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.salmonllc.servlets.RemoteReflector;

/**
 * Created by IntelliJ IDEA. User: Fred Cahill Date: Oct 4, 2004 Time: 2:23:20
 * PM To change this template use Options | File Templates.
 */
/*
 * This class is another example of a RemoteReflectionSecurityPolicy instance
 * that can be assigned to RemoteReflector servlet. Use setAsSecurityManager to
 * set this as the SecurityManager for a particular session. This class is given
 * such that it can be extended to specify your own logic for allowing
 * instantiation and methods based on some arbitrary security.
 */
public class HttpRemoteReflectionSecurityManager extends RemoteReflectionSecurityManager {
	private HttpServletRequest _req;
	private HttpSession _sess;
	private String _reflectionallowedattribute;

	/**
	 * Creates an instance of HttpRemoteReflectionSecurityManager based on the
	 * passed HttpServletRequest.
	 * 
	 * @param req
	 *            HttpServletRequest The request to base security on.
	 */
	public HttpRemoteReflectionSecurityManager(HttpServletRequest req) {
		this(req, null);
	}

	/**
	 * Creates an instance of HttpRemoteReflectionSecurityManager based on the
	 * passed HttpServletRequest.
	 * 
	 * @param req
	 *            HttpServletRequest The request to base security on.
	 * @param String
	 *            the name of a Boolean Session Attribute to indicate whether
	 *            reflection is allowed or not.
	 */
	public HttpRemoteReflectionSecurityManager(HttpServletRequest req, String sReflectionAllowed) {
		super();
		_req = req;
		_sess = _req.getSession();
		_reflectionallowedattribute = sReflectionAllowed;
	}

	/**
	 * No args constructor for the remote reflection security manager
	 */
	public HttpRemoteReflectionSecurityManager() {
		super();
	}	
	/**
	 * Checks to see if Instantiation is allowed for the passed class, if the
	 * session is new then Instantiation is not allowed.
	 * 
	 * @param cl
	 *            java.lang.Class The class to check to see if allowed to
	 *            instantiate.
	 * @return boolean indicates wheather the class is allowed to be
	 *         instantiated by the RemoteReflector Servlet
	 */
	public boolean isInstantiationAllowed(Class cl) {
		if (_sess.isNew())
			return false;
		Boolean bAllowed = (Boolean) _sess.getAttribute(_reflectionallowedattribute);
		if (bAllowed != null && !bAllowed.booleanValue())
			return false;
		return super.isInstantiationAllowed(cl);
	}

	/**
	 * Checks to see if Instantiation is allowed for the passed class, if the
	 * session is new then Method Call is not allowed.
	 * 
	 * @param obj
	 *            java.lang.Object The object for which you want to execute the
	 *            method on.
	 * @param sMethod
	 *            java.lang.String The method you want to check if you are
	 *            allowed to execute.
	 * @return boolean indicates wheather the method is allowed to be executed
	 *         on the passed object by the RemoteReflector Servlet
	 */
	public boolean isMethodCallAllowed(Object obj, String sMethod) {
		if (_sess.isNew())
			return false;
		Boolean bAllowed = (Boolean) _sess.getAttribute(_reflectionallowedattribute);
		if (bAllowed != null && !bAllowed.booleanValue())
			return false;
		return super.isMethodCallAllowed(obj, sMethod);
	}

	/**
	 * Sets this instance as the RemoteReflectionPolicy for the current session.
	 */
	public void setAsSecurityManager() {
		RemoteReflector.setSecurityPolicy(_sess, this);
	}

    /**
     * Returns true if a security policy is set for this session
     */
    public static boolean isSecurityPolicySet(HttpSession sess) {
    	return RemoteReflector.isSecurityPolicySet(sess);
    }	
    
	/**
	 * @return Returns the current servlet request.
	 */
	public HttpServletRequest getReq() {
		return _req;
	}
	/**
	 * @return Returns the current servlet session.
	 */
	public HttpSession getSess() {
		return _sess;
	}
	
	
	/**
	 * Framework method, do not call directly.
	 */
	public void setReq(HttpServletRequest req) {
		_req = req;
	}
	/**
	 * @Framework method, do not call directly
	 */
	public void setSess(HttpSession sess) {
		_sess = sess;
	}
}