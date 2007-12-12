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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreProxy.java $
//$Author: Fred $
//$Revision: 39 $
//$Modtime: 9/14/04 12:37p $
/////////////////////////

import java.io.*;
import java.util.*;

/**
 * This class provides a storage buffer for data in SQL ResultSets for applets. The data is retrieved from a RemoteDataStore on a server via the DataServer servlet. This class is intended to be used by applets served up by framework html pages that need to get data from a database. For each ProxyDataStore in the applet there needs to be one RemoteDataStore on the server. The following classes need to be in the applets jar file in order for the DataStoreProxy to work correctly:DataStoreBuffer, DataStoreEvaluator, DataStoreException, DataStoreInterface, DataStoreProxy, DataStoreRow, DSColumnDescriptor, DSDataRow, DSDataSourceProxy, DSDataStoreDescriptor, DSJoinDescriptor, DSQuickSort, DSTableAliasDescriptor
 */
public class DataStoreProxy extends DataStoreBuffer implements Runnable, Serializable, DataStoreInterface {

    public static final int UPDATEMETHOD_UPDATES = 1;
    public static final int UPDATEMETHOD_DELETEINSERTS = 2;

    public static final int BIND_DEFAULT = 0;
    public static final int BIND_TRUE = 1;
    public static final int BIND_FALSE = 2;

    public static final int REMOTE_STATUS_OK = 0;
    public static final int REMOTE_STATUS_BAD_REQUEST = 1;
    public static final int REMOTE_STATUS_NOT_FOUND = 2;
    public static final int REMOTE_STATUS_SQL_ERROR = 3;
    public static final int REMOTE_STATUS_ACCESS_DENIED = 4;

    public static final String OPERATION_CREATE = "Create";
    public static final String OPERATION_RETRIEVE = "Retrieve";
    public static final String OPERATION_CANCEL = "Cancel";
    public static final String OPERATION_PING = "Ping";
    public static final String OPERATION_UPDATE = "Update";
    public static final String OPERATION_COUNT = "Count";
    public static final String OPERATION_DESTROY = "Destroy";
	public static final String OPERATION_VALIDATE = "Validate";
	public static final String OPERATION_GET_TABLE_COLUMNS = "GetTableColumns";
	public static final String USE_COMPRESSION = "SOFIA-Use-Compression";
    public static final String KEEP_DATA_ON_SERVER = "SOFIA-Keep-Data-On-Server";


    private int _updateMethod = UPDATEMETHOD_UPDATES;


    private boolean _checkConcurrency = false;
    private boolean _useBind = false;
    private int _maxRows = -1;
    private DSDataSourceProxy _dataSource;
    private String _remoteID = null;
    private String _updateResults = null;
    private boolean _threaded = true;
    private boolean _useCompression = false;
    private String _dbms = null;
    private boolean _keepDataOnServer=false;

    /*private class ModelChangedNotifier implements Runnable {
        private ModelChangedListener _mcl;
        private ModelChangedEvent _mce;

        public ModelChangedNotifier(ModelChangedListener mcl,ModelChangedEvent mce) {
            _mcl=mcl;
            _mce=mce;
        }

        public void run() {
            _mcl.modelChanged(_mce);
        }
    }*/

    /**
     * Creates a new empty DataStore that will retrieve it's data from a remote dataserver. This class is intended to be use by applets served up by framework html pages that need to get data from a database. For each ProxyDataStore in the applet there needs to be one RemoteDataStore on the server. The following classes need to be in the applets jar file in order for the DataStoreProxy to work correctly:DataStoreBuffer, DataStoreEvaluator, DataStoreException, DataStoreProxy, DataStoreRow, DSColumnDescriptor, DSDataRow, DSDataSourceProxy, DSDataStoreDescriptor, DSJoinDescriptor, DSQuickSort, DSTableAliasDescriptor
     * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/DataServerURI/RemoteDataStoreClassName
     * @param sessionID The server side session id to use.
     */
    public DataStoreProxy(String url, String sessionID) throws DataStoreException {
        this(url,sessionID,false);
    }

	/**
	 * Creates a new empty DataStore that will retrieve it's data from a remote dataserver. This class is intended to be use by applets served up by framework html pages that need to get data from a database. For each ProxyDataStore in the applet there needs to be one RemoteDataStore on the server. The following classes need to be in the applets jar file in order for the DataStoreProxy to work correctly:DataStoreBuffer, DataStoreEvaluator, DataStoreException, DataStoreProxy, DataStoreRow, DSColumnDescriptor, DSDataRow, DSDataSourceProxy, DSDataStoreDescriptor, DSJoinDescriptor, DSQuickSort, DSTableAliasDescriptor
	 * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/DataServerURI/RemoteDataStoreClassName
	 * @param sessionID The server side session id to use.
	 * @param useCompression Use zip compression to transfer data back from the server
	 */
	public DataStoreProxy(String url, String sessionID, boolean useCompression) throws DataStoreException {
		super();
		_useCompression = useCompression;
		_dataSource = new DSDataSourceProxy(url, sessionID, null, null, null, null, null, this);
	}
	/**
	  * Creates a new empty DataStore that will retrieve it's data from a remote dataserver.
	  * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/DataServerURI/RemoteDataStoreClassName
	  * @param userID The user id to use.
	  * @param passWord The password to use.
	  */
	 public DataStoreProxy(String url, String userID, String passWord) throws DataStoreException {
		 this(url, userID, passWord, null, null, null, null);
	 }
	 
	/**
	  * Creates a new empty DataStore that will retrieve it's data from a remote dataserver.
	  * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/DataServerURI/RemoteDataStoreClassName
	  * @param userID The user id to use.
	  * @param passWord The password to use.
	  * @param useCompression Use zip compression to transfer data back from the server
	  */
	 public DataStoreProxy(String url, String userID, String passWord, boolean useCompression) throws DataStoreException {
		 this(url, userID, passWord, null, null, null, null,null,useCompression);
	 }

    /**
     * Creates a new empty DataStore that will retrieve it's data from a remote dataserver.
     * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/servlet/DataServer/Application/RemoteDataStore
     * @param userID The user id to use.
     * @param passWord The password to use.
     * @param proxyHost The host name of a proxy server to use
     * @param proxyPort The port number of a proxy server to use
     * @param proxyUser The user id to get through the proxy server
     * @param proxyPassword The password to get through the proxy server
     */
    public DataStoreProxy(String url, String userID, String passWord, String proxyHost, String proxyPort, String proxyUser, String proxyPassword) throws DataStoreException {
        this(url, userID, passWord, proxyHost, proxyPort, proxyUser, proxyPassword, null);
    }

	/**
	 * Creates a new empty DataStore that will retrieve it's data from a remote dataserver.
	 * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/servlet/DataServer/Application/RemoteDataStore
	 * @param userID The user id to use.
	 * @param passWord The password to use.
	 * @param proxyHost The host name of a proxy server to use
	 * @param proxyPort The port number of a proxy server to use
	 * @param proxyUser The user id to get through the proxy server
	 * @param proxyPassword The password to get through the proxy server
	 * @param criteria Additional criteria to pass to the server
	 */
	public DataStoreProxy(String url, String userID, String passWord, String proxyHost, String proxyPort, String proxyUser, String proxyPassword, String criteria) throws DataStoreException {
		this(url, userID,  passWord, proxyHost, proxyPort, proxyUser, proxyPassword, criteria,false);
	}
	
	/**
	 * Creates a new empty DataStore that will retrieve it's data from a remote dataserver.
	 * @param url The url of the RemoteDataStore. Remote DataStores urls follow the following convention http://hostname/servlet/DataServer/Application/RemoteDataStore
	 * @param userID The user id to use.
	 * @param passWord The password to use.
	 * @param proxyHost The host name of a proxy server to use
	 * @param proxyPort The port number of a proxy server to use
	 * @param proxyUser The user id to get through the proxy server
	 * @param proxyPassword The password to get through the proxy server
	 * @param criteria Additional criteria to pass to the server
	 * @param useCompression Use zip compression to transfer data back from the server
	 */
	public DataStoreProxy(String url, String userID, String passWord, String proxyHost, String proxyPort, String proxyUser, String proxyPassword, String criteria, boolean useCompression) throws DataStoreException {
        super();
        if (proxyHost != null) {
            Properties p = System.getProperties();
            p.put("proxySet", "true");
            p.put("proxyHost", proxyHost);
            p.put("proxyPort", proxyPort);
        }

        _dataSource = new DSDataSourceProxy(url, null, userID, passWord, proxyUser, proxyPassword, criteria, this);
    }

    /**
     * This method indicates whether all the data in the result set that is to be returned by the last retrieve statement has in fact been retrieved.
     * @return true if all the data has been retrieved and false if the retrieve is still in progress.
     */
    public boolean allDataRetrieved() {
        return (!_retrieveInProgress);
    }

    /**
     * If a retrieve is in progress, this method will cancel it.
     */
    public void cancelRetrieve() {
        if (_retrieveInProgress) {
            try {
                _dataSource.cancelRetrieve(this);
            } catch (Exception e) {
                System.out.println("DataStoreProxy.cancelRetrieve" + e);
            }
            _retrieveInProgress = false;
            interruptWaitingRetrieveThreads();
        }

    }

    /**
     * This method will destroy the remote data store on the data server. All resources on the server will be reclaimed.
     */
    public void destroy() throws Exception {
        _dataSource.destroy(this);
    }

    /**
     * Use this method to get the amount of rows that will be retrieved when a data store retrieve is executed.
     */
    public int estimateRowsRetrieved() throws DataStoreException {
        String nullSt = null;
        return estimateRowsRetrieved(nullSt);
    }

    /**
     * Use this method to get the amount of rows that will be retrieved when a data store retrieve is executed.
     * @param criteria The selection criteria to use for the select.
     */
    public int estimateRowsRetrieved(String criteria) throws DataStoreException {

        try {
            retrieve(criteria, true);
        } catch (DataStoreException e) {
            throw e;
        }
        _retrieveInProgress = false;
        interruptWaitingRetrieveThreads();
        return _dataSource.getCount();
    }

    /**
     * This method returns the name of one of the aliases used by the datastore. Use the method getAliasCount() to find out how many tables or aliases are used by the datastore.
     * @return The table name.
     */
    public String getAlias(int tableNo) throws DataStoreException {
        if (tableNo < 0 || tableNo >= _desc.getAliasCount())
            throw (new DataStoreException("Table Number " + tableNo + " is out of range "));

        return _desc.getAlias(tableNo).getAlias();
    }

    /**
     * This method returns the number of aliases used by the datastore.
     */
    public int getAliasCount() {
        return _desc.getAliasCount();
    }

    /**
     * Use this method to get whether or not the datastore will do a concurrency check when rows are updated and deleted.
     */
    public boolean getCheckConcurrency() {
        return _checkConcurrency;
    }

    /**
     * This method returns the database name of the column in the data store given its index.
     */
    public String getColumnDatabaseName(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");
        DSColumnDescriptor d = _desc.getColumn(col);
        String table = d.getTable();
        if (table == null)
            table = _desc.getDefaultTable();

        if (table == null)
            return d.getColumn();
        else
            return table + "." + d.getColumn();
    }

    /**
     * This method returns the name of the database table that the column is for.
     */
    public String getColumnTableName(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");
        DSColumnDescriptor d = _desc.getColumn(col);
        String table = d.getTable();
        if (table == null)
            table = _desc.getDefaultTable();

        return null;
    }

    /**
     * This method is used to get whether a column should be used in the update, delete concurrency check.
     */
    public boolean getConcurrencyCheckColumn(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");

        DSColumnDescriptor c = _desc.getColumn(col);
        return c.getConcurrency();
    }

    /**
     * This method is used to get whether a column should be used in the update, delete concurrency check.
     */
    public boolean getConcurrencyCheckColumn(String col) throws DataStoreException {
        int c = getColumnIndex(col);
        return getConcurrencyCheckColumn(c);
    }

    /**
     * This method is used to get selection criteria filtering for the result set of the datastore.
     */
    public String getCriteria() {
        return _desc.getWhereClause();
    }

    /**
     * This method returns the default table for the datastore
     */
    public String getDefaultTable() {
        return _desc.getDefaultTable();
    }

    /**
     * This method will return whether the distinct flag in the data store is set. The flag indicates that the distinct keyword should be placed at the beginning of a select statement.
     */
    public boolean getDistinct() {
        return _desc.getDistinct();
    }

    /**
     * This method returns the column list in the group by clause.
     */
    public String getGroupBy() {
        return _desc.getGroupByClause();
    }

    /**
     * This method returns the having clause for the datastore.
     */
    public String getHaving() {
        return _desc.getHavingClause();
    }

    /**
     * This method returns the number of columns in a particular join.
     */
    public int getJoinColumnCount(int joinNo) {
        return _desc.getJoin(joinNo).getLeftCount();
    }

    /**
     * This method returns the number of joins in the datastore.
     */
    public int getJoinCount() {
        return _desc.getJoinCount();
    }

    /**
     * This method returns a column on the left side of the join.
     */
    public String getJoinLeftColumn(int joinNo, int colNo) {
        return _desc.getJoin(joinNo).getLeftColumn(colNo);
    }

    /**
     * This method returns the true if a particular join is outer.
     */
    public boolean getJoinOuter(int joinNo) {
        return _desc.getJoin(joinNo).isOuter();
    }

    //fc 06/11/04: Implements the newly added method to DataStoreInterface.
    /**
     * This method returns the relation type of the join. (RELATION_ONE_TO_ONE, RELATION_ONE_TO_MANY, RELATION_MANY_TO_ONE)
     */
    public int getJoinRelationType(int joinNo) {
        return _desc.getJoin(joinNo).getRelationType();
    }

    /**
     * This method returns a column on the right side of the join.
     */
    public String getJoinRightColumn(int joinNo, int colNo) {
        return _desc.getJoin(joinNo).getRightColumn(colNo);
    }

    /**
     * This method will return the maximum number of rows that the datastore will retrieve. If the max is set to -1, the datastore will retrieve all rows in the result set. Otherwise it will stop retrieving when the max is reached.
     */
    public int getMaxRows() {
        return _maxRows;
    }

    /**
     * This method returns the order by clause for the datastore.
     */
    public String getOrderBy() {
        return _desc.getOrderByClause();
    }

    /**
     * This method creates a properties object containing the definition of the data store.
     */
    public Properties getProperties() {

        Properties p = super.getProperties();
        String desc = "base.";

        p.put(desc + "updateMethod", getIntProperty(_updateMethod));
        p.put(desc + "checkConcurrency", getBoolProperty(_checkConcurrency));
        p.put(desc + "useBind", getBoolProperty(_useBind));
        p.put(desc + "maxRows", getIntProperty(_maxRows));
        p.put(desc + "remoteID", getStringProperty(_remoteID));

        return p;
    }

    /**
     * Used to get the remote id for the datastore
     */
    public String getRemoteID() {
        return _remoteID;
    }

    /**
     * This method returns the name of one of the tables used by the datastore. Use the method getAliasCount() to find out how many tables or aliases are used by the datastore.
     * @return The table name.
     */
    public String getTable(int tableNo) throws DataStoreException {
        if (tableNo < 0 || tableNo >= _desc.getAliasCount())
            throw (new DataStoreException("Table Number " + tableNo + " is out of range "));

        return _desc.getAlias(tableNo).getTable();
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
                if (!found && tableName != null) {
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
        } else {
            for (int i = 0; i < getAliasCount(); i++) {
                try {
                    String table = getTable(i);
                    boolean found = false;
                    for (int j = 0; j < tables.size(); j++) {
                        if ((tables.elementAt(j)).equals(table))
                            found = true;
                    }
                    if (!found && table != null)
                        tables.addElement(table);
                } catch (Exception e) {
                }
            }
        }

        String retVal[] = new String[tables.size()];
        tables.copyInto(retVal);

        return retVal;
    }

    /**
     * Use this method to get whether the DataStore will trim (remove trailing spaces) from columns retrieved from the database;
     */
    public boolean getTrimStrings() {
        return _desc.getTrimStrings();
    }

    /**
     * Gets the update method for the datastore.
     * @see DataStore#setUpdateMethod
     */
    public int getUpdateMethod() {
        return _updateMethod;
    }

    /**
     * Returns the results of the previous update.
     */
    public String getUpdateResults() {
        return _updateResults;
    }

    void setUpdateResults(String updateResults) {
        _updateResults = updateResults;
    }

    /**
     * This method is used to get whether a column should use bind variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and BIND_DEFAULT (Use default for datastore)
     */
    public int getUseBindColumn(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");

        DSColumnDescriptor c = _desc.getColumn(col);
        return c.getUseBind();
    }

    /**
     * This method is used to get whether a column should use bind variables for inserts and updates. Valid values and BIND_TRUE, BIND_FALSE and BIND_DEFAULT (Use default for datastore)
     */
    public int getUseBindColumn(String col) throws DataStoreException {
        int c = getColumnIndex(col);
        return getUseBindColumn(c);
    }

    /**
     * Use this method to get whether or not the datastore will use bind variables as the default for updating or inserting columns.
     */
    public boolean getUseBindForUpdate() {
        return _useBind;
    }

    /**
     * This method returns whether a column is part of the primary key
     */
    public boolean isPrimaryKey(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");

        DSColumnDescriptor c = _desc.getColumn(col);
        return c.isPrimaryKey();
    }

    /**
     * This method returns whether a column is part of the primary key
     */
    public boolean isPrimaryKey(String col) throws DataStoreException {
        int c = getColumnIndex(col);
        return isPrimaryKey(c);
    }

    /**
     * This method returns whether a column is updateable
     */
    public boolean isUpdateable(int col) throws DataStoreException {
        if (col < 0 || _desc.getColumnCount() == 0)
            throw new DataStoreException("Specified column (" + col + ") does not exist.");

        DSColumnDescriptor c = _desc.getColumn(col);
        return c.isUpdateable();
    }

    /**
     * This method returns whether a column is updateable
     */
    public boolean isUpdateable(String col) throws DataStoreException {
        int c = getColumnIndex(col);
        return isUpdateable(c);
    }

    /**
     * This method will ping the server for this particular data store. Pinging from time to time will prevent the server session from expiring.
     * @return true if the ping succeeds and false if not.
     */
    public boolean ping() throws Exception {
        return _dataSource.ping(this);
    }

    /**
     * This method will clear all rows in the dataStore.
     */
    public synchronized void reset() {
        if (_retrieveInProgress)
            cancelRetrieve();

        super.reset();
    }

    /**
     * Executes the sql statement and retrieves to data. The data is retrieved in a new thread so the beginning of the result set can be accessed before all the data has been retrieved.
     * You do not need to pass a database connection to this version of retrieve, but in order to use it the DataStore must be created with a constructor that passes an application (not the no args constructor).
     */
    public void retrieve() throws DataStoreException {
        String criteria = null;
        retrieve(criteria, false);
    }

    /**
     * Executes the sql statement and retrieves to data. The data is retrieved in a new thread so the beginning of the result set can be accessed before all the data has been retrieved.
     * @param criteria Additional selection criteria to use to limit the result set
     */
    public void retrieve(String criteria) throws DataStoreException {
        retrieve(criteria, false);
    }

    private synchronized void retrieve(String criteria, boolean countOnly) throws  DataStoreException {
        if (!countOnly)
            reset();
        waitForCancel(); //just in case there was already a retrieve running wait for it to be cancelled before continuing
        _retrieveInProgress = true;

        try {
            _dataSource.preRetrieve(this, criteria, countOnly);
        } catch (Exception e) {
            try {
                _dataSource.postRetrieve(this);
            } catch (java.sql.SQLException ex) {
                throw new DataStoreException(ex.toString(),ex);
            } catch (Exception ex) {
                throw new DataStoreException(ex.toString());
            }
            _retrieveInProgress = false;
            interruptWaitingRetrieveThreads();
            throw new DataStoreException(e.toString(),e);
        }

        if (!countOnly) {
        	if (_threaded) {
	            Thread t=new Thread(this);
    	        t.start();
        	}
        	else
        		run();
        }

    }

    /**
     * The run method for the thread that retrieves the data from the database. This method should not be called directly. Instead use the retrieve method.
     */
    public void run() {
        try {
            DataStoreRow row = new DataStoreRow(this, new DSDataRow(_desc), _desc);

            while (_dataSource.retrieveRow(this, row)) {
                _rows.addElement(row.getDSDataRow());
                if (_threaded)
	        		notifyListeners(ModelChangedEvent.TYPE_ROW_INSERTED_OR_DELETED);

                if (!_retrieveInProgress || (_maxRows > -1 && _rows.size() >= _maxRows)) {
                    _cancelInProgress = true;
                    _retrieveInProgress = false;
                    interruptWaitingRetrieveThreads();
                    break;
                }
                if (_threaded)
	                Thread.yield();

                row.setDSDataRow(new DSDataRow(_desc));
            }
            _dataSource.postRetrieve(this);
            _cancelInProgress = false;
            interruptWaitingCancelThreads();
			notifyListeners(ModelChangedEvent.TYPE_DATA_LOADED);
        } catch (Exception e) {
            System.out.println("DataStore.run:" + e);
            _cancelInProgress = false;
            interruptWaitingCancelThreads();
        }

        _retrieveInProgress = false;
        interruptWaitingRetrieveThreads();
        _cancelInProgress = false;
        interruptWaitingCancelThreads();
    }

    /**
     * This method builds the datastore from the information in the properties object.
     */
    public void setProperties(Properties p) {
        super.setProperties(p);
        String desc = "base.";
        _updateMethod = setIntProperty(p.getProperty(desc + "updateMethod"));
        _checkConcurrency = setBoolProperty(p.getProperty(desc + "checkConcurrency"));
        _useBind = setBoolProperty(p.getProperty(desc + "useBind"));
        _maxRows = setIntProperty(p.getProperty(desc + "maxRows"));
        _remoteID = getStringProperty(p.getProperty(desc + "remoteID"));
        _dbms = getStringProperty(p.getProperty(desc + "DBMS"));
    }

    /**
     * Used to set the remote id for the datastore
     */
    public void setRemoteID(String remoteID) {
        _remoteID = remoteID;
    }

    /**
     * Notifies all listeners that a model changed event occurred
     */
    public void notifyListeners(ModelChangedEvent e) {
        if (!areThereModelListeners())
            return;

		Vector modelListeners=getModelListeners();
        for (int i = 0; i < modelListeners.size(); i++) {
		    ((ModelChangedListener) modelListeners.elementAt(i)).modelChanged(e);
       //     notifyThread((ModelChangedListener) modelListeners.elementAt(i),e);
        }
    }

   // private void notifyThread(ModelChangedListener mcl,ModelChangedEvent e) {
   //     Thread t=new Thread(new ModelChangedNotifier(mcl,e));
   //     t.start();
   // }

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

        return _rows.size() - 1;
    }

    /**
     * This method will cause the database to reflect the changes made in the data store's buffer.
     * @exception com.salmonllc.sql.DataStoreException If a SQLError occurs while the datastore is updating.
     */
    public void update() throws DataStoreException {
        waitForRetrieve();
        DSDataSourceProxy out = _dataSource;
        _updateResults = null;
        DataStoreProxy[] proxy = {this};
        try {
            out.preUpdate(this);
            out.writeDSHeader(this);
            updateDs(out);
            out.commit(this);
            out.postUpdate(proxy, true);
            resetStatus();
            notifyListeners(ModelChangedEvent.TYPE_DATA_LOADED);
        }
        catch (DataStoreException e) {
            try {
                out.postUpdate(proxy, false);
            } catch (Exception ex) {
                System.err.println("DataStoreProxy.update() -- postUpdate:" + ex);
            }
            throw(e);
        } catch (Exception e) {
            try {
                out.postUpdate(proxy, false);
            } catch (Exception ex) {
                System.err.println("DataStoreProxy.update() -- postUpdate:" + ex);
            }

            String message = e.getMessage();
            if (message.equals("$Update$") || message.equals("$Insert$") || message.equals("$Delete$")) {
                throw new DataStoreException("DataStore updated canceled.");
            } else
                throw new DataStoreException(e.toString());

        }
    }

    void updateDs(DSDataSourceProxy out) throws DataStoreException, java.sql.SQLException, Exception {
        int rowNo = -1;
        DataStoreRow row = new DataStoreRow(this, null, _desc);

        //do deletes
        for (rowNo = 0; rowNo < _deletedRows.size(); rowNo++) {
            row.setDSDataRow((DSDataRow) _deletedRows.elementAt(rowNo));
            ((DSDataRow) _deletedRows.elementAt(rowNo)).setProxyRow(rowNo);
            if (!out.deleteRow(this, row))
                throw new Exception("$Delete$");
        }

        //do the updates
        for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
            row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
            ((DSDataRow) _rows.elementAt(rowNo)).setProxyRow(rowNo);
            if (row.getDSDataRow().getRowStatus() == STATUS_MODIFIED) {
                if (!out.updateRow(this, row))
                    throw new Exception("$Update$");
            }
        }

        //do the inserts
        for (rowNo = 0; rowNo < _rows.size(); rowNo++) {
            row.setDSDataRow((DSDataRow) _rows.elementAt(rowNo));
            ((DSDataRow) _rows.elementAt(rowNo)).setProxyRow(rowNo);
            if (row.getDSDataRow().getRowStatus() == STATUS_NEW_MODIFIED) {
                if (!out.insertRow(this, row))
                    throw new Exception("$Insert$");
            }
        }
    }

    DSDataSourceProxy getDataSourceProxy() {
        return _dataSource;
    }

    /**
     * @return The id of the server session this proxy is using
     */
    public String getSessionID() {
        if (_remoteID == null)
            return null;
       int pos = _remoteID.lastIndexOf("-");
	   if (pos > -1)
			return _remoteID.substring(pos + 1);
       return null;
    }

    /**
     * Updates multiple proxy data stores in a single transaction
     * @param ds The Array of DataStores to update
     * @throws DataStoreException
     */
    public static void update(DataStoreProxy[] ds) throws DataStoreException {

        int i = 0;
        DSDataSourceProxy out = ds[0].getDataSourceProxy();
        try {
            out.preUpdate(ds[0]);
            for (i = 0; i < ds.length; i++) {
                out.writeDSHeader(ds[i]);
                ds[i].setRemoteUpdateReturnValue(null);
                ds[i].updateDs(out);
            }
            out.commit(ds[0]);
            out.postUpdate(ds, true);
            for (i=0; i < ds.length; i++)
                ds[i].resetStatus();

        }
        catch (DataStoreException e) {
            try {
                out.postUpdate(ds, false);
            } catch (Exception ex) {
                System.err.println("DataStoreProxy.update() -- postUpdate:" + ex);
            }
            throw(e);
        } catch (Exception e) {
            try {
                out.postUpdate(ds, false);
            } catch (Exception ex) {
                System.err.println("DataStoreProxy.update() -- postUpdate:" + ex);
            }

            String message = e.getMessage();
            if (message.equals("$Update$") || message.equals("$Insert$") || message.equals("$Delete$")) {
                throw new DataStoreException("DataStore updated canceled.");
            } else
                throw new DataStoreException(e.toString());

        }
    }

    protected void validateColumn(int rowNo,int colNo,Vector v, DBConnection conn) {
        if (rowNo < 0 || rowNo > getRowCount() || colNo < 0 || colNo > getColumnCount())
            return;
        DSColumnDescriptor col = _desc.getColumn(colNo);
        boolean remoteRules = false;
        for (int i = 0; i < col.getRuleCount(); i++) {
            try {
                ValidationRule r = col.getRule(i);
                if (r.getRuleType() == ValidationRule.TYPE_REMOTE)
                    remoteRules = true;
                else
                    r.evaluateRule(this,rowNo,colNo,conn);
            }
            catch (DataStoreException ex) {
                ex.setRowNo(rowNo);
                try {
                    ex.setColumn(getColumnName(colNo));
                } catch (DataStoreException e){};
                v.add(ex);
            }
        }
        if (remoteRules) {
           try {
               DataStoreRow r = getDataStoreRow(rowNo,BUFFER_STANDARD);
               r.getDSDataRow().setProxyRow(rowNo);
               DataStoreException ex[] = getDataSourceProxy().validateRemoteRules(this,r,rowNo,colNo);
               for (int i = 0; i < ex.length;i++)
                   v.add(ex[i]);
           }
           catch (DataStoreException dex) {}
        }
    }


    /**
     * Use this method to get whether or not the datastore does a retrieve in a separate thread.
     */
    public boolean getEnableThreads() {
        return _threaded;
    }

    /**
     * Use this method to set whether or not the datastore will do retrieves in a separate thread
     */
    public void setEnableThreads(boolean truefalse) {
        _threaded = truefalse;
    }
    
    
	/**
	 * @return true if the DataStore is using compression to transmit data back and forth to the server.
	 */
	public boolean getUseCompression() {
		return _useCompression;
	}

	/**
	 * Set to true for the DataStore to use compression to transmit data back and forth to the server. 
	 */
	public void setUseCompression(boolean b) {
		_useCompression = b;
	}

	/**
	 * Returns a list of column definitions for a particular table in the database that the datastore is using. Note, the datastore must have an app name for this method to work.
	 */	
	public ColumnDefinition[] getColumnsForTable(String table) {
		try {
			return _dataSource.getColumnsForTable(this,table);
		}
		catch (Exception ex) {
			return null;	
		}		
	}
	
	/**
	 * Returns the name of the database engine being used
	 */
	public String getDBMS() {
		return _dbms;	
	}

    /**
     * Returns whether the data for this Proxy Datastore is also kept on the server
     * Default Value is false
     * @return true if the DataStore is keeping the data also on the server.
     */
    public boolean getKeepDataOnServer() {
        return _keepDataOnServer;
    }

    /**
     * Sets whether the data for this Proxy Datastore is also kept on the server
     */
    public void setKeepDataOnServer(boolean keepDataOnServer) {
        _keepDataOnServer = keepDataOnServer;
    }

}