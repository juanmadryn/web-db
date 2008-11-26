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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlLink.java $
//$Author: Dan $
//$Revision: 45 $
//$Modtime: 9/24/04 12:19p $
/////////////////////////

import com.salmonllc.html.events.*;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This container will construct an anchor tag with a html link (HREF) reference.
 */
public class HtmlLink extends HtmlContainer {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4345409243987602470L;
	private String _href;
    private String _onClick;
    private String _onMouseOut;
    private String _onMouseOver;
    private String _target;
    private DataStoreEvaluator _dsEval;
    private boolean _doSubmit = false;
    private String _zoomTo = null;
    private Vector<SubmitListener> _listeners;
    private int _rowNo = -1;
    private HtmlStyle _style;
    private boolean _generateLink = true;
    private boolean _submitAlreadyGenerated = false;
	private Integer _tabIndex;
	private String _accessKey;

    /**
     * HtmlLink constructor.
     * @param name The name of the link
     * @param href The url for the link
     * @param p The page the link will go in.
     */

    public HtmlLink(String name, String href, com.salmonllc.html.HtmlPage p) {
        this(name, href, null, p);
    }

    /**
     * HtmlLink constructor comment.
     * @param name The name of the link
     * @param href The url for the link
     * @param target The target for the link
     * @param p The page the link will go in.
     */
    public HtmlLink(String name, String href, String target, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _href = href;
        _target = target;
    }


    /**
     * This method will clear all pending events from the event queue for this component and components it contains.
     */
    public void reset() {
        super.reset();
        _submitAlreadyGenerated = false;
    }

    /**
     * This method adds a listener the will be notified when this button causes the page to be submitted.
     * @param l The listener to add.
     */
    public void addSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector<SubmitListener>();
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }
        _listeners.addElement(l);
        setDoSubmit(true);
    }

    /**
     * This method inserts a listener the will be notified when this button causes the page to be submitted.
     * @param l The listener to add.
     */
    public void insertSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector<SubmitListener>();
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }
        _listeners.insertElementAt(l, 0);
        setDoSubmit(true);
    }

    public boolean executeEvent(int eventType) throws Exception {
        if (eventType == EVENT_SUBMIT && _zoomTo != null) {
            // check listeners
            // if  any have been registered

            if (_listeners != null && _listeners.size() > 0) {
                SubmitEvent e =
                        new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);
                SubmitListener l = null;
                int listenersSize = _listeners.size();
                for (int i = 0; i < listenersSize; i++) {
                    l = (SubmitListener) _listeners.elementAt(i);
                    e.setNextListener( _listeners.size() > (i + 1) ?_listeners.elementAt(i + 1) : null);
                    if (!l.submitPerformed(e))
                        return false;
                }
                return true;
            } else {
                getPage().getCurrentResponse().sendRedirect(_zoomTo);
                _zoomTo = null;
            }
        } else if (!super.executeEvent(eventType)) {
            return false;
        }
        return true;
    }

    public void generateHTML(PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;
        if (!_generateLink) {
            super.generateHTML(p, rowNo);
            return;
        }
        if (getPage() instanceof JspController && !_submitAlreadyGenerated)
            generateInitialHTML(p);
        _zoomTo = null;
        StringBuffer href = null;
        if (_doSubmit) {
            href = new StringBuffer("javascript:" + getFullName() + "_submit('" + rowNo + "');");
        } else {
            if (_dsEval != null) {
                if (rowNo > -1) {
                    _href = _dsEval.evaluateRowFormat(rowNo);
                } else {
                    _href = _dsEval.evaluateRowFormat();
                }
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_href)) {
                href = new StringBuffer(_href);
                if (!_href.startsWith("javascript:")) {
                	int hrefLength = href.length();
                	for (int i = 0; i < hrefLength; i++) {
                		if (href.charAt(i) == ' ') {
                			href.setCharAt(i, '+');
                		}
                	}
                }	
            }
        }
        String row = "";
        if (rowNo != -1)
            row = "_" + row + new Integer(rowNo).toString();
        StringBuffer sbOut=new StringBuffer();

		String onClick = _onClick;
		String valScript = HtmlValidatorText.generateOnClickJavaScriptForButtons(_onClick,_listeners,getFullName());
		if (valScript != null) {
			sbOut.append(valScript);
			onClick = "return " + HtmlValidatorText.generateOnClickJavaScriptFunctionName(getFullName()) + ";";
		}
        
        sbOut.append("<A NAME=\"");
        sbOut.append(getFullName());
        sbOut.append(row);
        sbOut.append("\" ID=\"");
        sbOut.append(getFullName());
        sbOut.append(row);
        sbOut.append("\" HREF=\"");
        sbOut.append(encodeURL(href));
        sbOut.append('"');
//        String out = "<A NAME=\"" + getFullName() + row + "\" HREF=\"" + encodeURL(href) + "\"";
        if (_target != null) {
            sbOut.append(" TARGET=\"");
            sbOut.append(_target);
            sbOut.append('"');
//            out += " TARGET=\"" + _target + "\"";
        }
        if (onClick != null) {
            sbOut.append(" ONCLICK=\"");
            sbOut.append(onClick);
            sbOut.append('"');
//            out += " ONCLICK=\"" + onClick + "\"";
        }
        if (_onMouseOver != null) {
            sbOut.append(" ONMOUSEOVER=\"");
            sbOut.append(_onMouseOver);
            sbOut.append('"');
//            out += " ONMOUSEOVER=\"" + _onMouseOver + "\"";
        }
        if (_onMouseOut != null) {
            sbOut.append(" ONMOUSEOUT=\"");
            sbOut.append(_onMouseOut);
            sbOut.append('"');
//            out += " ONMOUSEOUT=\"" + _onMouseOut + "\"";
        }
        
        boolean styleAdded = false; 
		if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
			sbOut.append(" CLASS=\"");
			sbOut.append(_style.getStyleName());
			sbOut.append('"');
			styleAdded = true;
//			  out += " CLASS=\"" + _style.getStyleName() + "\"";
		}
		
		String classname = getClassName();
		if (Util.isFilled(classname) && !styleAdded) {
			sbOut.append(" CLASS=\"");
			sbOut.append(classname);
			sbOut.append('"');
//			  out += " CLASS=\"" + _style.getStyleName() + "\"";
		}
        
		if (_tabIndex != null) { 
			sbOut.append(" tabindex=\"");
			sbOut.append(_tabIndex.toString());
			sbOut.append("\"");
		}	
		if (_accessKey != null) {
			sbOut.append(" accesskey=\"");
			sbOut.append(_accessKey);
			sbOut.append("\"");
		}	
		
        sbOut.append('>');
//        out += ">";
        p.print(sbOut.toString());
        super.generateHTML(p, rowNo);
        p.print("</A>");
    }

    public void generateHTML(PrintWriter p, int rowStart, int rowEnd) throws Exception {
        if (!_visible)
            return;
        if (!_generateLink) {
            super.generateHTML(p, rowStart, rowEnd);
            return;
        }

        if (getPage() instanceof JspController && !_submitAlreadyGenerated)
            generateInitialHTML(p);
        _zoomTo = null;
        StringBuffer href = null;
        if (_doSubmit)
            href = new StringBuffer("javascript:" + getFullName() + "_submit('" + rowStart + "');");
        else {
            if (_dsEval != null)
                if (rowStart > -1)
                    _href = _dsEval.evaluateRowFormat(rowStart);
                else
                    _href = _dsEval.evaluateRowFormat();

            // ==========================================================================
            // BYD - 04/18/01 (If any question ask Fred or Bruno)
            // --------------
            // Correction introduced to fix NullPointerException when HtmlLink is used in
            // a ControlBreakFooterComponent. Can probably be applied to other components
            // visible even when the datastore is empty
            if (_href == null)
                return;
            // ==========================================================================

            href = new StringBuffer(_href);
            if (!_href.startsWith("javascript:")) {
            	int hrefLength = href.length();
            	for (int i = 0; i < hrefLength; i++)
            		if (href.charAt(i) == ' ')
            			href.setCharAt(i, '+');
            }    
        }
        String row = "";
        if (rowStart != -1)
            row = "_" + row + new Integer(rowStart).toString();
        StringBuffer sbOut=new StringBuffer();
        sbOut.append("<A NAME=\"");
        sbOut.append(getFullName());
        sbOut.append(row);
        sbOut.append("\" HREF=\"");
        sbOut.append(encodeURL(href));
        sbOut.append('"');
//        String out = "<A NAME=\"" + getFullName() + row + "\" HREF=\"" + encodeURL(href) + "\"";
        if (_target != null) {
            sbOut.append(" TARGET=\"");
            sbOut.append(_target);
            sbOut.append('"');
//            out += " TARGET=\"" + _target + "\"";
        }
        if (_onClick != null) {
            sbOut.append(" ONCLICK=\"");
            sbOut.append(_onClick);
            sbOut.append('"');
//            out += " ONCLICK=\"" + _onClick + "\"";
        }
        if (_onMouseOver != null) {
            sbOut.append(" ONMOUSEOVER=\"");
            sbOut.append(_onMouseOver);
            sbOut.append('"');
//            out += " ONMOUSEOVER=\"" + _onMouseOver + "\"";
        }
        if (_onMouseOut != null) {
            sbOut.append(" ONMOUSEOUT=\"");
            sbOut.append(_onMouseOut);
            sbOut.append('"');
//            out += " ONMOUSEOUT=\"" + _onMouseOut + "\"";
        }
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            sbOut.append(" CLASS=\"");
            sbOut.append(_style.getStyleName());
            sbOut.append('"');
//            out += " CLASS=\"" + _style.getStyleName() + "\"";
        }
        
		if (_tabIndex != null) { 
			sbOut.append(" tabindex=\"");
			sbOut.append(_tabIndex.toString());
			sbOut.append("\"");
		}	
		if (_accessKey != null) {
			sbOut.append(" accesskey=\"");
			sbOut.append(_accessKey);
			sbOut.append("\"");
		}	
        sbOut.append('>');
//        out += ">";
        p.print(sbOut.toString());
        super.generateHTML(p, rowStart, rowEnd);
        p.print("</A>");
    }

    public void generateInitialHTML(PrintWriter p) throws Exception {
        if (_visible && _doSubmit) {
            p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "_HIDDEN\" VALUE=\"\">");
            getPage().writeScript(getFormString() + getFullName() + "_HIDDEN.value = '';");
            p.println("<SCRIPT>");
            p.println("function " + getFullName() + "_submit(row) {");
            p.println(getFormString() + getFullName() + "_HIDDEN.value = row;");
            p.println(getFormString() + "submit();");
            p.println("}");
            p.println("</SCRIPT>");
            _submitAlreadyGenerated = true;
        }
    }

    /**
     * Gets whether or not the component will submit the page before transfering control to the link page. If the link does a submit, all the user entered data will sent to the server to be recorded.
     */
    public boolean getDoSubmit() {
        return _doSubmit;
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
     * Use this method to get the javascript that will be executed when the user clicks on one of the components in the link.
     */
    public String getOnClick() {
        return _onClick;
    }

    /**
     * Use this method to get the javascript that will be executed when the mouse passes over out of all the components
     */
    public String getOnMouseOut() {
        return _onMouseOut;
    }

    /**
     * Use this method to get the javascript that will be executed when the mouse passes over any component in the link
     */
    public String getOnMouseOver() {
        return _onMouseOver;
    }

    public HtmlStyle getStyle() {
        return _style;
    }

    /**
     * Returns the target for the link.
     */
    public String getTarget() {
        return _target;
    }

    /**
     * This method will process the parms from a post for every component in the container.
     */
    public boolean processParms(Hashtable<String, Object>parms, int rowNo) throws Exception {
        if (super.processParms(parms, rowNo))
            return true;

        String name = getFullName() + "_HIDDEN";
        String[] p = (String[]) parms.get(name);

        if (p != null && p[0].trim().length() > 0 && _zoomTo == null) {
            _submit = this;

            _rowNo = Integer.parseInt(p[0].trim());
            String href = "";

            if (_dsEval != null) {
                if (_rowNo > -1)
                    href = _dsEval.evaluateRowFormat(_rowNo);
                else
                    href = _dsEval.evaluateRowFormat();
            } else
                href = _href;

            _zoomTo = href;

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
        //
        int listenersSize = _listeners.size();
        for (int i = 0; i < listenersSize; i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l) {
                _listeners.removeElementAt(i);
                return;
            }
        }
    }

    /**
     * Sets whether or not the component will submit the page before transfering control to the link page. If the link does a submit, all the user entered data will sent to the server to be recorded. If the flag is true, the target and onClick properties cannot be used.
     */
    public void setDoSubmit(boolean doSubmit) {
        _doSubmit = doSubmit;
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
     * This method was created in VisualAge.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     * @exception java.lang.Exception The exception description.
     */
    public void setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }

    /**
     * This method was created in VisualAge.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     * @exception java.lang.Exception The exception description.
     */
    public void setHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }

    /**
     * Use this method to set the javascript that will be executed when the user clicks on one of the components in the link.
     */
    public void setOnClick(String onClick) {
        _onClick = onClick;
    }

    /**
     * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
     */
    public void setOnMouseOut(String onMouseOut) {
        _onMouseOut = onMouseOut;
    }

    /**
     * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
     */
    public void setOnMouseOver(String onMouseOver) {
        _onMouseOver = onMouseOver;
    }

    /**
     * This method was created in VisualAge.
     * @param style com.salmonllc.html.HtmlStyle
     */
    public void setStyle(HtmlStyle style) {
        _style = style;
    }

    /**
     * Sets the target for the link.<BR>
     * _blank opens the destination document in a new unnamed window. <BR>
     * _parent opens the destination document in the parent window of the one displaying the current document. <BR>
     * _self opens the destination document in the same window as the one in which the link was clicked. <BR>
     * _top opens the destination document in the full body of the current window. This value can be used to ensure that the       destination document takes over the full window even if the original document was displayed in a frame. <BR>
     */
    public void setTarget(String target) {
        _target = target;
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
     * Clears the submit component in this container or children containers. The container stores which component inside it
     * submitted a particular page for one invocation so it can route to the correct submit performed methods. Once that's done,
     * the framework needs to clear out that value for the next page invocation. This method is used by the framework and should not be called directly.
     */
    public void clearSubmit() {
        super.clearSubmit();
        _submitAlreadyGenerated = false;
    }
    
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
	}


	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null)
			return -1;
		return _tabIndex.intValue();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String string) {
		_accessKey = string;
	}



	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
			_tabIndex = new Integer(val);
	}

}
