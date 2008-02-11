package partesMO.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * LogValidacionPartesMoModel: A SOFIA generated model
 */
public class LogValidacionPartesMoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -6434370914968159961L;
	//constants for columns
     public static final String LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID="log_validacion_partes_mo.log_valida_id";
     public static final String LOG_VALIDACION_PARTES_MO_FECHA="log_validacion_partes_mo.fecha";
     public static final String LOG_VALIDACION_PARTES_MO_ESTADO="log_validacion_partes_mo.estado";
     public static final String LOG_VALIDACION_PARTES_MO_VALIDACION_ID="log_validacion_partes_mo.validacion_id";
     public static final String LOG_VALIDACION_PARTES_MO_PARTE_ID="log_validacion_partes_mo.parte_id";
     public static final String VALIDACIONES_PARTES_MO_NOMBRE="validaciones_partes_mo.nombre";
     public static final String VALIDACIONES_PARTES_MO_VALIDADOR="validaciones_partes_mo.validador";
     public static final String VALIDACIONES_PARTES_MO_TIPO="validaciones_partes_mo.tipo";
     public static final String ESTADOS_NOMBRE="estados.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new LogValidacionPartesMoModel object.
      * @param appName The SOFIA application name
      */
     public LogValidacionPartesMoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new LogValidacionPartesMoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public LogValidacionPartesMoModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("log_validacion_partes_mo"),"log_validacion_partes_mo");
          addTableAlias(computeTableName("infraestructura.estados"),"estados");
          addTableAlias(computeTableName("validaciones_partes_mo"),null);

          //add columns
          addColumn(computeTableName("log_validacion_partes_mo"),"log_valida_id",DataStore.DATATYPE_INT,true,true,LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID);
          addColumn(computeTableName("log_validacion_partes_mo"),"fecha",DataStore.DATATYPE_DATETIME,false,true,LOG_VALIDACION_PARTES_MO_FECHA);
          addColumn(computeTableName("log_validacion_partes_mo"),"estado",DataStore.DATATYPE_STRING,false,true,LOG_VALIDACION_PARTES_MO_ESTADO);
          addColumn(computeTableName("log_validacion_partes_mo"),"validacion_id",DataStore.DATATYPE_INT,false,true,LOG_VALIDACION_PARTES_MO_VALIDACION_ID);
          addColumn(computeTableName("log_validacion_partes_mo"),"parte_id",DataStore.DATATYPE_INT,false,true,LOG_VALIDACION_PARTES_MO_PARTE_ID);
          addColumn(computeTableName("validaciones_partes_mo"),"nombre",DataStore.DATATYPE_STRING,false,false,VALIDACIONES_PARTES_MO_NOMBRE);
          addColumn(computeTableName("validaciones_partes_mo"),"validador",DataStore.DATATYPE_STRING,false,false,VALIDACIONES_PARTES_MO_VALIDADOR);
          addColumn(computeTableName("validaciones_partes_mo"),"tipo",DataStore.DATATYPE_STRING,false,false,VALIDACIONES_PARTES_MO_TIPO);
          addColumn(computeTableName("infraestructura.estados"),"nombre",DataStore.DATATYPE_STRING,false,false,ESTADOS_NOMBRE);

          //add joins
          addJoin(computeTableAndFieldName("log_validacion_partes_mo.validacion_id"),computeTableAndFieldName("validaciones_partes_mo.validacion_id"),false);
          addJoin(computeTableAndFieldName("log_validacion_partes_mo.estado"),computeTableAndFieldName("estados.estado"),false);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.log_valida_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoLogValidaId() throws DataStoreException {
          return  getInt(LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.log_valida_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoLogValidaId(int row) throws DataStoreException {
          return  getInt(row,LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID);
     }

     /**
      * Set the value of the log_validacion_partes_mo.log_valida_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoLogValidaId(int newValue) throws DataStoreException {
          setInt(LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID, newValue);
     }

     /**
      * Set the value of the log_validacion_partes_mo.log_valida_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoLogValidaId(int row,int newValue) throws DataStoreException {
          setInt(row,LOG_VALIDACION_PARTES_MO_LOG_VALIDA_ID, newValue);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.fecha column for the current row.
      * @return java.sql.Timestamp
      * @throws DataStoreException
      */ 
     public java.sql.Timestamp getLogValidacionPartesMoFecha() throws DataStoreException {
          return  getDateTime(LOG_VALIDACION_PARTES_MO_FECHA);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.fecha column for the specified row.
      * @param row which row in the table
      * @return java.sql.Timestamp
      * @throws DataStoreException
      */ 
     public java.sql.Timestamp getLogValidacionPartesMoFecha(int row) throws DataStoreException {
          return  getDateTime(row,LOG_VALIDACION_PARTES_MO_FECHA);
     }

     /**
      * Set the value of the log_validacion_partes_mo.fecha column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoFecha(java.sql.Timestamp newValue) throws DataStoreException {
          setDateTime(LOG_VALIDACION_PARTES_MO_FECHA, newValue);
     }

     /**
      * Set the value of the log_validacion_partes_mo.fecha column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoFecha(int row,java.sql.Timestamp newValue) throws DataStoreException {
          setDateTime(row,LOG_VALIDACION_PARTES_MO_FECHA, newValue);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.estado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLogValidacionPartesMoEstado() throws DataStoreException {
          return  getString(LOG_VALIDACION_PARTES_MO_ESTADO);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLogValidacionPartesMoEstado(int row) throws DataStoreException {
          return  getString(row,LOG_VALIDACION_PARTES_MO_ESTADO);
     }

     /**
      * Set the value of the log_validacion_partes_mo.estado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoEstado(String newValue) throws DataStoreException {
          setString(LOG_VALIDACION_PARTES_MO_ESTADO, newValue);
     }

     /**
      * Set the value of the log_validacion_partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoEstado(int row,String newValue) throws DataStoreException {
          setString(row,LOG_VALIDACION_PARTES_MO_ESTADO, newValue);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.validacion_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoValidacionId() throws DataStoreException {
          return  getInt(LOG_VALIDACION_PARTES_MO_VALIDACION_ID);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.validacion_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoValidacionId(int row) throws DataStoreException {
          return  getInt(row,LOG_VALIDACION_PARTES_MO_VALIDACION_ID);
     }

     /**
      * Set the value of the log_validacion_partes_mo.validacion_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoValidacionId(int newValue) throws DataStoreException {
          setInt(LOG_VALIDACION_PARTES_MO_VALIDACION_ID, newValue);
     }

     /**
      * Set the value of the log_validacion_partes_mo.validacion_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoValidacionId(int row,int newValue) throws DataStoreException {
          setInt(row,LOG_VALIDACION_PARTES_MO_VALIDACION_ID, newValue);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.parte_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoParteId() throws DataStoreException {
          return  getInt(LOG_VALIDACION_PARTES_MO_PARTE_ID);
     }

     /**
      * Retrieve the value of the log_validacion_partes_mo.parte_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLogValidacionPartesMoParteId(int row) throws DataStoreException {
          return  getInt(row,LOG_VALIDACION_PARTES_MO_PARTE_ID);
     }

     /**
      * Set the value of the log_validacion_partes_mo.parte_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoParteId(int newValue) throws DataStoreException {
          setInt(LOG_VALIDACION_PARTES_MO_PARTE_ID, newValue);
     }

     /**
      * Set the value of the log_validacion_partes_mo.parte_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLogValidacionPartesMoParteId(int row,int newValue) throws DataStoreException {
          setInt(row,LOG_VALIDACION_PARTES_MO_PARTE_ID, newValue);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoNombre() throws DataStoreException {
          return  getString(VALIDACIONES_PARTES_MO_NOMBRE);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoNombre(int row) throws DataStoreException {
          return  getString(row,VALIDACIONES_PARTES_MO_NOMBRE);
     }

     /**
      * Set the value of the validaciones_partes_mo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoNombre(String newValue) throws DataStoreException {
          setString(VALIDACIONES_PARTES_MO_NOMBRE, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoNombre(int row,String newValue) throws DataStoreException {
          setString(row,VALIDACIONES_PARTES_MO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.validador column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoValidador() throws DataStoreException {
          return  getString(VALIDACIONES_PARTES_MO_VALIDADOR);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.validador column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoValidador(int row) throws DataStoreException {
          return  getString(row,VALIDACIONES_PARTES_MO_VALIDADOR);
     }

     /**
      * Set the value of the validaciones_partes_mo.validador column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoValidador(String newValue) throws DataStoreException {
          setString(VALIDACIONES_PARTES_MO_VALIDADOR, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.validador column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoValidador(int row,String newValue) throws DataStoreException {
          setString(row,VALIDACIONES_PARTES_MO_VALIDADOR, newValue);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.tipo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoTipo() throws DataStoreException {
          return  getString(VALIDACIONES_PARTES_MO_TIPO);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.tipo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoTipo(int row) throws DataStoreException {
          return  getString(row,VALIDACIONES_PARTES_MO_TIPO);
     }

     /**
      * Set the value of the validaciones_partes_mo.tipo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoTipo(String newValue) throws DataStoreException {
          setString(VALIDACIONES_PARTES_MO_TIPO, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.tipo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoTipo(int row,String newValue) throws DataStoreException {
          setString(row,VALIDACIONES_PARTES_MO_TIPO, newValue);
     }
     
     /**
      * Retrieve the value of the estadosnombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre() throws DataStoreException {
          return  getString(ESTADOS_NOMBRE);
     }

     /**
      * Retrieve the value of the estadosnombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre(int row) throws DataStoreException {
          return  getString(row,ESTADOS_NOMBRE);
     }

     /**
      * Set the value of the estadosnombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(String newValue) throws DataStoreException {
          setString(ESTADOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the estadosnombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_NOMBRE, newValue);
     }

     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
