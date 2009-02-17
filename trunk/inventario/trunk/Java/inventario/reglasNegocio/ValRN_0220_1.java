/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.OrdenesCompraModel;
import inventario.util.OrdenesDeCompraTANGO;

import java.sql.SQLException;
import java.text.ParseException;

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
			
			// Ejecutamos la replicación en tango			
			//OrdenesDeCompraTANGO ordenesDeCompraTANGO = new OrdenesDeCompraTANGO();
			//ordenesDeCompraTANGO.insertaCabeceraOC(ds, ds.isReplicadoEnTango());
						
			// Actualizamos el atributo monto ultima compra
			DetalleSCModel detalleSCModel = new DetalleSCModel("inventario");
			
			detalleSCModel.retrieve(conn, 
					DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " + ds.getOrdenesCompraOrdenCompraId());
			detalleSCModel.waitForRetrieve();
			detalleSCModel.gotoFirst();
			
			for(int row = 0; row < detalleSCModel.getRowCount(); row++) {
				AtributosEntidadModel.setValorAtributoObjeto(
						String.valueOf(detalleSCModel.getDetalleScMontoUnitario(row)),
						"MONTO_ULTIMA_COMPRA", ds.getOrdenesCompraOrdenCompraId(), "TABLA", "ordenes_compra");
			}
			
			return true;
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba la emisión: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba la emisión: " + ex.getMessage());
			ex.printStackTrace();
			return false;		
		} /*catch (ParseException e) {
			msg.append("Ocurrio un error mientras se procesaba la emisión: " + e.getMessage());
			e.printStackTrace();
			return false;
		}*/ catch (ValidationException ex) {
			for (String er : ex.getStackErrores()) {						
				msg.append(er + '\n');
			}										
			return false;
		}
	}

}
