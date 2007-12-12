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
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/JspListForm.java $
//$Author: Dan $ 
//$Revision: 14 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////

/**
 * This class is used to create the ListForm component in the framework.  This component is a formatiuon of a Search Form and JSP data table.  This is used when search form and datatable are specified in JSP pages Creation date: (7/20/01 2:48:19 PM)
 * @author  : Deepak Agarwal
 */
public class JspListForm extends JspContainer
{
    private String fieldName = "";
    private String fieldDataDictionary = "";
    private String fieldButtons = "";
    private String fieldDetailPageName = "";
    private String fieldCaption = "";
    private JspSearchForm fieldSearchForm = null;
    private JspDataTable fieldDataTable = null;
/**
 * JspListForm constructor comment.
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 */
public JspListForm(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p);
}
/**
 * Gets the buttons used by Listform
 * Creation date: (4/5/02 2:53:04 PM)
 * @return java.lang.String
 */
public java.lang.String getButtons() {
	return fieldButtons;
}
/**
 * Gets the caption set on the ListForm component
 * Creation date: (8/2/01 2:13:05 PM)
 * @return java.lang.String
 */
public java.lang.String getCaption() {
	return fieldCaption;
}
/**
 * Gets the data definition, XML file name.
 * Creation date: (7/20/01 2:49:44 PM)
 * @return java.lang.String
 */
public java.lang.String getDataDictionary() {
	return fieldDataDictionary;
}
/**
 * Gets the JspDatatable contained in the Listform.
 * Creation date: (7/27/01 4:35:38 PM)
 * @return com.salmonllc.jsp.JspDataTable
 */
public JspDataTable getDataTable() {
	return fieldDataTable;
}
/**
 * Gets the name of the detail page used.
 * Creation date: (7/25/01 3:43:23 PM)
 * @return java.lang.String
 */
public java.lang.String getDetailPageName() {
	return fieldDetailPageName;
}
/**
 * Gets the name of the component.
 * Creation date: (7/20/01 2:49:44 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return fieldName;
}
/**
 * Gets the Search Form component used by Listform.
 * Creation date: (7/27/01 4:33:29 PM)
 * @return com.salmonllc.jsp.JspSearchForm
 */
public JspSearchForm getSearchForm() {
	return fieldSearchForm;
}
/**
 * This method sets the buttons used in listform.
 * Creation date: (4/5/02 2:53:04 PM)
 * @param newButtons java.lang.String
 */
public void setButtons(java.lang.String newButtons) {
	fieldButtons = newButtons;
}
/**
 * This method sets the caption for the listform
 * Creation date: (8/2/01 2:13:05 PM)
 * @param newCaption java.lang.String
 */
public void setCaption(java.lang.String newCaption) {
	fieldCaption = newCaption;
}
/**
 * Sets the data definition, XML file, used by List form
 * Creation date: (7/20/01 2:49:44 PM)
 * @param newDataDictionary java.lang.String
 */
public void setDataDictionary(java.lang.String newDataDictionary) {
	fieldDataDictionary = newDataDictionary;
}
/**
 * Sets the Data Table component.
 * Creation date: (7/27/01 4:35:38 PM)
 * @param newDataTable com.salmonllc.jsp.JspDataTable
 */
public void setDataTable(JspDataTable newDataTable) {
	fieldDataTable = newDataTable;
}
/**
 * Sets the JSP page name for Detail form is used in conjuction with Listform.
 * Creation date: (7/25/01 3:43:23 PM)
 * @param newDetailPageName java.lang.String
 */
public void setDetailPageName(java.lang.String newDetailPageName) {
	fieldDetailPageName = newDetailPageName;
}
/**
 * Sets the name of the Listform component
 * Creation date: (7/20/01 2:49:44 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	fieldName = newName;
}
/**
 * Sets the SearchForm component.
 * Creation date: (7/27/01 4:33:29 PM)
 * @param newSearchForm com.salmonllc.jsp.JspSearchForm
 */
public void setSearchForm(JspSearchForm newSearchForm) {
	fieldSearchForm = newSearchForm;
}
}
