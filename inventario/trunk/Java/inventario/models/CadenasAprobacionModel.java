package inventario.models;

import java.util.Iterator;
import java.util.Vector;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CadenaFirmasModel: A SOFIA generated model
 */
public class CadenasAprobacionModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1557788025764261352L;
	// constants for columns
	public static final String CADENAS_APROBACION_CADENA_APROBACION_ID = "cadenas_aprobacion.cadena_aprobacion_id";
	public static final String CADENAS_APROBACION_USER_FIRMANTE = "cadenas_aprobacion.user_firmante";
	public static final String CADENAS_APROBACION_ORDEN = "cadenas_aprobacion.orden";
	public static final String CADENAS_APROBACION_CONFIGURACION_ID = "cadenas_aprobacion.configuracion_id";	
	
	// $CUSTOMVARS$/
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String WEBSITE_USER_NOMBRE_COMPLETO = "website_user.nombre_completo";
	
	private int order = 0;

	// $ENDCUSTOMVARS$

	/**
	 * Create a new CadenaFirmasModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public CadenasAprobacionModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new CadenaFirmasModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public CadenasAprobacionModel(String appName, String profile) {
		super(appName, profile);

		try {
			// add aliases
			addTableAlias(computeTableName("cadenas_aprobacion"),
					"cadenas_aprobacion");
			addTableAlias(computeTableName("infraestructura.website_user"),
					"website_user");

			// add columns
			addColumn(computeTableName("cadenas_aprobacion"),
					"cadena_aprobacion_id", DataStore.DATATYPE_INT, true, true,
					CADENAS_APROBACION_CADENA_APROBACION_ID);
			addColumn(computeTableName("cadenas_aprobacion"), "user_firmante",
					DataStore.DATATYPE_INT, false, true,
					CADENAS_APROBACION_USER_FIRMANTE);
			addColumn(computeTableName("cadenas_aprobacion"), "orden",
					DataStore.DATATYPE_INT, false, true,
					CADENAS_APROBACION_ORDEN);
			addColumn(computeTableName("cadenas_aprobacion"),
					"configuracion_id", DataStore.DATATYPE_INT, false, true,
					CADENAS_APROBACION_CONFIGURACION_ID);
			addColumn(computeTableName("website_user"), "nombre_completo",
					DataStore.DATATYPE_STRING, false, false,
					WEBSITE_USER_NOMBRE_COMPLETO);

			// add joins
			addJoin(
					computeTableAndFieldName("cadenas_aprobacion.user_firmante"),
					computeTableAndFieldName("website_user.user_id"), true);

			// add validations
			addRequiredRule(CADENAS_APROBACION_USER_FIRMANTE,
					"El usuario firmante es obligatorio");
			addRequiredRule(CADENAS_APROBACION_CONFIGURACION_ID,
					"La configuración correspondiente es obligatoria");
			addRequiredRule(CADENAS_APROBACION_ORDEN, "El orden es obligatorio");

			addLookupRule(
					WEBSITE_USER_NOMBRE_COMPLETO,
					"infraestructura.website_user",
					"'infraestructura.website_user.user_id = ' + cadenas_aprobacion.user_firmante",
					"nombre_completo", WEBSITE_USER_NOMBRE_COMPLETO,
					"Usuario inexistente");

			// set autoincrement
			setAutoIncrement(CADENAS_APROBACION_CADENA_APROBACION_ID, true);
			setUpdateable(CADENAS_APROBACION_CADENA_APROBACION_ID, false);
			setOrderBy("orden");

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.cadena_aprobacion_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionCadenaAprobacionId()
			throws DataStoreException {
		return getInt(CADENAS_APROBACION_CADENA_APROBACION_ID);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.cadena_aprobacion_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionCadenaAprobacionId(int row)
			throws DataStoreException {
		return getInt(row, CADENAS_APROBACION_CADENA_APROBACION_ID);
	}

	/**
	 * Set the value of the cadenas_aprobacion.cadena_aprobacion_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionCadenaAprobacionId(int newValue)
			throws DataStoreException {
		setInt(CADENAS_APROBACION_CADENA_APROBACION_ID, newValue);
	}

	/**
	 * Set the value of the cadenas_aprobacion.cadena_aprobacion_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionCadenaAprobacionId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CADENAS_APROBACION_CADENA_APROBACION_ID, newValue);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.user_firmante column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionUserFirmante() throws DataStoreException {
		return getInt(CADENAS_APROBACION_USER_FIRMANTE);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.user_firmante column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionUserFirmante(int row)
			throws DataStoreException {
		return getInt(row, CADENAS_APROBACION_USER_FIRMANTE);
	}

	/**
	 * Set the value of the cadenas_aprobacion.user_firmante column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionUserFirmante(int newValue)
			throws DataStoreException {
		setInt(CADENAS_APROBACION_USER_FIRMANTE, newValue);
	}

	/**
	 * Set the value of the cadenas_aprobacion.user_firmante column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionUserFirmante(int row, int newValue)
			throws DataStoreException {
		setInt(row, CADENAS_APROBACION_USER_FIRMANTE, newValue);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.orden column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionOrden() throws DataStoreException {
		return getInt(CADENAS_APROBACION_ORDEN);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.orden column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionOrden(int row) throws DataStoreException {
		return getInt(row, CADENAS_APROBACION_ORDEN);
	}

	/**
	 * Set the value of the cadenas_aprobacion.orden column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionOrden(int newValue)
			throws DataStoreException {
		setInt(CADENAS_APROBACION_ORDEN, newValue);
	}

	/**
	 * Set the value of the cadenas_aprobacion.orden column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionOrden(int row, int newValue)
			throws DataStoreException {
		setInt(row, CADENAS_APROBACION_ORDEN, newValue);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.configuracion_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionConfiguracionId() throws DataStoreException {
		return getInt(CADENAS_APROBACION_CONFIGURACION_ID);
	}

	/**
	 * Retrieve the value of the cadenas_aprobacion.configuracion_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getCadenasAprobacionConfiguracionId(int row)
			throws DataStoreException {
		return getInt(row, CADENAS_APROBACION_CONFIGURACION_ID);
	}

	/**
	 * Set the value of the cadenas_aprobacion.configuracion_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionConfiguracionId(int newValue)
			throws DataStoreException {
		setInt(CADENAS_APROBACION_CONFIGURACION_ID, newValue);
	}

	/**
	 * Set the value of the cadenas_aprobacion.configuracion_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCadenasAprobacionConfiguracionId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CADENAS_APROBACION_CONFIGURACION_ID, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombre() throws DataStoreException {
		return getString(WEBSITE_USER_NOMBRE_COMPLETO);
	}

	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombre(int row) throws DataStoreException {
		return getString(row, WEBSITE_USER_NOMBRE_COMPLETO);
	}

	/**
	 * Set the value of the website_user.nombre_completo column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombre(String newValue) throws DataStoreException {
		setString(WEBSITE_USER_NOMBRE_COMPLETO, newValue);
	}

	/**
	 * Set the value of the website_user.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NOMBRE_COMPLETO, newValue);
	}

	public void recuperaCadena(int configuracion_id) throws DataStoreException {
		filter("configuracion_id ==" + configuracion_id);
	}

	public Iterator<Integer> getSiguientesFirmantes(boolean checkForMore, int orden) throws DataStoreException {
		if (checkForMore) {
			gotoFirst();
			while(getCadenasAprobacionOrden() != orden && gotoNext())
				System.out.println("distinto: "+getRow());
				;
			while(getCadenasAprobacionOrden() == orden)
				if (!gotoNext())
					return null;	
				;			
		}
		
		Vector<Integer> vectorFirmantes = new Vector<Integer>();
		vectorFirmantes.add(getCadenasAprobacionUserFirmante());
		orden = getCadenasAprobacionOrden();
		System.out.println("orden: "+orden);
		while (gotoNext() && orden == getCadenasAprobacionOrden()) {
			vectorFirmantes.add(getCadenasAprobacionUserFirmante());	
		}		
		System.out.println("orden1: "+orden);
		setOrder(orden);
		return vectorFirmantes.iterator();

	}

	public Iterator<Integer> getFirmantesPorOrden(int orden,
			int configuracion_id) throws DataStoreException {
		filter("configuracion_id == " + configuracion_id
				+ "&& cadenas_aprobacion.orden == " + orden);

		Vector<Integer> vectorFirmantes = new Vector<Integer>();

		for (int row = 0; row < getRowCount(); row++) {
			vectorFirmantes.add(getCadenasAprobacionUserFirmante(row));
		}

		return vectorFirmantes.iterator();

	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int previousOrder) {
		this.order = previousOrder;
	}

	// $ENDCUSTOMMETHODS$

}
