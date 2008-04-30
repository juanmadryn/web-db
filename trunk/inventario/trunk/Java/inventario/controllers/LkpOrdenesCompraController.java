//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.sql.QBEBuilder;

/**
 * LkpOrdenesCompraController: a SOFIA generated controller
 */
public class LkpOrdenesCompraController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1535379108201291304L;
	//Visual Components
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _ordenCompraDescCAP11;
	public com.salmonllc.html.HtmlText _ordenCompraDescTXT7;
	public com.salmonllc.html.HtmlText _ordenCompraIdCAP10;
	public com.salmonllc.html.HtmlText _ordenCompraIdTXT6;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
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
	public static final String DSORDENESCOMPRA_ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";
	public static final String DSORDENESCOMPRA_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";
	public static final String DSORDENESCOMPRA_SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String DSORDENESCOMPRA_WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String DSORDENESCOMPRA_ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String DSORDENESCOMPRA_OBSERVACIONES = "observaciones";
	public static final String DSORDENESCOMPRA_TOTAL_ORDEN_COMPRA = "total_orden_compra";

	public static final String DSQBE_BUSCAR = "buscar";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {		
		// solamente recupera ordenes de compra en estado generado o revisado
		_dsQBE.addCriteria("estadosEditables", QBEBuilder.CRITERIA_TYPE_IN, "ordenes_compra.estado");
		_dsQBE.setString("estadosEditables", "0008.0001,0008.0005");
	}	
}
