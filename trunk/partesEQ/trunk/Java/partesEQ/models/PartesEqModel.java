package partesEQ.models;

import infraestructura.models.BaseModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import proyectos.models.TareasProyectoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * java: A SOFIA generated model
 */
public class PartesEqModel extends BaseModel {

	//constants for columns
	public static final String PARTES_EQ_PARTE_ID = "partes_eq.parte_id";
	public static final String PARTES_EQ_PROYECTO_ID = "partes_eq.proyecto_id";
	public static final String PARTES_EQ_TAREA_ID = "partes_eq.tarea_id";
	public static final String PARTES_EQ_ESTADO = "partes_eq.estado";
	public static final String PARTES_EQ_LOTE_ID = "partes_eq.lote_id";
	public static final String PARTES_EQ_EQUIPO_ID = "partes_eq.equipo_id";
	public static final String PARTES_EQ_CHOFER_ID = "partes_eq.chofer_id";
	public static final String PARTES_EQ_FECHA = "partes_eq.fecha";
	public static final String PARTES_EQ_HORA_DESDE = "partes_eq.hora_desde";
	public static final String PARTES_EQ_HORA_HASTA = "partes_eq.hora_hasta";
	public static final String PARTES_EQ_HORAS = "partes_eq.horas";
	public static final String EQUIPOS_NOMBRE = "equipos.nombre";
	public static final String EQUIPOS_CODIGO_INVENTARIO = "equipos.codigo_inventario";
	public static final String LOTE_CARGA_PARTES_EQ_ESTADO = "lote_carga_partes_eq.estado";
	public static final String LOTE_CARGA_PARTES_EQ_FECHA_ALTA = "lote_carga_partes_eq.fecha_alta";
	public static final String LOTE_CARGA_PARTES_EQ_FECHA_CIERRE = "lote_carga_partes_eq.fecha_cierre";
	public static final String CHOFERES_PERSONAL_ID = "choferes.personal_id";
	public static final String CHOFERES_NRO_LEGAJO = "choferes.nro_legajo";
	public static final String CHOFERES_APEYNOM = "choferes.apeynom";
	public static final String CHOFERES_DESDE = "choferes.desde";
	public static final String CHOFERES_HASTA = "choferes.hasta";

	//constants for buckets
	public static final String MENSAJE_ERROR = "mensaje_error";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
	private static final long serialVersionUID = -6521608215127806107L;
	public static final String ESTADOS_NOMBRE = "estados.nombre";
	public static final String PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String TAREAS_PROYECTO_NOMBRE="tareas_proyecto.nombre";
	public static final String TAREAS_PROYECTOS_DESCRIPCION="tareas_proyecto.descripcion";

	private String _estado_inicial = null;
	//$ENDCUSTOMVARS$

	/**
	 * Create a new java object.
	 * @param appName The SOFIA application name
	 */
	public PartesEqModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new java object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public PartesEqModel(String appName, String profile) {
		super(appName, profile);

		try {

			//add aliases
			addTableAlias(computeTableName("equipos"), "equipos");
			addTableAlias(computeTableName("lote_carga_partes_eq"),"lote_carga_partes_eq");
			addTableAlias(computeTableName("choferes"), "choferes");
			addTableAlias(computeTableName("infraestructura.estados"),"estados");
			addTableAlias(computeTableName("proyectos.proyectos"), "proyectos");
			addTableAlias(computeTableName("proyectos.tareas_proyecto"),"tareas");

			//add columns
			addColumn(computeTableName("partes_eq"), "parte_id",
					DataStore.DATATYPE_INT, true, true, PARTES_EQ_PARTE_ID);
			addColumn(computeTableName("partes_eq"), "proyecto_id",
					DataStore.DATATYPE_INT, false, true, PARTES_EQ_PROYECTO_ID);
			addColumn(computeTableName("partes_eq"), "tarea_id",
					DataStore.DATATYPE_INT, false, true, PARTES_EQ_TAREA_ID);
			addColumn(computeTableName("partes_eq"), "estado",
					DataStore.DATATYPE_STRING, false, true, PARTES_EQ_ESTADO);
			addColumn(computeTableName("partes_eq"), "lote_id",
					DataStore.DATATYPE_INT, false, true, PARTES_EQ_LOTE_ID);
			addColumn(computeTableName("partes_eq"), "equipo_id",
					DataStore.DATATYPE_INT, false, true, PARTES_EQ_EQUIPO_ID);
			addColumn(computeTableName("partes_eq"), "chofer_id",
					DataStore.DATATYPE_INT, false, true, PARTES_EQ_CHOFER_ID);
			addColumn(computeTableName("partes_eq"), "fecha",
					DataStore.DATATYPE_DATE, false, true, PARTES_EQ_FECHA);
			addColumn(computeTableName("partes_eq"), "hora_desde",
					DataStore.DATATYPE_STRING, false, true,	PARTES_EQ_HORA_DESDE);
			addColumn(computeTableName("partes_eq"), "hora_hasta",
					DataStore.DATATYPE_STRING, false, true,	PARTES_EQ_HORA_HASTA);
			addColumn(computeTableName("partes_eq"), "horas",
					DataStore.DATATYPE_DOUBLE, false, true, PARTES_EQ_HORAS);
			addColumn(computeTableName("equipos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, EQUIPOS_NOMBRE);
			addColumn(computeTableName("equipos"), "codigo_inventario",
					DataStore.DATATYPE_STRING, false, false, EQUIPOS_CODIGO_INVENTARIO);
			addColumn(computeTableName("lote_carga_partes_eq"), "estado",
					DataStore.DATATYPE_STRING, false, false, LOTE_CARGA_PARTES_EQ_ESTADO);
			addColumn(computeTableName("lote_carga_partes_eq"), "fecha_alta",
					DataStore.DATATYPE_DATE, false, false, LOTE_CARGA_PARTES_EQ_FECHA_ALTA);
			addColumn(computeTableName("lote_carga_partes_eq"), "fecha_cierre",
					DataStore.DATATYPE_DATE, false, false, LOTE_CARGA_PARTES_EQ_FECHA_CIERRE);
			addColumn(computeTableName("choferes"), "personal_id",
					DataStore.DATATYPE_INT, false, false, CHOFERES_PERSONAL_ID);
			addColumn(computeTableName("choferes"), "nro_legajo",
					DataStore.DATATYPE_INT, false, false, CHOFERES_NRO_LEGAJO);
			addColumn(computeTableName("choferes"), "apeynom",
					DataStore.DATATYPE_STRING, false, false, CHOFERES_APEYNOM);
			addColumn(computeTableName("choferes"), "desde",
					DataStore.DATATYPE_DATE, false, false, CHOFERES_DESDE);
			addColumn(computeTableName("choferes"), "hasta",
					DataStore.DATATYPE_DATE, false, false, CHOFERES_HASTA);

			addColumn(computeTableName("infraestructura.estados"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ESTADOS_NOMBRE);
			addColumn(computeTableName("proyectos.proyectos"), "proyecto",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_PROYECTO);
			addColumn(computeTableName("proyectos.proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
			
			addColumn(computeTableName("tareas"),"nombre",DataStore.DATATYPE_STRING,false,false,TAREAS_PROYECTO_NOMBRE);
			addColumn(computeTableName("tareas"),"descripcion",DataStore.DATATYPE_STRING,false,false,TAREAS_PROYECTOS_DESCRIPCION);

			//add buckets
			addBucket(MENSAJE_ERROR, DataStore.DATATYPE_STRING);

			//add joins
			addJoin(computeTableAndFieldName("partes_eq.chofer_id"),
					computeTableAndFieldName("choferes.chofer_id"), true);
			addJoin(computeTableAndFieldName("partes_eq.equipo_id"),
					computeTableAndFieldName("equipos.equipo_id"), true);
			addJoin(computeTableAndFieldName("partes_eq.lote_id"),
					computeTableAndFieldName("lote_carga_partes_eq.lote_id"), true);

			addJoin(computeTableAndFieldName("partes_eq.estado"),
					computeTableAndFieldName("estados.estado"), true);
			addJoin(computeTableAndFieldName("partes_eq.proyecto_id"),
					computeTableAndFieldName("proyectos.proyecto_id"), true);
			
			addJoin(computeTableAndFieldName("partes_eq.tarea_id"),computeTableAndFieldName("tareas.tarea_id"),true);

			//set order by
			setOrderBy(computeTableAndFieldName("partes_eq.tarea_id") + " DESC");

			//add validations
			addRequiredRule(PARTES_EQ_PROYECTO_ID,
					"El proyecto (OT) es obligatorio en el parte");
			addRequiredRule(PARTES_EQ_HORAS,
					"La cantidad de horas trabajadas es obligatoria para el parte");
			addRequiredRule(PARTES_EQ_HORA_HASTA,
					"Hora hasta es obligatoria para el parte");
			addRequiredRule(PARTES_EQ_HORA_DESDE,
					"Hora desde es obligatoria para el parte");
			addRequiredRule(PARTES_EQ_FECHA,
					"La fecha del partes es obligatoria");

			addLookupRule(PARTES_EQ_LOTE_ID, "lote_carga_partes_eq",
					"'lote_carga_partes_eq.lote_id = ' + partes_eq.lote_id",
					"estado", "lote_carga_partes_eq.estado", "Lote inexistente");
			addLookupRule(
					PARTES_EQ_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = \"' + partes_eq.estado + '\"' ",
					"nombre", "estados.nombre", "Estado inexistente");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
		try {
			// establece la columna de autoincrement
			setAutoIncrement(PARTES_EQ_PARTE_ID, true);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the partes_eq.parte_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqParteId() throws DataStoreException {
		return getInt(PARTES_EQ_PARTE_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.parte_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqParteId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_PARTE_ID);
	}

	/**
	 * Set the value of the partes_eq.parte_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqParteId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_PARTE_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.parte_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqParteId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_PARTE_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.proyecto_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqProyectoId() throws DataStoreException {
		return getInt(PARTES_EQ_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.proyecto_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqProyectoId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_PROYECTO_ID);
	}

	/**
	 * Set the value of the partes_eq.proyecto_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqProyectoId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.proyecto_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_PROYECTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.tarea_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqTareaId() throws DataStoreException {
		return getInt(PARTES_EQ_TAREA_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.tarea_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqTareaId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_TAREA_ID);
	}

	/**
	 * Set the value of the partes_eq.tarea_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqTareaId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.tarea_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.estado column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqEstado() throws DataStoreException {
		return getString(PARTES_EQ_ESTADO);
	}

	/**
	 * Retrieve the value of the partes_eq.estado column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqEstado(int row) throws DataStoreException {
		return getString(row, PARTES_EQ_ESTADO);
	}

	/**
	 * Set the value of the partes_eq.estado column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqEstado(String newValue) throws DataStoreException {
		setString(PARTES_EQ_ESTADO, newValue);
	}

	/**
	 * Set the value of the partes_eq.estado column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, PARTES_EQ_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.lote_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqLoteId() throws DataStoreException {
		return getInt(PARTES_EQ_LOTE_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.lote_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqLoteId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_LOTE_ID);
	}

	/**
	 * Set the value of the partes_eq.lote_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqLoteId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_LOTE_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.lote_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqLoteId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_LOTE_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.equipo_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqEquipoId() throws DataStoreException {
		return getInt(PARTES_EQ_EQUIPO_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.equipo_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqEquipoId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_EQUIPO_ID);
	}

	/**
	 * Set the value of the partes_eq.equipo_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqEquipoId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_EQUIPO_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.equipo_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqEquipoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_EQUIPO_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.chofer_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqChoferId() throws DataStoreException {
		return getInt(PARTES_EQ_CHOFER_ID);
	}

	/**
	 * Retrieve the value of the partes_eq.chofer_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getPartesEqChoferId(int row) throws DataStoreException {
		return getInt(row, PARTES_EQ_CHOFER_ID);
	}

	/**
	 * Set the value of the partes_eq.chofer_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqChoferId(int newValue) throws DataStoreException {
		setInt(PARTES_EQ_CHOFER_ID, newValue);
	}

	/**
	 * Set the value of the partes_eq.chofer_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqChoferId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PARTES_EQ_CHOFER_ID, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.fecha column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getPartesEqFecha() throws DataStoreException {
		return getDate(PARTES_EQ_FECHA);
	}

	/**
	 * Retrieve the value of the partes_eq.fecha column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getPartesEqFecha(int row) throws DataStoreException {
		return getDate(row, PARTES_EQ_FECHA);
	}

	/**
	 * Set the value of the partes_eq.fecha column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqFecha(java.sql.Date newValue)
			throws DataStoreException {
		setDate(PARTES_EQ_FECHA, newValue);
	}

	/**
	 * Set the value of the partes_eq.fecha column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqFecha(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, PARTES_EQ_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.hora_desde column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqHoraDesde() throws DataStoreException {
		return getString(PARTES_EQ_HORA_DESDE);
	}

	/**
	 * Retrieve the value of the partes_eq.hora_desde column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqHoraDesde(int row) throws DataStoreException {
		return getString(row, PARTES_EQ_HORA_DESDE);
	}

	/**
	 * Set the value of the partes_eq.hora_desde column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoraDesde(String newValue) throws DataStoreException {
		setString(PARTES_EQ_HORA_DESDE, newValue);
	}

	/**
	 * Set the value of the partes_eq.hora_desde column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoraDesde(int row, String newValue)
			throws DataStoreException {
		setString(row, PARTES_EQ_HORA_DESDE, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.hora_hasta column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqHoraHasta() throws DataStoreException {
		return getString(PARTES_EQ_HORA_HASTA);
	}

	/**
	 * Retrieve the value of the partes_eq.hora_hasta column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqHoraHasta(int row) throws DataStoreException {
		return getString(row, PARTES_EQ_HORA_HASTA);
	}

	/**
	 * Set the value of the partes_eq.hora_hasta column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoraHasta(String newValue) throws DataStoreException {
		setString(PARTES_EQ_HORA_HASTA, newValue);
	}

	/**
	 * Set the value of the partes_eq.hora_hasta column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoraHasta(int row, String newValue)
			throws DataStoreException {
		setString(row, PARTES_EQ_HORA_HASTA, newValue);
	}

	/**
	 * Retrieve the value of the partes_eq.horas column for the current row.
	 * @return double
	 * @throws DataStoreException
	 */
	public double getPartesEqHoras() throws DataStoreException {
		return getDouble(PARTES_EQ_HORAS);
	}

	/**
	 * Retrieve the value of the partes_eq.horas column for the specified row.
	 * @param row which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getPartesEqHoras(int row) throws DataStoreException {
		return getDouble(row, PARTES_EQ_HORAS);
	}

	/**
	 * Set the value of the partes_eq.horas column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoras(double newValue) throws DataStoreException {
		setDouble(PARTES_EQ_HORAS, newValue);
	}

	/**
	 * Set the value of the partes_eq.horas column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqHoras(int row, double newValue)
			throws DataStoreException {
		setDouble(row, PARTES_EQ_HORAS, newValue);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposNombre() throws DataStoreException {
		return getString(EQUIPOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposNombre(int row) throws DataStoreException {
		return getString(row, EQUIPOS_NOMBRE);
	}

	/**
	 * Set the value of the equipos.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposNombre(String newValue) throws DataStoreException {
		setString(EQUIPOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the equipos.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposCodigoInventario() throws DataStoreException {
		return getString(EQUIPOS_CODIGO_INVENTARIO);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposCodigoInventario(int row) throws DataStoreException {
		return getString(row, EQUIPOS_CODIGO_INVENTARIO);
	}

	/**
	 * Set the value of the equipos.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposCodigoInventario(String newValue)
			throws DataStoreException {
		setString(EQUIPOS_CODIGO_INVENTARIO, newValue);
	}

	/**
	 * Set the value of the equipos.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposCodigoInventario(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_CODIGO_INVENTARIO, newValue);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.estado column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getLoteCargaPartesEqEstado() throws DataStoreException {
		return getString(LOTE_CARGA_PARTES_EQ_ESTADO);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.estado column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getLoteCargaPartesEqEstado(int row) throws DataStoreException {
		return getString(row, LOTE_CARGA_PARTES_EQ_ESTADO);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.estado column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqEstado(String newValue)
			throws DataStoreException {
		setString(LOTE_CARGA_PARTES_EQ_ESTADO, newValue);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.estado column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, LOTE_CARGA_PARTES_EQ_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.fecha_alta column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getLoteCargaPartesEqFechaAlta()
			throws DataStoreException {
		return getDate(LOTE_CARGA_PARTES_EQ_FECHA_ALTA);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.fecha_alta column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getLoteCargaPartesEqFechaAlta(int row)
			throws DataStoreException {
		return getDate(row, LOTE_CARGA_PARTES_EQ_FECHA_ALTA);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.fecha_alta column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqFechaAlta(java.sql.Date newValue)
			throws DataStoreException {
		setDate(LOTE_CARGA_PARTES_EQ_FECHA_ALTA, newValue);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.fecha_alta column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqFechaAlta(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, LOTE_CARGA_PARTES_EQ_FECHA_ALTA, newValue);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.fecha_cierre column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getLoteCargaPartesEqFechaCierre()
			throws DataStoreException {
		return getDate(LOTE_CARGA_PARTES_EQ_FECHA_CIERRE);
	}

	/**
	 * Retrieve the value of the lote_carga_partes_eq.fecha_cierre column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getLoteCargaPartesEqFechaCierre(int row)
			throws DataStoreException {
		return getDate(row, LOTE_CARGA_PARTES_EQ_FECHA_CIERRE);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.fecha_cierre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqFechaCierre(java.sql.Date newValue)
			throws DataStoreException {
		setDate(LOTE_CARGA_PARTES_EQ_FECHA_CIERRE, newValue);
	}

	/**
	 * Set the value of the lote_carga_partes_eq.fecha_cierre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setLoteCargaPartesEqFechaCierre(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, LOTE_CARGA_PARTES_EQ_FECHA_CIERRE, newValue);
	}

	/**
	 * Retrieve the value of the choferes.personal_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getChoferesPersonalId() throws DataStoreException {
		return getInt(CHOFERES_PERSONAL_ID);
	}

	/**
	 * Retrieve the value of the choferes.personal_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getChoferesPersonalId(int row) throws DataStoreException {
		return getInt(row, CHOFERES_PERSONAL_ID);
	}

	/**
	 * Set the value of the choferes.personal_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesPersonalId(int newValue) throws DataStoreException {
		setInt(CHOFERES_PERSONAL_ID, newValue);
	}

	/**
	 * Set the value of the choferes.personal_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesPersonalId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CHOFERES_PERSONAL_ID, newValue);
	}

	/**
	 * Retrieve the value of the choferes.nro_legajo column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getChoferesNroLegajo() throws DataStoreException {
		return getInt(CHOFERES_NRO_LEGAJO);
	}

	/**
	 * Retrieve the value of the choferes.nro_legajo column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getChoferesNroLegajo(int row) throws DataStoreException {
		return getInt(row, CHOFERES_NRO_LEGAJO);
	}

	/**
	 * Set the value of the choferes.nro_legajo column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesNroLegajo(int newValue) throws DataStoreException {
		setInt(CHOFERES_NRO_LEGAJO, newValue);
	}

	/**
	 * Set the value of the choferes.nro_legajo column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesNroLegajo(int row, int newValue)
			throws DataStoreException {
		setInt(row, CHOFERES_NRO_LEGAJO, newValue);
	}

	/**
	 * Retrieve the value of the choferes.apeynom column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getChoferesApeynom() throws DataStoreException {
		return getString(CHOFERES_APEYNOM);
	}

	/**
	 * Retrieve the value of the choferes.apeynom column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getChoferesApeynom(int row) throws DataStoreException {
		return getString(row, CHOFERES_APEYNOM);
	}

	/**
	 * Set the value of the choferes.apeynom column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesApeynom(String newValue) throws DataStoreException {
		setString(CHOFERES_APEYNOM, newValue);
	}
	
	/**
	 * Set the value of the choferes.apeynom column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesApeynom(int row, String newValue)
			throws DataStoreException {
		setString(row, CHOFERES_APEYNOM, newValue);
	}
	
//----
	
	/**
	 * Set the value of the choferes.hasta column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesHasta(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, CHOFERES_HASTA, newValue);
	}

	/**
	 * Retrieve the value of the choferes.hasta column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getChoferesHasta() throws DataStoreException {
		return getDate(CHOFERES_HASTA);
	}

	/**
	 * Retrieve the value of the choferes.hasta column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getChoferesHasta(int row) throws DataStoreException {
		return getDate(row, CHOFERES_HASTA);
	}

	/**
	 * Set the value of the choferes.hasta column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesHasta(java.sql.Date newValue) throws DataStoreException {
		setDate(CHOFERES_HASTA, newValue);
	}
//--
	
	/**
	 * Set the value of the choferes.desde column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesDesde(int row, java.sql.Date newValue)	throws DataStoreException {
		setDate(row, CHOFERES_DESDE, newValue);
	}

	/**
	 * Retrieve the value of the choferes.desde column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getChoferesDesde() throws DataStoreException {
		return getDate(CHOFERES_DESDE);
	}

	/**
	 * Retrieve the value of the choferes.desde column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getChoferesDesde(int row) throws DataStoreException {
		return getDate(row, CHOFERES_DESDE);
	}

	/**
	 * Set the value of the choferes.desde column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setChoferesDesde(java.sql.Date newValue) throws DataStoreException {
		setDate(CHOFERES_DESDE, newValue);
	}
	
	/**
	 * Retrieve the value of the mensaje_error bucket for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMensajeError() throws DataStoreException {
		return getString(MENSAJE_ERROR);
	}

	/**
	 * Retrieve the value of the mensaje_error bucket for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMensajeError(int row) throws DataStoreException {
		return getString(row, MENSAJE_ERROR);
	}

	/**
	 * Set the value of the mensaje_error bucket for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setMensajeError(String newValue) throws DataStoreException {
		setString(MENSAJE_ERROR, newValue);
	}

	/**
	 * Set the value of the mensaje_error bucket for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setMensajeError(int row, String newValue)
			throws DataStoreException {
		setString(row, MENSAJE_ERROR, newValue);
	}

	// ..

	/**
	 * Retrieve the value of the proyectos.proyecto column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqProyecto() throws DataStoreException {
		return getString(PROYECTOS_PROYECTO);
	}

	/**
	 * Retrieve the value of the proyectos.proyecto column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getPartesEqProyecto(int row) throws DataStoreException {
		return getString(row, PROYECTOS_PROYECTO);
	}

	/**
	 * Set the value of the proyectos.proyecto column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the proyectos.proyecto for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setPartesEqProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_PROYECTO, newValue);
	}
	
	/**
     * Retrieve the value of the proyectos.Nombre column for the current row.
     * @return int
     * @throws DataStoreException
     */ 
    public String getProyectosNombre() throws DataStoreException {
         return  getString(PROYECTOS_NOMBRE);
    }

    /**
     * Retrieve the value of the proyectos.Nombre column for the specified row.
     * @param row which row in the table
     * @return int
     * @throws DataStoreException
     */ 
    public String getProyectosNombre(int row) throws DataStoreException {
         return  getString(row,PROYECTOS_NOMBRE);
    }

    /**
     * Set the value of the proyectos.Nombre column for the current row.
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setProyectosNombre(String newValue) throws DataStoreException {
         setString(PROYECTOS_NOMBRE, newValue);
    }

    /**
     * Set the value of the proyectos.Nombre column for the specified row.
     * @param row which row in the table
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setProyectosNombre(int row,String newValue) throws DataStoreException {
         setString(row,PROYECTOS_NOMBRE, newValue);
    }
    
    /**
     * Retrieve the value of the tareas_proyecto.nombre column for the current row.
     * @return int
     * @throws DataStoreException
     */ 
    public String getTareasProyectoNombre() throws DataStoreException {
         return  getString(TAREAS_PROYECTO_NOMBRE);
    }

    /**
     * Retrieve the value of the tareas_proyecto.nombre column for the specified row.
     * @param row which row in the table
     * @return int
     * @throws DataStoreException
     */ 
    public String getTareasProyectoNombre(int row) throws DataStoreException {
         return  getString(row,TAREAS_PROYECTO_NOMBRE);
    }

    /**
     * Set the value of the tareas_proyecto.nombre column for the current row.
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setTareasProyectoNombre(String newValue) throws DataStoreException {
         setString(TAREAS_PROYECTO_NOMBRE, newValue);
    }

    /**
     * Set the value of the tareas_proyecto.nombre column for the specified row.
     * @param row which row in the table
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setTareasProyectoNombre(int row,String newValue) throws DataStoreException {
         setString(row,TAREAS_PROYECTO_NOMBRE, newValue);
    }
    
    /**
     * Retrieve the value of the tareas_proyecto.descripcion column for the current row.
     * @return int
     * @throws DataStoreException
     */ 
    public String getTareasProyectoDescripcion() throws DataStoreException {
         return  getString(TAREAS_PROYECTOS_DESCRIPCION);
    }

    /**
     * Retrieve the value of the tareas_proyecto.descripcion column for the specified row.
     * @param row which row in the table
     * @return int
     * @throws DataStoreException
     */ 
    public String getTareasProyectoDescripcion(int row) throws DataStoreException {
         return  getString(row,TAREAS_PROYECTOS_DESCRIPCION);
    }

    /**
     * Set the value of the tareas_proyecto.descripcion column for the current row.
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setTareasProyectoDescripcion(String newValue) throws DataStoreException {
         setString(TAREAS_PROYECTOS_DESCRIPCION, newValue);
    }

    /**
     * Set the value of the tareas_proyecto.descripcion column for the specified row.
     * @param row which row in the table
     * @param newValue the new item value
     * @throws DataStoreException
     */ 
    public void setTareasProyectoDescripcion(int row,String newValue) throws DataStoreException {
         setString(row,TAREAS_PROYECTOS_DESCRIPCION, newValue);
    }

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		validarPartes();
		super.update(conn, handleTrans);
	}

	public void validarPartes() throws DataStoreException, SQLException {
		//boolean hubo_errores_legajo = false;
		boolean hubo_errores_chofer = false;
		boolean hubo_errores_horario = false;
		boolean hubo_errores_equipo = false;
		boolean hubo_errores_proyecto = false;
		boolean hubo_errores_tarea = false;
		boolean hubo_errores_lote = false;
		boolean hubo_errores_dup = false;
		String estado_inicial = null;

		// recupera estado inicial para los partes
		estado_inicial = getEstadoInicial();

		// realiza las tareas de lookup sobre los datos de tango y completa información
		// realiza validaciones varias de consistencia de información
		// aplica el tratamiento de horas y rango horario
		for (int i = 0; i < getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (getRowStatus(i) == STATUS_NEW_MODIFIED
					|| getRowStatus(i) == STATUS_MODIFIED) {

				if (estado_inicial != null
						&& (getPartesEqEstado(i) == null || getPartesEqEstado(i)
								.trim().length() == 0))
					setPartesEqEstado(i, "0005.0002");
				else if (getPartesEqEstado(i) == null
						|| getPartesEqEstado(i).trim().length() == 0)
					setPartesEqEstado(i, "0005.0002");

				// limpio mensajes de error que pueden haber quedado
				setMensajeError(i, "");

				if (hubo_errores_horario || hubo_errores_proyecto || hubo_errores_tarea || hubo_errores_equipo || hubo_errores_chofer ) {
					//controlaLegajoTango(i);
					//getCategoriaLegajo(i);
					getDatosChofer(i);
					getDatosEquipo(i);
					getDatosProyecto(i);
					getDatosTarea(i);
					controlaHorario(i);
					parteDuplicado(i);
				} else {
					//hubo_errores_legajo = controlaLegajoTango(i);
					//getCategoriaLegajo(i);
					hubo_errores_equipo = (getDatosEquipo(i) == -1);
					hubo_errores_chofer = getDatosChofer(i);
					hubo_errores_proyecto = (getDatosProyecto(i) == -1);
					hubo_errores_tarea = (getDatosTarea(i) == -1);
					hubo_errores_lote = (getLote(i) == -1);
					hubo_errores_horario = controlaHorario(i);
					hubo_errores_dup = parteDuplicado(i);
				}
			}
		}

		if (hubo_errores_horario 
				|| hubo_errores_proyecto 
				|| hubo_errores_tarea
				|| hubo_errores_equipo 
				|| hubo_errores_chofer
				|| hubo_errores_lote 
				|| hubo_errores_dup) 
		{
			throw new DataStoreException("Hubo errores procesando partes. Corríjalos y grabe nuevamente");
		}

	}
	
	public int getLote(int row) throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		int lote_id = getPartesEqLoteId(row);
		Date fecha_parte = getPartesEqFecha(row);
		GregorianCalendar cal = new GregorianCalendar();
		String quincena;
		
		if (fecha_parte == null) {
			setMensajeError(row, "La fecha del parte es obligatoria. Imposible calcular lote.");
			return -1;
		}

		if (lote_id < 1) {
			// no está seteado el lote aún. determina o crea lote
			try {
				// determina la quincena, según la fecha
				cal.setTime(fecha_parte);
				int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
				
				if (diaDelMes < 16)
					//para la primer quincena, poco que analizar
					quincena = "Primer quincena, ";
				else 
					quincena = "Segunda quincena, ";
				
				// ahora completo el mes y año
				switch (cal.get(Calendar.MONTH)){
				case Calendar.JANUARY:
					quincena = quincena + "Enero ";
					break;
				case Calendar.FEBRUARY:
					quincena = quincena + "Febrero ";
					break;
				case Calendar.MARCH:
					quincena = quincena + "Marzo ";
					break;
				case Calendar.APRIL:
					quincena = quincena + "Abril ";
					break;
				case Calendar.MAY:
					quincena = quincena + "Mayo ";
					break;
				case Calendar.JUNE:
					quincena = quincena + "Junio ";
					break;
				case Calendar.JULY:
					quincena = quincena + "Julio ";
					break;
				case Calendar.AUGUST:
					quincena = quincena + "Agosto ";
					break;
				case Calendar.SEPTEMBER:
					quincena = quincena + "Septiembre ";
					break;
				case Calendar.OCTOBER:
					quincena = quincena + "Octubre ";
					break;
				case Calendar.NOVEMBER:
					quincena = quincena + "Noviembre ";
					break;
				case Calendar.DECEMBER:
					quincena = quincena + "Diciembre ";
					break;
				default:
					quincena = quincena + "MES y AñO NO DETERMINADO";	
				}
				
				// completa con el año
				quincena = quincena + Integer.toString(cal.get(Calendar.YEAR));
				
				conexion = DBConnection.getConnection("partesEQ","partesEQ");

				// primero busco el circuito y la columna asociada a los partes
				SQL = "select lote_id"
						+ "  from lote_carga_partes_eq "
						+ " where estado in ('0005.0001','0005.0002')"
						+ " and descripcion = '" + quincena + "'"; 
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					lote_id = r.getInt("lote_id");
				} else {
					// debo crear el nuevo lote
					Date hoy = new Date(System.currentTimeMillis());
					r.close();
					st.close();
					SQL = " insert into lote_carga_partes_eq "
						+ " (estado,fecha_alta,descripcion) VALUES "
						+ "('0005.0002','" + hoy.toString() + "','" + quincena + "')";
					st = conexion.createStatement();
					st.executeUpdate(SQL);
					
					st.close();
					SQL = "select lote_id"
						+ "  from lote_carga_partes_eq "
						+ " where estado in ('0005.0001','0005.0002')"
						+ " and descripcion = '" + quincena + "'"; 
					st = conexion.createStatement();
					r = st.executeQuery(SQL);

					if (r.first()) {
						lote_id = r.getInt("lote_id");
					} else {
						if (getMensajeError(row) == null)
							setMensajeError(row, "No se pudo recuperar el lote, pese a que se ha insertado...");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - No se pudo recuperar el lote, pese a que se ha insertado...");
						lote_id = -1;
					}
					
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				if (getMensajeError(row) == null)
					setMensajeError(row, "Error determinando lote para el parte: "
							+ e.getMessage());
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error determinando lote para el parte: "
							+ e.getMessage());
				lote_id = -1;
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
			
			// setea las columnas
			if (lote_id != -1) {
				setPartesEqLoteId(row, lote_id);
			}
		}

		return lote_id;
	}
	
	/**
	 * TODO: Tomado de PartesMoModel. Logica duplicada.
	 * @param row
	 * @return
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public boolean parteDuplicado(int row) throws DataStoreException, SQLException {
		
		Date fechaRow = getPartesEqFecha(row);		
		int idEquipoRow = getPartesEqEquipoId(row);
		String horaDesdeRow = getPartesEqHoraDesde(row);
		String horaHastaRow = getPartesEqHoraHasta(row);
		int hora_d = parserHora(horaDesdeRow);
		int minuto_d = parserMinutos(horaDesdeRow);
		int hora_h = parserHora(horaHastaRow);
		int minuto_h = parserMinutos(horaHastaRow);
		int hora_d_abs = hora_d*100 + minuto_d;
		int hora_h_abs = hora_h*100 + minuto_h;
		int parteId = getPartesEqParteId(row);
					
		// primero controla partes duplicados en el datastore
		for (int i = 0; i < getRowCount(); i++) {
	
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (i != row && (getRowStatus(i) == STATUS_NEW_MODIFIED || getRowStatus(i) == STATUS_MODIFIED)) {

				Date fechaI = getPartesEqFecha(i);
				int idEquipoI = getPartesEqEquipoId(i);
				String horaDesdeI = getPartesEqHoraDesde(i);
				String horaHastaI = getPartesEqHoraHasta(i);
				int hora_d_i = parserHora(horaDesdeI);
				int minuto_d_i = parserMinutos(horaDesdeI);
				int hora_h_i = parserHora(horaHastaI);
				int minuto_h_i = parserMinutos(horaHastaI);
				int hora_d_abs_i = hora_d_i*100 + minuto_d_i;
				int hora_h_abs_i = hora_h_i*100 + minuto_h_i;
								
				// si es misma fecha y legajo, es candidato
				if (fechaRow.equals(fechaI) && idEquipoRow == idEquipoI) {
					if ( (hora_d_abs < hora_h_abs_i) && (hora_h_abs_i<= hora_h_abs)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el equipo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el equipo fecha con mismo horario o superposición de horario");
						return true;
					}
					if ( (hora_d_abs_i < hora_h_abs) && (hora_h_abs<= hora_h_abs_i)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el equipo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el equipo fecha con mismo horario o superposición de horario");
						return true;
					}
				}
			}			
		}
		
		// ahora controlo posibles partes duplicados en la base de datos
		PartesEqModel partesDB = new PartesEqModel(getAppName(),getDbProfile());
		partesDB.retrieve("partes_eq.fecha = '" + fechaRow.toString() + "'");
		for (int i = 0; i < partesDB.getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (parteId != partesDB.getPartesEqParteId(i)) {
			
				Date fechaI = partesDB.getPartesEqFecha(i);
				int idEquipoI = partesDB.getPartesEqEquipoId(i);
				String horaDesdeI = partesDB.getPartesEqHoraDesde(i);
				String horaHastaI = partesDB.getPartesEqHoraHasta(i);
				int hora_d_i = partesDB.parserHora(horaDesdeI);
				int minuto_d_i = partesDB.parserMinutos(horaDesdeI);
				int hora_h_i = partesDB.parserHora(horaHastaI);
				int minuto_h_i = partesDB.parserMinutos(horaHastaI);
				int hora_d_abs_i = hora_d_i*100 + minuto_d_i;
				int hora_h_abs_i = hora_h_i*100 + minuto_h_i;
				
				// si es misma fecha y legajo, es candidato
				if (fechaRow.equals(fechaI) && idEquipoRow == idEquipoI) {
					if ( (hora_d_abs < hora_h_abs_i) && (hora_h_abs_i<= hora_h_abs)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
					if ( (hora_d_abs_i < hora_h_abs) && (hora_h_abs<= hora_h_abs_i)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * TODO Tomado de PartesMoModel. Logica duplicada.
	 * @param row
	 * @return
	 * @throws DataStoreException
	 */
	public boolean controlaHorario(int row) throws DataStoreException {
		boolean hubo_errores = false;
		String hora_desde = null;		
		int hora_d = -1;
		int minuto_d = -1;
		String hora_hasta = null;
		int hora_h = -1;
		int minuto_h = -1;
		double horas = -1;

		/***********************************************************************
		 * Controla y calcula el horario y horas ingresadas
		 **********************************************************************/
		hora_desde = getPartesEqHoraDesde(row);
		hora_hasta = getPartesEqHoraHasta(row);
		horas = getPartesEqHoras(row);

		// si hora desde es null --> es error
		if (hora_desde == null) {
			if (getMensajeError(row) == null)
				setMensajeError(row, "Hora desde es obligatoria");
			else
				setMensajeError(row, getMensajeError(row)
						+ " - Hora desde es obligatoria");
			hubo_errores = true;
		} else if (hora_hasta == null && horas <= 0) {
			if (getMensajeError(row) == null)
				setMensajeError(row,
						"Debe ingresar al menos hora hasta o cantidad de horas trabajadas");
			else
				setMensajeError(row, getMensajeError(row) + " - Debe ingresar al menos hora hasta o cantidad de horas trabajadas");
			hubo_errores = true;

		} else {
			// verifico formato de la hora desde
			if ((hora_d = parserHora(hora_desde)) == -1) {
				if (getMensajeError(row) == null)
					setMensajeError(row, "Error en la hora desde");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error en la hora desde");
				hubo_errores = true;
			}
			if ((minuto_d = parserMinutos(hora_desde)) == -1) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Error en los minutos de la hora desde");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error en los minutos de la hora desde");
				hubo_errores = true;
			}

			// se ingreso hora hasta????
			if (hora_hasta != null) {
				// verifico formato de la hora hasta
				if ((hora_h = parserHora(hora_hasta)) == -1) {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Error en la hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - Error en la hora hasta");
					hubo_errores = true;
				}
				if ((minuto_h = parserMinutos(hora_hasta)) == -1) {
					if (getMensajeError(row) == null)
						setMensajeError(row,
								"Error en los minutos de la hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - Error en los minutos de la hora hasta");
					hubo_errores = true;
				}
			}
		}

		// si no ingresú horas, calculo las horas según desde y hasta,
		// siempre y cuando no haya errores
		// de paso se chequea que el rango horario sea correcto
		if (!hubo_errores && horas <= 0) {
			// si es límite de 24 o 0 horas suma a la hora hasta 24
			// horas para los cálculos
			if (hora_d > 12 && hora_h < 6)
				hora_h = hora_h + 24;
			if (hora_d > hora_h) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Hora desde es mayor que la hora hasta");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Hora desde es mayor que la hora hasta");
				hubo_errores = true;
			}

			if (hora_d == hora_h && minuto_d > minuto_h) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Hora desde es mayor que la hora hasta");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Hora desde es mayor que la hora hasta");
				hubo_errores = true;
			}

			int tmpHoras = hora_h - (hora_d + 1);
			int tmpMinutosRemanentesDesde = 60 - minuto_d;
			int tmpMinutosRemanentesHasta = minuto_h;
			int tmpMinutos = tmpMinutosRemanentesDesde
					+ tmpMinutosRemanentesHasta;
			if (tmpMinutos > 59) {
				tmpMinutos = tmpMinutos - 60;
				tmpHoras = tmpHoras + 1;
			}

			tmpMinutos = (tmpMinutos * 100 / 60);

			horas = (double) tmpHoras + ((double) tmpMinutos) / 100.00;
		}

		if (!hubo_errores && hora_hasta == null) {
			// calculo la hora hasta en función de la hora desde y las
			// horas trabajadas
			try {

				hora_h = hora_d + (int) Math.ceil(horas);
				if (hora_h > 24)
					hora_h = hora_h - 24;

				int tmpMinutosRemanente = (int) ((horas - Math.floor(horas)) * 100);
				tmpMinutosRemanente = tmpMinutosRemanente * 100 / 60;

				minuto_h = minuto_d + tmpMinutosRemanente;
				if (minuto_h > 60) {
					hora_h = hora_h + 1;
					if (hora_h > 24)
						hora_h = hora_h - 24;
					minuto_h = minuto_h - 60;
				}

				// ultimo chequeo
				if (hora_h < 0 || hora_h > 24 || minuto_h < 0 || minuto_h > 60) {
					if (getMensajeError(row) == null)
						setMensajeError(row, "problemas calculando hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - problemas calculando hora hasta");
					hubo_errores = true;
				}

				// ahora convierto a string la hora hasta
				hora_hasta = Integer.toString(hora_h) + ":"
						+ Integer.toString(minuto_h);

			} catch (Exception e) {
				if (getMensajeError(row) == null)
					setMensajeError(row, "problemas calculando hora hasta: "
							+ e.getMessage());
				else
					setMensajeError(row, getMensajeError(row)
							+ " - problemas calculando hora hasta: "
							+ e.getMessage());
				hubo_errores = true;
			}
		}

		// seteo los datos del datastore
		if (!hubo_errores) {
			setPartesEqHoraDesde(row, hora_desde);
			setPartesEqHoraHasta(row, hora_hasta);
			setPartesEqHoras(row, horas);
		}

		return hubo_errores;

	}
	
	/**
	 * TODO Tomado de PartesMoModel. Lógica duplicada.
	 * @param hora
	 * @return
	 */
	private int parserHora(String hora) {
		int tmpHora = -1;

		// si el largo es mayor a 5 doy error
		if (hora.length() > 5)
			return -1;

		// verifico formato si viene con minutos o sólo hora
		int index2Puntos = hora.indexOf(":");
		if (index2Puntos == -1)
			index2Puntos = hora.indexOf(".");

		if (index2Puntos > -1) {
			// horas y minutos extraigo solo las horas
			try {
				tmpHora = Integer.parseInt(hora.substring(0, index2Puntos));
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}

		} else {
			// no hay minutos, es hora pura
			try {
				tmpHora = Integer.parseInt(hora);
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}
		}

		// controlo rango horario
		if (tmpHora < 0 || tmpHora > 24)
			return -1;

		// todo ok regreso la hora
		return tmpHora;
	}

	/**
	 * TODO Tomado de PartesMoModel. Lógica duplicada.
	 * @param hora
	 * @return
	 */
	private int parserMinutos(String hora) {
		int tmpMinutos = -1;

		// si el largo es mayor a 5 doy error
		if (hora.length() > 5)
			return -1;

		// verifico formato si viene con minutos o sólo hora
		int index2Puntos = hora.indexOf(":");
		if (index2Puntos == -1)
			index2Puntos = hora.indexOf(".");

		if (index2Puntos > -1) {
			// horas y minutos extraigo solo las horas
			try {
				tmpMinutos = Integer.parseInt(hora.substring(index2Puntos + 1));
				// si los minutos están expresados con una sola cifra la llevo a dos
				if (hora.substring(index2Puntos + 1).length() == 1)
					tmpMinutos = tmpMinutos * 10;
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}

		} else {
			// no hay minutos, es hora pura
			tmpMinutos = 0;
		}

		// controlo rango de minutos
		if (tmpMinutos < 0 || tmpMinutos > 59)
			return -1;

		// todo ok regreso la hora
		return tmpMinutos;
	}

	
	public int getDatosProyecto(int row) throws DataStoreException{
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		String nombre = null;
		int proyecto_id = -1;
		java.sql.Date vigenciaHasta = null;
		java.sql.Date vigenciaDesde = null;
		String proyecto = getPartesEqProyecto(row);
		
		if ((proyecto != null) && (proyecto.trim().length() > 0)) {
			try {
				conexion = DBConnection.getConnection("partesEQ","proyectos");

				// primero busco el circuito y la columna asociada a los partes
				// Agregamos vigencia_hasta para controlar vigencia del proyecto 
				SQL = "select proyecto_id, nombre, vigencia_desde, vigencia_hasta "
					+ "  from proyectos "
					+ " where proyecto = '" + proyecto + "'";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					proyecto_id = r.getInt("proyecto_id");
					nombre = r.getString("nombre");
					vigenciaHasta = r.getDate("vigencia_hasta");
					vigenciaDesde = r.getDate("vigencia_desde");
				} else {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Proyecto inexistente");
					else
						setMensajeError(row, getMensajeError(row) + " - Proyecto inexistente");
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Error determinando proyecto: "+ e.getMessage());
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
			
			// setea las columnas
			if (proyecto_id != -1) {  
				// controlamos la fecha de vigencia del proyecto
				int f = controlaFechas(getPartesEqFecha(row), vigenciaDesde, vigenciaHasta);
				if(f == 0) {				
					setProyectosNombre(row, nombre);
					setPartesEqProyectoId(row, proyecto_id);		
					//setPartesEqTareaId(row, proyecto_id);	// tarea_id == proyecto_id				
				}
				else if(f < 0) {
					setMensajeError(row,"Fecha de parte anterior a comienzo del proyecto.");
					proyecto_id = -1;
				} 
				else if(f > 0) {
					setMensajeError(row,"Fecha de parte posterior a finalizacion del proyecto.");
					proyecto_id = -1;
				}
			}
		} else {
			if (getMensajeError(row) == null)
				setMensajeError(row, "El proyecto es obligatorio");
			else
				setMensajeError(row, getMensajeError(row) + " - El proyecto es obligatorio");

		}

		return proyecto_id;
	}
	
	/**
	 * @param row
	 * @return
	 * @throws DataStoreException
	 */
	public int getDatosTarea(int row) throws DataStoreException {		
		TareasProyectoModel dsTareasProyecto = new TareasProyectoModel(getAppName(),"proyectos");
		int proyectoId = getPartesEqProyectoId(row);
		int tareaId = -1;
		String tareaProyectoNombre = getTareasProyectoNombre(row);
		StringBuilder whereClause = new StringBuilder(50);
		
		try {
			// check if a task was specified
			if ((tareaProyectoNombre != null) && (tareaProyectoNombre.trim().length() > 0)) {
				whereClause.append(TareasProyectoModel.TAREAS_PROYECTO_NOMBRE + " = '" + tareaProyectoNombre + "' and ");				
			}			
			whereClause.append(TareasProyectoModel.TAREAS_PROYECTO_PROYECTO_ID + " = " + proyectoId);
			// the first task added to the project on top
			dsTareasProyecto.setOrderBy(TareasProyectoModel.TAREAS_PROYECTO_TAREA_ID + " asc");
			
			dsTareasProyecto.retrieve(whereClause.toString());
			dsTareasProyecto.gotoFirst();
			tareaId = dsTareasProyecto.getRow();
			
			if (tareaId != -1) {
				setPartesEqTareaId(row, dsTareasProyecto.getTareasProyectoTareaId());
				setTareasProyectoNombre(row, dsTareasProyecto.getTareasProyectoNombre());				
			} else {
				if (getMensajeError(row) == null)
					setMensajeError(row, "Tarea inexistente");
				else
					setMensajeError(row, getMensajeError(row) + " - Tarea inexistente");
			}
			
			return tareaId;
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException("Error determinando tarea: " + e.getMessage(), e);
		}		
	}
	
	/**
	 * Comprueba si una fecha esta fuera o dentro de un intervalo de tiempo dado
	 * @param fechaParte fecha a comprobar
	 * @param vigenciaDesde fecha de inicio del intervalo
	 * @param vigenciaHasta fecha de fin del intervalo
	 * @return 0 si la fecha se encuentra entras las fechas dadas, 1 si es posterior, -1 si es anterior
	 * TODO: Considerar mover este metodo a infraestructura
	 */
	private int controlaFechas(java.sql.Date fecha, java.sql.Date vigenciaDesde, java.sql.Date vigenciaHasta) {
		if (fecha == null) return (-1);
		if ((vigenciaDesde != null) && (fecha.compareTo(vigenciaDesde) <=0)) return (-1); 
		if ((vigenciaHasta != null) && (fecha.compareTo(vigenciaHasta) >=0)) return 1;
		return 0;
	}

	public String getEstadoInicial() throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;

		if (_estado_inicial == null || _estado_inicial.trim().length() == 0) {
			try {
				conexion = DBConnection.getConnection("partesEQ",
						"infraestructura");

				// ahora busco el primer estado posible según la máquina de estados
				SQL = "select estado_origen "
						+ " from transicion_estados "
						+ " where estado_origen in (select estado from estados where circuito = '0005')"
						+ " and estado_origen not in (select estado_destino from transicion_estados)";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					_estado_inicial = r.getString("estado_origen");
				}

				if (_estado_inicial != null)
					_estado_inicial = "0005.0001";

			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException(
						"Error determinando circuito y estado inicial para los proyectos: "
								+ e.getMessage());
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

		return _estado_inicial;
	}

	/**
	 * Completa un parte
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void completaUnParte(int row) throws DataStoreException,
			SQLException {
		boolean hubo_errores_legajo = false;
		boolean hubo_errores_horario = false;
		boolean hubo_errores_proyecto = false;
		boolean hubo_errores_tarea = false;
		boolean hubo_errores_lote = false;
		boolean hubo_errores_dup = false;
		boolean hubo_errores_equipo = false;

		// limpio mensajes de error que pueden haber quedado
		setMensajeError(row, "");

		hubo_errores_equipo = (getDatosEquipo(row) == -1);
		hubo_errores_proyecto = (getDatosProyecto(row) == -1);
		hubo_errores_tarea = (getDatosTarea(row) == -1);
		hubo_errores_lote = (getLote(row) == -1);
		hubo_errores_horario = controlaHorario(row);
		hubo_errores_dup = parteDuplicado(row);

		if (hubo_errores_legajo 
				|| hubo_errores_horario 
				|| hubo_errores_proyecto
				|| hubo_errores_tarea
				|| hubo_errores_equipo
				|| hubo_errores_lote 
				|| hubo_errores_dup) 
		{
			throw new DataStoreException("Hubo errores procesando partes. Corríjalos y grabe nuevamente");
		}
	}
	
	/**
	 * Retorna el id de equipo a partir del código de inventario ingresado en la pantalla de carga
	 * @param row 
	 * @return id del equipo
	 * @throws DataStoreException
	 */
	public int getDatosEquipo(int row) throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;		
		int equipo_id = -1;
		String nombre = null;
		String codigo_inventario = getEquiposCodigoInventario(row);
		
		if ((codigo_inventario != null) && (codigo_inventario.trim().length() > 0)) {
			try {
				conexion = DBConnection.getConnection("partesEQ","partesEQ");

				// primero busco el circuito y la columna asociada a los partes 
				SQL = "select equipo_id, nombre "
					+ " from equipos "
					+ " where codigo_inventario = '" + codigo_inventario + "'";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					equipo_id = r.getInt("equipo_id");
					nombre = r.getString("nombre");
				} else {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Equipo inexistente");
					else
						setMensajeError(row, getMensajeError(row) + " - Equipo inexistente");
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Error determinando equipo: "+ e.getMessage());
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
			
			// Seteamos el id del equipo
			if (equipo_id != -1) {
				setPartesEqEquipoId(row, equipo_id);
				setEquiposNombre(row, nombre);
			}
			
		} else {
			if (getMensajeError(row) == null)
				setMensajeError(row, "El equipo es obligatorio");
			else
				setMensajeError(row, getMensajeError(row) + " - El equipo es obligatorio");

		}

		return equipo_id;
	}
	
	/** 
	 * Obtiene los datos del chofer y controla que las fechas sean correctas
	 * @param row
	 * @return false si no hubo errores, true en caso contrario
	 * @throws DataStoreException
	 */
	public boolean getDatosChofer(int row) throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;		
		int chofer_id = 0;	
		String apeynom = null;
		int nro_legajo = getChoferesNroLegajo(row);
		boolean hubo_errores = false;
		
		// Si se introdujo un legajo
		if (nro_legajo > 0) {
			try {
				conexion = DBConnection.getConnection("partesEQ","partesEQ");

				// primero busco el circuito y la columna asociada al chofer 
				SQL = "select chofer_id, apeynom "
					+ " from choferes "
					+ " where nro_legajo = " + nro_legajo + "";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);
				
				if (r.first()) {
					chofer_id = r.getInt("chofer_id");	
					apeynom = r.getString("apeynom");
				} else {
					// El legajo introducido no esta asignado como chofer
					hubo_errores = true;
					if (getMensajeError(row) == null)
						setMensajeError(row, "El legajo no esta asignado como chofer");
					else
						setMensajeError(row, getMensajeError(row) + " - El legajo no esta asignado como chofer");
				}
			} catch (SQLException e) {
					MessageLog.writeErrorMessage(e, null);
					throw new DataStoreException("Error determinando chofer: "+ e.getMessage());
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
			
			// Comprobamos la fecha de vigencia como chofer del legajo contra la fecha del parte
			if (!hubo_errores) {
				if (controlaFechas(getPartesEqFecha(row), getChoferesDesde(row), getChoferesHasta(row)) != 0) {
					hubo_errores = true;
					if (getMensajeError(row) == null)
						setMensajeError(row, "El legajo no esta asignado como chofer para la fecha dada");
					else
						setMensajeError(row, getMensajeError(row) + " - El legajo no esta asignado como chofer para la fecha dada");
				} else {
					setPartesEqChoferId(row, chofer_id);
					setChoferesApeynom(row, apeynom);
				}
			}
		}
					
		return hubo_errores;
	}	

	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return getPartesEqEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return getPartesEqParteId();
	}


	//$ENDCUSTOMMETHODS$

}
