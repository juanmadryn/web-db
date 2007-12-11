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


import com.salmonllc.html.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * This type create a group of text components that shows the history of where you have been in the site.
 * @see com.salmonllc.jsp.tags.CookieCrumbleTag
 * @see  com.salmonllc.jsp.tags.CrumbleTag
 */
public class JspCookieCrumble extends HtmlComponent
{
    private ArrayList     _arrCrumble                = new ArrayList();
    private Hashtable     _hashCrumble               = new Hashtable();
    private HtmlContainer _cont;
    private String        _fCrumbleFont              = "CrumbleFont";
    private String        _fCrumbleLinkFont          = "CrumbleLinkFont";
    private String        _fSeparator;
    private String        _fTheme;
    private boolean       _fFixSpecialHtmlCharacters;

    /**
     * JspCookieCrumble constructor comment.
     *
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public JspCookieCrumble(String name, HtmlPage p)
    {
        super("CookieCrumbleComp" + name, p);

        try
        {
            _cont       = new HtmlContainer("crumble_cont", p);
            _fSeparator = " : ";
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("CookieCrumbleComp", e, this);
        }
    }

    /**
     * This method gets a crumble object from the cookie crumble.
     *
     * @param key - name of crumble you are looking for
     *
     * @return JspCrumbleObject
     */
    public JspCrumbleObject getCrumble(String key)
    {
        if (key == null)
        {
            return null;
        }
        else
        {
            return (JspCrumbleObject) _hashCrumble.get(key);
        }
    }

    /**
     * Sets the crumbles text portion font type.
     *
     * @param newCrumbleFont - font to use for the crumbles
     */
    public void setCrumbleFont(java.lang.String newCrumbleFont)
    {
        _fCrumbleFont = newCrumbleFont;
    }

    /**
     * Gets the crumbles text portion font type.
     *
     * @return java.lang.String
     */
    public java.lang.String getCrumbleFont()
    {
        return _fCrumbleFont;
    }

    /**
     * Sets the crumbles link portion font type.
     *
     * @param newCrumbleLinkFont java.lang.String
     */
    public void setCrumbleLinkFont(java.lang.String newCrumbleLinkFont)
    {
        _fCrumbleLinkFont = newCrumbleLinkFont;
    }

    /**
     * Gets the crumbles link portion font type.
     *
     * @return java.lang.String
     */
    public java.lang.String getCrumbleLinkFont()
    {
        return _fCrumbleLinkFont;
    }

    /**
     * Sets the cookie crumble to fix html characters in each crumble object
     *
     * @param b - flag value
     */
    public void setFixSpecialHtmlCharacters(boolean b)
    {
        _fFixSpecialHtmlCharacters = b;
    }

    /**
     * Gets whether the cookie crumble should fix html characters in each crumble object
     *
     * @return boolean
     */
    public boolean isFixSpecialHtmlCharacters()
    {
        return _fFixSpecialHtmlCharacters;
    }

    /**
     * Sets the seperator character to use between crumbles
     *
     * @param string
     */
    public void setSeparator(String string)
    {
        _fSeparator = string;
    }

    /**
     * Gets the seperator character to use between crumbles
     *
     * @return
     */
    public String getSeparator()
    {
        return _fSeparator;
    }

    /**
     * Sets the crumble theme.
     *
     * @param newTheme java.lang.String
     */
    public void setTheme(java.lang.String newTheme)
    {
        _fTheme = newTheme;
    }

    /**
     * Gets the theme of the Crumble component.
     *
     * @return java.lang.String
     */
    public java.lang.String getTheme()
    {
        return _fTheme;
    }

    /**
     * This method adds a crumble object to the cookie crumble.
     *
     * @param key - name used for looking up the crumble
     * @param textDesc - text of teh crumble
     * @param href - (Optional) href or the crumble
     */
    public void addCrumble(String key, String textDesc, String href)
    {
        JspCrumbleObject cro = new JspCrumbleObject(key, textDesc, href);
        addCrumble(cro);
    }

    /**
     * This method adds a crumble object to the cookie crumble.
     *
     * @param cro
     */
    public void addCrumble(JspCrumbleObject cro)
    {
        _arrCrumble.add(cro);
        _hashCrumble.put(cro.getName(), cro);
    }

    /**
     * This method will generate the html for each component in the page
     *
     * @param p - PrintWriter
     * @param rowNo - rowNo
     *
     * @throws Exception
     */
    public void generateHTML(PrintWriter p, int rowNo)
                      throws Exception
    {
        if (!_visible)
        {
            return;
        }

        // for each item in list
        JspCrumbleObject cro           = null;
        HtmlText         htText        = null;
        HtmlText         separatorText = null;
        HtmlLink         hlLink        = null;

        _cont.removeAll();

        int arrSize = _arrCrumble.size();

        if (arrSize <= 0)
        {
            return;
        }

        //  MessageLog.writeDebugMessage(" 1 generateHTML arrSize=" + arrSize, this);
        String croText = null;
        String croHref = null;

        for (int i = 0; i < arrSize; i++)
        {
            //      MessageLog.writeDebugMessage(" 1 generateHTML i=" + i, this);
            // create a link with text in it
            cro     = (JspCrumbleObject) _arrCrumble.get(i);
            htText  = null;
            croHref = cro.getHref();
            croText = cro.getText();

            if (Util.isFilled(croHref))
            {
                htText = new HtmlText(croText, getCrumbleLinkFont(), getPage());
                htText.setFixSpecialHtmlCharacters(isFixSpecialHtmlCharacters());
                hlLink = new HtmlLink("hlLink" + i, croHref, getPage());
                hlLink.add(htText);
            }
            else
            {
                htText = new HtmlText(croText, getCrumbleFont(), getPage());
                htText.setFixSpecialHtmlCharacters(isFixSpecialHtmlCharacters());
            }

            // add link
            // add :
            String separator = getSeparator();

            if (!Util.isFilled(separator))
            {
                separator = " : ";
            }

            separatorText = new HtmlText(separator, getCrumbleLinkFont(), getPage());
            separatorText.setFixSpecialHtmlCharacters(isFixSpecialHtmlCharacters());

            if (Util.isFilled(croHref))
            {
                _cont.add(hlLink);
            }
            else
            {
                _cont.add(htText);
            }

            if (i < (arrSize - 1))
            {
                _cont.add(separatorText);
            }
        }

        _cont.generateHTML(p, rowNo);
    }

    /**
     * This method removes all crumble objects from the cookie crumble.
     *
     * @return boolean
     */
    public boolean removeAllCrumble()
    {
        boolean ret = false;
        _hashCrumble.clear();
        _arrCrumble.clear();

        if ((_hashCrumble.size() == 0) && (_arrCrumble.size() == 0))
        {
            ret = true;
        }

        return ret;
    }

    /**
     * This method removes a single crumble object from the cookie crumble.
     *
     * @param key - name of crumble you are looking for
     *
     * @return boolean
     */
    public boolean removeCrumble(String key)
    {
        boolean ret = false;

        if (key == null)
        {
            ret = false;
        }
        else
        {
            JspCrumbleObject cro = (JspCrumbleObject) _hashCrumble.get(key);
            ret = _arrCrumble.remove(cro);
            _hashCrumble.remove(key);
        }

        return ret;
    }

    /**
     * This method removes a crumble object from the cookie crumble. And all ones that follow.
     *
     * @param key - name of crumble you are looking for
     */
    public void trimCrumble(String key)
    {
        if (key == null)
        {
            return;
        }
        else
        {
            JspCrumbleObject cro = (JspCrumbleObject) _hashCrumble.get(key);

            if (cro == null)
            {
                return;
            }

            int i         = _arrCrumble.size() - 1;
            int stopIndex = _arrCrumble.indexOf(cro);

            for (; i >= stopIndex; i--)
            {
                cro = (JspCrumbleObject) _arrCrumble.remove(i);
                _hashCrumble.remove(cro.getName());
            }
        }
    }
}
