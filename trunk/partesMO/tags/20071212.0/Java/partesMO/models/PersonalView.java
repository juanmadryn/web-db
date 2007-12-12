package partesMO.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * PersonalView: A SOFIA generated model
 */
public class PersonalView extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -280391968317283244L;
	//constants for columns
     public static final String PERSONAL_PERSONAL_ID="personal.personal_id";
     public static final String PERSONAL_LEGAJO="personal.legajo";
     public static final String PERSONAL_NOMBRE="personal.nombre";
     public static final String PERSONAL_CATEGORIA="personal.categoria";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new PersonalView object.
      * @param appName The SOFIA application name
      */
     public PersonalView (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new PersonalView object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public PersonalView (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("personal"),"personal");

          //add columns
          addColumn(computeTableName("personal"),"personal_id",DataStore.DATATYPE_INT,true,false,PERSONAL_PERSONAL_ID);
          addColumn(computeTableName("personal"),"legajo",DataStore.DATATYPE_STRING,false,false,PERSONAL_LEGAJO);
          addColumn(computeTableName("personal"),"nombre",DataStore.DATATYPE_STRING,false,false,PERSONAL_NOMBRE);
          addColumn(computeTableName("personal"),"categoria",DataStore.DATATYPE_STRING,false,false,PERSONAL_CATEGORIA);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the personal.personal_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPersonalPersonalId() throws DataStoreException {
          return  getInt(PERSONAL_PERSONAL_ID);
     }

     /**
      * Retrieve the value of the personal.personal_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPersonalPersonalId(int row) throws DataStoreException {
          return  getInt(row,PERSONAL_PERSONAL_ID);
     }

     /**
      * Set the value of the personal.personal_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalPersonalId(int newValue) throws DataStoreException {
          setInt(PERSONAL_PERSONAL_ID, newValue);
     }

     /**
      * Set the value of the personal.personal_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalPersonalId(int row,int newValue) throws DataStoreException {
          setInt(row,PERSONAL_PERSONAL_ID, newValue);
     }

     /**
      * Retrieve the value of the personal.legajo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalLegajo() throws DataStoreException {
          return  getString(PERSONAL_LEGAJO);
     }

     /**
      * Retrieve the value of the personal.legajo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalLegajo(int row) throws DataStoreException {
          return  getString(row,PERSONAL_LEGAJO);
     }

     /**
      * Set the value of the personal.legajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalLegajo(String newValue) throws DataStoreException {
          setString(PERSONAL_LEGAJO, newValue);
     }

     /**
      * Set the value of the personal.legajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalLegajo(int row,String newValue) throws DataStoreException {
          setString(row,PERSONAL_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the personal.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalNombre() throws DataStoreException {
          return  getString(PERSONAL_NOMBRE);
     }

     /**
      * Retrieve the value of the personal.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalNombre(int row) throws DataStoreException {
          return  getString(row,PERSONAL_NOMBRE);
     }

     /**
      * Set the value of the personal.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalNombre(String newValue) throws DataStoreException {
          setString(PERSONAL_NOMBRE, newValue);
     }

     /**
      * Set the value of the personal.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalNombre(int row,String newValue) throws DataStoreException {
          setString(row,PERSONAL_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the personal.categoria column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalCategoria() throws DataStoreException {
          return  getString(PERSONAL_CATEGORIA);
     }

     /**
      * Retrieve the value of the personal.categoria column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPersonalCategoria(int row) throws DataStoreException {
          return  getString(row,PERSONAL_CATEGORIA);
     }

     /**
      * Set the value of the personal.categoria column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalCategoria(String newValue) throws DataStoreException {
          setString(PERSONAL_CATEGORIA, newValue);
     }

     /**
      * Set the value of the personal.categoria column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPersonalCategoria(int row,String newValue) throws DataStoreException {
          setString(row,PERSONAL_CATEGORIA, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
