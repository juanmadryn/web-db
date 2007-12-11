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
package com.salmonllc.wml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlSelect.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 6/11/03 4:52p $
/////////////////////////

import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is used for Wml Select Component.
 */
public class WmlSelect extends WmlFormComponent {
    private String _title;
    private String _value;
    private String _iname;
    private String _ivalue;
    private boolean _multiple=false;
    private int _tabindex=-1;
    private Vector _options = new Vector();
    private Vector _wmloptions = new Vector();
    private String _hrefonpick;
    private DataStoreEvaluator _dsEval;

    /**
     * Returns the href for the onpick action
     */
    public String getHrefOnPick() {
        return _hrefonpick;
    }

    /**
     * Sets the href for the onpick action
     */
    public void setHrefOnPick(String _hrefonpick) {
        this._hrefonpick = _hrefonpick;
    }

    /**
     * Use this method to add new choices to the list.
     * @param wo An WmlOption component
     */
    public void addOption(WmlOption wo) {
        _wmloptions.addElement(wo);
    }


    /**
     * Use this method to add new choices to the list.
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     */
    public void addOption(String key, String disp) {
        addOption(key, disp, false);
    }

    /**
     * Use this method to add new choices to the list.
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     */
    public void addOption(String key, String disp, boolean selected) {
        ThreeObjectContainer t = new ThreeObjectContainer(key, disp, new Boolean(selected));
        _options.addElement(t);
    }

    /**
     * This method removes all options from the component.
     */
    public void resetOptions() {
        _options.removeAllElements();
    }

    /**
     * This method removes all options from the component.
     */
    public void resetWmlOptions() {
        _wmloptions.removeAllElements();
    }


    /**
     * Creates a list box based on a table with an <BR>
     * integer primary key column (typically an id) and a string column.<BR>
     * A simplifying assumption is that each of the following <BR>
     * is the same: <BR>
     * - name of column in the main table which refers to the simple table <BR>
     * - name of integer column in simple table <BR>
     * @param table - name of table to look up keys and displays from
     * @param keyColumn - column to get key values from
     * @param dispColumn - column to get display values from
     * @param criteria - optional selection criteria
     * @param inputVersion - optional value that allows for a null to be placed at the top
     * @param trimResults - optional value that trims the rtesults before adding the options
     * @param toUpper - optional value that makes the option's kays and display values all upper case
     */
    public void initialize(
            String table,
            String keyColumn,
            String dispColumn,
            String criteria,
            boolean inputVersion,
            boolean trimResults,
            boolean toUpper) {
        resetOptions();
        if (inputVersion)
            addOption(null, "");

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
            System.out.println(query);
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
     * Constructs a new WmlSelect component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public WmlSelect(String name, HtmlPage p) {
        super(name, p);
    }

    /**
     * This method returns the key of the first selected option in the list box..
     */
    private String getInternalValue() {
        int optionsSize = _options.size();
        ThreeObjectContainer t = null;
        Boolean b = null;
        for (int i = 0; i < optionsSize; i++) {
            t = (ThreeObjectContainer) _options.elementAt(i);
            b = (Boolean) t.getObject3();
            if (b.booleanValue())
                return (String) t.getObject1();
        }
        return null;
    }

    /**
     *Processes the submitted parameters. This method is called by the framework and should not be called directly
     */
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        Object oldValue = _value;
        String name = getName();
        if (rowNo > -1) {
            name += "_" + rowNo;
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(rowNo, _dsColNo);
        } else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(_dsColNo);
        }
        String val[] = (String[]) parms.get(name);
        int optionsSize = _options.size();
        ThreeObjectContainer t = null;
        for (int i = 0; i < optionsSize; i++) {
            t = (ThreeObjectContainer) _options.elementAt(i);
            t.setObject3(new Boolean(false));
        }
        if (val != null) {
            for (int i = 0; i < val.length; i++) {
                for (int k = 0; k < optionsSize; k++) {
                    t = (ThreeObjectContainer) _options.elementAt(k);
                    if (t.getObject1().equals(val[i])) {
                      t.setObject3(new Boolean(true));
                      break;
                    }
                }
            }
        }
        _value = getInternalValue();
        if (!valuesEqual(oldValue, _value)) {
            String s = null;
            if (oldValue != null)
                s = oldValue.toString();
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }
        return false;
    }

    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;

        if (!getEnabled()) {
            String out = getValue(rowNo, true);
            if (out != null) 
                out = fixSpecialHTMLCharacters(out);
            else
                out = "";

            // Code added in an attempt to remove unsightly '&nbsp;' in the HtmlTelephonecomponent and
            // HtmlSSNComponent.
            // The premise is that the '&nbsp;' was introduced to provide for the case when the field
            // is disabled and when the string is empty or null
            // The IF statement is introduced to do as much without impacting non-null and non-empty strings
            if ((out == null) || ((out != null) && (out.equals(""))))
                out += "&nbsp;";

            if (_disabledFontStartTag != null)
                p.print(_disabledFontStartTag + out + _disabledFontEndTag);
            else
                p.print(out);
            return;
        }

        String name = getName();
        if (rowNo > -1)
            name += "_" + rowNo;
        String tag = "<select id=\"" + name + "\" name=\"" + name + "\"";

        if (_multiple)
            tag += " multiple=\"TRUE\"";



        String value = getValue(rowNo, true);

        if (value != null)
            tag += " value=\"" + fixSpecialHTMLCharacters(value) + "\"";


        if (_class != null)
            tag += " class=\"" + _class + "\"";

        if (_iname != null)
            tag += " iname=\"" + _iname + "\"";

        if (_ivalue != null)
            tag += " ivalue=\""+_ivalue+"\"";

        if (_tabindex > -1)
            tag += " tabindex=\"" + _tabindex + "\"";

        if (_title != null)
            tag += " title=\"" + _title + "\"";

        tag += ">";


        p.print(tag);

        if (_options.size()>0) {
            for (int i=0; i<_options.size();i++) {
                StringBuffer href = null;
                   try {
                        if (_dsEval != null) {
                            _hrefonpick = _dsEval.evaluateRowFormat(i);
                        }
                    }
                    catch (Exception e) {
                    }
                    // sr 12-08-2000 was getting a null pointer exception
                    if (!Util.isNull(_hrefonpick)) {
                        href = new StringBuffer(_hrefonpick);
                        int hrefLength = href.length();
                        for (int k = 0; k < hrefLength; k++) {
                            if (href.charAt(k) == ' ') {
                                href.setCharAt(k, '+');
                            }
                        }
                    }
               String sValue=((ThreeObjectContainer)_options.elementAt(i)).getObject1().toString();
               String sDisp=((ThreeObjectContainer)_options.elementAt(i)).getObject2().toString();
               String sOption="<option value=\""+sValue+"\"";
               if (href!=null && !href.toString().trim().equals(""))
                 sOption+=" onpick=\""+href+"\"";
               sOption+=">"+sDisp+"</option>";
               p.print(sOption);
            }
        }
        if (_wmloptions.size()>0) {
            for (int i=0; i<_wmloptions.size();i++) {
                ((WmlOption)_wmloptions.elementAt(i)).generateHTML(p,rowNo);
            }
        }
        p.print("</select>");
    }


    /**
     * This method gets the iname for the text in the component.
     */
    public String getIname() {
        return _iname;
    }


    /**
     * This method gets the ivalue for the component in characters.
     */
    public String getIvalue() {
        return _ivalue;
    }

        /**
         * This method gets the title for the input.
         */
        public String getTitle() {
            return _title;
        }
        /**
         * This method gets the tabindex of the input.
         */
        public int getTabIndex() {
            return _tabindex;
        }
        /**
         * This method gets the multiple flag of the input.
         */
        public boolean getMultiple() {
            return _multiple;
        }



    /**
     * This method sets the iname for the text in the component.
     */
    public void setIname(String iname) {
        _iname = iname;
    }


    /**
     * This method sets the ivalue for the component in characters.
     */
    public void setIvalue(String ivalue) {
        _ivalue = ivalue;
    }

    /**
     * This method sets the multiple flag for input.
     */
    public void setMultiple(boolean multiple) {
        _multiple = multiple;
    }
    /**
     * This method sets the tabindex for input.
     */
    public void setTabIndex(int tabindex) {
        _tabindex = tabindex;
    }
    /**
     * This method sets the title for input.
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * This method gets the postfield form of this component.
     */
    public String getPostForm() {
        if (getVisible() && getEnabled())
          return "<postfield name=\""+getName()+"\" value=\"$("+getName()+")\"/>";
        return "";
    }

    /**
     *Does the binding for the component. This method is called by the framework and should not be called directly
     */
    public void doBinding() throws Exception {
        for (int i=0;i<_wmloptions.size();i++) {
            WmlOption wo=(WmlOption)_wmloptions.elementAt(i);
            wo.doBinding();
        }
        if (_options.size()>0) {
            String dataSource = getDataSource();
            String dsName = null;
            String dsExp = null;

            if (dataSource == null)
                return;

            int pos = dataSource.indexOf(":");
            if (pos == -1)
                dsName = dataSource;
            else {
                dsName = dataSource.substring(0, pos);
                dsExp = dataSource.substring(pos + 1);
            }

            DataStoreBuffer ds = ((JspController)getPage()).getDataSource(dsName);
            if (ds == null)
                return;

            if (!ds.getAutoBind())
                return;

            if (dsExp==null)
              dsExp=_hrefonpick;
            setHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
        }
    }

    /**
     * This method gets the DataStoreEvaluator being used for href expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getHrefExpression() {
        return _dsEval;
    }

    /**
     * This method sets a datastore expression that will be used to compute the href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     */
    public void setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     */
    public void setHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }

}
