package partesMO.models;

import infraestructura.models.BaseModel;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * LoteCargaPartesMoModel: A SOFIA generated model
 */
public class LoteCargaPartesMoModel extends BaseModel {

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
     
	@Override
	public String getEstadoActual() throws DataStoreException {
		// TODO Auto-generated method stub
		return getLoteCargaPartesMoEstado();
	}

	@Override
	public int getIdRegistro() throws DataStoreException {
		// TODO Auto-generated method stub
		return getLoteCargaPartesMoLoteId();
	}
     
     //$ENDCUSTOMMETHODS$
     
}
