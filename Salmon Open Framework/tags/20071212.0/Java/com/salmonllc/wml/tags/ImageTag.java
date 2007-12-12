//** Copyright Statement ***************************************************
// Licensed Material - Property of Salmon LLC
// (C) Copyright Salmon LLC. 1999 - All Rights Reserved
// For more information go to www.salmonllc.com
// 
// *************************************************************************
// DISCLAIMER:
// The following code has been created by Salmon LLC. The code is provided
// 'AS IS' , without warranty of any kind unless covered in another agreement
// between your corporation and Salmon LLC.  Salmon LLC shall not be liable
// for any damages arising out of your use of this, even if they have been
// advised of the possibility of such damages.
//** End Copyright Statement ***************************************************
package com.salmonllc.wml.tags;


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseEmptyTag;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.wml.WmlImage;

/**
 * This is a tag used to place images on a wml page
 */

public class ImageTag extends BaseEmptyTag {
	private String _src;
    private String _localsrc;
	private String _alt;
	private String _align;
	private String _width;
	private String _height;
	private String _vspace;
    private String _hspace;
	private String _dataSource;
    private String _class;


/**
 * This method creates the Wml Image used by the tag.
 */ 
public HtmlComponent createComponent() {

	WmlImage imgComp = new WmlImage(getName(), _src, getHelper().getController());

	if(_align != null)
		imgComp.setAlign(_align);
		
	if(_alt != null)
		imgComp.setAlt(_alt);


	if(_height != null)
		imgComp.setHeight(BaseTagHelper.stringToInt(_height));

	if(_vspace != null)
		imgComp.setVerticalSpace(BaseTagHelper.stringToInt(_vspace));

    if(_hspace != null)
		imgComp.setHorizontalSpace(BaseTagHelper.stringToInt(_hspace));

	if(_width != null)
		imgComp.setWidth(BaseTagHelper.stringToInt(_width));

    if (_dataSource != null)
     		imgComp.setDataSource(_dataSource);

    if(_localsrc != null)
		imgComp.setLocalSrc(_localsrc);


    if (getClassname() != null)
        imgComp.setClassName(getClassname());

	return imgComp;

    
}
/**
 * This method gets the alignment attribute for the tag
 */ 
public String getAlign()
{
	return _align;
}
/**
 * This method gets the alt attribute for the tag
 */

public String getAlt() {
    return _alt;
}
/**
 * This method gets the bordet attribute for the tag
 */ 

public String getLocalsrc() {
    return _localsrc;
}
/**
 * Get the Data Source the component should be bound to
 */

public String getDatasource() {
    return _dataSource;
}
/**
 * This method gets the height attribute for the tag
 */ 

public String getHeight() {
    return _height;
}

/**
 * This method gets the source attribute for the tag
 */
public String getSrc() {
    return _src;
}

    /**
     * Use this method to get the classname attribute
     */
    public String getClassname() {
        return _class;
    }


/**
 * This method gets the vspace attribute for the tag
 */

public String getVspace() {
    return _vspace;
}
    /**
     * This method gets the hspace attribute for the tag
     */

    public String getHspace() {
        return _hspace;
    }
/**
 * This method gets the width attribute for the tag
 */

public String getWidth() {
    return _width;
}
/**
 * This method is part of the JSP spec
 */ 

public void release() {
    super.release();
 	 _src = null;
	 _alt = null;
	 _align = null;
	 _localsrc = null;
	 _width = null;
	 _height = null;
	 _vspace = null;
	 _hspace = null;
     _class = null;
     _dataSource = null;
}
/**
 * This method sets the align attribute for the tag
 */

public void setAlign(String newAlign) {
    _align = newAlign;
}
/**
 * This method sets the alt attribute for the tag
 */

public void setAlt(String newAlt) {
    _alt = newAlt;
}
/**
 * This method sets the localsrc attribute for the tag
 */ 

public void setLocalsrc(String localsrc)
{
	_localsrc = localsrc;
}
/**
 * Set the Data Source the component should be bound to
 */

public void setDatasource(String val) {
    _dataSource = val;
}
/**
 * This method sets the height attribute for the tag
 */

public void setHeight(String newHeight) {
    _height = newHeight;
}
/**
 * This method sets the source attribute for the tag
 */

public void setSrc(String newSrc) {
    _src = newSrc;
}
/**
 * This method sets the hspace attribute for the tag
 */ 

public void setHspace(String hspace) {
    _hspace = hspace;
}
/**
 * This method sets the vertical space attribute for the tag
 */ 

public void setVspace(String newVspace) {
    _vspace = newVspace;
}
/**
 * This method sets the width attribute for the tag
 */

public void setWidth(String newWidth) {
    _width = newWidth;
}

    /**
     * This method sets the classname attribute.
     */
    public void setClassname(String className) {
        _class = className;
    }

}
