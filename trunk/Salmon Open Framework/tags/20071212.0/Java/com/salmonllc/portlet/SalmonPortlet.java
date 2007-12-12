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
package com.salmonllc.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import com.salmonllc.html.HttpServletRequestWrapper;
import com.salmonllc.html.HttpServletResponseWrapper;
import com.salmonllc.html.HttpSessionWrapper;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.tags.PageTag;
import com.salmonllc.properties.Props;
import com.salmonllc.util.ApplicationContext;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;

/**
 * Provides an interface between the portlet api and a SOFIA JSP page
 **/
public class SalmonPortlet extends GenericPortlet {

	public static final String PORTLET_INFO_TOKEN = "$$$SalmonPortletInfo$$$";
	private static final String PORTLET_CREATE_TOKEN = "SalmonPortletCreateToken";

	/* (non-Javadoc)
	 * @see javax.portlet.Portlet#render(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public void render(RenderRequest req, RenderResponse res) throws PortletException, IOException {
		res.setContentType("text/html");
		if (req.getWindowState().equals(WindowState.MINIMIZED))
			return;
		setUpApplicationContext(getPortletContext());
		
		String url = getPortletJsp(req.getPortletMode());
		PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(url);
		PortletInfo info = new PortletInfo();

		info.setPortletRenderURL(res.createRenderURL().toString());
		info.setPortletActionURL(res.createActionURL().toString());
		info.setPortletName(getPortletName());
		info.setNameSpace(res.getNamespace());
		info.setParmameterMap(req.getParameterMap());
		info.setPortletJsp(url);
		info.setRenderRequest(req);
		info.setRenderResponse(res);
		info.setPortletTitle(getTitle(req));

		JspController cont = getJspController(req, info);
		if (!handleException(cont, true, res))
			return;

		if (cont != null) {
			PortletInfo oldInfo = cont.getPortletInfo();
			if (oldInfo != null)
				info.setFromPost(oldInfo.isFromPost());
		} else {
			PortletSession sess = req.getPortletSession(true);
			Props p = Props.getSystemProps();
			String sessKey = "$jsp$" + PageTag.generateSessionName(info) + "createPage";
			if (sess.getAttribute(sessKey, PortletSession.APPLICATION_SCOPE) == null) {
				sess.setAttribute(sessKey, "true", PortletSession.APPLICATION_SCOPE);
				if (!p.getBooleanProperty(Props.SYS_REPLACE_JSP_FACTORY, true)) {
					String rUrl = info.getPortletRenderURL();
					createPage(req, res, req.getPortletSession(true), rUrl, sessKey);
				}
				sess.removeAttribute(sessKey, PortletSession.APPLICATION_SCOPE);
			}
		}

		req.setAttribute(PORTLET_INFO_TOKEN, info);

		try {
			rd.include(req, res);
			cont = getJspController(req, info);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error rendering portlet", e, this);
			throw new PortletException(e);
		}
		handleException(cont, true, res);

	}

	/* (non-Javadoc)
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	public void processAction(ActionRequest req, ActionResponse res) throws PortletException, IOException {
		setUpApplicationContext(getPortletContext());
		PortletInfo info = new PortletInfo();
		info.setPortletName(getPortletName());
		info.setParmameterMap(req.getParameterMap());
		info.setPortletJsp(getPortletJsp(req.getPortletMode()));
		info.setFromPost(true);
		info.setActionRequest(req);
		info.setActionResponse(res);
		req.setAttribute(PORTLET_INFO_TOKEN, info);
		JspController cont = getJspController(req, res, info);
		try {
			if (cont != null) {
				cont.setPortletInfo(info);
				cont.doPost(new HttpServletRequestWrapper(req), new HttpServletResponseWrapper(res, req, cont));
			}
		} catch (SalmonPortletException ex) {
			handleException(cont, false, null);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error posting to portlet", e, this);
			throw new PortletException(e);
		}

	}

	private JspController getJspController(RenderRequest req) {
		PortletInfo info = new PortletInfo();
		info.setPortletName(getPortletName());
		info.setPortletJsp(getPortletJsp(req.getPortletMode()));
		info.setRenderRequest(req);
		return getJspController(req, info);

	}
	private JspController getJspController(RenderRequest req, PortletInfo info) {
		JspController cont = null;
		String sessKey = PageTag.generateSessionName(info);
		String sessName = "$jsp$" + sessKey;

		PortletSession sess = req.getPortletSession(true);
		Object o = sess.getAttribute(sessName, PortletSession.APPLICATION_SCOPE);
		if (o == null)
			return null;
		else
			return (JspController) o;
	}

	private JspController getJspController(ActionRequest req, ActionResponse res, PortletInfo info) {
		JspController cont = null;
		String sessKey = PageTag.generateSessionName(info);
		String sessName = "$jsp$" + sessKey;

		PortletSession sess = req.getPortletSession(true);
		Object o = sess.getAttribute(sessName, PortletSession.APPLICATION_SCOPE);
		if (o == null)
			return null;
		else {
			cont = (JspController) o;

			//since the namespace isn't passed in an action request, get it from the render request
			PortletInfo oldInfo = cont.getPortletInfo();
			info.setNameSpace(oldInfo.getNameSpace());
			cont.setCurrentRequest(req);
			cont.setCurrentResponse(res, req);
			cont.setSession(new HttpSessionWrapper(sess));
			cont.setSessionExpired(false);

			cont.setServletBaseURL(req.getContextPath());
			cont.setForwardPerformed(false);
			cont.setUpLanguagePreferences();
			HttpServletRequest sReq = cont.getCurrentRequest();
			if (req.getScheme() == null || req.getScheme().equalsIgnoreCase("http"))
				cont.setServerURL(URLGenerator.generateServerURL(sReq));
			else
				cont.setServerURL(URLGenerator.generateSecureServerURL(sReq));
		}
		return cont;

	}

	private String getPortletJsp(PortletMode mode) {
		String modeSt = mode.toString();
		int pos = modeSt.indexOf(";");
		if (pos > -1)
			modeSt=modeSt.substring(0,pos);
		String url = getPortletConfig().getInitParameter(modeSt + "JSP");
		if (url == null)
			url = getPortletConfig().getInitParameter("JSP");
		return url;
	}

	private void createPage(RenderRequest req, RenderResponse res, PortletSession sess, String url, String pageID) {
		try {
			int questionPos = url.indexOf("?");
			String page = "";
			String parms = "";
			if (questionPos != -1) {
				parms = url.substring(questionPos);
				page = url.substring(0, questionPos);
			} else
				page = url;
			int semiPos = page.indexOf(";");
			if (semiPos != -1)
				page = page.substring(0, semiPos);
			page = page + ";JSESSIONID=" + sess.getId() + parms;
			URL u = new URL(page + parms);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();

			conn.setRequestProperty("Cookie", "sesessionid=" + sess.getId() + ";session=" + sess.getId() + ";sessionid=" + sess.getId() + ";JSESSIONID=" + sess.getId());
			conn.setRequestProperty("Accept-Language", constructLanguageString(req.getLocales()));
			conn.setDoInput(true);
			conn.setUseCaches(false);
			HttpURLConnection.setFollowRedirects(false);
			InputStream in = conn.getInputStream();
			while (in.read() > -1);
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error creating page", e, this);
		}
	}

	public static String constructLanguageString(Enumeration enumera) {
		StringBuffer langString = new StringBuffer();
		while (enumera.hasMoreElements()) {
			langString.append(enumera.nextElement().toString());
			langString.append(",");
		}
		if (langString.length() > 0)
			langString.setLength(langString.length() - 1);
		return langString.toString();
	}
	/* (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#getTitle(javax.portlet.RenderRequest)
	 */
	protected String getTitle(RenderRequest req) {
		JspController cont = getJspController(req);
		if (cont != null && cont.getTitle() != null)
			return cont.getTitle();
		else
			return super.getTitle(req);
	}

	private boolean handleException(JspController cont, boolean throwError, RenderResponse res) throws PortletException, IOException {
		if (cont != null && cont.getPortletException() != null) {
			SalmonPortletException ex1 = cont.getPortletException();
			if (throwError)
				cont.clearPortletException();
			if (ex1.getLogMessage()) {
				MessageLog.writeErrorMessage("Error in portlet", ex1.getRealException(), this);
				ex1.messageLogged();
			}
			if (ex1.getThrowMessage() && throwError)
				throw (ex1.getRealException());

			if (res != null && ex1.getDisplayMessage() != null) {
				PrintWriter pw = res.getWriter();
				pw.print(ex1.getDisplayMessage());
				pw.flush();
				pw.close();
			}

			return false;

		}
		return true;

	}
	
    public static void setUpApplicationContext(PortletContext sContext) {
        ApplicationContext context=ApplicationContext.getContext();
        if (context == null) {
            context=new ApplicationContext();
            ApplicationContext.setContext(context);
        }
        context.setAppDocumentRoot(sContext.getRealPath("/"));
        String webAppName;
        try {
            webAppName = sContext.getResource("/").toString();
            if (webAppName.endsWith("/"))
                webAppName=webAppName.substring(0,webAppName.length() - 1);
            int pos = webAppName.lastIndexOf("/");
            if (pos > -1)
                webAppName=webAppName.substring(pos + 1);
            if (webAppName==null)
                webAppName="";
            context.setAppID(webAppName);
            context.setLogger(MessageLog.getLoggerForApplication(webAppName));

        } catch (MalformedURLException e) {
            MessageLog.writeErrorMessage ("setUpApplicationContext", e, null);
        }
    }
}
