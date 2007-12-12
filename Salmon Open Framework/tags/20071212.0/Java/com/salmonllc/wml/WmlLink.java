
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
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlLink.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 6/11/03 4:52p $
/////////////////////////

import com.salmonllc.jsp.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;

/**
 * This container will construct an anchor tag with a Wml link (HREF) reference.
 */
public class WmlLink extends JspContainer {
    private String _href;
    private DataStoreEvaluator _dsEval;
    private String _class;
    private boolean _generateLink = true;
    private boolean _genBracket = false;
    private String _bracketFont;
    private String _title;

    /**
     * WmlLink constructor comment.
     * @param name The name of the link
     * @param href The url for the link
     * @param p The page the link will go in.
     */

    public WmlLink(String name, String href, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _href = href;
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
    public void generateHTML(TagWriter t, String box, int rowNo) throws java.io.IOException {
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
   	    String bracketStartFont ="";
   	    String bracketEndFont ="";
        if (rowNo != -1)
            row = "_" + row + new Integer(rowNo).toString();
        if (_genBracket)
         {
      	   com.salmonllc.properties.Props props = getPage().getPageProperties();
	       if (_bracketFont != null)
	        {
            	   bracketStartFont = props.getProperty(_bracketFont + Props.TAG_START);
            	   bracketEndFont = props.getProperty(_bracketFont + Props.TAG_END);
	        }
	       else
	        {
            	   bracketStartFont = props.getProperty(com.salmonllc.html.HtmlText.FONT_DEFAULT + Props.TAG_START);
            	   bracketEndFont = props.getProperty(com.salmonllc.html.HtmlText.FONT_DEFAULT + Props.TAG_END);
	        }

        	sb.append(bracketStartFont + " [" + bracketEndFont);
         }
        sb.append("<a");
        sb.append(" id=\"" + getName() + row + "\"");
        sb.append(" href=\"" + encodeURL(href) + "\"");
        if (_title != null)
           sb.append(" title=\"" + _title + "\"");
        if (_class != null)
            sb.append(" class=\"" + _class + "\"");
        sb.append(">");

        if (_generateLink)
            t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        t.print(box, TagWriter.TYPE_CONTENT);
        if (_generateLink)
         {
            t.print("</a>", TagWriter.TYPE_END_TAG);
	        if (_genBracket)
 	       		t.print(bracketStartFont + "]" + bracketEndFont);
         }

    }
    /**
     * This method gets whether or not the anchor tag will be generated for the link. Pass true to have the a tag generated and false not to. Setting the value to false, will cause the items inside the container to be displayed but nothing will happen when the user clicks on them.
     */
    public boolean getGenerateLink() {
        return _generateLink;
    }
    /**
     * Returns the href for the link
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
     * Use this method to get the style that will be used to display the link
     */
    public String getClassName() {
        return _class;
    }
    /**
     * Use this method to get the title for the link
     */
    public String getTitle() {
        return _title;
    }
    /**
     * This method sets whether or not the anchor tag will be generated with brackets([]) around the Tag. Pass true to have the tag generated with and false not to.
     */
    public void setBracket(boolean bracket) {
        _genBracket = bracket;
    }
    /**
     * This method sets the font for the brackets around the link.
     */
    public void setBracketFont(String font) {
        _bracketFont = font;
    }
    /**
     * This method sets whether or not the anchor tag will be generated for the link. Pass true to have the a tag generated and false not to. Setting the value to false, will cause the items inside the container to be displayed but nothing will happen when the user clicks on them.
     */
    public void setGenerateLink(boolean gen) {
        _generateLink = gen;
    }
    /**
     * Sets the href for the link
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
     * This method sets the class for the link.
     */
    public void setClassName(String className) {
        _class = className;
    }
    /**
     * This method sets the title for the link.
     */
    public void setTitle(String title) {
        _title = title;
    }
}
