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
package com.salmonllc.servlets;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/AppServer.java $
//$Author: Dan $
//$Revision: 11 $
//$Modtime: 8/24/04 4:20p $
/////////////////////////

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlPageBase;
import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;
import com.salmonllc.personalization.Skin;

/**
 * This servlet is the entry point for all servlet based pages in the framework. JSP Pages do not use it.<BR>
 * It will handle get and post calls from the browser and direct them to the appropriate com.salmonllc.html.HtmlPage class<BR>
 * The page will be located through additional path info appended to this servlets URL.<BR>
 *    Example: http://hostname/webapp/AppServer/app1/Page1<BR>
 * will load the page Page1 for the application app1.<BR>
 * <BR>
 * The page should be a subclass of com.salmonllc.html.HtmlPageBase and should reside in a package named with the following convention:<BR>
 *		[basepackage].applicationname<BR>
 * where [basepackage] is the value of the ApplicationPackage property in the properties file and applicationname is the name of the application specified in the URL.<BR>
 *    Example: If the system properties file has the token: ApplicationPackage=com.mycompany.apps and the URL specified is: http://hostname/com.salmonllc.servlets.AppServer/app1/Page1 the class: com.mycompany.apps.app1.Page1 would be loaded.<BR><BR>
 *<BR>
 * If the class does not exist or isn't a subclass of com.salmonllc.html.HtmlPageBase the page manager will return an error to the browser.
 * dan
 */

public class AppServer extends HttpServlet {
    int _count = 0;
    boolean _schedulerStarted = false;
    boolean _doDumps = false;

    /**
     * This method handles get events from the browser.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	MessageLog.writeInfoMessage("doGet(): Path=" + req.getPathInfo(), this);
        if (_doDumps)
            dumpHeaders(req);

        //if (!_schedulerStarted) {
            //String path = req.getPathInfo();
            //_schedulerStarted = true;

            //String redirect = URLGenerator.generateServerURL(req) + "/" + URLGenerator.generateServletBaseURL(req) + "/com.salmonllc.servlets.Scheduler?ret=" + path;
            //PrintWriter pw = res.getWriter();
            //res.setStatus(res.SC_OK);
            //res.sendRedirect(redirect);
            //pw.close();
            //return;
        //}

        long time = System.currentTimeMillis();
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setHeader("Cache-Control", "no-cache");

        res.setStatus(HttpServletResponse.SC_OK);

        PrintWriter pw = res.getWriter();
        try {
            HtmlPageBase p = getPage(req, res);
            if (p != null) {
                synchronized (p) {
                    if (p.getSubPageName() == null) {
                        if (p.getContentType() == null)
                            res.setContentType("text/html");
                        else
                            res.setContentType(p.getContentType());
                    } else if (p instanceof HtmlPage) {
                        String type = ((HtmlPage) p).getSubPageMimeType(p.getSubPageName());
                        if (type == null)
                            res.setContentType("text/html");
                        else
                            res.setContentType(type);
                    } else {
                        if (p.getContentType() == null)
                            res.setContentType("text/html");
                        else
                            res.setContentType(p.getContentType());
                    }
                    if (req.getParameterValues("iFrameSubmitFormParms") != null)
                        p.processParms(true);
                    p.setCurrentRequest(req);
                    p.setCurrentResponse(res);
                    p.generateHTML(pw);

                }
            } else {
                if (!res.isCommitted()) {
                    res.setContentType("text/html");
                    pw.println("<HTML><HEAD></HEAD><BODY>");
                    pw.println("Error: Page not found");
                    pw.println("</BODY></HTML>");
                }
            }
        } catch (Exception e) {
            HtmlPageBase.displayError("Error in get to url", e, pw);
            MessageLog.writeErrorMessage("Error in get to url:" + req.getPathInfo(), e, this);
        }
        //pw.flush();
        //pw.close();
        com.salmonllc.jsp.JspServlet.addPageHit(System.currentTimeMillis() - time);
    }

    /**
     * This method handles post events from the browser.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	long time = System.currentTimeMillis();
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	MessageLog.writeInfoMessage("doPost(): Path=" + req.getPathInfo(), this);
        if (_doDumps)
            dumpHeaders(req);

        res.setContentType("text/html");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setHeader("Cache-Control", "no-cache");

        res.setStatus(HttpServletResponse.SC_OK);

        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        try {
            HtmlPageBase p = getPage(req, res);
            if (p != null) {
                synchronized (p) {
                    p.setCurrentRequest(req);
                    p.setCurrentResponse(res);
                    p.processParms(false);
                    if (!p.getHistoryFixUp()) {
                        //p.generateHTML(pw);
                        String url = p.getPageName();
                        if (p.getSubPageName() != null)
                            url += "-" + p.getSubPageName();
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
                        }

                        if (queryString != null) {
                            url += "?" + queryString;
                            url += "&pagePostSerialID=" + _count++;
                        } else
                            url += "?pagePostSerialID=" + _count++;

                        //pw.println("<HTML><HEAD></HEAD><BODY>");
                        //pw.println("<SCRIPT>");
                        //pw.println(" location.replace('" + url + "');");
                        //pw.println("</SCRIPT>");
                        //pw.println("</BODY></HTML>");

                        com.salmonllc.html.HttpServletResponseWrapper w = (com.salmonllc.html.HttpServletResponseWrapper) p.getCurrentResponse();
                        if (!w.getRedirectSent() && !p.getDisableRedirect())
                            w.sendRedirect(url, false);
                        else
                            p.generateHTML(pw);
                    } else {
                        pw.println("<HTML><HEAD></HEAD><BODY>");
                        pw.println("<SCRIPT>");
                        pw.println(" window.history.go(-1);");
                        pw.println("</SCRIPT>");
                        pw.println("</BODY></HTML>");
                    }
                }
            } else
                pw.println("Error: Page not found");
        } catch (Exception e) {
            HtmlPageBase.displayError("Error in post to url", e, pw);
            MessageLog.writeErrorMessage("Error in post to url:" + req.getPathInfo(), e, this);
        }

        pw.close();
        com.salmonllc.jsp.JspServlet.addPageHit(System.currentTimeMillis() - time);


    }

    private void dumpHeaders(HttpServletRequest req) {

        StringBuffer out = new StringBuffer();
        out.append("\n---------------------------------\n");
        out.append(req.getMethod() + " " + req.getPathInfo());
        out.append('\n');
        out.append("-----------Headers---------------");
        out.append('\n');

        Enumeration e = req.getHeaderNames();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            out.append(s + "=" + req.getHeader(s));
            out.append('\n');
        }

        out.append("------------Parms-----------------");
        out.append('\n');
        e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            out.append(s + "=" + req.getParameter(s));
            out.append('\n');
        }
        out.append("-------------End------------------");
        MessageLog.writeDebugMessage(out.toString(), this);

    }

    private HtmlPageBase getPage(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String app = null;
        String page = null;
        String subPage = null;

        boolean sessExp = false;

        String sessID = req.getRequestedSessionId();
        boolean sessValid = req.isRequestedSessionIdValid();

        if (sessID != null && !sessValid)
            sessExp = true;

        String path = req.getPathInfo();
        HttpSession sess = req.getSession(true);

        synchronized (sess) {
            if (sess.getAttribute("AppServer_SessExp") != null) {
                sessExp = true;
                sess.removeAttribute("AppServer_SessExp");
            }

            try {
                StringTokenizer t = new StringTokenizer(path, "/");
                app = t.nextToken();
                page = t.nextToken();
            } catch (Exception e) {
                Props pr = Props.getSystemProps();
                if (app == null) {
                    app = pr.getProperty(Props.SYS_DEFAULT_APP);
                }

                if (app == null)
                    return null;

                pr = Props.getProps(app, null);
                page = pr.getProperty(Props.SYS_DEFAULT_PAGE);

                if (page == null)
                    page = pr.getProperty(app + "." + Props.SYS_DEFAULT_PAGE);

                if (page == null)
                    return null;

                if (sessExp)
                    sess.setAttribute("AppServer_SessExp", "Yes");

                PrintWriter pw = res.getWriter();
                res.setStatus(HttpServletResponse.SC_OK);
                res.sendRedirect(URLGenerator.generateServerURL(req) + "/" + URLGenerator.generateServletBaseURL(req) + "/com.salmonllc.servlets.AppServer/" + app + "/" + page);
                pw.close();
                return null;
            }

            int pos = page.indexOf("-");
            if (pos > -1) {
                subPage = page.substring(pos + 1);
                page = page.substring(0, pos);
            }

            Props p = Props.getProps(app, page);
            String cName = p.getProperty(Props.SYS_APP_PACKAGE);
            String cName2 = cName;
            if (cName == null) {
                cName = app + "." + page;
                cName2 = page;
            } else {
                cName += "." + app + "." + page;
                cName2 += "." + page;
            }

            HtmlPageBase ret = null;
            Object o = null;
            String sesName = null;


            if (cName.endsWith("genPgNdx")) {
                sesName = "$page$" + cName.substring(0, cName.length() - 8);
                o = sess.getAttribute(sesName);
                cName = cName.substring(0, cName.lastIndexOf("_"));
                cName2 = cName2.substring(0, cName2.lastIndexOf("_"));
            } else if (cName.endsWith("_")) {
                cName = cName.substring(0, cName.length() - 1);
                int i = 0;
                while (true) {
                    sesName = "$page$" + cName + "_" + i;
                    if (sess.getAttribute(sesName) == null) {
                        String redirect = req.getServletPath() + "/" + app + "/" + page + i + "genPgNdx";
                        if (req.getQueryString() != null)
                            redirect += "?" + req.getQueryString();
                        res.sendRedirect(redirect);
                        return null;
                    }
                    ;
                    i++;
                }
            } else {
                sesName = "$page$" + cName;
                o = sess.getAttribute(sesName);
            }

            boolean init = false;

            if (o != null)
                ret = (HtmlPageBase) o;
            else {
                Class c = null;

                try {
                    c = Class.forName(cName);
                } catch (LinkageError e) {
                    try {
                        c = Class.forName(cName2);
                    } catch (ClassNotFoundException eX) {
                        MessageLog.writeErrorMessage("Couldn't find page class:" + cName + " or " + cName2, eX, this);
                        return null;
                    } catch (LinkageError eX) {
                        MessageLog.writeErrorMessage("Couldn't find page class:" + cName + " or " + cName2, eX, this);
                        return null;
                    }
                } catch (ClassNotFoundException e) {
                    try {
                        c = Class.forName(cName2);
                    } catch (ClassNotFoundException eX) {
                        MessageLog.writeErrorMessage("Couldn't find page class:" + cName + " or " + cName2, eX, this);
                        return null;
                    }
                } catch (Exception e) {
                    MessageLog.writeErrorMessage("Execption getting page:" + cName, e, this);
                    throw(e);
                }

                Object h = sess.getAttribute(HtmlPageBase.APPLICATION_ALIAS_SESSION_KEY);
                String appAlias = null;
                if (h != null)
                    appAlias = (String) ((Hashtable) h).get(app);

                ret = (HtmlPageBase) c.newInstance();
                ret.setPageName(page);
                ret.setOrigApplicationName(app);
                if (appAlias != null)
                    ret.setApplicationName(appAlias);
                else
                    ret.setApplicationName(app);
                ret.setSession(sess);
                Skin sk = ret.getSkin();
                if (sk != null)
                    ret.applySkin(sk,false);
                ret.loadProperties();
                sess.setAttribute(sesName, ret);
                init = true;
            }

            ret.setSubPageName(subPage);
            ret.setCurrentRequest(req);
            ret.setCurrentResponse(res);
            ret.setSession(sess);
            ret.setSessionExpired(sessExp);
            ret.setServerURL(URLGenerator.generateServerURL(req));
            ret.setServletBaseURL(URLGenerator.generateServletBaseURL(req));
            ret.setUpLanguagePreferences();
            if (init) {
                ret.initialize();
                if (ret instanceof HtmlPage)
                    ((HtmlPage) ret).setFormType();
            }

            return ret;
        }
    }

    /**
     * This method occurs when the servlet is initialized
     */
    public void init(ServletConfig s) throws ServletException {
        Properties p = System.getProperties();
        //p.put("server.root","D:\\IBMVJava3.5\\ide\\project_resources\\IBM Websphere Test Environment");
        if (p.getProperty("server.root") == null) {
            String serverRoot = s.getInitParameter("server.root");
            if (serverRoot != null)
                p.put("server.root", serverRoot);
        }
        super.init(s);
    }
}
