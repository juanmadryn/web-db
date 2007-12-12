//** Copyright Statement ***************************************************
// The JADE Open Framework
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

import java.sql.*;
import java.util.*;

/**
 * This class represents a list of database connections sharing a common specification. It is used by the system to maintain connection pools.
 */
public class DBConnectionList {
	Stack _unused = new Stack();
	DBConnection _first;
	DBConnection _last;
	int _max;
	int _count;
	long _timeOut;
	long _idleTimeOut;
	String _DBMS;
	String _driver;
	String _databaseURL;
	String _userID;
	String _password;
	String _dbName;
	Hashtable _connectionParms;

	DBConnectionList(int max, long timeOut, long idleTimeout, String DBMS, String jdbcDriver, String databaseURL, String userID, String password, String dbName, String connectionParms) {
		super();
		_max = max;
		_timeOut = timeOut;
		_idleTimeOut = idleTimeout;
		_count = 0;
		_DBMS = DBMS;
		_driver = jdbcDriver;
		_databaseURL = databaseURL;
		_userID = userID;
		_password = password;
		_dbName = dbName;
		setConnectionParms(connectionParms);
	}
	
	DBConnectionList(int max, long timeOut, long idleTimeout, String DBMS, String jdbcDriver, String databaseURL, String userID, String password, String dbName) {
		this(max,timeOut,idleTimeout,DBMS,jdbcDriver,databaseURL,userID,password,dbName,null);	
	}
		
	/**
	 * This method will remove all idle connections from the connection pool.
	 */
	public synchronized void clearIdleConnections() throws SQLException {
		int size = _unused.size();
		Vector v = new Vector(size);
		for (int i = 0; i < size; i++) {
			DBConnection conn = (DBConnection) _unused.pop();
			if (conn.getJDBCConnection().isClosed()) {
				_count--;
			} else if (System.currentTimeMillis() > (conn.getLastUsed() + _idleTimeOut)) {
				try {
					_count--;
					conn.getJDBCConnection().close();
				} catch (Exception e) {
				}
			} else {
				v.addElement(conn);
			}
		}

		for (int i = 0; i < v.size(); i++) {
			_unused.push(v.elementAt(i));
		}
	}
	Connection createConnection() throws SQLException {
		try {
			Properties JDBCParms = null;
			Hashtable parms = getConnectionParms();
			Enumeration enumera = parms.keys();
			while (enumera.hasMoreElements()) {
				String st=(String) enumera.nextElement();
				if (st != null && st.startsWith("JDBC-")) {
					if (JDBCParms == null) {
						JDBCParms=new Properties();
						JDBCParms.setProperty("user",_userID);
						JDBCParms.setProperty("password",_password);	
					}		
					JDBCParms.setProperty(st.substring(5),(String)parms.get(st));		
				}	
			}
				
			Class.forName(_driver);
			Connection connection=null;
			if (JDBCParms == null)
			   connection = DriverManager.getConnection(_databaseURL, _userID, _password);
			else 
			   connection = DriverManager.getConnection(_databaseURL,JDBCParms);  
			_count++;
			return connection;
		} catch (ClassNotFoundException ex) {
			throw (new java.sql.SQLException(ex.toString()));
		} catch (SQLException ex) {
			throw (ex);
		}
	}
	/**
	 * This method was created in VisualAge.
	 */
	private void dump(String message) {
		if (getInUseCount() >= (_max - 2)) {
			System.err.println("---------------------");
			//System.err.println(message);
			//System.err.println("first=" + _first);
			//System.err.println("last=" + _last);
			//System.err.println("list=");
			//DBConnection node = _first;
			//while (node != null)
			//{
			//System.err.println(node);
			//node = node.getNext();

			//}

			System.err.println("IdleCount =" + getIdleCount());
			System.err.println("InUseCount =" + getInUseCount());
			System.err.println("Wait Time Out =" + _timeOut);
			System.err.println("Max =" + _max);
			System.err.println("---------------------");
		}
	}
	/**
	 * This method was created in VisualAge.
	 * @return com.salmonllc.sql.DBConnection
	 */
	synchronized void freeConnection(DBConnection conn) {
		if (conn == null)
			return;

		if (!conn.getInUse())
			return;

		DBConnection next = conn.getNext();
		DBConnection prior = conn.getPrior();

		if (next != null)
			next.setPrior(prior);

		if (prior != null)
			prior.setNext(next);

		if (conn == _last)
			_last = prior;

		if (conn == _first)
			_first = next;

		conn.setNext(null);
		conn.setPrior(null);

		_unused.push(conn);

		conn.setLastDuration(System.currentTimeMillis() - conn.getLastUsed());
		conn.setInUse(false);

		notifyAll();
		dump("freeConnection:" + conn);
	}
	/**
	 * This method was created in VisualAge.
	 * @return com.salmonllc.sql.DBConnection
	 */
	synchronized DBConnection getConnection() throws SQLException {
		DBConnection DBConn = null;

		if (_unused.isEmpty()) {
			Connection conn = null;
			if (_count < _max) {
				conn = createConnection();
				DBConn = new DBConnection(conn, _DBMS, false, false);
				DBConn.setDatabaseDriver(_driver);
				DBConn.setUserName(_userID);
				DBConn.setPassword(_password);
				DBConn.setDatabaseUrl(_databaseURL);
				DBConn.setCreated(System.currentTimeMillis());
			} else
				DBConn = waitForConnection();
		} else {
			DBConn = getUnused();
		}

		if (_first == null)
			_first = DBConn;
		if (_last != null)
			_last.setNext(DBConn);

		DBConn.setPrior(_last);
		DBConn.setNext(null);
		DBConn.setList(this);
		DBConn.setInUse(true);
		DBConn.setLastUsed(System.currentTimeMillis());

		_last = DBConn;

		dump("getConnection:" + DBConn);

		return DBConn;
	}
	/**
	 * This method returns an enumeration of all the connections maintained in the list.
	 */
	public Enumeration getConnections() {
		Vector v = new Vector(_count);
		for (int i = 0; i < _unused.size(); i++) {
			v.addElement(_unused.elementAt(i));
		}

		DBConnection node = _first;
		while (node != null) {
			v.addElement(node);
			node = node.getNext();
		}

		return v.elements();
	}
	/**
	 * This method returns the Database URL for the connection pool.
	 */
	public String getDatabaseURL() {
		return _databaseURL;
	}
	/**
	 * This method returns the Database User for the connection pool.
	 */
	public String getDatabaseUser() {
		return _userID;
	}
	/**
	 * This method returns the DBMS for the connection pool.
	 */
	public String getDBMS() {
		return _DBMS;
	}
	/**
	 * This method returns the DB name for the connection pool.
	 */
	public String getDBName() {
		return _dbName;
	}
	/**
	 * This method returns the Database Driver for the connection pool.
	 */
	public String getDriver() {
		return _driver;
	}
	/**
	 * This method returns the number of idle connections in the list.
	 */
	public int getIdleCount() {
		return _unused.size();
	}
	/**
	 * This method returns the number of connections currently in use.
	 */
	public int getInUseCount() {
		return _count - _unused.size();
	}
	private DBConnection getUnused() throws SQLException {
		DBConnection conn = (DBConnection) _unused.pop();
		if (conn.getJDBCConnection().isClosed()) {
			_count--;
			conn.setJDBCConnection(createConnection());
			conn.setCreated(System.currentTimeMillis());
		}
		return conn;
	}
	void setIdleTimeOut(long time) {
		_idleTimeOut = time;
	}
	void setMax(int max) {
		_max = max;
	}
	void setWaitTimeOut(long time) {
		_timeOut = time;
	}
	private DBConnection waitForConnection() throws SQLException {

		if (_unused.isEmpty() && _count >= _max) {
			// dsa 05-29-2001
			try {
				com.salmonllc.util.MessageLog.writeDebugMessage("-------------- waiting for connection---------" + _timeOut, null);
				wait(_timeOut);

				if (_unused.isEmpty() && _count >= _max)
					throw (new SQLException("Timeout occurred waiting for connection"));
			} catch (InterruptedException ie) {
				// go
			} catch (SQLException se) {
				// throw it to the caller
				throw (se);

				// maybe after we should try to getConnection again
			} catch (Exception e) {
				com.salmonllc.util.MessageLog.writeErrorMessage("waitForConnection", e, this);
			}

		}

		if (_unused.isEmpty()) {
			Connection conn = createConnection();
			DBConnection dbc = new DBConnection(conn, _DBMS, false, false);
			dbc.setDatabaseDriver(_driver);
			dbc.setUserName(_userID);
			dbc.setPassword(_password);
			dbc.setDatabaseUrl(_databaseURL);
			dbc.setCreated(System.currentTimeMillis());
			return dbc;
		} else {
			return getUnused();
		}

	}
	/**
	* This method sets parameters for the connection
	*/
	private void setConnectionParms(String parms) {
		_connectionParms = DBConnection.parseConnectionParms(parms);
	}

	/**
	 * This method returns a connection parameter
	 * @param key
	 * @return
	 */
	String getConnectionParm(String key) {
		if (_connectionParms == null)
			return null;
		return (String) _connectionParms.get(key);
	}

	Hashtable getConnectionParms() {
		return _connectionParms;
	}
}
