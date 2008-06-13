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
 * Transici�n: "Aprobada" -> "Cotizada"
 * 
 * Acci�n: "Cotizar"
 * 
 * Regla de Negocio asociada con la cotizaci�n de una Solicitud de Compra.
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
				msg.append("Debe cotizar por lo menos un art�culo de la Solicitud de Materiales");
				return false;
			}			
						
			return true;
		} catch (DataStoreException ex) {
			msg.append("Ocurri� un error en el DataStore mientras se procesaba la cotizaci�n: "
							+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg.append("Ocurri� un error de SQL mientras se procesaba su cotizaci�n: "
							+ ex.getMessage());
			return false;
		}
	}

}
