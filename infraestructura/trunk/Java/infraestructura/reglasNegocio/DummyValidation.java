package infraestructura.reglasNegocio;

import com.salmonllc.sql.DBConnection;

public class DummyValidation extends ValidadorReglasNegocio {

	@Override
	public boolean esValido(Object obj, StringBuffer msg, DBConnection conn) {
		msg.append("DummyValidation se ejecutó para este parte");
		return false;
	}

}
