package proyectos.models;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * TareasProyectoModel: A SOFIA generated model
 */
public class TareasProyectoModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4321325017793350813L;

	// constants for columns
	public static final String TAREAS_PROYECTO_TAREA_ID = "tareas_proyecto.tarea_id";

	public static final String TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";

	public static final String TAREAS_PROYECTO_DESCRIPCION = "tareas_proyecto.descripcion";

	public static final String TAREAS_PROYECTO_OBSERVACIONES = "tareas_proyecto.observaciones";

	public static final String TAREAS_PROYECTO_ESTADO = "tareas_proyecto.estado";

	public static final String TAREAS_PROYECTO_PROYECTO_ID = "tareas_proyecto.proyecto_id";

	public static final String TAREAS_PROYECTO_CLASE_TAREA_ID = "tareas_proyecto.clase_tarea_id";

	public static final String TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID = "tareas_proyecto.actividad_proyecto_id";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String PROYECTOS_PROYECTO = "proyectos.proyecto";

	public static final String ACTIVIDADES_PROYECTO_ACTIVIDAD_ID = "actividades_proyecto.actividad_id";

	public static final String ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE = "actividad_padre.actividad_id_padre";

	public static final String ACTIVIDAD_PADRE_PADRE_NOMBRE = "actividad_padre_padre.nombre";

	public static final String CLASES_TAREA_NOMBRE = "clases_tareas.nombre";

	public static final String ESTADOS_NOMBRE = "estados.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	// private BaseController _pagina;

	// $ENDCUSTOMVARS$

	/**
	 * Create a new TareasProyectoModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 */
	public TareasProyectoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new TareasProyectoModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 * @param profile
	 *           The database profile to use
	 */
	public TareasProyectoModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("tareas_proyecto"), "tareas_proyecto");
			addTableAlias(computeTableName("proyectos"), "proyectos");
			addTableAlias(computeTableName("actividades_proyecto"),
					"actividades_proyecto");
			addTableAlias(computeTableName("actividades"), "actividades");

			addTableAlias(computeTableName("clases_tareas"), "clases_tareas");
			addTableAlias(computeTableName("infraestructura.estados"), "estados");

			// add columns
			addColumn(computeTableName("tareas_proyecto"), "tarea_id",
					DataStore.DATATYPE_INT, true, true, TAREAS_PROYECTO_TAREA_ID);
			addColumn(computeTableName("tareas_proyecto"), "nombre",
					DataStore.DATATYPE_STRING, false, true, TAREAS_PROYECTO_NOMBRE);
			addColumn(computeTableName("tareas_proyecto"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					TAREAS_PROYECTO_DESCRIPCION);
			addColumn(computeTableName("tareas_proyecto"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					TAREAS_PROYECTO_OBSERVACIONES);
			addColumn(computeTableName("tareas_proyecto"), "estado",
					DataStore.DATATYPE_STRING, false, true, TAREAS_PROYECTO_ESTADO);
			addColumn(computeTableName("tareas_proyecto"), "proyecto_id",
					DataStore.DATATYPE_INT, false, true, TAREAS_PROYECTO_PROYECTO_ID);
			addColumn(computeTableName("tareas_proyecto"), "clase_tarea_id",
					DataStore.DATATYPE_INT, false, true,
					TAREAS_PROYECTO_CLASE_TAREA_ID);
			addColumn(computeTableName("tareas_proyecto"),
					"actividad_proyecto_id", DataStore.DATATYPE_INT, false, true,
					TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID);
			addColumn(computeTableName("proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
			addColumn(computeTableName("proyectos"), "proyecto",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_PROYECTO);
			addColumn(computeTableName("actividades"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ACTIVIDADES_NOMBRE);
			addColumn(computeTableName("clases_tareas"), "nombre",
					DataStore.DATATYPE_STRING, false, false, CLASES_TAREA_NOMBRE);
			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ESTADOS_NOMBRE);

			// addBucket("nombre", DataStore.DATATYPE_STRING);

			// add joins
			addJoin(computeTableAndFieldName("tareas_proyecto.proyecto_id"),
					computeTableAndFieldName("proyectos.proyecto_id"), false);
			addJoin(
					computeTableAndFieldName("tareas_proyecto.actividad_proyecto_id")
							+ ","
							+ computeTableAndFieldName("tareas_proyecto.proyecto_id"),
					computeTableAndFieldName("actividades_proyecto.actividad_id"
							+ ","
							+ computeTableAndFieldName("actividades_proyecto.proyecto_id")),
					true);
			addJoin(
					computeTableAndFieldName("tareas_proyecto.actividad_proyecto_id"),
					computeTableAndFieldName("actividades.actividad_id"), true);

			addJoin(computeTableAndFieldName("tareas_proyecto.clase_tarea_id"),
					computeTableAndFieldName("clases_tareas.clase_tarea_id"), false);
			addJoin(computeTableAndFieldName("tareas_proyecto.estado"),
					computeTableAndFieldName("estados.estado"), true);

			// set order by
			setOrderBy(computeTableAndFieldName("tareas_proyecto.nombre") + " ASC");

			// add validations

			/*
			 * addLookupRule( TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID,
			 * "actividades_proyecto", "'actividades_proyecto.actividad_id = '+
			 * tareas_proyecto.actividad_proyecto_id + ' and
			 * actividades_proyecto.proyecto_id = '+ tareas_proyecto.proyecto_id ",
			 * "", "", "Actividad inexistente");
			 */
			/*
			 * addLookupRule( TAREAS_PROYECTO_PROYECTO_ID, "actividades_proyecto",
			 * "'actividades_proyecto.proyecto_id = '+ tareas_proyecto.proyecto_id ",
			 * "", "", "Actividad inexistente");
			 */

			addLookupRule(
					TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID,
					"actividades",
					"'actividades.actividad_id = '+  tareas_proyecto.actividad_proyecto_id ",
					"actividades.nombre", "actividades.nombre",
					"Actividad inexistente");

			addLookupRule(
					TAREAS_PROYECTO_CLASE_TAREA_ID,
					"clases_tareas",
					"'clases_tareas.clase_tarea_id = '+  tareas_proyecto.clase_tarea_id ",
					"clases_tareas.nombre", "clases_tareas.nombre",
					"Clase de tarea inexistente");

			addRequiredRule(TAREAS_PROYECTO_NOMBRE,
					"Nombre de la tarea es obligatorio");
			addRequiredRule(TAREAS_PROYECTO_PROYECTO_ID,
					"el Proyecto para la tarea es obligatorio");
			addRequiredRule(TAREAS_PROYECTO_CLASE_TAREA_ID,
					"La clase de la tarea es obligatoria");

			setAutoIncrement(TAREAS_PROYECTO_TAREA_ID, true);
			setUpdateable(TAREAS_PROYECTO_TAREA_ID, false);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the tareas_proyecto.tarea_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoTareaId() throws DataStoreException {
		return getInt(TAREAS_PROYECTO_TAREA_ID);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoTareaId(int row) throws DataStoreException {
		return getInt(row, TAREAS_PROYECTO_TAREA_ID);
	}

	/**
	 * Set the value of the tareas_proyecto.tarea_id column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoTareaId(int newValue) throws DataStoreException {
		setInt(TAREAS_PROYECTO_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.tarea_id column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, TAREAS_PROYECTO_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoNombre() throws DataStoreException {
		return getString(TAREAS_PROYECTO_NOMBRE);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoNombre(int row) throws DataStoreException {
		return getString(row, TAREAS_PROYECTO_NOMBRE);
	}

	/**
	 * Set the value of the tareas_proyecto.nombre column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoNombre(String newValue)
			throws DataStoreException {
		setString(TAREAS_PROYECTO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREAS_PROYECTO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoDescripcion() throws DataStoreException {
		return getString(TAREAS_PROYECTO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoDescripcion(int row)
			throws DataStoreException {
		return getString(row, TAREAS_PROYECTO_DESCRIPCION);
	}

	/**
	 * Set the value of the tareas_proyecto.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoDescripcion(String newValue)
			throws DataStoreException {
		setString(TAREAS_PROYECTO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREAS_PROYECTO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoObservaciones() throws DataStoreException {
		return getString(TAREAS_PROYECTO_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoObservaciones(int row)
			throws DataStoreException {
		return getString(row, TAREAS_PROYECTO_OBSERVACIONES);
	}

	/**
	 * Set the value of the tareas_proyecto.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoObservaciones(String newValue)
			throws DataStoreException {
		setString(TAREAS_PROYECTO_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREAS_PROYECTO_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.estado column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoEstado() throws DataStoreException {
		return getString(TAREAS_PROYECTO_ESTADO);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoEstado(int row) throws DataStoreException {
		return getString(row, TAREAS_PROYECTO_ESTADO);
	}

	/**
	 * Set the value of the tareas_proyecto.estado column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoEstado(String newValue)
			throws DataStoreException {
		setString(TAREAS_PROYECTO_ESTADO, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.estado column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREAS_PROYECTO_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.proyecto_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoProyectoId() throws DataStoreException {
		return getInt(TAREAS_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoProyectoId(int row) throws DataStoreException {
		return getInt(row, TAREAS_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Set the value of the tareas_proyecto.proyecto_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoProyectoId(int newValue)
			throws DataStoreException {
		setInt(TAREAS_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.proyecto_id column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, TAREAS_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.clase_tarea_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoClaseTareaId() throws DataStoreException {
		return getInt(TAREAS_PROYECTO_CLASE_TAREA_ID);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoClaseTareaId(int row) throws DataStoreException {
		return getInt(row, TAREAS_PROYECTO_CLASE_TAREA_ID);
	}

	/**
	 * Set the value of the tareas_proyecto.clase_tarea_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoClaseTareaId(int newValue)
			throws DataStoreException {
		setInt(TAREAS_PROYECTO_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoClaseTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, TAREAS_PROYECTO_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.actividad_proyecto_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoActividadProyectoId() throws DataStoreException {
		return getInt(TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.actividad_proyecto_id column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getTareasProyectoActividadProyectoId(int row)
			throws DataStoreException {
		return getInt(row, TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID);
	}

	/**
	 * Set the value of the tareas_proyecto.actividad_proyecto_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoActividadProyectoId(int newValue)
			throws DataStoreException {
		setInt(TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.actividad_proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoActividadProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID, newValue);
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
	 *           which row in the table
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
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the proyectos.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_NOMBRE, newValue);
	}
	
	/**
	 * Retrieve the value of the proyectos.proyecto column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosProyecto() throws DataStoreException {
		return getString(PROYECTOS_PROYECTO);
	}

	/**
	 * Retrieve the value of the proyectos.proyecto column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosProyecto(int row) throws DataStoreException {
		return getString(row, PROYECTOS_PROYECTO);
	}

	/**
	 * Set the value of the proyectos.proyecto column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the proyectos.proyecto column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesProyectoActividadId() throws DataStoreException {
		return getInt(ACTIVIDADES_PROYECTO_ACTIVIDAD_ID);
	}

	/**
	 * Retrieve the value of the actividades_proyecto.actividad_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
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
	 *           the new item value
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
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesProyectoActividadId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_PROYECTO_ACTIVIDAD_ID, newValue);
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
	 * Retrieve the value of the actividades.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
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
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesNombre(String newValue) throws DataStoreException {
		setString(ACTIVIDADES_NOMBRE, newValue);
	}

	/**
	 * Set the value of the actividades.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
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
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadesActividadIdPadre(int row) throws DataStoreException {
		return getInt(row, ACTIVIDADES_ACTIVIDAD_ID_PADRE);
	}

	/**
	 * Set the value of the actividades.actividad_id_padre column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
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
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadesActividadIdPadre(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDADES_ACTIVIDAD_ID_PADRE, newValue);
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
	 *           which row in the table
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
	 *           the new item value
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
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadreNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDAD_PADRE_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the actividad_padre.actividad_id_padre column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadPadreActividadIdPadre() throws DataStoreException {
		return getInt(ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE);
	}

	/**
	 * Retrieve the value of the actividad_padre.actividad_id_padre column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getActividadPadreActividadIdPadre(int row)
			throws DataStoreException {
		return getInt(row, ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE);
	}

	/**
	 * Set the value of the actividad_padre.actividad_id_padre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadreActividadIdPadre(int newValue)
			throws DataStoreException {
		setInt(ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE, newValue);
	}

	/**
	 * Set the value of the actividad_padre.actividad_id_padre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadreActividadIdPadre(int row, int newValue)
			throws DataStoreException {
		setInt(row, ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE, newValue);
	}

	/**
	 * Retrieve the value of the actividad_padre_padre.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadPadrePadreNombre() throws DataStoreException {
		return getString(ACTIVIDAD_PADRE_PADRE_NOMBRE);
	}

	/**
	 * Retrieve the value of the actividad_padre_padre.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getActividadPadrePadreNombre(int row)
			throws DataStoreException {
		return getString(row, ACTIVIDAD_PADRE_PADRE_NOMBRE);
	}

	/**
	 * Set the value of the actividad_padre_padre.nombre column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadrePadreNombre(String newValue)
			throws DataStoreException {
		setString(ACTIVIDAD_PADRE_PADRE_NOMBRE, newValue);
	}

	/**
	 * Set the value of the actividad_padre_padre.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setActividadPadrePadreNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ACTIVIDAD_PADRE_PADRE_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the clases_tareas.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareaNombre() throws DataStoreException {
		return getString(CLASES_TAREA_NOMBRE);
	}

	/**
	 * Retrieve the value of the clases_tareas.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClasesTareaNombre(int row) throws DataStoreException {
		return getString(row, CLASES_TAREA_NOMBRE);
	}

	/**
	 * Set the value of the clases_tareas.nombre column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareaNombre(String newValue) throws DataStoreException {
		setString(CLASES_TAREA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the clases_tareas.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setClasesTareaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASES_TAREA_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadosNombre() throws DataStoreException {
		return getString(ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadosNombre(int row) throws DataStoreException {
		return getString(row, ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setEstadosNombre(String newValue) throws DataStoreException {
		setString(ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESTADOS_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// Juan Manuel Cortez - 30/10/2007
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {

		if (this.getTareasProyectoEstado() == null)
			this.setTareasProyectoEstado("0001.0001");

		ActividadesProyectoModel dsActividadesProyecto = new ActividadesProyectoModel(
				getAppName());
		for (int i = 0; i < this.getRowCount(); i++)
			if (getTareasProyectoActividadProyectoId(i) != 0
					&& dsActividadesProyecto
							.estimateRowsRetrieved("actividades_proyecto.proyecto_id = "
									+ getTareasProyectoProyectoId(i)
									+ " and actividades_proyecto.actividad_id ="
									+ getTareasProyectoActividadProyectoId(i)) == 0)
				throw new DataStoreException(
						"La tarea nº "
								+ getTareasProyectoTareaId(i)
								+ " tiene asignada una actividad no relacionada con este proyecto");
		super.update(conn, handleTrans);

	}

	@Override
	public boolean deleteRow(int row) {
		// TODO Auto-generated method stub
		try {
			if (this.estimateRowsRetrieved("tareas_proyecto.proyecto_id = "
					+ getTareasProyectoProyectoId(row)) == 1)
				return false;
			;
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.deleteRow(row);
	}

	// $ENDCUSTOMMETHODS$

}
