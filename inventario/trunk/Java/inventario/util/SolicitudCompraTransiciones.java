package inventario.util;

import java.sql.SQLException;

import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.SolicitudCompraModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Metodos utiles para la transiciones de estados de solicitudes para
 * estados OC parcial y completa
 * 
 * @author fep
 *
 */
public class SolicitudCompraTransiciones {
	
	
	/**
	 * Ejecuta acciones de cambios de estado cuando se remueve un detalle de SC de una OC.
	 * Las acciones que se ejecutan son las siguientes:
	 * 1) Con OC Completa --> Con OC Parcial
	 * 2) Con OC Parcial --> Aprobada
	 * 
	 * @param conn conexion en la que se enmarca la transaccion
	 * @param _dsDetalleSC detalles asociados a una OC
	 * @param remoteHost host remoto
	 * @param webSiteUserId usuario remoto
	 * @throws ValidationException 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	static public void remueveDeOc(DBConnection conn,
			DetalleSCModel _dsDetalleSC, String remoteHost, int webSiteUserId)
			throws ValidationException, DataStoreException, SQLException {
		
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel(
				"inventario");
		
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {					
			if ("0006.0007".equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
				dsSolicitudCompra.retrieve(conn,
						SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
						" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
				);
				dsSolicitudCompra.gotoFirst();
				try {
					dsSolicitudCompra.ejecutaAccion(20, "0006", 
							remoteHost,
							webSiteUserId, "solicitudes_compra",
							conn, false);
					_dsDetalleSC.reloadRow(conn, i);
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}
			}					
		}				

		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			if ("0006.0006".equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
				dsSolicitudCompra.retrieve(conn,
						SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
						" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
				);
				dsSolicitudCompra.gotoFirst();
				try {
					dsSolicitudCompra.ejecutaAccion(21,	"0006",
							remoteHost,
							webSiteUserId,
							"solicitudes_compra", conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}														
			}
		}
	}
	
	/**
	 * Ejecuta acciones de cambios de estado cuando se agrega un detalle de SC de una OC.
	 * Las acciones que se ejecutan son las siguientes:
	 * 1) Aprobada --> Con OC Parcial
	 * 2) Con OC Parcial --> Con OC Completa
	 * 
	 * @param conn conexion en la que se enmarca la transaccion
	 * @param _dsDetalleSC detalles asociados a una OC
	 * @param remoteHost host remoto
	 * @param webSiteUserId usuario remoto
	 * @throws ValidationException
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	static public void agregarEnOc(DBConnection conn,
			DetalleSCModel _dsDetalleSC, String remoteHost, int webSiteUserId)
			throws ValidationException, DataStoreException, SQLException {
			
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel("inventario");
		
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			dsSolicitudCompra.retrieve(conn,
					SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
					" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
					);					
			dsSolicitudCompra.gotoFirst();					
			
			if ("0006.0003".equalsIgnoreCase(dsSolicitudCompra.getEstadoActual())) {
				try {
					dsSolicitudCompra.ejecutaAccion(18,	"0006",
							remoteHost,	webSiteUserId, 
							"solicitudes_compra", conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}		
			}
		}
		
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			dsSolicitudCompra.retrieve(conn,
					SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
					" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
					);					
			dsSolicitudCompra.gotoFirst();
			
			if ("0006.0006".equalsIgnoreCase(dsSolicitudCompra.getEstadoActual())) {
				try {
					dsSolicitudCompra.ejecutaAccion(19,	"0006",
							remoteHost,	webSiteUserId, 
							"solicitudes_compra", conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}											
			}
		}	
	}

}