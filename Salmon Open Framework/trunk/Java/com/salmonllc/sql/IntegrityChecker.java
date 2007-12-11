package com.salmonllc.sql;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataDictionary;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This class performs referential integrity checking functions even if the underlying database doesn't. Each IntegrityChecker object should correspond to a single DataStore. The object may contain rules that are checked for inserts/updates as well as rules to be checked before a row is to be deleted. Note: The column within the datastore and the column in the external table we are checking must be of the same data type.
 * @author  Tyler Williams http://www.dataterrace.com
 */
public class IntegrityChecker {
  private Vector _refCheckDel = new Vector();
  private Vector _refCheckInsUpd = new Vector();
  private String _appName;
  private DataStore _ds;
  private DataDictionary _dd;
  private static final int CHECK_DELETE = 0;
  private static final int CHECK_INSERTUPDATE = 1;

  /**
   * Constructs a new integrity checker object and initializes variables.
   * @param appName The application to get the default database connection for.
   * @param ds The data store containing the rows we will be checking for integrity
   */
  public IntegrityChecker (String appName, DataStore ds) {
    _appName = appName;
    _ds = ds;
    _dd = new DataDictionary(_appName);
  }

  /**
   * This is an inner class that represents a specific referential integrity rule.
   */
  private class RefRule {
    private String _localColumnName;
    private String _tableName;
    private String _columnName;
    private String _query;

    /**
     * Constructs a new referential integrity rule object and initializes variables.
     * @param lc The local column.
     * @param t The external table.
     * @param c The external column.
     * @param q The query being used.
     */
    RefRule (String lc, String t, String c, String q) {
      _localColumnName = lc;
      _tableName = t;
      _columnName = c;
      _query = q;
    }
    String getLocalColumnName() {
      return _localColumnName;
    }
    String getTableName() {
      return _tableName;
    }
    String getColumnName() {
      return _columnName;
    }
    String getQuery() {
      return _query;
    }
  }

  /**
   * Adds a referential integrity rule that will be used on deletion attempts.
   * Note: The localColumn and the externalColumn must be of the same data type.
   * @param localColumn The column within this IntegrityChecker object's DataStore _ds.
   * @param externalTable The external table we will use to check against the internal column.
   * @param externalColumn The external column we will use to check against the internal column.
   * @throws DataStoreException when the local or external column or external table are not found.
   */
  public void addDeleteRefCheck (String localColumn, String externalTable, String externalColumn) throws DataStoreException {
    addRefCheck(CHECK_DELETE, localColumn, externalTable, externalColumn);
  }

  /**
   * Adds a referential integrity rule that will be used on insert/update attempts.
   * Note: The localColumn and the externalColumn must be of the same data type.
   * @param localColumn The column within this IntegrityChecker object's DataStore _ds.
   * @param externalTable The external table we will use to check against the internal column.
   * @param externalColumn The external column we will use to check against the internal column.
   * @throws DataStoreException when the local or external column or external table are not found.
   */
  public void addInsertUpdateRefCheck (String localColumn, String externalTable, String externalColumn) throws DataStoreException {
    addRefCheck(CHECK_INSERTUPDATE, localColumn, externalTable, externalColumn);
  }

  /**
   * An internal method that actually adds the rules.
   * @param checkType The type of rule we are adding (CHECK_DELETE or CHECK_INSERTUPDATE).
   * @param localColumn The column within this IntegrityChecker object's DataStore _ds.
   * @param localColumn The column within this IntegrityChecker object's DataStore _ds.
   * @param externalTable The external table we will use to check against the internal column.
   * @param externalColumn The external column we will use to check against the internal column.
   * @throws DataStoreException when the local or external column or external table are not found.
   */
  private void addRefCheck (int checkType, String localColumn, String externalTable, String externalColumn) throws DataStoreException {
    Vector _localVector;
    int localColumnIndex = _ds.getColumnIndex(localColumn);
    if (localColumnIndex == -1)
      throw new DataStoreException("Specified local column (" + localColumn + ") does not exist.");
    if (!_dd.getTableNames().contains(externalTable))
      throw new DataStoreException("Specified external table (" + externalTable + ") does not exist.");
//    if (!_dd.getColumns(externalTable).contains(externalColumn))
//      throw new DataStoreException("Specified external column (" + externalColumn + ") does not exist in table " + externalTable + ".");
    StringBuffer sb = new StringBuffer("select (1) from ");
    if (checkType == CHECK_DELETE)
      _localVector = _refCheckDel;
    else
      _localVector = _refCheckInsUpd;
    sb.append(externalTable + " where " + externalColumn + " = ?");
    _localVector.add(new RefRule(localColumn, externalTable, externalColumn, sb.toString()));
  }

  /**
   * A method that checks whether a row passes the rules for allowing deletion.
   * @param row The row within DataStore _ds that we are checking the integrity of.
   * @throws DataStoreException when the local column doesn't contain data.
   */
  public IntegrityMessage isRowOKToDelete(int row) throws DataStoreException {
    return checkIntegrity(CHECK_DELETE, row);
  }

  /**
   * A method that checks whether a row passes the rules for allowing updating/insertion.
   * @param row The row within DataStore _ds that we are checking the integrity of.
   * @throws DataStoreException when the local column doesn't contain data.
   */
  public IntegrityMessage isRowOKToInsertOrUpdate(int row) throws DataStoreException {
    return checkIntegrity(CHECK_INSERTUPDATE, row);
  }

  /**
   * An internal method that actually checks the rules.
   * @param checkType The type of rule we are checking (CHECK_DELETE or CHECK_INSERTUPDATE).
   * @param row The row within DataStore _ds that we are checking the integrity of.
   * @throws DataStoreException when the local column doesn't contain data.
   */
  private IntegrityMessage checkIntegrity (int checkType, int row) throws DataStoreException {
    DBConnection _conn = null;
    PreparedStatement _st = null;
    ResultSet _rs = null;
    String _query = "";
    RefRule _rr;
    Vector _localVector;
    IntegrityMessage _im = new IntegrityMessage();
    // this should give a pointer to the original vector
    if (checkType == CHECK_DELETE)
      _localVector = _refCheckDel;
    else
      _localVector = _refCheckInsUpd;

    try {
      for (int i=0;i<_localVector.size();i++) {
        _rr = (RefRule)_localVector.elementAt(i);
        _conn = DBConnection.getConnection(_appName);
        _query = _rr.getQuery();
        _st = _conn.getJDBCConnection().prepareStatement(_query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        Object _colVal = _ds.getAny(row,_rr.getLocalColumnName());
        if (_colVal == null)
          throw new DataStoreException("Specified column (" + _rr.getLocalColumnName() + ") does not contain a value.");
        DSDataSourceJDBC.prepareForType(_ds,_st,_colVal,_ds.getColumnDataType(_rr.getLocalColumnName()),1);
        _rs = _st.executeQuery();
        if (_rs.first()) {
          // If we are looking at deletes we have a problem when any rows exist
          if (checkType == CHECK_DELETE) {
             _im.addViolation(_rr.getTableName(), _rr.getColumnName());
          }
        }
        else {
          // If we are looking at inserts or updates we have a problem when NO rows exist
          if (checkType == CHECK_INSERTUPDATE) {
            _im.addViolation(_rr.getTableName(), _rr.getColumnName());
          }
        }
      }
    }
    catch (SQLException el) {
      throw new DataStoreException( "Error reading from database, row = " +  row + el.getErrorCode() + el.getMessage());
    }
    finally {
      try {
        // A little db cleanup
        if (_rs != null) _rs.close();
        if (_st != null) _st.close();
        if (_conn != null)
          _conn.freeConnection();
      }  catch (SQLException se) {
           MessageLog.writeErrorMessage("Error cleaning up database connection",se,this);
         }
    }
    return _im;
  }


}
