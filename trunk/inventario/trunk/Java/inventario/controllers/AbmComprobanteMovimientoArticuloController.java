//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.models.UsuarioRolesModel;
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
 * AbmComprobanteMovimientoArticuloController: a SOFIA generated controller
 */
public class AbmComprobanteMovimientoArticuloController extends
		BaseEntityController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 317179372458526303L;
	// Visual Components
	public com.salmonllc.html.HtmlCheckBox _seleccion_detalle2;
	public com.salmonllc.html.HtmlDropDownList _almacen2;
	public com.salmonllc.html.HtmlDropDownList _tipo_movimiento2;
	public com.salmonllc.html.HtmlDropDownList _unidad_medida2;	
	public com.salmonllc.html.HtmlImage _imprimirTXT2;
	public com.salmonllc.html.HtmlLookUpComponent _articulo2;
	public com.salmonllc.html.HtmlLookUpComponent _legajo1;
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;
	public com.salmonllc.html.HtmlLookUpComponent _cargo2;
	public com.salmonllc.html.HtmlLookUpComponent _tarea3;
	public com.salmonllc.html.HtmlLookUpComponent _valorTE11;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observaciones2;
	public com.salmonllc.html.HtmlSubmitButton _customBUT100;
	public com.salmonllc.html.HtmlSubmitButton _customBUT110;
	public com.salmonllc.html.HtmlSubmitButton _customBUT120;
	public com.salmonllc.html.HtmlSubmitButton _customBUT130;
	public com.salmonllc.html.HtmlSubmitButton _customBUT140;
	public com.salmonllc.html.HtmlSubmitButton _customBUT150;
	public com.salmonllc.html.HtmlText _almacen1;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _atributoCAP15;
	public com.salmonllc.html.HtmlText _atributoTXT6;
	public com.salmonllc.html.HtmlText _cantidad_entregada1;
	public com.salmonllc.html.HtmlText _cantidad_solicitada1;
	public com.salmonllc.html.HtmlText _cantidad_anulada1;
	public com.salmonllc.html.HtmlText _descripcion3;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _nombre_completo_user_prepara1;
	public com.salmonllc.html.HtmlText _nombre_completo_user_prepara2;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _observaciones3;
	public com.salmonllc.html.HtmlText _proyecto1;
	public com.salmonllc.html.HtmlText _recepcion_compra_id1;
	public com.salmonllc.html.HtmlText _recepcion_compra_id2;
	public com.salmonllc.html.HtmlText _recibe1;
	public com.salmonllc.html.HtmlText _recibe2;
	public com.salmonllc.html.HtmlText _seleccion_detalle1;
	public com.salmonllc.html.HtmlText _tarea1;
	public com.salmonllc.html.HtmlText _tipo_movimiento1;
	public com.salmonllc.html.HtmlText _unidad_medida1;
	public com.salmonllc.html.HtmlText _valorCAP16;
	public com.salmonllc.html.HtmlTextEdit _cantidad_entregada2;
	public com.salmonllc.html.HtmlTextEdit _cantidad_solicitada2;
	public com.salmonllc.html.HtmlTextEdit _cantidad_anulada2;
	public com.salmonllc.html.HtmlTextEdit _descripcion4;
	public com.salmonllc.html.HtmlTextEdit _observaciones4;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;	
	public com.salmonllc.jsp.JspLink _imprimirComprobante2;
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
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader6;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader7;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader8;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader9;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow6;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow7;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow9;
	public com.salmonllc.jsp.JspTableCell _table1TDRow0;
	public com.salmonllc.jsp.JspTableCell _table1TDRow1;
	public com.salmonllc.jsp.JspTableCell _table1TDRow2;
	public com.salmonllc.jsp.JspTableCell _table1TDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableCell _tarea2;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader1;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow1;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table1TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public infraestructura.models.AtributosEntidadModel _dsAtributos;
	public inventario.models.ComprobanteMovimientoArticuloModel _dsComprobante;
	public inventario.models.MovimientoArticuloModel _dsMovimientos;

	// DataSource Column Constants
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID = "movimiento_articulo.movimiento_articulo_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID = "movimiento_articulo.centro_costo_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_PROYECTO_ID = "movimiento_articulo.proyecto_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_TAREA_ID = "movimiento_articulo.tarea_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID = "movimiento_articulo.resumen_saldo_articulo_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID = "movimiento_articulo.comprobante_movimiento_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_ARTICULO_ID = "movimiento_articulo.articulo_id";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA = "movimiento_articulo.cantidad_solicitada";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA = "movimiento_articulo.cantidad_entregada";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA = "movimiento_articulo.cantidad_anulada";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_DESCRIPCION = "movimiento_articulo.descripcion";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_OBSERVACIONES = "movimiento_articulo.observaciones";
	public static final String DSMOVIMIENTOS_MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID = "movimiento_articulo.unidad_medida_id";
	public static final String DSMOVIMIENTOS_PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String DSMOVIMIENTOS_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSMOVIMIENTOS_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSMOVIMIENTOS_UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String DSMOVIMIENTOS_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSMOVIMIENTOS_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSMOVIMIENTOS_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";

	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID = "comprobante_movimiento_articulo.comprobante_movimiento_id";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID = "comprobante_movimiento_articulo.almacen_id";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA = "comprobante_movimiento_articulo.user_id_retira";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR = "comprobante_movimiento_articulo.user_id_preparador";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO = "comprobante_movimiento_articulo.estado";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID = "comprobante_movimiento_articulo.tipo_movimiento_articulo_id";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA = "comprobante_movimiento_articulo.fecha";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES = "comprobante_movimiento_articulo.observaciones";
	public static final String DSCOMPROBANTE_COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID = "comprobante_movimiento_articulo.recepcion_compra_id";
	public static final String DSCOMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_NOMBRE = "tipo_movimiento_articulo.nombre";
	public static final String DSCOMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_POSITIVO = "tipo_movimiento_articulo.positivo";
	public static final String DSCOMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_RESERVA = "tipo_movimiento_articulo.reserva";
	public static final String DSCOMPROBANTE_WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String DSCOMPROBANTE_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSCOMPROBANTE_WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO = "website_user_preparador.nombre_completo";
	public static final String DSCOMPROBANTE_LEGAJOS_APEYNOM = "legajos.APEYNOM";

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

	// customs components
	public HtmlSubmitButton _nuevoComprobanteBUT1;
	public HtmlSubmitButton _grabarComprobanteBUT1;
	public HtmlSubmitButton _articulosAgregarBUT1;
	public HtmlSubmitButton _articulosEliminarBUT1;
	public HtmlSubmitButton _articulosCancelarBUT1;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT1;

	public com.salmonllc.html.HtmlSubmitButton _grabarAtributoBUT1;
	public com.salmonllc.html.HtmlSubmitButton _atributoGenerarAtributosBUT11;

	private String SELECCION_DETALLE_FLAG = "SELECCION_DETALLE_FLAG";

	private static final String CIRCUITO = "0010";

	private boolean recargar = false;
	private boolean seleccionarTodo = false;
 
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

		_grabarComprobanteBUT1 = new HtmlSubmitButton("grabarComprobanteBUT1",
				"Grabar comprobante", this);
		_grabarComprobanteBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarComprobanteBUT1);

		_nuevoComprobanteBUT1 = new HtmlSubmitButton("nuevoComprobanteBUT1",
				"Nuevo comprobante", this);
		_nuevoComprobanteBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevoComprobanteBUT1);

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
		_nuevoComprobanteBUT1.addSubmitListener(this);
		_grabarComprobanteBUT1.addSubmitListener(this);
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
		_dsMovimientos
				.addBucket(SELECCION_DETALLE_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsMovimientos, SELECCION_DETALLE_FLAG);
		_seleccion_detalle2.setFalseValue(null);

		// seteo la validación para los datasource
		_dsComprobante.setAutoValidate(true);
		_dsMovimientos.setAutoValidate(true);

		// seteo inicial para la visualización de componentes
		// genero un nuevo proyecto vacio.
		_dsComprobante.reset();
		_dsMovimientos.reset();

		_dsComprobante.insertRow();

		_tipo_movimiento2.setFocus();

		_detailformdisplaybox1.setReloadRowAfterSave(true);

		_tipo_movimiento2.setCriteria("tipo_movimiento_articulo_id <> "
				+ getPageProperties().getIntProperty(
						TIPO_MOVIMIENTO_RECEPCIONES));
		_tipo_movimiento2.set_reloadDropDownInEveryPageRequest(true);
		addPageListener(_tipo_movimiento2);

		setTabla_principal("comprobante_movimiento_articulo");
		set_dsAtributos(_dsAtributos);
		setContainer(_listformdisplaybox4);

		setDatosBasicosComprobante();

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

		setDatosBasicosComprobante();

		try {
			if (component == _customBUT100) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT100
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT110) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT110
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT120) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT120
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT130) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT130
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT140) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT140
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT150) {
				if (CIRCUITO != null) {
					_dsComprobante.ejecutaAccion(Integer.parseInt(_customBUT150
							.getDisplayNameLocaleKey()), CIRCUITO, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			conn.commit();
		} catch (DataStoreException ex) {
			displayErrorMessage(ex.getMessage());
			return false;
		} catch (ValidationException ex) {
			for (String er : ex.getStackErrores()) {
				displayErrorMessage(er);
			}
			return false;
		} finally {
			conn.rollback();
			conn.freeConnection();
		}

		if (component == _nuevoComprobanteBUT1) {
			// genero un nuevo comprobante vacio.
			_dsComprobante.reset();
			_dsMovimientos.reset();
			_dsAtributos.reset();
			_dsComprobante.gotoRow(_dsComprobante.insertRow());
			_tipo_movimiento2.setFocus();
		}

		if (component == _grabarComprobanteBUT1) {
			// si el comprobante esta en estado generado o esta siendo generada
			if (isModificable(_dsComprobante
					.getComprobanteMovimientoArticuloEstado())
					|| UsuarioRolesModel.isRolUsuario(userId,
							USER_ENCARGADO_ALMACEN)) {
				conn = DBConnection.getConnection("inventario");
				conn.beginTransaction();
				try {
					// grabo todos los datasource
					if (_dsComprobante.getRow() == -1)
						return false;

					_dsComprobante.update(conn);

					// actualizo los detalles
					if (_dsMovimientos.getRow() != -1) {
						_dsMovimientos.update(conn);
					}

					if (_dsAtributos.getRow() == -1) {
						if (!(_dsComprobante
								.getComprobanteMovimientoArticuloComprobanteMovimientoId() > 0)) {
							displayErrorMessage("Debe seleccionar un proyecto para poder generar sus atributos");
							return false;
						}
						// manda a generar los atributos de la entidad
						_dsAtributos.generaAtributosObjetoAplicacion(
								getRow_id(), getTabla_principal());
					} else
						_dsAtributos.update(conn);

					conn.commit();
					_dsComprobante.resetStatus();
					_dsMovimientos.resetStatus();
					_dsAtributos.resetStatus();

					// _dsMovimientos.reloadRows(conn);

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
					MessageLog.writeErrorMessage(ex, _dsComprobante);
					displayErrorMessage(ex.getMessage());
					return false;
				} finally {
					if (conn != null) {
						conn.rollback();
						conn.freeConnection();
					}
				}

			} else {
				// si el comprobante no está generado, bloqueo toda modificación
				displayErrorMessage("No puede modificar el comprobante en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}

		}

		if (component == _articulosAgregarBUT1) {
			// crea un nuevo registro de tarea
			try {
				setRow_id(_dsComprobante
						.getComprobanteMovimientoArticuloComprobanteMovimientoId());
				if (isModificable(_dsComprobante
						.getComprobanteMovimientoArticuloEstado())) {
					if (getRow_id() == 0)
						_dsComprobante.update();
					setRow_id(_dsComprobante
							.getComprobanteMovimientoArticuloComprobanteMovimientoId());
					if (_dsMovimientos.getRow() != -1)
						_dsMovimientos.update();
					// _dsMovimientos.reloadRows();
					// recupero el row actual
					int rowActual = _dsMovimientos.getRow();

					int row = _dsMovimientos.insertRow(0);					
					_dsMovimientos.gotoRow(row);
					_dsMovimientos
							.setMovimientoArticuloComprobanteMovimientoId(row,
									getRow_id());

					// si existe row anterior, sólo copia valores básicos
					int tarea = _dsMovimientos
							.getMovimientoArticuloTareaId(rowActual + 1);
					int proyecto = _dsMovimientos
							.getMovimientoArticuloProyectoId(rowActual + 1);
					if (rowActual != -1) {
						if (tarea != 0) {
							_dsMovimientos.setMovimientoArticuloTareaId(row,
									tarea);
						}
						if (proyecto != 0) {
							_dsMovimientos.setMovimientoArticuloProyectoId(row,
									proyecto);
						}
					}

					// hace foco en el registro
					int nroPagerow = _datatable2.getPage(row);
					int nroPageActual = _datatable2.getPage(_dsMovimientos
							.getRow());
					if (nroPagerow != nroPageActual)
						_datatable2.setPage(_datatable2.getPage(row));
					_articulo2.setFocus(row, true);

				} else {
					// si el comprobante no está generado, bloqueo toda
					// modificación
					displayErrorMessage("No puede agregar artículos al comprobante en el estado actual.");
					setRecargar(true);
					pageRequested(new PageEvent(this));
					return false;
				}
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsComprobante);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		if (component == _articulosEliminarBUT1) {
			if (isModificable(_dsComprobante
					.getComprobanteMovimientoArticuloEstado())) {
				// elimina todas las actividades seleccionadas
				for (int row = _dsMovimientos.getRowCount() - 1; row >= 0; row--) {
					if (_dsMovimientos.getInt(row, SELECCION_DETALLE_FLAG) == 1) {
						// Rol marcado para selección
						try {
							_dsMovimientos.deleteRow(row);
							// _dsMovimientos.update();
						} catch (Exception e) {
							displayErrorMessage("No se ha podido eliminar el artículo seleccionado. Error: "
									+ e.getMessage());
							_dsMovimientos.unDeleteRow(row);
							_dsMovimientos.setInt(row, SELECCION_DETALLE_FLAG,
									0);
							return false;
						}
					}
				}
			} else {
				// si el comprobante no está generado, bloqueo toda modificación
				displayErrorMessage("No puede modificar el comprobante en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}
		}

		if (component == _articulosCancelarBUT1) {
			// crea un nuevo registro de tarea
			try {
				_dsMovimientos.reset();
				_dsMovimientos.resetStatus();
				_dsMovimientos
						.retrieve("movimiento_articulo.comprobante_movimiento_id ="
								+ getRow_id());
				_dsMovimientos.gotoFirst();
				_dsMovimientos.update();
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, _dsComprobante);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsComprobante);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// marca - desmarca todos los partes del datasource como seleccionados
		if (component == _desSeleccionaTodoBUT1) {
			seleccionarTodo = !seleccionarTodo;
		}

		// graba atributos de entidad
		if (component == _grabarAtributoBUT1) {
			if (isModificable(_dsComprobante
					.getComprobanteMovimientoArticuloEstado())) {
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
				displayErrorMessage("No puede modificar el comprobante una vez que lo ha completado");
				return false;
			}
		}

		// genera atributos, por si faltan
		if (component == _atributoGenerarAtributosBUT11) {
			// primero determina contexto
			if (getRow_id() < 1) {
				displayErrorMessage("Debe seleccionar un comprobante para poder generar sus atributos");
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

		if (conn != null)
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
				setRow_id(getIntParameter("comprobante_movimiento_id"));
				if (isRecargar())
					setRow_id(_dsComprobante
							.getComprobanteMovimientoArticuloComprobanteMovimientoId());
				if (getRow_id() > 0) {
					// Viene seteado el proyecto. lo recupero sino no se hace
					// nada
					// resetea todos los datasource
					_dsComprobante.reset();
					_dsMovimientos.reset();
					_dsAtributos.reset();

					// recupera toda la información para el proyecto
					_dsComprobante
							.retrieve("comprobante_movimiento_articulo.comprobante_movimiento_id = "
									+ getRow_id());
					_dsComprobante.waitForRetrieve();
					_dsComprobante.gotoFirst();

					if (_dsComprobante
							.getComprobanteMovimientoArticuloTipoMovimientoArticuloId() == getPageProperties()
							.getIntProperty(TIPO_MOVIMIENTO_RECEPCIONES))
						sendRedirect("AbmRecepciones.jsp?recepcion_compra_id="
								+ _dsComprobante
										.getComprobanteMovimientoArticuloRecepcionCompraId());
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

					_dsMovimientos
							.retrieve("movimiento_articulo.comprobante_movimiento_id = "
									+ getRow_id());

					_dsMovimientos.gotoFirst();

					setDatosBasicosComprobante();
				}
			}
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

		setDatosBasicosComprobante();
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
			int row = _dsComprobante.getRow();
			int objeto_id = 0;
			int objeto_id_atributos = 0;
			if (row != -1) {

				// completa default de las columnas
				// _dsProyecto.setColumnasDefault(row);

				// recupera el id del proyecto de contexto
				objeto_id = _dsComprobante
						.getComprobanteMovimientoArticuloComprobanteMovimientoId();
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

	private void setDatosBasicosComprobante() throws DataStoreException,
			SQLException {

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		if (_dsComprobante.getRow() != -1) {
			setRow_id(_dsComprobante
					.getComprobanteMovimientoArticuloComprobanteMovimientoId());

			String titulo = "Comprobante de movimiento de artículos Nº"
					+ getRow_id();
			if (_dsComprobante.getEstadosNombre() != null)
				titulo += " (" + _dsComprobante.getEstadosNombre() + ")";
			_detailformdisplaybox1.setHeadingCaption(titulo);
			_dsComprobante.setCurrentWebsiteUserId(currentUser);

			// setea la URL del reporte a generar al presionar el botón de
			// impresión
			String URL = armarUrlReporte("PDF", _dsComprobante
					.getTipoMovimientoArticuloImpresion(),
					"&param_comprobante_movimiento_id=" + getRow_id());
			_imprimirComprobante2.setHref(URL);
			System.out.println(URL);

			if ("false".equalsIgnoreCase(getPageProperties().getProperty(
					"ShowTareaLookup")))
				_tarea3.setEnabled(false);

			boolean isModificable = isModificable(_dsComprobante
					.getComprobanteMovimientoArticuloEstado());			
			_tipo_movimiento2.setEnabled(isModificable);
			// _legajo1.setReadOnly(!isModificable);
			_almacen2.setEnabled(isModificable);
			_cantidad_solicitada2.setReadOnly(!isModificable);
			_unidad_medida2.setEnabled(isModificable);
			_descripcion4.setReadOnly(!isModificable);
			_articulo2.setReadOnly(!isModificable);
			_proyecto2.setReadOnly(!isModificable);
			_cargo2.setReadOnly(!isModificable);

			if ("0010.0003".equalsIgnoreCase(_dsComprobante
					.getComprobanteMovimientoArticuloEstado())) {
				_cantidad_entregada2.setReadOnly(true);
				_cantidad_anulada2.setReadOnly(true);
			} else {
				_cantidad_entregada2.setReadOnly(false);
				_cantidad_anulada2.setReadOnly(false);
			}

		} else
			_dsComprobante.gotoRow(_dsComprobante.insertRow());
		
		// setea el boton de seleccion/deseleccion segun corresponda
		if (_dsMovimientos.getRowCount() == 0) seleccionarTodo = true;
		
		if (seleccionarTodo)
			_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
		else
			_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.deseleccion");		
		
		_desSeleccionaTodoBUT1.setOnClick("CheckAll(" + seleccionarTodo + ");");
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
		if (_dsComprobante.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar un comprobante de movimiento de artículos para recuperar su estado");
		}

		estado = _dsComprobante.getComprobanteMovimientoArticuloEstado();

		try {
			conn = DBConnection.getConnection("infraestructura");

			// determina datos para evaluar las transiciones y acciones del
			// circuiro
			// recupero la columna para el circuito
			// Si no existe configuración no hace nada
			/*
			 * SQL = "select nombre_detalle from infraestructura.aplica_circuito
			 * where circuito = '" + CIRCUITO + "'"; st =
			 * conn.createStatement(); r = st.executeQuery(SQL);
			 */

			// en función de la columna del circuito, determino el estado actual
			// estado =
			// _dsComprobante.getString("solicitudes_compra.estado");
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
				ESTADOS_DE_MODIFICACION_COMPROBANTE_MOVIMIENTO_ARTICULO).split(
				",");
		for (int i = 0; i < estados.length; i++)
			estadosSet.add(estados[i]);
		return estadoActual == null ? true : estadosSet.contains(estadoActual);

	}
}
