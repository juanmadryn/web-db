package proyectos.models;

import infraestructura.utils.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ClasesTareaModel: A SOFIA generated model
 */
public class ClasesTareaModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7147358948201750669L;

	// constants for columns
	public static final String CLASES_TAREAS_CLASE_TAREA_ID = "clases_tareas.clase_tarea_id";

	public static final String CLASES_TAREAS_NOMBRE = "clases_tareas.nombre";

	public static final String CLASES_TAREAS_DESCRIPCION = "clases_tareas.descripcion";

	public static final String CLASES_TAREAS_OBSERVACIONES = "clases_tareas.observaciones";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ClasesTareaModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ClasesTareaModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ClasesTareaModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ClasesTareaModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("clases_tareas"), "clases_tareas");

			// add columns
			addColumn(computeTableName("clases_tareas"), "clase_tarea_id",
					DataStore.DATATYPE_INT, true, true,
					CLASES_TAREAS_CLASE_TAREA_ID);
			addColumn(computeTableName("clases_tareas"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					CLASES_TAREAS_NOMBRE);
			addColumn(computeTableName("clases_tareas"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					CLASES_TAREAS_DESCRIPCION);
			addColumn(computeTableName("clases_tareas"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					CLASES_TAREAS_OBSERVACIONES);

			// set order by
			setOrderBy(computeTableAndFieldName("clases_tareas.nombre")
					+ " ASC");

			// add validations
			addRequiredRule(CLASES_TAREAS_NOMBRE,
					"Nombre de la Clase es obligatorio");
			addExpressionRule(CLASES_TAREAS_NOMBRE,
					new ConvierteMayusculasValidation(CLASES_TAREAS_NOMBRE),
					"", false);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the clases_tareas.clase_tarea_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getClasesTareasClaseTareaId() throws DataStoreException {
		return getInt(CLASES_TAREAS_CLASE_TAREA_ID);
	}

	/**
	 * Retrieve the value of the clases_tareas.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getClasesTareasClaseTareaId(int row) throws DataStoreException {
		return getInt(row, CLASES_TAREAS_CLASE_TAREA_ID);
	}

	/**
	 * Set the value of the clases_tareas.clase_tarea_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasClaseTareaId(int newValue)
			throws DataStoreException {
		setInt(CLASES_TAREAS_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the clases_tareas.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasClaseTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CLASES_TAREAS_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the clases_tareas.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasNombre() throws DataStoreException {
		return getString(CLASES_TAREAS_NOMBRE);
	}

	/**
	 * Retrieve the value of the clases_tareas.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasNombre(int row) throws DataStoreException {
		return getString(row, CLASES_TAREAS_NOMBRE);
	}

	/**
	 * Set the value of the clases_tareas.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasNombre(String newValue)
			throws DataStoreException {
		setString(CLASES_TAREAS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the clases_tareas.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASES_TAREAS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the clases_tareas.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasDescripcion() throws DataStoreException {
		return getString(CLASES_TAREAS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the clases_tareas.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasDescripcion(int row) throws DataStoreException {
		return getString(row, CLASES_TAREAS_DESCRIPCION);
	}

	/**
	 * Set the value of the clases_tareas.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasDescripcion(String newValue)
			throws DataStoreException {
		setString(CLASES_TAREAS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the clases_tareas.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASES_TAREAS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the clases_tareas.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasObservaciones() throws DataStoreException {
		return getString(CLASES_TAREAS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the clases_tareas.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareasObservaciones(int row)
			throws DataStoreException {
		return getString(row, CLASES_TAREAS_OBSERVACIONES);
	}

	/**
	 * Set the value of the clases_tareas.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasObservaciones(String newValue)
			throws DataStoreException {
		setString(CLASES_TAREAS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the clases_tareas.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareasObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASES_TAREAS_OBSERVACIONES, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
