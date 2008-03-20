//package statement
package inventario.controllers;

//Salmon import statements
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspController;

/**
 * LkpAtributosRolController: a SOFIA generated controller
 */
public class LkpAtributosRolController extends JspController implements SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381403351264768911L;
	// Visual Components
	public com.salmonllc.html.HtmlText _atributoIdCAP10;
	public com.salmonllc.html.HtmlText _atributoIdTXT6;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _descripcionCAP12;
	public com.salmonllc.html.HtmlText _descripcionTXT8;
	public com.salmonllc.html.HtmlText _nombreCAP11;
	public com.salmonllc.html.HtmlText _nombreTXT7;
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
	public static final String DSATTR_ATRIBUTOS_ROL_CLAVE = "atributos_rol.clave";
	public static final String DSATTR_ATRIBUTOS_ROL_OBLIGATORIO = "atributos_rol.obligatorio";
	public static final String DSATTR_ATRIBUTOS_ROL_TIPO_DATO = "atributos_rol.tipo_dato";
	public static final String DSATTR_ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID = "atributos_rol.clase_lov_atributo_id";
	public static final String DSATTR_ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID = "atributos_rol.clase_atributo_rol_id";
	public static final String DSATTR_ATRIBUTOS_ROL_LOV = "atributos_rol.lov";
	public static final String DSATTR_ATRIBUTOS_ROL_VALIDADOR = "atributos_rol.validador";
	public static final String DSATTR_ATRIBUTOS_ROL_NOMBRE_OBJETO = "atributos_rol.nombre_objeto";
	public static final String DSATTR_ATRIBUTOS_ROL_TIPO_OBJETO = "atributos_rol.tipo_objeto";
	public static final String DSATTR_CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";
	public static final String DSATTR_ROL_ENTIDAD_NOMBRE = "rol_entidad.nombre";
	public static final String DSATTR_CLASE_LOV_ATRIBUTO_NOMBRE = "clase_lov_atributo.nombre";

	// custom
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
    private String rol = null;
    private String nombre_objeto = null;
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		addPageListener(this);
		
		// find button
		_buscarBUT = new HtmlSubmitButton("buscarBUT","Buscar",this);
		_buscarBUT.setAccessKey("b");		
		_searchformdisplaybox1.addButton(_buscarBUT);
		_buscarBUT.addSubmitListener(this);
	}

	public boolean submitPerformed(SubmitEvent e) throws Exception {
		
		// find button
		if (e.getComponent() == _buscarBUT) {
			rol = getParameter("rol",null);
			nombre_objeto = getParameter("nombre_objeto",null);
			
			// si no tiene parámetros reseteo el criterio de búsqueda y las variables globales
			if (rol == null && nombre_objeto == null) 
					_dsAttr.setCriteria(null);				
			else {			
				if (rol != null)
					// es rol seteo criterio para rol
					_dsAttr.setCriteria("(rol is null and tipo_objeto is null and nombre objeto is null) OR"
							+ " (atributos_rol.rol = \"" + rol + "\")");
				else 
					// el que es no nulo es el objeto
					_dsAttr.setCriteria("(atributos_rol.rol is null and atributos_rol.tipo_objeto is null and atributos_rol.nombre_objeto is null) OR"
							+ " (atributos_rol.tipo_objeto = 'TABLA' and"
							+ " atributos_rol.nombre_objeto = '" + nombre_objeto + "')");
			}			
			
			_dsAttr.retrieve();
		}
		
		return false;
	}

	public void pageRequestEnd(PageEvent p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void pageRequested(PageEvent p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void pageSubmitEnd(PageEvent p) {
		// TODO Auto-generated method stub
		
	}

	public void pageSubmitted(PageEvent p) {
		// TODO Auto-generated method stub
		
	}

}
