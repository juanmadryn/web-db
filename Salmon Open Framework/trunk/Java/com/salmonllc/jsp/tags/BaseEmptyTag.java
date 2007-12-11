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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/BaseEmptyTag.java $
//$Author: Len $
//$Revision: 25 $
//$Modtime: 11/10/04 10:51a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspController;
import com.salmonllc.util.MessageLog;
import java.io.PrintWriter;
import java.net.SocketException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * This tag is the ancestor of all the empty tags in the library
 */

public abstract class BaseEmptyTag extends TagSupport {
	private String _name;
	private String _visible;
	private BaseTagHelper _helper = new BaseTagHelper(this);

	public static final int CONV_DONT_CONVERT = 4; //Just return the tag to dreamweaver as it is
	public static final int CONV_DEFAULT = 5; //Do the default conversion on the tag (for empty tags)

	/**
	 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
	 */

	public abstract HtmlComponent createComponent();
	public void afterInit(HtmlComponent comp) {}
	/**
	* This method is part of the JSP Tag Library Specification
	*/
	public int doStartTag() throws JspException {

		try {
			if (_helper.isInitializing()) {
				HtmlComponent comp = createComponent();
				if (comp != null) {
					_helper.addComponent(comp);
					if (_visible != null)
						comp.setVisible(BaseTagHelper.stringToBoolean(_visible, true));
					afterInit(comp);
				}
			} else {
				JspController jc = _helper.getController();
				if (!jc.getDoPostRedirected()) {
                    if (jc.getHideAllComponents() && ! (this instanceof HtmlBodyTag || this instanceof HtmlEndBodyTag))
                        return BodyTag.SKIP_BODY;
				    if (jc.getCurrentRequest().getMethod().equals("GET") || jc.getDisableRedirect() || jc.isRequestFromForward() || _helper.getTagContext().getDreamWeaverMode()) {
				    	HtmlComponent comp = _helper.getComponent();
				    	if (comp == null)
				    		generateComponentHTML(pageContext.getOut());
				    	else if (comp.getVisible())
				    		generateComponentHTML(pageContext.getOut());
				    }	
				}
			}
		} catch (SocketException e) {
			// ignore java.net.SocketException
			MessageLog.writeInfoMessage("SocketException would have been thrown", this);
			return BodyTag.SKIP_BODY;
		} catch (Exception e) {
			MessageLog.writeErrorMessage ("doStartTag", e, this);
			throw new JspException(e.getMessage());
		}
		return Tag.EVAL_BODY_INCLUDE;

	}
	/**
	 * This method is called when necessary to generate the required html for a tag. It should be overridden by tags that have more Html to generate (generally tags that require several passes to complete). A tag shouldn't generate any Html itself, but should instead delagate that to the Html or JSP component within it.
	 */

	protected void generateComponentHTML(JspWriter w) throws Exception {
		HtmlComponent comp = _helper.getComponent();
		int startRow = -1;
		int endRow = -1;
        DataTableTag dt = _helper.getDataTableTag();
        ListTag l = _helper.getListTag();
        if (l!=null && dt!= null && (DataTableTag) findAncestorWithClass(l, DataTableTag.class) == dt)
        {
         dt=null;
        }
		if (dt != null) {
			if (dt.getMode() == BaseBodyTag.MODE_HEADER) {
				startRow = -1;
				endRow = -1;
			} else {
				startRow = dt.getStartRow();
				endRow = dt.getEndRow();
			}	
		} else {
			if (l != null) {
				startRow = l.getRow();
				endRow = l.getRow();
			}
		}

		if (comp != null) {
			if (startRow == -1 && endRow == -1)
				comp.generateHTML(new PrintWriter(w), -1);
			else if (comp != null)
				comp.generateHTML(new PrintWriter(w), startRow, endRow);
		}

	}
	/**
	 * This method returns the tag helper class for the custom tag.
	 */
	public BaseTagHelper getHelper() {
		return _helper;
	}
	/**
	 * This method returns the name attribute for the tag.
	 */
	public String getName() {
		return _name;
	}
	/**
	 * This method returns the Page Context object associated with the tag
	 */
	public PageContext getPageContext() {
		return pageContext;
	}
	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 * Valid values are
	 *
	 *  <BR> CONV_DEFAULT : Do the default conversion on the tag
	 *  <BR> CONT_DONT_CONVERT : Don't convert to Dreamweaver, always return the original tag
	 */
	public int getTagConvertType() {
		return CONV_DEFAULT;
	}
	/**
	 * This method returns the visible attribute for the tag
	 */
	public String getVisible() {
		return _visible;
	}
	/**
	 * This method is part of the JSP tag lib specification.
	 */

	public void release() {
		super.release();
		_name = null;
		_visible = null;

	}
	/**
	 * This method returns the name attribute for the tag.
	 */
	public void setName(String newName) {
		_name = newName;
	}
	/**
	 * This method sets the visible attribute for the tag
	 */
	public void setVisible(String val) {
		_visible = val;
	}
}
