package infraestructura.controllers;


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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * This class generates a unique number for the primary keys in the database.
 * There are different ways to generate the sequence number current code implements following;
 * I created a static singleton sequence generator. Selected and stored all types from the database into a static instance variables. Whenever a new sequence number is required, I increment the number in the variables and update the corresponding value in the sequence table. Code looks like this;
 *
 * Initilize_Once_In_Constructor{
 * 	SELECT all types from the sequence table and store the values in Instance
 * 	variables of A, B, C...
 * }
 *
 * synchronized getNextNumber{
 * 	Increment the instance variable of A
 * 	UPDATE the new value in sequence generator by A;
 * 	Return A
 * }
 *
 * Possible problem with the above logic is that you must have an instance
 * variable for each primary key type. You may store those variables in a hashtable to lookup and update the values.
 *
 *  Other approaches would be;
 *  1= Update the sequence table in the database and select the new value from DB.  This approach requires at lease 2 hits to the database.This would be the safest but also the slowest approach. Code looks like this;
 *
 * Try{
 * 	UPDATE the new value in sequence generator;
 * 	SELECT the updated value from database and return it
 * }catch{
 * 	INSERT the new value in sequence generator;
 * 	SELECT the updated value from database and return it
 * }
 *
 *
 *
 *  2= In the application like petstore I do not need the above logic either. I can select the MAX primary key values from different tables and store them in the instance variables. Increment and return the new values. This would hit the database once the application is started. Than we have nothing to do with database. This would be the fastest solution. Code look like this;
 *
 * Initilize_Once_In_Constructor{
 * 	SELECT MAX(primary_Key) from EACH table and store the values in Instance
 * variables of A, B, C...
 * }
 *
 * synchronized getNextNumber{
 * Increment the instance variable of A
 * Return A
 * }
 *
 *  3= All of the above solutions has advantages and disadvantages. The major drawback is if you want to deploy the application in different servers or you want the external procedures like stored procedure in the DB use the same logic you may want to use a servlet which has static variables set the to max on load and then when the value changes just do an update to the database. This servlet would be the only code, which updates table. Any other code would use a function, which talks to the servlet to get its next value. This function could then be used from any server in a clustered environment. The servlet would only be loaded on one of the clustered machines.
 *
 *
 *
 */

public class SequenceGenerator implements Constants{
	private static  String _sAppName= "petmart";

	//Following variables initilized once.
	private static int _user_id = 0;

	private static boolean _isInitilized = false;
	private static SequenceGenerator _seqGen = null;

	/**
	 * Private constructor.
	 */
	private SequenceGenerator(String app_name) {
		super();

		if (app_name != null)
			_sAppName = app_name;
	}

	/**
	 * This method will be called in the base page. We generate only one instance of the object.
	 */
	public static SequenceGenerator getSequenceGenerator() {
		if (_seqGen == null || !_isInitilized){
			_seqGen = new SequenceGenerator(_sAppName );

			try{
				initValues();
				_isInitilized = true;
			}catch(Exception e){
				MessageLog.writeErrorMessage("******//////////***** ERROR At init SequenceGenerator", e, _seqGen);
			}
		}
		return _seqGen;
	}

	/**
	 * Returns the new index number.
	 * @param sType
	 * @return int Index.
	 */
	private int getNewValue(String sType){
		if (sType == null)
			return -1;

		if(sType.equals(USER_ID_FIELD))
			return ++_user_id;

		return -1;
	}

	/**
	 * Initilizes the static variables.
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	public static void initValues() throws SQLException, DataStoreException{
		DataStore ds = new DataStore(_sAppName,"infraestructura");
		ds.addColumn(SEQUENCE_TABLE, SEQ_ID_FIELD, DataStoreBuffer.DATATYPE_INT, false, true);
		ds.addColumn(SEQUENCE_TABLE, SEQ_TYPE_FIELD, DataStoreBuffer.DATATYPE_STRING, true, true);
		ds.retrieve();
		ds.waitForRetrieve();

		int iRowCnt = ds.getRowCount();

		for (int i=0 ; i<iRowCnt ; i++){
			String sType = ds.getString(i, SEQUENCE_SEQ_TYPE_TFC);
			int iSeq = ds.getInt(i, SEQUENCE_SEQ_ID_TFC);

			if(sType == null)
				continue;

			if(sType.equals(USER_ID_FIELD))
				_user_id = iSeq;
			else
				MessageLog.writeDebugMessage("Invalid type, " + sType, _seqGen);
		}
	}

	/**
	 * Retrieves next number in the sequence table.
	 * @param seqType
	 * @return  int
	 * @throws SQLException
	 */
	public int getNextNumber(String seqType) throws SQLException{
		return getNextNumber(seqType, null, 1);
	}

	/**
	 * Sets the application name which is required to make the database connection.
	 * @param _sAppName
	 */
	public static void setAppName(String _sAppName) {
		SequenceGenerator._sAppName = _sAppName;
	}

	/**
	 * Retrieves next number in the sequence table.
	 * @param seqType
	 * @param sAppName
	 * @param blockSize
	 * @return long
	 * @throws SQLException
	 */
	synchronized public int getNextNumber(String seqType,  String sAppName, int blockSize) throws SQLException{

		if(sAppName !=null)
			_sAppName = sAppName;

		int nextNo = -1;
		DBConnection conn = null;
		Statement s = null;
		ResultSet r = null;
		try {
			//Get the new value from the static variables.
			nextNo = getNewValue(seqType);

			if (nextNo < 0){
				MessageLog.writeDebugMessage("******* ERROR; Invalid sequence number for " + seqType, this);
				return -1;
			}
			//Create a connection with the properties from the system.properties file
			conn = DBConnection.getConnection(_sAppName);
			s = conn.createStatement();
			//set the new value of the number in the database
			s.executeUpdate("UPDATE " + SEQUENCE_TABLE + " SET " + SEQ_ID_FIELD + " = " + nextNo + " WHERE " + SEQ_TYPE_FIELD + " = '" + seqType + "'");
		} catch (SQLException e){
			//If there is no entry in the sequence table handel the exception and create a new row in the sequence table...
			try{
				if (conn != null){
					s = conn.createStatement();
					//Insert a new value for the current type
					s.executeUpdate("INSERT INTO " + SEQUENCE_TABLE + "  values('" + seqType + "'," +nextNo + ")");
				}

			} catch (Exception ee){
				MessageLog.writeErrorMessage("getNextNumber", ee, null);
			}
		} catch (Exception e){
			MessageLog.writeErrorMessage("getNextNumber", e, null);
		} finally{
			// Make sure that all closed nicely...
			if (r != null){
				r.close();
			}
			if (s != null){
				s.close();
			}
			if (conn != null)
				conn.freeConnection();
		}
		return nextNo;
	}
}
