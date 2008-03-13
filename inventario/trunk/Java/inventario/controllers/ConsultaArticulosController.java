//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import inventario.util.ReplicateSta11QuartzJob;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.QBEBuilder;

/**
 * ConsultaArticulosController: a SOFIA generated controller
 */
public class ConsultaArticulosController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5076870436431282948L;
	//Visual Components
	public com.salmonllc.html.HtmlText _articulosTE26;
	public com.salmonllc.html.HtmlText _buscarCAP1;
	public com.salmonllc.html.HtmlText _claseArticuloTE24;
	public com.salmonllc.html.HtmlText _claseCAP24;
	public com.salmonllc.html.HtmlText _descripcionCAP23;
	public com.salmonllc.html.HtmlText _descripcionTE23;
	public com.salmonllc.html.HtmlText _idTXT1;
	public com.salmonllc.html.HtmlText _nombreCAP22;
	public com.salmonllc.html.HtmlText _nombreTE22;
	public com.salmonllc.html.HtmlText _observacionCAP26;
	public com.salmonllc.html.HtmlText _parteIdCAP2;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ArticulosModel _dsArticulos;

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSARTICULOS_ARTICULOS_ARTICULO_ID = "articulos.articulo_id";
	public static final String DSARTICULOS_ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String DSARTICULOS_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSARTICULOS_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSARTICULOS_ARTICULOS_OBSERVACIONES = "articulos.observaciones";
	public static final String DSARTICULOS_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSARTICULOS_ARTICULOS_CLAVE_EXTERNA1 = "articulos.clave_externa1";
	public static final String DSARTICULOS_ARTICULOS_CLAVE_EXTERNA2 = "articulos.clave_externa2";
	public static final String DSARTICULOS_ARTICULOS_CLAVE_EXTERNA3 = "articulos.clave_externa3";
	public static final String DSARTICULOS_ARTICULOS_ACTIVO = "articulos.activo";
	public static final String DSARTICULOS_ARTICULOS_ANULADO = "articulos.anulado";
	public static final String DSARTICULOS_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSARTICULOS_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	
	// custom components
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
	public com.salmonllc.html.HtmlSubmitButton _replicaFromTangoBUT;	
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		_buscarBUT = new HtmlSubmitButton("buscarBUT","Buscar",this);
		_buscarBUT.setAccessKey("b");
		_buscarBUT.addSubmitListener(this);
		_searchformdisplaybox1.addButton(_buscarBUT);
		
		// Test the import process
		_replicaFromTangoBUT = new HtmlSubmitButton("replicaFromTangoBUT","Replicar desde Tango",this);
		_replicaFromTangoBUT.addSubmitListener(this);
		_searchformdisplaybox1.addButton(_replicaFromTangoBUT);
		
		// do not retrieve disallowed items
		_dsQBE.addCriteria("anulado", QBEBuilder.CRITERIA_TYPE_NOT_EQUALS, "articulos.anulado");
		_dsQBE.setString("anulado", "V");
	}
	
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// Find
		if (e.getComponent() == _buscarBUT) {		
			_dsArticulos.reset();		
			_dsArticulos.retrieve(_dsQBE);
			if (_dsArticulos.getRowCount() <= 0) { 
				displayErrorMessage("No se encontraron articulos");
				return false;
			}
		}
		
		// Call ReplicateSta11QuartzJob manually
		if (e.getComponent() == _replicaFromTangoBUT) {
			ReplicateSta11QuartzJob replicateSta11 = new ReplicateSta11QuartzJob();
			replicateSta11.replicate();
			_dsArticulos.reset();		
			_dsArticulos.retrieve(_dsQBE);
			if (_dsArticulos.getRowCount() <= 0) { 
				displayErrorMessage("No se encontraron articulos");
				return false;
			}
		}
		
		return super.submitPerformed(e);
	}
}
