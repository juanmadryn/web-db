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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSDataRow.java $
//$Author: Dan $
//$Revision: 50 $
//$Modtime: 11/08/04 10:20a $
/////////////////////////

import java.sql.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.io.InputStream;

import com.salmonllc.util.*;

/**
 * This class should not be used outside the framework. It was made public as an
 * implemetation detail. To access the contents of a row in a DataStore, use the
 * DataStoreRow object.
 */
public class DSDataRow implements java.io.Serializable {
	private Object[] _currentData;
	private Object[] _origData;
	private String[] _tempData;
	private int _tempCount = 0;
	private int _rowStatus = DataStoreBuffer.STATUS_NOT_MODIFIED;
	private boolean _pKeyMod = false;
	private byte _columnStatus[];
	private Object _bean;
	private boolean _bucketsModified = false;
	private transient ResultSetMetaData _rsmd;
	private int _proxyRow = -1;

	public int getProxyRow() {
		return _proxyRow;
	}

	public void setProxyRow(int proxyRow) {
		_proxyRow = proxyRow;
	}

	public DSDataRow(DSDataStoreDescriptor d) {
		_currentData = new Object[d.getColumnCount()];
		_columnStatus = new byte[d.getColumnCount()];
		_rowStatus = DataStoreBuffer.STATUS_NEW;
		if (d.areNullDefaultsDefined()) {
			for (int i = 0; i < d.getColumnCount(); i++)
				_currentData[i] = d.getNullDefault(d.getColumn(i).getType());
		}
	}

	public DSDataRow(DSDataStoreDescriptor d, Object bean) throws Exception {
		this(d);
		populateFromBean(d, bean, DataStoreBuffer.STATUS_MODIFIED);
	}

	public DSDataRow(DSDataStoreDescriptor d, ResultSet r) {
		this(d);
		try {
			_rsmd = r.getMetaData();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("DSDataRow", e, this);
		}
		populateFromResultSet(d, r);
	}

	public void clearOrigData() {
		_origData = null;
	}

	private Object cloneData(Object data) {
		Object retVal = null;

		if (data == null)
			retVal = null;
		else if (data instanceof String)
			retVal = new String((String) data);
		else if (data instanceof Integer)
			retVal = new Integer(((Integer) data).intValue());
		else if (data instanceof Timestamp)
			retVal = Timestamp.valueOf(data.toString());
		else if (data instanceof java.sql.Date)
			retVal = java.sql.Date.valueOf(data.toString());
		else if (data instanceof java.sql.Time)
			retVal = java.sql.Time.valueOf(data.toString());
		else if (data instanceof Double)
			retVal = new Double(((Double) data).doubleValue());
		else if (data instanceof byte[]) {
			retVal = new byte[((byte[]) data).length];
			System.arraycopy(data, 0, retVal, 0, ((byte[]) data).length);
		} else if (data instanceof Short)
			retVal = new Short(((Short) data).shortValue());
		else if (data instanceof Long)
			retVal = new Long(((Long) data).longValue());
		else if (data instanceof Float)
			retVal = new Float(((Float) data).floatValue());

		return retVal;
	}

	private int compareByteArrays(byte[] v1, byte[] v2) {
		int len1 = v1.length;
		int len2 = v2.length;

		for (int i = 0; i < len1 && i < len2; i++) {
			if (v1[i] == v2[i])
				continue;
			else if (v1[i] < v2[i])
				return -1;
			else
				return 1;
		}

		if (len1 == len2)
			return 0;
		else if (len1 < len2)
			return -1;
		else
			return 1;
	}

	public boolean compareExpressions(DataStoreEvaluator dsEval, int row1, int row2, int d) {
		try {
			Object com1 = dsEval.evaluateRow(row1);
			Object com2 = dsEval.evaluateRow(row2);
			boolean dir = (d == DataStoreBuffer.SORT_DES);

			if ((com1 == null) && (com2 == null)) {
				return !dir;
			} else if ((com1 == null) && (com2 != null)) {
				return !dir;
			} else if ((com1 != null) && (com2 == null)) {
				return dir;
			} else if (com1 instanceof String) {
				int comp = ((String) com1).toUpperCase().compareTo(((String) com2).toUpperCase());
				if (comp == 0)
					return !dir;
				else if (comp < 0)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					return !dir;
				else if (v1 < v2)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					return !dir;
				else if (((Timestamp) com1).before((Timestamp) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					return !dir;
				else if (((java.sql.Date) com1).before((java.sql.Date) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					return false;
				else if (((java.sql.Time) com1).before((java.sql.Time) com2))
					return !dir;
				else
					return dir;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean equalExpressions(DataStoreEvaluator dsEval, int row1, int row2) {
		try {
			Object com1 = dsEval.evaluateRow(row1);
			Object com2 = dsEval.evaluateRow(row2);

			if ((com1 == null) && (com2 == null)) {
				return true;
			} else if ((com1 == null) && (com2 != null)) {
				return false;
			} else if ((com1 != null) && (com2 == null)) {
				return false;
			} else if (com1 instanceof String) {
				int comp = ((String) com1).toUpperCase().compareTo(((String) com2).toUpperCase());
				if (comp == 0)
					return true;
				else
					return false;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					return true;
				else
					return false;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					return true;
				else
					return false;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					return true;
				else
					return false;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					return true;
				else
					return false;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Returns true if the d is greater than this row
	 * 
	 * @return boolean
	 * @param d
	 *            com.salmonllc.sql.DSDataRow
	 * @param cols
	 *            int[]
	 * @param dirs
	 *            short[]
	 */
	public boolean compareRows(int row1, int row2, DSDataRow d, Object[] cols, int[] dirs) {
		for (int i = 0; i < cols.length; i++) {
			Object com1;
			Object com2;

			if (cols[i] instanceof DataStoreEvaluator) {
				DataStoreEvaluator de = (DataStoreEvaluator) cols[i];
				try {
					com1 = de.evaluateRow(row1);
				} catch (Exception e) {
					com1 = null;
				}

				try {
					com2 = de.evaluateRow(row2);
				} catch (Exception e) {
					com2 = null;
				}
			} else {
				com1 = getData(((Integer) cols[i]).intValue());
				com2 = d.getData(((Integer) cols[i]).intValue());
			}

			boolean dir = (dirs[i] == DataStoreBuffer.SORT_DES);

			if (com1 == null) {
				if (com2 == null)
					continue;
				else
					return !dir;
			} else if (com2 == null)
				return dir;

			if (com1 instanceof String) {
				int comp = ((String) com1).compareToIgnoreCase(((String) com2));
				if (comp == 0)
					continue;
				else if (comp < 0)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					continue;
				else if (v1 < v2)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					continue;
				else if (((Timestamp) com1).before((Timestamp) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Date) com1).before((java.sql.Date) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Time) com1).before((java.sql.Time) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof byte[]) {
				int comp = compareByteArrays((byte[]) com1, (byte[]) com2);
				if (comp == 0)
					continue;
				else if (comp < 0)
					return !dir;
				else
					return dir;
			}
		}
		return false;
	}

	/**
	 * Returns true if the rows are equals
	 * 
	 * @return boolean
	 * @param d
	 *            com.salmonllc.sql.DSDataRow
	 * @param cols
	 *            int[]
	 */
	public boolean equalRows(int row1, int row2, DSDataRow d, Object[] cols) {
		for (int i = 0; i < cols.length; i++) {
			Object com1;
			Object com2;

			if (cols[i] instanceof DataStoreEvaluator) {
				DataStoreEvaluator de = (DataStoreEvaluator) cols[i];
				try {
					com1 = de.evaluateRow(row1);
				} catch (Exception e) {
					com1 = null;
				}

				try {
					com2 = de.evaluateRow(row2);
				} catch (Exception e) {
					com2 = null;
				}
			} else {
				com1 = getData(((Integer) cols[i]).intValue());
				com2 = d.getData(((Integer) cols[i]).intValue());
			}

			if (com1 == null) {
				if (com2 == null)
					continue;
				else
					return false;
			} else if (com2 == null)
				return false;

			if (com1 instanceof String) {
				int comp = ((String) com1).compareToIgnoreCase(((String) com2));
				if (comp == 0)
					continue;
				else
					return false;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					continue;
				else
					return !false;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					continue;
				else
					return false;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					continue;
				else
					return false;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					continue;
				else
					return false;
			} else if (com1 instanceof byte[]) {
				int comp = compareByteArrays((byte[]) com1, (byte[]) com2);
				if (comp == 0)
					continue;
				else if (comp < 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if the d is greater than this row
	 * 
	 * @return boolean
	 * @param d
	 *            com.salmonllc.sql.DSDataRow
	 * @param cols
	 *            int[]
	 */
	public int compareRows(DSDataRow d, int[] cols) {
		for (int i = 0; i < cols.length; i++) {
			Object com1 = getData(cols[i]);
			Object com2 = d.getData(cols[i]);

			if (com1 == null) {
				if (com2 == null)
					continue;
				else
					return -1;
			} else if (com2 == null)
				return 0;

			if (com1 instanceof String) {
				//fc: 12/17/02 A bug fix where Strings Compare value was just
				// returned, should have been checking for 0.
				int iCompare = ((String) com1).toUpperCase().compareTo(((String) com2).toUpperCase());
				if (iCompare == 0)
					continue;
				return iCompare;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					continue;
				else if (v1 < v2)
					return -1;
				else
					return 1;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					continue;
				else if (((Timestamp) com1).before((Timestamp) com2))
					return -1;
				else
					return 1;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Date) com1).before((java.sql.Date) com2))
					return -1;
				else
					return 1;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Time) com1).before((java.sql.Time) com2))
					return -1;
				else
					return 1;
			} else if (com1 instanceof byte[]) {
				//fc: 12/17/02 A bug fix where byte[] Compare value was just
				// returned, should have been checking for 0.
				int iCompare = compareByteArrays((byte[]) com1, (byte[]) com2);
				if (iCompare == 0)
					continue;
				return iCompare;
			}
		}
		return 0;
	}

	/**
	 * Returns true if the d row is equal to the current row
	 * 
	 * @return boolean
	 * @param d
	 *            com.salmonllc.sql.DSDataRow
	 * @param cols
	 *            int[]
	 */
	public boolean equalRows(DSDataRow d, int[] cols) {
		for (int i = 0; i < cols.length; i++) {
			Object com1 = getData(cols[i]);
			Object com2 = d.getData(cols[i]);

			if (com1 == null) {
				if (com2 == null)
					continue;
				else
					return false;
			} else if (com2 == null)
				return false;

			if (com1 instanceof String) {
				//fc: 12/17/02 A bug fix where Strings Compare value was just
				// returned, should have been checking for 0.
				int iCompare = ((String) com1).toUpperCase().compareTo(((String) com2).toUpperCase());
				if (iCompare == 0)
					continue;
				return false;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					continue;
				else
					return false;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					continue;
				else
					return false;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					continue;
				else
					return false;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					continue;
				else
					return false;
			} else if (com1 instanceof byte[]) {
				//fc: 12/17/02 A bug fix where byte[] Compare value was just
				// returned, should have been checking for 0.
				int iCompare = compareByteArrays((byte[]) com1, (byte[]) com2);
				if (iCompare == 0)
					continue;
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if the d is greater than this row
	 * 
	 * @return boolean
	 * @param d
	 *            com.salmonllc.sql.DSDataRow
	 * @param cols
	 *            int[]
	 * @param dirs
	 *            short[]
	 */
	public boolean compareRows(DSDataRow d, int[] cols, int[] dirs) {
		for (int i = 0; i < cols.length; i++) {
			Object com1 = getData(cols[i]);
			Object com2 = d.getData(cols[i]);
			boolean dir = (dirs[i] == DataStoreBuffer.SORT_DES);

			if (com1 == null) {
				if (com2 == null)
					continue;
				else
					return !dir;
			} else if (com2 == null)
				return dir;

			if (com1 instanceof String) {
				int comp = ((String) com1).compareToIgnoreCase(((String) com2));
				if (comp == 0)
					continue;
				else if (comp < 0)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Number) {
				double v1 = ((Number) com1).doubleValue();
				double v2 = ((Number) com2).doubleValue();
				if (v1 == v2)
					continue;
				else if (v1 < v2)
					return !dir;
				else
					return dir;
			} else if (com1 instanceof Timestamp) {
				if (((Timestamp) com1).equals((Timestamp) com2))
					continue;
				else if (((Timestamp) com1).before((Timestamp) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Date) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Date) com1).before((java.sql.Date) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof java.sql.Time) {
				if (com1.equals(com2))
					continue;
				else if (((java.sql.Time) com1).before((java.sql.Time) com2))
					return !dir;
				else
					return dir;
			} else if (com1 instanceof byte[]) {
				int comp = compareByteArrays((byte[]) com1, (byte[]) com2);
				if (comp == 0)
					continue;
				else if (comp < 0)
					return !dir;
				else
					return dir;
			}
		}
		return false;
	}

	/**
	 * Copy data back to the bean
	 * 
	 * @param d
	 */
	public boolean copyDataToBean(DSDataStoreDescriptor d, boolean resetStatus) throws DataStoreException {
		try {
			return copyBeanData(d, resetStatus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataStoreException("Can not copy data to Bean", getCause(e));
		}
	}
	/**
	 * Copy data back to the bean
	 * 
	 * @param d
	 */

	public boolean copyDataToBean(DSDataStoreDescriptor d) throws DataStoreException {
		try {
			return copyBeanData(d, true);
		} catch (Exception e) {
			throw new DataStoreException("Can not copy data to Bean", getCause(e));
		}
	}
	/**
	 * Utility method to retrieve an error cause.
	 * 
	 * @param e
	 * @return
	 */
	public static Throwable getCause(Exception e) {
		if (e instanceof InvocationTargetException) 
			return Util.getCause(e);
		else
			return e;
	}

	private boolean copyBeanData(DSDataStoreDescriptor d, boolean resetStatus) throws Exception {
		if (_rowStatus == DataStoreBuffer.STATUS_NOT_MODIFIED || _rowStatus == DataStoreBuffer.STATUS_NEW)
			return false;

		boolean retVal = false;
		Object getParms[] = new Object[0];
		Object setParms[] = new Object[1];
		for (int i = 0; i < d.getColumnCount(); i++) {
			int status = getColumnStatus(i);
			if (status == DataStoreBuffer.STATUS_NOT_MODIFIED)
				continue;

			DSColumnDescriptor col = d.getColumn(i);

			if (!col.isUpdateable()) {
				setColumnStatus(i, DataStoreBuffer.STATUS_NOT_MODIFIED);
				continue;
			}

			retVal = true;
			int type = col.getType();
			Method setMethod = col.getSetMethod();
			Object o = _currentData[i];
			Class propertyClass = col.getPropertyClass();

			if (propertyClass == Boolean.TYPE) {
				if (o==null)
					o=new Boolean(false);
				else if (o.equals(d.getBooleanTrueValue()))
					o=new Boolean(true); 	
				else
					o=new Boolean(false);
			} else {
				if (type == DataStoreBuffer.DATATYPE_STRING) {
					if (propertyClass == Character.TYPE) {
						if (o == null)
							o = new Character(' ');
						else {
							String s = (String) o;
							if (s.length() == 0)
								o = new Character(' ');
							else
								o = new Character(s.charAt(0));
						}
					}
				}  else if (o == null && propertyClass.isPrimitive()) {
					if (type == DataStoreBuffer.DATATYPE_INT)
						o = new Integer(0);
					else if (type == DataStoreBuffer.DATATYPE_DOUBLE)
						o = new Double(0);
					else if (type == DataStoreBuffer.DATATYPE_FLOAT)
						o = new Float(0);
					else if (type == DataStoreBuffer.DATATYPE_LONG)
						o = new Long(0);
					else if (type == DataStoreBuffer.DATATYPE_SHORT)
						o = new Short((short)0);
				} else if (Util.instanceOf(BigDecimal.class, propertyClass)) {
					if (o != null)
						o = new BigDecimal(((Double) o).doubleValue());
				}
			}
			setParms[0] = o;
			Object bean=getBean(_bean, col.getParentGetMethods(), getParms, col, o != null);
			if (bean != null)
				setMethod.invoke(bean, setParms);
			if (resetStatus)
				setColumnStatus(i, DataStoreBuffer.STATUS_NOT_MODIFIED);
		}
		return retVal;
	}

	public void undoChanges() {
		if (_origData != null) {
			_currentData = new Object[_origData.length];
			for (int i = 0; i < _origData.length; i++)
				_currentData[i] = cloneData(_origData[i]);
		}
		resetStatus();
	}

	public void copyDataToOrig() {
		if (_origData == null)
			_origData = new Object[_currentData.length];

		for (int i = 0; i < _currentData.length; i++)
			_origData[i] = cloneData(_currentData[i]);

	}

	public Object getBean() {
		return _bean;
	}

	public int getColumnStatus(int columnNo) {
		if (columnNo >= _columnStatus.length)
			return DataStoreBuffer.STATUS_NOT_MODIFIED;

		return _columnStatus[columnNo];
	}

	public Object getData(int columnNo) {
		if (columnNo < 0)
			return null;

		if (columnNo >= _currentData.length) {
			Object[] temp = new Object[columnNo + 1];
			System.arraycopy(_currentData, 0, temp, 0, _currentData.length);
			_currentData = temp;
		}

		return _currentData[columnNo];
	}

	public Object getOrigData(int columnNo) {
		if (columnNo < 0)
			return null;

		if (columnNo >= _origData.length) {
			Object[] temp = new Object[columnNo + 1];
			System.arraycopy(_origData, 0, temp, 0, _origData.length);
			_origData = temp;
		}

		if (_origData == null)
			return null;
		else if (columnNo >= _origData.length)
			return null;
		else
			return _origData[columnNo];

	}

	public int getRowStatus() {
		return _rowStatus;
	}

	public boolean getBucketsModified() {
		return _bucketsModified;
	}

	public void setBucketsModified(boolean modified) {
		_bucketsModified = modified;
	}

	public boolean isPrimaryKeyModified() {
		return _pKeyMod;
	}

	public void populateFromBean(DSDataStoreDescriptor d, Object bean, int initialRowStatus) throws DataStoreException {
		populateFromBean(d,bean,initialRowStatus, null);
	}	
	
	public void populateFromBean(DSDataStoreDescriptor d, Object bean, int initialRowStatus, String prefix) throws DataStoreException {
		Object getParms[] = new Object[0];
		for (int i = 0; i < d.getColumnCount(); i++) {
			try {
				DSColumnDescriptor col = d.getColumn(i);
				if (col.getGetMethod() == null) {
					continue;
				}	
				if (prefix != null) {
					if (! col.getInternalName().startsWith(prefix)) 
						continue;
				}	
				int type = col.getType();
				Method getMethod = col.getGetMethod();
				Object beanToInvoke = getBean(bean, col.getParentGetMethods(), getParms, col, false);
				if (beanToInvoke == null)
					continue;
				Object o = getMethod.invoke(beanToInvoke, getParms);
				if (o instanceof Boolean) {
					if (o != null)
						o = ((Boolean) o).booleanValue() ? d.getBooleanTrueValue() : d.getBooleanFalseValue();
					else
						o = d.getBooleanFalseValue();
					_currentData[i]=o;
				} else if (type == DataStoreBuffer.DATATYPE_STRING) {
					if (o != null) {
						o = o.toString();
						if (d.getTrimStrings())
							o = ((String) o).trim();
					}
					_currentData[i] = o;
				} else
					_currentData[i] = o;
				_columnStatus[i] = (byte)DataStoreBuffer.STATUS_NOT_MODIFIED;	
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataStoreException(e.getMessage(), -1, d.getColumn(i).getColumn());
			}
		}

		_bean = bean;
		_rowStatus = initialRowStatus;
	}

	private Object getBean(Object bean, Method parentGetters[], Object getParms[], DSColumnDescriptor columnDesc, boolean setValues) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object ret = bean;
		Object topNotNull = bean;
		if (ret instanceof Object[]) {
			Object[] work = (Object[]) ret;
			BeanDataStore.BeanClassInfo inf = columnDesc.getBeanInfo();
			ret = work[inf.getIndex()];
		}
		for (int i = 0; i < parentGetters.length; i++) {
			topNotNull = ret;
			ret = parentGetters[i].invoke(ret, getParms);
			if (ret == null) {
				if (setValues) {
					Class c = parentGetters[i].getReturnType();
					try {
						ret = c.newInstance();
						String setMethodName = parentGetters[i].getName();
						if (setMethodName.startsWith("get"))
							setMethodName = "set" + setMethodName.substring(3);
						else
							setMethodName = "set" + setMethodName.substring(2);
						Class types[] = {c};
						Object parms[] = {ret};
						Method setMethod = topNotNull.getClass().getMethod(setMethodName, types);
						setMethod.invoke(topNotNull, parms);

					} catch (Exception e) {
						return null;
					}
				} else
					return null;
			}
		}
		return ret;

	}
	public void populateFromResultSet(DSDataStoreDescriptor d, ResultSet r) {
		int rsRow = 0;
		try {
			if (_rsmd == null)
				_rsmd = r.getMetaData();
			for (int i = 0; i < d.getColumnCount(); i++) {
				DSColumnDescriptor col = d.getColumn(i);
				if (col.getColumn() == null) {
					_currentData[i] = null;
				} else {
					rsRow++;
					int type = col.getType();
					Object o = r.getObject(rsRow);
					switch (type) {
						case DataStoreBuffer.DATATYPE_STRING :
							if (o instanceof byte[]) {
								String sDataType = _rsmd.getColumnTypeName(rsRow);
								if (sDataType.equals("TEXT")) {
									InputStream is = r.getUnicodeStream(rsRow);
									if (is != null) {
										byte[] baData = new byte[is.available()];
										is.read(baData);
										is.close();
										o = new String(baData);
									} else
										o = null;
								} else {
									// MySql special case in aggregate functions
									o = r.getString(rsRow);
								}
							}
							if (d.getTrimStrings() && o != null)
								o = o.toString().trim();
							_currentData[i] = o;
							break;
						case DataStoreBuffer.DATATYPE_DATETIME :
							// MySql special case in aggregate functions
							if (o instanceof byte[])
								o = r.getDate(rsRow);
							_currentData[i] = o;
						case DataStoreBuffer.DATATYPE_BYTEARRAY :
							_currentData[i] = o;
							break;
						case DataStoreBuffer.DATATYPE_DATE :
							// MySql special case in aggregate functions
							if (o instanceof byte[])
								o = r.getDate(rsRow);
							_currentData[i] = o;
							break;
						case DataStoreBuffer.DATATYPE_TIME :
							// MySql special case in aggregate functions
							if (o instanceof byte[])
								o = r.getTimestamp(rsRow);
							_currentData[i] = o;
							break;
						case DataStoreBuffer.DATATYPE_INT :
							if (o == null)
								_currentData[i] = null;
							else
								_currentData[i] = new Integer(((Number) o).intValue());
							break;
						case DataStoreBuffer.DATATYPE_DOUBLE :
							if (o == null)
								_currentData[i] = null;
							else
								_currentData[i] = new Double(((Number) o).doubleValue());
							break;
						case DataStoreBuffer.DATATYPE_SHORT :
							if (o == null) {
								_currentData[i] = null;
							} else {
								if (o instanceof Boolean) {
									if (((Boolean) o).booleanValue()) {
										_currentData[i] = new Short((short) 1);
									} else {
										_currentData[i] = new Short((short) 0);
									}
								} else {
									_currentData[i] = new Short(((Number) o).shortValue());
								}
							}
							break;
						case DataStoreBuffer.DATATYPE_LONG :
							if (o == null)
								_currentData[i] = null;
							else
								_currentData[i] = new Long(((Number) o).longValue());
							break;
						case DataStoreBuffer.DATATYPE_FLOAT :
							if (o == null)
								_currentData[i] = null;
							else
								_currentData[i] = new Float(((Number) o).floatValue());
							break;
						default :
							if (o instanceof byte[] && ((byte[]) o).length == 4) {
								String sDataType = _rsmd.getColumnTypeName(rsRow);
								if (sDataType.equals("TEXT")) {
									InputStream is = r.getUnicodeStream(rsRow);
									if (is != null) {
										byte[] baData = new byte[is.available()];
										is.read(baData);
										is.close();
										o = new String(baData);
									} else
										o = null;
								}
							}
							_currentData[i] = o;
							break;
					}

				}
			}
		} catch (Exception e) {
			System.err.println("DSDataRow.populateFromResultSet:" + e);
			MessageLog.writeErrorMessage("DSDataRow.populateFromResultSet:", e, this);
		}
		_rowStatus = DataStoreBuffer.STATUS_NOT_MODIFIED;

	}

	public void resetStatus() {
		setRowStatus(DataStoreBuffer.STATUS_NOT_MODIFIED);
		_bucketsModified = false;
		for (int i = 0; i < _columnStatus.length; i++)
			_columnStatus[i] = (byte) DataStoreBuffer.STATUS_NOT_MODIFIED;
	}

	public void setColumnStatus(int columnNo, int status) {
		if (columnNo >= _columnStatus.length) {
			byte[] temp = new byte[columnNo + 1];
			System.arraycopy(_columnStatus, 0, temp, 0, _columnStatus.length);
			_columnStatus = temp;
		}

		_columnStatus[columnNo] = (byte) status;

	}

	public void setRowStatus(int status) {

		if (_rowStatus == DataStoreBuffer.STATUS_NOT_MODIFIED && status == DataStoreBuffer.STATUS_MODIFIED) {
			if (_origData == null) {
				_origData = new Object[_currentData.length];
				for (int i = 0; i < _currentData.length; i++) {
					_origData[i] = cloneData(_currentData[i]);
				}
			}
		}
		_rowStatus = status;
	}

	public void setTempValue(int columnNo, String value, DSDataStoreDescriptor d) {
		if (columnNo < 0)
			return;
		if (value == null && _tempCount == 0)
			return;
		if (value == null && columnNo >= _tempData.length)
			return;

		if (_tempData == null)
			_tempData = new String[columnNo + 1];
		else if (columnNo >= _tempData.length) {
			String[] temp = new String[columnNo + 1];
			System.arraycopy(_tempData, 0, temp, 0, _tempData.length);
			_tempData = temp;
		}
		if (value == null && _tempData[columnNo] != null)
			_tempCount--;
		else if (value != null && _tempData[columnNo] == null)
			_tempCount++;
		_tempData[columnNo] = value;
		updateStatus(d.getColumn(columnNo), columnNo);
	}

	public String getTempValue(int columnNo) {
		if (_tempData == null)
			return null;

		if (columnNo < 0)
			return null;

		if (columnNo >= _tempData.length)
			return null;

		return _tempData[columnNo];
	}

	public void clearTempValue(int columnNo, DSDataStoreDescriptor desc) {
		setTempValue(columnNo, null, desc);
	}

	public void clearTempValues() {
		_tempCount = 0;
		_tempData = null;
	}

	public boolean hasTempValues() {
		return _tempCount > 0;
	}

	public void setValue(int columnNo, Object value, DSDataStoreDescriptor d) {
		if (columnNo < 0)
			return;

		if (columnNo >= _currentData.length) {
			Object[] temp = new Object[columnNo + 1];
			System.arraycopy(_currentData, 0, temp, 0, _currentData.length);
			_currentData = temp;
		}

		DSColumnDescriptor c = d.getColumn(columnNo);
		updateStatus(c, columnNo);

		if (value == null)
			value = d.getNullDefault(d.getColumn(columnNo).getType());

		_currentData[columnNo] = value;
		clearTempValue(columnNo, d);

	}

	private void updateStatus(DSColumnDescriptor c, int columnNo) {
		if (c.getTable() != null || c.getColumn() != null) {
			if (_rowStatus == DataStoreBuffer.STATUS_NOT_MODIFIED) {
				if (_origData == null) {
					_origData = new Object[_currentData.length];
					for (int i = 0; i < _currentData.length; i++) {
						_origData[i] = cloneData(_currentData[i]);
					}
				} else if (columnNo >= _origData.length) {
					Object[] temp = new Object[columnNo];
					System.arraycopy(_origData, 0, temp, 0, _origData.length);
					_origData = temp;
					for (int i = 0; i < _currentData.length; i++) {
						_origData[i] = cloneData(_currentData[i]);
					}
				}
				_rowStatus = DataStoreBuffer.STATUS_MODIFIED;
			} else if (_rowStatus == DataStoreBuffer.STATUS_NEW) {
				_rowStatus = DataStoreBuffer.STATUS_NEW_MODIFIED;
			}
			setColumnStatus(columnNo, DataStoreBuffer.STATUS_MODIFIED);

			if (c.isPrimaryKey())
				_pKeyMod = true;
		} else {
			_bucketsModified = true;
			if (_bean != null) {
				if (_rowStatus == DataStoreBuffer.STATUS_NEW)
					_rowStatus = DataStoreBuffer.STATUS_NEW_MODIFIED;
				else if (_rowStatus != DataStoreBuffer.STATUS_NEW_MODIFIED)
					_rowStatus = DataStoreBuffer.STATUS_MODIFIED;
				setColumnStatus(columnNo, DataStoreBuffer.STATUS_MODIFIED);
			}
		}

	}
	/**
	 * Returns a string representation of the data in the row
	 */
	public String toString() {
		StringBuffer sBuff = new StringBuffer("[");
		int compSize = _currentData.length;

		boolean commaFlag = false;

		for (int i = 0; i < compSize; i++) {

			if (commaFlag == true) {
				sBuff.append(",");
			}

			if (Util.isNull(_currentData[i])) {
				//break;
				sBuff.append("nullval");
				continue;
			}

			if (Util.isEmpty(_currentData[i].toString())) {
				sBuff.append("EMPTY_DATA");
			} else {
				sBuff.append(_currentData[i].toString());
				commaFlag = true;

			}

		}
		sBuff.append("]");
		return sBuff.toString();

	}

	public void copyTo(DSDataRow target, DSDataStoreDescriptor sourceDescriptor, DSDataStoreDescriptor targetDescriptor) {
		for (int i = 0; i < sourceDescriptor.getColumnCount(); i++) {
			DSColumnDescriptor sourceCol = sourceDescriptor.getColumn(i);
			for (int j = 0; j < targetDescriptor.getColumnCount(); j++) {
				DSColumnDescriptor targetCol = targetDescriptor.getColumn(j);
				if (sourceCol.getType() != targetCol.getType())
					continue;

				String sourceTable = sourceCol.getTable();
				String targetTable = targetCol.getTable();
				if (sourceTable == null && targetTable != null)
					continue;
				if (targetTable == null && sourceTable != null)
					continue;
				if (!sourceTable.equals(targetTable))
					continue;

				String sourceColName = sourceCol.getColumn();
				String targetColName = targetCol.getColumn();

				if (sourceColName == null && targetColName != null)
					continue;
				if (targetColName == null && sourceColName != null)
					continue;
				if (!sourceColName.equals(targetColName))
					continue;

				//table, column and datatype match so copy the data from the
				// source to the target
				Object o = getData(i);
				target.setValue(j, o, targetDescriptor);

			}
		}
	}

	protected Object[] getCurrentData() {
		return _currentData;
	}
}