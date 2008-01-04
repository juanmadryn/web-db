package inventario.models;

import infraestructura.models.BaseModel;
import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ClaseArticuloModel: A SOFIA generated model
 */
public class ClaseArticuloModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2961446008197962548L;
	//constants for columns
	public static final String CLASE_ARTICULO_CLASE_ARTICULO_ID="clase_articulo.clase_articulo_id";
	public static final String CLASE_ARTICULO_NOMBRE="clase_articulo.nombre";
	public static final String CLASE_ARTICULO_DESCRIPCION="clase_articulo.descripcion";
	public static final String CLASE_ARTICULO_OBSERVACIONES="clase_articulo.observaciones";
	public static final String CLASE_ARTICULO_GENERICO="clase_articulo.generico";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMVARS$

	/**
	 * Create a new ClaseArticuloModel object.
	 * @param appName The SOFIA application name
	 */
	public ClaseArticuloModel (String appName) { 
		this(appName, null);
	}

	/**
	 * Create a new ClaseArticuloModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	/**
	 * @param appName
	 * @param profile
	 */
	public ClaseArticuloModel (String appName, String profile) { 
		super(appName, profile);

		try {
            //add aliases
			addTableAlias(computeTableName("clase_articulo"),"clase_articulo");

			//add columns
			addColumn(computeTableName("clase_articulo"),"clase_articulo_id",DataStore.DATATYPE_INT,true,true,CLASE_ARTICULO_CLASE_ARTICULO_ID);
			addColumn(computeTableName("clase_articulo"),"nombre",DataStore.DATATYPE_STRING,false,true,CLASE_ARTICULO_NOMBRE);
			addColumn(computeTableName("clase_articulo"),"descripcion",DataStore.DATATYPE_STRING,false,true,CLASE_ARTICULO_DESCRIPCION);
			addColumn(computeTableName("clase_articulo"),"observaciones",DataStore.DATATYPE_STRING,false,true,CLASE_ARTICULO_OBSERVACIONES);
			addColumn(computeTableName("clase_articulo"),"generico",DataStore.DATATYPE_STRING,false,true,CLASE_ARTICULO_GENERICO);

			//set order by
			setOrderBy(computeTableAndFieldName("clase_articulo.nombre") + " ASC");

			//add validations
			addRequiredRule(CLASE_ARTICULO_CLASE_ARTICULO_ID,"El id del articulo es obligatorio");
			addRequiredRule(CLASE_ARTICULO_NOMBRE,"El nombre del artí­culo es obligatorio");
			addRequiredRule(CLASE_ARTICULO_GENERICO,"Debe definir si esta clase de artículo es genérico o no");			
		}
		catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
		try {
			addExpressionRule(CLASE_ARTICULO_NOMBRE,new ConvierteMayusculasValidation(CLASE_ARTICULO_NOMBRE),"",false);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}
		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the clase_articulo.clase_articulo_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getClaseArticuloClaseArticuloId() throws DataStoreException {
		return  getInt(CLASE_ARTICULO_CLASE_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the clase_articulo.clase_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getClaseArticuloClaseArticuloId(int row) throws DataStoreException {
		return  getInt(row,CLASE_ARTICULO_CLASE_ARTICULO_ID);
	}

	/**
	 * Set the value of the clase_articulo.clase_articulo_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloClaseArticuloId(int newValue) throws DataStoreException {
		setInt(CLASE_ARTICULO_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the clase_articulo.clase_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloClaseArticuloId(int row,int newValue) throws DataStoreException {
		setInt(row,CLASE_ARTICULO_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the clase_articulo.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloNombre() throws DataStoreException {
		return  getString(CLASE_ARTICULO_NOMBRE);
	}

	/**
	 * Retrieve the value of the clase_articulo.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloNombre(int row) throws DataStoreException {
		return  getString(row,CLASE_ARTICULO_NOMBRE);
	}

	/**
	 * Set the value of the clase_articulo.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloNombre(String newValue) throws DataStoreException {
		setString(CLASE_ARTICULO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the clase_articulo.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloNombre(int row,String newValue) throws DataStoreException {
		setString(row,CLASE_ARTICULO_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the clase_articulo.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloDescripcion() throws DataStoreException {
		return  getString(CLASE_ARTICULO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the clase_articulo.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloDescripcion(int row) throws DataStoreException {
		return  getString(row,CLASE_ARTICULO_DESCRIPCION);
	}

	/**
	 * Set the value of the clase_articulo.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloDescripcion(String newValue) throws DataStoreException {
		setString(CLASE_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the clase_articulo.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloDescripcion(int row,String newValue) throws DataStoreException {
		setString(row,CLASE_ARTICULO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the clase_articulo.observaciones column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloObservaciones() throws DataStoreException {
		return  getString(CLASE_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the clase_articulo.observaciones column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloObservaciones(int row) throws DataStoreException {
		return  getString(row,CLASE_ARTICULO_OBSERVACIONES);
	}

	/**
	 * Set the value of the clase_articulo.observaciones column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloObservaciones(String newValue) throws DataStoreException {
		setString(CLASE_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the clase_articulo.observaciones column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloObservaciones(int row,String newValue) throws DataStoreException {
		setString(row,CLASE_ARTICULO_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the clase_articulo.generico column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloGenerico() throws DataStoreException {
		return  getString(CLASE_ARTICULO_GENERICO);
	}

	/**
	 * Retrieve the value of the clase_articulo.generico column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getClaseArticuloGenerico(int row) throws DataStoreException {
		return  getString(row,CLASE_ARTICULO_GENERICO);
	}

	/**
	 * Set the value of the clase_articulo.generico column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloGenerico(String newValue) throws DataStoreException {
		setString(CLASE_ARTICULO_GENERICO, newValue);
	}

	/**
	 * Set the value of the clase_articulo.generico column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setClaseArticuloGenerico(int row,String newValue) throws DataStoreException {
		setString(row,CLASE_ARTICULO_GENERICO, newValue);
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
		return getClaseArticuloClaseArticuloId();
	}
	//$ENDCUSTOMMETHODS$

}
