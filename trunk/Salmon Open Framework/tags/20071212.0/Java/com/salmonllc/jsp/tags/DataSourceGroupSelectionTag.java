package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DataSourceGroupSelectionTag.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 7/31/02 7:13p $
/////////////////////////
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Implements the selection tag.
 */
public class DataSourceGroupSelectionTag extends DataSourceSelectionTag
{

    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp)
    {
        if (comp instanceof DataStore)
            ((DataStore) comp).setGroupAutoRetrieveCriteria(_crit);


    }
}
