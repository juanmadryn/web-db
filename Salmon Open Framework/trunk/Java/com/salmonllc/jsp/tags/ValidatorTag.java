package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ValidatorTag.java $
//$Author: Fred $
//$Revision: 28 $
//$Modtime: 6/10/04 11:43a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlLookUpComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.HtmlSubmitImage;
import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspLink;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import java.lang.reflect.Constructor;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * This class creates a validator tag
 */
public class ValidatorTag extends BaseBodyTag {

	public class Attributes {
		public String name;
		public String font;
		public String dbprofile;
		public String validatenewrows;
		public String autopopulatelookups;
		public String breaksbefore;
		public String breaksafter;
		public String breaksbetween;
		public String fixhtml;
		public String allowmultipleerrors;
		public String validatorClass;
		public String useJavaScriptForRules;
		public String useJavaScriptForRangeRules;
		public String useJavaScriptForRegExpRules;
		public String useJavaScriptForRequiredRules;
        public String useJavaScriptForValidationsOnly;
		public String useAlertsForErrors;
		public String addFocusLinksToErrors;
		public String autoImportRules;

		public String datasource;
		public String submitcomponents;
		public Vector expRules;
		public Vector lookupRules;
		public Vector typeCheckRules;
		public Vector regExpRules;
		public Vector javascriptRules;
		public Vector requiredRules;
		public Vector rangeRules;
	}

	private Attributes _att = new Attributes();

	public int doStartTag() throws JspException {
		return super.doStartTag();

	}
	void addExpressionRule(ValidatorRuleTag.Attributes a) {
		if (_att.expRules == null)
			_att.expRules = new Vector();
		_att.expRules.addElement(a);
	}

	void addRangeRule(ValidatorRangeTag.Attributes a) {
		if (_att.rangeRules == null)
			_att.rangeRules = new Vector();
		_att.rangeRules.addElement(a);
	}
	void addLookupRule(ValidatorLookupTag.Attributes a) {
		if (_att.lookupRules == null)
			_att.lookupRules = new Vector();
		_att.lookupRules.addElement(a);
	}
	void addTypeCheckRule(ValidatorTypeCheckTag.Attributes a) {
		if (_att.typeCheckRules == null)
			_att.typeCheckRules = new Vector();
		_att.typeCheckRules.addElement(a);
	}
	void addRegExpRule(ValidatorRegExpTag.Attributes a) {
		if (_att.regExpRules == null)
			_att.regExpRules = new Vector();
		_att.regExpRules.addElement(a);
	}
	void addJavascriptRule(ValidatorJavascriptTag.Attributes a) {
		if (_att.javascriptRules == null)
			_att.javascriptRules = new Vector();
		_att.javascriptRules.addElement(a);
	}
	void addRequiredRule(ValidatorRequiredTag.Attributes a) {
		if (_att.requiredRules == null)
			_att.requiredRules = new Vector();
		_att.requiredRules.addElement(a);
	}

	/**
	 * @see com.salmonllc.jsp.tags.BaseBodyTag#afterInit(HtmlComponent)
	 */
	public void afterInit(HtmlComponent comp) {
		_att.name = getName();
		getHelper().getTagContext().addValidatorTag(_att);
		_att = new Attributes();
	}
	/**
	 * @see com.salmonllc.jsp.tags.BaseBodyTag#createComponent()
	 */
	public HtmlComponent createComponent() {
		HtmlValidatorText v = null;
		if (_att.validatorClass != null)
			v = buildComponent();
		if (v == null)
			v = new HtmlValidatorText(getName(), _att.font, getHelper().getController());
		v.setBreaksBefore(BaseTagHelper.stringToInt(_att.breaksbefore, 0));
		v.setBreaksAfter(BaseTagHelper.stringToInt(_att.breaksafter, 0));
		v.setBreaksBetween(BaseTagHelper.stringToInt(_att.breaksbetween, 1));
		if (!BaseTagHelper.isEmpty(_att.dbprofile))
			v.setDBProfile(_att.dbprofile);
		if (!BaseTagHelper.isEmpty(_att.validatenewrows))
			v.setValidateNewRows(BaseTagHelper.stringToBoolean(_att.validatenewrows));
		if (!BaseTagHelper.isEmpty(_att.autopopulatelookups))
			v.setAutoPopulateLookups(BaseTagHelper.stringToBoolean(_att.autopopulatelookups));
		if (!BaseTagHelper.isEmpty(_att.fixhtml))
			v.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(_att.fixhtml));
		if (!BaseTagHelper.isEmpty(_att.fixhtml))
			v.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(_att.fixhtml));
		if (!BaseTagHelper.isEmpty(_att.allowmultipleerrors))
			v.setMultipleErrorsOK(BaseTagHelper.stringToBoolean(_att.allowmultipleerrors, false));
		if (!BaseTagHelper.isEmpty(_att.datasource))
			v.setDataSource(_att.datasource);
		if (!BaseTagHelper.isEmpty(_att.useAlertsForErrors))
			v.setUseAlertsForErrors(BaseTagHelper.stringToBoolean(_att.useAlertsForErrors, false));
		if (!BaseTagHelper.isEmpty(_att.addFocusLinksToErrors))
			v.setAddFocusLinksToErrors(BaseTagHelper.stringToBoolean(_att.addFocusLinksToErrors, false));

		return v;
	}
	/**
	 * @see com.salmonllc.jsp.tags.BaseBodyTag#generateComponentHTML(JspWriter)
	 */
	protected void generateComponentHTML(JspWriter w) throws Exception {
		if (getTagWriter().getDreamWeaverConv()) {
			HtmlValidatorText v = (HtmlValidatorText) getHelper().getComponent();
			v.setVisible(true);
			v.setErrorMessage("Validator:" + v.getName());
		}
		super.generateComponentHTML(w);
	}

	private HtmlValidatorText buildComponent() {
		try {
			Class c = Class.forName(_att.validatorClass, true, Thread.currentThread().getContextClassLoader());
			Class[] parms = { String.class, String.class, HtmlPage.class };
			Constructor con = c.getConstructor(parms);
			Object[] args = { getName(), _att.font, getHelper().getController()};
			return (HtmlValidatorText) con.newInstance(args);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error creating HtmlValidatorText component. Make sure the component exists and has a constructor with the arguments (String name, String font, HtmlPage p)", e, this);
		}
		return null;
	}
	/**
	 * @see com.salmonllc.jsp.tags.BaseBodyTag#getTagConvertType()
	 */
	public int getTagConvertType() {
		return CONV_WRAP_ALL_NESTED;
	}
	/**
	 * This method is part of the JSP tag lib specification.
	 */

	public void release() {
		super.release();
	}
	/**
	 * Sets the autopopulatelookups.
	 * @param autopopulatelookups The autopopulatelookups to set
	 */
	public void setAutopopulatelookups(String autopopulatelookups) {
		_att.autopopulatelookups = autopopulatelookups;
	}
	/**
	 * Sets the breaksAfter.
	 * @param breaksAfter The breaksAfter to set
	 */
	public void setBreaksafter(String breaksAfter) {
		_att.breaksafter = breaksAfter;
	}

	/**
	 * Sets the breaks between.
	 * @param breaksBetween The breaksAfter to set
	 */
	public void setBreaksbetween(String breaksBetween) {
		_att.breaksbetween = breaksBetween;
	}

	/**
	* Sets the fix html.
	* @param fix The breaksAfter to set
	*/
	public void setFixhtml(String fix) {
		_att.fixhtml = fix;
	}
	/**
	* Sets the allow multiples.
	* @param allowmultipleerrors
	*/
	public void setAllowmultipleerrors(String allowmultipleerrors) {
		_att.allowmultipleerrors = allowmultipleerrors;
	}
	/**
	 * Sets the breaksBefore.
	 * @param breaksBefore The breaksBefore to set
	 */
	public void setBreaksbefore(String breaksBefore) {
		_att.breaksbefore = breaksBefore;
	}
	/**
	 * Sets the datasource.
	 * @param datasource The datasource to set
	 */
	public void setDatasource(String datasource) {
		_att.datasource = datasource;
	}
	/**
	 * Sets the dbprofile.
	 * @param dbprofile The dbprofile to set
	 */
	public void setDbprofile(String dbprofile) {
		_att.dbprofile = dbprofile;
	}
	/**
	 * Sets the font.
	 * @param font The font to set
	 */
	public void setFont(String font) {
		_att.font = font;
	}
	/**
	 * Sets the submitcomponents.
	 * @param submitcomponents The submitcomponents to set
	 */
	public void setSubmitcomponents(String submitcomponents) {
		_att.submitcomponents = submitcomponents;
	}
	/**
	 * Sets up a validator. This is called after the page is initialized because the validator needs other components in the page
	 */
	public static void setUpValidator(Attributes att, HtmlValidatorText val, JspController cont) throws DataStoreException {

		boolean useJavaScriptForRules=BaseTagHelper.stringToBoolean(att.useJavaScriptForRules, false);
		boolean useJavascriptForRangeRules=BaseTagHelper.stringToBoolean(att.useJavaScriptForRangeRules, false);
		boolean useJavascriptForRegExpRules=BaseTagHelper.stringToBoolean(att.useJavaScriptForRegExpRules, false);
		boolean useJavascriptForReqRules=BaseTagHelper.stringToBoolean(att.useJavaScriptForRequiredRules, false);
        boolean useJavascriptForValidationsOnly=BaseTagHelper.stringToBoolean(att.useJavaScriptForValidationsOnly, false);
		int jsRuleCount = (att.javascriptRules != null ? att.javascriptRules.size() : 0);
		int ruleCount = (att.typeCheckRules != null ? att.typeCheckRules.size() : 0) + (att.expRules != null ? att.expRules.size() : 0) + (att.lookupRules != null ? att.lookupRules.size() : 0);

		if (useJavaScriptForRules || useJavascriptForRangeRules)
			jsRuleCount += (att.rangeRules != null ? att.rangeRules.size() : 0);
		else
			ruleCount += (att.rangeRules != null ? att.rangeRules.size() : 0);
			
		if (useJavaScriptForRules || useJavascriptForRegExpRules)
			jsRuleCount += (att.regExpRules != null ? att.regExpRules.size() : 0);
		else
			ruleCount += (att.regExpRules != null ? att.regExpRules.size() : 0);
			
		if (useJavaScriptForRules || useJavascriptForReqRules)
			jsRuleCount += (att.requiredRules != null ? att.requiredRules.size() : 0);
		else
			ruleCount += (att.requiredRules != null ? att.requiredRules.size() : 0);
				
		if (useJavaScriptForRules || useJavascriptForRegExpRules)
			jsRuleCount += (att.regExpRules != null ? att.regExpRules.size() : 0);
		else
			ruleCount += (att.regExpRules != null ? att.regExpRules.size() : 0); 
			
		ruleCount += jsRuleCount;

		// get the data store buffer
		DataStoreBuffer b = cont.getDataSource(att.datasource);
		if (b != null) {
			val.setDataStore(b);
			if (BaseTagHelper.stringToBoolean(att.autoImportRules, false))
				val.importRules(b);
		} else if (ruleCount > 0 && ruleCount > jsRuleCount)
			throw new NullPointerException("A data source is required for a validator with rules. Validator:" + att.name);

		//get the submit buttons
		if (att.submitcomponents != null) {
			StringTokenizer st = new StringTokenizer(att.submitcomponents, ",");
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				HtmlComponent comp = cont.getComponent(tok);
				if (comp != null) {
					if (comp instanceof HtmlSubmitButton)
						val.addSubmitToListenTo((HtmlSubmitButton) comp);
					else if (comp instanceof HtmlSubmitImage)
						val.addSubmitToListenTo((HtmlSubmitImage) comp);
					else if (comp instanceof JspLink)
						val.addSubmitToListenTo((JspLink) comp);
					else
						throw new ClassCastException("Error making: " + tok + " a submit object for validator: " + att.name);
				}
			}
		}
		//else if (ruleCount > 0)
		//throw new NullPointerException("A submit component source is required for a validator with rules. Validator:" + att.name);

		//do the typecheck rules
		if (att.typeCheckRules != null) {
			for (int i = 0; i < att.typeCheckRules.size(); i++) {
				ValidatorTypeCheckTag.Attributes a = (ValidatorTypeCheckTag.Attributes) att.typeCheckRules.elementAt(i);
				HtmlComponent comp = cont.getComponent(a.focuscomp);
				if (comp == null)
					throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for type check rule for validator:" + att.name);
				if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
					throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addTypeCheckRule(formComp, a.message, a.messagelocalekey);
			}
		}

		//do the required rules
		if (att.requiredRules != null) {
			for (int i = 0; i < att.requiredRules.size(); i++) {
				ValidatorRequiredTag.Attributes a = (ValidatorRequiredTag.Attributes) att.requiredRules.elementAt(i);
				HtmlComponent comp = cont.getComponent(a.focuscomp);
				if (comp == null)
					throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for type required rule for validator:" + att.name);
				if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
					throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				
				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addRequiredRule(formComp, a.message, a.messagelocalekey);
			}
		}
		//do the expression rules
		if (att.expRules != null) {
			for (int i = 0; i < att.expRules.size(); i++) {
				ValidatorRuleTag.Attributes a = (ValidatorRuleTag.Attributes) att.expRules.elementAt(i);

				HtmlComponent comp = null;
				if (a.focuscomp != null) {
					comp = cont.getComponent(a.focuscomp);
					if (comp == null)
						throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for expression rule for validator:" + att.name);
					if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
						throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				}
				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addExpressionRule(cont.convertExpressionOperators(a.expression), a.message, a.messagelocalekey, formComp);
			}
		}

		//do the regular expression rules
		if (att.regExpRules != null) {
			for (int i = 0; i < att.regExpRules.size(); i++) {
				ValidatorRegExpTag.Attributes a = (ValidatorRegExpTag.Attributes) att.regExpRules.elementAt(i);

				HtmlComponent comp = null;
				if (a.focuscomp != null) {
					comp = cont.getComponent(a.focuscomp);
					if (comp == null)
						throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for regular expression rule for validator:" + att.name);
					if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
						throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				}
				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addRegularExpressionRule(a.expression, a.message, a.messagelocalekey, formComp);
			}
		}
		//do the javascript rules
		if (att.javascriptRules != null) {
			for (int i = 0; i < att.javascriptRules.size(); i++) {
				ValidatorJavascriptTag.Attributes a = (ValidatorJavascriptTag.Attributes) att.javascriptRules.elementAt(i);

				HtmlComponent comp = null;
				if (a.focuscomp != null) {
					comp = cont.getComponent(a.focuscomp);
					if (comp == null)
						throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for javascript rule for validator:" + att.name);
					if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
						throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				}
				
				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addJavascriptRule(a.javascript, a.message, a.messagelocalekey, formComp);
			}
		}

		//do the lookup rules
		if (att.lookupRules != null) {
			for (int i = 0; i < att.lookupRules.size(); i++) {
				ValidatorLookupTag.Attributes a = (ValidatorLookupTag.Attributes) att.lookupRules.elementAt(i);

				HtmlComponent comp = null;
				if (a.focuscomp != null) {
					comp = cont.getComponent(a.focuscomp);
					if (comp == null)
						throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for expression rule for validator:" + att.name);
					if (!(comp instanceof HtmlFormComponent || comp instanceof HtmlLookUpComponent))
						throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				}

				HtmlFormComponent formComp;
				if (comp instanceof HtmlLookUpComponent)
					formComp = ((HtmlLookUpComponent)comp).getEditField();
				else
					formComp = (HtmlFormComponent) comp;	
				val.addLookupRule(a.lookupfromtable, cont.convertExpressionOperators(a.lookupwhereexpression), a.message, formComp, a.descriptionsource, a.descriptiontarget);
			}
		}

		//do the range rules
		if (att.rangeRules != null) {
			for (int i = 0; i < att.rangeRules.size(); i++) {
				ValidatorRangeTag.Attributes a = (ValidatorRangeTag.Attributes) att.rangeRules.elementAt(i);

				HtmlComponent comp = null;
				if (a.focuscomp != null) {
					comp = cont.getComponent(a.focuscomp);
					if (comp == null)
						throw new NullPointerException("Can't find focus component:" + a.focuscomp + " for range rule for validator:" + att.name);
					if (!(comp instanceof HtmlFormComponent))
						throw new ClassCastException("Error focus component:" + a.focuscomp + " must be an HtmlFormComponent for validator:" + att.name);
				}
				int dataType = DataStoreBuffer.DATATYPE_STRING;
				if (val.getDataStore() != null)
					dataType = val.getDataStore().getColumnDataType(((HtmlFormComponent) comp).getColumnNumber());
				try {
					val.addRangeRule(DataStoreBuffer.convertValue(a.minimum, dataType), DataStoreBuffer.convertValue(a.maximum, dataType), a.message, a.messagelocalekey, (HtmlFormComponent) comp);
				} catch (Exception ex) {
					throw new DataStoreException("Error parsing value for range validation", ex);
				}
			}
		}
		if (!BaseTagHelper.isEmpty(att.useJavaScriptForRules))
			val.setUseJavaScript(useJavaScriptForRules);

		if (!BaseTagHelper.isEmpty(att.useJavaScriptForRangeRules))
			val.setUseJavaScriptForRangeRules(useJavascriptForRangeRules);
		if (!BaseTagHelper.isEmpty(att.useJavaScriptForRegExpRules))
			val.setUseJavaScriptForRegularExpRules(useJavascriptForRegExpRules);
		if (!BaseTagHelper.isEmpty(att.useJavaScriptForRequiredRules))
			val.setUseJavaScriptForRequiredRules(useJavascriptForReqRules);
        if (!BaseTagHelper.isEmpty(att.useJavaScriptForValidationsOnly))
            val.setUseJavaScriptForValidationsOnly(useJavascriptForValidationsOnly);

	}
	/**
	 * Sets the validatenewrows.
	 * @param validatenewrows The validatenewrows to set
	 */
	public void setValidatenewrows(String validatenewrows) {
		_att.validatenewrows = validatenewrows;
	}

	/**
	  * Sets the validator class attribute
	  * @param validatorClass
	  */
	public void setValidatorclass(String validatorClass) {
		_att.validatorClass = validatorClass;
	}

	public void setRulejs(String val) {
		_att.useJavaScriptForRules = val;
	}

    public void setJavascriptforvalidationsonly(String val) {
        _att.useJavaScriptForValidationsOnly = val;
    }


	public void setRangerulejs(String val) {
		_att.useJavaScriptForRangeRules = val;
	}

	public void setRegexprulejs(String val) {
		_att.useJavaScriptForRegExpRules = val;
	}

	public void setRequiredrulejs(String val) {
		_att.useJavaScriptForRequiredRules = val;
	}

	public void setAlerts(String val) {
		_att.useAlertsForErrors = val;
	}

	public void setFocuslinks(String val) {
		_att.addFocusLinksToErrors = val;
	}

	public void setAutoimportrules(String val) {
		_att.autoImportRules = val;
	}

}
