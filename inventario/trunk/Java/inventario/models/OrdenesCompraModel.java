package inventario.models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.BaseModel;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * OrdenesCompraModel: A SOFIA generated model
 */
public class OrdenesCompraModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8803142152331745657L;
	//constants for columns
	public static final String ORDENES_COMPRA_ORDEN_COMPRA_ID="ordenes_compra.orden_compra_id";
	public static final String ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR="ordenes_compra.entidad_id_proveedor";
	public static final String ORDENES_COMPRA_USER_ID_COMPRADOR="ordenes_compra.user_id_comprador";
	public static final String ORDENES_COMPRA_ESTADO="ordenes_compra.estado";
	public static final String ORDENES_COMPRA_FECHA="ordenes_compra.fecha";
	public static final String ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA="ordenes_compra.fecha_estimada_entrega";
	public static final String ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA="ordenes_compra.fecha_entrega_completa";
	public static final String ORDENES_COMPRA_DESCRIPCION="ordenes_compra.descripcion";
	public static final String ORDENES_COMPRA_OBSERVACIONES="ordenes_compra.observaciones";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
	public static final String ESTADO_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_NOMBRE_COMPRADOR = "nombre_completo_comprador";
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";
	public static final String ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String OBSERVACIONES = "observaciones";
	public static final String TOTAL_ORDENCOMPRA = "total_orden_compra";
	//$ENDCUSTOMVARS$

	/**
	 * Create a new OrdenesCompraModel object.
	 * @param appName The SOFIA application name
	 */
	public OrdenesCompraModel (String appName) { 
		this(appName, null);
	}

	/**
	 * Create a new OrdenesCompraModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public OrdenesCompraModel (String appName, String profile) { 
		super(appName, profile);

		try {
			//add columns
			addColumn(computeTableName("ordenes_compra"),"orden_compra_id",DataStore.DATATYPE_INT,true,true,ORDENES_COMPRA_ORDEN_COMPRA_ID);
			addColumn(computeTableName("ordenes_compra"),"entidad_id_proveedor",DataStore.DATATYPE_INT,false,true,ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR);
			addColumn(computeTableName("ordenes_compra"),"user_id_comprador",DataStore.DATATYPE_INT,false,true,ORDENES_COMPRA_USER_ID_COMPRADOR);
			addColumn(computeTableName("ordenes_compra"),"estado",DataStore.DATATYPE_STRING,false,true,ORDENES_COMPRA_ESTADO);
			addColumn(computeTableName("ordenes_compra"),"fecha",DataStore.DATATYPE_DATE,false,true,ORDENES_COMPRA_FECHA);
			addColumn(computeTableName("ordenes_compra"),"fecha_estimada_entrega",DataStore.DATATYPE_DATE,false,true,ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA);
			addColumn(computeTableName("ordenes_compra"),"fecha_entrega_completa",DataStore.DATATYPE_DATE,false,true,ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA);
			addColumn(computeTableName("ordenes_compra"),"descripcion",DataStore.DATATYPE_STRING,false,true,ORDENES_COMPRA_DESCRIPCION);
			addColumn(computeTableName("ordenes_compra"),"observaciones",DataStore.DATATYPE_STRING,false,true,ORDENES_COMPRA_OBSERVACIONES);

			//set order by
			setOrderBy(computeTableAndFieldName("ordenes_compra.orden_compra_id") + " ASC");

			//add validations
			addRequiredRule(ORDENES_COMPRA_FECHA,"La fecha es obligatoria");
		}
		catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
		try {
			addTableAlias(computeTableName("infraestructura.estados"),"estados");
			addTableAlias(computeTableName("infraestructura.website_user"),"website_user_comprador");

			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, true, ESTADO_NOMBRE);
			addColumn(computeTableName("website_user_comprador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NOMBRE_COMPRADOR);
			
			// add buckets
			addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);
			addBucket(ESQUEMA_CONFIGURACION_ID, DATATYPE_INT);
			addBucket(OBSERVACIONES, DATATYPE_STRING);
			addBucket(TOTAL_ORDENCOMPRA, DATATYPE_FLOAT);

			setAutoIncrement(ORDENES_COMPRA_ORDEN_COMPRA_ID, true);
			setUpdateable(ORDENES_COMPRA_ORDEN_COMPRA_ID, false);

			addJoin(computeTableAndFieldName("ordenes_compra.estado"),
					computeTableAndFieldName("estados.estado"), true);
			addJoin(computeTableAndFieldName("ordenes_compra.user_id_comprador"),
					computeTableAndFieldName("website_user_comprador.user_id"),
					true);		

			addLookupRule(
					ORDENES_COMPRA_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = ' + ordenes_compra.estado",
					"nombre", ESTADO_NOMBRE, "Estado inexistente");
			addLookupRule(
					ORDENES_COMPRA_USER_ID_COMPRADOR,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + ordenes_compra.user_id_comprador",
					"nombre_completo", WEBSITE_USER_NOMBRE_COMPRADOR,
					"Usuario inexistente");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}
		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the ordenes_compra.orden_compra_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraOrdenCompraId() throws DataStoreException {
		return  getInt(ORDENES_COMPRA_ORDEN_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the ordenes_compra.orden_compra_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraOrdenCompraId(int row) throws DataStoreException {
		return  getInt(row,ORDENES_COMPRA_ORDEN_COMPRA_ID);
	}

	/**
	 * Set the value of the ordenes_compra.orden_compra_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraOrdenCompraId(int newValue) throws DataStoreException {
		setInt(ORDENES_COMPRA_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.orden_compra_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraOrdenCompraId(int row,int newValue) throws DataStoreException {
		setInt(row,ORDENES_COMPRA_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.entidad_id_proveedor column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraEntidadIdProveedor() throws DataStoreException {
		return  getInt(ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR);
	}

	/**
	 * Retrieve the value of the ordenes_compra.entidad_id_proveedor column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraEntidadIdProveedor(int row) throws DataStoreException {
		return  getInt(row,ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR);
	}

	/**
	 * Set the value of the ordenes_compra.entidad_id_proveedor column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraEntidadIdProveedor(int newValue) throws DataStoreException {
		setInt(ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.entidad_id_proveedor column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraEntidadIdProveedor(int row,int newValue) throws DataStoreException {
		setInt(row,ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.user_id_comprador column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraUserIdComprador() throws DataStoreException {
		return  getInt(ORDENES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Retrieve the value of the ordenes_compra.user_id_comprador column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraUserIdComprador(int row) throws DataStoreException {
		return  getInt(row,ORDENES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Set the value of the ordenes_compra.user_id_comprador column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraUserIdComprador(int newValue) throws DataStoreException {
		setInt(ORDENES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.user_id_comprador column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraUserIdComprador(int row,int newValue) throws DataStoreException {
		setInt(row,ORDENES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.estado column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraEstado() throws DataStoreException {
		return  getString(ORDENES_COMPRA_ESTADO);
	}

	/**
	 * Retrieve the value of the ordenes_compra.estado column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraEstado(int row) throws DataStoreException {
		return  getString(row,ORDENES_COMPRA_ESTADO);
	}

	/**
	 * Set the value of the ordenes_compra.estado column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraEstado(String newValue) throws DataStoreException {
		setString(ORDENES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.estado column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraEstado(int row,String newValue) throws DataStoreException {
		setString(row,ORDENES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFecha() throws DataStoreException {
		return  getDate(ORDENES_COMPRA_FECHA);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFecha(int row) throws DataStoreException {
		return  getDate(row,ORDENES_COMPRA_FECHA);
	}

	/**
	 * Set the value of the ordenes_compra.fecha column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFecha(java.sql.Date newValue) throws DataStoreException {
		setDate(ORDENES_COMPRA_FECHA, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.fecha column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFecha(int row,java.sql.Date newValue) throws DataStoreException {
		setDate(row,ORDENES_COMPRA_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha_estimada_entrega column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFechaEstimadaEntrega() throws DataStoreException {
		return  getDate(ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha_estimada_entrega column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFechaEstimadaEntrega(int row) throws DataStoreException {
		return  getDate(row,ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_estimada_entrega column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFechaEstimadaEntrega(java.sql.Date newValue) throws DataStoreException {
		setDate(ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_estimada_entrega column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFechaEstimadaEntrega(int row,java.sql.Date newValue) throws DataStoreException {
		setDate(row,ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha_entrega_completa column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFechaEntregaCompleta() throws DataStoreException {
		return  getDate(ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha_entrega_completa column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */ 
	public java.sql.Date getOrdenesCompraFechaEntregaCompleta(int row) throws DataStoreException {
		return  getDate(row,ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_entrega_completa column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFechaEntregaCompleta(java.sql.Date newValue) throws DataStoreException {
		setDate(ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_entrega_completa column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFechaEntregaCompleta(int row,java.sql.Date newValue) throws DataStoreException {
		setDate(row,ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraDescripcion() throws DataStoreException {
		return  getString(ORDENES_COMPRA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the ordenes_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraDescripcion(int row) throws DataStoreException {
		return  getString(row,ORDENES_COMPRA_DESCRIPCION);
	}

	/**
	 * Set the value of the ordenes_compra.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraDescripcion(String newValue) throws DataStoreException {
		setString(ORDENES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraDescripcion(int row,String newValue) throws DataStoreException {
		setString(row,ORDENES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.observaciones column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraObservaciones() throws DataStoreException {
		return  getString(ORDENES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the ordenes_compra.observaciones column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraObservaciones(int row) throws DataStoreException {
		return  getString(row,ORDENES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Set the value of the ordenes_compra.observaciones column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraObservaciones(String newValue) throws DataStoreException {
		setString(ORDENES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.observaciones column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraObservaciones(int row,String newValue) throws DataStoreException {
		setString(row,ORDENES_COMPRA_OBSERVACIONES, newValue);
	}

	@Override
	public String getEstadoActual() throws DataStoreException {
		return getOrdenesCompraEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getOrdenesCompraOrdenCompraId();
	}

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
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
	
	@Override
	public void update() throws DataStoreException, SQLException {
		
		if (getOrdenesCompraEstado() == null)
			setOrdenesCompraEstado("0008.0001");
		
		if (getOrdenesCompraFecha() == null)
			setOrdenesCompraFecha(new Date((Calendar.getInstance()
					.getTimeInMillis())));
		
		super.update();
	}
	
	public float getAtributoTotalOrdenCompra() throws DataStoreException,
			SQLException {

		int ordencompra_id = getOrdenesCompraOrdenCompraId();

		float total = 0;

		DetalleSCModel detalles = new DetalleSCModel("inventario", "inventario");
		detalles.retrieve("detalle_sc.orden_compra_id = " + ordencompra_id);

		for (int row = 0; row < detalles.getRowCount(); row++) {
			detalles.setMontoTotal(row);
			total += detalles.getMontoTotal(row);
		}

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(total),
				"TOTAL_ORDENCOMPRA", ordencompra_id, "TABLA", "ordenes_compra");

		return total;

	}
	
	/**
	 * Retrieve the value of the total_solicitud bucket
	 * 
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getTotalOrdenCompra() throws DataStoreException {
		return getFloat(TOTAL_ORDENCOMPRA);
	}

	/**
	 * Set the value of the total_solicitud bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTotalOrdenCompra(float newValue) throws DataStoreException {
		setFloat(TOTAL_ORDENCOMPRA, newValue);
	}
	// $ENDCUSTOMMETHODS$

}
