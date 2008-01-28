package partesMO.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
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
     * Add the specifed id into the comma-separated id list.
	 * @param parteId
	 * @throws DataStoreException
	 */
    public void addParteId(int parteId, int row) throws DataStoreException {    	
    	addFichadaParteId(parteId, row, RESUMEN_HORAS_RELOJ_PARTE_IDS);
    }
    
    /**
     * @param parteId
     * @throws DataStoreException
     */
    public void addParteId(int parteId) throws DataStoreException {
    	addFichadaParteId(parteId, getRow(), RESUMEN_HORAS_RELOJ_PARTE_IDS);
    }
    
    /**
     * @param fichadaId
     * @param row
     * @throws DataStoreException
     */
    public void addFichadaId(int fichadaId, int row) throws DataStoreException {    	
    	addFichadaParteId(fichadaId, row, RESUMEN_HORAS_RELOJ_FICHADA_IDS);
    }
    
    /**
     * @param fichadaId
     * @throws DataStoreException
     */
    public void addFichadaId(int fichadaId) throws DataStoreException {
    	addFichadaParteId(fichadaId, getRow(), RESUMEN_HORAS_RELOJ_FICHADA_IDS);
    }
    
    /**
     * @param id
     * @param row
     * @param column
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
     * @param horario
     * @throws DataStoreException
     */
    public void addHorario(Date horario) throws DataStoreException {
		// formatea el horario a HH:MM
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());		
		if (getResumenHorasRelojHorarios() != null) {
			StringBuilder sb = new StringBuilder(35);
			sb.append(getResumenHorasRelojHorarios());
			if ((nroFichadas() % 2) == 0)
				sb.append(" a ");
			else
				sb.append(" - ");
			sb.append(timeFormatter.format(horario.getTime()));
			setResumenHorasRelojHorarios(sb.toString());
		} else
			setResumenHorasRelojHorarios(timeFormatter.format(horario));		
    }
		
	/**
	 * @return
	 * @throws DataStoreException
	 */
	public int nroFichadas() throws DataStoreException {
		return nroParteFichada(getRow(), RESUMEN_HORAS_RELOJ_FICHADA_IDS);
	}
	
	/**
	 * @return
	 * @throws DataStoreException
	 */
	public int nroPartes() throws DataStoreException {		
		return nroParteFichada(getRow(), RESUMEN_HORAS_RELOJ_PARTE_IDS);
	}
	
	/**
	 * @param row
	 * @param column
	 * @return
	 * @throws DataStoreException
	 */
	private int nroParteFichada(int row, String column) throws DataStoreException {
		if (getString(row,column) != null) {
			return getString(row,column).split(",").length;
		} else
			return 0;
	}
	
	public void cierraResumen() throws DataStoreException {
		cierraResumen(getRow());
	}
	
	/**
	 * Cierra el resumen 
	 * @throws DataStoreException 
	 */
	public void cierraResumen(int row) throws DataStoreException {
		// tolerancias
		double toleranciad = Double.valueOf(Props.getProps(getAppName(),null).getProperty("RelojToleranciaEntrada")) ;
		double toleranciah = Double.valueOf(Props.getProps(getAppName(),null).getProperty("RelojToleranciaSalida"));
		toleranciad = (toleranciad * 100 / 60) / 100;
		toleranciah = (toleranciah * 100 / 60) / 100;
		
		if (getResumenHorasRelojParteIds(row) != null) {
			// Si hay una correspondencia partes - fichadas
			if (getResumenHorasRelojFichadaIds(row) != null) {
				double parte_desde = getResumenHorasRelojParteD(row);
				double parte_hasta = getResumenHorasRelojParteH(row);
				double fichada_desde = getResumenHorasRelojFichadaD(row);
				double fichada_hasta = getResumenHorasRelojFichadaH(row);
				double totalParte = getResumenHorasRelojHorasParte(row);
				double totalFichada = getResumenHorasRelojHorasFichada(row);
				
				if ( (Math.abs(parte_desde - fichada_desde) <= toleranciad) &&
						(Math.abs(parte_hasta - fichada_hasta) <= toleranciah) &&
						(Math.abs(totalParte - totalFichada) <= (toleranciad + toleranciah)) )
				{					
					// actualizo las observaciones para indicar que este conjunto de partes esta ok
					setResumenHorasRelojEstado(row, ResumenHorasRelojModel.PARTES_OK);
					setResumenHorasRelojObservaciones(row, ResumenHorasRelojModel.PARTES_OK_MSG);
				} else {
					// actualizo las observaciones para indicar que este conjunto de partes contiene errores
					setResumenHorasRelojEstado(row, ResumenHorasRelojModel.PARTES_ERROR);
					setResumenHorasRelojObservaciones(row, ResumenHorasRelojModel.PARTES_ERROR_MSG);
				}
			}
		} else {
			// no se encontraron partes que correspondan con las fichadas
			setResumenHorasRelojEstado(row, ResumenHorasRelojModel.FICHADA_SIN_PARTES);
			setResumenHorasRelojObservaciones(row, ResumenHorasRelojModel.FICHADA_SIN_PARTES_MSG);				
		}
	}
	
	/**
	 * 
	 * @throws DataStoreException
	 */
	public void cierraResumenesSinFichadas() throws DataStoreException {		
		filter(RESUMEN_HORAS_RELOJ_FICHADA_IDS + " == null");
		for (int i = 0; i < getRowCount(); i++ ) {
			setResumenHorasRelojEstado(i, ResumenHorasRelojModel.PARTES_SIN_FICHADA);
			setResumenHorasRelojObservaciones(i, ResumenHorasRelojModel.PARTES_SIN_FICHADA_MSG);							
		}		
		filter(null);
	}
	
	
	/**
	 * TODO: make me decent
	 * @return
	 */
	public String conErroresInClause() {
		return PARTES_ERROR + "," + PARTES_SIN_FICHADA + "," + FICHADA_SIN_PARTES;
	}
	
	/**
	 * @return
	 */
	public String todosInClause() {
		return PARTES_OK + "," + conErroresInClause();
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
     * Search for the unit of works in the date range and compute the total amount of hours worked by each employee per date: 
     * <ol>
     * <li>acording with the units of works entered</li>
     * <li>acording with the clock records from tango database</li> 
     * </ol> 
     * @param fechaDesde begin date
     * @param fechaHasta end date
     * @throws DataStoreException
     */
    public void generaResumenRelojes(java.sql.Date fechaDesde, java.sql.Date fechaHasta, DBConnection conexion) throws DataStoreException {
    	String SQL;    	
    	Statement st = null;
    	ResultSet r = null;
    	   	
    	try {
    		conexion.beginTransaction();    		
    		// delete the records which contain non validated units of work 
    		SQL = " delete from resumen_horas_reloj "
    			+ " where (fecha between '" + fechaDesde.toString()	+ "' and '" + fechaHasta.toString() + "')" 
    			+ " and (estado != " + ResumenHorasRelojModel.PARTES_VAL + " or estado is null)";
    		st = conexion.createStatement();
    		st.executeUpdate(SQL);
    		st.close();    		
    		conexion.commit();
    		
    		// retrieve units of works in the range of dates
    		retrieve("fecha between '"
					+ fechaDesde.toString()
					+ "' and '"
					+ fechaHasta.toString() + "'");
    		setBatchInserts(true);
    		
    		conexion.beginTransaction();
    		preprocesaPartesMo(fechaDesde, fechaHasta, conexion);
    		preprocesaFichadas(fechaDesde, fechaHasta, conexion);    		
    		update(conexion);
    		conexion.commit();    		
    	} catch (SQLException e) {    		
    		throw new DataStoreException(
    				"Error validando partes de mano de obra contra relojes: "
    				+ e.getMessage(), e);
    	} finally {
    		if (r != null) {
    			try {
    				r.close();
    			} catch (Exception e) {
    				e.printStackTrace();
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
    
    /**
     * Calculate the total amount of worked hours per day by each employee according with the units of work, in a specified period
     * @author Francisco
     * @param fechaDesde period begin date
     * @param fechaHasta period end date
     * @throws DataStoreException
     */
    private void preprocesaPartesMo(java.sql.Date fechaDesde, java.sql.Date fechaHasta, DBConnection conexion) throws DataStoreException {    	
    	PartesMoModel _dsPartesMo;    
    	
    	try {
    		_dsPartesMo = new PartesMoModel(getAppName(),"partesmo");    		
    		_dsPartesMo.reset();
    		// as the order by clause mix varchar and int types, the +0 trick is needed to ensure proper order
    		_dsPartesMo.setOrderBy("partes_mo.nro_legajo asc, partes_mo.fecha asc, partes_mo.hora_desde+0 asc");
			// retrieve units of works which are not validated, signed or canceled
    		_dsPartesMo.retrieve("partes_mo.fecha between '"
					+ fechaDesde.toString()	+ "' and '"	+ fechaHasta.toString()
					+ "' and partes_mo.estado in ('0003.0002','0003.0004','0003.0006')");

    		for (int i = 0; i < _dsPartesMo.getRowCount(); i++) {

    			int parte_id = _dsPartesMo.getPartesMoParteId(i);
    			int nro_legajo = _dsPartesMo.getPartesMoNroLegajo(i);
    			String apeynom = _dsPartesMo.getPartesMoApeynom(i);
    			Date fecha = _dsPartesMo.getPartesMoFecha(i);
    			String horaDesde = _dsPartesMo.getPartesMoHoraDesde(i);
    			String horaHasta = _dsPartesMo.getPartesMoHoraHasta(i);   			

    			int hora_d = _dsPartesMo.parserHora(horaDesde);
    			int minuto_d = _dsPartesMo.parserMinutos(horaDesde);
    			int hora_h = _dsPartesMo.parserHora(horaHasta);
    			int minuto_h = _dsPartesMo.parserMinutos(horaHasta);
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
    				// the unit of work span around two days
    				dia2 = dia1 + 1;
    				hora_d2 = 0;
    				hora_h2 = hora_h;
    				minuto_d2 = 0;
    				minuto_h2 = minuto_h;
    				hora_h = 24;
    				minuto_h = 0;
    			}
    			if (dia2 > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    				// next month
    				dia2 = 1;
    				mes2 = mes1 + 1;
    			}
    			if (mes2 > cal.getActualMaximum(Calendar.MONTH)+1) {
    				// next year
    				mes2 = 1;
    				anio2 = anio1 + 1;
    			}
    			GregorianCalendar cal2 = null;
    			if (dia2 != -1) {
    				cal2 = new GregorianCalendar();
    				cal2.set(anio2, mes2 - 1, dia2);
    			}   			
    			
    			// total amount of worked hours
    			double horas1 = 0;
    			double horas2 = 0;
    			horas1 = horasTrabajadas(hora_d, minuto_d, hora_h, minuto_h);
    			if (dia2 != -1) {
    				horas2 = horasTrabajadas(hora_d2, minuto_d2, hora_h2, minuto_h2);
    			}
    			
    			// retrieve the correct summary
    			int resumen1 = getResumen(nro_legajo, cal);
    			int resumen2 = -1;
    			if ( resumen1 == -1)
    				resumen1 = addResumen(nro_legajo, cal, apeynom.toString());
    			if (dia2 != -1) {
    				resumen2 = getResumen(nro_legajo, cal2);
    				if ( resumen2 == -1)
        				resumen2 = addResumen(nro_legajo, cal2, apeynom.toString());
    			}

    			// aprovechamos el orden del query y seteamos el hora desde del dia
    			if (getResumenHorasRelojParteIds(resumen1) == null) {
    				setResumenHorasRelojParteD(resumen1, (double) hora_d + ((double) minuto_d * 100 / 60 ) / 100.00 );    				
    			}
    			// agregamos el id del parte procesado
    			addParteId(parte_id, resumen1);
    			
    			if (getResumenHorasRelojParteH(resumen1) <= hora_h)    				
    				setResumenHorasRelojParteH(resumen1, (double) hora_h + ((double) minuto_h * 100 / 60 ) / 100.00 );

    			// dia partido
    			if (dia2 != -1) {
        			// aprovechamos el orden del query y seteamos el hora desde del dia
    				if (getResumenHorasRelojParteIds(resumen2) == null) {
        				setResumenHorasRelojParteD(resumen2, (double) hora_d2 + ((double) minuto_d2 * 100 / 60 ) / 100.00 );    				
        			}
        			// agregamos el id del parte procesado
        			addParteId(parte_id, resumen2);
        			
    				if (getResumenHorasRelojParteH(resumen2) <= hora_h2) 
        				setResumenHorasRelojParteH(resumen2, (double) hora_h2 + ((double) minuto_h2 * 100 / 60 ) / 100.00 );    					    					
    			}

    			// suma las horas del parte al total    			
    			setResumenHorasRelojHorasParte(resumen1, getResumenHorasRelojHorasParte(resumen1) + horas1);
    			if (dia2 != -1)
    				setResumenHorasRelojHorasParte(resumen2, getResumenHorasRelojHorasParte(resumen2) + horas2);
    		} // fin for    		
    	} catch (SQLException e) {
    		// además de escribir en el log mando mensaje a la página
    		throw new DataStoreException(
    				"Error validando partes de mano de obra contra relojes: "
    				+ e.getMessage(), e);
    	} finally {
    		// empty
    	}
    }
    
    /**
     * Calcula el total de horas trabajadas
     * @param horaDesde Hora de inicio
     * @param minutoDesde Minuto de inicio
     * @param horaHasta Hora de finalización
     * @param minutoHasta Minuto de finalización
     * @return la cantidad de tiempo transcurrido en horas decimales
     */
    private double horasTrabajadas(int horaDesde, int minutoDesde, int horaHasta, int minutoHasta) {
    	int tmpHoras = 0;
		int tmpMinutosRemanentesDesde = 0;
		int tmpMinutosRemanentesHasta = 0;
		int tmpMinutos = 0;
		double horas = 0;
		
		// calcula horas 			
		tmpHoras = horaHasta - (horaDesde + 1);
		tmpMinutosRemanentesDesde = 60 - minutoDesde;
		tmpMinutosRemanentesHasta = minutoHasta;
		tmpMinutos = tmpMinutosRemanentesDesde + tmpMinutosRemanentesHasta;
		if (tmpMinutos > 59) {
			tmpMinutos = tmpMinutos - 60;
			tmpHoras = tmpHoras + 1;
		}
		tmpMinutos = (tmpMinutos * 100 / 60);
		
		horas = (double) tmpHoras + ((double) tmpMinutos) / 100.00;
		
		return horas;
    }
    
    /**
     * Actualiza la tabla de preprocesos con la información de las fichadas encontradas en el periodo indicado.</br>
     * Se calculan los totales para los intervalos de las fichadas, y se relacionan con los partes correspondientes.</br>
     * Se ignoran las fichadas intermedias que no tengan asignado un parte diario (condicion ID_PARTE_DIARIO NOT NULL)
     * </p>
     * Dadas las fichadas: A.a, B.b, C.c y D.d (HORA.minuto):
     * </p>
     *  
     * 	nro_fichadas	fichada	hda	mda		hd	md	hh	mh	</br>
     * 		0			A.a		A	a		A	a	A	a	</br>
	 *  	1			B.b		A	a		A	a	B	b	</br>
	 *  	2			C.c		C	c		C	c	C	c	</br>
	 *  	3			D.d		C	c		C	c	D	d	</br>
	 *  
	 * 0 y 2 daran totales nulos, y 1 y 3 los totales reales.
	 * </p>
	 * Para un numero de fichadas impar > 1 da el total excluyendo la ultima fichada. Si la cantidad = 1, total nulo.  
	 * Todas los id de las fichadas, sumen o no al total, son incluidos en fichada_ids  
     * 
     * @author Francisco 18/11/2007 
     * @param fechaDesde fecha de inicio del periodo a validar
     * @param fechaHasta fecha de finalizacion del periodo a validar
     * @param conexion Conexion
     * @throws DataStoreException 
     */
    private void preprocesaFichadas(java.sql.Date fechaDesde, java.sql.Date fechaHasta, DBConnection conexion)
			throws DataStoreException 
	{    	
    	Connection connTango;    	
    	String SQLtango;    	
    	PreparedStatement pst = null;
    	ResultSet r = null;
    	
    	// variables para almacenar datos de la fichada en proceso
    	int nro_legajo;
    	int id_fichada;
    	int id_parte;
    	StringBuilder apeynom;    	
		GregorianCalendar fichada = new GregorianCalendar(); 
    	
    	// variables para almacenar datos de la fichada procesada inmediata anterior
    	int hora_d_ant;
    	int minuto_d_ant;
    	int id_parte_ant;   
    	int nro_fichadas;
    	int resumen;

    	try {    		
    		connTango = getConexionTango();

    		// fichadas realizadas por el legajo dado entre las fechas especificadas
    		SQLtango = "SELECT F.ID_FICHADA, F.ID_PARTE_DIARIO, "
    			+ " F.FICHADA, L.NRO_LEGAJO, L.APELLIDO, L.NOMBRE, F.ORIGEN_FICHADA "
    			+ " FROM FICHADA F "
    			+ " INNER JOIN LEGAJO L ON F.ID_LEGAJO = L.ID_LEGAJO "    			    				
    			+ " WHERE cast(CONVERT(varchar(8), F.FICHADA, 112) AS datetime) BETWEEN ? AND ? "
    			+ " AND F.ID_PARTE_DIARIO IS NOT NULL"
    			+ " ORDER BY L.NRO_LEGAJO ASC, F.FICHADA ASC, F.ID_FICHADA ASC ";

    		pst = connTango.prepareStatement(
    				SQLtango, ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_READ_ONLY);

    		pst.setDate(1, fechaDesde);
    		pst.setDate(2, fechaHasta);
    		r = pst.executeQuery();
    		
    		// Si el resulset no esta vacio 			
    		if (r.isBeforeFirst()) {

    			hora_d_ant = -1;
    			minuto_d_ant = -1;    			
    			id_parte_ant = -1;
    			resumen = -1;
    			nro_fichadas = 0;
    			
    			while(r.next()) {

    				id_fichada = r.getInt(1); // F.ID_FICHADA
    				id_parte = r.getInt(2); // F.ID_PARTE_DIARIO    				
    				// obtengo fecha y hora de la fichada
    				fichada.setTime(r.getTimestamp(3)); // F.FICHADA
    				nro_legajo = r.getInt(4); // L.NRO_LEGAJO
    				
    				apeynom = new StringBuilder(50);
    				apeynom.append(r.getString(5)).append(", ").append(r.getString(6)); // L.APELLIDO, L.NOMBRE
    				
    				int dia1 = fichada.get(Calendar.DAY_OF_MONTH);
    				int dia2 = -1;
    				int mes1 = fichada.get(Calendar.MONTH)+1;
    				int mes2 = mes1;
    				//int anio1 = fichada.get(Calendar.YEAR);
    				//int anio2 = anio1;

    				int hora_d;
    				int minuto_d;    			

    				// buscamos el resumen
        			resumen = getResumen(nro_legajo, fichada);
    				// no existe
    				if (resumen == -1) {
    					resumen = addResumen(nro_legajo, fichada, apeynom.toString());
    				} else {
            			// el resumen ya esta validado, lo salteamos
        				if (getResumenHorasRelojEstado() == ResumenHorasRelojModel.PARTES_VAL) {
        					nro_fichadas++;
        					continue;
        				}
    				}
    				
    				// Si no seguimos procesando el mismo parte...
    				if (id_parte_ant != id_parte ) {
    					nro_fichadas = 0;			
    				}    				
    				// Calcula las horas de un par de fichadas E/S y lo agrega al total
    				if ((nro_fichadas % 2) == 0) {    						
    					// Hora de inicio igual a la de la fichada actual
    					hora_d = fichada.get(Calendar.HOUR_OF_DAY);
    					minuto_d = fichada.get(Calendar.MINUTE);
    					hora_d_ant = hora_d;
    					minuto_d_ant = minuto_d;
    				} else {
    					// Mantengo los valores de la fichada anterior
    					hora_d = hora_d_ant;
    					minuto_d = minuto_d_ant;

    					// Hora de finalizacion
    					int hora_h = fichada.get(Calendar.HOUR_OF_DAY);
    					int minuto_h = fichada.get(Calendar.MINUTE);				

    					int hora_d2 = -1;
    					int minuto_d2 = -1;
    					int hora_h2 = -1;
    					int minuto_h2 = -1;

    					if (hora_h < hora_d) {
    						// abarca dos días setea horario también 
    						dia2 = dia1 + 1;
    						hora_d2 = 0;
    						hora_h2 = hora_h;
    						minuto_d2 = 0;
    						minuto_h2 = minuto_h;
    						hora_h = 24;
    						minuto_h = 0;
    					}
    					if (dia2 > fichada.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    						// se pasó de mes
    						dia2 = 1;
    						mes2 = mes1 + 1;
    					}
    					if (mes2 > fichada.getActualMaximum(Calendar.MONTH)+1) {
    						// se pasó de año
    						mes2 = 1;
    						//anio2 = anio1 + 1;
    					}
    					
    					double horas1 = 0;
    					double horas2 = 0;		

    					horas1 = horasTrabajadas(hora_d, minuto_d, hora_h, minuto_h);
    	    			if (dia2 != -1) {
    	    				horas2 = horasTrabajadas(hora_d2, minuto_d2, hora_h2, minuto_h2);
    	    			}
    	    			
    					// seteamos el hora hasta segun haya dia partido o no    	    			
    					if (hora_h2 != -1) {
    						if (getResumenHorasRelojFichadaH() >= hora_h2)
    							setResumenHorasRelojFichadaH( (double) hora_h2 + ((double) minuto_h2 * 100 / 60 ) / 100.00 );
    					} else {
    						if (getResumenHorasRelojFichadaH() <= hora_h)
    							setResumenHorasRelojFichadaH( (double) hora_h + ((double) minuto_h * 100 / 60 ) / 100.00 );
    					}
    					// suma las horas del parte al total
    					setResumenHorasRelojHorasFichada(getResumenHorasRelojHorasFichada() + horas1 + horas2);
    				} // fin if  

    				if (getResumenHorasRelojFichadaIds() == null) {
        				setResumenHorasRelojFichadaD( (double) hora_d + ((double) minuto_d * 100 / 60 ) / 100.00 );    				
        			}
    				// Agrego el id de fichada que se proceso
        			addFichadaId(id_fichada);
    				// Agrego el horario
        			addHorario(new java.sql.Date(fichada.getTimeInMillis()));
					// Cierro el resumen calculando las horas
        			cierraResumen();            			

        			nro_fichadas++;
    				id_parte_ant = id_parte; // F.ID_PARTE_DIARIO    				    				
    			} // fin while
    			
    		} // fin if    		
    		cierraResumenesSinFichadas();
    	} catch (SQLException e) {
    		// además de escribir en el log mando mensaje a la página
    		throw new DataStoreException(
    				"Error validando partes de mano de obra contra relojes: "
    				+ e.getMessage(), e);
    	} finally {
    		if (r != null) {
    			try {
    				r.close();
    			} catch (Exception e) {
    				throw new DataStoreException(
    	    				"Error validando partes de mano de obra contra relojes: "
    	    				+ e.getMessage(), e);
    			}
    		}
    		if (pst != null)
    			try {
    				pst.close();
    			} catch (SQLException e) {
    				throw new DataStoreException(
    	    				"Error validando partes de mano de obra contra relojes: "
    	    				+ e.getMessage(), e);
    			}    		
    	}
    }    
    
    public Connection getConexionTango() throws DataStoreException {
    	Connection connTango = null;

    	Props p = Props.getProps("partesMO", null);
    	String driverTango = p.getProperty("driverTango");
    	String urlTango = p.getProperty("urlTango");
    	String userTango = p.getProperty("userTango");
    	String passWordTango = p.getProperty("passWordTango");

    	try {
    		// Se carga el driver JTDS (JDBC-ODBC si no es encontrado)
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
    		throw new DataStoreException("imposible establecer conexión con la base tango: " + e.getMessage());
    	}
    	return connTango;
	}

    //$ENDCUSTOMMETHODS$
     
}
