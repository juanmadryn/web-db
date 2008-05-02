/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.DetalleRCModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.RecepcionesComprasModel;
import inventario.models.ResumenSaldoArticulosModel;
import inventario.models.TipoMovimientoArticuloModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Hashtable;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0213_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {
		Statement st = null;
		try {
			RecepcionesComprasModel ds = (RecepcionesComprasModel) obj;
			DetalleRCModel detalles = new DetalleRCModel("inventario");

			int recepcionCompraId = ds.getRecepcionesComprasRecepcionCompraId();

			detalles.setOrderBy("detalles_rc.almacen_id");
			detalles.retrieve("detalles_rc.recepcion_compra_id ="
					+ recepcionCompraId);
			detalles.gotoFirst();

			ComprobanteMovimientoArticuloModel comprobanteMovimiento = new ComprobanteMovimientoArticuloModel(
					"inventario");
			MovimientoArticuloModel movimiento = new MovimientoArticuloModel(
					"inventario");
			ResumenSaldoArticulosModel resumen = new ResumenSaldoArticulosModel(
					"inventario");

			ds.setRecepcionesComprasUserIdRecibe(ds.getCurrentWebsiteUserId());

			int tipoMovimiento = Props.getProps("inventario", null)
					.getIntProperty("TipoMovimientoRecepciones");
			int almacen_id = 0;
			Hashtable<Integer, Integer> almacen_comprobantes = new Hashtable<Integer, Integer>();
			for (; detalles.gotoNext();) {
				if (almacen_id != detalles.getDetallesRcAlmacenId()) {
					almacen_id = detalles.getDetallesRcAlmacenId();
					comprobanteMovimiento.gotoRow(comprobanteMovimiento
							.insertRow());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloAlmacenId(almacen_id);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloEstado("0010.0003");
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloFecha(ds
									.getRecepcionesComprasFecha());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloObservaciones(ds
									.getRecepcionesComprasObservaciones());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloRecepcionCompraId(recepcionCompraId);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloTipoMovimientoArticuloId(tipoMovimiento);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloUserIdPreparador(ds
									.getRecepcionesComprasUserIdCompleta());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloUserIdRetira(ds
									.getRecepcionesComprasUserIdRecibe());
					almacen_comprobantes
							.put(
									almacen_id,
									comprobanteMovimiento
											.getComprobanteMovimientoArticuloComprobanteMovimientoId());
				}
				comprobanteMovimiento.update(conn);
			}

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(detalles_rc.cantidad_recibida) cantidad_recibida, detalle_sc.articulo_id, detalles_rc.almacen_id, solicitudes.proyecto_id, detalle_sc.tarea_id, cantidad_pedida FROM  detalles_rc detalles_rc inner join recepciones_compras ON detalles_rc.recepcion_compra_id = recepciones_compras.recepcion_compra_id  inner join detalle_sc detalle_sc ON detalles_rc.detalle_sc_id = detalle_sc.detalle_SC_id  inner join almacenes ON detalles_rc.almacen_id = almacenes.almacen_id  inner join articulos articulos ON detalle_sc.articulo_id = articulos.articulo_id  inner join unidades_medida unidades_medida ON detalle_sc.unidad_medida_id = unidades_medida.unidad_medida_id JOIN solicitudes_compra solicitudes ON detalle_sc.solicitud_compra_id = solicitudes.solicitud_compra_id WHERE detalles_rc.recepcion_compra_id = "
							+ recepcionCompraId
							+ " GROUP BY almacen_id, articulo_id ORDER BY almacen_id");

			int articulo_id = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			while (rs.next()) {
				almacen_id = rs.getInt("almacen_id");
				articulo_id = rs.getInt("articulo_id");
				comprobanteMovimiento.setFindExpression("comprobante_movimiento_articulo.almacen_id == "+ almacen_id);				
				comprobanteMovimiento.findFirst();				
				movimiento.gotoRow(movimiento.insertRow());
				movimiento.setMovimientoArticuloArticuloId(articulo_id);
				movimiento.setMovimientoArticuloCantidadEntregada(rs
						.getDouble("cantidad_recibida"));
				
				movimiento.setMovimientoArticuloCantidadSolicitada(rs
						.getDouble("cantidad_pedida"));				
				movimiento
						.setMovimientoArticuloComprobanteMovimientoId(comprobanteMovimiento
								.getComprobanteMovimientoArticuloComprobanteMovimientoId());				
				movimiento.setMovimientoArticuloProyectoId(rs
						.getInt("proyecto_id"));
				movimiento.setMovimientoArticuloTareaId(rs.getInt("tarea_id"));
				
				resumen.retrieve("resumen_saldo_articulos.almacen_id = "
						+ almacen_id
						+ " AND resumen_saldo_articulos.articulo_id = "
						+ articulo_id
						+ " AND resumen_saldo_articulos.periodo = '"
						+ new java.sql.Date(calendar.getTimeInMillis()) 
						+ "'");

				double stock = ResumenSaldoArticulosModel.getStockEnMano(
						articulo_id, almacen_id, conn);
				double cantidad = movimiento
						.getMovimientoArticuloCantidadEntregada();

				if ("F".equalsIgnoreCase(TipoMovimientoArticuloModel
						.getPositivo(tipoMovimiento, conn)))
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
						.getReserva(tipoMovimiento, conn)))
					cantidad_reservada += cantidad;
				resumen.setResumenSaldoArticulosReservado(cantidad_reservada);
				resumen.setResumenSaldoArticulosStockEnMano(stock);

				resumen.update(conn);

				movimiento.setMovimientoArticuloResumenSaldoArticuloId(resumen
						.getResumenSaldoArticulosResumenSaldoArticuloId());
				movimiento.update(conn);
				resumen.resetStatus();
				movimiento.resetStatus();
				
			}
			
		} catch (DataStoreException ex) {
			msg
					.append("Ocurri� un error en el DataStore mientras se procesaba su aprobaci�n: "
							+ ex.getMessage());

			return false;
		} catch (SQLException ex) {
			msg
					.append("Ocurri� un error de SQL mientras se procesaba su aprobaci�n: "
							+ ex.getMessage());
			return false;
		} finally {
			try {
				st.close();
			} catch (SQLException ex) {
				msg
						.append("Ocurri� un error de SQL mientras se cerraba el Statement: "
								+ ex.getMessage());
			}
		}
		return true;
	}
}