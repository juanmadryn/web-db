package infraestructura.utils;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

/*  
 * This class contains utilities needed for normal execution of application
 */

public class Utilities {	
	
	/**
	 * @param user_id
	 * @return
	 */
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
	
	
	/**
	 * @param user_id
	 * @return
	 */
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
	
	/**
	 * Calcula el número de Solicitudes de Compra (Materiales) que han sido cotizadas
	 * @param user_id
	 * @return
	 */
	public static int getSolicitudesCompraCotizadas(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int scCotizadas = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");
				
			String SQL = "SELECT count(*) " +
					"FROM  solicitudes_compra solicitudes_compra " +					
					"WHERE solicitudes_compra.estado LIKE '0006.0008'";
			
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				scCotizadas = r.getInt(1);				
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

		return scCotizadas;
	}
	
	/**
	 * Calcula el número de Ordenes de Compra que un usuario tiene pendientes para aprobar
	 * @param user_id Id del usuario encargado de aprobar la OC
	 * @return Número de Ordenes de Compra que el usuario tiene pendientes para aprobar
	 */
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
	
	/**
	 * Calcula el número de Ordenes de Compra de un usuario esperando su revision 
	 * @param user_id Id del usuario que genero las OC 
	 * @return Número de Ordenes de Compra que el usuario tiene pendientes para revision
	 */
	public static int getOrdenesCompraPendientesObservacion(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int solicitudes_pendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");

			String SQL = "SELECT count(*) " +
					"FROM  ordenes_compra oc WHERE oc.user_id_comprador ="+user_id+" " +
							"AND oc.estado LIKE '0008.0005'";
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
	
	/**
	 * 
	 * 
	 * @return
	 */
	public static DecimalFormat getDecimalFormat() {
		DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setRoundingMode(RoundingMode.HALF_UP);
		DecimalFormatSymbols decimalSymbol = DecimalFormatSymbols
				.getInstance();
		decimalSymbol.setDecimalSeparator('.');
		decimalSymbol.setGroupingSeparator(',');
		format.setDecimalFormatSymbols(decimalSymbol);
		
		return format;
	}
	
	/** 
	 * Veras este codigo en tus pesadillas...
	 * 
	 * @param numero
	 * @return
	 */
	public static String getDecimalFormatNumber(float numero) {
		DecimalFormat format = getDecimalFormat();
		return format.format(numero).replace(",", "");
	}
	
	/**
	 * Calcula el número de comprobantes de movimientos de artículos 
	 * que se encuentran pendientes de confirmación
	 * @param user_id
	 * @return
	 */
	public static int getComprobantesMovimientosArticulosPendientes() {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int compPendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");
				
			String SQL = "SELECT count(*) " +
					"FROM  comprobante_movimiento_articulo c " +					
					"WHERE c.estado LIKE '%.0002' AND c.tipo_movimiento_articulo_id <>1";
			
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				compPendientes = r.getInt(1);				
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
		return compPendientes;
	}
}
