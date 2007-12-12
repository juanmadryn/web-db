package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceDefinitionTag.java $
//$Author: Srufle $
//$Revision: 9 $
//$Modtime: 4/15/03 1:56a $
/////////////////////////
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Data Source Definition Tag
 */
public class DataSourceDefinitionTag extends DataSourceNestedTag
{
    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp)
    {
        if (getHelper().getDataSourceTag().getDSType() == DataSourceTag.TYPE_XML)
        {
            String xml = getBodyContentData(false);
            getHelper().getDataSourceTag().setXmlDef(xml);
        }

    }
}
