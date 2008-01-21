//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;

/**
 * AbmcClaseArticuloController: a SOFIA generated controller
 */
public class AbmcClaseArticuloController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8890819624401404940L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _inventariableTE10;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _descripcionCAP12;
	public com.salmonllc.html.HtmlText _descripcionCAP16;
	public com.salmonllc.html.HtmlText _descripcionTXT8;
	public com.salmonllc.html.HtmlText _inventariableCAP20;
	public com.salmonllc.html.HtmlText _nombreCAP11;
	public com.salmonllc.html.HtmlText _nombreTXT7;
	public com.salmonllc.html.HtmlText _observacionesCAP13;
	public com.salmonllc.html.HtmlText _observacionesCAP18;
	public com.salmonllc.html.HtmlText _observacionesTXT9;
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

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ClaseArticuloModel _dsClase;

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSCLASE_CLASE_ARTICULO_CLASE_ARTICULO_ID = "clase_articulo.clase_articulo_id";
	public static final String DSCLASE_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSCLASE_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String DSCLASE_CLASE_ARTICULO_OBSERVACIONES = "clase_articulo.observaciones";
	public static final String DSCLASE_CLASE_ARTICULO_GENERICO = "clase_articulo.generico";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		_detailformdisplaybox1.setVisible((_dsClase.getRow() == -1) ? false : true);		
		super.pageRequested(p);
	}	
}
