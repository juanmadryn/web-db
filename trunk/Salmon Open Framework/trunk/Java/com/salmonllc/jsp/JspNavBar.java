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
import com.salmonllc.properties.*;
import com.salmonllc.util.*;
import java.util.*;


/**
 * This object is used to render a Navigation Menu in a JSP Page
 */
public class JspNavBar extends JspContainer implements Constants, java.io.Serializable
{
    private static final String CUSTOMGROUPHREFSTYLE  = "CUSTOMGROUPHREFSTYLE";
    private static final String CUSTOMGROUPHOVERSTYLE = "CUSTOMGROUPHOVERSTYLE";
    Hashtable                   _nav;
    HtmlScript                  _imagesScript;
    HtmlTable                   _table;
    String                      _groupname;
    String                      _navWidthPercent      = null;

    //	String _selectedItemMarkerImg,_selectedItemMarkerMouseOverImg,_itemMarkerImg,_itemTitleMarkerImg,_itemMarkerMouserOverImg;
    Vector  _groups;
    Vector  _subItems;
    Vector  _vPopups;
    boolean _autoSelectNav   = true;
    boolean _bHorizontalMode = false;

    // If a new mode is added be sure to define it sequentially.
    boolean _dreamMode          = false;
    boolean _showSubMenuMarker  = true;
    int     _navWidth;
    int     _selectedGroupIndex = -1;
    int     _selectedItemIndex  = -1;
    int     _sizeOption         = 0;

    //
    private String _align           = null;
    private String _anchorPrefixCss = "A.";

    //////////////
    //
    private String  _bgColor                     = "";
    private String  _cellBgColor;
    private String  _groupCellBgColor;
    private String  _groupHoverBgColor;
    private String  _groupHoverStyle;
    private String  _groupHrefStyle;
    private String  _groupSpacerImage;
    private String  _hSpaceImage;
    private String  _height;
    private String  _hoverBgColor;
    private String  _hoverStyle;
    private String  _hrefStyle;
    private String  _lastSubMenuAdded;
    private String  _markerImage;
    private String  _markerOverImage;
    private String  _sPctSign                    = "%";
    private String  _selectedBgColor;
    private String  _selectedHoverBgColor;
    private String  _selectedHoverStyle;
    private String  _selectedMarkerImage;
    private String  _selectedStyle;
    private String  _showPopupBgColor;
    private String  _showPopupHoverStyle;
    private String  _showPopupImage;
    private String  _showPopupSelectedHoverStyle;
    private String  _showPopupSelectedStyle;
    private String  _showPopupStyle;
    private String  _subHoverBgColor;
    private String  _subHoverStyle;
    private String  _subHrefStyle;
    private String  _subMenuBgColor;
    private String  _textImage;
    private String  _textStyle;
    private String  _theme;
    private String  _vSpaceImage;
    private String  _width;
    private boolean _bGenerateScripts            = true;
    private boolean _oneSelected                 = false;
    private int     _border                      = -1;
    private int     _cellPadding                 = -1;
    private int     _cellSpacing                 = -1;
    private int     _cols                        = -1;
    private int     _hSpace                      = -1;
    private int     _lastGroupAdded;
    private int     _vSpace                      = -1;

    /**
     * This constructor creates a NavigationMenu using properties from a group with a groupname of default.
     *
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public JspNavBar(String name, HtmlPage p)
    {
        this(name, null, p);
    }

    /**
     * This constructor creates a NavigationMenu using properties from a group with a groupname of default.
     *
     * @param name java.lang.String
     * @param theme DOCUMENT ME!
     * @param p com.salmonllc.html.HtmlPage
     */
    public JspNavBar(String name, String theme, HtmlPage p)
    {
        super(name, p);
        _groups   = new Vector();
        _subItems = new Vector();
        p.setDocumentType("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");

        p.addNavBar(this);
        setTheme(theme);
    }

    /**
     * Sets the alignment property for the table.
     *
     * @param align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
     */
    public void setAlign(String align)
    {
        _align = align;
    }

    public void setAnchorPrefixCss(String anchorPrefixCss)
    {
        _anchorPrefixCss = anchorPrefixCss;
    }

    public String getAnchorPrefixCss()
    {
        return _anchorPrefixCss;
    }

    /**
     * Sets whether or not the navbar will generate extra url parameters that automatically select another nav item if the user clicks on it
     *
     * @param autoSelect DOCUMENT ME!
     */
    public void setAutoSelectNavbar(boolean autoSelect)
    {
        _autoSelectNav = autoSelect;
    }

    /**
     * Sets the background color for the table.
     *
     * @param value DOCUMENT ME!
     */
    public void setBgcolor(String value)
    {
        _bgColor = value;
    }

    /**
     * Sets the border width for the table.
     *
     * @param border DOCUMENT ME!
     */
    public void setBorder(int border)
    {
        _border = border;
    }

    /**
     * This method sets the NavBar Item Cell BG Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setCellBgColor(String val)
    {
        _cellBgColor = val;
    }

    /**
     * Sets the cell padding for the table.
     *
     * @param value DOCUMENT ME!
     */
    public void setCellPadding(int value)
    {
        _cellPadding = value;
    }

    /**
     * Sets the cell spacing for the table.
     *
     * @param value DOCUMENT ME!
     */
    public void setCellSpacing(int value)
    {
        _cellSpacing = value;
    }

    /**
     * Sets the cell padding for the table.
     *
     * @param value DOCUMENT ME!
     */
    public void setCols(int value)
    {
        _cols = value;
    }

    /*
     * In the horizonal nav bar display the first level nav bars under the groups. others will expand to the right.
     */
    public boolean isFirstLevelSubMenu(String subMenu)
    {
        for (int i = 1; i <= _groups.size(); i++)
        {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
            {
                continue;
            }

            for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
            {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                {
                    continue;
                }

                String sItemName = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;

                if ((sItemName != null) && sItemName.equals(subMenu))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param bGenScript DOCUMENT ME!
     */
    public void setGenerateScripts(boolean bGenScript)
    {
        _bGenerateScripts = bGenScript;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isGenerateScripts()
    {
        return _bGenerateScripts;
    }

    /**
     * This method sets the Group Cell BG Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setGroupCellBgColor(String val)
    {
        _groupCellBgColor = val;
    }

    /**
     * This method sets the Group Cell BG Color.
     *
     * @param groupIdx DOCUMENT ME!
     * @param bgColor DOCUMENT ME!
     */
    public void setGroupCellBgColor(int groupIdx, String bgColor)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);
            group._bgColor = bgColor;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get Is Group Expanded
     *
     * @param groupName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isGroupExpanded(String groupName)
    {
        boolean isExpanded = false;

        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    isExpanded = group._expanded;

                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return isExpanded;
    }

    /**
     * Set Group Expansion by groupName
     *
     * @param groupName DOCUMENT ME!
     */
    public void setGroupExpansion(String groupName)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group   group    = (Group) _groups.elementAt(i);
                boolean expanded = group._expanded;

                if (group._name.equals(groupName))
                {
                    if (expanded)
                    {
                        contractGroup(groupName);
                    }
                    else
                    {
                        expandGroup(groupName);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the Group Cell Hover BG Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setGroupHoverBgColor(String val)
    {
        _groupHoverBgColor = val;
    }

    /**
     * This method sets the Group Hover Style.
     *
     * @param groupIdx int index of the group
     * @param groupHoverStyle String group Hover Style.
     */
    public void setGroupHoverStyle(int groupIdx, String groupHoverStyle)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);
            group._groupHoverStyle = groupHoverStyle;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the Group Cell Hover Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setGroupHoverStyle(String val)
    {
        _groupHoverStyle = val;
    }

    /**
     * This method sets the Group Href Style.
     *
     * @param groupIdx int index of the group
     * @param groupHrefStyle String group Href Style.
     */
    public void setGroupHrefStyle(int groupIdx, String groupHrefStyle)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);
            group._groupHrefStyle = groupHrefStyle;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the Group Cell Href Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setGroupHrefStyle(String val)
    {
        _groupHrefStyle = val;
    }

    /**
     * Get Group Index by groupName
     *
     * @param groupName is the group name
     *
     * @return DOCUMENT ME!
     */
    public int getGroupIndex(String groupName)
    {
        int groupIdx = -1;

        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    groupIdx = i;

                    break;
                }
            }

            return groupIdx;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return groupIdx;
        }
    }

    /**
     * This method sets the Group to a state of selected. This is used to give the group the same style as when it is rolled over.
     *
     * @param groupIdx - index of the group to set selected
     * @param selected - status true or false
     */
    public void setGroupSelected(int groupIdx, boolean selected)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);
            group._selected = selected;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setGroupSpacerImage(String val)
    {
        _groupSpacerImage = translateSiteMapURL(val);
    }

    /**
     * Set Group Title to be displayed by groupName
     *
     * @param groupName
     * @param titleText is the Text the Group should display.
     */
    public void setGroupTitle(String groupName, String titleText)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    group._groupTitle = titleText;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get Group Title Text by group Index
     *
     * @param groupIdx is the group index
     *
     * @return DOCUMENT ME!
     */
    public String getGroupTitle(int groupIdx)
    {
        String groupTitle = null;

        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);
            groupTitle = group._groupTitle;

            return groupTitle;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return groupTitle;
        }
    }

    /**
     * Sets the horizontal margin for the table.
     *
     * @param val DOCUMENT ME!
     */
    public void setHSpace(int val)
    {
        _hSpace = val;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setHSpaceImage(String val)
    {
        _hSpaceImage = translateSiteMapURL(val);
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setHeight(String val)
    {
        _height = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @param bHorizontalMode DOCUMENT ME!
     */
    public void setHorizontalMode(boolean bHorizontalMode)
    {
        this._bHorizontalMode = bHorizontalMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean getHorizontalMode()
    {
        return _bHorizontalMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isHorizontalMode()
    {
        return _bHorizontalMode;
    }

    /**
     * This method sets the NavBar Item MouseOver Cell BG Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setHoverBgColor(String val)
    {
        _hoverBgColor = val;
    }

    /**
     * This method sets the NavBar Item MouseOver Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setHoverStyle(String val)
    {
        _hoverStyle = val;
    }

    /**
     * Set Href for a Group Item
     *
     * @param groupIndex is the group index of the entry to change the HRef of.
     * @param itemIndex is the item index of the entry to change the HRef of.
     * @param href is the new URL for the HREF of the link.
     */
    public void setHref(int groupIndex, int itemIndex, String href)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._href = href;

            //	createTable();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Href for a Group
     *
     * @param groupName is the group name of the entry to change the HRef of.
     * @param href is the new URL for the HREF of the link.
     */
    public void setHref(String groupName, String href)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    group._href = href;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Href for a Group
     *
     * @param groupIndex is the group index of the entry to change the HRef of.
     * @param href is the new URL for the HREF of the link.
     */
    public void setHref(int groupIndex, String href)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            group._href = href;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the NavBar Item Href Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setHrefStyle(String val)
    {
        _hrefStyle = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isIE5()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) && (getPage().getBrowserVersion() < 5));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isIE6()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) && (getPage().getBrowserVersion() > 5) && (getPage().getBrowserVersion() < 7));
    }

    /**
     * Set the Background color of a Group Item
     *
     * @param groupIndex is the group index of the entry .
     * @param itemIndex is the item index of the entry .
     * @param color is to indicate what Background color the item should show.
     */
    public void setItemBgColor(int groupIndex, int itemIndex, String color)
    {
        try
        {
            Group group       = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._itemBgcolor = color;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the Last Group added.
     *
     * @param val DOCUMENT ME!
     */
    public void setLastGroupAdded(int val)
    {
        _lastGroupAdded = val;
    }

    /**
     * This method sets the Last Group added.
     *
     * @return DOCUMENT ME!
     */
    public int getLastGroupAdded()
    {
        return _lastGroupAdded;
    }

    /**
     * This method sets the Last Group added.
     *
     * @param val DOCUMENT ME!
     */
    public void setLastSubMenuAdded(String val)
    {
        _lastSubMenuAdded = val;
    }

    /**
     * This method sets the Last Group added.
     *
     * @return DOCUMENT ME!
     */
    public String getLastSubMenuAdded()
    {
        return _lastSubMenuAdded;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setMarkerImage(String val)
    {
        _markerImage = translateSiteMapURL(val);
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setMarkerOverImage(String val)
    {
        _markerOverImage = translateSiteMapURL(val);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isNetscape4()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() < 4));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isNetscape6()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() == 6));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isNetscape7()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() == 7));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isNetscapeBelowV6()
    {
        return ((getPage().getBrowserType() == HtmlPageBase.BROWSER_NETSCAPE) && (getPage().getBrowserVersion() < 6));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Vector getPopupsVector()
    {
        _vPopups = new Vector();

        Vector vHidePopups = new Vector();

        for (int i = 1; i <= _groups.size(); i++)
        {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
            {
                continue;
            }

            for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
            {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                {
                    continue;
                }

                //String sItemName=((Item)((Group)_groups.elementAt(i-1))._items.elementAt(j-1))._name;
                String  sItemMenu      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;
                boolean sItemShowPopup = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._showPopup;

                if ((sItemMenu != null) && (_vPopups.indexOf(sItemMenu) < 0))
                {
                    if (sItemShowPopup)
                    {
                        _vPopups.addElement(sItemMenu);
                    }
                    else
                    {
                        vHidePopups.addElement(sItemMenu);
                    }
                }
            }
        }

        for (int k = 1; k <= _subItems.size(); k++)
        {
            boolean _sVisible  = ((SubItem) _subItems.elementAt(k - 1))._visible;
            String  subSubName = ((SubItem) _subItems.elementAt(k - 1))._subMenu;

            //String subGroupName = ((SubItem) _subItems.elementAt(k-1))._subMenuGroup;
            if ((subSubName != null) && (_vPopups.indexOf(subSubName) < 0) && _sVisible && (vHidePopups.indexOf(subSubName) == -1))
            {
                _vPopups.addElement(subSubName);
            }
        }

        return _vPopups;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setSelectedBgColor(String val)
    {
        _selectedBgColor = val;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setSelectedHoverBgColor(String val)
    {
        _selectedHoverBgColor = val;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setSelectedHoverStyle(String val)
    {
        _selectedHoverStyle = val;
    }

    /**
     * This selects the current page shown if it has a reference in the menu.
     */
    public void setSelectedItem()
    {
        clearSelectedItem();

        boolean outer_loop = false;

        for (int i = 0; i < _groups.size(); i++)
        {
            for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
            {
                String sHref = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j))._href;

                if (isPage(sHref))
                {
                    setSelectedItem((i + 1), (j + 1), true);
                    outer_loop = true;

                    break;
                }
            }

            if (outer_loop)
            {
                break;
            }
        }
    }

    /**
     * Set a Group Item to selected
     *
     * @param groupIndex int group index of item to select in menu.
     * @param itemIndex int item index of item to select in menu.
     * @param selected DOCUMENT ME!
     */
    public void setSelectedItem(int groupIndex, int itemIndex, boolean selected)
    {
        clearSelectedItem();

        Group group    = (Group) _groups.elementAt(groupIndex - 1);
        Item  item = (Item) group._items.elementAt(itemIndex - 1);
        item._selected = selected;
    }

    /**
     * This selects the current page shown if it has a reference in the menu.
     *
     * @param itemName DOCUMENT ME!
     */
    public void setSelectedItem(String itemName)
    {
        clearSelectedItem();

        if (!Util.isFilled(itemName))
        {
            return;
        }

        boolean outer_loop = false;

        for (int i = 0; i < _groups.size(); i++)
        {
            for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
            {
                String sName = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j))._name;

                if (sName.toUpperCase().equals(itemName.toUpperCase()))
                {
                    setSelectedItem((i + 1), (j + 1), true);
                    outer_loop = true;

                    break;
                }
            }

            if (outer_loop)
            {
                break;
            }
        }
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setSelectedMarkerImage(String val)
    {
        _selectedMarkerImage = translateSiteMapURL(val);
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setSelectedStyle(String val)
    {
        _selectedStyle = val;
    }

    /**
     * This sets the subItem to Selected.
     *
     * @param subItemName DOCUMENT ME!
     */
    public void setSelectedSubItem(String subItemName)
    {
        for (int i = 0; i < _subItems.size(); i++)
        {
            SubItem subItem = (SubItem) _subItems.elementAt(i);

            if (subItem._name.equals(subItemName))
            {
                clearSelectedSubItem();
                subItem._selected = true;

                return;
            }
        }

        return;
    }

    /**
     * Set whether the submenu of a Group item should have its submenu shown on the navbar or while hovering over the item.
     *
     * @param groupIndex is the group index of the entry to hide/show.
     * @param itemIndex is the item index of the entry to hide/show.
     * @param show is to indicate whether the item should show popup in navbar or on mouseover. True = On MouseOver False= In Navbar
     */
    public void setShowPopup(int groupIndex, int itemIndex, boolean show)
    {
        try
        {
            Group group     = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._showPopup = show;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set whether the submenu of a Group item should have its submenu shown on the navbar or while hovering over the item.
     *
     * @param groupName is the group of the entry to hide/show.
     * @param itemName is the item of the entry to hide/show.
     * @param show is to indicate whether the submenu should be shown in navbar or On Mouseover. True = On MouseOver False= In Navbar
     */
    public void setShowPopup(String groupName, String itemName, boolean show)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
                    {
                        Item item = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j));

                        if (item._name.equals(itemName))
                        {
                            item._showPopup = show;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the Show Popup In Navbar Background Color. If the background color is set A table will be inserted around the Popup items with this background color
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupBgColor(String val)
    {
        _showPopupBgColor = val;
    }

    /**
     * This method sets the Show Popup Hover Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupHoverStyle(String val)
    {
        _showPopupHoverStyle = val;
    }

    /**
     * This method sets the Show Popup Image
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupImage(String val)
    {
        _showPopupImage = val;
    }

    /**
     * This method sets the Show Popup Selected Hover Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupSelectedHoverStyle(String val)
    {
        _showPopupSelectedHoverStyle = val;
    }

    /**
     * This method sets the Show Popup Selected Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupSelectedStyle(String val)
    {
        _showPopupSelectedStyle = val;
    }

    /**
     * This method sets the Show Popup Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setShowPopupStyle(String val)
    {
        _showPopupStyle = val;
    }

    /**
     * Show the item marker in the sub menus. In the horizontal mode you may not want to show the marker in the sub menus. Default is true.
     *
     * @param showSubMenuMarker boolean .
     */
    public void setShowSubMenuMarker(boolean showSubMenuMarker)
    {
        this._showSubMenuMarker = showSubMenuMarker;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean getShowSubMenuMarker()
    {
        return _showSubMenuMarker;
    }

    /**
     * Set Size Option
     *
     * @param sizeoption int
     */
    public void setSizeOption(int sizeoption)
    {
        _sizeOption = sizeoption;
        _table.setSizeOption(sizeoption);
    }

    /**
     * This method sets the NavBar SubItem MouseOver Cell BG Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setSubHoverBgColor(String val)
    {
        _subHoverBgColor = val;
    }

    /**
     * This method sets the NavBar SubItem MouseOver Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setSubHoverStyle(String val)
    {
        _subHoverStyle = val;
    }

    /**
     * This method sets the NavBar SubItem Href Style.
     *
     * @param val DOCUMENT ME!
     */
    public void setSubHrefStyle(String val)
    {
        _subHrefStyle = val;
    }

    /**
     * This method sets the Sub Menu Default Background Color.
     *
     * @param val DOCUMENT ME!
     */
    public void setSubMenuBgColor(String val)
    {
        _subMenuBgColor = val;
    }

    /**
     * Set Background color of an item on the Submenu
     *
     * @param subItemName is the sub Item Name .
     * @param bgColor is the color to set the Background Color of the Item.
     */
    public void setSubMenuItemBgColor(String subItemName, String bgColor)
    {
        try
        {
            for (int i = 0; i < _subItems.size(); i++)
            {
                SubItem subItem = (SubItem) _subItems.elementAt(i);

                if (subItem._name.equals(subItemName))
                {
                    subItem._subItemBgColor = bgColor;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set SubMenu visibility
     *
     * @param itemName is the item name of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setSubMenuItemVisible(String itemName, boolean visible)
    {
        try
        {
            for (int i = 0; i < _subItems.size(); i++)
            {
                SubItem subItem = (SubItem) _subItems.elementAt(i);

                if (subItem._name.equals(itemName))
                {
                    subItem._visible = visible;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Submenu visibility
     *
     * @param subMenuName is the subMenu  to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setSubMenuVisible(String subMenuName, boolean visible)
    {
        try
        {
            for (int i = 0; i < _subItems.size(); i++)
            {
                SubItem subItem = (SubItem) _subItems.elementAt(i);

                if (subItem._subMenuGroup.equals(subMenuName))
                {
                    subItem._visible = visible;

                    String subMenu  = subItem._subMenu;
                    String subGroup = subItem._subMenuGroup;

                    if (!subGroup.equals(subMenu))
                    {
                        setSubMenuVisible(subMenu, visible);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Target of a Group Item
     *
     * @param groupIndex is the group index of the entry to change the target of.
     * @param itemIndex is the item index of the entry to change the target of.
     * @param target is the new target associated with the link.
     */
    public void setTarget(int groupIndex, int itemIndex, String target)
    {
        try
        {
            Group group  = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._target = target;

            //	createTable();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*  String sItemKey=_groupname+".Group"+groupIndex+".Item"+itemIndex;
           HtmlContainer hc=(HtmlContainer)_nav.get(sItemKey);
           Enumeration enumera=hc.getComponents();
           while(enumera.hasMoreElements()) {
                 HtmlComponent hcp=(HtmlComponent)enumera.nextElement();
                 if (hcp instanceof HtmlLink) {
                   HtmlLink hl=(HtmlLink)hcp;
                   hl.setTarget(target);
                  }
            }*/
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setTextImage(String val)
    {
        _textImage = translateSiteMapURL(val);
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setTextStyle(String val)
    {
        _textStyle = val;
    }

    /**
     * This method sets the property theme for the component.
     *
     * @param theme The theme to use.
     */
    public void setTheme(String theme)
    {
        Props prop = getPage().getPageProperties();

        int   iBorder = prop.getThemeIntProperty(theme, Props.NAVBAR_BORDER);

        if (iBorder >= 0)
        {
            _border = iBorder;
        }

        String bgColor = prop.getThemeProperty(theme, Props.NAVBAR_BG_COLOR);

        if ((bgColor != null) && (bgColor.length() > 0))
        {
            _bgColor = bgColor;
        }

        _cellPadding                 = prop.getThemeIntProperty(theme, Props.NAVBAR_CELLPADDING);
        _cellSpacing                 = prop.getThemeIntProperty(theme, Props.NAVBAR_CELLSPACING);
        _align                       = prop.getThemeProperty(theme, Props.NAVBAR_ALIGN);
        _hSpace                      = prop.getThemeIntProperty(theme, Props.NAVBAR_H_SPACE);
        _vSpace                      = prop.getThemeIntProperty(theme, Props.NAVBAR_V_SPACE);
        _height                      = prop.getThemeProperty(theme, Props.NAVBAR_HEIGHT);
        _width                       = prop.getThemeProperty(theme, Props.NAVBAR_WIDTH);
        _hrefStyle                   = prop.getThemeProperty(theme, Props.NAVBAR_HREF_STYLE);
        _hoverStyle                  = prop.getThemeProperty(theme, Props.NAVBAR_HOVER_STYLE);
        _textStyle                   = prop.getThemeProperty(theme, Props.NAVBAR_TEXT_STYLE);
        _selectedStyle               = prop.getThemeProperty(theme, Props.NAVBAR_SELECTED_STYLE);
        _selectedHoverStyle          = prop.getThemeProperty(theme, Props.NAVBAR_SELECTED_HOVER_STYLE);
        _showPopupStyle              = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_STYLE);
        _showPopupHoverStyle         = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_HOVER_STYLE);
        _showPopupSelectedStyle      = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_SELECTED_STYLE);
        _showPopupSelectedHoverStyle = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_SELECTED_HOVER_STYLE);
        _cellBgColor                 = prop.getThemeProperty(theme, Props.NAVBAR_CELL_BG_COLOR);
        _groupCellBgColor            = prop.getThemeProperty(theme, Props.NAVBAR_GROUP_CELL_BG_COLOR);
        _groupHrefStyle              = prop.getThemeProperty(theme, Props.NAVBAR_GROUP_HREF_STYLE);
        _groupHoverStyle             = prop.getThemeProperty(theme, Props.NAVBAR_GROUP_HOVER_STYLE);
        _groupHoverBgColor           = prop.getThemeProperty(theme, Props.NAVBAR_GROUP_HOVER_BG_COLOR);
        _hoverBgColor                = prop.getThemeProperty(theme, Props.NAVBAR_HOVER_BG_COLOR);
        _selectedBgColor             = prop.getThemeProperty(theme, Props.NAVBAR_SELECTED_BG_COLOR);
        _selectedHoverBgColor        = prop.getThemeProperty(theme, Props.NAVBAR_SELECTED_HOVER_BG_COLOR);
        _showPopupBgColor            = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_BG_COLOR);
        _subMenuBgColor              = prop.getThemeProperty(theme, Props.NAVBAR_SUBMENU_BG_COLOR);
        _markerImage                 = prop.getThemeProperty(theme, Props.NAVBAR_MARKER_IMAGE);
        _markerOverImage             = prop.getThemeProperty(theme, Props.NAVBAR_MARKER_OVER_IMAGE);
        _selectedMarkerImage         = prop.getThemeProperty(theme, Props.NAVBAR_SELECTED_MARKER_IMAGE);
        _textImage                   = prop.getThemeProperty(theme, Props.NAVBAR_TEXT_IMAGE);
        _vSpaceImage                 = prop.getThemeProperty(theme, Props.NAVBAR_V_SPACE_IMAGE);
        _hSpaceImage                 = prop.getThemeProperty(theme, Props.NAVBAR_H_SPACE_IMAGE);
        _groupSpacerImage            = prop.getThemeProperty(theme, Props.NAVBAR_GROUP_SPACER_IMAGE);
        _showPopupImage              = prop.getThemeProperty(theme, Props.NAVBAR_SHOW_POPUP_IMAGE);

        //        _subCellBgColor              = prop.getThemeProperty(theme, Props.NAVBAR_SUB_CELL_BG_COLOR);
        _subHoverBgColor = prop.getThemeProperty(theme, Props.NAVBAR_SUB_HOVER_BG_COLOR);
        _subHoverStyle   = prop.getThemeProperty(theme, Props.NAVBAR_SUB_HOVER_STYLE);
        _subHrefStyle    = prop.getThemeProperty(theme, Props.NAVBAR_SUB_HREF_STYLE);

        _theme = theme;
    }

    /**
     * Set Title of a Group Item
     *
     * @param groupIndex is the group index of the entry to change the caption of.
     * @param itemIndex is the item index of the entry to change the caption of.
     * @param title is the new caption for the item.
     */
    public void setTitle(int groupIndex, int itemIndex, String title)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._title = title;

            //	createTable();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*  String sItemKey=_groupname+".Group"+groupIndex+".Item"+itemIndex;
           HtmlContainer hc=(HtmlContainer)_nav.get(sItemKey);
           Enumeration enumera=hc.getComponents();
           while(enumera.hasMoreElements()) {
                 HtmlComponent hcp=(HtmlComponent)enumera.nextElement();
                 if (hcp instanceof HtmlText) {
                   HtmlText ht=(HtmlText)hcp;
                   ht.setText(title);
                  }
                 if (hcp instanceof HtmlRaw) {
                   HtmlRaw hr=(HtmlRaw)hcp;
                   hr.setHtml(title);
                  }
                 if (hcp instanceof HtmlLink) {
                   Enumeration linkEnum=((HtmlLink)hcp).getComponents();
                   while(linkEnum.hasMoreElements()) {
                         HtmlComponent hcpLink=(HtmlComponent)enumera.nextElement();
                     if (hcpLink instanceof HtmlText) {
                       HtmlText ht=(HtmlText)hcp;
                       ht.setText(title);
                      }
                     if (hcpLink instanceof HtmlRaw) {
                       HtmlRaw hr=(HtmlRaw)hcp;
                       hr.setHtml(title);
                      }
                    }
                  }
            }*/
    }

    /**
     * Sets the vertical margin for the table.
     *
     * @param val DOCUMENT ME!
     */
    public void setVSpace(int val)
    {
        _vSpace = val;
    }

    /**
     * This method gets the minimum height of the table in pixels.
     *
     * @param val DOCUMENT ME!
     */
    public void setVSpaceImage(String val)
    {
        _vSpaceImage = translateSiteMapURL(val);
    }

    /**
     * Set Group Item Visibility
     *
     * @param groupIndex is the group index of the entry to hide/show.
     * @param itemIndex is the item index of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(int groupIndex, int itemIndex, boolean visible)
    {
        try
        {
            Group group   = (Group) _groups.elementAt(groupIndex - 1);
            Item  item = (Item) group._items.elementAt(itemIndex - 1);
            item._visible = visible;

            //	createTable();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Group visibilty
     *
     * @param groupIndex is the group index of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(int groupIndex, boolean visible)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            group._visible = visible;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Group Item visibility
     *
     * @param groupName is the group name of the entry to hide/show.
     * @param itemName is the item name of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(String groupName, String itemName, boolean visible)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
                    {
                        Item item = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j));

                        if (item._name.equals(itemName))
                        {
                            item._visible = visible;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Group visibility
     *
     * @param groupName is the group name of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(String groupName, boolean visible)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    group._visible = visible;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set Width
     *
     * @param val String
     */
    public void setWidth(String val)
    {
        _width = val;

        //	_table.setWidth(width);
    }

    /**
     * Get Width
     *
     * @return width String
     */
    public String getWidth()
    {
        return _width;
    }

    /**
     * Add a NavBar Item to the specified group
     *
     * @param name java.lang.String - Internal Name of Item
     * @param group int - Index of Group to add Item
     * @param title java.lang.String - Text To be Displayed
     * @param href java.lang.String - Href
     * @param target java.lang.String - Target
     * @param horizPadding int - Horizontal Padding to Be added In front of Displayed Text
     * @param subMenu java.lang.String - Name of Submenu for the Item
     */
    public void addGroupItem(String name, int group, String title, String href, String target, int horizPadding, String subMenu)
    {
        this.addGroupItem(name, group, title, href, target, horizPadding, null, subMenu, true);

        return;
    }

    /**
     * Add a NavBar Item to the specified group
     *
     * @param name java.lang.String - Internal Name of Item
     * @param group int - Index of Group to add Item
     * @param title java.lang.String - Text To be Displayed
     * @param href java.lang.String - Href
     * @param target java.lang.String - Target
     * @param horizPadding int - Horizontal Padding to Be added In front of Displayed Text
     * @param bgColor java.lang.String - Background Color of Item .  Will override default
     * @param subMenu java.lang.String - Name of Submenu for the Item
     * @param showPopup boolean - Should Popup SubMenu Be shown inside the Navbar or Popup when Mouse Over
     */
    public void addGroupItem(String name, int group, String title, String href, String target, int horizPadding, String bgColor, String subMenu, boolean showPopup)
    {
        try
        {
            Item item = new Item();
            item._name         = name;
            item._title        = title;
            item._horizPadding = horizPadding;
            item._href         = href;
            item._target       = target;
            item._itemBgcolor  = bgColor;
            item._submenu      = subMenu;
            item._showPopup    = showPopup;
            setLastSubMenuAdded(subMenu);

            ((Group) _groups.elementAt(group - 1))._items.addElement(item);
        }
        catch (Exception e)
        {
        }

        return;
    }

    /**
     * Add a NavBar Item to the last group Added
     *
     * @param name java.lang.String - Internal Name of Item
     * @param title java.lang.String - Text To be Displayed
     * @param href java.lang.String - Href
     */
    public void addGroupItem(String name, String title, String href)
    {
        this.addGroupItem(name, title, href, null, 0);
    }

    /**
     * Add a NavBar Item to the last group Added
     *
     * @param name java.lang.String - Internal Name of Item
     * @param title java.lang.String - Text To be Displayed
     * @param href DOCUMENT ME!
     * @param horizPadding int - Horizontal Padding to Be added In front of Displayed Text
     */
    public void addGroupItem(String name, String title, String href, int horizPadding)
    {
        this.addGroupItem(name, title, href, null, horizPadding);
    }

    /**
     * Add a NavBar Item to the last group Added
     *
     * @param name java.lang.String - Internal Name of Item
     * @param title java.lang.String - Text To be Displayed
     * @param href java.lang.String - Href
     * @param target java.lang.String - Target
     * @param horizPadding int - Horizontal Padding to Be added In front of Displayed Text
     */
    public void addGroupItem(String name, String title, String href, String target, int horizPadding)
    {
        this.addGroupItem(name, getLastGroupAdded(), title, href, target, horizPadding, null, null, true);

        return;
    }

    /**
     * Add Sub Item to a navBar Item
     *
     * @param name DOCUMENT ME!
     * @param title DOCUMENT ME!
     * @param href DOCUMENT ME!
     * @param target DOCUMENT ME!
     * @param horizPadding DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param subMenuGroup DOCUMENT ME!
     */
    public void addSubItem(String name, String title, String href, String target, int horizPadding, String subMenu, String subMenuGroup)
    {
        this.addSubItem(name, title, href, target, horizPadding, subMenu, subMenuGroup, null);

        return;
    }

    /**
     * Add subItem to a navbar item
     *
     * @param name DOCUMENT ME!
     * @param title DOCUMENT ME!
     * @param href DOCUMENT ME!
     * @param target DOCUMENT ME!
     * @param horizPadding DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param subMenuGroup DOCUMENT ME!
     * @param bgColor DOCUMENT ME!
     */
    public void addSubItem(String name, String title, String href, String target, int horizPadding, String subMenu, String subMenuGroup, String bgColor)
    {
        try
        {
            //Check what was passed.  If null make the correct adjustments to the Sub Menu and Sub Menu Group
            String _Menu      = "";
            String _MenuGroup = "";

            if (subMenu != null)
            {
                if (subMenuGroup != null)
                {
                    _Menu      = subMenu;
                    _MenuGroup = subMenuGroup;
                }
                else
                {
                    _Menu      = subMenu;
                    _MenuGroup = getLastSubMenuAdded();
                }
            }
            else if (subMenuGroup != null)
            {
                _Menu      = subMenuGroup;
                _MenuGroup = subMenuGroup;
            }
            else
            {
                _Menu      = getLastSubMenuAdded();
                _MenuGroup = getLastSubMenuAdded();
            }

            //Add SubItem
            SubItem subItem = new SubItem();
            subItem._name           = name;
            subItem._title          = title;
            subItem._horizPadding   = horizPadding;
            subItem._href           = href;
            subItem._target         = target;
            subItem._subItemBgColor = bgColor;
            subItem._subMenu        = _Menu;
            subItem._subMenuGroup   = _MenuGroup;
            _subItems.addElement(subItem);
        }
        catch (Exception e)
        {
        }

        return;
    }

    /**
     * This method clears all the selected groups.
     */
    public void clearSelectedGroups()
    {
        for (int i = 0; i < _groups.size(); i++)
        {
            ((Group) _groups.elementAt(i))._selected = false;
        }
    }

    /**
     * This method clears the selected menu item.
     */
    public void clearSelectedItem()
    {
        for (int i = 0; i < _groups.size(); i++)
        {
            for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
            {
                ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j))._selected = false;
            }
        }
    }

    /**
     * This method clears the selected menu item.
     */
    public void clearSelectedSubItem()
    {
        for (int i = 0; i < _subItems.size(); i++)
        {
            SubItem subItem = (SubItem) _subItems.elementAt(i);
            subItem._selected = false;
        }
    }

    /**
     * Collapse all group items in the Navbar
     */
    public void contractAllGroups()
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                for (int j = 0; j < group._items.size(); j++)
                {
                    Item item = ((Item) group._items.elementAt(j));
                    item._visible = false;

                    String subMenu = item._submenu;

                    if (subMenu != null)
                    {
                        setSubMenuVisible(subMenu, false);
                    }
                }

                group._expanded = false;
            }
        }
        catch (Exception e)
        {
            com.salmonllc.util.MessageLog.writeErrorMessage("ERROR:Contract All Groups", e, this);
            e.printStackTrace();
        }
    }

    /**
     * Collapse Group specified
     *
     * @param groupIdx is the group index of the entry to hide/show.
     */
    public void contractGroup(int groupIdx)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);

            for (int j = 0; j < group._items.size(); j++)
            {
                Item item = ((Item) group._items.elementAt(j));
                item._visible = false;

                String subMenu = item._submenu;

                if (subMenu != null)
                {
                    setSubMenuVisible(subMenu, false);
                }
            }

            group._expanded = false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Collapse Group specified by groupName
     *
     * @param groupName is the group name of the entry to hide/show.
     */
    public void contractGroup(String groupName)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
                    {
                        Item item = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j));
                        item._visible = false;

                        String subMenu = item._submenu;

                        if (subMenu != null)
                        {
                            setSubMenuVisible(subMenu, false);
                        }
                    }

                    group._expanded = false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create a Group Item
     *
     * @param name java.lang.String
     * @param image DOCUMENT ME!
     * @param href DOCUMENT ME!
     * @param groupTitle DOCUMENT ME!
     * @param vertPadding DOCUMENT ME!
     */
    public void createGroup(String name, String image, String href, String groupTitle, int vertPadding)
    {
        this.createGroup(name, image, href, groupTitle, vertPadding, null, null, null);

        return;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     * @param image DOCUMENT ME!
     * @param href DOCUMENT ME!
     * @param groupTitle DOCUMENT ME!
     * @param vertPadding DOCUMENT ME!
     * @param bgColor DOCUMENT ME!
     */
    public void createGroup(String name, String image, String href, String groupTitle, int vertPadding, String bgColor)
    {
        this.createGroup(name, image, href, groupTitle, vertPadding, bgColor, null, null);

        return;
    }

    /**
     * Create a Group Item
     *
     * @param name java.lang.String
     * @param image DOCUMENT ME!
     * @param href DOCUMENT ME!
     * @param groupTitle DOCUMENT ME!
     * @param vertPadding DOCUMENT ME!
     * @param bgColor DOCUMENT ME!
     * @param groupHrefStyle The overriding Group Href Style
     * @param groupHoverStyle The overriding Group Hover Style
     */
    public void createGroup(String name, String image, String href, String groupTitle, int vertPadding, String bgColor, String groupHrefStyle, String groupHoverStyle)
    {
        try
        {
            Group group = new Group();
            group._name            = name;
            group._image           = image;
            group._groupTitle      = groupTitle;
            group._vertPadding     = vertPadding;
            group._href            = href;
            group._bgColor         = bgColor;
            group._groupHrefStyle  = groupHrefStyle;
            group._groupHoverStyle = groupHoverStyle;
            _groups.addElement(group);
            group._items = new Vector();
            setLastGroupAdded(_groups.indexOf(group) + 1);
        }
        catch (Exception e)
        {
        }

        return;
    }

    /**
     * Expand all Group Items
     */
    public void expandAllGroups()
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                for (int j = 0; j < group._items.size(); j++)
                {
                    Item item = ((Item) group._items.elementAt(j));
                    item._visible = true;

                    String subMenu = item._submenu;

                    if (subMenu != null)
                    {
                        setSubMenuVisible(subMenu, true);
                    }
                }

                group._expanded = true;
            }
        }
        catch (Exception e)
        {
            com.salmonllc.util.MessageLog.writeErrorMessage("ERROR:Expand All Groups", e, this);
            e.printStackTrace();
        }
    }

    /**
     * Expand Group item by group Index
     *
     * @param groupIdx is the group index of the entry to hide/show.
     */
    public void expandGroup(int groupIdx)
    {
        try
        {
            Group group = (Group) _groups.elementAt(groupIdx);

            for (int j = 0; j < group._items.size(); j++)
            {
                Item item = ((Item) group._items.elementAt(j));
                item._visible = true;

                String subMenu = item._submenu;

                if (subMenu != null)
                {
                    setSubMenuVisible(subMenu, true);
                }
            }

            group._expanded = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Expand Group item by name
     *
     * @param groupName is the group name of the entry to hide/show.
     */
    public void expandGroup(String groupName)
    {
        try
        {
            for (int i = 0; i < _groups.size(); i++)
            {
                Group group = (Group) _groups.elementAt(i);

                if (group._name.equals(groupName))
                {
                    for (int j = 0; j < ((Group) _groups.elementAt(i))._items.size(); j++)
                    {
                        Item item = ((Item) ((Group) _groups.elementAt(i))._items.elementAt(j));
                        item._visible = true;

                        String subMenu = item._submenu;

                        if (subMenu != null)
                        {
                            setSubMenuVisible(subMenu, true);
                        }
                    }

                    group._expanded = true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Generates the Html for the component. This method is called by the framework and should not be called directly
     *
     * @param p DOCUMENT ME!
     *
     * @throws java.io.IOException DOCUMENT ME!
     */
    public void generateHTML(TagWriter p) throws java.io.IOException
    {
        synchronized (this)
        {
            _dreamMode = p.getDreamWeaverConv();
            _vPopups   = null;

            //Generate Styles used in the navbar
            // hrefStyle  "Style of the Href Text when not Hovering"
            // hoverStyle  "Style of the Hover Text while Hovering"
            // groupHrefStyle  "Style of the Group Href Text when not Hovering"
            // groupHoverStyle  "Style of the Group Hover Text while Hovering"
            // textStyle   "Style of text if not an href"
            // selectedStyle "Style of href when selected"
            // selectedHoverStyle "Style of selected href while hovering"
            // groupCellBgColor  "BackGround Color of Group Cell when not Hovering"
            // grouphoverBgColor "BackGround color of Group cell while hovering"
            // cellBgColor  "BackGround Color of Cell when not Hovering"
            // hoverBgColor "BackGround color of cell while hovering"
            // selectedBgColor  "bg color of selected href"
            // selectedHoverBgColor "bg color of selected href while hovering"
            if (isNetscape4())
            {
                _sPctSign = "pct";
            }

            p.println("<STYLE>");

            String anchorPrefix = getAnchorPrefixCss();

            if (!isNullOrEmty(_hrefStyle))
            {
                p.println(anchorPrefix + getName() + HREFSTYLE + "{" + _hrefStyle + "}");
                p.println(anchorPrefix + getName() + HREFSTYLE + ":hover" + "{" + _hrefStyle + "}");
                p.println(anchorPrefix + getName() + HREFSTYLE + ":visited" + "{" + _hrefStyle + "}");
                p.println(anchorPrefix + getName() + HREFSTYLE + ":active" + "{" + _hrefStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + HREFSTYLE + "{ }");
            }

            if (!isNullOrEmty(_hoverStyle))
            {
                p.println(anchorPrefix + getName() + HOVERSTYLE + "{" + _hoverStyle + "}");
                p.println(anchorPrefix + getName() + HOVERSTYLE + ":hover" + "{" + _hoverStyle + "}");
                p.println(anchorPrefix + getName() + HOVERSTYLE + ":visited" + "{" + _hoverStyle + "}");
                p.println(anchorPrefix + getName() + HOVERSTYLE + ":active" + "{" + _hoverStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + HOVERSTYLE + "{ }");
            }

            if (!isNullOrEmty(_textStyle))
            {
                p.println("." + getName() + TEXTSTYLE + "{" + _textStyle + "}");
            }
            else
            {
                p.println("." + getName() + TEXTSTYLE + "{ }");
            }

            if (!isNullOrEmty(_selectedStyle))
            {
                p.println(anchorPrefix + getName() + SELECTEDSTYLE + "{" + _selectedStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDSTYLE + ":hover" + "{" + _selectedStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDSTYLE + ":visited" + "{" + _selectedStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDSTYLE + ":active" + "{" + _selectedStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SELECTEDSTYLE + "{ }");
            }

            if (!isNullOrEmty(_selectedHoverStyle))
            {
                p.println(anchorPrefix + getName() + SELECTEDHOVERSTYLE + "{" + _selectedHoverStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDHOVERSTYLE + ":hover" + "{" + _selectedHoverStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDHOVERSTYLE + ":visited" + "{" + _selectedHoverStyle + "}");
                p.println(anchorPrefix + getName() + SELECTEDHOVERSTYLE + ":active" + "{" + _selectedHoverStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SELECTEDHOVERSTYLE + "{ }");
            }

            if (!isNullOrEmty(_showPopupStyle))
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSTYLE + "{" + _showPopupStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSTYLE + ":hover" + "{" + _showPopupStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSTYLE + ":visited" + "{" + _showPopupStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSTYLE + ":active" + "{" + _showPopupStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSTYLE + "{ }");
            }

            if (!isNullOrEmty(_showPopupHoverStyle))
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPHOVERSTYLE + "{" + _showPopupHoverStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPHOVERSTYLE + ":hover" + "{" + _showPopupHoverStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPHOVERSTYLE + ":visited" + "{" + _showPopupHoverStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPHOVERSTYLE + ":active" + "{" + _showPopupHoverStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPHOVERSTYLE + "{ }");
            }

            if (!isNullOrEmty(_showPopupSelectedStyle))
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDSTYLE + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDSTYLE + ":hover" + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDSTYLE + ":visited" + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDSTYLE + ":active" + "{" + _showPopupSelectedStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDSTYLE + "{ }");
            }

            if (!isNullOrEmty(_showPopupSelectedHoverStyle))
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + ":hover" + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + ":visited" + "{" + _showPopupSelectedStyle + "}");
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + ":active" + "{" + _showPopupSelectedStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + "{ }");
            }

            if (!isNullOrEmty(_cellBgColor))
            {
                p.println("TD." + getName() + CELLBGSTYLE + "{BACKGROUND:" + _cellBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + CELLBGSTYLE + "{ }");
            }

            if (!isNullOrEmty(_hoverBgColor))
            {
                p.println("TD." + getName() + CELLHOVERBGSTYLE + "{BACKGROUND:" + _hoverBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + CELLHOVERBGSTYLE + "{ }");
            }

            if (!isNullOrEmty(_selectedBgColor))
            {
                p.println("TD." + getName() + SELECTEDBGSTYLE + "{BACKGROUND:" + _selectedBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + SELECTEDBGSTYLE + "{ }");
            }

            if (!isNullOrEmty(_selectedHoverBgColor))
            {
                p.println("TD." + getName() + SELECTEDHOVERBGSTYLE + "{BACKGROUND:" + _selectedHoverBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + SELECTEDHOVERBGSTYLE + "{ }");
            }

            if (!isNullOrEmty(_showPopupBgColor))
            {
                p.println("TD." + getName() + SHOWPOPUPBGSTYLE + "{BACKGROUND:" + _showPopupBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + SHOWPOPUPBGSTYLE + "{}");
            }

            if (!isNullOrEmty(_groupHrefStyle))
            {
                p.println(anchorPrefix + getName() + GROUPHREFSTYLE + "{" + _groupHrefStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHREFSTYLE + ":hover" + "{" + _groupHrefStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHREFSTYLE + ":visited" + "{" + _groupHrefStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHREFSTYLE + ":active" + "{" + _groupHrefStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + GROUPHREFSTYLE + "{}");
            }

            if (!isNullOrEmty(_groupHoverStyle))
            {
                p.println(anchorPrefix + getName() + GROUPHOVERSTYLE + "{" + _groupHoverStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHOVERSTYLE + ":hover" + "{" + _groupHoverStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHOVERSTYLE + ":visited" + "{" + _groupHoverStyle + "}");
                p.println(anchorPrefix + getName() + GROUPHOVERSTYLE + ":active" + "{" + _groupHoverStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + GROUPHOVERSTYLE + "{}");
            }

            if (!isNullOrEmty(_groupCellBgColor))
            {
                p.println("TD." + getName() + GROUPCELLBGSTYLE + "{BACKGROUND:" + _groupCellBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + GROUPCELLBGSTYLE + "{}");
            }

            if (!isNullOrEmty(_groupHoverBgColor))
            {
                p.println("TD." + getName() + GROUPHOVERBGSTYLE + "{BACKGROUND:" + _groupHoverBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + GROUPHOVERBGSTYLE + "{}");
            }

            if (!isNullOrEmty(_subHrefStyle))
            {
                p.println(anchorPrefix + getName() + SUBHREFSTYLE + "{" + _subHrefStyle + "}");
                p.println(anchorPrefix + getName() + SUBHREFSTYLE + ":hover" + "{" + _subHrefStyle + "}");
                p.println(anchorPrefix + getName() + SUBHREFSTYLE + ":visited" + "{" + _subHrefStyle + "}");
                p.println(anchorPrefix + getName() + SUBHREFSTYLE + ":active" + "{" + _subHrefStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SUBHREFSTYLE + "{ }");
            }

            if (!isNullOrEmty(_subHoverStyle))
            {
                p.println(anchorPrefix + getName() + SUBHOVERSTYLE + "{" + _subHoverStyle + "}");
                p.println(anchorPrefix + getName() + SUBHOVERSTYLE + ":hover" + "{" + _subHoverStyle + "}");
                p.println(anchorPrefix + getName() + SUBHOVERSTYLE + ":visited" + "{" + _subHoverStyle + "}");
                p.println(anchorPrefix + getName() + SUBHOVERSTYLE + ":active" + "{" + _subHoverStyle + "}");
            }
            else
            {
                p.println(anchorPrefix + getName() + SUBHOVERSTYLE + "{ }");
            }

            if (!isNullOrEmty(_subMenuBgColor))
            {
                p.println("TD." + getName() + SUBCELLBGSTYLE + "{BACKGROUND:" + _subMenuBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + SUBCELLBGSTYLE + "{ }");
            }

            /*
               if(!isNullOrEmty(_subHoverBgColor))
               p.println("<STYLE>TD." + getName() + SUBCELLHOVERBGSTYLE + "{BACKGROUND:" + _subHoverBgColor + "}</STYLE>");
                 else
                    p.println("<STYLE>TD." + getName() + SUBCELLHOVERBGSTYLE + "{ }</STYLE>");
             */
            if (!isNullOrEmty(_subHoverBgColor))
            {
                p.println("TD." + getName() + SUBCELLHOVERBGSTYLE + "{BACKGROUND:" + _subHoverBgColor + "}");
            }
            else
            {
                p.println("TD." + getName() + SUBCELLHOVERBGSTYLE + "{ }");
            }

            p.println("</STYLE>");

            //
            //Generate function scripts for loading images
            p.println("<SCRIPT LANGUAGE=JavaScript1.2>");
            p.println(getLoadFunctionScript());
            p.println(getImagesScript());
            p.println("</SCRIPT>");
            p.println();

            p.println("<!-- //Generate Table to be displayed  -->");
            p.print("<TABLE");

            if (_border != -1)
            {
                p.print(" BORDER=\"" + _border + "\"");
            }

            if (_cellPadding != -1)
            {
                p.print(" CELLPADDING=\"" + _cellPadding + "\"");
            }

            if (_cellSpacing != -1)
            {
                p.print(" CELLSPACING=\"" + _cellSpacing + "\"");
            }

            if (_bgColor != null)
            {
                p.print(" BGCOLOR=\"" + _bgColor + "\"");
            }

            if (_height != null)
            {
                p.print(" HEIGHT=\"" + _height + "\"");
            }

            if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
            {
                if (_width != null)
                {
                    int tempWidth = 0;

                    try
                    {
                        tempWidth = Integer.parseInt(_width);
                    }
                    catch (Exception e)
                    {
                        _navWidthPercent = _width;
                    }

                    _navWidth = tempWidth + _cellPadding;

                    if (_navWidthPercent != null)
                    {
                        p.print(" WIDTH=\"" + _navWidthPercent + "\"");
                    }
                    else
                    {
                        p.print(" WIDTH=\"" + _navWidth + "\"");
                    }
                }
            }
            else if (_width != null)
            {
                p.print(" WIDTH=\"" + _width + "\"");
            }

            if (_align != null)
            {
                p.print(" ALIGN=\"" + _align + "\"");
            }

            if (_cols != -1)
            {
                p.print(" COLS=\"" + _cols + "\"");
            }

            if (_hSpace != -1)
            {
                p.print(" HSPACE=\"" + _hSpace + "\"");
            }

            if (_vSpace != -1)
            {
                p.print(" VSPACE=\"" + _vSpace + "\"");
            }

            p.print(">");

            //p.println();
            String sGroupDividerStart = "<TR>";
            String sGroupDividerEnd = "</TR>";

            if (isHorizontalMode())
            {
                sGroupDividerStart = "";
                sGroupDividerEnd   = "";
                p.print("<TR>");
                p.println();
            }

            //Generate tr and td lines for all navbar groups and the related items
            //check to see if there is a forced setSelected
            _oneSelected = oneSelected();

            for (int i = 1; i <= _groups.size(); i++)
            {
                if (((Group) _groups.elementAt(i - 1))._visible == false)
                {
                    continue;
                }

                //Load Vertical Space Image
                if ((_vSpaceImage != null) && (!_vSpaceImage.equals("")))
                {
                    p.print(sGroupDividerStart);
                    p.println();
                    p.print("<TD><IMG SRC =\"" + _vSpaceImage + "\"");
                    p.print(" BORDER=0");
                    p.print(" VSPACE=" + ((Group) _groups.elementAt(i - 1))._vertPadding);
                    p.print("></TD>");
                    p.println();
                    p.print(sGroupDividerEnd);
                }

                //Group header image
                String  sGroupName     = ((Group) _groups.elementAt(i - 1))._name;
                String  sGroupHref     = ((Group) _groups.elementAt(i - 1))._href;
                String  sGroupImage    = ((Group) _groups.elementAt(i - 1))._image;
                String  sGroupTitle    = ((Group) _groups.elementAt(i - 1))._groupTitle;
                String  sGroupBgColor  = ((Group) _groups.elementAt(i - 1))._bgColor;
                int     sGroupVpadding = ((Group) _groups.elementAt(i - 1))._vertPadding;
                boolean bGroupSelected = ((Group) _groups.elementAt(i - 1))._selected;

                /** Saqib Chowdhry Jul 30, 2003 (12:09:54 PM) */
                String sGroupHrefStyle  = ((Group) _groups.elementAt(i - 1))._groupHrefStyle;
                String sGroupHoverStyle = ((Group) _groups.elementAt(i - 1))._groupHoverStyle;

                if ((Util.isFilled(sGroupHrefStyle) || (Util.isFilled(sGroupHoverStyle))))
                {
                    p.println("<STYLE>");
                }

                if (Util.isFilled(sGroupHrefStyle))
                {
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHREFSTYLE + i + "{" + sGroupHrefStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHREFSTYLE + i + ":hover" + "{" + sGroupHrefStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHREFSTYLE + i + ":visited" + "{" + sGroupHrefStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHREFSTYLE + i + ":active" + "{" + sGroupHrefStyle + "}");
                }

                if (Util.isFilled(sGroupHoverStyle))
                {
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHOVERSTYLE + i + "{" + sGroupHoverStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHOVERSTYLE + i + ":hover" + "{" + sGroupHoverStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHOVERSTYLE + i + ":visited" + "{" + sGroupHoverStyle + "}");
                    p.println(anchorPrefix + getName() + CUSTOMGROUPHOVERSTYLE + i + ":active" + "{" + sGroupHoverStyle + "}");
                }

                if ((Util.isFilled(sGroupHrefStyle) || (Util.isFilled(sGroupHoverStyle))))
                {
                    p.println("</STYLE>");
                }

                if (sGroupHref != null)
                {
                    p.println();
                    p.print(sGroupDividerStart);
                    p.println();

                    if ((sGroupTitle != null) && !sGroupTitle.equals(""))
                    {
                        // SC (10/10/02 3:23:51 PM)
                        if (!sGroupHref.toUpperCase().startsWith("JAVASCRIPT:"))
                        {
                            p.print("<TD ONCLICK=\"document.location='" + encodeURL(sGroupHref) + "';\"");
                        }
                        else
                        {
                            p.print("<TD ONCLICK=\"" + sGroupHref + "\"");
                        }

                        //If Browser Type is NETSCAPE OR MAC DO NOT CODE MOUSEOVER OR MOUSEOUT
                        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
                        {
                            //ONMOUSEOVER
                            p.print(" ONMOUSEOVER=\"");

                            if ((_groupHoverBgColor != null) && (!_groupHoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + GROUPHOVERBGSTYLE + "';");
                            }
                            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
                            }

                            /**
                             * Saqib Chowdhry Jul 30, 2003 (12:09:54 PM)
                             */
                            if (Util.isFilled(sGroupHoverStyle))
                            {
                                p.print("document.getElementById('" + sGroupName + getName() + "')" + ".className='" + getName() + CUSTOMGROUPHOVERSTYLE + i + "';");
                            }
                            else if ((_groupHoverStyle != null) && (!_groupHoverStyle.equals("")))
                            {
                                p.print("document.getElementById('" + sGroupName + getName() + "')" + ".className='" + getName() + GROUPHOVERSTYLE + "';");
                            }
                            else
                            {
                                p.print("document.getElementById('" + sGroupName + getName() + "')" + ".className='" + getName() + HOVERSTYLE + "';");
                            }

                            p.print("\"");

                            //ONMOUSEOUT
                            p.print(" ONMOUSEOUT=\"");

                            //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
                            if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                            {
                                p.print("this.className='TD.{BACKGROUND:" + sGroupBgColor + ";}" + "';");
                            }
                            else if ((_groupHoverBgColor != null) && (!_groupHoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + GROUPCELLBGSTYLE + "';");
                            }
                            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + CELLBGSTYLE + "';");
                            }

                            /**
                             * Saqib Chowdhry Jul 30, 2003 (12:09:54 PM)
                             */
                            if (Util.isFilled(sGroupHoverStyle))
                            {
                                if (bGroupSelected)
                                {
                                    p.print("document.getElementById('" + sGroupName + getName() + "')" + ".className='" + getName() + CUSTOMGROUPHOVERSTYLE + i + "';");
                                }
                                else
                                {
                                    p.print("document.getElementById('" + sGroupName + getName() + "').className='" + getName() + CUSTOMGROUPHREFSTYLE + i + "';");
                                }
                            }
                            else if ((_groupHoverStyle != null) && (!_groupHoverStyle.equals("")))
                            {
                                if (bGroupSelected)
                                {
                                    p.print("document.getElementById('" + sGroupName + getName() + "')" + ".className='" + getName() + GROUPHOVERSTYLE + "';");
                                }
                                else
                                {
                                    p.print("document.getElementById('" + sGroupName + getName() + "').className='" + getName() + GROUPHREFSTYLE + "';");
                                }
                            }
                            else
                            {
                                p.print("document.getElementById('" + sGroupName + getName() + "').className='" + getName() + HREFSTYLE + "';");
                            }

                            p.print("\"");
                        }

                        //CLASS
                        //IF A BG COLOR IS PASSED OVERIDE THE GROUP CELL BG COLOR
                        if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                        {
                            p.print(" BGCOLOR=\"" + sGroupBgColor + "\" NOWRAP");
                        }
                        else if ((_groupCellBgColor != null) && (!_groupCellBgColor.equals("")))
                        {
                            p.print(" CLASS=\"" + getName() + GROUPCELLBGSTYLE + "\" NOWRAP");
                        }
                        else
                        {
                            p.print(" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP");
                        }

                        p.print(">");

                        //HREF
                        p.print("<A NAME=\"" + sGroupName + getName() + "\" ID=\"" + sGroupName + getName() + "\" HREF=\"" + encodeURL(sGroupHref) + "\"");

                        if (!((isNetscapeBelowV6())))
                        {
                            p.print(" ONCLICK=\"return false;\"");
                        }

                        /**
                         * Saqib Chowdhry Jul 30, 2003 (12:09:54 PM)
                         */
                        if (Util.isFilled(sGroupHoverStyle))
                        {
                            if (bGroupSelected)
                            {
                                if (Util.isFilled(sGroupHoverStyle))
                                {
                                    p.print(" CLASS=\"" + getName() + CUSTOMGROUPHOVERSTYLE + i + "\"");
                                }
                            }
                            else
                            {
                                if (Util.isFilled(sGroupHrefStyle))
                                {
                                    p.print(" CLASS=\"" + getName() + CUSTOMGROUPHREFSTYLE + i + "\"");
                                }
                            }
                        }
                        else if ((_groupHrefStyle != null) && (!_groupHrefStyle.equals("")))
                        {
                            if (bGroupSelected)
                            {
                                p.print(" CLASS=\"" + getName() + GROUPHOVERSTYLE + "\"");
                            }
                            else
                            {
                                p.print(" CLASS=\"" + getName() + GROUPHREFSTYLE + "\"");
                            }
                        }
                        else
                        {
                            p.print(" CLASS=\"" + getName() + HREFSTYLE + "\"");
                        }

                        p.print(">");

                        //						p.print("<font class=\""+getName() + GROUPHREFSTYLE+"\">");
                        p.print(sGroupTitle);

                        //						p.print(	"</font>");
                    }
                    else
                    {
                        p.print("<TD");

                        if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                        {
                            p.print(" BGCOLOR=\"" + sGroupBgColor + "\" NOWRAP");
                        }
                        else if ((_groupCellBgColor != null) && (!_groupCellBgColor.equals("")))
                        {
                            p.print(" CLASS=\"" + getName() + GROUPCELLBGSTYLE + "\" NOWRAP");
                        }
                        else
                        {
                            p.print(" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP");
                        }

                        p.print("><A NAME=\"" + sGroupName + getName() + "\"  ID=\"" + sGroupName + getName() + "\" HREF=\"" + encodeURL(sGroupHref) + "\"");
                        p.print(">");
                        p.print("<IMG SRC =\"" + sGroupImage + "\"");
                        p.print(" BORDER=0");
                        p.print(" VSPACE=" + sGroupVpadding + ">");
                    }

                    p.print("</A></TD>");
                    p.println();
                    p.print(sGroupDividerEnd);
                }
                else
                {
                    if ((sGroupTitle != null) && !sGroupTitle.equals(""))
                    {
                        p.print(sGroupDividerStart);
                        p.print("<TD");

                        //If Browser Type is NETSCAPE OR MAC DO NOT CODE MOUSEOVER OR MOUSEOUT
                        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
                        {
                            //ONMOUSEOVER
                            p.print(" ONMOUSEOVER=\"");

                            if ((_groupHoverBgColor != null) && (!_groupHoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + GROUPHOVERBGSTYLE + "';");
                            }
                            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
                            }

                            p.print("\"");

                            //ONMOUSEOUT
                            p.print(" ONMOUSEOUT=\"");

                            //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
                            if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                            {
                                p.print("this.className='TD.{BACKGROUND:" + sGroupBgColor + ";}" + "';");
                            }
                            else if ((_groupHoverBgColor != null) && (!_groupHoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + GROUPCELLBGSTYLE + "';");
                            }
                            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
                            {
                                p.print("this.className='" + getName() + CELLBGSTYLE + "';");
                            }

                            p.print("\"");
                        }

                        //CLASS
                        //IF A BG COLOR IS PASSED OVERIDE THE GROUP CELL BG COLOR
                        if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                        {
                            p.print(" BGCOLOR=\"" + sGroupBgColor + "\" NOWRAP");
                        }
                        else if ((_groupCellBgColor != null) && (!_groupCellBgColor.equals("")))
                        {
                            p.print(" CLASS=\"" + getName() + GROUPCELLBGSTYLE + "\" NOWRAP");
                        }
                        else
                        {
                            p.print(" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP");
                        }

                        p.print(">");
                        p.print("<SPAN CLASS=\"" + getName() + TEXTSTYLE + "\"");
                        p.print(">");
                        p.print(sGroupTitle);
                        p.print("</SPAN>");
                        p.print("</TD>");
                        p.print(sGroupDividerEnd);
                    }
                    else if (sGroupImage != null)
                    {
                        p.print(sGroupDividerStart);
                        p.print("<TD");

                        if ((sGroupBgColor != null) && (!sGroupBgColor.equals("")))
                        {
                            p.print(" BGCOLOR=\"" + sGroupBgColor + "\" NOWRAP");
                        }
                        else if ((_groupCellBgColor != null) && (!_groupCellBgColor.equals("")))
                        {
                            p.print(" CLASS=\"" + getName() + GROUPCELLBGSTYLE + "\" NOWRAP");
                        }
                        else
                        {
                            p.print(" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP");
                        }

                        p.print("><IMG SRC =\"" + sGroupImage + "\"");
                        p.print(" BORDER=0");
                        p.print(" VSPACE=" + sGroupVpadding + ">");
                        p.print("</TD>");
                        p.print(sGroupDividerEnd);
                    }
                }

                //In the horizontal mode, we will show the all navbaritems. It is best to have ONLY ONE GROUP.
                for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
                {
                    if ((((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false) && !isHorizontalMode())
                    {
                        continue;
                    }

                    p.println();

                    String  sName      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._name;
                    String  sTitle     = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._title;
                    String  sHref      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._href;
                    String  sTarget    = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._target;
                    String  sBgColor   = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._itemBgcolor;
                    int     sHSpace    = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._horizPadding;
                    boolean sShowPopup = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._showPopup;
                    boolean sSelected  = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._selected;
                    String  sSubMenu   = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;

                    if (!sShowPopup && getSubMenuVisibility(sSubMenu))
                    {
                        //p.println("<!--generateTDPopupInNavBarLines STARTING-->");
                        generateTDPopupInNavBarLines(sName, sHref, sTitle, sTarget, sHSpace, sSubMenu, sBgColor, p);

                        continue;
                    }
                    else
                    {
                        p.print(sGroupDividerStart);
                    }

                    if ((sSubMenu != null) && getSubMenuVisibility(sSubMenu) && (!(isNetscapeBelowV6())))
                    {
                        //p.println("<!--generateTDSubMenuLine STARTING -->");
                        p.println(generateTDSubMenuLine(sName, sHref, sTitle, sTarget, sHSpace, sSubMenu, sSubMenu, sBgColor, false, sSelected));
                    }
                    else
                    {
                        if (sSelected)
                        {
                            p.println(generateTDSelectedLine(sName, sHref, sTitle, sTarget, sHSpace, sBgColor, false));
                        }
                        else if (isPage(sName) && !_oneSelected)
                        {
                            p.println(generateTDSelectedLine(sName, sHref, sTitle, sTarget, sHSpace, sBgColor, false));
                        }
                        else
                        {
                            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
                            {
                                p.println(generateTDLine(sName, sHref, sTitle, sTarget, sHSpace, sBgColor, false));
                            }
                            else
                            {
                                p.println(generateTDTextLine(sName, sTitle, sHSpace));
                            }
                        }
                    }

                    p.println();
                    p.print(sGroupDividerEnd);
                }

                //Spacer Image after each group
                if ((_groupSpacerImage != null) && !_groupSpacerImage.equals(""))
                {
                    p.println();
                    p.println(sGroupDividerStart);
                    p.println();
                    p.print("<TD><IMG SRC =\"" + _groupSpacerImage + "\"");
                    p.print(" BORDER=0");
                    p.print(" VSPACE=" + ((Group) _groups.elementAt(i - 1))._vertPadding);
                    p.print("></TD>");
                    p.println();
                    p.print(sGroupDividerEnd);
                }
            }

            if (isHorizontalMode())
            {
                p.print("</TR>");
                p.println();

                if ((_groups != null) && !_groups.isEmpty())
                {
                    expandAllGroups();
                }
            }

            p.println("</TABLE>");

            if ((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC))
            {
                return;
            }
            else
            {
                createSubMenuScript(p);
            }
        }
    }

    /**
     * Remove all items from the specified Group index
     *
     * @param groupIdx is the group index of which items will be removed . return true/false if removal was successful.
     *
     * @return DOCUMENT ME!
     */
    public boolean removeAllGroupItems(int groupIdx)
    {
        boolean itemsCleared = false;

        try
        {
            Group   group = (Group) _groups.elementAt(groupIdx);

            int     itemCount      = group._items.size();
            int     subItemCount   = 0;
            Item    testItem       = null;
            String  itemSubMenuVal = null;

            SubItem testSubItem     = null;
            String  subMenuGroupVal = null;

            for (int i = itemCount - 1; i >= 0; i--)
            {
                testItem       = (Item) (group._items.elementAt(i));
                itemSubMenuVal = testItem._submenu;

                if (!Util.isFilled(itemSubMenuVal))
                {
                    group._items.remove(i);

                    //  MessageLog.writeDebugMessage("Removed Item:" + testItem._name, null);
                    continue;
                }

                subItemCount = _subItems.size();

                for (int j = subItemCount - 1; j >= 0; j--)
                {
                    testSubItem = (SubItem) _subItems.elementAt(j);

                    subMenuGroupVal = testSubItem._subMenuGroup;

                    if (itemSubMenuVal.equalsIgnoreCase(subMenuGroupVal))
                    {
                        if (removeSubItems(subMenuGroupVal))
                        {
                            break;
                        }
                    }
                }

                group._items.remove(i);

                //        MessageLog.writeDebugMessage("Removed Item After:" + testItem._name, null);
            }

            group._items.clear();
            itemsCleared = true;

            return itemsCleared;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return itemsCleared;
        }
    }

    /**
     * Remove all items from the specified Group name
     *
     * @param groupName is the group name to remove items from.
     *
     * @return DOCUMENT ME!
     */
    public boolean removeAllGroupItems(String groupName)
    {
        return removeAllGroupItems(getGroupIndex(groupName));
    }

    /**
     * Remove all Groups and their associated Items from the Navbar return true/false if removal was successful.
     *
     * @return DOCUMENT ME!
     */
    public boolean removeAllGroups()
    {
        boolean groupsCleared = false;

        try
        {
            _groups.clear();
            _subItems.clear();

            return groupsCleared;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return groupsCleared;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param subMenuName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean removeSubItems(String subMenuName)
    {
        boolean itemsCleared = false;

        //        MessageLog.writeDebugMessage("--->", null);
        try
        {
            int     subItemCount = _subItems.size();

            SubItem testSubItem       = null;
            String  subItemSubMenuVal = null;
            String  subMenuGroupVal   = null;

            for (int j = subItemCount - 1; j >= 0; j--)
            {
                testSubItem = (SubItem) _subItems.elementAt(j);

                subItemSubMenuVal = testSubItem._subMenu;
                subMenuGroupVal   = testSubItem._subMenuGroup;

                //                MessageLog.writeDebugMessage(subMenuName + "subMenuName --- subMenuGroupVal :" + subMenuGroupVal, null);
                if (subMenuName.equalsIgnoreCase(subMenuGroupVal))
                {
                    if (subMenuName.equalsIgnoreCase(subItemSubMenuVal))
                    {
                        _subItems.remove(j);

                        //                        MessageLog.writeDebugMessage("Removed SubItem :" + testSubItem._name, null);
                    }
                    else
                    {
                        removeSubItems(subItemSubMenuVal);
                        _subItems.remove(j);
                    }
                }
            }

            itemsCleared = true;

            //            MessageLog.writeDebugMessage("<---", null);
            return itemsCleared;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return itemsCleared;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param pixels DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected HtmlImage newHorizonalSpacer(int pixels)
    {
        HtmlPage  page             = getPage();
        Props     pr               = page.getPageProperties();
        String    sVertSpacerImage = pr.getProperty(_groupname + ".Group.HorizSpacerImage");
        HtmlImage img              = new HtmlImage(sVertSpacerImage, page);
        img.setHorizontalSpace(pixels);

        return img;
    }

    /**
     * DOCUMENT ME!
     *
     * @param pixels int
     *
     * @return com.salmonllc.html.HtmlImage
     */
    protected HtmlImage newVerticalSpacer(int pixels)
    {
        HtmlPage  page             = getPage();
        Props     pr               = page.getPageProperties();
        String    sVertSpacerImage = pr.getProperty(_groupname + ".Group.VertSpacerImage");
        HtmlImage img              = new HtmlImage(sVertSpacerImage, page);
        img.setVerticalSpace(pixels);

        return img;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tdLine DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     * @param bSelected DOCUMENT ME!
     * @param sName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param subMenuName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String getFlyoutSubmenuMouseEvents(String tdLine, boolean showPopup, boolean bSelected, String sName, String sBgColor, String subMenuName)
    {
        //ONMOUSEOVER
        tdLine += "\n  ONMOUSEOVER=\"";

        //Hover BG Color
        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_subHoverBgColor != null) && (!_subHoverBgColor.equals("")) && !bSelected)
            {
                tdLine += ("this.className='" + getName() + SUBCELLHOVERBGSTYLE + "';");
            }
            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")) && !bSelected)
            {
                tdLine += ("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")) && bSelected)
            {
                tdLine += ("this.className='" + getName() + SELECTEDHOVERBGSTYLE + "';");
            }

            //        }else{
            //            tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
        }

        if (isIE6())
        {
            if (subMenuName != null)
            {
                tdLine += ("popup" + subMenuName + getName() + "ShowPopup();");
            }

            if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && _showSubMenuMarker)
            {
                tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");");
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Hover Style
        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            if (!bSelected)
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + "';\"");
            }
        }
        else if (!bSelected)
        {
            if ((_subHoverStyle != null) && (!_subHoverStyle.equals("")))
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHOVERSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';\"");
            }
        }
        else
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDHOVERSTYLE + "';\"");
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        tdLine += " ONMOUSEOUT=\"";

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += ("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        //Cell BG color
        {
            if (!bSelected)
            {
                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                {
                    tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
                }
                else
                {
                    if ((_subMenuBgColor != null) && (!_subMenuBgColor.equals("")))
                    {
                        tdLine += ("this.className='" + getName() + SUBCELLBGSTYLE + "';");
                    }
                    else
                    {
                        tdLine += ("this.className='" + getName() + CELLBGSTYLE + "';");
                    }
                }

                //            } else {
                //                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                //                {
                //                    tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
                //                }
                //                else
                //                {
                //                    tdLine += ("this.className='" + getName() + SELECTEDBGSTYLE + "';");
                //                }
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Cell Style
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            if (!bSelected)
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
            }
        }
        else
        {
            if (!bSelected)
            {
                if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                {
                    tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHREFSTYLE + "';\"");
                }
                else
                {
                    tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
                }
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDSTYLE + "';\"");
            }
        }

        return tdLine;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param subChildren DOCUMENT ME!
     * @param sAnchorName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer getIE6NS7PopupFunc(StringBuffer sScript, String subMenu, Vector subChildren, String sAnchorName)
    {
        sScript.append("\nfunction popup" + subMenu + getName() + "Popup() ");
        sScript.append("\n{");
        sScript.append("\nif ( document.getElementById(\"popup" + subMenu + getName() + "\").style.visibility=='hidden') ");
        sScript.append("\n{");

        //Following is changed to allocate the horizonal nav bar TT
        if (isHorizontalMode())
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop( document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent);");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)-" + _cellPadding + ";"); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +");");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop( document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent);");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");

            //sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + "\").offsetParent)-" + _cellPadding + ";"); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +");");
        }

        sScript.append("\n}");
        sScript.append("\nif (document.getElementById(\"popup" + subMenu + getName() + "\").style.visibility=='visible') ");
        sScript.append("\n{");

        //
        if (isHorizontalMode())
        {
            //if(isFirstLevelSubMenu(subMenu)) {
            sScript.append("\n	if ( ");
            sScript.append("\n		(");
            sScript.append("\n			!(");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseX()+document.body.scrollLeft > document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft &&"); // + popupsubMenu1.offsetLeft &&
            sScript.append("\n					" + getName() + "getMouseX()+document.body.scrollLeft <(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ")+ (document.getElementById(\"popup" + subMenu + getName() + "\").offsetWidth+" + _cellPadding + ")");
            sScript.append("\n				) &&");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseY()+document.body.scrollTop > document.getElementById(\"popup" + subMenu + getName() + "\").offsetTop - " + _cellPadding + " &&");
            sScript.append("\n					" + getName() + "getMouseY()+document.body.scrollTop < document.getElementById(\"popup" + subMenu + getName() + "\").offsetTop + document.getElementById(\"popup" + subMenu + getName() + "\").offsetHeight"); // + document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight +" + 2* _cellPadding);
            sScript.append("\n				)");
            sScript.append("\n			)");
            sScript.append("\n		)");
            sScript.append("\n		&&");
            sScript.append("\n		(");
            sScript.append("\n			!(");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseX()+document.body.scrollLeft > popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) &&");
            sScript.append("\n					" + getName() + "getMouseX()+document.body.scrollLeft <( popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+" + _cellPadding + ")+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth+" + _cellPadding + ")");
            sScript.append("\n				) &&");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseY()+document.body.scrollTop > (popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) -" + (2 * _cellPadding) + ") && ");
            sScript.append("\n					" + getName() + "getMouseY()+document.body.scrollTop  < popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+" + getName() + "getMaxHeight(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight) +" + _cellPadding);
            sScript.append("\n				)");
            sScript.append("\n			)");
            sScript.append("\n		)");
            sScript.append("\n	)");
            sScript.append("\n{");

            /*sScript.append("\nif ((" + getName() + "getMouseX()+document.body.scrollLeft<popup" + subMenu + getName() + ".offsetLeft-" + sAnchorName + ".offsetWidth || " + getName() + "getMouseX()+document.body.scrollLeft>");
               sScript.append("\n(popup" + subMenu + getName() + ".offsetLeft+" + _cellPadding +")+(popup" + subMenu + getName() + ".offsetWidth+" + _cellPadding +")+(" + sAnchorName + ".offsetWidth+" + _cellPadding +")) || (getMouseY()+document.body.scrollTop<");
               sScript.append("\npopup" + subMenu + getName() + ".offsetTop-" + sAnchorName + ".offsetHeight || getMouseY()+document.body.scrollTop>");
               sScript.append("\npopup" + subMenu + getName() + ".offsetTop+popup" + subMenu + getName() + ".offsetHeight+" + sAnchorName + ".offsetHeight)) ");
               sScript.append("\n{");     */
            /*}else{
               sScript.append("\nif ((" + getName() + "getMouseX()+document.body.scrollLeft<popup" + subMenu + getName() + ".offsetLeft || " + getName() + "getMouseX()+document.body.scrollLeft>");
               sScript.append("\n(popup" + subMenu + getName() + ".offsetLeft+" + _cellPadding +")+(popup" + subMenu + getName() + ".offsetWidth+" + _cellPadding +")) || (getMouseY()+document.body.scrollTop<");
               sScript.append("\npopup" + subMenu + getName() + ".offsetTop-getMaxHeight(" + sAnchorName + ".offsetHeight) || getMouseY()+document.body.scrollTop>");
               sScript.append("\npopup" + subMenu + getName() + ".offsetTop+popup" + subMenu + getName() + ".offsetHeight+" + sAnchorName + ".offsetHeight)) ");
               sScript.append("\n{");
               }*/
        }
        else
        {
            /*sScript.append("\nif ((" + getName() + "getMouseX()+document.body.scrollLeft<document.getElementById(\"popup"+ subMenu + "\").offsetLeft-document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth || " + getName() + "getMouseX()+document.body.scrollLeft>");
               sScript.append("\n(document.getElementById(\"popup"+ subMenu + "\").offsetLeft+" + _cellPadding +")+(document.getElementById(\"popup"+ subMenu + "\").offsetWidth+" + _cellPadding +")+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +")) || (getMouseY()+document.body.scrollTop<");
               sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").offsetTop-document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight || getMouseY()+document.body.scrollTop>");
               sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").offsetTop+document.getElementById(\"popup"+ subMenu + "\").offsetHeight+document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight)) ");
               sScript.append("\n{");*/
            sScript.append("\nif ((" + getName() + "getMouseX()+document.body.scrollLeft<document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft-document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth || " + getName() + "getMouseX()+document.body.scrollLeft>");
            sScript.append("\n(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ")+(document.getElementById(\"popup" + subMenu + getName() + "\").offsetWidth+" + _cellPadding + ")) "); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +")) ");
            sScript.append("|| (" + getName() + "getMouseY()+document.body.scrollTop<");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").offsetTop || "); //-document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight || ");
            sScript.append("" + getName() + "getMouseY()+document.body.scrollTop>");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").offsetTop+document.getElementById(\"popup" + subMenu + getName() + "\").offsetHeight  )) "); //+document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight)) ");
            sScript.append("\n{");
        }

        if (subChildren.size() > 0)
        {
            sScript.append("\nif (");

            for (int i = 0; i < subChildren.size(); i++)
            {
                if (i > 0)
                {
                    sScript.append("&& ");
                }

                sScript.append("\ndocument.getElementById(\"popup" + (String) subChildren.elementAt(i) + getName() + "\").style.visibility=='hidden' ");
            }

            sScript.append(")");
            sScript.append("\n{");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");

            for (int i = 0; i < subChildren.size(); i++)
            {
                String subChildName = (String) subChildren.elementAt(i);
                sScript.append("\ndocument.getElementById(\"popup" + subChildName + getName() + "\").style.visibility='hidden';");
            }

            sScript.append("\n}");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");
        }

        sScript.append("\n}");

        //
        if (isHorizontalMode())
        {
            sScript.append("\nif (" + getName() + "getMouseX()+document.body.scrollLeft<(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ") && (" + getName() + "getMouseY()+document.body.scrollTop<");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) || " + getName() + "getMouseY()+document.body.scrollTop>");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight +" + (2 * _cellPadding) + ")) ");
            sScript.append("\n{");
        }
        else
        {
            if (isNetscape7() || isNetscape6())
            {
                sScript.append("\nif (" + getName() + "getMouseX()+document.body.scrollLeft<(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft) && (" + getName() + "getMouseY()+document.body.scrollTop<");
            }
            else
            {
                sScript.append("\nif (" + getName() + "getMouseX()+document.body.scrollLeft<(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ") && (" + getName() + "getMouseY()+document.body.scrollTop<");
            }

            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) || " + getName() + "getMouseY()+document.body.scrollTop>");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight )) "); //+"+(2*_cellPadding)+")) ");
            sScript.append("\n{");
        }

        if (subChildren.size() > 0)
        {
            sScript.append("\nif (");

            for (int i = 0; i < subChildren.size(); i++)
            {
                if (i > 0)
                {
                    sScript.append("&& ");
                }

                sScript.append("\ndocument.getElementById(\"popup" + (String) subChildren.elementAt(i) + getName() + "\").style.visibility=='hidden' ");
            }

            sScript.append(")");
            sScript.append("\n{");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");

            for (int i = 0; i < subChildren.size(); i++)
            {
                String subChildName = (String) subChildren.elementAt(i);
                sScript.append("\ndocument.getElementById(\"popup" + subChildName + getName() + "\").style.visibility='hidden';");
            }

            sScript.append("\n}");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");
        }

        return sScript;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param sAnchorName DOCUMENT ME!
     * @param subGroupName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer getIE6NS7ShowPopupFunc(StringBuffer sScript, String subMenu, String sAnchorName, String subGroupName)
    {
        sScript.append("\nfunction popup" + subMenu + getName() + "ShowPopup() {");

        //Following has cahnged to allocate the horizonal nav bar TT
        if (isHorizontalMode() && isFirstLevelSubMenu(subMenu))
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+( " + getName() + "getMaxHeight(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight" + ") );"); //+ " + (2 * _cellPadding) + ");");//" + sAnchorName + ".offsetHeight+" + (2 * _cellPadding) + ");");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)-(" + _cellPadding + ");");
        }
        else
        {
            sScript.append("var varObjWidth = popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("if( varObjWidth < document.body.clientWidth){");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) ;");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("}else{"); // Move the new cel to the left 5 pixel down to make sure that the menu will be in the window
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent) + 5;");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\").offsetParent)-(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("}");
        }

        sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='visible';");

        Vector checkHide = new Vector();

        for (int k = 1; k <= _subItems.size(); k++)
        {
            String sSubGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;
            String sSubMenu = ((SubItem) _subItems.elementAt(k - 1))._subMenu;

            if ((!sSubMenu.equals(subMenu)) && (sSubGroupName.equals(subGroupName)) && (!sSubMenu.equals(sSubGroupName)) && (checkHide.indexOf(sSubMenu) < 0))
            {
                sScript.append("\ndocument.getElementById(\"popup" + sSubMenu + getName() + "\").style.visibility='hidden';");
                checkHide.addElement(sSubMenu);
            }
        }

        return sScript;
    }

    /**
     *
     */
    private String getIETDFlyoutLine(String tdLine, boolean showPopup, String sName, String sBgColor)
    {
        //ONMOUSEOVER
        tdLine += " ONMOUSEOVER=\"";

        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_subHoverBgColor != null) && (!_subHoverBgColor.equals("")))
            {
                tdLine += ("this.className='" + getName() + SUBCELLHOVERBGSTYLE + "';");
            }
            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
            {
                tdLine += ("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';");
        }
        else
        {
            if ((_subHoverStyle != null) && (!_subHoverStyle.equals("")))
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHOVERSTYLE + "';");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';");
            }
        }

        //
        //In the NS^ move the follwoing function call to anchor which is inside the current TD.
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && !isNetscape6())
        {
            tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            tdLine += "\"";
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        tdLine += " ONMOUSEOUT=\"";

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += ("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
            }
            else
            {
                if ((_subMenuBgColor != null) && (!_subMenuBgColor.equals("")))
                {
                    tdLine += ("this.className='" + getName() + SUBCELLBGSTYLE + "';");
                }
                else
                {
                    tdLine += ("this.className='" + getName() + CELLBGSTYLE + "';");
                }
            }
        }

        tdLine += "event.cancelBubble=true;";

        //STYLE
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';");
        }
        else
        {
            if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHREFSTYLE + "';");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';");
            }
        }

        //
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
        {
            tdLine += ("fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            tdLine += "\"";
        }

        return tdLine;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tdLine DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     * @param sName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String getIETDLine(String tdLine, boolean showPopup, String sName, String sBgColor)
    {
        //ONMOUSEOVER
        tdLine += " ONMOUSEOVER=\"";

        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
            {
                tdLine += ("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';");
        }
        else
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';");
        }

        //
        //In the NS^ move the follwoing function call to anchor which is inside the current TD.
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && !isNetscape6())
        {
            tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            tdLine += "\"";
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        tdLine += " ONMOUSEOUT=\"";

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += ("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
            }
            else
            {
                tdLine += ("this.className='" + getName() + CELLBGSTYLE + "';");
            }
        }

        tdLine += "event.cancelBubble=true;";

        //STYLE
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';");
        }
        else
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';");
        }

        //
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
        {
            tdLine += ("fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            tdLine += "\"";
        }

        return tdLine;
    }

    /**
     * Generates The proper Java Scripts for all the images in the NavBar Table with the appropriate attributes
     *
     * @return DOCUMENT ME!
     */
    private String getImagesScript()
    {
        StringBuffer sScript = new StringBuffer();
        sScript.append("var highlight;\n");
        sScript.append("var highlightSelected;\n");

        sScript.append("function " + getName() + "hideImages(selectedItem){\n");

        if (isNetscape7() || isNetscape6())
        {
            sScript.append("if(selectedItem && highlightSelected){ \n");
            sScript.append("fnc" + getName() + "_SelectedMarkerImage(highlightSelected);\n");
            sScript.append("highlight = \"undefined\";\n");
            sScript.append("}else	if(highlight){ \n");
            sScript.append("fnc" + getName() + "_MarkerImage(highlight);\n");
            sScript.append("} \n");
        }
        else
        {
            sScript.append("if(selectedItem!=null && highlightSelected!=null){ \n");
            sScript.append("fnc" + getName() + "_SelectedMarkerImage(highlightSelected);\n");
            sScript.append("highlight = null;\n");
            sScript.append("}else	if(highlight!=null){ \n");
            sScript.append("fnc" + getName() + "_MarkerImage(highlight);\n");
            sScript.append("} \n");
            sScript.append(" \n");
        }

        //	sScript.append("fnc"+getName()+"_MarkerImage(highlight);\n");
        //	sScript.append("}\n");
        sScript.append("}\n");

        if ((_markerImage != null) && !_markerImage.equals(""))
        {
            sScript.append("\nvar " + getName() + "_MarkerImage=new Image();\n");
            sScript.append(getName() + "_MarkerImage.onload=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerImage.onerror=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerImage.onabort=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerImage.src=\"" + _markerImage + "\";\n");

            sScript.append("function fnc" + getName() + "_MarkerImage(source) {\n");

            //sScript.append("if("+getName()+"_loading) {\n");
            //sScript.append("setTimeout('fnc"+getName()+"_MarkerImage(document.'+source.name+');',10);\n");
            // sScript.append("return;\n");
            //sScript.append("}\n");
            if (isNetscape7() || isNetscape6())
            {
                sScript.append("if (!source) \n");
            }
            else
            {
                sScript.append("if (source==null) \n");
            }

            sScript.append("return;\n");
            sScript.append(getName() + "_loading=true;\n");
            sScript.append("source.src=" + getName() + "_MarkerImage.src;\n");
            sScript.append("};\n\n");
        }

        if ((_markerOverImage != null) && !_markerOverImage.equals(""))
        {
            sScript.append("\nvar " + getName() + "_MarkerOverImage=new Image();\n");
            sScript.append(getName() + "_MarkerOverImage.onload=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerOverImage.onerror=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerOverImage.onabort=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_MarkerOverImage.src=\"" + _markerOverImage + "\";\n");

            sScript.append("function fnc" + getName() + "_MarkerOverImage(source, selectedItem) {\n");

            //sScript.append("if("+getName()+"_loading) {\n");
            //sScript.append("setTimeout('fnc"+getName()+"_MarkerOverImage(document.'+source.name+');',10);\n");
            // sScript.append("return;\n");
            //sScript.append("}\n");
            if (isNetscape7() || isNetscape6())
            {
                sScript.append("if (!source) \n");
            }
            else
            {
                sScript.append("if (source==null) \n");
            }

            sScript.append("return;\n");

            if (isNetscape7() || isNetscape6())
            {
                sScript.append(" if(selectedItem)\n");
            }
            else
            {
                sScript.append(" if(selectedItem!=null)\n");
            }

            sScript.append("highlightSelected=source;\n");
            sScript.append("else\n");
            sScript.append("highlight=source; \n");

            sScript.append(getName() + "hideImages(selectedItem);\n");
            sScript.append(getName() + "_loading=true;\n");
            sScript.append("source.src=" + getName() + "_MarkerOverImage.src;\n");

            sScript.append("};\n\n");
        }

        if ((_textImage != null) && !_textImage.equals(""))
        {
            sScript.append("\nvar " + getName() + "_TextImage=new Image();\n");
            sScript.append(getName() + "_TextImage.onload=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_TextImage.onerror=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_TextImage.onabort=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_TextImage.src=\"" + _textImage + "\";\n");
            sScript.append("function fnc" + getName() + "_TextImage(source) {\n");
            sScript.append("if(" + getName() + "_loading) {\n");
            sScript.append("setTimeout('fnc" + getName() + "_TextImage(document.'+source.name+');',10);\n");
            sScript.append("return;\n");
            sScript.append("}\n");

            if (isNetscape7() || isNetscape6())
            {
                sScript.append("if (!source) \n");
            }
            else
            {
                sScript.append("if (source==null) \n");
            }

            sScript.append("return;\n");
            sScript.append(getName() + "_loading=true;\n");
            sScript.append("source.src=" + getName() + "_TextImage.src;\n");
            sScript.append("};\n\n");
        }

        if ((_selectedMarkerImage != null) && !_selectedMarkerImage.equals(""))
        {
            sScript.append("\nvar " + getName() + "_SelectedMarkerImage=new Image();\n");
            sScript.append(getName() + "_SelectedMarkerImage.onload=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_SelectedMarkerImage.onerror=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_SelectedMarkerImage.onabort=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_SelectedMarkerImage.src=\"" + _selectedMarkerImage + "\";\n");
            sScript.append("function fnc" + getName() + "_SelectedMarkerImage(source) {\n");
            sScript.append("if(" + getName() + "_loading) {\n");
            sScript.append("setTimeout('fnc" + getName() + "_SelectedMarkerImage(document.'+source.name+');',10);\n");
            sScript.append("return;\n");
            sScript.append("}\n");

            if (isNetscape7() || isNetscape6())
            {
                sScript.append("if (!source) \n");
            }
            else
            {
                sScript.append("if (source==null) \n");
            }

            sScript.append("return;\n");
            sScript.append(getName() + "_loading=true;\n");
            sScript.append("source.src=" + getName() + "_SelectedMarkerImage.src;\n");
            sScript.append("};\n\n");
        }

        if ((_showPopupImage != null) && !_showPopupImage.equals(""))
        {
            sScript.append("\nvar " + getName() + "_ShowPopupImage=new Image();\n");
            sScript.append(getName() + "_ShowPopupImage.onload=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_ShowPopupImage.onerror=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_ShowPopupImage.onabort=this." + getName() + "_imageLoaded;\n");
            sScript.append(getName() + "_ShowPopupImage.src=\"" + _showPopupImage + "\";\n");
            sScript.append("function fnc" + getName() + "_ShowPopupImage(source) {\n");
            sScript.append("if(" + getName() + "_loading) {\n");
            sScript.append("setTimeout('fnc" + getName() + "_ShowPopupImage(document.'+source.name+');',10);\n");
            sScript.append("return;\n");
            sScript.append("}\n");

            if (isNetscape7() || isNetscape6())
            {
                sScript.append("if (!source) \n");
            }
            else
            {
                sScript.append("if (source==null) \n");
            }

            sScript.append("return;\n");
            sScript.append(getName() + "_loading=true;\n");
            sScript.append("source.src=" + getName() + "_ShowPopupImage.src;\n");
            sScript.append("};\n\n");
        }

        return sScript.toString();
    }

    /**
     * Get Load Function
     *
     * @return String
     */
    private String getLoadFunctionScript()
    {
        StringBuffer sScript = new StringBuffer();
        sScript.append("var " + getName() + "_loading=false;");
        sScript.append("function " + getName() + "_imageLoaded(e) {");
        sScript.append(getName() + "_loading=false;");
        sScript.append("}");

        return sScript.toString();
    }

    /**
     *
     */
    private void getNS45TDFlyoutLine(StringBuffer sbNS6TDLine, boolean showPopup, String sName, String sBgColor)
    {
        //ONMOUSEOVER
        sbNS6TDLine.append(" generateTDLine ONMOUSEOVER=\"");

        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_subHoverBgColor != null) && (!_subHoverBgColor.equals("")))
            {
                sbNS6TDLine.append("this.className='" + getName() + SUBCELLHOVERBGSTYLE + "';");
            }
            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
            {
                sbNS6TDLine.append("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
        }

        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';");
        }
        else
        {
            if ((_subHoverStyle != null) && (!_subHoverStyle.equals("")))
            {
                sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHOVERSTYLE + "';");
            }
            else
            {
                sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';");
            }
        }

        //
        //In the NS^ move the follwoing function call to anchor which is inside the current TD.
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && !isNetscape6())
        {
            sbNS6TDLine.append("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            sbNS6TDLine.append("\"");
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        sbNS6TDLine.append(" ONMOUSEOUT=\"");

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            sbNS6TDLine.append("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                sbNS6TDLine.append("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
            }
            else
            {
                if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                {
                    sbNS6TDLine.append("this.className='" + getName() + SUBCELLBGSTYLE + "';");
                }
                else
                {
                    sbNS6TDLine.append("this.className='" + getName() + CELLBGSTYLE + "';");
                }
            }
        }

        //STYLE
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';");
        }
        else
        {
            if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
            {
                sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHREFSTYLE + "';");
            }
            else
            {
                sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';");
            }
        }

        //
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
        {
            sbNS6TDLine.append("fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            sbNS6TDLine.append("\"");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param sbNS6TDLine DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     * @param sName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     */
    private void getNS45TDLine(StringBuffer sbNS6TDLine, boolean showPopup, String sName, String sBgColor)
    {
        //ONMOUSEOVER
        sbNS6TDLine.append(" generateTDLine ONMOUSEOVER=\"");

        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")))
            {
                sbNS6TDLine.append("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
        }

        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';");
        }
        else
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';");
        }

        //
        //In the NS^ move the follwoing function call to anchor which is inside the current TD.
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && !isNetscape6())
        {
            sbNS6TDLine.append("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            sbNS6TDLine.append("\"");
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        sbNS6TDLine.append(" ONMOUSEOUT=\"");

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            sbNS6TDLine.append("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                sbNS6TDLine.append("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
            }
            else
            {
                sbNS6TDLine.append("this.className='" + getName() + CELLBGSTYLE + "';");
            }
        }

        //STYLE
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';");
        }
        else
        {
            sbNS6TDLine.append("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';");
        }

        //
        if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
        {
            sbNS6TDLine.append("fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\"");
        }
        else
        {
            sbNS6TDLine.append("\"");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param subChildren DOCUMENT ME!
     * @param sAnchorName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer getNS6PopupFunc(StringBuffer sScript, String subMenu, Vector subChildren, String sAnchorName)
    {
        sScript.append("\nfunction popup" + subMenu + getName() + "Popup() ");
        sScript.append("\n{");
        sScript.append("\nif ( document.getElementById(\"popup" + subMenu + getName() + "\").style.visibility=='hidden') ");
        sScript.append("\n{");

        //Following is changed to allocate the horizonal nav bar TT
        if (isHorizontalMode())
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop( document.getElementById(\"" + sAnchorName + getName() + "\").parentNode)+( " + getName() + "getMaxHeight(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight) );");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))-" + (2 * _cellPadding) + ";"); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +");");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop( document.getElementById(\"" + sAnchorName + getName() + "\"));");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");

            //sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + "\").offsetParent)-" + _cellPadding + ";"); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +");");
        }

        sScript.append("\n}");
        sScript.append("\nif (document.getElementById(\"popup" + subMenu + getName() + "\").style.visibility=='visible') ");
        sScript.append("\n{");

        //
        if (isHorizontalMode())
        {
            sScript.append("\n	if ( ");
            sScript.append("\n		(");
            sScript.append("\n			!(");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseX()+window.pageXOffset > document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft &&"); // + popupsubMenu1.offsetLeft &&
            sScript.append("\n					" + getName() + "getMouseX()+window.pageXOffset <(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ")+ (document.getElementById(\"popup" + subMenu + getName() + "\").offsetWidth+" + _cellPadding + ")");
            sScript.append("\n				) &&");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseY()+window.pageYOffset > document.getElementById(\"popup" + subMenu + getName() + "\").offsetTop - " + _cellPadding + " &&");
            sScript.append("\n					" + getName() + "getMouseY()+window.pageYOffset < document.getElementById(\"popup" + subMenu + getName() + "\").offsetTop + document.getElementById(\"popup" + subMenu + getName() + "\").offsetHeight"); // + document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight +" + 2* _cellPadding);
            sScript.append("\n				)");
            sScript.append("\n			)");
            sScript.append("\n		)");
            sScript.append("\n		&&");
            sScript.append("\n		(");
            sScript.append("\n			!(");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseX()+window.pageXOffset > popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\")) &&");
            sScript.append("\n					" + getName() + "getMouseX()+window.pageXOffset <( popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))+" + _cellPadding + ")+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth+" + _cellPadding + ")");
            sScript.append("\n				) &&");
            sScript.append("\n				(");
            sScript.append("\n					" + getName() + "getMouseY()+window.pageYOffset > (popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\")) -" + (2 * _cellPadding) + ") && ");
            sScript.append("\n					" + getName() + "getMouseY()+window.pageYOffset  < popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\"))+" + getName() + "getMaxHeight(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight) +" + _cellPadding);
            sScript.append("\n				)");
            sScript.append("\n			)");
            sScript.append("\n		)");
            sScript.append("\n	)");
            sScript.append("\n{");
        }
        else
        {
            sScript.append("\nif ((" + getName() + "getMouseX()+window.pageXOffset<document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft-document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth || " + getName() + "getMouseX()+window.pageXOffset>");
            sScript.append("\n(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ")+(document.getElementById(\"popup" + subMenu + getName() + "\").offsetWidth+" + _cellPadding + ")) "); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +")) ");
            sScript.append("|| (" + getName() + "getMouseY()+window.pageYOffset<");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").offsetTop || "); //-document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight || ");
            sScript.append("" + getName() + "getMouseY()+window.pageYOffset>");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").offsetTop+document.getElementById(\"popup" + subMenu + getName() + "\").offsetHeight  )) "); //+document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight)) ");
            sScript.append("\n{");
        }

        if (subChildren.size() > 0)
        {
            sScript.append("\nif (");

            for (int i = 0; i < subChildren.size(); i++)
            {
                if (i > 0)
                {
                    sScript.append("&& ");
                }

                sScript.append("\ndocument.getElementById(\"popup" + (String) subChildren.elementAt(i) + getName() + "\").style.visibility=='hidden' ");
            }

            sScript.append(")");
            sScript.append("\n{");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");

            for (int i = 0; i < subChildren.size(); i++)
            {
                String subChildName = (String) subChildren.elementAt(i);
                sScript.append("\ndocument.getElementById(\"popup" + subChildName + getName() + "\").style.visibility='hidden';");
            }

            sScript.append("\n}");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");
        }

        sScript.append("\n}");

        //
        if (isHorizontalMode())
        {
            sScript.append("\nif (" + getName() + "getMouseX()+window.pageXOffset<(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ") && (" + getName() + "getMouseY()+window.pageYOffset<");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\")) || " + getName() + "getMouseY()+window.pageYOffset>");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\"))+document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight +" + (2 * _cellPadding) + ")) ");
            sScript.append("\n{");
        }
        else
        {
            sScript.append("\nif (" + getName() + "getMouseX()+window.pageXOffset<(document.getElementById(\"popup" + subMenu + getName() + "\").offsetLeft+" + _cellPadding + ") && (" + getName() + "getMouseY()+window.pageYOffset<");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\")) || " + getName() + "getMouseY()+window.pageYOffset>");
            sScript.append("\npopup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\"))+document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight )) "); //+"+(2*_cellPadding)+")) ");
            sScript.append("\n{");
        }

        if (subChildren.size() > 0)
        {
            sScript.append("\nif (");

            for (int i = 0; i < subChildren.size(); i++)
            {
                if (i > 0)
                {
                    sScript.append("&& ");
                }

                sScript.append("\ndocument.getElementById(\"popup" + (String) subChildren.elementAt(i) + getName() + "\").style.visibility=='hidden' ");
            }

            sScript.append(")");
            sScript.append("\n{");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");

            for (int i = 0; i < subChildren.size(); i++)
            {
                String subChildName = (String) subChildren.elementAt(i);
                sScript.append("\ndocument.getElementById(\"popup" + subChildName + getName() + "\").style.visibility='hidden';");
            }

            sScript.append("\n}");
        }
        else
        {
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='hidden';");
        }

        return sScript;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     * @param sAnchorName DOCUMENT ME!
     * @param subGroupName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer getNS6ShowPopupFunc(StringBuffer sScript, String subMenu, String sAnchorName, String subGroupName)
    {
        sScript.append("\nfunction popup" + subMenu + getName() + "ShowPopup() {");

        //Following has cahnged to allocate the horizonal nav bar TT
        if (isHorizontalMode() && isFirstLevelSubMenu(subMenu))
        {
            /*sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + "\"))+( getMaxHeight(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight) );");//+ " + (2 * _cellPadding) + ");");//" + sAnchorName + ".offsetHeight+" + (2 * _cellPadding) + ");");  /// .parentNode)+( getMaxHeight(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetHeight" + ") );");//+ " + (2 * _cellPadding) + ");");//" + sAnchorName + ".offsetHeight+" + (2 * _cellPadding) + ");");
               sScript.append("\ndocument.getElementById(\"popup"+ subMenu + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + "\"))-(" +  _cellPadding * 2 + ");");
             */
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop( document.getElementById(\"" + sAnchorName + getName() + "\").parentNode)+( " + getName() + "getMaxHeight(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetHeight) );");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))-" + (2 * _cellPadding) + ";"); //+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth+" + _cellPadding +");");
        }
        else
        {
            sScript.append("var varObjWidth = popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");"); //.offsetParent)+(document.getElementById(\"" + sAnchorName + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("if( varObjWidth < window.innerWidth){");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\")) ;");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))+(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("}else{"); // Move the new cel to the left 5 pixel down to make sure that the menu will be in the window
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.top=popup" + subMenu + getName() + "getPixelTop(document.getElementById(\"" + sAnchorName + getName() + "\")) + 5;");
            sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.left=popup" + subMenu + getName() + "getPixelLeft(document.getElementById(\"" + sAnchorName + getName() + "\"))-(document.getElementById(\"" + sAnchorName + getName() + "\").parentNode.offsetWidth-" + _cellPadding + ");");
            sScript.append("}");
        }

        sScript.append("\ndocument.getElementById(\"popup" + subMenu + getName() + "\").style.visibility='visible';");

        Vector checkHide = new Vector();

        for (int k = 1; k <= _subItems.size(); k++)
        {
            String sSubGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;
            String sSubMenu = ((SubItem) _subItems.elementAt(k - 1))._subMenu;

            if ((!sSubMenu.equals(subMenu)) && (sSubGroupName.equals(subGroupName)) && (!sSubMenu.equals(sSubGroupName)) && (checkHide.indexOf(sSubMenu) < 0))
            {
                sScript.append("\ndocument.getElementById(\"popup" + sSubMenu + getName() + "\").style.visibility='hidden';");
                checkHide.addElement(sSubMenu);
            }
        }

        return sScript;
    }

    /**
     * Get If Item Is on the Navbar as a item
     *
     * @param itemName DOCUMENT ME!
     *
     * @return boolean
     */
    private boolean isNavBarItem(String itemName)
    {
        boolean isItem = false;

        try
        {
outerloop: 
            for (int i = 1; i <= _groups.size(); i++)
            {
                if (((Group) _groups.elementAt(i - 1))._visible == false)
                {
                    continue;
                }

                for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
                {
                    if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                    {
                        continue;
                    }

                    String  sName      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._name;
                    boolean sShowPopup = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._showPopup;
                    String  sSubMenu   = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;

                    if (itemName.equals(sName))
                    {
                        isItem = true;

                        break outerloop;
                    }
                    else
                    {
                        if (!sShowPopup && getSubMenuVisibility(sSubMenu))
                        {
                            for (int k = 1; k <= _subItems.size(); k++)
                            {
                                boolean _sVisible    = ((SubItem) _subItems.elementAt(k - 1))._visible;
                                String  subGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;
                                String  subItemName  = ((SubItem) _subItems.elementAt(k - 1))._name;

                                if (subGroupName.equals(sSubMenu) && _sVisible)
                                {
                                    if (itemName.equals(subItemName))
                                    {
                                        isItem = true;

                                        break outerloop;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return isItem;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sVal DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private boolean isNullOrEmty(String sVal)
    {
        if ((sVal == null) || sVal.equalsIgnoreCase("null") || (sVal.length() < 1))
        {
            return true;
        }

        return false;
    }

    /**
     * Check to if the current page is equal to the page requested
     *
     * @param sName java.lang.String
     *
     * @return boolean
     */
    private boolean isPage(String sName)
    {
        if (sName == null)
        {
            return false;
        }

        String NavBarIdName = "";

        if (getPage().getParameter(NAVBARID) != null)
        {
            NavBarIdName = getPage().getParameter(NAVBARID);
        }

        if (sName.equals(NavBarIdName))
        {
            return true;
        }

        return false;

        /*        if (sHref==null)
           return false;
           String pageName=getPage().getPageName();
           int qindex=sHref.indexOf('?');
           if (qindex>-1)
             sHref=sHref.substring(0,qindex);
           if (sHref.endsWith(pageName))
             return true;
           return false;
         */
    }

    /**
     * Get Popup Item
     *
     * @param subMenuGroup DOCUMENT ME!
     *
     * @return String
     */
    private String getPopupItem(String subMenuGroup)
    {
        String sReturn = null;
outerloop: 
        for (int i = 1; i <= _groups.size(); i++)
        {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
            {
                continue;
            }

            for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
            {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                {
                    continue;
                }

                String sName    = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._name;
                String sSubMenu = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;

                if ((sSubMenu != null) && sSubMenu.equals(subMenuGroup))
                {
                    sReturn = sName;

                    break outerloop;
                }
            }
        }

        if (sReturn == null)
        {
            for (int k = 1; k <= _subItems.size(); k++)
            {
                boolean _bVisible = ((SubItem) _subItems.elementAt(k - 1))._visible;

                if (!_bVisible)
                {
                    continue;
                }

                String sName      = ((SubItem) _subItems.elementAt(k - 1))._name;
                String subSubName = ((SubItem) _subItems.elementAt(k - 1))._subMenu;

                if ((subSubName != null) && subSubName.equals(subMenuGroup))
                {
                    sReturn = sName;

                    break;
                }
            }
        }

        return sReturn;
    }

    /**
     * Get Sub Menu Mouse Script
     *
     * @param subMenu DOCUMENT ME!
     *
     * @return String
     */
    private String getSubMenuMouseScript(Vector subMenu)
    {
        if (!isGenerateScripts()) // In that case scripts will be generated by the associated nav bar
        {
            return "";
        }

        Vector       vNavBars          = getPage().getNavBarsVector();
        StringBuffer sbGetMouseScripts = new StringBuffer(); //If there is more than one nav bar in a page generate the mouse event trackers
        StringBuffer sScript           = new StringBuffer();
        StringBuffer sShowScript       = new StringBuffer();
        StringBuffer sbScriptNS        = new StringBuffer();
        StringBuffer sbVars            = new StringBuffer();

        if ((vNavBars != null) && (vNavBars.size() > 0))
        {
            Enumeration enumNavbars = vNavBars.elements();

            while (enumNavbars.hasMoreElements())
            {
                JspNavBar nb = (JspNavBar) enumNavbars.nextElement();

                if ((nb == null) || !nb.getVisible())
                {
                    continue;
                }

                String sName = nb.getName();

                if ((sName == null) || sName.equals(getName()))
                {
                    continue;
                }

                sbVars.append("\nvar " + sName + "mouseX=0;");
                sbVars.append("\nvar " + sName + "mouseY=0;");
                sbScriptNS.append("\n" + sName + "mouseX = e.pageX || e.clientX;");
                sbScriptNS.append("\n" + sName + "mouseY = e.pageY || e.clientY;");
                sbGetMouseScripts.append("\nfunction " + sName + "getMouseX() {");
                sbGetMouseScripts.append("\nreturn " + sName + "mouseX;");
                sbGetMouseScripts.append("\n}");
                sbGetMouseScripts.append("\nfunction " + sName + "getMouseY() {");
                sbGetMouseScripts.append("\nreturn " + sName + "mouseY;");
                sbGetMouseScripts.append("\n}");

                Vector vSubMenu = nb.getPopupsVector();

                for (int i = 0; i < vSubMenu.size(); i++)
                {
                    sbScriptNS.append("\npopup" + (String) vSubMenu.elementAt(i) + sName + "Popup();");
                    sShowScript.append("\npopup" + (String) vSubMenu.elementAt(i) + sName + "ShowPopup();");
                    sShowScript.append("\npopup" + (String) vSubMenu.elementAt(i) + sName + "Popup();");
                }

                nb.setGenerateScripts(false); //Do not generate ths scripts in more than onece in the page
            }
        }

        sScript.append("\n<SCRIPT LANGUAGE='JavaScript1.2'>");
        sScript.append("\nvar " + getName() + "mouseX=0;");
        sScript.append("\nvar " + getName() + "mouseY=0;");

        if ((sbVars != null) && (sbVars.length() > 0))
        {
            sScript.append(sbVars.toString());
        }

        sScript.append("\nfunction " + getName() + "mouseTracker(e) {");
        sScript.append("\ne = e || window.Event || window.event;");
        sScript.append("\n" + getName() + "mouseX = e.pageX || e.clientX;");
        sScript.append("\n" + getName() + "mouseY = e.pageY || e.clientY;");

        if ((sbScriptNS != null) && (sbScriptNS.length() > 0))
        {
            sScript.append(sbScriptNS.toString());
        }

        for (int i = 0; i < subMenu.size(); i++)
        {
            sScript.append("\npopup" + (String) subMenu.elementAt(i) + getName() + "Popup();");
            sShowScript.append("\npopup" + (String) subMenu.elementAt(i) + getName() + "ShowPopup();");
            sShowScript.append("\npopup" + (String) subMenu.elementAt(i) + getName() + "Popup();");
        }

        sScript.append("\n}");
        sScript.append("\nfunction set" + getName() + "MouseTracker() {");
        sScript.append("\nif (document.captureEvents)");
        sScript.append("\ndocument.captureEvents(window.Event.MOUSEMOVE|window.Event.MOUSEUP);");
        sScript.append("\ndocument.onmousemove = this." + getName() + "mouseTracker;");
        sScript.append("\n}");
        sScript.append("\nfunction " + getName() + "getMouseX() {");
        sScript.append("\nreturn " + getName() + "mouseX;");
        sScript.append("\n}");
        sScript.append("\nfunction " + getName() + "getMouseY() {");
        sScript.append("\nreturn " + getName() + "mouseY;");
        sScript.append("\n}");

        if ((sbGetMouseScripts != null) && (sbGetMouseScripts.length() > 0))
        {
            sScript.append(sbGetMouseScripts.toString());
        }

        sScript.append("\nset" + getName() + "MouseTracker();");

        sScript.append("\n</SCRIPT>");

        //In order to avoid the flash effect in the first hit of the page initilize the components in onLoad()
        if (isNetscape6() || isNetscape7())
        {
            //getPage().setOnLoad(sShowScript.toString());
            getPage().writeScript(sShowScript.toString());
        }

        setGenerateScripts(true);

        return sScript.toString();
    }

    /**
     * Get Sub Menu Popup Script
     *
     * @param subMenu DOCUMENT ME!
     * @param sName DOCUMENT ME!
     * @param subGroupName DOCUMENT ME!
     * @param subChildren DOCUMENT ME!
     *
     * @return String
     */
    private String getSubMenuPopupScript(String subMenu, String sName, String subGroupName, Vector subChildren)
    {
        String       sAnchorName = subGroupName + sName;
        StringBuffer sScript = new StringBuffer();
        sScript.append("\n<SCRIPT LANGUAGE=\"JavaScript1.2\">");

        if (isNetscape6())
        {
            writeNS6getPixelTopFunc(sScript, subMenu);
            writeNS6getPixelLeftFunc(sScript, subMenu);
        }
        else
        {
            sScript.append("\nfunction popup" + subMenu + getName() + "getPixelTop(htmlElement)");
            sScript.append("\n{");
            sScript.append("\nif (htmlElement.offsetParent==null)");
            sScript.append("\nreturn htmlElement.offsetTop-" + _cellSpacing + ";");
            sScript.append("\nreturn htmlElement.offsetTop+popup" + subMenu + getName() + "getPixelTop(htmlElement.offsetParent)");
            sScript.append("\n}");

            //
            sScript.append("\nfunction popup" + subMenu + getName() + "getPixelLeft(htmlElement)");
            sScript.append("\n{");
            sScript.append("\nif (htmlElement.offsetParent==null)");
            sScript.append("\nreturn htmlElement.offsetLeft+" + _cellPadding + ";");
            sScript.append("\nreturn htmlElement.offsetLeft+popup" + subMenu + getName() + "getPixelLeft(htmlElement.offsetParent)");
            sScript.append("\n}");
        }

        if (isNetscape7() || (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT))
        {
            getIE6NS7PopupFunc(sScript, subMenu, subChildren, sAnchorName);
            sScript.append("\n}");
            sScript.append("\n}");
            sScript.append("\n}");
            getIE6NS7ShowPopupFunc(sScript, subMenu, sAnchorName, subGroupName);
        }
        else if (isNetscape6())
        {
            getNS6PopupFunc(sScript, subMenu, subChildren, sAnchorName);
            sScript.append("\n}");
            sScript.append("\n}");
            sScript.append("\n}");
            getNS6ShowPopupFunc(sScript, subMenu, sAnchorName, subGroupName);
        }

        sScript.append("\n}");
        sScript.append("\n</SCRIPT>");

        return sScript.toString();
    }

    /*public boolean isSecondLevelSubMenu(String subMenu){
       for (int i=1;i<=_groups.size();i++) {
               if (((Group)_groups.elementAt(i-1))._visible==false)
                       continue;
               for (int j=1;j<=((Group)_groups.elementAt(i-1))._items.size();j++) {
                       Item itm = ((Item)((Group)_groups.elementAt(i-1))._items.elementAt(j-1));
                       if (itm._visible==false)
                               continue;
                       String sItemName=itm._submenu;
                       for(int k=0 ; k < _subItems.size() ; k++){
                                ((Item)((Group)_groups.elementAt(i-1))._items.elementAt(j-1));
                       }
                       if(sItemName!= null && sItemName.equals(subMenu))
                               return true;
               }
       }
       return false;
       }*/

    /**
     * Get Submenu Visibility
     *
     * @param subMenuName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private boolean getSubMenuVisibility(String subMenuName)
    {
        Vector  subMenu    = new Vector();
        boolean visibility = false;

        try
        {
            for (int i = 0; i < _subItems.size(); i++)
            {
                SubItem subItem = (SubItem) _subItems.elementAt(i);

                if (subItem._subMenu.equals(subMenuName))
                {
                    subMenu.addElement(subItem);
                }
            }

            if (subMenu.size() == 0)
            {
                return true;
            }

            for (int i = 0; i < subMenu.size(); i++)
            {
                SubItem subItem = (SubItem) subMenu.elementAt(i);

                if (!subItem._visible)
                {
                    continue;
                }

                visibility = true;

                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return visibility;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tdLine DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     * @param bSelected DOCUMENT ME!
     * @param sName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param subMenuName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String getSubmenuMouseEvents(String tdLine, boolean showPopup, boolean bSelected, String sName, String sBgColor, String subMenuName)
    {
        //ONMOUSEOVER
        tdLine += "\n  ONMOUSEOVER=\"";

        //Hover BG Color
        //Check if line being generated is inside a ShowPopup menu
        //If so DO NOT USE HOVER BG COLORS
        if (!showPopup)
        {
            if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")) && !bSelected)
            {
                tdLine += ("this.className='" + getName() + CELLHOVERBGSTYLE + "';");
            }
            else if ((_hoverBgColor != null) && (!_hoverBgColor.equals("")) && bSelected)
            {
                tdLine += ("this.className='" + getName() + SELECTEDHOVERBGSTYLE + "';");
            }

            //        }else{
            //            tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
        }

        if (isIE6())
        {
            if (subMenuName != null)
            {
                tdLine += ("popup" + subMenuName + getName() + "ShowPopup();");
            }

            if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && _showSubMenuMarker)
            {
                tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");");
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Hover Style
        //Check if line being generated is inside a ShowPopup menu
        //If so use showpopup styles
        if (showPopup && ((_showPopupHoverStyle != null) && (!_showPopupHoverStyle.equals(""))))
        {
            if (!bSelected)
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPHOVERSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + "';\"");
            }
        }
        else if (!bSelected)
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HOVERSTYLE + "';\"");
        }
        else
        {
            tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDHOVERSTYLE + "';\"");
        }

        //ONMOUSEOUT
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        tdLine += " ONMOUSEOUT=\"";

        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += ("this.className='TD.{BACKGROUND:" + sBgColor + ";}" + "';");
        }
        else
        //Cell BG color
        {
            if (!bSelected)
            {
                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                {
                    tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
                }
                else
                {
                    tdLine += ("this.className='" + getName() + CELLBGSTYLE + "';");
                }

                //            } else {
                //                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                //                {
                //                    tdLine += ("this.className='" + getName() + SHOWPOPUPBGSTYLE + "';");
                //                }
                //                else
                //                {
                //                    tdLine += ("this.className='" + getName() + SELECTEDBGSTYLE + "';");
                //                }
            }
        }

        tdLine += "event.cancelBubble=true;";

        //Cell Style
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            if (!bSelected)
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
            }
        }
        else
        {
            if (!bSelected)
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDSTYLE + "';\"");
            }
        }

        return tdLine;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sHref DOCUMENT ME!
     * @param sName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String computeHref(String sHref, String sName)
    {
        if (sHref == null)
        {
            return sHref;
        }

        sHref = encodeURL(sHref);

        // FC (05/06/03 11:32 AM)
        if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
        {
            return sHref;
        }

        if (!_autoSelectNav)
        {
            return sHref;
        }

        int    qindex = sHref.indexOf('#');
        String zoom = "";

        if (qindex > -1)
        {
            zoom  = sHref.substring(qindex);
            sHref = sHref.substring(0, qindex);
        }

        qindex = sHref.indexOf('?');

        if (qindex > -1)
        {
            sHref += ("&" + NAVBARID + "=" + sName + zoom);
        }
        else
        {
            // SC (10/10/02 3:01:04 PM)
            sHref += ("?" + NAVBARID + "=" + sName + zoom);
        }

        return sHref;
    }

    /**
     * Create the Div section for the submenu that will be popped up when hovering over an item
     *
     * @param sSubMenu DOCUMENT ME!
     * @param sItemName DOCUMENT ME!
     * @param sSubGroupName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String createPopupDiv(String sSubMenu, String sItemName, String sSubGroupName)
    {
        Vector       subChildren = new Vector();
        StringBuffer sScript = new StringBuffer();

        //Separate style tag did not work in the pages where more than 1 navbar is used. Therefore moved the stype inside the div tag. 10/7/02
        /*sScript.append("\n<STYLE type=\"text/css\">#popup" + sSubMenu + getName());
           sScript.append("\n{position:absolute;top:0px;left:0px;width:150px;visibility:hidden;z-index:0;}");
           sScript.append("\n</STYLE>");*/
        sScript.append("\n<DIV id=\"popup" + sSubMenu + getName() + "\"");
        sScript.append(" style='position:absolute;top:0px;left:0px;width:150px;visibility:hidden;z-index:0;' >");
        sScript.append("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" BGCOLOR=\"" + _bgColor + "\" CELLPADDING=\"" + _cellPadding + "\" CELLSPACING=\"" + _cellSpacing + "\">");

        for (int k = 1; k <= _subItems.size(); k++)
        {
            boolean _sVisible    = ((SubItem) _subItems.elementAt(k - 1))._visible;
            String  subGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;

            if (subGroupName.equals(sSubMenu) && _sVisible)
            {
                String sName      = ((SubItem) _subItems.elementAt(k - 1))._name;
                String sTitle     = ((SubItem) _subItems.elementAt(k - 1))._title;
                String sHref      = ((SubItem) _subItems.elementAt(k - 1))._href;
                String sTarget    = ((SubItem) _subItems.elementAt(k - 1))._target;
                String sBgColor   = ((SubItem) _subItems.elementAt(k - 1))._subItemBgColor;
                int    sHspace    = ((SubItem) _subItems.elementAt(k - 1))._horizPadding;
                String subSubName = ((SubItem) _subItems.elementAt(k - 1))._subMenu;
                sScript.append("\n<TR>");

                if ((subSubName != null) && (!subSubName.equals(sSubMenu)) && _sVisible)
                {
                    if ((sBgColor == null) && (_subMenuBgColor != null))
                    {
                        sBgColor = _subMenuBgColor;
                    }

                    //sScript.append(generateTDSubMenuLine(sName, sHref, sTitle, sTarget, sHspace, subSubName, subGroupName, sBgColor, true, false));
                    sScript.append(generateTDFlyoutSubMenuLine(sName, sHref, sTitle, sTarget, sHspace, subSubName, subGroupName, sBgColor, false, false));
                    subChildren.addElement(subSubName);
                }
                else
                {
                    if ((sBgColor == null) && (_subMenuBgColor != null))
                    {
                        sBgColor = _subMenuBgColor;
                    }

                    //sScript.append(generateTDLine(sName, sHref, sTitle, sTarget, sHspace, sBgColor, true));
                    sScript.append(generateTDFlyoutLine(sName, sHref, sTitle, sTarget, sHspace, sBgColor, false));
                }

                sScript.append("\n</TR>");
            }
        }

        sScript.append("</TABLE></DIV>");
        sScript.append(getSubMenuPopupScript(sSubMenu, sItemName, sSubGroupName, subChildren));

        return sScript.toString();
    }

    /**
     * Create Scriot for all submenus
     *
     * @param p DOCUMENT ME!
     */
    private void createSubMenuScript(TagWriter p)
    {
        StringBuffer sScript = new StringBuffer();
        _vPopups = new Vector();

        Vector vHidePopups = new Vector();

        //
        StringBuffer sbFirstLevelMenusAnchors = new StringBuffer();

        for (int i = 1; i <= _groups.size(); i++)
        {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
            {
                continue;
            }

            for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
            {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                {
                    continue;
                }

                String  sItemName      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._name;
                String  sItemMenu      = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;
                boolean sItemShowPopup = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._showPopup;

                if ((sItemMenu != null) && (_vPopups.indexOf(sItemMenu) < 0))
                {
                    if (sItemShowPopup)
                    {
                        if (isNavBarItem(sItemName))
                        {
                            sbFirstLevelMenusAnchors.append("document.getElementById(\"");
                            sbFirstLevelMenusAnchors.append(sItemMenu);
                            sbFirstLevelMenusAnchors.append(sItemName + getName());
                            sbFirstLevelMenusAnchors.append("\")");
                            sbFirstLevelMenusAnchors.append(" ,");
                        }

                        sScript.append(createPopupDiv(sItemMenu, getPopupItem(sItemMenu), sItemMenu));
                        _vPopups.addElement(sItemMenu);
                    }
                    else
                    {
                        vHidePopups.addElement(sItemMenu);
                    }
                }
                else
                {
                    if (isNavBarItem(sItemName))
                    {
                        sbFirstLevelMenusAnchors.append("document.getElementById(\"");
                        sbFirstLevelMenusAnchors.append(sItemName + getName());
                        sbFirstLevelMenusAnchors.append("\")");
                        sbFirstLevelMenusAnchors.append(" ,");
                    }
                }
            }
        }

        for (int k = 1; k <= _subItems.size(); k++)
        {
            boolean _sVisible    = ((SubItem) _subItems.elementAt(k - 1))._visible;
            String  subSubName   = ((SubItem) _subItems.elementAt(k - 1))._subMenu;
            String  subGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;

            if ((subSubName != null) && (_vPopups.indexOf(subSubName) < 0) && _sVisible && (vHidePopups.indexOf(subSubName) == -1))
            {
                sScript.append(createPopupDiv(subSubName, getPopupItem(subSubName), subGroupName));
                _vPopups.addElement(subSubName);
            }
        }

        sScript.append("\n<SCRIPT language=JavaScript1.2> ");
        sScript.append("\n");
        sScript.append("\n function " + getName() + "getMaxHeight(subRet){");
        sScript.append("\n var aryMaxH = [");

        if (sbFirstLevelMenusAnchors.length() > 0)
        {
            sScript.append(sbFirstLevelMenusAnchors.substring(0, sbFirstLevelMenusAnchors.length() - 2));
        }

        sScript.append("\n];");

        sScript.append("\nvar arlen = aryMaxH.length; ");
        sScript.append("\nvar retVal = 0;  ");
        sScript.append("\nfor (i=0;i<arlen;i++){ ");
        sScript.append("	var obj = aryMaxH[i]; ");
        sScript.append("	var tempH = 0;         ");
        sScript.append("	if(obj==null || obj=='undefined')   ");
        sScript.append("		continue; ");
        sScript.append("	else ");
        sScript.append("		tempH = obj.offsetHeight; ");
        sScript.append("	if(retVal < tempH) ");
        sScript.append("		retVal = tempH;      ");
        sScript.append("\n}   ");
        sScript.append("\nif(retVal<subRet) ");
        sScript.append("\n   return subRet;");
        sScript.append("\nelse");
        sScript.append("\n   return retVal;	");
        sScript.append("\n}");
        sScript.append("\n</SCRIPT>");

        try
        {
            p.println(sScript.toString());
            p.println(getSubMenuMouseScript(_vPopups));
        }
        catch (Exception e)
        {
        }

        return;
    }

    /**
     *
     */
    private String generateTDFlyoutLine(String sName, String sHref, String sTitle, String sTarget, int sHpad, String sBgColor, boolean showPopup)
    {
        String tdLine = "";

        //onmouseover and onmouse out with document.getElementByID... does not work on NS6. THerefore move it to the next anchor.
        StringBuffer sbNS6TDLine = new StringBuffer();

        //HREF GIVE EACH NAVBARITEM A UNIQUE NAME AND SAVE IT IN THE NAVBARID PARAMETER
        sHref = computeHref(sHref, sName);

        //
        //SET THE MOUSEOVER,MOUSEOUT AND CLASS FOR THE TD
        if ((sTarget != null) && (sTarget.length() > 0))
        {
            tdLine = "<TD ONCLICK=\"window.open('" + sHref + "');\"";
        }

        // SC (10/10/02 3:12:57 PM)
        else if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
        {
            tdLine = "<TD ONCLICK=\"" + sHref + "\"";
        }
        else
        {
            tdLine = "<TD 922 ONCLICK=\"document.location='" + sHref + "';\"";
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
        {
            tdLine = getIETDFlyoutLine(tdLine, showPopup, sName, sBgColor);
        }
        else if (!isNetscapeBelowV6())
        {
            getNS45TDFlyoutLine(sbNS6TDLine, showPopup, sName, sBgColor);
        }

        //CLASS
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += (" BGCOLOR=\"" + sBgColor + "\" NOWRAP>");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                tdLine += (" CLASS=\"" + getName() + SHOWPOPUPBGSTYLE + "\" NOWRAP>");
            }
            else
            {
                if ((_subMenuBgColor != null) && (!_subMenuBgColor.equals("")))
                {
                    tdLine += (" CLASS=\"" + getName() + SUBCELLBGSTYLE + "\" NOWRAP>");
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP>");
                }
            }
        }

        //ANCHOR
        if (!_dreamMode)
        {
            //HREF CLASS
            //LS 03/30/04
            //Do Not encode URL again as this was done previously by the computeHref Method
            //            tdLine += ("\n<A HREF=\"" + encodeURL(sHref) + "\"");
            tdLine += ("\n<A HREF=\"" + sHref + "\"");
            tdLine += " ONCLICK=\"return false;\"";

            if (isNetscape6())
            {
                tdLine += sbNS6TDLine.toString();
            }

            if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
            {
                tdLine += (" CLASS=\"" + getName() + SUBHREFSTYLE + "\">");
            }
            else
            {
                tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
            }
        }

        //
        if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
        {
            if (_navWidthPercent == null)
            {
                int navTableWidth = (_navWidth - _hSpace) - _cellPadding;

                if (navTableWidth <= 0)
                {
                    tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
                else
                {
                    tdLine += ("\n<TABLE WIDTH=\"" + navTableWidth + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
            }
            else
            {
                tdLine += ("\n<TABLE WIDTH=\"" + _navWidthPercent + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
            }
        }
        else
        {
            tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
        }

        tdLine += "\n<TR><TD";
        tdLine += (" WIDTH =\"5" + _sPctSign + "\">");

        //
        //IMAGE SOURCE MARKER IMAGE
        tdLine += ("<IMG SRC=\"" + _markerImage + "\"");

        if (isIE6())
        {
            tdLine = getIETDFlyoutLine(tdLine, showPopup, sName, sBgColor);
        }

        tdLine += (" NAME=\"Marker" + sName + getName() + "\" ONLOAD=\"" + getName() + "_imageLoaded();\" BORDER=\"0\"" + " HSPACE=\"0\">");

        //IMAGE SOURCE NULL IMAGE BETWEEN MARKER IMAGE AND TITLE
        tdLine += ("<IMG SRC=\"" + _hSpaceImage + "\"");
        tdLine += " BORDER=\"0\"";

        //If a submenu(showPopup) is being inserted into the NavBar add spacing of 3 for indentation
        if (showPopup)
        {
            sHpad += 3;
        }

        tdLine += (" HSPACE=\"" + (sHpad + 3) + "\">");

        //
        tdLine += "\n</TD>";

        //
        //If the td line being generated is not on the navbar menu inforce no word wrap
        if (isNavBarItem(sName)) // && !isHorizontalMode())
        {
            tdLine += "\n<TD ";
        }
        else
        {
            tdLine += "\n<TD NOWRAP";
        }

        //
        tdLine += (" WIDTH =\"95" + _sPctSign + "\">");

        tdLine += ("<A NAME=\"" + sName + getName() + "\"");

        //LS 03/30/04
        //Do Not encode URL again as this was done previously by the computeHref Method
        //        tdLine += (" HREF=\"" + encodeURL(sHref) + "\"");
        tdLine += (" HREF=\"" + sHref + "\"");

        if (!((isNetscapeBelowV6())))
        {
            tdLine += " ONCLICK=\"return false;\"";
        }

        if (sTarget != null)
        {
            tdLine += (" TARGET=\"" + sTarget + "\"");
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
        {
            if (isIE6())
            {
                tdLine = getIETDFlyoutLine(tdLine, showPopup, sName, sBgColor);
            }
            else
            {
                //HREF ONMOUSEOVER
                if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
                {
                    tdLine += (" ONMOUSEOVER=\"fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\" ");
                }

                //HREF ONMOUSEOUT
                if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
                {
                    tdLine += (" ONMOUSEOUT=\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\" ");
                }
            }
        }

        //HREF CLASS
        //If showPopup and a different style was given for the submenu in Navbar
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
        }
        else
        {
            if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
            {
                tdLine += (" CLASS=\"" + getName() + SUBHREFSTYLE + "\">");
            }
            else
            {
                tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
            }
        }

        //HREF TITLE
        tdLine += (sTitle + "</A>");

        //
        tdLine += "</TD>";
        tdLine += "</TR>";
        tdLine += "</TABLE>";

        if (!_dreamMode)
        {
            tdLine += "</A>";
        }

        tdLine += "</TD>";

        return tdLine;
    }

    /**
     *
     */
    private String generateTDFlyoutSubMenuLine(String sName, String sHref, String sTitle, String sTarget, int sHpad, String subMenuName, String sGroupName, String sBgColor, boolean showPopup, boolean bSelected)
    {
        String tdLine      = "";
        String sAnchorName = sGroupName + sName;

        try
        {
            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
            {
                sHref = computeHref(sHref, sName);

                if ((sTarget != null) && (sTarget.length() > 0))
                {
                    tdLine = "<TD  ONCLICK=\"window.open('" + sHref + "');\"";
                }

                // SC (10/10/02 3:12:57 PM)
                else if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
                {
                    tdLine = "<TD ONCLICK=\"" + sHref + "\"";
                }
                else
                {
                    //LS 03/30/04
                    //Do Not encode URL again as this was done previously by the computeHref Method
                    //                    tdLine = "\n<TD 1409 ONCLICK=\"document.location='" + encodeURL(sHref) + "';\"";
                    tdLine = "\n<TD 1409 ONCLICK=\"document.location='" + sHref + "';\"";
                }
            }
            else
            {
                tdLine = "\n<TD ONCLICK=\"return false;\"";
            }

            //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
            if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
            {
                tdLine = getFlyoutSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
            }

            //CLASS
            //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
            if ((sBgColor != null) && (!sBgColor.equals("")))
            {
                tdLine += (" BGCOLOR=\"" + sBgColor + "\" NOWRAP>");
            }
            else
            {
                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPBGSTYLE + "\" NOWRAP>");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\" NOWRAP>");
                    }
                }
                else if (!bSelected)
                {
                    if ((_subMenuBgColor != null) && (!_subMenuBgColor.equals("")))
                    {
                        tdLine += (" CLASS=\"" + getName() + SUBCELLBGSTYLE + "\" NOWRAP>");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP>");
                    }
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SELECTEDBGSTYLE + "\" NOWRAP>");
                }
            }

            if (!_dreamMode)
            {
                tdLine += ("\n<A NAME=\"" + sAnchorName + getName() + "\" ID=\"" + sAnchorName + getName() + "\"");
                tdLine += " ONCLICK=\"return false;\"";

                if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
                {
                    //LS 03/30/04
                    //Do Not encode URL again as this was done previously by the computeHref Method
                    //                    tdLine += (" HREF=\"" + encodeURL(sHref) + "\" ");
                    tdLine += (" HREF=\"" + sHref + "\" ");
                }

                //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
                if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
                {
                    //HREF ONMOUSEOVER
                    String tdMOverLine = "";

                    if (!isNetscape6())
                    {
                        tdMOverLine += ("\"popup" + subMenuName + getName() + "ShowPopup();");
                    }
                    else
                    {
                        tdLine += "\"";
                    }

                    if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && _showSubMenuMarker)
                    {
                        tdMOverLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");");
                    }

                    if (!tdMOverLine.equals(""))
                    {
                        tdLine += ("ONMOUSEOVER=" + tdMOverLine);
                        tdLine += "\"";
                    }

                    //HREF ONMOUSEOUT
                    tdLine += "\nONMOUSEOUT=";
                    tdLine += ("\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");");

                    if (!isNetscape6())
                    {
                        tdLine += ("popup" + subMenuName + getName() + "Popup();");
                    }

                    if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                    {
                        if (!bSelected)
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
                        }
                        else
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
                        }
                    }
                    else
                    {
                        if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHREFSTYLE + "';\"");
                        }
                        else
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
                        }
                    }
                }

                //HREF CLASS
                //If showPopup and a different style was given for the submenu in Navbar
                if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\">");
                    }
                }
                else
                {
                    if (!bSelected)
                    {
                        if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                        {
                            tdLine += (" CLASS=\"" + getName() + SUBHREFSTYLE + "\">");
                        }
                        else
                        {
                            tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
                        }
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
                    }
                }
            }

            //
            tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
            tdLine += "\n<TR><TD";
            tdLine += (" WIDTH =\"5" + _sPctSign + "\">");

            //IMAGE SOURCE MARKER IMAGE
            if (!isHorizontalMode() || _showSubMenuMarker || isFirstLevelSubMenu(subMenuName))
            {
                tdLine += ("<IMG SRC=\"" + _markerImage + "\"");

                if (isIE6())
                {
                    tdLine = getFlyoutSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
                }

                tdLine += (" NAME=\"Marker" + sName + getName() + "\" ONLOAD=\"" + getName() + "_imageLoaded();\" BORDER=\"0\"" + " HSPACE=\"0\">");
            }

            //IMAGE SOURCE NULL IMAGE BETWEEN MARKER IMAGE AND TITLE
            tdLine += ("<IMG SRC=\"" + _hSpaceImage + "\"");

            tdLine += " BORDER=\"0\"";

            //If a submenu(showPopup) is being inserted into the NavBar add spacing of 3 for indentation
            if (showPopup)
            {
                sHpad += 3;
            }

            tdLine += (" HSPACE=\"" + (sHpad + 3) + "\">");

            //
            tdLine += "\n</TD>";

            //Check if Td Line is on the Navbar or is a popup submenu

            /*if( isHorizontalMode() && isFirstLevelSubMenu(subMenuName))
               tdLine += "\n<TD NOWRAP";
               else */
            if (isNavBarItem(sName))
            {
                tdLine += "\n<TD";
            }
            else
            {
                tdLine += "\n<TD NOWRAP";
            }

            tdLine += (" WIDTH =\"95" + _sPctSign + "\">");

            tdLine += ("<A NAME=\"" + sName + getName() + "\"  ID=\"" + sName + getName() + "\"");

            if (isNetscape6())
            {
                tdLine += ("ONMOUSEOVER=\"popup" + subMenuName + getName() + "ShowPopup();");
                ;
                tdLine += "\"";
                tdLine += "\nONMOUSEOUT=";
                tdLine += ("\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");");
                tdLine += ("popup" + subMenuName + getName() + "Popup();");

                if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
                    }
                    else
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
                    }
                }
                else
                {
                    if (!bSelected)
                    {
                        if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SUBHREFSTYLE + "';\"");
                        }
                        else
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
                        }
                    }
                    else
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDSTYLE + "';\"");
                    }
                }
            }

            if (isIE6())
            {
                tdLine = getFlyoutSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
            }

            tdLine += " ONCLICK=\"return false;\"";

            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
            {
                tdLine += (" HREF=\"" + sHref + "\"");
            }

            if (sTarget != null)
            {
                tdLine += (" TARGET=\"" + sTarget + "\"");
            }

            if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
            {
                if (!bSelected)
                {
                    tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\">");
                }
            }
            else
            {
                if (!bSelected)
                {
                    if ((_subHrefStyle != null) && (!_subHrefStyle.equals("")))
                    {
                        tdLine += (" CLASS=\"" + getName() + SUBHREFSTYLE + "\">");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
                    }
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
                }
            }

            tdLine += sTitle;
            tdLine += "</A>";
            tdLine += "\n</TD>";
            tdLine += "\n</TR>";
            tdLine += "\n</TABLE>\n";

            if (!_dreamMode)
            {
                tdLine += "</A>";
            }

            //
            tdLine += "</TD>";
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("generateTDSubMenuLine", e, this);
        }

        return tdLine;
    }

    /**
     * Generates a TD lines For a NavBar item
     *
     * @param sName DOCUMENT ME!
     * @param sHref DOCUMENT ME!
     * @param sTitle DOCUMENT ME!
     * @param sTarget DOCUMENT ME!
     * @param sHpad DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String generateTDLine(String sName, String sHref, String sTitle, String sTarget, int sHpad, String sBgColor, boolean showPopup)
    {
        String tdLine = "";

        //onmouseover and onmouse out with document.getElementByID... does not work on NS6. THerefore move it to the next anchor.
        StringBuffer sbNS6TDLine = new StringBuffer();

        //HREF GIVE EACH NAVBARITEM A UNIQUE NAME AND SAVE IT IN THE NAVBARID PARAMETER
        sHref = computeHref(sHref, sName);

        //
        //SET THE MOUSEOVER,MOUSEOUT AND CLASS FOR THE TD
        if ((sTarget != null) && (sTarget.length() > 0))
        {
            tdLine = "<TD ONCLICK=\"window.open('" + sHref + "');\"";
        }

        // SC (10/10/02 3:12:57 PM)
        else if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
        {
            tdLine = "<TD ONCLICK=\"" + sHref + "\"";
        }
        else
        {
            tdLine = "<TD 922 ONCLICK=\"document.location='" + sHref + "';\"";
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
        {
            tdLine = getIETDLine(tdLine, showPopup, sName, sBgColor);
        }
        else if (!isNetscapeBelowV6())
        {
            getNS45TDLine(sbNS6TDLine, showPopup, sName, sBgColor);
        }

        //CLASS
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += (" BGCOLOR=\"" + sBgColor + "\" NOWRAP>");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                tdLine += (" CLASS=\"" + getName() + SHOWPOPUPBGSTYLE + "\" NOWRAP>");
            }
            else
            {
                tdLine += (" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP>");
            }
        }

        //ANCHOR
        if (!_dreamMode)
        {
            //HREF CLASS
            //LS 03/30/04
            //Do Not encode URL again as this was done previously by the computeHref Method
            //            tdLine += ("\n<A HREF=\"" + encodeURL(sHref) + "\"");
            tdLine += ("\n<A HREF=\"" + sHref + "\"");
            tdLine += " ONCLICK=\"return false;\"";

            if (isNetscape6())
            {
                tdLine += sbNS6TDLine.toString();
            }

            tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
        }

        //
        if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
        {
            if (_navWidthPercent == null)
            {
                int navTableWidth = (_navWidth - _hSpace) - _cellPadding;

                if (navTableWidth <= 0)
                {
                    tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
                else
                {
                    tdLine += ("\n<TABLE WIDTH=\"" + navTableWidth + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
            }
            else
            {
                tdLine += ("\n<TABLE WIDTH=\"" + _navWidthPercent + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
            }
        }
        else
        {
            tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
        }

        tdLine += "\n<TR><TD";
        tdLine += (" WIDTH =\"5" + _sPctSign + "\">");

        //
        //IMAGE SOURCE MARKER IMAGE
        tdLine += ("<IMG SRC=\"" + _markerImage + "\"");

        if (isIE6())
        {
            tdLine = getIETDLine(tdLine, showPopup, sName, sBgColor);
        }

        tdLine += (" NAME=\"Marker" + sName + getName() + "\" ONLOAD=\"" + getName() + "_imageLoaded();\" BORDER=\"0\"" + " HSPACE=\"0\">");

        //IMAGE SOURCE NULL IMAGE BETWEEN MARKER IMAGE AND TITLE
        tdLine += ("<IMG SRC=\"" + _hSpaceImage + "\"");
        tdLine += " BORDER=\"0\"";

        //If a submenu(showPopup) is being inserted into the NavBar add spacing of 3 for indentation
        if (showPopup)
        {
            sHpad += 3;
        }

        tdLine += (" HSPACE=\"" + (sHpad + 3) + "\">");

        //
        tdLine += "\n</TD>";

        //
        //If the td line being generated is not on the navbar menu inforce no word wrap
        if (isNavBarItem(sName)) // && !isHorizontalMode())
        {
            tdLine += "\n<TD ";
        }
        else
        {
            tdLine += "\n<TD NOWRAP";
        }

        //
        tdLine += (" WIDTH =\"95" + _sPctSign + "\">");

        tdLine += ("<A NAME=\"" + sName + getName() + "\"");

        //LS 03/30/04
        //Do Not encode URL again as this was done previously by the computeHref Method
        //        tdLine += (" HREF=\"" + encodeURL(sHref) + "\"");
        tdLine += (" HREF=\"" + sHref + "\"");

        if (!((isNetscapeBelowV6())))
        {
            tdLine += " ONCLICK=\"return false;\"";
        }

        if (sTarget != null)
        {
            tdLine += (" TARGET=\"" + sTarget + "\"");
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
        {
            if (isIE6())
            {
                tdLine = getIETDLine(tdLine, showPopup, sName, sBgColor);
            }
            else
            {
                //HREF ONMOUSEOVER
                if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
                {
                    tdLine += (" ONMOUSEOVER=\"fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\" ");
                }

                //HREF ONMOUSEOUT
                if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
                {
                    tdLine += (" ONMOUSEOUT=\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");\" ");
                }
            }
        }

        //HREF CLASS
        //If showPopup and a different style was given for the submenu in Navbar
        if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
        {
            tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
        }
        else
        {
            tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
        }

        //HREF TITLE
        tdLine += (sTitle + "</A>");

        //
        tdLine += "</TD>";
        tdLine += "</TR>";
        tdLine += "</TABLE>";

        if (!_dreamMode)
        {
            tdLine += "</A>";
        }

        tdLine += "</TD>";

        return tdLine;
    }

    /**
     * Generates a TD lines For a NavBar item and all of the Subitems associated to the Item
     *
     * @param sName DOCUMENT ME!
     * @param sHref DOCUMENT ME!
     * @param sTitle DOCUMENT ME!
     * @param sTarget DOCUMENT ME!
     * @param sHSpace DOCUMENT ME!
     * @param sMenuName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param p DOCUMENT ME!
     *
     * @throws java.io.IOException DOCUMENT ME!
     */
    private void generateTDPopupInNavBarLines(String sName, String sHref, String sTitle, String sTarget, int sHSpace, String sMenuName, String sBgColor, TagWriter p)
                                       throws java.io.IOException
    {
        //PRINT OUT THE CORRECT NAVBARITEM FIRST
        //If The Popup Items has a background color place all items in a table and set the
        //BG color
        if (_showPopupBgColor != null)
        {
            if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
            {
                if (_navWidthPercent == null)
                {
                    int navTableWidth = (_navWidth - _hSpace) - _cellPadding;
                    p.println("<TR BGCOLOR=\"" + _showPopupBgColor + "\">");

                    if (navTableWidth <= 0)
                    {
                        p.print("<TD><TABLE BORDER=0 CELLPADDING=\"0\" CELLSPACING=\"0\" BGCOLOR=\"" + _showPopupBgColor + "\" width=\"100" + _sPctSign + "\"><TR>");
                    }
                    else
                    {
                        p.print("<TD><TABLE BORDER=0 CELLPADDING=\"0\" CELLSPACING=\"0\" BGCOLOR=\"" + _showPopupBgColor + "\" width=\"" + navTableWidth + "\"><TR>");
                    }

                    sBgColor = _showPopupBgColor;
                    sHSpace += -1;
                }
                else
                {
                    p.println("<TR BGCOLOR=\"" + _showPopupBgColor + "\">");
                    p.print("<TD><TABLE BORDER=0 CELLPADDING=\"0\" CELLSPACING=\"0\" BGCOLOR=\"" + _showPopupBgColor + "\" width=\"" + _navWidthPercent + "\"><TR>");
                    sBgColor = _showPopupBgColor;
                    sHSpace += -1;
                }
            }
            else
            {
                p.println("<TR BGCOLOR=\"" + _showPopupBgColor + "\">");
                p.print("<TD><TABLE BORDER=0 BGCOLOR=\"" + _showPopupBgColor + "\" width=\"100" + _sPctSign + "\"><TR>");
                sBgColor = _showPopupBgColor;
                sHSpace += -1;
            }
        }

        //The (sHSpace -3) is to Prevent the first item in the ShowPopup from indenting
        p.println(generateTDSelectedLine(sName, sHref, sTitle, sTarget, sHSpace - 3, sBgColor, true));

        p.println();
        p.print("</TR>");

        //PRINT OUT THE CORRECT SUBITEM ASSOCIATED WITH THIS NAVBARITEM SUBMENU
        for (int k = 1; k <= _subItems.size(); k++)
        {
            boolean _sVisible    = ((SubItem) _subItems.elementAt(k - 1))._visible;
            String  subGroupName = ((SubItem) _subItems.elementAt(k - 1))._subMenuGroup;

            if (subGroupName.equals(sMenuName) && _sVisible)
            {
                p.println();
                p.print("<TR>");

                String  subName     = ((SubItem) _subItems.elementAt(k - 1))._name;
                String  subTitle    = ((SubItem) _subItems.elementAt(k - 1))._title;
                String  subHref     = ((SubItem) _subItems.elementAt(k - 1))._href;
                String  subTarget   = ((SubItem) _subItems.elementAt(k - 1))._target;
                String  subBgColor  = ((SubItem) _subItems.elementAt(k - 1))._subItemBgColor;
                int     subHSpace   = ((SubItem) _subItems.elementAt(k - 1))._horizPadding;
                boolean subSelected = ((SubItem) _subItems.elementAt(k - 1))._selected;
                String  subMenuName = ((SubItem) _subItems.elementAt(k - 1))._subMenu;

                if ((subMenuName != subGroupName) && getSubMenuVisibility(subMenuName) && (getPage().getBrowserType() != HtmlPage.BROWSER_NETSCAPE))
                {
                    p.println(generateTDSubMenuLine(subName, subHref, subTitle, subTarget, subHSpace, subMenuName, subGroupName, subBgColor, true, subSelected));
                }
                else
                {
                    if (subSelected)
                    {
                        p.println(generateTDSelectedLine(subName, subHref, subTitle, subTarget, subHSpace, subBgColor, true));
                    }
                    else if (isPage(subName) && !_oneSelected)
                    {
                        p.println(generateTDSelectedLine(subName, subHref, subTitle, subTarget, subHSpace, subBgColor, true));
                    }
                    else
                    {
                        if (sHref != null)
                        {
                            p.println(generateTDLine(subName, subHref, subTitle, subTarget, subHSpace, subBgColor, true));
                        }
                        else
                        {
                            p.println(generateTDTextLine(subName, subTitle, subHSpace));
                        }
                    }
                }

                p.println();
                p.print("</TR>");
            }
        }

        if (_showPopupBgColor != null)
        {
            p.println("</TABLE></TD></TR>");
        }

        return;
    }

    /**
     * Generates a Selected TD line inside the NavBar Table with the appropriate attributes
     *
     * @param sName DOCUMENT ME!
     * @param sHref DOCUMENT ME!
     * @param sTitle DOCUMENT ME!
     * @param sTarget DOCUMENT ME!
     * @param sHpad DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String generateTDSelectedLine(String sName, String sHref, String sTitle, String sTarget, int sHpad, String sBgColor, boolean showPopup)
    {
        String tdLine = "";

        //HREF
        if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
        {
            sHref = computeHref(sHref, sName);

            if ((sTarget != null) && (sTarget.length() > 0))
            {
                tdLine = "<TD  ONCLICK=\"window.open('" + sHref + "');\"";
            }

            // SC (10/10/02 3:12:57 PM)
            else if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
            {
                tdLine = "<TD ONCLICK=\"" + sHref + "\"";
            }
            else
            {
                //LS 03/30/04
                //Do Not encode URL again as this was done previously by the computeHref Method
                //                tdLine = "<TD 1238 ONCLICK=\"document.location='" + encodeURL(sHref) + "';\"";
                tdLine = "<TD 1238 ONCLICK=\"document.location='" + sHref + "';\"";
            }
        }
        else
        {
            tdLine = "\n<TD ONCLICK=\"return false;\"";
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
        {
            //ONMOUSEOVER
            tdLine += " ONMOUSEOVER=\"";

            //Selected BG Color
            //Check if line being generated is inside a ShowPopup menu
            //If so use DO NOT USE HOVER BG COLOR
            if (!showPopup)
            {
                if ((_selectedHoverBgColor != null) && (!_selectedHoverBgColor.equals("")))
                {
                    tdLine += ("this.className='" + getName() + SELECTEDHOVERBGSTYLE + "';");
                }
            }

            //
            //Selected Style
            //
            //Check if line being generated is inside a ShowPopup menu
            //If so use showpopup styles
            if (showPopup && ((_showPopupSelectedHoverStyle != null) && (!_showPopupSelectedHoverStyle.equals(""))))
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDHOVERSTYLE + "';");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDHOVERSTYLE + "';");
            }

            if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
            {
                if ((_selectedMarkerImage != null) && !_selectedMarkerImage.trim().equals(""))
                {
                    tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ", true);\"");
                }
                else
                {
                    tdLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\"");
                }
            }
            else
            {
                tdLine += "\"";
            }

            //ONMOUSEOUT
            tdLine += " ONMOUSEOUT=\"";

            //Selected BG Color
            if ((_selectedHoverBgColor != null) && (!_selectedHoverBgColor.equals("")))
            {
                tdLine += ("this.className='" + getName() + SELECTEDBGSTYLE + "';");
            }

            //Selected Style
            //STYLE
            if (showPopup && ((_showPopupSelectedStyle != null) && (!_showPopupSelectedStyle.equals(""))))
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';");
            }
            else
            {
                tdLine += ("document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDSTYLE + "';");
            }

            if ((_selectedMarkerImage != null) && !_selectedMarkerImage.trim().equals(""))
            {
                tdLine += ("fnc" + getName() + "_SelectedMarkerImage(document.Marker" + sName + getName() + ");\"");
            }
            else
            {
                tdLine += "\"";
            }
        }

        //CLASS
        //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
        if ((sBgColor != null) && (!sBgColor.equals("")))
        {
            tdLine += (" BGCOLOR=\"" + sBgColor + "\" NOWRAP>");
        }
        else
        {
            if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
            {
                tdLine += (" CLASS=\"" + getName() + SHOWPOPUPBGSTYLE + "\" NOWRAP>");
            }
            else if ((_selectedBgColor != null) && (!_selectedBgColor.equals("")))
            {
                tdLine += (" CLASS=\"" + getName() + SELECTEDBGSTYLE + "\" NOWRAP>");
            }
            else
            {
                tdLine += (" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP>");
            }
        }

        //
        if (!_dreamMode)
        {
            //HREF CLASS
            tdLine += "\n<A";

            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
            {
                //LS 03/30/04
                //Do Not encode URL again as this was done previously by the computeHref Method
                //                tdLine += (" HREF=\"" + encodeURL(sHref) + "\"");
                tdLine += (" HREF=\"" + sHref + "\"");
            }

            tdLine += " ONCLICK=\"return false;\"";
            tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
        }

        if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
        {
            if (_navWidthPercent == null)
            {
                int navTableWidth = (_navWidth - _hSpace) - _cellPadding;

                if (navTableWidth <= 0)
                {
                    tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
                else
                {
                    tdLine += ("\n<TABLE WIDTH=\"" + navTableWidth + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
                }
            }
            else
            {
                tdLine += ("\n<TABLE WIDTH=\"" + _navWidthPercent + "\" BORDER=\"0\" CELLPADDING=\"2\" CELLSPACING=\"0\">");
            }
        }
        else
        {
            tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
        }

        tdLine += "\n<TR>\n<TD";
        tdLine += (" WIDTH =\"5" + _sPctSign + "\">");

        //
        //IMAGE SOURCE MARKER IMAGE
        tdLine += ("<IMG SRC=\"" + _selectedMarkerImage + "\"");
        tdLine += (" NAME=\"Marker" + sName + getName() + "\" ONLOAD=\"" + getName() + "_imageLoaded();\" BORDER=\"0\"" + " HSPACE=\"0\">");

        //IMAGE SOURCE NULL IMAGE BETWEEN MARKER IMAGE AND TITLE
        tdLine += ("<IMG SRC=\"" + _hSpaceImage + "\"");
        tdLine += " BORDER=\"0\"";

        //If a submenu(showPopup) is being inserted into the NavBar add spacing of 3 for indentation
        if (showPopup)
        {
            sHpad += 3;
        }

        tdLine += (" HSPACE=\"" + (sHpad + 3) + "\">");

        tdLine += "\n</TD>";

        //
        //
        //IF THE TD LINE BEING GENERATED IS ON THE NAVIGATION MENU WORD WRAP ELSE NO WORD WRAP
        if (isNavBarItem(sName))
        {
            tdLine += "\n<TD";
        }
        else
        {
            tdLine += "\n<TD NOWRAP";
        }

        tdLine += (" WIDTH =\"95" + _sPctSign + "\">");

        //HREF
        tdLine += ("<A NAME=\"" + sName + getName() + "\" ID=\"" + sName + getName() + "\"");

        if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
        {
            //LS 03/30/04
            //Do Not encode URL again as this was done previously by the computeHref Method
            //            tdLine += (" HREF=\"" + encodeURL(sHref) + "\"");
            tdLine += (" HREF=\"" + sHref + "\"");
        }

        if (!((isNetscapeBelowV6())))
        {
            tdLine += " ONCLICK=\"return false;\"";
        }

        if (sTarget != null)
        {
            tdLine += (" TARGET=\"" + sTarget + "\"");
        }

        //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
        if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)))
        {
            //HREF ONMOUSEOVER
            if ((_markerOverImage != null) && !_markerOverImage.trim().equals(""))
            {
                if ((_selectedMarkerImage != null) && !_selectedMarkerImage.trim().equals(""))
                {
                    tdLine += (" ONMOUSEOVER=\"fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ", true);\" ");
                }
                else
                {
                    tdLine += (" ONMOUSEOVER=\"fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");\" ");
                }
            }

            //HREF ONMOUSEOUT
            if ((_selectedMarkerImage != null) && !_selectedMarkerImage.trim().equals(""))
            {
                tdLine += (" ONMOUSEOUT=\"fnc" + getName() + "_SelectedMarkerImage(document.Marker" + sName + getName() + ");\" ");
            }
        }

        //HREF CLASS
        if (showPopup && ((_showPopupSelectedStyle != null) && (!_showPopupSelectedStyle.equals(""))))
        {
            tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\">");
        }
        else
        {
            tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
        }

        //HREF TITLE
        tdLine += (sTitle + "</A>");

        //
        //
        tdLine += "</TD>";
        tdLine += "</TR>";
        tdLine += "</TABLE>";

        if (!_dreamMode)
        {
            tdLine += "</A>";
        }

        tdLine += "</TD>";

        return tdLine;
    }

    /**
     * Generates a TD line For a SubMenu inside the NavBar Table with the appropriate attributes
     *
     * @param sName DOCUMENT ME!
     * @param sHref DOCUMENT ME!
     * @param sTitle DOCUMENT ME!
     * @param sTarget DOCUMENT ME!
     * @param sHpad DOCUMENT ME!
     * @param subMenuName DOCUMENT ME!
     * @param sGroupName DOCUMENT ME!
     * @param sBgColor DOCUMENT ME!
     * @param showPopup DOCUMENT ME!
     * @param bSelected DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String generateTDSubMenuLine(String sName, String sHref, String sTitle, String sTarget, int sHpad, String subMenuName, String sGroupName, String sBgColor, boolean showPopup, boolean bSelected)
    {
        String tdLine      = "";
        String sAnchorName = sGroupName + sName;

        try
        {
            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
            {
                sHref = computeHref(sHref, sName);

                if ((sTarget != null) && (sTarget.length() > 0))
                {
                    tdLine = "<TD  ONCLICK=\"window.open('" + sHref + "');\"";
                }

                // SC (10/10/02 3:12:57 PM)
                else if (sHref.toUpperCase().startsWith("JAVASCRIPT:"))
                {
                    tdLine = "<TD ONCLICK=\"" + sHref + "\"";
                }
                else
                {
                    //LS 03/30/04
                    //Do Not encode URL again as this was done previously by the computeHref Method
                    //                    tdLine = "\n<TD 1409 ONCLICK=\"document.location='" + encodeURL(sHref) + "';\"";
                    tdLine = "\n<TD 1409 ONCLICK=\"document.location='" + sHref + "';\"";
                }
            }
            else
            {
                tdLine = "\n<TD ONCLICK=\"return false;\"";
            }

            //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
            if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
            {
                tdLine = getSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
            }

            //CLASS
            //IF A BG COLOR IS PASSED OVERIDE THE CELL BG COLOR
            if ((sBgColor != null) && (!sBgColor.equals("")))
            {
                tdLine += (" BGCOLOR=\"" + sBgColor + "\" NOWRAP>");
            }
            else
            {
                if (showPopup && ((_showPopupBgColor != null) && (!_showPopupBgColor.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPBGSTYLE + "\" NOWRAP>");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\" NOWRAP>");
                    }
                }
                else if (!bSelected)
                {
                    tdLine += (" CLASS=\"" + getName() + CELLBGSTYLE + "\" NOWRAP>");
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SELECTEDBGSTYLE + "\" NOWRAP>");
                }
            }

            if (!_dreamMode)
            {
                tdLine += ("\n<A NAME=\"" + sAnchorName + getName() + "\" ID=\"" + sAnchorName + getName() + "\"");
                tdLine += " ONCLICK=\"return false;\"";

                if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
                {
                    //LS 03/30/04
                    //Do Not encode URL again as this was done previously by the computeHref Method
                    //                    tdLine += (" HREF=\"" + encodeURL(sHref) + "\" ");
                    tdLine += (" HREF=\"" + sHref + "\" ");
                }

                //If Browser Type is NETSCAPE OR OS IS MAC DO NOT CODE MOUSEOVER AND MOUSEOUT
                if (!((isNetscapeBelowV6()) || (getPage().getBrowserOS() == HtmlPage.BROWSER_OS_MAC)) && !isIE6())
                {
                    //HREF ONMOUSEOVER
                    String tdMOverLine = "";

                    if (!isNetscape6())
                    {
                        tdMOverLine += ("\"popup" + subMenuName + getName() + "ShowPopup();");
                    }
                    else
                    {
                        tdLine += "\"";
                    }

                    if ((_markerOverImage != null) && !_markerOverImage.trim().equals("") && _showSubMenuMarker)
                    {
                        tdMOverLine += ("fnc" + getName() + "_MarkerOverImage(document.Marker" + sName + getName() + ");");
                    }

                    if (!tdMOverLine.equals(""))
                    {
                        tdLine += ("ONMOUSEOVER=" + tdMOverLine);
                        tdLine += "\"";
                    }

                    //HREF ONMOUSEOUT
                    tdLine += "\nONMOUSEOUT=";
                    tdLine += ("\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");");

                    if (!isNetscape6())
                    {
                        tdLine += ("popup" + subMenuName + getName() + "Popup();");
                    }

                    if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                    {
                        if (!bSelected)
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
                        }
                        else
                        {
                            tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
                        }
                    }
                    else
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
                    }
                }

                //HREF CLASS
                //If showPopup and a different style was given for the submenu in Navbar
                if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\">");
                    }
                }
                else
                {
                    if (!bSelected)
                    {
                        tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
                    }
                    else
                    {
                        tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
                    }
                }
            }

            //
            tdLine += ("\n<TABLE WIDTH=\"100" + _sPctSign + "\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
            tdLine += "\n<TR><TD";
            tdLine += (" WIDTH =\"5" + _sPctSign + "\">");

            //IMAGE SOURCE MARKER IMAGE
            if (!isHorizontalMode() || _showSubMenuMarker || isFirstLevelSubMenu(subMenuName))
            {
                tdLine += ("<IMG SRC=\"" + _markerImage + "\"");

                if (isIE6())
                {
                    tdLine = getSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
                }

                tdLine += (" NAME=\"Marker" + sName + getName() + "\" ONLOAD=\"" + getName() + "_imageLoaded();\" BORDER=\"0\"" + " HSPACE=\"0\">");
            }

            //IMAGE SOURCE NULL IMAGE BETWEEN MARKER IMAGE AND TITLE
            tdLine += ("<IMG SRC=\"" + _hSpaceImage + "\"");

            tdLine += " BORDER=\"0\"";

            //If a submenu(showPopup) is being inserted into the NavBar add spacing of 3 for indentation
            if (showPopup)
            {
                sHpad += 3;
            }

            tdLine += (" HSPACE=\"" + (sHpad + 3) + "\">");

            //
            tdLine += "\n</TD>";

            //Check if Td Line is on the Navbar or is a popup submenu

            /*if( isHorizontalMode() && isFirstLevelSubMenu(subMenuName))
               tdLine += "\n<TD NOWRAP";
               else */
            if (isNavBarItem(sName))
            {
                tdLine += "\n<TD";
            }
            else
            {
                tdLine += "\n<TD NOWRAP";
            }

            tdLine += (" WIDTH =\"95" + _sPctSign + "\">");

            tdLine += ("<A NAME=\"" + sName + getName() + "\"  ID=\"" + sName + getName() + "\"");

            if (isNetscape6())
            {
                tdLine += ("ONMOUSEOVER=\"popup" + subMenuName + getName() + "ShowPopup();");
                ;
                tdLine += "\"";
                tdLine += "\nONMOUSEOUT=";
                tdLine += ("\"fnc" + getName() + "_MarkerImage(document.Marker" + sName + getName() + ");");
                tdLine += ("popup" + subMenuName + getName() + "Popup();");

                if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
                {
                    if (!bSelected)
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSTYLE + "';\"");
                    }
                    else
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SHOWPOPUPSELECTEDSTYLE + "';\"");
                    }
                }
                else
                {
                    if (!bSelected)
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + HREFSTYLE + "';\"");
                    }
                    else
                    {
                        tdLine += ("\n" + "document.getElementById('" + sName + getName() + "').className='" + getName() + SELECTEDSTYLE + "';\"");
                    }
                }
            }

            if (isIE6())
            {
                tdLine = getSubmenuMouseEvents(tdLine, showPopup, bSelected, sName, sBgColor, subMenuName);
            }

            tdLine += " ONCLICK=\"return false;\"";

            if (!Util.isNull(sHref) && !Util.isEmpty(sHref))
            {
                tdLine += (" HREF=\"" + sHref + "\"");
            }

            if (sTarget != null)
            {
                tdLine += (" TARGET=\"" + sTarget + "\"");
            }

            if (showPopup && ((_showPopupStyle != null) && (!_showPopupStyle.equals(""))))
            {
                if (!bSelected)
                {
                    tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSTYLE + "\">");
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SHOWPOPUPSELECTEDSTYLE + "\">");
                }
            }
            else
            {
                if (!bSelected)
                {
                    tdLine += (" CLASS=\"" + getName() + HREFSTYLE + "\">");
                }
                else
                {
                    tdLine += (" CLASS=\"" + getName() + SELECTEDSTYLE + "\">");
                }
            }

            tdLine += sTitle;
            tdLine += "</A>";
            tdLine += "\n</TD>";
            tdLine += "\n</TR>";
            tdLine += "\n</TABLE>\n";

            if (!_dreamMode)
            {
                tdLine += "</A>";
            }

            //
            tdLine += "</TD>";
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("generateTDSubMenuLine", e, this);
        }

        return tdLine;
    }

    /**
     * Generates a TD That is not a Href (plain Text) line inside the NavBar Table with the appropriate attributes
     *
     * @param sName DOCUMENT ME!
     * @param sTitle DOCUMENT ME!
     * @param sHpad DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private String generateTDTextLine(String sName, String sTitle, int sHpad)
    {
        String tdLine = "";

        tdLine = "<TD CLASS=" + getName() + CELLBGSTYLE + " NOWRAP>";
        tdLine += ("<IMG SRC=\"" + _textImage + "\"");
        tdLine += (" NAME=\"Marker" + sName + getName() + "\" BORDER=\"0\"" + " HSPACE=\"" + sHpad + "\">");
        tdLine += ("<SPAN CLASS=\"" + getName() + TEXTSTYLE + "\">" + sTitle + "</SPAN></TD>");

        return tdLine;
    }

    /**
     * Check if Current Item is selected
     *
     * @return boolean
     */
    private boolean oneSelected()
    {
        boolean selected = false;

        for (int i = 1; i <= _groups.size(); i++)
        {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
            {
                continue;
            }

            for (int j = 1; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++)
            {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                {
                    continue;
                }

                boolean sSelected = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._selected;

                if (sSelected)
                {
                    return true;
                }
            }
        }

        return selected;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer writeNS6getPixelLeftFunc(StringBuffer sScript, String subMenu)
    {
        sScript.append("\n function  popup" + subMenu + getName() + "getPixelLeft(htmlElementLeft)");
        sScript.append("\n { ");
        sScript.append("\n  xPos = htmlElementLeft.offsetLeft;");
        sScript.append("\n  tempEl = htmlElementLeft.offsetParent;");
        sScript.append("\n  while (tempEl != null) {");
        sScript.append("\n			xPos += tempEl.offsetLeft;");
        sScript.append("\n			tempEl = tempEl.offsetParent;");
        sScript.append("\n	 } ");
        sScript.append("\n  return xPos;");
        sScript.append("\n } ");

        return sScript;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sScript DOCUMENT ME!
     * @param subMenu DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringBuffer writeNS6getPixelTopFunc(StringBuffer sScript, String subMenu)
    {
        sScript.append("\n function  popup" + subMenu + getName() + "getPixelTop(htmlElementTop)");
        sScript.append("\n { ");
        sScript.append("\n  yPos = htmlElementTop.offsetTop;");
        sScript.append("\n  tempEl = htmlElementTop.offsetParent;");
        sScript.append("\n  while (tempEl != null) {");
        sScript.append("\n			yPos += tempEl.offsetTop;");
        sScript.append("\n			tempEl = tempEl.offsetParent;");
        sScript.append("\n	 } ");
        sScript.append("\n  return yPos;");
        sScript.append("\n } ");

        return sScript;
    }

    /////////////
    public class Group implements java.io.Serializable
    {
        String _bgColor;
        String _groupHoverStyle;

        // Saqib Chowdhry Jul 30, 2003 (11:34:12 AM)
        // adding more variables for greater flexibility
        String  _groupHrefStyle;
        String  _groupTitle;
        String  _href;
        String  _image;
        String  _name;
        Vector  _items;
        boolean _expanded    = true;
        boolean _selected    = false;
        boolean _visible     = true;
        int     _vertPadding;
    }

    public class Item implements java.io.Serializable
    {
        String  _href;
        String  _itemBgcolor;
        String  _name;
        String  _submenu;
        String  _target;
        String  _title;
        boolean _selected     = false;
        boolean _showPopup;
        boolean _visible      = true;
        int     _horizPadding;
    }

    public class SubItem implements java.io.Serializable
    {
        String  _href;
        String  _name;
        String  _subItemBgColor;
        String  _subMenu;
        String  _subMenuGroup;
        String  _target;
        String  _title;
        boolean _selected     = false;
        boolean _visible      = true;
        int     _horizPadding;
    }
}
