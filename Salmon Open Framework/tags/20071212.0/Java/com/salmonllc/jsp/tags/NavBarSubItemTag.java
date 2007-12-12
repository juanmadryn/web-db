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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/NavBarSubItemTag.java $
//$Author: Srufle $ 
//$Revision: 13 $ 
//$Modtime: 4/15/03 10:24a $ 
/////////////////////////
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspNavBar;

/**
 * Represents a nested item in a navbar
 */
public class NavBarSubItemTag extends BaseEmptyTag {
	private String _title;
	private String _href;
	private String _hspace;
	private String _target;
	private String _bgcolor;
	private String _subMenu;
	private String _subMenuGroup;
	
/**
 * This method creates a Sub NavBarItem. 
 * Must find the NavBar Tag and the last subMenu added. 
 * Will be placed in this Submenu
 */ 

 public HtmlComponent createComponent() {
	NavBarTag nbt = getHelper().getNavBarTag();
	if (nbt != null) {
		JspNavBar nb = (JspNavBar) nbt.getHelper().getComponent();
        int iHpad=BaseTagHelper.stringToInt(_hspace)==-1?0:BaseTagHelper.stringToInt(_hspace);
        nb.addSubItem(getName(),_title,_href,_target,iHpad,_subMenu,_subMenuGroup,_bgcolor);
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
 * Get the Submenu Group attribute for the tag
 */
public String getSubmenugroup() {
	return _subMenuGroup;
}
/**
 * Get the Submenu attribute for the tag
 */
public String getSubmenuname() {
	return _subMenu;
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
    _subMenu=null;
    _bgcolor=null;
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
 * This method sets the Hspace attribute for the tag
 */ 
public void setHspace(String val) {
	_hspace = val;
}
/**
 * This method sets the Sub menu Group attribute for the tag
 */ 
public void setSubmenugroup(String val) {
	_subMenuGroup = val;
}
/**
 * This method sets the Sub menu attribute for the tag
 */ 
public void setSubmenuname(String val) {
	_subMenu = val;
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
