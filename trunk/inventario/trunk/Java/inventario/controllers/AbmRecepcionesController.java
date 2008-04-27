//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.reglasNegocio.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AbmRecepcionesController: a SOFIA generated controller
 */
public class AbmRecepcionesController extends BaseEntityController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794864105993076143L;
	// Visual Components
	public com.salmonllc.html.HtmlCheckBox _seleccion_detalle2;
	public com.salmonllc.html.HtmlDropDownList _proveedor2;
	public com.salmonllc.html.HtmlLookUpComponent _articulo2;
	public com.salmonllc.html.HtmlLookUpComponent _valorTE11;
	public com.salmonllc.html.HtmlSubmitButton _customBUT100;
	public com.salmonllc.html.HtmlSubmitButton _customBUT110;
	public com.salmonllc.html.HtmlSubmitButton _customBUT120;
	public com.salmonllc.html.HtmlSubmitButton _customBUT130;
	public com.salmonllc.html.HtmlSubmitButton _customBUT140;
	public com.salmonllc.html.HtmlSubmitButton _customBUT150;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _atributoCAP15;
	public com.salmonllc.html.HtmlText _atributoTXT6;
	public com.salmonllc.html.HtmlText _cantidad_recibida1;
	public com.salmonllc.html.HtmlText _almacen1;
	public com.salmonllc.html.HtmlText _completo1;
	public com.salmonllc.html.HtmlText _completo2;
	public com.salmonllc.html.HtmlText _descripcion3;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _nombre_articulo1;
	public com.salmonllc.html.HtmlText _orden_de_compra_id1;
	public com.salmonllc.html.HtmlText _orden_de_compra_id2;
	public com.salmonllc.html.HtmlText _proveedor1;
	public com.salmonllc.html.HtmlText _recibe1;
	public com.salmonllc.html.HtmlText _recibe2;
	public com.salmonllc.html.HtmlText _seleccion_detalle1;
	public com.salmonllc.html.HtmlText _unidad_medida1;
	public com.salmonllc.html.HtmlText _unidad_medida2;
	public com.salmonllc.html.HtmlText _valorCAP16;
	public com.salmonllc.html.HtmlTextEdit _cantidad_recibida2;
	public com.salmonllc.html.HtmlTextEdit _almacen2;
	public com.salmonllc.html.HtmlTextEdit _fecha2;
	public com.salmonllc.html.HtmlText descripcion_completa_articulo1;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox4;
	public com.salmonllc.jsp.JspTable _table1;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow4;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _table1TDRow0;
	public com.salmonllc.jsp.JspTableCell _table1TDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table1TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public infraestructura.models.AtributosEntidadModel _dsAtributos;
	public inventario.models.DetalleRCModel _dsDetalle;
	public inventario.models.RecepcionesComprasModel _dsRecepciones;

	// DataSource Column Constants
	public static final String DSDETALLE_DETALLES_RC_DETALLE_RC_ID = "detalles_rc.detalle_rc_id";
	public static final String DSDETALLE_DETALLES_RC_RECEPCION_COMPRA_ID = "detalles_rc.recepcion_compra_id";
	public static final String DSDETALLE_DETALLES_RC_DETALLE_SC_ID = "detalles_rc.detalle_sc_id";
	public static final String DSDETALLE_DETALLES_RC_ALMACEN_ID = "detalles_rc.almacen_id";
	public static final String DSDETALLE_DETALLES_RC_CANTIDAD = "detalles_rc.cantidad_recibida";
	public static final String DSDETALLE_RECEPCIONES_COMPRAS_FECHA = "recepciones_compras.fecha";
	public static final String DSDETALLE_RECEPCIONES_COMPRAS_ESTADO = "recepciones_compras.estado";
	public static final String DSDETALLE_RECEPCIONES_COMPRAS_PROVEDOR_ID = "recepciones_compras.proveedor_id";
	public static final String DSDETALLE_RECEPCIONES_COMPRAS_USER_ID_COMPLETA = "recepciones_compras.user_id_completa";
	public static final String DSDETALLE_RECEPCIONES_COMPRAS_USER_ID_RECIBE = "recepciones_compras.user_id_recibe";
	public static final String DSDETALLE_DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DSDETALLE_DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DSDETALLE_DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DSDETALLE_DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DSDETALLE_DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DSDETALLE_DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DSDETALLE_DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DSDETALLE_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLE_DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DSDETALLE_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLE_DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DSDETALLE_DETALLE_SC_UNIDAD_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String DSDETALLE_ALMACENES_NOMBRE = "almacenes.nombre";
	public static final String DSDETALLE_ALMACENES_DESCRIPCION = "almacenes.descripcion";
	public static final String DSDETALLE_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSDETALLE_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSDETALLE_UNIDAD_MEDIDA_NOMBRE = "unidad_medida.nombre";

	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID = "atributos_entidad.atributo_entidad_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ATRIBUTO_ID = "atributos_entidad.atributo_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ENTIDAD_ID = "atributos_entidad.entidad_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_OBJETO_ID = "atributos_entidad.objeto_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_TIPO_OBJETO = "atributos_entidad.tipo_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO = "atributos_entidad.nombre_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR = "atributos_entidad.valor";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_ENTERO = "atributos_entidad.valor_entero";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_REAL = "atributos_entidad.valor_real";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_FECHA = "atributos_entidad.valor_fecha";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_LOGICO = "atributos_entidad.valor_logico";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_ROL = "atributos_rol.rol";
	public static final String DSATRIBUTOS_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";
	public static final String DSATRIBUTOS_CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_NOMBRE_OBJETO = "atributos_rol.nombre_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_TIPO_OBJETO = "atributos_rol.tipo_objeto";

	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID = "recepciones_compras.recepcion_compra_id";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_FECHA = "recepciones_compras.fecha";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_ESTADO = "recepciones_compras.estado";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_PROVEDOR_ID = "recepciones_compras.proveedor_id";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_USER_ID_COMPLETA = "recepciones_compras.user_id_completa";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_USER_ID_RECIBE = "recepciones_compras.user_id_recibe";
	public static final String DSRECEPCIONES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSRECEPCIONES_PROVEEDORES_NOMBRE = "proveedores.nombre";
	public static final String DSRECEPCIONES_USER_COMPLETA_NOMBRE_COMPLETO = "user_completa.nombre_completo";
	public static final String DSRECEPCIONES_USER_RECIBE_NOMBRE_COMPLETO = "user_recibe.nombre_completo";

	// customs components
	public HtmlSubmitButton _nuevaRecepcionCompraBUT1;
	public HtmlSubmitButton _grabarRecepcionCompraBUT1;
	public HtmlSubmitButton _articulosAgregarBUT1;
	public HtmlSubmitButton _articulosEliminarBUT1;
	public HtmlSubmitButton _articulosCancelarBUT1;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT1;

	public com.salmonllc.jsp.JspLink _imprimirRecepcionCompraBUT1;
	public com.salmonllc.jsp.JspLink _imprimirRecepcionCompraBUT2;

	public com.salmonllc.html.HtmlSubmitButton _grabarAtributoBUT1;
	public com.salmonllc.html.HtmlSubmitButton _atributoGenerarAtributosBUT11;

	private String SELECCION_DETALLE_FLAG = "SELECCION_DETALLE_FLAG";

	private static final String CIRCUITO = "0009";

	private boolean recargar = false;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		try {
			super.initialize();
		} catch (Exception ex) {
			displayErrorMessage(ex.getMessage());
		}
		// completo y asigno botones custom

		_grabarRecepcionCompraBUT1 = new HtmlSubmitButton(
				"grabarRecepcionCompraBUT1", "Grabar", this);
		_grabarRecepcionCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarRecepcionCompraBUT1);

		_nuevaRecepcionCompraBUT1 = new HtmlSubmitButton(
				"nuevaRecepcionCompraBUT1", "Nueva recepcion", this);
		_nuevaRecepcionCompraBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevaRecepcionCompraBUT1);

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

		_desSeleccionaTodoBUT1 = new HtmlSubmitButton("desSeleccionaTodoBUT2",
				"Seleccionar todo", this);
		_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
		_listformdisplaybox2.addButton(_desSeleccionaTodoBUT1);

		// botones para atributos
		_grabarAtributoBUT1 = new HtmlSubmitButton("grabarAtributoBUT1",
				"grabar", this);
		_listformdisplaybox4.addButton(_grabarAtributoBUT1);

		_atributoGenerarAtributosBUT11 = new HtmlSubmitButton(
				"atributoGenerarAtributosBUT11", "generar", this);
		_listformdisplaybox4.addButton(_atributoGenerarAtributosBUT11);

		// agrega los listener a lso botones
		_nuevaRecepcionCompraBUT1.addSubmitListener(this);
		_grabarRecepcionCompraBUT1.addSubmitListener(this);
		_articulosAgregarBUT1.addSubmitListener(this);
		_articulosEliminarBUT1.addSubmitListener(this);
		_articulosCancelarBUT1.addSubmitListener(this);
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);
		_desSeleccionaTodoBUT1.addSubmitListener(this);

		_atributoGenerarAtributosBUT11.addSubmitListener(this);
		_grabarAtributoBUT1.addSubmitListener(this);

		// agrego columna de seleccion
		_dsDetalle.addBucket(SELECCION_DETALLE_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsDetalle, SELECCION_DETALLE_FLAG);
		_seleccion_detalle2.setFalseValue(null);

		// seteo la validación para los datasource
		_dsRecepciones.setAutoValidate(true);
		_dsDetalle.setAutoValidate(true);

		// seteo inicial para la visualización de componentes
		// genero un nuevo proyecto vacio.
		_dsRecepciones.reset();
		_dsDetalle.reset();

		_dsRecepciones.insertRow();

		_proveedor2.setFocus();

		_detailformdisplaybox1.setReloadRowAfterSave(true);

		setTabla_principal("recepciones_compras");
		set_dsAtributos(_dsAtributos);
		setContainer(_listformdisplaybox4);

		setDatosBasicosRecepcion();

		seteaBotonesAtributos();
		recuperaAtributosBotonSeleccionado();
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

		setDatosBasicosRecepcion();

		try {
			if (component == _customBUT100) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT100
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT110) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT110
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT120) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT120
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT130) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT130
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT140) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT140
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT150) {
				if (CIRCUITO != null) {
					_dsRecepciones.ejecutaAccion(Integer.parseInt(_customBUT150
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
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

		if (component == _nuevaRecepcionCompraBUT1) {
			// genero una nueva recepcion vacia.
			_dsRecepciones.reset();
			_dsDetalle.reset();
			_dsAtributos.reset();
			_dsRecepciones.gotoRow(_dsRecepciones.insertRow());
			_proveedor2.setFocus();
		}

		if (component == _grabarRecepcionCompraBUT1) {
			conn = DBConnection.getConnection("inventario", "inventario");
			conn.beginTransaction();

			// si la recepcion esta en estado generado o esta siendo generada
			if (isModificable(_dsRecepciones.getRecepcionesComprasEstado())) {
				try {
					// grabo todos los datasource
					if (_dsRecepciones.getRow() == -1)
						return false;

					_dsRecepciones.update(conn);

					// actualizo los detalles
					if (_dsDetalle.getRow() != -1) {
						_dsDetalle.update(conn);
					}

					if (_dsAtributos.getRow() == -1) {
						if (!(_dsRecepciones
								.getRecepcionesComprasRecepcionCompraId() > 0)) {
							displayErrorMessage("Debe seleccionar un proyecto para poder generar sus atributos");
							return false;
						}
						// manda a generar los atributos de la entidad
						_dsAtributos.generaAtributosObjetoAplicacion(
								getRow_id(), getTabla_principal());
					} else
						_dsAtributos.update(conn);

					_dsRecepciones.resetStatus();
					_dsDetalle.resetStatus();
					_dsAtributos.resetStatus();

					conn.commit();
					_dsDetalle.reloadRows();

				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					String mensaje = "";
					if (ex.getRow() != -1) {
						mensaje = " -> Detalle Nº: " + (ex.getRow() + 1);
						_articulo2.setFocus(ex.getRow());
					}
					displayErrorMessage(ex.getMessage() + mensaje);
					return false;
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsRecepciones);
					displayErrorMessage(ex.getMessage());
					return false;
				} finally {
					if (conn != null) {
						conn.rollback();
						conn.freeConnection();
					}
				}

			} else {
				// si la recepcion no está generada, bloqueo toda modificación
				displayErrorMessage("No puede modificar la recepcion en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}

		}

		if (component == _articulosAgregarBUT1) {
			// crea un nuevo registro de tarea
			try {
				setRow_id(_dsRecepciones
						.getRecepcionesComprasRecepcionCompraId());
				if (isModificable(_dsRecepciones.getRecepcionesComprasEstado())) {
					if (getRow_id() == 0)
						_dsRecepciones.update();
					setRow_id(_dsRecepciones
							.getRecepcionesComprasRecepcionCompraId());
					if (_dsDetalle.getRow() != -1)
						_dsDetalle.update();
					_dsDetalle.reloadRows();
					// recupero el row actual
					int rowActual = _dsDetalle.getRow();

					int row = _dsDetalle.insertRow(0);

					_dsDetalle.setDetallesRcRecepcionCompraId(row, getRow_id());

					// si existe row anterior, sólo copia valores básicos
					// int tarea = _dsDetalle.getDetalleScTareaId(rowActual +
					// 1);
					// if (rowActual != -1 && tarea != 0) {
					// valido el parte que estoy copiando
					// _dsDetalle.setDetalleScTareaId(row, tarea);
					// }

					// hace foco en el registro
					int nroPagerow = _datatable2.getPage(row);
					int nroPageActual = _datatable2
							.getPage(_dsDetalle.getRow());
					if (nroPagerow != nroPageActual)
						_datatable2.setPage(_datatable2.getPage(row));
					_articulo2.setFocus(row, true);

				} else {
					// si la recepcion no está generada, bloqueo toda
					// modificación
					displayErrorMessage("No puede agregar artículos a la recepcion en el estado actual.");
					setRecargar(true);
					pageRequested(new PageEvent(this));
					return false;
				}
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsRecepciones);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		if (component == _articulosEliminarBUT1) {
			if (isModificable(_dsRecepciones.getRecepcionesComprasEstado())) {
				// elimina todas las actividades seleccionadas
				for (int row = _dsDetalle.getRowCount() - 1; row >= 0; row--) {
					if (_dsDetalle.getInt(row, SELECCION_DETALLE_FLAG) == 1) {
						// Rol marcado para selección
						try {
							_dsDetalle.deleteRow(row);
							// _dsDetalle.update();
						} catch (Exception e) {
							displayErrorMessage("No se ha podido eliminar el artículo seleccionado. Error: "
									+ e.getMessage());
							_dsDetalle.unDeleteRow(row);
							_dsDetalle.setInt(row, SELECCION_DETALLE_FLAG, 0);
							return false;
						}
					}
				}
			} else {
				// si la recepcion no está generada, bloqueo toda modificación
				displayErrorMessage("No puede modificar la recepcion en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}
		}

		if (component == _articulosCancelarBUT1) {
			// crea un nuevo registro de tarea
			try {
				_dsDetalle.reset();
				_dsDetalle.resetStatus();
				_dsDetalle.retrieve("detalles_rc.recepcion_compra_id ="
						+ getRow_id());
				_dsDetalle.gotoFirst();
				_dsDetalle.update();
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, _dsRecepciones);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsRecepciones);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// marca - desmarca todos los partes del datasource como seleccionados
		if (component == _desSeleccionaTodoBUT1) {
			if ("text.seleccion".equalsIgnoreCase(_desSeleccionaTodoBUT1
					.getDisplayNameLocaleKey())) {
				for (int i = 0; i < _dsDetalle.getRowCount(); i++) {
					_dsDetalle.setInt(i, SELECCION_DETALLE_FLAG, 1);
				}
				_desSeleccionaTodoBUT1
						.setDisplayNameLocaleKey("text.deseleccion");
			} else {
				for (int i = 0; i < _dsDetalle.getRowCount(); i++) {
					_dsDetalle.setInt(i, SELECCION_DETALLE_FLAG, 0);
				}
				_desSeleccionaTodoBUT1
						.setDisplayNameLocaleKey("text.seleccion");
			}
		}

		// graba atributos de entidad
		if (component == _grabarAtributoBUT1) {
			if (isModificable(_dsRecepciones.getRecepcionesComprasEstado())) {
				try {
					_dsAtributos.update();
				} catch (ValidationException ex) {
					for (String er : ex.getStackErrores())
						displayErrorMessage(er);
					MessageLog.writeErrorMessage(ex, null);
					return false;

				} catch (DataStoreException ex) {
					displayErrorMessage(ex.getMessage());
					return false;
				}
			} else {
				displayErrorMessage("No puede modificar el proyecto una vez que lo ha completado");
				return false;
			}
		}

		// genera atributos, por si faltan
		if (component == _atributoGenerarAtributosBUT11) {
			// primero determina contexto
			if (getRow_id() < 1) {
				displayErrorMessage("Debe seleccionar un proyecto para poder generar sus atributos");
				return false;
			}

			// manda a generar los atributos de la entidad
			try {
				_dsAtributos.generaAtributosObjetoAplicacion(getRow_id(),
						getTabla_principal());
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
			// seteaBotonesAtributos();
			// recuperaAtributosBotonSeleccionado();
		}

		setLookupArticulosParaRecepcionURL();
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
				setRow_id(getIntParameter("recepcion_compra_id"));
				if (isRecargar())
					setRow_id(_dsRecepciones
							.getRecepcionesComprasRecepcionCompraId());
				if (getRow_id() > 0) {
					// Viene seteado el proyecto. lo recupero sino no se hace
					// nada
					// resetea todos los datasource
					_dsRecepciones.reset();
					_dsDetalle.reset();
					_dsAtributos.reset();

					// recupera toda la información para el proyecto
					_dsRecepciones
							.retrieve("recepciones_compras.recepcion_compra_id = "
									+ getRow_id());
					_dsRecepciones.waitForRetrieve();
					_dsRecepciones.gotoFirst();

					// genero atributos si faltan
					_dsAtributos.generaAtributosObjetoAplicacion(getRow_id(),
							getTabla_principal());

					// setea los botones de los atributos
					seteaBotonesAtributos();

					// recupera la información del boton seleccionado
					recuperaAtributosBotonSeleccionado();

					_dsAtributos.gotoFirst();

					// sigue recuperando información del resto de los detalles
					// (actividades y tareas)

					_dsDetalle.retrieve("detalles_rc.recepcion_compra_id = "
							+ getRow_id());

					_dsDetalle.gotoFirst();

					setDatosBasicosRecepcion();
				}
			}
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

		setLookupArticulosParaRecepcionURL();
		setDatosBasicosRecepcion();
		armaBotonera();

		super.pageRequested(event);
	}

	/**
	 * Process the page request end event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequestEnd(PageEvent event) {
		try {
			super.pageRequestEnd(event);
		} catch (Exception ex) {
			displayErrorMessage(ex.getMessage());
		}
	}

	@Override
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		// ante cada requerimiento verifica contexto y determina detalle de
		// atributos y completa FK's
		// Es row de rol válida?
		try {
			boolean actualizar = false;
			int row = _dsRecepciones.getRow();
			int objeto_id = 0;
			int objeto_id_atributos = 0;
			if (row != -1) {

				// completa default de las columnas
				// _dsProyecto.setColumnasDefault(row);

				// recupera el id del proyecto de contexto
				objeto_id = _dsRecepciones
						.getRecepcionesComprasRecepcionCompraId();
				// si se está insertando un nuevo registro de rol, no se
				// actualiza
				if (!(_dsAtributos.getRowStatus() == DataStoreBuffer.STATUS_NEW || _dsAtributos
						.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) {
					// Ya existe detalle de atributos?
					if (_dsAtributos.getRowCount() > 0) {
						// es el mismo contexto? --> recupero la entidad del
						// detalle para verificación, siempre del primer
						// registro
						objeto_id_atributos = _dsAtributos
								.getAtributosEntidadObjetoId(0);
						if (objeto_id_atributos == 0)
							actualizar = true;
						if (objeto_id_atributos != objeto_id) {
							// Es distinto el contexto del rol de atributo
							actualizar = true;
						}
					} else {
						actualizar = true;
					}
				}
			}
		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}

	}

	/**
	 * Process the page submit event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageSubmitted(PageEvent event) {
		super.pageSubmitted(event);
	}

	private void setDatosBasicosRecepcion() throws DataStoreException,
			SQLException {

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		setRow_id(_dsRecepciones.getRecepcionesComprasRecepcionCompraId());

		String titulo = "Recepcion de compra Nº" + getRow_id();
		if (_dsRecepciones.getEstadosNombre() != null)
			titulo += " (" + _dsRecepciones.getEstadosNombre() + ")";
		_detailformdisplaybox1.setHeadingCaption(titulo);
		_dsRecepciones.setCurrentWebsiteUserId(currentUser);

		// setea la URL del reporte a generar al presionar el botón de
		// impresión
		String URL = armarUrlReporte("XLS", "recepcion_compra",
				"&Parameter_recepcion_compra_id=" + getRow_id());
		_imprimirRecepcionCompraBUT1.setHref(URL);

		URL = armarUrlReporte("PDF", "recepcion_compra",
				"&Parameter_recepcion_compra_id=" + getRow_id());
		_imprimirRecepcionCompraBUT2.setHref(URL);

		if (_dsDetalle.getRowCount() > 0)
			_proveedor2.setEnabled(false);
		else
			_proveedor2.setEnabled(true);
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
		if (_dsRecepciones.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar una recepcion de compra para recuperar su estado");
		}

		estado = _dsRecepciones.getString("recepciones_compras.estado");

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
			// estado =
			// _dsRecepciones.getString("solicitudes_compra.estado");

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

	private boolean isModificable(String estadoActual) {
		Set<String> estadosSet = new HashSet<String>();
		String[] estados = getPageProperties().getThemeProperty(null,
				ESTADOS_DE_MODIFICACION_RECEPCIONES_COMPRA).split(",");
		for (int i = 0; i < estados.length; i++)
			estadosSet.add(estados[i]);
		return estadoActual == null ? true : estadosSet.contains(estadoActual);

	}

	private void setLookupArticulosParaRecepcionURL() throws DataStoreException {
		_articulo2.setLookUpPageURL("%LkpArticulosParaRecepcion?proveedor_id="
				+ _dsRecepciones.getRecepcionesComprasProvedorId());
	}
}
