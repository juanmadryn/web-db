/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.SolicitudCompraModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Demian
 * 
 */
public final class ValRN_0202_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {

		DBConnection connection = null;
		try {
			connection = DBConnection.getConnection("inventario", "inventario");
			connection.beginTransaction();

			SolicitudCompraModel ds = (SolicitudCompraModel) obj;

			int solicitudCompraId = ds.getSolicitudesCompraSolicitudCompraId();

			String valorAtributo = AtributosEntidadModel
					.getValorAtributoObjeto("CONFIGURACION_ID",
							solicitudCompraId, "TABLA", "solicitudes_compra");

			int configuracion_id;

			if (valorAtributo != null)
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
					"nombre_objeto = 'solicitudes_compra' AND " +
					"objeto_id = " + solicitudCompraId +
					" AND user_firmante =" + currentWebsiteUser +
					" AND estado = 0007.0001"
					);

			if (!instancia.gotoFirst()) {
				msg
						.append("Usted no está autorizado para aprobar la solicitud en su estado actual");
				return false;
			}

			Iterator<Integer> siguientesFirmantes = cadena
					.getSiguientesFirmantes(true, instancia
							.getInstanciasAprobacionOrden());
			

			instancia.retrieve(
					"nombre_objeto = 'solicitudes_compra' AND " +
					"objeto_id = " + solicitudCompraId
					);
			instancia.firmarInstanciasAprobacionSolicitud(currentWebsiteUser,
					connection);

			if (siguientesFirmantes == null) {
				ds.setSolicitudesCompraFechaAprobacion(new Date((Calendar
						.getInstance().getTimeInMillis())));
				ds.update(connection);
				connection.commit();
				connection.freeConnection();
				return true;
			}

			while (siguientesFirmantes.hasNext()) {
				instancia.gotoRow(instancia.insertRow());
				instancia.setInstanciasAprobacionEstado("0007.0001");
				instancia.setInstanciasAprobacionFechaEntrada(new Date(
						(Calendar.getInstance().getTimeInMillis())));
				instancia.setInstanciasAprobacionNombreObjeto("solicitudes_compra");
				instancia.setInstanciasAprobacionObjetoId(solicitudCompraId);
				instancia
						.setInstanciasAprobacionUserFirmante(siguientesFirmantes
								.next());
				instancia.setInstanciasAprobacionOrden(cadena.getOrder());
			}
			instancia.update(connection);
			connection.commit();
			msg.append("Su aprobación fue registrada exitosamente");
		} catch (DataStoreException ex) {
			connection.rollback();
			msg
					.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;
		} catch (SQLException ex) {
			connection.rollback();
			msg
					.append("Ocurrió un error de SQL mientras se procesaba su aprobación: "
							+ ex.getMessage());
			return false;
		} finally {
			if (connection != null)
				connection.freeConnection();
		}
		return false;
	}

}
