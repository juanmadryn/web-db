package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceBucketTag.java $
//$Author: Srufle $
//$Revision: 6 $
//$Modtime: 4/15/03 1:56a $
/////////////////////////
import com.salmonllc.sql.DataStoreBuffer;
/**
 * Implements the bucket tag.
 */
public class DataSourceBucketTag extends BaseEmptyTag {
	String _dataType;
	String _format;
/**
 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
 */
public com.salmonllc.html.HtmlComponent createComponent() {
	BaseTagHelper h = getHelper();
	DataStoreBuffer buf = h.getDataSourceObject();

	String name = getName();
	buf.addBucket(name,DataSourceFieldTag.translateDataType(_dataType));
	try {
		if (_format != null) 
			buf.setFormat(name,_format);
	} catch (Exception e) {} 		
	
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
	
	_dataType=null;
	_format=null;
	
}
/**
 * Sets the fieldname attribute.
 */
public void setDatatype(String value) {
	_dataType = value;	
}
/**
 * Sets the format attribute.
 */
public void setFormat(String value) {
	_format = value;	
}
}
