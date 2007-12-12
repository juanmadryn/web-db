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
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/NestedTag.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 10/30/02 2:40p $
/////////////////////////

import com.salmonllc.html.*;

/**
 * This is a special type of tag that is only used as part of another tag. It is used as a placeholder to indicate the different sections of a master tag.
 */

public class NestedTag extends BaseBodyTag
{
    private int _activeMode;

    public NestedTag()
    {
    }

    /**
     * This is a stub method. Nested tags don't create components.
     */

    public HtmlComponent createComponent()
    {
        return null;
    }

    /**
     * Tests if the nested tags contents should be generated for a particular mode of the parent tag.
     */
    public boolean isTagOKForMode()
    {
        boolean retVal = getHelper().getContainerTag().getMode() == _activeMode;
        return retVal;
    }

    /**
     * This method is used by the subclasess of this one to set the mode that the tag is valid in.
     */
    protected void setActiveMode(int mode)
    {
        _activeMode = mode;
    }

    /**
     * Returns the type of dreamweaver conversion to do for this tag
     */
    public int getTagConvertType()
    {
        return CONV_INVISIBLE;
    }
}
