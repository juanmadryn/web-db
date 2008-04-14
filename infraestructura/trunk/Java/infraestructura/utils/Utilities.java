package infraestructura.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

/*  
 * This class contains utilities needed for normal execution of application
 */

public class Utilities {
	
	
	public static int getSolicitudesCompraPendientesAprobacion(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int solicitudes_pendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");

			String SQL = "SELECT count(*) " +
					"FROM  solicitudes_compra solicitudes_compra WHERE solicitudes_compra.solicitud_compra_id IN " +
					"(SELECT objeto_id AS solicitud_compra_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante ="
					+ user_id + " AND i.nombre_objeto='solicitudes_compra') AND solicitudes_compra.estado LIKE '0006.0002'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				solicitudes_pendientes = r.getInt(1);				
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
		return solicitudes_pendientes;
	}
	
	public static int getSolicitudesCompraPendientesObservacion(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int solicitudes_pendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");

			String SQL = "SELECT count(*) " +
					"FROM  solicitudes_compra solicitudes_compra WHERE solicitudes_compra.user_id_solicita ="+user_id+" AND solicitudes_compra.estado LIKE '0006.0005'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				solicitudes_pendientes = r.getInt(1);				
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
		return solicitudes_pendientes;
	}
	
	public static int getOrdenesCompraPendientesAprobacion(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int solicitudes_pendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");

			String SQL = "SELECT count(*) " +
					"FROM  ordenes_compra oc " +
					"LEFT OUTER JOIN infraestructura.estados e ON oc.estado = e.estado  " +
					"LEFT OUTER JOIN infraestructura.website_user wuc ON oc.user_id_comprador = wuc.user_id  " +
					"WHERE oc.orden_compra_id IN " +
					"(SELECT objeto_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante ="
					+ user_id + " AND nombre_objeto='ordenes_compra') AND oc.estado LIKE '0008.0002'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				solicitudes_pendientes = r.getInt(1);				
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
		return solicitudes_pendientes;
	}
	
}
