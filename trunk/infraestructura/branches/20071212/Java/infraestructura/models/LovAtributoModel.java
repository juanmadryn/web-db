package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ListaValoresAtributoModel: A SOFIA generated model
 */
public class LovAtributoModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4595340861068574570L;

	// constants for columns
	public static final String LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID = "lov_atributo.clase_lov_atributo_id";

	public static final String LISTA_VALORES_ATRIBUTO_VALOR = "lov_atributo.valor";

	public static final String LISTA_VALORES_ATRIBUTO_DESCRIPCION = "lov_atributo.descripcion";

	public static final String CLASE_LOV_ATRIBUTO_NOMBRE = "clase_lov_atributo.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ListaValoresAtributoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public LovAtributoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ListaValoresAtributoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public LovAtributoModel(String appName, String profile) {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("lov_atributo"), "lov");
		addTableAlias(computeTableName("clase_lov_atributo"), "clase_lov");

		// add columns
		addColumn(computeTableName("lov_atributo"), "clase_lov_atributo_id",DataStore.DATATYPE_INT, true, true,LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
		addColumn(computeTableName("lov_atributo"), "valor",DataStore.DATATYPE_STRING, true, true,LISTA_VALORES_ATRIBUTO_VALOR);
		addColumn(computeTableName("lov_atributo"), "descripcion",DataStore.DATATYPE_STRING, false, true,LISTA_VALORES_ATRIBUTO_DESCRIPCION);
		addColumn(computeTableName("clase_lov_atributo"), "nombre",DataStore.DATATYPE_STRING, false, false,CLASE_LOV_ATRIBUTO_NOMBRE);

		// add joins
		addJoin(computeTableAndFieldName("lov_atributo.clase_lov_atributo_id"),computeTableAndFieldName("clase_lov_atributo.clase_lov_atributo_id"),false);

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it
		// be overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the lov_atributo.clase_lov_atributo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getListaValoresAtributoClaseLovAtributoId()
			throws DataStoreException {
		return getInt(LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
	}

	/**
	 * Retrieve the value of the lov_atributo.clase_lov_atributo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getListaValoresAtributoClaseLovAtributoId(int row)
			throws DataStoreException {
		return getInt(row, LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID);
	}

	/**
	 * Set the value of the lov_atributo.clase_lov_atributo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoClaseLovAtributoId(int newValue)
			throws DataStoreException {
		setInt(LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, newValue);
	}

	/**
	 * Set the value of the lov_atributo.clase_lov_atributo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoClaseLovAtributoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, LISTA_VALORES_ATRIBUTO_CLASE_LOV_ATRIBUTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the lov_atributo.valor column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getListaValoresAtributoValor() throws DataStoreException {
		return getString(LISTA_VALORES_ATRIBUTO_VALOR);
	}

	/**
	 * Retrieve the value of the lov_atributo.valor column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getListaValoresAtributoValor(int row)
			throws DataStoreException {
		return getString(row, LISTA_VALORES_ATRIBUTO_VALOR);
	}

	/**
	 * Set the value of the lov_atributo.valor column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoValor(String newValue)
			throws DataStoreException {
		setString(LISTA_VALORES_ATRIBUTO_VALOR, newValue);
	}

	/**
	 * Set the value of the lov_atributo.valor column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoValor(int row, String newValue)
			throws DataStoreException {
		setString(row, LISTA_VALORES_ATRIBUTO_VALOR, newValue);
	}

	/**
	 * Retrieve the value of the lov_atributo.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getListaValoresAtributoDescripcion()
			throws DataStoreException {
		return getString(LISTA_VALORES_ATRIBUTO_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the lov_atributo.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getListaValoresAtributoDescripcion(int row)
			throws DataStoreException {
		return getString(row, LISTA_VALORES_ATRIBUTO_DESCRIPCION);
	}

	/**
	 * Set the value of the lov_atributo.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoDescripcion(String newValue)
			throws DataStoreException {
		setString(LISTA_VALORES_ATRIBUTO_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the lov_atributo.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setListaValoresAtributoDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, LISTA_VALORES_ATRIBUTO_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the clase_lov_atributo.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseLovAtributoNombre() throws DataStoreException {
		return getString(CLASE_LOV_ATRIBUTO_NOMBRE);
	}

	/**
	 * Retrieve the value of the clase_lov_atributo.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseLovAtributoNombre(int row) throws DataStoreException {
		return getString(row, CLASE_LOV_ATRIBUTO_NOMBRE);
	}

	/**
	 * Set the value of the clase_lov_atributo.nombre column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseLovAtributoNombre(String newValue)
			throws DataStoreException {
		setString(CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the clase_lov_atributo.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseLovAtributoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
