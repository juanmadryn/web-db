//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;

/**
 * AbmcCentroCostoController: a SOFIA generated controller
 */
public class AbmcCentroCostoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5591139079139545785L;
	//Visual Components
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _descripcionCAP12;
	public com.salmonllc.html.HtmlText _descripcionCAP16;
	public com.salmonllc.html.HtmlText _descripcionTXT8;
	public com.salmonllc.html.HtmlText _nombreCAP11;
	public com.salmonllc.html.HtmlText _nombreTXT7;
	public com.salmonllc.html.HtmlText _observacionesCAP13;
	public com.salmonllc.html.HtmlText _observacionesCAP18;
	public com.salmonllc.html.HtmlText _observacionesTXT9;
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
	public inventario.models.CentroCostoModel _dsCentroCosto;

	//DataSource Column Constants
	public static final String DSCENTROCOSTO_CENTRO_COSTO_CENTRO_COSTO_ID = "centro_costo.centro_costo_id";
	public static final String DSCENTROCOSTO_CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String DSCENTROCOSTO_CENTRO_COSTO_DESCRIPCION = "centro_costo.descripcion";
	public static final String DSCENTROCOSTO_CENTRO_COSTO_OBSERVACIONES = "centro_costo.observaciones";

	public static final String DSQBE_BUSCAR = "buscar";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		_detailformdisplaybox1
				.setVisible((_dsCentroCosto.getRow() == -1) ? false : true);
		super.pageRequested(p);
	}
}
