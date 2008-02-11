package partesMO.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * SectorTrabajoModel: A SOFIA generated model
 */
public class SectorTrabajoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 153187977319498282L;
	//constants for columns
     public static final String SECTOR_TRABAJO_SECTOR_ID="sector_trabajo.sector_id";
     public static final String SECTOR_TRABAJO_NOMBRE="sector_trabajo.nombre";
     public static final String SECTOR_TRABAJO_DESCRIPCION="sector_trabajo.descripcion";
     public static final String SECTOR_TRABAJO_OBSERVACIONES="sector_trabajo.observaciones";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new SectorTrabajoModel object.
      * @param appName The SOFIA application name
      */
     public SectorTrabajoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new SectorTrabajoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public SectorTrabajoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("sector_trabajo"),"sector_trabajo");

               //add columns
               addColumn(computeTableName("sector_trabajo"),"sector_id",DataStore.DATATYPE_INT,true,true,SECTOR_TRABAJO_SECTOR_ID);
               addColumn(computeTableName("sector_trabajo"),"nombre",DataStore.DATATYPE_STRING,false,true,SECTOR_TRABAJO_NOMBRE);
               addColumn(computeTableName("sector_trabajo"),"descripcion",DataStore.DATATYPE_STRING,false,true,SECTOR_TRABAJO_DESCRIPCION);
               addColumn(computeTableName("sector_trabajo"),"observaciones",DataStore.DATATYPE_STRING,false,true,SECTOR_TRABAJO_OBSERVACIONES);

               //set order by
               setOrderBy(computeTableAndFieldName("sector_trabajo.nombre") + " ASC");

               //add validations
               addRequiredRule(SECTOR_TRABAJO_NOMBRE,"Nombre del sector obligatorio");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the sector_trabajo.sector_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getSectorTrabajoSectorId() throws DataStoreException {
          return  getInt(SECTOR_TRABAJO_SECTOR_ID);
     }

     /**
      * Retrieve the value of the sector_trabajo.sector_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getSectorTrabajoSectorId(int row) throws DataStoreException {
          return  getInt(row,SECTOR_TRABAJO_SECTOR_ID);
     }

     /**
      * Set the value of the sector_trabajo.sector_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoSectorId(int newValue) throws DataStoreException {
          setInt(SECTOR_TRABAJO_SECTOR_ID, newValue);
     }

     /**
      * Set the value of the sector_trabajo.sector_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoSectorId(int row,int newValue) throws DataStoreException {
          setInt(row,SECTOR_TRABAJO_SECTOR_ID, newValue);
     }

     /**
      * Retrieve the value of the sector_trabajo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoNombre() throws DataStoreException {
          return  getString(SECTOR_TRABAJO_NOMBRE);
     }

     /**
      * Retrieve the value of the sector_trabajo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoNombre(int row) throws DataStoreException {
          return  getString(row,SECTOR_TRABAJO_NOMBRE);
     }

     /**
      * Set the value of the sector_trabajo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoNombre(String newValue) throws DataStoreException {
          setString(SECTOR_TRABAJO_NOMBRE, newValue);
     }

     /**
      * Set the value of the sector_trabajo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoNombre(int row,String newValue) throws DataStoreException {
          setString(row,SECTOR_TRABAJO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the sector_trabajo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoDescripcion() throws DataStoreException {
          return  getString(SECTOR_TRABAJO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the sector_trabajo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoDescripcion(int row) throws DataStoreException {
          return  getString(row,SECTOR_TRABAJO_DESCRIPCION);
     }

     /**
      * Set the value of the sector_trabajo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoDescripcion(String newValue) throws DataStoreException {
          setString(SECTOR_TRABAJO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the sector_trabajo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,SECTOR_TRABAJO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the sector_trabajo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoObservaciones() throws DataStoreException {
          return  getString(SECTOR_TRABAJO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the sector_trabajo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getSectorTrabajoObservaciones(int row) throws DataStoreException {
          return  getString(row,SECTOR_TRABAJO_OBSERVACIONES);
     }

     /**
      * Set the value of the sector_trabajo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoObservaciones(String newValue) throws DataStoreException {
          setString(SECTOR_TRABAJO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the sector_trabajo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSectorTrabajoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,SECTOR_TRABAJO_OBSERVACIONES, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
