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

public class ReplicateSta11QuartzJob implements Job {

	public void replicate() throws DataStoreException, SQLException {
		Connection connTango = null;
		DBConnection connInv = null;
		PreparedStatement pst = null, pst2 = null;
		ResultSet r = null;
		int clase_articulo_inv = 0, clase_articulo_noinv = 0;

		try {
			connTango = getConexionTango();
			connInv = DBConnection.getConnection("inventario", "inventario");

			String SQLclaseArticulo = "SELECT clase_articulo_id FROM clase_articulo WHERE nombre = ?";
			pst2 = connInv.prepareStatement(SQLclaseArticulo);
			pst2.setString(1, "TANGO");
			r = pst2.executeQuery();
			if (r.first()) {
				clase_articulo_inv = r.getInt(1);
				System.out.println(clase_articulo_inv);
			}
			pst2.setString(1, "TANGO_NOINV");
			r = pst2.executeQuery();
			if (r.first()) {
				clase_articulo_noinv = r.getInt(1);
				System.out.println(clase_articulo_noinv);
			}

			String SQLarticulo = "INSERT INTO inventario.articulos " +
					"(clase_articulo_id,nombre,descripcion,clave_externa1,observaciones) " +
					"VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE clase_articulo_id = ?, " +
					"nombre = ?, descripcion = ?, clave_externa1 = ?, observaciones = ?";
			pst2 = connInv.prepareStatement(SQLarticulo);

			String SQLtango = "SELECT COD_ARTICU,DESC_ADIC,DESCRIPCIO,STOCK,STOCK_MAXI FROM STA11";
			pst = connTango
					.prepareStatement(SQLtango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			r = pst.executeQuery();

			String cod_articu = "";
			String desc_adic = "";
			String descripcio = "";
			int stock = 0;
			int stock_maxi = 0;

			// Si el resulset no esta vacio
			if (r.isBeforeFirst()) {
				connInv.beginTransaction();

				while (r.next()) {
					cod_articu = r.getString(1);
					desc_adic = r.getString(2);
					descripcio = r.getString(3);
					stock = r.getInt(4);
					stock_maxi = r.getInt(5);

					if (stock > 0) { // inventariable
						pst2.setInt(1, clase_articulo_inv);
						pst2.setInt(6, clase_articulo_inv);
					} else {
						pst2.setInt(1, clase_articulo_noinv);
						pst2.setInt(6, clase_articulo_noinv);
					}

					pst2.setString(2, descripcio + " [" + cod_articu + "]");
					pst2.setString(7, descripcio + " [" + cod_articu + "]");
					pst2.setString(3, desc_adic);
					pst2.setString(8, desc_adic);
					pst2.setString(4, cod_articu);
					pst2.setString(9, cod_articu);
					pst2.setString(5, "Stock maximo = " + stock_maxi);
					pst2.setString(10, "Stock maximo = " + stock_maxi);

					pst2.executeUpdate();
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
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			if (pst2 != null)
				try {
					pst2.close();
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
