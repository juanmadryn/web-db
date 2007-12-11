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

package com.salmonllc.sql;

import com.salmonllc.util.RegularExpressionMatcher;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The definition of a validation rule
 */
public class ValidationRule implements java.io.Serializable {
    public static final int TYPE_EXPRESSION = 0;
    public static final int TYPE_TYPE_CHECK = 1;
    public static final int TYPE_LOOKUP = 3;
	public static final int TYPE_REMOTE = 4;
	public static final int TYPE_REQUIRED = 5;
	public static final int TYPE_JAVASCRIPT = 6;
	public static final int TYPE_REGULAR_EXPRESSION = 7;
	public static final int TYPE_RANGE = 8;
    private int _ruleType;
    private String _expression;
    private DataStoreExpression _dsExpression;
    private transient DataStoreEvaluator _eval;
    private String _errorMessage;
    private Object _minValue;
    private Object _maxValue;
    private boolean _executeOnServer=false;
    private String _lookupTable;
    private String _lookupExpression;
    private String _desciptionColumn;
    private String _descriptionBucket;
	private RegularExpressionMatcher _regMatcher;
    /**
     * Creates an expression rule. The expression is a boolean value and the rule will be violated if the expression returns false
     * @param expression The expression to evaluate
     * @param errorMessage The message to return if the rule is violated
     */
    public ValidationRule(String expression, String errorMessage) {
        _expression = expression;
        _executeOnServer = false;
        _errorMessage = errorMessage;
        _ruleType = TYPE_EXPRESSION;
    }

	/**
	 * Creates a range rule. The rule is true if the value of the field is within the minValue and maxValue
	 */
	public ValidationRule(Object minValue, Object maxValue, String errorMessage) {
		_minValue = minValue;
		_maxValue = maxValue;
		_executeOnServer = false;
		_errorMessage = errorMessage;
		_ruleType = TYPE_RANGE;
	}
	/**
	 * Creates an expression rule. The rule can be a SOFIA expression or a regular expression. A SOFIA expression returns a boolean value and the rule will be violated if the expression returns false. A regular expression will be violated if the value in the datastore column doesn't match the specified regular expression
	 * @param expression The expression to evaluate
	 * @param errorMessage The message to return if the rule is violated
	 * @param type TYPE_EXPRESSION (SOFIA Expression) or TYPE_REGULAR_EXPRESSION (Regular Expression) or TYPE_JAVASCRIPT (Note JavaScript Rules are not executed by this validator, they can be added here as a place holder in case the rules are imported into an HtmlValidatorTextComponent)
	 */
	public ValidationRule(String expression, String errorMessage, int type) {
		_expression = expression;
		_executeOnServer = false;
		_errorMessage = errorMessage;
		if (type == TYPE_REGULAR_EXPRESSION)
			_regMatcher = new RegularExpressionMatcher(expression);
		_ruleType = type;
	}
	
    /**
     * Creates an expression rule. The expression is a boolean value and the rule will be violated if the expression returns false
     * @param expression The expression to evaluate
     * @param errorMessage The message to return if the rule is violated
     * @param executeOnServer If the validator is being used from a proxy datastore and the rule must be executed on the server set this value to true
     */
    public ValidationRule(DataStoreExpression expression, String errorMessage, boolean executeOnServer) {
        _dsExpression = expression;
        _executeOnServer = executeOnServer;
        _errorMessage = errorMessage;
        _ruleType = TYPE_EXPRESSION;
    }

	/**
	 * Creates a type check rule. The type check rule checks if a field is the correct data type
	 * @param errorMessage The message to return if the rule is violated
	 */
	public ValidationRule(String errorMessage) {
		_ruleType= TYPE_TYPE_CHECK;
		_executeOnServer=false;
		_errorMessage=errorMessage;
	}
	
	/**
	 * Creates a type or a required rule rule. The type check rule checks if a field is the correct data type, required checks that the value is not null or spaces
	 * @param errorMessage The message to return if the rule is violated
	 * @param type The type of rule TYPE_TYPE_CHECK or TYPE_REQUIRED 
	 */
	public ValidationRule(String errorMessage, int type) {
		_ruleType= type;
		_executeOnServer=false;
		_errorMessage=errorMessage;
	}

    /**
     * Creates a lookup rule
     * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStore Expression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param descriptionColumn The name of the column in the lookup table used to fill in the description.
	 * @param descriptionBucket The name of a bucket column in the datastore to place the description.
     * @param errorMessage The message to return if the rule is violated
     */
    public ValidationRule(String lookupTable,String searchExpression,String descriptionColumn,String descriptionBucket, String errorMessage) {
        _ruleType = TYPE_LOOKUP;
        _executeOnServer = true;
        _lookupTable = lookupTable;
        _lookupExpression = searchExpression;
        _desciptionColumn = descriptionColumn;
        _descriptionBucket = descriptionBucket;
        _errorMessage = errorMessage;
    }

    /**
     * Creates a proxy rule that will run on the server
     */
    public ValidationRule() {
        _ruleType = TYPE_REMOTE;
        _executeOnServer = true;
    }
    /**
     * Evaluates the rule for a specific datastore, row and column. It throws a DataStoreException if the rule is violated
     */
    public void evaluateRule(DataStoreBuffer ds, int rowNo, int colNo, DBConnection conn) throws DataStoreException {
        if (_ruleType == TYPE_TYPE_CHECK) {
            if (ds.getTempValue(rowNo,colNo) == null)
                return;
            else if (! ds.isFormattedStringValid(colNo,ds.getTempValue(rowNo, colNo))) {
                throw new DataStoreException(_errorMessage);
            }
        }
        else if (_ruleType == TYPE_EXPRESSION) {
            DataStoreEvaluator eval = getEvaluator(ds);
            Object o = eval.evaluateRow(rowNo);
            if (! (o instanceof Boolean))
                throw new DataStoreException("Error, rule expression is not boolean");
            else if (!((Boolean)o).booleanValue())
                throw new DataStoreException(_errorMessage);
        }
        else if (_ruleType == TYPE_LOOKUP && conn != null) {
            if (! validateLookup(ds,rowNo,colNo,conn)) {
				throw new DataStoreException(_errorMessage);
            }
        }
        else if (_ruleType == TYPE_REQUIRED) {
        	if ( ds.getAny(rowNo,colNo) == null)
				throw new DataStoreException(_errorMessage);
			else if (ds.getColumnDataType(colNo) == DataStoreBuffer.DATATYPE_STRING && ds.getString(rowNo,colNo).trim().length() == 0)
				throw new DataStoreException(_errorMessage);	
        }
        else if (_ruleType == TYPE_REGULAR_EXPRESSION) {
        	String st = ds.getFormattedString(rowNo, colNo);
        	if (! _regMatcher.match(st))
				throw new DataStoreException(_errorMessage);
        }		
        else if (_ruleType == TYPE_RANGE) {
        	if (_minValue != null) {
				if (! ds.valueEqual(rowNo,colNo,_minValue))
					if (! ds.valueGreater(rowNo,colNo,_minValue))
						throw new DataStoreException(_errorMessage);
        	}		  
        	if (_maxValue != null) {
				if (! ds.valueEqual(rowNo,colNo,_maxValue))
					if (! ds.valueLess(rowNo,colNo,_maxValue))
						throw new DataStoreException(_errorMessage);
        	}	      		
        }	
    }

	
	     
    public String getDesciptionColumn() {
        return _desciptionColumn;
    }

    public String getDescriptionBucket() {
        return _descriptionBucket;
    }

    public DataStoreExpression getDsExpression() {
        return _dsExpression;
    }

    public String getErrorMessage() {
        return _errorMessage;
    }

    public boolean isExecuteOnServer() {
        return _executeOnServer;
    }

    public String getExpression() {
        return _expression;
    }

    public String getLookupExpression() {
        return _lookupExpression;
    }

    public String getLookupTable() {
        return _lookupTable;
    }

    public int getRuleType() {
        return _ruleType;
    }

    private boolean validateLookup(DataStoreBuffer ds, int rowNo, int colNo, DBConnection conn) throws DataStoreException {

        boolean retVal = false;

        String lookupTable = _lookupTable;
        DataStoreEvaluator d = getEvaluator(ds);
        String descColumn = _desciptionColumn;
        String descBucket = _descriptionBucket;

        if (ds.getAny(rowNo,colNo) == null) {
            if (descBucket != null) {
               ds.setAny(rowNo, descBucket, null);
               return true;
            }
        }

        String sql = "";
        if (descColumn != null)
            sql = "SELECT " + descColumn + " FROM " + lookupTable + " WHERE ";
        else
            sql = "SELECT count(*) FROM " + lookupTable + " WHERE ";

        sql += (String) d.evaluateRow(rowNo);

        String desc = null;
        try {
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery(sql);

            if (r.next()) {
                if (descColumn == null) {
                    if (r.getInt(1) > 0)
                        retVal = true;
                } else {
                    desc = r.getString(1);
                    retVal = true;
                }
            }
            r.close();
            st.close();
        }
        catch (java.sql.SQLException ex) {
            throw new DataStoreException(ex.getMessage(),ex);
        }

        if (descBucket != null)
            ds.setString(rowNo, descBucket, desc);

        return retVal;
    }

    private DataStoreEvaluator getEvaluator(DataStoreBuffer ds) throws DataStoreException {
        if (_eval != null)
            if (_eval.getDataStore() != ds)
                _eval = null;

        if (_eval == null) {
            if (_expression != null)
               _eval = new DataStoreEvaluator(ds,_expression);
            else if (_dsExpression != null)
               _eval = new DataStoreEvaluator(ds,_dsExpression);
            else if (_lookupExpression != null)
               _eval = new DataStoreEvaluator(ds,_lookupExpression);
        }
        return _eval;
    }
    	

	public Object getMaxValue() {
		return _maxValue;
	}

	public Object getMinValue() {
		return _minValue;
	}

}
