//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseEntityController;
import infraestructura.models.UsuarioRolesModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.jsp.JspLink;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * EditarCotizacionCompraController: a SOFIA generated controller
 */
public class EditarCotizacionCompraController extends BaseEntityController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942161845038128245L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _forma_Pago_proveedor1;
	public com.salmonllc.html.HtmlDropDownList _forma_Pago_proveedor2;
	public com.salmonllc.html.HtmlDropDownList _forma_Pago_proveedor3;
	public com.salmonllc.html.HtmlDropDownList _nombre_completo_comprador2;
	public com.salmonllc.html.HtmlImage _imprimirTXT1;
	public com.salmonllc.html.HtmlImage _imprimirTXT2;
	public com.salmonllc.html.HtmlLookUpComponent _entidad_id_proveedor1;
	public com.salmonllc.html.HtmlLookUpComponent _entidad_id_proveedor2;
	public com.salmonllc.html.HtmlLookUpComponent _entidad_id_proveedor3;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observaciones2;
	public com.salmonllc.html.HtmlSubmitButton _customBUT100;
	public com.salmonllc.html.HtmlSubmitButton _customBUT110;
	public com.salmonllc.html.HtmlSubmitButton _customBUT120;
	public com.salmonllc.html.HtmlSubmitButton _customBUT130;
	public com.salmonllc.html.HtmlSubmitButton _customBUT140;
	public com.salmonllc.html.HtmlSubmitButton _customBUT150;
	public com.salmonllc.html.HtmlText _articulo2;
	public com.salmonllc.html.HtmlText _articuloId1;
	public com.salmonllc.html.HtmlText _bonificacionHeading1;
	public com.salmonllc.html.HtmlText _cantidad_solicitada1;
	public com.salmonllc.html.HtmlText _cantidad_solicitada2;
	public com.salmonllc.html.HtmlText _descComplArticulo3;
	public com.salmonllc.html.HtmlText _descComplArticulo4;
	public com.salmonllc.html.HtmlText _descripcion3;
	public com.salmonllc.html.HtmlText _descripcion4;
	public com.salmonllc.html.HtmlText _descripcionSc;
	public com.salmonllc.html.HtmlText _forma_pagoHeading1;
	public com.salmonllc.html.HtmlText _marca_proveedor1;
	public com.salmonllc.html.HtmlText _marca_proveedor2;
	public com.salmonllc.html.HtmlText _marca_proveedor3;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra1;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra2;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _plazo_entregaHeading1;
	public com.salmonllc.html.HtmlText _precio_unitario_proveedor1;
	public com.salmonllc.html.HtmlText _precio_unitario_proveedor2;
	public com.salmonllc.html.HtmlText _precio_unitario_proveedor3;
	public com.salmonllc.html.HtmlText _proveedorHeading1;
	public com.salmonllc.html.HtmlText _seleccion_proveedor1;
	public com.salmonllc.html.HtmlText _seleccion_proveedor2;
	public com.salmonllc.html.HtmlText _seleccion_proveedor3;
	public com.salmonllc.html.HtmlText _signo_pesos1;
	public com.salmonllc.html.HtmlText _solicitudCompra3;
	public com.salmonllc.html.HtmlText _solicitudCompra4;
	public com.salmonllc.html.HtmlText _total_proveedor1;
	public com.salmonllc.html.HtmlText _total_proveedor2;
	public com.salmonllc.html.HtmlText _total_proveedor3;
	public com.salmonllc.html.HtmlText _totalHeading1;
	public com.salmonllc.html.HtmlText _unidad_medidida1;
	public com.salmonllc.html.HtmlText _unidadMedida;
	public com.salmonllc.html.HtmlTextEdit _bonificacion_proveedor1;
	public com.salmonllc.html.HtmlTextEdit _bonificacion_proveedor2;
	public com.salmonllc.html.HtmlTextEdit _bonificacion_proveedor3;
	public com.salmonllc.html.HtmlTextEdit _monto_unitario1;
	public com.salmonllc.html.HtmlTextEdit _plazo_entrega_proveedor1;
	public com.salmonllc.html.HtmlTextEdit _plazo_entrega_proveedor2;
	public com.salmonllc.html.HtmlTextEdit _plazo_entrega_proveedor3;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspLink _imprimirCotizacionCompraBUT1;
	public com.salmonllc.jsp.JspLink _imprimirCotizacionCompraBUT2;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspTable _table1;

	// DataSources
	public inventario.models.CotizacionesCompraModel _dsCotizacionesCompra;
	public inventario.models.DetalleCotizacionModel _dsDetalleCotizacion;

	// DataSource Column Constants
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_DETALLE_SC_ID = "detalle_cotizacion.detalle_SC_id";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_COMPRA_ID = "detalle_cotizacion.cotizacion_compra_id";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1 = "detalle_cotizacion.monto_unitario_proveedor1";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MARCA_PROVEEDOR1 = "detalle_cotizacion.marca_proveedor1";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1 = "detalle_cotizacion.cotizacion_seleccionada_proveedor1";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2 = "detalle_cotizacion.monto_unitario_proveedor2";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MARCA_PROVEEDOR2 = "detalle_cotizacion.marca_proveedor2";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2 = "detalle_cotizacion.cotizacion_seleccionada_proveedor2";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3 = "detalle_cotizacion.monto_unitario_proveedor3";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MARCA_PROVEEDOR3 = "detalle_cotizacion.marca_proveedor3";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3 = "detalle_cotizacion.cotizacion_seleccionada_proveedor3";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4 = "detalle_cotizacion.monto_unitario_proveedor4";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MARCA_PROVEEDOR4 = "detalle_cotizacion.marca_proveedor4";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4 = "detalle_cotizacion.cotizacion_seleccionada_proveedor4";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5 = "detalle_cotizacion.monto_unitario_proveedor5";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_MARCA_PROVEEDOR5 = "detalle_cotizacion.marca_proveedor5";
	public static final String DSDETALLECOTIZACION_DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5 = "detalle_cotizacion.cotizacion_seleccionada_proveedor5";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DSDETALLECOTIZACION_DETALLE_SC_UNIDAD_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String DSDETALLECOTIZACION_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSDETALLECOTIZACION_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSDETALLECOTIZACION_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSDETALLECOTIZACION_UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";

	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID = "cotizaciones_compra.cotizacion_compra_id";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ESTADO = "cotizaciones_compra.estado";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1 = "cotizaciones_compra.entidad_id_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_USER_ID_COMPRADOR = "cotizaciones_compra.user_id_comprador";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1 = "cotizaciones_compra.condicion_compra_id_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1 = "cotizaciones_compra.forma_pago_id_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1 = "cotizaciones_compra.plazo_entrega_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1 = "cotizaciones_compra.bonificacion_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1 = "cotizaciones_compra.total_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1 = "cotizaciones_compra.observaciones_proveedor1";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2 = "cotizaciones_compra.entidad_id_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2 = "cotizaciones_compra.condicion_compra_id_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2 = "cotizaciones_compra.forma_pago_id_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2 = "cotizaciones_compra.plazo_entrega_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2 = "cotizaciones_compra.bonificacion_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2 = "cotizaciones_compra.total_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2 = "cotizaciones_compra.observaciones_proveedor2";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3 = "cotizaciones_compra.entidad_id_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3 = "cotizaciones_compra.condicion_compra_id_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3 = "cotizaciones_compra.forma_pago_id_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3 = "cotizaciones_compra.plazo_entrega_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3 = "cotizaciones_compra.bonificacion_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3 = "cotizaciones_compra.total_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3 = "cotizaciones_compra.observaciones_proveedor3";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4 = "cotizaciones_compra.entidad_id_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4 = "cotizaciones_compra.condicion_compra_id_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4 = "cotizaciones_compra.forma_pago_id_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4 = "cotizaciones_compra.plazo_entrega_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4 = "cotizaciones_compra.bonificacion_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4 = "cotizaciones_compra.total_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4 = "cotizaciones_compra.observaciones_proveedor4";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5 = "cotizaciones_compra.entidad_id_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5 = "cotizaciones_compra.condicion_compra_id_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5 = "cotizaciones_compra.forma_pago_id_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5 = "cotizaciones_compra.plazo_entrega_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5 = "cotizaciones_compra.bonificacion_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5 = "cotizaciones_compra.total_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5 = "cotizaciones_compra.observaciones_proveedor5";
	public static final String DSCOTIZACIONESCOMPRA_COTIZACIONES_COMPRA_OBSERVACIONES = "cotizaciones_compra.observaciones";
	public static final String DSCOTIZACIONESCOMPRA_CONDICIONES_COMPRA_PROVEEDOR1_NOMBRE = "condiciones_compra_proveedor1.nombre";
	public static final String DSCOTIZACIONESCOMPRA_CONDICIONES_COMPRA_PROVEEDOR2_NOMBRE = "condiciones_compra_proveedor2.nombre";
	public static final String DSCOTIZACIONESCOMPRA_CONDICIONES_COMPRA_PROVEEDOR3_NOMBRE = "condiciones_compra_proveedor3.nombre";
	public static final String DSCOTIZACIONESCOMPRA_CONDICIONES_COMPRA_PROVEEDOR4_NOMBRE = "condiciones_compra_proveedor4.nombre";
	public static final String DSCOTIZACIONESCOMPRA_CONDICIONES_COMPRA_PROVEEDOR5_NOMBRE = "condiciones_compra_proveedor5.nombre";
	public static final String DSCOTIZACIONESCOMPRA_FORMAS_PAGO_PROVEEDOR1_NOMBRE = "formas_pago_proveedor1.nombre";
	public static final String DSCOTIZACIONESCOMPRA_FORMAS_PAGO_PROVEEDOR2_NOMBRE = "formas_pago_proveedor2.nombre";
	public static final String DSCOTIZACIONESCOMPRA_FORMAS_PAGO_PROVEEDOR3_NOMBRE = "formas_pago_proveedor3.nombre";
	public static final String DSCOTIZACIONESCOMPRA_FORMAS_PAGO_PROVEEDOR4_NOMBRE = "formas_pago_proveedor4.nombre";
	public static final String DSCOTIZACIONESCOMPRA_FORMAS_PAGO_PROVEEDOR5_NOMBRE = "formas_pago_proveedor5.nombre";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR1_CODIGO = "entidad_externa_proveedor1.codigo";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR1_NOMBRE = "entidad_externa_proveedor1.nombre";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR2_CODIGO = "entidad_externa_proveedor2.codigo";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR2_NOMBRE = "entidad_externa_proveedor2.nombre";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR3_CODIGO = "entidad_externa_proveedor3.codigo";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR3_NOMBRE = "entidad_externa_proveedor3.nombre";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR4_CODIGO = "entidad_externa_proveedor4.codigo";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR4_NOMBRE = "entidad_externa_proveedor4.nombre";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR5_CODIGO = "entidad_externa_proveedor5.codigo";
	public static final String DSCOTIZACIONESCOMPRA_ENTIDAD_EXTERNA_PROVEEDOR5_NOMBRE = "entidad_externa_proveedor5.nombre";
	public static final String DSCOTIZACIONESCOMPRA_WEBSITE_USER_USER_ID = "website_user.user_id";

	// custom componenets
	public HtmlSubmitButton _grabarCotizacionCompraBUT1;
	public HtmlSubmitButton _generarOCBUT1;
	public HtmlSubmitButton _cotizacionOptimaBUT;
	public HtmlSubmitButton _limpiaSeleccionBUT;
	public JspLink _lnkordencompra1;
	
	private static final String CIRCUITO = "0006";
	
	// especifica si hay que recargar la pagina
	private boolean recargar = false;

	@Override
	public void initialize() throws Exception {

		super.initialize();

		_grabarCotizacionCompraBUT1 = new HtmlSubmitButton("grabarCotizacionCompraBUT1", "Grabar", this);
		_grabarCotizacionCompraBUT1.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarCotizacionCompraBUT1);

		_cotizacionOptimaBUT = new HtmlSubmitButton("cotizacionOptimaBUT", "Cotización Óptima",this);
		_cotizacionOptimaBUT.setAccessKey("C");
		_detailformdisplaybox1.addButton(_cotizacionOptimaBUT);
		
		_limpiaSeleccionBUT = new HtmlSubmitButton("limpiaSeleccionBUT", "limpia Selección",this);
		_limpiaSeleccionBUT.setAccessKey("X");
		_detailformdisplaybox1.addButton(_limpiaSeleccionBUT);

		_generarOCBUT1 = new HtmlSubmitButton("generarOCBUT1", "generar OC",this);
		_generarOCBUT1.setAccessKey("O");
		_detailformdisplaybox1.addButton(_generarOCBUT1);

		// agrega los listener a los botones
		_grabarCotizacionCompraBUT1.addSubmitListener(this);
		_cotizacionOptimaBUT.addSubmitListener(this);
		_limpiaSeleccionBUT.addSubmitListener(this);
		_generarOCBUT1.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT150.addSubmitListener(this);

		// seteo la validación para los datasource
		_dsCotizacionesCompra.setAutoValidate(true);
		_dsDetalleCotizacion.setAutoValidate(true);

	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		if (e.getComponent() == _grabarCotizacionCompraBUT1) {
			// graba la información
			try {
				_dsDetalleCotizacion.update();
				// calcula totales
				_dsCotizacionesCompra.setTotalesProveedor();
				_dsCotizacionesCompra.update();				
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		if (e.getComponent() == _cotizacionOptimaBUT) {
			
			try {
				
				// graba por si hay datos pendientes y verifica información
				_dsDetalleCotizacion.update();
				_dsCotizacionesCompra.setTotalesProveedor();
				_dsCotizacionesCompra.update();
				
				// genera la cotización óptima
				_dsCotizacionesCompra.generaCotizacionOptima();
				_dsCotizacionesCompra.update();
				
				setRecargar(true);
				sendPageRedirect();
				
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			}
			
			
		}
		
		if (e.getComponent() == _limpiaSeleccionBUT) {
			try {
				// recorro el data store del detalle desmarcando TODO
				if (_dsDetalleCotizacion.getRowCount() > 0) {
					for (int row = 0 ; row < _dsDetalleCotizacion.getRowCount() ; row++) {
						_dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor1(row, 0);
						_dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor2(row, 0);
						_dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor3(row, 0);
						_dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor4(row, 0);
						_dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor5(row, 0);
					}
				} else {
					displayErrorMessage("No existen detalles en esta Cotización");
					return false;
				}
				
				_dsDetalleCotizacion.update();
				_dsCotizacionesCompra.setTotalesProveedor();
				_dsCotizacionesCompra.update();
				
				setRecargar(true);
				sendPageRedirect();
				
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		if (e.getComponent() == _generarOCBUT1) {
			//verifica y genera OC's correspondientes
			DBConnection conn = DBConnection.getConnection(getApplicationName());
			try {
				// primero graba por si existen commit pendiente
				_dsDetalleCotizacion.update();
				// calcula totales
				_dsCotizacionesCompra.setTotalesProveedor();
				_dsCotizacionesCompra.update();

				// luego genera las OC's
				conn.beginTransaction();

				_dsCotizacionesCompra.generaOrdenesCompra(getCurrentRequest()
						.getRemoteHost(), conn);

				conn.commit();
				
				setRecargar(true);

				sendPageRedirect();

			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} finally {
				if (conn != null) {
					conn.rollback();
					conn.freeConnection();
				}
			}
		}

		return super.submitPerformed(e);
	}

	@Override
	public void pageRequested(PageEvent event) throws Exception {
		try {
			// si la página es requerida por si misma o está en proceso de
			// recargar un proyecto no hago nada
			if (!isReferredByCurrentPage() || isRecargar()) {
				// verifico si tiene parámetro
				setRow_id(getIntParameter("cotizacion_compra_id"));
				if (getRow_id() > 0) {
					// Viene seteada la cotización la recupero sino da error ya
					// que al cotizador sólo
					// se puede acceder vía otra pantalla

					// resetea todos los datasource
					_dsCotizacionesCompra.reset();
					_dsDetalleCotizacion.reset();

					// recupera toda la información para la cotización
					_dsCotizacionesCompra
							.retrieve("cotizaciones_compra.cotizacion_compra_id = "
									+ Integer.toString(getRow_id()));
					_dsCotizacionesCompra.waitForRetrieve();
					_dsDetalleCotizacion
							.retrieve("detalle_cotizacion.cotizacion_compra_id = "
									+ Integer.toString(getRow_id()));
					_dsDetalleCotizacion.waitForRetrieve();
					_dsDetalleCotizacion.gotoFirst();
					_dsCotizacionesCompra.gotoFirst();

				} else {
					displayErrorMessage("Debe Especificar una cotización de Compra");
				}

				// oculta el menú para ganar espacio, total, lo único que puede
				// hacer es regresar a la SC
				_rawAddRow.setHtml("</tr><tr>");
				_navbarTable.setVisible(false);
			}
			
			setDatosBasicosSolicitud();
			armaBotonera();
			setRecargar(false);

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}

		super.pageRequested(event);
	}

	/**
	 * Determina si existe un registro en contexto, recupera su estado y arma la
	 * botonera acorde
	 * 
	 * @throws DataStoreException
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
		if (_dsCotizacionesCompra.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar una cotización de compra para recuperar su estado");
		}

		estado = _dsCotizacionesCompra.getString("cotizaciones_compra.estado");

		// chequeo que el usuario tenga el rol COMPRADOR
		int currentUser = getSessionManager().getWebSiteUser().getUserID();
		if (UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR"))
			_generarOCBUT1.setVisible(true);
		else
			_generarOCBUT1.setVisible(false);

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
			// _dsSolicitudCompra.getString("solicitudes_compra.estado");
			// recorro los estados y seteo los botones
			SQL = "SELECT t.prompt_accion,t.accion,a.manual FROM infraestructura.transicion_estados t left join infraestructura.estados e on t.estado_origen = e.estado "
					+ " left join infraestructura.acciones_apps a on t.accion = a.accion "
					+ "where e.circuito = '"
					+ CIRCUITO
					+ "' and t.estado_origen = '" + estado + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				int i = 100;
				com.salmonllc.html.HtmlSubmitButton boton;
				do {
					if (r.getBoolean(3) == true) {
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

	private void setDatosBasicosSolicitud() throws DataStoreException,
			SQLException {

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		String titulo = "Cotización de materiales Nº" + getRow_id();
		_detailformdisplaybox1.setHeadingCaption(titulo);
		_dsCotizacionesCompra.setCurrentWebsiteUserId(currentUser);
		// setea la URL del reporte a generar al presionar el botón de
		// impresión
		String URL = armarUrlReporte("XLS", "cotizacion_compra",
				"&param_cotizacion_compra_id=" + getRow_id());
		_imprimirCotizacionCompraBUT1.setHref(URL);

		URL = armarUrlReporte("PDF", "cotizacion_compra",
				"&param_cotizacion_compra_id=" + getRow_id());
		_imprimirCotizacionCompraBUT2.setHref(URL);
		
		// si no existe solicitud desactivo el enlace en cada detalle
		for (int row=0; row < _dsDetalleCotizacion.getRowCount(); row++) {
			if (_dsDetalleCotizacion.getDetalleScOrdenCompraId(row) == 0) {
				_lnkordencompra1.setVisible(false);
			} else {
				_lnkordencompra1.setVisible(true);
			}
		}
		
	}

	/**
	 * Setea la variable recargar al valor boleano indicado
	 * 
	 * @param recargar el valor boleano a setear
	 */
	public void setRecargar(boolean recargar) {
		this.recargar = recargar;
	}

	/**
	 * Indica si se debe o no recargar los datastores de controlador
	 * en el request actual
	 * 
	 * @return el valor boleano actual de recargar
	 */
	public boolean isRecargar() {
		return recargar;
	}

}
