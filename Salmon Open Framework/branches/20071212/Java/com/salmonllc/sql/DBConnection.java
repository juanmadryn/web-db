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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DBConnection.java $
//$Author: Dan $
//$Revision: 42 $
//$Modtime: 10/15/04 2:35p $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

/**
 * This class abstracts and simplifies the process of getting database connections from connection pools and connection managers, provides some transaction support as well as a wrapper for the java.sql.Connection class. The constructor for this class is private. The only way to get an instance of the class is to use one of the static "getConnection" methods shown below.<BR><BR> An important note:<BR> In order for connection pooling to operate properly, the connection must be released when it is no longer needed.<BR><BR> Example usage:<BR> <pre> DBConnection conn = null; try { conn =DBConnection.getConnection("Application1"); ...Sql Statements using the connection } catch (SQLException e) { ..handle error } finally { if (conn != null) conn.freeConnection(); } </pre>
 * @see java.sql.Connection
 * @see java.sql.Statement
 * @see  java.sql.PreparedStatement
 */
public class DBConnection {
	// Connection type for a Sybase DBMS.
	public static final String SYBASE_CONNECTION = Props.DBMSTYPE_SYBASE;

	// Connection type for a DB2 DBMS.
	public static final String DB2_CONNECTION = Props.DBMSTYPE_DB2;

	//Connection type for a DB2-VSE DBMS.
	public static final String DB2VSE_CONNECTION = Props.DBMSTYPE_DB2VSE;

	//Connection type for a DB2-MVS DBMS.
	public static final String DB2MVS_CONNECTION = Props.DBMSTYPE_DB2MVS;

	//Connection type for a DB2-400 DBMS;
	//Added by DR, 8 Jan 2003
	public static final String DB2400_CONNECTION = Props.DBMSTYPE_DB2400;

	// Connection type for a SQL Anywhere DBMS.
	public static final String SQLANYWHERE_CONNECTION = Props.DBMSTYPE_SQLANYWHERE;

	//Connection type for a Microsoft SQL Server DBMS.
	public static final String MSSQLSEVER_CONNECTION = Props.DBMSTYPE_MSSQLSERVER;

	//Connection type for an Oracle Server DBMS.
	public static final String ORACLE_CONNECTION = Props.DBMSTYPE_ORACLE;

	//Connection type for an MySql Server DBMS.
	public static final String MYSQL_CONNECTION = Props.DBMSTYPE_MYSQL;

	//Connection type for an Ingres Server DBMS.
	public static final String INGRES_CONNECTION = Props.DBMSTYPE_INGRES;

	//Connection type for an Firebird/Interbase Server DBMS.
	public static final String FIREBIRDSQL_CONNECTION = Props.DBMSTYPE_FIREBIRDSQL;

	//Connection type for an Firebird/Interbase Server DBMS.
	public static final String ANSISQL92_CONNECTION = Props.DBMSTYPE_ANSISQL92;

	//Connection type for an Postgres Server DBMS.
	public static final String POSTGRES_CONNECTION = Props.DBMSTYPE_POSTGRES;

	private Connection _conn;
	private String _type;
	private boolean _direct;
	private long _created = 0;
	private long _lastUsed = 0;
	private long _lastDuration = 0;
	private boolean _inUse = false;
	private String _databaseDriver;
	private String _username;
	private String _password;
	private String _databaseUrl;
	private DataSource _dsDataSource;

	private DBConnection _next;
	private DBConnection _prior;
	private DBConnectionList _list;
	private boolean _verifyConnection = false;
	private String _verifyConnectionStatement;
	private int _verifyConnectionMaxAge = 0;

	private Vector _retrieves = new Vector();

	private static Hashtable _connections = new Hashtable();

	private String _lastSql = null;

	private boolean _dataSource;
	private static Hashtable _dataSources = new Hashtable();
	private Hashtable _connectionParms;
	private String _dbName;

	private DataDictionary _dd;
	private boolean _transactionStarted;
	private static String DEFAULT_VERIFY_STATEMENT = "SELECT 1";
	private String _application;
	private String _profileName;

	/**
	 * This method was created in VisualAge.
	 * @param conn java.sql.Connection
	 * @param type java.lang.String
	 */
	DBConnection(Connection conn, String type, boolean direct, boolean dataSource) {
		_type = type;
		_conn = conn;
		_direct = direct;
		_dataSource = dataSource;
	}

	/**
	 * Create a DBConnection
	 *
	 * @param conn java.sql.Connection
	 * @param type The database type (mySQL, DB2, Oracle, etc..)
	 * @param direct If true, by-pass the local connection pool
	 * @param dataSource If true, use the app-server dataSource specified in the properties file
	 * @param dbName Database name.  Used by various implementations.  Usually null.
	 */
	DBConnection(Connection conn, String type, boolean direct, boolean dataSource, String dbName) {
		this(conn, type, direct, dataSource);
		_dbName = dbName;
	}

	/**
	 * Marks the logical beginning of a transaction.
	 * The transaction must be completed later in the code via a call to either commit or rollback methods.
	 * @see DBConnection#commit
	 * @see DBConnection#rollback
	 */
	public void beginTransaction() throws java.sql.SQLException {
		verify();

		try {
			_conn.setAutoCommit(false);
		} catch (SQLException e) {
			if (getDBMS().equals(SYBASE_CONNECTION) || getDBMS().equals(MSSQLSEVER_CONNECTION)) {
				Statement s = _conn.createStatement();
				s.execute("begin transaction");
				s.close();
			}
		}
		_transactionStarted=true;

	}
	/**
	 * Marks the logical beginning of a transaction.
	 * The transaction must be completed later in the code via a call to either commit or rollback methods.
	 * @exception java.sql.SQLException The SQL Exception.
	 * @see DBConnection#commit
	 * @see DBConnection#rollback
	 */
	public void beginTransaction(String fromWhere) throws java.sql.SQLException {
		MessageLog.writeDebugMessage("BeginTransaction-->" + fromWhere, this);
		System.err.println("BeginTransaction-->" + fromWhere);
		beginTransaction();
	}
	/**
	 * This method commits a transaction started by a call to the beginTransaction method.
	 * @see DBConnection#beginTransaction
	 * @see DBConnection#rollback
	 */
	public void commit() {
		verify();

		try {
			if (getDBMS().equals(ORACLE_CONNECTION)) {
				Statement s = _conn.createStatement();
				s.execute("commit");
				s.close();
			}
			_conn.commit();
			_conn.setAutoCommit(true);
			_transactionStarted=false;
		} catch (Exception e) {
			//System.err.println("DBConnection.commit " + e);
		}
		
	}
	/**
	 * This method commits a transaction started by a call to the beginTransaction method.
	 * @see DBConnection#beginTransaction
	 * @see DBConnection#rollback
	 */
	public void commit(String fromWhere) {

		MessageLog.writeDebugMessage("Commit-->" + fromWhere, this);
		System.err.println("Commit-->" + fromWhere);
		commit();
	}
	private static String computeDBMS(String databaseURL) {
		String DBMS = SQLANYWHERE_CONNECTION;
		if (databaseURL.startsWith("jdbc:sybase:"))
			DBMS = SYBASE_CONNECTION;
		else if (databaseURL.startsWith("jdbc:db2:"))
			DBMS = DB2_CONNECTION;
		else if (databaseURL.startsWith("jdbc:oracle:"))
			DBMS = ORACLE_CONNECTION;
		else if (databaseURL.startsWith("jdbc:mysql:"))
			DBMS = MYSQL_CONNECTION;
		else if (databaseURL.startsWith("jdbc:edbc:"))
			DBMS = INGRES_CONNECTION;
		else if (databaseURL.startsWith("jdbc:firebirdsql:"))
			DBMS = FIREBIRDSQL_CONNECTION;
		else if (databaseURL.startsWith("jdbc:microsoft:"))
			DBMS = MSSQLSEVER_CONNECTION;
		else if (databaseURL.startsWith("jdbc:attunity:"))
			DBMS = ANSISQL92_CONNECTION;
		else if (databaseURL.startsWith("jdbc:postgresql:"))
			DBMS = POSTGRES_CONNECTION;
		return DBMS;
	}
	/**
	 * SQL statements without parameters are normally executed using Statement objects. If the same SQL statement is executed many times, it is more efficient to use a PreparedStatement
	 * @return a new Statement object
	 * @exception java.sql.SQLException
	 */
	public java.sql.Statement createStatement() throws java.sql.SQLException {
		verify();
		return new DBStatement(_conn.createStatement(), this);
	}
	/**
	 * Returns the connection back to the connection pool for use by other processes.
	 */
	public synchronized void freeConnection() {
		if (!_direct && !_dataSource) {
			try {
				for (int i = 0; i < _retrieves.size(); i++) {
					((DataStore) _retrieves.elementAt(i)).cancelRetrieve();
					((DataStore) _retrieves.elementAt(i)).waitForCancel();
					_retrieves.removeAllElements();
				}

				if (_transactionStarted)
					rollback();
				//	synchronized (_list) {
				_list.freeConnection(this);
				//		_inUse = false;
				//		_lastDuration = System.currentTimeMillis() - _lastUsed;
				//	}
			} catch (Exception e) {
				MessageLog.writeErrorMessage("freeConnection()", e, this);
			}
		} else if (_conn != null) {
			try {
				_conn.close();
			} catch (Exception e) {
				MessageLog.writeErrorMessage("freeConnection()", e, this);
			}
		}
	}
	/**
	 * This method was created in VisualAge.
	 * @return java.lang.String
	 * @param DBMS java.lang.String
	 * @param jdbcDriver java.lang.String
	 * @param databaseURL java.lang.String
	 * @param userID java.lang.String
	 * @param password java.lang.String
	 * @param dbName java.lang.String
	 */
	private static String generateSpec(String DBMS, String jdbcDriver, String databaseURL, String userID, String password, String dbName, String connectionParms) {
		int size = (DBMS == null ? 0 : DBMS.length());
		size += (jdbcDriver == null ? 0 : jdbcDriver.length());
		size += (databaseURL == null ? 0 : databaseURL.length());
		size += (userID == null ? 0 : userID.length());
		size += (password == null ? 0 : password.length());

		StringBuffer sb = new StringBuffer(size);

		if (DBMS != null)
			sb.append(DBMS);
		if (jdbcDriver != null)
			sb.append(jdbcDriver);
		if (databaseURL != null)
			sb.append(databaseURL);
		if (userID != null)
			sb.append(userID);
		if (password != null)
			sb.append(password);
		if (dbName != null)
			sb.append(dbName);
		if (connectionParms != null)
			sb.append(connectionParms);	
		return sb.toString();
	}
	/**
	 * This method will create a new database connection using the information passed to it.
	 * @return The connection created from the passed.
	 * @param maxConnections The maximum number of database connections of this type that can be used concurrently.
	 * @param waitTimeout The time in milliseconds to wait for a connection before throwing an exception.
	 * @param idleTimeout The time in milliseconds for a connection to remain idle before it is disconnected from the database.
	 * @param DBMS The name of the DBMS you are using.
	 * @param jdbcDriver The name of the jdbc driver for this connection.
	 * @param databaseURL The url for the specific database that you want to connect to.
	 * @param userID A valid user in the specified database.
	 * @param password The password for the specified user.
	 * @param dbName The database name used for this connection.
	 * @param connectionParms A string of semicolon seperated values that tell the connection and datastores that use it how to behave
	 * @exception java.sql.SQLException
	 */
	public static synchronized DBConnection getConnection(int maxConnections, long waitTimeout, long idleTimeout, String DBMS, String jdbcDriver, String databaseURL, String userID, String password, String dbName, String connectionParms) throws java.sql.SQLException {

		if (DBMS == null)
			DBMS = computeDBMS(databaseURL);

		String spec = generateSpec(DBMS, jdbcDriver, databaseURL, userID, password, dbName, connectionParms);
		DBConnectionList list = null;
		if (_connections.containsKey(spec)) {
			list = (DBConnectionList) _connections.get(spec);
			list.setMax(maxConnections);
			list.setWaitTimeOut(waitTimeout);
			list.setIdleTimeOut(idleTimeout);
		} else {
			list = new DBConnectionList(maxConnections, waitTimeout, idleTimeout, DBMS, jdbcDriver, databaseURL, userID, password, dbName, connectionParms);
			_connections.put(spec, list);
		}

		return list.getConnection();

	}
	
	/**
	 * This method will create a new database connection using the information passed to it.
	 * @return The connection created from the passed.
	 * @param maxConnections The maximum number of database connections of this type that can be used concurrently.
	 * @param waitTimeout The time in milliseconds to wait for a connection before throwing an exception.
	 * @param idleTimeout The time in milliseconds for a connection to remain idle before it is disconnected from the database.
	 * @param DBMS The name of the DBMS you are using.
	 * @param jdbcDriver The name of the jdbc driver for this connection.
	 * @param databaseURL The url for the specific database that you want to connect to.
	 * @param userID A valid user in the specified database.
	 * @param password The password for the specified user.
	 * @param dbName The database name used for this connection.
	 * @exception java.sql.SQLException
	 */
	public static synchronized DBConnection getConnection(int maxConnections, long waitTimeout, long idleTimeout, String DBMS, String jdbcDriver, String databaseURL, String userID, String password, String dbName) throws java.sql.SQLException {
			return getConnection(maxConnections,waitTimeout,idleTimeout,DBMS,jdbcDriver,databaseURL,userID,password, dbName, null);
	}
	/**
	 * This method returns the default database connection for a particular application.
	 * @return the default connection for a particular application as indicated in the properties file.
	 * @param application The name to search under for connection information.
	 * @exception java.sql.SQLException
	 */
	public static DBConnection getConnection(String application) throws java.sql.SQLException {

		return getConnection(application, null);

	}
	/**
	 * This method will search a properties file for a particular application for information on connecting to a database
	 * with the specified name and returns a DBConnection attached to it.
	 * @return The connection created from the information found in the property file.
	 * @param name The name to search under for connection information.
	 * @exception java.sql.SQLException
	 */
	public static DBConnection getConnection(String application, String name) throws java.sql.SQLException {
		String profileName=name;
		Props p = Props.getProps(application, null);

		String def = p.getProperty(Props.DB_DEFAULT);
		if (def != null)
			def += ".";
		else
			def = "";

		if (name == null)
			name = def;
		else
			name += ".";

		String dbms = p.getProperty(name + Props.DB_DBMS);
		String driver = p.getProperty(name + Props.DB_DRIVER);
		String url = p.getProperty(name + Props.DB_URL);
		String user = p.getProperty(name + Props.DB_USER);
		String pass = p.getProperty(name + Props.DB_PASSWORD);
		String dataSource = p.getProperty(name + Props.DB_DATASOURCE);
		String dbName = p.getProperty(name + Props.DB_NAME);
		String dbParms = p.getProperty(name + Props.DB_PARMS);

		int maxConn = p.getIntProperty(name + Props.DB_MAX_CONNECTIONS);
		if (maxConn == -1)
			maxConn = 50;
		int waitTimeOut = p.getIntProperty(name + Props.DB_WAIT_TIME_OUT);
		if (waitTimeOut == -1)
			waitTimeOut = 60000;
		int idleTimeOut = p.getIntProperty(name + Props.DB_IDLE_TIME_OUT);
		if (idleTimeOut == -1)
			idleTimeOut = 3600000;
		boolean verifyConnection = p.getBooleanProperty(name + Props.DB_VERIFY_CONNECTION);
		String verifyConnectionStatement = p.getProperty(name + Props.DB_VERIFY_CONNECTION_STATEMENT);
		int verifyConnectionMaxAge = p.getIntProperty(name + Props.DB_VERIFY_CONNECTION_MAXAGE);
		if (verifyConnectionStatement == null)
			verifyConnectionStatement = DEFAULT_VERIFY_STATEMENT;

		if (dataSource == null) {
			DBConnection dbConn = getConnection(maxConn, waitTimeOut, idleTimeOut, dbms, driver, url, user, pass, dbName, dbParms);
			dbConn.setVerifyConnection(verifyConnection);
			dbConn.setVerifyConnectionStatement(verifyConnectionStatement);
			dbConn.setVerifyConnectionMaxAge(verifyConnectionMaxAge);
			dbConn.setApplication(application);
			dbConn.setProfileName(profileName);
			return dbConn;
		} else {
			try {
				DataSource ds = (DataSource) _dataSources.get(dataSource);
				if (ds == null) {
					Context ctx = new InitialContext();

					if (dataSource.indexOf("/") != -1)
						ds = (DataSource) ctx.lookup(dataSource);
					else
						ds = (DataSource) ctx.lookup("jdbc/" + dataSource);

					_dataSources.put(dataSource, ds);
				}
				Connection con = null;
				if (user != null)
					con = ds.getConnection(user, pass);
				else
					con = ds.getConnection();
				if (dbms == null) {
					DatabaseMetaData md = con.getMetaData();
					url = md.getURL();
					dbms = computeDBMS(url);
				}
				DBConnection dbc = new DBConnection(con, dbms, false, true, dbName);
				dbc.setDataSource(ds);
				dbc.setVerifyConnection(verifyConnection);
				dbc.setVerifyConnectionStatement(verifyConnectionStatement);
				dbc.setVerifyConnectionMaxAge(verifyConnectionMaxAge);
				dbc.setConnectionParms(parseConnectionParms(dbParms));
				dbc.setApplication(application);
				dbc.setProfileName(profileName);
				return dbc;
			} catch (NamingException ex) {
				throw new SQLException(ex.toString());
			}
		}

	}
	/**
	 * This method returns an enumeration of all the connection pools maintained by the system.
	 */
	public static Enumeration getConnectionLists() {
		return _connections.elements();
	}
	/**
	 * This method returns the DBMS that the object is connected to.
	 * @return SYBASE_CONNECTION, DB2_CONNECTION or SQLANYWHERE_CONNECTION.
	 */
	public String getDBMS() {
		return _type;
	}
	/**
	 * This method returns the DBName used by the connection (used for DB2)
	 */
	public String getDBName() {
		if (_dbName == null && _list != null)
			return _list.getDBName();
		else
			return _dbName;
	}
	/**
	 * Use this method to get a connection to the database without using connection pooling.
	 * @param url The url for the specific database that you want to connect to.
	 * @param driverName The name of the jdbc driver for this connection.
	 * @param user A valid user in the specified database.
	 * @param passwd The password for the specified user.
	  */
	public static DBConnection getDirectConnection(String url, String driverName, String user, String passwd) throws java.sql.SQLException {
		String type = computeDBMS(url);

		return getDirectConnection(type, url, driverName, null, user, passwd);
	}
	/**
	 * Use this method to get a connection to the database without using connection pooling.
	 * @param url The url for the specific database that you want to connect to.
	 * @param driverName The name of the jdbc driver for this connection.
	 * @param user A valid user in the specified database.
	 * @param passwd The password for the specified user.
	  */
	public static DBConnection getDirectConnection(String dbms, String url, String driverName, String user, String passwd) throws java.sql.SQLException {
		return getDirectConnection(dbms, url, driverName, null, user, passwd);
	}
	/**
	 * Use this method to get a connection to the database without using connection pooling.
	 * @param dbms The Database Management System you are using.
	 * @param url The url for the specific database that you want to connect to.
	 * @param driverName The name of the jdbc driver for this connection.
	 * @param user A valid user in the specified database.
	 * @param passwd The password for the specified user.
	  */
	public static DBConnection getDirectConnection(String dbms, String url, String driverName, String dbName, String user, String passwd) throws java.sql.SQLException {
		try {
			Class.forName(driverName);
			Connection connection = DriverManager.getConnection(url, user, passwd);
			DBConnection dbc = new DBConnection(connection, dbms, true, false, dbName);
			dbc.setDatabaseDriver(driverName);
			dbc.setDatabaseUrl(url);
			dbc.setPassword(passwd);
			dbc.setUserName(user);
			dbc.setCreated(System.currentTimeMillis());
			return dbc;
		} catch (ClassNotFoundException ex) {
			System.err.println("Cannot find the database driver classes.");
			System.err.println(ex);
		} catch (SQLException ex) {
			System.err.println(ex);
			throw new java.sql.SQLException(ex.toString());
		}

		return null;

	}
	/**
	 * This method will return whether the Connection is currently being used.
	 */
	public boolean getInUse() {
		return _inUse;
	}

	/**
	* This method will return the actual JDBC connection that this class is a wrapper for.
	*/
	public Connection getJDBCConnection() {
		return _conn;
	}
	/**
	 * This method will return that time in millseconds that it took between the last time the connection was requested and released
	 */
	public long getLastDuration() {
		return _lastDuration;
	}
	/**
	 * This method will return the last SQL statement that was executed on the connection
	 */
	public String getLastSQL() {
		return _lastSql;
	}
	/**
	 * This method will return that time in millseconds that the connection was last used.
	 */
	public long getLastUsed() {
		return _lastUsed;
	}
	DBConnection getNext() {
		return _next;
	}
	DBConnection getPrior() {
		return _prior;
	}
	/**
	 * A SQL stored procedure call statement is handled by creating a CallableStatement for it. The CallableStatement provides methods for setting up its IN and OUT parameters, and methods for executing it.
	 * @return a new CallableStatement object containing the pre-compiled SQL statement
	 * @param sql a SQL statement that may contain one or more '?' parameter placeholders. Typically this statement is a JDBC function call escape string.
	 * @exception java.sql.SQLException
	 */
	public java.sql.CallableStatement prepareCall(String sql) throws java.sql.SQLException {
		verify();
		MessageLog.writeSQLMessage("prepareCall()", sql, this);
		setLastSQL(sql);
		return _conn.prepareCall(sql);
	}
	/**
	 * A SQL statement with or without IN parameters can be pre-compiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.
	 * @return a new PreparedStatement object containing the pre-compiled statement.
	 * @param sql a SQL statement that may contain one or more '?' IN parameter placeholders
	 * @exception java.sql.SQLException
	 */
	public java.sql.PreparedStatement prepareStatement(String sql) throws java.sql.SQLException {
		verify();
		MessageLog.writeSQLMessage("prepareStatement()", sql, this);
		setLastSQL(sql);
		return _conn.prepareStatement(sql);
	}
	synchronized void registerRetrieve(DataStore d) {
		for (int i = 0; i < _retrieves.size(); i++) {
			if ((DataStore) _retrieves.elementAt(i) == d)
				return;
		}
		_retrieves.addElement(d);
	}
	/**
	 * This method rolls back a transaction started by a call to the beginTransaction method.
	 * @see DBConnection#beginTransaction
	 * @see DBConnection#commit
	 */
	public void rollback() {
		verify();

		try {
			if (getDBMS().equals(ORACLE_CONNECTION)) {
				Statement s = _conn.createStatement();
				s.execute("rollback");
				s.close();
			}
			_conn.rollback();
			_conn.setAutoCommit(true);
			_transactionStarted=false;
		} catch (Exception e) {
			//System.err.println("DBConnection.rollback " + e);
		}
	}
	/**
	 * This method rolls back a transaction started by a call to the beginTransaction method.
	 * @see DBConnection#beginTransaction
	 * @see DBConnection#commit
	 */
	public void rollback(String fromWhere) {
		MessageLog.writeDebugMessage("Rollback-->" + fromWhere, this);
		System.err.println("Rollback-->" + fromWhere);
		rollback();
	}
	void setDBName(String dbName) {
		_dbName = dbName;
	}
	/**
	 * This method will set whether the Connection is currently being used.
	 */
	void setInUse(boolean inUse) {
		_inUse = inUse;
	}
	void setJDBCConnection(Connection conn) {
		_conn = conn;
	}
	/**
	 * This method will set whether the last duration for the connection.
	 */
	void setLastDuration(long lastDuration) {
		_lastDuration = lastDuration;
	}
	/**
	 * This method will set the last SQL statement that was executed on the connection
	 */
	void setLastSQL(String sql) {
		_lastSql = sql;
	}
	/**
	 * This method will set whether the Connection is currently being used.
	 */
	void setLastUsed(long lastUsed) {
		_lastUsed = lastUsed;
		_lastDuration = 0;
	}
	void setList(DBConnectionList list) {
		_list = list;
	}
	void setNext(DBConnection conn) {
		_next = conn;
	}
	void setPrior(DBConnection conn) {
		_prior = conn;
	}
	synchronized void unregisterRetrieve(DataStore d) {
		for (int i = 0; i < _retrieves.size(); i++) {
			if ((DataStore) _retrieves.elementAt(i) == d) {
				_retrieves.removeElement(d);
			}
		}

	}
	private Connection newConnection() throws SQLException, ClassNotFoundException {
		if (_list != null)
			return _list.createConnection();
		else {
			Class.forName(_databaseDriver);
			return DriverManager.getConnection(_databaseUrl, _username, _password);
		}			
	}	
	
	/**
	 * This method was created in VisualAge.
	 */
	private void verify() {
		if (!_verifyConnection)
			return;
		try {
			if (_conn == null || _conn.isClosed()) {
				if (_dsDataSource != null)
					_conn = _dsDataSource.getConnection();
				else {
					Class.forName(_databaseDriver);
					_conn = newConnection();
				}
			} else {
				if (_verifyConnectionMaxAge > 0) {
					long lNow = System.currentTimeMillis();
					long lDiff = lNow - _created;
					if (lDiff > (long) (_verifyConnectionMaxAge * 1000)) {
						_conn.close();
						try {
							if (_dsDataSource != null)
								_conn = _dsDataSource.getConnection();
							else 
								_conn = newConnection();	
							setCreated(System.currentTimeMillis());
							MessageLog.writeInfoMessage("Connection re-established.", this);
						} catch (Exception ex) {
							MessageLog.writeInfoMessage("Failed to re-establish connection.", this);
						}
					}
				}
				Statement st = _conn.createStatement();
				ResultSet r = st.executeQuery(_verifyConnectionStatement == null ? DEFAULT_VERIFY_STATEMENT : _verifyConnectionStatement);
				r.close();
				st.close();
			}
		} catch (Exception e) {
			try {
				if (_conn != null)
					_conn.close();
				if (_dsDataSource != null)
					_conn = _dsDataSource.getConnection();
				else {
					Class.forName(_databaseDriver);
					_conn = newConnection();
				}
				setCreated(System.currentTimeMillis());
				MessageLog.writeInfoMessage("Connection re-established.", this);
			} catch (Exception ex) {
				MessageLog.writeInfoMessage("Failed to re-establish connection.", this);
			}
		}
	}

	/**
	 * This method will store the datasource the connection was created with.
	 */
	void setDataSource(DataSource ds) {
		_dsDataSource = ds;
	}

	/**
	 * This method will store the database driver the connection was created with.
	 */
	void setDatabaseDriver(String driverName) {
		_databaseDriver = driverName;
	}

	/**
	 * This method will store the username the connection was created with.
	 */
	void setUserName(String user) {
		_username = user;
	}

	/**
	 * This method will store the password the connection was created with.
	 */
	void setPassword(String pass) {
		_password = pass;
	}

	/**
	 * This method will store the database Url the connection was created with.
	 */
	void setDatabaseUrl(String url) {
		_databaseUrl = url;
	}

	/**
	 * This method will indicate whether a connection should be verified.
	 */
	void setVerifyConnection(boolean bVerify) {
		_verifyConnection = bVerify;
	}

	/**
	 * This method will set the connection verify statement.
	 */
	void setVerifyConnectionStatement(String sVerifyStatement) {
		_verifyConnectionStatement = sVerifyStatement;
	}

	/**
	 * This method will set the connections verification max age in seconds.
	 * If the connection is older than the max age the connection will be reestablished.
	 */
	void setVerifyConnectionMaxAge(int iMaxAge) {
		_verifyConnectionMaxAge = iMaxAge;
	}

	/**
	 * This method will set the creation time of the connection.
	 */
	void setCreated(long lCreated) {
		_created = lCreated;
	}

	/**
	 * This method returns a connection parameter
	 * @param key
	 * @return
	 */
	String getConnectionParm(String key) {
		if (_connectionParms != null) 
			return (String) _connectionParms.get(key);	
		else	
			return (String) _list.getConnectionParm(key);
	}

	/**
	 * Returns a Hashtable of connection parms used for this connection
	 * @return
	 */
	Hashtable getConnectionParms() {
		if (_connectionParms != null) 
			return _connectionParms;	
		else	
			return  _list.getConnectionParms();
		
	}	

	static Hashtable parseConnectionParms(String parms) {
			Hashtable tab = new Hashtable();
			if (parms == null)
				return tab;
			StringTokenizer st = new StringTokenizer(parms,";");	
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				int pos = tok.indexOf("=");
				if (pos == -1)
					pos = tok.indexOf(":");
				if (pos != -1) {
					String key = tok.substring(0,pos).trim();
					String val = tok.substring(pos + 1).trim();	
					tab.put(key,val);
				}			
			}	
			return tab;

	}	
	void setConnectionParms(Hashtable hashtable) {
		_connectionParms = hashtable;
	}

	/**
	 * @return true if a transaction has been started on this connection but not yet committed or rolled back. If the transaction has not been, it will be rolled back when the connection has been returned to the pool.
	 */
	public boolean isTransactionStarted() {
		return _transactionStarted;
	}
	
	/**
	 * Returns a data dictionary object containing information on the tables in this database. The DataDictionary object should only be used for the time that the connection object is help (between the DBConnection.getConnection() and DBConnection.freeConnection() methods)
	 */
	public DataDictionary getDataDictionary() {
		String cacheDD=getConnectionParm(DataStore.CONNECTION_PARM_CACHE_DATADICTIONARY);
		if (cacheDD != null && cacheDD.equalsIgnoreCase("false")) {
			_dd=null;
			return new DataDictionary(this);
		}	
		
		if (_dd == null)
			_dd=new DataDictionary(this);
		return _dd;
	}	

	/**
	 * @return Returns the application.
	 */
	String getApplication() {
		return _application;
	}
	/**
	 * @return Returns the profileName.
	 */
	String getProfileName() {
		return _profileName;
	}
	/**
	 * @param application The application to set.
	 */
	private void setApplication(String application) {
		_application = application;
	}
	/**
	 * @param profileName The profileName to set.
	 */
	private void setProfileName(String profileName) {
		_profileName = profileName;
	}
}
