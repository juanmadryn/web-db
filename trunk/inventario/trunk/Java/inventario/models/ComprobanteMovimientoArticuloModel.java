package inventario.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ComprobanteMovimientoArticuloModel: A SOFIA generated model
 */
public class ComprobanteMovimientoArticuloModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1154387961837240619L;
	// constants for columns
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID = "comprobante_movimiento_articulo.comprobante_movimiento_id";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID = "comprobante_movimiento_articulo.almacen_id";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA = "comprobante_movimiento_articulo.user_id_retira";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR = "comprobante_movimiento_articulo.user_id_preparador";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO = "comprobante_movimiento_articulo.estado";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID = "comprobante_movimiento_articulo.tipo_movimiento_articulo_id";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA = "comprobante_movimiento_articulo.fecha";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES = "comprobante_movimiento_articulo.observaciones";
	public static final String TIPO_MOVIMIENTO_ARTICULO_NOMBRE = "tipo_movimiento_articulo.nombre";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID = "comprobante_movimiento_articulo.recepcion_compra_id";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String ESTADOS_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_RETIRA_NOMBRE_COMPLETO = "website_user_retira.nombre_completo";
	public static final String WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO = "website_user_preparador.nombre_completo";

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ComprobanteMovimientoArticuloModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ComprobanteMovimientoArticuloModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ComprobanteMovimientoArticuloModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ComprobanteMovimientoArticuloModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("comprobante_movimiento_articulo"),
					"comprobante_movimiento_articulo");
			addTableAlias(computeTableName("tipo_movimiento_articulo"),
					"tipo_movimiento_articulo");
			addTableAlias("recepciones_compras", "recepciones_compras");

			// add columns
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"comprobante_movimiento_id", DataStore.DATATYPE_INT, true,
					true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"almacen_id", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"user_id_retira", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"user_id_preparador", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"estado", DataStore.DATATYPE_STRING, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"tipo_movimiento_articulo_id", DataStore.DATATYPE_INT,
					false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"fecha", DataStore.DATATYPE_DATE, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"observaciones", DataStore.DATATYPE_STRING, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"recepcion_compra_id", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID);
			addColumn(computeTableName("tipo_movimiento_articulo"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
		

			// add joins
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.tipo_movimiento_articulo_id"),
					computeTableAndFieldName("tipo_movimiento_articulo.tipo_movimiento_articulo_id"),
					false);
			// add joins
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.recepcion_compra_id"),
					computeTableAndFieldName("recepciones_compras.recepcion_compra_id"),
					false);

			// set order by
			setOrderBy(computeTableAndFieldName("comprobante_movimiento_articulo.comprobante_movimiento_id")
					+ " ASC");

			// add validations
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID,
					"El almacen es requerido");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR,
					"El usuario preparador es obligatorio");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA,
					"El usuario que retira es obligatorio");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO,
					"El estado es obligatorio");
			addRequiredRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
					"El tipo de movimiento de artículo es obligatorio");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA,
					"La fecha es obligatoria");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated
		// add aliases
		addTableAlias(computeTableName("infraestructura.estados"), "estados");
		addTableAlias(computeTableName("infraestructura.website_user"),
				"website_user_retira");
		addTableAlias(computeTableName("infraestructura.website_user"),
				"website_user_preparador");

		// add columns
		addColumn(computeTableName("estados"), "nombre",
				DataStore.DATATYPE_STRING, false, false, ESTADOS_NOMBRE);
		addColumn(computeTableName("website_user_retira"), "nombre_completo",
				DataStore.DATATYPE_STRING, false, false,
				WEBSITE_USER_RETIRA_NOMBRE_COMPLETO);
		addColumn(computeTableName("website_user_preparador"),
				"nombre_completo", DataStore.DATATYPE_STRING, false, false,
				WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO);

		// add joins
		addJoin(
				computeTableAndFieldName("comprobante_movimiento_articulo.estado"),
				computeTableAndFieldName("estados.estado"), true);
		addJoin(
				computeTableAndFieldName("comprobante_movimiento_articulo.user_id_retira"),
				computeTableAndFieldName("website_user.user_id"), true);
		addJoin(
				computeTableAndFieldName("comprobante_movimiento_articulo.user_id_preparador"),
				computeTableAndFieldName("website_user.user_id"), true);

		// add lookup rules
		try {
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = \"' + comprobante_movimiento_articulo.estado + '\"' ",
					"nombre", computeTableAndFieldName("estados.nombre"),
					"Estado inexistente");
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + comprobante_movimiento_articulo.user_id_preparador",
					"nombre_completo", WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO,
					"Estado inexistente");
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + comprobante_movimiento_articulo.user_id_retira",
					"nombre_completo", WEBSITE_USER_RETIRA_NOMBRE_COMPLETO,
					"Estado inexistente");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.comprobante_movimiento_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloComprobanteMovimientoId()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.comprobante_movimiento_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloComprobanteMovimientoId(int row)
			throws DataStoreException {
		return getInt(row,
				COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Set the value of the
	 * comprobante_movimiento_articulo.comprobante_movimiento_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloComprobanteMovimientoId(
			int newValue) throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID,
				newValue);
	}

	/**
	 * Set the value of the
	 * comprobante_movimiento_articulo.comprobante_movimiento_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloComprobanteMovimientoId(
			int row, int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID,
				newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.almacen_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloAlmacenId()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.almacen_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloAlmacenId(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.almacen_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloAlmacenId(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.almacen_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloAlmacenId(int row, int newValue)
			throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID, newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.user_id_retira
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdRetira()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.user_id_retira
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdRetira(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_retira
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdRetira(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_retira
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdRetira(int row,
			int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA, newValue);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_preparador column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdPreparador()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_preparador column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdPreparador(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_preparador
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdPreparador(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_preparador
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdPreparador(int row,
			int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR,
				newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.estado column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteMovimientoArticuloEstado()
			throws DataStoreException {
		return getString(COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.estado column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteMovimientoArticuloEstado(int row)
			throws DataStoreException {
		return getString(row, COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.estado column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloEstado(String newValue)
			throws DataStoreException {
		setString(COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.estado column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.tipo_movimiento_articulo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloTipoMovimientoArticuloId()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.tipo_movimiento_articulo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloTipoMovimientoArticuloId(int row)
			throws DataStoreException {
		return getInt(row,
				COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Set the value of the
	 * comprobante_movimiento_articulo.tipo_movimiento_articulo_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloTipoMovimientoArticuloId(
			int newValue) throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
				newValue);
	}

	/**
	 * Set the value of the
	 * comprobante_movimiento_articulo.tipo_movimiento_articulo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloTipoMovimientoArticuloId(
			int row, int newValue) throws DataStoreException {
		setInt(row,
				COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
				newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.fecha column
	 * for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getComprobanteMovimientoArticuloFecha()
			throws DataStoreException {
		return getDate(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.fecha column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getComprobanteMovimientoArticuloFecha(int row)
			throws DataStoreException {
		return getDate(row, COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.fecha column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloFecha(java.sql.Date newValue)
			throws DataStoreException {
		setDate(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloFecha(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.observaciones
	 * column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteMovimientoArticuloObservaciones()
			throws DataStoreException {
		return getString(COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.observaciones
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getComprobanteMovimientoArticuloObservaciones(int row)
			throws DataStoreException {
		return getString(row, COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.observaciones column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloObservaciones(String newValue)
			throws DataStoreException {
		setString(COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.observaciones column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloObservaciones(int row,
			String newValue) throws DataStoreException {
		setString(row, COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.recepcion_compra_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloRecepcionCompraId()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the comprobante_movimiento_articulo.recepcion_compra_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloRecepcionCompraId(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.recepcion_compra_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloRecepcionCompraId(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.recepcion_compra_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloRecepcionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the tipo_movimiento_articulo.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloNombre() throws DataStoreException {
		return getString(TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloNombre(int row)
			throws DataStoreException {
		return getString(row, TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloNombre(String newValue)
			throws DataStoreException {
		setString(TIPO_MOVIMIENTO_ARTICULO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, TIPO_MOVIMIENTO_ARTICULO_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	@Override
	public String getEstadoActual() throws DataStoreException {
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return this.getComprobanteMovimientoArticuloComprobanteMovimientoId();
	}
	// $ENDCUSTOMMETHODS$

}
