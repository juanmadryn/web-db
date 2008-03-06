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
 * Replicate SQL Server STA11 table contents into MySQL.  
 * 
 * @author Francisco Ezequiel Paez
 */
public class ReplicateSta11QuartzJob implements Job {

	/**
	 * Replicate SQL Server STA11 table contents into MySQL.  
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void replicate() throws SQLException, ClassNotFoundException {
		Connection connTango = null;
		Connection connInv = null;
		PreparedStatement psTango = null, pstMySql = null, pstMySql2 = null;
		ResultSet r = null, rMySql = null;
		
		String driverTango = "net.sourceforge.jtds.jdbc.Driver";
		String urlTango="jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
		String userTango="Axoft";
		String passWordTango="Axoft";

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);
			// Se establece la conexión con la base de datos
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);				
			// Now attempt to create a database connection with MySQL
			Class.forName("com.mysql.jdbc.Driver");
			connInv = DriverManager.getConnection ("jdbc:mysql://localhost:3306/inventario", 
					"inventario", "inventario");			
			connInv.setAutoCommit(false);
			
			// Get the item classes
			String SQLclaseArticuloTango = 
				"SELECT t.clase FROM (SELECT substring(COD_ARTICU,1,5) as clase FROM STA11) t group by t.clase";
			psTango = connTango.prepareStatement(SQLclaseArticuloTango,
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);						
			String SQLclaseArticulo = "INSERT INTO inventario.clase_articulo " +
					"(nombre) VALUES (?) ON DUPLICATE KEY UPDATE nombre = values(nombre)";
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
			String SQLclaseArticuloTangoDesc =
				"SELECT t.descripcio, t.clase FROM (SELECT substring(COD_ARTICU,1,5) as clase, " +
				"descripcio FROM STA11 where substring(COD_ARTICU,6,8)='999') t ";
			psTango = connTango.prepareStatement(SQLclaseArticuloTangoDesc,
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
			String SQLarticulo = "INSERT INTO inventario.articulos " +
					"(clase_articulo_id,nombre,descripcion,descripcion_completa,clave_externa1) VALUES (?, ?, ?, ?, ?) " +
					"ON DUPLICATE KEY UPDATE clase_articulo_id = values(clase_articulo_id), nombre = values(nombre)," +
					"descripcion = values(descripcion), descripcion_completa = values(descripcion_completa), " +
					"clave_externa1 = values(clave_externa1)";
			pstMySql = connInv.prepareStatement(SQLarticulo);
			
			String SQLgetFk = "select c.clase_articulo_id from clase_articulo c where substring(?,1,5) = c.nombre";
			pstMySql2 = connInv.prepareStatement(SQLgetFk);
			
			String SQLtango = "SELECT COD_ARTICU,DESC_ADIC,DESCRIPCIO,STOCK FROM STA11";
			psTango = connTango.prepareStatement(SQLtango,
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
				//connInv.commit();
			}
			
			// Finally, set the correct fk for each item
			/*String SQLupdateFk = "update articulos set clase_articulo_id = (select c.clase_articulo_id " +
					"from clase_articulo c where substring(articulos.nombre,1,5) = c.nombre)";*/
			
			// Finally, remove the legacy records which contains the item descriptions...
			String SQLremoveLegacyRec = "delete from articulos where substring(nombre,6,8) = '999'";
			pstMySql = connInv.prepareStatement(SQLremoveLegacyRec);
			pstMySql.executeUpdate();
			
			connInv.commit();
			
			/***
			 *  importa precio/fecha ultima compra
			 */
			String SQLimportaPrecioFechaTango = 
				"SELECT COD_ARTICU, MAX(FECHA_MOV) AS FECHA, MAX(PRECIO_NET) AS MONTO " +
				"FROM CPA46 GROUP BY COD_ARTICU ORDER BY COD_ARTICU";
			psTango = connTango.prepareStatement(SQLimportaPrecioFechaTango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);						
			String SQLtmpFechaPrecio = "INSERT INTO inventario.tmp_fechaprecio " +
				"(cod_articu,fecha_mov,precio_net,done) VALUES (?, ?, ?, NULL) " +
				"ON DUPLICATE KEY UPDATE fecha_mov = values(fecha_mov), precio_net = values(precio_net), " +
				"done = values(done)";
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
			
			// now update the date and amount properties
			String SQLupdMonto = "update infraestructura.atributos_entidad a" +
				"inner join " +
				"(SELECT a.articulo_id, t.fecha_mov, t.precio_net, t.done " + 
				"FROM tmp_fechaprecio t, articulos a WHERE t.cod_articu = a.nombre order by a.articulo_id) t1 on a.objeto_id = t1.articulo_id " +
				"set a.valor_real = t1.precio_net, a.valor = truncate(t1.precio_net,2) " +
				"where a.atributo_id = 3";
			String SQLupdFecha = "update infraestructura.atributos_entidad a" +
				"inner join " +
				"(SELECT a.articulo_id, t.fecha_mov, t.precio_net, t.done " + 
				"FROM tmp_fechaprecio t, articulos a WHERE t.cod_articu = a.nombre order by a.articulo_id) t1 on a.objeto_id = t1.articulo_id " +
				"set a.valor_fecha = t1.fecha_mov, a.valor = date_format(date(t1.fecha_mov),'%d/%m/%Y') " +
				"where a.atributo_id = 4"; 
			pstMySql = connInv.prepareStatement(SQLupdMonto);
			pstMySql.executeUpdate();
			pstMySql = connInv.prepareStatement(SQLupdFecha);
			pstMySql.executeUpdate();
			
			/*
			insert into infraestructura.atributos_entidad(valor,valor_fecha,atributo_id,objeto_id,tipo_objeto,nombre_objeto)
			select date_format(date(t1.fecha_mov),'%d/%m/%Y') as valor, t1.fecha_mov as valor_fecha, 3 as atributo_id, t1.articulo_id as objeto_id,
			'TABLA' as tipo_objeto, 'articulos' as nombre_objecto from
			(SELECT a.articulo_id, t.fecha_mov, t.precio_net, t.done
			FROM tmp_fechaprecio t, articulos a WHERE t.cod_articu = a.nombre order by a.articulo_id) as t1
			left outer join infraestructura.atributos_entidad as a1 on t1.articulo_id = a1.objeto_id
			where a1.objeto_id is null

			insert into infraestructura.atributos_entidad (valor,valor_real,atributo_id,objeto_id,tipo_objeto,nombre_objeto)
			select truncate(t1.precio_net,2) as valor, t1.precio_net as valor_real, 3 as atributo_id, t1.articulo_id as objeto_id,
			'TABLA' as tipo_objeto, 'articulos' as nombre_objeto from
			(SELECT a.articulo_id, t.fecha_mov, t.precio_net, t.done
			FROM tmp_fechaprecio t, articulos a WHERE t.cod_articu = a.nombre order by a.articulo_id) as t1
			left outer join infraestructura.atributos_entidad as a1 on t1.articulo_id = a1.objeto_id
			where a1.objeto_id is null or a1.atributo_id != 3			
			*/
			connInv.commit();			
		} finally {
			if (r != null)
				r.close();
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
}
