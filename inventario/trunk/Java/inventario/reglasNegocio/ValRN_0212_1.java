/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.DetalleRCModel;
import inventario.models.RecepcionesComprasModel;

import java.sql.SQLException;

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
		try {
			RecepcionesComprasModel ds = (RecepcionesComprasModel) obj;
			DetalleRCModel detalles = new DetalleRCModel("inventario",
					"inventario");		
			
			int recepcionCompraId = ds.getRecepcionesComprasRecepcionCompraId();

			detalles.retrieve("detalles_rc.recepcion_compra_id =" + recepcionCompraId);
			
			if (detalles.getRowCount() == 0) {
				msg.append("Debe detallar por lo menos un artículo a recibir");
				return false;
			}
			ds
					.setRecepcionesComprasUserIdCompleta(ds
							.getCurrentWebsiteUserId());
			ds.update(conn);

			double cantidad_disponible = 0;
			for (int row = 0; row < detalles.getRowCount(); row++) {
				cantidad_disponible = detalles.getDetalleScCantidadPedida(row)
						- detalles.getDetalleScCantidadRecibida(row);
				if (detalles.getDetallesRcCantidad(row) > (cantidad_disponible)) {
					msg
							.append("La cantidad recibida es mayor a la cantidad comprada en el detalle Nº "
									+ (row + 1)
									+ ". La cantidad disponible es "
									+ cantidad_disponible);
					msg.append('\n');
				}
				System.out.println(cantidad_disponible);
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
		}
		return true;
	}
}
