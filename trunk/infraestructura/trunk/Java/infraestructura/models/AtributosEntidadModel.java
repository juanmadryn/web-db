package infraestructura.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AtributosEntidadModel: A SOFIA generated model
 */
public class AtributosEntidadModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -1192786751287119153L;
	//constants for columns
     public static final String ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID="atributos_entidad.atributo_entidad_id";
     public static final String ATRIBUTOS_ENTIDAD_ATRIBUTO_ID="atributos_entidad.atributo_id";
     public static final String ATRIBUTOS_ENTIDAD_ENTIDAD_ID="atributos_entidad.entidad_id";
     public static final String ATRIBUTOS_ENTIDAD_OBJETO_ID="atributos_entidad.objeto_id";
     public static final String ATRIBUTOS_ENTIDAD_TIPO_OBJETO="atributos_entidad.tipo_objeto";
     public static final String ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO="atributos_entidad.nombre_objeto";
     public static final String ATRIBUTOS_ENTIDAD_VALOR="atributos_entidad.valor";
     public static final String ATRIBUTOS_ENTIDAD_VALOR_ENTERO="atributos_entidad.valor_entero";
     public static final String ATRIBUTOS_ENTIDAD_VALOR_REAL="atributos_entidad.valor_real";
     public static final String ATRIBUTOS_ENTIDAD_VALOR_FECHA="atributos_entidad.valor_fecha";
     public static final String ATRIBUTOS_ENTIDAD_VALOR_LOGICO="atributos_entidad.valor_logico";
     public static final String ATRIBUTOS_ROL_NOMBRE="atributos_rol.nombre";
     public static final String ATRIBUTOS_ROL_ROL="atributos_rol.rol";
     public static final String ENTIDAD_EXTERNA_NOMBRE="entidad_externa.nombre";
     public static final String CLASE_ATRIBUTO_ROL_ETIQUETA="clase_atributo_rol.etiqueta";
     public static final String ATRIBUTOS_ROL_NOMBRE_OBJETO="atributos_rol.nombre_objeto";
     public static final String ATRIBUTOS_ROL_TIPO_OBJETO="atributos_rol.tipo_objeto";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new AtributosEntidadModel object.
      * @param appName The SOFIA application name
      */
     public AtributosEntidadModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new AtributosEntidadModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public AtributosEntidadModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("atributos_entidad"),"atributos_entidad");
               addTableAlias(computeTableName("atributos_rol"),null);
               addTableAlias(computeTableName("entidad_externa"),null);
               addTableAlias(computeTableName("clase_atributo_rol"),null);

               //add columns
               addColumn(computeTableName("atributos_entidad"),"atributo_entidad_id",DataStore.DATATYPE_INT,true,true,ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID);
               addColumn(computeTableName("atributos_entidad"),"atributo_id",DataStore.DATATYPE_INT,true,true,ATRIBUTOS_ENTIDAD_ATRIBUTO_ID);
               addColumn(computeTableName("atributos_entidad"),"entidad_id",DataStore.DATATYPE_INT,false,true,ATRIBUTOS_ENTIDAD_ENTIDAD_ID);
               addColumn(computeTableName("atributos_entidad"),"objeto_id",DataStore.DATATYPE_INT,false,true,ATRIBUTOS_ENTIDAD_OBJETO_ID);
               addColumn(computeTableName("atributos_entidad"),"tipo_objeto",DataStore.DATATYPE_STRING,false,true,ATRIBUTOS_ENTIDAD_TIPO_OBJETO);
               addColumn(computeTableName("atributos_entidad"),"nombre_objeto",DataStore.DATATYPE_STRING,false,true,ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO);
               addColumn(computeTableName("atributos_entidad"),"valor",DataStore.DATATYPE_STRING,true,true,ATRIBUTOS_ENTIDAD_VALOR);
               addColumn(computeTableName("atributos_entidad"),"valor_entero",DataStore.DATATYPE_INT,false,true,ATRIBUTOS_ENTIDAD_VALOR_ENTERO);
               addColumn(computeTableName("atributos_entidad"),"valor_real",DataStore.DATATYPE_DOUBLE,false,true,ATRIBUTOS_ENTIDAD_VALOR_REAL);
               addColumn(computeTableName("atributos_entidad"),"valor_fecha",DataStore.DATATYPE_DATE,false,true,ATRIBUTOS_ENTIDAD_VALOR_FECHA);
               addColumn(computeTableName("atributos_entidad"),"valor_logico",DataStore.DATATYPE_STRING,false,true,ATRIBUTOS_ENTIDAD_VALOR_LOGICO);
               addColumn(computeTableName("atributos_rol"),"nombre",DataStore.DATATYPE_STRING,false,false,ATRIBUTOS_ROL_NOMBRE);
               addColumn(computeTableName("atributos_rol"),"rol",DataStore.DATATYPE_STRING,false,false,ATRIBUTOS_ROL_ROL);
               addColumn(computeTableName("entidad_externa"),"nombre",DataStore.DATATYPE_STRING,false,false,ENTIDAD_EXTERNA_NOMBRE);
               addColumn(computeTableName("clase_atributo_rol"),"etiqueta",DataStore.DATATYPE_STRING,false,false,CLASE_ATRIBUTO_ROL_ETIQUETA);
               addColumn(computeTableName("atributos_rol"),"nombre_objeto",DataStore.DATATYPE_STRING,false,false,ATRIBUTOS_ROL_NOMBRE_OBJETO);
               addColumn(computeTableName("atributos_rol"),"tipo_objeto",DataStore.DATATYPE_STRING,false,false,ATRIBUTOS_ROL_TIPO_OBJETO);

               //add joins
               addJoin(computeTableAndFieldName("atributos_entidad.atributo_id"),computeTableAndFieldName("atributos_rol.atributo_id"),false);
               addJoin(computeTableAndFieldName("atributos_entidad.entidad_id"),computeTableAndFieldName("entidad_externa.entidad_id"),true);
               addJoin(computeTableAndFieldName("atributos_rol.clase_atributo_rol_id"),computeTableAndFieldName("clase_atributo_rol.clase_atributo_rol_id"),true);

               //set order by
               setOrderBy(computeTableAndFieldName("clase_atributo_rol.etiqueta") + " ASC," + computeTableAndFieldName("atributos_rol.nombre") + " ASC");
               
               //set auto increment
               setAutoIncrement(ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID, true);
               setUpdateable(ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID, false);

               //add validations
               addRequiredRule(ATRIBUTOS_ENTIDAD_ATRIBUTO_ID,"id del atributo es obligatorio");
               addLookupRule(ATRIBUTOS_ENTIDAD_ATRIBUTO_ID,"atributos_rol","'atributo_id = '+ atributos_entidad.atributo_id","nombre","atributos_rol.nombre","Atributo inexistente");
               addLookupRule(ATRIBUTOS_ENTIDAD_ENTIDAD_ID,"entidad_externa","'entidad_id = '+ atributos_entidad.entidad_id","nombre","entidad_externa.nombre","Entidad externa inexistente");
               addRequiredRule(ATRIBUTOS_ENTIDAD_VALOR,"Debe ingresar un valor para el atributo");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the atributos_entidad.atributo_entidad_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadAtributoEntidadId() throws DataStoreException {
          return  getInt(ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID);
     }

     /**
      * Retrieve the value of the atributos_entidad.atributo_entidad_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadAtributoEntidadId(int row) throws DataStoreException {
          return  getInt(row,ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID);
     }

     /**
      * Set the value of the atributos_entidad.atributo_entidad_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadAtributoEntidadId(int newValue) throws DataStoreException {
          setInt(ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID, newValue);
     }

     /**
      * Set the value of the atributos_entidad.atributo_entidad_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadAtributoEntidadId(int row,int newValue) throws DataStoreException {
          setInt(row,ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.atributo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadAtributoId() throws DataStoreException {
          return  getInt(ATRIBUTOS_ENTIDAD_ATRIBUTO_ID);
     }

     /**
      * Retrieve the value of the atributos_entidad.atributo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadAtributoId(int row) throws DataStoreException {
          return  getInt(row,ATRIBUTOS_ENTIDAD_ATRIBUTO_ID);
     }

     /**
      * Set the value of the atributos_entidad.atributo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadAtributoId(int newValue) throws DataStoreException {
          setInt(ATRIBUTOS_ENTIDAD_ATRIBUTO_ID, newValue);
     }

     /**
      * Set the value of the atributos_entidad.atributo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadAtributoId(int row,int newValue) throws DataStoreException {
          setInt(row,ATRIBUTOS_ENTIDAD_ATRIBUTO_ID, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.entidad_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadEntidadId() throws DataStoreException {
          return  getInt(ATRIBUTOS_ENTIDAD_ENTIDAD_ID);
     }

     /**
      * Retrieve the value of the atributos_entidad.entidad_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadEntidadId(int row) throws DataStoreException {
          return  getInt(row,ATRIBUTOS_ENTIDAD_ENTIDAD_ID);
     }

     /**
      * Set the value of the atributos_entidad.entidad_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadEntidadId(int newValue) throws DataStoreException {
          setInt(ATRIBUTOS_ENTIDAD_ENTIDAD_ID, newValue);
     }

     /**
      * Set the value of the atributos_entidad.entidad_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadEntidadId(int row,int newValue) throws DataStoreException {
          setInt(row,ATRIBUTOS_ENTIDAD_ENTIDAD_ID, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.objeto_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadObjetoId() throws DataStoreException {
          return  getInt(ATRIBUTOS_ENTIDAD_OBJETO_ID);
     }

     /**
      * Retrieve the value of the atributos_entidad.objeto_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadObjetoId(int row) throws DataStoreException {
          return  getInt(row,ATRIBUTOS_ENTIDAD_OBJETO_ID);
     }

     /**
      * Set the value of the atributos_entidad.objeto_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadObjetoId(int newValue) throws DataStoreException {
          setInt(ATRIBUTOS_ENTIDAD_OBJETO_ID, newValue);
     }

     /**
      * Set the value of the atributos_entidad.objeto_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadObjetoId(int row,int newValue) throws DataStoreException {
          setInt(row,ATRIBUTOS_ENTIDAD_OBJETO_ID, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadTipoObjeto() throws DataStoreException {
          return  getString(ATRIBUTOS_ENTIDAD_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the atributos_entidad.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadTipoObjeto(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ENTIDAD_TIPO_OBJETO);
     }

     /**
      * Set the value of the atributos_entidad.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadTipoObjeto(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ENTIDAD_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the atributos_entidad.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ENTIDAD_TIPO_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadNombreObjeto() throws DataStoreException {
          return  getString(ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the atributos_entidad.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadNombreObjeto(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the atributos_entidad.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadNombreObjeto(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the atributos_entidad.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadValor() throws DataStoreException {
          return  getString(ATRIBUTOS_ENTIDAD_VALOR);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadValor(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ENTIDAD_VALOR);
     }

     /**
      * Set the value of the atributos_entidad.valor column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValor(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ENTIDAD_VALOR, newValue);
     }

     /**
      * Set the value of the atributos_entidad.valor column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValor(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ENTIDAD_VALOR, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_entero column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadValorEntero() throws DataStoreException {
          return  getInt(ATRIBUTOS_ENTIDAD_VALOR_ENTERO);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_entero column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAtributosEntidadValorEntero(int row) throws DataStoreException {
          return  getInt(row,ATRIBUTOS_ENTIDAD_VALOR_ENTERO);
     }

     /**
      * Set the value of the atributos_entidad.valor_entero column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorEntero(int newValue) throws DataStoreException {
          setInt(ATRIBUTOS_ENTIDAD_VALOR_ENTERO, newValue);
     }

     /**
      * Set the value of the atributos_entidad.valor_entero column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorEntero(int row,int newValue) throws DataStoreException {
          setInt(row,ATRIBUTOS_ENTIDAD_VALOR_ENTERO, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_real column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getAtributosEntidadValorReal() throws DataStoreException {
          return  getDouble(ATRIBUTOS_ENTIDAD_VALOR_REAL);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_real column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getAtributosEntidadValorReal(int row) throws DataStoreException {
          return  getDouble(row,ATRIBUTOS_ENTIDAD_VALOR_REAL);
     }

     /**
      * Set the value of the atributos_entidad.valor_real column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorReal(double newValue) throws DataStoreException {
          setDouble(ATRIBUTOS_ENTIDAD_VALOR_REAL, newValue);
     }

     /**
      * Set the value of the atributos_entidad.valor_real column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorReal(int row,double newValue) throws DataStoreException {
          setDouble(row,ATRIBUTOS_ENTIDAD_VALOR_REAL, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_fecha column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getAtributosEntidadValorFecha() throws DataStoreException {
          return  getDate(ATRIBUTOS_ENTIDAD_VALOR_FECHA);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_fecha column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getAtributosEntidadValorFecha(int row) throws DataStoreException {
          return  getDate(row,ATRIBUTOS_ENTIDAD_VALOR_FECHA);
     }

     /**
      * Set the value of the atributos_entidad.valor_fecha column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorFecha(java.sql.Date newValue) throws DataStoreException {
          setDate(ATRIBUTOS_ENTIDAD_VALOR_FECHA, newValue);
     }

     /**
      * Set the value of the atributos_entidad.valor_fecha column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorFecha(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,ATRIBUTOS_ENTIDAD_VALOR_FECHA, newValue);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_logico column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadValorLogico() throws DataStoreException {
          return  getString(ATRIBUTOS_ENTIDAD_VALOR_LOGICO);
     }

     /**
      * Retrieve the value of the atributos_entidad.valor_logico column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosEntidadValorLogico(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ENTIDAD_VALOR_LOGICO);
     }

     /**
      * Set the value of the atributos_entidad.valor_logico column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorLogico(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ENTIDAD_VALOR_LOGICO, newValue);
     }

     /**
      * Set the value of the atributos_entidad.valor_logico column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosEntidadValorLogico(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ENTIDAD_VALOR_LOGICO, newValue);
     }

     /**
      * Retrieve the value of the atributos_rol.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolNombre() throws DataStoreException {
          return  getString(ATRIBUTOS_ROL_NOMBRE);
     }

     /**
      * Retrieve the value of the atributos_rol.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolNombre(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ROL_NOMBRE);
     }

     /**
      * Set the value of the atributos_rol.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolNombre(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ROL_NOMBRE, newValue);
     }

     /**
      * Set the value of the atributos_rol.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolNombre(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ROL_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the atributos_rol.rol column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolRol() throws DataStoreException {
          return  getString(ATRIBUTOS_ROL_ROL);
     }

     /**
      * Retrieve the value of the atributos_rol.rol column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolRol(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ROL_ROL);
     }

     /**
      * Set the value of the atributos_rol.rol column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolRol(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ROL_ROL, newValue);
     }

     /**
      * Set the value of the atributos_rol.rol column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolRol(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ROL_ROL, newValue);
     }

     /**
      * Retrieve the value of the entidad_externa.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaNombre() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_NOMBRE);
     }

     /**
      * Retrieve the value of the entidad_externa.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaNombre(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_NOMBRE);
     }

     /**
      * Set the value of the entidad_externa.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaNombre(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_NOMBRE, newValue);
     }

     /**
      * Set the value of the entidad_externa.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaNombre(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the clase_atributo_rol.etiqueta column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseAtributoRolEtiqueta() throws DataStoreException {
          return  getString(CLASE_ATRIBUTO_ROL_ETIQUETA);
     }

     /**
      * Retrieve the value of the clase_atributo_rol.etiqueta column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getClaseAtributoRolEtiqueta(int row) throws DataStoreException {
          return  getString(row,CLASE_ATRIBUTO_ROL_ETIQUETA);
     }

     /**
      * Set the value of the clase_atributo_rol.etiqueta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolEtiqueta(String newValue) throws DataStoreException {
          setString(CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
     }

     /**
      * Set the value of the clase_atributo_rol.etiqueta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setClaseAtributoRolEtiqueta(int row,String newValue) throws DataStoreException {
          setString(row,CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
     }

     /**
      * Retrieve the value of the atributos_rol.nombre_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolNombreObjeto() throws DataStoreException {
          return  getString(ATRIBUTOS_ROL_NOMBRE_OBJETO);
     }

     /**
      * Retrieve the value of the atributos_rol.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolNombreObjeto(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ROL_NOMBRE_OBJETO);
     }

     /**
      * Set the value of the atributos_rol.nombre_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolNombreObjeto(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ROL_NOMBRE_OBJETO, newValue);
     }

     /**
      * Set the value of the atributos_rol.nombre_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolNombreObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ROL_NOMBRE_OBJETO, newValue);
     }

     /**
      * Retrieve the value of the atributos_rol.tipo_objeto column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolTipoObjeto() throws DataStoreException {
          return  getString(ATRIBUTOS_ROL_TIPO_OBJETO);
     }

     /**
      * Retrieve the value of the atributos_rol.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAtributosRolTipoObjeto(int row) throws DataStoreException {
          return  getString(row,ATRIBUTOS_ROL_TIPO_OBJETO);
     }

     /**
      * Set the value of the atributos_rol.tipo_objeto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolTipoObjeto(String newValue) throws DataStoreException {
          setString(ATRIBUTOS_ROL_TIPO_OBJETO, newValue);
     }

     /**
      * Set the value of the atributos_rol.tipo_objeto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAtributosRolTipoObjeto(int row,String newValue) throws DataStoreException {
          setString(row,ATRIBUTOS_ROL_TIPO_OBJETO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

 	/**
 	 * Inserta todos los atributos definidos para el objecto de la aplicacción según p_objeto_id, p_tabla, 
 	 * Sólo completa los atributos que faltan. Dejando intactos los atributos
 	 * existente para el objeto
 	 * 
 	 * @param p_objeto_id --> id del objeto
 	 * @param p_tabla --> nombre de la tabla (objeto)
 	 * @throws DataStoreException
 	 * @throws SQLException
 	 */
 	public void generaAtributosObjetoAplicacion(int p_objeto_id, String p_tabla) throws SQLException,
 			DataStoreException {

 		DBConnection conexion = null;
 		Statement st = null;
 		String SQL = null;

 		// recupero todos los posibles atributos para un proyecto y recorro
 		// buscando si ya existe o no
 		// si no existe lo inserto en el data store
 		try {
 			conexion = DBConnection.getConnection("infraestructura","infraestructura");
 			conexion.beginTransaction();

 			// inserto todos los atributos faltantes
 			SQL = "insert into atributos_entidad (objeto_id,tipo_objeto,nombre_objeto,atributo_id) "
 				+ "select " + Integer.toString(p_objeto_id)  
 				+ ",\"TABLA\" ,\"" + p_tabla 
 				+ "\",atributo_id from infraestructura.atributos_rol "
 				+ "where ((rol is null and tipo_objeto is null and nombre_objeto is null)"
 				+ " or (tipo_objeto = \"TABLA\" "
 				+ "  and nombre_objeto = \""+ p_tabla + "\")) "
 				+ "  and atributo_id not in (select atributo_id from atributos_entidad "
                + "                        where objeto_id = " + Integer.toString(p_objeto_id) 
                						  + " and tipo_objeto = \"TABLA\""
                						  + " and nombre_objeto = \""+ p_tabla + "\")";
 			st = conexion.createStatement();
 			st.executeUpdate(SQL);
 			
 			conexion.commit();

 		} catch (SQLException e) {
 			MessageLog.writeErrorMessage(e, null);
 			// además de escribir en el log mando mensaje a la página
 			conexion.rollback();
 			throw new SQLException("Error insertando atributos para objeto de la aplicación: "+ p_tabla +" --> "+ e.getMessage());
 		} finally {
 			if (st != null)
 				try {
 					st.close();
 				} catch (SQLException e) {
 					e.printStackTrace();
 				}

 			if (conexion != null)
 				conexion.freeConnection();
 		}

 		// reseteo el datastore y recupero los atributos existenes para el
 		// proyecto
 		reset();
 		retrieve( "atributos_entidad.objeto_id = " + Integer.toString(p_objeto_id) 
 				+ " and atributos_entidad.tipo_objeto = \" TABLA\""
 				+ " and atributos_entidad.nombre_objeto = \""+ p_tabla + "\"");

 	}
 	
 	/**
 	 * Inserta todos los atributos definidos para una entidad según p_entidad_id 
 	 * Sólo completa los atributos que faltan. Dejando intactos los atributos
 	 * existente para la entidad
 	 * 
 	 * @param p_entidad_id --> id de la entidad donde hay que insertar los atributos
 	 * @throws DataStoreException
 	 * @throws SQLException
 	 */
 	public void generaAtributosEntidad(int p_entidad_id) throws SQLException,
 			DataStoreException {

 		DBConnection conexion = null;
 		Statement st = null;
 		ResultSet r = null;
 		String SQL = null;
 		String roles = null;
 		boolean primero = true;

 		// recupero todos los posibles atributos para un proyecto y recorro
 		// buscando si ya existe o no
 		// si no existe lo inserto en el data store
 		try {
 			conexion = DBConnection.getConnection("infraestructura","infraestructura");
 			conexion.beginTransaction();

 			// recupero los roles de la entidad y armo un string con la lista de roles
 			SQL = " select rol from infraestructura.roles_entidad"
 				+ " where roles_entidad.entidad_id = " + Integer.toString(p_entidad_id);
 			
 			st = conexion.createStatement();
 			r = st.executeQuery(SQL);
 			while (r.next()) {
 				if (! primero)
 					roles = roles + ",";
 				else{
 					roles = "(";
 					primero = false;
 				}
 				roles = roles + "\"" + r.getString("rol") + "\"";
 			}
 			if (roles != null)
 				roles = roles + ")";
 			r.close();
 			st.close();

 			if (roles == null)
 				return;

 			// inserto todos los atributos faltantes
 			SQL = "insert into atributos_entidad (entidad_id,atributo_id) "
 				+ "select " + Integer.toString(p_entidad_id)  
 				+ ",atributo_id from infraestructura.atributos_rol "
 				+ "where ((rol is null and tipo_objeto is null and nombre_objeto is null) OR (rol in "+ roles + "))"
 				+ "  and atributo_id not in (select atributo_id from atributos_entidad "
                + "                        where entidad_id = " + Integer.toString(p_entidad_id) + ")";
 			st = conexion.createStatement();
 			st.executeUpdate(SQL);
 			
 			conexion.commit();

 		} catch (SQLException e) {
 			MessageLog.writeErrorMessage(e, null);
 			// además de escribir en el log mando mensaje a la página
 			conexion.rollback();
 			throw new SQLException("Error insertando atributos para la entidad: "
 					+ Integer.toString(p_entidad_id) +" --> "+ e.getMessage());
 		} finally {
 			if (st != null)
 				try {
 					st.close();
 				} catch (SQLException e) {
 					e.printStackTrace();
 				}

 			if (conexion != null)
 				conexion.freeConnection();
 		}

 		// reseteo el datastore y recupero los atributos existenes para el
 		// proyecto
 		reset();
 		retrieve("atributos_entidad.entidad_id = " + Integer.toString(p_entidad_id));

 	}
 	
 	/**
 	 * recupera los atributos de un objeto de la aplicación según la etiqueta pasada como parámetro
 	 * @param p_etiqueta --> Etiqueta a recuperar
 	 * @param p_objeto_id --> id del objeto
 	 * @param p_tabla --> tabla a la que pertenece el atributo
 	 * @throws DataStoreException 
 	 * @throws SQLException 
 	 */
 	public void recuperaAtributosEtiquetaObjetoAplicacion(String p_etiqueta,int p_objeto_id, String p_tabla) throws SQLException, DataStoreException {
 		String cond = null;
 		
 		reset();
 		
 		cond = "atributos_entidad.objeto_id = " + Integer.toString(p_objeto_id)
 			 + " and atributos_entidad.tipo_objeto = \"TABLA\""
 			 + " and atributos_entidad.nombre_objeto = \"" + p_tabla + "\"";
 		if (p_etiqueta == null || p_etiqueta.trim().length() == 0)
 			// la etiqueta es null o vacia, recupera lo sin etiqueta
 			cond = cond + " and clase_atributo_rol.etiqueta is null";
 		else
 			// la etiqueta es no nula recupera lo satributos de esta etiqueta
 			cond = cond + " and clase_atributo_rol.etiqueta = \"" + p_etiqueta + "\"";
 				
 		retrieve(cond);
 		gotoFirst();
 	}
 	
 	/**
 	 * recupera los atributos de una entidad según la etiqueta pasada como parámetro
 	 * @param p_etiqueta --> Etiqueta a recuperar
 	 * @param p_entidad_id --> id de la entidad
 	 * @throws DataStoreException 
 	 * @throws SQLException 
 	 */
 	public void recuperaAtributosEtiquetaEntidad(String p_etiqueta,int p_entidad_id) throws SQLException, DataStoreException {
 		String cond = null;
 		
 		reset();
 		
 		cond = "atributos_entidad.entidad_id = " + Integer.toString(p_entidad_id);
 		if (p_etiqueta == null || p_etiqueta.trim().length() == 0)
 			// la etiqueta es null o vacia, recupera lo sin etiqueta
 			cond = cond + " and clase_atributo_rol.etiqueta is null";
 		else
 			// la etiqueta es no nula recupera lo satributos de esta etiqueta
 			cond = cond + " and clase_atributo_rol.etiqueta = \"" + p_etiqueta + "\"";
 				
 		retrieve(cond);
 		gotoFirst();
 	}

 	/**
 	 * Recupera la lista completa de etiquetas para las definiciones de los atributos de una tabla (objeto de la aplicación)
 	 * Este procedimiento permite armar los labels de los botones para la carga de atributos
 	 * @param p_tabla
 	 * @return
 	 */
 	public Hashtable recuperaEtiquetasAtributosObjetoAplicacion(String p_tabla) throws SQLException {
 		Hashtable<Integer, String> etiquetas = new Hashtable<Integer, String>();
 		
 		DBConnection conexion = null;
 		Statement st = null;
 		ResultSet r = null;
 		String SQL = null;

 		// recupero todos los etiquetas para un objeto de la aplicación
 		try {
 			conexion = DBConnection.getConnection("infraestructura","infraestructura");

 			// recupeto todas las etiquetas para el objeto de la aplicación
 			SQL = "select distinct t2.etiqueta etiqueta"
 				+ "  from atributos_rol t1 "
 				+ "  left join clase_atributo_rol t2 using (clase_atributo_rol_id) "
 				+ "  where (tipo_objeto = \"TABLA\" and nombre_objeto = \""+ p_tabla + "\")"
 				+ "    or  (t1.rol is null and t1.tipo_objeto is null and t1.nombre_objeto is null) ";
 			st = conexion.createStatement();
 			r = st.executeQuery(SQL);
 			
 			while (r.next()){
 				String etiqueta = r.getString("etiqueta");
 				if (etiqueta == null)
 					etiqueta = " ";
 				etiquetas.put(Integer.valueOf(r.getRow()), etiqueta );
 			}
 			
 		} catch (SQLException e) {
 			MessageLog.writeErrorMessage(e, null);
 			// además de escribir en el log mando mensaje a la página
 			throw new SQLException("Error recuperando etiquetas de atributos para objeto: " + p_tabla + " --> "+e.getMessage());
 		} finally {
 			if (r != null) {
 				try {
 					r.close();
 				} catch (Exception ex) {
 				}
 			}

 			if (st != null)
 				try {
 					st.close();
 				} catch (SQLException e) {
 					e.printStackTrace();
 				}

 			if (conexion != null)
 				conexion.freeConnection();
 		}

 		return etiquetas;
 	}

 	/**
 	 * Recupera la lista completa de etiquetas para una Entidad. Para ello recupera todos los roles de la misma
 	 * Este procedimiento permite armar los labels de los botones para la carga de atributos a una entidad
 	 * @param p_tabla
 	 * @return
 	 */
 	public Hashtable recuperaEtiquetasAtributosEntidad(int p_entidad_id) throws SQLException {
 		Hashtable<Integer, String> etiquetas = new Hashtable<Integer, String>();
 		
 		DBConnection conexion = null;
 		Statement st = null;
 		ResultSet r = null;
 		String SQL = null;
 		String roles = null;
 		boolean primero = true;

 		// recupero todos los posibles atributos para una entidad
 		try {
 			conexion = DBConnection.getConnection("infraestructura","infraestructura");
 			
 			// recupero los roles de la entidad y armo un string con la lista de roles
 			SQL = " select rol from infraestructura.roles_entidad"
 				+ " where roles_entidad.entidad_id = " + Integer.toString(p_entidad_id);
 			
 			st = conexion.createStatement();
 			r = st.executeQuery(SQL);
 			while (r.next()) {
 				if (! primero)
 					roles = roles + ",";
 				else {
 					roles = "(";
 					primero = false;
 				}
 				roles = roles + "\"" + r.getString("rol") + "\"";
 			}
 			if (roles != null)
 				roles = roles + ")";
 			r.close();
 			st.close();
 			
 			// si no se recuperan roles la lista de etiquetas es vacía
 			if (roles == null)
 				return null;

 			// recupeto todas las etiquetas para el objeto de la aplicación
 			SQL = "select distinct t2.etiqueta etiqueta"
 				+ "  from atributos_rol t1 "
 				+ "  left join clase_atributo_rol t2 using (clase_atributo_rol_id) "
 				+ "  where t1.rol in " + roles
 				+ "    or  (t1.rol is null and t1.tipo_objeto is null and t1.nombre_objeto is null) ";
 			st = conexion.createStatement();
 			r = st.executeQuery(SQL);
 			
 			while (r.next()){
 				String etiqueta = r.getString("etiqueta");
 				if (etiqueta == null)
 					etiqueta = " ";
 				etiquetas.put(Integer.valueOf(r.getRow()), etiqueta );
 			}
 			
 		} catch (SQLException e) {
 			MessageLog.writeErrorMessage(e, null);
 			// además de escribir en el log mando mensaje a la página
 			throw new SQLException("Error recuperando etiquetas de atributos para entidad: " 
 					+ Integer.toString(p_entidad_id) + " --> "+e.getMessage());
 		} finally {
 			if (r != null) {
 				try {
 					r.close();
 				} catch (Exception ex) {
 				}
 			}

 			if (st != null)
 				try {
 					st.close();
 				} catch (SQLException e) {
 					e.printStackTrace();
 				}

 			if (conexion != null)
 				conexion.freeConnection();
 		}

 		return etiquetas;
 	}

 	//$ENDCUSTOMMETHODS$
     
}
