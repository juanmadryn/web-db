//package statement
package inventario.controllers;

//Salmon import statements
import inventario.models.DetalleSCModel;

import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * ListaSolicitantesController: a SOFIA generated controller
 */
public class ListaSolicitantesController extends JspController implements
		SubmitListener, PageListener {

	private static final long serialVersionUID = 1L;
	//Visual Components
	public com.salmonllc.html.HtmlSubmitButton _cerrar;
	public com.salmonllc.html.HtmlText _apeynomCAP1;
	public com.salmonllc.html.HtmlText _apeynomTXT1;
	public com.salmonllc.html.HtmlText _descripcionCAP3;
	public com.salmonllc.html.HtmlText _descripcionTXT3;
	public com.salmonllc.html.HtmlText _fechaAprobacionCAP5;
	public com.salmonllc.html.HtmlText _fechaAprobacionTXT5;
	public com.salmonllc.html.HtmlText _fechaSolicitudCAP4;
	public com.salmonllc.html.HtmlText _fechaSolicitudTXT4;
	public com.salmonllc.html.HtmlText _nroSolicitudCAP2;
	public com.salmonllc.html.HtmlText _nroSolicitudTXT2;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.DetalleSCModel _dsDetalleSc;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

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
	public static final String DSDETALLESC_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSDETALLESC_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSDETALLESC_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLESC_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String DSDETALLESC_DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DSDETALLESC_DETALLE_SC_UNIDAD_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String DSDETALLESC_UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String DSDETALLESC_MONTO_TOTAL = "monto_total";
	public static final String DSDETALLESC_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSDETALLESC_CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_DESCRIPCION = "solicitudes_compra.descripcion";
	public static final String DSDETALLESC_PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String DSDETALLESC_NOMBRE_COMPLETO_SOLICITANTE = "nombre_completo_solicitante";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_USER_ID_SOLICITA = "solicitudes_compra.user_id_solicita";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		addPageListener(this);
		_cerrar.addSubmitListener(this);
	}

	/**
	 * Process the given submit event
	 * @param event the submit event to be processed
	 * @return true to continue processing events, false to stop processing events
	 */
	public boolean submitPerformed(SubmitEvent event) {
		return true;
	}

	/**
	 * Process the page requested event
	 * @param event the page event to be processed
	 */
	public void pageRequested(PageEvent event) {
		try {
			// si la página es requerida por si misma no hago nada
			if (!isReferredByCurrentPage()) {				
				int orden_id = getIntParameter("orden_id");
				if (orden_id > 0) {					
					_dsDetalleSc.setGroupBy(
							DetalleSCModel.SOLICITUDES_COMPRA_USER_ID_SOLICITA + ", " +
							DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID);
					_dsDetalleSc.retrieve(DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " +orden_id);
					_dsDetalleSc.gotoFirst();
				}				
			}

		} catch (DataStoreException e) {
			MessageLog.writeInfoMessage(e.getMessage(), this);
		} catch (SQLException e) {
			MessageLog.writeInfoMessage(e.getMessage(), this);
		}
	}

	/**
	 * Process the page request end event
	 * @param event the page event to be processed
	 */
	public void pageRequestEnd(PageEvent event) {
	}

	/**
	 * Process the page submit end event
	 * @param event the page event to be processed
	 */
	public void pageSubmitEnd(PageEvent event) {
	}

	/**
	 * Process the page submit event
	 * @param event the page event to be processed
	 */
	public void pageSubmitted(PageEvent event) {
	}

}
