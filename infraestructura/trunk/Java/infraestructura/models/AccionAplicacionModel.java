package infraestructura.models;

import infraestructura.utils.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AccioAplicacionModel: A SOFIA generated model
 */
public class AccionAplicacionModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = 5156546636767496650L;
	//constants for columns
     public static final String ACCIONES_APLICACION_ACCION="acciones_apps.accion";
     public static final String ACCIONES_APLICACION_NOMBRE="acciones_apps.nombre";
     public static final String ACCIONES_APLICACION_DESCRIPCION="acciones_apps.descripcion";
     public static final String ACCIONES_APLICACION_OBSERVACIONES="acciones_apps.observaciones";
     public static final String ACCIONES_APLICACION_CIRCUITO="acciones_apps.circuito";
     public static final String CIRCUITOS_ESTADOS_NOMBRE="circuitos_estados.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new AccioAplicacionModel object.
      * @param appName The SOFIA application name
      */
     public AccionAplicacionModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new AccioAplicacionModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public AccionAplicacionModel(String appName, String profile) {
		super(appName, profile);

		try {
			// add columns
			addColumn(computeTableName("acciones_apps"), "accion",
					DataStore.DATATYPE_INT, true, true,
					ACCIONES_APLICACION_ACCION);
			addColumn(computeTableName("acciones_apps"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					ACCIONES_APLICACION_NOMBRE);
			addColumn(computeTableName("acciones_apps"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					ACCIONES_APLICACION_DESCRIPCION);
			addColumn(computeTableName("acciones_apps"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					ACCIONES_APLICACION_OBSERVACIONES);
			addColumn(computeTableName("acciones_apps"), "circuito",
					DataStore.DATATYPE_STRING, false, true,
					ACCIONES_APLICACION_CIRCUITO);
			addColumn(computeTableName("circuitos_estados"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CIRCUITOS_ESTADOS_NOMBRE);

			// add validations
			addRequiredRule(ACCIONES_APLICACION_NOMBRE,"El nombre de la acción es obligatoria");
			addRequiredRule(ACCIONES_APLICACION_CIRCUITO,"EL circuito al que pertenece la acción es obligatorio");
			addExpressionRule(ACCIONES_APLICACION_NOMBRE, new ConvierteMayusculasValidation(ACCIONES_APLICACION_NOMBRE), "", false);

			// add joins
			addJoin(computeTableAndFieldName("acciones_apps.circuito"),computeTableAndFieldName("circuitos_estados.circuito"),false);

			// $CUSTOMCONSTRUCTOR$
			// Put custom constructor code between these comments, otherwise it
			// be overwritten if the model is regenerated
			setAutoIncrement(ACCIONES_APLICACION_ACCION, true);
			// $ENDCUSTOMCONSTRUCTOR$

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
		}
	}

     /**
      * Retrieve the value of the acciones_apps.accion column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccionesAplicacionAccion() throws DataStoreException {
          return  getInt(ACCIONES_APLICACION_ACCION);
     }

     /**
      * Retrieve the value of the acciones_apps.accion column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getAccionesAplicacionAccion(int row) throws DataStoreException {
          return  getInt(row,ACCIONES_APLICACION_ACCION);
     }

     /**
      * Set the value of the acciones_apps.accion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionAccion(int newValue) throws DataStoreException {
          setInt(ACCIONES_APLICACION_ACCION, newValue);
     }

     /**
      * Set the value of the acciones_apps.accion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionAccion(int row,int newValue) throws DataStoreException {
          setInt(row,ACCIONES_APLICACION_ACCION, newValue);
     }

     /**
      * Retrieve the value of the acciones_apps.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionNombre() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_NOMBRE);
     }

     /**
      * Retrieve the value of the acciones_apps.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionNombre(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_NOMBRE);
     }

     /**
      * Set the value of the acciones_apps.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionNombre(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_NOMBRE, newValue);
     }

     /**
      * Set the value of the acciones_apps.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionNombre(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the acciones_apps.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionDescripcion() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_DESCRIPCION);
     }

     /**
      * Retrieve the value of the acciones_apps.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionDescripcion(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_DESCRIPCION);
     }

     /**
      * Set the value of the acciones_apps.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionDescripcion(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the acciones_apps.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the acciones_apps.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionObservaciones() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the acciones_apps.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionObservaciones(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_OBSERVACIONES);
     }

     /**
      * Set the value of the acciones_apps.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionObservaciones(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the acciones_apps.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the acciones_apps.circuito column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionCircuito() throws DataStoreException {
          return  getString(ACCIONES_APLICACION_CIRCUITO);
     }

     /**
      * Retrieve the value of the acciones_apps.circuito column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getAccionesAplicacionCircuito(int row) throws DataStoreException {
          return  getString(row,ACCIONES_APLICACION_CIRCUITO);
     }

     /**
      * Set the value of the acciones_apps.circuito column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionCircuito(String newValue) throws DataStoreException {
          setString(ACCIONES_APLICACION_CIRCUITO, newValue);
     }

     /**
      * Set the value of the acciones_apps.circuito column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setAccionesAplicacionCircuito(int row,String newValue) throws DataStoreException {
          setString(row,ACCIONES_APLICACION_CIRCUITO, newValue);
     }

     /**
      * Retrieve the value of the circuitos_estados.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosNombre() throws DataStoreException {
          return  getString(CIRCUITOS_ESTADOS_NOMBRE);
     }

     /**
      * Retrieve the value of the circuitos_estados.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCircuitosEstadosNombre(int row) throws DataStoreException {
          return  getString(row,CIRCUITOS_ESTADOS_NOMBRE);
     }

     /**
      * Set the value of the circuitos_estados.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosNombre(String newValue) throws DataStoreException {
          setString(CIRCUITOS_ESTADOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the circuitos_estados.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCircuitosEstadosNombre(int row,String newValue) throws DataStoreException {
          setString(row,CIRCUITOS_ESTADOS_NOMBRE, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
