//package statement
package infraestructura.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;


/**
 * AbmcRolEntidadController: a SOFIA generated controller
 */
public class AbmcRolEntidadController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -6319958091965018930L;
	//Visual Components
      public com.salmonllc.html.HtmlCheckBox _obligatorioCB1;
      public com.salmonllc.html.HtmlDropDownList _etiquetaTE63;
      public com.salmonllc.html.HtmlDropDownList _tipoDatoTE53;
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _descripcionCAP10;
      public com.salmonllc.html.HtmlText _descripcionCAP13;
      public com.salmonllc.html.HtmlText _descripcionCAP19;
      public com.salmonllc.html.HtmlText _descripcionCAP24;
      public com.salmonllc.html.HtmlText _descripcionTXT16;
      public com.salmonllc.html.HtmlText _descripcionTXT7;
      public com.salmonllc.html.HtmlText _desdeCAP21;
      public com.salmonllc.html.HtmlText _desdeCAP31;
      public com.salmonllc.html.HtmlText _desdeTXT31;
      public com.salmonllc.html.HtmlText _desdeTXT41;
      public com.salmonllc.html.HtmlText _etiquetaCAP62;
      public com.salmonllc.html.HtmlText _hastaCAP22;
      public com.salmonllc.html.HtmlText _hastaCAP32;
      public com.salmonllc.html.HtmlText _hastaTXT32;
      public com.salmonllc.html.HtmlText _hastaTXT42;
      public com.salmonllc.html.HtmlText _nombreCAP11;
      public com.salmonllc.html.HtmlText _nombreCAP20;
      public com.salmonllc.html.HtmlText _nombreCAP23;
      public com.salmonllc.html.HtmlText _nombreTXT18;
      public com.salmonllc.html.HtmlText _nombreTXT8;
      public com.salmonllc.html.HtmlText _obligatorioCAP51;
      public com.salmonllc.html.HtmlText _observacionesCAP15;
      public com.salmonllc.html.HtmlText _observacionesCAP25;
      public com.salmonllc.html.HtmlText _rolCAP12;
      public com.salmonllc.html.HtmlText _rolCAP9;
      public com.salmonllc.html.HtmlText _rolTXT6;
      public com.salmonllc.html.HtmlText _vigenciaCAP20;
      public com.salmonllc.html.HtmlText _vigenciaCAP40;
      public com.salmonllc.html.HtmlText _welcomeText;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _descripcionTE10;
      public com.salmonllc.html.HtmlTextEdit _descripcionTE5;
      public com.salmonllc.html.HtmlTextEdit _desdeTE21;
      public com.salmonllc.html.HtmlTextEdit _desdeTE41;
      public com.salmonllc.html.HtmlTextEdit _hastaTE22;
      public com.salmonllc.html.HtmlTextEdit _hastaTE42;
      public com.salmonllc.html.HtmlTextEdit _nombreTE6;
      public com.salmonllc.html.HtmlTextEdit _nombreTE9;
      public com.salmonllc.html.HtmlTextEdit _observacionesTE11;
      public com.salmonllc.html.HtmlTextEdit _observacionesTE7;
      public com.salmonllc.html.HtmlTextEdit _rolTE4;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspBox _box3;
      public com.salmonllc.jsp.JspBox _box4;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox2;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public infraestructura.models.AtributosRolModel _dsAttrRol;
      public infraestructura.models.RolEntidadModel _dsRolEntidad;

//DataSource Column Constants
       public static final String DSROLENTIDAD_ROL_ENTIDAD_ROL="rol_entidad.rol";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_NOMBRE="rol_entidad.nombre";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_DESCRIPCION="rol_entidad.descripcion";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_OBSERVACIONES="rol_entidad.observaciones";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_DESDE="rol_entidad.desde";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_HASTA="rol_entidad.hasta";
       public static final String DSROLENTIDAD_ROL_ENTIDAD_ANULADO="rol_entidad.anulado";

       public static final String DSQBE_BUSCAR="buscar";

       public static final String DSATTRROL_ATRIBUTOS_ROL_ATRIBUTO_ID="atributos_rol.atributo_id";
       public static final String DSATTRROL_ATRIBUTOS_ROL_NOMBRE="atributos_rol.nombre";
       public static final String DSATTRROL_ATRIBUTOS_ROL_DESCRIPCION="atributos_rol.descripcion";
       public static final String DSATTRROL_ATRIBUTOS_ROL_OBSERVACIONES="atributos_rol.observaciones";
       public static final String DSATTRROL_ATRIBUTOS_ROL_ROL="atributos_rol.rol";
       public static final String DSATTRROL_ATRIBUTOS_ROL_DESDE="atributos_rol.desde";
       public static final String DSATTRROL_ATRIBUTOS_ROL_HASTA="atributos_rol.hasta";
       public static final String DSATTRROL_ATRIBUTOS_ROL_ANULADO="atributos_rol.anulado";
       public static final String DSATTRROL_ATRIBUTOS_ROL_OBLIGATORIO="atributos_rol.obligatorio";
       public static final String DSATTRROL_ATRIBUTOS_ROL_TIPO_DATO="atributos_rol.tipo_dato";
       public static final String DSATTRROL_ATRIBUTOS_ROL_LOV="atributos_rol.lov";
       public static final String DSATTRROL_ATRIBUTOS_ROL_VALIDADOR="atributos_rol.validador";
       public static final String DSATTRROL_ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID="atributos_rol.clase_atributo_rol_id";
       public static final String DSATTRROL_ATRIBUTOS_ROL_NOMBRE_OBJETO="atributos_rol.nombre_objeto";
       public static final String DSATTRROL_ATRIBUTOS_ROL_TIPO_OBJETO="atributos_rol.tipo_objeto";
       public static final String DSATTRROL_CLASE_ATRIBUTO_ROL_ETIQUETA="clase_atributo_rol.etiqueta";
       public static final String DSATTRROL_ROL_ENTIDAD_NOMBRE="rol_entidad.nombre";

       
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 * @throws Exception 
 */
public void initialize() throws Exception{
	super.initialize();
}
 
/**
 * Process the page submit end event
 * @param event the page event to be processed
 */
public void pageSubmitEnd(PageEvent event) {
	super.pageSubmitEnd(event);
	
	// ante cada requerimiento verifica contexto y determina detalle de
	// atributos y completa FK's
	// Es row de rol válida?
	try {
		boolean actualizar = false;
		String rol = null;
		String rol_attr = null;
		if (_dsRolEntidad.getRow() != -1) {
			// recupera el rol del registro actual de roles
			rol = _dsRolEntidad.getRolEntidadRol();
			// si se está insertando un nuevo registro de atributos, no se actualiza
			if (!(_dsAttrRol.getRowStatus() == DataStoreBuffer.STATUS_NEW || 
					_dsAttrRol.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) 
			{
				// Ya existe detalle de atributos?
				if (_dsAttrRol.getRowCount() > 0 ) {
					// es el mismo contexto? --> recupero el rol del detalle para verificación, siempre del primer registro
					rol_attr = _dsAttrRol.getAtributosRolRol(0);
					if (rol_attr == null)
						actualizar = true;
					if (rol_attr != null && ! rol_attr.equalsIgnoreCase(rol)) {
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
			_dsAttrRol.reset();
			_dsAttrRol.retrieve("atributos_rol.rol = '" + rol + "'");
			_dsAttrRol.gotoFirst();
		}
		
		// reviso el contexto de datos a actualizar o grabar
		if (_dsRolEntidad.getRow() != -1 && _dsRolEntidad.getRolEntidadAnulado() == null)
			_dsRolEntidad.setRolEntidadAnulado("F");
		if (_dsRolEntidad.getRow() != -1 && _dsAttrRol.getRow() != -1 && _dsAttrRol.getAtributosRolRol() == null)
			_dsAttrRol.setAtributosRolRol(rol);
		if (_dsRolEntidad.getRow() != -1 && _dsAttrRol.getRow() != -1 && _dsAttrRol.getAtributosRolAnulado() == null)
			_dsAttrRol.setAtributosRolAnulado("F");
	} catch (DataStoreException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
 

}
