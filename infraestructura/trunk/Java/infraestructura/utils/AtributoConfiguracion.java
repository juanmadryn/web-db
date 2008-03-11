package infraestructura.utils;

public class AtributoConfiguracion {
	int atributo_id;
	String atributo_valor;
	
	public AtributoConfiguracion() {
		super();
		this.atributo_id = 0;
		this.atributo_valor = null;
	}
	
	public AtributoConfiguracion(int atributo_id, String atributo_valor) {
		super();
		this.atributo_id = atributo_id;
		this.atributo_valor = atributo_valor;
	}

	public int getAtributo_id() {
		return atributo_id;
	}

	public void setAtributo_id(int atributo_id) {
		this.atributo_id = atributo_id;
	}

	public String getAtributo_valor() {
		return atributo_valor;
	}

	public void setAtributo_valor(String atributo_valor) {
		this.atributo_valor = atributo_valor;
	}
}