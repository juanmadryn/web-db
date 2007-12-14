package infraestructura.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * RolEntidadModel: A SOFIA generated model
 */
public class RolEntidadModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 3329375419098489133L;
	//constants for columns
     public static final String ROL_ENTIDAD_ROL="rol_entidad.rol";
     public static final String ROL_ENTIDAD_NOMBRE="rol_entidad.nombre";
     public static final String ROL_ENTIDAD_DESCRIPCION="rol_entidad.descripcion";
     public static final String ROL_ENTIDAD_OBSERVACIONES="rol_entidad.observaciones";
     public static final String ROL_ENTIDAD_DESDE="rol_entidad.desde";
     public static final String ROL_ENTIDAD_HASTA="rol_entidad.hasta";
     public static final String ROL_ENTIDAD_ANULADO="rol_entidad.anulado";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new RolEntidadModel object.
      * @param appName The SOFIA application name
      */
     public RolEntidadModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new RolEntidadModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public RolEntidadModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("rol_entidad"),"rol_entidad");

               //add columns
               addColumn(computeTableName("rol_entidad"),"rol",DataStore.DATATYPE_STRING,true,true,ROL_ENTIDAD_ROL);
               addColumn(computeTableName("rol_entidad"),"nombre",DataStore.DATATYPE_STRING,false,true,ROL_ENTIDAD_NOMBRE);
               addColumn(computeTableName("rol_entidad"),"descripcion",DataStore.DATATYPE_STRING,false,true,ROL_ENTIDAD_DESCRIPCION);
               addColumn(computeTableName("rol_entidad"),"observaciones",DataStore.DATATYPE_STRING,false,true,ROL_ENTIDAD_OBSERVACIONES);
               addColumn(computeTableName("rol_entidad"),"desde",DataStore.DATATYPE_DATE,false,true,ROL_ENTIDAD_DESDE);
               addColumn(computeTableName("rol_entidad"),"hasta",DataStore.DATATYPE_DATE,false,true,ROL_ENTIDAD_HASTA);
               addColumn(computeTableName("rol_entidad"),"anulado",DataStore.DATATYPE_STRING,false,true,ROL_ENTIDAD_ANULADO);

               //set order by
               setOrderBy(computeTableAndFieldName("rol_entidad.rol") + " ASC");

               //add validations
               addRequiredRule(ROL_ENTIDAD_ROL,"Identificador de rol requerido");
               addRequiredRule(ROL_ENTIDAD_NOMBRE,"Nombre obligatorio");
               addRequiredRule(ROL_ENTIDAD_DESDE,"Fecha de vigencia desde requerida");
               addExpressionRule(ROL_ENTIDAD_ROL, new ConvierteMayusculasValidation(ROL_ENTIDAD_ROL), "",false);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the rol_entidad.rol column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadRol() throws DataStoreException {
          return  getString(ROL_ENTIDAD_ROL);
     }

     /**
      * Retrieve the value of the rol_entidad.rol column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadRol(int row) throws DataStoreException {
          return  getString(row,ROL_ENTIDAD_ROL);
     }

     /**
      * Set the value of the rol_entidad.rol column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadRol(String newValue) throws DataStoreException {
          setString(ROL_ENTIDAD_ROL, newValue);
     }

     /**
      * Set the value of the rol_entidad.rol column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadRol(int row,String newValue) throws DataStoreException {
          setString(row,ROL_ENTIDAD_ROL, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadNombre() throws DataStoreException {
          return  getString(ROL_ENTIDAD_NOMBRE);
     }

     /**
      * Retrieve the value of the rol_entidad.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadNombre(int row) throws DataStoreException {
          return  getString(row,ROL_ENTIDAD_NOMBRE);
     }

     /**
      * Set the value of the rol_entidad.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadNombre(String newValue) throws DataStoreException {
          setString(ROL_ENTIDAD_NOMBRE, newValue);
     }

     /**
      * Set the value of the rol_entidad.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadNombre(int row,String newValue) throws DataStoreException {
          setString(row,ROL_ENTIDAD_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadDescripcion() throws DataStoreException {
          return  getString(ROL_ENTIDAD_DESCRIPCION);
     }

     /**
      * Retrieve the value of the rol_entidad.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadDescripcion(int row) throws DataStoreException {
          return  getString(row,ROL_ENTIDAD_DESCRIPCION);
     }

     /**
      * Set the value of the rol_entidad.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadDescripcion(String newValue) throws DataStoreException {
          setString(ROL_ENTIDAD_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the rol_entidad.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ROL_ENTIDAD_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadObservaciones() throws DataStoreException {
          return  getString(ROL_ENTIDAD_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the rol_entidad.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadObservaciones(int row) throws DataStoreException {
          return  getString(row,ROL_ENTIDAD_OBSERVACIONES);
     }

     /**
      * Set the value of the rol_entidad.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadObservaciones(String newValue) throws DataStoreException {
          setString(ROL_ENTIDAD_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the rol_entidad.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ROL_ENTIDAD_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.desde column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getRolEntidadDesde() throws DataStoreException {
          return  getDate(ROL_ENTIDAD_DESDE);
     }

     /**
      * Retrieve the value of the rol_entidad.desde column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getRolEntidadDesde(int row) throws DataStoreException {
          return  getDate(row,ROL_ENTIDAD_DESDE);
     }

     /**
      * Set the value of the rol_entidad.desde column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadDesde(java.sql.Date newValue) throws DataStoreException {
          setDate(ROL_ENTIDAD_DESDE, newValue);
     }

     /**
      * Set the value of the rol_entidad.desde column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadDesde(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,ROL_ENTIDAD_DESDE, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.hasta column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getRolEntidadHasta() throws DataStoreException {
          return  getDate(ROL_ENTIDAD_HASTA);
     }

     /**
      * Retrieve the value of the rol_entidad.hasta column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getRolEntidadHasta(int row) throws DataStoreException {
          return  getDate(row,ROL_ENTIDAD_HASTA);
     }

     /**
      * Set the value of the rol_entidad.hasta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadHasta(java.sql.Date newValue) throws DataStoreException {
          setDate(ROL_ENTIDAD_HASTA, newValue);
     }

     /**
      * Set the value of the rol_entidad.hasta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadHasta(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,ROL_ENTIDAD_HASTA, newValue);
     }

     /**
      * Retrieve the value of the rol_entidad.anulado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadAnulado() throws DataStoreException {
          return  getString(ROL_ENTIDAD_ANULADO);
     }

     /**
      * Retrieve the value of the rol_entidad.anulado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getRolEntidadAnulado(int row) throws DataStoreException {
          return  getString(row,ROL_ENTIDAD_ANULADO);
     }

     /**
      * Set the value of the rol_entidad.anulado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadAnulado(String newValue) throws DataStoreException {
          setString(ROL_ENTIDAD_ANULADO, newValue);
     }

     /**
      * Set the value of the rol_entidad.anulado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setRolEntidadAnulado(int row,String newValue) throws DataStoreException {
          setString(row,ROL_ENTIDAD_ANULADO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
