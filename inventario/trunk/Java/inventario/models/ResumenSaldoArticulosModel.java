package inventario.models;

import infraestructura.models.BaseModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ResumenSaldoArticulosModel: A SOFIA generated model
 */
public class ResumenSaldoArticulosModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377732412781479286L;
	// constants for columns
	public static final String RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID = "resumen_saldo_articulos.resumen_saldo_articulo_id";
	public static final String RESUMEN_SALDO_ARTICULOS_ALMACEN_ID = "resumen_saldo_articulos.almacen_id";
	public static final String RESUMEN_SALDO_ARTICULOS_ARTICULO_ID = "resumen_saldo_articulos.articulo_id";
	public static final String RESUMEN_SALDO_ARTICULOS_PERIODO = "resumen_saldo_articulos.periodo";
	public static final String RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO = "resumen_saldo_articulos.stock_en_mano";
	public static final String RESUMEN_SALDO_ARTICULOS_RESERVADO = "resumen_saldo_articulos.reservado";
	public static final String RESUMEN_SALDO_ARTICULOS_EN_PROCESO = "resumen_saldo_articulos.en_proceso";
	public static final String RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS = "resumen_saldo_articulos.total_ingresos";
	public static final String RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS = "resumen_saldo_articulos.total_egresos";
	public static final String RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS = "resumen_saldo_articulos.cant_transacciones_ingresos";
	public static final String RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS = "resumen_saldo_articulos.cant_transacciones_egresos";
	public static final String ALMACENES_NOMBRE = "almacenes.nombre";	
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ResumenSaldoArticulosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ResumenSaldoArticulosModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ResumenSaldoArticulosModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ResumenSaldoArticulosModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("articulos"), null);
			addTableAlias(computeTableName("almacenes"), null);
			addTableAlias(computeTableName("resumen_saldo_articulos"), null);

			// add columns
			addColumn(computeTableName("resumen_saldo_articulos"),
					"resumen_saldo_articulo_id", DataStore.DATATYPE_INT, true,
					true, RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID);
			addColumn(computeTableName("resumen_saldo_articulos"),
					"almacen_id", DataStore.DATATYPE_INT, false, true,
					RESUMEN_SALDO_ARTICULOS_ALMACEN_ID);
			addColumn(computeTableName("resumen_saldo_articulos"),
					"articulo_id", DataStore.DATATYPE_INT, false, true,
					RESUMEN_SALDO_ARTICULOS_ARTICULO_ID);
			addColumn(computeTableName("resumen_saldo_articulos"), "periodo",
					DataStore.DATATYPE_DATE, false, true,
					RESUMEN_SALDO_ARTICULOS_PERIODO);
			addColumn(computeTableName("resumen_saldo_articulos"),
					"stock_en_mano", DataStore.DATATYPE_DOUBLE, false, true,
					RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO);
			addColumn(computeTableName("resumen_saldo_articulos"), "reservado",
					DataStore.DATATYPE_DOUBLE, false, true,
					RESUMEN_SALDO_ARTICULOS_RESERVADO);
			addColumn(computeTableName("resumen_saldo_articulos"), "en_proceso",
					DataStore.DATATYPE_DOUBLE, false, true,
					RESUMEN_SALDO_ARTICULOS_EN_PROCESO);
			addColumn(computeTableName("resumen_saldo_articulos"), "total_ingresos",
					DataStore.DATATYPE_DOUBLE, false, true,
					RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS);
			addColumn(computeTableName("resumen_saldo_articulos"), "total_egresos",
					DataStore.DATATYPE_DOUBLE, false, true,
					RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS);
			addColumn(computeTableName("resumen_saldo_articulos"), "cant_transacciones_ingresos",
					DataStore.DATATYPE_INT, false, true,
					RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS);
			addColumn(computeTableName("resumen_saldo_articulos"), "cant_transacciones_egresos",
					DataStore.DATATYPE_INT, false, true,
					RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS);
			addColumn(computeTableName("almacenes"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ALMACENES_NOMBRE);
			addColumn(computeTableName("articulos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"), "descripcion",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("articulos"), "descripcion_completa",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_DESCRIPCION_COMPLETA);

			// add joins
			addJoin(
					computeTableAndFieldName("resumen_saldo_articulos.almacen_id"),
					computeTableAndFieldName("almacenes.almacen_id"), false);
			addJoin(
					computeTableAndFieldName("resumen_saldo_articulos.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);

			// set order by
			setOrderBy(computeTableAndFieldName("resumen_saldo_articulos.resumen_saldo_articulo_id")
					+ " DESC");

			// add validations
			addRequiredRule(RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO,
					"El stock en mano es obligatorio");
			addRequiredRule(RESUMEN_SALDO_ARTICULOS_RESERVADO,					
					"La cantidad de reservas es obligatoria");
			
			setAutoIncrement(RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID, true);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.resumen_saldo_articulo_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosResumenSaldoArticuloId()
			throws DataStoreException {
		return getInt(RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.resumen_saldo_articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosResumenSaldoArticuloId(int row)
			throws DataStoreException {
		return getInt(row, RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.resumen_saldo_articulo_id
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosResumenSaldoArticuloId(int newValue)
			throws DataStoreException {
		setInt(RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.resumen_saldo_articulo_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosResumenSaldoArticuloId(int row,
			int newValue) throws DataStoreException {
		setInt(row, RESUMEN_SALDO_ARTICULOS_RESUMEN_SALDO_ARTICULO_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the resumen_saldo_articulos.almacen_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosAlmacenId() throws DataStoreException {
		return getInt(RESUMEN_SALDO_ARTICULOS_ALMACEN_ID);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.almacen_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosAlmacenId(int row)
			throws DataStoreException {
		return getInt(row, RESUMEN_SALDO_ARTICULOS_ALMACEN_ID);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.almacen_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosAlmacenId(int newValue)
			throws DataStoreException {
		setInt(RESUMEN_SALDO_ARTICULOS_ALMACEN_ID, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.almacen_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosAlmacenId(int row, int newValue)
			throws DataStoreException {
		setInt(row, RESUMEN_SALDO_ARTICULOS_ALMACEN_ID, newValue);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.articulo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosArticuloId() throws DataStoreException {
		return getInt(RESUMEN_SALDO_ARTICULOS_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.articulo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosArticuloId(int row)
			throws DataStoreException {
		return getInt(row, RESUMEN_SALDO_ARTICULOS_ARTICULO_ID);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.articulo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosArticuloId(int newValue)
			throws DataStoreException {
		setInt(RESUMEN_SALDO_ARTICULOS_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, RESUMEN_SALDO_ARTICULOS_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.periodo column for the
	 * current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getResumenSaldoArticulosPeriodo()
			throws DataStoreException {
		return getDate(RESUMEN_SALDO_ARTICULOS_PERIODO);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.periodo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getResumenSaldoArticulosPeriodo(int row)
			throws DataStoreException {
		return getDate(row, RESUMEN_SALDO_ARTICULOS_PERIODO);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.periodo column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosPeriodo(java.sql.Date newValue)
			throws DataStoreException {
		setDate(RESUMEN_SALDO_ARTICULOS_PERIODO, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.periodo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosPeriodo(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, RESUMEN_SALDO_ARTICULOS_PERIODO, newValue);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.stock_en_mano column
	 * for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosStockEnMano()
			throws DataStoreException {
		return getDouble(RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.stock_en_mano column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosStockEnMano(int row)
			throws DataStoreException {
		return getDouble(row, RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.stock_en_mano column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosStockEnMano(double newValue)
			throws DataStoreException {
		setDouble(RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.stock_en_mano column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosStockEnMano(int row, double newValue)
			throws DataStoreException {
		setDouble(row, RESUMEN_SALDO_ARTICULOS_STOCK_EN_MANO, newValue);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.reservado column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosReservado() throws DataStoreException {
		return getDouble(RESUMEN_SALDO_ARTICULOS_RESERVADO);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.reservado column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosReservado(int row)
			throws DataStoreException {
		return getDouble(row, RESUMEN_SALDO_ARTICULOS_RESERVADO);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.reservado column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosReservado(double newValue)
			throws DataStoreException {
		setDouble(RESUMEN_SALDO_ARTICULOS_RESERVADO, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.reservado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosReservado(int row, double newValue)
			throws DataStoreException {
		setDouble(row, RESUMEN_SALDO_ARTICULOS_RESERVADO, newValue);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.en_proceso column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosEnProceso() throws DataStoreException {
		return getDouble(RESUMEN_SALDO_ARTICULOS_EN_PROCESO);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.en_proceso column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosEnProceso(int row)
			throws DataStoreException {
		return getDouble(row, RESUMEN_SALDO_ARTICULOS_EN_PROCESO);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.en_proceso column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosEnProceso(double newValue)
			throws DataStoreException {
		setDouble(RESUMEN_SALDO_ARTICULOS_EN_PROCESO, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.en_proceso column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosEnProceso(int row, double newValue)
			throws DataStoreException {
		setDouble(row, RESUMEN_SALDO_ARTICULOS_EN_PROCESO, newValue);
	}
	
	/**
	 * Retrieve the value of the resumen_saldo_articulos.total_egresos column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosTotalEgresos() throws DataStoreException {
		return getDouble(RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.total_egresos column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosTotalEgresos(int row)
			throws DataStoreException {
		return getDouble(row, RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.total_egresos column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosTotalEgresos(double newValue)
			throws DataStoreException {
		setDouble(RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.total_egresos column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosTotalEgresos(int row, double newValue)
			throws DataStoreException {
		setDouble(row, RESUMEN_SALDO_ARTICULOS_TOTAL_EGRESOS, newValue);
	}
	
	/**
	 * Retrieve the value of the resumen_saldo_articulos.total_ingresos column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosTotalIngresos() throws DataStoreException {
		return getDouble(RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS);
	}

	/**
	 * Retrieve the value of the resumen_saldo_articulos.total_ingresos column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getResumenSaldoArticulosTotalIngresos(int row)
			throws DataStoreException {
		return getDouble(row, RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.total_ingresos column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosTotalIngresos(double newValue)
			throws DataStoreException {
		setDouble(RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.total_ingresos column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosTotalIngresos(int row, double newValue)
			throws DataStoreException {
		setDouble(row, RESUMEN_SALDO_ARTICULOS_TOTAL_INGRESOS, newValue);
	}
	

	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.cant_transacciones_ingresos column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosCantTransaccionesIngresos()
			throws DataStoreException {
		return getInt(RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS);
	}

	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.cant_transacciones_ingresos column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosCantTransaccionesIngresos(int row)
			throws DataStoreException {
		return getInt(row, RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.cant_transacciones_ingresos
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosCantTransaccionesIngresos(int newValue)
			throws DataStoreException {
		setInt(RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.cant_transacciones_ingresos
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosCantTransaccionesIngresos(int row,
			int newValue) throws DataStoreException {
		setInt(row, RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_INGRESOS, newValue);
	}
	
	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.cant_transacciones_egresos column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosCantTransaccionesEgresos()
			throws DataStoreException {
		return getInt(RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS);
	}

	/**
	 * Retrieve the value of the
	 * resumen_saldo_articulos.cant_transacciones_egresos column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getResumenSaldoArticulosCantTransaccionesEgresos(int row)
			throws DataStoreException {
		return getInt(row, RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.cant_transacciones_egresos
	 * column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosCantTransaccionesEgresos(int newValue)
			throws DataStoreException {
		setInt(RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS, newValue);
	}

	/**
	 * Set the value of the resumen_saldo_articulos.cant_transacciones_egresos
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setResumenSaldoArticulosCantTransaccionesEgresos(int row,
			int newValue) throws DataStoreException {
		setInt(row, RESUMEN_SALDO_ARTICULOS_CANT_TRANSACCIONES_EGRESOS, newValue);
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
	 * Retrieve the value of the articulos.descripcion column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcion() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the specified row.
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
	public void setArticulosDescripcion(String newValue) throws DataStoreException {
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
	 * Retrieve the value of the articulos.descripcion_completa column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta(int row) throws DataStoreException {
		return getString(row, ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(String newValue) throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the specified row.
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

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	@Override
	public String getEstadoActual() throws DataStoreException {
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return this.getResumenSaldoArticulosResumenSaldoArticuloId();
	}

	public static double getStockEnMano(int articulo_id, int almacen_id,
			DBConnection conn) throws DataStoreException, SQLException {
		Statement st = null;
		try {
			st = conn.createStatement();

			ResultSet rs = st
					.executeQuery("SELECT stock_en_mano, periodo FROM resumen_saldo_articulos WHERE articulo_id = "
							+ articulo_id
							+ " AND almacen_id = "
							+ almacen_id
							+ " HAVING MAX(periodo) = periodo");
			if (rs.first())
				return rs.getDouble("stock_en_mano");
			else
				return 0;
		} finally {
			if (st != null)
				st.close();
		}
	}
	
	public static double getReservado(int articulo_id, int almacen_id,
			DBConnection conn) throws DataStoreException, SQLException {
		Statement st = null;
		try {
			st = conn.createStatement();

			ResultSet rs = st
					.executeQuery("SELECT reservado, periodo FROM resumen_saldo_articulos WHERE articulo_id = "
							+ articulo_id
							+ " AND almacen_id = "
							+ almacen_id
							+ " HAVING MAX(periodo) = periodo");
			if (rs.first())
				return rs.getDouble("reservado");
			else
				return 0;
		} finally {
			if (st != null)
				st.close();
		}
	}
	
	public void incrementarTotalIngresos(double cantidad) throws DataStoreException {
		System.out.println("cantidad "+(getResumenSaldoArticulosTotalIngresos()-cantidad));
		setResumenSaldoArticulosTotalIngresos(getResumenSaldoArticulosTotalIngresos()+cantidad);		
	}
	
	public void incrementarTotalEgresos(double cantidad) throws DataStoreException {
		setResumenSaldoArticulosTotalEgresos(getResumenSaldoArticulosTotalEgresos()+cantidad);
	}
	
	public void decrementarTotalIngresos(double cantidad) throws DataStoreException {
		setResumenSaldoArticulosTotalIngresos(getResumenSaldoArticulosTotalIngresos()-cantidad);		
	}
	
	public void decrementarTotalEgresos(double cantidad) throws DataStoreException {
		setResumenSaldoArticulosTotalEgresos(getResumenSaldoArticulosTotalEgresos()-cantidad);		
	}
	
	public void incrementarCantTransaccionesIngresos() throws DataStoreException {
		System.out.println("cant trans "+(getResumenSaldoArticulosCantTransaccionesIngresos()+1));
		setResumenSaldoArticulosCantTransaccionesIngresos(getResumenSaldoArticulosCantTransaccionesIngresos()+1);		
	}
	
	public void incrementarCantTransaccionesEgresos() throws DataStoreException {
		setResumenSaldoArticulosCantTransaccionesEgresos(getResumenSaldoArticulosCantTransaccionesEgresos()+1);
	}
	
	public void decrementarCantTransaccionesIngresos() throws DataStoreException {
		setResumenSaldoArticulosCantTransaccionesIngresos(getResumenSaldoArticulosCantTransaccionesIngresos()-1);
	}
	
	public void decrementarCantTransaccionesEgresos() throws DataStoreException {
		setResumenSaldoArticulosCantTransaccionesEgresos(getResumenSaldoArticulosCantTransaccionesEgresos()-1);
	}
	
	// $ENDCUSTOMMETHODS$

}
