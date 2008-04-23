package infraestructura.utils;

import infraestructura.models.AtributosEntidadModel;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Iterator;

import com.salmonllc.util.SalmonDateFormat;

/**
 * 
 * 
 * @author fep
 *
 */
public class BusquedaPorAtributo {
	
	public static final int OPERATOR_AND = 1;
	public static final int OPERATOR_OR = 2;
	
	private static String AND_OPERATOR = "AND";
	private static String OR_OPERATOR = "OR";
	
	/**
	 * Dado un código de operador retorna el nombre de este
	 * 
	 * @param operator código del operador
	 * @return nombre del operador
	 */
	private static String getOperator(int operator) {
		switch (operator) {
			case OPERATOR_AND:
				return AND_OPERATOR;
			case OPERATOR_OR:
				return OR_OPERATOR;
			default:
				return null;
		}
	}
	
	/**
	 * Construye dinamicamente una query retornando los identificadores de objetos
	 * que satisfacen las condiciones de igualdad sobre algunos o todos de los valores
	 * contenidos en el Hashtable atributos.
	 * 
	 * @param atributos Hashtable con pares código de atributo / valor esperado
	 * @param operator OPERATOR_AND (satisfacer todos los atributos) o OPERATOR_OR (satisfacer uno o más) 
	 * @param tabla tabla sobre la que aplicar la busqueda
	 * @param columna columna con los valoresa  retornar
	 * @return un SELECT apto para utilizar en un clausula IN
	 * @throws SQLException
	 */
	public static String armarBusquedaPorAtributos(
			Hashtable<Integer, String> atributos, int operator, String tabla,
			String columna) throws SQLException {
		
		StringBuilder querySql = new StringBuilder(500);
		StringBuilder innerJoinSql = new StringBuilder(500);
		StringBuilder whereClauseSql = new StringBuilder(500);
		
		String tablaColumna = tabla + "." + columna;

		// si se especifico al menos un atributo
		if (atributos.size() > 0) {
			querySql.append("SELECT " + tablaColumna + " FROM " + tabla + " " + tabla + " ");
			whereClauseSql.append(" where (");
			
			Iterator<Integer> i = atributos.keySet().iterator();
			int count = 1;
			
			while (i.hasNext()) {
				int atributoId = i.next();
				String valorAtributo = atributos.get(atributoId);
				
				String innerTabla = "atributos_entidad" + count;
				innerJoinSql.append(						
						" left outer join infraestructura.atributos_entidad " + innerTabla +  
						" ON " + innerTabla + ".objeto_id = " + tablaColumna + " AND " + innerTabla + ".atributo_id = ")
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
						SalmonDateFormat sdf = new SalmonDateFormat("dd/MM/yyyy");
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
					whereClauseSql.append(innerTabla + "." + sqlClause);
				}
			
				if (i.hasNext()) {
					whereClauseSql.append(" " + getOperator(operator) + " ");
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
