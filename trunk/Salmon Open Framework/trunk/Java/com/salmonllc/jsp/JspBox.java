package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspBox.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 12/19/03 1:07p $
/////////////////////////
import com.salmonllc.html.HtmlPage;
import com.salmonllc.properties.Props;

/**
 * This container draws a box around the components inside
 */

public class JspBox extends JspContainer {
	private String _lineWidth;
	private String _bgColor;
	private String _lineColor;
	private String _theme;
	private String _width;
	private String _margin;
	private String _height;


	/**
	 * Creates a new JSP Box
	 */
	public JspBox(String name, HtmlPage p) {
		this(name, p,null);
	}
	/**
	 * Creates a new JSP Box with the specified theme
	 */
	public JspBox(String name, HtmlPage p, String theme) {
		super(name, p);
		setTheme(theme);
	}
	/**
	 *Generates the Html for the component. This method is called by the framework and should not be called directly
	 */
	public void generateHTML(TagWriter t, String box) throws java.io.IOException {
		StringBuffer sb = new StringBuffer();

		if (_lineColor.toUpperCase().equals("NONE"))
			sb.append("<table border=\"0\"");
		else {
			sb.append("<table border=\"0\" bgcolor=\"");
			sb.append(_lineColor);
		}
		if (_width != null){
			sb.append("\" width=\"");
			sb.append(_width);
		}
		if (_height != null){
			sb.append("\" height=\"");
			sb.append(_height);
		}        
		sb.append("\" cellpadding=\"");
		sb.append(_lineWidth);
		sb.append("\" cellspacing=\"0\"><tr><td>");
		sb.append("<table height=\"100%\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"");
		sb.append(_margin);
		sb.append("\">");
		if (_bgColor.toUpperCase().equals("NONE"))
			sb.append("<tr>");
		else {
			sb.append("<tr bgcolor=\"");
			sb.append(_bgColor);
			sb.append("\">");
		}
		sb.append("<td>");


		t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
		t.print(box, TagWriter.TYPE_CONTENT);
		t.print("</td></tr></table></td></tr></table>", TagWriter.TYPE_END_TAG);

	}
	/**
	 * Returns the background color of the box contents
	 */

	public String getBgColor() {
		return _bgColor;
	}
	/**
	 * Returns the line color for the line surrounding the box
	 */

	public String getLineColor() {
		return _lineColor;
	}
	/**
	 * Returns the width of the line of the box in pixels
	 */
	public String getLineWidth() {
		return _lineWidth;
	}
	/**
	 * Returns the gap in pixels between the border line and the contents of the box
	 */

	public String getMargin() {
		return _margin;
	}
	/**
	 * This method returns the property theme for the component.
	 * @param theme The theme to use.
	 */
	public String getTheme() {
		return _theme;
	}
	/**
	 * Returns the width of the table
	 */
	public String getWidth() {
		return _width;
	}
	/**
	 * Sets the background color of the box contents
	 */

	public void setBgColor(String bgColor) {
		_bgColor = bgColor;
	}
	/**
	 * Sets the line color for the line surrounding the box
	 */

	public void setLineColor(String lineColor) {
		_lineColor = lineColor;
	}
	/**
	 * Sets the width of the line of the box in pixels
	 */

	public void setLineWidth(String lineWidth) {
		_lineWidth = lineWidth;
	}
	/**
	 * Sets the gap in pixels between the border line and the contents of the box
	 */
	public void setMargin(String borderSpacing) {
		_margin = borderSpacing;
	}
	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {
		Props prop = getPage().getPageProperties();

		_lineWidth = prop.getThemeProperty(theme,Props.BOX_LINE_WIDTH);
		if (_lineWidth == null)
			_lineWidth = "1";
		_margin = prop.getThemeProperty(theme,Props.BOX_MARGIN);
		if (_margin == null)
			_margin = "1";

		_lineColor = prop.getThemeProperty(theme,Props.BOX_LINE_COLOR);
		if (_lineColor == null)
			_lineColor = "black";

		_bgColor = prop.getThemeProperty(theme,Props.BOX_BG_COLOR);
		if (_bgColor == null)
			_bgColor = "white";

		_theme = theme;
	}
	 /**
	 * Sets the width of the table
	 */
	public void setWidth(String width) {
	   _width = width;
	 }
	/**
	 * @return the height of the table
	 */
	public String getHeight() {
		return _height;
	}

	/**
	* Sets the height of the table
	*/
	public void setHeight(String string) {
		_height = string;
	}

}
