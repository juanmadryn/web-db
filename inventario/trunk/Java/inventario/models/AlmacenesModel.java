package inventario.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AlmacenesModel: A SOFIA generated model
 */
public class AlmacenesModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7160376754443057611L;
	//constants for columns
	public static final String ALMACENES_ALMACEN_ID="almacenes.almacen_id";
	public static final String ALMACENES_NOMBRE="almacenes.nombre";
	public static final String ALMACENES_DESCRIPCION="almacenes.descripcion";
	public static final String ALMACENES_OBSERVACIONES="almacenes.observaciones";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMVARS$

	/**
	 * Create a new AlmacenesModel object.
	 * @param appName The SOFIA application name
	 */
	public AlmacenesModel (String appName) { 
		this(appName, null);
	}

	/**
	 * Create a new AlmacenesModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public AlmacenesModel (String appName, String profile) { 
		super(appName, profile);

		try {

			//add columns
			addColumn(computeTableName("almacenes"),"almacen_id",DataStore.DATATYPE_INT,true,true,ALMACENES_ALMACEN_ID);
			addColumn(computeTableName("almacenes"),"nombre",DataStore.DATATYPE_STRING,false,true,ALMACENES_NOMBRE);
			addColumn(computeTableName("almacenes"),"descripcion",DataStore.DATATYPE_STRING,false,true,ALMACENES_DESCRIPCION);
			addColumn(computeTableName("almacenes"),"observaciones",DataStore.DATATYPE_STRING,false,true,ALMACENES_OBSERVACIONES);

			//set order by
			setOrderBy(computeTableAndFieldName("almacenes.nombre") + " ASC");

			//add validations
			addRequiredRule(ALMACENES_ALMACEN_ID,"El Id del almacen es obligatorio");
			addRequiredRule(ALMACENES_NOMBRE,"El nombre del almacen es obligatorio");
		}
		catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the almacenes.almacen_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getAlmacenesAlmacenId() throws DataStoreException {
		return  getInt(ALMACENES_ALMACEN_ID);
	}

	/**
	 * Retrieve the value of the almacenes.almacen_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getAlmacenesAlmacenId(int row) throws DataStoreException {
		return  getInt(row,ALMACENES_ALMACEN_ID);
	}

	/**
	 * Set the value of the almacenes.almacen_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesAlmacenId(int newValue) throws DataStoreException {
		setInt(ALMACENES_ALMACEN_ID, newValue);
	}

	/**
	 * Set the value of the almacenes.almacen_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesAlmacenId(int row,int newValue) throws DataStoreException {
		setInt(row,ALMACENES_ALMACEN_ID, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesNombre() throws DataStoreException {
		return  getString(ALMACENES_NOMBRE);
	}

	/**
	 * Retrieve the value of the almacenes.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesNombre(int row) throws DataStoreException {
		return  getString(row,ALMACENES_NOMBRE);
	}

	/**
	 * Set the value of the almacenes.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesNombre(String newValue) throws DataStoreException {
		setString(ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Set the value of the almacenes.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesNombre(int row,String newValue) throws DataStoreException {
		setString(row,ALMACENES_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesDescripcion() throws DataStoreException {
		return  getString(ALMACENES_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the almacenes.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesDescripcion(int row) throws DataStoreException {
		return  getString(row,ALMACENES_DESCRIPCION);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesDescripcion(String newValue) throws DataStoreException {
		setString(ALMACENES_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the almacenes.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesDescripcion(int row,String newValue) throws DataStoreException {
		setString(row,ALMACENES_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the almacenes.observaciones column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesObservaciones() throws DataStoreException {
		return  getString(ALMACENES_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the almacenes.observaciones column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getAlmacenesObservaciones(int row) throws DataStoreException {
		return  getString(row,ALMACENES_OBSERVACIONES);
	}

	/**
	 * Set the value of the almacenes.observaciones column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesObservaciones(String newValue) throws DataStoreException {
		setString(ALMACENES_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the almacenes.observaciones column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setAlmacenesObservaciones(int row,String newValue) throws DataStoreException {
		setString(row,ALMACENES_OBSERVACIONES, newValue);
	}

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
	@Override
	public String getEstadoActual() throws DataStoreException {
		return null; 
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getAlmacenesAlmacenId();
	}     
	//$ENDCUSTOMMETHODS$

}
