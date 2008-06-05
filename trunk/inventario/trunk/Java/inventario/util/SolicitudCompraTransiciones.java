package inventario.util;

import java.sql.SQLException;
import java.util.Vector;

import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.SolicitudCompraModel;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Metodos utiles para la transiciones de estados de solicitudes para
 * estados OC parcial y completa.
 * 
 * Se debe agregar en el System.properties de la aplicacion los Ids de las acciones 
 * a utilizar. Ejemplo:
 * 
 *  ## Id de Acciones Solicitudes de Compra
 *	GENERAR_OC = 18
 *	COMPLETAR_OC = 19
 *	REVERTIR_A_OC_PARCIAL = 20
 *	REVERTIR_A_SC_APROBADA = 21
 *	REVERTIR_A_SC_COTIZADA = 35
 * 
 * @author fep
 *
 */
public class SolicitudCompraTransiciones {
	
	// Estados
	private static String ESTADO_APROBADA = "0006.0003";
	private static String ESTADO_COTIZADA = "0006.0008";
	private static String ESTADO_OC_COMPLETA = "0006.0007";
	private static String ESTADO_OC_PARCIAL = "0006.0006";
	
	// Acciones
	private static int GENERAR_OC = 18;
	private static int COMPLETAR_OC = 19;
	private static int REVERTIR_A_OC_PARCIAL = 20;
	private static int REVERTIR_A_SC_APROBADA = 21;
	private static int REVERTIR_A_SC_COTIZADA = 35;
	private static int FIN_DE_COTIZACION = 54;
	
	// Circuito
	private static String CIRCUITO_SC = "0006";
	
	// Nombre de Objeto (Tabla Solicitudes)
	private static String NOMBRE_TABLA_SC = "solicitudes_compra";
	
	/**
	 * Ejecuta acciones de cambios de estado cuando se remueve un detalle de SC de una OC.
	 * Las acciones que se ejecutan son las siguientes:
	 * 
	 * 1) Con OC Completa --> Con OC Parcial
	 * 
	 * 2) Con OC Parcial --> Aprobada si la Solicitud no cuenta con una cotización
	 * 	  Con OC Parcial --> Cotizada si la Solicitud tiene una cotizacion generada
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
		
		// recuperamos acciones y estados
		recuperaEstadosAcciones();
		
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel(
				"inventario");
		
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {					
			if (ESTADO_OC_COMPLETA.equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
				dsSolicitudCompra.retrieve(conn,
						SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
						" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
				);
				dsSolicitudCompra.gotoFirst();
				try {
					dsSolicitudCompra.ejecutaAccion(REVERTIR_A_OC_PARCIAL, CIRCUITO_SC, 
							remoteHost,
							webSiteUserId, NOMBRE_TABLA_SC,
							conn, false);
					_dsDetalleSC.reloadRow(conn, i);
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}
			}					
		}				

		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			if (ESTADO_OC_PARCIAL.equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
				dsSolicitudCompra.retrieve(conn,
						SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
						" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
				);
				dsSolicitudCompra.gotoFirst();
				try {
					// si la solicitud tiene cotizacion revertimos a el estado cotizada
					if (_dsDetalleSC.getDetalleScCotizacionCompraId(i) > 0) {
						dsSolicitudCompra.ejecutaAccion(REVERTIR_A_SC_COTIZADA,	CIRCUITO_SC,
								remoteHost,
								webSiteUserId,
								NOMBRE_TABLA_SC, conn, false);
					}
					// si la solicitud no tiene cotizacion revertimos a el estado aprobada
					else {
						dsSolicitudCompra.ejecutaAccion(REVERTIR_A_SC_APROBADA,	CIRCUITO_SC,
								remoteHost,
								webSiteUserId,
								NOMBRE_TABLA_SC, conn, false);
					}
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);						
				}
			}
		}
	}
	
	/**
	 * Ejecuta acciones de cambios de estado cuando se agrega un detalle de SC de una OC.
	 * Las acciones que se ejecutan son las siguientes:
	 * 
	 * 1) Si la SC se encuentra aprobada: Aprobada --> Con OC Parcial 
	 * 	  Si la SC se encuentra cotizada: Cotizada --> Con OC Parcial
	 * 
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
			throws DataStoreException, SQLException, ValidationException {
		
		// recuperamos acciones y estados
		recuperaEstadosAcciones();
			
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel("inventario");
		
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			dsSolicitudCompra.retrieve(conn,
					SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
					" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
					);					
			dsSolicitudCompra.gotoFirst();					
			
			// aprobada
			if (ESTADO_APROBADA.equalsIgnoreCase(dsSolicitudCompra.getEstadoActual())) {
				try {
					dsSolicitudCompra.ejecutaAccion(GENERAR_OC,	CIRCUITO_SC,
							remoteHost,	webSiteUserId, 
							NOMBRE_TABLA_SC, conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}	
			// cotizada
			} else if (ESTADO_COTIZADA.equalsIgnoreCase(dsSolicitudCompra.getEstadoActual())) {
				try {
					dsSolicitudCompra.ejecutaAccion(GENERAR_OC,	CIRCUITO_SC,
							remoteHost,	webSiteUserId, 
							NOMBRE_TABLA_SC, conn, false);	
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
			
			if (ESTADO_OC_PARCIAL.equalsIgnoreCase(dsSolicitudCompra.getEstadoActual())) {
				try {
					dsSolicitudCompra.ejecutaAccion(COMPLETAR_OC, CIRCUITO_SC,
							remoteHost,	webSiteUserId, 
							NOMBRE_TABLA_SC, conn, false);	
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
				}											
			}
		}	
	}
	
	public static void cotizarSolicitudCompra(DBConnection conn, SolicitudCompraModel solicitud, 
			String remoteHost, int webSiteUserId) throws SQLException, DataStoreException {
		cotizarSolicitudCompra(conn, solicitud.getSolicitudesCompraSolicitudCompraId(), remoteHost, webSiteUserId);
	}
	
	/**
	 * Ejecuta la transicion a cotizada
	 * 
	 * @param conn
	 * @param _dsDetalleSC
	 * @param remoteHost
	 * @param webSiteUserId
	 * @throws SQLException 
	 */
	public static void cotizarSolicitudCompra(DBConnection conn, int SolicitudId, 
			String remoteHost, int webSiteUserId) throws SQLException {
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel("inventario");
		dsSolicitudCompra.retrieve(
				conn, SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID + " = " + SolicitudId);
		
		try {
			dsSolicitudCompra.ejecutaAccion(FIN_DE_COTIZACION, CIRCUITO_SC,
					remoteHost,	webSiteUserId, 
					NOMBRE_TABLA_SC, conn, false);	
		} catch (DataStoreException ex) {
			MessageLog.writeErrorMessage(ex, null);
		}
	}
	
	/**
	 * Recupera los ids de las acciones a ejecutar desde el archivo de propiedades.
	 * TODO: el codigo funciona, pero es un asco, refactorear.
	 */
	private static void recuperaEstadosAcciones() {
		Props props = Props.getProps("inventario", null);
		Vector<String> errores = new Vector<String>();		
		
		int generarOcProp = props.getIntProperty("GENERAR_OC");
		if (generarOcProp == -1) 
			errores.add("No se ha indicado acción GENERAR_OC en archivo de configuración");
		else
			GENERAR_OC = generarOcProp;
		
		int completarOcProp = props.getIntProperty("COMPLETAR_OC");
		if (completarOcProp == -1)
			errores.add("No se ha indicado acción COMPLETAR_OC en archivo de configuración");
		else
			COMPLETAR_OC = completarOcProp;
		
		int revertirOcParcialProp = props.getIntProperty("REVERTIR_A_OC_PARCIAL");
		if (revertirOcParcialProp == -1) 
			errores.add("No se ha indicado acción REVERTIR_A_OC_PARCIAL en archivo de configuración");
		else
			REVERTIR_A_OC_PARCIAL = revertirOcParcialProp;
		
		int revertirScAprobadaProp = props.getIntProperty("REVERTIR_A_SC_APROBADA");
		if (revertirScAprobadaProp == -1) 
			errores.add("No se ha indicado acción REVERTIR_A_SC_APROBADA en archivo de configuración");
		else
			REVERTIR_A_SC_APROBADA = revertirScAprobadaProp;
		
		int revertirScCotizadaProp = props.getIntProperty("REVERTIR_A_SC_COTIZADA");
		if (revertirScCotizadaProp == -1) 
			errores.add("No se ha indicado acción REVERTIR_A_SC_COTIZADA en archivo de configuración");
		else
			REVERTIR_A_SC_COTIZADA = revertirScCotizadaProp;
		
		int finCotizacionProp = props.getIntProperty("FIN_DE_COTIZACION");
		if (revertirScCotizadaProp == -1) 
			errores.add("No se ha indicado acción FIN_DE_COTIZACION en archivo de configuración");
		else
			FIN_DE_COTIZACION = finCotizacionProp;
		
		if (errores.size() > 0) 
			throw new ValidationException(errores);
	}

}
