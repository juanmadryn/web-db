package inventario.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CondicionesCompraModel: A SOFIA generated model
 */
public class CondicionesCompraModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1376545761234287629L;
	//constants for columns
	public static final String CONDICIONES_COMPRA_CONDICION_COMPRA_ID = "condiciones_compra.condicion_compra_id";
	public static final String CONDICIONES_COMPRA_NOMBRE = "condiciones_compra.nombre";
	public static final String CONDICIONES_COMPRA_DESCRIPCION = "condiciones_compra.descripcion";
	public static final String CONDICIONES_COMPRA_OBSERVACIONES = "condiciones_compra.observaciones";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMVARS$

	/**
	 * Create a new CondicionesCompraModel object.
	 * @param appName The SOFIA application name
	 */
	public CondicionesCompraModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new CondicionesCompraModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public CondicionesCompraModel(String appName, String profile) {
		super(appName, profile);

		try {

			//add columns
			addColumn(computeTableName("condiciones_compra"),
					"condicion_compra_id", DataStore.DATATYPE_INT, true, true,
					CONDICIONES_COMPRA_CONDICION_COMPRA_ID);
			addColumn(computeTableName("condiciones_compra"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					CONDICIONES_COMPRA_NOMBRE);
			addColumn(computeTableName("condiciones_compra"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					CONDICIONES_COMPRA_DESCRIPCION);
			addColumn(computeTableName("condiciones_compra"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					CONDICIONES_COMPRA_OBSERVACIONES);

			//set order by
			setOrderBy(computeTableAndFieldName("condiciones_compra.condicion_compra_id")
					+ " ASC");

			//add validations
			addRequiredRule(CONDICIONES_COMPRA_NOMBRE,
					"El campo nombre es obligatorio");
			addRequiredRule(CONDICIONES_COMPRA_DESCRIPCION,
					"El campo descripcion es obligatorio");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the condiciones_compra.condicion_compra_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCondicionesCompraCondicionCompraId()
			throws DataStoreException {
		return getInt(CONDICIONES_COMPRA_CONDICION_COMPRA_ID);
	}

	/**
	 * Retrieve the value of the condiciones_compra.condicion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCondicionesCompraCondicionCompraId(int row)
			throws DataStoreException {
		return getInt(row, CONDICIONES_COMPRA_CONDICION_COMPRA_ID);
	}

	/**
	 * Set the value of the condiciones_compra.condicion_compra_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraCondicionCompraId(int newValue)
			throws DataStoreException {
		setInt(CONDICIONES_COMPRA_CONDICION_COMPRA_ID, newValue);
	}

	/**
	 * Set the value of the condiciones_compra.condicion_compra_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraCondicionCompraId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CONDICIONES_COMPRA_CONDICION_COMPRA_ID, newValue);
	}

	/**
	 * Retrieve the value of the condiciones_compra.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraNombre() throws DataStoreException {
		return getString(CONDICIONES_COMPRA_NOMBRE);
	}

	/**
	 * Retrieve the value of the condiciones_compra.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraNombre(int row) throws DataStoreException {
		return getString(row, CONDICIONES_COMPRA_NOMBRE);
	}

	/**
	 * Set the value of the condiciones_compra.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraNombre(String newValue)
			throws DataStoreException {
		setString(CONDICIONES_COMPRA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the condiciones_compra.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CONDICIONES_COMPRA_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the condiciones_compra.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraDescripcion() throws DataStoreException {
		return getString(CONDICIONES_COMPRA_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the condiciones_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraDescripcion(int row)
			throws DataStoreException {
		return getString(row, CONDICIONES_COMPRA_DESCRIPCION);
	}

	/**
	 * Set the value of the condiciones_compra.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraDescripcion(String newValue)
			throws DataStoreException {
		setString(CONDICIONES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the condiciones_compra.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, CONDICIONES_COMPRA_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the condiciones_compra.observaciones column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraObservaciones() throws DataStoreException {
		return getString(CONDICIONES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the condiciones_compra.observaciones column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCondicionesCompraObservaciones(int row)
			throws DataStoreException {
		return getString(row, CONDICIONES_COMPRA_OBSERVACIONES);
	}

	/**
	 * Set the value of the condiciones_compra.observaciones column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraObservaciones(String newValue)
			throws DataStoreException {
		setString(CONDICIONES_COMPRA_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the condiciones_compra.observaciones column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setCondicionesCompraObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, CONDICIONES_COMPRA_OBSERVACIONES, newValue);
	}

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return 0;
	}
	//$ENDCUSTOMMETHODS$

}
