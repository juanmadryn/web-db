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


import com.salmonllc.html.events.*;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;
import java.util.*;

/**
 * This component is used to transform an HtmlImage object into an Html Image Map. An image map is like an image except that clicking or a particular area in the map will cause a link to be followed or a submit to be executed.
 * @see  HtmlImage
 */

public class HtmlImageMapArea extends HtmlComponent {
    public static final int AREA_CIRCLE = 0;
    public static final int AREA_RECTANGLE = 1;

    private int _shape = 0;
    private HtmlImage _img;
    private int _x1;
    private int _y1;
    private int _x2;
    private int _y2;
    private int _radius;
    private String _href;
    private String _target;
    private String _onMouseOver;
    private String _onMouseOut;

    private Vector _listeners;
    private DataStoreEvaluator _dsEval;
    private boolean _clicked = false;
    private int _rowNo = 0;
    private boolean _submitAlreadyGenerated=false;

    /**
     * Use this constructor to create a circular shaped image map area.
     * @param name The name of the component
     * @param centerX The x position of the center of the area
     * @param centerY The y position of the center of the area
     * @param radius Radius of the circle
     */
    public HtmlImageMapArea(String name, int centerX, int centerY, int radius) {
        super(name, null);
        _x1 = centerX;
        _y1 = centerY;
        _radius = radius;
        _shape = AREA_RECTANGLE;
    }

    /**
     * Use this constructor to create a rectangular shaped image map area.
     * @param name The name of the component
     * @param x1 Top left corner x position of the area
     * @param y1 Top left corner y position of the area
     * @param x2 Bottom right corner x position of the area
     * @param y2 Bottom right corner y position of the area
     */
    public HtmlImageMapArea(String name, int x1, int y1, int x2, int y2) {
        super(name, null);
        _x1 = x1;
        _y1 = y1;
        _y2 = y2;
        _x2 = x2;
        _shape = AREA_RECTANGLE;
    }

    /**
     * Use this constructor to create a rectangular shaped image map area.
     * @param name The name of the component
     * @param x1 Top left corner x position of the area
     * @param y1 Top left corner y position of the area
     * @param x2 Bottom right corner x position of the area
     * @param y2 Bottom right corner y position of the area
     * @param href The url of the page to open
     */
    public HtmlImageMapArea(String name, int x1, int y1, int x2, int y2, String href) {
        this(name, x1, y1, x2, y2);
        _href = href;
    }

    /**
     * Use this constructor to create a circular shaped image map area.
     * @param name The name of the component
     * @param centerX The x position of the center of the area
     * @param centerY The y position of the center of the area
     * @param radius Radius of the circle
     * @param href The url of the page to open
     */
    public HtmlImageMapArea(String name, int centerX, int centerY, int radius, String href) {
        this(name, centerX, centerY, radius);
        _href = href;
    }

    /**
     * This method will clear all pending events from the event queue for this component and components it contains.
     */
    public void reset() {
        super.reset();
        _submitAlreadyGenerated=false;
    }


    /**
     * This method adds a listener the will be notified when this button causes the page to be submitted.
     * @param l The listener to add.
     */
    public void addSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector();

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }

        _listeners.addElement(l);
    }

    public boolean executeEvent(int eventType) throws Exception {
        if (eventType == EVENT_SUBMIT && _clicked) {
            if (_listeners != null && _listeners.size() > 0) {
                SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);
                for (int i = 0; i < _listeners.size(); i++) {
                    SubmitListener l = (SubmitListener) _listeners.elementAt(i);
                    e.setNextListener( _listeners.size() > (i + 1) ?_listeners.elementAt(i + 1) : null);
                    if (!l.submitPerformed(e))
                        return false;
                }
            }
        }
        return true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        StringBuffer href = new StringBuffer("");
        StringBuffer out = new StringBuffer();

        if (getPage() instanceof JspController && !_submitAlreadyGenerated)
          generateInitialHTML(p);
        if (_listeners != null && _listeners.size() > 0) {
            href.append("javascript:" + getFullName() + "_submit(" + rowNo + ");");
        } else {
            if (_dsEval != null)
                if (rowNo > -1)
                    _href = _dsEval.evaluateRowFormat(rowNo);
                else
                    _href = _dsEval.evaluateRowFormat();

            if (_href != null) {
                href = new StringBuffer(_href);
                for (int i = 0; i < href.length(); i++)
                    if (href.charAt(i) == ' ')
                        href.setCharAt(i, '+');
            }
        }

        out.append("<AREA ");
        if (_shape == AREA_RECTANGLE) {
            out.append("COORDS=\"" + _x1 + "," + _y1 + "," + _x2 + "," + _y2 + "\" ");
            out.append("SHAPE=\"RECT\" ");
        } else {
            out.append("COORDS=\"" + _x1 + "," + _y1 + "," + _radius + "\" ");
            out.append("SHAPE=\"CIRCLE\" ");
        }

        if (href.length() == 0)
            out.append("NOHREF ");
        else
            out.append("HREF=\"" + href + "\" ");

        if (_target != null)
            out.append("TARGET=\"" + _target + "\" ");

        if (_onMouseOver != null)
            out.append("ONMOUSEOVER=\"" + _onMouseOver + "\" ");

        if (_onMouseOut != null)
            out.append("ONMOUSEOUT=\"" + _onMouseOut + "\" ");

        out.append("NAME=\"" + getFullName() + "\">");

        p.print(out.toString());
    }

    public void generateInitialHTML(java.io.PrintWriter p) throws Exception {
        if (_listeners != null && _listeners.size() > 0) {
            p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "__HIDDEN_FIELD\" VALUE=\"\">");
            getPage().writeScript(getFormString() + getFullName() + "__HIDDEN_FIELD.value = '';");

            p.println("<SCRIPT>");
            p.println("function " + getFullName() + "_submit(row) {");
            p.println(getFormString() + getFullName() + "__HIDDEN_FIELD.value = row;");
            p.println(getFormString() + "submit();");
            p.println("}");
            p.println("</SCRIPT>");
            _submitAlreadyGenerated=true;
        }

    }

    /**
     * This gets the Href for the area. The link will be followed when the user clicks on the area.
     */
    public String getHref() {
        return _href;
    }

    /**
     * This method gets the javascript that will be executed when the mouse leaves the area.
     */
    public String getOnMouseOut() {
        return _onMouseOut;
    }

    /**
     * This method gets the javascript that will be executed when the mouse passes over the area.
     */
    public String getOnMouseOver() {
        return _onMouseOver;
    }

    /**
     * This gets the target for the area. It specifies which frame will be used for the linked page.
     */
    public String getTarget() {
        return _target;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        String name = getFullName() + "__HIDDEN_FIELD";
        String[] p = (String[]) parms.get(name);

        if (p != null && p[0].trim().length() > 0) {
            _rowNo = Integer.parseInt(p[0].trim());
            _clicked = true;
            return true;
        }

        return false;

    }

    /**
     * This method removes a listener from the list that will be notified if this button causes the page to be submitted.
     * @param l The listener to remove.
     */
    public void removeSubmitListener(SubmitListener l) {
        if (_listeners == null)
            return;

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l) {
                _listeners.removeElementAt(i);
                return;
            }
        }
    }

    /**
     * Sets the coordinates of a circular area of the image
     * @param centerX The x position of the center of the area
     * @param centerY The y position of the center of the area
     * @param radius Radius of the circle
     */
    public void setCircularArea(int centerX, int centerY, int radius) {
        _x1 = centerX;
        _y1 = centerY;
        _radius = radius;
        _shape = AREA_CIRCLE;
    }

    /**
     * This sets the Href for the area. The link will be followed when the user clicks on the area.
     */
    public void setHref(String hRef) {
        _href = hRef;
        ;
    }

    /**
     * This method creates an Href on the fly from the expression.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     * @exception java.lang.Exception The exception description.
     */
    public void setHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        _dsEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Sets the image that this map will use
     * @param img com.salmonllc.html.HtmlImage
     */
    public void setImage(HtmlImage img) {
        _img = img;
        setParent(_img);
        setPage(_img.getPage());
    }

    /**
     * This method sets the javascript that will be executed when the mouse leaves the area.
     */
    public void setOnMouseOut(String javaScript) {
        _onMouseOut = javaScript;
    }

    /**
     * This method sets the javascript that will be executed when the mouse passes over the area.
     */
    public void setOnMouseOver(String javaScript) {
        _onMouseOver = javaScript;
    }

    /**
     * Sets the coordinates of a rectangular area of the image
     * @param x1 Top left corner x position of the area
     * @param y1 Top left corner y position of the area
     * @param x2 Bottom right corner x position of the area
     * @param y2 Bottom right corner y position of the area
     */
    public void setRectangularArea(int x1, int y1, int x2, int y2) {
        _x1 = x1;
        _y1 = y1;
        _x2 = x2;
        _y2 = y2;
        _shape = AREA_RECTANGLE;
    }

    /**
     * This sets the target for the area. It specifies which frame will be used for the linked page.
     */
    public void setTarget(String target) {
        _target = target;
    }
}
