package com.salmonllc.sql;

import com.salmonllc.xml.*;
/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreBuffer.java $
//$Author: Dan $
//$Revision: 87 $
//$Modtime: 11/08/04 10:08a $
/////////////////////////

import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.*;

import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.util.*;

/**
 * This class provides a storage buffer for data in the form of rows and column.
 */
public class DataStoreBuffer implements Serializable {

	public static final int DATATYPE_STRING = 0;
	public static final int DATATYPE_INT = 1;
	public static final int DATATYPE_DATETIME = 2;
	public static final int DATATYPE_DOUBLE = 3;
	public static final int DATATYPE_BYTEARRAY = 4;
	public static final int DATATYPE_SHORT = 5;
	public static final int DATATYPE_LONG = 6;
	public static final int DATATYPE_FLOAT = 7;
	public static final int DATATYPE_DATE = 8;
	public static final int DATATYPE_TIME = 9;
	protected static final int DATATYPE_ANY = 99;

	public static final int SORT_ASC = 0;
	public static final int SORT_DES = 1;

	public static final int EXPORT_XML = 0;
	public static final int EXPORT_HTML = 1;
	public static final int EXPORT_TAB_DELIMITED = 2;

	public static final int STATUS_NOT_MODIFIED = 0;
	public static final int STATUS_MODIFIED = 1;
	public static final int STATUS_NEW = 2;
	public static final int STATUS_NEW_MODIFIED = 3;

	public static final int BUFFER_STANDARD = 0;
	public static final int BUFFER_DELETED = 1;
	public static final int BUFFER_FILTERED = 2;

	public static final int BIND_DEFAULT = 0;
	public static final int BIND_TRUE = 1;
	public static final int BIND_FALSE = 2;

    //fc 06/11/04: Added these Constants to be used in the One to Many Relationship Models (Master/Detail)
    public static final int RELATION_ONE_TO_ONE=1;
    public static final int RELATION_MANY_TO_ONE=2;
    public static final int RELATION_ONE_TO_MANY=3;


	protected DSDataStoreDescriptor _desc = new DSDataStoreDescriptor();
	protected Vector _rows = new Vector();
	protected Vector _deletedRows = new Vector();
	protected Vector _filteredRows = new Vector();
	protected boolean _retrieveInProgress, _cancelInProgress;
	protected Vector _waitingRetrieveThreads = new Vector();
	protected Vector _waitingCancelThreads = new Vector();

	private int _currentRow = -1;
	private DataStoreEvaluator _find;

	SimpleDateFormat _dateTimeFormatVSE = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
	SimpleDateFormat _dateTimeFormatSTD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final int AUTORETRIEVE_NEVER = 0;
	public static final int AUTORETRIEVE_ALWAYS = 1;
	public static final int AUTORETRIEVE_ONCHANGE = 2;

	private boolean _autoBind = false;
	private int _autoRetrieve = AUTORETRIEVE_NEVER;
	private AutoRetrieveCriteria _crit;

	protected static final boolean debug = false;
	private String _remoteID;
	private String _remoteUpdateRetVal;
	private Vector _modelListeners;
	private LanguagePreferences _lang;
    //fc 06/14/04: indicates whether master row should be deleted on update for a one to many relationship. Default is true.
    private boolean _allowMasterRowDelete=true;
    //fc 06/11/04: indicates whether model is a one to many relationship model.
    protected boolean _manytoonerelationship=false;

    // stack filter criteria, to recover used criteria's / Added by Juan Manuel Cortez (G2P), 09/12/2007
    private Stack<String> _criteriasStack;


	/**
	 * Creates a new empty DataStore object. Any method requiring a database connection (retrieve,update and union) will have to be explicitly passed a DBConnection created outside the datastore.
	 */
	public DataStoreBuffer() {
		super();
	}
	/**
	 * This method adds a bucket to the DataStoreBuffer. The bucket will be filled in with nulls when the datastore is retrieved.
	 * @param internalname The name of the bucket to add to the datastore.
	 * @param type The type of the column to add to the datastore. This must be one of the "TYPE" constants in the class.
	 */
	public void addBucket(String internalname, int type) {
		_desc.addColumn(null, null, type, false, false, internalname);
	}
	/**
	 * This method adds a bucket to the DataStoreBuffer. The bucket will be filled in with nulls when the datastore is retrieved.
	 * @param internalname The name of the bucket to add to the datastore.
	 * @param type The type of the column to add to the datastore. This must be one of the "TYPE" constants in the class.
	 */
	public void addBucket(String internalname, int type, String format) {
		try {

			addBucket(internalname, type);
			setFormat(internalname, format);
		} catch (DataStoreException de) {
			MessageLog.writeErrorMessage("addBucket(String internalname, int type, String format)", de, this);
		}
	}
	/**
	 * This method will append the rows in the DataStore. The vector to append should be created via the getRows method of this datastore.
	 */
	public void appendRows(Vector rows) {
		Vector rowTemp = (Vector) rows.elementAt(0);
		int rowSize = rowTemp.size();
		// transfer rows
		for (int i = 0; i < rowSize; i++) {
			_rows.addElement(rowTemp.elementAt(i));
		}
		rowTemp = (Vector) rows.elementAt(1);
		rowSize = rowTemp.size();
		// transfer filter rows
		for (int i = 0; i < rowSize; i++) {
			_filteredRows.addElement(rowTemp.elementAt(i));
		}
		rowTemp = (Vector) rows.elementAt(2);
		rowSize = rowTemp.size();
		// transfer deleted rows
		for (int i = 0; i < rowSize; i++) {
			_deletedRows.addElement(rowTemp.elementAt(i));
		}
	}
	/**
	 * This method is invoked when the datastore buffer needs to be automatically retrieved by a JspController
	 */
	public void autoRetrieve() throws Exception {

	}
	/**
	 * This method will compare the contents of two rows using the specified column list. It will return < 0 if row1 is less then row2, 0 if they are equal and > 0 if row1 is greater than row2
	 * @param row1 the first row to check
	 * @param row2 the next row to check
	 * @param cols An array of column numbers to use for the comparison
	 */
	public int compareRows(int row1, int row2, int[] cols) {

		if (row1 < 0 || row1 >= getRowCount())
			return -1;

		if (row2 < 0 || row2 >= getRowCount())
			return 1;

		DSDataRow d1 = (DSDataRow) _rows.elementAt(row1);
		DSDataRow d2 = (DSDataRow) _rows.elementAt(row2);

		return d1.compareRows(d2, cols);

	}
	/**
	 * This method will compare the contents of two rows using the specified column list. It will return < 0 if row1 is less then row2, 0 if they are equal and > 0 if row1 is greater than row2
	 * @param row1 the first row to check
	 * @param row2 the next row to check
	 * @param cols An array of column names to use for the comparison
	 */
	public int compareRows(int row1, int row2, String[] cols) {

		int colsn[] = new int[cols.length];

		for (int i = 0; i < cols.length; i++) {
			int ndx = getColumnIndex(cols[i]);
			if (ndx < 0)
				return 0;
			colsn[i] = ndx;
		}

		return compareRows(row1, row2, colsn);

	}
	/**
	 * This deletes the current row in the DataStoreBuffer. This will generate a delete statment that will remove the row from the database when the update method is called.
	 * @return true if the row is deleted and false if not.
	 */
	public boolean deleteRow() {
		return deleteRow(getRow());
	}
	/**
	 * This deletes the row in the DataStoreBuffer. This will generate a delete statment that will remove the row from the database when the update method is called.
	 * @param row The number of the row to delete.
	 * @return True if the row is deleted and false if not.
	 */
	public boolean deleteRow(int row) {
		if (row < 0)
			return false;
		waitForRow(row);
		if (row >= getRowCount())
			return false;
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		d.copyDataToOrig();
        //fc 06/11/04: Code to handle the deletion of the first row in a One to Many Relationship Model.
		if (d.getRowStatus() != STATUS_NEW && d.getRowStatus() != STATUS_NEW_MODIFIED) {
			if (_manytoonerelationship && row == 0) {
				if (getRowCount() > 1) {
					if (this instanceof DataStoreInterface) {
						DSDataRow dsTo = (DSDataRow) _rows.elementAt(1);
						DataStoreRow dsrFrom = new DataStoreRow(this, d, getDescriptor());
						DataStoreRow dsrTo = new DataStoreRow(this, dsTo, getDescriptor());
						try {
							copyMasterDataValuesToRow(getTablesRelationshipDescription((DataStoreInterface) this), dsrFrom, dsrTo);
						} catch (DataStoreException dse) {
							MessageLog.writeErrorMessage(dse, this);
						}
					}
				}
			}
		}
		_rows.removeElementAt(row);
		if (d.getRowStatus() != STATUS_NEW && d.getRowStatus() != STATUS_NEW_MODIFIED)
			_deletedRows.addElement(d);

		if (areThereModelListeners()) {
			ModelChangedEvent evt = new ModelChangedEvent(this, getRow());
			notifyListeners(evt);
		}

		if (row == getRow() && row >= getRowCount())
			setCurrentRow(getRow() - 1);
        //fc 06/11/04: Ensures the current row cannot be greater than the row count after a delete.
        if (getRow()>=getRowCount())
            setCurrentRow(getRowCount() - 1);

		return true;
	}
	public String export() throws com.salmonllc.sql.DataStoreException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter p = new PrintWriter(out);
		p.println();
		export(p);
		p.close();
		return out.toString();
	}
	/**
	 * Exports the rows of the data store in the format specified.
	 * @param format: Can be EXPORT_XML, EXPORT_HTML or EXPORT_TAB_DELIMITED;
	 * @param includeHeaders: True if the output should contain column headings.
	 * @param p: The PrintWriter to send the output to.
	 *
	 */
	public void export(int format, boolean includeHeaders, PrintWriter p) {
		waitForRetrieve();
		DSColumnDescriptor col = null;
		DSDataRow row = null;
		StringBuffer work = new StringBuffer();
		Object o;
		if (format == EXPORT_XML) {
			exportXML(p);
		} else if (format == EXPORT_HTML) {
			//do html
			p.write("<TABLE BORDER = \"1\">");
			if (includeHeaders) {
				work.append("<TR>");
				for (int i = 0; i < _desc.getColumnCount(); i++) {
					col = _desc.getColumn(i);
					String name = col.getColumn();
					if (name == null) {
						name = col.getInternalName();
					}
					String table = col.getTable();
					if (table == null)
						table = _desc.getDefaultTable();
					work.append("<TH>");
					work.append(table);
					work.append(".");
					work.append(name);
					work.append("</TH>");
				}
				work.append("</TR>");
				p.println(work.toString());
			}
			for (int i = 0; i < getRowCount(); i++) {
				work.setLength(0);
				work.append("<TR>");
				row = (DSDataRow) _rows.elementAt(i);
				for (int j = 0; j < _desc.getColumnCount(); j++) {
					o = row.getData(j);
					work.append("<TD>");
					if (o == null)
						work.append("&nbsp;");
					else {
						try {
							work.append(fixSpecialHTMLCharacters(getFormattedString(i, j)));
						} catch (Exception e) {
						}
					}
					work.append("</TD>");
				}
				work.append("</TR>");
				p.println(work.toString());
			}
			p.write("</TABLE>");
		} else {
			//tab delimited
			if (includeHeaders) {
				for (int i = 0; i < _desc.getColumnCount(); i++) {
					col = _desc.getColumn(i);
					String name = col.getColumn();
					if (name == null) {
						name = col.getInternalName();
					}
					String table = col.getTable();
					if (table == null)
						table = _desc.getDefaultTable();
					work.append(table);
					work.append(".");
					work.append(name);
					work.append('\t');
				}
				p.println(work.toString());
			}
			for (int i = 0; i < getRowCount(); i++) {
				work.setLength(0);
				row = (DSDataRow) _rows.elementAt(i);
				for (int j = 0; j < _desc.getColumnCount(); j++) {

					o = row.getData(j);
					if (o != null) {
						try {
							work.append(removeSpecialCharacters(getFormattedString(i, j)));
						} catch (Exception e) {
						}
					}
					work.append('\t');
				}
				p.println(work.toString());
			}
		}
	}
	public void export(PrintWriter p) throws com.salmonllc.sql.DataStoreException {
		String TAB = "      ";
		p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		p.println("");
		p.println("<!DOCTYPE ResultSet SYSTEM \"datastore.dtd\">");

		p.println("<ResultSet>");

		// Printing the Meta Data
		// ------------------------
		p.println("<ResultSetMetaData>");
		String columnMetaData = "";
		for (int i = 0; i < _desc.getColumnCount(); i++) {
			columnMetaData = "";
			DSColumnDescriptor col = _desc.getColumn(i);
			columnMetaData += TAB + "<ColumnMetaData ";
			String cName = col.getColumn();
			if (cName == null)
				cName = "Column" + i;

			columnMetaData += " component=\"" + cName + "\"";
			columnMetaData += " columnName=\"" + cName + "\"";
			columnMetaData += " caption=\"" + cName + "\"";

			String table = col.getTable();
			if (table == null)
				table = "defaultTable";

			columnMetaData += " table=\"" + table + "\"";
			columnMetaData += " columnType=\"";
			int colType = col.getType();
			switch (colType) {
				case DATATYPE_STRING :
					columnMetaData += "String";
					break;
				case DATATYPE_DATE :
					columnMetaData += "Date";
					break;
				case DATATYPE_DATETIME :
					columnMetaData += "DateTime";
					break;
				case DATATYPE_TIME :
					columnMetaData += "Time";
					break;
				case DATATYPE_SHORT :
					columnMetaData += "Short";
					break;
				case DATATYPE_INT :
					columnMetaData += "Integer";
					break;
				case DATATYPE_LONG :
					columnMetaData += "Long";
					break;
				case DATATYPE_FLOAT :
					columnMetaData += "Float";
					break;
				case DATATYPE_DOUBLE :
					columnMetaData += "Double";
					break;
				case DATATYPE_BYTEARRAY :
					columnMetaData += "ByteArray";
					break;
			}
			columnMetaData += "\"";
			columnMetaData += " primaryKey=\"" + col.isPrimaryKey() + "\"";

			if (col.getFormat() != null)
				columnMetaData += " format=\"" + col.getFormat() + "\"";

			if (col.getInternalName() != null)
				columnMetaData += " internalName=\"" + col.getInternalName() + "\"";

			columnMetaData += "> </ColumnMetaData>";

			p.println(columnMetaData);
		}
		p.println("</ResultSetMetaData>");

		// Printing the Data
		// -----------------------
		p.println("<ResultSetData>");
		String row = "";
		for (int r = 0; r < getRowCount(); r++) {
			row = "";
			DSDataRow dRow = getDataRow(r);
			row += TAB + "<Row>";
			for (int c = 0; c < getColumnCount(); c++) {
				if (dRow.getData(c) != null) {
					row += "\n"
						+ TAB
						+ "<Column "
						+ " table=\""
						+ (_desc.getColumn(c).getTable() == null ? "defaultTable" : _desc.getColumn(c).getTable())
						+ "\""
						+ " name=\""
						+ (_desc.getColumn(c).getColumn() == null ? ("Column" + c) : _desc.getColumn(c).getColumn())
						+ "\""
						+ " value=\""
						+ getFormattedString(r, c)
						+ "\" />";
				}
			}
			row += "</Row>";
			p.println(row);
		}
		p.println("</ResultSetData>");

		p.println("</ResultSet>");
	}
	private void exportXML(PrintWriter p) {
		p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		p.println("");
		p.println("<!DOCTYPE resultSet [");
		p.println("<!ELEMENT resultSet (dataDef, dataRows)>");
		p.println("<!ATTLIST resultSet rows CDATA #REQUIRED>");
		p.println("<!ELEMENT dataDef (columnDef)+>");
		p.println("<!ELEMENT columnDef EMPTY>");
		p.println("<!ATTLIST columnDef name CDATA #REQUIRED");
		p.println("  type (String|Date|DateTime|Time|Short|Integer|Long|Float|Double|ByteArray) #REQUIRED");
		p.println("  number CDATA #REQUIRED");
		p.println("  pkey (true|false) #REQUIRED");
		p.println("  updateable (true|false) #REQUIRED >");
		p.println("<!ELEMENT dataRows (row*)>");
		String rowElement = "<!ELEMENT row (";
		for (int i = 0; i < _desc.getColumnCount(); i++) {
			if (i != 0)
				rowElement += ",";
			rowElement += "c" + i;
		}
		rowElement += ")+>";
		p.println(rowElement);
		p.println("<!ATTLIST row number CDATA #REQUIRED>");
		for (int i = 0; i < _desc.getColumnCount(); i++)
			p.println("<!ELEMENT c" + i + " (#PCDATA)>");
		p.println("]>");
		p.println("");
		p.println("<resultSet rows=\"" + getRowCount() + "\">");
		p.println("");
		p.println(" <dataDef>");
		DSColumnDescriptor col;
		String colDef = "";
		for (int i = 0; i < _desc.getColumnCount(); i++) {
			col = _desc.getColumn(i);
			String name = col.getColumn();
			if (name == null) {
				name = col.getInternalName();
			}
			String table = col.getTable();
			if (table == null)
				table = "defaultTable";

			colDef = "  <columnDef name=\"" + table + "." + name + "\" type=\"";
			int colType = col.getType();
			switch (colType) {
				case DATATYPE_STRING :
					colDef += "String";
					break;
				case DATATYPE_DATE :
					colDef += "Date";
					break;
				case DATATYPE_DATETIME :
					colDef += "DateTime";
					break;
				case DATATYPE_TIME :
					colDef += "Time";
					break;
				case DATATYPE_SHORT :
					colDef += "Short";
					break;
				case DATATYPE_INT :
					colDef += "Integer";
					break;
				case DATATYPE_LONG :
					colDef += "Long";
					break;
				case DATATYPE_FLOAT :
					colDef += "Float";
					break;
				case DATATYPE_DOUBLE :
					colDef += "Double";
					break;
				case DATATYPE_BYTEARRAY :
					colDef += "ByteArray";
					break;
			}
			colDef += "\" number=\"" + i + "\" pkey=\"" + col.isPrimaryKey() + "\" updateable=\"" + col.isUpdateable() + "\"/>";
			p.println(colDef);
		}
		p.println(" </dataDef>");
		p.println("");
		p.println(" <dataRows>");
		StringBuffer sb = new StringBuffer();
		DSDataRow row;
		for (int i = 0; i < getRowCount(); i++) {
			sb.setLength(0);
			sb.append("   <row number=\"" + i + "\">");
			row = (DSDataRow) _rows.elementAt(i);
			for (int j = 0; j < _desc.getColumnCount(); j++) {
				sb.append("<c" + j + ">");
				Object o = row.getData(j);
				if (o != null) {
					try {
						sb.append(fixSpecialHTMLCharacters(getFormattedString(i, j)));
					} catch (Exception e) {
					}
				}
				sb.append("</c" + j + ">");
			}
			sb.append("</row>");
			p.println(sb.toString());
		}
		p.println(" </dataRows>");
		p.println("");
		p.println("</resultSet>");
	}

	/**
	 * This method will remove all rows from the buffer that don't match the criteria and place them in the datastore's filter buffer.
	 * @param filter A QBEBuilder that will build the filter
	 */
	public void filterQBE(QBEBuilder filter) throws DataStoreException {
		filter(filter.generateDataStoreFilter(this));

	}
	/**
	 * This method will remove all rows from the buffer that don't match the criteria and place them in the datastore's filter buffer.
	 * The method is passed a String containing the filter expression that will be used.
	 * Examples:<BR>
	 *		  <pre>
	 *        ds.filter("table1.column1 == 'xxxx'); <BR>
	 * 		  ds.filter("table1.column1.substring(2,3) == table1.column2");<BR>
	 * 		  ds.filter("table1.column1.substring(2,3) == table1.column2 || table1.column3.startsWith('xxx')");<BR><BR>
	 *		  </pre>
	 * @param filter The criteria to filter on. Enter a value of null to clear the filter.
	 * @see DataStoreEvaluator
	 */

	public void filter(String filter) throws DataStoreException {
		waitForRetrieve();

		for (int i = getFilteredCount() - 1; i >= 0; i--)
			_rows.addElement(_filteredRows.elementAt(i));

		_filteredRows.removeAllElements();

		if (filter != null) {
			DataStoreEvaluator eval = new DataStoreEvaluator(this, filter);
			int rowCount = getRowCount();
			for (int i = rowCount - 1; i > -1; i--) {
				DSDataRow row = (DSDataRow) _rows.elementAt(i);
				try {
					Boolean result = (Boolean) eval.evaluateRow(i);
					if (!result.booleanValue()) {
						_rows.removeElementAt(i);
						_filteredRows.addElement(row);
					}
				} catch (DataStoreException e) {
					throw (e);
				} catch (Exception e) {
					throw (new DataStoreException("Results of expression must be boolean"));
				}
			}
			notifyListeners(ModelChangedEvent.TYPE_DATA_FILTERED);
			pushCriteria(filter);
		}

	}
	/**
	 * This method finds the next row in the result set that matches the criteria entered and is within the start and end range.
	 * @param start The first row in the DataStoreBuffer to search.
	 * @param end The last row in the DataStoreBuffer to search (Make start > end to search backwards).
	 * @return Returns the first row matching the criteria within the range or -1 if none are found.
	 * @see DataStore#setFindExpression
	 */
	public synchronized int find(int start, int end) throws DataStoreException {
		if (_find == null)
			throw new DataStoreException("Find Expression not initialized. Use the setFindExpression before calling this method");

		if (start < end) {
			if (start < 0)
				start = 0;
			if (end >= getRowCount())
				end = getRowCount() - 1;

			for (int i = start; i <= end; i++) {
				try {
					Boolean res = (Boolean) _find.evaluateRow(i);
					if (res.booleanValue())
						return i;
				} catch (DataStoreException e) {
					throw (e);
				} catch (Exception e) {
					throw new DataStoreException("Find Expression must return a boolean value.");
				}
			}
		} else {
			if (end < 0)
				end = 0;
			if (start >= getRowCount())
				start = getRowCount() - 1;

			for (int i = start; i >= end; i--) {
				try {
					Boolean res = (Boolean) _find.evaluateRow(i);
					if (res.booleanValue())
						return i;
				} catch (DataStoreException e) {
					throw (e);
				} catch (Exception e) {
					throw new DataStoreException("Find Expression must return a boolean value.");
				}
			}
		}

		return -1;
	}
	/**
	 * This method finds the firs row in the result set that matches the criteria entered and makes it the current row.
	 * @return Returns false if no rows are found that match the criteria.
	 * @see DataStoreBuffer#setFindExpression
	 */
	public synchronized boolean findFirst() throws DataStoreException {
		int row = find(0, getRowCount());
		if (row != -1)
			return gotoRow(row);
		else
			return false;
	}
	/**
	 * This method finds the last row in the result set that matches the criteria entered and makes it the current row.
	 * @return Returns false if no rows are found that match the criteria.
	 * @see DataStoreBuffer#setFindExpression
	 */
	public synchronized boolean findLast() throws DataStoreException {
		int row = find(getRowCount() - 1, 0);
		if (row != -1)
			return gotoRow(row);
		else
			return false;
	}
	/**
	 * This method finds the next row in the result set that matches the criteria entered and makes it the current row.
	 * @return Returns false if no rows are found that match the criteria.
	 * @see DataStoreBuffer#setFindExpression
	 */
	public synchronized boolean findNext() throws DataStoreException {
		int st = getRow() + 1;
		if (st == getRowCount())
			return false;

		int row = find(st, getRowCount());
		if (row != -1)
			return gotoRow(row);
		else
			return false;
	}
	/**
	 * This method finds the next row in the result set that matches the criteria entered and makes it the current row.
	 * @return Returns false if no rows are found that match the criteria.
	 * @see DataStoreBuffer#setFindExpression
	 */
	public synchronized boolean findPrior() throws DataStoreException {
		int en = getRow() - 1;
		if (en < 0)
			return false;

		int row = find(en, 0);
		if (row != -1)
			return gotoRow(row);
		else
			return false;
	}
	protected String fixSpecialHTMLCharacters(String input) {
		if (input == null)
			return null;

		StringBuffer sb = new StringBuffer(input.length());

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '<')
				sb.append("&lt;");

			else if (c == '&')
				sb.append("&amp;");

			else if (c == '>')
				sb.append("&gt;");

			else if (c == '>')
				sb.append("&gt;");

			else if (c == '"')
				sb.append("&quot;");
			else
				sb.append(c);
		}

		return sb.toString();
	}
	/**
	 * This method return a Object value from the current row of the data store buffer.
	 * @return The Object value
	 * @param column The column number in the data store buffer.
	 */
	public Object getAny(int column) throws DataStoreException {
		return getObject(getRow(), column, DATATYPE_ANY);
	}
	/**
	 * This method return a Object value from the data store buffer.
	 * @return The Object value
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public Object getAny(int row, int column) throws DataStoreException {
		return getObject(row, column, DATATYPE_ANY);
	}
	/**
	 * This method return a Object value from the data store buffer.
	 * @return The Object value
	 * @param row The row number in the data store buffer.
	 * @param column The column name (in the form table.column) in the data store buffer.
	 */
	public Object getAny(int row, String column) throws DataStoreException {
		return getObject(row, column, DATATYPE_ANY);
	}
	/**
	 * This method return a Object value from the current row of the data store buffer.
	 * @return The Object value
	 * @param column The column name in the data store buffer.
	 */
	public Object getAny(String column) throws DataStoreException {
		return getObject(getRow(), column, DATATYPE_ANY);
	}
	/**
	 * This method will get the whether or not columns in the DatastoreBuffer should be automatically bound to components in the page (Only used for DataStoreBuffers created via the DataSource jsp tag)
	 */
	public boolean getAutoBind() {
		return _autoBind;
	}
	/**
	 * This method will get whether or not the DatastoreBuffer should be automatically retrieved when a page is requested (Only used for DataStoreBuffers created via the DataSource jsp tag)
	 * Valid values are AUTORETRIEVE_NEVER (Don't Autoretrieve),AUTORETRIEVE_ALWAYS (Retrieve on each page request), AUTORETRIEVE_ONCHANGE (Autoretrieve if the selection criteria changes);
	 */
	public int getAutoRetrieve() {
		return _autoRetrieve;
	}
	/**
	 * This method will return the autoretrieve criteria used for the datastore.
	 */
	public AutoRetrieveCriteria getAutoRetrieveCriteria() {
		return _crit;
	}
	static String getBoolProperty(boolean p) {
		return (p ? "true" : "false");
	}
	/**
	 * This method returns the true if any bucket columns (columns not representing database columns) have been modified on the current row. Bucket column modfications aren't reflected in the row status for each row.
	 */
	public boolean getBucketsModified() {
		return getBucketsModified(getRow());
	}
	/**
	 * This method returns the true if any bucket columns (columns not representing database columns) have been modified on the specified row. Bucket column modfications aren't reflected in the row status for each row.
	 * @param row The row in the datastore buffer.
	 */
	public boolean getBucketsModified(int row) {
		if (row < 0)
			return false;
		waitForRow(row);
		if (row >= getRowCount())
			return false;
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		return d.getBucketsModified();
	}
	/**
	 * This method return a byte array value from the current row in the data store buffer.
	 * @return The byte array
	 * @param column The column number in the data store buffer.
	 */
	public byte[] getByteArray(int column) throws DataStoreException {
		return (byte[]) getObject(getRow(), column, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method return a byte array value from the data store buffer.
	 * @return The byte array
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public byte[] getByteArray(int row, int column) throws DataStoreException {
		return (byte[]) getObject(row, column, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method return a byte array value from the data store buffer.
	 * @return The byte array
	 * @param row The row number in the data store buffer.
	 * @param column The column name (in the form table.column) in the data store buffer.
	 */
	public byte[] getByteArray(int row, String column) throws DataStoreException {
		return (byte[]) getObject(row, column, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method return a byte array value from the current row in the data store buffer.
	 * @return The byte array
	 * @param column The column name (in the form table.column) in the data store buffer.
	 */
	public byte[] getByteArray(String column) throws DataStoreException {
		return (byte[]) getObject(getRow(), column, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method returns the number of columns in the datastore.
	 */
	public int getColumnCount() {
		return _desc.getColumnCount();
	}
	/**
	 * This method returns the Data Type for a particular column.
	 * @param column The column number in the data store buffer.
	 */

	public int getColumnDataType(int column) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		DSColumnDescriptor des = _desc.getColumn(column);
		return des.getType();
	}
	/**
	 * This method returns the Data Type for a particular column.
	 * @param column The column name in the data store buffer.
	 */

	public int getColumnDataType(String column) throws DataStoreException {
		int col = getColumnIndex(column);
		return getColumnDataType(col);
	}
	/**
	 * This method returns the index of the column in the data store given its name.
	 * @return The column number or -1 if the column name is not found.
	 * @param column The name of the column to search for.
	 */
	public int getColumnIndex(String column) {
		return _desc.getColumnIndex(column);
	}
	/**
	 * This method returns a list with the names of all the columns in the data store.
	 */
	public String[] getColumnList() {
		String[] colList = new String[_desc.getColumnCount()];

		try {
			for (int i = 0; i < _desc.getColumnCount(); i++)
				colList[i] = getColumnName(i);
		} catch (Exception e) {
		}

		return colList;
	}
	/**
	 * This method returns the name of the column in the data store given its index.
	 */
	public String getColumnName(int col) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");
		DSColumnDescriptor d = _desc.getColumn(col);
		String inName = d.getInternalName();
		if (inName != null && inName.trim().length() > 0)
			return inName;
		String table = d.getTable();
		if (table == null)
			table = _desc.getDefaultTable();

	    if (table == null)
	    	return d.getColumn();
	    else
	    	return table + "." + d.getColumn();
	}
	DSDataRow getDataRow(int rowNo) {
		if (_rows == null)
			return null;

		if (rowNo < 0 || rowNo >= _rows.size())
			return null;

		return (DSDataRow) _rows.elementAt(rowNo);
	}

	/**
	 * If the datastore is connected to a client side proxy datastore, the row in the proxy that matches the specified one in the server side datastore
	 */
	public int getProxyRow(int rowNo) {
		if (_rows == null)
			return -1;

		if (rowNo < 0 || rowNo >= _rows.size())
			return -1;

		return ((DSDataRow) _rows.elementAt(rowNo)).getProxyRow();
	}
	/**
	 * This method gets the specified row from the DataStore.
	 * @param rowNo The number of the row to get.
	 * @param buffer The datastore buffer to look in. Valid values are BUFFER_STANDARD, BUFFER_DELETED, BUFFER_FILTERED.
	 */
	public DataStoreRow getDataStoreRow(int rowNo, int buffer) throws DataStoreException {
		Vector rows = null;
		if (buffer == BUFFER_STANDARD)
			rows = _rows;
		else if (buffer == BUFFER_DELETED)
			rows = _deletedRows;
		else if (buffer == BUFFER_FILTERED)
			rows = _filteredRows;
		else
			throw new DataStoreException("Buffer is invalid");
		;

		if (rows == null)
			throw new DataStoreException("Row number:" + rowNo + " is out of range.");
		;

		if (rowNo < 0 || rowNo >= rows.size())
			throw new DataStoreException("Row number:" + rowNo + " is out of range.");
		;

		return new DataStoreRow(this, (DSDataRow) rows.elementAt(rowNo), _desc);
	}
	/**
	 * This method return a date value from current row of the data store buffer.
	 * @return The DateTime Value
	 * @param column The column number in the data store buffer.
	 */
	public java.sql.Date getDate(int column) throws DataStoreException {
		return getDate(getRow(), column);
	}
	/**
	 * This method return a date value from the data store buffer.
	 * @return The DateTime Value
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public java.sql.Date getDate(int row, int column) throws DataStoreException {
		Object o = getObject(row, column, DATATYPE_DATE);
		if (o == null)
			return null;
		else {
			try {
				return (java.sql.Date) o;
			} catch (ClassCastException e) {
				return new java.sql.Date(((java.sql.Timestamp) o).getTime());
			}
		}
	}
	/**
	 * This method return a date value from the data store buffer.
	 * @return The DateTime Value
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the data store buffer.
	 */
	public java.sql.Date getDate(int row, String column) throws DataStoreException {
		return getDate(row, getColumnIndex(column));
	}
	/**
	 * This method return a date value from current row of the data store buffer.
	 * @return The DateTime Value
	 * @param column The column name in the data store buffer.
	 */
	public java.sql.Date getDate(String column) throws DataStoreException {
		return getDate(getRow(), column);
	}
	/**
	 * This method return a date value from current row of the data store buffer.
	 * @return The DateTime Value
	 * @param column The column number in the data store buffer.
	 */
	public Timestamp getDateTime(int column) throws DataStoreException {
		return getDateTime(getRow(), column);
	}
	/**
	 * This method return a date value from the data store buffer.
	 * @return The DateTime Value
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public Timestamp getDateTime(int row, int column) throws DataStoreException {
		Object o = getObject(row, column, DATATYPE_DATETIME);
		if (o == null)
			return null;
		else
			return (Timestamp) o;
	}
	/**
	 * This method return a date value from the data store buffer.
	 * @return The DateTime Value
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the data store buffer.
	 */
	public Timestamp getDateTime(int row, String column) throws DataStoreException {
		Object o = getObject(row, column, DATATYPE_DATETIME);
		if (o == null)
			return null;
		else
			return (Timestamp) o;
	}
	/**
	 * This method return a datetime value from current row of the data store buffer.
	 * @return The DateTime Value
	 * @param column The column name in the data store buffer.
	 */
	public java.sql.Timestamp getDateTime(String column) throws DataStoreException {
		return getDateTime(getRow(), column);
	}
	/**
	 * This method returns the current number of rows that will be deleted when the datastores update method is called.
	 * @return int
	 */
	public int getDeletedCount() {
		return _deletedRows.size();
	}
	DSDataStoreDescriptor getDescriptor() {
		return _desc;
	}
	/**
	 * This method return a double value from the current row in data store buffer.
	 * @return The double value.
	 * @param column The column number in the in the data store buffer.
	 */
	public double getDouble(int column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_DOUBLE).doubleValue();
	}
	/**
	 * This method return a double value from the data store buffer.
	 * @return The double value.
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the in the data store buffer.
	 */
	public double getDouble(int row, int column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_DOUBLE).doubleValue();
	}
	/**
	 * This method return a double value from the data store buffer.
	 * @return The double value.
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public double getDouble(int row, String column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_DOUBLE).doubleValue();
	}
	/**
	 * This method return a double value from the current row in data store buffer.
	 * @return The double value.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public double getDouble(String column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_DOUBLE).doubleValue();
	}
	/**
	 * This method returns the current number of rows in the DataStores filter buffer.
	 * @return the number of rows in the filter buffer.
	 */
	public int getFilteredCount() {
		return _filteredRows.size();
	}
	/**
	 * This method return a float value from the current row in the data store buffer.
	 * @return The long value.
	 * @param column The column number in the data store buffer.
	 */
	public float getFloat(int column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_FLOAT).floatValue();
	}
	/**
	 * This method return a float value from the data store buffer.
	 * @return The long value.
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public float getFloat(int row, int column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_FLOAT).floatValue();
	}
	/**
	 * This method return a float value from the data store buffer.
	 * @return The long value.
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the data store buffer.
	 */
	public float getFloat(int row, String column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_FLOAT).floatValue();
	}
	/**
	 * This method return a float value from the current row in the data store buffer.
	 * @return The long value.
	 * @param column The column name in the data store buffer.
	 */
	public float getFloat(String column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_FLOAT).floatValue();
	}
	/**
	 * This method returns a the format string for a date, time, datetime or numeric type column.
	 * @param col The column number to get the format for.
	 * @see DataStore#getFormattedString
	 * @see DataStore#setFormattedString
	 */
	public String getFormat(int col) throws DataStoreException {
		if (col < 0 || col >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor d = _desc.getColumn(col);

		java.text.Format f = d.getFormat();

		if (f == null)
			return null;
		else if (f instanceof java.text.DecimalFormat)
			return ((java.text.DecimalFormat) f).toPattern();
		else
			return ((java.text.SimpleDateFormat) f).toPattern();

	}
	/**
	 * This method returns a the format string for a date, time, datetime or numeric type column.
	 * @param col The column name to get the format for.
	 * @see DataStore#getFormattedString
	 * @see DataStore#setFormattedString
	 */
	public String getFormat(String col) throws DataStoreException {
		int coln = getColumnIndex(col);
		return getFormat(coln);
	}
	/**
	 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
	 * @see DataStore#setFormat
	 */
	public String getFormattedString(int column) throws DataStoreException {
		if (getRow() == -1)
			return null;
		else
			return getFormattedString(getRow(), column);
	}
	/**
	 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>If there is a temporary value specified for the column, that will be returned instead.
	 * @see DataStore#setFormat
		 * @see DataStoreBuffer#setTempValue
	 */
	public String getFormattedString(int row, int column) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		DSColumnDescriptor d = _desc.getColumn(column);

		DSDataRow dsRow = (DSDataRow) _rows.elementAt(row);
		String tempVal = dsRow.getTempValue(column);
		if (tempVal != null)
			return tempVal;

		java.text.Format f = d.getFormat();
		Object value = getObject(row, column, DATATYPE_ANY);
		String retVal = null;

		if (value == null)
			retVal = null;
		else if (value instanceof String)
			retVal = (String) value;
		else if (value instanceof byte[])
			retVal = new String((byte[])value);
		else if (f == null)
			retVal = value.toString();
		else {
			if (f instanceof java.text.DecimalFormat)
				retVal = f.format(value);
			else
				retVal = ((SalmonDateFormat) f).format((java.util.Date) value);
		}

		return retVal;
	}
	/**
	 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
	 * @see DataStore#setFormat
	 */
	public String getFormattedString(int row, String column) throws DataStoreException {
		int coln = getColumnIndex(column);
		return getFormattedString(row, coln);
	}
	/**
	 * This method return a string value from the data store buffer. If the column is numeric, date, time or datetime it will first be formatted into a String based on the pattern specified in the setFormat method.<BR><BR>
	 * @see DataStore#setFormat
	 */
	public String getFormattedString(String column) throws DataStoreException {
		return getFormattedString(getRow(), column);
	}
	/**
	 * This method return a integer value from the current row in the data store buffer.
	 * @return The integer value.
	 * @param column The column number in the data store buffer
	 */
	public int getInt(int column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_INT).intValue();
	}
	/**
	 * This method return a integer value from the data store buffer.
	 * @return The integer value.
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer
	 */
	public int getInt(int row, int column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_INT).intValue();
	}
	/**
	 * This method return a integer value from the data store buffer.
	 * @return The integer value.
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public int getInt(int row, String column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_INT).intValue();
	}
	/**
	 * This method return a integer value from the current row in data store buffer.
	 * @return The integer value.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public int getInt(String column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_INT).intValue();
	}
	static String getIntProperty(int p) {
		return new Integer(p).toString();
	}
	/**
	 * This method return a long value from the current row in the data store buffer.
	 * @return The long value.
	 * @param column The column number in the data store buffer.
	 */
	public long getLong(int column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_LONG).longValue();
	}
	/**
	 * This method return a long value from the data store buffer.
	 * @return The long value.
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public long getLong(int row, int column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_LONG).longValue();
	}
	/**
	 * This method return a long value from the data store buffer.
	 * @return The long value.
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the data store buffer.
	 */
	public long getLong(int row, String column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_LONG).longValue();
	}
	/**
	 * This method return a long value from the data store buffer.
	 * @return The long value.
	 * @param column The column name in the data store buffer.
	 */
	public long getLong(String column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_LONG).longValue();
	}
	private Number getNumber(int row, int column, int type) throws DataStoreException {
		Object o = getObject(row, column, type);
		if (o == null)
			return new Integer(0);
		else
			return (Number) o;
	}
	private Number getNumber(int row, String column, int type) throws DataStoreException {
		Object o = getObject(row, column, type);
		if (o == null)
			return new Integer(0);
		else
			return (Number) o;
	}
	private Object getObject(int row, int column, int type) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		if (type != DATATYPE_ANY) {
			DSColumnDescriptor des = _desc.getColumn(column);
			if (des.getType() != type)
				throw new DataStoreException("Column type mismatch.");
		}



		DSDataRow d = (DSDataRow) _rows.elementAt(row);
        Object oData=d.getData(column);
        //fc 06/11/04: If this model is a One To Many Model and column is in master then get value from first row.
        if (_manytoonerelationship && row>0 && isColumnInOneToManyRelationship(getColumnName(column))) {
          //fc 10/11/04: If this model is a One To Many Model and column is new or new modified get value from first row.
          if (row!=0 && (getRowStatus(row)==STATUS_NEW || getRowStatus(row)==STATUS_NEW_MODIFIED)) {
              DSDataRow dsFrom = (DSDataRow) _rows.elementAt(0);
              DSDataRow dsTo = (DSDataRow) _rows.elementAt(row);
              DataStoreRow dsrFrom = new DataStoreRow(this, dsFrom, getDescriptor());
              DataStoreRow dsrTo = new DataStoreRow(this, dsTo, getDescriptor());
              try {
                  copyMasterDataValuesToRow(getTablesRelationshipDescription((DataStoreInterface) this), dsrFrom, dsrTo);
              } catch (DataStoreException dse) {
                  MessageLog.writeErrorMessage(dse, this);
              }
              oData=d.getData(column);
             //oData=((DSDataRow)_rows.elementAt(0)).getData(column);
          }
        }
		return oData;
	}
	private Object getObject(int row, String column, int type) throws DataStoreException {
		int col = getColumnIndex(column);
		if (col == -1) {
			MessageLog.writeInfoMessage("column : " + column + " -> NOT FOUND", this);
		}
		return getObject(row, col, type);
	}
	protected int getObjectType(Object obj) {

		if (obj instanceof String)
			return DATATYPE_STRING;

		if (obj instanceof Integer)
			return DATATYPE_INT;

		if (obj instanceof java.sql.Timestamp)
			return DATATYPE_DATETIME;

		if (obj instanceof java.sql.Time)
			return DATATYPE_TIME;

		if (obj instanceof java.sql.Date)
			return DATATYPE_DATE;

		if (obj instanceof Double)
			return DATATYPE_DOUBLE;

		if (obj instanceof byte[])
			return DATATYPE_BYTEARRAY;

		if (obj instanceof Short)
			return DATATYPE_SHORT;

		if (obj instanceof Long)
			return DATATYPE_LONG;

		if (obj instanceof Float)
			return DATATYPE_FLOAT;

		return DATATYPE_ANY;
	}
	/**
	 * This method creates a properties object containing the definition of the data store.
	 */
	public Properties getProperties() {

		Properties p = new Properties();
		java.text.DecimalFormat fmt = new java.text.DecimalFormat("0000");

		String desc = "base.";
		p.put(desc + "defaultTable", getStringProperty(_desc.getDefaultTable()));
		p.put(desc + "whereClause", getStringProperty(_desc.getWhereClause()));
		p.put(desc + "groupByClause", getStringProperty(_desc.getGroupByClause()));
		p.put(desc + "havingClause", getStringProperty(_desc.getHavingClause()));
		p.put(desc + "orderByClause", getStringProperty(_desc.getOrderByClause()));
		p.put(desc + "distinct", getBoolProperty(_desc.getDistinct()));
		p.put(desc + "trimStrings", getBoolProperty(_desc.getTrimStrings()));

		for (int i = 0; i < _desc.getColumnCount(); i++) {
			DSColumnDescriptor d = _desc.getColumn(i);
			String name = "col." + fmt.format(i) + ".";
			p.put(name + "table", getStringProperty(d.getTable()));
			p.put(name + "column", getStringProperty(d.getColumn()));
			p.put(name + "type", getIntProperty(d.getType()));
			p.put(name + "pkey", getBoolProperty(d.isPrimaryKey()));
			p.put(name + "update", getBoolProperty(d.isUpdateable()));
			p.put(name + "name", getStringProperty(d.getInternalName()));
			p.put(name + "concurrency", getBoolProperty(d.getConcurrency()));
			p.put(name + "useBind", getIntProperty(d.getUseBind()));

			java.text.Format f = d.getFormat();
			if (f instanceof java.text.SimpleDateFormat)
				p.put(name + "format", getStringProperty(((java.text.SimpleDateFormat) f).toPattern()));
			else if (f instanceof java.text.DecimalFormat)
				p.put(name + "format", getStringProperty(((java.text.DecimalFormat) f).toPattern()));
			else
				p.put(name + "format", "null");
		}

		StringBuffer s = new StringBuffer();
		for (int i = 0; i < _desc.getJoinCount(); i++) {
			DSJoinDescriptor d = _desc.getJoin(i);
			String name = "join." + fmt.format(i) + ".";

			s.setLength(0);
			for (int j = 0; j < d.getLeftCount(); j++) {
				s.append(d.getLeftColumn(j));
				s.append(',');
			}
			if (s.length() > 0)
				s.setLength(s.length() - 1);
			p.put(name + "left", s.toString());

			s.setLength(0);
			for (int j = 0; j < d.getRightCount(); j++) {
				s.append(d.getRightColumn(j));
				s.append(',');
			}
			if (s.length() > 0)
				s.setLength(s.length() - 1);
			p.put(name + "right", s.toString());

			p.put(name + "outer", getBoolProperty(d.isOuter()));
		}

		for (int i = 0; i < _desc.getAliasCount(); i++) {
			DSTableAliasDescriptor d = _desc.getAlias(i);
			String name = "tableAlias." + fmt.format(i) + ".";
			p.put(name + "table", getStringProperty(d.getTable()));
			p.put(name + "alias", getStringProperty(d.getAlias()));
		}

		return p;
	}
	/**
	 * This method returns the current row in the result set.
	 * @return The current row in the result set or -1 if the result set is empty.
	 */
	public int getRow() {
		return _currentRow;
	}
	/**
	 * This method returns the current number of rows in the DataStores data buffer. If a retrieve is in progress this value will change as the rows are retrieved.
	 * @return the number of rows in the buffer.
	 */
	public int getRowCount() {
		return _rows.size();
	}
	/**
	 * This method will return a copy of all the rows in the DataStore. The rows can then be restored with the setRows method. The intention of this method is to allow for the caching of data in the datastore so it doesn't have to be retrieved.
	 */
	public Vector getRows() {
		Vector v = new Vector(3);
		v.addElement(_rows.clone());
		v.addElement(_filteredRows.clone());
		v.addElement(_deletedRows.clone());
		return v;
	}
	/**
	 * This method returns the status flag of the current row.
	 * @return -1 if the row is not in the buffer or the status if it is (Valid values: STATUS_NOT_MODIFIED, STATUS_MODIFIED, STATUS_NEW, STATUS_NEW_MODIFIED).
	 */
	public int getRowStatus() {
		return getRowStatus(getRow());
	}
	/**
	 * This method returns the status flag of the specified row.
	 * @param row The row in the datastore buffer.
	 * @return -1 if the row is not in the buffer or the status if it is. (Valid values: STATUS_NOT_MODIFIED, STATUS_MODIFIED, STATUS_NEW, STATUS_NEW_MODIFIED).
	 */
	public int getRowStatus(int row) {
		if (row < 0)
			return -1;
		waitForRow(row);
		if (row >= getRowCount())
			return -1;
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		return d.getRowStatus();
	}
	/**
	 * This method return a short value from the current row in the data store buffer.
	 * @return The short value.
	 * @param column The column number in the data store buffer.
	 */
	public short getShort(int column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_SHORT).shortValue();
	}
	/**
	 * This method return a short value from the data store buffer.
	 * @return The short value.
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public short getShort(int row, int column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_SHORT).shortValue();
	}
	/**
	 * This method return a short value from the data store buffer.
	 * @return The short value.
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public short getShort(int row, String column) throws DataStoreException {
		return getNumber(row, column, DATATYPE_SHORT).shortValue();
	}
	/**
	 * This method return a short value from the current row in the data store buffer.
	 * @return The short value.
	 * @param column The column name in the form table.column in the data store buffer
	 */
	public short getShort(String column) throws DataStoreException {
		return getNumber(getRow(), column, DATATYPE_SHORT).shortValue();
	}
	/**
	 * This method return a string value from the data store buffer.
	 * @return The String Value
	 * @param column The column number in the data store buffer
	 */
	public String getString(int column) throws DataStoreException {
		return (String) getObject(getRow(), column, DATATYPE_STRING);
	}
	/**
	 * This method return a string value from the data store buffer.
	 * @return The String Value
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public String getString(int row, int column) throws DataStoreException {
		return (String) getObject(row, column, DATATYPE_STRING);
	}
	/**
	 * This method return a String value from the data store buffer.
	 * @return The String value
	 * @param row The row number in the data store buffer.
	 * @param column The column name (in the form table.column) in the data store buffer.
	 */
	public String getString(int row, String column) throws DataStoreException {
		return (String) getObject(row, column, DATATYPE_STRING);
	}
	/**
	 * This method return a string value from the data store buffer.
	 * @return The String Value
	 * @param column The column name (in the form table.column) in the data store buffer.
	 */
	public String getString(String column) throws DataStoreException {
		return (String) getObject(getRow(), column, DATATYPE_STRING);
	}
	static String getStringProperty(String p) {
		if (p == null)
			return "null";
		else
			return p;
	}
	/**
	 * This method returns an array of all the tables referenced in the datastore.
	 * @param updateable True if the table list should only include updateable tables and false if it should include all.
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

			if ((!updateable) || col.isUpdateable()) {
				boolean found = false;
				for (int j = 0; j < tables.size(); j++) {
					if ((tables.elementAt(j)).equals(tableName)) {
						if (col.isPrimaryKey())
							pkey.setElementAt(new Boolean(true), j);
						found = true;
						break;
					}
				}
				if (!found) {
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
		}

		String retVal[] = new String[tables.size()];
		tables.copyInto(retVal);

		return retVal;
	}
	/**
	 * This method return a time value from current row of the data store buffer.
	 * @return The time Value
	 * @param column The column number in the data store buffer.
	 */
	public java.sql.Time getTime(int column) throws DataStoreException {
		return getTime(getRow(), column);
	}
	/**
	 * This method return a time value from the data store buffer.
	 * @return The time Value
	 * @param row The row number in the data store buffer.
	 * @param column The column number in the data store buffer.
	 */
	public java.sql.Time getTime(int row, int column) throws DataStoreException {
		Object o = getObject(row, column, DATATYPE_TIME);
		if (o == null)
			return null;
		else
			return (java.sql.Time) o;
	}
	/**
	 * This method return a time value from the data store buffer.
	 * @return The Time Value
	 * @param row The row number in the data store buffer.
	 * @param column The column name in the data store buffer.
	 */
	public java.sql.Time getTime(int row, String column) throws DataStoreException {
		Object o = getObject(row, column, DATATYPE_TIME);
		if (o == null)
			return null;
		else
			return (java.sql.Time) o;
	}
	/**
	 * This method return a time value from current row of the data store buffer.
	 * @return The Time Value
	 * @param column The column name in the data store buffer.
	 */
	public java.sql.Time getTime(String column) throws DataStoreException {
		return getTime(getRow(), column);
	}
	public static String getTypeDescription(int type) {
		String ret = "";
		switch (type) {
			case DATATYPE_STRING :
				ret = "DATATYPE_STRING";
				break;
			case DATATYPE_INT :
				ret = "DATATYPE_INT";
				break;
			case DATATYPE_DATETIME :
				ret = "DATATYPE_DATETIME";
				break;
			case DATATYPE_DOUBLE :
				ret = "DATATYPE_DOUBLE";
				break;
			case DATATYPE_BYTEARRAY :
				ret = "DATATYPE_BYTEARRAY";
				break;
			case DATATYPE_SHORT :
				ret = "DATATYPE_SHORT";
				break;
			case DATATYPE_LONG :
				ret = "DATATYPE_LONG";
				break;
			case DATATYPE_FLOAT :
				ret = "DATATYPE_FLOAT";
				break;
			case DATATYPE_DATE :
				ret = "DATATYPE_DATE";
				break;
			case DATATYPE_TIME :
				ret = "DATATYPE_TIME";
				break;
			default :
				ret = "UNDEFINED";
				break;
		}
		return ret;
	}
	/**
	 * This method makes the current row the first one in the result set.
	 * @return Returns fals if the result set is empty.
	 */
	public synchronized boolean gotoFirst() {
		return gotoRow(0);
	}
	/**
	 * This method makes the current row the last one in the result set.
	 * @return Returns fals if the result set is empty.
	 */
	public synchronized boolean gotoLast() {
		waitForRetrieve();
		return gotoRow(getRowCount() - 1);
	}
	/**
	 * This method increments the current row in the result set.
	 * @return Returns false if the result set is empty or the current row is the last.
	 */
	public synchronized boolean gotoNext() {
		return gotoRow(getRow() + 1);
	}
	/**
	 * This method decrements the current row in the result set.
	 * @return Returns false if the result set is empty or the current row is the first.
	 */
	public synchronized boolean gotoPrior() {
		return gotoRow(getRow() - 1);
	}
	/**
	 * This method makes a specific row the current one in the result set.
	 * @return Returns false if the result set is empty or the row passed is beyond the bounds of the result set.
	 * @param row The row in the result set to make current.
	 */
	public synchronized boolean gotoRow(int row) {
		if (row < 0)
			return false;
		waitForRow(row);
		if (row >= getRowCount())
			return false;
		setCurrentRow(row);
		return true;

	}
	/**
	 * Imports an XML row into the Buffer
	 */
	public void importRow(com.salmonllc.xml.Row row) throws DataStoreException {
		// Insert a row
		// Then set row data
		int i = insertRow();

		DataStoreRow dataStoreRow = new DataStoreRow(this, new DSDataRow(_desc), _desc);
		DSDataRow dsRow = dataStoreRow.getDSDataRow();

		for (int c = 0; c < _desc.getColumnCount(); c++) {
			DSColumnDescriptor col = _desc.getColumn(c);
			String tableName = col.getTable();
			if (tableName == null)
				tableName = "";

			String colName = col.getColumn();
			if (colName == null)
				colName = "Column" + c;

			Column columnData = (Column) row.get(tableName == "" ? colName : tableName + "." + colName);

			int colType = col.getType();
			if (columnData != null) {
				Object oVal = null;
				switch (colType) {
					case DATATYPE_STRING :
						oVal = columnData.getValue();
						break;
					case DATATYPE_DATE :
						try {
							oVal = new SalmonDateFormat().parse(columnData.getValue());
						} catch (ParseException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_DATETIME :
						try {
							oVal = new Timestamp(new SalmonDateFormat().parse(columnData.getValue()).getTime());
						} catch (ParseException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_TIME :
						try {
							oVal = new Time(new SalmonDateFormat().parse(columnData.getValue()).getTime());
						} catch (ParseException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_SHORT :
						try {
							oVal = new Short(columnData.getValue());
						} catch (NumberFormatException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_INT :
						try {
							oVal = new Integer(columnData.getValue());
						} catch (NumberFormatException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_LONG :
						try {
							oVal = new Long(columnData.getValue());
						} catch (NumberFormatException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_FLOAT :
						try {
							oVal = new Float(columnData.getValue());
						} catch (NumberFormatException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_DOUBLE :
						try {
							oVal = new Double(columnData.getValue());
						} catch (NumberFormatException ex) {
							MessageLog.writeErrorMessage(ex, this);
						}
						break;
					case DATATYPE_BYTEARRAY :
						oVal = columnData.getValue().getBytes();
						break;
				}
				dsRow.setValue(c, oVal, _desc);
			}
		}

		setDataStoreRow(i, BUFFER_STANDARD, dataStoreRow);

	}
	/**
	 * Inserts a blank row at the end of the DataStoreBuffer. Returns the number of the row added.
	 */
	public int insertRow() {
		DSDataRow d = new DSDataRow(_desc);
		_rows.addElement(d);
		if (areThereModelListeners()) {
			ModelChangedEvent evt = new ModelChangedEvent(this, _rows.size() - 1);
			notifyListeners(evt);
		}
		  setCurrentRow(_rows.size() - 1);
		return getRow();
	}
	/**
	 * Inserts a blank row at the specified position of the DataStore's result set buffer. Returns the number of the row added.
	 */
	public int insertRow(int atPosition) {
		int rows = _rows.size();

		DSDataRow d = new DSDataRow(_desc);
		if (atPosition < 0)
			atPosition = 0;

		if (atPosition >= rows) {
			_rows.addElement(d);
			atPosition = rows;
		} else
			_rows.insertElementAt(d, atPosition);
		if (areThereModelListeners()) {
			ModelChangedEvent evt = new ModelChangedEvent(this, atPosition);
			notifyListeners(evt);
		}
		  setCurrentRow(atPosition);

		return _currentRow;
	}
	/**
	 * This method returns true if a particular column is modified
	 */
	public boolean isColumnModified(int row, int column) throws DataStoreException {
		if (row < 0)
			throw new DataStoreException("Invalid Row Number: " + row);
		waitForRow(row);
		if (row >= getRowCount())
			throw new DataStoreException("Invalid Row Number: " + row);
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		return d.getColumnStatus(column) == STATUS_MODIFIED || d.getColumnStatus(column) == STATUS_NEW_MODIFIED;
	}
	/**
	 * This method returns true if a particular column is modified
	 */
	public boolean isColumnModified(int row, String column) throws DataStoreException {
		int ndx = getColumnIndex(column);
		if (ndx == -1)
			throw new DataStoreException("Invalid Column Name:" + column);
		return isColumnModified(row, ndx);
	}
	/**
	 * This method will return true if the passed string value if a valid entry for the column based on the String format specified for the column via the setFormat method.
	 * @see DataStoreBuffer#setFormat
	 */
	public boolean isFormattedStringValid(int column, String value) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		DSColumnDescriptor d = _desc.getColumn(column);
		int type = d.getType();
		java.text.Format f = d.getFormat();

		try {
			if (value == null)
				return true;
			else if (f == null) {
				if (type == DATATYPE_INT)
					Integer.valueOf(value);
				else if (type == DATATYPE_DOUBLE)
					Double.valueOf(value);
				else if (type == DATATYPE_FLOAT)
					Float.valueOf(value);
				else if (type == DATATYPE_SHORT)
					Short.valueOf(value);
				else if (type == DATATYPE_LONG)
					Long.valueOf(value);
				else if (type == DATATYPE_DATETIME)
					Timestamp.valueOf(value);
				else if (type == DATATYPE_TIME)
					java.sql.Time.valueOf(value);
				else if (type == DATATYPE_DATE)
					java.sql.Date.valueOf(value);
			} else {
				if (f instanceof java.text.DecimalFormat)
					 ((java.text.DecimalFormat) f).parse(value);
				else
					 ((SalmonDateFormat) f).parse(value);
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	/**
	 * This method will return true if the passed string value if a valid entry for the column based on the String format specified for the column via the setFormat method.
	 * @see DataStoreBuffer#setFormat
	 */
	public boolean isFormattedStringValid(String column, String value) throws DataStoreException {
		int coln = getColumnIndex(column);
		return isFormattedStringValid(coln, value);
	}
	/**
	 * This removes the current row from the DataStore buffer. This will not generate a delete statment that will remove the row from the database when the update method is called.
	 * @return True if the row is remove and false if not.
	 */
	public boolean removeRow() {
		return removeRow(getRow());
	}
	/**
	 * This removes the row from the DataStore buffer. This will not generate a delete statment that will remove the row from the database when the update method is called.
	 * @param row The number of the row to remove.
	 * @return True if the row is remove and false if not.
	 */
	public boolean removeRow(int row) {
		if (row < 0)
			return false;
		waitForRow(row);
		if (row >= getRowCount())
			return false;

		if (areThereModelListeners()) {
			ModelChangedEvent evt = new ModelChangedEvent(this, getRow());
			notifyListeners(evt);
		}
		_rows.removeElementAt(row);

		if (row == getRow() && row >= getRowCount())
			_currentRow--;

		return true;
	}
	private String removeSpecialCharacters(String input) {
		if (input == null)
			return null;

		StringBuffer sb = new StringBuffer(input.length());

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c < 32 || c == '\t')
				sb.append(' ');
			else
				sb.append(c);
		}

		return sb.toString();
	}
	/**
	 * This method will clear all rows in the dataStore.
	 */
	public synchronized void reset() {
		_rows.removeAllElements();
		_rows.trimToSize();
		//
		_deletedRows.removeAllElements();
		_deletedRows.trimToSize();
		//
		_filteredRows.removeAllElements();
		_deletedRows.trimToSize();
		//
		notifyListeners(ModelChangedEvent.TYPE_RESET);
		setCurrentRow(-1);
	}
	/**
	 * This method will clear the row status flags and clear the deleted buffer in the DataStoreBuffer.
	 */
	public void resetStatus() {
		DSDataRow row;

		for (int i = 0; i < _rows.size(); i++) {
			row = (DSDataRow) _rows.elementAt(i);
            //fc 06/11/04: Only reset status of modified rows not new rows.
            if (row.getRowStatus()!=STATUS_NEW)
			    row.setRowStatus(STATUS_NOT_MODIFIED);
			row.setBucketsModified(false);
			row.copyDataToOrig();
		}
		_deletedRows.removeAllElements();
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setAny(int row, int column, Object value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_ANY);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setAny(int column, Object value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_ANY);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setAny(int row, String column, Object value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_ANY);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setAny(String column, Object value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_ANY);
	}
	/**
	 * This method will set the whether or not columns in the DatastoreBuffer should be automatically bound to components in the page (Only used for DataStoreBuffers created via the DataSource jsp tag)
	 */
	public void setAutoBind(boolean value) {
		_autoBind = value;
	}
	/**
	 * This method will set the whether or not the DatastoreBuffer should be automatically retrieved when a page is requested (Only used for DataStoreBuffers created via the DataSource jsp tag)
	 * Valid values are AUTORETRIEVE_NEVER (Don't Autoretrieve),AUTORETRIEVE_ALWAYS (Retrieve on each page request), AUTORETRIEVE_ONCHANGE (Autoretrieve if the selection criteria changes);
	 */
	public void setAutoRetrieve(int value) {
		_autoRetrieve = value;
	}
	/**
	 * This method will set the autoretrieve criteria used for the datastore.
	 */
	public void setAutoRetrieveCriteria(AutoRetrieveCriteria crit) {
		_crit = crit;
	}
	static boolean setBoolProperty(String p) {
		if (p == null)
			return false;
		else if (p.equals("true"))
			return true;
		else
			return false;
	}
	/**
	 * This method sets a flag indicating if any bucket columns (columns not representing database columns) have been modified on the specified row. Bucket column modfications aren't reflected in the row status for each row.
	 * @param row The row in the datastore buffer.
	 */
	public void setBucketsModified(int row, boolean modified) {
		if (row < 0)
			return;
		waitForRow(row);
		if (row >= getRowCount())
			return;
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		d.setBucketsModified(modified);
	}
	/**
	* This method sets a flag indicating if any bucket columns (columns not representing database columns) have been modified on the current row. Bucket column modfications aren't reflected in the row status for each row.
	*/
	public void setBucketsModified(boolean modified) {
		setBucketsModified(getRow(), modified);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setByteArray(int column, byte[] value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setByteArray(int row, int column, byte[] value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setByteArray(int row, String column, byte[] value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setByteArray(String column, byte[] value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_BYTEARRAY);
	}
	/**
	 * This method sets the specified row in the DataStore. The DataStoreRow must be compatible with the internal structure of the datastore (eg created with the getDataStoreRow method from the same datastore). To create a blank row in the datastore call the insertRow method and then getDataStoreRow.
	 * @param rowNo The number of the row to get.
	 * @param buffer The datastore buffer to look in. Valid values are BUFFER_STANDARD, BUFFER_DELETED, BUFFER_FILTERED.
	 * @param row The row to set
	 */
	public void setDataStoreRow(int rowNo, int buffer, DataStoreRow row) throws DataStoreException {
		Vector rows = null;
		if (buffer == BUFFER_STANDARD)
			rows = _rows;
		else if (buffer == BUFFER_DELETED)
			rows = _deletedRows;
		else if (buffer == BUFFER_FILTERED)
			rows = _filteredRows;
		else
			throw new DataStoreException("Buffer is invalid.");
		;

		if (rows == null)
			throw new DataStoreException("Row number:" + rowNo + " is out of range.");
		;

		if (rowNo < 0 || rowNo >= rows.size())
			throw new DataStoreException("Row number:" + rowNo + " is out of range.");
		;

		rows.setElementAt(row.getDSDataRow(), rowNo);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDate(int row, int column, java.sql.Date value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_DATE);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDate(int row, String column, java.sql.Date value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_DATE);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDate(int column, java.sql.Date value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_DATE);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setDate(String column, java.sql.Date value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_DATE);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDateTime(int row, int column, java.sql.Timestamp value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_DATETIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDateTime(int row, String column, java.sql.Timestamp value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_DATETIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDateTime(int column, java.sql.Timestamp value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_DATETIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setDateTime(String column, java.sql.Timestamp value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_DATETIME);
	}
	/**
	 * This method will set the distinct flag in the data store.
	 * @param distinct if the flag is set to true the generated select statement will begin with a select distinct.
	 */
	public void setDistinct(boolean distinct) {
		_desc.setDistinct(distinct);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDouble(int column, double value) throws DataStoreException {
		setObject(getRow(), column, new Double(value), DATATYPE_DOUBLE);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDouble(int row, int column, double value) throws DataStoreException {
		setObject(row, column, new Double(value), DATATYPE_DOUBLE);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDouble(int row, String column, double value) throws DataStoreException {
		setObject(row, column, new Double(value), DATATYPE_DOUBLE);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setDouble(String column, double value) throws DataStoreException {
		setObject(getRow(), column, new Double(value), DATATYPE_DOUBLE);
	}
	/**
	 * This method will set the expression used to find rows in the datastore. It is used in conjunction with the findNext, findPrior, findFirst, findLast and find methods.
	 * @param exp The expression to use in the find.
	 * @see DataStore#findNext
	 * @see DataStore#findPrior
	 * @see DataStore#findFirst
	 * @see DataStore#findLast
	 * @see DataStore#find
	 * @see DataStore#filter
	 * @see DataStoreEvaluator
	 */
	public void setFindExpression(String exp) throws DataStoreException {
		_find = new DataStoreEvaluator(this, exp);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setFloat(int column, float value) throws DataStoreException {
		setObject(getRow(), column, new Float(value), DATATYPE_FLOAT);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setFloat(int row, int column, float value) throws DataStoreException {
		setObject(row, column, new Float(value), DATATYPE_FLOAT);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setFloat(int row, String column, float value) throws DataStoreException {
		setObject(row, column, new Float(value), DATATYPE_FLOAT);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setFloat(String column, float value) throws DataStoreException {
		setObject(getRow(), column, new Float(value), DATATYPE_FLOAT);
	}
	/**
	 * This method sets a the format string for a date, time, datetime or numeric type column.
	 * @param col The column number to set the format for.
	 * @param format The format to use in parsing or formatting the value
	 * @see DataStore#getFormattedString
	 * @see DataStore#setFormattedString
	 */
	public void setFormat(int col, String format) throws DataStoreException {
		if (col < 0 || col >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor d = _desc.getColumn(col);

		if (d.getType() == DATATYPE_INT || d.getType() == DATATYPE_LONG || d.getType() == DATATYPE_DOUBLE || d.getType() == DATATYPE_SHORT || d.getType() == DATATYPE_FLOAT)
			d.setFormat(new java.text.DecimalFormat(format));
		else if (d.getType() == DATATYPE_DATE || d.getType() == DATATYPE_TIME || d.getType() == DATATYPE_DATETIME) {
			java.text.SimpleDateFormat df = new SalmonDateFormat(format);
			df.setLenient(false);
			d.setFormat(df);
		} else
			throw new DataStoreException("Formats may only be specified on numeric or date columns");

	}
	/**
	 * This method sets a the format string for a date, time, datetime or numeric type column.<BR><BR>
	 * Date Formats<BR><BR>
	 *  Symbol   Meaning                 Presentation        Example<BR>
	 *  ------   -------                 ------------        -------<BR>
	 *  G        era designator          (Text)              AD<BR>
	 *  y        year                    (Number)            1996<BR>
	 *  M        month in year           (Text & Number)     July & 07<BR>
	 *  d        day in month            (Number)            10<BR>
	 *  h        hour in am/pm (1~12)    (Number)            12<BR>
	 *  H        hour in day (0~23)      (Number)            0<BR>
	 *  m        minute in hour          (Number)            30<BR>
	 *  s        second in minute        (Number)            55<BR>
	 *  S        millisecond             (Number)            978<BR>
	 *  E        day in week             (Text)              Tuesday<BR>
	 *  D        day in year             (Number)            189<BR>
	 *  F        day of week in month    (Number)            2 (2nd Wed in July)<BR>
	 *  w        week in year            (Number)            27<BR>
	 *  W        week in month           (Number)            2<BR>
	 *  a        am/pm marker            (Text)              PM<BR>
	 *  k        hour in day (1~24)      (Number)            24<BR>
	 *  K        hour in am/pm (0~11)    (Number)            0<BR>
	 *  z        time zone               (Text)              Pacific Standard Time<BR>
	 *  '        escape for text         (Delimiter)<BR>
	 *  ''       single quote            (Literal)           '<BR><BR>
	 * Numeric Formats<BR><BR>
	 *  Symbol Meaning<BR>
	 *  ------ -------<BR>
	 *  0      a digit<BR>
	 *  #      a digit, zero shows as absent<BR>
	 *  .      placeholder for decimal separator<BR>
	 *  ,      placeholder for grouping separator.<BR>
	 *  ;      separates formats.<BR>
	 *  -      default negative prefix.<BR>
	 *  %      multiply by 100 and show as percentage<BR>
	 *  ?      multiply by 1000 and show as per mille<BR>
	 *  ¤      currency sign; replaced by currency symbol; if<BR>
	 *         doubled, replaced by international currency symbol.<BR>
	 *         If present in a pattern, the monetary decimal separator<BR>
	 *         is used instead of the decimal separator.<BR>
	 *  X      any other characters can be used in the prefix or suffix<BR>
	 *  '      used to quote special characters in a prefix or suffix.<BR>
	 * @param col The column name to set the format for.
	 * @param format The format to use in parsing or formatting the value
	 * @see DataStore#getFormattedString
	 * @see DataStore#setFormattedString
	 */
	public void setFormat(String col, String format) throws DataStoreException {
		int coln = getColumnIndex(col);
		setFormat(coln, format);
	}
	/**
	 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
	 * @see DataStore#setFormat
	 */
	public void setFormattedString(int row, int column, String value) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		DSColumnDescriptor d = _desc.getColumn(column);
		int colType = d.getType();
		java.text.Format f = d.getFormat();

		try {
			if (value == null)
				setObject(row, column, null, DATATYPE_ANY);
			else if (f == null)
				setAny(row, column, value);
			else {
				if (f instanceof java.text.DecimalFormat) {
					Number parsed = ((java.text.DecimalFormat) f).parse(value);
					if (colType == DATATYPE_INT)
						setInt(row, column, parsed.intValue());
					else if (colType == DATATYPE_DOUBLE)
						setDouble(row, column, parsed.doubleValue());
					else if (colType == DATATYPE_SHORT)
						setShort(row, column, parsed.shortValue());
					else if (colType == DATATYPE_LONG)
						setLong(row, column, parsed.longValue());
					else if (colType == DATATYPE_FLOAT)
						setFloat(row, column, parsed.floatValue());
				} else {
					java.util.Date dt = ((SalmonDateFormat) f).parse(value);
					GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
					cal.setTime(dt);
					int year = cal.get(Calendar.YEAR);
					int century = year / 100;
					if (century == 0) {
						GregorianCalendar c1 = (GregorianCalendar) GregorianCalendar.getInstance();
						century = c1.get(Calendar.YEAR) / 100;
						century *= 100;
						cal.set(Calendar.YEAR, century + year);
					}

					long parsed = cal.getTime().getTime();
					if (colType == DATATYPE_DATE)
						setDate(row, column, new java.sql.Date(parsed));
					else if (colType == DATATYPE_TIME)
						setTime(row, column, new java.sql.Time(parsed));
					else if (colType == DATATYPE_DATETIME)
						setDateTime(row, column, new java.sql.Timestamp(parsed));

				}
			}
		} catch (Exception e) {
			setObject(row, column, null, DATATYPE_ANY);
		}

	}
	/**
	 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
	 * @see DataStore#setFormat
	 */
	public void setFormattedString(int column, String value) throws DataStoreException {
		setFormattedString(getRow(), column, value);

	}
	/**
	 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
	 * @see DataStore#setFormat
	 */
	public void setFormattedString(int row, String column, String value) throws DataStoreException {
		int coln = getColumnIndex(column);
		setFormattedString(row, coln, value);
	}
	/**
	 * This method will set a parse a string value using the format specified in setFormat for this column and place it in the datastore if it is valid.<BR>
	 * @see DataStore#setFormat
	 */
	public void setFormattedString(String column, String value) throws DataStoreException {
		setFormattedString(getRow(), column, value);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setInt(int row, int column, int value) throws DataStoreException {
		setObject(row, column, new Integer(value), DATATYPE_INT);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setInt(int row, String column, int value) throws DataStoreException {
		setObject(row, column, new Integer(value), DATATYPE_INT);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setInt(String column, int value) throws DataStoreException {
		setObject(getRow(), column, new Integer(value), DATATYPE_INT);
	}
	static int setIntProperty(String p) {
		if (p == null)
			return -1;
		else {
			try {
				return (new Integer(p).intValue());
			} catch (Exception e) {
				return -1;
			}
		}
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setLong(int row, int column, long value) throws DataStoreException {
		setObject(row, column, new Long(value), DATATYPE_LONG);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setLong(int column, long value) throws DataStoreException {
		setObject(getRow(), column, new Long(value), DATATYPE_LONG);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setLong(int row, String column, long value) throws DataStoreException {
		setObject(row, column, new Long(value), DATATYPE_LONG);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setLong(String column, long value) throws DataStoreException {
		setObject(getRow(), column, new Long(value), DATATYPE_LONG);
	}
	private void setObject(int row, int col, Object data, int type) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor des = _desc.getColumn(col);

		if (type == DATATYPE_ANY) {
			type = getObjectType(data);
			if (type == DATATYPE_STRING && des.getType() != DATATYPE_STRING) {
				try {
					type = des.getType();
					if (type == DATATYPE_INT)
						data = Integer.valueOf((String) data);
					else if (type == DATATYPE_DOUBLE)
						data = Double.valueOf((String) data);
					else if (type == DATATYPE_FLOAT)
						data = Float.valueOf((String) data);
					else if (type == DATATYPE_SHORT)
						data = Short.valueOf((String) data);
					else if (type == DATATYPE_LONG)
						data = Long.valueOf((String) data);
					else if (type == DATATYPE_DATETIME) {
						//Correction added by Fred Cahill on 4/26/01
						//try/catch for the test to check if data passed
						//is either a date or timestamp.
						//if not timestamp then must be a date
						//therefore try to convert to timestamp.
						try {
							data = Timestamp.valueOf((String) data);
						} catch (Exception e) {
							SalmonDateFormat sdf = new SalmonDateFormat();
							data = new Timestamp(sdf.parse((String) data).getTime());
							//data = new Timestamp(Timestamp.parse((String) data));
							com.salmonllc.util.MessageLog.writeDebugMessage("setObject() - received date expecting timestamp performed conversion", this);
						}
					} else if (type == DATATYPE_TIME)
						data = java.sql.Time.valueOf((String) data);
					else if (type == DATATYPE_DATE) {
						//Correction added by Fred Cahill on 4/26/01
						//try/catch for the test to check if data passed
						//is either a date or timestamp.
						//if not date then must be a timestamp
						//therefore try to convert to date.
						try {
							data = java.sql.Date.valueOf((String) data);
						} catch (Exception e) {
							SalmonDateFormat sdf = new SalmonDateFormat();
							data = new java.sql.Date(sdf.parse((String) data).getTime());
							//data = new java.sql.Date(java.sql.Date.parse((String) data));
							com.salmonllc.util.MessageLog.writeDebugMessage("setObject() - received timestamp expecting date performed conversion", this);
						}
					}
					//					data = java.sql.Date.valueOf((String) data);
					else
						data = ((String) data).getBytes();
				} catch (Exception e) {
					data = null;
					com.salmonllc.util.MessageLog.writeErrorMessage("setObject()", e, this);
				}
			}
		}

		if (des.getType() != type && data != null)
			throw new DataStoreException("Column type mismatch.\n\tExpecting: " + getTypeDescription(des.getType()) + "\n\tPassed: " + getTypeDescription(type), row, getColumnName(col));

		DSDataRow d = (DSDataRow) _rows.elementAt(row);

		Object oldData = d.getData(col);
		d.setValue(col, data, _desc);
        if (_manytoonerelationship && row>0 && isColumnInOneToManyRelationship(getColumnName(col)))
            setObject(0,col,data,type);

		if (areThereModelListeners()) {
			if (oldData == null && data == null)
				return;
			if (oldData != null && data != null)
				if (oldData.equals(data))
					return;
			notifyListeners(new ModelChangedEvent(this, col, oldData, data));
		}
	}

	private void setObject(int row, String column, Object data, int type) throws DataStoreException {
		int col = getColumnIndex(column);
		if (col == -1) {
			MessageLog.writeInfoMessage("column : " + column + " -> NOT FOUND", this);
		}
		setObject(row, col, data, type);
	}
	/**
	 * Sets the order by clause of the DataStore's SQL Statement
	 * @param orderBy The columns to use to sort the result set.
	 */
	public void setOrderBy(String orderBy) {
		_desc.setOrderByClause(orderBy);
	}
	/**
	 * This method is used to indicate whether a column is part of the primary key
	 */
	public void setPrimaryKey(int col, boolean pkey) throws DataStoreException {
		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSColumnDescriptor c = _desc.getColumn(col);
		c.setPrimaryKey(pkey);
	}
	/**
	 * This method is used to indicate whether a column is part of the primary key
	 */
	public void setPrimaryKey(String col, boolean pkey) throws DataStoreException {
		int c = getColumnIndex(col);
		setPrimaryKey(c, pkey);
	}
	/**
	 * This method reads a properties object containing the definition of the data store and builds the datastore from it.
	 */
	public void setProperties(Properties p) {

		reset();
		java.text.DecimalFormat fmt = new java.text.DecimalFormat("0000");
		_desc = new DSDataStoreDescriptor();

		String desc = "base.";

		_desc.setDefaultTable(setStringProperty(p.getProperty(desc + "defaultTable")));
		_desc.setWhereClause(setStringProperty(p.getProperty(desc + "whereClause")));
		_desc.setGroupByClause(setStringProperty(p.getProperty(desc + "groupByClause")));
		_desc.setHavingClause(setStringProperty(p.getProperty(desc + "havingClause")));
		_desc.setOrderByClause(setStringProperty(p.getProperty(desc + "orderByClause")));
		_desc.setDistinct(setBoolProperty(p.getProperty(desc + "distinct")));
		_desc.setTrimStrings(setBoolProperty(p.getProperty(desc + "trimStrings")));

		//columns
		int i = 0;
		String name = "col." + fmt.format(i) + ".";
		while (p.getProperty(name + "column") != null) {
			DSColumnDescriptor d = new DSColumnDescriptor();
			d.setTable(setStringProperty(p.getProperty(name + "table")));
			d.setColumn(setStringProperty(p.getProperty(name + "column")));
			d.setType(setIntProperty(p.getProperty(name + "type")));
			d.setPrimaryKey(setBoolProperty(p.getProperty(name + "pkey")));
			d.setUpdateable(setBoolProperty(p.getProperty(name + "update")));
			d.setConcurrency(setBoolProperty(p.getProperty(name + "concurrency")));
			d.setUseBind(setIntProperty(p.getProperty(name + "useBind")));
			d.setInternalName(setStringProperty(p.getProperty(name + "name")));
			String format = setStringProperty(p.getProperty(name + "format"));
			if (format != null) {
				if (d.getType() == DATATYPE_INT || d.getType() == DATATYPE_LONG || d.getType() == DATATYPE_DOUBLE || d.getType() == DATATYPE_SHORT || d.getType() == DATATYPE_FLOAT)
					d.setFormat(new java.text.DecimalFormat(format));
				else if (d.getType() == DATATYPE_DATE || d.getType() == DATATYPE_TIME || d.getType() == DATATYPE_DATETIME) {
					java.text.SimpleDateFormat df = new SalmonDateFormat(format);
					df.setLenient(false);
					d.setFormat(df);
				}
			}

			_desc.addColumn(d);
			i++;
			name = "col." + fmt.format(i) + ".";
		}

		//joins
		i = 0;
		name = "join." + fmt.format(i) + ".";
		while (p.getProperty(name + "outer") != null) {
			_desc.addJoin(setStringProperty(p.getProperty(name + "left")), setStringProperty(p.getProperty(name + "right")), setBoolProperty(p.getProperty(name + "outer")));
			i++;
			name = "join." + fmt.format(i) + ".";
		}

		//table aliases
		i = 0;
		name = "tableAlias." + fmt.format(i) + ".";
		while (p.getProperty(name + "table") != null) {
			_desc.addAlias(setStringProperty(p.getProperty(name + "table")), setStringProperty(p.getProperty(name + "alias")));
			i++;
			name = "tableAlias." + fmt.format(i) + ".";
		}

	}
	/**
	 * This method will set the rows in the DataStore. The vector set should be created via the getRows method of this datastore.
	 */
	public void setRows(Vector rows) {
		_rows = (Vector) rows.elementAt(0);
		_filteredRows = (Vector) rows.elementAt(1);
		_deletedRows = (Vector) rows.elementAt(2);
	}
	/**
	 * This method sets the status flag of the current row.
	 * @param status (STATUS_NOT_MODIFIED,STATUS_MODIFIED,STATUS_NEW,STATUS_NEW_MODIFIED)
	 * @return True if the row is in the buffer and false if not
	 */
	public boolean setRowStatus(int status) {
		return setRowStatus(getRow(), status);
	}
	/**
	 * This method sets the status flag of the specified row.
	 * @param row The row in the datastore buffer.
	 * @param status (STATUS_NOT_MODIFIED,STATUS_MODIFIED,STATUS_NEW,STATUS_NEW_MODIFIED)
	 * @return True if the row is in the buffer and false if not
	 */
	public boolean setRowStatus(int row, int status) {
		if (row < 0)
			return false;
		waitForRow(row);
		if (row >= getRowCount())
			return false;
		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		d.setRowStatus(status);
		return true;
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setShort(int row, int column, short value) throws DataStoreException {
		setObject(row, column, new Short(value), DATATYPE_SHORT);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setShort(int row, String column, short value) throws DataStoreException {
		setObject(row, column, new Short(value), DATATYPE_SHORT);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setShort(int column, short value) throws DataStoreException {
		setObject(getRow(), column, new Short(value), DATATYPE_SHORT);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setShort(String column, short value) throws DataStoreException {
		setObject(getRow(), column, new Short(value), DATATYPE_SHORT);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setString(int row, int column, String value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_STRING);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setString(int column, String value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_STRING);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setString(int row, String column, String value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_STRING);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setString(String column, String value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_STRING);
	}
	static String setStringProperty(String p) {
		if (p == null)
			return null;
		else if (p.equals("null"))
			return null;
		else
			return p;
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setTime(int row, int column, java.sql.Time value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_TIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setTime(int row, String column, java.sql.Time value) throws DataStoreException {
		setObject(row, column, value, DATATYPE_TIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	 * @param column The column for the value to set.
	 * @param value The data to place in the column.
	 */
	public void setTime(int column, java.sql.Time value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_TIME);
	}
	/**
	 * This method sets a value in the data store's internal buffer at the current row.
	  * @param column The column for the value to set.
	 * @param value The data to place in the column
	 */
	public void setTime(String column, java.sql.Time value) throws DataStoreException {
		setObject(getRow(), column, value, DATATYPE_TIME);
	}
	/**
	 * This method will sort the rows in the data store on an array of columns.
	 * @param col A array of column numbers to sort on.
	 * @param dir An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
	 */
	public void sort(int[] col, int dir[]) throws DataStoreException {
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTING);
		for (int i = 0; i < col.length; i++) {
			if (col[i] < 0 || col[i] >= _desc.getColumnCount())
				throw new DataStoreException("Sort error:column not found");
		}

		waitForRetrieve();
		new DSQuickSort(_rows, col, dir);
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTED);
	}
	/**
	 * This method will sort the rows in the data store on an array of columns.
	 * @param col A array of column numbers to sort on.
	 * @param dir An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
	 * @param bUseQSAlgoritm A boolean to indicate to use only the Quick Sort Algoritm. No use of Hash Maps.
	 */
	public void sort(int[] col, int dir[], boolean bUseQSAlgoritm) throws DataStoreException {
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTING);
		for (int i = 0; i < col.length; i++) {
			if (col[i] < 0 || col[i] >= _desc.getColumnCount())
				throw new DataStoreException("Sort error:column not found");
		}

		waitForRetrieve();
		new DSQuickSort(_rows, col, dir, bUseQSAlgoritm);
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTED);
	}
	/**
	 * This method will sort the rows in the data store on an array of columns.
	 * @param obj A array of objects to sort on. The array can contain Strings (column name), Integers (column numbers) or DataStoreEvaluators (expressions)
	 * @param dir An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
	 */
	public void sort(Object[] obj, int dir[]) throws DataStoreException {
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTING);
		Object sort[] = new Object[obj.length];

		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof DataStoreEvaluator)
				sort[i] = obj[i];
			else if (obj[i] instanceof String) {
				int index = getColumnIndex((String) obj[i]);
				if (index < 0)
					throw new DataStoreException("Sort error:column not found");
				sort[i] = new Integer(index);
			} else if (obj[i] instanceof Integer) {
				int colNo = ((Integer) obj[i]).intValue();
				if (colNo < 0 || colNo >= _desc.getColumnCount())
					throw new DataStoreException("Sort error:column not found");
				sort[i] = obj[i];
			} else
				throw new DataStoreException("Sort error:column not found");
		}
		waitForRetrieve();
		new DSQuickSort(_rows, sort, dir);
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTED);
	}
	/**
	 * This method will sort the rows in the data store on an array of columns.
	 * @param col A String array of column names to sort on.
	 * @param dir An integer array of the direction to sort on. Each element can contain either SORT_ASC or SORT_DESC.
	 */
	public void sort(String col[], int dir[]) throws DataStoreException {
		int[] c = new int[col.length];

		for (int i = 0; i < c.length; i++) {
			c[i] = getColumnIndex(col[i]);
		}
		sort(c, dir);

	}
	/**
	 * This method will sort the rows in the data store on a particular column.
	 * @param col The column number of the column to sort on.
	 * @param dir The direction to sort on. Either SORT_ASC or SORT_DESC.
	 */
	public void sort(int col, int dir) throws DataStoreException {
		int cols[] = new int[1];
		int dirs[] = new int[1];
		cols[0] = col;
		dirs[0] = dir;
		sort(cols, dirs);
	}
	/**
	 * This method will sort the rows in the data store using an expression.
	 * @param eval A datastore evaluator containing the expression.
	 * @param dir An integer indicating the sort direction valid values are SORT_ASC or SORT_DESC.
	 */
	public void sort(DataStoreEvaluator eval, int dir) throws DataStoreException {
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTING);
		waitForRetrieve();
		new DSQuickSort(_rows, eval, dir);
		notifyListeners(ModelChangedEvent.TYPE_DATA_SORTED);
	}
	/**
	 * This method will sort the rows in the data store on a particular column.
	 * @param col The name of the column to sort on.
	 * @param dir The direction to sort on. Either SORT_ASC or SORT_DESC.
	 */
	public void sort(String col, int dir) throws DataStoreException {
		String cols[] = new String[1];
		int dirs[] = new int[1];
		cols[0] = col;
		dirs[0] = dir;
		sort(cols, dirs);
	}
	/**
	 * This method will take a row from the datastores deleted buffer and move it back to the standard buffer.
	 * @param row The number of the row to undelete. Note: this is the row number of the row in the deleted buffer not the standard buffer.
	 * @return The number that the deleted row was moved to in the standard buffer or -1 if an error occurs.
	 */
	public int unDeleteRow(int row) {
		if (row < 0)
			return -1;
		if (row >= getDeletedCount())
			return -1;
		DSDataRow d = (DSDataRow) _deletedRows.elementAt(row);
		_deletedRows.removeElementAt(row);
		_rows.addElement(d);

		if (areThereModelListeners()) {
			ModelChangedEvent evt = new ModelChangedEvent(this, getRow());
			notifyListeners(evt);
		}

		return _rows.size() - 1;
	}
	/**
	 * This method will block until a datastore cancel command is finished
	 */
	public void waitForCancel() {
		Thread waitForCancelThread = null;
		try {
			while (_cancelInProgress) {
				waitForCancelThread = Thread.currentThread();
				_waitingCancelThreads.addElement(waitForCancelThread);
				try {
					Thread.sleep(1000 * 60 * 60);
				} catch (InterruptedException ie) {
					;
				}
			}
		} catch (Exception e) {
		}
		if (waitForCancelThread != null)
			_waitingCancelThreads.remove(waitForCancelThread);
	}
	/**
	 * This method will block until all the data from the last retrieve method call has been loaded into the DataStore's internal buffer.
	 */
	public void waitForRetrieve() {
		Thread waitForRetrieveThread = null;
		try {
			while (_retrieveInProgress) {
				waitForRetrieveThread = Thread.currentThread();
				_waitingRetrieveThreads.addElement(waitForRetrieveThread);
				try {
					Thread.sleep(1000 * 60 * 60);
				} catch (InterruptedException ie) {
					;
				}
			}
		} catch (Exception e) {
		}
		if (waitForRetrieveThread != null)
			_waitingRetrieveThreads.remove(waitForRetrieveThread);
	}

	/**
	 * Waits for the retrieve process to finish for the period specified in seconds.
	 */
	public void waitForRetrieve(int iSeconds) {
		Thread waitForRetrieveThread = null;
		try {
			while (_retrieveInProgress) {
				waitForRetrieveThread = Thread.currentThread();
				_waitingRetrieveThreads.addElement(waitForRetrieveThread);
				try {
					Thread.sleep(1000 * iSeconds);
				} catch (InterruptedException ie) {
					;
				}
			}
		} catch (Exception e) {
		}
		if (waitForRetrieveThread != null)
			_waitingRetrieveThreads.remove(waitForRetrieveThread);
	}

	/**
	 * This method was created in VisualAge.
	 * @param row int
	 */
	protected void waitForRow(int row) {
		try {
			while (_retrieveInProgress && (row >= _rows.size())) {
				Thread.yield();
			}
		} catch (Exception e) {
		}
	}
	/**
	 * This method was created in VisualAge.
	 * @param out java.io.ObjectOutputStream
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
		waitForRetrieve();
		out.defaultWriteObject();
	}

	/**
	* Used to set the remote id for the datastore
	*/
	public void setRemoteID(String remoteID) {
		_remoteID = remoteID;
	}

	/**
	 * Used to get the remote id for the datastore
	 */
	public String getRemoteID() {
		return _remoteID;
	}

	/**
	* This method will return the value that will be returned to the proxy datastore after an remote update.
	*/
	public String getRemoteUpdateReturnValue() {
		return _remoteUpdateRetVal;
	}
	/**
	* This method will return the value that will be returned to the a proxy datastore after a remote update.
	*/
	public void setRemoteUpdateReturnValue(String update) {
		_remoteUpdateRetVal = update;
	}

	/**
	 * Sets the _currentRow.
	 * @param currentRow The _currentRow to set
	 */
	protected void setCurrentRow(int currentRow) {
		int oldRow = _currentRow;
		_currentRow = currentRow;
		if (areThereModelListeners() && oldRow != currentRow) {
			ModelChangedEvent evt = new ModelChangedEvent(this, oldRow, _currentRow);
			notifyListeners(evt);
		}

	}

	/**
	 * Adds a new listerner to this datastore to be notified when the model changes in some way
	 */
	public void addModelChangedListener(ModelChangedListener l) {
		if (_modelListeners == null)
			_modelListeners = new Vector();

		for (int i = 0; i < _modelListeners.size(); i++) {
			if (((ModelChangedListener) _modelListeners.elementAt(i)) == l)
				return;
		}

		_modelListeners.addElement(l);
	}

	/**
	* This method removes a listener from the list of listeners that will be notified when a model changed event is fired.
	*/
	public void removeModelChangedListener(ModelChangedListener l) {
		if (_modelListeners == null)
			return;

		for (int i = 0; i < _modelListeners.size(); i++) {
			if (((ModelChangedListener) _modelListeners.elementAt(i)) == l) {
				_modelListeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * Notifies all listeners that a model changed event occurred
	 */
	public void notifyListeners(ModelChangedEvent e) {
		if (_modelListeners == null)
			return;

		for (int i = 0; i < _modelListeners.size(); i++) {
			((ModelChangedListener) _modelListeners.elementAt(i)).modelChanged(e);
		}
	}

	protected void notifyListeners(int type) {
		if (_modelListeners == null)
			return;

		ModelChangedEvent e = new ModelChangedEvent(type, this);

		notifyListeners(e);
	}

	protected boolean areThereModelListeners() {
		return (_modelListeners != null);
	}

	protected Vector getModelListeners() {
		return _modelListeners;
	}

	/**
	 * Sets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 * @param data The data to place in the temporary column.
	 */
	private void setTempValue(int row, String column, String data) throws DataStoreException {
		int col = getColumnIndex(column);
		if (col == -1) {
			MessageLog.writeInfoMessage("column : " + column + " -> NOT FOUND", this);
		}
		setTempValue(row, col, data);
	}
	/**
	 * Sets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	 * @param row The row number for the value to set.
	 * @param col The column for the value to set.
	 * @param value The data to place in the temporary column.
	 */
	public void setTempValue(int row, int col, String value) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		if (col < 0 || _desc.getColumnCount() == 0)
			throw new DataStoreException("Specified column (" + col + ") does not exist.");

		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		d.setTempValue(col, value, _desc);
		if (areThereModelListeners())
			notifyListeners(new ModelChangedEvent(this, col, getAny(row, col), value));

	}

	/**
	 * Sets a temporary value in the buffer for the current row. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	 * @param col The column for the value to set.
	 * @param value The data to place in the temporary column.
	 */
	public void setTempValue(int col, String value) throws DataStoreException {
		setTempValue(getRow(), col, value);
	}

	/**
	 * Sets a temporary value in the buffer for the current row. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	 * @param col The column for the value to set.
	 * @param value The data to place in the temporary column.
	 */
	public void setTempValue(String col, String value) throws DataStoreException {
		setTempValue(getRow(), col, value);
	}

	/**
	 * Gets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	 * @param row The row number for the value to set.
	 * @param column The column for the value to set.
	 */
	public String getTempValue(int row, int column) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");

		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		return d.getTempValue(column);
	}

	/**
	* Gets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	* @param row The row number for the value to set.
	* @param column The column for the value to set.
	*/
	public String getTempValue(int row, String column) throws DataStoreException {
		int col = getColumnIndex(column);
		if (col == -1) {
			MessageLog.writeInfoMessage("column : " + column + " -> NOT FOUND", this);
		}
		return getTempValue(row, col);
	}

	/**
	* Gets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	* @param column The column to get the value for
	*/
	public String getTempValue(String column) throws DataStoreException {
		return getTempValue(getRow(), column);
	}

	/**
	* Gets a temporary value in the buffer. A temporary value in one that doesn't effect the interaction with the datasource and will get cleared whenever a actual value is set on the column. It's intended purpose is to provide an area where invalid values entered by users can be safely stored for later processing without effecting the clean data in the buffer.
	* @param column The column to get the value for
	*/
	public String getTempValue(int column) throws DataStoreException {
		return getTempValue(getRow(), column);
	}

	/**
	* Clears all temporary values from the specified row
	* @param row The row number to clear.
	*/
	public void clearTempValues(int row) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		d.clearTempValues();
	}

	/**
	* Clears all temporary values from the whole datastore
	*/
	public void clearTempValues() throws DataStoreException {
		for (int i = 0; i < getRowCount(); i++) {
			DSDataRow d = (DSDataRow) _rows.elementAt(i);
			d.clearTempValues();
		}
	}

	/**
	* Returns true if the specified row has temporary values
	* @param row The row number to check
	*/
	public boolean hasTempValues(int row) throws DataStoreException {
		waitForRow(row);
		if (row < 0 || row >= _rows.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		DSDataRow d = (DSDataRow) _rows.elementAt(row);
		return d.hasTempValues();
	}

	/**
	 * Adds a validation rule to the datastore for the specified column
	 */
	int addValidationRule(int column, ValidationRule rule) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");
		return _desc.getColumn(column).addRule(rule);
	}

	/**
	 * Adds a validation rule to the datastore for the specified column
	 */
	int addValidationRule(String column, ValidationRule rule) throws DataStoreException {
		return addValidationRule(_desc.getColumnIndex(column), rule);
	}

	/**
	 * Use this method to add validation rules that will be checked by the validator.
	 * @param column The name of the column to validate.
	 * @param expression A DataStoreExpression class that returns a boolean value. It will trigger an error if the value returns false.
	 * @param errorMessage The error message to display if the validation fails.
	 * @param executeOnServer Whether or not this rule should evaluate on the server for proxy datastores. Otherwise the expression object will be serialized and copied to the client to execute there.
	 */
	public void addExpressionRule(String column, DataStoreExpression expression, String errorMessage, boolean executeOnServer) throws DataStoreException {
		addValidationRule(column, new ValidationRule(expression, errorMessage, executeOnServer));
	}

	/**
	* Use this method to add validation rules that will be checked by the validator.
	* @param column The name of the column to validate.
	* @param expression A DataStore expression  that returns a boolean value. It will trigger an error if the value returns false.
	* @param errorMessage The error message to display if the validation fails.
	*/
	public void addExpressionRule(String column, String expression, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(expression, errorMessage));
	}

	/**
	* Use this method to add validation rules that will be checked by the validator.
	* @param column The name of the column to validate.
	* @param expression A DataStoreExpression class that returns a boolean value. It will trigger an error if the value returns false.
	* @param errorMessage The error message to display if the validation fails.
	* @param executeOnServer Whether or not this rule should evaluate on the server for proxy datastores. Otherwise the expression object will be serialized and copied to the client to execute there.
	*/
	public void addExpressionRule(int column, DataStoreExpression expression, String errorMessage, boolean executeOnServer) throws DataStoreException {
		addValidationRule(column, new ValidationRule(expression, errorMessage, executeOnServer));
	}

	/**
	* Use this method to add validation rules that will be checked by the validator.
	* @param column The name of the column to validate.
	* @param expression A DataStore expression  that returns a boolean value. It will trigger an error if the value returns false.
	* @param errorMessage The error message to display if the validation fails.
	*/
	public void addExpressionRule(int column, String expression, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(expression, errorMessage));
	}

	/**
	* Adds a range rule to the DataStore buffer
	* @param column The name of the column to validate.
	* @param minValue The minimum value to allow in the column. Null to allow any minimum value.
	* @param maxValue The maximum value to allow in the column. Null to allow any maximum value.
	* @param errorMessage The error message to display if the validation fails.
	*/
	public void addRangeRule(int column, Object minValue, Object maxValue, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(minValue,maxValue, errorMessage));
	}

	/**
	* Adds a range rule to the DataStore buffer
	* @param column The name of the column to validate.
	* @param minValue The minimum value to allow in the column. Null to allow any minimum value.
	* @param maxValue The maximum value to allow in the column. Null to allow any maximum value.
	* @param errorMessage The error message to display if the validation fails.
	*/
	public void addRangeRule(String column, Object minValue, Object maxValue, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(minValue,maxValue, errorMessage));
	}

	/**
	 * Adds a type check rule to the validator. This makes sure that the value in temp area for the column is of the correct datatype.
	 * @param column The column to validate
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addTypeCheckRule(String column, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(errorMessage));
	}

	/**
	 * Adds a type check rule to the validator. This makes sure that the value in temp area for the column is of the correct datatype.
	 * @param column The column to validate
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addTypeCheckRule(int column, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(errorMessage));
	}

	/**
	 * Adds a validation rule to indicate a value is required
	 * @param column The column to validate
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addRequiredRule(String column, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(errorMessage, ValidationRule.TYPE_REQUIRED));
	}

	/**
	 * Adds a validation rule to indicate a value is required
	 * @param column The column to validate
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addRequiredRule(int column, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(errorMessage, ValidationRule.TYPE_REQUIRED));
	}

	/**
	 * Adds a regular expression rule. The specified column must match the regular expression to be valid
	 * @param column The column to validate
	 * @param regExp The regular expression to match
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addReqularExpressionRule(String column, String regExp, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(regExp, errorMessage, ValidationRule.TYPE_REGULAR_EXPRESSION));
	}

	/**
	 * Adds a regular expression rule. The specified column must match the regular expression to be valid
	 * @param column The column to validate
	 * @param regExp The regular expression to match
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addReqularExpressionRule(int column, String regExp, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(regExp, errorMessage, ValidationRule.TYPE_REGULAR_EXPRESSION));
	}

	/**
	 * Adds a javascript rule. Since javascript must be executed on a browser, and a this is a server side component, the rule won't be executed in this component. This method allows the rule to be placed here in case the datastore's rules are imported into an HtmlValidatorText component which can execute javascript rules.
	 * @param column The column to validate
	 * @param javaScript The javascript to execute. The javascript may contain replaceable parameters beginning with a % sign that are translated into the proper component names in the javascript. For example %comp1 would represent a form component on the page called comp1.
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addJavaScriptRule(String column, String javaScript, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(javaScript, errorMessage, ValidationRule.TYPE_JAVASCRIPT));
	}

	/**
	 * Adds a javascript rule. Since javascript must be executed on a browser, and a this is a server side component, the rule won't be executed in this component. This method allows the rule to be placed here in case the datastore's rules are imported into an HtmlValidatorText component which can execute javascript rules.
	 * @param column The column to validate
	 * @param javaScript The javascript to execute. The javascript may contain replaceable parameters beginning with a % sign that are translated into the proper component names in the javascript. For example %comp1 would represent a form component on the page called comp1.
	 * @param errorMessage The error message to display if the validation fails
	 */
	public void addJavaScriptRule(int column, String javaScript, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(javaScript, errorMessage, ValidationRule.TYPE_JAVASCRIPT));
	}

	/**
	 * Creates a lookup rule
	 * @param lookupTable The name of the table to lookup the value against.
	 * @param searchExpression A DataStore Expression that returns a String and will be used as the where clause for the SQL that will validate the data.
	 * @param errorMessage The error message to display if the error expression returns false.
	 * @param descriptionColumn The name of the column in the lookup table used to fill in the description.
	 * @param descriptionBucket The name of a bucket column in the datastore to place the description.
	 * @param errorMessage The message to return if the rule is violated
	 */
	public void addLookupRule(String column, String lookupTable, String searchExpression, String descriptionColumn, String descriptionBucket, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(lookupTable, searchExpression, descriptionColumn, descriptionBucket, errorMessage));
	}

	/**
	* Creates a lookup rule
	* @param lookupTable The name of the table to lookup the value against.
	* @param searchExpression A DataStore Expression that returns a String and will be used as the where clause for the SQL that will validate the data.
	* @param errorMessage The error message to display if the error expression returns false.
	* @param descriptionColumn The name of the column in the lookup table used to fill in the description.
	* @param descriptionBucket The name of a bucket column in the datastore to place the description.
	* @param errorMessage The message to return if the rule is violated
	*/
	public void addLookupRule(int column, String lookupTable, String searchExpression, String descriptionColumn, String descriptionBucket, String errorMessage) throws DataStoreException {
		addValidationRule(column, new ValidationRule(lookupTable, searchExpression, descriptionColumn, descriptionBucket, errorMessage));
	}

	/**
	* Clears all validation rules for the specified column
	*/
	public void clearValidationRules(int column) throws DataStoreException {
		if (column < 0 || column >= _desc.getColumnCount())
			throw new DataStoreException("Specified column (" + column + ") is out of range.");
		_desc.getColumn(column).clearValidationRules();
	}

	/**
	* Clears all validation rules for the specified column
	*/
	public void clearValidationRules(String column) throws DataStoreException {
		clearValidationRules(_desc.getColumnIndex(column));
	}

	/**
	 * Clears all validations rules from all columns in the DataStore
	 */
	public void clearAllValidationRules() {
		try {
			for (int i = 0; i < getColumnCount(); i++)
				clearValidationRules(i);
		} catch (Exception e) {
		}
	}

	/**
	 * Validates all rows in the datastore that are candidates for insert or update. stopAtFIrstError is an indication to stop the validation after the first error is found.
	 */
	public DataStoreException[] validateRowsToUpdate(DBConnection conn, boolean stopAtFirstError) {
		Vector v = new Vector();
		for (int i = 0; i < getRowCount(); i++) {
			if (getRowStatus(i) == STATUS_MODIFIED || getRowStatus() == STATUS_NEW_MODIFIED) {
				for (int j = 0; j < getColumnCount(); j++) {
					validateColumn(i, j, v, conn);
					if (v.size() > 0 && stopAtFirstError) {
						DataStoreException ret[] = new DataStoreException[1];
						ret[0] = (DataStoreException) v.elementAt(0);
						return ret;
					}
				}
			}
		}
		DataStoreException ret[] = new DataStoreException[v.size()];
		v.copyInto(ret);
		return ret;
	}
	/**
	 * Validates an entire row in the DataStore. Returns an array of exceptions that will be empty if there are no errors.
	 */
	public DataStoreException[] validateRow(int rowNo, DBConnection conn) {
		Vector v = new Vector();
		for (int i = 0; i < getColumnCount(); i++)
			validateColumn(rowNo, i, v, conn);
		DataStoreException ret[] = new DataStoreException[v.size()];
		v.copyInto(ret);
		return ret;
	}

	/**
	* Executes all the validation rules for the specified column and row. Returns an array of exceptions that will be empty if there are no errors.
	*/
	public DataStoreException[] validateColumn(int rowNo, String colName, DBConnection conn) {
		return validateColumn(rowNo, getColumnIndex(colName), conn);
	}
	/**
	 * Executes all the validation rules for the specified column and row. Returns an array of exceptions that will be empty if there are no errors.
	 */
	public DataStoreException[] validateColumn(int rowNo, int colNo, DBConnection conn) {
		Vector v = new Vector();
		validateColumn(rowNo, colNo, v, conn);
		DataStoreException ret[] = new DataStoreException[v.size()];
		v.copyInto(ret);
		return ret;
	}

	/**
	 * Returns a list of all validation rules for each column or null if there aren't any
	 */
	public ValidationRule[] getValidationRulesForColumn(int colNo) {
		if (colNo < 0 || colNo >= getColumnCount())
			return null;
		DSColumnDescriptor col = _desc.getColumn(colNo);
		if (col.getRuleCount() == 0)
			return null;
		ValidationRule[] ret = new ValidationRule[col.getRuleCount()];
		for (int i = 0; i < col.getRuleCount(); i++) {
			ret[i] = col.getRule(i);
		}
		return ret;
	}

	protected void validateColumn(int rowNo, int colNo, Vector v, DBConnection conn) {
		if (rowNo < 0 || rowNo >= getRowCount() || colNo < 0 || colNo >= getColumnCount())
			return;
        //fc 06/11/04: Dont validate the column if its a master column not from the first row.
        try {
            if (_manytoonerelationship && rowNo>0 && isColumnInOneToManyRelationship(getColumnName(colNo)))
              return;
        }
        catch (DataStoreException ex) {
          ;
        }
		DSColumnDescriptor col = _desc.getColumn(colNo);
		for (int i = 0; i < col.getRuleCount(); i++) {
			try {
				col.getRule(i).evaluateRule(this, rowNo, colNo, conn);
			} catch (DataStoreException ex) {
				ex.setRowNo(rowNo);
				try {
					ex.setColumn(getColumnName(colNo));
				} catch (DataStoreException e) {
				};
				v.add(ex);
			}
		}
	}

	protected synchronized void interruptWaitingRetrieveThreads() {
		for (int i = 0; i < _waitingRetrieveThreads.size(); i++) {
			((Thread) _waitingRetrieveThreads.elementAt(i)).interrupt();
		}
		_waitingRetrieveThreads.removeAllElements();
	}

	protected synchronized void interruptWaitingCancelThreads() {
		for (int i = 0; i < _waitingCancelThreads.size(); i++) {
			((Thread) _waitingCancelThreads.elementAt(i)).interrupt();
		}
		_waitingCancelThreads.removeAllElements();
	}

	/**
	 * For Systems that don't allow null values in columns, specify the value to use in place of null for each datastore datatype. Whenever the datastore encounters a null value for a column it will replace it with real value for the appropriate type
	 * @param dataType One of the datatype constants, ex: DATATYPE_STRING, DATATYPE_INT, etc..
	 * @param defaultValue The value to use to replace null values set in the datastore
	 */
	public void setNullDefault(int dataType, Object defaultValue) {
		_desc.setNullDefault(dataType, defaultValue);
	}

	/**
	 * Returns the value to use in place of null for a particular datatype
	 * @param dataType One of the datatype constants, ex: DATATYPE_STRING, DATATYPE_INT, etc..
	 */
	public Object getNullDefault(int dataType) {
		return _desc.getNullDefault(dataType);
	}

	/**
	 * @return the language preferences object for the datastore
	 */
	public LanguagePreferences getLang() {
		return _lang;
	}

	/**
	 * @param sets a language preference for this datastore. The datastore implementation doesn't use it, but classes extending the datastore may find it useful
	 */
	public void setLang(LanguagePreferences preferences) {
		_lang = preferences;
	}

	private boolean compare(Object com1, Object com2) {
		if ((com1 == null) && (com2 == null)) {
			return false;
		} else if ((com1 == null) && (com2 != null)) {
			return false;
		} else if ((com1 != null) && (com2 == null)) {
			return true;
		} else if (com1 instanceof String) {
			if (!(com2 instanceof String))
			  return false;
			int comp = ((String) com1).toUpperCase().compareTo(((String) com2).toUpperCase());
			if (comp == 0)
				return false;
			else if (comp < 0)
				return false;
			else
				return true;
		} else if (com1 instanceof Number) {
			if (!(com2 instanceof Number))
				return false;
			double v1 = ((Number) com1).doubleValue();
			double v2 = ((Number) com2).doubleValue();
			if (v1 == v2)
				return false;
			else if (v1 < v2)
				return false;
			else
				return true;
		} else if (com1 instanceof Timestamp) {
			if (! (com2 instanceof Timestamp))
			  return false;

			if (((Timestamp) com1).equals((Timestamp) com2))
				return false;
			else if (((Timestamp) com1).before((Timestamp) com2))
				return false;
			else
				return true;
		} else if (com1 instanceof java.sql.Date) {
			if (! (com2 instanceof java.sql.Date))
			  return false;
			if (com1.equals(com2))
				return false;
			else if (((java.sql.Date) com1).before((java.sql.Date) com2))
				return false;
			else
				return true;
		} else if (com1 instanceof java.sql.Time) {
			if (! (com2 instanceof java.sql.Time))
			  return false;

			if (com1.equals(com2))
				return false;
			else if (((java.sql.Time) com1).before((java.sql.Time) com2))
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * Returns true if the value passed is greater then the value in the specified datastore column
	 */
	public boolean valueGreater(int row, int col, Object value) throws DataStoreException {
		Object com1 = value;
		Object com2 = getAny(row, col);
		return compare(com2,com1);
	}

	/**
	 * Returns true if the value passed is less then the value in the specified datastore column
	 */
	public boolean valueLess(int row, int col, Object value) throws DataStoreException {
		Object com1 = value;
		Object com2 = getAny(row, col);
		return compare(com1,com2);
	}

	/**
	 * Returns true if the value passed is less then the value in the specified datastore column
	 */
	public boolean valueEqual(int row, int col, Object value) throws DataStoreException {
		Object com1 = value;
		Object com2 = getAny(row, col);
		if (compare(com2,com1))
			return false;
		else if (compare(com1,com2))
			return false;
		else
			return true;
	}

	/**
		 * This method will fix quotes in a string constant so it can be used in a SQL Statement
		 */
		public static String fixQuote(String data, int dataType) {
			if (dataType != DATATYPE_STRING || data == null)
				return data;

			StringBuffer buf = null;
			for (int i = 0; i < data.length(); i++) {
				char c = data.charAt(i);
				if (c == '\'' || c == '\"') {
					if (buf == null) {
						buf = new StringBuffer(data.length());
						buf.append(data.substring(0, i));
					}
					int ic = c;
					buf.append("' + char(");
					buf.append(ic);
					buf.append(") + '");
				} else if (buf != null) {
					buf.append(c);
				}
			}

			if (buf == null)
				return data;
			else
				return buf.toString();

		}

		/**
		 * This method will fix quotes in a string constant so it can be used in a SQL Statement
		 */
		public static String fixQuote(String data, int dataType, String connectionType) {
			if (dataType != DATATYPE_STRING || data == null)
				return data;

			boolean mySQLConn = connectionType.equals(DBConnection.MYSQL_CONNECTION);
			//if (!connectionType.startsWith(DBConnection.DB2_CONNECTION) && !connectionType.equals(DBConnection.ORACLE_CONNECTION) && !mySQLConn)
			//    return fixQuote(data, dataType);
			if (!connectionType.startsWith(DBConnection.DB2_CONNECTION) && !connectionType.equals(DBConnection.ORACLE_CONNECTION) && !connectionType.equals(DBConnection.POSTGRES_CONNECTION) && !mySQLConn)
				return fixQuote(data, dataType);

			String append = "''";
			if (mySQLConn)
				append = "\\'";

			StringBuffer buf = null;
			for (int i = 0; i < data.length(); i++) {
				char c = data.charAt(i);
				if (c == '\'') {
					if (buf == null) {
						buf = new StringBuffer(data.length());
						buf.append(data.substring(0, i));
					}
					buf.append(append);
				} else if (buf != null) {
					buf.append(c);
				}
			}

			if (buf == null)
				return data;
			else
				return buf.toString();

		}

	/**
	 * Resets the current row pointer to an undefined row
	 */
	public void clearSelectedRow() {
		_currentRow = -1;
	}

	/**
	 * Restores a row to the state it was when it was first loaded from the database
	 */
	public void undoChanges(int row) {
		if (row < 0 || row >= getRowCount())
			return;
		getDataRow(row).undoChanges();
	}
	/**
	 * Converts a string to a DataStoreBuffer object type using the type of the specified column
	 * @throws ParseException
	 */

	public Object convertValue(String val, String columnName) throws DataStoreException {
			int type = getColumnDataType(columnName);
			try {
				return convertValue(val,type);
			} catch (ParseException e) {
				throw new DataStoreException("Parse Error converting value " + val + " for column " + columnName,e);
			}
	}
	/**
	 * Converts a string to a DataStoreBuffer object type
	 * @throws ParseException
	 */
	public static Object convertValue(String val, int dataType) throws ParseException {
		if (val == null)
			return val;
		if (dataType == DataStoreBuffer.DATATYPE_STRING)
			return val;
		if (dataType == DataStoreBuffer.DATATYPE_LONG)
			return new Long(Long.parseLong(val));
		if (dataType == DataStoreBuffer.DATATYPE_SHORT)
			return new Short(Short.parseShort(val));
		if (dataType == DataStoreBuffer.DATATYPE_INT)
			return new Integer(Integer.parseInt(val));
		if (dataType == DataStoreBuffer.DATATYPE_DOUBLE)
			return new Double(Double.parseDouble(val));
		if (dataType == DataStoreBuffer.DATATYPE_FLOAT)
			return new Double(Float.parseFloat(val));
		if (dataType == DataStoreBuffer.DATATYPE_DATE)
			return new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(val).getTime());
		if (dataType == DataStoreBuffer.DATATYPE_TIME)
			return new java.sql.Time(new SimpleDateFormat("hh:mm").parse(val).getTime());
		if (dataType == DataStoreBuffer.DATATYPE_DATETIME)
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(val).getTime());

		return null;
	}

    //fc 06/11/04: Method to return whether model is a One to Many Relationship model (Master/Detail)
    /**
     * Returns whether the Datastore has a Many to One Relationship in its joins.
     * @return boolean
     */
    public boolean hasManyToOneRelationship() {
      return _manytoonerelationship;
    }

    //fc 06/14/04: Method to set whether Master Row can be deleted on update for a One to Many Relationship model (Master/Detail)
    /**
     * Sets whether Master Row can be deleted on update for a One to Many Relationship model (Master/Detail)
     * @param bAllow - Indicates whether to allow Master Row Delete on update.
     */
    public void setAllowMasterRowDelete(boolean bAllow) {
      _allowMasterRowDelete=bAllow;
    }


    //fc 06/14/04: Method returns whether Master Row can be deleted on update for a One to Many Relationship model (Master/Detail)
    /**
     * Returns whether Master Row can be deleted on update for a One to Many Relationship model (Master/Detail)
     * @return boolean - Indicates whether to allow Master Row Delete on update.
     */
    public boolean getAllowMasterRowDelete() {
      return _allowMasterRowDelete;
    }

    //fc 06/11/04: Inner class to store One to Many relationship metadata
    // Used to code the correct logic for updates/inserts and deletes
    class TablesRelationshipDescription extends DataStoreBuffer {
        public static final String TABLE="TABLE";
        public static final String RELATIONSHIPTYPE="RELATIONSHIPTYPE";
        public static final String MASTERKEYCOLUMNS="MASTERKEYCOLUMNS";
        public static final String DETAILKEYCOLUMNS="DETAILKEYCOLUMNS";

        public TablesRelationshipDescription() {
            addBucket(TABLE,DataStoreBuffer.DATATYPE_STRING);
            addBucket(RELATIONSHIPTYPE,DataStoreBuffer.DATATYPE_INT);
            addBucket(MASTERKEYCOLUMNS,DataStoreBuffer.DATATYPE_STRING);
            addBucket(DETAILKEYCOLUMNS,DataStoreBuffer.DATATYPE_STRING);
        }
    }

    //fc 06/11/04: Returns an Instance of the inner class TablesRelationshipDescription
    TablesRelationshipDescription getTablesRelationshipDescription(DataStoreInterface ds) {
       return getTablesRelationshipDescription(ds,true);
    }

    //fc 06/11/04: Returns an Instance of the inner class TablesRelationshipDescription
    TablesRelationshipDescription getTablesRelationshipDescription(DataStoreInterface ds,boolean bUpdateable) {
        TablesRelationshipDescription dsb=new TablesRelationshipDescription();

        String sTable;
        int iJoinCount;
        int iJoinColumnCount;
        String sLeftJoin;
        String sRightJoin;
        int iJoinRelType;
        String[] tables=getTableList(bUpdateable);
        try {
            nextTable:
            for (int i = 0; i < tables.length; i++) {
              sTable=tables[i]+".";
              iJoinCount=ds.getJoinCount();
              for (int j=0;j<iJoinCount;j++) {
                  iJoinRelType=ds.getJoinRelationType(j);
                  if (iJoinRelType!=DataStoreBuffer.RELATION_ONE_TO_ONE) {
                      iJoinColumnCount=ds.getJoinColumnCount(j);
                      for (int k=0;k<iJoinColumnCount;k++) {
                          sLeftJoin=ds.getJoinLeftColumn(j,k);
                          sRightJoin=ds.getJoinRightColumn(j,k);
                          if (sLeftJoin.startsWith(sTable) || sRightJoin.startsWith(sTable)) {
                            StringBuffer sbLeftKeyColumns=new StringBuffer(sLeftJoin);
                            StringBuffer sbRightKeyColumns=new StringBuffer(sRightJoin);
                            for (int m=1;m<iJoinColumnCount;m++) {
                              sbLeftKeyColumns.append(',');
                              sbRightKeyColumns.append(',');
                              sbLeftKeyColumns.append(ds.getJoinLeftColumn(j,m));
                              sbRightKeyColumns.append(ds.getJoinRightColumn(j,m));
                            }
                            dsb.insertRow();
                            dsb.setString(TablesRelationshipDescription.TABLE,tables[i]);
                            if (sLeftJoin.startsWith(sTable)) {
                                dsb.setInt(TablesRelationshipDescription.RELATIONSHIPTYPE,iJoinRelType==DataStoreBuffer.RELATION_ONE_TO_MANY?DataStoreBuffer.RELATION_ONE_TO_MANY:DataStoreBuffer.RELATION_MANY_TO_ONE);
                            }
                            if (sRightJoin.startsWith(sTable)) {
                                dsb.setInt(TablesRelationshipDescription.RELATIONSHIPTYPE,iJoinRelType==DataStoreBuffer.RELATION_ONE_TO_MANY?DataStoreBuffer.RELATION_MANY_TO_ONE:DataStoreBuffer.RELATION_ONE_TO_MANY);
                            }
                            dsb.setString(TablesRelationshipDescription.MASTERKEYCOLUMNS,iJoinRelType==DataStoreBuffer.RELATION_ONE_TO_MANY?sbLeftKeyColumns.toString():sbRightKeyColumns.toString());
                            dsb.setString(TablesRelationshipDescription.DETAILKEYCOLUMNS,iJoinRelType==DataStoreBuffer.RELATION_ONE_TO_MANY?sbRightKeyColumns.toString():sbLeftKeyColumns.toString());
                            continue nextTable;
                          }
                      }
                  }
              }
              dsb.insertRow();
              dsb.setString(TablesRelationshipDescription.TABLE,tables[i]);
              dsb.setInt(TablesRelationshipDescription.RELATIONSHIPTYPE,DataStoreBuffer.RELATION_ONE_TO_ONE);
            }
        } catch (DataStoreException e) {
        MessageLog.writeErrorMessage ("getTablesRelationshipDescription", e, this);
        }
        return dsb;
    }


    //fc 06/11/04: Indicates whether column is part of the master of a One To Many Relationship Model.
    /**
     * Indicates whether column is part of the master of a One To Many Relationship Model.
     * @return booelan
     */
    public boolean isColumnInOneToManyRelationship(String sColumn) {
      if (!(this instanceof DataStoreInterface))
        return false;
      TablesRelationshipDescription trd=getTablesRelationshipDescription((DataStoreInterface)this);
      String[] saTableColumn=Util.split(sColumn,".");
      if (saTableColumn.length<2)
        return false;
      String sTable=saTableColumn[0];
      try {
          for (int i=0;i<trd.getRowCount();i++) {
              if (sTable.equals(trd.getString(i,TablesRelationshipDescription.TABLE))) {
                if (trd.getInt(i,TablesRelationshipDescription.RELATIONSHIPTYPE)==RELATION_ONE_TO_MANY)
                  return true;
                else
                  return false;
              }
          }
      }
      catch (DataStoreException dse) {
          MessageLog.writeErrorMessage(dse,this);
      }
      return false;
    }

//fc 06/11/04: Copies the master data values from one row to another. Used for one to many relationship models.
    void copyMasterDataValuesToRow(DataStoreBuffer.TablesRelationshipDescription dsTables,DataStoreRow fromrow,DataStoreRow torow) throws DataStoreException {
        for (int i=0;i<dsTables.getRowCount();i++) {
              if (dsTables.getInt(i,TablesRelationshipDescription.RELATIONSHIPTYPE)==RELATION_ONE_TO_MANY) {
                copyMasterDataValuesToRow(dsTables,fromrow,torow,i);
              }
        }
    }

//fc 06/11/04: Copies the master key values from one row to another. Used for one to many relationship models.
    void copyMasterKeyValuesToRow(DataStoreBuffer.TablesRelationshipDescription dsTables,DataStoreRow fromrow,DataStoreRow torow,int iTableRow) throws DataStoreException {
        String sMasterColumns=dsTables.getString(iTableRow,DataStoreBuffer.TablesRelationshipDescription.MASTERKEYCOLUMNS);
        String[] saMasterColumns=Util.split(sMasterColumns,",");
        for (int i=0;i<saMasterColumns.length;i++) {
          torow.setData(saMasterColumns[i],fromrow.getData(saMasterColumns[i]));
        }
    }

//fc 06/11/04: Copies the master data values from one row to another. Used for one to many relationship models.
    void copyMasterDataValuesToRow(DataStoreBuffer.TablesRelationshipDescription dsTables,DataStoreRow fromrow,DataStoreRow torow,int iTableRow) throws DataStoreException {
        String sTable=dsTables.getString(iTableRow,DataStoreBuffer.TablesRelationshipDescription.TABLE)+".";
        String[] saColumns=getColumnList();
        for (int i=0;i<saColumns.length;i++) {
          if (saColumns[i].startsWith(sTable)) {
            torow.setData(saColumns[i],fromrow.getData(saColumns[i]));
            //fc 10/11/04: set the status of the master columns to not modified.
            torow.getDSDataRow().setColumnStatus(i,STATUS_NOT_MODIFIED);
          }
        }
    }

    // These methods adds support to push and pop filter criterias / Added by Juan Manuel Cortez (G2P), 09/12/2007

    public String popCriteria() {
    	if (_criteriasStack == null)
    		_criteriasStack = new Stack<String>();
    	if(_criteriasStack.isEmpty())
    		return null;
    	return _criteriasStack.pop();
    }

    public void pushCriteria(String criteria) {
    	if (_criteriasStack == null)
    		_criteriasStack = new Stack<String>();
    	_criteriasStack.push(criteria);
    }

}
