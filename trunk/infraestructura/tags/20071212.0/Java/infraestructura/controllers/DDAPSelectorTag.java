package infraestructura.controllers;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseEmptyTag;


public class DDAPSelectorTag extends BaseEmptyTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505425298056095670L;

	public HtmlComponent createComponent() {
        return new DDAPSelector(getName(),getHelper().getController());
	}

}
