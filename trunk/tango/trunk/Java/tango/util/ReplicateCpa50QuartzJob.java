package tango.util;

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
 * Extrae información de legajos de la tabla LEGAJO de Tango
 * y la agrega o actualiza en la tabla LEGAJO
 * 
 * @author Francisco Paez
 *
 */
public class ReplicateCpa50QuartzJob implements Job {
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaCompradores();
	}

	/**
	 * @throws JobExecutionException
	 */
	public void importaCompradores() throws JobExecutionException {
		Connection connTango = null;
		Connection connTangoMySQL = null;
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
			connTangoMySQL = DriverManager.getConnection ("jdbc:mysql://localhost:3306/tango","tango", "tango");			
			connTangoMySQL.setAutoCommit(false);
			
			/**
			 * Seleccionamos de la tabla legajo los datos básicos para la tabla legajos
			 */
			String cpa50TangoSQL = 
				"SELECT [ID_CPA50], [FILLER], [COD_COMPRA], [NOM_COMPRA] FROM [FABRI_S.A.].[dbo].[CPA50]";			
			tangoSt = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			/**
			 * PreparedStatement para insertar datos en la tabla legajos
			 */
			String cpa50MySQL = 
				"INSERT INTO tango.cpa50 " +
				"(ID_CPA50, FILLER, COD_COMPRA, NOM_COMPRA) " +
				"VALUES (?,?,?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"ID_CPA50 = values(ID_CPA50), FILLER = values(FILLER)," +
				"COD_COMPRA = values(COD_COMPRA), NOM_COMPRA = values(NOM_COMPRA)";
			pstMySql = connTangoMySQL.prepareStatement(cpa50MySQL);
			
			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(cpa50TangoSQL);			
			while (r.next()) {
				pstMySql.setString(1, r.getString(1)); // ID_CPA50
				pstMySql.setString(2, r.getString(2)); // FILLER
				pstMySql.setString(3, r.getString(3)); // COD_COMPRA
				pstMySql.setString(4, r.getString(4)); // NOM_COMPRA
				pstMySql.executeUpdate();				
			}			
			r.close();
			
			connTangoMySQL.commit();
			
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
				if (connTangoMySQL != null) {
					connTangoMySQL.rollback();
					connTangoMySQL.close();
				}
			} catch (SQLException e) {
				throw new JobExecutionException(e);				
			}
		}
	}
}
