package inventario.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ArticulosExtendidosModel: A SOFIA generated model
 */
public class ArticulosExtendidosModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5745575353118947999L;
	// constants for columns
	public static final String ARTICULOS_EXTENDIDOS_ARTICULO_ID = "articulos_extendidos.articulo_id";
	public static final String ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID = "articulos_extendidos.clase_articulo_id";
	public static final String ARTICULOS_EXTENDIDOS_NOMBRE = "articulos_extendidos.nombre";
	public static final String ARTICULOS_EXTENDIDOS_DESCRIPCION = "articulos_extendidos.descripcion";
	public static final String ARTICULOS_EXTENDIDOS_OBSERVACIONES = "articulos_extendidos.observaciones";
	public static final String ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA = "articulos_extendidos.descripcion_completa";
	public static final String ARTICULOS_EXTENDIDOS_ACTIVO = "articulos_extendidos.activo";
	public static final String ARTICULOS_EXTENDIDOS_ANULADO = "articulos_extendidos.anulado";
	public static final String ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD = "articulos_extendidos.nombre_unidad";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ArticulosExtendidosModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 */
	public ArticulosExtendidosModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ArticulosExtendidosModel object.
	 * 
	 * @param appName
	 *           The SOFIA application name
	 * @param profile
	 *           The database profile to use
	 */
	public ArticulosExtendidosModel(String appName, String profile) {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("articulos_extendidos"),
				"articulos_extendidos");

		// add columns
		addColumn(computeTableName("articulos_extendidos"), "articulo_id",
				DataStore.DATATYPE_INT, false, false,
				ARTICULOS_EXTENDIDOS_ARTICULO_ID);
		addColumn(computeTableName("articulos_extendidos"), "clase_articulo_id",
				DataStore.DATATYPE_INT, false, false,
				ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID);
		addColumn(computeTableName("articulos_extendidos"), "nombre",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_NOMBRE);
		addColumn(computeTableName("articulos_extendidos"), "descripcion",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_DESCRIPCION);
		addColumn(computeTableName("articulos_extendidos"), "observaciones",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_OBSERVACIONES);
		addColumn(computeTableName("articulos_extendidos"),
				"descripcion_completa", DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA);
		addColumn(computeTableName("articulos_extendidos"), "activo",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_ACTIVO);
		addColumn(computeTableName("articulos_extendidos"), "anulado",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_ANULADO);
		addColumn(computeTableName("articulos_extendidos"), "nombre_unidad",
				DataStore.DATATYPE_STRING, false, false,
				ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD);

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the articulos_extendidos.articulo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosExtendidosArticuloId() throws DataStoreException {
		return getInt(ARTICULOS_EXTENDIDOS_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosExtendidosArticuloId(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_EXTENDIDOS_ARTICULO_ID);
	}

	/**
	 * Set the value of the articulos_extendidos.articulo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosArticuloId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_EXTENDIDOS_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_EXTENDIDOS_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.clase_articulo_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosExtendidosClaseArticuloId() throws DataStoreException {
		return getInt(ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.clase_articulo_id column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getArticulosExtendidosClaseArticuloId(int row)
			throws DataStoreException {
		return getInt(row, ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Set the value of the articulos_extendidos.clase_articulo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosClaseArticuloId(int newValue)
			throws DataStoreException {
		setInt(ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.clase_articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosClaseArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ARTICULOS_EXTENDIDOS_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosNombre() throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosNombre(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_NOMBRE);
	}

	/**
	 * Set the value of the articulos_extendidos.nombre column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosNombre(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosDescripcion() throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosDescripcion(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos_extendidos.descripcion column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosDescripcion(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.observaciones column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosObservaciones()
			throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.observaciones column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosObservaciones(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the articulos_extendidos.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosObservaciones(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.descripcion_completa column
	 * for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosDescripcionCompleta()
			throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.descripcion_completa column
	 * for the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosDescripcionCompleta(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Set the value of the articulos_extendidos.descripcion_completa column for
	 * the current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosDescripcionCompleta(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.descripcion_completa column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosDescripcionCompleta(int row,
			String newValue) throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.activo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosActivo() throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_ACTIVO);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.activo column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosActivo(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_ACTIVO);
	}

	/**
	 * Set the value of the articulos_extendidos.activo column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosActivo(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_ACTIVO, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.activo column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosActivo(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_ACTIVO, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.anulado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosAnulado() throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_ANULADO);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.anulado column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosAnulado(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_ANULADO);
	}

	/**
	 * Set the value of the articulos_extendidos.anulado column for the current
	 * row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosAnulado(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_ANULADO, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.anulado column for the specified
	 * row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosAnulado(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_ANULADO, newValue);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.nombre_unidad column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosNombreUnidad() throws DataStoreException {
		return getString(ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD);
	}

	/**
	 * Retrieve the value of the articulos_extendidos.nombre_unidad column for
	 * the specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosExtendidosNombreUnidad(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD);
	}

	/**
	 * Set the value of the articulos_extendidos.nombre_unidad column for the
	 * current row.
	 * 
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosNombreUnidad(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD, newValue);
	}

	/**
	 * Set the value of the articulos_extendidos.nombre_unidad column for the
	 * specified row.
	 * 
	 * @param row
	 *           which row in the table
	 * @param newValue
	 *           the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosExtendidosNombreUnidad(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_EXTENDIDOS_NOMBRE_UNIDAD, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
