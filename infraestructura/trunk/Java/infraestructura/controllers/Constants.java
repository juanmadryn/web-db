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

package infraestructura.controllers;

public interface Constants {

	public static final String PARAM_REDIRECT = "redir";
	public static final String PARAM_SEACRCH_CRITERIA = "searchCri";

	public static final String SESSION_VALUE_USER_PWD = "sessUserPwd";
	public static final String SESSION_VALUE_USER_NAME = "sessUserName";
	public static final String SESSION_VALUE_GUI_OPTIONS = "GUIOptions";
	public static final String SESSION_VALUE_WEBSITE_USER = "SessionManager_WebUser";

	//Language preferances constants
	public static final String LANG_PROP_LANGUAGE_ENGLISH = "en";
	public static final String LANG_PROP_LANGUAGE_FRENCH = "fr";
	public static final String LANG_PROP_LANGUAGE_TURKISH = "tr";
	public static final String LANG_PROP_LANGUAGE_SPANISH = "sp";
	public static final String LANG_PROP_DISPLAY_FORMAT = "Displayformat";
	public static final String LANG_PROP_HEADING_HOME_PAGE = "HdngHomePage";

	public static final String DEFAULT_DISPLAY_FORMAT =  "$ #,##0.00";
	public static final String DEFAULT_HEADING_HOME_PAGE = "Página Principal";


	//Navigation menu constants
	public static final String NAVBAR_GROUP = "navbarGroup";

	//Property values
	public static final String PROPERTY_VAL_DEFAULT_USER_NAME = "defaultUserLoginName";
	public static final String PROPERTY_VAL_DEFAULT_USER_PASSWORD = "defaultUserLoginPassword";

	//Prefixes
	public static final String PREFIX_NAVBAR = "navBar";
	public static final String PREFIX_NAVBAR_GROUP = "navbarGroup_";

	//Other
	public static final String TRUE = "1";
	public static final String FALSE = "0";
	public static final String COOKIE_REMEMBER_ME = "infraestructura.RememberMe";
	public static final String COOKIE_USER_NAME = "infraestructura.userName";
	public static final String COOKIE_USER_PW = "infraestructura.userPw";
	public static final String COOKIE_USER_ID = "infraestructura.iserID";
	public static final int COOKIE_MAX_AGE =1000000;


	/* DATABASE TABLE CONSTANTS*/
	// tables
	static final String ACCESO_MENU_TABLE = "acceso_menu";
	static final String MENU_TABLE = "menu";
	static final String PREFERENCIAS_TABLE = "preferencias";
	static final String ROLES_TABLE = "roles";
	static final String USUARIO_PREFERENCIAS_TABLE = "usuario_preferencias";
	static final String USUARIO_ROLES_TABLE = "usuario_roles";
	static final String USUARIOS_TABLE = "usuario_roles";
	static final String	WEBSITE_USER_TABLE = "website_user";
	static final String	SEQUENCE_TABLE = "sequence";

	// tables_fields
	static final String MENU_MENU_ID_TFC = "menu.menu_id";
	static final String MENU_NOMBRE_TFC = "menu.nombre";
	static final String MENU_DESCRIPCION_TFC = "menu.descripcion";
	static final String MENU_URL_TFC = "menu.url";
	static final String MENU_MENU_PADRE_TFC = "menu.menu_padre";
	static final String	WEBSITE_USER_USER_ID_TFC = "website_user.user_id";
	static final String	WEBSITE_USER_LOGIN_PASSWORD_TFC = "website_user.login_password";
	static final String	WEBSITE_USER_LOGIN_NAME_TFC = "website_user.login_name";
	static final String	USUARIOS_USER_ID_TFC = "usuarios.user_id";
	static final String	USUARIOS_LEGAJO_TFC = "usuarios.legajo";

	// fields
	static final String MENU_ID_FIELD = "menu_id";
	static final String NOMBRE_FIELD = "nombre";
	static final String DESCRIPCION_FIELD = "descripcion";
	static final String URL_FIELD = "url";
	static final String GRUPO_FIELD = "grupo";
	static final String	LOGIN_NAME_FIELD = "login_name";
	static final String	USER_ID_FIELD = "user_id";
	static final String	LOGIN_PASSWORD_FIELD = "login_password";
	static final String LEGAJO_FIELD = "legajo";
	static final String	SEQUENCE_SEQ_TYPE_TFC = "sequence.seq_type";
	static final String	SEQUENCE_SEQ_ID_TFC = "sequence.seq_id";
	static final String	SEQ_TYPE_FIELD = "seq_type";
	static final String	SEQ_ID_FIELD = "seq_id";

	// Prodiedades Custom
	static final String BIRT_RUN_PATH = "BirtRunPath";
	static final String BIRT_FRAMESET_PATH = "BirtFramesetPath";
	static final String REPORT_PATH = "ReportPath";
	static final String REPORT_PARAMETROS_PDF = "ParametrosReportePDF";
	static final String REPORT_PARAMETROS_HTML = "ParametrosReporteHTML";
	static final String REPORT_PARAMETROS_XLS = "ParametrosReporteXLS";
	static final String REPORT_PARAMETROS_DOC = "ParametrosReporteDOC";
	static final String ESTADOS_DE_MODIFICACION_SOLICITUDES_COMPRA = "EstadosDeModificacionSolicitudesCompra";
	static final String ESTADOS_DE_MODIFICACION_RECEPCIONES_COMPRA = "EstadosDeModificacionRecepcionesCompra";
	static final String ESTADOS_DE_MODIFICACION_COMPROBANTE_MOVIMIENTO_ARTICULO = "EstadosDeModificacionComprobanteMovimientoArticulo";
	static final String ESTADOS_DE_MODIFICACION_ORDENES_COMPRA = "EstadosDeModificacionOrdenesCompra";
	static final String LISTA_UNIDADES_MEDIDA = "ListaUnidadesMedida";
	
	static final String IVA_OC = "IVA_ORDEN_COMPRA";
	static final String DESCUENTO_OC = "DESCUENTO_ORDEN_COMPRA";
	static final String NETO_OC = "NETO_ORDEN_COMPRA";
	static final String TOTAL_OC = "TOTAL_ORDEN_COMPRA";
	
	static final String ARTICULO_IVA_PORCENTAJE = "ARTICULO_IVA_PORCENTAJE";

	static final String TIPO_MOVIMIENTO_RECEPCIONES = "TipoMovimientoRecepciones";
	
	static final String ARTICULO_STOCK_MINIMO = "ARTICULO_STOCK_MINIMO";
	static final String ARTICULO_UNIDAD_MEDIDA = "ARTICULO_UNIDAD_MEDIDA";
	
	static final String USER_ENCARGADO_ALMACEN = "ALMACENERO";
	
	static final String ACCION_CONFIRMA_MOVIMIENTO = "AccionConfirmaMovimiento";
	
	static final String ALMACEN_GENERAL = "AlmacenGeneralId";
	
	// Nombre de atributo que almacena el nro. de orden de compra generado por tango.
	static final String N_ORDEN_CO = "N_ORDEN_CO";
}

