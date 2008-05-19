package inventario.models;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * ConversionesModel: A SOFIA generated model
 */
public class ConversionesModel extends DataStore implements Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1128424351276757735L;
	// constants for columns
	public static final String CONVERSIONES_CONVERSION_ID = "conversiones.conversion_id";
	public static final String CONVERSIONES_ARTICULO_ID = "conversiones.articulo_id";
	public static final String CONVERSIONES_UNIDAD_MEDIDA_ID = "conversiones.unidad_medida_id";
	public static final String CONVERSIONES_FACTOR = "conversiones.factor";
	public static final String CONVERSIONES_OBSERVACIONES = "conversiones.observaciones";
	public static final String UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String ARTICULOS_OBSERVACIONES = "articulos.observaciones";
	public static final String ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String ARTICULOS_UNIDAD_MEDIDA = "articulo_unidad_medida";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new ConversionesModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public ConversionesModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ConversionesModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public ConversionesModel(String appName, String profile) {
		super(appName, profile);

		try {
			// add aliases
			addTableAlias(computeTableName("conversiones"), "conversiones");
			addTableAlias(computeTableName("unidades_medida"),
					"unidades_medida");
			addTableAlias(computeTableName("articulos"), "articulos");

			// add columns
			addColumn(computeTableName("conversiones"), "conversion_id",
					DataStore.DATATYPE_INT, true, true,
					CONVERSIONES_CONVERSION_ID);
			addColumn(computeTableName("conversiones"), "articulo_id",
					DataStore.DATATYPE_INT, false, true,
					CONVERSIONES_ARTICULO_ID);
			addColumn(computeTableName("conversiones"), "unidad_medida_id",
					DataStore.DATATYPE_INT, false, true,
					CONVERSIONES_UNIDAD_MEDIDA_ID);
			addColumn(computeTableName("conversiones"), "factor",
					DataStore.DATATYPE_STRING, false, true, CONVERSIONES_FACTOR);
			addColumn(computeTableName("conversiones"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					CONVERSIONES_OBSERVACIONES);
			addColumn(computeTableName("unidades_medida"), "nombre",
					DataStore.DATATYPE_STRING, false, false,
					UNIDADES_MEDIDA_NOMBRE);
			addColumn(computeTableName("articulos"), "nombre",
					DataStore.DATATYPE_STRING, false, false, ARTICULOS_NOMBRE);
			addColumn(computeTableName("articulos"), "descripcion",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION);
			addColumn(computeTableName("articulos"), "observaciones",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_OBSERVACIONES);
			addColumn(computeTableName("articulos"), "descripcion_completa",
					DataStore.DATATYPE_STRING, false, false,
					ARTICULOS_DESCRIPCION_COMPLETA);

			addBucket(ARTICULOS_UNIDAD_MEDIDA, DataStore.DATATYPE_STRING);

			// add joins
			addJoin(
					computeTableAndFieldName("conversiones.unidad_medida_id"),
					computeTableAndFieldName("unidades_medida.unidad_medida_id"),
					false);
			addJoin(computeTableAndFieldName("conversiones.articulo_id"),
					computeTableAndFieldName("articulos.articulo_id"), false);

			addRequiredRule(CONVERSIONES_ARTICULO_ID, "Indique el art�culo");
			addRequiredRule(CONVERSIONES_UNIDAD_MEDIDA_ID,
					"Indique la unidad de medida");
			addRequiredRule(CONVERSIONES_FACTOR,
					"Indique el factor de conversi�n");

			addLookupRule(
					CONVERSIONES_ARTICULO_ID,
					"articulos",
					"'inventario.articulos.articulo_id = ' + conversiones.articulo_id",
					"nombre", ARTICULOS_NOMBRE,
					"El art�culo indicado no existe");
			addLookupRule(
					CONVERSIONES_ARTICULO_ID,
					"articulos",
					"'inventario.articulos.articulo_id = ' + conversiones.articulo_id",
					"descripcion", ARTICULOS_DESCRIPCION,
					"El art�culo indicado no existe");
			addLookupRule(
					CONVERSIONES_ARTICULO_ID,
					"articulos",
					"'inventario.articulos.articulo_id = ' + conversiones.articulo_id",
					"descripcion_completa", ARTICULOS_DESCRIPCION_COMPLETA,
					"El art�culo indicado no existe");

		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated

		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the conversiones.conversion_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesConversionId() throws DataStoreException {
		return getInt(CONVERSIONES_CONVERSION_ID);
	}

	/**
	 * Retrieve the value of the conversiones.conversion_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesConversionId(int row) throws DataStoreException {
		return getInt(row, CONVERSIONES_CONVERSION_ID);
	}

	/**
	 * Set the value of the conversiones.conversion_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesConversionId(int newValue)
			throws DataStoreException {
		setInt(CONVERSIONES_CONVERSION_ID, newValue);
	}

	/**
	 * Set the value of the conversiones.conversion_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesConversionId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CONVERSIONES_CONVERSION_ID, newValue);
	}

	/**
	 * Retrieve the value of the conversiones.articulo_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesArticuloId() throws DataStoreException {
		return getInt(CONVERSIONES_ARTICULO_ID);
	}

	/**
	 * Retrieve the value of the conversiones.articulo_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesArticuloId(int row) throws DataStoreException {
		return getInt(row, CONVERSIONES_ARTICULO_ID);
	}

	/**
	 * Set the value of the conversiones.articulo_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesArticuloId(int newValue)
			throws DataStoreException {
		setInt(CONVERSIONES_ARTICULO_ID, newValue);
	}

	/**
	 * Set the value of the conversiones.articulo_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesArticuloId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CONVERSIONES_ARTICULO_ID, newValue);
	}

	/**
	 * Retrieve the value of the conversiones.unidad_medida_id column for the
	 * current row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesUnidadMedidaId() throws DataStoreException {
		return getInt(CONVERSIONES_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Retrieve the value of the conversiones.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getConversionesUnidadMedidaId(int row) throws DataStoreException {
		return getInt(row, CONVERSIONES_UNIDAD_MEDIDA_ID);
	}

	/**
	 * Set the value of the conversiones.unidad_medida_id column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesUnidadMedidaId(int newValue)
			throws DataStoreException {
		setInt(CONVERSIONES_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Set the value of the conversiones.unidad_medida_id column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesUnidadMedidaId(int row, int newValue)
			throws DataStoreException {
		setInt(row, CONVERSIONES_UNIDAD_MEDIDA_ID, newValue);
	}

	/**
	 * Retrieve the value of the conversiones.factor column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConversionesFactor() throws DataStoreException {
		return getString(CONVERSIONES_FACTOR);
	}

	/**
	 * Retrieve the value of the conversiones.factor column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConversionesFactor(int row) throws DataStoreException {
		return getString(row, CONVERSIONES_FACTOR);
	}

	/**
	 * Set the value of the conversiones.factor column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesFactor(String newValue)
			throws DataStoreException {
		setString(CONVERSIONES_FACTOR, newValue);
	}

	/**
	 * Set the value of the conversiones.factor column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesFactor(int row, String newValue)
			throws DataStoreException {
		setString(row, CONVERSIONES_FACTOR, newValue);
	}

	/**
	 * Retrieve the value of the conversiones.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConversionesObservaciones() throws DataStoreException {
		return getString(CONVERSIONES_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the conversiones.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getConversionesObservaciones(int row)
			throws DataStoreException {
		return getString(row, CONVERSIONES_OBSERVACIONES);
	}

	/**
	 * Set the value of the conversiones.observaciones column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesObservaciones(String newValue)
			throws DataStoreException {
		setString(CONVERSIONES_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the conversiones.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setConversionesObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, CONVERSIONES_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaNombre() throws DataStoreException {
		return getString(UNIDADES_MEDIDA_NOMBRE);
	}

	/**
	 * Retrieve the value of the unidades_medida.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getUnidadesMedidaNombre(int row) throws DataStoreException {
		return getString(row, UNIDADES_MEDIDA_NOMBRE);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaNombre(String newValue)
			throws DataStoreException {
		setString(UNIDADES_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Set the value of the unidades_medida.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setUnidadesMedidaNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, UNIDADES_MEDIDA_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosNombre() throws DataStoreException {
		return getString(ARTICULOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosNombre(int row) throws DataStoreException {
		return getString(row, ARTICULOS_NOMBRE);
	}

	/**
	 * Set the value of the articulos.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosNombre(String newValue) throws DataStoreException {
		setString(ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the articulos.nombre column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcion() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the articulos.descripcion column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcion(int row) throws DataStoreException {
		return getString(row, ARTICULOS_DESCRIPCION);
	}

	/**
	 * Set the value of the articulos.descripcion column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcion(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the articulos.observaciones column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosObservaciones() throws DataStoreException {
		return getString(ARTICULOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the articulos.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosObservaciones(int row) throws DataStoreException {
		return getString(row, ARTICULOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the articulos.observaciones column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosObservaciones(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the articulos.observaciones column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_OBSERVACIONES, newValue);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta() throws DataStoreException {
		return getString(ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Retrieve the value of the articulos.descripcion_completa column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getArticulosDescripcionCompleta(int row)
			throws DataStoreException {
		return getString(row, ARTICULOS_DESCRIPCION_COMPLETA);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(String newValue)
			throws DataStoreException {
		setString(ARTICULOS_DESCRIPCION_COMPLETA, newValue);
	}

	/**
	 * Set the value of the articulos.descripcion_completa column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setArticulosDescripcionCompleta(int row, String newValue)
			throws DataStoreException {
		setString(row, ARTICULOS_DESCRIPCION_COMPLETA, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	public void setArticuloUnidadMedida(int row) throws DataStoreException,
			SQLException {
		setString(row, ARTICULOS_UNIDAD_MEDIDA, UnidadesMedidaModel
				.getUnidadMedidaNombre(Integer.parseInt(AtributosEntidadModel
						.getValorAtributoObjeto(ARTICULO_UNIDAD_MEDIDA,
								getConversionesArticuloId(), "TABLA",
								"articulos"))));
	}

	public int getArticuloUnidadMedida(int row) throws DataStoreException {
		return getInt(row, ARTICULO_UNIDAD_MEDIDA);
	}

	@Override
	public void update(DBConnection conn, boolean handleTrans)
			throws DataStoreException, SQLException {
		// TODO Auto-generated method stub
		ArticulosModel articulos;
		// fills detalle_sc.articulo_id field through ArticulosNombre
		if (getArticulosNombre() != null) {
			articulos = new ArticulosModel("inventario", "inventario");
			articulos.retrieve(conn, "articulos.nombre LIKE '"
					+ getArticulosNombre() + "'");
			if (!articulos.gotoFirst()) {
				DataStoreException ex = new DataStoreException(
						"El c�digo de articulo ingresado no corresponde a ninguno registrado");
				throw ex;
			}
			;
			setConversionesArticuloId(articulos.getArticulosArticuloId());
		}
		super.update(conn, handleTrans);
	}

	/**
	 * Devuelve la cantidad convertida a la unidad patr�n del art�culo para la
	 * unidad de medida especificada. Crea una nueva DBConnection
	 * 
	 * @param articuloId
	 *            El Id del art�culo
	 * @param unidadMedidaId
	 *            El Id de la unidad de medida cuyo factor de conversi�n debe
	 *            estar indicado en la tabla de conversiones
	 * @param cantidad
	 *            La cantidad a convertir
	 * 
	 * @return valor double indicando la cantidad convertida
	 * @throws SQLException
	 *             Si ocurre alg�n error en la ejecuci�n del SQL
	 * @throws DataStoreException
	 *             Si ocurre alg�n error en la recuperaci�n del valor del
	 *             atributo ARTICULO_UNIDAD_MEDIDA o si el factor de conversi�n
	 *             no est� indicado en la tabla de conversiones
	 */
	public static double getUnidadConvertida(int articuloId,
			int unidadMedidaId, double cantidad) throws SQLException,
			DataStoreException {
		DBConnection conn = DBConnection.getConnection("inventario");
		return getUnidadConvertida(articuloId, unidadMedidaId, cantidad, conn);
	}

	/**
	 * Devuelve la cantidad convertida a la unidad patr�n del art�culo para la
	 * unidad de medida especificada. Utiliza la DBConnection indicada como
	 * par�metro
	 * 
	 * @param articuloId
	 *            El Id del art�culo
	 * @param unidadMedidaId
	 *            El Id de la unidad de medida cuyo factor de conversi�n debe
	 *            estar indicado en la tabla de conversiones
	 * @param cantidad
	 *            La cantidad a convertir
	 * @param conn
	 *            La DBConnection a utilizar
	 * @return valor double indicando la cantidad convertida
	 * @throws SQLException
	 *             Si ocurre alg�n error en la ejecuci�n del SQL
	 * @throws DataStoreException
	 *             Si ocurre alg�n error en la recuperaci�n del valor del
	 *             atributo ARTICULO_UNIDAD_MEDIDA o si el factor de conversi�n
	 *             no est� indicado en la tabla de conversiones
	 */
	public static double getUnidadConvertida(int articuloId,
			int unidadMedidaId, double cantidad, DBConnection conn)
			throws SQLException, DataStoreException {
		if (Integer.parseInt(AtributosEntidadModel.getValorAtributoObjeto(
				ARTICULO_UNIDAD_MEDIDA, articuloId, "TABLA", "articulos")) == unidadMedidaId)
			return cantidad;
		Statement st = conn.createStatement();
		String sql = "SELECT c.factor FROM inventario.conversiones c WHERE c.articulo_id = "
				+ articuloId + " AND c.unidad_medida_id =" + unidadMedidaId;
		ResultSet rs = st.executeQuery(sql);
		if (rs.first())
			return rs.getDouble(1) * cantidad;
		else
			throw new DataStoreException(
					"No ha indicado el factor de conversi�n para la unidad de medida indicada para el art�culo. Dirijase a la pantalla Configuraci�n --> Tabla de Conversiones para registrarlo.");
	}
	// $ENDCUSTOMMETHODS$

}
