package infraestructura.models;

import infraestructura.utils.AtributoConfiguracion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ConfiguracionModel: A SOFIA generated model
 */
public class ConfiguracionModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 6954322817547376661L;
	//constants for columns
     public static final String CONFIGURACION_CONFIGURACION_ID="configuracion.configuracion_id";
     public static final String CONFIGURACION_ESQUEMA_CONFIGURACION_ID="configuracion.esquema_configuracion_id";
     public static final String CONFIGURACION_NOMBRE="configuracion.nombre";
     public static final String CONFIGURACION_DESCRIPCION="configuracion.descripcion";
     public static final String CONFIGURACION_OBSERVACIONES="configuracion.observaciones";
     public static final String CONFIGURACION_CARDINALIDAD="configuracion.cardinalidad";
     public static final String CONFIGURACION_PRIORIDAD="configuracion.prioridad";
     public static final String ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";
     public static final String ESQUEMA_CONFIGURACION_TIPO_OBJETO="esquema_configuracion.tipo_objeto";
     public static final String ESQUEMA_CONFIGURACION_NOMBRE_OBJETO="esquema_configuracion.nombre_objeto";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ConfiguracionModel object.
      * @param appName The SOFIA application name
      */
     public ConfiguracionModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ConfiguracionModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ConfiguracionModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("configuracion"),null);
               addTableAlias(computeTableName("esquema_configuracion"),null);

               //add columns
               addColumn(computeTableName("configuracion"),"configuracion_id",DataStore.DATATYPE_INT,true,true,CONFIGURACION_CONFIGURACION_ID);
               addColumn(computeTableName("configuracion"),"esquema_configuracion_id",DataStore.DATATYPE_INT,false,true,CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
               addColumn(computeTableName("configuracion"),"nombre",DataStore.DATATYPE_STRING,false,true,CONFIGURACION_NOMBRE);
               addColumn(computeTableName("configuracion"),"descripcion",DataStore.DATATYPE_STRING,false,true,CONFIGURACION_DESCRIPCION);
               addColumn(computeTableName("configuracion"),"observaciones",DataStore.DATATYPE_STRING,false,true,CONFIGURACION_OBSERVACIONES);
               addColumn(computeTableName("configuracion"),"cardinalidad",DataStore.DATATYPE_INT,false,true,CONFIGURACION_CARDINALIDAD);
               addColumn(computeTableName("configuracion"),"prioridad",DataStore.DATATYPE_INT,false,true,CONFIGURACION_PRIORIDAD);
               addColumn(computeTableName("esquema_configuracion"),"nombre",DataStore.DATATYPE_STRING,false,false,ESQUEMA_CONFIGURACION_NOMBRE);
               addColumn(computeTableName("esquema_configuracion"),"tipo_objeto",DataStore.DATATYPE_STRING,false,false,ESQUEMA_CONFIGURACION_TIPO_OBJETO);
               addColumn(computeTableName("esquema_configuracion"),"nombre_objeto",DataStore.DATATYPE_STRING,false,false,ESQUEMA_CONFIGURACION_NOMBRE_OBJETO);

               //add joins
               addJoin(computeTableAndFieldName("configuracion.esquema_configuracion_id"),computeTableAndFieldName("esquema_configuracion.esquema_configuracion_id"),false);

               //add validations
               addRequiredRule(CONFIGURACION_CARDINALIDAD,"La cardinalidad es obligatoria");
               addRequiredRule(CONFIGURACION_ESQUEMA_CONFIGURACION_ID,"El esquema de configuración es Obligatorio");
               addRequiredRule(CONFIGURACION_NOMBRE,"El nombre de la configuración es obligatorio");
               addRequiredRule(CONFIGURACION_PRIORIDAD,"La prioridad es obligatoria");
               
               // setea autoincremento para el id y no update
               setAutoIncrement(CONFIGURACION_CONFIGURACION_ID, true);
               setUpdateable(CONFIGURACION_CONFIGURACION_ID, false);
               
               // setea order by por default
               setOrderBy("configuracion.esquema_configuracion_id, configuracion.cardinalidad DESC, configuracion.prioridad ASC");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the configuracion.configuracion_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionConfiguracionId() throws DataStoreException {
          return  getInt(CONFIGURACION_CONFIGURACION_ID);
     }

     /**
      * Retrieve the value of the configuracion.configuracion_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionConfiguracionId(int row) throws DataStoreException {
          return  getInt(row,CONFIGURACION_CONFIGURACION_ID);
     }

     /**
      * Set the value of the configuracion.configuracion_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionConfiguracionId(int newValue) throws DataStoreException {
          setInt(CONFIGURACION_CONFIGURACION_ID, newValue);
     }

     /**
      * Set the value of the configuracion.configuracion_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionConfiguracionId(int row,int newValue) throws DataStoreException {
          setInt(row,CONFIGURACION_CONFIGURACION_ID, newValue);
     }

     /**
      * Retrieve the value of the configuracion.esquema_configuracion_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionEsquemaConfiguracionId() throws DataStoreException {
          return  getInt(CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
     }

     /**
      * Retrieve the value of the configuracion.esquema_configuracion_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionEsquemaConfiguracionId(int row) throws DataStoreException {
          return  getInt(row,CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
     }

     /**
      * Set the value of the configuracion.esquema_configuracion_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionEsquemaConfiguracionId(int newValue) throws DataStoreException {
          setInt(CONFIGURACION_ESQUEMA_CONFIGURACION_ID, newValue);
     }

     /**
      * Set the value of the configuracion.esquema_configuracion_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionEsquemaConfiguracionId(int row,int newValue) throws DataStoreException {
          setInt(row,CONFIGURACION_ESQUEMA_CONFIGURACION_ID, newValue);
     }

     /**
      * Retrieve the value of the configuracion.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionNombre() throws DataStoreException {
          return  getString(CONFIGURACION_NOMBRE);
     }

     /**
      * Retrieve the value of the configuracion.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionNombre(int row) throws DataStoreException {
          return  getString(row,CONFIGURACION_NOMBRE);
     }

     /**
      * Set the value of the configuracion.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionNombre(String newValue) throws DataStoreException {
          setString(CONFIGURACION_NOMBRE, newValue);
     }

     /**
      * Set the value of the configuracion.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionNombre(int row,String newValue) throws DataStoreException {
          setString(row,CONFIGURACION_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the configuracion.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionDescripcion() throws DataStoreException {
          return  getString(CONFIGURACION_DESCRIPCION);
     }

     /**
      * Retrieve the value of the configuracion.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionDescripcion(int row) throws DataStoreException {
          return  getString(row,CONFIGURACION_DESCRIPCION);
     }

     /**
      * Set the value of the configuracion.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionDescripcion(String newValue) throws DataStoreException {
          setString(CONFIGURACION_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the configuracion.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,CONFIGURACION_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the configuracion.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionObservaciones() throws DataStoreException {
          return  getString(CONFIGURACION_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the configuracion.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getConfiguracionObservaciones(int row) throws DataStoreException {
          return  getString(row,CONFIGURACION_OBSERVACIONES);
     }

     /**
      * Set the value of the configuracion.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionObservaciones(String newValue) throws DataStoreException {
          setString(CONFIGURACION_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the configuracion.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,CONFIGURACION_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the configuracion.cardinalidad column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionCardinalidad() throws DataStoreException {
          return  getInt(CONFIGURACION_CARDINALIDAD);
     }

     /**
      * Retrieve the value of the configuracion.cardinalidad column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionCardinalidad(int row) throws DataStoreException {
          return  getInt(row,CONFIGURACION_CARDINALIDAD);
     }

     /**
      * Set the value of the configuracion.cardinalidad column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionCardinalidad(int newValue) throws DataStoreException {
          setInt(CONFIGURACION_CARDINALIDAD, newValue);
     }

     /**
      * Set the value of the configuracion.cardinalidad column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionCardinalidad(int row,int newValue) throws DataStoreException {
          setInt(row,CONFIGURACION_CARDINALIDAD, newValue);
     }

     /**
      * Retrieve the value of the configuracion.prioridad column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionPrioridad() throws DataStoreException {
          return  getInt(CONFIGURACION_PRIORIDAD);
     }

     /**
      * Retrieve the value of the configuracion.prioridad column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getConfiguracionPrioridad(int row) throws DataStoreException {
          return  getInt(row,CONFIGURACION_PRIORIDAD);
     }

     /**
      * Set the value of the configuracion.prioridad column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionPrioridad(int newValue) throws DataStoreException {
          setInt(CONFIGURACION_PRIORIDAD, newValue);
     }

     /**
      * Set the value of the configuracion.prioridad column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setConfiguracionPrioridad(int row,int newValue) throws DataStoreException {
          setInt(row,CONFIGURACION_PRIORIDAD, newValue);
     }

     /**
      * Retrieve the value of the esquema_configuracion.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionNombre() throws DataStoreException {
          return  getString(ESQUEMA_CONFIGURACION_NOMBRE);
     }

     /**
      * Retrieve the value of the esquema_configuracion.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionNombre(int row) throws DataStoreException {
          return  getString(row,ESQUEMA_CONFIGURACION_NOMBRE);
     }

     /**
      * Set the value of the esquema_configuracion.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionNombre(String newValue) throws DataStoreException {
          setString(ESQUEMA_CONFIGURACION_NOMBRE, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESQUEMA_CONFIGURACION_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the esquema_configuracion.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionTipoObjeto() throws DataStoreException {
          return  getString(ESQUEMA_CONFIGURACION_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the esquema_configuracion.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionTipoObjeto(int row) throws DataStoreException {
          return  getString(row,ESQUEMA_CONFIGURACION_TIPO_OBJETO);
     }

     /**
      * Set the value of the esquema_configuracion.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionTipoObjeto(String newValue) throws DataStoreException {
          setString(ESQUEMA_CONFIGURACION_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ESQUEMA_CONFIGURACION_TIPO_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the esquema_configuracion.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionNombreObjeto() throws DataStoreException {
          return  getString(ESQUEMA_CONFIGURACION_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the esquema_configuracion.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionNombreObjeto(int row) throws DataStoreException {
          return  getString(row,ESQUEMA_CONFIGURACION_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the esquema_configuracion.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionNombreObjeto(String newValue) throws DataStoreException {
          setString(ESQUEMA_CONFIGURACION_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ESQUEMA_CONFIGURACION_NOMBRE_OBJETO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
    
		
     //$ENDCUSTOMMETHODS$
     
}
