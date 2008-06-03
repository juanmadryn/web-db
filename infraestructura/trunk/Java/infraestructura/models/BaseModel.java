package infraestructura.models;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.reglasNegocio.ValidationException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

public abstract class BaseModel extends DataStore {
	/**
	 * Create a new ProyectoModel object.
	 * 
	 * @param appName The SOFIA application name
	 */
	public BaseModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ProyectoModel object.
	 * 
	 * @param appName The SOFIA application name
	 * @param profile The database profile to use
	 */
	public BaseModel(String appName, String profile) {
		super(appName, profile);
	}

	/**
	 * Ejecuta la acción dada para el informe de devolución. Concentra TODAS las
	 * acciones posibles para un informe de devolición
	 * 
	 * @param row
	 *            número de fila sobre la cual se desea hacer la operacion
	 * @param accion
	 *            accion grabada en la tabla de tarnsición de estados
	 * @param circuito
	 *            circuito al cual pertenece la acción. Permite recuperar la
	 *            columna de estado
	 * @param pagina
	 *            Página de la cual se realiza la operación
	 */
	public void ejecutaAccion(int row, int accion, String circuito,
			String remoteAddr, int userId, String nombre_tabla,
			DBConnection conn, boolean batchInserts) throws DataStoreException, SQLException, ValidationException {
		if (!gotoRow(row)) {
			throw new DataStoreException("Fila " + row + " fuera de contexto");
		}
		ejecutaAccion(accion, circuito, remoteAddr, userId, nombre_tabla, conn,
				batchInserts);
	}

	/**
	 * Ejecuta la acción dada para el registro actual. Concentra TODAS las
	 * acciones posibles para un registro
	 * 
	 * @param accion accion grabada en la tabla de tarnsición de estados
	 * @param circuito circuito al cual pertenece la acción. Permite recuperar la
	 *            columna de estado
	 * @param pagina Página de la cual se realiza la operación
	 */
	public void ejecutaAccion(int accion, String circuito, String remoteAddr,
			int userId, String nombre_tabla, DBConnection conn,
			boolean batchInserts) throws DataStoreException, SQLException,
			ValidationException {
		String estado_actual = null;
		String proximo_estado = null;
		String nombre_accion = null;
		String columna_circuito = null;
		String validador = null;
		StringBuilder resultado;
		boolean ok = false;
		
		// verifico si está conectado un usuario		
		if (userId == 0) {
			throw new DataStoreException(
					"Debe estar conectado como un usuario de la aplicación...");
		}
		
		// chequeo que el registro este en el contexto correspondiente
		if (getRow() == -1) {
			throw new DataStoreException("No hay ningún registro seleccionado");
		}

		// recupero toda la información requerida para evaluar la acción
		// correspondiente
		// y ejecutarla
		try {
			
			AplicaCircuitoModel aplicaCircuito = new AplicaCircuitoModel(
					"infraestructura", "infraestructura");
			aplicaCircuito.retrieve("aplica_circuito.circuito = '" + circuito
					+ "'");		
			if (aplicaCircuito.gotoFirst()) {
				columna_circuito = aplicaCircuito
						.getAplicaCircuitoNombreDetalle();
			} else {
				return;
			}

			estado_actual = getEstadoActual();
		
			// recupero el próximo estado y el nombre de la acción en función de
			// la acción
			TransicionEstadoModel transicion = new TransicionEstadoModel(
					"infraestructura", "infraestructura");
			transicion.retrieve("estados_origen.circuito = '" + circuito
					+ "' and estado_origen = '" + estado_actual
					+ "' and transicion_estados.accion = "
					+ Integer.toString(accion));

			System.out.println(nombre_tabla + " " +transicion.getRowCount());
			if (transicion.gotoFirst()) {
				proximo_estado = transicion.getTransicionEstadosEstadoDestino();
				nombre_accion = transicion.getTransicionEstadosPromptAccion();
				validador = transicion.getTransicionEstadosValidador();
			}
		
			// Verifica rutina de validación dinámica
			try {
				if (validador != null && validador.length() > 0
						&& !validador.equalsIgnoreCase("No Validar")) {
					Class<?> claseVal = Class.forName(validador);
					ValidadorReglasNegocio val = (ValidadorReglasNegocio) claseVal
							.newInstance();

					// Para ejecutar acciones desde la regla de negocio 
					val.setRemoteHost(remoteAddr);
					val.setUserId(userId);

					resultado = new StringBuilder("");
					if (val.esValido(this, resultado, conn)) {
						ok = true;
					} else {
						ok = false;
						throw new DataStoreException(resultado.toString());
					}
				} else if (validador == null || validador.length() == 0) {
					throw new DataStoreException(
							nombre_accion
									+ " -- No tiene implementada Validación. Se requiere especificar Validación");
				} else if (validador.equalsIgnoreCase("No Validar")) {
					// La regla NO requiere de validación
					ok = true;
				} else {
					throw new DataStoreException(nombre_accion
							+ " -- Situación no prevista");
				}
			} catch (ClassNotFoundException e) {
				throw new DataStoreException(e.getMessage());
			} catch (InstantiationException e) {
				throw new DataStoreException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new DataStoreException(e.getMessage());
			}

			// si hay cambio de estado al finalizar, independientemente de la
			// acción paso al próximo
			// estado en el registro y actualizo
			// Se inserta también el registro de auditoría correspondiente sólo
			// si cambió estado			
			
			if (ok && !estado_actual.equalsIgnoreCase(proximo_estado)) {

				setString(nombre_tabla + "." + columna_circuito, proximo_estado);
				update(conn);

				AuditaEstadosCircuitosModel auditoriaModel = new AuditaEstadosCircuitosModel(
						"infraestructura", "infraestructura");

				auditoriaModel.setBatchInserts(batchInserts);

				auditoriaModel.gotoRow(auditoriaModel.insertRow());
				auditoriaModel.setAuditaEstadosCircuitosCircuito(circuito);
				auditoriaModel.setAuditaEstadosCircuitosFecha(new Timestamp(
						(new Date()).getTime()));
				auditoriaModel.setAuditaEstadosCircuitosAEstado(proximo_estado);
				auditoriaModel.setAuditaEstadosCircuitosDeEstado(estado_actual);
				auditoriaModel.setAuditaEstadosCircuitosUserId(userId);
				auditoriaModel
						.setAuditaEstadosCircuitosNombreTabla(getAppName()
								+ "." + nombre_tabla);
				auditoriaModel
						.setAuditaEstadosCircuitosRegistroId(getIdRegistro());
				auditoriaModel.setAuditaEstadosCircuitosHost(remoteAddr);
				auditoriaModel.setAuditaEstadosCircuitosAccion(accion);

				auditoriaModel.update(conn);
			}
			if (!ok)
				throw new DataStoreException("Falló la validación del registro");

		} catch (DataStoreException e) {
			throw new DataStoreException(e.getMessage());
		} catch (SQLException e) {
			throw new DataStoreException(nombre_accion + " -- "
					+ e.getMessage());
		}
	}

	/**
	 * Se debe definir en cada Model el estado del registro actual
	 * 
	 * @return Nombre del estado
	 * @throws DataStoreException
	 */
	public abstract String getEstadoActual() throws DataStoreException;

	/**
	 * Se debe definir en cada Model qué valor representa el Id del registro
	 * 
	 * @return el Id del regsitro actual
	 */
	public abstract int getIdRegistro() throws DataStoreException;

}
