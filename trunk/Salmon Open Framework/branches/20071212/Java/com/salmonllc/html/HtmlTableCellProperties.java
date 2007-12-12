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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTableCellProperties.java $
//$Author: Dan $
//$Revision: 15 $
//$Modtime: 4/13/04 9:51a $
/////////////////////////

/**
 * This is used in conjunction with HtmlTable to set the properties for a particular cell
 * @see  HtmlTable#setComponentAt
 */
public class HtmlTableCellProperties implements java.io.Serializable {
    private String _align = HtmlTable.ALIGN_NONE;
    private String _vAlign = HtmlTable.VALIGN_NONE;
    private String _bgColor = null;
    private boolean _wrap = true;
    private int _colSpan = 1;
    private int _rowSpan = 1;
    //
    private int _cellWidth = -1;
    private int _cellHeight = -1;
    //
    private int _cellWidthSizeOption = HtmlTable.SIZE_PIXELS;
    private int _cellHeightSizeOption = HtmlTable.SIZE_PIXELS;
    HtmlStyle _style;
    String _onMouseDown;
    String _onMouseOut;
    String _onMouseOver;
    String _onMouseUp;
    String _onClick;


    /** The following constants cover most uses of HtmlTableCellProperties.
     * They are to be used instead of creating local instances page after page.
     */

    public static HtmlTableCellProperties TOP_LEFT;
    public static HtmlTableCellProperties TOP_LEFT_NO_WRAP;
    public static HtmlTableCellProperties TOP_CENTER;
    public static HtmlTableCellProperties TOP_CENTER_NO_WRAP;
    public static HtmlTableCellProperties TOP_RIGHT;
    public static HtmlTableCellProperties TOP_RIGHT_NO_WRAP;
    public static HtmlTableCellProperties MIDDLE_LEFT;
    public static HtmlTableCellProperties MIDDLE_LEFT_NO_WRAP;
    public static HtmlTableCellProperties MIDDLE_CENTER;
    public static HtmlTableCellProperties MIDDLE_CENTER_NO_WRAP;
    public static HtmlTableCellProperties MIDDLE_RIGHT;
    public static HtmlTableCellProperties MIDDLE_RIGHT_NO_WRAP;
    public static HtmlTableCellProperties BOTTOM_LEFT;
    public static HtmlTableCellProperties BOTTOM_LEFT_NO_WRAP;
    public static HtmlTableCellProperties BOTTOM_CENTER;
    public static HtmlTableCellProperties BOTTOM_CENTER_NO_WRAP;
    public static HtmlTableCellProperties BOTTOM_RIGHT;
    public static HtmlTableCellProperties BOTTOM_RIGHT_NO_WRAP;

    static {
        TOP_LEFT = new HtmlTableCellProperties();
        TOP_LEFT.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_LEFT.setAlign(HtmlTable.ALIGN_LEFT);

        TOP_LEFT_NO_WRAP = new HtmlTableCellProperties();
        TOP_LEFT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_LEFT_NO_WRAP.setAlign(HtmlTable.ALIGN_LEFT);
        TOP_LEFT_NO_WRAP.setWrap(false);

        TOP_CENTER = new HtmlTableCellProperties();
        TOP_CENTER.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_CENTER.setAlign(HtmlTable.ALIGN_CENTER);

        TOP_CENTER_NO_WRAP = new HtmlTableCellProperties();
        TOP_CENTER_NO_WRAP.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_CENTER_NO_WRAP.setAlign(HtmlTable.ALIGN_CENTER);
        TOP_CENTER_NO_WRAP.setWrap(false);

        TOP_RIGHT = new HtmlTableCellProperties();
        TOP_RIGHT.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_RIGHT.setAlign(HtmlTable.ALIGN_RIGHT);

        TOP_RIGHT_NO_WRAP = new HtmlTableCellProperties();
        TOP_RIGHT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_TOP);
        TOP_RIGHT_NO_WRAP.setAlign(HtmlTable.ALIGN_RIGHT);
        TOP_RIGHT_NO_WRAP.setWrap(false);

        MIDDLE_LEFT = new HtmlTableCellProperties();
        MIDDLE_LEFT.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_LEFT.setAlign(HtmlTable.ALIGN_LEFT);

        MIDDLE_LEFT_NO_WRAP = new HtmlTableCellProperties();
        MIDDLE_LEFT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_LEFT_NO_WRAP.setAlign(HtmlTable.ALIGN_LEFT);
        MIDDLE_LEFT_NO_WRAP.setWrap(false);

        MIDDLE_CENTER = new HtmlTableCellProperties();
        MIDDLE_CENTER.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_CENTER.setAlign(HtmlTable.ALIGN_CENTER);

        MIDDLE_CENTER_NO_WRAP = new HtmlTableCellProperties();
        MIDDLE_CENTER_NO_WRAP.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_CENTER_NO_WRAP.setAlign(HtmlTable.ALIGN_CENTER);
        MIDDLE_CENTER_NO_WRAP.setWrap(false);

        MIDDLE_RIGHT = new HtmlTableCellProperties();
        MIDDLE_RIGHT.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_RIGHT.setAlign(HtmlTable.ALIGN_RIGHT);

        MIDDLE_RIGHT_NO_WRAP = new HtmlTableCellProperties();
        MIDDLE_RIGHT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_MIDDLE);
        MIDDLE_RIGHT_NO_WRAP.setAlign(HtmlTable.ALIGN_RIGHT);
        MIDDLE_RIGHT_NO_WRAP.setWrap(false);

        BOTTOM_LEFT = new HtmlTableCellProperties();
        BOTTOM_LEFT.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_LEFT.setAlign(HtmlTable.ALIGN_LEFT);

        BOTTOM_LEFT_NO_WRAP = new HtmlTableCellProperties();
        BOTTOM_LEFT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_LEFT_NO_WRAP.setAlign(HtmlTable.ALIGN_LEFT);
        BOTTOM_LEFT_NO_WRAP.setWrap(false);

        BOTTOM_CENTER = new HtmlTableCellProperties();
        BOTTOM_CENTER.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_CENTER.setAlign(HtmlTable.ALIGN_CENTER);

        BOTTOM_CENTER_NO_WRAP = new HtmlTableCellProperties();
        BOTTOM_CENTER_NO_WRAP.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_CENTER_NO_WRAP.setAlign(HtmlTable.ALIGN_CENTER);
        BOTTOM_CENTER_NO_WRAP.setWrap(false);

        BOTTOM_RIGHT = new HtmlTableCellProperties();
        BOTTOM_RIGHT.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_RIGHT.setAlign(HtmlTable.ALIGN_RIGHT);

        BOTTOM_RIGHT_NO_WRAP = new HtmlTableCellProperties();
        BOTTOM_RIGHT_NO_WRAP.setVertAlign(HtmlTable.VALIGN_BOTTOM);
        BOTTOM_RIGHT_NO_WRAP.setAlign(HtmlTable.ALIGN_RIGHT);
        BOTTOM_RIGHT_NO_WRAP.setWrap(false);
    }

    /**
     * Constructs a new empty object
     */
    public HtmlTableCellProperties() {
        super();
    }

    /**
     * Constructs a new object with all the properties set
     * @param align Valid values are HtmlTable.ALIGN_LEFT,HtmlTable.ALIGN_CENTER,HtmlTable.ALIGN_RIGHT,HtmlTable.ALIGN_NONE
     * @param vAlign Valid values are HtmlTable.VALIGN_BASELINE,HtmlTable.VALIGN_BOTTOM,HtmlTable.VALIGN_MIDDLE and HtmlTable.VALIGN_TOP.
     * @param backGroundColor The background color for the cell.
     * @param wrap True if the contents of the cell should word wrap.
     */
    public HtmlTableCellProperties(String align, String vAlign, String backGroundColor, boolean wrap) {
        super();
        _align = align;
        _vAlign = vAlign;
        _bgColor = backGroundColor;
        _wrap = wrap;
    }

    /**
     * Returns the alignment for the cell. Values are HtmlTable.ALIGN_CENTER, HtmlTable.ALIGN_RIGHT, HtmlTable.ALIGN_LEFT, HtmlTable.ALIGN_NONE
     */

    public String getAlign() {

        return _align;
    }

    /**
     * Returns the background color for the cell
     */
    public String getBackgroundColor() {
        return _bgColor;
    }

    /**
     * This method gets the height of the table cell.
     */
    public int getCellHeight() {
        return _cellHeight;
    }

    /**
     * This method gets the height size option of the table cell.
     */
    public int getCellHeightSizeOption() {
        return _cellHeightSizeOption;
    }

    /**
     * This method gets the width of the table cell.
     */
    public int getCellWidth() {
        return _cellWidth;
    }

    /**
     * This method gets the width size option of the table cell.
     */
    public int getCellWidthSizeOption() {
        return _cellWidthSizeOption;
    }

    /**
     * This method gets the column span for a table cell
     */
    public int getColumnSpan() {
        return _colSpan;
    }

    /**
     * Use this method to get the javascript that will be executed when the user clicks on one of the components in the link.
     */
    public String getOnClick() {
        return _onClick;
    }

    /**
     * Use this method to get the javascript that will be executed when mouse clicks on the components with either mouse button
     */
    public String getOnMouseDown() {
        return _onMouseDown;
    }

    /**
     * Use this method to get the javascript that will be executed when the mouse passes over out of all the components
     */
    public String getOnMouseOut() {
        return _onMouseOut;
    }

    /**
     * Use this method to get the javascript that will be executed when the mouse passes over any component in the link
     */
    public String getOnMouseOver() {
        return _onMouseOver;
    }

    /**
     * Use this method to get the javascript that will be executed when releases a mouse button while the mouse is over the component
     */
    public String getOnMouseUp() {
        return _onMouseUp;
    }

    /**
     * This method gets the column span for a table cell
     */
    public int getRowSpan() {
        return _rowSpan;
    }

    /**
     * This method was created in VisualAge.
     * @return com.salmonllc.html.HtmlStyle
     */
    public HtmlStyle getStyle() {
        return _style;
    }

    /**
     * Sets the vertical align property for the page. Valid values are HtmlTable.VALIGN_BASELINE,HtmlTable.VALIGN_BOTTOM,HtmlTable.VALIGN_MIDDLE,HtmlTable.VALIGN_TOP,HtmlTable.VALIGN_NONE
     */
    public String getVertAlign() {
        return _vAlign;
    }

    /**
     * true false indicating whether the text in the component should wrap or not
     */
    public boolean getWrap() {
        return _wrap;
    }

    /**
     * Sets the alignment for the cell. Values are HtmlTable.ALIGN_CENTER, HtmlTable.ALIGN_RIGHT, HtmlTable.ALIGN_LEFT, HtmlTable.ALIGN_NONE
     */
    public void setAlign(String value) {
        _align = value;
    }

    /**
     * Sets the bacground color for the cell.
     */
    public void setBackgroundColor(String color) {
        _bgColor = color;
    }

    /**
     * This method sets the height of the table cell. Overerides table column height.
     * @param cellHeight the height of the cell
     */
    public void setCellHeight(int cellHeight) {
        _cellHeight = cellHeight;
    }

    /**
     * This method sets the height of the table cell. Overerides table column height.
     * @param cellHeight the height of the cell
     * @param sizeOption SIZE_PIXELS or SIZE_PERCENT
     */
    public void setCellHeight(int cellHeight, int sizeOption) {
        _cellHeight = cellHeight;
        _cellHeightSizeOption = sizeOption;
    }

    /**
     * This method sets the height size option for the cell
     * @param sizeOption SIZE_PIXELS or SIZE_PERCENT
     */
    public void setCellHeightSizeOption(int sizeOption) {
        _cellHeightSizeOption = sizeOption;
    }

    /**
     * This method sets the width of the table cell. Overerides table column width.
     * @param cellWidth the width of the cell
     */
    public void setCellWidth(int cellWidth) {
        _cellWidth = cellWidth;
    }

    /**
     * This method sets the width of the table cell. Overerides table column width.
     * @param cellWidth the width of the cell
     * @param sizeOption SIZE_PIXELS or SIZE_PERCENT
     */
    public void setCellWidth(int cellWidth, int sizeOption) {
        _cellWidth = cellWidth;
        _cellWidthSizeOption = sizeOption;
    }

    /**
     * This method sets the width size option for the cell
     * @param sizeOption SIZE_PIXELS or SIZE_PERCENT
     */
    public void setCellWidthSizeOption(int sizeOption) {
        _cellHeightSizeOption = sizeOption;
    }

    /**
     * This method sets the column span for a table cell
     * @param colSpan the number of columns to span
     */
    public void setColumnSpan(int colSpan) {
        _colSpan = colSpan;
    }

    /**
     * Use this method to set the javascript that will be executed when the user clicks on one of the components in the link.
     */
    public void setOnClick(String onClick) {
        _onClick = onClick;
    }

    /**
     * Use this method to get the javascript that will be executed when mouse clicks on the components with either mouse button
     */
    public void setOnMouseDown(String onMouseDown) {
        _onMouseDown = onMouseDown;
    }

    /**
     * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
     */
    public void setOnMouseOut(String onMouseOut) {
        _onMouseOut = onMouseOut;
    }

    /**
     * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
     */
    public void setOnMouseOver(String onMouseOver) {
        _onMouseOver = onMouseOver;
    }

    /**
     * Use this method to get the javascript that will be executed when releases a mouse button while the mouse is over the component
     */
    public void setOnMouseUp(String onMouseUp) {
        _onMouseUp = onMouseUp;
    }

    /**
     * This method sets the column span for a table cell
     * @param colSpan the number of columns to span
     */
    public void setRowSpan(int rowSpan) {
        _rowSpan = rowSpan;
    }

    /**
     * This method was created in VisualAge.
     * @param style com.salmonllc.html.HtmlStyle
     */
    public void setStyle(HtmlStyle style) {
        _style = style;
    }

    /**
     * Sets the vertical align property for the page. Valid values are HtmlTable.VALIGN_BASELINE,HtmlTable.VALIGN_BOTTOM,HtmlTable.VALIGN_MIDDLE,HtmlTable.VALIGN_TOP,HtmlTable.VALIGN_NONE
     */

    public void setVertAlign(String value) {
        _vAlign = value;
    }

    /**
     * Sets whether or not the text in the cell should wrap if there isn't enough room to display it.
     */
    public void setWrap(boolean value) {
        _wrap = value;
    }
}
