package inventario.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CentroCostoModel: A SOFIA generated model
 */
public class CentroCostoModel extends DataStore {

     //constants for columns
     public static final String CENTRO_COSTO_CENTRO_COSTO_ID="centro_costo.centro_costo_id";
     public static final String CENTRO_COSTO_NOMBRE="centro_costo.nombre";
     public static final String CENTRO_COSTO_DESCRIPCION="centro_costo.descripcion";
     public static final String CENTRO_COSTO_OBSERVACIONES="centro_costo.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new CentroCostoModel object.
      * @param appName The SOFIA application name
      */
     public CentroCostoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new CentroCostoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public CentroCostoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add columns
               addColumn(computeTableName("centro_costo"),"centro_costo_id",DataStore.DATATYPE_INT,true,true,CENTRO_COSTO_CENTRO_COSTO_ID);
               addColumn(computeTableName("centro_costo"),"nombre",DataStore.DATATYPE_STRING,false,true,CENTRO_COSTO_NOMBRE);
               addColumn(computeTableName("centro_costo"),"descripcion",DataStore.DATATYPE_STRING,false,true,CENTRO_COSTO_DESCRIPCION);
               addColumn(computeTableName("centro_costo"),"observaciones",DataStore.DATATYPE_STRING,false,true,CENTRO_COSTO_OBSERVACIONES);

               //set order by
               setOrderBy(computeTableAndFieldName("centro_costo.nombre") + " ASC");

               //add validations
               addRequiredRule(CENTRO_COSTO_CENTRO_COSTO_ID,"El id del cento de costo es obligatorio");
               addRequiredRule(CENTRO_COSTO_NOMBRE,"El nombre del centro de costo es obligatorio");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the centro_costo.centro_costo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCentroCostoCentroCostoId() throws DataStoreException {
          return  getInt(CENTRO_COSTO_CENTRO_COSTO_ID);
     }

     /**
      * Retrieve the value of the centro_costo.centro_costo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCentroCostoCentroCostoId(int row) throws DataStoreException {
          return  getInt(row,CENTRO_COSTO_CENTRO_COSTO_ID);
     }

     /**
      * Set the value of the centro_costo.centro_costo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoCentroCostoId(int newValue) throws DataStoreException {
          setInt(CENTRO_COSTO_CENTRO_COSTO_ID, newValue);
     }

     /**
      * Set the value of the centro_costo.centro_costo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoCentroCostoId(int row,int newValue) throws DataStoreException {
          setInt(row,CENTRO_COSTO_CENTRO_COSTO_ID, newValue);
     }

     /**
      * Retrieve the value of the centro_costo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoNombre() throws DataStoreException {
          return  getString(CENTRO_COSTO_NOMBRE);
     }

     /**
      * Retrieve the value of the centro_costo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoNombre(int row) throws DataStoreException {
          return  getString(row,CENTRO_COSTO_NOMBRE);
     }

     /**
      * Set the value of the centro_costo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoNombre(String newValue) throws DataStoreException {
          setString(CENTRO_COSTO_NOMBRE, newValue);
     }

     /**
      * Set the value of the centro_costo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoNombre(int row,String newValue) throws DataStoreException {
          setString(row,CENTRO_COSTO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the centro_costo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoDescripcion() throws DataStoreException {
          return  getString(CENTRO_COSTO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the centro_costo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoDescripcion(int row) throws DataStoreException {
          return  getString(row,CENTRO_COSTO_DESCRIPCION);
     }

     /**
      * Set the value of the centro_costo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoDescripcion(String newValue) throws DataStoreException {
          setString(CENTRO_COSTO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the centro_costo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,CENTRO_COSTO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the centro_costo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoObservaciones() throws DataStoreException {
          return  getString(CENTRO_COSTO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the centro_costo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCentroCostoObservaciones(int row) throws DataStoreException {
          return  getString(row,CENTRO_COSTO_OBSERVACIONES);
     }

     /**
      * Set the value of the centro_costo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoObservaciones(String newValue) throws DataStoreException {
          setString(CENTRO_COSTO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the centro_costo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCentroCostoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,CENTRO_COSTO_OBSERVACIONES, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
