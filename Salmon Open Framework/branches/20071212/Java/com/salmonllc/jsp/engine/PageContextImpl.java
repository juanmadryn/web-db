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
// ====================================================================
//
// The Apache Software License, Version 1.1
//
// Copyright (c) 1999 The Apache Software Foundation.  All rights
// reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in
//    the documentation and/or other materials provided with the
//    distribution.
//
// 3. The end-user documentation included with the redistribution, if
//    any, must include the following acknowlegement:
//       "This product includes software developed by the
//        Apache Software Foundation (http://www.apache.org/)."
//    Alternately, this acknowlegement may appear in the software itself,
//    if and wherever such third-party acknowlegements normally appear.
//
// 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
//    Foundation" must not be used to endorse or promote products derived
//    from this software without prior written permission. For written
//    permission, please contact apache@apache.org.
//
// 5. Products derived from this software may not be called "Apache"
//    nor may "Apache" appear in their names without prior written
//    permission of the Apache Group.
//
//  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
//  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
//  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
//  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
//  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
//  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
//  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
//  SUCH DAMAGE.
//  ====================================================================
//
//  This software consists of voluntary contributions made by many
//  individuals on behalf of the Apache Software Foundation.  For more
//  information on the Apache Software Foundation, please see
//  <http://www.apache.org/>.

package com.salmonllc.jsp.engine;

import com.salmonllc.util.MessageLog;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Stack;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * This class implements a J2EE Page Context Object.
 */

public class PageContextImpl extends PageContext implements Serializable {
    Stack _writerStack = new Stack();
    protected Servlet _servlet;
    protected ServletConfig _config;
    protected ServletContext _context;
    protected boolean _needsSession;
    protected String _errorPageURL;
    protected boolean _autoFlush;
    protected int _bufferSize;

    protected transient Hashtable _attributes = new Hashtable(16);
    protected transient ServletRequest _request;
    protected transient ServletResponse _response;
    protected transient Object _page;

    protected transient HttpSession _session;
    protected transient JspWriter _out;

    private BodyContentImpl _dummyBodyContent;
    private boolean _printContent = true;


    /**
     * Empties the stack of JSPWriters used by this page context
     */
    public void clearWriterStack() throws IOException {
        for (int i = 0; i < _writerStack.size(); i++) {
            try {
                JspWriter w = (JspWriter) _writerStack.elementAt(i);
                if (w instanceof BodyContentImpl) {
                    ((BodyContentImpl) w).clearDreamWeaverConv();
                    w.clearBuffer();
                    BodyContentImpl.freeBodyContent((BodyContentImpl) w);
                }
            } catch (Exception e) {
            }
        }
        _writerStack.removeAllElements();

    }
    protected JspWriter createOut(int bufferSize, boolean autoFlush) throws IOException, IllegalArgumentException {
        try {
            return new JspWriterImpl(_response, _bufferSize, _autoFlush, _printContent);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    /**
     * Searches for the named attribute in page, request, session (if valid), and application scope(s) in order and returns the value associated or null.
     */
    public Object findAttribute(String name) {
        Object o = _attributes.get(name);
        if (o != null)
            return o;

        o = _request.getAttribute(name);
        if (o != null)
            return o;

        if (_session != null) {
            o = _session.getAttribute(name);
            if (o != null)
                return o;
        }

        return _context.getAttribute(name);
    }
    /**
     * This method is used to re-direct, or "forward" the current ServletRequest and ServletResponse to another active component in the application
     */
    public void forward(String relativeUrlPath) throws ServletException, IOException {
        String path = getAbsolutePathRelativeToContext(relativeUrlPath);
        try {
	        _context.getRequestDispatcher(path).forward(_request, _response);
        }    
        catch (ServletException e) {
			MessageLog.writeErrorMessage("forward()",e,this);
			throw e;
		}		
		catch (IOException e) {
			MessageLog.writeErrorMessage("forward()",e,this);
			throw e;
		}
    }
    private final String getAbsolutePathRelativeToContext(String relativeUrlPath) {
        String path = relativeUrlPath;

        if (!path.startsWith("/")) {
            String uri = (String) _request.getAttribute("javax.servlet.include.servlet_path");
            if (uri == null)
                uri = ((HttpServletRequest) _request).getServletPath();
            String baseURI = uri.substring(0, uri.lastIndexOf('/'));
            path = baseURI + '/' + path;
        }

        return path;
    }
    /**
     *  return the object associated with the name in the page scope or null
     */


    public Object getAttribute(String name) {
        return _attributes.get(name);
    }
    /**
     *  return the object associated with the name in the page scope or null
     */

    public Object getAttribute(String name, int scope) {
        switch (scope) {
            case PAGE_SCOPE:
                return _attributes.get(name);

            case REQUEST_SCOPE:
                return _request.getAttribute(name);

            case SESSION_SCOPE:
                if (_session == null)
                    throw new IllegalArgumentException("can't access SESSION_SCOPE without an HttpSession");
                else
                    return _session.getAttribute(name);

            case APPLICATION_SCOPE:
                return _context.getAttribute(name);

            default :
                throw new IllegalArgumentException("unidentified scope");
        }
    }
    /**
     * Returns an enumeration of all the attributes in a specified scope
     */
    public Enumeration getAttributeNamesInScope(int scope) {
        switch (scope) {
            case PAGE_SCOPE:
                return _attributes.keys();

            case REQUEST_SCOPE:
                return _request.getAttributeNames();

            case SESSION_SCOPE:
                if (_session != null) {
                    return _session.getAttributeNames();
                } else
                    throw new IllegalArgumentException("can't access SESSION_SCOPE without an HttpSession");

            case APPLICATION_SCOPE:
                return _context.getAttributeNames();

            default :
                return new Enumeration() {
                    // empty enumeration
                    public boolean hasMoreElements() {
                        return false;
                    }

                    public Object nextElement() {
                        throw new NoSuchElementException();
                    }
                };
        }
    }
    /**
     * Returns the scope of the object associated with the name specified or 0
     */
    public int getAttributesScope(String name) {
        if (_attributes.get(name) != null)
            return PAGE_SCOPE;

        if (_request.getAttribute(name) != null)
            return REQUEST_SCOPE;

        if (_session != null) {
            if (_session.getAttribute(name) != null)
                return SESSION_SCOPE;
        }

        if (_context.getAttribute(name) != null)
            return APPLICATION_SCOPE;

        return 0;
    }
    /**
     * Returns any exception passed to this as an errorpage
     */
    public Exception getException() {
        return (Exception) _request.getAttribute(EXCEPTION);
    }
    /**
     * Returns the current JspWriter stream being used for client response
     */
    public JspWriter getOut() {
        return _out;
    }
    /**
     * Returns the Page implementation class instance (Servlet) associated with this PageContext
     */
    public Object getPage() {
        return _servlet;
    }
    /**
     * Returns the ServletRequest for this PageContext
     */
    public ServletRequest getRequest() {
        return _request;
    }
    /**
     * Returns the ServletResponse for this PageContext
     */
    public ServletResponse getResponse() {
        return _response;
    }
    /**
     * Returns the Servlet for this PageContext
     */
    public Servlet getServlet() {
        return _servlet;
    }
    /**
     * Returns the ServletConfig for this PageContext
     */

    public ServletConfig getServletConfig() {
        return _config;
    }
    /**
     * Returns the ServletContext for this PageContext
     */

    public ServletContext getServletContext() {
        return _config.getServletContext();
    }
    /**
     * Returns the current user session
     */
    public HttpSession getSession() {
        return _session;
    }
    /**
     * returns the stack of writers used by the page.
     */
    public Stack getStack() {
        return _writerStack;
    }
    /**
     * This method is intended to process an unhandled "page" level exception by redirecting the exception to either the specified error page for this JSP, or if none was specified, to perform some implementation dependent action.
     */
    public void handlePageException(Exception e) throws IOException, ServletException {

        // set the request attribute with the exception.
        _request.setAttribute("javax.servlet.jsp.jspException", e);

        if (_errorPageURL != null && !_errorPageURL.equals("")) {
            try {
                forward(_errorPageURL);
            } catch (IllegalStateException ise) {
                include(_errorPageURL);
            }
        } // Otherwise throw the exception wrapped inside a ServletException.
        else {
            // Set the exception as the root cause in the ServletException
            // to get a stack trace for the real problem
            if (e instanceof IOException)
                throw (IOException) e;
            if (e instanceof ServletException)
                throw (ServletException) e;
            //	    e.printStackTrace();
            throw new ServletException(e);
        }

    }
    /**
     * This method is intended to process an unhandled "page" level exception by redirecting the exception to either the specified error page for this JSP, or if none was specified, to perform some implementation dependent action
     */
    public void handlePageException(Throwable throwable) throws ServletException, IOException {
        MessageLog.writeErrorMessage(throwable, this);
    }
    /**
     * Causes the resource specified to be processed as part of the current ServletRequest and ServletResponse being processed by the calling Thread.
     */
    public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
        String path = getAbsolutePathRelativeToContext(relativeUrlPath);
        javax.servlet.RequestDispatcher d = _context.getRequestDispatcher(path);

        //weblogic 6 & Silverstream gets upset if you pass anything but the original response to the include method
        //Tomcat calls the flushbuffer, which causes things like send redirect to fail.
        //This provides a work around for everybody (tomcat always gets a dummy response that it can do anything to and can't break anything)
        //Weblogic & Silverstream gets the real response which it doesn't seem to break

        //Uncomment the line below for Debugging Purposes if you have a problem with a particular Web Server
        //System.out.println("d Class Name is "+d.getClass().getName());
		try {
	        if (_printContent || d.getClass().getName().startsWith("weblogic") || d.getClass().getName().startsWith("com.sssw")) {
	    	    if (flush)
	    	       _out.flush();
        	    d.include(_request, _response);
	        } else {
	    	    if (flush)
	    	       _out.flush();
    	        d.include(_request, new com.salmonllc.html.HttpServletResponseDummy(null, null));
        	}
		}
		catch (ServletException e) {
			MessageLog.writeErrorMessage("include()",e,this);
			throw e;
		}		
		catch (IOException e) {
			MessageLog.writeErrorMessage("include()",e,this);
			throw e;
		}		
    }
    /**
     * The initialize emthod is called to initialize an uninitialized PageContext so that it may be used by a JSP Implementation class to service an incoming request and response wihtin it's _jspService() method.<BR><BR>
     * This method is typically called from JspFactory.getPageContext() in order to initialize state.<BR><BR>
     * This method is required to create an initial JspWriter, and associate the "out" name in page scope with this newly created object.
     */
    public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException, IllegalStateException, IllegalArgumentException {

        // initialize state

        _servlet = servlet;
        _config = _servlet.getServletConfig();
        _context = _config.getServletContext();
        _needsSession = needsSession;
        _errorPageURL = errorPageURL;
        _bufferSize = bufferSize;
        _autoFlush = autoFlush;
        _request = request;
        _response = response;

        // setup session (if required)
        if (_request instanceof HttpServletRequest && needsSession)
            _session = ((HttpServletRequest) _request).getSession();

        if (needsSession && _session == null)
            throw new IllegalStateException("Page needs a session and none is available");

        // initialize the initial out ...
        //	System.out.println("Initialize PageContextImpl " + out );
        if (_out == null) {
            _out = createOut(bufferSize, autoFlush); // throws
        } else
            ((JspWriterImpl) _out).init(response, bufferSize, autoFlush);

        if (_out == null)
            throw new IllegalStateException("failed initialize JspWriter");

        // register names/values as per spec

        setAttribute(OUT, _out);
        setAttribute(REQUEST, request);
        setAttribute(RESPONSE, response);

        if (_session != null)
            setAttribute(SESSION, _session);

        setAttribute(PAGE, servlet);
        setAttribute(CONFIG, _config);
        setAttribute(PAGECONTEXT, this);
        setAttribute(APPLICATION, _context);
    }
    /**
     * Return the previous JspWriter "out" saved by the matching pushBody(), and update the value of the "out" attribute in the page scope attribute namespace of the PageConxtext
     */
    public JspWriter popBody() {
        if (_out instanceof BodyContentImpl && (!(_out == _dummyBodyContent))) {
            BodyContentImpl.freeBodyContent((BodyContentImpl) _out);
            ((BodyContentImpl) _out).clearDreamWeaverConv();
        }
        _out = (JspWriter) _writerStack.pop();
        return _out;
    }
    /**
     * Return a new BodyContent object, save the current "out" JspWriter, and update the value of the "out" attribute in the page scope attribute namespace of the PageContext
     */
    public BodyContent pushBody() {
        if (_printContent) {
            JspWriter previous = _out;
            _writerStack.push(_out);
            _out = BodyContentImpl.getBodyContent(previous,_printContent);
        } else {
            _writerStack.push(_out);
            if (_dummyBodyContent == null)
                _dummyBodyContent = new BodyContentImpl(_out, _printContent);
            _dummyBodyContent.setPrintContent(false);
            _out = _dummyBodyContent;
        }
        return (BodyContent) _out;
    }
    /**
     * This method shall "reset" the internal state of a PageContext, releasing all internal references, and preparing the PageContext for potential reuse by a later invocation of initialize().
     */
    public void release() {
        _servlet = null;
        _config = null;
        _context = null;
        _needsSession = false;
        _errorPageURL = null;
        _bufferSize = JspWriter.DEFAULT_BUFFER;
        _autoFlush = true;
        _request = null;
        _response = null;
        if (_out instanceof JspWriterImpl)
            ((JspWriterImpl) _out).recycle();
        _session = null;
        _out = null;
        try {
            clearWriterStack();
        } catch (Exception e) {
        }
        _attributes.clear();


        _writerStack = null;
        _attributes = null;
        _page = null;
        _dummyBodyContent = null;


    }
    /**
     * remove the object reference associated with the specified name
     */
    public void removeAttribute(String name) {
        try {
            removeAttribute(name, PAGE_SCOPE);
            removeAttribute(name, REQUEST_SCOPE);
            removeAttribute(name, SESSION_SCOPE);
            removeAttribute(name, APPLICATION_SCOPE);
        } catch (Exception ex) {
            // we remove as much as we can, and
            // simply ignore possible exceptions
        }
    }
    /**
     * remove the object reference associated with the specified name
     */
    public void removeAttribute(String name, int scope) {
        switch (scope) {
            case PAGE_SCOPE:
                _attributes.remove(name);
                break;

            case REQUEST_SCOPE:
                _request.removeAttribute(name);

            case SESSION_SCOPE:
                if (_session == null)
                    throw new IllegalArgumentException("can't access SESSION_SCOPE without an HttpSession");
                else
                    _session.removeAttribute(name);
                break;

            case APPLICATION_SCOPE:
                _context.removeAttribute(name);
                break;

            default :
        }
    }
    /**
     * Changes the servlet response for the context
     */
    public void replaceResponse(ServletResponse res) {
        _response = res;

    }
    /**
     * register the name and object specified with appropriate scope semantics
     */
    public void setAttribute(String name, Object attribute) {
        _attributes.put(name, attribute);
    }
    /**
     * register the name and object specified with appropriate scope semantics
     */
    public void setAttribute(String name, Object o, int scope) {
        switch (scope) {
            case PAGE_SCOPE:
                _attributes.put(name, o);
                break;

            case REQUEST_SCOPE:
                _request.setAttribute(name, o);
                break;

            case SESSION_SCOPE:
                if (_session == null)
                    throw new IllegalArgumentException("can't access SESSION_SCOPE without an HttpSession");
                else
                    _session.setAttribute(name, o);
                break;

            case APPLICATION_SCOPE:
                _context.setAttribute(name, o);
                break;

            default :
        }
    }
    /**
     * Set whether the Impl will return a real BodyContent or a stub
     */
    public void setPrintContent(boolean print) {
        _printContent = print;
    }
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.PageContext#include(java.lang.String, boolean)
	 */
	public void include(String arg0) throws ServletException, IOException {
		include(arg0,true);
	}
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getExpressionEvaluator()
	 */
	public ExpressionEvaluator getExpressionEvaluator() {
		return null;
	}
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspContext#getVariableResolver()
	 */
	public VariableResolver getVariableResolver() {
		return null;
	}

}
