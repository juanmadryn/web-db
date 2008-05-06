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
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaProveedores();
	}

	/**
	 * @throws JobExecutionException
	 */
	public void importaProveedores() throws JobExecutionException {
		Connection connTango = null;
		Connection connInf = null;
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
			stMySql = connInf.createStatement();			
			stMySql.executeUpdate(relacionaProveedoresConRolMySql);
				
			/**
			 * Seleccionamos de la tabla CPA01 los datos para los atributos
			 * de entidades externas con roles de proveedor
			 */
			String proveedoresAtributosTangoSQL = 
				"SELECT COD_PROVEE,DOMICILIO,TELEFONO_1,E_MAIL FROM CPA01";
			
			/**
			 * PreparedStatements para atributos de entidades_externas con rol proveedor 
			 */
			// Direccion y Telefono
			String proveedoresAtributoStringMySQL = 
				"INSERT INTO infraestructura.atributos_entidad " +
				"(entidad_id,atributo_id,valor) " +
				"VALUES (?,?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"valor = values(valor), atributo_id = values(atributo_id), entidad_id = values(entidad_id)";
			pstMySql = connInf.prepareStatement(proveedoresAtributoStringMySQL);
			
			String getEntidadExternaByCodigo =
				"SELECT e.entidad_id FROM infraestructura.entidad_externa e WHERE e.codigo = ?";
			pstMySql2 = connInf.prepareStatement(getEntidadExternaByCodigo);
			
			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(proveedoresAtributosTangoSQL);			
			while (r.next()) {
				pstMySql2.setString(1, r.getString(1)); // busco por codigo
				rMySql = pstMySql2.executeQuery();
				rMySql.first();				
				
				if (rMySql.isFirst()) {
					int entidad_id = rMySql.getInt(1);
					String domicilio = r.getString(2);
					String telefono = r.getString(3);
					String email = r.getString(4);

					pstMySql.setInt(1, entidad_id);
					pstMySql.setInt(2, 11); // 11 -> atributo_id telefono
					pstMySql.setString(3, telefono);
					pstMySql.executeUpdate();

					pstMySql.setInt(1, entidad_id);
					pstMySql.setInt(2, 10); // 10 -> atributo_id domicilio
					pstMySql.setString(3, domicilio);
					pstMySql.executeUpdate();
					
					pstMySql.setInt(1, entidad_id);
					pstMySql.setInt(2, 17); // 17 -> atributo_id domicilio
					pstMySql.setString(3, email);
					pstMySql.executeUpdate();
				}
			}			
			r.close();
			
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
				if (stMySql != null)
					stMySql.close();
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
}
