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
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/JspDetailForm.java $
//$Author: Dan $ 
//$Revision: 13 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////

/**
 * This class serves as the component used in JSP pages to create the detail form. 
 * This component uses an XML file as datadefinition.
 * Creation date: (7/20/01 2:48:19 PM)
 * @author: Administrator
 */
public class JspDetailForm extends JspContainer
{
    private String fieldName = "";
    private String fieldDataDictionary = "";
    private String fieldSaveButton = "";
    private String fieldDeleteButton = "";
    private String fieldCancelButton = "";
    private String fieldCaption = "";
/**
 * JspDetailForm constructor comment.
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 */
public JspDetailForm(String name, com.salmonllc.html.HtmlPage p) {
	super(name, p);
}
/**
 * This method gets the cancel button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @return java.lang.String
 */
public java.lang.String getCancelButton() {
	return fieldCancelButton;
}
/**
 * This method sets the caption on a detail form
 * Creation date: (8/2/01 2:11:13 PM)
 * @return java.lang.String
 */
public java.lang.String getCaption() {
	return fieldCaption;
}
/**
 * This method sets the data definition used by Detail form to create and implements the functinality.
 * Creation date: (7/20/01 2:49:44 PM)
 * @return java.lang.String
 */
public java.lang.String getDataDictionary() {
	return fieldDataDictionary;
}
/**
 * This method gets the delete button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @return java.lang.String
 */
public java.lang.String getDeleteButton() {
	return fieldDeleteButton;
}
/**
 * Gets the name of the JspDetailForm Component
 * Creation date: (7/20/01 2:49:44 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return fieldName;
}
/**
 * This method gets the save button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @return java.lang.String
 */
public java.lang.String getSaveButton() {
	return fieldSaveButton;
}
/**
 * This method sets the cancel button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @param newCancelButton java.lang.String
 */
public void setCancelButton(java.lang.String newCancelButton) {
	fieldCancelButton = newCancelButton;
}
/**
 * This method sets the caption on a detail form
 * Creation date: (8/2/01 2:11:13 PM)
 * @param newCaption java.lang.String
 */
public void setCaption(java.lang.String newCaption) {
	fieldCaption = newCaption;
}
/**
 * This method sets the datadefinition used by the detail form
 * Creation date: (7/20/01 2:49:44 PM)
 * @param newDataDictionary java.lang.String
 */
public void setDataDictionary(java.lang.String newDataDictionary) {
	fieldDataDictionary = newDataDictionary;
}
/**
 * This method sets the delete button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @param newDeleteButton java.lang.String
 */
public void setDeleteButton(java.lang.String newDeleteButton) {
	fieldDeleteButton = newDeleteButton;
}
/**
 * Sets the name of the component
 * Creation date: (7/20/01 2:49:44 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	fieldName = newName;
}
/**
 * This method sets the save button on a detail form
 * Creation date: (8/2/01 12:59:30 PM)
 * @param newSaveButton java.lang.String
 */
public void setSaveButton(java.lang.String newSaveButton) {
	fieldSaveButton = newSaveButton;
}
}
