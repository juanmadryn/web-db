//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.utils.BusquedaPorAtributo;
import inventario.util.ReplicateSta11QuartzJob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;
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
	
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP1;
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP2;
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP3;
	public com.salmonllc.html.HtmlTextEdit _valorAttr1;
	public com.salmonllc.html.HtmlTextEdit _valorAttr2;
	public com.salmonllc.html.HtmlTextEdit _valorAttr3;

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
	public com.salmonllc.html.HtmlSubmitButton _limpiarBUT;
	public com.salmonllc.html.HtmlDropDownList _operador;
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		_buscarBUT = new HtmlSubmitButton("buscarBUT","Buscar",this);
		_buscarBUT.setAccessKey("b");
		_buscarBUT.addSubmitListener(this);
		_searchformdisplaybox1.addButton(_buscarBUT);
		
		_limpiarBUT = new HtmlSubmitButton("limpiarBUT","Limpiar",this);
		_limpiarBUT.setAccessKey("b");		
		_limpiarBUT.addSubmitListener(this);
		_searchformdisplaybox1.addButton(_limpiarBUT);
		
		// Obtiene operadores 
		_operador.addOption(String.valueOf(BusquedaPorAtributo.OPERATOR_AND), "And");
		_operador.addOption(String.valueOf(BusquedaPorAtributo.OPERATOR_OR), "Or");
		
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
			try {			
				_dsArticulos.reset();		
				_dsArticulos.retrieve(armarCriterio());
				if (_dsArticulos.getRowCount() <= 0) { 
					displayErrorMessage("La búsqueda no ha retornado resultados");
					return false;
				}
			} catch (RuntimeException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}
		
		// clear button
		if (e.getComponent() == _limpiarBUT) {
			_lkpAttrINP1.setValue(null);
			_lkpAttrINP2.setValue(null);
			_lkpAttrINP3.setValue(null);
			_valorAttr1.setValue(null);
			_valorAttr2.setValue(null);
			_valorAttr3.setValue(null);
		}
		
		// Call ReplicateSta11QuartzJob manually
		if (e.getComponent() == _replicaFromTangoBUT) {
			ReplicateSta11QuartzJob replicateSta11 = new ReplicateSta11QuartzJob();
			replicateSta11.replicate();
			_dsArticulos.reset();		
			_dsArticulos.retrieve(armarCriterio());
			if (_dsArticulos.getRowCount() <= 0) { 
				displayErrorMessage("No se encontraron articulos");
				return false;
			}
		}
		
		return super.submitPerformed(e);
	}
	
	/**
	 * Genera el query SQL segùn los datos ingresados por el usuario en los campos asociados
	 * al QBE y los pares atributo/valor ingresados.
	 * @return La clausula WHERE a utilizar como parametro de retrieve() 
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	private String armarCriterio() throws SQLException, DataStoreException {
		StringBuilder sb = new StringBuilder(500);
		
		// add all the selection criterias specified by the user
		String sqlFilter = _dsQBE.generateSQLFilter(_dsArticulos);		
		if (sqlFilter != null) {
			sb.append(sqlFilter);
		}
		
		// build where clause with specified attributes if any
		String criterioAtributos = BusquedaPorAtributo.armarBusquedaPorAtributos(atributosValoresBusqueda(),
				Integer.valueOf(_operador.getValue()), "articulos",
				"articulo_id");
		if (criterioAtributos.length() > 0) {
			sb.append(" and articulos.articulo_id IN ( ").append(criterioAtributos).append(" )");
			System.out.println(criterioAtributos);
		}
			
		return sb.toString();
	}	
	
	/**
	 * Colecta en un hashtable los pares atributo/valor ingreados por el usuario para la bùsqueda
	 * 
	 * TODO: 
	 * Al usar HashTable no se permiten claves duplicadas, por lo que no se puede buscar por
	 * multiples valores correspondientes a un solo atributo. Reemplazar por unas soluciòn 
	 * "multimap" usando Map<key,List<value>> o un multimap de Apache Commons. 
	 * 
	 * La cantidad de pares atributo/valor esta limitada al numero de componentes visuales 
	 * agregados a tal efecto. Generar estos componentes dinamicamente.
	 * 
	 * @return hashtable
	 */
	private Hashtable<Integer,String> atributosValoresBusqueda() {
		// atributos y valores ingresados por el usuario
		Hashtable<Integer,String> atributos = new Hashtable<Integer,String>();
		if ((_lkpAttrINP1.getValue() != null) && (_valorAttr1.getValue() != null))
			atributos.put(Integer.parseInt(_lkpAttrINP1.getValue()),
					_valorAttr1.getValue());
		if ((_lkpAttrINP2.getValue() != null) && (_valorAttr2.getValue() != null))
			atributos.put(Integer.parseInt(_lkpAttrINP2.getValue()),
					_valorAttr2.getValue());
		if ((_lkpAttrINP3.getValue() != null) && (_valorAttr3.getValue() != null))
			atributos.put(Integer.parseInt(_lkpAttrINP3.getValue()),
					_valorAttr3.getValue());		
		
		return atributos;
	}
}
