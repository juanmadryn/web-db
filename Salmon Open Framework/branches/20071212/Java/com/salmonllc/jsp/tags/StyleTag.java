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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/StyleTag.java $
//$Author: Srufle $ 
//$Revision: 12 $ 
//$Modtime: 4/15/03 2:24p $ 
/////////////////////////


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlStyle;

/**
 * This tag creates an Html Style
 */

public class StyleTag extends BaseEmptyTag {
	private String _type;
	private String _style;

	
public HtmlComponent createComponent() {
    return new HtmlStyle(getName(), _type, _style, getHelper().getController());
}
/**
 * Returns the style attribute
 */

public String getStyle() {
	return _style;
}
/**
 * Returns the type attribute
 */

public String getType() {
	return _type;
}
public void release() {
    super.release();
    _style = null;
    _type = null;

}
/**
 * Sets the style attribute
 */

public void setStyle(String style) {
	_style = style;
}
/**
 * Sets the type attribute
 */
public void setType(String type) {
	_type = type;
}
}
