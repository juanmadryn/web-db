package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * feriadosModel: A SOFIA generated model
 */
public class feriadosModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -6849437624303584817L;
	//constants for columns
     public static final String FERIADOS_FERIADO="feriados.feriado";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new feriadosModel object.
      * @param appName The SOFIA application name
      */
     public feriadosModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new feriadosModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public feriadosModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("feriados"),"feriados");

          //add columns
          addColumn(computeTableName("feriados"),"feriado",DataStore.DATATYPE_DATE,true,true,FERIADOS_FERIADO);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the feriados.feriado column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getFeriadosFeriado() throws DataStoreException {
          return  getDate(FERIADOS_FERIADO);
     }

     /**
      * Retrieve the value of the feriados.feriado column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getFeriadosFeriado(int row) throws DataStoreException {
          return  getDate(row,FERIADOS_FERIADO);
     }

     /**
      * Set the value of the feriados.feriado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFeriadosFeriado(java.sql.Date newValue) throws DataStoreException {
          setDate(FERIADOS_FERIADO, newValue);
     }

     /**
      * Set the value of the feriados.feriado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setFeriadosFeriado(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,FERIADOS_FERIADO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     public boolean esFeriado(java.sql.Date fecha) throws DataStoreException {
    	 for (int i = 0 ; i < getRowCount() ; i++)
    		 if (getFeriadosFeriado(i).compareTo(fecha) == 0)
    			 return true;
    	 
    	 return false;
     }
     //$ENDCUSTOMMETHODS$
     
}
