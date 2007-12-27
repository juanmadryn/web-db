package partesMO.models;

import infraestructura.models.BaseModel;
import infraestructura.models.feriadosModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import proyectos.models.TareasProyectoModel;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * PartesMoModel: A SOFIA generated model
 */
public class PartesMoModel extends BaseModel {

     /**
	 * 
	 */
	private static final long serialVersionUID = -8639292475993325297L;
	//constants for columns
     public static final String PARTES_MO_PARTE_ID="partes_mo.parte_id";
     public static final String PARTES_MO_FECHA="partes_mo.fecha";
     public static final String PARTES_MO_HORA_DESDE="partes_mo.hora_desde";
     public static final String PARTES_MO_HORA_HASTA="partes_mo.hora_hasta";
     public static final String PARTES_MO_HORAS="partes_mo.horas";
     public static final String PARTES_MO_LOTE_ID="partes_mo.lote_id";
     public static final String PARTES_MO_SUPERVISOR="partes_mo.supervisor";
     public static final String PARTES_MO_CATEGORIA="partes_mo.categoria";
     public static final String PARTES_MO_DESC_CATEGORIA="partes_mo.desc_categoria";
     public static final String PARTES_MO_SECTOR_ID="partes_mo.sector_id";
     public static final String PARTES_MO_PROYECTO_ID="partes_mo.proyecto_id";
     public static final String PARTES_MO_TAREA_ID="partes_mo.tarea_id";
     public static final String PARTES_MO_PERSONAL_ID="partes_mo.personal_id";
     public static final String PARTES_MO_NRO_LEGAJO="partes_mo.nro_legajo";
     public static final String PARTES_MO_APEYNOM="partes_mo.apeynom";
     public static final String PARTES_MO_ESTADO="partes_mo.estado";
     public static final String SECTOR_TRABAJO_NOMBRE="sector_trabajo.nombre";
     public static final String SUPERVISORES_APEYNOM="supervisores.apeynom";
     public static final String ESTADOS_NOMBRE="estados.nombre";
     public static final String LOTE_CARGA_PARTES_MO_ESTADO="lote_carga_partes_mo.estado";
     public static final String LOTE_CARGA_PARTES_MO_FECHA_ALTA="lote_carga_partes_mo.fecha_alta";
     public static final String LOTE_CARGA_PARTES_MO_FECHA_CIERRE="lote_carga_partes_mo.fecha_cierre";
     public static final String PROYECTOS_PROYECTO="proyectos.proyecto";
     public static final String PROYECTOS_NOMBRE="proyectos.nombre";
     public static final String TAREAS_PROYECTO_NOMBRE="tareas_proyecto.nombre";
     public static final String TAREAS_PROYECTOS_DESCRIPCION="tareas_proyecto.descripcion";

     //constants for buckets
     public static final String MENSAJE_ERROR="mensaje_error";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
	 private Connection _connTango = null;
	 private String _estado_inicial = null;
	 private Props  _p = null;
	 private String _driverTango = null;
	 private String _urlTango = null;
	 private String _userTango = null;
	 private String _passWordTango = null;
	 
	 private double _horas1 = 0;
	 private double _horas2 = 0;
	 private double _normales1 = 0;
	 private double _normales2 = 0;
	 private double _al_50_1 = 0;
	 private double _al_50_2 = 0;
	 private double _al_100_1 = 0;
	 private double _al_100_2 = 0;
	 private double _nocturnas1 = 0;
	 private double _nocturnas2 = 0;
	 
	 private boolean _validarPartes = true;
	 	 
	 //$ENDCUSTOMVARS$
     
     /**
      * Create a new PartesMoModel object.
      * @param appName The SOFIA application name
      */
     public PartesMoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new PartesMoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public PartesMoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("partes_mo"),"partes_mo");
               addTableAlias(computeTableName("lote_carga_partes_mo"),"lote_carga_partes_mo");
               addTableAlias(computeTableName("sector_trabajo"),"sector_trabajo");
               addTableAlias(computeTableName("supervisores"),"supervisores");
               addTableAlias(computeTableName("infraestructura.estados"),"estados");
               addTableAlias(computeTableName("proyectos.proyectos"),"proyectos");
               addTableAlias(computeTableName("proyectos.tareas_proyecto"),"tareas");

               //add columns
               addColumn(computeTableName("partes_mo"),"parte_id",DataStore.DATATYPE_INT,true,true,PARTES_MO_PARTE_ID);
               addColumn(computeTableName("partes_mo"),"fecha",DataStore.DATATYPE_DATE,false,true,PARTES_MO_FECHA);
               addColumn(computeTableName("partes_mo"),"hora_desde",DataStore.DATATYPE_STRING,false,true,PARTES_MO_HORA_DESDE);
               addColumn(computeTableName("partes_mo"),"hora_hasta",DataStore.DATATYPE_STRING,false,true,PARTES_MO_HORA_HASTA);
               addColumn(computeTableName("partes_mo"),"horas",DataStore.DATATYPE_DOUBLE,false,true,PARTES_MO_HORAS);
               addColumn(computeTableName("partes_mo"),"lote_id",DataStore.DATATYPE_INT,false,true,PARTES_MO_LOTE_ID);
               addColumn(computeTableName("partes_mo"),"supervisor",DataStore.DATATYPE_INT,false,true,PARTES_MO_SUPERVISOR);
               addColumn(computeTableName("partes_mo"),"categoria",DataStore.DATATYPE_STRING,false,true,PARTES_MO_CATEGORIA);
               addColumn(computeTableName("partes_mo"),"desc_categoria",DataStore.DATATYPE_STRING,false,true,PARTES_MO_DESC_CATEGORIA);
               addColumn(computeTableName("partes_mo"),"sector_id",DataStore.DATATYPE_INT,false,true,PARTES_MO_SECTOR_ID);
               addColumn(computeTableName("partes_mo"),"proyecto_id",DataStore.DATATYPE_INT,false,true,PARTES_MO_PROYECTO_ID);
               addColumn(computeTableName("partes_mo"),"tarea_id",DataStore.DATATYPE_INT,false,true,PARTES_MO_TAREA_ID);
               addColumn(computeTableName("partes_mo"),"personal_id",DataStore.DATATYPE_INT,false,true,PARTES_MO_PERSONAL_ID);
               addColumn(computeTableName("partes_mo"),"nro_legajo",DataStore.DATATYPE_INT,false,true,PARTES_MO_NRO_LEGAJO);
               addColumn(computeTableName("partes_mo"),"apeynom",DataStore.DATATYPE_STRING,false,true,PARTES_MO_APEYNOM);
               addColumn(computeTableName("partes_mo"),"estado",DataStore.DATATYPE_STRING,false,true,PARTES_MO_ESTADO);
               addColumn(computeTableName("sector_trabajo"),"nombre",DataStore.DATATYPE_STRING,false,false,SECTOR_TRABAJO_NOMBRE);
               addColumn(computeTableName("supervisores"),"apeynom",DataStore.DATATYPE_STRING,true,false,SUPERVISORES_APEYNOM);
               addColumn(computeTableName("infraestructura.estados"),"nombre",DataStore.DATATYPE_STRING,false,false,ESTADOS_NOMBRE);
               addColumn(computeTableName("lote_carga_partes_mo"),"estado",DataStore.DATATYPE_STRING,false,false,LOTE_CARGA_PARTES_MO_ESTADO);
               addColumn(computeTableName("lote_carga_partes_mo"),"fecha_alta",DataStore.DATATYPE_DATE,false,false,LOTE_CARGA_PARTES_MO_FECHA_ALTA);
               addColumn(computeTableName("lote_carga_partes_mo"),"fecha_cierre",DataStore.DATATYPE_DATE,false,false,LOTE_CARGA_PARTES_MO_FECHA_CIERRE);
               addColumn(computeTableName("proyectos.proyectos"),"proyecto",DataStore.DATATYPE_STRING,false,false,PROYECTOS_PROYECTO);
               addColumn(computeTableName("proyectos.proyectos"),"nombre",DataStore.DATATYPE_STRING,false,false,PROYECTOS_NOMBRE);
               addColumn(computeTableName("tareas"),"nombre",DataStore.DATATYPE_STRING,false,false,TAREAS_PROYECTO_NOMBRE);
               addColumn(computeTableName("tareas"),"descripcion",DataStore.DATATYPE_STRING,false,false,TAREAS_PROYECTOS_DESCRIPCION);

               //add buckets
               addBucket(MENSAJE_ERROR,DataStore.DATATYPE_STRING);

               //add joins
               addJoin(computeTableAndFieldName("partes_mo.lote_id"),computeTableAndFieldName("lote_carga_partes_mo.lote_id"),true);
               addJoin(computeTableAndFieldName("partes_mo.supervisor"),computeTableAndFieldName("supervisores.nro_legajo"),true); // antes personal_id
               addJoin(computeTableAndFieldName("partes_mo.sector_id"),computeTableAndFieldName("sector_trabajo.sector_id"),true);
               addJoin(computeTableAndFieldName("partes_mo.estado"),computeTableAndFieldName("estados.estado"),true);
               addJoin(computeTableAndFieldName("partes_mo.proyecto_id"),computeTableAndFieldName("proyectos.proyecto_id"),true);
               addJoin(computeTableAndFieldName("partes_mo.tarea_id"),computeTableAndFieldName("tareas.tarea_id"),true);

               //add validations
               addRequiredRule(PARTES_MO_FECHA,"La fecha del partes es obligatoria");
               addRequiredRule(PARTES_MO_HORA_DESDE,"Hora desde es obligatoria para el parte");
               addRequiredRule(PARTES_MO_HORA_HASTA,"Hora hasta es obligatoria para el parte");
               addRequiredRule(PARTES_MO_HORAS,"La cantidad de horas trabajadas es obligatoria para el parte");
               addRequiredRule(PARTES_MO_PROYECTO_ID,"El proyecto (OT) es obligatorio en el parte");
               addRequiredRule(PARTES_MO_TAREA_ID,"La tarea es obligatoria en el parte");
               addRequiredRule(PARTES_MO_PERSONAL_ID,"Se debe indicar un legajo para el parte");
               addRequiredRule(PARTES_MO_NRO_LEGAJO,"Se debe indicar un legajo para el parte");
               addRequiredRule(PARTES_MO_APEYNOM,"Se debe indicar un legajo para el parte");
               addRequiredRule(PARTES_MO_ESTADO,"Se debe indicar un estado para el parte");

               //add lookups
               addLookupRule(PARTES_MO_LOTE_ID, "lote_carga_partes_mo",
					"'lote_carga_partes_mo.lote_id = ' + partes_mo.lote_id",
					"estado", "lote_carga_partes_mo.estado", "Lote inexistente");
               addLookupRule(PARTES_MO_SUPERVISOR, "supervisores",
					"'supervisores.nro_legajo = ' + partes_mo.supervisor",
					"apeynom", "sector_trabajo.nombre",
					"Supervisor inexistente");
               addLookupRule(PARTES_MO_SECTOR_ID, "sector_trabajo",
					"'sector_trabajo.sector_id = ' + partes_mo.sector_id",
					"nombre", "sector_trabajo.nombre", "Sector inexistente");
               addLookupRule(
					PARTES_MO_ESTADO,
					"infraestructura.estados",
					"'infraestructura.estados.estado = \"' + partes_mo.estado + '\"' ",
					"nombre", "estados.nombre", "Estado inexistente");
               
               // order by
               setOrderBy(PARTES_MO_PARTE_ID + " desc");
               
               // establece la columna de autoincrement
               setAutoIncrement(PARTES_MO_PARTE_ID, true);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the partes_mo.parte_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoParteId() throws DataStoreException {
          return  getInt(PARTES_MO_PARTE_ID);
     }

     /**
      * Retrieve the value of the partes_mo.parte_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoParteId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_PARTE_ID);
     }

     /**
      * Set the value of the partes_mo.parte_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoParteId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_PARTE_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.parte_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoParteId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_PARTE_ID, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.fecha column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getPartesMoFecha() throws DataStoreException {
          return  getDate(PARTES_MO_FECHA);
     }

     /**
      * Retrieve the value of the partes_mo.fecha column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getPartesMoFecha(int row) throws DataStoreException {
          return  getDate(row,PARTES_MO_FECHA);
     }

     /**
      * Set the value of the partes_mo.fecha column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoFecha(java.sql.Date newValue) throws DataStoreException {
          setDate(PARTES_MO_FECHA, newValue);
     }

     /**
      * Set the value of the partes_mo.fecha column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoFecha(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,PARTES_MO_FECHA, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.hora_desde column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoHoraDesde() throws DataStoreException {
          return  getString(PARTES_MO_HORA_DESDE);
     }

     /**
      * Retrieve the value of the partes_mo.hora_desde column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoHoraDesde(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_HORA_DESDE);
     }

     /**
      * Set the value of the partes_mo.hora_desde column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoraDesde(String newValue) throws DataStoreException {
          setString(PARTES_MO_HORA_DESDE, newValue);
     }

     /**
      * Set the value of the partes_mo.hora_desde column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoraDesde(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_HORA_DESDE, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.hora_hasta column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoHoraHasta() throws DataStoreException {
          return  getString(PARTES_MO_HORA_HASTA);
     }

     /**
      * Retrieve the value of the partes_mo.hora_hasta column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoHoraHasta(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_HORA_HASTA);
     }

     /**
      * Set the value of the partes_mo.hora_hasta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoraHasta(String newValue) throws DataStoreException {
          setString(PARTES_MO_HORA_HASTA, newValue);
     }

     /**
      * Set the value of the partes_mo.hora_hasta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoraHasta(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_HORA_HASTA, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.horas column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getPartesMoHoras() throws DataStoreException {
          return  getDouble(PARTES_MO_HORAS);
     }

     /**
      * Retrieve the value of the partes_mo.horas column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getPartesMoHoras(int row) throws DataStoreException {
          return  getDouble(row,PARTES_MO_HORAS);
     }

     /**
      * Set the value of the partes_mo.horas column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoras(double newValue) throws DataStoreException {
          setDouble(PARTES_MO_HORAS, newValue);
     }

     /**
      * Set the value of the partes_mo.horas column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoHoras(int row,double newValue) throws DataStoreException {
          setDouble(row,PARTES_MO_HORAS, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.lote_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoLoteId() throws DataStoreException {
          return  getInt(PARTES_MO_LOTE_ID);
     }

     /**
      * Retrieve the value of the partes_mo.lote_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoLoteId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_LOTE_ID);
     }

     /**
      * Set the value of the partes_mo.lote_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoLoteId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_LOTE_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.lote_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoLoteId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_LOTE_ID, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.supervisor column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoSupervisor() throws DataStoreException {
          return  getInt(PARTES_MO_SUPERVISOR);
     }

     /**
      * Retrieve the value of the partes_mo.supervisor column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoSupervisor(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_SUPERVISOR);
     }

     /**
      * Set the value of the partes_mo.supervisor column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoSupervisor(int newValue) throws DataStoreException {
          setInt(PARTES_MO_SUPERVISOR, newValue);
     }

     /**
      * Set the value of the partes_mo.supervisor column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoSupervisor(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_SUPERVISOR, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.categoria column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoCategoria() throws DataStoreException {
          return  getString(PARTES_MO_CATEGORIA);
     }

     /**
      * Retrieve the value of the partes_mo.categoria column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoCategoria(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_CATEGORIA);
     }

     /**
      * Set the value of the partes_mo.categoria column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoCategoria(String newValue) throws DataStoreException {
          setString(PARTES_MO_CATEGORIA, newValue);
     }

     /**
      * Set the value of the partes_mo.categoria column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoCategoria(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_CATEGORIA, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.desc_categoria column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoDescCategoria() throws DataStoreException {
          return  getString(PARTES_MO_DESC_CATEGORIA);
     }

     /**
      * Retrieve the value of the partes_mo.desc_categoria column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoDescCategoria(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_DESC_CATEGORIA);
     }

     /**
      * Set the value of the partes_mo.desc_categoria column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoDescCategoria(String newValue) throws DataStoreException {
          setString(PARTES_MO_DESC_CATEGORIA, newValue);
     }

     /**
      * Set the value of the partes_mo.desc_categoria column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoDescCategoria(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_DESC_CATEGORIA, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.sector_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoSectorId() throws DataStoreException {
          return  getInt(PARTES_MO_SECTOR_ID);
     }

     /**
      * Retrieve the value of the partes_mo.sector_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoSectorId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_SECTOR_ID);
     }

     /**
      * Set the value of the partes_mo.sector_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoSectorId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_SECTOR_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.sector_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoSectorId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_SECTOR_ID, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.proyecto_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoProyectoId() throws DataStoreException {
          return  getInt(PARTES_MO_PROYECTO_ID);
     }

     /**
      * Retrieve the value of the partes_mo.proyecto_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoProyectoId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_PROYECTO_ID);
     }

     /**
      * Set the value of the partes_mo.proyecto_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyectoId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_PROYECTO_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.proyecto_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyectoId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_PROYECTO_ID, newValue);
     }    

     /**
      * Retrieve the value of the partes_mo.tarea_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoTareaId() throws DataStoreException {
          return  getInt(PARTES_MO_TAREA_ID);
     }

     /**
      * Retrieve the value of the partes_mo.tarea_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoTareaId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_TAREA_ID);
     }

     /**
      * Set the value of the partes_mo.tarea_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoTareaId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_TAREA_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.tarea_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoTareaId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_TAREA_ID, newValue);
     }   
     
     /**
      * Retrieve the value of the proyectos.proyecto column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoProyecto() throws DataStoreException {
          return  getString(PROYECTOS_PROYECTO);
     }

     /**
      * Retrieve the value of the proyectos.proyecto column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoProyecto(int row) throws DataStoreException {
          return  getString(row,PROYECTOS_PROYECTO);
     }

     /**
      * Set the value of the proyectos.proyecto column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyecto(String newValue) throws DataStoreException {
          setString(PROYECTOS_PROYECTO, newValue);
     }

     /**
      * Set the value of the proyectos.proyecto column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyecto(int row,String newValue) throws DataStoreException {
          setString(row,PROYECTOS_PROYECTO, newValue);
     }

     /**
      * Retrieve the value of the proyectos.Nombre column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoProyectoNombre() throws DataStoreException {
          return  getString(PROYECTOS_NOMBRE);
     }

     /**
      * Retrieve the value of the proyectos.Nombre column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getPartesMoProyectoNombre(int row) throws DataStoreException {
          return  getString(row,PROYECTOS_NOMBRE);
     }

     /**
      * Set the value of the proyectos.Nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyectoNombre(String newValue) throws DataStoreException {
          setString(PROYECTOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the proyectos.Nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoProyectoNombre(int row,String newValue) throws DataStoreException {
          setString(row,PROYECTOS_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.personal_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoPersonalId() throws DataStoreException {
          return  getInt(PARTES_MO_PERSONAL_ID);
     }

     /**
      * Retrieve the value of the partes_mo.personal_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoPersonalId(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_PERSONAL_ID);
     }

     /**
      * Set the value of the partes_mo.personal_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoPersonalId(int newValue) throws DataStoreException {
          setInt(PARTES_MO_PERSONAL_ID, newValue);
     }

     /**
      * Set the value of the partes_mo.personal_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoPersonalId(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_PERSONAL_ID, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.nro_legajo column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoNroLegajo() throws DataStoreException {
          return  getInt(PARTES_MO_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the partes_mo.nro_legajo column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getPartesMoNroLegajo(int row) throws DataStoreException {
          return  getInt(row,PARTES_MO_NRO_LEGAJO);
     }

     /**
      * Set the value of the partes_mo.nro_legajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoNroLegajo(int newValue) throws DataStoreException {
          setInt(PARTES_MO_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the partes_mo.nro_legajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,PARTES_MO_NRO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.apeynom column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoApeynom() throws DataStoreException {
          return  getString(PARTES_MO_APEYNOM);
     }

     /**
      * Retrieve the value of the partes_mo.apeynom column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoApeynom(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_APEYNOM);
     }

     /**
      * Set the value of the partes_mo.apeynom column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoApeynom(String newValue) throws DataStoreException {
          setString(PARTES_MO_APEYNOM, newValue);
     }

     /**
      * Set the value of the partes_mo.apeynom column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoApeynom(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_APEYNOM, newValue);
     }

     /**
      * Retrieve the value of the partes_mo.estado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoEstado() throws DataStoreException {
          return  getString(PARTES_MO_ESTADO);
     }

     /**
      * Retrieve the value of the partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getPartesMoEstado(int row) throws DataStoreException {
          return  getString(row,PARTES_MO_ESTADO);
     }

     /**
      * Set the value of the partes_mo.estado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoEstado(String newValue) throws DataStoreException {
          setString(PARTES_MO_ESTADO, newValue);
     }

     /**
      * Set the value of the partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setPartesMoEstado(int row,String newValue) throws DataStoreException {
          setString(row,PARTES_MO_ESTADO, newValue);
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
      * Retrieve the value of the supervisores.apeynom column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getSupervisoresApeynom() throws DataStoreException {
          return  getString(SUPERVISORES_APEYNOM);
     }

     /**
      * Retrieve the value of the supervisores.apeynom column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getSupervisoresApeynom(int row) throws DataStoreException {
          return  getString(row,SUPERVISORES_APEYNOM);
     }

     /**
      * Set the value of the supervisores.apeynom column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresApeynom(String newValue) throws DataStoreException {
          setString(SUPERVISORES_APEYNOM, newValue);
     }

     /**
      * Set the value of the supervisores.apeynom column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresApeynom(int row,String newValue) throws DataStoreException {
          setString(row,SUPERVISORES_APEYNOM, newValue);
     }    
          
     /**
      * Retrieve the value of the estados.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre() throws DataStoreException {
          return  getString(ESTADOS_NOMBRE);
     }

     /**
      * Retrieve the value of the estados.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEstadosNombre(int row) throws DataStoreException {
          return  getString(row,ESTADOS_NOMBRE);
     }

     /**
      * Set the value of the estados.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(String newValue) throws DataStoreException {
          setString(ESTADOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the estados.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEstadosNombre(int row,String newValue) throws DataStoreException {
          setString(row,ESTADOS_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.estado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLoteCargaPartesMoEstado() throws DataStoreException {
          return  getString(LOTE_CARGA_PARTES_MO_ESTADO);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLoteCargaPartesMoEstado(int row) throws DataStoreException {
          return  getString(row,LOTE_CARGA_PARTES_MO_ESTADO);
     }

     /**
      * Set the value of the lote_carga_partes_mo.estado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoEstado(String newValue) throws DataStoreException {
          setString(LOTE_CARGA_PARTES_MO_ESTADO, newValue);
     }

     /**
      * Set the value of the lote_carga_partes_mo.estado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoEstado(int row,String newValue) throws DataStoreException {
          setString(row,LOTE_CARGA_PARTES_MO_ESTADO, newValue);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.fecha_alta column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getLoteCargaPartesMoFechaAlta() throws DataStoreException {
          return  getDate(LOTE_CARGA_PARTES_MO_FECHA_ALTA);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.fecha_alta column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getLoteCargaPartesMoFechaAlta(int row) throws DataStoreException {
          return  getDate(row,LOTE_CARGA_PARTES_MO_FECHA_ALTA);
     }

     /**
      * Set the value of the lote_carga_partes_mo.fecha_alta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoFechaAlta(java.sql.Date newValue) throws DataStoreException {
          setDate(LOTE_CARGA_PARTES_MO_FECHA_ALTA, newValue);
     }

     /**
      * Set the value of the lote_carga_partes_mo.fecha_alta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoFechaAlta(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,LOTE_CARGA_PARTES_MO_FECHA_ALTA, newValue);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.fecha_cierre column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getLoteCargaPartesMoFechaCierre() throws DataStoreException {
          return  getDate(LOTE_CARGA_PARTES_MO_FECHA_CIERRE);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.fecha_cierre column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getLoteCargaPartesMoFechaCierre(int row) throws DataStoreException {
          return  getDate(row,LOTE_CARGA_PARTES_MO_FECHA_CIERRE);
     }

     /**
      * Set the value of the lote_carga_partes_mo.fecha_cierre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoFechaCierre(java.sql.Date newValue) throws DataStoreException {
          setDate(LOTE_CARGA_PARTES_MO_FECHA_CIERRE, newValue);
     }

     /**
      * Set the value of the lote_carga_partes_mo.fecha_cierre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoFechaCierre(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,LOTE_CARGA_PARTES_MO_FECHA_CIERRE, newValue);
     }

     /**
      * Retrieve the value of the mensaje_error bucket for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMensajeError() throws DataStoreException {
          return  getString(MENSAJE_ERROR);
     }

     /**
      * Retrieve the value of the mensaje_error bucket for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMensajeError(int row) throws DataStoreException {
          return  getString(row,MENSAJE_ERROR);
     }

     /**
      * Set the value of the mensaje_error bucket for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMensajeError(String newValue) throws DataStoreException {
          setString(MENSAJE_ERROR, newValue);
     }

     /**
      * Set the value of the mensaje_error bucket for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMensajeError(int row,String newValue) throws DataStoreException {
          setString(row,MENSAJE_ERROR, newValue);
     }
     
     // ---
     
     /**
      * Retrieve the value of the tareas_proyecto.nombre column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getTareasProyectoNombre() throws DataStoreException {
          return  getString(TAREAS_PROYECTO_NOMBRE);
     }

     /**
      * Retrieve the value of the tareas_proyecto.nombre column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getTareasProyectoNombre(int row) throws DataStoreException {
          return  getString(row,TAREAS_PROYECTO_NOMBRE);
     }

     /**
      * Set the value of the tareas_proyecto.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTareasProyectoNombre(String newValue) throws DataStoreException {
          setString(TAREAS_PROYECTO_NOMBRE, newValue);
     }

     /**
      * Set the value of the tareas_proyecto.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTareasProyectoNombre(int row,String newValue) throws DataStoreException {
          setString(row,TAREAS_PROYECTO_NOMBRE, newValue);
     }
     
     // --
     
     /**
      * Retrieve the value of the tareas_proyecto.descripcion column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public String getTareasProyectoDescripcion() throws DataStoreException {
          return  getString(TAREAS_PROYECTOS_DESCRIPCION);
     }

     /**
      * Retrieve the value of the tareas_proyecto.descripcion column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public String getTareasProyectoDescripcion(int row) throws DataStoreException {
          return  getString(row,TAREAS_PROYECTOS_DESCRIPCION);
     }

     /**
      * Set the value of the tareas_proyecto.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTareasProyectoDescripcion(String newValue) throws DataStoreException {
          setString(TAREAS_PROYECTOS_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the tareas_proyecto.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setTareasProyectoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,TAREAS_PROYECTOS_DESCRIPCION, newValue);
     }
     
    // $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
     
 	@Override
	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, SQLException {
		if (_validarPartes)
			validarPartes();			
		super.update(conn, handleTrans);
	}	
 	
 	/**
 	 * Habilita el control de los atributos del parte al llamar al metodo update del modelo.
 	 * Si se setea en true, estos atributos son verificados automaticamente al llamar a update.
 	 * Si es false, los metodos correspondientes deben ser invocados manualmente.
 	 * @param validar true para controlar los atributos, false para saltar el control.
 	 */
 	public void doValidarPartes(boolean validar) {
 		_validarPartes = validar; 		
 	}
 	
	public int parserHora(String hora) {
		int tmpHora = -1;

		// si el largo es mayor a 5 doy error
		if (hora.length() > 5)
			return -1;

		// verifico formato si viene con minutos o sólo hora
		int index2Puntos = hora.indexOf(":");
		if (index2Puntos == -1)
			index2Puntos = hora.indexOf(".");

		if (index2Puntos > -1) {
			// horas y minutos extraigo solo las horas
			try {
				tmpHora = Integer.parseInt(hora.substring(0, index2Puntos));
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}

		} else {
			// no hay minutos, es hora pura
			try {
				tmpHora = Integer.parseInt(hora);
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}
		}

		// controlo rango horario
		if (tmpHora < 0 || tmpHora > 24)
			return -1;

		// todo ok regreso la hora
		return tmpHora;
	}

	public int parserMinutos(String hora) {
		int tmpMinutos = -1;

		// si el largo es mayor a 5 doy error
		if (hora.length() > 5)
			return -1;

		// verifico formato si viene con minutos o sólo hora
		int index2Puntos = hora.indexOf(":");
		if (index2Puntos == -1)
			index2Puntos = hora.indexOf(".");

		if (index2Puntos > -1) {
			// horas y minutos extraigo solo las horas
			try {
				tmpMinutos = Integer.parseInt(hora.substring(index2Puntos + 1));
				// si los minutos están expresados con una sola cifra la llevo a dos
				if (hora.substring(index2Puntos + 1).length() == 1)
					tmpMinutos = tmpMinutos * 10;
			} catch (Exception e) {
				// hubo algun error de parsering
				return -1;
			}

		} else {
			// no hay minutos, es hora pura
			tmpMinutos = 0;
		}

		// controlo rango de minutos
		if (tmpMinutos < 0 || tmpMinutos > 59)
			return -1;

		// todo ok regreso la hora
		return tmpMinutos;
	}
	
	public boolean validaParte(int row){
		
		return true;
	}
	
	public String getEstadoInicial() throws DataStoreException{
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		
		if (_estado_inicial == null || _estado_inicial.trim().length() == 0) {
			try {
				conexion = DBConnection.getConnection("partesMO","infraestructura");

			   // ahora busco el primer estado posible según la máquina de estados
				SQL = "select estado_origen "
						+ " from transicion_estados "
						+ " where estado_origen in (select estado from estados where circuito = '0003')"
						+ " and estado_origen not in (select estado_destino from transicion_estados)";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					_estado_inicial = r.getString("estado_origen");
				}
				
				if (_estado_inicial != null) 
					_estado_inicial = "0003.0001";

			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Error determinando circuito y estado inicial para los proyectos: "+ e.getMessage());
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
		}

		return _estado_inicial;
	}
	
	public Connection getConexionTango() throws DataStoreException {

		if (_connTango == null) {
			_p = Props.getProps("partesMO", null);
			_driverTango = _p.getProperty("driverTango", "sun.jdbc.odbc.JdbcOdbcDriver");
			_urlTango = _p.getProperty("urlTango", "jdbc:odbc:tango");
			_userTango = _p.getProperty("userTango", "tango");
			_passWordTango = _p.getProperty("passWordTango", "tango");
			
			try {
			    // Se carga el driver JTDS (JDBC-ODBC si no es encontrado)
				Class.forName( _driverTango );
			} catch (ClassNotFoundException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Imposible cargar el driver para Tango: " + e.getMessage());
			}

			try {
				// Se establece la conexión con la base de datos
				_connTango = DriverManager.getConnection( _urlTango,_userTango,_passWordTango );
			} catch (Exception e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("imposible establecer conexión con la base tango: " + e.getMessage());
			}
		}
		
		return _connTango;
	}
	
	public int getDatosProyecto(int row) throws DataStoreException{
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		String nombre = null;
		int proyecto_id = -1;
		java.sql.Date vigenciaHasta = null;
		java.sql.Date vigenciaDesde = null;
		String proyecto = getPartesMoProyecto(row);
		
		if ((proyecto != null) && (proyecto.trim().length() > 0)) {
			try {
				conexion = DBConnection.getConnection("partesMO","proyectos");

				// primero busco el circuito y la columna asociada a los partes
				// Agregamos vigencia_hasta para controlar vigencia del proyecto 
				SQL = "select proyecto_id, nombre, vigencia_desde, vigencia_hasta "
					+ "  from proyectos "
					+ " where proyecto = '" + proyecto + "'";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					proyecto_id = r.getInt("proyecto_id");
					nombre = r.getString("nombre");
					vigenciaHasta = r.getDate("vigencia_hasta");
					vigenciaDesde = r.getDate("vigencia_desde");
				} else {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Proyecto inexistente");
					else
						setMensajeError(row, getMensajeError(row) + " - Proyecto inexistente");
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Error determinando proyecto: "+ e.getMessage());
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
 			
			// setea las columnas
			if (proyecto_id != -1) {  
				// controlamos la fecha de vigencia del proyecto
				int f = verificaFechaPeriodo(getPartesMoFecha(row), vigenciaDesde, vigenciaHasta);
				if (f == 0) {				
					setPartesMoProyectoId(row, proyecto_id);
					setPartesMoProyectoNombre(row, nombre);
				}
				else if (f < 0) {
					setMensajeError(row,"Fecha de parte anterior a comienzo del proyecto.");
					proyecto_id = -1;
				} 
				else if (f > 0) {
					setMensajeError(row,"Fecha de parte posterior a finalización del proyecto.");
					proyecto_id = -1;
				}
			}
		} else {
			if (getMensajeError(row) == null)
				setMensajeError(row, "El proyecto es obligatorio");
			else
				setMensajeError(row, getMensajeError(row) + " - El proyecto es obligatorio");

		}

		return proyecto_id;
	}
	
	/**
	 * @param row
	 * @return
	 * @throws DataStoreException
	 */
	public int getDatosTarea(int row) throws DataStoreException {		
		TareasProyectoModel dsTareasProyecto = new TareasProyectoModel(getAppName(),"proyectos");
		int proyectoId = getPartesMoProyectoId(row);
		int tareaId = -1;
		String tareaProyectoNombre = getTareasProyectoNombre(row);
		StringBuilder whereClause = new StringBuilder(50);
		
		try {
			// check if a task was specified
			if ((tareaProyectoNombre != null) && (tareaProyectoNombre.trim().length() > 0)) {
				whereClause.append(TareasProyectoModel.TAREAS_PROYECTO_NOMBRE + " = '" + tareaProyectoNombre + "' and ");				
			}			
			whereClause.append(TareasProyectoModel.TAREAS_PROYECTO_PROYECTO_ID + " = " + proyectoId);
			// the first task added to the project on top
			dsTareasProyecto.setOrderBy(TareasProyectoModel.TAREAS_PROYECTO_TAREA_ID + " asc");
			
			dsTareasProyecto.retrieve(whereClause.toString());
			dsTareasProyecto.gotoFirst();
			tareaId = dsTareasProyecto.getRow();
			
			if (tareaId != -1) {
				setPartesMoTareaId(row, dsTareasProyecto.getTareasProyectoTareaId());
				setTareasProyectoNombre(row, dsTareasProyecto.getTareasProyectoNombre());
				setTareasProyectoDescripcion(row, dsTareasProyecto.getTareasProyectoDescripcion());
			} else {
				if (getMensajeError(row) == null)
					setMensajeError(row, "Tarea inexistente");
				else
					setMensajeError(row, getMensajeError(row) + " - Tarea inexistente");
			}
			
			return tareaId;
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			throw new DataStoreException("Error determinando proyecto: " + e.getMessage(), e);
		}		
	}

	/**
	 * Controla que la fecha del parte sea coherente con las fechas de inicio y fin de vigencia dadas
	 * @param fechaParte fecha del parte de mano de obra a ingresar
	 * @param vigenciaDesde fecha de inicio de vigencia del proyecto al que este parte corresponde
	 * @param vigenciaHasta fecha de fin de vigencia del proyecto al que este parte corresponde
	 * @return 0 si la fecha del parte se encuentra entras las fechas dadas, 1 si es posterior, -1 si es anterior
	 */
	private int verificaFechaPeriodo(java.sql.Date fechaParte, java.sql.Date vigenciaDesde, java.sql.Date vigenciaHasta) {
		if (fechaParte == null) return (-1);
		if ((vigenciaDesde != null) && (fechaParte.compareTo(vigenciaDesde) < 0)) return (-1);		
		if ((vigenciaHasta != null) && (fechaParte.compareTo(vigenciaHasta) > 0)) return 1;
		return 0;
	}

	public boolean controlaLegajoTango(int row) throws DataStoreException {
		Connection connTango;
		int personal_id = -1;
		int nro_legajo = -1;
		String apeynom = null;
		String nombre = null;
		String apellido = null;		
		String SQL = null;
		String debug = null;
		Statement st = null;
		ResultSet r = null;
		boolean hubo_errores = false;


		/***********************************************************************
		 * Calcula, controla y resuelve legajo
		 **********************************************************************/
		// recupera la conexión a tango
		connTango = getConexionTango();
		nro_legajo = getPartesMoNroLegajo(row);

		try {
			SQL = "select L.ID_LEGAJO,L.NOMBRE,L.APELLIDO "
					+ " from LEGAJO L" + " where L.NRO_LEGAJO = "
					+ Integer.toString(nro_legajo);
			debug = "createStatement 2";
			st = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			debug = "executeQuery 2";
			r = st.executeQuery(SQL);

			debug = "r.first 2";
			if (r.first()) {
				personal_id = r.getInt(1);	// L.ID_LEGAJO
				nombre = r.getString(2);	// L.NOMBRE
				apellido = r.getString(3);	// L.APELLIDO
			} else {
				// no es por el numero de legajo, busco por el id
				debug = "r.close";
				r.close();
				debug = "st.close";
				st.close();

				personal_id = getPartesMoPersonalId(row);

				SQL = "select L.NRO_LEGAJO,L.NOMBRE,L.APELLIDO"
					+ " from LEGAJO L" + " where L.ID_LEGAJO = "
					+ Integer.toString(personal_id);
				debug = "CreateStatement";
				st = connTango.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				debug = "executeQuery";
				r = st.executeQuery(SQL);
				
				debug = "r.first";
				if (r.first()) {
					nro_legajo = r.getInt(1);	// NRO_LEGAJO
					nombre = r.getString(2);	// L.NOMBRE
					apellido = r.getString(3);	// L.APELLIDO					
				} else {
					// no existe el legajo, setea el error y marca el
					// procesamiento con error
					setMensajeError(row, "Legajo inexistente en tango");
					hubo_errores = true;
				}
			}

			apeynom = apellido + ", " + nombre;

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException("Error determinando legajo en tango: "	+ e.getMessage() + " Debug: " + debug);
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

		}
		
		// Verifica que la fecha del parte coincida con un periodo de vigencia del legajo
		if (!controlaFechaIngresoEgreso(this.getPartesMoFecha(row), personal_id)) {			
			setMensajeError(row, "Legajo dado de baja durante la fecha del parte");
			hubo_errores = true;			
		}

		// setea los datos recuperados
		setPartesMoApeynom(row, apeynom);
		setPartesMoNroLegajo(row, nro_legajo);
		setPartesMoPersonalId(row, personal_id);
		
		return hubo_errores;
	}
	
	/**
	 * Controla que la fecha del parte corresponda con el periodo de vigencia del puesto de supervisor 
	 * @param row
	 * @return true si no corresponde a un periodo valido
	 * @throws DataStoreException 
	 */	
	public boolean controlaSupervisor(int row) throws DataStoreException {
		boolean hubo_errores = false;
		
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;		
		int personal_id = -1;
		java.sql.Date fechaHasta = null;
		java.sql.Date fechaDesde = null;
		int supervisor = getPartesMoSupervisor(row);
		
		// Si un supervisor fue seleccionado
		if (supervisor > 0) {
			try {
				conexion = DBConnection.getConnection("partesMO","partesmo");

				SQL = "select personal_id, fecha_desde, fecha_hasta "
					+ "  from supervisores where nro_legajo = " + getPartesMoSupervisor(row) + "";
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					personal_id = r.getInt("personal_id");					
					fechaHasta = r.getDate("fecha_hasta");
					fechaDesde = r.getDate("fecha_desde");
				} else {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Supervisor inexistente");
					else
						setMensajeError(row, getMensajeError(row) + " - Supervisor inexistente");
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException("Error determinando supervisor: "+ e.getMessage());
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

			if ((personal_id != -1) && (verificaFechaPeriodo(getPartesMoFecha(row), fechaDesde, fechaHasta) != 0)) {
				if (getMensajeError(row) == null)
					setMensajeError(row, "La fecha del parte es anterior a la vigencia del cargo del supervisor");
				else
					setMensajeError(row, getMensajeError(row) + " - La fecha del parte es anterior a la vigencia del cargo del supervisor");				
				hubo_errores = true;
			}
		} 
		
		return hubo_errores;
	}
	
	/**
	 * Controla que la fecha del parte dado pertenezca a por lo menos 
	 * un periodo en que el legajo haya o este dado de alta. 
	 * @param fechaparte Fecha del parte de mano de obra
	 * @param legajoid Id del legajo
	 * @return true si la fecha del parte corresponde a algun periodo valido del legajo
	 * @throws DataStoreException
	 */
	public boolean controlaFechaIngresoEgreso(java.sql.Date fechaparte, int legajoid) throws DataStoreException {
		Connection connTango;
		java.sql.Date fechaingreso = null;
		java.sql.Date fechaegreso = null;
		String SQL = null;
		String debug = null;
		Statement st = null;
		ResultSet r = null;
		boolean valido = false;
		
		connTango = getConexionTango();
		
		try {	
			// Query en la tabla de egresos / ingresos 
			SQL = "SELECT I.ID_LEGAJO, I.FECHA_INGRESO, I.FECHA_EGRESO"
				+ " FROM INGRESO_EGRESO I "
				+ " WHERE I.ID_LEGAJO = " + Integer.toString(legajoid);
			debug = "createStatement 2";
			st = connTango.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			debug = "executeQuery 2";
			r = st.executeQuery(SQL);

			// iteramos a travez de los resultados hasta encontrar uno valido
			while (r.next() && !valido) {
				fechaingreso = r.getDate(2);
				fechaegreso = r.getDate(3);
				// controla que la fecha este en un periodo valido
				if (verificaFechaPeriodo(fechaparte, fechaingreso, fechaegreso) == 0) {
					valido = true;
				}
			}
			
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException("Error determinando fecha ingreso egreso en tango: "	+ e.getMessage() + " Debug: " + debug);
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
		}
		
		return valido;
	}
	
	public boolean controlaHorario(int row) throws DataStoreException {
		boolean hubo_errores = false;
		String hora_desde = null;
		int hora_d = -1;
		int minuto_d = -1;
		String hora_hasta = null;
		int hora_h = -1;
		int minuto_h = -1;
		double horas = -1;

		/***********************************************************************
		 * Controla y calcula el horario y horas ingresadas
		 **********************************************************************/
		hora_desde = getPartesMoHoraDesde(row);
		hora_hasta = getPartesMoHoraHasta(row);
		horas = getPartesMoHoras(row);

		// si hora desde es null --> es error
		if (hora_desde == null) {
			if (getMensajeError(row) == null)
				setMensajeError(row, "Hora desde es obligatoria");
			else
				setMensajeError(row, getMensajeError(row)
						+ " - Hora desde es obligatoria");
			hubo_errores = true;
		} else if (hora_hasta == null && horas <= 0) {
			if (getMensajeError(row) == null)
				setMensajeError(row,
						"Debe ingresar al menos hora hasta o cantidad de horas trabajadas");
			else
				setMensajeError(
						row,
						getMensajeError(row)
								+ " - Debe ingresar al menos hora hasta o cantidad de horas trabajadas");
			hubo_errores = true;

		} else {
			// verifico formato de la hora desde
			if ((hora_d = parserHora(hora_desde)) == -1) {
				if (getMensajeError(row) == null)
					setMensajeError(row, "Error en la hora desde");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error en la hora desde");
				hubo_errores = true;
			}
			if ((minuto_d = parserMinutos(hora_desde)) == -1) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Error en los minutos de la hora desde");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error en los minutos de la hora desde");
				hubo_errores = true;
			}

			// se ingreso hora hasta????
			if (hora_hasta != null) {
				// verifico formato de la hora hasta
				if ((hora_h = parserHora(hora_hasta)) == -1) {
					if (getMensajeError(row) == null)
						setMensajeError(row, "Error en la hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - Error en la hora hasta");
					hubo_errores = true;
				}
				if ((minuto_h = parserMinutos(hora_hasta)) == -1) {
					if (getMensajeError(row) == null)
						setMensajeError(row,
								"Error en los minutos de la hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - Error en los minutos de la hora hasta");
					hubo_errores = true;
				}
			}
		}

		// si no ingresó horas, calculo las horas según desde y hasta,
		// siempre y cuando no haya errores
		// de paso se chequea que el rango horario sea correcto
		if (!hubo_errores && horas <= 0) {
			// si es límite de 24 o 0 horas suma a la hora hasta 24
			// horas para los cálculos
			if (hora_d > 12 && hora_h < 6)
				hora_h = hora_h + 24;
			if (hora_d > hora_h) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Hora desde es mayor que la hora hasta");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Hora desde es mayor que la hora hasta");
				hubo_errores = true;
			}

			if (hora_d == hora_h && minuto_d > minuto_h) {
				if (getMensajeError(row) == null)
					setMensajeError(row,
							"Hora desde es mayor que la hora hasta");
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Hora desde es mayor que la hora hasta");
				hubo_errores = true;
			}

			int tmpHoras = hora_h - (hora_d + 1);
			int tmpMinutosRemanentesDesde = 60 - minuto_d;
			int tmpMinutosRemanentesHasta = minuto_h;
			int tmpMinutos = tmpMinutosRemanentesDesde
					+ tmpMinutosRemanentesHasta;
			if (tmpMinutos > 59) {
				tmpMinutos = tmpMinutos - 60;
				tmpHoras = tmpHoras + 1;
			}

			tmpMinutos = (tmpMinutos * 100 / 60);

			horas = (double) tmpHoras + ((double) tmpMinutos) / 100.00;
		}

		if (!hubo_errores && hora_hasta == null) {
			// calculo la hora hasta en función de la hora desde y las
			// horas trabajadas
			try {

				hora_h = hora_d + (int) Math.ceil(horas);
				if (hora_h > 24)
					hora_h = hora_h - 24;

				int tmpMinutosRemanente = (int) ((horas - Math.floor(horas)) * 100);
				tmpMinutosRemanente = tmpMinutosRemanente * 100 / 60;

				minuto_h = minuto_d + tmpMinutosRemanente;
				if (minuto_h > 60) {
					hora_h = hora_h + 1;
					if (hora_h > 24)
						hora_h = hora_h - 24;
					minuto_h = minuto_h - 60;
				}

				// ultimo chequeo
				if (hora_h < 0 || hora_h > 24 || minuto_h < 0 || minuto_h > 60) {
					if (getMensajeError(row) == null)
						setMensajeError(row, "problemas calculando hora hasta");
					else
						setMensajeError(row, getMensajeError(row)
								+ " - problemas calculando hora hasta");
					hubo_errores = true;
				}

				// ahora convierto a string la hora hasta
				hora_hasta = Integer.toString(hora_h) + ":"
						+ Integer.toString(minuto_h);

			} catch (Exception e) {
				if (getMensajeError(row) == null)
					setMensajeError(row, "problemas calculando hora hasta: "
							+ e.getMessage());
				else
					setMensajeError(row, getMensajeError(row)
							+ " - problemas calculando hora hasta: "
							+ e.getMessage());
				hubo_errores = true;
			}
		}

		// seteo los datos del datastore
		if (!hubo_errores) {
			setPartesMoHoraDesde(row, hora_desde);
			setPartesMoHoraHasta(row, hora_hasta);
			setPartesMoHoras(row, horas);
		}

		return hubo_errores;

	}
	
	public int getLote(int row) throws DataStoreException {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		int lote_id = getPartesMoLoteId(row);
		Date fecha_parte = getPartesMoFecha(row);
		GregorianCalendar cal = new GregorianCalendar();
		String quincena;
		
		if (fecha_parte == null) {
			setMensajeError(row, "La fecha del parte es obligatoria. Imposible calcular lote.");
			return -1;
		}

		if (lote_id < 1) {
			// no está seteado el lote aún. determina o crea lote
			try {
				// determina la quincena, según la fecha
				cal.setTime(fecha_parte);
				int diaDelMes = cal.get(Calendar.DAY_OF_MONTH);
				
				if (diaDelMes < 16)
					//para la aprimer quincena, poco que analizar
					quincena = "Primer quincena, ";
				else 
					quincena = "Segunda quincena, ";
				
				// ahora completo el mes y año
				switch (cal.get(Calendar.MONTH)){
				case Calendar.JANUARY:
					quincena = quincena + "Enero ";
					break;
				case Calendar.FEBRUARY:
					quincena = quincena + "Febrero ";
					break;
				case Calendar.MARCH:
					quincena = quincena + "Marzo ";
					break;
				case Calendar.APRIL:
					quincena = quincena + "Abril ";
					break;
				case Calendar.MAY:
					quincena = quincena + "Mayo ";
					break;
				case Calendar.JUNE:
					quincena = quincena + "Junio ";
					break;
				case Calendar.JULY:
					quincena = quincena + "Julio ";
					break;
				case Calendar.AUGUST:
					quincena = quincena + "Agosto ";
					break;
				case Calendar.SEPTEMBER:
					quincena = quincena + "Septiembre ";
					break;
				case Calendar.OCTOBER:
					quincena = quincena + "Octubre ";
					break;
				case Calendar.NOVEMBER:
					quincena = quincena + "Noviembre ";
					break;
				case Calendar.DECEMBER:
					quincena = quincena + "Diciembre ";
					break;
				default:
					quincena = quincena + "MES y AÑO NO DETERMINADO";	
				}
				
				// completa con el año
				quincena = quincena + Integer.toString(cal.get(Calendar.YEAR));
				
				conexion = DBConnection.getConnection("partesMO","partesmo");

				// primero busco el circuito y la columna asociada a los partes
				SQL = "select lote_id"
						+ "  from lote_carga_partes_mo "
						+ " where estado in ('0004.0001','0004.0002')"
						+ " and descripcion = '" + quincena + "'"; 
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					lote_id = r.getInt("lote_id");
				} else {
					// debo crear el nuevo lote
					Date hoy = new Date(System.currentTimeMillis());
					r.close();
					st.close();
					SQL = " insert into lote_carga_partes_mo "
						+ " (estado,fecha_alta,descripcion) VALUES "
						+ "('0004.0002','" + hoy.toString() + "','" + quincena + "')";
					st = conexion.createStatement();
					st.executeUpdate(SQL);
					
					st.close();
					SQL = "select lote_id"
						+ "  from lote_carga_partes_mo "
						+ " where estado in ('0004.0001','0004.0002')"
						+ " and descripcion = '" + quincena + "'"; 
					st = conexion.createStatement();
					r = st.executeQuery(SQL);

					if (r.first()) {
						lote_id = r.getInt("lote_id");
					} else {
						if (getMensajeError(row) == null)
							setMensajeError(row, "No se pudo recuperar el lote, pese a que se ha insertado...");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - No se pudo recuperar el lote, pese a que se ha insertado...");
						lote_id = -1;
					}
					
				}
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				if (getMensajeError(row) == null)
					setMensajeError(row, "Error determinando lote para el parte: "
							+ e.getMessage());
				else
					setMensajeError(row, getMensajeError(row)
							+ " - Error determinando lote para el parte: "
							+ e.getMessage());
				lote_id = -1;
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
			
			// setea las columnas
			if (lote_id != -1) {
				setPartesMoLoteId(row, lote_id);
			}
		}

		return lote_id;
	}
	
	public String getCategoriaLegajo(int row) throws DataStoreException{
		String cod_categoria = null;
		String desc_categoria = null;
		Connection connTango;
		int nro_legajo = -1;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;

		/***********************************************************************
		 * Calcula la categoria del legajo ingresado si es que no está seteada
		 **********************************************************************/
		// recupera la conexión a tango
		connTango = getConexionTango();
		nro_legajo = getPartesMoNroLegajo(row);
		cod_categoria = getPartesMoCategoria(row);
		
		if (cod_categoria == null || cod_categoria.trim().length() == 0) {
			try {
				SQL = " select CATEGORIA.COD_CATEGORIA, CATEGORIA.DESC_CATEGORIA"
					+ " from LEGAJO as LEGAJO "
					+ " left join LEGAJO_SU as LEGAJO_SU on LEGAJO_SU.ID_LEGAJO = LEGAJO.ID_LEGAJO "
					+ " left join CATEGORIA as CATEGORIA on CATEGORIA.ID_CATEGORIA = LEGAJO_SU.ID_CATEGORIA "
					+ " where LEGAJO.NRO_LEGAJO = " + Integer.toString(nro_legajo);
				
				st = connTango.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				r = st.executeQuery(SQL);
				
				if (r.first()) {
					cod_categoria = r.getString(1);
					desc_categoria = r.getString(2);
				}

			} catch (SQLException e) {
				MessageLog.writeErrorMessage(SQL,e, null);
				MessageLog.writeErrorMessage(e, null);
				// además de escribir en el log mando mensaje a la página
				throw new DataStoreException("Error determinando categoria en tango: "	+ e.getMessage());
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

			}

			// setea los datos recuperados
			setPartesMoCategoria(row, cod_categoria);
			setPartesMoDescCategoria(row, desc_categoria);
			
		}
		
		return cod_categoria;
	}
	
	public boolean parteDuplicado(int row) throws DataStoreException, SQLException {
		
		Date fechaRow = getPartesMoFecha(row);
		int nroLegajoRow = getPartesMoNroLegajo(row);
		String horaDesdeRow = getPartesMoHoraDesde(row);
		String horaHastaRow = getPartesMoHoraHasta(row);
		int hora_d = parserHora(horaDesdeRow);
		int minuto_d = parserMinutos(horaDesdeRow);
		int hora_h = parserHora(horaHastaRow);
		int minuto_h = parserMinutos(horaHastaRow);
		int hora_d_abs = hora_d*100 + minuto_d;
		int hora_h_abs = hora_h*100 + minuto_h;
		int parteId = getPartesMoParteId(row);
		
		// primero controla partes duplicados en el datastore
		for (int i = 0; i < getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (i != row && (getRowStatus(i) == STATUS_NEW_MODIFIED || getRowStatus(i) == STATUS_MODIFIED)) {

				Date fechaI = getPartesMoFecha(i);
				int nroLegajoI = getPartesMoNroLegajo(i);
				String horaDesdeI = getPartesMoHoraDesde(i);
				String horaHastaI = getPartesMoHoraHasta(i);
				int hora_d_i = parserHora(horaDesdeI);
				int minuto_d_i = parserMinutos(horaDesdeI);
				int hora_h_i = parserHora(horaHastaI);
				int minuto_h_i = parserMinutos(horaHastaI);
				int hora_d_abs_i = hora_d_i*100 + minuto_d_i;
				int hora_h_abs_i = hora_h_i*100 + minuto_h_i;
				
				// si es misma fecha y legajo, es candidato
				if (fechaRow.equals(fechaI) && nroLegajoRow == nroLegajoI) {
					if ( (hora_d_abs < hora_h_abs_i) && (hora_h_abs_i<= hora_h_abs)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
					if ( (hora_d_abs_i < hora_h_abs) && (hora_h_abs<= hora_h_abs_i)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
				}
			}
		}
		
		// ahora controlo posibles partes duplicados en la base de datos
		PartesMoModel partesDB = new PartesMoModel(getAppName(),getDbProfile());
		partesDB.retrieve("partes_mo.fecha = '" + fechaRow.toString() + "'");
		for (int i = 0; i < partesDB.getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (parteId != partesDB.getPartesMoParteId(i)) {

				Date fechaI = partesDB.getPartesMoFecha(i);
				int nroLegajoI = partesDB.getPartesMoNroLegajo(i);
				String horaDesdeI = partesDB.getPartesMoHoraDesde(i);
				String horaHastaI = partesDB.getPartesMoHoraHasta(i);
				int hora_d_i = partesDB.parserHora(horaDesdeI);
				int minuto_d_i = partesDB.parserMinutos(horaDesdeI);
				int hora_h_i = partesDB.parserHora(horaHastaI);
				int minuto_h_i = partesDB.parserMinutos(horaHastaI);
				int hora_d_abs_i = hora_d_i*100 + minuto_d_i;
				int hora_h_abs_i = hora_h_i*100 + minuto_h_i;
				
				// si es misma fecha y legajo, es candidato
				if (fechaRow.equals(fechaI) && nroLegajoRow == nroLegajoI) {
					if ( (hora_d_abs < hora_h_abs_i) && (hora_h_abs_i<= hora_h_abs)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
					if ( (hora_d_abs_i < hora_h_abs) && (hora_h_abs<= hora_h_abs_i)) {
						if (getMensajeError(row) == null)
							setMensajeError(row, "Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						else
							setMensajeError(row, getMensajeError(row)
									+ " - Ya existe un parte para el legajo fecha con mismo horario o superposición de horario");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void completaUnParte(int row) throws DataStoreException, SQLException{
		boolean hubo_errores_legajo = false;
		boolean hubo_errores_horario = false;
		boolean hubo_errores_proyecto = false;
		boolean hubo_errores_tarea = false;
		boolean hubo_errores_lote = false;
		boolean hubo_errores_dup = false;

		// limpio mensajes de error que pueden haber quedado
		setMensajeError(row, "");

		hubo_errores_legajo = controlaLegajoTango(row);
		getCategoriaLegajo(row);
		hubo_errores_proyecto = (getDatosProyecto(row) == -1);
		hubo_errores_tarea = (getDatosTarea(row) == -1);
		hubo_errores_lote = (getLote(row) == -1);
		hubo_errores_horario = controlaHorario(row);
		hubo_errores_dup = parteDuplicado(row);

		if (hubo_errores_legajo 
				|| hubo_errores_horario 
				|| hubo_errores_proyecto 
				|| hubo_errores_tarea
				|| hubo_errores_lote
				|| hubo_errores_dup) 
		{
			throw new DataStoreException("Hubo errores procesando partes. Corríjalos y grabe nuevamente");
		}
	}
		
	
	public void validarPartes() throws DataStoreException, SQLException {
		boolean hubo_errores_legajo = false;
		boolean hubo_errores_horario = false;
		boolean hubo_errores_proyecto = false;
		boolean hubo_errores_tarea = false;
		boolean hubo_errores_lote = false;
		boolean hubo_errores_dup = false;
		boolean hubo_errores_supervisor = false;
		String estado_inicial = null;

		// recupera estado inicial para los partes
		estado_inicial = getEstadoInicial();

		// realiza las tareas de lookup sobre los datos de tango y completa información
		// realiza validaciones varias de consistencia de información
		// aplica el tratamiento de horas y rango horario
		for (int i = 0; i < getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
			if (getRowStatus(i) == STATUS_NEW_MODIFIED
					|| getRowStatus(i) == STATUS_MODIFIED) {

				if (estado_inicial != null && (getPartesMoEstado(i) == null || getPartesMoEstado(i).trim().length() == 0))
					setPartesMoEstado(i, "0003.0002");
				else if (getPartesMoEstado(i) == null || getPartesMoEstado(i).trim().length() == 0)
					setPartesMoEstado(i, "0003.0002");

				// limpio mensajes de error que pueden haber quedado
				setMensajeError(i, "");

				if (hubo_errores_legajo || hubo_errores_horario || hubo_errores_proyecto || hubo_errores_tarea) {
					controlaLegajoTango(i);
					getCategoriaLegajo(i);
					getDatosProyecto(i);
					getDatosTarea(i);
					controlaHorario(i);					
					parteDuplicado(i);
				} else {
					hubo_errores_legajo = controlaLegajoTango(i);
					getCategoriaLegajo(i);
					hubo_errores_proyecto = (getDatosProyecto(i) == -1);
					hubo_errores_tarea = (getDatosTarea(i) == -1);
					hubo_errores_lote = (getLote(i) == -1);
					hubo_errores_horario = controlaHorario(i);
					hubo_errores_supervisor = controlaSupervisor(i);
					hubo_errores_dup = parteDuplicado(i);
				}
				
			}
		}

		if (hubo_errores_legajo 
				|| hubo_errores_horario 
				|| hubo_errores_proyecto 
				|| hubo_errores_tarea
				|| hubo_errores_lote
				|| hubo_errores_supervisor
				|| hubo_errores_dup) 
		{
			throw new DataStoreException("Hubo errores procesando partes. Corríjalos y grabe nuevamente");
		}

	}
    
    /**
     * servicio que genera el resumen de liquidacion para un periodo
     * @param p_mes
     * @param p_anio
     * @throws DataStoreException 
     */
    public void liquidarPeriodo(int p_mes, int p_anio) throws DataStoreException{
		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;
		feriadosModel _dsFeriados = new feriadosModel("partesMO","infraestructura");

		// valida los parámetros y acomoda el año en función de 2 o 4 dígitos
		if (p_mes < 1 || p_mes > 12 )
			throw new DataStoreException("Error en parámetro mes debe estar entre 1 y 12");
		
		if (p_anio < 1980 || p_anio > 2999)
			throw new DataStoreException("Error en parámetro año. Debe ser de 4 dígitos");
		
		// si existe ya existe liquidación la borro
		try {
			conexion = DBConnection.getConnection("partesMO");
			conexion.beginTransaction();

			// recupera los feriados
			_dsFeriados.retrieve();
			
			// elimino los detalles de resumen para el período
			SQL = " delete from detalle_resumen_horas "
				+ " where fecha between '"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01' " 
				+ "                and last_day('"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01')";
			st = conexion.createStatement();
			st.executeUpdate(SQL);
			st.close();
			
			// elimino los resumen para el período
			SQL = " delete from resumen_horas "
				+ " where periodo between '"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01' " 
				+ "                and last_day('"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01')";
			st = conexion.createStatement();
			st.executeUpdate(SQL);
			st.close();
			
			conexion.commit();
			conexion.freeConnection();
			
			// limpio (por si las moscas el datastore
			reset();
			
			// recupero todos los partes a resumir. todos los que tienen fecha del periodo
			retrieve("partes_mo.fecha between '"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01' " 
				   + "and last_day('"+Integer.toString(p_anio)+"-" + Integer.toString(p_mes)+"-01')");
			
			// itero por todos los partes recuperados procesando el resumen
			
			for (int i = 0 ; i < getRowCount() ; i++) {
				// setea todas las variables de uso para determinar reglas de liquidación
				int nro_legajo = getPartesMoNroLegajo(i);
				String apeynom = getPartesMoApeynom(i);
				Date fecha = getPartesMoFecha(i);
				String horaDesde = getPartesMoHoraDesde(i);
				String horaHasta = getPartesMoHoraHasta(i);
				int hora_d = parserHora(horaDesde);
				int minuto_d = parserMinutos(horaDesde);
				int hora_h = parserHora(horaHasta);
				int minuto_h = parserMinutos(horaHasta);
				int hora_d2 = -1;
				int minuto_d2 = -1;
				int hora_h2 = -1;
				int minuto_h2 = -1;
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(fecha);
				int dia1 = cal.get(Calendar.DAY_OF_MONTH);
				int dia2 = -1;
				int mes1 = cal.get(Calendar.MONTH)+1;
				int mes2 = mes1;
				int anio1 = cal.get(Calendar.YEAR);
				int anio2 = anio1;
				if (hora_h < hora_d) {
					// abarca dos días setea hrorario también 
					dia2 = dia1 + 1;
					hora_d2 = 0;
					hora_h2 = hora_h;
					minuto_d2 = 0;
					minuto_h2 = minuto_h;
					hora_h = 24;
					minuto_h = 0;
				}
				if (dia2 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					// se pasó de mes
					dia2 = 1;
					mes2 = mes1 + 1;
				}
				if (mes2 > cal.getActualMaximum(Calendar.MONTH)+1) {
					// se pasó de año
					mes2 = 1;
					anio2 = anio1 + 1;
				}
				GregorianCalendar cal2 = null;
				if (dia2 != -1) {
					cal2 = new GregorianCalendar();
					cal2.set(anio2, mes2 - 1, dia2);
				}
				
				int quincena1 = -1;
				int quincena2 = -1;
				
				// determina la quincena
				if (dia1 < 16)
					quincena1 = 1;
				else
					quincena1 = 2;
				if (dia2 < 16)
					quincena2 = 1;
				else
					quincena2 = 2;

				// recupera o inserta las cabeceras para dia1 y dia2 (sólo si corresponde)
				int resumen_id1 = getResumenHoras(1,dia1,mes1,anio1,quincena1,nro_legajo,apeynom);
				int resumen_id2 = -1;
				if (dia2 != -1 && (mes2 != mes1 || anio2 != anio1 || quincena2 != quincena1)) {
					resumen_id2 = getResumenHoras(2,dia2,mes2,anio2,quincena2,nro_legajo,apeynom);
				}
				
				// calcula horas y su tipo
				int tmpHoras1 = 0;
				int tmpHoras2 = 0;
				int tmpMinutosRemanentesDesde1 = 0;
				int tmpMinutosRemanentesDesde2 = 0;
				int tmpMinutosRemanentesHasta1 = 0;
				int tmpMinutosRemanentesHasta2 = 0;
				int tmpMinutos1 = 0;
				int tmpMinutos2 = 0;
				double horas1 = 0;
				double horas2 = 0;
				
				tmpHoras1 = hora_h - (hora_d + 1);
				tmpMinutosRemanentesDesde1 = 60 - minuto_d;
				tmpMinutosRemanentesHasta1 = minuto_h;
				tmpMinutos1 = tmpMinutosRemanentesDesde1 + tmpMinutosRemanentesHasta1;
				if (tmpMinutos1 > 59) {
					tmpMinutos1 = tmpMinutos1 - 60;
					tmpHoras1 = tmpHoras1 + 1;
				}
				tmpMinutos1 = (tmpMinutos1 * 100 / 60);
				horas1 = (double) tmpHoras1 + ((double) tmpMinutos1) / 100.00;
				horas2 = 0.00;
				if (tmpMinutosRemanentesDesde1 == 60)
					tmpMinutosRemanentesDesde1 = 0;
				
				if (dia2 != -1) {
					tmpHoras2 = hora_h2 - (hora_d2 + 1);
					tmpMinutosRemanentesDesde2 = 60 - minuto_d2;
					tmpMinutosRemanentesHasta2 = minuto_h2;
					tmpMinutos2 = tmpMinutosRemanentesDesde2 + tmpMinutosRemanentesHasta2;
					if (tmpMinutos2 > 59) {
						tmpMinutos2 = tmpMinutos2 - 60;
						tmpHoras2 = tmpHoras2 + 1;
					}
					tmpMinutos2 = (tmpMinutos2 * 100 / 60);
					horas2 = (double) tmpHoras2 + ((double) tmpMinutos2) / 100.00;
					if (tmpMinutosRemanentesDesde2 == 60)
						tmpMinutosRemanentesDesde2 = 0;
				}
				
				_horas1 = _horas1 + horas1;
				// determina si son nocturnas
				if ( hora_h >= 21 || hora_d < 6) 
				{
					if (hora_h >= 21)
						if (hora_d > 21)
							_nocturnas1 = _nocturnas1 + (double)(hora_h - hora_d) + ((double) tmpMinutos1) / 100.00;
						else
							_nocturnas1 = _nocturnas1 + (double)(hora_h - 21) + ((double)(tmpMinutosRemanentesHasta1 * 100 / 60) / 100.00);
					if (hora_d < 6)
						if (hora_h > 6)
							_nocturnas1 = _nocturnas1 + (double)(6 - hora_d) + ((double)(tmpMinutosRemanentesDesde1 * 100 / 60) / 100.00);
						else
							_nocturnas1 = _nocturnas1 + (double)(hora_h - hora_d) + ((double) tmpMinutos1) / 100.00;					
				}
				
				// determina si es feriado y/o domingo o sábado despues de las 13
				if (_dsFeriados.esFeriado(new Date(cal.getTimeInMillis())) ||
						cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
					_al_100_1 = _al_100_1 + horas1;
				else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && hora_h > 13)
					_al_100_1 = _al_100_1 + (double)(hora_h - 13) + ((double) tmpMinutosRemanentesHasta1 * 100 / 60) / 100.00;

				// determina lashoras al 50
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && (_horas1-_al_100_1) > 8)
						_al_50_1 = (_horas1-_al_100_1) - 8;
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && (_horas1-_al_100_1) > 4)
					_al_50_1 = (_horas1-_al_100_1) - 4;

				_normales1 = _horas1 - _al_50_1 - _al_100_1; 
				if (_normales1 < 0)
					_normales1 = 0;
				// actualiza la tabla resumen
				setResumenHoras(resumen_id1,_horas1,_normales1,_al_50_1,_al_100_1,_nocturnas1);
				
				// verifico si es día partido
				if (resumen_id2 != -1) {
					_horas2 = _horas2 + horas2;
					
					// determina si son nocturnas
					if ( hora_h2 >= 21 || hora_d2 <= 6) 
					{
						if (hora_h2 >= 21)
							if (hora_d2 > 21)
								_nocturnas2 = _nocturnas2 + (double)(hora_h2 - hora_d2) + ((double) tmpMinutos2) / 100.00;
							else
								_nocturnas2 = _nocturnas2 + (double)(hora_h2 - 21) + ((double)(tmpMinutosRemanentesHasta2 * 100 / 60) / 100.00);
						if (hora_d2 <= 6)
							if (hora_h2 > 6)
								_nocturnas2 = _nocturnas2 + (double)(6 - hora_d2) + ((double)(tmpMinutosRemanentesDesde2 * 100 / 60) / 100.00);
							else
								_nocturnas2 = _nocturnas2 + (double)(hora_h2 - hora_d2) + ((double) tmpMinutos2) / 100.00;					
					}

					// determina si es feriado y/o domingo o sábado despues de las 13
					if (_dsFeriados.esFeriado(new Date(cal2.getTimeInMillis())) ||
							cal2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
						_al_100_2 = _al_100_2 + horas2;
					else if (cal2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && hora_h2 > 13)
						_al_100_2 = _al_100_2 + (double)(hora_h2 - 13) + ((double) tmpMinutosRemanentesHasta2 * 100 / 60) / 100.00;

					// determina si son al 50
					if (cal2.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && (_horas2-_al_100_2) > 8)
							_al_50_2 = (_horas2-_al_100_2) - 8;
					if (cal2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && (_horas2-_al_100_2) > 4)
						_al_50_2 = (_horas2-_al_100_2) - 4;

					_normales2 = _horas2 - _al_50_2 - _al_100_2;
					if (_normales2 < 0)
						_normales2 = 0;
					// actualiza la tabla resumen
					setResumenHoras(resumen_id2,_horas2,_normales2,_al_50_2,_al_100_2,_nocturnas2);
				}
				
			}
			
		} catch (SQLException e) {
			conexion.rollback();
			conexion.freeConnection();
			MessageLog.writeErrorMessage(SQL,e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException("Error generando la liquidación del periodo " 
					+ Integer.toString(p_mes) + "/" + Integer.toString(p_anio) + ": "	+ e.getMessage());
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

		}
    }
    
    private int getResumenHoras(int var, int dia, int mes, int anio, int quincena, int nro_legajo, String apeynom) throws DataStoreException {
		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;
		int resumen_id = -1;
		
		// siempre resetea las variables globales
		_horas1 = 0;
		_normales1 = 0;
		_al_50_1 = 0;
		_al_100_1 = 0;
		_nocturnas1 = 0;
		_horas2 = 0;
		_normales2 = 0;
		_al_50_2 = 0;
		_al_100_2 = 0;
		_nocturnas2 = 0;

		try {
			conexion = DBConnection.getConnection("partesMO");
			conexion.beginTransaction();

			// elimino los detalles de resumen para el período
			SQL = " select resumen_id, horas, normales, al_50, al_100, nocturnas "
				+ " from resumen_horas "
				+ " where resumen_horas.periodo = '"+Integer.toString(anio)+"-" 
													+Integer.toString(mes)+"-"
													+Integer.toString(dia)+"' "
				+ " and quincena = "+Integer.toString(quincena)
				+ " and nro_legajo = "+Integer.toString(nro_legajo);
			st = conexion.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				resumen_id = r.getInt("resumen_id");
				if (var == 1) {
					_horas1 = r.getFloat("horas");
					_normales1 = r.getFloat("normales");
					_al_50_1 = r.getFloat("al_50");
					_al_100_1 = r.getFloat("al_100");
					_nocturnas1 = r.getFloat("nocturnas");
				} else {
					_horas2 = r.getFloat("horas");
					_normales2 = r.getFloat("normales");
					_al_50_2 = r.getFloat("al_50");
					_al_100_2 = r.getFloat("al_100");
					_nocturnas2 = r.getFloat("nocturnas");
				}
			}
			r.close();
			st.close();
			
			
			if (resumen_id == -1){
				// el resumen no existe lo creo
				SQL = " insert into resumen_horas (periodo,nro_legajo,apeynom,quincena) " 
					+ " values ('"+Integer.toString(anio)+"-" + Integer.toString(mes)+"-"+Integer.toString(dia)+"',"
					+           Integer.toString(nro_legajo)+",'"+apeynom+"',"+Integer.toString(quincena)+")";
				st = conexion.createStatement();
				st.executeUpdate(SQL);
				st.close();

				SQL = " select resumen_id, horas, normales, al_50, al_100, nocturnas "
					+ " from resumen_horas "
					+ " where resumen_horas.periodo = '"+Integer.toString(anio)+"-" 
														+Integer.toString(mes)+"-"
														+Integer.toString(dia)+"' "
					+ " and quincena = "+Integer.toString(quincena)
					+ " and nro_legajo = "+Integer.toString(nro_legajo);
				st = conexion.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()) {
					resumen_id = r.getInt("resumen_id");
					if (var == 1) {
						_horas1 = r.getFloat("horas");
						_normales1 = r.getFloat("normales");
						_al_50_1 = r.getFloat("al_50");
						_al_100_1 = r.getFloat("al_100");
						_nocturnas1 = r.getFloat("nocturnas");
					} else {
						_horas2 = r.getFloat("horas");
						_normales2 = r.getFloat("normales");
						_al_50_2 = r.getFloat("al_50");
						_al_100_2 = r.getFloat("al_100");
						_nocturnas2 = r.getFloat("nocturnas");
					}
				}
				r.close();
				st.close();
			}
			
			conexion.commit();
			conexion.freeConnection();
			
			if (resumen_id == -1) 
				// no recuperó nada, por lo tanto es error
				throw new DataStoreException("Imposible recuperar resumen de horas");
			
			return resumen_id;

		} catch (SQLException e) {
			conexion.rollback();
			conexion.freeConnection();
			MessageLog.writeErrorMessage(SQL,e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException("Error recuperando resumen de horas: "	+ e.getMessage());
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
		}

    }
    
    private void setResumenHoras(int resumen_id,double horas, double normales, 
    		double al_50, double al_100, double nocturnas) throws DataStoreException {
		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;
		
		try {
			conexion = DBConnection.getConnection("partesMO");
			conexion.beginTransaction();

			// elimino los detalles de resumen para el período
			SQL = " update resumen_horas set "
				+ "     horas = " + Double.toString(horas) + " ,"
				+ "     normales = " + Double.toString(normales) + " ,"
				+ "     al_50 = " + Double.toString(al_50) + " ,"
				+ "     al_100 = " + Double.toString(al_100) + " ,"
				+ "     nocturnas = " + Double.toString(nocturnas)
				+ " where resumen_id = " + Integer.toString(resumen_id);
			st = conexion.createStatement();
			st.executeUpdate(SQL);
			
			conexion.commit();
			conexion.freeConnection();

		} catch (SQLException e) {
			conexion.rollback();
			conexion.freeConnection();
			MessageLog.writeErrorMessage(SQL,e, null);
			// además de escribir en el log mando mensaje a la página
			throw new DataStoreException("Error actualizando resumen de horas: " + e.getMessage());
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
		}
    }
    
	@Override
	public String getEstadoActual() throws DataStoreException {
		return getPartesMoEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getPartesMoParteId();
	}
	
	//$ENDCUSTOMMETHODS$
}