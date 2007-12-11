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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlImage.java $
//$Author: Srufle $
//$Revision: 6 $
//$Modtime: 4/15/03 2:25p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;
import com.salmonllc.util.Util;
import java.io.PrintWriter;

/**
 * This type can be used to add an image to your page.
 */
public class WmlImage extends HtmlComponent {
    public static final String ALIGN_BOTTOM = "BOTTOM";
    public static final String ALIGN_MIDDLE = "MIDDLE";
    public static final String ALIGN_TOP = "TOP";
    public static final String ALIGN_NONE = "";
    //
    public static final int SIZE_PIXELS = 0;
    public static final int SIZE_PERCENT = 1;
    //
    private String _source = "";
    private String _localsrc = "";
    private String _alt = "";
    private String _align = ALIGN_NONE;
    private String _class;
    //
    private int _width = -1;
    private int _height = -1;
    private int _hSpace = -1;
    private int _vSpace = -1;
    //
    private int _widthSizeOption = SIZE_PIXELS;
    private int _heightSizeOption = SIZE_PIXELS;
    //


    private DataStoreEvaluator _dsEval = null;
    private DataStoreEvaluator _altEval = null;

    /**
     * Constructs an new Image
     * @param source - image that is being used.
     * @param p The page the image will be placed in
     */
    public WmlImage(String source, HtmlPage p) {
        this("", source, p);
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
        if (index != -1) {
          setExpression(ds, ds.getColumnName(index));
            index = ds.getColumnIndex(getName()+"ALT");
            if (index > -1)
               setAltExpression(ds, ds.getColumnName(index));
        }
    }


    /**
     * Constructs an image object for the page
     */
    public WmlImage(String name, String source, HtmlPage p) {
        super(name, p);
        _source = source;
    }




    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(PrintWriter p, int rowNo) throws Exception {
        if (!getVisible()) {
            return;
        }

        if (_dsEval != null) {
            if (rowNo > -1)
                _source = _dsEval.evaluateRowFormat(rowNo);
            else
                _source = _dsEval.evaluateRowFormat();

        }

        String row = "";
        if (rowNo != -1)
            row = "_" + rowNo;

        String source = _source;

        //
        StringBuffer out = new StringBuffer("<img");

        if (!Util.isNull(source) && !Util.isEmpty(source))
          out.append(" src=\"" + source + "\"");

        // NAME
        if (!Util.isNull(getName()) && !Util.isEmpty(getName())) {
            out.append(" id=\"" + getName() + row +"\"");
        }
        // ALIGN
        if (!Util.isNull(_class) && !Util.isEmpty(_class)) {
            out.append(" class=\"" + _class + "\"");
        }

        // ALT
        String alt = _alt;
        if (_altEval != null) {
            if (rowNo > -1)
                alt = _altEval.evaluateRowFormat(rowNo);
            else
                alt = _altEval.evaluateRowFormat();
        }
        if (!Util.isNull(alt) && !Util.isEmpty(alt)) {
            out.append(" alt=\"" + alt + "\"");
        }

        // ALIGN
        if (!Util.isNull(_align) && !Util.isEmpty(_align)) {
            out.append(" align=\"" + _align + "\"");
        }




        // WIDTH
        if (_width > -1) {
            out.append(" width=\"" + _width + (_widthSizeOption == SIZE_PERCENT ? "%" : "") + "\"");
        }

        // HEIGHT
        if (_height > -1) {
            out.append(" height=\"" + _height + (_heightSizeOption == SIZE_PERCENT ? "%" : "") + "\"");
        }

        // HSPACE
        if (_hSpace > -1) {
            out.append(" hspace=\"" + _hSpace + "\"");
        }
        // VSPACE
        if (_vSpace > -1) {
            out.append(" vspace=\"" + _vSpace + "\"");
        }

        //
        out.append("/>");
        p.print(out);
    }





    /**
     * Returns the alignment property for the image.
     */
    public String getAlign() {
        return _align;
    }

    /**
     * Returns the alt for the image
     */
    public String getAlt() {
        return _alt;
    }


    /**
     * This method returns the localsrc of the image.
     */
    public String getLocalSrc() {
        return _localsrc;
    }







    /**
     * This method returns the height of the image.
     */
    public int getHeight() {
        return _height;
    }

    /**
     * This method returns SIZE_PIXELS or SIZE_PERCENT.
     */
    public int getHeightSizeOption() {
        return _heightSizeOption;
    }

    /**
     * This method returns the horizontal space around the the image.
     */
    public int getHorizontalSpace() {
        return _hSpace;
    }

    /**
     * This method gets the source url for the image.
     */
    public String getSource() {
        return _source;
    }






    /**
     * This method returns the vertical space around the the image.
     */
    public int getVerticalSpace() {
        return _vSpace;
    }

    /**
     * This method returns the width of the image.
     */
    public int getWidth() {
        return _width;
    }

    /**
     * This method returns SIZE_PIXELS or SIZE_PERCENT.
     */
    public int getWidthSizeOption() {
        return _widthSizeOption;
    }

    /**
     * Use this method to get the class for the image tag
     */
    public String getClassName() {
        return _class;
    }


    /**
     * Sets the alignment property for the image.
     */

    public void setAlign(String align) {
        _align = align;
    }

    /**
     * This method sets the alt for the image
     */
    public void setAlt(String alt) {
        _alt = alt;
    }

    /**
     * Use this method to bind the alt property to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setAltExpression(DataStoreBuffer ds, String expression) throws Exception {
        _altEval = new DataStoreEvaluator(ds, expression);
    }


    /**
     * This method sets the localsrc of the image.
     */
    public void setLocalSrc(String localsrc) {
        _localsrc = localsrc;
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
     * This method sets the height of the image.
     */
    public void setHeight(int height) {
        _height = height;
    }

    /**
     * Valid values for this method are SIZE_PIXELS or SIZE_PERCENT.
     */
    public void setHeightSizeOption(int option) {
        _heightSizeOption = option;
    }

    /**
     * This method sets the Horizontal space around the image in pixels.
     */
    public void setHorizontalSpace(int width) {
        _hSpace = width;
    }


    /**
     * This method sets the source url for the image.
     */
    public void setSource(String source) {
        _source = source;
    }




    /**
     * This method sets the Vertical space around the image in pixels.
     */
    public void setVerticalSpace(int width) {
        _vSpace = width;
    }

    /**
     * This method sets the width of the image.
     */
    public void setWidth(int width) {
        _width = width;
    }

    /**
     * Valid values for this method are SIZE_PIXELS or SIZE_PERCENT.
     */
    public void setWidthSizeOption(int option) {
        _widthSizeOption = option;
    }

    /**
     * This method gets the DataStoreEvaluator being used for expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getExpression() {
        return _dsEval;
    }

    /**
     * This method gets the DataStoreEvaluator being used for alt expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getAltExpression() {
        return _altEval;
    }

    /**
     * This method sets the class for the link.
     */
    public void setClassName(String className) {
        _class = className;
    }

}
