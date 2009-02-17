/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.ConversionesModel;
import inventario.models.DetalleRCModel;
import inventario.models.DetalleSCModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.RecepcionesComprasModel;
import inventario.models.ResumenSaldoArticulosModel;

import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada a la anulación de una recepción
 * 
 */
public final class ValRN_0222_1 extends ValidadorReglasNegocio implements
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
			RecepcionesComprasModel recepcion = (RecepcionesComprasModel) obj;
			
			if(!UsuarioRolesModel.isRolUsuario(recepcion.getCurrentWebsiteUserId(), USER_ENCARGADO_ALMACEN))
				throw new DataStoreException("Usted no está autorizado para anular recepciones.");
			
			DetalleRCModel detalles = new DetalleRCModel("inventario");
			DetalleSCModel detallesSC = new DetalleSCModel("inventario");
			
			detalles.retrieve("detalles_rc.recepcion_compra_id = "+recepcion.getRecepcionesComprasRecepcionCompraId());
			
			for(int row = 0; row < detalles.getRowCount(); row++) {
				detallesSC.retrieve("detalle_SC_id ="+detalles.getDetallesRcDetalleScId(row));
				if(detallesSC.gotoFirst()) {
					detallesSC.setDetalleScRecepcionCompraId(0);
					detallesSC.update(conn);
				}
			}

			
		} catch (SQLException ex) {
			msg
					.append("Ocurrió un error en el SQL mientras se procesaba su anulación: "
							+ ex.getMessage());

			return false;
		} catch (DataStoreException ex) {
			msg
					.append("Ocurrió un error en el DataStore mientras se procesaba su anulación: "
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
