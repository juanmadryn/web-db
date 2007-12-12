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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/BaseTagHelper.java $
//$Author: Dan $
//$Revision: 26 $
//$Modtime: 10/05/04 12:19p $
/////////////////////////

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * The Base Tag Helper has some useful utility methods. There is one BaseTagHelper for each Tag Handler.
 */
public class BaseTagHelper {
	private TagSupport _tagSupport;
	

	protected BaseTagHelper(TagSupport tag) {
		_tagSupport = tag;

	}

	/**
	 * This method adds a component to the controller object.
	 */
	void addComponent(HtmlComponent comp) {
		if (comp != null) {
			JspContainer cont=getTagContext().getCurrentContainer();
			JspController controller = getTagContext().getControllerObject();
			if (cont != null) {
				int type = getComponentType();
				cont.add(comp, type);
				controller.assignComponentToField(getName(), comp, false);
			} else
				controller.assignComponentToField(getName(), comp, true);
		}
	}

	/**
	 * This method adds a DataStoreBuffer to the controller object.
	 */
	void addDataSource(com.salmonllc.sql.DataStoreBuffer ds) {
		if (ds != null) {
			JspController controller = getTagContext().getControllerObject();
			controller.assignDataSourceToField(getName(), ds);
		}
	}

	/**
	 * This method returns the HtmlComponent that is associated with this tag.
	 */
	public HtmlComponent getComponent() {
		JspController cont = getTagContext().getControllerObject();
		if (cont != null)
			return cont.getComponent(getName());
		return null;
	}

	/**
	 * This method returns the type of component in the tag. See JspContainer type constants for a list of valid values.
	 */
	public int getComponentType() {
		int type = JspContainer.TYPE_COMP;
		DataTableHeaderTag t = getDataTableBandTag();
		if (t != null) {
			type = JspContainer.TYPE_HEADER;
			if (t instanceof DataTableFooterTag)
				type = JspContainer.TYPE_FOOTER;
			else if (t instanceof DataTableRowsTag)
				type = JspContainer.TYPE_ROW;
			else if (t instanceof DataTableGroupFooterTag)
				type = JspContainer.TYPE_GROUP_FOOTER;
			else if (t instanceof DataTableGroupHeaderTag)
				type = JspContainer.TYPE_GROUP_HEADER;

		}
		return type;
	}

	/**
	 * This method returns this tags container or null if it isn't in a container.
	 */
	public ContainerTag getContainerTag() {
		return (ContainerTag) findAncestorWithClass( ContainerTag.class);
	}

	/**
	 * This method returns the JSPController associated with this tag.
	 */
	public JspController getController() {
		return getTagContext().getControllerObject();

	}

	/**
	 * This method returns this tags parent DataSourceNested Tag.
	 */
	public DataSourceNestedTag getDataSourceNestedTag() {
		return (DataSourceNestedTag) findAncestorWithClass( DataSourceNestedTag.class);
	}

	/**
	 * Returns the parent Datastore object used by the tag
	 */
	public DataStoreBuffer getDataSourceObject() {
		DataSourceTag t = null;
		if (_tagSupport instanceof DataSourceTag)
			t = (DataSourceTag) _tagSupport;
		else
			t = getDataSourceTag();

		if (t == null)
			return null;
		else
			return getController().getDataSource(t.getName());

	}

	/**
	 * This method returns this tags parent DataSourceTag or null if it isn't in one.
	 */
	public DataSourceTag getDataSourceTag() {
		return (DataSourceTag) findAncestorWithClass( DataSourceTag.class);
	}

	/**
	 * If this tag is embedded in a DataTable, this tag will indicate which band it is in.
	 */
	public DataTableHeaderTag getDataTableBandTag() {
		return (DataTableHeaderTag) findAncestorWithClass( DataTableHeaderTag.class);
	}

	/**
	 * If the tag is nested in a data table, this tag will return it.
	 */
	public DataTableTag getDataTableTag() {
		return getDataTableTag(false);
	}

	/**
	 * This method returns this tags InputTag or null if it isn't in one.
	 */
	public InputTag getInputTag() {
		return (InputTag) findAncestorWithClass( InputTag.class);
	}


    /**
	 * This method returns this tags AppletTag or null if it isn't in one.
	 */
	public AppletTag getAppletTag() {
		return (AppletTag) findAncestorWithClass( AppletTag.class);
	}

	/**
	 * If the tag is nested in a list, this method will return it.
	 */
	public ListTag getListTag() {
		return (ListTag) findAncestorWithClass( ListTag.class);

	}

	/**
	 * This method returns the name attribute for the tag
	 */
	public String getName() {
		if (_tagSupport instanceof BaseEmptyTag)
			return ((BaseEmptyTag) _tagSupport).getName();
		else
			return ((BaseBodyTag) _tagSupport).getName();
	}

	/**
	 * If the tag is nested in a data table, this tag will return it.
	 */
	public NavBarTag getNavBarTag() {
		Tag t;
		t = getParent();
		while (t != null) {
			if (t instanceof NavBarTag)
				return (NavBarTag) t;
			else if (t instanceof NavBarTag)
				return null;
			t = t.getParent();
		}
		return null;
	}

	/**
	 * Returns the Page Context associated with the tag
	 */
	public PageContext getPageContext() {
		if (_tagSupport instanceof BaseBodyTag)
			return ((BaseBodyTag) _tagSupport).getPageContext();
		else if (_tagSupport instanceof BaseEmptyTag)
			return ((BaseEmptyTag) _tagSupport).getPageContext();
		else
			return null;
	}

	/**
	 * This method returns the page tag that this tag is in. Each tag must be nested in a page tag to work properly.
	 */

	//public PageTag getPageTag() {
	//	return (PageTag) getPageContext().getRequest().getAttribute(PageTag.PAGE_TAG_REQ_KEY);
		//return (PageTag) _tagSupport.findAncestorWithClass(_tagSupport,PageTag.class);
	//}

	/**
	 * Returns the Context Object for the page tag
	 */
	public TagContext getTagContext() {
		return (TagContext) getPageContext().getRequest().getAttribute(TagContext.TAG_CONTEXT_REQ_KEY);
	}	
	/**
	 * This method returns the tags immediate parent tag
	 */
	public Tag getParent() {
		return _tagSupport.getParent();
	}

	/**
	 * If the tag is nested in a Table, this tag will return it.
	 */
	public TableTag getTableTag() {
		return getTableTag(false);
	}

	/**
	 * This method get the TagSupport object of the helper object
	 */
	protected TagSupport getTagSupport() {
		return _tagSupport;
	}

	/**
	 * If the tag is nested in a Table, this tag will return it.
	 */
	public TreeTag getTreeTag() {
		Tag t;
		t = getParent();
		while (t != null) {
			if (t instanceof TreeItemTag)
				return (TreeItemTag) t;
			if (t instanceof TreeTag)
				return (TreeTag) t;
			t = t.getParent();
		}
		return null;
	}

	/**
	 * Returns the actual value if it isn't null; otherwise substitutes a default value
	 */
	public static String ifNull(String actualValue, String defaultValue) {
		return actualValue != null ? actualValue : defaultValue;
	}

	/**
	 * Returns true if the string is null or blank
	 */
	public static boolean isEmpty(String test) {
		if (test == null)
			return true;
		if (test.length() == 0)
			return true;
		return false;
	}

	/**
	 * Each tag is executed once during the initialization phase of the JSP and should not generate any Html at that point. This method can be called to find out if the tag is initalizing.
	 */
	public boolean isInitializing() {
		return getTagContext().isInitializing();
	}

	/**
	 * Attempts to convert a string into its boolean equivalent; otherwise returns true.
	 */
	public static boolean stringToBoolean(String val) {
		return stringToBoolean(val, true);

	}

	/**
	 * Attempts to convert a string into its boolean equivalent; otherwise returns the default
	 */
	public static boolean stringToBoolean(String val, boolean def) {
		if (val == null)
			return def;
		String test = val.toUpperCase();
		if (test.equals("1") || test.equals("T") || test.equals("Y") || test.equals("TRUE") || test.equals("YES"))
			return true;
		else if (test.equals("0") || test.equals("F") || test.equals("N") || test.equals("FALSE") || test.equals("NO"))
			return false;
		else
			return def;
	}

	/**
	 * Attempts to convert a string to it's integer equivalant. If it can't it returns -1;
	 */
	public static int stringToInt(String val) {
		return stringToInt(val, -1);
	}

	/**
	 * Attempts to convert a string to it's integer equivalant. If it can't it returns -1;
	 */
	public static int stringToInt(String val, int def) {
		if (val == null)
			return def;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return def;
		}

	}

	/**
	 * This method returns this tags parent DataSourceJoinTag or null if it isn't in one.
	 */
	public DataSourceJoinTag getDataSourceJoinTag() {
		return (DataSourceJoinTag) findAncestorWithClass( DataSourceJoinTag.class);
	}

	/**
	 * If the tag is nested in a data table, this tag will return it.
	 */
	public DataTableTag getDataTableTag(boolean findTable) {
		if (findTable) {
			Tag t;
			t = getParent();
			while (t != null) {
				if (t instanceof DataTableTag)
					return (DataTableTag) t;
				else if (t instanceof TableTag)
					return null;
				t = t.getParent();
			}
			return null;
		} else {
			return (DataTableTag) findAncestorWithClass( DataTableTag.class);

		}

	}

	/**
	 * If the tag is nested in a Table, this tag will return it.
	 */
	public TableTag getTableTag(boolean findDataTable) {
		if (findDataTable) {
			Tag t;
			t = getParent();
			while (t != null) {
				if (t instanceof TableTag)
					return (TableTag) t;
				else if (t instanceof DataTableTag)
					return null;
				t = t.getParent();
			}
			return null;
		} else {
			return (TableTag) findAncestorWithClass( TableTag.class);
		}
	}

	/**
	* If the tag is nested in a validator, this method will return it.
	*/
	public ValidatorTag getValidatorTag() {
		return (ValidatorTag) findAncestorWithClass( ValidatorTag.class);
	}
	
	/**
	 * Finds the ancestor tag of this one with the correct class. This method fixes a bug in the J2EE findAncestorWithClass method where the tree is not searched up across includes.
	 */
	public Tag findAncestorWithClass(Class clazz) {
		Tag ret=TagSupport.findAncestorWithClass(_tagSupport,clazz);
		if (ret != null)
			return ret;	
		else 
			return getTagContext().findAncestorWithClass(clazz);
	}	
}
