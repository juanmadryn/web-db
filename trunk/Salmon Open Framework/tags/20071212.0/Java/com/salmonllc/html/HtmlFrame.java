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
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlFrame.java $
//$Author: Dan $ 
//$Revision: 8 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
 
/**
 * This class is used in conjunction with HtmlFrameset. A frameset is componed of two or more HtmlFrames.
 * @see HtmlFrameset
 */
public class HtmlFrame {
	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;

	public static final int SCROLLBARS_YES = 0;
	public static final int SCROLLBARS_NO = 1;
	public static final int SCROLLBARS_AUTO = 2;

	private String _name = "";
	private boolean _resize = true;
	private int _scroll = SCROLLBARS_AUTO;
	private String _url = "";
	private int _size = -1;
	private int _sizeMethod = SIZE_PIXELS;
	
/**
 * Creates a new HtmlFrame.
 */
public HtmlFrame() {
	super();
}
/**
 * This method gets the name of the frame
 */
public String getName() {
	return _name;
}
/**
 * This method gets whether or not the frame is resizeable.
 */
public boolean getResize() {
	return _resize;
}
/**
 * This method gets whether or not the frame has scroll bars. Valid values are SCROLLBARS_YES,SCROLLBARS_NO and SCROLLBARS_AUTO.
 */
public int getScrollBars() {
	return _scroll;
}
/**
 * This method gets the size of the frame. A value <= 0 will cause the frame to use all available space.
 */
public int getSize() {
	return _size;
}
/**
 * This method gets the size method for the frame. Valid values are SIZE_PIXELS or SIZE_PERCENT.
 */
public int getSizeMethod() {
	return _sizeMethod;
}
/**
 * This method gets the URL of the source page of the frame.
 */
public String getURL() {
	return _url;
}
/**
 * This method sets the name of the frame
 */
public void setName(String value) {
	_name = value;
}
/**
 * This method sets whether or not the frame is resizeable.
 */
public void setResize(boolean value) {
	_resize = value;
}
/**
 * This method sets whether or not the frame has scroll bars. Valid values are SCROLLBARS_YES,SCROLLBARS_NO and SCROLLBARS_AUTO.
 */
public void setScrollBars(int value) {
	_scroll = value;
}
/**
 * This method sets the size of the frame. A value <= 0 will cause the frame to use all available space.
 */
public void setSize(int size) {
	_size = size;
}
/**
 * This method sets the size method for the frame. Valid values are SIZE_PIXELS or SIZE_PERCENT.
 */
public void setSizeMethod(int value) {
	_sizeMethod = value;
}
/**
 * This method sets the URL of the source page of the frame.
 */
public void setURL(String value) {
	_url = value;
}
}
