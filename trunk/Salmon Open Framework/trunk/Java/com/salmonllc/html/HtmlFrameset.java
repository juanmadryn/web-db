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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlFrameset.java $
//$Author: Srufle $ 
//$Revision: 14 $ 
//$Modtime: 4/15/03 2:24p $ 
/////////////////////////
 
import java.util.Vector;

import com.salmonllc.html.events.FramesetListener;
import com.salmonllc.html.events.PageEvent;

/**
 * This class implements an Html Frameset. A frameset divides the browser screen into multiple regions. Each region contains a frame.
 * @see HtmlFrame
 */
public abstract class HtmlFrameset extends HtmlPageBase {
	public static final int TYPE_VERTICAL = 0 ;
	public static final int TYPE_HORIZONTAL = 1;

	private int _type = TYPE_VERTICAL;
	private int _borderWidth = 3;
	private String _borderColor = "";
	private boolean _border = true;

	private Vector _frames = new Vector();
	private Vector _listeners;

	private String _title;
	
/**
 * This method adds a new frame to the frameset
 * @param f The frame to add.
 */
public void addFrame(HtmlFrame f) {
	_frames.addElement(f);
}
/**
 * Adds a new listerner to this page to handle custom page events.
 */
public void addFramesetListener(FramesetListener p) {
	if (_listeners == null) 
		_listeners = new Vector();

	for (int i = 0; i < _listeners.size(); i ++) {
		if ( ((FramesetListener) _listeners.elementAt(i)) == p)
			return;
	}

	_listeners.addElement(p);
}
public void generateHTML(java.io.PrintWriter p) throws Exception {
	notifyListeners();

	String title = _title;
	if (title == null)
		title = getApplicationName() + "_" + getPageName();
	
	p.println("<HTML><HEAD><TITLE>" + title + "</TITLE>");
	p.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">");
	p.println("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">");
	p.println("</HEAD>");
	String frame = "<FRAMESET ";
	if (_type == TYPE_VERTICAL) 
		frame += " ROWS=\"";
	else
		frame += " COLS=\"";

	for (int i = 0; i < _frames.size(); i++) {
		HtmlFrame f = (HtmlFrame) _frames.elementAt(i);
		if (i > 0)
			frame += ",";
			
		if (f.getSize() <= 0)
			frame += "*";
		else {
			frame += f.getSize();
			if (f.getSizeMethod() == HtmlFrame.SIZE_PERCENT)
				frame += "%";
		}		
	}

	frame += "\"";

	if (getBorderColor() != null)
		if (! getBorderColor().equals(""))
			frame += " BORDERCOLOR=\"" + getBorderColor() + "\"";

	if (getBorder()) {
		frame += " FRAMEBORDER=\"YES\"";
		frame += " FRAMESPACING=\"" + getBorderWidth() + "\"";
		if (getBorderWidth() >= 0)
			frame += " BORDER=\"" + getBorderWidth() + "\"";
	}	
	else
		frame += " FRAMEBORDER=\"NO\"";
	

			
	frame += ">";
	
	p.println(frame);

	for (int i = 0; i < _frames.size(); i++) {
		frame = " <FRAME";
		HtmlFrame f = (HtmlFrame) _frames.elementAt(i);
		frame += " NAME=\"" + f.getName() + "\"";
		frame += " SRC=\"" + f.getURL() + "\"";
		if (! f.getResize())
			frame += " NORESIZE";

		if (f.getScrollBars() == HtmlFrame.SCROLLBARS_NO)
			frame += " SCROLLING=\"NO\"";
		else if (f.getScrollBars() == HtmlFrame.SCROLLBARS_YES)
			frame += " SCROLLING=\"YES\"";
		else
			frame += " SCROLLING=\"AUTO\"";
		frame += ">";
		p.println(frame);	
	}	

	p.println("</FRAMESET>");
	p.println("</HTML>");
	
		
}
/**
 * This method gets whether or not the frames have borders
 */
public boolean getBorder() {
	return _border;
}
/**
 * This method gets the color of the frame borders.
 */
public String getBorderColor() {
	return _borderColor;
}
/**
 * This method gets the width of the frame borders in pixels..
 */
public int getBorderWidth() {
	return _borderWidth;
}
/**
 * This method gets the title of the page displayed in the browser.
 */
public String getTitle() {
	return _title;
}
/**
 * This method gets the type of frameset. Valid values are TYPE_VERTICAL and TYPE_HORIZONTAL
 */
public int getType() {
	return _type;
}
/**
 * This method should be overridden in subclasses of this class. Use this method to set the initial properties for the frameset.
 */
public abstract void initialize() ;
private boolean notifyListeners() throws Exception {
	if (_listeners == null)
		return true;
		
	PageEvent p = new PageEvent(this);

	for (int i = 0; i < _listeners.size(); i ++) {
		((FramesetListener) _listeners.elementAt(i)).pageRequested(p);
		if (! p.getContinueProcessing())
			return false;
	}

	return true;
}
public void processParms(java.io.PrintWriter p, boolean iFrame) {
}
/**
 * This method removes a listener from the list of listeners that will be notified when a page event is fired.
 */
public void removeFramesetListener(FramesetListener p) {
	if (_listeners == null) 
		return;

	for (int i = 0; i < _listeners.size(); i ++) {
		if ( ((FramesetListener) _listeners.elementAt(i)) == p) {
			_listeners.removeElementAt(i);
			return;
		}	
	}
}
/**
 * This method sets whether or not the frames have borders
 */
public void setBorder(boolean border) {
	_border = border;
}
/**
 * This method sets the color of the frame borders.
 */
public void setBorderColor(String borderColor) {
	_borderColor = borderColor;
}
/**
 * This method sets the width of the frame borders in pixels..
 */
public void setBorderWidth(int width) {
	_borderWidth = width;
}
/**
 * This method sets the title of the page displayed in the browser.
 */
public void setTitle(String title) {
	_title = title;
}
/**
 * This method sets the type of frameset. Valid values are TYPE_VERTICAL and TYPE_HORIZONTAL
 */
public void setType(int type) {
	_type = type;
}
}
