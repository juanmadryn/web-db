package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ImageTag.java $
//$Author: Dan $
//$Revision: 21 $
//$Modtime: 10/30/04 11:27a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlImage;
import com.salmonllc.util.Util;


/**
 * This is a tag used to place images on a page
 */

public class ImageTag extends BaseEmptyTag
{
    private String _src;
    private String _alt;
    private String _align;
    
    private String _backgroundColor;
    private String _fDisplaystyle;
    
    private String _border;
    private String _width;
    private String _height;
    private String _vspace;
    private String _hspace;
    private String _usemap;
    private String _dataSource;
    private String _srclocalkey;
    private String _altlocalkey;
    private String _title;

/**
 * This method creates the Html Image used by the tag.
 */
public HtmlComponent createComponent()
{

    HtmlImage imgComp = null;
    boolean generateImage = false;
    if (Util.isFilled(getDisplaystyle()))
    {
        if (getDisplaystyle().equalsIgnoreCase("pixel"))
        {
            generateImage = true;
            imgComp = new HtmlImage(getName(), null, 1, getHelper().getController());
            imgComp.setDisplayStyle(HtmlImage.STYLE_PIXEL);
            String width = getWidth();

            if (width.indexOf("%") > -1)
            {
                imgComp.setWidthSizeOption(HtmlImage.SIZE_PERCENT);
            }
            else
            {
                imgComp.setWidthSizeOption(HtmlImage.SIZE_PIXELS);
            }

            imgComp.setBackgroundColor(Util.stringToColor("#" + getBgcolor()));
            imgComp.setWidth(Util.stringToInt(Util.stripChars(width, "0123456789", null)));
        }

    }

    if (!generateImage)
    {
        imgComp = new HtmlImage(getName(), _src, getHelper().getController());

        if (_width != null)
            imgComp.setWidth(BaseTagHelper.stringToInt(_width));
    }

    if (_align != null)
        imgComp.setAlign(_align);

    if (_alt != null)
        imgComp.setAlt(_alt);

    if (_border != null)
        imgComp.setBorder(BaseTagHelper.stringToInt(_border));

    if (_height != null)
        imgComp.setHeight(BaseTagHelper.stringToInt(_height));

    if (_vspace != null)
        imgComp.setVerticalSpace(BaseTagHelper.stringToInt(_vspace));

    if (_hspace != null)
    	imgComp.setHorizontalSpace(BaseTagHelper.stringToInt(_hspace));

    if (_dataSource != null)
        imgComp.setDataSource(_dataSource);
    if (_srclocalkey != null)
        imgComp.setSourceLocaleKey(_srclocalkey);
    if (_altlocalkey != null)
        imgComp.setAltLocaleKey(_altlocalkey);
    if (_usemap != null)
        imgComp.setUseMap(_usemap);
    if (_title != null)
        imgComp.setTitle(_title);

    return imgComp;

}
	/**
	 * This method gets the alignment attribute for the tag
	 */
	public String getAlign() {
		return _align;
	}
	/**
	 * This method gets the alt attribute for the tag
	 */

	public String getAlt() {
		return _alt;
	}
    /**
     * Get the tag's background color attribute
     */
    public String getBgcolor()
    {
        return _backgroundColor;
    }
	/**
	 * This method gets the bordet attribute for the tag
	 */

	public String getBorder() {
		return _border;
	}
	/**
	 * Get the Data Source the component should be bound to
	 */

	public String getDatasource() {
		return _dataSource;
	}
/**
 * Insert the method's description here.
 * Creation date: (10/8/2002 9:10:08 AM)
 * @return java.lang.String
 */
public java.lang.String getDisplaystyle() {
	return _fDisplaystyle;
}
	/**
	 * This method gets the height attribute for the tag
	 */

	public String getHeight() {
		return _height;
	}

public String getHspace()
{
	return _hspace;
}
	/**
	 * This method gets the source attribute for the tag
	 */

	public String getSrc() {
		return _src;
	}
	/**
	 * This method gets the source attribute for the tag
	 */

	public String getUsemap() {
		return _usemap;
	}
	/**
	 * This method gets the vspace attribute for the tag
	 */

	public String getVspace() {
		return _vspace;
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
		_border = null;
		_width = null;
		_height = null;
		_vspace = null;
		_hspace = null;
		_usemap = null;
		_srclocalkey = null;
		_altlocalkey = null;
		_backgroundColor = null;
		_fDisplaystyle= null;
		_title= null;
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
	 * This method sets the altlocalekey attribute for the tag
	 */
	public void setAltlocalekey(String newValue) {
		_altlocalkey = newValue;
	}
    /**
     * Sets the tag's background color attribute
     */
    public void setBgcolor(String backgroundColor)
    {
        _backgroundColor = backgroundColor;
    }
	/**
	 * This method sets the border attribute for the tag
	 */

	public void setBorder(String newBorder) {
		_border = newBorder;
	}
	/**
	 * Set the Data Source the component should be bound to
	 */

	public void setDatasource(String val) {
		_dataSource = val;
	}
/**
 * Insert the method's description here.
 * Creation date: (10/8/2002 9:10:08 AM)
 * @param newDisplaystyle java.lang.String
 */
public void setDisplaystyle(java.lang.String newDisplaystyle) {
	_fDisplaystyle = newDisplaystyle;
}
	/**
	 * This method sets the height attribute for the tag
	 */

	public void setHeight(String newHeight) {
		_height = newHeight;
	}

public void setHspace(String hSpace)
{
	_hspace = hSpace;
}
	/**
	 * This method sets the source attribute for the tag
	 */

	public void setSrc(String newSrc) {
		_src = newSrc;
	}
	/**
	 * This method sets the srclocalekey attribute for the tag
	 */
	public void setSrclocalekey(String newValue) {
		_srclocalkey = newValue;
	}
	/**
	 * This method sets the use map attribute for the tag
	 */

	public void setUsemap(String newUsemap) {
		_usemap = newUsemap;
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
	 * @return Returns the _title.
	 */
	public String getTitle()
	{
		return _title;
	}
	/**
	 * @param string The _title to set.
	 */
	public void setTitle(String string)
	{
		this._title = string;
	}
}
