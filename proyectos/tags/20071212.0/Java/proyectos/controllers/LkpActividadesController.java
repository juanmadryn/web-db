//package statement
package proyectos.controllers;

//Salmon import statements
import com.salmonllc.jsp.*;
import com.salmonllc.html.events.*;

/**
 * LkpActividadesController: a SOFIA generated controller
 */
public class LkpActividadesController extends JspController implements
		SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9160162437970187321L;

	// Visual Components
	public com.salmonllc.html.HtmlText _actividadIdCAP10;

	public com.salmonllc.html.HtmlText _actividadIdTXT6;

	public com.salmonllc.html.HtmlText _buscarCAP5;

	public com.salmonllc.html.HtmlText _descripcionCAP12;

	public com.salmonllc.html.HtmlText _descripcionTXT8;

	public com.salmonllc.html.HtmlText _nombreCAP11;

	public com.salmonllc.html.HtmlText _nombreTXT7;

	public com.salmonllc.html.HtmlTextEdit _buscarTE3;

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

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSACTIVIDAD_ACTIVIDADES_ACTIVIDAD_ID = "actividades.actividad_id";

	public static final String DSACTIVIDAD_ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String DSACTIVIDAD_ACTIVIDADES_DESCRIPCION = "actividades.descripcion";

	public static final String DSACTIVIDAD_ACTIVIDADES_OBSERVACIONES = "actividades.observaciones";

	public static final String DSACTIVIDAD_ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String DSACTIVIDAD_ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String DSACTIVIDAD_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		addPageListener(this);
	}

	/**
	 * Process the given submit event
	 * 
	 * @param event
	 *            the submit event to be processed
	 * @return true to continue processing events, false to stop processing
	 *         events
	 */
	public boolean submitPerformed(SubmitEvent event) {
		return true;
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) {
		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			// verifico si tiene parámetro
			if (getBooleanParameter("p_es_hoja")) {
				// requere sólo actividades hoja
				_dsActividad.reset();
				_dsActividad.setCriteria("actividades.es_hoja = 'V'");
			} else {
				_dsActividad.reset();
				_dsActividad.setCriteria("");
			}

		}
	}

	/**
	 * Process the page request end event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequestEnd(PageEvent event) {
	}

	/**
	 * Process the page submit end event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageSubmitEnd(PageEvent event) {
	}

	/**
	 * Process the page submit event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageSubmitted(PageEvent event) {
	}

}
