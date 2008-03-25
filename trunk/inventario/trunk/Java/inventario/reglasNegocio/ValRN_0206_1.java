package inventario.reglasNegocio;

import java.sql.SQLException;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.OrdenesCompraModel;
import inventario.models.SolicitudCompraModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

public class ValRN_0206_1 extends ValidadorReglasNegocio {

	@Override
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn)
		throws ValidationException {

		if (obj instanceof SolicitudCompraModel) {
			try {				
				SolicitudCompraModel sc = (SolicitudCompraModel) obj;

				DetalleSCModel _dsDetalleSC = new DetalleSCModel("inventario","inventario");
				
				if (_dsDetalleSC.estimateRowsRetrieved(conn, DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID + " = " +
						sc.getSolicitudesCompraSolicitudCompraId() + " AND " + DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " IS NOT NULL") == 0) {

					OrdenesCompraModel _dsOrdenCompra = new OrdenesCompraModel("inventario");

					int ocId = _dsOrdenCompra.insertRow();

					_dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(ocId, 1);
					_dsOrdenCompra.setOrdenesCompraUserIdComprador(ocId, sc.getCurrentWebsiteUserId());

					_dsOrdenCompra.update(conn);

					_dsDetalleSC.retrieve(DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID + " = " +
							sc.getSolicitudesCompraSolicitudCompraId() + " AND " + DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " IS NULL");
					
					for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
						if (_dsDetalleSC.getDetalleScOrdenCompraId(i) == 0) {
							_dsDetalleSC.setDetalleScOrdenCompraId(i, 
									_dsOrdenCompra.getOrdenesCompraOrdenCompraId(ocId));							
						}
						if (_dsDetalleSC.getDetalleScCantidadPedida(i) == 0) {
							_dsDetalleSC.setDetalleScCantidadPedida(i, 
									_dsDetalleSC.getDetalleScCantidadSolicitada(i));
						}
					}
					_dsDetalleSC.update(conn);
				}

				return true;

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
			} 			
		} else
			return false;
	}
}



