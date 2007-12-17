package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AccesoMenuModel: A SOFIA generated model
 */
public class AccesoMenuModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -6506385058792598662L;
	//constants for columns
     public static final String ACCESO_MENU_MENU_ID="acceso_menu.menu_id";
     public static final String ACCESO_MENU_ROL_ID="acceso_menu.rol_id";
     public static final String ACCESO_MENU_USER_ID="acceso_menu.user_id";
     public static final String WEBSITE_USER_LOGIN_NAME="website_user.login_name";
     public static final String WEBSITE_USER_APEYNOM="website_user.nombre_completo";
     public static final String ROLES_NOMBRE="roles.nombre";
     public static final String MENU_NOMBRE="menu.nombre";
     public static final String MENU_DESCRIPCION="menu.descripcion";
     public static final String MENU_GRUPO="menu.grupo";
     public static final String ACCESO_MENU_ACCESO_MENU_ID="acceso_menu.acceso_menu_id";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new AccesoMenuModel object.
      * @param appName The SOFIA application name
      */
     public AccesoMenuModel (String appName) { 
          this(appName,null);
     }

     /**
      * Create a new AccesoMenuModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public AccesoMenuModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("acceso_menu"),null);
               addTableAlias(computeTableName("website_user"),null);
               addTableAlias(computeTableName("roles"),null);
               addTableAlias(computeTableName("menu"),null);

               //add columns
               addColumn(computeTableName("acceso_menu"),"menu_id",DataStore.DATATYPE_INT,false,true,ACCESO_MENU_MENU_ID);
               addColumn(computeTableName("acceso_menu"),"rol_id",DataStore.DATATYPE_INT,false,true,ACCESO_MENU_ROL_ID);
               addColumn(computeTableName("acceso_menu"),"user_id",DataStore.DATATYPE_INT,false,true,ACCESO_MENU_USER_ID);
               addColumn(computeTableName("website_user"),"login_name",DataStore.DATATYPE_STRING,false,false,WEBSITE_USER_LOGIN_NAME);
               addColumn(computeTableName("website_user"),"nombre_completo",DataStore.DATATYPE_STRING,false,false,WEBSITE_USER_APEYNOM);
               addColumn(computeTableName("roles"),"nombre",DataStore.DATATYPE_STRING,false,false,ROLES_NOMBRE);
               addColumn(computeTableName("menu"),"nombre",DataStore.DATATYPE_STRING,false,false,MENU_NOMBRE);
               addColumn(computeTableName("menu"),"descripcion",DataStore.DATATYPE_STRING,false,false,MENU_DESCRIPCION);
               addColumn(computeTableName("menu"),"grupo",DataStore.DATATYPE_STRING,false,false,MENU_GRUPO);
               addColumn(computeTableName("acceso_menu"),"acceso_menu_id",DataStore.DATATYPE_INT,true,true,ACCESO_MENU_ACCESO_MENU_ID);

               //add joins
               addJoin(computeTableAndFieldName("acceso_menu.user_id"),computeTableAndFieldName("website_user.user_id"),true);
               addJoin(computeTableAndFieldName("acceso_menu.rol_id"),computeTableAndFieldName("roles.rol_id"),true);
               addJoin(computeTableAndFieldName("acceso_menu.menu_id"),computeTableAndFieldName("menu.menu_id"),false);

               //add validations
               addLookupRule(WEBSITE_USER_LOGIN_NAME,"website_user","'user_id = ' + acceso_menu.user_id","user_id","website_user.login_name","Usuario inexistente");
               addLookupRule(ROLES_NOMBRE,"roles","'rol_id = ' + acceso_menu.rol_id","rol_id","roles.nombre","Rol inexistente");
               addLookupRule(MENU_NOMBRE,"menu","'menu_id = ' + acceso_menu.menu_id","menu_id","menu.nombre","Menú inexistente");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the acceso_menu.menu_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuMenuId() throws DataStoreException {
          return  getInt(ACCESO_MENU_MENU_ID);
     }

     /**
      * Retrieve the value of the acceso_menu.menu_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuMenuId(int row) throws DataStoreException {
          return  getInt(row,ACCESO_MENU_MENU_ID);
     }

     /**
      * Set the value of the acceso_menu.menu_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuMenuId(int newValue) throws DataStoreException {
          setInt(ACCESO_MENU_MENU_ID, newValue);
     }

     /**
      * Set the value of the acceso_menu.menu_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuMenuId(int row,int newValue) throws DataStoreException {
          setInt(row,ACCESO_MENU_MENU_ID, newValue);
     }

     /**
      * Retrieve the value of the acceso_menu.rol_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuRolId() throws DataStoreException {
          return  getInt(ACCESO_MENU_ROL_ID);
     }

     /**
      * Retrieve the value of the acceso_menu.rol_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuRolId(int row) throws DataStoreException {
          return  getInt(row,ACCESO_MENU_ROL_ID);
     }

     /**
      * Set the value of the acceso_menu.rol_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuRolId(int newValue) throws DataStoreException {
          setInt(ACCESO_MENU_ROL_ID, newValue);
     }

     /**
      * Set the value of the acceso_menu.rol_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuRolId(int row,int newValue) throws DataStoreException {
          setInt(row,ACCESO_MENU_ROL_ID, newValue);
     }

     /**
      * Retrieve the value of the acceso_menu.user_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuUserId() throws DataStoreException {
          return  getInt(ACCESO_MENU_USER_ID);
     }

     /**
      * Retrieve the value of the acceso_menu.user_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuUserId(int row) throws DataStoreException {
          return  getInt(row,ACCESO_MENU_USER_ID);
     }

     /**
      * Set the value of the acceso_menu.user_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuUserId(int newValue) throws DataStoreException {
          setInt(ACCESO_MENU_USER_ID, newValue);
     }

     /**
      * Set the value of the acceso_menu.user_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuUserId(int row,int newValue) throws DataStoreException {
          setInt(row,ACCESO_MENU_USER_ID, newValue);
     }

     /**
      * Retrieve the value of the website_user.login_name column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getWebsiteUserLoginName() throws DataStoreException {
          return  getString(WEBSITE_USER_LOGIN_NAME);
     }

     /**
      * Retrieve the value of the website_user.login_name column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getWebsiteUserLoginName(int row) throws DataStoreException {
          return  getString(row,WEBSITE_USER_LOGIN_NAME);
     }

     /**
      * Set the value of the website_user.login_name column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setWebsiteUserLoginName(String newValue) throws DataStoreException {
          setString(WEBSITE_USER_LOGIN_NAME, newValue);
     }

     /**
      * Set the value of the website_user.login_name column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setWebsiteUserLoginName(int row,String newValue) throws DataStoreException {
          setString(row,WEBSITE_USER_LOGIN_NAME, newValue);
     }

     /**
      * Retrieve the value of the website_user.nombre_completo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getWebsiteUserNombreCompleto() throws DataStoreException {
          return  getString(WEBSITE_USER_APEYNOM);
     }

     /**
      * Retrieve the value of the website_user.nombre_completo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getWebsiteUserNombreCompleto(int row) throws DataStoreException {
          return  getString(row,WEBSITE_USER_APEYNOM);
     }

     /**
      * Set the value of the website_user.nombre_completo for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setWebsiteUserNombreCompleto(String newValue) throws DataStoreException {
          setString(WEBSITE_USER_APEYNOM, newValue);
     }

     /**
      * Set the value of the website_user.nombre_completo for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setWebsiteUserNombreCompleto(int row,String newValue) throws DataStoreException {
          setString(row,WEBSITE_USER_APEYNOM, newValue);
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
      * Retrieve the value of the menu.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuNombre() throws DataStoreException {
          return  getString(MENU_NOMBRE);
     }

     /**
      * Retrieve the value of the menu.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuNombre(int row) throws DataStoreException {
          return  getString(row,MENU_NOMBRE);
     }

     /**
      * Set the value of the menu.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuNombre(String newValue) throws DataStoreException {
          setString(MENU_NOMBRE, newValue);
     }

     /**
      * Set the value of the menu.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuNombre(int row,String newValue) throws DataStoreException {
          setString(row,MENU_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the menu.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuDescripcion() throws DataStoreException {
          return  getString(MENU_DESCRIPCION);
     }

     /**
      * Retrieve the value of the menu.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuDescripcion(int row) throws DataStoreException {
          return  getString(row,MENU_DESCRIPCION);
     }

     /**
      * Set the value of the menu.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuDescripcion(String newValue) throws DataStoreException {
          setString(MENU_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the menu.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,MENU_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the menu.grupo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuGrupo() throws DataStoreException {
          return  getString(MENU_GRUPO);
     }

     /**
      * Retrieve the value of the menu.grupo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuGrupo(int row) throws DataStoreException {
          return  getString(row,MENU_GRUPO);
     }

     /**
      * Set the value of the menu.grupo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuGrupo(String newValue) throws DataStoreException {
          setString(MENU_GRUPO, newValue);
     }

     /**
      * Set the value of the menu.grupo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuGrupo(int row,String newValue) throws DataStoreException {
          setString(row,MENU_GRUPO, newValue);
     }

     /**
      * Retrieve the value of the acceso_menu.acceso_menu_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuAccesoMenuId() throws DataStoreException {
          return  getInt(ACCESO_MENU_ACCESO_MENU_ID);
     }

     /**
      * Retrieve the value of the acceso_menu.acceso_menu_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccesoMenuAccesoMenuId(int row) throws DataStoreException {
          return  getInt(row,ACCESO_MENU_ACCESO_MENU_ID);
     }

     /**
      * Set the value of the acceso_menu.acceso_menu_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuAccesoMenuId(int newValue) throws DataStoreException {
          setInt(ACCESO_MENU_ACCESO_MENU_ID, newValue);
     }

     /**
      * Set the value of the acceso_menu.acceso_menu_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccesoMenuAccesoMenuId(int row,int newValue) throws DataStoreException {
          setInt(row,ACCESO_MENU_ACCESO_MENU_ID, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
