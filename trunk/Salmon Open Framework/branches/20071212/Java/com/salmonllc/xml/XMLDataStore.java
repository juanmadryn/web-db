package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/XMLDataStore.java $
//$Author: Srufle $ 
//$Revision: 8 $ 
//$Modtime: 7/31/02 7:13p $ 
/////////////////////////
/**
 * The XML Datastore is used to store any XML file or data from datastore. Creation date: (8/7/01 11:58:02 AM)
 * @author  : Deepak Agarwal
 */
public class XMLDataStore {

	private ResultSetMetaData rsm = null;
	private ResultSetData rsData = null;
/**
 * XMLDataStore constructor comment.
 */
public XMLDataStore() {
	super();
}
/**
 * Gets the ResultSetData for the XML file read Creation date: (8/7/01 11:59:16 AM)
 * @return  com.salmonllc.xml.ResultSetData
 * @uml.property  name="rsData"
 */
public ResultSetData getRsData() {
	return rsData;
}
/**
 * Gets the ResultSetMetaData (Column definition) for the XML file read Creation date: (8/7/01 11:59:16 AM)
 * @return  com.salmonllc.xml.ResultSetMetaData
 * @uml.property  name="rsm"
 */
public ResultSetMetaData getRsm() {
	return rsm;
}
/**
 * Creation date: (8/7/01 11:59:16 AM)
 * @param newRsData  com.salmonllc.xml.ResultSetData
 * @uml.property  name="rsData"
 */
public void setRsData(ResultSetData newRsData) {
	rsData = newRsData;
}
/**
 * Creation date: (8/7/01 11:59:16 AM)
 * @param newRsm  com.salmonllc.xml.ResultSetMetaData
 * @uml.property  name="rsm"
 */
public void setRsm(ResultSetMetaData newRsm) {
	rsm = newRsm;
}
}
