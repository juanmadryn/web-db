//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.AtributosEntidadModel;
import inventario.util.ReplicateSta11QuartzJob;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.QBEBuilder;
import com.salmonllc.util.SalmonDateFormat;

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
			_dsArticulos.retrieve(_dsQBE);
			if (_dsArticulos.getRowCount() <= 0) { 
				displayErrorMessage("No se encontraron articulos");
				return false;
			}
		}
		
		return super.submitPerformed(e);
	}
	
	private String armarCriterio() throws SQLException, DataStoreException {
		StringBuilder sb = new StringBuilder(500);
		
		// add all the selection criterias specified by the user
		String sqlFilter = _dsQBE.generateSQLFilter(_dsArticulos);		
		if (sqlFilter != null) {
			sb.append(sqlFilter);
		}
		
		// build where clause with specified attributes if any
		String criterioAtributos = armarBusquedaPorAtributos();
		if (criterioAtributos.length() > 0) {
			sb.append(" and articulos.articulo_id IN ( ").append(criterioAtributos).append(" )");
			System.out.println(armarBusquedaPorAtributos2());
		}
			
		return sb.toString();
	}	
	
	private String armarBusquedaPorAtributos() throws SQLException {
		StringBuilder querySql = new StringBuilder(500);

		// get the attributes
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
				

		// si se especifico al menos un atributo
		if (atributos.size() > 0) {
			querySql.append(" SELECT objeto_id "
					+ " FROM infraestructura.atributos_entidad " 
					+ " WHERE tipo_objeto = 'TABLA' AND nombre_objeto = 'articulos' "
					+ " AND ( ");
			
			Iterator<Integer> i = atributos.keySet().iterator();
			
			// completamos el query con pares atributo valor
			while (i.hasNext()) {
				int atributoId = i.next();
				String valorAtributo = atributos.get(atributoId);
					
				String tipoAtributo = AtributosEntidadModel.getTipoAtributo(atributoId);
				
				if (tipoAtributo == null) throw new RuntimeException("Atributo inexistente");
				
				String sqlClause = null;
				
				try {
					if ("entero".equalsIgnoreCase(tipoAtributo)) {
						sqlClause = "valor_entero = "
								+ Integer.parseInt(valorAtributo);
					} else if ("real".equalsIgnoreCase(tipoAtributo)) {
						sqlClause = "valor_real = "
								+ Float.parseFloat(valorAtributo);
					} else if ("fecha".equalsIgnoreCase(tipoAtributo)) {
						SalmonDateFormat sdf = new SalmonDateFormat();
						sqlClause = "valor_fecha = '"
								+ new java.sql.Date(sdf.parse(
										(String) valorAtributo).getTime())
										.toString() + "'";
					} else if ("logico".equalsIgnoreCase(tipoAtributo)) {
						if (valorAtributo.equalsIgnoreCase("V")
								|| valorAtributo.equalsIgnoreCase("F"))
							sqlClause = "valor_logico = '" + valorAtributo
									+ "'";
						else
							throw new RuntimeException(
									"Debe introducir 'V' para verdadero o 'F' para falso para el atributo");
					} else {
						sqlClause = "valor LIKE '%" + valorAtributo + "%'";
					}
				} catch (NumberFormatException e) {
					throw new RuntimeException("Valor de atributo númerico incorrecto");
				} catch (ParseException e) {
					throw new RuntimeException("Valor de atributo fecha con formato incorrecto");
				}
			
				// conector
				querySql.append(sqlClause);				
				if (i.hasNext()) 
					querySql.append(" OR ");
				
			}	
			
			querySql.append(" ) ");
		}		
		return querySql.toString();
	}
	
	private String armarBusquedaPorAtributos2() throws SQLException {
		StringBuilder querySql = new StringBuilder(500);
		StringBuilder innerJoinSql = new StringBuilder(500);
		StringBuilder whereClauseSql = new StringBuilder(500);

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
		
		// si se especifico al menos un atributo
		if (atributos.size() > 0) {
			querySql.append("SELECT articulos.articulo_id FROM articulos articulos ");
			whereClauseSql.append(" where (");
			
			Iterator<Integer> i = atributos.keySet().iterator();
			int count = 1;
			
			while (i.hasNext()) {
				int atributoId = i.next();
				String valorAtributo = atributos.get(atributoId);
				
				String tabla = "atributos_entidad" + count;
				innerJoinSql.append(						
						" left outer join infraestructura.atributos_entidad " + tabla +  
						" ON " + tabla + ".objeto_id = articulos.articulo_id AND " + tabla + ".atributo_id = ")
						.append(atributoId);
				
				String tipoAtributo = AtributosEntidadModel.getTipoAtributo(atributoId);				
				if (tipoAtributo == null) throw new RuntimeException("Atributo inexistente");
				
				String sqlClause = null;
				
				try {
					if ("entero".equalsIgnoreCase(tipoAtributo)) {
						sqlClause = "valor_entero = "
								+ Integer.parseInt(valorAtributo);
					} else if ("real".equalsIgnoreCase(tipoAtributo)) {
						sqlClause = "valor_real = "
								+ Float.parseFloat(valorAtributo);
					} else if ("fecha".equalsIgnoreCase(tipoAtributo)) {
						/*SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");						
						sqlClause = "valor_fecha = '"
							+ new java.sql.Date(df.parse(
									(String) valorAtributo).getTime())
									.toString() + "'";					*/	
						SalmonDateFormat sdf = new SalmonDateFormat();
						sqlClause = "valor_fecha = '"
								+ new java.sql.Date(sdf.parse(
										(String) valorAtributo).getTime())
										.toString() + "'";
					} else if ("logico".equalsIgnoreCase(tipoAtributo)) {
						if (valorAtributo.equalsIgnoreCase("V")
								|| valorAtributo.equalsIgnoreCase("F"))
							sqlClause = "valor_logico = '" + valorAtributo
									+ "'";
						else
							throw new RuntimeException(
									"Debe introducir 'V' para verdadero o 'F' para falso para el atributo");
					} else {
						sqlClause = "valor LIKE '%" + valorAtributo + "%'";
					}
				} catch (NumberFormatException e) {
					throw new RuntimeException("Valor de atributo númerico incorrecto");
				} catch (ParseException e) {
					throw new RuntimeException("Valor de atributo fecha con formato incorrecto");
				}
				
				if (sqlClause != null) {
					whereClauseSql.append(tabla + "." + sqlClause);
				}
			
				if (i.hasNext()) {
					whereClauseSql.append(" AND ");
					count++;
				} else {
					whereClauseSql.append(")");
				}
			}
			
			querySql.append(innerJoinSql).append(whereClauseSql);
		}		
		
		return querySql.toString();
	}

}
