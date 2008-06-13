package infraestructura.models;

import infraestructura.utils.ConvierteMayusculasValidation;

import java.sql.SQLException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * RolesEntidadModel: A SOFIA generated model
 */
public class RolesEntidadModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2274694010463921032L;
	// constants for columns
	public static final String ROLES_ENTIDAD_DESDE = "roles_entidad.desde";
	public static final String ROLES_ENTIDAD_HASTA = "roles_entidad.hasta";
	public static final String ROLES_ENTIDAD_ACTIVO = "roles_entidad.activo";
	public static final String ROLES_ENTIDAD_ANULADO = "roles_entidad.anulado";
	public static final String ROLES_ENTIDAD_ENTIDAD_ID = "roles_entidad.entidad_id";
	public static final String ROLES_ENTIDAD_ROL = "roles_entidad.rol";
	public static final String ROL_ENTIDAD_NOMBRE = "rol_entidad.nombre";
	public static final String ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";
	public static final String ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new RolesEntidadModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public RolesEntidadModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new RolesEntidadModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public RolesEntidadModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("roles_entidad"), "roles_entidad");
			addTableAlias(computeTableName("rol_entidad"), null);
			addTableAlias(computeTableName("entidad_externa"), null);

			// add columns
			addColumn(computeTableName("roles_entidad"), "desde",
					DataStore.DATATYPE_DATE, false, true, ROLES_ENTIDAD_DESDE);
			addColumn(computeTableName("roles_entidad"), "hasta",
					DataStore.DATATYPE_DATE, false, true, ROLES_ENTIDAD_HASTA);
			addColumn(computeTableName("roles_entidad"), "activo",
					DataStore.DATATYPE_STRING, false, true,
					ROLES_ENTIDAD_ACTIVO);
			addColumn(computeTableName("roles_entidad"), "anulado",
					DataStore.DATATYPE_STRING, false, true,
					ROLES_ENTIDAD_ANULADO);
			addColumn(computeTableName("roles_entidad"), "entidad_id",
					DataStore.DATATYPE_INT, true, true,
					ROLES_ENTIDAD_ENTIDAD_ID);
			addColumn(computeTableName("roles_entidad"), "rol",
					DataStore.DATATYPE_STRING, true, true, ROLES_ENTIDAD_ROL);
			addColumn(computeTableName("rol_entidad"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ROL_ENTIDAD_NOMBRE);
			addColumn(computeTableName("entidad_externa"), "codigo",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_CODIGO);
			addColumn(computeTableName("entidad_externa"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					ENTIDAD_EXTERNA_NOMBRE);

			// add joins
			addJoin(computeTableAndFieldName("roles_entidad.entidad_id"),
					computeTableAndFieldName("entidad_externa.entidad_id"),
					false);
			addJoin(computeTableAndFieldName("roles_entidad.rol"),
					computeTableAndFieldName("rol_entidad.rol"), false);

			// set order by
			setOrderBy(computeTableAndFieldName("roles_entidad.rol") + " ASC");

			// add validations
			addRequiredRule(ROLES_ENTIDAD_DESDE,
					"Fecha de vigencia desde es obligatoria");
			addRequiredRule(ROLES_ENTIDAD_ENTIDAD_ID,
					"Se requiere vincular el rola una entidad");
			addLookupRule(ROLES_ENTIDAD_ENTIDAD_ID, "entidad_externa",
					"'entidad_id = '+ roles_entidad.entidad_id", "nombre",
					"entidad_externa.nombre", "Entidad externa inexistente");
			addRequiredRule(ROLES_ENTIDAD_ROL, "Se requiere asignar un rol");
			addLookupRule(ROLES_ENTIDAD_ROL, computeTableName("rol_entidad"),
					"'rol_entidad.rol = \"' + roles_entidad.rol + '\"'",
					"nombre", ROL_ENTIDAD_NOMBRE, "Rol de entidad inexistente");
			addExpressionRule(ROLES_ENTIDAD_ROL,
					new ConvierteMayusculasValidation(ROLES_ENTIDAD_ROL), "",
					false);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the roles_entidad.desde column for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRolesEntidadDesde() throws DataStoreException {
		return getDate(ROLES_ENTIDAD_DESDE);
	}

	/**
	 * Retrieve the value of the roles_entidad.desde column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRolesEntidadDesde(int row)
			throws DataStoreException {
		return getDate(row, ROLES_ENTIDAD_DESDE);
	}

	/**
	 * Set the value of the roles_entidad.desde column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadDesde(java.sql.Date newValue)
			throws DataStoreException {
		setDate(ROLES_ENTIDAD_DESDE, newValue);
	}

	/**
	 * Set the value of the roles_entidad.desde column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadDesde(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, ROLES_ENTIDAD_DESDE, newValue);
	}

	/**
	 * Retrieve the value of the roles_entidad.hasta column for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRolesEntidadHasta() throws DataStoreException {
		return getDate(ROLES_ENTIDAD_HASTA);
	}

	/**
	 * Retrieve the value of the roles_entidad.hasta column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getRolesEntidadHasta(int row)
			throws DataStoreException {
		return getDate(row, ROLES_ENTIDAD_HASTA);
	}

	/**
	 * Set the value of the roles_entidad.hasta column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadHasta(java.sql.Date newValue)
			throws DataStoreException {
		setDate(ROLES_ENTIDAD_HASTA, newValue);
	}

	/**
	 * Set the value of the roles_entidad.hasta column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadHasta(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, ROLES_ENTIDAD_HASTA, newValue);
	}

	/**
	 * Retrieve the value of the roles_entidad.activo column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadActivo() throws DataStoreException {
		return getString(ROLES_ENTIDAD_ACTIVO);
	}

	/**
	 * Retrieve the value of the roles_entidad.activo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadActivo(int row) throws DataStoreException {
		return getString(row, ROLES_ENTIDAD_ACTIVO);
	}

	/**
	 * Set the value of the roles_entidad.activo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadActivo(String newValue)
			throws DataStoreException {
		setString(ROLES_ENTIDAD_ACTIVO, newValue);
	}

	/**
	 * Set the value of the roles_entidad.activo column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadActivo(int row, String newValue)
			throws DataStoreException {
		setString(row, ROLES_ENTIDAD_ACTIVO, newValue);
	}

	/**
	 * Retrieve the value of the roles_entidad.anulado column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadAnulado() throws DataStoreException {
		return getString(ROLES_ENTIDAD_ANULADO);
	}

	/**
	 * Retrieve the value of the roles_entidad.anulado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadAnulado(int row) throws DataStoreException {
		return getString(row, ROLES_ENTIDAD_ANULADO);
	}

	/**
	 * Set the value of the roles_entidad.anulado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadAnulado(String newValue)
			throws DataStoreException {
		setString(ROLES_ENTIDAD_ANULADO, newValue);
	}

	/**
	 * Set the value of the roles_entidad.anulado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadAnulado(int row, String newValue)
			throws DataStoreException {
		setString(row, ROLES_ENTIDAD_ANULADO, newValue);
	}

	/**
	 * Retrieve the value of the roles_entidad.entidad_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRolesEntidadEntidadId() throws DataStoreException {
		return getInt(ROLES_ENTIDAD_ENTIDAD_ID);
	}

	/**
	 * Retrieve the value of the roles_entidad.entidad_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getRolesEntidadEntidadId(int row) throws DataStoreException {
		return getInt(row, ROLES_ENTIDAD_ENTIDAD_ID);
	}

	/**
	 * Set the value of the roles_entidad.entidad_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadEntidadId(int newValue)
			throws DataStoreException {
		setInt(ROLES_ENTIDAD_ENTIDAD_ID, newValue);
	}

	/**
	 * Set the value of the roles_entidad.entidad_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadEntidadId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ROLES_ENTIDAD_ENTIDAD_ID, newValue);
	}

	/**
	 * Retrieve the value of the roles_entidad.rol column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadRol() throws DataStoreException {
		return getString(ROLES_ENTIDAD_ROL);
	}

	/**
	 * Retrieve the value of the roles_entidad.rol column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolesEntidadRol(int row) throws DataStoreException {
		return getString(row, ROLES_ENTIDAD_ROL);
	}

	/**
	 * Set the value of the roles_entidad.rol column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadRol(String newValue) throws DataStoreException {
		setString(ROLES_ENTIDAD_ROL, newValue);
	}

	/**
	 * Set the value of the roles_entidad.rol column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolesEntidadRol(int row, String newValue)
			throws DataStoreException {
		setString(row, ROLES_ENTIDAD_ROL, newValue);
	}

	/**
	 * Retrieve the value of the rol_entidad.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolEntidadNombre() throws DataStoreException {
		return getString(ROL_ENTIDAD_NOMBRE);
	}

	/**
	 * Retrieve the value of the rol_entidad.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getRolEntidadNombre(int row) throws DataStoreException {
		return getString(row, ROL_ENTIDAD_NOMBRE);
	}

	/**
	 * Set the value of the rol_entidad.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolEntidadNombre(String newValue) throws DataStoreException {
		setString(ROL_ENTIDAD_NOMBRE, newValue);
	}

	/**
	 * Set the value of the rol_entidad.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setRolEntidadNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ROL_ENTIDAD_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the entidad_externa.codigo column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaCodigo() throws DataStoreException {
		return getString(ENTIDAD_EXTERNA_CODIGO);
	}

	/**
	 * Retrieve the value of the entidad_externa.codigo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaCodigo(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_CODIGO);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaCodigo(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_CODIGO, newValue);
	}

	/**
	 * Set the value of the entidad_externa.codigo column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaCodigo(int row, String newValue)
			throws DataStoreException {
		setString(row, ENTIDAD_EXTERNA_CODIGO, newValue);
	}

	/**
	 * Retrieve the value of the entidad_externa.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaNombre() throws DataStoreException {
		return getString(ENTIDAD_EXTERNA_NOMBRE);
	}

	/**
	 * Retrieve the value of the entidad_externa.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getEntidadExternaNombre(int row) throws DataStoreException {
		return getString(row, ENTIDAD_EXTERNA_NOMBRE);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(String newValue)
			throws DataStoreException {
		setString(ENTIDAD_EXTERNA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the entidad_externa.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setEntidadExternaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ENTIDAD_EXTERNA_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	/**
	 * Completa las columnas por default para evitar probelmas con la base de
	 * datos Sólo setea el default si no está definido aún
	 * 
	 * @author demian
	 * @param row
	 *            indica sobre que fila del datastor se realiza la operación
	 */
	public void setColumnasDefault(int row, int entidad_id)
			throws DataStoreException {
		// le asigno la entidad (maestro), sólo si no está seteada aún
		int v_entidad_id = getRolesEntidadEntidadId(row);
		if (v_entidad_id < 1)
			setRolesEntidadEntidadId(row, entidad_id);

		// solo setea las columnas si son null
		String col = getRolesEntidadActivo(row);
		if (col == null)
			setRolesEntidadActivo(row, "F");
		col = getRolesEntidadAnulado(row);
		if (col == null)
			setRolesEntidadAnulado(row, "F");
	}

	/**
	 * Realiza el proceso de activación de una entidad
	 * 
	 * @param row
	 *            registro dentro del datastor que se desea activar
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void activarEntidad(int row) throws DataStoreException, SQLException {
		String estado = getRolesEntidadActivo(row);
		if (estado == "V") {
			throw new DataStoreException(
					"El rol para la entidad YA está activa");
		}

		// TODO: Se debe completar con las validaciones "reglas de negocio" para
		// activar a una entidad

		setRolesEntidadActivo(row, "V");
		update();
	}

	@Override
	public void retrieve(DBConnection conn, String criteria)
			throws SQLException {

		String criterioAdicional = "roles_entidad.anulado = \"F\"";

		if (criteria == null || criteria.length() == 0) {
			criteria = criterioAdicional;
		} else {
			criteria = criteria + " and " + criterioAdicional;
		}

		super.retrieve(conn, criteria);
	}

	// $ENDCUSTOMMETHODS$

}
