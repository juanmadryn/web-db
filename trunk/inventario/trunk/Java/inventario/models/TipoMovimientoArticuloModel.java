package inventario.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * TipoMovimientoArticuloModel: A SOFIA generated model
 */
public class TipoMovimientoArticuloModel extends DataStore {

     //constants for columns
     public static final String TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID="tipo_movimiento_articulo.tipo_movimiento_articulo_id";
     public static final String TIPO_MOVIMIENTO_ARTICULO_NOMBRE="tipo_movimiento_articulo.nombre";
     public static final String TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION="tipo_movimiento_articulo.descripcion";
     public static final String TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES="tipo_movimiento_articulo.observaciones";
     public static final String TIPO_MOVIMIENTO_ARTICULO_POSITIVO="tipo_movimiento_articulo.positivo";
     public static final String TIPO_MOVIMIENTO_ARTICULO_RESERVA="tipo_movimiento_articulo.reserva";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new TipoMovimientoArticuloModel object.
      * @param appName The SOFIA application name
      */
     public TipoMovimientoArticuloModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new TipoMovimientoArticuloModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public TipoMovimientoArticuloModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add columns
               addColumn(computeTableName("tipo_movimiento_articulo"),"tipo_movimiento_articulo_id",DataStore.DATATYPE_INT,true,true,TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
               addColumn(computeTableName("tipo_movimiento_articulo"),"nombre",DataStore.DATATYPE_STRING,false,true,TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
               addColumn(computeTableName("tipo_movimiento_articulo"),"descripcion",DataStore.DATATYPE_STRING,false,true,TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION);
               addColumn(computeTableName("tipo_movimiento_articulo"),"observaciones",DataStore.DATATYPE_STRING,false,true,TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES);
               addColumn(computeTableName("tipo_movimiento_articulo"),"positivo",DataStore.DATATYPE_STRING,false,true,TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
               addColumn(computeTableName("tipo_movimiento_articulo"),"reserva",DataStore.DATATYPE_STRING,false,true,TIPO_MOVIMIENTO_ARTICULO_RESERVA);

               //set order by
               setOrderBy(computeTableAndFieldName("tipo_movimiento_articulo.nombre") + " ASC");

               //add validations
               addRequiredRule(TIPO_MOVIMIENTO_ARTICULO_NOMBRE,"El nombre del tipo de movimiento es obligatorio");
               addRequiredRule(TIPO_MOVIMIENTO_ARTICULO_POSITIVO,"Debe indicar si el tipo de movimiento es positivo o no");
               addRequiredRule(TIPO_MOVIMIENTO_ARTICULO_RESERVA,"Debe indicar si el tipo de movimiento genera reserva o no");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.tipo_movimiento_articulo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getTipoMovimientoArticuloTipoMovimientoArticuloId() throws DataStoreException {
          return  getInt(TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.tipo_movimiento_articulo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getTipoMovimientoArticuloTipoMovimientoArticuloId(int row) throws DataStoreException {
          return  getInt(row,TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.tipo_movimiento_articulo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloTipoMovimientoArticuloId(int newValue) throws DataStoreException {
          setInt(TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.tipo_movimiento_articulo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloTipoMovimientoArticuloId(int row,int newValue) throws DataStoreException {
          setInt(row,TIPO_MOVIMIENTO_ARTICULO_TIPO_MOVIMIENTO_ARTICULO_ID, newValue);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloNombre() throws DataStoreException {
          return  getString(TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloNombre(int row) throws DataStoreException {
          return  getString(row,TIPO_MOVIMIENTO_ARTICULO_NOMBRE);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloNombre(String newValue) throws DataStoreException {
          setString(TIPO_MOVIMIENTO_ARTICULO_NOMBRE, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloNombre(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_MOVIMIENTO_ARTICULO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloDescripcion() throws DataStoreException {
          return  getString(TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloDescripcion(int row) throws DataStoreException {
          return  getString(row,TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloDescripcion(String newValue) throws DataStoreException {
          setString(TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloObservaciones() throws DataStoreException {
          return  getString(TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloObservaciones(int row) throws DataStoreException {
          return  getString(row,TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloObservaciones(String newValue) throws DataStoreException {
          setString(TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_MOVIMIENTO_ARTICULO_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.positivo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloPositivo() throws DataStoreException {
          return  getString(TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.positivo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloPositivo(int row) throws DataStoreException {
          return  getString(row,TIPO_MOVIMIENTO_ARTICULO_POSITIVO);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.positivo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloPositivo(String newValue) throws DataStoreException {
          setString(TIPO_MOVIMIENTO_ARTICULO_POSITIVO, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.positivo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloPositivo(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_MOVIMIENTO_ARTICULO_POSITIVO, newValue);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.reserva column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloReserva() throws DataStoreException {
          return  getString(TIPO_MOVIMIENTO_ARTICULO_RESERVA);
     }

     /**
      * Retrieve the value of the tipo_movimiento_articulo.reserva column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getTipoMovimientoArticuloReserva(int row) throws DataStoreException {
          return  getString(row,TIPO_MOVIMIENTO_ARTICULO_RESERVA);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.reserva column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloReserva(String newValue) throws DataStoreException {
          setString(TIPO_MOVIMIENTO_ARTICULO_RESERVA, newValue);
     }

     /**
      * Set the value of the tipo_movimiento_articulo.reserva column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTipoMovimientoArticuloReserva(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_MOVIMIENTO_ARTICULO_RESERVA, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
