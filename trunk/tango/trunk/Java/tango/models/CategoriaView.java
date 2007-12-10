package tango.models;

import com.salmonllc.sql.*;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CategoriaView: A SOFIA generated model
 */
public class CategoriaView extends DataStore {

     /**
	 * 
	 */
	private static final long serialVersionUID = -3045300279238524339L;
	//constants for columns
     public static final String CATEGORIA_COD_CATEGORIA="CATEGORIA.COD_CATEGORIA";
     public static final String CATEGORIA_DESC_CATEGORIA="CATEGORIA.DESC_CATEGORIA";
     public static final String CATEGORIA_DIAS_POR_MES="CATEGORIA.DIAS_POR_MES";
     public static final String CATEGORIA_DIAS_POR_SEMANA="CATEGORIA.DIAS_POR_SEMANA";
     public static final String CATEGORIA_HORAS_POR_DIA="CATEGORIA.HORAS_POR_DIA";
     public static final String CATEGORIA_HORAS_POR_SEMANA="CATEGORIA.HORAS_POR_SEMANA";
     public static final String CATEGORIA_HORAS_POR_MES="CATEGORIA.HORAS_POR_MES";
     public static final String CATEGORIA_CA_14_ADICIO1="CATEGORIA.CA_14_ADICIO1";
     public static final String CATEGORIA_CA_14_ADICIO2="CATEGORIA.CA_14_ADICIO2";
     public static final String CATEGORIA_CA_14_HS_MINIMO="CATEGORIA.CA_14_HS_MINIMO";
     public static final String CATEGORIA_CA_14_DIAS_MINIMO="CATEGORIA.CA_14_DIAS_MINIMO";
     public static final String CATEGORIA_CA_14_APLICA_3AM="CATEGORIA.CA_14_APLICA_3AM";

     //$CUSTOMVARS$
     //Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMVARS$
     
     /**
      * Create a new CategoriaView object.
      * @param appName The SOFIA application name
      */
     public CategoriaView (String appName) { 
          this(appName, null);
     }

     /**
      * Create a new CategoriaView object.
      * @param appName The SOFIA application name
      * @param profile The database profile to use
      */
     public CategoriaView (String appName, String profile) { 
          super(appName, profile);

          //add aliases
          addTableAlias(computeTableName("CATEGORIA"),"CATEGORIA");

          //add columns
          addColumn(computeTableName("CATEGORIA"),"COD_CATEGORIA",DataStore.DATATYPE_STRING,false,false,CATEGORIA_COD_CATEGORIA);
          addColumn(computeTableName("CATEGORIA"),"DESC_CATEGORIA",DataStore.DATATYPE_STRING,false,false,CATEGORIA_DESC_CATEGORIA);
          addColumn(computeTableName("CATEGORIA"),"DIAS_POR_MES",DataStore.DATATYPE_INT,false,false,CATEGORIA_DIAS_POR_MES);
          addColumn(computeTableName("CATEGORIA"),"DIAS_POR_SEMANA",DataStore.DATATYPE_INT,false,false,CATEGORIA_DIAS_POR_SEMANA);
          addColumn(computeTableName("CATEGORIA"),"HORAS_POR_DIA",DataStore.DATATYPE_INT,false,false,CATEGORIA_HORAS_POR_DIA);
          addColumn(computeTableName("CATEGORIA"),"HORAS_POR_SEMANA",DataStore.DATATYPE_INT,false,false,CATEGORIA_HORAS_POR_SEMANA);
          addColumn(computeTableName("CATEGORIA"),"HORAS_POR_MES",DataStore.DATATYPE_INT,false,false,CATEGORIA_HORAS_POR_MES);
          addColumn(computeTableName("CATEGORIA"),"CA_14_ADICIO1",DataStore.DATATYPE_DOUBLE,false,false,CATEGORIA_CA_14_ADICIO1);
          addColumn(computeTableName("CATEGORIA"),"CA_14_ADICIO2",DataStore.DATATYPE_DOUBLE,false,false,CATEGORIA_CA_14_ADICIO2);
          addColumn(computeTableName("CATEGORIA"),"CA_14_HS_MINIMO",DataStore.DATATYPE_DOUBLE,false,false,CATEGORIA_CA_14_HS_MINIMO);
          addColumn(computeTableName("CATEGORIA"),"CA_14_DIAS_MINIMO",DataStore.DATATYPE_DOUBLE,false,false,CATEGORIA_CA_14_DIAS_MINIMO);
          addColumn(computeTableName("CATEGORIA"),"CA_14_APLICA_3AM",DataStore.DATATYPE_STRING,false,false,CATEGORIA_CA_14_APLICA_3AM);

          //$CUSTOMCONSTRUCTOR$
          //Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated

          //$ENDCUSTOMCONSTRUCTOR$

     }

     /**
      * Retrieve the value of the CATEGORIA.COD_CATEGORIA column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaCodCategoria() throws DataStoreException {
          return  getString(CATEGORIA_COD_CATEGORIA);
     }

     /**
      * Retrieve the value of the CATEGORIA.COD_CATEGORIA column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaCodCategoria(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_COD_CATEGORIA);
     }

     /**
      * Set the value of the CATEGORIA.COD_CATEGORIA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCodCategoria(String newValue) throws DataStoreException {
          setString(CATEGORIA_COD_CATEGORIA, newValue);
     }

     /**
      * Set the value of the CATEGORIA.COD_CATEGORIA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCodCategoria(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_COD_CATEGORIA, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.DESC_CATEGORIA column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaDescCategoria() throws DataStoreException {
          return  getString(CATEGORIA_DESC_CATEGORIA);
     }

     /**
      * Retrieve the value of the CATEGORIA.DESC_CATEGORIA column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaDescCategoria(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_DESC_CATEGORIA);
     }

     /**
      * Set the value of the CATEGORIA.DESC_CATEGORIA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDescCategoria(String newValue) throws DataStoreException {
          setString(CATEGORIA_DESC_CATEGORIA, newValue);
     }

     /**
      * Set the value of the CATEGORIA.DESC_CATEGORIA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDescCategoria(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_DESC_CATEGORIA, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.DIAS_POR_MES column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaDiasPorMes() throws DataStoreException {
          return  getInt(CATEGORIA_DIAS_POR_MES);
     }

     /**
      * Retrieve the value of the CATEGORIA.DIAS_POR_MES column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaDiasPorMes(int row) throws DataStoreException {
          return  getInt(row,CATEGORIA_DIAS_POR_MES);
     }

     /**
      * Set the value of the CATEGORIA.DIAS_POR_MES column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDiasPorMes(int newValue) throws DataStoreException {
          setInt(CATEGORIA_DIAS_POR_MES, newValue);
     }

     /**
      * Set the value of the CATEGORIA.DIAS_POR_MES column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDiasPorMes(int row,int newValue) throws DataStoreException {
          setInt(row,CATEGORIA_DIAS_POR_MES, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.DIAS_POR_SEMANA column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaDiasPorSemana() throws DataStoreException {
          return  getInt(CATEGORIA_DIAS_POR_SEMANA);
     }

     /**
      * Retrieve the value of the CATEGORIA.DIAS_POR_SEMANA column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaDiasPorSemana(int row) throws DataStoreException {
          return  getInt(row,CATEGORIA_DIAS_POR_SEMANA);
     }

     /**
      * Set the value of the CATEGORIA.DIAS_POR_SEMANA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDiasPorSemana(int newValue) throws DataStoreException {
          setInt(CATEGORIA_DIAS_POR_SEMANA, newValue);
     }

     /**
      * Set the value of the CATEGORIA.DIAS_POR_SEMANA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaDiasPorSemana(int row,int newValue) throws DataStoreException {
          setInt(row,CATEGORIA_DIAS_POR_SEMANA, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_DIA column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorDia() throws DataStoreException {
          return  getInt(CATEGORIA_HORAS_POR_DIA);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_DIA column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorDia(int row) throws DataStoreException {
          return  getInt(row,CATEGORIA_HORAS_POR_DIA);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_DIA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorDia(int newValue) throws DataStoreException {
          setInt(CATEGORIA_HORAS_POR_DIA, newValue);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_DIA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorDia(int row,int newValue) throws DataStoreException {
          setInt(row,CATEGORIA_HORAS_POR_DIA, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_SEMANA column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorSemana() throws DataStoreException {
          return  getInt(CATEGORIA_HORAS_POR_SEMANA);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_SEMANA column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorSemana(int row) throws DataStoreException {
          return  getInt(row,CATEGORIA_HORAS_POR_SEMANA);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_SEMANA column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorSemana(int newValue) throws DataStoreException {
          setInt(CATEGORIA_HORAS_POR_SEMANA, newValue);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_SEMANA column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorSemana(int row,int newValue) throws DataStoreException {
          setInt(row,CATEGORIA_HORAS_POR_SEMANA, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_MES column for the current row.
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorMes() throws DataStoreException {
          return  getInt(CATEGORIA_HORAS_POR_MES);
     }

     /**
      * Retrieve the value of the CATEGORIA.HORAS_POR_MES column for the specified row.
      * @param row which row in the table
      * @return int
      * @throws DataStoreException
      */ 
     public int getCategoriaHorasPorMes(int row) throws DataStoreException {
          return  getInt(row,CATEGORIA_HORAS_POR_MES);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_MES column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorMes(int newValue) throws DataStoreException {
          setInt(CATEGORIA_HORAS_POR_MES, newValue);
     }

     /**
      * Set the value of the CATEGORIA.HORAS_POR_MES column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaHorasPorMes(int row,int newValue) throws DataStoreException {
          setInt(row,CATEGORIA_HORAS_POR_MES, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_ADICIO1 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14Adicio1() throws DataStoreException {
          return  getDouble(CATEGORIA_CA_14_ADICIO1);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_ADICIO1 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14Adicio1(int row) throws DataStoreException {
          return  getDouble(row,CATEGORIA_CA_14_ADICIO1);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_ADICIO1 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Adicio1(double newValue) throws DataStoreException {
          setDouble(CATEGORIA_CA_14_ADICIO1, newValue);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_ADICIO1 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Adicio1(int row,double newValue) throws DataStoreException {
          setDouble(row,CATEGORIA_CA_14_ADICIO1, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_ADICIO2 column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14Adicio2() throws DataStoreException {
          return  getDouble(CATEGORIA_CA_14_ADICIO2);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_ADICIO2 column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14Adicio2(int row) throws DataStoreException {
          return  getDouble(row,CATEGORIA_CA_14_ADICIO2);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_ADICIO2 column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Adicio2(double newValue) throws DataStoreException {
          setDouble(CATEGORIA_CA_14_ADICIO2, newValue);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_ADICIO2 column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Adicio2(int row,double newValue) throws DataStoreException {
          setDouble(row,CATEGORIA_CA_14_ADICIO2, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_HS_MINIMO column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14HsMinimo() throws DataStoreException {
          return  getDouble(CATEGORIA_CA_14_HS_MINIMO);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_HS_MINIMO column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14HsMinimo(int row) throws DataStoreException {
          return  getDouble(row,CATEGORIA_CA_14_HS_MINIMO);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_HS_MINIMO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14HsMinimo(double newValue) throws DataStoreException {
          setDouble(CATEGORIA_CA_14_HS_MINIMO, newValue);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_HS_MINIMO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14HsMinimo(int row,double newValue) throws DataStoreException {
          setDouble(row,CATEGORIA_CA_14_HS_MINIMO, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_DIAS_MINIMO column for the current row.
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14DiasMinimo() throws DataStoreException {
          return  getDouble(CATEGORIA_CA_14_DIAS_MINIMO);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_DIAS_MINIMO column for the specified row.
      * @param row which row in the table
      * @return double
      * @throws DataStoreException
      */ 
     public double getCategoriaCa14DiasMinimo(int row) throws DataStoreException {
          return  getDouble(row,CATEGORIA_CA_14_DIAS_MINIMO);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_DIAS_MINIMO column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14DiasMinimo(double newValue) throws DataStoreException {
          setDouble(CATEGORIA_CA_14_DIAS_MINIMO, newValue);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_DIAS_MINIMO column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14DiasMinimo(int row,double newValue) throws DataStoreException {
          setDouble(row,CATEGORIA_CA_14_DIAS_MINIMO, newValue);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_APLICA_3AM column for the current row.
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaCa14Aplica3am() throws DataStoreException {
          return  getString(CATEGORIA_CA_14_APLICA_3AM);
     }

     /**
      * Retrieve the value of the CATEGORIA.CA_14_APLICA_3AM column for the specified row.
      * @param row which row in the table
      * @return String
      * @throws DataStoreException
      */ 
     public String getCategoriaCa14Aplica3am(int row) throws DataStoreException {
          return  getString(row,CATEGORIA_CA_14_APLICA_3AM);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_APLICA_3AM column for the current row.
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Aplica3am(String newValue) throws DataStoreException {
          setString(CATEGORIA_CA_14_APLICA_3AM, newValue);
     }

     /**
      * Set the value of the CATEGORIA.CA_14_APLICA_3AM column for the specified row.
      * @param row which row in the table
      * @param newValue the new item value
      * @throws DataStoreException
      */ 
     public void setCategoriaCa14Aplica3am(int row,String newValue) throws DataStoreException {
          setString(row,CATEGORIA_CA_14_APLICA_3AM, newValue);
     }
     
     //$CUSTOMMETHODS$
     //Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated
     
     //$ENDCUSTOMMETHODS$
     
}
