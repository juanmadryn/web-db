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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlComponentValidator.java $
//$Author: Dan $
//$Revision: 17 $
//$Modtime: 9/08/04 5:53p $
/////////////////////////

import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.sql.*;
import java.util.*;
/**
 * This class can be used to do basic validations against rows and columns in a DataStore. Simple Validation Rules can be added using the addValidationRule method and all the tests can be iterated through using the findFirstError and findNextError methods.
 */
public class HtmlComponentValidator {
	private DataStoreBuffer _ds;
	private Vector _rules = new Vector();
	private int _currentRule = -1;
	private String _appName;
 	private String _profile = null;

	private static final String TYPE_EXP = "E";
	private static final String TYPE_SQL = "S";
	private static final String TYPE_REQ = "R";
	private static final String TYPE_JS = "J";
	private static final String TYPE_REGEXP = "X";
	private static final String TYPE_RANGE = "N";

	private boolean _validateRequired=true;
	private boolean _validateRegExp=true;
	private boolean _validateRange=true;
	private boolean _validateLookup=true;
	private boolean _validateExpression=true;
	
		
	/**
	 * Constructs a new HtmlComponentValidator based on the DataStoreBuffer passed in.
	 */
	public HtmlComponentValidator(String applicationName, DataStoreBuffer ds) {
		super();
		_ds = ds;
		_appName = applicationName;
	}
	/**
	 * Constructs a new HtmlComponentValidator based on the DataStoreBuffer passed in.
	 */
	public HtmlComponentValidator(String applicationName, String DBProfile, DataStoreBuffer ds) {
		super();
		_ds = ds;
		_appName = applicationName;
		_profile = DBProfile;
	}

	/**
	 * Use this method to add validation rules that will be checked for a required field.
	 * @param column The colum to validate
	 * @param errorMessage The error message to display if the field is not filled in.
	 * @param focusComp The HtmlComponent to set focus too if the error expression returns false.
	 */
	public void addRequiredRule( String column, String errorMessage, HtmlFormComponent focusComp)  {
		FourObjectContainer f = new FourObjectContainer(TYPE_REQ, column, errorMessage, focusComp);
		_rules.addElement(f);
	}
	
	void addJavaScriptRule( String javaScript, String errorMessage, HtmlFormComponent focusComp) {
		FourObjectContainer f = new FourObjectContainer(TYPE_JS, javaScript, errorMessage, focusComp);
		_rules.addElement(f);
	}


	/**
	 * Adds a regular expression rule to the datastore. The rule will be violated if the value in the focus component doesn't match the regular expression
	 * @param The column to validate
	 * @param regExp The regular expression to use for validation
	 * @param errorMessage The error message to display if the field is not filled in.
	 * @param focusComp The HtmlComponent to set focus too if the error expression returns false.
	 */
	public void addRegularExpressionRule( String column,String regExp, String errorMessage, HtmlFormComponent focusComp) {
		FourObjectContainer f = new FourObjectContainer(TYPE_REGEXP, new TwoObjectContainer(column,new RegularExpressionMatcher(regExp)), errorMessage, focusComp);
		_rules.addElement(f);
	}
	
	
	/**
	 * Use this method to add validation rules that will be checked by the validator.
	 * @param expression A boolean expression that will trigger an error if evaluated to false.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus too if the error expression returns false.
	 */
	public void addExpressionRule(String expression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, expression);
		FourObjectContainer f = new FourObjectContainer(TYPE_EXP, d, errorMessage, focusComp);
		_rules.addElement(f);
	}

	/**
	 * Use this method to add validation rules that will be checked by the validator.
	 * @param expression A DataStoreExpression class that returns a boolean value. It will trigger an error if the value returns false.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus too if the error expression returns false.
	 */
	public void addExpressionRule(DataStoreExpression expression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, expression);
		FourObjectContainer f = new FourObjectContainer(TYPE_EXP, d, errorMessage, focusComp);
		_rules.addElement(f);
	}

	/**
	 * Use this method to add a range validation rule that will be checked by the validator.
	 * @param minValue The minimum allowed value in the component
	 * @param maxValue The maximum allowed value in the component
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus too if the error expression returns false.
	 */
	public void addRangeRule(String column,Object minValue, Object maxValue, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		ThreeObjectContainer cont=new ThreeObjectContainer(column,minValue,maxValue);
		FourObjectContainer f = new FourObjectContainer(TYPE_RANGE, cont, errorMessage, focusComp);
		_rules.addElement(f);
	}


	/**
	 * Use this method to add validation rules that will be checked via a lookup to another table.
	 * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStoreEvaluator expression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus to if the error expression returns false.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, searchExpression);
		FourObjectContainer sql = new FourObjectContainer(lookupTable, d, null, null);
		FourObjectContainer f = new FourObjectContainer(TYPE_SQL, sql, errorMessage, focusComp);
		_rules.addElement(f);
	}
	/**
	 * Use this method to add validation rules that will be checked via a lookup to another table. In addition to checking if a set of columns are valid, this type of rule will also fill in a bucket in the datastore with a description taken from the row retrieved in the lookup table.
	 * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStoreEvaluator expression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus to if the error expression returns false.
	 * @param descColumn The name of the column in the lookup table used to fill in the description.
	 * @param descBucket The name of a bucket column in the datastore to place the description.
	 */
	public void addLookupRule(String lookupTable, String searchExpression, String errorMessage, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, searchExpression);
		FourObjectContainer sql = new FourObjectContainer(lookupTable, d, descColumn, descBucket);
		FourObjectContainer f = new FourObjectContainer(TYPE_SQL, sql, errorMessage, focusComp);
		_rules.addElement(f);
	}

	/**
	 * Use this method to add validation rules that will be checked via a lookup to another table.
	 * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStoreExpression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus to if the error expression returns false.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, HtmlFormComponent focusComp) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, searchExpression);
		FourObjectContainer sql = new FourObjectContainer(lookupTable, d, null, null);
		FourObjectContainer f = new FourObjectContainer(TYPE_SQL, sql, errorMessage, focusComp);
		_rules.addElement(f);
	}
	/**
	 * Use this method to add validation rules that will be checked via a lookup to another table. In addition to checking if a set of columns are valid, this type of rule will also fill in a bucket in the datastore with a description taken from the row retrieved in the lookup table.
	 * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStoreExpression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param focusComp The HtmlComponent to set focus to if the error expression returns false.
	 * @param descColumn The name of the column in the lookup table used to fill in the description.
	 * @param descBucket The name of a bucket column in the datastore to place the description.
	 */
	public void addLookupRule(String lookupTable, DataStoreExpression searchExpression, String errorMessage, HtmlFormComponent focusComp, String descColumn, String descBucket) throws DataStoreException {
		DataStoreEvaluator d = new DataStoreEvaluator(_ds, searchExpression);
		FourObjectContainer sql = new FourObjectContainer(lookupTable, d, descColumn, descBucket);
		FourObjectContainer f = new FourObjectContainer(TYPE_SQL, sql, errorMessage, focusComp);
		_rules.addElement(f);
	}
	/**
	 * Use this method to find the next rule that is being violated using the current row in the DataStore.
	 * @return true if no errors are found and false if an error is found.
	 */
	public boolean findNextError() throws Exception {
		return findNextError(_ds.getRow());
	}
	/**
	 * Use this method to find the next rule that is being violated.
	 * @param rowNo The row number in the DataStore to validate.
	 * @return true if no errors are found and false if an error is found.
	 */
	public boolean findNextError(int rowNo) throws Exception {
		DBConnection conn = null;
		Statement st = null;

		_currentRule++;

		for (int i = _currentRule; i < _rules.size(); i++) {
			try {

				FourObjectContainer f = (FourObjectContainer) _rules.elementAt(i);
				boolean b = true;

				if (f.getObject1().equals(TYPE_REQ))
					b = validateRequired(f,rowNo);
				else if (f.getObject1().equals(TYPE_EXP))
					b = validateExpression(f, rowNo);
				else if (f.getObject1().equals(TYPE_REGEXP))
					b = validateRegularExpression(f,rowNo);	
				else if (f.getObject1().equals(TYPE_RANGE))
					b = validateRange(f,rowNo);	
				else if (f.getObject1().equals(TYPE_SQL)){
					if (conn == null) {
						conn = DBConnection.getConnection(_appName);
						st = conn.createStatement();
					}
					b = validateLookup(f, rowNo, st);
				}

				if (!b) {
					_currentRule = i;
					if (st != null)
						st.close();
					if (conn != null)
						conn.freeConnection();
					return true;
				}
			} catch (Exception e) {
				if (st != null)
					st.close();
				if (conn != null)
					conn.freeConnection();
				_currentRule = i;
				MessageLog.writeErrorMessage("findNextError()", e, this);
				return true;	
			}
		}

		if (st != null)
			st.close();

		if (conn != null)
			conn.freeConnection();

		_currentRule = -1;

		return false;
	}
	/**
	 * This method returns the DataStoreBuffer that the component will validate.
	 */
	public DataStoreBuffer getDataStoreBuffer() {
		return _ds;
	}
	/**
	 * This method returns the HtmlComponent that should have the focus when it's corresponding rule is violated or null if there are no errors.
	 */
	public HtmlFormComponent getErrorComponent() {
		if (_currentRule == -1)
			return null;
		else {
			FourObjectContainer f = (FourObjectContainer) _rules.elementAt(_currentRule);
			if (f.getObject4() == null)
				return null;
			else
				return (HtmlFormComponent) f.getObject4();
		}

	}
	/**
	 * This method returns the error message that should be displayed when the current rule is violated or null if there are no errors.
	 */
	public String getErrorMessage() {
		if (_currentRule == -1)
			return null;
		else {
			FourObjectContainer f = (FourObjectContainer) _rules.elementAt(_currentRule);
			return (String) f.getObject3();
		}

	}

    /**
     * Returns the number of the rule that was violated or -1 if none was
     */
    public int getCurrentRule() {
        return _currentRule;
    }

	/**
	 * Use this method to fill in the descriptions for lookup columns for the current row in the datastore.
	 */
	public void populateDescriptions() throws Exception {
		populateDescriptions(_ds.getRow());
	}
	/**
	 * Use this method to fill in the descriptions for lookup columns.
	 * @param rowNo The row number in the DataStore to fill in.
	 */
	public void populateDescriptions(int rowNo) throws Exception {
		DBConnection conn = null;
		Statement st = null;

		try {
			conn = DBConnection.getConnection(_appName, _profile);
			st = conn.createStatement();

			for (int i = 0; i < _rules.size(); i++) {
				FourObjectContainer f = (FourObjectContainer) _rules.elementAt(i);

				if (f.getObject1().equals(TYPE_SQL))
					validateLookup(f, rowNo, st);
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("populateDescriptions()", e, this);
		}

		if (st != null)
			st.close();

		if (conn != null)
			conn.freeConnection();

	}
	/**
	 * Use this method to reset the validator to the first rule in the list. Subsequent calls to findNextError will begin checking with the first rule instead of the one with the last error.
	 */
	public void reset() {
		_currentRule = -1;
	}
	private boolean validateRegularExpression(FourObjectContainer f, int rowNo) throws Exception {
		if (!_validateRegExp)
			return true;
			
		TwoObjectContainer cont = (TwoObjectContainer) f.getObject2();
		String column = (String) cont.getObject1();
		RegularExpressionMatcher matcher = (RegularExpressionMatcher) cont.getObject2();
		String s = _ds.getFormattedString(rowNo,column);
		return matcher.match(s);
	}	

	private boolean validateRange(FourObjectContainer f, int rowNo) throws Exception {
		if (!_validateRange)
			return true;
			
		ThreeObjectContainer cont = (ThreeObjectContainer) f.getObject2();
		
		String column = (String) cont.getObject1();
		int colNo = _ds.getColumnIndex(column);
		Object minValue = cont.getObject2();
		Object maxValue = cont.getObject3();
		if (minValue != null) {
			if (! _ds.valueEqual(rowNo,colNo,minValue))
				  if (! _ds.valueGreater(rowNo,colNo,minValue))
						return false;
		}
						  
		if (maxValue != null) {
			if (! _ds.valueEqual(rowNo,colNo,maxValue))
					if (! _ds.valueLess(rowNo,colNo,maxValue))
						return false;
		}	      
		
		return true;
	}	
	
	private boolean validateExpression(FourObjectContainer f, int rowNo) throws Exception {
		if (! _validateExpression)
			return true;
			
		DataStoreEvaluator d = (DataStoreEvaluator) f.getObject2();
		Boolean b = (Boolean) d.evaluateRow(rowNo);
		return b.booleanValue();
	}
	
	private boolean validateRequired(FourObjectContainer f, int rowNo) throws Exception {
		if (!_validateRequired)
			return true;
			
		Object o = _ds.getAny(rowNo,(String) f.getObject2());
		if (o == null)
			return false;
		else if (o.toString().trim().length() == 0)
			return false;
		else		
			return true;		
	}
	private boolean validateLookup(FourObjectContainer f1, int rowNo, Statement st) throws Exception {
		if (!_validateLookup)
			return false;
			
		boolean retVal = false;

		FourObjectContainer f = (FourObjectContainer) f1.getObject2();
		HtmlFormComponent comp = (HtmlFormComponent) f1.getObject4();

		String lookupTable = (String) f.getObject1();
		DataStoreEvaluator d = (DataStoreEvaluator) f.getObject2();
		String descColumn = (String) f.getObject3();
		String descBucket = (String) f.getObject4();

		if (comp != null) {
			if (comp.getDataStoreValue(rowNo) == null) {
				if (descBucket != null)
					_ds.setString(rowNo, descBucket, null);
				return true;
			}
		}

		String sql = "";
		if (descColumn != null)
			sql = "SELECT " + descColumn + " FROM " + lookupTable + " WHERE ";
		else
			sql = "SELECT count(*) FROM " + lookupTable + " WHERE ";

		sql += (String) d.evaluateRow(rowNo);

		Object desc = null;
		ResultSet r = st.executeQuery(sql);

		int descCount=0;
		if (r.next()) {
			if (descColumn == null) {
				if (r.getInt(1) > 0)
					retVal = true;
			} else {
				desc = r.getObject(1);
				descCount=1;
				if (r.next()) 
					descCount=2;
				retVal = true;
			}
		}
		r.close();
		
		if (descBucket != null) {
			if (comp.getParent() instanceof HtmlLookUpComponent) {
				HtmlLookUpComponent parent=(HtmlLookUpComponent) comp.getParent();
				if (parent.getEditDescription()) {
					if (_ds.getAny(rowNo,descBucket) == null) 
						_ds.setAny(rowNo, descBucket, desc);
				}	
				else
					_ds.setAny(rowNo, descBucket, desc);
			}	
			else	
				_ds.setAny(rowNo, descBucket, desc);
			
		}	

		return retVal;
	}

    /**
     * Return the number of rules in the validator
     */
    public int getRuleCount() {
        return _rules.size();
    }
    
    /**
     * Returns the type of validation rule (com.salmonllc.sql.ValidationRule TYPE_ constants)
     */
    public int getRuleType(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
    	String type = (String) cont.getObject1();
    	if (type == TYPE_EXP)
    		return ValidationRule.TYPE_EXPRESSION;
    	else if (type == TYPE_SQL)
    		return ValidationRule.TYPE_LOOKUP;
		else if (type == TYPE_REQ)
			return ValidationRule.TYPE_REQUIRED;
		else if (type == TYPE_JS)
			return ValidationRule.TYPE_JAVASCRIPT;	
		else if (type == TYPE_REGEXP)
			return ValidationRule.TYPE_REGULAR_EXPRESSION;		
		else if (type == TYPE_RANGE)
			return ValidationRule.TYPE_RANGE;						
    	else
    		return -1;			
    }	
    
    /**
     * Returns the form component associated with a rule
     */
    public HtmlFormComponent getRuleFocusComponent(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		return (HtmlFormComponent) cont.getObject4();
    }	
    
    /**
     * Returns the error message associated with a rule
     */
    public String getRuleErrorMessage(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		return (String) cont.getObject3();
    	
    }
    
    RegularExpressionMatcher getRuleRegExpression(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		TwoObjectContainer cont2 = (TwoObjectContainer) cont.getObject2();
		return (RegularExpressionMatcher) cont2.getObject2();
		
    }	
	String getRuleJavaScript(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		return (String) cont.getObject2();
	}	 
	Object getRuleMinValue(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		ThreeObjectContainer cont2 = (ThreeObjectContainer) cont.getObject2();
		return  cont2.getObject2();
	}	 
	Object getRuleMaxValue(int ruleNo) {
		FourObjectContainer cont = (FourObjectContainer) _rules.elementAt(ruleNo);
		ThreeObjectContainer cont2 = (ThreeObjectContainer) cont.getObject2();
		return  cont2.getObject3();
	}	 
	
	/**
	 * @return
	 */
	public boolean getValidateExpression() {
		return _validateExpression;
	}

	/**
	 * @return
	 */
	public boolean getValidateLookup() {
		return _validateLookup;
	}

	/**
	 * @return
	 */
	public boolean getValidateRange() {
		return _validateRange;
	}

	/**
	 * @return
	 */
	public boolean getValidateRegExp() {
		return _validateRegExp;
	}

	/**
	 * @return
	 */
	public boolean getValidateRequired() {
		return _validateRequired;
	}

	/**
	 * @param b
	 */
	public void setValidateExpression(boolean b) {
		_validateExpression = b;
	}

	/**
	 * @param b
	 */
	public void setValidateLookup(boolean b) {
		_validateLookup = b;
	}

	/**
	 * @param b
	 */
	public void setValidateRange(boolean b) {
		_validateRange = b;
	}

	/**
	 * @param b
	 */
	public void setValidateRegExp(boolean b) {
		_validateRegExp = b;
	}

	/**
	 * @param b
	 */
	public void setValidateRequired(boolean b) {
		_validateRequired = b;
	}

}
