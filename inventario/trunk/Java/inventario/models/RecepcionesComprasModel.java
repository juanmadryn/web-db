package inventario.models;

import infraestructura.models.BaseModel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * RecepcionesComprasModel: A SOFIA generated model
 */
public class RecepcionesComprasModel extends BaseModel {

	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub		
		return getRecepcionesComprasEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return getRecepcionesComprasRecepcionCompraId();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2060662906911331785L;
	// constants for columns
	public static final String RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID = "recepciones_compras.recepcion_compra_id";
	public static final String RECEPCIONES_COMPRAS_FECHA = "recepciones_compras.fecha";
	public static final String RECEPCIONES_COMPRAS_ESTADO = "recepciones_compras.estado";
	public static final String RECEPCIONES_COMPRAS_PROVEEDOR_ID = "recepciones_compras.proveedor_id";
	public static final String RECEPCIONES_COMPRAS_USER_ID_COMPLETA = "recepciones_compras.user_id_completa";
	public static final String RECEPCIONES_COMPRAS_USER_ID_RECIBE = "recepciones_compras.user_id_recibe";
	public static final String RECEPCIONES_COMPRAS_OBSERVACIONES = "recepciones_compras.observaciones";
	public static final String USER_RECIBE_NOMBRE_COMPLETO = "user_recibe.nombre_completo";
	public static final String USER_COMPLETA_NOMBRE_COMPLETO = "user_completa.nombre_completo";
	public static final String PROVEEDOR_NOMBRE = "proveedores.nombre";
	public static final String ESTADOS_NOMBRE = "estados.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";
	// $ENDCUSTOMVARS$

	/**
	 * Create a new RecepcionesComprasModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public RecepcionesComprasModel(String appName) throws DataStoreException {
		this(appName, null);
	}

	/**
	 * Create a new RecepcionesComprasModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public RecepcionesComprasModel(String appName, String profile)
			throws DataStoreException {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("recepciones_compras"), "recepciones");
		addTableAlias(computeTableName("infraestructura.estados"), "estados");
		addTableAlias(computeTableName("infraestructura.website_user"), "website_user_completa");
		addTableAlias(computeTableName("infraestructura.website_user"), "website_user_recibe");
		addTableAlias(computeTableName("proveedores"), "proveedores");

		// add columns
		addColumn(computeTableName("recepciones_compras"),
				"recepcion_compra_id", DataStore.DATATYPE_INT, true, true,
				RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID);
		addColumn(computeTableName("recepciones_compras"), "fecha",
				DataStore.DATATYPE_DATETIME, false, true,
				RECEPCIONES_COMPRAS_FECHA);
		addColumn(computeTableName("recepciones_compras"), "estado",
				DataStore.DATATYPE_STRING, false, true,
				RECEPCIONES_COMPRAS_ESTADO);
		addColumn(computeTableName("recepciones_compras"), "proveedor_id",
				DataStore.DATATYPE_INT, false, true,
				RECEPCIONES_COMPRAS_PROVEEDOR_ID);
		addColumn(computeTableName("recepciones_compras"), "user_id_completa",
				DataStore.DATATYPE_INT, false, true,
				RECEPCIONES_COMPRAS_USER_ID_COMPLETA);
		addColumn(computeTableName("recepciones_compras"), "user_id_recibe",
				DataStore.DATATYPE_INT, false, true,
				RECEPCIONES_COMPRAS_USER_ID_RECIBE);
		addColumn(computeTableName("recepciones_compras"), "observaciones",
				DataStore.DATATYPE_STRING, false, true,
				RECEPCIONES_COMPRAS_OBSERVACIONES);
		addColumn(computeTableName("estados"), "nombre",
				DataStore.DATATYPE_STRING, false, true, ESTADOS_NOMBRE);
		addColumn(computeTableName("proveedores"), "nombre",
				DataStore.DATATYPE_STRING, false, true, PROVEEDOR_NOMBRE);
		addColumn(computeTableName("website_user_completa"), "nombre_completo",
				DataStore.DATATYPE_STRING, false, true,
				USER_COMPLETA_NOMBRE_COMPLETO);
		addColumn(computeTableName("website_user_recibe"), "nombre_completo",
				DataStore.DATATYPE_STRING, false, true,
				USER_RECIBE_NOMBRE_COMPLETO);

		// add joins
		addJoin(computeTableAndFieldName("recepciones_compras.estado"),
				computeTableAndFieldName("estados.estado"), false);
		
		addJoin(computeTableAndFieldName("recepciones_compras.proveedor_id"),
				computeTableAndFieldName("proveedores.entidad_id"), false);
		addJoin(
				computeTableAndFieldName("recepciones_compras.user_id_completa"),
				computeTableAndFieldName("website_user_completa.user_id"), false);
		
		addJoin(computeTableAndFieldName("recepciones_compras.user_id_recibe"),
				computeTableAndFieldName("website_user_recibe.user_id"), true);

		// add validations
		addRequiredRule(RECEPCIONES_COMPRAS_ESTADO,
				"Debe indicar el estado de la recepción");
		addRequiredRule(RECEPCIONES_COMPRAS_FECHA,
				"Debe indicar la fecha de la recepción");
		addRequiredRule(RECEPCIONES_COMPRAS_PROVEEDOR_ID,
				"El proveedor es obligatorio");
		addRequiredRule(RECEPCIONES_COMPRAS_USER_ID_COMPLETA,
				"Indique el usuario que completa la recepción");	

		// add lookups
		addLookupRule(
				RECEPCIONES_COMPRAS_ESTADO,
				"infraestructura.estados",
				"'infraestructura.estados.estado = ' + recepciones_compras.estado",
				"nombre", ESTADOS_NOMBRE, "Estado inexistente");
		addLookupRule(
				RECEPCIONES_COMPRAS_PROVEEDOR_ID,
				computeTableName("proveedores"),
				"'inventario.proveedores.entidad_id = ' + recepciones_compras.proveedor_id",
				"nombre", PROVEEDOR_NOMBRE, "Proveedor inexistente");
		addLookupRule(
				RECEPCIONES_COMPRAS_USER_ID_COMPLETA,
				"infraestructura.website_user",
				"'infraestructura.website_user.user_id = ' + recepciones_compras.user_id_completa",
				"nombre_completo", USER_COMPLETA_NOMBRE_COMPLETO,
				"Usuario inexistente");
		addLookupRule(
				RECEPCIONES_COMPRAS_USER_ID_RECIBE,
				"infraestructura.website_user",
				"'infraestructura.website_user.user_id = ' + recepciones_compras.user_id_recibe",
				"nombre_completo", USER_RECIBE_NOMBRE_COMPLETO,
				"Usuario inexistente");

		setAutoIncrement(RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID, true);
		
		addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);
		
		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the recepciones_compras.recepcion_compra_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasRecepcionCompraId()
			throws DataStoreException {
		return getInt(RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the recepciones_compras.recepcion_compra_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasRecepcionCompraId(int row)
			throws DataStoreException {
		return getInt(row, RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID);
	}

	/**
	 * Set the value of the recepciones_compras.recepcion_compra_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasRecepcionCompraId(int newValue)
			throws DataStoreException {
		setInt(RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.recepcion_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasRecepcionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the recepciones_compras.fecha column for the
	 * current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getRecepcionesComprasFecha()
			throws DataStoreException {
		return getDateTime(RECEPCIONES_COMPRAS_FECHA);
	}

	/**
	 * Retrieve the value of the recepciones_compras.fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getRecepcionesComprasFecha(int row)
			throws DataStoreException {
		return getDateTime(row, RECEPCIONES_COMPRAS_FECHA);
	}

	/**
	 * Set the value of the recepciones_compras.fecha column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasFecha(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(RECEPCIONES_COMPRAS_FECHA, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.fecha column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasFecha(int row, java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(row, RECEPCIONES_COMPRAS_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the recepciones_compras.estado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRecepcionesComprasEstado() throws DataStoreException {
		return getString(RECEPCIONES_COMPRAS_ESTADO);
	}

	/**
	 * Retrieve the value of the recepciones_compras.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRecepcionesComprasEstado(int row)
			throws DataStoreException {
		return getString(row, RECEPCIONES_COMPRAS_ESTADO);
	}

	/**
	 * Set the value of the recepciones_compras.estado column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasEstado(String newValue)
			throws DataStoreException {
		setString(RECEPCIONES_COMPRAS_ESTADO, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, RECEPCIONES_COMPRAS_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the recepciones_compras.provedor_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasProvedorId() throws DataStoreException {
		return getInt(RECEPCIONES_COMPRAS_PROVEEDOR_ID);
	}

	/**
	 * Retrieve the value of the recepciones_compras.provedor_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasProvedorId(int row)
			throws DataStoreException {
		return getInt(row, RECEPCIONES_COMPRAS_PROVEEDOR_ID);
	}

	/**
	 * Set the value of the recepciones_compras.provedor_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasProvedorId(int newValue)
			throws DataStoreException {
		setInt(RECEPCIONES_COMPRAS_PROVEEDOR_ID, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.provedor_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasProvedorId(int row, int newValue)
			throws DataStoreException {
		setInt(row, RECEPCIONES_COMPRAS_PROVEEDOR_ID, newValue);
	}

	/**
	 * Retrieve the value of the recepciones_compras.user_id_completa column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasUserIdCompleta() throws DataStoreException {
		return getInt(RECEPCIONES_COMPRAS_USER_ID_COMPLETA);
	}

	/**
	 * Retrieve the value of the recepciones_compras.user_id_completa column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasUserIdCompleta(int row)
			throws DataStoreException {
		return getInt(row, RECEPCIONES_COMPRAS_USER_ID_COMPLETA);
	}

	/**
	 * Set the value of the recepciones_compras.user_id_completa column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasUserIdCompleta(int newValue)
			throws DataStoreException {
		setInt(RECEPCIONES_COMPRAS_USER_ID_COMPLETA, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.user_id_completa column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasUserIdCompleta(int row, int newValue)
			throws DataStoreException {
		setInt(row, RECEPCIONES_COMPRAS_USER_ID_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the recepciones_compras.user_id_recibe column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasUserIdRecibe() throws DataStoreException {
		return getInt(RECEPCIONES_COMPRAS_USER_ID_RECIBE);
	}

	/**
	 * Retrieve the value of the recepciones_compras.user_id_recibe column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprasUserIdRecibe(int row)
			throws DataStoreException {
		return getInt(row, RECEPCIONES_COMPRAS_USER_ID_RECIBE);
	}

	/**
	 * Set the value of the recepciones_compras.user_id_recibe column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasUserIdRecibe(int newValue)
			throws DataStoreException {
		setInt(RECEPCIONES_COMPRAS_USER_ID_RECIBE, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.user_id_recibe column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasUserIdRecibe(int row, int newValue)
			throws DataStoreException {
		setInt(row, RECEPCIONES_COMPRAS_USER_ID_RECIBE, newValue);
	}


	/**
	 * Retrieve the value of the recepciones_compras.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRecepcionesComprasObservaciones() throws DataStoreException {
		return getString(RECEPCIONES_COMPRAS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the recepciones_compras.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRecepcionesComprasObservaciones(int row)
			throws DataStoreException {
		return getString(row, RECEPCIONES_COMPRAS_OBSERVACIONES);
	}

	/**
	 * Set the value of the recepciones_compras.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasObservaciones(String newValue)
			throws DataStoreException {
		setString(RECEPCIONES_COMPRAS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the recepciones_compras.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprasObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, RECEPCIONES_COMPRAS_OBSERVACIONES, newValue);
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
	public void getEstadosNombre(String newValue) throws DataStoreException {
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
	public void getEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the proveedores.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProveedorNombre() throws DataStoreException {
		return getString(PROVEEDOR_NOMBRE);
	}

	/**
	 * Retrieve the value of the proveedores.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProveedorNombre(int row) throws DataStoreException {
		return getString(row, PROVEEDOR_NOMBRE);
	}

	/**
	 * Set the value of the proveedores.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProveedorNombre(String newValue) throws DataStoreException {
		setString(PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Set the value of the proveedores.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProveedorNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the user_completa.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUserCompletaNombreCompleto() throws DataStoreException {
		return getString(USER_COMPLETA_NOMBRE_COMPLETO);
	}

	/**
	 * Retrieve the value of the user_completa.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUserCompletaNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, USER_COMPLETA_NOMBRE_COMPLETO);
	}

	/**
	 * Set the value of the user_completa.nombre_completo column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUserCompletaNombreCompleto(String newValue)
			throws DataStoreException {
		setString(USER_COMPLETA_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Set the value of the user_completa.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUserCompletaNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, USER_COMPLETA_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Retrieve the value of the user_recibe.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUserRecibeNombreCompleto() throws DataStoreException {
		return getString(USER_RECIBE_NOMBRE_COMPLETO);
	}

	/**
	 * Retrieve the value of the user_recibe.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUserRecibeNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, USER_RECIBE_NOMBRE_COMPLETO);
	}

	/**
	 * Set the value of the user_recibe.nombre_completo column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUserRecibeNombreCompleto(String newValue)
			throws DataStoreException {
		setString(USER_RECIBE_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Set the value of the user_recibe.nombre_completo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUserRecibeNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, USER_RECIBE_NOMBRE_COMPLETO, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
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
	
	private void completarDatosRecepcion() throws DataStoreException, SQLException{
		if (getRecepcionesComprasEstado() == null)
			setRecepcionesComprasEstado("0009.0001");
		if (getRecepcionesComprasFecha() == null)
			setRecepcionesComprasFecha(new Timestamp((Calendar.getInstance()
					.getTimeInMillis())));
		if(getRecepcionesComprasUserIdCompleta() == 0)
			setRecepcionesComprasUserIdCompleta(getCurrentWebsiteUserId());
	}
	
	@Override
	public void update()
			throws DataStoreException, SQLException {
		completarDatosRecepcion();
		super.update();
	}
	
	@Override
	public void update(DBConnection conn)
			throws DataStoreException, SQLException {
		completarDatosRecepcion();
		update(conn, false);
	}
	
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		completarDatosRecepcion();
		super.update(conn, handleTrans);
	}
	// $ENDCUSTOMMETHODS$

}