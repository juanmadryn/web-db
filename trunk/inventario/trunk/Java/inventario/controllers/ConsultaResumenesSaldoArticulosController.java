//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import inventario.models.MovimientoArticuloModel;

import java.util.Vector;

import com.salmonllc.html.events.PageEvent;

/**
 * ConsultaResumenesSaldoArticulosController: a SOFIA generated controller
 */
public class ConsultaResumenesSaldoArticulosController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6907786139688548928L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _almacen2;
	public com.salmonllc.html.HtmlDropDownList _periodo2;
	public com.salmonllc.html.HtmlLookUpComponent _articulo2;
	public com.salmonllc.html.HtmlText _almacen1;
	public com.salmonllc.html.HtmlText _almacen3;
	public com.salmonllc.html.HtmlText _almacen4;
	public com.salmonllc.html.HtmlText _almacen5;
	public com.salmonllc.html.HtmlText _almacen6;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _articulo3;
	public com.salmonllc.html.HtmlText _articulo4;
	public com.salmonllc.html.HtmlText _articulo5;
	public com.salmonllc.html.HtmlText _articulo6;
	public com.salmonllc.html.HtmlText _cant_transacciones_egresos1;
	public com.salmonllc.html.HtmlText _cant_transacciones_egresos2;
	public com.salmonllc.html.HtmlText _cant_transacciones_ingresos1;
	public com.salmonllc.html.HtmlText _cant_transacciones_ingresos2;
	public com.salmonllc.html.HtmlText _completo1;
	public com.salmonllc.html.HtmlText _completo2;
	public com.salmonllc.html.HtmlText _en_proceso1;
	public com.salmonllc.html.HtmlText _en_proceso2;
	public com.salmonllc.html.HtmlText _en_proceso3;
	public com.salmonllc.html.HtmlText _en_proceso4;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _n2;
	public com.salmonllc.html.HtmlText _periodo1;
	public com.salmonllc.html.HtmlText _periodo3;
	public com.salmonllc.html.HtmlText _periodo4;
	public com.salmonllc.html.HtmlText _periodo5;
	public com.salmonllc.html.HtmlText _periodo6;
	public com.salmonllc.html.HtmlText _recepcion1;
	public com.salmonllc.html.HtmlText _recepcion2;
	public com.salmonllc.html.HtmlText _reservado1;
	public com.salmonllc.html.HtmlText _reservado2;
	public com.salmonllc.html.HtmlText _reservado3;
	public com.salmonllc.html.HtmlText _reservado4;
	public com.salmonllc.html.HtmlText _retiro1;
	public com.salmonllc.html.HtmlText _retiro2;
	public com.salmonllc.html.HtmlText _stock_en_mano1;
	public com.salmonllc.html.HtmlText _stock_en_mano2;
	public com.salmonllc.html.HtmlText _stock_en_mano3;
	public com.salmonllc.html.HtmlText _stock_en_mano4;
	public com.salmonllc.html.HtmlText _tipo_movimiento1;
	public com.salmonllc.html.HtmlText _tipo_movimiento2;
	public com.salmonllc.html.HtmlText _total_egresos1;
	public com.salmonllc.html.HtmlText _total_egresos2;
	public com.salmonllc.html.HtmlText _total_ingresos1;
	public com.salmonllc.html.HtmlText _total_ingresos2;
	public com.salmonllc.html.HtmlText _unidad_medida_patron1;
	public com.salmonllc.html.HtmlText _unidad_medida_patron2;
	public com.salmonllc.html.HtmlText _unidad_medida_patron3;
	public com.salmonllc.html.HtmlText _unidad_medida_patron4;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspBox _box3;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspLink _lnkcomprobante1;
	public com.salmonllc.jsp.JspLink _lnkrecepcion1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;
	public com.salmonllc.jsp.JspLink _imprimirReporteBUT1;
	public com.salmonllc.jsp.JspLink _imprimirReporteBUT2;	

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ComprobanteMovimientoArticuloModel _dsComprobantes;
	public inventario.models.ResumenSaldoArticulosModel _dsResumenes;

	// DataSource Column Constants
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID = "resumen_saldo_articulos.resumen_saldo_articulo_id";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_ALMACEN_ID = "resumen_saldo_articulos.almacen_id";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_ARTICULO_ID = "resumen_saldo_articulos.articulo_id";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_PERIODO = "resumen_saldo_articulos.periodo";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO = "resumen_saldo_articulos.stock_en_mano";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_RESERVADO = "resumen_saldo_articulos.reservado";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_EN_PROCESO = "resumen_saldo_articulos.en_proceso";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS = "resumen_saldo_articulos.total_ingresos";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS = "resumen_saldo_articulos.total_egresos";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS = "resumen_saldo_articulos.cant_transacciones_ingresos";
	public static final String DSRESUMENES_RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS = "resumen_saldo_articulos.cant_transacciones_egresos";
	public static final String DSRESUMENES_ALMACENES_NOMBRE = "almacenes.nombre";
	public static final String DSRESUMENES_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSRESUMENES_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSRESUMENES_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSRESUMENES_ARTICULOS_UNIDAD_PATRON = "articulos.unidad_patron";

	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID = "comprobante_movimiento_articulo.comprobante_movimiento_id";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID = "comprobante_movimiento_articulo.almacen_id";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA = "comprobante_movimiento_articulo.user_id_retira";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR = "comprobante_movimiento_articulo.user_id_preparador";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO = "comprobante_movimiento_articulo.estado";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID = "comprobante_movimiento_articulo.tipo_movimiento_articulo_id";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA = "comprobante_movimiento_articulo.fecha";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES = "comprobante_movimiento_articulo.observaciones";
	public static final String DSCOMPROBANTES_COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID = "comprobante_movimiento_articulo.recepcion_compra_id";
	public static final String DSCOMPROBANTES_TIPO_MOVIMIENTO_ARTICULO_NOMBRE = "tipo_movimiento_articulo.nombre";
	public static final String DSCOMPROBANTES_TIPO_MOVIMIENTO_ARTICULO_POSITIVO = "tipo_movimiento_articulo.positivo";
	public static final String DSCOMPROBANTES_TIPO_MOVIMIENTO_ARTICULO_RESERVA = "tipo_movimiento_articulo.reserva";
	public static final String DSCOMPROBANTES_TIPO_MOVIMIENTO_ARTICULO_IMPRESION = "tipo_movimiento_articulo.impresion";
	public static final String DSCOMPROBANTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSCOMPROBANTES_WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO = "website_user_preparador.nombre_completo";
	public static final String DSCOMPROBANTES_LEGAJOS_APEYNOM = "legajos.APEYNOM";
	public static final String DSCOMPROBANTES_WEBSITE_USER_USER_ID = "website_user.user_id";

	public static final String DSQBE_ARTICULO = "articulo";
	public static final String DSQBE_ALMACEN = "almacen";
	public static final String DSQBE_PERIODO = "periodo";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		try {
			//_searchformdisplaybox1.getSearchButton().setOnClick("mostrarOcultar();");
			setOnLoad("mostrarOcultar();");
			super.initialize();
		} catch (Exception e) {
			displayErrorMessage(e.getMessage());
		}
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) {
		try {
			int resumen_id = 0;
			MovimientoArticuloModel movimientos = new MovimientoArticuloModel(
					"inventario");
			Vector<Integer> comprobantes = new Vector<Integer>();
			if (_dsResumenes.getRow() != -1) {
				resumen_id = _dsResumenes
						.getResumenSaldoArticulosResumenSaldoArticuloId();
				movimientos
						.retrieve("movimiento_articulo.resumen_saldo_articulo_id ="
								+ resumen_id);
				movimientos.waitForRetrieve();
				int comprobanteId;

				for (int row = 0; row < movimientos.getRowCount(); row++) {
					comprobanteId = movimientos
							.getMovimientoArticuloComprobanteMovimientoId(row);
					if (!comprobantes.contains(comprobanteId))
						comprobantes.add(comprobanteId);
				}
				if (comprobantes.size() > 0) {
					StringBuilder inCondition = new StringBuilder();
					inCondition.append("(");
					for (int id : comprobantes) {
						inCondition.append(id).append(",");
					}
					inCondition.replace(inCondition.length() - 1, inCondition
							.length(), "");
					inCondition.append(")");

					_dsComprobantes.reset();
					_dsComprobantes
							.retrieve("comprobante_movimiento_articulo.comprobante_movimiento_id IN "
									+ inCondition.toString());
					_dsComprobantes.gotoFirst();
				} else
					_dsComprobantes.reset();
			}
			setURLReporte();
			super.pageRequested(event);			
		} catch (Exception e) {
			displayErrorMessage(e.getMessage());
		}
	}	
	
	private void setURLReporte() {
		// setea la URL del reporte a generar al presionar el botón de
		// impresión
		String articulo = _articulo2.getValue() != null ? _articulo2.getValue() : "";
		String almacen = _almacen2.getValue() != null ? _almacen2.getValue() : "";
		String periodo = _periodo2.getValue() != null ? _periodo2.getValue() : "";
		String parametros = "&param_codigo_articulo=" + articulo +"&param_almacen_id=" + almacen +"&param_periodo=" + periodo; 
		String URL = armarUrlReporte("XLS", "reporte_stock_por_consulta", parametros);
		_imprimirReporteBUT1.setHref(URL);
		URL = armarUrlReporte("PDF", "reporte_stock_por_consulta",parametros);
		_imprimirReporteBUT2.setHref(URL);
	}
}
