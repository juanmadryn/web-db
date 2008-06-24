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
import infraestructura.models.UsuarioRolesModel;
import infraestructura.utils.Utilities;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

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
 * este controlador es base para todos los controladores de la aplicación
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

	// private static WebSiteUser _user;

	public com.salmonllc.jsp.JspContainer _noCache;

	private boolean _checkSessionExpired = false;

	private boolean _checkPageExpired = true;

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

	public com.salmonllc.html.HtmlText _txtBannerOptions;

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

	public com.salmonllc.jsp.JspLink _lnkBannerSolicitudesPendientes;

	public com.salmonllc.html.HtmlText _txtBannerSolicitudesPendientes;

	public com.salmonllc.jsp.JspLink _lnkBannerOrdenesPendientes;

	public com.salmonllc.html.HtmlText _txtBannerOrdenesPendientes;

	public com.salmonllc.jsp.JspLink _lnkBannerSolicitudesObservadas;

	public com.salmonllc.html.HtmlText _txtBannerSolicitudesObservadas;

	public com.salmonllc.jsp.JspLink _lnkBannerSolicitudesCotizadas;

	public com.salmonllc.html.HtmlText _txtBannerSolicitudesCotizadas;

	public com.salmonllc.jsp.JspLink _lnkBannerOrdenesObservadas;

	public com.salmonllc.html.HtmlText _txtBannerOrdenesObservadas;

	// public com.salmonllc.jsp.JspForm _searchForm;
	public com.salmonllc.html.HtmlText _welcomeUser;

	/* Error message comtainer object */
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;

	/* Navigation menu object */
	public com.salmonllc.jsp.JspTable _navbarTable;

	public com.salmonllc.jsp.JspNavBar _navbar1;

	public com.salmonllc.jsp.JspForm _bannerForm;

	/* A static sequence generator will be used to generate primary key IDs */
	private static SequenceGenerator _seq_gen = null;

	/* Application properties */
	protected Props _props;

	/* definición de TODOS los DataStore usados en la aplicación */
	public infraestructura.models.AccesoMenuModel _dsAccesoMenu;

	public infraestructura.models.AccionAplicacionModel _dsAccionesAplicacion;

	public infraestructura.models.AplicaCircuitoModel _dsAppCir;

	public infraestructura.models.CircuitoEstadoModel _dsCircuito;

	public infraestructura.models.DiccionarioAplicacionDetalleModel _dsDAPdet;

	public infraestructura.models.EstadoModel _dsEstado;

	public infraestructura.models.TransicionEstadoModel _dsTransicionEstado;

	public infraestructura.models.UsuarioRolesModel _dsUsuarioRoles;

	public infraestructura.models.WebsiteUserModel _dsWebSiteUser;

	// hash table that contains all actually logged users - Added by Juan Manuel
	// Cortez at 12/12/2007
	private static Hashtable<String, WebSiteUser> users = new Hashtable<String, WebSiteUser>();

	// In order to manage every session for each application and for each remote
	// ip, a Hashtable was added containing another Hashtable per ip with the
	// applications run by the said ip and its corresponding sessions

	private static Hashtable<String, Hashtable<String, HttpSession>> aplications = new Hashtable<String, Hashtable<String, HttpSession>>();

	private static Hashtable<String, Long> timeStart = new Hashtable<String, Long>();
	
	// msj al usuario para mantenimiento
	public com.salmonllc.html.HtmlText _avisoMantenimiento;

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
		setCheckSessionExpired(true);
		setCheckPageExpired(true);
		// lo primero es controlar que se está conectado a la aplicación.
		// Salvo que sea la home page
		WebSiteUser user = checkUser();
		if (!(getPageName().equalsIgnoreCase("HomePage.jsp") || getPageName()
				.equalsIgnoreCase("LoginPage.jsp"))) {

			// check logged users

			if (user == null) {
				clearAllPagesFromSession();
				gotoSiteMapPage(SiteMapConstants.USUARIO_INVALIDO_ERROR);
			}
		} else if (user == null)
			setCheckPageExpired(false);

		// save the page's session with the ip and application
		String appName = getApplicationName();
		String ip = getCurrentRequest().getRemoteAddr();
		Hashtable<String, HttpSession> aplicationsForRemoteAddress = aplications
				.get(ip);

		if (aplicationsForRemoteAddress == null)
			aplicationsForRemoteAddress = new Hashtable<String, HttpSession>();

		aplicationsForRemoteAddress.put(appName, getSession());

		aplications.put(ip, aplicationsForRemoteAddress);

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

		if (user == null) {
			_lnkBannerSolicitudesPendientes.setVisible(false);
			_txtBannerSolicitudesPendientes.setVisible(false);
			_lnkBannerOrdenesPendientes.setVisible(false);
			_txtBannerOrdenesPendientes.setVisible(false);
			_lnkBannerSolicitudesObservadas.setVisible(false);
			_txtBannerSolicitudesObservadas.setVisible(false);
			_lnkBannerSolicitudesCotizadas.setVisible(false);
			_txtBannerSolicitudesCotizadas.setVisible(false);
			_lnkBannerOrdenesObservadas.setVisible(false);
			_txtBannerOrdenesObservadas.setVisible(false);
			_txtBannerAccount.setVisible(false);
			_txtBannerOptions.setVisible(false);
		}

		if (timeStart.get(ip) == null)
			timeStart.put(ip, System.currentTimeMillis());
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
		WebSiteUser user = checkUser();
		if (user == null)
			setCheckPageExpired(false);
		else
			setCheckPageExpired(true);
		checkPageRedirect();

		if (hasPageRedirected())
			p.setContinueProcessing(false);

		if (!isReferredByCurrentPage()) {

			// Set the login links on the top of the page.

			if (user != null && user.isValid()) {
				_lnkBannerSignIn.setVisible(false);
				_lnkBannerSignOut.setVisible(true);
				_welcomeUser.setText(user.getApeynom());
				_welcomeUser.setVisible(true);
				_txtBannerAccount.setVisible(true);
				_txtBannerOptions.setVisible(true);
			} else {
				_lnkBannerSignIn.setVisible(true);
				_lnkBannerSignOut.setVisible(false);
				_welcomeUser.setText("");
				_welcomeUser.setVisible(false);
				_txtBannerAccount.setVisible(false);
				_txtBannerOptions.setVisible(false);
			}

			// Check if we need to change the page appearence.
			refreshGUIOptions();
		}
		if (user != null) {

			int user_id = user.getUserID();

			int solicitudes_pendientes = 0;
			int ordenes_pendientes = 0;
			int solicitudes_observadas = 0;
			int ordenes_observadas = 0;
			int solicitudes_cotizadas = 0;

			if ((solicitudes_pendientes = Utilities
					.getSolicitudesCompraPendientesAprobacion(user_id)) > 0) {
				_lnkBannerSolicitudesPendientes
						.setHref("/inventario/Jsp/ConsultaSolicitudCompra.jsp?user_id="
								+ user.getUserID() + "&mode=0");
				_txtBannerSolicitudesPendientes
						.setText("Solicitudes pendientes: "
								+ solicitudes_pendientes);
				_lnkBannerSolicitudesPendientes.setVisible(true);
				_txtBannerSolicitudesPendientes.setVisible(true);

			} else {
				_lnkBannerSolicitudesPendientes.setVisible(false);
				_txtBannerSolicitudesPendientes.setVisible(false);
			}

			if ((ordenes_pendientes = Utilities
					.getOrdenesCompraPendientesAprobacion(user_id)) > 0) {
				_lnkBannerOrdenesPendientes
						.setHref("/inventario/Jsp/ConsultaOrdenesCompra.jsp?user_id="
								+ user.getUserID() + "&mode=0");
				_txtBannerOrdenesPendientes.setText("Ordenes pendientes: "
						+ ordenes_pendientes);
				_lnkBannerOrdenesPendientes.setVisible(true);
				_txtBannerOrdenesPendientes.setVisible(true);
			} else {
				_lnkBannerOrdenesPendientes.setVisible(false);
				_txtBannerOrdenesPendientes.setVisible(false);
			}

			if ((solicitudes_observadas = Utilities
					.getSolicitudesCompraPendientesObservacion(user_id)) > 0) {
				_lnkBannerSolicitudesObservadas
						.setHref("/inventario/Jsp/ConsultaSolicitudCompra.jsp?user_id="
								+ user.getUserID() + "&mode=1");
				_txtBannerSolicitudesObservadas
						.setText("Solicitudes observadas: "
								+ solicitudes_observadas);
				_lnkBannerSolicitudesObservadas.setVisible(true);
				_txtBannerSolicitudesObservadas.setVisible(true);

			} else {
				_lnkBannerSolicitudesObservadas.setVisible(false);
				_txtBannerSolicitudesObservadas.setVisible(false);
			}

			if ((ordenes_observadas = Utilities
					.getOrdenesCompraPendientesObservacion(user_id)) > 0) {
				_lnkBannerOrdenesObservadas
						.setHref("/inventario/Jsp/ConsultaOrdenesCompra.jsp?user_id="
								+ user.getUserID() + "&mode=1");
				_txtBannerOrdenesObservadas.setText("Ordenes observadas: "
						+ ordenes_observadas);
				_lnkBannerOrdenesObservadas.setVisible(true);
				_txtBannerOrdenesObservadas.setVisible(true);
			} else {
				_lnkBannerOrdenesObservadas.setVisible(false);
				_txtBannerOrdenesObservadas.setVisible(false);
			}
			
			if (UsuarioRolesModel.isRolUsuario(user_id, "COMPRADOR")) {
				if ((solicitudes_cotizadas = Utilities.getSolicitudesCompraCotizadas(user_id)) > 0) {
					_lnkBannerSolicitudesCotizadas.setHref("/inventario/Jsp/ConsultaSolicitudCompra.jsp?user_id="+user.getUserID()+"&mode=2");
					_txtBannerSolicitudesCotizadas.setText("Solicitudes cotizadas: " + solicitudes_cotizadas);
					_lnkBannerSolicitudesCotizadas.setVisible(true);
					_txtBannerSolicitudesCotizadas.setVisible(true);
				} else {
					_lnkBannerSolicitudesCotizadas.setVisible(false);
					_txtBannerSolicitudesCotizadas.setVisible(false);
				}
			} else {
				_lnkBannerSolicitudesCotizadas.setVisible(false);
				_txtBannerSolicitudesCotizadas.setVisible(false);
			}

		}
		
		// chequea si el sistema va a ser dado de baja y avisa al usuario
		// modificado por Demian Barry el 18/05/2008
		// Codigo protectivo por si la propiedad de MantenimientoSistema
		// no está seteada en el system.properties
		// ya que de otra forma explota la aplicación
		Props props = Props.getProps("infraestructura", "infraestructura");
		String hayMantenimiento = props.getProperty("MantenimientoSistema");
		if (hayMantenimiento != null && !hayMantenimiento.isEmpty()) {
			_avisoMantenimiento.setText("El sistema será detenido a las " 
					+ hayMantenimiento
					+ " horas para tareas de mantenimiento. Guarde los trabajos realizados y salga de su cuenta.");
			_avisoMantenimiento.setVisible(true);			
		} else {			
			_avisoMantenimiento.setVisible(false);
		}
		
		populateNavBar();
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
	 * Determina si el menu es accesible para el usario o algún rol que tenga
	 * 
	 * @param user_id
	 *            --> id único que identifica a un usuario de la aplicación
	 *            websitestoredUser
	 * @param menu_id
	 *            --> id único que identifiva al menu (tabla menu)
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

			if (_navbar1 == null) {
				return;
			}
			/*
			 * Crea el DataStore mediante el MenuModel
			 */
			DataStore ds;

			ds = new MenuModel(getApplicationName(), "infraestructura");

			// Recupera menú según padre, si Existe y setéa como submenú
			// si no existen sub menues para dicho grupo, re setea a HomePage
			// que es el menú principal
			menuActual = getSiteMapEntryNameForPage();

			if (menuActual == null) {
				menuActual = "HomePage";
			}

			// Recupero el menú padre del menu actual, para poder retroceder un
			// nivel
			String descMenuAnterior = null;
			String urlMenuAnterior = null;
			// recupero la url del grupo del menu actual
			ds.retrieve("url = " + String.valueOf('"') + menuActual
					+ String.valueOf('"'));
			ds.waitForRetrieve();
			if (ds.gotoFirst())
				urlMenuAnterior = ds.getString("menu." + GRUPO_FIELD);

			// si el menu anterior no es la página principal, recupero su
			// descripción
			if (urlMenuAnterior != "HomePage") {
				ds.retrieve("url = " + String.valueOf('"') + urlMenuAnterior
						+ String.valueOf('"'));
				ds.waitForRetrieve();
				if (ds.gotoFirst())
					descMenuAnterior = ds.getString("menu." + NOMBRE_FIELD);
			}

			// empieza recuperación del resto de los menues correspondientes
			ds.retrieve("grupo = " + String.valueOf('"') + menuActual
					+ String.valueOf('"'));
			// Se espera hasta que se recuperen todos los datos
			ds.waitForRetrieve();

			// Se verifica si recuperó algo, si es así se setea nuevo nivel de
			// menú
			// sino se regresa a HomePage y se recupera nuevamente
			if (ds.getRowCount() == 0) {
				// no se recuperaron menues, setea el menú actual al anterior y
				// recupera

				menuActual = urlMenuAnterior;
				ds.retrieve("grupo = " + String.valueOf('"') + menuActual
						+ String.valueOf('"')); //
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

			DataStore dsAux = new MenuModel(getApplicationName(),
					"infraestructura");
			dsAux.retrieve("url = \"" + urlMenuAnterior + "\"");
			dsAux.waitForRetrieve();
			String urlAux = null;
			if (dsAux.gotoFirst())
				urlAux = dsAux.getString("menu." + GRUPO_FIELD);

			if (descMenuAnterior != null
					&& (urlMenuAnterior != menuActual || (!urlAux
							.equalsIgnoreCase("HomePage"))))
				_navbar1.createGroup(PREFIX_NAVBAR_GROUP + urlMenuAnterior,
						null, "%" + urlMenuAnterior, "Página anterior", 1);

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

				// verifica el acceso del usuarioal menó si es que estó
				// conectado.
				// si no estó conectado no muestra ningón menó
				/* recupera al usuario de la sessión */
				WebSiteUser user = checkUser();
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
			displayErrorMessage(e.getMessage() + "\n" + e.getStackTrace());
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
			// getSessionManager().setWebSiteUser(null);
			_lnkBannerSignIn.setVisible(true);
			_lnkBannerSignOut.setVisible(false);
			Cookie cookieRemMe = getCookie(COOKIE_REMEMBER_ME);
			if (cookieRemMe != null) {
				cookieRemMe.setMaxAge(0);
				getCurrentResponse().addCookie(cookieRemMe);
			}

			removeSessionsForIp();

			// removes the user
			users.remove(getCurrentRequest().getRemoteAddr());

			gotoSiteMapPage(SiteMapConstants.HOME_PAGE);
		}

		if (e.getComponent() == _menuBUT) {
			// en función de su valor de texto oculta o muestra el menu
			if (_menuBUT.getDisplayName().equalsIgnoreCase("Ocultar Menú")) {
				// oculta el menú y cambia el texto al botón
				_rawAddRow.setHtml("</tr><tr>");
				_menuBUT.setDisplayName("Ver Menú");
				_navbarTable.setVisible(false);
			} else if (_menuBUT.getDisplayName().equalsIgnoreCase("Ver Menú")) {
				_rawAddRow.setHtml("");
				_menuBUT.setDisplayName("Ocultar Menú");
				_navbarTable.setVisible(true);
			}
		}

		checkPageRedirect();		
		return true;
	}

	private void removeSessionsForIp() {
		// Removes the WebSiteUser and clear all pages from all sessions of
		// applications that were initialized
		Hashtable<String, HttpSession> aplicationsForRemoteAddress = aplications
				.get(getCurrentRequest().getRemoteAddr());

		aplications.remove(getCurrentRequest().getRemoteAddr());

		if (aplicationsForRemoteAddress != null) {
			Enumeration<HttpSession> aplicaciones = aplicationsForRemoteAddress
					.elements();
			HttpSession sess;
			while (aplicaciones.hasMoreElements()) {
				sess = aplicaciones.nextElement();
				clearAllPagesFromSession(sess);
				sess.setAttribute(SESSION_VALUE_WEBSITE_USER, null);
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
		String ip = getCurrentRequest().getRemoteAddr();
		try {
			// Sequence of the cehecs are important here. If you check the user
			// Loggin first, session expiration will not be cheched. That may
			// cause errors...

			_redirected = false;

			if (isCheckPageExpired()) {
				// Get the now time
				Long time = System.currentTimeMillis();
				// Get the timer from .properties to check if page expired
				Props props = getPageProperties();
				int i = Integer.parseInt(props.getThemeProperty(null,
						"SegundosTimer"));

				// compares the elapsed time since page was requested with the
				// timer

				if (((time - timeStart.get(ip)) / 1000) > i) {

					_redirected = true;

					// remove sessions and users
					removeSessionsForIp();
					users.remove(ip);
					gotoSiteMapPage(SiteMapConstants.SESSION_EXPIRED);
					return;
				}

			}
			/*
			 * if (_checkSessionExpired) { if (isSessionExpired()) { _redirected =
			 * true; gotoSiteMapPage(SiteMapConstants.SESSION_EXPIRED); return; } }
			 */

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

		timeStart.put(ip, System.currentTimeMillis());
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

		if (tipo == null)
			URL += getPageProperties().getProperty(BIRT_FRAMESET_PATH);
		else
			URL += getPageProperties().getProperty(BIRT_RUN_PATH);

		URL += getPageProperties().getProperty(REPORT_PATH) + reporte
				+ ".rptdesign";

		if (tipo != null && "PDF".equalsIgnoreCase(tipo))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_PDF);
		else if (tipo != null && "HTML".equalsIgnoreCase(tipo))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_HTML);
		else if (tipo != null && "XLS".equalsIgnoreCase(tipo))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_XLS);
		else if (tipo != null && "DOC".equalsIgnoreCase(tipo))
			URL = URL + getPageProperties().getProperty(REPORT_PARAMETROS_DOC);
		else
			URL = URL + "";

		URL = URL + parametros;

		return URL;
	}

	/**
	 * Checks if there are an conected user.
	 * 
	 * @return the user conected or null if there isn't
	 */
	public WebSiteUser checkUser() {
		WebSiteUser user = getSessionManager().getWebSiteUser();
		String ip = this.getCurrentRequest().getRemoteAddr();
		WebSiteUser storedUser = users.get(ip);

		// If user is null, check if there are a stored user into users'
		// hashtable.
		if (user == null) {
			// If user is in users' hashtable, set it as actual user
			if (storedUser != null) {
				user = storedUser;
			}
			// If user isn't null put it in users hashtable
		} else if (storedUser == null)
			users.put(ip, user);
		// save user in the session
		getSessionManager().setWebSiteUser(user);
		return user;
	}

	public WebSiteUser getUserFromSession(String ip) {
		return users.get(ip);
	}

}
