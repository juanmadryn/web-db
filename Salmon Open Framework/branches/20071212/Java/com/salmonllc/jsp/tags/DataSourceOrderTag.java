package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DataSourceOrderTag.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 7/31/02 7:13p $
/////////////////////////

import com.salmonllc.fatwire.UEDataStore;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Implements the order by tag.
 */
public class DataSourceOrderTag extends DataSourceNestedTag {
    StringBuffer _fields = new StringBuffer();

    void addColumn(String field, String dir) {
        String d = "ASC";
        if (dir != null) {
            if (dir.charAt(0) == 'D' || dir.charAt(0) == 'd')
                d = "DESC";
        }

        if (_fields.length() > 0) {
            _fields.append(",");
        }
        _fields.append(field);
        _fields.append(' ');
        _fields.append(d);

    }
    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp) {
        if (_fields.length() > 0) {
            if (comp instanceof DataStore) {
                DataStore ds = (DataStore) comp;
                ds.setOrderBy(_fields.toString());
            } else if (comp instanceof UEDataStore) {
                UEDataStore ds = (UEDataStore) comp;
                ds.setOrderBy(_fields.toString());
            }
        }
    }
    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent() {
        _fields.setLength(0);
        return null;
    }
    /**
     * Returns CONV_DONT_CONVERT
     */
    public int getTagConvertType() {
        return CONV_DONT_CONVERT;
    }
    /**
     * Release resources used by the tag.
     */
    public void release() {
        super.release();

        _fields.setLength(0);

    }
}
