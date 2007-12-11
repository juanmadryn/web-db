//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/PropertyTag.java $
//$Author: Srufle $
//$Revision: 14 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////


import java.util.Hashtable;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.util.Util;

/**
 * This tag creates an Html Style
 */

public class PropertyTag extends BaseEmptyTag implements TagConstants{
	private String _component;
	private String _propertyName;
	private String _expression;
	private String _dataSource;


/**
 * Converts text expression operators to the actual operators (ex GREATER_THAN to >).
 */
public String convertExpressionOperators(String expression) {
	String retStr=expression;
	retStr= Util.replaceString(retStr,EQUALS,"==",1,-1);
	retStr= Util.replaceString(retStr,NOT_EQUAL,"!=",1,-1);
	retStr= Util.replaceString(retStr,GREATER_THAN,OPER_GREATER_THAN,1,-1);
	retStr= Util.replaceString(retStr,GREATER_THAN_OR_EQUALS,OPER_GREATER_THAN_OR_EQUALS,1,-1);
	retStr= Util.replaceString(retStr,LESS_THAN,OPER_LESS_THAN,1,-1);
	retStr= Util.replaceString(retStr,LESS_THAN_OR_EQUALS,OPER_LESS_THAN_OR_EQUALS,1,-1);
    // fc:12/23/02 added the replacement of &QUOT; to ' to allow for JSP Engines which have problems with sinqle quotes in tags. e.g. Silverstream
    retStr= Util.replaceString(retStr,"&QUOT;","'",1,-1);
	return retStr;
}
public HtmlComponent createComponent() {
  String ds = _dataSource;
  DataStoreBuffer datasource = null;
  if (ds != null)
   {
		Hashtable htDs = getHelper().getController().getDataSourceTable();
    	datasource = (DataStoreBuffer)htDs.get(ds);
   }
  String expression = convertExpressionOperators(_expression);

  getHelper().getController().assignPropertyExpression(_component,_propertyName,expression,datasource);

  return null;
}
/**
 * Returns the component attribute
 */

public String getComponent() {
	return _component;
}
/**
 * Returns the dataSource attribute
 */

public String getDatasource() {
	return _dataSource;
}
/**
 * Returns the expression attribute
 */

public String getExpression() {
	return _expression;
}
/**
 * Returns the Property Name attribute
 */

public String getPropertyname() {
	return _propertyName;
}
public void release() {
    super.release();
    _component = null;
    _propertyName = null;
    _expression = null;

}
/**
 * Sets the component attribute
 */

public void setComponent(String val) {
	_component = val;
}
/**
 * Sets the dataSource attribute
 */

public void setDatasource(String val) {
	_dataSource = val;
}
/**
 * Sets the Expression attribute
 */
public void setExpression(String val) {
	_expression = val;
}
/**
 * Sets the PropertyName attribute
 */
public void setPropertyname(String val) {
	_propertyName = val;
}

/**
 * Returns the type of DreamWeaver conversion that this tag uses.
 */
public int getTagConvertType() {
	return CONV_DONT_CONVERT;
}
}
