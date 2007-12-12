package partesMO.models;

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
 * SupervisoresModel: A SOFIA generated model
 */
public class SupervisoresModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -3505353622294077318L;
	//constants for columns
     public static final String SUPERVISORES_PERSONAL_ID="supervisores.personal_id";
     public static final String SUPERVISORES_NRO_LEGAJO="supervisores.nro_legajo";
     public static final String SUPERVISORES_APEYNOM="supervisores.apeynom";
     public static final String SUPERVISORES_FECHA_DESDE="supervisores.fecha_desde";
     public static final String SUPERVISORES_FECHA_HASTA="supervisores.fecha_hasta";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
	 private Connection _connTango = null;
	 private Props  _p = null;
	 private String _driverTango = null;
	 private String _urlTango = null;
	 private String _userTango = null;
	 private String _passWordTango = null;

     //$ENDCUSTOMVARS$
     
     /**
      * Create a new SupervisoresModel object.
      * @param appName The SOFIA application name
      */
     public SupervisoresModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new SupervisoresModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public SupervisoresModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("supervisores"),"supervisores");

          //add columns
          addColumn(computeTableName("supervisores"),"personal_id",DataStore.DATATYPE_INT,true,true,SUPERVISORES_PERSONAL_ID);
          addColumn(computeTableName("supervisores"),"nro_legajo",DataStore.DATATYPE_INT,true,true,SUPERVISORES_NRO_LEGAJO);
          addColumn(computeTableName("supervisores"),"apeynom",DataStore.DATATYPE_STRING,false,true,SUPERVISORES_APEYNOM);
          addColumn(computeTableName("supervisores"),"fecha_desde",DataStore.DATATYPE_DATE,false,true,SUPERVISORES_FECHA_DESDE);
          addColumn(computeTableName("supervisores"),"fecha_hasta",DataStore.DATATYPE_DATE,false,true,SUPERVISORES_FECHA_HASTA);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the supervisores.personal_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getSupervisoresPersonalId() throws DataStoreException {
          return  getInt(SUPERVISORES_PERSONAL_ID);
     }

     /**
      * Retrieve the value of the supervisores.personal_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getSupervisoresPersonalId(int row) throws DataStoreException {
          return  getInt(row,SUPERVISORES_PERSONAL_ID);
     }

     /**
      * Set the value of the supervisores.personal_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresPersonalId(int newValue) throws DataStoreException {
          setInt(SUPERVISORES_PERSONAL_ID, newValue);
     }

     /**
      * Set the value of the supervisores.personal_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresPersonalId(int row,int newValue) throws DataStoreException {
          setInt(row,SUPERVISORES_PERSONAL_ID, newValue);
     }

     /**
      * Retrieve the value of the supervisores.NroLegajo column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getSupervisoresNroLegajo() throws DataStoreException {
          return  getInt(SUPERVISORES_NRO_LEGAJO);
     }

     /**
      * Retrieve the value of the supervisores.NroLegajo column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getSupervisoresNroLegajo(int row) throws DataStoreException {
          return  getInt(row,SUPERVISORES_NRO_LEGAJO);
     }

     /**
      * Set the value of the supervisores.NroLegajo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresNroLegajo(int newValue) throws DataStoreException {
          setInt(SUPERVISORES_NRO_LEGAJO, newValue);
     }

     /**
      * Set the value of the supervisores.NroLegajo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresNroLegajo(int row,int newValue) throws DataStoreException {
          setInt(row,SUPERVISORES_NRO_LEGAJO, newValue);
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
      * Retrieve the value of the supervisores.fecha_desde column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getSupervisoresFechaDesde() throws DataStoreException {
          return  getDate(SUPERVISORES_FECHA_DESDE);
     }

     /**
      * Retrieve the value of the supervisores.fecha_desde column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getSupervisoresFechaDesde(int row) throws DataStoreException {
          return  getDate(row,SUPERVISORES_FECHA_DESDE);
     }

     /**
      * Set the value of the supervisores.fecha_desde column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresFechaDesde(java.sql.Date newValue) throws DataStoreException {
          setDate(SUPERVISORES_FECHA_DESDE, newValue);
     }

     /**
      * Set the value of the supervisores.fecha_desde column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresFechaDesde(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,SUPERVISORES_FECHA_DESDE, newValue);
     }

     /**
      * Retrieve the value of the supervisores.fecha_hasta column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getSupervisoresFechaHasta() throws DataStoreException {
          return  getDate(SUPERVISORES_FECHA_HASTA);
     }

     /**
      * Retrieve the value of the supervisores.fecha_hasta column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getSupervisoresFechaHasta(int row) throws DataStoreException {
          return  getDate(row,SUPERVISORES_FECHA_HASTA);
     }

     /**
      * Set the value of the supervisores.fecha_hasta column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresFechaHasta(java.sql.Date newValue) throws DataStoreException {
          setDate(SUPERVISORES_FECHA_HASTA, newValue);
     }

     /**
      * Set the value of the supervisores.fecha_hasta column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setSupervisoresFechaHasta(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,SUPERVISORES_FECHA_HASTA, newValue);
     }
	 
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
    
    @Override
 	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, SQLException {
    	controlaLegajoTango();
 		super.update(conn, handleTrans);
 	}     
     
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
	
	public boolean controlaLegajoTango() throws DataStoreException {
		Connection connTango;
		Statement st = null;
		ResultSet r = null;
		int personal_id;
		int nro_legajo = -1;
		String apeynom = null;
		String nombre = null;
		String apellido = null;
		java.sql.Date fechaingreso = null;
		String SQL = null;
		boolean hubo_errores = false;

		// realiza las tareas de lookup sobre los datos de tango y completa información
 		for (int i = 0; i < getRowCount(); i++) {
			// verifico lookup y actualiza los datos automáticos en caso de insertar
 			if (getRowStatus(i) == STATUS_NEW_MODIFIED || getRowStatus(i) == STATUS_MODIFIED) {
 				
 				// recupera la conexión a tango
 				connTango = getConexionTango();
 				nro_legajo = getSupervisoresNroLegajo(i);

 				try {
 					// Legajos que tengan un periodo de vigencia actual
 					SQL = "select L.ID_LEGAJO, L.NOMBRE, L.APELLIDO, I.FECHA_INGRESO " 
 						+ " from LEGAJO L inner join INGRESO_EGRESO I "
 						+ " on L.ID_LEGAJO = I.ID_LEGAJO and I.FECHA_EGRESO IS NULL"
 						+ " where L.NRO_LEGAJO = " + Integer.toString(nro_legajo);
 					
 					st = connTango.createStatement(
 							ResultSet.TYPE_SCROLL_SENSITIVE,
 							ResultSet.CONCUR_READ_ONLY);
 					r = st.executeQuery(SQL);

 					if (r.first()) { 						
 						personal_id = r.getInt(1); // ID_LEGAJO
 						nombre = r.getString(2); // NOMBRE
 						apellido = r.getString(3); // APELLIDO
 						fechaingreso = r.getDate(4); // FECHA_INGRESO
 					} else {
 						// no es por el numero de legajo, busco por el id
 						r.close();
 						st.close();

 						personal_id = getSupervisoresPersonalId(i);
 						
 					    // Legajos que tengan un periodo de vigencia actual
 	 					SQL = "select L.NRO_LEGAJO, L.NOMBRE, L.APELLIDO, I.FECHA_INGRESO " 
 	 						+ " from LEGAJO L inner join INGRESO_EGRESO I "
 	 						+ " on L.ID_LEGAJO = I.ID_LEGAJO and I.FECHA_EGRESO IS NULL"
 	 						+ " where L.ID_LEGAJO = " + Integer.toString(personal_id); 	 					
 						
 						st = connTango.createStatement(
 								ResultSet.TYPE_SCROLL_SENSITIVE,
 								ResultSet.CONCUR_READ_ONLY);
 						r = st.executeQuery(SQL);

 						if (r.first()) {
 							nro_legajo = r.getInt(1); // NRO_LEGAJO
 							nombre = r.getString(2); // NOMBRE
 							apellido = r.getString(3); // APELLIDO
 							fechaingreso = r.getDate(4); // FECHA_INGRESO
 						} else {
 							// no existe el legajo, setea el error y marca el
 							// procesamiento con error
 							throw new DataStoreException("Legajo inexistente en tango");
 						}
 					}

 					apeynom = apellido + ", " + nombre;

 				} catch (SQLException e) {
 					MessageLog.writeErrorMessage(e, null);
 					// además de escribir en el log mando mensaje a la página
 					throw new DataStoreException("Error determinando legajo en tango: "	+ e.getMessage());
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
 				
 				// si la fecha de inicio como supervisor es anterior al ingreso de legajo
 				if (getSupervisoresFechaDesde(i).before(fechaingreso)) { 
 					throw new DataStoreException("La fecha de inicio es anterior a la fecha de ingreso del legajo");
 				} else { 				
 					//setea los datos recuperados
 					setSupervisoresApeynom(i, apeynom);
 					setSupervisoresNroLegajo(i, nro_legajo);
 					setSupervisoresPersonalId(i, personal_id);
 				}
 			}
 		}
 		
 		return hubo_errores;
	}
	//$ENDCUSTOMMETHODS$
     
}
