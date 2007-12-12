package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ValidatorTypeCheckTag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 9/02/03 5:32p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;

/**
 * @author  Administrator  To change this generated comment edit the template variable "typecomment":  Window>Preferences>Java>Templates.  To enable and disable the creation of type comments go to  Window>Preferences>Java>Code Generation.
 */
public class ValidatorTypeCheckTag extends BaseEmptyTag {

	public class Attributes {
		public String message;
		public String focuscomp;
		public String messagelocalekey;
	}

	Attributes _att = new Attributes();	
	/**
	 * @see com.salmonllc.jsp.tags.BaseEmptyTag#createComponent()
	 */
	public HtmlComponent createComponent() {
		getHelper().getValidatorTag().addTypeCheckRule(_att);
		_att = new Attributes();
		return null;
	}
    /**
     * This method is part of the JSP tag lib specification.
     */

    public void release() {
        super.release();
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
