package inventario.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CotizacionesCompraModel: A SOFIA generated model
 */
public class CotizacionesCompraModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1454206165004425368L;
	// constants for columns
	public static final String COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID = "cotizaciones_compra.cotizacion_compra_id";
	public static final String COTIZACIONES_COMPRA_ESTADO = "cotizaciones_compra.estado";
	public static final String COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1 = "cotizaciones_compra.entidad_id_proveedor1";
	public static final String COTIZACIONES_COMPRA_USER_ID_COMPRADOR = "cotizaciones_compra.user_id_comprador";
	public static final String COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1 = "cotizaciones_compra.condicion_compra_id_proveedor1";
	public static final String COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1 = "cotizaciones_compra.forma_pago_id_proveedor1";
	public static final String COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1 = "cotizaciones_compra.plazo_entrega_proveedor1";
	public static final String COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1 = "cotizaciones_compra.bonificacion_proveedor1";
	public static final String COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1 = "cotizaciones_compra.total_proveedor1";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1 = "cotizaciones_compra.observaciones_proveedor1";
	public static final String COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2 = "cotizaciones_compra.entidad_id_proveedor2";
	public static final String COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2 = "cotizaciones_compra.condicion_compra_id_proveedor2";
	public static final String COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2 = "cotizaciones_compra.forma_pago_id_proveedor2";
	public static final String COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2 = "cotizaciones_compra.plazo_entrega_proveedor2";
	public static final String COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2 = "cotizaciones_compra.bonificacion_proveedor2";
	public static final String COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2 = "cotizaciones_compra.total_proveedor2";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2 = "cotizaciones_compra.observaciones_proveedor2";
	public static final String COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3 = "cotizaciones_compra.entidad_id_proveedor3";
	public static final String COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3 = "cotizaciones_compra.condicion_compra_id_proveedor3";
	public static final String COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3 = "cotizaciones_compra.forma_pago_id_proveedor3";
	public static final String COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3 = "cotizaciones_compra.plazo_entrega_proveedor3";
	public static final String COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3 = "cotizaciones_compra.bonificacion_proveedor3";
	public static final String COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3 = "cotizaciones_compra.total_proveedor3";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3 = "cotizaciones_compra.observaciones_proveedor3";
	public static final String COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4 = "cotizaciones_compra.entidad_id_proveedor4";
	public static final String COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4 = "cotizaciones_compra.condicion_compra_id_proveedor4";
	public static final String COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4 = "cotizaciones_compra.forma_pago_id_proveedor4";
	public static final String COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4 = "cotizaciones_compra.plazo_entrega_proveedor4";
	public static final String COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4 = "cotizaciones_compra.bonificacion_proveedor4";
	public static final String COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4 = "cotizaciones_compra.total_proveedor4";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4 = "cotizaciones_compra.observaciones_proveedor4";
	public static final String COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5 = "cotizaciones_compra.entidad_id_proveedor5";
	public static final String COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5 = "cotizaciones_compra.condicion_compra_id_proveedor5";
	public static final String COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5 = "cotizaciones_compra.forma_pago_id_proveedor5";
	public static final String COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5 = "cotizaciones_compra.plazo_entrega_proveedor5";
	public static final String COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5 = "cotizaciones_compra.bonificacion_proveedor5";
	public static final String COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5 = "cotizaciones_compra.total_proveedor5";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5 = "cotizaciones_compra.observaciones_proveedor5";
	public static final String COTIZACIONES_COMPRA_OBSERVACIONES = "cotizaciones_compra.observaciones";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR1 = "condiciones_compra_proveedor1.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR2 = "condiciones_compra_proveedor2.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR3 = "condiciones_compra_proveedor3.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR4 = "condiciones_compra_proveedor4.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR5 = "condiciones_compra_proveedor5.nombre";
	public static final String NOMBRE_FORMA_PAGO_PROVEEDOR1 = "formas_pago_proveedor1.nombre";
	public static final String NOMBRE_FORMA_PAGO_PROVEEDOR2 = "formas_pago_proveedor2.nombre";
	public static final String NOMBRE_FORMA_PAGO_PROVEEDOR3 = "formas_pago_proveedor3.nombre";
	public static final String NOMBRE_FORMA_PAGO_PROVEEDOR4 = "formas_pago_proveedor4.nombre";
	public static final String NOMBRE_FORMA_PAGO_PROVEEDOR5 = "formas_pago_proveedor5.nombre";
	public static final String ESTADO_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_NOMBRE_COMPRADOR = "nombre_completo";
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";
	public static final String ENTIDAD_EXTERNA_CODIGO_PROVEEDOR1 = "entidad_externa_proveedor1.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR1 = "entidad_externa_proveedor1.nombre";
	public static final String ENTIDAD_EXTERNA_CODIGO_PROVEEDOR2 = "entidad_externa_proveedor2.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR2 = "entidad_externa_proveedor2.nombre";
	public static final String ENTIDAD_EXTERNA_CODIGO_PROVEEDOR3 = "entidad_externa_proveedor3.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR3 = "entidad_externa_proveedor3.nombre";
	public static final String ENTIDAD_EXTERNA_CODIGO_PROVEEDOR4 = "entidad_externa_proveedor4.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR4 = "entidad_externa_proveedor4.nombre";
	public static final String ENTIDAD_EXTERNA_CODIGO_PROVEEDOR5 = "entidad_externa_proveedor5.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR5 = "entidad_externa_proveedor5.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new CotizacionesCompraModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public CotizacionesCompraModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new CotizacionesCompraModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public CotizacionesCompraModel(String appName, String profile) {
		super(appName, profile);

		try {
			// add aliases
			addTableAlias(computeTableName("condiciones_compra"),
					"condiciones_compra_proveedor1");
			addTableAlias(computeTableName("condiciones_compra"),
					"condiciones_compra_proveedor2");
			addTableAlias(computeTableName("condiciones_compra"),
					"condiciones_compra_proveedor3");
			addTableAlias(computeTableName("condiciones_compra"),
					"condiciones_compra_proveedor4");
			addTableAlias(computeTableName("condiciones_compra"),
					"condiciones_compra_proveedor5");
			addTableAlias(computeTableName("cotizaciones_compra"), null);
			addTableAlias(computeTableName("formas_pago"),
					"formas_pago_proveedor1");
			addTableAlias(computeTableName("formas_pago"),
					"formas_pago_proveedor2");
			addTableAlias(computeTableName("formas_pago"),
					"formas_pago_proveedor3");
			addTableAlias(computeTableName("formas_pago"),
					"formas_pago_proveedor4");
			addTableAlias(computeTableName("formas_pago"),
					"formas_pago_proveedor5");
			addTableAlias(computeTableName("infraestructura.estados"),
					"estados");
			addTableAlias(computeTableName("infraestructura.website_user"),
					"website_user_comprador");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa_proveedor1");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa_proveedor2");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa_proveedor3");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa_proveedor4");
			addTableAlias(computeTableName("infraestructura.entidad_externa"),
					"entidad_externa_proveedor5");

			// add columns
			addColumn(computeTableName("cotizaciones_compra"),
					"cotizacion_compra_id", DataStore.DATATYPE_INT, true, true,
					COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID);
			addColumn(computeTableName("cotizaciones_compra"), "estado",
					DataStore.DATATYPE_STRING, false, true,
					COTIZACIONES_COMPRA_ESTADO);
			addColumn(computeTableName("cotizaciones_compra"),
					"entidad_id_proveedor1", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"user_id_comprador", DataStore.DATATYPE_INT, false, true,
					COTIZACIONES_COMPRA_USER_ID_COMPRADOR);
			addColumn(computeTableName("cotizaciones_compra"),
					"condicion_compra_id_proveedor1", DataStore.DATATYPE_INT,
					false, true,
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"forma_pago_id_proveedor1", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"plazo_entrega_proveedor1", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"bonificacion_proveedor1", DataStore.DATATYPE_DOUBLE,
					false, true, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"total_proveedor1", DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"observaciones_proveedor1", DataStore.DATATYPE_STRING,
					false, true, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1);
			addColumn(computeTableName("cotizaciones_compra"),
					"entidad_id_proveedor2", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"condicion_compra_id_proveedor2", DataStore.DATATYPE_INT,
					false, true,
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"forma_pago_id_proveedor2", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"plazo_entrega_proveedor2", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"bonificacion_proveedor2", DataStore.DATATYPE_DOUBLE,
					false, true, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"total_proveedor2", DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"observaciones_proveedor2", DataStore.DATATYPE_STRING,
					false, true, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2);
			addColumn(computeTableName("cotizaciones_compra"),
					"entidad_id_proveedor3", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"condicion_compra_id_proveedor3", DataStore.DATATYPE_INT,
					false, true,
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"forma_pago_id_proveedor3", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"plazo_entrega_proveedor3", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"bonificacion_proveedor3", DataStore.DATATYPE_DOUBLE,
					false, true, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"total_proveedor3", DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"observaciones_proveedor3", DataStore.DATATYPE_STRING,
					false, true, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3);
			addColumn(computeTableName("cotizaciones_compra"),
					"entidad_id_proveedor4", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"condicion_compra_id_proveedor4", DataStore.DATATYPE_INT,
					false, true,
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"forma_pago_id_proveedor4", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"plazo_entrega_proveedor4", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"bonificacion_proveedor4", DataStore.DATATYPE_DOUBLE,
					false, true, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"total_proveedor4", DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"observaciones_proveedor4", DataStore.DATATYPE_STRING,
					false, true, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4);
			addColumn(computeTableName("cotizaciones_compra"),
					"entidad_id_proveedor5", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"condicion_compra_id_proveedor5", DataStore.DATATYPE_INT,
					false, true,
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"forma_pago_id_proveedor5", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"plazo_entrega_proveedor5", DataStore.DATATYPE_INT, false,
					true, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"bonificacion_proveedor5", DataStore.DATATYPE_DOUBLE,
					false, true, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"total_proveedor5", DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"),
					"observaciones_proveedor5", DataStore.DATATYPE_STRING,
					false, true, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5);
			addColumn(computeTableName("cotizaciones_compra"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					COTIZACIONES_COMPRA_OBSERVACIONES);
			addColumn(computeTableName("estados"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					ESTADO_NOMBRE);
			addColumn(computeTableName("website_user_comprador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, false,
					WEBSITE_USER_NOMBRE_COMPRADOR);
			addColumn(computeTableName("condiciones_compra_proveedor1"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					NOMBRE_CONDICION_COMPRA_PROVEEDOR1);
			addColumn(computeTableName("condiciones_compra_proveedor2"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					NOMBRE_CONDICION_COMPRA_PROVEEDOR2);
			addColumn(computeTableName("condiciones_compra_proveedor3"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					NOMBRE_CONDICION_COMPRA_PROVEEDOR3);
			addColumn(computeTableName("condiciones_compra_proveedor4"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					NOMBRE_CONDICION_COMPRA_PROVEEDOR4);
			addColumn(computeTableName("condiciones_compra_proveedor5"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					NOMBRE_CONDICION_COMPRA_PROVEEDOR5);
			addColumn(computeTableName("formas_pago_proveedor1"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					NOMBRE_FORMA_PAGO_PROVEEDOR1);
			addColumn(computeTableName("formas_pago_proveedor2"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					NOMBRE_FORMA_PAGO_PROVEEDOR2);
			addColumn(computeTableName("formas_pago_proveedor3"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					NOMBRE_FORMA_PAGO_PROVEEDOR3);
			addColumn(computeTableName("formas_pago_proveedor4"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					NOMBRE_FORMA_PAGO_PROVEEDOR4);
			addColumn(computeTableName("formas_pago_proveedor5"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					NOMBRE_FORMA_PAGO_PROVEEDOR5);
			addColumn(computeTableName("entidad_externa_proveedor1"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO_PROVEEDOR1);
			addColumn(computeTableName("entidad_externa_proveedor1"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR1);
			addColumn(computeTableName("entidad_externa_proveedor2"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO_PROVEEDOR2);
			addColumn(computeTableName("entidad_externa_proveedor2"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR2);
			addColumn(computeTableName("entidad_externa_proveedor3"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO_PROVEEDOR3);
			addColumn(computeTableName("entidad_externa_proveedor3"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR3);
			addColumn(computeTableName("entidad_externa_proveedor4"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO_PROVEEDOR4);
			addColumn(computeTableName("entidad_externa_proveedor4"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR4);
			addColumn(computeTableName("entidad_externa_proveedor5"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO_PROVEEDOR5);
			addColumn(computeTableName("entidad_externa_proveedor5"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR5);
			
			// add buckets
			addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);
			
			setAutoIncrement(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, true);
			setUpdateable(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, false);

			// add joins
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.estado"),
					computeTableAndFieldName("estados.estado"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.user_id_comprador"),
					computeTableAndFieldName("website_user_comprador.user_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.condicion_compra_id_proveedor1"),
					computeTableAndFieldName("condiciones_compra_proveedor1.condicion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.condicion_compra_id_proveedor2"),
					computeTableAndFieldName("condiciones_compra_proveedor2.condicion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.condicion_compra_id_proveedor3"),
					computeTableAndFieldName("condiciones_compra_proveedor3.condicion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.condicion_compra_id_proveedor4"),
					computeTableAndFieldName("condiciones_compra_proveedor4.condicion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.condicion_compra_id_proveedor5"),
					computeTableAndFieldName("condiciones_compra_proveedor5.condicion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.forma_pago_id_proveedor1"),
					computeTableAndFieldName("formas_pago_proveedor1.forma_pago_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.forma_pago_id_proveedor2"),
					computeTableAndFieldName("formas_pago_proveedor2.forma_pago_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.forma_pago_id_proveedor3"),
					computeTableAndFieldName("formas_pago_proveedor3.forma_pago_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.forma_pago_id_proveedor4"),
					computeTableAndFieldName("formas_pago_proveedor4.forma_pago_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.forma_pago_id_proveedor5"),
					computeTableAndFieldName("formas_pago_proveedor5.forma_pago_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.entidad_id_proveedor1"),
					computeTableAndFieldName("entidad_externa_proveedor1.entidad_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.entidad_id_proveedor2"),
					computeTableAndFieldName("entidad_externa_proveedor2.entidad_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.entidad_id_proveedor3"),
					computeTableAndFieldName("entidad_externa_proveedor3.entidad_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.entidad_id_proveedor4"),
					computeTableAndFieldName("entidad_externa_proveedor4.entidad_id"),
					true);
			addJoin(
					computeTableAndFieldName("cotizaciones_compra.entidad_id_proveedor5"),
					computeTableAndFieldName("entidad_externa_proveedor5.entidad_id"),
					true);

			// set order by
			setOrderBy(computeTableAndFieldName("cotizaciones_compra.cotizacion_compra_id")	+ " ASC");
			
			setAutoIncrement(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, true);
			setUpdateable(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, false);


			addLookupRule(
					COTIZACIONES_COMPRA_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = \"' + cotizaciones_compra.estado + '\"'",
					"nombre", ESTADO_NOMBRE, "Estado inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_USER_ID_COMPRADOR,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + cotizaciones_compra.user_id_comprador",
					"nombre_completo", WEBSITE_USER_NOMBRE_COMPRADOR,
					"Usuario inexistente");

			// Lookup de los proveedores
			addLookupRule(
					COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + cotizaciones_compra.entidad_id_proveedor1",
					"nombre",
					computeTableAndFieldName(ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR1),
					"Proveedor 1 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + cotizaciones_compra.entidad_id_proveedor2",
					"nombre",
					computeTableAndFieldName(ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR2),
					"Proveedor 2 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + cotizaciones_compra.entidad_id_proveedor3",
					"nombre",
					computeTableAndFieldName(ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR3),
					"Proveedor 3 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + cotizaciones_compra.entidad_id_proveedor4",
					"nombre",
					computeTableAndFieldName(ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR4),
					"Proveedor 4 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5,
					"infraestructura.entidad_externa",
					"'infraestructura.entidad_externa.entidad_id = ' + cotizaciones_compra.entidad_id_proveedor5",
					"nombre",
					computeTableAndFieldName(ENTIDAD_EXTERNA_NOMBRE_PROVEEDOR5),
					"Proveedor 5 inexistente");
			
			// lookup a las formas de pago
			addLookupRule(
					COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1,
					"formas_pago",
					"'formas_pago.forma_pago_id = ' + cotizaciones_compra.formas_pago_id_proveedor1",
					"nombre",
					computeTableAndFieldName(NOMBRE_FORMA_PAGO_PROVEEDOR1),
					"Forma de pago para el proveedor 1 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2,
					"formas_pago",
					"'formas_pago.forma_pago_id = ' + cotizaciones_compra.formas_pago_id_proveedor2",
					"nombre",
					computeTableAndFieldName(NOMBRE_FORMA_PAGO_PROVEEDOR2),
					"Forma de pago para el proveedor 2 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3,
					"formas_pago",
					"'formas_pago.forma_pago_id = ' + cotizaciones_compra.formas_pago_id_proveedor3",
					"nombre",
					computeTableAndFieldName(NOMBRE_FORMA_PAGO_PROVEEDOR3),
					"Forma de pago para el proveedor 3 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4,
					"formas_pago",
					"'formas_pago.forma_pago_id = ' + cotizaciones_compra.formas_pago_id_proveedor4",
					"nombre",
					computeTableAndFieldName(NOMBRE_FORMA_PAGO_PROVEEDOR4),
					"Forma de pago para el proveedor 4 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5,
					"formas_pago",
					"'formas_pago.forma_pago_id = ' + cotizaciones_compra.formas_pago_id_proveedor5",
					"nombre",
					computeTableAndFieldName(NOMBRE_FORMA_PAGO_PROVEEDOR5),
					"Forma de pago para el proveedor 5 inexistente");

			// lookup a las condiciones de pago
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor1",
					"nombre",
					computeTableAndFieldName(NOMBRE_CONDICION_COMPRA_PROVEEDOR1),
					"Condición de compra para el proveedor 1 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor2",
					"nombre",
					computeTableAndFieldName(NOMBRE_CONDICION_COMPRA_PROVEEDOR2),
					"Condición de compra para el proveedor 2 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor3",
					"nombre",
					computeTableAndFieldName(NOMBRE_CONDICION_COMPRA_PROVEEDOR3),
					"Condición de compra para el proveedor 3 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor4",
					"nombre",
					computeTableAndFieldName(NOMBRE_CONDICION_COMPRA_PROVEEDOR4),
					"Condición de compra para el proveedor 4 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor5",
					"nombre",
					computeTableAndFieldName(NOMBRE_CONDICION_COMPRA_PROVEEDOR5),
					"Condición de compra para el proveedor 5 inexistente");

		
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the cotizaciones_compra.cotizacion_compra_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCotizacionCompraId()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.cotizacion_compra_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCotizacionCompraId(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID);
	}

	/**
	 * Set the value of the cotizaciones_compra.cotizacion_compra_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCotizacionCompraId(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.cotizacion_compra_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCotizacionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_COTIZACION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.estado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraEstado() throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_ESTADO);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraEstado(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_ESTADO);
	}

	/**
	 * Set the value of the cotizaciones_compra.estado column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEstado(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.estado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor1
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor1()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor1(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor1 column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor1(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor1 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor1(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.user_id_comprador column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraUserIdComprador() throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.user_id_comprador column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraUserIdComprador(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_USER_ID_COMPRADOR);
	}

	/**
	 * Set the value of the cotizaciones_compra.user_id_comprador column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraUserIdComprador(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.user_id_comprador column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraUserIdComprador(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_USER_ID_COMPRADOR, newValue);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor1 column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor1()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor1 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor1(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor1
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor1(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor1(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR1,
				newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor1
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor1()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor1(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor1 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor1(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor1 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor1(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor1
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor1()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor1(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor1 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor1(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor1 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor1(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor1
	 * column for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor1()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor1(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor1 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor1(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor1 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor1(int row,
			double newValue) throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor1 column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor1()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor1 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor1(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor1 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor1(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor1 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor1(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor1
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor1()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor1
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor1(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor1 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor1(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor1 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor1(int row,
			String newValue) throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor2
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor2()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor2(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor2 column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor2(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor2 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor2(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor2 column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor2()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor2 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor2(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor2
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor2(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor2(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2,
				newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor2
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor2()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor2(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor2 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor2(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor2 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor2(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor2
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor2()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor2(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor2 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor2(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor2 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor2(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor2
	 * column for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor2()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor2(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor2 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor2(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor2 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor2(int row,
			double newValue) throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor2 column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor2()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor2 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor2(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor2 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor2(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor2 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor2(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor2
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor2()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor2
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor2(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor2 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor2(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor2 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor2(int row,
			String newValue) throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor3
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor3()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor3(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor3 column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor3(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor3 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor3(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor3 column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor3()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor3 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor3(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor3
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor3(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor3(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3,
				newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor3
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor3()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor3(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor3 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor3(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor3 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor3(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor3
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor3()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor3(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor3 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor3(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor3 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor3(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor3
	 * column for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor3()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor3(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor3 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor3(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor3 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor3(int row,
			double newValue) throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor3 column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor3()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor3 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor3(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor3 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor3(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor3 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor3(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor3
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor3()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor3
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor3(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor3 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor3(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor3 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor3(int row,
			String newValue) throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor4
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor4()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor4(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor4 column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor4(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor4 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor4(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor4 column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor4()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor4 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor4(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor4
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor4(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor4(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4,
				newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor4
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor4()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor4(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor4 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor4(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor4 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor4(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor4
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor4()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor4(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor4 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor4(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor4 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor4(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor4
	 * column for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor4()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor4(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor4 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor4(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor4 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor4(int row,
			double newValue) throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor4 column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor4()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor4 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor4(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor4 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor4(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor4 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor4(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor4
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor4()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor4
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor4(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor4 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor4(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor4 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor4(int row,
			String newValue) throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor5
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor5()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.entidad_id_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraEntidadIdProveedor5(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor5 column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor5(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.entidad_id_proveedor5 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraEntidadIdProveedor5(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_ENTIDAD_ID_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor5 column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor5()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the
	 * cotizaciones_compra.condicion_compra_id_proveedor5 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraCondicionCompraIdProveedor5(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor5
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor5(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.condicion_compra_id_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraCondicionCompraIdProveedor5(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5,
				newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor5
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor5()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.forma_pago_id_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraFormaPagoIdProveedor5(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor5 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor5(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.forma_pago_id_proveedor5 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraFormaPagoIdProveedor5(int row, int newValue)
			throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_FORMA_PAGO_ID_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor5
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor5()
			throws DataStoreException {
		return getInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.plazo_entrega_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCotizacionesCompraPlazoEntregaProveedor5(int row)
			throws DataStoreException {
		return getInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor5 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor5(int newValue)
			throws DataStoreException {
		setInt(COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.plazo_entrega_proveedor5 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraPlazoEntregaProveedor5(int row,
			int newValue) throws DataStoreException {
		setInt(row, COTIZACIONES_COMPRA_PLAZO_ENTREGA_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor5
	 * column for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor5()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.bonificacion_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraBonificacionProveedor5(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor5 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor5(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.bonificacion_proveedor5 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraBonificacionProveedor5(int row,
			double newValue) throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_BONIFICACION_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor5 column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor5()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_proveedor5 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalProveedor5(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor5 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor5(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_proveedor5 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalProveedor5(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor5
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor5()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones_proveedor5
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservacionesProveedor5(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor5 column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor5(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones_proveedor5 column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservacionesProveedor5(int row,
			String newValue) throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservaciones()
			throws DataStoreException {
		return getString(COTIZACIONES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.observaciones column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCotizacionesCompraObservaciones(int row)
			throws DataStoreException {
		return getString(row, COTIZACIONES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservaciones(String newValue)
			throws DataStoreException {
		setString(COTIZACIONES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, COTIZACIONES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor1 column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor1()
			throws DataStoreException {
		return getString(NOMBRE_CONDICION_COMPRA_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor1 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor1(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR1);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor1 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor1(String newValue)
			throws DataStoreException {
		setString(NOMBRE_CONDICION_COMPRA_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor1 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor1(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor2 column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor2()
			throws DataStoreException {
		return getString(NOMBRE_CONDICION_COMPRA_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor2 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor2(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR2);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor2 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor2(String newValue)
			throws DataStoreException {
		setString(NOMBRE_CONDICION_COMPRA_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor2 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor2(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor3 column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor3()
			throws DataStoreException {
		return getString(NOMBRE_CONDICION_COMPRA_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor3 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor3(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR3);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor3 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor3(String newValue)
			throws DataStoreException {
		setString(NOMBRE_CONDICION_COMPRA_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor3 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor3(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor4 column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor4()
			throws DataStoreException {
		return getString(NOMBRE_CONDICION_COMPRA_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor4 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor4(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR4);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor4 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor4(String newValue)
			throws DataStoreException {
		setString(NOMBRE_CONDICION_COMPRA_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor4 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor4(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor5 column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor5()
			throws DataStoreException {
		return getString(NOMBRE_CONDICION_COMPRA_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the nombre_condicion_compra_proveedor5 column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreCondicionCompraProveedor5(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR5);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor5 column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor5(String newValue)
			throws DataStoreException {
		setString(NOMBRE_CONDICION_COMPRA_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the nombre_condicion_compra_proveedor5 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreCondicionCompraProveedor5(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_CONDICION_COMPRA_PROVEEDOR5, newValue);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor1 column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor1() throws DataStoreException {
		return getString(NOMBRE_FORMA_PAGO_PROVEEDOR1);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor1 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor1(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_FORMA_PAGO_PROVEEDOR1);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor1 column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor1(String newValue)
			throws DataStoreException {
		setString(NOMBRE_FORMA_PAGO_PROVEEDOR1, newValue);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor1 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor1(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_FORMA_PAGO_PROVEEDOR1, newValue);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor2 column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor2() throws DataStoreException {
		return getString(NOMBRE_FORMA_PAGO_PROVEEDOR2);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor2 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor2(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_FORMA_PAGO_PROVEEDOR2);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor2 column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor2(String newValue)
			throws DataStoreException {
		setString(NOMBRE_FORMA_PAGO_PROVEEDOR2, newValue);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor2 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor2(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_FORMA_PAGO_PROVEEDOR2, newValue);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor3 column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor3() throws DataStoreException {
		return getString(NOMBRE_FORMA_PAGO_PROVEEDOR3);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor3 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor3(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_FORMA_PAGO_PROVEEDOR3);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor3 column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor3(String newValue)
			throws DataStoreException {
		setString(NOMBRE_FORMA_PAGO_PROVEEDOR3, newValue);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor3 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor3(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_FORMA_PAGO_PROVEEDOR3, newValue);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor4 column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor4() throws DataStoreException {
		return getString(NOMBRE_FORMA_PAGO_PROVEEDOR4);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor4 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor4(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_FORMA_PAGO_PROVEEDOR4);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor4 column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor4(String newValue)
			throws DataStoreException {
		setString(NOMBRE_FORMA_PAGO_PROVEEDOR4, newValue);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor4 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor4(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_FORMA_PAGO_PROVEEDOR4, newValue);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor5 column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor5() throws DataStoreException {
		return getString(NOMBRE_FORMA_PAGO_PROVEEDOR5);
	}

	/**
	 * Retrieve the value of the nombre_forma_pago_proveedor5 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getNombreFormaPagoProveedor5(int row)
			throws DataStoreException {
		return getString(row, NOMBRE_FORMA_PAGO_PROVEEDOR5);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor5 column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor5(String newValue)
			throws DataStoreException {
		setString(NOMBRE_FORMA_PAGO_PROVEEDOR5, newValue);
	}

	/**
	 * Set the value of the nombre_forma_pago_proveedor5 column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setNombreFormaPagoProveedor5(int row, String newValue)
			throws DataStoreException {
		setString(row, NOMBRE_FORMA_PAGO_PROVEEDOR5, newValue);
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
	

	@Override
	public String getEstadoActual() throws DataStoreException {
		return getCotizacionesCompraEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getCotizacionesCompraCotizacionCompraId();
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
