package inventario.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ArticulosCompradosModel: A SOFIA generated model
 */
public class ArticulosCompradosModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1452612161900271696L;
	// constants for columns
	public static final String ARTICULOS_COMPRADOS_DETALLE_SC_ID = "articulos_comprados.detalle_SC_id";
	public static final String ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID = "articulos_comprados.solicitud_compra_id";
	public static final String ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID = "articulos_comprados.orden_compra_id";
	public static final String ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA = "articulos_comprados.cantidad_pedida";
	public static final String ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA = "articulos_comprados.cantidad_recibida";
	public static final String ARTICULOS_COMPRADOS_ESTADO = "articulos_comprados.estado";
	public static final String ARTICULOS_COMPRADOS_NOMBRE = "articulos_comprados.nombre";
	public static final String ARTICULOS_COMPRADOS_DESCRIPCION = "articulos_comprados.descripcion";
	public static final String ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA = "articulos_comprados.descripcion_completa";
	public static final String ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR = "articulos_comprados.entidad_id_proveedor";
	public static final String ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE = "articulos_comprados.proveedor_nombre";
	public static final String ARTICULOS_COMPRADOS_FECHA = "articulos_comprados.fecha";
	public static final String ARTICULOS_COMPRADOS_USER_ID_COMPRADOR = "articulos_comprados.user_id_comprador";
	public static final String ARTICULOS_COMPRADOS_COMPRADOR = "articulos_comprados.comprador";
	public static final String ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE = "articulos_comprados.user_id_solicitante";
	public static final String ARTICULOS_COMPRADOS_SOLICITANTE = "articulos_comprados.solicitante";

	// $CUSTOMVARS$

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ArticulosCompradosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ArticulosCompradosModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ArticulosCompradosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ArticulosCompradosModel(String appName, String profile) {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("articulos_comprados"),
				"articulos_comprados");

		// add columns
		addColumn(computeTableName("articulos_comprados"), "detalle_SC_id",
				DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_DETALLE_SC_ID);
		addColumn(computeTableName("articulos_comprados"), "solicitud_compra_id",
				DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID);
		addColumn(computeTableName("articulos_comprados"), "orden_compra_id",
				DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID);
		addColumn(computeTableName("articulos_comprados"), "cantidad_pedida",
				DataStore.DATATYPE_DOUBLE, false, false,
				ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA);
		addColumn(computeTableName("articulos_comprados"), "cantidad_recibida",
				DataStore.DATATYPE_DOUBLE, false, false,
				ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA);
		addColumn(computeTableName("articulos_comprados"), "estado",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_ESTADO);
		addColumn(computeTableName("articulos_comprados"), "nombre",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_NOMBRE);
		addColumn(computeTableName("articulos_comprados"), "descripcion",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_DESCRIPCION);
		addColumn(computeTableName("articulos_comprados"),
				"descripcion_completa", DataStore.DATATYPE_STRING, false,
				false, ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA);
		addColumn(computeTableName("articulos_comprados"),
				"entidad_id_proveedor", DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR);
		addColumn(computeTableName("articulos_comprados"), "proveedor_nombre",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE);
		addColumn(computeTableName("articulos_comprados"), "fecha",
				DataStore.DATATYPE_DATETIME, false, false,
				ARTICULOS_COMPRADOS_FECHA);
		addColumn(computeTableName("articulos_comprados"),
				"user_id_comprador", DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_USER_ID_COMPRADOR);
		addColumn(computeTableName("articulos_comprados"), "comprador",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_COMPRADOR);
		addColumn(computeTableName("articulos_comprados"),
				"user_id_solicitante", DataStore.DATATYPE_INT, false, false,
				ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE);
		addColumn(computeTableName("articulos_comprados"), "solicitante",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_COMPRADOS_SOLICITANTE);

		// $CUSTOMCONSTRUCTOR$

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the articulos_comprados.detalle_SC_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosDetalleScId() throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_DETALLE_SC_ID);
	}

	/**
	 * Retrieve the value of the articulos_comprados.detalle_SC_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosDetalleScId(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_DETALLE_SC_ID);
	}

	/**
	 * Set the value of the articulos_comprados.detalle_SC_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDetalleScId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_DETALLE_SC_ID, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.detalle_SC_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDetalleScId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_DETALLE_SC_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.orden_compra_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosSolicitudCompraId() throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the articulos_comprados.orden_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosSolicitudCompraId(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Set the value of the articulos_comprados.orden_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosSolicitudCompraId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.orden_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosSolicitudCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_SOLICITUD_COMPRA_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the articulos_comprados.orden_compra_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosOrdenCompraId() throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the articulos_comprados.orden_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosOrdenCompraId(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID);
	}

	/**
	 * Set the value of the articulos_comprados.orden_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosOrdenCompraId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.orden_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosOrdenCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.cantidad_pedida column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getArticulosCompradosCantidadPedida()
			throws DataStoreException {
		return getDouble(ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA);
	}

	/**
	 * Retrieve the value of the articulos_comprados.cantidad_pedida column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getArticulosCompradosCantidadPedida(int row)
			throws DataStoreException {
		return getDouble(row, ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA);
	}

	/**
	 * Set the value of the articulos_comprados.cantidad_pedida column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosCantidadPedida(double newValue)
			throws DataStoreException {
		setDouble(ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.cantidad_pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosCantidadPedida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.cantidad_recibida column
	 * for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getArticulosCompradosCantidadRecibida()
			throws DataStoreException {
		return getDouble(ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA);
	}

	/**
	 * Retrieve the value of the articulos_comprados.cantidad_recibida column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getArticulosCompradosCantidadRecibida(int row)
			throws DataStoreException {
		return getDouble(row, ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA);
	}

	/**
	 * Set the value of the articulos_comprados.cantidad_recibida column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosCantidadRecibida(double newValue)
			throws DataStoreException {
		setDouble(ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosCantidadRecibida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.estado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosEstado() throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_ESTADO);
	}

	/**
	 * Retrieve the value of the articulos_comprados.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosEstado(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_ESTADO);
	}

	/**
	 * Set the value of the articulos_comprados.estado column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosEstado(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_ESTADO, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosNombre() throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos_comprados.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosNombre(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_NOMBRE);
	}

	/**
	 * Set the value of the articulos_comprados.nombre column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosNombre(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosDescripcion() throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos_comprados.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosDescripcion(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos_comprados.descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDescripcion(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.descripcion_completa column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosDescripcionCompleta()
			throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Retrieve the value of the articulos_comprados.descripcion_completa column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosDescripcionCompleta(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Set the value of the articulos_comprados.descripcion_completa column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDescripcionCompleta(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.descripcion_completa column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosDescripcionCompleta(int row,
			String newValue) throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.entidad_id_proveedor column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosEntidadIdProveedor()
			throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR);
	}

	/**
	 * Retrieve the value of the articulos_comprados.entidad_id_proveedor column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosEntidadIdProveedor(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR);
	}

	/**
	 * Set the value of the articulos_comprados.entidad_id_proveedor column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosEntidadIdProveedor(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.entidad_id_proveedor column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosEntidadIdProveedor(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.proveedor_nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosProveedorNombre()
			throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos_comprados.proveedor_nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosProveedorNombre(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE);
	}

	/**
	 * Set the value of the articulos_comprados.proveedor_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosProveedorNombre(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.proveedor_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosProveedorNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.fecha column for the
	 * current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getArticulosCompradosFecha()
			throws DataStoreException {
		return getDateTime(ARTICULOS_COMPRADOS_FECHA);
	}

	/**
	 * Retrieve the value of the articulos_comprados.fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getArticulosCompradosFecha(int row)
			throws DataStoreException {
		return getDateTime(row, ARTICULOS_COMPRADOS_FECHA);
	}

	/**
	 * Set the value of the articulos_comprados.fecha column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosFecha(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(ARTICULOS_COMPRADOS_FECHA, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.fecha column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosFecha(int row, java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(row, ARTICULOS_COMPRADOS_FECHA, newValue);
	}


	/**
	 * Retrieve the value of the articulos_comprados.user_id_comprador column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosUserIdComprador()
			throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_USER_ID_COMPRADOR);
	}

	/**
	 * Retrieve the value of the articulos_comprados.user_id_comprador column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosUserIdComprador(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_USER_ID_COMPRADOR);
	}

	/**
	 * Set the value of the articulos_comprados.user_id_comprador column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosUserIdComprador(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.user_id_comprador column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosUserIdComprador(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_USER_ID_COMPRADOR, newValue);
	}
	
	
	/**
	 * Retrieve the value of the articulos_comprados.comprador column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosNombreCompleto()
			throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_COMPRADOR);
	}

	/**
	 * Retrieve the value of the articulos_comprados.comprador column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_COMPRADOR);
	}

	/**
	 * Set the value of the articulos_comprados.comprador column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosNombreCompleto(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.comprador column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_COMPRADOR, newValue);
	}

	/**
	 * Retrieve the value of the articulos_comprados.user_id_solicitante column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosUserIdSolicitante()
			throws DataStoreException {
		return getInt(ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE);
	}

	/**
	 * Retrieve the value of the articulos_comprados.user_id_solicitante column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosCompradosUserIdSolicitante(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE);
	}

	/**
	 * Set the value of the articulos_comprados.user_id_solicitante column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosUserIdSolicitante(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.user_id_solicitante column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosUserIdSolicitante(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_COMPRADOS_USER_ID_SOLICITANTE, newValue);
	}
	
	/**
	 * Retrieve the value of the articulos_comprados.solicitante column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosSolicitanteNombreCompleto()
			throws DataStoreException {
		return getString(ARTICULOS_COMPRADOS_SOLICITANTE);
	}

	/**
	 * Retrieve the value of the articulos_comprados.solicitante column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosCompradosSolicitanteNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_COMPRADOS_SOLICITANTE);
	}

	/**
	 * Set the value of the articulos_comprados.solicitante column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosSolicitanteNombreCompleto(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_COMPRADOS_SOLICITANTE, newValue);
	}

	/**
	 * Set the value of the articulos_comprados.solicitante column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosCompradosSolicitanteNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_COMPRADOS_SOLICITANTE, newValue);
	}
	
	// $CUSTOMMETHODS$

	// $ENDCUSTOMMETHODS$

}
