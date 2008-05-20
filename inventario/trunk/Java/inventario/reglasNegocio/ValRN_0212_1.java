/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.DetalleRCModel;
import inventario.models.RecepcionesComprasModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0212_1 extends ValidadorReglasNegocio {

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
			DetalleRCModel detalles = new DetalleRCModel("inventario",
					"inventario");

			int recepcionCompraId = ds.getRecepcionesComprasRecepcionCompraId();

			detalles.retrieve("detalles_rc.recepcion_compra_id ="
					+ recepcionCompraId);

			if (ds.getRecepcionesComprasUserIdRecibe() == 0)
				throw new DataStoreException("Indique el legajo de quien recibe");
			if (detalles.getRowCount() == 0) {
				msg.append("Debe detallar por lo menos un artículo a recibir");
				return false;
			}
			ds
					.setRecepcionesComprasUserIdCompleta(ds
							.getCurrentWebsiteUserId());
			ds.update(conn);

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT detalles_rc.detalle_rc_id,detalles_rc.detalle_sc_id,sum(detalles_rc.cantidad_recibida) cantidad_pedida,recepciones_compras.proveedor_id,detalle_sc.articulo_id,(detalle_sc.cantidad_pedida-detalle_sc.cantidad_recibida) cantidad_disponible FROM  detalles_rc detalles_rc inner join recepciones_compras ON detalles_rc.recepcion_compra_id = recepciones_compras.recepcion_compra_id  inner join detalle_sc detalle_sc ON detalles_rc.detalle_sc_id = detalle_sc.detalle_SC_id WHERE detalles_rc.recepcion_compra_id = "
							+ recepcionCompraId
							+ " GROUP BY detalle_sc_id HAVING cantidad_pedida > (cantidad_disponible)");
			Hashtable<Integer, String> detalles_rc_ids = new Hashtable<Integer, String>();

			while (rs.next()) {
				detalles_rc_ids.put(rs.getInt("detalles_rc.detalle_sc_id"), rs
						.getString("cantidad_disponible"));
			}

			for (int row = 0; row < detalles.getRowCount(); row++) {
				if (detalles_rc_ids.containsKey(detalles
						.getDetallesRcDetalleScId(row))) {
					msg
							.append("La cantidad recibida es mayor a la cantidad comprada en el detalle nº"
									+ (row + 1)
									+ ". La cantidad disponible total es "
									+ detalles_rc_ids.get(detalles
											.getDetallesRcDetalleScId(row))
									+ "\r");
				}
			}
			if (msg.length() > 0)
				return false;

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
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					msg.append(e.getMessage());
				}
			}
		}
		return true;
	}
}
