package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/ResultSetMetaData.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 7/31/02 7:12p $ 
/////////////////////////
/**
 * This object is used to store the metadata, header of the datastore.
 * Creation date: (7/18/01 3:37:45 PM)
 * @author: Administrator
 */
public class ResultSetMetaData {
	private java.util.Vector columnMetaData = new java.util.Vector();
/**
 * ResultSetMetaData constructor comment.
 */
public ResultSetMetaData() {
	super();
}
/**
 * Gets the MetaData Vector. This vector contains information for all the columns specified in Result Set
 * Creation date: (7/18/01 4:21:44 PM)
 * @return java.util.Vector
 */
public java.util.Vector getMetaData() {
	return columnMetaData;
}
/**
 * Sets the ResultSetMetaData
 * Creation date: (7/18/01 4:21:44 PM)
 * @param newMetaData java.util.Vector
 */
public void setMetaData(java.util.Vector newMetaData) {
	columnMetaData = newMetaData;
}
}
