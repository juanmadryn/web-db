package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/Row.java $
//$Author: Srufle $ 
//$Revision: 6 $ 
//$Modtime: 7/31/02 6:11p $ 
/////////////////////////
/**
 * This object stores all the columns in form of a Hashtable.
 * Creation date: (8/7/01 12:00:55 PM)
 * @author: Administrator
 */
public class Row extends java.util.Hashtable {
/**
 * Row constructor comment.
 */
public Row() {
	super();
}
/**
 * Row constructor comment.
 * @param initialCapacity int
 */
public Row(int initialCapacity) {
	super(initialCapacity);
}
/**
 * Row constructor comment.
 * @param initialCapacity int
 * @param loadFactor float
 */
public Row(int initialCapacity, float loadFactor) {
	super(initialCapacity, loadFactor);
}
/**
 * Row constructor comment.
 * @param t java.util.Map
 */
public Row(java.util.Map t) {
	super(t);
}
}
