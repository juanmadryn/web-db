package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * UsuarioRolesModel: A SOFIA generated model
 */
public class UsuarioRolesModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -7372371120348857890L;
	//constants for columns
     public static final String USUARIO_ROLES_USER_ID="usuario_roles.user_id";
     public static final String USUARIO_ROLES_ROL_ID="usuario_roles.rol_id";
     public static final String USUARIO_ROLES_DESDE="usuario_roles.desde";
     public static final String USUARIO_ROLES_HASTA="usuario_roles.hasta";
     public static final String USUARIO_ROLES_ANULADO="usuario_roles.anulado";
     public static final String ROLES_NOMBRE="roles.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new UsuarioRolesModel object.
      * @param appName The SOFIA application name
      */
     public UsuarioRolesModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new UsuarioRolesModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public UsuarioRolesModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("usuario_roles"),null);

          //add columns
          addColumn(computeTableName("usuario_roles"),"user_id",DataStore.DATATYPE_INT,true,true,USUARIO_ROLES_USER_ID);
          addColumn(computeTableName("usuario_roles"),"rol_id",DataStore.DATATYPE_INT,true,true,USUARIO_ROLES_ROL_ID);
          addColumn(computeTableName("usuario_roles"),"desde",DataStore.DATATYPE_DATE,false,true,USUARIO_ROLES_DESDE);
          addColumn(computeTableName("usuario_roles"),"hasta",DataStore.DATATYPE_DATE,false,true,USUARIO_ROLES_HASTA);
          addColumn(computeTableName("usuario_roles"),"anulado",DataStore.DATATYPE_STRING,false,true,USUARIO_ROLES_ANULADO);
          addColumn(computeTableName("roles"),"nombre",DataStore.DATATYPE_STRING,false,false,ROLES_NOMBRE);

          //add joins
          addJoin(computeTableAndFieldName("usuario_roles.rol_id"),computeTableAndFieldName("roles.rol_id"),false);

          //set order by
          setOrderBy(computeTableAndFieldName("usuario_roles.user_id") + " ASC," + computeTableAndFieldName("usuario_roles.rol_id") + " ASC," + computeTableAndFieldName("usuario_roles.desde") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the usuario_roles.user_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioRolesUserId() throws DataStoreException {
          return  getInt(USUARIO_ROLES_USER_ID);
     }

     /**
      * Retrieve the value of the usuario_roles.user_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioRolesUserId(int row) throws DataStoreException {
          return  getInt(row,USUARIO_ROLES_USER_ID);
     }

     /**
      * Set the value of the usuario_roles.user_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesUserId(int newValue) throws DataStoreException {
          setInt(USUARIO_ROLES_USER_ID, newValue);
     }

     /**
      * Set the value of the usuario_roles.user_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesUserId(int row,int newValue) throws DataStoreException {
          setInt(row,USUARIO_ROLES_USER_ID, newValue);
     }

     /**
      * Retrieve the value of the usuario_roles.rol_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioRolesRolId() throws DataStoreException {
          return  getInt(USUARIO_ROLES_ROL_ID);
     }

     /**
      * Retrieve the value of the usuario_roles.rol_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getUsuarioRolesRolId(int row) throws DataStoreException {
          return  getInt(row,USUARIO_ROLES_ROL_ID);
     }

     /**
      * Set the value of the usuario_roles.rol_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesRolId(int newValue) throws DataStoreException {
          setInt(USUARIO_ROLES_ROL_ID, newValue);
     }

     /**
      * Set the value of the usuario_roles.rol_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesRolId(int row,int newValue) throws DataStoreException {
          setInt(row,USUARIO_ROLES_ROL_ID, newValue);
     }

     /**
      * Retrieve the value of the usuario_roles.desde column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getUsuarioRolesDesde() throws DataStoreException {
          return  getDate(USUARIO_ROLES_DESDE);
     }

     /**
      * Retrieve the value of the usuario_roles.desde column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getUsuarioRolesDesde(int row) throws DataStoreException {
          return  getDate(row,USUARIO_ROLES_DESDE);
     }

     /**
      * Set the value of the usuario_roles.desde column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesDesde(java.sql.Date newValue) throws DataStoreException {
          setDate(USUARIO_ROLES_DESDE, newValue);
     }

     /**
      * Set the value of the usuario_roles.desde column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesDesde(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,USUARIO_ROLES_DESDE, newValue);
     }

     /**
      * Retrieve the value of the usuario_roles.hasta column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getUsuarioRolesHasta() throws DataStoreException {
          return  getDate(USUARIO_ROLES_HASTA);
     }

     /**
      * Retrieve the value of the usuario_roles.hasta column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getUsuarioRolesHasta(int row) throws DataStoreException {
          return  getDate(row,USUARIO_ROLES_HASTA);
     }

     /**
      * Set the value of the usuario_roles.hasta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesHasta(java.sql.Date newValue) throws DataStoreException {
          setDate(USUARIO_ROLES_HASTA, newValue);
     }

     /**
      * Set the value of the usuario_roles.hasta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesHasta(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,USUARIO_ROLES_HASTA, newValue);
     }

     /**
      * Retrieve the value of the usuario_roles.anulado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getUsuarioRolesAnulado() throws DataStoreException {
          return  getString(USUARIO_ROLES_ANULADO);
     }

     /**
      * Retrieve the value of the usuario_roles.anulado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getUsuarioRolesAnulado(int row) throws DataStoreException {
          return  getString(row,USUARIO_ROLES_ANULADO);
     }

     /**
      * Set the value of the usuario_roles.anulado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesAnulado(String newValue) throws DataStoreException {
          setString(USUARIO_ROLES_ANULADO, newValue);
     }

     /**
      * Set the value of the usuario_roles.anulado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setUsuarioRolesAnulado(int row,String newValue) throws DataStoreException {
          setString(row,USUARIO_ROLES_ANULADO, newValue);
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
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
