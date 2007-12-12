package proyectos.models;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ActividadesProyectoModel: A SOFIA generated model
 */
public class ActividadesProyectoModel extends DataStore {

	/**
	 *
	 */
	private static final long serialVersionUID = 4900543883792651288L;

	// constants for columns
	public static final String ACTIVIDADES_PROYECTO_PROYECTO_ID = "actividades_proyecto.proyecto_id";

	public static final String ACTIVIDADES_PROYECTO_ACTIVIDAD_ID = "actividades_proyecto.actividad_id";

	public static final String ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID = "actividades_proyecto.actividad_proyecto_id";

	public static final String ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String ACTIVIDADES_DESCRIPCION = "actividades.descripcion";

	public static final String ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ActividadesProyectoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 */
	public ActividadesProyectoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ActividadesProyectoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ActividadesProyectoModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("actividades_proyecto"),
					"actividades_proyecto");
			addTableAlias(computeTableName("actividades"), "actividades");
			addTableAlias(computeTableName("actividades"), "actividad_padre");
			addTableAlias(computeTableName("proyectos"), "proyectos");

			// add columns
			addColumn(computeTableName("actividades_proyecto"), "proyecto_id",
					DataStore.DATATYPE_INT, false, true,
					ACTIVIDADES_PROYECTO_PROYECTO_ID);
			addColumn(computeTableName("actividades_proyecto"), "actividad_id",
					DataStore.DATATYPE_INT, false, true,
					ACTIVIDADES_PROYECTO_ACTIVIDAD_ID);
			addColumn(computeTableName("actividades_proyecto"),
					"actividad_proyecto_id", DataStore.DATATYPE_INT, true,
					true, ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID);
			addColumn(computeTableName("actividades"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ACTIVIDADES_NOMBRE);
			addColumn(computeTableName("actividades"), "descripcion",
					DataStore.DATATYPE_STRING, false, false, ACTIVIDADES_DESCRIPCION);
			addColumn(computeTableName("actividades"), "es_hoja",
					DataStore.DATATYPE_STRING, false, false,
					ACTIVIDADES_ES_HOJA);
			addColumn(computeTableName("actividades"), "actividad_id_padre",
					DataStore.DATATYPE_STRING, false, false,
					ACTIVIDADES_ACTIVIDAD_ID_PADRE);
			addColumn(computeTableName("actividad_padre"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE);


			// add joins
			addJoin(
					computeTableAndFieldName("actividades_proyecto.actividad_id"),
					computeTableAndFieldName("actividades.actividad_id"), false);
			addJoin(
					computeTableAndFieldName("actividades_proyecto.proyecto_id"),
					computeTableAndFieldName("proyectos.proyecto_id"), false);
			addJoin(
					computeTableAndFieldName("actividades.actividad_id_padre"),
					computeTableAndFieldName("actividad_padre.actividad_id"), true);

			// set order by
			setOrderBy(computeTableAndFieldName("actividades.nombre") + " ASC");

			// add validations
			addLookupRule(
					ACTIVIDADES_PROYECTO_ACTIVIDAD_ID,
					"actividades",
					"'actividades.actividad_id = ' + actividades_proyecto.actividad_id",
					"actividades.nombre", "actividades.nombre", "Actividad inexistente");

			addRequiredRule(ACTIVIDADES_PROYECTO_ACTIVIDAD_ID,
					"La Actividad es obligatoria");
			addRequiredRule(ACTIVIDADES_PROYECTO_PROYECTO_ID,
					"El proyecto para las actividades es obligatorio");

			setAutoIncrement(ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID, true);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the actividades_proyecto.proyecto_id column for the
	 * current row.
	 *
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoProyectoId() throws DataStoreException {
		return getInt(ACTIVIDADES_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.proyecto_id column for the
	 * specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoProyectoId(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDADES_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Set the value of the actividades_proyecto.proyecto_id column for the
	 * current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoProyectoId(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the actividades_proyecto.proyecto_id column for the
	 * specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_id column for
	 * the current row.
	 *
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoActividadId() throws DataStoreException {
		return getInt(ACTIVIDADES_PROYECTO_ACTIVIDAD_ID);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_id column for
	 * the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoActividadId(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDADES_PROYECTO_ACTIVIDAD_ID);
	}

	/**
	 * Set the value of the actividades_proyecto.actividad_id column for the
	 * current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoActividadId(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_PROYECTO_ACTIVIDAD_ID, newValue);
	}

	/**
	 * Set the value of the actividades_proyecto.actividad_id column for the
	 * specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoActividadId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_PROYECTO_ACTIVIDAD_ID, newValue);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_proyecto_id
	 * column for the current row.
	 *
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoActividadProyectoId()
			throws DataStoreException {
		return getInt(ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_proyecto_id
	 * column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoActividadProyectoId(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID);
	}

	/**
	 * Set the value of the actividades_proyecto.actividad_proyecto_id column
	 * for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoActividadProyectoId(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the actividades_proyecto.actividad_proyecto_id column
	 * for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoActividadProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID, newValue);
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
	 * Retrieve the value of the actividades.actividad_id_padre column for the
	 * current row.
	 *
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadPadreNombre() throws DataStoreException {
		return getInt(ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE);
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
	public int getActividadesActividadPadreNombre(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE);
	}

	/**
	 * Set the value of the actividades.actividad_id_padre column for the
	 * current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadPadreNombre(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE, newValue);
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
	public void setActividadesActividadPadreNombre(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_ACTIVIDAD_PADRE_NOMBRE, newValue);
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
	 * Retrieve the value of the proyectos.nombre column for the current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosNombre() throws DataStoreException {
		return getString(PROYECTOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the proyectos.nombre column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosNombre(int row) throws DataStoreException {
		return getString(row, PROYECTOS_NOMBRE);
	}

	/**
	 * Set the value of the proyectos.nombre column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the proyectos.nombre column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_NOMBRE, newValue);
	}


	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	@Override
	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		// verifico que las actividades declaradas en el Model sean imputables (hojas)
		ActividadesModel dsActividades = new ActividadesModel(getAppName());
		for (int i = 0; i < this.getRowCount(); i++)
			if(dsActividades.estimateRowsRetrieved("actividades.es_hoja = 'V' and actividades.actividad_id ="+ getActividadesProyectoActividadId(i)) == 0)
				throw new DataStoreException("La actividad nº "+getActividadesProyectoActividadId(i)+" no es imputable");
		super.update(conn, handleTrans);
	}
	// $ENDCUSTOMMETHODS$

}
