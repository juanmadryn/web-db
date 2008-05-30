package inventario.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * DetalleCotizacionModel: A SOFIA generated model
 */
public class DetalleCotizacionModel extends BaseModel {

     /**
	 * 
	 */
	private static final long serialVersionUID = 5913677318410449388L;
	//constants for columns
     public static final String DETALLE_COTIZACION_DETALLE_SC_ID="detalle_cotizacion.detalle_SC_id";
     public static final String DETALLE_COTIZACION_COTIZACION_COMPRA_ID="detalle_cotizacion.cotizacion_compra_id";
     public static final String DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1="detalle_cotizacion.monto_unitario_proveedor1";
     public static final String DETALLE_COTIZACION_MARCA_PROVEEDOR1="detalle_cotizacion.marca_proveedor1";
     public static final String DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1="detalle_cotizacion.cotizacion_seleccionada_proveedor1";
     public static final String DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2="detalle_cotizacion.monto_unitario_proveedor2";
     public static final String DETALLE_COTIZACION_MARCA_PROVEEDOR2="detalle_cotizacion.marca_proveedor2";
     public static final String DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2="detalle_cotizacion.cotizacion_seleccionada_proveedor2";
     public static final String DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3="detalle_cotizacion.monto_unitario_proveedor3";
     public static final String DETALLE_COTIZACION_MARCA_PROVEEDOR3="detalle_cotizacion.marca_proveedor3";
     public static final String DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3="detalle_cotizacion.cotizacion_seleccionada_proveedor3";
     public static final String DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4="detalle_cotizacion.monto_unitario_proveedor4";
     public static final String DETALLE_COTIZACION_MARCA_PROVEEDOR4="detalle_cotizacion.marca_proveedor4";
     public static final String DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4="detalle_cotizacion.cotizacion_seleccionada_proveedor4";
     public static final String DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5="detalle_cotizacion.monto_unitario_proveedor5";
     public static final String DETALLE_COTIZACION_MARCA_PROVEEDOR5="detalle_cotizacion.marca_proveedor5";
     public static final String DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5="detalle_cotizacion.cotizacion_seleccionada_proveedor5";
     public static final String DETALLE_SC_ARTICULO_ID="detalle_sc.articulo_id";
     public static final String DETALLE_SC_SOLICITUD_COMPRA_ID="detalle_sc.solicitud_compra_id";
     public static final String DETALLE_SC_CANTIDAD_SOLICITADA="detalle_sc.cantidad_solicitada";
     public static final String DETALLE_SC_DESCRIPCION="detalle_sc.descripcion";
     public static final String DETALLE_SC_TAREA_ID="detalle_sc.tarea_id";
     public static final String DETALLE_SC_MONTO_ULTIMA_COMPRA="detalle_sc.monto_ultima_compra";
     public static final String DETALLE_SC_FECHA_ULTIMA_COMPRA="detalle_sc.fecha_ultima_compra";
     public static final String DETALLE_SC_UNIDAD_MEDIDA_ID="detalle_sc.unidad_medida_id";
     public static final String DETALLE_SC_ORDEN_COMPRA_ID="detalle_sc.orden_compra_id";
     public static final String ARTICULOS_NOMBRE="articulos.nombre";
     public static final String ARTICULOS_DESCRIPCION="articulos.descripcion";
     public static final String ARTICULOS_DESCRIPCION_COMPLETA="articulos.descripcion_completa";
     public static final String UNIDADES_MEDIDA_NOMBRE="unidades_medida.nombre";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new DetalleCotizacionModel object.
      * @param appName The SOFIA application name
      */
     public DetalleCotizacionModel (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new DetalleCotizacionModel object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public DetalleCotizacionModel (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("detalle_cotizacion"),null);
          addTableAlias(computeTableName("detalle_sc"),null);
          addTableAlias(computeTableName("articulos"),null);
          addTableAlias(computeTableName("unidades_medida"),null);

          //add columns
          addColumn(computeTableName("detalle_cotizacion"),"detalle_SC_id",DataStore.DATATYPE_INT,true,true,DETALLE_COTIZACION_DETALLE_SC_ID);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_compra_id",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_COMPRA_ID);
          addColumn(computeTableName("detalle_cotizacion"),"monto_unitario_proveedor1",DataStore.DATATYPE_DOUBLE,false,true,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1);
          addColumn(computeTableName("detalle_cotizacion"),"marca_proveedor1",DataStore.DATATYPE_STRING,false,true,DETALLE_COTIZACION_MARCA_PROVEEDOR1);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_seleccionada_proveedor1",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1);
          addColumn(computeTableName("detalle_cotizacion"),"monto_unitario_proveedor2",DataStore.DATATYPE_DOUBLE,false,true,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2);
          addColumn(computeTableName("detalle_cotizacion"),"marca_proveedor2",DataStore.DATATYPE_STRING,false,true,DETALLE_COTIZACION_MARCA_PROVEEDOR2);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_seleccionada_proveedor2",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2);
          addColumn(computeTableName("detalle_cotizacion"),"monto_unitario_proveedor3",DataStore.DATATYPE_DOUBLE,false,true,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3);
          addColumn(computeTableName("detalle_cotizacion"),"marca_proveedor3",DataStore.DATATYPE_STRING,false,true,DETALLE_COTIZACION_MARCA_PROVEEDOR3);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_seleccionada_proveedor3",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3);
          addColumn(computeTableName("detalle_cotizacion"),"monto_unitario_proveedor4",DataStore.DATATYPE_DOUBLE,false,true,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4);
          addColumn(computeTableName("detalle_cotizacion"),"marca_proveedor4",DataStore.DATATYPE_STRING,false,true,DETALLE_COTIZACION_MARCA_PROVEEDOR4);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_seleccionada_proveedor4",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4);
          addColumn(computeTableName("detalle_cotizacion"),"monto_unitario_proveedor5",DataStore.DATATYPE_DOUBLE,false,true,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5);
          addColumn(computeTableName("detalle_cotizacion"),"marca_proveedor5",DataStore.DATATYPE_STRING,false,true,DETALLE_COTIZACION_MARCA_PROVEEDOR5);
          addColumn(computeTableName("detalle_cotizacion"),"cotizacion_seleccionada_proveedor5",DataStore.DATATYPE_INT,false,true,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5);
          addColumn(computeTableName("detalle_sc"),"articulo_id",DataStore.DATATYPE_INT,false,false,DETALLE_SC_ARTICULO_ID);
          addColumn(computeTableName("detalle_sc"),"solicitud_compra_id",DataStore.DATATYPE_INT,false,false,DETALLE_SC_SOLICITUD_COMPRA_ID);
          addColumn(computeTableName("detalle_sc"),"cantidad_solicitada",DataStore.DATATYPE_DOUBLE,false,false,DETALLE_SC_CANTIDAD_SOLICITADA);
          addColumn(computeTableName("detalle_sc"),"descripcion",DataStore.DATATYPE_STRING,false,false,DETALLE_SC_DESCRIPCION);
          addColumn(computeTableName("detalle_sc"),"tarea_id",DataStore.DATATYPE_INT,false,false,DETALLE_SC_TAREA_ID);
          addColumn(computeTableName("detalle_sc"),"monto_ultima_compra",DataStore.DATATYPE_STRING,false,false,DETALLE_SC_MONTO_ULTIMA_COMPRA);
          addColumn(computeTableName("detalle_sc"),"fecha_ultima_compra",DataStore.DATATYPE_DATE,false,false,DETALLE_SC_FECHA_ULTIMA_COMPRA);
          addColumn(computeTableName("detalle_sc"),"unidad_medida_id",DataStore.DATATYPE_INT,false,false,DETALLE_SC_UNIDAD_MEDIDA_ID);
          addColumn(computeTableName("detalle_sc"),"orden_compra_id",DataStore.DATATYPE_INT,false,false,DETALLE_SC_ORDEN_COMPRA_ID);
          addColumn(computeTableName("articulos"),"nombre",DataStore.DATATYPE_STRING,false,false,ARTICULOS_NOMBRE);
          addColumn(computeTableName("articulos"),"descripcion",DataStore.DATATYPE_STRING,false,false,ARTICULOS_DESCRIPCION);
          addColumn(computeTableName("articulos"),"descripcion_completa",DataStore.DATATYPE_STRING,false,false,ARTICULOS_DESCRIPCION_COMPLETA);
          addColumn(computeTableName("unidades_medida"),"nombre",DataStore.DATATYPE_STRING,false,false,UNIDADES_MEDIDA_NOMBRE);

          //add joins
          addJoin(computeTableAndFieldName("detalle_cotizacion.detalle_SC_id"),computeTableAndFieldName("detalle_sc.detalle_SC_id"),false);
          addJoin(computeTableAndFieldName("detalle_sc.articulo_id"),computeTableAndFieldName("articulos.articulo_id"),false);
          addJoin(computeTableAndFieldName("detalle_sc.unidad_medida_id"),computeTableAndFieldName("unidades_medida.unidad_medida_id"),false);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the detalle_cotizacion.detalle_SC_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionDetalleScId() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_DETALLE_SC_ID);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.detalle_SC_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionDetalleScId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_DETALLE_SC_ID);
     }

     /**
      * Set the value of the detalle_cotizacion.detalle_SC_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionDetalleScId(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_DETALLE_SC_ID, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.detalle_SC_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionDetalleScId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_DETALLE_SC_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_compra_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionCompraId() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_COMPRA_ID);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_compra_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionCompraId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_COMPRA_ID);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_compra_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionCompraId(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_COMPRA_ID, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_compra_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionCompraId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_COMPRA_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor1 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor1() throws DataStoreException {
          return  getDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor1 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor1(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor1(double newValue) throws DataStoreException {
          setDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor1(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR1, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor1 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor1() throws DataStoreException {
          return  getString(DETALLE_COTIZACION_MARCA_PROVEEDOR1);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor1 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor1(int row) throws DataStoreException {
          return  getString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR1);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor1(String newValue) throws DataStoreException {
          setString(DETALLE_COTIZACION_MARCA_PROVEEDOR1, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor1(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR1, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor1 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor1() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor1 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor1(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor1(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor1(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR1, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor2 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor2() throws DataStoreException {
          return  getDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor2 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor2(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor2(double newValue) throws DataStoreException {
          setDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor2(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR2, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor2 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMaracaProveedor2() throws DataStoreException {
          return  getString(DETALLE_COTIZACION_MARCA_PROVEEDOR2);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor2 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMaracaProveedor2(int row) throws DataStoreException {
          return  getString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR2);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMaracaProveedor2(String newValue) throws DataStoreException {
          setString(DETALLE_COTIZACION_MARCA_PROVEEDOR2, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMaracaProveedor2(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR2, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor2 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor2() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor2 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor2(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor2(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor2(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR2, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor3 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor3() throws DataStoreException {
          return  getDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor3 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor3(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor3 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor3(double newValue) throws DataStoreException {
          setDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor3 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor3(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR3, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor3 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor3() throws DataStoreException {
          return  getString(DETALLE_COTIZACION_MARCA_PROVEEDOR3);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor3 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor3(int row) throws DataStoreException {
          return  getString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR3);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor3 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor3(String newValue) throws DataStoreException {
          setString(DETALLE_COTIZACION_MARCA_PROVEEDOR3, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor3 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor3(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR3, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor3 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor3() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor3 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor3(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor3 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor3(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor3 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor3(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR3, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor4 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor4() throws DataStoreException {
          return  getDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor4 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor4(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor4 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor4(double newValue) throws DataStoreException {
          setDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor4 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor4(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR4, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor4 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor4() throws DataStoreException {
          return  getString(DETALLE_COTIZACION_MARCA_PROVEEDOR4);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor4 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor4(int row) throws DataStoreException {
          return  getString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR4);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor4 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor4(String newValue) throws DataStoreException {
          setString(DETALLE_COTIZACION_MARCA_PROVEEDOR4, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor4 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor4(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR4, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor4 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor4() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor4 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor4(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor4 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor4(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor4 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor4(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR4, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor5 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor5() throws DataStoreException {
          return  getDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.monto_unitario_proveedor5 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleCotizacionMontoUnitarioProveedor5(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor5(double newValue) throws DataStoreException {
          setDouble(DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.monto_unitario_proveedor5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMontoUnitarioProveedor5(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_COTIZACION_MONTO_UNITARIO_PROVEEDOR5, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor5 column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor5() throws DataStoreException {
          return  getString(DETALLE_COTIZACION_MARCA_PROVEEDOR5);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.marca_proveedor5 column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleCotizacionMarcaProveedor5(int row) throws DataStoreException {
          return  getString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR5);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor5(String newValue) throws DataStoreException {
          setString(DETALLE_COTIZACION_MARCA_PROVEEDOR5, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.marca_proveedor5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionMarcaProveedor5(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_COTIZACION_MARCA_PROVEEDOR5, newValue);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor5 column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor5() throws DataStoreException {
          return  getInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5);
     }

     /**
      * Retrieve the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor5 column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleCotizacionCotizacionSeleccionadaProveedor5(int row) throws DataStoreException {
          return  getInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor5 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor5(int newValue) throws DataStoreException {
          setInt(DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5, newValue);
     }

     /**
      * Set the value of the detalle_cotizacion.cotizacion_seleccionada_proveedor5 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleCotizacionCotizacionSeleccionadaProveedor5(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_COTIZACION_COTIZACION_SELECCIONADA_PROVEEDOR5, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.articulo_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScArticuloId() throws DataStoreException {
          return  getInt(DETALLE_SC_ARTICULO_ID);
     }

     /**
      * Retrieve the value of the detalle_sc.articulo_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScArticuloId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_SC_ARTICULO_ID);
     }

     /**
      * Set the value of the detalle_sc.articulo_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScArticuloId(int newValue) throws DataStoreException {
          setInt(DETALLE_SC_ARTICULO_ID, newValue);
     }

     /**
      * Set the value of the detalle_sc.articulo_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScArticuloId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_SC_ARTICULO_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.solicitud_compra_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScSolicitudCompraId() throws DataStoreException {
          return  getInt(DETALLE_SC_SOLICITUD_COMPRA_ID);
     }

     /**
      * Retrieve the value of the detalle_sc.solicitud_compra_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScSolicitudCompraId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_SC_SOLICITUD_COMPRA_ID);
     }

     /**
      * Set the value of the detalle_sc.solicitud_compra_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScSolicitudCompraId(int newValue) throws DataStoreException {
          setInt(DETALLE_SC_SOLICITUD_COMPRA_ID, newValue);
     }

     /**
      * Set the value of the detalle_sc.solicitud_compra_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScSolicitudCompraId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_SC_SOLICITUD_COMPRA_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.cantidad_solicitada column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleScCantidadSolicitada() throws DataStoreException {
          return  getDouble(DETALLE_SC_CANTIDAD_SOLICITADA);
     }

     /**
      * Retrieve the value of the detalle_sc.cantidad_solicitada column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getDetalleScCantidadSolicitada(int row) throws DataStoreException {
          return  getDouble(row,DETALLE_SC_CANTIDAD_SOLICITADA);
     }

     /**
      * Set the value of the detalle_sc.cantidad_solicitada column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScCantidadSolicitada(double newValue) throws DataStoreException {
          setDouble(DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
     }

     /**
      * Set the value of the detalle_sc.cantidad_solicitada column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScCantidadSolicitada(int row,double newValue) throws DataStoreException {
          setDouble(row,DETALLE_SC_CANTIDAD_SOLICITADA, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleScDescripcion() throws DataStoreException {
          return  getString(DETALLE_SC_DESCRIPCION);
     }

     /**
      * Retrieve the value of the detalle_sc.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleScDescripcion(int row) throws DataStoreException {
          return  getString(row,DETALLE_SC_DESCRIPCION);
     }

     /**
      * Set the value of the detalle_sc.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScDescripcion(String newValue) throws DataStoreException {
          setString(DETALLE_SC_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the detalle_sc.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_SC_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.tarea_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScTareaId() throws DataStoreException {
          return  getInt(DETALLE_SC_TAREA_ID);
     }

     /**
      * Retrieve the value of the detalle_sc.tarea_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScTareaId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_SC_TAREA_ID);
     }

     /**
      * Set the value of the detalle_sc.tarea_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScTareaId(int newValue) throws DataStoreException {
          setInt(DETALLE_SC_TAREA_ID, newValue);
     }

     /**
      * Set the value of the detalle_sc.tarea_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScTareaId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_SC_TAREA_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.monto_ultima_compra column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleScMontoUltimaCompra() throws DataStoreException {
          return  getString(DETALLE_SC_MONTO_ULTIMA_COMPRA);
     }

     /**
      * Retrieve the value of the detalle_sc.monto_ultima_compra column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getDetalleScMontoUltimaCompra(int row) throws DataStoreException {
          return  getString(row,DETALLE_SC_MONTO_ULTIMA_COMPRA);
     }

     /**
      * Set the value of the detalle_sc.monto_ultima_compra column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScMontoUltimaCompra(String newValue) throws DataStoreException {
          setString(DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
     }

     /**
      * Set the value of the detalle_sc.monto_ultima_compra column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScMontoUltimaCompra(int row,String newValue) throws DataStoreException {
          setString(row,DETALLE_SC_MONTO_ULTIMA_COMPRA, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.fecha_ultima_compra column for the current row.
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getDetalleScFechaUltimaCompra() throws DataStoreException {
          return  getDate(DETALLE_SC_FECHA_ULTIMA_COMPRA);
     }

     /**
      * Retrieve the value of the detalle_sc.fecha_ultima_compra column for the specified row.
      * @param row which row in the table
      * @return java.sql.Date
      * @throws DataStoreException
      */ 
     public java.sql.Date getDetalleScFechaUltimaCompra(int row) throws DataStoreException {
          return  getDate(row,DETALLE_SC_FECHA_ULTIMA_COMPRA);
     }

     /**
      * Set the value of the detalle_sc.fecha_ultima_compra column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScFechaUltimaCompra(java.sql.Date newValue) throws DataStoreException {
          setDate(DETALLE_SC_FECHA_ULTIMA_COMPRA, newValue);
     }

     /**
      * Set the value of the detalle_sc.fecha_ultima_compra column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScFechaUltimaCompra(int row,java.sql.Date newValue) throws DataStoreException {
          setDate(row,DETALLE_SC_FECHA_ULTIMA_COMPRA, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.unidad_medida_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScUnidadMedidaId() throws DataStoreException {
          return  getInt(DETALLE_SC_UNIDAD_MEDIDA_ID);
     }

     /**
      * Retrieve the value of the detalle_sc.unidad_medida_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScUnidadMedidaId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_SC_UNIDAD_MEDIDA_ID);
     }

     /**
      * Set the value of the detalle_sc.unidad_medida_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScUnidadMedidaId(int newValue) throws DataStoreException {
          setInt(DETALLE_SC_UNIDAD_MEDIDA_ID, newValue);
     }

     /**
      * Set the value of the detalle_sc.unidad_medida_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScUnidadMedidaId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_SC_UNIDAD_MEDIDA_ID, newValue);
     }

     /**
      * Retrieve the value of the detalle_sc.Orden_Compra_id column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScOrdenCompraId() throws DataStoreException {
          return  getInt(DETALLE_SC_ORDEN_COMPRA_ID);
     }

     /**
      * Retrieve the value of the detalle_sc.Orden_Compra_id column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getDetalleScOrdenCompraId(int row) throws DataStoreException {
          return  getInt(row,DETALLE_SC_ORDEN_COMPRA_ID);
     }

     /**
      * Set the value of the detalle_sc.Orden_Compra_id column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScOrdenCompraId(int newValue) throws DataStoreException {
          setInt(DETALLE_SC_ORDEN_COMPRA_ID, newValue);
     }

     /**
      * Set the value of the detalle_sc.Orden_Compra_id column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setDetalleScOrdenCompraId(int row,int newValue) throws DataStoreException {
          setInt(row,DETALLE_SC_ORDEN_COMPRA_ID, newValue);
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

     /**
      * Retrieve the value of the articulos.descripcion column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosDescripcion() throws DataStoreException {
          return  getString(ARTICULOS_DESCRIPCION);
     }

     /**
      * Retrieve the value of the articulos.descripcion column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosDescripcion(int row) throws DataStoreException {
          return  getString(row,ARTICULOS_DESCRIPCION);
     }

     /**
      * Set the value of the articulos.descripcion column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosDescripcion(String newValue) throws DataStoreException {
          setString(ARTICULOS_DESCRIPCION, newValue);
     }

     /**
      * Set the value of the articulos.descripcion column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosDescripcion(int row,String newValue) throws DataStoreException {
          setString(row,ARTICULOS_DESCRIPCION, newValue);
     }

     /**
      * Retrieve the value of the articulos.descripcion_completa column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosDescripcionCompleta() throws DataStoreException {
          return  getString(ARTICULOS_DESCRIPCION_COMPLETA);
     }

     /**
      * Retrieve the value of the articulos.descripcion_completa column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getArticulosDescripcionCompleta(int row) throws DataStoreException {
          return  getString(row,ARTICULOS_DESCRIPCION_COMPLETA);
     }

     /**
      * Set the value of the articulos.descripcion_completa column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosDescripcionCompleta(String newValue) throws DataStoreException {
          setString(ARTICULOS_DESCRIPCION_COMPLETA, newValue);
     }

     /**
      * Set the value of the articulos.descripcion_completa column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setArticulosDescripcionCompleta(int row,String newValue) throws DataStoreException {
          setString(row,ARTICULOS_DESCRIPCION_COMPLETA, newValue);
     }

	@Override
	public String getEstadoActual() throws DataStoreException {
		return null;
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		return getDetalleScSolicitudCompraId();
	}
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
	
	/**
	 * @author demian
	 * Permite verificar la coherencia de los datos entre los proveedores del artículo
	 * básicamente creado para verificar el check de compra
	 * @param row de la fila del datasotore a verificar
	 * @throws DataStoreException cuando NO verificó la línea
	 */
	public int verificaIntegridadDetalle(int row) throws DataStoreException {
		gotoRow(row);
		return verificaIntegridadDetalle();
	}
	
	/**
	 * @author demian
	 * Permite verificar la coherencia de los datos entre los proveedores del artículo
	 * básicamente creado para verificar el check de compra
	 * @throws DataStoreException cuando NO verificó la línea
	 */
	public int verificaIntegridadDetalle() throws DataStoreException {
		int check1 = 0, check2 = 0, check3 = 0, check4 = 0, check5 = 0;
		
		check1 = getDetalleCotizacionCotizacionSeleccionadaProveedor1();
		check2 = getDetalleCotizacionCotizacionSeleccionadaProveedor2();
		check3 = getDetalleCotizacionCotizacionSeleccionadaProveedor3();
		check4 = getDetalleCotizacionCotizacionSeleccionadaProveedor4();
		check5 = getDetalleCotizacionCotizacionSeleccionadaProveedor5();
		
		if ((check1 + check2 + check3 + check4 + check5) > 1)
			throw new DataStoreException("El artículo " + getArticulosNombre()
					+ " - " + getArticulosDescripcion()
					+ " Tiene seleccionado mas de un proveedor.");

		// verifica que para el proveedor seleccionado tenga precio
		if (check1 > 0	&& !(getDetalleCotizacionMontoUnitarioProveedor1() > 0.000))
			throw new DataStoreException(
					"El proveedor seleccionado para el artículo "
							+ getArticulosNombre() + " - "
							+ getArticulosDescripcion()
							+ " No tiene precio asignado");
		if (check2 > 0	&& !(getDetalleCotizacionMontoUnitarioProveedor2() > 0.000))
			throw new DataStoreException(
					"El proveedor seleccionado para el artículo "
							+ getArticulosNombre() + " - "
							+ getArticulosDescripcion()
							+ " No tiene precio asignado");
		if (check3 > 0	&& !(getDetalleCotizacionMontoUnitarioProveedor3() > 0.000))
			throw new DataStoreException(
					"El proveedor seleccionado para el artículo "
							+ getArticulosNombre() + " - "
							+ getArticulosDescripcion()
							+ " No tiene precio asignado");
		if (check4 > 0	&& !(getDetalleCotizacionMontoUnitarioProveedor4() > 0.000))
			throw new DataStoreException(
					"El proveedor seleccionado para el artículo "
							+ getArticulosNombre() + " - "
							+ getArticulosDescripcion()
							+ " No tiene precio asignado");
		if (check5 > 0	&& !(getDetalleCotizacionMontoUnitarioProveedor5() > 0.000))
			throw new DataStoreException(
					"El proveedor seleccionado para el artículo "
							+ getArticulosNombre() + " - "
							+ getArticulosDescripcion()
							+ " No tiene precio asignado");

		//determina que proveedor está seleccionado
		if (check1 > 0)
			return 1;
		if (check2 > 0)
			return 2;
		if (check3 > 0)
			return 3;
		if (check4 > 0)
			return 4;
		if (check5 > 0)
			return 5;
		
		return 0;
		
	}
     
     //$ENDCUSTOMMETHODS$
     
}
