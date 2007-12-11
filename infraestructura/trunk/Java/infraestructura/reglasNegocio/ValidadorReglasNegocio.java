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
	public abstract boolean esValido(Object obj,StringBuilder msg,DBConnection conn);

}
