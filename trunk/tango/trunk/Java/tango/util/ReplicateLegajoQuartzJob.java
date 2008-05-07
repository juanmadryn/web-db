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
 * Extrae información de proveedores de la tabla CPA01 de Tango
 * y la agrega o actualiza en la tabla entidad_externa y en los
 * atributos corresponientes. 
 * 
 * @author Francisco Paez
 *
 */
public class ReplicateLegajoQuartzJob implements Job {
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaLegajos();
	}

	/**
	 * @throws JobExecutionException
	 */
	/**
	 * @throws JobExecutionException
	 */
	public void importaLegajos() throws JobExecutionException {
		Connection connTango = null;
		Connection connTangoMySQL = null;
		Statement tangoSt = null, stMySql = null;
		PreparedStatement pstMySql = null, pstMySql2 = null;
		ResultSet r = null, rMySql = null;
		
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
			 * Seleccionamos de la tabla legajo los datos básicos para la tabla
			 * entidades_externas
			 */
			String legajoBasicoTangoSQL = 
				"SELECT ID_LEGAJO, NRO_LEGAJO, APELLIDO, NOMBRE, CUIL FROM LEGAJO";			
			tangoSt = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			/**
			 * PreparedStatement para insertar datos en la tabla entidades_externas 
			 */
			String legajoBasicoMySQL = 
				"INSERT INTO tango.LEGAJO " +
				"(ID_LEGAJO, NRO_LEGAJO, APELLIDO, NOMBRE, CUIL) " +
				"VALUES (?,?,?,?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"ID_LEGAJO = values(ID_LEGAJO), NRO_LEGAJO = values(NRO_LEGAJO)," +
				"APELLIDO = values(APELLIDO), NOMBRE = values(NOMBRE)," +
				"CUIL = values(CUIL)";
			pstMySql = connTangoMySQL.prepareStatement(legajoBasicoMySQL);
			
			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(legajoBasicoTangoSQL);			
			while (r.next()) {
				pstMySql.setString(1, r.getString(1)); //ID_LEGAJO --> codigo
				pstMySql.setString(2, r.getString(2)); //NRO_LEGAJO
				pstMySql.setString(3, r.getString(3)); //APELLIDO
				pstMySql.setString(4, r.getString(4)); //NOMBRE
				pstMySql.setString(5, r.getString(5)); //CUIL
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
