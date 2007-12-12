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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspSearchForm.java $
//$Author: Srufle $ 
//$Revision: 4 $ 
//$Modtime: 4/15/03 1:55a $ 
/////////////////////////

import com.salmonllc.sql.DataStoreBuffer;
/**
 * This method creates the SearchForm component. This can be used as a separate component to create the criteria or can be used in components like JspListForm etc. Creation date: (7/20/01 2:48:19 PM)
 * @author  : Administrator
 */
public class JspSearchForm extends JspContainer
{
    private DataStoreBuffer _ds;
    private String criteria;
    private String searchbutton;
    private String advancesearchlink;

    private boolean fieldCasesensitive;
    private boolean fieldExactmatch;
    private boolean fieldLeadingwildcard;

/**
 * JspListForm constructor comment.
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 */
public JspSearchForm(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p); 
}
/**
 * Gets the Advance Search link page name Creation date: (7/30/01 3:23:01 PM)
 * @return  java.lang.String
 * @uml.property  name="advancesearchlink"
 */
public java.lang.String getAdvancesearchlink() {
	return advancesearchlink;
}
/**
 * Gets the criteria created by search form Creation date: (7/30/01 3:17:32 PM)
 * @return  java.lang.String
 * @uml.property  name="criteria"
 */
public java.lang.String getCriteria() {
	return criteria;
}
/**
 * Gets the datastore used by search form to create or set the criteria
 * Creation date: (7/25/01 10:13:56 AM)
 * @return DataStoreBuffer
 */
public DataStoreBuffer getDataStore() {
	return _ds;
}
/**
 * Gets the search button used by Search form Creation date: (7/30/01 3:17:32 PM)
 * @return  java.lang.String
 * @uml.property  name="searchbutton"
 */
public java.lang.String getSearchbutton() {
	return searchbutton;
}
/**
 * Gets the value of Case sensitive Flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @return boolean
 */
public boolean isCasesensitive() {
	return fieldCasesensitive;
}
/**
 * Gets the value of exact match flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @return boolean
 */
public boolean isExactmatch() {
	return fieldExactmatch;
}
/**
 * Gets the value of wildcard flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @return boolean
 */
public boolean isLeadingwildcard() {
	return fieldLeadingwildcard;
}
/**
 * Sets the advance search link. Creation date: (7/30/01 3:23:01 PM)
 * @param newAdvancesearchlink  java.lang.String
 * @uml.property  name="advancesearchlink"
 */
public void setAdvancesearchlink(java.lang.String newAdvancesearchlink) {
	advancesearchlink = newAdvancesearchlink;
}
/**
 * Sets the value of Case Sensitive search flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @param newCasesensitive boolean
 */
public void setCasesensitive(boolean newCasesensitive) {
	fieldCasesensitive = newCasesensitive;
}
/**
 * Sets the criteria created by searchform Creation date: (7/30/01 3:17:32 PM)
 * @param newCriteria  java.lang.String
 * @uml.property  name="criteria"
 */
public void setCriteria(java.lang.String newCriteria) {
	criteria = newCriteria;
}
/**
 * Sets the datastore used by Search form to create or perform the search criteria
 * Creation date: (7/25/01 10:13:56 AM)
 * @param new_ds DataStoreBuffer
 */
public void setDataStoreBuffer(DataStoreBuffer new_ds) {
	_ds = new_ds;
}
/**
 * Sets the value of exact match flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @param newExactmatch boolean
 */
public void setExactmatch(boolean newExactmatch) {
	fieldExactmatch = newExactmatch;
}
/**
 * Sets the value of Wild card flag
 * Creation date: (7/31/01 9:34:17 AM)
 * @param newFieldleadingwildcard boolean
 */
public void setLeadingwildcard(boolean newFieldleadingwildcard) {
	fieldLeadingwildcard = newFieldleadingwildcard;
}
/**
 * Sets the search button to be used by JspSearchForm Creation date: (7/30/01 3:17:32 PM)
 * @param newSearchbutton  java.lang.String
 * @uml.property  name="searchbutton"
 */
public void setSearchbutton(java.lang.String newSearchbutton) {
	searchbutton = newSearchbutton;
}
}
