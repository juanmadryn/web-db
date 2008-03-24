//package statement
package inventario.controllers;

//Salmon import statements
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import infraestructura.controllers.BaseEntityController;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidationException;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * EditarOrdenCompraController: a SOFIA generated controller
 */
public class EditarOrdenCompraController extends BaseEntityController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 69778698733022158L;
	//Visual Components
	public com.salmonllc.html.HtmlCheckBox _seleccion_detalle2;
	public com.salmonllc.html.HtmlDropDownList _nombre_completo_comprador2;
	public com.salmonllc.html.HtmlDropDownList _unidad_medida2;
	public com.salmonllc.html.HtmlText _articulo2;
	public com.salmonllc.html.HtmlText _tarea3;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observaciones2;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observacionX2;
	public com.salmonllc.html.HtmlSubmitButton _customBUT100;
	public com.salmonllc.html.HtmlSubmitButton _customBUT110;
	public com.salmonllc.html.HtmlSubmitButton _customBUT120;
	public com.salmonllc.html.HtmlSubmitButton _customBUT130;
	public com.salmonllc.html.HtmlSubmitButton _customBUT140;
	public com.salmonllc.html.HtmlSubmitButton _customBUT150;
	public com.salmonllc.html.HtmlText _articuloId1;
	public com.salmonllc.html.HtmlTextEdit _cantidad_pedida2;
	public com.salmonllc.html.HtmlText _cantidad_recibida2;
	public com.salmonllc.html.HtmlText _cantidad_solicitada1;
	public com.salmonllc.html.HtmlText _cantidadPedida1;
	public com.salmonllc.html.HtmlText _cantidadRecibida1;
	public com.salmonllc.html.HtmlText _descripcion1;
	public com.salmonllc.html.HtmlText _descripcion3;
	public com.salmonllc.html.HtmlText _solicitud_compra3;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa1;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa2;
	public com.salmonllc.html.HtmlText _fecha_estimada_entrega1;
	public com.salmonllc.html.HtmlText _fecha_estimada_entrega2;
	public com.salmonllc.html.HtmlText _fecha_ordencompra1;
	public com.salmonllc.html.HtmlText _fecha_ordencompra2;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra1;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra2;
	public com.salmonllc.html.HtmlText _monto_total1;
	public com.salmonllc.html.HtmlText _monto_total2;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _observaciones3;
	public com.salmonllc.html.HtmlText _observacionX1;
	public com.salmonllc.html.HtmlText _seleccion_detalle1;
	public com.salmonllc.html.HtmlText _tarea1;
	public com.salmonllc.html.HtmlText _tarea4;	
	public com.salmonllc.html.HtmlText _total_solicitud1;
	public com.salmonllc.html.HtmlText _total_solicitud2;
	public com.salmonllc.html.HtmlText _unidad_medida1;
	public com.salmonllc.html.HtmlText _cantidad_solicitada2;
	public com.salmonllc.html.HtmlTextEdit _descripcion2;
	public com.salmonllc.html.HtmlText _descripcion4;
	public com.salmonllc.html.HtmlText _solicitud_compra4;
	public com.salmonllc.html.HtmlTextEdit _monto_unitario1;
	public com.salmonllc.html.HtmlText _observaciones4;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;	
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	

	//DataSources
	public inventario.models.DetalleSCModel _dsDetalleSC;
	public inventario.models.OrdenesCompraModel _dsOrdenesCompra;

	//DataSource Column Constants
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_ORDEN_COMPRA_ID = "ordenes_compra.orden_compra_id";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR = "ordenes_compra.entidad_id_proveedor";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_USER_ID_COMPRADOR = "ordenes_compra.user_id_comprador";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_ESTADO = "ordenes_compra.estado";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_FECHA = "ordenes_compra.fecha";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA = "ordenes_compra.fecha_estimada_entrega";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA = "ordenes_compra.fecha_entrega_completa";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_DESCRIPCION = "ordenes_compra.descripcion";
	public static final String DSORDENESCOMPRA_ORDENES_COMPRA_OBSERVACIONES = "ordenes_compra.observaciones";
	public static final String DSORDENESCOMPRA_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSORDENESCOMPRA_NOMBRE_COMPLETO_COMPRADOR = "nombre_completo_comprador";

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
	public static final String DSDETALLESC_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSDETALLESC_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLESC_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String DSDETALLESC_DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DSDETALLESC_MONTO_TOTAL = "monto_total";
	public static final String DSDETALLESC_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSDETALLESC_CENTRO_COSTO_NOMBRE = "centro_costo.nombre";

	// custom
	public HtmlSubmitButton _nuevaOrdenCompraBUT1;
	public HtmlSubmitButton _grabarOrdenCompraBUT1;
	public HtmlSubmitButton _articulosAgregarBUT1;
	public HtmlSubmitButton _articulosEliminarBUT1;
	public HtmlSubmitButton _articulosCancelarBUT1;
	
	private String SELECCION_DETALLE_FLAG = "SELECCION_DETALLE_FLAG";

	private static final String CIRCUITO = "0008";
	
	private boolean recargar = false;
	
	/** 
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// buttons
		_nuevaOrdenCompraBUT1 = new HtmlSubmitButton("nuevaOrdenCompraBUT1", "Nueva orden", this);
		_nuevaOrdenCompraBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevaOrdenCompraBUT1);

		_grabarOrdenCompraBUT1 = new HtmlSubmitButton("grabarOrdenCompraBUT1", "Grabar", this);
		_grabarOrdenCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarOrdenCompraBUT1);
		
		_articulosAgregarBUT1 = new HtmlSubmitButton("articulosAgregarBUT1", "Agregar", this);
		_articulosAgregarBUT1.setAccessKey("A");
		_listformdisplaybox2.addButton(_articulosAgregarBUT1);

		_articulosEliminarBUT1 = new HtmlSubmitButton("articulosEliminarBUT1", "Eliminar", this);
		_articulosEliminarBUT1.setAccessKey("E");
		_listformdisplaybox2.addButton(_articulosEliminarBUT1);

		_articulosCancelarBUT1 = new HtmlSubmitButton("articulosCancelarBUT1", "Cancelar", this);
		_articulosCancelarBUT1.setAccessKey("C");
		_listformdisplaybox2.addButton(_articulosCancelarBUT1);
		
		// buttons listeners
		_nuevaOrdenCompraBUT1.addSubmitListener(this);
		_grabarOrdenCompraBUT1.addSubmitListener(this);
		_articulosAgregarBUT1.addSubmitListener(this);
		_articulosEliminarBUT1.addSubmitListener(this);
		_articulosCancelarBUT1.addSubmitListener(this);
		
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);
		
		// data selection checkbox column
		_dsDetalleSC.addBucket(SELECCION_DETALLE_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsDetalleSC, SELECCION_DETALLE_FLAG);
		_seleccion_detalle2.setFalseValue(null);

		// run datasources validations at the update event
		_dsOrdenesCompra.setAutoValidate(true);
		_dsDetalleSC.setAutoValidate(true);

		// an empty OC as default view
		_dsOrdenesCompra.reset();
		_dsDetalleSC.reset();
		_dsOrdenesCompra.insertRow();

		setTabla_principal("ordenes_compra");
	}
	
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// basic information used in event actions
		String remoteAddr = this.getCurrentRequest().getRemoteHost();
		String nombre_tabla = getTabla_principal();
		int userId = getSessionManager().getWebSiteUser().getUserID();
		DBConnection conn = DBConnection.getConnection(getApplicationName());

		boolean batchInserts = false;
		HtmlComponent component = e.getComponent();
		
		conn.beginTransaction();
		
		try {
			if (component == _customBUT100) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT100.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT110) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT110.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT120) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT120.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT130) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT130.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT140) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT140.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
					armaBotonera();

				}
			}

			if (component == _customBUT150) {
				if (CIRCUITO != null) {
					_dsOrdenesCompra.ejecutaAccion(Integer
							.parseInt(_customBUT150.getDisplayNameLocaleKey()),
							CIRCUITO, remoteAddr, userId, nombre_tabla, conn,
							batchInserts);
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
		}
		
		if (e.getComponent() == _grabarOrdenCompraBUT1) {			
			
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();			
			// si la orden de compra esta en estado generado o esta siendo revisada
			if ("0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado) || estado == null) {
				try {
					conn.beginTransaction();

					if (_dsOrdenesCompra.getRow() == -1)
						return false;

					if (_dsOrdenesCompra.getOrdenesCompraUserIdComprador() == 0)
						_dsOrdenesCompra.setOrdenesCompraUserIdComprador(
								getUserFromSession(getCurrentRequest().getRemoteAddr()).getUserID());

					_dsOrdenesCompra.update(conn);

					if (_dsDetalleSC.getRow() != -1) {
						_dsDetalleSC.update(conn);
						
						for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
							if (_dsDetalleSC.getDetalleScMontoUltimaCompra(row) == 0) {
								
								try {
									_dsDetalleSC.setDetalleScMontoUltimaCompra(
											row, Float.parseFloat(
													AtributosEntidadModel.getValorAtributoObjeto(
															"MONTO_ULTIMA_COMPRA", 
															_dsDetalleSC.getDetalleScArticuloId(row), 
															"TABLA", "articulos")));
									
									_dsDetalleSC.setDetalleScFechaUltimaCompra(
											row, AtributosEntidadModel.getValorAtributoObjeto(
													"FECHA_ULTIMA_COMPRA",	
													_dsDetalleSC.getDetalleScArticuloId(row), 
													"TABLA", "articulos"));
								} catch (NullPointerException ex) { }
							}
							
							if (_dsDetalleSC.getDetalleScMontoUnitario(row) == 0)
								_dsDetalleSC.setDetalleScMontoUnitario(
										row, _dsDetalleSC.getDetalleScMontoUltimaCompra(row));							
							_dsDetalleSC.setMontoTotal(row);
							
							if (_dsDetalleSC.getDetalleScUnidadMedida(row) == null)
								_dsDetalleSC.setDetalleScUnidadMedida(
										row, AtributosEntidadModel.getValorAtributoObjeto(
												"UNIDAD_DE_MEDIDA", _dsDetalleSC.getDetalleScArticuloId(row),
												"TABLA","articulos"));
							
							//_dsDetalleSC.update(conn);
						}
					}
					
					conn.commit();
					_dsOrdenesCompra.reloadRow();
					
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsOrdenesCompra);
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
				displayErrorMessage("No puede modificar la orden de compra en el estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}
		}
		
		if (conn != null) {
			conn.freeConnection();
		}
	
		armaBotonera();
		return super.submitPerformed(e);
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {

		try {
			// si la página es requerida por si misma o está en proceso de
			// recargar un proyecto no hago nada
			if (!isReferredByCurrentPage() || isRecargar()) {
				// verifico si tiene parámetro
				setRow_id(getIntParameter("orden_compra_id"));
				if (isRecargar())
					setRow_id(_dsOrdenesCompra
							.getOrdenesCompraOrdenCompraId());
				if (getRow_id() > 0) {
					// resetea todos los datasource
					_dsOrdenesCompra.reset();
					_dsDetalleSC.reset();

					// recupera toda la información para la orden de compra
					_dsOrdenesCompra
							.retrieve("ordenes_compra.orden_compra_id = "
									+ Integer.toString(getRow_id()));
					_dsOrdenesCompra.gotoFirst();

					// sigue recuperando información del resto de los detalles
					// (actividades y tareas)
					_dsDetalleSC.retrieve("detalle_sc.orden_compra_id = "
							+ Integer.toString(getRow_id()));
					if (_dsDetalleSC.gotoFirst()) {
						for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
							_dsDetalleSC.setMontoTotal(i);
						}
					}
					setDatosBasicosOrdenCompra();
					//setTareaLookupURL();
				}
			}
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}
		setDatosBasicosOrdenCompra();
		armaBotonera();
		super.pageRequested(p);	
	}
	
	private void setDatosBasicosOrdenCompra() throws DataStoreException,
			SQLException {
		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		setRow_id(_dsOrdenesCompra.getOrdenesCompraOrdenCompraId());
		_detailformdisplaybox1.setHeadingCaption("Orden de compra Nº"
				+ getRow_id());
		_dsOrdenesCompra.setCurrentWebsiteUserId(currentUser);
		_dsOrdenesCompra.setEsquemaConfiguracionId(Integer
				.parseInt(getPageProperties().getThemeProperty(null,
						"EsquemaConfiguracionIdOrdenesCompra")));
		_dsOrdenesCompra.setTotalOrdenCompra(_dsOrdenesCompra
				.getAtributoTotalOrdenCompra());

		String estado = _dsOrdenesCompra.getOrdenesCompraEstado();
		/*if (!"0008.0002".equalsIgnoreCase(estado))
			_dsOrdenesCompra.setObservaciones();*/

		if ("0008.0002".equalsIgnoreCase(estado)
				|| "0008.0004".equalsIgnoreCase(estado)
				|| "0008.0005".equalsIgnoreCase(estado)) {
			_observacionX1.setVisible(true);
			_observacionX2.setVisible(true);
		} else {
			_observacionX1.setVisible(false);
			_observacionX2.setVisible(false);
		}

		if ((new UsuarioRolesModel("infraestructura", "infraestructura"))
				.isRolUsuario(currentUser, "COMPRADOR"))
			_nombre_completo_comprador2.setEnabled(true);
		else {
			_dsOrdenesCompra.setOrdenesCompraUserIdComprador(currentUser);
			_nombre_completo_comprador2.setEnabled(false);
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

		// hide all buttons
		_customBUT100.setVisible(false);
		_customBUT110.setVisible(false);
		_customBUT120.setVisible(false);
		_customBUT130.setVisible(false);
		_customBUT140.setVisible(false);
		_customBUT150.setVisible(false);
		
		if (_dsOrdenesCompra.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar una orden de compra para recuperar su estado");
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
			estado = _dsOrdenesCompra.getString("ordenes_compra.estado");

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

	// encapsulate regarcar field 
	public boolean isRecargar() {
		return recargar;
	}
	
	public void setRecargar(boolean recargar) {
		this.recargar = recargar;
	}
}
