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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlRadioButtonGroup.java $
//$Author: Dan $
//$Revision: 28 $
//$Modtime: 10/20/03 4:07p $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.ThreeObjectContainer;

import com.salmonllc.html.events.*;

import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is used to create a group of radio buttons that act like a single entity.
 */
public class HtmlRadioButtonGroup extends HtmlFormComponent {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    public static final int CAPTIONS_ON_LEFT = 0;
    public static final int CAPTIONS_ON_RIGHT = 1;
    public static final int CAPTIONS_ON_TOP = 2;

    private String _fontTagStart;
    private String _fontTagEnd;

    private Vector _options = new Vector();
    private int _orientation = ORIENTATION_HORIZONTAL;
    private int _captionLayout = CAPTIONS_ON_LEFT;
    private String _onClick = null;
    private boolean _useProportions = false;

    private String _imageOn;
    private String _imageOff;
    private Integer _tabIndex;

    /**
     * Constructs a new HtmlRadioButtonGroup component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlRadioButtonGroup(String name, HtmlPage p) {
        this(name, null, p);
    }

    /**
     * Constructs a new HtmlRadioButtonGroup component.
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page the component will be placed in.
     */
    public HtmlRadioButtonGroup(String name, String theme, HtmlPage p) {
        super(name, p);
        setTheme(theme);

    }

    /**
     * Creates a radio button group plus an associated datastore column based on a simple table with an <BR>
     * integer primary key column and a string column.  Typically the integer is a type value <BR>
     * and the string is a description.  A simplifying assumption is that each of the following <BR>
     * is the same: <BR>
     * - name of column in the main table which refers to the simple table <BR>
     * - name of integer column in simple table <BR>
     * @param page com.salmonllc.html.HtmlPage		The page hold the new component
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     */
    public HtmlRadioButtonGroup(String name, String theme, HtmlPage page, String table, String keyColumn, String dispColumn) {
        super(name, theme, page);
        /**
         * srufle 04-02-2002
         * using initialize method now
         * should be functionally the same
         */
        initialize(table, keyColumn, dispColumn, null);
    }

    /**
     * Creates a radio button group based on a table with an <BR>
     * integer primary key column (typically an id) and a string column.<BR>
     * A simplifying assumption is that each of the following <BR>
     * is the same: <BR>
     * - name of column in the main table which refers to the simple table <BR>
     * - name of integer column in simple table <BR>
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     */
    public void initialize(
            String table,
            String keyColumn,
            String dispColumn,
            String criteria) {
        initialize(table, keyColumn, dispColumn, criteria, false);
    }

    /**
     * Creates a radio button group based on a table with an <BR>
     * integer primary key column (typically an id) and a string column.<BR>
     * A simplifying assumption is that each of the following <BR>
     * is the same: <BR>
     * - name of column in the main table which refers to the simple table <BR>
     * - name of integer column in simple table <BR>
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param trimResults - optional value that trims the rtesults before adding the options
     */
    public void initialize(
            String table,
            String keyColumn,
            String dispColumn,
            String criteria,
            boolean trimResults) {
        initialize(table, keyColumn, dispColumn, criteria, trimResults, false);

    }

    /**
     * Creates a radio button group based on a table with an <BR>
     * integer primary key column (typically an id) and a string column.<BR>
     * A simplifying assumption is that each of the following <BR>
     * is the same: <BR>
     * - name of column in the main table which refers to the simple table <BR>
     * - name of integer column in simple table <BR>
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param trimResults - optional value that trims the rtesults before adding the options
     * @param toUpper - optional value that makes the option's kays and display values all upper case
     */
    public void initialize(
            String table,
            String keyColumn,
            String dispColumn,
            String criteria,
            boolean trimResults,
            boolean toUpper) {
        resetOptions();

        DBConnection connection = null;
        try {
            connection = DBConnection.getConnection(getPage().getApplicationName());
            java.sql.Statement s = connection.createStatement();
            String query = null;
            if (criteria != null) {
                query = "SELECT " + keyColumn + "," + dispColumn + " FROM " + table + " WHERE " + criteria + " ORDER BY ";

                if (toUpper)
                    query = query + "UPPER(" + dispColumn + ")";
                else
                    query += dispColumn;
            } else {
                query = "SELECT " + keyColumn + "," + dispColumn + " FROM " + table + " ORDER BY ";

                if (toUpper)
                    query = query + "UPPER(" + dispColumn + ")";
                else
                    query += dispColumn;
            }

            java.sql.ResultSet r = s.executeQuery(query);
            if (r.next()) {
                do {
                    if (trimResults) {
                        addOption(r.getObject(1).toString().trim(), r.getObject(2).toString().trim());
                    } else {
                        addOption(r.getObject(1).toString(), r.getObject(2).toString());
                    }
                } while (r.next());
            }
            r.close();
            s.close();
        } catch (java.sql.SQLException se) {
            MessageLog.writeErrorMessage("initialize", se, null);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("initialize", e, this);
        } finally {
            if (connection != null)
                connection.freeConnection();
        }

    }

    /**
     * Use this method to add new choices to the list.
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     */
    public void addOption(String key, String disp) {
        ThreeObjectContainer t = new ThreeObjectContainer(key, disp, null);
        _options.addElement(t);
    }

    /**
     * Use this method to add new choices to the list.
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     * @param javaScript The javaScript to be executed when the radio button is clicked.
     */
    public void addOption(String key, String disp, String javaScript) {
        ThreeObjectContainer t = new ThreeObjectContainer(key, disp, javaScript);
        _options.addElement(t);
    }

    /**
     * This method returns the index of the option with the specified key.
     * @return The index of the key or -1 if not found.
     */
    public int findOptionIndexOf(String key) {
        int optionsSize = _options.size();
        ThreeObjectContainer t = null;
        for (int i = 0; i < optionsSize; i++) {
            t = (ThreeObjectContainer) _options.elementAt(i);
            if (key == null) {
                if (t.getObject1() == null)
                    return i;
            } else if (key.equals(t.getObject1()))
                return i;
        }
        return -1;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;
        if (!getEnabled() && !useDisabledAttribute()) {
            generateHTMLDisabled(p, rowNo);
            return;
        }
        String align = "";
        if (_captionLayout == CAPTIONS_ON_TOP)
            align = " ALIGN=\"CENTER\"";
        String startTag = "";
        String endTag = "";
        if (_orientation == ORIENTATION_HORIZONTAL) {
            if (_useProportions) {
                int width = 100 / _options.size();
                startTag = "<TD WIDTH=\"" + width + "%\"" + align;
                if (_class!=null && !_class.trim().equals(""))
                  startTag+=" class=\""+_class+"\"";
                startTag+=">";
            } else {
                startTag = "<TD" + align;
                if (_class!=null && !_class.trim().equals(""))
                  startTag+=" class=\""+_class+"\"";
                startTag+=">";
            }
            endTag = "</TD>";
        } else {
            startTag = "<TR" + align + ">";
            endTag = "</TR>";
        }
        String tag = "<TABLE>";
        if (_orientation == ORIENTATION_HORIZONTAL && _useProportions)
            tag = "<TABLE WIDTH=\"100%\">";
        _value = getValue(rowNo, true);
        if (_captionLayout == CAPTIONS_ON_RIGHT || _captionLayout == CAPTIONS_ON_LEFT) {
            if (_orientation == ORIENTATION_HORIZONTAL)
                tag += "<TR>";
            for (int i = 0; i < _options.size(); i++) {
                tag += startTag;
                if (_orientation == ORIENTATION_VERTICAL) {
                    tag += "<TD";
                    if (_class!=null && !_class.trim().equals(""))
                      tag+=" class=\""+_class+"\"";
                    tag +=">";
                }
                if (_captionLayout == CAPTIONS_ON_LEFT)
                    tag += getCaptionTag(i) + getRadioButtonTag(i, rowNo, _value);
                else
                    tag += getRadioButtonTag(i, rowNo, _value) + getCaptionTag(i);
                if (_orientation == ORIENTATION_VERTICAL)
                    tag += "</TD>";
                tag += endTag;
            }
            if (_orientation == ORIENTATION_HORIZONTAL)
                tag += "</TR>";
        } else {
            // CAPTIONS_ON_TOP
            int optionsSize = _options.size();
            if (_orientation == ORIENTATION_VERTICAL) {
                for (int i = 0; i < optionsSize; i++) {
                    tag += startTag + "<TD";
                    if (_class!=null && !_class.trim().equals(""))
                      tag+=" class=\""+_class+"\"";
                    tag +=">" + getCaptionTag(i) + "</TD>" + endTag;
                    tag += startTag + "<TD";
                    if (_class!=null && !_class.trim().equals(""))
                      tag+=" class=\""+_class+"\"";
                    tag+=">" + getRadioButtonTag(i, rowNo, _value) + "</TD>" + endTag;
                }
            } else {
                tag += "<TR>";
                for (int i = 0; i < optionsSize; i++)
                    tag += startTag + getCaptionTag(i) + endTag;
                tag += "</TR><TR>";
                for (int i = 0; i < optionsSize; i++)
                    tag += startTag + getRadioButtonTag(i, rowNo, _value) + endTag;
            }
        }
        tag += "</TABLE>";
        p.println(tag);
        writeFocusScript(p, rowNo);
    }

    public void generateHTMLDisabled(java.io.PrintWriter p, int rowNo) throws Exception {
        String align = "";
        if (_captionLayout == CAPTIONS_ON_TOP)
            align = " ALIGN=\"CENTER\"";
        String startTagImage = "<TD>";
        String startTagText = "";
        String endTag = "";
        if (_orientation == ORIENTATION_HORIZONTAL) {
            endTag = "</TD>";
            if (_useProportions) {
                int width = 100 / _options.size();
                startTagText = "<TD NOWRAP VALIGN=\"MIDDLE\" WIDTH=\"" + width + "%\"" + align;
                if (_class!=null && !_class.trim().equals(""))
                  startTagText+=" class=\""+_class+"\"";
                startTagText+=">";
            } else {
                startTagText = "<TD NOWRAP VALIGN=\"MIDDLE\"" + align;
                if (_class!=null && !_class.trim().equals(""))
                  startTagText+=" class=\""+_class+"\"";
                startTagText+=">";
            }
            switch (_captionLayout) {
                case CAPTIONS_ON_TOP:
                    startTagImage = "<TD VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" HEIGHT=\"22\"";
                    if (_class!=null && !_class.trim().equals(""))
                      startTagImage+=" class=\""+_class+"\"";
                    startTagImage+=">";
                    break;
                case CAPTIONS_ON_RIGHT:
                    startTagImage = "<TD VALIGN=\"MIDDLE\" HEIGHT=\"22\" WIDTH=\"16\" ALIGN=\"RIGHT\"";
                    if (_class!=null && !_class.trim().equals(""))
                      startTagImage+=" class=\""+_class+"\"";
                    startTagImage+=">";
                    break;
                case CAPTIONS_ON_LEFT:
                default :
                    startTagImage = "<TD VALIGN=\"MIDDLE\" HEIGHT=\"22\" WIDTH=\"16\" ALIGN=\"CENTER\"";
                    if (_class!=null && !_class.trim().equals(""))
                      startTagImage+=" class=\""+_class+"\"";
                    startTagImage+=">";
                    break;
            }
        } else {
            startTagImage = startTagText = "<TR" + align + "><TD";
            if (_class!=null && !_class.trim().equals(""))
              startTagImage+=" class=\""+_class+"\"";
            startTagImage+=">";
            endTag = "</TD></TR>";
        }
        String tag = "<TABLE>";
        if (_orientation == ORIENTATION_HORIZONTAL && _useProportions)
            tag = "<TABLE WIDTH=\"100%\">";
        _value = getValue(rowNo, true);
        // get size of vector now so we don't have to do it lots of times in the loops
        int optionsSize = _options.size();
        //
        if (_captionLayout == CAPTIONS_ON_RIGHT || _captionLayout == CAPTIONS_ON_LEFT) {
            if (_orientation == ORIENTATION_VERTICAL) {
                for (int i = 0; i < optionsSize; i++) {
                    if (_captionLayout == CAPTIONS_ON_LEFT)
                        tag += startTagText + getCaptionTag(i) + getRadioButtonTag(i, rowNo, _value);
                    else
                        tag += startTagImage + getRadioButtonTag(i, rowNo, _value) + getCaptionTag(i);
                    tag += endTag;
                }
            } else {
                tag += "<TR>";
                for (int i = 0; i < optionsSize; i++) {
                    if (_captionLayout == CAPTIONS_ON_LEFT)
                        tag += startTagText + getCaptionTag(i) + endTag + startTagImage + getRadioButtonTag(i, rowNo, _value);
                    else
                        tag += startTagImage + getRadioButtonTag(i, rowNo, _value) + endTag + startTagText + getCaptionTag(i);
                    tag += endTag;
                }
                tag += "</TR>";
            }
        } else {
            if (_orientation == ORIENTATION_VERTICAL) {
                for (int i = 0; i < optionsSize; i++)
                    tag += startTagText + getCaptionTag(i) + endTag + startTagImage + getRadioButtonTag(i, rowNo, _value) + endTag;
            } else {
                tag += "<TR>";
                for (int i = 0; i < optionsSize; i++)
                    tag += startTagText + getCaptionTag(i) + endTag;
                tag += "</TR><TR>";
                for (int i = 0; i < optionsSize; i++)
                    tag += startTagImage + getRadioButtonTag(i, rowNo, _value) + endTag;
            }
        }
        tag += "</TABLE>";
        p.println(tag);
        writeFocusScript(p, rowNo);
    }

    /**
     * Returns the caption layout of the component. Valid Values are CAPTIONS_ON_LEFT, CAPTIONS_ON_RIGHT and CAPTIONS_ON_TOP.
     */
    public int getCaptionLayout() {
        return _captionLayout;
    }

    private String getCaptionTag(int capNo) {
        String retVal = "";

        ThreeObjectContainer t = (ThreeObjectContainer) _options.elementAt(capNo);
        String caption = (String) t.getObject2();

        if (caption == null)
            return "&nbsp;";

        String fontS = _fontTagStart;
        if (fontS == null)
            fontS = "";
        String fontE = _fontTagEnd;
        if (fontE == null)
            fontE = "";

        if (caption != null)
            retVal += fontS + caption + fontE;

        return retVal;
    }

    /**
     * This method gets the end font tag for the component.
     */
    public String getFontEndTag() {
        return _fontTagEnd;
    }

    /**
     * This method gets the start font tag for the component.
     */
    public String getFontStartTag() {
        return _fontTagStart;
    }

    /**
     * This method gets the javascript to be executed when the component gets clicked.
     */
    public String getOnClick() {
        return _onClick;
    }

    /**
     * Use this method get the value of the key at index.
     */
    public String getOptionKey(int index) {
        if (index < 0 || index >= _options.size())
            return null;

        ThreeObjectContainer t = (ThreeObjectContainer) _options.elementAt(index);
        return (String) t.getObject1();
    }

    /**
     * Use this method get the value of the option at index.
     */
    public String getOptionValue(int index) {
        if (index < 0 || index >= _options.size())
            return null;

        ThreeObjectContainer t = (ThreeObjectContainer) _options.elementAt(index);
        return (String) t.getObject2();
    }

    /**
     * Returns the orientation of the component. Valid Values are ORIENTATION_VERTICAL and ORIENTATION_HORIZONTAL.
     */
    public int getOrientation() {
        return _orientation;
    }

    private String getRadioButtonTag(int capNo, int rowNo, String val) {
        ThreeObjectContainer t = (ThreeObjectContainer) _options.elementAt(capNo);
        String value = (String) t.getObject1();

        if (value == null)
            return "&nbsp;";

        boolean checked = false;
        if (val != null) {
            if (val.equals(value))
                checked = true;
        }

        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;

        String tag = "<INPUT TYPE=\"RADIO\" NAME=\"" + name + "\" VALUE=\"" + value + "\"";

        if ((!_enabled) && useDisabledAttribute()) {
             tag += " disabled=\"true\"";
        }
        else if (!_enabled && (_imageOn != null) && (_imageOff != null)) {
            String out = "<IMG SRC=\"";
            if (checked)
                out += _imageOn + "\"";
            else
                out += _imageOff + "\"";
            if (_orientation == ORIENTATION_VERTICAL)
                out += " ALIGN=\"ABSBOTTOM\" HSPACE= \"4\" VSPACE=\"5\"";
            out += ">";
            return out;
        }


        if (_onClick != null)
            tag += " ONCLICK=\"" + _onClick + "\"";
        if (_class != null)
            tag += " class=\"" + _class + "\"";
        else {
            String jScript = (String) t.getObject3();
            if (jScript != null)
                tag += " ONCLICK=\"" + jScript + "\"";
        }


        if (checked)
            tag += " CHECKED";

		if (_tabIndex != null) 
			tag += " tabindex=\"" + _tabIndex + "\"";
			
        tag += ">";

        if (_fontTagStart != null)
            tag = _fontTagStart + tag + _fontTagEnd;

        return tag;
    }

    /**
     * Returns the index of the selected option.
     */
    public int getSelectedIndex() {
        String option = getValue();
        return findOptionIndexOf(option);
    }

    /**
     * Returns the index of the selected option.
     */
    public int getSelectedIndex(int rowNo) {
        String option = getValue(rowNo);
        return findOptionIndexOf(option);
    }

    /**
     * Returns whether or not to use a fixed percent of available space for each button in the radio button group (Horizontal Orientation only)
     */
    public boolean getUseProportions() {
        return _useProportions;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        if (!getEnabled())
            return false;
        Object oldValue = _value;

        String name = getFullName();
        if (rowNo > -1) {
            name += "_" + rowNo;
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(rowNo, _dsColNo);
        } else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(_dsColNo);
        }

        String val[] = (String[]) parms.get(name);

        if (val == null)
            _value = null;
        else
            _value = val[0];

        if (!valuesEqual(oldValue, _value) && _value != null) {
            String s = null;
            if (oldValue != null)
                s = oldValue.toString();
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }


        return false;
    }

    /**
     * Use this method to remove an option from the list.
     * @param index The index of the option to remove.
     */
    public void removeOption(int index) {
        if (index < 0 || index >= _options.size())
            return;

        _options.removeElementAt(index);
    }

    /**
     * This method removes all options from the component.
     */
    public void resetOptions() {
        _options.removeAllElements();
    }

    /**
     * Sets the caption layout of the component. Valid Values are CAPTIONS_ON_LEFT, CAPTIONS_ON_RIGHT and CAPTIONS_ON_TOP.
     */
    public void setCaptionLayout(int layout) {
        _captionLayout = layout;
    }

    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontTagEnd = value;
    }

    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontTagStart = value;
    }

    /**
     * This method sets the javascript to be executed when the component is checked.
     */
    public void setOnClick(String value) {
        _onClick = value;
    }

    /**
     * Sets the orientation of the component. Valid Values are ORIENTATION_VERTICAL and ORIENTATION_HORIZONTAL.
     */
    public void setOrientation(int orientation) {
        _orientation = orientation;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        super.setTheme(theme);
        Props props = getPage().getPageProperties();

        _fontTagStart = props.getThemeProperty(theme, Props.FONT_DEFAULT + Props.TAG_START);
        _fontTagEnd = props.getThemeProperty(theme, Props.FONT_DEFAULT + Props.TAG_END);
        _imageOn = props.getThemeProperty(theme, Props.RADIOBUTTON_IMAGE_ON);
        _imageOff = props.getThemeProperty(theme, Props.RADIOBUTTON_IMAGE_OFF);

    }

    /**
     * This method is used to set whether the each button in the group will occupy a fixed percent available space (Horizontal Orientation only)
     */
    public void setUseProportions(boolean use) {
        _useProportions = use;
    }

    /**
     * This method sets the layout property for the component
     */
    public void setCaptionLayout(String layout) {
        int iLayout = CAPTIONS_ON_LEFT;
        if (layout != null) {
            layout = layout.toUpperCase();
            iLayout = layout.equals("TOP") ? CAPTIONS_ON_TOP : (layout.equals("RIGHT") ? CAPTIONS_ON_RIGHT : CAPTIONS_ON_LEFT);
        }
        setCaptionLayout(iLayout);
    }

    /**
     * This method sets the orientation property for the component
     */
    public void setOrientation(String orientation) {
        setOrientation(orientation == null || !orientation.toUpperCase().equals("VERTICAL") ? ORIENTATION_HORIZONTAL : ORIENTATION_VERTICAL);
    }
    
	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else	
			_tabIndex = new Integer(val);
	}
}
