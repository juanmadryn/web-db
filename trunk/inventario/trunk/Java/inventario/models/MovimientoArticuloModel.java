package inventario.models;

import infraestructura.models.AtributosEntidadModel;

import java.sql.SQLException;

import proyectos.models.ProyectoModel;
import proyectos.models.TareasProyectoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * MovimientoArticuloModel: A SOFIA generated model
 */
public class MovimientoArticuloModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9185545827130310022L;
	// constants for columns
	public static final String MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID = "movimiento_articulo.movimiento_articulo_id";
	public static final String MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID = "movimiento_articulo.centro_costo_id";
	public static final String MOVIMIENTO_ARTICULO_PROYECTO_ID = "movimiento_articulo.proyecto_id";
	public static final String MOVIMIENTO_ARTICULO_TAREA_ID = "movimiento_articulo.tarea_id";
	public static final String MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID = "movimiento_articulo.resumen_saldo_articulo_id";
	public static final String MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID = "movimiento_articulo.comprobante_movimiento_id";
	public static final String MOVIMIENTO_ARTICULO_ARTICULO_ID = "movimiento_articulo.articulo_id";
	public static final String MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA = "movimiento_articulo.cantidad_solicitada";
	public static final String MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA = "movimiento_articulo.cantidad_entregada";
	public static final String MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA = "movimiento_articulo.cantidad_anulada";
	public static final String MOVIMIENTO_ARTICULO_DESCRIPCION = "movimiento_articulo.descripcion";
	public static final String MOVIMIENTO_ARTICULO_OBSERVACIONES = "movimiento_articulo.observaciones";
	public static final String MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID = "movimiento_articulo.unidad_medida_id";
	public static final String MOVIMIENTO_ARTICULO_LEGAJO_CARGO = "movimiento_articulo.legajo_cargo";
	public static final String PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String LEGAJOS_APEYNOM = "legajos.APEYNOM";
	// $ENDCUSTOMVARS$

	/**
	 * Create a new MovimientoArticuloModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public MovimientoArticuloModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new MovimientoArticuloModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public MovimientoArticuloModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("movimiento_articulo"),
					"movimiento_articulo");
			addTableAlias(computeTableName("articulos"), "articulos");
			addTableAlias(computeTableName("resumen_saldo_articulos"),
					"resumen_saldo_articulos");
			addTableAlias(computeTableName("comprobante_movimiento_articulo"),
					"comprobante_movimiento_articulo");
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida");
			addTableAlias("proyectos.proyectos", "proyectos");
			addTableAlias("proyectos.tareas_proyecto",
					"tareas_proyecto");			

			// add columns
			addColumn(computeTableName("movimiento_articulo"),
					"movimiento_articulo_id", DataStore.DATATYPE_INT, true,
					true, MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
			addColumn(computeTableName("movimiento_articulo"),
					"centro_costo_id", DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
			addColumn(computeTableName("movimiento_articulo"), "proyecto_id",
					DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_PROYECTO_ID);
			addColumn(computeTableName("movimiento_articulo"), "tarea_id",
					DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_TAREA_ID);
			addColumn(computeTableName("movimiento_articulo"),
					"resumen_saldo_articulo_id", DataStore.DATATYPE_INT, false,
					true, MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
			addColumn(computeTableName("movimiento_articulo"),
					"comprobante_movimiento_id", DataStore.DATATYPE_INT, false,
					true, MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
			addColumn(computeTableName("movimiento_articulo"), "articulo_id",
					DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_ARTICULO_ID);
			addColumn(computeTableName("movimiento_articulo"),
					"cantidad_solicitada", DataStore.DATATYPE_DOUBLE, false,
					true, MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
			addColumn(computeTableName("movimiento_articulo"),
					"cantidad_entregada", DataStore.DATATYPE_DOUBLE, false,
					true, MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
			addColumn(computeTableName("movimiento_articulo"),
					"cantidad_anulada", DataStore.DATATYPE_DOUBLE, false, true,
					MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
			addColumn(computeTableName("movimiento_articulo"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					MOVIMIENTO_ARTICULO_DESCRIPCION);
			addColumn(computeTableName("movimiento_articulo"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					MOVIMIENTO_ARTICULO_OBSERVACIONES);
			addColumn(computeTableName("movimiento_articulo"),
					"unidad_medida_id", DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID);
			addColumn(computeTableName("proyectos"), "proyecto",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_PROYECTO);
			addColumn(computeTableName("proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
			addColumn(computeTableName("tareas_proyecto"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					TAREAS_PROYECTO_NOMBRE);
			addColumn(computeTableName("unidades_medida"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					UNIDADES_MEDIDA_NOMBRE);
			addColumn(computeTableName("articulos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("articulos"), "descripcion_completa",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION_COMPLETA);
			addColumn(computeTableName("movimiento_articulo"),
					"legajo_cargo", DataStore.DATATYPE_INT, false, true,
					MOVIMIENTO_ARTICULO_LEGAJO_CARGO);
			addColumn(computeTableName("legajos"), "APEYNOM",
					DataStore.DATATYPE_STRING, false, false, LEGAJOS_APEYNOM);
			
			
			// add joins
			addJoin(
					computeTableAndFieldName("movimiento_articulo.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);
			addJoin(
					computeTableAndFieldName("movimiento_articulo.resumen_saldo_articulo_id"),
					computeTableAndFieldName("resumen_saldo_articulos.resumen_saldo_articulo_id"),
					true);
			addJoin(
					computeTableAndFieldName("movimiento_articulo.comprobante_movimiento_id"),
					computeTableAndFieldName("comprobante_movimiento_articulo.comprobante_movimiento_id"),
					false);
			addJoin(
					computeTableAndFieldName("movimiento_articulo.unidad_medida_id"),
					computeTableAndFieldName("unidades_medida.unidad_medida_id"),
					false);			
			addJoin(
					computeTableAndFieldName("movimiento_articulo.proyecto_id"),
					"proyectos.proyecto_id", false);
			
			addJoin(computeTableAndFieldName("movimiento_articulo.tarea_id"),
					"tareas_proyecto.tarea_id", false);
			
			addJoin(
					computeTableAndFieldName("movimiento_articulo.legajo_cargo"),
					computeTableAndFieldName("legajos.nro_legajo"), true);

			// set order by
			setOrderBy(computeTableAndFieldName("movimiento_articulo.movimiento_articulo_id")
					+ " ASC");

			// add validations
			addRequiredRule(MOVIMIENTO_ARTICULO_ARTICULO_ID,
					"El id del artículo es obligatorio");
			addRequiredRule(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA,
					"La cantidad solicitada es obligatoria");			
			addRequiredRule(MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID,
					"La unidad de medida es obligatoria");

			addLookupRule(
					MOVIMIENTO_ARTICULO_ARTICULO_ID,
					"articulos",
					"'articulos.articulo_id = ' + movimiento_articulo.articulo_id",
					"descripcion", ARTICULOS_DESCRIPCION, "Artículo inexistente");
			addLookupRule(
					MOVIMIENTO_ARTICULO_PROYECTO_ID,
					computeTableName("proyectos.proyectos"),
					"'proyectos.proyectos.proyecto_id = ' + movimiento_articulo.proyecto_id",
					"nombre", PROYECTOS_NOMBRE, "Proyecto inexistente");
			addLookupRule(
					MOVIMIENTO_ARTICULO_TAREA_ID,
					"proyectos.tareas_proyecto",
					"'proyectos.tareas_proyecto.tarea_id = ' + movimiento_articulo.tarea_id",
					"nombre", TAREAS_PROYECTO_NOMBRE, "Tarea inexistente");

			addLookupRule(
					MOVIMIENTO_ARTICULO_LEGAJO_CARGO,
					"inventario.legajos",
					"'inventario.legajos.nro_legajo = ' + movimiento_articulo.legajo_cargo",
					"APEYNOM", LEGAJOS_APEYNOM,
					"Legajo inexistente");
			
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the movimiento_articulo.movimiento_articulo_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloMovimientoArticuloId()
			throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.movimiento_articulo_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloMovimientoArticuloId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.movimiento_articulo_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloMovimientoArticuloId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.movimiento_articulo_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloMovimientoArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.centro_costo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloCentroCostoId() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.centro_costo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloCentroCostoId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.centro_costo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCentroCostoId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.centro_costo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCentroCostoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.proyecto_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloProyectoId() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloProyectoId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_PROYECTO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.proyecto_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloProyectoId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_PROYECTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.tarea_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloTareaId() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_TAREA_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloTareaId(int row) throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_TAREA_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.tarea_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloTareaId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_TAREA_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.tarea_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloTareaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_TAREA_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.resumen_saldo_articulo_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloResumenSaldoArticuloId()
			throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.resumen_saldo_articulo_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloResumenSaldoArticuloId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.resumen_saldo_articulo_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloResumenSaldoArticuloId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.resumen_saldo_articulo_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloResumenSaldoArticuloId(int row,
			int newValue) throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.comprobante_movimiento_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloComprobanteMovimientoId()
			throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.comprobante_movimiento_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloComprobanteMovimientoId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.comprobante_movimiento_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloComprobanteMovimientoId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.comprobante_movimiento_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloComprobanteMovimientoId(int row,
			int newValue) throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.articulo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloArticuloId() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloArticuloId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_ARTICULO_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.articulo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloArticuloId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_solicitada column
	 * for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadSolicitada()
			throws DataStoreException {
		return getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_solicitada column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadSolicitada(int row)
			throws DataStoreException {
		return getDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_solicitada column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadSolicitada(double newValue)
			throws DataStoreException {
		setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_solicitada column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadSolicitada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_entregada column
	 * for the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadEntregada()
			throws DataStoreException {
		return getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_entregada column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadEntregada(int row)
			throws DataStoreException {
		return getDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_entregada column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadEntregada(double newValue)
			throws DataStoreException {
		setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_entregada column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadEntregada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_anulada column for
	 * the current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadAnulada()
			throws DataStoreException {
		return getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.cantidad_anulada column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getMovimientoArticuloCantidadAnulada(int row)
			throws DataStoreException {
		return getDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_anulada column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadAnulada(double newValue)
			throws DataStoreException {
		setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.cantidad_anulada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloCantidadAnulada(int row, double newValue)
			throws DataStoreException {
		setDouble(row, MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMovimientoArticuloDescripcion() throws DataStoreException {
		return getString(MOVIMIENTO_ARTICULO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMovimientoArticuloDescripcion(int row)
			throws DataStoreException {
		return getString(row, MOVIMIENTO_ARTICULO_DESCRIPCION);
	}

	/**
	 * Set the value of the movimiento_articulo.descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloDescripcion(String newValue)
			throws DataStoreException {
		setString(MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.observaciones column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMovimientoArticuloObservaciones()
			throws DataStoreException {
		return getString(MOVIMIENTO_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.observaciones column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getMovimientoArticuloObservaciones(int row)
			throws DataStoreException {
		return getString(row, MOVIMIENTO_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Set the value of the movimiento_articulo.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloObservaciones(String newValue)
			throws DataStoreException {
		setString(MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.articulo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloUnidadMedidaId() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloUnidadMedidaId(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Set the value of the movimiento_articulo.articulo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloUnidadMedidaId(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloUnidadMedidaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_UNIDAD_MEDIDA_ID, newValue);
	}


	/**
	 * Retrieve the value of the movimiento_articulo.legajo_cargo column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloLegajoCargo() throws DataStoreException {
		return getInt(MOVIMIENTO_ARTICULO_LEGAJO_CARGO);
	}

	/**
	 * Retrieve the value of the movimiento_articulo.legajo_cargo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getMovimientoArticuloLegajoCargo(int row)
			throws DataStoreException {
		return getInt(row, MOVIMIENTO_ARTICULO_LEGAJO_CARGO);
	}

	/**
	 * Set the value of the movimiento_articulo.legajo_cargo column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloLegajoCargo(int newValue)
			throws DataStoreException {
		setInt(MOVIMIENTO_ARTICULO_LEGAJO_CARGO, newValue);
	}

	/**
	 * Set the value of the movimiento_articulo.legajo_cargo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setMovimientoArticuloLegajoCargo(int row, int newValue)
			throws DataStoreException {
		setInt(row, MOVIMIENTO_ARTICULO_LEGAJO_CARGO, newValue);
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
	 * Retrieve the value of the articulos.descripcion_completa column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_DESCRIPCION, newValue);
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
	 * Set the value of the proyectos.proyecto column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosProyecto(String newValue) throws DataStoreException {
		setString(PROYECTOS_PROYECTO, newValue);
	}

	/**
	 * Set the value of the proyectos.proyecto column for the specified row.
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
	 * Set the value of the proyectos.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the proyectos.nombre column for the specified row.
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
	 * Retrieve the value of the tareas_proyecto.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTareasProyectoNombre() throws DataStoreException {
		return getString(TAREAS_PROYECTO_NOMBRE);
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
	public String getTareasProyectoNombre(int row) throws DataStoreException {
		return getString(row, TAREAS_PROYECTO_NOMBRE);
	}

	/**
	 * Set the value of the tareas_proyecto.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTareasProyectoNombre(String newValue)
			throws DataStoreException {
		setString(TAREAS_PROYECTO_NOMBRE, newValue);
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
	public void setTareasProyectoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, TAREAS_PROYECTO_NOMBRE, newValue);
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

	
	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		ProyectoModel dsProyecto;
		for (int row = 0; row < getRowCount(); row++) {
			if (getProyectosProyecto(row) != null) {
				dsProyecto = new ProyectoModel("proyectos", "proyectos");
				dsProyecto.retrieve("proyecto = '" + getProyectosProyecto()
						+ "'");
				if (dsProyecto.gotoFirst())
					setMovimientoArticuloProyectoId(row, dsProyecto
							.getProyectosProyectoId());
				else
					throw new DataStoreException(
							"El proyecto indicado no existe");
			} else
				setMovimientoArticuloProyectoId(row, 0);

			// checks if tarea exist for specified project
			TareasProyectoModel dsTareas = new TareasProyectoModel("proyectos",
					"proyectos");
			int proyecto_id = getMovimientoArticuloProyectoId(row);
			if (proyecto_id != 0) {
				dsTareas.retrieve("tareas_proyecto.proyecto_id = "
						+ proyecto_id);
				dsTareas.gotoFirst();
				setMovimientoArticuloTareaId(row, dsTareas
						.getTareasProyectoTareaId());
			}
			if (dsTareas.estimateRowsRetrieved("tareas_proyecto.proyecto_id = "
					+ proyecto_id + " AND tareas_proyecto.tarea_id = "
					+ getMovimientoArticuloTareaId(row)) == 0)
				throw new DataStoreException(
						"La tarea especificada no pertenece al proyecto al cual está imputada la solicitud");

			ArticulosModel articulos;
			// fills detalle_sc.articulo_id field through ArticulosNombre
			if (getArticulosNombre(row) != null) {
				articulos = new ArticulosModel("inventario", "inventario");
				articulos.retrieve(conn, "articulos.nombre LIKE '"
						+ getArticulosNombre(row) + "'");
				if (!articulos.gotoFirst()) {
					DataStoreException ex = new DataStoreException(
							"El código de articulo ingresado no corresponde a ninguno registrado");
					ex.setRowNo(row);
					throw ex;
				}
				;
				setMovimientoArticuloArticuloId(row, articulos
						.getArticulosArticuloId());
			}
			if (getMovimientoArticuloDescripcion(row) == null)
				setMovimientoArticuloDescripcion(row,
						getArticulosDescripcionCompleta(row));
			if (getMovimientoArticuloUnidadMedidaId(row) == 0)
				setMovimientoArticuloUnidadMedidaId(row, Integer
						.parseInt(AtributosEntidadModel.getValorAtributoObjeto(
								"ARTICULO_UNIDAD_MEDIDA",
								getMovimientoArticuloArticuloId(row), "TABLA",
								"articulos")));
			if (getMovimientoArticuloCantidadAnulada(row) == 0)
				setMovimientoArticuloCantidadAnulada(row,
						getMovimientoArticuloCantidadSolicitada(row)
								- getMovimientoArticuloCantidadEntregada(row));
			super.update(conn, handleTrans);
		}
	}

	// $ENDCUSTOMMETHODS$

}
