package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceAliasTag.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 4/15/03 1:56a $
/////////////////////////
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
/**
 * Implements the bucket tag.
 */
public class DataSourceAliasTag extends BaseEmptyTag {
	String _tableName;
	String _alias;
/**
 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
 */
public com.salmonllc.html.HtmlComponent createComponent() {
	BaseTagHelper h = getHelper();
	DataStoreBuffer buf = h.getDataSourceObject();

	if (buf instanceof DataStore) {
		DataStore ds = (DataStore) buf;
		ds.addTableAlias(ds.computeTableName(_tableName),_alias);
	}
	
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
	
	_tableName=null;
	_alias=null;
	
}
/**
 * Sets the alias attribute.
 */
public void setAlias(String value) {
	_alias = value;	
}
/**
 * Sets the table name attribute.
 */
public void setTablename(String value) {
	_tableName = value;	
}
}
