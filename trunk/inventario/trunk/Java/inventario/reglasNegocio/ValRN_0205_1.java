package inventario.reglasNegocio;

import java.sql.SQLException;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.SolicitudCompraModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

public class ValRN_0205_1 extends ValidadorReglasNegocio {

	@Override
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn)
			throws ValidationException {
		
		DBConnection conexion = null;
		
		if (obj instanceof SolicitudCompraModel) {
			try {				
				SolicitudCompraModel sc = (SolicitudCompraModel) obj;
				
				DetalleSCModel _dsDetalleSC = new DetalleSCModel("inventario","inventario");
				
				String estado = sc.getSolicitudesCompraEstado();
				
				if ("0006.0007".equalsIgnoreCase(estado)) {
					if (_dsDetalleSC.estimateRowsRetrieved(DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID + " = " +
							sc.getSolicitudesCompraSolicitudCompraId() + " AND " + DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " IS NULL") > 0) {
						return true;
					} else { 
						msg.append("Todos los articulos de esta SC se encuentran asociados con una OC");
						return false;
					}
				} 
				
				if ("0006.0006".equalsIgnoreCase(estado)) {
					if (_dsDetalleSC.estimateRowsRetrieved(DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID + " = " +
							sc.getSolicitudesCompraSolicitudCompraId() + " AND " + DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " IS NOT NULL") == 0) {
						return true;
					} else { 
						msg.append("Algunos articulos de esta SC se encuentran asociados con una OC");
						return false;
					}
				} 
					
				return false;
			} catch (SQLException e) {
				msg
				.append("Ocurrió un error en el DataStore mientras se procesaba la generación: "
						+ e.getMessage());
				MessageLog.writeErrorMessage(e, null);
				return false;
			} catch (DataStoreException e) {
				msg
				.append("Ocurrió un error en el DataStore mientras se procesaba su generación: "
						+ e.getMessage());
				MessageLog.writeErrorMessage(e, null);
				return false;
			} finally {
				if (conexion != null) {
					conexion.rollback();
					conexion.freeConnection();
				}
			}
		}
		
		return false;
	}

}
