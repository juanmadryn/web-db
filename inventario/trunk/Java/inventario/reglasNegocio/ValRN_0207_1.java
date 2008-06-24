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
import inventario.models.SolicitudCompraModel;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Demian
 * 
 * Rechazo de una Solicitud de Compra/Material
 */
public final class ValRN_0207_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {		
	
		try {		
			SolicitudCompraModel ds = (SolicitudCompraModel) obj;
			
			// Se comprueba el rol usuario
			if (!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),Constants.USER_COMPRADOR)) {
				// Si el rol es SOLICITANTE, realizamos chequeos extra
				if (UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),Constants.USER_SOLICITANTE)) {
					// el estado debe ser previo a la firma de la SM/SC
					if (!"0006.0001".equalsIgnoreCase(ds.getSolicitudesCompraEstado())) {
						msg.append("Usted no está autorizado para rechazar esta Solicitud en el estado actual.");
						return false;
					}
					// la solicitud debe pertenecer al solicitante que ejecuta la accion
					if (ds.getCurrentWebsiteUserId() != ds.getSolicitudesCompraUserIdSolicita()) {
						msg.append("Usted no está autorizado para rechazar una Solicitud de otro usuario.");
						return false;
					}
				} else {
					// El usuario no es ni COMPRADOR ni SOLICITANTE
					msg.append("Usted no está autorizado para rechazar una solicitud.");
					return false;
				}					
			}

			// chequeo si la solicitud tiene seteadas las obervaciones
			if (ds.getObservaciones() == null || ds.getObservaciones().trim().length() < 5) {
				msg	.append("Debe completar el cuadro de observaciones con el motivo del rechazo.");
				return false;
			}
			
			// Actualizo la cadena de firmas solo si se encuentra pendiente de aprobación
			if ("0006.0002".equalsIgnoreCase(ds.getSolicitudesCompraEstado())) {
				int solicitudCompraId = ds.getSolicitudesCompraSolicitudCompraId();

				// a continuación, obtengo la cadena de firmas asociada a la solicitud o la calculo si no está seteada
				String valorAtributo = AtributosEntidadModel
				.getValorAtributoObjeto("CONFIGURACION_ID",
						solicitudCompraId, "TABLA", "solicitudes_compra");

				int configuracion_id;

				if (valorAtributo != null)
					configuracion_id = Integer.parseInt(valorAtributo);
				else {
					configuracion_id = DeterminaConfiguracionServicio
					.determinaConfiguracion(AtributosConfiguracionModel
							.determinaAtributosConfiguracion(ds
									.getEsquemaConfiguracionId(),
									solicitudCompraId, "TABLA",
							"solicitudes_compra"));
					AtributosEntidadModel.setValorAtributoObjeto(String
							.valueOf(configuracion_id), "CONFIGURACION_ID",
							solicitudCompraId, "TABLA", "solicitudes_compra");
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
						"instancias_aprobacion.nombre_objeto = 'solicitudes_compra' AND " +
						"instancias_aprobacion.objeto_id = " + solicitudCompraId +
						" AND instancias_aprobacion.user_firmante =" + currentWebsiteUser +
				" AND instancias_aprobacion.estado = 0007.0001");
				if (!instancia.gotoFirst()) {
					msg.append("Usted no está autorizado para aprobar la solicitud en su estado actual");
					return false;
				}
				int orden = instancia.getInstanciasAprobacionOrden();

				instancia.retrieve(
						"instancias_aprobacion.nombre_objeto = 'solicitudes_compra' AND " +
						"instancias_aprobacion.objeto_id = " + solicitudCompraId
				);


				instancia.firmarInstanciasAprobacionSolicitud(currentWebsiteUser, orden,
						conn);


				// actualizo el mensaje de la instancia con las observaciones indicadas
				instancia.gotoFirst();
				instancia.setInstanciasAprobacionMensaje(ds.getObservaciones());
				instancia.update(conn);
			}

			
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
