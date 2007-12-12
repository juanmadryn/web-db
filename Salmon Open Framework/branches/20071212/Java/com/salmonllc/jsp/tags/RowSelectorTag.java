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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/RowSelectorTag.java $
//$Author: Srufle $ 
//$Revision: 6 $ 
//$Modtime: 4/15/03 10:38a $ 
/////////////////////////


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspRowSelector;

/**
 * This tag creates an Html Style
 */

public class RowSelectorTag extends BaseEmptyTag {
	private String _onClick;

	
public HtmlComponent createComponent() {
    
    JspRowSelector _rowSelector = new JspRowSelector(getName(), getHelper().getController());
    if (_onClick != null)
       _rowSelector.setOnClick(_onClick);

    
    return _rowSelector;
}
/**
 * Returns the On Click attribute
 */

public String getOnclick() {
	return _onClick;
}
public void release() {
    super.release();
    _onClick = null;

}
/**
 * Sets the On Click attribute
 */

public void setOnclick(String val) {
	_onClick = val;
}
}
