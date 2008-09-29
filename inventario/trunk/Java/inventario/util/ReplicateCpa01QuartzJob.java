package inventario.util;

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
import com.salmonllc.sql.DBConnection;

/**
 * Extrae información de proveedores de la tabla CPA01 de Tango y la agrega o
 * actualiza en la tabla entidad_externa y en los atributos corresponientes.
 * 
 * @author Francisco Paez
 */
public class ReplicateCpa01QuartzJob implements Job {

	/**
	 * Parametros para la conexión con SQL Server 2000
	 */
	private String driverTango = "net.sourceforge.jtds.jdbc.Driver";
	private String urlTango = "jdbc:jtds:sqlserver://[HOST]/[DB];instance=[INSTANCE];prepareSQL=3";
	private String userTango = null;
	private String passWordTango = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.importaProveedores();
	}

	/**
	 * @throws JobExecutionException
	 */
	public void importaProveedores() throws JobExecutionException {
		Connection connTango = null;
		DBConnection connInf = null;
		Statement tangoSt = null, stMySql = null;
		PreparedStatement pstMySql = null, pstMySql2 = null;
		ResultSet r = null, rMySql = null;

		// Parametros de conexión
		getConnectionInfo();

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);
			// Conexion con Tango (SQL Server 2000)
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);
			// Conexion con MySQL
			connInf = DBConnection.getConnection("infraestructura",
					"infraestructura");
			connInf.beginTransaction();

			/**
			 * Seleccionamos de la tabla CPA01 los datos básicos para la tabla
			 * entidades_externas
			 */
			String proveedoresBasicoTangoSQL = "SELECT COD_PROVEE,NOM_PROVEE,ORDEN,OBSERVACIO FROM CPA01";
			tangoSt = connTango
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			/**
			 * PreparedStatement para insertar datos en la tabla
			 * entidades_externas
			 */
			String proveedoresBasicoMySQL = "INSERT INTO infraestructura.entidad_externa "
					+ "(codigo,nombre,descripcion,observaciones,activo,anulado) "
					+ "VALUES (?,?,?,?,'V','F') "
					+ "ON DUPLICATE KEY UPDATE "
					+ "codigo = values(codigo), nombre = values(nombre),"
					+ "descripcion = values(descripcion), observaciones = values(observaciones)";
			pstMySql = connInf.prepareStatement(proveedoresBasicoMySQL);

			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(proveedoresBasicoTangoSQL);
			while (r.next()) {
				pstMySql.setString(1, r.getString(1)); // COD_PROVEE --> codigo
				pstMySql.setString(2, r.getString(2)); // NOM_PROVEE --> nombre
				pstMySql.setString(3, r.getString(3)); // ORDEN --> descripcion
				pstMySql.setString(4, r.getString(4)); // OBSERVACIO -->
														// observaciones
				pstMySql.executeUpdate();
			}
			r.close();

			/**
			 * Statement para insertar el rol proveedor a la entidad externa
			 */
			String relacionaProveedoresConRolMySql = "INSERT IGNORE INTO infraestructura.roles_entidad "
					+ "(desde,activo,anulado,entidad_id,rol)"
					+ "SELECT {d '2007-01-01'},'V','F',e.entidad_id,'PROV' "
					+ "FROM infraestructura.entidad_externa e "
					+ "LEFT OUTER JOIN infraestructura.roles_entidad rol ON rol.entidad_id = e.entidad_id "
					+ "WHERE rol IS NULL or rol = 'PROV'";
			stMySql = connInf.createStatement();
			stMySql.executeUpdate(relacionaProveedoresConRolMySql);

			/**
			 * Seleccionamos de la tabla CPA01 los datos para los atributos de
			 * entidades externas con roles de proveedor
			 */
			String proveedoresAtributosTangoSQL = "SELECT COD_PROVEE,DOMICILIO,TELEFONO_1,E_MAIL,LOCALIDAD,PROVINCIA FROM CPA01";

			/**
			 * PreparedStatements para atributos de entidades_externas con rol
			 * proveedor
			 */
			// Direccion y Telefono
			String proveedoresAtributoStringMySQL = "INSERT INTO infraestructura.atributos_entidad "
					+ "(entidad_id,atributo_id,valor) "
					+ "VALUES (?,?,?) "
					+ "ON DUPLICATE KEY UPDATE "
					+ "valor = values(valor), atributo_id = values(atributo_id), entidad_id = values(entidad_id)";
			pstMySql = connInf.prepareStatement(proveedoresAtributoStringMySQL);

			String getEntidadExternaByCodigo = "SELECT e.entidad_id FROM infraestructura.entidad_externa e WHERE e.codigo = ?";
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
					String localidad = r.getString(5);
					String provincia = r.getString(6);

					pstMySql.setInt(1, entidad_id);
					pstMySql.setInt(2, 11); // 11 -> atributo_id telefono
					pstMySql.setString(3, telefono);
					pstMySql.executeUpdate();

					pstMySql.setInt(1, entidad_id);
					pstMySql.setInt(2, 10); // 10 -> atributo_id domicilio
					pstMySql.setString(3, domicilio
							+ getLocalidadProvinciaPorCodigo(localidad, provincia));
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
					connInf.freeConnection();
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
		Props props = Props.getProps("inventario", null);
		Vector<String> errores = new Vector<String>();

		String driverTango = props.getProperty("driverTango");
		if (driverTango == null)
			errores
					.add("ReplicateCpa01QuartzJob.getConnectionInfo(): No se ha indicado la propiedad 'driverTango' en archivo de configuración");

		String urlTango = props.getProperty("urlTango");
		if (urlTango == null)
			errores
					.add("ReplicateCpa01QuartzJob.getConnectionInfo(): No se ha indicado la propiedad 'urlTango' en archivo de configuración");

		String usrTango = props.getProperty("usrTango");
		if (usrTango == null)
			errores
					.add("ReplicateCpa01QuartzJob.getConnectionInfo(): No se ha indicado la propiedad 'usrTango' en archivo de configuración");

		String passWordTango = props.getProperty("passWordTango");
		if (passWordTango == null)
			errores
					.add("ReplicateCpa01QuartzJob.getConnectionInfo(): No se ha indicado la propiedad 'passWordTango' en archivo de configuración");

		if (errores.size() > 0)
			throw new ValidationException(errores);

		this.driverTango = driverTango;
		this.urlTango = urlTango;
		this.userTango = usrTango;
		this.passWordTango = passWordTango;
	}

	private String getLocalidadProvinciaPorCodigo(String localidad, String codigo) {
		String local_prov = "";
		if (localidad.trim().length() != 0) {
			local_prov += " - "+localidad;
		}
		System.out.println("-->ACA");
		if (codigo.trim().length() != 0)
		switch (Integer.parseInt(codigo)) {
		case 0:
			local_prov += " - " + "Capital Federal".toUpperCase();
			break;
		case 1:
			local_prov += " - " + "Buenos Aires".toUpperCase();
			break;
		case 2:
			local_prov += " - " + "Catamarca".toUpperCase();
			break;
		case 3:
			local_prov += " - " + "Córdoba".toUpperCase();
			break;
		case 4:
			local_prov += " - " + "Corrientes".toUpperCase();
			break;
		case 5:
			local_prov += " - " + "Entre Ríos".toUpperCase();
			break;
		case 6:
			local_prov += " - " + "Jujuy".toUpperCase();
			break;
		case 7:
			local_prov += " - " + "Mendoza".toUpperCase();
			break;
		case 8:
			local_prov += " - " + "La Rioja".toUpperCase();
			break;
		case 9:
			local_prov += " - " + "Salta".toUpperCase();
			break;
		case 10:
			local_prov += " - " + "San Juan".toUpperCase();
			break;
		case 11:
			local_prov += " - " + "San Luis".toUpperCase();
			break;
		case 12:
			local_prov += " - " + "Santa Fé".toUpperCase();
			break;
		case 13:
			local_prov += " - " + "Santiago del Estero".toUpperCase();
			break;
		case 14:
			local_prov += " - " + "Tucumán".toUpperCase();
			break;
		case 15:
			local_prov += " - " + "Chaco".toUpperCase();
			break;
		case 16:
			local_prov += " - " + "Chaco".toUpperCase();
			break;
		case 17:
			local_prov += " - " + "Chubut".toUpperCase();
			break;
		case 18:
			local_prov += " - " + "Formosa".toUpperCase();
			break;
		case 19:
			local_prov += " - " + "Misiones".toUpperCase();
			break;
		case 20:
			local_prov += " - " + "Neuquén".toUpperCase();
			break;
		case 21:
			local_prov += " - " + "La Pampa".toUpperCase();
			break;
		case 22:
			local_prov += " - " + "Río Negro".toUpperCase();
			break;
		case 23:
			local_prov += " - " + "Santa Cruz".toUpperCase();
			break;
		case 24:
			local_prov += " - " + "Tierra del Fuego".toUpperCase();
			break;
		case 99:
			break;				
		}		
		return local_prov;
	}
}
