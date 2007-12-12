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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStore.java $
//$Author: Dan $
//$Revision: 97 $
//$Modtime: 11/02/04 9:59a $
/////////////////////////

import com.salmonllc.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * This class provides a storage buffer for data in SQL ResultSets that allow for retreves, inserts updates and deletes.
 */
public class DataStore extends DataStoreBuffer implements Runnable, Serializable, DataStoreInterface {

	public static final int UPDATEMETHOD_UPDATES = 1;
	public static final int UPDATEMETHOD_DELETEINSERTS = 2;

	private int _updateMethod = UPDATEMETHOD_UPDATES;
	private Vector _listeners;

	private transient DBConnection _dbConn;

	protected String _appName = null;
	private String _dbProfile = null;
	private boolean _releaseConn = false;
	private boolean _checkConcurrency = false;
	private boolean _useBind = false;
	private boolean _enableCancel = false; //Added by FC on 2/21/03. Was added
										   // to handle JDBC drivers which have
										   // problems with the cancel() method
										   // being called. Example Driver:
										   // JSQLDriver from NetDirect.
	private int _maxRows = -1;
	private String _connType;
	private DataSourceIn _dsIn;
	private DataSourceIn _curDsIn;
	private DataSourceOut _dsOut;
	private AutoRetrieveCriteria _havingCrit;
	private boolean _threaded = false;
	private String _dbName = null;
	private String _dbms = null;
	private boolean _autoValidate = false;
	private boolean _batchInserts = false;

	public static final String CONNECTION_PARM_CHECK_CONCURRENCY = "CheckConcurrency";
	public static final String CONNECTION_PARM_USE_BIND_FOR_UPDATES = "UseBindForUpdates";
	public static final String CONNECTION_PARM_USE_DELETE_INSERT_FOR_UPDATES = "UseDeleteInsertForUpdates";
	public static final String CONNECTION_PARM_TRIM_STRINGS = "TrimStrings";
	public static final String CONNECTION_PARM_CACHE_DATADICTIONARY = "CacheDataDictionary";

	public static final String CONNECTION_PARM_REPLACE_NULL_STRINGS = "ReplaceNullStrings";
	public static final String CONNECTION_PARM_REPLACE_NULL_INTS = "ReplaceNullInts";
	public static final String CONNECTION_PARM_REPLACE_NULL_DECIMALS = "ReplaceNullDecimals";
	public static final String CONNECTION_PARM_REPLACE_NULL_DATES = "ReplaceNullDates";
	public static final String CONNECTION_PARM_REPLACE_NULL_TIMES = "ReplaceNullTimes";
	public static final String CONNECTION_PARM_REPLACE_NULL_DATETIMES = "ReplaceNullDateTimes";

	/**
	 * Creates a new empty DataStore object. Any method requiring a database
	 * connection (retrieve,update and union) will have to be explicitly passed
	 * a DBConnection created outside the datastore.
	 */
	public DataStore() {
		super();
	}

	/**
	 * Creates a new empty DataStore. Any methods requiring a database
	 * connection (retrieve, update and union) will be able to use appName to
	 * get connection parameters from properties files and so an externally
	 * created DBConnection object does not need to be provided.
	 * 
	 * @param dsIn
	 *            The data source used to retrieve data.
	 * @param dsOut
	 *            The data source used to update data.
	 */
	public DataStore(DataSourceIn dsIn, DataSourceOut dsOut) {
		super();
		_appName = null;
		_dbProfile = null;
		_dsIn = dsIn;
		_dsOut = dsOut;
	}

	/**
	 * Creates a new empty DataStore. Any methods requiring a database
	 * connection (retrieve, update and union) will be able to use appName to
	 * get connection parameters from properties files and so an externally
	 * created DBConnection object does not need to be provided.
	 * 
	 * @param appName
	 *            The application to get the default database connection for.
	 */
	public DataStore(String appName) {
		this(appName, null);
	}

	/**
	 * Creates a new empty DataStore. Creates a new empty DataStore. Any methods
	 * requiring a database connection (retrieve, update and union) will be able
	 * to use appName and dbProfile to get connection parameters from properties
	 * files and so an externally created DBConnection object does not need to
	 * be provided.
	 * 
	 * @param appName
	 *            The application to get the database connection for.
	 * @param dbProfile
	 *            The particular database profile to load.
	 */
	public DataStore(String appName, String dbProfile) {
		super();
		boolean loadConnParms = false;
		_appName = appName;
		_dbProfile = dbProfile;

		/*
		 * srufle : May 17, 2004 7 : 37 : 04 PM Added to allow for a
		 * construction with no appName or dbProfile
		 */
		if (Util.isFilled(_appName)) {
			loadConnParms = true;
		} else if (Util.isFilled(_dbProfile)) {
			loadConnParms = true;
		}

		if (loadConnParms) {
			loadConnectionParms();
		}

	}

	/**
	 * This method adds a column to the DataStore using the datastore's default
	 * table. The column is not part of the primary key of the table and is not
	 * updatable.
	 * 
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @see DataStore#setDefaultTable
	 */
	public void addColumn(String column, int type) {
		addColumn(column, type, false, false);
	}

	/**
	 * This method adds a column to the DataStore using the datastore's default
	 * table.
	 * 
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @param primaryKey
	 *            True if the column is part of the primary key of the table it
	 *            is in.
	 * @param updatable
	 *            True if this column should be updated when the update method
	 *            is called.
	 * @see DataStore#setDefaultTable
	 */
	public void addColumn(String column, int type, boolean primaryKey, boolean updatable) {
		String table = null;
		int i = column.lastIndexOf(".");
		if (i >= 0) {
			table = column.substring(0, i);
			column = column.substring(i + 1);
		}
		_desc.addColumn(table, column, type, primaryKey, updatable, null);
	}

	/**
	 * This method adds a column to the DataStore using the datastore's default
	 * table. The column is not part of the primary key of the table and is not
	 * updatable.
	 * 
	 * @param table
	 *            The name of the table to add to the datastore.
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @see DataStore#setDefaultTable
	 */
	public void addColumn(String table, String column, int type) {
		_desc.addColumn(table, column, type, false, false, null);
	}

	/**
	 * This method adds a column to the DataStore
	 * 
	 * @param table
	 *            The name of the table containing the column.
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @param primaryKey
	 *            True if the column is part of the primary key of the table it
	 *            is in.
	 * @param updateable
	 *            True if this column should be updated when the update method
	 *            is called.
	 */
	public void addColumn(String table, String column, int type, boolean primaryKey, boolean updateable) {
		_desc.addColumn(table, column, type, primaryKey, updateable, null);
	}

	/**
	 * This method adds a column to the DataStore
	 * 
	 * @param table
	 *            The name of the table containing the column.
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @param primaryKey
	 *            True if the column is part of the primary key of the table it
	 *            is in.
	 * @param updateable
	 *            True if this column should be updated when the update method
	 *            is called.
	 * @param internalName
	 *            The name to use to get the value of the column later. If not
	 *            used, the column can be referenced by tablename.columnname
	 */
	public void addColumn(String table, String column, int type, boolean primaryKey, boolean updateable, String internalName) {
		_desc.addColumn(table, column, type, primaryKey, updateable, internalName);
	}

	/**
	 * This method adds a column to the DataStore and passes the format to
	 * setFormat
	 * 
	 * @param table
	 *            The name of the table containing the column.
	 * @param column
	 *            The name of the column to add to the datastore.
	 * @param type
	 *            The type of the column to add to the datastore. This must be
	 *            one of the "TYPE" constants in the class.
	 * @param primaryKey
	 *            True if the column is part of the primary key of the table it
	 *            is in.
	 * @param updateable
	 *            True if this column should be updated when the update method
	 *            is called.
	 * @param colFormat
	 *            The format to use in parsing or formatting the value.
	 * @see DataStoreBuffer#setFormat
	 */

	public void addFormattedColumn(String table, String column, int type, boolean primaryKey, boolean updateable, String colFormat) {
		try {
			_desc.addColumn(table, column, type, primaryKey, updateable, null);
			setFormat(table + "." + column, colFormat);
		} catch (DataStoreException e) {
			MessageLog.writeAssertionMessage("addFormattedColumn thru a DataStoreException this should not happen !!", this);
		}
	}

	/**
	 * This method is used to set the join clause connecting two or more tables
	 * used in the DataStore. This is not necessary for single table DataStores.
	 * 
	 * @param columnList1:
	 *            A list of column names seperated by commas on the left side of
	 *            the join
	 * @param columnList2:
	 *            A list of column names seperated by commas on the right side
	 *            of the join
	 * @param outerJoin:
	 *            True if this is an outer join and false for a regular join.
	 *            Note: the left side of the join (columnList 1) will always be
	 *            the outer member of the join.
	 */
	public void addJoin(String columnList1, String columnList2, boolean outerJoin) {
		_desc.addJoin(columnList1, columnList2, outerJoin);
	}

	//fc 06/11/04: Added this method to specify in One to Many Relationship
	// Model
	/**
	 * This method is used to set the join clause connecting two or more tables
	 * used in the DataStore. This is not necessary for single table DataStores.
	 * 
	 * @param columnList1:
	 *            A list of column names seperated by commas on the left side of
	 *            the join
	 * @param columnList2:
	 *            A list of column names seperated by commas on the right side
	 *            of the join
	 * @param outerJoin:
	 *            True if this is an outer join and false for a regular join.
	 *            Note: the left side of the join (columnList 1) will always be
	 *            the outer member of the join.
	 * @param relationType:
	 *            Specifies the type of relation the join represents.
	 *            (RELATION_ONE_TO_ONE, RELATION_ONE_TO_MANY,
	 *            RELATION_MANY_TO_ONE)
	 */
	public void addJoin(String columnList1, String columnList2, boolean outerJoin, int relationType) {
		if (!_manytoonerelationship && relationType > RELATION_MANY_TO_ONE)
			_manytoonerelationship = true;
		_desc.addJoin(columnList1, columnList2, outerJoin, relationType);
	}

	/**
	 * Adds a new listerner to this page to handle custom page events events.
	 */
	public void addSQLPreviewListener(SQLPreviewListener p) {
		if (_listeners == null)
			_listeners = new Vector();

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SQLPreviewListener) _listeners.elementAt(i)) == p)
				return;
		}

		_listeners.addElement(p);
	}

	/**
	 * This method adds a table alias to the datastore. Calls to the addColumn
	 * method can then use the alias specified to represent the table name.
	 * 
	 * @param table
	 *            The name of the table to alias.
	 * @param alias
	 *            The name of the alias.
	 */
	public void addTableAlias(String table, String alias) {
		_desc.addAlias(table, alias);
	}

	/**
	 * This method indicates whether all the data in the result set that is to
	 * be returned by the last retrieve statement has in fact been retrieved.
	 * 
	 * @return true if all the data has been retrieved and false if the retrieve
	 *         is still in progress.
	 */
	public boolean allDataRetrieved() {
		return (!_retrieveInProgress);
	}

	/**
	 * This method is called from JspController when the DataStore needs to be
	 * automatically retrieved.
	 */
	public void autoRetrieve() {
		try {
			if (getGroupAutoRetrieveCriteria() != null)
				setHaving(buildCriteriaString(getGroupAutoRetrieveCriteria()));

			if (getAutoRetrieveCriteria() != null)
				retrieve(buildCriteriaString(getAutoRetrieveCriteria()));
			else
				retrieve();

			waitForRetrieve();
			gotoFirst();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("autoRetrieve()", e, this);
		}
	}

	/**
	 * This method will build a datastore buffer from the columns in the sql
	 * statement and retrieves the data. After the initial call to this method
	 * (for subsequent retrieves), the execute method can be used and is more
	 * efficient.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param sql
	 *            The sql to execute.
	 */
	public synchronized void buildBuffer(DBConnection conn, String sql) throws java.sql.SQLException, DataStoreException {
		reset();
		_retrieveInProgress = true;
		retrieve(conn, null, null, null, sql, true);
	}

	/**
	 * This method will build a datastore buffer from the columns in the sql
	 * statement and retrieves the data. After the initial call to this method
	 * (for subsequent retrieves), the execute method can be used and is more
	 * efficient.
	 * 
	 * @param sql
	 *            The sql to execute.
	 */
	public synchronized void buildBuffer(String sql) throws java.sql.SQLException, DataStoreException {
		if (_appName == null)
			throw new DataStoreException("This version of execute requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		_releaseConn = true;
		buildBuffer(conn, sql);
	}

	private void buildBuffer(ResultSetMetaData m, String sql) throws Exception {
		StringTokenizer work = null;
		String test = sql.toUpperCase().trim();
		if (test.startsWith("SELECT")) {
			int pos = test.indexOf(" DISTINCT ");
			if (pos == -1)
				pos = 7;
			else
				pos += 10;
			int pos2 = test.indexOf(" FROM ");
			// sr 10-24-2001
			// pos2 was off by one. I think we were alway clipping the last char
			// sql.substring(beginindex,endindex)
			// but nobody was referencing columns by name
			work = new StringTokenizer(sql.substring(pos, pos2 + 1), ",", false);

		} else if (test.startsWith("INSERT") || test.startsWith("UPDATE") || test.startsWith("DEL")) {
			throw new DataStoreException("The buildBuffer method can only be used with SELECT statements or stored procedure calls");
		}

		//Rebuild the DataStore buffer before we retrieve
		_desc = new DSDataStoreDescriptor();

		int cc = m.getColumnCount();
		for (int i = 1; i <= cc; i++) {
			int type = m.getColumnType(i);

			switch (type) {
				case Types.CHAR :
				case Types.VARCHAR :
				case Types.LONGVARCHAR :
				case Types.NULL :
					type = DATATYPE_STRING;
					break;
				case Types.INTEGER :
					type = DATATYPE_INT;
					break;
				case Types.FLOAT :
					type = DATATYPE_FLOAT;
					break;
				case Types.DOUBLE :
				case Types.REAL :
				case Types.NUMERIC :
				case Types.DECIMAL :
					type = DATATYPE_DOUBLE;
					break;
				case Types.TIMESTAMP :
					type = DATATYPE_DATETIME;
					break;
				case Types.DATE :
					type = DATATYPE_DATE;
					break;
				case Types.BIGINT :
					type = DATATYPE_LONG;
					break;
				case Types.BINARY :
				case Types.LONGVARBINARY :
				case Types.VARBINARY :
				case Types.OTHER :
					type = DATATYPE_BYTEARRAY;
					break;
				case Types.SMALLINT :
				case Types.TINYINT :
				case Types.BIT :
					type = DATATYPE_SHORT;
					break;
				case Types.TIME :
					type = DATATYPE_TIME;
					break;
			}

			String table = null;
			String column = null;
			String name = "*";
			if (work == null)
				name = "*";
			else if (work.hasMoreTokens()) {
				name = work.nextToken();
				while ((!parensMatch(name)) && work.hasMoreTokens())
					name += work.nextToken();
				name = name.trim();
			}

			if (name.equals("*")) {
				String label = m.getColumnLabel(i);
				int ndx = 1;
				while (getColumnIndex(label) > -1) {
					label += ndx++;
				}
				addColumn(null, m.getColumnName(i), type, false, false, label);
			} else {
				int pos = name.indexOf(".");
				if (pos == -1) {
					column = m.getColumnLabel(i);
					name = column;
				} else {
					table = name.substring(0, pos);
					column = name.substring(pos + 1);
				}

				StringBuffer sb = new StringBuffer(name.length());
				for (int j = 0; j < name.length(); j++) {
					char c = name.charAt(j);
					if (c == ')' || c == '(' || c == '*' || c == ',' || c == ' ' || c == '+' || c == '-' || c == '/')
						sb.append('_');
					else
						sb.append(c);
				}
				name = sb.toString();
				int ndx = 1;
				while (getColumnIndex(name) > -1) {
					name += ndx++;
				}
				addColumn(table, column, type, false, false, name);
			}

		}
	}

	/**
	 * This method builds a sql string from an AutoRetrieveCriteria object
	 */
	public static String buildCriteriaString(AutoRetrieveCriteria crit) {
		StringBuffer ret = new StringBuffer();

		for (int i = 0; i < crit.getCriteriaCount(); i++) {
			if (crit.getPrefix(i) != null)
				ret.append(crit.getPrefix(i));
			ret.append(crit.getColumn(i));
			ret.append(' ');
			ret.append(crit.getOperator(i));
			ret.append(' ');
			ret.append(crit.getValue(i));
			if (crit.getSuffix(i) != null)
				ret.append(crit.getSuffix(i));
			if (crit.getConnector(i) != null) {
				ret.append(' ');
				ret.append(crit.getConnector(i));
				ret.append(' ');
			}
		}

		if (ret.length() == 0)
			return null;
		else
			return ret.toString();
	}

	/**
	 * If a retrieve is in progress, this method will cancel it.
	 */
	public void cancelRetrieve() {
		_retrieveInProgress = false;
		interruptWaitingRetrieveThreads();
	}

	/**
	 * This method will instantiate the datastore described in the className
	 * field. The datastore class must have at least one of three constructors.
	 * One that takes two strings (application and dbprofile), One that takes
	 * one string (application), or a no args constructor. If the app and
	 * profile constructor is found, it will be called first, followed by the
	 * app only constructor, followed by the no args constructor.
	 */
	public static DataStoreBuffer constructDataStore(String className, String applicationName) {
		return constructDataStore(className, applicationName, null);
	}

	/**
	 * This method will instantiate the datastore described in the className
	 * field. The datastore class must have at least one of three constructors.
	 * One that takes two strings (application and dbprofile), One that takes
	 * one string (application), or a no args constructor. If the app and
	 * profile constructor is found, it will be called first, followed by the
	 * app only constructor, followed by the no args constructor.
	 */
	public static DataStoreBuffer constructDataStore(String className, String applicationName, String profileName) {
		DataStoreBuffer ret = null;
		try {
			Class stringClass = new String().getClass();
			Class cl=null;
			className=className.trim();
			boolean multiClass=false;
			if (className.indexOf(",") == -1 && className.indexOf(" ") == -1)
				 cl = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
			else
				 multiClass=true;

			if (multiClass || (cl != null && !Util.instanceOf(cl, DataStoreBuffer.class)))
				return new BeanDataStore(className, multiClass);
			

			Constructor cs[] = cl.getConstructors();
			Constructor noArgsCon = null;
			Constructor appCon = null;
			Constructor appProfileCon = null;
			for (int i = 0; i < cs.length; i++) {
				Class[] args = cs[i].getParameterTypes();
				if (args.length == 2) {
					if (args[0] == stringClass && args[1] == stringClass)
						appProfileCon = cs[i];
				} else if (args.length == 1) {
					if (args[0] == stringClass)
						appCon = cs[i];
				} else if (args.length == 0) {
					noArgsCon = cs[i];
				}
			}

			if (applicationName == null) {
				if (noArgsCon != null) {
					Object[] args = new Object[0];
					ret = (DataStoreBuffer) noArgsCon.newInstance(args);
				}
			} else {
				if (appProfileCon != null) {
					Object[] args = new String[2];
					args[0] = applicationName;
					args[1] = profileName;
					ret = (DataStoreBuffer) appProfileCon.newInstance(args);
				} else if (appCon != null) {
					Object[] args = new String[1];
					args[0] = applicationName;
					ret = (DataStoreBuffer) appCon.newInstance(args);
				} else if (noArgsCon != null) {
					Object[] args = new Object[0];
					ret = (DataStoreBuffer) noArgsCon.newInstance(args);
				}
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("DataStore.constructDataStore()", e, null);
		}
		return ret;
	}

	/**
	 * This method is not used.
	 */
	public void destroy() throws Exception {
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 */
	public int estimateRowsRetrieved() throws DataStoreException, SQLException {
		String nullSt = null;
		return estimateRowsRetrieved(nullSt);
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 */
	public int estimateRowsRetrieved(DBConnection conn) throws SQLException {
		String st = null;
		return estimateRowsRetrieved(conn, st);
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	public int estimateRowsRetrieved(DBConnection conn, QBEBuilder criteria) throws SQLException {
		return estimateRowsRetrieved(conn, criteria.generateSQLFilter(this));
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	public int estimateRowsRetrieved(QBEBuilder criteria) throws DataStoreException, SQLException {
		return estimateRowsRetrieved(criteria.generateSQLFilter(this));
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	public int estimateRowsRetrieved(DBConnection conn, String criteria) throws SQLException {

		int count = 0;
		try {
			Statement st = conn.createStatement();
			String sql = getCountSelectStatement(conn, criteria);
			ResultSet r = st.executeQuery(sql);
			if (r.next())
				count = r.getInt(1);
			r.close();
			st.close();
		} catch (java.sql.SQLException e) {
			throw e;
		}
		return count;
	}

	//fc 06/24/04
	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select in ? format.
	 * @param psp
	 *            the prepared statement parameters for the criteria.
	 */
	public int estimateRowsRetrieved(DBConnection conn, String criteria, PreparedStatementParms psp) throws SQLException {

		int count = 0;
		try {
			String sql = getCountSelectStatement(conn, criteria);
			PreparedStatement pst = conn.prepareStatement(sql);
			if (psp != null) {
				DataSourceIn dsi = getDSIn(conn);

				if (dsi instanceof DSDataSourceJDBC) {
					for (int i = 0; i < psp.getParmCount(); i++)
						DSDataSourceJDBC.prepareForType(null, pst, psp.getParm(i), psp.getDataType(i), i + 1);
				}
			}
			ResultSet r = pst.executeQuery();
			if (r.next())
				count = r.getInt(1);
			r.close();
			pst.close();
		} catch (java.sql.SQLException e) {
			throw e;
		}
		return count;
	}

	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	public int estimateRowsRetrieved(String criteria) throws DataStoreException, SQLException {
		if (_appName == null)
			throw new DataStoreException("This version of retrieve requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn = null;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		int ret = 0;
		try {
			ret = estimateRowsRetrieved(conn, criteria);
		} catch (SQLException e) {
			if (conn != null)
				conn.freeConnection();
			throw e;
		}

		if (conn != null)
			conn.freeConnection();

		return ret;
	}

	//fc 06/24/04
	/**
	 * Use this method to get the amount of rows that will be retrieved when a
	 * data store retrieve is executed.
	 * 
	 * @param criteria
	 *            The selection criteria to use for the select in ? format.
	 * @param psp
	 *            the prepared statement parameters for the criteria.
	 */
	public int estimateRowsRetrieved(String criteria, PreparedStatementParms psp) throws DataStoreException, SQLException {
		if (_appName == null)
			throw new DataStoreException("This version of retrieve requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn = null;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		int ret = 0;
		try {
			ret = estimateRowsRetrieved(conn, criteria, psp);
		} catch (SQLException e) {
			if (conn != null)
				conn.freeConnection();
			throw e;
		}

		if (conn != null)
			conn.freeConnection();

		return ret;
	}

	/**
	 * Executes the supplied sql instead of the generated sql. This method will
	 * ues the SQL statement supplied and load the returned data into the
	 * DataStore buffer. The column types and column order in the SQL supplied
	 * must match the types and columns in the buffer.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param sql
	 *            The sql to execute.
	 */
	public synchronized void execute(DBConnection conn, String sql) throws java.sql.SQLException {
		retrieve(conn, null, null, null, sql, false);
	}

	/**
	 * Executes the supplied sql instead of the generated sql. This method will
	 * ues the SQL statement supplied and load the returned data into the
	 * DataStore buffer. The column types and column order in the SQL supplied
	 * must match the types and columns in the buffer.
	 * 
	 * @param sql
	 *            The sql to execute.
	 */
	public synchronized void execute(String sql) throws java.sql.SQLException, DataStoreException {
		if (_appName == null)
			throw new DataStoreException("This version of execute requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		_releaseConn = true;
		execute(conn, sql);
	}

	/**
	 * Executes the stored procuedure with no parameters.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param proc
	 *            The procedure statement to execute.
	 */
	public synchronized void executeProc(DBConnection conn, String proc) throws java.sql.SQLException {
		executeProc(conn, proc, null);
	}

	/**
	 * Executes the sql statement and retrieves to data. The datatypes and
	 * number of columns in the result set must exactly match the columns in the
	 * specified sql statement.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param proc
	 *            The procedure to execute.
	 * @param parms
	 *            A StoredProcedureParms object containing the parameters to
	 *            pass to the proc.
	 */
	public synchronized void executeProc(DBConnection conn, String proc, StoredProcedureParms parms) throws java.sql.SQLException {
		retrieve(conn, null, proc, parms, null, false);
	}

	/**
	 * Executes the procuedure and retrieves the data. The datatypes and number
	 * of columns in the result set must exactly match the columns in the
	 * specified sql statement.
	 * 
	 * @param proc
	 *            The procedure to execute.
	 */

	public void executeProc(String proc) throws java.sql.SQLException, DataStoreException {
		executeProc(proc, null);
	}

	/**
	 * Executes the sql statement and retrieves to data. The datatypes and
	 * number of columns in the result set must exactly match the columns in the
	 * specified sql statement.
	 * 
	 * @param proc
	 *            The procedure to execute.
	 * @param parms
	 *            A StoredProcedureParms object containing the parameters to
	 *            pass to the proc.
	 */
	public synchronized void executeProc(String proc, StoredProcedureParms parms) throws java.sql.SQLException, DataStoreException {
		if (_appName == null)
			throw new DataStoreException("This version of execute requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		_releaseConn = true;
		executeProc(conn, proc, parms);
	}

	/**
	 * This method will fix quotes in a string constant so it can be used in a
	 * SQL Statement. This method will automatically figure out which DBMS is in
	 * use and make the changes accordingly. Because of this, this method can
	 * only be called for datastores created with a constructor that takes
	 * database profile arguments (not the no args constructor). Note: You
	 * should only use this method with character data (DATATYPE_STRING) types.
	 */
	public String fixQuote(String data) {
		DBConnection conn = null;

		if (_connType == null) {
			try {
				if (_appName != null) {
					if (_dbProfile != null)
						conn = DBConnection.getConnection(_appName, _dbProfile);
					else
						conn = DBConnection.getConnection(_appName);
				}

				_connType = conn.getDBMS();
			} catch (Exception e) {
				_connType = "";
			} finally {
				if (conn != null)
					conn.freeConnection();
			}
		}

		return fixQuote(data, DATATYPE_STRING, _connType);

	}

	/**
	 * This method will format a Date value so it can be used in an SQL
	 * Statement. This method will automatically figure out which DBMS is in use
	 * and make the changes accordingly. Because of this, this method can only
	 * be called for datastores created with a constructor that takes database
	 * profile arguments (not the no args constructor).
	 */
	public String formatDateTime(java.util.Date data) {
		DBConnection conn = null;
		// Initialize the connection type if necessary.
		if (_connType == null) {
			try {
				if (_appName != null) {
					if (_dbProfile != null)
						conn = DBConnection.getConnection(_appName, _dbProfile);
					else
						conn = DBConnection.getConnection(_appName);
				}

				_connType = conn.getDBMS();
			} catch (Exception e) {
				_connType = "";
			} finally {
				if (conn != null)
					conn.freeConnection();
			}
		}
		// Format the data according to connection type.
		SimpleDateFormat df;
		if (_connType.equals(DBConnection.DB2VSE_CONNECTION) || _connType.equals(DBConnection.DB2MVS_CONNECTION))
			df = _dateTimeFormatVSE;
		else
			df = _dateTimeFormatSTD;
		return "'" + df.format(data) + "'";
	}

	/**
	 * This method returns the name of one of the aliases used by the datastore.
	 * Use the method getAliasCount() to find out how many tables or aliases are
	 * used by the datastore.
	 * 
	 * @return The table name.
	 */
	public String getAlias(int tableNo) throws DataStoreException {
		if (tableNo < 0 || tableNo >= _desc.getAliasCount())
			throw (new DataStoreException("Table Number " + tableNo + " is out of range "));

		return _desc.getAlias(tableNo).getAlias();
	}

	/**
	 * This method returns the number of aliases used by the datastore.
	 */
	public int getAliasCount() {
		return _desc.getAliasCount();
	}

	/**
	 * Use this method to get whether or not the datastore will do a concurrency
	 * check when rows are updated and deleted.
	 */
	public boolean getCheckConcurrency() {
		return _checkConcurrency;
	}

	/**
	 * This method returns the database name of the column in the data store
	 * given its index.
	 */
	public String getColumnDatabaseName(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		String table = d.getTable();
		if (table == null)
			table = _desc.getDefaultTable();

		if (table == null)
			return d.getColumn();
		else
			return table + "." + d.getColumn();
	}

	/**
	 * This method returns the name of the database table that the column is
	 * for.
	 */
	public String getColumnFormat(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		if (d.getFormat() == null)
			return null;

		return d.getFormat().toString();
	}

	/**
	 * This method returns the name of the database table that the column is
	 * for.
	 */
	public String getColumnInternalName(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		return d.getInternalName();
	}

	/**
	 * This method returns the name of the database table that the column is
	 * for.
	 */
	public String getColumnTableName(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		String table = d.getTable();
		if (table == null)
			table = _desc.getDefaultTable();

		return table;
	}

	/**
	 * This method returns the database name of the column not including the
	 * table name.
	 */
	public String getColumnDBColumnName(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		return d.getColumn();
	}

	/**
	 * This method is used to get whether a column should be used in the update,
	 * delete concurrency check.
	 */
	public boolean getConcurrencyCheckColumn(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		return c.getConcurrency();
	}

	/**
	 * This method is used to get whether a column should be used in the update,
	 * delete concurrency check.
	 */
	public boolean getConcurrencyCheckColumn(String col) throws DataStoreException {
		int c = getColumnIndex(col);
		return getConcurrencyCheckColumn(c);
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @param conn
	 *            com.salmonllc.sql.DBConnection
	 * @param criteria
	 *            java.lang.String
	 */
	private String getCountSelectStatement(DBConnection conn, String criteria) {
		DataSourceIn dsIn = getDSIn(conn);
		if (dsIn instanceof DSDataSourceJDBC)
			try {
				return ((DSDataSourceJDBC) dsIn).generateSelect(this, criteria, true);
			} catch (Exception e) {
				return null;
			}
		else
			return null;
	}

	/**
	 * This method is used to get selection criteria filtering for the result
	 * set of the datastore.
	 */
	public String getCriteria() {
		return _desc.getWhereClause();
	}

	/**
	 * This method returns the default table for the datastore
	 */
	public String getDefaultTable() {
		return _desc.getDefaultTable();
	}

	/**
	 * This method will return whether the distinct flag in the data store is
	 * set. The flag indicates that the distinct keyword should be placed at the
	 * beginning of a select statement.
	 */
	public boolean getDistinct() {
		return _desc.getDistinct();
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @return com.salmonllc.sql.DataSourceIn
	 * @param conn
	 *            com.salmonllc.sql.DBConnection
	 */
	private DataSourceIn getDSIn(DBConnection conn) {
		if (_dsIn != null)
			return _dsIn;
		else {
			if (conn.getDBMS().equals(DBConnection.DB2_CONNECTION))
				return new DSDataSourceDB2();
			else if (conn.getDBMS().equals(DBConnection.DB2VSE_CONNECTION))
				return new DSDataSourceDB2VSE();
			else if (conn.getDBMS().equals(DBConnection.DB2MVS_CONNECTION))
				return new DSDataSourceDB2MVS();
			else if (conn.getDBMS().equals(DBConnection.ORACLE_CONNECTION))
				return new DSDataSourceOracle();
			else if (conn.getDBMS().equals(DBConnection.MYSQL_CONNECTION))
				return new DSDataSourceMySql();
			else if (conn.getDBMS().equals(DBConnection.MSSQLSEVER_CONNECTION))
				return new DSDataSourceMSSQL();
			else if (conn.getDBMS().equals(DBConnection.DB2400_CONNECTION))
				return new DSDataSourceDB2400();
			else if (conn.getDBMS().equals(DBConnection.INGRES_CONNECTION))
				return new DSDataSourceIngres();
			else if (conn.getDBMS().equals(DBConnection.FIREBIRDSQL_CONNECTION))
				return new DSDataSourceFireBird();
			else if (conn.getDBMS().equals(DBConnection.POSTGRES_CONNECTION))
				return new DSDataSourcePostGres();
			else if (conn.getDBMS().equals(DBConnection.ANSISQL92_CONNECTION))
				return new DSDataSourceANSISQL92();
			else
				return new DSDataSourceSybase();
		}
	}

	/**
	 * This method was created in VisualAge.
	 * 
	 * @return com.salmonllc.sql.DataSourceIn
	 * @param conn
	 *            com.salmonllc.sql.DBConnection
	 */
	private DataSourceOut getDSOut(DBConnection conn) {
		if (_dsOut != null)
			return _dsOut;
		else {
			if (conn.getDBMS().equals(DBConnection.DB2_CONNECTION))
				return new DSDataSourceDB2();
			else if (conn.getDBMS().equals(DBConnection.DB2VSE_CONNECTION))
				return new DSDataSourceDB2VSE();
			else if (conn.getDBMS().equals(DBConnection.DB2MVS_CONNECTION))
				return new DSDataSourceDB2MVS();
			else if (conn.getDBMS().equals(DBConnection.ORACLE_CONNECTION))
				return new DSDataSourceOracle();
			else if (conn.getDBMS().equals(DBConnection.MYSQL_CONNECTION))
				return new DSDataSourceMySql();
			else if (conn.getDBMS().equals(DBConnection.DB2400_CONNECTION))
				return new DSDataSourceDB2400();
			else if (conn.getDBMS().equals(DBConnection.MSSQLSEVER_CONNECTION))
				return new DSDataSourceMSSQL();
			else if (conn.getDBMS().equals(DBConnection.INGRES_CONNECTION))
				return new DSDataSourceIngres();
			else if (conn.getDBMS().equals(DBConnection.FIREBIRDSQL_CONNECTION))
				return new DSDataSourceFireBird();
			else if (conn.getDBMS().equals(DBConnection.POSTGRES_CONNECTION))
				return new DSDataSourcePostGres();
			else if (conn.getDBMS().equals(DBConnection.ANSISQL92_CONNECTION))
				return new DSDataSourceANSISQL92();
			else
				return new DSDataSourceSybase();
		}
	}

	/**
	 * Use this method to get whether or not the datastore does a retrieve in a
	 * separate thread.
	 */
	public boolean getEnableThreads() {
		return _threaded;
	}

	/**
	 * This method will return the autoretrieve group criteria used for the
	 * datastore.
	 */
	public AutoRetrieveCriteria getGroupAutoRetrieveCriteria() {
		return _havingCrit;
	}

	/**
	 * This method returns the column list in the group by clause.
	 */
	public String getGroupBy() {
		return _desc.getGroupByClause();
	}

	/**
	 * This method returns the having clause for the datastore.
	 */
	public String getHaving() {
		return _desc.getHavingClause();
	}

	/**
	 * This method returns the number of columns in a particular join.
	 */
	public int getJoinColumnCount(int joinNo) {
		return _desc.getJoin(joinNo).getLeftCount();
	}

	/**
	 * This method returns the number of joins in the datastore.
	 */
	public int getJoinCount() {
		return _desc.getJoinCount();
	}

	/**
	 * This method returns a column on the left side of the join.
	 */
	public String getJoinLeftColumn(int joinNo, int colNo) {
		return _desc.getJoin(joinNo).getLeftColumn(colNo);
	}

	/**
	 * This method returns the true if a particular join is outer.
	 */
	public boolean getJoinOuter(int joinNo) {
		return _desc.getJoin(joinNo).isOuter();
	}

	//fc 06/11/04: Implements the newly added method to DataStoreInterface.
	/**
	 * This method returns the relation type of the join. (RELATION_ONE_TO_ONE,
	 * RELATION_ONE_TO_MANY, RELATION_MANY_TO_ONE)
	 */
	public int getJoinRelationType(int joinNo) {
		return _desc.getJoin(joinNo).getRelationType();
	}

	/**
	 * This method returns a column on the right side of the join.
	 */
	public String getJoinRightColumn(int joinNo, int colNo) {
		return _desc.getJoin(joinNo).getRightColumn(colNo);
	}

	/**
	 * This method will return the maximum number of rows that the datastore
	 * will retrieve. If the max is set to -1, the datastore will retrieve all
	 * rows in the result set. Otherwise it will stop retrieving when the max is
	 * reached.
	 */
	public int getMaxRows() {
		return _maxRows;
	}

	/**
	 * This method returns the order by clause for the datastore.
	 */
	public String getOrderBy() {
		return _desc.getOrderByClause();
	}

	/**
	 * This method creates a properties object containing the definition of the
	 * data store.
	 */
	public Properties getProperties() {

		Properties p = super.getProperties();
		String desc = "base.";

		p.put(desc + "updateMethod", getIntProperty(_updateMethod));
		p.put(desc + "checkConcurrency", getBoolProperty(_checkConcurrency));
		p.put(desc + "useBind", getBoolProperty(_useBind));
		p.put(desc + "maxRows", getIntProperty(_maxRows));
		p.put(desc + "remoteID", getStringProperty(getRemoteID()));
		p.put(desc + "DBMS", getStringProperty(getDBMS()));
		return p;
	}

	String getSelectStatement(DBConnection conn) {
		return getSelectStatement(conn, null);
	}

	public String getSelectStatement(DBConnection conn, String criteria) {
		DataSourceIn dsIn = getDSIn(conn);
		if (dsIn instanceof DSDataSourceJDBC) {
			try {
				return ((DSDataSourceJDBC) dsIn).generateSelect(this, criteria, false);
			} catch (Exception e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * This method returns the name of one of the tables used by the datastore.
	 * Use the method getAliasCount() to find out how many tables or aliases are
	 * used by the datastore.
	 * 
	 * @return The table name.
	 */
	public String getTable(int tableNo) throws DataStoreException {
		if (tableNo < 0 || tableNo >= _desc.getAliasCount())
			throw (new DataStoreException("Table Number " + tableNo + " is out of range "));

		return _desc.getAlias(tableNo).getTable();
	}

	/**
	 * This method returns an array of all the tables referenced in the
	 * datastore.
	 * 
	 * @param updateable
	 *            True if the table list should only include updateable tables
	 *            and false if it should include all.
	 */
	public String[] getTableList(boolean updateable) {

		DSColumnDescriptor col = null;

		Vector tables = new Vector();
		Vector pkey = new Vector();

		for (int i = 0; i < _desc.getColumnCount(); i++) {
			col = _desc.getColumn(i);
			String tableName = col.getTable();
			if (tableName == null)
				tableName = _desc.getDefaultTable();

			if ((!updateable) || col.isUpdateable() || col.isAutoIncrement()) {
				boolean found = false;
				for (int j = 0; j < tables.size(); j++) {
					if (tables.elementAt(j).equals(tableName)) {
						if (col.isPrimaryKey())
							pkey.setElementAt(new Boolean(true), j);
						found = true;
						break;
					}
				}
				if (!found && tableName != null) {
					tables.addElement(tableName);
					pkey.addElement(new Boolean(col.isPrimaryKey()));
				}
			}

		}

		if (updateable) {
			for (int i = pkey.size() - 1; i > -1; i--) {
				if (!((Boolean) pkey.elementAt(i)).booleanValue())
					tables.removeElementAt(i);
			}
		} else {
			for (int i = 0; i < getAliasCount(); i++) {
				try {
					String table = getTable(i);
					boolean found = false;
					for (int j = 0; j < tables.size(); j++) {
						if (tables.elementAt(j).equals(table))
							found = true;
					}
					if (!found && table != null)
						tables.addElement(table);
				} catch (Exception e) {
				}
			}
		}

		String retVal[] = new String[tables.size()];
		tables.copyInto(retVal);

		return retVal;
	}

	/**
	 * Use this method to get whether the DataStore will trim (remove trailing
	 * spaces) from columns retrieved from the database;
	 */
	public boolean getTrimStrings() {
		return _desc.getTrimStrings();
	}

	/**
	 * Gets the update method for the datastore.
	 * 
	 * @see DataStore#setUpdateMethod
	 */
	public int getUpdateMethod() {
		return _updateMethod;
	}

	/**
	 * This method is used to get whether a column should use bind variables for
	 * inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and
	 * BIND_DEFAULT (Use default for datastore)
	 */
	public int getUseBindColumn(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		return c.getUseBind();
	}

	/**
	 * This method is used to get whether a column should use bind variables for
	 * inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and
	 * BIND_DEFAULT (Use default for datastore)
	 */
	public int getUseBindColumn(String col) throws DataStoreException {
		int c = getColumnIndex(col);
		return getUseBindColumn(c);
	}

	/**
	 * Use this method to get whether or not the datastore will use bind
	 * variables as the default for updating or inserting columns.
	 */
	public boolean getUseBindForUpdate() {
		return _useBind;
	}

	//Added by FC on 2/21/03. Was added to handle JDBC drivers which have
	// problems with the cancel() method being called.
	//Example Driver: JSQLDriver from NetDirect.
	/**
	 * Use this method to get whether or not the cancel() method of a JDBC
	 * Statement is to be called when cancelling a retrieve.
	 */
	public boolean getEnableCancel() {
		return _enableCancel;
	}

	/**
	 * This method returns the name of the database table that the column is
	 * for.
	 */
	public boolean isColumnPrimaryKey(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);

		return d.isPrimaryKey();
	}

	/**
	 * This method returns whether a column is part of the primary key
	 */
	public boolean isPrimaryKey(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		return c.isPrimaryKey();
	}

	/**
	 * This method returns whether a column is part of the primary key
	 */
	public boolean isPrimaryKey(String col) throws DataStoreException {
		int c = getColumnIndex(col);
		return isPrimaryKey(c);
	}

	/**
	 * This method returns whether a column is updateable
	 */
	public boolean isUpdateable(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		return c.isUpdateable();
	}

	/**
	 * This method returns whether a column is updateable
	 */
	public boolean isUpdateable(String col) throws DataStoreException {
		int c = getColumnIndex(col);
		return isUpdateable(c);
	}

	/**
	 * This method returns whether a column is an auto increment primary key
	 */
	public boolean isAutoIncrement(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		return c.isAutoIncrement();
	}

	/**
	 * This method returns whether a column is an auto increment primary key
	 */
	public boolean isAutoIncrement(String col) throws DataStoreException {
		int c = getColumnIndex(col);
		return isAutoIncrement(c);
	}

	private void notifyListeners(int buffer, int row, String statement, DBConnection conn) throws SQLException {
		if (_listeners == null)
			return;
		if (statement == null || statement.length() == 0)
			return;

		SQLPreviewEvent e = new SQLPreviewEvent(this, buffer, row, statement, conn);

		for (int i = 0; i < _listeners.size(); i++) {
			((SQLPreviewListener) _listeners.elementAt(i)).SQLPreview(e);
		}
	}

	/**
	 * This method is not used.
	 */
	public boolean ping() throws Exception {
		return false;
	}

	/**
	 * This method removes a column to the DataStore. And resets the datastore.
	 * 
	 * @param column
	 *            The name of the column to remove to the datastore.
	 */
	public void removeColumn(String column) {
		_desc.removeColumn(column);
		reset();
	}

	/**
	 * This method is used to remove a join clause connecting two or more tables
	 * used in the DataStore.
	 * 
	 * @param left:
	 *            A list of column names seperated by commas on the left side of
	 *            the join
	 * @param right:
	 *            A list of column names seperated by commas on the right side
	 *            of the join
	 */
	public void removeJoin(String left, String right) {
		_desc.removeJoin(left, right);
	}

	/**
	 * This method removes a listener from the list of listeners that will be
	 * notified when a page event is fired.
	 */
	public void removeSQLPreviewListener(SQLPreviewListener p) {
		if (_listeners == null)
			return;

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SQLPreviewListener) _listeners.elementAt(i)) == p) {
				_listeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * This method removes a table alias from the datastore.
	 * 
	 * @param table
	 *            The name of the table to alias.
	 */
	public void removeTableAlias(String table) {
		_desc.removeTableAlias(table);
	}

	/**
	 * This method will clear all rows in the dataStore.
	 */
	public synchronized void reset() {
		if (_retrieveInProgress)
			cancelRetrieve();

		super.reset();
	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. You do not need to pass a database
	 * connection to this version of retrieve, but in order to use it the
	 * DataStore must be created with a constructor that passes an application
	 * (not the no args constructor).
	 */
	public void retrieve() throws java.sql.SQLException, DataStoreException {
		String criteria = null;
		retrieve(criteria);
	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 */
	public void retrieve(DBConnection conn) throws java.sql.SQLException, DataStoreException {
		String st = null;
		retrieve(conn, st);
	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. This method will replace the selection
	 * criteria with the supplied argument before the data is retieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	public void retrieve(DBConnection conn, String criteria) throws SQLException {
		retrieve(conn, criteria, null, null, null, false);
	}

	//fc 06/24/04
	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. This method will replace the selection
	 * criteria with the supplied argument before the data is retieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select in ? format.
	 * @param psp
	 *            the prepared statement parameters for the criteria.
	 */
	public void retrieve(DBConnection conn, String criteria, PreparedStatementParms psp) throws SQLException {
		retrieve(conn, criteria, null, psp, null, false);
	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. This method will replace the selection
	 * criteria with the supplied argument before the data is retieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria QBEBuilder to use for the select.
	 */
	public void retrieve(DBConnection conn, QBEBuilder criteria) throws SQLException {
		retrieve(conn, criteria.generateSQLFilter(this));
	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. This method will replace the selection
	 * criteria with the supplied argument before the data is retieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria QBEBuilder to use for the select.
	 */
	public void retrieve(QBEBuilder criteria) throws SQLException, DataStoreException {
		retrieve(criteria.generateSQLFilter(this));
	}
	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. This method will replace the selection
	 * criteria with the supplied argument before the data is retieved.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param criteria
	 *            The selection criteria to use for the select.
	 */
	private synchronized void retrieve(DBConnection conn, String criteria, String proc, StoredProcedureParms parms, String select, boolean buildBuffer) throws SQLException {
		reset();
		waitForCancel(); //just in case there was already a retrieve running
						 // wait for it to be cancelled before continuing
		_retrieveInProgress = true;

		try {
			try {
				_curDsIn = getDSIn(conn);

				if (_curDsIn instanceof DSDataSourceJDBC) {
					if (proc != null)
						((DSDataSourceJDBC) _curDsIn).setProc(proc, parms);
					else if (select != null)
						((DSDataSourceJDBC) _curDsIn).setSelect(select);
					//fc 06/24/04
					else if (criteria != null && parms instanceof PreparedStatementParms) {
						((DSDataSourceJDBC) _curDsIn).setSelType(DSDataSourceJDBC.SEL_GEN_PREP_STMT);
						((DSDataSourceJDBC) _curDsIn).setParms(parms);
					}
				}

				_curDsIn.preRetrieve(this, criteria, false, conn);
				if (_curDsIn instanceof DSDataSourceJDBC) {
					notifyListeners(BUFFER_STANDARD, -1, ((DSDataSourceJDBC) _curDsIn).getSelect(), conn);
					if (buildBuffer)
						buildBuffer(((DSDataSourceJDBC) _curDsIn).getMetaData(), select);
				}
			} catch (java.sql.SQLException e) {
				_curDsIn.postRetrieve(this);
				_retrieveInProgress = false;
				interruptWaitingRetrieveThreads();
				if (_releaseConn) {
					conn.freeConnection();
					_releaseConn = false;
				}
				throw (e);
			}
		} catch (Exception e) {
			if (_releaseConn) {
				conn.freeConnection();
				_releaseConn = false;
			}
			if (e instanceof java.sql.SQLException)
				throw ((java.sql.SQLException) e);
			else
				MessageLog.writeErrorMessage("Retrieve", e, this);
		}

		_dbConn = conn;

		if (conn != null)
			conn.registerRetrieve(this);
		if (_threaded) {
			Thread t = new Thread(this);
			t.start();
		} else {
			run();
		}

	}

	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. You do not need to pass a database
	 * connection to this version of retrieve, but in order to use it the
	 * DataStore must be created with a constructor that passes an application
	 * (not the no args constructor).
	 */
	public synchronized void retrieve(String criteria) throws java.sql.SQLException, DataStoreException {
		DBConnection conn = null;
		if (_dbConn == null) {
			if (_appName == null)
				throw new DataStoreException("This version of retrieve requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

			if (_dbProfile == null)
				conn = DBConnection.getConnection(_appName);
			else
				conn = DBConnection.getConnection(_appName, _dbProfile);

			_releaseConn = true;
		} else {
			conn = _dbConn;
		}

		retrieve(conn, criteria);
	}

	//fc 06/24/04
	/**
	 * Executes the sql statement and retrieves to data. The data is retrieved
	 * in a new thread so the beginning of the result set can be accessed before
	 * all the data has been retrieved. You do not need to pass a database
	 * connection to this version of retrieve, but in order to use it the
	 * DataStore must be created with a constructor that passes an application
	 * (not the no args constructor).
	 * 
	 * @param criteria
	 *            The selection criteria to use for the select in ? format.
	 * @param psp
	 *            the prepared statement parameters for the criteria.
	 */
	public synchronized void retrieve(String criteria, PreparedStatementParms psp) throws java.sql.SQLException, DataStoreException {
		DBConnection conn = null;
		if (_dbConn == null) {
			if (_appName == null)
				throw new DataStoreException("This version of retrieve requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

			if (_dbProfile == null)
				conn = DBConnection.getConnection(_appName);
			else
				conn = DBConnection.getConnection(_appName, _dbProfile);

			_releaseConn = true;
		} else {
			conn = _dbConn;
		}

		retrieve(conn, criteria, psp);
	}

	/**
	 * The run method for the thread that retrieves the data from the database.
	 * This method should not be called directly. Instead use the retrieve
	 * method.
	 */
	public void run() {
		try {

			DataStoreRow row = new DataStoreRow(this, new DSDataRow(_desc), _desc);

			while (_curDsIn.retrieveRow(this, row)) {
				_rows.addElement(row.getDSDataRow());

				if (!_retrieveInProgress || (_maxRows > -1 && _rows.size() >= _maxRows)) {
					_cancelInProgress = true;
					_retrieveInProgress = false;
					interruptWaitingRetrieveThreads();
					if (_curDsIn instanceof DSDataSourceJDBC)
						((DSDataSourceJDBC) _curDsIn).cancel();
					break;
				}
				Thread.yield();

				row.setDSDataRow(new DSDataRow(_desc));
			}
			_curDsIn.postRetrieve(this);
			notifyListeners(ModelChangedEvent.TYPE_DATA_LOADED);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("DataStore.run", e, this);
			_cancelInProgress = false;
			interruptWaitingCancelThreads();
		}

		if (_dbConn != null) {
			_dbConn.unregisterRetrieve(this);
			if (_releaseConn) {
				_dbConn.freeConnection();
				_dbConn = null;
				_releaseConn = false;
			}
		}

		_retrieveInProgress = false;
		interruptWaitingRetrieveThreads();
		_cancelInProgress = false;
		interruptWaitingCancelThreads();

	}

	/**
	 * This method sets the name of the application that the datastore is
	 * running in
	 */
	public void setAppName(String appName) {
		_appName = appName;
	}

	/**
	 * This method gets the name of the application that the datastore is
	 * running in
	 */
	public String getAppName() {
		return _appName;
	}

	/**
	 * Use this method to set whether or not the datastore will do a concurrency
	 * check when rows are updated and deleted. defaults to falses
	 */
	public void setCheckConcurrency(boolean truefalse) {
		_checkConcurrency = truefalse;
	}

	/**
	 * This method is used to indicate whether a column should be used in the
	 * update, delete concurrency check.
	 */
	public void setConcurrencyCheckColumn(int col, boolean truefalse) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setConcurrency(truefalse);
	}

	/**
	 * This method is used to indicate whether a column should be used in the
	 * update, delete concurrency check.
	 */
	public void setConcurrencyCheckColumn(String col, boolean truefalse) throws DataStoreException {
		int c = getColumnIndex(col);
		setConcurrencyCheckColumn(c, truefalse);
	}

	/**
	 * This method sets the database connection that the datastore will use for
	 * retrieves.
	 */
	public void setConnection(DBConnection conn) {
		_dbConn = conn;
	}

	/**
	 * This method is used to set selection criteria filtering for the result
	 * set of the datastore.
	 * 
	 * @param criteria
	 *            The SQL used in the WHERE clause to filter rows in the result
	 *            set.
	 */
	public void setCriteria(String criteria) {
		_desc.setWhereClause(criteria);
	}

	/**
	 * This method is used to change the data source that the data store will
	 * use to retrieve the data
	 */
	public void setDataSourceIn(DataSourceIn dsIn) {
		_dsIn = dsIn;
	}

	/**
	 * This method is used to change the data source that the data store will
	 * use update data
	 */
	public void setDataSourceOut(DataSourceOut dsOut) {
		_dsOut = dsOut;
	}

	/**
	 * This method sets the name of the database profile that datastore will use
	 */
	public void setDBProfile(String dbProfile) {
		_dbProfile = dbProfile;
	}

	/**
	 * If this DataStore will retrieve data from a single table, it isn't
	 * necessary to pass it each time a column is added to the select statement.
	 * Instead this method can be called an AddColumn method that does not
	 * include table as a parameter.
	 * 
	 * @param table
	 *            The default table for this DataStore
	 */
	public void setDefaultTable(String table) {
		_desc.setDefaultTable(table);
	}

	/**
	 * This method will set the distinct flag in the data store.
	 * 
	 * @param distinct
	 *            if the flag is set to true the generated select statement will
	 *            begin with a select distinct.
	 */
	public void setDistinct(boolean distinct) {
		_desc.setDistinct(distinct);
	}

	/**
	 * Use this method to set whether or not the datastore will do retrieves in
	 * a separate thread
	 */
	public void setEnableThreads(boolean truefalse) {
		_threaded = truefalse;
	}

	/**
	 * This method will set the autoretrieve group criteria used for the
	 * datastore.
	 */
	public void setGroupAutoRetrieveCriteria(AutoRetrieveCriteria crit) {
		_havingCrit = crit;
	}

	/**
	 * This method is used to set a list of columns in the SQL Statement to
	 * group together.
	 * 
	 * @param groupByColumns
	 *            a list of columns seperated by commas.
	 */
	public void setGroupBy(String groupByColumns) {
		_desc.setGroupByClause(groupByColumns);
	}

	/**
	 * This method is used to set selection criteria that acts upon groups of
	 * data in the result set rather than individual rows. It is used in
	 * conjusction with the setGroupBy method.
	 * 
	 * @param havingCriteria
	 *            Selection criteria for the group
	 * @see DataStore#setGroupBy
	 */
	public void setHaving(String havingCriteria) {
		_desc.setHavingClause(havingCriteria);
	}

	/**
	 * This method will set the maximum number of rows that the datastore will
	 * retrieve. If the max is set to -1, the datastore will retrieve all rows
	 * in the result set. Otherwise it will stop retrieving when the max is
	 * reached.
	 */
	public void setMaxRows(int max) {
		_maxRows = max;
	}

	/**
	 * Sets the order by clause of the DataStore's SQL Statement
	 * 
	 * @param orderBy
	 *            The columns to use to sort the result set.
	 */
	public void setOrderBy(String orderBy) {
		_desc.setOrderByClause(orderBy);
	}

	/**
	 * This method is used to indicate whether a column is part of the primary
	 * key
	 */
	public void setPrimaryKey(int col, boolean pkey) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setPrimaryKey(pkey);
	}

	/**
	 * This method is used to indicate whether a column is part of the primary
	 * key
	 */
	public void setPrimaryKey(String col, boolean pkey) throws DataStoreException {
		int c = getColumnIndex(col);
		setPrimaryKey(c, pkey);
	}

	/**
	 * This method builds the datastore from the information in the properties
	 * object.
	 */
	public void setProperties(Properties p) {
		super.setProperties(p);
		String desc = "base.";
		_updateMethod = setIntProperty(p.getProperty(desc + "updateMethod"));
		_checkConcurrency = setBoolProperty(p.getProperty(desc + "checkConcurrency"));
		_useBind = setBoolProperty(p.getProperty(desc + "useBind"));
		_maxRows = setIntProperty(p.getProperty(desc + "maxRows"));
		setRemoteID(getStringProperty(p.getProperty(desc + "remoteID")));
	}

	/**
	 * Use this method to set whether the DataStore will trim (remove trailing
	 * spaces) from columns retrieved from the database;
	 */
	public void setTrimStrings(boolean trim) {
		_desc.setTrimStrings(trim);
	}

	/**
	 * This method is used to indicate whether a column is updateable
	 */
	public void setUpdateable(int col, boolean updateable) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setUpdateable(updateable);
	}

	/**
	 * This method is used to indicate whether a column is updateable
	 */
	public void setUpdateable(String col, boolean updateable) throws DataStoreException {
		int c = getColumnIndex(col);
		setUpdateable(c, updateable);
	}

	/**
	 * This method is used to indicate whether a column is auto increment
	 */
	public void setAutoIncrement(int col, boolean auto) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setAutoIncrement(auto);
	}

	/**
	 * This method is used to indicate whether a column is auto increment
	 */
	public void setAutoIncrement(String col, boolean auto) throws DataStoreException {
		int c = getColumnIndex(col);
		setAutoIncrement(c, auto);
	}

	/**
	 * Sets the update method for the datastore. This will effect the SQL
	 * Generated when a row is modified and the changes is made to the database
	 * via the update() method.
	 * 
	 * @param updateMethod
	 *            Valid values are UPDATEMETHOD_UPDATES (will use update
	 *            statements to change the values of rows in the database) or
	 *            UPDATEMETHOD_DELETEINSERTS (will used delete statements and
	 *            then insert statements to modify rows in the database).
	 */
	public void setUpdateMethod(int updateMethod) {
		_updateMethod = updateMethod;
	}

	/**
	 * This method is used to indicate whether a column should use bind
	 * variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE
	 * and BIND_DEFAULT (Use default for datastore)
	 */
	public void setUseBindColumn(int col, int useBind) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setUseBind(useBind);
	}

	/**
	 * This method is used to indicate whether a column should use bind
	 * variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE
	 * and BIND_DEFAULT (Use default for datastore)
	 */
	public void setUseBindColumn(String col, int useBind) throws DataStoreException {
		int c = getColumnIndex(col);
		setUseBindColumn(c, useBind);
	}

	/**
	 * Use this method to set whether or not the datastore will use bind
	 * variables as the default for updating or inserting columns. This is the
	 * default for every column in the datastore. The behavior for individual
	 * columns can be set using the setUseBindColumn method.
	 */
	public void setUseBindForUpdate(boolean truefalse) {
		_useBind = truefalse;
	}

	//Added by FC on 2/21/03. Was added to handle JDBC drivers which have
	// problems with the cancel() method being called.
	//Example Driver: JSQLDriver from NetDirect.
	/**
	 * Use this method to set whether or not the cancel() method of a JDBC
	 * Statement is to be called when cancelling a retrieve.
	 */
	public void setEnableCancel(boolean truefalse) {
		_enableCancel = truefalse;
	}

	/**
	 * This method will take a row from the datastores deleted buffer and move
	 * it back to the standard buffer.
	 * 
	 * @param row
	 *            The number of the row to undelete. Note: this is the row
	 *            number of the row in the deleted buffer not the standard
	 *            buffer.
	 * @return The number that the deleted row was moved to in the standard
	 *         buffer or -1 if an error occurs.
	 */
	public int unDeleteRow(int row) {
		if (row < 0)
			return -1;
		if (row >= getDeletedCount())
			return -1;
		DSDataRow d = (DSDataRow) _deletedRows.elementAt(row);
		_deletedRows.removeElementAt(row);
		_rows.addElement(d);

		return _rows.size() - 1;
	}

	/**
	 * Use this method to retrieve the result sets from multiple data stores
	 * into the data buffer of this one. The datastores must all be compatible
	 * with this one in that the datatypes and number of columns in each much
	 * match exactly. You do not need to pass a database connection to this
	 * version of union, but in order to use it the DataStore must be created
	 * with a constructor that passes an application (not the no args
	 * constructor).
	 * 
	 * @param dataStores
	 *            The array of data stores to union result sets together.
	 */
	public void union(DataStore dataStores[]) throws java.sql.SQLException, DataStoreException {
		if (_appName == null)
			throw new DataStoreException("This version of union requires an dbprofile and/or application name. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		_releaseConn = true;

		union(conn, dataStores);

	}

	/**
	 * Use this method to retrieve the result set from a second data stores into
	 * the data buffer of this one. The datastores must all be compatible with
	 * this one in that the datatypes and number of columns in each much match
	 * exactly. You do not need to pass a database connection to this version of
	 * union, but in order to use it the DataStore must be created with a
	 * constructor that passes an application (not the no args constructor).
	 * 
	 * @param dataStore
	 *            The datastore to union with this one.
	 */
	public void union(DataStore dataStore) throws java.sql.SQLException, DataStoreException {
		DataStore d[] = new DataStore[1];
		d[0] = dataStore;
		union(d);
	}

	/**
	 * Use this method to retrieve the result sets from multiple data stores
	 * into the data buffer of this one. The datastores must all be compatible
	 * with this one in that the datatypes and number of columns in each much
	 * match exactly.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param dataStores
	 *            The array of data stores to union result sets together.
	 */
	public void union(DBConnection conn, DataStore dataStores[]) throws java.sql.SQLException {
		reset();

		String sql = getSelectStatement(conn);
		for (int i = 0; i < dataStores.length; i++) {
			sql += " UNION " + dataStores[i].getSelectStatement(conn);
		}

		retrieve(conn, null, null, null, sql, false);
	}

	/**
	 * Use this method to retrieve the result set from a second data stores into
	 * the data buffer of this one. The datastores must all be compatible with
	 * this one in that the datatypes and number of columns in each much match
	 * exactly.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param dataStore
	 *            The datastore to union with this one.
	 */
	public void union(DBConnection conn, DataStore dataStore) throws java.sql.SQLException, DataStoreException {
		DataStore d[] = new DataStore[1];
		d[0] = dataStore;
		union(conn, d);
	}

	/**
	 * This method will cause the database to reflect the changes made in the
	 * data store's buffer. All transaction operations are handled within the
	 * datastore. You do not need to pass a database connection to this version
	 * of update, but in order to use it the DataStore must be created with a
	 * constructor that passes an application (not the no args constructor).
	 * 
	 * @exception com.salmonllc.sql.DataStoreException
	 *                If a SQLError occurs while the datastore is updating.
	 */
	public void update() throws DataStoreException, java.sql.SQLException {
		if (_appName == null)
			throw new DataStoreException("This version of update requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn = null;

		try {
			if (_dbConn == null) {
				if (_dbProfile == null)
					conn = DBConnection.getConnection(_appName);
				else
					conn = DBConnection.getConnection(_appName, _dbProfile);
			} else {
				conn = _dbConn;
			}
			update(conn, true);
		} catch (DataStoreException e) {
			throw (e);
		} catch (java.sql.SQLException e) {
			throw (e);
		} catch (Exception e) {
			MessageLog.writeErrorMessage ("update", e, this);
		} finally {
			if (conn != null)
				conn.freeConnection();
		}
	}

	/**
	 * This method will cause the database to reflect the changes made in the
	 * data store's buffer. All transaction operations must be handled outside
	 * of the datastore.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @exception com.salmonllc.sql.DataStoreException
	 *                If a SQLError occurs while the datastore is updating.
	 */
	public void update(DBConnection conn) throws DataStoreException, java.sql.SQLException {
		update(conn, false);
	}

	/**
	 * This method will cause the database to reflect the changes made in the
	 * data store's buffer.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param handleTrans
	 *            True if the update should perform it's own transaction
	 *            management and false if the transaction management will be
	 *            handled outside the datastore.
	 * @exception com.salmonllc.sql.DataStoreException
	 *                If a SQLError occurs while the datastore is updating.
	 */
	public void update(DBConnection conn, boolean handleTrans) throws DataStoreException, java.sql.SQLException {
		waitForRetrieve();

		if (_autoValidate) {
			DataStoreException[] valEx = validateRowsToUpdate(conn, true);
			if (valEx.length > 0)
				throw (valEx[0]);
		}

		int rowNo = -1;
		int buffer = BUFFER_STANDARD;
		DataSourceOut out = getDSOut(conn);
		DataStoreRow row = new DataStoreRow(this, null, _desc);

		try {
			out.preUpdate(this, conn, handleTrans);
			//do deletes
			buffer = BUFFER_DELETED;
			for (rowNo = 0; rowNo < _deletedRows.size(); rowNo++) {
				row.setDSDataRow((DSDataRow) _deletedRows.elementAt(rowNo));
				if (!out.deleteRow(this, row, conn))
					throw new Exception("$Delete$");
				if (out instanceof DSDataSourceJDBC)
					notifyListeners(BUFFER_DELETED, rowNo, ((DSDataSourceJDBC) out).getSelect(), conn);
			}

			//do the updates
			buffer = BUFFER_STANDARD;
			if (_updateMethod == UPDATEMETHOD_DELETEINSERTS) {
				for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
					row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
					if (row.getDSDataRow().getRowStatus() == STATUS_MODIFIED) {
						if (!out.deleteRow(this, row, conn))
							throw new Exception("$Update$");
						if (out instanceof DSDataSourceJDBC)
							notifyListeners(BUFFER_STANDARD, rowNo, ((DSDataSourceJDBC) out).getSelect(), conn);
					}
				}

				for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
					row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
					if (row.getDSDataRow().getRowStatus() == STATUS_MODIFIED) {
						if (!out.insertRow(this, row, conn))
							throw new Exception("$Update$");
						if (out instanceof DSDataSourceJDBC)
							notifyListeners(BUFFER_STANDARD, rowNo, ((DSDataSourceJDBC) out).getSelect(), conn);
					}
				}
			} else {
				for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
					row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
					if (row.getDSDataRow().getRowStatus() == STATUS_MODIFIED) {
						if (!out.updateRow(this, row, conn))
							throw new Exception("$Update$");
						if (out instanceof DSDataSourceJDBC)
							notifyListeners(BUFFER_STANDARD, rowNo, ((DSDataSourceJDBC) out).getSelect(), conn);
					}
				}
			}

			//do the inserts
			for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
				row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
				if (row.getDSDataRow().getRowStatus() == STATUS_NEW_MODIFIED) {
					if (!out.insertRow(this, row, conn))
						throw new Exception("$Insert$");
					if (out instanceof DSDataSourceJDBC)
						notifyListeners(BUFFER_STANDARD, rowNo, ((DSDataSourceJDBC) out).getSelect(), conn);
				}
			}

			out.postUpdate(this, conn, handleTrans, true);
			if (handleTrans)
				resetStatus();
		} catch (DirtyDataException e) {
			try {
				out.postUpdate(this, conn, handleTrans, false);
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("update() -- postUpdate", ex, this);
			}
			e.setRowAndBuffer(rowNo, buffer);
			throw (e);
		} catch (SQLException e) {
			try {
				out.postUpdate(this, conn, handleTrans, false);
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("update() -- postUpdate", ex, this);
			}
			throw (e);
		} catch (DataStoreException e) {
			try {
				out.postUpdate(this, conn, handleTrans, false);
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("update() -- postUpdate", ex, this);
			}
			throw (e);

		} catch (Exception e) {
			try {
				out.postUpdate(this, conn, handleTrans, false);
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("update() -- postUpdate", ex, this);
			}

			String message = e.getMessage();
			if (message.equals("$Update$") || message.equals("$Insert$") || message.equals("$Delete$")) {
				throw new DataStoreException("DataStore updated canceled.");
			} else
				throw new DataStoreException(message);

		}
	}

	/**
	 * This method is used for databases profiles where the DBName attribute is
	 * specified. It will take a short table name and return the fully qualified
	 * table name, including the database name.
	 */
	public String computeTableName(String nameIn) {
		//fc: 07/17/02 Added a null check to if statement to stop
		// a null pointer exception from occuring.
		if (nameIn == null || nameIn.indexOf(".") != -1)
			return nameIn;
		if (_appName == null)
			return nameIn;
		for (int i = 0; i < _desc.getAliasCount(); i++) {
			DSTableAliasDescriptor test = _desc.getAlias(i);
			if (test.getAlias() != null && test.getAlias().equalsIgnoreCase(nameIn))
				return nameIn;
		}
		loadDBName();
		if (_dbName == null)
			return nameIn;
		else if (_dbms.equals(DBConnection.SYBASE_CONNECTION) || _dbms.equals(DBConnection.MSSQLSEVER_CONNECTION))
			return _dbName + ".dbo." + nameIn;
		else
			return _dbName + "." + nameIn;
	}

	/**
	 * This method is used for databases profiles where the DBName attribute is
	 * specified. It will take a short table name, column name combination and
	 * return the fully qualified table name + column name, including the
	 * database name.
	 */
	public String computeTableAndFieldName(String fieldName) {
		int pos = fieldName.lastIndexOf(".");
		if (pos == -1)
			return fieldName;
		String tableName = fieldName.substring(0, pos);
		String colName = fieldName.substring(pos);
		return computeTableName(tableName) + colName;
	}

	private void loadDBName() {
		if (_dbms != null)
			return;
		if (_appName == null)
			return;
		DBConnection conn = null;
		try {
			conn = DBConnection.getConnection(_appName, _dbProfile);
			String DBName = conn.getDBName();
			String DBMS = conn.getDBMS();
			_dbms = DBMS;
			if (DBName != null)
				_dbName = DBName;
		} catch (Exception e) {
			MessageLog.writeErrorMessage("loadDBName", e, this);
		} finally {
			if (conn != null)
				conn.freeConnection();
		}

	}

	/**
	 * This method takes the SQL INSERT statements that would have been
	 * generated to add these rows to the database, and, rather than executing
	 * them, returns them as a string that can be written to a SQL script file.
	 */

	public String exportAsSQL(DBConnection conn, String sDBType, String separator) throws Exception {
		DSDataSourceJDBC x = (DSDataSourceJDBC) Class.forName("com.salmonllc.sql.DSDataSource" + sDBType).newInstance();
		String s = x.exportAsSQL(this, conn, separator);
		return s;
	}

	/**
	 * Returns true if the open and close parens on a string match
	 */
	public static boolean parensMatch(String s) {
		int open = 0;
		int close = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(')
				open++;
			else if (s.charAt(i) == ')')
				close++;
		}
		return (open == close);

	}

	/**
	 * @return the database profile this datastore is using
	 */
	public String getDbProfile() {
		return _dbProfile;
	}

	/**
	 * Returns true if the datastore will execute any defined validation rules
	 * before an update
	 */
	public boolean getAutoValidate() {
		return _autoValidate;
	}

	/**
	 * Sets whether the datastore will execute any defined validation rules
	 * before an update
	 */
	public void setAutoValidate(boolean autoValidate) {
		_autoValidate = autoValidate;
	}

	/**
	 * Executes the prepared statement with no parameters.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param stmt
	 *            The prepared statement to execute.
	 */
	public synchronized void executePrepStmt(DBConnection conn, String stmt) throws java.sql.SQLException {
		executePrepStmt(conn, stmt, new PreparedStatementParms());
	}

	/**
	 * Executes the prepared statement and retrieves to data. The datatypes and
	 * number of columns in the result set must exactly match the columns in the
	 * specified sql statement.
	 * 
	 * @param conn
	 *            The database connection to use to perform this operation.
	 * @param stmt
	 *            The prepared statement to execute.
	 * @param parms
	 *            A PreparedStatementParms object containing the parameters to
	 *            pass to the prepared statement.
	 */
	public synchronized void executePrepStmt(DBConnection conn, String stmt, PreparedStatementParms parms) throws java.sql.SQLException {
		retrieve(conn, null, stmt, parms, null, false);
	}

	/**
	 * Executes the prepared statement and retrieves to data.
	 * 
	 * @param stmt
	 *            The prepared statement to execute.
	 */

	public void executePrepStmt(String stmt) throws java.sql.SQLException, DataStoreException {
		executePrepStmt(stmt, new PreparedStatementParms());
	}

	/**
	 * Executes the prepared statement and retrieves to data. The datatypes and
	 * number of columns in the result set must exactly match the columns in the
	 * specified sql statement.
	 * 
	 * @param stmt
	 *            The prepared statement to execute.
	 * @param parms
	 *            A PreparedStatementParms object containing the parameters to
	 *            pass to the proc.
	 */
	public synchronized void executePrepStmt(String stmt, PreparedStatementParms parms) throws java.sql.SQLException, DataStoreException {
		if (_appName == null)
			throw new DataStoreException("This version of execute requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");

		DBConnection conn;
		if (_dbProfile == null)
			conn = DBConnection.getConnection(_appName);
		else
			conn = DBConnection.getConnection(_appName, _dbProfile);

		_releaseConn = true;
		executePrepStmt(conn, stmt, parms);
	}

	/**
	 * Loads connection parms from the database connection object into the
	 * datastore. This method can only be used with the datastore constructor
	 * that takes and application name and/or profile name and will be called
	 * automatically from those constructors
	 */
	public void loadConnectionParms() {
		DBConnection conn = null;
		try {
			conn = DBConnection.getConnection(_appName, _dbProfile);
			loadConnectionParms(conn);
		} catch (Exception ex) {
		} finally {
			if (conn != null)
				conn.freeConnection();
		}
	}

	/**
	 * Loads connection parameters for this datastore from the specified
	 * connection object
	 */
	public void loadConnectionParms(DBConnection conn) {
		String val = conn.getConnectionParm(CONNECTION_PARM_CHECK_CONCURRENCY);
		if (val != null)
			setCheckConcurrency(val.equalsIgnoreCase("true"));
		val = conn.getConnectionParm(CONNECTION_PARM_USE_BIND_FOR_UPDATES);
		if (val != null)
			setUseBindForUpdate(val.equalsIgnoreCase("true"));
		val = conn.getConnectionParm(CONNECTION_PARM_USE_DELETE_INSERT_FOR_UPDATES);
		if (val != null)
			setUpdateMethod(val.equalsIgnoreCase("true") ? UPDATEMETHOD_DELETEINSERTS : UPDATEMETHOD_UPDATES);
		val = conn.getConnectionParm(CONNECTION_PARM_TRIM_STRINGS);
		if (val != null)
			setTrimStrings(val.equalsIgnoreCase("true"));

		//Handle Nulls
		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_STRINGS);
		if (val != null) {
			if (val.equalsIgnoreCase("true"))
				setNullDefault(DATATYPE_STRING, "");
		}

		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_INTS);
		if (val != null) {
			try {
				short i = Short.parseShort(val);
				setNullDefault(DATATYPE_INT, new Integer(i));
				setNullDefault(DATATYPE_LONG, new Long(i));
				setNullDefault(DATATYPE_SHORT, new Short(i));
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("Error Parsing Null Integer Default for Datastore (" + val + ").", ex, this);
			}
		}

		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_DECIMALS);
		if (val != null) {
			try {
				float f = Float.parseFloat(val);
				setNullDefault(DATATYPE_FLOAT, new Float(f));
				setNullDefault(DATATYPE_DOUBLE, new Double(f));
			} catch (Exception ex) {
				MessageLog.writeErrorMessage("Error Parsing Null Decimal Default for Datastore (" + val + ").", ex, this);
			}
		}

		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_DATES);
		if (val != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date d = df.parse(val);
				setNullDefault(DATATYPE_DATE, new java.sql.Date(d.getTime()));
			} catch (ParseException ex) {
				MessageLog.writeErrorMessage("Error Parsing Null Date Default For Datastore (" + val + "). It must be in the form yyyy-mm-dd", ex, this);
			}
		}

		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_DATETIMES);
		if (val != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				java.util.Date d = df.parse(val);
				setNullDefault(DATATYPE_DATETIME, new java.sql.Timestamp(d.getTime()));
			} catch (ParseException ex) {
				MessageLog.writeErrorMessage("Error Parsing Null DateTime Default For Datastore (" + val + "). It must be in the form yyyy-mm-dd hh:mm:ss", ex, this);
			}
		}

		val = conn.getConnectionParm(CONNECTION_PARM_REPLACE_NULL_TIMES);
		if (val != null) {
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			try {
				java.util.Date d = df.parse(val);
				setNullDefault(DATATYPE_TIME, new java.sql.Time(d.getTime()));
			} catch (ParseException ex) {
				MessageLog.writeErrorMessage("Error Parsing Null Time Default For Datastore (" + val + "). It must be in the form hh:mm:ss", ex, this);
			}
		}

	}

	/**
	 * Returns a list of column definitions for a particular table in the
	 * database that the datastore is using. Note, the datastore must have an
	 * app name for this method to work.
	 */
	public ColumnDefinition[] getColumnsForTable(String table) {
		DBConnection conn = null;
		ColumnDefinition ret[] = null;
		try {
			conn = DBConnection.getConnection(getAppName(), getDbProfile());
			DataDictionary d = conn.getDataDictionary();
			Vector v = d.getColumns(table);
			if (v != null) {
				ret = new ColumnDefinition[v.size()];
				v.copyInto(ret);
			} else
				ret = new ColumnDefinition[0];
		} catch (SQLException ex) {
			MessageLog.writeErrorMessage("getColumnsForTable()",ex,this);
			ret = new ColumnDefinition[0];
		}	
		finally {
			conn.freeConnection();
		}
		return ret;

	}

	/**
	 * Returns the name of the database engine being used
	 */
	public String getDBMS() {
		loadDBName();
		return _dbms;
	}

	/**
	 * Builds a where clause suitable for retrieving a single row from the
	 * specified table where the primary key fields match the values in the
	 * specified row.
	 */
	public String buildCriteriaStringForRow(int row, String table) throws DataStoreException {
		StringBuffer ret = new StringBuffer(255);
		int colCount = getColumnCount();
		for (int i = 0; i < colCount; i++) {
			String tabName = getColumnTableName(i);
			if (tabName == null || tabName != table)
				continue;
			if (isColumnPrimaryKey(i)) {
				if (ret.length() != 0)
					ret.append(" AND ");
				ret.append(getColumnDatabaseName(i));
				Object data = getAny(row, i);
				if (data == null)
					ret.append(" IS NULL ");
				else {
					ret.append(" = ");
					int type = getColumnDataType(i);
					if (type == DataStore.DATATYPE_DATETIME)
						ret.append(" {ts '" + DataStore.fixQuote(data.toString(), type, getDBMS()) + "'}");
					else if (type == DataStore.DATATYPE_DATE)
						ret.append(" {d '" + DataStore.fixQuote(data.toString(), type, getDBMS()) + "'}");
					else if (type == DataStore.DATATYPE_TIME)
						ret.append(" {t '" + DataStore.fixQuote(data.toString(), type, getDBMS()) + "'}");
					else if (type == DataStore.DATATYPE_STRING)
						ret.append("'" + DataStore.fixQuote(data.toString(), type, getDBMS()) + "'");
					else
						ret.append(data.toString());
				}
			}
		}
		if (ret.length() == 0)
			return null;
		else
			return ret.toString();
	}

	/**
	 * Reloads the specified row from the database. Any unsaved changes will be
	 * overwritten.
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public synchronized void reloadRow() throws DataStoreException, SQLException {
		reloadRow(getRow());
	}

	/**
	 * Reloads the specified row from the database. Any unsaved changes will be
	 * overwritten.
	 * 
	 * @param rowNo
	 */
	public synchronized void reloadRow(int rowNo) throws DataStoreException, SQLException {
		DBConnection conn = null;
		if (_appName == null)
			throw new DataStoreException("This version of reloadRow requires an application name and/or dbprofile. You cannot call this method on a DataStore created with the no-args constructor");
		try {
			if (_dbProfile == null)
				conn = DBConnection.getConnection(_appName);
			else
				conn = DBConnection.getConnection(_appName, _dbProfile);
			reloadRow(conn, rowNo);
		} finally {
			if (conn != null)
				conn.freeConnection();
		}
	}

	/**
	 * Reloads the specified row from the database. Any unsaved changes will be
	 * overwritten.
	 * 
	 * @param conn
	 *            The database connection to use to load the data
	 * @param rowNo
	 */
	public synchronized void reloadRow(DBConnection conn, int rowNo) throws DataStoreException, SQLException {
		if (rowNo >= getRowCount() || rowNo < 0)
			throw new DataStoreException("Row number:" + rowNo + " out of range for reloadRow.");
		int rowStatus = getRowStatus(rowNo);
		if (rowStatus == STATUS_NEW || rowStatus == STATUS_NEW_MODIFIED)
			return;
		DSDataRow row = getDataRow(rowNo);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DataSourceIn in = getDSIn(conn);
		if (in instanceof DSDataSourceJDBC)
			df = ((DSDataSourceJDBC) in).getDateTimeFormat();

		StringBuffer criteria = new StringBuffer();
		for (int i = 0; i < getColumnCount(); i++) {
			if (!isPrimaryKey(i))
				continue;

			String databaseName = getColumnDatabaseName(i);

			Object data = null;
			if (rowStatus == STATUS_NOT_MODIFIED)
				data = row.getData(i);
			else {
				if (row.getColumnStatus(i) == STATUS_MODIFIED)
					data = row.getOrigData(i);
				else
					data = row.getData(i);
			}

			Object curData = row.getData(i);
			String value = null;

			int type = getColumnDataType(i);

			if (type == DataStore.DATATYPE_DATETIME) {
				if (data == null)
					value = " IS NULL";
				else
					value = " = {ts '" + DSDataSourceJDBC.formatDateTime((Timestamp) data, df, conn) + "'}";
			} else if (type == DataStore.DATATYPE_DATE) {
				if (data == null)
					value = " IS NULL";
				else
					value = " = {d '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";
			} else if (type == DataStore.DATATYPE_TIME) {
				if (data == null)
					value = " IS NULL";
				else
					value = " = {t '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";
			} else if (type == DataStore.DATATYPE_STRING) {
				if (data == null)
					value = " IS NULL";
				else
					value = " = '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'";
			} else {
				if (data == null)
					value = " IS NULL";
				else
					value = " = " + data.toString();
			}
			if (criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append(databaseName);
			criteria.append(value);
		}
		if (criteria.length() == 0)
			throw new DataStoreException("Could not reload row. No primary key columns defined");

		String select = getSelectStatement(conn, criteria.toString());
		notifyListeners(BUFFER_STANDARD, rowNo, select, conn);
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery(select);
		if (res.next()) {
			DSDataRow newRow = new DSDataRow(_desc);
			newRow.populateFromResultSet(_desc, res);
			_rows.setElementAt(newRow, rowNo);
			ModelChangedEvent evt = new ModelChangedEvent(ModelChangedEvent.TYPE_DATA_LOADED, this);
			notifyListeners(evt);
		}
		res.close();
		st.close();
	}

	/**
	 * Populates the datastore from a given resultSet.
	 * 
	 * @param rs
	 *            the resultset.
	 * @throws Exception
	 */
	public void populateFromResultSet(ResultSet rs) throws Exception {
		while (rs.next()) {
			getDataStoreRow(insertRow(), DataStore.BUFFER_STANDARD).populateFromResultSet(rs);
		}
	}

	/**
	 * @return Returns whether or not the datastore will batch insert statements.
	 */
	public boolean getBatchInserts() {
		return _batchInserts;
	}
	/**
	 * Set to true to batch insert statements. This can speed things up if you are doing a high volume of inserts
	 */
	public void setBatchInserts(boolean batchInserts) {
		_batchInserts = batchInserts;
	}
}