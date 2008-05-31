//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.CotizacionesCompraModel;
import inventario.models.DetalleCotizacionModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.OrdenesCompraModel;
import inventario.util.SolicitudCompraTransiciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AbmcSolicitudCompraController: a SOFIA generated controller
 */
public class AbmcSolicitudCompraController extends BaseEntityController {

	/**
	 * Modificado por Demian Barry el 21/05/2008
	 * Agrega funcionalidadpara el tratamiento de las Cotizaciones de Compra cc 
	 */
	
	private static final long serialVersionUID = 6985914806600939656L;
	// Visual Components
	public com.salmonllc.html.HtmlText _observacionX1;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observacionX2;
	public com.salmonllc.html.HtmlCheckBox _seleccion_detalle2;
	public com.salmonllc.html.HtmlLookUpComponent _tarea3;
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observaciones2;
	public com.salmonllc.html.HtmlText _articuloId1;
	public com.salmonllc.html.HtmlText _cantidad_pedida2;
	public com.salmonllc.html.HtmlText _cantidad_recibida2;
	public com.salmonllc.html.HtmlText _cantidad_solicitada1;
	public com.salmonllc.html.HtmlText _cantidadPedida1;
	public com.salmonllc.html.HtmlText _cantidadRecibida1;
	public com.salmonllc.html.HtmlText _descripcion1;
	public com.salmonllc.html.HtmlText _descripcion3;
	public com.salmonllc.html.HtmlText _fecha_aprobacion1;
	public com.salmonllc.html.HtmlText _fecha_aprobacion2;
	public com.salmonllc.html.HtmlText _fecha_oc1;
	public com.salmonllc.html.HtmlText _fecha_oc2;
	public com.salmonllc.html.HtmlText _fecha_solicitud1;
	public com.salmonllc.html.HtmlText _fecha_solicitud2;
	public com.salmonllc.html.HtmlText _monto_total1;
	public com.salmonllc.html.HtmlText _monto_total2;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _n2;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador2;
	public com.salmonllc.html.HtmlText _nombre_completo_solicitante1;
	public com.salmonllc.html.HtmlDropDownList _nombre_completo_solicitante2;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _observaciones3;
	public com.salmonllc.html.HtmlText _proyecto1;
	public com.salmonllc.html.HtmlText _seleccion_detalle1;
	public com.salmonllc.html.HtmlText _tarea1;
	public com.salmonllc.html.HtmlText _centro_costo1;
	public com.salmonllc.html.HtmlLookUpComponent _centro_costo2;
	public com.salmonllc.html.HtmlLookUpComponent _articulo2;
	public com.salmonllc.html.HtmlTextEdit _cantidad_solicitada2;
	public com.salmonllc.html.HtmlTextEdit _descripcion2;
	public com.salmonllc.html.HtmlTextEdit _descripcion4;
	public com.salmonllc.html.HtmlTextEdit _monto_unitario1;
	public com.salmonllc.html.HtmlTextEdit _observaciones4;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspTable _table1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader10;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader11;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader6;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader7;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader8;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader9;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow10;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow11;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow6;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow7;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow9;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _proyectoTableTD;
	public com.salmonllc.jsp.JspTableCell _table1TDRow0;
	public com.salmonllc.jsp.JspTableCell _table1TDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableCell _tarea2;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader1;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow1;
	public com.salmonllc.jsp.JspTableRow _table1TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;
	public com.salmonllc.jsp.JspLink _imprimirSolicitudCompraBUT1;
	public com.salmonllc.jsp.JspLink _verFirmantes;
	public com.salmonllc.jsp.JspLink _imprimirSolicitudCompraBUT2;
	public com.salmonllc.html.HtmlDropDownList _unidad_medida2;
	public com.salmonllc.jsp.JspTableCell _cantidadPedidaRowTd;
	public com.salmonllc.jsp.JspTableCell _cantidadPedidaHeaderTd;
	public com.salmonllc.jsp.JspTableCell _ocRowTd;
	public com.salmonllc.jsp.JspTableCell _ocHeaderTd;
	public com.salmonllc.jsp.JspTableCell _ccRowTd;
	public com.salmonllc.jsp.JspTableCell _ccHeaderTd;

	// DataSources
	public inventario.models.DetalleSCModel _dsDetalleSC;
	public inventario.models.SolicitudCompraModel _dsSolicitudCompra;

	// DataSource Column Constants
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID = "solicitudes_compra.solicitud_compra_id";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_USER_ID_COMPRADOR = "solicitudes_compra.user_id_comprador";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_USER_ID_SOLICITA = "solicitudes_compra.user_id_solicita";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_FECHA_SOLICITUD = "solicitudes_compra.fecha_solicitud";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_FECHA_OC = "solicitudes_compra.fecha_oc";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_DESCRIPCION = "solicitudes_compra.descripcion";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_OBSERVACIONES = "solicitudes_compra.observaciones";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_CENTRO_COSTO_ID = "solicitudes_compra.centro_costo_id";
	public static final String DSSOLICITUDCOMPRA_SOLICITUDES_COMPRA_PROYECTO_ID = "solicitudes_compra.proyecto_id";
	public static final String DSSOLICITUDCOMPRA_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSSOLICITUDCOMPRA_NOMBRE_COMPLETO_SOLICITANTE = "nombre_completo_solicitante";
	public static final String DSSOLICITUDCOMPRA_NOMBRE_COMPLETO_COMPRADOR = "nombre_completo_comprador";
	public static final String DSSOLICITUDCOMPRA_PROYETOS_NOMBRE = "proyetos.nombre";
	public static final String DSSOLICITUDCOMPRA_PROYETOS_PROYECTO = "proyetos.proyecto";
	public static final String DSSOLICITUDCOMPRA_CENTRO_COSTO_NOMBRE = "centro_costo.nombre";

	public static final String DSDETALLESC_DETALLE_SC_DETALLE_SC_ID = "detalle_sc.detalle_SC_id";
	public static final String DSDETALLESC_DETALLE_SC_RECEPCION_COMPRA_ID = "detalle_sc.recepcion_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_COTIZACION_COMPRA_ID = "detalle_sc.cotizacion_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DSDETALLESC_DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DSDETALLESC_DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_OBSERVACIONES = "detalle_sc.observaciones";
	public static final String DSDETALLESC_ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String DSDETALLESC_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSDETALLESC_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLESC_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSDETALLESC_MONTO_TOTAL = "monto_total";

	// customs components
	public HtmlSubmitButton _nuevaSolicitudCompraBUT1;
	public HtmlSubmitButton _grabarSolicitudCompraBUT1;
	public HtmlSubmitButton _articulosAgregarBUT1;
	public HtmlSubmitButton _articulosEliminarBUT1;
	public HtmlSubmitButton _articulosCancelarBUT1;
	public HtmlSubmitButton _generarOCBUT1;
	public HtmlSubmitButton _generarCCBUT1;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT1;
	public com.salmonllc.jsp.JspLink _lnkOc1;
	public com.salmonllc.jsp.JspLink _lnkCc1;

	public com.salmonllc.html.HtmlSubmitButton _customBUT100;

	public com.salmonllc.html.HtmlSubmitButton _customBUT110;

	public com.salmonllc.html.HtmlSubmitButton _customBUT120;

	public com.salmonllc.html.HtmlSubmitButton _customBUT130;

	public com.salmonllc.html.HtmlSubmitButton _customBUT140;

	public com.salmonllc.html.HtmlSubmitButton _customBUT150;

	private String SELECCION_DETALLE_FLAG = "SELECCION_DETALLE_FLAG";

	private static final String CIRCUITO = "0006";

	private boolean recargar = false;
	
	// Indica si se debe seleccionar o deseleccionar elementos de la lista de detalles
	private Boolean seleccionarTodo = true;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();

		_centro_costo1.setVisible(false);
		_centro_costo2.setVisible(false);

		// completo y asigno botones custom

		_grabarSolicitudCompraBUT1 = new HtmlSubmitButton(
				"grabarSolicitudCompraBUT1", "Grabar", this);
		_grabarSolicitudCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarSolicitudCompraBUT1);

		_nuevaSolicitudCompraBUT1 = new HtmlSubmitButton(
				"nuevaSolicitudCompraBUT1", "Nueva solicitud", this);
		_nuevaSolicitudCompraBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevaSolicitudCompraBUT1);

		_articulosAgregarBUT1 = new HtmlSubmitButton("articulosAgregarBUT1","Agregar", this);
		_articulosAgregarBUT1.setAccessKey("A");
		_listformdisplaybox2.addButton(_articulosAgregarBUT1);

		_articulosEliminarBUT1 = new HtmlSubmitButton("articulosEliminarBUT1","Eliminar", this);
		_articulosEliminarBUT1.setAccessKey("E");
		_listformdisplaybox2.addButton(_articulosEliminarBUT1);

		_articulosCancelarBUT1 = new HtmlSubmitButton("articulosCancelarBUT1","Cancelar", this);
		_articulosCancelarBUT1.setAccessKey("C");
		_listformdisplaybox2.addButton(_articulosCancelarBUT1);

		_desSeleccionaTodoBUT1 = new HtmlSubmitButton("desSeleccionaTodoBUT2","Seleccionar todo", this);
		_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
		_listformdisplaybox2.addButton(_desSeleccionaTodoBUT1);

		_generarCCBUT1 = new HtmlSubmitButton("generarCCBUT1", "generar Cotización",this);
		_generarCCBUT1.setAccessKey("C");
		_listformdisplaybox2.addButton(_generarCCBUT1);

		_generarOCBUT1 = new HtmlSubmitButton("generarOCBUT1", "Generar OC",this);
		_generarOCBUT1.setAccessKey("O");
		_listformdisplaybox2.addButton(_generarOCBUT1);

		// agrega los listener a los botones
		_nuevaSolicitudCompraBUT1.addSubmitListener(this);
		_grabarSolicitudCompraBUT1.addSubmitListener(this);
		_articulosAgregarBUT1.addSubmitListener(this);
		_articulosEliminarBUT1.addSubmitListener(this);
		_articulosCancelarBUT1.addSubmitListener(this);
		_generarCCBUT1.addSubmitListener(this);
		_generarOCBUT1.addSubmitListener(this);
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);
		_desSeleccionaTodoBUT1.addSubmitListener(this);

		// agrego columna de seleccion
		_dsDetalleSC.addBucket(SELECCION_DETALLE_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsDetalleSC, SELECCION_DETALLE_FLAG);
		_seleccion_detalle2.setFalseValue(null);
		
		/**
		 * Modificado por Demian Barry el 21/05/2008, agregando funcionalidad para cotizaciones  
		 */
		// muestro link a CC y OC solo si el rol es COMPRADOR
		int userId = getSessionManager().getWebSiteUser().getUserID();
		if (!UsuarioRolesModel.isRolUsuario(userId, "COMPRADOR")) {
			_ccHeaderTd.setVisible(false);
			_ccRowTd.setVisible(false);
			_ocHeaderTd.setVisible(false);
			_ocRowTd.setVisible(false);			
			_cantidadPedidaHeaderTd.setColSpan(3);
			_cantidadPedidaRowTd.setColSpan(3);
			_cantidadPedidaRowTd.setAlign("LEFT");
		} else {
			_ccHeaderTd.setVisible(true);
			_ccRowTd.setVisible(true);
			_ocHeaderTd.setVisible(true);
			_ocRowTd.setVisible(true);
			_cantidadPedidaHeaderTd.setColSpan(1);
			_cantidadPedidaRowTd.setColSpan(1);
		}
	
		// seteo la validación para los datasource
		_dsSolicitudCompra.setAutoValidate(true);
		_dsDetalleSC.setAutoValidate(true);

		// seteo inicial para la visualización de componentes
		// genero un nuevo proyecto vacio.
		_dsSolicitudCompra.reset();
		_dsDetalleSC.reset();

		_dsSolicitudCompra.insertRow();

		_proyecto2.setFocus();

		_detailformdisplaybox1.setReloadRowAfterSave(true);

		setTabla_principal("solicitudes_compra");

		setDatosBasicosSolicitud();
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
		String remoteAddr = this.getCurrentRequest().getRemoteHost();
		String nombre_tabla = getTabla_principal();
		int userId = getSessionManager().getWebSiteUser().getUserID();
		DBConnection conn = DBConnection.getConnection(getApplicationName());
		boolean batchInserts = false;

		conn.beginTransaction();
		HtmlComponent component = event.getComponent();

		setDatosBasicosSolicitud();

		try {
			if (component == _customBUT100) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT100.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT110) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT110.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT120) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT120.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT130) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT130.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT140) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT140.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT150) {
				if (CIRCUITO != null) {
					_dsSolicitudCompra.ejecutaAccion(Integer
							.parseInt(_customBUT150.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			conn.commit();
		} catch (DataStoreException ex) {
			conn.rollback();
			displayErrorMessage(ex.getMessage());
			return false;
		} catch (ValidationException ex) {
			conn.rollback();
			for (String er : ex.getStackErrores()) {
				displayErrorMessage(er);
			}
			return false;
		} finally {
			conn.freeConnection();
		}

		if (component == _nuevaSolicitudCompraBUT1) {
			// genero una nueva solicitud vacia.
			_dsSolicitudCompra.reset();
			_dsDetalleSC.reset();
			_dsSolicitudCompra.gotoRow(_dsSolicitudCompra.insertRow());
			_proyecto2.setFocus();
		}

		if (component == _grabarSolicitudCompraBUT1) {
			conn = DBConnection.getConnection("inventario");
			conn.beginTransaction();

			// si la solicitud esta en estado generado o esta siendo generada
			if (isModificable(_dsSolicitudCompra.getSolicitudesCompraEstado())) {
				try {
					// grabo todos los datasource
					if (_dsSolicitudCompra.getRow() == -1)
						return false;

					_dsSolicitudCompra.update(conn);

					// actualizo los detalles
					if (_dsDetalleSC.getRow() != -1) {
						_dsDetalleSC.update(conn);
					}
					System.out.println("Salio");
					_dsSolicitudCompra.resetStatus();
					_dsDetalleSC.resetStatus();
					System.out.println("AntesCommit");
					conn.commit();
					System.out.println("DespuesCommit");
					setTareaLookupURL();

				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					String mensaje = "";
					System.out.println("AntesIF");
					if (ex.getRow() != -1) {
						mensaje = " -> Detalle Nº: " + (ex.getRow() + 1);
						_articulo2.setFocus(ex.getRow());
					}
					System.out.println("AntesMensaje");
					displayErrorMessage(ex.getMessage() + mensaje);
					System.out.println("DespuesMensaje");
					return false;
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsSolicitudCompra);
					displayErrorMessage(ex.getMessage());
					return false;
				} finally {
					if (conn != null) {
						conn.rollback();
						conn.freeConnection();
					}
				}

			} else {
				// si la solicitud no está generada, bloqueo toda modificación
				displayErrorMessage("No puede modificar la solicitud en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}

		}

		if (component == _articulosAgregarBUT1) {
			// crea un nuevo registro de tarea
			try {
				setRow_id(_dsSolicitudCompra
						.getSolicitudesCompraSolicitudCompraId());
				if (isModificable(_dsSolicitudCompra
						.getSolicitudesCompraEstado())) {
					if (getRow_id() == 0)
						_dsSolicitudCompra.update();
					setRow_id(_dsSolicitudCompra
							.getSolicitudesCompraSolicitudCompraId());
					if (_dsDetalleSC.getRow() != -1)
						_dsDetalleSC.update();
					// recupero el row actual
					int rowActual = _dsDetalleSC.getRow();

					int row = _dsDetalleSC.insertRow(0);

					_dsDetalleSC
							.setDetalleScSolicitudCompraId(row, getRow_id());

					// si existe row anterior, sólo copia valores básicos
					int tarea = _dsDetalleSC.getDetalleScTareaId(rowActual + 1);
					if (rowActual != -1 && tarea != 0) {
						// valido el parte que estoy copiando
						_dsDetalleSC.setDetalleScTareaId(row, tarea);
					}

					// hace foco en el registro
					int nroPagerow = _datatable2.getPage(row);
					int nroPageActual = _datatable2.getPage(_dsDetalleSC
							.getRow());
					if (nroPagerow != nroPageActual)
						_datatable2.setPage(_datatable2.getPage(row));
					_articulo2.setFocus(row, true);
					setTareaLookupURL();

				} else {
					// si la solicitud no está generada, bloqueo toda
					// modificación
					displayErrorMessage("No puede agregar artículos a la solicitud en el estado actual.");
					setRecargar(true);
					pageRequested(new PageEvent(this));
					return false;
				}
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsSolicitudCompra);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		if (component == _articulosEliminarBUT1) {
			if (isModificable(_dsSolicitudCompra.getSolicitudesCompraEstado())) {
				// elimina todas las actividades seleccionadas
				for (int row = _dsDetalleSC.getRowCount() - 1; row >= 0; row--) {
					if (_dsDetalleSC.getInt(row, SELECCION_DETALLE_FLAG) == 1) {
						// Rol marcado para selección
						try {
							_dsDetalleSC.deleteRow(row);
							// _dsDetalleSC.update();
						} catch (Exception e) {
							displayErrorMessage("No se ha podido eliminar la actividad seleccionada. Error: "
									+ e.getMessage());
							_dsDetalleSC.unDeleteRow(row);
							_dsDetalleSC.setInt(row, SELECCION_DETALLE_FLAG, 0);
							return false;
						}
					}
				}
			} else {
				// si la solicitud no está generada, bloqueo toda modificación
				displayErrorMessage("No puede modificar la solicitud en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}
		}

		if (component == _articulosCancelarBUT1) {
			// crea un nuevo registro de tarea
			try {
				_dsDetalleSC.reset();
				_dsDetalleSC.resetStatus();
				_dsDetalleSC.retrieve("detalle_sc.solicitud_compra_id ="
						+ getRow_id());
				_dsDetalleSC.gotoFirst();
				_dsDetalleSC.update();
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, _dsSolicitudCompra);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsSolicitudCompra);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		/**
		 * Agregado por Demian Barry el 21/05/2008, nueva funcionalidad de cotización de compra 
		 */
		// genera Cotizaciones para articulos seleccionados
		if (component == _generarCCBUT1) {
			try {
				// chequeo que el usuario tenga el rol COMPRADOR
				int currentUser = getSessionManager().getWebSiteUser().getUserID();
				if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
					displayErrorMessage("Debe ser COMPRADOR para generar una cotización de compra");
					return false;
				}	
				
				conn = DBConnection.getConnection(getApplicationName());
				conn.beginTransaction();

				_dsDetalleSC.filter(SELECCION_DETALLE_FLAG + " != null " + "&& detalle_sc.cotizacion_compra_id == null");
				_dsDetalleSC.gotoFirst();

				if (_dsDetalleSC.getRowCount() <= 0) {
					displayErrorMessage("Debe seleccionar al menos un artículo sin Cotización");
					_dsDetalleSC.filter(null);
					return false;
				}

				CotizacionesCompraModel dsCotizacionCompra = new CotizacionesCompraModel("inventario");
				DetalleCotizacionModel dsDetalleCotizacion = new DetalleCotizacionModel("inventario");

				int ccId = dsCotizacionCompra.insertRow();
				dsCotizacionCompra.setCurrentWebsiteUserId(getUserFromSession(getCurrentRequest().getRemoteAddr()).getUserID());
				dsCotizacionCompra.setCotizacionesCompraEstado(ccId,"0008.0001");

				dsCotizacionCompra.update(conn);

				for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
					int CotizacionCompraId = dsCotizacionCompra.getCotizacionesCompraCotizacionCompraId(ccId);
					int ScId = _dsDetalleSC.getDetalleScDetalleScId(row); 
					_dsDetalleSC.setDetalleScCotizacionCompraId(row,CotizacionCompraId);
					// para cada artículo seleccionado lo inserta en le detalle de la cotización
					int dccId = dsDetalleCotizacion.insertRow();
					dsDetalleCotizacion.setDetalleCotizacionDetalleScId(dccId,ScId);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionCompraId(dccId,CotizacionCompraId);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor1(dccId, 0);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor2(dccId, 0);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor3(dccId, 0);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor4(dccId, 0);
					dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor5(dccId, 0);
				}

				_dsDetalleSC.update(conn);
				dsDetalleCotizacion.update(conn);


				dsCotizacionCompra.resetStatus();
				dsDetalleCotizacion.resetStatus();
				_dsDetalleSC.resetStatus();
				conn.commit();

				_dsDetalleSC.filter(null);

				this.gotoSiteMapPage("EditarCotizacionCompra", "?cotizacion_compra_id="
						+ dsCotizacionCompra.getCotizacionesCompraCotizacionCompraId(ccId));

			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (SQLException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (ValidationException ex) {
				MessageLog.writeErrorMessage(ex, null);
				for (String er : ex.getStackErrores()) {
					displayErrorMessage(er);
				}
				return false;
			} finally {
				if (conn != null) {
					conn.rollback();
					conn.freeConnection();
				}
			}
		}

		// genera OCs para articulos seleccionados
		if (component == _generarOCBUT1) {
			try {
				// chequeo que el usuario tenga el rol COMPRADOR
				int currentUser = getSessionManager().getWebSiteUser().getUserID();
				if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
					displayErrorMessage("Debe ser COMPRADOR para revisar una solicitud aprobada.");
					return false;
				}	
				
				conn = DBConnection.getConnection(getApplicationName());
				conn.beginTransaction();

				_dsDetalleSC.filter(SELECCION_DETALLE_FLAG + " != null "
						+ "&& detalle_sc.orden_compra_id == null");
				_dsDetalleSC.gotoFirst();

				if (_dsDetalleSC.getRowCount() <= 0) {
					displayErrorMessage("Debe seleccionar al menos un artículo sin OC");
					_dsDetalleSC.filter(null);
					return false;
				}

				OrdenesCompraModel dsOrdenCompra = new OrdenesCompraModel(
						"inventario");

				int ocId = dsOrdenCompra.insertRow();
				dsOrdenCompra.setCurrentWebsiteUserId(
						getUserFromSession(getCurrentRequest().getRemoteAddr()).getUserID());

				/*dsOrdenCompra.setOrdenesCompraUserIdComprador(ocId,
						getUserFromSession(getCurrentRequest().getRemoteAddr())
								.getUserID());*/

				dsOrdenCompra.update(conn);

				for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
					_dsDetalleSC.setDetalleScOrdenCompraId(row, dsOrdenCompra
							.getOrdenesCompraOrdenCompraId(ocId));
					_dsDetalleSC.setDetalleScCantidadPedida(row, _dsDetalleSC
							.getDetalleScCantidadSolicitada(row));
				}

				_dsDetalleSC.update(conn);

				// update the SC states			
				SolicitudCompraTransiciones.agregarEnOc(conn,
						_dsDetalleSC, getCurrentRequest().getRemoteHost(),
						getSessionManager().getWebSiteUser().getUserID());


				dsOrdenCompra.resetStatus();
				_dsDetalleSC.resetStatus();
				conn.commit();

				_dsDetalleSC.filter(null);

				this.gotoSiteMapPage("EditarOrdenCompra", "?orden_compra_id="
						+ dsOrdenCompra.getOrdenesCompraOrdenCompraId(ocId));

			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (SQLException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (ValidationException ex) {
				MessageLog.writeErrorMessage(ex, null);
				for (String er : ex.getStackErrores()) {
					displayErrorMessage(er);
				}
				return false;
			} finally {
				if (conn != null) {
					conn.rollback();
					conn.freeConnection();
				}
			}
		}

		// marca - desmarca todos los partes del datasource como seleccionados
		if (component == _desSeleccionaTodoBUT1) {
			seleccionarTodo = !seleccionarTodo;
		}

		if(conn != null)
			conn.freeConnection();
		armaBotonera();
		return super.submitPerformed(event);

	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) throws Exception {

		try {
			// si la página es requerida por si misma o está en proceso de
			// recargar un proyecto no hago nada
			if (!isReferredByCurrentPage() || isRecargar()) {
				// verifico si tiene parámetro
				setRow_id(getIntParameter("solicitud_compra_id"));
				if (isRecargar())
					setRow_id(_dsSolicitudCompra.getSolicitudesCompraSolicitudCompraId());
				if (getRow_id() > 0) {
					// Viene seteado el proyecto. lo recupero sino no se hace
					// nada

					// resetea todos los datasource
					_dsSolicitudCompra.reset();
					_dsDetalleSC.reset();

					// recupera toda la información para el proyecto
					_dsSolicitudCompra.retrieve("solicitudes_compra.solicitud_compra_id = "	+ Integer.toString(getRow_id()));
					_dsSolicitudCompra.waitForRetrieve();
					_dsSolicitudCompra.gotoFirst();

					// sigue recuperando información del resto de los detalles
					// (actividades y tareas)

					_dsDetalleSC.retrieve("detalle_sc.solicitud_compra_id = " + Integer.toString(getRow_id()));

					if (_dsDetalleSC.gotoFirst())
						for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
							_dsDetalleSC.setMontoTotal(i, _dsSolicitudCompra);
							// activo el link a la CC si corresponde
							if (_dsDetalleSC.getDetalleScCotizacionCompraId(i) > 0) 	
								_lnkCc1.setVisible(true);					
							else
								_lnkOc1.setVisible(false);
							// activo el link a la OC si corresponde
							if (_dsDetalleSC.getDetalleScOrdenCompraId(i) > 0) 	
								_lnkOc1.setVisible(true);					
							else
								_lnkOc1.setVisible(false);
					
						}

					setDatosBasicosSolicitud();
					setTareaLookupURL();
				}
			}
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}

		setDatosBasicosSolicitud();
		armaBotonera();
		super.pageRequested(event);
	}

	public void setTareaLookupURL() throws DataStoreException {
		_tarea3.setLookUpPageURL("%LkpTareasProyecto?proyecto_id="
				+ _dsSolicitudCompra.getSolicitudesCompraProyectoId());
	}

	private void setDatosBasicosSolicitud() throws DataStoreException,
			SQLException {

		try {
			int currentUser = getSessionManager().getWebSiteUser().getUserID();

			setRow_id(_dsSolicitudCompra
					.getSolicitudesCompraSolicitudCompraId());

			String titulo = "Solicitud de materiales Nº" + getRow_id();
			if (_dsSolicitudCompra.getEstadoNombre() != null)
				titulo += " (" + _dsSolicitudCompra.getEstadoNombre() + ")";
			_detailformdisplaybox1.setHeadingCaption(titulo);
			_dsSolicitudCompra.setCurrentWebsiteUserId(currentUser);
			_dsSolicitudCompra.setEsquemaConfiguracionId(Integer
					.parseInt(getPageProperties().getThemeProperty(null,
							"EsquemaConfiguracionIdSolicitudesCompra")));

//			if ((_dsSolicitudCompra.getRowStatus() != DataStore.STATUS_NEW)
//				&& (_dsSolicitudCompra.getRowStatus() != DataStore.STATUS_NEW_MODIFIED)) 
				_dsSolicitudCompra.setTotalSolicitud(_dsSolicitudCompra
						.getAtributoTotalSolicitud());
			

			String estado = _dsSolicitudCompra.getSolicitudesCompraEstado();
			if (!"0006.0002".equalsIgnoreCase(estado))
				_dsSolicitudCompra.setObservaciones();

			if ("0006.0002".equalsIgnoreCase(estado)
					|| "0006.0004".equalsIgnoreCase(estado)
					|| "0006.0003".equalsIgnoreCase(estado)
					|| "0006.0005".equalsIgnoreCase(estado)) {
				_observacionX1.setVisible(true);
				_observacionX2.setVisible(true);
			} else {
				_observacionX1.setVisible(false);
				_observacionX2.setVisible(false);
			}

			if (UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
				_nombre_completo_solicitante2.setEnabled(true);
				_dsSolicitudCompra
						.setSolicitudesCompraUserIdComprador(currentUser);
				if (_monto_unitario1 != null)
					_monto_unitario1.setReadOnly(false);
			} else {
				if (_monto_unitario1 != null)
					_monto_unitario1.setReadOnly(true);
				int solicitante = _dsSolicitudCompra
						.getSolicitudesCompraUserIdSolicita();
				if (solicitante == 0) {
					_dsSolicitudCompra
							.setSolicitudesCompraUserIdSolicita(currentUser);
				}
				_nombre_completo_solicitante2.setEnabled(false);

				if ("0006.0002".equalsIgnoreCase(_dsSolicitudCompra
						.getSolicitudesCompraEstado())
						&& !InstanciasAprobacionModel.isUsuarioHabilitado(
								currentUser, "solicitudes_compra", getRow_id())) {
					_customBUT100.setEnabled(false);
					_customBUT110.setEnabled(false);
					_customBUT120.setEnabled(false);
				} else {
					_customBUT100.setEnabled(true);
					_customBUT110.setEnabled(true);
					_customBUT120.setEnabled(true);
				}
			}

			// setea la URL del reporte a generar al presionar el botón de
			// impresión
			String URL = armarUrlReporte("XLS", "solicitud_compra",
					"&Parameter_solicitud_compra_id=" + getRow_id());
			_imprimirSolicitudCompraBUT1.setHref(URL);

			URL = armarUrlReporte("PDF", "solicitud_compra",
					"&Parameter_solicitud_compra_id=" + getRow_id());
			_imprimirSolicitudCompraBUT2.setHref(URL);

			_verFirmantes.setHref("ListaFirmantes.jsp?solicitud_id="
					+ getRow_id());
			// hide tarea lookup if disabled in properties file
			if ("false".equalsIgnoreCase(getPageProperties().getProperty(
					"ShowTareaLookup")))
				_tarea3.setEnabled(false);
			
			// setea el boton de seleccion/deseleccion segun corresponda
			if (_dsDetalleSC.getRowCount() == 0) seleccionarTodo = true;
			
			if (seleccionarTodo)
				_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
			else
				_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.deseleccion");		
			
			_desSeleccionaTodoBUT1.setOnClick("CheckAll(" + seleccionarTodo + ");");

		} catch (ParseException ex) {
			displayErrorMessage("Ocurrió un error de parseo en la aplicación: "
					+ ex.getMessage());
		}

	}

	/**
	 * Determina si existe un registro en contexto, recupera su estado y arma la
	 * botonera acorde
	 * 
	 * @throws DataStoreException
	 *             TODO generalizar este método para un número indefinido de
	 *             transiciones entre estados TODO considerar recuperar todo el
	 *             circuito para la entidad actual y recuperar los estados de un
	 *             mismo model. Se reducirían las interacciones con la BD.
	 * @throws SQLException 
	 */
	private void armaBotonera() throws DataStoreException, SQLException {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		String SQL;
		String nombre_boton;
		String estado = null;

		// boton genera OC solo es visible si ???

		// resetea todos los botones
		_customBUT100.setVisible(false);
		_customBUT110.setVisible(false);
		_customBUT120.setVisible(false);
		_customBUT130.setVisible(false);
		_customBUT140.setVisible(false);
		_customBUT150.setVisible(false);
		_generarOCBUT1.setVisible(false);

		// controla estar dentro de un contexto de Informe
		if (_dsSolicitudCompra.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar una solicitud de materiales para recuperar su estado");
		}

		estado = _dsSolicitudCompra.getString("solicitudes_compra.estado");

		// mostrar el boton de generar OC solo si hay articulos sin OC asociado
		_dsDetalleSC.setFindExpression("detalle_sc.orden_compra_id == null");
		if (_dsDetalleSC.findFirst()) {
			// chequeo que el usuario tenga el rol COMPRADOR
			int currentUser = getSessionManager().getWebSiteUser().getUserID();
			if (UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR"))
				_generarOCBUT1.setVisible(true);
			else
				_generarOCBUT1.setVisible(false);
		}

		try {
			conn = DBConnection.getConnection("infraestructura");

			// determina datos para evaluar las transiciones y acciones del
			// circuiro
			// recupero la columna para el circuito
			// Si no existe configuración no hace nada
			/*SQL = "select nombre_detalle from infraestructura.aplica_circuito where circuito = '"
					+ CIRCUITO + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);*/

			// en función de la columna del circuito, determino el estado actual
			// estado =
			// _dsSolicitudCompra.getString("solicitudes_compra.estado");

			// recorro los estados y seteo los botones
			SQL = "SELECT t.prompt_accion,t.accion,a.manual FROM infraestructura.transicion_estados t left join infraestructura.estados e on t.estado_origen = e.estado " +
					" left join infraestructura.acciones_apps a on t.accion = a.accion "
					+ "where e.circuito = '"
					+ CIRCUITO
					+ "' and t.estado_origen = '" + estado + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				int i = 100;
				com.salmonllc.html.HtmlSubmitButton boton;
				do {
					if ("V".equalsIgnoreCase(r.getString(3))) {
						nombre_boton = "customBUT" + i;
						boton = (com.salmonllc.html.HtmlSubmitButton) this
						.getComponent(nombre_boton);
						if (boton != null) {
							boton.setVisible(true);
							boton.setDisplayName(r.getString(1));
							boton.setDisplayNameLocaleKey(Integer.toString(r
									.getInt(2)));
							boton
							.setButtonFontStyle("font-weight:bold; COLOR: red");
						}
						i = i + 10;
					}
				} while (r.next() && i < 150);
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

	}

	public boolean isRecargar() {
		return recargar;
	}

	public void setRecargar(boolean recargar) {
		this.recargar = recargar;
	}

	private boolean isModificable(String estadoActual) {
		Set<String> estadosSet = new HashSet<String>();
		String[] estados = getPageProperties().getThemeProperty(null,
				ESTADOS_DE_MODIFICACION_SOLICITUDES_COMPRA).split(",");
		for (int i = 0; i < estados.length; i++)
			estadosSet.add(estados[i]);
		return estadoActual == null ? true : estadosSet.contains(estadoActual);

	}
}
