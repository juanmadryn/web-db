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

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * This class contains information on the portlet that launched a particluar JSP page
 */
public class PortletInfo {
	private String _portletName;
	private String _portletRenderURL;
	private String _portletActionURL;
	private Map _parmameterMap;
	private String _portletJsp;
	private boolean _fromPost;
	private String _nameSpace;
	private ActionRequest _actionRequest;
	private ActionResponse _actionResponse;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private String _portletTitle;
	
	/**
	 * @return the URL to invoke a portlet action
	 */
	public String getPortletActionURL() {
		return _portletActionURL;
	}

	/**
	 * @return the name of the portlet
	 */
	public String getPortletName() {
		return _portletName;
	}

	/**
	 * @return the URL to invoke a portlet rendering
	 */
	public String getPortletRenderURL() {
		return _portletRenderURL;
	}

	/**
	 * @param string the URL to invoke a portlet action
	 */
	void setPortletActionURL(String string) {
		_portletActionURL = string;
	}

	/**
	 * @param string the name of the portlet
	 */
	void setPortletName(String string) {
		_portletName = string;
	}

	/**
	 * @param string the URL to invoke a portlet rendering
	 */
	void setPortletRenderURL(String string) {
		_portletRenderURL = string;
	}

	/**
	 * @return the map of parameters sent to the servlet
	 */
	public Map getParmameterMap() {
		return _parmameterMap;
	}

	/**
	 * @param set the map of parameters sent to the servlet
	 */
	void setParmameterMap(Map map) {
		_parmameterMap = map;
	}

	/**
	 * @return the JSP page used by this portlet
	 */
	public String getPortletJsp() {
		return _portletJsp;
	}

	/**
	 * @param sets the JSP page used by this portlet
	 */
	void setPortletJsp(String string) {
		_portletJsp = string;
	}

	/**
	 * @return true if the page render request is due to a post request
	 */
	public boolean isFromPost() {
		return _fromPost;
	}

	/**
	 * @param set to true if the render request is due to a port request
	 */
	public void setFromPost(boolean b) {
		_fromPost = b;
	}

	/**
	 * @return a unique name used to uniquely identify components in a portlet
	 */
	public String getNameSpace() {
		return _nameSpace;
	}

	/**
	 * @param set a unique name used to uniquely identify components in a portlet
	 */
	void setNameSpace(String string) {
		_nameSpace = string;
	}

	/**
	 * @return the ActionRequest object for the portlet (submit events only)
	 */
	public ActionRequest getActionRequest() {
		return _actionRequest;
	}

	/**
	 * @return the ActionResponse object for the portlet (submit events only)
	 */
	public ActionResponse getActionResponse() {
		return _actionResponse;
	}

	/**
	 * @return the RenderRequest object for the portlet (requested events only)
	 */
	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	/**
	 * @return the RenderReesponse object for the portlet (requested events only)
	 */
	public RenderResponse getRenderResponse() {
		return _renderResponse;
	}

	
	void setActionRequest(ActionRequest request) {
		_actionRequest = request;
	}

	void setActionResponse(ActionResponse response) {
		_actionResponse = response;
	}

	void setRenderRequest(RenderRequest request) {
		_renderRequest = request;
	}

	void setRenderResponse(RenderResponse response) {
		_renderResponse = response;
	}

	/**
	 * @return the title for the portlet
	 */
	public String getPortletTitle() {
		return _portletTitle;
	}

	/**
	 * @param set the title for the portlet
	 */
	void setPortletTitle(String string) {
		_portletTitle = string;
	}

}
