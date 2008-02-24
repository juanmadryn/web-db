package inventario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Dummy class for replicate SQL Server STA11 table contents into MySQL.  
 * 
 * @author Francisco Ezequiel Paez
 */
public class ReplicateSta11QuartzJob implements Job {

	private static final String claseTango = "ARTICULO TANGO";

	/**
	 * Replicate SQL Server STA11 table contents into MySQL.  
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void replicate() throws SQLException, ClassNotFoundException {
		Connection connTango = null;
		Connection connInv = null;
		PreparedStatement psTango = null, pstMysql = null;
		ResultSet r = null;
		int clase_articulo_inv = 1;
		
		String driverTango = "net.sourceforge.jtds.jdbc.Driver";
		String urlTango="jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
		String userTango="Axoft";
		String passWordTango="Axoft";

		try {
			// Se carga el driver JTDS (JDBC-ODBC si no es encontrado)
			Class.forName(driverTango);
			// Se establece la conexión con la base de datos
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);				
			// Now attempt to create a database connection with MySQL
			Class.forName("com.mysql.jdbc.Driver");
			connInv = DriverManager.getConnection ("jdbc:mysql://localhost:3306/inventario", 
					"inventario", "inventario");
			
			// Get the primary keys for setting clase_articulo_id accordingly
			String SQLclaseArticulo = "SELECT clase_articulo_id FROM clase_articulo WHERE nombre = ?";
			pstMysql = connInv.prepareStatement(SQLclaseArticulo);
			pstMysql.setString(1, claseTango);
			r = pstMysql.executeQuery();
			if (r.first()) {
				clase_articulo_inv = r.getInt(1);
			}

			String SQLarticulo = "INSERT INTO inventario.articulos " +
					"(clase_articulo_id,nombre,descripcion,descripcion_completa,clave_externa1) VALUES (?, ?, ?, ?, ?) " +
					"ON DUPLICATE KEY UPDATE clase_articulo_id = values(clase_articulo_id), nombre = values(nombre)," +
					"descripcion = values(descripcion), descripcion_completa = values(descripcion_completa), " +
					"clave_externa1 = values(clave_externa1)";
			pstMysql = connInv.prepareStatement(SQLarticulo);

			String SQLtango = "SELECT COD_ARTICU,DESC_ADIC,DESCRIPCIO,STOCK FROM STA11";
			psTango = connTango.prepareStatement(SQLtango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			r = psTango.executeQuery();			

			String cod_articu = "";
			String desc_adic = "";
			String descripcio = "";
			connInv.setAutoCommit(false);

			// If the resulset is not empty
			if (r.isBeforeFirst()) {				
				while (r.next()) {					
					cod_articu = r.getString(1);
					desc_adic = r.getString(2);
					descripcio = r.getString(3);
					
					pstMysql.setInt(1, clase_articulo_inv);
					pstMysql.setString(2, cod_articu);					
					pstMysql.setString(3, descripcio);					
					pstMysql.setString(4, desc_adic);
					pstMysql.setString(5, cod_articu);
					
					pstMysql.executeUpdate();					
				}
				connInv.commit();
			}
		} finally {
			if (r != null)
				r.close();
			if (psTango != null)
				psTango.close();
			if (pstMysql != null)
				pstMysql.close();
			if (connTango != null)
				connTango.close();
			if (connInv != null) {
				connInv.rollback();
				connInv.close();
			}
		}

	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.replicate();
		} catch (SQLException e) {
			e.printStackTrace();
			MessageLog.writeErrorMessage(e, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			MessageLog.writeErrorMessage(e, null);
		}
	}
}
