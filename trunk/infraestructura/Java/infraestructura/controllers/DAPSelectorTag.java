package infraestructura.controllers;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.tags.BaseEmptyTag;


public class DAPSelectorTag extends BaseEmptyTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6226486739081372356L;

	public HtmlComponent createComponent() {
        return new DAPSelector(getName(),getHelper().getController());
	}

}
