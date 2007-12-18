/**
 *
 */
package proyectos.reglasNegocio;

import java.io.Serializable;
import java.sql.SQLException;

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;

/**
 * @author Demian
 *
 */
public class ValidaActividadProyecto implements DataStoreExpression,
		Serializable {
	private static final long serialVersionUID = 7236951794700822688L;

	//private String _colName;

	public proyectos.models.ActividadesProyectoModel _dsActividadesProyecto;
	/**
	 * @param name
	 */
	public ValidaActividadProyecto(int proyecto_id, int actividad_id) {
		//_dsActividadesProyecto.setCriteria();
		try {
			_dsActividadesProyecto.retrieve("proyecto_id = "+proyecto_id+ " and "+ "actividad_id = "+actividad_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.salmonllc.sql.DataStoreExpression#evaluateExpression(com.salmonllc.sql.DataStoreBuffer,
	 *      int)
	 */
	public Object evaluateExpression(DataStoreBuffer dsBuf, int row)
			throws DataStoreException {
		//String columna = dsBuf.getString(row, _colName);
		if (_dsActividadesProyecto.getRowCount() == 0)
			return Boolean.FALSE;
		return Boolean.TRUE;
	}

}
