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


////////////////////////////////////////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/CrumbleTag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 10/30/02 2:40p $
////////////////////////////////////////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspCookieCrumble;

/**
 * The CrumbleTag tag represents the crumbles in a CookieCrumble tag.
 */
public class CrumbleTag extends BaseEmptyTag {
    private String _fDisplay;
    private String _fKey;
    private String _fHref;

    /**
     * There is no component for a crumble. It adds the crumble to it's parent CookieCrumble tag.
     */
    public HtmlComponent createComponent() {
        CookieCrumbleTag cct = (CookieCrumbleTag) getParent();
        HtmlComponent comp = cct.getHelper().getComponent();
        // add to parent tag and return null
        ((JspCookieCrumble) comp).addCrumble(_fKey, _fDisplay, _fHref);

        return null;
    }

    /**
     * Get the text that is to be displayed on the screen.
     * @return java.lang.String
     */
    public java.lang.String getDisplay() {
        return _fDisplay;
    }

    /**
     * Get the href that is to be used.
     * @return java.lang.String
     */
    public java.lang.String getHref() {
        return _fHref;
    }

    /**
     * Get the key to be used for looking up this crumble object.
     * @return java.lang.String
     */
    public java.lang.String getKey() {
        return _fKey;
    }

    /**
     * Release all resources used by the tag.
     */
    public void release() {
        super.release();
        _fDisplay = null;
        _fKey = null;
        _fHref = null;
    }

    /**
     * Sets the text that is to be displayed on the screen.
     * @param newDisplay java.lang.String
     */
    public void setDisplay(java.lang.String newDisplay) {
        _fDisplay = newDisplay;
    }

    /**
     * Sets the href that is to be used.
     * @param newHref java.lang.String
     */
    public void setHref(java.lang.String newHref) {
        _fHref = newHref;
    }

    /**
     * Sets the key that is to be used for looking up this crumble object.
     * @param newKey java.lang.String
     */
    public void setKey(java.lang.String newKey) {
        _fKey = newKey;
    }
}
