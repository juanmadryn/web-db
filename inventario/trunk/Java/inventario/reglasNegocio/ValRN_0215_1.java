/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.ResumenSaldoArticulosModel;
import inventario.models.TipoMovimientoArticuloModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0215_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {
		Statement st = null;
		try {
			ComprobanteMovimientoArticuloModel ds = (ComprobanteMovimientoArticuloModel) obj;
			MovimientoArticuloModel movimientos = new MovimientoArticuloModel(
					"inventario");
			ResumenSaldoArticulosModel resumen = new ResumenSaldoArticulosModel(
					"inventario");

			ds.update(conn, false);
			ds.resetStatus();

			int comprobante_movimiento_id = ds
					.getComprobanteMovimientoArticuloComprobanteMovimientoId();
			int tipo_movimiento_id = ds
					.getComprobanteMovimientoArticuloTipoMovimientoArticuloId();
			int almacen_id = ds.getComprobanteMovimientoArticuloAlmacenId();

			movimientos
					.retrieve("movimiento_articulo.comprobante_movimiento_id ="
							+ comprobante_movimiento_id);

			double cantidad_entregada = 0;
			double cantidad_solicitada = 0;
			double cantidad_anulada = 0;

			for (int row = 0; row < movimientos.getRowCount(); row++) {
				cantidad_entregada = movimientos
						.getMovimientoArticuloCantidadEntregada(row);
				cantidad_solicitada = movimientos
						.getMovimientoArticuloCantidadSolicitada(row);
				cantidad_anulada = movimientos
						.getMovimientoArticuloCantidadAnulada(row);

				if (cantidad_entregada > cantidad_solicitada)
					msg
							.append("La cantidad entregada no puede ser mayor a la solicitada para el artículo "
									+ movimientos.getArticulosDescripcion(row));
				else if (cantidad_solicitada != (cantidad_anulada + cantidad_entregada))
					msg
							.append("La cantidad solicitada debe ser igual a lo entregado más lo anulado para el artículo "
									+ movimientos.getArticulosDescripcion(row));
			}
			if (msg.length() > 0)
				return false;

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(movimiento.cantidad_entregada) cantidad_entregada, movimiento.articulo_id, movimiento.proyecto_id, movimiento.tarea_id, cantidad_solicitada FROM  comprobante_movimiento_articulo comprobante INNER JOIN movimiento_articulo movimiento ON comprobante.comprobante_movimiento_id = movimiento.comprobante_movimiento_id WHERE comprobante.comprobante_movimiento_id = "
							+ comprobante_movimiento_id
							+ " GROUP BY articulo_id, proyecto_id, tarea_id ORDER BY articulo_id");

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			int articulo_id = 0;
			double stock = 0;
			cantidad_entregada = 0;
			double cantidad_reservada = 0;
			double cantidad_en_proceso = 0;

			while (rs.next()) {
				articulo_id = rs.getInt("articulo_id");

				resumen.retrieve("resumen_saldo_articulos.almacen_id = "
						+ almacen_id
						+ " AND resumen_saldo_articulos.articulo_id = "
						+ articulo_id);
				resumen.waitForRetrieve();
				
				if (resumen.gotoFirst()) {
					stock = resumen.getResumenSaldoArticulosStockEnMano();
					cantidad_reservada = resumen
							.getResumenSaldoArticulosReservado();
					cantidad_en_proceso = resumen
							.getResumenSaldoArticulosEnProceso();
				}
				
				java.sql.Date periodo = new java.sql.Date(calendar
						.getTimeInMillis());
				
				if (resumen.getRowCount() == 0
						|| resumen.getResumenSaldoArticulosPeriodo().equals(periodo)) {
					System.out.println("resumen: "+resumen.getResumenSaldoArticulosPeriodo() + " periodo: "+ periodo);
					resumen.gotoRow(resumen.insertRow());
					resumen.setResumenSaldoArticulosAlmacenId(almacen_id);
					resumen.setResumenSaldoArticulosArticuloId(articulo_id);
					resumen.setResumenSaldoArticulosPeriodo(periodo);
				}
				cantidad_entregada = rs.getDouble("cantidad_entregada");

				if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
						.getPositivo(tipo_movimiento_id, conn))) {
					if("F".equalsIgnoreCase(TipoMovimientoArticuloModel
							.getReserva(tipo_movimiento_id, conn)))
						stock -= cantidad_entregada;
					else {
						cantidad_reservada -= cantidad_entregada;						
					}
					if (cantidad_en_proceso >= (cantidad_entregada + cantidad_anulada)) 
						cantidad_en_proceso -= (cantidad_entregada + cantidad_anulada);
				}
				else {
					if("F".equalsIgnoreCase(TipoMovimientoArticuloModel
							.getReserva(tipo_movimiento_id, conn)))
						stock += cantidad_entregada;
					else 
						cantidad_reservada += cantidad_entregada;
					
				}
				resumen.setResumenSaldoArticulosReservado(cantidad_reservada);
				resumen.setResumenSaldoArticulosStockEnMano(stock);
				resumen.setResumenSaldoArticulosEnProceso(cantidad_en_proceso);

				resumen.update(conn);
				resumen.resetStatus();

				movimientos.filter("movimiento_articulo.articulo_id =="
						+ articulo_id);
				while (movimientos.gotoNext()) {
					movimientos
							.setMovimientoArticuloResumenSaldoArticuloId(resumen
									.getResumenSaldoArticulosResumenSaldoArticuloId());
				}
				movimientos.update(conn);
				resumen.resetStatus();
				movimientos.resetStatus();
			}

		} catch (DataStoreException ex) {
			msg
					.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;
		} catch (SQLException ex) {
			msg
					.append("Ocurrió un error de SQL mientras se procesaba su aprobación: "
							+ ex.getMessage());
			return false;
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException ex) {
				msg
						.append("Ocurrió un error de SQL mientras se cerraba el Statement: "
								+ ex.getMessage());
			}
		}
		return true;
	}
}
