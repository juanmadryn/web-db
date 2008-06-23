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
 * Transici�n: 	"Completa" -> "Observada" 
 * 
 * Acci�n: "Observar"
 * 
 * Regla de negocio asociada a la revision. Chequea que se halla 
 * ingresado una descripci�n acerca de la motivaci�n que lleva a 
 * marcar la orden de compra para revisi�n.
 * 
 */
public final class ValRN_0210_1 extends ValidadorReglasNegocio {
	
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
			
			// el usuario debe tener rol comprador
			if (!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),Constants.USER_COMPRADOR)) {
				msg.append("Debe ser " + Constants.USER_COMPRADOR + " para observar una Orden de Compra.");
				return false;				
			}

			// checkeo si la solicitud tiene seteadas las obervaciones
			if (ds.getObservaciones() == null
					|| ds.getObservaciones().trim().length() < 5) {
				msg.append("Debe completar el cuadro de observaciones con la informaci�n suficiente para corregir la orden de compra");
				return false;
			}

			int ordenCompraId = ds.getOrdenesCompraOrdenCompraId();

			// a continuaci�n, obtengo la cadena de firmas asociada a la
			// solicitud o la calculo si no est� seteada
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

			// recupero la cadena de aprobaci�n
			CadenasAprobacionModel cadena = new CadenasAprobacionModel(
					"inventario", "inventario");
			InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
					"inventario", "inventario");

			cadena.retrieve("configuracion_id = " + configuracion_id);

			if (!cadena.gotoFirst()) {
				msg.append("No se recupero ninguna cadena de aprobaci�n!");
				return false;
			}

			int currentWebsiteUser = ds.getCurrentWebsiteUserId();

			// checkeo si est� pendiente la aprobaci�n del usuario actual
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId +					
					" AND instancias_aprobacion.user_firmante =" + currentWebsiteUser +
					" AND instancias_aprobacion.estado = 0007.0001"
					);

			if (!instancia.gotoFirst()) {
				msg.append("Usted no est� autorizado para revisar la orden de compra en su estado actual");
				return false;
			}

			// actualizo el mensaje de la instancia con las observaciones indicadas
			instancia.setInstanciasAprobacionMensaje(ds.getObservaciones());
			instancia.update(conn);

		} catch (DataStoreException ex) {
			msg.append("Ocurri� un error en el DataStore mientras se procesaba su aprobaci�n: " 
					+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg.append("Ocurri� un error de SQL mientras se procesaba su aprobaci�n: "
					+ ex.getMessage());
			return false;
		}
		return true;
	}
}
