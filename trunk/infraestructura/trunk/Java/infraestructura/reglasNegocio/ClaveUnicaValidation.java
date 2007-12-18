/**
 * 
 */
package infraestructura.reglasNegocio;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.util.MessageLog;

/**
 * @author Demian
 *
 */
public class ClaveUnicaValidation implements DataStoreExpression,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -770342785111717383L;
	private String _tableName;
	private String _colName;
	private boolean _convierteMayuscula;
	private String _appName;
	private String _connectName;

	/**
	 * @param name
	 */
	public ClaveUnicaValidation(String tableName, String colName, boolean mayuscula, String appName, String connectName ) {
		_tableName = tableName;
		_colName = colName;
		_convierteMayuscula = mayuscula;
		_appName = appName;
		_connectName = connectName;
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.sql.DataStoreExpression#evaluateExpression(com.salmonllc.sql.DataStoreBuffer, int)
	 */
	public Object evaluateExpression(DataStoreBuffer dsBuf, int row) throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;

		String columna = dsBuf.getString(row, _colName);
		
		if (_convierteMayuscula)
			columna = columna.toUpperCase();
		
		try {
			conexion = DBConnection.getConnection(_appName,_connectName);

		   // ahora busco el primer estado posible según la máquina de estados
			SQL = "select 'x' "
					+ " from " + _tableName
					+ " where " + _colName + " = '" + columna + "'"; 
			st = conexion.createStatement();
			r = st.executeQuery(SQL);

			if (r.first())
				return Boolean.FALSE;
			else
				return Boolean.TRUE;

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException("Error determinando clave duplicada: "+ e.getMessage());
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conexion != null)
				conexion.freeConnection();
		}
	}

}
