package inventario.models;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

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
public class DetalleSCModel extends DataStore implements Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4256105432668574634L;
	// constants for columns
	public static final String DETALLE_SC_DETALLE_SC_ID = "detalle_sc.detalle_SC_id";
	public static final String DETALLE_SC_RECEPCION_COMPRA_ID = "detalle_sc.recepcion_compra_id";
	public static final String DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DETALLE_SC_COTIZACION_COMPRA_ID = "detalle_sc.cotizacion_compra_id";
	public static final String DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DETALLE_SC_OBSERVACIONES = "detalle_sc.observaciones";
	public static final String DETALLE_SC_OBSERVACIONES_OC = "detalle_sc.observaciones_oc";
	public static final String ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DETALLE_SC_UNIDAD_DE_MEDIDA_ID = "detalle_sc.unidad_medida_id";
	public static final String DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA = "detalle_sc.unidad_medida_id_pedida";
	public static final String UNIDAD_DE_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA = "unidades_medida_pedida.nombre";
	public static final String DETALLE_SC_IVA = "detalle_sc.iva";
	public static final String DETALLE_SC_DESCUENTO = "detalle_sc.descuento";
	public static final String DETALLE_SC_ARTICULO_ID_SOLICITADO = "detalle_sc.articulo_id_solicitado";

	// $CUSTOMVARS$
	public static final String TAREA_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DETALLE_SC_MONTO_TOTAL = "monto_total";
	public static final String CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String CENTRO_COSTO_NOMBRE = "centro_costo.nombre";
	public static final String SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String SOLICITUDES_COMPRA_FECHA_SOLICITUD = "solicitudes_compra.fecha_solicitud";
	public static final String SOLICITUDES_COMPRA_DESCRIPCION = "solicitudes_compra.descripcion";

	public static final String WEBSITE_USER_NOMBRE_SOLICITANTE = "nombre_completo_solicitante";
	public static final String SOLICITUDES_COMPRA_USER_ID_SOLICITA = "solicitudes_compra.user_id_solicita";
	public static final String DETALLE_SC_MONTO_TOTAL_PEDIDO = "monto_total_pedido";
	public static final String DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO = "monto_total_neto_pedido";
	public static final String DETALLE_SC_MONTO_UNITARIO_PAM = "monto_unitario_pam";

	public static final String ORDENES_COMPRA_ESTADO = "ordenes_compra.estado";
	public static final String ORDENES_COMPRA_ESTADO_NOMBRE = "oc_estado_nombre";

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
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida_pedida");

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
			addColumn(computeTableName("detalle_sc"), "cotizacion_compra_id",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_COTIZACION_COMPRA_ID);
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
			addColumn(computeTableName("articulos"), "descripcion_completa",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION_COMPLETA);
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
			addColumn(computeTableName("detalle_sc"),
					"unidad_medida_id_pedida", DataStore.DATATYPE_INT, false,
					true, DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA);
			addColumn(computeTableName("unidades_medida_pedida"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA);
			addColumn(computeTableName("detalle_sc"), "articulo_id_solicitado",
					DataStore.DATATYPE_INT, false, true,
					DETALLE_SC_ARTICULO_ID_SOLICITADO);

			// add bucket
			addBucket(DETALLE_SC_MONTO_TOTAL, DataStore.DATATYPE_FLOAT);

			// add joins
			addJoin(computeTableAndFieldName("detalle_sc.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);
			addJoin(
					computeTableAndFieldName("articulos.clase_articulo_id"),
					computeTableAndFieldName("clase_articulo.clase_articulo_id"),
					true);
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
			addJoin(
					computeTableAndFieldName("detalle_sc.unidad_medida_id_pedida"),
					computeTableAndFieldName("unidades_medida_pedida.unidad_medida_id"),
					true);

			// add validations
			addRequiredRule(ARTICULOS_NOMBRE,
					"Especifique el código del artículo.");
			addRequiredRule(DETALLE_SC_CANTIDAD_SOLICITADA,
					"Especifique la cantidad a pedir del artículo.");

			// add lookups
			addLookupRule(
					DETALLE_SC_ARTICULO_ID,
					"inventario.articulos",
					"'inventario.articulos.articulo_id = ' + detalle_sc.articulo_id",
					"descripcion_completa", ARTICULOS_DESCRIPCION_COMPLETA,
					"Articulo inexistente");
			addLookupRule(
					DETALLE_SC_ARTICULO_ID,
					"inventario.articulos",
					"'inventario.articulos.articulo_id = ' + detalle_sc.articulo_id",
					"descripcion", ARTICULOS_DESCRIPCION,
					"Articulo inexistente");
			addLookupRule(
					DETALLE_SC_TAREA_ID,
					"proyectos.tareas_proyecto",
					"'proyectos.tareas_proyecto.tarea_id = ' + detalle_sc.tarea_id",
					"nombre", TAREA_PROYECTO_NOMBRE, "Tarea inexistente");
			addLookupRule(
					DETALLE_SC_SOLICITUD_COMPRA_ID,
					"solicitudes_compra",
					"'solicitudes_compra.solicitud_compra_id = ' + detalle_sc.solicitud_compra_id",
					"estado", SOLICITUDES_COMPRA_ESTADO,
					"Solicitud de materiales inexistente");
			addLookupRule(
					DETALLE_SC_UNIDAD_DE_MEDIDA_ID,
					"unidades_medida",
					"'unidades_medida.unidad_medida_id = ' + detalle_sc.unidad_medida_id",
					"nombre", UNIDAD_DE_MEDIDA_NOMBRE,
					"Unidad de medida inexistente");
			addLookupRule(
					DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA,
					"unidades_medida",
					"'unidades_medida.unidad_medida_id = ' + detalle_sc.unidad_medida_id_pedida",
					"nombre", UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA,
					"Unidad de medida pedida en la OC inexistente");

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
		addTableAlias(computeTableName("infraestructura.website_user"),
				"website_user_solicitante");
		addTableAlias(computeTableName("infraestructura.estados"), "estados");

		addColumn(computeTableName("proyectos"), "nombre",
				DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
		addColumn(computeTableName("centro_costo"), "nombre",
				DataStore.DATATYPE_STRING, false, false, CENTRO_COSTO_NOMBRE);
		addColumn(computeTableName("solicitudes_compra"), "fecha_aprobacion",
				DataStore.DATATYPE_DATE, false, false,
				SOLICITUDES_COMPRA_FECHA_APROBACION);
		addColumn(computeTableName("solicitudes_compra"), "descripcion",
				DataStore.DATATYPE_STRING, false, false,
				SOLICITUDES_COMPRA_DESCRIPCION);
		addColumn(computeTableName("proyectos"), "proyecto",
				DataStore.DATATYPE_STRING, false, false, PROYECTOS_PROYECTO);
		addColumn(computeTableName("website_user_solicitante"),
				"nombre_completo", DataStore.DATATYPE_STRING, false, false,
				WEBSITE_USER_NOMBRE_SOLICITANTE);
		addColumn(computeTableName("solicitudes_compra"), "user_id_solicita",
				DataStore.DATATYPE_INT, false, false,
				SOLICITUDES_COMPRA_USER_ID_SOLICITA);
		addColumn(computeTableName("detalle_sc"), "observaciones_oc",
				DataStore.DATATYPE_STRING, false, true,
				DETALLE_SC_OBSERVACIONES_OC);
		addColumn(computeTableName("solicitudes_compra"), "fecha_solicitud",
				DataStore.DATATYPE_DATE, false, false,
				SOLICITUDES_COMPRA_FECHA_SOLICITUD);
		addColumn(computeTableName("detalle_sc"), "iva",
				DataStore.DATATYPE_FLOAT, false, true, DETALLE_SC_IVA);
		addColumn(computeTableName("detalle_sc"), "descuento",
				DataStore.DATATYPE_FLOAT, false, true, DETALLE_SC_DESCUENTO);
		addColumn(computeTableName("ordenes_compra"), "estado",
				DataStore.DATATYPE_STRING, false, false, ORDENES_COMPRA_ESTADO);
		addColumn(computeTableName("estados"), "nombre",
				DataStore.DATATYPE_STRING, false, false,
				ORDENES_COMPRA_ESTADO_NOMBRE);

		// add bucket
		addBucket(DETALLE_SC_MONTO_TOTAL_PEDIDO, DataStore.DATATYPE_FLOAT);
		addBucket(DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO, DataStore.DATATYPE_FLOAT);
		addBucket(DETALLE_SC_MONTO_UNITARIO_PAM, DataStore.DATATYPE_FLOAT);

		addJoin(computeTableAndFieldName("solicitudes_compra.proyecto_id"),
				computeTableAndFieldName("proyectos.proyecto_id"), true);
		addJoin(computeTableAndFieldName("solicitudes_compra.centro_costo_id"),
				computeTableAndFieldName("centro_costo.centro_costo_id"), true);
		addJoin(computeTableAndFieldName("detalle_sc.orden_compra_id"),
				computeTableAndFieldName("ordenes_compra.orden_compra_id"),
				true);
		addJoin(
				computeTableAndFieldName("solicitudes_compra.user_id_solicita"),
				computeTableAndFieldName("website_user_solicitante.user_id"),
				true);
		addJoin(computeTableAndFieldName("ordenes_compra.estado"),
				computeTableAndFieldName("estados.estado"), true);

		try {
			addLookupRule(
					SOLICITUDES_COMPRA_USER_ID_SOLICITA,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + solicitudes_compra.user_id_solicita",
					"nombre_completo", WEBSITE_USER_NOMBRE_SOLICITANTE,
					"Usuario inexistente");
			addLookupRule(
					ORDENES_COMPRA_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = ' + ordenes_compra.estado",
					"nombre", ORDENES_COMPRA_ESTADO_NOMBRE,
					"Estado inexistente");

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
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
	 * Retrieve the value of the detalle_sc.Cotizacion_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScCotizacionCompraId() throws DataStoreException {
		return getInt(DETALLE_SC_COTIZACION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the detalle_sc.Cotizacion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScCotizacionCompraId(int row)
			throws DataStoreException {
		return getInt(row, DETALLE_SC_COTIZACION_COMPRA_ID);
	}

	/**
	 * Set the value of the detalle_sc.Cotizacion_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCotizacionCompraId(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_COTIZACION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the detalle_sc.Cotizacion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScCotizacionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_COTIZACION_COMPRA_ID, newValue);
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
	public Float getDetalleScCantidadSolicitada() throws DataStoreException {
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
	public Float getDetalleScCantidadSolicitada(int row)
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
	public Float getDetalleScCantidadPedida() throws DataStoreException {
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
	public Float getDetalleScCantidadPedida(int row) throws DataStoreException {
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
	 * Retrieve the value of the articulos.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION_COMPLETA);
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
	public String getArticulosDescripcionCompleta(int row)
			throws DataStoreException, SQLException {
		DBConnection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection("inventario", "inventario");
			st = conn.createStatement();
			rs = st
					.executeQuery("SELECT descripcion_completa FROM articulos WHERE articulo_id ="
							+ getDetalleScArticuloId(row));
			String descripCompleta = null;
			if (rs.first()) {
				descripCompleta = rs.getString(1);
			}
			return descripCompleta;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null) {
				conn.freeConnection();
			}
		}
	}

	/**
	 * Set the value of the articulos.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION_COMPLETA, newValue);
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
	public void setArticulosDescripcionCompleta(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_DESCRIPCION_COMPLETA, newValue);
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

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida_Pedida column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedidaPedida() throws DataStoreException {
		return getInt(DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA);
	}

	/**
	 * Retrieve the value of the detalle_sc.unidad_medida_Pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public int getDetalleScUnidadMedidaPedida(int row)
			throws DataStoreException {
		return getInt(row, DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida_Pedida column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedidaPedida(int newValue)
			throws DataStoreException {
		setInt(DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.unidad_medida_Pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScUnidadMedidaPedida(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_UNIDAD_DE_MEDIDA_ID_PEDIDA, newValue);
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
	public void setMontoTotal() throws DataStoreException, SQLException,
			ParseException {
		setMontoTotal(getRow());
	}

	public void setMontoTotal(int row) throws DataStoreException, SQLException,
			ParseException {
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
			throws DataStoreException, SQLException, ParseException {
		Float monto_unitario = getDetalleScMontoUnitario(row);
		Float cantidad = getDetalleScCantidadSolicitada(row);

		// preciso formatear el total antes de guardarlo, para que no agregue
		// decimales innecesarios, y para que los totales generales luego
		// muestren la suma exacta de cada detalle.
		if (monto_unitario != null && cantidad != null) {
			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setRoundingMode(RoundingMode.HALF_UP);
			DecimalFormatSymbols decimalSymbol = DecimalFormatSymbols
					.getInstance();
			decimalSymbol.setDecimalSeparator('.');
			decimalSymbol.setGroupingSeparator(',');
			format.setDecimalFormatSymbols(decimalSymbol);
			Float total = monto_unitario * cantidad;
			setMontoTotal(row, Float.parseFloat(format.format(total).replace(
					",", "")));
		}
	}

	@Override
	public void update(DBConnection connection, boolean handleTrans)
			throws DataStoreException, SQLException {

		SolicitudCompraModel solicitud = new SolicitudCompraModel("inventario",
				"inventario");

		for (int row = 0; row < getRowCount(); row++) {
			if (!isEnProcesoDeRecepcion(connection, row)) {

				solicitud.retrieve(connection, "solicitud_compra_id = "
						+ getDetalleScSolicitudCompraId(row));
				solicitud.gotoFirst();

				int proyecto_id = solicitud.getSolicitudesCompraProyectoId();

				TareasProyectoModel dsTareas = new TareasProyectoModel("proyectos",
				"proyectos");

				if (proyecto_id != 0) {
					dsTareas.retrieve("tareas_proyecto.proyecto_id = "
							+ proyecto_id);
					dsTareas.gotoFirst();
					setDetalleScTareaId(row, dsTareas.getTareasProyectoTareaId());
				}

				// checks if tarea exist for specified project
				if (dsTareas.estimateRowsRetrieved("tareas_proyecto.proyecto_id = "
						+ proyecto_id + " AND tareas_proyecto.tarea_id = "
						+ getDetalleScTareaId(row)) == 0)
					throw new DataStoreException(
					"La tarea especificada no pertenece al proyecto al cual está imputada la solicitud");

				ArticulosModel articulos;
				// fills detalle_sc.articulo_id field through ArticulosNombre
				if (getArticulosNombre(row) != null) {
					articulos = new ArticulosModel("inventario", "inventario");
					articulos.retrieve(connection, "articulos.nombre LIKE '"
							+ getArticulosNombre(row) + "'");
					if (!articulos.gotoFirst()) {
						DataStoreException ex = new DataStoreException(
						"El código de articulo ingresado no corresponde a ninguno registrado");
						ex.setRowNo(row);
						throw ex;
					}
					;
					setDetalleScArticuloId(row, articulos.getArticulosArticuloId());
				}

				// fills detalle_sc.descripcion with articulos.descripcion_completa
				// if is
				// null
				/*
				 * if (getDetalleScDescripcion(row) == null)
				 * setDetalleScDescripcion(row,
				 * getArticulosDescripcionCompleta(row));
				 */

				// si tiene un oc asignado
				if (getDetalleScOrdenCompraId(row) > 0) {
					if (getDetalleScCantidadPedida(row) <= 0)
						throw new DataStoreException(
						"La cantidad pedida debe ser un número positivo mayor que cero");
					if (getDetalleScDescuento(row) < 0)
						throw new DataStoreException(
						"El descuento debe ser un número positivo mayor o igual que cero");
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

				int unidad_patron = Integer.parseInt(AtributosEntidadModel
						.getValorAtributoObjeto(ARTICULO_UNIDAD_MEDIDA,
								getDetalleScArticuloId(row), "TABLA", "articulos"));

				if (getDetalleScUnidadMedida(row) == 0 && unidad_patron != 0) {
					setDetalleScUnidadMedida(row, unidad_patron);
				}

				if (getDetalleScIva(row) == 0) {
					setDetalleScIva(row, Float.parseFloat(AtributosEntidadModel
							.getValorAtributoObjeto(
									Constants.ARTICULO_IVA_PORCENTAJE,
									getDetalleScArticuloId(row), "TABLA",
							"articulos")));
				}

				try {
					setMontoTotal(row);
					calculaMontoTotalPedido(row);
					calculaMontoTotalNetoPedido(row);
				} catch (ParseException ex) {
					throw new DataStoreException(
					"Error parseando cantidad y monto unitario para calcular el total.");
				}

				/*
				 * Si la SC/SM a la cual esta asignado el detalle es modificable, copiar
				 * el id del articulo seleccionado en el campo articulo_id_solicitado
				 */
				if (solicitud.isModificable()) {
					setDetalleScArticuloIdSolicitado(row, getDetalleScArticuloId(row));
				}
			}
		}

		super.update(connection, handleTrans);
	}

	/**
	 * checks if every detail has monto_unitario filled
	 * 
	 * @param solicitud_id
	 * @return
	 * @throws DataStoreException
	 * @throws SQLException
	 */
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
	 * Set the value of the estado proyectos.nombre for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the estado proyectos.nombre for the specified row.
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
	 * Retrieve the value of the unidades_medida.nombre column for the current
	 * row.
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
	public void setUnidadMedidaNombre(String newValue)
			throws DataStoreException {
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

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadMedidaNombrePedida() throws DataStoreException {
		return getString(UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA);
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
	public String getUnidadMedidaNombrePedida(int row)
			throws DataStoreException {
		return getString(row, UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadMedidaNombrePedida(String newValue)
			throws DataStoreException {
		setString(UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA, newValue);
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
	public void setUnidadMedidaNombrePedida(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDAD_DE_MEDIDA_NOMBRE_PEDIDA, newValue);
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
	 * Set the value of the estado proyectos.proyecto for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the estado proyectos.proyecto for the specified row.
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
	 * Retrieve the value of the detalle_sc.observaciones_oc column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScObservacionesOc() throws DataStoreException {
		return getString(DETALLE_SC_OBSERVACIONES_OC);
	}

	/**
	 * Retrieve the value of the detalle_sc.observaciones_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getDetalleScObservacionesOc(int row)
			throws DataStoreException {
		return getString(row, DETALLE_SC_OBSERVACIONES);
	}

	/**
	 * Set the value of the detalle_sc.observaciones column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScObservacionesOc(String newValue)
			throws DataStoreException {
		setString(DETALLE_SC_OBSERVACIONES_OC, newValue);
	}

	/**
	 * Set the value of the detalle_sc.observaciones_oc column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScObservacionesOc(int row, String newValue)
			throws DataStoreException {
		setString(row, DETALLE_SC_OBSERVACIONES_OC, newValue);
	}

	/**
	 * Retrieve the value of the monto_total_pedido bucket for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotalPedido() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_TOTAL_PEDIDO);
	}

	/**
	 * Retrieve the value of monto_total_pedido bucket for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotalPedido(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_TOTAL_PEDIDO);
	}

	/**
	 * Set the value of the monto_total_pedido bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotalPedido(Float newValue) throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_TOTAL_PEDIDO, newValue);
	}

	/**
	 * Set the value of the monto_total_pedido bucket for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotalPedido(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_TOTAL_PEDIDO, newValue);
	}

	/**
	 * Calculate the value of the monto_total column.
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaMontoTotalPedido() throws DataStoreException,
			SQLException, ParseException {
		calculaMontoTotalPedido(getRow(), null);
	}

	/**
	 * Calculate the value of the monto_total column for the specified column.
	 * 
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaMontoTotalPedido(int row) throws DataStoreException,
			SQLException, ParseException {
		calculaMontoTotalPedido(row, null);
	}

	/**
	 * @param row
	 * @param porc
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaMontoTotalPedido(int row, Float porc)
			throws DataStoreException, SQLException, ParseException {
		Float monto_unitario = getDetalleScMontoUnitario(row);
		Float cantidad = getDetalleScCantidadPedida(row);

		// preciso formatear el total antes de guardarlo, para que no agregue
		// decimales innecesarios, y para que los totales generales luego
		// muestren la suma exacta de cada detalle.
		if (monto_unitario != null && cantidad != null) {
			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setRoundingMode(RoundingMode.HALF_UP);
			DecimalFormatSymbols decimalSymbol = DecimalFormatSymbols
					.getInstance();
			decimalSymbol.setDecimalSeparator('.');
			decimalSymbol.setGroupingSeparator(',');
			format.setDecimalFormatSymbols(decimalSymbol);

			Float total = (monto_unitario * cantidad);
			if ((porc != null) && (porc > 0)) {
				total -= total * (porc / 100);
			} else if (getDetalleScDescuento(row) > 0) {
				total -= total * (getDetalleScDescuento(row) / 100);
			}
			setMontoTotalPedido(row, Float.parseFloat(format.format(total)
					.replace(",", "")));
		}
	}

	/**
	 * checks if every detail has monto_unitario filled
	 * 
	 * @param solicitud_id
	 * @return
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public boolean chequeaTotalesDetallesOrden(int orden_id)
			throws DataStoreException, SQLException {
		retrieve("detalle_sc.orden_compra_id = " + orden_id);
		waitForRetrieve();
		for (int row = 0; row < getRowCount(); row++) {
			if (getDetalleScMontoUnitario(row) == 0)
				return false;
		}
		return true;
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
	 * Filtra el datastore obteniendo los detalles a remover del OC actual,
	 * elimina el fk al OC y resetea el campo cantidad pedida.
	 * 
	 * @param conn
	 *            La conexión en la que se enmarca la transacción
	 * @param bucketName
	 *            nombre del bucket que contiene el flag de seleccion
	 * @return true si han sido eliminado algun detalle
	 * @throws DataStoreException
	 */
	public boolean eliminaDetallesSeleccionados(DBConnection conn,
			String bucketName) throws DataStoreException {
		// filtramos detalles marcados para remoción
		filter(bucketName + " == 1");

		boolean detalleEliminado = getRowCount() > 0 ? true : false;
		int row = 0;

		try {
			for (row = 0; row < getRowCount(); row++) {
				setAny(row, DetalleSCModel.DETALLE_SC_CANTIDAD_PEDIDA,
						getNullDefault(DataStore.DATATYPE_INT));
				setAny(row, DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID,
						getNullDefault(DataStore.DATATYPE_INT));
			}
			return detalleEliminado;

		} catch (DataStoreException ex) {
			undoChanges(row);
			setInt(row, bucketName, 0);
			throw new DataStoreException(
					"No se ha podido remover el artículo seleccionado. Error: "
							+ ex.getMessage());
		} finally {
			// removemos el filtro
			filter(null);
		}
	}

	/**
	 * Retrieve the value of the detalle_sc.iva column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public float getDetalleScIva() throws DataStoreException {
		return getFloat(DETALLE_SC_IVA);
	}

	/**
	 * Retrieve the value of the detalle_sc.iva column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public float getDetalleScIva(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_IVA);
	}

	/**
	 * Set the value of the detalle_sc.iva column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScIva(float newValue) throws DataStoreException {
		setFloat(DETALLE_SC_IVA, newValue);
	}

	/**
	 * Set the value of the detalle_sc.iva column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScIva(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_IVA, newValue);
	}

	/**
	 * Retrieve the value of the detalle_sc.descuento column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public float getDetalleScDescuento() throws DataStoreException {
		return getFloat(DETALLE_SC_DESCUENTO);
	}

	/**
	 * Retrieve the value of the detalle_sc.descuento column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public float getDetalleScDescuento(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_DESCUENTO);
	}

	/**
	 * Set the value of the detalle_sc.descuento column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDescuento(float newValue) throws DataStoreException {
		setFloat(DETALLE_SC_DESCUENTO, newValue);
	}

	/**
	 * Set the value of the detalle_sc.descuento column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScDescuento(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_DESCUENTO, newValue);
	}

	/**
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaMontoTotalNetoPedido(int row) throws DataStoreException,
			SQLException, ParseException {
		Float monto_unitario = getDetalleScMontoUnitario(row);
		Float cantidad = getDetalleScCantidadPedida(row);

		// preciso formatear el total antes de guardarlo, para que no agregue
		// decimales innecesarios, y para que los totales generales luego
		// muestren la suma exacta de cada detalle.
		if (monto_unitario != null && cantidad != null) {
			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setRoundingMode(RoundingMode.HALF_UP);
			DecimalFormatSymbols decimalSymbol = DecimalFormatSymbols
					.getInstance();
			decimalSymbol.setDecimalSeparator('.');
			decimalSymbol.setGroupingSeparator(',');
			format.setDecimalFormatSymbols(decimalSymbol);
			Float total = (monto_unitario * cantidad);
			setMontoTotalPedidoNeto(row, Float.parseFloat(format.format(total)
					.replace(",", "")));
		}

	}

	/**
	 * Retrieve the value of the monto_total_pedido_neto bucket for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotalPedidoNeto() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO);
	}

	/**
	 * Retrieve the value of monto_total_pedido_neto bucket for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoTotalNetoPedido(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO);
	}

	/**
	 * Set the value of the monto_total_pedido_neto bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotalPedidoNeto(Float newValue)
			throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO, newValue);
	}

	/**
	 * Set the value of the monto_total_pedido_neto bucket for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoTotalPedidoNeto(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_TOTAL_NETO_PEDIDO, newValue);
	}

	/**
	 * Retrieve the value of the ordenes_compra.estado column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getOrdenesCompraEstado() throws DataStoreException {
		return getString(ORDENES_COMPRA_ESTADO);
	}

	/**
	 * Retrieve the value of the ordenes_compra.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getOrdenesCompraEstado(int row) throws DataStoreException {
		return getString(row, ORDENES_COMPRA_ESTADO);
	}

	/**
	 * Set the value of the ordenes_compra.estado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraEstado(String newValue)
			throws DataStoreException {
		setString(ORDENES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Set the value of the ordenes_compra.estado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, ORDENES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the oc_estado_nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getOrdenesCompraEstadoNombre() throws DataStoreException {
		return getString(ORDENES_COMPRA_ESTADO_NOMBRE);
	}

	/**
	 * Retrieve the value of the oc_estado_nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getOrdenesCompraEstadoNombre(int row)
			throws DataStoreException {
		return getString(row, ORDENES_COMPRA_ESTADO_NOMBRE);
	}

	/**
	 * Set the value of the oc_estado_nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraEstadoNombre(String newValue)
			throws DataStoreException {
		setString(ORDENES_COMPRA_ESTADO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the oc_estado_nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setOrdenesCompraEstadoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ORDENES_COMPRA_ESTADO_NOMBRE, newValue);
	}

	/**
	 * Calculate the value of the monto_total column.
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaPrecioUnitarioPam() throws DataStoreException,
			SQLException, ParseException {
		calculaPrecioUnitarioPam(getRow(), null);
	}

	/**
	 * Calculate the value of the monto_total column for the specified column.
	 * 
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void calculaPrecioUnitarioPam(int row) throws DataStoreException,
			SQLException, ParseException {
		calculaPrecioUnitarioPam(row, null);
	}

	/**
	 * @param row
	 * @param porc
	 * @throws DataStoreException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculaPrecioUnitarioPam(int row, Float porc)
			throws DataStoreException, SQLException, ParseException {
		Float monto_unitario = getDetalleScMontoUnitario(row);
		Float total = 0F;

		// preciso formatear el total antes de guardarlo, para que no agregue
		// decimales innecesarios, y para que los totales generales luego
		// muestren la suma exacta de cada detalle.
		if (monto_unitario != null) {
			total = monto_unitario;

			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setRoundingMode(RoundingMode.HALF_UP);
			DecimalFormatSymbols decimalSymbol = DecimalFormatSymbols
					.getInstance();
			decimalSymbol.setDecimalSeparator('.');
			decimalSymbol.setGroupingSeparator(',');
			format.setDecimalFormatSymbols(decimalSymbol);

			if ((porc != null) && (porc > 0)) {
				total -= monto_unitario * (porc / 100);
			} else if (getDetalleScDescuento(row) > 0) {
				total -= monto_unitario * (getDetalleScDescuento(row) / 100);
			}
			setMontoUnitarioPam(row, Float.parseFloat(format.format(total)
					.replace(",", "")));
		}
	}

	/**
	 * Retrieve the value of the monto_unitario_pam bucket for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoUnitarioPam() throws DataStoreException {
		return getFloat(DETALLE_SC_MONTO_UNITARIO_PAM);
	}

	/**
	 * Retrieve the value of monto_unitario_pam bucket for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public Float getMontoUnitarioPam(int row) throws DataStoreException {
		return getFloat(row, DETALLE_SC_MONTO_UNITARIO_PAM);
	}

	/**
	 * Set the value of the monto_unitario_pam bucket for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoUnitarioPam(Float newValue) throws DataStoreException {
		setFloat(DETALLE_SC_MONTO_UNITARIO_PAM, newValue);
	}

	/**
	 * Set the value of the monto_unitario_pam bucket for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMontoUnitarioPam(int row, float newValue)
			throws DataStoreException {
		setFloat(row, DETALLE_SC_MONTO_UNITARIO_PAM, newValue);
	}
	
	/**
	 * Retrieve the value of the detalle_sc.articulo_id_solicitado column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScArticuloIdSolicitado() throws DataStoreException {
		return getInt(DETALLE_SC_ARTICULO_ID_SOLICITADO);
	}

	/**
	 * Retrieve the value of the detalle_sc.articulo_id_solicitado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getDetalleScArticuloIdSolicitado(int row) throws DataStoreException {
		return getInt(row, DETALLE_SC_ARTICULO_ID_SOLICITADO);
	}

	/**
	 * Set the value of the detalle_sc.articulo_id_solicitado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScArticuloIdSolicitado(int newValue) throws DataStoreException {
		setInt(DETALLE_SC_ARTICULO_ID_SOLICITADO, newValue);
	}

	/**
	 * Set the value of the detalle_sc.articulo_id_solicitado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setDetalleScArticuloIdSolicitado(int row, int newValue)
			throws DataStoreException {
		setInt(row, DETALLE_SC_ARTICULO_ID_SOLICITADO, newValue);
	}
	
	/**
	 * Chequea si la fila actual se encuentra en proceso de recepcion
	 * @return true si la fila actual se encuentra en proceso de recepcion
	 * @throws DataStoreException 
	 * @throws SQLException 
	 */
	public boolean isEnProcesoDeRecepecion() throws SQLException, DataStoreException {
		return isEnProcesoDeRecepcion(getRow());
	}
	
	/**
	 * Chequea si la fila especificida se encuentra en proceso de recepcion
	 * @param row la fila a chequear
	 * @return true si la fila acutal se encuentra en proceso de recepcion
	 * @throws DataStoreException 
	 * @throws SQLException 
	 * @throws DataStoreException 
	 * @throws SQLException 
	 */
	public boolean isEnProcesoDeRecepcion(int row) throws SQLException, DataStoreException {
		return isEnProcesoDeRecepcion(null, row);
	}
	
	/**
	 * Chequea si la fila especificida se encuentra en proceso de recepcion
	 * @param conn la conexión dentro de la cual se enmarca la transaccion
	 * @param row la fila a chequear
	 * @return true si la fila acutal se encuentra en proceso de recepcion
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	public boolean isEnProcesoDeRecepcion(DBConnection conn, int row) throws SQLException, DataStoreException {
		DetalleRCModel detalleRCModel = new DetalleRCModel(getAppName());
				
		if (conn == null) 
			conn = DBConnection.getConnection(_appName);
		
		// recuperamos todos los detalles de recepcion que esten asociadas al detalle indicado
		if (detalleRCModel.estimateRowsRetrieved(conn, DetalleRCModel.DETALLES_RC_DETALLE_SC_ID + " = " + getDetalleScDetalleScId(row)) == 0) {
			return false;
		} else {			
			return true;
		}		
	}
	// $ENDCUSTOMMETHODS$
}
