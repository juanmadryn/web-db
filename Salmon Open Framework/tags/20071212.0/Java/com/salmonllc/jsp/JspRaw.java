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

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/JspRaw.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 10/30/02 2:38p $
/////////////////////////

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreExpression;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This type can be used to add raw html to your page.
 */
public class JspRaw extends JspContainer {
    private String _html;
    private DataStoreEvaluator _dsEval = null;
    private InputStream _in = null;
    private String _fileName;


    /**
     * Constructs a raw html object for the page
     * @param html - HTML to place in the page.
     * @param p - page to place the HTML in.
     */
    public JspRaw(String html, com.salmonllc.html.HtmlPage p) {
        super("", p);
        _html = html;
    }


    /**
     * Constructs a raw html object for the page
     * @param name - The name of the component in the page
     * @param html - HTML to place in the page.
     * @param p - page to place the HTML in.
     */

    public JspRaw(String name, String html, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _html = html;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws java.io.IOException {
        try {
            if (_dsEval != null) {
                Object res;

                if (rowNo > -1)
                    res = _dsEval.evaluateRow(rowNo);
                else
                    res = _dsEval.evaluateRow();

                if (res == null)
                    _html = null;
                else
                    _html = res.toString();
            }
        }
        catch (Exception e) {
        }
        if (!getVisible())
            return;

        if (_in != null) {
            BufferedReader buf = new BufferedReader(new InputStreamReader(_in, "ISO8859_1"));
            char[] c = new char[1024];
            int bytesRead = 0;
            while (true) {
                bytesRead = buf.read(c, 0, 1024);
                if (bytesRead <= 0)
                    break;
                p.write(c, 0, bytesRead);
            }
            buf.close();
            return;
        }

        if (_html == null)
            return;

        p.print(_html);
    }

    /**
     * Use this method get the name file on the server file system to be streamed back to the browser.
     */
    public String getFileName() {
        return _fileName;

    }

    /**
     * This method returns the html in the component.
     */
    public String getHtml() {
        return _html;
    }

    /**
     * This method sets the input stream that the component will draw it's html from.
     */
    public InputStream getInputStream() {
        return _in;
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
     * Use this method to bind this component to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setExpression(DataStoreBuffer ds, String expression) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to specify the name file on the server file system to be streamed back to the browser.
     */
    public void setFileName(String fileName) {
        try {
            setInputStream(new java.io.FileInputStream(fileName));
            _fileName = fileName;
        }
        catch (Exception e) {
        }

    }

    /**
     * This method sets the html that the componnt will generate.
     */
    public void setHtml(String html) {
        _html = html;
    }

    /**
     * This method sets the input stream that the component will draw it's html from.
     */
    public void setInputStream(InputStream in) {
        _in = in;
    }

    /**
     * This method gets the DataStoreEvaluator being used for expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getExpression() {
        return _dsEval;
    }
}
