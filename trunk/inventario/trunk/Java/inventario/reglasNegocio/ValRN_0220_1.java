/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.OrdenesCompraModel;
import inventario.util.SolicitudCompraTransiciones;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco 
 * 
 * Regla de Negocio asociada con la emision de una Orden de Compra
 * Cambia los estados de la Solicitud de Compra. Ver SolicitudCompraTransiciones.
 * 
 */
public final class ValRN_0220_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {

		try {
			OrdenesCompraModel ds = (OrdenesCompraModel) obj;
			
			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
				msg.append("Debe ser COMPRADOR para emitir la Orden de Compra.");
				return false;
			}
			
			// Obtiene los detalles de la orden de compra correspondiente 
			DetalleSCModel detalles = new DetalleSCModel("inventario","inventario");			
			detalles.retrieve(conn, DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " + ds.getOrdenesCompraOrdenCompraId());
			detalles.waitForRetrieve();
			
			// Actualiza los estados de la Solicitudes de Compras asociadas
			SolicitudCompraTransiciones.agregarEnOc(conn,
					detalles, getRemoteHost(), getUserId());
			
			// Somos buenos con el garbage collector...
			detalles = null;
						
			return true;
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba la cotización: "	+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba su cotización: " + ex.getMessage());
			return false;
		} catch (ValidationException ex) {
			for (String er : ex.getStackErrores()) {						
				msg.append(er + '\n');
			}										
			return false;
		}
	}

}
