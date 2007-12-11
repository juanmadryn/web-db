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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/BaseBodyTag.java $
//$Author: Len $
//$Revision: 43 $
//$Modtime: 11/10/04 10:37a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.Constants;
import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.TagWriter;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.MessageLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * This tag is the ancestor of all the body tags in the library
 */

public abstract class BaseBodyTag extends BodyTagSupport {
	public static final int MODE_INITIALIZE = 0;
	public static final int MODE_HEADER = 1;
	public static final int MODE_ROW = 2;
	public static final int MODE_FOOTER = 3;
	public static final int MODE_BOX = 4;
	public static final int MODE_GROUP_HEADER = 5;
	public static final int MODE_GROUP_FOOTER = 6;
	public static final int MODE_END = 9;

	//conversion types for getTagConvertType (Dreamweaver)
	public static final int CONV_NONE = -1;

	public static final int CONV_CUSTOM = 0; //The tag handles the conversion itself in the generateHtml Method.
	public static final int CONV_WRAP_ALL_NESTED = 1; //The conversion is handled by the engine, all nested tags are combined into one locked dreamweaver tag.
	public static final int CONV_INVISIBLE = 2; //The start and end tag convert to locked dreamweaver tags, but don't have a conversion. Items in the body are processed depending on their conversion type.
	public static final int CONV_PAGE_INVISIBLE = 3; //A special type for the page tag.
	public static final int CONV_DONT_CONVERT = 4; //Just return the tag to dreamweaver as it is

	private String _name;
	private String _visible;
	private int _mode = -1;
	private BaseTagHelper _helper = new BaseTagHelper(this);
	private TagWriter _tagWriter;
	private boolean _afterBodyCalled;

	/**
	 * This method will add a component to the controller. It is overloaded in the DataSourceTag to add a DataStoreBuffer instead
	 */
	protected void addItem() {
		HtmlComponent comp=createComponent();
		_helper.addComponent(comp);
		if (comp instanceof JspContainer)
			_helper.getTagContext().pushContainer((JspContainer)comp);
	}

	/**
	 * This method can be used by components that need to change a component after all the sub tags have been initialized.
	 */
	public void afterInit(HtmlComponent comp) {
		if (_visible != null && comp != null)
			comp.setVisible(BaseTagHelper.stringToBoolean(_visible));
	}

	/**
	 * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
	 */
	public void afterInit(DataStoreBuffer comp) {
	}

	/**
	 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
	 */

	public abstract HtmlComponent createComponent();

	/**
	 * This method is part of the JSP tag library specification
	 */
	public int doAfterBody() throws JspException {
		_afterBodyCalled = true;
		int retVal = BodyTag.SKIP_BODY;
		try {
			if (!_helper.isInitializing()) {
				HtmlComponent comp = _helper.getComponent();
				if (incrementMode())
					retVal = BodyTag.EVAL_BODY_TAG;

				if ((comp == null || comp.getVisible()) && retVal == BodyTag.SKIP_BODY) {
					BodyContent b = getBodyContent();
  				   JspController cont = _helper.getController();
                    if (! cont.getHideAllComponents()) {
					    if (cont.getCurrentRequest().getMethod().equals("GET") || cont.getDisableRedirect() || cont.isRequestFromForward() || _helper.getTagContext().getDreamWeaverMode()) {
						    if (b != null) {
							    generateComponentHTML(b);
							    b.writeOut(b.getEnclosingWriter());
							    b.clearBody();
						    } else {
							    generateComponentHTML(pageContext.getOut());
						    }
				        }
                    }
                    else
                        b.clearBody();
				}
			} else {
				afterInit(_helper.getComponent());
				afterInit(_helper.getDataSourceObject());
				if (_helper.getComponent() instanceof JspContainer)
					_helper.getTagContext().popContainer();
			}

			return retVal;
		} catch (SocketException e) {
			// ignore java.net.SocketException
			MessageLog.writeInfoMessage("SocketException would have been thrown", this);

			return BodyTag.SKIP_BODY;
		} catch (Exception e) {
			MessageLog.writeErrorMessage("doAfterBody()", e, this);
			throw new JspException(e.getMessage());
		}
	}

	/**
	 * This method is part of the JSP Tag Library Specification
	 */
	public int doStartTag() throws JspException {
		try {
			getHelper().getTagContext().pushTag(this);
			_afterBodyCalled = false;
			if (_tagWriter == null)
				_tagWriter = new TagWriter();

			if (_helper.isInitializing()) {
				addItem();
				return BodyTag.EVAL_BODY_TAG;
			} else {
				JspController jc = _helper.getController();
				if (!jc.getDoPostRedirected()) {
					if (_helper.getComponent() == null || _helper.getComponent().getVisible()) {
						initMode();
						if (isTagOKForMode())
							return BodyTag.EVAL_BODY_TAG;
						else
							return BodyTag.SKIP_BODY;
					} else
						return Tag.SKIP_BODY;
				} else
					return Tag.SKIP_BODY;
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("doStartTag()", e, this);
			throw new JspException(e.getMessage());
		}
	}
	/**
	 * This method is part of the JSP Tag Library Specification
	 */
    public void doInitBody() {
        if (alwaysWriteContent()) {
            BodyContent cont = getBodyContent();
            if (cont != null && cont instanceof com.salmonllc.jsp.engine.BodyContentImpl)
                ((com.salmonllc.jsp.engine.BodyContentImpl)cont).setPrintContent(true);
        }
    }

	public int doEndTag() throws JspException {
		try {

			//This is a work around for a bug in tomcat 4 which they refused to fix.
			//If a bodytag doesn't have anything in the body, the do afterbody method isn't called by tomcat.
			//We do a lot of important stuff in doAfterBody so it needs to be called, so we will do it ourselves if the jsp engine doesn't.
			JspController jc = _helper.getController();
			if (!jc.getDoPostRedirected()) {
				if (!_afterBodyCalled) {
					setBodyContent(pageContext.pushBody());
					doAfterBody();
					pageContext.popBody();
				}
			}
			getHelper().getTagContext().popTag();
			return EVAL_PAGE;

		} catch (Exception e) {
			MessageLog.writeErrorMessage("doEndTag()", e, this);
			throw new JspException(e.getMessage());
		}
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
			if (dt.getMode() == MODE_HEADER) {
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
			else if (comp != null) {
				if( startRow != endRow )
					comp.generateHTML(new PrintWriter(w), startRow, endRow);
				else
					comp.generateHTML(new PrintWriter(w), startRow);
			}
		}
	}

	/**
	 * This method returns the tag helper class for the custom tag.
	 */
	public BaseTagHelper getHelper() {
		return _helper;
	}

	/**
	 * This method returns the current mode for the tag. Modes are used for tags that require multiple passes to complete (Each mode corresponds to one type of pass).
	 */
	protected int getMode() {
		return _mode;
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
	 *  <BR> CONV_CUSTOM : The tag handles the conversion itself in the generateHtml Method.
	 *  <BR> CONV_WRAP_ALL_NESTED : The conversion is handled by the engine, all nested tags are combined into one locked dreamweaver tag.
	 *  <BR> CONV_INVISIBLE : The start and end tag convert to locked dreamweaver tags, but don't have a conversion. Items in the body are processed depending on their conversion type.
	 *  <BR> CONV_PAGE_INVISIBLE : A special type used for the page tag only
	 *  <BR> CONT_DONT_CONVERT : Don't convert to Dreamweaver, always return the original tag
	 */
	public abstract int getTagConvertType();

	/**
	 * This method returns the TagWriter object for the tag.
	 */
	public TagWriter getTagWriter() {
		return _tagWriter;
	}

	/**
	 * This method returns the visible attribute for the tag
	 */
	public String getVisible() {
		return _visible;
	}

	/**
	 * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
	 */
	protected boolean incrementMode() {
		return false;
	}

	/**
	 * For tags requiring more than one pass to complete, this method should be implemented. It should initialize the mode to whatever is the starting mode for the component and perform any other initializetion tasks.
	 */
	protected void initMode() {
	}

	/**
	 * The method should be overridden by a subclass to indicate if a tag is valid for a particular mode of its parent.
	 */
	protected boolean isTagOKForMode() {
		return true;
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
	 * This method should be called from the incrementMode method of a subclass when the mode should be changed.
	 */
	protected void setMode(int mode) {
		_mode = mode;
	}

	/**
	 * This method sets the name attribute for the tag.
	 */
	public void setName(String newName) {
		_name = newName;
	}

	/**
	 * This method is used by the com.salmonllc.jsp.engine.JspConverter class to specify the TagWriter to use for this tag
	 */
	public void setTagWriter(TagWriter writer) {
		_tagWriter = writer;
	}

	/**
	 * This method sets the tag's visible attribute
	 */
	public void setVisible(String val) {
		_visible = val;
	}

	/**
	 * Override this method for tags that contain NestedTags within to return true
	 */
	public boolean hasNestedTags() {
		return false;
	}

	/**
	 * Returns a string with the contents of the body of this tag
	 */
	public String getBodyContentData(boolean clear) {
		BodyContent b = getBodyContent();
		String ret = "";
		if (b != null)
			ret = b.getString();
		if (clear && b != null)
			b.clearBody();
		return ret;
	}

	/**
	 * Clears the body content
	 */
	public void clearBodyContent() {
		BodyContent b = getBodyContent();
		if (b != null)
			b.clearBody();
	}

    /**
     * Override in tags like paramater and raw that always should write body content even when initializing
     * @return
     */
    protected boolean alwaysWriteContent() {
        return false;
    }
    
    protected void writeFormHiddenFields(JspWriter w) throws IOException {
		String hidden=generateFormHiddenFieldHtml();	
		if (hidden != null && hidden.length() > 0)
			w.print(hidden);	
    }
    	
    protected String generateFormHiddenFieldHtml() {
		JspController cont=getHelper().getController();
		TagContext t = getHelper().getTagContext();
    	String ret = "";
		if (!t.getDreamWeaverMode() && !t.getRefIndexPrinted()) {
			ret = "<INPUT TYPE=\"HIDDEN\" NAME=\"Page_refIndex_hidden\" VALUE=\"" + cont.getRefIndex() + "\">";
			// Add a transaction token (if present in our session)
			javax.servlet.http.HttpSession session = cont.getSession();
			if (session != null) {
				String token =(String) session.getAttribute(HtmlPage.TRANSACTION_TOKEN_KEY);
				if (token != null) 
					ret += "<input type=\"hidden\" name=\"" + Constants.TOKEN_KEY + "\" value=\"" + token + "\">";
			}
			t.setRefIndexPrinted(true);
		}	
			
    	return ret;
    }	
    
	public String toString() {
		BodyContent b = getBodyContent();
		if ( b != null)
			return b.getString();
		else
			return "[No Content]";	
	}	
}
