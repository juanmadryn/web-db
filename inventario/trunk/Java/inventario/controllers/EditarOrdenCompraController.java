//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.util.SolicitudCompraTransiciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * EditarOrdenCompraController: a SOFIA generated controller
 */
public class EditarOrdenCompraController extends BaseEntityController implements ValueChangedListener {

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
	public com.salmonllc.html.HtmlText _descComplArticulo3;
	public com.salmonllc.html.HtmlText _descComplArticulo4;
	public com.salmonllc.html.HtmlText _solicitudCompra3;
	public com.salmonllc.html.HtmlText _solicitudCompra4;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa1;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa2;
	public com.salmonllc.html.HtmlText _fecha_estimada_entrega1;
	public com.salmonllc.html.HtmlTextEdit _fecha_estimada_entrega2;
	public com.salmonllc.html.HtmlText _fecha_ordencompra1;
	public com.salmonllc.html.HtmlText _fecha_ordencompra2;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra1;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra2;
	public com.salmonllc.html.HtmlText _monto_total1;
	public com.salmonllc.html.HtmlText _monto_total2;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;	
	public com.salmonllc.html.HtmlText _observaciones3;
	public com.salmonllc.html.HtmlText _observacionX1;
	public com.salmonllc.html.HtmlText _seleccion_detalle1;
	public com.salmonllc.html.HtmlText _tarea1;
	public com.salmonllc.html.HtmlText _proyecto1;
	public com.salmonllc.html.HtmlText _proyecto2;
	public com.salmonllc.html.HtmlText _centroCosto1;
	public com.salmonllc.html.HtmlText _tarea4;	
	public com.salmonllc.html.HtmlText _neto_orden1;
	public com.salmonllc.html.HtmlText _neto_orden2;
	public com.salmonllc.html.HtmlText _cantidad_solicitada2;
	public com.salmonllc.html.HtmlTextEdit _descripcion2;
	public com.salmonllc.html.HtmlText _descripcion4;
	public com.salmonllc.html.HtmlText _solicitud_compra4;
	public com.salmonllc.html.HtmlTextEdit _monto_unitario1;
	public com.salmonllc.html.HtmlText _centroCosto2;
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
	//public HtmlSubmitButton _articulosNuevoBUT1;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT1;
	public com.salmonllc.jsp.JspLink _imprimirOrdenCompraBUT1;
	public com.salmonllc.jsp.JspLink _imprimirOrdenCompraBUT2;
	public com.salmonllc.jsp.JspLink _lnksolicitud1;
	public com.salmonllc.jsp.JspLink _verFirmantes;
	public com.salmonllc.jsp.JspTableCell _tareaTableTd;
	public com.salmonllc.jsp.JspTableCell _proyectoTableTd;
	public com.salmonllc.jsp.JspTableCell _tareaHeaderTd;
	public com.salmonllc.jsp.JspTableCell _proyectoHeaderTd;
	public com.salmonllc.jsp.JspLink _verSolicitantes;
	public com.salmonllc.jsp.JspTableRow _descAdicionalTr;	
	public HtmlSubmitButton _muestraDescAdicionalBUT;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	//public com.salmonllc.jsp.JspTableRow _nuevoDetalleSinSc;
	public com.salmonllc.html.HtmlTextEdit _descuento2;
	
	private static final String SELECCION_DETALLE_SC_FLAG = "SELECCION_DETALLE_FLAG";
	private static final String REMOVER_DE_OC = "REMOVER_DE_OC";
	private static final String DESCRIPCION_ADICIONAL = "DESCRIPCION_ADICIONAL";

	private static final String CIRCUITO = "0008";
	
	private boolean recargar = false;
	
	/** 
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// buttons
		_grabarOrdenCompraBUT1 = new HtmlSubmitButton("grabarOrdenCompraBUT1", "Grabar OC", this);
		_grabarOrdenCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarOrdenCompraBUT1);

		_nuevaOrdenCompraBUT1 = new HtmlSubmitButton("nuevaOrdenCompraBUT1", "Nueva OC", this);
		_nuevaOrdenCompraBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevaOrdenCompraBUT1);
		
		/*_articulosNuevoBUT1 = new HtmlSubmitButton("articulosNuevoBUT1", "Nuevo", this);
		_articulosNuevoBUT1.setAccessKey("V");
		_listformdisplaybox2.addButton(_articulosNuevoBUT1);*/
		
		_articulosAgregarBUT1 = new HtmlSubmitButton("articulosAgregarBUT1", "Agregar", this);
		_articulosAgregarBUT1.setAccessKey("A");
		_listformdisplaybox2.addButton(_articulosAgregarBUT1);

		_articulosEliminarBUT1 = new HtmlSubmitButton("articulosEliminarBUT1", "Eliminar", this);
		_articulosEliminarBUT1.setAccessKey("E");
		_listformdisplaybox2.addButton(_articulosEliminarBUT1);

		_articulosCancelarBUT1 = new HtmlSubmitButton("articulosCancelarBUT1", "Cancelar", this);
		_articulosCancelarBUT1.setAccessKey("C");
		_listformdisplaybox2.addButton(_articulosCancelarBUT1);
		
		_muestraDescAdicionalBUT = new HtmlSubmitButton("muestraDescAdicionalBUT", null, this);
		_muestraDescAdicionalBUT.setAccessKey("D");
		_muestraDescAdicionalBUT.setDisplayName("Desc. Adicional");
		_listformdisplaybox2.addButton(_muestraDescAdicionalBUT);
		
		_desSeleccionaTodoBUT1 = new HtmlSubmitButton("desSeleccionaTodoBUT2",null,this);
		_desSeleccionaTodoBUT1.setAccessKey("E");
		_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
		_listformdisplaybox2.addButton(_desSeleccionaTodoBUT1);
		
		// buttons listeners
		_nuevaOrdenCompraBUT1.addSubmitListener(this);
		_grabarOrdenCompraBUT1.addSubmitListener(this);
		_articulosAgregarBUT1.addSubmitListener(this);
		_articulosEliminarBUT1.addSubmitListener(this);
		_articulosCancelarBUT1.addSubmitListener(this);
		_desSeleccionaTodoBUT1.addSubmitListener(this);
		_muestraDescAdicionalBUT.addSubmitListener(this);
		//_articulosNuevoBUT1.addSubmitListener(this);
		
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);
		
		// data selection checkbox column
		_dsDetalleSC.addBucket(SELECCION_DETALLE_SC_FLAG, DataStore.DATATYPE_INT);
		_seleccion_detalle2.setColumn(_dsDetalleSC, SELECCION_DETALLE_SC_FLAG);
		_seleccion_detalle2.setFalseValue(null);
		
		// bucket para eliminar linea de OC
		_dsDetalleSC.addBucket(REMOVER_DE_OC, DataStore.DATATYPE_INT);	
		
		// bucket para descripcion adicional
		_dsDetalleSC.addBucket(DESCRIPCION_ADICIONAL, DataStore.DATATYPE_INT);
		
		// oculta/muestra información de tarea del proyecto		
		if("false".equalsIgnoreCase(Props.getProps("inventario",null).getProperty("VerTareaEnDetalleOc"))) {
			_tareaHeaderTd.setEnabled(false);
			_tareaHeaderTd.setVisible(false);
			_tareaTableTd.setEnabled(false);
			_tareaTableTd.setVisible(false);
			_proyectoHeaderTd.setColSpan(3);
			_proyectoTableTd.setColSpan(3);
		}
		
		// run datasources validations at the update event
		_dsOrdenesCompra.setAutoValidate(true);
		_dsDetalleSC.setAutoValidate(true);

		// an empty OC as default view
		_dsOrdenesCompra.reset();
		_dsDetalleSC.reset();
		_dsOrdenesCompra.insertRow();
		
		_detailformdisplaybox1.setReloadRowAfterSave(true);
		
		setTabla_principal("ordenes_compra");
		
		setDatosBasicosOrdenCompra();
		armaBotonera();
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
			//setRecargar(true);
			setDatosBasicosOrdenCompra();
			conn.rollback();
		}
		
		if (e.getComponent() == _grabarOrdenCompraBUT1) {			
			
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();			
			// si la orden de compra esta en estado generado o esta siendo revisada
			if (estado == null || "0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado)) {				
				try {
					conn.beginTransaction();
				
					if (_dsOrdenesCompra.getRow() == -1)
						return false;

					_dsOrdenesCompra.update(conn);

					if (_dsDetalleSC.getRow() != -1) {
						// remueve detalles marcados para eliminar
						boolean detalleEliminado = _dsDetalleSC.eliminaDetallesSeleccionados(conn, REMOVER_DE_OC);						
												
						_dsDetalleSC.update(conn);
						
						// update the SC states
						if (detalleEliminado) {							
							SolicitudCompraTransiciones.remueveDeOc(conn,
									_dsDetalleSC, getCurrentRequest().getRemoteHost(),
									getSessionManager().getWebSiteUser().getUserID());
						}
					}
					
					_dsOrdenesCompra.resetStatus();
					_dsDetalleSC.resetStatus();
					
					conn.commit();
					
					_dsOrdenesCompra.reloadRow();
					setRecargar(true);
					
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
		
		// marca - desmarca todos los partes del datasource como seleccionados
		if (e.getComponent() == _desSeleccionaTodoBUT1) {
			if ("text.seleccion".equalsIgnoreCase(_desSeleccionaTodoBUT1.getDisplayNameLocaleKey()) ) {
				for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
					_dsDetalleSC.setInt(i, SELECCION_DETALLE_SC_FLAG,1);
				}
				_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.deseleccion");
			} else {
				for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
					_dsDetalleSC.setInt(i, SELECCION_DETALLE_SC_FLAG,0);
				}
				_desSeleccionaTodoBUT1.setDisplayNameLocaleKey("text.seleccion");
			}
		}
		
		// marca para eliminacion las linea de la orden de compra seleccionadas
		if (component == _articulosEliminarBUT1) {
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();
			if ("0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado) || estado == null) {
				for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
					if (_dsDetalleSC.getInt(row, SELECCION_DETALLE_SC_FLAG) == 1) {
						_dsDetalleSC.setInt(row, SELECCION_DETALLE_SC_FLAG, 0);
						_dsDetalleSC.setInt(row, REMOVER_DE_OC, 1);
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
		
		// genero un nuevo orden de compra vacia
		if (component == _nuevaOrdenCompraBUT1) {
			_dsOrdenesCompra.reset();
			_dsDetalleSC.reset();
			_dsOrdenesCompra.gotoRow(_dsOrdenesCompra.insertRow());
		}
		
		// cancela la eliminacion de los detalles marcados para tal efecto
		if (component == _articulosCancelarBUT1) {
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();			
			if ("0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado) || estado == null) {
				_dsDetalleSC.filter(null);
				for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
					if (_dsDetalleSC.getInt(row, REMOVER_DE_OC) == 1) {
						_dsDetalleSC.setInt(row, REMOVER_DE_OC, 0);
					}					
				}
			}			
		}
		
		// Free the connection
		if (conn != null) {
			conn.rollback();
			conn.freeConnection();
		}
		
		// Muestra/Oculta campo de texto para descripcion adicional
		if (component == _muestraDescAdicionalBUT) {
			for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {
				if (_dsDetalleSC.getInt(row, SELECCION_DETALLE_SC_FLAG) == 1) {
					_dsDetalleSC.setInt(row, DESCRIPCION_ADICIONAL, 1);
					_dsDetalleSC.setInt(row, SELECCION_DETALLE_SC_FLAG, 0);
				}					
			}
		}
		
		// Agrega un nuevo detalle a la OC
		/*if (component == _articulosNuevoBUT1) {
			setRow_id(_dsOrdenesCompra.getOrdenesCompraOrdenCompraId());
			
			// Si la OC se encuentra en un estado editable
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();
			if ("0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado) || estado == null) {
				
				// Si la OC no esta grabada, la grabamos (?)
				if (getRow_id() == 0) {
					_dsOrdenesCompra.update();
					setRow_id(_dsOrdenesCompra.getOrdenesCompraOrdenCompraId());
				}
				
				// Grabamos los cambios hechos a los detalles, si los hubiera
				if (_dsDetalleSC.getRow() != -1)
					_dsDetalleSC.update();
				
				// Inserto un nuevo detalle
				int row = _dsDetalleSC.insertRow(0);
				_dsDetalleSC.setDetalleScOrdenCompraId(row, getRow_id());
				
				// hace foco en el registro agregado
				int nroPagerow = _datatable2.getPage(row);
				int nroPageActual = _datatable2.getPage(_dsDetalleSC.getRow());
				if (nroPagerow != nroPageActual)
					_datatable2.setPage(_datatable2.getPage(row));				
			} else {
				// No se puede modificar la OC
				displayErrorMessage("No puede agregar artículos a la OC en su estado actual.");
				setRecargar(true);
				pageRequested(new PageEvent(this));
				return false;
			}
			
		}*/
		
		// Redirecciona a la pantalla de Generacion de OCs
		if (component == _articulosAgregarBUT1) {
			// Si la OC se encuentra en un estado editable
			String estado = _dsOrdenesCompra.getOrdenesCompraEstado();
			if ("0008.0001".equalsIgnoreCase(estado)
					|| "0008.0005".equalsIgnoreCase(estado) || estado == null) {
				setRecargar(false);
				this.gotoSiteMapPage("GenerarOrdenesCompra","?orden_compra_id=" + getRow_id());
			} else {
				// si la solicitud no está generada, bloqueo toda modificación
				displayErrorMessage("No puede agregar artículos a la OC en su estado actual.");				
				pageRequested(new PageEvent(this));
				return false;
			}
		}
	
		armaBotonera();
		return super.submitPerformed(e);
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {

		try {
			if (!isReferredByCurrentPage() || isRecargar()) {
				
				setRow_id(isRecargar() ? _dsOrdenesCompra.getOrdenesCompraOrdenCompraId()
						: getIntParameter("orden_compra_id"));
				
				if (getRow_id() > 0) {
					// resetea todos los datasource
					_dsOrdenesCompra.reset();
					_dsDetalleSC.reset();

					// recupera cabecera de la OC
					_dsOrdenesCompra.retrieve("ordenes_compra.orden_compra_id = " + getRow_id());
					_dsOrdenesCompra.gotoFirst();
					
					// recupera detalles de la OC
					_dsDetalleSC.retrieve("detalle_sc.orden_compra_id = " + getRow_id());
					if (_dsDetalleSC.gotoFirst()) {
						for (int row = 0; row < _dsDetalleSC.getRowCount(); row++) {							
							if (_dsDetalleSC.getAny(row, DetalleSCModel.DETALLE_SC_IVA) == null) {
								_dsDetalleSC.setDetalleScIva(row,
										Float.parseFloat(AtributosEntidadModel.getValorAtributoObjeto(Constants.ARTICULO_IVA_PORCENTAJE,
												_dsDetalleSC.getDetalleScArticuloId(row), "TABLA", "articulos")));
								_dsDetalleSC.update();
							}			
							// calcula totales de cada detalle
							_dsDetalleSC.calculaMontoTotalPedido(row);
							_dsDetalleSC.calculaMontoTotalNetoPedido(row);
							
							// calcula totales de la OC
							_dsOrdenesCompra.calculaAtributoNetoOrdenCompra();
							_dsOrdenesCompra.calculaAtributoDescuentoOrdenCompra();
							_dsOrdenesCompra.calculaAtributoIvaOrdenCompra();
							_dsOrdenesCompra.calculaAtributoTotalOrdenCompra();
						}
					}
				}
			}
			
			// filtramos detalles marcados para eliminar
			_dsDetalleSC.filter(REMOVER_DE_OC + " != 1");
			
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}
		
		setDatosBasicosOrdenCompra();
		armaBotonera();
		super.pageRequested(p);	
	}
	
	/**
	 * Recupera datos adicionales de la orden de compra que no se encuentran en 
	 * la tabla inventario.ordenes_compra y configura adecuadamente la vista
	 * @throws Exception 
	 * @throws Exception 
	 */
	private void setDatosBasicosOrdenCompra() throws Exception {
		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		setRow_id(_dsOrdenesCompra.getOrdenesCompraOrdenCompraId());
		
		// Nùmero de OC y estado en el tìtulo
		String titulo = "Orden de compra Nº" + getRow_id();
		if (_dsOrdenesCompra.getEstadoNombre() != null)
			titulo += " (" + _dsOrdenesCompra.getEstadoNombre() + ")";		
		_detailformdisplaybox1.setHeadingCaption(titulo);
		
		_dsOrdenesCompra.setCurrentWebsiteUserId(currentUser);
		
		// Recupera esquema de configuración para la cadena de firmas
		_dsOrdenesCompra.setEsquemaConfiguracionId(Integer
				.parseInt(getPageProperties().getProperty(
						"EsquemaConfiguracionIdOrdenesCompra")));
		
		// Deshabilita descuento en detalles si es seteado uno en la cabecera
		_descuento2.setEnabled(_dsOrdenesCompra.getOrdenesCompraDescuento() > 0 ? false : true);
		if (_dsOrdenesCompra.getOrdenesCompraDescuento() > 0) {
			_descuento2.setEnabled(false);
			_monto_total2.setExpression(_dsDetalleSC, DetalleSCModel.DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO);			
		} else {
			_descuento2.setEnabled(true);
			_monto_total2.setExpression(_dsDetalleSC, DetalleSCModel.DETALLE_SC_MONTO_TOTAL);
		}
		_monto_total2.setDisplayFormatLocaleKey("CurrencyFormatConSigno");
				
		// Neto
		_dsOrdenesCompra.setNetoOrdenCompra(_dsOrdenesCompra.getAtributoNetoOrdenCompra());
		// Descuento
		_dsOrdenesCompra.setDescuentoOrdenCompra(_dsOrdenesCompra.getAtributoDescuentoOrdenCompra());
		// IVA
		_dsOrdenesCompra.setIvaOrdenCompra(_dsOrdenesCompra.getAtributoIvaOrdenCompra());
		// Total OC
		_dsOrdenesCompra.setTotalOrdenCompra(_dsOrdenesCompra.getAtributoTotalOrdenCompra());
		
		// Muestra observaciones realizadas a la OC para el estado adecuado
		String estado = _dsOrdenesCompra.getOrdenesCompraEstado();
		if ("0008.0002".equalsIgnoreCase(estado)
				|| "0008.0004".equalsIgnoreCase(estado)
				|| "0008.0005".equalsIgnoreCase(estado)) {
			_dsOrdenesCompra.recuperaObservaciones();
			_observacionX1.setVisible(true);
			_observacionX2.setVisible(true);			
		} else {
			_observacionX1.setVisible(false);
			_observacionX2.setVisible(false);
		}

		// setea comprador 
		int comprador = _dsOrdenesCompra.getOrdenesCompraUserIdComprador();
		if (comprador == 0)
			_dsOrdenesCompra.setOrdenesCompraUserIdComprador(currentUser);		
		
		// setea la URL del reporte a generar al presionar el botón de impresión
		String URL = armarUrlReporte("XLS", "orden_compra",
				"&orden_compra_id_parameter=" + getRow_id());
		_imprimirOrdenCompraBUT1.setHref(URL);

		URL = armarUrlReporte("PDF", "orden_compra",
				"&orden_compra_id_parameter=" + getRow_id());
		_imprimirOrdenCompraBUT2.setHref(URL);
		
		// setea la URL de lista de firmantes y transiciones de estado
		_verFirmantes.setHref("ListaFirmantes.jsp?orden_id=" + getRow_id());
		
		// setea la URL de lista de solicitantes
		_verSolicitantes.setHref("ListaSolicitantes.jsp?orden_id=" + getRow_id());
				
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
		
		_desSeleccionaTodoBUT1.setVisible(_dsDetalleSC.getRowCount() == 0 ? false: true);
		
		if ("0008.0001".equalsIgnoreCase(estado)
				|| "0008.0005".equalsIgnoreCase(estado) || estado == null)
			_muestraDescAdicionalBUT.setVisible(_dsDetalleSC.getRowCount() == 0 ? false: true);
		else
			_muestraDescAdicionalBUT.setVisible(false);

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

	public boolean valueChanged(ValueChangedEvent e) throws Exception {		

		return false;
	}
	
}
