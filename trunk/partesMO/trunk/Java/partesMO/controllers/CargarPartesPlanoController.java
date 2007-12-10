//package statement
package partesMO.controllers;

//Salmon import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import infraestructura.controllers.BaseController;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;


/**
 * CargarPartesPlanoController: a SOFIA generated controller
 */
public class CargarPartesPlanoController extends BaseController implements ValueChangedListener {

/**
	 * 
	 */
	private static final long serialVersionUID = -3504882331164132502L;
	//Visual Components
      public com.salmonllc.html.HtmlDropDownList _categoriaTE2;
      public com.salmonllc.html.HtmlDropDownList _proyectoTE3;
      public com.salmonllc.html.HtmlDropDownList _sectorTE7;
      public com.salmonllc.html.HtmlDropDownList _supervisorTE8;
      public com.salmonllc.html.HtmlLookUpComponent _legajoTE1;
      public com.salmonllc.html.HtmlSubmitButton _nuevoBUT91;
      public com.salmonllc.html.HtmlSubmitButton _nuevoBUT92;
      public com.salmonllc.html.HtmlText _accionesCAP9;
      public com.salmonllc.html.HtmlText _categoriaCAP2;
      public com.salmonllc.html.HtmlText _fechaCAP3;
      public com.salmonllc.html.HtmlText _horaDesdeCAP5;
      public com.salmonllc.html.HtmlText _horaHastaCAP5;
      public com.salmonllc.html.HtmlText _horasCAP6;
      public com.salmonllc.html.HtmlText _legajoCAP1;
      public com.salmonllc.html.HtmlText _mensajesCAP10;
      public com.salmonllc.html.HtmlText _mensajesTE19;
      public com.salmonllc.html.HtmlText _proyectoCAP4;
      public com.salmonllc.html.HtmlText _sectorCAP7;
      public com.salmonllc.html.HtmlText _supervisorCAP8;
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

//DataSources
      public partesMO.models.PartesMoModel _dsPartes;

//DataSource Column Constants
       public static final String DSPARTES_PARTES_MO_PARTE_ID="partes_mo.parte_id";
       public static final String DSPARTES_PARTES_MO_FECHA="partes_mo.fecha";
       public static final String DSPARTES_PARTES_MO_HORA_DESDE="partes_mo.hora_desde";
       public static final String DSPARTES_PARTES_MO_HORA_HASTA="partes_mo.hora_hasta";
       public static final String DSPARTES_PARTES_MO_HORAS="partes_mo.horas";
       public static final String DSPARTES_PARTES_MO_LOTE_ID="partes_mo.lote_id";
       public static final String DSPARTES_PARTES_MO_SUPERVISOR="partes_mo.supervisor";
       public static final String DSPARTES_PARTES_MO_CATEGORIA="partes_mo.categoria";
       public static final String DSPARTES_PARTES_MO_DESC_CATEGORIA="partes_mo.desc_categoria";
       public static final String DSPARTES_PARTES_MO_SECTOR_ID="partes_mo.sector_id";
       public static final String DSPARTES_PARTES_MO_PROYECTO_ID="partes_mo.proyecto_id";
       public static final String DSPARTES_PARTES_MO_PERSONAL_ID="partes_mo.personal_id";
       public static final String DSPARTES_PARTES_MO_NRO_LEGAJO="partes_mo.nro_legajo";
       public static final String DSPARTES_PARTES_MO_APEYNOM="partes_mo.apeynom";
       public static final String DSPARTES_PARTES_MO_ESTADO="partes_mo.estado";
       public static final String DSPARTES_SECTOR_TRABAJO_NOMBRE="sector_trabajo.nombre";
       public static final String DSPARTES_SUPERVISORES_APEYNOM="supervisores.apeynom";
       public static final String DSPARTES_ESTADOS_NOMBRE="estados.nombre";
       public static final String DSPARTES_LOTE_CARGA_PARTES_MO_ESTADO="lote_carga_partes_mo.estado";
       public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_ALTA="lote_carga_partes_mo.fecha_alta";
       public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_CIERRE="lote_carga_partes_mo.fecha_cierre";
       public static final String DSPARTES_MENSAJE_ERROR="mensaje_error";

   	// componentes custom
	public com.salmonllc.html.HtmlCheckBox _seleccionParte;
	public com.salmonllc.html.HtmlText _selParteCAP70;
	private String SELECCION_PARTE_FLAG = "SELECCION_PARTE_FLAG";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// genera botones custom
		_grabarParteBUT3 = new HtmlSubmitButton("grabarParteBUT3","Grabar",this);
		_grabarParteBUT3.setAccessKey("G");
		_listFormDisplayBox1.addButton(_grabarParteBUT3);
		
		_nuevoParteCopiarBUT1 = new HtmlSubmitButton("nuevoParteCopiarBUT1","Copiar Parte",this);
		_nuevoParteCopiarBUT1.setAccessKey("G");
		_listFormDisplayBox1.addButton(_nuevoParteCopiarBUT1);
		_nuevoParteNuevoBUT2 = new HtmlSubmitButton("nuevoParteNuevoBUT2","Nuevo Parte",this);
		_nuevoParteNuevoBUT2.setAccessKey("N");
		_listFormDisplayBox1.addButton(_nuevoParteNuevoBUT2);
		
		_eliminaParteBUT4 = new HtmlSubmitButton("eliminaParteBUT4","Eliminar",this);
		_eliminaParteBUT4.setAccessKey("E");
		_listFormDisplayBox1.addButton(_eliminaParteBUT4);
		
		_refrescarBUT5 = new HtmlSubmitButton("refrescarBUT5","Recargar",this);
		_refrescarBUT5.setAccessKey("R");
		_listFormDisplayBox1.addButton(_refrescarBUT5);
				
		// Agrega columna de seleccion al datasource de informes
		_dsPartes.addBucket(SELECCION_PARTE_FLAG, DataStore.DATATYPE_INT);
		_seleccionParte.setColumn(_dsPartes, SELECCION_PARTE_FLAG);
		_seleccionParte.setFalseValue(null);
				
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
		
		// completa la poplist de categorías
		try {
			completaCategorias();
		} catch (DataStoreException e) {
			displayErrorMessage("Error cargando categorias: " + e.getMessage());
		}
	}
	
	public void refrescaPartes() throws SQLException, DataStoreException {
		// setea y recupera los partes en estado generados
		_dsPartes.reset();
		_dsPartes.retrieve("partes_mo.estado = \"0003.0001\"");
		
		// si no recuperó ningún parte en estado generado, inserta al menos un registro
		_dsPartes.waitForRetrieve();
		if (_dsPartes.getRowCount() == 0){
			int row = _dsPartes.insertRow();
			java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis()-(1000*60*60*24));
			_dsPartes.setPartesMoFecha(row, hoy);
			_fechaTE3.setFocus(row, true);
		}
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		
		if (e.getComponent() == _grabarParteBUT3){
			try {
				_dsPartes.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
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

				_dsPartes.setPartesMoFecha(row, _dsPartes.getPartesMoFecha(rowActual+1));
				_dsPartes.setPartesMoHoraDesde(row, _dsPartes.getPartesMoHoraDesde(rowActual+1));
				_dsPartes.setPartesMoHoraHasta(row, _dsPartes.getPartesMoHoraHasta(rowActual+1));
				_dsPartes.setPartesMoNroLegajo(row, _dsPartes.getPartesMoNroLegajo(rowActual+1));
				_dsPartes.setPartesMoProyecto(row, _dsPartes.getPartesMoProyecto(rowActual+1));
				_dsPartes.setPartesMoProyectoNombre(row, _dsPartes.getPartesMoProyectoNombre(rowActual+1));
				_dsPartes.setPartesMoSectorId(row, _dsPartes.getPartesMoSectorId(rowActual+1));
				_dsPartes.setPartesMoSupervisor(row, _dsPartes.getPartesMoSupervisor(rowActual+1));

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
		
		if (e.getComponent() == _nuevoParteNuevoBUT2 || e.getComponent() == _nuevoBUT92){

			int rowActual = _dsPartes.getRow();
			int row = _dsPartes.insertRow(0);
			// si existe row anterior, s�lo copia valores b�sicos
			if (row > 1) {
				// valido el parte que estoy copiando
				_dsPartes.setPartesMoFecha(row, _dsPartes.getPartesMoFecha(rowActual+1));
				_dsPartes.setPartesMoProyectoId(row, _dsPartes.getPartesMoProyectoId(rowActual+1));
			}
			
			// hace foco en el registro
			int nroPagerow = _datatable1.getPage(row);
			int nroPageActual = _datatable1.getPage(_dsPartes.getRow());
			if (nroPagerow != nroPageActual)
				_datatable1.setPage(_datatable1.getPage(row));
			_fechaTE3.setFocus(row, true);
		}
		
		if (e.getComponent() == _eliminaParteBUT4) {
			// elimina todos los partes seleccionados
			for (int i = 0; i < _dsPartes.getRowCount(); i++) {
				if (_dsPartes.getInt(i, SELECCION_PARTE_FLAG) == 1) {
					// Rol marcado para selecci�n
					_dsPartes.deleteRow(i);
				}
			}
			
			_dsPartes.update();
			refrescaPartes();
		}
		
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

	public void completaCategorias() throws DataStoreException {
		Props p;
		String driverTango = null;
		String urlTango = null;
		String userTango = null;
		String passWordTango = null;
		Connection connTango;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;
		String cod_categoria = null;
		String desc_categoria = null;

		p = Props.getProps("partesMO", null);
		driverTango = p.getProperty("driverTango","sun.jdbc.odbc.JdbcOdbcDriver");
		urlTango = p.getProperty("urlTango", "jdbc:odbc:tango");
		userTango = p.getProperty("userTango", "tango");
		passWordTango = p.getProperty("passWordTango", "tango");

		// realiza una búsqueda custom de los legajos
		try {
			// Se carga el driver
			Class.forName(driverTango);
		} catch (ClassNotFoundException e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException("Imposible cargar el driver para Tango: " + e.getMessage());
		}

		try {
			// Se establece la conexión con la base de datos
			connTango = DriverManager.getConnection(urlTango, userTango,passWordTango);
		} catch (Exception e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException("imposible establecer conexión con la base tango: " + e.getMessage());
		}

		try {
			SQL = "select COD_CATEGORIA,DESC_CATEGORIA from CATEGORIA order by COD_CATEGORIA";
			st = connTango.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			r = st.executeQuery(SQL);
			
			MessageLog.writeErrorMessage(SQL, 1, null, null);
			
			_categoriaTE2.resetOptions();
			_categoriaTE2.addOption("", "");

			while (r.next()) {
				cod_categoria = r.getString("COD_CATEGORIA");
				desc_categoria = r.getString("DESC_CATEGORIA");
				
				_categoriaTE2.addOption(cod_categoria, desc_categoria);
			}

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// adem�s de escribir en el log mando mensaje a la p�gina
			throw new DataStoreException("Error determinando categorías en tango: " + e.getMessage());

		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		int v_parte_id = -1;
		String v_grp_parte_id = null;
		
		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			
			// verifico si tiene parametro
			v_parte_id = getIntParameter("p_parte_id");
			v_grp_parte_id = getParameter("p_grp_parte_id");			
			
			
			// Viene seteado el parte. lo recupero sino no se hace nada
			if (v_parte_id > 0) {			
				// resetea todos los datasource
				_dsPartes.reset();

				// recupera toda la informacion del parte
				_dsPartes.retrieve("partes_mo.parte_id = " + Integer.toString(v_parte_id));
				_dsPartes.gotoFirst();
				_fechaTE3.setFocus(true);
			}
			// Varios partes
			else if (v_grp_parte_id != null) {				
				if (v_grp_parte_id.trim().length() > 0) {
					// resetea todos los datasource
					_dsPartes.reset();

					// recupera toda la informacion del parte 
					_dsPartes.retrieve("partes_mo.parte_id IN (" + v_grp_parte_id + ")");
					_dsPartes.gotoFirst();
					_fechaTE3.setFocus(true);
				}
			}
			else {
				refrescaPartes();
			}
		}

		super.pageRequested(p);
	}
	
	

}
