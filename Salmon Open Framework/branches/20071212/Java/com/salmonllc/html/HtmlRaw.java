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
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlRaw.java $
//$Author: Dan $
//$Revision: 15 $
//$Modtime: 10/30/02 2:58p $
/////////////////////////

import com.salmonllc.html.events.*;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreExpression;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This type can be used to add a region of raw HTML to the page. The HTML can be static or can be loaded from an input stream.
 */
public class HtmlRaw extends HtmlComponent {
    private String _html;
    private DataStoreEvaluator _dsEval = null;
    private InputStream _in = null;

    /**
	 * @author  demian
	 */
    private class _PageAdapter implements PageListener {
        private String _fileName;
        private HtmlRaw _par;

        public _PageAdapter(HtmlRaw parent, String fileName) {
            _par = parent;
            _fileName = fileName;
            _par.getPage().addPageListener(this);
        }

        public void clear() {
            _par.getPage().removePageListener(this);
            _fileName = null;
            _par = null;
        }

        public String getFileName() {
            return _fileName;
        }

        public void pageRequested(PageEvent p) throws Exception {
            setInputStream(new FileInputStream(_fileName));
        }

        public void pageRequestEnd(PageEvent p) throws Exception {
        }

        public void pageSubmitEnd(PageEvent p) {
        }

        public void pageSubmitted(PageEvent p) {
        }
    }

    private _PageAdapter _fileProcessor;


    /**
     * Constructs a new raw html component
     * @param html - The html to place in the page.
     * @param p - page to use.
     */
    public HtmlRaw(String html, HtmlPage p) {
        super("", p);
        _html = html;
    }

    /**
     * Constructs a raw html component
     * @param name - The name of the component in the page
     * @param html - The html to place in the page.
     * @param p - page to use.
     */
    public HtmlRaw(String name, String html, HtmlPage p) {
        super(name, p);
        _html = html;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
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
        }

        if (_html == null)
            return;

        p.print(_html);
    }

    /**
     * Use this method get the name of the file on the server's file system to be streamed back to the browser.
     */
    public String getFileName() {
        if (_fileProcessor != null)
            return null;
        else
            return _fileProcessor.getFileName();

    }

    /**
     * This method returns the HTML in the component.
     */
    public String getHtml() {
        return _html;
    }

    public boolean processParms(java.util.Hashtable h, int row) {
        return false;
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
     * Use this method to specify the name of the file on the server's file system to be streamed back to the browser.
     */
    public void setFileName(String name) {
        if (_fileProcessor != null)
            _fileProcessor.clear();

        _fileProcessor = new _PageAdapter(this, name);

    }

    /**
     * This method sets the HTML that the componnt will generate.
     */
    public void setHtml(String html) {
        _html = html;
    }

    /**
     * This method sets the input stream that the component will draw it's HTML from.
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
