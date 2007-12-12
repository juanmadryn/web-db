package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ValidatorJavascriptTag.java $
//$Author: Dan $
//$Revision: 1 $
//$Modtime: 10/29/03 11:56a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;

/**
 * @author  Administrator  To change this generated comment edit the template variable "typecomment":  Window>Preferences>Java>Templates.  To enable and disable the creation of type comments go to  Window>Preferences>Java>Code Generation.
 */
public class ValidatorJavascriptTag extends BaseEmptyTag {

	public class Attributes {
		public String javascript;
		public String message;
		public String focuscomp;
		public String messagelocalekey;
	}	
	Attributes _att = new Attributes();	
	
	/**
	 * @see com.salmonllc.jsp.tags.BaseEmptyTag#createComponent()
	 */
	public HtmlComponent createComponent() {
		getHelper().getValidatorTag().addJavascriptRule(_att);
		_att = new Attributes();
		return null;
	}
    /**
     * This method is part of the JSP tag lib specification.
     */

    public void release() {
        super.release();
	    _att = new Attributes();
    }
	/**
	 * Sets the expression.
	 * @param expression The expression to set
	 */
	public void setJavascript(String expression) {
		_att.javascript = expression;
	}
	/**
	 * Sets the focuscomp.
	 * @param focuscomp The focuscomp to set
	 */
	public void setFocuscomp(String focuscomp) {
		_att.focuscomp = focuscomp;
	}
	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message) {
		_att.message = message;
	}
	/**
	 * Sets the messagelocalekey.
	 * @param messagelocalekey The messagelocalekey to set
	 */
	public void setMessagelocalekey(String messagelocalekey) {
		_att.messagelocalekey = messagelocalekey;
	}
}
