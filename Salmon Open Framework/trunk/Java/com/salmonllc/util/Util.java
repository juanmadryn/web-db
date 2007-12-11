package com.salmonllc.util;


/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/util/Util.java $
//$Author: Dan $
//$Revision: 46 $
//$Modtime: 11/08/04 11:36a $
/////////////////////////
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.salmonllc.html.*;
import com.salmonllc.sql.DataStore;


/**
 * This class is used to hold general helper methods
 */
public class Util
{

    private static int NUM_MILLIS_IN_DAY = (1000 * 60 * 60 * 24);

    public static java.sql.Date getDateObject(int iYear, int iMonth, int iDay, int iHour, int iMinute)
    {
        GregorianCalendar greCal = new GregorianCalendar();

        if (iYear > 0)
        {
            greCal.set(Calendar.YEAR, iYear);
        }

        /*
           Claudio Pi (4-01-2003) Commented out since
           Util.getDateObject(2003,1-1,0,1,1).toString() returned 2003-04-01 instead of 2003-01-01

           srufle: greCal.MONTH ranges from 0 to 11
		   From  java.util.Calendar Javadoc
		   public final void set(int year,
                      int month,
                      int date)
           month - the value used to set the MONTH time field. Month value is 0-based. e.g., 0 for January.
         */
            greCal.set(Calendar.MONTH, iMonth);

        if (iDay > 0)
        {
            greCal.set(Calendar.DAY_OF_MONTH, iDay);
        }

        if (iHour > 0)
        {
            greCal.set(Calendar.HOUR_OF_DAY, iHour);
        }

        if (iMinute > 0)
        {
            greCal.set(Calendar.MINUTE, iMinute);
        }

        java.sql.Date sqlDate = new java.sql.Date(greCal.getTime().getTime());

        return sqlDate;
    }

    /**
     * This method checks to see if the string passed into it is a double value.  It returns true if the string parses to a double.
     *
     * @param s java.lang.String
     *
     * @return boolean If the String is a double value
     */
    public static boolean isDouble(String s)
    {
        boolean ret = false;

        if (!isNumeric(s))
        {
            try
            {
                Double.parseDouble(s);
                ret = true;
            }
            catch (Exception de)
            {
                ret = false;
            }
        }
        else
        {
            ret = true;
        }

        return ret;
    }

    /**
     * Checks a String to see if it  is empty ""
     *
     * @param objToCheck - object to check
     *
     * @return boolean true if it is empty
     */
    public static boolean isEmpty(Object objToCheck)
    {
       try {
        if (objToCheck.toString().trim().equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
       }
         catch (Exception e)
         {
           return true;
       }
    }

    /**
     * This method checks to see if the string passed into it contains any letters or digits.  It returns a true or false value whether or not the string has any characters in it.
     *
     * @param s java.lang.String
     *
     * @return boolean
     */
    public static boolean isFilled(String s)
    {
        return ((s != null) && !s.trim().equals(""));
    }

    /**
     * This method checks to see if the string passed into it is an integer value.  It returns true if the string parses to an integer.
     *
     * @param s java.lang.String
     *
     * @return boolean If the String is an integer value
     */
    public static boolean isInteger(String s)
    {
        boolean ret = false;

        if (!isNumeric(s))
        {
            try
            {
                Integer.parseInt(s);
                ret = true;
            }
            catch (Exception ie)
            {
                ret = false;
            }
        }
        else
        {
            ret = true;
        }

        return ret;
    }

    /**
     * Checks an Object to see if it is null
     *
     * @param objToCheck - object to check
     *
     * @return boolean true if it is null
     */
    public static boolean isNull(Object objToCheck)
    {
        if (objToCheck == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This method checks to see if the string passed into it contains all numeric digits.  It returns true if the string contains only digits.
     *
     * @param s java.lang.String
     *
     * @return boolean If the String is numeric
     */
    public static boolean isNumeric(String s)
    {
        boolean ret = false;

        if (s != null)
        {
            int length = s.length();

            for (int i = 0; i < length; i++)
            {
                if (!Character.isDigit(s.charAt(i)))
                {
                    ret = false;

                    break;
                }
                else
                {
                    ret = true;
                }
            }
        }

        return ret;
    }

    /**
     * This method sets the width of the passed in HtmlDataTable to 100 percent
     *
     * @param dataTable - HtmlDataTable to be sized
     */
    public static void setTo100Percent(HtmlDataTable dataTable)
    {
        dataTable.setSizeOption(HtmlTable.SIZE_PERCENT);
        dataTable.setWidth(100);
    }

    /**
     * This method sets the width of the passed in HtmlDisplayBox to 100 percent
     *
     * @param dispBox - HtmlDisplayBox to be sized
     */
    public static void setTo100Percent(HtmlDisplayBox dispBox)
    {
        dispBox.setSizeOption(HtmlTable.SIZE_PERCENT);
        dispBox.setWidth(100);
    }

    /**
     * This method sets the width of the passed in HtmlDisplayBox to 100 percent
     *
     * @param table - HtmlTable to be sized
     */
    public static void setTo100Percent(HtmlTable table)
    {
        table.setSizeOption(HtmlTable.SIZE_PERCENT);
        table.setWidth(100);
    }

    /**
     * This method was created in VisualAge.
     *
     * @param filter java.lang.String
     * @param add java.lang.String
     *
     * @return java.lang.String
     */
    public static String addAndFilter(String filter, String add)
    {
        if (isNull(filter) || isEmpty(filter))
        {
            return add;
        }
        else
        {
            return filter + " AND " + add;
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @param filter java.lang.String
     * @param add java.lang.String
     */
    public static void addAndFilter(StringBuffer filter, String add)
    {
        //sr 12 -21-2000 if (filter.toString().equals(""))
        if (isNull(filter.toString()) || isEmpty(filter.toString()))
        {
            filter.append(add);
        }
        else
        {
            filter.append(" AND ").append(add);
        }
    }

    /**
     * This method returns what you passed in as a Html Comment.
     *
     * @param commentStr - String that will be wrapped in an html comment
     *
     * @return String
     */
    public static String addHtmlComment(String commentStr)
    {
        return ("<!-- " + commentStr + " -->");
    }

    /**
     * This method was created in VisualAge.
     *
     * @param filter java.lang.String
     * @param add java.lang.String
     *
     * @return java.lang.String
     */
    public static String addOrFilter(String filter, String add)
    {
        // sr 12-21-2000 if (filter == null || filter.equals(""))
        if (isNull(filter) || isEmpty(filter))
        {
            return add;
        }
        else
        {
            return filter + " OR " + add;
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @param filter java.lang.String
     * @param add java.lang.String
     */
    public static void addOrFilter(StringBuffer filter, String add)
    {
        //sr 12 -21-2000 if (filter.toString().equals(""))
        if (isNull(filter.toString()) || isEmpty(filter.toString()))
        {
            filter.append(add);
        }
        else
        {
            filter.append(" OR ").append(add);
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @param paramString java.lang.String
     * @param add java.lang.String
     * @param type int type of param
     */
    public static void addSPParameter(StringBuffer paramString, String add, int type)
    {
        String commaChar = "";

        if (!paramString.toString().equals(""))
        {
            commaChar = ",";
        }

        switch (type)
        {
            case 0:
                paramString.append(commaChar).append("'").append(add).append("'");

                break;

            default:
                paramString.append(commaChar).append(add);

                break;
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @param clause java.lang.String
     * @param add java.lang.String
     * @param type int type of param
     *
     * @return java.lang.String
     */
    public static String addToINClause(String clause, String add, int type)
    {
        String commaChar = "";

        if (!isEmpty(clause))
        {
            commaChar = ",";
        }

        switch (type)
        {
            case DataStore.DATATYPE_STRING:
                return clause + commaChar + "'" + add + "'";

            default:
                return clause + commaChar + add;
        }
    }

    /**
     * This method will will automatically set a property value on the specified component. This can be used for example to make a component invisible or visible depending on the value in the datastore for a particular row: ex: addPropertyExpression(comp,"visible","bucket==1") will call the setVisible method on the component comp passing the results of the expression.
     *
     * @param comp The component to set the property for
     * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
     * @param value - The value being passed to the method
     *
     * @exception NoSuchMethodException The exception description.
     */
    public static void executeMethod(Object comp, String propertyName, Object value)
                              throws NoSuchMethodException
    {
        Class    c       = comp.getClass();
        Method   m[]     = c.getMethods();
        Method   exe     = null;
        String   name    = "set" + propertyName;
        Class    parms[] = null;

        for (int i = 0; i < m.length; i++)
        {
            if (name.equalsIgnoreCase(m[i].getName()))
            {
                parms = m[i].getParameterTypes();

                if (parms.length == 1)
                {
                    exe = m[i];

                    break;
                }
            }
        }

        if (exe == null)
        {
            throw new NoSuchMethodException("Couldn't find a set method for property:" + propertyName);
        }

        executeReflectedMethod(comp, exe, value);
    }

    /**
     * This method is used to execute set methods indirectly.
     *
     * @param comp DOCUMENT ME!
     * @param meth DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public static void executeReflectedMethod(Object comp, Method meth, Object value)
    {
        try
        {
            Object   res[]   = null;
            Class    parms[] = null;
            String   name    = null;
            Number   n[]     = null;

            res = new Object[1];

            res[0] = value;

            parms = meth.getParameterTypes();
            name  = parms[0].getName().toUpperCase();

            if (res[0] != null)
            {
                if (name.startsWith("BOOLEAN") && res[0] instanceof Boolean)
                {
                    meth.invoke(comp, res);
                }
                else if (name.endsWith("STRING") && res[0] instanceof String)
                {
                    meth.invoke(comp, res);
                }
                else if (res[0] instanceof Number)
                {
                    n = new Number[1];

                    if (name.startsWith("INT"))
                    {
                        n[0] = new Integer(((Number) res[0]).intValue());
                    }
                    else if (name.startsWith("LONG"))
                    {
                        n[0] = new Long(((Number) res[0]).longValue());
                    }
                    else if (name.startsWith("FLOAT"))
                    {
                        n[0] = new Float(((Number) res[0]).floatValue());
                    }
                    else if (name.startsWith("DOUBLE"))
                    {
                        n[0] = new Double(((Number) res[0]).doubleValue());
                    }
                    else if (name.startsWith("BYTE"))
                    {
                        n[0] = new Byte(((Number) res[0]).byteValue());
                    }
                    else if (name.startsWith("SHORT"))
                    {
                        n[0] = new Short(((Number) res[0]).shortValue());
                    }
                    else
                    {
                        return;
                    }

                    meth.invoke(comp, n);
                }
            }
            else
            {
                if (name.startsWith("BOOLEAN"))
                {
                    Boolean b = new Boolean(false);
                    res[0] = b;
                    meth.invoke(comp, res);
                }
                else if (name.endsWith("STRING"))
                {
                    String s = null;
                    res[0] = s;
                    meth.invoke(comp, res);
                }
                else
                {
                    n = new Number[1];

                    if (name.startsWith("INT"))
                    {
                        n[0] = new Integer((int) 0);
                    }
                    else if (name.startsWith("LONG"))
                    {
                        n[0] = new Long((long) 0);
                    }
                    else if (name.startsWith("FLOAT"))
                    {
                        n[0] = new Float((float) 0);
                    }
                    else if (name.startsWith("DOUBLE"))
                    {
                        n[0] = new Double((double) 0);
                    }
                    else if (name.startsWith("BYTE"))
                    {
                        n[0] = new Byte((byte) 0);
                    }
                    else if (name.startsWith("SHORT"))
                    {
                        n[0] = new Short((short) 0);
                    }
                    else
                    {
                        return;
                    }

                    meth.invoke(comp, n);
                }
            }
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("Execute Property Method", e, null);
        }
    }

    /**
     * This method returns a HTML script that opens the print dialog box of the browser.
     *
     * @param submitBtn - Button that will get the script added to it
     * @param p - Page
     *
     * @return HtmlScript
     */
    public HtmlScript getPrintScript(HtmlSubmitImage submitBtn, HtmlPage p)
    {
        HtmlScript script = new HtmlScript("", "JavaScript1.1", p);

        String     sScript = " function PrintScript() { \n";
        sScript += "    if (!(window.print)) { \n";
        sScript += "        alert( \"Please select the Print option from File Menu\"); \n";
        sScript += "     } \n";
        sScript += "    else{ \n";
        sScript += "        window.print(); \n";
        sScript += "    } \n";
        sScript += "} \n";

        script.setScript(sScript);

        if (submitBtn != null)
        {
            submitBtn.setOnClick("PrintScript()");
        }

        return script;
    }

	/**
	 * Check an int and returns if it resolves to true boolean value. numbers greater then 0 are treated as true
	 *
	 * @param intToCheck - Int to check
	 *
	 * @return boolean
	 */
	public static boolean isTrue(int intToCheck)
	{
		boolean ret = false;
		
		if ( intToCheck <=0)
		{
			ret = false;
		}else
		{
			ret = true;
		}

		return ret;
		
	}
	/**
	 * Check a string and returns if it resolves to true boolean value. numbers greater then 0 are treated as true
	 *
	 * @param strToCheck - String to check
	 *
	 * @return boolean
	 */
	public static boolean isTrue(String strToCheck)
	{
        if (strToCheck == null)
        {
            return false;
        }

        strToCheck = strToCheck.toLowerCase();

        if (Util.isNumeric(strToCheck))
        {
            /**
             * numbers greater then 0 are treated as true
             */
            if (Integer.parseInt(strToCheck) > 0)
            {
                return true;
            }
        }
        else if (strToCheck.equals("t") || strToCheck.equals("true") || strToCheck.equals("y") || strToCheck.equals("yes"))
        {
            return true;
        }

        return false;
    }

    /**
     * This method finds the nth occurrence of a string within another string and return the location within the source string
     *
     * @param searchStr is the string to search for.
     * @param source is the string to be searched.
     * @param numOfOcurrence is the number of occurrence we are looking for.
     *
     * @return int index of occurence
     */
    public static int indexOfOccurence(String searchStr, String source, int numOfOcurrence)
    {
        int index = -1;

        // Find the nth occurrence of a string within another string
        // and return the location within the source string
        // The current position within the soruce string
        int currPos = 0;

        // The current number of matches found
        int mathesFound = 0;

        if (numOfOcurrence == 0)
        {
            return index; // Return -1 if the number of occurrences is 0
        }

        if (source.indexOf(searchStr) == -1)
        {
            return index; // Return -1 if either string is Null
        }

        if (stringCount(source, searchStr) < numOfOcurrence)
        {
            return index; // Return -1 if there is not the specified number of occurences of the search string in the source string
        }

        while (mathesFound < numOfOcurrence)
        {
            mathesFound = mathesFound + 1;
            currPos     = source.indexOf(searchStr, currPos) + searchStr.length();
        }

        index = currPos - searchStr.length();

        return index;
    }

    /**
     * Returns true if class2 is a subclass of class1
     *
     * @param class1
     * @param class2
     *
     * @return
     */
    public static boolean instanceOf(Class class1, Class class2)
    {
        if (class1.isPrimitive() || class2.isPrimitive())
        {
            return (class1 == class2);
        }

        while (class1 != Object.class)
        {
            if (class1 == class2)
            {
                return true;
            }

            class1 = class1.getSuperclass();

            if (class1 == null)
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Maps a string to a datastore datatype constant
     *
     * @param val
     *
     * @return int value that is  DataStore.DATATYPE_
     */
    public static int javaClassToDataStoreType(String val)
    {
        // String
        if (val.indexOf("String") > -1)
        {
            return DataStore.DATATYPE_STRING;
        }

        // Integer
        if (val.indexOf("Integer") > -1)
        {
            return DataStore.DATATYPE_INT;
        }

        // Date
        if (val.indexOf("Date") > -1)
        {
            return DataStore.DATATYPE_DATE;
        }

        // DateTime
        if (val.indexOf("DateTime") > -1)
        {
            return DataStore.DATATYPE_DATETIME;
        }

        // Double
        if (val.indexOf("Double") > -1)
        {
            return DataStore.DATATYPE_DOUBLE;
        }

        // Time
        if (val.indexOf("Time") > -1)
        {
            return DataStore.DATATYPE_TIME;
        }

        // Short
        if (val.indexOf("Short") > -1)
        {
            return DataStore.DATATYPE_SHORT;
        }

        // Long
        if (val.indexOf("Long") > -1)
        {
            return DataStore.DATATYPE_LONG;
        }

        // Float
        if (val.indexOf("Float") > -1)
        {
            return DataStore.DATATYPE_FLOAT;
        }

        // ByteArray
        if (val.indexOf("ByteArray") > -1)
        {
            return DataStore.DATATYPE_BYTEARRAY;
        }

        return -1;
    }

    /**
     * This method checks a column to see if it has been filled. If it has not been filled it with a safe value.
     *
     * @param ds DataStore the datastore to use during checking
     * @param row int
     * @param colName String
     *
     * @throws Exception - if column is not found
     */
    public static void makeSureNotNull(DataStore ds, int row, String colName)
                                throws Exception
    {
        makeSureNotNull(ds, row, colName, null);
    }

    /**
     * This method checks a column to see if it has been filled. If it has not been filled it with a safe value.
     *
     * @param ds DataStore the datastore to use during checking
     * @param row - row to check
     * @param colName - column to check
     * @param defaultVal - Object You can specify what your defaultvalue should be.
     *
     * @throws Exception - if column is not found
     */
    public static void makeSureNotNull(DataStore ds, int row, String colName, Object defaultVal)
                                throws Exception
    {
        try
        {
            Object oTest   = ds.getAny(row, colName);
            int    colType = ds.getColumnDataType(colName);

            if (oTest == null)
            {
                switch (colType)
                {
                    case DataStore.DATATYPE_BYTEARRAY:

                        if (defaultVal == null)
                        {
                            ds.setByteArray(row, colName, new byte[1]);
                        }
                        else
                        {
                            if (defaultVal instanceof byte[])
                            {
                                ds.setByteArray(row, colName, (byte[]) defaultVal);
                            }
                            else
                            {
                                throw new Exception("Expecting Byte array,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_SHORT:

                        if (defaultVal == null)
                        {
                            ds.setShort(row, colName, (short) 0);
                        }
                        else
                        {
                            if (defaultVal instanceof Short)
                            {
                                ds.setShort(row, colName, ((Short) defaultVal).shortValue());
                            }
                            else
                            {
                                throw new Exception("Expecting short value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_INT:

                        if (defaultVal == null)
                        {
                            ds.setInt(row, colName, 0);
                        }
                        else
                        {
                            if (defaultVal instanceof Integer)
                            {
                                ds.setInt(row, colName, ((Integer) defaultVal).intValue());
                            }
                            else
                            {
                                throw new Exception("Expecting int value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_LONG:

                        if (defaultVal == null)
                        {
                            ds.setLong(row, colName, 0);
                        }
                        else
                        {
                            if (defaultVal instanceof Long)
                            {
                                ds.setLong(row, colName, ((Long) defaultVal).longValue());
                            }
                            else
                            {
                                throw new Exception("Expecting long value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_FLOAT:

                        if (defaultVal == null)
                        {
                            ds.setFloat(row, colName, 0.0f);
                        }
                        else
                        {
                            if (defaultVal instanceof Float)
                            {
                                ds.setFloat(row, colName, ((Float) defaultVal).floatValue());
                            }
                            else
                            {
                                throw new Exception("Expecting float value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_DOUBLE:

                        if (defaultVal == null)
                        {
                            ds.setDouble(row, colName, 0.0);
                        }
                        else
                        {
                            if (defaultVal instanceof Double)
                            {
                                ds.setDouble(row, colName, ((Double) defaultVal).doubleValue());
                            }
                            else
                            {
                                throw new Exception("Expecting double value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_STRING:

                        if (defaultVal == null)
                        {
                            ds.setString(row, colName, " ");
                        }
                        else
                        {
                            if (defaultVal instanceof String)
                            {
                                ds.setString(row, colName, ((String) defaultVal).toString());
                            }
                            else
                            {
                                throw new Exception("Expecting String value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_DATE:

                        if (defaultVal == null)
                        {
                            ds.setDate(row, colName, Util.getDateObject(0, 0, 0, 0, 0));
                        }
                        else
                        {
                            if (defaultVal instanceof java.sql.Date)
                            {
                                ds.setDate(row, colName, (java.sql.Date) defaultVal);
                            }
                            else
                            {
                                throw new Exception("Expecting java.sql.Date value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_DATETIME:

                        if (defaultVal == null)
                        {
                            ds.setDateTime(row, colName, new Timestamp(0));
                        }
                        else
                        {
                            if (defaultVal instanceof java.sql.Timestamp)
                            {
                                ds.setDateTime(row, colName, (java.sql.Timestamp) defaultVal);
                            }
                            else
                            {
                                throw new Exception("Expecting  java.sql.Timestamp value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;

                    case DataStore.DATATYPE_TIME:

                        if (defaultVal == null)
                        {
                            ds.setTime(row, colName, new Time(0));
                        }
                        else
                        {
                            if (defaultVal instanceof java.sql.Time)
                            {
                                ds.setTime(row, colName, (java.sql.Time) defaultVal);
                            }
                            else
                            {
                                throw new Exception("Expecting ava.sql.Time value,You passed the wrong type aurgument for this coulmn type.");
                            }
                        }

                        break;
                }
            }
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("makeSureNotNull", e, null);
            throw e;
        }
    }

    /**
     * This method checks a column to see if it has been filled. If it has not been filled it with a safe value.
     *
     * @param ds DataStore the datastore to use during checking
     * @param colName - column to check
     *
     * @throws Exception - if column is not found
     */
    public static void makeSureNotNull(DataStore ds, String colName)
                                throws Exception
    {
        makeSureNotNull(ds, 0, colName);
    }



    /**
     * This function is used to pad a string to the specified length with a specific character and return this created string.
     * If passed String is greater than iPadLength than the string is chopped to that length.
     * @param sPassed String -  the string to be padded.
     * @param iPadLength int -  the length of the resulting string.
     * @param cPadChar char -  the character to pad the string with.
     * @return String - the padded string of the operation.
     */
	public final static String padString(String sPassed,int iPadLength,char cPadChar)
	  {
	    StringBuffer sbReturn=new StringBuffer(sPassed);
	     if (sbReturn.length()<iPadLength) {
	       int iPadding=iPadLength-sbReturn.length() ;
	       for (int i=0;i<iPadding;i++)
	          sbReturn.append(cPadChar);
	     }
		 if (sbReturn.length()>iPadLength)
		   sbReturn.setLength(iPadLength);
	   return sbReturn.toString();
	 }

    /**
     * This function is used to pad a string in the front to the specified length with a specific character and return this created string.
     * If passed String is greater than iPadLength than the string is chopped to that length.
     * @param sPassed String -  the string to be padded.
     * @param iPadLength int -  the length of the resulting string.
     * @param cPadChar char -  the character to pad the string with.
     * @return String - the padded string of the operation.
     */
	public final static String padStringInFront(String sPassed,int iPadLength,char cPadChar)
	  {
	    StringBuffer sbReturn=new StringBuffer(sPassed);
	     if (sbReturn.length()<iPadLength) {
	       int iPadding=iPadLength-sbReturn.length() ;
	       for (int i=0;i<iPadding;i++)
	          sbReturn.insert(0,cPadChar);
	     }
		 if (sbReturn.length()>iPadLength)
		   sbReturn.setLength(iPadLength);
	   return sbReturn.toString();
	 }


	/**
	   * This method replaces strings within another string with a third specified string Usage: replaceString(StringBuffer soucre, String searchStr, String replaceStr)
	   * replaces all occurences
	   * @param source is the string to be searched.
	   * @param searchStr is the string to search for.
	   * @param replaceStr is the string that replaces searchStr in soucre.  If omitted, replacement is the empty string.
	   *
	   * @return DOCUMENT ME!
	   */
	public static String replaceString(String source, String searchStr, String replaceStr)
	  {
		return replaceString( source,  searchStr,  replaceStr,  -1,  -1);
	  }
    /**
     * This method replaces strings within another string with a third specified string Usage: replaceString(StringBuffer soucre, String searchStr, String replaceStr, int startOcurrence, int numOfOccurences)
     *
     * @param source is the string to be searched.
     * @param searchStr is the string to search for.
     * @param replaceStr is the string that replaces searchStr in soucre.  If omitted, replacement is the empty string.
     * @param startOcurrence is the first occurrence of searchStr that is replaced.  If omitted, the first occurrence is used.
     * @param numOfOccurences is the number of occurrences of searchStr that are replaced.  If omitted, all occurrences are replaced.
     *
     * @return DOCUMENT ME!
     */
	public static String replaceString(String source, String searchStr, String replaceStr, int startOcurrence, int numOfOccurences)
	{
        if ((source == null) || (searchStr == null) || (replaceStr == null))
        {
            return null;
        }

        // If the search string does not appear in the source string then return the original source string
        int totalOccurences = Util.stringCount(source.toString(), searchStr);

        if (totalOccurences == 0)
        {
            return source;
        }

        if (startOcurrence < 1)
        {
            startOcurrence = 1;
        }

        // If lngStartOccurrence is greater than the total number of occurrences then
        // start with the last occurrence by default
        if (startOcurrence > totalOccurences)
        {
            startOcurrence = totalOccurences;
        }

        // If numOfOccurences is either omitted, less than one, or greater than
        // the total number of occurrences then replace all remaining occurrences by default
        if ((numOfOccurences < 1) || (totalOccurences < numOfOccurences) || ((numOfOccurences + startOcurrence) > totalOccurences))
        {
            numOfOccurences = totalOccurences - startOcurrence + 1;
        }

        // Step through all specified occurrences of the search string within the soruce
        // string, replacing the search string with the replacement string for each
        // required occurrence
        int          currPos       = indexOfOccurence(searchStr, source.toString(), startOcurrence);
        StringBuffer work          = new StringBuffer(source.substring(0, currPos));
        int          currOcurrence = 0;
        int          nextPos       = 0;

        for (currOcurrence = startOcurrence; currOcurrence < ((startOcurrence + numOfOccurences) - 1); currOcurrence++)
        {
            nextPos = source.indexOf(searchStr, currPos + 1);
            work.append(replaceStr);

            String temp = source.substring(currPos + searchStr.length(), nextPos);
            work.append(temp);

            // old		currPos = nextPos + searchStr.length();
            currPos = nextPos;
        }

        work.append(replaceStr);
        work.append(source.substring(currPos + searchStr.length()));

        return work.toString();

        //// Finally, append the remainder of the source string to the result
        //StrTran = StrTran & Mid(strSourceString, lngCurrentPosition, Len(strSourceString) - lngCurrentPosition + 1) java.util.Vector indexVec = new java.util.Vector();
        //int searchStrLen = searchStr.length();
        //int searchStrPos = -1;
        //String work = sb.toString();
        //for (int i = 0; i < source.length(); i++)
        //{
        //searchStrPos = work.indexOf(searchStr, i);
        //if (searchStrPos > -1)
        //{
        //i = searchStrPos;
        //indexVec.add(new Integer(searchStrPos));
        //} else
        //{
        //break;
        //}
        //}
        //for (int i = indexVec.size() - 1; i >= 0; i--)
        //{
        //int startPos = ((Integer) indexVec.get(i)).intValue();
        //sb.replace(startPos, startPos + searchStrLen, serverUrl);
        //}
    }

    /**
     * This method Counts the number of occurences of one string within another
     *
     * @param source - String to be searched in
     * @param searchStr - String that is tring to be found
     *
     * @return number of occurences
     */
    public static int stringCount(String source, String searchStr)
    {
        int count        = 0;
        int searchStrPos = -1;

        // check to make sure all strings involved are not null.
        if ((source == null) || (searchStr == null))
        {
            return count;
        }

        for (int i = 0; i < source.length(); i++)
        {
            searchStrPos = source.indexOf(searchStr, i);

            if (searchStrPos > -1)
            {
                count++;
                i = searchStrPos;
            }
            else
            {
                break;
            }
        }

        return count;
    }

    /**
     * Attempts to convert a string into its boolean equivalent; otherwise returns the true
     *
     * @param val - String to be converted
     *
     * @return boolean
     */
    public static boolean stringToBoolean(String val)
    {
        return stringToBoolean(val, true);
    }

    /**
     * Attempts to convert a string into its boolean equivalent; otherwise returns the default
     *
     * @param val java.lang.String
     * @param def boolean
     *
     * @return boolean
     */
    public static boolean stringToBoolean(String val, boolean def)
    {
        if (val == null)
        {
            return def;
        }

        String test = val.toUpperCase();

        if (test.equals("1") || test.equals("T") || test.equals("Y") || test.equals("TRUE") || test.equals("YES"))
        {
            return true;
        }
        else if (test.equals("0") || test.equals("F") || test.equals("N") || test.equals("FALSE") || test.equals("NO"))
        {
            return false;
        }
        else
        {
            return def;
        }
    }

    /**
     * This method will return the Color-object equivalent of a string
     *
     * @param s - colr passed as a string.
     *
     * @return Color
     */
    public static Color stringToColor(String s)
    {
        if (s == null)
        {
            return null;
        }

        if (s.startsWith("#"))
        {
            return Color.decode(s);
        }
        else
        {
            s = s.toUpperCase();

            if (s.equals("WHITE"))
            {
                return Color.white;
            }
            else if (s.equals("LIGHTGRAY"))
            {
                return Color.lightGray;
            }
            else if (s.equals("GRAY"))
            {
                return Color.gray;
            }
            else if (s.equals("DARKGRAY"))
            {
                return Color.darkGray;
            }
            else if (s.equals("BLACK"))
            {
                return Color.black;
            }
            else if (s.equals("RED"))
            {
                return Color.red;
            }
            else if (s.equals("PINK"))
            {
                return Color.pink;
            }
            else if (s.equals("ORANGE"))
            {
                return Color.orange;
            }
            else if (s.equals("YELLOW"))
            {
                return Color.yellow;
            }
            else if (s.equals("YELLOW"))
            {
                return Color.yellow;
            }
            else if (s.equals("MAGENTA"))
            {
                return Color.magenta;
            }
            else if (s.equals("CYAN"))
            {
                return Color.cyan;
            }
            else if (s.equals("BLUE"))
            {
                return Color.blue;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Converts a string to an int or returns -1 if it can't
     *
     * @param val - value to convert
     *
     * @return int
     */
    public static int stringToInt(String val)
    {
        if (val == null)
        {
            return -1;
        }

        try
        {
            return Integer.parseInt(val.trim());
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    /**
     * This method returns a new string containing only letters and numeric digits in the original string.
     *
     * @param s java.lang.String string to be stripped of all characters other than letters or digits
     *
     * @return java.lang.String the new string containing only letters or digits
     */
    public static String stripChars(String s)
    {
        char  sourceArray[] = s.toCharArray();
        int   length      = sourceArray.length;
        char  destArray[] = new char[length];

        int   j = 0;

        for (int i = 0; i < length; i++)
        {
            if (Character.isLetterOrDigit(sourceArray[i]))
            {
                destArray[j++] = sourceArray[i];
            }
        }

        return (j <= 0) ? null : (new String(destArray)).trim();
    }

    /**
     * This method returns a new string with only the allowed characters and no disallowed characters.
     *
     * @param source java.lang.String source string
     * @param allowedChars a String containing the characters allowed to be in the resulting string.
     * @param disallowedChars a String containing the characters disallowed to be in the resulting string
     *
     * @return the new formatted string containing the allowed characters and not containing the disallowed characters.
     */
    public static String stripChars(String source, String allowedChars, String disallowedChars)
    {
        if (source == null)
        {
            return null;
        }

        char  sourceArray[] = source.toCharArray();
        int   length      = sourceArray.length;
        char  destArray[] = new char[length];

        int   j = 0;

        for (int i = 0; i < length; i++)
        {
            if (((allowedChars != null) && (allowedChars.indexOf(sourceArray[i]) >= 0)) || ((disallowedChars != null) && (disallowedChars.indexOf(sourceArray[i]) < 0)))
            {
                destArray[j++] = sourceArray[i];
            }
        }

        return (j <= 0) ? null : (new String(destArray)).trim();
    }

    /**
     * This method returns a new string containing only the root string with no .jsp.
     *
     * @param s java.lang.String string to be stripped
     *
     * @return java.lang.String the new string containing only  root string.
     */
    public static String stripDotJsp(String s)
    {
        if (s == null)
        {
            return null;
        }

        String work = s;

        int    dotJspPos = work.indexOf(".jsp");

        if (dotJspPos > -1)
        {
            s = work.substring(0, dotJspPos);
        }

        return s;
    }

    /**
     * Removes the html from an html string and leaves only the text
     *
     * @param stripString - String to strip html from
     *
     * @return - stripped String
     */
    public static String stripHtmlFromText(String stripString)
    {
		String  temp      = "";
    	if (Util.isFilled(stripString ))
    	{
	
		   boolean whileLoop = true;

		   for (int i = 0; i < stripString.length(); i++)
		   {
			   if (stripString.charAt(i) != '<')
			   {
				   temp = temp + stripString.charAt(i);
			   }
			   else
			   {
				   whileLoop = true;

				   while (whileLoop)
				   {
					   if ((i < stripString.length()) && (stripString.charAt(i) != '>'))
					   {
						   i++;
					   }
					   else
					   {
						   whileLoop = false;
					   }
				   }
			   }
		   }

    	}
       
        return temp;
    }

    /**
     * This method returns a string containing only digits and the decimal point.
     *
     * @param sField java.lang.String
     *
     * @return java.lang.String a string containing only digits and the decimal point
     */
    public static String stripSpecialChars(String sField)
    {
        char  acOrigField[] = sField.toCharArray();
        int   length        = acOrigField.length;
        char  acDestField[] = new char[length];
        int   j             = 0;

        for (int i = 0; i < length; i++)
        {
            if (Character.isLetterOrDigit(acOrigField[i]))
            {
                acDestField[j++] = acOrigField[i];
            }
            else
            {
                if ((acOrigField[i] == ' ') || (acOrigField[i] == '@') || (acOrigField[i] == '#') || (acOrigField[i] == '$') || (acOrigField[i] == '%') || (acOrigField[i] == '&') || (acOrigField[i] == '*') || (acOrigField[i] == '(') || (acOrigField[i] == ')') || (acOrigField[i] == '[') || (acOrigField[i] == ']') || (acOrigField[i] == '.') || (acOrigField[i] == ',') || (acOrigField[i] == ';') ||
                        (acOrigField[i] == ':') || (acOrigField[i] == '~') || (acOrigField[i] == '`') || (acOrigField[i] == '!') || (acOrigField[i] == '"'))
                {
                    acDestField[j++] = acOrigField[i];
                }
            }
        }

        return (j <= 0) ? null : (new String(acDestField)).trim();
    }

    /**
     * Convert a java.sql.Timestamp to a valid java.sql.Date
     *
     * @param timeStampToConvert - Timestamp to convert
     *
     * @return valid java.sql.Date if a null is passed in a null is back out
     */
    public static java.sql.Date timeToDate(java.sql.Timestamp timeStampToConvert)
    {
		java.sql.Date sqlDate =null;
    	if ( !Util.isNull(timeStampToConvert ))
    	{
			sqlDate = new java.sql.Date(timeStampToConvert.getTime());
    	}

        return sqlDate;
    }

    /**
     * This method will remove illegal characters for strings so they can be placed in a url link.
     *
     * @param s - string to work on
     *
     * @return - encoded String
     */
    public static String urlEncode(String s)
    {
        return urlEncode(s, false);
    }

    /**
     * This method will remove illegal characters for strings so they can be placed in a url link.
     *
     * @param s - string to work on
     * @param encodeFull - whether to encode all characters
     *
     * @return - encoded String
     */
    public static String urlEncode(String s, boolean encodeFull)
    {
        if (s == null)
        {
            return null;
        }

        StringBuffer b = new StringBuffer();

        for (int i = 0; i < s.length(); i++)
        {
            char c  = s.charAt(i);
            int  ci = (int) c;

            if (c == ' ')
            {
                b.append("%20");
            }
            else if (c == '&')
            {
                b.append("%26");
            }
            else if (c == '?')
            {
                b.append("%3F");
            }
            else if (c == '#')
            {
                b.append("%35");
            }
            else if (c == '/')
            {
                if (encodeFull)
                {
                    b.append("%2F");
                }
                else
                {
                    b.append('/');
                }
            }
            else if (c == '.')
            {
                if (encodeFull)
                {
                    b.append("%2E");
                }
                else
                {
                    b.append('.');
                }
            }
            else if (c == '-')
            {
                if (encodeFull)
                {
                    b.append("%2D");
                }
                else
                {
                    b.append('-');
                }
            }
            else if (c == '_')
            {
                if (encodeFull)
                {
                    b.append("%5F");
                }
                else
                {
                    b.append('_');
                }
            }
            else if (c == '+')
            {
                b.append("%2B");
            }
            else if (c == '"')
            {
                b.append("%22");
            }
            else if (((ci >= 48) && (ci <= 57)) || ((ci >= 65) && (ci <= 90)) || ((ci >= 97) && (ci <= 122)))
            {
                b.append(c);
            }
            else
            {
                b.append("%");
                if (ci <= 15)
                    b.append("0");
                b.append(Integer.toHexString(ci).toUpperCase());
            }
        }

        return b.toString();
    }

    /**
     * Returns the number of whole days that have elapsed from a given day to
     * today. If the given day is today, it will return zero. If the day
     * is yesterday, it will return 1, etc. If the given day is in the future,
     * the reult will be a negative number.
     *
     * @param comDate - the date to compare to
     *
     * @return int
     */
    public static int getDaysElapsed(java.sql.Timestamp compDate)
    {
        long currentMillis;
        long compareMillis;

        // Always compare to the last millsecond of today. This guarantees
        // that the result will be a whole day.
        GregorianCalendar current = new GregorianCalendar();
        current.setTime(new Date(System.currentTimeMillis()));
        current.set(GregorianCalendar.HOUR_OF_DAY, 23);
        current.set(GregorianCalendar.MINUTE, 59);
        current.set(GregorianCalendar.SECOND, 59);
        current.set(GregorianCalendar.MILLISECOND, 999);
        currentMillis = current.getTime().getTime();

        GregorianCalendar compareDate = new GregorianCalendar();
        compareDate.setTime(new Date(compDate.getTime()));
        compareMillis = compareDate.getTime().getTime();

        return (int) ((currentMillis - compareMillis) / NUM_MILLIS_IN_DAY);

    }
    
    /**
     * Splits a String into an array of Strings based on a delimiter
     * @param st
     * @return
     */
    public static String[] split(String st, String delimiter) {
    	if (st == null)
    		return new String[0];
    	ArrayList l=new ArrayList();
    	StringTokenizer tok=new StringTokenizer(st,delimiter);
    	while (tok.hasMoreTokens()) 
    		l.add(tok.nextToken());
    	String ret[]=new String[l.size()];
    	l.toArray(ret);
    	return ret;
    }	
    
    /**
     * Returns the cause for the exception, JDK 1.4 and above only
     * @param e
     * @return
     */
    public static Throwable getCause(Exception e) {
    	Class c=e.getClass();
    	Class parms[] = {};
    	Object pass[]= {};
    	try {
			Method m=c.getMethod("getCause",parms);
			Throwable t=(Throwable)m.invoke(e,pass);
			return t;
		} catch (SecurityException e1) {
			return null;
		} catch (NoSuchMethodException e1) {
			return null;
		} catch (IllegalArgumentException e1) {
			return null;
		} catch (IllegalAccessException e1) {
			return null;
		} catch (InvocationTargetException e1) {
			return null;
		}		
    	
    }	

}
