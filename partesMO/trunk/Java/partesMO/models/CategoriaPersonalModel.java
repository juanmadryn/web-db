package partesMO.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CategoriaPersonalModel: A SOFIA generated model
 */
public class CategoriaPersonalModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -4315389924483256401L;
	//constants for columns
     public static final String CATEGORIA_PERSONAL_CATEGORIA="categoria_personal.categoria";
     public static final String CATEGORIA_PERSONAL_NOMBRE="categoria_personal.Nombre";
     public static final String CATEGORIA_PERSONAL_DESCRIPCION="categoria_personal.descripcion";
     public static final String CATEGORIA_PERSONAL_OBSERVACIONES="categoria_personal.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new CategoriaPersonalModel object.
      * @param appName The SOFIA application name
      */
     public CategoriaPersonalModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new CategoriaPersonalModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public CategoriaPersonalModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("categoria_personal"),"categoria_personal");

               //add columns
               addColumn(computeTableName("categoria_personal"),"categoria",DataStore.DATATYPE_STRING,true,true,CATEGORIA_PERSONAL_CATEGORIA);
               addColumn(computeTableName("categoria_personal"),"Nombre",DataStore.DATATYPE_STRING,false,true,CATEGORIA_PERSONAL_NOMBRE);
               addColumn(computeTableName("categoria_personal"),"descripcion",DataStore.DATATYPE_STRING,false,true,CATEGORIA_PERSONAL_DESCRIPCION);
               addColumn(computeTableName("categoria_personal"),"observaciones",DataStore.DATATYPE_STRING,false,true,CATEGORIA_PERSONAL_OBSERVACIONES);

               //set order by
               setOrderBy(computeTableAndFieldName("categoria_personal.categoria") + " ASC");

               //add validations
               addRequiredRule(CATEGORIA_PERSONAL_NOMBRE,"Nombre de la categoría es obligatorio");
               addRequiredRule(CATEGORIA_PERSONAL_CATEGORIA,"Código de categoría obligatorio");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the categoria_personal.categoria column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalCategoria() throws DataStoreException {
          return  getString(CATEGORIA_PERSONAL_CATEGORIA);
     }

     /**
      * Retrieve the value of the categoria_personal.categoria column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalCategoria(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_PERSONAL_CATEGORIA);
     }

     /**
      * Set the value of the categoria_personal.categoria column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalCategoria(String newValue) throws DataStoreException {
          setString(CATEGORIA_PERSONAL_CATEGORIA, newValue);
     }

     /**
      * Set the value of the categoria_personal.categoria column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalCategoria(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_PERSONAL_CATEGORIA, newValue);
     }

     /**
      * Retrieve the value of the categoria_personal.Nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalNombre() throws DataStoreException {
          return  getString(CATEGORIA_PERSONAL_NOMBRE);
     }

     /**
      * Retrieve the value of the categoria_personal.Nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalNombre(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_PERSONAL_NOMBRE);
     }

     /**
      * Set the value of the categoria_personal.Nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalNombre(String newValue) throws DataStoreException {
          setString(CATEGORIA_PERSONAL_NOMBRE, newValue);
     }

     /**
      * Set the value of the categoria_personal.Nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalNombre(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_PERSONAL_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the categoria_personal.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalDescripcion() throws DataStoreException {
          return  getString(CATEGORIA_PERSONAL_DESCRIPCION);
     }

     /**
      * Retrieve the value of the categoria_personal.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalDescripcion(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_PERSONAL_DESCRIPCION);
     }

     /**
      * Set the value of the categoria_personal.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalDescripcion(String newValue) throws DataStoreException {
          setString(CATEGORIA_PERSONAL_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the categoria_personal.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_PERSONAL_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the categoria_personal.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalObservaciones() throws DataStoreException {
          return  getString(CATEGORIA_PERSONAL_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the categoria_personal.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaPersonalObservaciones(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_PERSONAL_OBSERVACIONES);
     }

     /**
      * Set the value of the categoria_personal.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalObservaciones(String newValue) throws DataStoreException {
          setString(CATEGORIA_PERSONAL_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the categoria_personal.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaPersonalObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_PERSONAL_OBSERVACIONES, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
