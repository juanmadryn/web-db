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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspDisplayBox.java $
//$Author: Dan $
//$Revision: 23 $
//$Modtime: 8/23/04 11:38a $
/////////////////////////


import com.salmonllc.html.HtmlPage;
import com.salmonllc.properties.Props;


/**
 * This container can be used to create a display box in a page. The display box has a heading caption, components in the heading band and one component inside the box.
 */
public class JspDisplayBox extends JspContainer {
	public static final int SIZE_PERCENT = 0;
    public static final int SIZE_PIXELS = 1;

    String _headingCaption;
    private String _font;
    protected String _fontStartTag;
    protected String _fontEndTag;
    private String _headingBackgroundColor;
    private String _backgroundColor;
    private int _innerCellPadding=2;
    private int _innerCellSpacing=0;
    private int _border = 0;

    private String _containerWidth = null;

    private boolean _suppressHeading = false;

    private int _cellPadding = -1;
    private int _cellSpacing = -1;

    private String _theme = null;
	private String _headingClassname;
	private String _bodyClassname;


    /**
     * Constructs an HtmlDisplayBox using the default theme.
     * @param name The name of the component
     * @param p The page that it goies in.
     */
    public JspDisplayBox(String name, HtmlPage p) {
        this(name, null, p);
    }

    /**
     * Constructs an HtmlDisplayBox using the default theme.
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page that it goes in.
     */
    public JspDisplayBox(String name, String theme, HtmlPage p) {
        super(name, p);

        _font = Props.FONT_DISPLAY_BOX_HEADING;

        setTheme(theme);
    }

    /**
     * This method gets the background color for the heading on the component
     */
    public String getBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * This method gets the border size for the component
     */
    public int getBorder() {
        return _border;
    }

    /**
     * Gets the cell padding for the table.
     */
    public int getCellPadding() {
        return _cellPadding;
    }

    /**
     * Gets the cell spacing for the table.
     */
    public int getCellSpacing() {
        return _cellSpacing;
    }


    /**
     * Gets the cell padding for the inner table.
     */
    public int getInnerCellPadding() {
        return _innerCellPadding;
    }

    /**
     * Gets the cell spacing for the inner table.
     */
    public int getInnerCellSpacing() {
        return _innerCellSpacing;
    }


    /**
     * This method will return the font type used by this control.See the Constants at the top of the class for valid font types.
     */
    public String getFont() {
        return _font;
    }

    /**
     * This method gets the background color for the heading on the component
     */
    public String getHeadingBackgroundColor() {
        return _headingBackgroundColor;
    }

    /**
     * This method returns the heading caption for the component.
     */
    public String getHeadingCaption() {
        return _headingCaption;
    }

    /**
     * This method returns the property theme for the component.
     * @param theme The theme to use.
     */
    public String getTheme() {
        return _theme;
    }

    /**
     * This method returns the width of the table.
     */
    public String getWidth() {
        return _containerWidth;
    }

    /**
     * This method sets the background color for the heading on the component
     */
    public void setBackgroundColor(String color) {
        _backgroundColor = color;
    }

    /**
     * This method will process the parms from a post for every component in the container.
     */
    public void setBorder(int border) {
        _border = border;
    }

    /**
     * Sets the cell padding for the table.
     */
    public void setCellPadding(int value) {
        _cellPadding = value;
    }

    /**
     * Sets the cell spacing for the table.
     */
    public void setCellSpacing(int value) {
        _cellSpacing = value;
    }


    /**
     * Sets the cell padding for the inner table.
     */
    public void setInnerCellPadding(int value) {
        _innerCellPadding = value;
    }

    /**
     * Sets the cell spacing for the inner table.
     */
    public void setInnerCellSpacing(int value) {
        _innerCellSpacing = value;
    }


    /**
     * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
     */
    public void setFont(String font) {
        _font = font;
        Props p = getPage().getPageProperties();
        _fontStartTag = p.getThemeProperty(_theme, font + Props.TAG_START);
        _fontEndTag = p.getThemeProperty(_theme, font + Props.TAG_END);
    }

    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontEndTag = value;
    }

    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontStartTag = value;
    }

    /**
     * This method sets the background color for the heading on the component
     */
    public void setHeadingBackgroundColor(String color) {
        _headingBackgroundColor = color;
    }

    /**
     * This method sets the text for the heading on the component
     */
    public void setHeadingCaption(String text) {
        _headingCaption = text;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        Props props = getPage().getPageProperties();

        _fontStartTag = props.getThemeProperty(theme, _font + Props.TAG_START);
        _fontEndTag = props.getThemeProperty(theme, _font + Props.TAG_END);
        _headingBackgroundColor = props.getThemeProperty(theme, Props.DISPLAY_BOX_HEADING_BACKGROUND_COLOR);
        _backgroundColor = props.getThemeProperty(theme, Props.DISPLAY_BOX_BACKGROUND_COLOR);
        _border = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_BORDER);
        _cellPadding = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_CELLPADDING);
        _cellSpacing = props.getThemeIntProperty(theme, Props.DISPLAY_BOX_CELLSPACING);

        _theme = theme;
    }

    /**
     * This method sets the minimum width of the table in either pixels or percent depending on size option.
     */
    public void setWidth(String width) {
        _containerWidth = width;
    }

    /**
     * This method sets whether or not headings should be displayed on the display box
     */
    public void suppressHeading(boolean supHead) {
        _suppressHeading = supHead;
    }

    /**
     * This method gets the font for the heading on the component
     */
    public String getHeaderFont() {
        return _font;
    }

    /**
     * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
     */
    public void setHeaderFont(String fontName) {
        setFont(fontName);
    }

    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(TagWriter t, String headingBody, String boxBody) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();

        if (!_suppressHeading) {
            int cellPadding = _cellPadding > -1 ? _cellPadding : 0;
            int cellSpacing = _cellSpacing > -1 ? _cellSpacing : 0;
            //do the heading

            String heading = "<TABLE BORDER=\"" + _border + "\" CELLSPACING=\"" + cellSpacing + "\" CELLPADDING=\"" + cellPadding + "\"";
            if (_containerWidth != null)
                heading += " WIDTH=\"" + _containerWidth + "\"";
//          added by ilev 2004-08-10
            if (getClassName() != null) 
            	heading += " CLASS=\"" + getClassName() + "\"";
//          end added by ilev
            heading += ">";
            sb.append(heading);
            String headingColor = "";

            if (_headingBackgroundColor != null)
                headingColor = " BGCOLOR=\"" + _headingBackgroundColor + "\"";
			String headingStyle = "";
			if (_headingClassname != null)
				headingStyle = " class=\"" + _headingClassname + "\"";
            sb.append("<TR><TD" + headingColor + headingStyle + "><TABLE WIDTH=\"100%\" CELLSPACING=\""+_innerCellSpacing+"\" CELLPADDING=\""+_innerCellPadding+"\">");
            sb.append("<TR><TD" + headingColor + headingStyle + " ALIGN=\"LEFT\">" + _fontStartTag + _headingCaption + _fontEndTag + "</TD><TD"  + headingColor + " ALIGN=\"RIGHT\" NOWRAP>");
            t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
            if (headingBody != null)
                t.print(headingBody, TagWriter.TYPE_CONTENT);
            sb.setLength(0);
            sb.append("</TD></TR></TABLE></TD></TR>");
        }

        String boxColor = "";
        if (_backgroundColor != null)
            boxColor = " BGCOLOR=\"" + _backgroundColor + "\"";
		String bodyStyle = "";
		if (_bodyClassname != null)
			bodyStyle = " class=\"" + _bodyClassname + "\"";
		sb.append("<TR " + bodyStyle + "><TD" + boxColor + ">");

        t.print(sb.toString(), TagWriter.TYPE_INSERTED_CONTENT);

        if (boxBody != null)
            t.print(boxBody, TagWriter.TYPE_CONTENT);

        t.print("</TD></TR></TABLE>", TagWriter.TYPE_END_TAG);
    }
    
	/**
	 * @returns true if the headind won't be displayed
	 */
	public boolean isHeadingSuppressed() {
		return _suppressHeading;
	}

	/**
	 * @returns the style sheet class name used for the heading
	 */
	public String getHeadingClassname() {
		return _headingClassname;
	}

	/**
	 * @param sets the style sheet class name for the heading
	 */
	public void setHeadingClassname(String string) {
		_headingClassname = string;
	}
	
	/**
	 * @returns the style sheet class name used for the body
	 */
	public String getBodyClassname() {
		return _bodyClassname;
	}

	/**
	 * @param sets the style sheet class name for the body
	 */
	public void setBodyClassname(String string) {
		_bodyClassname = string;
	}

}
