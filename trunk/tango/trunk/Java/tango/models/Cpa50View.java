package tango.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * Cpa50View: A SOFIA generated model
 */
public class Cpa50View extends DataStore {

     //constants for columns
     public static final String CPA50_ID_CPA50="cpa50.ID_CPA50";
     public static final String CPA50_FILLER="cpa50.FILLER";
     public static final String CPA50_COD_COMPRA="cpa50.COD_COMPRA";
     public static final String CPA50_NOM_COMPRA="cpa50.NOM_COMPRA";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new Cpa50View object.
      * @param appName The SOFIA application name
      */
     public Cpa50View (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new Cpa50View object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public Cpa50View (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("cpa50"),"ID_CPA50",DataStore.DATATYPE_INT,false,true,CPA50_ID_CPA50);
          addColumn(computeTableName("cpa50"),"FILLER",DataStore.DATATYPE_STRING,false,true,CPA50_FILLER);
          addColumn(computeTableName("cpa50"),"COD_COMPRA",DataStore.DATATYPE_STRING,false,true,CPA50_COD_COMPRA);
          addColumn(computeTableName("cpa50"),"NOM_COMPRA",DataStore.DATATYPE_STRING,false,true,CPA50_NOM_COMPRA);

          //set order by
          setOrderBy(computeTableAndFieldName("cpa50.ID_CPA50") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the cpa50.ID_CPA50 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCpa50IdCpa50() throws DataStoreException {
          return  getInt(CPA50_ID_CPA50);
     }

     /**
      * Retrieve the value of the cpa50.ID_CPA50 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCpa50IdCpa50(int row) throws DataStoreException {
          return  getInt(row,CPA50_ID_CPA50);
     }

     /**
      * Set the value of the cpa50.ID_CPA50 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50IdCpa50(int newValue) throws DataStoreException {
          setInt(CPA50_ID_CPA50, newValue);
     }

     /**
      * Set the value of the cpa50.ID_CPA50 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50IdCpa50(int row,int newValue) throws DataStoreException {
          setInt(row,CPA50_ID_CPA50, newValue);
     }

     /**
      * Retrieve the value of the cpa50.FILLER column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50Filler() throws DataStoreException {
          return  getString(CPA50_FILLER);
     }

     /**
      * Retrieve the value of the cpa50.FILLER column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50Filler(int row) throws DataStoreException {
          return  getString(row,CPA50_FILLER);
     }

     /**
      * Set the value of the cpa50.FILLER column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50Filler(String newValue) throws DataStoreException {
          setString(CPA50_FILLER, newValue);
     }

     /**
      * Set the value of the cpa50.FILLER column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50Filler(int row,String newValue) throws DataStoreException {
          setString(row,CPA50_FILLER, newValue);
     }

     /**
      * Retrieve the value of the cpa50.COD_COMPRA column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50CodCompra() throws DataStoreException {
          return  getString(CPA50_COD_COMPRA);
     }

     /**
      * Retrieve the value of the cpa50.COD_COMPRA column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50CodCompra(int row) throws DataStoreException {
          return  getString(row,CPA50_COD_COMPRA);
     }

     /**
      * Set the value of the cpa50.COD_COMPRA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50CodCompra(String newValue) throws DataStoreException {
          setString(CPA50_COD_COMPRA, newValue);
     }

     /**
      * Set the value of the cpa50.COD_COMPRA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50CodCompra(int row,String newValue) throws DataStoreException {
          setString(row,CPA50_COD_COMPRA, newValue);
     }

     /**
      * Retrieve the value of the cpa50.NOM_COMPRA column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50NomCompra() throws DataStoreException {
          return  getString(CPA50_NOM_COMPRA);
     }

     /**
      * Retrieve the value of the cpa50.NOM_COMPRA column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCpa50NomCompra(int row) throws DataStoreException {
          return  getString(row,CPA50_NOM_COMPRA);
     }

     /**
      * Set the value of the cpa50.NOM_COMPRA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50NomCompra(String newValue) throws DataStoreException {
          setString(CPA50_NOM_COMPRA, newValue);
     }

     /**
      * Set the value of the cpa50.NOM_COMPRA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCpa50NomCompra(int row,String newValue) throws DataStoreException {
          setString(row,CPA50_NOM_COMPRA, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
