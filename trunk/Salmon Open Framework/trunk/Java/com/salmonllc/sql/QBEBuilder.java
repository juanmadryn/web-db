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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class is used to to build Query By Example filters for datastore retrieves. Selection criteria buckets are added to the datastore using the add criteria method. These buckets can be bound to GUI components which allow the user to enter selection criteria into the builder. Then the builder combines the entered criteria with bucket definition to generate a SQL filter or DataStoreExpression filter to filter the data.
 */
public class QBEBuilder extends DataStoreBuffer {
	public static final int CRITERIA_TYPE_EQUALS_IGNORE_CASE = 101;
	public static final int CRITERIA_TYPE_STARTS_WITH = 102;
	public static final int CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE = 103;
	public static final int CRITERIA_TYPE_CONTAINS = 104;
	public static final int CRITERIA_TYPE_CONTAINS_IGNORE_CASE = 105;

	static final int CRITERIA_STRING_ONLY = 100;

	public static final int CRITERIA_TYPE_LTE = 1;
	public static final int CRITERIA_TYPE_LT = 2;
	public static final int CRITERIA_TYPE_GTE = 3;
	public static final int CRITERIA_TYPE_GT = 4;
	public static final int CRITERIA_TYPE_EQUALS = 5;
	public static final int CRITERIA_TYPE_NOT_EQUALS = 6;
	public static final int CRITERIA_TYPE_IN = 7;
	public static final int CRITERIA_TYPE_CUSTOM = 9;
	public static final int CRITERIA_TYPE_COMPLEX = 99;
	public String _stopWords[] = null;

	public class CriteriaElement implements Serializable {
		public String bucketName;
		public int type;
		public String columnList;
	}

	private ArrayList _criteriaElements = new ArrayList();
	private boolean _useOr = false;
	private Vector _qbeListeners;
	HashMap _tables = null;
	DataStoreQBEInterface _lastDSI;

	/**
	 * Adds a selection criteria bucket to the builder
	 * @param bucketName The name of the bucket that will hold the user entered selection criteria
	 * @param criteriaType The type of criteria to use with this bucket. Valid values are one of the CRITERIA_TYPE constants described in this class.
	 * @param columnList A comma separated list of database column names (for SQL filters) or datatable internal names (for datastore filters). Each column in the list will be compared with the filter value in the generated filter. Wild cards are allowed including: *: all columns in all tables in the DataStore or for datastore filters, all columns in the datastore and  tablename.*	: all columns in the specified table
	 */
	public void addCriteria(String bucketName, int criteriaType, String columnList) {
		CriteriaElement ele = new CriteriaElement();
		ele.bucketName = bucketName;
		ele.type = criteriaType;
		ele.columnList = columnList;
		_criteriaElements.add(ele);
		addBucket(bucketName, DATATYPE_STRING);
		_tables=null;
		reset();
	}

	/**
	 * Blanks out all criteria in the builder
	 */
	public void reset() {
		super.reset();
		super.insertRow();
	}

	/**
	 * @returns true if criteria elements should be combined with or instead of and
	 */
	public boolean getUseOr() {
		return _useOr;
	}

	/**
	 * @set to true if criteria elements should be combined with or instead of and
	 */
	public void setUseOr(boolean b) {
		_useOr = b;
	}

	/**
	  * Builds a datastore filter expression by combining the selection criteria rules with the values in the DataStore and then runs the filter on the DataStore.
	  * @param dsb The DataStore to run the filter against.
	  */
	public void filter(DataStoreBuffer dsb) throws DataStoreException {
		String st = generateDataStoreFilter(dsb);
		dsb.filter(st);
	}
	
	/**
	  * Builds a datastore SQL where clause by combining the selection criteria rules with the values in the DataStore and then uses that to retreive the data into the passed DataStore
	  * @param dsb The DataStore to load the data for.(Note: this method takes a DataStoreBuffer so subclasses can override it for different types of DataStoreBuffer. This implementation will only work with a DataStore or ProxyDataStore).
	  */
	public void retrieve(DataStoreBuffer dsb) throws SQLException, DataStoreException {
		if (! (dsb instanceof DataStoreQBEInterface)) 
			return;
		DataStoreQBEInterface dsi = (DataStoreQBEInterface) dsb;
		String st = generateSQLFilter(dsb);
		if (st != null && st.length() == 0)
			st = null;
		dsi.retrieve(st);	
	}	
	
	/**
	  * Estimates the number of rows to retrieve for the passed datastore using the criteria and rules in the component
	  * @param dsb The DataStore to estimate the retrieve for.(Note: this method takes a DataStoreBuffer so subclasses can override it for different types of DataStoreBuffer. This implementation will only work with a DataStore or ProxyDataStore).
	  */
	public int estimateRowsRetrieved(DataStoreBuffer dsb) throws Exception {
		if (! (dsb instanceof DataStoreQBEInterface)) 
			return 0;
		DataStoreQBEInterface dsi = (DataStoreQBEInterface) dsb;
		String st = generateSQLFilter(dsb);
		return dsi.estimateRowsRetrieved(st);	
	}
	/**
	 * Builds a datastore filter expression string by combining the selection criteria rules with the values in the DataStore
	 * @param dsb The DataStore to run the filter against.
	 */
	public String generateDataStoreFilter(DataStoreBuffer dsb) {
		StringBuffer filter = new StringBuffer(255);
		String eleFilter = null;
		for (int i = 0; i < _criteriaElements.size(); i++) {
			String criteriaEntered = null;
			CriteriaElement ele = (CriteriaElement) _criteriaElements.get(i);
			try {
				criteriaEntered = getString(ele.bucketName);
				String cols[] = getDataStoreColumnNamesFromList(dsb, ele.columnList);
				eleFilter = QBECriteriaBuilder.buildFilter(dsb, criteriaEntered, ele.type, cols, _stopWords);

			} catch (DataStoreException e) {
			}
			if (eleFilter != null) {
				if (filter.length() > 0)
					filter.append(_useOr ? " || " : " && ");
				filter.append("(");
				filter.append(eleFilter);
				filter.append(")");
			}
		}
		String ret = null;
		if (filter.length() == 0 || filter.toString().equals("()"))
			ret = null;
		else	
			ret = filter.toString();
				
		QBEEvent evt = new QBEEvent(this,QBEEvent.TYPE_FILTER_PREVIEW,ret);
		notifyListeners(evt);
		return evt.getFilter();
	}

	/**
	 * Builds a SQL where clause string by combining the selection criteria rules with the values in the DataStoreBuffer. 
	 * @param dsb The DataStoreBuffer to run the filter against (Note: this method takes a DataStoreBuffer so subclasses can override it for different types of DataStore. This implementation will only work with a DataStore or ProxyDataStore).
	 */
	public String generateSQLFilter(DataStoreBuffer dsb) {
		if (! (dsb instanceof DataStoreQBEInterface))
			return null;
		DataStoreQBEInterface dsi = (DataStoreQBEInterface) dsb;

		StringBuffer filter = new StringBuffer(255);
		String eleFilter = null;
		for (int i = 0; i < _criteriaElements.size(); i++) {
			String criteriaEntered = null;
			CriteriaElement ele = (CriteriaElement) _criteriaElements.get(i);
			try {
				criteriaEntered = getString(ele.bucketName);
				ColumnDefinition cols[] = getTableColumnNamesFromList(dsi, ele.columnList);
				eleFilter = QBECriteriaBuilder.buildSQL(dsi, criteriaEntered, ele.type, cols, _stopWords);

			} catch (DataStoreException e) {
			}
			if (eleFilter != null && eleFilter.length() > 0) {
				if (filter.length() > 0)
					filter.append(_useOr ? " or " : " and ");
				filter.append("(");
				filter.append(eleFilter);
				filter.append(")");
			}
		}
		
		String ret = null;
		if (filter.length() == 0 || filter.toString().equals("()"))
			ret = null;
		else	
			ret = filter.toString();
				
		QBEEvent evt = new QBEEvent(this,QBEEvent.TYPE_SQL_PREVIEW,ret);
		notifyListeners(evt);
		return evt.getFilter();
	}
		
	private ColumnDefinition[] getTableColumnNamesFromList(DataStoreQBEInterface dsi, String colList) throws DataStoreException {
		HashMap columns = new HashMap();
		if (_tables == null) 
			_tables = new HashMap();
		else {
			if (_lastDSI != null) {
				if (_lastDSI != dsi)
					_tables.clear();
				else if (dsi.getAliasCount() != _lastDSI.getAliasCount())
					_tables.clear();
			}		
		}	
		_lastDSI = dsi;
		StringTokenizer st = new StringTokenizer(colList, ",");
		while (st.hasMoreTokens()) {
			String tok = st.nextToken().trim();
			int dotPos = tok.lastIndexOf(".");
			if (dotPos == -1) {
				if (tok.equals("*")) {
					int count = dsi.getColumnCount();
					for (int i = 0; i < count; i++) {
						String tableName = dsi.getColumnTableName(i);
						if (tableName == null)
							continue;
							
						if (!_tables.containsKey(tableName)) {
							ColumnDefinition[] c = getColumnsForTable(dsi,tableName);
							_tables.put(tableName, c);
						}
						
						ColumnDefinition[] c = (ColumnDefinition[]) _tables.get(tableName);	
						for (int j = 0; j < c.length; j++) {
							String key = c[j].getTableName() + "." + c[j].getColumnName();
							columns.put(key, c[j]);
						}
					}
				} else {
					int ndx = dsi.getColumnIndex(tok);
					if (ndx != -1) {
						String tableName = dsi.getColumnTableName(ndx);
						String databaseName = dsi.getColumnDatabaseName(ndx);
						String columnName = dsi.getColumnDatabaseName(ndx);
						if (tableName != null && databaseName != null) {
							if (!_tables.containsKey(tableName))
								_tables.put(tableName, getColumnsForTable(dsi,tableName));
							ColumnDefinition[] c = (ColumnDefinition[]) _tables.get(tableName);
							for (int i = 0; i < c.length; i++) {
								if (c[i].getColumnName().equals(columnName))
									columns.put(databaseName, c[i]);
							}
						}
					}
				}
			} else {
				String tableName = tok.substring(0, dotPos);
				String columnName = tok.substring(dotPos + 1);
					
				if (!_tables.containsKey(tableName))
					_tables.put(tableName, getColumnsForTable(dsi,tableName));

				ColumnDefinition[] c = (ColumnDefinition[]) _tables.get(tableName);
				if (columnName.equals("*")) {
					for (int i = 0; i < c.length; i++) {
						String key = c[i].getTableName() + "." + c[i].getColumnName();
						columns.put(key, c[i]);
					}
				} else {
					for (int i = 0; i < c.length; i++) {
						if (c[i].getColumnName().equalsIgnoreCase(columnName))
							columns.put(c[i].getTableName() + "." + c[i].getColumnName(), c[i]);
					}
				}
			}
		}

		ColumnDefinition ret[] = new ColumnDefinition[columns.size()];
		columns.values().toArray(ret);
		return ret;
	}

	private ColumnDefinition[] getColumnsForTable(DataStoreQBEInterface dsi, String table) throws DataStoreException {
		String realTable = getTableFromAlias(dsi,table);
		ColumnDefinition ret[] = dsi.getColumnsForTable(realTable);
		if (ret != null && ! realTable.equals(table)) {
			for (int i = 0; i < ret.length;i++)
				ret[i].setTableName(table);				
		}	
		return ret;
	}	
	private String getTableFromAlias(DataStoreQBEInterface dsi, String alias) throws DataStoreException {
		for (int i = 0; i < dsi.getAliasCount(); i++) {
			if (dsi.getAlias(i) != null && dsi.getAlias(i).equals(alias))
				return dsi.getTable(i);	
		}		
		return alias;
	}	
	private String[] getDataStoreColumnNamesFromList(DataStoreBuffer dsb, String colList) {
		ArrayList l = new ArrayList();
		StringTokenizer tok = new StringTokenizer(colList, ",");
		while (tok.hasMoreTokens()) {
			String ele = tok.nextToken().trim();
			if (ele.equals("*"))
				return dsb.getColumnList();
			else if (dsb.getColumnIndex(ele) != -1)
				l.add(ele);
			else {
				int pos = ele.indexOf(".*");
				if (pos > -1) {
					String table = ele.substring(0, pos);
					DSDataStoreDescriptor desc = getDescriptor();
					for (int i = 0; i < desc.getColumnCount(); i++) {
						DSColumnDescriptor col = desc.getColumn(i);
						if (col.getTable() != null && col.getTable().equals(table))
							try {
								l.add(getColumnName(i));
							} catch (DataStoreException e) {
							}
					}
				}
			}
		}

		String ret[] = new String[l.size()];
		l.toArray(ret);
		return ret;

	}

	/**
	 * Returns an array of all the criteria elements in the QBEBuilder
	 */
	public CriteriaElement[] getCriteriaElements() {
		CriteriaElement el[] = new CriteriaElement[_criteriaElements.size()];
		_criteriaElements.toArray(el);
		return el;
	}
	
	/**
	 * Adds a new listerner to this QBEBuilder that will be notified whenever a SQL or DataStore filter is generated
	 */
	public void addQBEListener(QBEListener l) {
		if (_qbeListeners == null)
			_qbeListeners = new Vector();

		for (int i = 0; i < _qbeListeners.size(); i++) {
			if (((ModelChangedListener) _qbeListeners.elementAt(i)) == l)
				return;
		}

		_qbeListeners.addElement(l);
	}

	/**
	* This method removes a listener from the list of listeners that will be notified when a model changed event is fired.
	*/
	public void removeQBEListener(QBEListener l) {
		if (_qbeListeners == null)
			return;

		for (int i = 0; i < _qbeListeners.size(); i++) {
			if (((QBEListener) _qbeListeners.elementAt(i)) == l) {
				_qbeListeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * Notifies all listeners that a model changed event occurred
	 */
	public void notifyListeners(QBEEvent e) {
		if (_qbeListeners == null)
			return;

		for (int i = 0; i < _qbeListeners.size(); i++) {
			((QBEListener) _qbeListeners.elementAt(i)).QBEPreview(e);
		}
	}

	/**
	 * @return The list of stop words used for building complex criteria. Stop words are words that are ignored when buildinng the criteria. Set to null to use the default list or an empty array to not use stop words.
	 */
	public String[] getStopWords() {
		return _stopWords;
	}

	/**
	 * Sets the list of stop words used for building complex criteria. Stop words are words that are ignored when building the criteria. Set to null to use the default list or an empty array to not use stop words.
	 */
	public void setStopWords(String[] stopWords) {
		_stopWords = stopWords;
	}
	


}
