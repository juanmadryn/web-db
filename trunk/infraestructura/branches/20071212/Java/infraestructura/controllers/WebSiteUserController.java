//package statement
package infraestructura.controllers;

import infraestructura.models.UsuarioRolesModel;
import infraestructura.models.WebsiteUserModel;

import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.sql.DataStoreException;

//Salmon import statements


/**
 * WebSiteUserController: a SOFIA generated controller
 */
public class WebSiteUserController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = 674147795690380653L;
	//Visual Components
      public com.salmonllc.html.HtmlDropDownList _rolTE4;
      public com.salmonllc.html.HtmlLookUpComponent _documentoField;
      public com.salmonllc.html.HtmlText _apeynomCAP11;
      public com.salmonllc.html.HtmlText _apeynomTXT7;
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _desdeCAP13;
      public com.salmonllc.html.HtmlText _desdeCAP14;
      public com.salmonllc.html.HtmlText _desdeTXT8;
      public com.salmonllc.html.HtmlText _hastaCAP14;
      public com.salmonllc.html.HtmlText _hastaCAP15;
      public com.salmonllc.html.HtmlText _hastaTXT9;
      public com.salmonllc.html.HtmlText _loginNameCAP12;
      public com.salmonllc.html.HtmlText _loginNameCAP17;
      public com.salmonllc.html.HtmlText _nombreCompletoCAP17;
      public com.salmonllc.html.HtmlText _emailCAP17;
      public com.salmonllc.html.HtmlText _loginNameTXT8;
      public com.salmonllc.html.HtmlText _loginPasswordCAP18;
      public com.salmonllc.html.HtmlText _nombreRolCAP11;
      public com.salmonllc.html.HtmlText _nombreRolTXT7;
      public com.salmonllc.html.HtmlText _rolCAP14;
      public com.salmonllc.html.HtmlText _rolIdCAP10;
      public com.salmonllc.html.HtmlText _rolIdTXT6;
      public com.salmonllc.html.HtmlText _text1Footer;
      public com.salmonllc.html.HtmlText _text3Footer;
      public com.salmonllc.html.HtmlText _userIdCAP10;
      public com.salmonllc.html.HtmlText _userIdTE9;
      public com.salmonllc.html.HtmlText _userIdTXT6;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _desdeTE4;
      public com.salmonllc.html.HtmlTextEdit _hastaTE5;
      public com.salmonllc.html.HtmlTextEdit _loginNameTE7;
      public com.salmonllc.html.HtmlTextEdit _nombreCompletoTE7;
      public com.salmonllc.html.HtmlTextEdit _emailTE7;
      public com.salmonllc.html.HtmlTextEdit _loginPasswordTE8;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspBox _box3;
      public com.salmonllc.jsp.JspBox _box4;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox2;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;
      // componentes custom
      public com.salmonllc.html.HtmlSubmitButton _agregarCustom;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public WebsiteUserModel _dsWebSiteUser;
      public UsuarioRolesModel _dsUsuarioRoles;
      

//DataSource Column Constants
       public static final String DSQBE_BUSCAR="buscar";

       public static final String DSUSUARIOROLES_USUARIO_ROLES_USER_ID="usuario_roles.user_id";
       public static final String DSUSUARIOROLES_USUARIO_ROLES_ROL_ID="usuario_roles.rol_id";
       public static final String DSUSUARIOROLES_USUARIO_ROLES_DESDE="usuario_roles.desde";
       public static final String DSUSUARIOROLES_USUARIO_ROLES_HASTA="usuario_roles.hasta";
       public static final String DSUSUARIOROLES_USUARIO_ROLES_ANULADO="usuario_roles.anulado";
       public static final String DSUSUARIOROLES_ROLES_NOMBRE="roles.nombre";

       public static final String DSWEBSITEUSER_WEBSITE_USER_USER_ID="website_user.user_id";
       public static final String DSWEBSITEUSER_WEBSITE_USER_LOGIN_NAME="website_user.login_name";
       public static final String DSWEBSITEUSER_WEBSITE_USER_LOGIN_PASSWORD="website_user.login_password";
       public static final String DSWEBSITEUSER_WEBSITE_NOMBRE_COMPLETO="website_user.nombre_completo";
       public static final String DSWEBSITEUSER_WEBSITE_EMAIL="website_user.email";

	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		
		// ante cada requerimiento verifica contexto y determina detalle de
		// estados y completa FK's
		// Es row de usuario válida?
		
		try {
			boolean actualizar = false;
			int usuario = 0;
			int usuario_rol = 0;
			if (_dsWebSiteUser.getRow() != -1) {
				usuario = _dsWebSiteUser.getInt(DSWEBSITEUSER_WEBSITE_USER_USER_ID);
				// Ya existe detalle de roles asignados?
				if (_dsUsuarioRoles.getRowCount() > 0) {
					// es el mismo contexto?
					usuario_rol = _dsUsuarioRoles.getInt(0,DSUSUARIOROLES_USUARIO_ROLES_USER_ID);
					if (_dsUsuarioRoles.getRowCount() == 1 && usuario_rol == 0){
						// Es la primera de las inserciones, no hago nada
					} else {
						if (usuario_rol != usuario) {
							// cambio contexto, debo actualizar
							actualizar = true;
						}
					}
				}
				else {
					actualizar = true;
				}
			}
			if (actualizar) {
				_dsUsuarioRoles.reset();
				_dsUsuarioRoles.retrieve("usuario_roles.user_id = " + usuario);
			}
			
			// reviso el contexto de datos a actualizar o grabar
			if (_dsWebSiteUser.getRow() != -1 && _dsUsuarioRoles.getRow() != -1 && _dsUsuarioRoles.getInt(DSUSUARIOROLES_USUARIO_ROLES_USER_ID) == 0){
				_dsUsuarioRoles.setInt(DSUSUARIOROLES_USUARIO_ROLES_USER_ID,usuario);
				_dsUsuarioRoles.setString(DSUSUARIOROLES_USUARIO_ROLES_ANULADO,"F");
			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
   	}
				
}
