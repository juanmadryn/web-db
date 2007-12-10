//package statement
package infraestructura.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;


/**
 * AbmcEntidadExternaController: a SOFIA generated controller
 */
public class CopyOfAbmcEntidadExternaController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -2170057286620401927L;
	//Visual Components
      public com.salmonllc.html.HtmlLookUpComponent _rolTE51;
      public com.salmonllc.html.HtmlText _activoCAP32;
      public com.salmonllc.html.HtmlText _activoCAP44;
      public com.salmonllc.html.HtmlText _activoTXT32;
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _codigoCAP10;
      public com.salmonllc.html.HtmlText _codigoCAP12;
      public com.salmonllc.html.HtmlText _codigoTXT7;
      public com.salmonllc.html.HtmlText _descripcionCAP13;
      public com.salmonllc.html.HtmlText _idCAP42;
      public com.salmonllc.html.HtmlText _idCAP9;
      public com.salmonllc.html.HtmlText _idTXT43;
      public com.salmonllc.html.HtmlText _idTXT6;
      public com.salmonllc.html.HtmlText _nombreCAP11;
      public com.salmonllc.html.HtmlText _nombreTXT8;
      public com.salmonllc.html.HtmlText _observacionesCAP15;
      public com.salmonllc.html.HtmlText _rolCAP50;
      public com.salmonllc.html.HtmlText _activoTXT45;
      public com.salmonllc.html.HtmlText _vigenciaCAP21;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _codigoTE4;
      public com.salmonllc.html.HtmlTextEdit _descripcionTE5;
      public com.salmonllc.html.HtmlTextEdit _desdeTE41;
      public com.salmonllc.html.HtmlTextEdit _hastaTE42;
      public com.salmonllc.html.HtmlTextEdit _nombreTE6;
      public com.salmonllc.html.HtmlTextEdit _observacionesTE7;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      
// Componentes custom
      public com.salmonllc.html.HtmlSubmitButton _agregarRolBUT1;
      public com.salmonllc.html.HtmlSubmitButton _grabarRolBUT2;
      public com.salmonllc.html.HtmlSubmitButton _borrarRolBUT3;
      public com.salmonllc.html.HtmlSubmitButton _activarRolBUT4;
      public com.salmonllc.html.HtmlSubmitButton _activarEntidadBUT5;
      
      public com.salmonllc.html.HtmlCheckBox _seleccion;
      public com.salmonllc.html.HtmlText _selCAP60;

      private String SELECCION_FLAG = "SELECCION_FLAG";


//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public infraestructura.models.EntidadExternaModel _dsEntidad;
      public infraestructura.models.RolesEntidadModel _dsRoles;

//DataSource Column Constants
       public static final String DSROLES_ROLES_ENTIDAD_DESDE="roles_entidad.desde";
       public static final String DSROLES_ROLES_ENTIDAD_HASTA="roles_entidad.hasta";
       public static final String DSROLES_ROLES_ENTIDAD_ACTIVO="roles_entidad.activo";
       public static final String DSROLES_ROLES_ENTIDAD_ANULADO="roles_entidad.anulado";
       public static final String DSROLES_ROLES_ENTIDAD_ENTIDAD_ID="roles_entidad.entidad_id";
       public static final String DSROLES_ROLES_ENTIDAD_ROL="roles_entidad.rol";
       public static final String DSROLES_ROL_ENTIDAD_NOMBRE="rol_entidad.nombre";
       public static final String DSROLES_ENTIDAD_EXTERNA_CODIGO="entidad_externa.codigo";
       public static final String DSROLES_ENTIDAD_EXTERNA_NOMBRE="entidad_externa.nombre";

       public static final String DSQBE_BUSCAR="buscar";

       public static final String DSENTIDAD_ENTIDAD_EXTERNA_ENTIDAD_ID="entidad_externa.entidad_id";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_CODIGO="entidad_externa.codigo";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_NOMBRE="entidad_externa.nombre";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_DESCRIPCION="entidad_externa.descripcion";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_OBSERVACIONES="entidad_externa.observaciones";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_ACTIVO="entidad_externa.activo";
       public static final String DSENTIDAD_ENTIDAD_EXTERNA_ANULADO="entidad_externa.anulado";

 
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 * @throws Exception 
 */
public void initialize() throws Exception{
	super.initialize();
	
	// completo botones a los listform
	_activarEntidadBUT5 = new HtmlSubmitButton("activarEntidadBUT5","acTivar",this);
	_activarEntidadBUT5.setAccessKey("T");
	_detailformdisplaybox1.addButton(_activarEntidadBUT5);
	
	_grabarRolBUT2 = new HtmlSubmitButton("grabarRolBUT2","Grabar",this);
	_grabarRolBUT2.setAccessKey("G");
	_listformdisplaybox2.addButton(_grabarRolBUT2);
	_agregarRolBUT1 = new HtmlSubmitButton("agregarRolBUT1","Agregar",this);
	_agregarRolBUT1.setAccessKey("A");
	_listformdisplaybox2.addButton(_agregarRolBUT1);
	_borrarRolBUT3 = new HtmlSubmitButton("borrarRolBUT3","Borrar",this);
	_agregarRolBUT1.setAccessKey("B");
	_listformdisplaybox2.addButton(_borrarRolBUT3);
	_activarRolBUT4 = new HtmlSubmitButton("activarRolBUT4","acTivar",this);
	_activarRolBUT4.setAccessKey("T");
	_listformdisplaybox2.addButton(_activarRolBUT4);

	_activarEntidadBUT5.addSubmitListener(this);
	_agregarRolBUT1.addSubmitListener(this);
	_grabarRolBUT2.addSubmitListener(this);
	_borrarRolBUT3.addSubmitListener(this);
	_activarRolBUT4.addSubmitListener(this);
	
	
	// Agrega columna de seleccion al datasource de informes
	_dsRoles.addBucket(SELECCION_FLAG, DataStore.DATATYPE_INT);
	_seleccion.setColumn(_dsRoles, SELECCION_FLAG);
	_seleccion.setFalseValue(null);

	
	_dsEntidad.setAutoValidate(true);
	_dsRoles.setAutoValidate(true);
}
 
public boolean submitPerformed(SubmitEvent e) throws Exception {

	if (e.getComponent() == _agregarRolBUT1) {
		// agrega nuevo registro al detalle
		_dsRoles.insertRow();
	}
	
	if (e.getComponent() == _grabarRolBUT2) {
		int entidad_id = 0;
		
		if (_dsEntidad.getRow() != -1) {
			// recupera la entidad de contexto
			entidad_id = _dsEntidad.getEntidadExternaEntidadId();
		}
		
		completarDefaultValues(entidad_id);

		try {
			_dsRoles.update();
		} catch (DataStoreException ex) {
			displayErrorMessage(ex.getMessage());
			return false;
		}
	}
	
	if (e.getComponent() == _borrarRolBUT3) {
		// Recorro todos los registros y para aquelos marcados les doy la baja lógica
		// por ahora anulado hasta futura implementación
		for (int i = 0; false && i < _dsRoles.getRowCount(); i++) {
			if (_dsRoles.getInt(i, SELECCION_FLAG) == 1) {
				// Rol marcado para selección
				_dsRoles.deleteRow(i);
				try {
					_dsRoles.update();
				} catch (DataStoreException ex) {
					displayErrorMessage(ex.getMessage());
					return false;
				}
			}
		}
	}
	
	if (e.getComponent() == _activarEntidadBUT5) {
		//TODO: IMPORTANTE: Se debe completar la funcionalidad de Reglas de negocio de activación en el modelo ante el UPDATE
		// activa la entidad corriente
		if (_dsEntidad.getRow() != -1) {
			_dsEntidad.setEntidadExternaActivo("V");
			try {
				_dsEntidad.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
	}
	
	if (e.getComponent() == _activarRolBUT4) {
		//TODO: IMPORTANTE: Se debe completar la funcionalidad de Reglas de negocio de activación en el modelo ante el UPDATE

		// activa la entidad corriente
		// Recorro todos los registros y para aquelos marcados llos activo
		for (int i = 0; i < _dsRoles.getRowCount(); i++) {
			if (_dsRoles.getInt(i, SELECCION_FLAG) == 1) {
				// Rol marcado para selección
				_dsRoles.setRolesEntidadActivo(i, "V");
				// dessetea el flag
				_dsRoles.setInt(i, SELECCION_FLAG, 0);
				try {
					_dsRoles.update();
				} catch (DataStoreException ex) {
					displayErrorMessage(ex.getMessage());
					return false;
				}
			}
		}
	}
	
	return super.submitPerformed(e);
}
 
/**
 * Process the page submit end event
 * @param event the page event to be processed
 */
public void pageSubmitEnd(PageEvent event) {
	// ante cada requerimiento verifica contexto y determina detalle de
	// atributos y completa FK's
	// Es row de rol válida?
	try {
		boolean actualizar = false;
		int entidad_id = 0;
		int entidad_id_roles = 0;
		if (_dsEntidad.getRow() != -1) {
			// recupera la entidad de contexto
			entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			// si se está insertando un nuevo registro de rol, no se actualiza
			if (!(_dsRoles.getRowStatus() == DataStoreBuffer.STATUS_NEW || 
					_dsRoles.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) 
			{
				// Ya existe detalle de roles?
				if (_dsRoles.getRowCount() > 0 ) {
					// es el mismo contexto? --> recupero la entidad del detalle para verificación, siempre del primer registro
					entidad_id_roles = _dsRoles.getRolesEntidadEntidadId(0);
					if (entidad_id_roles == 0)
						actualizar = true;
					if (entidad_id_roles != entidad_id) {
						// Es distinto el contexto del rol de atributo
						actualizar = true;
					}
				}
				else {
					actualizar = true;
				}
			}
		}
		if (actualizar) {
			actualizarRoles(entidad_id);
		}
		
		completarDefaultValues(entidad_id);
		
	} catch (DataStoreException e) {
		displayErrorMessage(e.getMessage());
	} catch (SQLException e) {
		displayErrorMessage(e.getMessage());
	}
}

public void actualizarRoles(int entidad_id) throws SQLException, DataStoreException{
	_dsRoles.reset();
	_dsRoles.retrieve("roles_entidad.entidad_id = " + Integer.toString(entidad_id) + " AND roles_entidad.anulado = \"F\"" );
	_dsRoles.gotoFirst();
}

public void completarDefaultValues(int entidad_id) throws DataStoreException{
	// reviso el contexto de datos a actualizar o grabar
	
	if (_dsEntidad.getRow() != -1 && _dsEntidad.getEntidadExternaAnulado() == null)
		_dsEntidad.setEntidadExternaAnulado("F");
	if (_dsEntidad.getRow() != -1 && _dsEntidad.getEntidadExternaActivo() == null)
		_dsEntidad.setEntidadExternaActivo("F");
	if (_dsEntidad.getRow() != -1 && _dsRoles.getRow() != -1 && _dsRoles.getRolesEntidadEntidadId() == 0)
		_dsRoles.setRolesEntidadEntidadId(entidad_id);
	if (_dsEntidad.getRow() != -1 && _dsRoles.getRow() != -1 && _dsRoles.getRolesEntidadAnulado() == null)
		_dsRoles.setRolesEntidadAnulado("F");
	if (_dsEntidad.getRow() != -1 && _dsRoles.getRow() != -1 && _dsRoles.getRolesEntidadActivo() == null)
		_dsRoles.setRolesEntidadActivo("F");
}
 

}
