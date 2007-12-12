package infraestructura.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * EstadoModel: A SOFIA generated model
 */
public class EstadoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 3906239415213983438L;
	//constants for columns
     public static final String ESTADOS_ESTADO="estados.estado";
     public static final String ESTADOS_NOMBRE="estados.nombre";
     public static final String ESTADOS_DESCRIPCION="estados.descripcion";
     public static final String ESTADOS_OBSERVACIONES="estados.observaciones";
     public static final String ESTADOS_CIRCUITO="estados.circuito";
     public static final String CIRCUITOS_ESTADOS_NOMBRE="circuitos_estados.nombre";
     public static final String CIRCUITOS_ESTADOS_DESCRIPCION="circuitos_estados.descripcion";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new EstadoModel object.
      * @param appName The SOFIA application name
      */
     public EstadoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new EstadoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public EstadoModel (String appName, String profile) { 
          super(appName, profile);

          try {
              //add aliases
              addTableAlias(computeTableName("estados"),"estados");
              addTableAlias(computeTableName("circuitos_estados"),"circuitos_estados");

              //add columns
              addColumn(computeTableName("estados"),"estado",DataStore.DATATYPE_STRING,true,true,ESTADOS_ESTADO);
              addColumn(computeTableName("estados"),"nombre",DataStore.DATATYPE_STRING,false,true,ESTADOS_NOMBRE);
              addColumn(computeTableName("estados"),"descripcion",DataStore.DATATYPE_STRING,false,true,ESTADOS_DESCRIPCION);
              addColumn(computeTableName("estados"),"observaciones",DataStore.DATATYPE_STRING,false,true,ESTADOS_OBSERVACIONES);
              addColumn(computeTableName("estados"),"circuito",DataStore.DATATYPE_STRING,false,true,ESTADOS_CIRCUITO);
              addColumn(computeTableName("circuitos_estados"),"nombre",DataStore.DATATYPE_STRING,false,false,CIRCUITOS_ESTADOS_NOMBRE);
              addColumn(computeTableName("circuitos_estados"),"descripcion",DataStore.DATATYPE_STRING,false,false,CIRCUITOS_ESTADOS_DESCRIPCION);

              //add joins
              addJoin(computeTableAndFieldName("estados.circuito"),computeTableAndFieldName("circuitos_estados.circuito"),false);

              //set order by
              setOrderBy(computeTableAndFieldName("estados.estado") + " ASC," + computeTableAndFieldName("estados.nombre") + " DESC");

              // add validatios
              addRequiredRule(ESTADOS_ESTADO,"El código de estado es obligatorio");
              addRequiredRule(ESTADOS_CIRCUITO,"El circuito al que pertenece el estado es obligatorio");
              addRequiredRule(ESTADOS_NOMBRE,"El nombre es obligatorio");
              addExpressionRule(ESTADOS_ESTADO, new ConvierteMayusculasValidation(ESTADOS_ESTADO), "",false);
              addExpressionRule(ESTADOS_CIRCUITO, new ConvierteMayusculasValidation(ESTADOS_CIRCUITO), "",false);
              addExpressionRule(ESTADOS_NOMBRE, new ConvierteMayusculasValidation(ESTADOS_NOMBRE), "",false);
		} catch (DataStoreException e) {
            com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}
          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the estados.estado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosEstado() throws DataStoreException {
          return  getString(ESTADOS_ESTADO);
     }

     /**
      * Retrieve the value of the estados.estado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosEstado(int row) throws DataStoreException {
          return  getString(row,ESTADOS_ESTADO);
     }

     /**
      * Set the value of the estados.estado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosEstado(String newValue) throws DataStoreException {
          setString(ESTADOS_ESTADO, newValue);
     }

     /**
      * Set the value of the estados.estado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosEstado(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_ESTADO, newValue);
     }

     /**
      * Retrieve the value of the estados.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre() throws DataStoreException {
          return  getString(ESTADOS_NOMBRE);
     }

     /**
      * Retrieve the value of the estados.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre(int row) throws DataStoreException {
          return  getString(row,ESTADOS_NOMBRE);
     }

     /**
      * Set the value of the estados.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(String newValue) throws DataStoreException {
          setString(ESTADOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the estados.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the estados.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDescripcion() throws DataStoreException {
          return  getString(ESTADOS_DESCRIPCION);
     }

     /**
      * Retrieve the value of the estados.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosDescripcion(int row) throws DataStoreException {
          return  getString(row,ESTADOS_DESCRIPCION);
     }

     /**
      * Set the value of the estados.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDescripcion(String newValue) throws DataStoreException {
          setString(ESTADOS_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the estados.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the estados.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosObservaciones() throws DataStoreException {
          return  getString(ESTADOS_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the estados.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosObservaciones(int row) throws DataStoreException {
          return  getString(row,ESTADOS_OBSERVACIONES);
     }

     /**
      * Set the value of the estados.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosObservaciones(String newValue) throws DataStoreException {
          setString(ESTADOS_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the estados.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the estados.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosCircuito() throws DataStoreException {
          return  getString(ESTADOS_CIRCUITO);
     }

     /**
      * Retrieve the value of the estados.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosCircuito(int row) throws DataStoreException {
          return  getString(row,ESTADOS_CIRCUITO);
     }

     /**
      * Set the value of the estados.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosCircuito(String newValue) throws DataStoreException {
          setString(ESTADOS_CIRCUITO, newValue);
     }

     /**
      * Set the value of the estados.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosCircuito(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_CIRCUITO, newValue);
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

     /**
      * Retrieve the value of the circuitos_estados.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosDescripcion() throws DataStoreException {
          return  getString(CIRCUITOS_ESTADOS_DESCRIPCION);
     }

     /**
      * Retrieve the value of the circuitos_estados.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosDescripcion(int row) throws DataStoreException {
          return  getString(row,CIRCUITOS_ESTADOS_DESCRIPCION);
     }

     /**
      * Set the value of the circuitos_estados.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosDescripcion(String newValue) throws DataStoreException {
          setString(CIRCUITOS_ESTADOS_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the circuitos_estados.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,CIRCUITOS_ESTADOS_DESCRIPCION, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
