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
import inventario.models.OrdenesCompraModel;
import inventario.models.SolicitudCompraModel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Cadena de firmas 
 * 
 */
public final class ValRN_0209_1 extends ValidadorReglasNegocio {
	
	private static final String NOMBRE_OBJETO = "ordenes_compra";

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

			OrdenesCompraModel ds = (OrdenesCompraModel) obj;

			int ordenCompraId = ds.getOrdenesCompraOrdenCompraId();

			String valorAtributo = AtributosEntidadModel
					.getValorAtributoObjeto("CONFIGURACION_ID",
							ordenCompraId, "TABLA", NOMBRE_OBJETO);

			int configuracion_id;

			if (valorAtributo != null)
				configuracion_id = Integer.parseInt(valorAtributo);
			else {
				configuracion_id = DeterminaConfiguracionServicio
						.determinaConfiguracion(AtributosConfiguracionModel
								.determinaAtributosConfiguración(ds
										.getEsquemaConfiguracionId(),
										ordenCompraId, "TABLA",
										NOMBRE_OBJETO));
				AtributosEntidadModel.setValorAtributoObjeto(String
						.valueOf(configuracion_id), "CONFIGURACION_ID",
						ordenCompraId, "TABLA", NOMBRE_OBJETO);
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
					"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId +
					" AND instancias_aprobacion.user_firmante =" + currentWebsiteUser +
					" AND instancias_aprobacion.estado = 0007.0001"
					);

			if (!instancia.gotoFirst()) {
				msg.append("Usted no está autorizado para aprobar la orden en su estado actual");
				return false;
			}

			Iterator<Integer> siguientesFirmantes = cadena
					.getSiguientesFirmantes(true, instancia
							.getInstanciasAprobacionOrden());
			

			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '"+ NOMBRE_OBJETO +"' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId
					);
			instancia.firmarInstanciasAprobacionSolicitud(currentWebsiteUser,
					connection);

			if (siguientesFirmantes == null) {
				ds.setOrdenesCompraFechaAprobacion(new Timestamp((Calendar
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
				instancia.setInstanciasAprobacionNombreObjeto(NOMBRE_OBJETO);
				instancia.setInstanciasAprobacionObjetoId(ordenCompraId);
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
