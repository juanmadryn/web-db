package partesMO.reglasNegocio;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import partesMO.models.LogValidacionPartesMoModel;
import partesMO.models.ResumenHorasRelojModel;
import partesMO.models.ValidacionesPartesMoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;

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

	public ValidarTotalRelojPartesMo() {		
	}

	private boolean doValidacion() throws SQLException, DataStoreException {
		// obtiene el id de la entrada de la clase en la tabla de validaciones
		ValidacionesPartesMoModel validaciones = new ValidacionesPartesMoModel(_dsResHor.getAppName(), _dsResHor.getDbProfile());		
		validaciones.retrieve("validador = '" + getClass().getSimpleName() + "'");
		validaciones.gotoFirst();
		validacion_id = validaciones.getValidacionesPartesMoValidacionId();
		
		fechaEjecValidacion = new Timestamp(System.currentTimeMillis());
		
		for (int i = 0; i < _dsResHor.getRowCount(); i++) {
			StringTokenizer st = new StringTokenizer(_dsResHor.getResumenHorasRelojParteIds(i),",");
			while (st.hasMoreElements()) {
				int row = _dsLogVal.insertRow();
				_dsLogVal.setLogValidacionPartesMoFecha(row, fechaEjecValidacion);
				_dsLogVal.setLogValidacionPartesMoEstado(row, estado_valido);
				_dsLogVal.setLogValidacionPartesMoValidacionId(row, validacion_id);
				_dsLogVal.setLogValidacionPartesMoParteId(row, Integer.valueOf(st.nextToken()));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}

}
