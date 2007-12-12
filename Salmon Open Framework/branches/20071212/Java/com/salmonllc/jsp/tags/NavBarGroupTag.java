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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/NavBarGroupTag.java $
//$Author: Saqib $ 
//$Revision: 17 $ 
//$Modtime: 7/30/03 4:41p $ 
////////////////////////////////////////////////////////////
import com.salmonllc.html.HtmlComponent;

import com.salmonllc.jsp.JspNavBar;


/**
 * This is an empty tag but must be placed inside a navbar tag.
 */
public class NavBarGroupTag extends BaseEmptyTag
{
    private String _headerImage;
    private String _vspace;
    private String _href;
    private String _groupTitle;
    private String _bgColor;
    private String _groupHrefStyle;
    private String _groupHoverStyle;

    /**
     * This method creates a NavBargroup. Must find the NavBar Tag the group will be placed in first.
     */
    public HtmlComponent createComponent()
    {
        NavBarTag nbt = getHelper().getNavBarTag();

        if (nbt != null)
        {
            JspNavBar nb = (JspNavBar) nbt.getHelper().getComponent();
            nb.createGroup(getName(), _headerImage, _href, _groupTitle, BaseTagHelper.stringToInt(_vspace), _bgColor, _groupHrefStyle, _groupHoverStyle);
        }

        return null;
    }

    /**
     * Get the Group BgColor attribute for the tag
     */
    public String getBgcolor()
    {
        return _bgColor;
    }

    /**
     * Get the Group Title attribute for the tag
     */
    public String getGrouptitle()
    {
        return _groupTitle;
    }

    /**
     * Get the Group Header Image attribute for the tag
     */
    public String getHeaderimage()
    {
        return _headerImage;
    }

    /**
     * Get the href attribute for the tag
     */
    public String getHref()
    {
        return _href;
    }

    /**
     * Get the vertical space attribute for the tag
     */
    public String getVspace()
    {
        return _vspace;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _headerImage = null;
        _vspace      = null;
        _href        = null;
        _groupTitle  = null;
        _bgColor     = null;
        _groupHrefStyle = null;
        _groupHoverStyle = null;
    }

    /**
     * This method sets the Group BgColor attribute for the group created
     */
    public void setBgcolor(String val)
    {
        _bgColor = val;
    }

    /**
     * This method sets the Group Title attribute for the tag Text to be displayed above group
     */
    public void setGrouptitle(String val)
    {
        _groupTitle = val;
    }

    /**
     * This method sets the Header Image attribute for the tag Image to be displayed above group
     */
    public void setHeaderimage(String val)
    {
        _headerImage = val;
    }

    /**
     * This method sets the href attribute for the tag
     */
    public void setHref(String val)
    {
        _href = val;
    }

    /**
     * This method sets the vertical space attribute for the tag
     */
    public void setVspace(String val)
    {
        _vspace = val;
    }
	/**
	 * @return
	 */
	public String getGrouphoverstyle()
	{
		return _groupHoverStyle;
	}

	/**
	 * @return
	 */
	public String getGrouphrefstyle()
	{
		return _groupHrefStyle;
	}

	/**
	 * @param string
	 */
	public void setGrouphoverstyle(String string)
	{
		_groupHoverStyle = string;
	}

	/**
	 * @param string
	 */
	public void setGrouphrefstyle(String string)
	{
		_groupHrefStyle = string;
	}

}
