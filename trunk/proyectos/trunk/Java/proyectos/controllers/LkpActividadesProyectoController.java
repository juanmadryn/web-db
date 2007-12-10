//package statement
package proyectos.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;

/**
 * LkpActividadesProyectoController: a SOFIA generated controller
 */
public class LkpActividadesProyectoController extends BaseController {

	/**
	 *
	 */
	private static final long serialVersionUID = 1118619639052849898L;

	// Visual Components
	public com.salmonllc.html.HtmlText _actividadIdCAP10;

	public com.salmonllc.html.HtmlText _actividadIdTXT6;

	public com.salmonllc.html.HtmlText _buscarCAP5;

	public com.salmonllc.html.HtmlText _descripcionCAP12;

	public com.salmonllc.html.HtmlText _descripcionTXT8;

	public com.salmonllc.html.HtmlText _nombreCAP11;

	public com.salmonllc.html.HtmlText _nombreTXT7;

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

	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;

	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;

	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;

	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;

	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;

	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;

	public proyectos.models.ActividadesModel _dsActividad;

	public proyectos.models.ActividadesProyectoModel _dsActividadProyecto;

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSACTIVIDAD_ACTIVIDADES_ACTIVIDAD_ID = "actividades.actividad_id";

	public static final String DSACTIVIDAD_ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String DSACTIVIDAD_ACTIVIDADES_DESCRIPCION = "actividades.descripcion";

	public static final String DSACTIVIDAD_ACTIVIDADES_OBSERVACIONES = "actividades.observaciones";

	public static final String DSACTIVIDAD_ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String DSACTIVIDAD_ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String DSACTIVIDAD_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_PROYECTO_PROYECTO_ID = "actividades_proyecto.proyecto_id";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_PROYECTO_ACTIVIDAD_ID = "actividades_proyecto.actividad_id";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID = "actividades_proyecto.actividad_proyecto_id";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String DSACTIVIDADPROYECTO_PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String DSACTIVIDADPROYECTO_ACTIVIDAD_PADRE_PADRE_NOMBRE = "actividad_padre_padre.nombre";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		try {
			super.initialize();
			addPageListener(this);
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Process the page requested event
	 *
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) {
		// si la página es requerida por si misma no hago nada
		try {
			if (!isReferredByCurrentPage()) {
				// verifico si tiene parámetro
				int param = getIntParameter("proyecto_id");

				if (param != (-1)) {
					// requere sólo actividades hoja
					_dsActividadProyecto.reset();
					_dsActividadProyecto
							.setCriteria("actividades_proyecto.proyecto_id = "
									+ param);
				}
			}
			super.pageRequested(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
