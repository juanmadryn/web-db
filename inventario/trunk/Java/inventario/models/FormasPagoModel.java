package inventario.models;

import infraestructura.models.BaseModel;
import infraestructura.utils.ConvierteMayusculasValidation;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * FormaPagoModel: A SOFIA generated model
 */
public class FormasPagoModel extends BaseModel {

     /**
	 * 
	 */
	private static final long serialVersionUID = -8992499839526741039L;
	//constants for columns
     public static final String FORMAS_PAGO_FORMA_PAGO_ID="formas_pago.forma_pago_id";
     public static final String FORMAS_PAGO_NOMBRE="formas_pago.nombre";
     public static final String FORMAS_PAGO_DESCRIPCION="formas_pago.descripcion";
     public static final String FORMAS_PAGO_OBSERVACIONES="formas_pago.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new FormaPagoModel object.
      * @param appName The SOFIA application name
      */
     public FormasPagoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new FormaPagoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public FormasPagoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("formas_pago"),null);

               //add columns
               addColumn(computeTableName("formas_pago"),"forma_pago_id",DataStore.DATATYPE_INT,true,true,FORMAS_PAGO_FORMA_PAGO_ID);
               addColumn(computeTableName("formas_pago"),"nombre",DataStore.DATATYPE_STRING,false,true,FORMAS_PAGO_NOMBRE);
               addColumn(computeTableName("formas_pago"),"descripcion",DataStore.DATATYPE_STRING,false,true,FORMAS_PAGO_DESCRIPCION);
               addColumn(computeTableName("formas_pago"),"observaciones",DataStore.DATATYPE_STRING,false,true,FORMAS_PAGO_OBSERVACIONES);

               //set order by
               setOrderBy(computeTableAndFieldName("formas_pago.nombre") + " ASC");

               //add validations
               addRequiredRule(FORMAS_PAGO_FORMA_PAGO_ID,"El id es obligatorio");
               addRequiredRule(FORMAS_PAGO_NOMBRE,"El nombre de la forma de pago es obligatorio");
   			   addExpressionRule(FORMAS_PAGO_NOMBRE, new ConvierteMayusculasValidation(FORMAS_PAGO_NOMBRE), "", false);
               
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the formas_pago.forma_pago_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getFormasPagoFormaPagoId() throws DataStoreException {
          return  getInt(FORMAS_PAGO_FORMA_PAGO_ID);
     }

     /**
      * Retrieve the value of the formas_pago.forma_pago_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getFormasPagoFormaPagoId(int row) throws DataStoreException {
          return  getInt(row,FORMAS_PAGO_FORMA_PAGO_ID);
     }

     /**
      * Set the value of the formas_pago.forma_pago_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoFormaPagoId(int newValue) throws DataStoreException {
          setInt(FORMAS_PAGO_FORMA_PAGO_ID, newValue);
     }

     /**
      * Set the value of the formas_pago.forma_pago_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoFormaPagoId(int row,int newValue) throws DataStoreException {
          setInt(row,FORMAS_PAGO_FORMA_PAGO_ID, newValue);
     }

     /**
      * Retrieve the value of the formas_pago.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoNombre() throws DataStoreException {
          return  getString(FORMAS_PAGO_NOMBRE);
     }

     /**
      * Retrieve the value of the formas_pago.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoNombre(int row) throws DataStoreException {
          return  getString(row,FORMAS_PAGO_NOMBRE);
     }

     /**
      * Set the value of the formas_pago.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoNombre(String newValue) throws DataStoreException {
          setString(FORMAS_PAGO_NOMBRE, newValue);
     }

     /**
      * Set the value of the formas_pago.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoNombre(int row,String newValue) throws DataStoreException {
          setString(row,FORMAS_PAGO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the formas_pago.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoDescripcion() throws DataStoreException {
          return  getString(FORMAS_PAGO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the formas_pago.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoDescripcion(int row) throws DataStoreException {
          return  getString(row,FORMAS_PAGO_DESCRIPCION);
     }

     /**
      * Set the value of the formas_pago.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoDescripcion(String newValue) throws DataStoreException {
          setString(FORMAS_PAGO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the formas_pago.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,FORMAS_PAGO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the formas_pago.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoObservaciones() throws DataStoreException {
          return  getString(FORMAS_PAGO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the formas_pago.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getFormasPagoObservaciones(int row) throws DataStoreException {
          return  getString(row,FORMAS_PAGO_OBSERVACIONES);
     }

     /**
      * Set the value of the formas_pago.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoObservaciones(String newValue) throws DataStoreException {
          setString(FORMAS_PAGO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the formas_pago.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFormasPagoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,FORMAS_PAGO_OBSERVACIONES, newValue);
     }

	@Override
	public String getEstadoActual() throws DataStoreException {
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getFormasPagoFormaPagoId();
	}
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
