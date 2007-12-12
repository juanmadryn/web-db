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


/**
 */
class DSDataSourceIngres extends DSDataSourceJDBC {
    
    public DSDataSourceIngres() {
        super();
    }    

    private int findAlias(DataStore ds,String table) {
        try {
            int count = ds.getAliasCount();

            for (int i = 0;i < count; i ++) {
                if (ds.getAlias(i) != null)
                    if (ds.getAlias(i).equalsIgnoreCase(table))
                        return i;

                if (ds.getTable(i).equalsIgnoreCase(table))
                    return i;       
            }

            if (ds.getDefaultTable() != null) {
                if (ds.getDefaultTable().equalsIgnoreCase(table))
                    return count;
            }
        }
        catch (Exception e) {}  
    
        return -1;
    }
    /**
     * @return java.lang.String
     * @param ds com.salmonllc.sql.DataStore
     */
    public String generateSelect(DataStore ds, String criteria, boolean countOnly) throws DataStoreException {

        //build the column list
        StringBuffer colList = new StringBuffer();
        colList.append("SELECT ");
        if (ds.getDistinct())
            colList.append("DISTINCT ");
        if (countOnly)
            colList.append("count(*)");
        else
        {
            for (int i = 0; i < ds.getColumnCount(); i++)
            {
                String databaseName = ds.getColumnDatabaseName(i);
                if (databaseName != null) {
                    colList.append(databaseName);
                    colList.append(",");
                }
            }
            colList.setCharAt(colList.length() - 1, ' ');
        }

        //build the join portion of the from clause
        StringBuffer joinClause = new StringBuffer();
        boolean aliasUsed[] = new boolean[ds.getAliasCount() + 1];
        for (int i = 0; i < ds.getJoinCount(); i++)
        {
            String join = " inner join ";
            if (ds.getJoinOuter(i))
                join = " left outer join ";
            String leftTable = ds.getJoinLeftColumn(i,0);
            String rightTable = ds.getJoinRightColumn(i,0);
            int pos = leftTable.indexOf(".");
            if (pos > -1)
                leftTable = leftTable.substring(0, pos);
            pos = rightTable.indexOf(".");
            if (pos > -1)
                rightTable = rightTable.substring(0, pos);
            int index = findAlias(ds,leftTable);
            if (index > -1)
            {
                if (aliasUsed[index])
                    leftTable = "";
                aliasUsed[index] = true;
            }
            index = findAlias(ds,rightTable);
            if (index > -1)
                aliasUsed[index] = true;
            joinClause.append(" " + leftTable + join + rightTable + " ON ");
            for (int j = 0; j < ds.getJoinColumnCount(i); j++)
            {
                if (j > 0)
                    joinClause.append(" AND ");
                joinClause.append(ds.getJoinLeftColumn(i,j) + " = " + ds.getJoinRightColumn(i,j));
            }
        }
        StringBuffer fromClause = new StringBuffer();
        fromClause.append(" FROM ");
        if ((ds.getDefaultTable() != null) && (!aliasUsed[ds.getAliasCount() + 1]))
            fromClause.append(ds.getDefaultTable() + ",");
        for (int i = 0; i < ds.getAliasCount(); i++)
        {
            if (!aliasUsed[i])
            {
                fromClause.append(ds.getTable(i));
                if (ds.getAlias(i) != null)
                    fromClause.append(" " + ds.getAlias(i));
                fromClause.append(",");
            }
        }
        if (fromClause.length() > 0 && joinClause.length() == 0)
            fromClause.setCharAt(fromClause.length() - 1, ' ');
        fromClause.append(joinClause);

        //build the where clause

        StringBuffer whereClause = new StringBuffer();
        // fc: 07/18/02 Added check for empty criteria to stop a bad sql statement from being generated.
        if (criteria != null && !criteria.trim().equals("") && ds.getCriteria() != null && !ds.getCriteria().trim().equals(""))
            criteria = "(" + criteria + ") AND (" + ds.getCriteria() + ")";
        else
            if (criteria == null)
                criteria = ds.getCriteria();
        if (criteria != null)
            if (!criteria.trim().equals(""))
                whereClause.append(" WHERE " + criteria);


            //finish it up and return
        String retVal = colList.toString() + fromClause.toString() + whereClause.toString();
        if (ds.getGroupBy() != null)
            retVal += " GROUP BY " + ds.getGroupBy();
        if (ds.getHaving() != null)
            retVal += " HAVING " + ds.getHaving();
        if (ds.getOrderBy() != null && !countOnly)
            retVal += " ORDER BY " + ds.getOrderBy();
        return retVal;
    
    }

}
