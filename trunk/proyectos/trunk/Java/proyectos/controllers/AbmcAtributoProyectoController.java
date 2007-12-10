//package statement
package proyectos.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * AbmcAtributoProyectoController: a SOFIA generated controller
 */
public class AbmcAtributoProyectoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7893948680274157351L;

	// Visual Components
	public com.salmonllc.html.HtmlCheckBox _obligatorioCB1;

	public com.salmonllc.html.HtmlDropDownList _etiquetaTE63;

	public com.salmonllc.html.HtmlDropDownList _tipoDatoTE53;

	public com.salmonllc.html.HtmlText _buscarCAP5;

	public com.salmonllc.html.HtmlText _descripcionCAP19;

	public com.salmonllc.html.HtmlText _descripcionCAP24;

	public com.salmonllc.html.HtmlText _descripcionTXT16;

	public com.salmonllc.html.HtmlText _desdeCAP21;

	public com.salmonllc.html.HtmlText _desdeTXT41;

	public com.salmonllc.html.HtmlText _etiquetaCAP62;

	public com.salmonllc.html.HtmlText _hastaCAP22;

	public com.salmonllc.html.HtmlText _hastaTXT42;

	public com.salmonllc.html.HtmlText _nombreCAP20;

	public com.salmonllc.html.HtmlText _nombreCAP23;

	public com.salmonllc.html.HtmlText _nombreTXT18;

	public com.salmonllc.html.HtmlText _obligatorioCAP51;

	public com.salmonllc.html.HtmlText _observacionesCAP25;

	public com.salmonllc.html.HtmlText _tipoDatoCAP52;

	public com.salmonllc.html.HtmlText _vigenciaCAP40;

	public com.salmonllc.html.HtmlTextEdit _buscarTE3;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE10;

	public com.salmonllc.html.HtmlTextEdit _desdeTE41;

	public com.salmonllc.html.HtmlTextEdit _hastaTE42;

	public com.salmonllc.html.HtmlTextEdit _nombreTE9;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE11;

	public com.salmonllc.jsp.JspBox _box1;

	public com.salmonllc.jsp.JspBox _box2;

	public com.salmonllc.jsp.JspDataTable _datatable1;

	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;

	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	public com.salmonllc.jsp.JspTable _table2;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;

	public infraestructura.models.AtributosRolModel _dsAttr;

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSATTR_ATRIBUTOS_ROL_ATRIBUTO_ID = "atributos_rol.atributo_id";

	public static final String DSATTR_ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";

	public static final String DSATTR_ATRIBUTOS_ROL_DESCRIPCION = "atributos_rol.descripcion";

	public static final String DSATTR_ATRIBUTOS_ROL_OBSERVACIONES = "atributos_rol.observaciones";

	public static final String DSATTR_ATRIBUTOS_ROL_ROL = "atributos_rol.rol";

	public static final String DSATTR_ATRIBUTOS_ROL_DESDE = "atributos_rol.desde";

	public static final String DSATTR_ATRIBUTOS_ROL_HASTA = "atributos_rol.hasta";

	public static final String DSATTR_ATRIBUTOS_ROL_ANULADO = "atributos_rol.anulado";

	public static final String DSATTR_ATRIBUTOS_ROL_OBLIGATORIO = "atributos_rol.obligatorio";

	public static final String DSATTR_ATRIBUTOS_ROL_TIPO_DATO = "atributos_rol.tipo_dato";

	public static final String DSATTR_ATRIBUTOS_ROL_LOV = "atributos_rol.lov";

	public static final String DSATTR_ATRIBUTOS_ROL_VALIDADOR = "atributos_rol.validador";

	public static final String DSATTR_ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID = "atributos_rol.clase_atributo_rol_id";

	public static final String DSATTR_ATRIBUTOS_ROL_NOMBRE_OBJETO = "atributos_rol.nombre_objeto";

	public static final String DSATTR_ATRIBUTOS_ROL_TIPO_OBJETO = "atributos_rol.tipo_objeto";

	public static final String DSATTR_CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";

	public static final String DSATTR_ROL_ENTIDAD_NOMBRE = "rol_entidad.nombre";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();

		_dsAttr
				.setCriteria("atributos_rol.tipo_objeto = \"TABLA\" AND atributos_rol.nombre_objeto = \"proyectos\"");
	}

	public void pageSubmitEnd(PageEvent event) {
		super.pageSubmitEnd(event);

		// ante cada requerimiento verifica contexto y determina detalle de
		// atributos y completa FK's
		// Es row de rol válida?
		try {
			// reviso el contexto de datos a actualizar o grabar
			if (_dsAttr.getRow() != -1
					&& _dsAttr.getAtributosRolNombreObjeto() == null)
				_dsAttr.setAtributosRolNombreObjeto("proyectos");
			if (_dsAttr.getRow() != -1
					&& _dsAttr.getAtributosRolTipoObjeto() == null)
				_dsAttr.setAtributosRolTipoObjeto("TABLA");
		} catch (DataStoreException e) {
			e.printStackTrace();
		}
	}

}
