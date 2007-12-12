package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceFieldTag.java $
//$Author: Dan $
//$Revision: 19 $
//$Modtime: 6/11/03 4:58p $
/////////////////////////
import com.salmonllc.sql.*;

/**
 * Implements the field tag.
 */
public class DataSourceFieldTag extends BaseEmptyTag {
    String _tableName;
    String _fieldName;
    String _dataType;
    String _primaryKey;
    String _updateable;
    String _format;
    String _sortdir;
    String _aliasName;

    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    void addField(DataStoreBuffer buf, DataDictionary dict) {
        DataStore ds = (DataStore) buf;
        String name = getName();
        if (_fieldName != null && _tableName == null) {
            int pos = _fieldName.lastIndexOf(".");
            if (pos > -1 && _fieldName.indexOf('(') == -1 && _fieldName.indexOf(',') == -1) {
                _tableName = _fieldName.substring(0, pos);
                _fieldName = _fieldName.substring(pos + 1);
            }
        }

        try {
            _aliasName = _tableName;
            if (_aliasName == null)
                _aliasName = ds.getDefaultTable();

            if (_aliasName != null) {
                for (int i = 0; i < ds.getAliasCount(); i++) {
                    String alias = ds.getAlias(i);
                    if (alias != null && alias.equals(_aliasName)) {
                        _aliasName = ds.getTable(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }

        if (_tableName != null && ds.getDefaultTable() != null) {
            if (_tableName.equalsIgnoreCase(ds.getDefaultTable()))
                _tableName = null;
        }

        if (name == null) {
            if (_tableName != null)
                name = _tableName + "." + _fieldName;
            else
                name = _fieldName;
        }

        ds.addColumn(ds.computeTableName(_tableName), _fieldName, getDataType(dict), getPrimaryKey(dict), BaseTagHelper.stringToBoolean(_updateable, true), name);
        try {
            if (_format != null)
                ds.setFormat(name, _format);
        } catch (Exception e) {
        }


    }
    public Object clone() {
        DataSourceFieldTag f = new DataSourceFieldTag();
        f.setName(getName());
        f.setDatatype(_dataType);
        f.setFieldname(_fieldName);
        f.setFormat(_format);
        f.setPrimarykey(_primaryKey);
        f.setSortdir(_sortdir);
        f.setTablename(_tableName);
        f.setUpdateable(_updateable);
        return f;
    }
    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent() {
        BaseTagHelper h = getHelper();
        DataStoreBuffer buf = h.getDataSourceObject();
        DataSourceNestedTag par = h.getDataSourceNestedTag();

        try {
            if (par instanceof DataSourceDefinitionTag) {
                DataSourceTag t = h.getDataSourceTag();
                if (t != null)
                    t.addField((DataSourceFieldTag) this.clone());
            } else if (par instanceof DataSourceOrderTag) {
                String name = _fieldName;
                if (name == null)
                    name = getName();
                if (buf instanceof DataStore)
                    name = ((DataStore)buf).computeTableAndFieldName(name);
                ((DataSourceOrderTag) par).addColumn(name, _sortdir);
            } else if (par instanceof DataSourceGroupTag) {
                String name = _fieldName;
                if (name == null)
                    name = getName();
                if (buf instanceof DataStore)
                    name = ((DataStore)buf).computeTableAndFieldName(name);
                ((DataSourceGroupTag) par).addColumn(name);
            }

        } catch (Exception e) {
        }

        return null;
    }
    /**
     * Returns the DataType
     */
    private int getDataType(DataDictionary d) {
        if (_dataType == null)
            return d.getDSDataType(_aliasName + "." + _fieldName);
        else
            return translateDataType(_dataType);
    }
    /**
     * Gets the fieldname attribute.
     */
    public String getFieldname() {
        return _fieldName;
    }
    /**
     * Gets the format attribute.
     */
    public String getFormat() {
        return _format;
    }
    /**
     * Returns whether or not the field is a primary key
     */
    private boolean getPrimaryKey(DataDictionary d) {
        if (_primaryKey == null)
            return d.isPkey(_aliasName + "." + _fieldName);
        else
            return BaseTagHelper.stringToBoolean(_primaryKey, false);


    }

    /**
     * Returns CONV_DONT_CONVERT
     */
    public int getTagConvertType() {
        return CONV_DONT_CONVERT;
    }
    /**
     * Releases resources used by the tag
     */
    public void release() {
        super.release();
        _tableName = null;
        _fieldName = null;
        _dataType = null;
        _primaryKey = null;
        _updateable = null;
        _format = null;
        _sortdir = null;
        _aliasName = null;
    }
    /**
     * Sets the fieldname attribute.
     */
    public void setDatatype(String value) {
        _dataType = value;
    }
    /**
     * Sets the fieldname attribute.
     */
    public void setFieldname(String value) {
        _fieldName = value;
    }
    /**
     * Sets the format attribute.
     */
    public void setFormat(String value) {
        _format = value;
    }
    /**
     * Sets the primary key attribute.
     */
    public void setPrimarykey(String value) {
        _primaryKey = value;
    }
    /**
     * Sets the sortdir attribute.
     */
    public void setSortdir(String value) {
        _sortdir = value;
    }
    /**
     * Sets the tablename attribute.
     */
    public void setTablename(String value) {
        _tableName = value;
    }
    /**
     * Sets the updateable attribute.
     */
    public void setUpdateable(String value) {
        _updateable = value;
    }
    /**
     * Translates a string to a datastore datatype
     */
    static int translateDataType(String type) {
        if (type == null)
            return DataStore.DATATYPE_STRING;
        type = type.toUpperCase();
        if (type.equals("STRING"))
            return DataStore.DATATYPE_STRING;
        else if (type.startsWith("INT"))
            return DataStore.DATATYPE_INT;
        else if (type.equals("DATETIME"))
            return DataStore.DATATYPE_DATETIME;
        else if (type.equals("DOUBLE"))
            return DataStore.DATATYPE_DOUBLE;
        else if (type.equals("BYTEARRAY"))
            return DataStore.DATATYPE_BYTEARRAY;
        else if (type.equals("SHORT"))
            return DataStore.DATATYPE_SHORT;
        else if (type.equals("LONG"))
            return DataStore.DATATYPE_LONG;
        else if (type.equals("FLOAT"))
            return DataStore.DATATYPE_FLOAT;
        else if (type.equals("DATE"))
            return DataStore.DATATYPE_DATE;
        else if (type.equals("TIME"))
            return DataStore.DATATYPE_TIME;
        else
            return DataStore.DATATYPE_STRING;
    }
}
