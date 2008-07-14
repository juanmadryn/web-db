package infraestructura.reglasNegocio;

import java.util.Vector;

public class ValidationException extends RuntimeException {
	private Vector<String> stackErrores;
	/**
	 *
	 */
	private static final long serialVersionUID = 3631693394100855853L;

	public ValidationException() {
		// TODO Auto-generated constructor stub
	}

	public ValidationException(Vector<String> vector) {
		// TODO Auto-generated constructor stub
		setStackErrores(vector);
	}

	public ValidationException(String arg0, Vector<String> vector) {
		super(arg0);
		setStackErrores(vector);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(Throwable arg0, Vector<String> vector) {
		super(arg0);
		setStackErrores(vector);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String arg0, Throwable arg1, Vector<String> vector) {
		super(arg0, arg1);
		setStackErrores(vector);
		// TODO Auto-generated constructor stub
	}

	public Vector<String> getStackErrores() {
		return stackErrores;
	}

	public void setStackErrores(Vector<String> mensajeError) {
		this.stackErrores = mensajeError;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		String mensaje = "";
		for(int i = 0; i < stackErrores.size(); i++) {
			 mensaje += stackErrores.elementAt(i);
			 mensaje += i < stackErrores.size() ? " - ":"";
		}
		
		return mensaje;
	}

}
