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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlPage.java $
//$Author: Dan $
//$Revision: 98 $
//$Modtime: 9/16/04 5:08p $
/////////////////////////

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.jsp.Constants;
import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspForm;
import com.salmonllc.jsp.JspNavBar;
import com.salmonllc.personalization.Skin;
import com.salmonllc.portlet.PortletInfo;
import com.salmonllc.portlet.SalmonPortletException;
import com.salmonllc.properties.Props;
import com.salmonllc.sitemap.SiteMap;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.util.ApplicationContext;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.ThreeObjectContainer;
import com.salmonllc.util.URLGenerator;
import com.salmonllc.util.Util;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

/**
 * This type is the superclass for every page in the HTML Framework. The developer creating the page should override the initialize method. In this method you should create and place any HTMLComponents that will reside in the page plus any other required initialization. The rest of the custom code in the page will be handled via listeners.
 */
public abstract class HtmlPage extends HtmlPageBase implements Serializable {
	public static final int FORMTYPE_URLENCODED = 0;
	public static final int FORMTYPE_MULTIPART = 1;

	private int _formType = 0;

	private Vector _listeners = null;
	private Vector _propertyExpressions;
	private HtmlContainer _componentsCont;
	protected static final int POST_EVENT = 0;
	protected static final int GET_EVENT = 1;

	private String _title = null;
	private String _baseTag = null;
	private String _metaTag = null;
	private String _pageDocType = null;

	private String _bgColor = null;
	private String _background = null;
	private String _textColor = null;
	private String _linkColor = null;
	private String _visitedLinkColor = null;
	private String _activeLinkColor = null;
	private String _onLoad = null;
	private String _hoverLinkColor = null;
	private boolean _generateForm = true;
	private long _refIndex = 1;
	protected String _script;
	protected Hashtable _scriptTable;
	protected Hashtable _htmlTable;
	private String _lastReferer = null;
	private int _marginWidth = -1;
	private int _marginHeight = -1;
	private int _leftMargin = -1;
	private int _topMargin = -1;
	private int _rightMargin = -1;
	private HtmlSubmitButton _defaultButton;
	private boolean _currentReferrerCleared = false;
	private Hashtable _subPages;
	private Vector _applets;
	private Hashtable _imageGenerators;
	private PageRunThread _runThread;
	private int _runThreadRefreshInterval;
	private boolean _isWmlMaintained = false;
	private boolean _encodeURLs = true;
	private String _popupscript = "";
	private JspForm _lastSubmitForm;
	private SalmonPortletException _portletException; 


	protected Vector _vnavbars;
	public static final String TRANSACTION_TOKEN_KEY = "com.salmonllc.jsp.htmlpage.TOKEN";
	protected String _token;

	/**
	 * Adds a new html component to this page
	 * @param comp com.salmonllc.html.HtmlComponent
	 */
	public void add(HtmlComponent comp) {
		if (_componentsCont == null)
			_componentsCont = new HtmlContainer("htmlPageTopContainer", this);

		_componentsCont.add(comp);
	}

	void addApplet(HtmlApplet a) {
		if (_applets == null)
			_applets = new Vector();
		_applets.addElement(a);
	}

	/**
	 * Adds a new listerner to this page to handle custom page events events.
	 */
	public void addPageListener(PageListener p) {
		if (_listeners == null)
			_listeners = new Vector();

		for (int i = 0; i < _listeners.size(); i++) {
			if (((PageListener) _listeners.elementAt(i)) == p)
				return;
		}

		_listeners.addElement(p);
	}

	/**
	 * Adds a HtmlPopup to a page.
	 * Creation date: (3/27/01 11:12:03 AM)
	 * @param popup com.salmonllc.html.HtmlPopup The popup to be added to the page.
	 */
	public void addPopup(HtmlPopup popup) {
		_popupscript += popup.getPopupScript() + "\n";
		add(popup);
	}

	/**
	 * This method will add a property expression to the Page.
	 * The propExpression will be evaluated by the  processPropertyExpression method for the current row in the datastore
	 * @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @param ds The datastore to use for the evaluation
	 * @param propExpression com.salmonllc.html.PropertyExpression The instance of PropertyExpression that should do the evaluating.
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreBuffer ds, DataStoreExpression propExpression) throws NoSuchMethodException, DataStoreException {
		DataStoreEvaluator dse = new DataStoreEvaluator(ds, propExpression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * This method will add a property expression to the page. The property expression will automatically set a property value with the results of the passed expression for the current row in the datastore.
	 * @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @param ds The datastore to use for the evaluation
	 * @param expression java.lang.String The datastore expression to evaluate
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreBuffer ds, String expression) throws NoSuchMethodException, DataStoreException {
		DataStoreEvaluator dse = new DataStoreEvaluator(ds, expression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * This method will add a property expression to the page. The property expression will automatically set a property value with the results of the passed expression.
	 * @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreEvaluator expEval) throws NoSuchMethodException, DataStoreException {
		Class c = comp.getClass();
		Method m[] = c.getMethods();
		Method exe = null;
		String name = "set" + propertyName;
		Class parms[] = null;
		for (int i = 0; i < m.length; i++) {
			if (name.equalsIgnoreCase(m[i].getName())) {
				parms = m[i].getParameterTypes();
				if (parms.length == 1) {
					exe = m[i];
					break;
				}
			}
		}
		if (exe == null)
			throw new NoSuchMethodException("Couldn't find a set method for property:" + propertyName);
		ThreeObjectContainer t = new ThreeObjectContainer(comp, exe, expEval);
		if (_propertyExpressions == null)
			_propertyExpressions = new Vector();
		_propertyExpressions.addElement(t);
	}

	/**
	 * This method cancels the thread running in the page.
	 */
	public void cancelRunThread() {
		if (_runThread != null && _runThread.getStatus() == PageRunThread.STATUS_RUNNING)
			_runThread.cancel();
	}

	/**
	 * Use this method to clear the last page referred flag. This will allow a submit button on the page to cause a page refresh on the next request by changing the result of isRequestedByCurrentPage to false.
	 */
	public void clearCurrentPageReferer() {
		_lastReferer = null;
		_lastSubmitForm = null;
		_currentReferrerCleared = true;
	}

	/**
	 * This method is used by components in the framework to process property expressions and shouldn't be called directly.
	 */
	public static void executePropertyMethod(Object comp, Method meth, DataStoreEvaluator eval, int row) {
		try {
			Object res[] = null;
			Class parms[] = null;
			String name = null;
			Number[] n = null;

			res = new Object[1];
			if (row == -1)
				res[0] = eval.evaluateRow();
			else
				res[0] = eval.evaluateRow(row);
			parms = meth.getParameterTypes();
			name = parms[0].getName().toUpperCase();
			if (res[0] != null) {
				if (name.startsWith("BOOLEAN") && res[0] instanceof Boolean)
					meth.invoke(comp, res);
				else if (name.endsWith("STRING") && res[0] instanceof String)
					meth.invoke(comp, res);
				else if (res[0] instanceof Number) {
					n = new Number[1];
					if (name.startsWith("INT"))
						n[0] = new Integer(((Number) res[0]).intValue());
					else if (name.startsWith("LONG"))
						n[0] = new Long(((Number) res[0]).longValue());
					else if (name.startsWith("FLOAT"))
						n[0] = new Float(((Number) res[0]).floatValue());
					else if (name.startsWith("DOUBLE"))
						n[0] = new Double(((Number) res[0]).doubleValue());
					else if (name.startsWith("BYTE"))
						n[0] = new Byte(((Number) res[0]).byteValue());
					else if (name.startsWith("SHORT"))
						n[0] = new Short(((Number) res[0]).shortValue());
					else
						return;
					meth.invoke(comp, n);
				}else if (name.endsWith("ARRAYLIST") && res[0] instanceof java.util.ArrayList)  {
					/*+- Following method is mainly used in the HtmlDaropDownList.setOptions()--*/
                    meth.invoke(comp, res);
                }
			} else {
				if (name.startsWith("BOOLEAN")) {
					Boolean b = new Boolean(false);
					res[0] = b;
					meth.invoke(comp, res);
				} else if (name.endsWith("STRING")) {
					String s = null;
					res[0] = s;
					meth.invoke(comp, res);
				} else {
					n = new Number[1];
					if (name.startsWith("INT"))
						n[0] = new Integer(0);
					else if (name.startsWith("LONG"))
						n[0] = new Long((long) 0);
					else if (name.startsWith("FLOAT"))
						n[0] = new Float((float) 0);
					else if (name.startsWith("DOUBLE"))
						n[0] = new Double((double) 0);
					else if (name.startsWith("BYTE"))
						n[0] = new Byte((byte) 0);
					else if (name.startsWith("SHORT"))
						n[0] = new Short((short) 0);
					else
						return;
					meth.invoke(comp, n);
				}
			}

		} catch (Exception e) {
			MessageLog.writeErrorMessage("Execute Property Method", e, null);
		}
	}

	private void findContainerComponents(HtmlContainer cont, Vector list) {
		list.addElement(cont);
		Enumeration e = cont.getComponents();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			if (o instanceof HtmlContainer)
				findContainerComponents((HtmlContainer) o, list);
			else if (o instanceof JspContainer)
				findContainerComponents((JspContainer) o, list);
			else
				list.addElement(o);

		}
	}

	private void findContainerComponents(JspContainer cont, Vector list) {
		list.addElement(cont);
		Enumeration e = cont.getComponents();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			if (o instanceof HtmlContainer)
				findContainerComponents((HtmlContainer) o, list);
			else if (o instanceof JspContainer)
				findContainerComponents((JspContainer) o, list);
			else
				list.addElement(o);

		}
	}
	/**
	 * This method was created in VisualAge.
	 * @param pw java.io.PrintWriter
	 */
	// private void generateDefButtonScript(PrintWriter pw, HtmlComponent button) {

	/*if (!button.getVisible(true))
	  return;
	
	writeScript("if(parseInt(navigator.appVersion) >= 4 && navigator.appName == \"Netscape\")");
	writeScript("  document.captureEvents(Event.KEYDOWN);");
	writeScript("document.onkeydown = checkChar;");
	
	pw.println("<SCRIPT>");
	pw.println("function checkChar(evt) {");
	pw.println("   var theBtnOrKey;");
	pw.println("   if (parseInt(navigator.appVersion) >= 4 && navigator.appName == \"Netscape\")");
	pw.println("      theBtnOrKey = evt.which;");
	pw.println("   else if (parseInt(navigator.appVersion) >= 4 && navigator.appName != \"Netscape\")");
	pw.println("      theBtnOrKey = window.event.keyCode;");
	pw.println("   if(theBtnOrKey == 13) {");
	pw.println("     document.forms[0]." + button.getFullName() + ".focus();");
	pw.println("     document.forms[0]." + button.getFullName() + ".click();");
	pw.println("	}");
	pw.println("}");
	pw.println("</SCRIPT>");*/
	//  return;
	//}

	/**
	 * This method will generate the html for each component in the page
	 */
	public synchronized void generateHTML(PrintWriter p) throws Exception {
		if (!notifyListeners(GET_EVENT, true))
			return;

		processPropertyExpressions();
		if (_subPageNameValue == null || _subPages == null)
			generatePageHtml(p, null);
		else {
			HtmlComponent comp = getSubPage(_subPageNameValue);
			if (comp == null)
				p.println("Sub Page not found");
			else {
				generatePageHtml(p, comp);
			}
		}

		notifyListeners(GET_EVENT, false);

		_lastReferer = null;

	}

	/**
	 * This method will generat and image from a component in the page and send it to the output stream.
	 * @param compName The full name for the component that will generate the image
	 * @param imageName The name of the image
	 * @param out The output stream to write to
	 */
	public void generateImage(String compName, String imageName, OutputStream out) throws IOException {
		if (_imageGenerators == null)
			throw new IOException("Image Not Found:" + compName + " " + imageName);
		else {
			ImageGenerator gen = (ImageGenerator) _imageGenerators.get(compName);
			if (gen != null)
				gen.generateImage(imageName, out);
		}
	}

	private void generatePageHtml(PrintWriter p, HtmlComponent comp) throws Exception {
		String title = _title;
		if (title == null)
			title = getApplicationName() + "_" + getPageName();
		if (_pageDocType != null)
			p.println(_pageDocType);
		if (getGenerateFullHTML()) {
			p.println("<HTML>");
			p.println("<HEAD>");
			if (getContentType() != null)
				p.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"" + getContentType() + "\">");
			p.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">");
			p.println("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">");
			p.println("<TITLE>" + title + "</TITLE>");

			// BASE
			if (!Util.isNull(_baseTag) && !Util.isEmpty(_baseTag)) {
				p.println("<BASE " + _baseTag + ">");
			}

			// META
			if (!Util.isNull(_metaTag) && !Util.isEmpty(_metaTag)) {
				p.println("<META " + _metaTag + ">");
			}

			if (_runThread != null) {
				if (_runThread.getStatus() == PageRunThread.STATUS_RUNNING)
					p.println("<META HTTP-EQUIV=\"refresh\" content=\"" + _runThreadRefreshInterval + ";URL=" + getServerURL() + "/" + getServletBaseURL() + "/AppServer/" + getApplicationName() + "/" + getPageName() + "\">");
			}

			// Hover Link code
			if (!Util.isNull(_hoverLinkColor) && !Util.isEmpty(_hoverLinkColor)) {
				p.println("<style type=\"text/css\">");
				p.println("<!--");
				p.println("a:hover { color: " + _hoverLinkColor + "}");
				p.println("-->");
				p.println("</style>");
			}
			p.println("</HEAD>");
			String bodyTag = "<BODY";

			// BGCOLOR
			if (!Util.isNull(_bgColor) && !Util.isEmpty(_bgColor)) {
				bodyTag += " BGCOLOR=\"" + _bgColor + "\"";
			}

			// BACKGROUND
			if (!Util.isNull(_background) && !Util.isEmpty(_background)) {
				bodyTag += " BACKGROUND=\"" + _background + "\"";
			}

			// TEXT
			if (!Util.isNull(_textColor) && !Util.isEmpty(_textColor)) {
				bodyTag += " TEXT=\"" + _textColor + "\"";
			}

			// LINK
			if (!Util.isNull(_linkColor) && !Util.isEmpty(_linkColor)) {
				bodyTag += " LINK=\"" + _linkColor + "\"";
			}

			// VLINK
			if (!Util.isNull(_visitedLinkColor) && !Util.isEmpty(_visitedLinkColor)) {
				bodyTag += " VLINK=\"" + _visitedLinkColor + "\"";
			}

			// ALINK
			if (!Util.isNull(_activeLinkColor) && !Util.isEmpty(_activeLinkColor)) {
				bodyTag += " ALINK=\"" + _activeLinkColor + "\"";
			}

			// ONLOAD
			if (!Util.isNull(_onLoad) && !Util.isEmpty(_onLoad)) {
				bodyTag += " ONLOAD=\"" + _onLoad + "\"";
			}

			// LEFTMARGIN
			if (_leftMargin > -1) {
				bodyTag += " LEFTMARGIN=\"" + _leftMargin + "\"";
			}

			// RIGHTMARGIN
			if (_rightMargin > -1) {
				bodyTag += " RIGHTMARGIN=\"" + _rightMargin + "\"";
			}

			// TOPMARGIN
			if (_topMargin > -1) {
				bodyTag += " TOPMARGIN=\"" + _topMargin + "\"";
			}

			// MARGINWIDTH
			if (_marginWidth > -1) {
				bodyTag += " MARGINWIDTH=\"" + _marginWidth + "\"";
			}

			// MARGINHEIGHT
			if (_marginHeight > -1) {
				bodyTag += " MARGINHEIGHT=\"" + _marginHeight + "\"";
			}
			p.println(bodyTag + ">");
		}

		//
		StringBuffer submitMethod = new StringBuffer();
		if (_generateForm) {
			String enctype = "";
			if (_formType == FORMTYPE_MULTIPART)
				enctype = " ENCTYPE=\"multipart/form-data\"";
			p.print("<FORM NAME=\"PageForm\" METHOD=\"POST\"" + enctype);
			if (_applets != null && comp == null) {
				for (int i = 0; i < _applets.size(); i++) {
					HtmlApplet apl = (HtmlApplet) _applets.elementAt(i);
					if (apl.getVisible() && apl.getInteractWithForm()) {
						submitMethod.append("document.forms[0].");
						submitMethod.append(apl.getFullName());
						submitMethod.append("HIDDENVALUE.value=document.applets['");
						submitMethod.append(apl.getFullName());
						submitMethod.append("'].getValue(); ");
						//
						submitMethod.append("document.forms[0].");
						submitMethod.append(apl.getFullName());
						submitMethod.append("HIDDENSTATE.value=document.applets['");
						submitMethod.append(apl.getFullName());
						submitMethod.append("'].getState(); ");
					}
				}
				if (submitMethod.length() > 0)
					p.print(" ONSUBMIT=\"transferAppletValues();\"");
			}
			p.print(">");
		}
		if (getSubPageName() == null)
			_refIndex++;
		p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + Constants.TOKEN_KEY + "\" VALUE=\"" + _token + "\">");
		p.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getApplicationName() + "_" + getPageName() + "_refIndex\" VALUE=\"" + _refIndex + "\">");
		if (comp != null) {
			comp.generateInitialHTML(p);
			comp.generateHTML(p, -1);
		} else {
			if (_componentsCont != null) {
				_componentsCont.generateInitialHTML(p);
				_componentsCont.generateHTML(p, -1);
			}
		}

		// SCRIPT
		if (!Util.isNull(_script) && !Util.isEmpty(_script)) {
			p.println("<SCRIPT>");
			p.println(_script);
			writeNamedScripts(p);
			p.println("</SCRIPT>");
			_script = null;
		}

		//
		if (_generateForm) {
			if (submitMethod.length() > 0) {
				p.println("<SCRIPT>");
				p.println("function transferAppletValues() {");
				p.println(submitMethod.toString());
				p.println("}");
				p.println("</SCRIPT>");
			}
			p.println("</FORM>");
		}
		// if (_defaultButton != null)
		//    generateDefButtonScript(p, _defaultButton);
		if (getBrowserType() == BROWSER_MICROSOFT && getBrowserVersion() >= 4) {
			if (_popupscript != null && !_popupscript.equals("")) {
				p.println("<SCRIPT LANGUAGE='JavaScript1.2'>");
				p.println(getMouseCaptureScript());
				p.println("</SCRIPT>");
			}
		}
		writeNamedHtml(p);
		if (getGenerateFullHTML())
			p.println("</BODY></HTML>");
	}

	/**
	 * This method gets the active link color for the page
	 */
	public String getActivelinkColor() {
		return _activeLinkColor;
	}

	/**
	 * This method returns the  url of a background image for the page
	 */
	public String getBackground() {
		return _background;
	}

	/**
	 * This method gets the background color for the page
	 */
	public String getBackgroundColor() {
		return _bgColor;
	}

	/**
	 * This method sets the contents of the base tag in the html head tag.
	 */
	public String getBaseTag() {
		return _baseTag;
	}

	/**
	 * This method will return a list of all components in the page (including subcomponents).
	 */
	public Enumeration getComponents() {
		Vector list = new Vector();
		if (_componentsCont != null)
			findContainerComponents(_componentsCont, list);
		return list.elements();
	}

	/**
	 * Use this method to find out which button will be clicked when the enter key is pressed.
	 * @deprecated
	 */
	public HtmlSubmitButton getDefaultButton() {
		return _defaultButton;
	}

	/**
	 * This method gets the form type. Valid values are FORMTYPE_URLENCODED and FORMTYPE_MULTIPART.
	 */
	public int getFormType() {
		return _formType;
	}

	/**
	* This method gets the page DOCTYPE type string.
	*/
	public String getDocumentType() {
		return _pageDocType;
	}
	/**
	 * This method gets whether or not the page will generate a form tag.
	 */
	public boolean getGenerateFormTag() {
		return _generateForm;
	}

	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	  */
	private String getHeaderValue(String header, String search) {
		StringTokenizer t = new StringTokenizer(header, ";");
		try {
			while (t.hasMoreTokens()) {
				String token = t.nextToken().trim();
				if (token.startsWith(search)) {
					int pos = token.indexOf("=");
					if (pos > -1) {
						String ret = token.substring(pos + 1).trim();
						if (ret.charAt(0) == '"' && ret.charAt(ret.length() - 1) == '"')
							ret = ret.substring(1, ret.length() - 1);
						return ret;
					}
				}
			}

		} catch (Exception e) {
			return null;
		}

		return null;
	}

	/**
	 * This method gets the hover link color for the page
	 */
	public String getHoverLinkColor() {
		return _activeLinkColor;
	}

	/**
	 * This method will get an image generator from a component in the page.
	 * @param compName The full name for the component that will generate the image
	 */
	public ImageGenerator getImageGenerator(String compName) {
		if (_imageGenerators == null)
			return null;
		else
			return (ImageGenerator) _imageGenerators.get(compName);
	}

	/**
	 * This method was created in VisualAge.
	 */
	protected String getLastReferer() {
		return _lastReferer;
	}

	/**
	 * This method returns the left margin for the page or -1 if no margin is set.
	 * @return int
	 */
	public int getLeftMargin() {
		return _leftMargin;
	}

	/**
	 * This method gets the link color for the page
	 */
	public String getLinkColor() {
		return _linkColor;
	}

	/**
	 * This method returns the margin height for the page or -1 if no margin is set.
	 * @return int
	 */
	public int getMarginHeight() {
		return _marginHeight;
	}

	/**
	 * This method returns the margin width for the page or -1 if no margin is set.
	 * @return int
	 */
	public int getMarginWidth() {
		return _marginWidth;
	}

	/**
	 * This method gets the contents of the meta tag in the html head tag.
	 */
	public String getMetaTag() {
		return _metaTag;
	}

	public void addNavBar(JspNavBar jspNavBar) {
		if (jspNavBar == null)
			return;
		if (_vnavbars == null)
			_vnavbars = new Vector(2);
		_vnavbars.addElement(jspNavBar);
	}

	public Vector getNavBarsVector() {
		return _vnavbars;
	}
	private String getMouseCaptureScript() {
		String sScript = "var mouseX=0;\n";
		sScript += "var mouseY=0;\n";
		sScript += "function mouseTracker(e) {\n";
		sScript += "e = e || window.Event || window.event;\n";
		sScript += "mouseX = e.pageX || e.clientX;\n";
		sScript += "mouseY = e.pageY || e.clientY;\n";
		sScript += _popupscript;
		sScript += "}\n";
		sScript += "function setMouseTracker() {\n";
		sScript += "if (document.captureEvents)\n";
		sScript += "document.captureEvents(window.Event.MOUSEMOVE|window.Event.MOUSEUP);\n";
		sScript += "document.onmousemove = this.mouseTracker;\n";
		sScript += "}\n";
		sScript += "function getMouseX() {\n";
		sScript += "return mouseX;\n";
		sScript += "}\n";
		sScript += "function getMouseY() {\n";
		sScript += "return mouseY;\n";
		sScript += "}\n";
		sScript += "setMouseTracker();\n";
		return sScript;
	}

	/**
	 * This method gets the java script to be executed when the page is loaded.
	 */
	public String getOnLoad() {
		return _onLoad;
	}

	/**
	 * Use this method to get a parameter from the HTTP Request.
	 * @return The requested parameter or the passed default value if the parameter is not found
	 * @param name The name of the parameter to search for
	 * @param defaultValue The value to use if the parameter is missing
	 */
	public String getParameter(String name, String defaultValue) {
		String ret = getParameter(name);
		if(!Util.isFilled(ret))
		{
			ret=defaultValue;
		}
		return ret;
	}
	/**
	 * Use this method to get a parameter from the HTTP Request.
	 * @return The requested parameter or null if the parameter is not found
	 * @param name The name of the parameter to search for
	 */
	public String getParameter(String name) {
		String[] parm = getCurrentRequest().getParameterValues(name);

		if (parm == null) {
			String query = getCurrentRequest().getQueryString();

			/**
			 * 12-05-2002
			 * srufle
			 * Added query.indexOf("=") This protects our sites from a query that does not provide a value
			 * ex. if you pass ?f on the url line the call to parseQueryString(query) will throw an IllegalArgumentException
			 */
			if (query == null || query.indexOf("=") < 0) {
				return null;
			} else {
				Hashtable ht = HttpUtils.parseQueryString(query);
				parm = (String[]) ht.get(name);
				if (parm == null)
					return null;
				else
					return parm[0];
			}
		} else
			return parm[0];
	}

	/**
	 * This method is basically copy of the same method in HttpUtils, but it fixes the
	 * following bug:  if the server port in the request is the default, it would be
	 * converted to ":0" instead of the empty string.
	 * @return java.lang.StringBuffer
	 * @param req HttpServletRequest
	 */
	public static StringBuffer getRequestURL(javax.servlet.http.HttpServletRequest req) {
		StringBuffer url = new StringBuffer();
		url.append(URLGenerator.generateServerURL(req));
		String servletURL = URLGenerator.generateServletURL(req);
		if (servletURL != null && servletURL.length() > 0 && servletURL.charAt(0) != '/')
			url.append("/");
		url.append(servletURL);

		// we were adding a 'null' to the end of the string
		String testPath = req.getPathInfo();
		if (testPath != null) {
			url.append(testPath);
		}
		/*String scheme = req.getScheme();
		int port = req.getServerPort();
		// BUG FIX START
		if (port <= 0)
		    port = 80;
		// BUG FIX END
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		url.append(scheme); // http, https
		
		url.append("://");
		url.append(req.getServerName());
		if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
		    url.append(':');
		    url.append(req.getServerPort());
		}
		if (servletPath != null)
		    url.append(servletPath);
		if (pathInfo != null)
		    url.append(pathInfo);*/
		return url;
	}

	/**
	 * This method returns the right margin for the page or -1 if no margin is set.
	 * @return int
	 */
	public int getRightMargin() {
		return _rightMargin;
	}

	/**
	 * This method will return the exception thrown by the current run thread. If the status of the the thread is not PageRunThread.STATUS_ERROR the method will return null.
	 */
	public Exception getRunThreadException() {
		if (_runThread == null)
			return null;
		else
			return _runThread.getException();
	}

	/**
	 * This method will return a number between 0 and 100 to indicate the percent the process in the run thread is complete. If the thread is not set the method will return -1.
	 */
	public int getRunThreadPercentComplete() {
		if (_runThread == null)
			return -1;
		else
			return _runThread.getPercentComplete();
	}

	/**
	 * This method will return the status of the currently running page thread (started by the run thread method). Valid Return Values are PageRunThread.STATUS_NOT_STARTED, PageRunThread.STATUS_RUNNING, PageRunThread.STATUS_COMPLETED, PageRunThread.STATUS_CANCELED and PageRunThread.STATUS_NOT_SET
	 */
	public int getRunThreadStatus() {
		if (_runThread == null)
			return PageRunThread.STATUS_NOT_SET;
		else
			return _runThread.getStatus();
	}

	/**
	 * This method returns the component that submitted the page.
	 */
	public HtmlComponent getSubmitComponent() {
		return _componentsCont.getSubmitComponent();
	}

	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	 * @param comp com.salmonllc.html.HtmlComponent
	 * @param url java.lang.String
	 */
	String getSubmitJavaScript(HtmlComponent comp, String url, HtmlInlineFrame frame) {
		String name = null;
		if (comp != null) {
			if (comp instanceof HtmlSubmitButton)
				name = comp.getFullName();
			else if (comp instanceof HtmlSubmitImage)
				name = comp.getFullName() + ".x";
		}

		String ret = null;

		boolean doIt = false;
		if (frame != null)
			doIt = getUseIFrames();

		if (name != null && doIt) {
			String loading = "<FONT face=Helvetica Size=4> Loading. Please Wait...</FONT>";
			boolean transferApplets = false;
			if (_applets != null) {
				for (int i = 0; i < _applets.size(); i++) {
					HtmlApplet apl = (HtmlApplet) _applets.elementAt(i);
					if (apl.getVisible() && apl.getInteractWithForm()) {
						transferApplets = true;
						break;
					}
				}
			}

			if (transferApplets)
				ret = "\r\ntransferAppletValues(); \r\n";
			else
				ret = "\r\n";

			ret += "ele = document.forms[0].elements; \r\n"
				+ "layerURL = 'iFrameSubmitFormParms=true&"
				+ name
				+ "=clicked'; \r\n"
				+ "for (i = 0; i < ele.length; i++) { \r\n"
				+ "    if (ele[i].value != null && ele[i].value != '') { \r\n"
				+ "	    if (ele[i].type == 'text' || ele[i].type == 'hidden' || ele[i].type == 'password' || ele[i].type == 'textarea' || ele[i].type == 'select-one') \r\n"
				+ "          layerURL += '&' + ele[i].name + '=' + escape(ele[i].value); \r\n"
				+ "       else if ((ele[i].type == 'checkbox' || ele[i].type == 'radio') && ele[i].checked) \r\n"
				+ "          layerURL += '&' + ele[i].name + '=' + escape(ele[i].value); \r\n"
				+ "       else if (ele[i].type == 'select-multiple') { \r\n"
				+ "			opt = ele[i].options; \r\n"
				+ "			for (j = 0; j < opt.length; j++) { \r\n"
				+ "				if (opt[j].selected) \r\n"
				+ "		   		   layerURL += '&' + ele[i].name + '=' + opt[j].value; \r\n"
				+ "	        } \r\n"
				+ "	    } \r\n"
				+ "    } \r\n"
				+ "} \r\n"
				+ "layerURL = '"
				+ url
				+ "?' + layerURL; \r\n";

			if (getBrowserType() == BROWSER_MICROSOFT) {
				ret += "window2 = document." + frame.getFullName() + ".document.open('text/html','replace');\r\n" + "window2.writeln('" + loading + "'); \r\n" + "document." + frame.getFullName() + ".document.location.replace(layerURL);";
			} else {
				ret += "ed = document.getElementById('" + frame.getFullName() + "');\r\n" + "ed.setAttribute('src',layerURL);";
			}

		}

		return ret;
	}

	/**
	 * This method returns the component that represents the subpage.
	 * @param name java.lang.String the name of the subpage component.
	 */
	public HtmlComponent getSubPage(String name) {
		if (_subPages == null)
			return null;
		Object o = _subPages.get(name);
		if (o == null)
			return null;
		return (HtmlComponent) ((Object[]) o)[0];
	}

	/**
	 * This method returns the mime type of the component that represents the subpage.
	 * @param name java.lang.String the name of the subpage component.
	 */
	public String getSubPageMimeType(String name) {
		if (_subPages == null)
			return null;
		Object o = _subPages.get(name);
		if (o == null)
			return null;
		return (String) ((Object[]) o)[1];
	}

	/**
	 * This method gets the text color for the page
	 */
	public String getTextColor() {
		return _textColor;
	}

	/**
	 * This method gets the title of the page.
	 */
	public String getTitle() {
		if (_title == null)
			return getApplicationName() + "_" + getPageName();
		else
			return _title;
	}

	/**
	 * This method will return a the container that holds all the components in the page.
	 */
	public HtmlContainer getTopContainer() {
		return _componentsCont;
	}

	/**
	 * This method returns the top margin for the page or -1 if no margin is set.
	 * @return int
	 */
	public int getTopMargin() {
		return _topMargin;
	}

	/**
	 * This method returns true if the browser supports iFrames.
	 */
	public boolean getUseIFrames() {
		if (getBrowserType() == BROWSER_MICROSOFT && getBrowserVersion() >= 4)
			return true;
		else if (getBrowserType() == BROWSER_NETSCAPE && getBrowserVersion() > 4)
			return true;

		return false;
	}

	/**
	 * This method gets the visited link color for the page
	 */
	public String getVisitedLinkColor() {
		return _visitedLinkColor;
	}

	/**
	 * This method will indicate whether the page submitting a request is the current one or has been retrieved from the browsers cache
	 */
	public boolean isExpired() {
		String ref[] = getCurrentRequest().getParameterValues(getApplicationName() + "_" + getPageName() + "_refIndex");
		if (ref != null)
			if (!ref[0].equals(new Long(_refIndex).toString()))
				return true;

		return false;
	}

	/**
	 * Use this method to find out how this page was envoked. If it was invoked from itself (via a submit button) the method will return true.
	 */
	public boolean isReferredByCurrentPage() {
		String referer = getCurrentRequest().getHeader("referer");

		if (referer == null) {
			if (_lastReferer == null)
				return false;
			else
				referer = _lastReferer;
		}

		referer = trimUrl(referer);
		String url = trimUrl(getRequestURL(getCurrentRequest()).toString());
		if (url.equals(referer))
			return true;
		else
			return false;
	}

	/**
	 * This method returns true if the page represents a wml page
	 */
	public boolean isWMLMaintained() {
		return _isWmlMaintained;
	}

	public void loadProperties() {

		Props p = getPageProperties();

		_bgColor = p.getProperty(Props.PAGE_BACKGROUND_COLOR);
		_background = p.getProperty(Props.PAGE_BACKGROUND);
		_textColor = p.getProperty(Props.PAGE_TEXT_COLOR);
		_linkColor = p.getProperty(Props.PAGE_LINK_COLOR);
		_visitedLinkColor = p.getProperty(Props.PAGE_VISITED_LINK_COLOR);
		_activeLinkColor = p.getProperty(Props.PAGE_ACTIVE_LINK_COLOR);
		_hoverLinkColor = p.getProperty(Props.PAGE_HOVER_LINK_COLOR);
		_marginHeight = p.getIntProperty(Props.PAGE_MARGINHEIGHT);
		_marginWidth = p.getIntProperty(Props.PAGE_MARGINWIDTH);
		_leftMargin = p.getIntProperty(Props.PAGE_LEFTMARGIN);
		_rightMargin = p.getIntProperty(Props.PAGE_RIGHTMARGIN);
		_topMargin = p.getIntProperty(Props.PAGE_TOPMARGIN);
		_useDisabledAttribute = p.getBooleanProperty(Props.USE_DISABLED_ATTRIBUTE, _useDisabledAttribute);

		String historyFixUp = p.getProperty(Props.SYS_HISTORY_FIX);
		if (historyFixUp == null)
			setHistoryFixUp(false);
		else
			setHistoryFixUp((historyFixUp.equalsIgnoreCase("true")));
	}

	protected boolean notifyListeners(int event, boolean pre) throws Exception {
		if (_listeners == null)
			return true;

		PageEvent p = null;

		if (_subPageNameValue != null && _subPages != null) {
			HtmlComponent comp = getSubPage(_subPageNameValue);
			p = new PageEvent(this, comp, _subPageNameValue);
		} else
			p = new PageEvent(this);

		for (int i = 0; i < _listeners.size(); i++) {
			if (event == GET_EVENT)
				if (pre)
					 ((PageListener) _listeners.elementAt(i)).pageRequested(p);
				else
					 ((PageListener) _listeners.elementAt(i)).pageRequestEnd(p);
			else if (pre)
				 ((PageListener) _listeners.elementAt(i)).pageSubmitted(p);
			else
				 ((PageListener) _listeners.elementAt(i)).pageSubmitEnd(p);

			if (!p.getContinueProcessing() || _portletException != null)
				return false;
		}

		return true;
	}

	/**
	 * This method was created in VisualAge.
	 * @return java.util.Hashtable
	 */
	private Hashtable processMultiPartForm() throws Exception {
		Hashtable ht = new Hashtable();
		HttpServletRequest req = getCurrentRequest();
		String type = req.getHeader("Content-Type");
		String bound = getHeaderValue(type, "boundary");
		byte[] boundary = new byte[bound.length() + 4];
		boundary[0] = 13;
		boundary[1] = 10;
		boundary[2] = 45;
		boundary[3] = 45;
		for (int i = 0; i < bound.length(); i++)
			boundary[i + 4] = (byte) bound.charAt(i);

		InputStream in1 = req.getInputStream();
		InputStreamReader inr = new InputStreamReader(in1, "ISO8859_1");

		BufferedReader in = new BufferedReader(inr, 2048);
		//BufferedInputStream inTmp = new BufferedInputStream(in1, 2048);

		//StringBuffer sbTemp = new StringBuffer();
		//int i = 0;
		//try
		//{
		//while ((i = inTmp.read()) > -1)
		//{
		//sbTemp.append((char) i);
		//}

		//}
		//catch (Exception e)
		//{
		//MessageLog.writeErrorMessage("SR", e, null);
		//}

		bound = "--" + bound;
		String line = in.readLine();
		while (line != null) {
			String name = getHeaderValue(line, "name");
			if (name == null) {
				line = in.readLine();
				continue;
			}

			String filename = getHeaderValue(line, "filename");
			if (filename != null) {
				line = in.readLine();
				String mimeType = "";
				if (line.length() > 0)
					mimeType = line.substring(14);
				in.readLine();
				ht.put(name, filename);
				ht.put(name + "_contentType", mimeType);
				byte[] section = readSection(in, boundary);
				section = (new String(section, getCurrentResponse().getCharacterEncoding())).getBytes(getCurrentResponse().getCharacterEncoding());
				ht.put(name + "_content", section);
			} else {
				line = in.readLine();
				line = getCharEncodedValue(line);
				String[] value = new String[1];
				value[0] = "";
				String newline = "";
				// Read text until boundary sequence is matched at the beginning of a line.
				// Use startsWith() to match the boundary because sometimes there are extra
				// dashes at the end.
				// Newline characters must be accumulated, e.g. for multi-line text edits.
				while (((line = in.readLine()) != null) && !line.startsWith(bound)) {
					line = getCharEncodedValue(line);
					value[0] += newline + line;
					newline = "\r\n";
				}
				String oldVal[] = (String[]) ht.get(name);

				if (oldVal != null) {
					int i = 0;
					Vector vec = new Vector();
					for (i = 0; i < oldVal.length; i++) {
						vec.add(oldVal[i]);
					}

					vec.add(value[0]);
					value = new String[vec.size()];
					for (i = 0; i < vec.size(); i++) {
						value[i] = vec.get(i).toString();
					}
					ht.put(name, value);
				} else {
					ht.put(name, value);
				}
			}
			line = in.readLine();
		}

		return ht;
	}

	private Hashtable buildTableFromParms(HttpServletRequest r) {
		Hashtable h = new Hashtable();
		Enumeration e = r.getParameterNames();
		while (e.hasMoreElements()) {
			String parm = (String) e.nextElement();
			String[] saParmValues = r.getParameterValues(parm);
			String[] saCharEncodedValues = getCharEncodedParms(saParmValues);
			h.put(parm, saCharEncodedValues);
		}
		return h;
	}

	private String[] getCharEncodedParms(String[] saParmValues) {
		if (saParmValues == null)
			return saParmValues;
		String charEncoding = getCurrentResponse().getCharacterEncoding();
		if (charEncoding == null)
			return saParmValues;
		String[] saCharEncodedValues = new String[saParmValues.length];
		for (int i = 0; i < saParmValues.length; i++) {
			try {
				saCharEncodedValues[i] = new String(saParmValues[i].getBytes("ISO8859_1"), charEncoding);
			} catch (UnsupportedEncodingException uee) {
				saCharEncodedValues[i] = saParmValues[i];
			}
		}
		return saCharEncodedValues;
	}

	private String getCharEncodedValue(String value) {
		try {
			return new String(value.getBytes("ISO8859_1"), getCurrentResponse().getCharacterEncoding());
		} catch (UnsupportedEncodingException uee) {
			return value;
		}
	}

	/**
	 * This method will process the parms from a post for every component in the page.
	 */
	public synchronized void processParms(boolean iFrameSubmit) throws Exception {

		Hashtable h = null;
		HttpServletRequestWrapper req = (HttpServletRequestWrapper) getCurrentRequest();
		HttpServletResponseWrapper res = (HttpServletResponseWrapper) getCurrentResponse();

		if (_formType == FORMTYPE_MULTIPART)
			try {
				h = processMultiPartForm();
			} catch (Exception e) {
				h = buildTableFromParms(req);
			} else {
			h = buildTableFromParms(req);
		}

		res.setRedirectSent(false);
		_currentReferrerCleared = false;

		if (!notifyListeners(POST_EVENT, true))
			return;

		if (_subPageNameValue == null || _subPages == null || iFrameSubmit) {
			if (_componentsCont != null) {
				_componentsCont.clearSubmit();
				_componentsCont.processParms(h, -1);

				if (!_componentsCont.executeEvent(HtmlComponent.EVENT_OTHER))
					_componentsCont.clearSubmit();
				else {
					HtmlComponent scomp = getSubmitComponent();
					if (scomp != null)
						_lastSubmitForm = JspForm.findParentForm(scomp);
					_componentsCont.executeEvent(HtmlComponent.EVENT_SUBMIT);
				}
			}
		} else {
			HtmlComponent comp = getSubPage(_subPageNameValue);
			if (comp != null) {
				if (_componentsCont != null)
					_componentsCont.clearSubmit();

				comp.processParms(h, -1);

				if (!comp.executeEvent(HtmlComponent.EVENT_OTHER)) {
					if (_componentsCont != null)
						_componentsCont.clearSubmit();
				} else {
					HtmlComponent scomp = getSubmitComponent();
					if (comp != null)
						_lastSubmitForm = JspForm.findParentForm(scomp);

					comp.executeEvent(HtmlComponent.EVENT_SUBMIT);
				}
			}
		}

		notifyListeners(POST_EVENT, false);

		if (!_currentReferrerCleared) {
			if (isRequestFromPortlet())
				_lastReferer = getPortletInfo().getPortletRenderURL();
			else
				_lastReferer = HttpUtils.getRequestURL(getCurrentRequest()).toString();
		}

	}

	/**
	 * This method was created in VisualAge.
	 */
	protected void processPropertyExpressions() {
		if (_propertyExpressions == null)
			return;

		ThreeObjectContainer c = null;
		Object comp = null;
		Method meth = null;
		DataStoreEvaluator eval = null;

		int propertyExpressionsSize = _propertyExpressions.size();
		for (int i = 0; i < propertyExpressionsSize; i++) {
			c = (ThreeObjectContainer) _propertyExpressions.elementAt(i);
			comp = c.getObject1();
			meth = (Method) c.getObject2();
			eval = (DataStoreEvaluator) c.getObject3();
			HtmlPage.executePropertyMethod(comp, meth, eval, -1);
		}
	}

	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	 * @param in java.io.InputStream
	 */
	private String readLine(InputStream in) throws IOException {

		int i = in.read();
		if (i == -1)
			return null;

		StringBuffer sb = new StringBuffer();

		do {
			byte b = (byte) i;
			if (b == 13 && in.read() == 10)
				return sb.toString();

			sb.append((char) b);
			i = in.read();
		} while (i != -1);

		return sb.toString();

	}

	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	 * @param in java.io.InputStream
	 */
	private byte[] readSection(Reader in, byte[] boundary) throws IOException {
		byte[] temp = new byte[boundary.length];
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);

		int pos = 0;
		int i = in.read();
		while (i != -1) {
			byte b = (byte) i;
			if (b == boundary[pos]) {
				temp[pos] = b;
				pos++;
				if (pos == boundary.length) {
					out.flush();
					return out.toByteArray();
				}
			} else {
				if (pos > 0) {
					out.write(temp, 0, pos);
					pos = 0;
					continue;
				}
				out.write(b);
			}

			i = in.read();
		}
		out.flush();
		return out.toByteArray();
	}

	public void registerImageGenerator(String key, ImageGenerator gen) {
		if (_imageGenerators == null)
			_imageGenerators = new Hashtable();
		_imageGenerators.put(key, gen);
	}

	/**
	 * This method allows you to specify particular components in the page to be treated as sub pages. These sub pages will have their own URL which is a combiniation of the page's url and the name specified. For example a page called Page1 with a subpage called comp1 would have a URL of http://host/servet/AppServer/application/Page1/Comp1. When a subpage is called, only the Html for the specified component will be generated and sent back to the browser.
	 * @param name java.lang.String The name of the subcomponent from the URL
	 * @param comp com.salmonllc.html.HtmlComponent The component to generate the Html
	 */
	public void registerSubPage(String name, HtmlComponent comp) {
		if (_subPages == null)
			_subPages = new Hashtable();

		Object o[] = { comp, null };

		_subPages.put(name, o);
	}

	/**
	 * This method allows you to specify particular components in the page to be treated as sub pages. These sub pages will have their own URL which is a combiniation of the page's url and the name specified. For example a page called Page1 with a subpage called comp1 would have a URL of http://host/servet/AppServer/application/Page1/Comp1. When a subpage is called, only the Html for the specified component will be generated and sent back to the browser.
	 * @param name java.lang.String The name of the subcomponent from the URL
	 * @param comp com.salmonllc.html.HtmlComponent The component to generate the Html
	 * @param mimeType com.salmonllc.html.HtmlComponent The mime type of the component to send back to the browser
	 */
	public void registerSubPage(String name, HtmlComponent comp, String mimeType) {
		if (_subPages == null)
			_subPages = new Hashtable();

		Object o[] = { comp, mimeType };

		_subPages.put(name, o);
	}

	/**
	 * Removes an html component from this page.
	 * @param comp The component to remove
	 */
	public void remove(HtmlComponent comp) {
		if (_componentsCont != null)
			_componentsCont.remove(comp);
		if (comp instanceof HtmlPopup) {
			String sPopupScript = ((HtmlPopup) comp).getPopupScript() + "\n";
			int iPopIndex = _popupscript.indexOf(sPopupScript);
			if (iPopIndex >= 0)
				_popupscript = _popupscript.substring(0, iPopIndex) + _popupscript.substring(iPopIndex + sPopupScript.length());
		}
	}

	/**
	 * This method removes a listener from the list of listeners that will be notified when a page event is fired.
	 */
	public void removePageListener(PageListener p) {
		if (_listeners == null)
			return;

		for (int i = 0; i < _listeners.size(); i++) {
			if (((PageListener) _listeners.elementAt(i)) == p) {
				_listeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * This method will clear out any pending events for all the components in this page. Use this method if you reset or re-retrieve a datastore in the page.
	 */
	public void resetComponents() {
		if (_componentsCont != null)
			_componentsCont.reset();
	}

	/**
	 * This method will cause a page to scroll to an anchor.
	 * @param itemName The full name of the anchor to scroll to
	 */
	public void scrollToItem(String itemName) {
		writeScript("document.location='#" + itemName + "';");
		/*       if (getBrowserType() == HtmlPage.BROWSER_NETSCAPE) {
		            writeScript("if (navigator.appVersion.substring(0,1) >= '4' && navigator.appVersion.substring(0,1) < '5' ) {y = document.anchors[\"" + itemName + "\"].y; window.scrollTo(0,y);}");
		            writeScript("if (navigator.appVersion.substring(0,1) >= '5') { y = document.anchors[\"" + itemName + "\"].offsetTop; window.scrollTo(0,y); }");
		        } else {
		            writeScript("if (navigator.appVersion.substring(0,1) >= '4') { document.anchors(\"" + itemName + "\").scrollIntoView(true);}");
		        }*/
	}

	/**
	 * This method sets the active link color for the page
	 */
	public void setActiveLinkColor(String value) {
		_activeLinkColor = value;
	}

	/**
	 * This method sets the  url of a background image for the page
	 */
	public void setBackground(String value) {
		_background = value;
	}

	/**
	 * This method sets the background color for the page
	 */
	public void setBackgroundColor(String value) {
		_bgColor = value;
	}

	/**
	 * This method sets the contents of the base tag in the html head tag.
	 */
	public void setBaseTag(String value) {
		_baseTag = value;
	}

	/**
	 * Use this method to specify which button will be clicked when the enter key is pressed.
	 * @param button The button to click.
	 *  @deprecated
	 */
	public void setDefaultButton(HtmlSubmitButton button) {
		_defaultButton = button;
	}

	/**
	 * Use this method to specify the DOCTYPE of the page. This is printed as the first line of the page.
	 * @param sDocumentType String to be printed as the first line
	 */
	public void setDocumentType(String sDocumentType) {
		_pageDocType = sDocumentType;
	}

	/**
	 * This method sets the form type depending on the components in the page
	 */
	public void setFormType() {
		_formType = FORMTYPE_URLENCODED;
		Enumeration e = getComponents();
		while (e.hasMoreElements()) {
			HtmlComponent c = (HtmlComponent) e.nextElement();
			if (c instanceof HtmlFileUpload) {
				_formType = FORMTYPE_MULTIPART;
				return;
			} else if (c instanceof HtmlContainer) {
				if (setFormType((HtmlContainer) c))
					return;
			}
		}

	}

	/**
	 * This method sets the form type. Valid values are FORMTYPE_URLENCODED and FORMTYPE_MULTIPART.
	 */
	public void setFormType(int type) {
		_formType = type;
	}

	/**
	 * This method sets the form type depending on the components in the page
	 */
	private boolean setFormType(HtmlContainer cont) {
		Enumeration e = cont.getComponents();
		while (e.hasMoreElements()) {
			HtmlComponent c = (HtmlComponent) e.nextElement();
			if (c instanceof HtmlFileUpload) {
				_formType = FORMTYPE_MULTIPART;
				return true;
			} else if (c instanceof HtmlContainer) {
				if (setFormType((HtmlContainer) c))
					return true;
			}
		}

		return false;
	}

	/**
	 * This method sets whether or not the page will generate a form tag.
	 */
	public void setGenerateFormTag(boolean gen) {
		_generateForm = gen;
	}

	/**
	 * This method sets the hover link color for the page
	 */
	public void setHoverLinkColor(String value) {
		_hoverLinkColor = value;
	}

	/**
	 * This method sets the left margin for the page or -1 to clear it.
	 */
	public void setLeftMargin(int margin) {
		_leftMargin = margin;
	}

	/**
	 * This method sets the link color for the page
	 */
	public void setLinkColor(String value) {
		_linkColor = value;
	}

	/**
	 * This method sets the margin height for the page or -1 to clear it.
	 */
	public void setMarginHeight(int height) {
		_marginHeight = height;
	}

	/**
	 * This method sets the margin width for the page or -1 to clear it.
	 */
	public void setMarginWidth(int width) {
		_marginWidth = width;
	}

	/**
	 * This method sets the contents of the meta tag in the html head tag.
	 */
	public void setMetaTag(String value) {
		_metaTag = value;
	}

	/**
	 * This method sets the java script to be executed when the page is loaded.
	 */
	public void setOnLoad(String value) {
		_onLoad = value;
	}

	/**
	 * This method sets the right margin for the page or -1 to clear it.
	 */
	public void setRightMargin(int margin) {
		_rightMargin = margin;
	}

	/**
	 * This method sets the text color for the page
	 */
	public void setTextColor(String value) {
		_textColor = value;
	}

	/**
	 * This method sets the title of the page.
	 */
	public void setTitle(String title) {
		_title = title;
	}

	/**
	 * This method sets the top margin for the page or -1 to clear it.
	 */
	public void setTopMargin(int margin) {
		_topMargin = margin;
	}

	/**
	 * This method sets the visited link color for the page
	 */
	public void setVisitedLinkColor(String value) {
		_visitedLinkColor = value;
	}

	/**
	 * Sets whether the page is a WML page or not.
	 */
	public void setWMLMaintained(boolean isWmlMaintained) {
		_isWmlMaintained = isWmlMaintained;
	}

	/**
	 * Use this method to run a process in a thread. The page will be refreshed at pageRefreshInterval seconds and allow you to display a progress bar.
	 */
	public void startRunThread(PageRunThread r, int pageRefreshInterval) {
		_runThread = r;
		_runThreadRefreshInterval = pageRefreshInterval;
		Thread t = ApplicationContext.createThreadWithContextClone(r);
		t.start();
		r.setStatus(PageRunThread.STATUS_RUNNING);
	}

	/**
	 * Use this method to trim the url used in isReferedByCurrentPage(). It looks for the third '/' and a '?' and gives you back the middle
	 */
	protected String trimUrl(String url, PortletInfo info) {
		//this is a hack for a pluto application, we can tell what the real servlet URL is by looking for the first path item in the url line starting with an _.
		//It may not work in other portal implemenations
		int quesPos = url.indexOf("/_");
		int slashPos = 0;
		int slashCount = 0;
		for (int i = 0; i < url.length(); i++) {
			if (url.charAt(i) == '/') {
				slashCount++;
			}

			if (slashCount == 3) {
				slashPos = i;
				break;
			}
		}

		if (quesPos > -1) {
			url = url.substring(slashPos, quesPos);
		} else {
			url = url.substring(slashPos);
		}

		int semipos = url.indexOf(";");
		if (semipos > -1)
			url = url.substring(0, semipos);

		return url;
	}
	/**
	 * Use this method to trim the url used in isReferedByCurrentPage(). It looks for the third '/' and a '?' and gives you back the middle
	 */
	protected String trimUrl(String url) {
		int quesPos = url.indexOf('?');
		int slashPos = 0;
		int slashCount = 0;
		for (int i = 0; i < url.length(); i++) {
			if (url.charAt(i) == '/') {
				slashCount++;
			}

			if (slashCount == 3) {
				slashPos = i;
				break;
			}
		}

		if (quesPos > -1) {
			url = url.substring(slashPos, quesPos);
		} else {
			url = url.substring(slashPos);
		}

		int semipos = url.indexOf(";");
		if (semipos > -1)
			url = url.substring(0, semipos);

		return url;
	}

	/**
	 * This method was created in VisualAge.
	 * @param script java.lang.String
	 */
	public void writeScript(String script) {
		if (_script == null)
			_script = script;
		else
			_script += script;
	}

	/**
	 * This method will remove all property expressions from the list
	 */
	public void clearPropertyExpressions() {
		_propertyExpressions.setSize(0);
	}

	/**
	 * This method will return false if the page is to skip URL encoding for links within it
	 */
	public boolean getEncodeURLs() {
		return _encodeURLs;
	}

	/**
	 * This method sets whether or not links in the page will use URL Encoding (rewriting the URL with the sessionid for devices that don't support cookies)
	 */
	public void setEncodeURLs(boolean encodeURLs) {
		_encodeURLs = encodeURLs;
	}

	/**
	  * Return <code>true</code> if there is a transaction token stored in
	  * the user's current session, and the value submitted as a request
	  * parameter with this action matches it.  Returns <code>false</code>
	  * under any of the following circumstances:
	  * <ul>
	  * <li>No session associated with this request</li>
	  * <li>No transaction token saved in the session</li>
	  * <li>No transaction token included as a request parameter</li>
	  * <li>The included transaction token value does not match the
	  *     transaction token in the user's session</li>
	  * </ul>
	  *
	  */
	public boolean isTokenValid() {

		// Retrieve the saved transaction token from our session
		HttpSession session = getSession();
		if (session == null)
			return (false);
		String saved = (String) session.getAttribute(TRANSACTION_TOKEN_KEY);
		if (saved == null)
			return (false);

		// Retrieve the transaction token included in this request
		if (_token == null)
			return (false);

		// Do the values match?
		return (saved.equals(_token));

	}

	/**
	 * Save a new transaction token in the user's current session, creating
	 * a new session if necessary.
	 *
	 */
	public void saveToken() {

		String token = generateToken();
		if (token != null)
			getSession().setAttribute(TRANSACTION_TOKEN_KEY, token);

	}

	/**
	 * Reset the saved transaction token in the user's session.  This
	 * indicates that transactional token checking will not be needed
	 * on the next request that is submitted.
	 *
	 */
	public void resetToken() {

		HttpSession session = getSession();
		if (session == null)
			return;
		session.removeAttribute(TRANSACTION_TOKEN_KEY);

	}
	/**
	 * Generate a new transaction token, to be used for enforcing a single
	 * request for a particular transaction.
	 *
	 */
	protected String generateToken() {

		HttpSession session = getSession();
		try {
			byte id[] = session.getId().getBytes();
			byte now[] = new Long(System.currentTimeMillis()).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			return (toHex(md.digest()));
		} catch (IllegalStateException e) {
			return (null);
		} catch (NoSuchAlgorithmException e) {
			return (null);
		}

	}
	/**
	 * Convert a byte array to a String of hexadecimal digits and return it.
	 *
	 * @param buffer The byte array to be converted
	 */
	public String toHex(byte buffer[]) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buffer.length; i++)
			sb.append(Integer.toHexString((int) buffer[i] & 0xff));
		return (sb.toString());

	}

	/**
	 *  This method  will set the value for all form components on a page to null
	 */
	public void clearFormComponents() {
		Enumeration e = getComponents();
		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			if (o instanceof HtmlFormComponent)
				 ((HtmlFormComponent) o).setValue(null);
		}
	}

	/**
	* Applies the skin to the page. This method is called from the framework and should not be called directly. Instead use setSkin(Skin sk)
	*/
	public void applySkin(Skin skin, boolean doSetTheme) {
		Enumeration e = getComponents();
		while (e.hasMoreElements()) {
			HtmlComponent comp = (HtmlComponent) e.nextElement();
			applySkin(comp, skin, doSetTheme);
		}
	}

	private void applySkin(HtmlComponent comp, Skin sk, boolean doSetTheme) {
		if (doSetTheme)
			comp.setTheme(comp.getTheme());
		sk.applyAttributes(comp);
		Enumeration e = null;
		if (comp instanceof HtmlContainer)
			e = ((HtmlContainer) comp).getComponents();
		else if (comp instanceof JspContainer)
			e = ((JspContainer) comp).getComponents();
		if (e != null) {
			while (e.hasMoreElements()) {
				HtmlComponent c = (HtmlComponent) e.nextElement();
				applySkin(c, sk, doSetTheme);
			}
		}
	}

	/**
	 * Returns the url for a specified logical site map entry
	 * @param logicalName The name of the entry in the site map
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(String logicalName) {
		return getSiteMapURL(logicalName, null, true);
	}

	/**
	 * Returns the url for a specified logical site map entry
	 * @param logicalName The name of the entry in the site map
	 * @param additionalParms Any additional parameters that need to be appended to the url
	 * @param javaScriptOK True if the URL can contain javascript (for popup windows), False to just contain the location of the page/file
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(String logicalName, String additionalParms, boolean javaScriptOK) {
		SiteMap m = SiteMap.getSiteMap(getApplicationName());
		if (m == null)
			return null;
		else
			return m.getSiteMapURL(getCurrentRequest(), logicalName, additionalParms, javaScriptOK);
	}

	/**
	* Returns the url for a specified logical site map entry action
	* @param logicalName The name of the entry in the site map
	* @param actionName The name of the action for the site map entry
	* @param additionalParms Any additional parameters that need to be appended to the url
	* @param javaScriptOK True if the URL can contain javascript (for popup windows), False to just contain the location of the page/file
	* @return The absolute url to the page
	*/
	public String getSiteMapActionURL(String logicalName, String actionName, String additionalParms, boolean javaScriptOK) {
		SiteMap m = SiteMap.getSiteMap(getApplicationName());
		if (m == null)
			return null;
		else {
			String name = m.getActionEntry(logicalName, actionName);
			if (name == null)
				return null;
			else
				return m.getSiteMapURL(getCurrentRequest(), name, additionalParms, javaScriptOK);
		}
	}

	/**
	 * Returns the url for a specified logical site map entry action
	 * @param logicalName The name of the entry in the site map
	 * @param actionName The name of the action for the site map entry
	 * @return The absolute url to the page
	 */
	public String getSiteMapActionURL(String logicalName, String actionName) {
		return getSiteMapActionURL(logicalName, actionName, null, true);
	}

	/**
	 * Returns the name of a site map entry by doing a reverse lookup uisng the page's url
	 */
	public String getSiteMapEntryNameForPage() {
		SiteMap m = SiteMap.getSiteMap(getApplicationName());
		if (m == null)
			return null;
		else 
			return m.getEntryName(getCurrentRequest().getServletPath());
	}

	/**
	 * Returns the site map for the application
	 */
	public SiteMap getSiteMap() {
		return SiteMap.getSiteMap(getApplicationName());
	}
	protected JspForm getLastSubmitForm() {
		return _lastSubmitForm;
	}
	/**
	 * Portlet errors have to bubble up through so many layers and wrapped exceptions that they very often aren't recognizable to the portlet itself. Rather then throwing a portlet exception directly, you can use this method to make sure the exception bubbles up to the portlet unchanged.
	 * @param ex The exception to handle
	 * @param logEx True to log the exception to the Sofia message log
	 * @param throwEx True to throw the exception to the portal, false to just not render the portlet
	 * @param displayHTML An HTML String to display in the Portlet or null to not display anything
	 * @throws Exception
	 */
	public void handlePortletException(Exception ex, boolean logEx, boolean throwEx, String displayHTML) throws SalmonPortletException {
		PortletException pex;
		if (ex instanceof PortletException)
			pex = (PortletException) ex;
		else
			pex = new PortletException(ex);	
		_portletException = new SalmonPortletException(pex,logEx,throwEx, displayHTML);	
		throw _portletException;			
	}
	
	/**
	 * Displays an error message in the portlet instead of the content. 
	 * @param ex The message to display
	 */
	public void displayPortletException(String ex)  {
		displayPortletException(ex,Props.FONT_ERROR);
	}
	
	/**
	 * Displays an error message in the portlet instead of the contentg. 
	 * @param ex The message to display
	 * @param font The font from the properties file to use
	 */
	public void displayPortletException(String ex, String font)  {
		Props p = getPageProperties();
		String txt = p.getProperty(Props.FONT_ERROR + Props.TAG_START);
		txt += ex;
		txt += p.getProperty(Props.FONT_ERROR + Props.TAG_END); 
		_portletException = new SalmonPortletException(new PortletException(txt),false,false, txt);	
	}
	/**
	 * Returns the last portlet exception that occurred 
	 * @return
	 */
	public SalmonPortletException getPortletException() {
		SalmonPortletException ret = _portletException;
		return ret;
	}
	
	/**
	 * Clears the last portlet exception that occurred
	 */
	public void clearPortletException() {
		_portletException=null;	
	}

	   /**
	     * This method tries to get a the string parameter passed into this function from the URL.  It then checks to see if that "name" parameter is of boolean type.  The default is FALSE.
	     *
	     * @param name - name of parameter that should be used
	     *
	     * @return boolean - returns false if the parameter does not exist 
	     */
	public boolean getBooleanParameter(String name){
	    String s = getParameter(name);
	    return Util.isTrue(s);
	}

	/**
	* This method tries to get the integer value from the parameter in the URL called passed into this method.  If the value of the parameter is not an integer, a -1 value will be returned.
	*
	* @param name - name of parameter that should be used
	* @param defaultValue - value to use if the parameter does not exist
	*
	* @return int - returns the passed default value if the parameter does not exist 
	*/
	public int getIntParameter(String name, int defaultValue)	{
		int ret =  getIntParameter(name);
		
		if (ret==-1)
		{
			ret=defaultValue;
		}
		
		return ret;
	}
	   /**
	     * This method tries to get the integer value from the parameter in the URL called passed into this method.  If the value of the parameter is not an integer, a -1 value will be returned.
	     *
	     * @param name - name of parameter that should be used
	     *
	     * @return int - returns -1 if the parameter does not exist 
	     */
	public int getIntParameter(String name)	{
	    String s = getParameter(name);
	
	    try
	    {
	        return Integer.parseInt(s);
	    }
	    catch (Exception e)
	    {
	        return -1;
	    }
	}	
	/**
	 * Returns the list of applets on this page
	 */
	public Vector getApplets() {
		return _applets;	
	}		
	
	/**
	 * Add a named piece of javascript to your page for this request. If a script with that name is already on the page, it will be replaced. This method is useful for different components that want to share some javascript functions. The first component on the page can add the script and subsequent components can check if the script already exists before adding it again.
	 * @param key The name of the script
	 * @param script The script to add
	 */
	public void addScript(String name, String script) {
		if (_scriptTable == null)
			_scriptTable=new Hashtable();
		_scriptTable.put(name,script);		
	}
	/**
	 * Adds a named piece of HTML to the end of your page. If the HTML with that name is already on the page, it will be replaced. Usually used to support a named script.
	 * @param name
	 * @param HTML
	 */
	public void addHtml(String name, String html) {
		if (_htmlTable == null)
			_htmlTable=new Hashtable();
		_htmlTable.put(name,html);		
	}
	

	/**
	 * Check if a named script is part of the page.
	 * @param name The name for the script to find
	 * @return true if the script is in the page
	 */
	public boolean isScriptAdded(String name) {
		if (_scriptTable==null)
			return false;
		return _scriptTable.containsKey(name);		
	}		
	
	/**
	 * Check if a named HTML snippet is part of the page.
	 * @param name The name for the script to find
	 * @return true if the script is in the page
	 */
	public boolean isHtmlAdded(String name) {
		if (_htmlTable==null)
			return false;
		return _htmlTable.containsKey(name);		
	}	
	/**
	 * Framework method, do not call directly
	 */
	public void writeNamedScripts(Writer pw) throws IOException {
		if (_scriptTable != null) {
			Enumeration enumera=_scriptTable.elements();
			while (enumera.hasMoreElements()) {
				String script=(String)enumera.nextElement();
				pw.write(script);
				pw.write("\r\n\r\n");	
			}
			_scriptTable.clear();		
		}	
	}
	
	/**
	 * Framework method, do not call directly
	 */
	public void writeNamedHtml(Writer pw) throws IOException {
		if (_htmlTable != null) {
			Enumeration enumera=_htmlTable.elements();
			while (enumera.hasMoreElements()) {
				String html=(String)enumera.nextElement();
				pw.write(html);
			}
			_htmlTable.clear();		
		}	
	}	
	
	  /**
     * Obtains the lookup component that called this page.
     * @return
     */
    public HtmlLookUpComponent getPopupLookupComponent() {
		HtmlLookUpComponent lookupComponent = null;
		String callingController = getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER);
		if (callingController != null) {
			try {
				JspController otherCont = (JspController) getSession().getAttribute(callingController);
				if (otherCont != null) {
					lookupComponent= (HtmlLookUpComponent) otherCont.getComponent(getParameter(HtmlLookUpComponent.PARAM_LOOKUP_COMPONENT));
				}
			} catch (Exception ex) {
			}
		}
	    return lookupComponent;
	}


    /**
     * Obtains the lookup component row that called this page.
     * @return 
     */
    public int getPopupLookupComponentRow() {
		String callingController = getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER);
        int luRow = -1;
		if (callingController != null) {
			try {
				JspController otherCont = (JspController) getSession().getAttribute(callingController);
				if (otherCont != null) {
					luRow = Integer.parseInt(getParameter(HtmlLookUpComponent.PARAM_LOOKUP_ROW));
				}
			} catch (Exception ex) {
			}
		}
        return luRow;
	}
}
