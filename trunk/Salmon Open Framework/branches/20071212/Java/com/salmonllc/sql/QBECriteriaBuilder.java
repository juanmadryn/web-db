//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2003, Salmon LLC
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
import com.salmonllc.util.SalmonDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class builds selection criteria strings for the QBEBuilder component
 */
public class QBECriteriaBuilder {
	public static final int OR_OPERATOR = 0;
	public static final int AND_OPERATOR = 1;
	public static final int NOT_OPERATOR = 2;

	private static final int FILTERTYPE_STRING = 0;
	private static final int FILTERTYPE_NUMBER = 1;
	private static final int FILTERTYPE_NONE = 2;
	private static final int FILTERTYPE_TEXT = 3;

	public static final String DEFAULT_STOP_WORDS[] =
		{
			"the",
			"of",
			"to",
			"and",
			"in",
			"that",
			"for",
			"by",
			"as",
			"be",
			"or",
			"this",
			"which",
			"with",
			"at",
			"an",
			"from",
			"under",
			"such",
			"there",
			"other",
			"if",
			"but",
			"upon",
			"where",
			"these",
			"when",
			"whether",
			"also",
			"than",
			"after",
			"within",
			"before",
			"because",
			"without",
			"however",
			"between",
			"those",
			"since",
			"into",
			"out",
			"a",
			"is",
			"it",
			"i" };

	private static SalmonDateFormat _dateFormat = new SalmonDateFormat();

	private static class Tokenizer {
		private Vector _tokens = new Vector();
		private Vector _operators = new Vector();
		private int _nextElement = -1;
		public static final String DELIMITERS = ",.?\"!()[]{}:;\\/ +";

		public Tokenizer(String parseIt) {
			super();

			if (parseIt == null)
				return;

			int size = parseIt.length();
			StringBuffer work = new StringBuffer(size);

			char lastOp = ' ';
			char lastChar = ' ';
			char c = ' ';
			boolean quoteMode = false;
			boolean delimitMode = false;

			for (int i = 0; i < size; i++) {
				lastChar = c;
				c = parseIt.charAt(i);
				if (c == '+') {
					if (quoteMode)
						work.append(c);
					else {
						lastOp = c;
						if (work.length() > 0) {
							if (addString(work, lastOp));
							lastOp = ' ';
						}
						delimitMode = true;
					}
				} else if (c == '-') {
					if (quoteMode || DELIMITERS.indexOf(lastChar) < 0)
						work.append(c);
					else
						lastOp = c;
				} else if (c == '"') {
					if (addString(work, lastOp))
						lastOp = ' ';
					quoteMode = !quoteMode;
				} else {
					if (quoteMode)
						work.append(c);
					else if ((DELIMITERS.indexOf(c) != -1)) {
						if (!delimitMode) {
							if (work.length() > 0) {
								if (addString(work, lastOp));
								lastOp = ' ';
							}
							delimitMode = true;
						}
					} else {
						delimitMode = false;
						work.append(c);
					}
				}
			}

			addString(work, lastOp);
		}

		private boolean addString(StringBuffer s, char op) {
			if (s.length() == 0)
				return false;

			int operator = OR_OPERATOR;
			String token = s.toString();

			if (op == '+')
				operator = AND_OPERATOR;
			else if (op == '-')
				operator = NOT_OPERATOR;

			_operators.addElement(new Integer(operator));
			_tokens.addElement(token);
			s.setLength(0);

			return true;
		}

		public int currentOperator() {
			if (_nextElement == -1 || _nextElement >= _operators.size())
				return -1;
			else
				return ((Integer) _operators.elementAt(_nextElement)).intValue();
		}

		public boolean hasMoreTokens() {

			int size = _operators.size();

			if (size > (_nextElement + 1))
				return true;

			return false;
		}

		public String nextToken() {
			if (!hasMoreTokens())
				return null;

			_nextElement++;

			return (String) _tokens.elementAt(_nextElement);
		}
	}

	public static String buildSQL(DataStoreQBEInterface ds, String filterString, int filterType, ColumnDefinition[] colList, String stopWords[]) throws DataStoreException {
		String val = filterString;
		if (val == null)
			return null;
		if (colList == null || colList.length == 0)
			return null;

		String dbms = ds.getDBMS();

		StringBuffer filterBuffer = new StringBuffer(255);

		if (filterType == QBEBuilder.CRITERIA_TYPE_COMPLEX) {
			String complexFilter = buildComplexSQL(val, colList, dbms, stopWords);
			if (complexFilter != null)
				filterBuffer.append(complexFilter);
		} else {
			for (int i = 0; i < colList.length; i++) {
				if (filterType >= QBEBuilder.CRITERIA_STRING_ONLY && colList[i].getDSDataType() != QBEBuilder.DATATYPE_STRING)
					continue;
				if (filterType == QBEBuilder.CRITERIA_TYPE_CUSTOM)
					continue;
				val = DataStoreBuffer.fixQuote(val.trim(), colList[i].getDSDataType(), dbms);
				String column = colList[i].getTableName() + "." + colList[i].getColumnName();
				if (filterType == QBEBuilder.CRITERIA_TYPE_CONTAINS) {
					addOr(filterBuffer);
					filterBuffer.append(column);
					filterBuffer.append(" LIKE '%");
					filterBuffer.append(val);
					filterBuffer.append("%'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE) {
					addOr(filterBuffer);
					filterBuffer.append("upper(");
					filterBuffer.append(column);
					filterBuffer.append(") LIKE '%");
					filterBuffer.append(val.toUpperCase());
					filterBuffer.append("%'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE) {
					addOr(filterBuffer);
					filterBuffer.append("upper(");
					filterBuffer.append(column);
					filterBuffer.append(") = '");
					filterBuffer.append(val.toUpperCase());
					filterBuffer.append("'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_STARTS_WITH) {
					addOr(filterBuffer);
					filterBuffer.append(column);
					filterBuffer.append(" LIKE '");
					filterBuffer.append(val);
					filterBuffer.append("%'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE) {
					addOr(filterBuffer);
					filterBuffer.append("upper(");
					filterBuffer.append(column);
					filterBuffer.append(") LIKE '");
					filterBuffer.append(val.toUpperCase());
					filterBuffer.append("%'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_IN) {
					String in = buildInSQL(val, colList[i]);
					if (in != null) {
						addOr(filterBuffer);
						filterBuffer.append(column);
						filterBuffer.append(" IN (");
						filterBuffer.append(in);
						filterBuffer.append(")");
					}
				} else {
					int datatype = colList[i].getDSDataType();
					String lit = getColumnLiteralValue(val, datatype, true);
					String op = null;
					if (filterType == QBEBuilder.CRITERIA_TYPE_LTE)
						op = " <= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_LT)
						op = " < ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_GTE)
						op = " >= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_GT)
						op = " >= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_EQUALS)
						op = " = ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_NOT_EQUALS) {
						lit += " or " + column + " IS NULL ";
						op = " <> ";
					}
					if (lit != null && op != null) {
						addOr(filterBuffer);
						filterBuffer.append(column);
						filterBuffer.append(op);
						filterBuffer.append(lit);
					}
				}
			}
		}
		return filterBuffer.toString();

	}

	private static void addOr(StringBuffer sb) {
		if (sb.length() != 0)
			sb.append(" or ");
	}

	private static void addDoubleBar(StringBuffer sb) {
		if (sb.length() != 0)
			sb.append(" || ");
	}
	/**
	* Builds a datastore filter expression for the specified datastore and filter string comparing to all columns in the datastore
	* @param dsb The DataStore to build the filter for
	* @param filterString the filter to value to compare to elements in the datastore
	* @param filterTye The type of comparison to use in the filter. Valid values are one of the QBEBuilder CRITERIA_TYPE constants
	**/
	public static String buildFilter(DataStoreBuffer dsb, String filterString, int filterType) throws DataStoreException {
		String colList[] = dsb.getColumnList();
		return buildFilter(dsb, filterString, filterType, colList, null);
	}

	/**
	* Builds a datastore filter expression for the specified datastore and filter string comparing to all columns in the specified column list
	* @param dsb The DataStore to build the filter for
	* @param filterString the filter to value to compare to elements in the datastore
	* @param filterType The type of comparison to use in the filter. Valid values are one of the QBEBuilder CRITERIA_TYPE constants
	* @param colList The list of columns in the datastore to compare to the filter string using the filter type
	* @param stopWords A list of words to exclude from complex criteria. Set to null to use the default list.
	**/
	public static String buildFilter(DataStoreBuffer dsb, String filterString, int filterType, String colList[], String stopWords[]) throws DataStoreException {
		String val = filterString;
		if (val == null)
			return null;
		if (colList == null || colList.length == 0)
			return null;

		StringBuffer filterBuffer = new StringBuffer(255);

		if (filterType == QBEBuilder.CRITERIA_TYPE_COMPLEX)
			filterBuffer.append(buildComplexFilter(dsb, val, colList, stopWords));
		else {
			for (int i = 0; i < colList.length; i++) {
				if (filterType >= QBEBuilder.CRITERIA_STRING_ONLY && dsb.getColumnDataType(colList[i]) != QBEBuilder.DATATYPE_STRING)
					continue;
				if (filterType == QBEBuilder.CRITERIA_TYPE_CUSTOM)
					continue;
				val = fixQuoteForDsExp(val.trim());
				if (filterType == QBEBuilder.CRITERIA_TYPE_CONTAINS) {
					addDoubleBar(filterBuffer);
					filterBuffer.append(colList[i]);
					filterBuffer.append(".indexOf('");
					filterBuffer.append(val);
					filterBuffer.append("') > -1");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE) {
					addDoubleBar(filterBuffer);
					filterBuffer.append(colList[i]);
					filterBuffer.append(".toLowerCase().indexOf('");
					filterBuffer.append(val.toLowerCase());
					filterBuffer.append("') > -1");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE) {
					addDoubleBar(filterBuffer);
					filterBuffer.append(colList[i]);
					filterBuffer.append(".toLowerCase() == '");
					filterBuffer.append(val.toLowerCase());
					filterBuffer.append("'");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_STARTS_WITH) {
					addDoubleBar(filterBuffer);
					filterBuffer.append(colList[i]);
					filterBuffer.append(".indexOf('");
					filterBuffer.append(val);
					filterBuffer.append("') == 0");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE) {
					addDoubleBar(filterBuffer);
					filterBuffer.append(colList[i]);
					filterBuffer.append(".toLowerCase().indexOf('");
					filterBuffer.append(val.toLowerCase());
					filterBuffer.append("') == 0");
				} else if (filterType == QBEBuilder.CRITERIA_TYPE_IN) {
					String in = buildInFilter(val, colList[i], dsb.getColumnDataType(colList[i]));
					if (in != null) {
						addDoubleBar(filterBuffer);
						filterBuffer.append(in);
					}
				} else {
					int datatype = dsb.getColumnDataType(colList[i]);
					String lit = getColumnLiteralValue(val, datatype, false);
					String op = null;
					if (filterType == QBEBuilder.CRITERIA_TYPE_LTE)
						op = " <= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_LT)
						op = " < ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_GTE)
						op = " >= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_GT)
						op = " >= ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_EQUALS)
						op = " == ";
					else if (filterType == QBEBuilder.CRITERIA_TYPE_NOT_EQUALS)
						op = " != ";
					if (lit != null && op != null) {
						addDoubleBar(filterBuffer);
						filterBuffer.append(colList[i]);
						filterBuffer.append(op);
						filterBuffer.append(lit);
					}
				}
			}
		}
		return filterBuffer.toString();
	}

	/**
	 * Builds a datastore filter expression for the specified datastore and filter string
	 * @param ds The DataStore to build the filter for
	 * @param filterString the filter to parse and convert into a datastore filter
	 * @param the column list to compare the filter to
	 */
	private static String buildComplexFilter(DataStoreBuffer ds, String filterString, String[] colList, String[] stopWords) {
		try {
			if (filterString == null)
				return null;
			if (filterString.length() == 0)
				return null;

			//figure out if we use the default stop word list or the one passed in
			String sw[]=DEFAULT_STOP_WORDS;
			if (stopWords != null)
				sw=stopWords;

			//figure out from the datastore what colums and datatypes we can use to build a filter
			int colTypes[] = new int[colList.length];
			int colCount = 0;
			for (int i = 0; i < colList.length; i++) {
				String colName = colList[i];
				int type = ds.getColumnDataType(colName);
				if (type == DataStoreBuffer.DATATYPE_STRING) {
					type = FILTERTYPE_STRING;
					colCount++;
				} else if (type == DataStoreBuffer.DATATYPE_DOUBLE || type == DataStoreBuffer.DATATYPE_FLOAT || type == DataStoreBuffer.DATATYPE_INT || type == DataStoreBuffer.DATATYPE_LONG || type == DataStoreBuffer.DATATYPE_SHORT) {
					type = FILTERTYPE_NUMBER;
					colCount++;
				} else
					type = FILTERTYPE_NONE;

				colTypes[i] = type;
			}

			if (colCount == 0)
				return null;

			//parse the criteria into terms, types and operators
			String work = filterString.trim();

			Tokenizer t = new Tokenizer(work);
			Vector fTerms = new Vector();
			Vector fTypes = new Vector();
			Vector fOperators = new Vector();
			while (t.hasMoreTokens()) {
				String token = t.nextToken();
				boolean addToken = true;
				for (int i = 0; i < sw.length; i++) {
					String temp = token.toLowerCase();
					if (temp.equals(sw[i])) {
						addToken = false;
						break;
					}
				}
				if (token.length() == 1)
					addToken = false;

				if (addToken) {
					fTerms.addElement(fixQuoteForDsExp(token));
					try {
						Integer.parseInt(token);
						fTypes.addElement(new Integer(FILTERTYPE_NUMBER));
					} catch (Exception e) {
						fTypes.addElement(new Integer(FILTERTYPE_STRING));
					}
					fOperators.addElement(new Integer(t.currentOperator()));
				}
			}

			//build a filter string
			String ret = "";
			String ors = "";
			String ands = "";
			work = "";
			int termCount = fTerms.size();
			for (int i = 0; i < termCount; i++) {
				String or = "";
				work = "";
				String fTerm = (String) fTerms.elementAt(i);
				int fType = ((Integer) fTypes.elementAt(i)).intValue();
				int fOperator = ((Integer) fOperators.elementAt(i)).intValue();

				for (int j = 0; j < colList.length; j++) {

					String col = colList[j];

					if (colTypes[j] == FILTERTYPE_NONE)
						continue;
					else if (colTypes[j] == FILTERTYPE_STRING) {
						work += or;
						String token = (String) fTerms.elementAt(i);
						work += "(" + col + ".toUpperCase().indexOf('" + token.toUpperCase() + "')";
						if (fOperator == NOT_OPERATOR) {
							work += " == -1 || " + col + " == null)";
							or = " && ";
						} else {
							work += " > -1)";
							or = " || ";
						}
					} else if (fType == FILTERTYPE_NUMBER) {
						work += or;
						work += "(" + col;
						if (fOperator == NOT_OPERATOR) {
							work += " != ";
							or = " && ";
						} else {
							work += " == ";
							or = " || ";
						}

						work += fTerm + ")";

					}
				}
				String op = "\n";

				if (fOperator == OR_OPERATOR) {
					if (ors.length() > 0)
						ors += " || ";
					ors += "(" + work + ")";
				} else {
					if (ands.length() > 0)
						ands += " && ";
					ands += "(" + work + ")";
				}
			}

			if (ors.length() > 0)
				ret += " (" + ors + ")";

			if (ands.length() > 0) {
				if (ors.length() > 0)
					ret += " && ";
				ret += ands;
			}
			return ret;
		} catch (Exception ex) {
			return null;
		}
	}

	private static String buildInSQL(String filterString, ColumnDefinition col) {
		String ret = null;
		if (filterString == null)
			return ret;
		if (filterString.length() == 0)
			return ret;
		StringTokenizer st = new StringTokenizer(filterString, ",");
		while (st.hasMoreElements()) {
			String tok = st.nextToken();
			tok = getColumnLiteralValue(tok, col.getDSDataType(), true);
			if (tok != null) {
				if (ret == null)
					ret = tok;
				else
					ret += "," + tok;
			}
		}
		return ret;
	}

	private static String buildInFilter(String filterString, String colName, int colType) {
		String ret = null;
		if (filterString == null)
			return ret;
		if (filterString.length() == 0)
			return ret;
		StringTokenizer st = new StringTokenizer(filterString, ",");
		while (st.hasMoreElements()) {
			String tok = st.nextToken();
			tok = getColumnLiteralValue(tok, colType, false);
			if (tok != null) {
				if (ret != null)
					ret += " || ";
				else
					ret = "";
				ret += colName + " == " + tok;
			}
		}
		return ret;
	}
	/**
	 * Builds a datastore filter expression for the specified datastore and filter string
	 * @param ds The DataStore to build the filter for
	 * @param filterString the filter to parse and convert into a datastore filter
	 * @param the column list to compare the filter to
	 */
	private static String buildComplexSQL(String filterString, ColumnDefinition[] colList, String DBMS, String[] stopWords) {
		try {
			if (filterString == null)
				return null;
			if (filterString.length() == 0)
				return null;

			//figure out if we use the default stop word list or the one passed in
			String sw[]=DEFAULT_STOP_WORDS;
			if (stopWords != null)
				sw=stopWords;

			//figure out from the datastore what colums and datatypes we can use to build a filter
			int colTypes[] = new int[colList.length];
			int colCount = 0;
			for (int i = 0; i < colList.length; i++) {
				int type = colList[i].getDSDataType();
				if (type == DataStoreBuffer.DATATYPE_STRING) {
					type = FILTERTYPE_STRING;
					if (DBMS.equals(DBConnection.SYBASE_CONNECTION) || DBMS.equals(DBConnection.MSSQLSEVER_CONNECTION))
						if (colList[i].getDBDataType() != null && colList[i].getDBDataType().equalsIgnoreCase("text"))
							type=FILTERTYPE_TEXT;
					colCount++;
				} else if (type == DataStoreBuffer.DATATYPE_DOUBLE || type == DataStoreBuffer.DATATYPE_FLOAT || type == DataStoreBuffer.DATATYPE_INT || type == DataStoreBuffer.DATATYPE_LONG || type == DataStoreBuffer.DATATYPE_SHORT) {
					type = FILTERTYPE_NUMBER;
					colCount++;
				} else
					type = FILTERTYPE_NONE;

				colTypes[i] = type;
			}

			if (colCount == 0)
				return null;

			//parse the criteria into terms, types and operators
			String work = filterString.trim();

			Tokenizer t = new Tokenizer(work);
			Vector fTerms = new Vector();
			Vector fTypes = new Vector();
			Vector fOperators = new Vector();
			while (t.hasMoreTokens()) {
				String token = t.nextToken();
				boolean addToken = true;
				for (int i = 0; i < sw.length; i++) {
					String temp = token.toLowerCase();
					if (temp.equals(sw[i])) {
						addToken = false;
						break;
					}
				}

				if (addToken) {
					fTerms.addElement(DataStoreBuffer.fixQuote(token, DataStoreBuffer.DATATYPE_STRING, DBMS));
					try {
						Integer.parseInt(token);
						fTypes.addElement(new Integer(FILTERTYPE_NUMBER));
					} catch (Exception e) {
						fTypes.addElement(new Integer(FILTERTYPE_STRING));
					}
					fOperators.addElement(new Integer(t.currentOperator()));
				}
			}

			//build a filter string
			String ret = "";
			String ors = "";
			String ands = "";
			work = "";
			int termCount = fTerms.size();
			for (int i = 0; i < termCount; i++) {
				String or = "";
				work = "";
				String fTerm = (String) fTerms.elementAt(i);
				int fType = ((Integer) fTypes.elementAt(i)).intValue();
				int fOperator = ((Integer) fOperators.elementAt(i)).intValue();

				for (int j = 0; j < colList.length; j++) {

					String col = colList[j].getTableName() + "." + colList[j].getColumnName();

					if (colTypes[j] == FILTERTYPE_NONE)
						continue;
					else if (colTypes[j] == FILTERTYPE_STRING || colTypes[j] == FILTERTYPE_TEXT) {
						work += or;
						String token = (String) fTerms.elementAt(i);
						String temp = col;
						if (colTypes[j] == FILTERTYPE_TEXT)
	//						temp = "convert(char(8000), " + col + ")";
                            temp = "convert(char(255), " + col + ")";
						if (fOperator == NOT_OPERATOR) {
							work += "( upper(" + temp + ") NOT LIKE '%" + token.toUpperCase() + "%'";
							work += " or " + col + " IS NULL) ";
							or = " and ";
						} else {
							work += "( upper(" + temp + ") LIKE '%" + token.toUpperCase() + "%')";
							or = " or ";
						}
					} else if (fType == FILTERTYPE_NUMBER) {
						work += or;
						work += "(" + col;
						if (fOperator == NOT_OPERATOR) {
							work += " <> ";
							or = " and ";
						} else {
							work += " = ";
							or = " or ";
						}

						work += fTerm + ")";

					}
				}
				String op = "\n";

				if (fOperator == OR_OPERATOR) {
					if (ors.length() > 0)
						ors += " or ";
					ors += "(" + work + ")";
				} else {
					if (ands.length() > 0)
						ands += " and ";
					ands += "(" + work + ")";
				}
			}

			if (ors.length() > 0)
				ret += " (" + ors + ")";

			if (ands.length() > 0) {
				if (ors.length() > 0)
					ret += " and ";
				ret += ands;
			}
			if (ret.length() == 0)
				return null;
			else
				return ret;
		} catch (Exception ex) {
			return null;
		}
	}

	private static String fixQuoteForDsExp(String token) {
		StringBuffer sb = new StringBuffer(token.length());
		for (int i = 0; i < token.length(); i++) {
			char c = token.charAt(i);
			if (c == '\'')
				sb.append("~'");
			else
				sb.append(c);
		}
		return sb.toString();
	}

	private static String getColumnLiteralValue(String token, int dataType, boolean SQL) {
		if (dataType == QBEBuilder.DATATYPE_ANY)
			return token;
		else if (dataType == QBEBuilder.DATATYPE_STRING)
			return "'" + token + "'";
		else if (dataType == QBEBuilder.DATATYPE_SHORT || dataType == QBEBuilder.DATATYPE_INT || dataType == QBEBuilder.DATATYPE_LONG) {
			try {
				double d = Double.parseDouble(token);
				long l = (long) d;
				return new Long(l).toString();
			} catch (Exception ex) {
				return null;
			}
		} else if (dataType == QBEBuilder.DATATYPE_DOUBLE || dataType == QBEBuilder.DATATYPE_FLOAT) {
			try {
				return new Double(Double.parseDouble(token)).toString();
			} catch (Exception ex) {
				return null;
			}
		} else if (dataType == QBEBuilder.DATATYPE_DATETIME || dataType == QBEBuilder.DATATYPE_DATE || dataType == QBEBuilder.DATATYPE_TIME) {
			try {
				if (SQL) {
					Date d = _dateFormat.parse(token);
					String val = "";
					if (dataType == QBEBuilder.DATATYPE_DATETIME)
						val = "{ts ' " + new Timestamp(d.getTime()).toString() + "'}";
					else if (dataType == QBEBuilder.DATATYPE_DATE)
						val = "{d ' " + new java.sql.Date(d.getTime()).toString() + "'}";
					else if (dataType == QBEBuilder.DATATYPE_TIME)
						val = "{t '" + new java.sql.Time(d.getTime()).toString() + "'}";

					return val;
				} else {
					Date d = _dateFormat.parse(token);
					String val = "";
					if (dataType == QBEBuilder.DATATYPE_DATETIME)
						val = new Timestamp(d.getTime()).toString();
					else if (dataType == QBEBuilder.DATATYPE_DATE)
						val = new java.sql.Date(d.getTime()).toString();
					else if (dataType == QBEBuilder.DATATYPE_TIME)
						val = new java.sql.Time(d.getTime()).toString();
					return "'" + val + "'";
				}
			} catch (ParseException e) {
				return null;
			}
		} else
			return null;
	}

	private static String formatDateTime(Timestamp t, SimpleDateFormat f, String DBMS) {
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

		if (DBMS == DBConnection.SYBASE_CONNECTION && nanosString.length() > 3)
			nanosString = nanosString.substring(0, 3);

		return ret + "." + nanosString;
	}
}
