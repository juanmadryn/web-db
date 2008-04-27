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
public final class ValRN_0213_1 extends ValidadorReglasNegocio {

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

			if (detalles.estimateRowsRetrieved("detalles_rc.recepcion_compra_id ="
							+ recepcionCompraId) == 0) {
				msg.append("Debe detallar por lo menos un artículo a recibir");
				return false;
			}
			ds.setRecepcionesComprasUserIdCompleta(ds.getCurrentWebsiteUserId());
			ds.update(conn);
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba su aprobación: "
							+ ex.getMessage());
			return false;
		}
		return true;
	}
}
