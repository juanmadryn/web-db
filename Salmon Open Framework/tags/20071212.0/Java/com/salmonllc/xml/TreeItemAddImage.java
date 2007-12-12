package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/TreeItemAddImage.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 7/31/02 7:13p $ 
/////////////////////////
/**
 * This datastructure is used to store the attributes for Image which is added to a node, TreeItem.
 * Creation date: (8/21/01 4:57:38 PM)
 * @author: DeepakAgarwal
 */
public class TreeItemAddImage {

	private String fieldSrc = "";
	private String fieldHref = "";
	private String fieldText = "";
	private boolean fieldVisible = true;
	private String fieldName = "";
	
/**
 * TreeItemAddImage constructor comment.
 */
public TreeItemAddImage() {
	super();
}
/**
 * Gets the link, href, if specified for additional image on tree nodes
 * Creation date: (8/21/01 4:59:02 PM)
 * @return java.lang.String
 */
public java.lang.String getHref() {
	return fieldHref;
}
/**
 * Gets the name for the TreeAdditionalAddImage Component
 * Creation date: (8/21/01 4:59:02 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return fieldName;
}
/**
 * Gets the source for the image used by Tree AdditionalImage component
 * Creation date: (8/21/01 4:59:02 PM)
 * @return java.lang.String
 */
public java.lang.String getSrc() {
	return fieldSrc;
}
/**
 * Gets the text used to display on Tree Additinal Image
 * Creation date: (8/21/01 4:59:02 PM)
 * @return java.lang.String
 */
public java.lang.String getText() {
	return fieldText;
}
/**
 * Gets the flag 'Visible' for TreeAdditinalImage Tag
 * Creation date: (8/21/01 4:59:02 PM)
 * @return boolean
 */
public boolean isVisible() {
	return fieldVisible;
}
/**
 * Sets the Href link for Additional Image on Tree node
 * Creation date: (8/21/01 4:59:02 PM)
 * @param newHref java.lang.String
 */
public void setHref(java.lang.String newHref) {
	fieldHref = newHref;
}
/**
 * Sets the name of the component
 * Creation date: (8/21/01 4:59:02 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	fieldName = newName;
}
/**
 * Sets the source for the additional image for a tree
 * Creation date: (8/21/01 4:59:02 PM)
 * @param newSrc java.lang.String
 */
public void setSrc(java.lang.String newSrc) {
	fieldSrc = newSrc;
}
/**
 * Sets the text used by component
 * Creation date: (8/21/01 4:59:02 PM)
 * @param newText java.lang.String
 */
public void setText(java.lang.String newText) {
	fieldText = newText;
}
/**
 * Sets the visibility of Tree Additinal images on a node
 * Creation date: (8/21/01 4:59:02 PM)
 * @param newVisible boolean
 */
public void setVisible(boolean newVisible) {
	fieldVisible = newVisible;
}
}
