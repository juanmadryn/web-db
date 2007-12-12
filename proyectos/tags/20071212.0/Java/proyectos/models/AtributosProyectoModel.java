package proyectos.models;

import infraestructura.controllers.BaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AtributosProyectoModel: A SOFIA generated model
 */
public class AtributosProyectoModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3281244297024598522L;

	// constants for columns
	public static final String ATRIBUTOS_PROYECTO_VALOR = "atributos_proyecto.valor";

	public static final String ATRIBUTOS_PROYECTO_VALOR_ENTERO = "atributos_proyecto.valor_entero";

	public static final String ATRIBUTOS_PROYECTO_VALOR_REAL = "atributos_proyecto.valor_real";

	public static final String ATRIBUTOS_PROYECTO_VALOR_FECHA = "atributos_proyecto.valor_fecha";

	public static final String ATRIBUTOS_PROYECTO_VALOR_LOGICO = "atributos_proyecto.valor_logico";

	public static final String ATRIBUTOS_PROYECTO_ATRIBUTO_ID = "atributos_proyecto.atributo_id";

	public static final String ATRIBUTOS_PROYECTO_PROYECTO_ID = "atributos_proyecto.proyecto_id";

	public static final String PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";

	public static final String ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID = "atributos_rol.clase_atributo_rol_id";

	public static final String CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	private BaseController _pagina;

	// $ENDCUSTOMVARS$

	/**
	 * Create a new AtributosProyectoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public AtributosProyectoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new AtributosProyectoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public AtributosProyectoModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("atributos_proyecto"),
					"atributos_proyecto");
			addTableAlias(computeTableName("proyectos"), "proyectos");
			addTableAlias(computeTableName("infraestructura.atributos_rol"),
					"atributos_rol");
			addTableAlias(
					computeTableName("infraestructura.clase_atributo_rol"),
					"clase_atributo_rol");

			// add columns
			addColumn(computeTableName("atributos_proyecto"), "valor",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_PROYECTO_VALOR);
			addColumn(computeTableName("atributos_proyecto"), "valor_entero",
					DataStore.DATATYPE_INT, false, true,
					ATRIBUTOS_PROYECTO_VALOR_ENTERO);
			addColumn(computeTableName("atributos_proyecto"), "valor_real",
					DataStore.DATATYPE_DOUBLE, false, true,
					ATRIBUTOS_PROYECTO_VALOR_REAL);
			addColumn(computeTableName("atributos_proyecto"), "valor_fecha",
					DataStore.DATATYPE_DATE, false, true,
					ATRIBUTOS_PROYECTO_VALOR_FECHA);
			addColumn(computeTableName("atributos_proyecto"), "valor_logico",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_PROYECTO_VALOR_LOGICO);
			addColumn(computeTableName("atributos_proyecto"), "atributo_id",
					DataStore.DATATYPE_INT, true, true,
					ATRIBUTOS_PROYECTO_ATRIBUTO_ID);
			addColumn(computeTableName("atributos_proyecto"), "proyecto_id",
					DataStore.DATATYPE_INT, true, true,
					ATRIBUTOS_PROYECTO_PROYECTO_ID);
			addColumn(computeTableName("proyectos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, PROYECTOS_NOMBRE);
			addColumn(computeTableName("infraestructura.atributos_rol"),
					"nombre", DataStore.DATATYPE_STRING, false, false,
					ATRIBUTOS_ROL_NOMBRE);
			addColumn(computeTableName("infraestructura.atributos_rol"),
					"clase_atributo_rol_id", DataStore.DATATYPE_INT, false,
					false, ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID);
			addColumn(computeTableName("infraestructura.clase_atributo_rol"),
					"etiqueta", DataStore.DATATYPE_STRING, false, false,
					CLASE_ATRIBUTO_ROL_ETIQUETA);

			// add joins
			addJoin(computeTableAndFieldName("atributos_proyecto.proyecto_id"),
					computeTableAndFieldName("proyectos.proyecto_id"), false);
			addJoin(computeTableAndFieldName("atributos_proyecto.atributo_id"),
					computeTableAndFieldName("atributos_rol.atributo_id"),
					false);
			addJoin(
					computeTableAndFieldName("atributos_rol.clase_atributo_rol_id"),
					computeTableAndFieldName("clase_atributo_rol.clase_atributo_rol_id"),
					true);

			// add validations
			addRequiredRule(ATRIBUTOS_PROYECTO_ATRIBUTO_ID,
					"Indicar un Atributo es obligatorio");
			addRequiredRule(ATRIBUTOS_PROYECTO_PROYECTO_ID,
					"El Proyecto para los atributos es obligatorio");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosProyectoValor() throws DataStoreException {
		return getString(ATRIBUTOS_PROYECTO_VALOR);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosProyectoValor(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_PROYECTO_VALOR);
	}

	/**
	 * Set the value of the atributos_proyecto.valor column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValor(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_PROYECTO_VALOR, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.valor column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValor(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_PROYECTO_VALOR, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_entero column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoValorEntero() throws DataStoreException {
		return getInt(ATRIBUTOS_PROYECTO_VALOR_ENTERO);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_entero column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoValorEntero(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_PROYECTO_VALOR_ENTERO);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_entero column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorEntero(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_PROYECTO_VALOR_ENTERO, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_entero column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorEntero(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_PROYECTO_VALOR_ENTERO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_real column for the
	 * current row.
	 * 
	 * @return double
	 * @throws DataStoreException
	 */
	public double getAtributosProyectoValorReal() throws DataStoreException {
		return getDouble(ATRIBUTOS_PROYECTO_VALOR_REAL);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_real column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return double
	 * @throws DataStoreException
	 */
	public double getAtributosProyectoValorReal(int row)
			throws DataStoreException {
		return getDouble(row, ATRIBUTOS_PROYECTO_VALOR_REAL);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_real column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorReal(double newValue)
			throws DataStoreException {
		setDouble(ATRIBUTOS_PROYECTO_VALOR_REAL, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_real column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorReal(int row, double newValue)
			throws DataStoreException {
		setDouble(row, ATRIBUTOS_PROYECTO_VALOR_REAL, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_fecha column for the
	 * current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosProyectoValorFecha()
			throws DataStoreException {
		return getDate(ATRIBUTOS_PROYECTO_VALOR_FECHA);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosProyectoValorFecha(int row)
			throws DataStoreException {
		return getDate(row, ATRIBUTOS_PROYECTO_VALOR_FECHA);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_fecha column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorFecha(java.sql.Date newValue)
			throws DataStoreException {
		setDate(ATRIBUTOS_PROYECTO_VALOR_FECHA, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_fecha column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorFecha(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, ATRIBUTOS_PROYECTO_VALOR_FECHA, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_logico column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosProyectoValorLogico() throws DataStoreException {
		return getString(ATRIBUTOS_PROYECTO_VALOR_LOGICO);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.valor_logico column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosProyectoValorLogico(int row)
			throws DataStoreException {
		return getString(row, ATRIBUTOS_PROYECTO_VALOR_LOGICO);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_logico column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorLogico(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_PROYECTO_VALOR_LOGICO, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.valor_logico column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoValorLogico(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_PROYECTO_VALOR_LOGICO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.atributo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoAtributoId() throws DataStoreException {
		return getInt(ATRIBUTOS_PROYECTO_ATRIBUTO_ID);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.atributo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoAtributoId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_PROYECTO_ATRIBUTO_ID);
	}

	/**
	 * Set the value of the atributos_proyecto.atributo_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoAtributoId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_PROYECTO_ATRIBUTO_ID, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.atributo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoAtributoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_PROYECTO_ATRIBUTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.proyecto_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoProyectoId() throws DataStoreException {
		return getInt(ATRIBUTOS_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Retrieve the value of the atributos_proyecto.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosProyectoProyectoId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_PROYECTO_PROYECTO_ID);
	}

	/**
	 * Set the value of the atributos_proyecto.proyecto_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoProyectoId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Set the value of the atributos_proyecto.proyecto_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosProyectoProyectoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_PROYECTO_PROYECTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the proyectos.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosNombre() throws DataStoreException {
		return getString(PROYECTOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the proyectos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getProyectosNombre(int row) throws DataStoreException {
		return getString(row, PROYECTOS_NOMBRE);
	}

	/**
	 * Set the value of the proyectos.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(String newValue) throws DataStoreException {
		setString(PROYECTOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the proyectos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setProyectosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, PROYECTOS_NOMBRE, newValue);
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
	 * Retrieve the value of the atributos_rol.clase_atributo_rol_id column for
	 * the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolClaseAtributoRolId() throws DataStoreException {
		return getInt(ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID);
	}

	/**
	 * Retrieve the value of the atributos_rol.clase_atributo_rol_id column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolClaseAtributoRolId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID);
	}

	/**
	 * Set the value of the atributos_rol.clase_atributo_rol_id column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClaseAtributoRolId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID, newValue);
	}

	/**
	 * Set the value of the atributos_rol.clase_atributo_rol_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClaseAtributoRolId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID, newValue);
	}

	/**
	 * Retrieve the value of the clase_atributo_rol.etiqueta column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseAtributoRolEtiqueta() throws DataStoreException {
		return getString(CLASE_ATRIBUTO_ROL_ETIQUETA);
	}

	/**
	 * Retrieve the value of the clase_atributo_rol.etiqueta column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseAtributoRolEtiqueta(int row)
			throws DataStoreException {
		return getString(row, CLASE_ATRIBUTO_ROL_ETIQUETA);
	}

	/**
	 * Set the value of the clase_atributo_rol.etiqueta column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseAtributoRolEtiqueta(String newValue)
			throws DataStoreException {
		setString(CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
	}

	/**
	 * Set the value of the clase_atributo_rol.etiqueta column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseAtributoRolEtiqueta(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASE_ATRIBUTO_ROL_ETIQUETA, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	public void setPage(BaseController pagina) {
		this._pagina = pagina;
	}

	/**
	 * Inserta todos los atributos de proyectos para el proyecto p_proyecto_id
	 * Sólo completa los atributos que faltan. Dejando intactos los atributos
	 * existente para el proyecto
	 * 
	 * @param p_proyecto_id
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void generaAtributosProyecto(int p_proyecto_id) throws SQLException,
			DataStoreException {

		DBConnection conexion = null;
		Statement st = null;
		String SQL = null;

		// recupero todos los posibles atributos para un proyecto y recorro
		// buscando si ya existe o no
		// si no existe lo inserto en el data store
		try {
			conexion = DBConnection.getConnection(_pagina.getApplicationName(),
					"proyectos");
			conexion.beginTransaction();

			// inserto todos los atrubutos faltantes
			SQL = "insert into atributos_proyecto (proyecto_id,atributo_id) "
					+ "select "
					+ Integer.toString(p_proyecto_id)
					+ ",atributo_id from infraestructura.atributos_rol "
					+ "where tipo_objeto = \"TABLA\" "
					+ "  and nombre_objeto = \"proyectos\" "
					+ "  and atributo_id not in (select atributo_id from atributos_proyecto "
					+ "                          where proyecto_id = "
					+ Integer.toString(p_proyecto_id) + ")";
			st = conexion.createStatement();
			st.executeUpdate(SQL);

			conexion.commit();

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			_pagina
					.displayErrorMessage("Error insertando atributos para los proyectos");

			conexion.rollback();
		} finally {
			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conexion != null)
				conexion.freeConnection();
		}

		// reseteo el datastore y recupero los atributos existenes para el
		// proyecto
		reset();
		retrieve("atributos_proyecto.proyecto_id = "
				+ Integer.toString(p_proyecto_id));

	}

	/**
	 * recupera los atributos del proyecto según la etiqueta pasada como
	 * parámetro
	 * 
	 * @param p_etiqueta
	 * @param p_proyecto_id
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void recuperaAtributosEtiqueta(String p_etiqueta, int p_proyecto_id)
			throws SQLException, DataStoreException {
		String cond = null;

		reset();

		cond = "atributos_proyecto.proyecto_id = "
				+ Integer.toString(p_proyecto_id);
		if (p_etiqueta == null || p_etiqueta.trim().length() == 0)
			// la etiqueta es null o vacia, recupera lo ssin etiqueta
			cond = cond + " and clase_atributo_rol.etiqueta is null";
		else
			// la etiqueta es no nula recupera lo satributos de esta etiqueta
			cond = cond + " and clase_atributo_rol.etiqueta = \"" + p_etiqueta
					+ "\"";

		retrieve(cond);
		gotoFirst();
	}

	public Hashtable recuperaEtiquetasAtributos() {
		Hashtable<Integer, String> etiquetas = new Hashtable<Integer, String>();

		DBConnection conexion = null;
		Statement st = null;
		ResultSet r = null;
		String SQL = null;

		// recupero todos los posibles atributos para un proyecto y recorro
		// buscando si ya existe o no
		// si no existe lo inserto en el data store
		try {
			conexion = DBConnection.getConnection(_pagina.getApplicationName(),
					"infraestructura");

			// inserto todos los atrubutos faltantes
			SQL = "select distinct t2.etiqueta etiqueta"
					+ "  from atributos_rol t1 "
					+ "  left join clase_atributo_rol t2 using (clase_atributo_rol_id) "
					+ " where tipo_objeto = \"TABLA\" and nombre_objeto = \"proyectos\"";
			st = conexion.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				String etiqueta = r.getString("etiqueta");
				if (etiqueta == null)
					etiqueta = " ";
				etiquetas.put(Integer.valueOf(r.getRow()), etiqueta);
			}

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
			// además de escribir en el log mando mensaje a la página
			_pagina
					.displayErrorMessage("Error recuperando etiquetas de atributos para los proyectos");
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

		return etiquetas;
	}

	// $ENDCUSTOMMETHODS$

}
