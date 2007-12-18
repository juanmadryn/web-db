//package statement
package partesEQ.controllers;

//Salmon import statements
import java.sql.SQLException;

import infraestructura.controllers.BaseController;

import com.salmonllc.jsp.*;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.*;

/**
 * CargarPartesEqPlanoController: a SOFIA generated controller
 */
public class CargarPartesEqPlanoController extends BaseController implements ValueChangedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8853861713303315246L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _categoriaTE2;
	public com.salmonllc.html.HtmlDropDownList _proyectoTE3;
	public com.salmonllc.html.HtmlDropDownList _choferTE20;
	public com.salmonllc.html.HtmlLookUpComponent _codinvTE1;
	public com.salmonllc.html.HtmlSubmitButton _nuevoBUT91;
	public com.salmonllc.html.HtmlSubmitButton _nuevoBUT92;
	public com.salmonllc.html.HtmlText _accionesCAP9;
	public com.salmonllc.html.HtmlText _categoriaCAP2;
	public com.salmonllc.html.HtmlText _fechaCAP3;
	public com.salmonllc.html.HtmlText _horaDesdeCAP5;
	public com.salmonllc.html.HtmlText _horaHastaCAP5;
	public com.salmonllc.html.HtmlText _horasCAP6;
	public com.salmonllc.html.HtmlText _codinvCAP1;
	public com.salmonllc.html.HtmlText _mensajesCAP10;
	public com.salmonllc.html.HtmlText _mensajesTE19;
	public com.salmonllc.html.HtmlText _proyectoCAP4;
	public com.salmonllc.html.HtmlText _choferCAP7;
	//public com.salmonllc.html.HtmlText _apeynomTEX1;
	//public com.salmonllc.html.HtmlText _nombreequipoTEX2;
	public com.salmonllc.html.HtmlTextEdit _fechaTE3;
	public com.salmonllc.html.HtmlTextEdit _horaDesdeTE4;
	public com.salmonllc.html.HtmlTextEdit _horahastaTE5;
	public com.salmonllc.html.HtmlTextEdit _horasTE6;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listFormDisplayBox1;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.html.HtmlSubmitButton _nuevoParteCopiarBUT1;
	public com.salmonllc.html.HtmlSubmitButton _nuevoParteNuevoBUT2;
	public com.salmonllc.html.HtmlSubmitButton _grabarParteBUT3;
	public com.salmonllc.html.HtmlSubmitButton _eliminaParteBUT4;
	public com.salmonllc.html.HtmlSubmitButton _refrescarBUT5;

	// DataSources
	public partesEQ.models.PartesEqModel _dsPartes;

	// DataSource Column Constants
	public static final String DSPARTES_PARTES_EQ_PARTE_ID = "partes_eq.parte_id";
	public static final String DSPARTES_PARTES_EQ_PROYECTO_ID = "partes_eq.proyecto_id";
	public static final String DSPARTES_PARTES_EQ_TAREA_ID = "partes_eq.tarea_id";
	public static final String DSPARTES_PARTES_EQ_ESTADO = "partes_eq.estado";
	public static final String DSPARTES_PARTES_EQ_LOTE_ID = "partes_eq.lote_id";
	public static final String DSPARTES_PARTES_EQ_EQUIPO_ID = "partes_eq.equipo_id";
	public static final String DSPARTES_PARTES_EQ_CHOFER_ID = "partes_eq.chofer_id";
	public static final String DSPARTES_PARTES_EQ_FECHA = "partes_eq.fecha";
	public static final String DSPARTES_PARTES_EQ_HORA_DESDE = "partes_eq.hora_desde";
	public static final String DSPARTES_PARTES_EQ_HORA_HASTA = "partes_eq.hora_hasta";
	public static final String DSPARTES_PARTES_EQ_HORAS = "partes_eq.horas";
	public static final String DSPARTES_EQUIPOS_NOMBRE = "equipos.nombre";
	public static final String DSPARTES_LOTE_CARGA_PARTES_EQ_ESTADO = "lote_carga_partes_eq.estado";
	public static final String DSPARTES_LOTE_CARGA_PARTES_EQ_FECHA_ALTA = "lote_carga_partes_eq.fecha_alta";
	public static final String DSPARTES_LOTE_CARGA_PARTES_EQ_FECHA_CIERRE = "lote_carga_partes_eq.fecha_cierre";
	public static final String DSPARTES_CHOFERES_PERSONAL_ID = "choferes.personal_id";
	public static final String DSPARTES_CHOFERES_NRO_LEGAJO = "choferes.nro_legajo";
	public static final String DSPARTES_CHOFERES_APEYNOM = "choferes.apeynom";
	public static final String DSPARTES_MENSAJE_ERROR = "mensaje_error";

	// componentes custom
	public com.salmonllc.html.HtmlCheckBox _seleccionParteEq;
	public com.salmonllc.html.HtmlText _selParteCAP70;
	private String SELECCION_PARTE_FLAG = "SELECCION_PARTE_FLAG";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// genera botones custom
		_grabarParteBUT3 = new HtmlSubmitButton("grabarParteBUT3","Grabar",this);
		_grabarParteBUT3.setAccessKey("g");
		_listFormDisplayBox1.addButton(_grabarParteBUT3);
		
		_nuevoParteCopiarBUT1 = new HtmlSubmitButton("nuevoParteCopiarBUT1","Copiar Parte",this);
		_nuevoParteCopiarBUT1.setAccessKey("c");
		_listFormDisplayBox1.addButton(_nuevoParteCopiarBUT1);
		_nuevoParteNuevoBUT2 = new HtmlSubmitButton("nuevoParteNuevoBUT2","Nuevo Parte",this);
		_nuevoParteNuevoBUT2.setAccessKey("n");
		_listFormDisplayBox1.addButton(_nuevoParteNuevoBUT2);
		
		_eliminaParteBUT4 = new HtmlSubmitButton("eliminaParteBUT4","Eliminar",this);
		_eliminaParteBUT4.setAccessKey("e");
		_listFormDisplayBox1.addButton(_eliminaParteBUT4);
		
		_refrescarBUT5 = new HtmlSubmitButton("refrescarBUT5","Recargar",this);
		_refrescarBUT5.setAccessKey("r");
		_listFormDisplayBox1.addButton(_refrescarBUT5);
		
		// Agrega columna de seleccion al datasource de informes
		_dsPartes.addBucket(SELECCION_PARTE_FLAG, DataStore.DATATYPE_INT);
		_seleccionParteEq.setColumn(_dsPartes, SELECCION_PARTE_FLAG);
		_seleccionParteEq.setFalseValue(null);
				
		// Listeners
		addPageListener(this);
		_nuevoBUT92.addSubmitListener(this);
		_nuevoBUT91.addSubmitListener(this);
		_menuBUT.addSubmitListener(this);
		_grabarParteBUT3.addSubmitListener(this);
		_nuevoParteCopiarBUT1.addSubmitListener(this);
		_nuevoParteNuevoBUT2.addSubmitListener(this);
		_eliminaParteBUT4.addSubmitListener(this);
		_refrescarBUT5.addSubmitListener(this);
		
		
		// Listener para validar fecha antes de ser enviada al DataStore
		_fechaTE3.addValueChangedListener(this);
		
		_dsPartes.setAutoValidate(true);	
		
		//refresca la pantalla de partes
		refrescaPartes();
	}
	
	public void refrescaPartes() throws SQLException, DataStoreException {
		// setea y recupera los partes en estado generados
		_dsPartes.reset();
		_dsPartes.retrieve("partes_eq.estado = \"0005.0001\"");
		
		// si no recuperá ningún parte en estado generado, inserta al menos un registro
		_dsPartes.waitForRetrieve();
		if (_dsPartes.getRowCount() == 0){
			int row = _dsPartes.insertRow();
			java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis()-(1000*60*60*24));
			_dsPartes.setPartesEqFecha(row, hoy);
			_fechaTE3.setFocus(row, true);
		}
	}

	/**
	 * Process the given submit event
	 * 
	 * @param event the submit event to be processed
	 * @return true to continue processing events, false to stop processing
	 *         events
	 * 
	 */
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// Grabar
		if (e.getComponent() == _grabarParteBUT3){
			try {
				_dsPartes.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		// Crear un nuevo parte copiando el contenido del parte anterior
		if (e.getComponent() == _nuevoParteCopiarBUT1 || e.getComponent() == _nuevoBUT91){
			
			int rowActual = _dsPartes.getRow();
			// si existe row anterior, le copia los valores
			if (rowActual != -1) {
				// valido el parte que estoy copiando
				try {
					_dsPartes.completaUnParte(rowActual);
				}catch (DataStoreException ex) {
					// muestro mensaje pero sigo sin problemas
					displayErrorMessage(ex.getMessage());
					_fechaTE3.setFocus(rowActual, true);
					return false;
				}
				int row = _dsPartes.insertRow(0);
				
				_dsPartes.setPartesEqFecha(row, _dsPartes.getPartesEqFecha(rowActual + 1));
				_dsPartes.setEquiposCodigoInventario(row, _dsPartes.getEquiposCodigoInventario(rowActual + 1));
				// Copiamos el número de legajo solo si este fue especificado				
				if (_dsPartes.getChoferesNroLegajo(rowActual + 1) > 0) {
					_dsPartes.setChoferesNroLegajo(row, _dsPartes.getChoferesNroLegajo(rowActual + 1));
				}				
				_dsPartes.setPartesEqProyecto(row, _dsPartes.getPartesEqProyecto(rowActual + 1));
				_dsPartes.setPartesEqHoraDesde(row, _dsPartes.getPartesEqHoraDesde(rowActual + 1));
				_dsPartes.setPartesEqHoraHasta(row, _dsPartes.getPartesEqHoraHasta(rowActual + 1));
								
				// hace foco en el registro
				int nroPagerow = _datatable1.getPage(row);
				int nroPageActual = _datatable1.getPage(_dsPartes.getRow());
				if (nroPagerow != nroPageActual)
					_datatable1.setPage(_datatable1.getPage(row));
				_fechaTE3.setFocus(row, true);
			} else {
				displayErrorMessage("Debe seleccionar un parte primero antes de copiarlo");
				return false;
			}
			
		}
		
		// Crea un nuevo parte
		if (e.getComponent() == _nuevoParteNuevoBUT2 || e.getComponent() == _nuevoBUT92) {
			int rowActual = _dsPartes.getRow();
			int row = _dsPartes.insertRow(0);
			// si existe row anterior, sólo copia valores básicos
			if (row > 1) {
				// valido el parte que estoy copiando
				_dsPartes.setPartesEqFecha(row, _dsPartes.getPartesEqFecha(rowActual+1));
				_dsPartes.setPartesEqProyectoId(row, _dsPartes.getPartesEqProyectoId(rowActual+1));
			}
			
			// hace foco en el registro
			int nroPagerow = _datatable1.getPage(row);
			int nroPageActual = _datatable1.getPage(_dsPartes.getRow());
			if (nroPagerow != nroPageActual)
				_datatable1.setPage(_datatable1.getPage(row));
			_fechaTE3.setFocus(row, true);
		}
		
		// Elimina un parte
		if (e.getComponent() == _eliminaParteBUT4) {
			// elimina todos los partes seleccionados
			for (int i = 0; i < _dsPartes.getRowCount(); i++) {
				if (_dsPartes.getInt(i, SELECCION_PARTE_FLAG) == 1) {
					// Rol marcado para selección
					_dsPartes.deleteRow(i);
				}
			}
			
			_dsPartes.update();
			refrescaPartes();
		}
		
		// Refresca los partes
		if (e.getComponent() == _refrescarBUT5) {
			refrescaPartes();
		}
		
		return super.submitPerformed(e);
	}

	/*	 
	 * (non-Javadoc)
	 * @see com.salmonllc.html.events.ValueChangedListener#valueChanged(com.salmonllc.html.events.ValueChangedEvent)
	 */
	public boolean valueChanged(ValueChangedEvent e) throws Exception {
		// TextEdits para fechas de vigencia
		if (e.getComponent() == _fechaTE3) {
			if (_dsPartes != null) {
				if (!_dsPartes.isFormattedStringValid(e.getColumn(), e.getNewValue())) {
					// No movemos el nuevo valor al dataStore,pero evitamos 
					// que sea eliminado la proxima vez que la pagina sea mostrada					
					e.setAcceptValue(ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE);
					
					// Mostramos el error
					if (_dsPartes.getMensajeError(_dsPartes.getRow()) == null)
						_dsPartes.setMensajeError(_dsPartes.getRow(), "Formato de fecha incorrecto.");
					else
						_dsPartes.setMensajeError(_dsPartes.getRow(), _dsPartes.getMensajeError(_dsPartes.getRow())
								+ " - Formato de fecha incorrecto.");
										
					displayErrorMessage("Hubo errores procesando partes. Corríjalos y grabe nuevamente.");					
					
					
					return false;
				}
			}
		}		
		return true;
	}
	
	@Override
	public void pageRequested(PageEvent p) throws Exception {
		int v_parte_id = -1;

		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			
			// verifico si tiene parámetro
			v_parte_id = getIntParameter("p_parte_id"); 
			if (v_parte_id > 0){
				// Viene seteado el parte. lo recupero sino no se hace nada
				
				// resetea todos los datasource
				_dsPartes.reset();

				// recupera toda la información del parte
				_dsPartes.retrieve("partes_eq.parte_id = " + Integer.toString(v_parte_id));
				_dsPartes.gotoFirst();
				_fechaTE3.setFocus(true);
			}
			else {
				refrescaPartes();
			}
		}

		super.pageRequested(p);
	}	

}