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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/NavBarTag.java $
//$Author: Dan $
//$Revision: 32 $
//$Modtime: 6/11/03 4:58p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * This is a tag used to create a display box (similar to a window).
 */

public class NavBarTag extends BaseBodyTag
{
    // The following are inherited from BaseBodyTag
    // private String _visible;
    //

    private String _navName;
    private String _align;
    private String _bgcolor;
    private String _border;
    private String _cellPadding;
    private String _cellSpacing;
    private String _height;
    private String _width;
    private String _cols;
    private String _hspace;
    private String _vspace;
    private String _theme;

    private String _enabled;

    private String _horizontal;
    private String _showSubMenuMarker;
    private String _hrefStyle;
    private String _hoverStyle;
    private String _groupHrefStyle;
    private String _groupHoverStyle;
    private String _textStyle;
    private String _selectedStyle;
    private String _selectedHoverStyle;
    private String _showPopupStyle;
    private String _showPopupHoverStyle;
    private String _showPopupSelectedStyle;
    private String _showPopupSelectedHoverStyle;
    private String _groupCellBgColor;
    private String _cellBgColor;
    private String _hoverBgColor;
    private String _groupHoverBgColor;
    private String _selectedBgColor;
    private String _selectedHoverBgColor;
    private String _showPopupBgColor;
    private String _subMenuBgColor;
    private String _markerImage;
    private String _markerOverImage;
    private String _selectedMarkerImage;
    private String _textImage;
    private String _vspaceImage;
    private String _hspaceImage;
    private String _groupSpacerImage;
    private String _showPopupImage;

    private String _subHrefStyle;
    private String _subHoverStyle;
    private String _subHoverBgColor;

    private String _dataSource;

    /**
     * This method creates the JSPNavBar used by the tag.
     */

    public HtmlComponent createComponent()
    {
        BaseTagHelper helper = getHelper();

        JspNavBar bar = new JspNavBar(getName(), _theme,helper.getController());

//        if (_theme != null)
//            bar.setTheme(_theme);
        if (_bgcolor != null)
            bar.setBgcolor(_bgcolor);
        if (_border != null)
            bar.setBorder(BaseTagHelper.stringToInt(_border));
        if (_cellPadding != null)
            bar.setCellPadding(BaseTagHelper.stringToInt(_cellPadding));
        if (_cellSpacing != null)
            bar.setCellSpacing(BaseTagHelper.stringToInt(_cellSpacing));
        if (_height != null)
            bar.setHeight(_height);
        if (_width != null)
            bar.setWidth(_width);
        if (_cols != null)
            bar.setCols(BaseTagHelper.stringToInt(_cols));
        if (_hspace != null)
            bar.setHSpace(BaseTagHelper.stringToInt(_hspace));
        if (_vspace != null)
            bar.setVSpace(BaseTagHelper.stringToInt(_vspace));
        if (getVisible() != null)
            bar.setVisible(new Boolean(getVisible()).booleanValue());
        if (_enabled != null)
            bar.setEnabled(new Boolean(_enabled).booleanValue());
        if (_horizontal != null)
            bar.setHorizontalMode(new Boolean(_horizontal).booleanValue());
        if (_showSubMenuMarker != null)
		      bar.setShowSubMenuMarker(new Boolean(_showSubMenuMarker).booleanValue());
   	    if (_hrefStyle != null)
            bar.setHrefStyle(_hrefStyle);
        if (_hoverStyle != null)
            bar.setHoverStyle(_hoverStyle);
        if (_groupHrefStyle != null)
            bar.setGroupHrefStyle(_groupHrefStyle);
        if (_groupHoverStyle != null)
            bar.setGroupHoverStyle(_groupHoverStyle);
        if (_textStyle != null)
            bar.setTextStyle(_textStyle);
        if (_selectedStyle != null)
            bar.setSelectedStyle(_selectedStyle);
        if (_selectedHoverStyle != null)
            bar.setSelectedHoverStyle(_selectedHoverStyle);
        if (_showPopupStyle != null)
            bar.setShowPopupStyle(_showPopupStyle);
        if (_showPopupHoverStyle != null)
            bar.setShowPopupHoverStyle(_showPopupHoverStyle);
        if (_showPopupSelectedStyle != null)
            bar.setShowPopupSelectedStyle(_showPopupSelectedStyle);
        if (_showPopupSelectedHoverStyle != null)
            bar.setShowPopupSelectedHoverStyle(_showPopupSelectedHoverStyle);
        if (_groupCellBgColor != null)
            bar.setGroupCellBgColor(_groupCellBgColor);
        if (_groupHoverBgColor != null)
            bar.setGroupHoverBgColor(_groupHoverBgColor);
        if (_cellBgColor != null)
            bar.setCellBgColor(_cellBgColor);
        if (_hoverBgColor != null)
            bar.setHoverBgColor(_hoverBgColor);
        if (_selectedBgColor != null)
            bar.setSelectedBgColor(_selectedBgColor);
        if (_selectedHoverBgColor != null)
            bar.setSelectedHoverBgColor(_selectedHoverBgColor);
        if (_showPopupBgColor != null)
            bar.setShowPopupBgColor(_showPopupBgColor);
        if (_subMenuBgColor != null)
            bar.setSubMenuBgColor(_subMenuBgColor);
        if (_markerImage != null)
            bar.setMarkerImage(_markerImage);
        if (_markerOverImage != null)
            bar.setMarkerOverImage(_markerOverImage);
        if (_selectedMarkerImage != null)
            bar.setSelectedMarkerImage(_selectedMarkerImage);
        if (_textImage != null)
            bar.setTextImage(_textImage);
        if (_vspaceImage != null)
            bar.setVSpaceImage(_vspaceImage);
        if (_hspaceImage != null)
            bar.setHSpaceImage(_hspaceImage);
        if (_groupSpacerImage != null)
            bar.setGroupSpacerImage(_groupSpacerImage);
        if (_showPopupImage != null)
            bar.setShowPopupImage(_showPopupImage);
        if (_subHrefStyle != null)
            bar.setSubHrefStyle(_subHrefStyle);
        if (_subHoverStyle != null)
            bar.setSubHoverStyle(_subHoverStyle);
        if (_subHoverBgColor != null)
            bar.setSubHoverBgColor(_subHoverBgColor);
        if (_dataSource != null)
            bar.setDataSource(_dataSource);


        return bar;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        JspNavBar bar = (JspNavBar) getHelper().getController().getComponent(getName());
        if (bar == null)
            return;
        TagWriter w = getTagWriter();
        w.setWriter(p);
        bar.generateHTML(w);
    }

    /**
     * Get the alignment attribute for the tag
     */
    public String getAlign()
    {
        return _align;
    }

    /**
     * Get the background color attribute for the tag
     */
    public String getBgcolor()
    {
        return _bgcolor;
    }

    /**
     * Get the border attribute for the tag
     */
    public String getBorder()
    {
        return _border;
    }

    /**
     * Get the cell background color attribute
     */
    public String getCellbgcolor()
    {
        return _cellBgColor;
    }

    /**
     * Get the cell padding attribute for the tag
     */
    public String getCellpadding()
    {
        return _cellPadding;
    }

    /**
     * Get the cell spacing attribute for the tag
     */
    public String getCellspacing()
    {
        return _cellSpacing;
    }

    /**
     * Get the columns attribute for the tag
     */
    public String getCols()
    {
        return _cols;
    }

    /**
     * Get the Data Source the component should be bound to
     */
    public String getDatasource()
    {
        return _dataSource;
    }

    /**
     * Get the enabled attribute
     */
    public String getEnabled()
    {
        return _enabled;
    }

    /**
     * Get the horizontal attribute
     */
    public String getHorizontal()
    {
        return _horizontal;
    }

	/**
     * Get the showSubMenuMarker attribute
     */
    public String getShowsubmenumarker()
    {
        return _showSubMenuMarker;
    }

    /**
     * Get the Group cell background color attribute
     */
    public String getGroupcellbgcolor()
    {
        return _groupCellBgColor;
    }

    /**
     * Get the group spacer image attribute
     */
    public String getGroupspacerimage()
    {
        return _groupSpacerImage;
    }

    /**
     * Get the height attribute for the tag
     */
    public String getHeight()
    {
        return _height;
    }

    /**
     * Get the hover background color attribute
     */
    public String getHoverbgcolor()
    {
        return _hoverBgColor;
    }

    /**
     * Get the hover style attribute for the tag
     */
    public String getHoverstyle()
    {
        return _hoverStyle;
    }

    /**
     * Get the href style attribute for the tag
     */
    public String getHrefstyle()
    {
        return _hrefStyle;
    }

    /**
     * Get the Group hover background color attribute
     */
    public String getGrouphoverbgcolor()
    {
        return _groupHoverBgColor;
    }

    /**
     * Get the Group hover style attribute for the tag
     */
    public String getGrouphoverstyle()
    {
        return _groupHoverStyle;
    }

    /**
     * Get the Group href style attribute for the tag
     */
    public String getGrouphrefstyle()
    {
        return _groupHrefStyle;
    }



    /**
     * Get the Sub Item hover background color attribute
     */
    public String getSubHoverbgcolor()
    {
        return _subHoverBgColor;
    }

    /**
     * Get the SubItem hover style attribute for the tag
     */
    public String getSubHoverstyle()
    {
        return _subHoverStyle;
    }

    /**
     * Get the SubItem href style attribute for the tag
     */
    public String getSubHrefstyle()
    {
        return _subHrefStyle;
    }



    /**
     * Get the horizontal space attribute for the tag
     */
    public String getHspace()
    {
        return _hspace;
    }

    /**
     * Get the horizontal space image attribute
     */
    public String getHspaceimage()
    {
        return _hspaceImage;
    }

    /**
     * Get the marker image attribute
     */
    public String getMarkerimage()
    {
        return _markerImage;
    }

    /**
     * Get the marker over attribute
     */
    public String getMarkeroverimage()
    {
        return _markerOverImage;
    }

    /**
     * Get the name given to the navbar tag
     */
    public String getNavName()
    {
        return _navName;
    }

    /**
     * Get the selected background color attribute
     */
    public String getSelectedbgcolor()
    {
        return _selectedBgColor;
    }

    /**
     * Get the selected hover background color attribute
     */
    public String getSelectedhoverbgcolor()
    {
        return _selectedHoverBgColor;
    }

    /**
     * Get the selected hover style attribute for the tag
     */

    public String getSelectedhoverstyle()
    {
        return _selectedHoverStyle;
    }

    /**
     * Get the selectedc marker image attribute
     */
    public String getSelectedmarkerimage()
    {
        return _selectedMarkerImage;
    }

    /**
     * Get the selected style attribute for the tag
     */
    public String getSelectedstyle()
    {
        return _selectedStyle;
    }

    /**
     * Get the show Popup background color attribute
     */
    public String getShowpopupbgcolor()
    {
        return _showPopupBgColor;
    }

    /**
     * Get the Show Popup Hover style attribute for the tag
     */

    public String getShowpopuphoverstyle()
    {
        return _showPopupHoverStyle;
    }

    /**
     * Get the Show Popup image attribute
     */
    public String getShowpopupimage()
    {
        return _showPopupImage;
    }

    /**
     * Get the Show Popup Selected Hover style attribute for the tag
     */

    public String getShowpopupselectedhoverstyle()
    {
        return _showPopupSelectedHoverStyle;
    }

    /**
     * Get the Show Popup Selected Hover style attribute for the tag
     */

    public String getShowpopupselectedstyle()
    {
        return _showPopupSelectedStyle;
    }

    /**
     * Get the Show Popup style attribute for the tag
     */

    public String getShowpopupstyle()
    {
        return _showPopupStyle;
    }

    /**
     * Get the sub Menu background color attribute
     */
    public String getSubmenubgcolor()
    {
        return _subMenuBgColor;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType()
    {
        return CONV_WRAP_ALL_NESTED;
    }

    /**
     * Get the Text Image attribute
     */
    public String getTextimage()
    {
        return _textImage;
    }

    /**
     * Get the Text Style attribute
     */
    public String getTextstyle()
    {
        return _textStyle;
    }

    /**
     * Get the Theme attribute
     */
    public String getTheme()
    {
        return _theme;
    }

    /**
     * Get the vertical space image attribute
     */
    public String getVspaceimage()
    {
        return _vspaceImage;
    }

    /**
     * Get the width attribute
     */
    public String getWidth()
    {
        return _width;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();
        _navName = null;
        _align = null;
        _bgcolor = null;
        _border = null;
        _cellPadding = null;
        _cellSpacing = null;
        _height = null;
        _width = null;
        _cols = null;
        _hspace = null;
        _vspace = null;
        _theme = null;
        _enabled = null;
        _horizontal = null;
		 _showSubMenuMarker=null;
        _hrefStyle = null;
        _hoverStyle = null;
        _groupHrefStyle = null;
        _groupHoverStyle = null;
        _textStyle = null;
        _selectedStyle = null;
        _selectedHoverStyle = null;
        _showPopupStyle = null;
        _showPopupHoverStyle = null;
        _showPopupSelectedStyle = null;
        _showPopupSelectedHoverStyle = null;
        _groupCellBgColor = null;
        _cellBgColor = null;
        _hoverBgColor = null;
        _groupHoverBgColor = null;
        _selectedBgColor = null;
        _selectedHoverBgColor = null;
        _showPopupBgColor = null;
        _subMenuBgColor = null;
        _markerImage = null;
        _markerOverImage = null;
        _selectedMarkerImage = null;
        _textImage = null;
        _vspaceImage = null;
        _hspaceImage = null;
        _groupSpacerImage = null;
        _showPopupImage = null;
        _subHoverBgColor = null;
        _subHrefStyle = null;
        _subHoverStyle = null;

    }

    /**
     * Sets the alignment property for the tag
     */
    public void setAlign(String newAlign)
    {
        _align = newAlign;
    }

    /**
     * Sets the Background Color property for the tag
     */
    public void setBgcolor(String val)
    {
        _bgcolor = val;
    }

    /**
     * Sets the border property for the tag
     */
    public void setBorder(String val)
    {
        _border = val;
    }

    /**
     * Sets the cell Background Color property for the tag
     * Used for the NavBar Styles
     */
    public void setCellbgcolor(String val)
    {
        _cellBgColor = val;
    }

    /**
     * Sets the cell Padding property for the tag
     */
    public void setCellpadding(String val)
    {
        _cellPadding = val;
    }

    /**
     * Sets the cell Spacing property for the tag
     */
    public void setCellspacing(String val)
    {
        _cellSpacing = val;
    }

    /**
     * Sets the columns property for the tag
     */
    public void setCols(String val)
    {
        _cols = val;
    }

    /**
     * Set the Data Source the component should be bound to
     */
    public void setDatasource(String val)
    {
        _dataSource = val;
    }

    /**
     * Sets the Enabled property for the tag
     */
    public void setEnabled(String val)
    {
        _enabled = val;
    }
     /**
     * Sets the horizontal property for the tag
     */
    public void setHorizontal (String val)
    {
        _horizontal = val;
    }

	/**
     * Sets the ShowSubMenuMarker property for the tag
     */
    public void setShowsubmenumarker(String val)
    {
        _showSubMenuMarker = val;
    }

    /**
     * Sets the Group cell Background Color property for the tag
     * Used for the NavBar Styles
     */
    public void setGroupcellbgcolor(String val)
    {
        _groupCellBgColor = val;
    }

    /**
     * Sets the Group cell Hover Background Color property for the tag
     * Used for the NavBar Styles
     */
    public void setGrouphoverbgcolor(String val)
    {
        _groupHoverBgColor = val;
    }

    /**
     * Sets the Group Space image property for the tag
     * Used for the Spacing in between NavBar groups
     */
    public void setGroupspacerimage(String val)
    {
        _groupSpacerImage = val;
    }

    /**
     * Sets the Height property for the tag
     */

    public void setHeight(String val)
    {
        _height = val;
    }

    /**
     * Sets the Hover Background Color property for the tag
     * Used for the NavBar Styles
     * Background Color while hovering over a navBar item
     */
    public void setHoverbgcolor(String val)
    {
        _hoverBgColor = val;
    }

    /**
     * Sets the Hover style property for the tag
     * Used for the NavBar Styles
     * While Hovering over
     */
    public void setHoverstyle(String val)
    {
        _hoverStyle = val;
    }

    /**
     * Sets the Href Style property for the tag
     * Used for the NavBar Styles
     * While Not hovering or selected
     */
    public void setHrefstyle(String val)
    {
        _hrefStyle = val;
    }

    /**
     * Sets the Group Hover style property for the tag
     * Used for the NavBar Styles
     * While Hovering over
     */
    public void setGrouphoverstyle(String val)
    {
        _groupHoverStyle = val;
    }

    /**
     * Sets the Group Href Style property for the tag
     * Used for the NavBar Styles
     * While Not hovering or selected
     */
    public void setGrouphrefstyle(String val)
    {
        _groupHrefStyle = val;
    }


    /**
     * Sets the SubItem Hover Background Color property for the tag
     * Used for the NavBar Styles
     * Background Color while hovering over a navBar item
     */
    public void setSubhoverbgcolor(String val)
    {
        _subHoverBgColor = val;
    }

    /**
     * Sets the SubItem Hover style property for the tag
     * Used for the NavBar Styles
     * While Hovering over
     */
    public void setSubhoverstyle(String val)
    {
        _subHoverStyle = val;
    }

    /**
     * Sets the SubItem Href Style property for the tag
     * Used for the NavBar Styles
     * While Not hovering or selected
     */
    public void setSubhrefstyle(String val)
    {
        _subHrefStyle = val;
    }

    /**
     * Sets the Horizontal Space property for the tag
     */
    public void setHspace(String val)
    {
        _hspace = val;
    }

    /**
     * Sets the Horizontal space image property for the tag
     *
     */
    public void setHspaceimage(String val)
    {
        _hspaceImage = val;
    }

    /**
     * Sets the Marker Image property for the tag
     * Image displayed to the left of NavBar items
     */
    public void setMarkerimage(String val)
    {
        _markerImage = val;
    }

    /**
     * Sets the Marker over Image property for the tag
     * Image displayed to the left of NavBar items while hovering over
     */
    public void setMarkeroverimage(String val)
    {
        _markerOverImage = val;
    }

    /**
     * Sets the Selected Background color property for the tag
     * Background Color when a navBar item is selected
     */
    public void setSelectedbgcolor(String val)
    {
        _selectedBgColor = val;
    }

    /**
     * Sets the Selected Hover Background color property for the tag
     * Background Color when a navBar item is selected and hovering over
     */
    public void setSelectedhoverbgcolor(String val)
    {
        _selectedHoverBgColor = val;
    }

    /**
     * Sets the Selected Hover Style property for the tag
     * Style when a navBar item is selected and hovering over
     */
    public void setSelectedhoverstyle(String val)
    {
        _selectedHoverStyle = val;
    }

    /**
     * Sets the Selecyed Marker Image property for the tag
     * Image displayed to the left of NavBar items while selected
     */
    public void setSelectedmarkerimage(String val)
    {
        _selectedMarkerImage = val;
    }

    /**
     * Sets the Selected Style property for the tag
     * Style when a navBar item is selected
     */
    public void setSelectedstyle(String val)
    {
        _selectedStyle = val;
    }

    /**
     * Sets the SHow Popup Background color property for the tag
     * Background Color when a subMenu is being shown inside the Navbar
     */
    public void setShowpopupbgcolor(String val)
    {
        _showPopupBgColor = val;
    }

    /**
     * Sets the Show Popup Hover Style property for the tag
     * Style to be used when submenu is in the Navbar
     */
    public void setShowpopuphoverstyle(String val)
    {
        _showPopupHoverStyle = val;
    }

    /**
     * Sets the Show Popup image property for the tag
     * Used for the image to be placed before the tesxt in a submenu in the  NavBar
     */
    public void setShowpopupimage(String val)
    {
        _showPopupImage = val;
    }

    /**
     * Sets the Show Popup Selected Hover Style property for the tag
     * Style to be used when submenu is in the Navbar
     */
    public void setShowpopupselectedhoverstyle(String val)
    {
        _showPopupSelectedHoverStyle = val;
    }

    /**
     * Sets the Show Popup Selected Style property for the tag
     * Style to be used when submenu is in the Navbar
     */
    public void setShowpopupselectedstyle(String val)
    {
        _showPopupSelectedStyle = val;
    }

    /**
     * Sets the Show Popup Style property for the tag
     * Style to be used when submenu is in the Navbar
     */
    public void setShowpopupstyle(String val)
    {
        _showPopupStyle = val;
    }

    /**
     * Sets the Sub Menu Background color property for the tag
     * Default Background Color for a subMenu
     */
    public void setSubmenubgcolor(String val)
    {
        _subMenuBgColor = val;
    }

    /**
     * Sets the Text Image property for the tag
     * Image displayed when a navBar item is plain text title (not an href)
     */
    public void setTextimage(String val)
    {
        _textImage = val;
    }

    /**
     * Sets the Text Style property for the tag
     * Style when a navBar item is plain text (not an href)
     */
    public void setTextstyle(String val)
    {
        _textStyle = val;
    }

    /**
     * Sets the Theme property for the tag
     */
    public void setTheme(String val)
    {
        _theme = val;
    }

    /**
     * Sets the vertical space property for the tag
     */
    public void setVspace(String val)
    {
        _vspace = val;
    }

    /**
     * Sets the vertical space image property for the tag
     *
     */
    public void setVspaceimage(String val)
    {
        _vspaceImage = val;
    }

    /**
     * Sets the Width property for the tag
     */
    public void setWidth(String val)
    {
        _width = val;
    }
}
