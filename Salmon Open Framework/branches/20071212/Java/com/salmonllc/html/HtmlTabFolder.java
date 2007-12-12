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
package com.salmonllc.html;

import com.salmonllc.properties.*;
import java.util.*;

/**
 * This type implements a navigation bar which resembles a tab folder.  Each tab entry contains a display component, typically text, and an HTML link.
 */
public class HtmlTabFolder extends HtmlComponent {
    /**
	 * @author  demian
	 */
    class TabInfo {
        String _href;
        HtmlComponent _item;
        String _target;
        boolean _active;

        TabInfo (HtmlComponent item, String href, String target, boolean active) {
            _href = href;
            _item = item;
            _target = target;
            _active = active;
        }
    };
    private Vector _tabItems;
    protected String _imagePath;
    private String _bgColor;
    private String _bgColorActive;
    private String _tabImageLeft;
    private String _tabImageRight;
    private String _tabImageBorder;
    private String _tabFontStartTag;
    private String _tabFontEndTag;
    private String _tabFontActiveStartTag;
    private String _tabFontActiveEndTag;
    private int _height;
    private boolean _showStripe;
    private HtmlStyle _tabStyle;
    private String _width = "100%";
    String _theme = null;
    boolean enabledTab = true;
    /**
     * Constructs a new HtmlTabFolder to place in the given page.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public HtmlTabFolder(String name, HtmlPage p) {
        this(name,null,p);
    }
    /**
     * Constructs a new HtmlTabFolder to place in the given page.
     * @param name java.lang.String
     * @param theme The theme to use for loading properties
     * @param p com.salmonllc.html.HtmlPage
     */
    public HtmlTabFolder(String name, String theme, HtmlPage p) {
        super(name, p);
        // Initialize properties with default values.
        _tabItems = new Vector();
        _imagePath = "../../Objectstore/Images/";
        _tabImageLeft = _imagePath + "TabLeft3D.gif";
        _tabImageRight = _imagePath + "TabRight3D.gif";
        _tabImageBorder = _imagePath + "999999_1x1.gif";		// a kind of grey
        _bgColor = "#000066";
        _bgColorActive = "#c9c2a2";							// light beige
        _tabFontStartTag = "<FONT FACE=\"Helvetica\" SIZE=\"2\" COLOR=\"ORANGE\"><B>";
        _tabFontEndTag = "</B></FONT>";
        _tabFontActiveStartTag = "<FONT FACE=\"Helvetica\" SIZE=\"2\" COLOR=\"BLACK\"><B>";
        _tabFontActiveEndTag = "</B></FONT>";
        _height = 20;
        setTheme(theme);
    }
    /**
     * This method adds a tab entry to the folder.
     * @param item HtmlComponent The component that will appear in the tab
     * @param href java.lang.String The href for the tab link
     * @param target java.lang.String Target for the href, optionally null
     * @param is_active boolean True if this tab is active (displayed in the active color).
     */
    public void addTab (HtmlComponent item, String href, String target, boolean is_active) {
        _tabItems.addElement(new TabInfo(item, href, target, is_active));
    }
    /**
     * This method adds a tab entry to the folder.  Text is displayed; target of the href is null; tab is not active.
     * @param text Text to display in the tab
     * @param href Href to use in the link
     */
    public void addTab (String text, String href) {
        addTab(text, href, null, false);
    }
    /**
     * This method adds a tab to the folder.
     * @param text java.lang.String The text that will appear in the tab
     * @param href java.lang.String The href for the tab link
     * @param target java.lang.String Target for the href, optionally null
     * @param is_active boolean True if this tab is active (use active color)
     */
    public void addTab (String text, String href, String target, boolean is_active) {
        HtmlText item = new HtmlText(text, getPage());
        if (is_active) {
            item.setFontStartTag(_tabFontActiveStartTag);
            item.setFontEndTag(_tabFontActiveEndTag);
        }
        else {
            item.setFontStartTag(_tabFontStartTag);
            item.setFontEndTag(_tabFontEndTag);
        }
        _tabItems.addElement(new TabInfo(item, href, target, is_active));
    }
    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (! getVisible() || (_tabItems.size() == 0))
            return;

        p.print("<TABLE cellspacing=0 cellpadding=0 border=0 ");
        p.println(" width=" + getWidth() + ">");
        p.println("<TR>");
        for (int i = 0; i < _tabItems.size(); i++) {
            TabInfo info = (TabInfo)_tabItems.elementAt(i);
            p.print("<TD align=middle bgColor=\"" + (info._active ? _bgColorActive : _bgColor) + "\"");
            p.print(" vAlign=top>");
            p.print("<IMG align=left height=5 hspace=0 src=\"" + _tabImageLeft + "\" width=5>");
            p.print("<IMG align=right height=5 hspace=0 src=\"" + _tabImageRight + "\" width=5>");
            p.print("<IMG height=1 hspace=0 src=\"" + _tabImageBorder + "\" width=\"100%\">");
            p.print("<BR clear=all>");
            // We would like to put 100% for the height.  However this only seems to work for table
            // cells other than the first. i..e the browser requires that a specific height be given
            // first.
            p.print("<IMG align=right height=" + _height + " hspace=0 src=\"" + _tabImageBorder + "\" width=2>");
            if(enabledTab)
            {
                p.print("<A href=\"" + info._href + "\"");
                if (info._target != null)
                    p.print(" target=\"" + info._target + "\"");
                if (_tabStyle != null)
                    p.print(" class=\"" + _tabStyle.getStyleName() + "\"");
                p.print(">");
            }
            info._item.generateHTML(p, rowNo);
            p.println("</A></TD>");
        }
        p.println("</TR>");
        if (_showStripe) {
            p.print("<TR><TD colspan=" + _tabItems.size() + " height=" + (_height + 5));
            p.println(" bgColor=" + _bgColorActive + "></TD></TR>");
        }
        p.println("</TABLE>");
    }


    /**
	 * @param enabledTab  the enabledTab to set
	 * @uml.property  name="enabledTab"
	 */
    public void setEnabledTab(boolean enabled)
    {
        enabledTab = enabled;
    }

    /**
     * This method returns the href corresponding to active tab, or null if no active tab.
     */
    public String getActiveHref () {
        int i;
        int n = _tabItems.size();
        for (i = 0; i < n; i++) {
            TabInfo info = (TabInfo)_tabItems.elementAt(i);
            if (info._active)
                return info._href;
        }
        return null;
    }
    /**
     * This method returns the HTML color value for the active tab background.
     */
    public String getTabActiveColor () {
        return _bgColorActive;
    }
    /**
     * This method returns the name of the image file used to display the tab border.
     */
    public String getTabBorderImage () {
        return _tabImageBorder;
    }
    /**
     * This method returns the HTML color value used for the (non-active) tab background.
     * @return java.lang.String
     */
    public String getTabColor () {
        return _bgColor;
    }
    /**
     * This method returns the height in pixels of the tab folder, not counting the optional stripe.
     */
    public int getTabHeight () {
        return _height;
    }
    /**
     * This method returns the name of the image file for the left side of each tab.
     * @return java.lang.String
     */
    public String getTabLeftImage () {
        return _tabImageLeft;
    }
    /**
     * This method returns the name of the image file for the right side of each tab.
     * @return java.lang.String
     */
    public String getTabRightImage () {
        return _tabImageRight;
    }
    /**
     * This method returns the HTML color value used for the (non-active) tab background.
     * @return java.lang.String
     */
    public HtmlStyle getTabStyle () {
        return _tabStyle;
    }
    /**
     * This method returns the property theme for the component.
     */
    public String getTheme() {
        return _theme;
    }
    /**
     * This method sets the tab with the given href to be "active", i.e. displayed using the active display attributes.
     * @param href java.lang.String
     */
    public void setActiveHref (String href) {
        int i;
        int n = _tabItems.size();
        int j;
        // Strip trailing parameters from string to compare.
        if ((j = href.indexOf('?')) >= 0)
            href = href.substring(0, j);
        for (i = 0; i < n; i++) {
            TabInfo info = (TabInfo)_tabItems.elementAt(i);
            HtmlText ht;
            // Strip trailing parameters from string to compare.
            String prefix;
            if ((j = info._href.indexOf('?')) >= 0)
                prefix = info._href.substring(0, j);
            else
                prefix = info._href;
            if (info._active) {
                if (prefix.equals(href))
                // Nothing to do.
                    return;
                // Clear active attributes
                info._active = false;
                if (info._item instanceof HtmlText) {
                    ht = (HtmlText)info._item;
                    ht.setFontStartTag(_tabFontStartTag);
                    ht.setFontEndTag(_tabFontEndTag);
                }
            }
            else if (prefix.equals(href)) {
                info._active = true;
                if (info._item instanceof HtmlText) {
                    ht = (HtmlText)info._item;
                    ht.setFontStartTag(_tabFontActiveStartTag);
                    ht.setFontEndTag(_tabFontActiveEndTag);
                }
            }
        }
    }
    /**
     * This method sets the HTML color value for the active tab background.
     * @param value java.lang.String
     */
    public void setTabActiveColor (String value) {
        _bgColorActive = value;
    }
    /**
     * This method sets the HTML font tags to use for text in the active tab.
     * @param tagStart java.lang.String Start font tag
     * @param tagEnd java.lang.String End font tag
     */
    public void setTabActiveFont (String tagStart, String tagEnd) {
        _tabFontActiveStartTag = tagStart;
        _tabFontActiveEndTag = tagEnd;
    }
    /**
     * This method sets the image file used for tab borders.
     * @param image java.lang.String
     */
    public void setTabBorderImage (String image) {
        _tabImageBorder = image;
    }
    /**
     * This method sets the HTML color value for the (non-active) tab background.
     * @param value java.lang.String
     */
    public void setTabColor (String value) {
        _bgColor = value;
    }
    /**
     * This method sets the HTML font tags for text in each (non-active) tab.
     * @param tagStart java.lang.String Start font tag
     * @param tagEnd java.lang.String End font tag
     */
    public void setTabFont (String tagStart, String tagEnd) {
        _tabFontStartTag = tagStart;
        _tabFontEndTag = tagEnd;
    }
    /**
     * This method sets the height in pixels of the tab folder, not counting the optional stripe.
     * @param height int
     */
    public void setTabHeight (int height) {
        _height = height;
    }
    /**
     * This method sets the image file for the left side of each tab.
     * @param image java.lang.String
     */
    public void setTabLeftImage (String image) {
        _tabImageLeft = image;
    }
    /**
     * This method sets the image file for the right side of each tab.
     * @param image java.lang.String
     */
    public void setTabRightImage (String image) {
        _tabImageRight = image;
    }
    /**
     * This method sets the HTML color value for the active tab background.
     */
    public void setTabStyle(String sStyle) {
        _tabStyle = new HtmlStyle(getName()+"Style", HtmlStyle.ANCHOR_TYPE,
                sStyle, getPage());
        getPage().add(_tabStyle);
    }
    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {
        Props props = getPage().getPageProperties();
        // Now overload defaults as necessary.
        String s;
        if ((s = props.getThemeProperty(theme,Props.TAB_COLOR)) != null)
            _bgColor = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_ACTIVE_COLOR)) != null)
            _bgColorActive = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_FONT_START_TAG)) != null)
            _tabFontStartTag = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_FONT_END_TAG)) != null)
            _tabFontEndTag = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_ACTIVE_FONT_START_TAG)) != null)
            _tabFontActiveStartTag = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_ACTIVE_FONT_END_TAG)) != null)
            _tabFontActiveEndTag = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_LEFT_IMAGE)) != null)
            _tabImageLeft = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_RIGHT_IMAGE)) != null)
            _tabImageRight = s;
        if ((s = props.getThemeProperty(theme,Props.TAB_BORDER_IMAGE)) != null)
            _tabImageBorder = s;
        if ((s = props.getThemeProperty(theme,Props.ALL_TAB_WIDTH)) != null)
            _width = s;

        int n = props.getThemeIntProperty(theme,Props.TAB_HEIGHT);
        if (n != -1)
            _height = n;
        // NOTE: we can use getBooleanProperty here because the default for _showStripe is false.
        _showStripe = props.getThemeBooleanProperty(theme,Props.TAB_SHOW_STRIPE);

        _theme = theme;
    }
    /**
     * This method determines whether a horizontal stripe below the tab folder,  in the same color as the active
     * background, is displayed.
     * @param show boolean
     */
    public void showStripe (boolean show) {
        _showStripe = show;
    }

    /**
     * Set active tab number
     * @ author Ian Booth
     */
    public void setActiveTab (int tabNr) {
        int i;
        int n = _tabItems.size();
        for (i = 0; i < n; i++) {
            TabInfo info = (TabInfo)_tabItems.elementAt(i);
            info._active = i+1==tabNr;
            if (info._item instanceof HtmlText) {
                HtmlText ht = (HtmlText)info._item;
                if( info._active ) {
                    ht.setFontStartTag(_tabFontActiveStartTag);
                    ht.setFontEndTag(_tabFontActiveEndTag);
                } else {
                    ht.setFontStartTag(_tabFontStartTag);
                    ht.setFontEndTag(_tabFontEndTag);
                }
            }
        }
    }

    /**
     * get active tab number
     * @ author Ian Booth
     */
    public int getActiveTab () {
        int n = _tabItems.size();
        for (int i = 0; i < n; i++) {
            TabInfo info = (TabInfo)_tabItems.elementAt(i);
            if(info._active)
            {
                return i + 1;
            }
        }
        return 1;
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String _imagePath) {
        this._imagePath = _imagePath;
    }

    public String getWidth() {
        return _width;
    }

    public void setWidth(String _width) {
        this._width = _width;
    }
}
