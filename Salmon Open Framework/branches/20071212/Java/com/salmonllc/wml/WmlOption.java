
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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlOption.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 6/11/03 4:52p $
/////////////////////////

import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.PrintWriter;

/**
 * This container will construct an option in a wml select.
 */
public class WmlOption extends JspContainer {
    private String _href;
    private DataStoreEvaluator _dsEval;
    private String _class;
    private String _value;
    private String _title;
    private String _box;

    /**
     * WmlLink constructor comment.
     * @param name The name of the option
     * @param href The url for the option to go to
     * @param p The page the option will go in.
     */

    public WmlOption(String name, String value, String title, String href, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _href = href;
        _value=value;
        _title=title;
    }

    /**
     *Does the binding for the component. This method is called by the framework and should not be called directly
     */
    public void doBinding() throws Exception {
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

        setHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
    }

    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(PrintWriter t, int rowNo) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();

        if (!_visible)
            return;
        StringBuffer href = null;
           try {
                if (_dsEval != null) {
                    if (rowNo > -1) {
                        _href = _dsEval.evaluateRowFormat(rowNo);
                    }
                    else {
                        _href = _dsEval.evaluateRowFormat();
                    }
                }
            }
            catch (Exception e) {
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_href)) {
                href = new StringBuffer(_href);
                int hrefLength = href.length();
                for (int i = 0; i < hrefLength; i++) {
                    if (href.charAt(i) == ' ') {
                        href.setCharAt(i, '+');
                    }
                }
            }
        String row = "";
        if (rowNo != -1)
            row = "_" + rowNo;
        sb.append("<option");
        sb.append(" id=\"" + getName() + row + "\"");
        if (_value != null)
            sb.append(" value=\"" + _value + "\"");
        if (_title != null)
            sb.append(" title=\"" + _title + "\"");
        if (_class != null)
            sb.append(" class=\"" + _class + "\"");
        if (_href != null)
            sb.append(" onpick=\"" + encodeURL(_href) + "\"");
        sb.append(">");
        t.print(sb.toString());
        t.print(_box);
        t.print("</option>");

    }

    /**
     *Sets the body content of the Option. This method is called by the framework and should not be called directly
     */
    public void setBodyContent(String box) {
        _box=box;
    }

    /**
     *Gets the body content of the Option. This method is called by the framework and should not be called directly
     */
    public String getBodyContent() {
        return _box;
    }


    /**
     * Returns the href for the option
     */
    public String getHref() {
        return _href;
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
     * Use this method to get the class attribute of the option
     */
    public String getClassName() {
        return _class;
    }
    /**
     * Use this method to get the value of the option
     */
    public String getValue() {
        return _value;
    }
    /**
     * This method sets the title.
     */
    public void setTitle(String title) {
        _title = title;
    }
    /**
     * This method sets the value.
     */
    public void setValue(String value) {
        _value = value;
    }
    /**
     * Sets the href for the option
     * @param href java.lang.String
     */
    public void setHref(String href) {
        _href = href;
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
    /**
     * This method sets the class for the option.
     */
    public void setClassName(String className) {
        _class = className;
    }

}
