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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspController.java $
//$Author: Dan $
//$Revision: 135 $
//$Modtime: 10/04/04 1:38p $
/////////////////////////

import com.salmonllc.html.HtmlApplet;
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlContainer;
import com.salmonllc.html.HtmlFileUpload;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlImage;
import com.salmonllc.html.HtmlLookUpComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlText;
import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.html.HttpServletResponseWrapper;
import com.salmonllc.jsp.tags.PageTag;
import com.salmonllc.properties.Props;
import com.salmonllc.sitemap.SiteMap;
import com.salmonllc.sql.AutoRetrieveCriteria;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.CodeGenerater;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.TwoObjectContainer;
import com.salmonllc.util.URLGenerator;
import com.salmonllc.util.Util;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * This class is the ancestor of all JSP controllers. A subclass of this object can be used to interface with a JSP Page containing Salmon custom tags. The appropriate controller class is specified in "controller" attribute of the JSP "page" custom tag. Each custom tag will be added to a Hashtable within this object. In addition, if there are any public instance variables in the page with the same name (preceeded with an underscore) and type as their corresponding tag created components they will automatically be assigned by the JSP engine. <BR> <BR> For example a tag &lt;page controller=&quot;com.jspImplementation.ControllerSubClass&quot;&gt; would attempt to create a subclass of this class named com.jspImplementation.ControllerSubClass. <BR> A tag in the page &lt;text name=&quot;text1&quot; text=&quot;Text&quot;&gt; would automatically assign a value to an instance variable in the page described as public com.salmonllc.html.HtmlText _text1;
 */

public class JspController extends HtmlPage implements Constants {

	private Hashtable _componentsHash = new Hashtable();

	private Hashtable _datastoreHash = new Hashtable();

	private JspController _secondaryController;

	private int _count = 0;

	private boolean _pageExp;

	private String _sessionKey;

	private int _refIndex = 0;

	private boolean _isInitializing = true;

	private boolean _initMethodCalled = false;

	private String _extraScript = null;

	private String _class = null;

	private transient PageContext _context;

	private Vector _propertyExpression;

	private boolean _keepOnSession = true;

	private boolean _doPostRedirected = false;

	private Vector _validators;

	private boolean _pageClearedFromSession;

	private static final String FROM_FORWARD_REQ_KEY = "$SALMONFROMFORWARDREQKEY$";

	private boolean _hideAllComponents;

	private String _onblur;

	private String _onkeydown;

	private String _removeFromQueryString;

	private String _onclick;

	private int _sessionKeepAliveMinutes = -1;

	/* Claudio Pi - 5/25/04 Added for modal popup windows */
	private String _onfocus;

    private boolean _addExpireHeaders=true;


	/**
	 * @return Returns the includeIDAfterPost flag. If true, makes each page submit include an extra pagePostSerialID parameter on the URL line. This will insure that a new page will never come from the browser's cache, but the browser back button will treat each post as a seperate page.
	 */
	public boolean isIncludeIDAfterPost() {
		return _includeIDAfterPost;
	}
	/**
	 * @param includeIDAfterPost If true, makes each page submit include an extra pagePostSerialID parameter on the URL line. This will insure that a new page will never come from the browser's cache, but the browser back button will treat each post as a seperate page.
	 */
	public void setIncludeIDAfterPost(boolean includeIDAfterPost) {
		_includeIDAfterPost = includeIDAfterPost;
	}
	private boolean _includeIDAfterPost;

	/**
	 * @author  demian
	 */
	private class PropertyExpression implements Serializable {
		String comp;

		String prop;

		String exp;

		DataStoreBuffer ds;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */
	public boolean getDoPostRedirected() {
		return _doPostRedirected;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */
	public void setDoPostRedirected(boolean dopostredirected) {
		_doPostRedirected = dopostredirected;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */
	public void assignComponentToField(String fieldName, HtmlComponent comp, boolean addToPage) {
		_componentsHash.put(fieldName, comp);
		if (comp instanceof HtmlApplet) {
			HtmlApplet applet = (HtmlApplet) comp;
			if (applet.getAppletAttributesDatasource() != null && !applet.getAppletAttributesDatasource().equals("") && fieldName.startsWith(applet.getAppletAttributesDatasource() + ":"))
				comp.setName(fieldName);
			else
				comp.setName(removeSpaces(fieldName));
		} else
			comp.setName(removeSpaces(fieldName));
		if (addToPage)
			add(comp);
		try {
			// this method trys to assign the comp to the specified field
			// recursively
			// looking at the parent until the parent == null
			// then it just assumes that the field does not exist
			String varName = computeFieldName(fieldName);
			assignToField(varName, comp, this.getClass());
			if (_secondaryController != null)
				_secondaryController.assignToField(varName, comp, _secondaryController.getClass());
			if (comp instanceof HtmlValidatorText) {
				if (_validators == null)
					_validators = new Vector();
				_validators.addElement(comp);
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("assignComponentToField", e, this);
		}
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */
	public void assignDataSourceToField(String fieldName, DataStoreBuffer ds) {
		_datastoreHash.put(fieldName, ds);
		try {
			// this method trys to assign the comp to the specified field
			// recursively
			// looking at the parent until the parent == null
			// then it just assumes that the field does not exist
			String varName = computeFieldName(fieldName);
			assignToField(varName, ds, this.getClass());
			if (_secondaryController != null)
				_secondaryController.assignToField(varName, ds, _secondaryController.getClass());
		} catch (Exception e) {
			MessageLog.writeErrorMessage("assignDataSourceToField", e, this);
		}
	}

	/**
	 * This method is called by the framework tag libraries to for the property
	 * tag and should not be called directly in your application.
	 */
	public void assignPropertyExpression(String component, String propName, String expression, DataStoreBuffer dataStore) {
		PropertyExpression propeExp = new PropertyExpression();
		propeExp.comp = component;
		propeExp.prop = propName;
		propeExp.exp = expression;
		propeExp.ds = dataStore;

		if (_propertyExpression == null)
			_propertyExpression = new Vector();
		_propertyExpression.addElement(propeExp);

	}

	private void assignToField(String fieldName, Object comp, Class fieldClass) {

		try {
			// keep checking the parent until parent is null then return
			if (fieldClass == null) {
				return;
			}

			Field f = fieldClass.getDeclaredField(fieldName);
			if (f.getType().isAssignableFrom(comp.getClass())) {
				f.set(this, comp);
			}
		} catch (NoSuchFieldException nsfe) {
			assignToField(fieldName, comp, fieldClass.getSuperclass());
		} catch (Exception e) {
			MessageLog.writeErrorMessage("assignToField", e, this);
		}
	}

	/**
	 * This method will automatically match components in the JSP to datasources
	 * in the JSP via the Datasource attribute.
	 */
	public void autoBindComponents() throws Exception {
		if (getDataSourceTable().size() == 0)
			return;

		Vector validators = null;
		Vector jspFormComps = null;
		Enumeration e = getComponentTable().keys();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			HtmlComponent comp = getComponent(name);

			if (comp instanceof JspFormDisplayBox) {
				if (jspFormComps == null)
					jspFormComps = new Vector();
				jspFormComps.add(comp);
				continue;
			}

			//special case for lookup components. They have description
			// datasources as well
			if (comp instanceof HtmlLookUpComponent) {
				String dataSource = ((HtmlLookUpComponent) comp).getDescriptionDataSource();
				if (dataSource != null) {
					int pos = dataSource.indexOf(":");
					if (pos != -1) {
						String dsName = dataSource.substring(0, pos);
						String dsExp = dataSource.substring(pos + 1);
						DataStoreBuffer ds = getDataSource(dsName);
						if (ds != null && ds.getAutoBind())     {
							((HtmlLookUpComponent) comp).setDescriptionExpression(ds, dsExp);

                        }
					}
				}
			}

			String dataSource = comp.getDataSource();
			String dsName = null;
			String dsExp = null;

			if (dataSource == null)
				continue;

			int pos = dataSource.indexOf(":");
			if (pos == -1)
				dsName = dataSource;
			else {
				dsName = dataSource.substring(0, pos);
				dsExp = dataSource.substring(pos + 1);
			}

			DataStoreBuffer ds = getDataSource(dsName);
			if (ds == null)
				continue;

			if (!ds.getAutoBind())
				continue;

			int index = -1;
			if (dsExp != null)
				index = ds.getColumnIndex(dsExp);

			if (comp instanceof HtmlFormComponent) {
				if (index != -1)
					((HtmlFormComponent) comp).setColumn(ds, index);
			} else if (comp instanceof HtmlText) {
				if (index != -1)
					((HtmlText) comp).setExpression(ds, ds.getColumnName(index), ds.getFormat(index));
				else
					((HtmlText) comp).setExpression(ds, convertExpressionOperators(dsExp));
			} else if (comp instanceof JspRaw) {
				((JspRaw) comp).setExpression(ds, dsExp);
			} else if (comp instanceof HtmlImage) {
				if (index != -1) {
					((HtmlImage) comp).setExpression(ds, ds.getColumnName(index));
					index = ds.getColumnIndex(name + "ALT");
					if (index > -1)
						((HtmlImage) comp).setAltExpression(ds, ds.getColumnName(index));
				} else
					((HtmlImage) comp).setExpression(ds, convertExpressionOperators(dsExp));
			} else if (comp instanceof JspLink) {
				((JspLink) comp).setHrefExpression(ds, convertExpressionOperators(dsExp));
			} else if (comp instanceof JspDataTable) {
				((JspDataTable) comp).setDataStoreBuffer(ds);
			} else if (comp instanceof JspList) {
				((JspList) comp).setDataStore(ds);
			} else if (comp instanceof HtmlValidatorText) {
				if (validators == null)
					validators = new Vector();
				validators.add(new TwoObjectContainer(ds, comp));
			} else if (comp instanceof HtmlLookUpComponent) {
				/*
				 * Claudio Pi (02-20-2003) Added, the following to support the
				 * datasource attribute in the LookupTag
				 */
				((HtmlLookUpComponent) comp).setColumn(ds, index);
			} else if (comp instanceof HtmlFileUpload) {
				if (index != -1) {
					if (ds.getColumnDataType(index) == DataStoreBuffer.DATATYPE_BYTEARRAY)
						((HtmlFileUpload) comp).setColumns(ds, null, null, ds.getColumnName(index));
					else if (ds.getColumnDataType(index) == DataStoreBuffer.DATATYPE_STRING)
						((HtmlFileUpload) comp).setColumns(ds, ds.getColumnName(index), null, null);
				}

			}

			comp.doBinding();

		}

		if (validators != null) {
			for (int i = 0; i < validators.size(); i++) {
				TwoObjectContainer cont = (TwoObjectContainer) validators.elementAt(i);
				HtmlValidatorText comp = (HtmlValidatorText) cont.getObject2();
				comp.setDataStore((DataStoreBuffer) cont.getObject1());
				comp.initialize();
			}
		}
		if (jspFormComps != null) {
			for (int i = 0; i < jspFormComps.size(); i++)
				((JspFormDisplayBox) jspFormComps.elementAt(i)).autoBindComponents();
		}

		if (_propertyExpression != null) {
			for (int i = 0; i < _propertyExpression.size(); i++) {
				PropertyExpression propExp = (PropertyExpression) _propertyExpression.elementAt(i);
				Hashtable ht = getComponentTable();
				HtmlComponent comp = (HtmlComponent) ht.get(propExp.comp);
				HtmlComponent parentComp = comp.getParent();
				boolean addToPage = true;
				while (parentComp != null) {
					if (parentComp instanceof JspDataTable) {
						((JspDataTable) parentComp).addPropertyExpression(comp, propExp.prop, convertExpressionOperators(propExp.exp));
						addToPage = false;
						break;
					}
					if (parentComp instanceof JspList) {
						((JspList) parentComp).addPropertyExpression(comp, propExp.prop, convertExpressionOperators(propExp.exp));
						addToPage = false;
						break;
					}

					parentComp = parentComp.getParent();
				}

				if (addToPage)
					addPropertyExpression(comp, propExp.prop, propExp.ds, convertExpressionOperators(propExp.exp));
			}
		}

		_propertyExpression = null;
	}

	/**
	 * Returns the Form Component in the Controller that is bound to a
	 * particular column in a DataStoreBuffer
	 */
	public HtmlFormComponent getBoundComponent(DataStoreBuffer dsb, String columnName) {
		/**
		 * If there is no column name available, do not bother to go through the
		 * remaining code *
		 */
		if (columnName != null && columnName.length() > 0) {

			Enumeration e = getComponentTable().elements();
			int colNo = dsb.getColumnIndex(columnName);

			if (colNo == -1)
				return null;

			while (e.hasMoreElements()) {
				Object o = e.nextElement();
				if (o instanceof HtmlFormComponent) {
					HtmlFormComponent comp = (HtmlFormComponent) o;
					if (comp.getColumnNumber() == colNo && comp.getBoundDataStore() == dsb)
						return comp;
				} else if (o instanceof HtmlLookUpComponent) {
                    //Special case for the HtmlLookupComponent
                    HtmlFormComponent comp = (HtmlFormComponent) ((HtmlLookUpComponent)o).getEditField();
                    if (comp.getColumnNumber() == colNo && comp.getBoundDataStore() == dsb)
                        return comp;
                }
			}
		}
		return null;
	}

	private String buildRedirectURL(String url, String queryString) {
		String ret = url;
		String parm = getParameter("pagePostSerialID");
		if (parm != null) {
			try {
				int i = Integer.parseInt(parm);
				_count = i + 1;
			} catch (Exception e) {
			}
		}

		if (queryString != null) {
			String sTmpQueryString = queryString;
			int iMethodIndex = sTmpQueryString.toLowerCase().indexOf("method=post");
			if (sTmpQueryString.toLowerCase().indexOf("method=post") >= 0) {
				if (iMethodIndex + 11 < queryString.length())
					sTmpQueryString = queryString.substring(0, iMethodIndex) + queryString.substring(iMethodIndex + 12);
				else
					sTmpQueryString = queryString.substring(0, iMethodIndex - 1);
			}
			if (sTmpQueryString.endsWith("&"))
				sTmpQueryString = sTmpQueryString.substring(0, sTmpQueryString.length() - 1);
			ret += "?" + sTmpQueryString;
			if (_includeIDAfterPost)
				ret += "&pagePostSerialID=" + _count++;
			if (isWMLMaintained()) {
				if (sTmpQueryString.indexOf(PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getSession())) < 0)
					ret += "&" + PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getSession());
			}
		} else {
			if (_includeIDAfterPost)
				ret += "?pagePostSerialID=" + _count++;
			if (isWMLMaintained()) {
				ret += "&" + PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getSession());
			}
		}
		return ret;
	}

	/**
	 * This method is called from the tag library when the page if finished
	 * initializing
	 */
	public void clearInitializing() {
		_isInitializing = false;
		setFormType(FORMTYPE_URLENCODED);
		Enumeration e = _componentsHash.elements();
		while (e.hasMoreElements()) {
			if (e.nextElement() instanceof HtmlFileUpload) {
				setFormType(FORMTYPE_MULTIPART);
				return;
			}
		}
	}

	/**
	 * This method will remove all instances the current page from the session.
	 * Future calls to the page will create and initialize a new page each time.
	 */
	public void clearPageFromSession() {
		String cName = _sessionKey;

		cName = "$jsp$" + cName;

		HttpSession sess = getSession();

		if (sess.getAttribute(cName) != null)
			sess.removeAttribute(cName);

		_pageClearedFromSession = true;

	}
	
	/**
	 * This method removes the pages from the session. That will force the
	 * application to reinitialize the pages the next time they are visited.
	 */
	public void clearAllPagesFromSession() {
		HttpSession sess = getSession();
		clearAllPagesFromSession(sess);
	}
	
	/**
	 * This method removes the pages from the session. That will force the
	 * application to reinitialize the pages the next time they are visited.
	 */
	public void clearAllPagesFromSession(HttpSession sess) {
		Enumeration e = sess.getAttributeNames();
		Object o = null;
		while (e.hasMoreElements()) {
			o = sess.getAttribute(e.nextElement().toString());
			if (o instanceof JspController) {
				((JspController) o).clearPageFromSession();
				e = sess.getAttributeNames();
			}
		}
	}

	/**
	 * This method removes the pages from the session except for the controller
	 * calling the method. That will force the application to reinitialize the
	 * pages the next time they are visited.
	 */
	public void clearAllPagesFromSessionButCurrent() {
		HttpSession sess = getSession();
		Enumeration e = sess.getAttributeNames();
		Object o = null;
		while (e.hasMoreElements()) {
			o = sess.getAttribute(e.nextElement().toString());
			if (o instanceof JspController && o != this) {
				((JspController) o).clearPageFromSession();
				e = sess.getAttributeNames();
			}
		}
	}

	private void putPageOnSession() {
		if (_pageClearedFromSession)
			return;

		String cName = _sessionKey;

		cName = "$jsp$" + cName;

		HttpSession sess = getSession();

		sess.setAttribute(cName, this);

	}

	private String computeFieldName(String fieldName) {
		int TO_UPPER = 1;
		int TO_LOWER = 2;
		int NO_CHANGE = 0;

		StringBuffer sb = new StringBuffer(fieldName.length() + 1);
		sb.append('_');
		int nextChange = TO_LOWER;
		for (int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			if (c == '.' || c == ' ')
				nextChange = TO_UPPER;
			else if (nextChange == TO_LOWER) {
				sb.append(Character.toLowerCase(c));
				nextChange = NO_CHANGE;
			} else if (nextChange == TO_UPPER) {
				sb.append(Character.toUpperCase(c));
				nextChange = NO_CHANGE;
			} else
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * This method will convert the expression operators used in JSP tags to
	 * normal datastore evaluator operators (ex: GREATER_THAN to >)
	 */
	public String convertExpressionOperators(String expression) {
		String retStr = expression;
		//ilev 2004-08-30 changed the order of conversion as the
		// greater_than_or_equals was overwritten
		//by equals
		//retStr = Util.replaceString(retStr, EQUALS, "==", 1, -1);
		retStr = Util.replaceString(retStr, NOT_EQUAL, "!=", 1, -1);
		//ilev 2004-08-30 changed the order of conversion as the
		// greater_than_or_equals was overwritten
		//by greater_than
		//retStr = Util.replaceString(retStr, GREATER_THAN, OPER_GREATER_THAN,
		// 1, -1);
		//retStr = Util.replaceString(retStr, GREATER_THAN_OR_EQUALS,
		// OPER_GREATER_THAN_OR_EQUALS, 1, -1);
		retStr = Util.replaceString(retStr, GREATER_THAN_OR_EQUALS, OPER_GREATER_THAN_OR_EQUALS, 1, -1);
		retStr = Util.replaceString(retStr, GREATER_THAN, OPER_GREATER_THAN, 1, -1);
		//end changed by ilev

		//ilev 2004-08-30 changed the order of conversion as the
		// less_than_or_equals was overwritten
		//by less_than
		//retStr = Util.replaceString(retStr, LESS_THAN, OPER_LESS_THAN, 1,
		// -1);
		//retStr = Util.replaceString(retStr, LESS_THAN_OR_EQUALS,
		// OPER_LESS_THAN_OR_EQUALS, 1, -1);
		retStr = Util.replaceString(retStr, LESS_THAN_OR_EQUALS, OPER_LESS_THAN_OR_EQUALS, 1, -1);
		retStr = Util.replaceString(retStr, LESS_THAN, OPER_LESS_THAN, 1, -1);
		//end changed by ilev

		retStr = Util.replaceString(retStr, EQUALS, "==", 1, -1);

		return retStr;

	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public synchronized int doGet(HttpServletRequest req, boolean pre) throws Exception {
		//do the autoretrieves
		if (pre) {
			Enumeration e = getDataSourceTable().elements();
			while (e.hasMoreElements()) {
				DataStoreBuffer b = (DataStoreBuffer) e.nextElement();
				AutoRetrieveCriteria crit = b.getAutoRetrieveCriteria();
				AutoRetrieveCriteria gCrit = null;
				if (b instanceof DataStore)
					gCrit = ((DataStore) b).getGroupAutoRetrieveCriteria();

				boolean critChanged = false;

				if (crit != null) {
					crit.setParms(req);
					critChanged = crit.haveParmsChanged();
				}

				if (gCrit != null) {
					gCrit.setParms(req);
					if (!critChanged)
						critChanged = gCrit.haveParmsChanged();
				}

				if (b.getAutoRetrieve() != DataStoreBuffer.AUTORETRIEVE_NEVER) {
					if (!isReferredByCurrentPage()) {
						doAutoRetrieve(b);
					} else if (b.getAutoRetrieve() == DataStoreBuffer.AUTORETRIEVE_ONCHANGE && critChanged) {
						doAutoRetrieve(b);
					}
				}

			}
		}

		// SR & DD 07-15-2002
		// calling clear submit to fix possible dirty submit flag on a page that
		// was previously submitted.
		if (getTopContainer() != null && pre)
			getTopContainer().clearSubmit();

		//notify the listeners
		int retVal = 0;
		if (!notifyListeners(GET_EVENT, pre))
			retVal = Tag.SKIP_PAGE;
		else {
			if (pre)
				processPropertyExpressions();
			retVal = Tag.EVAL_BODY_INCLUDE;
		}

		if (!pre) {
			clearCurrentPageReferer();
			if (isRequestFromPortlet())
				getPortletInfo().setFromPost(false);
			if (!_keepOnSession)
				clearPageFromSession();
			else
				putPageOnSession();
		}

		return retVal;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public synchronized int doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
		setCurrentRequest(req);
		setCurrentResponse(res);

		/*
		 * We need the token value in the processparms(), submit performed
		 * events...
		 */
		_token = getCurrentRequest().getParameter(TOKEN_KEY);

		processParms(false);

		//check if the page expired
		String ref[] = getCurrentRequest().getParameterValues("Page_refIndex_hidden");

		boolean pageExp = false;
		if (ref != null) {
			int count = -1;
			try {
				count = Integer.parseInt(ref[0]);
			} catch (Exception e) {
			}
			pageExp = (count != _refIndex);
		}
		setPageExpired(pageExp);

		//get the redirect url
		String url = getPageUrl(req);
		String queryString = getQueryString(req);
		_removeFromQueryString = null;
		url = buildRedirectURL(url, queryString);

		com.salmonllc.html.HttpServletResponseWrapper w = (com.salmonllc.html.HttpServletResponseWrapper) getCurrentResponse();
		if (!w.getRedirectSent() && !getDisableRedirect() && !getForwardPerformed() && (req.getAttribute(FROM_FORWARD_REQ_KEY) == null)) {
			w.sendRedirect(url, false);
			_doPostRedirected = true;
		}

		if (_keepOnSession)
			putPageOnSession();

		if (getForwardPerformed())
			return Tag.SKIP_PAGE;
		else
			return Tag.EVAL_BODY_INCLUDE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlPageBase#loadProperties()
	 */
	public void loadProperties() {
		super.loadProperties();
		Props p = getPageProperties();
		setSessionKeepAliveMinutes(p.getIntProperty(Props.SYS_SESSION_KEEP_ALIVE_MINUTES));
		setIncludeIDAfterPost(p.getBooleanProperty(Props.SYS_INCLUDE_ID_AFTER_POST,false));
	}
	/**
	 * Generate session keep alive html
	 * 
	 * @throws IOException
	 */
	public void generateSessionKeepAliveHtml(JspWriter p) throws IOException {
		int clientTimeoutMinutes = _sessionKeepAliveMinutes;
		int clientTimeoutSeconds = _sessionKeepAliveMinutes * 60;
		String url = getCurrentResponse().encodeURL(getPageURLNoQueryString() + "?autoPing=true&sessionKeepAlive=true");
		HttpSession sess = getSession();
		int max = sess.getMaxInactiveInterval();
		long clientRefreshTime = (max / 2) * 1000;
		long secondsSinceFirstLoad = 0;
		boolean doRefresh = true;
		if (getCurrentRequest().getParameter("autoPing") == null) {
			sess.setAttribute("com.salmonllc.jsp.JspController.autoPing", new Long(sess.getLastAccessedTime()));
			if (clientTimeoutMinutes == -1)
				doRefresh = false;
			if (clientTimeoutSeconds > 0 && clientTimeoutSeconds <= max)
				doRefresh = false;
		} else {
			Long l = (Long) sess.getAttribute("com.salmonllc.jsp.JspController.autoPing");
			if (l == null) {
				l = new Long(sess.getLastAccessedTime());
				sess.setAttribute("com.salmonllc.jsp.JspController.autoPing", l);
			}
			secondsSinceFirstLoad = (System.currentTimeMillis() - l.longValue()) / 1000;
			if (clientTimeoutMinutes > 0)
				if (clientTimeoutSeconds <= max)
					doRefresh = false;
				else if (secondsSinceFirstLoad >= (clientTimeoutSeconds - max))
					doRefresh = false;
		}
		if (doRefresh) {
			p.println("<body onload=\"setTimeout('reload()'," + clientRefreshTime + ")\">");
			p.println("<script language=\"javascript\">");
			p.println("function reload() {");
			p.println(" clearTimeout();");
			p.println("	document.location='" + url + "';");
			p.println("}");
			p.println("</script>");
			p.println("Session ID is:" + sess.getId());
			p.println("<br>Auto refresh in:" + clientRefreshTime / 60000f + " minutes");
			if (clientTimeoutMinutes > 0)
				p.println("<br>Session Timeout in:" + ((clientTimeoutSeconds - secondsSinceFirstLoad) / 60) + " minutes");
			p.println("</body>");
		} else {
			p.println("<body>");
			p.println("Session ID is:" + sess.getId());
			p.println("<br>No more auto refreshes");
			if (clientTimeoutMinutes > 0)
				p.println("<br>Session Timeout in:" + (sess.getMaxInactiveInterval() / 60) + " minutes");
			p.println("</body>");
		}
		p.flush();

	}

	/**
	 * This method generates the Html for the body tag.
	 */
	public void generateBodyHtml(JspWriter p) throws IOException {
		StringBuffer out = new StringBuffer("<body");
		if (getActivelinkColor() != null)
			out.append(" alink=\"" + getActivelinkColor() + "\"");
		if (getBackground() != null)
			out.append(" background=\"" + getTranslatedFromSiteMapURL(getBackground()) + "\"");
		if (getBackgroundColor() != null)
			out.append(" bgcolor=\"" + getBackgroundColor() + "\"");
		if (getStyle() != null)
			out.append(" class=\"" + getStyle() + "\"");
		if (getLeftMargin() != -1)
			out.append(" leftmargin=\"" + getLeftMargin() + "\"");
		if (getLinkColor() != null)
			out.append(" link=\"" + getLinkColor() + "\"");
		if (getMarginWidth() != -1)
			out.append(" marginwidth=\"" + getMarginWidth() + "\"");
		if (getMarginHeight() != -1)
			out.append(" marginheight=\"" + getMarginHeight() + "\"");
		if (_onblur != null)
			out.append(" onblur=\"" + _onblur + "\"");
		if (_onkeydown != null)
			out.append(" onkeydown=\"" + _onkeydown + "\"");
		out.append(" onload=\"" + getOnLoadFunction() + "\"");
		if (getRightMargin() != -1)
			out.append(" rightmargin=\"" + getRightMargin() + "\"");
		if (getTextColor() != null)
			out.append(" text=\"" + getTextColor() + "\"");
		if (getTopMargin() != -1)
			out.append(" topmargin=\"" + getTopMargin() + "\"");
		if (getVisitedLinkColor() != null)
			out.append(" vlink=\"" + getVisitedLinkColor() + "\"");
		if (_onclick != null)
			out.append(" onclick=\"" + _onclick + "\"");

		/* Claudio Pi - 5/25/04 Added for modal popup windows */
		if (_onfocus != null)
			out.append(" onfocus=\"" + _onfocus + "\"");

		out.append(">");

		if (_sessionKeepAliveMinutes > -1) {
			String pageURL = getPageURLNoQueryString();
			p.print("<iframe style=\"width:0px; height:0px; border:0px;\" src=\"" + pageURL + "?sessionKeepAlive=true\"></iframe>");
		}
		p.print(out.toString());

	}

	private String getTranslatedFromSiteMapURL(String url) {
		if (url == null)
			return url;
		if (url.length() == 0)
			return url;
		if (!(url.charAt(0) == '%'))
			return url;
		int split = url.indexOf('/');
		if (split == -1)
			return url;
		String key = url.substring(1, split);
		if (getSiteMapURL(key) != null)
			key = getSiteMapURL(key);
		String rest = url.substring(split);
		return key + rest;
	}

	/**
	 * This method will print a list of all the HtmlComponents in the page to
	 * the print writer
	 */
	public void generateCode(PrintWriter pw, String controllerName) throws IOException {
		CodeGenerater.generateCode(pw, getComponentTable(), this, controllerName);
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void generateScriptHtml(JspWriter p) throws java.lang.Exception {
		if (_script != null) {
			p.println(_script);
			_script = null;
		}
		if (_extraScript != null) {
			p.print(_extraScript);
			if (!_extraScript.endsWith(";"))
				p.print(";");
			p.println();
		}
	}

	/**
	 * This returns a component in the page based on its name
	 */

	public HtmlComponent getComponent(String name) {
		if (name == null)
			return null;
		else
			return (HtmlComponent) _componentsHash.get(name);
	}

	/**
	 * This method returns a Hashtable of HtmlComponents keyed by the component
	 * name
	 */

	public Hashtable getComponentTable() {
		return _componentsHash;
	}

	/**
	 * This method will return an enoumeration of DataStoreBuffers for of all
	 * the datasources tags in the page.
	 */
	public Enumeration getDataSources() {
		return _datastoreHash.elements();
	}

	/**
	 * This returns a datasource in the page based on its name
	 */

	public DataStoreBuffer getDataSource(String name) {
		if (name == null)
			return null;
		else
			return (DataStoreBuffer) _datastoreHash.get(name);
	}

	public DataStoreBuffer getDataSource(Class c) {
		Enumeration e = getDataSourceTable().elements();
		while (e.hasMoreElements()) {
			DataStoreBuffer ds = (DataStoreBuffer) e.nextElement();
			if (c.isInstance(ds))
				return ds;
		}
		return null;
	}

	/**
	 * This method returns a Hashtable of DataStoreBuffers keyed by the
	 * component name
	 */

	public Hashtable getDataSourceTable() {
		return _datastoreHash;
	}

	/**
	 * This method should not be called directly. It is public so it can be
	 * called from the InputTag class.
	 * 
	 * @return int
	 */
	public int getRefIndex() {
		return _refIndex;
	}

	/**
	 * This method should not be called directly. It is public so it can be
	 * called from the PagrTag class.
	 */
	public void incrementRefIndex() {
		_refIndex++;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public String getOnLoadFunction() {
		return "pageOnLoad()";
	}

	/**
	 * Returns the context object for the current page
	 */
	public PageContext getPageContext() {
		return _context;
	}

	private String getPageUrl(HttpServletRequest req) {
		String url = "";
		if (req.getScheme().equals("https"))
			url = URLGenerator.generateSecureServerURL(req);
		else
			url = URLGenerator.generateServerURL(req);
		String baseURL = URLGenerator.generateServletBaseURL(req);
		if (baseURL != null && baseURL.length() > 0)
			baseURL += "/";
		else
			baseURL = "";
		url += "/" + baseURL + getPageName();
		if (getSubPageName() != null)
			url += "-" + getSubPageName();
		String path = req.getPathInfo();
		if (path != null) {
			int ndx = path.lastIndexOf("/" + url + "/");
			if (ndx > 0 && !path.endsWith(url)) {
				ndx += url.length() + 2;
				int pos = ndx + url.length() + 2;
				url = "../" + url + "/" + path.substring(ndx);
				while ((pos = path.indexOf("/", pos)) != -1) {
					url = "../" + url;
					pos = pos + 1;
				}
			}
		}
		return url;
	}

	private String getQueryString(HttpServletRequest req) {
		String queryString = req.getQueryString();
		if (queryString != null) {
			int index = queryString.indexOf("pagePostSerialID=");
			if (index > -1) {
				if (index > 0)
					index--;
				queryString = queryString.substring(0, index);
				if (queryString.equals(""))
					queryString = null;
			}

			if (queryString != null) {
				index = queryString.indexOf("iFrameSubmitFormParms=true");
				if (index > -1) {
					if (index > 0)
						index--;
					queryString = queryString.substring(0, index);
					if (queryString.equals(""))
						queryString = null;
				}
			}
			if (_removeFromQueryString != null && queryString != null) {
				StringTokenizer st = new StringTokenizer(_removeFromQueryString, ",");
				while (st.hasMoreTokens()) {
					String tok = st.nextToken();
					index = queryString.indexOf(tok + "=");
					if (index > -1) {
						String leftSide = queryString.substring(0, index);
						String rightSide = "";
						index = queryString.indexOf("&", index + 1);
						if (index != -1)
							rightSide = queryString.substring(index + 1);
						queryString = leftSide + rightSide;
					}
				}
				if (queryString.equals(""))
					queryString = null;
				//_removeFromQueryString = null;
			}
		}
		return queryString;
	}

	/**
	 * This method returns a secondary controller if there is more than one
	 * associated with the page or null if there isn't.
	 */

	public JspController getSecondaryController() {
		return _secondaryController;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public String getSessionKey() {
		return _sessionKey;
	}

	/**
	 * Gets the sytle attribute for the page
	 */
	public String getStyle() {
		return _class;
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void initialize() throws Exception {
	}

	/**
	 * Called from the framework, do not call directly
	 */
	public void initializeContainers() throws Exception {
		Enumeration en = getComponentTable().elements();
		while (en.hasMoreElements()) {
			HtmlComponent comp = (HtmlComponent) en.nextElement();
			if (comp instanceof JspContainer) {
				// Assign components defined within JSP Container
				JspContainer container = (JspContainer) comp;
				if (container.getDoInit())
					initializeContainerComponents(container, container);
				container.initialize();
			}
		}

	}

	private void initializeContainerComponents(JspContainer top, JspContainer cont) {
		Enumeration enumera = cont.getComponents();
		while (enumera.hasMoreElements()) {
			HtmlComponent comp = (HtmlComponent) enumera.nextElement();
			String nameOfComponent = comp.getName();
			String fieldName = "_" + nameOfComponent;
			assignToContainerField(top, fieldName, comp, top.getClass());

			if (comp instanceof JspContainer)
				initializeContainerComponents(top, (JspContainer) comp);
		}
		Hashtable dst = getDataSourceTable();
		enumera = dst.keys();
		while (enumera.hasMoreElements()) {
			String key = (String) enumera.nextElement();
			DataStoreBuffer dsb = (DataStoreBuffer) dst.get(key);
			String fieldName = "_" + key;
			assignToContainerField(top, fieldName, dsb, top.getClass());
		}
	}

	private void assignToContainerField(JspContainer cont, String fieldName, Object comp, Class fieldClass) {
		try {
			// keep checking the parent until parent is null then return
			if (fieldClass == null)
				return;

			Field f = fieldClass.getDeclaredField(fieldName);
			if (f.getType().isAssignableFrom(comp.getClass())) {
				f.set(cont, comp);
			}
		} catch (NoSuchFieldException nsfe) {
			assignToContainerField(cont, fieldName, comp, fieldClass.getSuperclass());
		} catch (Exception e) {
			MessageLog.writeErrorMessage("assignToContainerField", e, this);
		}
	}

	/**
	 * This method will indicate whether the page submitting a request is the
	 * current one or has been retrieved from the browsers cache
	 */
	public boolean isExpired() {
		return _pageExp;
	}

	/**
	 * This method returns true if the controller's initialize method has been
	 * called
	 */
	public boolean isInitialized() {
		return _initMethodCalled;
	}

	/**
	 * This method returns whether or not the page is currently initializing
	 * (It's components are being created).
	 */
	public boolean isInitializing() {
		return _isInitializing;
	}

	protected boolean notifyListeners(int event, boolean pre) throws Exception {
		if (!super.notifyListeners(event, pre))
			return false;

		if (_secondaryController != null)
			return _secondaryController.notifyListeners(event, pre);

		return true;

	}

	/**
	 * This method will print a list of all the HtmlComponents in the page to
	 * the print writer
	 */
	public void printVars(PrintWriter p) throws IOException {
		try {
			//create a new data store
			DataStore ds = new DataStore();
			ds.addBucket("col", DataStoreBuffer.DATATYPE_STRING);
			ArrayList containers = new ArrayList();
			ArrayList containerElements = new ArrayList();
			Hashtable com = getComponentTable();
			Enumeration en = com.keys();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				Object o = com.get(key);
				if (o instanceof JspContainer) {
					if (((JspContainer) o).getDoInit()) {
						containers.add(key);
						containerElements.add(new ArrayList());
					}
				}
			}

			//print the visual components
			en = com.keys();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String fieldName = computeFieldName(key);
				Object obj = com.get(key);
				if (!isFieldInParent(fieldName) && !isObjectInContainer((HtmlComponent) obj, containers, containerElements, com)) {
					ds.insertRow();
					ds.setString("col", obj.getClass().getName() + " " + fieldName + ";");
				}
			}
			ds.sort("col", DataStoreBuffer.SORT_ASC);
			if (ds.getRowCount() > 0)
				p.println("<BR>//Visual Components<BR>");

			for (int i = 0; i < ds.getRowCount(); i++) {
				p.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ");
				p.println(ds.getString(i, "col"));
				p.println("<BR>");
			}

			//print out any components in specific containers
			for (int i = 0; i < containers.size(); i++) {
				ds.reset();
				String contName = (String) containers.get(i);
				ArrayList l = (ArrayList) containerElements.get(i);
				for (int j = 0; j < l.size(); j++) {
					HtmlComponent obj = (HtmlComponent) l.get(j);
					ds.insertRow();
					ds.setString("col", obj.getClass().getName() + " " + computeFieldName(obj.getName()) + ";");
				}
				if (ds.getRowCount() > 0) {
					ds.sort("col", DataStoreBuffer.SORT_ASC);

					p.println("<BR>//Visual Components in Container: " + contName);
					p.println("<BR>");
					for (int j = 0; j < ds.getRowCount(); j++) {
						p.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ");
						p.println(ds.getString(j, "col"));
						p.println("<BR>");
					}
				}
			}

			//print the data source components
			ds.reset();
			com = getDataSourceTable();
			en = com.keys();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String fieldName = computeFieldName(key);
				if (!isFieldInParent(fieldName)) {
					Object obj = com.get(key);
					ds.insertRow();
					ds.setString("col", obj.getClass().getName() + " " + computeFieldName(key) + ";");
				}
			}
			ds.sort("col", DataStoreBuffer.SORT_ASC);
			if (ds.getRowCount() > 0) {
				p.println("<BR>");
				p.println("//DataSources<BR>");
			}
			for (int i = 0; i < ds.getRowCount(); i++) {
				p.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ");
				p.println(ds.getString(i, "col"));
				p.println("<BR>");
			}

			//print the datasource column constants

			com = getDataSourceTable();
			en = com.keys();
			if (en.hasMoreElements())
				p.println("<BR>//DataSource Column Constants<BR>");
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				DataStoreBuffer buf = (DataStoreBuffer) com.get(key);
				key = "public static final String " + key.toUpperCase() + "_";
				int count = buf.getColumnCount();
				for (int i = 0; i < count; i++) {
					String colName = buf.getColumnName(i);
					StringBuffer contName = new StringBuffer(colName.length());
					for (int j = 0; j < colName.length(); j++) {
						char c = colName.charAt(j);
						if (c == '.')
							c = '_';
						else
							c = Character.toUpperCase(c);
						contName.append(c);
					}
					p.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
					p.print(key);
					p.print(contName.toString());
					p.print("=&quot;");
					p.print(colName);
					p.print("&quot;;<BR>");
				}
				p.println("<BR>");
			}
		} catch (DataStoreException e) {
		}

	}

	private boolean isFieldInParent(String fieldName) {
		Class c = getClass();
		return isFieldInParent(c, fieldName, "com.salmonllc.jsp.JspController");
	}

	private boolean isFieldInParent(Class c, String fieldName, String testClass) {
		if (c.getName().equals(testClass))
			return false;

		while (true) {
			c = c.getSuperclass();
			if (c.getName().equals(testClass))
				return false;
			try {
				c.getField(fieldName);
				return true;
			} catch (Exception e) {
			}
		}
	}

	private boolean isObjectInContainer(HtmlComponent comp, ArrayList containers, ArrayList containerElements, Hashtable comps) {
		for (int i = 0; i < containers.size(); i++) {
			String key = (String) containers.get(i);
			JspContainer cont = (JspContainer) comps.get(key);
			if (cont.isComponentInContainer(comp)) {
				ArrayList list = (ArrayList) containerElements.get(i);
				String fieldName = computeFieldName(comp.getName());
				if (!isFieldInParent(cont.getClass(), fieldName, "com.salmonllc.jsp.JspContainer"))
					list.add(comp);
				return true;
			}
		}
		return false;
	}

	private String removeSpaces(String fieldName) {
		StringBuffer sb = new StringBuffer(fieldName.length());
		for (int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			if (c != '.' && c != ' ')
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * This method can be used to replace a component in the page with a
	 * different Html Component. For example a place holder component can be put
	 * into the correct position in the JSP page and then replaced at run time
	 * with a different one. The Html Generated by the replaced component will
	 * then display in the page.
	 */

	public void replaceComponent(String name, HtmlComponent newComponent) {
		if (_componentsHash.containsKey(name)) {

			HtmlComponent objToReplace = (HtmlComponent) _componentsHash.get(name);
			HtmlComponent parentObj = objToReplace.getParent();

			if (parentObj != null) {
				if (parentObj instanceof HtmlContainer)
					((HtmlContainer) parentObj).replaceComponent(newComponent, objToReplace);
				else if (parentObj instanceof JspContainer)
					((JspContainer) parentObj).replaceComponent(newComponent, objToReplace);
			}

			_componentsHash.put(name, newComponent);
		}
	}

	/**
	 * This method can be used to replace a datasource in the page with a
	 * different DataStoreBuffer.
	 */
	public void replaceDataSource(String name, DataStoreBuffer newDs) {
		if (_datastoreHash.containsKey(name)) {
			_datastoreHash.put(name, newDs);
		}
	}

	/**
	 * This method will cause the browser to reload this page;
	 */
	public void sendPageRedirect() throws IOException {
		sendPageRedirect(null);
	}

	/**
	 * This method will cause the browser to reload this page and scroll to an
	 * anchor;
	 */
	public void sendPageRedirect(String anchor) throws IOException {
		//get the redirect url
		String url = getPageUrl(getCurrentRequest());
		String queryString = getQueryString(getCurrentRequest());
		url = buildRedirectURL(url, queryString);
		if (anchor != null) {
			url += "#" + anchor;
		}

		//do the redirect
		getCurrentResponse().sendRedirect(url);
	}

	/**
	 * This returns the URL for the page
	 */
	public String getPageURL() {
		String url = getPageUrl(getCurrentRequest());
		String queryString = getQueryString(getCurrentRequest());
		if (queryString == null)
			return url;
		else
			return url + "?" + queryString;
	}

	/**
	 * This returns the URL for the page not including the query string
	 */
	public String getPageURLNoQueryString() {
		return getPageUrl(getCurrentRequest());
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setApplicationName(String name) {
		super.setApplicationName(name);
		if (_secondaryController != null)
			_secondaryController.setApplicationName(name);
	}

	private void setComponentTable(Hashtable tab) {
		_componentsHash = tab;
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setCurrentRequest(HttpServletRequest r) {
		super.setCurrentRequest(r);
		if (_secondaryController != null)
			_secondaryController.setCurrentRequest(getCurrentRequest());
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setCurrentResponse(HttpServletResponse r) {
		super.setCurrentResponse(r);
		if (_secondaryController != null)
			_secondaryController.setCurrentResponse(getCurrentResponse());
	}

	private void setDataSourceTable(Hashtable tab) {
		_datastoreHash = tab;
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setInitialized() {
		_initMethodCalled = true;
	}

	/**
	 * This method sets some javascript to be executed each time the page loses
	 * focus.
	 */
	public void setOnBlur(String script) {
		_onblur = script;
	}

	/**
	 * This method sets some javascript to be executed each time a key is
	 * pressed.
	 */
	public void setOnKeydown(String script) {
		_onkeydown = script;
	}

	/**
	 * This method sets some javascript to be executed each time the page is
	 * clicked on.
	 */
	public void setOnClick(String script) {
		_onclick = script;
	}

	/**
	 * This method returns some javascript to be executed each time the page is
	 * clicked on.
	 */
	public String getOnClick() {
		return _onclick;
	}

	/**
	 * This method sets some javascript to be executed each time the page loads.
	 */
	public void setOnLoad(String script) {
		_extraScript = script;
	}

	/**
	 * 
	 * This method sets some javascript to be executed each time the page gets
	 * the focus.
	 */
	public void setOnFocus(String script) {
		/* Claudio Pi - 5/25/04 Added for modal popup windows */
		_onfocus = script;
	}

	public void setOrigApplicationName(String name) {
		super.setOrigApplicationName(name);
		if (_secondaryController != null)
			_secondaryController.setOrigApplicationName(name);
	}

	/**
	 * Sets the context object for the current page. Generally, this method is
	 * called by other classes in the framework and does not need to be called
	 * directly.
	 */
	public void setPageContext(PageContext cont) {
		_context = cont;
		if (_secondaryController != null)
			_secondaryController.setPageContext(cont);
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void setPageExpired(boolean exp) {
		_pageExp = exp;
		if (_secondaryController != null)
			_secondaryController.setPageExpired(exp);
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void setPageName(String name) {
		super.setPageName(name);
		if (_secondaryController != null)
			_secondaryController.setPageName(name);
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void setSecondaryController(JspController cont) {
		_secondaryController = cont;
		_secondaryController.setComponentTable(_componentsHash);
		_secondaryController.setDataSourceTable(_datastoreHash);
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setServerURL(String url) {
		super.setServerURL(url);
		if (_secondaryController != null)
			_secondaryController.setServerURL(url);
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setServletBaseURL(String url) {
		super.setServletBaseURL(url);
		if (_secondaryController != null)
			_secondaryController.setServletBaseURL(url);
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setSession(HttpSession s) {
		super.setSession(s);
		if (_secondaryController != null)
			_secondaryController.setSession(s);
	}

	/**
	 * This method is called from the tag library to indicate that the page if
	 * fully initialized
	 */
	public void setSessionExpired(boolean exp) {
		super.setSessionExpired(exp);
		if (_secondaryController != null)
			_secondaryController.setSessionExpired(exp);
	}

	/**
	 * This method should not be called directly. It public so it can be called
	 * from com.salmonllc.jsp.tags.
	 */

	public void setSessionKey(String key) {
		_sessionKey = key;
		if (_secondaryController != null)
			_secondaryController.setSessionKey(key);
	}

	/**
	 * Sets the sytle attribute for the page
	 */
	public void setStyle(String style) {
		_class = style;
	}

	/**
	 * Set whether or not a page will be stored on the session. Pages not stored
	 * on the session will not persist from one request to the next, but will be
	 * more memory efficient
	 */
	public void setKeepOnSession(boolean keep) {
		_keepOnSession = keep;
	}

	/**
	 * Get whether or not a page will be stored on the session. Pages not stored
	 * on the session will not persist from one request to the next, but will be
	 * more memory efficient
	 */
	public boolean getKeepOnSession() {
		return _keepOnSession;
	}

	/**
	 * Returns a vector of HtmlValidatorText components in the page or null if
	 * there aren't any
	 */
	public Vector getValidators() {
		return _validators;
	}

	private void doAutoRetrieve(DataStoreBuffer b) throws Exception {
		b.autoRetrieve();
		b.gotoFirst();
		if (_validators != null) {
			for (int i = 0; i < _validators.size(); i++) {
				HtmlValidatorText val = (HtmlValidatorText) _validators.elementAt(i);
				if (val.getDataStore() == b)
					val.autoPopulateLookups();
			}
		}

	}

	/**
	 * Use this method to send a redirect to the browser. The browser will
	 * display the redirected page
	 * 
	 * @param url
	 *            The URL to redirect to
	 * @throws IOException
	 */
	public void sendRedirect(String url) throws IOException {
		getCurrentResponse().sendRedirect(url);
	}

	/**
	 * Use this method to forward this request to another page. The browser URL
	 * will stay the same as the current page.
	 * 
	 * @param url
	 *            The page to forward to
	 * @throws IOException
	 */
	public void forward(String url) throws IOException, ServletException {
		setForwardPerformed(true);
		getCurrentRequest().setAttribute(FROM_FORWARD_REQ_KEY, new Boolean(true));
		url = parseURL(getCurrentRequest().getContextPath(), getCurrentRequest().getRequestURI(), url);
		getPageContext().forward(url);
	}

	/**
	 * Returns true if the page has been requested from a JSP forward
	 */
	public boolean isRequestFromForward() {
		return getCurrentRequest().getAttribute(FROM_FORWARD_REQ_KEY) != null;
	}

	private String parseURL(String context, String pathInfo, String restOfURL) {
		if (restOfURL.startsWith("/")) {
			if (restOfURL.startsWith(context + "/"))
				return restOfURL.substring(context.length());
			else
				return restOfURL;

		}
		if (context != null && context.length() > 0) {
			if (pathInfo.startsWith(context))
				pathInfo = pathInfo.substring(context.length());
		}
		String url = pathInfo + "/../" + restOfURL;
		Vector paths = new Vector();
		StringTokenizer tok = new StringTokenizer(url, "/");
		String token = null;
		while (tok.hasMoreTokens()) {
			token = tok.nextToken();
			if (token.equals("..")) {
				if (paths.size() > 0)
					paths.setSize(paths.size() - 1);
			} else {
				paths.addElement(token);
			}
		}

		StringBuffer ret = new StringBuffer(url.length());
		for (int i = 0; i < paths.size(); i++) {
			token = (String) paths.elementAt(i);
			ret.append('/');
			ret.append(token);
		}

		return ret.toString();
	}

	/**
	 * Records a timer activity. The average time for each named timer can be
	 * viewed in the ConnectionMonitor Servlet
	 * 
	 * @param name
	 *            The name of the timer
	 * @param timeInMillis
	 *            The time in milliseconds that it took for an event to occur
	 */
	public void recordTimerActivity(String name, long timeInMillis) {
		JspServlet.recordTimerActivity(name, timeInMillis, this);
	}

	/**
	 * Are all the components in the page invisible
	 */
	public boolean getHideAllComponents() {
		return _hideAllComponents;
	}

	/**
	 * Set a flag to indicate whether all the components in the page should be
	 * hidden
	 */
	public void setHideAllComponents(boolean hideAllComponents) {
		_hideAllComponents = hideAllComponents;
	}

	/**
	 * Returns the web application the page is running in
	 */
	public String getWebAppName() {
		String app = URLGenerator.generateServletBaseURL(getCurrentRequest());
		int pos = app.indexOf("/");
		if (pos > -1)
			app = app.substring(0, pos);
		return app;
	}

	/**
	 * Forwards or Redirects to the page specified in the site map entry
	 * 
	 * @param logicalName
	 *            The name of the entry in the site map
	 */
	public void gotoSiteMapPage(String logicalName) throws IOException, ServletException {
		gotoSiteMapPage(logicalName, null);
	}

	/**
	 * Forwards or Redirects to the page specified in the site map entry
	 * 
	 * @param logicalName
	 *            The name of the entry in the site map
	 * @param additionalParms
	 *            Any additional parameters that need to be appended to the url
	 */
	public void gotoSiteMapPage(String logicalName, String additionalParms) throws IOException, ServletException {
		SiteMap m = SiteMap.getSiteMap(getApplicationName());
		if (m == null)
			return;
		else {
			String url = m.getSiteMapURL(getCurrentRequest(), logicalName, additionalParms, false, true);
			if (url != null) {
				if (m.useJavascript(logicalName)) {
					String encodedURL = HttpServletResponseWrapper.encodeURL(url, getCurrentRequest(), getCurrentResponse());
					writeScript(m.addJavaScriptToUrl(logicalName, encodedURL));
				} else if (m.useForward(logicalName))
					forward(url);
				else
					sendRedirect(url);
			}

		}
	}

	/**
	 * Forwards or Redirects to the page specified in the site map entry action
	 * 
	 * @param logicalName
	 *            The name of the entry in the site map
	 * @param actionName
	 *            The name of the action to use
	 * @param additionalParms
	 *            Any additional parameters that need to be appended to the url
	 */
	public void gotoSiteMapActionPage(String logicalName, String actionName, String additionalParms) throws IOException, ServletException {
		SiteMap m = SiteMap.getSiteMap(getApplicationName());
		if (m != null) {
			String action = m.getActionEntry(logicalName, actionName);
			if (action != null)
				gotoSiteMapPage(action, additionalParms);
		}
	}

	/**
	 * Forwards or Redirects to the page specified in the site map entry action
	 * for the current page
	 * 
	 * @param actionName
	 *            The name of the action to use
	 * @param additionalParms
	 *            Any additional parameters that need to be appended to the url
	 */
	public void gotoSiteMapActionPage(String actionName, String additionalParms) throws IOException, ServletException {
		String logicalName = getSiteMapEntryNameForPage();
		gotoSiteMapActionPage(logicalName, actionName, additionalParms);
	}

	/**
	 * Forwards or Redirects to the page specified in the site map entry action
	 * for the current page
	 * 
	 * @param actionName
	 *            The name of the action to use
	 */
	public void gotoSiteMapActionPage(String actionName) throws IOException, ServletException {
		String logicalName = getSiteMapEntryNameForPage();
		gotoSiteMapActionPage(logicalName, actionName, null);
	}

	/**
	 * Use this method to find out how this page was envoked. If it was invoked
	 * from itself (via a submit button) the method will return true.
	 */
	public boolean isReferredByCurrentPage() {
		if (isRequestFromPortlet()) {
			if (getPortletInfo().isFromPost())
				return true;
			String referer = getCurrentRequest().getHeader("referer");
			String renderURL = getPortletInfo().getPortletRenderURL();
			if (referer == null)
				return false;
			else {
				//TODO:This will work in Pluto 1.0, but may not in other portal
				// containers, Find a better way to implement
				referer = trimUrl(referer, getPortletInfo());
				renderURL = trimUrl(renderURL, getPortletInfo());
				return renderURL.startsWith(referer);
			}

		}
		String referer = getCurrentRequest().getHeader("referer");
		String lastReferer = getLastReferer();
		JspForm lastForm = getLastSubmitForm();
		if (lastForm == null)
			return super.isReferredByCurrentPage();
		String lastAction = lastForm.getLastAction();
		boolean retVal = super.isReferredByCurrentPage();
		if (!retVal) {
			if (lastForm.isLastActionFromForward() && referer != null && lastAction != null)
				if (!trimUrl(referer).equals(lastAction))
					retVal = true;
		}
		return retVal;
	}

	/**
	 * Framework method, do not call directly
	 */
	public void setRemoveFromQueryString(String val) {
		if (_removeFromQueryString != null)
			_removeFromQueryString += "," + val;
		else
			_removeFromQueryString = val;
	}



	/**
	 * @return Returns the number of minutes the client should keep the server
	 *         session alive for (-1=don't do keep alive, 0=keep session alive
	 *         as long as the browser is open) .
	 */
	public int getSessionKeepAliveMinutes() {
		return _sessionKeepAliveMinutes;
	}


  

	/**
	 * Sets the number of minutes the client should keep the server session
	 * alive for (-1=don't do keep alive, 0=keep session alive as long as the
	 * browser is open) .
	 */
	public void setSessionKeepAliveMinutes(int keepAliveMinutes) {
		_sessionKeepAliveMinutes = keepAliveMinutes;
	}


    public void setAddExpireHeaders(boolean val) {
        _addExpireHeaders=val;
    }

    public boolean getAddExpireHeaders() {
        return _addExpireHeaders;
    }



	public String getOnLoad() {
		return  _extraScript;
	}


}