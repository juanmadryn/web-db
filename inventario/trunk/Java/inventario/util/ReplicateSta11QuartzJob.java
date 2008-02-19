package inventario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Dummy class for replicate SQL Server STA11 table contents into MySQL.  
 * 
 * @author Francisco Ezequiel Paez
 */
public class ReplicateSta11QuartzJob implements Job {

	private static final String no_inventariable = "TANGO_NOINV";
	private static final String inventariable = "TANGO";

	/**
	 * Replicate SQL Server STA11 table contents into MySQL.  
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void replicate() throws DataStoreException, SQLException {
		Connection connTango = null;
		DBConnection connInv = null;
		PreparedStatement psTango = null, pstMysql = null;
		ResultSet r = null;
		int clase_articulo_inv = 0, clase_articulo_noinv = 0;

		try {
			connTango = getConexionTango();
			connInv = DBConnection.getConnection("inventario", "inventario");

			// Get the primary keys for setting clase_articulo_id accordingly
			String SQLclaseArticulo = "SELECT clase_articulo_id FROM clase_articulo WHERE nombre = ?";
			pstMysql = connInv.prepareStatement(SQLclaseArticulo);
			pstMysql.setString(1, inventariable);
			r = pstMysql.executeQuery();
			if (r.first()) {
				clase_articulo_inv = r.getInt(1);
				System.out.println(clase_articulo_inv);
			}
			pstMysql.setString(1, no_inventariable);
			r = pstMysql.executeQuery();
			if (r.first()) {
				clase_articulo_noinv = r.getInt(1);
				System.out.println(clase_articulo_noinv);
			}

			String SQLarticulo = "INSERT INTO inventario.articulos " +
					"(clase_articulo_id,nombre,descripcion,descripcion_completa,clave_externa1) VALUES (?, ?, ?, ?, ?) " +
					"ON DUPLICATE KEY UPDATE values(clase_articulo_id), values(nombre)," +
					"values(descripcion), values(descripcion_completa), values(clave_externa1)";
			pstMysql = connInv.prepareStatement(SQLarticulo);

			String SQLtango = "SELECT COD_ARTICU,DESC_ADIC,DESCRIPCIO,STOCK,STOCK_MAXI FROM STA11";
			psTango = connTango
					.prepareStatement(SQLtango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			r = psTango.executeQuery();

			String cod_articu = "";
			String desc_adic = "";
			String descripcio = "";
			int stock = 0;
			
			// If the resulset is not empty
			if (r.isBeforeFirst()) {
				connInv.beginTransaction();

				while (r.next()) {
					cod_articu = r.getString(1);
					desc_adic = r.getString(2);
					descripcio = r.getString(3);
					stock = r.getInt(4);					

					pstMysql.setInt(1, (stock > 0) ? clase_articulo_inv : clase_articulo_noinv );
					pstMysql.setString(2, cod_articu);					
					pstMysql.setString(3, descripcio);					
					pstMysql.setString(3, desc_adic);
					pstMysql.setString(5, cod_articu);
					
					pstMysql.executeUpdate();
				}

				connInv.commit();
			}
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			}
			if (psTango != null)
				try {
					psTango.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			if (pstMysql != null)
				try {
					pstMysql.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			if (connTango != null) {
				try {
					connTango.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connInv != null) {
				connInv.rollback();
				connInv.freeConnection();
			}
		}

	}

	public Connection getConexionTango() throws DataStoreException {
		Connection connTango = null;

		Props p = Props.getProps("inventario", null);
		String driverTango = p.getProperty("driverTango");
		String urlTango = p.getProperty("urlTango");
		String userTango = p.getProperty("userTango");
		String passWordTango = p.getProperty("passWordTango");

		try {
			// Se carga el driver JTDS (JDBC-ODBC si no es encontrado)
			Class.forName(driverTango);
		} catch (ClassNotFoundException e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException(
					"Imposible cargar el driver para Tango: " + e.getMessage());
		}

		try {
			// Se establece la conexión con la base de datos
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);
		} catch (Exception e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException(
					"imposible establecer conexión con la base tango: "
							+ e.getMessage());
		}
		return connTango;
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.replicate();
		} catch (DataStoreException e) {
			MessageLog.writeErrorMessage(e, null);
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		}
	}
}
