package infraestructura.models;

import infraestructura.utils.AtributoConfiguracion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AtributosCofiguracionModel: A SOFIA generated model
 */
public class AtributosConfiguracionModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1871094045587687699L;
	// constants for columns
	public static final String ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID = "atributos_configuracion.configuracion_id";
	public static final String ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID = "atributos_configuracion.atributo_id";
	public static final String ATRIBUTOS_CONFIGURACION_VALOR = "atributos_configuracion.valor";
	public static final String ATRIBUTOS_CONFIGURACION_VALOR_HASTA = "atributos_configuracion.valor_hasta";
	public static final String ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";
	public static final String CONFIGURACION_NOMBRE = "configuracion.nombre";
	public static final String ESQUEMA_CONFIGURACION_NOMBRE = "esquema_configuracion.nombre";
	public static final String ESQUEMA_CONFIGURACION_TIPO_OBJETO = "esquema_configuracion.tipo_objeto";
	public static final String ESQUEMA_CONFIGURACION_NOMBRE_OBJETO = "esquema_configuracion.nombre_objeto";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new AtributosCofiguracionModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public AtributosConfiguracionModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new AtributosCofiguracionModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public AtributosConfiguracionModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("atributos_rol"), null);
			addTableAlias(computeTableName("atributos_configuracion"), null);
			addTableAlias(computeTableName("esquema_configuracion"), null);
			addTableAlias(computeTableName("configuracion"), null);

			// add columns
			addColumn(computeTableName("atributos_configuracion"),
					"configuracion_id", DataStore.DATATYPE_INT, true, true,
					ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID);
			addColumn(computeTableName("atributos_configuracion"),
					"atributo_id", DataStore.DATATYPE_INT, true, true,
					ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID);
			addColumn(computeTableName("atributos_configuracion"), "valor",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_CONFIGURACION_VALOR);
			addColumn(computeTableName("atributos_configuracion"),
					"valor_hasta", DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_CONFIGURACION_VALOR_HASTA);
			addColumn(computeTableName("atributos_rol"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ATRIBUTOS_ROL_NOMBRE);
			addColumn(computeTableName("configuracion"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CONFIGURACION_NOMBRE);
			addColumn(computeTableName("esquema_configuracion"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ESQUEMA_CONFIGURACION_NOMBRE);
			addColumn(computeTableName("esquema_configuracion"), "tipo_objeto",
					DataStore.DATATYPE_STRING, false, false,
					ESQUEMA_CONFIGURACION_TIPO_OBJETO);
			addColumn(computeTableName("esquema_configuracion"),
					"nombre_objeto", DataStore.DATATYPE_STRING, false, false,
					ESQUEMA_CONFIGURACION_NOMBRE_OBJETO);

			// add joins
			addJoin(
					computeTableAndFieldName("atributos_configuracion.atributo_id"),
					computeTableAndFieldName("atributos_rol.atributo_id"),
					false);
			addJoin(
					computeTableAndFieldName("atributos_configuracion.configuracion_id"),
					computeTableAndFieldName("configuracion.configuracion_id"),
					false);
			addJoin(
					computeTableAndFieldName("configuracion.esquema_configuracion_id"),
					computeTableAndFieldName("esquema_configuracion.esquema_configuracion_id"),
					false);

			// add validations
			addRequiredRule(ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID,
					"El atributo es obligatorio");
			addRequiredRule(ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID,
					"La configuración es obligatoria");
			addRequiredRule(ATRIBUTOS_CONFIGURACION_VALOR,
					"El valor para el atributo es obligatorio");
			addRequiredRule(ATRIBUTOS_CONFIGURACION_VALOR_HASTA,
					"El valor hasta para el atributo es obligatorio");

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the atributos_configuracion.configuracion_id column
	 * for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosConfiguracionConfiguracionId()
			throws DataStoreException {
		return getInt(ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.configuracion_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosConfiguracionConfiguracionId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID);
	}

	/**
	 * Set the value of the atributos_configuracion.configuracion_id column for
	 * the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionConfiguracionId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID, newValue);
	}

	/**
	 * Set the value of the atributos_configuracion.configuracion_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionConfiguracionId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_CONFIGURACION_CONFIGURACION_ID, newValue);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.atributo_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosConfiguracionAtributoId() throws DataStoreException {
		return getInt(ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.atributo_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosConfiguracionAtributoId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID);
	}

	/**
	 * Set the value of the atributos_configuracion.atributo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionAtributoId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID, newValue);
	}

	/**
	 * Set the value of the atributos_configuracion.atributo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionAtributoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_CONFIGURACION_ATRIBUTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.valor column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosConfiguracionValor() throws DataStoreException {
		return getString(ATRIBUTOS_CONFIGURACION_VALOR);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.valor column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosConfiguracionValor(int row)
			throws DataStoreException {
		return getString(row, ATRIBUTOS_CONFIGURACION_VALOR);
	}

	/**
	 * Set the value of the atributos_configuracion.valor column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionValor(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_CONFIGURACION_VALOR, newValue);
	}

	/**
	 * Set the value of the atributos_configuracion.valor column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionValor(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_CONFIGURACION_VALOR, newValue);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.valor column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosConfiguracionValorHasta()
			throws DataStoreException {
		return getString(ATRIBUTOS_CONFIGURACION_VALOR_HASTA);
	}

	/**
	 * Retrieve the value of the atributos_configuracion.valor column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosConfiguracionValorHasta(int row)
			throws DataStoreException {
		return getString(row, ATRIBUTOS_CONFIGURACION_VALOR_HASTA);
	}

	/**
	 * Set the value of the atributos_configuracion.valor column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionValorHasta(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_CONFIGURACION_VALOR_HASTA, newValue);
	}

	/**
	 * Set the value of the atributos_configuracion.valor column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosConfiguracionValorHasta(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_CONFIGURACION_VALOR_HASTA, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolNombre() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_NOMBRE);
	}

	/**
	 * Retrieve the value of the atributos_rol.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolNombre(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_NOMBRE);
	}

	/**
	 * Set the value of the atributos_rol.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolNombre(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_NOMBRE, newValue);
	}

	/**
	 * Set the value of the atributos_rol.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the configuracion.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConfiguracionNombre() throws DataStoreException {
		return getString(CONFIGURACION_NOMBRE);
	}

	/**
	 * Retrieve the value of the configuracion.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConfiguracionNombre(int row) throws DataStoreException {
		return getString(row, CONFIGURACION_NOMBRE);
	}

	/**
	 * Set the value of the configuracion.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConfiguracionNombre(String newValue)
			throws DataStoreException {
		setString(CONFIGURACION_NOMBRE, newValue);
	}

	/**
	 * Set the value of the configuracion.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConfiguracionNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CONFIGURACION_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the esquema_configuracion.nombre column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEsquemaConfiguracionNombre() throws DataStoreException {
		return getString(ESQUEMA_CONFIGURACION_NOMBRE);
	}

	/**
	 * Retrieve the value of the esquema_configuracion.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEsquemaConfiguracionNombre(int row)
			throws DataStoreException {
		return getString(row, ESQUEMA_CONFIGURACION_NOMBRE);
	}

	/**
	 * Set the value of the esquema_configuracion.nombre column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEsquemaConfiguracionNombre(String newValue)
			throws DataStoreException {
		setString(ESQUEMA_CONFIGURACION_NOMBRE, newValue);
	}

	/**
	 * Set the value of the esquema_configuracion.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEsquemaConfiguracionNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ESQUEMA_CONFIGURACION_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	public static Iterator<AtributoConfiguracion> determinaAtributosConfiguración(
			int esquema_configuracion_id, int objeto_id, String tipo_objeto,
			String nombre_objeto) throws DataStoreException, SQLException {

		ArrayList<AtributoConfiguracion> atributosConfiguracion = new ArrayList<AtributoConfiguracion>();
		AtributoConfiguracion atributo = new AtributoConfiguracion();

		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String sql = null;

		try {
			conexion = DBConnection.getConnection("infraestructura",
					"infraestructura");

			// Selecciona todos los id's de atributos utilizados para determinar
			// cadenas de aprobación de solicitudes de compra
			sql = "SELECT DISTINCT atributo_id "
					+ "FROM atributos_configuracion NATURAL JOIN configuracion "
					+ "WHERE esquema_configuracion_id = "
					+ esquema_configuracion_id;

			st = conexion.createStatement();
			r = st.executeQuery(sql);

			r.beforeFirst();
			while (r.next()) {
				atributo = new AtributoConfiguracion(r.getInt(1), AtributosEntidadModel
						.getValorAtributoObjeto(r.getInt(1), objeto_id,
								tipo_objeto, nombre_objeto));
				atributosConfiguracion.add(atributo);
			}

			if (atributosConfiguracion.size() == 0) {
				return null;
			}
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {					
				}
			}
			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					MessageLog.writeErrorMessage(e, null);
				}

			if (conexion != null)
				conexion.freeConnection();
		}

		return atributosConfiguracion.iterator();

	}

	// $ENDCUSTOMMETHODS$

}
