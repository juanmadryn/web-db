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

import java.io.Serializable;


/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlOption.java $
//$Author: Saqib $
//$Revision: 5 $
//$Modtime: 4/22/04 4:23p $
/////////////////////////


/**
 * This type is used for the options in the HtmlDropDownList.
 */
public class HtmlOption implements Serializable, Cloneable {
    private String _classname;
    private String _display;
    private String _key;
    private String _style;
    private boolean _selected;

    /**
     * This method is the default Constructor
     */
    public HtmlOption() {
        super();
    }

    public HtmlOption(String key, String disp) {
        this(key, disp, false);


    }

    public HtmlOption(String key, String disp, boolean selected) {
        super();
        setKey(key);
        setDisplay(disp);
        setSelected(selected);

    }

    /**
     * Sets the classname.
     *
     * @param classname The classname to set
     */
    public void setClassname(String classname) {
        _classname = classname;
    }

    /**
     * Returns the classname.
     *
     * @return String
     */
    public String getClassname() {
        return _classname;
    }

    /**
     * Sets the display.
     *
     * @param display The display to set
     */
    public void setDisplay(String display) {
        _display = display;
    }

    /**
     * Returns the display.
     *
     * @return String
     */
    public String getDisplay() {
        return _display;
    }

    /**
     * Sets the key.
     *
     * @param key The key to set
     */
    public void setKey(String key) {
        _key = key;
    }

    /**
     * Returns the key.
     *
     * @return String
     */
    public String getKey() {
        return _key;
    }

    /**
     * Sets the style.
     *
     * @param style The style to set
     */
    public void setStyle(String style) {
        _style = style;
    }

    /**
     * Returns the style.
     *
     * @return String
     */
    public String getStyle() {
        return _style;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String ret = super.toString();
        ret += "\nKey:'" + getKey() + "'";
        ret += "\nDisplay:'" + getDisplay() + "'";

        return ret;
    }

    /**
     * Returns the selected.
     * @return boolean
     */
    public boolean isSelected() {
        return _selected;
    }

    /**
     * Sets the selected.
     * @param selected The selected to set
     */
    public void setSelected(boolean selected) {
        _selected = selected;
    }

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

}
