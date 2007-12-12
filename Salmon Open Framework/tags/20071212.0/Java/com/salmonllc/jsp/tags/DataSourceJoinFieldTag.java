package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceJoinFieldTag.java $
//$Author: Srufle $
//$Revision: 6 $
//$Modtime: 4/15/03 1:56a $
/////////////////////////
/**
 * Implements the bucket tag.
 */
public class DataSourceJoinFieldTag extends BaseEmptyTag {
	String _field1;
	String _field2;
/**
 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
 */
public com.salmonllc.html.HtmlComponent createComponent() {
	DataSourceJoinTag tag = getHelper().getDataSourceJoinTag();
	if (tag != null) {
		tag.addColumn(_field1,_field2);
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
	
	_field1 = null;
	_field2 = null;

	
}
/**
 * Sets the fieldName1 attribute.
 */
public void setFieldname1(String value) {
	_field1 = value;	
}
/**
 * Sets the fieldName2 attribute.
 */
public void setFieldname2(String value) {
	_field2 = value;	
}
}
