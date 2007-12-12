//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.personalization;

import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

/**
 * An implementation of a Swing Metal Theme object. The object has set methods for each Swing attribute so colors and fonts can be set at run time.
 */
public class SMetalTheme extends DefaultMetalTheme {
    private ColorUIResource _acceleratorForeground;
    private ColorUIResource _acceleratorSelectedForeground;
    private ColorUIResource _black;
    private ColorUIResource _control;
    private ColorUIResource _controlDarkShadow;
    private ColorUIResource _controlDisabled;
    private ColorUIResource _controlHighlight;
    private ColorUIResource _controlInfo;
    private ColorUIResource _controlShadow;
    private ColorUIResource _controlTextColor;
    private FontUIResource _controlTextFont;
    private ColorUIResource _desktopColor;
    private ColorUIResource _focusColor;
    private ColorUIResource _highlightedTextColor;
    private ColorUIResource _inactiveTextColor;
    private ColorUIResource _inactiveSystemTextColor;
    private ColorUIResource _menuBackground;
    private ColorUIResource _menuDisabledForeground;
    private ColorUIResource _menuForeground;
    private ColorUIResource _menuSelectedBackground;
    private ColorUIResource _menuSelectedForeground;
    private FontUIResource _menuTextFont;
    private ColorUIResource _primary1;
    private ColorUIResource _primary2;
    private ColorUIResource _primary3;
    private ColorUIResource _primaryControl;
    private ColorUIResource _primaryControlDarkShadow;
    private ColorUIResource _primaryControlHighlight;
    private ColorUIResource _primaryControlInfo;
    private ColorUIResource _primaryControlShadow;
    private ColorUIResource _secondary1;
    private ColorUIResource _secondary2;
    private ColorUIResource _secondary3;
    private ColorUIResource _separatorBackground;
    private ColorUIResource _separatorForeground;
    private FontUIResource _subTextFont;
    private ColorUIResource _systemTextColor;
    private FontUIResource _systemTextFont;
    private ColorUIResource _textHighlightColor;
    private ColorUIResource _userTextColor;
    private FontUIResource _userTextFont;
    private ColorUIResource _white;
    private ColorUIResource _windowBackground;
    private ColorUIResource _windowTitleBackground;
    private FontUIResource _windowTitleFont;
    private ColorUIResource _windowTitleForeground;
    private ColorUIResource _windowInactiveBackground;
    private ColorUIResource _windowTitleInactiveForeground;

    public ColorUIResource getAcceleratorForeground() {
        if (_acceleratorForeground != null)
            return _acceleratorForeground;
        return super.getAcceleratorForeground();
    }

    public ColorUIResource getAcceleratorSelectedForeground() {
        if (_acceleratorSelectedForeground != null)
            return _acceleratorSelectedForeground;
        return super.getAcceleratorSelectedForeground();
    }

    protected ColorUIResource getBlack() {
        if (_black != null)
            return _black;
        return super.getBlack();
    }

    public ColorUIResource getControl() {
        if (_control != null)
            return _control;
        return super.getControl();
    }

    public ColorUIResource getControlDarkShadow() {
        if (_controlDarkShadow != null)
          return _controlDarkShadow;
        return super.getControlDarkShadow();
    }

    public ColorUIResource getControlDisabled() {
        if (_controlDisabled != null)
          return _controlDisabled;

        return super.getControlDisabled();
    }

    public ColorUIResource getControlHighlight() {
        if (_controlHighlight != null)
            return _controlHighlight;
        return super.getControlHighlight();
    }

    public ColorUIResource getControlInfo() {
        if (_controlInfo != null)
            return _controlInfo;
        return super.getControlInfo();
    }

    public ColorUIResource getControlShadow() {
        if (_controlShadow != null)
            return _controlShadow;
        return super.getControlShadow();
    }

    public ColorUIResource getControlTextColor() {
        if (_controlTextColor != null)
            return _controlTextColor;
        return super.getControlTextColor();
    }

    public FontUIResource getControlTextFont() {
        if (_controlTextFont != null)
            return _controlTextFont;
        return super.getControlTextFont();
    }

    public ColorUIResource getDesktopColor() {
        if (_desktopColor != null)
            return _desktopColor;

        return super.getDesktopColor();
    }

    public ColorUIResource getFocusColor() {
        if (_focusColor != null)
            return _focusColor;

        return super.getFocusColor();
    }

    public ColorUIResource getHighlightedTextColor() {
        if (_highlightedTextColor != null)
            return _highlightedTextColor;

        return super.getHighlightedTextColor();
    }

    public ColorUIResource getInactiveControlTextColor() {
        if (_inactiveTextColor != null)
            return _inactiveTextColor;

        return super.getInactiveControlTextColor();
    }

    public ColorUIResource getInactiveSystemTextColor() {
        if (_inactiveSystemTextColor != null)
            return _inactiveSystemTextColor;

        return super.getInactiveSystemTextColor();
    }

    public ColorUIResource getMenuBackground() {
        if (_menuBackground != null)
            return _menuBackground;

        return super.getMenuBackground();
    }

    public ColorUIResource getMenuDisabledForeground() {
        if (_menuDisabledForeground != null)
           return _menuDisabledForeground;
        return super.getMenuDisabledForeground();
    }

    public ColorUIResource getMenuForeground() {
        if (_menuForeground != null)
           return _menuForeground;
        return super.getMenuForeground();
    }

    public ColorUIResource getMenuSelectedBackground() {
        if (_menuSelectedBackground != null)
           return _menuSelectedBackground;

        return super.getMenuSelectedBackground();
    }

    public ColorUIResource getMenuSelectedForeground() {
        if (_menuSelectedForeground != null)
           return _menuSelectedForeground;

        return super.getMenuSelectedForeground();
    }

    public FontUIResource getMenuTextFont() {
        if (_menuTextFont != null)
           return _menuTextFont;

        return super.getMenuTextFont();
    }

    // these are blue in Metal Default Theme
    protected ColorUIResource getPrimary1() {
        if (_primary1 != null)
            return _primary1;
        return super.getPrimary1();
    }

    protected ColorUIResource getPrimary2() {
        if (_primary2 != null)
            return _primary2;
        return super.getPrimary2();
    }

    protected ColorUIResource getPrimary3() {
        if (_primary3 != null)
            return _primary3;

        return super.getPrimary3();
    }

    public ColorUIResource getPrimaryControl() {
        if (_primaryControl != null)
            return _primaryControl;

        return super.getPrimaryControl();
    }

    public ColorUIResource getPrimaryControlDarkShadow() {
        if (_primaryControlDarkShadow != null)
            return _primaryControlDarkShadow;

        return super.getPrimaryControlDarkShadow();
    }

    public ColorUIResource getPrimaryControlHighlight() {
        if (_primaryControlHighlight != null)
            return _primaryControlHighlight;
        return super.getPrimaryControlHighlight();
    }

    public ColorUIResource getPrimaryControlInfo() {
        if (_primaryControlInfo != null)
            return _primaryControlInfo;

        return super.getPrimaryControlInfo();
    }

    public ColorUIResource getPrimaryControlShadow() {
        if (_primaryControlShadow != null)
            return _primaryControlShadow;

        return super.getPrimaryControlShadow();
    }

    // these are gray in Metal Default Theme
    protected ColorUIResource getSecondary1() {
        if (_secondary1 != null)
            return _secondary1;

        return super.getSecondary1();
    }

    protected ColorUIResource getSecondary2() {
        if (_secondary2 != null)
            return _secondary2;

        return super.getSecondary2();
    }

    protected ColorUIResource getSecondary3() {
        if (_secondary3 != null)
            return _secondary3;

        return super.getSecondary3();
    }

    public ColorUIResource getSeparatorBackground() {
        if (_separatorBackground != null)
            return _separatorBackground;
        return super.getSeparatorBackground();
    }

    public ColorUIResource getSeparatorForeground() {
        if (_separatorForeground != null)
            return _separatorForeground;

        return super.getSeparatorForeground();
    }

    public FontUIResource getSubTextFont() {
        if (_subTextFont != null)
            return _subTextFont;
        return super.getSubTextFont();
    }

    /**
     * Returns the color used, by default, for the text in labels
     * and titled borders.
     */
    public ColorUIResource getSystemTextColor() {
        if (_systemTextColor != null)
            return _systemTextColor;

        return super.getSystemTextColor();
    }

    public FontUIResource getSystemTextFont() {
        if (_systemTextFont != null)
            return _systemTextFont;
        return super.getSystemTextFont();
    }

    public ColorUIResource getTextHighlightColor() {
        if (_textHighlightColor != null)
            return _textHighlightColor;

        return super.getTextHighlightColor();
    }

    public ColorUIResource getUserTextColor() {
        if (_userTextColor != null)
            return _userTextColor;

        return super.getUserTextColor();
    }

    public FontUIResource getUserTextFont() {
        if (_userTextFont != null)
            return _userTextFont;

        return super.getUserTextFont();
    }

    protected ColorUIResource getWhite() {
        if (_white != null)
            return _white;
        return super.getWhite();
    }

    public ColorUIResource getWindowBackground() {
        if (_windowBackground != null)
            return _windowBackground;
        return super.getWindowBackground();
    }

    public ColorUIResource getWindowTitleBackground() {
        if (_windowTitleBackground != null)
            return _windowTitleBackground;

        return super.getWindowTitleBackground();
    }

    public FontUIResource getWindowTitleFont() {
        if (_windowTitleFont != null)
            return _windowTitleFont;

        return super.getWindowTitleFont();
    }

    public ColorUIResource getWindowTitleForeground() {
        if (_windowTitleForeground != null)
            return _windowTitleForeground;

        return super.getWindowTitleForeground();
    }

    public ColorUIResource getWindowTitleInactiveBackground() {
        if (_windowInactiveBackground != null)
            return _windowInactiveBackground;

        return super.getWindowTitleInactiveBackground();
    }

    public ColorUIResource getWindowTitleInactiveForeground() {
        if (_windowTitleInactiveForeground != null)
            return _windowTitleInactiveForeground;

        return super.getWindowTitleInactiveForeground();
    }

    public void setAcceleratorForeground(ColorUIResource acceleratorForeground) {
        _acceleratorForeground = acceleratorForeground;
    }

    public void setAcceleratorSelectedForeground(ColorUIResource acceleratorSelectedForeground) {
        _acceleratorSelectedForeground = acceleratorSelectedForeground;
    }

    public void setBlack(ColorUIResource black) {
        _black = black;
    }

    public void setControl(ColorUIResource control) {
        _control = control;
    }

    public void setControlDarkShadow(ColorUIResource controlDarkShadow) {
        _controlDarkShadow = controlDarkShadow;
    }

    public void setControlDisabled(ColorUIResource controlDisabled) {
        _controlDisabled = controlDisabled;
    }

    public void setControlHighlight(ColorUIResource controlHighlight) {
        _controlHighlight = controlHighlight;
    }

    public void setControlInfo(ColorUIResource controlInfo) {
        _controlInfo = controlInfo;
    }

    public void setControlShadow(ColorUIResource controlShadow) {
        _controlShadow = controlShadow;
    }

    public void setControlTextColor(ColorUIResource controlTextColor) {
        _controlTextColor = controlTextColor;
    }

    public void setControlTextFont(FontUIResource controlTextFont) {
        _controlTextFont = controlTextFont;
    }

    public void setDesktopColor(ColorUIResource desktopColor) {
        _desktopColor = desktopColor;
    }

    public void setFocusColor(ColorUIResource focusColor) {
        _focusColor = focusColor;
    }

    public void setHighlightedTextColor(ColorUIResource highlightedTextColor) {
        _highlightedTextColor = highlightedTextColor;
    }

    public void setInactiveSystemTextColor(ColorUIResource inactiveSystemTextColor) {
        _inactiveSystemTextColor = inactiveSystemTextColor;
    }

    public void setInactiveTextColor(ColorUIResource inactiveTextColor) {
        _inactiveTextColor = inactiveTextColor;
    }

    public void setMenuBackground(ColorUIResource menuBackground) {
        _menuBackground = menuBackground;
    }

    public void setMenuDisabledForeground(ColorUIResource menuDisabledForeground) {
        _menuDisabledForeground = menuDisabledForeground;
    }

    public void setMenuForeground(ColorUIResource menuForeground) {
        _menuForeground = menuForeground;
    }

    public void setMenuSelectedBackground(ColorUIResource menuSelectedBackground) {
        _menuSelectedBackground = menuSelectedBackground;
    }

    public void setMenuSelectedForeground(ColorUIResource menuSelectedForeground) {
        _menuSelectedForeground = menuSelectedForeground;
    }

    public void setMenuTextFont(FontUIResource menuTextFont) {
        _menuTextFont = menuTextFont;
    }

    public void setPrimary1(ColorUIResource primary1) {
        _primary1 = primary1;
    }

    public void setPrimary2(ColorUIResource primary2) {
        _primary2 = primary2;
    }

    public void setPrimary3(ColorUIResource primary3) {
        _primary3 = primary3;
    }

    public void setPrimaryControl(ColorUIResource primaryControl) {
        _primaryControl = primaryControl;
    }

    public void setPrimaryControlDarkShadow(ColorUIResource primaryControlDarkShadow) {
        _primaryControlDarkShadow = primaryControlDarkShadow;
    }

    public void setPrimaryControlHighlight(ColorUIResource primaryControlHighlight) {
        _primaryControlHighlight = primaryControlHighlight;
    }

    public void setPrimaryControlInfo(ColorUIResource primaryControlInfo) {
        _primaryControlInfo = primaryControlInfo;
    }

    public void setPrimaryControlShadow(ColorUIResource primaryControlShadow) {
        _primaryControlShadow = primaryControlShadow;
    }

    public void setSecondary1(ColorUIResource secondary1) {
        _secondary1 = secondary1;
    }

    public void setSecondary2(ColorUIResource secondary2) {
        _secondary2 = secondary2;
    }

    public void setSecondary3(ColorUIResource secondary3) {
        _secondary3 = secondary3;
    }

    public void setSeparatorBackground(ColorUIResource separatorBackground) {
        _separatorBackground = separatorBackground;
    }

    public void setSeparatorForeground(ColorUIResource separatorForeground) {
        _separatorForeground = separatorForeground;
    }

    public void setSubTextFont(FontUIResource subTextFont) {
        _subTextFont = subTextFont;
    }

    public void setSystemTextColor(ColorUIResource systemTextColor) {
        _systemTextColor = systemTextColor;
    }

    public void setSystemTextFont(FontUIResource systemTextFont) {
        _systemTextFont = systemTextFont;
    }

    public void setTextHighlightColor(ColorUIResource textHighlightColor) {
        _textHighlightColor = textHighlightColor;
    }

    public void setUserTextColor(ColorUIResource userTextColor) {
        _userTextColor = userTextColor;
    }

    public void setUserTextFont(FontUIResource userTextFont) {
        _userTextFont = userTextFont;
    }

    public void setWhite(ColorUIResource white) {
        _white = white;
    }

    public void setWindowBackground(ColorUIResource windowBackground) {
        _windowBackground = windowBackground;
    }

    public void setWindowInactiveBackground(ColorUIResource windowInactiveBackground) {
        _windowInactiveBackground = windowInactiveBackground;
    }

    public void setWindowTitleBackground(ColorUIResource windowTitleBackground) {
        _windowTitleBackground = windowTitleBackground;
    }

    public void setWindowTitleFont(FontUIResource windowTitleFont) {
        _windowTitleFont = windowTitleFont;
    }

    public void setWindowTitleForeground(ColorUIResource windowTitleForeground) {
        _windowTitleForeground = windowTitleForeground;
    }

    public void setWindowTitleInactiveForeground(ColorUIResource windowTitleInactiveForeground) {
        _windowTitleInactiveForeground = windowTitleInactiveForeground;
    }
}
