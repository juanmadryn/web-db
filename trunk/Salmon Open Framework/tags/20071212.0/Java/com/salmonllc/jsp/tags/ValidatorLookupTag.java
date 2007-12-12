package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ValidatorLookupTag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 9/02/03 5:31p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;

/**
 * @author  Administrator  To change this generated comment edit the template variable "typecomment":  Window>Preferences>Java>Templates.  To enable and disable the creation of type comments go to  Window>Preferences>Java>Code Generation.
 */
public class ValidatorLookupTag extends BaseEmptyTag  {

	public class Attributes {
		public String lookupfromtable;
		public String lookupwhereexpression;
		public String focuscomp;
		public String message;
		public String messagelocalekey;
		public String descriptionsource;
		public String descriptiontarget;
	}	
	
	public Attributes _att = new Attributes();
	
	/**
	 * @see com.salmonllc.jsp.tags.BaseEmptyTag#createComponent()
	 */
	public HtmlComponent createComponent() {
		getHelper().getValidatorTag().addLookupRule(_att);
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
	 * Sets the descriptionsource.
	 * @param descriptionsource The descriptionsource to set
	 */
	public void setDescriptionsource(String descriptionsource) {
		_att.descriptionsource = descriptionsource;
	}
	/**
	 * Sets the descriptiontarget.
	 * @param descriptiontarget The descriptiontarget to set
	 */
	public void setDescriptiontarget(String descriptiontarget) {
		_att.descriptiontarget = descriptiontarget;
	}
	/**
	 * Sets the focuscomp.
	 * @param focuscomp The focuscomp to set
	 */
	public void setFocuscomp(String focuscomp) {
		_att.focuscomp = focuscomp;
	}
	/**
	 * Sets the lookupfromtable.
	 * @param lookupfromtable The lookupfromtable to set
	 */
	public void setLookupfromtable(String lookupfromtable) {
		_att.lookupfromtable = lookupfromtable;
	}
	/**
	 * Sets the lookupwhereexpression.
	 * @param lookupwhereexpression The lookupwhereexpression to set
	 */
	public void setLookupwhereexpression(String lookupwhereexpression) {
		_att.lookupwhereexpression = lookupwhereexpression;
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
