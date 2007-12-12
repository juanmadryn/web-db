package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * DiccionarioAplicacionDetalleModel: A SOFIA generated model
 */
public class DiccionarioAplicacionDetalleModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -2074193711308184200L;
	//constants for columns
     public static final String DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO="diccionario_aplicacion_detalle.tipo_objeto";
     public static final String DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO="diccionario_aplicacion_detalle.nombre_objeto";
     public static final String DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE="diccionario_aplicacion_detalle.tipo_detalle";
     public static final String DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE="diccionario_aplicacion_detalle.nombre_detalle";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new DiccionarioAplicacionDetalleModel object.
      * @param appName The SOFIA application name
      */
     public DiccionarioAplicacionDetalleModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new DiccionarioAplicacionDetalleModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public DiccionarioAplicacionDetalleModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("diccionario_aplicacion_detalle"),"tipo_objeto",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO);
          addColumn(computeTableName("diccionario_aplicacion_detalle"),"nombre_objeto",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO);
          addColumn(computeTableName("diccionario_aplicacion_detalle"),"tipo_detalle",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE);
          addColumn(computeTableName("diccionario_aplicacion_detalle"),"nombre_detalle",DataStore.DATATYPE_STRING,true,true,DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleTipoObjeto() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleTipoObjeto(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleTipoObjeto(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleNombreObjeto() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleNombreObjeto(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleNombreObjeto(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.tipo_detalle column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleTipoDetalle() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.tipo_detalle column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleTipoDetalle(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.tipo_detalle column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleTipoDetalle(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.tipo_detalle column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleTipoDetalle(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE, newValue);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.nombre_detalle column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleNombreDetalle() throws DataStoreException {
          return  getString(DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE);
     }

     /**
      * Retrieve the value of the diccionario_aplicacion_detalle.nombre_detalle column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDiccionarioAplicacionDetalleNombreDetalle(int row) throws DataStoreException {
          return  getString(row,DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.nombre_detalle column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleNombreDetalle(String newValue) throws DataStoreException {
          setString(DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE, newValue);
     }

     /**
      * Set the value of the diccionario_aplicacion_detalle.nombre_detalle column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDiccionarioAplicacionDetalleNombreDetalle(int row,String newValue) throws DataStoreException {
          setString(row,DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
