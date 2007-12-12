package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DataSourceSelectionTag.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 7/31/02 6:11p $
/////////////////////////

import com.salmonllc.sql.AutoRetrieveCriteria;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Implements the selection tag.
 */
public class DataSourceSelectionTag extends DataSourceNestedTag {
    AutoRetrieveCriteria _crit;

    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp) {
        if (comp instanceof DataStore) {
            for (int i = 0; i < _crit.getCriteriaCount(); i++)
                _crit.setColumn(i,((DataStore) comp).computeTableAndFieldName(_crit.getColumn(i)));
        }
        comp.setAutoRetrieveCriteria(_crit);

    }
    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent() {
        _crit = new AutoRetrieveCriteria();
        return null;
    }
    /**
     * Returns the object AutoRetrieveCriteria Object
     */
    public AutoRetrieveCriteria getAutoRetrieveCriteria() {
        return _crit;
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
        _crit = null;

    }
}
