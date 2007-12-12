//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.sql;

import java.lang.reflect.Method;

/**
 * Thin wrapper for DB2 "SqlException" and "DB2Sqlca", to make it look like a real Exception with
 * a working getMessage() that presents the almost-human-readable string hidden in the Sqlca
 * (what does "CA" stand for, anyway?) instead of the normal random garbage that you get from a DB2
 * SQLException, like "SQLCode -321, SqlState 32891, SqlErrMc null".
 * <p>Example usage:
 * <pre>try {
 * .....
 * } catch (SQLException ex) {
 * 	if (e.getClass().getName().equals("com.ibm.db2.jcc.c.SqlException")) {
 *				throw new DB2SalvageException(e);
 *		}
 *		throw new JspException(e.getMessage());
 * }</pre>
 * <p>
 * This Exception has been rewritten to use dynamic loading (from class Class) so that it should work fine whether you
 * have DB2 on your classpath or not; the DB2 drivers are definitely NOT needed to compile this (obviously, the SOFIA
 * framework could not be allowed to require DB2 drivers in order to compile...).
 * @author Ian Darwin.
 */
public final class DB2SalvageException extends RuntimeException {
	private static Class ibmSqlExceptionClass;
	private static Class ibmSqlCaClass;
	private static Method method_getMessage; // SqlException.getSqlca();
	private static Method method_getSqlca; // DB2Sqlca.getMessage();
	private Exception boringOldException;
	private Object ibmSqlEx;
	private Object ca;

	static {
		try {
			ibmSqlExceptionClass = Class.forName("com.ibm.db2.jcc.c.SqlException");
			ibmSqlCaClass = Class.forName("com.ibm.db2.jcc.DB2Sqlca");
			method_getSqlca = ibmSqlExceptionClass.getMethod("getSqlca", new Class[0]);
			method_getMessage = ibmSqlCaClass.getMethod("getMessage", new Class[0]);
		} catch (ClassNotFoundException e) {
			// This nattering omitted for the benefit of those who don't have to put up with DB2 :-)
			// System.err.println("IBM DB2 Driver Classes not on ClassPath");
		} catch (SecurityException e) {
			// "Can't Happen"
		} catch (NoSuchMethodException e) {
			System.err.println("DB2SalvageException.<clinit>: Problem finding getMethod(): " + e);
		}
	}
	
	/** Construct a DB2SalvageException around what is expected to be DB2 exception
	 * @param ex The SQLException you caught from the DB2 driver.
	 */
	public DB2SalvageException(Exception ex) {
		// this.ex = (SqlException)ex;
		//	ca = this.ex.getSqlca();
		
		if (ibmSqlExceptionClass == null) {
			boringOldException = ex;
		}
		
		if (!(ibmSqlExceptionClass.isAssignableFrom(ex.getClass()))) {
			throw new IllegalArgumentException("Can only salvage a DB2 SqlException");
		}
		ibmSqlEx = ex;
		try {
			ca =method_getSqlca.invoke(ibmSqlEx, null);
 
		} catch (Exception e) {
			System.err.println("Unable to invoke method: " + e);
		}
	}

	/** Present this Exception object as a String.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "DB2SalvageException: " + getMessage();
	}

	/** Get the almost-human-readable message contained in the nested DB2Sqlca object.
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		if (boringOldException != null) {
			return boringOldException.getMessage();
		}
		try {
			//return ca.getMessage();
			String result = (String)method_getMessage.invoke(ca, null);
			if (result == null) {
				return ibmSqlEx.toString();
			}
			return result;
		 } catch (NullPointerException npe) {
			return "The DB2 sqlca getMessage() threw a NPE; original was  " + ibmSqlEx.toString();
		 } 	catch (Exception e) {
			return "The DB2 sqlca getMessage() threw " + e + "; original was  " + ibmSqlEx.toString();
		}
	}
}
