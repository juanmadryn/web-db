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

/////////////////////////
//$Archive: /sofia/sourcecode/com/salmonllc/sql/DataStoreEvaluator.java $
//$Author: Deepak $
//$Revision: 33 $
//$Modtime: 9/07/04 2:53p $
/////////////////////////

import java.sql.Timestamp;
import java.text.*;
import java.util.*;

/**
 * This type is used to evaluate java-like expressions using rows in a DataBuffer. The class needs to be passed a String containing the expression that will be used. This expression will be parsed and evaluated using the evaluate row method.The rules for expressions are as follows:<BR><BR> Column names: Columns in the datastore can be referenced in the same way that they are referenced in the get and set methods in the datastore. Generally this is either by using tablename.columnname or a single intenral name string. Which you can use depends on how the column was added to the datastore.<BR><BR> Comparisons: You can use ==, >=, <=, !=, > or < comparisons in your expressions. Use these to compare the values of Strings, Dates, Numbers or Booleans. (Note: The values of each part of the comparison will be used as opposed to the default java method which will test object references).<BR><BR> Operators: You can use +, -, /, * and ^ (exponent) to perform operations on numeric values. The + operator may also be used to concatinate String values.<BR> In addition the if operator can be used to return a value based on a comparison. The syntax for if is : if(comparison,truevalue,falsevalue)<BR><BR> Literals: String Literals are surrounded by double quotes, Date, DateTime or Time Literals are also surrounded by quotes and in JDBC date escape form YYYY-MM-DD for dates, HH:MM:SS for times and a combination of the two for DateTimes<BR><BR> Nulls: The constant "null" can be used to check for columns values that are filled in. The equals(==) and not equals(!=) comparisons will return valid results when used with null. Every other comparison returns false when used with a null value on either side of the expression.<BR><BR> Several methods are available that can be called on the various columns in the datastore. This is done by placing the method name after the column name followed by any method parameters. ex: table1.column1.substring(0,10). Methods can only be invoked on columns in the datastore, not on literals as in Java. Any method invoked on a null column will itself return null.<BR><BR> Examples:<BR> <pre> "table1.column1 == 'xxxx'"<BR> "table1.column1.substring(2,3) + 'xxx'"<BR> </pre> String Methods:<BR> String substring(int start, int end): Gets a section of the String starting at start and ending at the character before end.<BR> Integer indexOf(String subString) : Finds the first position of subString in the string or -1 if no occurance is found.<BR> Integer lastIndexOf(String substring) : Finds the last position of subString in the string or -1 if no occurance is found.<BR> Integer length() : Finds the length of the string in characters.<BR> String toUpperCase(): Returns an all upper case version of the string.<BR> String urlEncode(): Replaces ampersand, questionmarks and spaces in a String with URL Encoded Characters<BR> String toLowerCase(): Returns an all lower case version of the string.<BR> String trim(): Returns all leading and trailing spaces from the string.<BR> String charAt(int position): Returns the character at position.<BR> String startsWith(String substring): Returns true if the string starts with the specified substring.<BR> String endsWith(String substring): Returns true if the string ends with the specified substring.<BR> String escape(): Returns a string with quotes and spaces escaped so they work in javascript code.<BR> String tempValue(): Returns a string with the temporary value of the column<BR> Numeric Methods:<BR><BR> Double sqrt(): Returns the square root of the number.<BR> Double round(int places): Returns the number rounded to "places" number of decimal places.<BR> String format(String pattern): Format the value as a string using the specified pattern.<BR><BR> Date Methods:<BR><BR> Integer getMonth(): Returns the month of the given date (January = 0, Dec = 11).<BR> Integer getDate(): Returns the day of the month of the given date.<BR> Integer getDay(): Returns the day of the week of a given date (Sunday = 0, Saturday = 6).<BR> Integer getYear(): Returns the year of the given date.<BR> Integer getHours(): Returns the hours of the given time.<BR> Integer getMinutes(): Returns the Minutes of the given time.<BR> Integer getSeconds(): Returns the Seconds of the given time.<BR> Date toDate(): Returns the Date portion of a DateTime.<BR> Time toTime(): Returns the Time portion of a DateTime.<BR> Date dateAdd(int days): Returns the specified date incremented by "days" number of days.<BR><BR> String format(String pattern): Format the value as a string using the specified pattern.<BR><BR> There are also five constants you can use<BR> null: The value null<BR> $DATE$:todays date as a Date value<BR> $TIME$: todays date and time as Datetime value<BR> $ROWNO$: the number of the row that the evaluator is evaluating<BR> $ROWCOUNT$: the total number of the rows in the datastore<BR> $ROWSTATUS$: the status of the row. See DatastoreBuffer.STATUS constants<br>  $QUOT$: include a single quote in a string<BR>
 */
public class DataStoreEvaluator implements java.io.Serializable {
    private String _terminators = "+-*/=()\",><&|!^,'";
    private String _expression;
    private String _lastToken;
    private String[] _inPost;
    int _pos = 0;
    private Stack _st = new Stack();
    private int _stackCount = 0;

    private DataStoreBuffer _ds;
    private String _pattern = null;
    private Format _format = null;
    private DataStoreExpression _exp;
    private String _resultBucket = null;

	/**
	 * @return Returns the resultBucket, a column to place the value of the results of the expression.
	 */
	public String getResultBucket() {
		return _resultBucket;
	}
	/**
	 * @param resultBucket The resultBucket to set. A column to place the value of the results of the expression
	 */
	public void setResultBucket(String resultBucket) {
		_resultBucket = resultBucket;
	}
    //private Vector _refs = new Vector();
    //private Vector _refsTypes = new Vector();

    //private static final int FUNC_REF = 0;
    //private static final int VAR_REF = 1;

    private static final int TYPE_NUM = 0;
    private static final int TYPE_DATETIME = 1;
    private static final int TYPE_STRING = 2;
    private static final int TYPE_BOOL = 3;
    private static final int TYPE_ANY = 4;
    private static final int TYPE_DATE = 5;
    private static final int TYPE_TIME = 6;

    public static final int AGGREGATE_SUM = 0;
    public static final int AGGREGATE_COUNT = 1;
    public static final int AGGREGATE_AVERAGE = 2;


    /**
     * This method will construct a new DataStoreEvaluator using the specified expression.
     * @param db The DataStoreBuffer to evaluate.
     * @param expression The expression to evaluate.
     */
    public DataStoreEvaluator(DataStoreBuffer db, DataStoreExpression expression) throws DataStoreException {
        _ds = db;
        _exp = expression;
    }

    /**
     * This method will construct a new DataStoreEvaluator using the specified expression.
     * @param db The DataStoreBuffer to evaluate.
     * @param expression The expression to evaluate.
     * @param format The pattern for the output.
     * @see DataStore#setFormat
     */
    public DataStoreEvaluator(DataStoreBuffer db, DataStoreExpression expression, String format) throws DataStoreException {
        this(db, expression);
        _pattern = format;
    }

    /**
     * This method will construct a new DataStoreEvaluator using the specified expression.
     * @param db The DataStoreBuffer to evaluate.
     * @param expression The expression to evaluate.
     */
    public DataStoreEvaluator(DataStoreBuffer db, String expression) throws DataStoreException {
        _ds = db;
        try {
            intoPost(expression);
        } catch (Exception e) {
            throw new DataStoreException(e.getMessage());
        }
    }

    /**
     * This method will construct a new DataStoreEvaluator using the specified expression.
     * @param db The DataStoreBuffer to evaluate.
     * @param expression The expression to evaluate.
     * @param format The pattern for the output.
     * @see DataStore#setFormat
     */
    public DataStoreEvaluator(DataStoreBuffer db, String expression, String format) throws DataStoreException {
        this(db, expression);
        _pattern = format;
    }

    /**
     *
     * @param v
     * @param token
     */
    private void appendToVector(Vector v, String token) {
        v.addElement(token);

        /*if (isNumber(token))
            return;
        if (isString(token))
            return;
        if (isDateTime(token))
            return;

        if (orderOfOperations(token) > 0 && ! isFunction(token))
            return;

        boolean found = false;
        for (int i = 0;i< _refs.size();i++) {
            if ( token.equals((String) _refs.elementAt(i))) {
                found = true;
                break;
            }
        }

        if (! found) {
            _refs.addElement(token);
            if (isFunction(token))
                _refTypes.addElement(new Integer(FUNC_REF));
            else
                _refTypes.addElement(new Integer(VAR_REF));
        }*/
    }

    /**
     * This method evaluates the expression passed in the constructor for every row in the DataStoreBuffer.
     * @return A object with the result of the evaluation.
     * @param aggregateType Valid Values are:AGGREGATE_SUM, AGGREGATE_COUNT
     */
    public Object evaluateAggregate(int aggregateType) throws DataStoreException {
        return evaluateAggregate(aggregateType, 0, _ds.getRowCount() - 1);
    }

    /**
     * This method evaluates the expression passed in the constructor for every row in the DataStoreBuffer.
     * @return A object with the result of the evaluation.
     * @param aggregateType Valid Values are:AGGREGATE_SUM, AGGREGATE_COUNT
     * @param startRow the first row to evaluate
     * @param endRow the last row to evaluate
     */
    public Object evaluateAggregate(int aggregateType, int startRow, int endRow) throws DataStoreException {
        double result = 0;
        int rc = 0;

        for (int i = startRow; i <= endRow; i++) {
            Object o = evaluateRow(i);
            if (aggregateType == AGGREGATE_AVERAGE) {
                rc++;
                if (o instanceof Number)
                    result = result + ((Number) o).doubleValue();
            }
            if (aggregateType == AGGREGATE_SUM) {
                if (o instanceof Number)
                    result = result + ((Number) o).doubleValue();
            } else if (aggregateType == AGGREGATE_COUNT) {
                if (o instanceof Boolean) {
                    if (((Boolean) o).booleanValue())
                        result++;
                }
                else if (o != null)
                    result++;
            }
        }

        if (aggregateType == AGGREGATE_AVERAGE && rc > 0)
            result = result / rc;

        if (_resultBucket != null)  {
        	_ds.setDouble(endRow,_resultBucket,result);
            _ds.setDouble(startRow,_resultBucket,result);
        }

        return new Double(result);
    }

    /**
     * This method evaluates the expression passed in the constructor for every row in the DataStoreBuffer. It will format the output according to the parrern specified and return a string version of it.
     * @return A object with the result of the evaluation.
     * @param aggregateType Valid Values are:AGGREGATE_SUM, AGGREGATE_COUNT
     */
    public String evaluateAggregateFormat(int aggregateType) throws DataStoreException {
        return evaluateAggregateFormat(aggregateType, 0, _ds.getRowCount() - 1);
    }

    /**
     * This method evaluates the expression passed in the constructor for every row in the DataStoreBuffer. It will format the output according to the parrern specified and return a string version of it.
     * @return A object with the result of the evaluation.
     * @param aggregateType Valid Values are:AGGREGATE_SUM, AGGREGATE_COUNT
     * @param startRow the first row to evaluate
     * @param endRow the last row to evaluate
     */
    public String evaluateAggregateFormat(int aggregateType, int startRow, int endRow) throws DataStoreException {
        Object res = evaluateAggregate(aggregateType, startRow, endRow);
        if (res == null)
            return null;

        if (_pattern == null)
            return res.toString();

        if (_format == null) {
            if (res instanceof Number)
                _format = new DecimalFormat(_pattern);
            else if (res instanceof java.util.Date)
                _format = new SimpleDateFormat(_pattern);
            else
                return res.toString();
        }

        if (res instanceof Number)
            return _format.format(res);
        else if (res instanceof java.util.Date)
            return _format.format(res);
        else
            return res.toString();
    }

    /**
     * This method evaluates the expression passed in the constructor for the current row in the DataStoreBuffer.
     * @return A object with the result of the evaluation.
     */
    public Object evaluateRow() throws DataStoreException {
        return evaluateRow(_ds.getRow());
    }


    /**
     * This method evaluates the expression passed in the constructor for a particular row in the DataStoreBuffer.
     * @param rowNo - row The row to evaluate.
     * @return A object with the result of the evaluation.
     * @throws DataStoreException
     */
    public Object evaluateRow(int rowNo) throws DataStoreException {
        if (_exp != null) {
            if (rowNo < 0 || rowNo >= _ds.getRowCount())
                return null;
            else
                return _exp.evaluateExpression(_ds, rowNo);
        }


        DSDataStoreDescriptor desc = _ds.getDescriptor();
        DSDataRow row = _ds.getDataRow(rowNo);
        if (row == null)
            return null;

        _st = new Stack();
        _stackCount = 0;

        int size = _inPost.length;
        String token = "";

        for (int i = 0; i < size; i++) {
            token = _inPost[i];
            if (token.equals("null"))
                _st.push(null);
            else if (token.charAt(0) == '$' && token.charAt(token.length() - 1) == '$') {
                if (token.equals("$TIME$"))
                    _st.push(functionCurrentTime());
                else if (token.equals("$DATE$"))
                    _st.push(functionCurrentDate());
                else if (token.equals("$ROWNO$"))
                    _st.push(new Integer(rowNo));
                else if (token.equals("$ROWCOUNT$"))
                    _st.push(new Integer(_ds.getRowCount()));
				else if (token.equals("$QUOT$"))
					_st.push("'");
				else if (token.equals("$ROWSTATUS$"))
					_st.push(new Integer(_ds.getRowStatus(rowNo)));  
                else
                    throw new DataStoreException("Unknown token error:" + token);
            } else if (isNumber(token))
                _st.push(Double.valueOf(token));
            else if (isDateTime(token))
                _st.push(java.sql.Timestamp.valueOf(token.substring(1, token.length() - 1)));
            else if (isDate(token))
                _st.push(java.sql.Date.valueOf(token.substring(1, token.length() - 1)));
            else if (isTime(token))
                _st.push(java.sql.Time.valueOf(token.substring(1, token.length() - 1)));
            else if (isString(token))
                _st.push(token.substring(1, token.length() - 1));
            else if (isBoolean(token))
                _st.push(Boolean.valueOf(token));
            else if (token.endsWith(".tempValue")) {
                token = token.substring(0,token.length()- 10);
                int ndx = desc.getColumnIndex(token);
                if (ndx < 0)
                    throw new DataStoreException("Unknown token error:" + token);
                else {
                    _st.push(row.getTempValue(ndx));
                }
            }
            else if (isOperator(token)) {
                Object o = executeOperation(desc, row, token);
                if (isFunction(token))
                    _stackCount = -1;
                _st.push(o);
            }
            else {
                int ndx = desc.getColumnIndex(token);
                if (ndx < 0)
                    throw new DataStoreException("Unknown token error:" + token);
                else {
                    _st.push(row.getData(ndx));
                }
            }
            _stackCount++;

        }

        Object o=_st.pop();

        if (_resultBucket != null)
        	_ds.setAny(rowNo,_resultBucket,o);

        return o;
    }

    /**
     * This method evaluates the expression passed in the constructor for the current row in the DataStoreBuffer. It will then format the result using the format pattern and return a String version of the result;
     * @return A String representation of the result of the evaluation.
     */
    public String evaluateRowFormat() throws DataStoreException {
        return evaluateRowFormat(_ds.getRow());
    }

    /**
     * This method evaluates the expression passed in the constructor for a particular row in the DataStoreBuffer. It will then format the result using the format pattern and return a String version of the result;
     * @return A String representation of the result of the evaluation.
     * @param rowNo The row to evaluate.
     */
    public String evaluateRowFormat(int rowNo) throws DataStoreException {
        Object res = evaluateRow(rowNo);
        if (res == null)
            return null;

        if (_pattern == null)
            return res.toString();

        if (_format == null) {
            if (res instanceof Number)
                _format = new DecimalFormat(_pattern);
            else if (res instanceof java.util.Date)
                _format = new SimpleDateFormat(_pattern);
            else
                return res.toString();
        }

        if (res instanceof Number)
            return _format.format(res);
        else if (res instanceof java.util.Date)
            return _format.format(res);
        else
            return res.toString();
    }


    private boolean executeComparison(String token) throws DataStoreException {
        boolean result = false;
        int type = -1;

        try {
            Object o1 = popValue(TYPE_ANY);
            Object o2 = popValue(TYPE_ANY);

            type = getType(o1);
            int type2 = getType(o2);

            //handle nulls
            if (o1 == null || o2 == null) {
                if (token.equals("==")) {
                    if (o1 == null && o2 == null)
                        return true;
                    else
                        return false;
                } else if (token.equals("!=")) {
                    if (o1 == null && o2 != null)
                        return true;
                    else if (o2 == null && o1 != null)
                        return true;
                    else
                        return false;
                } else
                    return false;
            }

            //make sure the data types match

            if (type != type2) {
                if (type == TYPE_STRING && (type2 == TYPE_DATETIME || type2 == TYPE_TIME || type2 == TYPE_DATE)) {
                    o2 = o2.toString();
                    type = TYPE_STRING;
                } else if (type2 == TYPE_STRING && (type == TYPE_DATETIME || type == TYPE_TIME || type == TYPE_DATE)) {
                    o1 = o1.toString();
                    type = TYPE_STRING;
                } else {
                    throw new DataStoreException("Error: datatypes do not match on comparison.");
                }
            }

            //execute the comparison
            if (type == TYPE_STRING) {
                String s1 = (String) o1;
                String s2 = (String) o2;

                if (token.equals("=="))
                    result = s1.equals(s2);
                else if (token.equals("!="))
                    result = !s1.equals(s2);
                else if (token.equals(">"))
                    result = (s1.compareTo(s2) < 0);
                else if (token.equals("<"))
                    result = (s1.compareTo(s2) > 0);
                else if (token.equals(">="))
                    result = (s1.compareTo(s2) <= 0);
                else if (token.equals("<="))
                    result = (s1.compareTo(s2) >= 0);
            } else if (type == TYPE_DATETIME) {
                java.sql.Timestamp d1 = (java.sql.Timestamp) o1;
                java.sql.Timestamp d2 = (java.sql.Timestamp) o2;

                if (token.equals("=="))
                    result = d1.equals(d2);
                else if (token.equals("!="))
                    result = !d1.equals(d2);
                else if (token.equals("<"))
                    result = d1.after(d2);
                else if (token.equals(">"))
                    result = d1.before(d2);
                else if (token.equals("<="))
                    result = (d1.after(d2) || d1.equals(d2));
                else if (token.equals(">="))
                    result = (d1.before(d2) || d1.equals(d2));

            } else if (type == TYPE_DATE) {
                java.sql.Date d1 = (java.sql.Date) o1;
                java.sql.Date d2 = (java.sql.Date) o2;

                if (token.equals("=="))
                    result = d1.equals(d2);
                else if (token.equals("!="))
                    result = !d1.equals(d2);
                else if (token.equals("<"))
                    result = d1.after(d2);
                else if (token.equals(">"))
                    result = d1.before(d2);
                else if (token.equals("<="))
                    result = (d1.after(d2) || d1.equals(d2));
                else if (token.equals(">="))
                    result = (d1.before(d2) || d1.equals(d2));

            } else if (type == TYPE_TIME) {
                java.sql.Time d1 = (java.sql.Time) o1;
                java.sql.Time d2 = (java.sql.Time) o2;

                if (token.equals("=="))
                    result = d1.equals(d2);
                else if (token.equals("!="))
                    result = !d1.equals(d2);
                else if (token.equals("<"))
                    result = d1.after(d2);
                else if (token.equals(">"))
                    result = d1.before(d2);
                else if (token.equals("<="))
                    result = (d1.after(d2) || d1.equals(d2));
                else if (token.equals(">="))
                    result = (d1.before(d2) || d1.equals(d2));

            } else if (type == TYPE_NUM) {
                double d1 = ((Number) o1).doubleValue();
                double d2 = ((Number) o2).doubleValue();

                if (token.equals("=="))
                    result = d1 == d2;
                else if (token.equals("!="))
                    result = d1 != d2;
                else if (token.equals(">"))
                    result = d2 > d1;
                else if (token.equals("<"))
                    result = d2 < d1;
                else if (token.equals(">="))
                    result = d2 >= d1;
                else if (token.equals("<="))
                    result = d2 <= d1;

            } else if (type == TYPE_BOOL) {
                boolean b1 = ((Boolean) o1).booleanValue();
                boolean b2 = ((Boolean) o2).booleanValue();

                if (token.equals("=="))
                    result = b1 == b2;
                else if (token.equals("!="))
                    result = b1 != b2;
                else
                    throw new DataStoreException("Only == and != are valid comparisons for boolean values");

            }
        } catch (DataStoreException e) {
            throw e;
        } catch (Exception e) {
            throw new DataStoreException(e.getMessage());
        }

        return result;
    }

    private Object executeFunction(DSDataStoreDescriptor desc, DSDataRow row, String token) throws DataStoreException {
        //to implement a new function
        //create a method for it and call below
        //add an entry to isFunction method
        //add an entry to getPopCount method

        Object result = null;

        try {

            int pos = token.lastIndexOf(".");
            Object o = null;

            String function = token.substring(pos + 1);
            String column = token.substring(0, pos);
            if (pos == 0) {
                Vector v = new Vector(_stackCount);
                for (int i = 0; i < _stackCount; i++)
                    v.addElement(_st.pop());
                if (_st.isEmpty())
                    o = v.elementAt(v.size() - 1);
                else
                    o = _st.pop();
                for (int i = v.size() - 1; i > -1; i--)
                    _st.push(v.elementAt(i));
            } else {
                int ndx = desc.getColumnIndex(column);
                if (ndx < 0)
                    throw (new DataStoreException("Error: Column " + column + " not found "));
                o = row.getData(ndx);
            }

            if (o == null) {
                for (int i = 0; i < getPopCount(function); i++)
                    popValue(TYPE_ANY);
                return null;
            }

            if (o instanceof String) {
                String s = (String) o;
                if (function.equals("substring"))
                    return functionSubstring(s);
                else if (function.equals("indexOf"))
                    return functionIndexOf(s);
                else if (function.equals("lastIndexOf"))
                    return functionLastIndexOf(s);
                else if (function.equals("toUpperCase"))
                    return functionToUpperCase(s);
                else if (function.equals("toLowerCase"))
                    return functionToLowerCase(s);
                else if (function.equals("charAt"))
                    return functionCharAt(s);
                else if (function.equals("startsWith"))
                    return functionStartsWith(s);
                else if (function.equals("endsWith"))
                    return functionEndsWith(s);
                else if (function.equals("length"))
                    return functionLength(s);
                else if (function.equals("trim"))
                    return functionTrim(s);
                else if (function.equals("urlEncode"))
                    return functionUrlEncode(s);
                else if (function.equals("escape"))
                    return functionEscape(s);
            } else if (o instanceof Number) {
                double d = ((Number) o).doubleValue();

                if (function.equals("sqrt"))
                    return functionSqrt(d);
                else if (function.equals("round"))
                    return functionRound(d);
                else if (function.equals("format"))
                    return functionFormat(o);
            }
            if (o instanceof java.util.Date) {
                java.util.Date d = (java.util.Date) o;

                if (function.equals("getHours"))
                    return functionGetHours(d);
                else if (function.equals("getYear"))
                    return functionGetYear(d);
                else if (function.equals("getMonth"))
                    return functionGetMonth(d);
                else if (function.equals("getDate"))
                    return functionGetDate(d);
                else if (function.equals("getDay"))
                    return functionGetDay(d);
                else if (function.equals("getMinutes"))
                    return functionGetMinutes(d);
                else if (function.equals("getSeconds"))
                    return functionGetSeconds(d);
                else if (function.equals("toDate"))
                    return functionToDate(d);
                else if (function.equals("toTime"))
                    return functionToTime(d);
                else if (function.equals("dateAdd"))
                    return functionDateAdd(d);
                else if (function.equals("substring"))
                    return functionSubstring(d.toString());
                else if (function.equals("indexOf"))
                    return functionIndexOf(d.toString());
                else if (function.equals("toUpperCase"))
                    return functionToUpperCase(d.toString());
                else if (function.equals("toLowerCase"))
                    return functionToLowerCase(d.toString());
                else if (function.equals("charAt"))
                    return functionCharAt(d.toString());
                else if (function.equals("startsWith"))
                    return functionStartsWith(d.toString());
                else if (function.equals("endsWith"))
                    return functionEndsWith(d.toString());
                else if (function.equals("length"))
                    return functionLength(d.toString());
                else if (function.equals("trim"))
                    return functionTrim(d.toString());
                else if (function.equals("format"))
                    return functionFormat(d);
            }
        } catch (DataStoreException e) {
            throw e;
        } catch (Exception e) {
            throw new DataStoreException(e.getMessage());
        }

        return result;
    }


    /**
     *
     * @param desc
     * @param row
     * @param token
     * @return
     * @throws DataStoreException
     */
    private Object executeOperation(DSDataStoreDescriptor desc, DSDataRow row, String token) throws DataStoreException {
        Object result = null;

        try {
            if (token.equals("+")) {
                Object o1 = popValue(TYPE_ANY);
                Object o2 = popValue(TYPE_ANY);

                if (o1 == null)
                    o1 = new String("");
                if (o2 == null)
                    o2 = new String("");

                if (getType(o1) == TYPE_STRING || getType(o2) == TYPE_STRING)
                    result = o2.toString() + o1.toString();
                else
                    result = new Double(((Number) o1).doubleValue() + ((Number) o2).doubleValue());
            } else if (token.equals("-")) {
                double v1 = ((Double) popValue(TYPE_NUM)).doubleValue();
                double v2 = ((Double) popValue(TYPE_NUM)).doubleValue();
                result = new Double(v2 - v1);
            } else if (token.equals("*")) {
                result = new Double(((Double) popValue(TYPE_NUM)).doubleValue() * ((Double) popValue(TYPE_NUM)).doubleValue());
            } else if (token.equals("/")) {
                double v1 = ((Double) popValue(TYPE_NUM)).doubleValue();
                double v2 = ((Double) popValue(TYPE_NUM)).doubleValue();
                result = new Double(v2 / v1);
            } else if (token.equals("^")) {
                double v1 = ((Double) popValue(TYPE_NUM)).doubleValue();
                double v2 = ((Double) popValue(TYPE_NUM)).doubleValue();
                result = new Double(Math.pow(v2, v1));
            } else if (token.equals("!")) {
                result = new Boolean(!((Boolean) popValue(TYPE_BOOL)).booleanValue());
            } else if (token.equals("&&")) {
                Boolean b1 = (Boolean) popValue(TYPE_BOOL);
                Boolean b2 = (Boolean) popValue(TYPE_BOOL);
                result = new Boolean(b1.booleanValue() && b2.booleanValue());
            } else if (token.equals("||")) {
                Boolean b1 = (Boolean) popValue(TYPE_BOOL);
                Boolean b2 = (Boolean) popValue(TYPE_BOOL);
                result = new Boolean(b1.booleanValue() || b2.booleanValue());
            } else if (token.equals("==") || token.equals(">") || token.equals("<") || token.equals(">=") || token.equals("<=") || token.equals("!=")) {
                result = new Boolean(executeComparison(token));
            } else if (token.equals("if")) {
                result = functionIf();
            } else if (isFunction(token)) {
                result = executeFunction(desc, row, token);
            }

        } catch (DataStoreException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private String functionCharAt(String s) throws DataStoreException {
        int i1 = ((Double) popValue(TYPE_NUM)).intValue();

        if ((i1 < 0) || i1 >= s.length())
            return null;

        char[] c = new char[1];
        c[0] = s.charAt(i1);
        return new String(c);
    }

//new method
    private String functionEscape(String value) {
        if (value == null)
            return null;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\'')
                buffer.append("\\'");
            else if (c == '\\')
                buffer.append("\\\\");
            else if (c == '"')
                buffer.append("&quot;");
            else if (c == ' ')
                buffer.append("%20");
            else
                buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private java.util.Date functionDateAdd(java.util.Date d) throws DataStoreException {

        Double arg = (Double) popValue(TYPE_NUM);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.DATE, arg.intValue());

        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DATE);

        return java.sql.Date.valueOf(year + "-" + month + "-" + day);


    }


    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private Boolean functionEndsWith(String s) throws DataStoreException {
        String s1 = ((String) popValue(TYPE_STRING));

        return new Boolean(s.endsWith(s1));
    }


    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetDate(java.util.Date d) throws DataStoreException {

        if (d instanceof java.sql.Time)
            throw (new DataStoreException("The getDate function cannot be used with Time values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.DATE));

    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetDay(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Time)
            throw (new DataStoreException("The getDay function cannot be used with Time values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.DAY_OF_WEEK));

//	return new Integer(d.getDay());

    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetHours(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Date)
            throw (new DataStoreException("The getHours function cannot be used with Date values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.HOUR_OF_DAY));

    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetMinutes(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Date)
            throw (new DataStoreException("The getMinutes function cannot be used with Date values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.MINUTE));

    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetMonth(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Time)
            throw (new DataStoreException("The getMonth function cannot be used with Time values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.MONTH));

    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetSeconds(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Date)
            throw (new DataStoreException("The getSeconds function cannot be used with Date values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.SECOND));


    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Integer functionGetYear(java.util.Date d) throws DataStoreException {
        if (d instanceof java.sql.Time)
            throw (new DataStoreException("The getYear function cannot be used with Time values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new Integer(cal.get(Calendar.YEAR));

    }

    /**
     *
     * @return
     * @throws DataStoreException
     */
    private Object functionIf() throws DataStoreException {
        Object falseValue = popValue(TYPE_ANY);
        Object trueValue = popValue(TYPE_ANY);
        Boolean exp = (Boolean) popValue(TYPE_BOOL);

        if (exp.booleanValue())
            return trueValue;
        else
            return falseValue;

    }

    private String functionFormat(Object o) throws DataStoreException {
        String pattern = ((String) popValue(TYPE_STRING));
        Format format = null;

        if (o instanceof java.util.Date) {
            format = new SimpleDateFormat(pattern);
            return format.format(o);
        } else if (o instanceof Number) {
            format = new DecimalFormat(pattern);
            return format.format(o);

        } else
            throw (new DataStoreException("The format function can only be used with Date or Numeric  values"));

    }

    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private Integer functionIndexOf(String s) throws DataStoreException {
        String s1 = (String) popValue(TYPE_STRING);
        if (s1 == null)
            return null;
        return new Integer(s.indexOf(s1));
    }

    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private Integer functionLastIndexOf(String s) throws DataStoreException {
        String s1 = (String) popValue(TYPE_STRING);
        if (s1 == null)
            return null;
        return new Integer(s.lastIndexOf(s1));
    }

    private Double functionLength(String s) {
        return new Double(s.length());
    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private Double functionRound(double d) throws DataStoreException {
        int places = ((Number) popValue(TYPE_NUM)).intValue();
        double ret;

        if (places > 0)
            places = 10 * places;
        else
            places = 1;

        ret = d * places;
        ret = Math.round(ret);
        ret = ret / places;

        return new Double(ret);
    }


    private Double functionSqrt(double d) {
        return new Double(Math.sqrt(d));
    }

    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private Boolean functionStartsWith(String s) throws DataStoreException {
        String s1 = ((String) popValue(TYPE_STRING));

        return new Boolean(s.startsWith(s1));
    }

    /**
     *
     * @param s
     * @return
     * @throws DataStoreException
     */
    private String functionSubstring(String s) throws DataStoreException {
        int i1 = ((Double) popValue(TYPE_NUM)).intValue();
        int i2 = ((Double) popValue(TYPE_NUM)).intValue();
        if ((i1 < 0) || i1 > s.length())
            return null;
        else if ((i2 < 0) || i2 > s.length())
            return null;
        else if (i1 <= i2)
            return null;
        else
            return (s.substring(i2, i1));
    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private java.sql.Date functionToDate(java.util.Date d) throws DataStoreException {
        if (!(d instanceof java.sql.Timestamp))
            throw (new DataStoreException("The toDate function can only be used with DateTime values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DATE);

        return java.sql.Date.valueOf(year + "-" + month + "-" + day);
    }

    private java.sql.Timestamp functionCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    private java.sql.Date functionCurrentDate() throws DataStoreException {
        return functionToDate(new Timestamp(System.currentTimeMillis()));
    }

    private String functionToLowerCase(String s) {
        return s.toLowerCase();
    }

    /**
     *
     * @param d
     * @return
     * @throws DataStoreException
     */
    private java.sql.Time functionToTime(java.util.Date d) throws DataStoreException {

        if (!(d instanceof java.sql.Timestamp))
            throw (new DataStoreException("The toTime function can only be used with DateTime values"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);

        return new java.sql.Time(cal.getTime().getTime());

    }


    private String functionToUpperCase(String s) {
        return s.toUpperCase();
    }


    private String functionTrim(String s) {
        return (s.trim());
    }


    private String functionUrlEncode(String s) {
        if (s == null)
            return null;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ')
                b.append('+');
            else if (c == '&')
                b.append("%26");
            else if (c == '?')
                b.append("%3F");
            else if (c == '#')
                b.append("%35");
            else
                b.append(c);
        }
        return b.toString();
    }

    /**
     * This method returns the original expression that will be evaluated.
     */
    public String getExpression() {
        return _expression;
    }

    /**
     * This method sets the display format for the result of the expression.
     * @see DataStore#setFormat(String,String).
     */
    public String getFormat() {
        return _pattern;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    private int getPopCount(String token) {
        if (token.equals("substring"))
            return 2;
        if (token.equals("indexOf"))
            return 1;
        if (token.equals("lastIndexOf"))
            return 1;
        if (token.equals("charAt"))
            return 1;
        if (token.equals("startsWith"))
            return 1;
        if (token.equals("endsWith"))
            return 1;
        if (token.equals("round"))
            return 1;
        if (token.equals("dateAdd"))
            return 1;

        return 0;
    }

    /**
     *
     * @return
     */
    private String getToken() {

        if (_pos >= _expression.length()) {
            _lastToken = null;
            return null;
        }

        StringBuffer ret = new StringBuffer();

        //remove white space
        char c = _expression.charAt(_pos);
        while (c == ' ') {
            _pos++;
            if (_pos >= _expression.length()) {
                _lastToken = null;
                return null;
            }
            c = _expression.charAt(_pos);
        }

        //get the token
        char lastChar;
        boolean quoteMode = false;
        char quoteModeStart = ' ';
        while (_pos < _expression.length()) {
            lastChar = c;
            c = _expression.charAt(_pos);
            if (c == '"' || c == '\'') {
                if (quoteMode) {
                    if (lastChar == '~')
                        ret.append(c);
                    else if (c != quoteModeStart)
                        ret.append(c);
                    else {
                        _pos++;
                        _lastToken = ret.toString() + c;
                        return _lastToken;
                    }
                } else {
                    quoteMode = true;
                    quoteModeStart = c;
                    ret.append(c);
                }
            } else if (c == '~') {
                if (!quoteMode || lastChar == '~')
                    ret.append("~");
            } else if (quoteMode)
                ret.append(c);
            else if (c == ' ') {
                _pos++;
                _lastToken = ret.toString();
                return _lastToken;
            } else if (_terminators.indexOf(c) > -1) {
                if (_pos < (_expression.length() - 1)) {
                    char next = _expression.charAt(_pos + 1);
                    if ((c == '=' || c == '|' || c == '&') && (next == c) && ret.length() == 0) {
                        ret.append(c);
                        _pos++;
                    } else if ((c == '!' || c == '>' || c == '<') && next == '=') {
                        if (ret.length() == 0) {
                            ret.append(c);
                            ret.append('=');
                            _lastToken = ret.toString();
                            _pos += 2;
                            return _lastToken;
                        } else {
                            _lastToken = ret.toString();
                            return _lastToken;
                        }
                    }
                }

                if (c == '-' && minusIsSign() && ret.length() == 0) {
                    ret.append(c);
                } else if (ret.length() == 0 || ((c == '=' || c == '|' || c == '&') && ret.length() == 1)) {
                    _pos++;
                    ret.append(c);
                    _lastToken = ret.toString();
                    return _lastToken;
                } else {
                    _lastToken = ret.toString();
                    return _lastToken;
                }
            } else
                ret.append(c);

            _pos++;
        }

        if (ret.length() == 0) {
            _lastToken = null;
            return null;
        } else {
            _lastToken = ret.toString();
            return _lastToken;
        }

    }

    /**
     * This method was created in VisualAge.
     * @return int
     * @param o java.lang.Object
     */
    private int getType(Object o) {
        if (o instanceof String)
            return TYPE_STRING;
        else if (o instanceof java.sql.Timestamp)
            return TYPE_DATETIME;
        else if (o instanceof java.sql.Time)
            return TYPE_TIME;
        else if (o instanceof java.sql.Date)
            return TYPE_DATE;
        else if (o instanceof Number)
            return TYPE_NUM;
        else if (o instanceof Boolean)
            return TYPE_BOOL;

        return -1;
    }

    /**
     *
     * @param exp
     * @throws EmptyStackException
     * @throws DataStoreException
     */
    private void intoPost(String exp) throws EmptyStackException, DataStoreException {
        //_refs.removeAllElements();
        //_refTypes.removeAllElements();

        _expression = exp;
        _pos = 0;
        _lastToken = null;

        Stack stack = new Stack();
        Vector inPost = new Vector();

        String token = getToken();
        while (token != null) {
            if (token.equals("("))
                stack.push(token);
            else if (token.equals(")")) {
                token = (String) stack.pop();
                while (!token.equals("(")) {
                    appendToVector(inPost, token);
                    token = (String) stack.pop();
                }
            } else if (token.equals(",")) {
                token = (String) stack.pop();
                while (!(token.equals("(") || token.equals(","))) {
                    appendToVector(inPost, token);
                    token = (String) stack.pop();
                }
                stack.push("(");
            } else {
                int ordOp = orderOfOperations(token);
                if (ordOp == 0) {
                    appendToVector(inPost, token);
                } else {
                    while ((!stack.isEmpty()) && orderOfOperations((String) stack.peek()) <= ordOp) {
                        appendToVector(inPost, (String) stack.pop());
                    }
                    stack.push(token);
                }
            }
            token = getToken();
        }

        while (!stack.isEmpty()) {
            token = (String) stack.pop();
            if (token.equals("(")) {
                throw new DataStoreException("Unmatched Parentheses Error");
            }
            appendToVector(inPost, token);
        }

        _inPost = new String[inPost.size()];
        inPost.copyInto(_inPost);
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isBoolean(String token) {
        String test = token.toLowerCase();
        if (test.equals("true") || test.equals("false")) {
            return true;
        }
        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isDate(String token) {
        if (token.length() > 0) {
            if ((token.charAt(0) == '"' || token.charAt(0) == '\'') && token.charAt(token.length() - 1) == token.charAt(0)) {
                try {
                    java.sql.Date.valueOf(token.substring(1, token.length() - 1));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isDateTime(String token) {
        if (token.length() > 0) {
            if ((token.charAt(0) == '"' || token.charAt(0) == '\'') && token.charAt(token.length() - 1) == token.charAt(0)) {
                try {
                    java.sql.Timestamp.valueOf(token.substring(1, token.length() - 1)); // unused
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    private boolean isFunction(String token) {
        if (token.endsWith(".substring"))
            return true;
        if (token.endsWith(".indexOf"))
            return true;
        if (token.endsWith(".lastIndexOf"))
            return true;
        if (token.endsWith(".toUpperCase"))
            return true;
        if (token.endsWith(".toLowerCase"))
            return true;
        if (token.endsWith(".charAt"))
            return true;
        if (token.endsWith(".startsWith"))
            return true;
        if (token.endsWith(".endsWith"))
            return true;
        if (token.endsWith(".trim"))
            return true;
        if (token.endsWith(".length"))
            return true;
        if (token.endsWith(".sqrt"))
            return true;
        if (token.endsWith(".round"))
            return true;
        if (token.endsWith(".getMonth"))
            return true;
        if (token.endsWith(".getDate"))
            return true;
        if (token.endsWith(".getDay"))
            return true;
        if (token.endsWith(".getYear"))
            return true;
        if (token.endsWith(".getHours"))
            return true;
        if (token.endsWith(".getMinutes"))
            return true;
        if (token.endsWith(".getSeconds"))
            return true;
        if (token.endsWith(".toDate"))
            return true;
        if (token.endsWith(".toTime"))
            return true;
        if (token.endsWith(".dateAdd"))
            return true;
        if (token.endsWith(".urlEncode"))
            return true;
        if (token.endsWith(".format"))
            return true;
        if (token.endsWith(".escape"))
            return true;
        //if (token.endsWith(".tempValue"))
        //    return true;

        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isNumber(String token) {
        String work = new String(token);
        int dotPos = token.indexOf(".");
        if (dotPos > -1)
            work = token.substring(0, dotPos) + token.substring(dotPos + 1);

        try {
            Integer.parseInt(work);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    private boolean isOperator(String token) {
        return (orderOfOperations(token) != 0);
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isString(String token) {
        if (token.length() > 0) {
            if ((token.charAt(0) == '"' || token.charAt(0) == '\'') && token.charAt(token.length() - 1) == token.charAt(0)) {
                if (isTime(token))
                    return false;
                else if (isDate(token))
                    return false;
                else if (isDateTime(token))
                    return false;
                else
                    return true;

            }
        }

        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     * @param token java.lang.String
     */
    public static boolean isTime(String token) {
        if (token.length() > 0) {
            if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {
                try {
                    java.sql.Time.valueOf(token.substring(1, token.length() - 1));// unused
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return boolean
     */
    private boolean minusIsSign() {
        if (_pos < (_expression.length() - 1)) {
            char nextChar = _expression.charAt(_pos + 1);
            if (Character.isDigit(nextChar) || nextChar == '.')
                if (_lastToken == null)
                    return true;
                else if (_lastToken.length() == 0)
                    return true;
                else {
                    char c = _lastToken.charAt(0);
                    if ("+*=(,><!".indexOf(c) > -1)
                        return true;
                    else if (_lastToken.equals("-"))
                        return true;
                }
        }

        return false;
    }

    /**
     * This method was created in VisualAge.
     * @return int
     * @param token java.lang.String
     */
    private int orderOfOperations(String token) {
        if (token.equals("^"))
            return 2;
        else if (token.equals("*") || token.equals("/"))
            return 3;
        else if (token.equals("+") || token.equals("-"))
            return 4;
        else if (isFunction(token) || token.equals("if") || token.equals("tempValue"))
            return 1;
        else if (token.equals("==") || token.equals(">") || token.equals("<") || token.equals(">=") || token.equals("<=") || token.equals("!="))
            return 6;
        else if (token.equals("!") || token.equals("&&") || token.equals("||"))
            return 7;
        else if (token.equals("(") || token.equals(")") || token.equals(","))
            return 8;
        else
            return 0;
    }

    /**
     *
     * @param type
     * @return
     * @throws DataStoreException
     */
    private Object popValue(int type) throws DataStoreException {
        if (_st.isEmpty())
            throw new DataStoreException("Expecting value evaluating expression");

        _stackCount--;

        Object o = _st.pop();
        if (o == null)
            return o;

        if (type == TYPE_NUM)
            if (!(o instanceof Number))
                throw new DataStoreException("Expecting Numeric Value");
            else if (!(o instanceof Double)) {
                o = new Double(((Number) o).doubleValue());
            }

        if (type == TYPE_DATETIME)
            if (!(o instanceof java.sql.Timestamp))
                throw new DataStoreException("Expecting DateTime Value");

        if (type == TYPE_DATE)
            if (!(o instanceof java.sql.Date))
                throw new DataStoreException("Expecting Date Value");

        if (type == TYPE_TIME)
            if (!(o instanceof java.sql.Time))
                throw new DataStoreException("Expecting Time Value");


        if (type == TYPE_BOOL)
            if (!(o instanceof Boolean))
                throw new DataStoreException("Expecting Boolean Value");

        if (type == TYPE_STRING)
            if (o instanceof java.sql.Timestamp)
                o = o.toString();
            else if (!(o instanceof String))
                throw new DataStoreException("Expecting String Value");

        return o;
    }

    /**
     * This method sets the display format for the result of the expression.
     * @see DataStore#setFormat(String,String).
     */
    public void setFormat(String format) {
        _pattern = format;
    }

    /**
     * This method gets the DataStoreExpression being used for this DataStoreEvaluator.
     * @return DataStoreExpression
     */
    public DataStoreExpression getDataStoreExpression() {
        return _exp;
    }

    /**
     * Returns the datastore
     */
    public DataStoreBuffer getDataStore() {
        return _ds;
    }
}
