package partesEQ.models;

import infraestructura.controllers.BaseController;
import infraestructura.utils.ConvierteMayusculasValidation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * EquipoModel: A SOFIA generated model
 */
public class EquipoModel extends DataStore {

	/**
	 *
	 */
	private static final long serialVersionUID = 5169139283963311988L;

	// constants for columns
	public static final String EQUIPOS_EQUIPO_ID = "equipos.equipo_id";
	public static final String EQUIPOS_TIPO_EQUIPO = "equipos.tipo_equipo";
	public static final String EQUIPOS_NOMBRE = "equipos.nombre";
	public static final String EQUIPOS_DESCRIPCION = "equipos.descripcion";
	public static final String EQUIPOS_OBSERVACIONES = "equipos.observaciones";
	public static final String EQUIPOS_CODIGO_INVENTARIO = "equipos.codigo_inventario";
	public static final String TIPO_EQUIPO_NOMBRE = "tipo_equipo.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	private BaseController _pagina;

	// $ENDCUSTOMVARS$

	/**
	 * Create a new EquipoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 */
	public EquipoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new EquipoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public EquipoModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("tipo_equipo"), "tipo_equipo");
			addTableAlias(computeTableName("equipos"), "equipos");

			// add columns
			addColumn(computeTableName("equipos"), "equipo_id",
					DataStore.DATATYPE_INT, true, true, EQUIPOS_EQUIPO_ID);
			addColumn(computeTableName("equipos"), "tipo_equipo",
					DataStore.DATATYPE_STRING, false, true, EQUIPOS_TIPO_EQUIPO);
			addColumn(computeTableName("equipos"), "nombre",
					DataStore.DATATYPE_STRING, false, true, EQUIPOS_NOMBRE);
			addColumn(computeTableName("equipos"), "descripcion",
					DataStore.DATATYPE_STRING, false, true, EQUIPOS_DESCRIPCION);
			addColumn(computeTableName("equipos"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					EQUIPOS_OBSERVACIONES);
			addColumn(computeTableName("equipos"), "codigo_inventario",
					DataStore.DATATYPE_STRING, false, true,
					EQUIPOS_CODIGO_INVENTARIO);
			addColumn(computeTableName("tipo_equipo"), "nombre",
					DataStore.DATATYPE_STRING, false, false, TIPO_EQUIPO_NOMBRE);

			// add joins
			addJoin(computeTableAndFieldName("equipos.tipo_equipo"),
					computeTableAndFieldName("tipo_equipo.tipo_equipo"), false);

			// add validations
			addRequiredRule(EQUIPOS_NOMBRE,
					"El nombre del equipo es obligatorio");
			addRequiredRule(EQUIPOS_TIPO_EQUIPO,
					"El tipo de equipo es obligatorio");
			addLookupRule(
					EQUIPOS_TIPO_EQUIPO,
					"tipo_equipo",
					"'tipo_equipo.tipo_equipo = \"' + equipos.tipo_equipo + '\"' ",
					"nombre", "tipo_equipo.nombre",
					"Tipo de equipo inexistente");
			addExpressionRule(EQUIPOS_NOMBRE,
					new ConvierteMayusculasValidation(EQUIPOS_NOMBRE), "",
					false);
			addExpressionRule(
					EQUIPOS_CODIGO_INVENTARIO,
					new ConvierteMayusculasValidation(EQUIPOS_CODIGO_INVENTARIO),
					"", false);

			// setea el autoincrement y protege la PK
			setAutoIncrement(EQUIPOS_EQUIPO_ID, true);
			setUpdateable(EQUIPOS_EQUIPO_ID, false);

		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the equipos.equipo_id column for the current row.
	 *
	 * @return int
	 * @throws DataStoreException
	 */
	public int getEquiposEquipoId() throws DataStoreException {
		return getInt(EQUIPOS_EQUIPO_ID);
	}

	/**
	 * Retrieve the value of the equipos.equipo_id column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getEquiposEquipoId(int row) throws DataStoreException {
		return getInt(row, EQUIPOS_EQUIPO_ID);
	}

	/**
	 * Set the value of the equipos.equipo_id column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposEquipoId(int newValue) throws DataStoreException {
		setInt(EQUIPOS_EQUIPO_ID, newValue);
	}

	/**
	 * Set the value of the equipos.equipo_id column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposEquipoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, EQUIPOS_EQUIPO_ID, newValue);
	}

	/**
	 * Retrieve the value of the equipos.tipo_equipo column for the current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposTipoEquipo() throws DataStoreException {
		return getString(EQUIPOS_TIPO_EQUIPO);
	}

	/**
	 * Retrieve the value of the equipos.tipo_equipo column for the specified
	 * row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposTipoEquipo(int row) throws DataStoreException {
		return getString(row, EQUIPOS_TIPO_EQUIPO);
	}

	/**
	 * Set the value of the equipos.tipo_equipo column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposTipoEquipo(String newValue) throws DataStoreException {
		setString(EQUIPOS_TIPO_EQUIPO, newValue);
	}

	/**
	 * Set the value of the equipos.tipo_equipo column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposTipoEquipo(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_TIPO_EQUIPO, newValue);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposNombre() throws DataStoreException {
		return getString(EQUIPOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the equipos.nombre column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposNombre(int row) throws DataStoreException {
		return getString(row, EQUIPOS_NOMBRE);
	}

	/**
	 * Set the value of the equipos.nombre column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposNombre(String newValue) throws DataStoreException {
		setString(EQUIPOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the equipos.nombre column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the equipos.descripcion column for the current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposDescripcion() throws DataStoreException {
		return getString(EQUIPOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the equipos.descripcion column for the specified
	 * row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposDescripcion(int row) throws DataStoreException {
		return getString(row, EQUIPOS_DESCRIPCION);
	}

	/**
	 * Set the value of the equipos.descripcion column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposDescripcion(String newValue)
			throws DataStoreException {
		setString(EQUIPOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the equipos.descripcion column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the equipos.observaciones column for the current
	 * row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposObservaciones() throws DataStoreException {
		return getString(EQUIPOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the equipos.observaciones column for the specified
	 * row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposObservaciones(int row) throws DataStoreException {
		return getString(row, EQUIPOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the equipos.observaciones column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposObservaciones(String newValue)
			throws DataStoreException {
		setString(EQUIPOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the equipos.observaciones column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the equipos.codigo_inventario column for the
	 * current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposCodigoInventario() throws DataStoreException {
		return getString(EQUIPOS_CODIGO_INVENTARIO);
	}

	/**
	 * Retrieve the value of the equipos.codigo_inventario column for the
	 * specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEquiposCodigoInventario(int row) throws DataStoreException {
		return getString(row, EQUIPOS_CODIGO_INVENTARIO);
	}

	/**
	 * Set the value of the equipos.codigo_inventario column for the current
	 * row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposCodigoInventario(String newValue)
			throws DataStoreException {
		setString(EQUIPOS_CODIGO_INVENTARIO, newValue);
	}

	/**
	 * Set the value of the equipos.codigo_inventario column for the specified
	 * row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEquiposCodigoInventario(int row, String newValue)
			throws DataStoreException {
		setString(row, EQUIPOS_CODIGO_INVENTARIO, newValue);
	}

	/**
	 * Retrieve the value of the tipo_equipo.nombre column for the current row.
	 *
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoEquipoNombre() throws DataStoreException {
		return getString(TIPO_EQUIPO_NOMBRE);
	}

	/**
	 * Retrieve the value of the tipo_equipo.nombre column for the specified
	 * row.
	 *
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getTipoEquipoNombre(int row) throws DataStoreException {
		return getString(row, TIPO_EQUIPO_NOMBRE);
	}

	/**
	 * Set the value of the tipo_equipo.nombre column for the current row.
	 *
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoEquipoNombre(String newValue) throws DataStoreException {
		setString(TIPO_EQUIPO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the tipo_equipo.nombre column for the specified row.
	 *
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setTipoEquipoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, TIPO_EQUIPO_NOMBRE, newValue);
	}

	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {

		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;

		try {
			conexion = DBConnection.getConnection(_pagina.getApplicationName(),
					"partesEQ");

			// chequeo si el tipo de equipo consignado es o no inventariable
			SQL = "select inventariable " + "  from tipo_equipo "
					+ "  where nombre = \"" + this.getTipoEquipoNombre() + "\"";
			st = conexion.createStatement();
			r = st.executeQuery(SQL);

			r.first();
			if (r.getString("inventariable").equalsIgnoreCase("V")) {
				// si es inventariable, chequeo q se haya especificado un código
				// de inventario
				if (this.getEquiposCodigoInventario() == null) {
					// si no hay código de inventario mando mensaje a la página
					// y freno el update
					_pagina.displayErrorMessage("Debe especificar un código de inventario para este tipo de equipo.");
					r.close();
					st.close();
					return;
				}
			}
			r.close();
			st.close();

			// chequeo si el código de inventario escrito es único para ese tipo
			// de equipo
			SQL = 	"select count(*) "
					+ "  from equipos "
					+ "  where tipo_equipo = \"" + this.getEquiposTipoEquipo() + "\""
					+ " AND codigo_inventario = \"" + this.getEquiposCodigoInventario() + "\""
					+ " AND equipo_id <> " + this.getEquiposEquipoId();
			st = conexion.createStatement();
			r = st.executeQuery(SQL);

			// si la cuenta da 1, el codigo de inventario ya fue usado
			r.first();
			if (r.getInt(1)==1){
				// mando mensaje a la página y freno el update
				_pagina.displayErrorMessage("El código de inventario especificado ya se ha usado para este tipo de equipo." );
				r.close();
				st.close();
				return;
			}

		} catch (SQLException e) {
			// si surge algún error, escribo mensaje en el log
			MessageLog.writeErrorMessage(e, null);

			// además de escribir en el log mando mensaje a la página
			_pagina.displayErrorMessage("Error determinando inventariabilidad del tipo de equipo");
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
					e.printStackTrace();
				}

			if (conexion != null)
				conexion.freeConnection();
		}

		super.update(conn, handleTrans);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	public void setPage(BaseController pagina) {
		this._pagina = pagina;
	}
	// $ENDCUSTOMMETHODS$

}
