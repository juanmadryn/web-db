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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/PageTag.java $
//$Author: Dan $
//$Revision: 82 $
//$Modtime: 8/25/04 11:34a $
/////////////////////////

import com.salmonllc.html.HtmlPageBase;
import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.html.HttpServletResponseWrapper;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspServlet;
import com.salmonllc.personalization.Skin;
import com.salmonllc.personalization.SkinManager;
import com.salmonllc.portlet.PortletInfo;
import com.salmonllc.portlet.SalmonPortlet;
import com.salmonllc.portlet.SalmonPortletException;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DB2SalvageException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The page tag is the parent for all the tags in the library. It controls the flow of and processing for the rest of the tags
 */

public class PageTag extends TagSupport {
	private String _controller;
	private String _secondaryController;
	private String _application;
	private String _contenttype;
	private boolean _alwaysCreateNew = false;
	private String _keepOnSession = null;
	private String _sessionKeepAlive = null;
	private int _startTagRet=0;

	private TagContext _tagContext;
	private boolean _isWml = false;
	private boolean _isWmlMaintained = false;
	private static Hashtable _wmlSessions = new Hashtable();
	private static String _sessidentifier = ";jid";
	private class WmlSession implements HttpSessionBindingListener {
		private String _sessId;
		private HttpSession _sess;
		public WmlSession(String sessId, HttpSession sess) {
			_sessId = sessId;
			_sess = sess;
		}
		public void valueBound(HttpSessionBindingEvent hsbe) {
		}
		public void valueUnbound(HttpSessionBindingEvent hsbe) {
			PageTag.removeSession(_sessId);
			PageTag.removeSession(_sess);
		}
	}

	public static String getSessionIdentifier() {
		return _sessidentifier;
	}

	public static HttpSession getSession(String sessid) {
		if (sessid == null)
			return null;
		return (HttpSession) _wmlSessions.get(sessid);
	}

	public static String getWmlSessId(HttpSession sess) {
		if (sess == null)
			return null;
		return (String) _wmlSessions.get(sess);
	}

	public static void removeSession(String sessid) {
		if (sessid == null)
			return;
		_wmlSessions.remove(sessid);
	}

	public static void removeSession(HttpSession sess) {
		if (sess == null)
			return;
		_wmlSessions.remove(sess);
	}

	private void maintainWmlSession(String sessid, HttpSession sess) {
		_wmlSessions.put(sessid, sess);
		_wmlSessions.put(sess, sessid);
		if (sess.getAttribute("wmlsession") == null)
			sess.setAttribute("wmlsession", new WmlSession(sessid, sess));
	}

	/**
	 * Removes the page from the session
	 */
	public void clearFromSession() {
		HttpSession sess = pageContext.getSession();
		if (sess.getAttribute("$jsp$" + _tagContext.getControllerObject().getSessionKey()) != null)
			sess.removeAttribute("$jsp$" + _tagContext.getControllerObject().getSessionKey());

	}

	/**
	* This method is part of the JSP tag library specification
	*/
	public int doStartTag() throws JspException {
		int retVal = BodyTag.EVAL_BODY_INCLUDE;
		_startTagRet=retVal;
		_isWmlMaintained = false;
		_isWml = false;
		if (_contenttype != null) {
			if (_contenttype.endsWith(".wml")) {
				_isWml = true;
				if (Props.getProps(getApplication(), "").getBooleanProperty(Props.WML_MAINTAIN_SESSIONS))
					_isWmlMaintained = true;
			}
		}
		try {
			HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
			_tagContext = getTagContext();
			try {
				boolean genVars = req.getParameter("vars") != null;
				boolean genCode = req.getParameter("generateCode") != null;
				boolean sessionKeepAlive = req.getParameter("sessionKeepAlive") != null;
				if ((genVars || genCode) && !Props.getSystemProps().getBooleanProperty(Props.SYS_ENABLE_CODE_GENERATION,true)) {
					genVars = false;
					genCode = false;
				}
				_tagContext.setPrintingVars(genVars);
				_tagContext.setGenerateCode(genCode);
				if (sessionKeepAlive) {
					getJSPController().generateSessionKeepAliveHtml(pageContext.getOut());
					_startTagRet=SKIP_PAGE;
					return SKIP_PAGE;
				}
					
			} catch (Exception e) {
			}

			_tagContext.setControllerObject(getJSPController());
            JspController controllerObject = _tagContext.getControllerObject();
            if (!_tagContext.isPrintingVars() && !_tagContext.isGenerateCode()) {
				if (!_tagContext.isInitializing()) {
					long time = System.currentTimeMillis();
					while (controllerObject.isInitializing()) {
						Thread.sleep(500);
						if ((System.currentTimeMillis() - time) > 5000) {
							clearFromSession();
							controllerObject.sendPageRedirect();
							return retVal;
						}
					}

					synchronized (controllerObject) {
						if (!_tagContext.getDreamWeaverMode() && ! controllerObject.isInitialized()) {
							controllerObject.autoBindComponents();
							setUpValidators(_tagContext.getValidatorAttributes(), controllerObject);

			                Skin sk = controllerObject.getSkin();
			                if (sk != null)
            			        controllerObject.applySkin(sk,false);
							
							controllerObject.initializeContainers();
							controllerObject.initialize();
                            if (controllerObject.getSecondaryController() != null)
								controllerObject.getSecondaryController().initialize();

							controllerObject.setInitialized();
						}

						_tagContext.setRefIndexPrinted(false);
						if (_contenttype != null) {
							controllerObject.setContentType(_contenttype);
							(pageContext.getResponse()).setContentType(_contenttype);
							if (_isWmlMaintained) {
								controllerObject.setWMLMaintained(true);
							}
						}
						if (!_isWml) {
							if (controllerObject.isRequestFromPortlet()) {
							   if (controllerObject.getPortletInfo().getRenderRequest() != null) {
								   controllerObject.incrementRefIndex();
								   retVal = controllerObject.doGet(req, true);
							   } 
							   else {
									retVal = controllerObject.doPost(req, (HttpServletResponse) pageContext.getResponse());
							   } 
							}  
							else if (req.getMethod().equals("GET") || controllerObject.isRequestFromForward()) {
                                controllerObject.incrementRefIndex();
                                retVal = controllerObject.doGet(req, true);
                            }
							else if (req.getMethod().equals("POST")) {
								retVal = controllerObject.doPost(req, (HttpServletResponse) pageContext.getResponse());
								HttpServletResponseWrapper res=(HttpServletResponseWrapper) controllerObject.getCurrentResponse();
								if (!controllerObject.getDoPostRedirected() && !res.getRedirectSent())
									controllerObject.doGet(req, true);
							}	
								
						} else {
							if (req.getMethod().equals("POST") || (req.getParameter("method") != null && req.getParameter("method").toUpperCase().equals("POST"))) {
								retVal = controllerObject.doPost(req, (HttpServletResponse) pageContext.getResponse());
							} else
								retVal = controllerObject.doGet(req, true);
						}
					}
				} else if (!_tagContext.getDreamWeaverMode() && (req.getAttribute(JspServlet.SALMON_SERVLET_KEY) == null))
					controllerObject.sendPageRedirect();
			} else if (!_tagContext.getDreamWeaverMode() && (req.getAttribute(JspServlet.SALMON_SERVLET_KEY) == null) && _tagContext.isInitializing())
				controllerObject.sendPageRedirect();
		} catch (SocketException e) {
			// ignore java.net.SocketException
			MessageLog.writeInfoMessage("SocketException would have been thrown", this);

		}
		catch (SalmonPortletException ex) {
			//let this bubble up to the SalmonPortlet and let it handle it
			throw new JspException(ex);
		}	
		catch (Exception e) {
			MessageLog.writeErrorMessage("doStartTag", e, this);
			if (e.getClass().getName().equals("com.ibm.db2.jcc.c.SqlException")) 
				throw new DB2SalvageException(e);
			throw new JspException(e.getMessage());
		}
		_startTagRet=retVal;
		return retVal;
	}

    public int doEndTag() throws JspException {
    	return _startTagRet;
    }


	public static String generateSessionName(PortletInfo info) {
		String jsp = info.getPortletJsp();
		if (jsp == null)
			return info.getPortletName();
		int pos = jsp.indexOf("?");
		if (pos > -1)
			jsp = jsp.substring(0,pos);
		return encodeSessionName(info.getPortletName() + jsp);
	}
		
	public static String generateSessionName(HttpServletRequest req) {
		PortletInfo inf = (PortletInfo) req.getAttribute(SalmonPortlet.PORTLET_INFO_TOKEN);
		if (inf != null) 
			return generateSessionName(inf);
		else	
			return encodeSessionName(req.getRequestURI());
	}
	
	private static String encodeSessionName(String sesName) {
		StringBuffer sb = new StringBuffer(sesName);
			for (int i = 0; i < sb.length(); i++)
				if (sb.charAt(i) == '/')
					sb.setCharAt(i, '.');
			return sb.toString();	
	}	

	/**
	* This method returns the application that the page is associated with
	*/
	public String getApplication() {
		if (_application != null)
		{
			return _application;
		}
		else {
			try {
				String base = URLGenerator.generateServletBaseURL((HttpServletRequest) pageContext.getRequest());
				if (base == null || base.length() == 0)
					return "none";
				int ndx = base.indexOf("/");
				if (ndx == -1)
					return base;
				else
					return base.substring(0, ndx);
			} catch (Exception e) {
				return "none";
			}
		}
		/*
		else if (_controller == null)
		      return "none";
		  else {
		      int pos = _controller.lastIndexOf(".");
		      if (pos == -1)
		          return "none";
		      else {
		          String retVal = _controller.substring(0, pos);
		          pos = retVal.lastIndexOf(".");
		          if (pos != -1)
		              retVal = retVal.substring(pos + 1);
		          return retVal;
		      }
		  }*/
	}
	/**
	* This method returns the name of the JSP Controller object used by the page
	*/
	public String getController() {
		return _controller;
	}


	private String getShortenedSessId(String sessid) {
		String sessID = sessid;
		int iWmlSessLen = Props.getProps(getApplication(), "").getIntProperty(Props.WML_SESSION_KEY_LENGTH);
		if (iWmlSessLen < sessID.length()) {
			String sTmpSessId = sessID.substring(0, iWmlSessLen);
			if (_wmlSessions.get(sTmpSessId) == null) {
				sessID = sTmpSessId;
			} else {
				sTmpSessId = sessID.substring(0, iWmlSessLen - 1);
				for (int i = iWmlSessLen; i < sessID.length(); i++) {
					sTmpSessId += sessID.charAt(i);
					if (_wmlSessions.get(sTmpSessId) == null) {
						sessID = sTmpSessId;
						break;
					}
				}
			}
		}
		return sessID;
	}

	private JspController getJSPController() throws Exception {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
        ClassLoader loader =  Thread.currentThread().getContextClassLoader();

		boolean sessExp = false;
		HttpSession sess = null;
		String sessID = null;
		if (_isWmlMaintained) {
			sessID = req.getParameter(_sessidentifier);
			if (sessID != null) {
				sess = (HttpSession) _wmlSessions.get(sessID);
				if (sess == null) {
					sess = req.getSession(true);
					sessID = sess.getId();
					sessID = getShortenedSessId(sessID);
					maintainWmlSession(sessID, sess);
				}
			} else {
				sess = req.getSession(true);
				sessID = sess.getId();
				sessID = getShortenedSessId(sessID);
				maintainWmlSession(sessID, sess);
			}
		} else {
			sessID = req.getRequestedSessionId();
			boolean sessValid = req.isRequestedSessionIdValid();
			if (sessID != null && !sessValid)
				sessExp = true;
			sess = req.getSession(true);
		}

		synchronized (sess) {
			if (sess.getAttribute("AppServer_SessExp") != null) {
				sessExp = true;
				sess.removeAttribute("AppServer_SessExp");
			}
			
			JspServlet.setUpApplicationContext(pageContext.getServletContext(),(HttpServletRequest) pageContext.getRequest());
			Props p = Props.getProps(getApplication(), null);
			String cName = _controller;
			JspController ret = null;
			JspController sec = null;
			Object o = null;
			String sesName = null;

			_tagContext.setInitializing(false);
			String sessKey = generateSessionName(req);
			sesName = "$jsp$" + sessKey;

			o = sess.getAttribute(sesName);

			if (o != null && _alwaysCreateNew) {
				sess.removeAttribute(sesName);
				o = null;
			}

			if (o != null)
				ret = (JspController) o;
			else {
				_tagContext.setInitializing(true);
				Class c = null;
				Class c2 = null;

				//get the primary controller class
				try {
					if (cName == null)
						c = JspController.class;
					else
						c = Class.forName(cName,true,loader);
				} catch (ClassNotFoundException eX) {
					c = JspController.class;
					if (!(_tagContext.isPrintingVars() || _tagContext.isGenerateCode()))
						MessageLog.writeErrorMessage("Couldn't find controller class:" + cName, eX, this);
				} catch (LinkageError eX) {
					MessageLog.writeErrorMessage("Couldn't find page class:" + cName, eX, this);
					return null;
				}

				//get the secondary controller class
				try {
					if (_secondaryController != null)
						c2 = Class.forName(_secondaryController,true,loader);
				} catch (ClassNotFoundException eX) {
					if (!(_tagContext.isPrintingVars() || _tagContext.isGenerateCode()))
						c2 = JspController.class;
					MessageLog.writeErrorMessage("Couldn't find page class:" + _secondaryController, eX, this);
				} catch (LinkageError eX) {
					MessageLog.writeErrorMessage("Couldn't find page class:" + _secondaryController, eX, this);
					return null;
				}

				Object h = sess.getAttribute(HtmlPageBase.APPLICATION_ALIAS_SESSION_KEY);
				String appAlias = null;
				String application = getApplication();
				if (h != null)
					appAlias = (String) ((Hashtable) h).get(application);
				ret = (JspController) c.newInstance();

				if (c2 != null) {
					sec = (JspController) c2.newInstance();
					ret.setSecondaryController(sec);
				}

				ret.setOrigApplicationName(application);
				if (appAlias != null)
					ret.setApplicationName(appAlias);
				else
					ret.setApplicationName(application);
				ret.setSessionKey(sessKey);

				String pageName = req.getRequestURI();
				int pos = pageName.lastIndexOf("/");
				if (pos > -1)
					pageName = pageName.substring(pos + 1);
				ret.setPageName(pageName);

				ret.setKeepOnSession(BaseTagHelper.stringToBoolean(_keepOnSession, true));
				ret.setSession(sess);
                if (ret.getSkin() == null) {
                	String defSkin = p.getProperty(Props.SKIN_DEFAULT);
                	if (defSkin != null) {
                		SkinManager man = SkinManager.getSkinManager(application);
                		Skin sk = new Skin();
                		man.load(defSkin,sk);	
                		ret.setSkin(sk);
                	}	
                }	
                ret.loadProperties();
				if (_sessionKeepAlive != null)
					ret.setSessionKeepAliveMinutes(BaseTagHelper.stringToInt(_sessionKeepAlive));
				sess.setAttribute(sesName, ret);
			}

			ret.setCurrentRequest(req);
			ret.setCurrentResponse(res);
			ret.setSession(sess);
			ret.setSessionExpired(sessExp);
			ret.setPageContext(pageContext);
			
			if (req.getScheme()==null || req.getScheme().equalsIgnoreCase("http"))
				ret.setServerURL(URLGenerator.generateServerURL(req));
			else
				ret.setServerURL(URLGenerator.generateSecureServerURL(req));
			ret.setServletBaseURL(URLGenerator.generateServletBaseURL(req));
            ret.setForwardPerformed(false);

			PortletInfo pInf=(PortletInfo)req.getAttribute(SalmonPortlet.PORTLET_INFO_TOKEN);
			if (pInf != null)
				ret.setDisableRedirect(true);
			ret.setPortletInfo(pInf);
			ret.setUpLanguagePreferences();
				
			return ret;
		}
	}
	/**
	* This method returns the name of the Secondary JSP Controller object used by the page
	*/
	public String getSecondarycontroller() {
		return _controller;
	}

	/**
	* Releases the resources used by the object
	*/

	public void release() {
		_controller = null;
		_secondaryController = null;
		_application = null;
		_keepOnSession = null;
		_sessionKeepAlive = null;
		_contenttype = null;
		_tagContext = null;
		_isWml = false;
		_isWmlMaintained = false;
		_tagContext = null;
	}

	/**
	 * Set to true if the object should always create a new controller object when the page is called instead of getting the controller from the session.
	 */
	public void setAlwaysCreateNewController(boolean alwaysCreate) {
		_alwaysCreateNew = alwaysCreate;

	}

	/**
	 * Set to true if the object should be kept on the session after it is called for the first time.
	 */
	public void setKeeponsession(String keep) {
		_keepOnSession = keep;
	}
	
	public void setSessionkeepaliveminutes(String minutes) {
		_sessionKeepAlive=minutes;
	}	
	/**
	* This method sets the application attribute for the page
	*/

	public void setApplication(String newApplication) {
		_application = newApplication;
	}
	/**
	* This method sets the controller attribute for the page
	*/
	public void setController(String newController) {
		_controller = newController;
	}
	/**
	* This method sets the contenttype attribute for the page
	*/
	public void setContenttype(String contenttype) {
		_contenttype = contenttype;
	}

	/**
	* This method sets the secondary controller attribute for the page
	*/
	public void setSecondarycontroller(String newController) {
		_secondaryController = newController;
	}

	/**
	 * Sets the dreamWeaverMode.
	 * @param dreamWeaverMode The dreamWeaverMode to set
	 */
	public void setDreamWeaverMode(boolean dreamWeaverMode) {
		getTagContext();
		_tagContext.setDreamWeaverMode(dreamWeaverMode);
	}

	/**
	 * Returns the controllerClass.
	 * @return JspController
	 */
	public JspController getControllerObject() {
		return _tagContext.getControllerObject();
	}

	public TagContext getTagContext() {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		_tagContext = (TagContext) req.getAttribute(TagContext.TAG_CONTEXT_REQ_KEY);
		if (_tagContext == null) {
			_tagContext = new TagContext(_controller);
			req.setAttribute(TagContext.TAG_CONTEXT_REQ_KEY, _tagContext);
		}
		return _tagContext;
	}

	private void setUpValidators(Vector attList, JspController cont) {
		try {
			if (attList != null) {
				for (int i = 0; i < attList.size(); i++) {
					ValidatorTag.Attributes att = (ValidatorTag.Attributes) attList.elementAt(i);
					HtmlValidatorText val = (HtmlValidatorText) cont.getComponent(att.name);
					if (val == null)
						throw new NullPointerException("Could not find validator component:" + att.name);
					ValidatorTag.setUpValidator(att, val, cont);
				}
			}
		}
		catch (Exception e) {
			MessageLog.writeErrorMessage("setUpValidators()",e,this);
		}
	}
}
