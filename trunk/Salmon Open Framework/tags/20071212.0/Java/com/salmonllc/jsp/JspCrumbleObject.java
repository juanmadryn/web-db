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
package com.salmonllc.jsp;



/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspCrumbleObject.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 4/21/03 9:53a $
/////////////////////////

import java.io.Serializable;

/**
 * This class is used in conjection with
 * @see com.salmonllc.jsp.JspCookieCrumble
 */

public class JspCrumbleObject  implements Serializable
{
    private String _fName;
    private String _fText;
    private String _fHref;

    /**
     * JspCrumbleObject constructor
     * @param name - mandatory
     * @param textDesc - optional
     * @param href - optional
     */
    public JspCrumbleObject(String name, String textDesc, String href)
    {
        super();
        _fName = name;
        _fText = textDesc;
        _fHref = href;
    }

    /**
     * Get crumble href value
     * @return String
     */
    public String getHref()
    {
        return _fHref;
    }

    /**
     * Get crumble name value
     * @return String
     */
    public String getName()
    {
        return _fName;
    }

    /**
     * Get crumble text value
     * @return String
     */
    public String getText()
    {
        return _fText;
    }

    /**
     * Set the crumble href
     * @param newHref
     */
    public void setHref(String newHref)
    {
        _fHref = newHref;
    }

    /**
     * Set the crumble name
     * @param newName
     */
    public void setName(String newName)
    {
        _fName = newName;
    }

    /**
     * Set the crumble text
     * @param newText
     */
    public void setText(String newText)
    {
        _fText = newText;
    }

    public boolean equals(Object object)
    {
        if (object == this){
            return true;
        }

        if (object == null){
            return false;
        }

        if (!(object instanceof JspCrumbleObject)){
            return false;
        }

        JspCrumbleObject crumble = (JspCrumbleObject) object;

        if ( ! crumble.getName().equals(this.getName()) ) {
            return false;
        }


        return true;
    }

}
