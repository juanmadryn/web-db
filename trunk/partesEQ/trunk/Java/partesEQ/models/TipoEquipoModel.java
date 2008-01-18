package partesEQ.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

/**
 * TipoEquipoModel: A SOFIA generated model
 */
public class TipoEquipoModel extends DataStore {

     /**
	 *
	 */
	private static final long serialVersionUID = -809963416878823634L;
	//constants for columns
     public static final String TIPO_EQUIPO_TIPO_EQUIPO="tipo_equipo.tipo_equipo";
     public static final String TIPO_EQUIPO_NOMBRE="tipo_equipo.nombre";
     public static final String TIPO_EQUIPO_DESCRIPCION="tipo_equipo.descripcion";
     public static final String TIPO_EQUIPO_OBSERVACIONES="tipo_equipo.observaciones";
     public static final String TIPO_EQUIPO_INVENTARIABLE="tipo_equipo.inventariable";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated

     //$ENDCUSTOMVARS$

     /**
      * Create a new TipoEquipoModel object.
      * @param appName The SOFIA application name
      */
     public TipoEquipoModel (String appName) {
          this(appName, null);
     }

     /**
      * Create a new TipoEquipoModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public TipoEquipoModel (String appName, String profile) {
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("tipo_equipo"),"tipo_equipo");

               //add columns
               addColumn(computeTableName("tipo_equipo"),"tipo_equipo",DataStore.DATATYPE_STRING,true,true,TIPO_EQUIPO_TIPO_EQUIPO);
               addColumn(computeTableName("tipo_equipo"),"nombre",DataStore.DATATYPE_STRING,false,true,TIPO_EQUIPO_NOMBRE);
               addColumn(computeTableName("tipo_equipo"),"descripcion",DataStore.DATATYPE_STRING,false,true,TIPO_EQUIPO_DESCRIPCION);
               addColumn(computeTableName("tipo_equipo"),"observaciones",DataStore.DATATYPE_STRING,false,true,TIPO_EQUIPO_OBSERVACIONES);
               addColumn(computeTableName("tipo_equipo"),"inventariable",DataStore.DATATYPE_STRING,false,true,TIPO_EQUIPO_INVENTARIABLE);

               //add validations
               addRequiredRule(TIPO_EQUIPO_TIPO_EQUIPO,"El tipo de equipo es obligatorio");
               addRequiredRule(TIPO_EQUIPO_NOMBRE,"El nombre del tipo es obligatorio");
               addRequiredRule(TIPO_EQUIPO_INVENTARIABLE,"Debe definir si este tipo de equipo es inventariable o no");
               addExpressionRule(TIPO_EQUIPO_TIPO_EQUIPO,new ConvierteMayusculasValidation(TIPO_EQUIPO_TIPO_EQUIPO),"",false);
               addExpressionRule(TIPO_EQUIPO_NOMBRE,new ConvierteMayusculasValidation(TIPO_EQUIPO_NOMBRE),"",false);

          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the tipo_equipo.tipo_equipo column for the current row.
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoTipoEquipo() throws DataStoreException {
          return  getString(TIPO_EQUIPO_TIPO_EQUIPO);
     }

     /**
      * Retrieve the value of the tipo_equipo.tipo_equipo column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoTipoEquipo(int row) throws DataStoreException {
          return  getString(row,TIPO_EQUIPO_TIPO_EQUIPO);
     }

     /**
      * Set the value of the tipo_equipo.tipo_equipo column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoTipoEquipo(String newValue) throws DataStoreException {
          setString(TIPO_EQUIPO_TIPO_EQUIPO, newValue);
     }

     /**
      * Set the value of the tipo_equipo.tipo_equipo column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoTipoEquipo(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_EQUIPO_TIPO_EQUIPO, newValue);
     }

     /**
      * Retrieve the value of the tipo_equipo.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoNombre() throws DataStoreException {
          return  getString(TIPO_EQUIPO_NOMBRE);
     }

     /**
      * Retrieve the value of the tipo_equipo.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoNombre(int row) throws DataStoreException {
          return  getString(row,TIPO_EQUIPO_NOMBRE);
     }

     /**
      * Set the value of the tipo_equipo.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoNombre(String newValue) throws DataStoreException {
          setString(TIPO_EQUIPO_NOMBRE, newValue);
     }

     /**
      * Set the value of the tipo_equipo.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoNombre(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_EQUIPO_NOMBRE, newValue);
     }

     /**
      * Retrieve the value of the tipo_equipo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoDescripcion() throws DataStoreException {
          return  getString(TIPO_EQUIPO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the tipo_equipo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoDescripcion(int row) throws DataStoreException {
          return  getString(row,TIPO_EQUIPO_DESCRIPCION);
     }

     /**
      * Set the value of the tipo_equipo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoDescripcion(String newValue) throws DataStoreException {
          setString(TIPO_EQUIPO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the tipo_equipo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_EQUIPO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the tipo_equipo.observaciones column for the current row.
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoObservaciones() throws DataStoreException {
          return  getString(TIPO_EQUIPO_OBSERVACIONES);
     }

     /**
      * Retrieve the value of the tipo_equipo.observaciones column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoObservaciones(int row) throws DataStoreException {
          return  getString(row,TIPO_EQUIPO_OBSERVACIONES);
     }

     /**
      * Set the value of the tipo_equipo.observaciones column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoObservaciones(String newValue) throws DataStoreException {
          setString(TIPO_EQUIPO_OBSERVACIONES, newValue);
     }

     /**
      * Set the value of the tipo_equipo.observaciones column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoObservaciones(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_EQUIPO_OBSERVACIONES, newValue);
     }

     /**
      * Retrieve the value of the tipo_equipo.inventariable column for the current row.
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoInventariable() throws DataStoreException {
          return  getString(TIPO_EQUIPO_INVENTARIABLE);
     }

     /**
      * Retrieve the value of the tipo_equipo.inventariable column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */
     public String getTipoEquipoInventariable(int row) throws DataStoreException {
          return  getString(row,TIPO_EQUIPO_INVENTARIABLE);
     }

     /**
      * Set the value of the tipo_equipo.inventariable column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoInventariable(String newValue) throws DataStoreException {
          setString(TIPO_EQUIPO_INVENTARIABLE, newValue);
     }

     /**
      * Set the value of the tipo_equipo.inventariable column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */
     public void setTipoEquipoInventariable(int row,String newValue) throws DataStoreException {
          setString(row,TIPO_EQUIPO_INVENTARIABLE, newValue);
     }

     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated

     //$ENDCUSTOMMETHODS$

}
