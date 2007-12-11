package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/TreeItem.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 7/31/02 7:13p $ 
/////////////////////////
/**
 * This datastructure is used to store a node of a tree. Every TreeItem, node, may or may not contain many TreeItemAddImage objects.
 * Creation date: (8/21/01 4:53:39 PM)
 * @author: Deepak Agarwal
 */
public class TreeItem extends Tree {

	private String fieldText = "";
	private String fieldImgSource = "";
	private String fieldImgExpSource = "";
	private String fieldHref = "";
	private String fieldImgHref="";
	private String fieldName="";
	private boolean fieldVisible=true;
	private java.util.Vector fieldAddImages = new java.util.Vector();
/**
 * TreeItem constructor comment.
 */
public TreeItem() {
	super();
}
/**
 * This method returns the images for a Tree Node
 * Creation date: (8/22/01 9:33:32 AM)
 * @return java.util.Vector
 */
public java.util.Vector getAddImages() {
	return fieldAddImages;
}
/**
 * Gets the link href, if specified for a tree node
 * Creation date: (8/21/01 4:56:43 PM)
 * @return java.lang.String
 */
public java.lang.String getHref() {
	return fieldHref;
}
/**
 * Gets the expand image source file name for a Tree Node
 * Creation date: (8/21/01 4:56:43 PM)
 * @return java.lang.String
 */
public java.lang.String getImgExpSource() {
	return fieldImgExpSource;
}
/**
 * Gets the image source file name for a Tree Node
 * Creation date: (8/21/01 4:56:43 PM)
 * @return java.lang.String
 */
public java.lang.String getImgHref() {
	return fieldImgHref;
}
/**
 * Gets the image source file name for a Tree Node
 * Creation date: (8/21/01 4:56:43 PM)
 * @return java.lang.String
 */
public java.lang.String getImgSource() {
	return fieldImgSource;
}
/**
 * Gets the name for the TreeItem component
 * Creation date: (8/21/01 4:57:14 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return fieldName;
}
/**
 * Gets the text used to display on Tree node
 * Creation date: (8/21/01 4:56:43 PM)
 * @return java.lang.String
 */
public java.lang.String getText() {
	return fieldText;
}
/**
 * Gets the flag 'Visible' for Tree Tag
 * Creation date: (8/21/01 4:59:17 PM)
 * @return boolean
 */
public boolean isVisible() {
	return fieldVisible;
}
/**
 * Sets the additinal images used by a Tree Node
 * Creation date: (8/22/01 9:33:32 AM)
 * @param newAddImages java.util.Vector
 */
public void setAddImages(java.util.Vector newAddImages) {
	fieldAddImages = newAddImages;
}
/**
 * Sets the Href link for Tree node
 * Creation date: (8/21/01 4:56:43 PM)
 * @param newHref java.lang.String
 */
public void setHref(java.lang.String newHref) {
	fieldHref = newHref;
}
/**
 * Creation date: (8/21/01 4:56:43 PM)
 * @param newImgExpSource java.lang.String
 */
public void setImgExpSource(java.lang.String newImgExpSource) {
	fieldImgExpSource = newImgExpSource;
}
/**
 * Sets the Href link for Image on Tree node
 * Creation date: (8/21/01 4:56:43 PM)
 * @param newImgHref java.lang.String
 */
public void setImgHref(java.lang.String newImgHref) {
	fieldImgHref = newImgHref;
}
/**
 * Sets the Href link for Image on Tree node
 * Creation date: (8/21/01 4:56:43 PM)
 * @param newImgSource java.lang.String
 */
public void setImgSource(java.lang.String newImgSource) {
	fieldImgSource = newImgSource;
}
/**
 * Sets the name of the component
 * Creation date: (8/21/01 4:57:14 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	fieldName = newName;
}
/**
 * Sets the text used by component
 * Creation date: (8/21/01 4:56:43 PM)
 * @param newText java.lang.String
 */
public void setText(java.lang.String newText) {
	fieldText = newText;
}
/**
 * Sets the visibility of Tree Node
 * Creation date: (8/21/01 4:59:17 PM)
 * @param newVisible boolean
 */
public void setVisible(boolean newVisible) {
	fieldVisible = newVisible;
}
}
