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
import java.util.Vector;

/**
 * This class is can be used to pipe data from one database to another assuming that the identical tables exist in both databases
 */
public class DataPipe {
    String _source;
    String _dest;
    String _application;
    Vector _tables = new Vector();
    DataDictionary _dataDict;
    boolean _continueOnError = false;
    int _maxRows = -1;
    boolean _updateTablesWithData=true;

    /**
     * Constructs a new DataPipe
     * @param application The application that this is running under
     * @param source The profile name for the source database
     * @param dest The profile name for the destination database
     * @param tables A vector of tables to pipe over. Leave null for all tables
     */
    public DataPipe(String application, String source, String dest, Vector tables) {
        _source = source;
        _dest = dest;
        _tables = tables;
        _application = application;
        _dataDict = new DataDictionary(application, source);
        if (_tables == null)
            _tables = _dataDict.getTableNames();
    }

    /**
     * Constructs a new DataPipe that will pipe all data from one db to another
     * @param application The application that this is running under
     * @param source The profile name for the source database
     * @param dest The profile name for the destination database
     */
    public DataPipe(String application, String source, String dest) {
        this(application, source, dest, null);
    }

    /**
     * Set to true if you want the pipe to empty the table before copying the data to it.
     * Set to false to skip tables that already have data in them
     * @param updateTablesWithData
     */
    public void setUpdateTablesWithData(boolean updateTablesWithData) {
        _updateTablesWithData = updateTablesWithData;
    }

    /**
     * Copies the data for the specifiec tables from one database to another
     * @throws Exception
     */
    public void pipeData() throws Exception {
        MessageLog.writeInfoMessage("Beginning Datapipe process", this);
        DBConnection dest = null;
        boolean doRollback = true;
        try {
            dest = DBConnection.getConnection(_application, _dest);
            java.sql.Statement st = dest.createStatement();
            for (int i = 0; i < _tables.size(); i++) {
                try {
                    String table = (String) _tables.elementAt(i);

                    DataStore ds = buildDs(table);
                    if (! _updateTablesWithData) {
                        int count = ds.estimateRowsRetrieved(dest);
                        if (count > 0) {
                            MessageLog.writeInfoMessage("Table:" + table + " SKIPPED. Target table already has data.", this);
                            continue;
                        }
                    }
                    if (_maxRows != -1) {
                        int count = ds.estimateRowsRetrieved();
                        if (count > _maxRows) {
                            MessageLog.writeInfoMessage("Table:" + table + " SKIPPED. It has too many rows(" + count + ")", this);
                            continue;
                        }
                    }
                    MessageLog.writeInfoMessage("Loading data for table:" + table, this);
                    ds.retrieve();
                    MessageLog.writeInfoMessage("Loaded:" + ds.getRowCount() + " rows for:" + table, this);
                    for (int j = 0; j < ds.getRowCount(); j++)
                        ds.setRowStatus(j, DataStore.STATUS_NEW_MODIFIED);
                    dest.beginTransaction();
                    doRollback = true;
                    st.executeUpdate("DELETE FROM " + table);
                    ds.update(dest);
                    MessageLog.writeInfoMessage("Updated:" + ds.getRowCount() + " rows for:" + table, this);
                    doRollback = false;
                    dest.commit();
                } catch (Exception e) {
                    if (doRollback)
                        dest.rollback();
                    if (!_continueOnError)
                        throw(e);
                    else
                        MessageLog.writeErrorMessage("Error Piping Data", e, this);
                }
            }
            st.close();
        } catch (Exception e) {
            throw (e);
        } finally {
            if (dest != null)
                dest.freeConnection();
        }
    }

    private DataStore buildDs(String tableName) {
        java.util.Vector columns = _dataDict.getColumns(tableName);
        DataStore ds = new DataStore(_application, _source);
        ds.setUseBindForUpdate(true);
        ds.setBatchInserts(true);
        int nColumns = columns.size();
        for (int i = 0; i < nColumns; i++) {
            ColumnDefinition column = (ColumnDefinition) columns.elementAt(i);
            if (column.getDSDataType() == DataStore.DATATYPE_DATE)
                ds.addColumn(tableName, column.getColumnName(), DataStore.DATATYPE_DATETIME, true, true);
            else if (column.getDSDataType() == DataStore.DATATYPE_DOUBLE && column.getScale() < 1)
                ds.addColumn(tableName, column.getColumnName(), DataStore.DATATYPE_INT, true, true);
            else
                ds.addColumn(tableName, column.getColumnName(), column.getDSDataType(), true, true);
        }
        return ds;
    }

    /**
     * Sets the maximum number of rows any one table will pipe over or -1 for unlimited
     */
    public void setMaxRows(int maxRows) {
        _maxRows = maxRows;
    }

    /**
     * Set to true to continue processing even if there is an error
     */
    public void setContinueOnError(boolean continueOnError) {
        _continueOnError = continueOnError;
    }


}



