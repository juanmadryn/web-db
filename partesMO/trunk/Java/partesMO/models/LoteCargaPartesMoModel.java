package partesMO.models;

import infraestructura.controllers.WebSiteUser;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * LoteCargaPartesMoModel: A SOFIA generated model
 */
public class LoteCargaPartesMoModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 5918090755862269507L;
	//constants for columns
     public static final String LOTE_CARGA_PARTES_MO_LOTE_ID="lote_carga_partes_mo.lote_id";
     public static final String LOTE_CARGA_PARTES_MO_ESTADO="lote_carga_partes_mo.estado";
     public static final String LOTE_CARGA_PARTES_MO_FECHA_ALTA="lote_carga_partes_mo.fecha_alta";
     public static final String LOTE_CARGA_PARTES_MO_FECHA_CIERRE="lote_carga_partes_mo.fecha_cierre";
     public static final String LOTE_CARGA_PARTES_MO_DESCRIPCION="lote_carga_partes_mo.descripcion";
     public static final String ESTADOS_NOMBRE="estados.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new LoteCargaPartesMoModel object.
      * @param appName The SOFIA application name
      */
     public LoteCargaPartesMoModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new LoteCargaPartesMoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public LoteCargaPartesMoModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("lote_carga_partes_mo"),"lote_carga_partes_mo");
               addTableAlias(computeTableName("infraestructura.estados"),"estados");

               //add columns
               addColumn(computeTableName("lote_carga_partes_mo"),"lote_id",DataStore.DATATYPE_INT,true,true,LOTE_CARGA_PARTES_MO_LOTE_ID);
               addColumn(computeTableName("lote_carga_partes_mo"),"estado",DataStore.DATATYPE_STRING,false,true,LOTE_CARGA_PARTES_MO_ESTADO);
               addColumn(computeTableName("lote_carga_partes_mo"),"fecha_alta",DataStore.DATATYPE_DATE,false,true,LOTE_CARGA_PARTES_MO_FECHA_ALTA);
               addColumn(computeTableName("lote_carga_partes_mo"),"fecha_cierre",DataStore.DATATYPE_DATE,false,true,LOTE_CARGA_PARTES_MO_FECHA_CIERRE);
               addColumn(computeTableName("lote_carga_partes_mo"),"descripcion",DataStore.DATATYPE_STRING,false,true,LOTE_CARGA_PARTES_MO_DESCRIPCION);
               addColumn(computeTableName("infraestructura.estados"),"nombre",DataStore.DATATYPE_STRING,false,false,ESTADOS_NOMBRE);

               //set order by
               setOrderBy(computeTableAndFieldName("lote_carga_partes_mo.lote_id") + " DESC");
               
               // add joins
               addJoin(computeTableAndFieldName(LOTE_CARGA_PARTES_MO_ESTADO),computeTableAndFieldName("estados.estado"),true);

               //add validations
               addRequiredRule(LOTE_CARGA_PARTES_MO_ESTADO,"Estado obligatorio");
               addRequiredRule(LOTE_CARGA_PARTES_MO_FECHA_ALTA,"Fecha de alta obligatoria");
               addRequiredRule(LOTE_CARGA_PARTES_MO_LOTE_ID,"ID LOTE Obligatorio");
               addLookupRule(LOTE_CARGA_PARTES_MO_ESTADO,"infraestructura.estados","'infraestructura.estados.estado = \"' + lote_carga_partes_mo.estado + '\"' ","nombre","estados.nombre","Estado inexistente");
               
               // Autoincrement
               setAutoIncrement(LOTE_CARGA_PARTES_MO_LOTE_ID, true);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.lote_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getLoteCargaPartesMoLoteId() throws DataStoreException {
          return  getInt(LOTE_CARGA_PARTES_MO_LOTE_ID);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.lote_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getLoteCargaPartesMoLoteId(int row) throws DataStoreException {
          return  getInt(row,LOTE_CARGA_PARTES_MO_LOTE_ID);
     }

     /**
      * Set the value of the lote_carga_partes_mo.lote_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoLoteId(int newValue) throws DataStoreException {
          setInt(LOTE_CARGA_PARTES_MO_LOTE_ID, newValue);
     }

     /**
      * Set the value of the lote_carga_partes_mo.lote_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoLoteId(int row,int newValue) throws DataStoreException {
          setInt(row,LOTE_CARGA_PARTES_MO_LOTE_ID, newValue);
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
      * Retrieve the value of the lote_carga_partes_mo.Descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getLoteCargaPartesMoDescripcion() throws DataStoreException {
          return  getString(LOTE_CARGA_PARTES_MO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the lote_carga_partes_mo.Descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getLoteCargaPartesMoDescripcion(int row) throws DataStoreException {
          return  getString(row,LOTE_CARGA_PARTES_MO_DESCRIPCION);
     }

     /**
      * Set the value of the lote_carga_partes_mo.Descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoDescripcion(String newValue) throws DataStoreException {
          setString(LOTE_CARGA_PARTES_MO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the lote_carga_partes_mo.Descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setLoteCargaPartesMoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,LOTE_CARGA_PARTES_MO_DESCRIPCION, newValue);
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
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     /**
      * @param row nro de registro sobre el que se ejecuta la acción
      * @param accion accion grabada en la tabla de tarnsición de estados
      * @param circuito circuito al cual pertenece la acción. Permite recuperar la columna de estado
      * Ejecuta la acción dada para parte y lo cambia de estado según corresponda.
      * Concentra TODAS las acciones posibles para un parte de MO
      * @throws DataStoreException 
      */
     public void ejecutaAccion(int row, String accion, String circuito, WebSiteUser user, String host) throws DataStoreException {
    	 if (!gotoRow(row)){
 		 throw new DataStoreException("Fila " + row + " fuera de contexto para el lote");
 	 }
 	 
 	 ejecutaAccion(accion,circuito,user,host);

     }

     	
     /**
      * @param accion accion grabada en la tabla de tarnsición de estados
      * @param circuito circuito al cual pertenece la acción. Permite recuperar la columna de estado
      * Ejecuta la acción dada para parte y lo cambia de estado según corresponda.
      * Concentra TODAS las acciones posibles para un parte de MO
      * @throws DataStoreException 
      */
     public void ejecutaAccion(String accion, String circuito, WebSiteUser user, String host) throws DataStoreException {
 		String estado_actual = null;
 		String proximo_estado = null;
 		String nombre_accion = null;
 		String validador = null;
 		DBConnection conn = null;
 		Statement st = null;
 		ResultSet r = null;
 		String SQL;
 		StringBuilder resultado;
 		boolean ok = false;
 		
 		// verifico si está conectado un usuario
 		if (user == null){
 			throw new DataStoreException("Debe estar conectado como un usuario de la aplicación...");
 		}
 		
 		// chequeo que el informe está en contexto de informe
 		if (getRow() == -1) {
 			throw new DataStoreException("No hay seleccionado ningún parte");
 		}
 		
 		// correspondiente y ejecutarla
 		try {
 			conn = DBConnection.getConnection("partesMO");
 			conn.beginTransaction();

 			estado_actual = getLoteCargaPartesMoEstado();

 			// recupero el próximo estado y el nombre de la acción en función dela acción
 			SQL = "SELECT t.estado_destino,a.nombre,t.validador "
 					+ " FROM infraestructura.transicion_estados t "
 					+ " left join infraestructura.estados e on t.estado_origen = e.estado "
 					+ " left join infraestructura.acciones_apps a on a.accion = t.accion "
 					+ " where e.circuito = '" + circuito + "'"
 					+ " and t.estado_origen = '" + estado_actual + "'"
 					+ " and t.accion = " + accion;
 			st = conn.createStatement();
 			r = st.executeQuery(SQL);

 			if (r.first()) {
 				proximo_estado = r.getString(1);
 				nombre_accion = r.getString(2);
 				validador = r.getString(3);
 			}

 			// Verifica rutina de validación dinmica
 			try {
 				if (validador != null && validador.length() > 0 && !validador.equalsIgnoreCase("No Validar")){
 					Class claseVal = Class.forName(validador);
 					ValidadorReglasNegocio val = (ValidadorReglasNegocio) claseVal.newInstance();
 					resultado = new StringBuilder("");
 					if (val.esValido(this,resultado,conn)) {
 						ok = true;
 					} else{
 						ok = false;
 					}
 				}
 				else if (validador == null || validador.length() == 0){
 					throw new DataStoreException(nombre_accion + " -- No tiene implementada Validación. Se requiere especificar Validación");
 				}
 				else if (validador.equalsIgnoreCase("No Validar")){
 					// La regla NO requiere de validación
 					ok = true;
 				}
 				else {
 					throw new DataStoreException(nombre_accion + " -- Situación no prevista");
 				}
 			} catch (ClassNotFoundException e) {
 				MessageLog.writeErrorMessage(e, null);
 				throw new DataStoreException("ClassNotFoundException: " + e.getMessage());
 			} catch (InstantiationException e) {
 				MessageLog.writeErrorMessage(e, null);
 				throw new DataStoreException("InstantiationException: " + e.getMessage());
 			} catch (IllegalAccessException e) {
 				MessageLog.writeErrorMessage(e, null);
 				throw new DataStoreException("IllegalAccessException: " + e.getMessage());
 			}
 			

 			// si hay cambio de estado al finalizar, independientemente de la
 			// acción paso al próximo estado  y actualizo
 			// Se inserta también el registro de auditoría correspondiente sólo si cambiá estado
 			if (ok && !estado_actual.equalsIgnoreCase(proximo_estado)) {
 				setLoteCargaPartesMoEstado(proximo_estado);
 				
 				update(conn);

 				SQL = "insert into audita_estados_circuitos"
 						+ " (circuito,fecha,de_estado,a_estado,user_id,clave_primaria,host,accion) values"
 						+ " ('" + circuito + "',now(),'" + estado_actual
 						+ "','" + proximo_estado + "'," + user.getUserID()
 						+ "," + getLoteCargaPartesMoLoteId() 
 						+ ",'" + host	
 						+ "'," + accion + ")";
 				st = conn.createStatement();
 				st.executeUpdate(SQL);
 				
 			}
 			if (ok)
 				conn.commit();
 			else
 				conn.rollback();

 		} catch (SQLException e) {
 			MessageLog.writeErrorMessage(e, null);
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

 			if (conn != null) {
 				conn.rollback();
 				conn.freeConnection();
 			}
 		}
 	}
     
     //$ENDCUSTOMMETHODS$
     
}
