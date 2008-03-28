package partesMO.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.StoredProcedureParms;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ResumenHorasRelojModel: A SOFIA generated model
 */
/**
 * @author fep
 *
 */
public class ResumenHorasRelojModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -4583440105501868493L;
	//constants for columns
     public static final String RESUMEN_HORAS_RELOJ_RESUMEN_ID="resumen_horas_reloj.resumen_id";
     public static final String RESUMEN_HORAS_RELOJ_FECHA="resumen_horas_reloj.fecha";
     public static final String RESUMEN_HORAS_RELOJ_NRO_LEGAJO="resumen_horas_reloj.nro_legajo";
     public static final String RESUMEN_HORAS_RELOJ_APEYNOM="resumen_horas_reloj.apeynom";
     public static final String RESUMEN_HORAS_RELOJ_PARTE_D="resumen_horas_reloj.parte_d";
     public static final String RESUMEN_HORAS_RELOJ_PARTE_H="resumen_horas_reloj.parte_h";
     public static final String RESUMEN_HORAS_RELOJ_HORAS_PARTE="resumen_horas_reloj.horas_parte";
     public static final String RESUMEN_HORAS_RELOJ_PARTE_IDS="resumen_horas_reloj.parte_ids";
     public static final String RESUMEN_HORAS_RELOJ_FICHADA_D="resumen_horas_reloj.fichada_d";
     public static final String RESUMEN_HORAS_RELOJ_FICHADA_H="resumen_horas_reloj.fichada_h";
     public static final String RESUMEN_HORAS_RELOJ_HORAS_FICHADA="resumen_horas_reloj.horas_fichada";
     public static final String RESUMEN_HORAS_RELOJ_FICHADA_IDS="resumen_horas_reloj.fichada_ids";
     public static final String RESUMEN_HORAS_RELOJ_HORARIOS="resumen_horas_reloj.horarios";
     public static final String RESUMEN_HORAS_RELOJ_OBSERVACIONES="resumen_horas_reloj.observaciones";
     public static final String RESUMEN_HORAS_RELOJ_ESTADO="resumen_horas_reloj.estado";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     public static int PARTES_OK = 1;
     public static int PARTES_ERROR = 2;
     public static int PARTES_SIN_FICHADA = 3;
     public static int FICHADA_SIN_PARTES = 4;
     public static int PARTES_VAL = 5;

     public static String PARTES_OK_MSG = "Ok";
     public static String PARTES_OK_MANUAL_MSG = "Ok (manual)";
     public static String PARTES_VAL_MSG = "Validado";
     public static String PARTES_ERROR_MSG = "Totales no concuerdan";
     public static String PARTES_SIN_FICHADA_MSG = "No se encontraron fichadas";
     public static String FICHADA_SIN_PARTES_MSG = "No se encontraron partes";
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ResumenHorasRelojModel object.
      * @param appName The SOFIA application name
      */
     public ResumenHorasRelojModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ResumenHorasRelojModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ResumenHorasRelojModel (String appName, String profile) { 
          super(appName, profile);

          //add columns
          addColumn(computeTableName("resumen_horas_reloj"),"resumen_id",DataStore.DATATYPE_INT,true,true,RESUMEN_HORAS_RELOJ_RESUMEN_ID);
          addColumn(computeTableName("resumen_horas_reloj"),"fecha",DataStore.DATATYPE_DATE,false,true,RESUMEN_HORAS_RELOJ_FECHA);
          addColumn(computeTableName("resumen_horas_reloj"),"nro_legajo",DataStore.DATATYPE_INT,false,true,RESUMEN_HORAS_RELOJ_NRO_LEGAJO);
          addColumn(computeTableName("resumen_horas_reloj"),"apeynom",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_RELOJ_APEYNOM);
          addColumn(computeTableName("resumen_horas_reloj"),"parte_d",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_PARTE_D);
          addColumn(computeTableName("resumen_horas_reloj"),"parte_h",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_PARTE_H);
          addColumn(computeTableName("resumen_horas_reloj"),"horas_parte",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_HORAS_PARTE);
          addColumn(computeTableName("resumen_horas_reloj"),"parte_ids",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_RELOJ_PARTE_IDS);
          addColumn(computeTableName("resumen_horas_reloj"),"fichada_d",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_FICHADA_D);
          addColumn(computeTableName("resumen_horas_reloj"),"fichada_h",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_FICHADA_H);
          addColumn(computeTableName("resumen_horas_reloj"),"horas_fichada",DataStore.DATATYPE_DOUBLE,false,true,RESUMEN_HORAS_RELOJ_HORAS_FICHADA);
          addColumn(computeTableName("resumen_horas_reloj"),"fichada_ids",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_RELOJ_FICHADA_IDS);
          addColumn(computeTableName("resumen_horas_reloj"),"horarios",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_RELOJ_HORARIOS);
          addColumn(computeTableName("resumen_horas_reloj"),"observaciones",DataStore.DATATYPE_STRING,false,true,RESUMEN_HORAS_RELOJ_OBSERVACIONES);
          addColumn(computeTableName("resumen_horas_reloj"),"estado",DataStore.DATATYPE_INT,false,true,RESUMEN_HORAS_RELOJ_ESTADO);

          //set order by
          setOrderBy(computeTableAndFieldName("resumen_horas_reloj.fecha") + " ASC," + computeTableAndFieldName("resumen_horas_reloj.nro_legajo") + " ASC," + computeTableAndFieldName("resumen_horas_reloj.parte_d") + " ASC");

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
          try {
			setFormat(RESUMEN_HORAS_RELOJ_HORAS_FICHADA, "##.##");
			setFormat(RESUMEN_HORAS_RELOJ_HORAS_PARTE, "##.##");
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          // $ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the resumen_horas_reloj.resumen_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojResumenId() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_RELOJ_RESUMEN_ID);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.resumen_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojResumenId(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_RELOJ_RESUMEN_ID);
     }

     /**
      * Set the value of the resumen_horas_reloj.resumen_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojResumenId(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_RELOJ_RESUMEN_ID, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.resumen_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojResumenId(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_RELOJ_RESUMEN_ID, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fecha column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getResumenHorasRelojFecha() throws DataStoreException {
          return  getDate(RESUMEN_HORAS_RELOJ_FECHA);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fecha column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getResumenHorasRelojFecha(int row) throws DataStoreException {
          return  getDate(row,RESUMEN_HORAS_RELOJ_FECHA);
     }

     /**
      * Set the value of the resumen_horas_reloj.fecha column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFecha(java.sql.Date newValue) throws DataStoreException {
          setDate(RESUMEN_HORAS_RELOJ_FECHA, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.fecha column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFecha(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,RESUMEN_HORAS_RELOJ_FECHA, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.nro_legajo column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojNroLegajo() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_RELOJ_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.nro_legajo column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojNroLegajo(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_RELOJ_NRO_LEGAJO);
     }

     /**
      * Set the value of the resumen_horas_reloj.nro_legajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojNroLegajo(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_RELOJ_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.nro_legajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_RELOJ_NRO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.apeynom column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojApeynom() throws DataStoreException {
          return  getString(RESUMEN_HORAS_RELOJ_APEYNOM);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.apeynom column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojApeynom(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_RELOJ_APEYNOM);
     }

     /**
      * Set the value of the resumen_horas_reloj.apeynom column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojApeynom(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_RELOJ_APEYNOM, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.apeynom column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojApeynom(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_RELOJ_APEYNOM, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_d column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojParteD() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_PARTE_D);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_d column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojParteD(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_PARTE_D);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_d column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteD(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_PARTE_D, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_d column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteD(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_PARTE_D, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_h column for the current row.
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojParteH() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_PARTE_H);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_h column for the specified row.
      * @param row which row in the table
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojParteH(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_PARTE_H);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_h column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteH(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_PARTE_H, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_h column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteH(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_PARTE_H, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horas_parte column for the current row.
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojHorasParte() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_HORAS_PARTE);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horas_parte column for the specified row.
      * @param row which row in the table
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojHorasParte(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_HORAS_PARTE);
     }

     /**
      * Set the value of the resumen_horas_reloj.horas_parte column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorasParte(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_HORAS_PARTE, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.horas_parte column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorasParte(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_HORAS_PARTE, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_ids column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojParteIds() throws DataStoreException {
          return  getString(RESUMEN_HORAS_RELOJ_PARTE_IDS);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.parte_ids column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojParteIds(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_RELOJ_PARTE_IDS);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_ids column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteIds(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_RELOJ_PARTE_IDS, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.parte_ids column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojParteIds(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_RELOJ_PARTE_IDS, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_d column for the current row.
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojFichadaD() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_FICHADA_D);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_d column for the specified row.
      * @param row which row in the table
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojFichadaD(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_FICHADA_D);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_d column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaD(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_FICHADA_D, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_d column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaD(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_FICHADA_D, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_h column for the current row.
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojFichadaH() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_FICHADA_H);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_h column for the specified row.
      * @param row which row in the table
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojFichadaH(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_FICHADA_H);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_h column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaH(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_FICHADA_H, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_h column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaH(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_FICHADA_H, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horas_fichada column for the current row.
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojHorasFichada() throws DataStoreException {
          return  getDouble(RESUMEN_HORAS_RELOJ_HORAS_FICHADA);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horas_fichada column for the specified row.
      * @param row which row in the table
      * @return Double
      * @throws DataStoreException
      */ 
     public Double getResumenHorasRelojHorasFichada(int row) throws DataStoreException {
          return  getDouble(row,RESUMEN_HORAS_RELOJ_HORAS_FICHADA);
     }

     /**
      * Set the value of the resumen_horas_reloj.horas_fichada column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorasFichada(Double newValue) throws DataStoreException {
          setDouble(RESUMEN_HORAS_RELOJ_HORAS_FICHADA, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.horas_fichada column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorasFichada(int row,Double newValue) throws DataStoreException {
          setDouble(row,RESUMEN_HORAS_RELOJ_HORAS_FICHADA, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_ids column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojFichadaIds() throws DataStoreException {
          return  getString(RESUMEN_HORAS_RELOJ_FICHADA_IDS);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.fichada_ids column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojFichadaIds(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_RELOJ_FICHADA_IDS);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_ids column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaIds(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_RELOJ_FICHADA_IDS, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.fichada_ids column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojFichadaIds(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_RELOJ_FICHADA_IDS, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horarios column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojHorarios() throws DataStoreException {
          return  getString(RESUMEN_HORAS_RELOJ_HORARIOS);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.horarios column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojHorarios(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_RELOJ_HORARIOS);
     }

     /**
      * Set the value of the resumen_horas_reloj.horarios column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorarios(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_RELOJ_HORARIOS, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.horarios column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojHorarios(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_RELOJ_HORARIOS, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojObservaciones() throws DataStoreException {
          return  getString(RESUMEN_HORAS_RELOJ_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getResumenHorasRelojObservaciones(int row) throws DataStoreException {
          return  getString(row,RESUMEN_HORAS_RELOJ_OBSERVACIONES);
     }

     /**
      * Set the value of the resumen_horas_reloj.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojObservaciones(String newValue) throws DataStoreException {
          setString(RESUMEN_HORAS_RELOJ_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,RESUMEN_HORAS_RELOJ_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.estado column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojEstado() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_RELOJ_ESTADO);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.estado column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojEstado(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_RELOJ_ESTADO);
     }

     /**
      * Set the value of the resumen_horas_reloj.estado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojEstado(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_RELOJ_ESTADO, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.estado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojEstado(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_RELOJ_ESTADO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     /**
      * Find a unique row according to the parameters and makes it the current row. 
      * @param nroLegajo employee file number
      * @param fecha date
      * @return The new current row in the resultset or -1 if the resulset is empty
      * @throws DataStoreException 
      */
     public int getResumen(int nroLegajo, Calendar fecha)
			throws DataStoreException {
		StringBuilder sb = new StringBuilder();
		sb.append("(resumen_horas_reloj.fecha.getDate() == ").append(
				fecha.get(Calendar.DAY_OF_MONTH)).append(
				" && resumen_horas_reloj.fecha.getMonth() == ").append(
				fecha.get(Calendar.MONTH)).append(
				" && resumen_horas_reloj.fecha.getYear() == ").append(
				fecha.get(Calendar.YEAR)).append(
				") && resumen_horas_reloj.nro_legajo == ").append(nroLegajo);
		setFindExpression(sb.toString());		
		if (findFirst())
			return getRow();
		else
			return -1;
	}
     
     /**
      * Insert a new row and make it the current row.
      * @param nroLegajo employee file number
      * @param fecha date
      * @param apeynom last and first name
      * @param quincena fortnight
      * @return The current row in the resultset or -1 if the resulset is empty.
      * @throws DataStoreException 
      */
     public int addResumen(int nroLegajo, Calendar fecha, String apeynom) throws DataStoreException {
		if (gotoRow(insertRow())) {
			setResumenHorasRelojFecha(new java.sql.Date(fecha.getTimeInMillis()));
			setResumenHorasRelojApeynom(apeynom);
			setResumenHorasRelojNroLegajo(nroLegajo);
			setResumenHorasRelojParteD(0.0);
			setResumenHorasRelojParteH(0.0);
			setResumenHorasRelojHorasParte(0.0);
			setResumenHorasRelojFichadaD(0.0);
			setResumenHorasRelojFichadaH(0.0);
			setResumenHorasRelojHorasFichada(0.0);
			return getRow();
		} else
			return -1;
	}
    
    /**
     * Add the specifed unit-of-work id into the comma-separated id list.
	 * @param parteId unit-of-work id 
	 * @param row datastore row
	 * @throws DataStoreException
	 */
    public void addParteId(int parteId, int row) throws DataStoreException {    	
    	addFichadaParteId(parteId, row, RESUMEN_HORAS_RELOJ_PARTE_IDS);
    }
    
    /**
     * Add the specifed unit-of-work id into the comma-separated id list of the current datastore row.
     * @param parteId unit-of-work id
     * @throws DataStoreException
     */
    public void addParteId(int parteId) throws DataStoreException {
    	addFichadaParteId(parteId, getRow(), RESUMEN_HORAS_RELOJ_PARTE_IDS);
    }
    
    /**
     * Add the specifed 'fichada' id into the comma-separated id list.
     * @param fichadaId fichada id
     * @param row datastore row
     * @throws DataStoreException
     */
    public void addFichadaId(int fichadaId, int row) throws DataStoreException {    	
    	addFichadaParteId(fichadaId, row, RESUMEN_HORAS_RELOJ_FICHADA_IDS);
    }
    
    /**
     * Add the specifed 'fichada' id into the comma-separated id list at the current datastore row.
     * @param fichadaId fichada id
     * @throws DataStoreException
     */
    public void addFichadaId(int fichadaId) throws DataStoreException {
    	addFichadaParteId(fichadaId, getRow(), RESUMEN_HORAS_RELOJ_FICHADA_IDS);
    }
    
    /**
     * Generic method for append a value into a comma-separated list
     * @param id value to insert
     * @param row datastore row
     * @param column datastore column
     * @throws DataStoreException
     */
    private void addFichadaParteId(int id, int row, String column) throws DataStoreException {
    	if(getString(row, column) != null) {
    		StringBuilder sb = new StringBuilder(getString(row, column).length() + 10);
    		sb.append(getString(row, column));
    		if (sb.length() > 0) sb.append(',');
    		sb.append(id);
    		setString(row, column, sb.toString().trim());
    	} else
    		setString(row, column, String.valueOf(id));
    }    
  		
	/**
	 * Calculate the amount of fichadas stored in the current datastore row's column RESUMEN_HORAS_RELOJ_FICHADA_IDS 
	 * @return the number of fichada ids
	 * @throws DataStoreException
	 */
	public int nroFichadas() throws DataStoreException {
		return nroParteFichada(getRow(), RESUMEN_HORAS_RELOJ_FICHADA_IDS);
	}
	
	/**
	 * Calculate the amount of unit-of-work stored in the current datastore row's column RESUMEN_HORAS_RELOJ_PARTE_IDS
	 * @return the number of unit-of-work ids
	 * @throws DataStoreException
	 */
	public int nroPartes() throws DataStoreException {		
		return nroParteFichada(getRow(), RESUMEN_HORAS_RELOJ_PARTE_IDS);
	}
	
	/**
	 * Generic method for calculate the amount of items in a comma-separated value list 
	 * @param row  datastore row index
	 * @param column datastore column index
	 * @return the amount of values
	 * @throws DataStoreException
	 */
	private int nroParteFichada(int row, String column) throws DataStoreException {
		if (getString(row,column) != null) {
			return getString(row,column).split(",").length;
		} else
			return 0;
	}
	
	/** 
	 * @return  
	 */
	public String conErroresInClause() {
		return PARTES_ERROR + "," + PARTES_SIN_FICHADA + "," + FICHADA_SIN_PARTES;
	}
	
	/**
	 * @return
	 */
	public String todosInClause() {
		return PARTES_OK + "," + PARTES_VAL + "," + conErroresInClause();
	}
	
	
	/**
	 * Retrieve the ids of the units of work contained in the selected records
	 * @return a set with the id of all selected units of work
	 * @throws DataStoreException
	 */
	public Set<Integer> getPartesSeleccionados() throws DataStoreException {
		Set<Integer> parteIds = new HashSet<Integer>();
		// parte ids seleccionados en el resumen
		for (int i = 0; i < getRowCount(); i++) {
			String[] pids = getResumenHorasRelojParteIds(i).split(",");
			for (String p : pids) {
				parteIds.add(Integer.valueOf(p));
			}
		}
		return parteIds;
	}
	
	/**
     * Connect to the Tango server, then fetch from it into MySQL the clocks marks records which match the given dates  
     * @param fechaDesde initial date of the range
	 * @param fechaHasta final date
	 * @param conexion TODO
     * @throws DataStoreException 
     * @throws SQLException
     */
    public void getFichadasFromTango(java.sql.Date fechaDesde, java.sql.Date fechaHasta, DBConnection conexion) throws DataStoreException, SQLException {
		Connection connTango = null;		
		PreparedStatement psTango = null, psMysql = null;
		ResultSet r = null;		

		try {
			connTango = getConexionTango();			
			
			String sqlFichada = "INSERT INTO partesMO.fichadas " +
					"(ID_FICHADA,ID_PARTE_DIARIO,FICHADA,NRO_LEGAJO,APELLIDO,NOMBRE,ORIGEN_FICHADA)" +
					"VALUES (?,?,?,?,?,?,?) " +
					"ON DUPLICATE KEY UPDATE " +
					"ID_FICHADA = values(ID_FICHADA), ID_PARTE_DIARIO = values(ID_PARTE_DIARIO)," +
					"FICHADA = values(FICHADA),NRO_LEGAJO = values(NRO_LEGAJO)," +
					"APELLIDO = values(APELLIDO),NOMBRE = values(NOMBRE),ORIGEN_FICHADA = values(ORIGEN_FICHADA)";
			psMysql = conexion.prepareStatement(sqlFichada);

    		// marks records between the given dates  
    		String sqlTango = "SELECT F.ID_FICHADA, F.ID_PARTE_DIARIO, "
    			+ " F.FICHADA, L.NRO_LEGAJO, L.APELLIDO, L.NOMBRE, F.ORIGEN_FICHADA "
    			+ " FROM FICHADA F "
    			+ " INNER JOIN LEGAJO L ON F.ID_LEGAJO = L.ID_LEGAJO "    			    				
    			+ " WHERE cast(CONVERT(varchar(8), F.FICHADA, 112) AS datetime) BETWEEN ? AND ? "
    			+ " AND F.ID_PARTE_DIARIO IS NOT NULL"
    			+ " ORDER BY L.NRO_LEGAJO ASC, F.FICHADA ASC, F.ID_FICHADA ASC";
			psTango = connTango.prepareStatement(sqlTango,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			psTango.setDate(1, fechaDesde);
			psTango.setDate(2, fechaHasta);
			r = psTango.executeQuery();

			Calendar fichada = new GregorianCalendar();
			
			// if the resulset is not empty
			if (r.isBeforeFirst()) {
				
				while (r.next()) {					
    				psMysql.setInt(1, r.getInt(1)); // id_fichada
    				psMysql.setInt(2, r.getInt(2)); // id_parte_diario
    				fichada.setTime(r.getTimestamp(3));
    				psMysql.setTimestamp(3, new Timestamp(fichada.getTimeInMillis())); // fichada
    				psMysql.setInt(4, r.getInt(4)); // nro_legajo
    				psMysql.setString(5, r.getString(5)); // apellido
    				psMysql.setString(6, r.getString(6)); // nombre
    				psMysql.setString(7, r.getString(7)); // origen_fichada    					
					psMysql.executeUpdate();
				}
				
			}
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			}
			if (psTango != null)
				try {
					psTango.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			if (psMysql != null)
				try {
					psMysql.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			if (connTango != null) {
				try {
					connTango.close();
				} catch (SQLException e) {
					throw new DataStoreException("Error: " + e.getMessage(), e);
				}
			}			
		}

	}
    
    /**
     * Search for the unit of works in the date range and compute the total amount of hours worked by each employee per date: 
     * <ol>
     * <li>acording with the units of works entered</li>
     * <li>acording with the imported clock records from tango database</li> 
     * </ol> 
     * @param fechaDesde first date of the range
     * @param fechaHasta last date of the range
     * @param conexion the database connection to use
     * @throws DataStoreException
     */
    public void generaResumenRelojes(java.sql.Date fechaDesde, java.sql.Date fechaHasta, DBConnection conexion) throws DataStoreException {
    	String sqlDelete;    	
    	PreparedStatement pst = null;
    	StoredProcedureParms params = null;
    	
    	// retrieve the tolerance margins
    	double toleranciad = Double.valueOf(Props.getProps(getAppName(),null).getProperty("RelojToleranciaEntrada")) ;
		double toleranciah = Double.valueOf(Props.getProps(getAppName(),null).getProperty("RelojToleranciaSalida"));
		toleranciad = (toleranciad * 100 / 60) / 100;
		toleranciah = (toleranciah * 100 / 60) / 100;
    	   	
    	try {
    		conexion.beginTransaction();
    		
    		getFichadasFromTango(fechaDesde, fechaHasta, conexion);    		
    		    		
    		// delete the records which contain non validated units of work 
    		sqlDelete = " delete from resumen_horas_reloj"
    			+ " where (fecha between ? and ?)" 
    			+ " and (estado != ? or estado is null)";
    		pst = conexion.prepareStatement(sqlDelete);
    		pst.setDate(1, fechaDesde);
    		pst.setDate(2, fechaHasta);
    		pst.setInt(3, ResumenHorasRelojModel.PARTES_VAL);
    		pst.executeUpdate();
    		pst.close();   		
    		
    		// call the stored procedures
    		params = new StoredProcedureParms();    		
    		params.addParm(fechaDesde);
    		params.addParm(fechaHasta);    		
    		executeProc(conexion, "procesaPartesMo", params);    		
    		executeProc(conexion, "procesaFichadas", params);
    		
    		params = new StoredProcedureParms();    		    		
    		params.addParm(toleranciad);
    		params.addParm(toleranciah);
    		params.addParm(fechaDesde);
    		params.addParm(fechaHasta);    		
    		executeProc(conexion, "controlaRljFichadaParte",params);
    		    		    		
    		conexion.commit();
    	} catch (SQLException e) {
   			throw new DataStoreException(e.getMessage(), e);
    	} 	
    }
    
    /**
     * Returns a connection to the Tango database. The parameters of the connection are
     * retrieved from the properties file.
     * @return a jdbc connection to the Tango database
     * @throws DataStoreException
     */
    private Connection getConexionTango() throws DataStoreException {
    	Connection connTango = null;

    	Props p = Props.getProps("partesMO", null);
    	String driverTango = p.getProperty("driverTango");
    	String urlTango = p.getProperty("urlTango");
    	String userTango = p.getProperty("userTango");
    	String passWordTango = p.getProperty("passWordTango");

    	try {
    		// Se carga el driver JTDS
    		Class.forName( driverTango );
    	} catch (ClassNotFoundException e) {
    		MessageLog.writeErrorMessage(e, null);
    		throw new DataStoreException("Imposible cargar el driver para Tango: " + e.getMessage());
    	}

    	try {
    		// Se establece la conexión con la base de datos
    		connTango = DriverManager.getConnection(urlTango,userTango,passWordTango);
    	} catch (Exception e) {
    		MessageLog.writeErrorMessage(e, null);
    		throw new DataStoreException("Imposible establecer conexión con la base Tango: " + e.getMessage());
    	}
    	return connTango;
	}
   
    //$ENDCUSTOMMETHODS$     
}
