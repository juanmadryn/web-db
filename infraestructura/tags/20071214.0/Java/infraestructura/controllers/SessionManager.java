package infraestructura.controllers;


//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com


import javax.servlet.http.HttpSession;


public class SessionManager implements Constants
{
	protected HttpSession _sess;
	private static final String APP_NAME="infraestructura";

	/**
	 * SessionManager constructor.
	 */
	public SessionManager(HttpSession sess) {
		super();
		_sess = sess;
		init();
	}

	/**
	 * Retruns the session
	 * @return javax.servlet.http.HttpSession
	 */
	public HttpSession getSession() {
		return _sess;
	}
	/**
	 * Returns the session website user
	 * @return com.salmonllc.petmart.WebSiteUser
	 */
	public WebSiteUser getWebSiteUser () {
		WebSiteUser wu = (WebSiteUser)_sess.getAttribute(SESSION_VALUE_WEBSITE_USER); 
		return wu;
	}

	/**
	 * This creates a new session if there is not a one that is already created.
	 */
	private void init(){
		String init = "SessionManager_" + APP_NAME + "_isInit";
		if (_sess.getAttribute(init) == null)
			_sess.setAttribute(init, new Boolean(true));
	}

	/**
	 * Remove WebSiteUser
	 */
	public void removeWebSiteUser () {
		_sess.removeAttribute(SESSION_VALUE_WEBSITE_USER);
	}

	/**
	 * Add WebSiteUser
	 * @param user WebSiteUser
	 */
	public void setWebSiteUser(WebSiteUser user) {
		_sess.setAttribute(SESSION_VALUE_WEBSITE_USER,user);
	}

	/**
	 * Returns an object from the session.
	 * @param name String
	 * @return Object
	 */
	public Object getValue(String name)	{
		return _sess.getAttribute("SessionManager_" + APP_NAME + "_" + name);
	}

	/**
	 * Stores a object to the session
	 * @param name String
	 * @param value Object
	 */
	public void putValue(String name, Object value)	{
		//Remove the object with the matching name if the object is null
		if(value == null)
			removeValue(name);

		_sess.setAttribute("SessionManager_" + APP_NAME + "_" + name, value);
	}

	public void removeValue (String name) {
		_sess.removeAttribute("SessionManager_" + APP_NAME + "_" + name);
	}
}
