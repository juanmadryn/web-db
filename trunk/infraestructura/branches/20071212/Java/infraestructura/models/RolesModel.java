package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * RolesModel: A SOFIA generated model
 */
public class RolesModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 2033771704470464149L;
	//constants for columns
     public static final String ROLES_ROL_ID="roles.rol_id";
     public static final String ROLES_NOMBRE="roles.nombre";
     public static final String ROLES_DESCRIPCION="roles.descripcion";
     public static final String ROLES_OBSERVACIONES="roles.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new RolesModel object.
      * @param appName The SOFIA application name
      */
     public RolesModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new RolesModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public RolesModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("roles"),"rol_id",DataStore.DATATYPE_INT,true,true,ROLES_ROL_ID);
          addColumn(computeTableName("roles"),"nombre",DataStore.DATATYPE_STRING,false,true,ROLES_NOMBRE);
          addColumn(computeTableName("roles"),"descripcion",DataStore.DATATYPE_STRING,false,true,ROLES_DESCRIPCION);
          addColumn(computeTableName("roles"),"observaciones",DataStore.DATATYPE_STRING,false,true,ROLES_OBSERVACIONES);

          //set order by
          setOrderBy(computeTableAndFieldName("roles.nombre") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the roles.rol_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getRolesRolId() throws DataStoreException {
          return  getInt(ROLES_ROL_ID);
     }

     /**
      * Retrieve the value of the roles.rol_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getRolesRolId(int row) throws DataStoreException {
          return  getInt(row,ROLES_ROL_ID);
     }

     /**
      * Set the value of the roles.rol_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesRolId(int newValue) throws DataStoreException {
          setInt(ROLES_ROL_ID, newValue);
     }

     /**
      * Set the value of the roles.rol_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesRolId(int row,int newValue) throws DataStoreException {
          setInt(row,ROLES_ROL_ID, newValue);
     }

     /**
      * Retrieve the value of the roles.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesNombre() throws DataStoreException {
          return  getString(ROLES_NOMBRE);
     }

     /**
      * Retrieve the value of the roles.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesNombre(int row) throws DataStoreException {
          return  getString(row,ROLES_NOMBRE);
     }

     /**
      * Set the value of the roles.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesNombre(String newValue) throws DataStoreException {
          setString(ROLES_NOMBRE, newValue);
     }

     /**
      * Set the value of the roles.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesNombre(int row,String newValue) throws DataStoreException {
          setString(row,ROLES_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the roles.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesDescripcion() throws DataStoreException {
          return  getString(ROLES_DESCRIPCION);
     }

     /**
      * Retrieve the value of the roles.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesDescripcion(int row) throws DataStoreException {
          return  getString(row,ROLES_DESCRIPCION);
     }

     /**
      * Set the value of the roles.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesDescripcion(String newValue) throws DataStoreException {
          setString(ROLES_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the roles.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ROLES_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the roles.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesObservaciones() throws DataStoreException {
          return  getString(ROLES_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the roles.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolesObservaciones(int row) throws DataStoreException {
          return  getString(row,ROLES_OBSERVACIONES);
     }

     /**
      * Set the value of the roles.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesObservaciones(String newValue) throws DataStoreException {
          setString(ROLES_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the roles.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolesObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ROLES_OBSERVACIONES, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
