package inventario.models;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * DetalleRCModel: A SOFIA generated model
 */
public class DetalleRCModel extends DataStore implements Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6156705448609010479L;
	// constants for columns
	public static final String DETALLES_RC_DETALLE_RC_ID = "detalles_rc.detalle_rc_id";
	public static final String DETALLES_RC_RECEPCION_COMPRA_ID = "detalles_rc.recepcion_compra_id";
	public static final String DETALLES_RC_DETALLE_SC_ID = "detalles_rc.detalle_sc_id";
	public static final String DETALLES_RC_ALMACEN_ID = "detalles_rc.almacen_id";
	public static final String DETALLES_RC_CANTIDAD = "detalles_rc.cantidad_recibida";
	public static final String DETALLES_RC_CANTIDAD_EXCEDENCIA = "detalles_rc.cantidad_excedencia";
	public static final String DETALLES_RC_UNIDAD_MEDIDA_ID = "detalles_rc.unidad_medida_id";
	public static final String RECEPCIONES_COMPRAS_FECHA = "recepciones_compras.fecha";
	public static final String RECEPCIONES_COMPRAS_ESTADO = "recepciones_compras.estado";
	public static final String RECEPCIONES_COMPRAS_PROVEEDOR_ID = "recepciones_compras.proveedor_id";
	public static final String RECEPCIONES_COMPRAS_USER_ID_COMPLETA = "recepciones_compras.user_id_completa";
	public static final String RECEPCIONES_COMPRAS_USER_ID_RECIBE = "recepciones_compras.user_id_recibe";
	public static final String DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DETALLE_SC_UNIDAD_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String ALMACENES_NOMBRE = "almacenes.nombre";
	public static final String ALMACENES_DESCRIPCION = "almacenes.descripcion";
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String UNIDAD_MEDIDA_NOMBRE = "unidad_medida.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new DetalleRCModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public DetalleRCModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new DetalleRCModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public DetalleRCModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("detalles_rc"), "detalles_rc");
			addTableAlias(computeTableName("detalle_sc"), "detalle_sc");
			addTableAlias(computeTableName("articulos"), "articulos");
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida");

			// add columns
			addColumn(computeTableName("detalles_rc"), "detalle_rc_id",
					DataStore.DATATYPE_INT, true, true,
					DETALLES_RC_DETALLE_RC_ID);
			addColumn(computeTableName("detalles_rc"), "recepcion_compra_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLES_RC_RECEPCION_COMPRA_ID);
			addColumn(computeTableName("detalles_rc"), "detalle_sc_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLES_RC_DETALLE_SC_ID);
			addColumn(computeTableName("detalles_rc"), "almacen_id",
					DataStore.DATATYPE_INT, false, true, DETALLES_RC_ALMACEN_ID);
			addColumn(computeTableName("detalles_rc"), "cantidad_recibida",
					DataStore.DATATYPE_DOUBLE, false, true,
					DETALLES_RC_CANTIDAD);
			addColumn(computeTableName("detalles_rc"), "cantidad_excedencia",
							DataStore.DATATYPE_DOUBLE, false, true,
							DETALLES_RC_CANTIDAD_EXCEDENCIA);
			addColumn(computeTableName("detalles_rc"), "unidad_medida_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLES_RC_UNIDAD_MEDIDA_ID);
			addColumn(computeTableName("recepciones_compras"), "fecha",
					DataStore.DATATYPE_DATETIME, false, false,
					RECEPCIONES_COMPRAS_FECHA);
			addColumn(computeTableName("recepciones_compras"), "estado",
					DataStore.DATATYPE_STRING, false, false,
					RECEPCIONES_COMPRAS_ESTADO);
			addColumn(computeTableName("recepciones_compras"), "proveedor_id",
					DataStore.DATATYPE_INT, false, false,
					RECEPCIONES_COMPRAS_PROVEEDOR_ID);
			addColumn(computeTableName("recepciones_compras"),
					"user_id_completa", DataStore.DATATYPE_INT, false, false,
					RECEPCIONES_COMPRAS_USER_ID_COMPLETA);
			addColumn(computeTableName("recepciones_compras"),
					"user_id_recibe", DataStore.DATATYPE_INT, false, false,
					RECEPCIONES_COMPRAS_USER_ID_RECIBE);
			addColumn(computeTableName("detalle_sc"), "orden_compra_id",
					DataStore.DATATYPE_INT, false, false,
					DETALLE_SC_ORDEN_COMPRA_ID);
			addColumn(computeTableName("detalle_sc"), "articulo_id",
					DataStore.DATATYPE_INT, false, false,
					DETALLE_SC_ARTICULO_ID);
			addColumn(computeTableName("detalle_sc"), "solicitud_compra_id",
					DataStore.DATATYPE_INT, false, false,
					DETALLE_SC_SOLICITUD_COMPRA_ID);
			addColumn(computeTableName("detalle_sc"), "cantidad_solicitada",
					DataStore.DATATYPE_DOUBLE, false, false,
					DETALLE_SC_CANTIDAD_SOLICITADA);
			addColumn(computeTableName("detalle_sc"), "cantidad_pedida",
					DataStore.DATATYPE_DOUBLE, false, false,
					DETALLE_SC_CANTIDAD_PEDIDA);
			addColumn(computeTableName("detalle_sc"), "cantidad_recibida",
					DataStore.DATATYPE_DOUBLE, false, false,
					DETALLE_SC_CANTIDAD_RECIBIDA);
			addColumn(computeTableName("detalle_sc"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					DETALLE_SC_DESCRIPCION);
			addColumn(computeTableName("detalle_sc"), "tarea_id",
					DataStore.DATATYPE_INT, false, false, DETALLE_SC_TAREA_ID);
			addColumn(computeTableName("detalle_sc"), "monto_unitario",
					DataStore.DATATYPE_STRING, false, false,
					DETALLE_SC_MONTO_UNITARIO);
			addColumn(computeTableName("detalle_sc"), "monto_ultima_compra",
					DataStore.DATATYPE_STRING, false, false,
					DETALLE_SC_MONTO_ULTIMA_COMPRA);
			addColumn(computeTableName("detalle_sc"), "fecha_ultima_compra",
					DataStore.DATATYPE_DATE, false, false,
					DETALLE_SC_FECHA_ULTIMA_COMPRA);
			addColumn(computeTableName("detalle_sc"), "unidad_medida_id",
					DataStore.DATATYPE_INT, false, false,
					DETALLE_SC_UNIDAD_MEDIDA_ID);
			addColumn(computeTableName("almacenes"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ALMACENES_NOMBRE);
			addColumn(computeTableName("almacenes"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					ALMACENES_DESCRIPCION);
			addColumn(computeTableName("articulos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("unidades_medida"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					UNIDAD_MEDIDA_NOMBRE);

			addBucket(UNIDAD_MEDIDA_NOMBRE, DataStore.DATATYPE_STRING);

			// add joins
			addJoin(
					computeTableAndFieldName("detalles_rc.recepcion_compra_id"),
					computeTableAndFieldName("recepciones_compras.recepcion_compra_id"),
					false);

			addJoin(computeTableAndFieldName("detalles_rc.detalle_sc_id"),
					computeTableAndFieldName("detalle_sc.detalle_SC_id"), false);

			addJoin(computeTableAndFieldName("detalles_rc.almacen_id"),
					computeTableAndFieldName("almacenes.almacen_id"), false);

			addJoin(computeTableAndFieldName("detalle_sc.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);

			addJoin(
					computeTableAndFieldName("detalles_rc.unidad_medida_id"),
					computeTableAndFieldName("unidades_medida.unidad_medida_id"),
					true);

			// add validations
			addRequiredRule(DETALLES_RC_ALMACEN_ID, "El almacén es obligatorio");
			addRequiredRule(DETALLES_RC_CANTIDAD,
					"Debe indicar la cantidad recibida del artículo");
			addRequiredRule(DETALLES_RC_DETALLE_SC_ID,
					"Debe indicar el artículo al que está relacionado esta recepción");
			addRequiredRule(DETALLES_RC_RECEPCION_COMPRA_ID,
					"Indique a qué recepción pertenece este artículo");

			// add lookups
			addLookupRule(
					DETALLES_RC_DETALLE_SC_ID,
					computeTableName("detalle_sc"),
					"'detalle_sc.detalle_sc_id = ' + detalles_rc.detalle_sc_id",
					"descripcion", DETALLE_SC_DESCRIPCION,
					"Articulo inexistente");
			addLookupRule(DETALLE_SC_ARTICULO_ID,
					computeTableName("articulos"),
					"'articulos.articulo_id = ' + detalle_sc.articulo_id",
					"nombre", ARTICULOS_NOMBRE, "Articulo inexistente");
			addLookupRule(DETALLE_SC_ARTICULO_ID,
					computeTableName("articulos"),
					"'articulos.articulo_id = ' + detalle_sc.articulo_id",
					"descripcion", ARTICULOS_DESCRIPCION,
					"Articulo inexistente");
			addLookupRule(
					DETALLES_RC_UNIDAD_MEDIDA_ID,
					computeTableName("unidades_medida"),
					"'unidades_medida.unidad_medida_id = ' + detalles_rc.unidad_medida_id",
					"nombre", UNIDAD_MEDIDA_NOMBRE,
					"Unidad de medida inexistente");

			setAutoIncrement(DETALLES_RC_DETALLE_RC_ID, true);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the detalles_rc.detalle_rc_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcDetalleRcId() throws DataStoreException {
		return getInt(DETALLES_RC_DETALLE_RC_ID);
	}

	/**
	 * Retrieve the value of the detalles_rc.detalle_rc_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcDetalleRcId(int row) throws DataStoreException {
		return getInt(row, DETALLES_RC_DETALLE_RC_ID);
	}

	/**
	 * Set the value of the detalles_rc.detalle_rc_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcDetalleRcId(int newValue)
			throws DataStoreException {
		setInt(DETALLES_RC_DETALLE_RC_ID, newValue);
	}

	/**
	 * Set the value of the detalles_rc.detalle_rc_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcDetalleRcId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLES_RC_DETALLE_RC_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalles_rc.recepcion_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcRecepcionCompraId() throws DataStoreException {
		return getInt(DETALLES_RC_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the detalles_rc.recepcion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcRecepcionCompraId(int row)
			throws DataStoreException {
		return getInt(row, DETALLES_RC_RECEPCION_COMPRA_ID);
	}

	/**
	 * Set the value of the detalles_rc.recepcion_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcRecepcionCompraId(int newValue)
			throws DataStoreException {
		setInt(DETALLES_RC_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the detalles_rc.recepcion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcRecepcionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLES_RC_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalles_rc.detalle_sc_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcDetalleScId() throws DataStoreException {
		return getInt(DETALLES_RC_DETALLE_SC_ID);
	}

	/**
	 * Retrieve the value of the detalles_rc.detalle_sc_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcDetalleScId(int row) throws DataStoreException {
		return getInt(row, DETALLES_RC_DETALLE_SC_ID);
	}

	/**
	 * Set the value of the detalles_rc.detalle_sc_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcDetalleScId(int newValue)
			throws DataStoreException {
		setInt(DETALLES_RC_DETALLE_SC_ID, newValue);
	}

	/**
	 * Set the value of the detalles_rc.detalle_sc_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcDetalleScId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLES_RC_DETALLE_SC_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalles_rc.almacen_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcAlmacenId() throws DataStoreException {
		return getInt(DETALLES_RC_ALMACEN_ID);
	}

	/**
	 * Retrieve the value of the detalles_rc.almacen_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcAlmacenId(int row) throws DataStoreException {
		return getInt(row, DETALLES_RC_ALMACEN_ID);
	}

	/**
	 * Set the value of the detalles_rc.almacen_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcAlmacenId(int newValue) throws DataStoreException {
		setInt(DETALLES_RC_ALMACEN_ID, newValue);
	}

	/**
	 * Set the value of the detalles_rc.almacen_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcAlmacenId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLES_RC_ALMACEN_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalles_rc.cantidad_recibida column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetallesRcCantidad() throws DataStoreException {
		return getDouble(DETALLES_RC_CANTIDAD);
	}

	/**
	 * Retrieve the value of the detalles_rc.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetallesRcCantidad(int row) throws DataStoreException {
		return getDouble(row, DETALLES_RC_CANTIDAD);
	}

	/**
	 * Set the value of the detalles_rc.cantidad_recibida column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcCantidad(double newValue)
			throws DataStoreException {
		setDouble(DETALLES_RC_CANTIDAD, newValue);
	}

	/**
	 * Set the value of the detalles_rc.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcCantidad(int row, double newValue)
			throws DataStoreException {
		setDouble(row, DETALLES_RC_CANTIDAD, newValue);
	}

	/**
	 * Retrieve the value of the detalles_rc.cantidad_excedencia column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetallesRcCantidadExcedencia() throws DataStoreException {
		return getDouble(DETALLES_RC_CANTIDAD_EXCEDENCIA);
	}

	/**
	 * Retrieve the value of the detalles_rc.cantidad_excedencia column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetallesRcCantidadExcedencia(int row) throws DataStoreException {
		return getDouble(row, DETALLES_RC_CANTIDAD_EXCEDENCIA);
	}

	/**
	 * Set the value of the detalles_rc.cantidad_excedencia column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcCantidadExcedencia(double newValue)
			throws DataStoreException {
		setDouble(DETALLES_RC_CANTIDAD_EXCEDENCIA, newValue);
	}

	/**
	 * Set the value of the detalles_rc.cantidad_excedencia column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcCantidadExcedencia(int row, double newValue)
			throws DataStoreException {
		setDouble(row, DETALLES_RC_CANTIDAD_EXCEDENCIA, newValue);
	}
	
	/**
	 * Retrieve the value of the detalles_rc.unidad_medida_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcUnidadMedidaId() throws DataStoreException {
		return getInt(DETALLES_RC_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the detalles_rc.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetallesRcUnidadMedidaId(int row) throws DataStoreException {
		return getInt(row, DETALLES_RC_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Set the value of the detalles_rc.unidad_medida_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcUnidadMedidaId(int newValue)
			throws DataStoreException {
		setInt(DETALLES_RC_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the detalles_rc.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetallesRcUnidadMedidaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLES_RC_UNIDAD_MEDIDA_ID, newValue);
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
	public int getRecepcionesComprasProveedorId() throws DataStoreException {
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
	public int getRecepcionesComprasProveedorId(int row)
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
	public void setRecepcionesComprasProveedorId(int newValue)
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
	public void setRecepcionesComprasProveedorId(int row, int newValue)
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
	 * Retrieve the value of the detalle_sc.orden_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScOrdenCompraId() throws DataStoreException {
		return getInt(DETALLE_SC_ORDEN_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.orden_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScOrdenCompraId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_ORDEN_COMPRA_ID);
	}

	/**
	 * Set the value of the detalle_sc.orden_compra_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScOrdenCompraId(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.orden_compra_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScOrdenCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.articulo_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScArticuloId() throws DataStoreException {
		return getInt(DETALLE_SC_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.articulo_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScArticuloId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_ARTICULO_ID);
	}

	/**
	 * Set the value of the detalle_sc.articulo_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScArticuloId(int newValue) throws DataStoreException {
		setInt(DETALLE_SC_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.articulo_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.solicitud_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScSolicitudCompraId() throws DataStoreException {
		return getInt(DETALLE_SC_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.solicitud_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScSolicitudCompraId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Set the value of the detalle_sc.solicitud_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScSolicitudCompraId(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.solicitud_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScSolicitudCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_solicitada column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadSolicitada() throws DataStoreException {
		return getDouble(DETALLE_SC_CANTIDAD_SOLICITADA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_solicitada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadSolicitada(int row)
			throws DataStoreException {
		return getDouble(row, DETALLE_SC_CANTIDAD_SOLICITADA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_solicitada column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadSolicitada(double newValue)
			throws DataStoreException {
		setDouble(DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_solicitada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadSolicitada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_pedida column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadPedida() throws DataStoreException {
		return getDouble(DETALLE_SC_CANTIDAD_PEDIDA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadPedida(int row) throws DataStoreException {
		return getDouble(row, DETALLE_SC_CANTIDAD_PEDIDA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_pedida column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadPedida(double newValue)
			throws DataStoreException {
		setDouble(DETALLE_SC_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_pedida column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadPedida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, DETALLE_SC_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_recibida column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadRecibida() throws DataStoreException {
		return getDouble(DETALLE_SC_CANTIDAD_RECIBIDA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getDetalleScCantidadRecibida(int row)
			throws DataStoreException {
		return getDouble(row, DETALLE_SC_CANTIDAD_RECIBIDA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_recibida column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadRecibida(double newValue)
			throws DataStoreException {
		setDouble(DETALLE_SC_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadRecibida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, DETALLE_SC_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScDescripcion() throws DataStoreException {
		return getString(DETALLE_SC_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the detalle_sc.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScDescripcion(int row) throws DataStoreException {
		return getString(row, DETALLE_SC_DESCRIPCION);
	}

	/**
	 * Set the value of the detalle_sc.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDescripcion(String newValue)
			throws DataStoreException {
		setString(DETALLE_SC_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the detalle_sc.descripcion column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, DETALLE_SC_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.tarea_id column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScTareaId() throws DataStoreException {
		return getInt(DETALLE_SC_TAREA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.tarea_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScTareaId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_TAREA_ID);
	}

	/**
	 * Set the value of the detalle_sc.tarea_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScTareaId(int newValue) throws DataStoreException {
		setInt(DETALLE_SC_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.tarea_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_unitario column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScMontoUnitario() throws DataStoreException {
		return getString(DETALLE_SC_MONTO_UNITARIO);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_unitario column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScMontoUnitario(int row) throws DataStoreException {
		return getString(row, DETALLE_SC_MONTO_UNITARIO);
	}

	/**
	 * Set the value of the detalle_sc.monto_unitario column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUnitario(String newValue)
			throws DataStoreException {
		setString(DETALLE_SC_MONTO_UNITARIO, newValue);
	}

	/**
	 * Set the value of the detalle_sc.monto_unitario column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUnitario(int row, String newValue)
			throws DataStoreException {
		setString(row, DETALLE_SC_MONTO_UNITARIO, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_ultima_compra column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScMontoUltimaCompra() throws DataStoreException {
		return getString(DETALLE_SC_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScMontoUltimaCompra(int row)
			throws DataStoreException {
		return getString(row, DETALLE_SC_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the detalle_sc.monto_ultima_compra column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUltimaCompra(String newValue)
			throws DataStoreException {
		setString(DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.monto_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUltimaCompra(int row, String newValue)
			throws DataStoreException {
		setString(row, DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.fecha_ultima_compra column for the
	 * current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getDetalleScFechaUltimaCompra()
			throws DataStoreException {
		return getDate(DETALLE_SC_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the detalle_sc.fecha_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getDetalleScFechaUltimaCompra(int row)
			throws DataStoreException {
		return getDate(row, DETALLE_SC_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the detalle_sc.fecha_ultima_compra column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScFechaUltimaCompra(java.sql.Date newValue)
			throws DataStoreException {
		setDate(DETALLE_SC_FECHA_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.fecha_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScFechaUltimaCompra(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, DETALLE_SC_FECHA_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedidaId() throws DataStoreException {
		return getInt(DETALLE_SC_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedidaId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedidaId(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedidaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesNombre() throws DataStoreException {
		return getString(ALMACENES_NOMBRE);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesNombre(int row) throws DataStoreException {
		return getString(row, ALMACENES_NOMBRE);
	}

	/**
	 * Set the value of the almacenes.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesNombre(String newValue) throws DataStoreException {
		setString(ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Set the value of the almacenes.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesDescripcion() throws DataStoreException {
		return getString(ALMACENES_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAlmacenesDescripcion(int row) throws DataStoreException {
		return getString(row, ALMACENES_DESCRIPCION);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesDescripcion(String newValue)
			throws DataStoreException {
		setString(ALMACENES_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAlmacenesDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ALMACENES_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosNombre() throws DataStoreException {
		return getString(ARTICULOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosNombre(int row) throws DataStoreException {
		return getString(row, ARTICULOS_NOMBRE);
	}

	/**
	 * Set the value of the articulos.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosNombre(String newValue) throws DataStoreException {
		setString(ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcion() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcion(int row) throws DataStoreException {
		return getString(row, ARTICULOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcion(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadMedidaNombre() throws DataStoreException {
		return getString(UNIDAD_MEDIDA_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadMedidaNombre(int row) throws DataStoreException {
		return getString(row, UNIDAD_MEDIDA_NOMBRE);
	}

	/**
	 * Set the value of the articulos.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadMedidaNombre(String newValue)
			throws DataStoreException {
		setString(UNIDAD_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadMedidaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDAD_MEDIDA_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	@Override
	public void update(DBConnection connection, boolean handleTrans)
			throws DataStoreException, SQLException {
		DetalleSCModel detalleSC = null;
		ArticulosModel articulos = null;
		for (int row = 0; row < getRowCount(); row++) {
			
			if (detalleSC == null)
				detalleSC = new DetalleSCModel("inventario");
			detalleSC.retrieve("detalle_SC_id ="
					+ getDetallesRcDetalleScId(row));
			detalleSC.waitForRetrieve();
			// fills detalle_sc.articulo_id field through ArticulosNombre
			if (detalleSC.gotoFirst()) {
				if (articulos == null)
					articulos = new ArticulosModel("inventario");
				articulos.retrieve("articulos.nombre LIKE '"
						+ detalleSC.getArticulosNombre() + "'");
				if (!articulos.gotoFirst()) {
					DataStoreException ex = new DataStoreException(
							"El código de articulo ingresado no corresponde a ninguno registrado");
					ex.setRowNo(row);
					throw ex;
				}
				setDetalleScArticuloId(row, articulos.getArticulosArticuloId());
				int unidad_patron = Integer.parseInt(AtributosEntidadModel
						.getValorAtributoObjeto(ARTICULO_UNIDAD_MEDIDA,
								articulos.getArticulosArticuloId(), "TABLA",
								"articulos"));
				
				if (getDetallesRcUnidadMedidaId(row) == 0 && unidad_patron != 0) {
					setDetallesRcUnidadMedidaId(row, unidad_patron);
				}				
			}			
		}

		super.update(connection, handleTrans);
	}

	public void reloadRows() throws DataStoreException, SQLException {
		DBConnection conn = DBConnection.getConnection("inventario");
		try {
		for (int row = 0; row < getRowCount(); row++) {
			reloadRow(conn, row);
		}
		} finally {
			if(conn != null)
				conn.freeConnection();
		}
	}
	
	public void reloadRows(DBConnection conn) throws DataStoreException, SQLException {
		for (int row = 0; row < getRowCount(); row++) {
			reloadRow(conn, row);
		}
	}
	// $ENDCUSTOMMETHODS$

}
