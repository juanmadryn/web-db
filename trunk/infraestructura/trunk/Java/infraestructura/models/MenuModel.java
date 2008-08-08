package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * MenuModel: A SOFIA generated model
 */
public class MenuModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 4224257784157635070L;
	//constants for columns
     public static final String MENU_MENU_ID="menu.menu_id";
     public static final String MENU_NOMBRE="menu.nombre";
     public static final String MENU_DESCRIPCION="menu.descripcion";
     public static final String MENU_URL="menu.url";
     public static final String MENU_GRUPO="menu.grupo";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new MenuModel object.
      * @param appName The SOFIA application name
      */
     public MenuModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new MenuModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public MenuModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("menu"),null);

          //add columns
          addColumn(computeTableName("menu"),"menu_id",DataStore.DATATYPE_INT,true,true,MENU_MENU_ID);
          addColumn(computeTableName("menu"),"nombre",DataStore.DATATYPE_STRING,false,true,MENU_NOMBRE);
          addColumn(computeTableName("menu"),"descripcion",DataStore.DATATYPE_STRING,false,true,MENU_DESCRIPCION);
          addColumn(computeTableName("menu"),"url",DataStore.DATATYPE_STRING,false,true,MENU_URL);
          addColumn(computeTableName("menu"),"grupo",DataStore.DATATYPE_STRING,false,true,MENU_GRUPO);

          //set order by
          setOrderBy(computeTableAndFieldName("menu.menu_id") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the menu.menu_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMenuMenuId() throws DataStoreException {
          return  getInt(MENU_MENU_ID);
     }

     /**
      * Retrieve the value of the menu.menu_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMenuMenuId(int row) throws DataStoreException {
          return  getInt(row,MENU_MENU_ID);
     }

     /**
      * Set the value of the menu.menu_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuMenuId(int newValue) throws DataStoreException {
          setInt(MENU_MENU_ID, newValue);
     }

     /**
      * Set the value of the menu.menu_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuMenuId(int row,int newValue) throws DataStoreException {
          setInt(row,MENU_MENU_ID, newValue);
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
      * Retrieve the value of the menu.url column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuUrl() throws DataStoreException {
          return  getString(MENU_URL);
     }

     /**
      * Retrieve the value of the menu.url column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMenuUrl(int row) throws DataStoreException {
          return  getString(row,MENU_URL);
     }

     /**
      * Set the value of the menu.url column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuUrl(String newValue) throws DataStoreException {
          setString(MENU_URL, newValue);
     }

     /**
      * Set the value of the menu.url column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMenuUrl(int row,String newValue) throws DataStoreException {
          setString(row,MENU_URL, newValue);
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
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     //$ENDCUSTOMMETHODS$
     
}
