package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlComponent.java $
//$Author: Dan $
//$Revision: 54 $
//$Modtime: 1/19/04 2:36p $
/////////////////////////


import com.salmonllc.jsp.JspForm;
import com.salmonllc.jsp.tags.PageTag;
import com.salmonllc.personalization.Nameable;
import com.salmonllc.sitemap.SiteMap;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;

/**
 * This component is the base class for every HTML component in the framework.
 */
public abstract class HtmlComponent implements Serializable, Nameable {

    public static final int EVENT_NONE = 0;
    public static final int EVENT_SUBMIT = 1;
    public static final int EVENT_OTHER = 2;

	public static final int DISABLED_ATTRIBUTE_USE_ON_SUPPORTED_BROWSERS = 0;
	public static final int DISABLED_ATTRIBUTE_USE_NEVER = 1;
	public static final int DISABLED_ATTRIBUTE_USE_SYSTEM_DEFAULT = 2;

    private HtmlPage _page;
    protected HtmlComponent _parent;
    String _name = "";
    String _fullName = null;
    protected boolean _visible = true;
    /** Write a newline, or not, at the end of the generated tag. */
    protected boolean _generateNewline = true;
    /** DataSource to bind */
    private String _dataSource;
    protected String _class;
    private String _form;
    private int _formNo = -1;
	private int _useDisabledAttribute = DISABLED_ATTRIBUTE_USE_SYSTEM_DEFAULT;

    protected static final boolean debug = false;

    /**
     * This method constructs a new HtmlComponent with the specified name.
     * @param name The name of the component. Each component in a page must have a unique name.
     */
    public HtmlComponent(String name, HtmlPage p) {
        _name = name;
        _page = p;
    }

    /**
     * This method should be implemented by subclasses of this component that will have autobinding.
     * This method is called by the framework and should not be called directly.
     */
    public void doBinding() throws Exception {
    }

    /**
     * This method will encode a url to support URL rewriting
     */
    public String encodeURL(String url) {
		if (url == null)
			return null;
        if (url.toLowerCase().startsWith("javascript:"))
          return url;
        if (!getPage().getEncodeURLs()) {
			String logicalName=getSiteMapEntryName(url);
			if (logicalName == null)
        		return url;
        	else {
        		url = translateSiteMapURL(url);
				SiteMap m = getPage().getSiteMap();
				if (m != null)
					return m.addJavaScriptToUrl(logicalName,url);
				else
					return url;
        	}
        }
        if (!getPage().isWMLMaintained()) {
            HttpServletRequest req = getPage().getCurrentRequest();
            HttpServletResponse res = getPage().getCurrentResponse();
			String logicalName=getSiteMapEntryName(url);
			if (logicalName == null)
				return HttpServletResponseWrapper.encodeURL(url, req, res);
			else {
            	String translatedURL = translateSiteMapURL(url);
            	String encodedURL = HttpServletResponseWrapper.encodeURL(translatedURL, req, res);
				SiteMap m = getPage().getSiteMap();
				if (m != null)
					return  m.addJavaScriptToUrl(logicalName,encodedURL);
				else
					return encodedURL;
			}
        } else {
			url = translateSiteMapURL(url);
            HttpServletRequest req = getPage().getCurrentRequest();
            HttpServletResponse res = getPage().getCurrentResponse();
            if (HttpServletResponseWrapper.encodeURL(url, req, res).indexOf(';') >= 0) {
                if (url.indexOf('?') >= 0)
                    if (url.indexOf(PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getPage().getSession())) < 0) {
                        if (url.endsWith("&amp;") || url.endsWith("?"))
                            return url + PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getPage().getSession());
                        else
                            return url + "&amp;" + PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getPage().getSession());
                    } else
                        return url;
                else
                    return url + "?" + PageTag.getSessionIdentifier() + "=" + PageTag.getWmlSessId(getPage().getSession());
            } else
                return url;
        }
    }

    protected String getSiteMapEntryName(String url) {
		if (url == null)
			return null;
		if (url.startsWith("%")) {
			int pos = url.length();
			for (int i = 0; i < pos; i++) {
				char c = url.charAt(i);
				if (c == '?' || c == '#' || c == ' ' || c == ';' || c == '/') {
					pos = i;
					break;
				}
			}
			return url.substring(1,pos);
		}
		else
			return null;
    }
    /**
     * Translates a site map url (preceeded by a % sign) to an actual URL
     * @param url
     * @return
     */
    public String translateSiteMapURL(String url) {
    	if (url == null)
    		return null;
    	String logical = getSiteMapEntryName(url);
		if (logical != null) {
			int pos = logical.length() + 1;
			String extraParms = null;
			if (pos < url.length())
				extraParms = url.substring(pos);
			String u = getPage().getSiteMapURL(logical,extraParms,false);
			if (u != null)
				url = u;
		}
		return url;
    }

    /**
     * This method will encode a url to support URL rewriting
     */
    public StringBuffer encodeURL(StringBuffer url) {
        if (url == null)
            return null;
        return new StringBuffer(encodeURL(url.toString()));
    }

    /**
     * This method should be implemented by subclasses of this component that will respond to events. This method should notify each of the component's listeners that an event occured.
     * This method is called by the framework and should not be called directly.
     * @param eventType valid Types are EVENT_SUBMIT and EVENT_OTHER
     */
    public boolean executeEvent(int eventType) throws Exception {
    	if (eventType == -999)
    		eventType = 0;
        return true;
    }

    /**
     * A utility method used to replace special HTML characters <, >, &, " with their HTML representations.
     */
    public static String fixSpecialHTMLCharacters(String input) {
        if (input == null)
            return null;

        int inputLength = input.length();
        StringBuffer sb = new StringBuffer(inputLength);
        for (int i = 0; i < inputLength; i++) {
            char c = input.charAt(i);
            if (c == '<')
                sb.append("&lt;");
            else if (c == '&')
                sb.append("&amp;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '>')
                sb.append("&gt;");
			else if (c == '"')
				sb.append("&quot;");
			else if (c == '\'')
				sb.append("&#39;");
            else
                sb.append(c);
        }
        return sb.toString();
    }

    /**
     * The purpose of this method is to generate the HTML a particular component needs to send back to the browser for its visual representation and must be implemented by each subclass.
     * The method is called by the framework and should not be called directly.
     */
    public abstract void generateHTML(PrintWriter p, int rowNo) throws Exception;

    /**
     * The purpose of this method is to generate the HTML a particular component needs to send back to the browser for its visual representation.
     * This version of the method is used for components that must process multiple rows in a DataStore in one generate (like computing a total for several rows).
     * The method is called by the framework and should not be called directly.
     */
    public void generateHTML(PrintWriter p, int rowStart, int rowEnd) throws Exception {
        generateHTML(p, rowStart);
    }

    /**
     * This method generates a URL for a dynamically generated image created by the component.
     * The method is called by the framework and should not be called directly.
     */
    protected String generateImageURL() {
        HtmlPage p = getPage();

        StringBuffer buf = new StringBuffer(com.salmonllc.util.URLGenerator.generateObjectstoreURL(p.getCurrentRequest(), getPage().getApplicationName()));
        buf.append("/");

        if (p instanceof com.salmonllc.jsp.JspController) {
            buf.append("JSP/");
            buf.append(((com.salmonllc.jsp.JspController) p).getSessionKey());
        } else {
            buf.append(p.getOrigApplicationName());
            buf.append("/");
            buf.append(p.getPageName());
        }

        buf.append("/");
        buf.append(getFullName());
        buf.append("/");


        return buf.toString();
    }

    /**
     * This method should be implemented by containers that need to iterate through data in a datastore. The generateHtml method
     * is called once for each row. This method is called once before the rows are processed.
     */
    public void generateInitialHTML(PrintWriter p) throws Exception {
    }

    /**
     * This method returns the class name used by the component (for DSS sytle sheets) .
     */
    public String getClassName() {
        return _class;
    }

    /**
     * This method returns the Data Source the component is bound to.
     */
    public String getDataSource() {
        return _dataSource;
    }

    /**
     * This will return the full name of the component. The full name is the name of the component appended to the names of its parent components and seperated by underscores. For example if this is a button named "button1" inside a table named "table1" the full name for this component would be "table1_button1".
     */
    public String getFullName() {
        if (_fullName == null) {
            HtmlComponent parent = _parent;
            String name = _name;
            // no need to create a new string for every time through the loop
            String parentName = null;
            //
            while (parent != null) {
                parentName = parent.getName();
                if (parentName != null) {
                    if (parentName != "") {
                        name = parentName + "_" + name;
                    }
                }
                parent = parent.getParent();
            }
            _fullName = getPortletNameSpace() + name;
        }

        return _fullName;
    }

    /**
     * This method returns the name of the component.
     */
    public String getName() {
        return _name;
    }

    /**
     * This method return the page that the component is a member of.
     */
    public HtmlPage getPage() {
        return _page;
    }

    /**
     * This method returns the parent container that this component is in.
     * @return The parent component or null if there is no parent.
     */
    public HtmlComponent getParent() {
        return _parent;
    }

    /**
     * This method returns whether or not the component is visible. If it is not visible, no HTML will be generated.
     */
    public boolean getVisible() {
        return getVisible(false);
    }

    /**
     * This method returns whether or not the component is visible. If it is not visible, no HTML will be generated.
     * @param checkParents A boolean value, true mean that this component is considered visible if it and all it's parents are visible. A false value only checks the component itself.
     */
    public boolean getVisible(boolean checkParents) {
        if (!_visible)
            return false;
        boolean bVisible = _visible;
        if (checkParents) {
            HtmlComponent hc = getParent();
            while (hc != null) {
                if (hc.getVisible() == false) {
                    bVisible = false;
                    break;
                }
                hc = hc.getParent();
            }
        }
        return bVisible;
    }

    /**
     * This method will process the parmeters from a HTTP post that are relavent to this component or components in it.
     * This is a framework method and should not be called directly.
     * @param parms a HashTable containing all the parameters for the servlet.
     * @param rowNo - int row number to act on
     * @return true if this component or a child is the one that submitted the page and false if not.
     */
    public boolean processParms(Hashtable<String, Object> parms, int rowNo) throws Exception {
        return false;
    }

    /**
     * This method will clear all pending events from the event queue for this component and components it contains.
     */
    public void reset() {
    }

    /**
     * This method sets the property class (for DSS - style sheets) for the component.
     * @param sClass The class to use.
     */
    public void setClassName(String sClass) {
        _class = sClass;
    }

    /**
     * This method sets the Data Source the component should be bound to.
     */
    public void setDataSource(String dataSource) {
        _dataSource = dataSource;
        if (_dataSource == null)
            return;

        //search and replace &QUOT; with a single quote. For Engines that have trouble with single quotes inside a tag
        String test = _dataSource.toUpperCase();
        int pos = test.indexOf("&QUOT;");
        if (pos == -1)
            return;

        StringBuffer work = new StringBuffer(_dataSource);
        while (pos > -1) {
            work.replace(pos, pos + 6, "'");
            test = work.toString().toUpperCase();
            pos = test.indexOf("&QUOT;");
        }
        _dataSource = work.toString();
    }

    /**
     * Enables or disables the newline at the end of the generated HTML tag.
     * @param enable If true, generate a newline after the generated HTML.
     */
    public void setGenerateNewline(boolean enable) {
        _generateNewline = enable;
    }

    /**
     * This method sets the name of the component.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * This method sets the page the component is in
     */
    public void setPage(HtmlPage p) {
        _page = p;
    }

    /**
     * This method sets the parent component of this component. If a component is inside of a container, the container is its parent.
     */
    public void setParent(HtmlComponent parent) {
        _parent = parent;
        _form = null;
    }

    /**
     * This method sets the visible property for the component. If it is not visible, no html will be generated.
     */
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    public String toString() {
        String ret = super.toString();
        ret += "\n" + getFullName();
        return ret;
    }

    public String getFormString() {
        if (_form != null)
            return _form;
        HtmlComponent comp = getParent();
        	
        while (comp != null) {
            if (comp instanceof JspForm) {
                _form = "document.forms['" + getPortletNameSpace() + comp.getName() + "'].";
                break;
            }
            comp = comp.getParent();
        }
        if (_form == null)
            if (_formNo == -1)
                _form = "document.forms[0].";
            else
                _form = "document.forms[" + _formNo + "].";
        return _form;
    }

    /**
     * Set the form that this component is in. This can be used if a page uses more than one form and the component is in an html form tag instead of the <sofia:form> tag.
     */
    public void setFormIndex(int formIndex) {
        _formNo = formIndex;
        _form = null;
    }

    /**
     * Get the form that this component is in. This can be used if a page uses more than one form and the component is in an html form tag instead of the <sofia:form> tag.
     */
    public int getFormIndex() {
        return _formNo;
    }

    protected boolean useDisabledAttribute() {
        HtmlPageBase p = getPage();
		if ( _useDisabledAttribute == DISABLED_ATTRIBUTE_USE_SYSTEM_DEFAULT) {
		     if (! p.getUseDisabledAttribute())
		           return false;
		}
		else if (_useDisabledAttribute == DISABLED_ATTRIBUTE_USE_NEVER)
			  return false;

        int browser=p.getBrowserType();
        int ver = p.getBrowserVersion();
        if (browser == HtmlPage.BROWSER_NETSCAPE && ver <= 4)
            return false;
        if (browser == HtmlPage.BROWSER_MICROSOFT && ver <=4)
            return false;
        return true;
    }

     /**
     * This method sets the property theme for the component.
      */
    public void setTheme(String theme) {
         //override this in subclasses
    }
    /**
     * Returns the theme for this component or null if it doesn't have one
     * @return
     */
    public String getTheme() {
        //override this in subclasses
        return null;
    }

	/**
	 * @return how the component will handle the disabled HTML attribute. DISABLED_ATTRIBUTE_USE_ON_SUPPORTED_BROWSERS (use disabled html attribute on browsers that support it) , DISABLED_ATTRIBUTE_USE_NEVER (Never use the disabled attribute for this component) or DISABLED_ATTRIBUTE_USE_SYSTEM_DEFAULT (Use the attribute if the system default is to and the browser supports it)
	 */
	public int getUseDisabledAttribute() {
		return _useDisabledAttribute;
	}

	/**
	 * Sets how the component will handle the disabled HTML attribute. DISABLED_ATTRIBUTE_USE_ON_SUPPORTED_BROWSERS (use disabled html attribute on browsers that support it) , DISABLED_ATTRIBUTE_USE_NEVER (Never use the disabled attribute for this component) or DISABLED_ATTRIBUTE_USE_SYSTEM_DEFAULT (Use the attribute if the system default is to and the browser supports it)
	 */
	public void setUseDisabledAttribute(int i) {
		_useDisabledAttribute = i;
	}

	protected String getPortletNameSpace() {
		if (getPage().isRequestFromPortlet())
			return getPage().getPortletInfo().getNameSpace();
		else
			return "";	
	}	
}
