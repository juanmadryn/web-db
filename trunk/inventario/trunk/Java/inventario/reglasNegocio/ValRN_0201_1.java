/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.DetalleSCModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.SolicitudCompraModel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Demian
 * 
 */
public final class ValRN_0201_1 extends ValidadorReglasNegocio {

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

			int solicitudCompraId = ds.getSolicitudesCompraSolicitudCompraId();

			if (detalles
					.estimateRowsRetrieved("detalle_sc.solicitud_compra_id ="
							+ solicitudCompraId) == 0) {
				msg.append("Debe detallar por lo menos un artículo a comprar");
				return false;
			}
			/*if (!detalles.chequeaTotalesDetallesSolicitud(solicitudCompraId)) {
				msg
						.append("Debe indicar el monto unitario de todos los articulos antes de completar la solicitud");
				return false;
			}*/

			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, "COMPRADOR")
					&& ds.getSolicitudesCompraUserIdSolicita() != currentUser) {
				msg
						.append("Debe ser COMPRADOR o el solicitante original para completar o revisar una Solicitud.");
				return false;
			}

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
						.determinaConfiguracion(AtributosConfiguracionModel.determinaAtributosConfiguracion(ds
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
			instancia.update(conn);
			
			ds.setSolicitudesCompraFechaSolicitud(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));
			ds.update(conn);
			
			return true;
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
	}

}
