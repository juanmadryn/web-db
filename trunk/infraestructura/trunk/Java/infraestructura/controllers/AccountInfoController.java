//package statement
package infraestructura.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * AccountInfoController: a SOFIA generated controller
 */
public class AccountInfoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015974742772724093L;
	// Visual Components
	public com.salmonllc.html.HtmlPasswordEdit _passwordAnteriorIMP1;
	public com.salmonllc.html.HtmlPasswordEdit _passwordNuevaIMP1;
	public com.salmonllc.html.HtmlPasswordEdit _passwordReNuevaIMP1;
	public com.salmonllc.html.HtmlText _emailTXT1;
	public com.salmonllc.html.HtmlText _idTXT1;
	public com.salmonllc.html.HtmlText _idTXT2;
	public com.salmonllc.html.HtmlText _nombreCompletoTXT1;
	public com.salmonllc.html.HtmlText _passwordAnteriorTXT1;
	public com.salmonllc.html.HtmlText _passwordNuevaTXT1;
	public com.salmonllc.html.HtmlText _passwordReNuevaTXT1;
	public com.salmonllc.html.HtmlText _textSeparador;	
	public com.salmonllc.html.HtmlText _usuarioTXT1;
	public com.salmonllc.html.HtmlTextEdit _emailIMP1;
	public com.salmonllc.html.HtmlTextEdit _nombreCompletoIMP1;
	public com.salmonllc.html.HtmlTextEdit _usuarioIMP1;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspTable _table1;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _table1TDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table1TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public infraestructura.models.WebsiteUserModel _dsWebsiteUsers;
	public HtmlSubmitButton _grabarBUT1;
	public HtmlSubmitButton _cancelarBUT1;

	// DataSource Column Constants
	public static final String DSWEBSITEUSERS_WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String DSWEBSITEUSERS_WEBSITE_USER_LOGIN_NAME = "website_user.login_name";
	public static final String DSWEBSITEUSERS_WEBSITE_USER_LOGIN_PASSWORD = "website_user.login_password";
	public static final String DSWEBSITEUSERS_WEBSITE_USER_NIVEL_VISIBILIDAD = "website_user.nivel_visibilidad";
	public static final String DSWEBSITEUSERS_WEBSITE_USER_NOMBRE_COMPLETO = "website_user.nombre_completo";
	public static final String DSWEBSITEUSERS_WEBSITE_USER_EMAIL = "website_user.email";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		_grabarBUT1 = new HtmlSubmitButton("grabarBUT1", "Grabar", this);
		_grabarBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarBUT1);
		_grabarBUT1.addSubmitListener(this);

		_cancelarBUT1 = new HtmlSubmitButton("cancelarBUT1", "Cancelar", this);
		_cancelarBUT1.setAccessKey("C");
		_detailformdisplaybox1.addButton(_cancelarBUT1);
		_cancelarBUT1.addSubmitListener(this);
		
		_detailformdisplaybox1.setReloadRowAfterSave(true);
		
		
		super.initialize();
	}

	/**
	 * Process the given submit event
	 * 
	 * @param event
	 *            the submit event to be processed
	 * @return true to continue processing events, false to stop processing
	 *         events
	 */
	public boolean submitPerformed(SubmitEvent event) throws Exception {
		if (event.getComponent() == _grabarBUT1) {
			if (_dsWebsiteUsers.getRow() != -1) {
				String passAnterior = null;
				String passNuevo = null;
				String passReNuevo = null;
				String mensaje = null;
				if ((passAnterior = _passwordAnteriorIMP1.getValue()) != null) {
					if (passAnterior.equals(_dsWebsiteUsers
							.getWebsiteUserLoginPassword())) {
						if ((passNuevo = _passwordNuevaIMP1.getValue()) != null) {
							if ((passReNuevo = _passwordReNuevaIMP1.getValue()) != null) {
								if (passNuevo.length() >= 8) {
									if (passNuevo.equals(passReNuevo)) {
										_dsWebsiteUsers
												.setWebsiteUserLoginPassword(passNuevo);
									} else
										mensaje = "La contraseña nueva no coincide";
								} else
									mensaje = "La contraseña debe tener por lo menos 8 caracteres";
							} else
								mensaje = "Reingrese la contraseña nueva";

						} else
							mensaje = "Para modificar la contraseña ingrese una nueva";
					} else
						mensaje = "La contraseña es incorrecta";
				} else if (passNuevo != null || passReNuevo != null) {
					mensaje = "Ingrese su contraseña actual para guardar una nueva";
				}
				if (mensaje != null) {
					displayErrorMessage(mensaje);
					return false;
				}
				_passwordAnteriorIMP1.setValue(null);
				_passwordNuevaIMP1.setValue(null);
				_passwordReNuevaIMP1.setValue(null);
				_dsWebsiteUsers.update();
			}
		}
		if (event.getComponent() == _cancelarBUT1) {
			try {			
				_dsWebsiteUsers.retrieve("website_user.user_id = "
						+ getSessionManager().getWebSiteUser().getUserID());
				_dsWebsiteUsers.gotoFirst();
			} catch (DataStoreException e) {
				displayErrorMessage("No se recupero la información de su cuenta.");
			} catch (SQLException e) {
				displayErrorMessage("No se recupero la información de su cuenta.");
			}
		}
		
		return super.submitPerformed(event);
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) throws Exception {
		if (!isReferredByCurrentPage()) {
			try {
				_dsWebsiteUsers.retrieve("website_user.user_id = "
						+ getSessionManager().getWebSiteUser().getUserID());
				_dsWebsiteUsers.gotoFirst();
			} catch (DataStoreException e) {
				displayErrorMessage("No se recupero la información de su cuenta.");
			} catch (SQLException e) {
				displayErrorMessage("No se recupero la información de su cuenta.");
			}
		}
		super.pageRequested(event);
	}
}
