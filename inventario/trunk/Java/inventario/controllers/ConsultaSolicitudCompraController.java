//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.UsuarioRolesModel;

import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.OptionsSort;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaSolicitudCompraController: a SOFIA generated controller
 */
public class ConsultaSolicitudCompraController extends BaseController implements
		SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992433529455710891L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _estado2;
	public com.salmonllc.html.HtmlDropDownList _solicitante2;
	public com.salmonllc.html.HtmlText _centro_costo1;
	public com.salmonllc.html.HtmlText _centro_costo2;
	public com.salmonllc.html.HtmlText _clienteCAP5;
	public com.salmonllc.html.HtmlText _comprador_nombreTXT3;
	public com.salmonllc.html.HtmlText _descripcion1;
	public com.salmonllc.html.HtmlText _descripcionCAP4;
	public com.salmonllc.html.HtmlText _descripcionTXT2;
	public com.salmonllc.html.HtmlText _descripción2;
	public com.salmonllc.html.HtmlText _editar;
	public com.salmonllc.html.HtmlText _estado1;
	public com.salmonllc.html.HtmlText _estadoCAP5;
	public com.salmonllc.html.HtmlText _estadoTXT3;
	public com.salmonllc.html.HtmlText _fecha_aprobacion1;
	public com.salmonllc.html.HtmlText _fecha_aprobacion2;
	public com.salmonllc.html.HtmlText _fecha_oc1;
	public com.salmonllc.html.HtmlText _fecha_oc2;
	public com.salmonllc.html.HtmlText _fecha_solicitud1;
	public com.salmonllc.html.HtmlText _fecha_solicitud2;
	public com.salmonllc.html.HtmlText _fecha_solicitudCAP5;
	public com.salmonllc.html.HtmlText _fecha_solicitudTXT4;
	public com.salmonllc.html.HtmlText _fechadesde1;
	public com.salmonllc.html.HtmlText _fechahasta1;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador2;
	public com.salmonllc.html.HtmlText _nombre_completo_solicitante1;
	public com.salmonllc.html.HtmlText _nombre_completo_solicitante2;
	public com.salmonllc.html.HtmlText _nombreCAP3;
	public com.salmonllc.html.HtmlText _numero1;
	public com.salmonllc.html.HtmlText _numero2;
	public com.salmonllc.html.HtmlText _numeroCAP2;
	public com.salmonllc.html.HtmlText _observacion1;
	public com.salmonllc.html.HtmlText _observacion2;
	public com.salmonllc.html.HtmlText _proyectos_nombre2;
	public com.salmonllc.html.HtmlText _proyectos_proyecto1;
	public com.salmonllc.html.HtmlText _proyectos_proyecto2;
	public com.salmonllc.html.HtmlText _proyectoTXT1;
	public com.salmonllc.html.HtmlText _separador;
	public com.salmonllc.html.HtmlText _solicitante1;
	public com.salmonllc.html.HtmlText _solicitante_nombreTXT3;
	public com.salmonllc.html.HtmlTextEdit _fechadesde2;
	public com.salmonllc.html.HtmlTextEdit _fechahasta2;
	public com.salmonllc.html.HtmlTextEdit _n2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspLink _footerproyectosHelp;
	public com.salmonllc.jsp.JspLink _lnksolicitud1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspRaw _rawAddRow;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader6;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow6;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _table2TDRow0;
	public com.salmonllc.jsp.JspTableCell _table2TDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table2TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.SolicitudCompraModel _dsSolicitudes;

	// DataSource Column Constants
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID = "solicitudes_compra.solicitud_compra_id";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_USER_ID_COMPRADOR = "solicitudes_compra.user_id_comprador";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_USER_ID_SOLICITA = "solicitudes_compra.user_id_solicita";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_FECHA_SOLICITUD = "solicitudes_compra.fecha_solicitud";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_FECHA_OC = "solicitudes_compra.fecha_oc";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_DESCRIPCION = "solicitudes_compra.descripcion";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_OBSERVACIONES = "solicitudes_compra.observaciones";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_CENTRO_COSTO_ID = "solicitudes_compra.centro_costo_id";
	public static final String DSSOLICITUDES_SOLICITUDES_COMPRA_PROYECTO_ID = "solicitudes_compra.proyecto_id";
	public static final String DSSOLICITUDES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSSOLICITUDES_NOMBRE_COMPLETO_SOLICITANTE = "nombre_completo_solicitante";
	public static final String DSSOLICITUDES_NOMBRE_COMPLETO_COMPRADOR = "nombre_completo_comprador";
	public static final String DSSOLICITUDES_PROYETOS_NOMBRE = "proyetos.nombre";
	public static final String DSSOLICITUDES_PROYETOS_PROYECTO = "proyetos.proyecto";
	public static final String DSSOLICITUDES_CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String DSSOLICITUDES_WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String DSSOLICITUDES_ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String DSSOLICITUDES_TOTAL_SOLICITUD_COMPRA = "total_solicitud_compra";
	public static final String DSSOLICITUDES_OBSERVACIONES = "observaciones";

	public static final String DSQBE_N = "n";
	public static final String DSQBE_DESDE = "desde";
	public static final String DSQBE_HASTA = "hasta";
	public static final String DSQBE_ESTADO = "estado";
	public static final String DSQBE_SOLICITANTE = "solicitante";

	public com.salmonllc.html.HtmlSubmitButton _recuperaSolicitudesPendientes;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		_recuperaSolicitudesPendientes = new HtmlSubmitButton(
				"recuperaSolicitudesPendientes",
				"Recuperar Solicitudes pendientes de aprobación", this);
		_recuperaSolicitudesPendientes.setAccessKey("R");
		_searchformdisplaybox1.addButton(_recuperaSolicitudesPendientes);
		_recuperaSolicitudesPendientes.addSubmitListener(this);
		_recuperaSolicitudesPendientes.setVisible(false);
		_searchformdisplaybox1.getSearchButton().addSubmitListener(this);
		
		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub		
		
		_listformdisplaybox1.setHeadingCaption("Solicitudes de compra");
		_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingFont");
		
		if (e.getComponent() == _recuperaSolicitudesPendientes) {
			try {
				_dsSolicitudes
						.retrieve("solicitudes_compra.solicitud_compra_id IN "
								+ "(SELECT solicitud_compra_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante = "
								+ getSessionManager().getWebSiteUser()
										.getUserID() + ")");
				_dsSolicitudes.gotoFirst();
				setSpecialTitle();
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		return super.submitPerformed(e);
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 * @throws Exception
	 */
	public void pageRequested(PageEvent event) throws Exception {
		
		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			int user_id = getIntParameter("user_id", -1);

			if (user_id != -1) {
				// verifica si cambión el contexto
				try {
					_dsSolicitudes
							.retrieve("solicitudes_compra.solicitud_compra_id IN "
									+ "(SELECT solicitud_compra_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante = "
									+ user_id + ")");
					_dsSolicitudes.gotoFirst();
					setSpecialTitle();
				} catch (SQLException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				} catch (DataStoreException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				}

			}
		}		
		
		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		int solicitudes_pendientes = _dsSolicitudes.estimateRowsRetrieved("solicitudes_compra.solicitud_compra_id IN (SELECT solicitud_compra_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante = "
				+ currentUser + ")");		
		if (solicitudes_pendientes > 0) {
			_recuperaSolicitudesPendientes.setVisible(true);				
		} else {			
			_recuperaSolicitudesPendientes.setVisible(false);
		}
		
		// Si el usuario no es comprador, solo puede consultar las solicitudes realizadas por él
		if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
			_solicitante2.setEnabled(false);
			_dsQBE.setString("solicitante", String.valueOf(currentUser));
		}
		else
			_solicitante2.setEnabled(true);
			
		
		super.pageRequested(event);
	}
	
	private void setSpecialTitle() {
		// TODO Auto-generated method stub
		_listformdisplaybox1.setHeadingCaption("Solicitudes de compra pendientes de aprobación");
		_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingSpecialFont");		
	}
}
