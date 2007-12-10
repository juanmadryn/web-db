package infraestructura.controllers;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com

import infraestructura.models.MenuModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * este controlador es base para todos los controladores de la aplicaci�n
 * implementa un cinunto de activiaddes comunes a todos los controladores el
 * resto de los controladores deben extender a este.
 */
public class BaseController extends JspController implements SubmitListener,
		PageListener, Constants {

	/**
	 *
	 */
	private static final long serialVersionUID = 3171333702070890884L;

	private static String menuActual = null;

	private static WebSiteUser _user;
	//private WebSiteUser _user;

	public com.salmonllc.jsp.JspContainer _noCache;

	private boolean _checkSessionExpired = false;

	private boolean _checkPageExpired = false;

	private boolean _checkUserLogin = false;

	private boolean _redirected = true;

	private boolean _checkDB = true;

	/* Search objects */
	// public com.salmonllc.html.HtmlSubmitButton _btnSearch;
	// public com.salmonllc.html.HtmlTextEdit _teSearch;
	public com.salmonllc.jsp.JspRaw _rawAddRow; // This object will add a

	// "</td><td>" string to the nav
	// bar table. It will help us to
	// place the navigation menu
	// horizontally or vertically.

	// public com.salmonllc.html.HtmlSubmitImage _imgFlagFR;
	// public com.salmonllc.html.HtmlSubmitImage _imgFlagTR;
	// public com.salmonllc.html.HtmlSubmitImage _imgFlagUS;

	/* Banner object references */
	public com.salmonllc.html.HtmlText _txtBannerAccount;

	// public com.salmonllc.html.HtmlText _txtBannerCart;
	public com.salmonllc.html.HtmlText _txtBannerSignIn;

	public com.salmonllc.html.HtmlText _txtBannerSignOut;

	public com.salmonllc.html.HtmlText _cantSinProcesarCAP;

	public com.salmonllc.html.HtmlText _cantParDevolverCAP;

	public com.salmonllc.html.HtmlText _cantMovSinConciliarCAP;

	public com.salmonllc.html.HtmlText _cantParaCancelarCAP;

	public com.salmonllc.html.HtmlText _cantSinProcesarTXT;

	public com.salmonllc.html.HtmlText _cantParDevolverTXT;

	public com.salmonllc.html.HtmlText _cantMovSinConciliarTXT;

	public com.salmonllc.html.HtmlText _cantParaCancelarTXT;

	public com.salmonllc.html.HtmlText _barraTXT100;

	public com.salmonllc.html.HtmlText _barraTXT200;

	public com.salmonllc.html.HtmlSubmitButton _menuBUT;

	public com.salmonllc.jsp.JspLink _lnkBannerAccount;

	// public com.salmonllc.jsp.JspLink _lnkBannerCart;
	public com.salmonllc.jsp.JspLink _lnkBannerSignIn;

	public com.salmonllc.jsp.JspLink _lnkBannerSignOut;

	// public com.salmonllc.jsp.JspForm _searchForm;
	public com.salmonllc.html.HtmlText _welcomeUser;

	/* Error message comtainer object */
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;

	/* Navigation menu object */
	public com.salmonllc.jsp.JspTable _navbarTable;

	public com.salmonllc.jsp.JspNavBar _navbar1;

	/* A static sequence generator will be used to generate primary key IDs */
	private static SequenceGenerator _seq_gen = null;

	/* Application properties */
	protected Props _props;

	/* definici�n de TODOS los DataStore usados en la aplicaci�n */
	public infraestructura.models.AccesoMenuModel _dsAccesoMenu;

	public infraestructura.models.AccionAplicacionModel _dsAccionesAplicacion;

	public infraestructura.models.AplicaCircuitoModel _dsAppCir;

	public infraestructura.models.CircuitoEstadoModel _dsCircuito;

	public infraestructura.models.DiccionarioAplicacionDetalleModel _dsDAPdet;

	public infraestructura.models.EstadoModel _dsEstado;

	public infraestructura.models.TransicionEstadoModel _dsTransicionEstado;

	public infraestructura.models.UsuarioRolesModel _dsUsuarioRoles;

	public infraestructura.models.WebsiteUserModel _dsWebSiteUser;

	/**
	 * This method tries to get the string parameter passed into this function
	 * from the URL. It then checks to see if that "name" parameter is of
	 * boolean type. The default is FALSE.
	 *
	 * @param name -
	 *            name of the parameter that is being looked up
	 *
	 */
	public boolean getBooleanParameter(String name) {
		String s = getParameter(name);
		return s != null
				&& (s.equals("true") || s.equals("TRUE") || s.equals("1"));
	}

	/**
	 * Return the text of the request referer, if available, else null.
	 *
	 * @param key -
	 *            name of the cookie
	 * @return java.lang.String
	 */
	public Cookie getCookie(String key) {
		Cookie ret = null;

		Cookie cookArr[] = getCurrentRequest().getCookies();
		if (cookArr != null) {
			for (int i = 0; i < cookArr.length; i++) {
				if (cookArr[i].getName().equals(key)) {
					ret = cookArr[i];
					break;
				}

			}
		}
		return ret;
	}

	/**
	 * This method tries to get the integer value from the parameter in the URL
	 * called passed into this method. If the value of the parameter is not an
	 * integer, a -1 value will be returned.
	 *
	 * @param name -
	 *            name of the parameter that is being looked up
	 * @return int - returns the value of the parameter as an int, if the value
	 *         can NOT be cast to an int -1 is returned
	 */
	public int getIntParameter(String name) {
		String s = getParameter(name);
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * This method - creates a SessionManager - adds a page Listener - adds
	 * listener to the other objects
	 *
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void initialize() throws Exception {

		// lo primero es controlar que se est� conectado a la aplicaci�n.
		// Salvo que sea la home page
		if (!(getPageName().equalsIgnoreCase("HomePage.jsp") || getPageName()
				.equalsIgnoreCase("LoginPage.jsp"))) {
			WebSiteUser user = getSessionManager().getWebSiteUser();
			/*
			 * Si el user es null intenta, por cambio de aplicaci�n si ya lo
			 * tiene seteado
			 */
			if (user == null) {
				/* Setea el usuario y la sessi�n */
				if (_user != null) {
					System.out.println("Usuario: " + _user);
					user = _user;					
					getSessionManager().setWebSiteUser(user);
				}
				/*user = getUserFromCookie();
				getSessionManager().setWebSiteUser(user);*/
			} else if (_user == null)
				/* lo seta ya que nunca fue seteado */
				_user = user;

			if (user == null) {
				removePagesFromSession();
				gotoSiteMapPage(SiteMapConstants.USUARIO_INVALIDO_ERROR);
			}
		}

		setCheckSessionExpired(true);

		// Populate the navigation menu dynamically
		populateNavBar();

		_lnkBannerSignOut.addSubmitListener(this);
		_lnkBannerSignOut.setVisible(false);
		_menuBUT.addSubmitListener(this);

		addPageListener(this);

		// Initilize the session manager.
		if (_seq_gen == null)
			_seq_gen = SequenceGenerator.getSequenceGenerator();
		// Inicializa los datarsorre si los pudo matear
		if (_dsAccesoMenu != null)
			_dsAccesoMenu.setAutoValidate(true);
		if (_dsAccionesAplicacion != null)
			_dsAccionesAplicacion.setAutoValidate(true);
		if (_dsAppCir != null)
			_dsAppCir.setAutoValidate(true);
		if (_dsCircuito != null)
			_dsCircuito.setAutoValidate(true);
		if (_dsDAPdet != null)
			_dsDAPdet.setAutoValidate(true);
		if (_dsEstado != null)
			_dsEstado.setAutoValidate(true);
		if (_dsTransicionEstado != null)
			_dsTransicionEstado.setAutoValidate(true);
		if (_dsUsuarioRoles != null)
			_dsUsuarioRoles.setAutoValidate(true);
		if (_dsWebSiteUser != null)
			_dsWebSiteUser.setAutoValidate(true);

	}

	/**
	 * This method/event will get fired each time a page is requested by the
	 * browser before Html is generated and sent back.
	 *
	 * @param p
	 *            PageEvent
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void pageRequested(PageEvent p) throws Exception {

		// If it is required check for the DB connection, session or page
		// expired.
		checkPageRedirect();

		if (hasPageRedirected())
			p.setContinueProcessing(false);

		if (!isReferredByCurrentPage()) {

			// Set the login links on the top of the page.
			WebSiteUser user = getSessionManager().getWebSiteUser();
			/*
			 * Si el user es null intenta, por cambio de aplicaci�n si ya lo
			 * tiene seteado
			 */
			if (user == null) {
				if (_user != null) {
					System.out.println("Usuario: " + _user);
					// Setea el usuario y la sessi�n
					user = _user;				
					getSessionManager().setWebSiteUser(user);
				}
				/*user = getUserFromCookie();
				getSessionManager().setWebSiteUser(user);*/
			} else if (_user == null)
				/* lo seta ya que nunca fue seteado */
				_user = user;

			if (user != null && user.isValid()) {
				_lnkBannerSignIn.setVisible(false);
				_lnkBannerSignOut.setVisible(true);
				_welcomeUser.setText(user.getApeynom());
				_welcomeUser.setVisible(true);
			} else {
				_lnkBannerSignIn.setVisible(true);
				_lnkBannerSignOut.setVisible(false);
				_welcomeUser.setText("");
				_welcomeUser.setVisible(false);
			}

			// Check if we need to change the page appearence.
			refreshGUIOptions();

			populateNavBar();

		}
	}

	public WebSiteUser getUserFromCookie() {
		Cookie cookieRemMe = getCookie(COOKIE_REMEMBER_ME);
		if (cookieRemMe != null) {
			String sCookieVal = cookieRemMe.getValue();
			if (sCookieVal.equals(TRUE)) {
				Cookie cookieUserName = getCookie(COOKIE_USER_NAME);
				String sUserName = cookieUserName.getValue();
				Cookie cookiePwd = getCookie(COOKIE_USER_PW);
				String sPwd = cookiePwd.getValue();
				if (sUserName != null) {
					WebSiteUser user = new WebSiteUser(getApplicationName(),
							sUserName, sPwd);
					if (user != null && user.isValid()) {
						getSessionManager().setWebSiteUser(user);
						return user;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Changes the page appearance.
	 */
	/**
	 * Determina si el menu es accesible para el usario o alg�n rol que tenga
	 *
	 * @param user_id
	 *            --> id �nico que identifica a un usuario de la aplicaci�n
	 *            website_user
	 * @param menu_id
	 *            --> id �nico que identifiva al menu (tabla menu)
	 */
	public boolean menuPermitido(String sAppName, WebSiteUser user, int menu_id) {
		DBConnection conn = null;
		setApplicationName(sAppName);

		Statement st = null;
		ResultSet r = null;
		try {
			conn = DBConnection.getConnection(sAppName, "infraestructura");
			String SQL = "select user_id,rol_id,menu_id" + " from acceso_menu"
					+ " where (rol_id in " + user.getSetRoles()
					+ " 		or user_id = " + user.getUserID() + ")"
					+ " and menu_id = " + menu_id;
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				return true;
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}

		return false;
	}

	protected void refreshGUIOptions() {

		// Check if there is a "GuiOptions" object in the session. This object
		// will be placed in the session at the "OptionsPage.jsp"
		Object o = getSessionManager().getValue(SESSION_VALUE_GUI_OPTIONS);

		if (o == null)
			return;

		GuiOptions gui = ((GuiOptions) o);

		// Customize the nav bar.
		int iNavBarOrient = gui.getNavBarOrientation();
		customizeNavbar(iNavBarOrient);

	}

	/**
	 * Sets the navigation meno orientation depending on the user preference.
	 *
	 * @param iNavBarOrient
	 */
	private void customizeNavbar(int iNavBarOrient) {
		if (iNavBarOrient == GuiOptions.NAVBAR_HORIZONTAL) {
			// Following code will insert a row between the two table sells in
			// the HTML. See the "banner.jsp" for further info.
			_rawAddRow.setHtml("</tr><tr>");
			_navbar1.setHorizontalMode(true);
		} else {
			_rawAddRow.setHtml("");
			_navbar1.setHorizontalMode(false);
		}
	}

	/**
	 * This method populates the navigation menu dynamically from the database.
	 *
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	protected void populateNavBar() throws DataStoreException, SQLException {

		try {
			boolean menuPermitido = false;

			if (_navbar1 == null)
				return;
			/*
			 * Crea el DataStore mediante el MenuModel
			 */
			DataStore ds;

			ds = new MenuModel(getApplicationName(), "infraestructura");

			/* recupera al usuario de la sessi�n */
			WebSiteUser user = getSessionManager().getWebSiteUser();

			// Recupera men� seg�n padre, si Existe y set�a como submen�
			// si no existen sub menues para dicho grupo, re setea a HomePage
			// que es el men� principal
			menuActual = getSiteMapEntryNameForPage();

			if (menuActual == null) {
				menuActual = "HomePage";
			}

			// Recupero el men� padre del menu actual, para poder retroceder un
			// nivel
			String descMenuAnterior = null;
			String urlMenuAnterior = null;
			// recupero la url del grupo del menu actual
			ds.retrieve("url = " + String.valueOf('"') + menuActual
					+ String.valueOf('"'));
			ds.waitForRetrieve();
			if (ds.gotoFirst())
				urlMenuAnterior = ds.getString("menu." + GRUPO_FIELD);

			// si el menu anterior no es la p�gina principal, recupero su
			// descripci�n
			if (urlMenuAnterior != "HomePage") {
				ds.retrieve("url = " + String.valueOf('"') + urlMenuAnterior
						+ String.valueOf('"'));
				ds.waitForRetrieve();
				if (ds.gotoFirst())
					descMenuAnterior = ds.getString("menu." + NOMBRE_FIELD);
			}

			// empieza recuperaci�n del resto de los menues correspondientes
			ds.retrieve("grupo = " + String.valueOf('"') + menuActual
					+ String.valueOf('"'));
			// Se espera hasta que se recuperen todos los datos
			ds.waitForRetrieve();

			// Se verifica si recuper� algo, si es as� se setea nuevo nivel de
			// men�
			// sino se regresa a HomePage y se recupera nuevamente
			if (ds.getRowCount() == 0) {
				// no se recuperaron menues, setea el men� actual al anterior y
				// recupera
				// menuActual = menuAnterior;
				ds.retrieve("grupo = " + String.valueOf('"') + menuActual
						+ String.valueOf('"'));
				// Se espera hasta que se recuperen todos los datos
				ds.waitForRetrieve();
			}

			String sDesc = "";
			int iID = -1;

			String sUrl = "";

			// Reset the nav bar...
			_navbar1.removeAllGroups();

			// For the different language preferences, find the corresponding
			// word
			// int language properties file. See "petmart.en.properties".
			LanguagePreferences p = getLanguagePreferences();
			String sName = LanguageResourceFinder.getResource(
					getApplicationName(), LANG_PROP_HEADING_HOME_PAGE, p);
			if (sName == null || sName.length() < 1)
				sName = DEFAULT_HEADING_HOME_PAGE;

			// Create the homepage link...
			_navbar1.createGroup(NAVBAR_GROUP, null, "%HomePage", sName, 1);

			// si tanto la url como la descripci�n del menu anterior son
			// v�lidas, a�ado la entrada en la navbar
			if (urlMenuAnterior != null && descMenuAnterior != null)
				_navbar1.createGroup(PREFIX_NAVBAR_GROUP + urlMenuAnterior,
						null, "%" + urlMenuAnterior, descMenuAnterior, 1);

			// Populate the navbar with new values.
			while (ds.gotoNext()) {
				sDesc = ds.getString(MENU_NOMBRE_TFC);
				sUrl = ds.getString(MENU_URL_TFC);
				if (sDesc == null || sDesc.length() < 1) // You do not want
					// to
					// show empty fields in
					// the navigation
					// menu...
					continue;

				// Get the correct translation from the LanguageResourceFinder
				// object
				sName = LanguageResourceFinder.getResource(
						getApplicationName(), PREFIX_NAVBAR + sDesc, p);
				if (sName != null && sName.length() > 0)
					sDesc = sName;

				sUrl = ds.getString(MENU_URL_TFC);

				iID = ds.getInt(MENU_MENU_ID_TFC);

				// verifica el acceso del usuarioal men� si es que est�
				// conectado.
				// si no est� conectado no muestra ning�n men�
				if (user != null) {
					menuPermitido = menuPermitido(getApplicationName(), user,
							iID);
					if (menuPermitido) {
						_navbar1.createGroup(PREFIX_NAVBAR_GROUP + sDesc, null,
								"%" + sUrl, sDesc, 1);
					}
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}

	/**
	 * This method/event will get fired each time a page is requested by the
	 * browser after Html is generated and sent back.
	 *
	 * @param p
	 *            PageEvent
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void pageRequestEnd(PageEvent p) throws Exception {
		return;
	}

	/**
	 * This method occurs each time a page is submitted before submit values are
	 * copied to components.
	 *
	 * @param p
	 *            PageEvent
	 */
	public void pageSubmitted(PageEvent p) {
		checkPageRedirect();
		if (hasPageRedirected())
			p.setContinueProcessing(false);
	}

	/**
	 * This method occurs each time a page is submitted after submit values are
	 * copied to components.
	 *
	 * @param p
	 *            PageEvent
	 */
	public void pageSubmitEnd(PageEvent p) {
		return;
	}

	/**
	 * Replace characters in URL string.
	 *
	 * @param s
	 *            java.lang.String
	 */
	protected static String encodeURL(String s) {
		if (s == null)
			return null;

		StringBuffer b = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ')
				b.append('+');
			else if (c == '&')
				b.append("%26");
			else if (c == '?')
				b.append("%3F");
			else
				b.append(c);
		}
		return b.toString();
	}

	/**
	 * This method is used to signal that a submit button has been pressed, and
	 * that the user has intended to submit the descendant class/page.
	 *
	 * @param e
	 *            SubmitEvent
	 * @throws Exception
	 *             if anything goes wrong
	 * @return true to continue processing events on the page or false to abort
	 */

	public boolean submitPerformed(SubmitEvent e) throws Exception {

		if (e.getComponent() == _lnkBannerSignOut) {
			// Remove the website user object from the session and flip the
			// "Sign in" and "Sign out" links on the banner
			getSessionManager().setWebSiteUser(null);
			_lnkBannerSignIn.setVisible(true);
			_lnkBannerSignOut.setVisible(false);
			Cookie cookieRemMe = getCookie(COOKIE_REMEMBER_ME);
			if (cookieRemMe != null) {
				cookieRemMe.setMaxAge(0);
				getCurrentResponse().addCookie(cookieRemMe);
			}
			gotoSiteMapPage(SiteMapConstants.HOME_PAGE);
			clearAllPagesFromSession();
			/* limpia el usuario seteado */
			System.out.println("Usuario: " + _user);
			_user = null;
		}

		if (e.getComponent() == _menuBUT) {
			// en funci�n de su valor de texto oculta o muestra el menu
			if (_menuBUT.getDisplayName().equalsIgnoreCase("Ocultar Men�")) {
				// oculta el men� y cambia el texto al bot�n
				_rawAddRow.setHtml("</tr><tr>");
				_menuBUT.setDisplayName("Ver Men�");
				_navbarTable.setVisible(false);
			} else if (_menuBUT.getDisplayName().equalsIgnoreCase("Ver Men�")) {
				_rawAddRow.setHtml("");
				_menuBUT.setDisplayName("Ocultar Men�");
				_navbarTable.setVisible(true);
			}
		}

		return true;
	}

	/**
	 * This method removes the pages from the session. That will force the
	 * application to re-generate the page HTML. That is necessary since the
	 * page HTML will change with the different language preferance.
	 */
	protected void removePagesFromSession() {
		HttpSession sess = getSession();
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
	 * @return boolean
	 */
	public boolean isCheckSessionExpired() {
		return _checkSessionExpired;
	}

	/**
	 * @return boolean
	 */
	public boolean isCheckPageExpired() {
		return _checkPageExpired;
	}

	/**
	 * @return boolean
	 */
	public boolean isCheckDB() {
		return _checkDB;
	}

	/**
	 * Set the boolean value to check DB connection
	 *
	 * @param _checkDB
	 *            boolean
	 */
	public void setCheckDB(boolean _checkDB) {
		this._checkDB = _checkDB;
	}

	/**
	 * Set the boolean value to check if user is logged in.
	 *
	 * @param _checkUserLogin
	 *            boolean
	 */
	public void setCheckUserLogin(boolean _checkUserLogin) {
		this._checkUserLogin = _checkUserLogin;
	}

	/**
	 * This method will do some safety checks in the page requested methods.
	 */
	private void checkPageRedirect() {

		try {
			// Sequence of the cehecs are important here. If you check the user
			// Loggin first, session expiration will not be cheched. That may
			// cause errors...
			_redirected = false;
			if (_checkSessionExpired) {
				if (isSessionExpired()) {
					_redirected = true;
					gotoSiteMapPage(SiteMapConstants.SESSION_EXPIRED);
					return;
				}
			}

			if (_checkPageExpired) {
				if (isExpired()) {
					_redirected = true;
					gotoSiteMapPage(SiteMapConstants.PAGE_EXPIRED);
					return;
				}
			}

			if (_checkDB) {
				DBConnection conn = null;
				try {
					conn = DBConnection.getConnection(getApplicationName());
				} catch (Exception e) {
					_redirected = true;
					gotoSiteMapPage(SiteMapConstants.DB_CONNECT_ERROR);
				} finally {
					if (conn != null)
						conn.freeConnection();
				}
			}

			if (_checkUserLogin) { // Check if user login is required
				WebSiteUser webSiteUser = getSessionManager().getWebSiteUser();

				if (webSiteUser == null || !webSiteUser.isValid()) {
					gotoSiteMapPage(SiteMapConstants.LOGIN, "?redir="
							+ getPageName());
					return;
				}
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("checkPageRedirect()", e, this);
		}
	}

	/**
	 * Returns true if either the page or the session is expired and the page
	 * has been redirected to another page indicating that
	 */
	public boolean hasPageRedirected() {
		return _redirected;
	}

	/**
	 * Tells the page whether or not it should check whether the page is expired
	 * on each request
	 */
	public void setCheckPageExpired(boolean check) {
		_checkPageExpired = check;
	}

	/**
	 * Tells the page whether or not it should check whether the session is
	 * expired on each request
	 */
	public void setCheckSessionExpired(boolean check) {
		_checkSessionExpired = check;
	}

	/**
	 * If you want to have the browser cache this page instead of reloading it
	 * each time call this method with a true argument.
	 */
	public void setNoCache(boolean noCache) {
		_noCache.setVisible(noCache);
	}

	/**
	 * Display the error messages in the page. Following objects are dfined in
	 * the message.jsp. Most of the pages include it.
	 *
	 * @param sMessage
	 */
	public void displayErrorMessage(String sMessage) {
		displayErrorMessage(sMessage, null);

	}

	/**
	 * Display the error messages in the page. Following objects are dfined in
	 * the message.jsp. Most of the pages include it.
	 *
	 * @param sMessage
	 *            String
	 * @param comp
	 *            HtmlFormComponent Focus component
	 */
	public void displayErrorMessage(String sMessage, HtmlFormComponent comp) {
		displayErrorMessage(sMessage, null, -1);
	}

	/**
	 * Display the error messages in the page. Following objects are dfined in
	 * the message.jsp. Most of the pages include it.
	 *
	 * @param sMessage
	 *            String
	 * @param comp
	 *            HtmlFormComponent Focus component
	 * @param rowNo
	 *            The row in the datatable with the error
	 */
	public void displayErrorMessage(String sMessage, HtmlFormComponent comp,
			int rowNo) {
		if (_valErrorMessage == null)
			return;

		if (comp != null)
			_valErrorMessage.addErrorMessage(sMessage, comp, rowNo);
		else
			_valErrorMessage.addErrorMessage(sMessage);
		return;
	}

	/**
	 * Returns the error count in the validator
	 *
	 * @return
	 */
	public int getErrorCount() {
		if (_valErrorMessage == null)
			return -1;
		return _valErrorMessage.getErrorCount();
	}

	/**
	 * Returns the sequence generator
	 *
	 * @return SequenceGenerator
	 */
	public SequenceGenerator getSequenceGenerator() {
		return _seq_gen;
	}

	/**
	 * Handle the send request in here. This might be handy if you want to
	 * append parameres to the query string.
	 *
	 * @param sUrl
	 */
	public void sendRedirect(String sUrl) throws IOException {
		if (sUrl == null || sUrl.length() < 1)
			return;

		super.sendRedirect(sUrl);
	}

	/**
	 * Gets the SessionManager for this application.
	 */
	public SessionManager getSessionManager() {
		return new SessionManager(getSession());
	}

	public String armarUrlReporte(String tipo, String reporte, String parametros) {
		String URL = getServerURL();
		URL = URL + getPageProperties().getProperty(BIRT_PATH);
		URL = URL + getPageProperties().getProperty(REPORT_PATH) + reporte;

		if (tipo.equalsIgnoreCase("PDF"))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_PDF);
		else if (tipo.equalsIgnoreCase("HTML"))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_HTML);
		else
			URL = URL + "";

		URL = URL + parametros;

		return URL;
	}

}
