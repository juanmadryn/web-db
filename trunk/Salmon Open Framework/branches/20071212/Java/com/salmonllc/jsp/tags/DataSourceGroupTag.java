package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DataSourceGroupTag.java $
//$Author: Srufle $
//$Revision: 6 $
//$Modtime: 7/31/02 7:13p $
/////////////////////////
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Implements the group tag.
 */
public class DataSourceGroupTag extends DataSourceNestedTag
{
    StringBuffer _fields = new StringBuffer();

    void addColumn(String field)
    {
        if (_fields.length() > 0)
            _fields.append(",");

        _fields.append(field);
    }
    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp)
    {
        if (comp instanceof DataStore && _fields.length() > 0)
        {
            DataStore ds = (DataStore) comp;
            ds.setGroupBy(_fields.toString());
        }

    }
    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent()
    {
        _fields.setLength(0);
        return null;
    }
    /**
     * Returns CONV_DONT_CONVERT
     */
    public int getTagConvertType()
    {
        return CONV_DONT_CONVERT;
    }
    /**
     * Release resources used by the tag.
     */
    public void release()
    {
        super.release();

        _fields.setLength(0);

    }
}
