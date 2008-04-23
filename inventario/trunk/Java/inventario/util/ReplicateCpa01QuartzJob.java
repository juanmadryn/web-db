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
 * Extrae información de proveedores de la tabla CPA01 de Tango
 * y la agrega o actualiza en la tabla entidad_externa y en los
 * atributos corresponientes. 
 * 
 * @author Francisco Paez
 *
 */
public class ReplicateCpa01QuartzJob implements Job {

	/**
	 * @throws JobExecutionException
	 */
	public void importaProveedores() throws JobExecutionException {
		Connection connTango = null;
		Connection connInf = null;
		Statement tangoSt = null, pstMySql2 = null;
		PreparedStatement pstMySql = null;
		ResultSet r = null, rMySql = null;
		
		String driverTango = "net.sourceforge.jtds.jdbc.Driver";
		String urlTango="jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
		String userTango="Axoft";
		String passWordTango="Axoft";
		
		try {
			// Conexion con Tango (SQL Server 2000) y carga driver JTDS
			Class.forName(driverTango);
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);				
			// Conexion con MySQL
			Class.forName("com.mysql.jdbc.Driver");
			connInf = DriverManager.getConnection ("jdbc:mysql://localhost:3306/infraestructura","root", "root");			
			connInf.setAutoCommit(false);
			
			/**
			 * Seleccionamos de la tabla CPA01 los datos básicos para la tabla
			 * entidades_externas
			 */
			String proveedoresBasicoTangoSQL = 
				"SELECT COD_PROVEE,NOM_PROVEE,ORDEN,OBSERVACIO FROM CPA01";			
			tangoSt = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			/**
			 * PreparedStatement para insertar datos en la tabla entidades_externas 
			 */
			String proveedoresBasicoMySQL = 
				"INSERT INTO infraestructura.entidad_externa " +
				"(codigo,nombre,descripcion,observaciones,activo,anulado) " +
				"VALUES (?,?,?,?,'V','F') " +
				"ON DUPLICATE KEY UPDATE " +
				"codigo = values(codigo), nombre = values(nombre)," +
				"descripcion = values(descripcion), observaciones = values(observaciones)";
			pstMySql = connInf.prepareStatement(proveedoresBasicoMySQL);
			
			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(proveedoresBasicoTangoSQL);			
			while (r.next()) {
				pstMySql.setString(1, r.getString(1)); //COD_PROVEE --> codigo
				pstMySql.setString(2, r.getString(2)); //NOM_PROVEE --> nombre
				pstMySql.setString(3, r.getString(3)); //ORDEN		--> descripcion
				pstMySql.setString(4, r.getString(4)); //OBSERVACIO --> observaciones				
				pstMySql.executeUpdate();				
			}			
			r.close();
			
			/**
			 * Statement para insertar el rol proveedor a la entidad externa
			 */
			String relacionaProveedoresConRolMySql = 
				"INSERT IGNORE INTO infraestructura.roles_entidad " +
				"(desde,activo,anulado,entidad_id,rol)" +
				"SELECT {d '2007-01-01'},'V','F',e.entidad_id,'PROV' " +
				"FROM infraestructura.entidad_externa e " +
					"LEFT OUTER JOIN infraestructura.roles_entidad rol ON rol.entidad_id = e.entidad_id " +
				"WHERE rol IS NULL or rol = 'PROV'";
			pstMySql2 = connInf.createStatement();			
			pstMySql2.executeUpdate(relacionaProveedoresConRolMySql);
				
			/**
			 * Seleccionamos de la tabla CPA01 los datos para los atributos
			 * de entidades externas con roles de proveedor
			 */
			String proveedoresaAtributosTangoSQL = 
				"SELECT COD_PROVEE,DOMICILIO,TELEFONO_1 FROM CPA01";
			
			connInf.commit();
			
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
				if (pstMySql != null)
					pstMySql.close();
				if (pstMySql2 != null)
					pstMySql2.close();
				if (connTango != null)
					connTango.close();
				if (connInf != null) {
					connInf.rollback();
					connInf.close();
				}
			} catch (SQLException e) {
				throw new JobExecutionException(e);				
			}
		}
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaProveedores();
	}
}
