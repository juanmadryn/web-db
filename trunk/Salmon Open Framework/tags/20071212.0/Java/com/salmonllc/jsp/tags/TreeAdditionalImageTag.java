package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/TreeAdditionalImageTag.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 7/31/02 7:13p $
/////////////////////////
import com.salmonllc.html.treeControl.*;
import com.salmonllc.html.*;
/**
 * This class is used to create additional images for every tree node
 * Creation date: (8/21/01 11:50:16 AM)
 * @author: Administrator
 */
public class TreeAdditionalImageTag extends BaseEmptyTag {

	private String _fieldName = "";
	private String _fieldSrc = "";
	private String _fieldHref = "";
	private String _fieldText = "";
/**
 * This method creates the images and add it to the tree buffer. These images are used with HtmlTreeControl Component which is created by using the Tree Tag
 */
public com.salmonllc.html.HtmlComponent createComponent() 
{
	TreeItemTag trtg = null;
	if(getHelper().getTreeTag() instanceof TreeItemTag)
		trtg = (TreeItemTag)getHelper().getTreeTag();
	else
		return null;
	
	if (trtg != null) 
	{
		HtmlTreeControl tree = (HtmlTreeControl)trtg.getHelper().getComponent();
		TreeBuffer tb = tree.getTreeBuffer();

		if(tb.getHandle() == -1)
			tb.gotoRoot();
		else
			tb.gotoItem(trtg.getHandle());

		int image = tb.addImage(getSrc());
		tb.addAdditionalImage(image, getText(), getHref());
	}	
    return null;
}
/**
 * Gets the link for the additinal image for a Tree node
 * Creation date: (8/21/01 12:04:41 PM)
 * @return java.lang.String
 */
public java.lang.String getHref() {
	return _fieldHref;
}
/**
 * Gets the name for the TreeAdditional Image component
 * Creation date: (8/21/01 12:04:41 PM)
 * @return java.lang.String
 */
public java.lang.String getName() {
	return _fieldName;
}
/**
 * Gets the source for the image used by Tree AdditionalImage component
 * Creation date: (8/21/01 12:04:41 PM)
 * @return java.lang.String
 */
public java.lang.String getSrc() {
	return _fieldSrc;
}
/**
 * Gets the text used by TreeAdditionalImage tag
 * Creation date: (8/21/01 12:06:22 PM)
 * @return java.lang.String
 */
public java.lang.String getText() {
	return _fieldText;
}
/**
 * Sets the Href link for Additional Image on Tree node
 * Creation date: (8/21/01 12:04:41 PM)
 * @param newHref java.lang.String
 */
public void setHref(java.lang.String newHref) {
	_fieldHref = newHref;
}
/**
 * Sets the name of the component
 * Creation date: (8/21/01 12:04:41 PM)
 * @param newName java.lang.String
 */
public void setName(java.lang.String newName) {
	_fieldName = newName;
}
/**
 * Sets the source for the additional image for a tree
 * Creation date: (8/21/01 12:04:41 PM)
 * @param newSrc java.lang.String
 */
public void setSrc(java.lang.String newSrc) {
	_fieldSrc = newSrc;
}
/**
 * Sets the text used by component
 * Creation date: (8/21/01 12:06:22 PM)
 * @param newText java.lang.String
 */
public void setText(java.lang.String newText) {
	_fieldText = newText;
}
}
