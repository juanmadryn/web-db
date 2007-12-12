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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlApplet.java $
//$Author: Dan $
//$Revision: 19 $
//$Modtime: 6/11/03 4:39p $
/////////////////////////

import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.util.*;
import java.io.*;
import java.util.*;

/**
 * This component will construct an applet tag.
 */
public class HtmlApplet extends HtmlComponent {
    public static final int SIZE_PERCENT = 0;
    public static final int SIZE_PIXELS = 1;

    public static final String ALIGN_LEFT = "LEFT";
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String ALIGN_TOP = "TOP";
    public static final String ALIGN_ABSMIDDLE = "ABSMIDDLE";
    public static final String ALIGN_ABSBOTTOM = "ABSBOTTOM";
    public static final String ALIGN_TEXTTOP = "TEXTTOP";
    public static final String ALIGN_MIDDLE = "MIDDLE";
    public static final String ALIGN_BASELINE = "BASELINE";
    public static final String ALIGN_BOTTOM = "BOTTOM";

    String _code;
    String _codeBase;
    String _archive;
    String _alt;
    String _align;
    String _object;
    String _title;
    String _width = "-1" ;
    String _height = "-1" ;
    String _hSpace = "-1" ;
    String _vSpace = "-1" ;
    String _mayScript="false";
    Hashtable _parms;
    int _sizeOption = SIZE_PIXELS;
    String _value;
    String _state;
    String _interactWithForm = "false";
    String _usePlugin="false";
    String _pluginClassId = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" ;
    String _pluginCodeBase = "http://java.sun.com/products/plugin/1.3/jinstall-13-win32.cab#Version=1,3,0,0" ;
    String _type="application/x-java-applet;version=1.3";
    String _pluginsPage="http://java.sun.com/products/plugin/1.3/plugin-install.html";
    String _nojavasupport="Browser has No Java Support for Applet.";
    String _appletAttributesDatasource;
    DataStoreBuffer _dsBuff;
    String _visible;


    /**
     * Creates a new HtmlApplet
     * @param name The name of the applet
     * @param className The class name of the applet
     * @param p The page the applet will go in.
     */
    public HtmlApplet(String name, String className, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _code = className;
        p.addApplet(this);
    }

    /**
     * Creates a new HtmlApplet
     * @param name The name of the applet
     * @param className The class name of the applet
     * @param codeBase The directory where the applets resources will be found
     * @param width The width of the applet
     * @param height The height of the applet
     * @param p The page the applet will go in.
     */
    public HtmlApplet(String name, String className, String codeBase, int width, int height, com.salmonllc.html.HtmlPage p) {
        this(name, className, codeBase, p);
        _width = ""+width;
        _height = ""+height;
    }

    /**
     * Creates a new HtmlApplet
     * @param name The name of the applet
     * @param className The class name of the applet
     * @param codeBase The directory where the applets resources will be found
     * @param width The width of the applet
     * @param height The height of the applet
     * @param p The page the applet will go in.
     */
    public HtmlApplet(String name, String className, String codeBase, int width, int height, String jarFile, com.salmonllc.html.HtmlPage p) {
        this(name, className, codeBase, p);
        _width = ""+width;
        _height = ""+height;
        _archive = jarFile; 
        
    }

    /**
     * Creates a new HtmlApplet
     * @param name The name of the applet
     * @param className The class name of the applet
     * @param codeBase The directory where the applets resources will be found
     * @param p The page the applet will go in.
     */
    public HtmlApplet(String name, String className, String codeBase, com.salmonllc.html.HtmlPage p) {
        this(name, className, p);
        _codeBase = codeBase;

    }

    /**
     * This method removes a parameter used by the applet.
     */

    public void clearParm(String name) {
        if (_parms == null)
            return;

        if (name == null)
            return;

        _parms.remove(name);
    }


    public void generateHTML(PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;
        int iBrowserVersion=getPage().getBrowserVersion();
        int iBrowserType=getPage().getBrowserType();
        boolean bUsePlugIn=Boolean.valueOf(evaluateExpression(_usePlugin,rowNo)).booleanValue();

        if (!bUsePlugIn || (bUsePlugIn && (iBrowserType!=HtmlPage.BROWSER_MICROSOFT && !(iBrowserType==HtmlPage.BROWSER_NETSCAPE && iBrowserVersion<6)))) {
            StringBuffer out = new StringBuffer("<APPLET ");
            out.append("CODE=\"" + evaluateExpression(_code,rowNo) + "\" ");

            // CODEBASE
            String sCodebase=evaluateExpression(_codeBase,rowNo);
            if (!Util.isNull(sCodebase) && !Util.isEmpty(sCodebase)) {
                out.append("CODEBASE=\"" + sCodebase + "\" ");
            }

            // ARCHIVE
            String sArchive=evaluateExpression(_archive,rowNo);
            if (!Util.isNull(sArchive) && !Util.isEmpty(sArchive)) {
                out.append("ARCHIVE=\"" + sArchive + "\" ");
            }

            // ALT
            String sAlt=evaluateExpression(_alt,rowNo);
            if (!Util.isNull(sAlt) && !Util.isEmpty(sAlt)) {
                out.append("ALT=\"" + sAlt + "\" ");
            }

            // ALIGN
            String sAlign=evaluateExpression(_align,rowNo);
            if (!Util.isNull(sAlign) && !Util.isEmpty(sAlign)) {
                out.append("ALIGN=\"" + sAlign + "\" ");
            }

            // HEIGHT
            String sHeight=evaluateExpression(_height,rowNo);
            if (!sHeight.equals("-1")) {
                out.append("HEIGHT=\"" + sHeight + (_sizeOption == SIZE_PERCENT && !sHeight.endsWith("%") ? "%" : "") + "\" ");
            }

            // TITLE
            String sTitle=evaluateExpression(_title,rowNo);
            if (!Util.isNull(sTitle) && !Util.isEmpty(sTitle)) {
                out.append("TITLE=\"" + sTitle + "\" ");
            }

            // WIDTH
            String sWidth=evaluateExpression(_width,rowNo);
            if (!sWidth.equals("-1")) {
                out.append("WIDTH=\"" + sWidth + (_sizeOption == SIZE_PERCENT && !sWidth.endsWith("%") ? "%" : "") + "\" ");
            }

            // HSPACE
            String sHSpace=evaluateExpression(_hSpace,rowNo);
            if (!sHSpace.equals("-1")) {
                out.append("HSPACE=\"" + sHSpace + "\" ");
            }

            // VSPACE
            String sVSpace=evaluateExpression(_vSpace,rowNo);
            if (!_vSpace.equals("-1")) {
                out.append("VSPACE=\"" + sVSpace + "\" ");
            }

            String sMayScript=evaluateExpression(_mayScript,rowNo);
            boolean bMayScript=Boolean.valueOf(sMayScript).booleanValue();
            // MAYSCRIPT
            if (bMayScript) {
                out.append("MAYSCRIPT ");
            }

            // NAME
            out.append("NAME=\"" + getFullName(rowNo) + "\">");
            p.println(out.toString());
            p.println("<PARAM NAME=\"servletSessionID\" VALUE=\"" + getPage().getSession().getId() + "\">");
            boolean bInteractWithForm=Boolean.valueOf(evaluateExpression(_interactWithForm,rowNo)).booleanValue();
            if (bInteractWithForm) {
                String sState=evaluateExpression(_state,rowNo);
                if (sState != null && sState.trim().length() > 0) {
                    p.println("<PARAM NAME=\"state\" VALUE=\"" + sState + "\">");
                }
                String sValue=evaluateExpression(_value,rowNo);
                if (sValue != null && sValue.trim().length() > 0) {
                    p.println("<PARAM NAME=\"value\" VALUE=\"" + sValue + "\">");
                }
            }
            if (_parms != null) {
                Enumeration keys = _parms.keys();
                // no need to have in loop
                String key = null;
                while (keys.hasMoreElements()) {
                    key = (String) keys.nextElement();
                    p.println("<PARAM NAME=\"" + key + "\" VALUE=\"" + (String) _parms.get(key) + "\">");
                }
            }
            p.println(_nojavasupport);
            p.println("</APPLET>");
        }
        else {
            if (iBrowserType==HtmlPage.BROWSER_MICROSOFT) {
              StringBuffer out = new StringBuffer("<OBJECT ");
              out.append("classid=\"");
              out.append(evaluateExpression(_pluginClassId,rowNo));
              out.append("\" ");
              out.append("codebase=\"");
              out.append(evaluateExpression(_pluginCodeBase,rowNo));
              out.append("\" ");

                // ALIGN
                String sAlign=evaluateExpression(_align,rowNo);
                if (!Util.isNull(sAlign) && !Util.isEmpty(sAlign)) {
                    out.append("ALIGN=\"" + sAlign + "\" ");
                }

                // HEIGHT
                String sHeight=evaluateExpression(_height,rowNo);
                if (!sHeight.equals("-1")) {
                    out.append("HEIGHT=\"" + sHeight + (_sizeOption == SIZE_PERCENT && !sHeight.endsWith("%") ? "%" : "") + "\" ");
                }

                // TITLE
                String sTitle=evaluateExpression(_title,rowNo);
                if (!Util.isNull(sTitle) && !Util.isEmpty(sTitle)) {
                    out.append("TITLE=\"" + sTitle + "\" ");
                }

                // WIDTH
                String sWidth=evaluateExpression(_width,rowNo);
                if (!sWidth.equals("-1")) {
                    out.append("WIDTH=\"" + sWidth + (_sizeOption == SIZE_PERCENT && !sWidth.endsWith("%") ? "%" : "") + "\" ");
                }

                // HSPACE
                String sHSpace=evaluateExpression(_hSpace,rowNo);
                if (!sHSpace.equals("-1")) {
                    out.append("HSPACE=\"" + sHSpace + "\" ");
                }

                // VSPACE
                String sVSpace=evaluateExpression(_vSpace,rowNo);
                if (!_vSpace.equals("-1")) {
                    out.append("VSPACE=\"" + sVSpace + "\" ");
                }


                // NAME
                out.append("NAME=\"" + getFullName(rowNo) + "\">");
                out.append("\r\n");
                out.append("<PARAM NAME=\"code\" VALUE=\"");
                out.append(evaluateExpression(_code,rowNo));
                out.append("\">\r\n");
                out.append("<PARAM NAME=\"type\" VALUE=\"");
                out.append(evaluateExpression(_type,rowNo));
                out.append("\">\r\n");
                // CODEBASE
                String sCodebase=evaluateExpression(_codeBase,rowNo);
                if (!Util.isNull(sCodebase) && !Util.isEmpty(sCodebase)) {
                    out.append("<PARAM NAME=\"codebase\" VALUE=\"");
                    out.append(sCodebase);
                    out.append("\">\r\n");
                }
                // ARCHIVE
                String sArchive=evaluateExpression(_archive,rowNo);
                if (!Util.isNull(sArchive) && !Util.isEmpty(sArchive)) {
                    out.append("<PARAM NAME=\"archive\" VALUE=\"");
                    out.append(sArchive);
                    out.append("\">\r\n");
                }
                String sMayScript=evaluateExpression(_mayScript,rowNo);
                boolean bMayScript=Boolean.valueOf(sMayScript).booleanValue();
                // MAYSCRIPT
                if (bMayScript) {
                    out.append("<PARAM NAME=\"mayscript\" VALUE=\"true\">\r\n");
                    out.append("<PARAM NAME=\"scriptable\" VALUE=\"true\">\r\n");
                }

                // NAME
                out.append("<PARAM NAME=\"NAME\" VALUE=\"" + getFullName(rowNo) + "\">\r\n");

                p.println(out.toString());
                p.println("<PARAM NAME=\"servletSessionID\" VALUE=\"" + getPage().getSession().getId() + "\">");
                boolean bInteractWithForm=Boolean.valueOf(evaluateExpression(_interactWithForm,rowNo)).booleanValue();
                if (bInteractWithForm) {
                    String sState=evaluateExpression(_state,rowNo);
                    if (sState != null && sState.trim().length() > 0) {
                        p.println("<PARAM NAME=\"state\" VALUE=\"" + sState + "\">");
                    }
                    String sValue=evaluateExpression(_value,rowNo);
                    if (sValue != null && sValue.trim().length() > 0) {
                        p.println("<PARAM NAME=\"value\" VALUE=\"" + sValue + "\">");
                    }
                }
                if (_parms != null) {
                    Enumeration keys = _parms.keys();
                    // no need to have in loop
                    String key = null;
                    while (keys.hasMoreElements()) {
                        key = (String) keys.nextElement();
                        p.println("<PARAM NAME=\"" + key + "\" VALUE=\"" + (String) _parms.get(key) + "\">");
                    }
                }
                p.println(_nojavasupport);
                p.println("</OBJECT>");
            }
            else if (getPage().getBrowserType()==HtmlPage.BROWSER_NETSCAPE) {
                StringBuffer out = new StringBuffer("<EMBED ");

                // ALIGN
                String sAlign=evaluateExpression(_align,rowNo);
                if (!Util.isNull(sAlign) && !Util.isEmpty(sAlign)) {
                    out.append("ALIGN=\"" + sAlign + "\" ");
                }

                // HEIGHT
                String sHeight=evaluateExpression(_height,rowNo);
                if (!sHeight.equals("-1")) {
                    out.append("HEIGHT=\"" + sHeight + (_sizeOption == SIZE_PERCENT && !sHeight.endsWith("%") ? "%" : "") + "\" ");
                }

                // TITLE
                String sTitle=evaluateExpression(_title,rowNo);
                if (!Util.isNull(sTitle) && !Util.isEmpty(sTitle)) {
                    out.append("TITLE=\"" + sTitle + "\" ");
                }

                // WIDTH
                String sWidth=evaluateExpression(_width,rowNo);
                if (!sWidth.equals("-1")) {
                    out.append("WIDTH=\"" + sWidth + (_sizeOption == SIZE_PERCENT && !sWidth.endsWith("%") ? "%" : "") + "\" ");
                }

                // HSPACE
                String sHSpace=evaluateExpression(_hSpace,rowNo);
                if (!sHSpace.equals("-1")) {
                    out.append("HSPACE=\"" + sHSpace + "\" ");
                }

                // VSPACE
                String sVSpace=evaluateExpression(_vSpace,rowNo);
                if (!_vSpace.equals("-1")) {
                    out.append("VSPACE=\"" + sVSpace + "\" ");
                }

                // ALT
                String sAlt=evaluateExpression(_alt,rowNo);
                if (!Util.isNull(sAlt) && !Util.isEmpty(sAlt)) {
                    out.append("ALT=\"" + sAlt + "\" ");
                }




                  // NAME
                  out.append("NAME=\"" + getFullName(rowNo) + "\"");
                  out.append("pluginspage=\"");
                  out.append(evaluateExpression(_pluginsPage,rowNo));
                  out.append("\" ");
                  out.append("code=\"");
                  out.append(evaluateExpression(_code,rowNo));
                  out.append("\" ");
                  out.append("type=\"");
                  out.append(evaluateExpression(_type,rowNo));
                  out.append("\" ");
                  // CODEBASE
                  String sCodebase=evaluateExpression(_codeBase,rowNo);
                  if (!Util.isNull(sCodebase) && !Util.isEmpty(sCodebase)) {
                      out.append("codebase=\"");
                      out.append(sCodebase);
                      out.append("\" ");
                  }
                  // ARCHIVE
                  String sArchive=evaluateExpression(_archive,rowNo);
                  if (!Util.isNull(sArchive) && !Util.isEmpty(sArchive)) {
                      out.append("archive=\"");
                      out.append(sArchive);
                      out.append("\" ");
                  }

                String sMayScript=evaluateExpression(_mayScript,rowNo);
                boolean bMayScript=Boolean.valueOf(sMayScript).booleanValue();
                // MAYSCRIPT
                if (bMayScript) {
                      out.append("mayscript=\"true\" ");
                  }


                  p.println(out.toString());
                  p.println("servletSessionID=\"" + getPage().getSession().getId() + "\" ");
                boolean bInteractWithForm=Boolean.valueOf(evaluateExpression(_interactWithForm,rowNo)).booleanValue();
                if (bInteractWithForm) {
                      String sState=evaluateExpression(_state,rowNo);
                      if (sState != null && sState.trim().length() > 0) {
                          p.println("state=\"" + sState + "\" ");
                      }
                      String sValue=evaluateExpression(_value,rowNo);
                      if (sValue != null && sValue.trim().length() > 0) {
                          p.println("value=\"" + sValue + "\" ");
                      }
                  }
                  if (_parms != null) {
                      Enumeration keys = _parms.keys();
                      // no need to have in loop
                      String key = null;
                      while (keys.hasMoreElements()) {
                          key = (String) keys.nextElement();
                          p.println(key + "=\"" + (String) _parms.get(key) + "\" ");
                      }
                  }
                  p.println("><NOEMBED>");
                  p.println(_nojavasupport);
                  p.println("</NOEMBED>");
                  p.println("</EMBED>");
            }
        }
        boolean bInteractWithForm=Boolean.valueOf(evaluateExpression(_interactWithForm,rowNo)).booleanValue();
        if (bInteractWithForm) {
            p.println("<INPUT TYPE=\"HIDDEN\" name=\"" + getFullName() + "HIDDENVALUE\">");
            p.println("<INPUT TYPE=\"HIDDEN\" name=\"" + getFullName() + "HIDDENSTATE\">");
        }
    }

    /**
     * Gets how the applet will be aligned on the page. Valid Values are ALIGN_LEFT,ALIGN_RIGHT, ALIGN_TOP, ALIGN_ABSMIDDLE, ALIGN_ABSBOTTOM, ALIGN_TEXTTOP, ALIGN_MIDDLE, ALIGN_BASELINE, ALIGN_BOTTOM
     */
    public String getAlign() {
        return _align;
    }


    /**
     * Gets the text to display for browsers that don't support java.
     */
    public String getAltText() {
        return _alt;
    }

    /**
     * Gets the Datasource which has the Applet Attributes
     */
    public String getAppletAttributesDatasource() {
        return _appletAttributesDatasource;
    }


    /**
     * Sets the name for the jar file that contains the applet and related classes.
     */
    public String getArchive() {
        return _archive;
    }

    /**
     * This method gets the name for the applet class.
     */
    public String getClassName() {
        return _code;
    }

    /**
     * Gets the directory containing the applet class file and any resources the applet needs. The value is a URL for an absolute or a relative pathname. An absolute URL is used as is without modification and is not affected by the document's BASE tag. A relative CODEBASE attribute is relative to the document's base URL defined by the BASE tag. If the document does not define a BASE tag, it is relative to the directory containing the HTML file.
     */
    public String getCodeBase() {
        return _codeBase;
    }

    /**
     * Gets the height of the applet
     */
    public int getHeight() {
        return Integer.parseInt(_height);
    }

    /**
     * Gets the horizontal space of the applet
     */
    public int getHSpace() {
        return Integer.parseInt(_hSpace);
    }

    /**
     * If the value is true if the applet will interact with the HTML form that it's in.
     */
    public boolean getInteractWithForm() {
        return Boolean.valueOf(_interactWithForm).booleanValue();
    }

    /**
     * This method gets whether the applet can be accessed via javaScript in the browser.
     */
    public boolean getMayScript() {
        return Boolean.valueOf(_mayScript).booleanValue();
    }

    /**
     * Gets the No Java Support String that is displayed in  browsers which cannot support applets.
     */
    public String getNoJavaSupport() {
        return _nojavasupport;
    }

    /**
     * Gets the object attribute of the applet. It specifies the serialized Java Applet.
     */
    public String getObject() {
        return _object;
    }

    /**
     * This method gets a parameter used by the applet.
     */

    public String getParm(String name) {
        if (_parms == null)
            return null;

        if (name == null)
            return null;

        return (String) _parms.get(name);
    }


    /**
     * Gets the Java Plug-in Class ID. Used by I.E. <Object Tag>
     */
    public String getPluginClassId() {
        return _pluginClassId;
    }

    /**
     * Gets the Java Plug-in Code Base. Used by I.E. <Object Tag>
     */
    public String getPluginCodeBase() {
        return _pluginCodeBase;
    }


    /**
     * Gets the Java Plug-in Download Page. Used by Netscape <Embed Tag>
     */
    public String getPluginsPage() {
        return _pluginsPage;
    }



    /**
     * Gets the SizeOption of the applet. Valid values are SIZE_PIXELS and SIZE_PERCENT
     */
    public int getSizeOption() {
        return _sizeOption;
    }

    /**
     * Gets the state attribute of the applet.
     */
    public String getState() {
        return _state;
    }


    /**
     * Gets the title attribute of the applet.
     */
    public String getTitle() {
        return _title;
    }


    /**
     * Gets the Java Plug-in Applet Type.
     */
    public String getType() {
        return _type;
    }

    /**
     * This method gets whether the applet uses the sun plug-in or not.
     */
    public boolean getUsePlugin() {
        return Boolean.valueOf(_usePlugin).booleanValue();
    }


    /**
     * If the applet can submit a value along with the page this will return it.
     */
    public String getValue() {
        return _value;
    }

    /**
     * This method gets whether the applet is visible or not.
     */
    public boolean getVisible() {
        return Boolean.valueOf(_visible).booleanValue();
    }


    /**
     * Gets the vertical space of the applet
     */
    public int getVSpace() {
        return Integer.parseInt(_vSpace);
    }

    /**
     * gets the width of the applet
     */
    public int getWidth() {
        return Integer.parseInt(_width);
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        String val[] = (String[]) parms.get(getFullName() + "HIDDENVALUE");
        if (val != null)
            _value = val[0];
        else
            _value = null;

        if (_value != null && _value.trim().length() == 0)
            _value = null;

        String state[] = (String[]) parms.get(getFullName() + "HIDDENSTATE");
        if (state != null)
            _state = state[0];
        else
            _state = null;

        if (_state != null && _state.trim().length() == 0)
            _state = null;

        return false;
    }

    /**
     * Sets how the applet will be aligned on the page. Valid Values are ALIGN_LEFT,ALIGN_RIGHT, ALIGN_TOP, ALIGN_ABSMIDDLE, ALIGN_ABSBOTTOM, ALIGN_TEXTTOP, ALIGN_MIDDLE, ALIGN_BASELINE, ALIGN_BOTTOM
     */
    public void setAlign(String align) {
        _align = align;
    }

    /**
     * Sets the text to display for browsers that don't support java.
     */
    public void setAltText(String text) {
        _alt = text;
    }


    /**
     * Sets the DataStore which has the Applet Attributes
     */
    public void setAppletAttributesDatasource(String sDatasource) {
        if (sDatasource==null || sDatasource.trim().equals(""))
         return;
        _appletAttributesDatasource=sDatasource;
        if (getPage() instanceof JspController) {
            _dsBuff = ((JspController)getPage()).getDataSource(_appletAttributesDatasource);
        }

    }


    /**
     * Sets the name for the jar file that contains the applet and related classes.
     */
    public void setArchive(String archive) {
        _archive = archive;
    }

    /**
     * This method sets the name for the applet class.
     */
    public void setClassName(String className) {
        _code = className;
    }


    /**
     * Sets the applet code class.
     */
    public void setCode(String code) {
        _code = code;
    }

    /**
     * Sets the directory containing the applet class file and any resources the applet needs. The value is a URL for an absolute or a relative pathname. An absolute URL is used as is without modification and is not affected by the document's BASE tag. A relative CODEBASE attribute is relative to the document's base URL defined by the BASE tag. If the document does not define a BASE tag, it is relative to the directory containing the HTML file.
     */
    public void setCodeBase(String codeBase) {
        _codeBase = codeBase;
    }

    /**
     * Sets the height of the applet
     */
    public void setHeight(int height) {
        _height = ""+height;
    }

    /**
     * Sets the height of the applet
     */
    public void setHeight(String height) {
        _height = height;
    }


    /**
     * Sets the horizontal space of the applet
     */
    public void setHSpace(int hSpace) {
        _hSpace = ""+hSpace;
    }


    /**
     * Sets the horizontal space of the applet
     */
    public void setHSpace(String hSpace) {
        _hSpace = hSpace;
    }

    /**
     * Set the value to true if you want the applet to interact with the HTML form that it's in. Interaction consists of the applet returning a string value to the page when it is submitted (which can be queried via the getValue() method in this object and the page returning a state string to the applet which tells it how to initialize<BR>The applet itself must have 2 public methods for this to work:<BR>public String getValue() - This will return the value that the applet should return to the page. <BR> public String getState() - This method will return a state string to the page. The page will return this string back to the applet the next time it goes back to the browser as an applet parameter: "state". The applet can then restore it's state using the state string specified. This is so the applet can be made to retain it's state across page submits. In addition, two other parameters will be passed to each applet: "value" contains the value of the applet on the server and "servletSessionID" contains the session id of the session on the server.
     */
    public void setInteractWithForm(boolean interact) {
        _interactWithForm = new Boolean(interact).toString();
    }

    /**
     * Set the value to true if you want the applet to interact with the HTML form that it's in. Interaction consists of the applet returning a string value to the page when it is submitted (which can be queried via the getValue() method in this object and the page returning a state string to the applet which tells it how to initialize<BR>The applet itself must have 2 public methods for this to work:<BR>public String getValue() - This will return the value that the applet should return to the page. <BR> public String getState() - This method will return a state string to the page. The page will return this string back to the applet the next time it goes back to the browser as an applet parameter: "state". The applet can then restore it's state using the state string specified. This is so the applet can be made to retain it's state across page submits. In addition, two other parameters will be passed to each applet: "value" contains the value of the applet on the server and "servletSessionID" contains the session id of the session on the server.
     */
    public void setInteractWithForm(String interact) {
        _interactWithForm = interact;
    }


    /**
     * This method sets whether the applet can be accessed via javaScript in the browser.
     */
    public void setMayScript(boolean mayScript) {
        _mayScript = new Boolean(mayScript).toString();
    }

    /**
     * This method sets whether the applet can be accessed via javaScript in the browser.
     */
    public void setMayScript(String mayScript) {
        _mayScript = mayScript;
    }


    /**
     * Sets the No Java Support String that is displayed in  browsers which cannot support applets.
     */
    public void setNoJavaSupport(String sNoJavaSupport) {
        _nojavasupport=sNoJavaSupport;
    }

    /**
     * Sets the object attribute of the applet. It specifies the serialized Java Applet.
     */
    public void setObject(String sObject) {
        _object=sObject;
    }

    /**
     * This method sets a parameter used by the applet. Name and value cannot be null.
     */
    public void setParm(String name, String value) {
        if (_parms == null)
            _parms = new Hashtable();

        if (name == null || value == null)
            return;

        _parms.put(name, value);
    }


    /**
     * Sets the Java Plug-in Class ID. Used by I.E. <Object Tag>
     */
    public void setPluginClassId(String sPluginClassId) {
        _pluginClassId=sPluginClassId;
    }

    /**
     * Sets the Java Plug-in Code Base. Used by I.E. <Object Tag>
     */
    public void setPluginCodeBase(String sPluginCodeBase) {
        _pluginCodeBase=sPluginCodeBase;
    }


    /**
     * Sets the Java Plug-in Download Page. Used by Netscape <Embed Tag>
     */
    public void setPluginsPage(String sPluginPage) {
        _pluginsPage=sPluginPage;
    }



    /**
     * Sets the SizeOption of the applet. Valid values are SIZE_PIXELS and SIZE_PERCENT
     */
    public void setSizeOption(int sizeOption) {
        _sizeOption = sizeOption;
    }


    /**
     * Sets the state attribute of the applet.
     */
    public void setState(String sState) {
        _state=sState;
    }


    /**
     * Sets the title attribute of the applet.
     */
    public void setTitle(String sTitle) {
        _title=sTitle;
    }


    /**
     * Sets the Java Plug-in Applet Type.
     */
    public void setType(String sType) {
        _type=sType;
    }

    /**
     * This method sets whether the applet uses the sun plug-in or not.
     */
    public void setUsePlugin(boolean bUsePlugIn) {
        _usePlugin=new Boolean(bUsePlugIn).toString();
    }

    /**
     * This method sets whether the applet uses the sun plug-in or not.
     */
    public void setUsePlugin(String sUsePlugIn) {
        _usePlugin=sUsePlugIn;
    }



    /**
     * This method sets whether the applet is visible or not.
     */
    public void setVisible(boolean bVisible) {
        _visible=new Boolean(bVisible).toString();
    }

    /**
     * This method sets whether the applet is visible or not.
     */
    public void setVisible(String sVisible) {
        _visible=sVisible;
    }




    /**
     * If the applet can display a value this will pass it .
     */
    public void setValue(String value) {
        _value = value;
    }

    /**
     * Sets the vertical space of the applet
     */
    public void setVSpace(int vSpace) {
        _vSpace = ""+vSpace;
    }


    /**
     * Sets the vertical space of the applet
     */
    public void setVSpace(String vSpace) {
        _vSpace = vSpace;
    }


    /**
     * Sets the width of the applet
     */
    public void setWidth(int width) {
        _width = ""+width;
    }

    /**
     * Sets the width of the applet
     */
    public void setWidth(String width) {
        _width = width;
    }


    private String evaluateExpression(String sExpression,int iRowNo) {
      if (sExpression==null)
        return null;
      if (_dsBuff!=null) {
          if (sExpression.startsWith(_appletAttributesDatasource+":")) {
            try {
                DataStoreEvaluator dsEval=new DataStoreEvaluator(_dsBuff,sExpression.substring(_appletAttributesDatasource.length()+1));
                Object o=null;
                if (iRowNo==-1)
                  o=dsEval.evaluateRow();
                else
                  o=dsEval.evaluateRow(iRowNo);
                return o!=null?o.toString().trim():null;
            }
            catch (Exception e) {
                MessageLog.writeErrorMessage("Unable to evaluate Expression "+sExpression,e,this);
            }
          }
      }
      return sExpression;
    }

    /**
     * Adds Applet Parameters based on a table with an <BR> integer primary key column (typically an id) and a name and value column.<BR> A simplifying assumption is that each of the following <BR> is the same: <BR> - name of column in the main table which refers to the simple table <BR> - name of integer column in simple table <BR>
     *
     * @param table - name of table to look up names and values from
     * @param nameColumn - column to get name values from
     * @param valueColumn - column to get value values from
     * @param criteria - optional selection criteria
     */
    public void addParameters(String table, String nameColumn, String valueColumn, String criteria)
    {
        DBConnection connection = null;

        try
        {
            connection = DBConnection.getConnection(getPage().getApplicationName());

            java.sql.Statement s     = connection.createStatement();
            String             query = null;

            if (criteria != null)
            {
                query = "SELECT " + nameColumn + "," + valueColumn + " FROM " + table + " WHERE " + criteria + " ORDER BY ";

                query += nameColumn;
            }
            else
            {
                query = "SELECT " + nameColumn + "," + valueColumn + " FROM " + table + " ORDER BY ";

                query += nameColumn;
            }

            java.sql.ResultSet r = s.executeQuery(query);

            if (r.next())
            {
                do
                {
                        setParm(r.getObject(1).toString(), r.getObject(2).toString());
                }
                while (r.next());
            }

            r.close();
            s.close();
        }
        catch (java.sql.SQLException se)
        {
            MessageLog.writeErrorMessage("addParameters", se, null);
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("addParameters", e, this);
        }
        finally
        {
            if (connection != null)
            {
                connection.freeConnection();
            }
        }
    }

    public String getFullName() {
        return getFullName(-1);
    }

    public String getFullName(int iRowNo) {
        if (_dsBuff!=null && getName().startsWith(_appletAttributesDatasource+":"))
          return evaluateExpression(getName(),iRowNo);
        if (iRowNo==-1)
          return super.getFullName();
        return super.getFullName()+"_"+iRowNo;
    }

}
