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

import java.sql.*;

/**
 * This type was created in VisualAge.
 */
class DSDataSourceMSSQL extends DSDataSourceSybase {

    /**
     * DSSybaseDataSource constructor comment.
     */
    public DSDataSourceMSSQL() {
        super();
    }

    protected void populateAutoIncrementValue(DataStoreRow row, DBConnection conn, int colNo) {
        try {
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT @@IDENTITY AS New_ID");
            if (r.next()) {
                int colStat = row.getDSDataRow().getColumnStatus(colNo);
                int rowStat = row.getDSDataRow().getRowStatus();
                Object val = null;
                if (row.getDataType(colNo) == DataStore.DATATYPE_LONG)
                    val = new Long(r.getLong(1));
                else if (row.getDataType(colNo) == DataStore.DATATYPE_INT)
                    val = new Integer(r.getInt(1));
                else if (row.getDataType(colNo) == DataStore.DATATYPE_SHORT)
                    val = new Short(r.getShort(1));

                row.setData(colNo, val);
                row.getDSDataRow().setColumnStatus(colNo, colStat);
                row.getDSDataRow().setRowStatus(rowStat);
            }
            r.close();
            st.close();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("Error getting last auto increment value", e, this);
        }
    }
}
