//package statement
package infraestructura.controllers;

import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//Salmon import statements


/**
 * AbmcConfiguracionController: a SOFIA generated controller
 */
public class AbmcConfiguracionController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -3157144373071726323L;
	//Visual Components
      public com.salmonllc.html.HtmlCheckBox _seleccion1;
      public com.salmonllc.html.HtmlCheckBox _seleccion2;
      public com.salmonllc.html.HtmlLookUpComponent _atributoTE4;
      public com.salmonllc.html.HtmlSubmitButton _actualizarAttrBUT8;
      public com.salmonllc.html.HtmlSubmitButton _actualizarBUT4;
      public com.salmonllc.html.HtmlSubmitButton _agregarAttrBUT6;
      public com.salmonllc.html.HtmlSubmitButton _agregarBUT2;
      public com.salmonllc.html.HtmlSubmitButton _cancelarAttrBUT9;
      public com.salmonllc.html.HtmlSubmitButton _cancelarBUT5;
      public com.salmonllc.html.HtmlSubmitButton _eliminarAttrBUT7;
      public com.salmonllc.html.HtmlSubmitButton _eliminarBUT3;
      public com.salmonllc.html.HtmlText _atributoCAP4;
      public com.salmonllc.html.HtmlText _back;
      public com.salmonllc.html.HtmlText _cardinalidadConfCAP8;
      public com.salmonllc.html.HtmlText _cardinalidadConfTE3;
      public com.salmonllc.html.HtmlText _descripcionConfCAP5;
      public com.salmonllc.html.HtmlText _descripcionEsquemaTE3;
      public com.salmonllc.html.HtmlText _idEsquemaCAP1;
      public com.salmonllc.html.HtmlText _idEsquemaTE1;
      public com.salmonllc.html.HtmlText _nombreConfCAP4;
      public com.salmonllc.html.HtmlText _nombreEsquemaCAP2;
      public com.salmonllc.html.HtmlText _nombreEsquemaTE2;
      public com.salmonllc.html.HtmlText _observacionConfCAP6;
      public com.salmonllc.html.HtmlText _prioridadConfCAP7;
      public com.salmonllc.html.HtmlText _selCAP3;
      public com.salmonllc.html.HtmlText _valorAttrCAP5;
      public com.salmonllc.html.HtmlTextEdit _descripcionConfTE2;
      public com.salmonllc.html.HtmlTextEdit _nombreConfTE2;
      public com.salmonllc.html.HtmlTextEdit _observacionesConfTE2;
      public com.salmonllc.html.HtmlTextEdit _prioridadConfTE2;
      public com.salmonllc.html.HtmlTextEdit _valorAttrTE6;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspBox _box3;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDataTable _datatable3;
      public com.salmonllc.jsp.JspDisplayBox _displaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _displaybox2;
      public com.salmonllc.jsp.JspDisplayBox _displaybox3;

//DataSources
      public infraestructura.models.AtributosConfiguracionModel _dsAttr;
      public infraestructura.models.ConfiguracionModel _dsConf;
      public infraestructura.models.EsquemaConfiguracionModel _dsEsquema;

//DataSource Column Constants
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID="esquema_configuracion.esquema_configuracion_id";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_DESCRIPCION="esquema_configuracion.descripcion";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_OBSERVACIONES="esquema_configuracion.observaciones";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_TIPO_OBJETO="esquema_configuracion.tipo_objeto";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_NOMBRE_OBJETO="esquema_configuracion.nombre_objeto";

       public static final String DSATTR_ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID="atributos_configuracion.configuracion_id";
       public static final String DSATTR_ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID="atributos_configuracion.atributo_id";
       public static final String DSATTR_ATRIBUTOS_CONFIGURACION_VALOR="atributos_configuracion.valor";
       public static final String DSATTR_ATRIBUTOS_ROL_NOMBRE="atributos_rol.nombre";
       public static final String DSATTR_CONFIGURACION_NOMBRE="configuracion.nombre";
       public static final String DSATTR_ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";

       public static final String DSCONF_CONFIGURACION_CONFIGURACION_ID="configuracion.configuracion_id";
       public static final String DSCONF_CONFIGURACION_ESQUEMA_CONFIGURACION_ID="configuracion.esquema_configuracion_id";
       public static final String DSCONF_CONFIGURACION_NOMBRE="configuracion.nombre";
       public static final String DSCONF_CONFIGURACION_DESCRIPCION="configuracion.descripcion";
       public static final String DSCONF_CONFIGURACION_OBSERVACIONES="configuracion.observaciones";
       public static final String DSCONF_CONFIGURACION_CARDINALIDAD="configuracion.cardinalidad";
       public static final String DSCONF_CONFIGURACION_PRIORIDAD="configuracion.prioridad";
       public static final String DSCONF_ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";
       public static final String DSCONF_ESQUEMA_CONFIGURACION_TIPO_OBJETO="esquema_configuracion.tipo_objeto";
       public static final String DSCONF_ESQUEMA_CONFIGURACION_NOMBRE_OBJETO="esquema_configuracion.nombre_objeto";
       
       private String SELECCION_FLAG1 = "SELECCION_FLAG1";
       private String SELECCION_FLAG2 = "SELECCION_FLAG2";
       private int v_esquema_configuracion_id = -2;
       private int v_configuracion_id = -2;
	
       
    @Override
	public void initialize() throws Exception {
    	
    	// Agrega lo sbotones al listFormDisplayBox
   		_agregarBUT2 = new HtmlSubmitButton("agregarBUT2","Agregar",this);
   		_agregarBUT2.setAccessKey("A");
   		_displaybox2.addButton(_agregarBUT2);

   		_eliminarBUT3 = new HtmlSubmitButton("eliminarBUT3","Eliminar",this);
   		_eliminarBUT3.setAccessKey("E");
   		_displaybox2.addButton(_eliminarBUT3);

   		_actualizarBUT4 = new HtmlSubmitButton("actualizarBUT4","Grabar",this);
   		_actualizarBUT4.setAccessKey("G");
   		_displaybox2.addButton(_actualizarBUT4);

   		_cancelarBUT5 = new HtmlSubmitButton("cancelarBUT5","Cancelar",this);
   		_cancelarBUT5.setAccessKey("C");
   		_displaybox2.addButton(_cancelarBUT5);

    	
		_agregarBUT2.addSubmitListener(this);
		_eliminarBUT3.addSubmitListener(this);
		_actualizarBUT4.addSubmitListener(this);
		_cancelarBUT5.addSubmitListener(this);
		_agregarAttrBUT6.addSubmitListener(this);
		_eliminarAttrBUT7.addSubmitListener(this);
		_actualizarAttrBUT8.addSubmitListener(this);
		_cancelarAttrBUT9.addSubmitListener(this);
		
		// prepara datastores para validar constraints
		_dsConf.setAutoValidate(true);
		_dsAttr.setAutoValidate(true);
		
		// Agrega columna de seleccion para lso datasource de Configuración y atributos
		_dsConf.addBucket(SELECCION_FLAG1, DataStore.DATATYPE_INT);
		_seleccion1.setColumn(_dsConf, SELECCION_FLAG1);
		_seleccion1.setFalseValue(null);

		_dsAttr.addBucket(SELECCION_FLAG2, DataStore.DATATYPE_INT);
		_seleccion2.setColumn(_dsAttr, SELECCION_FLAG2);
		_seleccion2.setFalseValue(null);
		
		// completa con la invocación al base controller
		super.initialize();
	}

    @Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		// Nuevo registro de configuración
		if (e.getComponent() == _agregarBUT2){
			
			if (_dsEsquema.getRow() == -1) {
				displayErrorMessage("Debe estar en el contexto de un Esquema de configuración para agregar una configuración");
				return false;
			}
			
			int v_esquema_id = _dsEsquema.getEsquemaConfiguracionEsquemaConfiguracionId();
			if (v_esquema_id < 1) {
				displayErrorMessage("Debe existir un esquema para agregar una confioguración");
				return false;
			}
			
			int row = _dsConf.insertRow(0);

			// completa default de las columnas
			_dsConf.setConfiguracionEsquemaConfiguracionId(row,v_esquema_id);
			_dsConf.setConfiguracionCardinalidad(row, 0);
			_dsConf.setConfiguracionPrioridad(row, 50);

			// hace foco en el registro
			int nroPagerow = _datatable2.getPage(row);
			int nroPageActual = _datatable2.getPage(_dsConf.getRow());
			if (nroPagerow != nroPageActual)
				_datatable2.setPage(_datatable2.getPage(row));
			_nombreConfTE2.setFocus();
		}
		
		// borro registros de configuración (baja física)
		if (e.getComponent() == _eliminarBUT3){
			// recorro el datastore y seteo como anulado a los registros
			for (int i = 0 ; i < _dsConf.getRowCount() ; i++) {
				if (_dsConf.getInt(i, SELECCION_FLAG1) == 1) {
					// Entidad marcado para selección
					_dsConf.deleteRow(i);
					// dessetea el flag
					_dsConf.setInt(i, SELECCION_FLAG1, 0);
					try {
						_dsConf.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}

			}
		}
		
		// graba los datos del datastore configuraciones
		if (e.getComponent() == _actualizarBUT4) {
			try {
				_dsConf.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		// cancela operación de Configuración
		if (e.getComponent() == _cancelarBUT5) {
			_dsAttr.resetStatus();
			_dsAttr.reset();
			_dsConf.resetStatus();
			_dsConf.reset();
			_dsConf.retrieve();
			_dsConf.gotoFirst();
		}
		
		// agrega atributo a la configuración
		if (e.getComponent() == _agregarAttrBUT6){
			
			if (_dsConf.getRow() == -1) {
				displayErrorMessage("Debe estar en el contexto de una configuración para agregar una definición de atributo");
				return false;
			}
			
			int v_configuracion_id = _dsConf.getConfiguracionConfiguracionId();
			if (v_configuracion_id < 1) {
				displayErrorMessage("Debe existir una configuración para poder agregar un atributo");
				return false;
			}
			
			int row = _dsAttr.insertRow(0);

			// completa default de las columnas
			_dsAttr.setAtributosConfiguracionConfiguracionId(row,v_configuracion_id);

			// hace foco en el registro
			int nroPagerow = _datatable3.getPage(row);
			int nroPageActual = _datatable3.getPage(_dsAttr.getRow());
			if (nroPagerow != nroPageActual)
				_datatable3.setPage(_datatable3.getPage(row));
		}
		
		// borro registros de atributos (baja física)
		if (e.getComponent() == _eliminarAttrBUT7){
			// recorro el datastore y seteo como anulado a los registros
			for (int i = 0 ; i < _dsAttr.getRowCount() ; i++) {
				if (_dsAttr.getInt(i, SELECCION_FLAG2) == 1) {
					// Entidad marcado para selección
					_dsAttr.deleteRow(i);
					// dessetea el flag
					_dsAttr.setInt(i, SELECCION_FLAG2, 0);
					try {
						_dsAttr.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}
			}
		}
		
		// graba los datos del datastore de atributos
		if (e.getComponent() == _actualizarAttrBUT8) {

			// garantiza que se esté en el contexto de una configuración
			if (_dsConf.getRow() == -1) {
				displayErrorMessage("Debe estar en el contexto de una configuración para grabar una definición de atributo");
				return false;
			}
			
			int v_configuracion_id = _dsConf.getConfiguracionConfiguracionId();
			if (v_configuracion_id < 1) {
				displayErrorMessage("Debe existir una configuración para poder grabar un atributo");
				return false;
			}

			try {
				// graba los datos del datastore de atributos
				_dsAttr.update();
				
				// re-ejecuta la recuperación de los atributos para garantizar que estan todos
				// para poder contar la cardinalidad y re-setearla en la configuración
				_dsAttr.setCriteria("atributos_configuracion.configuracion_id = " + Integer.toString(v_configuracion_id));
				_dsAttr.reset();
				_dsAttr.retrieve();
				_dsAttr.waitForRetrieve();
				
				// cuento y seteo la cardinalidad
				int v_cardinalidad = _dsAttr.getRowCount();
				_dsConf.setConfiguracionCardinalidad(v_cardinalidad);
				_dsConf.update();

			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		// cancela operación de atributos de configuracion
		if (e.getComponent() == _cancelarBUT5) {
			_dsAttr.resetStatus();
			_dsAttr.reset();
			_dsAttr.retrieve();
			_dsAttr.gotoFirst();
		}
		
		return super.submitPerformed(e);
	}


	@Override
	public void pageRequested(PageEvent p) throws Exception {
		// verifico parámetros y seteo criterio de búsqueda

		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			int tmp_esquema_configuracion_id = getIntParameter("p_esquema_configuracion_id",-1);
			
			if (tmp_esquema_configuracion_id == -1) {
				// debe venir con parámetro
				displayErrorMessage("La página debe ser invocada con un parámetro");
				_dsAttr.reset();
				_dsConf.reset();
				_dsEsquema.reset();
			} else {
				// verifica si cambión el contexto
				if (tmp_esquema_configuracion_id != v_esquema_configuracion_id) {
					// cambió contexto, actualizo información
					v_esquema_configuracion_id = tmp_esquema_configuracion_id;
					_dsEsquema.setCriteria("esquema_configuracion.esquema_configuracion_id = "
									+ Integer.toString(v_esquema_configuracion_id));
					_dsConf.setCriteria("configuracion.esquema_configuracion_id = "
									+ Integer.toString(v_esquema_configuracion_id));
					try {
						_dsEsquema.retrieve();
						// controla que se recupera estrictamente 1 registro y luego se para en el
						_dsEsquema.waitForRetrieve();
						if (_dsEsquema.getRowCount() != 1) {
							displayErrorMessage("Recuperó mas de un esquema...Llame a su adminsitrador de sistema");
							return;
						}
						_dsEsquema.gotoFirst();
						_dsConf.retrieve();
						_dsConf.gotoFirst();
					} catch (SQLException e) {
						displayErrorMessage(e.getMessage());
						e.printStackTrace();
					} catch (DataStoreException e) {
						displayErrorMessage(e.getMessage());
						e.printStackTrace();
					}
					
				}
			}
		}

		// verifica si cambio la configuración
		if (_dsConf.getRow() != -1) {
			int tmp_configuracion_id = _dsConf.getConfiguracionConfiguracionId();
			if (tmp_configuracion_id != v_configuracion_id) {
				// cambio contexto, cambio variables y datastore
				v_configuracion_id = tmp_configuracion_id;
				_dsAttr.setCriteria("atributos_configuracion.configuracion_id = "
						+ Integer.toString(v_configuracion_id));
				_dsAttr.retrieve();
				_dsAttr.gotoFirst();
			}
		} else {
			// No está en ninguna configuración reseteo variables y datastore
			v_configuracion_id = -2;
			_dsAttr.setCriteria(null);
			_dsAttr.reset();
		}

		super.pageRequested(p);
	}

    
    
}
