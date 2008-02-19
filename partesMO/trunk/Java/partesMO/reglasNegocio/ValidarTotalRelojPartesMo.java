package partesMO.reglasNegocio;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;

import java.sql.SQLException;
import java.sql.Timestamp;

import partesMO.models.LogValidacionPartesMoModel;
import partesMO.models.ResumenHorasRelojModel;
import partesMO.models.ValidacionesPartesMoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * 
 * @author fep
 *
 * validacion manual de partes 
 */
public class ValidarTotalRelojPartesMo extends ValidadorReglasNegocio {

	private int validacion_id;
	private ResumenHorasRelojModel _dsResHor;
	private LogValidacionPartesMoModel _dsLogVal;
	private DBConnection conn;
	private Timestamp fechaEjecValidacion;
	private String estado_valido = "0003.0003"; // valido	

	public ValidarTotalRelojPartesMo() { }

	private boolean doValidacion() throws SQLException, DataStoreException {
		// obtiene el id de la entrada de la clase en la tabla de validaciones
		ValidacionesPartesMoModel validaciones = new ValidacionesPartesMoModel(_dsResHor.getAppName(), _dsResHor.getDbProfile());		
		validaciones.retrieve("validador = '" + getClass().getSimpleName() + "'");
		validaciones.gotoFirst();
		validacion_id = validaciones.getValidacionesPartesMoValidacionId();
		
		fechaEjecValidacion = new Timestamp(System.currentTimeMillis());
		
		for (int i = 0; i < _dsResHor.getRowCount(); i++) {
			String[] st = _dsResHor.getResumenHorasRelojParteIds(i).split(",");
			for (String id : st) {
				int row = _dsLogVal.insertRow();
				_dsLogVal.setLogValidacionPartesMoFecha(row, fechaEjecValidacion);
				_dsLogVal.setLogValidacionPartesMoEstado(row, estado_valido);
				_dsLogVal.setLogValidacionPartesMoValidacionId(row, validacion_id);
				_dsLogVal.setLogValidacionPartesMoParteId(row, Integer.valueOf(id));
			}
			_dsResHor.setResumenHorasRelojEstado(i, ResumenHorasRelojModel.PARTES_VAL);
			_dsResHor.setResumenHorasRelojObservaciones(i, ResumenHorasRelojModel.PARTES_VAL_MSG);
		}
		_dsLogVal.update(conn);
		_dsResHor.update(conn);
		
		return true;
	}
	
	@Override
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {		
		_dsResHor = (ResumenHorasRelojModel) obj;
		_dsLogVal = new LogValidacionPartesMoModel(_dsResHor.getAppName(), _dsResHor.getDbProfile());
		_dsLogVal.setBatchInserts(Boolean.TRUE);		
		this.conn = conn;		
		try {
			return doValidacion();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		}		
		return false;
	}

}
