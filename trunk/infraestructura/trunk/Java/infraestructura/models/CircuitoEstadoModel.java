package infraestructura.models;

import infraestructura.reglasNegocio.ConvierteMayusculasValidation;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * CircuitoEstadoModel: A SOFIA generated model
 */
public class CircuitoEstadoModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8140673082781802933L;
	// constants for columns
	public static final String CIRCUITOS_ESTADOS_CIRCUITO = "circuitos_estados.circuito";
	public static final String CIRCUITOS_ESTADOS_NOMBRE = "circuitos_estados.nombre";
	public static final String CIRCUITOS_ESTADOS_DESCRIPCION = "circuitos_estados.descripcion";
	public static final String CIRCUITOS_ESTADOS_OBSERVACIONES = "circuitos_estados.observaciones";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated

	// $ENDCUSTOMVARS$

	/**
	 * Create a new CircuitoEstadoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public CircuitoEstadoModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new CircuitoEstadoModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public CircuitoEstadoModel(String appName, String profile) {
		super(appName, profile);

		try {
			// add columns
			addColumn(computeTableName("circuitos_estados"), "circuito",
					DataStore.DATATYPE_STRING, true, true,
					CIRCUITOS_ESTADOS_CIRCUITO);
			addColumn(computeTableName("circuitos_estados"), "nombre",
					DataStore.DATATYPE_STRING, false, true,
					CIRCUITOS_ESTADOS_NOMBRE);
			addColumn(computeTableName("circuitos_estados"), "descripcion",
					DataStore.DATATYPE_STRING, false, true,
					CIRCUITOS_ESTADOS_DESCRIPCION);
			addColumn(computeTableName("circuitos_estados"), "observaciones",
					DataStore.DATATYPE_STRING, false, true,
					CIRCUITOS_ESTADOS_OBSERVACIONES);

			// add validations
			addExpressionRule(CIRCUITOS_ESTADOS_CIRCUITO,
					new ConvierteMayusculasValidation(
							CIRCUITOS_ESTADOS_CIRCUITO), "", false);
			addRequiredRule(CIRCUITOS_ESTADOS_CIRCUITO,
					"El código de servicio es obligatorio");
			addExpressionRule(
					CIRCUITOS_ESTADOS_NOMBRE,
					new ConvierteMayusculasValidation(CIRCUITOS_ESTADOS_NOMBRE),
					"", false);
			addRequiredRule(CIRCUITOS_ESTADOS_NOMBRE,
					"El nombre de servicio es obligatorio");
		} catch (DataStoreException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		// $CUSTOMCONSTRUCTOR$
		// Put custom constructor code between these comments, otherwise it be
		// overwritten if the model is regenerated
		
		// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the circuitos_estados.circuito column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosCircuito() throws DataStoreException {
		return getString(CIRCUITOS_ESTADOS_CIRCUITO);
	}

	/**
	 * Retrieve the value of the circuitos_estados.circuito column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosCircuito(int row)
			throws DataStoreException {
		return getString(row, CIRCUITOS_ESTADOS_CIRCUITO);
	}

	/**
	 * Set the value of the circuitos_estados.circuito column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosCircuito(String newValue)
			throws DataStoreException {
		setString(CIRCUITOS_ESTADOS_CIRCUITO, newValue);
	}

	/**
	 * Set the value of the circuitos_estados.circuito column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosCircuito(int row, String newValue)
			throws DataStoreException {
		setString(row, CIRCUITOS_ESTADOS_CIRCUITO, newValue);
	}

	/**
	 * Retrieve the value of the circuitos_estados.nombre column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosNombre() throws DataStoreException {
		return getString(CIRCUITOS_ESTADOS_NOMBRE);
	}

	/**
	 * Retrieve the value of the circuitos_estados.nombre column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosNombre(int row) throws DataStoreException {
		return getString(row, CIRCUITOS_ESTADOS_NOMBRE);
	}

	/**
	 * Set the value of the circuitos_estados.nombre column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosNombre(String newValue)
			throws DataStoreException {
		setString(CIRCUITOS_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Set the value of the circuitos_estados.nombre column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosNombre(int row, String newValue)
			throws DataStoreException {
		setString(row, CIRCUITOS_ESTADOS_NOMBRE, newValue);
	}

	/**
	 * Retrieve the value of the circuitos_estados.descripcion column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosDescripcion() throws DataStoreException {
		return getString(CIRCUITOS_ESTADOS_DESCRIPCION);
	}

	/**
	 * Retrieve the value of the circuitos_estados.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosDescripcion(int row)
			throws DataStoreException {
		return getString(row, CIRCUITOS_ESTADOS_DESCRIPCION);
	}

	/**
	 * Set the value of the circuitos_estados.descripcion column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosDescripcion(String newValue)
			throws DataStoreException {
		setString(CIRCUITOS_ESTADOS_DESCRIPCION, newValue);
	}

	/**
	 * Set the value of the circuitos_estados.descripcion column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosDescripcion(int row, String newValue)
			throws DataStoreException {
		setString(row, CIRCUITOS_ESTADOS_DESCRIPCION, newValue);
	}

	/**
	 * Retrieve the value of the circuitos_estados.observaciones column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosObservaciones() throws DataStoreException {
		return getString(CIRCUITOS_ESTADOS_OBSERVACIONES);
	}

	/**
	 * Retrieve the value of the circuitos_estados.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getCircuitosEstadosObservaciones(int row)
			throws DataStoreException {
		return getString(row, CIRCUITOS_ESTADOS_OBSERVACIONES);
	}

	/**
	 * Set the value of the circuitos_estados.observaciones column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosObservaciones(String newValue)
			throws DataStoreException {
		setString(CIRCUITOS_ESTADOS_OBSERVACIONES, newValue);
	}

	/**
	 * Set the value of the circuitos_estados.observaciones column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setCircuitosEstadosObservaciones(int row, String newValue)
			throws DataStoreException {
		setString(row, CIRCUITOS_ESTADOS_OBSERVACIONES, newValue);
	}

	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated

	// $ENDCUSTOMMETHODS$

}
