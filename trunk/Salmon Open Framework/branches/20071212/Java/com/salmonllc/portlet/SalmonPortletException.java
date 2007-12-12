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
package com.salmonllc.portlet;

import javax.portlet.PortletException;

/**
 * A special exception handled by the SalmonPortlet. This is used by the framework and should not be called directly. Instead use the method handlePortletException in JspController to report errors to the SalmonPortlet
 */
public class SalmonPortletException extends PortletException {
	PortletException _realException;
	boolean _logMessage;
	boolean _throwMessage;
	String _displayMessage;
	
	public SalmonPortletException(PortletException realException, boolean logMessage, boolean throwMessage, String displayMessage) {
		_realException = realException;
		_logMessage = logMessage;
		_throwMessage = throwMessage;
		_displayMessage = displayMessage;
	}
	/**
	 * @return true to log this message to the message log
	 */
	public boolean getLogMessage() {
		return _logMessage;
	}

	/**
	 * @return the actual exception that caused this one
	 */
	public PortletException getRealException() {
		return _realException;
	}

	/**
	 * @return whether or not the portlet should throw the message to the portal
	 */
	public boolean getThrowMessage() {
		return _throwMessage;
	}
	
	/**
	 * @return a String to display or null if nothing should display in the portlet
	 */
	public String getDisplayMessage() {
		return _displayMessage;
	}
	
	void messageLogged() {
		_logMessage=false;
	}	

}
