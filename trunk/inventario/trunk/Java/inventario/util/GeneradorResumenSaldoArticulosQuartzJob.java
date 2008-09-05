package inventario.util;

import infraestructura.controllers.Constants;
import inventario.models.ResumenSaldoArticulosModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;


/**
 * Extrae información de proveedores de la tabla CPA01 de Tango
 * y la agrega o actualiza en la tabla entidad_externa y en los
 * atributos corresponientes. 
 * 
 * @author Francisco Paez
 */
public class GeneradorResumenSaldoArticulosQuartzJob implements Job {
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.generaResumenesSaldoArticulos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	/**
	 * @throws JobExecutionException
	 * @throws SQLException
	 * @throws DataStoreException 
	 */
	public void generaResumenesSaldoArticulos() throws JobExecutionException, SQLException, DataStoreException {
			ResumenSaldoArticulosModel resumenes = new ResumenSaldoArticulosModel("inventario");
			ResumenSaldoArticulosModel nuevo_resumen = new ResumenSaldoArticulosModel("inventario");
			int articulo_id = 0;
			int almacen_id = 0;		
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date periodo = new Date(calendar.getTimeInMillis()); 
			
			DBConnection conexion = DBConnection.getConnection("inventario");
			Statement st = conexion.createStatement();
			String query = "SELECT DISTINCT articulos.articulo_id, resumenes.almacen_id FROM inventario.articulos articulos LEFT OUTER JOIN resumen_saldo_articulos resumenes ON (resumenes.articulo_id = articulos.articulo_id) WHERE articulos.articulo_id NOT IN (SELECT resumenes.articulo_id FROM resumen_saldo_articulos resumenes WHERE resumenes.periodo = '"+ periodo +"')";
			ResultSet rs = st.executeQuery(query);
			
			resumenes.retrieve();
			while(rs.next()) {
				articulo_id = rs.getInt(1);
				almacen_id = rs.getInt(2) != 0 ? rs.getInt(2) : Props.getProps("inventario", "inventario").getIntProperty(Constants.ALMACEN_GENERAL);
				nuevo_resumen.gotoRow(nuevo_resumen.insertRow());
				nuevo_resumen.setResumenSaldoArticulosAlmacenId(almacen_id);
				nuevo_resumen.setResumenSaldoArticulosArticuloId(articulo_id);
				nuevo_resumen.setResumenSaldoArticulosCantTransaccionesEgresos(0);
				nuevo_resumen.setResumenSaldoArticulosCantTransaccionesIngresos(0);
				nuevo_resumen.setResumenSaldoArticulosPeriodo(periodo);
				nuevo_resumen.setResumenSaldoArticulosTotalEgresos(0);
				nuevo_resumen.setResumenSaldoArticulosTotalIngresos(0);
				
				resumenes.filter("resumen_saldo_articulos.articulo_id =="+rs.getInt(1) +" && resumen_saldo_articulos.almacen_id =="+rs.getInt(2));
				if(resumenes.gotoFirst()) {
					nuevo_resumen.setResumenSaldoArticulosEnProceso(resumenes.getResumenSaldoArticulosEnProceso());
					nuevo_resumen.setResumenSaldoArticulosPrecioReposicion(resumenes.getResumenSaldoArticulosPrecioReposicion());					
					nuevo_resumen.setResumenSaldoArticulosReservado(resumenes.getResumenSaldoArticulosReservado());
					nuevo_resumen.setResumenSaldoArticulosStockEnMano(resumenes.getResumenSaldoArticulosStockEnMano());
				} else {
					nuevo_resumen.setResumenSaldoArticulosEnProceso(0);
					nuevo_resumen.setResumenSaldoArticulosPrecioReposicion(0);					
					nuevo_resumen.setResumenSaldoArticulosReservado(0);
					nuevo_resumen.setResumenSaldoArticulosStockEnMano(0);					
				}			
			}
			nuevo_resumen.update();
	}	
}
