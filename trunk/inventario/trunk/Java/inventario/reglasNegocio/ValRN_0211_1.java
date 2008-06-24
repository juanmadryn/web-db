/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.OrdenesCompraModel;

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
 * Transición: 	"Completa" -> "Rechazada"
 * 
 * Acción: "Rechazar"
 * 
 * Regla de negocio asociada al rechazo de una OC.
 * 
 */
public final class ValRN_0211_1 extends ValidadorReglasNegocio {
	
	private static final String NOMBRE_OBJETO = "ordenes_compra";

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {		
	
		try {		
			OrdenesCompraModel ds = (OrdenesCompraModel) obj;
			
			// Se comprueba el rol usuario
			if (!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),Constants.USER_COMPRADOR)) {
				msg.append("Usted no está autorizado para rechazar una solicitud.");
				return false;
			}

			// checkeo si la solicitud tiene seteadas las obervaciones
			if (ds.getObservaciones() == null || ds.getObservaciones().trim().length() < 5) {
				msg	.append("Debe completar el cuadro de observaciones con el motivo del rechazo");
				return false;
			}

			// Actualizo la cadena de firmas solo si se encuentra pendiente de aprobación
			if ("0008.0002".equalsIgnoreCase(ds.getOrdenesCompraEstado())) {
				int ordenCompraId = ds.getOrdenesCompraOrdenCompraId();

				// a continuación, obtengo la cadena de firmas asociada a la solicitud o la calculo si no está seteada
				String valorAtributo = AtributosEntidadModel
				.getValorAtributoObjeto("CONFIGURACION_ID",
						ordenCompraId, "TABLA", NOMBRE_OBJETO);

				int configuracion_id;

				if (valorAtributo != null)
					configuracion_id = Integer.parseInt(valorAtributo);
				else {
					configuracion_id = DeterminaConfiguracionServicio
					.determinaConfiguracion(AtributosConfiguracionModel
							.determinaAtributosConfiguracion(ds
									.getEsquemaConfiguracionId(),
									ordenCompraId, "TABLA",
									NOMBRE_OBJETO));
					AtributosEntidadModel.setValorAtributoObjeto(String
							.valueOf(configuracion_id), "CONFIGURACION_ID",
							ordenCompraId, "TABLA", NOMBRE_OBJETO);
				}

				// recupero la cadena de aprobación
				CadenasAprobacionModel cadena = new CadenasAprobacionModel(
						"inventario", "inventario");
				InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
						"inventario", "inventario");

				cadena.retrieve("configuracion_id = " + configuracion_id);

				if (!cadena.gotoFirst()) {
					msg.append("No se recupero ninguna cadena de aprobación!");
					return false;
				}

				int currentWebsiteUser = ds.getCurrentWebsiteUserId();

				// checkeo si está pendiente la aprobación del usuario actual
				instancia.retrieve(
						"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " +
						"instancias_aprobacion.objeto_id = " + ordenCompraId +
						" AND instancias_aprobacion.user_firmante =" + currentWebsiteUser +
				" AND instancias_aprobacion.estado = 0007.0001");
				if (!instancia.gotoFirst()) {
					msg.append("Usted no está autorizado para rechazar la orden de compra en su estado actual");
					return false;
				}

				instancia.retrieve(
						"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " +
						"instancias_aprobacion.objeto_id = " + ordenCompraId
				);
				instancia.gotoFirst();
				instancia.firmarInstanciasAprobacionSolicitud(currentWebsiteUser,
						conn);

				// actualizo el mensaje de la instancia con las observaciones indicadas
				instancia.setInstanciasAprobacionMensaje(ds.getObservaciones());
				instancia.update(conn);
			}
			
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
