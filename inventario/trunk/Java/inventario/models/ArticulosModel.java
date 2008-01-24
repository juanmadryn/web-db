package inventario.models;

import java.sql.SQLException;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated
import infraestructura.models.BaseModel;
import infraestructura.reglasNegocio.ConvierteMayusculasValidation;
//$ENDCUSTOMIMPORTS$

/**
 * ArticulosModel: A SOFIA generated model
 */
public class ArticulosModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5797401331477436362L;
	//constants for columns
	public static final String ARTICULOS_ARTICULO_ID="articulos.articulo_id";
	public static final String ARTICULOS_CLASE_ARTICULO_ID="articulos.clase_articulo_id";
	public static final String ARTICULOS_NOMBRE="articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION="articulos.descripcion";
	public static final String ARTICULOS_OBSERVACIONES="articulos.observaciones";
	public static final String ARTICULOS_DESCRIPCION_COMPLETA="articulos.descripcion_completa";
	public static final String ARTICULOS_CLAVE_EXTERNA1="articulos.clave_externa1";
	public static final String ARTICULOS_CLAVE_EXTERNA2="articulos.clave_externa2";
	public static final String ARTICULOS_CLAVE_EXTERNA3="articulos.clave_externa3";
	public static final String ARTICULOS_ACTIVO="articulos.activo";
	public static final String ARTICULOS_ANULADO="articulos.anulado";
	public static final String CLASE_ARTICULO_NOMBRE="clase_articulo.nombre";
	public static final String CLASE_ARTICULO_DESCRIPCION="clase_articulo.descripcion";

	//$CUSTOMVARS$
	//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

	//$ENDCUSTOMVARS$

	/**
	 * Create a new ArticulosModel object.
	 * @param appName The SOFIA application name
	 */
	public ArticulosModel (String appName) { 
		this(appName, null);
	}

	/**
	 * Create a new ArticulosModel object.
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public ArticulosModel (String appName, String profile) { 
		super(appName, profile);

		try {

			//add aliases
			addTableAlias(computeTableName("articulos"),"articulos");
			addTableAlias(computeTableName("clase_articulo"),"clase_articulo");

			//add columns
			addColumn(computeTableName("articulos"),"articulo_id",DataStore.DATATYPE_INT,true,true,ARTICULOS_ARTICULO_ID);
			addColumn(computeTableName("articulos"),"clase_articulo_id",DataStore.DATATYPE_INT,false,true,ARTICULOS_CLASE_ARTICULO_ID);
			addColumn(computeTableName("articulos"),"nombre",DataStore.DATATYPE_STRING,false,true,ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"),"descripcion",DataStore.DATATYPE_STRING,false,true,ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("articulos"),"observaciones",DataStore.DATATYPE_STRING,false,true,ARTICULOS_OBSERVACIONES);
			addColumn(computeTableName("articulos"),"descripcion_completa",DataStore.DATATYPE_STRING,false,true,ARTICULOS_DESCRIPCION_COMPLETA);
			addColumn(computeTableName("articulos"),"clave_externa1",DataStore.DATATYPE_STRING,false,true,ARTICULOS_CLAVE_EXTERNA1);
			addColumn(computeTableName("articulos"),"clave_externa2",DataStore.DATATYPE_STRING,false,true,ARTICULOS_CLAVE_EXTERNA2);
			addColumn(computeTableName("articulos"),"clave_externa3",DataStore.DATATYPE_STRING,false,true,ARTICULOS_CLAVE_EXTERNA3);
			addColumn(computeTableName("articulos"),"activo",DataStore.DATATYPE_STRING,false,true,ARTICULOS_ACTIVO);
			addColumn(computeTableName("articulos"),"anulado",DataStore.DATATYPE_STRING,false,true,ARTICULOS_ANULADO);
			addColumn(computeTableName("clase_articulo"),"nombre",DataStore.DATATYPE_STRING,false,false,CLASE_ARTICULO_NOMBRE);
			addColumn(computeTableName("clase_articulo"),"descripcion",DataStore.DATATYPE_STRING,false,false,CLASE_ARTICULO_DESCRIPCION);

			//add joins
			addJoin(computeTableAndFieldName("articulos.clase_articulo_id"),computeTableAndFieldName("clase_articulo.clase_articulo_id"),false);

			//set order by
			setOrderBy(computeTableAndFieldName("articulos.nombre") + " ASC");

			//add validations
			addRequiredRule(ARTICULOS_ARTICULO_ID,"El Id del artí­culo es obligatorio");
			addRequiredRule(ARTICULOS_CLASE_ARTICULO_ID,"La clase del artículo es obligatoria");
			addRequiredRule(ARTICULOS_NOMBRE,"El nombre del artículo es obligatorio");
			addRequiredRule(ARTICULOS_ACTIVO,"Debe especificar si el artí­culo esta activo o no");
			addRequiredRule(ARTICULOS_ANULADO,"Debe especificar si el artí­culo esta anulado o no");
			
            // establece la columna de autoincrement
            setAutoIncrement(ARTICULOS_ARTICULO_ID, true);
		}
		catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}

		//$CUSTOMCONSTRUCTOR$
		//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
		try {
			addExpressionRule(ARTICULOS_NOMBRE,new ConvierteMayusculasValidation(ARTICULOS_NOMBRE),"",false);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}
		//$ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the articulos.articulo_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getArticulosArticuloId() throws DataStoreException {
		return  getInt(ARTICULOS_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the articulos.articulo_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getArticulosArticuloId(int row) throws DataStoreException {
		return  getInt(row,ARTICULOS_ARTICULO_ID);
	}

	/**
	 * Set the value of the articulos.articulo_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosArticuloId(int newValue) throws DataStoreException {
		setInt(ARTICULOS_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the articulos.articulo_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosArticuloId(int row,int newValue) throws DataStoreException {
		setInt(row,ARTICULOS_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos.clase_articulo_id column for the current row.
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getArticulosClaseArticuloId() throws DataStoreException {
		return  getInt(ARTICULOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the articulos.clase_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @return int
	 * @throws DataStoreException
	 */ 
	public int getArticulosClaseArticuloId(int row) throws DataStoreException {
		return  getInt(row,ARTICULOS_CLASE_ARTICULO_ID);
	}

	/**
	 * Set the value of the articulos.clase_articulo_id column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaseArticuloId(int newValue) throws DataStoreException {
		setInt(ARTICULOS_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the articulos.clase_articulo_id column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaseArticuloId(int row,int newValue) throws DataStoreException {
		setInt(row,ARTICULOS_CLASE_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosNombre() throws DataStoreException {
		return  getString(ARTICULOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosNombre(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_NOMBRE);
	}

	/**
	 * Set the value of the articulos.nombre column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosNombre(String newValue) throws DataStoreException {
		setString(ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos.nombre column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosNombre(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosDescripcion() throws DataStoreException {
		return  getString(ARTICULOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosDescripcion(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos.descripcion column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosDescripcion(String newValue) throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosDescripcion(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos.observaciones column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosObservaciones() throws DataStoreException {
		return  getString(ARTICULOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the articulos.observaciones column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosObservaciones(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the articulos.observaciones column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosObservaciones(String newValue) throws DataStoreException {
		setString(ARTICULOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the articulos.observaciones column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosObservaciones(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosDescripcionCompleta() throws DataStoreException {
		return  getString(ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosDescripcionCompleta(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosDescripcionCompleta(String newValue) throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosDescripcionCompleta(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa1 column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna1() throws DataStoreException {
		return  getString(ARTICULOS_CLAVE_EXTERNA1);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa1 column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna1(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_CLAVE_EXTERNA1);
	}

	/**
	 * Set the value of the articulos.clave_externa1 column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna1(String newValue) throws DataStoreException {
		setString(ARTICULOS_CLAVE_EXTERNA1, newValue);
	}

	/**
	 * Set the value of the articulos.clave_externa1 column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna1(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_CLAVE_EXTERNA1, newValue);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa2 column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna2() throws DataStoreException {
		return  getString(ARTICULOS_CLAVE_EXTERNA2);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa2 column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna2(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_CLAVE_EXTERNA2);
	}

	/**
	 * Set the value of the articulos.clave_externa2 column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna2(String newValue) throws DataStoreException {
		setString(ARTICULOS_CLAVE_EXTERNA2, newValue);
	}

	/**
	 * Set the value of the articulos.clave_externa2 column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna2(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_CLAVE_EXTERNA2, newValue);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa3 column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna3() throws DataStoreException {
		return  getString(ARTICULOS_CLAVE_EXTERNA3);
	}

	/**
	 * Retrieve the value of the articulos.clave_externa3 column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosClaveExterna3(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_CLAVE_EXTERNA3);
	}

	/**
	 * Set the value of the articulos.clave_externa3 column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna3(String newValue) throws DataStoreException {
		setString(ARTICULOS_CLAVE_EXTERNA3, newValue);
	}

	/**
	 * Set the value of the articulos.clave_externa3 column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosClaveExterna3(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_CLAVE_EXTERNA3, newValue);
	}

	/**
	 * Retrieve the value of the articulos.activo column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosActivo() throws DataStoreException {
		return  getString(ARTICULOS_ACTIVO);
	}

	/**
	 * Retrieve the value of the articulos.activo column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosActivo(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_ACTIVO);
	}

	/**
	 * Set the value of the articulos.activo column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosActivo(String newValue) throws DataStoreException {
		setString(ARTICULOS_ACTIVO, newValue);
	}

	/**
	 * Set the value of the articulos.activo column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosActivo(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_ACTIVO, newValue);
	}

	/**
	 * Retrieve the value of the articulos.anulado column for the current row.
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosAnulado() throws DataStoreException {
		return  getString(ARTICULOS_ANULADO);
	}

	/**
	 * Retrieve the value of the articulos.anulado column for the specified row.
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */ 
	public String getArticulosAnulado(int row) throws DataStoreException {
		return  getString(row,ARTICULOS_ANULADO);
	}

	/**
	 * Set the value of the articulos.anulado column for the current row.
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosAnulado(String newValue) throws DataStoreException {
		setString(ARTICULOS_ANULADO, newValue);
	}

	/**
	 * Set the value of the articulos.anulado column for the specified row.
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */ 
	public void setArticulosAnulado(int row,String newValue) throws DataStoreException {
		setString(row,ARTICULOS_ANULADO, newValue);
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

	//$CUSTOMMETHODS$
	//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getArticulosArticuloId();
	}
	
	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		validarArticulos();
		super.update(conn, handleTrans);
	}
	
	public void validarArticulos() throws SQLException, DataStoreException {		
		boolean hubo_errores_clase_articulo;
		if (getRowStatus() == STATUS_NEW_MODIFIED
				|| getRowStatus() == STATUS_MODIFIED) {
			hubo_errores_clase_articulo = (validarClaseArticulo() == -1);
		}
	}
	
	public int validarClaseArticulo() throws SQLException, DataStoreException {
		ClaseArticuloModel dsClaseArticulo = new ClaseArticuloModel(getAppName(),"inventario");
		
		if (getClaseArticuloNombre() == null) 
			throw new DataStoreException("La clase del artículo es obligatoria.");	
		
		dsClaseArticulo.retrieve(ClaseArticuloModel.CLASE_ARTICULO_NOMBRE + " = '" + getClaseArticuloNombre() + "'");
		dsClaseArticulo.gotoFirst();
		
		int claseId = dsClaseArticulo.getRow();		
		if (claseId != -1) {
			setArticulosClaseArticuloId(dsClaseArticulo.getClaseArticuloClaseArticuloId());
		} else {
			throw new DataStoreException("Error determinando clase de artículo");
		}		
		return claseId;
	}
	//$ENDCUSTOMMETHODS$

}
