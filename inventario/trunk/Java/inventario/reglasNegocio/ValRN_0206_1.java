package inventario.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.DetalleSCModel;
import inventario.models.SolicitudCompraModel;

import java.sql.SQLException;

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
					msg.append("No existen articulos con OC asociada en esta SC");
					return false;
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



