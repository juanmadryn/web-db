package inventario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Extrae información de condiciones de compra de la tabla CPA01 de Tango
 * y la agrega o actualiza en la tabla correspondiente en MySQL
 * 
 * @author Francisco Paez
 */
public class ReplicateCpa49QuartzJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub		
	}
	
	/**
	 * @throws JobExecutionException
	 */
	public void importaCondicionesCompra() throws JobExecutionException {
		Connection connTango = null;
		Connection connInv = null;
		Statement tangoSt = null, stMySql = null;
		PreparedStatement pstMySql = null, pstMySql2 = null;
		ResultSet r = null;
		
		String driverTango = "net.sourceforge.jtds.jdbc.Driver";
		String urlTango="jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
		String userTango="Axoft";
		String passWordTango="Axoft";

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);			
			// Conexion con Tango (SQL Server 2000)
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);				
			// Conexion con MySQL
			Class.forName("com.mysql.jdbc.Driver");
			connInv = DriverManager.getConnection ("jdbc:mysql://localhost:3306/inventario","inventario", "inventario");			
			connInv.setAutoCommit(false);
			
			/**
			 * Seleccionamos de la tabla CPA01 los datos básicos para la tabla
			 * entidades_externas
			 */
			String condicionesCompraTangoSQL = 
				"SELECT COD_CONDIC, DESC_CONDI  FROM CPA49 " +
				"GROUP BY COD_CONDIC, DESC_CONDI ORDER BY COD_CONDIC ASC";				
			tangoSt = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			/**
			 * PreparedStatement para insertar datos en la tabla condiciones_compra 
			 */
			String condicionesCompraMySQL = 
				"INSERT INTO inventario.condiciones_compra " +
				"(nombre,descripcion) " +
				"VALUES (?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"nombre = values(nombre), descripcion = values(descripcion)";
			pstMySql = connInv.prepareStatement(condicionesCompraMySQL);
			
			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(condicionesCompraTangoSQL);			
			while (r.next()) {
				pstMySql.setString(1, r.getString(1)); //COD_CONDIC --> nombre
				pstMySql.setString(2, r.getString(2)); //DESC_CONDI	--> descripcion
				pstMySql.executeUpdate();				
			}			
			r.close();		
			
			connInv.commit();
			
		} catch (ClassNotFoundException e) {
			throw new JobExecutionException(e);
		} catch (SQLException e) {
			throw new JobExecutionException(e);
		} finally {
			try {
				if (r != null)				
					r.close();				
				if (tangoSt != null)
					tangoSt.close();
				if (stMySql != null)
					stMySql.close();
				if (pstMySql != null)
					pstMySql.close();
				if (pstMySql2 != null)
					pstMySql2.close();
				if (connTango != null)
					connTango.close();
				if (connInv != null) {
					connInv.rollback();
					connInv.close();
				}
			} catch (SQLException e) {
				throw new JobExecutionException(e);				
			}
		}
	}
}
