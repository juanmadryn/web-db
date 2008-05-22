/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.DetalleRCModel;
import inventario.models.DetalleSCModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.RecepcionesComprasModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Hashtable;

import proyectos.models.ProyectoModel;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0213_1 extends ValidadorReglasNegocio implements
		Constants {

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
			DetalleSCModel detallesSC = new DetalleSCModel("inventario");

			int recepcionCompraId = ds.getRecepcionesComprasRecepcionCompraId();

			detalles.setOrderBy("detalles_rc.almacen_id");
			detalles.retrieve("detalles_rc.recepcion_compra_id ="
					+ recepcionCompraId);
			detalles.gotoFirst();
			ComprobanteMovimientoArticuloModel comprobanteMovimiento = new ComprobanteMovimientoArticuloModel(
					"inventario");
			MovimientoArticuloModel movimiento = new MovimientoArticuloModel(
					"inventario");

			int tipoMovimiento = Props.getProps("inventario", null)
					.getIntProperty("TipoMovimientoRecepciones");
			int almacen_id = 0;

			detallesSC.retrieve();
			Hashtable<Integer, Integer> almacen_comprobantes = new Hashtable<Integer, Integer>();
			do {
				if (almacen_id != detalles.getDetallesRcAlmacenId()) {
					almacen_id = detalles.getDetallesRcAlmacenId();
					comprobanteMovimiento.gotoRow(comprobanteMovimiento
							.insertRow());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloAlmacenId(almacen_id);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloEstado("0010.0002");
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
				detallesSC.filter("detalle_sc.detalle_SC_id =="
						+ detalles.getDetallesRcDetalleScId());
				detallesSC.gotoFirst();
				float cantidad_recibida = (float) (detallesSC
						.getDetalleScCantidadRecibida() + detalles
						.getDetallesRcCantidad());
				detallesSC.setDetalleScCantidadRecibida(cantidad_recibida);
				detallesSC.update(conn);
				detallesSC.resetStatus();
			} while (detalles.gotoNext());
			comprobanteMovimiento.update(conn);
			comprobanteMovimiento.resetStatus();

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(detalles_rc.cantidad_recibida) cantidad_recibida, sum(detalles_rc.cantidad_excedencia) cantidad_excedencia, detalle_sc.articulo_id, detalles_rc.almacen_id, solicitudes.proyecto_id, detalle_sc.tarea_id, cantidad_pedida, detalles_rc.unidad_medida_id FROM  detalles_rc detalles_rc inner join recepciones_compras ON detalles_rc.recepcion_compra_id = recepciones_compras.recepcion_compra_id  inner join detalle_sc detalle_sc ON detalles_rc.detalle_sc_id = detalle_sc.detalle_SC_id  inner join almacenes ON detalles_rc.almacen_id = almacenes.almacen_id  inner join articulos articulos ON detalle_sc.articulo_id = articulos.articulo_id  inner join unidades_medida unidades_medida ON detalle_sc.unidad_medida_id = unidades_medida.unidad_medida_id JOIN solicitudes_compra solicitudes ON detalle_sc.solicitud_compra_id = solicitudes.solicitud_compra_id WHERE detalles_rc.recepcion_compra_id = "
							+ recepcionCompraId
							+ " GROUP BY almacen_id, articulo_id ORDER BY almacen_id");

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int articulo_id = 0;
			int comprobante_movimiento_id = 0;

			while (rs.next()) {
				almacen_id = rs.getInt("almacen_id");
				articulo_id = rs.getInt("articulo_id");

				comprobanteMovimiento
						.setFindExpression("comprobante_movimiento_articulo.almacen_id == "
								+ almacen_id);
				comprobanteMovimiento.findFirst();
				comprobante_movimiento_id = comprobanteMovimiento
						.getComprobanteMovimientoArticuloComprobanteMovimientoId();

				movimiento.gotoRow(movimiento.insertRow());
				movimiento.setMovimientoArticuloArticuloId(articulo_id);
				movimiento.setMovimientoArticuloCantidadEntregada(rs
						.getDouble("cantidad_recibida")
						+ rs.getDouble("cantidad_excedencia"));

				movimiento.setMovimientoArticuloCantidadSolicitada(rs
						.getDouble("cantidad_pedida"));
				movimiento
						.setMovimientoArticuloComprobanteMovimientoId(comprobante_movimiento_id);
				movimiento.setMovimientoArticuloProyectoId(rs
						.getInt("proyecto_id"));
				movimiento.setMovimientoArticuloUnidadMedidaId(rs
						.getInt("unidad_medida_id"));

				movimiento.setMovimientoArticuloTareaId(rs.getInt("tarea_id"));
			}
			movimiento.update(conn);
			movimiento.resetStatus();

			int accion = Props.getProps("inventario", "inventario")
					.getIntProperty(ACCION_CONFIRMA_MOVIMIENTO);
			System.out.println(accion);
			for (int row = 0; row < comprobanteMovimiento.getRowCount(); row++) {
				comprobanteMovimiento.gotoRow(row);
				if (accion > 0)
					comprobanteMovimiento.ejecutaAccion(accion, "0010", "", ds
							.getCurrentWebsiteUserId(),
							"comprobante_movimiento_articulo", conn, false);
				else
					throw new DataStoreException(
							"No se ha indicado en el archivo System.properties la acción correspondiente a la confirmación de un movimiento de inventario.");
			}

		} catch (DataStoreException ex) {
			msg.append(ex.getMessage());
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
