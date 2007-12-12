package com.salmonllc.jsp.controller;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/controller/DataBinder.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 7/31/02 6:11p $
/////////////////////////
import com.salmonllc.sql.DataStore;
/**
 * Base class for all type of Binders.
 * Creation date: (8/1/01 11:15:04 AM)
 * @author: Deepak Agarwal
 */
public class DataBinder {
/**
 * DataBinder constructor comment.
 */
public DataBinder() {
	super();
}
/**
 * Creation date: (8/1/01 11:09:17 AM)
 * @return int
 * @param strDataType java.lang.String
 */
protected int mapDataType(String strDataType)
{

    if (strDataType == null)
        return 0;
    else if (strDataType.equalsIgnoreCase("String"))
        return DataStore.DATATYPE_STRING;
    else if (strDataType.equalsIgnoreCase("Integer") || strDataType.equalsIgnoreCase("int"))
        return DataStore.DATATYPE_INT;
    else if (strDataType.equalsIgnoreCase("Datetime"))
        return DataStore.DATATYPE_DATETIME;
    else if (strDataType.equalsIgnoreCase("double"))
        return DataStore.DATATYPE_DOUBLE;
    else if (strDataType.equalsIgnoreCase("bytearray"))
        return DataStore.DATATYPE_BYTEARRAY;
    else if (strDataType.equalsIgnoreCase("short"))
        return DataStore.DATATYPE_SHORT;
    else if (strDataType.equalsIgnoreCase("long"))
        return DataStore.DATATYPE_LONG;
    else if (strDataType.equalsIgnoreCase("float"))
        return DataStore.DATATYPE_FLOAT;
    else if (strDataType.equalsIgnoreCase("date"))
        return DataStore.DATATYPE_DATE;
    else if (strDataType.equalsIgnoreCase("time"))
        return DataStore.DATATYPE_TIME;

    return 99;
    /*
    DATATYPE_STRING = 0;
    DATATYPE_INT = 1;
    DATATYPE_DATETIME = 2;
    public static final int DATATYPE_DOUBLE = 3;
    public static final int DATATYPE_BYTEARRAY = 4;
    public static final int DATATYPE_SHORT = 5;
    public static final int DATATYPE_LONG = 6;
    public static final int DATATYPE_FLOAT = 7;
    public static final int DATATYPE_DATE = 8;
    public static final int DATATYPE_TIME = 9;
    protected static final int DATATYPE_ANY = 99;
    */
}
}
