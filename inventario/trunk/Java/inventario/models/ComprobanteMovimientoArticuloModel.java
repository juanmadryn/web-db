package inventario.models;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import infraestructura.models.BaseModel;
import infraestructura.models.WebsiteUserModel;

import com.salmonllc.sql.DBConnection;
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
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA = "comprobante_movimiento_articulo.user_id_autoriza";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA = "comprobante_movimiento_articulo.user_id_confirma";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO = "comprobante_movimiento_articulo.estado";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID = "comprobante_movimiento_articulo.tipo_movimiento_articulo_id";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA = "comprobante_movimiento_articulo.fecha";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_OBSERVACIONES = "comprobante_movimiento_articulo.observaciones";
	public static final String COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID = "comprobante_movimiento_articulo.recepcion_compra_id";
	public static final String TIPO_MOVIMIENTO_ARTICULO_NOMBRE = "tipo_movimiento_articulo.nombre";
	public static final String TIPO_MOVIMIENTO_ARTICULO_POSITIVO = "tipo_movimiento_articulo.positivo";
	public static final String TIPO_MOVIMIENTO_ARTICULO_RESERVA = "tipo_movimiento_articulo.reserva";
	public static final String TIPO_MOVIMIENTO_ARTICULO_IMPRESION = "tipo_movimiento_articulo.impresion";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String ESTADOS_NOMBRE = "estados.nombre";
	public static final String WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO = "website_user_preparador.nombre_completo";
	public static final String WEBSITE_USER_CONFIRMA_NOMBRE_COMPLETO = "website_user_confirma.nombre_completo";
	public static final String LEGAJOS_APEYNOM = "legajos.APEYNOM";
	public static final String LEGAJOS_APEYNOM_AUTORIZA = "legajos.APEYNOM_AUTORIZA";
	public static final String CURRENT_WEBSITE_USER_ID = "website_user.user_id";

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
			addTableAlias(computeTableName("recepciones_compras"),
					"recepciones_compras");
			addTableAlias("infraestructura.estados", "estados");
			addTableAlias("infraestructura.website_user",
					"website_user_preparador");
			addTableAlias("legajos", "legajos_retira");
			addTableAlias("legajos", "legajos_autoriza");

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
					"user_id_autoriza", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"user_id_confirma", DataStore.DATATYPE_INT, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"estado", DataStore.DATATYPE_STRING, false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"tipo_movimiento_articulo_id", DataStore.DATATYPE_INT,
					false, true,
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
			addColumn(computeTableName("comprobante_movimiento_articulo"),
					"fecha", DataStore.DATATYPE_DATETIME, false, true,
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
			addColumn(computeTableName("tipo_movimiento_articulo"), "positivo",
					DataStore.DATATYPE_STRING, false, false,
					TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
			addColumn(computeTableName("tipo_movimiento_articulo"), "reserva",
					DataStore.DATATYPE_STRING, false, false,
					TIPO_MOVIMIENTO_ARTICULO_RESERVA);
			addColumn(computeTableName("tipo_movimiento_articulo"),
					"impresion", DataStore.DATATYPE_STRING, false, false,
					TIPO_MOVIMIENTO_ARTICULO_IMPRESION);
			addColumn(computeTableName("estados"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ESTADOS_NOMBRE);
			addColumn(computeTableName("website_user_preparador"),
					"nombre_completo", DataStore.DATATYPE_STRING, false, false,
					WEBSITE_USER_PREPARADOR_NOMBRE_COMPLETO);
			addColumn(computeTableName("legajos_retira"), "APEYNOM",
					DataStore.DATATYPE_STRING, false, false, LEGAJOS_APEYNOM);
			addColumn(computeTableName("legajos_autoriza"), "APEYNOM",
					DataStore.DATATYPE_STRING, false, false,
					LEGAJOS_APEYNOM_AUTORIZA);

			// add buckets
			addBucket(CURRENT_WEBSITE_USER_ID, DATATYPE_INT);

			// add joins
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.tipo_movimiento_articulo_id"),
					computeTableAndFieldName("tipo_movimiento_articulo.tipo_movimiento_articulo_id"),
					false);
			// add joins
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.recepcion_compra_id"),
					computeTableAndFieldName("recepciones_compras.recepcion_compra_id"),
					true);
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.estado"),
					"estados.estado", false);
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.user_id_preparador"),
					"website_user_preparador.user_id", false);
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.user_id_retira"),
					computeTableAndFieldName("legajos_retira.nro_legajo"), true);
			addJoin(
					computeTableAndFieldName("comprobante_movimiento_articulo.user_id_autoriza"),
					computeTableAndFieldName("legajos_autoriza.nro_legajo"),
					true);

			// set order by
			setOrderBy(COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID	+ " DESC");

			// add validations
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_ALMACEN_ID,
					"El almacen es requerido");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_PREPARADOR,
					"El usuario preparador es obligatorio");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_ESTADO,
					"El estado es obligatorio");
			addRequiredRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
					"El tipo de movimiento de artículo es obligatorio");
			addRequiredRule(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA,
					"La fecha es obligatoria");

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
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
					"inventario.tipo_movimiento_articulo",
					"'inventario.tipo_movimiento_articulo.tipo_movimiento_articulo_id = ' + comprobante_movimiento_articulo.tipo_movimiento_articulo_id",
					"positivo", TIPO_MOVIMIENTO_ARTICULO_POSITIVO,
					"Tipo de movimiento inexistente");
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID,
					"inventario.tipo_movimiento_articulo",
					"'inventario.tipo_movimiento_articulo.tipo_movimiento_articulo_id = ' + comprobante_movimiento_articulo.tipo_movimiento_articulo_id",
					"reserva", TIPO_MOVIMIENTO_ARTICULO_RESERVA,
					"Tipo de movimiento inexistente");
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_RETIRA,
					"inventario.legajos",
					"'inventario.legajos.nro_legajo = ' + comprobante_movimiento_articulo.user_id_retira",
					"APEYNOM", LEGAJOS_APEYNOM, "Legajo inexistente");
			addLookupRule(
					COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA,
					"inventario.legajos",
					"'inventario.legajos.nro_legajo = ' + comprobante_movimiento_articulo.user_id_autoriza",
					"APEYNOM", LEGAJOS_APEYNOM_AUTORIZA, "Legajo inexistente");

			setAutoIncrement(
					COMPROBANTE_MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID,
					true);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated
		// add aliases
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
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_autoriza column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdAutoriza()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_autoriza column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdAutoriza(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_autoriza
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdAutoriza(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_autoriza
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdAutoriza(int row,
			int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_AUTORIZA, newValue);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_confirma column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdConfirma()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.user_id_confirma column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloUserIdConfirma(int row)
			throws DataStoreException {
		return getInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_confirma
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdConfirma(int newValue)
			throws DataStoreException {
		setInt(COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA, newValue);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.user_id_confirma
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloUserIdConfirma(int row,
			int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_USER_ID_CONFIRMA, newValue);
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
	public java.sql.Timestamp getComprobanteMovimientoArticuloFecha()
			throws DataStoreException {
		return getDateTime(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA);
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
	public java.sql.Timestamp getComprobanteMovimientoArticuloFecha(int row)
			throws DataStoreException {
		return getDateTime(row, COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA);
	}

	/**
	 * Set the value of the comprobante_movimiento_articulo.fecha column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloFecha(
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA, newValue);
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
			java.sql.Timestamp newValue) throws DataStoreException {
		setDateTime(row, COMPROBANTE_MOVIMIENTO_ARTICULO_FECHA, newValue);
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
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.recepcion_compra_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getComprobanteMovimientoArticuloRecepcionCompraId()
			throws DataStoreException {
		return getInt(COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the
	 * comprobante_movimiento_articulo.recepcion_compra_id column for the
	 * specified row.
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
	 * Set the value of the comprobante_movimiento_articulo.recepcion_compra_id
	 * column for the current row.
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
	 * Set the value of the comprobante_movimiento_articulo.recepcion_compra_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setComprobanteMovimientoArticuloRecepcionCompraId(int row,
			int newValue) throws DataStoreException {
		setInt(row, COMPROBANTE_MOVIMIENTO_ARTICULO_RECEPCION_COMPRA_ID,
				newValue);
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

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.positivo column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloPositivo() throws DataStoreException {
		return getString(TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.positivo column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloPositivo(int row)
			throws DataStoreException {
		return getString(row, TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.positivo column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloPositivo(String newValue)
			throws DataStoreException {
		setString(TIPO_MOVIMIENTO_ARTICULO_POSITIVO, newValue);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.positivo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloPositivo(int row, String newValue)
			throws DataStoreException {
		setString(row, TIPO_MOVIMIENTO_ARTICULO_POSITIVO, newValue);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.reserva column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloReserva() throws DataStoreException {
		return getString(TIPO_MOVIMIENTO_ARTICULO_RESERVA);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.reserva column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloReserva(int row)
			throws DataStoreException {
		return getString(row, TIPO_MOVIMIENTO_ARTICULO_RESERVA);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.reserva column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloReserva(String newValue)
			throws DataStoreException {
		setString(TIPO_MOVIMIENTO_ARTICULO_RESERVA, newValue);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.reserva column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloReserva(int row, String newValue)
			throws DataStoreException {
		setString(row, TIPO_MOVIMIENTO_ARTICULO_RESERVA, newValue);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.impresion column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloImpresion()
			throws DataStoreException {
		return getString(TIPO_MOVIMIENTO_ARTICULO_IMPRESION);
	}

	/**
	 * Retrieve the value of the tipo_movimiento_articulo.impresion column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoMovimientoArticuloImpresion(int row)
			throws DataStoreException {
		return getString(row, TIPO_MOVIMIENTO_ARTICULO_IMPRESION);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.impresion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloImpresion(String newValue)
			throws DataStoreException {
		setString(TIPO_MOVIMIENTO_ARTICULO_IMPRESION, newValue);
	}

	/**
	 * Set the value of the tipo_movimiento_articulo.impresion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoMovimientoArticuloImpresion(int row, String newValue)
			throws DataStoreException {
		setString(row, TIPO_MOVIMIENTO_ARTICULO_IMPRESION, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

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
	 * Retrieve the value of the user_recibe.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getLegajoApeynom() throws DataStoreException {
		return getString(LEGAJOS_APEYNOM);
	}

	/**
	 * Retrieve the value of the user_recibe.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getLegajoApeynom(int row) throws DataStoreException {
		return getString(row, LEGAJOS_APEYNOM);
	}

	/**
	 * Set the value of the user_recibe.nombre_completo column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setLegajoApeynom(String newValue) throws DataStoreException {
		setString(LEGAJOS_APEYNOM, newValue);
	}

	/**
	 * Set the value of the user_recibe.nombre_completo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setLegajoApeynom(int row, String newValue)
			throws DataStoreException {
		setString(row, LEGAJOS_APEYNOM, newValue);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadosNombre() throws DataStoreException {
		return getString(ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the estados.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEstadosNombre(int row) throws DataStoreException {
		return getString(row, ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEstadosNombre(String newValue) throws DataStoreException {
		setString(ESTADOS_NOMBRE, newValue);
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
	public void setEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESTADOS_NOMBRE, newValue);
	}

	@Override
	public String getEstadoActual() throws DataStoreException {
		return getComprobanteMovimientoArticuloEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getComprobanteMovimientoArticuloComprobanteMovimientoId();
	}

	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		WebsiteUserModel user = null;
		int currentUser = 0;
		if (getRow() != -1) {
			currentUser = getCurrentWebsiteUserId();
		}
		if (getComprobanteMovimientoArticuloUserIdPreparador() == 0)
			setComprobanteMovimientoArticuloUserIdPreparador(getCurrentWebsiteUserId());
		// si el no está indicado el usuario que autoriza, seteo el legajo del usuario actual
		if (getComprobanteMovimientoArticuloUserIdAutoriza() == 0) {
			if (user == null)
				user = new WebsiteUserModel("infraestructura",
						"infraestructura");
			if (user.getRow() != -1 && user.getWebsiteUserUserId() != currentUser) {
				user.retrieve("user_id =" + currentUser);
				user.waitForRetrieve();
			}			
			if (user.gotoFirst() && user.getWebsiteUserNroLegajo() != 0)
				setComprobanteMovimientoArticuloUserIdAutoriza(user
						.getWebsiteUserNroLegajo());
		}
		// si no está indicado el usuario que retira, seteo el legajo del usuario actual
		if (getComprobanteMovimientoArticuloUserIdRetira() == 0) {
			if (user == null)
				user = new WebsiteUserModel("infraestructura",
						"infraestructura");			
			if (user.getRow() != -1 && user.getWebsiteUserUserId() != currentUser) {
				user.retrieve("user_id =" + currentUser);
				user.waitForRetrieve();
			}			
			if (user.gotoFirst() && user.getWebsiteUserNroLegajo() != 0)
				setComprobanteMovimientoArticuloUserIdRetira(user
						.getWebsiteUserNroLegajo());
		}
		if (getComprobanteMovimientoArticuloEstado() == null)
			setComprobanteMovimientoArticuloEstado("0010.0001");
		if (getComprobanteMovimientoArticuloFecha() == null)
			setComprobanteMovimientoArticuloFecha(new Timestamp((Calendar
					.getInstance().getTimeInMillis())));

		super.update(conn, handleTrans);
	}

	// $ENDCUSTOMMETHODS$

}
