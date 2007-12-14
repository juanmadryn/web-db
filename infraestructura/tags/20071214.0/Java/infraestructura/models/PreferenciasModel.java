package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * PreferenciasModel: A SOFIA generated model
 */
public class PreferenciasModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 3711565396961953337L;
	//constants for columns
     public static final String PREFERENCIAS_PREFERENCIA_ID="preferencias.preferencia_id";
     public static final String PREFERENCIAS_NOMBRE="preferencias.nombre";
     public static final String PREFERENCIAS_DESCRIPCION="preferencias.descripcion";
     public static final String PREFERENCIAS_OBSERVACIONES="preferencias.observaciones";
     public static final String PREFERENCIAS_DEFAULT="preferencias.default";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new PreferenciasModel object.
      * @param appName The SOFIA application name
      */
     public PreferenciasModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new PreferenciasModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public PreferenciasModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("preferencias"),"preferencia_id",DataStore.DATATYPE_INT,true,true,PREFERENCIAS_PREFERENCIA_ID);
          addColumn(computeTableName("preferencias"),"nombre",DataStore.DATATYPE_STRING,false,true,PREFERENCIAS_NOMBRE);
          addColumn(computeTableName("preferencias"),"descripcion",DataStore.DATATYPE_STRING,false,true,PREFERENCIAS_DESCRIPCION);
          addColumn(computeTableName("preferencias"),"observaciones",DataStore.DATATYPE_STRING,false,true,PREFERENCIAS_OBSERVACIONES);
          addColumn(computeTableName("preferencias"),"default",DataStore.DATATYPE_STRING,false,true,PREFERENCIAS_DEFAULT);

          //set order by
          setOrderBy(computeTableAndFieldName("preferencias.nombre") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the preferencias.preferencia_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPreferenciasPreferenciaId() throws DataStoreException {
          return  getInt(PREFERENCIAS_PREFERENCIA_ID);
     }

     /**
      * Retrieve the value of the preferencias.preferencia_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPreferenciasPreferenciaId(int row) throws DataStoreException {
          return  getInt(row,PREFERENCIAS_PREFERENCIA_ID);
     }

     /**
      * Set the value of the preferencias.preferencia_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasPreferenciaId(int newValue) throws DataStoreException {
          setInt(PREFERENCIAS_PREFERENCIA_ID, newValue);
     }

     /**
      * Set the value of the preferencias.preferencia_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasPreferenciaId(int row,int newValue) throws DataStoreException {
          setInt(row,PREFERENCIAS_PREFERENCIA_ID, newValue);
     }

     /**
      * Retrieve the value of the preferencias.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasNombre() throws DataStoreException {
          return  getString(PREFERENCIAS_NOMBRE);
     }

     /**
      * Retrieve the value of the preferencias.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasNombre(int row) throws DataStoreException {
          return  getString(row,PREFERENCIAS_NOMBRE);
     }

     /**
      * Set the value of the preferencias.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasNombre(String newValue) throws DataStoreException {
          setString(PREFERENCIAS_NOMBRE, newValue);
     }

     /**
      * Set the value of the preferencias.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasNombre(int row,String newValue) throws DataStoreException {
          setString(row,PREFERENCIAS_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the preferencias.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasDescripcion() throws DataStoreException {
          return  getString(PREFERENCIAS_DESCRIPCION);
     }

     /**
      * Retrieve the value of the preferencias.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasDescripcion(int row) throws DataStoreException {
          return  getString(row,PREFERENCIAS_DESCRIPCION);
     }

     /**
      * Set the value of the preferencias.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasDescripcion(String newValue) throws DataStoreException {
          setString(PREFERENCIAS_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the preferencias.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,PREFERENCIAS_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the preferencias.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasObservaciones() throws DataStoreException {
          return  getString(PREFERENCIAS_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the preferencias.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasObservaciones(int row) throws DataStoreException {
          return  getString(row,PREFERENCIAS_OBSERVACIONES);
     }

     /**
      * Set the value of the preferencias.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasObservaciones(String newValue) throws DataStoreException {
          setString(PREFERENCIAS_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the preferencias.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,PREFERENCIAS_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the preferencias.default column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasDefault() throws DataStoreException {
          return  getString(PREFERENCIAS_DEFAULT);
     }

     /**
      * Retrieve the value of the preferencias.default column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPreferenciasDefault(int row) throws DataStoreException {
          return  getString(row,PREFERENCIAS_DEFAULT);
     }

     /**
      * Set the value of the preferencias.default column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasDefault(String newValue) throws DataStoreException {
          setString(PREFERENCIAS_DEFAULT, newValue);
     }

     /**
      * Set the value of the preferencias.default column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPreferenciasDefault(int row,String newValue) throws DataStoreException {
          setString(row,PREFERENCIAS_DEFAULT, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
