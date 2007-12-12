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
package com.salmonllc.html;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.*;

import javax.portlet.ActionRequest;
import javax.portlet.PortletSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is a wrapper used by internal processes in the framework and isn't intended to be used directly. For more information see HttpServletRequest in the JSDK documentation.
 */
public class HttpServletRequestWrapper implements HttpServletRequest {
	HttpServletRequest _req;
	ActionRequest _preq;
	/**
	 * HttpServletRequestWrapper constructor comment.
	 */
	public HttpServletRequestWrapper(HttpServletRequest req) {
		super();
		_req = req;
	}
	/**
	 * HttpServletRequestWrapper constructor comment.
	 */
	public HttpServletRequestWrapper(ActionRequest req) {
		super();
		_preq = req;
	}
	
	/**
	 * getAttribute method comment.
	 */
	public Object getAttribute(String arg1) {
		if (_preq != null)
			return _preq.getAttribute(arg1);
		else	
			return _req.getAttribute(arg1);
	}
	/**
	 * getAuthType method comment.
	 */
	public String getAuthType() {
		if (_preq != null)
			return _preq.getAuthType();
		else	
			return _req.getAuthType();
	}

	public boolean isUserInRole(String role) {
		if (_preq != null)
			return _preq.isUserInRole(role);
		else	
			return _req.isUserInRole(role);
	}

	public Principal getUserPrincipal() {
		if (_preq != null)
			return _preq.getUserPrincipal();
		else	
			return _req.getUserPrincipal();
	}
	/**
	 * getContentLength method comment.
	 */
	public int getContentLength() {
		if (_preq != null)
			return _preq.getContentLength();
		else	
			return _req.getContentLength();
	}
	/**
	 * getContentType method comment.
	 */
	public String getContentType() {
		if (_preq != null)
			return _preq.getContentType();
		else	
			return _req.getContentType();
	}
	/**
	 * getCookies method comment.
	 */
	public javax.servlet.http.Cookie[] getCookies() {
		if (_preq != null)
			return null;
		else	
			return _req.getCookies();
	}
	/**
	 * getDateHeader method comment.
	 */
	public long getDateHeader(String arg1) {
		if (_preq != null)
			return -1;
		else	
			return _req.getDateHeader(arg1);
	}
	/**
	 * getHeader method comment.
	 */
	public String getHeader(String arg1) {
		if (_preq != null)
			return null;
		else	
			return _req.getHeader(arg1);
	}
	/**
	 * getHeaderNames method comment.
	 */
	public java.util.Enumeration getHeaderNames() {
		if (_preq != null)
			return null;
		else	
			return _req.getHeaderNames();
	}
	/**
	 * getInputStream method comment.
	 */
	public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException {
		if (_preq != null)
			return null;
		else	
			return _req.getInputStream();
	}
	/**
	 * getIntHeader method comment.
	 */
	public int getIntHeader(String arg1) {
		if (_preq != null)
			return -1;
		else	
			return _req.getIntHeader(arg1);
	}
	/**
	 * getMethod method comment.
	 */
	public String getMethod() {
		if (_preq != null)
			return null;
		else
			return _req.getMethod();
	}
	/**
	 * getParameter method comment.
	 */
	public String getParameter(String arg1) {
		String parms[] = null;
		if (_preq!=null)
			parms = _preq.getParameterValues(arg1);
		else	
			parms = _req.getParameterValues(arg1);
		if (parms == null)
			return null;
		else
			return parms[0];
	}
	/**
	 * getParameterNames method comment.
	 */
	public java.util.Enumeration getParameterNames() {
		if (_preq != null)
			return _preq.getParameterNames();
		else	
			return _req.getParameterNames();
	}
	/**
	 * getParameterValues method comment.
	 */
	public java.lang.String[] getParameterValues(String arg1) {
		if (_preq != null)
			return _preq.getParameterValues(arg1);
		else	
			return _req.getParameterValues(arg1);
	}
	/**
	 * getPathInfo method comment.
	 */
	public String getPathInfo() {
		if (_preq != null)
			return null;
		else	
			return _req.getPathInfo();
	}
	/**
	 * getPathTranslated method comment.
	 */
	public String getPathTranslated() {
		if (_preq != null)
			return null;
		else	
			return _req.getPathTranslated();
	}
	/**
	 * getProtocol method comment.
	 */
	public String getProtocol() {
		if (_preq != null)
			return null;
		else	
			return _req.getProtocol();
	}
	/**
	 * getQueryString method comment.
	 */
	public String getQueryString() {
		if (_preq != null)
			return null;
		else	
			return _req.getQueryString();
	}
	/**
	 * getReader method comment.
	 */
	public java.io.BufferedReader getReader() throws java.io.IOException {
		if (_preq != null)
			return _preq.getReader();
		else	
			return _req.getReader();
	}
	/**
	 * getRealPath method comment.
	 */
	/** @deprecated */
	public String getRealPath(String arg1) {
		if (_preq != null)
			return null;
		else	
			return null;
	}
	/**
	 * getRemoteAddr method comment.
	 */
	public String getRemoteAddr() {
		if (_preq != null)
			return null;
		else	
			return _req.getRemoteAddr();
	}
	/**
	 * getRemoteHost method comment.
	 */
	public String getRemoteHost() {
		if (_preq != null)
			return null;
		else	
			return _req.getRemoteHost();
	}
	/**
	 * getRemoteUser method comment.
	 */
	public String getRemoteUser() {
		if (_preq != null)
			return _preq.getRemoteUser();
		else	
			return _req.getRemoteUser();
	}
	/**
	 * getRequestedSessionId method comment.
	 */
	public String getRequestedSessionId() {
		if (_preq != null) {
			PortletSession sess = _preq.getPortletSession(false);
			if (sess != null)
				return sess.getId();
			else
				return null;
		}			
		else	
			return _req.getRequestedSessionId();
	}
	/**
	 * getRequestURI method comment.
	 */
	public String getRequestURI() {
		if (_preq != null)
			return null;
		else	
			return _req.getRequestURI();
	}
	/**
	 * getScheme method comment.
	 */
	public String getScheme() {
		if (_preq != null)
			return _preq.getScheme();
		else	
			return _req.getScheme();
	}
	/**
	 * getServerName method comment.
	 */
	public String getServerName() {
		if (_preq != null)
			return _preq.getServerName();
		else	

		return _req.getServerName();
	}
	/**
	 * getServerPort method comment.
	 */
	public int getServerPort() {
		if (_preq != null)
			return _preq.getServerPort();
		else	
			return _req.getServerPort();
	}
	/**
	 * getServletPath method comment.
	 */
	public String getServletPath() {
		if (_preq != null)
			return null;
		else	
			return _req.getServletPath();
	}
	/**
	 * getSession method comment.
	 */
	public HttpSession getSession(boolean arg1) {
		if (_preq != null)
			return null;
		else	
			return _req.getSession(arg1);
	}
	
	/**
	 * Returns the portlet session used by the portlet
	 */
	public PortletSession getPortletSession(boolean arg1) {
		if (_preq != null)
			return _preq.getPortletSession(arg1);
		else
			return null;		
	}	
	
	/**
	 * isRequestedSessionIdFromCookie method comment.
	 */
	public boolean isRequestedSessionIdFromCookie() {
		if (_preq != null)
			return false;
		else	
			return _req.isRequestedSessionIdFromCookie();
	}
	/**
	 * isRequestedSessionIdFromUrl method comment.
	 */
	/** @deprecated */
	public boolean isRequestedSessionIdFromUrl() {
		if (_preq != null)
			return false;
		else	
			return _req.isRequestedSessionIdFromURL();
	}
	/**
	 * isRequestedSessionIdValid method comment.
	 */
	public boolean isRequestedSessionIdValid() {
		if (_preq != null)
			return _preq.isRequestedSessionIdValid();
		else	
			return _req.isRequestedSessionIdValid();
	}
	/**
	 * This method was created in VisualAge.
	 * @param req javax.servlet.http.HttpServletRequest
	 */
	void setRequest(HttpServletRequest req) {
		_req = req;
		_preq=null;
	}
	void setRequest(ActionRequest req) {
		_preq = req;
		_req = null;
	}

	public java.util.Enumeration getAttributeNames() {
		if (_preq != null)
			return (_preq.getAttributeNames());
		else	
			return _req.getAttributeNames();
	}
	public java.lang.String getCharacterEncoding() {
		if (_preq != null)
			return (_preq.getCharacterEncoding());
		else
			return _req.getCharacterEncoding();
	}
	public javax.servlet.http.HttpSession getSession() {
		if (_preq != null)
			return null;
		else
			return _req.getSession();
	} 
	/**
	 * Returns the portlet session used by the portlet
	 */
	public PortletSession getPortletSession() {
		if (_preq != null)
			return _preq.getPortletSession();
		else
			return null;		
	}	
	/**
	* isRequestedSessionIdFromUrl method comment.
	*/
	public boolean isRequestedSessionIdFromURL() {
		if (_preq != null)
			return false;
		else
			return _req.isRequestedSessionIdFromURL();
	}

	public void setAttribute(java.lang.String param1, java.lang.Object param2) {
		if (_preq != null)
			_preq.setAttribute(param1,param2);
		else
			_req.setAttribute(param1, param2);
	}

	public java.util.Enumeration getHeaders(String name) {
		if (_preq != null)
			return null;
		else	
			return _req.getHeaders(name);
	}

	public String getContextPath() {
		if (_preq != null)
			return _preq.getContextPath();
		else
			return _req.getContextPath();
	}

	public void removeAttribute(String name) {
		if (_preq != null)
			_preq.removeAttribute(name);
		else
			_req.removeAttribute(name);
	}

	public boolean isSecure() {
		if (_preq != null)
			return _preq.isSecure();
		else
			return _req.isSecure();
	}

	public RequestDispatcher getRequestDispatcher(String dispatcher) {
		if (_preq != null)
			return null;
		else
			return _req.getRequestDispatcher(dispatcher);
	}

	public Enumeration getLocales() {
		if (_preq != null)
			return _preq.getLocales();
		else
			return _req.getLocales();
	}

	public Locale getLocale() {
		if (_preq != null)
			return _preq.getLocale();
		else
			return _req.getLocale();
	}

	public Map getParameterMap() {
		if (_preq != null)
			return _preq.getParameterMap();
		else
			return null;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public void setCharacterEncoding(String enc) {

	}

	public HttpServletRequest getWrappedRequest() {
		return _req;
	}
	
	public ActionRequest getPortletWrapppedRequest() {
		return _preq;	
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequest#getRemotePort()
	 */
	public int getRemotePort() {
		try {
			Method m=_req.getClass().getMethod("getRemotePort",null);
			return ((Integer) m.invoke(this,null)).intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequest#getLocalName()
	 */
	public String getLocalName() {
		try {
			Method m=_req.getClass().getMethod("getLocalName",null);
			return (String) m.invoke(this,null);
		} catch (Exception e) {
			return null;
		}
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequest#getLocalAddr()
	 */
	public String getLocalAddr() {
		try {
			Method m=_req.getClass().getMethod("getLocalAddr",null);
			return (String) m.invoke(this,null);
		} catch (Exception e) {
			return null;
		}
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequest#getLocalPort()
	 */
	public int getLocalPort() {
		try {
			Method m=_req.getClass().getMethod("getLocalPort",null);
			return ((Integer) m.invoke(this,null)).intValue();
		} catch (Exception e) {
			return 0;
		}
	}	
}
