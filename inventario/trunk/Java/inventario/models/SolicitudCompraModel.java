package inventario.models;

import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.BaseModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import proyectos.models.ProyectoModel;

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
public class SolicitudCompraModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5225153047671545807L;
	// constants for columns
	public static final String SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID = "solicitudes_compra.solicitud_compra_id";
	public static final String SOLICITUDES_COMPRA_USER_ID_COMPRADOR = "solicitudes_compra.user_id_comprador";
	public static final String SOLICITUDES_COMPRA_USER_ID_SOLICITA = "solicitudes_compra.user_id_solicita";
	public static final String SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String SOLICITUDES_COMPRA_FECHA_SOLICITUD = "solicitudes_compra.fecha_solicitud";
	public static final String SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String SOLICITUDES_COMPRA_FECHA_OC = "solicitudes_compra.fecha_oc";
	public static final String SOLICITUDES_COMPRA_DESCRIPCION = "solicitudes_compra.descripcion";
	public static final String SOLICITUDES_COMPRA_OBSERVACIONES = "solicitudes_compra.observaciones";
	public static final String SOLICITUDES_COMPRA_CENTRO_COSTO_ID = "solicitudes_compra.centro_costo_id";
	public static final String SOLICITUDES_COMPRA_PROYECTO_ID = "solicitudes_compra.proyecto_id";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String ESTADO_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_NOMBRE_SOLICITANTE = "nombre_completo_solicitante";
	public static final String WEBSITE_USER_NOMBRE_COMPRADOR = "nombre_completo_comprador";
	public static final String PROYECTOS_PROYECTO = "proyetos.proyecto";
	public static final String PROYECTOS_NOMBRE = "proyetos.nombre";
	public static final String CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";
	public static final String ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String TOTAL_SOLICITUD = "total_solicitud_compra";
	public static final String OBSERVACIONES = "observaciones";

	// $ENDCUSTOMVARS$

	/**
	 * Create a new java object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public SolicitudCompraModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new java object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public SolicitudCompraModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("solicitudes_compra"),
					"solicitudes_compra");
			addTableAlias(computeTableName("infraestructura.estados"),
					"estados");
			addTableAlias(computeTableName("infraestructura.website_user"),
					"website_user_comprador");
			addTableAlias(computeTableName("infraestructura.website_user"),
					"website_user_solicitante");
			addTableAlias(computeTableName("proyectos.proyectos"), "proyectos");
			addTableAlias(computeTableName("centro_costo"), "centro_costo");

			// add columns
			addColumn(computeTableName("solicitudes_compra"),
					"solicitud_compra_id", DataStore.DATATYPE_INT, true, true,
					SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID);
			addColumn(computeTableName("solicitudes_compra"),
					"user_id_comprador", DataStore.DATATYPE_INT, false, true,
					SOLICITUDES_COMPRA_USER_ID_COMPRADOR);
			addColumn(computeTableName("solicitudes_compra"),
					"user_id_solicita", DataStore.DATATYPE_INT, false, true,
					SOLICITUDES_COMPRA_USER_ID_SOLICITA);
			addColumn(computeTableName("solicitudes_compra"), "estado",
					DataStore.DATATYPE_STRING, false, true,
					SOLICITUDES_COMPRA_ESTADO);
			addColumn(computeTableName("solicitudes_compra"),
					"fecha_solicitud", DataStore.DATATYPE_DATE, false, true,
					SOLICITUDES_COMPRA_FECHA_SOLICITUD);
			addColumn(computeTableName("solicitudes_compra"),
					"fecha_aprobacion", DataStore.DATATYPE_DATE, false, true,
					SOLICITUDES_COMPRA_FECHA_APROBACION);
			addColumn(computeTableName("solicitudes_compra"), "fecha_oc",
					DataStore.DATATYPE_DATE, false, true,
					SOLICITUDES_COMPRA_FECHA_OC);
			addColumn(computeTableName("solicitudes_compra"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					SOLICITUDES_COMPRA_DESCRIPCION);
			addColumn(computeTableName("solicitudes_compra"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					SOLICITUDES_COMPRA_OBSERVACIONES);
			addColumn(computeTableName("solicitudes_compra"),
					"centro_costo_id", DataStore.DATATYPE_INT, false, true,
					SOLICITUDES_COMPRA_CENTRO_COSTO_ID);
			addColumn(computeTableName("solicitudes_compra"), "proyecto_id",
					DataStore.DATATYPE_INT, false, true,
					SOLICITUDES_COMPRA_PROYECTO_ID);
			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, true, ESTADO_NOMBRE);
			addColumn(computeTableName("website_user_solicitante"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NOMBRE_SOLICITANTE);
			addColumn(computeTableName("website_user_comprador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NOMBRE_COMPRADOR);
			addColumn(computeTableName("proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
			addColumn(computeTableName("proyectos"), "proyecto",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_PROYECTO);
			addColumn(computeTableName("centro_costo"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CENTRO_COSTO_NOMBRE);

			// add buckets
			addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);
			addBucket(ESQUEMA_CONFIGURACION_ID, DATATYPE_INT);
			addBucket(TOTAL_SOLICITUD, DATATYPE_FLOAT);
			addBucket(OBSERVACIONES, DATATYPE_STRING);

			// add joins

			addJoin(computeTableAndFieldName("solicitudes_compra.estado"),
					computeTableAndFieldName("estados.estado"), true);

			addJoin(
					computeTableAndFieldName("solicitudes_compra.user_id_comprador"),
					computeTableAndFieldName("website_user_comprador.user_id"),
					true);

			addJoin(
					computeTableAndFieldName("solicitudes_compra.user_id_solicita"),
					computeTableAndFieldName("website_user_solicitante.user_id"),
					true);

			addJoin(
					computeTableAndFieldName("solicitudes_compra.centro_costo_id"),
					computeTableAndFieldName("centro_costo.centro_costo_id"),
					true);

			addJoin(computeTableAndFieldName("solicitudes_compra.proyecto_id"),
					computeTableAndFieldName("proyectos.proyecto_id"), true);

			// add validations
			addRequiredRule(SOLICITUDES_COMPRA_USER_ID_SOLICITA,
					"Debe especificar un usuario solicitante.");
			addRequiredRule(SOLICITUDES_COMPRA_FECHA_SOLICITUD,
					"La fecha de la solicitud es obligatoria.");

			// add lookups
			addLookupRule(
					SOLICITUDES_COMPRA_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = ' + solicitudes_compra.estado",
					"nombre", ESTADO_NOMBRE, "Estado inexistente");
			addLookupRule(
					SOLICITUDES_COMPRA_USER_ID_COMPRADOR,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + solicitudes_compra.user_id_comprador",
					"nombre_completo", WEBSITE_USER_NOMBRE_COMPRADOR,
					"Usuario inexistente");
			addLookupRule(
					SOLICITUDES_COMPRA_USER_ID_SOLICITA,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + solicitudes_compra.user_id_solicita",
					"nombre_completo", WEBSITE_USER_NOMBRE_SOLICITANTE,
					"Usuario inexistente");
			addLookupRule(
					SOLICITUDES_COMPRA_CENTRO_COSTO_ID,
					"centro_costo",
					"'centro_costo.centro_costo_id = ' + solicitudes_compra.centro_costo_id",
					"nombre", CENTRO_COSTO_NOMBRE,
					"Centro de costo inexistente.");
			addLookupRule(
					SOLICITUDES_COMPRA_PROYECTO_ID,
					"proyectos.proyectos",
					"'proyectos.proyectos.proyecto_id = ' + solicitudes_compra.proyecto_id",
					"nombre", PROYECTOS_NOMBRE, "Proyecto inexistente");
			addLookupRule(
					SOLICITUDES_COMPRA_PROYECTO_ID,
					"proyectos.proyectos",
					"'proyectos.proyectos.proyecto_id = ' + solicitudes_compra.proyecto_id",
					"proyecto", PROYECTOS_PROYECTO, "Proyecto inexistente");

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the solicitudes_compra.solicitud_compra_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraSolicitudCompraId()
			throws DataStoreException {
		return getInt(SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.solicitud_compra_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraSolicitudCompraId(int row)
			throws DataStoreException {
		return getInt(row, SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Set the value of the solicitudes_compra.solicitud_compra_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraSolicitudCompraId(int newValue)
			throws DataStoreException {
		setInt(SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.solicitud_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraSolicitudCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.user_id_comprador column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraUserIdComprador() throws DataStoreException {
		return getInt(SOLICITUDES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.user_id_comprador column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraUserIdComprador(int row)
			throws DataStoreException {
		return getInt(row, SOLICITUDES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Set the value of the solicitudes_compra.user_id_comprador column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraUserIdComprador(int newValue)
			throws DataStoreException {
		setInt(SOLICITUDES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.user_id_comprador column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraUserIdComprador(int row, int newValue)
			throws DataStoreException {
		setInt(row, SOLICITUDES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.user_id_solicita column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraUserIdSolicita() throws DataStoreException {
		return getInt(SOLICITUDES_COMPRA_USER_ID_SOLICITA);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.user_id_solicita column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraUserIdSolicita(int row)
			throws DataStoreException {
		return getInt(row, SOLICITUDES_COMPRA_USER_ID_SOLICITA);
	}

	/**
	 * Set the value of the solicitudes_compra.user_id_solicita column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraUserIdSolicita(int newValue)
			throws DataStoreException {
		setInt(SOLICITUDES_COMPRA_USER_ID_SOLICITA, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.user_id_solicita column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraUserIdSolicita(int row, int newValue)
			throws DataStoreException {
		setInt(row, SOLICITUDES_COMPRA_USER_ID_SOLICITA, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.estado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraEstado() throws DataStoreException {
		return getString(SOLICITUDES_COMPRA_ESTADO);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraEstado(int row) throws DataStoreException {
		return getString(row, SOLICITUDES_COMPRA_ESTADO);
	}

	/**
	 * Set the value of the solicitudes_compra.estado column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraEstado(String newValue)
			throws DataStoreException {
		setString(SOLICITUDES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, SOLICITUDES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_solicitud column for
	 * the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaSolicitud()
			throws DataStoreException {
		return getDate(SOLICITUDES_COMPRA_FECHA_SOLICITUD);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_solicitud column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaSolicitud(int row)
			throws DataStoreException {
		return getDate(row, SOLICITUDES_COMPRA_FECHA_SOLICITUD);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_solicitud column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaSolicitud(java.sql.Date newValue)
			throws DataStoreException {
		setDate(SOLICITUDES_COMPRA_FECHA_SOLICITUD, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_solicitud column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaSolicitud(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, SOLICITUDES_COMPRA_FECHA_SOLICITUD, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_aprobacion column for
	 * the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaAprobacion()
			throws DataStoreException {
		return getDate(SOLICITUDES_COMPRA_FECHA_APROBACION);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_aprobacion column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaAprobacion(int row)
			throws DataStoreException {
		return getDate(row, SOLICITUDES_COMPRA_FECHA_APROBACION);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_aprobacion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaAprobacion(java.sql.Date newValue)
			throws DataStoreException {
		setDate(SOLICITUDES_COMPRA_FECHA_APROBACION, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_aprobacion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaAprobacion(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, SOLICITUDES_COMPRA_FECHA_APROBACION, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_oc column for the
	 * current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaOc()
			throws DataStoreException {
		return getDate(SOLICITUDES_COMPRA_FECHA_OC);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.fecha_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getSolicitudesCompraFechaOc(int row)
			throws DataStoreException {
		return getDate(row, SOLICITUDES_COMPRA_FECHA_OC);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_oc column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaOc(java.sql.Date newValue)
			throws DataStoreException {
		setDate(SOLICITUDES_COMPRA_FECHA_OC, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.fecha_oc column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraFechaOc(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, SOLICITUDES_COMPRA_FECHA_OC, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraDescripcion() throws DataStoreException {
		return getString(SOLICITUDES_COMPRA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraDescripcion(int row)
			throws DataStoreException {
		return getString(row, SOLICITUDES_COMPRA_DESCRIPCION);
	}

	/**
	 * Set the value of the solicitudes_compra.descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraDescripcion(String newValue)
			throws DataStoreException {
		setString(SOLICITUDES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, SOLICITUDES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraObservaciones() throws DataStoreException {
		return getString(SOLICITUDES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudesCompraObservaciones(int row)
			throws DataStoreException {
		return getString(row, SOLICITUDES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Set the value of the solicitudes_compra.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraObservaciones(String newValue)
			throws DataStoreException {
		setString(SOLICITUDES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, SOLICITUDES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.centro_costo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraCentroCostoId() throws DataStoreException {
		return getInt(SOLICITUDES_COMPRA_CENTRO_COSTO_ID);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.centro_costo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraCentroCostoId(int row)
			throws DataStoreException {
		return getInt(row, SOLICITUDES_COMPRA_CENTRO_COSTO_ID);
	}

	/**
	 * Set the value of the solicitudes_compra.centro_costo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraCentroCostoId(int newValue)
			throws DataStoreException {
		setInt(SOLICITUDES_COMPRA_CENTRO_COSTO_ID, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.centro_costo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraCentroCostoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, SOLICITUDES_COMPRA_CENTRO_COSTO_ID, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	/**
	 * Retrieve the value of the estados.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadoNombre() throws DataStoreException {
		return getString(ESTADO_NOMBRE);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadoNombre(int row) throws DataStoreException {
		return getString(row, ESTADO_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEstadoNombre(String newValue) throws DataStoreException {
		setString(ESTADO_NOMBRE, newValue);
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
	public void setEstadoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESTADO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreComprador() throws DataStoreException {
		return getString(WEBSITE_USER_NOMBRE_COMPRADOR);
	}

	/**
	 * Retrieve the value of the website_user.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreComprador(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_NOMBRE_COMPRADOR);
	}

	/**
	 * Set the value of the website_user.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreComprador(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the website_user.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreComprador(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_COMPRADOR, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreSolicitante() throws DataStoreException {
		return getString(WEBSITE_USER_NOMBRE_SOLICITANTE);
	}

	/**
	 * Retrieve the value of the website_user.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreSolicitante(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_NOMBRE_SOLICITANTE);
	}

	/**
	 * Set the value of the website_user.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreSolicitante(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_SOLICITANTE, newValue);
	}

	/**
	 * Set the value of the website_user.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreSolicitante(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_SOLICITANTE, newValue);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.proyecto_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraProyectoId() throws DataStoreException {
		return getInt(SOLICITUDES_COMPRA_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the solicitudes_compra.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getSolicitudesCompraProyectoId(int row)
			throws DataStoreException {
		return getInt(row, SOLICITUDES_COMPRA_PROYECTO_ID);
	}

	/**
	 * Set the value of the solicitudes_compra.proyecto_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraProyectoId(int newValue)
			throws DataStoreException {
		setInt(SOLICITUDES_COMPRA_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the solicitudes_compra.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudesCompraProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, SOLICITUDES_COMPRA_PROYECTO_ID, newValue);
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
	 * Retrieve the value of the centro_costo.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCentroCostoNombre() throws DataStoreException {
		return getString(CENTRO_COSTO_NOMBRE);
	}

	/**
	 * Retrieve the value of the centro_costo.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCentroCostoNombre(int row) throws DataStoreException {
		return getString(row, CENTRO_COSTO_NOMBRE);
	}

	/**
	 * Set the value of the centro_costo.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCentroCostoNombre(String newValue) throws DataStoreException {
		setString(CENTRO_COSTO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the centro_costo.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCentroCostoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CENTRO_COSTO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the current website user id bucket
	 * 
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCurrentWebsiteUserId() throws DataStoreException {
		return getInt(CURRENT_WEBSITE_USER_ID);
	}

	/**
	 * Set the value of the current website user id bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCurrentWebsiteUserId(int newValue) throws DataStoreException {
		setInt(CURRENT_WEBSITE_USER_ID, newValue);
	}

	/**
	 * Retrieve the value of the current website user id bucket
	 * 
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getEsquemaConfiguracionId() throws DataStoreException {
		return getInt(ESQUEMA_CONFIGURACION_ID);
	}

	/**
	 * Set the value of the current website user id bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEsquemaConfiguracionId(int newValue)
			throws DataStoreException {
		setInt(ESQUEMA_CONFIGURACION_ID, newValue);
	}

	/**
	 * Retrieve the value of the total_solicitud bucket
	 * 
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getTotalSolicitud() throws DataStoreException {
		return getFloat(TOTAL_SOLICITUD);
	}

	/**
	 * Set the value of the total_solicitud bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTotalSolicitud(float newValue) throws DataStoreException {
		setFloat(TOTAL_SOLICITUD, newValue);
	}

	/**
	 * Retrieve the value of the observaciones bucket
	 * 
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getObservaciones() throws DataStoreException {
		return getString(OBSERVACIONES);
	}

	/**
	 * Set the value of the observaciones bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setObservaciones(String newValue) throws DataStoreException {
		setString(OBSERVACIONES, newValue);
	}

	public float getAtributoTotalSolicitud() throws DataStoreException,
			SQLException {

		int solicitud_id = getSolicitudesCompraSolicitudCompraId();

		float total = 0;

		DetalleSCModel detalles = new DetalleSCModel("inventario", "inventario");
		detalles.retrieve("detalle_sc.solicitud_compra_id = " + solicitud_id);

		for (int row = 0; row < detalles.getRowCount(); row++) {
			detalles.setMontoTotal(row, this);
			total += detalles.getMontoTotal(row);
		}

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(total),
				"TOTAL_SOLICITUD", solicitud_id, "TABLA", "solicitudes_compra");

		return total;

	}

	@Override
	public void update() throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		completarDatosSolicitud();
		super.update();

	}
	
	@Override
	public void update(DBConnection conn) throws DataStoreException,
			SQLException {
		// TODO Auto-generated method stub
		completarDatosSolicitud();
		super.update(conn);
	}

	private void completarDatosSolicitud() throws DataStoreException, SQLException{

		if (!(getSolicitudesCompraCentroCostoId() == 0 ^ getProyectosProyecto() == null))
			throw new DataStoreException(
					"Debe imputar la solicitud a un Centro de costo o a un Proyecto.");

		if (getSolicitudesCompraEstado() == null)
			setSolicitudesCompraEstado("0006.0001");

		if (getProyectosProyecto() != null) {
			ProyectoModel dsProyecto = new ProyectoModel("proyectos",
					"proyectos");
			dsProyecto.retrieve("proyecto = '" + getProyectosProyecto() + "'");
			dsProyecto.gotoFirst();
			setSolicitudesCompraProyectoId(dsProyecto.getProyectosProyectoId());
		} else
			setSolicitudesCompraProyectoId(0);

		if (getSolicitudesCompraFechaSolicitud() == null)
			setSolicitudesCompraFechaSolicitud(new Date((Calendar.getInstance()
					.getTimeInMillis())));

	}

	public void setObservaciones() throws DataStoreException, SQLException {
		InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
				"inventario", "inventario");
		instancia.retrieve("solicitud_compra_id ="
				+ getSolicitudesCompraSolicitudCompraId() + " AND estado ="
				+ "'0007.0001' AND mensaje IS NOT NULL");
		if (instancia.gotoFirst())
			setObservaciones(instancia.getInstanciasAprobacionMensaje());

	}

	public static int getSolicitudesCompraPendientesAprobacion(int user_id) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		int solicitudes_pendientes = 0;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");

			String SQL = "SELECT count(*) " +
					"FROM  solicitudes_compra solicitudes_compra " +
					"LEFT OUTER JOIN infraestructura.estados estados ON solicitudes_compra.estado = estados.estado  " +
					"LEFT OUTER JOIN infraestructura.website_user website_user_comprador ON solicitudes_compra.user_id_comprador = website_user_comprador.user_id  " +
					"LEFT OUTER JOIN infraestructura.website_user website_user_solicitante ON solicitudes_compra.user_id_solicita = website_user_solicitante.user_id  " +
					"LEFT OUTER JOIN centro_costo centro_costo ON solicitudes_compra.centro_costo_id = centro_costo.centro_costo_id  " +
					"LEFT OUTER JOIN proyectos.proyectos proyectos ON solicitudes_compra.proyecto_id = proyectos.proyecto_id " +
					"WHERE solicitudes_compra.solicitud_compra_id IN " +
					"(SELECT solicitud_compra_id FROM inventario.instancias_aprobacion i WHERE i.estado LIKE '0007.0001' and i.user_firmante ="
					+ user_id + ")";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				// guarda la cantidad actual
				solicitudes_pendientes = r.getInt(1);				
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
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

			if (conn != null)
				conn.freeConnection();
		}
		return solicitudes_pendientes;
	}

	// $ENDCUSTOMMETHODS$

	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return getSolicitudesCompraEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return getSolicitudesCompraSolicitudCompraId();
	}

}
