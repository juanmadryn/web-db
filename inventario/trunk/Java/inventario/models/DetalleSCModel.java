package inventario.models;

import infraestructura.models.AtributosEntidadModel;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import proyectos.models.TareasProyectoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * DetalleSCModel: A SOFIA generated model
 */
public class DetalleSCModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4256105432668574634L;
	// constants for columns
	public static final String DETALLE_SC_DETALLE_SC_ID = "detalle_sc.detalle_SC_id";
	public static final String DETALLE_SC_RECEPCION_COMPRA_ID = "detalle_sc.recepcion_compra_id";
	public static final String DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DETALLE_SC_OBSERVACIONES = "detalle_sc.observaciones";
	public static final String ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DETALLE_SC_UNIDAD_DE_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String UNIDAD_DE_MEDIDA_NOMBRE = "unidades_medida.nombre";

	// $CUSTOMVARS$
	public static final String TAREA_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DETALLE_SC_MONTO_TOTAL = "monto_total";
	public static final String CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";

	// $ENDCUSTOMVARS$

	/**
	 * Create a new DetalleSCModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public DetalleSCModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new DetalleSCModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public DetalleSCModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("detalle_sc"), "detalle_sc");
			addTableAlias(computeTableName("articulos"), "articulos");
			addTableAlias(computeTableName("proyectos.tareas_proyecto"),
					"tareas_proyecto");
			addTableAlias(computeTableName("solicitudes_compra"),
					"solicitudes_compra");
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida");

			// add columns
			addColumn(computeTableName("detalle_sc"), "detalle_SC_id",
					DataStore.DATATYPE_INT, true, true,
					DETALLE_SC_DETALLE_SC_ID);
			addColumn(computeTableName("detalle_sc"), "recepcion_compra_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_RECEPCION_COMPRA_ID);
			addColumn(computeTableName("detalle_sc"), "orden_compra_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_ORDEN_COMPRA_ID);
			addColumn(computeTableName("detalle_sc"), "articulo_id",
					DataStore.DATATYPE_INT, false, true, DETALLE_SC_ARTICULO_ID);
			addColumn(computeTableName("detalle_sc"), "solicitud_compra_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_SOLICITUD_COMPRA_ID);
			addColumn(computeTableName("detalle_sc"), "cantidad_solicitada",
					DataStore.DATATYPE_FLOAT, false, true,
					DETALLE_SC_CANTIDAD_SOLICITADA);
			addColumn(computeTableName("detalle_sc"), "cantidad_pedida",
					DataStore.DATATYPE_FLOAT, false, true,
					DETALLE_SC_CANTIDAD_PEDIDA);
			addColumn(computeTableName("detalle_sc"), "cantidad_recibida",
					DataStore.DATATYPE_FLOAT, false, true,
					DETALLE_SC_CANTIDAD_RECIBIDA);
			addColumn(computeTableName("detalle_sc"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					DETALLE_SC_DESCRIPCION);
			addColumn(computeTableName("detalle_sc"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					DETALLE_SC_OBSERVACIONES);
			addColumn(computeTableName("articulos"), "clase_articulo_id",
					DataStore.DATATYPE_INT, false, true,
					ARTICULOS_CLASE_ARTICULO_ID);
			addColumn(computeTableName("articulos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("clase_articulo"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CLASE_ARTICULO_NOMBRE);
			addColumn(computeTableName("clase_articulo"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					CLASE_ARTICULO_DESCRIPCION);
			addColumn(computeTableName("detalle_sc"), "tarea_id",
					DataStore.DATATYPE_INT, false, true, DETALLE_SC_TAREA_ID);
			addColumn(computeTableName("detalle_sc"), "monto_unitario",
					DataStore.DATATYPE_FLOAT, false, true,
					DETALLE_SC_MONTO_UNITARIO);
			addColumn(computeTableName("detalle_sc"), "monto_ultima_compra",
					DataStore.DATATYPE_FLOAT, false, true,
					DETALLE_SC_MONTO_ULTIMA_COMPRA);
			addColumn(computeTableName("tareas_proyecto"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					TAREA_PROYECTO_NOMBRE);
			addColumn(computeTableName("solicitudes_compra"), "estado",
					DataStore.DATATYPE_STRING, false, false,
					SOLICITUDES_COMPRA_ESTADO);
			addColumn(computeTableName("detalle_sc"), "fecha_ultima_compra",
					DataStore.DATATYPE_DATE, false, true,
					DETALLE_SC_FECHA_ULTIMA_COMPRA);
			addColumn(computeTableName("detalle_sc"), "unidad_medida_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_UNIDAD_DE_MEDIDA_ID);
			addColumn(computeTableName("unidades_medida"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					UNIDAD_DE_MEDIDA_NOMBRE);

			// add bucket
			addBucket(DETALLE_SC_MONTO_TOTAL, DataStore.DATATYPE_FLOAT);

			// add joins
			addJoin(computeTableAndFieldName("detalle_sc.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);
			addJoin(
					computeTableAndFieldName("articulos.clase_articulo_id"),
					computeTableAndFieldName("clase_articulo.clase_articulo_id"),
					false);
			addJoin(computeTableAndFieldName("detalle_sc.tarea_id"),
					computeTableAndFieldName("tareas_proyecto.tarea_id"), true);
			addJoin(
					computeTableAndFieldName("detalle_sc.solicitud_compra_id"),
					computeTableAndFieldName("solicitudes_compra.solicitud_compra_id"),
					false);
			addJoin(
					computeTableAndFieldName("detalle_sc.unidad_medida_id"),
					computeTableAndFieldName("unidades_medida.unidad_medida_id"),
					false);
			

			// add validations
			addRequiredRule(ARTICULOS_NOMBRE,
					"Especifique el código del artículo.");

			addRequiredRule(DETALLE_SC_CANTIDAD_SOLICITADA,
					"Especifique la cantidad a pedir del artículo.");

			// add lookups
			addLookupRule(
					TAREA_PROYECTO_NOMBRE,
					"proyectos.tareas_proyecto",
					"'proyectos.tareas_proyecto.tarea_id = ' + detalle_sc.tarea_id",
					"nombre", TAREA_PROYECTO_NOMBRE, "Tarea inexistente");
			addLookupRule(
					CLASE_ARTICULO_NOMBRE,
					"inventario.clase_articulo",
					"'inventario.clase_articulo.clase_articulo_id = ' + articulos.clase_articulo_id",
					"nombre", CLASE_ARTICULO_NOMBRE, "Proyecto inexistente");
			addLookupRule(
					SOLICITUDES_COMPRA_ESTADO,
					"solicitudes_compra",
					"'solicitudes_compra.solicitud_compra_id = ' + detalle_sc.solicitud_compra_id",
					"estado", SOLICITUDES_COMPRA_ESTADO,
					"Solicitud de compra inexistente");
			addLookupRule(
					UNIDAD_DE_MEDIDA_NOMBRE,
					"unidades_medida",
					"'unidades_medida.unidad_medida_id = ' + detalle_sc.unidad_medida_id",
					"nombre", UNIDAD_DE_MEDIDA_NOMBRE,
					"Unidad de medida inexistente");

			setAutoIncrement(DETALLE_SC_DETALLE_SC_ID, true);
			setUpdateable(DETALLE_SC_DETALLE_SC_ID, false);
			setOrderBy(DETALLE_SC_DETALLE_SC_ID + " DESC");

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		addTableAlias(computeTableName("proyectos.proyectos"), "proyectos");
		addTableAlias(computeTableName("centro_costo"), "centro_costo");
		addTableAlias(computeTableName("ordenes_compra"), "ordenes_compra");

		addColumn(computeTableName("proyectos"), "nombre",
				DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
		addColumn(computeTableName("centro_costo"), "nombre",
				DataStore.DATATYPE_STRING, false, false, CENTRO_COSTO_NOMBRE);
		addColumn(computeTableName("solicitudes_compra"), "fecha_solicitud",
				DataStore.DATATYPE_DATE, false, false,
				SOLICITUDES_COMPRA_FECHA_APROBACION);		

		addJoin(computeTableAndFieldName("solicitudes_compra.proyecto_id"),
				computeTableAndFieldName("proyectos.proyecto_id"), true);
		addJoin(computeTableAndFieldName("solicitudes_compra.centro_costo_id"),
				computeTableAndFieldName("centro_costo.centro_costo_id"), true);
		addJoin(computeTableAndFieldName("detalle_sc.orden_compra_id"),
				computeTableAndFieldName("ordenes_compra.orden_compra_id"),
				true);
		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the detalle_sc.detalle_SC_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScDetalleScId() throws DataStoreException {
		return getInt(DETALLE_SC_DETALLE_SC_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.detalle_SC_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScDetalleScId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_DETALLE_SC_ID);
	}

	/**
	 * Set the value of the detalle_sc.detalle_SC_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDetalleScId(int newValue) throws DataStoreException {
		setInt(DETALLE_SC_DETALLE_SC_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.detalle_SC_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDetalleScId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_DETALLE_SC_ID, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.recepcion_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScRecepcionCompraId() throws DataStoreException {
		return getInt(DETALLE_SC_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.recepcion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScRecepcionCompraId(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_RECEPCION_COMPRA_ID);
	}

	/**
	 * Set the value of the detalle_sc.recepcion_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScRecepcionCompraId(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.recepcion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScRecepcionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_RECEPCION_COMPRA_ID, newValue);
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
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadSolicitada() throws DataStoreException {
		return getFloat(DETALLE_SC_CANTIDAD_SOLICITADA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_solicitada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadSolicitada(int row)
			throws DataStoreException {
		return getFloat(row, DETALLE_SC_CANTIDAD_SOLICITADA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_solicitada column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadSolicitada(float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
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
	public void setDetalleScCantidadSolicitada(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_pedida column for the
	 * current row.
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadPedida() throws DataStoreException {
		return getFloat(DETALLE_SC_CANTIDAD_PEDIDA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadPedida(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_CANTIDAD_PEDIDA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_pedida column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadPedida(float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_CANTIDAD_PEDIDA, newValue);
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
	public void setDetalleScCantidadPedida(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_recibida column for the
	 * current row.
	 * 
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadRecibida() throws DataStoreException {
		return getFloat(DETALLE_SC_CANTIDAD_RECIBIDA);
	}

	/**
	 * Retrieve the value of the detalle_sc.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return float
	 * @throws DataStoreException
	 */
	public float getDetalleScCantidadRecibida(int row)
			throws DataStoreException {
		return getFloat(row, DETALLE_SC_CANTIDAD_RECIBIDA);
	}

	/**
	 * Set the value of the detalle_sc.cantidad_recibida column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCantidadRecibida(float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_CANTIDAD_RECIBIDA, newValue);
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
	public void setDetalleScCantidadRecibida(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_CANTIDAD_RECIBIDA, newValue);
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
	 * Retrieve the value of the detalle_sc.observaciones column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScObservaciones() throws DataStoreException {
		return getString(DETALLE_SC_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the detalle_sc.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScObservaciones(int row) throws DataStoreException {
		return getString(row, DETALLE_SC_OBSERVACIONES);
	}

	/**
	 * Set the value of the detalle_sc.observaciones column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScObservaciones(String newValue)
			throws DataStoreException {
		setString(DETALLE_SC_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the detalle_sc.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, DETALLE_SC_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the articulos.clase_articulo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosClaseArticuloId() throws DataStoreException {
		return getInt(ARTICULOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the articulos.clase_articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosClaseArticuloId(int row) throws DataStoreException {
		return getInt(row, ARTICULOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Set the value of the articulos.clase_articulo_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosClaseArticuloId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the articulos.clase_articulo_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosClaseArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_CLASE_ARTICULO_ID, newValue);
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
	 * @return Float
	 * @throws DataStoreException
	 */
	public Float getDetalleScMontoUnitario() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_UNITARIO);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_unitario column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return Float
	 * @throws DataStoreException
	 */
	public Float getDetalleScMontoUnitario(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_UNITARIO);
	}

	/**
	 * Set the value of the detalle_sc.monto_unitario column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUnitario(Float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_UNITARIO, newValue);
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
	public void setDetalleScMontoUnitario(int row, Float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_UNITARIO, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_ultima_compra column for the
	 * current row.
	 * 
	 * @return Float
	 * @throws DataStoreException
	 */
	public Float getDetalleScMontoUltimaCompra() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the detalle_sc.monto_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return Float
	 * @throws DataStoreException
	 */
	public Float getDetalleScMontoUltimaCompra(int row)
			throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the detalle_sc.monto_ultima_compra column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScMontoUltimaCompra(Float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
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
	public void setDetalleScMontoUltimaCompra(int row, Float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedida() throws DataStoreException {
		return getInt(DETALLE_SC_UNIDAD_DE_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedida(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_UNIDAD_DE_MEDIDA_ID);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedida(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_UNIDAD_DE_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedida(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_UNIDAD_DE_MEDIDA_ID, newValue);
	}

	// $CUSTOMMETHODS$
	/**
	 * Retrieve the value of the tareas_proyecto.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareaProyectoNombre() throws DataStoreException {
		return getString(TAREA_PROYECTO_NOMBRE);
	}

	/**
	 * Retrieve the value of the tareas_proyecto.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareaProyectoNombre(int row) throws DataStoreException {
		return getString(row, TAREA_PROYECTO_NOMBRE);
	}

	/**
	 * Set the value of the tareas_proyecto.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTareaProyectoNombre(String newValue)
			throws DataStoreException {
		setString(TAREA_PROYECTO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the tareas_proyecto.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTareaProyectoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREA_PROYECTO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the monto_total column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotal() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_TOTAL);
	}

	/**
	 * Retrieve the value of the monto_total column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotal(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_TOTAL);
	}

	/**
	 * Set the value of the monto_total column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotal(Float newValue) throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_TOTAL, newValue);
	}

	/**
	 * Set the value of the monto_total column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotal(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_TOTAL, newValue);
	}

	/**
	 * Retrieve the value of the estado column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudCompraEstado() throws DataStoreException {
		return getString(SOLICITUDES_COMPRA_ESTADO);
	}

	/**
	 * Retrieve the value of the estado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getSolicitudCompraEstado(int row) throws DataStoreException {
		return getString(row, SOLICITUDES_COMPRA_ESTADO);
	}

	/**
	 * Set the value of the estado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudCompraEstado(String newValue)
			throws DataStoreException {
		setString(SOLICITUDES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Set the value of the estado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setSolicitudCompraEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, SOLICITUDES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the fecha_ultima_compra column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public java.sql.Date getDetalleScFechaUltimaCompra()
			throws DataStoreException {
		return getDate(DETALLE_SC_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the fecha_ultima_compra column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public java.sql.Date getDetalleScFechaUltimaCompra(int row)
			throws DataStoreException {
		return getDate(row, DETALLE_SC_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the estado fecha_ultima_compra for the current row.
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
	 * Set the value of the estado fecha_ultima_compra for the specified row.
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
	 * Calculate the value of the monto_total column.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void setMontoTotal() throws DataStoreException, SQLException {
		setMontoTotal(getRow());
	}

	public void setMontoTotal(int row) throws DataStoreException, SQLException {
		SolicitudCompraModel solicitud = new SolicitudCompraModel("inventario",
				"inventario");
		solicitud.retrieve("solicitud_compra_id = "
				+ getDetalleScSolicitudCompraId(row));
		solicitud.gotoFirst();
		setMontoTotal(row, solicitud);
	}

	/**
	 * Calculate the value of the monto_total column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void setMontoTotal(int row, SolicitudCompraModel solicitud)
			throws DataStoreException, SQLException {
		Float monto_unitario = getDetalleScMontoUnitario(row);

		Float cantidad = null;
		if (("0006.0006".equalsIgnoreCase(solicitud
				.getSolicitudesCompraEstado()))
				|| ("0006.0007".equalsIgnoreCase(solicitud
						.getSolicitudesCompraEstado()))) {
			if (getDetalleScCantidadPedida(row) > 0)
				cantidad = getDetalleScCantidadPedida(row);
			else
				cantidad = getDetalleScCantidadSolicitada(row);
		} else {
			cantidad = getDetalleScCantidadSolicitada(row);
		}

		if (monto_unitario != null && cantidad != null) {
			Float total = monto_unitario * cantidad;
			setMontoTotal(row, total);
		}
	}

	@Override
	public void update(DBConnection connection, boolean handleTrans)
			throws DataStoreException, SQLException {

		SolicitudCompraModel solicitud = new SolicitudCompraModel("inventario",
				"inventario");		
		
		for (int row = 0; row < getRowCount(); row++) {
			solicitud.retrieve(connection, "solicitud_compra_id = "
					+ getDetalleScSolicitudCompraId(row));
			solicitud.gotoFirst();

			int proyecto_id = solicitud.getSolicitudesCompraProyectoId();
			int tarea_id = getDetalleScTareaId(row);
			TareasProyectoModel dsTareas = new TareasProyectoModel("proyectos",
					"proyectos");

			if (tarea_id != 0) {
				// checks if tarea exist for specified project
				if (dsTareas
						.estimateRowsRetrieved("tareas_proyecto.proyecto_id = "
								+ proyecto_id
								+ " AND tareas_proyecto.tarea_id = " + tarea_id) == 0)
					throw new DataStoreException(
							"La tarea especificada no pertenece al proyecto al cual está imputada la solicitud");
			} else if (proyecto_id != 0) {
				dsTareas.retrieve("tareas_proyecto.proyecto_id = "
						+ proyecto_id);
				dsTareas.gotoFirst();
				setDetalleScTareaId(row, dsTareas.getTareasProyectoTareaId());
			}

			ArticulosModel articulos;
			// fills detalle_sc.articulo_id field through ArticulosNombre
			if (getArticulosNombre() != null) {
				articulos = new ArticulosModel("inventario", "inventario");
				articulos.retrieve(connection, "articulos.nombre LIKE '"
						+ getArticulosNombre() + "'");
				if (!articulos.gotoFirst())
					throw new DataStoreException(
							"El código de articulo ingresado no corresponde a ninguno registrado");
				;
				setDetalleScArticuloId(row, articulos.getArticulosArticuloId());
			}

			// fills detalle_sc.descripcion with articulos.descripcion if is
			// null
			if (getDetalleScDescripcion(row) == null
					&& getArticulosDescripcion() != null)
				setDetalleScDescripcion(row, getArticulosDescripcion());

			// if the detail has an oc asigned, check that the amount ordered is
			// a positive number
			if ((getDetalleScOrdenCompraId(row) > 0)
					&& (getDetalleScCantidadPedida(row) <= 0)) {
				throw new DataStoreException(
						"La cantidad pedida debe ser un número positivo mayor que cero");
			}

			// sets monto and fecha ultima compra with value stored in
			// attributes table
			if (getDetalleScMontoUltimaCompra(row) == 0) {
				try {
					String monto_ultima_compra = AtributosEntidadModel
							.getValorAtributoObjeto("MONTO_ULTIMA_COMPRA",
									getDetalleScArticuloId(row), "TABLA",
									"articulos");
					String fecha_ultima_compra = AtributosEntidadModel
							.getValorAtributoObjeto("FECHA_ULTIMA_COMPRA",
									getDetalleScArticuloId(row), "TABLA",
									"articulos");

					setDetalleScMontoUltimaCompra(row, Float
							.parseFloat(monto_ultima_compra));

					if (fecha_ultima_compra != null) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						setDetalleScFechaUltimaCompra(row, new java.sql.Date(df
								.parse(fecha_ultima_compra).getTime()));
					}

				} catch (ParseException e) {
					throw new DataStoreException(
							"Error de parseo en el seteo del monto y fecha de última compra: "
									+ e.getMessage());
				}

			}

			if (getDetalleScMontoUnitario(row) == 0)
				setDetalleScMontoUnitario(row,
						getDetalleScMontoUltimaCompra(row));

			if (getDetalleScUnidadMedida(row) == 0)
				setDetalleScUnidadMedida(row, Integer
						.parseInt(AtributosEntidadModel.getValorAtributoObjeto(
								"UNIDAD_DE_MEDIDA",
								getDetalleScArticuloId(row), "TABLA",
								"articulos")));
			setMontoTotal(row);

		}
		
		super.update(connection, handleTrans);
	}

	// checks if every detail has monto_unitario filled
	public boolean chequeaTotalesDetallesSolicitud(int solicitud_id)
			throws DataStoreException, SQLException {
		retrieve("detalle_sc.solicitud_compra_id = " + solicitud_id);
		for (int row = 0; row < getRowCount(); row++) {
			if (getDetalleScMontoUnitario(row) == 0)
				return false;
		}
		return true;
	}

	/**
	 * Retrieve the value of the fecha_ultima_compra column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosProyecto() throws DataStoreException {
		return getString(PROYECTOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the fecha_ultima_compra column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosProyecto(int row) throws DataStoreException {
		return getString(row, PROYECTOS_NOMBRE);
	}

	/**
	 * Set the value of the estado fecha_ultima_compra for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the estado fecha_ultima_compra for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_NOMBRE, newValue);
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
	 * Retrieve the value of the unidades_medida.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadMedidaNombre() throws DataStoreException {
		return getString(UNIDAD_DE_MEDIDA_NOMBRE);
	}

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadMedidaNombre(int row) throws DataStoreException {
		return getString(row, UNIDAD_DE_MEDIDA_NOMBRE);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadMedidaNombre(String newValue) throws DataStoreException {
		setString(UNIDAD_DE_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadMedidaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDAD_DE_MEDIDA_NOMBRE, newValue);
	}
	// $ENDCUSTOMMETHODS$

}
