package tango.util;

import infraestructura.reglasNegocio.ValidationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.properties.Props;


/**
 * Extrae información de legajos de la tabla LEGAJO de Tango
 * y la agrega o actualiza en la tabla LEGAJO
 * 
 * @author Francisco Paez
 *
 */
public class ReplicateLegajoQuartzJob implements Job {

	private String driverTango = "net.sourceforge.jtds.jdbc.Driver";
	private String urlTango = null;
	private String userTango = null;
	private String passWordTango = null;
	private String dbTango = null;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaLegajos();
	}

	/**
	 * @throws JobExecutionException
	 */
	public void importaLegajos() throws JobExecutionException {
		Connection connTango = null;
		Connection connTangoMySQL = null;
		Statement tangoSt = null, stMySql = null;
		PreparedStatement pstMySql = null, pstMySql2 = null;
		ResultSet r = null;
		getConnectionInfo();
		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);			
			// Conexion con Tango (SQL Server 2000)
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);				
			// Conexion con MySQL
			Class.forName("com.mysql.jdbc.Driver");			
			connTangoMySQL = DriverManager.getConnection (Props.getProps("tango", null).getProperty("tango.DBURL"),"tango", "tango");			
			connTangoMySQL.setAutoCommit(false);
			
			/**
			 * Seleccionamos de la tabla legajo los datos básicos para la tabla legajos
			 */
			String legajoBasicoTangoSQL = 
				"SELECT ID_LEGAJO, NRO_LEGAJO, APELLIDO, NOMBRE, CUIL FROM LEGAJO";			
			tangoSt = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			/**
			 * PreparedStatement para insertar datos en la tabla legajos
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
	/**
	 * Obtiene parametros para la conexión desde el archivo de propiedades
	 */
	private void getConnectionInfo() {
		Props props = Props.getProps("tango", null);
		Vector<String> errores = new Vector<String>();
		
		String driverTango = props.getProperty("driverTango");
		if (driverTango == null) 
			errores.add("OrdenesDeCompraTANGO.getConnectionInfo(): No se ha indicado la propiedad 'driverTango' en archivo de configuración");

		String urlTango = props.getProperty("urlTango");
		if (urlTango == null) 
			errores.add("OrdenesDeCompraTANGO.getConnectionInfo(): No se ha indicado la propiedad 'urlTango' en archivo de configuración");

		String usrTango = props.getProperty("userTango");
		if (usrTango == null) 
			errores.add("OrdenesDeCompraTANGO.getConnectionInfo(): No se ha indicado la propiedad 'usrTango' en archivo de configuración");

		String passWordTango = props.getProperty("passWordTango");
		if (passWordTango == null) 
			errores.add("OrdenesDeCompraTANGO.getConnectionInfo(): No se ha indicado la propiedad 'passWordTango' en archivo de configuración");
		
		String dbTango = props.getProperty("dbTango");
		if (dbTango == null) 
			errores.add("OrdenesDeCompraTANGO.getConnectionInfo(): No se ha indicado la propiedad 'dbTango' en archivo de configuración");
		
		if (errores.size() > 0) 
			throw new ValidationException(errores);
		
		this.driverTango = driverTango;
		this.urlTango = urlTango;
		this.userTango = usrTango;
		this.passWordTango = passWordTango;
		this.dbTango = dbTango;
	}
}
