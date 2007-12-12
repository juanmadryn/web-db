package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/ResultSetData.java $
//$Author: Srufle $ 
//$Revision: 8 $ 
//$Modtime: 7/31/02 6:11p $ 
/////////////////////////
/**
 * This object contains the rows of a datastore. Each row is a collection (Vector) of Columns objects.
 * Creation date: (7/20/01 12:02:58 PM)
 * @author: Administrator
 */
public class ResultSetData {

	private java.util.Vector fieldRows = new java.util.Vector();
/**
 * ResultSetData constructor comment.
 */
public ResultSetData() {
	super();
}
/**
 * Get the vector of all rows of a datastore
 * Creation date: (8/7/01 12:01:53 PM)
 * @return java.util.Vector
 */
public java.util.Vector getRows() {
	return fieldRows;
}
/**
 * Sets the Rows for a Result set
 * Creation date: (8/7/01 12:01:53 PM)
 * @param newRows java.util.Vector
 */
public void setRows(java.util.Vector newRows) {
	fieldRows = newRows;
}
}
