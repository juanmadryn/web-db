package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /sofia/sourcecode/com/salmonllc/jsp/tags/HtmlBodyTag.java $
//$Author: Tezgiden $
//$Revision: 13 $
//$Modtime: 9/30/03 4:31p $
/////////////////////////
import javax.servlet.jsp.JspWriter;

import com.salmonllc.jsp.JspController;

/**
 * This tag is used to implement the Html Body tag
 */
public class HtmlBodyTag extends BaseEmptyTag
{
//	String _background,_bgcolor,_text,_link,_alink,_vlink,_onload,getClassname(),_leftmargin,_rightmargin,_topmargin,_marginwidth,_marginheight;

    private String _background;
    private String _bgcolor;
    private String _text;
    private String _link;
    private String _alink;
    private String _vlink;
    private String _onload;
    private String _class;
    private String _leftmargin;
    private String _rightmargin;
    private String _topmargin;
    private String _marginwidth;
    private String _marginheight;
    private String _onblur;
    private String _onkeydown;

    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent()
    {
        BaseTagHelper helper = getHelper();
        JspController cont = helper.getController();

        if (!BaseTagHelper.isEmpty(_alink))
            cont.setActiveLinkColor(_alink);
        if (!BaseTagHelper.isEmpty(_background))
            cont.setBackground(_background);
        if (!BaseTagHelper.isEmpty(_bgcolor))
            cont.setBackgroundColor(_bgcolor);
        if (!BaseTagHelper.isEmpty(_class))
            cont.setStyle(_class);
        if (!BaseTagHelper.isEmpty(_leftmargin))
            cont.setLeftMargin(BaseTagHelper.stringToInt(_leftmargin));
        if (!BaseTagHelper.isEmpty(_link))
            cont.setLinkColor(_link);
        if (!BaseTagHelper.isEmpty(_marginwidth))
            cont.setMarginWidth(BaseTagHelper.stringToInt(_marginwidth));
        if (!BaseTagHelper.isEmpty(_marginheight))
            cont.setMarginHeight(BaseTagHelper.stringToInt(_marginheight));
        if (!BaseTagHelper.isEmpty(_onload))
            cont.setOnLoad(_onload);
        if (!BaseTagHelper.isEmpty(_onblur))
            cont.setOnBlur(_onblur);
	     if (!BaseTagHelper.isEmpty(_onkeydown))
	         cont.setOnKeydown(_onkeydown);
        if (!BaseTagHelper.isEmpty(_rightmargin))
            cont.setRightMargin(BaseTagHelper.stringToInt(_rightmargin));
        if (!BaseTagHelper.isEmpty(_topmargin))
            cont.setTopMargin(BaseTagHelper.stringToInt(_topmargin));
        if (!BaseTagHelper.isEmpty(_text))
            cont.setTextColor(_text);
        if (!BaseTagHelper.isEmpty(_vlink))
            cont.setVisitedLinkColor(_vlink);


        return null;
    }
    /**
     * This method is called when necessary to generate the required html for a tag. It should be overridden by tags that have more Html to generate (generally tags that require several passes to complete).
     */

    protected void generateComponentHTML(JspWriter w) throws Exception
    {
        JspController cont = getHelper().getController();
        cont.generateBodyHtml(w);
    }
    /**
     * Clears the instance variable in the tag
     */
    public void release()
    {
        _background = null;
        _bgcolor = null;
        _text = null;
        _link = null;
        _alink = null;
        _vlink = null;
        _onload = null;
        _class = null;
        _leftmargin = null;
        _rightmargin = null;
        _topmargin = null;
        _marginwidth = null;
        _marginheight = null;
        _onblur=null;
	    _onkeydown=null;

    }
    /**
     * Sets the alink attribute.
     */
    public void setAlink(String newValue)
    {
        _alink = newValue;
    }
    /**
     * Sets the background attribute.
     */
    public void setBackground(String newValue)
    {
        _background = newValue;
    }
    /**
     * Sets the bgcolor attribute.
     */
    public void setBgcolor(String newValue)
    {
        _bgcolor = newValue;
    }
    /**
     * Sets the class attribute.
     */
    public void setClassname(String newValue)
    {
        _class = newValue;
    }
    /**
     * Sets the leftmargin attribute.
     */
    public void setLeftmargin(String newValue)
    {
        _leftmargin = newValue;
    }
    /**
     * Sets the link attribute.
     */
    public void setLink(String newValue)
    {
        _link = newValue;
    }
    /**
     * Sets the marginheight attribute.
     */
    public void setMarginheight(String newValue)
    {
        _marginheight = newValue;
    }
    /**
     * Sets the marginwidth attribute.
     */
    public void setMarginwidth(String newValue)
    {
        _marginwidth = newValue;
    }
    /**
     * Sets the onload attribute.
     */
    public void setOnblur(String newValue)
    {
        _onblur = newValue;
    }
	/**
     * Sets the onkeydown attribute.
     */
    public void setOnkeydown(String newValue)
    {
        _onkeydown = newValue;
    }
    /**
     * Sets the onload attribute.
     */
    public void setOnload(String newValue)
    {
        _onload = newValue;
    }
    /**
     * Sets the rightmargin attribute.
     */
    public void setRightmargin(String newValue)
    {
        _rightmargin = newValue;
    }
    /**
     * Sets the text attribute.
     */
    public void setText(String newValue)
    {
        _text = newValue;
    }
    /**
     * Sets the topmargin attribute.
     */
    public void setTopmargin(String newValue)
    {
        _topmargin = newValue;
    }
    /**
     * Sets the vlink attribute.
     */
    public void setVlink(String newValue)
    {
        _vlink = newValue;
    }
}
