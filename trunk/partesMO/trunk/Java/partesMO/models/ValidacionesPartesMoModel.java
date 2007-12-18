package partesMO.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ValidacionesPartesMoModel: A SOFIA generated model
 */
public class ValidacionesPartesMoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -4209144613099217142L;
	//constants for columns
     public static final String VALIDACIONES_PARTES_MO_VALIDACION_ID="validaciones_partes_mo.validacion_id";
     public static final String VALIDACIONES_PARTES_MO_NOMBRE="validaciones_partes_mo.nombre";
     public static final String VALIDACIONES_PARTES_MO_DESCRIPCION="validaciones_partes_mo.descripcion";
     public static final String VALIDACIONES_PARTES_MO_OBSERVACIONES="validaciones_partes_mo.observaciones";
     public static final String VALIDACIONES_PARTES_MO_VALIDADOR="validaciones_partes_mo.validador";
     public static final String VALIDACIONES_PARTES_MO_TIPO="validaciones_partes_mo.tipo";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ValidacionesPartesMoModel object.
      * @param appName The SOFIA application name
      */
     public ValidacionesPartesMoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ValidacionesPartesMoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ValidacionesPartesMoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("validaciones_partes_mo"),"validaciones_partes_mo");

               //add columns
               addColumn(computeTableName("validaciones_partes_mo"),"validacion_id",DataStore.DATATYPE_INT,true,true,VALIDACIONES_PARTES_MO_VALIDACION_ID);
               addColumn(computeTableName("validaciones_partes_mo"),"nombre",DataStore.DATATYPE_STRING,false,true,VALIDACIONES_PARTES_MO_NOMBRE);
               addColumn(computeTableName("validaciones_partes_mo"),"descripcion",DataStore.DATATYPE_STRING,false,true,VALIDACIONES_PARTES_MO_DESCRIPCION);
               addColumn(computeTableName("validaciones_partes_mo"),"observaciones",DataStore.DATATYPE_STRING,false,true,VALIDACIONES_PARTES_MO_OBSERVACIONES);
               addColumn(computeTableName("validaciones_partes_mo"),"validador",DataStore.DATATYPE_STRING,false,true,VALIDACIONES_PARTES_MO_VALIDADOR);
               addColumn(computeTableName("validaciones_partes_mo"),"tipo",DataStore.DATATYPE_STRING,false,true,VALIDACIONES_PARTES_MO_TIPO);

               //set order by
               setOrderBy(computeTableAndFieldName("validaciones_partes_mo.nombre") + " ASC");

               //add validations
               addRequiredRule(VALIDACIONES_PARTES_MO_NOMBRE,"Nombre de validación obligatorio");
               addRequiredRule(VALIDACIONES_PARTES_MO_TIPO,"Tipo de validación obligatorio");
               addRequiredRule(VALIDACIONES_PARTES_MO_VALIDADOR,"Rutina de validación obligatoria");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the validaciones_partes_mo.validacion_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getValidacionesPartesMoValidacionId() throws DataStoreException {
          return  getInt(VALIDACIONES_PARTES_MO_VALIDACION_ID);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.validacion_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getValidacionesPartesMoValidacionId(int row) throws DataStoreException {
          return  getInt(row,VALIDACIONES_PARTES_MO_VALIDACION_ID);
     }

     /**
      * Set the value of the validaciones_partes_mo.validacion_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoValidacionId(int newValue) throws DataStoreException {
          setInt(VALIDACIONES_PARTES_MO_VALIDACION_ID, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.validacion_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoValidacionId(int row,int newValue) throws DataStoreException {
          setInt(row,VALIDACIONES_PARTES_MO_VALIDACION_ID, newValue);
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
      * Retrieve the value of the validaciones_partes_mo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoDescripcion() throws DataStoreException {
          return  getString(VALIDACIONES_PARTES_MO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoDescripcion(int row) throws DataStoreException {
          return  getString(row,VALIDACIONES_PARTES_MO_DESCRIPCION);
     }

     /**
      * Set the value of the validaciones_partes_mo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoDescripcion(String newValue) throws DataStoreException {
          setString(VALIDACIONES_PARTES_MO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,VALIDACIONES_PARTES_MO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoObservaciones() throws DataStoreException {
          return  getString(VALIDACIONES_PARTES_MO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the validaciones_partes_mo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getValidacionesPartesMoObservaciones(int row) throws DataStoreException {
          return  getString(row,VALIDACIONES_PARTES_MO_OBSERVACIONES);
     }

     /**
      * Set the value of the validaciones_partes_mo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoObservaciones(String newValue) throws DataStoreException {
          setString(VALIDACIONES_PARTES_MO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the validaciones_partes_mo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setValidacionesPartesMoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,VALIDACIONES_PARTES_MO_OBSERVACIONES, newValue);
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
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
