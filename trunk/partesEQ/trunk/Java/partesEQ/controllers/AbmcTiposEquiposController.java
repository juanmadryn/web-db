//package statement
package partesEQ.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;

/**
 * AbmcTiposEquiposController: a SOFIA generated controller
 */
public class AbmcTiposEquiposController extends BaseController {

	//Visual Components
	public com.salmonllc.html.HtmlDropDownList _inventariableTE10;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _descripcionCAP12;
	public com.salmonllc.html.HtmlText _descripcionCAP16;
	public com.salmonllc.html.HtmlText _descripcionTXT8;
	public com.salmonllc.html.HtmlText _inventariableCAP20;
	public com.salmonllc.html.HtmlText _nombreCAP11;
	public com.salmonllc.html.HtmlText _nombreCAP14;
	public com.salmonllc.html.HtmlText _nombreTXT7;
	public com.salmonllc.html.HtmlText _observacionesCAP18;
	public com.salmonllc.html.HtmlText _tipoCAP12;
	public com.salmonllc.html.HtmlText _tipoIdCAP10;
	public com.salmonllc.html.HtmlText _tipoIdTXT6;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _descripcionTE6;
	public com.salmonllc.html.HtmlTextEdit _nombreTE5;
	public com.salmonllc.html.HtmlTextEdit _nombreTE6;
	public com.salmonllc.html.HtmlTextEdit _observacionesTE8;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	
	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public partesEQ.models.TipoEquipoModel _dsTipo;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSTIPO_TIPO_EQUIPO_TIPO_EQUIPO = "tipo_equipo.tipo_equipo";
	public static final String DSTIPO_TIPO_EQUIPO_NOMBRE = "tipo_equipo.nombre";
	public static final String DSTIPO_TIPO_EQUIPO_DESCRIPCION = "tipo_equipo.descripcion";
	public static final String DSTIPO_TIPO_EQUIPO_OBSERVACIONES = "tipo_equipo.observaciones";
	public static final String DSTIPO_TIPO_EQUIPO_INVENTARIABLE = "tipo_equipo.inventariable";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() {
		try {
			super.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void pageRequested(PageEvent p) throws Exception {
		_detailformdisplaybox1.setVisible((_dsTipo.getRow() == -1) ? false : true);		
		super.pageRequested(p);
	}	
}
