package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ClaseAtributoRolModel: A SOFIA generated model
 */
public class ClaseAtributoRolModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 5053001786365044726L;
	//constants for columns
     public static final String CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID="clase_atributo_rol.clase_atributo_rol_id";
     public static final String CLASE_ATRIBUTO_ROL_ETIQUETA="clase_atributo_rol.etiqueta";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ClaseAtributoRolModel object.
      * @param appName The SOFIA application name
      */
     public ClaseAtributoRolModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ClaseAtributoRolModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ClaseAtributoRolModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("clase_atributo_rol"),"clase_atributo_rol");

               //add columns
               addColumn(computeTableName("clase_atributo_rol"),"clase_atributo_rol_id",DataStore.DATATYPE_INT,true,true,CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID);
               addColumn(computeTableName("clase_atributo_rol"),"etiqueta",DataStore.DATATYPE_STRING,false,true,CLASE_ATRIBUTO_ROL_ETIQUETA);

               //set order by
               setOrderBy(computeTableAndFieldName("clase_atributo_rol.etiqueta") + " ASC");

               //add validations
               addRequiredRule(CLASE_ATRIBUTO_ROL_ETIQUETA,"La etiqueta es obligatoria para la clase");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the clase_atributo_rol.clase_atributo_rol_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getClaseAtributoRolClaseAtributoRolId() throws DataStoreException {
          return  getInt(CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID);
     }

     /**
      * Retrieve the value of the clase_atributo_rol.clase_atributo_rol_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getClaseAtributoRolClaseAtributoRolId(int row) throws DataStoreException {
          return  getInt(row,CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID);
     }

     /**
      * Set the value of the clase_atributo_rol.clase_atributo_rol_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolClaseAtributoRolId(int newValue) throws DataStoreException {
          setInt(CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID, newValue);
     }

     /**
      * Set the value of the clase_atributo_rol.clase_atributo_rol_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolClaseAtributoRolId(int row,int newValue) throws DataStoreException {
          setInt(row,CLASE_ATRIBUTO_ROL_CLASE_ATRIBUTO_ROL_ID, newValue);
     }

     /**
      * Retrieve the value of the clase_atributo_rol.etiqueta column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseAtributoRolEtiqueta() throws DataStoreException {
          return  getString(CLASE_ATRIBUTO_ROL_ETIQUETA);
     }

     /**
      * Retrieve the value of the clase_atributo_rol.etiqueta column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseAtributoRolEtiqueta(int row) throws DataStoreException {
          return  getString(row,CLASE_ATRIBUTO_ROL_ETIQUETA);
     }

     /**
      * Set the value of the clase_atributo_rol.etiqueta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolEtiqueta(String newValue) throws DataStoreException {
          setString(CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
     }

     /**
      * Set the value of the clase_atributo_rol.etiqueta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolEtiqueta(int row,String newValue) throws DataStoreException {
          setString(row,CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
