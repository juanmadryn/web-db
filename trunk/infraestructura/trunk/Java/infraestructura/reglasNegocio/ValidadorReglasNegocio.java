/**
 *
 */
package infraestructura.reglasNegocio;

import com.salmonllc.sql.DBConnection;

/**
 * @author Demian
 *
 * Formato general para construir reglas de validaci�n / negocio
 * para ejecutar din�micamente en la aplicaci�n
 */
public abstract class ValidadorReglasNegocio {

	public ValidadorReglasNegocio(){

	}

	/**
	 * @param obj --> objeto a validar.
	 * @param msg --> Mensaje a retornat si el obj es inv�lido
	 * @param conn --> conexi�n, si existe donde se enmarca esta transacci�n, sino null
	 * @return Verdadero si el objeto es v�lido,
	 * 		   	sino falso y el mensaje de error correspondiente.
	 * Quien implementa el m�todo DEBE saber de que tipo es objeto para validarlo
	 */
	public abstract boolean esValido(Object obj,StringBuilder msg,DBConnection conn);

}
