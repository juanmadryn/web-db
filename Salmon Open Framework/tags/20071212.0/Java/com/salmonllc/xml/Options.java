package com.salmonllc.xml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/xml/Options.java $
//$Author: Dan $ 
//$Revision: 10 $ 
//$Modtime: 6/11/03 4:53p $ 
/////////////////////////
import java.util.*;
/**
 * This data structure is used to store Option details which can be a part of a Dropdown, readioButtonGroup etc.
 * This is used as a corresponding java object for the options.dtd XML schema.
 * Creation date: (7/26/01 1:59:20 PM)
 * @author: Administrator
 */
public class Options extends java.util.Hashtable {
	private String fieldType = "";
	private String fieldComponent = "";
	private String fieldTable = "";
	private String fieldInitColumn = "";
	private String fieldDescColumn = "";
	private boolean fieldMandatory = false;
/**
 * Values constructor comment.
 */
public Options() {
	super();
}
/**
 * Gets the component to be used by options.
 * Creation date: (7/26/01 5:23:19 PM)
 * @return java.lang.String
 */
public java.lang.String getComponent() {
	return fieldComponent;
}
/**
 * Gets the Table Desctiptor columne name
 * Creation date: (7/26/01 5:29:55 PM)
 * @return java.lang.String
 */
public java.lang.String getDescColumn() {
	return fieldDescColumn;
}
/**
 * Gets the column name from the XML file which defines the init for this option
 * Creation date: (7/26/01 5:28:57 PM)
 * @return java.lang.String
 */
public java.lang.String getInitColumn() {
	return fieldInitColumn;
}
/**
 * Gets the Keys mentioned in XML file.
 * Creation date: (7/26/01 2:43:22 PM)
 * @return int[]
 */
public int[] getIntKeys() throws Exception{

	Enumeration eKeys = keys();
	int[] iKeys = new int[size()];
	int count = 0;
	while(eKeys.hasMoreElements())
	{
		Object objKey = eKeys.nextElement();
		if(objKey instanceof String)
		{
			iKeys[count] = (new Integer((String)objKey)).intValue();
			count++;	
		}
	}
	return iKeys;
}
/**
 * Get the keys in form of strings for all options.
 * Creation date: (7/26/01 2:43:22 PM)
 * @return int[]
 */
public String[] getStringKeys() {
	
	Enumeration eKeys = keys();
	String[] sKeys = new String[size()];
	int count = 0;
	while(eKeys.hasMoreElements())
	{
		Object objKey = eKeys.nextElement();
		if(objKey instanceof String)
		{
			sKeys[count] = (String)objKey;
			count++;	
		}
	}
	return sKeys;
}
/**
 * Gets all the String objects present in form of options
 * Creation date: (7/26/01 2:43:22 PM)
 * @return int[]
 */
public String[] getStringObjects() {
	
	
	Enumeration eKeys = keys();
	String[] sObjects = new String[size()];
	int count = 0;
	while(eKeys.hasMoreElements())
	{
		Object objKey = eKeys.nextElement();
		if(objKey instanceof String)
		{
			sObjects[count] = (String)get(objKey);
			count++;	
		}
	}
	return sObjects;

}
/**
 * Get the table name used for extracting options
 * Creation date: (7/26/01 5:28:57 PM)
 * @return java.lang.String
 */
public java.lang.String getTable() {
	return fieldTable;
}
/**
 * Get the type used by the Options
 * Creation date: (7/26/01 2:54:31 PM)
 * @return java.lang.String
 */
public java.lang.String getType() {
	return fieldType;
}
/**
 * Get the flag if the option selection is mandatory. if false this introduces a bank option at top
 * Creation date: (8/10/01 1:55:28 PM)
 * @return boolean
 */
public boolean isMandatory() {
	return fieldMandatory;
}
/**
 * Sethe the componentused by the option
 * Creation date: (7/26/01 5:23:19 PM)
 * @param newComponent java.lang.String
 */
public void setComponent(java.lang.String newComponent) {
	fieldComponent = newComponent;
}
/**
 * Sets the description column used by options if options are retrived from the table
 * Creation date: (7/26/01 5:29:55 PM)
 * @param newDescColumn java.lang.String
 */
public void setDescColumn(java.lang.String newDescColumn) {
	fieldDescColumn = newDescColumn;
}
/**
 * Sets the init column used by options if options are retrived from the table
 * Creation date: (7/26/01 5:28:57 PM)
 * @param newInitColumn java.lang.String
 */
public void setInitColumn(java.lang.String newInitColumn) {
	fieldInitColumn = newInitColumn;
}
/**
 * Sets the mandatory flag for options
 * Creation date: (8/10/01 1:55:28 PM)
 * @param newMandatory boolean
 */
public void setMandatory(boolean newMandatory) {
	fieldMandatory = newMandatory;
}
/**
 * Sets the table name for the options
 * Creation date: (7/26/01 5:28:57 PM)
 * @param newTable java.lang.String
 */
public void setTable(java.lang.String newTable) {
	fieldTable = newTable;
}
/**
 * Sets the type of options
 * Creation date: (7/26/01 2:54:31 PM)
 * @param newType java.lang.String
 */
public void setType(java.lang.String newType) {
	fieldType = newType;
}
}
