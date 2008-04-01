package inventario.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * RecepcionesComprasModel: A SOFIA generated model
 */
public class RecepcionesComprasModel extends DataStore {

	//constants for columns
	public static final String RECEPCIONES_RECEPCION_COMPRA_ID = "recepciones.recepcion_compra_id";
	public static final String RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID = "recepciones.comprobante_movimiento_id";
	public static final String RECEPCIONES_FECHA = "recepciones.fecha";
	public static final String RECEPCIONES_CANTIDAD_RECIBIDA = "recepciones.cantidad_recibida";
	public static final String COMPROBANTE_ALMACEN_ID = "comprobante.almacen_id";
	public static final String COMPROBANTE_USER_ID_RETIRA = "comprobante.user_id_retira";
	public static final String COMPROBANTE_USER_ID_PREPARADOR = "comprobante.user_id_preparador";
	public static final String COMPROBANTE_ESTADO = "comprobante.estado";
	public static final String COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID = "comprobante.tipo_movimiento_articulo_id";
	public static final String COMPROBANTE_FECHA = "comprobante.fecha";
	public static final String ALMACENES_NOMBRE = "almacenes.nombre";
	public static final String ALMACENES_DESCRIPCION = "almacenes.descripcion";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMVARS$

	/**
	 * Create a new RecepcionesComprasModel object.
	 * @param appName The SOFIA application name
	 */
	public RecepcionesComprasModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new RecepcionesComprasModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public RecepcionesComprasModel(String appName, String profile) {
		super(appName, profile);

		try {

			//add aliases
			addTableAlias(
				computeTableName("recepciones_compras"),
				"recepciones");
			addTableAlias(
				computeTableName("comprobante_movimiento_articulo"),
				"comprobante");
			addTableAlias(computeTableName("almacenes"), "almacenes");

			//add columns
			addColumn(
				computeTableName("recepciones"),
				"recepcion_compra_id",
				DataStore.DATATYPE_INT,
				true,
				true,
				RECEPCIONES_RECEPCION_COMPRA_ID);
			addColumn(
				computeTableName("recepciones"),
				"comprobante_movimiento_id",
				DataStore.DATATYPE_INT,
				false,
				true,
				RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID);
			addColumn(
				computeTableName("recepciones"),
				"fecha",
				DataStore.DATATYPE_DATE,
				false,
				true,
				RECEPCIONES_FECHA);
			addColumn(
				computeTableName("recepciones"),
				"cantidad_recibida",
				DataStore.DATATYPE_DOUBLE,
				false,
				true,
				RECEPCIONES_CANTIDAD_RECIBIDA);
			addColumn(
				computeTableName("comprobante"),
				"almacen_id",
				DataStore.DATATYPE_INT,
				false,
				true,
				COMPROBANTE_ALMACEN_ID);
			addColumn(
				computeTableName("comprobante"),
				"user_id_retira",
				DataStore.DATATYPE_INT,
				false,
				true,
				COMPROBANTE_USER_ID_RETIRA);
			addColumn(
				computeTableName("comprobante"),
				"user_id_preparador",
				DataStore.DATATYPE_INT,
				false,
				true,
				COMPROBANTE_USER_ID_PREPARADOR);
			addColumn(
				computeTableName("comprobante"),
				"estado",
				DataStore.DATATYPE_STRING,
				false,
				true,
				COMPROBANTE_ESTADO);
			addColumn(
				computeTableName("comprobante"),
				"tipo_movimiento_articulo_id",
				DataStore.DATATYPE_INT,
				false,
				true,
				COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID);
			addColumn(
				computeTableName("comprobante"),
				"fecha",
				DataStore.DATATYPE_DATE,
				false,
				true,
				COMPROBANTE_FECHA);
			addColumn(
				computeTableName("almacenes"),
				"nombre",
				DataStore.DATATYPE_STRING,
				false,
				true,
				ALMACENES_NOMBRE);
			addColumn(
				computeTableName("almacenes"),
				"descripcion",
				DataStore.DATATYPE_STRING,
				false,
				true,
				ALMACENES_DESCRIPCION);

			//add joins
			addJoin(
				computeTableAndFieldName("recepciones.comprobante_movimiento_id")
					+ ","
					+ computeTableAndFieldName("comprobante.almacen_id"),
				computeTableAndFieldName("comprobante.comprobante_movimiento_id")
					+ ","
					+ computeTableAndFieldName("almacenes.almacen_id"),
				false);

			//add validations
			addRequiredRule(
				RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID,
				"Seleccione un comprobante asociado a esta recepción");
			addRequiredRule(RECEPCIONES_FECHA, "Indique una fecha de recepción");
			addRequiredRule(
				RECEPCIONES_CANTIDAD_RECIBIDA,
				"Indique la cantidad recibida");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the recepciones.recepcion_compra_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesRecepcionCompraId() throws DataStoreException {
		return getInt(RECEPCIONES_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the recepciones.recepcion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesRecepcionCompraId(int row)
		throws DataStoreException {
		return getInt(row, RECEPCIONES_RECEPCION_COMPRA_ID);
	}

	/**
	 * Set the value of the recepciones.recepcion_compra_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesRecepcionCompraId(int newValue)
		throws DataStoreException {
		setInt(RECEPCIONES_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the recepciones.recepcion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesRecepcionCompraId(int row, int newValue)
		throws DataStoreException {
		setInt(row, RECEPCIONES_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the recepciones.comprobante_movimiento_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprobanteMovimientoId()
		throws DataStoreException {
		return getInt(RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Retrieve the value of the recepciones.comprobante_movimiento_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRecepcionesComprobanteMovimientoId(int row)
		throws DataStoreException {
		return getInt(row, RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Set the value of the recepciones.comprobante_movimiento_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprobanteMovimientoId(int newValue)
		throws DataStoreException {
		setInt(RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID, newValue);
	}

	/**
	 * Set the value of the recepciones.comprobante_movimiento_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesComprobanteMovimientoId(int row, int newValue)
		throws DataStoreException {
		setInt(row, RECEPCIONES_COMPROBANTE_MOVIMIENTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the recepciones.fecha column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRecepcionesFecha() throws DataStoreException {
		return getDate(RECEPCIONES_FECHA);
	}

	/**
	 * Retrieve the value of the recepciones.fecha column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRecepcionesFecha(int row) throws DataStoreException {
		return getDate(row, RECEPCIONES_FECHA);
	}

	/**
	 * Set the value of the recepciones.fecha column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesFecha(java.sql.Date newValue)
		throws DataStoreException {
		setDate(RECEPCIONES_FECHA, newValue);
	}

	/**
	 * Set the value of the recepciones.fecha column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesFecha(int row, java.sql.Date newValue)
		throws DataStoreException {
		setDate(row, RECEPCIONES_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the recepciones.cantidad_recibida column for the current row.
	 * @return double
	 * @throws DataStoreException
	 */
	public double getRecepcionesCantidadRecibida() throws DataStoreException {
		return getDouble(RECEPCIONES_CANTIDAD_RECIBIDA);
	}

	/**
	 * Retrieve the value of the recepciones.cantidad_recibida column for the specified row.
	 * @param row which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getRecepcionesCantidadRecibida(int row)
		throws DataStoreException {
		return getDouble(row, RECEPCIONES_CANTIDAD_RECIBIDA);
	}

	/**
	 * Set the value of the recepciones.cantidad_recibida column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesCantidadRecibida(double newValue)
		throws DataStoreException {
		setDouble(RECEPCIONES_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Set the value of the recepciones.cantidad_recibida column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setRecepcionesCantidadRecibida(int row, double newValue)
		throws DataStoreException {
		setDouble(row, RECEPCIONES_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.almacen_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteAlmacenId() throws DataStoreException {
		return getInt(COMPROBANTE_ALMACEN_ID);
	}

	/**
	 * Retrieve the value of the comprobante.almacen_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteAlmacenId(int row) throws DataStoreException {
		return getInt(row, COMPROBANTE_ALMACEN_ID);
	}

	/**
	 * Set the value of the comprobante.almacen_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteAlmacenId(int newValue) throws DataStoreException {
		setInt(COMPROBANTE_ALMACEN_ID, newValue);
	}

	/**
	 * Set the value of the comprobante.almacen_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteAlmacenId(int row, int newValue)
		throws DataStoreException {
		setInt(row, COMPROBANTE_ALMACEN_ID, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.user_id_retira column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteUserIdRetira() throws DataStoreException {
		return getInt(COMPROBANTE_USER_ID_RETIRA);
	}

	/**
	 * Retrieve the value of the comprobante.user_id_retira column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteUserIdRetira(int row) throws DataStoreException {
		return getInt(row, COMPROBANTE_USER_ID_RETIRA);
	}

	/**
	 * Set the value of the comprobante.user_id_retira column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteUserIdRetira(int newValue)
		throws DataStoreException {
		setInt(COMPROBANTE_USER_ID_RETIRA, newValue);
	}

	/**
	 * Set the value of the comprobante.user_id_retira column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteUserIdRetira(int row, int newValue)
		throws DataStoreException {
		setInt(row, COMPROBANTE_USER_ID_RETIRA, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.user_id_preparador column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteUserIdPreparador() throws DataStoreException {
		return getInt(COMPROBANTE_USER_ID_PREPARADOR);
	}

	/**
	 * Retrieve the value of the comprobante.user_id_preparador column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteUserIdPreparador(int row)
		throws DataStoreException {
		return getInt(row, COMPROBANTE_USER_ID_PREPARADOR);
	}

	/**
	 * Set the value of the comprobante.user_id_preparador column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteUserIdPreparador(int newValue)
		throws DataStoreException {
		setInt(COMPROBANTE_USER_ID_PREPARADOR, newValue);
	}

	/**
	 * Set the value of the comprobante.user_id_preparador column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteUserIdPreparador(int row, int newValue)
		throws DataStoreException {
		setInt(row, COMPROBANTE_USER_ID_PREPARADOR, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.estado column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteEstado() throws DataStoreException {
		return getString(COMPROBANTE_ESTADO);
	}

	/**
	 * Retrieve the value of the comprobante.estado column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteEstado(int row) throws DataStoreException {
		return getString(row, COMPROBANTE_ESTADO);
	}

	/**
	 * Set the value of the comprobante.estado column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteEstado(String newValue) throws DataStoreException {
		setString(COMPROBANTE_ESTADO, newValue);
	}

	/**
	 * Set the value of the comprobante.estado column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteEstado(int row, String newValue)
		throws DataStoreException {
		setString(row, COMPROBANTE_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.tipo_movimiento_articulo_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteTipoMovimientoArticuloId()
		throws DataStoreException {
		return getInt(COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the comprobante.tipo_movimiento_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteTipoMovimientoArticuloId(int row)
		throws DataStoreException {
		return getInt(row, COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Set the value of the comprobante.tipo_movimiento_articulo_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteTipoMovimientoArticuloId(int newValue)
		throws DataStoreException {
		setInt(COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the comprobante.tipo_movimiento_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteTipoMovimientoArticuloId(int row, int newValue)
		throws DataStoreException {
		setInt(row, COMPROBANTE_TIPO_MOVIMIENTO_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the comprobante.fecha column for the current row.
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getComprobanteFecha() throws DataStoreException {
		return getDate(COMPROBANTE_FECHA);
	}

	/**
	 * Retrieve the value of the comprobante.fecha column for the specified row.
	 * @param row which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getComprobanteFecha(int row) throws DataStoreException {
		return getDate(row, COMPROBANTE_FECHA);
	}

	/**
	 * Set the value of the comprobante.fecha column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteFecha(java.sql.Date newValue)
		throws DataStoreException {
		setDate(COMPROBANTE_FECHA, newValue);
	}

	/**
	 * Set the value of the comprobante.fecha column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteFecha(int row, java.sql.Date newValue)
		throws DataStoreException {
		setDate(row, COMPROBANTE_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesNombre() throws DataStoreException {
		return getString(ALMACENES_NOMBRE);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesNombre(int row) throws DataStoreException {
		return getString(row, ALMACENES_NOMBRE);
	}

	/**
	 * Set the value of the almacenes.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesNombre(String newValue) throws DataStoreException {
		setString(ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Set the value of the almacenes.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesNombre(int row, String newValue)
		throws DataStoreException {
		setString(row, ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesDescripcion() throws DataStoreException {
		return getString(ALMACENES_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesDescripcion(int row) throws DataStoreException {
		return getString(row, ALMACENES_DESCRIPCION);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesDescripcion(String newValue)
		throws DataStoreException {
		setString(ALMACENES_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesDescripcion(int row, String newValue)
		throws DataStoreException {
		setString(row, ALMACENES_DESCRIPCION, newValue);
	}

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMMETHODS$

}
