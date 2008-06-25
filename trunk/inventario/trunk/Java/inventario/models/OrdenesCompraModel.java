package inventario.models;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.BaseModel;
import infraestructura.reglasNegocio.ValidationException;
import infraestructura.utils.Utilities;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

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
	public static final String ORDENES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String ORDENES_COMPRA_USER_ID_GENERADOR="ordenes_compra.user_id_generador";	
	public static final String ORDENES_COMPRA_CONDICION_COMPRA_ID="ordenes_compra.condicion_compra_id";
	public static final String ORDENES_COMPRA_DESCUENTO="ordenes_compra.descuento";
	
	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
	public static final String ESTADO_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_NOMBRE_COMPRADOR = "nombre_completo_comprador";
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";
	public static final String ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String OBSERVACIONES = "observaciones";
	public static final String TOTAL_ORDENCOMPRA = "total_orden_compra";
	public static final String ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";	
	public static final String WEBSITE_USER_NOMBRE_GENERADOR = "nombre_completo_generador";
	public static final String NETO_ORDENCOMPRA = "neto_orden_compra";
	public static final String IVA_ORDENCOMPRA = "iva_orden_compra";
	public static final String DESCUENTO_ORDENCOMPRA = "descuento_orden_compra";
	public static final String CONDICION_COMPRA_DESCRIPCION = "condiciones_compra.descripcion";
	public static final String CONDICION_COMPRA_NOMBRE = "condiciones_compra.nombre";
	
	private Set<String> estadosDeModificacion;
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
			addColumn(computeTableName("ordenes_compra"),"fecha",DataStore.DATATYPE_DATETIME,false,true,ORDENES_COMPRA_FECHA);
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
			addTableAlias(computeTableName("infraestructura.entidad_externa"),"entidad_externa");
			addTableAlias(computeTableName("infraestructura.website_user"),"website_user_generador");
			addTableAlias(computeTableName("inventario.condiciones_compra"),"condiciones_compra");

			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, true, ESTADO_NOMBRE);
			addColumn(computeTableName("website_user_comprador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NOMBRE_COMPRADOR);
			addColumn(computeTableName("entidad_externa"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO);
			addColumn(computeTableName("entidad_externa"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE);
			addColumn(computeTableName("ordenes_compra"),
					"fecha_aprobacion", DataStore.DATATYPE_DATETIME, false, true,
					ORDENES_COMPRA_FECHA_APROBACION);
			addColumn(computeTableName("ordenes_compra"),"user_id_generador",
					DataStore.DATATYPE_INT,false,true,
					ORDENES_COMPRA_USER_ID_GENERADOR);
			addColumn(computeTableName("website_user_generador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, false,
					WEBSITE_USER_NOMBRE_GENERADOR);
			addColumn(computeTableName("ordenes_compra"),"condicion_compra_id",
					DataStore.DATATYPE_INT,false,true,
					ORDENES_COMPRA_CONDICION_COMPRA_ID);
			addColumn(computeTableName("condiciones_compra"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					CONDICION_COMPRA_DESCRIPCION);
			addColumn(computeTableName("condiciones_compra"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CONDICION_COMPRA_NOMBRE);
			addColumn(computeTableName("ordenes_compra"), "descuento",
					DataStore.DATATYPE_FLOAT, false, true,
					ORDENES_COMPRA_DESCUENTO);
			
			// add buckets
			addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);
			addBucket(ESQUEMA_CONFIGURACION_ID, DATATYPE_INT);
			addBucket(OBSERVACIONES, DATATYPE_STRING);
			addBucket(TOTAL_ORDENCOMPRA, DATATYPE_FLOAT);
			addBucket(NETO_ORDENCOMPRA, DATATYPE_FLOAT);
			addBucket(IVA_ORDENCOMPRA, DATATYPE_FLOAT);
			addBucket(DESCUENTO_ORDENCOMPRA, DATATYPE_FLOAT);

			setAutoIncrement(ORDENES_COMPRA_ORDEN_COMPRA_ID, true);
			setUpdateable(ORDENES_COMPRA_ORDEN_COMPRA_ID, false);

			addJoin(computeTableAndFieldName("ordenes_compra.estado"),
					computeTableAndFieldName("estados.estado"), 
					true);
			addJoin(computeTableAndFieldName("ordenes_compra.user_id_comprador"),
					computeTableAndFieldName("website_user_comprador.user_id"),
					true);	
			addJoin(computeTableAndFieldName("ordenes_compra.entidad_id_proveedor"),
					computeTableAndFieldName("entidad_externa.entidad_id"),
					true);
			addJoin(computeTableAndFieldName("ordenes_compra.user_id_generador"),
					computeTableAndFieldName("website_user_generador.user_id"),
					true);
			addJoin(computeTableAndFieldName("ordenes_compra.condicion_compra_id"),
					computeTableAndFieldName("condiciones_compra.condicion_compra_id"),
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
			addLookupRule(
					ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + ordenes_compra.entidad_id_proveedor",
					"nombre", ENTIDAD_EXTERNA_NOMBRE,
					"Proveedor inexistente");
			addLookupRule(
					ORDENES_COMPRA_USER_ID_GENERADOR,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + ordenes_compra.user_id_generador",
					"nombre_completo", WEBSITE_USER_NOMBRE_GENERADOR,
					"Usuario inexistente");
			addLookupRule(
					ORDENES_COMPRA_CONDICION_COMPRA_ID,
					"inventario.condiciones_compra",
					"'inventario.condiciones_compra.condicion_compra_id = ' + ordenes_compra.condicion_compra_id",
					"nombre", CONDICION_COMPRA_NOMBRE,
					"Condición de compra inexistente");
			
			// Obtiene los posibles estados de modificacion para este modelo
			estadosDeModificacion = getEstadosDeModificacion();
			
			// Formato de los campos de fecha
			setFormat(ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA, "dd/MM/yyyy");

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
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */ 
	public java.sql.Timestamp getOrdenesCompraFecha() throws DataStoreException {
		return  getDateTime(ORDENES_COMPRA_FECHA);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */ 
	public java.sql.Timestamp getOrdenesCompraFecha(int row) throws DataStoreException {
		return  getDateTime(row,ORDENES_COMPRA_FECHA);
	}

	/**
	 * Set the value of the ordenes_compra.fecha column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFecha(java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(ORDENES_COMPRA_FECHA, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.fecha column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraFecha(int row,java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row,ORDENES_COMPRA_FECHA, newValue);
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
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadoNombre(int row) throws DataStoreException {
		return getString(row, ESTADO_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEstadoNombre(String newValue) throws DataStoreException {
		setString(ESTADO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the estados.nombre column for the specified row.
	 * 
	 * @param row which row in the table
	 * @param newValue the new item value
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
	 * @param row which row in the table
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
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreComprador(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the website_user.nombre column for the specified row.
	 * 
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreComprador(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_COMPRADOR, newValue);
	}	
	
	/**
	 * Retrieve the value of the current website user id bucket
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
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCurrentWebsiteUserId(int newValue) throws DataStoreException {
		setInt(CURRENT_WEBSITE_USER_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the current website user id bucket
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
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEsquemaConfiguracionId(int newValue)
			throws DataStoreException {
		setInt(ESQUEMA_CONFIGURACION_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the observaciones bucket
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
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setObservaciones(String newValue) throws DataStoreException {
		setString(OBSERVACIONES, newValue);
	}
		
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		
		if (getOrdenesCompraEstado() == null)
			setOrdenesCompraEstado("0008.0001");
		
		if (getOrdenesCompraFecha() == null)
			setOrdenesCompraFecha(new Timestamp((Calendar.getInstance()
					.getTimeInMillis())));
		
		if (getOrdenesCompraUserIdComprador() == 0) {
			setOrdenesCompraUserIdComprador(getCurrentWebsiteUserId());
		}
		
		if (getOrdenesCompraUserIdGenerador() == 0) {
			setOrdenesCompraUserIdGenerador(getCurrentWebsiteUserId());
		}
		
		CondicionesCompraModel condicionesCompraModel;
		// completa condicion_compra_id segun el valor de condicion_compra_nombre
		if (getOrdenesCompraCondicionNombre() != null) {
			condicionesCompraModel = new CondicionesCompraModel("inventario", "inventario");
			condicionesCompraModel.retrieve(conn, "condiciones_compra.nombre LIKE '"
					+ getOrdenesCompraCondicionNombre() + "'");
			if (!condicionesCompraModel.gotoFirst()) {
				throw new DataStoreException(
						"La condición de compra ingresada no corresponde a ninguna registrada");
			}
			setOrdenesCompraCondicionCompraId(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
		}
		
		/*
		 * Normalizamos a cero horas, minutos y segundos para la comparacion
		 */
		if (getOrdenesCompraFechaEstimadaEntrega() != null) {			
			Calendar fechaActual = Calendar.getInstance();			
			fechaActual.set(Calendar.HOUR_OF_DAY, fechaActual.getMinimum(Calendar.HOUR_OF_DAY));
			fechaActual.set(Calendar.MINUTE, fechaActual.getMinimum(Calendar.MINUTE));
			fechaActual.set(Calendar.SECOND, fechaActual.getMinimum(Calendar.SECOND));
			fechaActual.set(Calendar.MILLISECOND, fechaActual.getMinimum(Calendar.MILLISECOND));
			
			Calendar fechaEstEntrega = Calendar.getInstance();
			fechaEstEntrega.setTimeInMillis(getOrdenesCompraFechaEstimadaEntrega().getTime());
			fechaEstEntrega.set(Calendar.HOUR_OF_DAY, fechaActual.getMinimum(Calendar.HOUR_OF_DAY));
			fechaEstEntrega.set(Calendar.MINUTE, fechaActual.getMinimum(Calendar.MINUTE));
			fechaEstEntrega.set(Calendar.SECOND, fechaActual.getMinimum(Calendar.SECOND));
			fechaEstEntrega.set(Calendar.MILLISECOND, fechaActual.getMinimum(Calendar.MILLISECOND));
			
			if (fechaEstEntrega.before(fechaActual))
				throw new DataStoreException("La fecha estimada de entrega debe ser igual o posterior a la fecha actual");
		}
		
		super.update(conn, handleTrans);
	}
	
	/**
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaAtributoTotalOrdenCompra() throws DataStoreException,
			SQLException, ParseException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();

		float total = 0;
		
		//total = getNetoOrdenCompra() - getAtributoDescuentoOrdenCompra() + getIvaOrdenCompra(); 
		total = getAtributoNetoOrdenCompra() - getAtributoDescuentoOrdenCompra() + getAtributoIvaOrdenCompra();

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(total),
				Constants.TOTAL_OC, ordencompra_id, "TABLA", "ordenes_compra");
	}
	
	/**
	 * Retorna el monto total de esta orden de compra.
	 * El monto total esta dado por el resultado de: NETO - DESCUENTO + IVA
	 * @return el monto total de esta orden de compra
	 * @throws NumberFormatException
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public float getAtributoTotalOrdenCompra() throws NumberFormatException,
			DataStoreException, SQLException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();
		return Float
				.parseFloat(AtributosEntidadModel.getValorAtributoObjeto(
						Constants.TOTAL_OC, ordencompra_id, "TABLA",
						"ordenes_compra"));
	}
	
	/**
	 * Retrieve the value of the total_solicitud bucket
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
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setTotalOrdenCompra(float newValue) throws DataStoreException {
		setFloat(TOTAL_ORDENCOMPRA, newValue);
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
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaCodigo(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_CODIGO);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaCodigo(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_CODIGO, newValue);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the specified row.
	 * 
	 * @param row which row in the table
	 * @param newValue the new item value
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
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaNombre(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_NOMBRE);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the specified row.
	 * 
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ENTIDAD_EXTERNA_NOMBRE, newValue);
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.fecha_aprobacion column for
	 * the current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getOrdenesCompraFechaAprobacion()
			throws DataStoreException {
		return getDateTime(ORDENES_COMPRA_FECHA_APROBACION);
	}

	/**
	 * Retrieve the value of the ordenes_compra.fecha_aprobacion column for
	 * the specified row.
	 * 
	 * @param row which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getOrdenesCompraFechaAprobacion(int row)
			throws DataStoreException {
		return getDateTime(row, ORDENES_COMPRA_FECHA_APROBACION);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_aprobacion column for the
	 * current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraFechaAprobacion(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(ORDENES_COMPRA_FECHA_APROBACION, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.fecha_aprobacion column for the
	 * specified row.
	 * 
	 * @param row  which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraFechaAprobacion(int row,
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, ORDENES_COMPRA_FECHA_APROBACION, newValue);
	}
	
	/**
	 * Recupera observaciones hechas a la OC actual en el bucket observaciones  
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void recuperaObservaciones() throws DataStoreException, SQLException {
		InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
				"inventario", "inventario");		
		instancia.retrieve(
				"nombre_objeto = 'ordenes_compra' AND " +
				"objeto_id = " + getOrdenesCompraOrdenCompraId() + 
				" AND mensaje IS NOT NULL");
		if (instancia.gotoFirst())
			setObservaciones(instancia.getInstanciasAprobacionMensaje());
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.user_id_generador column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraUserIdGenerador() throws DataStoreException {
		return  getInt(ORDENES_COMPRA_USER_ID_GENERADOR);
	}

	/**
	 * Retrieve the value of the ordenes_compra.user_id_generador column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraUserIdGenerador(int row) throws DataStoreException {
		return  getInt(row,ORDENES_COMPRA_USER_ID_GENERADOR);
	}

	/**
	 * Set the value of the ordenes_compra.user_id_generador column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraUserIdGenerador(int newValue) throws DataStoreException {
		setInt(ORDENES_COMPRA_USER_ID_GENERADOR, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.user_id_generador column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraUserIdGenerador(int row,int newValue) throws DataStoreException {
		setInt(row,ORDENES_COMPRA_USER_ID_GENERADOR, newValue);
	}
	
	/**
	 * Retrieve the value of the website_user.nombre column for the current row. 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreGenerador() throws DataStoreException {
		return getString(WEBSITE_USER_NOMBRE_GENERADOR);
	}

	/**
	 * Retrieve the value of the website_user.nombre column for the specified row.
	 * 
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreGenerador(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_NOMBRE_GENERADOR);
	}

	/**
	 * Set the value of the website_user.nombre column for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreGenerador(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_GENERADOR, newValue);
	}

	/**
	 * Set the value of the website_user.nombre column for the specified row.
	 * 
	 * @param row  which row in the table
	 * @param newValue  the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreGenerador(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_GENERADOR, newValue);
	}	
	
	/**
	 * Retrieve the value of the neto_orden_compra bucket
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getNetoOrdenCompra() throws DataStoreException {
		return getFloat(NETO_ORDENCOMPRA);
	}

	/**
	 * Set the value of the neto_orden_compra bucket for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setNetoOrdenCompra(float newValue) throws DataStoreException {
		setFloat(NETO_ORDENCOMPRA, newValue);
	}
	
	/**
	 * Retrieve the value of the iva_orden_compra bucket
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getIvaOrdenCompra() throws DataStoreException {
		return getFloat(IVA_ORDENCOMPRA);
	}

	/**
	 * Set the value of the iva_orden_compra bucket for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setIvaOrdenCompra(float newValue) throws DataStoreException {
		setFloat(IVA_ORDENCOMPRA, newValue);
	}
	
	/**
	 * Retrieve the value of the descuento_orden_compra bucket
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDescuentoOrdenCompra() throws DataStoreException {
		return getFloat(DESCUENTO_ORDENCOMPRA);
	}

	/**
	 * Set the value of the descuento_orden_compra bucket for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setDescuentoOrdenCompra(float newValue) throws DataStoreException {
		setFloat(DESCUENTO_ORDENCOMPRA, newValue);
	}
	
	/**
	 * Calcula el valor neto de la orden de compra. 
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaAtributoNetoOrdenCompra() throws DataStoreException,
			SQLException, ParseException {
		calculaAtributoNetoOrdenCompra(null);
	}
	
	/**
	 * Calcula el valor neto de la orden de compra. 
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaAtributoNetoOrdenCompra(DetalleSCModel detalles) throws DataStoreException,
			SQLException, ParseException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();

		float neto = 0;
		
		if (detalles == null) {
			detalles = new DetalleSCModel("inventario", "inventario");
			detalles.retrieve("detalle_sc.orden_compra_id = " + ordencompra_id);
		}
		
		for (int row = 0; row < detalles.getRowCount(); row++) {
			detalles.calculaMontoTotalNetoPedido(row);
			neto += detalles.getMontoTotalNetoPedido(row);
		}	

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(neto),
				Constants.NETO_OC, ordencompra_id, "TABLA", "ordenes_compra");
	}
	
	/**
	 * Retorna el valor neto de la orden de compra.
	 * @return el valor neto de la orden de compra
	 * @throws NumberFormatException
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public float getAtributoNetoOrdenCompra() throws NumberFormatException,
			DataStoreException, SQLException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();
		return Float.parseFloat(AtributosEntidadModel.getValorAtributoObjeto(
				Constants.NETO_OC, ordencompra_id, "TABLA", "ordenes_compra"));
	}
	
	/**
	 * Calcula el total de iva de la orden de compra.
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaAtributoIvaOrdenCompra() throws DataStoreException,
			SQLException, ParseException {
		calculaAtributoIvaOrdenCompra(null);
	}
	
	/**
	 * Calcula el total de iva de la orden de compra.
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaAtributoIvaOrdenCompra(DetalleSCModel detalles) throws DataStoreException,
			SQLException, ParseException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();

		float iva = 0;
		
		if (detalles == null) {
			detalles = new DetalleSCModel("inventario", "inventario");
			detalles.retrieve("detalle_sc.orden_compra_id = " + ordencompra_id);
		}

		for (int row = 0; row < detalles.getRowCount(); row++) {
			detalles.calculaMontoTotalPedido(row, 
					getOrdenesCompraDescuento() > 0 ? getOrdenesCompraDescuento() : null);
			float montoTotal = detalles.getMontoTotalPedido(row);
			float ivaArticulo = detalles.getDetalleScIva(row) / 100.0F;
			
			iva += Float.parseFloat(Utilities.getDecimalFormatNumber(montoTotal * ivaArticulo));
		}

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(iva),
				Constants.IVA_OC, ordencompra_id, "TABLA", "ordenes_compra");		
	}
	
	/**
	 * Retorna el monto total de IVA de la orden de compra
	 * @return el monto total de IVA de la orden de compra
	 * @throws NumberFormatException
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public float getAtributoIvaOrdenCompra() throws NumberFormatException,
			DataStoreException, SQLException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();
		return Float.parseFloat(AtributosEntidadModel.getValorAtributoObjeto(
				Constants.IVA_OC, ordencompra_id, "TABLA", "ordenes_compra"));
	}	
	
	/**
	 * Calcula el total de descuento de la orden de compra
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaAtributoDescuentoOrdenCompra() throws DataStoreException,
			SQLException, ParseException {
		calculaAtributoDescuentoOrdenCompra(null);
	}
	
	/**
	 * Calcula el total de descuento de la orden de compra
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaAtributoDescuentoOrdenCompra(DetalleSCModel detalles) throws DataStoreException,
			SQLException, ParseException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();

		float totalDescuento = 0;
		float descuento = 0;
		
		if (detalles == null) {
			detalles = new DetalleSCModel("inventario", "inventario");
			detalles.retrieve("detalle_sc.orden_compra_id = " + ordencompra_id);
		}

		for (int row = 0; row < detalles.getRowCount(); row++) {
			/*descuento = getOrdenesCompraDescuento() > 0 ?
				getOrdenesCompraDescuento() / 100 :	
				detalles.getDetalleScDescuento(row) / 100				 
				;*/
			descuento = (getOrdenesCompraDescuento() / 100) +
						(detalles.getDetalleScDescuento(row) / 100);
			
			detalles.calculaMontoTotalNetoPedido(row);		
			
			totalDescuento += Float.parseFloat(Utilities
					.getDecimalFormatNumber(detalles
							.getMontoTotalNetoPedido(row)
							* descuento));
		}

		AtributosEntidadModel.setValorAtributoObjeto(String.valueOf(totalDescuento),
				Constants.DESCUENTO_OC, ordencompra_id, "TABLA", "ordenes_compra");
	}	
	
	/**
	 * Retorna el descuento total de esta orden de compra
	 * @return el descuento total de esta orden de compra
	 * @throws NumberFormatException
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public float getAtributoDescuentoOrdenCompra()
			throws NumberFormatException, DataStoreException, SQLException {
		int ordencompra_id = getOrdenesCompraOrdenCompraId();
		return Float.parseFloat(AtributosEntidadModel.getValorAtributoObjeto(
				Constants.DESCUENTO_OC, ordencompra_id, "TABLA",
				"ordenes_compra"));
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.condicion_compra_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraCondicionCompraId() throws DataStoreException {
		return  getInt(ORDENES_COMPRA_CONDICION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the ordenes_compra.condicion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getOrdenesCompraCondicionCompraId(int row) throws DataStoreException {
		return  getInt(row,ORDENES_COMPRA_CONDICION_COMPRA_ID);
	}

	/**
	 * Set the value of the ordenes_compra.condicion_compra_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionCompraId(int newValue) throws DataStoreException {
		setInt(ORDENES_COMPRA_CONDICION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.condicion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionCompraId(int row,int newValue) throws DataStoreException {
		setInt(row,ORDENES_COMPRA_CONDICION_COMPRA_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraCondicionNombre() throws DataStoreException {
		return  getString(CONDICION_COMPRA_NOMBRE);
	}

	/**
	 * Retrieve the value of the ordenes_compra.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraCondicionNombre(int row) throws DataStoreException {
		return  getString(row,CONDICION_COMPRA_NOMBRE);
	}

	/**
	 * Set the value of the ordenes_compra.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionNombre(String newValue) throws DataStoreException {
		setString(CONDICION_COMPRA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionNombre(int row,String newValue) throws DataStoreException {
		setString(row,CONDICION_COMPRA_NOMBRE, newValue);
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraCondicionDescripcion() throws DataStoreException {
		return  getString(CONDICION_COMPRA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the ordenes_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getOrdenesCompraCondicionDescripcion(int row) throws DataStoreException {
		return  getString(row,CONDICION_COMPRA_DESCRIPCION);
	}

	/**
	 * Set the value of the ordenes_compra.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionDescripcion(String newValue) throws DataStoreException {
		setString(CONDICION_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraCondicionDescripcion(int row,String newValue) throws DataStoreException {
		setString(row,CONDICION_COMPRA_DESCRIPCION, newValue);
	}
	
	/**
	 * Retrieve the value of the ordenes_compra.descuento column for the current row.
	 * @return float
	 * @throws DataStoreException
	 */ 
	public float getOrdenesCompraDescuento() throws DataStoreException {
		return  getFloat(ORDENES_COMPRA_DESCUENTO);
	}

	/**
	 * Retrieve the value of the ordenes_compra.descuento column for the specified row.
	 * @param row which row in the table
	 * @return float
	 * @throws DataStoreException
	 */ 
	public float getOrdenesCompraDescuento(int row) throws DataStoreException {
		return  getFloat(row,ORDENES_COMPRA_DESCUENTO);
	}

	/**
	 * Set the value of the ordenes_compra.descuento column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraDescuento(float newValue) throws DataStoreException {
		setFloat(ORDENES_COMPRA_DESCUENTO, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.descuento column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setOrdenesCompraDescuento(int row,float newValue) throws DataStoreException {
		setFloat(row,ORDENES_COMPRA_DESCUENTO, newValue);
	}
	
	/**
	 * 
	 */
	public void calculaTotales() {
		
	}	
	
	/**
	 * Obtiene los posibles estados en los cuales una OC es modificable
	 * @return set con los estados susceptibles de modificacion
	 */
	private Set<String> getEstadosDeModificacion() {
		Props p = Props.getProps(getAppName(), null);
		estadosDeModificacion = new HashSet<String>();
		// modificado por demian barry el 28/05/2008 agregando código protectivo
		// por si la propiedad NO está definida
		String[] estados = null;
		if (p.getProperty(Constants.ESTADOS_DE_MODIFICACION_ORDENES_COMPRA) != null) {
			estados = p.getProperty(Constants.ESTADOS_DE_MODIFICACION_ORDENES_COMPRA).split(",");
			for (String e : estados) {
				estadosDeModificacion.add(e);
			}
		}

		return estadosDeModificacion;
	}
	
	/**
	 * Chequea si la OC se encuentra en un estado apto para modificacion
	 * @return true si la OC es modificable, false en caso contrario
	 * @throws DataStoreException
	 */
	public boolean isModificable() throws DataStoreException {
		String estadoActual = getOrdenesCompraEstado();
		return estadoActual == null ? true : estadosDeModificacion.contains(estadoActual);
	}
	
	/**
	 * Verifica si la orden de compra actual se encuentra replicada en Tango
	 * @return true si la orden de compra se encuentra replicada en Tango
	 * @throws SQLException 
	 * @throws DataStoreException 
	 */
	public boolean isReplicadoEnTango() throws DataStoreException, SQLException {
		return isReplicadoEnTango(getRow());
	}
	
	/**
	 * Verifica si la orden de compra se encuentra replicada en Tango
	 * @param row el registro a verificar
	 * @return true si la orden de compra se encuentra replicada en Tango
	 * @throws SQLException 
	 * @throws DataStoreException 
	 */
	public boolean isReplicadoEnTango(int row) throws DataStoreException, SQLException {
		String nroOcTango = getNroOrdenCompraTango(row);		
		if (nroOcTango == null || "0".equalsIgnoreCase(nroOcTango)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Retorna el Nro. de Orden de Compra generado por Tango para la OC si lo hubiera
	 * @return el nro. de orden generado por tango o null si no existe
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public String getNroOrdenCompraTango() throws DataStoreException, SQLException {
		return getNroOrdenCompraTango(getRow());
	}
	
	/**
	 * Retorna el Nro. de Orden de Compra generado por Tango para la OC si lo hubiera
	 * @param row el registro para el cual se quiere obtener el nro. de orden de compra
	 * @return el nro. de orden generado por tango o null si no existe
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public String getNroOrdenCompraTango(int row) throws DataStoreException, SQLException {
		// Vector de errores para excepción
		Vector<String> errores = new Vector<String>();
		/*
		 * Buscamos en el archivo System.properties el id de la propiedad
		 * N_ORDEN_CO para recuperar el nro. de orden de compra generado por Tango
		 */
		Props props = Props.getProps("inventario", null);							
		int N_ORDEN_CO_PROP = props.getIntProperty("N_ORDEN_CO_PROP");
		if (N_ORDEN_CO_PROP == -1) {
			errores.add("OrdenesCompraModel.getNroOrdenCompraTango(): No se ha indicado el atributo N_ORDEN_CO_PROP en archivo de configuración.");
			throw new ValidationException(errores);
		}
		// Obtenemos el atributo correspondiente
		return AtributosEntidadModel.getValorAtributoObjeto(N_ORDEN_CO_PROP,
				getOrdenesCompraOrdenCompraId(row), "TABLA", "ordenes_compra");

	}
	// $ENDCUSTOMMETHODS$

}
