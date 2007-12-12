package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlPageBase.java $
//$Author: Dan $
//$Revision: 40 $
//$Modtime: 9/22/04 10:15a $
/////////////////////////

import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.personalization.Skin;
import com.salmonllc.portlet.PortletInfo;
import com.salmonllc.portlet.SalmonPortlet;
import com.salmonllc.properties.*;
import java.io.*;
import java.util.*;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.*;

/**
 * This abstract class is the base class for the HTMLPage and HTMLFrameset classes
 */

public abstract class HtmlPageBase implements Serializable
{
    private transient HttpServletRequestWrapper _currentReq;
    private transient HttpServletResponseWrapper _currentRes;
    private transient HttpSession _currentSession;
    private String _pageName = null;
    private String _applicationName = null;

    public static final int BROWSER_NETSCAPE = 0;
    public static final int BROWSER_MICROSOFT = 1;
    public static final int BROWSER_DREAMWEAVER = 2;

    public static final int BROWSER_OS_WINDOWS = 0;
    public static final int BROWSER_OS_MAC = 1;
    public static final int BROWSER_OS_OTHER = 2;

    private int _browserType = BROWSER_NETSCAPE;
    private float _browserVersion = 4;
    private int _browserOS = BROWSER_OS_WINDOWS;
    private boolean _historyFixUp = false;
    private boolean _sessionExpired = false;

    private boolean _generateFull = true;
    protected String _subPageNameValue = null;
    private boolean _disableRedirect = false;
    private String _serverURL = null;

    private String _contentType = null;
    public static final String APPLICATION_ALIAS_SESSION_KEY = "$ApplicationAlias$";
	public static final String LANGUAGE_SESSION_KEY = "$SOFIALanguagePreferences$";

    private String _servletBaseURL = null;

    private String _origApplicationName = null;
    private boolean _forwardPerformed=false;
    protected boolean _useDisabledAttribute=true;
    protected static final boolean debug = false;
    private Skin _skin;
	private PortletInfo _portletInfo;

	/**
	 * This method removes the current language preferneces so it will be reloaded on the next page hit
	 */
	public void clearLanguagePreferences() {
		getSession().removeAttribute(LANGUAGE_SESSION_KEY);
	}
	/**
	 * This method will remove all instances the current page from the session. Future calls to the page will create and initialize a new page each time.
	 */
	public void clearPageFromSession() {
		clearPageFromSession(_pageName);
	}
	/**
	 * This method will remove all instances of a page from the session. Future calls to the page will create and initialize a new page each time.
	 */
	public void clearPageFromSession(String pageName) {

		String cName = getPageProperties().getProperty(Props.SYS_APP_PACKAGE);
		String app = getApplicationName();

		if (cName == null)
			cName = app + "." + pageName;
		else
			cName += "." + app + "." + pageName;

		cName = "$page$" + cName;

		HttpSession sess = getSession();

		if (sess.getAttribute(cName) != null)
			sess.removeAttribute(cName);

		int i = 0;
		while (true) {
			String name = cName + "_" + i;
			if (sess.getAttribute(name) == null)
				break;
			sess.removeAttribute(name);
			i++;
		}
	}
	/**
	* Called By the framework if a call to the page results in an error. This method should not be called directly.
	*/
	public static void displayError(String error, Exception e, PrintWriter p) {
		p.write("<HTML><HEAD></HEAD><BODY>");
		p.write("<H1>" + error + "</H1><BR>");

		if (e instanceof java.sql.SQLException)
			p.write("<H2>The application is having trouble communicating with the database engine. Please notify your system administrator. Detailed error messages follow.</H2><BR><BR>");

		p.write(e.getMessage() + "<BR><BR>");

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(out);
			e.printStackTrace(pw);
			pw.close();
			out.close();

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			BufferedReader b = new BufferedReader(new InputStreamReader(in));

			StringBuffer sb = new StringBuffer();
			String line = b.readLine();
			while (line != null) {
				sb.setLength(0);
				int count = 0;
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if (c == '\t') {
						sb.append("<BLOCKQUOTE>");
						count++;
					} else
						sb.append(c);
				}
				for (int i = 0; i < count; i++)
					sb.append("</BLOCKQUOTE>");
				p.println(sb.toString());
				line = b.readLine();
			}
			in.close();
		} catch (Exception ex) {
		}

		p.write("</BODY></HTML>");
	}
	/**
	 * This method will generate the html for each component in the page
	 */
	public abstract void generateHTML(PrintWriter p) throws Exception;
	/**
	 * This method gets the name of the application that the page is part of.
	 */
	public String getApplicationName() {
		return _applicationName;
	}
	/**
	 * This method Returns the Operating System the browser is running in. Valid return values are BROWSER_OS_MICROSOFT, BROWSER_OS_MAC or BROWSER_OS_OTHER;
	 */
	public int getBrowserOS() {
		return _browserOS;
	}
	/**
	 * This method was created in VisualAge.
	 * @return int
	 */
	public float getBrowserSubVersion() {
		return _browserVersion;
	}
	/**
	 * This method Returns the Browser Type: BROWSER_NETSCAPE or BROWSER_MICROSOFT.
	 */
	public int getBrowserType() {
		return _browserType;
	}
	/**
	 * This method was created in VisualAge.
	 * @return int
	 */
	public int getBrowserVersion() {
		return (int) _browserVersion;
	}
	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	 */
	public String getContentType() {
		return _contentType;
	}
	/**
	 * This method gets the current HttpServletRequest for the page.
	 */
	public HttpServletRequest getCurrentRequest() {
		return _currentReq;
	}
	/**
	 * This method gets the current HttpServletResponse for the page.
	 */
	public HttpServletResponse getCurrentResponse() {
		return _currentRes;
	}
	/**
	 * This method was created in VisualAge.
	 * @return boolean
	 */
	public boolean getDisableRedirect() {
		return _disableRedirect;
	}
	/**
	 * This method sets whether or not the page will generate full html. Setting the property to false will cause the page to only generate the html in the contained components and not any headers or footers.
	 */
	public boolean getGenerateFullHTML() {
		return _generateFull;
	}
	/**
	 * This method gets whether or not the pages history fixup mechanism is enabled.
	 */
	public boolean getHistoryFixUp() {
		return _historyFixUp;
	}
	/**
	 * This method returns the users language preference
	 */
	public LanguagePreferences getLanguagePreferences() {
		return (LanguagePreferences) getSession().getAttribute(LANGUAGE_SESSION_KEY);
	}
	/**
	 * This method returns the original name of the application if application alaising is used. In that case getApplicationName will return the name of the alias and getOrigApplication name will get the "real" name of the application. If aliasing isn't used, they will both return the same value.
	 */
	public String getOrigApplicationName() {
		return _origApplicationName;
	}
	/**
	 * This method gets the name of the page.
	 */
	public String getPageName() {
		return _pageName;
	}
	/**
	 * This method gets the current Properties Object (com.salmonllc.properties.props) associated with this page.
	 */
	public Props getPageProperties() {
		Props p = Props.getProps(_applicationName, _pageName);
        Skin sk = getSkin();
        if (sk != null)
            p.setUnitProperties(sk.getProperties());
        return p;
	}
	/**
	 * This method gets the current url for the server that the page is running on.
	 */
	public String getServerURL() {
		return _serverURL;
	}
	/**
	 * This method gets the base url for servlets in this environment from the server name up to but not including the servlet name.
	 */
	public String getServletBaseURL() {
		return _servletBaseURL;
	}
	/**
	 * This method gets the path for the servlet that ran the page.
	 */
	public String getServletPath() {
		return _servletBaseURL + "/AppServer";
	}
	/**
	 * This method gets the current HttpSession used by this page.
	 */
	public HttpSession getSession() {
		return _currentSession;
	}
	/**
	 * If the user went to a subpage, this method will return the name of it.
	 */
	public String getSubPageName() {
		return _subPageNameValue;
	}
	/**
	 * This method should be overridden in subclasses of the page class. In the method, HTML components should be created and placed in the page, listeners registered and any other initialization tasks should be performed.
	 */
	public abstract void initialize() throws Exception;
	/**
	 * This method will indicate whether the session for the current page request is expired.
	 */
	public boolean isSessionExpired() {
		return _sessionExpired;
	}
	/**
	* Called By the framework when the page is created to load properties from property files. This method should not be called directly.
	*/
	public void loadProperties() {

	}
	/**
	 * This method will process the parms from a post for every component in the page.
	 */
	public abstract void processParms(boolean iFrameSubmit) throws Exception;
	/**
	 * This method will set an alias for the current application for the current user session. Every page created in this application after this method is called will have its application name initialized to the specified alias name instead of the application name. This will cause the application to use a different property file to load page and component properties.
	 */
	public void resetApplicationAlias() {
		String path = getCurrentRequest().getPathInfo();
		StringTokenizer t = new StringTokenizer(path, "/");
		String app = t.nextToken();

		HttpSession sess = getSession();

		Object o = sess.getAttribute(APPLICATION_ALIAS_SESSION_KEY);

		if (o == null)
			return;

		java.util.Hashtable h = (java.util.Hashtable) o;
		h.remove(app);
		sess.setAttribute(APPLICATION_ALIAS_SESSION_KEY, h);
	}
	/**
	 * This method will set an alias for the current application for the current user session. Every page created in this application after this method is called will have its application name initialized to the specified alias name instead of the application name. This will cause the application to use a different property file to load page and component properties.
	 */
	public void setApplicationAlias(String applicationAlias) {
		String path = getCurrentRequest().getPathInfo();
		StringTokenizer t = new StringTokenizer(path, "/");
		String app = t.nextToken();

		HttpSession sess = getSession();

		Object o = sess.getAttribute(APPLICATION_ALIAS_SESSION_KEY);

		java.util.Hashtable h = (o == null ? new java.util.Hashtable() : (java.util.Hashtable) o);
		h.put(app, applicationAlias);
		sess.setAttribute(APPLICATION_ALIAS_SESSION_KEY, h);
	}
	public void setApplicationName(String name) {
		_applicationName = name;
	}
	/**
	 * Sets the mime type that this page will return to the browser
	 */
	public void setContentType(String contentType) {
		_contentType = contentType;
	}
	
	/**
	 * Called By the framework to on each request to set the HttpServletRequest. This method should not be called directly.
	 */
	public void setCurrentRequest(ActionRequest r) {
		if (_currentReq == null)
			_currentReq = new HttpServletRequestWrapper(r);
		else
			_currentReq.setRequest(r);
	}
	
	/**
	 * Called By the framework to on each request to set the HttpServletRequest. This method should not be called directly.
	 */
	public void setCurrentRequest(HttpServletRequest r) {
		if (r instanceof HttpServletRequestWrapper)
			_currentReq = (HttpServletRequestWrapper) r;
		else if (_currentReq == null)
			_currentReq = new HttpServletRequestWrapper(r);
		else
			_currentReq.setRequest(r);

		String userAgent = r.getHeader("user-agent");
		_browserType = BROWSER_NETSCAPE;
		_browserOS = BROWSER_OS_WINDOWS;
		_browserVersion = 4;
		if (userAgent != null) {
			if (userAgent.indexOf("Windows") > -1)
				_browserOS = BROWSER_OS_WINDOWS;
			else if (userAgent.indexOf("Mac") > -1)
				_browserOS = BROWSER_OS_MAC;
			else
				_browserOS = BROWSER_OS_OTHER;

			int pos = userAgent.indexOf("MSIE");
			if (pos > -1) {
				_browserType = BROWSER_MICROSOFT;
				int pos2 = userAgent.indexOf(";", pos);
				if (pos2 > -1)
					userAgent = userAgent.substring(pos, pos2);

				pos = userAgent.indexOf(' ');
				if (pos > -1) {
					String ver = userAgent.substring(pos + 1);
					try {
						Float f = new Float(ver);
						_browserVersion = f.floatValue();
					} catch (Exception e) {
						_browserVersion = 3;
					}
				}
			} else if (userAgent.startsWith("Mozilla")) {
				_browserType = BROWSER_NETSCAPE;

				//Search for Netscape version 7
				int idxNS7 = userAgent.indexOf("Netscape/7");
				if(idxNS7>0){ //version 7...
					 _browserVersion=7;
				}else{
					pos = userAgent.indexOf(' ');
					if (pos > -1)
						userAgent = userAgent.substring(0, pos);

					pos = userAgent.indexOf('/');
					if (pos > -1) {
						String ver = userAgent.substring(pos + 1);
						try {
							Float f = new Float(ver);
							_browserVersion = f.floatValue();
							if (((int) _browserVersion) == 5)
								_browserVersion++;
						} catch (Exception e) {
							_browserVersion = 3;
						}
					}
				}
			}  else if (userAgent.equalsIgnoreCase("MMHttp")) {
                _browserType=BROWSER_DREAMWEAVER;
                _browserVersion=6;
            }

		}

	}

	/**
	 * Called By the framework to on each request to set the servlet response. This method should not be called directly.
	 */

	public void setCurrentResponse(HttpServletResponse r) {
		if (r instanceof HttpServletResponseWrapper)
			_currentRes = (HttpServletResponseWrapper) r;
		else if (_currentRes == null || _currentRes instanceof HttpServletResponseDummy)
			_currentRes = new HttpServletResponseWrapper(r, this);
		else 
			_currentRes.setResponse(r);
	}
	
	/**
	 * Called By the framework to on each request to set the servlet response. This method should not be called directly.
	 */

	public void setCurrentResponse(ActionResponse res, ActionRequest req) {
		 if (_currentRes == null)
			_currentRes = new HttpServletResponseWrapper(res, req, this);
		else
			_currentRes.setResponse(res,req);
	}
	/**
	 * This method sets whether or not the page will do a redirect for each submit. The redirect prevents browsers from displaying a page expired message when the user clicks the back button.
	 * @param bRedirect boolean
	 */
	public void setDisableRedirect(boolean bRedirect) {
		_disableRedirect = bRedirect;
	}
	/**
	 * This method sets whether or not the page will generate full html. Setting the property to false will cause the page to only generate the html in the contained components and not any headers or footers.
	 */
	public void setGenerateFullHTML(boolean gen) {
		_generateFull = gen;
	}
	/**
	 * This method enables or disables the pages history fixup mechanism.
	 */
	public void setHistoryFixUp(boolean ok) {
		_historyFixUp = ok;
	}
	/**
	 * Use this to override the language preferences sent by the browser
	 */
	public void setLanguagePrefrence(LanguagePreferences pref) {
		getSession().setAttribute(LANGUAGE_SESSION_KEY,pref);
	}
	/**
	 * Called By the framework to on each request to set the Application Name. This method should not be called directly.
	 */

	public void setOrigApplicationName(String name) {
		_origApplicationName = name;
	}
	/**
	 * Called By the framework to on each request to set the page name. This method should not be called directly.
	 */

	public void setPageName(String name) {
		_pageName = name;
	}
	/**
	 * Called By the framework to on each request to set the Server's URL. This method should not be called directly.
	 */

	public void setServerURL(String url) {
		_serverURL = url;
	}
	/**
	 * Called By the framework to on each request to set the Base URL for the servlet. This method should not be called directly.
	 */
	public void setServletBaseURL(String url) {
		_servletBaseURL = url;
	}
	/**
	 * Called By the framework to on each request to set the HttpSession. This method should not be called directly.
	 */

	public void setSession(HttpSession s) {
		_currentSession = s;
	}
	/**
	 * Called By the framework to on each request to set whether the session is expired. This method should not be called directly.
	 */
	public void setSessionExpired(boolean exp) {
		_sessionExpired = exp;
	}
	/**
	 * Called By the framework to on each request to set the name of the subpage selected. This method should not be called directly.
	 */
	public void setSubPageName(String name) {
		_subPageNameValue = name;
	}
	/**
	* This method sets up the language prefrences for the page. This method should is called by the framework and should not be called directly
	*/
	public void setUpLanguagePreferences() {
		Props p = getPageProperties();
		if (!p.getBooleanProperty(Props.LOCALIZER_USE_CACHE, true))
			LanguageResourceFinder.clearCache();
		LanguagePreferences pr = (LanguagePreferences) getSession().getAttribute(LANGUAGE_SESSION_KEY);
		
		if (pr == null) {
			String lang = "";
			if (isRequestFromPortlet()) {
				Enumeration enumera = null;
				PortletInfo info = getPortletInfo();
				if (info.getRenderRequest() != null)
					enumera =	getPortletInfo().getRenderRequest().getLocales();
				else if (info.getActionRequest() != null)
					enumera =	getPortletInfo().getActionRequest().getLocales();
				lang=SalmonPortlet.constructLanguageString(enumera);
			}	
			else
				lang = getCurrentRequest().getHeader("Accept-Language");
			if (lang != null) {
				pr = new LanguagePreferences(lang);
				getSession().setAttribute(LANGUAGE_SESSION_KEY, pr);
			}
		}
	}

        /**
     * This method is called by the framework and should not be called directly
     */
    public boolean getForwardPerformed() {
        return _forwardPerformed;
    }

    /**
     * This method is called by the framework and should not be called directly
     */
    public void setForwardPerformed(boolean forwardPerformed) {
        _forwardPerformed = forwardPerformed;
    }

    /**
     * This method is used to indicate whether components on the page should use the Html disabled attribute for disabled form components
     */
    public boolean getUseDisabledAttribute() {
        return _useDisabledAttribute;
    }

    /**
     * This method is used to indicate whether components on the page should use the Html disabled attribute for disabled form components
     */
    public void setUseDisabledAttribute(boolean useDisabledAttribute) {
        _useDisabledAttribute = useDisabledAttribute;
    }

    /**
     * Sets the Default Skin for all pages. To apply the skin to pages already created you should clear them from the session after making this call.
     * @param skin the Skin to set
     * @param forAllApps true if you want to set the skin for all applications on the server, false if only for the app the component is in.
     */
    public void setDefaultSkin(Skin skin, boolean forAllApps) {
        setSkin(skin);
        String key = "%$DEFAULT_SKIN$%";
        if (!forAllApps)
            key = getApplicationName() + key;
        HttpSession sess = getSession();
        sess.setAttribute(key,skin);
        Enumeration e = sess.getAttributeNames();
        while (e.hasMoreElements()) {
            String ele = (String) e.nextElement();
            Object o = sess.getAttribute(ele);
            if (o instanceof HtmlPageBase && o != this) {
                ((HtmlPageBase)o).loadProperties();
                if (forAllApps || ((HtmlPageBase) o).getApplicationName().equals(getApplicationName())) {
                    ((HtmlPageBase) o).applySkin(skin,true);
                }
            }
        }
    }

	/**
	 * Removes a default skin set by calling the setDefaultSkinMethod
     * @param forAllApps true if you want to clear the skin for all applications on the server, false if only for the app the component is in.
	 */
	public void clearDefaultSkin(boolean forAllApps) {
		String key = "%$DEFAULT_SKIN$%";
		if (!forAllApps)
			key = getApplicationName() + key;
		HttpSession sess = getSession();
		sess.removeAttribute(key);
	}	
    /**
     * Sets the skin for this page and applies it
     * @param skin
     */
    public void setSkin(Skin skin) {
        _skin = skin;
        loadProperties();
        applySkin(skin, true);
    }

    /**
    * Applies the skin to the page. This method is called from the framework and should not be called directly. Instead use setSkin(Skin sk)
    */
    public void applySkin(Skin skin, boolean doSetTheme) {

    }
    /**
     * Gets the skin for this page. If one isn't found, the system will look for a default one for the application or all applications on the session.
     * @return
     */
    public Skin getSkin() {
        HttpSession sess = getSession();
        Skin ret = _skin;
        if (ret == null)
            ret = (Skin) sess.getAttribute(getApplicationName() + "%$DEFAULT_SKIN$%");
        if (ret == null)
            ret = (Skin) sess.getAttribute("%$DEFAULT_SKIN$%");
        return ret;
    }
    
	/**
	 * @returns if the page is launched inside a portlet, this method will provide information on the portlet or null if the page is not launched from a portlet
	 */
	public PortletInfo getPortletInfo() {
		return _portletInfo;
	}

	/**
	 * @param iframework method, do not call directly
	 */
	public void setPortletInfo(PortletInfo info) {
		_portletInfo = info;
	}

	/**
	 * @returns true if the page request is from a portlet
	 */
	public boolean isRequestFromPortlet() {
		return _portletInfo != null;
	}
}