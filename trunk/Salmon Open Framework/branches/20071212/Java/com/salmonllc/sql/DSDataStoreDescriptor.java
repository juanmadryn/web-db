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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSDataStoreDescriptor.java $
//$Author: Dan $
//$Revision: 27 $
//$Modtime: 11/02/04 3:55p $
/////////////////////////

import java.util.*;

/**
 * This class is public as an implementation detail. It should not be used outside the framework.
 */
public class DSDataStoreDescriptor implements java.io.Serializable {
    private Vector _columns = new Vector();
    private Vector _joins = new Vector(1, 1);
    private Vector _aliases = new Vector(1, 1);
    private String _defaultTable;
    private String _whereClause;
    private String _groupByClause;
    private String _havingClause;
    private String _orderByClause;
    private boolean _distinct = false;
    private boolean _trimStrings = true;
    private Hashtable _columnIndexes = new Hashtable();
	private Object[] _nullValues;
	private int _booleanDataType=DataStoreBuffer.DATATYPE_SHORT;
	private Object _booleanTrueValue=new Short((short)1);
	private Object _booleanFalseValue=new Short((short)0);

    public DSDataStoreDescriptor() {
        super();
    }

    public void addAlias(String table, String alias) {

        if (alias != null) {
            for (int i = 0; i < _aliases.size(); i++) {
                DSTableAliasDescriptor test = (DSTableAliasDescriptor) _aliases.elementAt(i);
                if (test.getAlias() != null && test.getAlias().equalsIgnoreCase(alias))
                    return;
            }
        } else {
            for (int i = 0; i < _aliases.size(); i++) {
                DSTableAliasDescriptor test = (DSTableAliasDescriptor) _aliases.elementAt(i);
                if (test.getTable().equalsIgnoreCase(table)) {
                    if (alias != null)
                        test.setAlias(alias);
                    return;
                } else if (test.getAlias() != null) {
                    if (test.getAlias().equalsIgnoreCase(table)) {
                        return;
                    }
                }
            }
        }

        DSTableAliasDescriptor d = new DSTableAliasDescriptor(table, alias);
        _aliases.addElement(d);
    }

    public synchronized void addColumn(DSColumnDescriptor col) {
        _columns.addElement(col);

        String cName = null;
        String cName2 = null;

        if (col.getInternalName() != null) {
            cName = col.getInternalName();
            if (col.getTable() == null)
                if (getDefaultTable() == null)
                    cName2 = col.getColumn();
                else
                    cName2 = getDefaultTable() + "." + col.getColumn();
            else
                cName2 = col.getTable() + "." + col.getColumn();
        } else {
            if (col.getTable() == null)
                if (getDefaultTable() == null)
                    cName = col.getColumn();
                else
                    cName = getDefaultTable() + "." + col.getColumn();
            else
                cName = col.getTable() + "." + col.getColumn();
        }

        Integer index = new Integer(_columns.size() - 1);
        String check = cName.toUpperCase();
        if (!_columnIndexes.containsKey(check))
            _columnIndexes.put(check, index);

        if (cName2 != null) {
            check = cName2.toUpperCase();
            if (!_columnIndexes.containsKey(check))
                _columnIndexes.put(check, index);
        }
    }

    public void addColumn(String table, String column, int type, boolean primaryKey, boolean updateable, String internalName) {
        if (table != null)
            addAlias(table, null);

        DSColumnDescriptor d = new DSColumnDescriptor(table, column, type, primaryKey, updateable);
        if (type == DataStoreBuffer.DATATYPE_BYTEARRAY) {
            d.setUseBind(DataStoreBuffer.BIND_TRUE);
            d.setConcurrency(false);
        }
        d.setInternalName(internalName);
        addColumn(d);
    }

    public void addJoin(String left, String right, boolean outer) {
        DSJoinDescriptor d = new DSJoinDescriptor(left, right, outer);
        _joins.addElement(d);
    }

    //fc 06/11/04: Added Method to specify the Relationship of the join. (Use for Master/Detail scenario)
    public void addJoin(String left, String right, boolean outer, int reltype) {
        DSJoinDescriptor d = new DSJoinDescriptor(left, right, outer, reltype);
        _joins.addElement(d);
    }

    public DSTableAliasDescriptor getAlias(int index) {
        if (index < 0 || index >= _aliases.size())
            return null;
        return (DSTableAliasDescriptor) _aliases.elementAt(index);
    }

    public int getAliasCount() {
        return _aliases.size();
    }

    public DSColumnDescriptor getColumn(int index) {
        if (index < 0 || index >= _columns.size())
            return null;
        return (DSColumnDescriptor) _columns.elementAt(index);
    }

    public int getColumnCount() {
        return _columns.size();
    }

    public int getColumnIndex(String column) {
        Integer i = (Integer) _columnIndexes.get(column.toUpperCase());
        if (i == null) {

            /*
            DAN Leave this it is useful for debugging problems
            MessageLog.writeInfoMessage("getColumnIndex : *** COLUMN " + column + " NOT FOUND ***", Props.LOG_LEVEL_9, this);
            */
            return -1;
        } else {
            return i.intValue();
        }

    }

    public String getDefaultTable() {
        return _defaultTable;
    }

    public boolean getDistinct() {
        return _distinct;
    }

    public String getGroupByClause() {
        return _groupByClause;
    }

    public String getHavingClause() {
        return _havingClause;
    }

    public DSJoinDescriptor getJoin(int index) {
        if (index < 0 || index >= _joins.size())
            return null;
        return (DSJoinDescriptor) _joins.elementAt(index);
    }

    public int getJoinCount() {
        return _joins.size();
    }

    public String getOrderByClause() {
        return _orderByClause;
    }

    public boolean getTrimStrings() {
        return _trimStrings;
    }

    public String getWhereClause() {
        return _whereClause;
    }

    public synchronized void removeColumn(String column) {
        int ndx = getColumnIndex(column);
        if (ndx > -1) {
            _columns.removeElementAt(ndx);
            Enumeration e = _columnIndexes.keys();
            while (e.hasMoreElements()) {
                String s = (String) e.nextElement();
                int ndx1 = ((Integer) _columnIndexes.get(s)).intValue();
                if (ndx1 == ndx)
                    _columnIndexes.remove(s);
                else if (ndx1 > ndx)
                    _columnIndexes.put(s, new Integer(ndx1 - 1));
            }
        }
    }

    public void removeJoin(String left, String right) {
        DSJoinDescriptor des = null;
        String jNameLeft = "";
        String jNameRight = "";
        // look through the columns
        for (int i = 0; i < getJoinCount(); i++) {
            des = getJoin(i);
            jNameLeft = "";
            jNameRight = "";

            // LEFT SIDE
            for (int leftIndex = 0; leftIndex < des.getLeftCount(); leftIndex++) {
                // go through append comma for all except for last
                if (des.getLeftCount() - 1 != leftIndex) {
                    jNameLeft += des.getLeftColumn(leftIndex) + ",";
                } else {
                    jNameLeft += des.getLeftColumn(leftIndex);
                }
            }

            // RIGHT SIDE
            for (int rightIndex = 0; rightIndex < des.getRightCount(); rightIndex++) {
                // go through append comma for all except for last
                if (des.getRightCount() - 1 != rightIndex) {
                    jNameRight += des.getRightColumn(rightIndex) + ",";
                } else {
                    jNameRight += des.getRightColumn(rightIndex);
                }
            }

            //
            if (left.equalsIgnoreCase(jNameLeft) && right.equalsIgnoreCase(jNameRight)) {
                // if found removw from _column vector
                _joins.removeElementAt(i);
                break;
            }
            // if found removw from _join vector

        }
    }

    public void removeTableAlias(String table) {
        DSTableAliasDescriptor des = null;
        // look through the columns
        for (int i = 0; i < getAliasCount(); i++) {
            des = getAlias(i);
            String tableName = null;
            tableName = des.getTable();
            if (table.equalsIgnoreCase(tableName)) {
                // if found removw from _column vector
                _aliases.removeElementAt(i);
                break;
            }
        }
    }

    public String resolveTable(String table) {
        if (table.equals(_defaultTable))
            return _defaultTable;
        for (int i = 0; i < getAliasCount(); i++) {
            DSTableAliasDescriptor d = getAlias(i);
            if (table.equals(d.getTable()))
                return table;
            else if (table.equals(d.getAlias()))
                return d.getTable();
        }
        return null;
    }

    public void setDefaultTable(String table) {
        _defaultTable = table;
    }

    public void setDistinct(boolean distinct) {
        _distinct = distinct;
    }

    public void setGroupByClause(String groupByClause) {
        _groupByClause = groupByClause;
    }

    public void setHavingClause(String havingClause) {
        _havingClause = havingClause;
    }

    public void setOrderByClause(String orderByClause) {
        _orderByClause = orderByClause;
    }

    public void setTrimStrings(boolean trim) {
        _trimStrings = trim;
    }

    public void setWhereClause(String whereClause) {
        _whereClause = whereClause;
    }
    
	public void setNullDefault(int type, Object value) {
		if (_nullValues == null)
			_nullValues = new Object[10];
		_nullValues[type] = value;	
	}	
	
	public Object getNullDefault(int type) {
		if (_nullValues == null)
			return null;
		return _nullValues[type];	
	}	
	
	public boolean areNullDefaultsDefined() {
		return (_nullValues != null);	
	}	
	public int getBooleanDataType() {
		return _booleanDataType;
	}
	public Object getBooleanFalseValue() {
		return _booleanFalseValue;
	}
	public Object getBooleanTrueValue() {
		return _booleanTrueValue;
	}
	public void setBooleanRepresentation(int dataType,Object trueValue, Object falseValue) {
		_booleanDataType=dataType;
		_booleanTrueValue=trueValue;
		_booleanFalseValue=falseValue;
	}	
}
