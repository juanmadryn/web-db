package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * UsuarioPreferenciasModel: A SOFIA generated model
 */
public class UsuarioPreferenciasModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 4036337901526429287L;
	//constants for columns
     public static final String USUARIO_PREFERENCIAS_USER_ID="usuario_preferencias.user_id";
     public static final String USUARIO_PREFERENCIAS_PREFERENCIA_ID="usuario_preferencias.preferencia_id";
     public static final String USUARIO_PREFERENCIAS_VALOR="usuario_preferencias.valor";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new UsuarioPreferenciasModel object.
      * @param appName The SOFIA application name
      */
     public UsuarioPreferenciasModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new UsuarioPreferenciasModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public UsuarioPreferenciasModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("usuario_preferencias"),"user_id",DataStore.DATATYPE_INT,true,true,USUARIO_PREFERENCIAS_USER_ID);
          addColumn(computeTableName("usuario_preferencias"),"preferencia_id",DataStore.DATATYPE_INT,true,true,USUARIO_PREFERENCIAS_PREFERENCIA_ID);
          addColumn(computeTableName("usuario_preferencias"),"valor",DataStore.DATATYPE_STRING,false,true,USUARIO_PREFERENCIAS_VALOR);

          //set order by
          setOrderBy(computeTableAndFieldName("usuario_preferencias.user_id") + " ASC," + computeTableAndFieldName("usuario_preferencias.preferencia_id") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the usuario_preferencias.user_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioPreferenciasUserId() throws DataStoreException {
          return  getInt(USUARIO_PREFERENCIAS_USER_ID);
     }

     /**
      * Retrieve the value of the usuario_preferencias.user_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioPreferenciasUserId(int row) throws DataStoreException {
          return  getInt(row,USUARIO_PREFERENCIAS_USER_ID);
     }

     /**
      * Set the value of the usuario_preferencias.user_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasUserId(int newValue) throws DataStoreException {
          setInt(USUARIO_PREFERENCIAS_USER_ID, newValue);
     }

     /**
      * Set the value of the usuario_preferencias.user_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasUserId(int row,int newValue) throws DataStoreException {
          setInt(row,USUARIO_PREFERENCIAS_USER_ID, newValue);
     }

     /**
      * Retrieve the value of the usuario_preferencias.preferencia_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioPreferenciasPreferenciaId() throws DataStoreException {
          return  getInt(USUARIO_PREFERENCIAS_PREFERENCIA_ID);
     }

     /**
      * Retrieve the value of the usuario_preferencias.preferencia_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioPreferenciasPreferenciaId(int row) throws DataStoreException {
          return  getInt(row,USUARIO_PREFERENCIAS_PREFERENCIA_ID);
     }

     /**
      * Set the value of the usuario_preferencias.preferencia_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasPreferenciaId(int newValue) throws DataStoreException {
          setInt(USUARIO_PREFERENCIAS_PREFERENCIA_ID, newValue);
     }

     /**
      * Set the value of the usuario_preferencias.preferencia_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasPreferenciaId(int row,int newValue) throws DataStoreException {
          setInt(row,USUARIO_PREFERENCIAS_PREFERENCIA_ID, newValue);
     }

     /**
      * Retrieve the value of the usuario_preferencias.valor column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getUsuarioPreferenciasValor() throws DataStoreException {
          return  getString(USUARIO_PREFERENCIAS_VALOR);
     }

     /**
      * Retrieve the value of the usuario_preferencias.valor column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getUsuarioPreferenciasValor(int row) throws DataStoreException {
          return  getString(row,USUARIO_PREFERENCIAS_VALOR);
     }

     /**
      * Set the value of the usuario_preferencias.valor column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasValor(String newValue) throws DataStoreException {
          setString(USUARIO_PREFERENCIAS_VALOR, newValue);
     }

     /**
      * Set the value of the usuario_preferencias.valor column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioPreferenciasValor(int row,String newValue) throws DataStoreException {
          setString(row,USUARIO_PREFERENCIAS_VALOR, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
