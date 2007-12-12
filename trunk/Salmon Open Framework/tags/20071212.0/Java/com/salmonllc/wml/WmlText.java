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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlText.java $
//$Author: Srufle $
//$Revision: 3 $
//$Modtime: 4/15/03 3:11p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;

/**
 * This type can be used to add text to your page.
 */
public class WmlText extends HtmlComponent {

    public boolean _bold = false;
    public boolean _big = false;
    public boolean _italic = false;
    public boolean _small = false;
    public boolean _strong = false;
    public boolean _underline = false;

    public static final int AGGREGATE_SUM = DataStoreEvaluator.AGGREGATE_SUM;
    public static final int AGGREGATE_COUNT = DataStoreEvaluator.AGGREGATE_COUNT;
    public static final int AGGREGATE_AVERAGE = DataStoreEvaluator.AGGREGATE_AVERAGE;

    private String _text;
    private int _aggregateType = -1;

    private DataStoreEvaluator _dsEval = null;
    private boolean _fixSpecialWml = true;

    /**
     * Constructs an wml text object for the page.
     * @param text The text to place in the page.
     * @param p The page to put the text in.
     */
    public WmlText(String text, HtmlPage p) {
        super("", p);
        _text = text;


    }

    /**
     * Constructs an wml text object for the page.
     * @param text The text to place in the page.
     * @param p The page to put the text in.
     */
    public WmlText(String name,String text, HtmlPage p) {
        super(name, p);
        _text = text;


    }



    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

        if (_dsEval != null) {
            if (_aggregateType != -1) {
                _text = _dsEval.evaluateAggregateFormat(_aggregateType);
            }
            else {
                if (rowNo > -1)
                    _text = _dsEval.evaluateRowFormat(rowNo);
                else
                    _text = _dsEval.evaluateRowFormat();
            }
        }

        String out = _text;

        if (_fixSpecialWml)
            out = fixSpecialHTMLCharacters(out);

        String start="";
        String end="";
        if (_bold) {
            start += "<b>";
            end = "</b>"+end;
        }
        if (_big) {
            start += "<big>";
            end = "</big>"+end;
        }
        if (_italic) {
            start += "<i>";
            end = "</i>"+end;
        }
        if (_small) {
            start += "<small>";
            end = "</small>"+end;
        }
        if (_strong) {
            start += "<strong>";
            end = "</strong>"+end;
        }
        if (_underline) {
            start += "<u>";
            end = "</u>"+end;
        }

        p.print(start+out+end);
    }

    public void generateHTML(java.io.PrintWriter p, int rowStart, int rowEnd) throws Exception {
        if (!getVisible())
            return;

        if (_dsEval != null) {
            if (_aggregateType != -1) {
                _text = _dsEval.evaluateAggregateFormat(_aggregateType, rowStart, rowEnd);
            }
            else {
                if (rowStart > -1)
                    _text = _dsEval.evaluateRowFormat(rowStart);
                else
                    _text = _dsEval.evaluateRowFormat();
            }
        }

        String out = _text;

        if (_fixSpecialWml)
            out = fixSpecialHTMLCharacters(out);

        //-----------------------------------------------------------------------------------------------

        String start="";
        String end="";
        if (_bold) {
            start += "<b>";
            end = "</b>"+end;
        }
        if (_big) {
            start += "<big>";
            end = "</big>"+end;
        }
        if (_italic) {
            start += "<i>";
            end = "</i>"+end;
        }
        if (_small) {
            start += "<small>";
            end = "</small>"+end;
        }
        if (_strong) {
            start += "<strong>";
            end = "</strong>"+end;
        }
        if (_underline) {
            start += "<u>";
            end = "</u>"+end;
        }

        p.print(start+out+end);
    }

    /**
     * Use this method to find out if the that the text will be bold.
     */
    public boolean getBold() {
        return _bold;
    }
    /**
     * Use this method to find out if the that the text will be big.
     */
    public boolean getBig() {
        return _big;
    }
    /**
     * Use this method to find out if the that the text will be italic.
     */
    public boolean getItalic() {
        return _italic;
    }
    /**
     * Use this method to find out if the that the text will be small.
     */
    public boolean getSmall() {
        return _small;
    }
    /**
     * Use this method to find out if the that the text will be strong.
     */
    public boolean getStrong() {
        return _strong;
    }
    /**
     * Use this method to find out if the that the text will be underline.
     */
    public boolean getUnderline() {
        return _underline;
    }

    /**
     * This method returns the DataStore Evaluator used to parse and evaluate the expression set in the setExpression method.
     */
    public DataStoreEvaluator getExpressionEvaluator() {
        return _dsEval;
    }

    /**
     * Returns whether special html characters (<,>,&,; etc..) should be converted to Html Escape Sequences before being generated.
     */
    public boolean getFixSpecialWmlCharacters() {
        return _fixSpecialWml;
    }



    /**
     * This method returns the text in the component.
     */
    public String getText() {
        return _text;
    }


    public boolean processParms(java.util.Hashtable h, int row) {
        return false;
    }

    /**
     * Use this method to indicate that the text should be bold.
     */
    public void setBold(boolean bold) {
        _bold = bold;
    }
    /**
     * Use this method to indicate that the text should be big.
     */
    public void setBig(boolean big) {
        _big = big;
    }
    /**
     * Use this method to indicate that the text should be italic.
     */
    public void setItalic(boolean italic) {
        _italic = italic;
    }
    /**
     * Use this method to indicate that the text should be small.
     */
    public void setSmall(boolean small) {
        _small = small;
    }
    /**
     * Use this method to indicate that the text should be strong.
     */
    public void setStrong(boolean strong) {
        _strong = strong;
    }
    /**
     * Use this method to indicate that the text should be underline.
     */
    public void setUnderline(boolean underline) {
        _underline = underline;
    }

    /**
     * Use this method to bind this component to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, int aggregateType) throws Exception {
        _aggregateType = aggregateType;
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
     * @param format The patter to use to format the result
     * @see DataStore#setFormat
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, int aggregateType, String format) throws Exception {
        _aggregateType = aggregateType;
        _dsEval = new DataStoreEvaluator(ds, expression, format);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The resulting expression wil be formatted according to the pattern specified.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param format The patter to use to format the result
     * @see DataStore#setFormat
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, String format) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression, format);
    }

    /**
     * Use this method to bind this component to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, String expression) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, String expression, int aggregateType) throws Exception {
        _aggregateType = aggregateType;
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
     * @param format The patter to use to format the result
     * @see DataStore#setFormat
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, String expression, int aggregateType, String format) throws Exception {
        _aggregateType = aggregateType;
        _dsEval = new DataStoreEvaluator(ds, expression, format);
    }

    /**
     * Use this method to bind this component to an expression in a DataStoreBuffer. The resulting expression wil be formatted according to the pattern specified.
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @param format The patter to use to format the result
     * @see DataStore#setFormat
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, String expression, String format) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression, format);
    }

    /**
     * Specify whether special wml characters (<,>,&,; etc..) should be converted to Html Escape Sequences before being generated.
     */
    public void setFixSpecialWmlCharacters(boolean fix) {
        _fixSpecialWml = fix;
    }


    /**
     * This method sets the text that the componnt will generate.
     */
    public void setText(String text) {
        _text = text;
    }


    /**
     * This method returns the text in the component.
     */
    public String getText(int rowNo) {
        try {
            if (_dsEval != null) {
                if (_aggregateType != -1) {
                    _text = _dsEval.evaluateAggregateFormat(_aggregateType);
                }
                else {
                    if (rowNo > -1)
                        _text = _dsEval.evaluateRowFormat(rowNo);
                    else
                        _text = _dsEval.evaluateRowFormat();
                }
            }
        }
        catch (Exception e) {
            com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
        }
        return _text;
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

        int index = -1;
        if (dsExp != null)
            index = ds.getColumnIndex(dsExp);

        if (index != -1)
            setExpression(ds, ds.getColumnName(index), ds.getFormat(index));
        else
            setExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));

    }


}
