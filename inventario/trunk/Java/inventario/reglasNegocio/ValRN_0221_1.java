package inventario.reglasNegocio;

import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.OrdenesCompraModel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author francisco
 * 
 * Regla de Negocio
 * 
 * Objeto asociado: ordenes_compra
 * 
 * Transición:	"Aprobada" -> "Observada"
 * 				"Emitida" -> "Observada"
 * 
 * Acción: "Revisar"
 * 
 * Ejecuta validaciones asociadas con la acción de observar una orden de compra.
 * 
 * Chequea:
 * 1) Rol del usuario que ejecuta la transición debe ser COMPRADOR.
 * 2) La orden debe tener seteada las observaciones
 * 
 * Si todos las verificaciones son exitosas se genera una cadena de aprobación 
 * nueva para el firmado de la orden de compra revisada.
 * 
 */
public class ValRN_0221_1 extends ValidadorReglasNegocio {
	private static final String NOMBRE_OBJETO = "ordenes_compra";

	@Override
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn)
			throws ValidationException {
		
		try {
			OrdenesCompraModel ds = (OrdenesCompraModel) obj;
			
			int ordenCompraId = ds.getOrdenesCompraOrdenCompraId();
			
			// chequeo que el usuario tenga el rol COMPRADOR
			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
				msg.append("Debe ser COMPRADOR para revisar una orden de compra.");
				return false;
			}			
			
			// checkeo si la solicitud tiene seteadas las obervaciones
			if (ds.getObservaciones() == null || ds.getObservaciones().trim().length() < 5) {
				msg.append("Debe completar el cuadro de observaciones con la información suficiente para corregir la orden");
				return false;
			}
			
			// genera nueva cadena de aprobacion
			InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
					"inventario", "inventario");
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '"+ NOMBRE_OBJETO +"' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId +
					" AND instancias_aprobacion.estado = 0007.0001"
					);
			if (instancia.gotoFirst()) {
				return true;
			}

			String valorAtributo = AtributosEntidadModel
					.getValorAtributoObjeto("CONFIGURACION_ID",
							ordenCompraId, "TABLA", NOMBRE_OBJETO);

			int configuracion_id;

			if (valorAtributo != null && valorAtributo != "0")
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

			CadenasAprobacionModel cadena = new CadenasAprobacionModel(
					"inventario", "inventario");

			cadena.retrieve("configuracion_id = " + configuracion_id);

			if (!cadena.gotoFirst()) {
				msg.append("No se recupero ninguna cadena de aprobación!");
				return false;
			}

			Iterator<Integer> siguientesFirmantes = cadena
					.getSiguientesFirmantes(false, 0);

			if (siguientesFirmantes == null) {
				msg
						.append("No se recuperó la lista de firmantes correspondiente");
				return false;
			}

			while (siguientesFirmantes.hasNext()) {
				instancia.gotoRow(instancia.insertRow());
				instancia.setInstanciasAprobacionEstado("0007.0001");
				instancia.setInstanciasAprobacionFechaEntrada(new Timestamp(
						(Calendar.getInstance().getTimeInMillis())));
				instancia.setInstanciasAprobacionNombreObjeto(NOMBRE_OBJETO);
				instancia.setInstanciasAprobacionObjetoId(ordenCompraId);
				instancia.setInstanciasAprobacionUserFirmante(siguientesFirmantes.next());
				instancia.setInstanciasAprobacionOrden(cadena.getOrder());

			}
			
			// agrega el mensaje de revision
			instancia.setInstanciasAprobacionMensaje(ds.getObservaciones());
			instancia.update(conn);
			
			/*ds.setSolicitudesCompraFechaSolicitud(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));*/
			ds.update(conn);
			
			return true;
			
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba su revisión: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba su revisión: "
					+ ex.getMessage());
		}		

		return false;
	}

}
