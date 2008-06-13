package infraestructura.models;

import java.sql.SQLException;

import infraestructura.utils.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * AtributosRolModel: A SOFIA generated model
 */
public class AtributosRolModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946764462031528457L;
	// constants for columns
	public static final String ATRIBUTOS_ROL_ATRIBUTO_ID = "atributos_rol.atributo_id";
	public static final String ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";
	public static final String ATRIBUTOS_ROL_DESCRIPCION = "atributos_rol.descripcion";
	public static final String ATRIBUTOS_ROL_OBSERVACIONES = "atributos_rol.observaciones";
	public static final String ATRIBUTOS_ROL_ROL = "atributos_rol.rol";
	public static final String ATRIBUTOS_ROL_DESDE = "atributos_rol.desde";
	public static final String ATRIBUTOS_ROL_HASTA = "atributos_rol.hasta";
	public static final String ATRIBUTOS_ROL_ANULADO = "atributos_rol.anulado";
	public static final String ATRIBUTOS_ROL_OBLIGATORIO = "atributos_rol.obligatorio";
	public static final String ATRIBUTOS_ROL_TIPO_DATO = "atributos_rol.tipo_dato";
	public static final String ATRIBUTOS_ROL_LOV = "atributos_rol.lov";
	public static final String ATRIBUTOS_ROL_VALIDADOR = "atributos_rol.validador";
	public static final String ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID = "atributos_rol.clase_atributo_rol_id";
	public static final String ATRIBUTOS_ROL_NOMBRE_OBJETO = "atributos_rol.nombre_objeto";
	public static final String ATRIBUTOS_ROL_TIPO_OBJETO = "atributos_rol.tipo_objeto";
	public static final String CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";
	public static final String ROL_ENTIDAD_NOMBRE = "rol_entidad.nombre";
	public static final String ATRIBUTOS_ROL_CLAVE = "atributos_rol.clave";
	public static final String ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID = "atributos_rol.clase_lov_atributo_id";
	public static final String CLASE_LOV_ATRIBUTO_NOMBRE = "clase_lov_atributo.nombre";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new AtributosRolModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public AtributosRolModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new AtributosRolModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public AtributosRolModel(String appName, String profile) {
		super(appName, profile);

		try {

			// add aliases
			addTableAlias(computeTableName("atributos_rol"), "atributos_rol");
			addTableAlias(computeTableName("clase_atributo_rol"), null);
			addTableAlias(computeTableName("rol_entidad"), null);
			addTableAlias(computeTableName("clase_lov_atributo"), null);

			// add columns
			addColumn(computeTableName("atributos_rol"), "atributo_id",
					DataStore.DATATYPE_INT, true, true,
					ATRIBUTOS_ROL_ATRIBUTO_ID);
			addColumn(computeTableName("atributos_rol"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_NOMBRE);
			addColumn(computeTableName("atributos_rol"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_DESCRIPCION);
			addColumn(computeTableName("atributos_rol"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_OBSERVACIONES);
			addColumn(computeTableName("atributos_rol"), "rol",
					DataStore.DATATYPE_STRING, false, true, ATRIBUTOS_ROL_ROL);
			addColumn(computeTableName("atributos_rol"), "desde",
					DataStore.DATATYPE_DATE, false, true, ATRIBUTOS_ROL_DESDE);
			addColumn(computeTableName("atributos_rol"), "hasta",
					DataStore.DATATYPE_DATE, false, true, ATRIBUTOS_ROL_HASTA);
			addColumn(computeTableName("atributos_rol"), "anulado",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_ANULADO);
			addColumn(computeTableName("atributos_rol"), "clave",
					DataStore.DATATYPE_STRING, false, true, ATRIBUTOS_ROL_CLAVE);
			addColumn(computeTableName("atributos_rol"), "obligatorio",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_OBLIGATORIO);
			addColumn(computeTableName("atributos_rol"), "tipo_dato",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_TIPO_DATO);
			addColumn(computeTableName("atributos_rol"),
					"clase_lov_atributo_id", DataStore.DATATYPE_INT, false,
					true, ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID);
			addColumn(computeTableName("atributos_rol"),
					"clase_atributo_rol_id", DataStore.DATATYPE_INT, false,
					true, ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID);
			addColumn(computeTableName("atributos_rol"), "lov",
					DataStore.DATATYPE_STRING, false, true, ATRIBUTOS_ROL_LOV);
			addColumn(computeTableName("atributos_rol"), "validador",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_VALIDADOR);
			addColumn(computeTableName("atributos_rol"), "nombre_objeto",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_NOMBRE_OBJETO);
			addColumn(computeTableName("atributos_rol"), "tipo_objeto",
					DataStore.DATATYPE_STRING, false, true,
					ATRIBUTOS_ROL_TIPO_OBJETO);
			addColumn(computeTableName("clase_atributo_rol"), "etiqueta",
					DataStore.DATATYPE_STRING, false, false,
					CLASE_ATRIBUTO_ROL_ETIQUETA);
			addColumn(computeTableName("rol_entidad"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ROL_ENTIDAD_NOMBRE);
			addColumn(computeTableName("clase_lov_atributo"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					CLASE_LOV_ATRIBUTO_NOMBRE);

			// add joins
			addJoin(
					computeTableAndFieldName("atributos_rol.clase_atributo_rol_id"),
					computeTableAndFieldName("clase_atributo_rol.clase_atributo_rol_id"),
					true);
			addJoin(computeTableAndFieldName("atributos_rol.rol"),
					computeTableAndFieldName("rol_entidad.rol"), true);
			addJoin(
					computeTableAndFieldName("atributos_rol.clase_lov_atributo_id"),
					computeTableAndFieldName("clase_lov_atributo.clase_lov_atributo_id"),
					true);

			// set order by
			setOrderBy(computeTableAndFieldName("atributos_rol.nombre")
					+ " ASC");

			// set autoincrement
			setAutoIncrement(ATRIBUTOS_ROL_ATRIBUTO_ID, true);
			setUpdateable(ATRIBUTOS_ROL_ATRIBUTO_ID, false);

			// add validations
			addRequiredRule(ATRIBUTOS_ROL_NOMBRE,
					"El nombre del rol es obligatorio");
			/*addLookupRule(ATRIBUTOS_ROL_ROL, computeTableName("rol_entidad"),
					"'rol_entidad.rol = \"'+ atributos_rol.rol + '\"'", "nombre",
					ROL_ENTIDAD_NOMBRE, "Rol de entidad inexistente");*/
			addRequiredRule(ATRIBUTOS_ROL_DESDE,
					"Se requiere la fecha desde cuando est'avigente el rol");
			addLookupRule(
					ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID,
					"clase_atributo_rol",
					"'clase_atributo_rol_id = ' + atributos_rol.clase_atributo_rol_id",
					"etiqueta", "clase_atributo_rol.etiqueta",
					"Clase de atributo inexistente");
			addLookupRule(
					ATRIBUTOS_ROL_NOMBRE_OBJETO,
					"diccionario_aplicacion",
					"'tipo_objeto = \"' + atributos_rol.tipo_objeto + '\" AND nombre_objeto = \"' + atributos_rol.nombre_objeto + '\"'",
					null, null, "Objeto de Aplicación inexistente");
			addExpressionRule(ATRIBUTOS_ROL_NOMBRE,
					new ConvierteMayusculasValidation(ATRIBUTOS_ROL_NOMBRE),
					"", false);
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}

		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the atributos_rol.atributo_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolAtributoId() throws DataStoreException {
		return getInt(ATRIBUTOS_ROL_ATRIBUTO_ID);
	}

	/**
	 * Retrieve the value of the atributos_rol.atributo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolAtributoId(int row) throws DataStoreException {
		return getInt(row, ATRIBUTOS_ROL_ATRIBUTO_ID);
	}

	/**
	 * Set the value of the atributos_rol.atributo_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolAtributoId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_ROL_ATRIBUTO_ID, newValue);
	}

	/**
	 * Set the value of the atributos_rol.atributo_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolAtributoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_ROL_ATRIBUTO_ID, newValue);
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
	 * Retrieve the value of the atributos_rol.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolDescripcion() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the atributos_rol.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolDescripcion(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_DESCRIPCION);
	}

	/**
	 * Set the value of the atributos_rol.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolDescripcion(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the atributos_rol.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolObservaciones() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the atributos_rol.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolObservaciones(int row)
			throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_OBSERVACIONES);
	}

	/**
	 * Set the value of the atributos_rol.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolObservaciones(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the atributos_rol.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.rol column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolRol() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_ROL);
	}

	/**
	 * Retrieve the value of the atributos_rol.rol column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolRol(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_ROL);
	}

	/**
	 * Set the value of the atributos_rol.rol column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolRol(String newValue) throws DataStoreException {
		setString(ATRIBUTOS_ROL_ROL, newValue);
	}

	/**
	 * Set the value of the atributos_rol.rol column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolRol(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_ROL, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.desde column for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosRolDesde() throws DataStoreException {
		return getDate(ATRIBUTOS_ROL_DESDE);
	}

	/**
	 * Retrieve the value of the atributos_rol.desde column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosRolDesde(int row)
			throws DataStoreException {
		return getDate(row, ATRIBUTOS_ROL_DESDE);
	}

	/**
	 * Set the value of the atributos_rol.desde column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolDesde(java.sql.Date newValue)
			throws DataStoreException {
		setDate(ATRIBUTOS_ROL_DESDE, newValue);
	}

	/**
	 * Set the value of the atributos_rol.desde column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolDesde(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, ATRIBUTOS_ROL_DESDE, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.hasta column for the current row.
	 * 
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosRolHasta() throws DataStoreException {
		return getDate(ATRIBUTOS_ROL_HASTA);
	}

	/**
	 * Retrieve the value of the atributos_rol.hasta column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return java.sql.Date
	 * @throws DataStoreException
	 */
	public java.sql.Date getAtributosRolHasta(int row)
			throws DataStoreException {
		return getDate(row, ATRIBUTOS_ROL_HASTA);
	}

	/**
	 * Set the value of the atributos_rol.hasta column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolHasta(java.sql.Date newValue)
			throws DataStoreException {
		setDate(ATRIBUTOS_ROL_HASTA, newValue);
	}

	/**
	 * Set the value of the atributos_rol.hasta column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolHasta(int row, java.sql.Date newValue)
			throws DataStoreException {
		setDate(row, ATRIBUTOS_ROL_HASTA, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.anulado column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolAnulado() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_ANULADO);
	}

	/**
	 * Retrieve the value of the atributos_rol.anulado column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolAnulado(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_ANULADO);
	}

	/**
	 * Set the value of the atributos_rol.anulado column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolAnulado(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_ANULADO, newValue);
	}

	/**
	 * Set the value of the atributos_rol.anulado column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolAnulado(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_ANULADO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.obligatorio column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolObligatorio() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_OBLIGATORIO);
	}

	/**
	 * Retrieve the value of the atributos_rol.obligatorio column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolObligatorio(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_OBLIGATORIO);
	}

	/**
	 * Set the value of the atributos_rol.obligatorio column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolObligatorio(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_OBLIGATORIO, newValue);
	}

	/**
	 * Set the value of the atributos_rol.obligatorio column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolObligatorio(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_OBLIGATORIO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.tipo_dato column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolTipoDato() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_TIPO_DATO);
	}

	/**
	 * Retrieve the value of the atributos_rol.tipo_dato column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolTipoDato(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_TIPO_DATO);
	}

	/**
	 * Set the value of the atributos_rol.tipo_dato column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolTipoDato(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_TIPO_DATO, newValue);
	}

	/**
	 * Set the value of the atributos_rol.tipo_dato column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolTipoDato(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_TIPO_DATO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.lov column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolLov() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_LOV);
	}

	/**
	 * Retrieve the value of the atributos_rol.lov column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolLov(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_LOV);
	}

	/**
	 * Set the value of the atributos_rol.lov column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolLov(String newValue) throws DataStoreException {
		setString(ATRIBUTOS_ROL_LOV, newValue);
	}

	/**
	 * Set the value of the atributos_rol.lov column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolLov(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_LOV, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.validador column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolValidador() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_VALIDADOR);
	}

	/**
	 * Retrieve the value of the atributos_rol.validador column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolValidador(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_VALIDADOR);
	}

	/**
	 * Set the value of the atributos_rol.validador column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolValidador(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_VALIDADOR, newValue);
	}

	/**
	 * Set the value of the atributos_rol.validador column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolValidador(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_VALIDADOR, newValue);
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
	 * Retrieve the value of the atributos_rol.nombre_objeto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolNombreObjeto() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_NOMBRE_OBJETO);
	}

	/**
	 * Retrieve the value of the atributos_rol.nombre_objeto column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolNombreObjeto(int row)
			throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_NOMBRE_OBJETO);
	}

	/**
	 * Set the value of the atributos_rol.nombre_objeto column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolNombreObjeto(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_NOMBRE_OBJETO, newValue);
	}

	/**
	 * Set the value of the atributos_rol.nombre_objeto column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolNombreObjeto(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_NOMBRE_OBJETO, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.tipo_objeto column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolTipoObjeto() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_TIPO_OBJETO);
	}

	/**
	 * Retrieve the value of the atributos_rol.tipo_objeto column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolTipoObjeto(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_TIPO_OBJETO);
	}

	/**
	 * Set the value of the atributos_rol.tipo_objeto column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolTipoObjeto(String newValue)
			throws DataStoreException {
		setString(ATRIBUTOS_ROL_TIPO_OBJETO, newValue);
	}

	/**
	 * Set the value of the atributos_rol.tipo_objeto column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolTipoObjeto(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_TIPO_OBJETO, newValue);
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
	 * Retrieve the value of the atributos_rol.clave column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolClave() throws DataStoreException {
		return getString(ATRIBUTOS_ROL_CLAVE);
	}

	/**
	 * Retrieve the value of the atributos_rol.clave column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getAtributosRolClave(int row) throws DataStoreException {
		return getString(row, ATRIBUTOS_ROL_CLAVE);
	}

	/**
	 * Set the value of the atributos_rol.clave column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClave(String newValue) throws DataStoreException {
		setString(ATRIBUTOS_ROL_CLAVE, newValue);
	}

	/**
	 * Set the value of the atributos_rol.clave column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClave(int row, String newValue)
			throws DataStoreException {
		setString(row, ATRIBUTOS_ROL_CLAVE, newValue);
	}

	/**
	 * Retrieve the value of the atributos_rol.Clase_Lista_valores_atributo_id
	 * column for the current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolClaseListaValoresAtributoId()
			throws DataStoreException {
		return getInt(ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID);
	}

	/**
	 * Retrieve the value of the atributos_rol.Clase_Lista_valores_atributo_id
	 * column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getAtributosRolClaseListaValoresAtributoId(int row)
			throws DataStoreException {
		return getInt(row, ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID);
	}

	/**
	 * Set the value of the atributos_rol.Clase_Lista_valores_atributo_id column
	 * for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClaseListaValoresAtributoId(int newValue)
			throws DataStoreException {
		setInt(ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID, newValue);
	}

	/**
	 * Set the value of the atributos_rol.Clase_Lista_valores_atributo_id column
	 * for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setAtributosRolClaseListaValoresAtributoId(int row, int newValue)
			throws DataStoreException {
		setInt(row, ATRIBUTOS_ROL_CLASE_LOV_ATRIBUTO_ID, newValue);
	}

	/**
	 * Retrieve the value of the Clase_Lista_valores_atributo.nombre column for
	 * the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseListaValoresAtributoNombre()
			throws DataStoreException {
		return getString(CLASE_LOV_ATRIBUTO_NOMBRE);
	}

	/**
	 * Retrieve the value of the Clase_Lista_valores_atributo.nombre column for
	 * the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getClaseListaValoresAtributoNombre(int row)
			throws DataStoreException {
		return getString(row, CLASE_LOV_ATRIBUTO_NOMBRE);
	}

	/**
	 * Set the value of the Clase_Lista_valores_atributo.nombre column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseListaValoresAtributoNombre(String newValue)
			throws DataStoreException {
		setString(CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
	}

	/**
	 * Set the value of the Clase_Lista_valores_atributo.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setClaseListaValoresAtributoNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CLASE_LOV_ATRIBUTO_NOMBRE, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	@Override
	public void update() throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		for (int row = 0; row < getRowCount(); row++) {
			if (getAtributosRolAnulado(row) == null)
				setAtributosRolAnulado(row, "F");
			if (getAtributosRolObligatorio(row) == null)
				setAtributosRolObligatorio(row, "F");
		}
		super.update();
	}

	// $ENDCUSTOMMETHODS$

}
