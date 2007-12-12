package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * DiccionarioAplicacionModel: A SOFIA generated model
 */
public class DiccionarioAplicacionModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 7318214966544586538L;
	//constants for columns
     public static final String DICCIONARIO_APLICACION_TIPO_OBJETO="diccionario_aplicacion.tipo_objeto";
     public static final String DICCIONARIO_APLICACION_NOMBRE_OBJETO="diccionario_aplicacion.nombre_objeto";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new DiccionarioAplicacionModel object.
      * @param appName The SOFIA application name
      */
     public DiccionarioAplicacionModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new DiccionarioAplicacionModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public DiccionarioAplicacionModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("diccionario_aplicacion"),"tipo_objeto",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_TIPO_OBJETO);
          addColumn(computeTableName("diccionario_aplicacion"),"nombre_objeto",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_NOMBRE_OBJETO);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the diccionario_aplicacion.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionTipoObjeto() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionTipoObjeto(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_TIPO_OBJETO);
     }

     /**
      * Set the value of the diccionario_aplicacion.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionTipoObjeto(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_TIPO_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionNombreObjeto() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionNombreObjeto(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the diccionario_aplicacion.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionNombreObjeto(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_NOMBRE_OBJETO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
