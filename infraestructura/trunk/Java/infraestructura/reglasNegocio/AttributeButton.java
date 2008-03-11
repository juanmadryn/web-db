package infraestructura.reglasNegocio;

import com.salmonllc.html.HtmlSubmitButton;

public class AttributeButton {
	private HtmlSubmitButton button;
	private String label;
	private int index;
	
	public AttributeButton(HtmlSubmitButton button, String label, int index) {
		super();
		this.button = button;
		this.label = label;
		this.index = index;
	}

	public HtmlSubmitButton getButton() {
		return button;
	}

	public void setButton(HtmlSubmitButton button) {
		this.button = button;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
