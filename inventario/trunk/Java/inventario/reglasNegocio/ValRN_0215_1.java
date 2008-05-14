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

			int comprobante_movimiento_id = ds
					.getComprobanteMovimientoArticuloComprobanteMovimientoId();
			int tipo_movimiento_id = ds
					.getComprobanteMovimientoArticuloTipoMovimientoArticuloId();
			int almacen_id = ds.getComprobanteMovimientoArticuloAlmacenId();

			if (ds.getComprobanteMovimientoArticuloUserIdRetira() == 0)
				throw new DataStoreException(
						"Indique el legajo de quien retira");

			movimientos
					.retrieve("movimiento_articulo.comprobante_movimiento_id ="
							+ comprobante_movimiento_id);
			for(int row = 0; row < movimientos.getRowCount(); row++) {
				if (movimientos.getMovimientoArticuloCantidadAnulada(row) == 0)
					movimientos.setMovimientoArticuloCantidadAnulada(row,
							movimientos.getMovimientoArticuloCantidadSolicitada(row)
									- movimientos.getMovimientoArticuloCantidadEntregada(row));
			}

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(movimiento.cantidad_entregada) cantidad_entregada, movimiento.articulo_id, movimiento.proyecto_id, movimiento.tarea_id, cantidad_solicitada FROM  comprobante_movimiento_articulo comprobante INNER JOIN movimiento_articulo movimiento ON comprobante.comprobante_movimiento_id = movimiento.comprobante_movimiento_id WHERE comprobante.comprobante_movimiento_id = "
							+ comprobante_movimiento_id
							+ " GROUP BY articulo_id, proyecto_id, tarea_id ORDER BY articulo_id");

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			int articulo_id = 0;

			while (rs.next()) {
				articulo_id = rs.getInt("articulo_id");

				resumen.retrieve("resumen_saldo_articulos.almacen_id = "
						+ almacen_id
						+ " AND resumen_saldo_articulos.articulo_id = "
						+ articulo_id
						+ " AND resumen_saldo_articulos.periodo = '"
						+ new java.sql.Date(calendar.getTimeInMillis()) + "'");

				double stock = ResumenSaldoArticulosModel.getStockEnMano(
						articulo_id, almacen_id, conn);
				double cantidad = rs.getDouble("cantidad_entregada");

				if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
						.getPositivo(tipo_movimiento_id, conn)))
					stock -= cantidad;
				else
					stock += cantidad;

				if (resumen.getRowCount() == 0) {
					resumen.gotoRow(resumen.insertRow());
					resumen.setResumenSaldoArticulosAlmacenId(almacen_id);
					resumen.setResumenSaldoArticulosArticuloId(articulo_id);
					resumen.setResumenSaldoArticulosPeriodo(new java.sql.Date(
							calendar.getTimeInMillis()));
				} else
					resumen.gotoFirst();

				double cantidad_reservada = ResumenSaldoArticulosModel
						.getReservado(articulo_id, almacen_id, conn);
				if (!"F".equalsIgnoreCase(TipoMovimientoArticuloModel
						.getReserva(tipo_movimiento_id, conn)))
					cantidad_reservada += cantidad;
				resumen.setResumenSaldoArticulosReservado(cantidad_reservada);
				resumen.setResumenSaldoArticulosStockEnMano(stock);

				resumen.update(conn);

				movimientos.filter("movimiento_articulo.articulo_id =="+articulo_id);
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
