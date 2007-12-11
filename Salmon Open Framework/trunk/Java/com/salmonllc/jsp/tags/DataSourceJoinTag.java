package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceJoinTag.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////
import java.util.Vector;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * Implements the bucket tag.
 */
public class DataSourceJoinTag extends DataSourceNestedTag {
    String _outer;
    Vector _leftSide = new Vector();
    Vector _rightSide = new Vector();

    void addColumn(String leftSide, String rightSide) {
        _leftSide.add(leftSide);
        _rightSide.add(rightSide);
    }
    /**
     * This method can be used by tags that need to change a datastore after all the sub tags have been initialized.
     */
    public void afterInit(DataStoreBuffer comp) {
        if (comp instanceof DataStore && _leftSide.size() > 0) {
            StringBuffer left = new StringBuffer();
            StringBuffer right = new StringBuffer();
            for (int i = 0; i < _leftSide.size();i++) {
                left.append(((DataStore) comp).computeTableAndFieldName((String) _leftSide.elementAt(i)));
                left.append(",");
                right.append(((DataStore) comp).computeTableAndFieldName((String) _rightSide.elementAt(i)));
                right.append(",");
            }
            left.setLength(left.length() - 1);
            right.setLength(right.length() - 1);
            DataStore ds = (DataStore) comp;
            ds.addJoin(left.toString(), right.toString(), BaseTagHelper.stringToBoolean(_outer, false));
        }

    }
    /**
     * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
     */
    public com.salmonllc.html.HtmlComponent createComponent() {
        _leftSide.removeAllElements();
        _rightSide.removeAllElements();
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

        _leftSide.removeAllElements();
        _rightSide.removeAllElements();
        _outer = null;

    }
    /**
     * Sets the outer attribute.
     */
    public void setOuter(String value) {
        _outer = value;
    }
}
