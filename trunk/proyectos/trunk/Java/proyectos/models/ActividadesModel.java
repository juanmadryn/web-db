package proyectos.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ActividadesModel: A SOFIA generated model
 */
public class ActividadesModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3593767571379555497L;

	// constants for columns
	public static final String ACTIVIDADES_ACTIVIDAD_ID = "actividades.actividad_id";

	public static final String ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String ACTIVIDADES_DESCRIPCION = "actividades.descripcion";

	public static final String ACTIVIDADES_OBSERVACIONES = "actividades.observaciones";

	public static final String ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ActividadesModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ActividadesModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ActividadesModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ActividadesModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("actividades"), "actividades");
			addTableAlias(computeTableName("actividades"), "actividad_padre");

			// add columns
			addColumn(computeTableName("actividades"), "actividad_id",
					DataStore.DATATYPE_INT, true, true,
					ACTIVIDADES_ACTIVIDAD_ID);
			addColumn(computeTableName("actividades"), "nombre",
					DataStore.DATATYPE_STRING, false, true, ACTIVIDADES_NOMBRE);
			addColumn(computeTableName("actividades"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					ACTIVIDADES_DESCRIPCION);
			addColumn(computeTableName("actividades"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					ACTIVIDADES_OBSERVACIONES);
			addColumn(computeTableName("actividades"), "actividad_id_padre",
					DataStore.DATATYPE_INT, false, true,
					ACTIVIDADES_ACTIVIDAD_ID_PADRE);
			addColumn(computeTableName("actividades"), "es_hoja",
					DataStore.DATATYPE_STRING, false, true, ACTIVIDADES_ES_HOJA);
			addColumn(computeTableName("actividad_padre"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ACTIVIDAD_PADRE_NOMBRE);

			// add joins
			addJoin(computeTableAndFieldName("actividades.actividad_id_padre"),
					computeTableAndFieldName("actividad_padre.actividad_id"),
					true);

			// set order by
			setOrderBy(computeTableAndFieldName("actividades.nombre") + " ASC");

			// add validations
			addRequiredRule(ACTIVIDADES_NOMBRE,
					"El nombre de la actividad es obligatorio");
			addRequiredRule(ACTIVIDADES_ES_HOJA,
					"Indicar si la actividad es hoja o padre es obligatorio");
			addExpressionRule(ACTIVIDADES_NOMBRE,
					new ConvierteMayusculasValidation(ACTIVIDADES_NOMBRE), "",
					false);
			addLookupRule(
					ACTIVIDADES_ACTIVIDAD_ID_PADRE,
					"actividades",
					"'actividades.actividad_id = ' + actividades.actividad_id_padre",
					"nombre", "actividad_padre.nombre",
					"Actividad padre inexistente");

			setAutoIncrement(ACTIVIDADES_ACTIVIDAD_ID, true);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the actividades.actividad_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadId() throws DataStoreException {
		return getInt(ACTIVIDADES_ACTIVIDAD_ID);
	}

	/**
	 * Retrieve the value of the actividades.actividad_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadId(int row) throws DataStoreException {
		return getInt(row, ACTIVIDADES_ACTIVIDAD_ID);
	}

	/**
	 * Set the value of the actividades.actividad_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadId(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_ACTIVIDAD_ID, newValue);
	}

	/**
	 * Set the value of the actividades.actividad_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_ACTIVIDAD_ID, newValue);
	}

	/**
	 * Retrieve the value of the actividades.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesNombre() throws DataStoreException {
		return getString(ACTIVIDADES_NOMBRE);
	}

	/**
	 * Retrieve the value of the actividades.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesNombre(int row) throws DataStoreException {
		return getString(row, ACTIVIDADES_NOMBRE);
	}

	/**
	 * Set the value of the actividades.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesNombre(String newValue) throws DataStoreException {
		setString(ACTIVIDADES_NOMBRE, newValue);
	}

	/**
	 * Set the value of the actividades.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDADES_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the actividades.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesDescripcion() throws DataStoreException {
		return getString(ACTIVIDADES_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the actividades.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesDescripcion(int row) throws DataStoreException {
		return getString(row, ACTIVIDADES_DESCRIPCION);
	}

	/**
	 * Set the value of the actividades.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesDescripcion(String newValue)
			throws DataStoreException {
		setString(ACTIVIDADES_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the actividades.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDADES_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the actividades.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesObservaciones() throws DataStoreException {
		return getString(ACTIVIDADES_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the actividades.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesObservaciones(int row)
			throws DataStoreException {
		return getString(row, ACTIVIDADES_OBSERVACIONES);
	}

	/**
	 * Set the value of the actividades.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesObservaciones(String newValue)
			throws DataStoreException {
		setString(ACTIVIDADES_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the actividades.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDADES_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the actividades.actividad_id_padre column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadIdPadre() throws DataStoreException {
		return getInt(ACTIVIDADES_ACTIVIDAD_ID_PADRE);
	}

	/**
	 * Retrieve the value of the actividades.actividad_id_padre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadIdPadre(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDADES_ACTIVIDAD_ID_PADRE);
	}

	/**
	 * Set the value of the actividades.actividad_id_padre column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadIdPadre(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_ACTIVIDAD_ID_PADRE, newValue);
	}

	/**
	 * Set the value of the actividades.actividad_id_padre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadIdPadre(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_ACTIVIDAD_ID_PADRE, newValue);
	}

	/**
	 * Retrieve the value of the actividades.es_hoja column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesEsHoja() throws DataStoreException {
		return getString(ACTIVIDADES_ES_HOJA);
	}

	/**
	 * Retrieve the value of the actividades.es_hoja column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadesEsHoja(int row) throws DataStoreException {
		return getString(row, ACTIVIDADES_ES_HOJA);
	}

	/**
	 * Set the value of the actividades.es_hoja column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesEsHoja(String newValue) throws DataStoreException {
		setString(ACTIVIDADES_ES_HOJA, newValue);
	}

	/**
	 * Set the value of the actividades.es_hoja column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesEsHoja(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDADES_ES_HOJA, newValue);
	}

	/**
	 * Retrieve the value of the actividad_padre.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadPadreNombre() throws DataStoreException {
		return getString(ACTIVIDAD_PADRE_NOMBRE);
	}

	/**
	 * Retrieve the value of the actividad_padre.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadPadreNombre(int row) throws DataStoreException {
		return getString(row, ACTIVIDAD_PADRE_NOMBRE);
	}

	/**
	 * Set the value of the actividad_padre.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadreNombre(String newValue)
			throws DataStoreException {
		setString(ACTIVIDAD_PADRE_NOMBRE, newValue);
	}

	/**
	 * Set the value of the actividad_padre.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadreNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDAD_PADRE_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
