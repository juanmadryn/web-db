package inventario.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * UnidadesMedida: A SOFIA generated model
 */
public class UnidadesMedidaModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8140582268143498062L;
	// constants for columns
	public static final String UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID = "unidades_medida.unidad_medida_id";
	public static final String UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String UNIDADES_MEDIDA_DESCRIPCION = "unidades_medida.descripcion";
	public static final String UNIDADES_MEDIDA_OBSERVACIONES = "unidades_medida.observaciones";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new UnidadesMedida object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public UnidadesMedidaModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new UnidadesMedida object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public UnidadesMedidaModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida");

			// add columns
			addColumn(computeTableName("unidades_medida"), "unidad_medida_id",
					DataStore.DATATYPE_INT, true, true,
					UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID);
			addColumn(computeTableName("unidades_medida"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					UNIDADES_MEDIDA_NOMBRE);
			addColumn(computeTableName("unidades_medida"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					UNIDADES_MEDIDA_DESCRIPCION);
			addColumn(computeTableName("unidades_medida"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					UNIDADES_MEDIDA_OBSERVACIONES);

			// add validations
			addRequiredRule(UNIDADES_MEDIDA_NOMBRE,
					"El nombre de la unidad de medida es obligatorio");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the unidades_medida.unidad_medida_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getUnidadesMedidaUnidadMedidaId() throws DataStoreException {
		return getInt(UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the unidades_medida.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getUnidadesMedidaUnidadMedidaId(int row)
			throws DataStoreException {
		return getInt(row, UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Set the value of the unidades_medida.unidad_medida_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaUnidadMedidaId(int newValue)
			throws DataStoreException {
		setInt(UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the unidades_medida.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaUnidadMedidaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, UNIDADES_MEDIDA_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaNombre() throws DataStoreException {
		return getString(UNIDADES_MEDIDA_NOMBRE);
	}

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaNombre(int row) throws DataStoreException {
		return getString(row, UNIDADES_MEDIDA_NOMBRE);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaNombre(String newValue)
			throws DataStoreException {
		setString(UNIDADES_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDADES_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the unidades_medida.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaDescripcion() throws DataStoreException {
		return getString(UNIDADES_MEDIDA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the unidades_medida.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaDescripcion(int row)
			throws DataStoreException {
		return getString(row, UNIDADES_MEDIDA_DESCRIPCION);
	}

	/**
	 * Set the value of the unidades_medida.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaDescripcion(String newValue)
			throws DataStoreException {
		setString(UNIDADES_MEDIDA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the unidades_medida.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDADES_MEDIDA_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the unidades_medida.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaObservaciones() throws DataStoreException {
		return getString(UNIDADES_MEDIDA_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the unidades_medida.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaObservaciones(int row)
			throws DataStoreException {
		return getString(row, UNIDADES_MEDIDA_OBSERVACIONES);
	}

	/**
	 * Set the value of the unidades_medida.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaObservaciones(String newValue)
			throws DataStoreException {
		setString(UNIDADES_MEDIDA_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the unidades_medida.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDADES_MEDIDA_OBSERVACIONES, newValue);
	}

	public static String getUnidadMedidaNombre(int id) throws SQLException {
		DBConnection conn = DBConnection.getConnection("inventario");
		Statement st = conn.createStatement();
		ResultSet rs = st
				.executeQuery("SELECT u.nombre FROM inventario.unidades_medida u WHERE u.unidad_medida_id = "
						+ id);
		if (rs.first())
			return rs.getString(1);
		return "Unidad de medida no indicada para el artículo";
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
