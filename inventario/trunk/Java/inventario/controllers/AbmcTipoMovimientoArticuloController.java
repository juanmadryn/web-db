//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;

/**
 * AbmcTipoMovimientoArticuloController: a SOFIA generated controller
 */
public class AbmcTipoMovimientoArticuloController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 569153663240868091L;
	//Visual Components
	public com.salmonllc.html.HtmlDropDownList _positivoTE9;
	public com.salmonllc.html.HtmlDropDownList _reservaTE10;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _descripcionCAP12;
	public com.salmonllc.html.HtmlText _descripcionCAP16;
	public com.salmonllc.html.HtmlText _descripcionTXT8;
	public com.salmonllc.html.HtmlText _nombreCAP11;
	public com.salmonllc.html.HtmlText _nombreTXT7;
	public com.salmonllc.html.HtmlText _observacionesCAP13;
	public com.salmonllc.html.HtmlText _observacionesCAP18;
	public com.salmonllc.html.HtmlText _observacionesTXT9;
	public com.salmonllc.html.HtmlText _positivoCAP13;
	public com.salmonllc.html.HtmlText _positivoCAP19;
	public com.salmonllc.html.HtmlText _positivoXT9;
	public com.salmonllc.html.HtmlText _reservaCAP13;
	public com.salmonllc.html.HtmlText _reservaCAP20;
	public com.salmonllc.html.HtmlText _reservaTXT9;
	public com.salmonllc.html.HtmlText _tipoCAP12;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _descripcionTE6;
	public com.salmonllc.html.HtmlTextEdit _nombreTE5;
	public com.salmonllc.html.HtmlTextEdit _observacionesTE8;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.TipoMovimientoArticuloModel _dsTipoMovimiento;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID="tipo_movimiento_articulo.tipo_movimiento_articulo_id";
    public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_NOMBRE="tipo_movimiento_articulo.nombre";
    public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION="tipo_movimiento_articulo.descripcion";
    public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES="tipo_movimiento_articulo.observaciones";
    public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_POSITIVO="tipo_movimiento_articulo.positivo";
    public static final String DSTIPOMOVIMIENTO_TIPO_MOVIMIENTO_ARTICULO_RESERVA="tipo_movimiento_articulo.reserva";
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
	}
	
	@Override
	public void pageRequested(PageEvent p) throws Exception {
		_detailformdisplaybox1.setVisible((_dsTipoMovimiento.getRow() == -1) ? false : true);		
		super.pageRequested(p);
	}
}
