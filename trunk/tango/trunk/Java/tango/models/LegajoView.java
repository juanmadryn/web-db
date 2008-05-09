package tango.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * LegajoView: A SOFIA generated model
 */
public class LegajoView extends DataStore {

     //constants for columns
     public static final String LEGAJO_ID_LEGAJO="LEGAJO.ID_LEGAJO";
     public static final String LEGAJO_ID_TIPO_DOCUMENTO="LEGAJO.ID_TIPO_DOCUMENTO";
     public static final String LEGAJO_ID_LEGAJO_JEFE="LEGAJO.ID_LEGAJO_JEFE";
     public static final String LEGAJO_ID_PROVINCIA="LEGAJO.ID_PROVINCIA";
     public static final String LEGAJO_ID_MODELO_ASIENTO_SU="LEGAJO.ID_MODELO_ASIENTO_SU";
     public static final String LEGAJO_ID_NACIONALIDAD="LEGAJO.ID_NACIONALIDAD";
     public static final String LEGAJO_ID_EXPEDIDO_POR="LEGAJO.ID_EXPEDIDO_POR";
     public static final String LEGAJO_NRO_LEGAJO="LEGAJO.NRO_LEGAJO";
     public static final String LEGAJO_APELLIDO="LEGAJO.APELLIDO";
     public static final String LEGAJO_NOMBRE="LEGAJO.NOMBRE";
     public static final String LEGAJO_APELLIDO_CONYUGE="LEGAJO.APELLIDO_CONYUGE";
     public static final String LEGAJO_FECHA_NACIMIENTO="LEGAJO.FECHA_NACIMIENTO";
     public static final String LEGAJO_NRO_DOCUMENTO="LEGAJO.NRO_DOCUMENTO";
     public static final String LEGAJO_CALLE="LEGAJO.CALLE";
     public static final String LEGAJO_NRO_DOMIC="LEGAJO.NRO_DOMIC";
     public static final String LEGAJO_PISO="LEGAJO.PISO";
     public static final String LEGAJO_DEPARTAMENTO_DOMIC="LEGAJO.DEPARTAMENTO_DOMIC";
     public static final String LEGAJO_CODIGO_POSTAL="LEGAJO.CODIGO_POSTAL";
     public static final String LEGAJO_LOCALIDAD="LEGAJO.LOCALIDAD";
     public static final String LEGAJO_TAREA_HABITUAL="LEGAJO.TAREA_HABITUAL";
     public static final String LEGAJO_CUIL="LEGAJO.CUIL";
     public static final String LEGAJO_EMAIL="LEGAJO.EMAIL";
     public static final String LEGAJO_SEXO="LEGAJO.SEXO";
     public static final String LEGAJO_ESTADO_CIVIL="LEGAJO.ESTADO_CIVIL";
     public static final String LEGAJO_FOTO_LEGAJO="LEGAJO.FOTO_LEGAJO";
     public static final String LEGAJO_CONFIDENCIAL="LEGAJO.CONFIDENCIAL";
     public static final String LEGAJO_OBSERVACIONES="LEGAJO.OBSERVACIONES";
     public static final String LEGAJO_APELLIDO_MATERNO="LEGAJO.APELLIDO_MATERNO";
     public static final String LEGAJO_ID_COMUNA="LEGAJO.ID_COMUNA";
     public static final String LEGAJO_CA_83_IMPORT1="LEGAJO.CA_83_IMPORT1";
     public static final String LEGAJO_CA_83_IMPORT2="LEGAJO.CA_83_IMPORT2";
     public static final String LEGAJO_CA_83_IMPORT3="LEGAJO.CA_83_IMPORT3";
     public static final String LEGAJO_CA_83_IMPORT4="LEGAJO.CA_83_IMPORT4";
     public static final String LEGAJO_CA_83_IMPORT5="LEGAJO.CA_83_IMPORT5";
     public static final String LEGAJO_CA_83_DESC1="LEGAJO.CA_83_DESC1";
     public static final String LEGAJO_CA_83_DESC2="LEGAJO.CA_83_DESC2";
     public static final String LEGAJO_CA_83_DESC3="LEGAJO.CA_83_DESC3";
     public static final String LEGAJO_CA_83_DESC4="LEGAJO.CA_83_DESC4";
     public static final String LEGAJO_CA_83_DESC5="LEGAJO.CA_83_DESC5";
     public static final String LEGAJO_CA_83_ANTIGUE="LEGAJO.CA_83_ANTIGUE";
     public static final String LEGAJO_CA_83_ANTIG_ANT="LEGAJO.CA_83_ANTIG_ANT";
     public static final String LEGAJO_CA_83_EVALUAC="LEGAJO.CA_83_EVALUAC";
     public static final String LEGAJO_CA_83_ADI_FIJ="LEGAJO.CA_83_ADI_FIJ";
     public static final String LEGAJO_CA_83_TIP_SEG="LEGAJO.CA_83_TIP_SEG";
     public static final String LEGAJO_CA_83_SEG_VID="LEGAJO.CA_83_SEG_VID";
     public static final String LEGAJO_CA_83_NUM_JUB="LEGAJO.CA_83_NUM_JUB";
     public static final String LEGAJO_CA_83_N_INSCRIP="LEGAJO.CA_83_N_INSCRIP";
     /*public static final String TIPO_DOCUMENTO_TIPO_DOCUMENTO="TIPO_DOCUMENTO.TIPO_DOCUMENTO";
     public static final String TIPO_DOCUMENTO_DESC_DOCUMENTO="TIPO_DOCUMENTO.DESC_DOCUMENTO";*/
     public static final String LEGAJO_APEYNOM="LEGAJO.APEYNOM";
     
     
     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new LegajoView object.
      * @param appName The SOFIA application name
      */
     public LegajoView (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new LegajoView object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public LegajoView (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("LEGAJO"),"LEGAJO");
          /*addTableAlias(computeTableName("TIPO_DOCUMENTO"),"TIPO_DOCUMENTO");
          addTableAlias(computeTableName("INGRESO_EGRESO"),null);*/

          //add columns
          addColumn(computeTableName("LEGAJO"),"ID_LEGAJO",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_LEGAJO);
          addColumn(computeTableName("LEGAJO"),"ID_TIPO_DOCUMENTO",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_TIPO_DOCUMENTO);
          addColumn(computeTableName("LEGAJO"),"ID_LEGAJO_JEFE",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_LEGAJO_JEFE);
          addColumn(computeTableName("LEGAJO"),"ID_PROVINCIA",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_PROVINCIA);
          addColumn(computeTableName("LEGAJO"),"ID_MODELO_ASIENTO_SU",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_MODELO_ASIENTO_SU);
          addColumn(computeTableName("LEGAJO"),"ID_NACIONALIDAD",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_NACIONALIDAD);
          addColumn(computeTableName("LEGAJO"),"ID_EXPEDIDO_POR",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_EXPEDIDO_POR);
          addColumn(computeTableName("LEGAJO"),"NRO_LEGAJO",DataStore.DATATYPE_INT,false,false,LEGAJO_NRO_LEGAJO);
          addColumn(computeTableName("LEGAJO"),"APELLIDO",DataStore.DATATYPE_STRING,false,false,LEGAJO_APELLIDO);
          addColumn(computeTableName("LEGAJO"),"NOMBRE",DataStore.DATATYPE_STRING,false,false,LEGAJO_NOMBRE);
          addColumn(computeTableName("LEGAJO"),"APELLIDO_CONYUGE",DataStore.DATATYPE_STRING,false,false,LEGAJO_APELLIDO_CONYUGE);
          addColumn(computeTableName("LEGAJO"),"FECHA_NACIMIENTO",DataStore.DATATYPE_DATETIME,false,false,LEGAJO_FECHA_NACIMIENTO);
          addColumn(computeTableName("LEGAJO"),"NRO_DOCUMENTO",DataStore.DATATYPE_INT,false,false,LEGAJO_NRO_DOCUMENTO);
          addColumn(computeTableName("LEGAJO"),"CALLE",DataStore.DATATYPE_STRING,false,false,LEGAJO_CALLE);
          addColumn(computeTableName("LEGAJO"),"NRO_DOMIC",DataStore.DATATYPE_STRING,false,false,LEGAJO_NRO_DOMIC);
          addColumn(computeTableName("LEGAJO"),"PISO",DataStore.DATATYPE_STRING,false,false,LEGAJO_PISO);
          addColumn(computeTableName("LEGAJO"),"DEPARTAMENTO_DOMIC",DataStore.DATATYPE_STRING,false,false,LEGAJO_DEPARTAMENTO_DOMIC);
          addColumn(computeTableName("LEGAJO"),"CODIGO_POSTAL",DataStore.DATATYPE_STRING,false,false,LEGAJO_CODIGO_POSTAL);
          addColumn(computeTableName("LEGAJO"),"LOCALIDAD",DataStore.DATATYPE_STRING,false,false,LEGAJO_LOCALIDAD);
          addColumn(computeTableName("LEGAJO"),"TAREA_HABITUAL",DataStore.DATATYPE_STRING,false,false,LEGAJO_TAREA_HABITUAL);
          addColumn(computeTableName("LEGAJO"),"CUIL",DataStore.DATATYPE_STRING,false,false,LEGAJO_CUIL);
          addColumn(computeTableName("LEGAJO"),"EMAIL",DataStore.DATATYPE_STRING,false,false,LEGAJO_EMAIL);
          addColumn(computeTableName("LEGAJO"),"SEXO",DataStore.DATATYPE_STRING,false,false,LEGAJO_SEXO);
          addColumn(computeTableName("LEGAJO"),"ESTADO_CIVIL",DataStore.DATATYPE_STRING,false,false,LEGAJO_ESTADO_CIVIL);
          addColumn(computeTableName("LEGAJO"),"FOTO_LEGAJO",DataStore.DATATYPE_BYTEARRAY,false,false,LEGAJO_FOTO_LEGAJO);
          addColumn(computeTableName("LEGAJO"),"CONFIDENCIAL",DataStore.DATATYPE_STRING,false,false,LEGAJO_CONFIDENCIAL);
          addColumn(computeTableName("LEGAJO"),"OBSERVACIONES",DataStore.DATATYPE_STRING,false,false,LEGAJO_OBSERVACIONES);
          addColumn(computeTableName("LEGAJO"),"APELLIDO_MATERNO",DataStore.DATATYPE_STRING,false,false,LEGAJO_APELLIDO_MATERNO);
          addColumn(computeTableName("LEGAJO"),"ID_COMUNA",DataStore.DATATYPE_INT,false,false,LEGAJO_ID_COMUNA);
          addColumn(computeTableName("LEGAJO"),"CA_83_IMPORT1",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_IMPORT1);
          addColumn(computeTableName("LEGAJO"),"CA_83_IMPORT2",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_IMPORT2);
          addColumn(computeTableName("LEGAJO"),"CA_83_IMPORT3",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_IMPORT3);
          addColumn(computeTableName("LEGAJO"),"CA_83_IMPORT4",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_IMPORT4);
          addColumn(computeTableName("LEGAJO"),"CA_83_IMPORT5",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_IMPORT5);
          addColumn(computeTableName("LEGAJO"),"CA_83_DESC1",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_DESC1);
          addColumn(computeTableName("LEGAJO"),"CA_83_DESC2",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_DESC2);
          addColumn(computeTableName("LEGAJO"),"CA_83_DESC3",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_DESC3);
          addColumn(computeTableName("LEGAJO"),"CA_83_DESC4",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_DESC4);
          addColumn(computeTableName("LEGAJO"),"CA_83_DESC5",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_DESC5);
          addColumn(computeTableName("LEGAJO"),"CA_83_ANTIGUE",DataStore.DATATYPE_INT,false,false,LEGAJO_CA_83_ANTIGUE);
          addColumn(computeTableName("LEGAJO"),"CA_83_ANTIG_ANT",DataStore.DATATYPE_INT,false,false,LEGAJO_CA_83_ANTIG_ANT);
          addColumn(computeTableName("LEGAJO"),"CA_83_EVALUAC",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_EVALUAC);
          addColumn(computeTableName("LEGAJO"),"CA_83_ADI_FIJ",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_ADI_FIJ);
          addColumn(computeTableName("LEGAJO"),"CA_83_TIP_SEG",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_TIP_SEG);
          addColumn(computeTableName("LEGAJO"),"CA_83_SEG_VID",DataStore.DATATYPE_DOUBLE,false,false,LEGAJO_CA_83_SEG_VID);
          addColumn(computeTableName("LEGAJO"),"CA_83_NUM_JUB",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_NUM_JUB);
          addColumn(computeTableName("LEGAJO"),"CA_83_N_INSCRIP",DataStore.DATATYPE_STRING,false,false,LEGAJO_CA_83_N_INSCRIP);
          /*addColumn(computeTableName("TIPO_DOCUMENTO"),"TIPO_DOCUMENTO",DataStore.DATATYPE_STRING,false,false,TIPO_DOCUMENTO_TIPO_DOCUMENTO);
          addColumn(computeTableName("TIPO_DOCUMENTO"),"DESC_DOCUMENTO",DataStore.DATATYPE_STRING,false,false,TIPO_DOCUMENTO_DESC_DOCUMENTO);*/

          addBucket(LEGAJO_APEYNOM, DataStore.DATATYPE_STRING);
          
          //add joins
          /*addJoin(computeTableAndFieldName("LEGAJO.ID_TIPO_DOCUMENTO"),computeTableAndFieldName("TIPO_DOCUMENTO.ID_TIPO_DOCUMENTO"),true);
          addJoin(computeTableAndFieldName("LEGAJO.ID_LEGAJO"),computeTableAndFieldName("INGRESO_EGRESO.ID_LEGAJO"),false);*/

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the LEGAJO.ID_LEGAJO column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdLegajo() throws DataStoreException {
          return  getInt(LEGAJO_ID_LEGAJO);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_LEGAJO column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdLegajo(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_LEGAJO);
     }

     /**
      * Set the value of the LEGAJO.ID_LEGAJO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdLegajo(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_LEGAJO, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_LEGAJO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_TIPO_DOCUMENTO column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdTipoDocumento() throws DataStoreException {
          return  getInt(LEGAJO_ID_TIPO_DOCUMENTO);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_TIPO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdTipoDocumento(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_TIPO_DOCUMENTO);
     }

     /**
      * Set the value of the LEGAJO.ID_TIPO_DOCUMENTO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdTipoDocumento(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_TIPO_DOCUMENTO, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_TIPO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdTipoDocumento(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_TIPO_DOCUMENTO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_LEGAJO_JEFE column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdLegajoJefe() throws DataStoreException {
          return  getInt(LEGAJO_ID_LEGAJO_JEFE);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_LEGAJO_JEFE column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdLegajoJefe(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_LEGAJO_JEFE);
     }

     /**
      * Set the value of the LEGAJO.ID_LEGAJO_JEFE column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdLegajoJefe(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_LEGAJO_JEFE, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_LEGAJO_JEFE column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdLegajoJefe(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_LEGAJO_JEFE, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_PROVINCIA column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdProvincia() throws DataStoreException {
          return  getInt(LEGAJO_ID_PROVINCIA);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_PROVINCIA column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdProvincia(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_PROVINCIA);
     }

     /**
      * Set the value of the LEGAJO.ID_PROVINCIA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdProvincia(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_PROVINCIA, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_PROVINCIA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdProvincia(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_PROVINCIA, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_MODELO_ASIENTO_SU column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdModeloAsientoSu() throws DataStoreException {
          return  getInt(LEGAJO_ID_MODELO_ASIENTO_SU);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_MODELO_ASIENTO_SU column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdModeloAsientoSu(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_MODELO_ASIENTO_SU);
     }

     /**
      * Set the value of the LEGAJO.ID_MODELO_ASIENTO_SU column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdModeloAsientoSu(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_MODELO_ASIENTO_SU, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_MODELO_ASIENTO_SU column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdModeloAsientoSu(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_MODELO_ASIENTO_SU, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_NACIONALIDAD column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdNacionalidad() throws DataStoreException {
          return  getInt(LEGAJO_ID_NACIONALIDAD);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_NACIONALIDAD column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdNacionalidad(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_NACIONALIDAD);
     }

     /**
      * Set the value of the LEGAJO.ID_NACIONALIDAD column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdNacionalidad(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_NACIONALIDAD, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_NACIONALIDAD column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdNacionalidad(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_NACIONALIDAD, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_EXPEDIDO_POR column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdExpedidoPor() throws DataStoreException {
          return  getInt(LEGAJO_ID_EXPEDIDO_POR);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_EXPEDIDO_POR column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdExpedidoPor(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_EXPEDIDO_POR);
     }

     /**
      * Set the value of the LEGAJO.ID_EXPEDIDO_POR column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdExpedidoPor(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_EXPEDIDO_POR, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_EXPEDIDO_POR column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdExpedidoPor(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_EXPEDIDO_POR, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_LEGAJO column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoNroLegajo() throws DataStoreException {
          return  getInt(LEGAJO_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_LEGAJO column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoNroLegajo(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_NRO_LEGAJO);
     }

     /**
      * Set the value of the LEGAJO.NRO_LEGAJO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroLegajo(int newValue) throws DataStoreException {
          setInt(LEGAJO_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the LEGAJO.NRO_LEGAJO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_NRO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellido() throws DataStoreException {
          return  getString(LEGAJO_APELLIDO);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellido(int row) throws DataStoreException {
          return  getString(row,LEGAJO_APELLIDO);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellido(String newValue) throws DataStoreException {
          setString(LEGAJO_APELLIDO, newValue);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellido(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_APELLIDO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.NOMBRE column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoNombre() throws DataStoreException {
          return  getString(LEGAJO_NOMBRE);
     }

     /**
      * Retrieve the value of the LEGAJO.NOMBRE column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoNombre(int row) throws DataStoreException {
          return  getString(row,LEGAJO_NOMBRE);
     }

     /**
      * Set the value of the LEGAJO.NOMBRE column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNombre(String newValue) throws DataStoreException {
          setString(LEGAJO_NOMBRE, newValue);
     }

     /**
      * Set the value of the LEGAJO.NOMBRE column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNombre(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO_CONYUGE column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellidoConyuge() throws DataStoreException {
          return  getString(LEGAJO_APELLIDO_CONYUGE);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO_CONYUGE column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellidoConyuge(int row) throws DataStoreException {
          return  getString(row,LEGAJO_APELLIDO_CONYUGE);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO_CONYUGE column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellidoConyuge(String newValue) throws DataStoreException {
          setString(LEGAJO_APELLIDO_CONYUGE, newValue);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO_CONYUGE column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellidoConyuge(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_APELLIDO_CONYUGE, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.FECHA_NACIMIENTO column for the current row.
      * @return java.sql.Timestamp
      * @throws DataStoreException
      */ 
     public java.sql.Timestamp getLegajoFechaNacimiento() throws DataStoreException {
          return  getDateTime(LEGAJO_FECHA_NACIMIENTO);
     }

     /**
      * Retrieve the value of the LEGAJO.FECHA_NACIMIENTO column for the specified row.
      * @param row which row in the table
      * @return java.sql.Timestamp
      * @throws DataStoreException
      */ 
     public java.sql.Timestamp getLegajoFechaNacimiento(int row) throws DataStoreException {
          return  getDateTime(row,LEGAJO_FECHA_NACIMIENTO);
     }

     /**
      * Set the value of the LEGAJO.FECHA_NACIMIENTO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoFechaNacimiento(java.sql.Timestamp newValue) throws DataStoreException {
          setDateTime(LEGAJO_FECHA_NACIMIENTO, newValue);
     }

     /**
      * Set the value of the LEGAJO.FECHA_NACIMIENTO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoFechaNacimiento(int row,java.sql.Timestamp newValue) throws DataStoreException {
          setDateTime(row,LEGAJO_FECHA_NACIMIENTO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_DOCUMENTO column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoNroDocumento() throws DataStoreException {
          return  getInt(LEGAJO_NRO_DOCUMENTO);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoNroDocumento(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_NRO_DOCUMENTO);
     }

     /**
      * Set the value of the LEGAJO.NRO_DOCUMENTO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroDocumento(int newValue) throws DataStoreException {
          setInt(LEGAJO_NRO_DOCUMENTO, newValue);
     }

     /**
      * Set the value of the LEGAJO.NRO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroDocumento(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_NRO_DOCUMENTO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CALLE column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCalle() throws DataStoreException {
          return  getString(LEGAJO_CALLE);
     }

     /**
      * Retrieve the value of the LEGAJO.CALLE column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCalle(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CALLE);
     }

     /**
      * Set the value of the LEGAJO.CALLE column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCalle(String newValue) throws DataStoreException {
          setString(LEGAJO_CALLE, newValue);
     }

     /**
      * Set the value of the LEGAJO.CALLE column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCalle(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CALLE, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_DOMIC column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoNroDomic() throws DataStoreException {
          return  getString(LEGAJO_NRO_DOMIC);
     }

     /**
      * Retrieve the value of the LEGAJO.NRO_DOMIC column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoNroDomic(int row) throws DataStoreException {
          return  getString(row,LEGAJO_NRO_DOMIC);
     }

     /**
      * Set the value of the LEGAJO.NRO_DOMIC column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroDomic(String newValue) throws DataStoreException {
          setString(LEGAJO_NRO_DOMIC, newValue);
     }

     /**
      * Set the value of the LEGAJO.NRO_DOMIC column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoNroDomic(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_NRO_DOMIC, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.PISO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoPiso() throws DataStoreException {
          return  getString(LEGAJO_PISO);
     }

     /**
      * Retrieve the value of the LEGAJO.PISO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoPiso(int row) throws DataStoreException {
          return  getString(row,LEGAJO_PISO);
     }

     /**
      * Set the value of the LEGAJO.PISO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoPiso(String newValue) throws DataStoreException {
          setString(LEGAJO_PISO, newValue);
     }

     /**
      * Set the value of the LEGAJO.PISO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoPiso(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_PISO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.DEPARTAMENTO_DOMIC column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoDepartamentoDomic() throws DataStoreException {
          return  getString(LEGAJO_DEPARTAMENTO_DOMIC);
     }

     /**
      * Retrieve the value of the LEGAJO.DEPARTAMENTO_DOMIC column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoDepartamentoDomic(int row) throws DataStoreException {
          return  getString(row,LEGAJO_DEPARTAMENTO_DOMIC);
     }

     /**
      * Set the value of the LEGAJO.DEPARTAMENTO_DOMIC column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoDepartamentoDomic(String newValue) throws DataStoreException {
          setString(LEGAJO_DEPARTAMENTO_DOMIC, newValue);
     }

     /**
      * Set the value of the LEGAJO.DEPARTAMENTO_DOMIC column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoDepartamentoDomic(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_DEPARTAMENTO_DOMIC, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CODIGO_POSTAL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCodigoPostal() throws DataStoreException {
          return  getString(LEGAJO_CODIGO_POSTAL);
     }

     /**
      * Retrieve the value of the LEGAJO.CODIGO_POSTAL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCodigoPostal(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CODIGO_POSTAL);
     }

     /**
      * Set the value of the LEGAJO.CODIGO_POSTAL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCodigoPostal(String newValue) throws DataStoreException {
          setString(LEGAJO_CODIGO_POSTAL, newValue);
     }

     /**
      * Set the value of the LEGAJO.CODIGO_POSTAL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCodigoPostal(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CODIGO_POSTAL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.LOCALIDAD column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoLocalidad() throws DataStoreException {
          return  getString(LEGAJO_LOCALIDAD);
     }

     /**
      * Retrieve the value of the LEGAJO.LOCALIDAD column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoLocalidad(int row) throws DataStoreException {
          return  getString(row,LEGAJO_LOCALIDAD);
     }

     /**
      * Set the value of the LEGAJO.LOCALIDAD column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoLocalidad(String newValue) throws DataStoreException {
          setString(LEGAJO_LOCALIDAD, newValue);
     }

     /**
      * Set the value of the LEGAJO.LOCALIDAD column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoLocalidad(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_LOCALIDAD, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.TAREA_HABITUAL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoTareaHabitual() throws DataStoreException {
          return  getString(LEGAJO_TAREA_HABITUAL);
     }

     /**
      * Retrieve the value of the LEGAJO.TAREA_HABITUAL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoTareaHabitual(int row) throws DataStoreException {
          return  getString(row,LEGAJO_TAREA_HABITUAL);
     }

     /**
      * Set the value of the LEGAJO.TAREA_HABITUAL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoTareaHabitual(String newValue) throws DataStoreException {
          setString(LEGAJO_TAREA_HABITUAL, newValue);
     }

     /**
      * Set the value of the LEGAJO.TAREA_HABITUAL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoTareaHabitual(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_TAREA_HABITUAL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CUIL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCuil() throws DataStoreException {
          return  getString(LEGAJO_CUIL);
     }

     /**
      * Retrieve the value of the LEGAJO.CUIL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCuil(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CUIL);
     }

     /**
      * Set the value of the LEGAJO.CUIL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCuil(String newValue) throws DataStoreException {
          setString(LEGAJO_CUIL, newValue);
     }

     /**
      * Set the value of the LEGAJO.CUIL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCuil(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CUIL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.EMAIL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoEmail() throws DataStoreException {
          return  getString(LEGAJO_EMAIL);
     }

     /**
      * Retrieve the value of the LEGAJO.EMAIL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoEmail(int row) throws DataStoreException {
          return  getString(row,LEGAJO_EMAIL);
     }

     /**
      * Set the value of the LEGAJO.EMAIL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoEmail(String newValue) throws DataStoreException {
          setString(LEGAJO_EMAIL, newValue);
     }

     /**
      * Set the value of the LEGAJO.EMAIL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoEmail(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_EMAIL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.SEXO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoSexo() throws DataStoreException {
          return  getString(LEGAJO_SEXO);
     }

     /**
      * Retrieve the value of the LEGAJO.SEXO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoSexo(int row) throws DataStoreException {
          return  getString(row,LEGAJO_SEXO);
     }

     /**
      * Set the value of the LEGAJO.SEXO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoSexo(String newValue) throws DataStoreException {
          setString(LEGAJO_SEXO, newValue);
     }

     /**
      * Set the value of the LEGAJO.SEXO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoSexo(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_SEXO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ESTADO_CIVIL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoEstadoCivil() throws DataStoreException {
          return  getString(LEGAJO_ESTADO_CIVIL);
     }

     /**
      * Retrieve the value of the LEGAJO.ESTADO_CIVIL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoEstadoCivil(int row) throws DataStoreException {
          return  getString(row,LEGAJO_ESTADO_CIVIL);
     }

     /**
      * Set the value of the LEGAJO.ESTADO_CIVIL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoEstadoCivil(String newValue) throws DataStoreException {
          setString(LEGAJO_ESTADO_CIVIL, newValue);
     }

     /**
      * Set the value of the LEGAJO.ESTADO_CIVIL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoEstadoCivil(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_ESTADO_CIVIL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.FOTO_LEGAJO column for the current row.
      * @return byte[]
      * @throws DataStoreException
      */ 
     public byte[] getLegajoFotoLegajo() throws DataStoreException {
          return  getByteArray(LEGAJO_FOTO_LEGAJO);
     }

     /**
      * Retrieve the value of the LEGAJO.FOTO_LEGAJO column for the specified row.
      * @param row which row in the table
      * @return byte[]
      * @throws DataStoreException
      */ 
     public byte[] getLegajoFotoLegajo(int row) throws DataStoreException {
          return  getByteArray(row,LEGAJO_FOTO_LEGAJO);
     }

     /**
      * Set the value of the LEGAJO.FOTO_LEGAJO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoFotoLegajo(byte[] newValue) throws DataStoreException {
          setByteArray(LEGAJO_FOTO_LEGAJO, newValue);
     }

     /**
      * Set the value of the LEGAJO.FOTO_LEGAJO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoFotoLegajo(int row,byte[] newValue) throws DataStoreException {
          setByteArray(row,LEGAJO_FOTO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CONFIDENCIAL column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoConfidencial() throws DataStoreException {
          return  getString(LEGAJO_CONFIDENCIAL);
     }

     /**
      * Retrieve the value of the LEGAJO.CONFIDENCIAL column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoConfidencial(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CONFIDENCIAL);
     }

     /**
      * Set the value of the LEGAJO.CONFIDENCIAL column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoConfidencial(String newValue) throws DataStoreException {
          setString(LEGAJO_CONFIDENCIAL, newValue);
     }

     /**
      * Set the value of the LEGAJO.CONFIDENCIAL column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoConfidencial(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CONFIDENCIAL, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.OBSERVACIONES column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoObservaciones() throws DataStoreException {
          return  getString(LEGAJO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the LEGAJO.OBSERVACIONES column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoObservaciones(int row) throws DataStoreException {
          return  getString(row,LEGAJO_OBSERVACIONES);
     }

     /**
      * Set the value of the LEGAJO.OBSERVACIONES column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoObservaciones(String newValue) throws DataStoreException {
          setString(LEGAJO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the LEGAJO.OBSERVACIONES column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO_MATERNO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellidoMaterno() throws DataStoreException {
          return  getString(LEGAJO_APELLIDO_MATERNO);
     }

     /**
      * Retrieve the value of the LEGAJO.APELLIDO_MATERNO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApellidoMaterno(int row) throws DataStoreException {
          return  getString(row,LEGAJO_APELLIDO_MATERNO);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO_MATERNO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellidoMaterno(String newValue) throws DataStoreException {
          setString(LEGAJO_APELLIDO_MATERNO, newValue);
     }

     /**
      * Set the value of the LEGAJO.APELLIDO_MATERNO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApellidoMaterno(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_APELLIDO_MATERNO, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_COMUNA column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdComuna() throws DataStoreException {
          return  getInt(LEGAJO_ID_COMUNA);
     }

     /**
      * Retrieve the value of the LEGAJO.ID_COMUNA column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoIdComuna(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_ID_COMUNA);
     }

     /**
      * Set the value of the LEGAJO.ID_COMUNA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdComuna(int newValue) throws DataStoreException {
          setInt(LEGAJO_ID_COMUNA, newValue);
     }

     /**
      * Set the value of the LEGAJO.ID_COMUNA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoIdComuna(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_ID_COMUNA, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT1 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import1() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_IMPORT1);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT1 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import1(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_IMPORT1);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import1(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_IMPORT1, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import1(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_IMPORT1, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT2 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import2() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_IMPORT2);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT2 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import2(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_IMPORT2);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import2(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_IMPORT2, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import2(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_IMPORT2, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT3 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import3() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_IMPORT3);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT3 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import3(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_IMPORT3);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT3 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import3(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_IMPORT3, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT3 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import3(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_IMPORT3, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT4 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import4() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_IMPORT4);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT4 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import4(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_IMPORT4);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT4 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import4(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_IMPORT4, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT4 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import4(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_IMPORT4, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT5 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import5() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_IMPORT5);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_IMPORT5 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Import5(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_IMPORT5);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import5(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_IMPORT5, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_IMPORT5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Import5(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_IMPORT5, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC1 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc1() throws DataStoreException {
          return  getString(LEGAJO_CA_83_DESC1);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC1 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc1(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_DESC1);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc1(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_DESC1, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc1(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_DESC1, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC2 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc2() throws DataStoreException {
          return  getString(LEGAJO_CA_83_DESC2);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC2 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc2(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_DESC2);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc2(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_DESC2, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc2(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_DESC2, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC3 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc3() throws DataStoreException {
          return  getString(LEGAJO_CA_83_DESC3);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC3 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc3(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_DESC3);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC3 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc3(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_DESC3, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC3 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc3(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_DESC3, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC4 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc4() throws DataStoreException {
          return  getString(LEGAJO_CA_83_DESC4);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC4 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc4(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_DESC4);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC4 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc4(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_DESC4, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC4 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc4(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_DESC4, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC5 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc5() throws DataStoreException {
          return  getString(LEGAJO_CA_83_DESC5);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC5 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83Desc5(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_DESC5);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc5(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_DESC5, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Desc5(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_DESC5, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ANTIGUE column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoCa83Antigue() throws DataStoreException {
          return  getInt(LEGAJO_CA_83_ANTIGUE);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ANTIGUE column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoCa83Antigue(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_CA_83_ANTIGUE);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ANTIGUE column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Antigue(int newValue) throws DataStoreException {
          setInt(LEGAJO_CA_83_ANTIGUE, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ANTIGUE column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Antigue(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_CA_83_ANTIGUE, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ANTIG_ANT column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoCa83AntigAnt() throws DataStoreException {
          return  getInt(LEGAJO_CA_83_ANTIG_ANT);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ANTIG_ANT column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLegajoCa83AntigAnt(int row) throws DataStoreException {
          return  getInt(row,LEGAJO_CA_83_ANTIG_ANT);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ANTIG_ANT column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83AntigAnt(int newValue) throws DataStoreException {
          setInt(LEGAJO_CA_83_ANTIG_ANT, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ANTIG_ANT column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83AntigAnt(int row,int newValue) throws DataStoreException {
          setInt(row,LEGAJO_CA_83_ANTIG_ANT, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_EVALUAC column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Evaluac() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_EVALUAC);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_EVALUAC column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83Evaluac(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_EVALUAC);
     }

     /**
      * Set the value of the LEGAJO.CA_83_EVALUAC column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Evaluac(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_EVALUAC, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_EVALUAC column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83Evaluac(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_EVALUAC, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ADI_FIJ column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83AdiFij() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_ADI_FIJ);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_ADI_FIJ column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83AdiFij(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_ADI_FIJ);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ADI_FIJ column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83AdiFij(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_ADI_FIJ, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_ADI_FIJ column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83AdiFij(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_ADI_FIJ, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_TIP_SEG column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83TipSeg() throws DataStoreException {
          return  getString(LEGAJO_CA_83_TIP_SEG);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_TIP_SEG column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83TipSeg(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_TIP_SEG);
     }

     /**
      * Set the value of the LEGAJO.CA_83_TIP_SEG column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83TipSeg(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_TIP_SEG, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_TIP_SEG column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83TipSeg(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_TIP_SEG, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_SEG_VID column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83SegVid() throws DataStoreException {
          return  getDouble(LEGAJO_CA_83_SEG_VID);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_SEG_VID column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getLegajoCa83SegVid(int row) throws DataStoreException {
          return  getDouble(row,LEGAJO_CA_83_SEG_VID);
     }

     /**
      * Set the value of the LEGAJO.CA_83_SEG_VID column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83SegVid(double newValue) throws DataStoreException {
          setDouble(LEGAJO_CA_83_SEG_VID, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_SEG_VID column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83SegVid(int row,double newValue) throws DataStoreException {
          setDouble(row,LEGAJO_CA_83_SEG_VID, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_NUM_JUB column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83NumJub() throws DataStoreException {
          return  getString(LEGAJO_CA_83_NUM_JUB);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_NUM_JUB column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83NumJub(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_NUM_JUB);
     }

     /**
      * Set the value of the LEGAJO.CA_83_NUM_JUB column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83NumJub(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_NUM_JUB, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_NUM_JUB column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83NumJub(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_NUM_JUB, newValue);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_N_INSCRIP column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83NInscrip() throws DataStoreException {
          return  getString(LEGAJO_CA_83_N_INSCRIP);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_N_INSCRIP column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoCa83NInscrip(int row) throws DataStoreException {
          return  getString(row,LEGAJO_CA_83_N_INSCRIP);
     }

     /**
      * Set the value of the LEGAJO.CA_83_N_INSCRIP column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83NInscrip(String newValue) throws DataStoreException {
          setString(LEGAJO_CA_83_N_INSCRIP, newValue);
     }

     /**
      * Set the value of the LEGAJO.CA_83_N_INSCRIP column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoCa83NInscrip(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_CA_83_N_INSCRIP, newValue);
     }

     /**
      * Retrieve the value of the TIPO_DOCUMENTO.TIPO_DOCUMENTO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     /*public String getTipoDocumentoTipoDocumento() throws DataStoreException {
          return  getString(TIPO_DOCUMENTO_TIPO_DOCUMENTO);
     }*/

     /**
      * Retrieve the value of the TIPO_DOCUMENTO.TIPO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     /*public String getTipoDocumentoTipoDocumento(int row) throws DataStoreException {
          return  getString(row,TIPO_DOCUMENTO_TIPO_DOCUMENTO);
     }*/

     /**
      * Set the value of the TIPO_DOCUMENTO.TIPO_DOCUMENTO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     /*public void setTipoDocumentoTipoDocumento(String newValue) throws DataStoreException {
          setString(TIPO_DOCUMENTO_TIPO_DOCUMENTO, newValue);
     }*/

     /**
      * Set the value of the TIPO_DOCUMENTO.TIPO_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     /*public void setTipoDocumentoTipoDocumento(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_DOCUMENTO_TIPO_DOCUMENTO, newValue);
     }*/

     /**
      * Retrieve the value of the TIPO_DOCUMENTO.DESC_DOCUMENTO column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     /*public String getTipoDocumentoDescDocumento() throws DataStoreException {
          return  getString(TIPO_DOCUMENTO_DESC_DOCUMENTO);
     }*/

     /**
      * Retrieve the value of the TIPO_DOCUMENTO.DESC_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     /*public String getTipoDocumentoDescDocumento(int row) throws DataStoreException {
          return  getString(row,TIPO_DOCUMENTO_DESC_DOCUMENTO);
     }*/

     /**
      * Set the value of the TIPO_DOCUMENTO.DESC_DOCUMENTO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     /*public void setTipoDocumentoDescDocumento(String newValue) throws DataStoreException {
          setString(TIPO_DOCUMENTO_DESC_DOCUMENTO, newValue);
     }*/

     /**
      * Set the value of the TIPO_DOCUMENTO.DESC_DOCUMENTO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     /*public void setTipoDocumentoDescDocumento(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_DOCUMENTO_DESC_DOCUMENTO, newValue);
     }*/
     
     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC5 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApeynom() throws DataStoreException {
          return  getString(LEGAJO_APELLIDO) + ", " + getString(LEGAJO_NOMBRE);
     }

     /**
      * Retrieve the value of the LEGAJO.CA_83_DESC5 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLegajoApeynom(int row) throws DataStoreException {
          return  getString(row,LEGAJO_APELLIDO) + ", " + getString(row,LEGAJO_NOMBRE);
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApeynom(String newValue) throws DataStoreException {
          setString(LEGAJO_APEYNOM, getString(LEGAJO_APELLIDO) + ", " + getString(LEGAJO_NOMBRE));
     }

     /**
      * Set the value of the LEGAJO.CA_83_DESC5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLegajoApeynom(int row,String newValue) throws DataStoreException {
          setString(row,LEGAJO_APEYNOM, getString(row,LEGAJO_APELLIDO) + ", " + getString(row,LEGAJO_NOMBRE));
     }
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
