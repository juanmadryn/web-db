//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.html;

import com.salmonllc.jsp.Constants;
import com.salmonllc.jsp.JspDataTable;

/**
 * A utility class that can be used by other components to generate various useful javascripts. Currently handles various scripts for popup windows
 */
public class HtmlScriptGenerator {

	HtmlPage _page;

	public HtmlScriptGenerator(HtmlPage page) {
		_page = page;
	}

	public String generatePopupDivScript(String url, int popupWidth, int popupHeight, HtmlComponent relativeComponent) {
		return generatePopupDivScript(url, Constants.POPUP_POSITION_RELATIVE, -1, -1, popupWidth, popupHeight, false, null, "no", relativeComponent, -1);
	}

	public String generatePopupDivScript(String url, String popupPosition, int popupTop, int popupLeft, int popupWidth, int popupHeight, boolean useModal, String borderStyle, String scrolling, HtmlComponent relativeComponent, int row) {

		if (!_page.isScriptAdded("RelativeDivPositionScript"))
			_page.addScript("RelativeDivPositionScript", generateRelativeDivPositionScript());

		if (!_page.isScriptAdded("DivPositionScript"))
			_page.addScript("DivPositionScript", generateDivPositionScripts());

		if (!_page.isScriptAdded("OpenDivScript"))
			_page.addScript("OpenDivScript", generateOpenDivScript());

		if (!_page.isHtmlAdded("PopupDiv"))
			_page.addHtml("PopupDiv", generatePopupDivHtml());

		StringBuffer openPopupScript = new StringBuffer();
		String tBodyId=null;
		if (relativeComponent != null) {
			openPopupScript.append("relativeComp=");
			openPopupScript.append(relativeComponent.getFormString());
			openPopupScript.append(relativeComponent.getFullName());
			openPopupScript.append(((row == -1) ? "" : ("_" + row)));
			openPopupScript.append(";");
			HtmlComponent parent=relativeComponent.getParent();
			while (parent != null) {
				if (parent instanceof JspDataTable || parent instanceof HtmlDataTable) {
					tBodyId=parent.getFullName() + "-tbody";
					break;
				}	
				parent=parent.getParent();	
			}	
		}
		openPopupScript.append("OpenPopupDiv(" + (relativeComponent != null ? "relativeComp" : "''") + "," + url + ",'" + popupPosition + "'" + "," + popupTop + "," + popupLeft + "," + popupWidth + "," + popupHeight + "," + useModal + ","
				+ (borderStyle == null ? "null" : "'" + borderStyle + "'") + "," + (scrolling == null ? "null" : "'" + scrolling + "'") + ",'" + tBodyId + "');");
		return openPopupScript.toString();
	}

	private String generateOpenDivScript() {
		StringBuffer openPopupScript;
		openPopupScript = new StringBuffer();
		openPopupScript.append(generateCheckModalScript());
		openPopupScript.append("var windowPopupDiv=null;\n\n");
		openPopupScript.append("function OpenPopupDiv(relativeComp,url,position,popupTop,popupLeft,popupWidth,popupHeight,modal, borderStyle, scrolling, tBodyId){\n");
		openPopupScript.append("  windowPopupDiv = document.getElementById(\"pseudoWindow\");\n");
		openPopupScript.append("  if (position=='" + Constants.POPUP_POSITION_RELATIVE + "') {\n");
		openPopupScript.append("     popupLeft=getDivRelativeLeftPosition(relativeComp,window,popupWidth,popupHeight); \n");
		openPopupScript.append("     popupTop=getDivRelativeTopPosition(relativeComp,window,popupWidth,popupHeight,tBodyId); \n");
		openPopupScript.append("  } else if (position =='" + Constants.POPUP_POSITION_CENTER + "') {\n");
		openPopupScript.append("     popupLeft=getDivCenterLeftPosition(popupWidth,popupHeight);\n ");
		openPopupScript.append("     popupTop=getDivCenterTopPosition(popupWidth,popupHeight);\n ");
		openPopupScript.append("}\n");
		openPopupScript.append("  if (windowPopupDiv.style.display==\"none\") {\n");
		openPopupScript.append("     if (!modal) {\n");
		openPopupScript.append("         nonModalDiv=windowPopupDiv;\n");
		openPopupScript.append("         modalDiv=null;\n");
		openPopupScript.append("      }\n");
		openPopupScript.append("      else {\n");
		openPopupScript.append("         nonModalDiv=null;\n");
		openPopupScript.append("         modalDiv=windowPopupDiv;\n");
		openPopupScript.append("      }\n");
		openPopupScript.append("      var contentFrameScroll=document.getElementById(\"contentFrameScroll\");\n");
		openPopupScript.append("      var contentFrameNoScroll=document.getElementById(\"contentFrameNoScroll\");\n");
		openPopupScript.append("      var contentFrame=contentFrameNoScroll;\n");
		openPopupScript.append("      contentFrameScroll.style.display=\"none\";\n");
		openPopupScript.append("      contentFrameNoScroll.style.display=\"none\";\n");
		openPopupScript.append("      if (scrolling!=null && scrolling=='yes' || scrolling=='auto') contentFrame=contentFrameScroll;\n");
		openPopupScript.append("      contentFrame.src=url;\n");
		openPopupScript.append("      contentFrame.width=popupWidth;\n");
		openPopupScript.append("      contentFrame.height=popupHeight;\n");
		openPopupScript.append("      windowPopupDiv.style.height=popupHeight;\n");
		openPopupScript.append("      windowPopupDiv.style.left=popupLeft;\n");
		openPopupScript.append("      windowPopupDiv.style.width=popupWidth;\n");
		openPopupScript.append("      windowPopupDiv.style.top=popupTop;\n");
		openPopupScript.append("      if (borderStyle != null) 	windowPopupDiv.style.border=borderStyle;\n");
		openPopupScript.append("      windowPopupDiv.style.display=\"block\";\n");
		openPopupScript.append("      contentFrame.style.display=\"block\";\n");
		openPopupScript.append("  }\n");
		openPopupScript.append("}\n\n");

		openPopupScript.append("  function ClosePopupDiv() {");
		openPopupScript.append("    windowPopupDiv = document.getElementById(\"pseudoWindow\");\n");
		openPopupScript.append("    var contentFrame1=document.getElementById(\"contentFrameScroll\");\n");
		openPopupScript.append("    var contentFrame2=document.getElementById(\"contentFrameNoScroll\");\n");
		openPopupScript.append("    contentFrame1.src=\"\";\n");
		openPopupScript.append("    contentFrame2.src=\"\";\n");
		openPopupScript.append("    windowPopupDiv.style.display=\"none\";\n");
		openPopupScript.append("}\n\n");

		return openPopupScript.toString();
	}

	private String generatePopupDivHtml() {
		StringBuffer sb = new StringBuffer();

		sb.append("<div id=\"pseudoWindow\" style=\"position:absolute; top:100px; left:100px; width:100px; height:100px; border:1px solid black;  background-color:#ffffff; display:none;\">");
		sb.append("<iframe id=\"contentFrameNoScroll\" src=\"\" frameborder=\"0\" vspace=\"0\" hspace=\"0\" marginwidth=\"0\" marginHeight=\"0\" scrolling=\"no\"></iframe>");
		sb.append("<iframe id=\"contentFrameScroll\" src=\"\" frameborder=\"0\" vspace=\"0\" hspace=\"0\" marginwidth=\"0\" marginHeight=\"0\" scrolling=\"auto\"></iframe>");
		sb.append("</div>");
		return sb.toString();
	}
	/**
	 * @see this.generatePopupScript
	 */
	public String generateOpenPopupScript(String url, int popupWidth, int popupHeight, HtmlComponent relativeComponent, boolean useModal) {
		return generateOpenPopupScript(url, Constants.POPUP_POSITION_RELATIVE, -1, -1, popupWidth, popupHeight, useModal, null, relativeComponent, -1);
	}

	/**
	 * @see this.generatePopupScript
	 */
	public String generateOpenPopupScript(String url, int popupWidth, int popupHeight, boolean useModal) {
		return generateOpenPopupScript(url, Constants.POPUP_POSITION_CENTER, -1, -1, popupWidth, popupHeight, useModal, null, null, -1);
	}

	/**
	 * Generates the necessary javascript to open a popup window for the given
	 * URL.
	 * 
	 * @param url
	 *            url for the popup.
	 * @param popupPosition
	 *            position in which the popup will be opened (relative, center
	 *            or custom).
	 * @param popupTop
	 *            top coordinate for the popup (Applies only to custom
	 *            positions).
	 * @param popupLeft
	 *            left coordinate for the popup (Applies only to custom
	 *            positions).
	 * @param popupWidth
	 *            popup width
	 * @param popupHeight
	 *            popup heigth
	 * @param useModal
	 *            indicates if the popup should be opened in a modal or non
	 *            modal fashion.
	 * @param relativeComponent
	 *            relative component to position the popup (must be provided for
	 *            the relative position).
	 * @param row
	 *            row number of the relative component (-1 if it isn't nested
	 *            inside a datatable).
	 * @param popupAttibutes
	 *            other then size and position, a comma seperated list of popup
	 *            attributes (see javascript window.open() for more info)
	 * @author Claudio Pi - claudiopi@users.sourceforge.net
	 * @return
	 */
	public String generateOpenPopupScript(String url, String popupPosition, int popupTop, int popupLeft, int popupWidth, int popupHeight, boolean useModal, String popupAttibutes, HtmlComponent relativeComponent, int row) {

		StringBuffer openPopupScript = new StringBuffer();

		if (!_page.isScriptAdded("OpenPopupScript"))
			_page.addScript("OpenPopupScript", generateOpenPopupScript());

		if (!_page.isScriptAdded("RelativePositionScript"))
			_page.addScript("RelativePositionScript", generateRelativePositionScript());

		if (!_page.isScriptAdded("CenterPositionScript"))
			_page.addScript("CenterPositionScript", generateCenterPositionScript());

		if (relativeComponent != null) {
			openPopupScript.append("relativeComp=");
			openPopupScript.append(relativeComponent.getFormString());
			openPopupScript.append(relativeComponent.getFullName());
			openPopupScript.append(((row == -1) ? "" : ("_" + row)));
			openPopupScript.append(";");
		}
		openPopupScript.append("OpenPopupWindow(" + (relativeComponent != null ? "relativeComp" : "''") + "," + url + ",'" + popupPosition + "'" + "," + popupTop + "," + popupLeft + "," + popupWidth + "," + popupHeight + "," + useModal + ","
				+ (popupAttibutes == null ? "null" : "'" + popupAttibutes + "'") + ");");

		return openPopupScript.toString();
	}

	private String generateOpenPopupScript() {
		StringBuffer openPopupScript;
		openPopupScript = new StringBuffer();

		openPopupScript.append(generateCheckModalScript());
		openPopupScript.append("function ");
		openPopupScript.append("OpenPopupWindow(relativeComp,url,position,popupTop,popupLeft,popupWidth,popupHeight,modal, popupAtt){\n");
		openPopupScript.append("  if (position=='" + Constants.POPUP_POSITION_RELATIVE + "') {\n");
		openPopupScript.append("     popupLeft=getPopupRelativeLeftPosition(relativeComp,window,popupWidth,popupHeight); \n");
		openPopupScript.append("     popupTop=getPopupRelativeTopPosition(relativeComp,window,popupWidth,popupHeight); \n");
		openPopupScript.append("  } else if (position =='" + Constants.POPUP_POSITION_CENTER + "') {\n");
		openPopupScript.append("     popupLeft=getPopupCenterLeftPosition(popupWidth,popupHeight);\n ");
		openPopupScript.append("     popupTop=getPopupCenterTopPosition(popupWidth,popupHeight);\n ");
		openPopupScript.append("}\n");
		openPopupScript.append("  var windname = \"ModPopWin\" + (new Date()).getSeconds().toString();\n");
		openPopupScript.append("  var attrib = 'status=yes,menubar=no,resizable,dependent=yes,scrollbars=yes,titlebar=no';\n");
		openPopupScript.append("  if (popupAtt != null)\n");
		openPopupScript.append("      attrib=popupAtt;\n");

		openPopupScript.append("attrib+=',width=' + popupWidth +  '");
		openPopupScript.append(",height=' + popupHeight + '");
		openPopupScript.append(",left=' + popupLeft + '");
		openPopupScript.append(",top=' + popupTop;\n");

		openPopupScript.append("  if (modal) {\n");
		openPopupScript.append("     //  Author:  Danny Goodman (http://www.dannyg.com)\n");
		openPopupScript.append("    if ((!modalPopup) || (!modalPopup.win) || (modalPopup.win && modalPopup.win.closed)) {\n");
		openPopupScript.append("		modalPopup=new Object();\n");
		openPopupScript.append("		modalPopup.win=window.open(url, windname, attrib);\n");
		openPopupScript.append("		if (modalPopup.win) modalPopup.win.focus();\n");
		openPopupScript.append("    } else  {\n ");
		openPopupScript.append("       modalPopup.win.focus();\n");
		openPopupScript.append("    }\n ");
		openPopupScript.append("  } else {\n");
		openPopupScript.append("	windhandle1=window.open(url,windname,attrib); \n");
		openPopupScript.append("	windhandle1.focus();\n");
		openPopupScript.append("  }\n");
		openPopupScript.append("}\n\n");

		return openPopupScript.toString();
	}

	private String generateCheckModalScript() {
		StringBuffer script = new StringBuffer();
		script.append("var windhandle1=null;\n\n");
		script.append("var modalPopup = null;\n");
		script.append("var nonModalDiv=null;\n");
		script.append("var modalDiv=null;\n");

		script.append("function checkModal() {\n");
		script.append("  if (nonModalDiv) {\n ClosePopupDiv(); nonModalDiv=null; }\n");
		script.append("  if (windhandle1) {\n windhandle1.close(); windhandle1=null; }\n");
		script.append("  if (modalPopup || modalDiv) { setTimeout(\"finishChecking()\", 50);}" + "\n");
		script.append("  return true;\n");
		script.append("}\n\n");

		script.append("function finishChecking() {\n");
		script.append("  if (modalPopup && modalPopup.win && !modalPopup.win.closed) \n");
		script.append("    modalPopup.win.focus()\n");
		script.append("  else if (modalDiv && modalDiv.style.display==\"block\")");
		script.append("    modalDiv.focus();\n");
		script.append("}\n\n");

		_page.writeScript("document.onclick=checkModal;");
		if (_page.getBrowserType() == HtmlPage.BROWSER_MICROSOFT)
			_page.writeScript("document.onclick=checkModal;");
		return script.toString();
	}
	private String generateRelativePositionScript() {

		StringBuffer relativePositionScript = new StringBuffer();
		relativePositionScript.append("function getPopupRelativeLeftPosition(relativeComp,relativeCompWindow,currentHeight,currentWidth) {\n");
		relativePositionScript.append("  newLeft = 0;\n");
		relativePositionScript.append("  if (isNaN(relativeCompWindow.screenLeft)) {\n");
		relativePositionScript.append("    newLeft=relativeCompWindow.screenX +(relativeCompWindow.outerWidth-relativeCompWindow.innerWidth)-relativeCompWindow.pageXOffset;\n");
		relativePositionScript.append("  } else {\n");
		relativePositionScript.append("    newLeft=relativeCompWindow.screenLeft;\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  if (relativeComp.offsetParent){\n");
		relativePositionScript.append("    while (relativeComp.offsetParent) {\n");
		relativePositionScript.append("      newLeft += relativeComp.offsetLeft;\n");
		relativePositionScript.append("      relativeComp = relativeComp.offsetParent;\n");
		relativePositionScript.append("    }\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  if ((newLeft + currentWidth) > screen.availWidth)\n");
		relativePositionScript.append("    newLeft -= ((newLeft + currentWidth + 100) - screen.availWidth);\n");
		relativePositionScript.append("  return newLeft;\n");
		relativePositionScript.append("}\n\n");

		relativePositionScript.append("function getPopupRelativeTopPosition(relativeComp,relativeCompWindow,currentHeight,currentWidth) {\n");
		relativePositionScript.append("  newTop = 0;\n");
		relativePositionScript.append("  newTopAbove = relativeCompWindow.screenTop - currentHeight;\n");
		relativePositionScript.append("  if (isNaN(relativeCompWindow.screenLeft)) {\n");
		relativePositionScript.append("    newTop=relativeCompWindow.screenY + (relativeCompWindow.outerHeight-24-relativeCompWindow.innerHeight)-relativeCompWindow.pageYOffset + 26;\n");
		relativePositionScript.append("    newTopAbove = newTop - currentHeight;\n");
		relativePositionScript.append("  } else {\n");
		relativePositionScript.append("    newTop=relativeCompWindow.screenTop + relativeComp.height + 26;\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  if (relativeComp.offsetParent){\n");
		relativePositionScript.append("    while (relativeComp.offsetParent) {\n");
		relativePositionScript.append("      newTop += relativeComp.offsetTop;\n");
		relativePositionScript.append("      newTopAbove += relativeComp.offsetTop;\n");
		relativePositionScript.append("      relativeComp = relativeComp.offsetParent;\n");
		relativePositionScript.append("    }\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  if ((newTop + currentHeight) > screen.availHeight)\n");
		relativePositionScript.append("    newTop = newTopAbove;\t");
		relativePositionScript.append("  return newTop;\n");
		relativePositionScript.append("}\n\n");

		return relativePositionScript.toString();

	}

	private String generateRelativeDivPositionScript() {

		StringBuffer relativePositionScript = new StringBuffer();
		relativePositionScript.append("function getDivRelativeLeftPosition(relativeComp,relativeCompWindow,currentWidth,currentHeight) {\n");
		relativePositionScript.append("  newLeft = 0;\n");
		relativePositionScript.append("  if (relativeComp.offsetParent){\n");
		relativePositionScript.append("    while (relativeComp.offsetParent) {\n");
		relativePositionScript.append("      newLeft += relativeComp.offsetLeft;\n");
		relativePositionScript.append("      relativeComp = relativeComp.offsetParent;\n");
		relativePositionScript.append("    }\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  return checkOffScreenX(newLeft,currentWidth);\n");
		relativePositionScript.append("}\n\n");

		relativePositionScript.append("function getDivRelativeTopPosition(relativeComp,relativeCompWindow,currentWidth,currentHeight,tBodyId) {\n");
		
		relativePositionScript.append("  newTop = 0;\n");
		relativePositionScript.append("  if (relativeComp.offsetParent){\n");
		relativePositionScript.append("    while (relativeComp.offsetParent) {\n");
		relativePositionScript.append("      newTop += relativeComp.offsetTop;\n");
		relativePositionScript.append("      newTop -= relativeComp.scrollTop;\n");
		relativePositionScript.append("      relativeComp = relativeComp.offsetParent;\n");
		relativePositionScript.append("    }\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  newTop += 28;\n");
		relativePositionScript.append("  if (tBodyId) {\n");
		relativePositionScript.append("      var tb=document.getElementById(tBodyId);\n");
		relativePositionScript.append("      if(tb) newTop-=tb.scrollTop;\n");
		relativePositionScript.append("  }\n");
		relativePositionScript.append("  return checkOffScreenY(newTop,currentHeight);\n");
		relativePositionScript.append("}\n\n");

		relativePositionScript.append("function checkOffScreenY(newTop, newHeight) {\n");
		relativePositionScript.append("  var scrollY=getScrollXY()[1];\n");
		relativePositionScript.append("  var winHeight=window.innerHeight ? window.innerHeight : document.body.offsetHeight;\n");
		relativePositionScript.append("  var newBottom = newTop + newHeight - scrollY;\n");
		relativePositionScript.append("  if (newBottom > winHeight) \n");
		relativePositionScript.append("      newTop=newTop -(newHeight+32);\n");
		relativePositionScript.append("  if (newTop < scrollY) \n");
		relativePositionScript.append("      newTop=scrollY;\n");
		relativePositionScript.append("  return newTop;\n");
		relativePositionScript.append("}\n\n");

		relativePositionScript.append("function checkOffScreenX(newLeft, newWidth) {\n");
		relativePositionScript.append("  var scrollX=getScrollXY()[0];\n");
		relativePositionScript.append("  var winWidth=window.innerWidth ? window.innerWidth : document.body.offsetWidth;\n");
		relativePositionScript.append("  var newEnd = newLeft + newWidth - scrollX;\n");
		relativePositionScript.append("  if ((newEnd+20) > winWidth) \n");
		relativePositionScript.append("      newLeft=(winWidth-newWidth)-20;\n");
		relativePositionScript.append("  if (newLeft < scrollX) \n");
		relativePositionScript.append("      newLeft=scrollX;\n");
		relativePositionScript.append("  return newLeft;\n");
		relativePositionScript.append("}\n\n");
		return relativePositionScript.toString();

	}

	private String generateCenterPositionScript() {

		StringBuffer centerPositionScript = new StringBuffer();

		centerPositionScript.append("function getPopupCenterTopPosition(currentWidth,currentHeight) {\n");
		centerPositionScript.append("  return (screen.height - currentHeight) / 2;\n");
		centerPositionScript.append("}\n\n");
		centerPositionScript.append("function getPopupCenterLeftPosition(currentWidth,currentHeight) {\n");
		centerPositionScript.append("  return (screen.width - currentWidth) / 2;\n");
		centerPositionScript.append("}\n\n");

		return centerPositionScript.toString();

	}

	private String generateDivPositionScripts() {
		StringBuffer centerPositionScript = new StringBuffer();
		centerPositionScript.append("function getScrollXY() {\n");
		centerPositionScript.append("    var scrOfX = 0, scrOfY = 0;\n");
		centerPositionScript.append("    if( typeof( window.pageYOffset ) == 'number' ) {\n");
		centerPositionScript.append("	        scrOfY = window.pageYOffset;\n");
		centerPositionScript.append("		    scrOfX = window.pageXOffset;\n");
		centerPositionScript.append("		  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {\n");
		centerPositionScript.append("	    scrOfY = document.body.scrollTop;\n");
		centerPositionScript.append("	    scrOfX = document.body.scrollLeft;\n");
		centerPositionScript.append("	  } else if( document.documentElement &&\n");
		centerPositionScript.append("	      ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {\n");
		centerPositionScript.append("    		    scrOfY = document.documentElement.scrollTop;\n");
		centerPositionScript.append("	    scrOfX = document.documentElement.scrollLeft;\n");
		centerPositionScript.append("	  }\n");
		centerPositionScript.append("	  return [ scrOfX, scrOfY ];\n");
		centerPositionScript.append("	}	\n");

		centerPositionScript.append("function getWindowSize() {\n");
		centerPositionScript.append("     var myWidth = 0, myHeight = 0;\n");
		centerPositionScript.append("	  if( typeof( window.innerWidth ) == 'number' ) {\n");
		centerPositionScript.append("	    myWidth = window.innerWidth;\n");
		centerPositionScript.append("	    myHeight = window.innerHeight;\n");
		centerPositionScript.append("	  } else if( document.documentElement &&\n");
		centerPositionScript.append("	      ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {\n");
		centerPositionScript.append("	    myWidth = document.documentElement.clientWidth;\n");
		centerPositionScript.append("	    myHeight = document.documentElement.clientHeight;\n");
		centerPositionScript.append("	  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {\n");
		centerPositionScript.append("	    myWidth = document.body.clientWidth;\n");
		centerPositionScript.append("	    myHeight = document.body.clientHeight;\n");
		centerPositionScript.append("	  }\n");
		centerPositionScript.append("	  return [myWidth,myHeight];\n");
		centerPositionScript.append("	}\n");

		centerPositionScript.append("function getDivCenterTopPosition(currentWidth,currentHeight) {\n");
		centerPositionScript.append("  var y=getScrollXY()[1];\n");
		centerPositionScript.append("  var height=window.innerHeight ? window.innerHeight : document.body.offsetHeight;\n");
		centerPositionScript.append("  return ((height-currentHeight)/2) + y;\n");
		centerPositionScript.append("}\n\n");
		centerPositionScript.append("function getDivCenterLeftPosition(currentWidth,currentHeight) {\n");
		centerPositionScript.append("  var x=getScrollXY()[0];\n");
		centerPositionScript.append("  var width=window.innerWidth ? window.innerWidth : document.body.offsetWidth;\n");
		centerPositionScript.append("  return ((width-currentWidth)/2) + x;\n");
		centerPositionScript.append("}\n\n");

		return centerPositionScript.toString();

	}

	/**
	 * @see this.generateRelativePopupPositionScript()
	 */
	public String generateRelativePopupPositionScript(HtmlComponent relativeComponent) {
		return generateRelativePopupPositionScript(relativeComponent, -1);
	}

	/**
	 * Generates the necessary javascript to move a popup window to a position
	 * relative to other component of the opener window.
	 * 
	 * @param relativeComponent
	 *            relative component of the opener window to position the popup.
	 * @param row
	 *            row number of the relative component (-1 if it isn't nested
	 *            inside a datatable).
	 * @author Claudio Pi - claudiopi@users.sourceforge.net
	 * @return
	 */

	public String generateRelativePopupPositionScript(HtmlComponent relativeComponent, int row) {

		String result = null;
		if (relativeComponent != null) {
			StringBuffer relativePopupPositionScript = new StringBuffer();

			if (!_page.isScriptAdded("RelativePositionScript")) {
				_page.addScript("RelativePositionScript", generateRelativePositionScript());
			}

			relativePopupPositionScript.append("\n\nrelativeComp=");
			relativePopupPositionScript.append("top.opener.");
			relativePopupPositionScript.append(relativeComponent.getFormString());
			relativePopupPositionScript.append(relativeComponent.getFullName());
			relativePopupPositionScript.append(((row == -1) ? "" : ("_" + row)));
			relativePopupPositionScript.append(";\n");
			relativePopupPositionScript.append("var popupLeft=getPopupRelativeLeftPosition(relativeComp,top.opener.window,popupHeight,popupWidth);\n");
			relativePopupPositionScript.append("var popupTop=getPopupRelativeTopPosition(relativeComp,top.opener.window,popupHeight,popupWidth);\n");
			relativePopupPositionScript.append("var popupWidth=window.outerWidth;\n");
			relativePopupPositionScript.append("var popupHeight=window.outerHeight;\n");
			relativePopupPositionScript.append("\n");
			relativePopupPositionScript.append("window.moveTo(popupLeft,popupTop);");

			result = relativePopupPositionScript.toString();

		}

		return result;

	}

	/**
	 * Generates the necessary javascript to return a key and a description to
	 * the lookup component that called this page.
	 * 
	 * @param key
	 *            the key to return to the edit field of the lookup.
	 * @param description
	 *            the description to return to the lookup.
	 * @author Claudio Pi - claudiopi@users.sourceforge.net
	 * @return the resulting javascript.
	 */
	public String generateReturnValueToLookupScript(String key, String description) {
		HtmlLookUpComponent lookup = _page.getPopupLookupComponent();
		int rowNum = _page.getPopupLookupComponentRow();
		String editField = lookup.getEditFieldFullName(rowNum);
		String descField = lookup.getHiddenDescrFieldFullName(rowNum);

		if (lookup.getEditDescription()) {
			editField = lookup.getHiddenKeyFieldFullName(rowNum);
			descField = lookup.getEditFieldFullName(rowNum);
		}

		if (!lookup.getUseDiv()) {
			return ("if (top.opener && !top.opener.closed) {" + "top.opener." + editField + ".value='" + key + "';" + "top.opener." + descField + ".value='" + description + "';" + "var theSpan=top.opener.document.getElementById('"
					+ _page.getPopupLookupComponent().getDivFullName(rowNum) + "');" + "if (theSpan) theSpan.innerHTML='" + description + "';" + "window.close();}");
		} else {
			return ("parent." + editField + ".value='" + key + "';" + "parent." + descField + ".value='" + description + "';" + "var theSpan=parent.document.getElementById('" + _page.getPopupLookupComponent().getDivFullName(rowNum) + "');"
					+ "if (theSpan) theSpan.innerHTML='" + description + "';" + "parent.ClosePopupDiv();");
		}
	}

	/**
	 * Generates the necessary javascript to close a popup lookup
	 * 
	 * @return the resulting javascript.
	 */
	public String generateCancelLookupScript() {
		HtmlLookUpComponent lookup = _page.getPopupLookupComponent();
		if (!lookup.getUseDiv()) {
			return ("if (top.opener && !top.opener.closed) window.close();");
		} else {
			return "parent.ClosePopupDiv();";
		}
	}

	/**
	 * Generates a script for selecting rows in a datatable
	 */
	public void generateSelectRowScript() {
		if (!_page.isScriptAdded("DataTableSelectRowScript")) {
			StringBuffer sb = new StringBuffer();
			sb.append("document.onselectstart = doSelect;\n");
			sb.append("var doSelect=true;\n");
			sb.append("var anchorRow=-1;\n");
			sb.append("function doSelect(){\n");
			sb.append("return doSelect;\n");
			sb.append("}\n");
			sb.append("\n");
			sb.append("function selectRows(pageForm,prefix, row, rowStart, rowEnd, event, trueValue, falseValue, selectedColor, selectOne) {\n");
			sb.append("   	if ((! selectOne) && shiftPressed(event) && anchorRow != -1) {\n");
			sb.append("    			for (var i=rowStart; i <=rowEnd; i++)\n");
			sb.append("					selectRow(pageForm,prefix,i,false, trueValue, falseValue, selectedColor);\n");
			sb.append("			var first=row;\n");
			sb.append("			var last=anchorRow;\n");
			sb.append("			if (first > last) {\n");
			sb.append("				first=anchorRow;\n");
			sb.append("				last=row;\n");
			sb.append("			}\n");
			sb.append("			for (var i=first; i <=last; i++)\n");
			sb.append("				selectRow(pageForm,prefix,i,true, trueValue, falseValue, selectedColor);\n");
			sb.append("		}\n");
			sb.append("		else if ((!selectOne) && ctrlPressed(event)) {\n");
			sb.append("			anchorRow=-1;\n");
			sb.append("			var hdn=pageForm.elements[prefix + '_select_' + row];\n");
			sb.append("         if (hdn.value==trueValue)\n");
			sb.append("				selectRow(pageForm,prefix,row,false, trueValue, falseValue, selectedColor);\n");
			sb.append("			else\n");
			sb.append("				selectRow(pageForm,prefix,row,true, trueValue, falseValue, selectedColor);\n");
			sb.append("      }\n");
			sb.append("      else {\n");
			sb.append("			for (var i=rowStart; i <=rowEnd; i++)\n");
			sb.append("				selectRow(pageForm,prefix,i,false, trueValue, falseValue, selectedColor);\n");
			sb.append("			selectRow(pageForm,prefix,row,true, trueValue, falseValue, selectedColor);\n");
			sb.append("			anchorRow=row;\n");
			sb.append("	    }\n");
			sb.append("	    return false;\n");
			sb.append("}\n");
			sb.append("	   function selectRow(pageForm,prefix,row,select, trueValue, falseValue, selectedColor) {\n");
			sb.append("	   	    var hdn=pageForm.elements[prefix + '_select_' + row];\n");
			sb.append("	   		if (hdn.value == trueValue && select)\n");
			sb.append("	   			return;\n");
			sb.append("    		if (hdn.value == falseValue && !select)\n");
			sb.append("				return;\n");
			sb.append("         var rowIn=findParentTR(hdn);\n");
			sb.append("   		var newColor=pageForm.elements[prefix + 'r0_color_' + row].value;\n");
			sb.append("	   	    if (! select) \n");
			sb.append("		       hdn.value=falseValue;\n");
			sb.append("	        else {\n");
			sb.append("	    			hdn.value=trueValue;\n");
			sb.append("	    			newColor=selectedColor;\n");
			sb.append("	    		}\n");
			sb.append("			rowNo=0;\n");
			sb.append("			while (rowIn != null) {\n");
			sb.append("	    		rowNo++;\n");
			sb.append("    			rowIn.bgColor=newColor;\n");
			sb.append("				var cells=rowIn.cells;\n");
			sb.append("    	    	for (var i=0;i<cells.length;i++)\n");
			sb.append("	    			cells[i].bgColor=newColor;\n");
			sb.append("	    		hdn=pageForm.elements[prefix + 'r' + rowNo + '_color_' + row];\n");
			sb.append("	    		if (hdn == null)\n");
			sb.append("	    			rowIn=null;\n");
			sb.append("   			else\n");
			sb.append("	    			rowIn=findParentTR(hdn);\n");
			sb.append("	    		}\n");
			sb.append("	    	}\n");
			sb.append("	    	function findParentTR(hdn) {\n");
			sb.append("             if (hdn==null)\n");
			sb.append("	    			return null;\n");
			sb.append("	            var parentNode=hdn.parentNode;\n");
			sb.append("	    	    while(parentNode != null && parentNode.tagName != 'TR')\n");
			sb.append("					parentNode=parentNode.parentNode;\n");
			sb.append("	    		return parentNode;\n");
			sb.append("}\n");
			sb.append("	    	function ctrlPressed(e) {\n");
			sb.append("  			var nav4 = window.Event ? true : false;\n");
			sb.append("  			if (nav4)  \n");
			sb.append("     			return e.ctrlKey;\n");
			sb.append("	    	    else \n");
			sb.append("	    	        return window.event.ctrlKey;\n");
			sb.append("	    	}\n");
			sb.append(" 		function shiftPressed(e) {\n");
			sb.append("             var nav4 = window.Event ? true : false;\n");
			sb.append("	    	    if (nav4)  \n");
			sb.append("	    	     return e.shiftKey;\n");
			sb.append("  			else \n");
			sb.append("	    	     return window.event.shiftKey;\n");
			sb.append("     	}\n");
			_page.addScript("DataTableSelectRowScript", sb.toString());
		}

	}

}