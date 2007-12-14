package infraestructura.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ClaseListaValoresAtributoModel: A SOFIA generated model
 */
public class ClaseLovAtributoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -3742485133660049347L;
	//constants for columns
     public static final String CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID="clase_lov_atributo.clase_lov_atributo_id";
     public static final String CLASE_LOV_ATRIBUTO_NOMBRE="clase_lov_atributo.nombre";
     public static final String CLASE_LOV_ATRIBUTO_DESCRIPCION="clase_lov_atributo.descripcion";
     public static final String CLASE_LOV_ATRIBUTO_OBSERVACIONES="clase_lov_atributo.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ClaseListaValoresAtributoModel object.
      * @param appName The SOFIA application name
      */
     public ClaseLovAtributoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ClaseListaValoresAtributoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ClaseLovAtributoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("clase_lov_atributo"),"clase_lov_atributo");

               //add columns
               addColumn(computeTableName("clase_lov_atributo"),"clase_lov_atributo_id",DataStore.DATATYPE_INT,true,true,CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
               addColumn(computeTableName("clase_lov_atributo"),"nombre",DataStore.DATATYPE_STRING,false,true,CLASE_LOV_ATRIBUTO_NOMBRE);
               addColumn(computeTableName("clase_lov_atributo"),"descripcion",DataStore.DATATYPE_STRING,false,true,CLASE_LOV_ATRIBUTO_DESCRIPCION);
               addColumn(computeTableName("clase_lov_atributo"),"observaciones",DataStore.DATATYPE_STRING,false,true,CLASE_LOV_ATRIBUTO_OBSERVACIONES);

               //set order by
               setOrderBy(computeTableAndFieldName("clase_lov_atributo.nombre") + " ASC");
               
               //set autoincrement
               setAutoIncrement(CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, true);
               setUpdateable(CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, false);

               //add validations
               addRequiredRule(CLASE_LOV_ATRIBUTO_NOMBRE,"El nombre de la clase de lista de atributo es obligatorio.");
               addExpressionRule(CLASE_LOV_ATRIBUTO_NOMBRE,new ConvierteMayusculasValidation(CLASE_LOV_ATRIBUTO_NOMBRE),"",false);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the clase_lov_atributo.clase_lov_atributo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getClaseListaValoresAtributoClaseListaValoresAtributoId() throws DataStoreException {
          return  getInt(CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.clase_lov_atributo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getClaseListaValoresAtributoClaseListaValoresAtributoId(int row) throws DataStoreException {
          return  getInt(row,CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
     }

     /**
      * Set the value of the clase_lov_atributo.clase_lov_atributo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoClaseListaValoresAtributoId(int newValue) throws DataStoreException {
          setInt(CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, newValue);
     }

     /**
      * Set the value of the clase_lov_atributo.clase_lov_atributo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoClaseListaValoresAtributoId(int row,int newValue) throws DataStoreException {
          setInt(row,CLASE_LOV_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, newValue);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoNombre() throws DataStoreException {
          return  getString(CLASE_LOV_ATRIBUTO_NOMBRE);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoNombre(int row) throws DataStoreException {
          return  getString(row,CLASE_LOV_ATRIBUTO_NOMBRE);
     }

     /**
      * Set the value of the clase_lov_atributo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoNombre(String newValue) throws DataStoreException {
          setString(CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
     }

     /**
      * Set the value of the clase_lov_atributo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoNombre(int row,String newValue) throws DataStoreException {
          setString(row,CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoDescripcion() throws DataStoreException {
          return  getString(CLASE_LOV_ATRIBUTO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoDescripcion(int row) throws DataStoreException {
          return  getString(row,CLASE_LOV_ATRIBUTO_DESCRIPCION);
     }

     /**
      * Set the value of the clase_lov_atributo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoDescripcion(String newValue) throws DataStoreException {
          setString(CLASE_LOV_ATRIBUTO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the clase_lov_atributo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,CLASE_LOV_ATRIBUTO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoObservaciones() throws DataStoreException {
          return  getString(CLASE_LOV_ATRIBUTO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the clase_lov_atributo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseListaValoresAtributoObservaciones(int row) throws DataStoreException {
          return  getString(row,CLASE_LOV_ATRIBUTO_OBSERVACIONES);
     }

     /**
      * Set the value of the clase_lov_atributo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoObservaciones(String newValue) throws DataStoreException {
          setString(CLASE_LOV_ATRIBUTO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the clase_lov_atributo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseListaValoresAtributoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,CLASE_LOV_ATRIBUTO_OBSERVACIONES, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
