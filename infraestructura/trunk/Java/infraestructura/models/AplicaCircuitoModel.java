package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AplicaCircuitoModel: A SOFIA generated model
 */
public class AplicaCircuitoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -6580514721549912697L;
	//constants for columns
     public static final String APLICA_CIRCUITO_TIPO_OBJETO="aplica_circuito.tipo_objeto";
     public static final String APLICA_CIRCUITO_NOMBRE_OBJETO="aplica_circuito.nombre_objeto";
     public static final String APLICA_CIRCUITO_TIPO_DETALLE="aplica_circuito.tipo_detalle";
     public static final String APLICA_CIRCUITO_NOMBRE_DETALLE="aplica_circuito.nombre_detalle";
     public static final String APLICA_CIRCUITO_CIRCUITO="aplica_circuito.circuito";
     public static final String CIRCUITOS_ESTADOS_CIRCUITO="circuitos_estados.circuito";
     public static final String CIRCUITOS_ESTADOS_NOMBRE="circuitos_estados.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new AplicaCircuitoModel object.
      * @param appName The SOFIA application name
      */
     public AplicaCircuitoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new AplicaCircuitoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public AplicaCircuitoModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("aplica_circuito"),"tipo_objeto",DataStore.DATATYPE_STRING,true,true,APLICA_CIRCUITO_TIPO_OBJETO);
          addColumn(computeTableName("aplica_circuito"),"nombre_objeto",DataStore.DATATYPE_STRING,true,true,APLICA_CIRCUITO_NOMBRE_OBJETO);
          addColumn(computeTableName("aplica_circuito"),"tipo_detalle",DataStore.DATATYPE_STRING,true,true,APLICA_CIRCUITO_TIPO_DETALLE);
          addColumn(computeTableName("aplica_circuito"),"nombre_detalle",DataStore.DATATYPE_STRING,true,true,APLICA_CIRCUITO_NOMBRE_DETALLE);
          addColumn(computeTableName("aplica_circuito"),"circuito",DataStore.DATATYPE_STRING,true,true,APLICA_CIRCUITO_CIRCUITO);
          addColumn(computeTableName("circuitos_estados"),"circuito",DataStore.DATATYPE_STRING,true,false,CIRCUITOS_ESTADOS_CIRCUITO);
          addColumn(computeTableName("circuitos_estados"),"nombre",DataStore.DATATYPE_STRING,false,false,CIRCUITOS_ESTADOS_NOMBRE);

          //add joins
          addJoin(computeTableAndFieldName("aplica_circuito.circuito"),computeTableAndFieldName("circuitos_estados.circuito"),false);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the aplica_circuito.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoTipoObjeto() throws DataStoreException {
          return  getString(APLICA_CIRCUITO_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the aplica_circuito.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoTipoObjeto(int row) throws DataStoreException {
          return  getString(row,APLICA_CIRCUITO_TIPO_OBJETO);
     }

     /**
      * Set the value of the aplica_circuito.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoTipoObjeto(String newValue) throws DataStoreException {
          setString(APLICA_CIRCUITO_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the aplica_circuito.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,APLICA_CIRCUITO_TIPO_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the aplica_circuito.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoNombreObjeto() throws DataStoreException {
          return  getString(APLICA_CIRCUITO_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the aplica_circuito.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoNombreObjeto(int row) throws DataStoreException {
          return  getString(row,APLICA_CIRCUITO_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the aplica_circuito.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoNombreObjeto(String newValue) throws DataStoreException {
          setString(APLICA_CIRCUITO_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the aplica_circuito.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,APLICA_CIRCUITO_NOMBRE_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the aplica_circuito.tipo_detalle column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoTipoDetalle() throws DataStoreException {
          return  getString(APLICA_CIRCUITO_TIPO_DETALLE);
     }

     /**
      * Retrieve the value of the aplica_circuito.tipo_detalle column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoTipoDetalle(int row) throws DataStoreException {
          return  getString(row,APLICA_CIRCUITO_TIPO_DETALLE);
     }

     /**
      * Set the value of the aplica_circuito.tipo_detalle column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoTipoDetalle(String newValue) throws DataStoreException {
          setString(APLICA_CIRCUITO_TIPO_DETALLE, newValue);
     }

     /**
      * Set the value of the aplica_circuito.tipo_detalle column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoTipoDetalle(int row,String newValue) throws DataStoreException {
          setString(row,APLICA_CIRCUITO_TIPO_DETALLE, newValue);
     }

     /**
      * Retrieve the value of the aplica_circuito.nombre_detalle column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoNombreDetalle() throws DataStoreException {
          return  getString(APLICA_CIRCUITO_NOMBRE_DETALLE);
     }

     /**
      * Retrieve the value of the aplica_circuito.nombre_detalle column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoNombreDetalle(int row) throws DataStoreException {
          return  getString(row,APLICA_CIRCUITO_NOMBRE_DETALLE);
     }

     /**
      * Set the value of the aplica_circuito.nombre_detalle column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoNombreDetalle(String newValue) throws DataStoreException {
          setString(APLICA_CIRCUITO_NOMBRE_DETALLE, newValue);
     }

     /**
      * Set the value of the aplica_circuito.nombre_detalle column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoNombreDetalle(int row,String newValue) throws DataStoreException {
          setString(row,APLICA_CIRCUITO_NOMBRE_DETALLE, newValue);
     }

     /**
      * Retrieve the value of the aplica_circuito.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoCircuito() throws DataStoreException {
          return  getString(APLICA_CIRCUITO_CIRCUITO);
     }

     /**
      * Retrieve the value of the aplica_circuito.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAplicaCircuitoCircuito(int row) throws DataStoreException {
          return  getString(row,APLICA_CIRCUITO_CIRCUITO);
     }

     /**
      * Set the value of the aplica_circuito.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoCircuito(String newValue) throws DataStoreException {
          setString(APLICA_CIRCUITO_CIRCUITO, newValue);
     }

     /**
      * Set the value of the aplica_circuito.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAplicaCircuitoCircuito(int row,String newValue) throws DataStoreException {
          setString(row,APLICA_CIRCUITO_CIRCUITO, newValue);
     }

     /**
      * Retrieve the value of the circuitos_estados.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosCircuito() throws DataStoreException {
          return  getString(CIRCUITOS_ESTADOS_CIRCUITO);
     }

     /**
      * Retrieve the value of the circuitos_estados.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosCircuito(int row) throws DataStoreException {
          return  getString(row,CIRCUITOS_ESTADOS_CIRCUITO);
     }

     /**
      * Set the value of the circuitos_estados.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosCircuito(String newValue) throws DataStoreException {
          setString(CIRCUITOS_ESTADOS_CIRCUITO, newValue);
     }

     /**
      * Set the value of the circuitos_estados.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosCircuito(int row,String newValue) throws DataStoreException {
          setString(row,CIRCUITOS_ESTADOS_CIRCUITO, newValue);
     }

     /**
      * Retrieve the value of the circuitos_estados.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosNombre() throws DataStoreException {
          return  getString(CIRCUITOS_ESTADOS_NOMBRE);
     }

     /**
      * Retrieve the value of the circuitos_estados.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosNombre(int row) throws DataStoreException {
          return  getString(row,CIRCUITOS_ESTADOS_NOMBRE);
     }

     /**
      * Set the value of the circuitos_estados.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosNombre(String newValue) throws DataStoreException {
          setString(CIRCUITOS_ESTADOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the circuitos_estados.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosNombre(int row,String newValue) throws DataStoreException {
          setString(row,CIRCUITOS_ESTADOS_NOMBRE, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
