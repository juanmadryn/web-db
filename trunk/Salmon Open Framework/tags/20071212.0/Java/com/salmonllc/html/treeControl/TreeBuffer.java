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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/treeControl/TreeBuffer.java $
//$Author: Len $
//$Revision: 18 $
//$Modtime: 2/20/04 8:22a $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlContainer;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlText;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.VectorSort;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

/**
 * This class stores the internal state of an HtmlTreeControl.
 */

public class TreeBuffer implements java.io.Serializable {
	public static final int SELECT_MANY = 2;
	public static final int SELECT_NONE = 0;
	public static final int SELECT_ONE = 1;

	public static final int MODE_SUBMIT = 0;
	public static final int MODE_LINK = 1;

	public static final int SORT_ASC = DataStoreBuffer.SORT_ASC;
	public static final int SORT_DES = DataStoreBuffer.SORT_DES;
	public static final int SORT_ANY = -1;

	TreeNode _root;
	TreeNode _current;
	Vector _list = new Vector();
	Vector _images = new Vector();
	TreeTraversalCallBack _ttcb;
	Integer _nextListItem = new Integer(-1);
	boolean _showRoot = true;
	int _scrollTo = -1;
	int _selected = -1;
	int _count = 0;
	int _selectMode = SELECT_NONE;
	int _mode = MODE_SUBMIT;
	Vector _headers;
	Vector _nodeComponents;

	/**
	 * This method places an image after the text of the current tree node.
	 * @param imageNo The number of the image to use (images are set using the addImage) method.
	 * @param text The tooltip for the image.
	 * @param url The url to go to when the user clicks on this image.
	 */

	public int addAdditionalImage(int imageNo, String text, String url) {
		if (_current == null)
			return -1;

		return _current.addAdditionalImage(imageNo, text, url);
	}

	/**
	 * This method adds an item to the tree under the current item.
	 * @param text The text to show on the line
	 * @param URL The URL to go to if the user clicks on the text.
	 * @param image The number of the image to use if the item is contracted (images are set using the addImage) method.
	 * @param image The number of the image to use if the item is expanded (images are set using the addImage) method.
	 * @param imageURL The url to go to when the user clicks on the image.
	 * @param key An object attached to the tree node containing any information that needs to be attached to this node.
	 */

	public int addChild(String text, String URL, int image, int expImage, String imageURL, Object key) {
		TreeNode n = new TreeNode(image, expImage, text, URL, imageURL, key);
		return addChild(n);
	}

	private int addChild(TreeNode n) {
		int retval;

		if (_nextListItem.intValue() == -1) {
			_list.addElement(n);
			retval = _list.size() - 1;
		} else {
			Integer bucket = (Integer) _list.elementAt(_nextListItem.intValue());
			_list.setElementAt(n, _nextListItem.intValue());
			retval = _nextListItem.intValue();
			_nextListItem = new Integer(bucket.intValue());
		}

		n.setHandle(retval);

		if (_root == null) {
			_root = n;
			_current = n;
			n.setExpanded(true);
			n.setLevel(0);
		} else {
			n.setParent(_current);
			TreeNode firstChild = _current.getFirstChild();
			TreeNode lastChild = _current.getLastChild();
			n.setLevel(_current.getLevel() + 1);
			n.setPriorNode(lastChild);
			if (firstChild == null) {
				_current.setFirstChild(n);
				_current.setLastChild(n);
			} else {
				_current.setLastChild(n);
				lastChild.setNextNode(n);
			}

		}

		return retval;
	}
	/**
	 * This method adds an image to the list of images used by the tree.
	 * @param URL The URL of the image file.
	 * @returns image The number of the image. Use this index to pass the image to other methods in the tree buffer.
	 */

	public int addImage(String URL) {
		for (int i = 0; i < _images.size(); i++)
			if (((String) _images.elementAt(i)).equals(URL))
				return i;

		_images.addElement(URL);
		return _images.size() - 1;
	}

	/**
	 * This method returns true if the current node has any children.
	 */

	public boolean areChildrenLoaded() {
		if (_current == null)
			return false;

		if (_current.getFirstChild() == null)
			return false;

		return true;
	}

	/**
	 * This method removes any images that appear after the text.
	 */

	public void clearAdditionalImages() {
		if (_current == null)
			return;

		_current.clearAdditionalImages();
	}

	/**
	 * This method will clear all selected items in the tree.
	 */
	public void clearSelections() {
		for (int i = 0; i < _list.size(); i++) {
			Object o = _list.elementAt(i);
			if (o != null && o instanceof TreeNode)
				 ((TreeNode) o).setSelected(false);
		}
	}

	/**
	 * This method will clear all selected items in the tree.
	 */
	public void clearVisibleSelections() {
		traverse(new VisibleItemDeselector(), null);
	}

	/**
	 * This method removes all the children of the current node from the tree.
	 * @param deleteParent true to remove the current node as well as its children.
	 */

	public void deleteChildren(boolean deleteParent) {
		if (_current == null)
			return;

		TreeNode thisOne = _current;

		TreeNode child = _current.getFirstChild();
		while (child != null) {
			TreeNode next = child.getNextNode();
			_current = child;
			deleteChildren(true);
			child = next;
		}

		if (!deleteParent)
			_current = thisOne;
		else {
			int handle = thisOne.getHandle();
			TreeNode parent = thisOne.getParent();
			TreeNode next = thisOne.getNextNode();
			TreeNode prior = thisOne.getPriorNode();

			if (parent != null) {
				if (parent.getFirstChild() == thisOne)
					parent.setFirstChild(next);
				if (parent.getLastChild() == thisOne)
					parent.setLastChild(prior);
			}

			if (next != null)
				next.setPriorNode(prior);
			if (prior != null)
				prior.setNextNode(next);

			thisOne.reset();
			if (_root == thisOne)
				_root = null;
			if (_current == thisOne)
				_current = null;
			_current = _root;

			_list.setElementAt(new Integer(_nextListItem.intValue()), handle);
			_nextListItem = new Integer(handle);
		}
	}

	/**
	 * This method writes the contents of the tree to System.error. It is useful for debugging.
	 */

	public void dump() {
		System.err.println("=============================================================");
		System.err.println("Elements=" + _list.size());
		System.err.println("Next element=" + _nextListItem);

		for (int i = 0; i < _list.size(); i++) {
			Object o = _list.elementAt(i);
			if (o == null)
				System.err.println("Item" + i + " = null");
			else if (o instanceof Integer)
				System.err.println("Item " + i + " value = " + ((Integer) o).intValue());
			else {
				int parent, child, next;
				parent = -1;
				child = -1;
				next = -1;

				TreeNode t = (TreeNode) o;

				TreeNode p = t.getParent();
				if (p != null)
					parent = p.getHandle();

				TreeNode c = t.getFirstChild();
				if (c != null)
					child = c.getHandle();

				TreeNode n = t.getNextNode();
				if (n != null)
					next = n.getHandle();

				String text = t.getText();

				System.err.println("Item=" + i + " Text = " + text + " Parent = " + parent + " First Child = " + child + " next=" + next);
			}
		}
	}

	/**
	 * This method returns the URL of the additional images in the node.
	 */
	public String getAdditionalImage(int index) {
		if (_current == null)
			return new String("");

		int imgNo = _current.getAdditionalImage(index);
		return (String) _images.elementAt(imgNo);
	}

	/**
	 * This method returns the number of additional images attached to this tree node.
	 */
	public int getAdditionalImageCount() {
		if (_current == null)
			return -1;

		return _current.getAdditionalImageCount();
	}

	/**
	 * This method returns the text of the additional images in the node.
	 */
	public String getAdditionalText(int index) {
		if (_current == null)
			return new String("");

		return _current.getAdditionalText(index);
	}

	/**
	 * This method returns the URL that the user will go to for the additional image in the node.
	 */
	public String getAdditionalURL(int index) {
		if (_current == null)
			return new String("");

		return _current.getAdditionalURL(index);
	}

	/**
	 * This method returns the value of an internal counter in the class. The counter will be incremented each time the method is called
	 */
	public int getCount() {
		_count++;
		return _count;
	}

	/**
	 * This method returns the a list of all the nodes in the tree.
	 */
	public Enumeration getElements() {
		return new TreeEnumerator(_list);
	}

	/**
	 * This method returns the handle of the current tree node.
	 */
	public int getHandle() {
		int retval = -1;
		if (_current != null)
			retval = _current.getHandle();
		return retval;
	}

	/**
	 * This method returns the URL of the image associated with the current tree node.
	 */
	public String getImage() {
		String retval = null;

		if (_current != null) {
			int imageNo = _current.getImage();
			if ((imageNo > -1) && (imageNo < _images.size()))
				retval = (String) _images.elementAt(imageNo);
		}

		return retval;
	}

	/**
	 * This method returns the URL that the user will go to if they click on  in the current node.
	 */
	public String getImageURL() {
		String retval = null;
		if (_current != null)
			retval = _current.getImageURL();
		return retval;
	}

	/**
	 * This method returns the user data Object associated with the current node.
	 */
	public Object getKey() {
		if (_current != null)
			return _current.getKey();
		else
			return null;
	}

	/**
	 * This method returns the level of indentation for the current node.
	 */
	public int getLevel() {
		int retval = -1;
		if (_current != null)
			retval = _current.getLevel();
		return retval;
	}

	/**
	 * This method gets the expand / contract mode for the tree. Valid Values are MODE_SUBMIT (use submit images to expand, contract tree items) or MODE_LINK (use html links and javascript to expand / contract the tree).
	 */
	public int getMode() {
		return _mode;
	}

	TreeNode getNode() {
		return _current;
	}

	/**
	 * This method will return the tree node that will be scrolled to the next time the page is loaded.
	 */
	public int getScrollTo() {
		int retval = _scrollTo;
		if (!itemExists(_scrollTo))
			retval = -1;
		_scrollTo = -1;
		return retval;
	}

	/**
	 * This method returns whether the current node will be selectable.
	 */
	public boolean getSelectable() {
		if (_current == null)
			return false;
		else
			return _current.getSelectable();
	}

	/**
	 * This method returns the handle of the currently selected node.
	 */
	public int getSelected() {
		int retval = _selected;
		return retval;
	}

	/**
	 * If select mode is MODE_MANY, this will return whether the item is selected or not.
	 */
	public boolean getSelected(int handle) {
		if (handle < 0)
			return false;
		if (handle >= _list.size())
			return false;
		Object o = _list.elementAt(handle);
		if (o == null)
			return false;
		if (o instanceof Integer)
			return false;
		return ((TreeNode) o).getSelected();
	}

	/**
	 * This method returns the select mode for the tree. Valid values are SELECT_NONE or SELECT_ONE.
	 */
	public int getSelectMode() {
		return _selectMode;
	}

	/**
	 * This method returns whether or not the root of the tree is visible.
	 */

	public boolean getShowRoot() {
		return _showRoot;
	}

	/**
	 * This method returns the text associated with the current tree node.
	 */
	public String getText() {
		String retval = null;
		if (_current != null)
			retval = _current.getText();
		return retval;
	}

	/**
	 * This method returns the URL for the current node in the tree.
	 */
	public String getURL() {
		String retval = null;
		if (_current != null)
			retval = _current.getURL();
		return retval;
	}

	/**
	 * This method positions the current node to the first child of the current one. It will return false if the current node doesn't have a child.
	 */
	public boolean gotoChild() {
		if (_current == null)
			return false;
		TreeNode n = _current.getFirstChild();
		if (n == null)
			return false;
		_current = n;
		return true;
	}

	/**
	 * This method positions the current node to a particular item (handle) on the tree. It will return false an node with the specified handle doesn't exist.if the current node doesn't have a child.
	 */

	public boolean gotoItem(int item) {
		if (item < 0)
			return false;
		if (item >= _list.size())
			return false;
		Object o = _list.elementAt(item);
		if (o == null)
			return false;
		if (o instanceof Integer)
			return false;
		_current = (TreeNode) o;
		return true;
	}

	/**
	 * This method positions the current node to the last child of the current one. It will return false if the current node doesn't have a child.
	 */
	public boolean gotoLastChild() {
		if (_current == null)
			return false;
		TreeNode n = _current.getLastChild();
		if (n == null)
			return false;
		_current = n;
		return true;
	}

	/**
	 * This method positions the current node to the next node on the same level. It will return false if the current node is the last one on the branch.doesn't have a child.
	 */
	public boolean gotoNext() {
		if (_current == null)
			return false;
		TreeNode n = _current.getNextNode();
		if (n == null)
			return false;
		if (n == _current)
			return false;

		_current = n;
		return true;
	}

	/**
	 * This method positions the current node to the parent of the current. It will return false if the current node is the root.
	 */
	public boolean gotoParent() {
		if (_current == null)
			return false;
		if (_current == _root)
			return false;
		TreeNode n = _current.getParent();
		_current = n;
		return true;
	}

	/**
	 * This method positions the current node to the root node. It will return false if the tree is empty.
	 */
	public boolean gotoRoot() {
		if (_root == null)
			return false;
		_current = _root;
		return true;
	}

	/**
	 * This method returns true if the current node has children.
	 */
	public boolean hasChildren() {
		if (_current == null)
			return false;

		if (_current.areThereChildren())
			return true;

		if (_current.getFirstChild() == null)
			return false;

		return true;

	}

	/**
	 * This method returns true if the current node is the root node.
	 */
	public boolean isCurrentRoot() {
		return (_current == _root);
	}

	/**
	 * This method returns true if the tree is empty.
	 */
	public boolean isEmpty() {
		return (_root == null);
	}

	/**
	 * This method returns true if the current item on the tree is expanded.
	 */
	public boolean isExpanded() {
		boolean retval = false;
		if (_current != null)
			retval = _current.isExpanded();
		return retval;
	}

	/**
	 * This method returns true if the current item on the tree is visible.
	 */

	public boolean isItemVisible() {
		return (getImage() != null);
	}

	/**
	 * This method returns true if a node with the specified handle exists in the tree.
	 */

	public boolean itemExists(int item) {
		if (item < 0)
			return false;
		if (item >= _list.size())
			return false;
		Object o = _list.elementAt(item);
		if (o == null)
			return false;
		if (o instanceof Integer)
			return false;
		return true;
	}

	/**
	 * This method replace the value of the string "$UNO$" in the passed string with a number unique for the life of this tree buffer. It can be used to add uniqueness to URLs to defeat browser caching for pages that should not be cached.
	 * @returns the new String.
	 */

	public String replaceUno(String st) {
		int pos = st.indexOf("$UNO$");
		if (pos < 0)
			return st;

		return (st.substring(0, pos) + getCount() + st.substring(pos + 5));
	}

	/**
	 * Seeds an internal counter in the tree.
	 */

	public void setCount(int count) {
		_count = count;
	}

	/**
	 * This method expands or contracts the current tree node.
	 */

	public void setExpanded(boolean expanded) {
		if (_current != null)
			_current.setExpanded(expanded);
	}

	/**
	 * This method tells the tree whether the tree has children nodes. It will place an expand icon on the tree node even if the children aren't loaded yet.
	 */
	public void setHasChildren(boolean hasChildren) {
		if (_current != null) {
			_current.setHasChildren(hasChildren);
			if (!hasChildren)
				_current.setExpanded(false);
		}
	}

	/**
	 * This sets key information associated with the current tree node.
	 */
	public void setKey(Object key) {
		if (_current != null)
			_current.setKey(key);
	}

	/**
	 * This method sets the expand / contract mode for the tree. Valid Values are MODE_SUBMIT (use submit images to expand, contract tree items) or MODE_LINK (use html links and javascript to expand / contract the tree).
	 */
	public void setMode(int mode) {
		_mode = mode;
	}

	/**
	 * This method returns the tree item that will be scrolled to the next time the tree is displayed in the browser.
	 */
	public void setScrollTo(int handle) {
		_scrollTo = handle;
	}

	/**
	 * This method returns whether the current node will be selectable.
	 */
	public void setSelectable(boolean sel) {
		if (_current != null)
			_current.setSelectable(sel);
	}

	/**
	 * This method sets the currently selected node.
	 */
	public void setSelected(int handle) {
		_selected = handle;
	}

	/**
	 * If select mode is MODE_MANY, this will set the item to either selected or not.
	 */
	public void setSelected(int handle, boolean trueFalse) {
		if (handle < 0)
			return;
		if (handle >= _list.size())
			return;
		Object o = _list.elementAt(handle);
		if (o == null)
			return;
		if (o instanceof Integer)
			return;
		((TreeNode) o).setSelected(trueFalse);
	}

	/**
	 * This method sets the select mode for the tree. Valid Values are SELET_ONE, SELECT_NONE and SELECT_MANY.
	 */
	public void setSelectMode(int selectMode) {
		_selectMode = selectMode;
	}

	/**
	 * This method shows or hides the root tree node.
	 */
	public void setShowRoot(boolean show) {
		_showRoot = show;
	}

	/**
	 * This method sets the text associated with the current tree node.
	 */
	public void setText(String text) {
		if (_current != null)
			_current.setText(text);
	}

	/**
	 * This method sets the URL associated with the current tree node.
	 */

	public void setURL(String url) {
		if (_current != null)
			_current.setURL(url);
	}

	/**
	 * For Trees with node components, you must specify a row in the datastore that corresponds to the items for the current node.
	 * @param rowNo
	 */
	public void setRow(int rowNo) {
		if (_current != null)
			_current.setDsRowNo(rowNo);
	}

	/**
	 * For Trees with node components, the datastore row associated with each component
	 * @param rowNo
	 */
	public int getRow() {
		if (_current != null)
			return _current.getDsRowNo();
		else
			return -1;
	}
	/**
	 * Use this method to traverse the tree. When each node is visited in the traversal the callback method in the TreeTraversalCallBack object will be invoked and passed the PrintWriter.
	 * @see TreeTraversalCallBack#callback
	 */

	public void traverse(TreeTraversalCallBack cb, PrintWriter p) {
		traverse(cb,p,true);
	}

	/**
	 * Use this method to traverse the tree. When each node is visited in the traversal the callback method in the TreeTraversalCallBack object will be invoked and passed the PrintWriter.
	 * @see TreeTraversalCallBack#callback
	 */

	public void traverse(TreeTraversalCallBack cb, PrintWriter p,boolean onlyExpanded) {
		_ttcb = cb;
		gotoRoot();
		int cur = getHandle();

		if (_showRoot) {
			_ttcb.callBack(this, p);
			gotoRoot();
		}

        if (onlyExpanded)
         {
			if (isExpanded())
				traverseChildren(cur, p);
         }
        else
        {
			traverseAllChildren(cur, p);
        }

		_ttcb = null;

	}


	private void traverseChildren(int item, PrintWriter p) {
		if (!gotoItem(item))
			return;

		if (!gotoChild())
			return;

		do {
			int cur = getHandle();
			_ttcb.callBack(this, p);
			gotoItem(cur);
			if (isExpanded())
				traverseChildren(cur, p);
			gotoItem(cur);
		} while (gotoNext());
	}

    //Added the private method to traverse All Children regardless
    //if the children are expanded or collapsed.
    //Called by traverse.
    //LS - 02/20/04
	private void traverseAllChildren(int item, PrintWriter p) {
		if (!gotoItem(item))
			return;

		if (!gotoChild())
			return;

		do {
			int cur = getHandle();
			_ttcb.callBack(this, p);
			gotoItem(cur);
//			if (isExpanded())
				traverseAllChildren(cur, p);
			gotoItem(cur);
		} while (gotoNext());
	}


	/**
	 * This method positions the current node to the prior node on the same level. It will return false if the current node is the parent of the  one on the branch.
	 */
	public boolean gotoPrior() {
		if (_current == null)
			return false;
		TreeNode n = _current.getPriorNode();
		if (n == null)
			return false;
		if (n == _current)
			return false;

		_current = n;
		return true;
	}

	/**
	 * Changes the images used by the node
	 */
	public void setImages(int image, int expandedImage) {
		if (_current == null)
			return;
		_current.setImages(image, expandedImage);
	}

	/**
	 * Add header components to the tree. The components will appear above the tree columns
	 */
	public void addHeaderComponent(HtmlComponent comp) {
		if (_headers == null)
			_headers = new Vector();
		Object o[] = new Object[3];
		o[0] = comp;
		_headers.add(o);
	}

	/**
	 * Add header components to the tree. The components will appear above the tree columns
	 */
	public void addHeaderComponent(HtmlComponent comp, String align, String width) {
		if (_headers == null)
			_headers = new Vector();
		Object o[] = new Object[3];
		o[0] = comp;
		o[1] = align;
		o[2] = width;
		_headers.add(o);
	}
	/**
	 * remove all header components from the tree
	 */
	public void resetHeaders() {
		_headers.removeAllElements();
	}

	/**
	 * @return the header component at a specific position
	 */
	public HtmlComponent getHeaderComponent(int index) {
		Object o[] = (Object[]) _headers.elementAt(index);
		return (HtmlComponent) o[0];
	}

	/**
	 * @return the header alignment at a specific position
	 */
	public String getHeaderAlign(int index) {
		Object o[] = (Object[]) _headers.elementAt(index);
		return (String) o[1];
	}

	/**
	 * @return the header width at a specific position
	 */
	public String getHeaderWidth(int index) {
		Object o[] = (Object[]) _headers.elementAt(index);
		return (String) o[2];
	}
	public int getHeaderCount() {
		if (_headers == null)
			return 0;
		else
			return _headers.size();
	}

	/**
	 * Add node components to the tree. The components will in new TDs after each node. Note, if you add nodes to trees with headers, the first header goes over the tree node, so the number of node components should number of headers - 1.
	 */
	public void addNodeComponent(HtmlComponent comp) {
		if (_nodeComponents == null)
			_nodeComponents = new Vector();
		_nodeComponents.add(comp);
	}
	/**
	 * remove all node components from the tree
	 */
	public void resetNodeComponents() {
		_nodeComponents.removeAllElements();
	}

	/**
	 * @return a vector of HTML components that will appear after each node.
	 */
	public Vector getNodeComponents() {
		return _nodeComponents;
	}

	public boolean canSortOnColumn(int colNo) {
		if (colNo == 0)
			return true;
		HtmlComponent comp = findSortComponent(colNo);
		if (comp == null)
			return false;

		return (findSortComponent(comp) != null);
	}

	private HtmlComponent findSortComponent(int col) {
		if (col == 0)
			return null;
		else {
			Vector nodeComps = getNodeComponents();
			if (nodeComps == null)
				return null;
			else if (col > nodeComps.size())
				return null;
			else
				return (HtmlComponent) nodeComps.elementAt(col - 1);
		}
	}

	private HtmlComponent findSortComponent(HtmlComponent sortComp) {
		if (sortComp instanceof HtmlFormComponent) {
			if (((HtmlFormComponent) sortComp).getColumnNumber() != -1)
				return sortComp;
		} else if (sortComp instanceof HtmlText) {
			if (((HtmlText) sortComp).getExpressionEvaluator() != null)
				return sortComp;
		} else if (sortComp instanceof HtmlContainer) {
			Enumeration e = ((HtmlContainer) sortComp).getComponents();
			while (e.hasMoreElements()) {
				sortComp = (HtmlComponent) e.nextElement();
				return findSortComponent(sortComp);
			}
		}
		return null;
	}

	/**
	 * Sort the tree
	 * @param column the number of the header column to sort on (0 for path name only sort)
	 * @param pathDir the sort direction for the tree path (first column) to sort on SORT_ASC, SORT_DES or SORT_ANY to ignore the path sort
	 * @param dir the direction to sort the specified column on SORT_ASC or SORT_DES if column is not zero
	 */
	public synchronized void sort(int column, int pathDir, int dir) {
		DataStoreBuffer ds = null;
		DataStoreEvaluator dsEval = null;
		int dsSortColumn = -1;
		HtmlComponent comp = findSortComponent(column);
		if (comp != null) {
			comp = findSortComponent(comp);
			if (comp instanceof HtmlFormComponent) {
				dsSortColumn = ((HtmlFormComponent) comp).getColumnNumber();
				ds = ((HtmlFormComponent) comp).getBoundDataStore();
			} else if (comp instanceof HtmlText) {
				dsEval = ((HtmlText) comp).getExpressionEvaluator();
				ds = dsEval.getDataStore();
				String exp = dsEval.getExpression();
				int index = -1;
				if (exp != null)
					dsSortColumn = ds.getColumnIndex(exp);
			}
		} else
			dir = pathDir;

		TreeNode parent = _root;
		VectorSort sorter = null;
		if (pathDir == SORT_ANY)
			sorter = new SorterNoPath(dir, dsSortColumn, dsEval, ds);
		else
			sorter = new Sorter(pathDir, dir);
			
		for (int i = 0; i < _list.size(); i++) {
			TreeNode n = (TreeNode) _list.elementAt(i);
			if (n != null && n != _root) {
				SortElement ele = new SortElement();
				ele.node = n;
				ele.parent = n.getParent();
				ele.path = n.getFullPath();
				try {
					if (dsSortColumn != -1)
						ele.value = ds.getAny(ele.node.getDsRowNo(), dsSortColumn);
					else if (dsEval != null)
						ele.value = dsEval.evaluateRow(ele.node.getDsRowNo());
					else {
						if (ele.node.getFirstChild() == null)
							ele.value = n.getText();
					}
				} catch (DataStoreException e) {
					MessageLog.writeErrorMessage("sort()", e, this);
				}
				sorter.add(ele);
			}
		}
		TreeNode selected = null;
		if (_selected != -1) {
			if (gotoItem(_selected))
				selected = _current;	
		}	
		
		sorter.sort();
		gotoRoot();
		deleteChildren(false);
		for (int i = 0; i < sorter.size(); i++) {
			SortElement ele = (SortElement) sorter.elementAt(i);
			_current = ele.parent;
			int handle = addChild(ele.node);
			if (ele.node == selected)
				_selected = handle;
		}
	}

	/**
	 * @author  demian
	 */
	private class SortElement {
		TreeNode node;
		TreeNode parent;
		String path;
		Object value;
	}

	private class Sorter extends VectorSort {
		private int _pathDir;
		private int _elementDir;
		public Sorter(int pathDir, int eleDir) {
			_pathDir = pathDir;
			_elementDir = eleDir;
		}
		public boolean compare(Object o1, Object o2) {
			SortElement s1 = (SortElement) o1;
			SortElement s2 = (SortElement) o2;
			boolean ret = compareElements(s1, s2);
			return ret;
		}

		public boolean compareElements(SortElement ele1, SortElement ele2) {
			String path1 = ele1.path;
			String path2 = ele2.path;
			Object val1 = ele1.value;
			Object val2 = ele2.value;
			int level1 = ele1.node.getLevel();
			int level2 = ele2.node.getLevel();

			if (level1 != level2)
				return level2 > level1;

			int ret = path1.compareToIgnoreCase(path2);
			if (ret != 0) {
				if (_pathDir == SORT_ASC)
					return ret < 0;
				else
					return ret > 0;
			}

			if (val1 == null) {
				if (val2 == null)
					ret = 0;
				else
					ret = -1;
			} else if (val2 == null)
				ret = 1;
			else if (val1 instanceof String) {
				ret = ((String) val1).compareToIgnoreCase((String) val2);
			} else if (val1 instanceof Number) {
				double v1 = ((Number) val1).doubleValue();
				double v2 = ((Number) val2).doubleValue();
				if (v1 == v2)
					ret = 0;
				else if (v1 < v2)
					ret = -1;
				else
					ret = 1;
			} else if (val1 instanceof Timestamp) {
				if (((Timestamp) val1).equals((Timestamp) val2))
					ret = 0;
				else if (((Timestamp) val1).before((Timestamp) val2))
					ret = -1;
				else
					ret = 1;
			} else if (val1 instanceof java.sql.Date) {
				if (val1.equals(val2))
					ret = 0;
				else if (((java.sql.Date) val1).before((java.sql.Date) val2))
					ret = -1;
				else
					ret = 1;
			} else if (val1 instanceof java.sql.Time) {
				if (val1.equals(val2))
					ret = 0;
				else if (((java.sql.Time) val1).before((java.sql.Time) val2))
					ret = -1;
				else
					ret = 1;
			}

			if (_elementDir == SORT_ASC)
				return ret < 0;
			else
				return ret > 0;
		}
	}
	
	/**
	 * @author  demian
	 */
	private class SorterNoPath extends VectorSort {
		private int _elementDir;
		private int _sortCol;
		private DataStoreEvaluator _eval;
		private DataStoreBuffer _ds;
		private Stack _left = new Stack();
		private Stack _right = new Stack();

		public SorterNoPath(int eleDir, int sortColumn, DataStoreEvaluator dsEval,	DataStoreBuffer ds) {
			_elementDir = eleDir;
			_sortCol = sortColumn;
			_eval = dsEval;
			_ds = ds;
		}

		public boolean compare(Object o1, Object o2) {
			SortElement s1 = (SortElement) o1;
			SortElement s2 = (SortElement) o2;
			boolean ret = compareElements(s1, s2);
			return ret;
		}

		private Object RetrieveValue(TreeNode node)	{
			Object retVal = null;
			if(node != null){
				try {
					retVal = node.getText();
					if (_sortCol != -1)
						retVal = _ds.getAny(node.getDsRowNo(), _sortCol);
					else if (_eval != null)
						retVal = _eval.evaluateRow(node.getDsRowNo());
				} catch (DataStoreException e) {
					MessageLog.writeErrorMessage("sort()", e, this);
				}
			}
			return retVal;
		}

		/*
		 * given two tree nodes, start at their most extreme parent, and start
		 * comparing each node in their corresponding paths.
		 * 
		 * Return the first pair of nodes in their paths that is NOT equal in id.
		 */
		private Pair RetrieveFirstNotEqualinPath(TreeNode left, TreeNode right)	{
			Pair retVal = new Pair();
			_left.clear();
			_right.clear();

			while(left != null)		{
				_left.push(left);
				left = left.getParent();
			}

			while(right != null) {
				_right.push(right);
				right = right.getParent();
			}

			boolean empty = _left.empty() || _right.empty();
			boolean equals = true;
			TreeNode leftCandidate = null;
			TreeNode rightCandidate = null;
			while(!empty && equals)	{
				leftCandidate = (TreeNode)_left.pop();
				rightCandidate = (TreeNode)_right.pop();
				equals = leftCandidate.equals(rightCandidate);
				empty = _left.empty() || _right.empty();
			}

			if(equals)	{
				if(_left.empty())
					leftCandidate = null;

				if(_right.empty())
					rightCandidate = null;
			}

			retVal.left = leftCandidate;
			retVal.right = rightCandidate;
			return retVal;
		}

		public boolean compareElements(SortElement ele1, SortElement ele2) {
			Object val1 = ele1.value;
			Object val2 = ele2.value;
			int ret = 0;

			// Retrieve first two nodes in paths that are not equal
			Pair nodePair = RetrieveFirstNotEqualinPath(ele1.node, ele2.node);

			// Retrieve the corresponding column value that we are sorting on.
			val1 = RetrieveValue((TreeNode)nodePair.left);
			val2 = RetrieveValue((TreeNode)nodePair.right);

			// Compare the values.
			if (val1 == null) {
				if (val2 == null)
					ret = 0;
				else
					ret = -1;
			} else if (val2 == null)
				ret = 1;
			else if (val1 instanceof String) {
				ret = ((String) val1).compareToIgnoreCase((String) val2);
			} else if (val1 instanceof Number) {
				double v1 = ((Number) val1).doubleValue();
				double v2 = ((Number) val2).doubleValue();
				if (v1 == v2)
					ret = 0;
				else if (v1 < v2)
					ret = -1;
				else
					ret = 1;
			} else if (val1 instanceof Timestamp) {
				if (((Timestamp) val1).equals((Timestamp) val2))
					ret = 0;
				else if (((Timestamp) val1).before((Timestamp) val2))
					ret = -1;
				else
					ret = 1;

			} else if (val1 instanceof java.sql.Date) {
				if (val1.equals(val2))
					ret = 0;
				else if (((java.sql.Date) val1).before((java.sql.Date) val2))
					ret = -1;
				else
					ret = 1;
			} else if (val1 instanceof java.sql.Time) {
				if (val1.equals(val2))
					ret = 0;
				else if (((java.sql.Time) val1).before((java.sql.Time) val2))
					ret = -1;
				else
					ret = 1;
			}

			boolean retVal = false;
			if (_elementDir == SORT_ASC)
				retVal = ret < 0;
			else
				retVal = ret > 0;

			return retVal;
		}
	}
	
	private class Pair	{
		public Object left;
		public Object right;
	}
}