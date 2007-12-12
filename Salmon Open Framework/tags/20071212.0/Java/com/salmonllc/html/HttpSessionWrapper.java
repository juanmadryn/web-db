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

import java.util.Enumeration;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * Wrapper for the http session object, used by the framework to adapt portlet sessions to http sessions
 */
public class HttpSessionWrapper implements HttpSession {
	HttpSession _sess;
	PortletSession _psess;

	public HttpSessionWrapper(PortletSession sess) {
		_psess = sess;
	}
	public HttpSessionWrapper(HttpSession sess) {
		_sess = sess;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String arg0) {
		if (_sess != null)
			return _sess.getAttribute(arg0);
		else
			return _psess.getAttribute(arg0,PortletSession.APPLICATION_SCOPE);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		if (_sess != null)
			return _sess.getAttributeNames();
		else
			return _psess.getAttributeNames(PortletSession.APPLICATION_SCOPE);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		if (_sess != null)
			return _sess.getCreationTime();
		else
			return _psess.getCreationTime();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		if (_sess != null)
			return _sess.getId();
		else
			return _psess.getId();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		if (_sess != null)
			return _sess.getLastAccessedTime();
		else
			return _psess.getLastAccessedTime();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		if (_sess != null)
			return _sess.getMaxInactiveInterval();
		else
			return _psess.getMaxInactiveInterval();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		//if (_sess != null)
		//	return _sess.getServletContext();
		//else
			return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public PortletContext getPortletContext() {
		if (_sess != null)
			return null;
		else
			return _psess.getPortletContext();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public HttpSessionContext getSessionContext() {
		if (_sess != null)
			return _sess.getSessionContext();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String arg0) {
		if (_sess != null)
			return _sess.getAttribute(arg0);
		else
			return _psess.getAttribute(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		if (_sess != null)
			return _sess.getValueNames();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {
		if (_sess != null)
			_sess.invalidate();
		else
			_psess.invalidate();

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		if (_sess != null)
			return _sess.isNew();
		else
			return _psess.isNew();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
	 */
	public void putValue(String arg0, Object arg1) {
		if (_sess != null)
			_sess.setAttribute(arg0, arg1);
		else
			_psess.setAttribute(arg0, arg1,PortletSession.APPLICATION_SCOPE);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String arg0) {
		if (_sess != null)
			_sess.removeAttribute(arg0);
		else
			_psess.removeAttribute(arg0,PortletSession.APPLICATION_SCOPE);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String arg0) {
		if (_sess != null)
			_sess.removeAttribute(arg0);
		else
			_psess.removeAttribute(arg0,PortletSession.APPLICATION_SCOPE);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String arg0, Object arg1) {
		if (_sess != null)
			_sess.setAttribute(arg0, arg1);
		else
			_psess.setAttribute(arg0, arg1,PortletSession.APPLICATION_SCOPE);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int arg0) {
		if (_sess != null)
			_sess.setMaxInactiveInterval(arg0);
		else
			_psess.setMaxInactiveInterval(arg0);

	}

}
