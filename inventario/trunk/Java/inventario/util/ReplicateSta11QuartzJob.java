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
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Replicate SQL Server STA11 table contents into MySQL.
 * 
 * @author Francisco Ezequiel Paez
 */
public class ReplicateSta11QuartzJob implements Job {
	
	/**
	 * Parámetros de conexión a Tango
	 */
	private String driverTango = "net.sourceforge.jtds.jdbc.Driver";
	private String urlTango = "jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
	private String userTango = "Axoft";
	private String passWordTango = "Axoft";

	/**
	 * Replicate SQL Server STA11 table contents into MySQL.
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void replicate() throws SQLException, ClassNotFoundException {
		Connection connTango = null;
		Connection connInv = null;
		Statement tangoSt = null;
		PreparedStatement psTango = null, pstMySql = null, pstMySql2 = null;
		ResultSet r = null, rMySql = null;

		String driverTango = "net.sourceforge.jtds.jdbc.Driver";
		String urlTango = "jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
		String userTango = "Axoft";
		String passWordTango = "Axoft";

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);
			// Se establece la conexión con la base de datos
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);
			// Now attempt to create a database connection with MySQL
			Class.forName("com.mysql.jdbc.Driver");
			connInv = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/inventario", "root", "root");
			connInv.setAutoCommit(false);

			// Get the item classes
			String SQLclaseArticuloTango = "SELECT t.clase FROM (SELECT substring(COD_ARTICU,1,5) as clase FROM STA11) t group by t.clase";
			psTango = connTango
					.prepareStatement(SQLclaseArticuloTango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String SQLclaseArticulo = "INSERT INTO inventario.clase_articulo "
					+ "(nombre) VALUES (?) ON DUPLICATE KEY UPDATE nombre = values(nombre)";
			pstMySql = connInv.prepareStatement(SQLclaseArticulo);

			r = psTango.executeQuery();
			if (r.isBeforeFirst()) {
				while (r.next()) {
					pstMySql.setString(1, r.getString(1));
					pstMySql.executeUpdate();
				}
			}
			r.close();

			// Get the items descriptions
			String SQLclaseArticuloTangoDesc = "SELECT t.descripcio, t.clase FROM (SELECT substring(COD_ARTICU,1,5) as clase, "
					+ "descripcio FROM STA11 where substring(COD_ARTICU,6,8)='999') t ";
			psTango = connTango
					.prepareStatement(SQLclaseArticuloTangoDesc,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String SQLclaseArticuloDesc = "UPDATE inventario.clase_articulo SET descripcion=? WHERE nombre=?";
			pstMySql = connInv.prepareStatement(SQLclaseArticuloDesc);

			r = psTango.executeQuery();
			if (r.isBeforeFirst()) {
				while (r.next()) {
					pstMySql.setString(1, r.getString(1));
					pstMySql.setString(2, r.getString(2));
					pstMySql.executeUpdate();
				}
			}
			r.close();

			// Get the items from Tango database
			String SQLarticulo = "INSERT INTO inventario.articulos "
					+ "(clase_articulo_id,nombre,descripcion,descripcion_completa,clave_externa1) VALUES (?, ?, ?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE clase_articulo_id = values(clase_articulo_id), nombre = values(nombre),"
					+ "descripcion = values(descripcion), descripcion_completa = values(descripcion_completa), "
					+ "clave_externa1 = values(clave_externa1)";
			pstMySql = connInv.prepareStatement(SQLarticulo);

			String SQLgetFk = "select c.clase_articulo_id from clase_articulo c where substring(?,1,5) = c.nombre";
			pstMySql2 = connInv.prepareStatement(SQLgetFk);

			String SQLtango = "SELECT COD_ARTICU,DESC_ADIC,DESCRIPCIO,STOCK FROM STA11";
			psTango = connTango
					.prepareStatement(SQLtango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			r = psTango.executeQuery();

			String cod_articu = "";
			String desc_adic = "";
			String descripcio = "";

			// If the resulset is not empty
			if (r.isBeforeFirst()) {
				while (r.next()) {
					cod_articu = r.getString(1);
					desc_adic = r.getString(2);
					descripcio = r.getString(3);

					pstMySql2.setString(1, cod_articu);
					rMySql = pstMySql2.executeQuery();
					rMySql.first();

					pstMySql.setInt(1, rMySql.getInt(1));
					pstMySql.setString(2, cod_articu);
					pstMySql.setString(3, descripcio);
					pstMySql.setString(4, desc_adic);
					pstMySql.setString(5, cod_articu);

					pstMySql.executeUpdate();
				}
			}

			// Finally, remove the legacy records which contains the item
			// descriptions...
			String SQLremoveLegacyRec = "delete from articulos where substring(nombre,6,8) = '999'";
			pstMySql = connInv.prepareStatement(SQLremoveLegacyRec);
			pstMySql.executeUpdate();

			connInv.commit();

			/*******************************************************************
			 * importa precio/fecha ultima compra
			 */
			String SQLimportaPrecioFechaTango = 
				"SELECT CPA36.COD_ARTICU, UP.FECHA AS FECHA, CPA36.PRECIO AS MONTO " + 
				"FROM CPA36 " + 
				"JOIN ( " + 
				"SELECT COD_ARTICU, MAX(CPA35.FEC_EMISIO) AS FECHA, MAX(ID_CPA36) AS ID_CPA36 " +
				"FROM CPA36 " + 
				"JOIN CPA35 ON CPA35.N_ORDEN_CO = CPA36.N_ORDEN_CO " +
				"GROUP BY COD_ARTICU " +
				") UP ON CPA36.ID_CPA36 = UP.ID_CPA36";
			psTango = connTango
					.prepareStatement(SQLimportaPrecioFechaTango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			String SQLtmpFechaPrecio = "INSERT INTO inventario.tmp_fechaprecio "
					+ "(cod_articu,fecha_mov,precio_net) VALUES (?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE fecha_mov = values(fecha_mov), precio_net = values(precio_net)";

			pstMySql = connInv.prepareStatement(SQLtmpFechaPrecio);

			r = psTango.executeQuery();
			if (r.isBeforeFirst()) {
				while (r.next()) {
					cod_articu = r.getString(1);
					desc_adic = r.getString(2);
					descripcio = r.getString(3);

					pstMySql.setString(1, r.getString(1));
					pstMySql.setDate(2, r.getDate(2));
					pstMySql.setDouble(3, r.getDouble(3));

					pstMySql.executeUpdate();
				}
			}

			// Sospecho que hay una forma mucho mas eficiente, elegante y mas
			// mejor,
			// posiblemente utilizando el comando INSERT ... ON DUPLICATE KEY
			// UPDATE ...
			// Sin embargo lo que sigue es la 1er solucion, esa que llega para
			// quedarse (?)

			// now update the date and amount properties already stored
			String SQLupdMonto = "update infraestructura.atributos_entidad a "
					+ "inner join "
					+ "(SELECT a.articulo_id, t.fecha_mov, t.precio_net "
					+ "FROM inventario.tmp_fechaprecio t, inventario.articulos a "
					+ "WHERE t.cod_articu = a.nombre order by a.articulo_id) "
					+ "t1 on a.objeto_id = t1.articulo_id and a.tipo_objeto = 'TABLA' "
					+ "and a.nombre_objeto = 'articulos' and a.atributo_id = 3 "
					+ "set a.valor_real = t1.precio_net, a.valor = truncate(t1.precio_net,2)";
			String SQLupdFecha = "update infraestructura.atributos_entidad a inner join "
					+ "(SELECT a.articulo_id, t.fecha_mov, t.precio_net "
					+ "FROM inventario.tmp_fechaprecio t, inventario.articulos a "
					+ "WHERE t.cod_articu = a.nombre order by a.articulo_id) t1 "
					+ "on a.objeto_id = t1.articulo_id and a.tipo_objeto = 'TABLA' "
					+ "and a.nombre_objeto = 'articulos' and a.atributo_id = 4 "
					+ "set a.valor_fecha = date(t1.fecha_mov), "
					+ "a.valor = date_format(date(t1.fecha_mov),'%d/%m/%Y')";

			pstMySql = connInv.prepareStatement(SQLupdMonto);
			pstMySql.executeUpdate();
			pstMySql = connInv.prepareStatement(SQLupdFecha);
			pstMySql.executeUpdate();

			// insert properties for new items
			String SQLinsertMonto = "insert into infraestructura.atributos_entidad "
					+ "(valor,valor_fecha,atributo_id,objeto_id,tipo_objeto,nombre_objeto) "
					+ "select "
					+ "date_format(date(t1.fecha_mov),'%d/%m/%Y') as valor, "
					+ "date(t1.fecha_mov) as valor_fecha, 4 as atributo_id, t1.articulo_id as objeto_id, "
					+ "'TABLA' as tipo_objeto, 'articulos' as nombre_objeto "
					+ "from "
					+ "(SELECT a.articulo_id, t.fecha_mov, t.precio_net "
					+ "FROM inventario.tmp_fechaprecio t, inventario.articulos a "
					+ "WHERE t.cod_articu = a.nombre order by a.articulo_id) as t1 "
					+ "left outer join infraestructura.atributos_entidad as a1 "
					+ "on t1.articulo_id = a1.objeto_id and a1.tipo_objeto = 'TABLA' "
					+ "and a1.nombre_objeto = 'articulos' and atributo_id = 4 "
					+ "where a1.objeto_id is null ";
			String SQLinsertFecha = "insert into infraestructura.atributos_entidad "
					+ "(valor,valor_real,atributo_id,objeto_id,tipo_objeto,nombre_objeto) "
					+ "select "
					+ "truncate(t1.precio_net,2) as valor, t1.precio_net as valor_real, "
					+ "3 as atributo_id, t1.articulo_id as objeto_id, "
					+ "'TABLA' as tipo_objeto, 'articulos' as nombre_objeto "
					+ "from "
					+ "(SELECT a.articulo_id, t.fecha_mov, t.precio_net "
					+ "FROM inventario.tmp_fechaprecio t, inventario.articulos a "
					+ "WHERE t.cod_articu = a.nombre order by a.articulo_id) as t1 "
					+ "left outer join infraestructura.atributos_entidad as a1 "
					+ "on t1.articulo_id = a1.objeto_id and a1.tipo_objeto = 'TABLA' "
					+ "and a1.nombre_objeto = 'articulos' and atributo_id = 3 "
					+ "where a1.objeto_id is null	";

			pstMySql = connInv.prepareStatement(SQLinsertMonto);
			pstMySql.executeUpdate();
			pstMySql = connInv.prepareStatement(SQLinsertFecha);
			pstMySql.executeUpdate();

			/**
			 * Articulos - IVA
			 */
			String proveedoresAtributosTangoSQL = "SELECT STA11.COD_ARTICU, CPA14.COD_IVA, CPA14.DESC_IVA, CPA14.PORC_IVA "
					+ "FROM STA11 JOIN CPA14 CPA14 ON CPA14.COD_IVA = STA11.COD_IVA_CO";
			tangoSt = connTango
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

			/**
			 * PreparedStatements para atributos IVA
			 */
			String proveedoresAtributoStringMySQL = "insert into infraestructura.atributos_entidad "
					+ "(valor,valor_real,atributo_id,objeto_id,tipo_objeto,nombre_objeto) "
					+ "VALUES (?,?,?,?,'TABLA','articulos') "
					+ "ON DUPLICATE KEY UPDATE "
					+ "valor = values(valor), valor_real = values(valor_real), "
					+ "atributo_id = values(atributo_id), objeto_id = values(objeto_id)";
			pstMySql = connInv.prepareStatement(proveedoresAtributoStringMySQL);

			String getEntidadExternaByCodigo = "SELECT a.articulo_id FROM inventario.articulos a WHERE a.clave_externa1 = ?";
			pstMySql2 = connInv.prepareStatement(getEntidadExternaByCodigo);

			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(proveedoresAtributosTangoSQL);
			while (r.next()) {
				pstMySql2.setString(1, r.getString(1)); // busco por codigo
				rMySql = pstMySql2.executeQuery();
				rMySql.first();

				if (rMySql.isFirst()) {
					int objeto_id = rMySql.getInt(1);
					String descIva = r.getString(3);
					Float porcIva = r.getFloat(4);

					pstMySql.setInt(4, objeto_id);
					pstMySql.setInt(3, 13); // 11 -> atributo_id descripcion iva
					pstMySql.setNull(2, java.sql.Types.FLOAT);
					pstMySql.setString(1, descIva);
					pstMySql.executeUpdate();

					pstMySql.setInt(4, objeto_id);
					pstMySql.setInt(3, 12); // 10 -> atributo_id porcentaje iva
					pstMySql.setFloat(2, porcIva);
					// pstMySql.setNull(1, java.sql.Types.VARCHAR);
					pstMySql.setString(1, String.valueOf(porcIva));
					pstMySql.executeUpdate();
				}
			}

			/**
			 * Seleccionamos de la tabla CPA01 los datos para los atributos de
			 * entidades externas con roles de proveedor
			 */
			//String articulosUnidadMedidaAtributosTangoSQL = "SELECT COD_ARTICU, UNIDAD_MED  FROM STA11";
			String articulosUnidadMedidaAtributosTangoSQL =
				"SELECT [COD_ARTICU], [UNIDAD_M_C] FROM [FABRI_S.A.].[dbo].[STA36]";

			String getArticuloByCodigo = "SELECT a.articulo_id FROM inventario.articulos a WHERE a.clave_externa1 = ?";
			pstMySql2 = connInv.prepareStatement(getArticuloByCodigo);

			/**
			 * PreparedStatements para atributos de entidades_externas con rol
			 * proveedor
			 */
			// Direccion y Telefono
			String articulosUnidadMedidaAtributoStringMySQL = "insert into infraestructura.atributos_entidad "
					+ "(valor,valor_entero,atributo_id,objeto_id,tipo_objeto,nombre_objeto) "
					+ "VALUES (?,?,7,?,'TABLA','articulos') "
					+ "ON DUPLICATE KEY UPDATE "
					+ "valor = values(valor), valor_entero = values(valor_entero), "
					+ "atributo_id = values(atributo_id), objeto_id = values(objeto_id)";
			pstMySql = connInv
					.prepareStatement(articulosUnidadMedidaAtributoStringMySQL);

			/**
			 * Insertamos los datos en la tabla entidades_externas
			 */
			r = tangoSt.executeQuery(articulosUnidadMedidaAtributosTangoSQL);
			String unidad_med = "1";
			String uni;
			int uniInt;
			while (r.next()) {
				pstMySql2.setString(1, r.getString(1)); // busco por codigo
				rMySql = pstMySql2.executeQuery();
				if (rMySql.first()) {

					unidad_med = r.getString(2);

					int objeto_id = rMySql.getInt(1);

					uni = "1";
					uniInt = 1;

					if ("KG".equalsIgnoreCase(unidad_med)
							|| "KGS".equalsIgnoreCase(unidad_med)) {
						uni = "4";
						uniInt = 4;
					} else if ("LT".equalsIgnoreCase(unidad_med)
							|| "LTS".equalsIgnoreCase(unidad_med)) {
						uni = "2";
						uniInt = 2;
					} else if ("HS".equalsIgnoreCase(unidad_med)) {
						uni = "9";
						uniInt = 9;
					} else if ("MT".equalsIgnoreCase(unidad_med)
							|| "MTS".equalsIgnoreCase(unidad_med)) {
						uni = "3";
						uniInt = 3;
					} else if ("M3".equalsIgnoreCase(unidad_med)
							|| "MT3".equalsIgnoreCase(unidad_med)) {
						uni = "7";
						uniInt = 7;
					} else if ("M2".equalsIgnoreCase(unidad_med)
							|| "MT2".equalsIgnoreCase(unidad_med)) {
						uni = "6";
						uniInt = 6;
					} else if ("TN".equalsIgnoreCase(unidad_med)) {
						uni = "11";
						uniInt = 11;
					} else if ("VIA".equalsIgnoreCase(unidad_med)) {
						uni = "12";
						uniInt = 12;
					} else if ("PAR".equalsIgnoreCase(unidad_med)) {
						uni = "13";
						uniInt = 13;
					} else if ("DIA".equalsIgnoreCase(unidad_med)) {
						uni = "14";
						uniInt = 14;
					} else if ("BOL".equalsIgnoreCase(unidad_med)) {
						uni = "15";
						uniInt = 15;
					}

					pstMySql.setString(1, uni);
					pstMySql.setInt(2, uniInt);
					pstMySql.setInt(3, objeto_id);
					pstMySql.executeUpdate();
				}
			}

			connInv.commit();
		} finally {
			if (tangoSt != null)
				tangoSt.close();
			if (r != null)
				r.close();
			if (rMySql != null)
				rMySql.close();
			if (psTango != null)
				psTango.close();
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
	
	private void getConnectionInfo() { 
		Props props = Props.getProps("inventario", null);
		Vector<String> errores = new Vector<String>();		
		
		String driverTango = props.getProperty("driverTango");
		if (driverTango == null) 
			errores.add("No se ha indicado la propiedad 'driverTango' en archivo de configuración");		
		String urlTango = props.getProperty("urlTango");
		if (urlTango == null) 
			errores.add("No se ha indicado la propiedad 'urlTango' en archivo de configuración");
		String userTango = props.getProperty("userTango");
		if (userTango == null) 
			errores.add("No se ha indicado la propiedad 'userTango' en archivo de configuración");
		String passWordTango = props.getProperty("passWordTango");
		if (passWordTango == null) 
			errores.add("No se ha indicado la propiedad 'passWordTango' en archivo de configuración");
		
		if (errores.size() > 0) 
			throw new ValidationException(errores);
		
		this.driverTango = driverTango;
		this.urlTango = urlTango;
		this.userTango = userTango;
		this.passWordTango = passWordTango;
	}
}
