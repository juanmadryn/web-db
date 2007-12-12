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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSColumnDescriptor.java $
//$Author: Dan $
//$Revision: 16 $
//$Modtime: 10/29/04 11:15a $
/////////////////////////

import java.lang.reflect.*;
import java.util.Vector;
/**
 * This class is public as an implementation detail. It should not be used outside the framework.
 */
public class DSColumnDescriptor implements java.io.Serializable {
    private String _table;
    private String _column;
    private int _type;
    private boolean _primaryKey;
    private boolean _updateable;
    private boolean _autoIncrement;
    private String _internalName;
    private java.text.Format _format;
    private boolean _checkConcurrency = true;
    private int _useBind = DataStoreBuffer.BIND_DEFAULT;
    private Method _setMethod;
    private Method _getMethod;
    private Method[] _parentGetMethods;
    private Class _propertyClass;
    private Vector _validationRules;
    private BeanDataStore.BeanClassInfo _beanInfo;

    public DSColumnDescriptor() {
        super();
    }

    public DSColumnDescriptor(String table, String column, int type, boolean primaryKey, boolean updateable) {
        _table = table;
        _column = column;
        _type = type;
        _primaryKey = primaryKey;
        _updateable = updateable;
    }

    public String getColumn() {
        return _column;
    }

    public boolean getConcurrency() {
        return _checkConcurrency;
    }

    public java.text.Format getFormat() {
        return _format;
    }

    public Method getGetMethod() {
        return _getMethod;
    }

    public String getInternalName() {
        return _internalName;
    }

    public Class getPropertyClass() {
        return _propertyClass;
    }

    public Method getSetMethod() {
        return _setMethod;
    }

    public String getTable() {
        return _table;
    }

    public int getType() {
        return _type;
    }

    public int getUseBind() {
        return _useBind;
    }

    public boolean isPrimaryKey() {
        return _primaryKey;
    }

    public boolean isUpdateable() {
        return _updateable;
    }

    public void setColumn(String column) {
        _column = column;
    }

    public void setConcurrency(boolean truefalse) {
        _checkConcurrency = truefalse;
    }

    public void setFormat(java.text.Format format) {
        _format = format;
    }

    public void setGetMethod(Method newGetMethod) {
        _getMethod = newGetMethod;
    }

    public void setInternalName(String internalName) {
        _internalName = internalName;
    }

    public void setPrimaryKey(boolean primaryKey) {
        _primaryKey = primaryKey;
    }

    public void setPropertyClass(Class newPropertyClass) {
        _propertyClass = newPropertyClass;
    }

    public void setSetMethod(Method newSetMethod) {
        _setMethod = newSetMethod;
    }

    public void setTable(String table) {
        _table = table;
    }

    public void setType(int type) {
        _type = type;
    }

    public void setUpdateable(boolean updateable) {
        _updateable = updateable;
    }

    public void setUseBind(int bind) {
        _useBind = bind;
    }

    public void setAutoIncrement(boolean autoInc) {
        _autoIncrement = autoInc;
    }

    public boolean isAutoIncrement() {
        return _autoIncrement;
    }

    public String toString() {
        return " Table: " + _table + " Column: " + _column + " InternalName: " + _internalName;
    }

    public int addRule(ValidationRule rule) {
        if (_validationRules == null)
            _validationRules = new Vector();
        _validationRules.add(rule);
        return _validationRules.size() - 1;
    }

    public void clearValidationRules() {
        _validationRules = null;
    }

    public int getRuleCount() {
        if (_validationRules == null)
            return 0;
        else
            return _validationRules.size();
    }

    public ValidationRule getRule(int no) {
        return (ValidationRule)_validationRules.elementAt(no);
    }
   
	public Method[] getParentGetMethods() {
		return _parentGetMethods;
	}
	public void setParentGetMethods(Method[] parentGetMethods) {
		_parentGetMethods = parentGetMethods;
	}
	
	public BeanDataStore.BeanClassInfo getBeanInfo() {
		return _beanInfo;
	}
	public void setBeanInfo(BeanDataStore.BeanClassInfo beanInfo) {
		_beanInfo = beanInfo;
	}
}
