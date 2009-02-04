package gerencia.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * MaterialCompradoModel: A SOFIA generated model
 */
public class MaterialCompradoModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3175097337918887325L;
	// constants for columns
	public static final String MATERIAL_COMPRADO_PROYECTO = "material_comprado.proyecto";
	public static final String MATERIAL_COMPRADO_TIPO_PROYECTO = "material_comprado.tipo_proyecto";
	public static final String MATERIAL_COMPRADO_NOMBRE_PROYECTO = "material_comprado.nombre_proyecto";
	public static final String MATERIAL_COMPRADO_DESCRIPCION_PROYECTO = "material_comprado.descripcion_proyecto";
	public static final String MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO = "material_comprado.observacioens_proyecto";
	public static final String MATERIAL_COMPRADO_ESTADO_ID_PROYECTO = "material_comprado.estado_id_proyecto";
	public static final String MATERIAL_COMPRADO_ESTADO_PROYECTO = "material_comprado.estado_proyecto";
	public static final String MATERIAL_COMPRADO_CLIENTE_ID = "material_comprado.cliente_id";
	public static final String MATERIAL_COMPRADO_CLIENTE_CODIGO = "material_comprado.cliente_codigo";
	public static final String MATERIAL_COMPRADO_CLIENTE_NOMBRE = "material_comprado.cliente_nombre";
	public static final String MATERIAL_COMPRADO_CLIENTE_DESCRIPCION = "material_comprado.cliente_descripcion";
	public static final String MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO = "material_comprado.vigencia_desde_proyecto";
	public static final String MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO = "material_comprado.vigencia_hasta_proyecto";
	public static final String MATERIAL_COMPRADO_DURACION_PROYECTO = "material_comprado.duracion_proyecto";
	public static final String MATERIAL_COMPRADO_TAREA_ID = "material_comprado.tarea_id";
	public static final String MATERIAL_COMPRADO_NOMBRE_TAREA = "material_comprado.nombre_tarea";
	public static final String MATERIAL_COMPRADO_DESCRIPCION_TAREA = "material_comprado.descripcion_tarea";
	public static final String MATERIAL_COMPRADO_OBSERVACIONES_TAREA = "material_comprado.observaciones_tarea";
	public static final String MATERIAL_COMPRADO_ESTADO_ID_TAREA = "material_comprado.estado_id_tarea";
	public static final String MATERIAL_COMPRADO_ESTADO_TAREA = "material_comprado.estado_tarea";
	public static final String MATERIAL_COMPRADO_CLASE_TAREA_ID = "material_comprado.clase_tarea_id";
	public static final String MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE = "material_comprado.clase_tarea_nombre";
	public static final String MATERIAL_COMPRADO_DETALLE_SC_ID = "material_comprado.detalle_SC_id";
	public static final String MATERIAL_COMPRADO_ARTICULO_ID = "material_comprado.articulo_id";
	public static final String MATERIAL_COMPRADO_ARTICULO_NOMBRE = "material_comprado.articulo_nombre";
	public static final String MATERIAL_COMPRADO_ARTICULO_DESCRIPCION = "material_comprado.articulo_descripcion";
	public static final String MATERIAL_COMPRADO_ARTICULO_CLASE = "material_comprado.articulo_clase";
	public static final String MATERIAL_COMPRADO_DESCRIPCION = "material_comprado.descripcion";
	public static final String MATERIAL_COMPRADO_OBSERVACIONES = "material_comprado.observaciones";
	public static final String MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO = "material_comprado.articulo_id_solicitado";
	public static final String MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL = "material_comprado.articulo_nombre_sol";
	public static final String MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL = "material_comprado.articulo_descripcion_sol";
	public static final String MATERIAL_COMPRADO_ARTICULO_CLASE_SOL = "material_comprado.articulo_clase_sol";
	public static final String MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID = "material_comprado.solicitud_compra_id";
	public static final String MATERIAL_COMPRADO_SOLICITANTE_ID = "material_comprado.solicitante_id";
	public static final String MATERIAL_COMPRADO_SOLICITANTE_LEGAJO = "material_comprado.solicitante_legajo";
	public static final String MATERIAL_COMPRADO_SOLICITANTE_NOMBRE = "material_comprado.solicitante_nombre";
	public static final String MATERIAL_COMPRADO_ESTADO_ID_SM = "material_comprado.estado_id_sm";
	public static final String MATERIAL_COMPRADO_ESTADO_SM = "material_comprado.estado_sm";
	public static final String MATERIAL_COMPRADO_FECHA_SOLICITUD = "material_comprado.fecha_solicitud";
	public static final String MATERIAL_COMPRADO_FECHA_APROBACION_SM = "material_comprado.fecha_aprobacion_sm";
	public static final String MATERIAL_COMPRADO_ORDEN_COMPRA_ID = "material_comprado.orden_compra_id";
	public static final String MATERIAL_COMPRADO_PROVEEDOR_ID = "material_comprado.proveedor_id";
	public static final String MATERIAL_COMPRADO_PROVEEDOR_CODIGO = "material_comprado.proveedor_codigo";
	public static final String MATERIAL_COMPRADO_PROVEEDOR_NOMBRE = "material_comprado.proveedor_nombre";
	public static final String MATERIAL_COMPRADO_COMPRADOR_ID = "material_comprado.comprador_id";
	public static final String MATERIAL_COMPRADO_COMPRADOR_LEGAJO = "material_comprado.comprador_legajo";
	public static final String MATERIAL_COMPRADO_COMPRADOR_NOMBRE = "material_comprado.comprador_nombre";
	public static final String MATERIAL_COMPRADO_ESTADO_ID_OC = "material_comprado.estado_id_oc";
	public static final String MATERIAL_COMPRADO_ESTADO_OC = "material_comprado.estado_oc";
	public static final String MATERIAL_COMPRADO_FECHA_OC = "material_comprado.fecha_oc";
	public static final String MATERIAL_COMPRADO_FECHA_APROBACION_OC = "material_comprado.fecha_aprobacion_oc";
	public static final String MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA = "material_comprado.fecha_estimada_entrega";
	public static final String MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA = "material_comprado.fecha_entrega_completa";
	public static final String MATERIAL_COMPRADO_DESCRIPCION_OC = "material_comprado.descripcion_oc";
	public static final String MATERIAL_COMPRADO_OBSERVACIONES_OC = "material_comprado.observaciones_oc";
	public static final String MATERIAL_COMPRADO_OBSERVACIONES2_OC = "material_comprado.observaciones2_oc";
	public static final String MATERIAL_COMPRADO_CONDICION_COMPRA_ID = "material_comprado.condicion_compra_id";
	public static final String MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO = "material_comprado.condicion_compra_codigo";
	public static final String MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION = "material_comprado.condicion_compra_descripcion";
	public static final String MATERIAL_COMPRADO_DESCUENTO_OC = "material_comprado.descuento_oc";
	public static final String MATERIAL_COMPRADO_CANTIDAD_SOLICITADA = "material_comprado.cantidad_solicitada";
	public static final String MATERIAL_COMPRADO_CANTIDAD_PEDIDA = "material_comprado.cantidad_pedida";
	public static final String MATERIAL_COMPRADO_CANTIDAD_RECIBIDA = "material_comprado.cantidad_recibida";
	public static final String MATERIAL_COMPRADO_MONTO_UNITARIO = "material_comprado.monto_unitario";
	public static final String MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA = "material_comprado.monto_ultima_compra";
	public static final String MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA = "material_comprado.fecha_ultima_compra";
	public static final String MATERIAL_COMPRADO_UM_ID = "material_comprado.um_id";
	public static final String MATERIAL_COMPRADO_UM = "material_comprado.um";
	public static final String MATERIAL_COMPRADO_UM_DESCRIPCION = "material_comprado.um_descripcion";
	public static final String MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA = "material_comprado.unidad_medida_id_pedida";
	public static final String MATERIAL_COMPRADO_IVA = "material_comprado.iva";
	public static final String MATERIAL_COMPRADO_DESCUENTO = "material_comprado.descuento";
	public static final String MATERIAL_COMPRADO_COTIZACION_COMPRA_ID = "material_comprado.cotizacion_compra_id";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new MaterialCompradoModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 */
	public MaterialCompradoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new MaterialCompradoModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 * @param profile
	 *           The database profile to use
	 */
	public MaterialCompradoModel(String appName, String profile) {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("material_comprado"), "material_comprado");

		// add columns
		addColumn(computeTableName("material_comprado"), "proyecto",
				DataStore.DATATYPE_STRING, false, false, MATERIAL_COMPRADO_PROYECTO);
		addColumn(computeTableName("material_comprado"), "tipo_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_TIPO_PROYECTO);
		addColumn(computeTableName("material_comprado"), "nombre_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_NOMBRE_PROYECTO);
		addColumn(computeTableName("material_comprado"), "descripcion_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_DESCRIPCION_PROYECTO);
		addColumn(computeTableName("material_comprado"),
				"observacioens_proyecto", DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO);
		addColumn(computeTableName("material_comprado"), "estado_id_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_ID_PROYECTO);
		addColumn(computeTableName("material_comprado"), "estado_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_PROYECTO);
		addColumn(computeTableName("material_comprado"), "cliente_id",
				DataStore.DATATYPE_INT, false, false, MATERIAL_COMPRADO_CLIENTE_ID);
		addColumn(computeTableName("material_comprado"), "cliente_codigo",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_CLIENTE_CODIGO);
		addColumn(computeTableName("material_comprado"), "cliente_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_CLIENTE_NOMBRE);
		addColumn(computeTableName("material_comprado"), "cliente_descripcion",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_CLIENTE_DESCRIPCION);
		addColumn(computeTableName("material_comprado"),
				"vigencia_desde_proyecto", DataStore.DATATYPE_DATE, false, false,
				MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO);
		addColumn(computeTableName("material_comprado"),
				"vigencia_hasta_proyecto", DataStore.DATATYPE_DATE, false, false,
				MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO);
		addColumn(computeTableName("material_comprado"), "duracion_proyecto",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_DURACION_PROYECTO);
		addColumn(computeTableName("material_comprado"), "tarea_id",
				DataStore.DATATYPE_INT, false, false, MATERIAL_COMPRADO_TAREA_ID);
		addColumn(computeTableName("material_comprado"), "nombre_tarea",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_NOMBRE_TAREA);
		addColumn(computeTableName("material_comprado"), "descripcion_tarea",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_DESCRIPCION_TAREA);
		addColumn(computeTableName("material_comprado"), "observaciones_tarea",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_OBSERVACIONES_TAREA);
		addColumn(computeTableName("material_comprado"), "estado_id_tarea",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_ID_TAREA);
		addColumn(computeTableName("material_comprado"), "estado_tarea",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_TAREA);
		addColumn(computeTableName("material_comprado"), "clase_tarea_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_CLASE_TAREA_ID);
		addColumn(computeTableName("material_comprado"), "clase_tarea_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE);
		addColumn(computeTableName("material_comprado"), "detalle_SC_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_DETALLE_SC_ID);
		addColumn(computeTableName("material_comprado"), "articulo_id",
				DataStore.DATATYPE_INT, false, false, MATERIAL_COMPRADO_ARTICULO_ID);
		addColumn(computeTableName("material_comprado"), "articulo_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ARTICULO_NOMBRE);
		addColumn(computeTableName("material_comprado"), "articulo_descripcion",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ARTICULO_DESCRIPCION);
		addColumn(computeTableName("material_comprado"), "articulo_clase",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ARTICULO_CLASE);
		addColumn(computeTableName("material_comprado"), "descripcion",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_DESCRIPCION);
		addColumn(computeTableName("material_comprado"), "observaciones",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_OBSERVACIONES);
		addColumn(computeTableName("material_comprado"),
				"articulo_id_solicitado", DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO);
		addColumn(computeTableName("material_comprado"), "articulo_nombre_sol",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL);
		addColumn(computeTableName("material_comprado"),
				"articulo_descripcion_sol", DataStore.DATATYPE_STRING, false,
				false, MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL);
		addColumn(computeTableName("material_comprado"), "articulo_clase_sol",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ARTICULO_CLASE_SOL);
		addColumn(computeTableName("material_comprado"), "solicitud_compra_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID);
		addColumn(computeTableName("material_comprado"), "solicitante_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_SOLICITANTE_ID);
		addColumn(computeTableName("material_comprado"), "solicitante_legajo",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_SOLICITANTE_LEGAJO);
		addColumn(computeTableName("material_comprado"), "solicitante_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_SOLICITANTE_NOMBRE);
		addColumn(computeTableName("material_comprado"), "estado_id_sm",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_ID_SM);
		addColumn(computeTableName("material_comprado"), "estado_sm",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_SM);
		addColumn(computeTableName("material_comprado"), "fecha_solicitud",
				DataStore.DATATYPE_DATETIME, false, false,
				MATERIAL_COMPRADO_FECHA_SOLICITUD);
		addColumn(computeTableName("material_comprado"), "fecha_aprobacion_sm",
				DataStore.DATATYPE_DATETIME, false, false,
				MATERIAL_COMPRADO_FECHA_APROBACION_SM);
		addColumn(computeTableName("material_comprado"), "orden_compra_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_ORDEN_COMPRA_ID);
		addColumn(computeTableName("material_comprado"), "proveedor_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_PROVEEDOR_ID);
		addColumn(computeTableName("material_comprado"), "proveedor_codigo",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_PROVEEDOR_CODIGO);
		addColumn(computeTableName("material_comprado"), "proveedor_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_PROVEEDOR_NOMBRE);
		addColumn(computeTableName("material_comprado"), "comprador_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_COMPRADOR_ID);
		addColumn(computeTableName("material_comprado"), "comprador_legajo",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_COMPRADOR_LEGAJO);
		addColumn(computeTableName("material_comprado"), "comprador_nombre",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_COMPRADOR_NOMBRE);
		addColumn(computeTableName("material_comprado"), "estado_id_oc",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_ID_OC);
		addColumn(computeTableName("material_comprado"), "estado_oc",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_ESTADO_OC);
		addColumn(computeTableName("material_comprado"), "fecha_oc",
				DataStore.DATATYPE_DATETIME, false, false,
				MATERIAL_COMPRADO_FECHA_OC);
		addColumn(computeTableName("material_comprado"), "fecha_aprobacion_oc",
				DataStore.DATATYPE_DATETIME, false, false,
				MATERIAL_COMPRADO_FECHA_APROBACION_OC);
		addColumn(computeTableName("material_comprado"),
				"fecha_estimada_entrega", DataStore.DATATYPE_DATE, false, false,
				MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA);
		addColumn(computeTableName("material_comprado"),
				"fecha_entrega_completa", DataStore.DATATYPE_DATE, false, false,
				MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA);
		addColumn(computeTableName("material_comprado"), "descripcion_oc",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_DESCRIPCION_OC);
		addColumn(computeTableName("material_comprado"), "observaciones_oc",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_OBSERVACIONES_OC);
		addColumn(computeTableName("material_comprado"), "observaciones2_oc",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_OBSERVACIONES2_OC);
		addColumn(computeTableName("material_comprado"), "condicion_compra_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_CONDICION_COMPRA_ID);
		addColumn(computeTableName("material_comprado"),
				"condicion_compra_codigo", DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO);
		addColumn(computeTableName("material_comprado"),
				"condicion_compra_descripcion", DataStore.DATATYPE_STRING, false,
				false, MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION);
		addColumn(computeTableName("material_comprado"), "descuento_oc",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_DESCUENTO_OC);
		addColumn(computeTableName("material_comprado"), "cantidad_solicitada",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_CANTIDAD_SOLICITADA);
		addColumn(computeTableName("material_comprado"), "cantidad_pedida",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_CANTIDAD_PEDIDA);
		addColumn(computeTableName("material_comprado"), "cantidad_recibida",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_CANTIDAD_RECIBIDA);
		addColumn(computeTableName("material_comprado"), "monto_unitario",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_MONTO_UNITARIO);
		addColumn(computeTableName("material_comprado"), "monto_ultima_compra",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA);
		addColumn(computeTableName("material_comprado"), "fecha_ultima_compra",
				DataStore.DATATYPE_DATE, false, false,
				MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA);
		addColumn(computeTableName("material_comprado"), "um_id",
				DataStore.DATATYPE_INT, false, false, MATERIAL_COMPRADO_UM_ID);
		addColumn(computeTableName("material_comprado"), "um",
				DataStore.DATATYPE_STRING, false, false, MATERIAL_COMPRADO_UM);
		addColumn(computeTableName("material_comprado"), "um_descripcion",
				DataStore.DATATYPE_STRING, false, false,
				MATERIAL_COMPRADO_UM_DESCRIPCION);
		addColumn(computeTableName("material_comprado"),
				"unidad_medida_id_pedida", DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA);
		addColumn(computeTableName("material_comprado"), "iva",
				DataStore.DATATYPE_DOUBLE, false, false, MATERIAL_COMPRADO_IVA);
		addColumn(computeTableName("material_comprado"), "descuento",
				DataStore.DATATYPE_DOUBLE, false, false,
				MATERIAL_COMPRADO_DESCUENTO);
		addColumn(computeTableName("material_comprado"), "cotizacion_compra_id",
				DataStore.DATATYPE_INT, false, false,
				MATERIAL_COMPRADO_COTIZACION_COMPRA_ID);

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the material_comprado.proyecto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProyecto() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProyecto(int row) throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.proyecto column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.proyecto column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.tipo_proyecto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoTipoProyecto() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_TIPO_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.tipo_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoTipoProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_TIPO_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.tipo_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoTipoProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_TIPO_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.tipo_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoTipoProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_TIPO_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.nombre_proyecto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoNombreProyecto() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_NOMBRE_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.nombre_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoNombreProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_NOMBRE_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.nombre_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoNombreProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_NOMBRE_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.nombre_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoNombreProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_NOMBRE_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_proyecto column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionProyecto()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_DESCRIPCION_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_proyecto column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_DESCRIPCION_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.descripcion_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_DESCRIPCION_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.descripcion_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_DESCRIPCION_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.observacioens_proyecto column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacioensProyecto()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.observacioens_proyecto column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacioensProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.observacioens_proyecto column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacioensProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.observacioens_proyecto column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacioensProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_OBSERVACIOENS_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_proyecto column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdProyecto()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_ID_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_proyecto column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_ID_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.estado_id_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_ID_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_id_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_ID_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_proyecto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoProyecto() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.estado_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoClienteId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_CLIENTE_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoClienteId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_CLIENTE_ID);
	}

	/**
	 * Set the value of the material_comprado.cliente_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_CLIENTE_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.cliente_id column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_CLIENTE_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_codigo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteCodigo() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CLIENTE_CODIGO);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_codigo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteCodigo(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CLIENTE_CODIGO);
	}

	/**
	 * Set the value of the material_comprado.cliente_codigo column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteCodigo(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CLIENTE_CODIGO, newValue);
	}

	/**
	 * Set the value of the material_comprado.cliente_codigo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteCodigo(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CLIENTE_CODIGO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteNombre() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CLIENTE_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CLIENTE_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.cliente_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CLIENTE_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.cliente_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CLIENTE_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_descripcion column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteDescripcion()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CLIENTE_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the material_comprado.cliente_descripcion column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClienteDescripcion(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CLIENTE_DESCRIPCION);
	}

	/**
	 * Set the value of the material_comprado.cliente_descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteDescripcion(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CLIENTE_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the material_comprado.cliente_descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClienteDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CLIENTE_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.vigencia_desde_proyecto column
	 * for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoVigenciaDesdeProyecto()
			throws DataStoreException {
		return getDate(MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.vigencia_desde_proyecto column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoVigenciaDesdeProyecto(int row)
			throws DataStoreException {
		return getDate(row, MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.vigencia_desde_proyecto column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoVigenciaDesdeProyecto(java.sql.Date newValue)
			throws DataStoreException {
		setDate(MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.vigencia_desde_proyecto column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoVigenciaDesdeProyecto(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, MATERIAL_COMPRADO_VIGENCIA_DESDE_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.vigencia_hasta_proyecto column
	 * for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoVigenciaHastaProyecto()
			throws DataStoreException {
		return getDate(MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.vigencia_hasta_proyecto column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoVigenciaHastaProyecto(int row)
			throws DataStoreException {
		return getDate(row, MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.vigencia_hasta_proyecto column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoVigenciaHastaProyecto(java.sql.Date newValue)
			throws DataStoreException {
		setDate(MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.vigencia_hasta_proyecto column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoVigenciaHastaProyecto(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, MATERIAL_COMPRADO_VIGENCIA_HASTA_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.duracion_proyecto column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDuracionProyecto()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_DURACION_PROYECTO);
	}

	/**
	 * Retrieve the value of the material_comprado.duracion_proyecto column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDuracionProyecto(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_DURACION_PROYECTO);
	}

	/**
	 * Set the value of the material_comprado.duracion_proyecto column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDuracionProyecto(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_DURACION_PROYECTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.duracion_proyecto column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDuracionProyecto(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_DURACION_PROYECTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.tarea_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoTareaId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_TAREA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoTareaId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_TAREA_ID);
	}

	/**
	 * Set the value of the material_comprado.tarea_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoTareaId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.tarea_id column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.nombre_tarea column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoNombreTarea() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_NOMBRE_TAREA);
	}

	/**
	 * Retrieve the value of the material_comprado.nombre_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoNombreTarea(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_NOMBRE_TAREA);
	}

	/**
	 * Set the value of the material_comprado.nombre_tarea column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoNombreTarea(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_NOMBRE_TAREA, newValue);
	}

	/**
	 * Set the value of the material_comprado.nombre_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoNombreTarea(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_NOMBRE_TAREA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_tarea column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionTarea()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_DESCRIPCION_TAREA);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_tarea column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionTarea(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_DESCRIPCION_TAREA);
	}

	/**
	 * Set the value of the material_comprado.descripcion_tarea column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionTarea(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_DESCRIPCION_TAREA, newValue);
	}

	/**
	 * Set the value of the material_comprado.descripcion_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionTarea(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_DESCRIPCION_TAREA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones_tarea column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacionesTarea()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_OBSERVACIONES_TAREA);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones_tarea column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacionesTarea(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_OBSERVACIONES_TAREA);
	}

	/**
	 * Set the value of the material_comprado.observaciones_tarea column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacionesTarea(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_OBSERVACIONES_TAREA, newValue);
	}

	/**
	 * Set the value of the material_comprado.observaciones_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacionesTarea(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_OBSERVACIONES_TAREA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_tarea column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdTarea() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_ID_TAREA);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdTarea(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_ID_TAREA);
	}

	/**
	 * Set the value of the material_comprado.estado_id_tarea column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdTarea(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_ID_TAREA, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_id_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdTarea(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_ID_TAREA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_tarea column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoTarea() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_TAREA);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoTarea(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_TAREA);
	}

	/**
	 * Set the value of the material_comprado.estado_tarea column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoTarea(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_TAREA, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_tarea column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoTarea(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_TAREA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.clase_tarea_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoClaseTareaId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_CLASE_TAREA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoClaseTareaId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_CLASE_TAREA_ID);
	}

	/**
	 * Set the value of the material_comprado.clase_tarea_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClaseTareaId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.clase_tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClaseTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_CLASE_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.clase_tarea_nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClaseTareaNombre()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.clase_tarea_nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoClaseTareaNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.clase_tarea_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClaseTareaNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.clase_tarea_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoClaseTareaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CLASE_TAREA_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.detalle_SC_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoDetalleScId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_DETALLE_SC_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.detalle_SC_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoDetalleScId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_DETALLE_SC_ID);
	}

	/**
	 * Set the value of the material_comprado.detalle_SC_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDetalleScId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_DETALLE_SC_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.detalle_SC_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDetalleScId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_DETALLE_SC_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoArticuloId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoArticuloId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_ARTICULO_ID);
	}

	/**
	 * Set the value of the material_comprado.articulo_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloNombre() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.articulo_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_descripcion column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloDescripcion()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_descripcion column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloDescripcion(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_DESCRIPCION);
	}

	/**
	 * Set the value of the material_comprado.articulo_descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloDescripcion(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_clase column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloClase() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_CLASE);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_clase column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloClase(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_CLASE);
	}

	/**
	 * Set the value of the material_comprado.articulo_clase column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloClase(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_CLASE, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_clase column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloClase(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_CLASE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcion() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcion(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_DESCRIPCION);
	}

	/**
	 * Set the value of the material_comprado.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcion(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the material_comprado.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservaciones() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservaciones(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_OBSERVACIONES);
	}

	/**
	 * Set the value of the material_comprado.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservaciones(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the material_comprado.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_id_solicitado column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoArticuloIdSolicitado()
			throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_id_solicitado column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoArticuloIdSolicitado(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO);
	}

	/**
	 * Set the value of the material_comprado.articulo_id_solicitado column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloIdSolicitado(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_id_solicitado column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloIdSolicitado(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_ARTICULO_ID_SOLICITADO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_nombre_sol column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloNombreSol()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_nombre_sol column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloNombreSol(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL);
	}

	/**
	 * Set the value of the material_comprado.articulo_nombre_sol column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloNombreSol(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_nombre_sol column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloNombreSol(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_NOMBRE_SOL, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_descripcion_sol
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloDescripcionSol()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_descripcion_sol
	 * column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloDescripcionSol(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL);
	}

	/**
	 * Set the value of the material_comprado.articulo_descripcion_sol column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloDescripcionSol(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_descripcion_sol column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloDescripcionSol(int row,
			String newValue) throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_DESCRIPCION_SOL, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_clase_sol column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloClaseSol()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ARTICULO_CLASE_SOL);
	}

	/**
	 * Retrieve the value of the material_comprado.articulo_clase_sol column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoArticuloClaseSol(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ARTICULO_CLASE_SOL);
	}

	/**
	 * Set the value of the material_comprado.articulo_clase_sol column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloClaseSol(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ARTICULO_CLASE_SOL, newValue);
	}

	/**
	 * Set the value of the material_comprado.articulo_clase_sol column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoArticuloClaseSol(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ARTICULO_CLASE_SOL, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitud_compra_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoSolicitudCompraId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitud_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoSolicitudCompraId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID);
	}

	/**
	 * Set the value of the material_comprado.solicitud_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitudCompraId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.solicitud_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitudCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_SOLICITUD_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoSolicitanteId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_SOLICITANTE_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoSolicitanteId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_SOLICITANTE_ID);
	}

	/**
	 * Set the value of the material_comprado.solicitante_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_SOLICITANTE_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.solicitante_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_SOLICITANTE_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_legajo column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoSolicitanteLegajo()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_SOLICITANTE_LEGAJO);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_legajo column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoSolicitanteLegajo(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_SOLICITANTE_LEGAJO);
	}

	/**
	 * Set the value of the material_comprado.solicitante_legajo column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteLegajo(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_SOLICITANTE_LEGAJO, newValue);
	}

	/**
	 * Set the value of the material_comprado.solicitante_legajo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteLegajo(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_SOLICITANTE_LEGAJO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoSolicitanteNombre()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_SOLICITANTE_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.solicitante_nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoSolicitanteNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_SOLICITANTE_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.solicitante_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_SOLICITANTE_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.solicitante_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoSolicitanteNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_SOLICITANTE_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_sm column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdSm() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_ID_SM);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_sm column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdSm(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_ID_SM);
	}

	/**
	 * Set the value of the material_comprado.estado_id_sm column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdSm(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_ID_SM, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_id_sm column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdSm(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_ID_SM, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_sm column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoSm() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_SM);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_sm column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoSm(int row) throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_SM);
	}

	/**
	 * Set the value of the material_comprado.estado_sm column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoSm(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_SM, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_sm column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoSm(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_SM, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_solicitud column for the
	 * current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaSolicitud()
			throws DataStoreException {
		return getDateTime(MATERIAL_COMPRADO_FECHA_SOLICITUD);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_solicitud column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaSolicitud(int row)
			throws DataStoreException {
		return getDateTime(row, MATERIAL_COMPRADO_FECHA_SOLICITUD);
	}

	/**
	 * Set the value of the material_comprado.fecha_solicitud column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaSolicitud(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(MATERIAL_COMPRADO_FECHA_SOLICITUD, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_solicitud column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaSolicitud(int row,
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, MATERIAL_COMPRADO_FECHA_SOLICITUD, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_aprobacion_sm column for
	 * the current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaAprobacionSm()
			throws DataStoreException {
		return getDateTime(MATERIAL_COMPRADO_FECHA_APROBACION_SM);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_aprobacion_sm column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaAprobacionSm(int row)
			throws DataStoreException {
		return getDateTime(row, MATERIAL_COMPRADO_FECHA_APROBACION_SM);
	}

	/**
	 * Set the value of the material_comprado.fecha_aprobacion_sm column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaAprobacionSm(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(MATERIAL_COMPRADO_FECHA_APROBACION_SM, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_aprobacion_sm column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaAprobacionSm(int row,
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, MATERIAL_COMPRADO_FECHA_APROBACION_SM, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.orden_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoOrdenCompraId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_ORDEN_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.orden_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoOrdenCompraId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_ORDEN_COMPRA_ID);
	}

	/**
	 * Set the value of the material_comprado.orden_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoOrdenCompraId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.orden_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoOrdenCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_ORDEN_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoProveedorId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_PROVEEDOR_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoProveedorId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_PROVEEDOR_ID);
	}

	/**
	 * Set the value of the material_comprado.proveedor_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_PROVEEDOR_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.proveedor_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_PROVEEDOR_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_codigo column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProveedorCodigo() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_PROVEEDOR_CODIGO);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_codigo column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProveedorCodigo(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_PROVEEDOR_CODIGO);
	}

	/**
	 * Set the value of the material_comprado.proveedor_codigo column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorCodigo(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_PROVEEDOR_CODIGO, newValue);
	}

	/**
	 * Set the value of the material_comprado.proveedor_codigo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorCodigo(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_PROVEEDOR_CODIGO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProveedorNombre() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_PROVEEDOR_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.proveedor_nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoProveedorNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_PROVEEDOR_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.proveedor_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.proveedor_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoProveedorNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_PROVEEDOR_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCompradorId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_COMPRADOR_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCompradorId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_COMPRADOR_ID);
	}

	/**
	 * Set the value of the material_comprado.comprador_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_COMPRADOR_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.comprador_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_COMPRADOR_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_legajo column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCompradorLegajo() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_COMPRADOR_LEGAJO);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_legajo column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCompradorLegajo(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_COMPRADOR_LEGAJO);
	}

	/**
	 * Set the value of the material_comprado.comprador_legajo column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorLegajo(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_COMPRADOR_LEGAJO, newValue);
	}

	/**
	 * Set the value of the material_comprado.comprador_legajo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorLegajo(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_COMPRADOR_LEGAJO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCompradorNombre() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_COMPRADOR_NOMBRE);
	}

	/**
	 * Retrieve the value of the material_comprado.comprador_nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCompradorNombre(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_COMPRADOR_NOMBRE);
	}

	/**
	 * Set the value of the material_comprado.comprador_nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorNombre(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_COMPRADOR_NOMBRE, newValue);
	}

	/**
	 * Set the value of the material_comprado.comprador_nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCompradorNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_COMPRADOR_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_oc column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdOc() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_ID_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_id_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoIdOc(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_ID_OC);
	}

	/**
	 * Set the value of the material_comprado.estado_id_oc column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdOc(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_ID_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_id_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoIdOc(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_ID_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_oc column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoOc() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_ESTADO_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.estado_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoEstadoOc(int row) throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_ESTADO_OC);
	}

	/**
	 * Set the value of the material_comprado.estado_oc column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoOc(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_ESTADO_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.estado_oc column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoEstadoOc(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_ESTADO_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_oc column for the
	 * current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaOc()
			throws DataStoreException {
		return getDateTime(MATERIAL_COMPRADO_FECHA_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaOc(int row)
			throws DataStoreException {
		return getDateTime(row, MATERIAL_COMPRADO_FECHA_OC);
	}

	/**
	 * Set the value of the material_comprado.fecha_oc column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaOc(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(MATERIAL_COMPRADO_FECHA_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_oc column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaOc(int row, java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(row, MATERIAL_COMPRADO_FECHA_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_aprobacion_oc column for
	 * the current row.
	 * 
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaAprobacionOc()
			throws DataStoreException {
		return getDateTime(MATERIAL_COMPRADO_FECHA_APROBACION_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_aprobacion_oc column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Timestamp
	 * @throws DataStoreException
	 */
	public java.sql.Timestamp getMaterialCompradoFechaAprobacionOc(int row)
			throws DataStoreException {
		return getDateTime(row, MATERIAL_COMPRADO_FECHA_APROBACION_OC);
	}

	/**
	 * Set the value of the material_comprado.fecha_aprobacion_oc column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaAprobacionOc(java.sql.Timestamp newValue)
			throws DataStoreException {
		setDateTime(MATERIAL_COMPRADO_FECHA_APROBACION_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_aprobacion_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaAprobacionOc(int row,
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, MATERIAL_COMPRADO_FECHA_APROBACION_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_estimada_entrega column
	 * for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaEstimadaEntrega()
			throws DataStoreException {
		return getDate(MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_estimada_entrega column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaEstimadaEntrega(int row)
			throws DataStoreException {
		return getDate(row, MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA);
	}

	/**
	 * Set the value of the material_comprado.fecha_estimada_entrega column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaEstimadaEntrega(java.sql.Date newValue)
			throws DataStoreException {
		setDate(MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_estimada_entrega column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaEstimadaEntrega(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, MATERIAL_COMPRADO_FECHA_ESTIMADA_ENTREGA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_entrega_completa column
	 * for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaEntregaCompleta()
			throws DataStoreException {
		return getDate(MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_entrega_completa column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaEntregaCompleta(int row)
			throws DataStoreException {
		return getDate(row, MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA);
	}

	/**
	 * Set the value of the material_comprado.fecha_entrega_completa column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaEntregaCompleta(java.sql.Date newValue)
			throws DataStoreException {
		setDate(MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_entrega_completa column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaEntregaCompleta(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, MATERIAL_COMPRADO_FECHA_ENTREGA_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_oc column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionOc() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_DESCRIPCION_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.descripcion_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoDescripcionOc(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_DESCRIPCION_OC);
	}

	/**
	 * Set the value of the material_comprado.descripcion_oc column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionOc(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_DESCRIPCION_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.descripcion_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescripcionOc(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_DESCRIPCION_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones_oc column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacionesOc() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_OBSERVACIONES_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones_oc column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservacionesOc(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_OBSERVACIONES_OC);
	}

	/**
	 * Set the value of the material_comprado.observaciones_oc column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacionesOc(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_OBSERVACIONES_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.observaciones_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservacionesOc(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_OBSERVACIONES_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones2_oc column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservaciones2Oc()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_OBSERVACIONES2_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.observaciones2_oc column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoObservaciones2Oc(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_OBSERVACIONES2_OC);
	}

	/**
	 * Set the value of the material_comprado.observaciones2_oc column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservaciones2Oc(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_OBSERVACIONES2_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.observaciones2_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoObservaciones2Oc(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_OBSERVACIONES2_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCondicionCompraId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_CONDICION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCondicionCompraId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_CONDICION_COMPRA_ID);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_CONDICION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_CONDICION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_codigo column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCondicionCompraCodigo()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_codigo column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCondicionCompraCodigo(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_codigo column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraCodigo(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO, newValue);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_codigo column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraCodigo(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CONDICION_COMPRA_CODIGO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_descripcion
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCondicionCompraDescripcion()
			throws DataStoreException {
		return getString(MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the material_comprado.condicion_compra_descripcion
	 * column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoCondicionCompraDescripcion(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_descripcion column
	 * for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraDescripcion(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the material_comprado.condicion_compra_descripcion column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCondicionCompraDescripcion(int row,
			String newValue) throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_CONDICION_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descuento_oc column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoDescuentoOc() throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_DESCUENTO_OC);
	}

	/**
	 * Retrieve the value of the material_comprado.descuento_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoDescuentoOc(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_DESCUENTO_OC);
	}

	/**
	 * Set the value of the material_comprado.descuento_oc column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescuentoOc(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_DESCUENTO_OC, newValue);
	}

	/**
	 * Set the value of the material_comprado.descuento_oc column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescuentoOc(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_DESCUENTO_OC, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_solicitada column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadSolicitada()
			throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_CANTIDAD_SOLICITADA);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_solicitada column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadSolicitada(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_CANTIDAD_SOLICITADA);
	}

	/**
	 * Set the value of the material_comprado.cantidad_solicitada column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadSolicitada(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Set the value of the material_comprado.cantidad_solicitada column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadSolicitada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_pedida column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadPedida() throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_CANTIDAD_PEDIDA);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadPedida(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_CANTIDAD_PEDIDA);
	}

	/**
	 * Set the value of the material_comprado.cantidad_pedida column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadPedida(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Set the value of the material_comprado.cantidad_pedida column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadPedida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_CANTIDAD_PEDIDA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_recibida column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadRecibida()
			throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_CANTIDAD_RECIBIDA);
	}

	/**
	 * Retrieve the value of the material_comprado.cantidad_recibida column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoCantidadRecibida(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_CANTIDAD_RECIBIDA);
	}

	/**
	 * Set the value of the material_comprado.cantidad_recibida column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadRecibida(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Set the value of the material_comprado.cantidad_recibida column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCantidadRecibida(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_CANTIDAD_RECIBIDA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.monto_unitario column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoMontoUnitario() throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_MONTO_UNITARIO);
	}

	/**
	 * Retrieve the value of the material_comprado.monto_unitario column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoMontoUnitario(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_MONTO_UNITARIO);
	}

	/**
	 * Set the value of the material_comprado.monto_unitario column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoMontoUnitario(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_MONTO_UNITARIO, newValue);
	}

	/**
	 * Set the value of the material_comprado.monto_unitario column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoMontoUnitario(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_MONTO_UNITARIO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.monto_ultima_compra column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoMontoUltimaCompra()
			throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the material_comprado.monto_ultima_compra column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoMontoUltimaCompra(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the material_comprado.monto_ultima_compra column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoMontoUltimaCompra(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Set the value of the material_comprado.monto_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoMontoUltimaCompra(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_MONTO_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_ultima_compra column for
	 * the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaUltimaCompra()
			throws DataStoreException {
		return getDate(MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Retrieve the value of the material_comprado.fecha_ultima_compra column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getMaterialCompradoFechaUltimaCompra(int row)
			throws DataStoreException {
		return getDate(row, MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA);
	}

	/**
	 * Set the value of the material_comprado.fecha_ultima_compra column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaUltimaCompra(java.sql.Date newValue)
			throws DataStoreException {
		setDate(MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Set the value of the material_comprado.fecha_ultima_compra column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoFechaUltimaCompra(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, MATERIAL_COMPRADO_FECHA_ULTIMA_COMPRA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.um_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoUmId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_UM_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.um_id column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoUmId(int row) throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_UM_ID);
	}

	/**
	 * Set the value of the material_comprado.um_id column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUmId(int newValue) throws DataStoreException {
		setInt(MATERIAL_COMPRADO_UM_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.um_id column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUmId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_UM_ID, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.um column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoUm() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_UM);
	}

	/**
	 * Retrieve the value of the material_comprado.um column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoUm(int row) throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_UM);
	}

	/**
	 * Set the value of the material_comprado.um column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUm(String newValue) throws DataStoreException {
		setString(MATERIAL_COMPRADO_UM, newValue);
	}

	/**
	 * Set the value of the material_comprado.um column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUm(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_UM, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.um_descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoUmDescripcion() throws DataStoreException {
		return getString(MATERIAL_COMPRADO_UM_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the material_comprado.um_descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMaterialCompradoUmDescripcion(int row)
			throws DataStoreException {
		return getString(row, MATERIAL_COMPRADO_UM_DESCRIPCION);
	}

	/**
	 * Set the value of the material_comprado.um_descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUmDescripcion(String newValue)
			throws DataStoreException {
		setString(MATERIAL_COMPRADO_UM_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the material_comprado.um_descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUmDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, MATERIAL_COMPRADO_UM_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.unidad_medida_id_pedida column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoUnidadMedidaIdPedida()
			throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA);
	}

	/**
	 * Retrieve the value of the material_comprado.unidad_medida_id_pedida column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoUnidadMedidaIdPedida(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA);
	}

	/**
	 * Set the value of the material_comprado.unidad_medida_id_pedida column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUnidadMedidaIdPedida(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA, newValue);
	}

	/**
	 * Set the value of the material_comprado.unidad_medida_id_pedida column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoUnidadMedidaIdPedida(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_UNIDAD_MEDIDA_ID_PEDIDA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.iva column for the current
	 * row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoIva() throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_IVA);
	}

	/**
	 * Retrieve the value of the material_comprado.iva column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoIva(int row) throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_IVA);
	}

	/**
	 * Set the value of the material_comprado.iva column for the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoIva(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_IVA, newValue);
	}

	/**
	 * Set the value of the material_comprado.iva column for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoIva(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_IVA, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.descuento column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoDescuento() throws DataStoreException {
		return getDouble(MATERIAL_COMPRADO_DESCUENTO);
	}

	/**
	 * Retrieve the value of the material_comprado.descuento column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMaterialCompradoDescuento(int row)
			throws DataStoreException {
		return getDouble(row, MATERIAL_COMPRADO_DESCUENTO);
	}

	/**
	 * Set the value of the material_comprado.descuento column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescuento(double newValue)
			throws DataStoreException {
		setDouble(MATERIAL_COMPRADO_DESCUENTO, newValue);
	}

	/**
	 * Set the value of the material_comprado.descuento column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoDescuento(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MATERIAL_COMPRADO_DESCUENTO, newValue);
	}

	/**
	 * Retrieve the value of the material_comprado.cotizacion_compra_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCotizacionCompraId() throws DataStoreException {
		return getInt(MATERIAL_COMPRADO_COTIZACION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the material_comprado.cotizacion_compra_id column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMaterialCompradoCotizacionCompraId(int row)
			throws DataStoreException {
		return getInt(row, MATERIAL_COMPRADO_COTIZACION_COMPRA_ID);
	}

	/**
	 * Set the value of the material_comprado.cotizacion_compra_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCotizacionCompraId(int newValue)
			throws DataStoreException {
		setInt(MATERIAL_COMPRADO_COTIZACION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the material_comprado.cotizacion_compra_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setMaterialCompradoCotizacionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MATERIAL_COMPRADO_COTIZACION_COMPRA_ID, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
