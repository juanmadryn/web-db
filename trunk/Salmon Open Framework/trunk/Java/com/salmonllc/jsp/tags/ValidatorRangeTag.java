package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ValidatorRangeTag.java $
//$Author: Dan $
//$Revision: 2 $
//$Modtime: 10/29/03 1:44p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;

/**
 * @author  Administrator  To change this generated comment edit the template variable "typecomment":  Window>Preferences>Java>Templates.  To enable and disable the creation of type comments go to  Window>Preferences>Java>Code Generation.
 */
public class ValidatorRangeTag extends BaseEmptyTag {

	public class Attributes {
		public String minimum;
		public String maximum;
		public String message;
		public String focuscomp;
		public String messagelocalekey;
	}	
	Attributes _att = new Attributes();	
	
	/**
	 * @see com.salmonllc.jsp.tags.BaseEmptyTag#createComponent()
	 */
	public HtmlComponent createComponent() {
		getHelper().getValidatorTag().addRangeRule(_att);
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
	 * Sets the minimum value for the tag.
	 */
	public void setMinimum(String val) {
		_att.minimum = val;
	}
	
	/**
	 * Sets the minimum value for the tag.
	 */
	public void setMaximum(String val) {
		_att.maximum = val;
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
