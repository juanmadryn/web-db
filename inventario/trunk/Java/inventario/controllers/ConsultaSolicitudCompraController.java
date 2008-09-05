//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.controllers.Constants;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.utils.Utilities;
import inventario.models.ResumenSaldoArticulosModel;
import inventario.models.SolicitudCompraModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.JobExecutionException;

import proyectos.models.ProyectoModel;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
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
	public com.salmonllc.html.HtmlText _descripcion2;
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
	// public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
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
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;

	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;

	// DataSources
	public com.salmonllc.sql.DataStore _dsPeriodo;
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
	public static final String DSQBE_ESTADO = "estado";
	public static final String DSQBE_SOLICITANTE = "solicitante";

	public static final String DSPERIODO_DESDE = "desde";
	public static final String DSPERIODO_HASTA = "hasta";

	public static final int MODO_TITULO_ESPECIAL_PENDIENTES = 0;
	public static final int MODO_TITULO_ESPECIAL_OBSERVADAS = 1;
	public static final int MODO_TITULO_ESPECIAL_COTIZADAS = 2;

	public com.salmonllc.html.HtmlSubmitButton _recuperaSolicitudesPendientes;
	public com.salmonllc.html.HtmlSubmitButton _recuperaSolicitudesRechazadas;
	public com.salmonllc.html.HtmlSubmitButton _recuperaSolicitudesObservadas;
	public com.salmonllc.html.HtmlSubmitButton _recuperaSolicitudesCotizadas;
	public com.salmonllc.html.HtmlSubmitButton _limpiarBUT;

	private Timestamp desde;
	private Timestamp hasta;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		_recuperaSolicitudesPendientes = new HtmlSubmitButton(
				"recuperaSolicitudesPendientes", "SM's pendientes", this);
		_recuperaSolicitudesPendientes.setAccessKey("R");
		_listformdisplaybox1.addButton(_recuperaSolicitudesPendientes);
		_recuperaSolicitudesPendientes.addSubmitListener(this);
		_recuperaSolicitudesPendientes.setVisible(false);

		_recuperaSolicitudesRechazadas = new HtmlSubmitButton(
				"recuperaSolicitudesRechazadas",
				"recuperar Solicitud rechazada", this);
		_recuperaSolicitudesRechazadas.setAccessKey("S");
		_detailformdisplaybox1.addButton(_recuperaSolicitudesRechazadas);
		_recuperaSolicitudesRechazadas.addSubmitListener(this);
		_recuperaSolicitudesRechazadas.setVisible(false);

		_recuperaSolicitudesObservadas = new HtmlSubmitButton(
				"recuperaSolicitudesObservadas", "SM's Observadas", this);
		_recuperaSolicitudesObservadas.setAccessKey("O");
		_listformdisplaybox1.addButton(_recuperaSolicitudesObservadas);
		_recuperaSolicitudesObservadas.addSubmitListener(this);
		_recuperaSolicitudesObservadas.setVisible(false);

		_recuperaSolicitudesCotizadas = new HtmlSubmitButton(
				"recuperaSolicitudesCotizadas", "SM's para autorizar", this);
		_recuperaSolicitudesCotizadas.setAccessKey("O");
		_listformdisplaybox1.addButton(_recuperaSolicitudesCotizadas);
		_recuperaSolicitudesCotizadas.addSubmitListener(this);
		_recuperaSolicitudesCotizadas.setVisible(false);

		_limpiarBUT = new HtmlSubmitButton("limpiarBUT", "Limpiar", this);
		_limpiarBUT.setAccessKey("L");
		_searchformdisplaybox1.addButton(_limpiarBUT);
		_limpiarBUT.addSubmitListener(this);

		_searchformdisplaybox1.getSearchButton().addSubmitListener(this);

		_dsPeriodo.reset();
		_dsPeriodo.insertRow();
		seteaPeriodo(null, null); // valores por defecto para el periodo de
		// fechas
		_dsPeriodo.gotoFirst();

		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub

		_listformdisplaybox1.setHeadingCaption("Solicitudes de materiales");
		_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingFont");

		if (e.getComponent() == _searchformdisplaybox1.getSearchButton()) {
			String whereFecha = null;
			if (_fechadesde2.getValue() != null
					&& _fechahasta2.getValue() != null) {
				desde = _dsPeriodo.getDateTime("desde");
				hasta = _dsPeriodo.getDateTime("hasta");
				seteaPeriodo(desde, hasta);

				if (desde != null && hasta != null) {
					// chequeo las fechas
					if (hasta.compareTo(desde) < 0) {
						displayErrorMessage("La fecha desde debe ser anterior a la fecha hasta");
						return false;
					}
					whereFecha = "solicitudes_compra.fecha_solicitud BETWEEN '"
							+ desde.toString() + "' AND '" + hasta.toString()
							+ "'";
				}
			}
			_dsSolicitudes.reset();
			String where = _dsQBE.generateSQLFilter(_dsSolicitudes);
			if (whereFecha != null) {
				if (where != null)
					where += " AND ";
				else
					where = "";
				where += whereFecha;
			} else if (where != null)
				where += "";

			System.out.println(_proyecto2.getValue());
			if (_proyecto2.getValue() != null
					&& _proyecto2.getValue().trim().length() > 0) {
				ProyectoModel dsProyecto = new ProyectoModel("proyectos",
						"proyectos");
				dsProyecto.retrieve("proyecto = '" + _proyecto2.getValue()
						+ "'");
				if (dsProyecto.gotoFirst()) {
					if (where != null)
						where += " AND ";
					else
						where = "";
					where += "solicitudes_compra.proyecto_id ="
							+ dsProyecto.getProyectosProyectoId();
				} else
					throw new DataStoreException(
							"El proyecto indicado no existe");
			}

			_dsSolicitudes.retrieve(where);
			_dsSolicitudes.gotoFirst();			
		}

		int user_id = getSessionManager().getWebSiteUser().getUserID();

		if (e.getComponent() == _recuperaSolicitudesPendientes) {
			try {
				_dsSolicitudes
						.setOrderBy("solicitudes_compra.fecha_aprobacion DESC");
				_dsSolicitudes
						.retrieve("solicitudes_compra.solicitud_compra_id IN "
								+ "(SELECT objeto_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante = "
								+ user_id
								+ " AND nombre_objeto='solicitudes_compra') AND solicitudes_compra.estado LIKE '0006.0002'");
				_dsSolicitudes.waitForRetrieve();
				_dsSolicitudes.gotoFirst();
				setSpecialTitle(MODO_TITULO_ESPECIAL_PENDIENTES);
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		if (e.getComponent() == _recuperaSolicitudesObservadas) {
			try {
				_dsSolicitudes
						.retrieve("solicitudes_compra.estado LIKE '0006.0005' AND user_id_solicita = "
								+ user_id);
				_dsSolicitudes.waitForRetrieve();
				_dsSolicitudes.gotoFirst();
				setSpecialTitle(MODO_TITULO_ESPECIAL_OBSERVADAS);
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		if (e.getComponent() == _recuperaSolicitudesCotizadas) {
			try {
				_dsSolicitudes
						.retrieve("solicitudes_compra.estado LIKE '0006.0008'");
				_dsSolicitudes.waitForRetrieve();
				_dsSolicitudes.gotoFirst();
				setSpecialTitle(MODO_TITULO_ESPECIAL_COTIZADAS);
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		if (e.getComponent() == _recuperaSolicitudesRechazadas) {
			_dsSolicitudes.setSolicitudesCompraEstado("0006.0001");
			_dsSolicitudes.update();
		}

		// limpia los criterios
		if (e.getComponent() == _limpiarBUT) {
			_n2.setValue(null);
			_estado2.setSelectedIndex(0);
			_fechadesde2.setValue(null);
			_fechahasta2.setValue(null);
			_solicitante2.setSelectedIndex(0);
			_proyecto2.setValue(null);
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
			int mode = getIntParameter("mode", -1);

			if (user_id != -1) {
				// en función del modo de invocación de la página muestra las
				// solicitudes pendientes, observadas o cotizadas.
				try {
					switch (mode) {
					case 0:
						_dsSolicitudes
								.retrieve("solicitudes_compra.solicitud_compra_id IN "
										+ "(SELECT objeto_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante = "
										+ user_id
										+ " AND nombre_objeto='solicitudes_compra') AND solicitudes_compra.estado LIKE '0006.0002'");
						_dsSolicitudes.gotoFirst();
						setSpecialTitle(MODO_TITULO_ESPECIAL_PENDIENTES);
						break;
					case 1:
						_dsSolicitudes
								.retrieve("solicitudes_compra.estado LIKE '0006.0005' AND user_id_solicita = "
										+ user_id);
						_dsSolicitudes.gotoFirst();
						setSpecialTitle(MODO_TITULO_ESPECIAL_OBSERVADAS);
						break;
					case 2:
						_dsSolicitudes
								.setOrderBy(SolicitudCompraModel.SOLICITUDES_COMPRA_FECHA_SOLICITUD
										+ " DESC");
						_dsSolicitudes
								.retrieve("solicitudes_compra.estado LIKE '0006.0008'");
						_dsSolicitudes.waitForRetrieve();
						_dsSolicitudes.gotoFirst();
						setSpecialTitle(MODO_TITULO_ESPECIAL_COTIZADAS);
						break;
					}

				} catch (SQLException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				} catch (DataStoreException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				}

			}
			_n2.setFocus();
		}

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		int solicitudes_pendientes = Utilities
				.getSolicitudesCompraPendientesAprobacion(currentUser);
		if (solicitudes_pendientes > 0) {
			_recuperaSolicitudesPendientes.setVisible(true);
		} else {
			_recuperaSolicitudesPendientes.setVisible(false);
		}

		int solicitudes_observadas = Utilities
				.getSolicitudesCompraPendientesObservacion(currentUser);
		if (solicitudes_observadas > 0) {
			_recuperaSolicitudesObservadas.setVisible(true);
		} else {
			_recuperaSolicitudesObservadas.setVisible(false);
		}

		// Si el usuario no es comprador, solo puede consultar las solicitudes
		// realizadas por él
		if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
			_dsQBE.setString("solicitante", String.valueOf(currentUser));
			_solicitante2.setEnabled(false);
		} else {
			_solicitante2.setEnabled(true);
			if (_dsSolicitudes.getRow() != -1
					&& "0006.0004".equalsIgnoreCase(_dsSolicitudes
							.getSolicitudesCompraEstado()))
				_recuperaSolicitudesRechazadas.setVisible(true);
			else
				_recuperaSolicitudesRechazadas.setVisible(false);

			int solicitudes_cotizadas = Utilities
					.getSolicitudesCompraCotizadas(currentUser);
			if (solicitudes_cotizadas > 0) {
				_recuperaSolicitudesCotizadas.setVisible(true);
			} else {
				_recuperaSolicitudesCotizadas.setVisible(false);
			}

		}
		super.pageRequested(event);
	}

	private void setSpecialTitle(int modo) {
		// TODO Auto-generated method stub
		switch (modo) {
		case 0:
			_listformdisplaybox1
					.setHeadingCaption("Solicitudes de materiales pendientes de aprobación");
			_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingSpecialFont");
			break;
		case 1:
			_listformdisplaybox1
					.setHeadingCaption("Solicitudes de materiales pendientes de observación");
			_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingSpecialFont");
			break;
		case 2:
			_listformdisplaybox1
					.setHeadingCaption("Solicitudes de materiales cotizadas para autorizar");
			_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingSpecialFont");
			break;
		}
	}

	/**
	 * Setea el periodo por defecto para el rango de fechas
	 * 
	 * @param desdeTime
	 *            extremo inferior del rango de fechas a consultar
	 * @param hastaTime
	 *            extremo superior del rango de fechas a consultar
	 * 
	 * @throws DataStoreException
	 */
	public void seteaPeriodo(Timestamp desdeTime, Timestamp hastaTime)
			throws DataStoreException {

		GregorianCalendar cal = new GregorianCalendar();
		if (desdeTime != null)
			cal.setTime(desdeTime);
		Timestamp day = new Timestamp(cal.getTimeInMillis());
		// _dsPeriodo.setDateTime("desde", day);
		desde = day;
		if (hastaTime != null)
			cal.setTime(hastaTime);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 9);
		day = new Timestamp(cal.getTimeInMillis());
		// _dsPeriodo.setDateTime("hasta", day);
		hasta = day;
	}
}
