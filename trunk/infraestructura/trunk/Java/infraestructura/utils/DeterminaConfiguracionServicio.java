/*
 * DeterminaConfiguracionServicio.java
 *
 * Created on 25-oct-2007, 16:26:33
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package infraestructura.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

/**
 * 
 * @author demian
 */

public class DeterminaConfiguracionServicio {

	/**
	 * Determina la configuraci√≥n seg√∫n los atributos recuperados del mensaje
	 */

	public static int determinaConfiguracion(Iterator<AtributoConfiguracion> p_atributos) throws SQLException{

			/* declara variables locales */
			
			AtributoConfiguracion atributo;
		
			String sqlPositivo = " SELECT * FROM configuracion c WHERE c.configuracion_id IN"
					+ "     (SELECT configuracion_id"
					+ "       FROM atributos_configuracion as t1"
					+ "       WHERE (";
			String sqlNegativo = "AND NOT EXISTS (SELECT configuracion_id"
					+ "                           FROM atributos_configuracion as t2"
					+ "                           WHERE t2.configuracion_id = t1.configuracion_id"
					+ "                               AND (";
			String sql = "";
			Boolean primero = true;

			for (; p_atributos.hasNext();) {
				atributo = p_atributos.next();

				if (primero) {
					primero = false;
				} else {
					sqlPositivo = sqlPositivo + " OR ";
					sqlNegativo = sqlNegativo + " OR ";
				}

				/* por cada atributo va armando el sql din√°mico */
				sqlPositivo = sqlPositivo + "( atributo_id = "
						+ atributo.getAtributo_id() + " AND " + atributo.getAtributo_valor()+" BETWEEN valor AND valor_hasta";
				sqlNegativo = sqlNegativo + "( atributo_id = "
				+ atributo.getAtributo_id() + " AND " + atributo.getAtributo_valor()+" NOT BETWEEN valor AND valor_hasta";
			}	
			
			/* arma el query definitivo */
			sqlPositivo = sqlPositivo + ")";
			sqlNegativo = sqlNegativo + "))";
			sql = sqlPositivo + sqlNegativo
					+ "))) ORDER BY cardinalidad desc, prioridad desc";

			DBConnection conexion = null;
			Statement st = null;
			ResultSet result = null;

			// recupero todos los posibles atributos para un proyecto y recorro
			// buscando si ya existe o no
			// si no existe lo inserto en el data store
			try {
				conexion = DBConnection.getConnection("infraestructura",
						"infraestructura");
				conexion.beginTransaction();
				st = conexion.createStatement();
				result = st.executeQuery(sql);
				
				/*
				 * retorna el ID de la primera de las configuraciones recuperadas
				 * --> la de mayor cardinalidad, mayor prioridad
				 */
				
				if (result != null && result.first())
					return result.getInt("configuracion_id");
				else
					return -1;
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				// adem·s de escribir en el log mando mensaje a la p·gina				
				throw new SQLException(
						"Error obteniendo lista de configuraciones posibles: "+ e.getMessage(), e);
			} finally {
				if (st != null)
					st.close();					
				if (conexion != null)
					conexion.freeConnection();
			}
			
			
		}
	}	

