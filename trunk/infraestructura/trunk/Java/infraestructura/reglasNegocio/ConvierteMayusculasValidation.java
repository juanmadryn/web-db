/**
 *
 */
package infraestructura.reglasNegocio;

import java.io.Serializable;

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;

/**
 * @author Demian
 *
 */
public class ConvierteMayusculasValidation implements DataStoreExpression,
		Serializable {
	private static final long serialVersionUID = 7236951794700822688L;

	private String _colName;

	/**
	 * @param name
	 */
	public ConvierteMayusculasValidation(String name) {
		_colName = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.salmonllc.sql.DataStoreExpression#evaluateExpression(com.salmonllc.sql.DataStoreBuffer,
	 *      int)
	 */
	public Object evaluateExpression(DataStoreBuffer dsBuf, int row)
			throws DataStoreException {
		String columna = dsBuf.getString(row, _colName);
		if (columna == null)
			return Boolean.TRUE;
		columna = columna.toUpperCase();
		dsBuf.setString(row, _colName, columna);

		return Boolean.TRUE;
	}

}
