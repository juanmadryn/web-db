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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/CookieCrumbleTag.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 12/15/03 7:43p $
////////////////////////////////////////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

import javax.servlet.jsp.JspWriter;

/**
 * This tag create a cooklie crumble object.
 */

public class CookieCrumbleTag extends BaseBodyTag
{
    private String _fLinkfont = "CrumbleLinkFont";
	private String _fFont = "CrumbleFont";
	private String _fSeparator = " : ";

    /**
     * Creates the component to be used by the tag.
     */
    public HtmlComponent createComponent()
    {
        JspCookieCrumble ret = new JspCookieCrumble(getName(), getHelper().getController());

        String linkFont = getLinkfont();
        if (linkFont != null)
        {
            ret.setCrumbleLinkFont(linkFont);
        }
		String font = getFont();
		if (font != null)
		{
			ret.setCrumbleFont(font);
		}

		String separator = getSeparator();
		if (separator != null)
		{
			ret.setSeparator(separator);
		}

        return ret;
    }

    /**
     * This method is called when necessary to generate the required html for a tag.
     *  It should be overridden by tags that have more Html to generate
     * (generally tags that require several passes to complete). A tag shouldn't
     * generate any Html itself, but should instead delagate that to the Html
     * or JSP component within it.
     */

    protected void generateComponentHTML(JspWriter w) throws Exception
    {
        super.generateComponentHTML(w);

    }

    /**
     * Gets the crumble font property for the tag
     * @return java.lang.String
     */
    public java.lang.String getFont()
    {
        return _fFont;
    }

    /**
     * Gets the crumble link font property for the tag
     * @return java.lang.String
     */

    public java.lang.String getLinkfont()
    {
        return _fLinkfont;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_WRAP_ALL_NESTED;
    }

    /**
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _fLinkfont = null;
        _fFont = null;

    }

    /**
     * Sets the crumble font property for the tag
     * @param font to be used for crumble objects
     */
    public void setFont(String font)
    {
        _fFont = font;
    }

    /**
     * Sets the crumble link font property for the tag
     * @param font to be used for crumble link objects
     */
    public void setLinkfont(String font)
    {
        _fLinkfont = font;
    }



	/**
	 * @return
	 */
	public String getSeparator()
	{
		return _fSeparator;
	}

	/**
	 * @param string
	 */
	public void setSeparator(String string)
	{              
		_fSeparator = string;
	}

}
