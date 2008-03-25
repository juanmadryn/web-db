//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AbmcSolicitudCompraController: a SOFIA generated controller
 */
public class AbmcSolicitudCompraController extends BaseEntityController
		implements SubmitListener, PageListener, ValueChangedListener {

	/**
	 * 
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
	public com.salmonllc.html.HtmlText _monto_unitario1;
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
	public com.salmonllc.html.HtmlTextEdit _articulo2;
	public com.salmonllc.html.HtmlTextEdit _cantidad_solicitada2;
	public com.salmonllc.html.HtmlTextEdit _descripcion2;
	public com.salmonllc.html.HtmlTextEdit _descripcion4;
	public com.salmonllc.html.HtmlTextEdit _monto_unitario2;
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
	public HtmlSubmitButton _imprimirSolicitudCompraBUT1;
	public HtmlSubmitButton _articulosAgregarBUT1;
	public HtmlSubmitButton _articulosEliminarBUT1;
	public HtmlSubmitButton _articulosCancelarBUT1;

	public com.salmonllc.html.HtmlSubmitButton _customBUT100;

	public com.salmonllc.html.HtmlSubmitButton _customBUT110;

	public com.salmonllc.html.HtmlSubmitButton _customBUT120;

	public com.salmonllc.html.HtmlSubmitButton _customBUT130;

	public com.salmonllc.html.HtmlSubmitButton _customBUT140;

	public com.salmonllc.html.HtmlSubmitButton _customBUT150;

	private String SELECCION_DETALLE_FLAG = "SELECCION_DETALLE_FLAG";

	private static final String CIRCUITO = "0006";

	private boolean recargar = false;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// completo y asigno botones custom

		_nuevaSolicitudCompraBUT1 = new HtmlSubmitButton(
				"nuevaSolicitudCompraBUT1", "Nueva solicitud", this);
		_nuevaSolicitudCompraBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevaSolicitudCompraBUT1);

		_grabarSolicitudCompraBUT1 = new HtmlSubmitButton(
				"grabarSolicitudCompraBUT1", "Grabar", this);
		_grabarSolicitudCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarSolicitudCompraBUT1);

		_imprimirSolicitudCompraBUT1 = new HtmlSubmitButton(
				"imprimirSolicitudCompraBUT1", "Imprimir solicitud", this);
		_imprimirSolicitudCompraBUT1.setAccessKey("I");
		_detailformdisplaybox1.addButton(_imprimirSolicitudCompraBUT1);

		_articulosAgregarBUT1 = new HtmlSubmitButton("articulosAgregarBUT1",
				"Agregar", this);
		_articulosAgregarBUT1.setAccessKey("A");
		_listformdisplaybox2.addButton(_articulosAgregarBUT1);

		_articulosEliminarBUT1 = new HtmlSubmitButton("articulosEliminarBUT1",
				"Eliminar", this);
		_articulosEliminarBUT1.setAccessKey("E");
		_listformdisplaybox2.addButton(_articulosEliminarBUT1);

		_articulosCancelarBUT1 = new HtmlSubmitButton("articulosCancelarBUT1",
				"Cancelar", this);
		_articulosCancelarBUT1.setAccessKey("C");
		_listformdisplaybox2.addButton(_articulosCancelarBUT1);

		/*
		 * _articulosGrabarBUT1 = new HtmlSubmitButton("articulosGrabarBUT1",
		 * "Grabar", this); _articulosGrabarBUT1.setAccessKey("C");
		 * _listformdisplaybox2.addButton(_articulosGrabarBUT1);
		 */

		// agrega los listener a lso botones
		_nuevaSolicitudCompraBUT1.addSubmitListener(this);
		_grabarSolicitudCompraBUT1.addSubmitListener(this);
		_imprimirSolicitudCompraBUT1.addSubmitListener(this);
		_articulosAgregarBUT1.addSubmitListener(this);
		_articulosEliminarBUT1.addSubmitListener(this);
		_articulosCancelarBUT1.addSubmitListener(this);
		// _articulosGrabarBUT1.addSubmitListener(this);
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);

		// _monto_unitario2.addValueChangedListener(this);
		// _cantidad_solicitada2.addValueChangedListener(this);

		// agrego columna de seleccion
		_dsDetalleSC.addBucket(SELECCION_DETALLE_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsDetalleSC, SELECCION_DETALLE_FLAG);
		_seleccion_detalle2.setFalseValue(null);

		// seteo la validación para los datasource
		_dsSolicitudCompra.setAutoValidate(true);
		_dsDetalleSC.setAutoValidate(true);

		// seteo inicial para la visualización de componentes
		// genero un nuevo proyecto vacio.
		_dsSolicitudCompra.reset();
		_dsDetalleSC.reset();

		_dsSolicitudCompra.insertRow();
		
		addPageListener(this);
		_menuBUT.addSubmitListener(this);
		
		setTabla_principal("solicitudes_compra");
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
			
			// 
			//_dsSolicitudCompra.reloadRow();
			if ("0006.0006".equalsIgnoreCase(_dsSolicitudCompra.getEstadoActual())) {
				try {
					_dsSolicitudCompra.ejecutaAccion(19,	"0006",
							this.getCurrentRequest().getRemoteHost(), 
							getSessionManager().getWebSiteUser().getUserID(), 
							"solicitudes_compra", conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
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
			// genero un nuevo proyecto vacio.
			_dsSolicitudCompra.reset();
			_dsDetalleSC.reset();
			_dsSolicitudCompra.gotoRow(_dsSolicitudCompra.insertRow());
		}

		if (component == _grabarSolicitudCompraBUT1) {
			String estado = _dsSolicitudCompra.getSolicitudesCompraEstado();
			// si la solicitud esta en estado generado o esta siendo generada
			if ("0006.0001".equalsIgnoreCase(estado)
					|| "0006.0005".equalsIgnoreCase(estado) || estado == null) {
				try {

					// grabo todos los datasource
					if (_dsSolicitudCompra.getRow() == -1)
						return false;

					if (_dsSolicitudCompra.getSolicitudesCompraUserIdSolicita() == 0)
						_dsSolicitudCompra
								.setSolicitudesCompraUserIdSolicita(getUserFromSession(
										getCurrentRequest().getRemoteAddr())
										.getUserID());

					_dsSolicitudCompra.update();

					// actualizo los detalles

					if (_dsDetalleSC.getRow() != -1) {
						_dsDetalleSC.update();
						for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
							if (_dsDetalleSC.getDetalleScMontoUltimaCompra(row) == 0) {
								try {
									_dsDetalleSC

											.setDetalleScMontoUltimaCompra(
													row,
													Float
															.parseFloat(AtributosEntidadModel
																	.getValorAtributoObjeto(
																			"MONTO_ULTIMA_COMPRA",
																			_dsDetalleSC
																					.getDetalleScArticuloId(row),
																			"TABLA",
																			"articulos")));
									_dsDetalleSC
											.setDetalleScFechaUltimaCompra(
													row,
													AtributosEntidadModel
															.getValorAtributoObjeto(
																	"FECHA_ULTIMA_COMPRA",
																	_dsDetalleSC
																			.getDetalleScArticuloId(row),
																	"TABLA",
																	"articulos"));
								} catch (NullPointerException e) {

								}
							}
							if (_dsDetalleSC.getDetalleScMontoUnitario(row) == 0)
								_dsDetalleSC
										.setDetalleScMontoUnitario(
												row,
												_dsDetalleSC
														.getDetalleScMontoUltimaCompra(row));
							_dsDetalleSC.setMontoTotal(row);
							if (_dsDetalleSC.getDetalleScUnidadMedida(row) == null)
								_dsDetalleSC
										.setDetalleScUnidadMedida(
												row,
												AtributosEntidadModel
														.getValorAtributoObjeto(
																"UNIDAD_DE_MEDIDA",
																_dsDetalleSC
																		.getDetalleScArticuloId(row),
																"TABLA",
																"articulos"));
						}
					}

					_dsSolicitudCompra.reloadRow();

					setTareaLookupURL();

				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsSolicitudCompra);
					displayErrorMessage(ex.getMessage());
					return false;
				}
			} else {
				// si la solicitud no está generada, bloqueo toda modificación
				displayErrorMessage("No puede modificar la solicitud en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}

		}

		if (component == _imprimirSolicitudCompraBUT1) {
			if (_dsSolicitudCompra.getRow() != -1) {
				String URL = armarUrlReporte("PDF", "solicitud_compra",
						"&Parameter_solicitud_compra_id=" + getRow_id());
				System.out.println(URL);
				sendRedirect(URL);
			} else {
				displayErrorMessage("Debe seleccionar una solicitud");
			}
		}

		if (component == _articulosAgregarBUT1) {
			// crea un nuevo registro de tarea
			try {
				String estado = _dsSolicitudCompra.getSolicitudesCompraEstado();
				if ("0006.0001".equalsIgnoreCase(estado)
						|| "0006.0005".equalsIgnoreCase(estado)
						|| estado == null) {
					int solicitud_id = _dsSolicitudCompra
							.getSolicitudesCompraSolicitudCompraId();

					if (solicitud_id > 0) {

						int reg = _dsDetalleSC.insertRow();
						_dsDetalleSC.gotoRow(reg);

						_dsDetalleSC
								.setDetalleScSolicitudCompraId(solicitud_id);

						setTareaLookupURL();

					}
				} else {
					// si la solicitud no está generada, bloqueo toda
					// modificación
					displayErrorMessage("No puede modificar la solicitud en el estado actual.");
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
			String estado = _dsSolicitudCompra.getSolicitudesCompraEstado();
			if ("0006.0001".equalsIgnoreCase(estado)
					|| "0006.0005".equalsIgnoreCase(estado) || estado == null) {
				// elimina todas las actividades seleccionadas
				for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
					if (_dsDetalleSC.getInt(i, SELECCION_DETALLE_FLAG) == 1) {
						// Rol marcado para selección
						try {
							_dsDetalleSC.deleteRow(i);
							_dsDetalleSC.update();
						} catch (DataStoreException ex) {
							displayErrorMessage("No se ha podido eliminar el artículo seleccionado. Error: "
									+ ex.getMessage());
							_dsDetalleSC.unDeleteRow(i);
							_dsDetalleSC.setInt(i, SELECCION_DETALLE_FLAG, 0);
							return false;
						} catch (SQLException ex) {
							displayErrorMessage(ex.getMessage());
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
					setRow_id(_dsSolicitudCompra
							.getSolicitudesCompraSolicitudCompraId());
				if (getRow_id() > 0) {
					// Viene seteado el proyecto. lo recupero sino no se hace
					// nada

					// resetea todos los datasource
					_dsSolicitudCompra.reset();
					_dsDetalleSC.reset();

					// recupera toda la información para el proyecto
					_dsSolicitudCompra
							.retrieve("solicitudes_compra.solicitud_compra_id = "
									+ Integer.toString(getRow_id()));
					_dsSolicitudCompra.gotoFirst();

					// sigue recuperando información del resto de los detalles
					// (actividades y tareas)

					_dsDetalleSC.retrieve("detalle_sc.solicitud_compra_id = "
							+ Integer.toString(getRow_id()));
					if (_dsDetalleSC.gotoFirst()) {
						for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
							_dsDetalleSC.setMontoTotal(i);
						}
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

	/**
	 * Process the page request end event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	/*
	 * public void pageRequestEnd(PageEvent event) { }
	 */

	/**
	 * Process the page submit end event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	/*
	 * public void pageSubmitEnd(PageEvent event) { }
	 */

	/**
	 * Process the page submit event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	/*
	 * public void pageSubmitted(PageEvent event) { }
	 */

	public boolean valueChanged(ValueChangedEvent event) throws Exception {
		return false;
	}

	public void setTareaLookupURL() throws DataStoreException {
		_tarea3.setLookUpPageURL("%LkpTareasProyecto?proyecto_id="
				+ _dsSolicitudCompra.getSolicitudesCompraProyectoId());
	}

	private void setDatosBasicosSolicitud() throws DataStoreException,
			SQLException {
		int currentUser = getSessionManager().getWebSiteUser().getUserID();
		
		setRow_id(_dsSolicitudCompra.getSolicitudesCompraSolicitudCompraId());
		_detailformdisplaybox1.setHeadingCaption("Solicitud de compra Nº"
				+ getRow_id());
		_dsSolicitudCompra.setCurrentWebsiteUserId(currentUser);
		_dsSolicitudCompra.setEsquemaConfiguracionId(Integer
				.parseInt(getPageProperties().getThemeProperty(null,
						"EsquemaConfiguracionIdSolicitudesCompra")));
		_dsSolicitudCompra.setTotalSolicitud(_dsSolicitudCompra
				.getAtributoTotalSolicitud());

		String estado = _dsSolicitudCompra.getSolicitudesCompraEstado();
		if (!"0006.0002".equalsIgnoreCase(estado))
			_dsSolicitudCompra.setObservaciones();

		if ("0006.0002".equalsIgnoreCase(estado)
				|| "0006.0004".equalsIgnoreCase(estado)
				|| "0006.0005".equalsIgnoreCase(estado)) {
			_observacionX1.setVisible(true);
			_observacionX2.setVisible(true);
		} else {
			_observacionX1.setVisible(false);
			_observacionX2.setVisible(false);
		}
		
		if ((new UsuarioRolesModel("infraestructura", "infraestructura"))
				.isRolUsuario(currentUser,
						"COMPRADOR"))
			_nombre_completo_solicitante2.setEnabled(true);
		else {
			_dsSolicitudCompra.setSolicitudesCompraUserIdSolicita(currentUser);
			_nombre_completo_solicitante2.setEnabled(false);			
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
	 */
	private void armaBotonera() throws DataStoreException {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		String SQL;
		String nombre_boton;
		String estado = null;

		// resetea todos los botones
		_customBUT100.setVisible(false);
		_customBUT110.setVisible(false);
		_customBUT120.setVisible(false);
		_customBUT130.setVisible(false);
		_customBUT140.setVisible(false);
		_customBUT150.setVisible(false);

		// controla estar dentro de un contexto de Informe
		if (_dsSolicitudCompra.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar una solicitud de compra para recuperar su estado");
		}

		try {
			conn = DBConnection.getConnection("infraestructura");

			// determina datos para evaluar las transiciones y acciones del
			// circuiro
			// recupero la columna para el circuito
			// Si no existe configuración no hace nada
			SQL = "select nombre_detalle from infraestructura.aplica_circuito where circuito = '"
					+ CIRCUITO + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			// en función de la columna del circuito, determino el estado actual
			estado = _dsSolicitudCompra.getString("solicitudes_compra.estado");

			// recorro los estados y seteo los botones
			SQL = "SELECT prompt_accion,accion FROM infraestructura.transicion_estados t left join infraestructura.estados e on t.estado_origen = e.estado "
					+ "where e.circuito = '"
					+ CIRCUITO
					+ "' and t.estado_origen = '" + estado + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				int i = 100;
				com.salmonllc.html.HtmlSubmitButton boton;
				do {
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
}
