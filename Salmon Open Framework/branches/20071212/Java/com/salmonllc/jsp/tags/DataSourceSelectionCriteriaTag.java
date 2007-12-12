package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/DataSourceSelectionCriteriaTag.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 7/31/02 7:12p $
/////////////////////////

import com.salmonllc.sql.*;
/**
 * Implements the SelectionCriteria tag.
 */
public class DataSourceSelectionCriteriaTag extends BaseEmptyTag implements TagConstants{
	String _prefix;
	String _fieldName;
	String _operator;
	String _value;
	String _connector;
	String _suffix;
/**
 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
 */
public com.salmonllc.html.HtmlComponent createComponent() {
	DataSourceNestedTag tag = getHelper().getDataSourceNestedTag();
	if (tag != null) {
		if (tag instanceof DataSourceSelectionTag) {
			AutoRetrieveCriteria c = ((DataSourceSelectionTag) tag).getAutoRetrieveCriteria();
			c.addCriteria(_prefix,_fieldName,_operator,_value,_suffix,_connector);
		}
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

	_prefix=null;
	_fieldName=null;
	_operator=null;
	_value=null;
	_connector=null;
	_suffix=null;
	
}
/**
 * Sets the connector attribute.
 */
public void setConnector(String value) {
	_connector = value;	
}
/**
 * Sets the fieldname attribute.
 */
public void setFieldname(String value) {
	_fieldName = value;	
}
/**
 * Sets the operator attribute.
 */
public void setOperator(String value) {
	if (value.equals(EQUALS))
	      value = OPER_EQUALS;
	else
	if (value.equals(NOT_EQUAL))
	      value = OPER_NOT_EQUAL;
	else
	if (value.equals(GREATER_THAN))
	      value = OPER_GREATER_THAN;
	else
	if (value.equals(GREATER_THAN_OR_EQUALS))
	      value = OPER_GREATER_THAN_OR_EQUALS;
	else
	if (value.equals(LESS_THAN))
	      value = OPER_LESS_THAN;
	else
	if (value.equals(LESS_THAN_OR_EQUALS))
	      value = OPER_LESS_THAN_OR_EQUALS;
	      
	_operator = value;	
}
/**
 * Sets the prefix attribute.
 */
public void setPrefix(String value) {
	_prefix = value;	
}
/**
 * Sets the suffix attribute.
 */
public void setSuffix(String value) {
	_suffix = value;	
}
/**
 * Sets the value attribute.
 */
public void setValue(String value) {
	_value = value;	
}
}
