package infraestructura.models;

import infraestructura.utils.ConvierteMayusculasValidation;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * EsquemaConfiguracionModel: A SOFIA generated model
 */
public class EsquemaConfiguracionModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 6172569081805599568L;
	//constants for columns
     public static final String ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID="esquema_configuracion.esquema_configuracion_id";
     public static final String ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";
     public static final String ESQUEMA_CONFIGURACION_DESCRIPCION="esquema_configuracion.descripcion";
     public static final String ESQUEMA_CONFIGURACION_OBSERVACIONES="esquema_configuracion.observaciones";
     public static final String ESQUEMA_CONFIGURACION_TIPO_OBJETO="esquema_configuracion.tipo_objeto";
     public static final String ESQUEMA_CONFIGURACION_NOMBRE_OBJETO="esquema_configuracion.nombre_objeto";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new EsquemaConfiguracionModel object.
      * @param appName The SOFIA application name
      */
     public EsquemaConfiguracionModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new EsquemaConfiguracionModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public EsquemaConfiguracionModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("esquema_configuracion"),null);

               //add columns
               addColumn(computeTableName("esquema_configuracion"),"esquema_configuracion_id",DataStore.DATATYPE_INT,true,true,ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
               addColumn(computeTableName("esquema_configuracion"),"nombre",DataStore.DATATYPE_STRING,false,true,ESQUEMA_CONFIGURACION_NOMBRE);
               addColumn(computeTableName("esquema_configuracion"),"descripcion",DataStore.DATATYPE_STRING,false,true,ESQUEMA_CONFIGURACION_DESCRIPCION);
               addColumn(computeTableName("esquema_configuracion"),"observaciones",DataStore.DATATYPE_STRING,false,true,ESQUEMA_CONFIGURACION_OBSERVACIONES);
               addColumn(computeTableName("esquema_configuracion"),"tipo_objeto",DataStore.DATATYPE_STRING,false,true,ESQUEMA_CONFIGURACION_TIPO_OBJETO);
               addColumn(computeTableName("esquema_configuracion"),"nombre_objeto",DataStore.DATATYPE_STRING,false,true,ESQUEMA_CONFIGURACION_NOMBRE_OBJETO);

               //set auto increment
               setAutoIncrement(ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID, true);
               setUpdateable(ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID, false);

               //add validations
               addRequiredRule(ESQUEMA_CONFIGURACION_NOMBRE,"El nombre del esquema es obligatorio");
               addRequiredRule(ESQUEMA_CONFIGURACION_TIPO_OBJETO,"El tipo de objeto es obligatorio");
               addRequiredRule(ESQUEMA_CONFIGURACION_NOMBRE_OBJETO,"EL nombre del objeto es obligatorio");
               addLookupRule(ESQUEMA_CONFIGURACION_NOMBRE_OBJETO,"diccionario_aplicacion","'tipo_objeto = \"TABLA\" AND nombre_objeto = \"' + esquema_configuracion.nombre_objeto + '\"'",null,null,"Objeto de Aplicación inexistente");
               addExpressionRule(ESQUEMA_CONFIGURACION_NOMBRE,new ConvierteMayusculasValidation(ESQUEMA_CONFIGURACION_NOMBRE),"",false);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the esquema_configuracion.esquema_configuracion_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getEsquemaConfiguracionEsquemaConfiguracionId() throws DataStoreException {
          return  getInt(ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
     }

     /**
      * Retrieve the value of the esquema_configuracion.esquema_configuracion_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getEsquemaConfiguracionEsquemaConfiguracionId(int row) throws DataStoreException {
          return  getInt(row,ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID);
     }

     /**
      * Set the value of the esquema_configuracion.esquema_configuracion_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionEsquemaConfiguracionId(int newValue) throws DataStoreException {
          setInt(ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.esquema_configuracion_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionEsquemaConfiguracionId(int row,int newValue) throws DataStoreException {
          setInt(row,ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID, newValue);
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
      * Retrieve the value of the esquema_configuracion.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionDescripcion() throws DataStoreException {
          return  getString(ESQUEMA_CONFIGURACION_DESCRIPCION);
     }

     /**
      * Retrieve the value of the esquema_configuracion.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionDescripcion(int row) throws DataStoreException {
          return  getString(row,ESQUEMA_CONFIGURACION_DESCRIPCION);
     }

     /**
      * Set the value of the esquema_configuracion.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionDescripcion(String newValue) throws DataStoreException {
          setString(ESQUEMA_CONFIGURACION_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ESQUEMA_CONFIGURACION_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the esquema_configuracion.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionObservaciones() throws DataStoreException {
          return  getString(ESQUEMA_CONFIGURACION_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the esquema_configuracion.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEsquemaConfiguracionObservaciones(int row) throws DataStoreException {
          return  getString(row,ESQUEMA_CONFIGURACION_OBSERVACIONES);
     }

     /**
      * Set the value of the esquema_configuracion.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionObservaciones(String newValue) throws DataStoreException {
          setString(ESQUEMA_CONFIGURACION_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the esquema_configuracion.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEsquemaConfiguracionObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ESQUEMA_CONFIGURACION_OBSERVACIONES, newValue);
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
 	@Override
	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, SQLException {
		// autocompleta columnas en el data store
 		
 		for (int i = 0; i < getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
 			if (getRowStatus(i) == STATUS_NEW_MODIFIED || getRowStatus(i) == STATUS_MODIFIED) {
 				// el registro está marcado para actualizar. veriofico la condición de las columnas autogeneradas
 				
 				// tipo objeto va con TABLA
 				String v_tipo_objeto = getEsquemaConfiguracionTipoObjeto(i);
 				if (v_tipo_objeto == null)
 					// setea el dato con el valor Tabla
 					setEsquemaConfiguracionTipoObjeto(i, "TABLA");
 			}
 		}
 				
		super.update(conn, handleTrans);
	}
     
     
     
     //$ENDCUSTOMMETHODS$
     
}
