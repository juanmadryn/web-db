//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
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
	public com.salmonllc.html.HtmlTextEdit _fechahastaTE2;
	public com.salmonllc.html.HtmlTextEdit _fechadesdeTE1;
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public partesMO.models.PartesMoModel _dsPartes;
	public com.salmonllc.sql.DataStore _dsPeriodo;

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
	public com.salmonllc.html.HtmlSubmitButton _eliminarButton;
	
	public com.salmonllc.html.HtmlCheckBox _seleccionParte;
	private String SELECCION_PARTE_FLAG = "SELECCION_PARTE_FLAG";
	

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
		
		_eliminarButton = new HtmlSubmitButton("eliminarButton", "Eliminar", this);
		_eliminarButton.setAccessKey("E");
		_listformdisplaybox1.addButton(_eliminarButton);

		addPageListener(this);
		_buscarBUT16.addSubmitListener(this);
		_guardarButton.addSubmitListener(this);
		_eliminarButton.addSubmitListener(this);

		// Agrega columna de seleccion al datasource de partes
		_dsPartes.addBucket(SELECCION_PARTE_FLAG, DataStore.DATATYPE_INT);
		_seleccionParte.setColumn(_dsPartes, SELECCION_PARTE_FLAG);
		
		_dsPeriodo.reset();
		_dsPeriodo.insertRow();
		seteaPeriodo(); // valores por defecto para el periodo de fechas
		_dsPeriodo.gotoFirst();
		
		_proyectoTE3.getEditField().addOnLoseFocus("llenarLista(true);");
		_proyectoTE3.getEditField().addOnChange("llenarLista(true);");
		_tarea_proyecto1.addOnFocus("llenarLista(false);");
		setOnFocus("llenarLista(false);");
		
		_horaDesdeTE26.setOnDoubleClick("copiarHora(event);");
		
		_horaHastaTE26.setOnDoubleClick("copiarHora(event);");
		
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
		String whereFecha = "";

		Date desde = _dsPeriodo.getDate("desde");
		Date hasta = _dsPeriodo.getDate("hasta");
		// chequeo las fechas
		if(desde != null && hasta != null)
		if (desde.compareTo(hasta) > 0) {
			displayErrorMessage("Error Controlando Relojes: combinación de fechas inválida");
			return false;
		} else {
			whereFecha = " fecha between '"
					+ desde.toString() + "' and '"
					+ hasta.toString() + "'";
		}

		// Buscar
		if (event.getComponent() == _buscarBUT16) {
			// solicita buscar partes
			String whereClause = _dsQBE.generateSQLFilter(_dsPartes);
			if (whereClause == "" || whereClause == null)
				whereClause = whereFecha;
			else
				whereClause += whereFecha != "" ? " and " + whereFecha : "";
			
			if (_proyecto2.getValue() != null
					&& _proyecto2.getValue().trim().length() > 0) {
					if (whereClause != null && whereClause.trim().length() != 0)
						whereClause += " AND ";
					else
						whereClause = "";
					whereClause += "proyectos.proyecto ="
							+ _proyecto2.getValue();				
			}
			_dsPartes.retrieve(whereClause);
		}

		if (event.getComponent() == _guardarButton) {
			// solicita buscar partes
			try {
				_dsPartes.setCorrige(true);
				_dsPartes.validarPartes();
				_dsPartes.update();
			} catch (DataStoreException e) {
				displayErrorMessage("Error guardando parte: " + e.getMessage());
			}

		}
		
		if (event.getComponent() == _eliminarButton) {
			// elimina todos los partes seleccionados
			for (int i = 0; i < _dsPartes.getRowCount(); i++) {
				if (_dsPartes.getInt(i, SELECCION_PARTE_FLAG) == 1) {
					// Rol marcado para seleccián
					_dsPartes.deleteRow(i);
				}
			}

			_dsPartes.update();
		}

		return super.submitPerformed(event);
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		int v_nro_legajo = -1;
		String v_fecha = null;
		String v_grp_parte_id = null;
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
			}
			// Varios partes
			v_grp_parte_id = getParameter("p_grp_parte_id");
			if (v_grp_parte_id != null) {
				if (v_grp_parte_id.trim().length() > 0) {
					// resetea todos los datasource
					_dsPartes.reset();

					// recupera toda la informacion del parte
					_dsPartes.retrieve("partes_mo.parte_id IN (" + v_grp_parte_id
							+ ")");
					_dsPartes.gotoFirst();
				}
			}
			if (v_fecha != null || v_grp_parte_id != null || v_nro_legajo != -1) {
				_tarea_proyecto1.setEnabled(true);
				_proyectoTE3.setEnabled(true);
				_horaDesdeTE26.setEnabled(true);
				_horaHastaTE26.setEnabled(true);
				_guardarButton.setEnabled(true);
				_eliminarButton.setEnabled(true);
				_dsPartes.completaHorasFichadas();
				_proyectoTE3.getEditField().setFocus(true);
			} else {
				_tarea_proyecto1.setEnabled(false);
				_proyectoTE3.setEnabled(false);
				_horaDesdeTE26.setEnabled(false);
				_horaHastaTE26.setEnabled(false);
				_guardarButton.setEnabled(false);
				_eliminarButton.setEnabled(false);
			}

		}
		super.pageRequested(p);
	}
	
	/**
	 * Setea el periodo por defecto para el rango de fechas
	 * 
	 * @throws DataStoreException
	 */
	public void seteaPeriodo() throws DataStoreException {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		_dsPeriodo.setDate("hasta", today);
		_dsPeriodo.setDate("desde", today);
	}
	
}
