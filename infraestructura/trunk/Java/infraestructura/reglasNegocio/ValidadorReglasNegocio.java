/**
 *
 */
package infraestructura.reglasNegocio;

import com.salmonllc.sql.DBConnection;

/**
 * @author Demian
 *
 * Formato general para construir reglas de validación / negocio
 * para ejecutar dinámicamente en la aplicación
 */
public abstract class ValidadorReglasNegocio {
	
	// Id del usuario 
	private int userId;
	// Host remoto del usuario
	private String remoteHost;

	public ValidadorReglasNegocio(){

	}

	/**
	 * @param obj --> objeto a validar.
	 * @param msg --> Mensaje a retornat si el obj es inválido
	 * @param conn --> conexión, si existe donde se enmarca esta transacción, sino null
	 * @return Verdadero si el objeto es válido,
	 * 		   	sino falso y el mensaje de error correspondiente.
	 * Quien implementa el método DEBE saber de que tipo es objeto para validarlo
	 */
	public abstract boolean esValido(Object obj,StringBuilder msg,DBConnection conn) throws ValidationException;

	/**
	 * @param userId el id del usuario a setear
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return el id del usuario
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param remoteHost el host remoto a setear
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/**
	 * @return el host remoto
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

}
