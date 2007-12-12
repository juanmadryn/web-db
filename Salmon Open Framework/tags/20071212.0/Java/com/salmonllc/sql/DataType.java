package com.salmonllc.sql;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataType.java $
//$Author: Srufle $
//$Revision: 19 $
//$Modtime: 11/04/04 10:57a $
/////////////////////////

import java.util.*;

/**
 * This class converts sql datatypes into datastore datatypes
 */

public class DataType {
    private static final Hashtable _types = new Hashtable();

    static { 
        addType("char", DataStore.DATATYPE_STRING);
        addType("date", DataStore.DATATYPE_DATETIME);
        addType(DBConnection.DB2_CONNECTION, "date", DataStore.DATATYPE_DATE);
        addType(DBConnection.DB2MVS_CONNECTION, "date", DataStore.DATATYPE_DATE);
        addType(DBConnection.DB2VSE_CONNECTION, "date", DataStore.DATATYPE_DATE);
        addType(DBConnection.MYSQL_CONNECTION, "date", DataStore.DATATYPE_DATE);
        addType("datetime", DataStore.DATATYPE_DATETIME);
        addType("timestmp", DataStore.DATATYPE_DATETIME);
        addType("timestamp", DataStore.DATATYPE_DATETIME);
        addType("time", DataStore.DATATYPE_DATETIME);
        addType(DBConnection.MYSQL_CONNECTION, "time", DataStore.DATATYPE_TIME);
        addType("decimal", DataStore.DATATYPE_DOUBLE);
        addType("float", DataStore.DATATYPE_DOUBLE);
        addType("double", DataStore.DATATYPE_DOUBLE);
        addType("int", DataStore.DATATYPE_INT);
        addType("integer", DataStore.DATATYPE_INT);
        addType("smallint", DataStore.DATATYPE_SHORT);
        addType("bit", DataStore.DATATYPE_SHORT);
        addType("bigint", DataStore.DATATYPE_LONG);
        addType("text", DataStore.DATATYPE_STRING);
        addType("varchar", DataStore.DATATYPE_STRING);
        addType("longvar", DataStore.DATATYPE_STRING);
        addType("image", DataStore.DATATYPE_BYTEARRAY);
        addType("blob", DataStore.DATATYPE_BYTEARRAY);
        addType("numeric", DataStore.DATATYPE_INT);
        addType(DBConnection.POSTGRES_CONNECTION, "numeric", DataStore.DATATYPE_DOUBLE);
        addType("nvarchar", DataStore.DATATYPE_STRING);
        addType("ntext", DataStore.DATATYPE_STRING);
        addType("number", DataStore.DATATYPE_DOUBLE);
        addType("timestamptz", DataStore.DATATYPE_DATETIME);
        addType("long", DataStore.DATATYPE_LONG);
        addType(DBConnection.ORACLE_CONNECTION, "long", DataStore.DATATYPE_STRING);
    }

    private static void addType(String dbDataType, int dsDataType) {
        _types.put(dbDataType, new Integer(dsDataType));
    }

    private static void addType(String dbms, String dbDataType, int dsDataType) {
        _types.put((dbms + "." + dbDataType).toLowerCase(), new Integer(dsDataType));
    }

    public static int toDSDataType(String dbDataType) {
        return toDSDataType(dbDataType, -1, -1);
    }

    public static int toDSDataType(String dbms, String dbDataType) {
        return toDSDataType(dbms, dbDataType, -1, -1);
    }

    public static int toDSDataType(String dbDataType, int precision, int scale) {
        try {
            if (dbDataType.equalsIgnoreCase("number") && scale < 1)
                return DataStore.DATATYPE_INT;
            else
                return ((Integer) _types.get(dbDataType.toLowerCase())).intValue();
        } catch (Exception e) {
            return DataStore.DATATYPE_STRING;
        }

    }

    public static int toDSDataType(String dbms, String dbDataType, int precision, int scale) {
        try {
            if (dbms == null)
                return toDSDataType(dbDataType, precision, scale);

            if (dbDataType.equalsIgnoreCase("number") && scale < 1)
                return DataStore.DATATYPE_INT;
            else {
                dbms = dbms.toLowerCase();
                dbDataType = dbDataType.toLowerCase();
                Integer type = (Integer) _types.get(dbms + "." + dbDataType);
                if (type == null)
                    type = (Integer) _types.get(dbDataType);
                if (type == null)
                    return DataStore.DATATYPE_STRING;
                else
                    return type.intValue();
            }
        } catch (Exception e) {
            return DataStore.DATATYPE_STRING;
        }

    }
}
