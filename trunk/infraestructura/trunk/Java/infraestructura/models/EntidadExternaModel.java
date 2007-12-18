package infraestructura.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * EntidadExternaModel: A SOFIA generated model
 */
public class EntidadExternaModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -3159968186874568306L;
	//constants for columns
     public static final String ENTIDAD_EXTERNA_ENTIDAD_ID="entidad_externa.entidad_id";
     public static final String ENTIDAD_EXTERNA_CODIGO="entidad_externa.codigo";
     public static final String ENTIDAD_EXTERNA_NOMBRE="entidad_externa.nombre";
     public static final String ENTIDAD_EXTERNA_DESCRIPCION="entidad_externa.descripcion";
     public static final String ENTIDAD_EXTERNA_OBSERVACIONES="entidad_externa.observaciones";
     public static final String ENTIDAD_EXTERNA_ACTIVO="entidad_externa.activo";
     public static final String ENTIDAD_EXTERNA_ANULADO="entidad_externa.anulado";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new EntidadExternaModel object.
      * @param appName The SOFIA application name
      */
     public EntidadExternaModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new EntidadExternaModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public EntidadExternaModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("entidad_externa"),"entidad_externa");

               //add columns
               addColumn(computeTableName("entidad_externa"),"entidad_id",DataStore.DATATYPE_INT,true,true,ENTIDAD_EXTERNA_ENTIDAD_ID);
               addColumn(computeTableName("entidad_externa"),"codigo",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_CODIGO);
               addColumn(computeTableName("entidad_externa"),"nombre",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_NOMBRE);
               addColumn(computeTableName("entidad_externa"),"descripcion",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_DESCRIPCION);
               addColumn(computeTableName("entidad_externa"),"observaciones",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_OBSERVACIONES);
               addColumn(computeTableName("entidad_externa"),"activo",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_ACTIVO);
               addColumn(computeTableName("entidad_externa"),"anulado",DataStore.DATATYPE_STRING,false,true,ENTIDAD_EXTERNA_ANULADO);

               //set order by
               setOrderBy(computeTableAndFieldName("entidad_externa.nombre") + " ASC");
               
               // setea autoincrement e impide update
               setAutoIncrement(ENTIDAD_EXTERNA_ENTIDAD_ID, true);
               setUpdateable(ENTIDAD_EXTERNA_ENTIDAD_ID, false);

               //add validations
               addRequiredRule(ENTIDAD_EXTERNA_NOMBRE,"El Nombre es obligatorio para la Entidad");
               addRequiredRule(ENTIDAD_EXTERNA_ACTIVO,"El indicador de activo es obligatorio");
               addRequiredRule(ENTIDAD_EXTERNA_ANULADO,"El indicador de anulado es obligatorio");
               addExpressionRule(ENTIDAD_EXTERNA_NOMBRE, new ConvierteMayusculasValidation(ENTIDAD_EXTERNA_NOMBRE), "",false);
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the entidad_externa.entidad_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getEntidadExternaEntidadId() throws DataStoreException {
          return  getInt(ENTIDAD_EXTERNA_ENTIDAD_ID);
     }

     /**
      * Retrieve the value of the entidad_externa.entidad_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getEntidadExternaEntidadId(int row) throws DataStoreException {
          return  getInt(row,ENTIDAD_EXTERNA_ENTIDAD_ID);
     }

     /**
      * Set the value of the entidad_externa.entidad_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaEntidadId(int newValue) throws DataStoreException {
          setInt(ENTIDAD_EXTERNA_ENTIDAD_ID, newValue);
     }

     /**
      * Set the value of the entidad_externa.entidad_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaEntidadId(int row,int newValue) throws DataStoreException {
          setInt(row,ENTIDAD_EXTERNA_ENTIDAD_ID, newValue);
     }

     /**
      * Retrieve the value of the entidad_externa.codigo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaCodigo() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_CODIGO);
     }

     /**
      * Retrieve the value of the entidad_externa.codigo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaCodigo(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_CODIGO);
     }

     /**
      * Set the value of the entidad_externa.codigo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaCodigo(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_CODIGO, newValue);
     }

     /**
      * Set the value of the entidad_externa.codigo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaCodigo(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_CODIGO, newValue);
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
      * Retrieve the value of the entidad_externa.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaDescripcion() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_DESCRIPCION);
     }

     /**
      * Retrieve the value of the entidad_externa.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaDescripcion(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_DESCRIPCION);
     }

     /**
      * Set the value of the entidad_externa.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaDescripcion(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the entidad_externa.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the entidad_externa.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaObservaciones() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the entidad_externa.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaObservaciones(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_OBSERVACIONES);
     }

     /**
      * Set the value of the entidad_externa.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaObservaciones(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the entidad_externa.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the entidad_externa.activo column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaActivo() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_ACTIVO);
     }

     /**
      * Retrieve the value of the entidad_externa.activo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaActivo(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_ACTIVO);
     }

     /**
      * Set the value of the entidad_externa.activo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaActivo(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_ACTIVO, newValue);
     }

     /**
      * Set the value of the entidad_externa.activo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaActivo(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_ACTIVO, newValue);
     }

     /**
      * Retrieve the value of the entidad_externa.anulado column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaAnulado() throws DataStoreException {
          return  getString(ENTIDAD_EXTERNA_ANULADO);
     }

     /**
      * Retrieve the value of the entidad_externa.anulado column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getEntidadExternaAnulado(int row) throws DataStoreException {
          return  getString(row,ENTIDAD_EXTERNA_ANULADO);
     }

     /**
      * Set the value of the entidad_externa.anulado column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaAnulado(String newValue) throws DataStoreException {
          setString(ENTIDAD_EXTERNA_ANULADO, newValue);
     }

     /**
      * Set the value of the entidad_externa.anulado column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setEntidadExternaAnulado(int row,String newValue) throws DataStoreException {
          setString(row,ENTIDAD_EXTERNA_ANULADO, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

     /** 
      * Completa las columnas por default para evitar probelmas con la base de datos
      * Sólo setea el default si no está definido aún 
      * @author demian
      * @param row indica sobre que fila del datastor se realiza la operación
      */
     public void setColumnasDefault(int row) throws DataStoreException {
    	 // solo setea las columnas si son null
    	 String col = getEntidadExternaActivo(row);
    	 if (col == null)
    		 setEntidadExternaActivo(row, "F");
    	 col = getEntidadExternaAnulado(row);
    	 if (col == null)
    		 setEntidadExternaAnulado(row, "F");
     }
     
     /**
      * Realiza el proceso de activación de una entidad
      * @param row registro dentro del datastor que se desea activar
      * @throws DataStoreException
      * @throws SQLException
      */
     public void activarEntidad(int row) throws DataStoreException, SQLException {
		String estado = getEntidadExternaActivo(row);
		if (estado == "V") {
			throw new DataStoreException("La entidad YA está activa");
		}
		
		//TODO: Se debe completar con las validaciones "reglas de negocio" para activar a una entidad
		
		setEntidadExternaActivo(row, "V");
		update();
	}

	@Override
	public void retrieve(DBConnection conn, String criteria) throws SQLException {
		
		String criterioAdicional = "entidad_externa.anulado = \"F\"";
		
 		if (criteria == null || criteria.length() == 0){
 			criteria = criterioAdicional;
 		}
  		else {
  			criteria = criteria + " and " + criterioAdicional;
  		}

		super.retrieve(conn, criteria);
	}
	
     //$ENDCUSTOMMETHODS$
     
     
}
