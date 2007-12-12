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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DBStatement.java $
//$Author: Ross $
//$Revision: 12 $
//$Modtime: 8/05/04 6:10p $
/////////////////////////

import com.salmonllc.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

/**
 * This class serves as a wrapper around the standard java.sql.Statement. It behaves in the same way, except SQL Statements are written to the framework log file.
 */
public class DBStatement implements Statement {
    Statement _st;
    DBConnection _conn;

    /**
     * Creates a new DBStatement.
     */
    DBStatement(Statement st, DBConnection conn) {
        super();
        _st = st;
        _conn = conn;
    }

    /**
     * JDBC 2.0
     *
     * Adds a SQL command to the current batch of commmands for the statement.
     * This method is optional.
     *
     * @param sql typically this is a static SQL INSERT or UPDATE statement
     * @exception SQLException if a database access error occurs, or the
     * driver does not support batch statements
     */
    public void addBatch(String sql) throws SQLException {
        _st.addBatch(sql);
    }	/**
     * JDBC 2.0
     *
     * Makes the set of commands in the current batch empty.
     * This method is optional.
     *
     * @exception SQLException if a database access error occurs or the
     * driver does not support batch statements
     */
    /**
     * Cancels this Statement object if both the DBMS and driver support aborting an SQL statement.
     */
    public void cancel() throws SQLException {
        _st.cancel();
    }
     /**
      * JDBC 2.0 Makes the set of commands in the current batch empty.
      */
    public void clearBatch() throws SQLException {
        _st.clearBatch();
    }
    /**
     *  Clears all the warnings reported on this Statement object.
     */
    public void clearWarnings() throws SQLException {
        _st.clearWarnings();
    }

    /**
     *  Releases this Statement object's database and JDBC resources immediately instead of waiting for this to happen when it is automatically closed.
     */
    public void close() throws SQLException {
        _st.close();
    }

    /**
     *  Executes a SQL statement that may return multiple results.
     */
    public boolean execute(String sql) throws SQLException {
        MessageLog.writeSQLMessage("execute()", sql, this);
        _conn.setLastSQL(sql);
        return _st.execute(sql);
    }

   /**
    * JDBC 2.0Submits a batch of commands to the database for execution. This method is optional.
    *@return an array of update counts containing one element for each command in the batch. The array is ordered according to the order in which commands were inserted into the batch.
    */
    public int[] executeBatch() throws SQLException {
        return _st.executeBatch();
    }
    /**
     * Executes a SQL statement that returns a single ResultSet.
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        MessageLog.writeSQLMessage("executeQuery()", sql, this);
        _conn.setLastSQL(sql);
        return _st.executeQuery(sql);
    }

    /**
     *Executes an SQL INSERT, UPDATE or DELETE statement. In addition, SQL statements that return nothing, such as SQL DDL statements, can be executed.
     */
    public int executeUpdate(String sql) throws SQLException {
        MessageLog.writeSQLMessage("executeUpdate()", sql, this);
        _conn.setLastSQL(sql);
        return _st.executeUpdate(sql);
    }


    /**
     * Returns the database connection this is statement is using
     */
    public Connection getConnection() throws SQLException 
    {
        return _st.getConnection();
    }

    /**
     * JDBC 2.0
     *
     * Retrieves the direction for fetching rows from
     * database tables that is the default for result sets
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch direction by calling the method <code>setFetchDirection</code>,
     * the return value is implementation-specific.
     *
     * @return the default fetch direction for result sets generated
     *          from this <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     */
    public int getFetchDirection() throws SQLException {
        return _st.getFetchDirection();
    }

    /**
     * JDBC 2.0
     *
     * Retrieves the number of result set rows that is the default
     * fetch size for result sets
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch size by calling the method <code>setFetchSize</code>,
     * the return value is implementation-specific.
     * @return the default fetch size for result sets generated
     *          from this <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     */
    public int getFetchSize() throws SQLException {
        return _st.getFetchSize();
    }
    /**
     * Returns the maximum number of bytes allowed for any column value. This limit is the maximum number of bytes that can be returned for any column value. The limit applies only to BINARY, VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and LONGVARCHAR columns. If the limit is exceeded, the excess data is silently discarded.
     */
    public int getMaxFieldSize() throws SQLException {
        return _st.getMaxFieldSize();
    }

    /**
     * Returns the maximum number of bytes allowed for any column value. This limit is the maximum number of bytes that can be returned for any column value. The limit applies only to BINARY, VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and LONGVARCHAR columns. If the limit is exceeded, the excess data is silently discarded.
     */
    public int getMaxRows() throws SQLException {
        return _st.getMaxRows();
    }

/**
 * Moves to a Statement's next result. It returns true if this result is a ResultSet. This method also implicitly closes any current ResultSet obtained with getResultSet. There are no more results when (!getMoreResults() && (getUpdateCount() == -1)
 */
     public boolean getMoreResults() throws SQLException {
        return _st.getMoreResults();
    }

    /**
     * Retrieves the number of seconds the driver will wait for a Statement to execute. If the limit is exceeded, a SQLException is thrown.
     */
    public int getQueryTimeout() throws SQLException {
        return _st.getQueryTimeout();
    }

    /**
     * Returns the current result as a ResultSet object. This method should be called only once per result.
     */
    public ResultSet getResultSet() throws SQLException {
        return _st.getResultSet();
    }
     /**
      * JDBC 2.0 Retrieves the result set concurrency.
      */
    public int getResultSetConcurrency() throws java.sql.SQLException {
        return _st.getResultSetConcurrency();
    }

    /**
     * JDBC 2.0
     *
     * Determine the result set type.
     */
    public int getResultSetType() throws SQLException {
        return _st.getResultSetType();
    }
    /**
     * Returns the current result as an update count; if the result is a ResultSet or there are no more results, -1 is returned. This method should be called only once per result.
     */
    public int getUpdateCount() throws SQLException {
        return _st.getUpdateCount();
    }

    /**
     * Retrieves the first warning reported by calls on this Statement. Subsequent Statement warnings will be chained to this SQLWarning.<BR><BR>
    * The warning chain is automatically cleared each time a statement is (re)executed.<BR>
    *  Note: If you are processing a ResultSet, any warnings associated with ResultSet reads will be chained on the ResultSet object.
     */
    public SQLWarning getWarnings() throws SQLException {
        return _st.getWarnings();
    }

    /**
     *  Defines the SQL cursor name that will be used by subsequent Statement execute methods.
     */
    public void setCursorName(String name) throws SQLException {
        _st.setCursorName(name);
    }

    /**
     * Sets escape processing on or off. If escape scanning is on (the default), the driver will do escape substitution before sending the SQL to the database. Note: Since prepared statements have usually been parsed prior to making this call, disabling escape processing for prepared statements will have no effect.
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        _st.setEscapeProcessing(enable);
    }

    /**
     * JDBC 2.0 Gives the driver a hint as to the direction in which the rows in a result set will be processed. The hint applies only to result sets created using this Statement object. The default value is ResultSet.FETCH_FORWARD.
     * Note that this method sets the default fetch direction for result sets generated by this Statement object. Each result set has its own methods for getting and setting its own fetch direction.
     */
    public void setFetchDirection(int direction) throws SQLException {
        _st.setFetchDirection(direction);
    }

    /**
     * JDBC 2.0
     *
     * Gives the JDBC driver a hint as to the number of rows that should
     * be fetched from the database when more rows are needed.  The number
     * of rows specified affects only result sets created using this
     * statement. If the value specified is zero, then the hint is ignored.
     * The default value is zero.
     *
     * @param rows the number of rows to fetch
     * @exception SQLException if a database access error occurs, or the
     * condition 0 <= rows <= this.getMaxRows() is not satisfied.
     */
    public void setFetchSize(int rows) throws SQLException {
        _st.setFetchSize(rows);
    }

    /**
     * Sets the limit for the maximum number of bytes in a column to the given number of bytes. This is the maximum number of bytes that can be returned for any column value. This limit applies only to BINARY, VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and LONGVARCHAR fields. If the limit is exceeded, the excess data is silently discarded. For maximum portability, use values greater than 256.
     */
    public void setMaxFieldSize(int max) throws SQLException {
        _st.setMaxFieldSize(max);
    }

    /**
     * Retrieves the maximum number of rows that a ResultSet can contain. If the limit is exceeded, the excess rows are silently dropped.
     */
    public void setMaxRows(int max) throws SQLException {
        _st.setMaxFieldSize(max);
    }

    /**
     * Retrieves the number of seconds the driver will wait for a Statement to execute. If the limit is exceeded, a SQLException is thrown.
     */
    public void setQueryTimeout(int seconds) throws SQLException {
        _st.setQueryTimeout(seconds);
    }


    private Object findAndInvokeMethod(String method, Object[] args) throws SQLException {
        //Used to invoke methods in the underlying Statement Object dynamically.
        //This is there so the class will be able to execute methods added in JDK 1.4, but still compile
        //in JDK 1.2 and JDK 1.3
        try {
            Class[] c = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Integer)
                    c[i] = Integer.TYPE;
                else
                    c[i] = args[i].getClass();
            }
            Method m = _st.getClass().getMethod(method,c);
            return m.invoke(_st,args);
        }
        catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof SQLException)
                throw (SQLException) e.getTargetException();
            else
                return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that any
     * auto-generated keys should be made available
     * for retrieval.  The driver will ignore this signal if the SQL statement
     * is not an <code>INSERT</code> statement.
     * <P>
     * In some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <P>
     * The <code>execute</code> method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * <code>getResultSet</code> or <code>getUpdateCount</code>
     * to retrieve the result, and <code>getMoreResults</code> to
     * move to any subsequent result(s).
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     *
     * @param sql any SQL statement
     * @param autoGeneratedKeys a constant indicating whether auto-generated
     *        keys should be made available for retrieval using the method
     *        <code>getGeneratedKeys</code>; one of the following constants:
     *        <code>Statement.RETURN_GENERATED_KEYS</code> or
     *	      <code>Statement.NO_GENERATED_KEYS</code>
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if it is an update count or there are
     *         no results
     * @exception SQLException if a database access error occurs
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     * @see #getGeneratedKeys
     *
     * @since 1.4
     */
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
        MessageLog.writeSQLMessage("execute()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, new Integer(autoGeneratedKeys)};
        Object ret = findAndInvokeMethod("execute",args);
        if (ret != null)
            return ((Boolean) ret).booleanValue();
        else
            return false;
	}

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.  This array contains the indexes of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the given SQL statement
     * is not an <code>INSERT</code> statement.
     * <P>
     * Under some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <P>
     * The <code>execute</code> method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * <code>getResultSet</code> or <code>getUpdateCount</code>
     * to retrieve the result, and <code>getMoreResults</code> to
     * move to any subsequent result(s).
     *
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @param sql any SQL statement
     * @param columnIndexes an array of the indexes of the columns in the
     *        inserted row that should be  made available for retrieval by a
     *        call to the method <code>getGeneratedKeys</code>
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if it is an update count or there
     *         are no results
     * @exception SQLException if a database access error occurs
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     *
     * @since 1.4
     */
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        //This calls the findAndInvokeSQL so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
		MessageLog.writeSQLMessage("execute()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, columnIndexes};
        Object ret = findAndInvokeMethod("execute",args);
        if (ret != null)
            return ((Boolean) ret).booleanValue();
        else
            return false;
	}

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval. This array contains the names of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the given SQL statement
     * is not an <code>INSERT</code> statement.
     * <P>
     * In some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <P>
     * The <code>execute</code> method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * <code>getResultSet</code> or <code>getUpdateCount</code>
     * to retrieve the result, and <code>getMoreResults</code> to
     * move to any subsequent result(s).
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @param sql any SQL statement
     * @param columnNames an array of the names of the columns in the inserted
     *        row that should be made available for retrieval by a call to the
     *        method <code>getGeneratedKeys</code>
     * @return <code>true</code> if the next result is a <code>ResultSet</code>
     *         object; <code>false</code> if it is an update count or there
     *         are no more results
     * @exception SQLException if a database access error occurs
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     * @see #getGeneratedKeys
     *
     * @since 1.4
     */
	public boolean execute(String sql, String[] columnNames) throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
		MessageLog.writeSQLMessage("execute()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, columnNames};
        Object ret = findAndInvokeMethod("execute",args);
        if (ret != null)
            return ((Boolean) ret).booleanValue();
        else
            return false;
	}

    /**
     * Executes the given SQL statement and signals the driver with the
     * given flag about whether the
     * auto-generated keys produced by this <code>Statement</code> object
     * should be made available for retrieval.
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @param sql must be an SQL <code>INSERT</code>, <code>UPDATE</code> or
     *        <code>DELETE</code> statement or an SQL statement that
     *        returns nothing
     * @param autoGeneratedKeys a flag indicating whether auto-generated keys
     *        should be made available for retrieval;
     *         one of the following constants:
     *         <code>Statement.RETURN_GENERATED_KEYS</code>
     *         <code>Statement.NO_GENERATED_KEYS</code>
     * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>
     *         or <code>DELETE</code> statements, or <code>0</code> for SQL
     *         statements that return nothing
     * @exception SQLException if a database access error occurs, the given
     *            SQL statement returns a <code>ResultSet</code> object, or
     *            the given constant is not one of those allowed
     * @since 1.4
     */
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
		MessageLog.writeSQLMessage("executeUpdate()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, new Integer(autoGeneratedKeys)};
        Object ret = findAndInvokeMethod("executeUpdate",args);
        if (ret != null)
            return ((Integer) ret).intValue();
        else
            return 0;
	}



    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.  The driver will ignore the array if the SQL statement
     * is not an <code>INSERT</code> statement.
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @param sql an SQL <code>INSERT</code>, <code>UPDATE</code> or
     *        <code>DELETE</code> statement or an SQL statement that returns nothing,
     *        such as an SQL DDL statement
     * @param columnIndexes an array of column indexes indicating the columns
     *        that should be returned from the inserted row
     * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>,
     *         or <code>DELETE</code> statements, or 0 for SQL statements
     *         that return nothing
     * @exception SQLException if a database access error occurs or the SQL
     *            statement returns a <code>ResultSet</code> object
     * @since 1.4
     */
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		MessageLog.writeSQLMessage("executeUpdate()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, columnIndexes};
        Object ret = findAndInvokeMethod("executeUpdate",args);
        if (ret != null)
            return ((Integer) ret).intValue();
        else
            return 0;
	}

    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.  The driver will ignore the array if the SQL statement
     * is not an <code>INSERT</code> statement.
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @param sql an SQL <code>INSERT</code>, <code>UPDATE</code> or
     *        <code>DELETE</code> statement or an SQL statement that returns nothing
     * @param columnNames an array of the names of the columns that should be
     *        returned from the inserted row
     * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>,
     *         or <code>DELETE</code> statements, or 0 for SQL statements
     *         that return nothing
     * @exception SQLException if a database access error occurs
     *
     * @since 1.4
     */
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.

        MessageLog.writeSQLMessage("executeUpdate()", sql, this);
        _conn.setLastSQL(sql);
        Object args[] = {sql, columnNames};
        Object ret = findAndInvokeMethod("executeUpdate",args);
        if (ret != null)
            return ((Integer) ret).intValue();
        else
            return 0;
	}

    /**
     * Retrieves any auto-generated keys created as a result of executing this
     * <code>Statement</code> object. If this <code>Statement</code> object did
     * not generate any keys, an empty <code>ResultSet</code>
     * object is returned.
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     *
     * @return a <code>ResultSet</code> object containing the auto-generated key(s)
     *         generated by the execution of this <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
	public ResultSet getGeneratedKeys() throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.

        Object args[] = {};
        return (ResultSet) findAndInvokeMethod("getGeneratedKeys",args);
	}

    /**
     * Moves to this <code>Statement</code> object's next result, returns
     * <code>true</code> if it is a <code>ResultSet</code> object, and
     * implicitly closes any current <code>ResultSet</code>
     * object(s) obtained with the method <code>getResultSet</code>.
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * <P>There are no more results when the following is true:
     * <PRE>
     *      <code>(!getMoreResults() && (getUpdateCount() == -1)</code>
     * </PRE>
     *
     * @return <code>true</code> if the next result is a <code>ResultSet</code>
     *         object; <code>false</code> if it is an update count or there are
     *         no more results
     * @exception SQLException if a database access error occurs
     * @see #execute
     */
	public boolean getMoreResults(int current) throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.

        Object args[] = {new Integer(current)};
        Object ret = findAndInvokeMethod("getMoreResults",args);
        if (ret != null)
            return ((Boolean) ret).booleanValue();
        else
            return false;
	}

   /**
     * Retrieves the result set holdability for <code>ResultSet</code> objects
     * generated by this <code>Statement</code> object.
     *
     * @return either <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *         <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @exception SQLException if a database access error occurs
     *
     * This will compile in JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.
     *
     * @since 1.4
     */
	public int getResultSetHoldability() throws SQLException {
        //This calls the findAndInvokeMethod so it will compile from JDK 1.3 and 1.4. It should only be called from a 1.4 JVM.

	    Object args[] = {};
        Object ret = findAndInvokeMethod("getResultSetHoldability",args);
        if (ret != null)
            return ((Integer) ret).intValue();
        else
            return 0;
	}

public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public boolean isPoolable() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public void setPoolable(boolean poolable) throws SQLException {
	// TODO Auto-generated method stub
	
}

public boolean isWrapperFor(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public Object unwrap(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


}
