//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaPartesMoController: a SOFIA generated controller
 */
public class ConsultaPartesMoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Visual Components

	public com.salmonllc.html.HtmlText _apeynomTE23;
	public com.salmonllc.html.HtmlText _buscarCAP1;
	public com.salmonllc.html.HtmlText _fechaCAP22;
	public com.salmonllc.html.HtmlText _fechaTE22;
	public com.salmonllc.html.HtmlText _horaaTE26;
	public com.salmonllc.html.HtmlText _horaGuionTE26;
	public com.salmonllc.html.HtmlText _horarioCAP27;
	public com.salmonllc.html.HtmlText _horasTE26;
	public com.salmonllc.html.HtmlText _legajoCAP23;
	public com.salmonllc.html.HtmlText _nroLegajoTE23;
	public com.salmonllc.html.HtmlText _parteIdCAP2;
	public com.salmonllc.html.HtmlText _proyectoCAP24;
	public com.salmonllc.html.HtmlText _proyectosNombreTE24;
	public com.salmonllc.html.HtmlText _proyectosProyectoTE24;
	public com.salmonllc.html.HtmlText _proyectoTXT1;
	public com.salmonllc.html.HtmlText _sectorCAP25;
	public com.salmonllc.html.HtmlText _sectrorTE25;
	public com.salmonllc.html.HtmlText _supervisorCAP26;
	public com.salmonllc.html.HtmlText _supervisorTE26;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _horaDesdeTE26;
	public com.salmonllc.html.HtmlTextEdit _horaHastaTE26;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspLink _lnkpartes1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.html.HtmlLookUpComponent _proyectoTE3;
	public com.salmonllc.html.HtmlDropDownList _tarea_proyecto1;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public partesMO.models.PartesMoModel _dsPartes;

	// DataSource Column Constants
	public static final String DSPARTES_PARTES_MO_PARTE_ID = "partes_mo.parte_id";
	public static final String DSPARTES_PARTES_MO_FECHA = "partes_mo.fecha";
	public static final String DSPARTES_PARTES_MO_HORA_DESDE = "partes_mo.hora_desde";
	public static final String DSPARTES_PARTES_MO_HORA_HASTA = "partes_mo.hora_hasta";
	public static final String DSPARTES_PARTES_MO_HORAS = "partes_mo.horas";
	public static final String DSPARTES_PARTES_MO_LOTE_ID = "partes_mo.lote_id";
	public static final String DSPARTES_PARTES_MO_SUPERVISOR = "partes_mo.supervisor";
	public static final String DSPARTES_PARTES_MO_CATEGORIA = "partes_mo.categoria";
	public static final String DSPARTES_PARTES_MO_DESC_CATEGORIA = "partes_mo.desc_categoria";
	public static final String DSPARTES_PARTES_MO_SECTOR_ID = "partes_mo.sector_id";
	public static final String DSPARTES_PARTES_MO_PROYECTO_ID = "partes_mo.proyecto_id";
	public static final String DSPARTES_PARTES_MO_TAREA_ID = "partes_mo.tarea_id";
	public static final String DSPARTES_PARTES_MO_PERSONAL_ID = "partes_mo.personal_id";
	public static final String DSPARTES_PARTES_MO_NRO_LEGAJO = "partes_mo.nro_legajo";
	public static final String DSPARTES_PARTES_MO_APEYNOM = "partes_mo.apeynom";
	public static final String DSPARTES_PARTES_MO_ESTADO = "partes_mo.estado";
	public static final String DSPARTES_SECTOR_TRABAJO_NOMBRE = "sector_trabajo.nombre";
	public static final String DSPARTES_SUPERVISORES_APEYNOM = "supervisores.apeynom";
	public static final String DSPARTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_ESTADO = "lote_carga_partes_mo.estado";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_ALTA = "lote_carga_partes_mo.fecha_alta";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_CIERRE = "lote_carga_partes_mo.fecha_cierre";
	public static final String DSPARTES_PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String DSPARTES_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSPARTES_MENSAJE_ERROR = "mensaje_error";

	public static final String DSQBE_BUSCAR = "buscar";

	// Componentes visuales custom
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT16;
	public com.salmonllc.html.HtmlSubmitButton _guardarButton;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();

		_buscarBUT16 = new HtmlSubmitButton("buscarBUT16", "Buscar", this);
		_buscarBUT16.setAccessKey("b");
		_searchformdisplaybox1.addButton(_buscarBUT16);

		_guardarButton = new HtmlSubmitButton("guardarButton", "Guardar", this);
		_guardarButton.setAccessKey("G");
		_listformdisplaybox1.addButton(_guardarButton);

		addPageListener(this);
		_buscarBUT16.addSubmitListener(this);
		_guardarButton.addSubmitListener(this);
		
		_proyectoTE3.getEditField().setOnLoseFocus("llenarLista(true);");		
		_tarea_proyecto1.setOnFocus("llenarLista(false);");		
		setOnFocus("llenarLista(false);");		
	}

	/**
	 * Process the given submit event
	 * 
	 * @param event
	 *           the submit event to be processed
	 * @return true to continue processing events, false to stop processing
	 *         events
	 */
	public boolean submitPerformed(SubmitEvent event) throws Exception {

		// Buscar
		if (event.getComponent() == _buscarBUT16) {
			// solicita buscar partes
			String whereClause = _dsQBE.generateSQLFilter(_dsPartes);
			_dsPartes.retrieve(whereClause);
		}

		if (event.getComponent() == _guardarButton) {
			// solicita buscar partes
			try {
				_dsPartes.update();
			} catch (DataStoreException e) {
				displayErrorMessage("Error guardando parte: "+e.getMessage());
			}

		}

		return super.submitPerformed(event);
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		int v_nro_legajo = -1;
		String v_fecha = null;
		StringBuilder whereClause = new StringBuilder(50);

		// si la pagina no es referida por si misma
		if (!isReferredByCurrentPage()) {
			v_nro_legajo = getIntParameter("v_nro_legajo");
			if (v_nro_legajo > 0)
				whereClause.append("partes_mo.nro_legajo = "
						+ Integer.toString(v_nro_legajo));

			v_fecha = getParameter("v_fecha");
			if (v_fecha != null) {
				if (whereClause.length() > 0)
					whereClause.append(" and ");
				whereClause.append("partes_mo.fecha = '" + v_fecha + "'");
			}
			if (whereClause.length() > 0) {
				_dsPartes.reset();
				_dsPartes.retrieve(whereClause.toString());
				_dsPartes.gotoFirst();
				_tarea_proyecto1.setEnabled(true);
				_proyectoTE3.setEnabled(true);
				_horaDesdeTE26.setEnabled(true);
				_horaHastaTE26.setEnabled(true);
			} else {
				_tarea_proyecto1.setEnabled(false);
				_proyectoTE3.setEnabled(false);
				_horaDesdeTE26.setEnabled(false);
				_horaHastaTE26.setEnabled(false);
			}
		}
		super.pageRequested(p);
	}
}
