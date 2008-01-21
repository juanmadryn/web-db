package partesEQ.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ChoferesModel: A SOFIA generated model
 */
public class ChoferesModel extends DataStore {

     //constants for columns
     public static final String CHOFERES_CHOFER_ID="choferes.chofer_id";
     public static final String CHOFERES_PERSONAL_ID="choferes.personal_id";
     public static final String CHOFERES_NRO_LEGAJO="choferes.nro_legajo";
     public static final String CHOFERES_APEYNOM="choferes.apeynom";
     public static final String CHOFERES_DESDE="choferes.desde";
     public static final String CHOFERES_HASTA="choferes.hasta";

     //constants for buckets
     public static final String MENSAJE_ERROR="mensaje_error";
     
     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     private static final long serialVersionUID = -6980805910579451863L; 
 	 //private BaseController _pagina;
 	 
 	 private Connection _connTango = null;	 
	 private Props  _p = null;
	 private String _driverTango = null;
	 private String _urlTango = null;
	 private String _userTango = null;
	 private String _passWordTango = null;
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new ChoferesModel object.
      * @param appName The SOFIA application name
      */
     public ChoferesModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new ChoferesModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public ChoferesModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add columns
               addColumn(computeTableName("choferes"),"chofer_id",DataStore.DATATYPE_INT,true,true,CHOFERES_CHOFER_ID);
               addColumn(computeTableName("choferes"),"personal_id",DataStore.DATATYPE_INT,false,true,CHOFERES_PERSONAL_ID);
               addColumn(computeTableName("choferes"),"nro_legajo",DataStore.DATATYPE_INT,false,true,CHOFERES_NRO_LEGAJO);
               addColumn(computeTableName("choferes"),"apeynom",DataStore.DATATYPE_STRING,false,true,CHOFERES_APEYNOM);
               addColumn(computeTableName("choferes"),"desde",DataStore.DATATYPE_DATE,false,true,CHOFERES_DESDE);
               addColumn(computeTableName("choferes"),"hasta",DataStore.DATATYPE_DATE,false,true,CHOFERES_HASTA);

               //add buckets
               addBucket(MENSAJE_ERROR,DataStore.DATATYPE_STRING);
               
               //set order by
               setOrderBy(computeTableAndFieldName("choferes.chofer_id") + " ASC");

               //add validations
               addRequiredRule(CHOFERES_CHOFER_ID,"Campo ID del chofer es obligatorio");
               addRequiredRule(CHOFERES_NRO_LEGAJO,"El número de legajo es obligatorio");
               addRequiredRule(CHOFERES_PERSONAL_ID,"El id de personal es obligatorio");
               addRequiredRule(CHOFERES_DESDE,"La fecha desde es obligatoria");
               addRequiredRule(CHOFERES_APEYNOM,"Apellido y nombre son obligatorios");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated
          try {
        	  // establece la columna de autoincrement
        	  setAutoIncrement(CHOFERES_CHOFER_ID, true);
          } catch (DataStoreException e) {
        	  com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
          }
          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the choferes.chofer_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesChoferId() throws DataStoreException {
          return  getInt(CHOFERES_CHOFER_ID);
     }

     /**
      * Retrieve the value of the choferes.chofer_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesChoferId(int row) throws DataStoreException {
          return  getInt(row,CHOFERES_CHOFER_ID);
     }

     /**
      * Set the value of the choferes.chofer_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesChoferId(int newValue) throws DataStoreException {
          setInt(CHOFERES_CHOFER_ID, newValue);
     }

     /**
      * Set the value of the choferes.chofer_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesChoferId(int row,int newValue) throws DataStoreException {
          setInt(row,CHOFERES_CHOFER_ID, newValue);
     }

     /**
      * Retrieve the value of the choferes.personal_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesPersonalId() throws DataStoreException {
          return  getInt(CHOFERES_PERSONAL_ID);
     }

     /**
      * Retrieve the value of the choferes.personal_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesPersonalId(int row) throws DataStoreException {
          return  getInt(row,CHOFERES_PERSONAL_ID);
     }

     /**
      * Set the value of the choferes.personal_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesPersonalId(int newValue) throws DataStoreException {
          setInt(CHOFERES_PERSONAL_ID, newValue);
     }

     /**
      * Set the value of the choferes.personal_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesPersonalId(int row,int newValue) throws DataStoreException {
          setInt(row,CHOFERES_PERSONAL_ID, newValue);
     }

     /**
      * Retrieve the value of the choferes.nro_legajo column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesNroLegajo() throws DataStoreException {
          return  getInt(CHOFERES_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the choferes.nro_legajo column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getChoferesNroLegajo(int row) throws DataStoreException {
          return  getInt(row,CHOFERES_NRO_LEGAJO);
     }

     /**
      * Set the value of the choferes.nro_legajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesNroLegajo(int newValue) throws DataStoreException {
          setInt(CHOFERES_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the choferes.nro_legajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,CHOFERES_NRO_LEGAJO, newValue);
     }

     /**
      * Retrieve the value of the choferes.apeynom column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getChoferesApeynom() throws DataStoreException {
          return  getString(CHOFERES_APEYNOM);
     }

     /**
      * Retrieve the value of the choferes.apeynom column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getChoferesApeynom(int row) throws DataStoreException {
          return  getString(row,CHOFERES_APEYNOM);
     }

     /**
      * Set the value of the choferes.apeynom column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesApeynom(String newValue) throws DataStoreException {
          setString(CHOFERES_APEYNOM, newValue);
     }

     /**
      * Set the value of the choferes.apeynom column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesApeynom(int row,String newValue) throws DataStoreException {
          setString(row,CHOFERES_APEYNOM, newValue);
     }

     /**
      * Retrieve the value of the choferes.desde column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getChoferesDesde() throws DataStoreException {
          return  getDate(CHOFERES_DESDE);
     }

     /**
      * Retrieve the value of the choferes.desde column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getChoferesDesde(int row) throws DataStoreException {
          return  getDate(row,CHOFERES_DESDE);
     }

     /**
      * Set the value of the choferes.desde column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesDesde(java.sql.Date newValue) throws DataStoreException {
          setDate(CHOFERES_DESDE, newValue);
     }

     /**
      * Set the value of the choferes.desde column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesDesde(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,CHOFERES_DESDE, newValue);
     }

     /**
      * Retrieve the value of the choferes.hasta column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getChoferesHasta() throws DataStoreException {
          return  getDate(CHOFERES_HASTA);
     }

     /**
      * Retrieve the value of the choferes.hasta column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getChoferesHasta(int row) throws DataStoreException {
          return  getDate(row,CHOFERES_HASTA);
     }

     /**
      * Set the value of the choferes.hasta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesHasta(java.sql.Date newValue) throws DataStoreException {
          setDate(CHOFERES_HASTA, newValue);
     }

     /**
      * Set the value of the choferes.hasta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setChoferesHasta(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,CHOFERES_HASTA, newValue);
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
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     @Override
 	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, SQLException {
 		validarChofer();
 		super.update(conn, handleTrans);
 	}
     
     public void validarChofer() throws DataStoreException, SQLException {    	 
    	 boolean hubo_errores_legajo = controlaLegajoTango(getRow()); 
    	 if (hubo_errores_legajo) {
    		 throw new DataStoreException("Hubo errores procesando el chofer. Corríjalos y grabe nuevamente");
    	 }
     }
     
     /*public void setPage(BaseController pagina) {
 		this._pagina = pagina;
     }*/
     
     public Connection getConexionTango() throws DataStoreException {

 		if (_connTango == null) {
 			_p = Props.getProps("partesMO", null);
 			_driverTango = _p.getProperty("driverTango", "sun.jdbc.odbc.JdbcOdbcDriver");
 			_urlTango = _p.getProperty("urlTango", "jdbc:odbc:tango");
 			_userTango = _p.getProperty("userTango", "tango");
 			_passWordTango = _p.getProperty("passWordTango", "tango");
 			
 			
 			try {
 			    // Se carga el driver JDBC-ODBC
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
 		nro_legajo = getChoferesNroLegajo(row);

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

 				personal_id = getChoferesPersonalId(row);

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
 		
 		// Si el legajo es correcto
 		if (!hubo_errores) {
 			// Verifica que la fecha del parte coincida con un periodo de vigencia del legajo
 			if ( (getChoferesHasta(row) != null) && (!getChoferesHasta(row).after(getChoferesDesde(row))) ) {
 				setMensajeError(row, "Combinación de fechas inválida");
 				hubo_errores = true;
 			} 
 			else if (!controlaFechaIngresoEgreso(this.getChoferesDesde(row), this.getChoferesHasta(row), personal_id)) {			
 				setMensajeError(row, "Legajo dado de baja durante la fecha del parte");
 				hubo_errores = true;			
 			}

 			// setea los datos recuperados
 			setChoferesApeynom(row, apeynom);
 			setChoferesNroLegajo(row, nro_legajo);
 			setChoferesPersonalId(row, personal_id);
 		}
 		return hubo_errores;
 	}
 	
 	/**
 	 * Controla que la fecha dada pertenezca a por lo menos un periodo en que el legajo haya o este dado de alta. 
 	 * @param fecha Fecha a controlar
 	 * @param legajoid Id del legajo
 	 * @return true si la fecha del parte corresponde a algun periodo valido del legajo
 	 * @throws DataStoreException
 	 */
 	public boolean controlaFechaIngresoEgreso(java.sql.Date desde, java.sql.Date hasta, int legajoid) throws DataStoreException {
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
 				// controla que el periodo corresponda con el ultimo ingreso del legajo
 				if ((controlaFechas(desde, fechaingreso, fechaegreso) == 0) && (fechaegreso == null)) {
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
 	
 	/**
	 * Controla que la fecha sea coherente con las fechas de inicio y fin de vigencia dadas
	 * @param fecha fecha del parte de mano de obra a ingresar
	 * @param vigenciaDesde fecha de inicio del intervalo
	 * @param vigenciaHasta fecha de fin del intervalo
	 * @return 0 si la fecha del parte se encuentra entras las fechas dadas, 1 si es posterior, -1 si es anterior
	 */
	private int controlaFechas(java.sql.Date fecha, java.sql.Date vigenciaDesde, java.sql.Date vigenciaHasta) {		
		if (fecha == null) return (-1);
		if ((vigenciaDesde != null) && (fecha.compareTo(vigenciaDesde) <=0)) return (-1); 
		if ((vigenciaHasta != null) && (fecha.compareTo(vigenciaHasta) >=0)) return 1;
		return 0;
	}
	
	//$ENDCUSTOMMETHODS$
     
}
