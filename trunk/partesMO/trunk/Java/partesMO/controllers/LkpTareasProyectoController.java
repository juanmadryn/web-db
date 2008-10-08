//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitListener;

/**
 * LkpTareasProyectoController: a SOFIA generated controller
 */
public class LkpTareasProyectoController extends BaseController implements
		SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2151535488544807840L;
	// Visual Components
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _tareasNombreCAP12;
	public com.salmonllc.html.HtmlText _tareasNombreTXT8;
	public com.salmonllc.html.HtmlText _tareasProyectoCAP11;
	public com.salmonllc.html.HtmlText _tareasProyectoIdCAP10;
	public com.salmonllc.html.HtmlText _tareasProyectoIdTXT6;
	public com.salmonllc.html.HtmlText _tareasProyectoTXT7;
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
	public proyectos.models.TareasProyectoModel _dsTareasProyecto;

	// DataSource Column Constants
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_TAREA_ID = "tareas_proyecto.tarea_id";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_DESCRIPCION = "tareas_proyecto.descripcion";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_OBSERVACIONES = "tareas_proyecto.observaciones";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_ESTADO = "tareas_proyecto.estado";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_PROYECTO_ID = "tareas_proyecto.proyecto_id";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_CLASE_TAREA_ID = "tareas_proyecto.clase_tarea_id";
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID = "tareas_proyecto.actividad_proyecto_id";
	public static final String DSTAREASPROYECTO_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSTAREASPROYECTO_ACTIVIDADES_NOMBRE = "actividades.nombre";
	public static final String DSTAREASPROYECTO_CLASES_TAREAS_NOMBRE = "clases_tareas.nombre";
	public static final String DSTAREASPROYECTO_ESTADOS_NOMBRE = "estados.nombre";

	public static final String DSQBE_BUSCAR = "buscar";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		try {
			// super.initialize();
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
			// if (!isReferredByCurrentPage()) {
			// verifico si tiene parámetro
			int param = getIntParameter("proyecto_id");
			if (param != (-1)) {
				// requere sólo actividades asociadas al proyecto actual
				_dsTareasProyecto
						.setCriteria("tareas_proyecto.proyecto_id = "
								+ param);
			}
			// }
			super.pageRequested(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
