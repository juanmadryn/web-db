package com.salmonllc.html;

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.html.events.ValidateEvent;
import com.salmonllc.html.events.ValidateListener;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.jsp.JspLink;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.sql.ValidationRule;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.RegularExpressionMatcher;
import com.salmonllc.util.VectorSort;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This is a special type of text component used in conjunction with the HtmlComponentValidator to perform validations and display an error message.
 */
public class HtmlValidatorText extends HtmlComponent implements SubmitListener, ValueChangedListener {

	private HtmlComponentValidator _val;
	private Hashtable _valueChangedItems;
	private Hashtable _ruleIndexes;
	private int _ruleNo;
	private DataStoreBuffer _ds;
	private String _font;
	private String _fontStartTag;
	private String _fontEndTag;
	private String _theme;
	private int _breaksBefore;
	private int _breaksAfter;
	private int _breaksBetween = 1;
	private ErrorEntries _errorEntries = new ErrorEntries();
	private String _dbProfile;
	private boolean _valOk;
	private boolean _validateNewRows = true;
	private boolean _autoPopulateLookups = false;
	private Vector _submitComponents;
	private boolean _fixSpecialHtml = true;
	private boolean _multipleErrorsOK = false;
	private float _nextSeq;
	private Vector _listeners;
	private DataStoreEvaluator _skipRowExp;
	private boolean _doSetFocus = true;

	private boolean _javaScriptRules = false;
	private boolean _regExpressionRules = false;
	private boolean _reqRules = false;
	private boolean _rangeRules = false;

	private boolean _useAlertsForErrors = false;
	private boolean _addFocusLinksToErrors = true;
	private boolean _useJavaScriptForRegExpRules = false;
	private boolean _useJavaScriptForReqRules = false;
	private boolean _useJavaScriptForRangeRules = false;
	//fc 06/11/04: Add this variable to indicate whether Javascript rules
	// should be validated on the client side only.
	// A value true means client side only validation for javascript enabled
	// validations otherwise validation occurs on both
	// client and server side which is the default.
	private boolean _useJavaScriptForValidationOnly = false;
	private HtmlComponent _extraComponent = null;
	private boolean _errorOnLastPass = false;

	/**
	 * @author  demian
	 */
	private class ErrorEntry implements Serializable {
		String error;
		HtmlFormComponent comp;
		HtmlComponent extraDisplay;
		int row;
		float seq;
	}

	private class ErrorEntries extends VectorSort {
		public boolean compare(Object o1, Object o2) {
			ErrorEntry e1 = (ErrorEntry) o1;
			ErrorEntry e2 = (ErrorEntry) o2;
			float e1Val = (e1.row * 1000000) + e1.seq;
			float e2Val = (e2.row * 1000000) + e2.seq;
			return e1Val < e2Val;
		}
		public ErrorEntry getEntry(int row) {
			return (ErrorEntry) elementAt(row);
		}
	}
	/**
	 * Constructs a new validator
	 */
	public HtmlValidatorText(String name, String font, HtmlPage p, String theme) {
		super(name, p);
		if (_font == null)
			_font = HtmlText.FONT_ERROR;
		_font = font;
		setTheme(theme);
		_ruleNo = -1;
	}

	/**
	 * Constructs a new validator
	 */
	public HtmlValidatorText(String name, String font, HtmlPage p) {
		this(name, font, p, null);
	}

	/**
	 * Constructs a new validator
	 */
	public HtmlValidatorText(String name, HtmlPage p) {
		this(name, null, p, null);
	}

	/**
	 * This method implements the SubmitListener interface and is used for the
	 * internal operation of the component. It should not be called directly
	 */
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		if (_ds != null) {
			HtmlComponentValidator val = getValidator();
			val.reset();
			for (int i = 0; i < _ds.getRowCount(); i++) {
				if (_skipRowExp != null) {
					Object o = _skipRowExp.evaluateRow(i);
					if (o instanceof Boolean) {
						if (((Boolean) o).booleanValue())
							continue;
					}
				}
				int rowStat = _ds.getRowStatus(i);
				if (rowStat == DataStoreBuffer.STATUS_MODIFIED || rowStat == DataStoreBuffer.STATUS_NEW_MODIFIED || (_validateNewRows && rowStat == DataStoreBuffer.STATUS_NEW) || _ds.getBucketsModified(i)) {
					while (val.findNextError(i)) {
						HtmlFormComponent comp = val.getErrorComponent();
						String format = null;
						String value = "";
						int ruleNo = ((Integer) _ruleIndexes.get(new Integer(val.getCurrentRule()))).intValue();
						String error = val.getErrorMessage();
						if (comp != null) {
							int col = comp.getColumnNumber();
							if (col > -1) {
								value = val.getDataStoreBuffer().getFormattedString(i, col);
								format = val.getDataStoreBuffer().getFormat(col);
							}
							error = processErrorMessage(error, value, format);
						}
						addErrorMessage(error, comp, i, ruleNo);
						_valOk = false;
					}
					if (_listeners != null) {
						ValidateEvent evt = new ValidateEvent(getPage(), this, getName(), getFullName(), _ds, i);
						for (int j = 0; j < _listeners.size(); j++)
							((ValidateListener) _listeners.elementAt(j)).validate(evt);
					}
				}
			}

			if (!_valOk) {
				if (!_multipleErrorsOK)
					_errorEntries.setSize(1);
			}
		}

		if (_listeners != null) {
			ValidateEvent evt = new ValidateEvent(getPage(), this, getName(), getFullName(), _ds);
			for (int i = 0; i < _listeners.size(); i++)
				((ValidateListener) _listeners.elementAt(i)).validateComplete(evt);
		}
		if (!_valOk) {
			e.setValidationFailed(true);
			HtmlFormComponent comp = _errorEntries.getEntry(0).comp;
			if (comp != null) {
				int row = _errorEntries.getEntry(0).row;
				if (_doSetFocus) {
					_ds.gotoRow(row);
					if (scrollToDataTableRow(comp, row))
						comp.setFocus(row);
					else
						comp.setFocus();
				}
			}
			if (e.getNextListener() instanceof HtmlValidatorText)
				return true;
		}
		return !e.isValidationFailed();
	}

	/**
	 * This method implements the ValueChangedListener interface and is used for
	 * the internal operation of the component. It should not be called directly
	 */
	public boolean valueChanged(ValueChangedEvent e) throws Exception {
		HtmlComponent comp = e.getSubmitComponent();
		if (_submitComponents != null && comp != null) {
			boolean doProcess = false;
			for (int i = 0; i < _submitComponents.size(); i++) {
				if (_submitComponents.elementAt(i) == comp) {
					doProcess = true;
					break;
				}
			}
			if (!doProcess)
				return true;
		}

		HtmlFormComponent src = (HtmlFormComponent) e.getComponent();
		DataStoreBuffer ds = e.getDataStore();
		if (ds != null) {
			if (!ds.isFormattedStringValid(e.getColumn(), e.getNewValue())) {
				String error = (String) getValueChangedItems().get(src);
				String format = e.getDataStore().getFormat(e.getColumn());
				int ruleNo = ((Integer) (_ruleIndexes.get(src))).intValue();
				if (error != null) {
					e.setAcceptValue(ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE);
					addErrorMessage(processErrorMessage(error, e.getNewValue(), format), src, e.getRow(), ruleNo);
					_valOk = false;
				}
			}
		}
		return true;
	}

	/**
	 * @see com.salmonllc.html.HtmlComponent#generateHTML(PrintWriter, int)
	 */
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {

		boolean displaySomething = true;
		if (!getVisible())
			displaySomething = false;
		if (_errorEntries.size() == 0)
			displaySomething = false;

		if (getUseJavaScript())
			p.println(generateValidationJavaScript());

		p.print("<DIV id=\"div" + getFullName() + "\">");
		if (displaySomething) {
			StringBuffer out = new StringBuffer();
			_errorEntries.sort();
			HtmlFormComponent comp = _errorEntries.getEntry(0).comp;

			if (_useAlertsForErrors) {
				String msg = _errorEntries.getEntry(0).error;
				StringBuffer script = new StringBuffer();
				script.append("var theFocus=null;\r\n");

				if (comp != null) {
					if (isInDataTableHeaderOrFooter(comp)) {
						generateJavaScriptForComponentByName(comp, comp.getFullName(), comp.getJavaScriptName(), msg, script, "true", null);
					} else if (isInDataTable(comp)) {
						int row = _errorEntries.getEntry(0).row;
						generateJavaScriptForComponentByName(comp, comp.getFullName() + "_" + row, comp.getJavaScriptName(), msg, script, "true", null);
					} else {
						generateJavaScriptForComponentByName(comp, comp.getFullName(), comp.getJavaScriptName(), msg, script, "true", null);
					}
				} else {
                    script.append("var theMessage='" + msg + "';\r\n");

                }
				//getPage().writeScript("alert('" + escapeSingleQuote(msg) +
				// "');");
				script.append("   alert(theMessage);\r\n");
				script.append("if (theFocus != null) {\r\n");
				script.append("   theFocus.focus(); \r\n");
				script.append("   }\r\n");
				getPage().writeScript(script.toString());
			} else {
				for (int i = 0; i < _errorEntries.size(); i++) {
					String msg = _errorEntries.getEntry(i).error;
					if (_fixSpecialHtml)
						msg = fixSpecialHTMLCharacters(msg);
					boolean addLink = (_errorEntries.getEntry(i).comp != null && _addFocusLinksToErrors);
					if (addLink) {
						out.append("<a href=\"javascript:");

						if (isInDataTableHeaderOrFooter(comp)) {
							out.append(_errorEntries.getEntry(i).comp.getJavaScriptName());
							out.append(".focus();\">");
						} else {
							if (isInDataTable(comp)) {
								int row = _errorEntries.getEntry(i).row;
								if (isDataRowOnPage(comp, row)) {
									out.append(_errorEntries.getEntry(i).comp.getJavaScriptName());
									out.append("_");
									out.append(new Integer(row).toString());
									out.append(".focus();\">");
								} else {
									out.append("scrollTo" + getFullName() + "('" + getDataTableNameForComp(comp) + "'," + row + ",'" + _errorEntries.getEntry(i).comp.getName() + "'" + ");\">");
								}
							} else {
								out.append(_errorEntries.getEntry(i).comp.getJavaScriptName());
								out.append(".focus();\">");
							}
						}
					}

					if (_fontStartTag != null)
						out.append(_fontStartTag);

					out.append(msg);

					if (_errorEntries.getEntry(i).extraDisplay != null) {
						_extraComponent = _errorEntries.getEntry(i).extraDisplay;
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						PrintWriter pout = new PrintWriter(bout);
						_extraComponent.generateHTML(pout, -1);
						pout.flush();
						out.append(bout.toString());
						pout.close();
					} else
						_extraComponent = null;

					if (_fontEndTag != null)
						out.append(_fontEndTag);

					if (addLink)
						out.append("</a>");

					if (i < (_errorEntries.size() - 1)) {
						for (int j = 0; j < _breaksBetween; j++)
							out.append("<BR>");
					}
				}

				for (int i = 0; i < _breaksBefore; i++)
					p.println("<BR>");

				p.print(out.toString());

				for (int i = 0; i < _breaksAfter; i++)
					p.println("<BR>");

				if (_addFocusLinksToErrors) {
					p.println("<INPUT type=\"hidden\" name=\"" + getFullName() + "dataTable\" value=\"\">");
					p.println("<INPUT type=\"hidden\" name=\"" + getFullName() + "dataTableRow\" value=\"-1\">");
					p.println("<INPUT type=\"hidden\" name=\"" + getFullName() + "focusComp\" value=\"\">");
					p.println("<script language=\"javascript\">");
					p.println("function scrollTo" + getFullName() + "(tableName, row, focusComp){");
					p.println("  " + getFormString() + getFullName() + "dataTable.value=tableName;");
					p.println("  " + getFormString() + getFullName() + "dataTableRow.value=row;");
					p.println("  " + getFormString() + getFullName() + "focusComp.value=focusComp;");
					p.println("  " + getFormString() + "submit();");
					p.println("}");
					p.println("</script>");
				}
			}
		}

		p.println("</DIV>");

		_errorEntries.removeAllElements();
		_doSetFocus = true;
		_errorOnLastPass = displaySomething;

	}

	/**
	 * This method sets the property theme for the component.
	 * 
	 * @param theme
	 *            The theme to use.
	 */
	public void setTheme(String theme) {
		Props props = getPage().getPageProperties();

		if (_font == null) {
			_fontStartTag = props.getThemeProperty(theme, HtmlText.FONT_ERROR + Props.TAG_START);
			_fontEndTag = props.getThemeProperty(theme, HtmlText.FONT_ERROR + Props.TAG_END);
		} else {
			_fontStartTag = props.getThemeProperty(theme, _font + Props.TAG_START);
			_fontEndTag = props.getThemeProperty(theme, _font + Props.TAG_END);
		}

		_theme = theme;
	}

	/**
	 * Returns the number of line breaks to generate after the error text is
	 * displayed.
	 * 
	 * @return int
	 */
	public int getBreaksAfter() {
		return _breaksAfter;
	}

	/**
	 * Returns the number of line breaks to generate before the error text is
	 * displayed.
	 * 
	 * @return int
	 */
	public int getBreaksBefore() {
		return _breaksBefore;
	}
	/**
	 * Returns the number of line breaks between error messages if multiple are
	 * displayed
	 * 
	 * @return int
	 */
	public int getBreaksBetween() {
		return _breaksBetween;
	}

	/**
	 * Returns the DataStore validated by the validator
	 * 
	 * @return DataStore
	 */
	public DataStoreBuffer getDataStore() {
		return _ds;
	}

	/**
	 * Returns the Font used to display the error message
	 * 
	 * @return String
	 */
	public String getFont() {
		return _font;
	}

	/**
	 * Sets the number of line breaks to generate after the error text is
	 * displayed.
	 */
	public void setBreaksAfter(int breaksAfter) {
		_breaksAfter = breaksAfter;
	}

	/**
	 * Sets the number of line breaks to generate before the error text is
	 * displayed.
	 */
	public void setBreaksBefore(int breaksBefore) {
		_breaksBefore = breaksBefore;
	}

	/**
	 * Sets the number of line breaks between error messages if multiple are
	 * displayed
	 */
	public void setBreaksBetween(int breaksBetween) {
		_breaksBetween = breaksBetween;
	}

	/**
	 * Sets the DataStore validated by the validator.
	 */
	public void setDataStore(DataStoreBuffer ds) {
		_ds = ds;
	}

	/**
	 * Sets the error message will display in.
	 */
	public void setFont(String font) {
		_font = font;
		if (font == null)
			_font = HtmlText.FONT_ERROR;
		setTheme(_theme);
	}

	/**
	 * Adds a type check rule to the validator. This makes sure that the value
	 * in the component is the correct type for the datastore column it is bound
	 * to.
	 * 
	 * @param comp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addTypeCheckRule(HtmlFormComponent comp, String errorMessage, String localizationKey) throws DataStoreException {
		if (comp == null)
			throw new DataStoreException("Error. Component may not be null");
		comp.addValueChangedListener(this);
		getValueChangedItems().put(comp, computeErrorMessage(errorMessage, localizationKey));
		indexTypeCheckRule(comp);
	}

	/**
	 * Adds a type check rule to the validator. This makes sure that the value
	 * in the component is the correct type for the datastore column it is bound
	 * to.
	 * 
	 * @param comp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 */
	public void addTypeCheckRule(HtmlFormComponent comp, String errorMessage) throws DataStoreException {
		this.addTypeCheckRule(comp, errorMessage, null);
	}

	/**
	 * Adds a required rule to the validator. This makes sure that the value in
	 * the component is entered by the user
	 * 
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addRequiredRule(HtmlFormComponent focusComp, String errorMessage, String localizationKey) throws DataStoreException {
		_reqRules = true;
		HtmlComponentValidator val = getValidator();
		String name = focusComp.getFullName();
		if (_ds != null)
			name = _ds.getColumnName(focusComp.getColumnNumber());
		val.addRequiredRule(name, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Adds a required rule to the validator. This makes sure that the value in
	 * the component is entered by the user
	 * 
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addRequiredRule(HtmlFormComponent focusComp, String errorMessage) throws DataStoreException {
		_reqRules = true;
		HtmlComponentValidator val = getValidator();
		String name = focusComp.getFullName();
		if (_ds != null)
			name = _ds.getColumnName(focusComp.getColumnNumber());
		val.addRequiredRule(name, errorMessage, focusComp);
		indexOtherRule();
	}

	/**
	 * Adds a javascript rule to the validator. The rule can be any javascript
	 * that returns a boolean value (true means the validation succeeded and
	 * false means it failed). This rule is only executed on the client browser
	 * and only if the validator is set to use javascript.
	 * 
	 * @param regExp
	 *            A regular expression. The expression needs to match the column
	 *            in the datastore or the rule will be violated
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addRegularExpressionRule(String regExp, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		_regExpressionRules = true;
		HtmlComponentValidator val = getValidator();
		String name = focusComp.getFullName();
		if (_ds != null)
			name = _ds.getColumnName(focusComp.getColumnNumber());
		val.addRegularExpressionRule(name, regExp, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Adds a regular expression rule to the validator. The rule can be any
	 * javascript that returns a boolean value (true means the validation
	 * succeeded and false means it failed). This rule is only executed on the
	 * client browser and only if the validator is set to use javascript.
	 * 
	 * @param regExp
	 *            A regular expression. The expression needs to match the column
	 *            in the datastore or the rule will be violated
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 */
	public void addRegularExpressionRule(String regExp, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		_regExpressionRules = true;
		HtmlComponentValidator val = getValidator();
		String name = focusComp.getFullName();
		if (_ds != null)
			name = _ds.getColumnName(focusComp.getColumnNumber());
		val.addRegularExpressionRule(name, regExp, errorMessage, focusComp);
		indexOtherRule();
	}
	/**
	 * Adds a javascript rule to the validator. The rule can be any javascript
	 * that returns a boolean value (true means the validation succeeded and
	 * false means it failed). This rule is only executed on the client browser
	 * and only if the validator is set to use javascript.
	 * 
	 * @param javascript
	 *            A boolean javascript expression. If the expression returns
	 *            false it will trigger an error
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addJavascriptRule(String javascript, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		_javaScriptRules = true;
		HtmlComponentValidator val = getValidator();
		val.addJavaScriptRule(convertJavaScript(javascript), computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Adds a javascript rule to the validator. The rule can be any javascript
	 * that returns a boolean value (true means the validation succeeded and
	 * false means it failed). This rule is only executed on the client browser
	 * and only if the validator is set to use javascript.
	 * 
	 * @param javascript
	 *            A boolean javascript expression. If the expression returns
	 *            false it will trigger an error
	 * @param focusComp
	 *            The component to check
	 * @param errorMessage
	 *            The error message to display if the validation fails
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 */
	public void addJavascriptRule(String javascript, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		_javaScriptRules = true;
		HtmlComponentValidator val = getValidator();
		val.addJavaScriptRule(convertJavaScript(javascript), errorMessage, focusComp);
		indexOtherRule();
	}

	private void addJavascriptRule(String javascript, String errorMessage, HtmlFormComponent focusComp, DataStoreBuffer ds) throws DataStoreException {
		_javaScriptRules = true;
		HtmlComponentValidator val = getValidator();
		val.addJavaScriptRule(convertJavaScript(javascript, ds), errorMessage, focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked by the
	 * validator.
	 * 
	 * @param expression
	 *            A boolean expression that will trigger an error if evaluated
	 *            to false.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addExpressionRule(String expression, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addExpressionRule(expression, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked by the
	 * validator.
	 * 
	 * @param expression
	 *            A boolean expression that will trigger an error if evaluated
	 *            to false.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addExpressionRule(String expression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		addExpressionRule(expression, errorMessage, null, focusComp);
	}

	/**
	 * Use this method to add validation rules that will be checked by the
	 * validator.
	 * 
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addExpressionRule(DataStoreExpression expression, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addExpressionRule(expression, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked by the
	 * validator.
	 * 
	 * @param expression
	 *            A DataStoreExpression class that returns a boolean value. It
	 *            will trigger an error if the value returns false.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addExpressionRule(DataStoreExpression expression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		addExpressionRule(expression, errorMessage, null, focusComp);
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreEvaluator expression that returns a String and will
	 *            be used as the where clause for the SQL that will validate the
	 *            data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addLookupRule(lookupTable, searchExpression, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreEvaluator expression that returns a String and will
	 *            be used as the where clause for the SQL that will validate the
	 *            data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		addLookupRule(lookupTable, searchExpression, errorMessage, null, focusComp);
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table. In addition to checking if a set of columns are valid,
	 * this type of rule will also fill in a bucket in the datastore with a
	 * description taken from the row retrieved in the lookup table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreEvaluator expression that returns a String and will
	 *            be used as the where clause for the SQL that will validate the
	 *            data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 * @param descColumn
	 *            The name of the column in the lookup table used to fill in the
	 *            description.
	 * @param descBucket
	 *            The name of a bucket column in the datastore to place the
	 *            description.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, String localizationKey, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addLookupRule(lookupTable, searchExpression, computeErrorMessage(errorMessage, localizationKey), focusComp, descColumn, descBucket);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table. In addition to checking if a set of columns are valid,
	 * this type of rule will also fill in a bucket in the datastore with a
	 * description taken from the row retrieved in the lookup table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreEvaluator expression that returns a String and will
	 *            be used as the where clause for the SQL that will validate the
	 *            data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 * @param descColumn
	 *            The name of the column in the lookup table used to fill in the
	 *            description.
	 * @param descBucket
	 *            The name of a bucket column in the datastore to place the
	 *            description.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		addLookupRule(lookupTable, searchExpression, errorMessage, null, focusComp, descColumn, descBucket);
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreExpression that returns a String and will be used
	 *            as the where clause for the SQL that will validate the data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addLookupRule(lookupTable, searchExpression, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreExpression that returns a String and will be used
	 *            as the where clause for the SQL that will validate the data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		addLookupRule(lookupTable, searchExpression, errorMessage, null, focusComp);
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table. In addition to checking if a set of columns are valid,
	 * this type of rule will also fill in a bucket in the datastore with a
	 * description taken from the row retrieved in the lookup table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreExpression that returns a String and will be used
	 *            as the where clause for the SQL that will validate the data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 * @param descColumn
	 *            The name of the column in the lookup table used to fill in the
	 *            description.
	 * @param descBucket
	 *            The name of a bucket column in the datastore to place the
	 *            description.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, String localizationKey, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		HtmlComponentValidator val = getValidator();
		val.addLookupRule(lookupTable, searchExpression, computeErrorMessage(errorMessage, localizationKey), focusComp, descColumn, descBucket);
		indexOtherRule();
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup
	 * to another table. In addition to checking if a set of columns are valid,
	 * this type of rule will also fill in a bucket in the datastore with a
	 * description taken from the row retrieved in the lookup table.
	 * 
	 * @param lookupTable
	 *            The name of the table to lookup the value against.
	 * @param searchExpression
	 *            A DataStoreExpression that returns a String and will be used
	 *            as the where clause for the SQL that will validate the data.
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus to if the error expression
	 *            returns false.
	 * @param descColumn
	 *            The name of the column in the lookup table used to fill in the
	 *            description.
	 * @param descBucket
	 *            The name of a bucket column in the datastore to place the
	 *            description.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		addLookupRule(lookupTable, searchExpression, errorMessage, null, focusComp, descColumn, descBucket);
	}

	/**
	 * Use this method to add a range validation rule.
	 * 
	 * @param minValue
	 *            The minimum value the component must meet or null for no
	 *            minimum
	 * @param maxValue
	 *            The maximum value the component must meet or null for no
	 *            maximum
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param localizationKey
	 *            For internationalization, the key value used to look up the
	 *            error message for the usesr language (may be null)
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addRangeRule(Object minValue, Object maxValue, String errorMessage, String localizationKey, HtmlFormComponent focusComp) throws DataStoreException {
		_rangeRules = true;
		HtmlComponentValidator val = getValidator();
		String name = focusComp.getFullName();
		if (_ds != null)
			name = _ds.getColumnName(focusComp.getColumnNumber());
		val.addRangeRule(name, minValue, maxValue, computeErrorMessage(errorMessage, localizationKey), focusComp);
		indexOtherRule();
	}

	/**
	 * Use this method to add a range validation rule.
	 * 
	 * @param minValue
	 *            The minimum value the component must meet or null for no
	 *            minimum
	 * @param maxValue
	 *            The maximum value the component must meet or null for no
	 *            maximum
	 * @param errorMessage
	 *            The error message to display if the validation fails. It can
	 *            have a %VALUE% token embedded in it to include the value of
	 *            the column in error in the message
	 * @param focusComp
	 *            The HtmlComponent to set focus too if the error expression
	 *            returns false.
	 */
	public void addRangeRule(Object minValue, Object maxValue, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		_rangeRules = true;
		HtmlComponentValidator val = getValidator();
		val.addRangeRule(_ds.getColumnName(focusComp.getColumnNumber()), minValue, maxValue, errorMessage, focusComp);
		indexOtherRule();
	}

	private String computeErrorMessage(String message, String localizationKey) {
		if (localizationKey != null) {
			String val = LanguageResourceFinder.getResource(getPage().getApplicationName(), localizationKey, getPage().getLanguagePreferences());
			if (val != null)
				return val;
		}
		if (message == null)
			message = "A validation error has occurred";

		return message;
	}

	private String processErrorMessage(String error, String value, String format) {
		if (value == null)
			value = "null";
		if (format == null)
			format = "null";
		String val = replace(error, "%VALUE%", value);
		val = replace(val, "%FORMAT%", format.toLowerCase());
		return val;
	}

	private String replace(String sString, String sOldString, String sNewString) {
		StringBuffer sbString = new StringBuffer(sString);
		while (true) {
			int iIndex = sbString.toString().indexOf(sOldString);
			if (iIndex < 0)
				break;
			sbString.replace(iIndex, iIndex + sOldString.length(), sNewString);
		}
		return sbString.toString();
	}

	private Hashtable getValueChangedItems() {
		if (_valueChangedItems == null)
			_valueChangedItems = new Hashtable();
		return _valueChangedItems;
	}

	private HtmlComponentValidator getValidator() throws DataStoreException {
		if (_val == null) {
			if (_ds == null)
				_val = new HtmlComponentValidator(getPage().getApplicationName(), _dbProfile, new DataStoreBuffer());
			else
				_val = new HtmlComponentValidator(getPage().getApplicationName(), _dbProfile, _ds);
		}
		return _val;
	}

	/**
	 * Gets the database profile used for lookup validations
	 * 
	 * @return String
	 */
	public String getDBProfile() {
		return _dbProfile;
	}

	/**
	 * Sets the database profile used for lookup validations
	 */
	public void setDBProfile(String dbProfile) {
		_dbProfile = dbProfile;
	}

	/**
	 * Adds an HtmlSubmitButton to this component. The validation will fire when
	 * the submit button is clicked.
	 */
	public void addSubmitToListenTo(HtmlSubmitButton b) {
		b.insertSubmitListener(this);
		if (_submitComponents == null)
			_submitComponents = new Vector();
		_submitComponents.addElement(b);
	}

	/**
	 * Adds an HtmlLink to this component. The validation will fire when the
	 * link is clicked.
	 */
	public void addSubmitToListenTo(HtmlLink b) {
		b.insertSubmitListener(this);
		if (_submitComponents == null)
			_submitComponents = new Vector();
		_submitComponents.addElement(b);
		b.setHref("none");
	}

	/**
	 * Adds an JspLink to this component. The validation will fire when the link
	 * is clicked.
	 */
	public void addSubmitToListenTo(JspLink b) {
		b.insertSubmitListener(this);
		if (_submitComponents == null)
			_submitComponents = new Vector();
		_submitComponents.addElement(b);
		b.setHref("none");
	}

	/**
	 * Adds an HtmlSubmitImage to this component. The validation will fire when
	 * the submit image is clicked.
	 */
	public void addSubmitToListenTo(HtmlSubmitImage b) {
		b.insertSubmitListener(this);
		if (_submitComponents == null)
			_submitComponents = new Vector();
		_submitComponents.addElement(b);
	}

	/**
	 * @see com.salmonllc.html.HtmlComponent#processParms(Hashtable, int)
	 */
	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		_valOk = true;
		_nextSeq = _ruleNo;
		String[] dt = (String[]) parms.get(getFullName() + "dataTable");
		String[] dtRow = (String[]) parms.get(getFullName() + "dataTableRow");
		String[] focusComp = (String[]) parms.get(getFullName() + "focusComp");
		if (dtRow != null && !dtRow[0].equals("-1")) {
			int row = Integer.parseInt(dtRow[0]);
			HtmlComponent table = ((JspController) getPage()).getComponent(dt[0]);
			HtmlFormComponent comp = (HtmlFormComponent) ((JspController) getPage()).getComponent(focusComp[0]);
			if (table != null) {
				if (table instanceof HtmlDataTable)
					((HtmlDataTable) table).setPage(((HtmlDataTable) table).getPage(row));
				else
					((JspDataTable) table).setPage(((JspDataTable) table).getPage(row));
				_doSetFocus = false;
				if (comp != null)
					comp.setFocus(row);
				submitPerformed(new SubmitEvent(getPage(), this, getName(), getFullName()));
				return true;
			}
		}

		if (_extraComponent != null)
			return (_extraComponent.processParms(parms, rowNo));

		return super.processParms(parms, rowNo);
	}

	/**
	 * @see com.salmonllc.html.HtmlComponent#executeEvent(int)
	 */
	public boolean executeEvent(int eventType) throws Exception {
		if (_extraComponent != null)
			return _extraComponent.executeEvent(eventType);
		else
			return super.executeEvent(eventType);
	}

	/**
	 * Returns a boolean value indicating whether or not the validation
	 * succeeded
	 */
	public boolean getValidationSucceeded() {
		return _valOk;
	}

	private boolean scrollToDataTableRow(HtmlFormComponent comp, int row) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable) {
				HtmlDataTable t = (HtmlDataTable) parent;
				t.setPage(t.getPage(row));
				return true;
			} else if (parent instanceof JspDataTable) {
				JspDataTable t = (JspDataTable) parent;
				t.setPage(t.getPage(row));
				return true;
			}

			parent = parent.getParent();
		}
		return false;

	}

	private boolean isInDataTable(HtmlFormComponent comp) {

		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable)
				return true;
			else if (parent instanceof JspDataTable)
				return true;

			parent = parent.getParent();
		}
		return false;

	}

	private String getDataTableNameForComp(HtmlFormComponent comp) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable)
				return parent.getName();
			else if (parent instanceof JspDataTable)
				return parent.getName();

			parent = parent.getParent();
		}
		return null;
	}

	//fc 06/11/04: Indicates whether the component being validated is in a Data
	// Table or not.
	private boolean isComponentInDataTable(HtmlFormComponent comp) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable)
				return true;
			else if (parent instanceof JspDataTable)
				return true;

			parent = parent.getParent();
		}
		return false;
	}

	//fc 06/11/04: returns the datatable the given component is within.
	private HtmlComponent getComponentsDataTable(HtmlFormComponent comp) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable)
				return parent;
			else if (parent instanceof JspDataTable)
				return parent;

			parent = parent.getParent();
		}
		return null;
	}

	private boolean isDataRowOnPage(HtmlFormComponent comp, int row) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent instanceof HtmlDataTable)
				return ((HtmlDataTable) parent).isRowOnPage(row);
			else if (parent instanceof JspDataTable)
				return ((JspDataTable) parent).isRowOnPage(row);;

			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * Returns the a value indicating whether or not new rows in the datastore
	 * that haven't been modified get validated
	 * 
	 * @return boolean
	 */
	public boolean getValidateNewRows() {
		return _validateNewRows;
	}

	/**
	 * Sets the a value indicating whether or not new rows in the datastore that
	 * haven't been modified get validated
	 * 
	 * @param validateNewRows
	 *            The validateNewRows to set
	 */
	public void setValidateNewRows(boolean validateNewRows) {
		_validateNewRows = validateNewRows;
	}

	/**
	 * Use this method to fill in the descriptions for lookup columns for the
	 * current row in the datastore.
	 */
	public void populateDescriptions() throws Exception {
		populateDescriptions(_ds.getRow());
	}
	/**
	 * Use this method to fill in the descriptions for lookup columns.
	 * 
	 * @param rowNo
	 *            The row number in the DataStore to fill in.
	 */
	public void populateDescriptions(int rowNo) throws Exception {
		getValidator().populateDescriptions(rowNo);
	}
	/**
	 * Removes all other messages and adds this one to the list for one request
	 */
	public void setErrorMessage(String msg) {
		setErrorMessage(msg, null);
	}

	/**
	 * Removes all other messages and adds this one to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 * @param row
	 *            The row in the datastore to scroll to/set focus to
	 * @param extraDisplay
	 *            An extra HTML component to display next to the error message
	 */
	public void setErrorMessage(String msg, HtmlFormComponent comp, int row, HtmlComponent extraDisplay) {
		_errorEntries.removeAllElements();
		addErrorMessage(msg, comp, row, 1.0f, extraDisplay);
	}

	/**
	 * Removes all other messages and adds this one to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 * @param row
	 *            The row in the datastore to scroll to/set focus to
	 */
	public void setErrorMessage(String msg, HtmlFormComponent comp, int row) {
		_errorEntries.removeAllElements();
		addErrorMessage(msg, comp, row, 1.0f);
	}

	/**
	 * Removes all other messages and adds this one to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 */
	public void setErrorMessage(String msg, HtmlFormComponent comp) {
		setErrorMessage(msg, comp, -1);
	}

	/**
	 * Adds an error message to the list for one request
	 */
	public void addErrorMessage(String msg) {
		addErrorMessage(msg, null, -1);
	}

	/**
	 * Adds an error message to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 */
	public void addErrorMessage(String msg, HtmlFormComponent comp) {
		addErrorMessage(msg, comp, -1);
	}
	/**
	 * Adds an error message to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 * @param row
	 *            The row in the datastore to scroll to/set focus to
	 */
	public void addErrorMessage(String msg, HtmlFormComponent comp, int row) {
		_valOk = false;
		ErrorEntry entry = new ErrorEntry();
		entry.error = msg;
		_nextSeq += 1.0f;
		entry.seq = _nextSeq;
		if (_ds != null)
			entry.row = row > -1 ? row : _ds.getRow();
		else
			entry.row = row;
		entry.comp = comp;
		_errorEntries.addElement(entry);
	}

	/**
	 * Adds an error message to the list for one request
	 * 
	 * @param msg
	 *            The message to add
	 * @param comp
	 *            The component to set focus to
	 * @param row
	 *            The row in the datastore to scroll to/set focus to. Use -1 for
	 *            the current row.
	 * @param seq
	 *            The position that the error should be displayed if there are
	 *            multiple errors. Errors generated by rules have an sequence
	 *            corresponding to the order that the rule was added (1.0, 2.0,
	 *            etc). Your errors can be inserted into the list by specifying
	 *            a number that falls in the middle of any two rules.
	 *  
	 */
	public void addErrorMessage(String msg, HtmlFormComponent comp, int row, float seq) {
		addErrorMessage(msg, comp, row, seq, null);
	}

	private void addErrorMessage(String msg, HtmlFormComponent comp, int row, float seq, HtmlComponent extraDisplay) {
		_valOk = false;
		ErrorEntry entry = new ErrorEntry();
		entry.error = msg;
		entry.seq = seq;
		entry.extraDisplay = extraDisplay;
		if (_ds != null && row == -1)
			entry.row = _ds.getRow();
		else
			entry.row = row;
		entry.comp = comp;
		_errorEntries.addElement(entry);
	}
	/**
	 * Sets the autoPopulateLookups flag. This indicates whether or not the
	 * component will automatically populate lookup descriptions after it's
	 * datasource get autoretreieved
	 * 
	 * @param autoPopulateLookups
	 *            The autoPopulateLookups to set
	 */
	public void setAutoPopulateLookups(boolean autoPopulateLookups) {
		_autoPopulateLookups = autoPopulateLookups;
	}

	/**
	 * If autopopulate is true, it will populate all the description columns for
	 * the datastore, otherwise it won't do anything
	 */
	public void autoPopulateLookups() throws Exception {
		if (_autoPopulateLookups && _ds != null) {
			for (int i = 0; i < _ds.getRowCount(); i++)
				populateDescriptions(i);
			_ds.resetStatus();
		}

	}

	/**
	 * Returns whether special html characters ( <,>,&,; etc..) should be
	 * converted to Html Escape Sequences before being generated.
	 */
	public boolean getFixSpecialHtmlCharacters() {
		return _fixSpecialHtml;
	}

	/**
	 * Specify whether special html characters ( <,>,&,; etc..) should be
	 * converted to Html Escape Sequences before being generated.
	 */
	public void setFixSpecialHtmlCharacters(boolean fix) {
		_fixSpecialHtml = fix;
	}

	/**
	 * Gets whether or not the validator will display multiple errors
	 */
	public boolean getMultipleErrorsOK() {
		return _multipleErrorsOK;
	}

	/**
	 * Sets whether or not the validator will display multiple errors
	 */
	public void setMultipleErrorsOK(boolean multipleErrors) {
		_multipleErrorsOK = multipleErrors;
	}

	private void indexTypeCheckRule(HtmlComponent comp) {
		if (_ruleIndexes == null)
			_ruleIndexes = new Hashtable();
		Integer index = new Integer(++_ruleNo);
		_ruleIndexes.put(comp, index);
	}
	private void indexOtherRule() {
		if (_ruleIndexes == null)
			_ruleIndexes = new Hashtable();
		Integer index = new Integer(++_ruleNo);
		Integer valIndex = new Integer(_val.getRuleCount() - 1);
		_ruleIndexes.put(valIndex, index);
	}

	/**
	 * This method adds a listener the will be notified when after the scripted
	 * validations complete.
	 * 
	 * @param l
	 *            The listener to add.
	 */
	public void addValidateListener(ValidateListener l) {
		if (_listeners == null)
			_listeners = new Vector();
		//
		int listenersSize = _listeners.size();
		for (int i = 0; i < listenersSize; i++) {
			if (((ValueChangedListener) _listeners.elementAt(i)) == l)
				return;
		}
		_listeners.addElement(l);
	}

	/**
	 * This method is called when the component is created from a Jsp validator
	 * tag after all the fields are initialized. It is a convenient place for
	 * sub classesto override to initialize the component.
	 */
	public void initialize() {

	}

	/**
	 * Returns the number of errors found in the validator
	 */
	public int getErrorCount() {
		return _errorEntries.size();
	}

	/**
	 * Returns the error message at the specified index
	 */
	public String getErrorMessage(int index) {
		ErrorEntry e = (ErrorEntry) _errorEntries.elementAt(index);
		return e.error;
	}

	/**
	 * Returns the error component at the specified index
	 */
	public HtmlFormComponent getErrorComponent(int index) {
		ErrorEntry e = (ErrorEntry) _errorEntries.elementAt(index);
		return e.comp;
	}

	/**
	 * Returns the error row at the specified index
	 */
	public int getErrorRow(int index) {
		ErrorEntry e = (ErrorEntry) _errorEntries.elementAt(index);
		return e.row;
	}

	/**
	 * Sets an expression that will cause a row to be skipped (not be validated)
	 * if it evaluates to true
	 */
	public void setSkipRowExpression(String exp) throws DataStoreException {
		_skipRowExp = new DataStoreEvaluator(_ds, exp);
	}

	/**
	 * Sets an expression that will cause a row to be skipped (not be validated)
	 * if it evaluates to true
	 */
	public void setSkipRowExpression(DataStoreExpression exp) throws DataStoreException {
		_skipRowExp = new DataStoreEvaluator(_ds, exp);
	}

	/**
	 * Imports any validation rules described in the datastore into the
	 * validator
	 */
	public void importRules(DataStoreBuffer ds) {
		//import any rules in the ds
		HtmlPage p = getPage();
		if (!(p instanceof JspController))
			return;
		JspController cont = (JspController) p;

		try {
			for (int i = 0; i < ds.getColumnCount(); i++) {
				ValidationRule[] v = ds.getValidationRulesForColumn(i);
				if (v != null) {
					String colName = ds.getColumnName(i);
					for (int j = 0; j < v.length; j++) {
						int type = v[j].getRuleType();
						HtmlComponent comp = cont.getBoundComponent(ds, colName);
						HtmlFormComponent fComp = null;
						if (comp instanceof HtmlFormComponent)
							fComp = (HtmlFormComponent) comp;

						if (type == ValidationRule.TYPE_EXPRESSION) {
							if (v[j].getDsExpression() != null)
								addExpressionRule(v[j].getDsExpression(), v[j].getErrorMessage(), fComp);
							else
								addExpressionRule(v[j].getExpression(), v[j].getErrorMessage(), fComp);
						} else if (type == ValidationRule.TYPE_TYPE_CHECK) {
							if (fComp != null)
								addTypeCheckRule(fComp, v[j].getErrorMessage());
						} else if (type == ValidationRule.TYPE_REQUIRED) {
							if (fComp != null)
								addRequiredRule(fComp, v[j].getErrorMessage());
						} else if (type == ValidationRule.TYPE_JAVASCRIPT) {
							if (fComp != null)
								addJavascriptRule(v[j].getExpression(), v[j].getErrorMessage(), fComp, ds);
						} else if (type == ValidationRule.TYPE_LOOKUP) {
							if (fComp != null)
								addLookupRule(v[j].getLookupTable(), v[j].getLookupExpression(), v[j].getErrorMessage(), null, fComp, v[j].getDesciptionColumn(), v[j].getDescriptionBucket());
						} else if (type == ValidationRule.TYPE_REGULAR_EXPRESSION) {
							if (fComp != null)
								addRegularExpressionRule(v[j].getExpression(), v[j].getErrorMessage(), fComp);
						} else if (type == ValidationRule.TYPE_RANGE) {
							if (fComp != null)
								addRangeRule(v[j].getMinValue(), v[j].getMaxValue(), v[j].getErrorMessage(), fComp);
						}
					}
				}
			}
		} catch (DataStoreException ex) {
			MessageLog.writeErrorMessage("set data store", ex, this);
		}
	}

	/**
	 * @return true if the component will use client side javascript for
	 *         validations where that is possible
	 */
	public boolean getUseJavaScript() {
		return (_useJavaScriptForRegExpRules && _regExpressionRules) || (_useJavaScriptForReqRules && _reqRules) || (_useJavaScriptForRangeRules && _rangeRules) || _javaScriptRules;
	}

	/**
	 * @returns true if the component will use javascript alert popups to
	 *          display errors
	 */
	public boolean getUseAlertsForErrors() {
		return _useAlertsForErrors;
	}

	/**
	 * Set to true if the component will use javascript alert popups to display
	 * errors
	 */
	public void setUseAlertsForErrors(boolean b) {
		_useAlertsForErrors = b;
	}

	/**
	 * For javascript validations, this method generates the name of the on
	 * click function used for submit buttons. This is used by the framework and
	 * generally does not need to be called directly.
	 */
	public static String generateOnClickJavaScriptFunctionName(String componentFullName) {
		return componentFullName + "_validate()";
	}

	/**
	 * For javascript validations, this method generates the javascript function
	 * that will fire when a sumbit button is clicked. This is used by the
	 * framework and generally does not need to be called directly.
	 */
	public static String generateOnClickJavaScriptForButtons(String onClickCode, Vector submitListeners, String componentFullName) {
		if (submitListeners == null)
			return null;

		boolean isThereAScript = false;
		StringBuffer valScript = new StringBuffer("<script language=\"javaScript\">\r\n");
		valScript.append(" function " + generateOnClickJavaScriptFunctionName(componentFullName) + " {\r\n");

		for (int i = 0; i < submitListeners.size(); i++) {
			Object o = submitListeners.elementAt(i);
			if (o instanceof HtmlValidatorText) {
				HtmlValidatorText comp = (HtmlValidatorText) o;
				if (comp.getUseJavaScript()) {
					isThereAScript = true;
					valScript.append("   if (!" + generateOnClickJavaScriptFunctionName(comp.getFullName()) + ")\r\n");
					valScript.append("       return false;\r\n");
				}
			}
		}

		if (!isThereAScript)
			return null;

		if (onClickCode != null)
			valScript.append(onClickCode);

		valScript.append("   return true;\r\n");
		valScript.append(" }\r\n");
		valScript.append("</script>\r\n");

		return valScript.toString();
	}

	/**
	 * Returns the body of a javascript on click method for validation for this
	 * validator. Used internally by the framework and generally does not need
	 * to be called directly.
	 */
	private String generateValidationJavaScript() {
		String breaksBefore = "";
		for (int i = 0; i < _breaksBefore; i++)
			breaksBefore += "<BR>";
		String breaksAfter = "";
		for (int i = 0; i < _breaksAfter; i++)
			breaksAfter += "<BR>";

		if (!getUseJavaScript())
			return "";

		StringBuffer script = new StringBuffer();
		script.append("<script language=\"javaScript\">\r\n\r\n");

		script.append("function " + generateOnClickJavaScriptFunctionName(getFullName()) + " {\r\n");
		script.append("\r\nfunction isNull(strText) {\r\n");
		script.append(" for (i = 0; i < strText.length; i++){\r\n");
		script.append("	 if (strText.charAt(i) != ' ') return false;\r\n");
		script.append(" }\r\n");
		script.append(" return true;\r\n");
		script.append("}\r\n\r\n");
		script.append("var theSpan=document.getElementById(\"div" + getFullName() + "\");\r\n");
		script.append("var theFocus=null;\r\n");
		script.append("var theMessage='';\r\n");

		for (int i = 0; i < _val.getRuleCount(); i++) {
			if (_val.getRuleType(i) == ValidationRule.TYPE_REQUIRED && _useJavaScriptForReqRules)
				generateJavaScriptForRequiredRule(_val.getRuleFocusComponent(i), _val.getRuleErrorMessage(i), script);
			else if (_val.getRuleType(i) == ValidationRule.TYPE_JAVASCRIPT)
				generateJavaScriptForJavaScriptRule(_val.getRuleJavaScript(i), _val.getRuleFocusComponent(i), _val.getRuleErrorMessage(i), script);
			else if (_val.getRuleType(i) == ValidationRule.TYPE_REGULAR_EXPRESSION && _useJavaScriptForRegExpRules)
				generateJavaScriptForRegExpRule(_val.getRuleRegExpression(i), _val.getRuleFocusComponent(i), _val.getRuleErrorMessage(i), script);
			else if (_val.getRuleType(i) == ValidationRule.TYPE_RANGE && _useJavaScriptForRangeRules)
				generateJavaScriptForRangeRule(_val.getRuleMinValue(i), _val.getRuleMaxValue(i), _val.getRuleFocusComponent(i), _val.getRuleErrorMessage(i), script);
		}

		script.append("if (theFocus != null) {\r\n");
		if (_useAlertsForErrors)
			script.append("   alert(theMessage);\r\n");
		else {
			script.append("   while( theMessage.lastIndexOf('<BR>') == (theMessage.length - 4) )\r\n");
			script.append("        theMessage=theMessage.substr(0,theMessage.length - 4);\r\n");
			script.append("   theMessage='" + breaksBefore + "' + theMessage + '" + breaksAfter + "';\r\n");
			script.append("   theSpan.innerHTML=theMessage;");
		}

		script.append("   theFocus.focus(); \r\n");
		script.append("   window.event.returnValue = false;\r\n");
		script.append("   return false;\r\n");
		script.append("   }\r\n");
		script.append("else\r\n");
		script.append("   return true;\r\n");
		script.append("}\r\n");
		script.append("</script>");

		return script.toString();
	}

	private void generateJavaScriptForRegExpRule(RegularExpressionMatcher regExp, HtmlFormComponent focusComp, String message, StringBuffer script) {

		String test = "comp != null &&";
		if (regExp.getHandleNull() == RegularExpressionMatcher.HANDLE_NULL_DEFAULT)
			test += "!(exp.test(comp.value))";
		else if (regExp.getHandleNull() == RegularExpressionMatcher.HANDLE_NULL_RETURN_FALSE)
			test += "(isNull(comp.value) || !(exp.test(comp.value)))";
		else
			test += "(! isNull(comp.value) && !(exp.test(comp.value)))";

		String flags = regExp.getJavaScriptFlage();
		String reg = "exp = new RegExp('" + regExp.getPattern() + "'";
		if (flags != null && flags.length() > 0)
			reg += ",'" + flags + "'";
		reg += ");\r\n";
		generateJavaScriptForRule(focusComp, message, script, test, reg);
	}

	private void generateJavaScriptForRangeRule(Object minValue, Object maxValue, HtmlFormComponent focusComp, String message, StringBuffer script) {
		String vars = "";
		String test = "comp != null && (";
		if (minValue != null) {
			vars += "minValue=" + formatLiteral(minValue) + ";\r\n";
			test += "isNull(comp.value) || " + formatComp(minValue) + " < minValue";
		}
		if (maxValue != null) {
			vars += "maxValue=" + formatLiteral(maxValue) + ";\r\n";
			if (minValue != null)
				test += " ||";

			test += formatComp(maxValue) + " > maxValue";
		}
		test += ")";

		generateJavaScriptForRule(focusComp, message, script, test, vars);
	}
	private String formatComp(Object val) {
		if (val instanceof String)
			return "comp.value.toUpperCase()";
		else if (val instanceof Date)
			return "new Date(comp.value)";
		else
			return "comp.value";
	}

	private String formatLiteral(Object val) {
		if (val instanceof Date) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(((Date) val));
			return "new Date(" + cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DAY_OF_MONTH) + ")";
		} else if (val instanceof String)
			return "'" + val.toString().toUpperCase() + "'";
		else
			return val.toString();
	}

	private void generateJavaScriptForRequiredRule(HtmlFormComponent focusComp, String message, StringBuffer script) {
		String test = "comp != null && isNull(comp.value)";
		generateJavaScriptForRule(focusComp, message, script, test, null);
	}

	private void generateJavaScriptForJavaScriptRule(String test, HtmlFormComponent focusComp, String message, StringBuffer script) {
		test = "!(" + test + ")";
		generateJavaScriptForRule(focusComp, message, script, test, null);
	}

	private void generateJavaScriptForRule(HtmlFormComponent focusComp, String message, StringBuffer script, String test, String extraScript) {
		//fc 06/11/04: Changed the logic for generating the appropriate
		// javacript depending on whether the component is in
		// a datatable or not.
		if (!isComponentInDataTable(focusComp) || isInDataTableHeaderOrFooter(focusComp))
			generateJavaScriptForComponentByName(focusComp, focusComp.getFullName(), focusComp.getJavaScriptName(), message, script, test, extraScript);
		else {
			HtmlComponent hcDataTable = getComponentsDataTable(focusComp);
			JspDataTable jdt = null;
			HtmlDataTable hdt = null;
			int iStartRow = 0;
			int iRowsPerPage = 0;
			if (hcDataTable instanceof JspDataTable) {
				jdt = (JspDataTable) hcDataTable;
				iStartRow = jdt.getFirstRowOnPage();
				iRowsPerPage = jdt.getRowsPerPage();
			} else {
				hdt = (HtmlDataTable) hcDataTable;
				iStartRow = hdt.getFirstRowOnPage();
				iRowsPerPage = hdt.getRowsPerPage();
			}
			for (int i = iStartRow; i < iStartRow + iRowsPerPage && i < _ds.getRowCount(); i++) {
				generateJavaScriptForComponentByName(focusComp, focusComp.getFullName() + "_" + i, focusComp.getJavaScriptName() + "_" + i, message, script, test, extraScript);
			}
		}
	}

	//fc 06/11/04: Moved code to this method for easier implementation of
	// generating javascript.
	private void generateJavaScriptForComponentByName(HtmlFormComponent focusComp, String sCompName, String sJavaScriptName, String message, StringBuffer script, String test, String extraScript) {
		script.append("comp=" + getFormString() + sCompName + ";\r\n");
		if (extraScript != null)
			script.append(extraScript);
		if (!_multipleErrorsOK || _useAlertsForErrors) {
			script.append("if (theFocus == null && " + test + ") {\r\n");
			script.append("    theFocus=comp;\r\n");
		} else {
			script.append("if (" + test + ") {\r\n");
			script.append("    if (theFocus == null)\r\n");
			script.append("    	  theFocus=comp;\r\n");
		}
		if (_useAlertsForErrors)
			script.append("    theMessage='" + escapeSingleQuote(message) + "';\r\n");
		else {
			if (!_multipleErrorsOK)
				script.append("    theMessage='" + formatMessage(message, focusComp, sJavaScriptName) + "';\r\n");
			else
				script.append("    theMessage+='" + formatMessage(message, focusComp, sJavaScriptName) + "';\r\n");
		}
		script.append("}\r\n");

	}

	private String escapeSingleQuote(String messageIn) {
		if (messageIn.indexOf('\'') == -1)
			return messageIn;
		StringBuffer sb = new StringBuffer(messageIn.length() + 10);
		for (int i = 0; i < messageIn.length(); i++) {
			char c = messageIn.charAt(i);
			if (c == '\'') {
				sb.append('\\');
				sb.append('\'');
			} else
				sb.append(c);
		}
		return sb.toString();
	}

	//fc 06/11/04: Updated this method to generate proper href for error
	// message.
	private String formatMessage(String message, HtmlFormComponent focusComp, String sJavaScriptName) {
		String out = "";

		if (focusComp != null && _addFocusLinksToErrors)
			out += "<a href=\"javascript:" + escapeSingleQuote(sJavaScriptName) + ".focus();\">";

		if (_fontStartTag != null)
			out += _fontStartTag + fixSpecialHTMLCharacters(message) + _fontEndTag;
		else
			out += fixSpecialHTMLCharacters(message);

		if (focusComp != null && _addFocusLinksToErrors)
			out += "</a>";

		for (int i = 0; i < _breaksBetween; i++)
			out += "<BR>";

		return out;
	}

	private String convertJavaScript(String javaScript) {
		JspController cont = (JspController) getPage();
		StringBuffer retVal = new StringBuffer(javaScript.length());
		StringBuffer temp = new StringBuffer();
		boolean tempMode = false;
		for (int i = 0; i < javaScript.length(); i++) {
			char c = javaScript.charAt(i);
			if (c == '%') {
				if (!tempMode) {
					tempMode = true;
					temp.append(c);
				} else {
					retVal.append(checkTemp(temp, cont));
					temp.setLength(0);
					temp.append(c);
				}
			} else if (!tempMode)
				retVal.append(c);
			else {
				if (Character.isLetterOrDigit(c) || c == '-' || c == '_')
					temp.append(c);
				else {
					tempMode = false;
					retVal.append(checkTemp(temp, cont));
					retVal.append(c);
					temp.setLength(0);
				}
			}
		}
		if (temp.length() > 0)
			retVal.append(checkTemp(temp, cont));

		return retVal.toString();
	}

	private String checkTemp(StringBuffer temp, JspController cont) {
		String compName = temp.substring(1);
		HtmlComponent comp = cont.getComponent(compName);
		if (comp != null && comp instanceof HtmlFormComponent)
			return ((HtmlFormComponent) comp).getJavaScriptName();
		else
			return temp.toString();
	}

	private String convertJavaScript(String javaScript, DataStoreBuffer ds) {
		JspController cont = (JspController) getPage();
		StringBuffer retVal = new StringBuffer(javaScript.length());
		StringBuffer temp = new StringBuffer();
		boolean tempMode = false;
		for (int i = 0; i < javaScript.length(); i++) {
			char c = javaScript.charAt(i);
			if (c == '%') {
				if (!tempMode) {
					tempMode = true;
					temp.append(c);
				} else {
					retVal.append(checkTemp(temp, cont, ds));
					temp.setLength(0);
					temp.append(c);
				}
			} else if (!tempMode)
				retVal.append(c);
			else {
				if (Character.isLetterOrDigit(c) || c == '-' || c == '_')
					temp.append(c);
				else if (c == '.') {
					HtmlFormComponent comp = checkTemp(temp, cont, ds);
					if (comp != null) {
						retVal.append(comp.getJavaScriptName());
						retVal.append(c);
						temp.setLength(0);
						tempMode = false;
					} else
						temp.append(c);
				} else {
					tempMode = false;
					HtmlFormComponent comp = checkTemp(temp, cont, ds);
					if (comp == null)
						retVal.append(temp);
					else
						retVal.append(comp.getJavaScriptName());
					retVal.append(c);
					temp.setLength(0);
				}
			}
		}
		if (temp.length() > 0) {
			HtmlFormComponent comp = checkTemp(temp, cont, ds);
			if (comp == null)
				retVal.append(temp);
			else
				retVal.append(comp.getJavaScriptName());
		}

		return retVal.toString();
	}

	private HtmlFormComponent checkTemp(StringBuffer temp, JspController cont, DataStoreBuffer ds) {
		String compName = temp.substring(1);
		int colIndex = ds.getColumnIndex(compName);
		if (colIndex > -1) {
			HtmlFormComponent test = cont.getBoundComponent(ds, compName);
			if (test != null)
				return test;
		}
		HtmlComponent comp = cont.getComponent(compName);
		if (comp != null && comp instanceof HtmlFormComponent)
			return ((HtmlFormComponent) comp);
		else
			return null;
	}

	/**
	 * Returns whether or not error messages contain links that set focus to a
	 * field when clicked on
	 */
	public boolean getAddFocusLinksToErrors() {
		return _addFocusLinksToErrors;
	}

	/**
	 * Sets whether or not error messages contain links that set focus to a
	 * field when clicked on
	 */
	public void setAddFocusLinksToErrors(boolean b) {
		_addFocusLinksToErrors = b;
	}

	/**
	 * Returns whether or not to use javascript for range rules
	 */
	public boolean getUseJavaScriptForRangeRules() {
		return _useJavaScriptForRangeRules;
	}

	/**
	 * Returns whether or not to use javascript for regular expression rules
	 */
	public boolean getUseJavaScriptForRegularExpRules() {
		return _useJavaScriptForRegExpRules;
	}

	/**
	 * Sets whether or not to use javascript for required rules
	 */
	public boolean getUseJavaScriptForRequiredRules() {
		return _useJavaScriptForReqRules;
	}

	/**
	 * Sets whether or not to use javascript for range rules
	 */
	public void setUseJavaScriptForRangeRules(boolean b) {
		_useJavaScriptForRangeRules = b;
		try {
			//fc 06/11/04: Updated logic to make validate range dependent on
			// the value of _useJavaScriptForValidationOnly.
			getValidator().setValidateRange(!(b && _useJavaScriptForValidationOnly));
		} catch (Exception ex) {
		}
	}

	/**
	 * Sets whether or not to use javascript for regular expression rules
	 */
	public void setUseJavaScriptForRegularExpRules(boolean b) {
		_useJavaScriptForRegExpRules = b;

		try {
			HtmlComponentValidator v = getValidator();
			//fc 06/11/04: Updated logic to make validate range dependent on
			// the value of _useJavaScriptForValidationOnly.
			v.setValidateRegExp(!(b && _useJavaScriptForValidationOnly));
		} catch (Exception ex) {
		}
	}

	/**
	 * Sets whether or not to use javascript for required rules
	 */
	public void setUseJavaScriptForRequiredRules(boolean b) {
		_useJavaScriptForReqRules = b;
		try {
			//fc 06/11/04: Updated logic to make validate range dependent on
			// the value of _useJavaScriptForValidationOnly.
			getValidator().setValidateRequired(!(b && _useJavaScriptForValidationOnly));
		} catch (Exception ex) {
		}
	}

	//fc 06/11/04 Added this method for setting _useJavaScriptForValidationOnly
	/**
	 * Sets whether or not to use javascript only for validation, by default
	 * validates on both client and server.
	 */
	public void setUseJavaScriptForValidationsOnly(boolean b) {
		_useJavaScriptForValidationOnly = b;
	}

	/**
	 * Enables or disables the use of javascript for all rule types
	 */
	public void setUseJavaScript(boolean b) {
		setUseJavaScriptForRangeRules(b);
		setUseJavaScriptForRequiredRules(b);
		setUseJavaScriptForRegularExpRules(b);

	}

	/**
	 * @return true if the last time the validator displayed there were errors
	 *         in it
	 */
	public boolean isErrorOnLastPass() {
		return _errorOnLastPass;
	}

	/**
	 * @return true if component is in a datatable header or footer.
	 */

	public boolean isInDataTableHeaderOrFooter(HtmlComponent comp) {

		Object o = comp.getParent();
		if (o instanceof HtmlLookUpComponent) {
			//Special case for the HtmlLookupComponent
			return isInDataTableHeaderOrFooter(comp.getParent());
		} else if (o instanceof JspContainer) {
			JspContainer cont = (JspContainer) o;
			int compSize = cont.getComponentCount();
			for (int i = 0; i < compSize; i++) {
				if (comp == cont.getComponent(i))
					return (cont.getComponentType(i) == JspContainer.TYPE_HEADER || cont.getComponentType(i) == JspContainer.TYPE_FOOTER);
			}
		}

		return false;

	}

}