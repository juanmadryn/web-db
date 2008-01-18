package partesMO.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ResumenHorasModel: A SOFIA generated model
 */
public class ResumenHorasModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -222544825410854588L;
	//constants for columns
     public static final String RESUMEN_HORAS_RESUMEN_ID="resumen_horas.resumen_id";
     public static final String RESUMEN_HORAS_PERIODO="resumen_horas.periodo";
     public static final String RESUMEN_HORAS_NRO_LEGAJO="resumen_horas.nro_legajo";
     public static final String RESUMEN_HORAS_APEYNOM="resumen_horas.apeynom";
     public static final String RESUMEN_HORAS_QUINCENA="resumen_horas.quincena";
     public static final String RESUMEN_HORAS_HORAS="resumen_horas.horas";
     public static final String RESUMEN_HORAS_NORMALES="resumen_horas.normales";
     public static final String RESUMEN_HORAS_AL_50="resumen_horas.al_50";
     public static final String RESUMEN_HORAS_AL_100="resumen_horas.al_100";
     public static final String RESUMEN_HORAS_NOCTURNAS="resumen_horas.nocturnas";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ResumenHorasModel object.
      * @param appName The SOFIA application name
      */
     public ResumenHorasModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ResumenHorasModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ResumenHorasModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("resumen_horas"),"resumen_horas");

          //add columns
          addColumn(computeTableName("resumen_horas"),"resumen_id",DataStore.DATATYPE_INT,true,true,RESUMEN_HORAS_RESUMEN_ID);
          addColumn(computeTableName("resumen_horas"),"periodo",DataStore.DATATYPE_DATE,false,true,RESUMEN_HORAS_PERIODO);
          addColumn(computeTableName("resumen_horas"),"nro_legajo",DataStore.DATATYPE_INT,false,true,RESUMEN_HORAS_NRO_LEGAJO);
          addColumn(computeTableName("resumen_horas"),"apeynom",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_APEYNOM);
          addColumn(computeTableName("resumen_horas"),"quincena",DataStore.DATATYPE_INT,false,true,RESUMEN_HORAS_QUINCENA);
          addColumn(computeTableName("resumen_horas"),"horas",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_HORAS);
          addColumn(computeTableName("resumen_horas"),"normales",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_NORMALES);
          addColumn(computeTableName("resumen_horas"),"al_50",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_AL_50);
          addColumn(computeTableName("resumen_horas"),"al_100",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_AL_100);
          addColumn(computeTableName("resumen_horas"),"nocturnas",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_NOCTURNAS);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the resumen_horas.resumen_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasResumenId() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_RESUMEN_ID);
     }

     /**
      * Retrieve the value of the resumen_horas.resumen_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasResumenId(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_RESUMEN_ID);
     }

     /**
      * Set the value of the resumen_horas.resumen_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasResumenId(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_RESUMEN_ID, newValue);
     }

     /**
      * Set the value of the resumen_horas.resumen_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasResumenId(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_RESUMEN_ID, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.periodo column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getResumenHorasPeriodo() throws DataStoreException {
          return  getDate(RESUMEN_HORAS_PERIODO);
     }

     /**
      * Retrieve the value of the resumen_horas.periodo column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getResumenHorasPeriodo(int row) throws DataStoreException {
          return  getDate(row,RESUMEN_HORAS_PERIODO);
     }

     /**
      * Set the value of the resumen_horas.periodo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasPeriodo(java.sql.Date newValue) throws DataStoreException {
          setDate(RESUMEN_HORAS_PERIODO, newValue);
     }

     /**
      * Set the value of the resumen_horas.periodo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasPeriodo(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,RESUMEN_HORAS_PERIODO, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.nro_legajo column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasNroLegajo() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the resumen_horas.nro_legajo column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasNroLegajo(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_NRO_LEGAJO);
     }

     /**
      * Set the value of the resumen_horas.nro_legajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNroLegajo(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the resumen_horas.nro_legajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_NRO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.apeynom column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasApeynom() throws DataStoreException {
          return  getString(RESUMEN_HORAS_APEYNOM);
     }

     /**
      * Retrieve the value of the resumen_horas.apeynom column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasApeynom(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_APEYNOM);
     }

     /**
      * Set the value of the resumen_horas.apeynom column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasApeynom(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_APEYNOM, newValue);
     }

     /**
      * Set the value of the resumen_horas.apeynom column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasApeynom(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_APEYNOM, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.quincena column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasQuincena() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_QUINCENA);
     }

     /**
      * Retrieve the value of the resumen_horas.quincena column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasQuincena(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_QUINCENA);
     }

     /**
      * Set the value of the resumen_horas.quincena column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasQuincena(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_QUINCENA, newValue);
     }

     /**
      * Set the value of the resumen_horas.quincena column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasQuincena(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_QUINCENA, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.horas column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasHoras() throws DataStoreException {
          return  getString(RESUMEN_HORAS_HORAS);
     }

     /**
      * Retrieve the value of the resumen_horas.horas column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasHoras(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_HORAS);
     }

     /**
      * Set the value of the resumen_horas.horas column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasHoras(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_HORAS, newValue);
     }

     /**
      * Set the value of the resumen_horas.horas column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasHoras(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_HORAS, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.normales column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasNormales() throws DataStoreException {
          return  getString(RESUMEN_HORAS_NORMALES);
     }

     /**
      * Retrieve the value of the resumen_horas.normales column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasNormales(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_NORMALES);
     }

     /**
      * Set the value of the resumen_horas.normales column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNormales(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_NORMALES, newValue);
     }

     /**
      * Set the value of the resumen_horas.normales column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNormales(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_NORMALES, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.al_50 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasAl50() throws DataStoreException {
          return  getString(RESUMEN_HORAS_AL_50);
     }

     /**
      * Retrieve the value of the resumen_horas.al_50 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasAl50(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_AL_50);
     }

     /**
      * Set the value of the resumen_horas.al_50 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasAl50(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_AL_50, newValue);
     }

     /**
      * Set the value of the resumen_horas.al_50 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasAl50(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_AL_50, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.al_100 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasAl100() throws DataStoreException {
          return  getString(RESUMEN_HORAS_AL_100);
     }

     /**
      * Retrieve the value of the resumen_horas.al_100 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasAl100(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_AL_100);
     }

     /**
      * Set the value of the resumen_horas.al_100 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasAl100(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_AL_100, newValue);
     }

     /**
      * Set the value of the resumen_horas.al_100 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasAl100(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_AL_100, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas.nocturnas column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasNocturnas() throws DataStoreException {
          return  getString(RESUMEN_HORAS_NOCTURNAS);
     }

     /**
      * Retrieve the value of the resumen_horas.nocturnas column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasNocturnas(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_NOCTURNAS);
     }

     /**
      * Set the value of the resumen_horas.nocturnas column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNocturnas(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_NOCTURNAS, newValue);
     }

     /**
      * Set the value of the resumen_horas.nocturnas column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasNocturnas(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_NOCTURNAS, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
