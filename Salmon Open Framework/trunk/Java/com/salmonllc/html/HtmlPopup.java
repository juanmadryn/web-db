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

import java.util.Enumeration;

/**
 * HtmlPopup is a class which gives a developer a way to generate a div/layer component of a HTML page that can be used as a popup.
 */
public class HtmlPopup extends HtmlContainer {
	HtmlStyle _hsPopup;
	HtmlScript _hscPopup;
	HtmlLayer _hlPopup;
	HtmlComponent _hcComp;
	int _x=0;
	int _y=0;
	int _xvisibleoffset=10;
	int _yvisibleoffset=10;
	int _xanchoroffset=0;
	int _yanchoroffset=0;
	int _width=0;
	boolean _fixedLocation=false;
	boolean _offsetByWidth=true;
	boolean _offsetByHeight=true;
	boolean _offsetByParent=false;
	String _hideCondition=null;
	String _visibleScript=null;
	String _hideScript=null;
	int _cellpadding=0;
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param x int 	The x coordinate of the popup.
 * @param y int 	The y coordinate of the popup.
 * @param width int 	The width of the div/layer in pixels.
 * @param xvisibleoffset int 	The number of horizontal pixels allowed to be away from the Popup in order for it to stay visible.
 * @param yvisibleoffset int	The number of vertical pixels allowed to be away from the Popup in order for it to stay visible.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 * @param fixed boolean A boolean indicating whether the popup is to have a fixed location.
 */
public HtmlPopup(String name, int x, int y, int width, int xvisibleoffset, int yvisibleoffset, HtmlPage p,boolean fixed) {
	super(name, p);
	_x=x;
	_y=y;
	_width=width;
	_xvisibleoffset=xvisibleoffset;
	_yvisibleoffset=yvisibleoffset;
	_fixedLocation=fixed;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param x int 	The x coordinate of the popup.
 * @param y int 	The y coordinate of the popup.
 * @param width int 	The width of the div/layer in pixels.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 */
public HtmlPopup(String name, int x, int y, int width, HtmlPage p) {
	super(name, p);
	_x=x;
	_y=y;
	_width=width;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param x int 	The x coordinate of the popup.
 * @param y int 	The y coordinate of the popup.
 * @param width int 	The width of the div/layer in pixels.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 * @param fixed boolean A boolean indicating whether the popup is to have a fixed location.
 */
public HtmlPopup(String name, int x, int y, int width, HtmlPage p,boolean fixed) {
	super(name, p);
	_x=x;
	_y=y;
	_width=width;
	_fixedLocation=fixed;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param width int 	The width of the div/layer in pixels.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 */
public HtmlPopup(String name,int width,HtmlPage p) {
	super(name,p);
	_width=width;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param width int 	The width of the div/layer in pixels.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 */
public HtmlPopup(String name, HtmlComponent comp,int width,HtmlPage p) {
	super(name,p);
	_hcComp=comp;
	_width=width;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param width int 	The width of the div/layer in pixels.
 * @param offsetByWidth boolean 	A flag to indicate to display the Popup to the right of comp.
 * @param offsetByHeight boolean 	A flag to indicate to display the Popup below comp.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 */
public HtmlPopup(String name, HtmlComponent comp,int width,boolean offsetByWidth, boolean offsetByHeight, HtmlPage p) {
	super(name,p);
	_hcComp=comp;
	_width=width;
	_offsetByHeight=offsetByHeight;
	_offsetByWidth=offsetByWidth;
	createPopup();
}
/**
 * HtmlPopup constructor comment.
 * @param name java.lang.String 	Name of the div/layer tag generated.
 * @param comp com.salmonllc.html.HtmlComponent 	The component which the div/layer is linked to.
 * @param width int 	The width of the div/layer in pixels.
 * @param offsetByWidth boolean 	A flag to indicate to display the Popup to the right of comp.
 * @param offsetByHeight boolean 	A flag to indicate to display the Popup below comp.
 * @param offsetByParent boolean 	A flag to indicate to display the Popup relative to comp parent component.
 * @param p com.salmonllc.html.HtmlPage The page that this popup is part of.
 */
public HtmlPopup(String name, HtmlComponent comp,int width,boolean offsetByWidth, boolean offsetByHeight, boolean offsetByParent, HtmlPage p) {
	super(name,p);
	_hcComp=comp;
	_width=width;
	_offsetByHeight=offsetByHeight;
	_offsetByWidth=offsetByWidth;
	_offsetByParent=offsetByParent;
	createPopup();
}
/**
 * Adds a new html component to the popup
 * @param comp com.salmonllc.html.HtmlComponent
 */
public void add(HtmlComponent comp) {
		
	_hlPopup.add(comp);
}
/**
 * A method to initialize the popup.
 * Creation date: (3/27/01 3:58:12 PM)
 */
private void createPopup() {
	_hsPopup=new HtmlStyle(getName(),"#","",getPage());
	_hsPopup.setStyle("position:absolute;top:"+_y+"px;left:"+_x+"px;width:"+_width+"px;visibility:hidden;z-index:0;");
	_hlPopup=new HtmlLayer(getName(),_hsPopup,getPage());
    _hscPopup=new HtmlScript(getDisplayPopupScript(),"JavaScript1.2",getPage());
	}
public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
	if (getPage().getBrowserType()!=HtmlPageBase.BROWSER_MICROSOFT || getPage().getBrowserVersion()<4)
	  return;
	if (! _visible )
		return;
		
	_hsPopup.generateHTML(p,rowNo);
	_hscPopup.generateHTML(p,rowNo);
	_hlPopup.generateHTML(p,rowNo);
	
	
	
}
/**
 * Returns the cell padding specified by the developer. This is usually the cell padding of a table the popup appears within
 * Creation date: (3/27/01 1:56:08 PM)
 * @return int The cellpadding specified with setCellPadding.
 */
public int getCellPadding() {
	return _cellpadding;
}
/**
 * This method will return a list of all components in the container.
 * @return Enumeration An enumeration of the components within the popup.
 */
public Enumeration getComponents()
{
	return _hlPopup.getComponents();
}
private String getDisplayPopupScript() {
  String sHideCondition="";
  if (_hideCondition!=null && !_hideCondition.trim().equals(""))
    sHideCondition="if ("+_hideCondition+") {\n";
	
  String sScript="function "+_hlPopup.getName()+"getPixelTop(htmlElement) {\n";
  sScript+="if (htmlElement.offsetParent==null)\n"; 
  sScript+="return htmlElement.offsetTop;\n"; 
  sScript+="return htmlElement.offsetTop+"+_hlPopup.getName()+"getPixelTop(htmlElement.offsetParent)\n"; 
  sScript+="}\n";
  
  sScript+="function "+_hlPopup.getName()+"getPixelLeft(htmlElement) {\n";
  sScript+="if (htmlElement.offsetParent==null)\n"; 
  sScript+="return htmlElement.offsetLeft;\n"; 
  sScript+="return htmlElement.offsetLeft+"+_hlPopup.getName()+"getPixelLeft(htmlElement.offsetParent)\n"; 
  sScript+="}\n";
	
  sScript+="function "+_hlPopup.getName()+"Popup() {\n";
  sScript+="if ("+_hlPopup.getName()+".style.visibility=='hidden') {\n";
  if (!_fixedLocation) {
	if (_hcComp==null) {
  		sScript+=_hlPopup.getName()+".style.top=getMouseY()+document.body.scrollTop;\n";
  		sScript+=_hlPopup.getName()+".style.left=getMouseX()+document.body.scrollLeft;\n";
    }
	else {
  		sScript+=_hlPopup.getName()+".style.top="+_hlPopup.getName()+"getPixelTop("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")"+(_offsetByHeight?"+"+_hcComp.getFullName()+".offsetHeight":"")+";\n";
  		sScript+=_hlPopup.getName()+".style.left="+_hlPopup.getName()+"getPixelLeft("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")"+(_offsetByWidth?"+"+_hcComp.getFullName()+".offsetWidth":"")+";\n";
	}
  }
  else {
  	sScript+=_hlPopup.getName()+".style.top="+_y+";\n";
  	sScript+=_hlPopup.getName()+".style.left="+_x+";\n";
  }
//  sScript+=_hlPopup.getName()+".style.visibility='visible';\n";	 //do not uncomment this line
  sScript+="}\n";
  sScript+="if ("+_hlPopup.getName()+".style.visibility=='visible') {\n";
  if (_hcComp==null) {
  	sScript+="if ((getMouseX()+document.body.scrollLeft<"+_hlPopup.getName()+".offsetLeft-"+_xvisibleoffset+" || getMouseX()+document.body.scrollLeft>"+_hlPopup.getName()+".offsetLeft+"+_hlPopup.getName()+".offsetWidth+"+_xvisibleoffset+") || (getMouseY()+document.body.scrollTop<"+_hlPopup.getName()+".offsetTop-"+_yvisibleoffset+" || getMouseY()+document.body.scrollTop>"+_hlPopup.getName()+".offsetTop+"+_hlPopup.getName()+".offsetHeight+"+_yvisibleoffset+")) {\n";
  }
  else {
  	sScript+="if ((getMouseX()+document.body.scrollLeft<"+_hlPopup.getName()+".offsetLeft-"+_hcComp.getFullName()+".offsetWidth || getMouseX()+document.body.scrollLeft>"+_hlPopup.getName()+".offsetLeft+"+_hlPopup.getName()+".offsetWidth+"+_hcComp.getFullName()+".offsetWidth) || (getMouseY()+document.body.scrollTop<"+_hlPopup.getName()+".offsetTop-"+_hcComp.getFullName()+".offsetHeight || getMouseY()+document.body.scrollTop>"+_hlPopup.getName()+".offsetTop+"+_hlPopup.getName()+".offsetHeight+"+_hcComp.getFullName()+".offsetHeight)) {\n";
  	sScript+=sHideCondition+_hlPopup.getName()+".style.visibility='hidden';\n";
  	if (_hideScript!=null)
  	  sScript+=_hideScript;
  	if (!sHideCondition.equals(""))
  	  sScript+="}\n";
	sScript+="}\n";
	if (_offsetByWidth) {
  	  sScript+="if (getMouseX()+document.body.scrollLeft<"+_hlPopup.getName()+".offsetLeft && (getMouseY()+document.body.scrollTop<"+_hlPopup.getName()+"getPixelTop("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+") || getMouseY()+document.body.scrollTop>"+_hlPopup.getName()+"getPixelTop("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")+"+_hcComp.getFullName()+".offsetHeight+"+(_cellpadding*2)+")) {\n";
  	  sScript+=sHideCondition+_hlPopup.getName()+".style.visibility='hidden';\n";
  	  if (_hideScript!=null)
  	    sScript+=_hideScript;
  	  if (!sHideCondition.equals(""))
  	    sScript+="}\n";
	  sScript+="}\n";
	 }		
	if (_offsetByHeight) {
  		sScript+="if ((getMouseX()+document.body.scrollLeft<"+_hlPopup.getName()+"getPixelLeft("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+") || getMouseX()+document.body.scrollLeft>"+_hlPopup.getName()+"getPixelLeft("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")+"+_hcComp.getFullName()+".offsetWidth) && getMouseY()+document.body.scrollTop<"+_hlPopup.getName()+".offsetTop) {\n";
  		sScript+=sHideCondition+_hlPopup.getName()+".style.visibility='hidden';\n";
  	    if (_hideScript!=null)
  	      sScript+=_hideScript;
  	    if (!sHideCondition.equals(""))
  	      sScript+="}\n";
		sScript+="}\n";
	 }	
  }	
  sScript+="}\n";
  sScript+="}\n";
  sScript+="function "+_hlPopup.getName()+"ShowPopup() {\n";
  if (!_fixedLocation) {
	if (_hcComp==null) {
  		sScript+=_hlPopup.getName()+".style.top=getMouseY()+document.body.scrollTop;";
  		sScript+=_hlPopup.getName()+".style.left=getMouseX()+document.body.scrollLeft;";
	}
	else {
  		sScript+=_hlPopup.getName()+".style.top="+_hlPopup.getName()+"getPixelTop("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")"+(_offsetByHeight?"+"+_hcComp.getFullName()+".offsetHeight":"")+";";
  		sScript+=_hlPopup.getName()+".style.left="+_hlPopup.getName()+"getPixelLeft("+_hcComp.getFullName()+(_offsetByParent?".offsetParent":"")+")"+(_offsetByWidth?"+"+_hcComp.getFullName()+".offsetWidth":"")+";";
	}
  }
  else {
  	sScript+=_hlPopup.getName()+".style.top="+_y+";";
  	sScript+=_hlPopup.getName()+".style.left="+_x+";";
  }
  sScript+=_hlPopup.getName()+".style.visibility='visible';";
  if (_visibleScript!=null)
    sScript+=_visibleScript;
  sScript+="}\n";
  return sScript;
}
/**
 * Returns whether the popup displays in a fixed location.
 * Creation date: (3/27/01 1:56:08 PM)
 * @return boolean Indicates whether the popup displays in a fixed location or not.
 */
public boolean getFixedLocation() {
	return _fixedLocation;
}
/**
 * Returns the Javascript condition under which the Popup hides.
 * Creation date: (3/27/01 1:56:08 PM)
 * @return String The Javascript representing the Condition under which the popup hides.
 */
public String getHideCondition() {
	return _hideCondition;
}
/**
 * Returns the Javascript used after hiding the Popup. Specified by user using setHideScript
 * Creation date: (3/27/01 1:56:08 PM)
 * @return String The Javascript used after hiding the popup.
 */
public String getHideScript() {
	return _hideScript;
}
/**
 * Returns the Javascript call to be made in javascript to decide to leaveup/hide popup.
 * Creation date: (3/27/01 11:14:44 AM)
 * @return java.lang.String The Javascript call used to leaveup/hide the popup.
 */
public String getPopupScript() {
	return _hlPopup.getName()+"Popup();";
}
/**
 * Returns the Javascript call to be made in javascript to show popup.
 * Creation date: (3/27/01 11:14:44 AM)
 * @return java.lang.String The Javascript call used to show the popup.
 */
public String getPopupShowScript() {
  return "setTimeout('"+_hlPopup.getName()+"ShowPopup();',100);";
}
/**
 * Returns the Javascript used after showing the Popup. Specified by user with setVisibleScript.
 * Creation date: (3/27/01 1:56:08 PM)
 * @return String The Javascript used to show the popup.
 */
public String getVisibleScript() {
	return _visibleScript;
}
/**
 * Sets the cellpadding used in generating the scripts representing the cell padding of the table containing the popup.
 * Creation date: (3/27/01 1:56:08 PM)
 * @param iCellPadding The cellpadding of the table containing the popup.
 */
public void setCellPadding(int iCellPadding) {
	_cellpadding=iCellPadding;
}
/**
 * Sets whether the popup is in a fixed location or not.
 * Creation date: (3/27/01 1:56:08 PM)
 * @param bFixed boolean Indicates whether the popup is in a fixed location or not.
 */
public void setFixedLocation(boolean bFixed) {
	_fixedLocation=bFixed;
}
/**
 * Specifies the javascript condition to use to hide the popup.
 * Creation date: (3/27/01 1:56:08 PM)
 * @param sHideCondition string The condition used to hide the popup.
 */
public void setHideCondition(String sHideConditon) {
	_hideCondition=sHideConditon;
	_hscPopup.setScript(getDisplayPopupScript());
}
/**
 * Specifies the javascript to be executed after hiding the popup.
 * Creation date: (3/27/01 1:56:08 PM)
 * @param sHideCondition string The javascipt to be executed after hiding popup.
 */
public void setHideScript(String sHideScript) {
	_hideScript=sHideScript;
	_hscPopup.setScript(getDisplayPopupScript());
}
/**
 * Specifies the javascript to be executed after showing the popup.
 * Creation date: (3/27/01 1:56:08 PM)
 * @param sHideCondition string The javascipt to be executed after showing popup.
 */
public void setVisibleScript(String sVisibleScript) {
	_visibleScript=sVisibleScript;
	_hscPopup.setScript(getDisplayPopupScript());
}
}
