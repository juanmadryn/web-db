package infraestructura.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AuditaEstadosCircuitosModel: A SOFIA generated model
 */
public class AuditaEstadosCircuitosModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1174445685711743365L;

	// constants for columns
	public static final String AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID = "audita_estados_circuitos.audita_id";

	public static final String AUDITA_ESTADOS_CIRCUITOS_CIRCUITO = "audita_estados_circuitos.circuito";

	public static final String AUDITA_ESTADOS_CIRCUITOS_FECHA = "audita_estados_circuitos.fecha";

	public static final String AUDITA_ESTADOS_CIRCUITOS_ACCION = "audita_estados_circuitos.accion";

	public static final String AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO = "audita_estados_circuitos.de_estado";

	public static final String AUDITA_ESTADOS_CIRCUITOS_A_ESTADO = "audita_estados_circuitos.a_estado";

	public static final String AUDITA_ESTADOS_CIRCUITOS_USER_ID = "audita_estados_circuitos.user_id";

	public static final String AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA = "audita_estados_circuitos.nombre_tabla";

	public static final String AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID = "audita_estados_circuitos.registro_id";

	public static final String AUDITA_ESTADOS_CIRCUITOS_HOST = "audita_estados_circuitos.host";
	
	public static final String AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES = "audita_estados_circuitos.observaciones";

	public static final String CIRCUITOS_ESTADOS_NOMBRE = "circuitos_estados.nombre";

	public static final String ACCIONES_APPS_NOMBRE = "acciones_apps.nombre";

	public static final String DE_ESTADOS_NOMBRE = "de_estados.nombre";

	public static final String A_ESTADOS_NOMBRE = "a_estados.nombre";

	public static final String WEBSITE_USER_NOMBRE_COMPLETO = "website_user.nombre_completo";

	public static final String WEBSITE_USER_NRO_LEGAJO = "website_user.nro_legajo";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new AuditaEstadosCircuitosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public AuditaEstadosCircuitosModel(String appName)
			throws DataStoreException {
		this(appName, null);
	}

	/**
	 * Create a new AuditaEstadosCircuitosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public AuditaEstadosCircuitosModel(String appName, String profile)
			throws DataStoreException {
		super(appName, profile);

		// add aliases
		addTableAlias(
				computeTableName("infraestructura.audita_estados_circuitos"),
				"audita_estados_circuitos");
		addTableAlias(computeTableName("infraestructura.circuitos_estados"),
				"circuitos_estados");
		addTableAlias(computeTableName("infraestructura.acciones_apps"),
				"acciones_apps");
		addTableAlias(computeTableName("infraestructura.estados"), "de_estados");
		addTableAlias(computeTableName("infraestructura.estados"), "a_estados");
		addTableAlias(computeTableName("infraestructura.website_user"),
				"website_user");

		// add columns
		addColumn(computeTableName("audita_estados_circuitos"), "audita_id",
				DataStore.DATATYPE_INT, true, true,
				AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID);
		addColumn(computeTableName("audita_estados_circuitos"), "circuito",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_CIRCUITO);
		addColumn(computeTableName("audita_estados_circuitos"), "fecha",
				DataStore.DATATYPE_DATETIME, false, true,
				AUDITA_ESTADOS_CIRCUITOS_FECHA);
		addColumn(computeTableName("audita_estados_circuitos"), "accion",
				DataStore.DATATYPE_INT, false, true,
				AUDITA_ESTADOS_CIRCUITOS_ACCION);
		addColumn(computeTableName("audita_estados_circuitos"), "de_estado",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO);
		addColumn(computeTableName("audita_estados_circuitos"), "a_estado",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_A_ESTADO);
		addColumn(computeTableName("audita_estados_circuitos"), "user_id",
				DataStore.DATATYPE_INT, false, true,
				AUDITA_ESTADOS_CIRCUITOS_USER_ID);
		addColumn(computeTableName("audita_estados_circuitos"), "nombre_tabla",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA);
		addColumn(computeTableName("audita_estados_circuitos"), "registro_id",
				DataStore.DATATYPE_INT, false, true,
				AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID);
		addColumn(computeTableName("audita_estados_circuitos"), "host",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_HOST);
		addColumn(computeTableName("audita_estados_circuitos"), "observaciones",
				DataStore.DATATYPE_STRING, false, true,
				AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES);
		addColumn(computeTableName("circuitos_estados"), "nombre",
				DataStore.DATATYPE_STRING, false, true,
				CIRCUITOS_ESTADOS_NOMBRE);
		addColumn(computeTableName("acciones_apps"), "nombre",
				DataStore.DATATYPE_STRING, false, true, ACCIONES_APPS_NOMBRE);
		addColumn(computeTableName("website_user"), "nombre_completo",
				DataStore.DATATYPE_STRING, false, true,
				WEBSITE_USER_NOMBRE_COMPLETO);
		addColumn(computeTableName("website_user"), "nro_legajo",
				DataStore.DATATYPE_INT, false, true, WEBSITE_USER_NRO_LEGAJO);
		addColumn(computeTableName("de_estados"), "nombre",
				DataStore.DATATYPE_STRING, false, true, DE_ESTADOS_NOMBRE);
		addColumn(computeTableName("a_estados"), "nombre",
				DataStore.DATATYPE_STRING, false, true, A_ESTADOS_NOMBRE);

		// add joins
		addJoin(computeTableAndFieldName("audita_estados_circuitos.circuito"),
				computeTableAndFieldName("circuitos_estados.circuito"), false);
		addJoin(computeTableAndFieldName("audita_estados_circuitos.accion"),
				computeTableAndFieldName("acciones_apps.accion"), false);
		addJoin(computeTableAndFieldName("audita_estados_circuitos.de_estado"),
				computeTableAndFieldName("de_estados.estado"), false);
		addJoin(computeTableAndFieldName("audita_estados_circuitos.a_estado"),
				computeTableAndFieldName("a_estados.estado"), false);
		addJoin(computeTableAndFieldName("audita_estados_circuitos.user_id"),
				computeTableAndFieldName("website_user.user_id"), false);

		addLookupRule(
				AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO,
				computeTableName("de_estados"),
				"'infraestructura.estados.estado = \"' + audita_estados_circuitos.de_estado + '\"' ",
				"nombre", DE_ESTADOS_NOMBRE,
				"El estados de origen indicado no existe");
		addLookupRule(
				AUDITA_ESTADOS_CIRCUITOS_A_ESTADO,
				computeTableName("a_estados"),
				"'infraestructura.estados.estado = \"' + audita_estados_circuitos.a_estado + '\"' ",
				"nombre", A_ESTADOS_NOMBRE,
				"El estados de destino indicado no existe");

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.audita_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosAuditaId() throws DataStoreException {
		return getInt(AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.audita_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosAuditaId(int row)
			throws DataStoreException {
		return getInt(row, AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID);
	}

	/**
	 * Set the value of the audita_estados_circuitos.audita_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAuditaId(int newValue)
			throws DataStoreException {
		setInt(AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.audita_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAuditaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.circuito column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosCircuito() throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_CIRCUITO);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.circuito column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosCircuito(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_CIRCUITO);
	}

	/**
	 * Set the value of the audita_estados_circuitos.circuito column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosCircuito(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_CIRCUITO, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.circuito column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosCircuito(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_CIRCUITO, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.fecha column for the
	 * current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getAuditaEstadosCircuitosFecha()
			throws DataStoreException {
		return getDateTime(AUDITA_ESTADOS_CIRCUITOS_FECHA);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getAuditaEstadosCircuitosFecha(int row)
			throws DataStoreException {
		return getDateTime(row, AUDITA_ESTADOS_CIRCUITOS_FECHA);
	}

	/**
	 * Set the value of the audita_estados_circuitos.fecha column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosFecha(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(AUDITA_ESTADOS_CIRCUITOS_FECHA, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosFecha(int row,
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, AUDITA_ESTADOS_CIRCUITOS_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.accion column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosAccion() throws DataStoreException {
		return getInt(AUDITA_ESTADOS_CIRCUITOS_ACCION);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.accion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosAccion(int row)
			throws DataStoreException {
		return getInt(row, AUDITA_ESTADOS_CIRCUITOS_ACCION);
	}

	/**
	 * Set the value of the audita_estados_circuitos.accion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAccion(int newValue)
			throws DataStoreException {
		setInt(AUDITA_ESTADOS_CIRCUITOS_ACCION, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.accion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAccion(int row, int newValue)
			throws DataStoreException {
		setInt(row, AUDITA_ESTADOS_CIRCUITOS_ACCION, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.de_estado column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosDeEstado() throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.de_estado column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosDeEstado(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO);
	}

	/**
	 * Set the value of the audita_estados_circuitos.de_estado column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosDeEstado(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.de_estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosDeEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.a_estado column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosAEstado() throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_A_ESTADO);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.a_estado column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosAEstado(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_A_ESTADO);
	}

	/**
	 * Set the value of the audita_estados_circuitos.a_estado column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAEstado(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_A_ESTADO, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.a_estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosAEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_A_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.user_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosUserId() throws DataStoreException {
		return getInt(AUDITA_ESTADOS_CIRCUITOS_USER_ID);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.user_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosUserId(int row)
			throws DataStoreException {
		return getInt(row, AUDITA_ESTADOS_CIRCUITOS_USER_ID);
	}

	/**
	 * Set the value of the audita_estados_circuitos.user_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosUserId(int newValue)
			throws DataStoreException {
		setInt(AUDITA_ESTADOS_CIRCUITOS_USER_ID, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.user_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosUserId(int row, int newValue)
			throws DataStoreException {
		setInt(row, AUDITA_ESTADOS_CIRCUITOS_USER_ID, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.nombre_tabla column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosNombreTabla()
			throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.nombre_tabla column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosNombreTabla(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA);
	}

	/**
	 * Set the value of the audita_estados_circuitos.nombre_tabla column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosNombreTabla(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.nombre_tabla column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosNombreTabla(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.registro_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosRegistroId() throws DataStoreException {
		return getInt(AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.registro_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAuditaEstadosCircuitosRegistroId(int row)
			throws DataStoreException {
		return getInt(row, AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID);
	}

	/**
	 * Set the value of the audita_estados_circuitos.registro_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosRegistroId(int newValue)
			throws DataStoreException {
		setInt(AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.registro_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosRegistroId(int row, int newValue)
			throws DataStoreException {
		setInt(row, AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.host column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosHost() throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_HOST);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.host column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosHost(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_HOST);
	}

	/**
	 * Set the value of the audita_estados_circuitos.host column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosHost(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_HOST, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.host column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosHost(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_HOST, newValue);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosObservaciones() throws DataStoreException {
		return getString(AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the audita_estados_circuitos.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAuditaEstadosCircuitosObservaciones(int row)
			throws DataStoreException {
		return getString(row, AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the audita_estados_circuitos.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosObservaciones(String newValue)
			throws DataStoreException {
		setString(AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the audita_estados_circuitos.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAuditaEstadosCircuitosObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, AUDITA_ESTADOS_CIRCUITOS_OBSERVACIONES, newValue);
	}
	
	/**
	 * Retrieve the value of the circuitos_estados.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosNombre() throws DataStoreException {
		return getString(CIRCUITOS_ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the circuitos_estados.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosNombre(int row) throws DataStoreException {
		return getString(row, CIRCUITOS_ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the circuitos_estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosNombre(String newValue)
			throws DataStoreException {
		setString(CIRCUITOS_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the circuitos_estados.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CIRCUITOS_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the acciones_apps.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAccionesAppsNombre() throws DataStoreException {
		return getString(ACCIONES_APPS_NOMBRE);
	}

	/**
	 * Retrieve the value of the acciones_apps.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAccionesAppsNombre(int row) throws DataStoreException {
		return getString(row, ACCIONES_APPS_NOMBRE);
	}

	/**
	 * Set the value of the acciones_apps.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAccionesAppsNombre(String newValue)
			throws DataStoreException {
		setString(ACCIONES_APPS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the acciones_apps.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAccionesAppsNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ACCIONES_APPS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDeEstadosNombre() throws DataStoreException {
		return getString(DE_ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDeEstadosNombre(int row) throws DataStoreException {
		return getString(row, DE_ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDeEstadosNombre(String newValue) throws DataStoreException {
		setString(DE_ESTADOS_NOMBRE, newValue);
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
	public void setDeEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, DE_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAEstadosNombre() throws DataStoreException {
		return getString(A_ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAEstadosNombre(int row) throws DataStoreException {
		return getString(row, A_ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAEstadosNombre(String newValue) throws DataStoreException {
		setString(A_ESTADOS_NOMBRE, newValue);
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
	public void setAEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, A_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreCompleto() throws DataStoreException {
		return getString(WEBSITE_USER_NOMBRE_COMPLETO);
	}

	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_NOMBRE_COMPLETO);
	}

	/**
	 * Set the value of the website_user.nombre_completo column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreCompleto(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Set the value of the website_user.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nro_legajo column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserNroLegajo() throws DataStoreException {
		return getInt(WEBSITE_USER_NRO_LEGAJO);
	}

	/**
	 * Retrieve the value of the website_user.nro_legajo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserNroLegajo(int row) throws DataStoreException {
		return getInt(row, WEBSITE_USER_NRO_LEGAJO);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroLegajo(int newValue) throws DataStoreException {
		setInt(WEBSITE_USER_NRO_LEGAJO, newValue);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroLegajo(int row, int newValue)
			throws DataStoreException {
		setInt(row, WEBSITE_USER_NRO_LEGAJO, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
