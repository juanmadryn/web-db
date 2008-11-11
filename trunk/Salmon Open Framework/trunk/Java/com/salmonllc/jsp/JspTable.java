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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspTable.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 1/22/04 12:36p $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.util.Util;

/**
 * This component will generate an HTML table. It serves as a container and layout manager for other components.
 */

public class JspTable extends JspContainer {
    public static final int SIZE_PERCENT = 0;
    public static final int SIZE_PIXELS = 1;
    //
    public static final String ALIGN_LEFT = "LEFT";
    public static final String ALIGN_CENTER = "CENTER";
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String ALIGN_NONE = "";
    //
    public static final String VALIGN_BASELINE = "BASELINE";
    public static final String VALIGN_BOTTOM = "BOTTOM";
    public static final String VALIGN_MIDDLE = "MIDDLE";
    public static final String VALIGN_TOP = "TOP";
    public static final String VALIGN_NONE = "";
    //
    private int _nextTDRowNo;
    private int _nextTRRowNo;
    //
    private String _bgcolor = "";
    private String _bordercolor = "";
    private String _theme;
    //
    private int _sizeOption = SIZE_PERCENT;

    //
    private String _align = null;
    private int _border = -1;
    private int _cellPadding = -1;
    private int _cellSpacing = -1;
    private int _cols = -1;
    private int _hSpace = -1;
    private int _vSpace = -1;
    private String _height;
    private String _width;
    private String _rowStyleClass;
    //


    /**
     * Constructs a new JSP Table.
     * @param name Each component on a page must have a unique name.
     * @param p The page that the table will be added to.
     */
    public JspTable(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);
    }

    /**
     * Constructs a new JSP Table.
     * @param name Each component on a page must have a unique name.
     * @param theme The theme to use for loading properties
     * @param p The page that the table will be added to.
     */
    public JspTable(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        setTheme(theme);
    }

    /**
     * This method will generate the HTML to render the table
     */
    public void generateHTML(TagWriter writer, String content) throws java.io.IOException {   	 
        StringBuffer sb = new StringBuffer();

        sb.append("<TABLE");

        if (_border != -1)
            sb.append(" BORDER=\"" + _border + "\"");

        if (Util.isFilled(_bordercolor) )
            sb.append(" BORDERCOLOR=\"" + _bordercolor + "\"");

        if (_cellPadding != -1)
            sb.append(" CELLPADDING=\"" + _cellPadding + "\"");

        if (_cellSpacing != -1)
            sb.append(" CELLSPACING=\"" + _cellSpacing + "\"");

        if (_bgcolor != null)
            sb.append(" BGCOLOR=\"" + _bgcolor + "\"");

        if (_height != null)
            sb.append(" HEIGHT=\"" + _height + "\"");

        if (_width != null)
            sb.append(" WIDTH=\"" + _width + "\"");

        if (_align != null)
            sb.append(" ALIGN=\"" + _align + "\"");

		if (_cols != -1)
			sb.append(" COLS=\"" + _cols + "\"");

		if (_class != null)
			  sb.append(" CLASS=\"" + _class + "\"");

        if (_hSpace != -1)
            sb.append(" HSPACE=\"" + _hSpace + "\"");

        if (_vSpace != -1)
            sb.append(" VSPACE=\"" + _vSpace + "\"");

        sb.append(">");

        writer.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        writer.print(content, TagWriter.TYPE_CONTENT);
        writer.print("</TABLE>", TagWriter.TYPE_END_TAG);
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags
     */
    public String generateNextTDName(int colSpan) {
        String ret = "";
        ret = getName() + "TDRow" + _nextTDRowNo;
        _nextTDRowNo += colSpan;

        return ret;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags
     */

    public String generateNextTRName() {
        String ret = "";
        ret = getName() + "TRRow" + _nextTRRowNo;
        _nextTRRowNo++;


        return ret;
    }

    /**
     * Returns the alignment property for the table.
     * @return align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
     */
    public String getAlign() {
        return _align;
    }

    /**
     * Gets the background color for the table.
     */
    public String getBackgroundColor() {
        return _bgcolor;
    }

    /**
     * Gets the border thickness for the table.
     */
    public int getBorder() {
        return _border;
    }

    /**
     * Gets the background color for the table.
     */
    public String getBorderColor() {
        return _bordercolor;
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
     * Sets the cell padding for the table.
     */
    public int getCols() {
        return _cols;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     */
    public String getHeight() {
        return _height;
    }

    /**
     * Gets the horizontal margin for the table.
     */
    public int getHSpace() {
        return _hSpace;
    }

    /**
     * This method returns the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
     */
    public int getSizeOption() {
        return _sizeOption;
    }

    /**
     * This method returns the property theme for the component.
     * @param theme The theme to use.
     */
    public String getTheme() {
        return _theme;
    }

    /**
     * Gets the vertical margin for the table.
     */
    public int getVSpace() {
        return _vSpace;
    }

    /**
     * This method returns the width of the table.
     */
    public String getWidth() {
        return _width;
    }

    /**
     * This method should not be called directly. It is public so it can be called from com.salmonllc.jsp.tags
     */

    public void resetCounters() {
        _nextTDRowNo = 0;
        _nextTRRowNo = 0;
    }

    /**
     * Sets the alignment property for the table.
     * @param align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
     */

    public void setAlign(String align) {
        _align = align;
    }

    /**
     * Sets the background color for the table.
     */
    public void setBackgroundColor(String value) {
        _bgcolor = value;
    }

    /**
     * Sets the border width for the table.
     */
    public void setBorder(int border) {
        _border = border;
    }

    /**
     * Sets the background color for the table.
     */
    public void setBorderColor(String value) {
        _bordercolor = value;
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
     * Sets the cell padding for the table.
     */
    public void setCols(int value) {
        _cols = value;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     */
    public void setHeight(String val) {
        _height = val;
    }

    /**
     * Sets the horizontal margin for the table.
     */
    public void setHSpace(int val) {
        _hSpace = val;
    }

    /**
     * This method sets the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
     */
    public void setSizeOption(int option) {
        _sizeOption = option;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {
        Props prop = getPage().getPageProperties();

        _border = prop.getThemeIntProperty(theme, Props.TABLE_BORDER);
        _bgcolor = prop.getThemeProperty(theme, Props.TABLE_BACKGROUND_COLOR);
        _cellPadding = prop.getThemeIntProperty(theme, Props.TABLE_CELLPADDING);
        _cellSpacing = prop.getThemeIntProperty(theme, Props.TABLE_CELLSPACING);

        _theme = theme;
    }

    /**
     * Sets the vertical margin for the table.
     */
    public void setVSpace(int val) {
        _vSpace = val;
    }

    /**
     * This method sets the minimum width of the table in either pixels or percent depending on size option.
     */
    public void setWidth(String width) {
        _width = width;
    }
	/**
	 * @return the default style sheet class for rows in the table
	 */
	public String getRowStyleClassName() {
		return _rowStyleClass;
	}

	/**
	 * @param string the default style sheet class for rows in the table
	 */
	public void setRowStyleClassName(String string) {
		_rowStyleClass = string;
	}

}
