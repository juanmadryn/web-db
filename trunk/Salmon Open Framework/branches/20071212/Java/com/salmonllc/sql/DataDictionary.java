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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataDictionary.java $
//$Author: Dan $
//$Revision: 50 $
//$Modtime: 11/16/04 5:27p $
/////////////////////////

import com.salmonllc.properties.Props;
import java.io.Serializable;
import java.sql.*;
import java.util.*;

/**
 * Builds a data dictionary of meta-data for all tables/columns in the database associated with a database profile
 */

public class DataDictionary implements Serializable {
    private Hashtable _elements;
    private Hashtable _tables;
    private String _application;
    private String _profile;
    private boolean _allTablesLoaded;
    private Hashtable _htOracleSynonyms=null;
    private DBConnection _conn;

    private class TableDefinition implements Serializable {
        public String tableName = null;
        public boolean keysLoaded = false;
        public Vector columns = new Vector();
    }

    private class StringVector extends com.salmonllc.util.VectorSort {
        public StringVector(int i) {
            super(i);
        }

        public boolean compare(Object o1, Object o2) {
            boolean ret = (((String) o1).compareToIgnoreCase((String) o2) < 0);
            return ret;
        }
    }

    /**
	 * Package Constructor, Only call from DBConnection 
	 */
	DataDictionary(DBConnection conn) {
		_conn=conn;
        _elements = new java.util.Hashtable();
        _tables = new java.util.Hashtable();
        _application = conn.getApplication();
        _profile=conn.getProfileName();
	}
    /**
     * Creates a data dictionary using the application name and default database profile.
     */
    public DataDictionary(String application) {
        this(application, null);
    }

    /**
     * Creates a data dictionary using the application name and database profile.
     */
    public DataDictionary(String application, String profile) {
        _application = application;
        _profile = profile;
        _elements = new java.util.Hashtable();
        _tables = new java.util.Hashtable();
    }

    /**
     * Returns the column definition object for a particular column (indicated by the column name table.column).
     */
    public ColumnDefinition getColumnDefintion(String columnName) {
        if (isLoaded(columnName))
            return (ColumnDefinition) _elements.get(columnName);
        else
            return null;
    }

    /**
     * Returns a Vector of column names in the database.
     */
    public Vector getColumnNames() {
        loadTable(null);
        Enumeration enumera = _elements.keys();
        StringVector columns = new StringVector(_elements.size());
        String columnName = "";
        while (enumera.hasMoreElements()) {
            columnName = (String) enumera.nextElement();
            columns.addElement(columnName);
        }
        columns.sort();

        return columns;
    }

    /**
     * Returns a vector of column definitions for the specified table
     */
    public java.util.Vector getColumns(String tableName) {
        loadTable(tableName);
        TableDefinition def = (TableDefinition) _tables.get(tableName);
        if (def == null)
            return null;
        else
            return def.columns;
    }

    private DBConnection getConnection() throws SQLException {
        if (_conn != null)
        	return _conn;
    	else if (_profile == null)
            return DBConnection.getConnection(_application);
        else
            return DBConnection.getConnection(_application, _profile);
    }
    
    private void freeConnection(DBConnection conn) {
    	if (_conn != null && conn==_conn)
    		return;
    	conn.freeConnection();
    }	

    /**
     * Returns the Database datatype for the column.
     */
    public String getDBDataType(String columnName) {
        if (isLoaded(columnName)) {
            ColumnDefinition cd = (ColumnDefinition) _elements.get(columnName);
            return cd.getDBDataType();
        } else
            return null;
    }

    /**
     * Returns the datastore datatype of a column
     */
    public int getDSDataType(String columnName) {
        if (isLoaded(columnName)) {
            ColumnDefinition cd = (ColumnDefinition) _elements.get(columnName);
            return cd.getDSDataType();
        } else
            return -1;
    }

    /**
     * Returns the datalength of the column
     */
    public int getLength(String columnName) {
        if (isLoaded(columnName)) {
            ColumnDefinition cd = (ColumnDefinition) _elements.get(columnName);
            return cd.getLength();
        } else
            return -1;

    }

    private String getTable(String columnName) {
        if (columnName == null)
            return null;
        int pos = columnName.lastIndexOf(".");
        if (pos < 0)
            return null;
        return columnName.substring(0, pos);
    }

    /**
     * Returns a vector of all the table names in the database
     */
    public Vector getTableNames() {
        loadTable(null);
        Enumeration enumera = _tables.keys();
        StringVector tables = new StringVector(_tables.size());
        String tableName = "";
        while (enumera.hasMoreElements()) {
            tableName = (String) enumera.nextElement();
            tables.addElement(tableName);
        }
        tables.sort();
        return tables;
    }

    private boolean isLoaded(String columnName) {
        String tab = getTable(columnName);
        if (tab == null)
            return false;
        if (tab.equals("null"))
            return false;
        loadTable(tab);
        return _elements.containsKey(columnName);
    }

    /**
     * Returns true if the column can be nulled.
     */
    public boolean isNullable(String columnName) {
        if (isLoaded(columnName)) {
            ColumnDefinition cd = (ColumnDefinition) _elements.get(columnName);
            return cd.isNullable();
        } else
            return false;
    }

    /**
     * Returns true if the column is part of the primary key of the table.
     */
    public boolean isPkey(String columnName) {
        if (isLoaded(columnName)) {
            ColumnDefinition cd = (ColumnDefinition) _elements.get(columnName);
            return cd.isPkey();
        } else
            return false;
    }


    private void loadTable(String tableName) {
        String user=null;
        String fullTableName = tableName;
        tableName = getTableFromFullName(tableName);

        if (tableName != null) {
            TableDefinition def = (TableDefinition) _tables.get(fullTableName);
            if (def != null) {
                if (!def.keysLoaded)
                    loadKeys(tableName, fullTableName);
                return;
            }
        } else if (_allTablesLoaded)
            return;


        if (_htOracleSynonyms!=null) {
            if (_htOracleSynonyms.containsKey(tableName)) {
              tableName=(String)_htOracleSynonyms.get(tableName);
            }
        }

        DBConnection connection = null;
        try {
            connection = getConnection();
            String dbms = connection.getDBMS();
            String column5 = null;
            boolean isSybase = dbms.equals(DBConnection.SYBASE_CONNECTION);
            boolean isSQLAnywhere = dbms.equals(DBConnection.SQLANYWHERE_CONNECTION);
            boolean isMSSQL = dbms.equals(DBConnection.MSSQLSEVER_CONNECTION);
            boolean isOracle = dbms.equals(DBConnection.ORACLE_CONNECTION);
            boolean isDB2MVS = dbms.equals(DBConnection.DB2MVS_CONNECTION);
            boolean isMySql = dbms.equals(DBConnection.MYSQL_CONNECTION);
            boolean isDB2 = dbms.equals(DBConnection.DB2_CONNECTION);
            boolean isDB2400 = dbms.equals(DBConnection.DB2400_CONNECTION);
            boolean isIngres = dbms.equals(DBConnection.INGRES_CONNECTION);
            boolean isFireBird = dbms.equals(DBConnection.FIREBIRDSQL_CONNECTION);
            boolean isAnsiSQL92 = dbms.equals(DBConnection.ANSISQL92_CONNECTION);
            boolean isPostGres = dbms.equals(DBConnection.POSTGRES_CONNECTION);
            String query;
            String dbname = connection.getDBName();


            if (isMySql) {
                Statement s = connection.createStatement();
                Vector vTables = new Vector();
                if (tableName == null) {
                    ResultSet r = s.executeQuery("show tables");
                    while (r.next()) {
                        vTables.addElement(r.getString(1));
                    }
                    r.close();
                } else
                    vTables.addElement(tableName);
                for (int i = 0; i < vTables.size(); i++) {
                    String table = (String) vTables.elementAt(i);
                    ResultSet r = s.executeQuery("describe " + fixDashes(table));
                    String column = null;
                    String field = null;
                    String type = null;
                    boolean nullable = false;
                    int length = -1;
                    while (r.next()) {
                        column = r.getString(1);
                        field = table + "." + column;
                        type = r.getString(2);
                        int iOpenParen = type.indexOf('(');
                        int iCloseParen = type.indexOf(')');
                        if (iOpenParen != -1) {
                        	String typeCheck = type.toUpperCase();
                        	if (typeCheck.startsWith("ENUM") || typeCheck.startsWith("SET"))
								type = type.substring(0, iOpenParen);
                        	else {
                            	String sLength = type.substring(iOpenParen + 1, iCloseParen);
                            	int iComma = sLength.indexOf(",");
                            	if (iComma == -1)
                                	length = (new Integer(sLength)).intValue();
                            	else {
                                	String sFirstNum = sLength.substring(0, iComma);
                                	String sLastNum = sLength.substring(iComma + 1);
                                	length = (new Integer(sFirstNum)).intValue() + (new Integer(sLastNum)).intValue();
                            	}
								type = type.substring(0, iOpenParen);
                        	}
                        }
                        String sNullable = r.getString(3);
                        nullable = (sNullable.length() > 0 && sNullable.charAt(0) == 'Y');
                        ColumnDefinition columnDef = new ColumnDefinition(connection.getDBMS(), table, column, type, length, nullable);
                        _elements.put(field, columnDef);
                        TableDefinition def = (TableDefinition) _tables.get(table);
                        if (def != null) {
                            def.columns.addElement(columnDef);
                        } else {
                            def = new TableDefinition();
                            def.columns.addElement(columnDef);
                            _tables.put(table, def);
                        }
                    }
                    r.close();
                }
                s.close();
            } else if (isIngres) {
                Statement s = connection.createStatement();
                Vector vTables = new Vector();
                if (tableName == null) {
                    ResultSet r = s.executeQuery("SELECT trim(table_name), table_type FROM iitables where " +
                            "table_owner != '$ingres' and table_name not like '$%' and " +
                            "table_name not like 'ii%' and table_type != 'I'");
                    while (r.next()) {
                        vTables.addElement(r.getString(1));
                    }
                    r.close();
                } else
                    vTables.addElement(tableName);
                for (int i = 0; i < vTables.size(); i++) {
                    String table = (String) vTables.elementAt(i);
                    ResultSet r = s.executeQuery("select * from iicolumns where table_name = '" + table + "'");
                    String column = null;
                    String field = null;
                    String type = null;
                    boolean nullable = false;
                    int length = -1;
                    while (r.next()) {
                        column = r.getString(3).trim();
                        field = table + "." + column;
                        type = r.getString(4).trim();
                        int iOpenParen = type.indexOf('(');
                        int iCloseParen = type.indexOf(')');
                        if (iOpenParen != -1) {
                            String sLength = type.substring(iOpenParen + 1, iCloseParen);
                            int iComma = sLength.indexOf(",");
                            if (iComma == -1)
                                length = (new Integer(sLength)).intValue();
                            else {
                                String sFirstNum = sLength.substring(0, iComma);
                                String sLastNum = sLength.substring(iComma + 1);
                                length = (new Integer(sFirstNum)).intValue() + (new Integer(sLastNum)).intValue();
                            }
                            type = type.substring(0, iOpenParen);
                        } else
                            length = r.getInt(5);
                        String sNullable = r.getString(7);
                        nullable = (sNullable.length() > 0 && sNullable.charAt(0) == 'Y');
                        ColumnDefinition columnDef = new ColumnDefinition(connection.getDBMS(), table, column, type, length, nullable);
                        _elements.put(field, columnDef);
                        TableDefinition def = (TableDefinition) _tables.get(table);
                        if (def != null) {
                            def.columns.addElement(columnDef);
                        } else {
                            def = new TableDefinition();
                            def.columns.addElement(columnDef);
                            _tables.put(table, def);
                        }
                    }
                    r.close();
                }
                s.close();
            } else if (isPostGres) {
                boolean nullable = false;

                Statement s = connection.createStatement();
                Vector vTables = new Vector();
                if (tableName == null) {
                    ResultSet r = s.executeQuery("select relname from pg_class where relkind = 'r' and not relname like 'pg_%'");
                    while (r.next()) {
                        vTables.addElement(r.getString(1));
                    }
                    r.close();
                } else
                    vTables.addElement(tableName);
                for (int i = 0; i < vTables.size(); i++) {
                    String table = (String) vTables.elementAt(i);
                    ResultSet r = s.executeQuery("select * from " + table + " where 1 = 2 ");
                    ResultSetMetaData rsmd = r.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();

                    String column = null;
                    String field = null;
                    String type = null;
                    nullable = false;
                    int length = -1;
                    int precision = -1;
                    int scale = -1;
                    for (int j = 1; j <= numberOfColumns; j++) {
                        column = rsmd.getColumnName(j);
                        field = table + "." + column;
                        type = rsmd.getColumnTypeName(j);

                        length = rsmd.getColumnDisplaySize(j);
                        precision = rsmd.getPrecision(j);
                        scale = rsmd.getScale(1);


                        if (type.equals("numeric") && (length - precision == 1))
                            type = "long";
                        ColumnDefinition columnDef4 = new ColumnDefinition(table, column, type, length, nullable, precision, scale);
                        _elements.put(field, columnDef4);
                        TableDefinition def4 = (TableDefinition) _tables.get(table);
                        if (def4 != null) {
                            def4.columns.addElement(columnDef4);
                        } else {
                            def4 = new TableDefinition();
                            def4.columns.addElement(columnDef4);
                            _tables.put(table, def4);
                        }
                    }
                    r.close();
                }
                s.close();
            } else if (isFireBird) {
                Statement s = connection.createStatement();
                Vector vTables = new Vector();
                if (tableName == null) {
                    ResultSet r = s.executeQuery(
                            "Select RDB$RELATION_NAME from RDB$RELATIONS" +
                            " where RDB$VIEW_BLR is NULL and RDB$SYSTEM_FLAG = 0");
                    while (r.next()) {
                        vTables.addElement(r.getString(1).trim());
                    }
                    r.close();
                } else
                    vTables.addElement(tableName);
                for (int i = 0; i < vTables.size(); i++) {
                    String table = (String) vTables.elementAt(i);
                    String SQL = "Select R.RDB$FIELD_NAME, T.RDB$TYPE_NAME," +
                            "R.RDB$NULL_FLAG," +
                            "F.RDB$FIELD_LENGTH," +
                            "RDB$FIELD_POSITION" +
                            " from RDB$RELATION_FIELDS R, RDB$FIELDS F ,RDB$TYPES T " +
                            " where R.RDB$RELATION_NAME = '" + table + "'" +
                            " and R.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME" +
                            " and t.rdb$type = f.rdb$field_type and t.RDB$FIELD_NAME='RDB$FIELD_TYPE'" +
                            " order by R.RDB$FIELD_POSITION";
                    ResultSet r = s.executeQuery(SQL);
                    String column = null;
                    String field = null;
                    String type = null;
                    boolean nullable = false;
                    int length = -1;

                    while (r.next()) {
                        column = r.getString(1).trim();
                        field = table + "." + column;  //get field name
                        type = r.getString(2).trim();			//get field type
                        String sNullable = r.getString(3);	// ==1 presend nullable
                        length = r.getInt(4);			//get field length
                        nullable = (sNullable != null) && (sNullable.length() > 0) && (sNullable.compareTo("1") != -1);
                        nullable = !nullable;
                        ColumnDefinition columnDef = new ColumnDefinition(connection.getDBMS(), table, column, type, length, nullable);
                        _elements.put(field, columnDef);
                        TableDefinition def = (TableDefinition) _tables.get(table);
                        if (def != null) {
                            def.columns.addElement(columnDef);
                        } else {
                            def = new TableDefinition();
                            def.columns.addElement(columnDef);
                            _tables.put(table, def);
                        }
                    }
                    r.close();
                }
                s.close();
            } else if (isAnsiSQL92) {
                Connection con = connection.getJDBCConnection();
                DatabaseMetaData meta = con.getMetaData();
                ResultSet tables = meta.getTables(null, null, tableName, null);
                while (tables.next()) {
                    String catName = tables.getString(1);
                    String schName = tables.getString(2);
                    String tabName = tables.getString(3);
                    TableDefinition def = (TableDefinition) _tables.get(tabName);
                    if (def != null)
                        continue;
                    def = new TableDefinition();
                    def.tableName = tabName;

                    ResultSet columns = meta.getColumns(catName, schName, tabName, null);
                    Vector cols = def.columns;
                    ColumnDefinition colDef;
                    String colName;
                    while (columns.next()) {
                        colName = columns.getString(4);
                        colDef =
                                new ColumnDefinition(
                                        connection.getDBMS(),
                                        tabName,
                                        colName,
                                        columns.getString(6),
                                        columns.getInt(7),
                                        "YES".equals(columns.getString(18)));
                        cols.add(colDef);
                        _elements.put(tabName + "." + colName, colDef);
                    }
                    columns.close();

                    ResultSet pkeys = meta.getPrimaryKeys(catName, schName, tabName);
                    while (pkeys.next()) {
                        colName = tabName + "." + pkeys.getString(4);
                        colDef = (ColumnDefinition) _elements.get(colName);
                        if (colDef != null)
                            colDef.setPkey();
                    }
                    pkeys.close();
                    def.keysLoaded = true;
                    _tables.put(tabName, def);
                }
            } else {
                if (isDB2MVS) {
                    query = "SELECT O.NAME,C.NAME,COLTYPE,LENGTH,NULLS FROM SYSIBM.SYSTABLES O,SYSIBM.SYSCOLUMNS C " + "WHERE O.NAME = C.TBNAME AND O.CREATOR = C.TBCREATOR " + "and " + (tableName == null ? "O.CREATOR = '" + dbname + "' " : "O.CREATOR = '" + dbname + "' and O.NAME = '" + tableName + "' ") + "ORDER BY O.NAME, C.NAME";
                } else if (isDB2) {
                    query = "SELECT O.NAME,C.NAME,COLTYPE,LENGTH,NULLS FROM SYSIBM.SYSTABLES O,SYSIBM.SYSCOLUMNS C " + "WHERE O.NAME = C.TBNAME AND O.CREATOR = C.TBCREATOR  AND " + (tableName == null ? "O.CREATOR != 'SYSIBM' AND O.CREATOR != 'SYSCAT' AND O.CREATOR != 'SYSSTAT' " : "O.NAME = '" + tableName + "' ") + "ORDER BY O.NAME, C.NAME";
                } else if (isDB2400) {
                    String locDbName = dbname.toUpperCase();
                    String locTableName = null;
                    if (tableName != null) locTableName = tableName.toUpperCase();
                    query = "SELECT O.NAME,C.NAME,COLTYPE,LENGTH,NULLS FROM QSYS2.SYSTABLES O,QSYS2.SYSCOLUMNS C " + "WHERE O.NAME = C.TBNAME AND O.TABLE_SCHEMA = C.TABLE_SCHEMA " + "and " + (locTableName == null ? "O.TABLE_SCHEMA = '" + locDbName + "' " : "O.TABLE_SCHEMA = '" + locDbName + "' and O.NAME = '" + locTableName + "' ") + "ORDER BY O.NAME, C.NAME";
                } else if (isSQLAnywhere) {
                    query = "SELECT o.name, c.cname, c.coltype, c.length, c.nulls " +
                            "FROM sys.syscolumns c, dbo.sysobjects o " +
                            "WHERE " +
                            "c.tname = o.name and o.type in ('U','V') and " +
                            "o.name not in('pbcatcol','pbcatedt','pbcatfmt','pbcattbl','pbcatvld', 'dtproperties') " +
                            "ORDER BY o.name, c.cname ";
                } else if (!isOracle) {
                	String userType="usertype";
                    if (isSybase) 
                        column5 = "c.status";
                    else if (isMSSQL) {
                        column5 = "c.isnullable";
                        userType="xusertype";
                    }    
                    /* Changed to use xusertype column on 11/11/2004 by MK */
                    query = "SELECT o.name, c.name, t.name, c.length, " + column5 + " FROM syscolumns c, sysobjects o, systypes t " + "WHERE c.id = o.id and (o.type = 'U' or o.type='V') and c." + userType + " = t." + userType + " and " + (tableName == null ? "o.name not in('pbcatcol','pbcatedt','pbcatfmt','pbcattbl','pbcatvld', 'dtproperties')" : "o.name = '" + tableName + "'") + "ORDER BY o.name, c.name";
                } else {
                    Props p = Props.getProps(_application, null);

                    String def = p.getProperty(Props.DB_DEFAULT);
                    if (def != null)
                        def += ".";
                    else
                        def = "";

                    String name = _profile;
                    if (name == null)
                        name = def;
                    else
                        name += ".";

                    user = p.getProperty(name + Props.DB_USER).toUpperCase();
                    if (_htOracleSynonyms==null)
                        _htOracleSynonyms=new Hashtable();
                    String sQuerySynonym="SELECT distinct table_name,synonym_name from all_synonyms where owner='"+user+"'";
                    Statement s = connection.createStatement();
                    ResultSet r = s.executeQuery(sQuerySynonym);
                    while (r.next()) {
                      _htOracleSynonyms.put(r.getString(2),r.getString(1));
                    }
                    String sSynonymName=null;
                    if (tableName!=null) {
                        if (_htOracleSynonyms.containsKey(tableName)) {
                          sSynonymName=tableName;
                          tableName=(String)_htOracleSynonyms.get(tableName);
                        }
                    }
                    r.close();
                    s.close();
             //,(select synonym_name from all_synonyms where owner='"+user+"' and table_name=o.object_name and table_owner=o.owner)
                    query =
                            "SELECT distinct o.object_name, c.column_name, c.data_type, c.data_length, c.nullable, c.data_precision, c.data_scale,o.owner,(select synonym_name from all_synonyms where owner='"+user+"' and table_name=o.object_name and table_owner=o.owner) FROM sys.all_tab_columns c, sys.all_objects o WHERE c.table_name = o.object_name and ((o.owner = '"
                            + user
                            + "' and c.owner = '"
                            + user
                            + "') or (c.owner||c.table_name in (select distinct table_owner||table_name from all_synonyms where table_name=c.table_name and owner='"
                            + user+"'"+(sSynonymName==null?" and synonym_name in (select synonym_name from all_synonyms where owner='"+user+"' and table_name=o.object_name and table_owner=o.owner)":" and synonym_name='"+sSynonymName+"'")+"))) and "
                            + (tableName == null ? "o.object_name not in('pbcatcol','pbcatedt','pbcatfmt','pbcattbl','pbcatvld')" : "o.object_name = '" + tableName + "'")
                            + " and (o.object_type='TABLE' or o.object_type='VIEW') ORDER BY o.object_name, c.column_name";

                }
                Statement s = connection.createStatement();
                ResultSet r = s.executeQuery(query);
                String table = null;
                String column = null;
                String field = null;
                String type = null;
                boolean nullable = false;
                int precision = -1;
                int scale = -1;
                int length = -1;
                /*if (dbname == null)
                    dbname = "";
                else if (isSybase)
                    dbname += ".dbo.";
                else
                    dbname += ".";*/
                dbname = "";

                while (r.next()) {
                    if (isOracle && !r.getString(8).equals(user)) {
                        String sSynom=r.getString(9);
                        table=dbname+sSynom;
                    }
                    else
                        table = dbname + r.getString(1);
                    column = r.getString(2);
                    field = table + "." + column;
                    type = r.getString(3).trim();
                    length = r.getInt(4);
                    if (isSybase)
                        nullable = (((r.getInt(5) % 16) / 8) == 1);
                    else if (isSQLAnywhere)
                        nullable = (r.getString(5).charAt(0) == 'Y');
                    else if (isMSSQL)
                        nullable = (r.getInt(5) == 1);
                    else if (isOracle)
                        nullable = (r.getString(5).charAt(0) == 'Y');

                    // TODO: get precision and scale for other database systems
                    if (isOracle) {
                        precision = r.getInt(6);
                        scale = r.getInt(7);
                    }

//                    ColumnDefinition columnDef = new ColumnDefinition(table, column, type, length, nullable);
                    ColumnDefinition columnDef = new ColumnDefinition(connection.getDBMS(), table, column, type, length, nullable, precision, scale);

                    _elements.put(field, columnDef);
                    TableDefinition def = (TableDefinition) _tables.get(table);
                    if (def != null) {
                        def.columns.addElement(columnDef);
                    } else {
                        def = new TableDefinition();
                        def.columns.addElement(columnDef);
                        _tables.put(table, def);
                    }
                }
                r.close();
                s.close();
            }
            if (tableName == null)
                _allTablesLoaded = true;
        } catch (SQLException e) {
            com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
        } finally {
            if (connection != null)
                freeConnection(connection);
        }

        if (tableName != null) {
            TableDefinition def = (TableDefinition) _tables.get(fullTableName);
            if (def != null) {
                if (!def.keysLoaded)
                    loadKeys(tableName, fullTableName);
            }
        }

    }

    /*
    TODO: research this code
    This method was submitted by a SOFIA user. It is the loadTable method done in
    a generic fashon using the JDBC driver built-in code. It has a few problems which has prevented
    it from being incorporated:
    1) It uses the full name for tables for every database including the database, owner and table.
    This should only be used for some engines and not others
    2) It is very slow to load all table at once for big databases
    3) There is no good way to test the code for every different engine since we don't have them all available
    private void loadTable(String tableName) {
        if (_allTablesLoaded || (tableName != null && _tables.get(tableName) != null))
            return;

        String catalogName = null, schemaName = null;
        String fullTableName = tableName;
        String[] names = new String[6];
        for (int i = 0; i < 3; i++)
            names[i] = null;
        if (fullTableName != null) {
            fullTableName = fullTableName.replaceAll("..", ". .");
            StringTokenizer st = new StringTokenizer(fullTableName, ".");
            int pos = 0;
            while (st.hasMoreTokens()) {
                String name = st.nextToken();
                if (" ".equals(name))
                    name = null;
                names[pos++] = name;
            }
            tableName = names[--pos];
            if (pos > 0)
                schemaName = names[--pos];
            if (pos > 0)
                catalogName = names[--pos];
        }

        DBConnection connection = null;
        try {
            connection = getConnection();
            Connection con = connection.getJDBCConnection();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet tables = meta.getTables(catalogName, schemaName, tableName, null);
            while (tables.next()) {
                String catName = tables.getString(1);
                String schName = tables.getString(2);
                String tabName = tables.getString(3);
                String fullName = getFullName(catName, schName, tabName);
                TableDefinition def = (TableDefinition) _tables.get(fullName);
                if (def != null)
                    continue;
                def = new TableDefinition();
                def.tableName = fullName;

                ResultSet columns = meta.getColumns(catName, schName, tabName, null);
                Vector cols = def.columns;
                ColumnDefinition colDef;
                String colName;
                while (columns.next()) {
                    colName = columns.getString(4);
                    colDef =
                            new ColumnDefinition(
                                    fullName,
                                    colName,
                                    columns.getString(6),
                                    columns.getInt(7),
                                    "YES".equals(columns.getString(18)));
                    cols.add(colDef);
                    _elements.put(fullName + "." + colName, colDef);
                }

                ResultSet pkeys = meta.getPrimaryKeys(catalogName, schemaName, tabName);
                while (pkeys.next()) {
                    colName = fullName + "." + pkeys.getString(4);
                    colDef = (ColumnDefinition) _elements.get(colName);
                    if (colDef != null)
                        colDef.setPkey();
                }
                _tables.put(fullName, def);
            }
        } catch (SQLException e) {
            com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
        } finally {
            if (connection != null)
                connection.freeConnection();
        }

        if (tableName == null) {
            _allTablesLoaded = true;
        }
    }

    private String getFullName(String catName,String schemaName, String tabName) {
        String ret = "";
        if (catName != null &&  catName.length() > 0)
            ret += catName + ".";
        if (schemaName != null && schemaName.length() > 0)
            ret += schemaName + ".";
        ret += tabName;
        return ret;
    }

    */



    private String getTableFromFullName(String fullName) {
        if (fullName == null)
            return null;
        int pos = fullName.lastIndexOf(".");
        if (pos < 0)
            return fullName;
        return fullName.substring(pos + 1);
    }

    private void loadKeys(String tableName, String fullTableName) {
        DBConnection conn = null;
        try {
            int indexNo = -1;
            int keyCount = -1;
            conn = getConnection();
            Statement st = conn.createStatement();
            if (conn.getDBMS().equals(DBConnection.MYSQL_CONNECTION)) {
                StringBuffer query = new StringBuffer("show index from " + fixDashes(tableName));
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(5));
                    if (r.getString(3).equals("PRIMARY")) {
                        ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                        if (def != null)
                            def.setPkey();
                    }
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.FIREBIRDSQL_CONNECTION)) {
                String theSQL = "SELECT  R.RDB$FIELD_NAME FROM " +
                        " RDB$INDEX_SEGMENTS R,  rdb$indices S" +
                        " WHERE  S.rdb$relation_name ='" + tableName + "'" +
                        " AND    R.RDB$INDEX_NAME= S.RDB$INDEX_NAME AND S.RDB$INDEX_ID=1";
                //select out the main key
                StringBuffer query = new StringBuffer(" " + tableName);
                ResultSet r = st.executeQuery(theSQL);
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1).trim());
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.INGRES_CONNECTION)) {
                StringBuffer query = new StringBuffer("SELECT trim(column_name), trim(table_name) from iiconstraints, iiindex_columns WHERE index_name = constraint_name " +
                        "AND constraint_type = 'P' AND table_name = '");
                query.append(tableName);
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1));
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.POSTGRES_CONNECTION)) {
                //TODO:Add load keys for POSTGRES
                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.DB2MVS_CONNECTION)) {
                String dbname = conn.getDBName();
                StringBuffer query = new StringBuffer("SELECT C.COLNAME FROM SYSIBM.SYSKEYS C, SYSIBM.SYSINDEXES O ");
                query.append("WHERE O.NAME = C.IXNAME AND O.CREATOR = C.IXCREATOR ");
                query.append("AND O.UNIQUERULE IN ('U','P') AND O.CREATOR = '" + dbname + "' AND O.TBNAME = '");
                query.append(tableName);
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1));
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.DB2_CONNECTION)) {
                StringBuffer query = new StringBuffer("SELECT C.COLNAME, I.TBNAME FROM SYSIBM.SYSINDEXCOLUSE C, SYSIBM.SYSINDEXES I WHERE I.NAME = C.INDNAME AND I.UNIQUERULE IN ('U','P') AND I.TBNAME='");
                query.append(tableName);
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1));
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (conn.getDBMS().equals(DBConnection.DB2400_CONNECTION)) {
                String dbname = conn.getDBName();
                dbname = dbname.toUpperCase();
                StringBuffer query = new StringBuffer("SELECT C.COLUMN_NAME FROM QSYS2.SYSCSTCOL C, QSYS2.SYSCST O ");
                query.append("WHERE O.CONSTRAINT_NAME = C.CONSTRAINT_NAME AND O.TABLE_SCHEMA = C.CONSTRAINT_SCHEMA ");
                query.append("AND O.CONSTRAINT_TYPE = 'PRIMARY KEY' AND O.TABLE_SCHEMA = '" + dbname + "' AND O.TABLE_NAME = '");
                query.append(tableName.toUpperCase());
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1));
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            } else if (!conn.getDBMS().equals(DBConnection.ORACLE_CONNECTION)) {
                //StringBuffer query = new StringBuffer("SELECT sysindexes.indid, sysindexes.keycnt,sysindexes.status FROM sysindexes,sysobjects WHERE sysindexes.id = sysobjects.id and (sysindexes.status & 2048 = 2048 or sysindexes.status & 2 = 2) and sysobjects.name ='");
                StringBuffer query;
                if (conn.getDBMS().equals(DBConnection.SQLANYWHERE_CONNECTION))
                    query = new StringBuffer("SELECT sysindexes.indid, sysindexes.keycnt,sysindexes.status FROM dbo.sysindexes, dbo.sysobjects WHERE sysindexes.id = sysobjects.id and (sysindexes.status & 2048 = 2048 or sysindexes.status & 2 = 2) and sysobjects.name ='");
                else
                    query = new StringBuffer("SELECT sysindexes.indid, sysindexes.keycnt,sysindexes.status FROM sysindexes,sysobjects WHERE sysindexes.id = sysobjects.id and (sysindexes.status & 2048 = 2048 or sysindexes.status & 2 = 2) and sysobjects.name ='");

                query.append(tableName);
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    indexNo = r.getInt(1);
                    keyCount = r.getInt(2);
                    int status = r.getInt(3);
                    if ((status & 2048) > 0)
                        break;
                }
                r.close();

                if ((indexNo > -1) && (keyCount >= 1)) {
                    String indexSt = new Integer(indexNo).toString();
                    query.setLength(7);
                    for (int i = 1; i <= keyCount; i++) {
                        query.append("index_col('");
                        query.append(tableName);
                        query.append("',");
                        query.append(indexSt);
                        query.append(",");
                        query.append(new Integer(i).toString());
                        query.append("),");
                    }
                    query.setLength(query.length() - 1);

                    r = st.executeQuery(query.toString());
                    if (r.next()) {
                        for (int i = 1; i <= keyCount; i++) {
                            query.setLength(0);
                            query.append(fullTableName);
                            query.append(".");
                            query.append(r.getString(i));
                            ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                            if (def != null)
                                def.setPkey();
                        }
                    }
                    r.close();

                    TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                    tab.keysLoaded = true;
                }
            } else {
                StringBuffer query = new StringBuffer("SELECT c.column_name FROM user_ind_columns c, user_constraints o WHERE o.constraint_name = c.index_name and o.constraint_type='P' and c.table_name ='");
                query.append(tableName);
                query.append("'");
                ResultSet r = st.executeQuery(query.toString());
                while (r.next()) {
                    query.setLength(0);
                    query.append(fullTableName);
                    query.append(".");
                    query.append(r.getString(1));
                    ColumnDefinition def = (ColumnDefinition) _elements.get(query.toString());
                    if (def != null)
                        def.setPkey();
                }
                r.close();

                TableDefinition tab = (TableDefinition) _tables.get(fullTableName);
                tab.keysLoaded = true;
            }
            st.close();
        } catch (Exception e) {
            com.salmonllc.util.MessageLog.writeErrorMessage("loadKeys()", e, this);
        } finally {
            if (conn != null)
                freeConnection(conn);
        }

    }
    
    private String fixDashes(String value) {
    	if (value != null) 
    		if (value.indexOf("-") > -1) {
    			return "`" + value + "`";
    		}	
    			
    	return value;
    }	 
}
