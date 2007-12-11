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
package com.salmonllc.gui;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/gui/NavigationMenu.java $
//$Author: Dan $
//$Revision: 83 $
//$Modtime: 6/11/03 4:37p $
/////////////////////////

/**
 * Licensed Material - Property of Salmon LLC
 * (C) Copyright Salmon LLC. 1999 - All Rights Reserved
 * For more information go to www.salmonllc.com
 *
 * *************************************************************************
 * DISCLAIMER:
 * The following code has been created by Salmon LLC. The code is provided
 * 'AS IS' , without warranty of any kind unless covered in another agreement
 * between your corporation and Salmon LLC.  Salmon LLC shall not be liable
 * for any damages arising out of your use of this, even if they have been
 * advised of the possibility of such damages.
 * *************************************************************************
 *
 */

import com.salmonllc.html.*;
import com.salmonllc.properties.Props;
import java.util.*;

/**
 * Creates a navigation menu for a web site
 */
public class NavigationMenu extends HtmlContainer {
    // If a new mode is added be sure to define it sequentially.
    Hashtable _nav;
    String _groupname;
    HtmlTable _table;
    HtmlStyle _linkstyle;
    HtmlStyle _linkhoverstyle;
    HtmlStyle _linkcellhoverstyle;
    HtmlStyle _cellStyle;
    HtmlStyle _cellHoverStyle;
    HtmlStyle _selectedCellStyle;
    HtmlStyle _selectedStyle;
    HtmlStyle _selectedHoverStyle;
    HtmlStyle _selectedCellHoverStyle;
    HtmlStyle _fontStyle;
    HtmlStyle _linkfontstyle;
    HtmlStyle _linkfonthoverstyle;
    HtmlStyle _selectedFontStyle;
    HtmlStyle _selectedFontHoverStyle;
    int _selectedGroupIndex = -1;
    int _selectedItemIndex = -1;
    int _sizeOption = 0;
    int _width = 100;
    int _layerwidth = 100;
    String _selectedItemMarkerImg;
    String _selectedItemMarkerMouseOverImg;
    String _itemMarkerImg;
    String _itemTitleMarkerImg;
    String _itemMarkerMouserOverImg;
    HtmlLayer _layer;
    boolean _enableLayer = false;
    Vector _groups;
    int _NavTableBorder;
    int _NavTableCellSpacing;
    int _NavTableCellPadding;
    int _NavTableLayerWidth;
    int _NavTableLayerHeight;
    int _NavTableLayerTop;
    int _NavTableLayerLeft;
    String _NavTableBackgroundColor;
    String _cellAlign;
    String _cellVertAlign;
    boolean _wrap;
    boolean _disableCellLink;
    String _cellBackgroundColor;
    Vector _popups = new Vector();
    private String _parentgroup;
    private boolean _useParentAttributes = false;

    private class Group implements NavigationGroupInterface {
        String _image;
        int _vertPadding;
        Vector _items = new Vector();
        boolean _visible = true;
        String _href;

        public String getImage() {
            return _image;
        }

        public void setImage(String sImage) {
            _image = sImage;
        }

        public int getVertPadding() {
            return _vertPadding;
        }

        public void setVertPadding(int iVertPadding) {
            _vertPadding = iVertPadding;
        }

        public boolean getVisible() {
            return _visible;
        }

        public void setVisible(boolean bVisible) {
            _visible = bVisible;
        }

        public NavigationItemInterface[] getItems() {
            if (_items == null || _items.size() == 0)
                return null;
            NavigationItemInterface[] aNii = new NavigationItemInterface[_items.size()];
            _items.toArray(aNii);
            return aNii;
        }

        public NavigationItemInterface addItem(String submenu, String title, String href, String target, int horizPadding) {
            Item item = new Item();
            item._title = title;
            item._horizPadding = horizPadding;
            item._href = href;
            item._target = target;
            item._submenu = submenu;
            _items.addElement(item);
            return item;
        }

        public NavigationItemInterface insertItemAt(String submenu, String title, String href, String target, int horizPadding, int iLocation) {
            Item item = new Item();
            item._title = title;
            item._horizPadding = horizPadding;
            item._href = href;
            item._target = target;
            item._submenu = submenu;
            _items.insertElementAt(item, iLocation - 1);
            return item;
        }

        public NavigationItemInterface insertItemAt(String submenu, String title, String href, String target, int horizPadding, NavigationItemInterface niiInsertBefore) {
            Item item = new Item();
            item._title = title;
            item._horizPadding = horizPadding;
            item._href = href;
            item._target = target;
            item._submenu = submenu;
            _items.insertElementAt(item, _items.indexOf((Item) niiInsertBefore));
            return item;
        }

        public String getHRef() {
            return _href;
        }

        public void setHRef(String sHRef) {
            _href = sHRef;
        }

        public void collapse() {
            for (int i = 0; i < _items.size(); i++)
                ((Item) _items.elementAt(i))._visible = false;
        }

        public void contract() {
            collapse();
        }

        public void expand() {
            for (int i = 0; i < _items.size(); i++)
                ((Item) _items.elementAt(i))._visible = true;
        }

    }

    private class Item implements NavigationItemInterface {
        String _title;
        String _target;
        String _href;
        String _submenu;
        int _horizPadding;
        boolean _visible = true;

        public String getTitle() {
            return _title;
        }

        public void setTitle(String sTitle) {
            _title = sTitle;
        }

        public String getTarget() {
            return _target;
        }

        public void setTarget(String sTarget) {
            _target = sTarget;
        }

        public String getHRef() {
            return _href;
        }

        public void setHRef(String sHRef) {
            _href = sHRef;
        }

        public String getSubMenu() {
            return _submenu;
        }

        public void setSubMenu(String sSubMenu) {
            _submenu = sSubMenu;
        }

        public int getHorizPadding() {
            return _horizPadding;
        }

        public void setHorizPadding(int iHorizPadding) {
            _horizPadding = iHorizPadding;
        }


        public boolean getVisible() {
            return _visible;
        }

        public void setVisible(boolean bVisible) {
            _visible = bVisible;
        }

    }

    HtmlScript _imagesScript;

    /**
     * NavigationMenu Constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     * @param groupname java.lang.String Name of group in properties files for generating menu.
     */
    public NavigationMenu(HtmlPage p) throws Exception {
        super("", p);
        _nav = new Hashtable();
    }

    /**
     * This constructor creates a NavigationMenu using properties from a group with a groupname of default.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public NavigationMenu(String name, HtmlPage p) throws Exception {
        this(name, p, "default");

    }

    /**
     * NavigationMenu Constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     * @param groupname java.lang.String Name of group in properties files for generating menu.
     */
    public NavigationMenu(String name, HtmlPage p, String groupname) throws Exception {
        super(name, p);
        _nav = new Hashtable();
        _groupname = groupname;
        Props pr = p.getPageProperties();
        String NavTableIsLayer = pr.getProperty(_groupname + ".IsLayer");
        if (NavTableIsLayer != null && !NavTableIsLayer.trim().equals("") && NavTableIsLayer.toUpperCase().charAt(0) == 'Y')
            _enableLayer = true;
        initNavigationProperties();
        createTable();
    }

    /**
     * NavigationMenu Constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     * @param groupname java.lang.String Name of group in properties files for generating menu.
     */
    public NavigationMenu(String name, HtmlPage p, String groupname, String parentgroup) throws Exception {
        super(name, p);
        _nav = new Hashtable();
        _groupname = groupname;
        _parentgroup = parentgroup;
        Props pr = p.getPageProperties();
        String NavTableIsLayer = pr.getProperty(_groupname + ".IsLayer");
        if (NavTableIsLayer != null && !NavTableIsLayer.trim().equals("") && NavTableIsLayer.toUpperCase().charAt(0) == 'Y')
            _enableLayer = true;
        initNavigationProperties();
        createTable();
    }

    /**
     * NavigationMenu Constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     * @param groupname java.lang.String Name of group in properties files for generating menu.
     */
    public NavigationMenu(String name, HtmlPage p, String groupname, boolean bSubmenu) throws Exception {
        super(name, p);
        _nav = new Hashtable();
        _groupname = groupname;
        Props pr = p.getPageProperties();
        String NavTableIsLayer = pr.getProperty(_groupname + ".IsLayer");
        if (NavTableIsLayer != null && !NavTableIsLayer.trim().equals("") && NavTableIsLayer.toUpperCase().charAt(0) == 'Y')
            _enableLayer = true;
        initNavigationProperties();
        createTable();
        if (bSubmenu)
        	bSubmenu=false;
    }

    /**
     * Adds a new html component to the container
     * @param comp com.salmonllc.html.HtmlComponent
     */
    public void add(HtmlComponent comp) {
        if (_enableLayer)
            _layer.add(comp);
        else
            super.add(comp);
    }

    /**
     * Adds a group to the NavigationMenu
     * @param image java.lang.String Filename of the Image
     * @param vertPadding int The number of pixels providing vertical padding for the group
     */
    public NavigationGroupInterface addGroup(String image, int vertPadding) {
        Group group = new Group();
        group._image = image;
        group._vertPadding = vertPadding;
        _groups.addElement(group);
        return group;
    }

    /**
     * Adds a menu item to Navigation Menu
     * @param menu int Index of the group menu to add to.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     */
    public int addMenuItem(int menu, String title, String href, String target, int horizPadding) throws Exception {
        return addMenuItem(menu, title, href, target, horizPadding, true);
    }

    /**
     * Adds a menu item to Navigation Menu
     * @param menu int Index of the group menu to add to.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param createTable boolean Indicates whether to regenerate the html for the navigation menu.
     */
    public int addMenuItem(int menu, String title, String href, String target, int horizPadding, boolean createTable) throws Exception {
        Item item = new Item();
        item._title = title;
        item._horizPadding = horizPadding;
        item._href = href;
        item._target = target;
        ((Group) _groups.elementAt(menu - 1))._items.addElement(item);
        if (createTable)
            createTable();
        return ((Group) _groups.elementAt(menu - 1))._items.indexOf(item) + 1;
    }

    /**
     * Adds a menu item to Navigation Menu
     * @param menu int Index of the group menu to add to.
     * @param submenu java.lang.String submenu name of the item added. Indicates item is a submenu.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     */
    public int addMenuItem(int menu, String submenu, String title, String href, String target, int horizPadding) throws Exception {
        return addMenuItem(menu, submenu, title, href, target, horizPadding, true);
    }

    /**
     * Adds a menu item to Navigation Menu
     * @param menu int Index of the group menu to add to.
     * @param submenu java.lang.String submenu name of the item added. Indicates item is a submenu.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param createTable boolean Indicates whether to regenerate the html for the navigation menu.
     */
    public int addMenuItem(int menu, String submenu, String title, String href, String target, int horizPadding, boolean createTable) throws Exception {
        Item item = new Item();
        item._title = title;
        item._horizPadding = horizPadding;
        item._href = href;
        item._target = target;
        item._submenu = submenu;
        ((Group) _groups.elementAt(menu - 1))._items.addElement(item);
        if (createTable)
            createTable();
        return ((Group) _groups.elementAt(menu - 1))._items.indexOf(item) + 1;
    }

    /**
     * This method clears the selected menu item.
     */
    public void clearSelectedItem() {
        if (_selectedGroupIndex <= 0)
            return;
        if (_selectedItemIndex <= 0)
            return;
        String sItemContainerKey = _groupname + ".Group" + _selectedGroupIndex + ".Item" + _selectedItemIndex;
        String sItemCellPropKey = _groupname + ".Group" + _selectedGroupIndex + ".Item" + _selectedItemIndex + "CELLPROP";
        HtmlContainer hc = (HtmlContainer) _nav.get(sItemContainerKey);
        if (hc == null)
            return;
        HtmlTableCellProperties htcp = (HtmlTableCellProperties) _nav.get(sItemCellPropKey);
        HtmlLink hl = null;
        HtmlImage hi = null;
        Enumeration enumera = hc.getComponents();
        while (enumera.hasMoreElements()) {
            HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
            if (hcp instanceof HtmlLink) {
                hl = (HtmlLink) hcp;
            }
            if (hcp instanceof HtmlImage) {
                if (hcp.getName().startsWith("itemMarker")) {
                    hi = (HtmlImage) hcp;
                }
            }
        }
        if (hi != null) {
            if (_itemMarkerImg != null)
                hi.setSource(_itemMarkerImg);
        }
        if (hl != null) {
            if (_selectedStyle != null)
                hl.setStyle(_linkstyle);
            if (_itemMarkerImg != null && hi != null && _itemMarkerMouserOverImg != null)
                hl.setOnMouseOut("fnc" + getFullName() + "_ItemMarkerImage(document." + hi.getFullName() + ");");
//	  hl.setOnMouseOut("document."+hi.getFullName()+".src='"+_itemMarkerImg+"';");
            if (_itemMarkerMouserOverImg != null && hi != null)
                hl.setOnMouseOver("fnc" + getFullName() + "_ItemMarkerMouseOverImage(document." + hi.getFullName() + ");");
//	  hl.setOnMouseOver("document."+hi.getFullName()+".src='"+_itemMarkerMouserOverImg+"';");
        }
        if (htcp != null) {
            htcp.setStyle(_cellStyle);
            if (hl != null) {
                String sMouseOver = "this.className='" + _cellHoverStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _linkcellhoverstyle.getStyleName() + "';";
                String sMouseOut = "this.className='" + _cellStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _linkstyle.getStyleName() + "';";
                if (hi != null) {
                    sMouseOver += hl.getOnMouseOver();
                    sMouseOut += hl.getOnMouseOut();
                }
                htcp.setOnMouseOver(sMouseOver);
                htcp.setOnMouseOut(sMouseOut);
                htcp.setOnClick("document.location='" + hl.getHref() + "';");
            }
        }
        _selectedGroupIndex = -1;
        _selectedItemIndex = -1;
    }

    /**
     * Creates a group menu in the Navigation Menu.
     * @param image java.lang.String The filename of the image to represent the group
     * @param vertPadding int The vertical padding for the group
     */
    public int createMenu(String image, int vertPadding) throws Exception {
        return createMenu(image, vertPadding, true);
    }

    /**
     * Creates a group menu in the Navigation Menu.
     * @param image java.lang.String The filename of the image to represent the group
     * @param vertPadding int The vertical padding for the group
     * @param createTable boolean Indicates whether to regenerate the html for the navigation menu.
     */
    public int createMenu(String image, int vertPadding, boolean createTable) throws Exception {
        Group group = new Group();
        group._image = image;
        group._vertPadding = vertPadding;
        _groups.addElement(group);
        if (createTable)
            createTable();
        return _groups.indexOf(group) + 1;
    }

    private void createTable() throws Exception {
        // get NavTable Properties
        HtmlPage page = getPage();
        removeAll();
        removePopups();
        if (_parentgroup == null) {
            if (_linkstyle != null)
                super.add(_linkstyle);
            if (_linkhoverstyle != null)
                super.add(_linkhoverstyle);
            if (_linkfontstyle != null)
                super.add(_linkfontstyle);
            if (_linkfonthoverstyle != null)
                super.add(_linkfonthoverstyle);
            if (_linkcellhoverstyle != null)
                super.add(_linkcellhoverstyle);
            if (_cellStyle != null)
                super.add(_cellStyle);
            if (_cellHoverStyle != null)
                super.add(_cellHoverStyle);
            if (_selectedStyle != null)
                super.add(_selectedStyle);
            if (_selectedHoverStyle != null)
                super.add(_selectedHoverStyle);
            if (_selectedFontStyle != null)
                super.add(_selectedFontStyle);
            if (_selectedFontHoverStyle != null)
                super.add(_selectedFontHoverStyle);
            if (_selectedCellStyle != null)
                super.add(_selectedCellStyle);
            if (_selectedCellHoverStyle != null)
                super.add(_selectedCellHoverStyle);
            if (_fontStyle != null)
                super.add(_fontStyle);
        }
        _imagesScript = new HtmlScript(getImagesScript(), "JavaScript1.2", getPage());
        super.add(_imagesScript);

        HtmlContainer cont = this;
        if (_enableLayer) {
            HtmlStyle layerStyle = newStyle(_groupname + "Layer", "#", _groupname + ".LayerStyle");
            super.add(layerStyle);
            _layer = new HtmlLayer(_groupname, layerStyle, getPage());
            int height = _NavTableLayerHeight;
            _layer.setHeight(height);
            _layerwidth = _NavTableLayerWidth;
            _layer.setWidth(_layerwidth);
            int top = _NavTableLayerTop;
            _layer.setTop(top);
            int left = _NavTableLayerLeft;
            _layer.setLeft(left);
            getPage().add(_layer);
            cont = _layer;
        }
        _table = new HtmlTable("tbl", page);
        _table.setBorder(_NavTableBorder);
        _table.setCellSpacing(_NavTableCellSpacing);
        _table.setCellPadding(_NavTableCellPadding);
        _table.setBackgroundColor(_NavTableBackgroundColor);
        cont.add(_table);

        // NavTable Cell Properties
        HtmlTableCellProperties navTableCellProp = new HtmlTableCellProperties();
        navTableCellProp.setAlign(_cellAlign);
        navTableCellProp.setVertAlign(_cellVertAlign);
        navTableCellProp.setWrap(_wrap);
        navTableCellProp.setBackgroundColor(_cellBackgroundColor);
        navTableCellProp.setStyle(_cellStyle);

        int row = 0;
        HtmlTable t = _table;
        int i = 1;
        for (; i <= _groups.size(); i++) {
            if (((Group) _groups.elementAt(i - 1))._visible == false)
                continue;
            String sNavTableImg = ((Group) _groups.elementAt(i - 1))._image;
            String sNavTableImgHref = ((Group) _groups.elementAt(i - 1))._href;
            int NavTableVertPadding = ((Group) _groups.elementAt(i - 1))._vertPadding;
            
           
            HtmlImage img;
            img = newVerticalSpacer(NavTableVertPadding);
            if (img != null)
                t.setComponentAt(row++, 0, img);
            if (sNavTableImg != null && !sNavTableImg.equals("")) {
                img = new HtmlImage(sNavTableImg, page);
                if (sNavTableImgHref != null && !sNavTableImgHref.equals("")) {
                    HtmlLink hlImg = new HtmlLink(_groupname + "GroupLink" + i, sNavTableImgHref, page);
                    hlImg.add(img);
                    t.setComponentAt(row++, 0, hlImg);
                } else
                    t.setComponentAt(row++, 0, img);
                img.setVerticalSpace(0);
                img.setHorizontalSpace(0);
                String sImageKey = _groupname + ".Group" + i + ".Image";
                _nav.put(sImageKey, img);
            }
            int j = 1;
            for (; j <= ((Group) _groups.elementAt(i - 1))._items.size(); j++) {
                if (((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._visible == false)
                    continue;
                String sItemKey = _groupname + ".Group" + i + ".Item" + j;
                String sNavGroupEntryTitle = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._title;
                String sNavGroupEntryHref = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._href;
                String sNavGroupEntrySubmenu = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._submenu;
                String sNavGroupEntryTarget = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._target;
                HtmlContainer hc;
                if (isPage(sNavGroupEntryHref) && _selectedGroupIndex == -1 && _selectedItemIndex == -1) {
                    _selectedGroupIndex = i;
                    _selectedItemIndex = j;
                }
                int iNavGroupEntrySpacer = ((Item) ((Group) _groups.elementAt(i - 1))._items.elementAt(j - 1))._horizPadding;
                if (sNavGroupEntrySubmenu != null && !sNavGroupEntrySubmenu.trim().equals(""))
                    if (iNavGroupEntrySpacer > 0)
                        hc = newEntry(sNavGroupEntrySubmenu, sNavGroupEntryHref, sNavGroupEntryTarget, sNavGroupEntryTitle, _groupname + "Group" + i + "Mark" + j, iNavGroupEntrySpacer);
                    else
                        hc = newEntry(sNavGroupEntrySubmenu, sNavGroupEntryHref, sNavGroupEntryTarget, sNavGroupEntryTitle, _groupname + "Group" + i + "Mark" + j);
                else {
                    if (iNavGroupEntrySpacer > 0)
                        hc = newEntry(sNavGroupEntryHref, sNavGroupEntryTarget, sNavGroupEntryTitle, _groupname + "Group" + i + "Mark" + j, iNavGroupEntrySpacer);
                    else
                        hc = newEntry(sNavGroupEntryHref, sNavGroupEntryTarget, sNavGroupEntryTitle, _groupname + "Group" + i + "Mark" + j);
                }
                HtmlTableCellProperties navLinkTableCellProp = new HtmlTableCellProperties();
                if (!_cellHoverStyle.getStyleName().trim().equals("")) {
                    HtmlLink hlLink = null;
                    Enumeration enumera = hc.getComponents();
                    while (enumera.hasMoreElements()) {
                        HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
                        if (hcp instanceof HtmlLink) {
                            hlLink = (HtmlLink) hcp;
                            Enumeration enumLink = hlLink.getComponents();
                            outer:
                            while (enumLink.hasMoreElements()) {
                                HtmlComponent hcpLink = (HtmlComponent) enumLink.nextElement();
                                if (hcpLink instanceof HtmlTable) {
                                    HtmlContainer hcCont = (HtmlContainer) ((HtmlTable) hcpLink).getComponents().nextElement();
                                    Enumeration enumCont = hcCont.getComponents();
                                    while (enumCont.hasMoreElements()) {
                                        HtmlComponent hcpCont = (HtmlComponent) enumCont.nextElement();
                                        if (hcpCont instanceof HtmlLink) {
                                            hlLink = (HtmlLink) hcpCont;
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }

                    }
                    navLinkTableCellProp.setAlign(_cellAlign);
                    navLinkTableCellProp.setVertAlign(_cellVertAlign);
                    navLinkTableCellProp.setWrap(_wrap);
                    navLinkTableCellProp.setBackgroundColor(_cellBackgroundColor);
                    navLinkTableCellProp.setStyle(_cellStyle);
                    if (hlLink != null) {
                        String sMouseOver = "this.className='" + _cellHoverStyle.getStyleName() + "';" + hlLink.getFullName() + ".className='" + _linkcellhoverstyle.getStyleName() + "';";
                        String sMouseOut = "this.className='" + _cellStyle.getStyleName() + "';" + hlLink.getFullName() + ".className='" + _linkstyle.getStyleName() + "';";
                        if (hlLink.getOnMouseOver() != null)
                            sMouseOver += hlLink.getOnMouseOver();
                        if (hlLink.getOnMouseOut() != null)
                            sMouseOut += hlLink.getOnMouseOut();
                        if (!_disableCellLink) {
                            navLinkTableCellProp.setOnMouseOver(sMouseOver);
                            navLinkTableCellProp.setOnMouseOut(sMouseOut);
                            if (sNavGroupEntrySubmenu == null || sNavGroupEntrySubmenu.trim().equals(""))
                                navLinkTableCellProp.setOnClick("document.location='" + hlLink.getHref() + "';");
                        }
                    }
                } else
                    navLinkTableCellProp = navTableCellProp;

                t.setComponentAt(row++, 0, hc, navLinkTableCellProp);
                _nav.put(sItemKey, hc);
                _nav.put(sItemKey + "CELLPROP", navLinkTableCellProp);
            }
        }
        if (_selectedGroupIndex > 0 && _selectedItemIndex > 0)
            setSelectedItem(_selectedGroupIndex, _selectedItemIndex);
        _table.setSizeOption(_sizeOption);
        _table.setWidth(_width);
        if (_popups.size() > 0) {
            for (int j = 0; j < _popups.size(); j++)
                page.addPopup((HtmlPopup) _popups.elementAt(j));
        }
        if (_enableLayer) {
            super.add(getScrollScript());
        }

    }

    /**
     * Returns whether the cell of the Navigation Menu can act as a link.
     * @return boolean Indicates whether cells in the Navigation Menu act as links.
     */
    public boolean getDisableCellLink() {
        return _disableCellLink;
    }

    /**
     * Returns an array Group Objects representing the Groups in the Navigation Menu.
     * @return NavigationGroupInterface[] Array of groups in NavigationMenu.
     */
    public NavigationGroupInterface[] getGroups() {
        if (_groups == null || _groups.size() == 0)
            return null;
        NavigationGroupInterface[] aNgi = new NavigationGroupInterface[_groups.size()];
        _groups.toArray(aNgi);

        return aNgi;
    }

    private String getImagesScript() {
        String sScript = "var " + getFullName() + "_loading=false;";
        sScript += "function " + getFullName() + "_imageLoaded(e) {";
        sScript += getFullName() + "_loading=false;";
        sScript += "}";
        if (_itemMarkerImg != null && !_itemMarkerImg.equals("")) {
            sScript += "\nvar " + getFullName() + "_ItemMarkerImage=new Image();\n";
            sScript += getFullName() + "_ItemMarkerImage.onload=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerImage.onerror=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerImage.onabort=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerImage.src=\"" + _itemMarkerImg + "\";\n";
            sScript += "function fnc" + getFullName() + "_ItemMarkerImage(source) {\n";
            if (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) {
                sScript += "if(" + getFullName() + "_loading) {";
                sScript += "if (source!=null) \n";
                sScript += "setTimeout('fnc" + getFullName() + "_ItemMarkerImage(document.'+source.name+');',100);";
                sScript += "return;";
                sScript += "}";
                sScript += "if (source==null) \n";
                sScript += "return;\n";
                sScript += getFullName() + "_loading=true;\n";
            }
            sScript += "source.src=" + getFullName() + "_ItemMarkerImage.src;\n";
            sScript += "};\n";
        }
        if (_itemMarkerMouserOverImg != null && !_itemMarkerMouserOverImg.equals("")) {
            sScript += "\nvar " + getFullName() + "_ItemMarkerMouseOverImage=new Image();\n";
            sScript += getFullName() + "_ItemMarkerMouseOverImage.onload=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerMouseOverImage.onerror=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerMouseOverImage.onabort=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemMarkerMouseOverImage.src=\"" + _itemMarkerMouserOverImg + "\";\n";
            sScript += "function fnc" + getFullName() + "_ItemMarkerMouseOverImage(source) {\n";
            if (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) {
                sScript += "if(" + getFullName() + "_loading) {";
                sScript += "if (source!=null) \n";
                sScript += "setTimeout('fnc" + getFullName() + "_ItemMarkerMouseOverImage(document.'+source.name+');',100);";
                sScript += "return;";
                sScript += "}";
                sScript += "if (source==null) \n";
                sScript += "return;\n";
                sScript += getFullName() + "_loading=true;\n";
            }
            sScript += "source.src=" + getFullName() + "_ItemMarkerMouseOverImage.src;\n";
            sScript += "};\n";
        }
        if (_itemTitleMarkerImg != null && !_itemTitleMarkerImg.equals("")) {
            sScript += "\nvar " + getFullName() + "_ItemTitleMarkerImage=new Image();\n";
            sScript += getFullName() + "_ItemTitleMarkerImage.onload=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemTitleMarkerImage.onerror=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemTitleMarkerImage.onabort=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_ItemTitleMarkerImage.src=\"" + _itemTitleMarkerImg + "\";\n";
            sScript += "function fnc" + getFullName() + "_ItemTitleMarkerImage(source) {\n";
            if (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) {
                sScript += "if(" + getFullName() + "_loading) {";
                sScript += "if (source!=null) \n";
                sScript += "setTimeout('fnc" + getFullName() + "_ItemTitleMarkerImage(document.'+source.name+');',100);";
                sScript += "return;";
                sScript += "}";
                sScript += "if (source==null) \n";
                sScript += "return;\n";
                sScript += getFullName() + "_loading=true;\n";
            }
            sScript += "source.src=" + getFullName() + "_ItemTitleMarkerImage.src;\n";
            sScript += "};\n";
        }
        if (_selectedItemMarkerImg != null && !_selectedItemMarkerImg.equals("")) {
            sScript += "\nvar " + getFullName() + "_SelectedItemMarkerImage=new Image();\n";
            sScript += getFullName() + "_SelectedItemMarkerImage.onload=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerImage.onerror=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerImage.onabort=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerImage.src=\"" + _selectedItemMarkerImg + "\";\n";
            sScript += "function fnc" + getFullName() + "_SelectedItemMarkerImage(source) {\n";
            if (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) {
                sScript += "if(" + getFullName() + "_loading) {";
                sScript += "if (source!=null) \n";
                sScript += "setTimeout('fnc" + getFullName() + "_SelectedItemMarkerImage(document.'+source.name+');',100);";
                sScript += "return;";
                sScript += "}";
                sScript += "if (source==null) \n";
                sScript += "return;\n";
                sScript += getFullName() + "_loading=true;\n";
            }
            sScript += "source.src=" + getFullName() + "_SelectedItemMarkerImage.src;\n";
            sScript += "};\n";
        }
        if (_selectedItemMarkerMouseOverImg != null && !_selectedItemMarkerMouseOverImg.equals("")) {
            sScript += "\nvar " + getFullName() + "_SelectedItemMarkerMouseOverImage=new Image();\n";
            sScript += getFullName() + "_SelectedItemMarkerMouseOverImage.onload=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerMouseOverImage.onerror=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerMouseOverImage.onabort=this." + getFullName() + "_imageLoaded;";
            sScript += getFullName() + "_SelectedItemMarkerMouseOverImage.src=\"" + _selectedItemMarkerMouseOverImg + "\";\n";
            sScript += "function fnc" + getFullName() + "_SelectedItemMarkerMouseOverImage() {\n";
            if (getPage().getBrowserType() == HtmlPageBase.BROWSER_MICROSOFT) {
                sScript += "if(" + getFullName() + "_loading) {";
                sScript += "if (source!=null) \n";
                sScript += "setTimeout('fnc" + getFullName() + "_SelectedItemMarkerMouseOverImage(document.'+source.name+');',100);";
                sScript += "return;";
                sScript += "}";
                sScript += "if (source==null) \n";
                sScript += "return;\n";
                sScript += getFullName() + "_loading=true;\n";
            }
            sScript += "source.src=" + getFullName() + "_SelectedItemMarkerMouseOverImage.src;\n";
            sScript += "};\n";
        }
        return sScript;
    }

    private String getLayerName() {
        return _layer.getFullName();
    }

    private int getLayerShowXCoord() {
        if (getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT && getPage().getBrowserVersion() >= 4)
            return 1;
        if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE && getPage().getBrowserVersion() >= 4)
            return 2;
        return 0;
    }

    private int getLayerWidth() {
        return _layerwidth;
    }

    /**
     * Returns a vector of com.salmonllc.html.HtmlPopups belonging to this navigation menu.
     * Creation date: (8/16/01 4:01:16 PM)
     * @return java.util.Vector Vector of com.salmonllc.html.HtmlPopups
     */
    public Vector getPopups() {
        return _popups;
    }

    private HtmlScript getScrollScript() {
        String sScript = "\n\r\tvar hideFlag=0;\n\r\tvar showFlag=0;\n\r\tvar xCoord=0;";
        sScript += "\n\r\tvar yCoord=0;\n\r\tvar yMenuCoord=0;\n\r\tfunction mouseTracker(e) {\n\r\te = e || window.Event || window.event;";
        sScript += "\n\r\txCoord = e.pageX || e.clientX;\n\r\tyCoord = e.pageY || e.clientY;\n\r\tif (xCoord<" + getLayerShowXCoord() + " && hideFlag==0) {";
        sScript += "\n\r\tyMenuCoord=yCoord;\n\r\tshowmenu();}\n\r\tif (document.all) {\n\r\tif (xCoord>" + getLayerName() + ".style.pixelWidth && showFlag==0)";
        sScript += "\n\r\thidemenu();\n\r\tif (" + getLayerName() + ".style.pixelLeft>=0 && showFlag==1)\n\r\tshowFlag=0;";
        sScript += "\n\r\tif (" + getLayerName() + ".style.pixelLeft<=-" + getLayerName() + ".style.pixelWidth && hideFlag==1)\n\r\thideFlag=0;\n\r\t}";
        sScript += "\n\r\telse {\n\r\tif (xCoord>" + getLayerWidth() + " && showFlag==0)\n\r\thidemenu();";
        sScript += "\n\r\tif (document." + getLayerName() + ".left>=0 && showFlag==1)\n\r\tshowFlag=0;";
        sScript += "\n\r\tif (document." + getLayerName() + ".left<=-" + getLayerWidth() + " && hideFlag==1)\n\r\thideFlag=0;\n\r\t}\n\r\t}";
        sScript += "\n\r\tfunction setMouseTracker() {\n\r\tif (document.captureEvents) {";
        sScript += "\n\r\tdocument.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);\n\r\t}";
        sScript += "\n\r\tdocument.onmousemove = this.mouseTracker;\n\r\t}\n\r\tfunction hidemenu(){\n\r\tif (document.all) {";
        sScript += "\n\r\tif (" + getLayerName() + ".style.pixelLeft>-" + getLayerName() + ".style.pixelWidth && showFlag==0) {";
        sScript += "\n\r\tshowFlag=0;\n\r\thideFlag=1;\n\r\tsetTimeout(\"" + getLayerName() + ".style.pixelLeft-=1;hidemenu();\",10);";
        sScript += "\n\r\t}\n\r\telse {\n\r\thideFlag=0;\n\r\tshowFlag=0;\n\r\t}\n\r\t}\n\r\telse {";
        sScript += "\n\r\tif (document." + getLayerName() + ".left>-" + getLayerWidth() + " && showFlag==0) {\n\r\tshowFlag=0;";
        sScript += "\n\r\thideFlag=1;\n\r\tsetTimeout(\"document." + getLayerName() + ".left-=1;hidemenu();\",10);\n\r\t}\n\r\telse {";
        sScript += "\n\r\thideFlag=0;\n\r\tshowFlag=0;\n\r\t}\n\r\t}\n\r\t}\n\r\tfunction showmenu(){\n\r\tif (document.all) {";
        sScript += "\n\r\tif (" + getLayerName() + ".style.pixelLeft<0 && hideFlag==0) {\n\r\thideFlag=0;\n\r\tshowFlag=1;";
        sScript += "\n\r\t" + getLayerName() + ".style.pixelTop=yMenuCoord;\n\r\tsetTimeout(\"" + getLayerName() + ".style.pixelLeft+=1;showmenu();\",10);\n\r\t}\n\r\telse {";
        sScript += "\n\r\tshowFlag==0;\n\r\thideFlag=0;\n\r\t}\n\r\t}\n\r\telse {";
        sScript += "\n\r\tif (document." + getLayerName() + ".left<0 && hideFlag==0) {\n\r\thideFlag=0;\n\r\tshowFlag=1;";
        sScript += "\n\r\tdocument." + getLayerName() + ".top=yMenuCoord;\n\r\tsetTimeout(\"document." + getLayerName() + ".left+=1;showmenu();\",10);\n\r\t}\n\r\telse {\n\r\tshowFlag=0;";
        sScript += "\n\r\thideFlag=0;\n\r\t}\n\r\t}\n\r\t}";
        sScript += "\n\r\tfunction initNavPosition() {\n\r\tif(document.all)\n\r\t" + getLayerName() + ".style.pixelLeft=-" + getLayerName() + ".style.pixelWidth;\n\r\telse\n\r\tdocument." + getLayerName() + ".left=-document." + getLayerName() + ".width;\n\r\t}";
        sScript += "\n\r\tsetMouseTracker();\n\r\tsetTimeout(\"initNavPosition()\",100);";
        return new HtmlScript(sScript, "JavaScript1.2", getPage());
    }

    /**
     * Returns a Navigation Menu representing a submenu.
     * @param groupIndex is the group index of the submenu entry.
     * @param itemIndex is the item index of the submenu entry.
     */
    public NavigationMenu getSubMenu(int groupIndex, int itemIndex) {
        NavigationMenu nmReturn = null;
        try {
            for (int i = 0; i < _popups.size(); i++) {
                HtmlPopup hpPop = (HtmlPopup) _popups.elementAt(i);
                String name = "popup" + _groupname + "Group" + groupIndex + "Mark" + itemIndex;
                if (hpPop.getName().equals(name)) {
                    Enumeration enumera = hpPop.getComponents();
                    nmReturn = (NavigationMenu) enumera.nextElement();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nmReturn;
    }

    private void initNavigationProperties() throws Exception {
        // get NavTable Properties
        HtmlPage page = getPage();
        Props pr = page.getPageProperties();
        String sGroupName = _groupname;
        String sStyleGroupName = _groupname;
        if (pr.getProperty(_groupname + ".UseParentAttributes") != null)
            _useParentAttributes = new Boolean(pr.getProperty(_groupname + ".UseParentAttributes")).booleanValue();
        if (_parentgroup != null && !_parentgroup.trim().equals("")) {
            sStyleGroupName = _parentgroup;
            if (_useParentAttributes)
                sGroupName = _parentgroup;
        }
        if (pr.getProperty(sGroupName + ".Border") == null)
            sGroupName = _parentgroup;
        _NavTableBorder = new Integer(pr.getProperty(sGroupName + ".Border")).intValue();
        _NavTableCellSpacing = new Integer(pr.getProperty(sGroupName + ".CellSpacing")).intValue();
        _NavTableCellPadding = new Integer(pr.getProperty(sGroupName + ".CellPadding")).intValue();
        _NavTableBackgroundColor = pr.getProperty(sGroupName + ".BackgroundColor");
        _selectedItemMarkerImg = pr.getProperty(sGroupName + ".Group.SelectedItemMarkerImage");
        _selectedItemMarkerMouseOverImg = pr.getProperty(sGroupName + ".Group.SelectedItemMarkerMouseOverImage");
        _itemMarkerImg = pr.getProperty(sGroupName + ".Group.ItemMarkerImage");
        _itemMarkerMouserOverImg = pr.getProperty(sGroupName + ".Group.ItemMarkerMouseOverImage");
        _itemTitleMarkerImg = pr.getProperty(sGroupName + ".Group.ItemTitleMarkerImage");
        _linkstyle = newStyle(sStyleGroupName, "A", sStyleGroupName + ".LinkStyle");
        _linkhoverstyle = newStyle(sStyleGroupName + ":hover", "A", sStyleGroupName + ".HoverStyle");
        _linkfontstyle = newStyle(sStyleGroupName, "", sStyleGroupName + ".LinkStyle");
        _linkfonthoverstyle = newStyle(sStyleGroupName + "hover", "", sStyleGroupName + ".HoverStyle");
        _linkcellhoverstyle = newStyle(sStyleGroupName + "hover", "A", sStyleGroupName + ".HoverStyle");
        _cellStyle = newStyle(sStyleGroupName + "CELL", "TD", sStyleGroupName + ".CellStyle");
        _cellHoverStyle = newStyle(sStyleGroupName + "CELLhover", "TD", sStyleGroupName + ".CellHoverStyle");
        _selectedStyle = newStyle(sStyleGroupName + "SELECTED", "A", sStyleGroupName + ".SelectedStyle");
        _selectedHoverStyle = newStyle(sStyleGroupName + "SELECTEDhover", "A", sStyleGroupName + ".SelectedHoverStyle");
        _selectedFontStyle = newStyle(sStyleGroupName + "SELECTED", "", sStyleGroupName + ".SelectedStyle");
        _selectedFontHoverStyle = newStyle(sStyleGroupName + "SELECTEDhover", "", sStyleGroupName + ".SelectedHoverStyle");
        _selectedCellStyle = newStyle(sStyleGroupName + "SELECTEDCELL", "TD", sStyleGroupName + ".SelectedCellStyle");
        _selectedCellHoverStyle = newStyle(sStyleGroupName + "SELECTEDCELLhover", "TD", sStyleGroupName + ".SelectedCellHoverStyle");
        _fontStyle = newStyle(sStyleGroupName + "Font", "", sStyleGroupName + ".FontStyle");

        String sCellAlign = pr.getProperty(sGroupName + ".CellAlign");
        String sCellVertAlign = pr.getProperty(sGroupName + ".CellVertAlign");
        boolean bCellWrap = new Boolean(pr.getProperty(sGroupName + ".CellWrap")).booleanValue();
        String sCellBackgroundColor = pr.getProperty(sGroupName + ".CellBackgroundColor");

        _cellAlign = sCellAlign;
        _cellVertAlign = sCellVertAlign;
        _wrap = bCellWrap;
        _cellBackgroundColor = sCellBackgroundColor;

        try {
            _NavTableLayerWidth = new Integer(pr.getProperty(sGroupName + ".LayerWidth")).intValue();
            _NavTableLayerHeight = new Integer(pr.getProperty(sGroupName + ".LayerHeight")).intValue();
            _NavTableLayerTop = new Integer(pr.getProperty(sGroupName + ".LayerTop")).intValue();
            _NavTableLayerLeft = new Integer(pr.getProperty(sGroupName + ".LayerLeft")).intValue();
        } catch (Exception e) {
            ;
        }

        _groups = new Vector();
        int i = 1;
        while (true) {
            if (pr.getProperty(_groupname + ".Group" + i + ".Title1") == null)
                break;
            String sNavTableImg = pr.getProperty(_groupname + ".Group" + i + ".Image");
            String sNavTableImgHref = pr.getProperty(_groupname + ".Group" + i + ".HREF");
            int NavTableVertPadding = 0;
            try {
                NavTableVertPadding = new Integer(pr.getProperty(_groupname + ".Group" + i + ".VertPadding")).intValue();
            } catch (Exception e) {
                ;
            }
            Group group = new Group();
            group._image = sNavTableImg;
            group._href = sNavTableImgHref;
            group._vertPadding = NavTableVertPadding;
            _groups.addElement(group);
            int j = 1;
            while (true) {
                String sNavGroupEntryTitle = pr.getProperty(_groupname + ".Group" + i + ".Title" + j);
                if (sNavGroupEntryTitle == null)
                    break;
                String sNavGroupEntryHref = pr.getProperty(_groupname + ".Group" + i + ".HREF" + j);
                String sNavGroupEntrySubmenu = pr.getProperty(_groupname + ".Group" + i + ".SUBMENU" + j);
                int iNavGroupEntrySpacer = 0;
                try {
                    iNavGroupEntrySpacer = Integer.parseInt(pr.getProperty(_groupname + ".Group" + i + ".Title" + j + ".HorizPadding"));
                } catch (Exception e) {
                    ;
                }
                Item item = new Item();
                item._title = sNavGroupEntryTitle;
                item._href = sNavGroupEntryHref;
                item._submenu = sNavGroupEntrySubmenu;
                item._horizPadding = iNavGroupEntrySpacer;
                group._items.addElement(item);
                j++;
            }
            i++;
        }

    }

    /**
     * Inserts a group into the NavigationMenu at specified location
     * @param image java.lang.String Filename of the Image
     * @param vertPadding int The number of pixels providing vertical padding for the group
     * @param iLocation int The index at which to Insert the Group
     */
    public NavigationGroupInterface insertGroupAt(String image, int vertPadding, int iLocation) {
        Group group = new Group();
        group._image = image;
        group._vertPadding = vertPadding;
        _groups.insertElementAt(group, iLocation - 1);
        return group;
    }

    /**
     * Inserts a group into the NavigationMenu at specified location
     * @param image java.lang.String Filename of the Image
     * @param vertPadding int The number of pixels providing vertical padding for the group
     * @param ngiInsertBefore NavigationGroupInterface The group at which to Insert the Group before.
     */
    public NavigationGroupInterface insertGroupAt(String image, int vertPadding, NavigationGroupInterface ngiInsertBefore) {
        Group group = new Group();
        group._image = image;
        group._vertPadding = vertPadding;
        _groups.insertElementAt(group, _groups.indexOf((Group) ngiInsertBefore));
        return group;
    }

    /**
     * Inserts a menu item into Navigation Menu at specified location
     * @param menu int Index of the group menu to add to.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param iLocation int The index at which to Insert the Item
     */
    public int insertMenuItemAt(int menu, String title, String href, String target, int horizPadding, int iLocation) throws Exception {
        return insertMenuItemAt(menu, title, href, target, horizPadding, true, iLocation);
    }

    /**
     * Inserts a menu item into Navigation Menu at specified location
     * @param menu int Index of the group menu to add to.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param createTable boolean Indicates whether to regenerate the html for the navigation menu.
     * @param iLocation int The index at which to Insert the Item
     */
    public int insertMenuItemAt(int menu, String title, String href, String target, int horizPadding, boolean createTable, int iLocation) throws Exception {
        Item item = new Item();
        item._title = title;
        item._horizPadding = horizPadding;
        item._href = href;
        item._target = target;
        ((Group) _groups.elementAt(menu - 1))._items.insertElementAt(item, iLocation - 1);
        if (createTable)
            createTable();
        return ((Group) _groups.elementAt(menu - 1))._items.indexOf(item) + 1;
    }

    /**
     * Inserts a menu item into Navigation Menu at specified location
     * @param menu int Index of the group menu to add to.
     * @param submenu java.lang.String submenu name of the item added. Indicates item is a submenu.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param iLocation int The index at which to Insert the Item
     */
    public int insertMenuItemAt(int menu, String submenu, String title, String href, String target, int horizPadding, int iLocation) throws Exception {
        return insertMenuItemAt(menu, submenu, title, href, target, horizPadding, true, iLocation);
    }

    /**
     * Inserts a menu item into Navigation Menu at specified location
     * @param menu int Index of the group menu to add to.
     * @param submenu java.lang.String submenu name of the item added. Indicates item is a submenu.
     * @param title java.lang.String title displayed of the item added
     * @param href java.lang.String href link of the item added
     * @param target java.lang.String target window of the href link
     * @param horizPadding int The number of pixels to pad the item by.
     * @param createTable boolean Indicates whether to regenerate the html for the navigation menu.
     * @param iLocation int The index at which to Insert the Item
     */
    public int insertMenuItemAt(int menu, String submenu, String title, String href, String target, int horizPadding, boolean createTable, int iLocation) throws Exception {
        Item item = new Item();
        item._title = title;
        item._horizPadding = horizPadding;
        item._href = href;
        item._target = target;
        item._submenu = submenu;
        ((Group) _groups.elementAt(menu - 1))._items.insertElementAt(item, iLocation - 1);
        if (createTable)
            createTable();
        return ((Group) _groups.elementAt(menu - 1))._items.indexOf(item) + 1;
    }

    private boolean isPage(String sHref) {
        if (sHref == null)
            return false;
        String pageName = getPage().getPageName();
        int qindex = sHref.indexOf('?');
        if (qindex > -1)
            sHref = sHref.substring(0, qindex);
        int sindex = sHref.indexOf('/');
        if (sindex >= 0 && sHref.endsWith("/" + pageName))
            return true;
        if (sHref.equals(pageName))
            return true;
        return false;
    }

    private boolean isPage(String sHref, String pageName) {
        if (sHref == null)
            return false;
        int qindex = sHref.indexOf('?');
        if (qindex > -1)
            sHref = sHref.substring(0, qindex);
        if (sHref.endsWith(pageName))
            return true;
        return false;
    }

    /**
     * Constructs an HtmlLink component containing a text caption.
     * @return HtmlContainer
     * @param href		The URL for the link.
     * @param target	The target associated with the link.
     * @param caption	The text caption for the link.
     * @param unique	A unique string to identify different entries in HTML for naming purposes.
     */
    protected HtmlContainer newEntry(String href, String target, String caption, String unique) throws Exception {
        HtmlPage page = getPage();
        HtmlComponent c;
        HtmlContainer ret = new HtmlContainer("", page);
        HtmlImage spacer = null;
        if (_itemMarkerImg != null && !_itemMarkerImg.trim().equals("") && href != null && !href.trim().equals("")) {
            spacer = new HtmlImage("itemMarker" + unique, _itemMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            ret.add(spacer);
        }
        if (_itemTitleMarkerImg != null && !_itemTitleMarkerImg.trim().equals("") && (href == null || href.trim().equals(""))) {
            spacer = new HtmlImage("itemTitleMarker" + unique, _itemTitleMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            ret.add(spacer);
        }
        if (href != null && !href.trim().equals("")) {
            HtmlRaw hrSpan = new HtmlRaw("<SPAN CLASS=\"" + _linkfontstyle.getStyleName() + "\">", page);
            ret.add(hrSpan);
            HtmlLink link = new HtmlLink("link" + unique, href, null, page);
            link.add(new HtmlRaw(caption, page));
            if (_itemMarkerImg != null && _itemMarkerMouserOverImg != null) {
//		  link.setOnMouseOver("document."+spacer.getFullName()+".src='"+_itemMarkerMouserOverImg+"';");
//		  link.setOnMouseOut("document."+spacer.getFullName()+".src='"+_itemMarkerImg+"';");
                link.setOnMouseOver("fnc" + getFullName() + "_ItemMarkerMouseOverImage(document." + spacer.getFullName() + ");");
                link.setOnMouseOut("fnc" + getFullName() + "_ItemMarkerImage(document." + spacer.getFullName() + ");");
            }
            if (target != null && !target.trim().equals(""))
                link.setTarget(target);
            link.setStyle(_linkstyle);
            c = link;
        } else if (_fontStyle != null)
            c = new HtmlText(caption, _fontStyle, page);
        else
            c = new HtmlText(caption, _groupname + ".CellFont", page);
        ret.add(c);
        if (c instanceof HtmlLink) {
            HtmlRaw hrEndSpan = new HtmlRaw("</SPAN", page);
            ret.add(hrEndSpan);
        }
        return ret;
    }

    /**
     * Constructs an HtmlLink component containing a text caption.
     * @return HtmlContainer
     * @param href		The URL for the link.
     * @param target	The target associated with the link.
     * @param caption	The text caption for the link.
     * @param unique	A unique string to identify different entries in HTML for naming purposes.
     * @param iHorSpacer The horizontal tabing of an entry.
     */
    protected HtmlContainer newEntry(String href, String target, String caption, String unique, int iHorSpacer) throws Exception {
        HtmlPage page = getPage();
        HtmlComponent c;
        HtmlContainer ret = new HtmlContainer("", page);
        HtmlImage spacer = null;
 
        if (_itemMarkerImg != null && !_itemMarkerImg.trim().equals("") && href != null && !href.trim().equals("")) {
            spacer = new HtmlImage("itemMarker" + unique, _itemMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            ret.add(spacer);
        }
        if (_itemTitleMarkerImg != null && !_itemTitleMarkerImg.trim().equals("") && (href == null || href.trim().equals(""))) {
            spacer = new HtmlImage("itemTitleMarker" + unique, _itemTitleMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            ret.add(spacer);
        }
        if (iHorSpacer > 0) {
            HtmlImage img = newHorizontalSpacer(iHorSpacer);
            if (img != null)
                ret.add(newHorizontalSpacer(iHorSpacer));
        }

        if (href != null && !href.trim().equals("")) {
            HtmlRaw hrSpan = new HtmlRaw("<SPAN CLASS=\"" + _linkfontstyle.getStyleName() + "\">", page);
            ret.add(hrSpan);
            HtmlLink link = new HtmlLink("link" + unique, href, null, page);
            link.add(new HtmlRaw(caption, page));
            if (_itemMarkerImg != null && _itemMarkerMouserOverImg != null) {
//	  link.setOnMouseOver("document."+spacer.getFullName()+".src='"+_itemMarkerMouserOverImg+"';");
//	  link.setOnMouseOut("document."+spacer.getFullName()+".src='"+_itemMarkerImg+"';");
                link.setOnMouseOver("fnc" + getFullName() + "_ItemMarkerMouseOverImage(document." + spacer.getFullName() + ");");
                link.setOnMouseOut("fnc" + getFullName() + "_ItemMarkerImage(document." + spacer.getFullName() + ");");
            }
            if (target != null && !target.trim().equals(""))
                link.setTarget(target);
            link.setStyle(_linkstyle);
            c = link;
        } else if (_fontStyle != null)
            c = new HtmlText(caption, _fontStyle, page);
        else
            c = new HtmlText(caption, _groupname + ".CellFont", page);
        ret.add(c);
        if (c instanceof HtmlLink) {
            HtmlRaw hrEndSpan = new HtmlRaw("</SPAN", page);
            ret.add(hrEndSpan);
        }
        return ret;
    }

    /**
     * Constructs an HtmlLink component containing a text caption.
     * @return HtmlContainer
     * @param submenu	The Name of another group for SubMenu.
     * @param caption	The text caption for the link.
     * @param unique	A unique string to identify different entries in HTML for naming purposes.
     */
    protected HtmlContainer newEntry(String submenu, String href, String target, String caption, String unique) throws Exception {
        HtmlPage page = getPage();
        HtmlComponent c;
        HtmlContainer ret = new HtmlContainer("submenuCell" + unique, page);
        HtmlLink anchor = new HtmlLink("submenu" + getName() + unique, href, page);
        HtmlTable tbl = new HtmlTable("submenuTable" + unique, page);
        tbl.setCellPadding(0);
        tbl.setSizeOption(HtmlTable.SIZE_PERCENT);
        tbl.setWidth(100);
/*	HtmlTableCellProperties navLinkTableCellProp = new HtmlTableCellProperties();
	navLinkTableCellProp.setAlign(_cellAlign);
	navLinkTableCellProp.setVertAlign(_cellVertAlign);
	navLinkTableCellProp.setWrap(_wrap);
	navLinkTableCellProp.setBackgroundColor(_cellBackgroundColor);
	navLinkTableCellProp.setStyle(_cellStyle);*/
        anchor.add(tbl);
        HtmlContainer cont = new HtmlContainer("", getPage());
        HtmlImage spacer = null;
        if (_itemMarkerImg != null && !_itemMarkerImg.trim().equals("") && submenu != null && !submenu.trim().equals("")) {
            spacer = new HtmlImage("itemMarker" + unique, _itemMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            cont.add(spacer);
            //tbl.setComponentAt(0,col++,spacer/*,navLinkTableCellProp*/);
        }
        if (submenu != null && !submenu.trim().equals("")) {
            String sParentGroup = _groupname;
            if (_parentgroup != null)
                sParentGroup = _parentgroup;
            HtmlPopup htPop = null;
            String sPopName = "popup" + getName() + unique;
            boolean bPopupAllReadyAdded = false;
            Enumeration enumera = getPage().getComponents();
            while (enumera.hasMoreElements()) {
                HtmlComponent hc = (HtmlComponent) enumera.nextElement();
                if (hc instanceof HtmlPopup) {
                    HtmlPopup hpPop = (HtmlPopup) hc;
                    if (hpPop.getName().equals(sPopName)) {
                        bPopupAllReadyAdded = true;
                        htPop = hpPop;
                        break;
                    }
                }
            }
            if (!bPopupAllReadyAdded) {
                htPop = new HtmlPopup(sPopName, anchor, 150, true, false, true, page);
                htPop.setCellPadding(_NavTableCellPadding);
                NavigationMenu nmPopup;
                htPop.add(nmPopup = new NavigationMenu(submenu + unique, page, submenu, sParentGroup));
                Vector vPopups = nmPopup.getPopups();
                if (vPopups != null && vPopups.size() > 0) {
                    String sHideCondition = "";
                    String sHideScript = "";
                    for (int j = 0; j < vPopups.size(); j++) {
                        sHideCondition += ((HtmlPopup) vPopups.elementAt(j)).getName() + ".style.visibility=='hidden' && ";
                        sHideScript += ((HtmlPopup) vPopups.elementAt(j)).getName() + ".style.visibility='hidden';\n";
                    }
                    if (sHideCondition.endsWith("&& "))
                        sHideCondition = sHideCondition.substring(0, sHideCondition.length() - 4);
                    htPop.setHideCondition(sHideCondition);
                    htPop.setHideScript(sHideScript);
                    for (int j = 0; j < vPopups.size(); j++) {
                        String sVisibleScript = "";
                        HtmlPopup hpChildPop = (HtmlPopup) vPopups.elementAt(j);
                        for (int k = 0; k < vPopups.size(); k++) {
                            if (j == k)
                                continue;
                            sVisibleScript += ((HtmlPopup) vPopups.elementAt(k)).getName() + ".style.visibility='hidden';\n";
                        }
                        hpChildPop.setVisibleScript(sVisibleScript);
                    }
                }
                _popups.addElement(htPop);
            }
            HtmlRaw hrSpan = new HtmlRaw("<SPAN CLASS=\"" + _linkfontstyle.getStyleName() + "\">", page);
            cont.add(hrSpan);
            HtmlLink lnk = new HtmlLink("submenuitem", href, page);
            if (href == null || href.equals(""))
                lnk.setOnClick("return false;");
            lnk.setStyle(_linkstyle);
            lnk.setTarget(target);
            lnk.add(new HtmlRaw(caption, page));
            cont.add(lnk);
            HtmlRaw hrEndSpan = new HtmlRaw("</SPAN", page);
            cont.add(hrEndSpan);
            //tbl.setComponentAt(0,col++,lnk/*,navLinkTableCellProp*/);
            if (_itemMarkerImg != null && _itemMarkerMouserOverImg != null) {
//		  link.setOnMouseOver("document."+spacer.getFullName()+".src='"+_itemMarkerMouserOverImg+"';");
//		  link.setOnMouseOut("document."+spacer.getFullName()+".src='"+_itemMarkerImg+"';");
                anchor.setOnMouseOver("fnc" + getFullName() + "_ItemMarkerMouseOverImage(document." + spacer.getFullName() + ");" + htPop.getPopupShowScript());
                anchor.setOnMouseOut("fnc" + getFullName() + "_ItemMarkerImage(document." + spacer.getFullName() + ");" + htPop.getPopupScript());
/*		  String sMouseOver="this.className='"+_cellHoverStyle.getStyleName()+"';"+anchor.getFullName()+".className='"+_linkcellhoverstyle.getStyleName()+"';";
		  String sMouseOut="this.className='"+_cellStyle.getStyleName()+"';"+anchor.getFullName()+".className='"+_linkstyle.getStyleName()+"';";
		  navLinkTableCellProp.setOnMouseOver(sMouseOver+anchor.getOnMouseOver());
	      navLinkTableCellProp.setOnMouseOut(sMouseOut+anchor.getOnMouseOut());*/
            } else {
                anchor.setOnMouseOver(htPop.getPopupShowScript());
                anchor.setOnMouseOut(htPop.getPopupScript());
            }
            if (href == null || href.equals(""))
                anchor.setOnClick("return false;");
            anchor.setTarget(target);
            anchor.setStyle(_linkstyle);
            c = anchor;
        } else if (_fontStyle != null)
            c = new HtmlText(caption, _fontStyle, page);
        else
            c = new HtmlText(caption, _groupname + ".CellFont", page);
        tbl.setComponentAt(0, 0, cont);
        ret.add(c);
        return ret;
    }

    /**
     * Constructs an HtmlLink component containing a text caption.
     * @return HtmlContainer
     * @param submenu	The Name of another group for SubMenu.
     * @param caption	The text caption for the link.
     * @param unique	A unique string to identify different entries in HTML for naming purposes.
     */
    protected HtmlContainer newEntry(String submenu, String href, String target, String caption, String unique, int iHorSpacer) throws Exception {
        HtmlPage page = getPage();
        HtmlComponent c;
        HtmlContainer ret = new HtmlContainer("submenuCell" + unique, page);
        HtmlLink anchor = new HtmlLink("submenu" + getName() + unique, href, page);
        HtmlTable tbl = new HtmlTable("submenuTable" + unique, page);
        tbl.setCellPadding(0);
        tbl.setSizeOption(HtmlTable.SIZE_PERCENT);
        tbl.setWidth(100);
/*	HtmlTableCellProperties navLinkTableCellProp = new HtmlTableCellProperties();
	navLinkTableCellProp.setAlign(_cellAlign);
	navLinkTableCellProp.setVertAlign(_cellVertAlign);
	navLinkTableCellProp.setWrap(_wrap);
	navLinkTableCellProp.setBackgroundColor(_cellBackgroundColor);
	navLinkTableCellProp.setStyle(_cellStyle);*/
        anchor.add(tbl);
        HtmlContainer cont = new HtmlContainer("", getPage());
        HtmlImage spacer = null;
        if (_itemMarkerImg != null && !_itemMarkerImg.trim().equals("") && submenu != null && !submenu.trim().equals("")) {
            spacer = new HtmlImage("itemMarker" + unique, _itemMarkerImg, page);
            spacer.setHorizontalSpace(3);
            spacer.setOnLoad(getFullName() + "_imageLoaded();");
            cont.add(spacer);
            //tbl.setComponentAt(0,col++,spacer/*,navLinkTableCellProp*/);
        }
        if (iHorSpacer > 0) {
            HtmlImage img = newHorizontalSpacer(iHorSpacer);
            if (img != null)
                cont.add(newHorizontalSpacer(iHorSpacer));
        }
        if (submenu != null && !submenu.trim().equals("")) {
            String sParentGroup = _groupname;
            if (_parentgroup != null)
                sParentGroup = _parentgroup;
            HtmlPopup htPop = null;
            String sPopName = "popup" + getName() + unique;
            boolean bPopupAllReadyAdded = false;
            Enumeration enumera = getPage().getComponents();
            while (enumera.hasMoreElements()) {
                HtmlComponent hc = (HtmlComponent) enumera.nextElement();
                if (hc instanceof HtmlPopup) {
                    HtmlPopup hpPop = (HtmlPopup) hc;
                    if (hpPop.getName().equals(sPopName)) {
                        bPopupAllReadyAdded = true;
                        htPop = hpPop;
                        break;
                    }
                }
            }
            if (!bPopupAllReadyAdded) {
                htPop = new HtmlPopup(sPopName, anchor, 150, true, false, true, page);
                htPop.setCellPadding(_NavTableCellPadding);
                NavigationMenu nmPopup;
                htPop.add(nmPopup = new NavigationMenu(submenu + unique, page, submenu, sParentGroup));
                Vector vPopups = nmPopup.getPopups();
                if (vPopups != null && vPopups.size() > 0) {
                    String sHideCondition = "";
                    String sHideScript = "";
                    for (int j = 0; j < vPopups.size(); j++) {
                        sHideCondition += ((HtmlPopup) vPopups.elementAt(j)).getName() + ".style.visibility=='hidden' && ";
                        sHideScript += ((HtmlPopup) vPopups.elementAt(j)).getName() + ".style.visibility='hidden';\n";
                    }
                    if (sHideCondition.endsWith("&& "))
                        sHideCondition = sHideCondition.substring(0, sHideCondition.length() - 4);
                    htPop.setHideCondition(sHideCondition);
                    htPop.setHideScript(sHideScript);
                    for (int j = 0; j < vPopups.size(); j++) {
                        String sVisibleScript = "";
                        HtmlPopup hpChildPop = (HtmlPopup) vPopups.elementAt(j);
                        for (int k = 0; k < vPopups.size(); k++) {
                            if (j == k)
                                continue;
                            sVisibleScript += ((HtmlPopup) vPopups.elementAt(k)).getName() + ".style.visibility='hidden';\n";
                        }
                        hpChildPop.setVisibleScript(sVisibleScript);
                    }
                }
                _popups.addElement(htPop);
            }
            HtmlRaw hrSpan = new HtmlRaw("<SPAN CLASS=\"" + _linkfontstyle.getStyleName() + "\">", page);
            cont.add(hrSpan);
            HtmlLink lnk = new HtmlLink("submenuitem", href, page);
            if (href == null || href.equals(""))
                lnk.setOnClick("return false;");
            lnk.setStyle(_linkstyle);
            lnk.setTarget(target);
            lnk.add(new HtmlRaw(caption, page));
            cont.add(lnk);
            HtmlRaw hrEndSpan = new HtmlRaw("</SPAN", page);
            cont.add(hrEndSpan);
            //tbl.setComponentAt(0,col++,lnk/*,navLinkTableCellProp*/);
            if (_itemMarkerImg != null && _itemMarkerMouserOverImg != null) {
//		  link.setOnMouseOver("document."+spacer.getFullName()+".src='"+_itemMarkerMouserOverImg+"';");
//		  link.setOnMouseOut("document."+spacer.getFullName()+".src='"+_itemMarkerImg+"';");
                anchor.setOnMouseOver("fnc" + getFullName() + "_ItemMarkerMouseOverImage(document." + spacer.getFullName() + ");" + htPop.getPopupShowScript());
                anchor.setOnMouseOut("fnc" + getFullName() + "_ItemMarkerImage(document." + spacer.getFullName() + ");" + htPop.getPopupScript());
/*		  String sMouseOver="this.className='"+_cellHoverStyle.getStyleName()+"';"+anchor.getFullName()+".className='"+_linkcellhoverstyle.getStyleName()+"';";
		  String sMouseOut="this.className='"+_cellStyle.getStyleName()+"';"+anchor.getFullName()+".className='"+_linkstyle.getStyleName()+"';";
		  navLinkTableCellProp.setOnMouseOver(sMouseOver+anchor.getOnMouseOver());
	      navLinkTableCellProp.setOnMouseOut(sMouseOut+anchor.getOnMouseOut());*/
            } else {
                anchor.setOnMouseOver(htPop.getPopupShowScript());
                anchor.setOnMouseOut(htPop.getPopupScript());
            }
            if (href == null || href.equals(""))
                anchor.setOnClick("return false;");
            anchor.setTarget(target);
            anchor.setStyle(_linkstyle);
            c = anchor;
        } else if (_fontStyle != null)
            c = new HtmlText(caption, _fontStyle, page);
        else
            c = new HtmlText(caption, _groupname + ".CellFont", page);
        tbl.setComponentAt(0, 0, cont);
        ret.add(c);
        return ret;
    }

    protected HtmlImage newHorizontalSpacer(int pixels) {
        HtmlPage page = getPage();
        Props pr = page.getPageProperties();
        String sGroupName = _groupname;
        if (_parentgroup != null && !_parentgroup.trim().equals("") && _useParentAttributes)
            sGroupName = _parentgroup;
        String sHorizSpacerImage = pr.getProperty(sGroupName + ".Group.HorizSpacerImage");
        if (sHorizSpacerImage == null || sHorizSpacerImage.trim().equals(""))
            return null;
        HtmlImage img = new HtmlImage(sHorizSpacerImage, page);
        img.setHorizontalSpace(pixels);
        return img;
    }

    /**
     * Creates a HtmlStyle object with the given name,type & property string containing the style information
     * @return com.salmonllc.html.HtmlStyle
     * @param name java.lang.String The name to be given to the style so that it can be reference in HTML
     * @param type java.lang.String The type of style to generate e.g. A an anchor style, TD a table cell style etc.
     * @param styleprop java.lang.String References a property in the properties file giving the style information for the HtmlStyle
     */
    public HtmlStyle newStyle(String name, String type, String styleprop) {
        Props pr = getPage().getPageProperties();
        String sStyle = "\t";
        int i = 1;
        while (true) {
            String StyleAttribute = pr.getProperty(styleprop + i);
            if (StyleAttribute == null)
                break;
            sStyle += StyleAttribute + ";\n\t";
            i++;
        }
        sStyle += "\n";
        HtmlStyle ret = null;
        if (i != 1)
            ret = new HtmlStyle(name, type, sStyle, getPage());
        return ret;
    }

    protected HtmlImage newVerticalSpacer(int pixels) {
        HtmlPage page = getPage();
        Props pr = page.getPageProperties();
        String sGroupName = _groupname;
        if (_parentgroup != null && !_parentgroup.trim().equals("") && _useParentAttributes)
            sGroupName = _parentgroup;
        String sVertSpacerImage = pr.getProperty(sGroupName + ".Group.VertSpacerImage");
        if (sVertSpacerImage == null || sVertSpacerImage.trim().equals(""))
            return null;
        HtmlImage img = new HtmlImage(sVertSpacerImage, page);
        img.setVerticalSpace(pixels);
        return img;
    }

    /**
     * Causes the Navigation Menu to be regenerated.
     */
    public void refresh() throws Exception {
        createTable();
    }

    /**
     * Removes the HtmlPopups from the NavigationMenu.
     * Creation date: (8/17/01 6:08:43 PM)
     */
    public void removePopups() {
        for (int i = 0; i < _popups.size(); i++) {
            HtmlPopup hpPop = (HtmlPopup) _popups.elementAt(i);
            Enumeration enumera = hpPop.getComponents();
            NavigationMenu nm = (NavigationMenu) enumera.nextElement();
            nm.removePopups();
            getPage().remove((HtmlPopup) _popups.elementAt(i));
        }
        _popups.removeAllElements();

    }

    /**
     * Specifies whether to disable/enable the cell in a Navigation Menu to be a link or not.
     * @param bDisable boolean Indicates to whether to disable/enable the link ability of cells within this Navigation Menu.
     */
    public void setDisableCellLink(boolean bDisable) {
        _disableCellLink = bDisable;
    }

    /**
     * Specifies whether the Navigation Menu is to be in its own layer.
     * @param enable boolean Indicate whether the Navigation Menu is to be in its own layer
     */
    public void setEnableLayer(boolean enable) throws Exception {
        _enableLayer = enable;
        initNavigationProperties();
        createTable();
    }

    /**
     * Specifies the Group Name in the Properties File for the Navigation Menu to be built from.
     * @param groupname the name of the group in the properties file describing the menu.
     */
    public void setGroup(String groupname) throws Exception {
        _groupname = groupname;
        initNavigationProperties();
        createTable();
    }

    /**
     * Changes the Href of an item in the Navigation Menu.
     * @param groupIndex is the group index of the entry to change the HRef of.
     * @param itemIndex is the item index of the entry to change the HRef of.
     * @param href is the new URL for the HREF of the link.
     */
    public void setHref(int groupIndex, int itemIndex, String href) {
        try {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item item = (Item) group._items.elementAt(itemIndex - 1);
            item._href = href;
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*  String sItemKey=_groupname+".Group"+groupIndex+".Item"+itemIndex;
  HtmlContainer hc=(HtmlContainer)_nav.get(sItemKey);
  Enumeration enumera=hc.getComponents();
  while(enumera.hasMoreElements()) {
	HtmlComponent hcp=(HtmlComponent)enumera.nextElement();
	if (hcp instanceof HtmlLink) {
	  HtmlLink hl=(HtmlLink)hcp;
	  hl.setHref(href);
	 }
   }*/
    }

    /**
     * This selects the current page shown if it has a reference in the menu.
     */
    public void setSelectedItem() {
        clearSelectedItem();
        outer_loop:
        for (int i = 1; ; i++) {
            for (int j = 1; ; j++) {
                String sItemContainerKey = _groupname + ".Group" + i + ".Item" + j;
                HtmlContainer hc = (HtmlContainer) _nav.get(sItemContainerKey);
                if (hc == null) {
                    if (j == 1) break outer_loop;
                    break;
                }
                HtmlLink hl = null;
                Enumeration enumera = hc.getComponents();
                while (enumera.hasMoreElements()) {
                    HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
                    if (hcp instanceof HtmlLink) {
                        hl = (HtmlLink) hcp;
                        String sHref = hl.getHref();
                        if (isPage(sHref)) {
                            _selectedGroupIndex = i;
                            _selectedItemIndex = j;
                            break outer_loop;
                        }
                    }
                }
            }
        }
        if (_selectedGroupIndex > 0 && _selectedItemIndex > 0)
            setSelectedItem(_selectedGroupIndex, _selectedItemIndex);

    }

    /**
     * Specify which item in the Navigation Menu is to be highlighted as selected..
     * @param groupIndex int group index of item to select in menu.
     * @param itemIndex int item index of item to select in menu.
     */
    public void setSelectedItem(int groupIndex, int itemIndex) {
        clearSelectedItem();
        String sItemContainerKey = _groupname + ".Group" + groupIndex + ".Item" + itemIndex;
        String sItemCellPropKey = _groupname + ".Group" + groupIndex + ".Item" + itemIndex + "CELLPROP";
        HtmlContainer hc = (HtmlContainer) _nav.get(sItemContainerKey);
        if (hc == null)
            return;
        HtmlTableCellProperties htcp = (HtmlTableCellProperties) _nav.get(sItemCellPropKey);
        HtmlLink hl = null;
        HtmlImage hi = null;
        Enumeration enumera = hc.getComponents();
        while (enumera.hasMoreElements()) {
            HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
            if (hcp instanceof HtmlLink) {
                hl = (HtmlLink) hcp;
                Enumeration enumLink = hl.getComponents();
                outer:
                while (enumLink.hasMoreElements()) {
                    HtmlComponent hcpLink = (HtmlComponent) enumLink.nextElement();
                    if (hcpLink instanceof HtmlTable) {
                        HtmlContainer hcCont = (HtmlContainer) ((HtmlTable) hcpLink).getComponents().nextElement();
                        Enumeration enumCont = hcCont.getComponents();
                        while (enumCont.hasMoreElements()) {
                            HtmlComponent hcpCont = (HtmlComponent) enumCont.nextElement();
                            if (hcpCont instanceof HtmlLink) {
                                hl = (HtmlLink) hcpCont;
                                break outer;
                            }
                        }
                    }
                }

            }
            if (hcp instanceof HtmlImage) {
                if (hcp.getName().startsWith("itemMarker")) {
                    hi = (HtmlImage) hcp;
                }
            }
        }
        if (hi != null) {
            if (_selectedItemMarkerImg != null)
                hi.setSource(_selectedItemMarkerImg);
        }
        if (hl != null) {
            if (_selectedStyle != null)
                hl.setStyle(_selectedStyle);
            if (_selectedItemMarkerImg != null && hi != null && _itemMarkerMouserOverImg != null)
                hl.setOnMouseOut("fnc" + getFullName() + "_SelectedItemMarkerImage(document." + hi.getFullName() + ");");
//	  hl.setOnMouseOut("document."+hi.getFullName()+".src='"+_selectedItemMarkerImg+"';");
            if (_selectedItemMarkerMouseOverImg != null && hi != null && _itemMarkerMouserOverImg != null)
                hl.setOnMouseOver("fnc" + getFullName() + "_SelectedItemMarkerMouseOverImage(document." + hi.getFullName() + ");");
//	  hl.setOnMouseOver("document."+hi.getFullName()+".src='"+_selectedItemMarkerMouseOverImg+"';");
        }

        if (htcp != null) {
            htcp.setStyle(_selectedCellStyle);
            if (hl != null) {
                String sMouseOver;
                String sMouseOut;
                if (_selectedCellHoverStyle != null && _selectedHoverStyle != null)
                    sMouseOver = "this.className='" + _selectedCellHoverStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _selectedHoverStyle.getStyleName() + "';";
                else
                    sMouseOver = "this.className='" + _cellHoverStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _linkcellhoverstyle.getStyleName() + "';";
                if (_selectedStyle != null && _selectedCellStyle != null)
                    sMouseOut = "this.className='" + _selectedCellStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _selectedStyle.getStyleName() + "';";
                else
                    sMouseOut = "this.className='" + _cellStyle.getStyleName() + "';" + hl.getFullName() + ".className='" + _linkstyle.getStyleName() + "';";
                if (hi != null) {
                    sMouseOver += hl.getOnMouseOver();
                    sMouseOut += hl.getOnMouseOut();
                }
                htcp.setOnMouseOver(sMouseOver);
                htcp.setOnMouseOut(sMouseOut);
                htcp.setOnClick("document.location='" + hl.getHref() + "';");
            }
            _selectedGroupIndex = groupIndex;
            _selectedItemIndex = itemIndex;
        }
    }

    /**
     * This selects the item via pagename if it exists in the menu.
     */
    public void setSelectedItem(String pageName) {
        clearSelectedItem();
        outer_loop:
        for (int i = 1; ; i++) {
            for (int j = 1; ; j++) {
                String sItemContainerKey = _groupname + ".Group" + i + ".Item" + j;
                HtmlContainer hc = (HtmlContainer) _nav.get(sItemContainerKey);
                if (hc == null) {
                    if (j == 1) break outer_loop;
                    break;
                }
                HtmlLink hl = null;
                Enumeration enumera = hc.getComponents();
                while (enumera.hasMoreElements()) {
                    HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
                    if (hcp instanceof HtmlLink) {
                        hl = (HtmlLink) hcp;
                        String sHref = hl.getHref();
                        if (isPage(sHref, pageName)) {
                            _selectedGroupIndex = i;
                            _selectedItemIndex = j;
                            break outer_loop;
                        }
                    }
                }
            }
        }
        if (_selectedGroupIndex > 0 && _selectedItemIndex > 0)
            setSelectedItem(_selectedGroupIndex, _selectedItemIndex);

    }

    /**
     * Specifies how the Navigation Menu is to be sized, via percatage or pixels.
     * @param sizeoption int A constant representing the type of size option.
     */
    public void setSizeOption(int sizeoption) {
        _sizeOption = sizeoption;
        _table.setSizeOption(sizeoption);
    }

    /**
     * Sets the target window for the link of an item in the Navigation Menu.
     * @param groupIndex is the group index of the entry to change the target of.
     * @param itemIndex is the item index of the entry to change the target of.
     * @param target is the new target associated with the link.
     */
    public void setTarget(int groupIndex, int itemIndex, String target) {
        try {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item item = (Item) group._items.elementAt(itemIndex - 1);
            item._target = target;
            createTable();
        } catch (Exception e) {
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
     * Sets the title displayed of an item in the Navigation Menu.
     * @param groupIndex is the group index of the entry to change the caption of.
     * @param itemIndex is the item index of the entry to change the caption of.
     * @param title is the new caption for the item.
     */
    public void setTitle(int groupIndex, int itemIndex, String title) {
        try {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item item = (Item) group._items.elementAt(itemIndex - 1);
            item._title = title;
            createTable();
        } catch (Exception e) {
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
     * Sets whether the item is visible or not.
     * @param groupIndex is the group index of the entry to hide/show.
     * @param itemIndex is the item index of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(int groupIndex, int itemIndex, boolean visible) {
/*  String sItemKey=_groupname+".Group"+groupIndex+".Item"+itemIndex;
  HtmlContainer hc=(HtmlContainer)_nav.get(sItemKey);
  hc.setVisible(visible);
  String sItemCellPropKey=_groupname+".Group"+groupIndex+".Item"+itemIndex+"CELLPROP";
  HtmlTableCellProperties htcp=(HtmlTableCellProperties)_nav.get(sItemCellPropKey);
  HtmlLink hl=null;
  HtmlImage hi=null;
  Enumeration enumera=hc.getComponents();
  while(enumera.hasMoreElements()) {
	HtmlComponent hcp=(HtmlComponent)enumera.nextElement();
	if (hcp instanceof HtmlLink) {
	  hl=(HtmlLink)hcp;
	 }
	if (hcp instanceof HtmlImage) {
	  if (hcp.getName().startsWith("itemMarker")) {
	    hi=(HtmlImage)hcp;
	   }
	 }
   }
  if (htcp!=null) {
	if (visible==false) {
	  htcp.setOnMouseOver(null);
	  htcp.setOnMouseOut(null);
	 }
	else
	  if (hl!=null && visible==true) {
	    String sMouseOver="this.className='"+_cellHoverStyle.getStyleName()+"';"+hl.getFullName()+".className='"+_linkcellhoverstyle.getStyleName()+"';";
	    String sMouseOut="this.className='"+_cellStyle.getStyleName()+"';"+hl.getFullName()+".className='"+_linkstyle.getStyleName()+"';";
	    if (hi!=null) {
		  sMouseOver+=hl.getOnMouseOver();
		  sMouseOut+=hl.getOnMouseOut();
	     }
	    htcp.setOnMouseOver(sMouseOver);
	    htcp.setOnMouseOut(sMouseOut);
	    htcp.setOnClick("document.location='"+hl.getHref()+"';");
	   }
   }*/
        try {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            Item item = (Item) group._items.elementAt(itemIndex - 1);
            item._visible = visible;
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether the group is visible or not.
     * @param groupIndex is the group index of the entry to hide/show.
     * @param visible is to indicate whether the item should be shown or not.
     */
    public void setVisible(int groupIndex, boolean visible) {
/*  for (int i=1;;i++)
   {
	   String sItemKey=_groupname+".Group"+groupIndex+".Item"+ i;
	   HtmlContainer hc=(HtmlContainer)_nav.get(sItemKey);
	   if (hc==null)
	     break;
	   setVisible(groupIndex,i,visible);
//	   hc.setVisible(visible);
   }
  String sImageKey=_groupname+".Group"+groupIndex+".Image";
  HtmlImage hi=(HtmlImage)_nav.get(sImageKey);
  if (hi!=null)
	hi.setVisible(visible);*/
        try {
            Group group = (Group) _groups.elementAt(groupIndex - 1);
            group._visible = visible;
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This hides/shows the item via pagename if it exists in the menu.
     */
    public void setVisible(String pageName, boolean visible) {
        outer_loop:
        for (int i = 1; ; i++) {
            for (int j = 1; ; j++) {
                String sItemContainerKey = _groupname + ".Group" + i + ".Item" + j;
                HtmlContainer hc = (HtmlContainer) _nav.get(sItemContainerKey);
                if (hc == null) {
                    if (j == 1) break outer_loop;
                    break;
                }
                HtmlLink hl = null;
                Enumeration enumera = hc.getComponents();
                while (enumera.hasMoreElements()) {
                    HtmlComponent hcp = (HtmlComponent) enumera.nextElement();
                    if (hcp instanceof HtmlLink) {
                        hl = (HtmlLink) hcp;
                        String sHref = hl.getHref();
                        if (isPage(sHref, pageName)) {
                            setVisible(i, j, visible);
                            break outer_loop;
                        }
                    }
                }
            }
        }

    }

    /**
     * Sets the width of the Navigation Menu.
     * @param width int The Width of the Navigation Menu
     */
    public void setWidth(int width) {
        _width = width;
        _table.setWidth(width);
    }
}
