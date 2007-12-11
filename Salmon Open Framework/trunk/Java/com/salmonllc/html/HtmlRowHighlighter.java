/*
 * Created on Oct 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.salmonllc.html;

import java.io.PrintWriter;
import java.util.Hashtable;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * @author Dan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HtmlRowHighlighter extends HtmlFormComponent {

	private String _highlightColor = "yellow";
	private boolean _multiple = true;
	private String _trueValue = "1";
	private String _falseValue = "0";
	private int _lastPrintedRow = -1;
	private int _lastPrintedTR = -1;

	/**
	 * @param name
	 * @param p
	 */
	public HtmlRowHighlighter(String name, HtmlPage p) {
		super(name, p);
		setTheme(null);
	}

	public void resetForGenerate() {
		_lastPrintedRow = -1;
		_lastPrintedTR = -1;
	}
	public HtmlComponent getParentDataTable() {
		HtmlComponent parent = getParent();
		while (parent != null) {
			if (parent instanceof JspDataTable)
				return parent;
			if (parent instanceof HtmlDataTable)
				return parent;
			parent = parent.getParent();
		}
		return null;
	}
	public String generateJavaScriptForTrOnClick() {
		if (!getVisible())
			return "";
		else
			return "doSelect=true;";
	}

	public String generateJavaScriptForTrOnMouseDown(int row, HtmlComponent tr) {
		if (!getVisible())
			return "";
		else {
			HtmlComponent comp = getParentDataTable();
			String formSt = getFormString();
			formSt = formSt.substring(0, formSt.length() - 1);
			int rowSt = -1;
			;
			int rowEnd = -1;
			String name = comp.getFullName();
			if (comp != null && comp instanceof JspDataTable) {
				JspDataTable tab = (JspDataTable) comp;
				rowSt = tab.getFirstRowOnPage();
				rowEnd = tab.getRowsPerPage() + rowSt - 1;

				if (tab != null) {
					DataStoreBuffer dsb = tab.getDataStoreBuffer();
					if (dsb != null && rowEnd >= dsb.getRowCount())
						rowEnd = tab.getDataStoreBuffer().getRowCount() - 1;
				}
			} else if (comp != null && comp instanceof HtmlDataTable) {
				HtmlDataTable tab = (HtmlDataTable) comp;
				rowSt = tab.getFirstRowOnPage();
				rowEnd = tab.getRowsPerPage() + rowSt - 1;
				if (tab != null) {
					DataStoreBuffer dsb = tab.getDataStoreBuffer();
					if (dsb != null && rowEnd >= dsb.getRowCount())
						rowEnd = tab.getDataStoreBuffer().getRowCount() - 1;
				}
			}

			String st = "doSelect=false;";
			boolean addToScript = true;
			if (tr != null) {
				addToScript = false;
				Object parent = tr.getParent();
				if (parent instanceof JspContainer)
					if (((JspContainer) parent).getComponentType(tr) == JspContainer.TYPE_ROW)
						addToScript = true;
			}
			if (addToScript)
				st += "return selectRows(" + formSt + ",'" + name + "'," + row + "," + rowSt + "," + rowEnd + ",event,'" + _trueValue + "','" + _falseValue + "','" + _highlightColor + "'," + !_multiple + ");";
			return st;
		}
	}

	public String generateHtmlForTd(int rowNo, int trNo, String bgColor) {
		if (!getVisible())
			return "";

		if (rowNo <= _lastPrintedRow && trNo <= _lastPrintedTR)
			return "";
		else {
			HtmlComponent comp = getParentDataTable();
			String name = getFullName() + "r" + trNo + "_color";
			if (comp != null)
				name = comp.getFullName() + "r" + trNo + "_color";
			if (rowNo > -1)
				name += "_" + rowNo;
			_lastPrintedRow = rowNo;
			_lastPrintedTR = trNo;
			return ("<INPUT TYPE=\"HIDDEN\" name=\"" + name + "\" value=\"" + bgColor + "\">");
		}
	}

	public String getBgColorForRow(int rowNo, String defColor, HtmlComponent comp) {
		if (comp != null) {
			Object parent = comp.getParent();
			if (parent instanceof JspContainer) {
				if (((JspContainer) parent).getComponentType(comp) != JspContainer.TYPE_ROW)
					return defColor;
			}
		}

		String value = getValue(rowNo);
		if (valuesEqual(value, _trueValue))
			return _highlightColor;
		else
			return defColor;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlComponent#generateHTML(java.io.PrintWriter,
	 *      int)
	 */
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		HtmlScriptGenerator gen = new HtmlScriptGenerator(getPage());
		gen.generateSelectRowScript();
		HtmlComponent comp = getParentDataTable();
		String name = getFullName();
		if (comp != null)
			name = comp.getFullName() + "_select";

		if (rowNo > -1)
			name += "_" + rowNo;

		if (!getVisible())
			return;

		_value = getValue(rowNo);

		String val = _value == null ? _falseValue : _value;

		String out = "<INPUT TYPE=\"HIDDEN\" name=\"" + name + "\" value=\"" + val + "\">";

		p.println(out);
	}
	/**
	 * @return Returns the falseValue.
	 */
	public String getFalseValue() {
		return _falseValue;
	}
	/**
	 * @param falseValue
	 *            The falseValue to set.
	 */
	public void setFalseValue(String falseValue) {
		_falseValue = falseValue;
	}
	/**
	 * @return Returns the highlightColor.
	 */
	public String getHighlightColor() {
		return _highlightColor;
	}
	/**
	 * @param highlightColor
	 *            The highlightColor to set.
	 */
	public void setHighlightColor(String highlightColor) {
		_highlightColor = highlightColor;
	}
	/**
	 * @return Returns the multiple.
	 */
	public boolean isMultiple() {
		return _multiple;
	}
	/**
	 * @param multiple
	 *            The multiple to set.
	 */
	public void setMultiple(boolean multiple) {
		_multiple = multiple;
	}
	/**
	 * @return Returns the trueValue.
	 */
	public String getTrueValue() {
		return _trueValue;
	}
	/**
	 * @param trueValue
	 *            The trueValue to set.
	 */
	public void setTrueValue(String trueValue) {
		_trueValue = trueValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salmonllc.html.HtmlComponent#setTheme(java.lang.String)
	 */
	public void setTheme(String theme) {
		_highlightColor = getPage().getPageProperties().getThemeProperty(theme, Props.LIST_FORM_DISPLAY_BOX_ROW_HIGHLIGHT_COLOR, "yellow");
		super.setTheme(theme);
	}
	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		Object oldValue = _value;

		HtmlComponent comp = getParentDataTable();
		String name = getFullName();
		if (comp != null)
			name = comp.getFullName() + "_select";

		if (rowNo > -1) {
			name += "_" + rowNo;
			if (_dsBuff != null) {
				try {
					oldValue = _dsBuff.getAny(rowNo, _dsColNo);
				} catch (Exception e) {
					oldValue = null;
				}
			}
		} else {
			if (_dsBuff != null) {
				try {
					oldValue = _dsBuff.getAny(_dsColNo);
				} catch (Exception e) {
					oldValue = null;
				}
			}
		}

		String val[] = (String[]) parms.get(name);

		if (val != null)
			_value = val[0];
		else
			_value = null;

		if (_value != null && _value.trim().length() == 0)
			_value = null;

		if (!valuesEqual(oldValue, _value)) {
			String s = null;
			if (oldValue != null)
				s = oldValue.toString();
			ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
			addEvent(e);
		}
		return false;
	}
}