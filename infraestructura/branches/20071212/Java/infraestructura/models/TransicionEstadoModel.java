package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * TransicionEstadoModel: A SOFIA generated model
 */
public class TransicionEstadoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 7845812326516831896L;
	//constants for columns
     public static final String TRANSICION_ESTADOS_ESTADO_ORIGEN="transicion_estados.estado_origen";
     public static final String TRANSICION_ESTADOS_ACCION="transicion_estados.accion";
     public static final String TRANSICION_ESTADOS_ESTADO_DESTINO="transicion_estados.estado_destino";
     public static final String TRANSICION_ESTADOS_PROMPT_ACCION="transicion_estados.prompt_accion";
     public static final String TRANSICION_ESTADOS_VALIDADOR="transicion_estados.validador";
     public static final String ESTADOS_ORIGEN_NOMBRE="estados_origen.nombre";
     public static final String ESTADOS_ORIGEN_CIRCUITO="estados_origen.circuito";
     public static final String ESTADOS_DESTINO_NOMBRE="estados_destino.nombre";
     public static final String ESTADOS_DESTINO_CIRCUITO="estados_destino.circuito";
     public static final String ACCIONES_APLICACION_NOMBRE="acciones_apps.nombre";
     public static final String ACCIONES_APLICACION_CIRCUITO="acciones_apps.circuito";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new TransicionEstadoModel object.
      * @param appName The SOFIA application name
      */
     public TransicionEstadoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new TransicionEstadoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public TransicionEstadoModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("transicion_estados"),null);
          addTableAlias(computeTableName("estados"),"estados_origen");
          addTableAlias(computeTableName("estados"),"estados_destino");
          addTableAlias(computeTableName("acciones_apps"),null);

          //add columns
          addColumn(computeTableName("transicion_estados"),"estado_origen",DataStore.DATATYPE_STRING,true,true,TRANSICION_ESTADOS_ESTADO_ORIGEN);
          addColumn(computeTableName("transicion_estados"),"accion",DataStore.DATATYPE_INT,true,true,TRANSICION_ESTADOS_ACCION);
          addColumn(computeTableName("transicion_estados"),"estado_destino",DataStore.DATATYPE_STRING,false,true,TRANSICION_ESTADOS_ESTADO_DESTINO);
          addColumn(computeTableName("transicion_estados"),"prompt_accion",DataStore.DATATYPE_STRING,false,true,TRANSICION_ESTADOS_PROMPT_ACCION);
          addColumn(computeTableName("transicion_estados"),"validador",DataStore.DATATYPE_STRING,false,true,TRANSICION_ESTADOS_VALIDADOR);
          addColumn(computeTableName("estados_origen"),"nombre",DataStore.DATATYPE_STRING,false,false,ESTADOS_ORIGEN_NOMBRE);
          addColumn(computeTableName("estados_origen"),"circuito",DataStore.DATATYPE_STRING,false,false,ESTADOS_ORIGEN_CIRCUITO);
          addColumn(computeTableName("estados_destino"),"nombre",DataStore.DATATYPE_STRING,false,false,ESTADOS_DESTINO_NOMBRE);
          addColumn(computeTableName("estados_destino"),"circuito",DataStore.DATATYPE_STRING,false,false,ESTADOS_DESTINO_CIRCUITO);
          addColumn(computeTableName("acciones_apps"),"nombre",DataStore.DATATYPE_STRING,false,false,ACCIONES_APLICACION_NOMBRE);
          addColumn(computeTableName("acciones_apps"),"circuito",DataStore.DATATYPE_STRING,false,false,ACCIONES_APLICACION_CIRCUITO);

          //add joins
          addJoin(computeTableAndFieldName("transicion_estados.estado_origen"),computeTableAndFieldName("estados_origen.estado"),false);
          addJoin(computeTableAndFieldName("transicion_estados.estado_destino"),computeTableAndFieldName("estados_destino.estado"),false);
          addJoin(computeTableAndFieldName("transicion_estados.accion"),computeTableAndFieldName("acciones_apps.accion"),false);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the transicion_estados.estado_origen column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosEstadoOrigen() throws DataStoreException {
          return  getString(TRANSICION_ESTADOS_ESTADO_ORIGEN);
     }

     /**
      * Retrieve the value of the transicion_estados.estado_origen column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosEstadoOrigen(int row) throws DataStoreException {
          return  getString(row,TRANSICION_ESTADOS_ESTADO_ORIGEN);
     }

     /**
      * Set the value of the transicion_estados.estado_origen column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosEstadoOrigen(String newValue) throws DataStoreException {
          setString(TRANSICION_ESTADOS_ESTADO_ORIGEN, newValue);
     }

     /**
      * Set the value of the transicion_estados.estado_origen column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosEstadoOrigen(int row,String newValue) throws DataStoreException {
          setString(row,TRANSICION_ESTADOS_ESTADO_ORIGEN, newValue);
     }

     /**
      * Retrieve the value of the transicion_estados.accion column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getTransicionEstadosAccion() throws DataStoreException {
          return  getInt(TRANSICION_ESTADOS_ACCION);
     }

     /**
      * Retrieve the value of the transicion_estados.accion column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getTransicionEstadosAccion(int row) throws DataStoreException {
          return  getInt(row,TRANSICION_ESTADOS_ACCION);
     }

     /**
      * Set the value of the transicion_estados.accion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosAccion(int newValue) throws DataStoreException {
          setInt(TRANSICION_ESTADOS_ACCION, newValue);
     }

     /**
      * Set the value of the transicion_estados.accion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosAccion(int row,int newValue) throws DataStoreException {
          setInt(row,TRANSICION_ESTADOS_ACCION, newValue);
     }

     /**
      * Retrieve the value of the transicion_estados.estado_destino column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosEstadoDestino() throws DataStoreException {
          return  getString(TRANSICION_ESTADOS_ESTADO_DESTINO);
     }

     /**
      * Retrieve the value of the transicion_estados.estado_destino column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosEstadoDestino(int row) throws DataStoreException {
          return  getString(row,TRANSICION_ESTADOS_ESTADO_DESTINO);
     }

     /**
      * Set the value of the transicion_estados.estado_destino column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosEstadoDestino(String newValue) throws DataStoreException {
          setString(TRANSICION_ESTADOS_ESTADO_DESTINO, newValue);
     }

     /**
      * Set the value of the transicion_estados.estado_destino column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosEstadoDestino(int row,String newValue) throws DataStoreException {
          setString(row,TRANSICION_ESTADOS_ESTADO_DESTINO, newValue);
     }

     /**
      * Retrieve the value of the transicion_estados.prompt_accion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosPromptAccion() throws DataStoreException {
          return  getString(TRANSICION_ESTADOS_PROMPT_ACCION);
     }

     /**
      * Retrieve the value of the transicion_estados.prompt_accion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosPromptAccion(int row) throws DataStoreException {
          return  getString(row,TRANSICION_ESTADOS_PROMPT_ACCION);
     }

     /**
      * Set the value of the transicion_estados.prompt_accion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosPromptAccion(String newValue) throws DataStoreException {
          setString(TRANSICION_ESTADOS_PROMPT_ACCION, newValue);
     }

     /**
      * Set the value of the transicion_estados.prompt_accion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosPromptAccion(int row,String newValue) throws DataStoreException {
          setString(row,TRANSICION_ESTADOS_PROMPT_ACCION, newValue);
     }

     /**
      * Retrieve the value of the transicion_estados.Validador column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosValidador() throws DataStoreException {
          return  getString(TRANSICION_ESTADOS_VALIDADOR);
     }

     /**
      * Retrieve the value of the transicion_estados.Validador column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTransicionEstadosValidador(int row) throws DataStoreException {
          return  getString(row,TRANSICION_ESTADOS_VALIDADOR);
     }

     /**
      * Set the value of the transicion_estados.Validador column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setsetTransicionEstadosValidador(String newValue) throws DataStoreException {
          setString(TRANSICION_ESTADOS_VALIDADOR, newValue);
     }

     /**
      * Set the value of the transicion_estados.Validador column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTransicionEstadosValidador(int row,String newValue) throws DataStoreException {
          setString(row,TRANSICION_ESTADOS_VALIDADOR, newValue);
     }

    /**
      * Retrieve the value of the estados_origen.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosOrigenNombre() throws DataStoreException {
          return  getString(ESTADOS_ORIGEN_NOMBRE);
     }

     /**
      * Retrieve the value of the estados_origen.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosOrigenNombre(int row) throws DataStoreException {
          return  getString(row,ESTADOS_ORIGEN_NOMBRE);
     }

     /**
      * Set the value of the estados_origen.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosOrigenNombre(String newValue) throws DataStoreException {
          setString(ESTADOS_ORIGEN_NOMBRE, newValue);
     }

     /**
      * Set the value of the estados_origen.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosOrigenNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_ORIGEN_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the estados_origen.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosOrigenCircuito() throws DataStoreException {
          return  getString(ESTADOS_ORIGEN_CIRCUITO);
     }

     /**
      * Retrieve the value of the estados_origen.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosOrigenCircuito(int row) throws DataStoreException {
          return  getString(row,ESTADOS_ORIGEN_CIRCUITO);
     }

     /**
      * Set the value of the estados_origen.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosOrigenCircuito(String newValue) throws DataStoreException {
          setString(ESTADOS_ORIGEN_CIRCUITO, newValue);
     }

     /**
      * Set the value of the estados_origen.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosOrigenCircuito(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_ORIGEN_CIRCUITO, newValue);
     }

     /**
      * Retrieve the value of the estados_destino.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDestinoNombre() throws DataStoreException {
          return  getString(ESTADOS_DESTINO_NOMBRE);
     }

     /**
      * Retrieve the value of the estados_destino.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDestinoNombre(int row) throws DataStoreException {
          return  getString(row,ESTADOS_DESTINO_NOMBRE);
     }

     /**
      * Set the value of the estados_destino.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDestinoNombre(String newValue) throws DataStoreException {
          setString(ESTADOS_DESTINO_NOMBRE, newValue);
     }

     /**
      * Set the value of the estados_destino.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDestinoNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_DESTINO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the estados_destino.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDestinoCircuito() throws DataStoreException {
          return  getString(ESTADOS_DESTINO_CIRCUITO);
     }

     /**
      * Retrieve the value of the estados_destino.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDestinoCircuito(int row) throws DataStoreException {
          return  getString(row,ESTADOS_DESTINO_CIRCUITO);
     }

     /**
      * Set the value of the estados_destino.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDestinoCircuito(String newValue) throws DataStoreException {
          setString(ESTADOS_DESTINO_CIRCUITO, newValue);
     }

     /**
      * Set the value of the estados_destino.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDestinoCircuito(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_DESTINO_CIRCUITO, newValue);
     }

     /**
      * Retrieve the value of the acciones_aplicacion.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionNombre() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_NOMBRE);
     }

     /**
      * Retrieve the value of the acciones_aplicacion.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionNombre(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_NOMBRE);
     }

     /**
      * Set the value of the acciones_aplicacion.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionNombre(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_NOMBRE, newValue);
     }

     /**
      * Set the value of the acciones_aplicacion.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionNombre(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the acciones_aplicacion.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionCircuito() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_CIRCUITO);
     }

     /**
      * Retrieve the value of the acciones_aplicacion.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionCircuito(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_CIRCUITO);
     }

     /**
      * Set the value of the acciones_aplicacion.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionCircuito(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_CIRCUITO, newValue);
     }

     /**
      * Set the value of the acciones_aplicacion.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionCircuito(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_CIRCUITO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
