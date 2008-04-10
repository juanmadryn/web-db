package inventario.models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * InstanciasAprobacionModel: A SOFIA generated model
 */
public class InstanciasAprobacionModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5322752820152343671L;
	// constants for columns
	public static final String INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID = "instancias_aprobacion.instancia_aprobacion_id";
	public static final String INSTANCIAS_APROBACION_USER_FIRMANTE = "instancias_aprobacion.user_firmante";
	public static final String INSTANCIAS_APROBACION_ESTADO = "instancias_aprobacion.estado";
	public static final String INSTANCIAS_APROBACION_FECHA_ENTRADA = "instancias_aprobacion.fecha_entrada";
	public static final String INSTANCIAS_APROBACION_FECHA_ACCION = "instancias_aprobacion.fecha_accion";
	public static final String INSTANCIAS_APROBACION_MENSAJE = "instancias_aprobacion.mensaje";
	public static final String INSTANCIAS_APROBACION_ORDEN = "instancias_aprobacion.orden";
	public static final String INSTANCIAS_APROBACION_OBJETO_ID = "instancias_aprobacion.objeto_id";
	public static final String INSTANCIAS_APROBACION_NOMBRE_OBJETO = "instancias_aprobacion.nombre_objeto";
	
	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new InstanciasAprobacionModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public InstanciasAprobacionModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new InstanciasAprobacionModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public InstanciasAprobacionModel(String appName, String profile) {
		super(appName, profile);

		// add aliases
		addTableAlias(computeTableName("instancias_aprobacion"),
				"instancias_aprobacion");

		// add columns
		addColumn(computeTableName("instancias_aprobacion"),
				"instancia_aprobacion_id", DataStore.DATATYPE_INT, true, true,
				INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID);
		addColumn(computeTableName("instancias_aprobacion"), "user_firmante",
				DataStore.DATATYPE_INT, false, true,
				INSTANCIAS_APROBACION_USER_FIRMANTE);
		addColumn(computeTableName("instancias_aprobacion"), "estado",
				DataStore.DATATYPE_STRING, false, true,
				INSTANCIAS_APROBACION_ESTADO);
		addColumn(computeTableName("instancias_aprobacion"), "fecha_entrada",
				DataStore.DATATYPE_DATE, false, true,
				INSTANCIAS_APROBACION_FECHA_ENTRADA);
		addColumn(computeTableName("instancias_aprobacion"), "fecha_accion",
				DataStore.DATATYPE_DATE, false, true,
				INSTANCIAS_APROBACION_FECHA_ACCION);
		addColumn(computeTableName("instancias_aprobacion"), "mensaje",
				DataStore.DATATYPE_STRING, false, true,
				INSTANCIAS_APROBACION_MENSAJE);
		addColumn(computeTableName("instancias_aprobacion"), "orden",
				DataStore.DATATYPE_INT, false, true,
				INSTANCIAS_APROBACION_ORDEN);
		addColumn(computeTableName("instancias_aprobacion"), "nombre_objeto",
				DataStore.DATATYPE_STRING, false, true,
				INSTANCIAS_APROBACION_NOMBRE_OBJETO);
		addColumn(computeTableName("instancias_aprobacion"), "objeto_id",
				DataStore.DATATYPE_INT, false, true,
				INSTANCIAS_APROBACION_OBJETO_ID);		

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the instancias_aprobacion.instancia_aprobacion_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionInstanciaAprobacionId()
			throws DataStoreException {
		return getInt(INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.instancia_aprobacion_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionInstanciaAprobacionId(int row)
			throws DataStoreException {
		return getInt(row, INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID);
	}

	/**
	 * Set the value of the instancias_aprobacion.instancia_aprobacion_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionInstanciaAprobacionId(int newValue)
			throws DataStoreException {
		setInt(INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.instancia_aprobacion_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionInstanciaAprobacionId(int row,
			int newValue) throws DataStoreException {
		setInt(row, INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID, newValue);
	}
	
	/**
	 * Retrieve the value of the instancias_aprobacion.user_firmante column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionUserFirmante() throws DataStoreException {
		return getInt(INSTANCIAS_APROBACION_USER_FIRMANTE);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.user_firmante column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionUserFirmante(int row)
			throws DataStoreException {
		return getInt(row, INSTANCIAS_APROBACION_USER_FIRMANTE);
	}

	/**
	 * Set the value of the instancias_aprobacion.user_firmante column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionUserFirmante(int newValue)
			throws DataStoreException {
		setInt(INSTANCIAS_APROBACION_USER_FIRMANTE, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.user_firmante column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionUserFirmante(int row, int newValue)
			throws DataStoreException {
		setInt(row, INSTANCIAS_APROBACION_USER_FIRMANTE, newValue);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.estado column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionEstado() throws DataStoreException {
		return getString(INSTANCIAS_APROBACION_ESTADO);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionEstado(int row)
			throws DataStoreException {
		return getString(row, INSTANCIAS_APROBACION_ESTADO);
	}

	/**
	 * Set the value of the instancias_aprobacion.estado column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionEstado(String newValue)
			throws DataStoreException {
		setString(INSTANCIAS_APROBACION_ESTADO, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.estado column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionEstado(int row, String newValue)
			throws DataStoreException {
		setString(row, INSTANCIAS_APROBACION_ESTADO, newValue);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.fecha_entrada column for
	 * the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getInstanciasAprobacionFechaEntrada()
			throws DataStoreException {
		return getDate(INSTANCIAS_APROBACION_FECHA_ENTRADA);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.fecha_entrada column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getInstanciasAprobacionFechaEntrada(int row)
			throws DataStoreException {
		return getDate(row, INSTANCIAS_APROBACION_FECHA_ENTRADA);
	}

	/**
	 * Set the value of the instancias_aprobacion.fecha_entrada column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionFechaEntrada(java.sql.Date newValue)
			throws DataStoreException {
		setDate(INSTANCIAS_APROBACION_FECHA_ENTRADA, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.fecha_entrada column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionFechaEntrada(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, INSTANCIAS_APROBACION_FECHA_ENTRADA, newValue);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.fecha_accion column for
	 * the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getInstanciasAprobacionFechaAccion()
			throws DataStoreException {
		return getDate(INSTANCIAS_APROBACION_FECHA_ACCION);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.fecha_accion column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getInstanciasAprobacionFechaAccion(int row)
			throws DataStoreException {
		return getDate(row, INSTANCIAS_APROBACION_FECHA_ACCION);
	}

	/**
	 * Set the value of the instancias_aprobacion.fecha_accion column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionFechaAccion(java.sql.Date newValue)
			throws DataStoreException {
		setDate(INSTANCIAS_APROBACION_FECHA_ACCION, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.fecha_accion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionFechaAccion(int row,
			java.sql.Date newValue) throws DataStoreException {
		setDate(row, INSTANCIAS_APROBACION_FECHA_ACCION, newValue);
	}


	/**
	 * Retrieve the value of the instancias_aprobacion.mensaje column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionMensaje() throws DataStoreException {
		return getString(INSTANCIAS_APROBACION_MENSAJE);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.mensaje column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionMensaje(int row)
			throws DataStoreException {
		return getString(row, INSTANCIAS_APROBACION_MENSAJE);
	}

	/**
	 * Set the value of the instancias_aprobacion.mensaje column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionMensaje(String newValue)
			throws DataStoreException {
		setString(INSTANCIAS_APROBACION_MENSAJE, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.mensaje column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionMensaje(int row, String newValue)
			throws DataStoreException {
		setString(row, INSTANCIAS_APROBACION_MENSAJE, newValue);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.orden column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionOrden() throws DataStoreException {
		return getInt(INSTANCIAS_APROBACION_ORDEN);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.orden column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionOrden(int row)
			throws DataStoreException {
		return getInt(row, INSTANCIAS_APROBACION_ORDEN);
	}

	/**
	 * Set the value of the instancias_aprobacion.orden column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionOrden(int newValue)
			throws DataStoreException {
		setInt(INSTANCIAS_APROBACION_ORDEN, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.orden column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionOrden(int row, int newValue)
			throws DataStoreException {
		setInt(row, INSTANCIAS_APROBACION_ORDEN, newValue);
	}
	
	/**
	 * Retrieve the value of the instancias_aprobacion.nombre_objeto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionNombreObjeto() throws DataStoreException {
		return getString(INSTANCIAS_APROBACION_NOMBRE_OBJETO);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.nombre_objeto column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getInstanciasAprobacionNombreObjeto(int row)
			throws DataStoreException {
		return getString(row, INSTANCIAS_APROBACION_NOMBRE_OBJETO);
	}

	/**
	 * Set the value of the instancias_aprobacion.nombre_objeto column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionNombreObjeto(String newValue)
			throws DataStoreException {
		setString(INSTANCIAS_APROBACION_NOMBRE_OBJETO, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.nombre_objeto column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionNombreObjeto(int row, String newValue)
			throws DataStoreException {
		setString(row, INSTANCIAS_APROBACION_NOMBRE_OBJETO, newValue);
	}
	
	/**
	 * Retrieve the value of the instancias_aprobacion.objeto_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionObjetoId() throws DataStoreException {
		return getInt(INSTANCIAS_APROBACION_OBJETO_ID);
	}

	/**
	 * Retrieve the value of the instancias_aprobacion.objeto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getInstanciasAprobacionObjetoId(int row) throws DataStoreException {
		return getInt(row, INSTANCIAS_APROBACION_OBJETO_ID);
	}

	/**
	 * Set the value of the instancias_aprobacion.objeto_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setInstanciasAprobacionObjetoId(int newValue)
			throws DataStoreException {
		setInt(INSTANCIAS_APROBACION_OBJETO_ID, newValue);
	}

	/**
	 * Set the value of the instancias_aprobacion.objeto_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosEntidadObjetoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, INSTANCIAS_APROBACION_OBJETO_ID, newValue);
	}
	
	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	public void firmarInstanciasAprobacionSolicitud(int user_firmante, DBConnection conn)
			throws DataStoreException, SQLException {
		for (int row = 0; row < getRowCount(); row++) {		
			setInstanciasAprobacionEstado(row, "0007.0002");
			if (getInstanciasAprobacionUserFirmante(row) == user_firmante)
				setInstanciasAprobacionFechaAccion(row, new Date((Calendar
						.getInstance().getTimeInMillis())));
		}
		if(conn != null)
			update(conn);
		else 
			update();
	}

	
	// $ENDCUSTOMMETHODS$

}
