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

import com.salmonllc.properties.Props;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.*;

/**
 * This class is a wrapper used by internal processes in the framework and isn't intended to be used directly. For more information see HttpServletResponse in the JSDK documentation.
 */

public class HttpServletResponseWrapper implements HttpServletResponse {
    HttpServletResponse _res;
	ActionResponse _pres;
	ActionRequest _preq;
    HtmlPageBase _page;
    boolean _redirectSent = false;

    /**
     * HttpServletResponseWrapper constructor comment.
     */
    public HttpServletResponseWrapper(HttpServletResponse res, HtmlPageBase page) {
        super();
        _res = res;
        _page = page;
    }

	/**
	 * HttpServletResponseWrapper constructor comment.
	 */
	public HttpServletResponseWrapper(ActionResponse res, ActionRequest req, HtmlPageBase page) {
		super();
		_pres = res;
		_preq = req;
		_page = page;
	}
	
    /**
     * addCookie method comment.
     */
    public void addCookie(Cookie arg1) {
    	if (_pres == null)
        	_res.addCookie(arg1);
    }

    /**
     * containsHeader method comment.
     */
    public boolean containsHeader(String arg1) {
    	if (_pres != null)
    		return false;
    	else	
        	return _res.containsHeader(arg1);
    }
    /**
     * encodeRedirectUrl method comment.
     */
    /** @deprecated */
    public String encodeRedirectUrl(String arg1) {
		if (_pres != null)
			return _pres.encodeURL(arg1);
		else
	        return _res.encodeRedirectURL(arg1);
    }
    /**
     * encodeUrl method comment.
     */
    /** @deprecated */
    public String encodeUrl(String arg1) {
		if (_pres != null)
			return _pres.encodeURL(arg1);
		else
	        return _res.encodeURL(arg1);
    }

    /**
     * getCharacterEncoding method comment.
     */
    public String getCharacterEncoding() {
		if (_pres != null)
			return _preq.getCharacterEncoding();
		else
	        return _res.getCharacterEncoding();
    }

    /**
     * getOutputStream method comment.
     */
    public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException {
		if (_pres != null)
			return null;
		else
	        return _res.getOutputStream();
    }

    /**
     * This method was created in VisualAge.
     */
    public boolean getRedirectSent() {
        return _redirectSent;
    }

    /**
     * getWriter method comment.
     */
    public java.io.PrintWriter getWriter() throws java.io.IOException {
		if (_pres != null)
			return null;
		else
	        return _res.getWriter();
    }

    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     * @param pathInfo java.lang.String
     * @param restOfURL java.lang.String
     */
    private String parseURL(String servletPath, String pathInfo, String restOfURL) {
        if (restOfURL.startsWith("/") && (!_page.getPageProperties().getBooleanProperty(Props.SYS_ABSOLUTE_URLS_TO_RELATIVE_ON_REDIRECT,false))) {
             return restOfURL;
        }

        if (pathInfo == null)
            pathInfo = "";

        String url = servletPath + pathInfo + "/../" + restOfURL;
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
     * sendError method comment.
     */
    public void sendError(int arg1) throws java.io.IOException {
		if (_pres == null)
		     _res.sendError(arg1);
    }

    /**
     * sendError method comment.
     */
    public void sendError(int arg1, String arg2) throws java.io.IOException {
    	if (_pres == null)
        	_res.sendError(arg1, arg2);
    }

    /**
     * sendRedirect method comment.
     */
    public void sendRedirect(String url) throws java.io.IOException {
        sendRedirect(url, true);
    }

    /**
     * sendRedirect method comment.
     */
    public void sendRedirect(String url, boolean setFlag) throws java.io.IOException {
        //Following code has to be remain in active. If following "if" statement is commented out,
        //it will generate problems in isRefferedByCurrentPage() method. Example;
        //go to page "A" then page "B" which is the child of "A". Than go back to page "A" again. In that case
        //isReferredByCurrentPage() method return true. Since the "_lastReferer" is not cleared, method
        // execute the "...else referer = _lastReferer;" line, which makes the referer and the referred the same page.
        if (_page instanceof HtmlPage && setFlag)
            ((HtmlPage) _page).clearCurrentPageReferer();

		if (_pres != null) {
			if (setFlag)
				_redirectSent = true;
			_pres.sendRedirect(url);	
			return;
		}	
			
        try {
           new URL(url);
        } catch (Exception e) {
            //its a partial url, construct a full url
            HttpServletRequest req = _page.getCurrentRequest();

            StringBuffer buf = new StringBuffer(_page.getServerURL());

            buf.append(parseURL(_page.getServletPath(), req.getPathInfo(), url));

            url = buf.toString();
        }

        if (setFlag)
            _redirectSent = true;

        if (_page instanceof HtmlPage && ((HtmlPage)_page).isWMLMaintained())
            _res.sendRedirect(url);
        else
            _res.sendRedirect(encodeURL(url, _page.getCurrentRequest(),this));

    }

    /**
     * setContentLength method comment.
     */
    public void setContentLength(int arg1) {
		if (_pres == null)
    		_res.setContentLength(arg1);
    }

    /**
     * setContentType method comment.
     */
    public void setContentType(String arg1) {
		if (_pres == null)
        	_res.setContentType(arg1);
    }

    /**
     * setDateHeader method comment.
     */
    public void setDateHeader(String arg1, long arg2) {
		if (_pres == null)
        	_res.setDateHeader(arg1, arg2);
    }

    /**
     * setHeader method comment.
     */
    public void setHeader(String arg1, String arg2) {
		if (_pres == null)
        	_res.setHeader(arg1, arg2);
    }

    /**
     * setIntHeader method comment.
     */
    public void setIntHeader(String arg1, int arg2) {
		if (_pres == null)
        	_res.setIntHeader(arg1, arg2);
    }

    /**
     * This method was created in VisualAge.
     * @param sent boolean
     */
    void setRedirectSent(boolean sent) {
        _redirectSent = sent;
    }

    /**
     * This method was created in VisualAge.
     * @param res javax.servlet.http.HttpServletResponse
     */
	void setResponse(HttpServletResponse res) {
		_res = res;
		_pres = null;
	}
	void setResponse(ActionResponse res, ActionRequest req) {
		_res = null;
		_pres = res;
		_preq = req;
	}


    /**
     * setStatus method comment.
     */
    public void setStatus(int arg1) {
		if (_pres == null)
        	_res.setStatus(arg1);
    }
    /**
     * setStatus method comment.
     */
    /** @deprecated */
    public void setStatus(int arg1, String arg2) {
		if (_pres == null) {
        	try {
            	_res.sendError(arg1, arg2);
        	} catch (Exception e) {
        	}
    	}	
    }

    /**
     * encodeRedirectUrl method comment.
     */
    public String encodeRedirectURL(String arg1) {
		if (_pres != null)
			return _pres.encodeURL(arg1);
		else	
        	return _res.encodeRedirectURL(arg1);
    }

    /**
     * encodeUrl method comment.
     */
    public String encodeURL(String arg1) {
		if (_pres != null)
			return _pres.encodeURL(arg1);
		else	
	        return _res.encodeURL(arg1);
    }

    /**
     * encodeUrl method, a bit more well behaved than some of the J2EE default implementations.
     */
    public static String encodeURL(String arg1, HttpServletRequest req, HttpServletResponse res) {
        if (doEncode(arg1, req))
            return res.encodeURL(arg1);
        else
            return arg1;
    }

    public void addIntHeader(String header, int value) {
		if (_pres == null)
	        _res.addIntHeader(header, value);
    }

    public void addHeader(String header, String value) {
		if (_pres == null)
	        _res.addHeader(header, value);
    }

    public void addDateHeader(String header, long value) {
		if (_pres == null)
	        _res.addDateHeader(header, value);
    }

    public void setLocale(Locale l) {
    	if (_pres == null)
        	_res.setLocale(l);
    }

    public void setBufferSize(int size) {
		if (_pres == null)
	        _res.setBufferSize(size);
    }

    public void reset() {
		if (_pres == null)
			_res.reset();
    }

    public boolean isCommitted() {
		if (_pres == null)
        	return _res.isCommitted();
        else
        	return false;	
    }

    public Locale getLocale() {
		if (_pres == null)
        	return _res.getLocale();
        else
        	return _preq.getLocale();	
    }

    public int getBufferSize() {
		if (_pres == null)
        	return _res.getBufferSize();
        else	
        	return -1;	
    }

    public void flushBuffer() throws java.io.IOException {
		if (_pres == null)
        	_res.flushBuffer();
    }

    public void resetBuffer() {
    }

    private static boolean doEncode(String loc, HttpServletRequest req) {
        if (loc.startsWith("javascript"))
            return false;
        if (req.isRequestedSessionIdFromCookie())
            return false;
        URL url = toURL(loc, req);
        if (!req.getScheme().equalsIgnoreCase(url.getProtocol()))
            return false;
        if (!req.getServerName().equalsIgnoreCase(url.getHost()))
            return false;
        int serverPort = req.getServerPort();
        if (serverPort == -1) {
            if ("https".equals(req.getScheme()))
                serverPort = 443;
            else
                serverPort = 80;
        }
        int urlPort = url.getPort();
        if (urlPort == -1) {
            if ("https".equals(url.getProtocol()))
                urlPort = 443;
            else
                urlPort = 80;
        }
        if (serverPort != urlPort)
            return (false);
        return true;
    }

    private static URL toURL(String location, HttpServletRequest req) {
        if (location == null)
            return (null);

        URL url = null;
        try {
            url = new URL(location);
        } catch (MalformedURLException e1) {
            String requrl = HttpUtils.getRequestURL(req).toString();
            try {
                url = new URL(new URL(requrl), location);
            } catch (MalformedURLException e2) {
                throw new IllegalArgumentException(location);
            }
        }
        return url;

    }

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#getContentType()
	 */
	public String getContentType() {
		try {
			Method m=_res.getClass().getMethod("getContentType",null);
			return (String) m.invoke(this,null);
		} catch (Exception e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
	 */
	public void setCharacterEncoding(String arg0) {
		Class c[] = {String.class};
		Object o[] = {arg0};
		try {
			Method m=_res.getClass().getMethod("setCharacterEncoding",c);
			m.invoke(this,o);
		} catch (Exception e) {}
	}

}
