package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceQBECriteriaTag.java $
//$Author: Dan $
//$Revision: 3 $
//$Modtime: 1/29/04 5:07p $
/////////////////////////
import java.util.HashMap;

import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.QBEBuilder;
/**
 * Implements the bucket tag.
 */
public class DataSourceQBECriteriaTag extends BaseEmptyTag {
	String _type;
	String _columns;
	private static HashMap _typeMap;
	static {
		_typeMap = new HashMap();
		_typeMap.put("EQUALS_IGNORE_CASE",new Integer(QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE));
		_typeMap.put("STARTS_WITH",new Integer(QBEBuilder.CRITERIA_TYPE_STARTS_WITH));
		_typeMap.put("STARTS_WITH_IGNORE_CASE",new Integer(QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE));
		_typeMap.put("STARTS_WITH_IGNORE_CASE_EXTENDED",new Integer(QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE_EXTENDED));
		_typeMap.put("CONTAINS",new Integer(QBEBuilder.CRITERIA_TYPE_CONTAINS));
		_typeMap.put("CONTAINS_IGNORE_CASE",new Integer(QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE));
		_typeMap.put("LTE",new Integer(QBEBuilder.CRITERIA_TYPE_LTE));
		_typeMap.put("LT",new Integer(QBEBuilder.CRITERIA_TYPE_LT));
		_typeMap.put("GTE",new Integer(QBEBuilder.CRITERIA_TYPE_GTE));
		_typeMap.put("GT",new Integer(QBEBuilder.CRITERIA_TYPE_GT));
		_typeMap.put("EQUALS",new Integer(QBEBuilder.CRITERIA_TYPE_EQUALS));
		_typeMap.put("NOT_EQUALS",new Integer(QBEBuilder.CRITERIA_TYPE_NOT_EQUALS));
		_typeMap.put("COMPLEX",new Integer(QBEBuilder.CRITERIA_TYPE_COMPLEX));
		_typeMap.put("IN",new Integer(QBEBuilder.CRITERIA_TYPE_IN));
		_typeMap.put("CUSTOM",new Integer(QBEBuilder.CRITERIA_TYPE_CUSTOM));
	}	
	
	/**
	 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
	 */
	public com.salmonllc.html.HtmlComponent createComponent() {
		BaseTagHelper h = getHelper();
		DataStoreBuffer buf = h.getDataSourceObject();
		if (! (buf instanceof QBEBuilder))
			return null;
			
		QBEBuilder qbe = (QBEBuilder) buf;
			
		String name = getName();
		String columns = _columns;
		Integer type = (Integer) _typeMap.get(_type.toUpperCase());
		if (type == null)
			return null;
			
		qbe.addCriteria(name, type.intValue(),columns);
		

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

		_type = null;
		_type = null;

	}

	/**
	 * Sets the columns attribute
	 */
	public void setColumns(String string) {
		_columns = string;
	}

	/**
	 * @Sets the type attribute
	 */
	public void setType(String string) {
		_type = string;
	}

}
