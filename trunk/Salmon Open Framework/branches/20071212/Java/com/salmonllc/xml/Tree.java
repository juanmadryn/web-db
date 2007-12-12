package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/Tree.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 7/31/02 6:11p $ 
/////////////////////////
/**
 * Datastructure used to upload the Tree XML file. Tree contains many TreeItem objects which are stored in form of a Vector.
 * Creation date: (8/21/01 4:53:27 PM)
 * @author: Deepak Agarwal
 */
public class Tree {


	private String fieldName = "";

	private String fieldExpandImage = "";
	private String fieldContractImage = "";
	private String fieldNullImage = "";

	private String fieldTarget = "";
	private String fieldSelectMode = "";
	private boolean fieldShowRoot = true;
	private String fieldTreeMode = "";
	private boolean fieldVisible = true;
	
	public java.util.Vector vctChilds = new java.util.Vector();
/**
 * Tree constructor comment.
 */
public Tree() {
	super();
}
/**
 * This method returns a vector of all Tree Child Nodes
 * Creation date: (8/21/01 4:54:42 PM)
 * @return java.util.Vector
 */
public java.util.Vector getChilds() {
	return vctChilds;
}
/**
 * Gets the Tree Contract Image
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getContractImage() {
	return fieldContractImage;
}
/**
 * Gets the expand image for a tree node
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getExpandImage() {
	return fieldExpandImage;
}
/**
 * Gets the name for the tree
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return fieldName;
}
/**
 * Gets the null image for the Tree Component
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getNullImage() {
	return fieldNullImage;
}
/**
 * Gets the Seletc mode for the tree. Single Select or Multiple Select
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getSelectMode() {
	return fieldSelectMode;
}
/**
 * Gets the source for the target for the tree display
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getTarget() {
	return fieldTarget;
}
/**
 * Creation date: (8/21/01 5:02:02 PM)
 * @return java.lang.String
 */
public java.lang.String getTreeMode() {
	return fieldTreeMode;
}
/**
 * Gets the flag ' is Show root' for Tree Tag
 * Creation date: (8/21/01 5:02:02 PM)
 * @return boolean
 */
public boolean isShowRoot() {
	return fieldShowRoot;
}
/**
 * Gets the flag 'Visible' for Tree Tag
 * Creation date: (8/21/01 5:02:02 PM)
 * @return boolean
 */
public boolean isVisible() {
	return fieldVisible;
}
/**
 * Sets the child of a Tree node
 * Creation date: (8/21/01 4:54:42 PM)
 * @param newChilds java.util.Vector
 */
public void setChilds(java.util.Vector newChilds) {
	vctChilds = newChilds;
}
/**
 * Sets the contract image
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newContractImage java.lang.String
 */
public void setContractImage(java.lang.String newContractImage) {
	fieldContractImage = newContractImage;
}
/**
 * Sets the exapand image
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newExpandImage java.lang.String
 */
public void setExpandImage(java.lang.String newExpandImage) {
	fieldExpandImage = newExpandImage;
}
/**
 * Sets the name of the component
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	fieldName = newName;
}
/**
 * Sets the null image for the tree
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newNullImage java.lang.String
 */
public void setNullImage(java.lang.String newNullImage) {
	fieldNullImage = newNullImage;
}
/**
 * Sets the select Mode for the tree
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newSelectMode java.lang.String
 */
public void setSelectMode(java.lang.String newSelectMode) {
	fieldSelectMode = newSelectMode;
}
/**
 * Sets the flag to 'Show root'
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newShowRoot boolean
 */
public void setShowRoot(boolean newShowRoot) {
	fieldShowRoot = newShowRoot;
}
/**
 * Sets the source for the target display for the tree
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newTarget java.lang.String
 */
public void setTarget(java.lang.String newTarget) {
	fieldTarget = newTarget;
}
/**
 * Sets the tree mode
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newTreeMode java.lang.String
 */
public void setTreeMode(java.lang.String newTreeMode) {
	fieldTreeMode = newTreeMode;
}
/**
 * Sets the visibility of Tree
 * Creation date: (8/21/01 5:02:02 PM)
 * @param newVisible boolean
 */
public void setVisible(boolean newVisible) {
	fieldVisible = newVisible;
}
}
