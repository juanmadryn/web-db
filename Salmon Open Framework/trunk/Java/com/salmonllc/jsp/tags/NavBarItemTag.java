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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/NavBarItemTag.java $
//$Author: Srufle $ 
//$Revision: 16 $ 
//$Modtime: 4/15/03 10:24a $ 
/////////////////////////
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspNavBar;

/**
 * Represents a line item in a nav bar
 */
public class NavBarItemTag extends BaseEmptyTag {
	private String _title;
	private String _href;
	private String _hspace;
	private String _target;
	private String _bgcolor;
	private String _showpopup;
	private String _subMenuName;
	
/**
 * This method creates a NavBarItem. Must find the NavBar Tag and the last group added. Will be placed in this group
 */ 
public HtmlComponent createComponent() {
	NavBarTag nbt = getHelper().getNavBarTag();
	if (nbt != null) {
		JspNavBar nb = (JspNavBar) nbt.getHelper().getComponent();
        boolean showPopup = true;
		if (_showpopup != null)
		   showPopup = new Boolean(_showpopup).booleanValue();
		nb.addGroupItem(getName(),nb.getLastGroupAdded(),_title,_href,_target,BaseTagHelper.stringToInt(_hspace),_bgcolor,_subMenuName,showPopup);
	}	
    return null;
}
/**
 * Get the Bg Color attribute for the tag
 */
public String getBgcolor() {
	return _bgcolor;
}
/**
 * Get the Href attribute for the tag
 */
public String getHref() {
	return _href;
}
/**
 * Get the Hspace attribute for the tag
 */
public String getHspace() {
	return _hspace;
}
/**
 * Get the Show Popup attribute for the tag
 */
public String getShowpopu() {
	return _showpopup;
}
/**
 * Get the SubMenu attribute for the tag
 */
public String getSubmenuname() {
	return _subMenuName;
}
/**
 * Get the Target attribute for the tag
 */
public String getTarget() {
	return _target;
}
/**
 * Get the Title attribute for the tag
 */
public String getTitle() {
	return _title;
}
/**
 * Part of the tag library specification. Clears all resources used by the tag.
 */
public void release() {
    super.release();
    _title = null;
    _href = null;
    _hspace = null;
    _target=null;
    _subMenuName=null;
    _bgcolor=null;
    _showpopup=null;
}
/**
 * This method sets the Bg Color attribute for the tag
 */ 
public void setBgcolor(String val) {
	_bgcolor = val;
}
/**
 * This method sets the Href attribute for the tag
 */ 
public void setHref(String val) {
	_href = val;
}
/**
 * This method sets the Horizontal space attribute for the tag
 */ 
public void setHspace(String val) {
	_hspace = val;
}
/**
 * This method sets the Show Popup attribute for the tag
 */ 
public void setShowpopup(String val) {
	_showpopup = val;
}
/**
 * This method sets the Submenu Name attribute for the tag
 */ 
public void setSubmenuname(String val) {
	_subMenuName = val;
}
/**
 * This method sets the Target attribute for the tag
 */ 
public void setTarget(String val) {
	_target = val;
}
/**
 * This method sets the Title attribute for the tag
 */ 
public void setTitle(String val) {
	_title = val;
}
}
