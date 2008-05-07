/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.MovimientoArticuloModel;

import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0214_1 extends ValidadorReglasNegocio {

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
			MovimientoArticuloModel movimientos = new MovimientoArticuloModel("inventario");
			movimientos.retrieve("movimiento_articulo.comprobante_movimiento_id ="+ds.getComprobanteMovimientoArticuloComprobanteMovimientoId());
						
			if (ds.getComprobanteMovimientoArticuloUserIdRetira() == 0)
				throw new DataStoreException(
						"Indique el legajo de quien retira");
			
			if (movimientos.getRowCount() == 0) {
				msg.append("Debe detallar por lo menos un artículo");
				return false;
			}
			
		} catch (SQLException ex) {
			msg
					.append("Ocurrió un error en el SQL mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;		
		} catch (DataStoreException ex) {
			msg
					.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
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
