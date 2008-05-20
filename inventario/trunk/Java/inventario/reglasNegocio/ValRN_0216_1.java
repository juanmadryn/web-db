package inventario.reglasNegocio;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.DetalleSCModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.SolicitudCompraModel;

/**
 * Agrega al circuito de SC una acción que permita pasar de aprobada /
 * cotizada a revisión. Esta situación se debe ante la situación de cambio de
 * artículo de una Solicitud de Compra en la etapa de cotización (por ejemplo
 * impedimento de conseguir una marca en especial). El comprador por lo tanto
 * debe pasar la solicitud al solicitante indicándole al situación y
 * pidiéndole el cambio del artículo.
 * 
 * Issue 10: Agregar al circuito de SC una acción que permita pasar de aprobada / cotizada a revisión
 * http://code.google.com/p/web-db/issues/detail?id=10
 * 
 * @author fep
 *
 */
public class ValRN_0216_1 extends ValidadorReglasNegocio {

	@Override
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn)
			throws ValidationException {
		
		try {
			SolicitudCompraModel ds = (SolicitudCompraModel) obj;
			
			int solicitudCompraId = ds.getSolicitudesCompraSolicitudCompraId();
			
			// chequeo que el usuario tenga el rol COMPRADOR
			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")) {
				msg.append("Debe ser COMPRADOR o el solicitante original revisar una solicitud.");
				return false;
			}			
			
			// checkeo si la solicitud tiene seteadas las obervaciones
			if (ds.getObservaciones() == null || ds.getObservaciones().trim().length() < 5) {
				msg.append("Debe completar el cuadro de observaciones con la información suficiente para corregir la solicitud");
				return false;
			}
			
			// genera nueva cadena de aprobacion
			InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
					"inventario", "inventario");
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = 'solicitudes_compra' AND " +
					"instancias_aprobacion.objeto_id = " + solicitudCompraId +
					" AND instancias_aprobacion.estado = 0007.0001"
					);
			if (instancia.gotoFirst()) {
				return true;
			}

			String valorAtributo = AtributosEntidadModel
					.getValorAtributoObjeto("CONFIGURACION_ID",
							solicitudCompraId, "TABLA", "solicitudes_compra");

			int configuracion_id;

			if (valorAtributo != null && valorAtributo != "0")
				configuracion_id = Integer.parseInt(valorAtributo);
			else {
				configuracion_id = DeterminaConfiguracionServicio
						.determinaConfiguracion(AtributosConfiguracionModel
								.determinaAtributosConfiguración(ds
										.getEsquemaConfiguracionId(),
										solicitudCompraId, "TABLA",
										"solicitudes_compra"));

				AtributosEntidadModel.setValorAtributoObjeto(String
						.valueOf(configuracion_id), "CONFIGURACION_ID",
						solicitudCompraId, "TABLA", "solicitudes_compra");
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
				instancia.setInstanciasAprobacionNombreObjeto("solicitudes_compra");
				instancia.setInstanciasAprobacionObjetoId(solicitudCompraId);
				instancia.setInstanciasAprobacionUserFirmante(siguientesFirmantes.next());
				instancia.setInstanciasAprobacionOrden(cadena.getOrder());

			}
			
			// agrega el mensaje de revision
			instancia.setInstanciasAprobacionMensaje(ds.getObservaciones());
			instancia.update(conn);
			
			ds.setSolicitudesCompraFechaSolicitud(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));
			ds.update(conn);
			
			return true;
			
		} catch (DataStoreException ex) {
			msg.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			msg.append("Ocurrió un error de SQL mientras se procesaba su aprobación: "
					+ ex.getMessage());
		}		

		return false;
	}

}
