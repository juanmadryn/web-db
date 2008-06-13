/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.DetalleSCModel;
import inventario.models.SolicitudCompraModel;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de Negocio
 * 
 * Objeto asociado: ordenes_compra
 * 
 * Transición: "Aprobada" -> "Cotizada"
 * 
 * Acción: "Cotizar"
 * 
 * Regla de Negocio asociada con la cotización de una Solicitud de Compra.
 *  
 */
public final class ValRN_0219_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {

		try {
			SolicitudCompraModel ds = (SolicitudCompraModel) obj;
			DetalleSCModel detalles = new DetalleSCModel("inventario",
					"inventario");
						
			// El usuario que cotiza debe tener rol COMPRADOR			 
			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
				msg.append("Debe ser COMPRADOR para cotizar la Solicitud de Materiales.");
				return false;
			}
			
			// La solicitud debe tener al menos un detalle cotizado
			if (detalles.estimateRowsRetrieved(
					DetalleSCModel.DETALLE_SC_COTIZACION_COMPRA_ID + " IS NOT NULL AND " + 
					DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID + " = " + ds.getSolicitudesCompraSolicitudCompraId()) == 0) 
			{
				msg.append("Debe cotizar por lo menos un artículo de la Solicitud de Materiales");
				return false;
			}			
						
			return true;
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba la cotización: "
							+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba su cotización: "
							+ ex.getMessage());
			return false;
		}
	}

}
