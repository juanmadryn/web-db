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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/events/PageEvent.java $
//$Author: Dan $ 
//$Revision: 8 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
 
import com.salmonllc.html.*;
import javax.servlet.http.*;

/**
 * This object will be created and passed to every page event method.
 * @see  PageListener
 */
 
public class PageEvent extends java.awt.AWTEvent {
	HtmlPageBase _page;
	HtmlComponent _subPage;
	boolean _continue = true;
	String _subPageName;
/**
 * Constructs a new PageEvent object.
 */
public PageEvent(HtmlPageBase p) {
	super(p,0);
	_page = p;
}
/**
 * Constructs a new PageEvent object.
 */
public PageEvent(HtmlPageBase p, HtmlComponent subPage, String subPageName) {
	super(p,0);
	_page = p;
	_subPage = subPage;
	_subPageName = subPageName;
}
/**
 * Use this method check whether processing should continue after this event;
 */
public boolean getContinueProcessing() {
	return _continue;
}
/**
 * This method will return the page that the event was fired from.
 */
public HtmlPageBase getPage() {
	return _page;
}
/**
 * This method will return the current HttpServletRequest for the page firing the event.
 */
public HttpServletRequest getRequest() {
	return _page.getCurrentRequest();
}
/**
 * This method will return the current HttpServletResponse for the page firing the event.
 */

public HttpServletResponse getResponse() {
	return _page.getCurrentResponse();
}
/**
 * This method will return the session object for the current user session.
 */
public HttpSession getSession() {
	return _page.getSession();
}
/**
 * This method will return the sub page that the event was fired from.
 */
public HtmlComponent getSubPage() {
	return _subPage;
}
/**
 * This method will return the name the sub page is registered under.
 */
public String getSubPageName() {
	return _subPageName;
}
/**
 * Use this method to stop the processing on a page.
 */
public void setContinueProcessing(boolean continueProcessing) {
	_continue = continueProcessing;
}
}
