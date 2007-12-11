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
package com.salmonllc.html.treeControl;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/treeControl/TreeNode.java $
//$Author: Dan $ 
//$Revision: 10 $ 
//$Modtime: 8/28/03 10:58a $ 
/////////////////////////

import java.util.*;

/**
 * @author  demian
 */
class TreeNode implements java.io.Serializable {
	Integer _image;
	Integer _expImage;
	Integer _index;
	Integer _level;
	String _text;
	String _URL;
	String _imageURL;
	boolean _expanded = false;
	TreeNode _parent;
	TreeNode _firstChild;
	TreeNode _lastChild;
	TreeNode _nextNode;
	TreeNode _priorNode;
	boolean _hasChildren = false;
	Vector _additionalImages;
	Vector _additionalText;
	Vector _additionalURL;
	Object _keyValue;
	boolean _selected = false;
	boolean _selectable = true;
	int _dsRowNo = -1;

	TreeNode(int image, int expImage, String text, String url, String imageURL, Object key) {
		super();
		_image = new Integer(image);
		_expImage = new Integer(expImage);
		_text = text;
		_URL = url;
		_expanded = false;
		_imageURL = imageURL;
		_keyValue = key;
	}

	void setImages(int image, int expImage) {
		_image = new Integer(image);
		_expImage = new Integer(expImage);
	}
	int addAdditionalImage(int imageNo, String text, String url) {
		if (_additionalImages == null) {
			_additionalImages = new Vector();
			_additionalText = new Vector();
			_additionalURL = new Vector();
		}

		_additionalImages.addElement(new Integer(imageNo));
		_additionalText.addElement(text);
		_additionalURL.addElement(url);

		return (_additionalImages.size() - 1);
	}
	boolean areThereChildren() {
		return _hasChildren;
	}
	void clearAdditionalImages() {
		_additionalImages = null;
		_additionalText = null;
	}
	int getAdditionalImage(int index) {
		return ((Integer) _additionalImages.elementAt(index)).intValue();
	}
	int getAdditionalImageCount() {
		if (_additionalImages == null)
			return 0;

		return _additionalImages.size();
	}
	String getAdditionalText(int index) {
		return (String) _additionalText.elementAt(index);
	}
	String getAdditionalURL(int index) {
		return (String) _additionalURL.elementAt(index);
	}
	TreeNode getFirstChild() {
		return _firstChild;
	}
	int getHandle() {
		return _index.intValue();
	}
	int getImage() {
		if (_expanded)
			return _expImage.intValue();
		else
			return _image.intValue();
	}
	String getImageURL() {
		return _imageURL;
	}
	Object getKey() {
		return _keyValue;
	}
	TreeNode getLastChild() {
		return _lastChild;
	}
	int getLevel() {
		return _level.intValue();
	}
	TreeNode getNextNode() {
		return _nextNode;
	}
	TreeNode getParent() {
		return _parent;
	}
	TreeNode getPriorNode() {
		return _priorNode;
	}
	boolean getSelectable() {
		return _selectable;
	}
	boolean getSelected() {
		return _selected;
	}
	String getText() {
		return _text;
	}
	String getURL() {
		return _URL;
	}
	boolean isExpanded() {
		return _expanded;
	}
	void reset() {
		_parent = null;
		_firstChild = null;
		_lastChild = null;
		_nextNode = null;
		_priorNode = null;
	}
	void setExpanded(boolean expanded) {
		_expanded = expanded;
	}
	void setFirstChild(TreeNode firstChild) {
		_firstChild = firstChild;
	}
	void setHandle(int handle) {
		_index = new Integer(handle);
	}
	void setHasChildren(boolean h) {
		_hasChildren = h;
	}
	void setKey(Object key) {
		_keyValue = key;
	}
	void setLastChild(TreeNode lastChild) {
		_lastChild = lastChild;
	}
	void setLevel(int level) {
		_level = new Integer(level);
	}
	void setNextNode(TreeNode nextNode) {
		_nextNode = nextNode;
	}
	void setParent(TreeNode parent) {
		_parent = parent;
	}
	void setPriorNode(TreeNode t) {
		_priorNode = t;
	}
	void setSelectable(boolean trueFalse) {
		_selectable = trueFalse;
	}
	void setSelected(boolean selected) {
		_selected = selected;
	}
	void setText(String text) {
		_text = text;
	}
	void setURL(String url) {
		_URL = url;
	}
	public int getDsRowNo() {
		return _dsRowNo;
	}

	public void setDsRowNo(int i) {
		_dsRowNo = i;
	}
	public String getFullPath() {
		StringBuffer ret = new StringBuffer();
		TreeNode n = this;
		if (_firstChild == null)
			n = _parent;
		
		while (n != null) {
			ret.insert(0,n.getText());
			ret.insert(0,"|");
			n = n.getParent();		
		}	
		ret.insert(0,new Integer(getLevel()).toString());
		return ret.toString();	
	}	

}
