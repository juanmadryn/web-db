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
 * This type was created in VisualAge.
 */
class DSDataSourceSybase extends DSDataSourceJDBC {

/**
 * DSSybaseDataSource constructor comment.
 */
public DSDataSourceSybase() {
	super();
}
/**
 * This method was created in VisualAge.
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
	else {		
		for (int i = 0;i < ds.getColumnCount(); i ++) {
			String dbName = ds.getColumnDatabaseName(i);
			if (dbName != null) {
				colList.append(dbName);
				colList.append(",");
			}	
		}
		colList.setCharAt(colList.length() - 1, ' ');
	}	

	//build the from clause
	StringBuffer fromClause = new StringBuffer();
	fromClause.append(" FROM ");
	if (ds.getDefaultTable() != null)
		fromClause.append(ds.getDefaultTable() + ",");
	for (int i = 0; i < ds.getAliasCount(); i++) {
		fromClause.append(ds.getTable(i));
		if (ds.getAlias(i) != null)
			fromClause.append(" " + ds.getAlias(i));
		fromClause.append(",");	
	}	
	fromClause.setCharAt(fromClause.length() - 1, ' ');

	//build the where clause
	StringBuffer whereClause = new StringBuffer();
    // fc: 07/18/02 Added check for empty criteria to stop a bad sql statement from being generated.
	if (criteria != null  && !criteria.trim().equals("") && ds.getCriteria() != null  && !ds.getCriteria().trim().equals(""))
		criteria = "(" + criteria + ") AND (" + ds.getCriteria() + ")";
	else if (criteria == null)
		criteria = ds.getCriteria();
	
	if (criteria !=  null)
		if (criteria.trim().equals(""))
			criteria = "";
			
	if (criteria !=  null || ds.getJoinCount() > 0) {
		whereClause.append(" WHERE ");
		if (ds.getJoinCount() > 0) {
			whereClause.append("(");
			for (int i = 0; i < ds.getJoinCount(); i ++) {
				for (int j = 0; j < ds.getJoinColumnCount(i);j++) {
					whereClause.append(ds.getJoinLeftColumn(i,j));
					if (ds.getJoinOuter(i))
						whereClause.append('*');
					whereClause.append('=');
					whereClause.append(ds.getJoinRightColumn(i,j));
					if (j < (ds.getJoinColumnCount(i) - 1))
						whereClause.append(" AND ");
				}
				if (i < (ds.getJoinCount() - 1))
					whereClause.append(" AND ");
			}		
			whereClause.append(")");
			if (criteria != null)
				whereClause.append(" AND (" + criteria + ")");
		}	
		else if (criteria != null)
			whereClause.append(criteria);
		

	}	

	//finish it up and return
	String retVal = colList.toString() + fromClause.toString() + whereClause.toString();

	if (ds.getGroupBy() != null)
		retVal += " GROUP BY " +  ds.getGroupBy();

	if (ds.getHaving() != null)
		retVal += " HAVING " +  ds.getHaving();

	if (ds.getOrderBy() != null && ! countOnly)
		retVal += " ORDER BY " +  ds.getOrderBy();
		
	return retVal;		
	
}
}
