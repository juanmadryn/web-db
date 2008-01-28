package proyectos.models;

import infraestructura.models.AplicaCircuitoModel;
import infraestructura.models.BaseModel;
import infraestructura.models.TransicionEstadoModel;
import infraestructura.reglasNegocio.ClaveUnicaValidation;
import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ProyectoModel: A SOFIA generated model
 */
public class ProyectoModel extends BaseModel {

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return this.getProyectosProyectoId();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2122265661780466721L;

	// constants for columns
	public static final String PROYECTOS_PROYECTO_ID = "proyectos.proyecto_id";

	public static final String PROYECTOS_PROYECTO = "proyectos.proyecto";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String PROYECTOS_DESCRIPCION = "proyectos.descripcion";

	public static final String PROYECTOS_OBSERVACIONES = "proyectos.observaciones";

	public static final String PROYECTOS_ESTADO = "proyectos.estado";

	public static final String PROYECTOS_PLANTILLA = "proyectos.plantilla";

	public static final String PROYECTOS_ENTIDAD_ID = "proyectos.entidad_id";

	public static final String PROYECTOS_VIGENCIA_DESDE = "proyectos.vigencia_desde";

	public static final String PROYECTOS_VIGENCIA_HASTA = "proyectos.vigencia_hasta";

	public static final String ESTADOS_NOMBRE = "estados.nombre";

	public static final String ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";

	public static final String ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ProyectoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ProyectoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ProyectoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ProyectoModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("proyectos"), "proyectos");
			addTableAlias(computeTableName("infraestructura.estados"),
					"estados");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa");

			// add columns
			addColumn(computeTableName("proyectos"), "proyecto_id",
					DataStore.DATATYPE_INT, true, false, PROYECTOS_PROYECTO_ID);
			addColumn(computeTableName("proyectos"), "proyecto",
					DataStore.DATATYPE_STRING, false, true, PROYECTOS_PROYECTO);
			addColumn(computeTableName("proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, true, PROYECTOS_NOMBRE);
			addColumn(computeTableName("proyectos"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					PROYECTOS_DESCRIPCION);
			addColumn(computeTableName("proyectos"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					PROYECTOS_OBSERVACIONES);
			addColumn(computeTableName("proyectos"), "estado",
					DataStore.DATATYPE_STRING, false, true, PROYECTOS_ESTADO);
			addColumn(computeTableName("proyectos"), "plantilla",
					DataStore.DATATYPE_STRING, false, true, PROYECTOS_PLANTILLA);
			addColumn(computeTableName("proyectos"), "entidad_id",
					DataStore.DATATYPE_INT, false, true, PROYECTOS_ENTIDAD_ID);
			addColumn(computeTableName("proyectos"), "vigencia_desde",
					DataStore.DATATYPE_DATE, false, true,
					PROYECTOS_VIGENCIA_DESDE);
			addColumn(computeTableName("proyectos"), "vigencia_hasta",
					DataStore.DATATYPE_DATE, false, true,
					PROYECTOS_VIGENCIA_HASTA);

			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ESTADOS_NOMBRE);

			addColumn(computeTableName("entidad_externa"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO);

			addColumn(computeTableName("entidad_externa"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE);

			// add joins

			addJoin(computeTableAndFieldName("proyectos.estado"),
					computeTableAndFieldName("estados.estado"), true);

			addJoin(computeTableAndFieldName("proyectos.entidad_id"),
					computeTableAndFieldName("entidad_externa.entidad_id"),
					true);

			// add validations
			addRequiredRule(PROYECTOS_PROYECTO,
					"El código de proyecto es obligatorio");
			addRequiredRule(PROYECTOS_NOMBRE,
					"El nombre del proyecto es obligatorio");

			addRequiredRule(PROYECTOS_ESTADO, "El Estado es obligatorio");

			addRequiredRule(PROYECTOS_ENTIDAD_ID, "EL cliente es obligatorio");
			addRequiredRule(PROYECTOS_VIGENCIA_DESDE,
					"La fecha de inicio de vigencia es obligatoria");

			addLookupRule(
					PROYECTOS_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = \"' + proyectos.estado + '\"' ",
					"nombre", computeTableAndFieldName("estados.nombre"),
					"Estado inexistente");

			addLookupRule(
					PROYECTOS_ENTIDAD_ID,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + proyectos.entidad_id",
					"nombre",
					computeTableAndFieldName("entidad_externa.nombre"),
					"Cliente inexistente");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated
		try {
			addExpressionRule(PROYECTOS_PROYECTO,
					new ConvierteMayusculasValidation(PROYECTOS_PROYECTO), "",
					false);
			addExpressionRule(PROYECTOS_PROYECTO, new ClaveUnicaValidation(
					"proyectos", PROYECTOS_PROYECTO, true, "proyectos",
					"proyectos"), "El número de proyecto YA existe.", false);

			// setea el autoincrement y protege la PK
			setAutoIncrement(PROYECTOS_PROYECTO_ID, true);
			setUpdateable(PROYECTOS_PROYECTO_ID, false);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the proyectos.proyecto_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getProyectosProyectoId() throws DataStoreException {
		return getInt(PROYECTOS_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the proyectos.proyecto_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getProyectosProyectoId(int row) throws DataStoreException {
		return getInt(row, PROYECTOS_PROYECTO_ID);
	}

	/**
	 * Set the value of the proyectos.proyecto_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyectoId(int newValue) throws DataStoreException {
		setInt(PROYECTOS_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the proyectos.proyecto_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PROYECTOS_PROYECTO_ID, newValue);
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
	 * Retrieve the value of the proyectos.proyecto column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
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
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the proyectos.proyecto column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_PROYECTO, newValue);
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

	/**
	 * Retrieve the value of the proyectos.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosDescripcion() throws DataStoreException {
		return getString(PROYECTOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the proyectos.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosDescripcion(int row) throws DataStoreException {
		return getString(row, PROYECTOS_DESCRIPCION);
	}

	/**
	 * Set the value of the proyectos.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosDescripcion(String newValue)
			throws DataStoreException {
		setString(PROYECTOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the proyectos.descripcion column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.observaciones column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosObservaciones() throws DataStoreException {
		return getString(PROYECTOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the proyectos.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosObservaciones(int row) throws DataStoreException {
		return getString(row, PROYECTOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the proyectos.observaciones column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosObservaciones(String newValue)
			throws DataStoreException {
		setString(PROYECTOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the proyectos.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.estado column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosEstado() throws DataStoreException {
		return getString(PROYECTOS_ESTADO);
	}

	/**
	 * Retrieve the value of the proyectos.estado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosEstado(int row) throws DataStoreException {
		return getString(row, PROYECTOS_ESTADO);
	}

	/**
	 * Set the value of the proyectos.estado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosEstado(String newValue) throws DataStoreException {
		setString(PROYECTOS_ESTADO, newValue);
	}

	/**
	 * Set the value of the proyectos.estado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.plantilla column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosPlantilla() throws DataStoreException {
		return getString(PROYECTOS_PLANTILLA);
	}

	/**
	 * Retrieve the value of the proyectos.plantilla column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosPlantilla(int row) throws DataStoreException {
		return getString(row, PROYECTOS_PLANTILLA);
	}

	/**
	 * Set the value of the proyectos.plantilla column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosPlantilla(String newValue)
			throws DataStoreException {
		setString(PROYECTOS_PLANTILLA, newValue);
	}

	/**
	 * Set the value of the proyectos.plantilla column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosPlantilla(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_PLANTILLA, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.entidad_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getProyectosEntidadId() throws DataStoreException {
		return getInt(PROYECTOS_ENTIDAD_ID);
	}

	/**
	 * Retrieve the value of the proyectos.entidad_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getProyectosEntidadId(int row) throws DataStoreException {
		return getInt(row, PROYECTOS_ENTIDAD_ID);
	}

	/**
	 * Set the value of the proyectos.entidad_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosEntidadId(int newValue) throws DataStoreException {
		setInt(PROYECTOS_ENTIDAD_ID, newValue);
	}

	/**
	 * Set the value of the proyectos.entidad_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosEntidadId(int row, int newValue)
			throws DataStoreException {
		setInt(row, PROYECTOS_ENTIDAD_ID, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.vigencia_desde column for the current
	 * row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getProyectosVigenciaDesde() throws DataStoreException {
		return getDate(PROYECTOS_VIGENCIA_DESDE);
	}

	/**
	 * Retrieve the value of the proyectos.vigencia_desde column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getProyectosVigenciaDesde(int row)
			throws DataStoreException {
		return getDate(row, PROYECTOS_VIGENCIA_DESDE);
	}

	/**
	 * Set the value of the proyectos.vigencia_desde column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosVigenciaDesde(java.sql.Date newValue)
			throws DataStoreException {
		setDate(PROYECTOS_VIGENCIA_DESDE, newValue);
	}

	/**
	 * Set the value of the proyectos.vigencia_desde column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosVigenciaDesde(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, PROYECTOS_VIGENCIA_DESDE, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.vigencia_hasta column for the current
	 * row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getProyectosVigenciaHasta() throws DataStoreException {
		return getDate(PROYECTOS_VIGENCIA_HASTA);
	}

	/**
	 * Retrieve the value of the proyectos.vigencia_hasta column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getProyectosVigenciaHasta(int row)
			throws DataStoreException {
		return getDate(row, PROYECTOS_VIGENCIA_HASTA);
	}

	/**
	 * Set the value of the proyectos.vigencia_hasta column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosVigenciaHasta(java.sql.Date newValue)
			throws DataStoreException {
		setDate(PROYECTOS_VIGENCIA_HASTA, newValue);
	}

	/**
	 * Set the value of the proyectos.vigencia_hasta column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosVigenciaHasta(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, PROYECTOS_VIGENCIA_HASTA, newValue);
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
	 *            which row in the table
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
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEstadosNombre(String newValue) throws DataStoreException {
		setString(ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the entidad_externa.codigo column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaCodigo() throws DataStoreException {
		return getString(ENTIDAD_EXTERNA_CODIGO);
	}

	/**
	 * Retrieve the value of the entidad_externa.codigo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaCodigo(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_CODIGO);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaCodigo(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_CODIGO, newValue);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaCodigo(int row, String newValue)
			throws DataStoreException {
		setString(row, ENTIDAD_EXTERNA_CODIGO, newValue);
	}

	/**
	 * Retrieve the value of the entidad_externa.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaNombre() throws DataStoreException {
		return getString(ENTIDAD_EXTERNA_NOMBRE);
	}

	/**
	 * Retrieve the value of the entidad_externa.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaNombre(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_NOMBRE);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ENTIDAD_EXTERNA_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {

		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		//String columna = null;
		String circuito = null;
		StringBuilder criteria = new StringBuilder();
		//String estado_inicial = null;

		try {
			conexion = DBConnection.getConnection(getAppName(),
					"infraestructura");

			AplicaCircuitoModel dsAplicaCircuito = new AplicaCircuitoModel(
					getAppName(), "infraestructura");
			// primero busco el circuito y la columna asociada a los proyectos

			dsAplicaCircuito
					.retrieve("tipo_objeto = 'TABLA' and nombre_objeto = 'proyectos'  and tipo_detalle = 'COLUMNA' ");

			if (dsAplicaCircuito.gotoFirst()) {
				//columna = dsAplicaCircuito.getAplicaCircuitoNombreDetalle();
				circuito = dsAplicaCircuito.getAplicaCircuitoCircuito();
			}

			// ahora busco el primer estado posible según la máquina de estados
			TransicionEstadoModel dsTransicionEstados = new TransicionEstadoModel(
					getAppName(), "infraestructura");

			criteria
					.append(
							"estado_origen in (select estado from estados where circuito = '")
					.append(circuito)
					.append(
							"')  and estado_origen not in (select estado_destino from transicion_estados)");
			dsTransicionEstados.retrieve(criteria.toString());

			if (dsTransicionEstados.gotoFirst()) {
				//estado_inicial = dsTransicionEstados.getTransicionEstadosEstadoOrigen();
			}
		} catch (DataStoreException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException(
					"Error determinando circuito y estado inicial para los proyectos",
					e);
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException(
					"Error determinando circuito y estado inicial para los proyectos",
					e);
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

		for (int i = 0; i < getRowCount(); i++) {
			if (getProyectosEstado(i) == null) {
				setProyectosEstado("0001.0001");
			}
		}

		super.update(conn, handleTrans);
	}

	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return getProyectosEstado();
	}

	// $ENDCUSTOMMETHODS$

}
