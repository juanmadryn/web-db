//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.jsp;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.salmonllc.html.*;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

/**
 * An extended version of the display box with addional functionality for building search, list and detail forms. This is the base class for the specific form components
 *
 */
public abstract class JspFormDisplayBox extends JspDisplayBox {
	public static final int BUTTON_DISPLAY_IN_HEADER = 0;
	public static final int BUTTON_DISPLAY_BELOW_TABLE = 1;
	public static final int BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE = 2;
	public static final String ALIGN_LEFT=HtmlTable.ALIGN_LEFT;
	public static final String ALIGN_CENTER=HtmlTable.ALIGN_CENTER;
	public static final String ALIGN_RIGHT=HtmlTable.ALIGN_RIGHT;
	protected int _buttonDisplayLocation;
	protected ArrayList _buttons = new ArrayList();
	protected ArrayList _messageButtons = new ArrayList();
	protected HtmlValidatorText _validator;
	protected boolean _validatorBuiltInternally = false;
	protected boolean _updateLocale = true;
	protected String _buttonBgColor;
	protected String _buttonFontStyle;
	protected String _okButtonCap, _cancelButtonCap, _undoButtonCap;
	protected String _bottomButtonAlign;
    protected ArrayList _hidden = new ArrayList();

	public JspFormDisplayBox(String name, String theme, HtmlPage p) {
		super(name, theme, p);
	}

	protected class MessageButton extends HtmlSubmitButton {
		public MessageButton(HtmlPage p, String name, String cap, SubmitListener listener) {
			super(name, cap, p);
			this.setButtonBgColor(_buttonBgColor);
			this.setButtonFontStyle(_buttonFontStyle);
			if (listener != null)
				addSubmitListener(listener);
		}
	}

	/**
	 *Generates the Html for the component. This method is called by the framework and should not be called directly
	 */
	public void generateHTML(TagWriter t, String boxBody) throws Exception {
		processLocaleInfo();
		StringBuffer sb = new StringBuffer();

		int cellPadding = getCellPadding() > -1 ? getCellPadding() : 0;
		int cellSpacing = getCellSpacing() > -1 ? getCellSpacing() : 0;
		//do the heading
		generateValidatorHtml(sb);
        generateConfirmHtml(t, sb);

		sb.append("<TABLE BORDER=\"" + getBorder() + "\" CELLSPACING=\"" + cellSpacing + "\" CELLPADDING=\"" + cellPadding + "\"");
		if (getWidth() != null)
			sb.append(" WIDTH=\"" + getWidth() + "\"");
		sb.append(">");

		if (isHeadingSuppressed() == false) {
			String headingColor = "";
			if (getHeadingBackgroundColor() != null)
				headingColor = " BGCOLOR=\"" + getHeadingBackgroundColor() + "\"";
			String headingStyle = "";
			if (getHeadingClassname() != null)
				headingStyle = " class=\"" + getHeadingClassname() + "\"";

			sb.append("<TR" + headingStyle + "><TD" + headingColor + "><TABLE WIDTH=\"100%\" CELLSPACING=\"" + getInnerCellSpacing() + "\" CELLPADDING=\"" + getInnerCellPadding() + "\">");
			sb.append("<TR" + headingStyle + "><TD" + headingColor + " ALIGN=\"LEFT\">" + _fontStartTag + getHeadingCaption() + _fontEndTag + "</TD><TD" + headingColor + " ALIGN=\"RIGHT\" NOWRAP>");
			if (_buttonDisplayLocation == BUTTON_DISPLAY_IN_HEADER || _buttonDisplayLocation == BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE)
				generateButtonHtml(sb);
			sb.append("</TD></TR></TABLE></TD></TR>");
		}

		String boxColor = "";
		if (getBackgroundColor() != null)
			boxColor = " BGCOLOR=\"" + getBackgroundColor() + "\"";
		String bodyStyle = "";
		if (getBodyClassname() != null)
			bodyStyle = " class=\"" + getBodyClassname() + "\"";
		sb.append("<TR " + bodyStyle + "><TD" + boxColor + ">");

		t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
		sb.setLength(0);

		if (boxBody != null)
			t.print(boxBody, TagWriter.TYPE_CONTENT);

		sb.append("</TD></TR>");
		if (_buttonDisplayLocation == BUTTON_DISPLAY_BELOW_TABLE || _buttonDisplayLocation == BUTTON_DISPLAY_IN_HEADER_AND_BELOW_TABLE || isHeadingSuppressed()) {
			sb.append("<TR><TD" + boxColor);
			if (_bottomButtonAlign != null) {
			 sb.append(" ALIGN=\"");
			 sb.append(_bottomButtonAlign);
			}
			sb.append("\">");
			generateButtonHtml(sb);
			sb.append("</TD></TR>");
		}
		sb.append("</TABLE>");
		sb.append("<a name=\"");
		sb.append(getFullName());
		sb.append("scrollToMe\">");
		t.print(sb.toString(), TagWriter.TYPE_END_TAG);
	}

	private void generateValidatorHtml(StringBuffer sb) throws Exception {
		if (!_validatorBuiltInternally)
			return;
		if (_validator == null)
			return;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(out);
		_validator.generateHTML(pw, -1);
		pw.flush();
		sb.append(out.toString());
		pw.close();
	}

	private void generateButtonHtml(StringBuffer sb) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(out);
		for (int i = 0; i < _buttons.size(); i++) {
			HtmlSubmitButton b = (HtmlSubmitButton) _buttons.get(i);
			b.generateHTML(pw, -1);
		}
		pw.flush();
		sb.append(out.toString());
		pw.close();
	}

    /* Nicolás Genta - 9/27/04 Added for javascript confirm messages */
    private void generateConfirmHtml(TagWriter tw, StringBuffer sb) throws Exception {
        if (tw.getDreamWeaverConv())
            return;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);
        for (int i = 0; i < _hidden.size(); i++) {
            HtmlHiddenField h = (HtmlHiddenField) _hidden.get(i);
            h.setValue("0");
            h.generateHTML(pw, -1);
        }
        pw.flush();
        sb.append(out.toString());
        pw.close();
    }

	/**
	 * Binds various components to the component based on their names passed to the constructor. CriteriaBuilder, CriteriaValidator and ListForm. This method is called by the framework and should not be called directly.
	 */
	public abstract void autoBindComponents() throws Exception;

	protected HtmlFormComponent findFirstFormComponent(JspContainer cont) {
		Enumeration en = cont.getComponents();
		while (en.hasMoreElements()) {
			Object o = en.nextElement();
			if (o instanceof JspContainer) {
				HtmlFormComponent comp = findFirstFormComponent((JspContainer) o);
				if (comp != null)
					return comp;
			} else if (o instanceof HtmlFormComponent)
				return (HtmlFormComponent) o;
		}
		return null;
	}

	protected HtmlCheckBox findFirstUnboundCheckBox(JspContainer cont) {
		Enumeration en = cont.getComponents();
		while (en.hasMoreElements()) {
			Object o = en.nextElement();
			if (o instanceof JspContainer) {
				HtmlCheckBox comp = findFirstUnboundCheckBox((JspContainer) o);
				if (comp != null)
					return comp;
			} else if (o instanceof HtmlCheckBox) {
				HtmlCheckBox cbx = (HtmlCheckBox) o;
				if (cbx.getBoundDataStore() == null)
					return cbx;
			}
		}
		return null;
	}

	protected HtmlText findFirstTextComponent(JspContainer cont) {
		int count = cont.getComponentCount();
		for (int i = 0; i < count; i++) {
			if (cont.getComponentType(i) == JspContainer.TYPE_ROW) {
				Object o = cont.getComponent(i);
				if (o instanceof JspContainer) {
					HtmlText comp = findFirstTextComponent((JspContainer) o);
					if (comp != null)
						return comp;
				} else if (o instanceof HtmlText)
					return (HtmlText) o;
			}
		}
		return null;
	}

	/**
	 * Updates the display box button labels for the current language
	 */
	public void updateLocale() {
		_updateLocale = true;
	}

	/**
	* Sets the theme for the component
	*/
	public void setTheme(String theme) {
		super.setTheme(theme);
		Props props = getPage().getPageProperties();
		_buttonBgColor = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_BUTTON_BG_COLOR);
		_buttonFontStyle = props.getThemeProperty(theme, Props.DETAIL_FORM_DISPLAY_BOX_BUTTON_FONT_STYLE);
		_okButtonCap=props.getThemeProperty(theme,Props.FORM_DISPLAY_BOX_OK_BUTTON_CAPTION);
		_cancelButtonCap=props.getThemeProperty(theme,Props.FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION);
		_undoButtonCap=props.getThemeProperty(theme,Props.FORM_DISPLAY_BOX_UNDO_BUTTON_CAPTION);
	}

	protected void setUpButtons() {
		if (_buttons != null) {
			for (int i = 0; i < _buttons.size(); i++) {
				HtmlSubmitButton b = (HtmlSubmitButton) _buttons.get(i);
				if (_buttonBgColor != null)
					b.setButtonBgColor(_buttonBgColor);
				if (_buttonFontStyle != null);
				b.setButtonFontStyle(_buttonFontStyle);
			}
		}

		if (_messageButtons != null) {
			for (int i = 0; i < _messageButtons.size(); i++) {
				HtmlSubmitButton b = (HtmlSubmitButton) _messageButtons.get(i);
				if (_buttonBgColor != null)
					b.setButtonBgColor(_buttonBgColor);
				if (_buttonFontStyle != null);
				b.setButtonFontStyle(_buttonFontStyle);
			}
		}
	}

	/**
	 * Sets the font style for the search and add buttons
	 */
	public void setButtonFontStyle(String style) {
		for (int i = 0; i < _buttons.size(); i++) {
			HtmlSubmitButton but = (HtmlSubmitButton) _buttons.get(i);
			but.setButtonFontStyle(style);
		}
		for (int i = 0; i < _messageButtons.size(); i++) {
			HtmlSubmitButton but = (HtmlSubmitButton) _messageButtons.get(i);
			but.setButtonFontStyle(style);
		}
	}
	/**
	 * Sets the background color for the search and add buttons
	 */
	public void setButtonBgColor(String bgColor) {
		for (int i = 0; i < _buttons.size(); i++) {
			HtmlSubmitButton but = (HtmlSubmitButton) _buttons.get(i);
			but.setButtonBgColor(bgColor);
		}
		for (int i = 0; i < _messageButtons.size(); i++) {
			HtmlSubmitButton but = (HtmlSubmitButton) _messageButtons.get(i);
			but.setButtonBgColor(bgColor);
		}
	}

	/**
	 * Sets the component and all subcomponents visible or not
	 */
	public void setVisible(boolean vis) {
		boolean butVal[] = new boolean[_buttons.size()];
		for (int i = 0; i < _buttons.size(); i++)
			butVal[i] = ((HtmlSubmitButton) _buttons.get(i)).getVisible();
		super.setVisible(vis);
		for (int i = 0; i < _buttons.size(); i++)
			((HtmlSubmitButton) _buttons.get(i)).setVisible(butVal[i]);
	}

	protected void processLocaleInfo() {
		try {
			LanguagePreferences p = getPage().getLanguagePreferences();

			String descr = null;
			String key = "FormDisplayBox.ok";
			String appName = getPage().getApplicationName();
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_okButtonCap=descr;

			descr = null;
			key = "FormDisplayBox.cancel";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_cancelButtonCap=descr;

			descr = null;
			key = "FormDisplayBox.undo";
			descr = LanguageResourceFinder.getResource(appName, key, p);
			if (descr != null)
				_undoButtonCap=descr;

			setUpButtons();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getStackTrace());
		}
	}


	/**
	 * @return the caption for the cancel button
	 */
	public String getCancelButtonCap() {
		return _cancelButtonCap;
	}

	/**
	 * @return the caption for the ok button
	 */
	public String getOkButtonCap() {
		return _okButtonCap;
	}

	/**
	 * @return the caption for the undo button
	 */
	public String getUndoButtonCap() {
		return _undoButtonCap;
	}

	/**
	 * sets the caption for the cancel button
	 */
	public void setCancelButtonCap(String string) {
		_cancelButtonCap = string;
	}

	/**
	 * sets the caption for the ok button
	 */
	public void setOkButtonCap(String string) {
		_okButtonCap = string;
	}

	/**
	 * sets the caption for the undo button
	 */
	public void setUndoButtonCap(String string) {
		_undoButtonCap = string;
	}

	public static String buildWhereFromDsRow(DataStore dsb, int row) throws DataStoreException {
		StringBuffer ret=new StringBuffer(255);
		int colCount = dsb.getColumnCount();
		for (int i = 0; i < colCount; i++) {
			if (dsb.isColumnPrimaryKey(i)) {
				if (ret.length() != 0)
					ret.append (" and ");
				ret.append(dsb.getColumnDatabaseName(i));
				ret.append(" = ");
			}
		}
		return ret.toString();
	}
	protected boolean[] getButtonsVisible() {
		boolean ret[] = new boolean[_buttons.size()];
		for (int i = 0; i < _buttons.size(); i++) {
			HtmlSubmitButton b = (HtmlSubmitButton) _buttons.get(i);
			ret[i] = b.getVisible();
		}
		return ret;
	}
	protected void setButtonVisible(boolean vis[]) {
		for (int i = 0; i < _buttons.size(); i++) {
			HtmlSubmitButton b = (HtmlSubmitButton) _buttons.get(i);
			b.setVisible(vis[i]);
		}
	}
	protected boolean isFromLookup() {
		return (getLookupReturnToURL(null,null) != null);
	}
	protected boolean isFromPopupLookup() {
		return (getPage().getParameter(HtmlLookUpComponent.PARAM_LOOKUP_CONTROLLER)) != null;
	}

	protected String getLookupReturnToURL(String keyVal, String descVal) {
		HttpServletRequest req = getPage().getCurrentRequest();
		String retTo=req.getParameter("returnTo");
		if (retTo == null)
			return null;
		String comp=req.getParameter("comp");
		if (comp == null)
			return null;

		String ret=retTo;
		if (keyVal != null) {
			ret += "?" + comp + "=" + keyVal;
			if (descVal != null)
				ret += "&descReturn=" + descVal;
		}
		return ret;
	}

    /* Nicolás Genta - 9/27/04 Added for javascript confirm messages */
    protected void addConfirmScript(String message, HtmlHiddenField hidden) {
        String script = "if (confirm('" + message + "')) {\n" +
                "\tdocument.forms['pageForm']." + hidden.getFullName() + ".value = 1;\n" +
                "}\n" +
                "document.forms['pageForm'].submit();";
        getPage().writeScript(script);
    }



	/**
	 * @return the alignment for buttons that appear at the bottom of the component under the table
	 */
	public String getBottomButtonAlign() {
		return _bottomButtonAlign;
	}

	/**
	 * rets the alignment for buttons that appear at the bottom of the component under the table. Valid values are ALIGN_LEFT, ALIGN_CENTER and ALIGN_RIGHT
	 */
	public void setBottomButtonAlign(String string) {
		_bottomButtonAlign = string;
	}

}
