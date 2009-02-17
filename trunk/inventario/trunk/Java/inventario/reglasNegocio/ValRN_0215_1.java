/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.ConversionesModel;
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
 * Regla de negocio asociada a confirmar movimientos de almacén
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
			if (!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),
					Constants.USER_ENCARGADO_ALMACEN))
				throw new DataStoreException(
						"Ud. no está autorizado para confirmar movimientos de almacén.");
			if (ds.getComprobanteMovimientoArticuloUserIdRetira() == 0
					&& "F".equalsIgnoreCase(ds
							.getTipoMovimientoArticuloPositivo())) {
				msg
						.append("Especifique el legajo de quien retira para completar el comprobante");
				return false;
			}

			MovimientoArticuloModel movimientos = new MovimientoArticuloModel(
					"inventario");
			ResumenSaldoArticulosModel resumen = new ResumenSaldoArticulosModel(
					"inventario");

			int comprobante_movimiento_id = ds
					.getComprobanteMovimientoArticuloComprobanteMovimientoId();
			int tipo_movimiento_id = ds
					.getComprobanteMovimientoArticuloTipoMovimientoArticuloId();
			int almacen_id = ds.getComprobanteMovimientoArticuloAlmacenId();

			// obtengo los movimientos asociados al comprobante
			movimientos.retrieve(conn,
					"movimiento_articulo.comprobante_movimiento_id ="
							+ comprobante_movimiento_id);
			movimientos.waitForRetrieve();

			double cantidad_entregada = 0;
			double cantidad_solicitada = 0;
			double cantidad_anulada = 0;

			// chequeo que las cantidades solicitada, entregada y anulada estén
			// balanceadas
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

			// obtengo las cantidades entregadas de artículos por proyecto -
			// tarea
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(movimiento.cantidad_entregada) cantidad_entregada, movimiento.articulo_id, movimiento.proyecto_id, movimiento.tarea_id, movimiento.unidad_medida_id, sum(movimiento.cantidad_anulada) cantidad_anulada, max(monto_unitario) monto_unitario "
							+ "FROM comprobante_movimiento_articulo comprobante "
							+ "INNER JOIN movimiento_articulo movimiento ON comprobante.comprobante_movimiento_id = movimiento.comprobante_movimiento_id "
							+ "WHERE comprobante.comprobante_movimiento_id = "
							+ comprobante_movimiento_id
							+ " GROUP BY articulo_id, proyecto_id, tarea_id ORDER BY articulo_id");

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			int articulo_id = 0;
			int unidad_medida_id = 0;
			double stock;
			cantidad_entregada = 0;
			double cantidad_entregada_convertida = 0;
			double cantidad_anulada_convertida = 0;
			double cantidad_reservada = 0;
			double cantidad_en_proceso = 0;

			while (rs.next()) {
				stock = 0;
				articulo_id = rs.getInt("articulo_id");

				// recupero el resumen de stock del articulo y voy al primero,
				// por orden, el del período más reciente
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

				// obtengo el período actual
				java.sql.Date periodo = new java.sql.Date(calendar
						.getTimeInMillis());

				// si no hay resumen de saldo del artículo para el período
				// actual, lo creo
				if (resumen.getRowCount() == 0
						|| !resumen.getResumenSaldoArticulosPeriodo()
								.toString().equals(periodo.toString())) {
					resumen.gotoRow(resumen.insertRow());
					resumen.setResumenSaldoArticulosAlmacenId(almacen_id);
					resumen.setResumenSaldoArticulosArticuloId(articulo_id);
					resumen.setResumenSaldoArticulosPeriodo(periodo);
				}

				cantidad_entregada = rs.getDouble("cantidad_entregada");
				cantidad_anulada = rs.getDouble("cantidad_anulada");
				unidad_medida_id = rs.getInt("unidad_medida_id");

				// Chequea si la unidad de medida indicada tiene el factor de
				// conversión correspondiente, y devuelve la cantidad convertida
				cantidad_entregada_convertida = ConversionesModel
						.getUnidadConvertida(articulo_id, unidad_medida_id,
								cantidad_entregada, conn);
				cantidad_anulada_convertida = ConversionesModel
						.getUnidadConvertida(articulo_id, unidad_medida_id,
								cantidad_anulada, conn);
				// chequea si el movimiento es positivo, y de reserva, y
				// actualiza las cantidades correspondientes
				if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
						.getPositivo(tipo_movimiento_id, conn))) {
					if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
							.getReserva(tipo_movimiento_id, conn)))
						stock -= cantidad_entregada_convertida;
					else {
						cantidad_reservada -= cantidad_entregada_convertida;
						stock -= cantidad_entregada_convertida;
					}
					if (cantidad_en_proceso >= (cantidad_entregada_convertida + cantidad_anulada_convertida))
						cantidad_en_proceso -= (cantidad_entregada_convertida + cantidad_anulada_convertida);
					resumen.incrementarCantTransaccionesEgresos();
					resumen
							.incrementarTotalEgresos(cantidad_entregada_convertida);
				} else {
					if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
							.getReserva(tipo_movimiento_id, conn)))
						stock += cantidad_entregada_convertida;
					else
						cantidad_reservada += cantidad_entregada_convertida;
					resumen.incrementarCantTransaccionesIngresos();
					resumen
							.incrementarTotalIngresos(cantidad_entregada_convertida);
				}
				// setea las cantidad obtenidas anteriormente
				resumen.setResumenSaldoArticulosReservado(cantidad_reservada);
				resumen.setResumenSaldoArticulosStockEnMano(stock);
				resumen.setResumenSaldoArticulosEnProceso(cantidad_en_proceso);
				resumen
						.setResumenSaldoArticulosPrecioReposicion(ConversionesModel
								.getUnidadConvertida(articulo_id,
										unidad_medida_id, rs
												.getDouble("monto_unitario"),
										conn));

				resumen.update(conn);
				resumen.resetStatus();

				// en los movimientos correspondientes seteo el resumen de saldo
				// de artículo que se ha actualizado
				movimientos.filter("movimiento_articulo.articulo_id =="
						+ articulo_id);
				for (int row = 0; row < movimientos.getRowCount(); row++) {
					movimientos.gotoRow(row);
					movimientos
							.setMovimientoArticuloResumenSaldoArticuloId(resumen
									.getResumenSaldoArticulosResumenSaldoArticuloId());
					movimientos
							.setMovimientoArticuloMontoUnitario(
									row,
									Double
											.parseDouble(AtributosEntidadModel
													.getValorAtributoObjeto(
															Constants.ARTICULO_PRECIO_REPOSICION,
															articulo_id,
															"TABLA",
															"articulos")));
				}
				movimientos.update(conn);
				movimientos.resetStatus();
			}
			ds.setComprobanteMovimientoArticuloUserIdConfirma(ds
					.getCurrentWebsiteUserId());
			ds.update(conn);

		} catch (DataStoreException ex) {
			msg.append(ex.getMessage());

			return false;
		} catch (SQLException ex) {
			msg
					.append("Ocurrió un error (SQL) mientras se procesaba la confirmación: "
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
