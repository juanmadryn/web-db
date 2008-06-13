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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de Negocio.
 * 
 * Objeto asociado: ordenes_compra
 * 
 * Transici�n: "Completa" -> "Aprobada"
 * 
 * Acci�n: "Aprobar"
 * 
 * Regla de negocio asociada con la validaci�n de la cadena de firmas
 * de una orden de compra. Si se consume la cadena de firmas de manera
 * exitosa, se pasa al estado "Aprobada".
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
			InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
					"inventario", "inventario");

			cadena.retrieve("configuracion_id = " + configuracion_id);

			if (!cadena.gotoFirst()) {
				msg.append("No se recupero ninguna cadena de aprobaci�n!");
				return false;
			}

			int currentWebsiteUser = ds.getCurrentWebsiteUserId();

			// est� pendiente la aprobaci�n del usuario actual?
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId +
					" AND instancias_aprobacion.user_firmante =" + currentWebsiteUser +
					" AND instancias_aprobacion.estado = 0007.0001"
					);
			
			if (!instancia.gotoFirst()) {
				msg.append("Usted no est� autorizado para aprobar la orden en su estado actual");
				return false;
			}
			
			int orden = instancia.getInstanciasAprobacionOrden();
			Iterator<Integer> siguientesFirmantes = cadena
					.getSiguientesFirmantes(true, orden);
			
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '"+ NOMBRE_OBJETO +"' AND " +
					"instancias_aprobacion.objeto_id = " + ordenCompraId
					);

			instancia.firmarInstanciasAprobacionSolicitud(currentWebsiteUser,
					orden, connection);
			
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
				instancia.setInstanciasAprobacionFechaEntrada(new Timestamp(
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
			msg.append("Su aprobaci�n fue registrada exitosamente");
			
		} catch (DataStoreException ex) {
			connection.rollback();
			msg.append("Ocurri� un error en el DataStore mientras se procesaba su aprobaci�n: " 
					+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			connection.rollback();
			msg.append("Ocurri� un error de SQL mientras se procesaba su aprobaci�n: "
					+ ex.getMessage());
			return false;
		} finally {
			if (connection != null)
				connection.freeConnection();
		}
		return false;
	}

}
