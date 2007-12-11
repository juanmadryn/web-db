package partesMO.models;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;

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
     public static final String RESUMEN_HORAS_RELOJ_QUINCENA="resumen_horas_reloj.quincena";
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
          addColumn(computeTableName("resumen_horas_reloj"),"quincena",DataStore.DATATYPE_INT,false,true,RESUMEN_HORAS_RELOJ_QUINCENA);
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
      * Retrieve the value of the resumen_horas_reloj.quincena column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojQuincena() throws DataStoreException {
          return  getInt(RESUMEN_HORAS_RELOJ_QUINCENA);
     }

     /**
      * Retrieve the value of the resumen_horas_reloj.quincena column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getResumenHorasRelojQuincena(int row) throws DataStoreException {
          return  getInt(row,RESUMEN_HORAS_RELOJ_QUINCENA);
     }

     /**
      * Set the value of the resumen_horas_reloj.quincena column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojQuincena(int newValue) throws DataStoreException {
          setInt(RESUMEN_HORAS_RELOJ_QUINCENA, newValue);
     }

     /**
      * Set the value of the resumen_horas_reloj.quincena column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setResumenHorasRelojQuincena(int row,int newValue) throws DataStoreException {
          setInt(row,RESUMEN_HORAS_RELOJ_QUINCENA, newValue);
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
      * @param nroLegajo 
      * @param fecha 
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
      * @param nroLegajo 
      * @param fecha 
      * @param apeynom
      * @param quincena
      * @return The current row in the resultset or -1 if the resulset is empty.
      * @throws DataStoreException
      */
     public int addResumen(int nroLegajo, Calendar fecha, String apeynom,
			int quincena) throws DataStoreException {
		if (gotoRow(insertRow())) {
			setResumenHorasRelojFecha(new java.sql.Date(fecha.getTimeInMillis()));
			setResumenHorasRelojApeynom(apeynom);
			setResumenHorasRelojNroLegajo(nroLegajo);
			setResumenHorasRelojQuincena(quincena);
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
     * Add the specifed id into the id's comma separated list.
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
				
				if ( (Math.abs(parte_desde - fichada_desde) <= toleranciad) &&
						(Math.abs(parte_hasta - fichada_hasta) <= toleranciah) )
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
		setFindExpression(RESUMEN_HORAS_RELOJ_FICHADA_IDS + " == null");
		gotoFirst();
		while (findNext()) {
			setResumenHorasRelojEstado(ResumenHorasRelojModel.PARTES_SIN_FICHADA);
			setResumenHorasRelojObservaciones(ResumenHorasRelojModel.PARTES_SIN_FICHADA_MSG);
		}			
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
	 * @param dsPartesMo
	 * @throws DataStoreException 
	 */
	public void getPartesSeleccionados(PartesMoModel dsPartesMo) throws DataStoreException {
		Set<Integer> parteIds = new HashSet<Integer>();
		// parte ids seleccionados en el resumen
		for (int i = 0; i < getRowCount(); i++) {
			String[] pids = getResumenHorasRelojParteIds(i).split(",");
			for (String p : pids) {
				parteIds.add(Integer.valueOf(p));
			}
		}
		dsPartesMo.gotoFirst();
		// removemos los partes no seleccionados
		for (int i = 0; i < dsPartesMo.getRowCount(); i++) {
			if (!parteIds.contains(dsPartesMo.getPartesMoParteId(i))) {
				dsPartesMo.removeRow(i);
			} else 
				parteIds.remove(dsPartesMo.getPartesMoParteId(i));
		}
		// si no se encontraron todos los partes
		if (!parteIds.isEmpty()) {
			// error
		}
	}
	
     //$ENDCUSTOMMETHODS$
     
}
