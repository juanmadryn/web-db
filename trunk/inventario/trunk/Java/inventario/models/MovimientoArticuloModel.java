package inventario.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * MovimientoArticuloModel: A SOFIA generated model
 */
public class MovimientoArticuloModel extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -9185545827130310022L;
	//constants for columns
     public static final String MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID="movimiento_articulo.movimiento_articulo_id";
     public static final String MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID="movimiento_articulo.centro_costo_id";
     public static final String MOVIMIENTO_ARTICULO_PROYECTO_ID="movimiento_articulo.proyecto_id";
     public static final String MOVIMIENTO_ARTICULO_TAREA_ID="movimiento_articulo.tarea_id";
     public static final String MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID="movimiento_articulo.resumen_saldo_articulo_id";
     public static final String MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID="movimiento_articulo.comprobante_movimiento_id";
     public static final String MOVIMIENTO_ARTICULO_ARTICULO_ID="movimiento_articulo.articulo_id";
     public static final String MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA="movimiento_articulo.cantidad_solicitada";
     public static final String MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA="movimiento_articulo.cantidad_entregada";
     public static final String MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA="movimiento_articulo.cantidad_anulada";
     public static final String MOVIMIENTO_ARTICULO_DESCRIPCION="movimiento_articulo.descripcion";
     public static final String MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL="movimiento_articulo.descripcion_adicional";
     public static final String ARTICULOS_NOMBRE="articulos.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new MovimientoArticuloModel object.
      * @param appName The SOFIA application name
      */
     public MovimientoArticuloModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new MovimientoArticuloModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public MovimientoArticuloModel (String appName, String profile) { 
          super(appName, profile);

          try {

               //add aliases
               addTableAlias(computeTableName("movimiento_articulo"),"movimiento_articulo");
               addTableAlias(computeTableName("articulos"),"articulos");
               addTableAlias(computeTableName("resumen_saldo_articulos"),"resumen_saldo_articulos");
               addTableAlias(computeTableName("comprobante_movimiento_articulo"),"comprobante_movimiento_articulo");

               //add columns
               addColumn(computeTableName("movimiento_articulo"),"movimiento_articulo_id",DataStore.DATATYPE_INT,true,true,MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
               addColumn(computeTableName("movimiento_articulo"),"centro_costo_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
               addColumn(computeTableName("movimiento_articulo"),"proyecto_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_PROYECTO_ID);
               addColumn(computeTableName("movimiento_articulo"),"tarea_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_TAREA_ID);
               addColumn(computeTableName("movimiento_articulo"),"resumen_saldo_articulo_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
               addColumn(computeTableName("movimiento_articulo"),"comprobante_movimiento_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
               addColumn(computeTableName("movimiento_articulo"),"articulo_id",DataStore.DATATYPE_INT,false,true,MOVIMIENTO_ARTICULO_ARTICULO_ID);
               addColumn(computeTableName("movimiento_articulo"),"cantidad_solicitada",DataStore.DATATYPE_DOUBLE,false,true,MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
               addColumn(computeTableName("movimiento_articulo"),"cantidad_entregada",DataStore.DATATYPE_DOUBLE,false,true,MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
               addColumn(computeTableName("movimiento_articulo"),"cantidad_anulada",DataStore.DATATYPE_DOUBLE,false,true,MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
               addColumn(computeTableName("movimiento_articulo"),"descripcion",DataStore.DATATYPE_STRING,false,true,MOVIMIENTO_ARTICULO_DESCRIPCION);
               addColumn(computeTableName("movimiento_articulo"),"descripcion_adicional",DataStore.DATATYPE_STRING,false,true,MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL);
               addColumn(computeTableName("articulos"),"nombre",DataStore.DATATYPE_STRING,false,false,ARTICULOS_NOMBRE);

               //add joins
               addJoin(computeTableAndFieldName("movimiento_articulo.articulo_id"),computeTableAndFieldName("articulos.articulo_id"),false);
               addJoin(computeTableAndFieldName("movimiento_articulo.resumen_saldo_articulo_id"),computeTableAndFieldName("resumen_saldo_articulos.resumen_saldo_articulo_id"),false);
               addJoin(computeTableAndFieldName("movimiento_articulo.comprobante_movimiento_id"),computeTableAndFieldName("comprobante_movimiento_articulo.comprobante_movimiento_id"),false);

               //set order by
               setOrderBy(computeTableAndFieldName("movimiento_articulo.movimiento_articulo_id") + " ASC");

               //add validations
               addRequiredRule(MOVIMIENTO_ARTICULO_ARTICULO_ID,"El id del artículo es obligatorio");
               addRequiredRule(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA,"La cantidad solicitada es obligatoria");
               addRequiredRule(MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA,"La cantidad entregada es obligatoria");
               addRequiredRule(MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA,"La cantidad anulada es obligatoria");
          }
          catch (DataStoreException e) {
               com.salmonllc.util.MessageLog.writeErrorMessage(e,this);
          }

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the movimiento_articulo.movimiento_articulo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloMovimientoArticuloId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.movimiento_articulo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloMovimientoArticuloId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.movimiento_articulo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloMovimientoArticuloId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.movimiento_articulo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloMovimientoArticuloId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_MOVIMIENTO_ARTICULO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.centro_costo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloCentroCostoId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.centro_costo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloCentroCostoId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.centro_costo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCentroCostoId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.centro_costo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCentroCostoId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_CENTRO_COSTO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.proyecto_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloProyectoId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_PROYECTO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.proyecto_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloProyectoId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_PROYECTO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.proyecto_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloProyectoId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_PROYECTO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.proyecto_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloProyectoId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_PROYECTO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.tarea_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloTareaId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_TAREA_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.tarea_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloTareaId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_TAREA_ID);
     }

     /**
      * Set the value of the movimiento_articulo.tarea_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloTareaId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_TAREA_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.tarea_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloTareaId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_TAREA_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.resumen_saldo_articulo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloResumenSaldoArticuloId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.resumen_saldo_articulo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloResumenSaldoArticuloId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.resumen_saldo_articulo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloResumenSaldoArticuloId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.resumen_saldo_articulo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloResumenSaldoArticuloId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_RESUMEN_SALDO_ARTICULO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.comprobante_movimiento_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloComprobanteMovimientoId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.comprobante_movimiento_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloComprobanteMovimientoId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.comprobante_movimiento_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloComprobanteMovimientoId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.comprobante_movimiento_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloComprobanteMovimientoId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_COMPROBANTE_MOVIMIENTO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.articulo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloArticuloId() throws DataStoreException {
          return  getInt(MOVIMIENTO_ARTICULO_ARTICULO_ID);
     }

     /**
      * Retrieve the value of the movimiento_articulo.articulo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getMovimientoArticuloArticuloId(int row) throws DataStoreException {
          return  getInt(row,MOVIMIENTO_ARTICULO_ARTICULO_ID);
     }

     /**
      * Set the value of the movimiento_articulo.articulo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloArticuloId(int newValue) throws DataStoreException {
          setInt(MOVIMIENTO_ARTICULO_ARTICULO_ID, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.articulo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloArticuloId(int row,int newValue) throws DataStoreException {
          setInt(row,MOVIMIENTO_ARTICULO_ARTICULO_ID, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_solicitada column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadSolicitada() throws DataStoreException {
          return  getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_solicitada column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadSolicitada(int row) throws DataStoreException {
          return  getDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_solicitada column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadSolicitada(double newValue) throws DataStoreException {
          setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_solicitada column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadSolicitada(int row,double newValue) throws DataStoreException {
          setDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_SOLICITADA, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_entregada column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadEntregada() throws DataStoreException {
          return  getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_entregada column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadEntregada(int row) throws DataStoreException {
          return  getDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_entregada column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadEntregada(double newValue) throws DataStoreException {
          setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_entregada column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadEntregada(int row,double newValue) throws DataStoreException {
          setDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_ENTREGADA, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_anulada column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadAnulada() throws DataStoreException {
          return  getDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
     }

     /**
      * Retrieve the value of the movimiento_articulo.cantidad_anulada column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getMovimientoArticuloCantidadAnulada(int row) throws DataStoreException {
          return  getDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_anulada column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadAnulada(double newValue) throws DataStoreException {
          setDouble(MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.cantidad_anulada column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloCantidadAnulada(int row,double newValue) throws DataStoreException {
          setDouble(row,MOVIMIENTO_ARTICULO_CANTIDAD_ANULADA, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMovimientoArticuloDescripcion() throws DataStoreException {
          return  getString(MOVIMIENTO_ARTICULO_DESCRIPCION);
     }

     /**
      * Retrieve the value of the movimiento_articulo.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMovimientoArticuloDescripcion(int row) throws DataStoreException {
          return  getString(row,MOVIMIENTO_ARTICULO_DESCRIPCION);
     }

     /**
      * Set the value of the movimiento_articulo.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloDescripcion(String newValue) throws DataStoreException {
          setString(MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,MOVIMIENTO_ARTICULO_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the movimiento_articulo.descripcion_adicional column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getMovimientoArticuloDescripcionAdicional() throws DataStoreException {
          return  getString(MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL);
     }

     /**
      * Retrieve the value of the movimiento_articulo.descripcion_adicional column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getMovimientoArticuloDescripcionAdicional(int row) throws DataStoreException {
          return  getString(row,MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL);
     }

     /**
      * Set the value of the movimiento_articulo.descripcion_adicional column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloDescripcionAdicional(String newValue) throws DataStoreException {
          setString(MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL, newValue);
     }

     /**
      * Set the value of the movimiento_articulo.descripcion_adicional column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setMovimientoArticuloDescripcionAdicional(int row,String newValue) throws DataStoreException {
          setString(row,MOVIMIENTO_ARTICULO_DESCRIPCION_ADICIONAL, newValue);
     }

     /**
      * Retrieve the value of the articulos.nombre column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosNombre() throws DataStoreException {
          return  getString(ARTICULOS_NOMBRE);
     }

     /**
      * Retrieve the value of the articulos.nombre column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosNombre(int row) throws DataStoreException {
          return  getString(row,ARTICULOS_NOMBRE);
     }

     /**
      * Set the value of the articulos.nombre column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosNombre(String newValue) throws DataStoreException {
          setString(ARTICULOS_NOMBRE, newValue);
     }

     /**
      * Set the value of the articulos.nombre column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosNombre(int row,String newValue) throws DataStoreException {
          setString(row,ARTICULOS_NOMBRE, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
