package inventario.models;

import infraestructura.models.BaseModel;
import inventario.util.SolicitudCompraTransiciones;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

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
	public static final String COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA = "cotizaciones_compra.total_cotizacion_seleccionada";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR1 = "condiciones_compra_proveedor1.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR2 = "condiciones_compra_proveedor2.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR3 = "condiciones_compra_proveedor3.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR4 = "condiciones_compra_proveedor4.nombre";
	public static final String NOMBRE_CONDICION_COMPRA_PROVEEDOR5 = "condiciones_compra_proveedor5.nombre";
	public static final String DESCRIPCION_CONDICION_COMPRA_PROVEEDOR1 = "condiciones_compra_proveedor1.descripcion";
	public static final String DESCRIPCION_CONDICION_COMPRA_PROVEEDOR2 = "condiciones_compra_proveedor2.descripcion";
	public static final String DESCRIPCION_CONDICION_COMPRA_PROVEEDOR3 = "condiciones_compra_proveedor3.descripcion";
	public static final String DESCRIPCION_CONDICION_COMPRA_PROVEEDOR4 = "condiciones_compra_proveedor4.descripcion";
	public static final String DESCRIPCION_CONDICION_COMPRA_PROVEEDOR5 = "condiciones_compra_proveedor5.descripcion";
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
			addColumn(computeTableName("cotizaciones_compra"), "total_cotizacion_seleccionada",
					DataStore.DATATYPE_DOUBLE, false, true,
					COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA);
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
			addColumn(computeTableName("condiciones_compra_proveedor1"),
					"descripcion", DataStore.DATATYPE_STRING, false, false,
					DESCRIPCION_CONDICION_COMPRA_PROVEEDOR1);
			addColumn(computeTableName("condiciones_compra_proveedor2"),
					"descripcion", DataStore.DATATYPE_STRING, false, false,
					DESCRIPCION_CONDICION_COMPRA_PROVEEDOR2);
			addColumn(computeTableName("condiciones_compra_proveedor3"),
					"descripcion", DataStore.DATATYPE_STRING, false, false,
					DESCRIPCION_CONDICION_COMPRA_PROVEEDOR3);
			addColumn(computeTableName("condiciones_compra_proveedor4"),
					"descripcion", DataStore.DATATYPE_STRING, false, false,
					DESCRIPCION_CONDICION_COMPRA_PROVEEDOR4);
			addColumn(computeTableName("condiciones_compra_proveedor5"),
					"descripcion", DataStore.DATATYPE_STRING, false, false,
					DESCRIPCION_CONDICION_COMPRA_PROVEEDOR5);
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
					"descripcion",
					computeTableAndFieldName(DESCRIPCION_CONDICION_COMPRA_PROVEEDOR1),
					"Condición de compra para el proveedor 1 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR2,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor2",
					"descripcion",
					computeTableAndFieldName(DESCRIPCION_CONDICION_COMPRA_PROVEEDOR2),
					"Condición de compra para el proveedor 2 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR3,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor3",
					"descripcion",
					computeTableAndFieldName(DESCRIPCION_CONDICION_COMPRA_PROVEEDOR3),
					"Condición de compra para el proveedor 3 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR4,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor4",
					"descripcion",
					computeTableAndFieldName(DESCRIPCION_CONDICION_COMPRA_PROVEEDOR4),
					"Condición de compra para el proveedor 4 inexistente");
			addLookupRule(
					COTIZACIONES_COMPRA_CONDICION_COMPRA_ID_PROVEEDOR5,
					"condiciones_compra",
					"'condiciones_compra.condicion_compra_id = ' + cotizaciones_compra.condicion_compra_id_proveedor5",
					"descripcion",
					computeTableAndFieldName(DESCRIPCION_CONDICION_COMPRA_PROVEEDOR5),
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
	 * Retrieve the value of the cotizaciones_compra.total_CotizacionSeleccionada column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalCotizacionSeleccionada()
			throws DataStoreException {
		return getDouble(COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA);
	}

	/**
	 * Retrieve the value of the cotizaciones_compra.total_CotizacionSeleccionada column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getCotizacionesCompraTotalCotizacionSeleccionada(int row)
			throws DataStoreException {
		return getDouble(row, COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_CotizacionSeleccionada column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalCotizacionSeleccionada(double newValue)
			throws DataStoreException {
		setDouble(COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA, newValue);
	}

	/**
	 * Set the value of the cotizaciones_compra.total_CotizacionSeleccionada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCotizacionesCompraTotalCotizacionSeleccionada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, COTIZACIONES_COMPRA_TOTAL_COTIZACION_SELECCIONADA, newValue);
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
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		CondicionesCompraModel condicionesCompraModel = new CondicionesCompraModel(getAppName());		
		
		if (getNombreCondicionCompraProveedor1() != null) {
			condicionesCompraModel.retrieve(CondicionesCompraModel.CONDICIONES_COMPRA_NOMBRE + " = '" + getNombreCondicionCompraProveedor1()+"'");
			if (condicionesCompraModel.gotoFirst()) {
				setCotizacionesCompraCondicionCompraIdProveedor1(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
			} else {
				throw new DataStoreException("No existe la condición de compra especificada para el proveedor 1");
			}
		}
		if (getNombreCondicionCompraProveedor2() != null) {
			condicionesCompraModel.retrieve(CondicionesCompraModel.CONDICIONES_COMPRA_NOMBRE + " = '" + getNombreCondicionCompraProveedor2()+"'");
			if (condicionesCompraModel.gotoFirst()) {
				setCotizacionesCompraCondicionCompraIdProveedor2(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
			} else {
				throw new DataStoreException("No existe la condición de compra especificada para el proveedor 2");
			}
		}
		if (getNombreCondicionCompraProveedor3() != null) {
			condicionesCompraModel.retrieve(CondicionesCompraModel.CONDICIONES_COMPRA_NOMBRE + " = '" + getNombreCondicionCompraProveedor3()+"'");
			if (condicionesCompraModel.gotoFirst()) {
				setCotizacionesCompraCondicionCompraIdProveedor3(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
			} else {
				throw new DataStoreException("No existe la condición de compra especificada para el proveedor 3");
			}	
		}
		if (getNombreCondicionCompraProveedor4() != null) {
			condicionesCompraModel.retrieve(CondicionesCompraModel.CONDICIONES_COMPRA_NOMBRE + " = '" + getNombreCondicionCompraProveedor4()+"'");
			if (condicionesCompraModel.gotoFirst()) {
				setCotizacionesCompraCondicionCompraIdProveedor4(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
			} else {
				throw new DataStoreException("No existe la condición de compra especificada para el proveedor 4");
			}
		}
		if (getNombreCondicionCompraProveedor5() != null) {
			condicionesCompraModel.retrieve(CondicionesCompraModel.CONDICIONES_COMPRA_NOMBRE + " = '" + getNombreCondicionCompraProveedor5()+"'");
			if (condicionesCompraModel.gotoFirst()) {
				setCotizacionesCompraCondicionCompraIdProveedor5(condicionesCompraModel.getCondicionesCompraCondicionCompraId());
			} else {
				throw new DataStoreException("No existe la condición de compra especificada para el proveedor 5");
			}
		}	
		super.update(conn, handleTrans);
	}
	
	/**
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void setTotalesProveedor(int row) throws DataStoreException, SQLException {
		this.gotoRow(row);
		setTotalesProveedor();
	}
	
	/**
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void setTotalesProveedor() throws DataStoreException, SQLException {
		
		double total_proveedor1 = 0,total_proveedor2 = 0,total_proveedor3 = 0,total_proveedor4 = 0, total_proveedor5 = 0;
		double totalCotizacionSeleccionada = 0;

		DetalleCotizacionModel dsDetalleCotizacion = new DetalleCotizacionModel("inventario", "inventario");

		// tabla con los descuentos ofrecidos por proveedor
		double descuentos[] = {1 - (getCotizacionesCompraBonificacionProveedor1() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor2() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor3() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor4() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor5() / 100),
				};
		// rocorro los descuentos para setear en 1 si el descuento es cero
		for (int i = 0 ; i < 5 ; i++)
			if (descuentos[i] == 0)
				descuentos[i] = 1;
		
		// recupera los detalles de la cotización actual
		dsDetalleCotizacion.retrieve("detalle_cotizacion.cotizacion_compra_id = " + getCotizacionesCompraCotizacionCompraId());
		dsDetalleCotizacion.waitForRetrieve();
		
		// si recueró registros, itera sumando los totales
		if (dsDetalleCotizacion.getRowCount() > 0) {
			for (int i = 0 ; i < dsDetalleCotizacion.getRowCount(); i++) {
				float cantidad = dsDetalleCotizacion.getDetalleCotizacionCantidad(i);
				total_proveedor1 += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor1(i) * descuentos[0];
				total_proveedor2 += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor2(i) * descuentos[1];
				total_proveedor3 += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor3(i) * descuentos[2];
				total_proveedor4 += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor4(i) * descuentos[3];
				total_proveedor5 += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor5(i) * descuentos[4];
				// acumula el total de los artículos seleeccionados
				if (dsDetalleCotizacion.getDetalleCotizacionCotizacionSeleccionadaProveedor1(i) > 0)
					totalCotizacionSeleccionada += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor1(i) * descuentos[0];
				if (dsDetalleCotizacion.getDetalleCotizacionCotizacionSeleccionadaProveedor2(i) > 0)
					totalCotizacionSeleccionada += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor2(i) * descuentos[1];
				if (dsDetalleCotizacion.getDetalleCotizacionCotizacionSeleccionadaProveedor3(i) > 0)
					totalCotizacionSeleccionada += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor3(i) * descuentos[2];
				if (dsDetalleCotizacion.getDetalleCotizacionCotizacionSeleccionadaProveedor4(i) > 0)
					totalCotizacionSeleccionada += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor4(i) * descuentos[3];
				if (dsDetalleCotizacion.getDetalleCotizacionCotizacionSeleccionadaProveedor5(i) > 0)
					totalCotizacionSeleccionada += cantidad * dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor5(i) * descuentos[4];
			}
			
			// setea los totales en el datastore
			setCotizacionesCompraTotalProveedor1(total_proveedor1);
			setCotizacionesCompraTotalProveedor2(total_proveedor2);
			setCotizacionesCompraTotalProveedor3(total_proveedor3);
			setCotizacionesCompraTotalProveedor4(total_proveedor4);
			setCotizacionesCompraTotalProveedor5(total_proveedor5);
			setCotizacionesCompraTotalCotizacionSeleccionada(totalCotizacionSeleccionada);
		}
	}
	
	/**
	 * @author demian
	 * genera recorriendo todos los artículos la selección optima en función de lso precios más bajos de cada artículo
	 * Si el artículo ya tiene selección, No la cambia.
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void generaCotizacionOptima(int row) throws DataStoreException, SQLException {
		gotoRow(row);
		generaCotizacionOptima();
	}
	
	/**
	 * @author demian
	 * genera recorriendo todos los artículos la selección optima en función de lso precios más bajos de cada artículo
	 * Si el artículo ya tiene selección, No la cambia.
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void generaCotizacionOptima() throws DataStoreException, SQLException {
		DetalleCotizacionModel dsDetalleCotizacion = new DetalleCotizacionModel("inventario", "inventario");
		
		dsDetalleCotizacion.retrieve("detalle_cotizacion.cotizacion_compra_id = " + getCotizacionesCompraCotizacionCompraId());
		dsDetalleCotizacion.waitForRetrieve();
		
		// tabla con los descuentos ofrecidos por proveedor
		double descuentos[] = {1 - (getCotizacionesCompraBonificacionProveedor1() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor2() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor3() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor4() / 100),
				1 - (getCotizacionesCompraBonificacionProveedor5() / 100),
				};
		// rocorro los descuentos para setear en 1 si el descuento es cero
		for (int i = 0 ; i < 5 ; i++)
			if (descuentos[i] == 0)
				descuentos[i] = 1;
		
		// si recueró registros, itera controlando, validando y generando la cotización óptima
		if (dsDetalleCotizacion.getRowCount() > 0) {
			for (int row = 0 ; row < dsDetalleCotizacion.getRowCount(); row++) {
				// verifica que el detalle esté oc
				int proveedor = dsDetalleCotizacion.verificaIntegridadDetalle(row);
				
				// Si el proveedor es distinto de cero significa que ya hay 
				// proveedor seleccionado y no hago nada, sino selecciono 
				// el precio mas bajo
				if (proveedor == 0) {
					// selecciono precio optimo
					double precios[] = {dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor1(row) * descuentos[0],
							dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor2(row) * descuentos[1],
							dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor3(row) * descuentos[2],
							dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor4(row) * descuentos[3],
							dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor5(row) * descuentos[4],
							};
					int seleccion = 0;
					
					for (int i = 0; i < 4; i++) {
						if (precios[i] > 0 && precios[i + 1] > 0){
							if (precios[i] < precios[i + 1]){
								if (precios[i] < precios[seleccion] || i == seleccion)
									seleccion = i;
								else if (precios[i + 1] < precios[seleccion])
									seleccion = i + 1;
							} 
						} else if (precios[i] > 0 && (precios[i] < precios[seleccion] || i == seleccion))
							seleccion = i;
						else if (precios[i + 1] > 0 && precios[i + 1] < precios[seleccion])
							seleccion = i + 1;
					}
					
					// verifica si la selección es sobre precio cero. 
					// Implica que NO hay precio para ningún proveedor, y por lo tanto no selecciona ninguno
					if (! (precios[seleccion] > 0) )
						seleccion = -1;
					
					// marca el proveedor óptimo
					switch(seleccion) {
					case 0:
						dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor1(row, 1);
						break;
					case 1:
						dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor2(row, 1);
						break;
					case 2:
						dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor3(row, 1);
						break;
					case 3:
						dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor4(row, 1);
						break;
					case 4:
						dsDetalleCotizacion.setDetalleCotizacionCotizacionSeleccionadaProveedor5(row, 1);
						break;
					}
				}
			}
			
			// grabo las seleciones y mando a calcular totales nuevamente
			dsDetalleCotizacion.update();
			setTotalesProveedor();
		}
		
	}
	
	
	/**
	 * @author demian
	 * Permite validar y generar las ordenes de compra asociadas a una cotización en función de la selección
	 * @param row
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void generaOrdenesCompra(int row,String host,DBConnection conn) throws DataStoreException, SQLException {
		gotoRow(row);
		generaOrdenesCompra(host,conn);
	}
	
	/**
	 * @param host
	 * @param conn
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void generaOrdenesCompra(String host,DBConnection conn) throws DataStoreException, SQLException {
		DetalleCotizacionModel dsDetalleCotizacion = new DetalleCotizacionModel("inventario", "inventario");
		DetalleSCModel dsDetalleSC = new DetalleSCModel("inventario","inventario");
		SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel("inventario","inventario");
		OrdenesCompraModel dsOrdenCompra = new OrdenesCompraModel("inventario","inventario");
		int detalleSCId = -1;

		int ocIdProveedor1 = -1, ocIdProveedor2 = -1,ocIdProveedor3 = -1,ocIdProveedor4 = -1,ocIdProveedor5 = -1;
		
		// recupera los detalles de la cotización actual
		dsDetalleCotizacion.retrieve(conn, "detalle_cotizacion.cotizacion_compra_id = " + getCotizacionesCompraCotizacionCompraId());
		dsDetalleSC.retrieve(conn, "detalle_sc.cotizacion_compra_id = "
				+ getCotizacionesCompraCotizacionCompraId()
				+ " and detalle_sc.orden_compra_id is null");
		dsDetalleCotizacion.waitForRetrieve();
		dsDetalleSC.waitForRetrieve();
		
		conn.beginTransaction();

		// si recueró registros, itera controlando, validando y generando las OC
		if (dsDetalleCotizacion.getRowCount() > 0) {
			
			for (int row = 0 ; row < dsDetalleCotizacion.getRowCount(); row++) {
				// verifico que la SC asociada esté en estado cotizada, sino aborto la operación
				dsSolicitudCompra.reset();
				dsSolicitudCompra.retrieve("solicitudes_compra.solicitud_compra_id = " + dsDetalleCotizacion.getDetalleScSolicitudCompraId(row));
				dsSolicitudCompra.waitForRetrieve();
				dsSolicitudCompra.gotoFirst();
				if (dsSolicitudCompra.getRowCount() == 0)
					throw new DataStoreException(
							"No se recuperó ningina Solicitud asociada al artículo: "
									+ dsDetalleCotizacion.getArticulosNombre(row)
									+ " - "
									+ dsDetalleCotizacion.getArticulosDescripcion(row));
				if (dsSolicitudCompra.getRowCount() > 1)
					throw new DataStoreException(
							"Se recuperó más de una Solicitud asociada al artículo: "
									+ dsDetalleCotizacion.getArticulosNombre(row)
									+ " - "
									+ dsDetalleCotizacion.getArticulosDescripcion(row));
				if (!dsSolicitudCompra.getEstadoActual().equalsIgnoreCase("0006.0008"))
					throw new DataStoreException(
							"La Solicitud asociada al artículo: "
									+ dsDetalleCotizacion.getArticulosNombre(row)
									+ " - "
									+ dsDetalleCotizacion.getArticulosDescripcion(row)
									+ " No está en estado Cotizada. Primero debe finalizar la cotización");
				
				// verifica que el detalle esté oc
				int proveedor = dsDetalleCotizacion.verificaIntegridadDetalle(row);
				// verifica que el proveedor seleecionado tenga FK al proveedor
				switch (proveedor) {
				case 1:
					if (!(getCotizacionesCompraEntidadIdProveedor1() > 0))
						throw new DataStoreException(
								"Existe articulo seleccionado para el proveedor 1, pero no se indica cual es el proveedor");

					// busca el detalle de SC correspondiente
					detalleSCId = -1;
					for (int j = 0; j < dsDetalleSC.getRowCount(); j++) {
						if (dsDetalleSC.getDetalleScDetalleScId(j) == dsDetalleCotizacion
								.getDetalleCotizacionDetalleScId(row))
							detalleSCId = j;
					}
					
					// si está ok, y no está en una OC YA va armando la data
					// para armar la OC
					if (detalleSCId != -1) {
						if (ocIdProveedor1 == -1) {
							// no se creó OC aún para el proveedor, la crea
							ocIdProveedor1 = dsOrdenCompra.insertRow();
							dsOrdenCompra.setCurrentWebsiteUserId(getCurrentWebsiteUserId());
							dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(getCotizacionesCompraEntidadIdProveedor1());
							dsOrdenCompra.setOrdenesCompraCondicionCompraId(getCotizacionesCompraCondicionCompraIdProveedor1());
							dsOrdenCompra.setOrdenesCompraDescuento((float)getCotizacionesCompraBonificacionProveedor1());
							dsOrdenCompra.update(conn);
						}
						dsDetalleSC.setDetalleScOrdenCompraId(detalleSCId, dsOrdenCompra
								.getOrdenesCompraOrdenCompraId(ocIdProveedor1));
						dsDetalleSC.setDetalleScCantidadPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionCantidad(row));
						dsDetalleSC.setDetalleScUnidadMedidaPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionUnidadMedidaId(row));
						dsDetalleSC.setDetalleScMontoUnitario(detalleSCId, (float)dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor1(row));
					}
					break;
				case 2:
					if (!(getCotizacionesCompraEntidadIdProveedor2() > 0))
						throw new DataStoreException(
								"Existe articulo seleccionado para el proveedor 2, pero no se indica cual es el proveedor");

					// busca el detalle de SC correspondiente
					detalleSCId = -1;
					for (int j = 0; j < dsDetalleSC.getRowCount(); j++) {
						if (dsDetalleSC.getDetalleScDetalleScId(j) == dsDetalleCotizacion
								.getDetalleCotizacionDetalleScId(row))
							detalleSCId = j;
					}
					
					// si está ok, y no está en ona OC YA va armando la data
					// para armar la OC
					if (detalleSCId != -1) {
						if (ocIdProveedor2 == -1) {
							// no se creó OC aún para el proveedor, la crea
							ocIdProveedor2 = dsOrdenCompra.insertRow();
							dsOrdenCompra.setCurrentWebsiteUserId(getCurrentWebsiteUserId());
							dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(getCotizacionesCompraEntidadIdProveedor2());
							dsOrdenCompra.setOrdenesCompraCondicionCompraId(getCotizacionesCompraCondicionCompraIdProveedor2());
							dsOrdenCompra.setOrdenesCompraDescuento((float)getCotizacionesCompraBonificacionProveedor2());
							dsOrdenCompra.update(conn);
						}
						dsDetalleSC.setDetalleScOrdenCompraId(detalleSCId, dsOrdenCompra
								.getOrdenesCompraOrdenCompraId(ocIdProveedor2));
						dsDetalleSC.setDetalleScCantidadPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionCantidad(row));
						dsDetalleSC.setDetalleScUnidadMedidaPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionUnidadMedidaId(row));
						dsDetalleSC.setDetalleScMontoUnitario(detalleSCId, (float)dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor2(row));
					}
					break;
				case 3:
					if (!(getCotizacionesCompraEntidadIdProveedor3() > 0))
						throw new DataStoreException(
								"Existe articulo seleccionado para el proveedor 3, pero no se indica cual es el proveedor");

					// busca el detalle de SC correspondiente
					detalleSCId = -1;
					for (int j = 0; j < dsDetalleSC.getRowCount(); j++) {
						if (dsDetalleSC.getDetalleScDetalleScId(j) == dsDetalleCotizacion
								.getDetalleCotizacionDetalleScId(row))
							detalleSCId = j;
					}
					
					// si está ok, y no está en ona OC YA va armando la data
					// para armar la OC
					if (detalleSCId != -1) {
						if (ocIdProveedor3 == -1) {
							// no se creó OC aún para el proveedor, la crea
							ocIdProveedor3 = dsOrdenCompra.insertRow();
							dsOrdenCompra.setCurrentWebsiteUserId(getCurrentWebsiteUserId());
							dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(getCotizacionesCompraEntidadIdProveedor3());
							dsOrdenCompra.setOrdenesCompraCondicionCompraId(getCotizacionesCompraCondicionCompraIdProveedor3());
							dsOrdenCompra.setOrdenesCompraDescuento((float)getCotizacionesCompraBonificacionProveedor3());
							dsOrdenCompra.update(conn);
						}
						dsDetalleSC.setDetalleScOrdenCompraId(detalleSCId, dsOrdenCompra
								.getOrdenesCompraOrdenCompraId(ocIdProveedor3));
						dsDetalleSC.setDetalleScCantidadPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionCantidad(row));
						dsDetalleSC.setDetalleScUnidadMedidaPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionUnidadMedidaId(row));
						dsDetalleSC.setDetalleScMontoUnitario(detalleSCId, (float)dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor3(row));
					}
					break;
				case 4:
					if (!(getCotizacionesCompraEntidadIdProveedor4() > 0))
						throw new DataStoreException(
								"Existe articulo seleccionado para el proveedor 4, pero no se indica cual es el proveedor");

					// busca el detalle de SC correspondiente
					detalleSCId = -1;
					for (int j = 0; j < dsDetalleSC.getRowCount(); j++) {
						if (dsDetalleSC.getDetalleScDetalleScId(j) == dsDetalleCotizacion
								.getDetalleCotizacionDetalleScId(row))
							detalleSCId = j;
					}
					
					// si está ok, y no está en ona OC YA va armando la data
					// para armar la OC
					if (detalleSCId != -1) {
						if (ocIdProveedor4 == -1) {
							// no se creó OC aún para el proveedor, la crea
							ocIdProveedor4 = dsOrdenCompra.insertRow();
							dsOrdenCompra.setCurrentWebsiteUserId(getCurrentWebsiteUserId());
							dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(getCotizacionesCompraEntidadIdProveedor4());
							dsOrdenCompra.setOrdenesCompraCondicionCompraId(getCotizacionesCompraCondicionCompraIdProveedor4());
							dsOrdenCompra.setOrdenesCompraDescuento((float)getCotizacionesCompraBonificacionProveedor4());
							dsOrdenCompra.update(conn);
						}
						dsDetalleSC.setDetalleScOrdenCompraId(detalleSCId, dsOrdenCompra
								.getOrdenesCompraOrdenCompraId(ocIdProveedor4));
						dsDetalleSC.setDetalleScCantidadPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionCantidad(row));
						dsDetalleSC.setDetalleScUnidadMedidaPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionUnidadMedidaId(row));
						dsDetalleSC.setDetalleScMontoUnitario(detalleSCId, (float)dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor4(row));
					}
					break;
				case 5:
					if (!(getCotizacionesCompraEntidadIdProveedor5() > 0))
						throw new DataStoreException(
								"Existe articulo seleccionado para el proveedor 5, pero no se indica cual es el proveedor");
					// busca el detalle de SC correspondiente
					detalleSCId = -1;
					for (int j = 0; j < dsDetalleSC.getRowCount(); j++) {
						if (dsDetalleSC.getDetalleScDetalleScId(j) == dsDetalleCotizacion
								.getDetalleCotizacionDetalleScId(row))
							detalleSCId = j;
					}
					
					// si está ok, y no está en ona OC YA va armando la data
					// para armar la OC
					if (detalleSCId != -1) {
						if (ocIdProveedor5 == -1) {
							// no se creó OC aún para el proveedor, la crea
							ocIdProveedor5 = dsOrdenCompra.insertRow();
							dsOrdenCompra.setCurrentWebsiteUserId(getCurrentWebsiteUserId());
							dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(getCotizacionesCompraEntidadIdProveedor5());
							dsOrdenCompra.setOrdenesCompraCondicionCompraId(getCotizacionesCompraCondicionCompraIdProveedor5());
							dsOrdenCompra.setOrdenesCompraDescuento((float)getCotizacionesCompraBonificacionProveedor5());
							dsOrdenCompra.update(conn);
						}
						dsDetalleSC.setDetalleScOrdenCompraId(detalleSCId, dsOrdenCompra
								.getOrdenesCompraOrdenCompraId(ocIdProveedor5));
						dsDetalleSC.setDetalleScCantidadPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionCantidad(row));
						dsDetalleSC.setDetalleScUnidadMedidaPedida(detalleSCId, dsDetalleCotizacion.getDetalleCotizacionUnidadMedidaId(row));
						dsDetalleSC.setDetalleScMontoUnitario(detalleSCId, (float)dsDetalleCotizacion.getDetalleCotizacionMontoUnitarioProveedor5(row));
					}
					break;
				default:
					break;
				} 
			}
			
			// update the SC states			
			dsDetalleSC.update(conn);
			dsDetalleSC.filter("detalle_sc.orden_compra_id != null");
			SolicitudCompraTransiciones.agregarEnOc(conn,dsDetalleSC, host,	getCurrentWebsiteUserId());
		}
		
	}
	
	// $ENDCUSTOMMETHODS$

}
