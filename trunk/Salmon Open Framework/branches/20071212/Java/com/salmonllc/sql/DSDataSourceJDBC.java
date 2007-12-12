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

import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * This type was created in VisualAge.
 */
abstract class DSDataSourceJDBC implements DataSourceIn, DataSourceOut, Serializable {
	public static final int SEL_GEN_SELECT = 0;
	public static final int SEL_GEN_PROC = 1;
	public static final int SEL_STATIC = 2;
	public static final int SEL_PREP_STMT = 3;
	//fc 06/24/04
	public static final int SEL_GEN_PREP_STMT = 4;

	protected int _selType = SEL_GEN_SELECT;
	protected Statement _st;
	protected ResultSet _res;
	protected String _sql;
	protected String _proc;
	protected StoredProcedureParms _parms;
	protected String _tables[];
	protected StringBuffer _sqlb;
	protected int _autoIncrementCol;
	protected boolean _fixDashes = false;

	protected SimpleDateFormat _dateTimeFormat;

	private boolean _enableCancel = false; //Added by FC on 2/21/03. Was added
	// to handle JDBC drivers which have
	// problems with the cancel() method
	// being called. Example Driver:
	// JSQLDriver from NetDirect.
	//fc 06/11/04 Hashtable used in handling the DataStores with a one to many
	// relationship (Master/Detail mode).
	private Hashtable _htinsertedmasterkeydata;
	private Hashtable _batchedInserts;
    private DataStoreBuffer.TablesRelationshipDescription _dsbTableRelationshipsInserts;
    private DataStoreBuffer.TablesRelationshipDescription _dsbTableRelationshipsDeletes;
	/**
	 * DSSQLDataSource constructor comment.
	 */
	public DSDataSourceJDBC() {
		super();
		_dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * This method was created in VisualAge.
	 */
	public void cancel() throws Exception {
		//Modified by FC on 2/21/03. Was changed to handle JDBC drivers which
		// have problems with the cancel() method being called.
		//Example Driver: JSQLDriver from NetDirect.
		if (_enableCancel) {
			if (_st != null)
				_st.cancel();
		} else {
			if (_res != null)
				_res.close();
			if (_st != null)
				_st.close();
			_res = null;
			_st = null;
		}
	}

	//fc 06/11/04: This returns the foreign key data of a detail row in a
	// vector stored as String Repesentations for SQL purposes. Used for one to
	// many relationship models.
	private Vector getDetailRowForeignKey(DataStore dsData, DataStoreBuffer dsTables, DataStoreRow row, int iTableRow, DBConnection conn) throws DataStoreException {
		String sDetailColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.DETAILKEYCOLUMNS);
		String[] saColumns = Util.split(sDetailColumns,(","));
		Vector vKey = new Vector();
		Object oData;
		DSDataStoreDescriptor desc = dsData.getDescriptor();
		DSColumnDescriptor column;
		int iType;
		String sValue;
		for (int i = 0; i < saColumns.length; i++) {
			oData = row.getOriginalData(saColumns[i]);
			column = desc.getColumn(dsData.getColumnIndex(saColumns[i]));
			iType = column.getType();
			if (oData == null)
				sValue = "";
			else if (iType == DataStore.DATATYPE_DATETIME) {
				sValue = "{ts '" + formatDateTime((Timestamp) oData, _dateTimeFormat, conn) + "'}";
			} else if (iType == DataStore.DATATYPE_DATE) {
				sValue = "{d '" + DataStore.fixQuote(oData.toString(), iType, conn.getDBMS()) + "'}";
			} else if (iType == DataStore.DATATYPE_TIME) {
				sValue = "{t '" + DataStore.fixQuote(oData.toString(), iType, conn.getDBMS()) + "'}";
			} else if (iType == DataStore.DATATYPE_STRING) {
				sValue = "'" + DataStore.fixQuote(oData.toString(), iType, conn.getDBMS()) + "'";
			} else {
				sValue = oData.toString();
			}
			vKey.addElement(sValue);
		}
		return vKey;
	}

	//fc 06/14/04: This returns the primary key data of a detail row in a
	// vector. Used for one to many relationship models.
	private Vector getDetailRowPrimaryKey(DataStore dsData, DataStoreBuffer dsTables, DataStoreRow row, int iTableRow) throws DataStoreException {
		String sTable = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.TABLE) + ".";
		String[] saColumnList = dsData.getColumnList();
		DSDataStoreDescriptor desc = dsData.getDescriptor();
		DSColumnDescriptor column;
		Object oData;
		Vector vKey = new Vector();
		for (int i = 0; i < saColumnList.length; i++) {
			if (saColumnList[i].startsWith(sTable)) {
				column = desc.getColumn(dsData.getColumnIndex(saColumnList[i]));
				if (column.isPrimaryKey()) {
					oData = row.getOriginalData(saColumnList[i]);
					if (oData == null)
						vKey.addElement("");
					else
						vKey.addElement(oData);
				}
			}
		}
		return vKey;
	}

	//fc 08/13/04: This returns whether the primary key data of a detail row in
	// a vector is valid. Used for one to many relationship models.
	private boolean isValidPrimaryKey(DataStore dsData, DataStoreBuffer dsTables, DataStoreRow row, int iTableRow) throws DataStoreException {
		Vector vKey = getDetailRowPrimaryKey(dsData, dsTables, row, iTableRow);
		boolean bValid = false;
		for (int i = 0; i < vKey.size(); i++) {
			Object oKey = vKey.elementAt(i);
			if (!oKey.toString().equals("")) {
				bValid = true;
				break;
			}
		}
		return bValid;
	}

	//fc 08/13/04: This returns whether columns have been modified in master
	// table for a particular row.
	private boolean areTableValuesModified(DataStore dsData, DataStoreRow row, String sTable) throws DataStoreException {
		sTable = sTable + ".";
		String[] saColumnList = dsData.getColumnList();
		boolean bModified = false;
		for (int i = 0; i < saColumnList.length; i++) {
			if (saColumnList[i].startsWith(sTable)) {
				int iStatus = row.getColumnStatus(dsData.getColumnIndex(saColumnList[i]));
				if (iStatus != DataStoreBuffer.STATUS_NOT_MODIFIED) {
					bModified = true;
					break;
				}
			}
		}
		return bModified;
	}

	//fc 06/11/04: This returns the key data of a master row in a vector stored
	// as the actual object. Used for one to many relationship models.
	private Vector getMasterRowKeyData(DataStoreBuffer.TablesRelationshipDescription dsTables, DataStoreRow row, int iTableRow) throws DataStoreException {
		String sMasterColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.MASTERKEYCOLUMNS);
		String[] saColumns = Util.split(sMasterColumns,",");
		Vector vKey = new Vector();
		Object oData;
		for (int i = 0; i < saColumns.length; i++) {
			oData = row.getData(saColumns[i]);
			if (oData != null)
				vKey.addElement(oData);
			else
				vKey.addElement("");
		}
		return vKey;
	}

	//fc 06/11/04: Copies the master key values to the foreign keys in the
	// detail row. Used for one to many relationship models.
	private void copyMasterValuesToDetail(DataStoreBuffer.TablesRelationshipDescription dsTables, DataStoreRow row, int iTableRow) throws DataStoreException {
		String sMasterColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.MASTERKEYCOLUMNS);
		String sDetailColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.DETAILKEYCOLUMNS);
		String[] saDetailColumns = Util.split(sDetailColumns,",");
		String[] saMasterColumns = Util.split(sMasterColumns,",");
		for (int i = 0; i < saDetailColumns.length; i++) {
			row.setData(saDetailColumns[i], row.getData(saMasterColumns[i]));
		}
	}

	//fc 06/11/04: Copies key values to master column to a specified row. Used
	// for one to many relationship models.
	private void copyKeyToMasterColumns(DataStoreBuffer.TablesRelationshipDescription dsTables, DataStoreRow row, int iTableRow, Vector vKeyData) throws DataStoreException {
		String sMasterColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.MASTERKEYCOLUMNS);
		String[] saMasterColumns = Util.split(sMasterColumns,",");
		Object oData;
		for (int i = 0; i < saMasterColumns.length; i++) {
			oData = vKeyData.elementAt(i);
			if (!oData.equals(""))
				row.setData(saMasterColumns[i], oData);
			else
				row.setData(saMasterColumns[i], null);
		}
	}

	//fc 06/11/04: Gets the detail row count from the database. Used for one to
	// many relationship models.
	private int getDetailRowCountFromDB(DataStore dsData, DataStoreBuffer.TablesRelationshipDescription dsTables, DataStoreRow row, int iTableRow, DBConnection conn) throws DataStoreException, SQLException {
		String sDetailColumns = dsTables.getString(iTableRow, DataStoreBuffer.TablesRelationshipDescription.DETAILKEYCOLUMNS);
		String[] saColumns = Util.split(sDetailColumns,(","));
		StringBuffer sbSQL = new StringBuffer();
		Vector vKey = getDetailRowForeignKey(dsData, dsTables, row, iTableRow, conn);
		for (int i = 0; i < saColumns.length; i++) {
			sbSQL.append(saColumns[i]);
			String sValue = (String) vKey.elementAt(i);
			if (sValue != null && !sValue.equals("")) {
				sbSQL.append(" = ");
				sbSQL.append(vKey.elementAt(i));
			} else {
				sbSQL.append(" is null");
			}
			sbSQL.append(" and ");
		}
		sbSQL.setLength(sbSQL.length() - 5);
		String[] saTableColumn = Util.split(saColumns[0],".");
		String sTable = saTableColumn[0];
		sbSQL.insert(0, "select count(*) from " + sTable + " where ");
		Statement st = null;
		int iRowCount = 0;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sbSQL.toString());
			if (rs.next())
				iRowCount = rs.getInt(1);
			st.close();
		} finally {
			if (st != null) {
				st.close();
			}
		}
		return iRowCount;
	}

	/**
	 * deleteRow method comment.
	 */
	public boolean deleteRow(DataStore ds, DataStoreRow row, DBConnection conn) throws Exception {
		//fc 06/11/04: Updated to handle One to Many Relationship model deletes
        DataStoreBuffer.TablesRelationshipDescription dsb = _dsbTableRelationshipsDeletes;
		String sTable;
		for (int i = 0; i < dsb.getRowCount(); i++) {
			sTable = dsb.getString(i, DataStoreBuffer.TablesRelationshipDescription.TABLE);
			int iRelType = dsb.getInt(i, DataStoreBuffer.TablesRelationshipDescription.RELATIONSHIPTYPE);
			_sql = "";
			PreparedStatement p = null;
			try {
				int iDetailRowCount = 0;
				if (iRelType == DataStoreBuffer.RELATION_ONE_TO_MANY) {
					iDetailRowCount = getDetailRowCountFromDB(ds, dsb, row, i, conn);
					if (iDetailRowCount > 0) {
						continue;
					} else {
						if (!ds.getAllowMasterRowDelete())
							continue;
					}
				}
				if (iRelType == DataStoreBuffer.RELATION_MANY_TO_ONE) {
					if (!isValidPrimaryKey(ds, dsb, row, i))
						continue;
				}
				p = prepareDelete(ds, row, sTable, conn, _sqlb);
				_sql = _sqlb.toString();
				if (p != null && p.executeUpdate() == 0)
					throw new DirtyDataException("Error: Deleted row changed between retrieve and update.");
			} finally {
				if (p != null)
					p.close();
			}
		}
		return true;

	}
	public String generateProc(DBConnection conn, DataStore ds) throws Exception {
		StringBuffer sql = new StringBuffer(256);
		sql.append("{call ");
		sql.append(_proc);
		if (_parms != null) {
			if (_parms.getParmCount() > 0) {
				sql.append(" (");
				for (int i = 0; i < _parms.getParmCount(); i++) {
					sql.append("?,");
				}
				sql.setCharAt(sql.length() - 1, ' ');
				sql.append(")");
			}
		}
		sql.append('}');

		String sqlSt = sql.toString();
		CallableStatement cst = null;

		cst = conn.prepareCall(sqlSt);
		if (_parms != null) {
			for (int i = 0; i < _parms.getParmCount(); i++)
				prepareForType(ds, cst, _parms.getParm(i), _parms.getDataType(i), i + 1);
		}
		_res = cst.executeQuery();
		_st = cst;

		return sqlSt;
	}
	public abstract String generateSelect(DataStore ds, String criteria, boolean countOnly) throws DataStoreException;
	/**
	 * This method was created in VisualAge.
	 *
	 * @return java.sql.ResultSetMetaData
	 */
	public ResultSetMetaData getMetaData() throws Exception {
		return _res.getMetaData();
	}
	/**
	 * This method was created in VisualAge.
	 *
	 * @return java.lang.String
	 */
	public String getSelect() {
		return _sql;
	}
	/**
	 * insertRow method comment.
	 */
	public boolean insertRow(DataStore ds, DataStoreRow row, DBConnection conn) throws Exception {
		//fc 06/11/04: Updated to handle One to Many Relationship model inserts
        DataStoreBuffer.TablesRelationshipDescription dsb = _dsbTableRelationshipsInserts;
		String sTable;
		for (int i = 0; i < dsb.getRowCount(); i++) {
			sTable = dsb.getString(i, DataStoreBuffer.TablesRelationshipDescription.TABLE);
			int iRelType = dsb.getInt(i, DataStoreBuffer.TablesRelationshipDescription.RELATIONSHIPTYPE);
			_sql = "";
			PreparedStatement p = null;
			try {
				_autoIncrementCol = -1;
				if (iRelType == DataStoreBuffer.RELATION_ONE_TO_MANY) {
					Vector vMasterRowKeyData = getMasterRowKeyData(dsb, row, i);
					Vector vKey = (Vector) _htinsertedmasterkeydata.get(sTable);
					if (vKey != null) {
						Object oData = vMasterRowKeyData.elementAt(0);
						if (oData.equals("")) {
							copyKeyToMasterColumns(dsb, row, i, vKey);
						}
						continue;
					} else {
						DataStoreRow firstrow = new DataStoreRow(ds, null, ds.getDescriptor());
						DSDataRow dsdrFirstRow = (DSDataRow) ((Vector) ds.getRows().elementAt(0)).elementAt(0);
						firstrow.setDSDataRow(dsdrFirstRow);
						Vector vFirstRowMasterRowKeyData = getMasterRowKeyData(dsb, firstrow, 0);
						if (firstrow.getDSDataRow().getRowStatus() == DataStoreBuffer.STATUS_NOT_MODIFIED || !areTableValuesModified(ds, row, sTable)) {
							_htinsertedmasterkeydata.put(sTable, vFirstRowMasterRowKeyData);
							ds.copyMasterKeyValuesToRow(dsb, firstrow, row, i);
							continue;
						}
					}
				}
				if (iRelType == DataStoreBuffer.RELATION_MANY_TO_ONE) {
					copyMasterValuesToDetail(dsb, row, i);
				}
				boolean updateHash=false;
				if (ds.getBatchInserts()) {
					p=getInsertBatch(sTable);
					if (p == null)
						updateHash=true;
				}
				p = prepareInsert(ds, row, sTable, conn, _sqlb,p);
				if (updateHash)
					setInsertBatch(sTable,p);

				_sql = _sqlb.toString();
				if (p != null) {
					if (ds.getBatchInserts()) {
						p.addBatch();
					}
					else if (p.executeUpdate() == 0)
						throw new SQLException("Error: Duplicate key on insert.");
				}

				if (_autoIncrementCol != -1)
					populateAutoIncrementValue(row, conn, _autoIncrementCol);
				if (p != null && iRelType == DataStoreBuffer.RELATION_ONE_TO_MANY) {
					Vector vMasterRowKeyData = getMasterRowKeyData(dsb, row, i);
					Vector vKey = (Vector) _htinsertedmasterkeydata.get(sTable);
					if (vKey == null)
						_htinsertedmasterkeydata.put(sTable, vMasterRowKeyData);
				}
			} finally {
				if (p != null && !ds.getBatchInserts())
					p.close();
			}
		}
		return true;
	}
	/**
	 * postRetrieve method comment.
	 */
	public void postRetrieve(DataStore ds) throws Exception {
		if (_res != null) {
			_res.close();
			_res = null;
		}
		if (_st != null) {
			_st.close();
			_st = null;
		}

	}
	/**
	 * postUpdate method comment.
	 */
	public void postUpdate(DataStore ds, DBConnection conn, boolean handleTrans, boolean updateSuccessful) throws Exception {
		Exception ex = null;
		try {
			if (_batchedInserts != null && updateSuccessful) {
				Enumeration en = _batchedInserts.elements();
				while (en.hasMoreElements()) {
					PreparedStatement st = (PreparedStatement) en.nextElement();
					st.executeBatch();
					st.close();
				}
			}
		} catch (Exception e) {
			updateSuccessful = false;
			ex = e;
		}

		if (handleTrans) {
			if (updateSuccessful)
				conn.commit();
			else
				conn.rollback();
		}

		_tables = null;
		_sqlb = null;
		_batchedInserts = null;
        _dsbTableRelationshipsDeletes=null;
        _dsbTableRelationshipsInserts=null;
		if (ex != null)
			throw (ex);

	}
	private PreparedStatement prepareDelete(DataStore ds, DataStoreRow row, String tableIn, DBConnection conn, StringBuffer sqlb) throws java.sql.SQLException, DataStoreException {

		DSColumnDescriptor col;
		String table;
		String name, value;
		int type;
		Object data;
		boolean useBind = false;

		SimpleDateFormat df = _dateTimeFormat;
		DSDataStoreDescriptor desc = ds.getDescriptor();

		sqlb.setLength(0);
		for (int i = 0; i < desc.getColumnCount(); i++) {
			col = desc.getColumn(i);
			table = col.getTable();
			if (table == null)
				table = desc.getDefaultTable();
			if (table == null)
				continue;

			if (table.equals(tableIn)) {
				data = row.getOriginalData(i);

				type = col.getType();
				name = col.getColumn();
				value = "";

				if (useBind(col, ds)) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = ?";
				} else if (type == DataStore.DATATYPE_DATETIME) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = {ts '" + formatDateTime((Timestamp) data, df, conn) + "'}";
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

				if (col.isPrimaryKey() || (col.getConcurrency() && ds.getCheckConcurrency())) {
					if (useBind(col, ds))
						useBind = true;

					if (sqlb.length() == 0)
						sqlb.append(" ");
					else
						sqlb.append(" AND ");
					sqlb.append(fixDashes(name));
					sqlb.append(value);
				}
			}
		}

		PreparedStatement p = null;

		if (sqlb.length() > 0) {
			sqlb.insert(0, "DELETE FROM " + fixDashes(desc.resolveTable(tableIn)) + " WHERE ");
			p = conn.prepareStatement(sqlb.toString());
			if (useBind) {
				int pos = 0;
				for (int i = 0; i < desc.getColumnCount(); i++) {
					table = desc.getDefaultTable();
					col = desc.getColumn(i);
					if (col.getTable() != null)
						table = col.getTable();
					if (table == null)
						continue;
					if (table.equals(tableIn) && useBind(col, ds) && (col.isPrimaryKey() || (col.getConcurrency() && ds.getCheckConcurrency()))) {
						data = row.getOriginalData(i);
						if (data != null) {
							pos++;
							prepareForType(ds, p, data, col.getType(), pos);
						}
					}
				}
			}
		}

		return p;

	}

	/**
	 * This method was created in VisualAge.
	 *
	 * @param pst
	 *            java.sql.PreparedStatement
	 * @param data
	 *            java.lang.Object
	 * @param type
	 *            int
	 */
	static void prepareForType(DataStore ds, PreparedStatement pst, Object data, int type, int pos) throws java.sql.SQLException {
		//fc 06/24/04
		if (type == DataStore.DATATYPE_ANY) {
			if (ds == null)
				ds = new DataStore();
			type = ds.getObjectType(data);
		}

		switch (type) {
			case DataStore.DATATYPE_STRING :
				if (data == null)
					pst.setNull(pos, java.sql.Types.VARCHAR);
				else {
					String sData = (String) data;
					if (sData.length() > 255) {
						StringBufferInputStream is = new StringBufferInputStream(sData);
						InputStreamReader isr = new InputStreamReader(is);
						pst.setCharacterStream(pos, isr, sData.length());
					} else
						pst.setString(pos, sData);
				}
				break;
			case DataStore.DATATYPE_INT :
				if (data == null)
					pst.setNull(pos, java.sql.Types.INTEGER);
				else
					pst.setInt(pos, ((Integer) data).intValue());
				break;
			case DataStore.DATATYPE_DATETIME :
				if (data == null)
					pst.setNull(pos, java.sql.Types.TIMESTAMP);
				else
					pst.setTimestamp(pos, (java.sql.Timestamp) data);
				break;
			case DataStore.DATATYPE_DATE :
				if (data == null)
					pst.setNull(pos, java.sql.Types.DATE);
				else
					pst.setDate(pos, (java.sql.Date) data);
				break;
			case DataStore.DATATYPE_TIME :
				if (data == null)
					pst.setNull(pos, java.sql.Types.TIME);
				else
					pst.setTime(pos, (java.sql.Time) data);
				break;
			case DataStore.DATATYPE_DOUBLE :
				if (data == null)
					pst.setNull(pos, java.sql.Types.DOUBLE);
				else
					pst.setDouble(pos, ((Double) data).doubleValue());
				break;
			case DataStore.DATATYPE_BYTEARRAY :
				pst.setBytes(pos, (byte[]) data);
				break;
			case DataStore.DATATYPE_SHORT :
				if (data == null)
					pst.setNull(pos, java.sql.Types.SMALLINT);
				else
					pst.setShort(pos, ((Short) data).shortValue());
				break;
			case DataStore.DATATYPE_LONG :
				if (data == null)
					pst.setNull(pos, java.sql.Types.BIGINT);
				else
					pst.setLong(pos, ((Long) data).longValue());
				break;
			case DataStore.DATATYPE_FLOAT :
				if (data == null)
					pst.setNull(pos, java.sql.Types.FLOAT);
				else
					pst.setFloat(pos, ((Float) data).floatValue());
				break;
			case DataStore.DATATYPE_ANY :
				pst.setNull(pos, java.sql.Types.VARCHAR);

		}

	}

	private PreparedStatement prepareInsert(DataStore ds, DataStoreRow row, String tableIn, DBConnection conn, StringBuffer sqlb, PreparedStatement pin) throws java.sql.SQLException, DataStoreException {
		DSColumnDescriptor col;
		Object data;
		int type;
		String value = "";
		StringBuffer columnList = new StringBuffer(256);
		StringBuffer values = new StringBuffer(256);
		boolean colUpdated = false;
		boolean useBind = false;
		DSDataStoreDescriptor desc = ds.getDescriptor();

		SimpleDateFormat df = _dateTimeFormat;

		for (int j = 0; j < desc.getColumnCount(); j++) {
			String table = desc.getDefaultTable();
			col = desc.getColumn(j);
			if (col.getTable() != null)
				table = col.getTable();
			if (table == null)
				continue;

			if (table.equals(tableIn)) {
				if (col.isAutoIncrement())
					_autoIncrementCol = j;
				if (col.isUpdateable()) {
					colUpdated = true;
					if (pin != null)
						useBind = true;
					else {
						data = row.getData(j);
						type = col.getType();
						if (ds.getBatchInserts() || useBind(col, ds)) {
							useBind = true;
							value = "?";
						} else if (data == null)
							value = "null";
						else if (type == DataStore.DATATYPE_DATETIME)
							value = "{ts '" + formatDateTime((Timestamp) data, df, conn) + "'}";
						else if (type == DataStore.DATATYPE_DATE)
							value = "{d '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";
						else if (type == DataStore.DATATYPE_TIME)
							value = "{t '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";
						else if (type == DataStore.DATATYPE_STRING)
							value = "'" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'";
						else
							value = data.toString();

						if (columnList.length() == 0) {
							columnList.append(fixDashes(col.getColumn()));
							values.append(value);
						} else {
							columnList.append("," + fixDashes(col.getColumn()));
							values.append("," + value);
						}
					}
				}

			}
		}

		if (pin == null) {
			sqlb.setLength(0);
			sqlb.append("INSERT INTO ");
			sqlb.append(fixDashes(desc.resolveTable(tableIn)));
			sqlb.append("(");
			sqlb.append(columnList.toString());
			sqlb.append(") VALUES (");
			sqlb.append(values.toString());
			sqlb.append(")");
		}
		if (colUpdated) {
			PreparedStatement p= (pin == null) ? conn.prepareStatement(sqlb.toString()) : pin;
			if (useBind) {
				int pos = 0;
				for (int j = 0; j < desc.getColumnCount(); j++) {
					String table = desc.getDefaultTable();
					col = desc.getColumn(j);
					if (col.getTable() != null)
						table = col.getTable();
					if (table == null)
						continue;
					if (table.equals(tableIn) && col.isUpdateable() && (useBind(col, ds)||ds.getBatchInserts())) {
						pos++;
						prepareForType(ds, p, row.getData(j), col.getType(), pos);
					}
				}
			}
			return p;
		} else
			return null;
	}
	private PreparedStatement prepareUpdate(DataStore ds, DataStoreRow row, String tableIn, DBConnection conn, StringBuffer sqlb) throws java.sql.SQLException, DataStoreException {

		DSColumnDescriptor col;
		String table, name;
		String value, value2;
		int type;
		Object data, curData;
		boolean colUpdated = false;
		boolean useBind = false;
		StringBuffer where = new StringBuffer(256);
		StringBuffer set = new StringBuffer(256);
		SimpleDateFormat df = _dateTimeFormat;
		DSDataStoreDescriptor desc = ds.getDescriptor();

		for (int i = 0; i < desc.getColumnCount(); i++) {
			col = desc.getColumn(i);
			table = col.getTable();
			if (table == null)
				table = desc.getDefaultTable();
			if (table == null)
				continue;

			if (table.equals(tableIn)) {
				data = row.getOriginalData(i);
				curData = row.getData(i);

				type = col.getType();
				name = col.getColumn();
				value = "";
				value2 = "";

				if (name == null) {
					continue;
				} else if (useBind(col, ds)) {
					if (data == null)
						value = " IS NULL ";
					else
						value = " = ? ";

					value2 = " = ?";
				} else if (type == DataStore.DATATYPE_DATETIME) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = {ts '" + formatDateTime((Timestamp) data, df, conn) + "'}";

					if (curData == null)
						value2 = " = NULL";
					else
						value2 = " = {ts '" + formatDateTime((Timestamp) curData, df, conn) + "'}";

				} else if (type == DataStore.DATATYPE_DATE) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = {d '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";

					if (curData == null)
						value2 = " = NULL";
					else
						value2 = " = {d '" + DataStore.fixQuote(curData.toString(), type, conn.getDBMS()) + "'}";

				} else if (type == DataStore.DATATYPE_TIME) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = {t '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'}";

					if (curData == null)
						value2 = " = NULL";
					else
						value2 = " = {t '" + DataStore.fixQuote(curData.toString(), type, conn.getDBMS()) + "'}";

				} else if (type == DataStore.DATATYPE_STRING) {
					if (data == null)
						value = " IS NULL";
					else
						value = " = '" + DataStore.fixQuote(data.toString(), type, conn.getDBMS()) + "'";

					if (curData == null)
						value2 = " = NULL";
					else
						value2 = " = '" + DataStore.fixQuote(curData.toString(), type, conn.getDBMS()) + "'";
				} else {
					if (data == null)
						value = " IS NULL";
					else
						value = " = " + data.toString();

					if (curData == null)
						value2 = " = NULL";
					else
						value2 = " = " + curData.toString();

				}

				if ((col.isPrimaryKey() || (col.getConcurrency() && ds.getCheckConcurrency()))) {
					if (where.length() == 0)
						where.append(" ");
					else
						where.append(" AND ");

					where.append(fixDashes(name));
					where.append(value);

					if (useBind(col, ds))
						useBind = true;

				}

				if (col.isUpdateable()) {
					if (row.isColumnModified(i)) {
						colUpdated = true;

						if (set.length() == 0)
							set.append(" SET ");
						else
							set.append(",");

						set.append(fixDashes(name));

						if (useBind(col, ds))
							useBind = true;

						set.append(value2);
					}
				}
			}
		}

		if (!colUpdated)
			return null;
		else {
			sqlb.setLength(0);
			sqlb.append("UPDATE ");
			sqlb.append(fixDashes(desc.resolveTable(tableIn)));
			sqlb.append(set.toString());
			sqlb.append(" WHERE ");
			sqlb.append(where.toString());
			PreparedStatement p = conn.prepareStatement(sqlb.toString());
			if (useBind) {
				int pos = 0;
				for (int i = 0; i < desc.getColumnCount(); i++) {
					table = desc.getDefaultTable();
					col = desc.getColumn(i);
					if (col.getTable() != null)
						table = col.getTable();
					if (table == null)
						continue;
					if (table.equals(tableIn) && useBind(col, ds) && col.isUpdateable() && row.isColumnModified(i)) {
						pos++;
						prepareForType(ds, p, row.getData(i), col.getType(), pos);
					}
				}

				for (int i = 0; i < desc.getColumnCount(); i++) {
					table = desc.getDefaultTable();
					col = desc.getColumn(i);
					if (col.getTable() != null)
						table = col.getTable();
					if (table == null)
						continue;
					if (table.equals(tableIn) && useBind(col, ds) && (col.isPrimaryKey() || (col.getConcurrency() && ds.getCheckConcurrency()))) {
						curData = row.getOriginalData(i);
						if (curData != null) {
							pos++;
							prepareForType(ds, p, curData, col.getType(), pos);
						}
					}
				}

			}
			return p;
		}

	}
	/**
	 * preRetrieve method comment.
	 */
	public boolean preRetrieve(DataStore ds, String criteria, boolean countOnly, DBConnection conn) throws Exception {
		//Modified by FC on 2/21/03. Was changed to handle JDBC drivers which
		// have problems with the cancel() method being called.
		//Example Driver: JSQLDriver from NetDirect.
		_enableCancel = ds.getEnableCancel();
		if (_selType == SEL_GEN_PROC)
			_sql = generateProc(conn, ds);
		else if (_selType == SEL_PREP_STMT)
			_sql = generatePrepStmt(conn, ds);
		//fc 06/24/04
		else if (_selType == SEL_GEN_PREP_STMT) {
			_proc = generateSelect(ds, criteria, countOnly);
			_sql = generatePrepStmt(conn, ds);
		} else {
			if (_selType == SEL_GEN_SELECT)
				_sql = generateSelect(ds, criteria, countOnly);
			_st = conn.createStatement();
			_res = _st.executeQuery(_sql);
		}
		return true;
	}
	/**
	 * preUpdate method comment.
	 */
	public boolean preUpdate(DataStore ds, DBConnection conn, boolean handleTrans) throws Exception {
		_tables = ds.getTableList(true);
		_sqlb = new StringBuffer(256);
		//fc 06/11/04: Creates the hashtables for One to Many Relationship
		// checks
		//    _htdeletedrowcount = new Hashtable();
		_htinsertedmasterkeydata = new Hashtable();
		if (_tables.length == 0)
			throw new DataStoreException("This datastore cannot be updated because there are either no updateable columns or the table(s) primary keys are not included");
        _dsbTableRelationshipsInserts = ds.getTablesRelationshipDescription(ds);
        _dsbTableRelationshipsDeletes = ds.getTablesRelationshipDescription(ds);
        _dsbTableRelationshipsInserts.sort(DataStoreBuffer.TablesRelationshipDescription.RELATIONSHIPTYPE, DataStoreBuffer.SORT_DES);
        _dsbTableRelationshipsDeletes.sort(DataStoreBuffer.TablesRelationshipDescription.RELATIONSHIPTYPE, DataStoreBuffer.SORT_ASC);

		if (handleTrans)
			conn.beginTransaction();

		return true;
	}
	/**
	 * retrieveRow method comment.
	 */
	public boolean retrieveRow(DataStore ds, DataStoreRow row) throws Exception {
		if (_res == null) {
			MessageLog.writeAssertionMessage("*******Result Set is null, make datastore not threaded.******", this);
			return false;
		}
		if (_res.next()) {
			row.populateFromResultSet(_res);
			return true;
		} else
			return false;
	}

	void setSelType(int iSelType) {
		_selType = iSelType;
	}

	void setParms(StoredProcedureParms p) {
		_parms = p;
	}

	public void setProc(String proc, StoredProcedureParms p) {
		if (p instanceof PreparedStatementParms)
			_selType = SEL_PREP_STMT;
		else
			_selType = SEL_GEN_PROC;
		_proc = proc;
		_parms = p;
	}

	public void setSelect(String sel) {
		_selType = SEL_STATIC;
		_sql = sel;
	}

	public boolean updateRow(DataStore ds, DataStoreRow row, DBConnection conn) throws Exception {
		//fc 06/11/04: Updated to handle One to Many Relationship model updates
		DataStoreBuffer.TablesRelationshipDescription dsb = _dsbTableRelationshipsInserts;
		String sTable;
		for (int i = 0; i < dsb.getRowCount(); i++) {
			sTable = dsb.getString(i, DataStoreBuffer.TablesRelationshipDescription.TABLE);
			int iRelType = dsb.getInt(i, DataStoreBuffer.TablesRelationshipDescription.RELATIONSHIPTYPE);
			_sql = "";
			PreparedStatement p = null;
			try {
				if (iRelType == DataStoreBuffer.RELATION_ONE_TO_MANY) {
					Vector vKey = (Vector) _htinsertedmasterkeydata.get(sTable);
					if (vKey != null) {
						continue;
					} else {
						DataStoreRow firstrow = new DataStoreRow(ds, null, ds.getDescriptor());
						DSDataRow dsdrFirstRow = (DSDataRow) ((Vector) ds.getRows().elementAt(0)).elementAt(0);
						firstrow.setDSDataRow(dsdrFirstRow);
						Vector vFirstRowMasterRowKeyData = getMasterRowKeyData(dsb, firstrow, i);
						if (firstrow.getDSDataRow().getRowStatus() == DataStoreBuffer.STATUS_NOT_MODIFIED) {
							_htinsertedmasterkeydata.put(sTable, vFirstRowMasterRowKeyData);
							ds.copyMasterKeyValuesToRow(dsb, firstrow, row, i);
							continue;
						}
					}
				}
				if (iRelType == DataStoreBuffer.RELATION_MANY_TO_ONE) {
					copyMasterValuesToDetail(dsb, row, i);
					//fc 06/14/04: if the detail row primary key is null then
					// use insert instead of update.
					Vector vKey = getDetailRowPrimaryKey(ds, dsb, row, i);
					boolean bNewRow = false;
					for (int j = 0; j < vKey.size(); j++) {
						if (vKey.elementAt(j).equals("")) {
							bNewRow = true;
							break;
						}
					}
					if (bNewRow)
						p = prepareInsert(ds, row, sTable, conn, _sqlb,null);

				}
				if (p == null)
					p = prepareUpdate(ds, row, sTable, conn, _sqlb);
				_sql = _sqlb.toString();
				if (p != null && p.executeUpdate() == 0)
					throw new DirtyDataException("Operation Failed: The data you are working on is out of date, another user had already updated or deleted the information, please reload the information and perform the action again.");
				if (p != null && iRelType == DataStoreBuffer.RELATION_ONE_TO_MANY) {
					Vector vMasterRowKeyData = getMasterRowKeyData(dsb, row, i);
					Vector vKey = (Vector) _htinsertedmasterkeydata.get(sTable);
					if (vKey == null)
						_htinsertedmasterkeydata.put(sTable, vMasterRowKeyData);
				}
			} finally {
				if (p != null)
					p.close();
			}
		}
		return true;
	}
	private boolean useBind(DSColumnDescriptor ds, DataStore d) {
		if (ds.getUseBind() == DataStore.BIND_DEFAULT)
			return d.getUseBindForUpdate();
		else if (ds.getUseBind() == DataStore.BIND_TRUE)
			return true;
		else
			return false;
	}

	/**
	 * This method takes the SQL INSERT statements that would have been
	 * generated to add these rows to the database, and, rather than executing
	 * them, returns them as a string that can be written to a SQL script file.
	 */

	public String exportAsSQL(DataStore ds, DBConnection conn, String separator) {
		String s = "";
		try {
			PreparedStatement p = null;
			DataStoreRow row = new DataStoreRow(ds, null, ds.getDescriptor());
			Vector rows = (Vector) (ds.getRows().elementAt(0));
			int rowCount = ds.getRowCount();
			String[] tables = ds.getTableList(true);
			StringBuffer sb = new StringBuffer(256);
			for (int rowNo = 0; rowNo < rowCount; rowNo++) {
				row.setDSDataRow((DSDataRow) rows.elementAt(rowNo));
				for (int i = 0; i < tables.length; i++) {
					p = prepareInsert(ds, row, tables[i], conn, sb, null);
					s += fixCRLF(sb.toString(), "||") + separator;
				}
				p.close();
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage(e, "Error in exportAsSQL");
		}
		return s;
	}

	public String fixCRLF(String s, String concatOperator) {
		int i = -1;
		String ss = "' " + concatOperator + " chr(10) " + concatOperator + " chr(13) " + concatOperator + " '";
		int j = ss.length();
		while ((i = s.indexOf("\r\n", i)) >= 0) {
			s = s.substring(0, i) + ss + s.substring(i + 2);
			i = i + j;
		}
		return s;
	}

	/**
	 * Called by SOFIA to find an auto-generated Primary Key after it does an
	 * Insert. Note that you must call setAutoIncrement(pkey_col, true) and
	 * setUpdatable(pkey_col, false) for autogenerated pkeys to work. The
	 * default implementation of this method is null, and must be overridden on
	 * a per-database basis because of the lamentable lack of standardization on
	 * how to retrieve the pkey of an autonumbered row after inserting it.
	 */

	protected void populateAutoIncrementValue(DataStoreRow row, DBConnection conn, int colNo) {
	}

	protected String generatePrepStmt(DBConnection conn, DataStore ds) throws Exception {

		PreparedStatement pst = null;

		pst = conn.prepareStatement(_proc);
		if (_parms != null) {
			for (int i = 0; i < _parms.getParmCount(); i++)
				prepareForType(ds, pst, _parms.getParm(i), _parms.getDataType(i), i + 1);
		}
		_res = pst.executeQuery();
		_st = pst;

		return _proc;
	}

	SimpleDateFormat getDateTimeFormat() {
		return _dateTimeFormat;
	}
	static String formatDateTime(Timestamp t, SimpleDateFormat f, DBConnection conn) {
		String ret = f.format(t);

		if (t.getNanos() == 0)
			return ret;

		String nanosString;
		String zeros = "000000000";

		nanosString = Integer.toString(t.getNanos());

		// Add leading zeros
		nanosString = zeros.substring(0, (9 - nanosString.length())) + nanosString;

		// Truncate trailing zeros
		char[] nanosChar = new char[nanosString.length()];
		nanosString.getChars(0, nanosString.length(), nanosChar, 0);
		int truncIndex = 8;
		while (nanosChar[truncIndex] == '0') {
			truncIndex--;
		}
		nanosString = new String(nanosChar, 0, truncIndex + 1);

		if (conn.getDBMS() == DBConnection.SYBASE_CONNECTION && nanosString.length() > 3)
			nanosString = nanosString.substring(0, 3);

		return ret + "." + nanosString;
	}

	protected String fixDashes(String source) {
		if (!_fixDashes)
			return source;
		if (source == null)
			return source;
		if (source.indexOf("-") == -1)
			return source;
		StringTokenizer st = new StringTokenizer(source, " .,", true);
		StringBuffer sb = new StringBuffer(source.length());
		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			char c = tok.charAt(0);
			if (c == ' ' || c == '.' || c == ',')
				sb.append(c);
			else if (tok.indexOf("-") > -1) {
				sb.append("`");
				sb.append(tok);
				sb.append("`");
			} else
				sb.append(tok);
		}
		return sb.toString();
	}

	private void setInsertBatch(String table, PreparedStatement st) {
		if (_batchedInserts == null)
			_batchedInserts = new Hashtable();
		_batchedInserts.put(table, st);
	}
	private PreparedStatement getInsertBatch(String table) {
		if (_batchedInserts == null)
			return null;
		return (PreparedStatement) _batchedInserts.get(table);

	}
}