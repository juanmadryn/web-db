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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/ContainerTag.java $
//$Author: Dan $
//$Revision: 23 $
//$Modtime: 7/12/04 12:14p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

import java.lang.reflect.Constructor;

/**
 * This tag is used to group other tags together
 */

public class ContainerTag extends BaseBodyTag {
	private String _enabled;
	private String _dataSource;
	private String _containerclass;
	private String _classname;

	/**
	 * This method is called after the container has been initialized. It is used to update the visible and enabled attributes of the sub tags.
	 * @param comp
	 */
	public void afterInit(HtmlComponent comp) {
		super.afterInit(comp);

		if (_enabled != null)
			 ((JspContainer) comp).setEnabled(BaseTagHelper.stringToBoolean(_enabled));
	}

	/**
	 * This method must be implemented by each subclass BaseTag. This one creates a JSPContainer object.
	 * @return - HtmlComponent
	 */
	public HtmlComponent createComponent() {
		//JspContainer con = new JspContainer(getName(), getHelper().getController());
		JspContainer con = null;
		if (_containerclass != null) {
			con = buildComponent(_containerclass);
			if (con != null)
				con.setDoInit(true);
		} else {
			Props p = getHelper().getController().getPageProperties();
			String className = p.getProperty(Props.CONTAINER_CLASS);
			if (className != null)
				con = buildComponent(className);
		}

		if (con == null)
			con = new JspContainer(getName(), getHelper().getController());
		if (_dataSource != null)
			con.setDataSource(_dataSource);
		return con;
	}

	private JspContainer buildComponent(String className) {
		try {
			Class c = Class.forName(className, true, Thread.currentThread().getContextClassLoader());

			try {
				Class[] parms = { String.class, String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), getHelper().getController()};
				return (JspContainer) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			try {
				Class[] parms = { String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), getHelper().getController()};
				return (JspContainer) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			throw new Exception("Could not find suitable constructor");
		} catch (Exception e) {
			if (getHelper().getTagContext().getDreamWeaverMode())
				return new JspContainer(getName(),getHelper().getController());
			else	
				MessageLog.writeErrorMessage("Error creating JspDetailFormDisplayBox component. Make sure the component exists and has a public constructor with the arguments (String name, String theme, HtmlPage page) or (String name,  HtmlPage page)", e, this);
		}
		return null;
	}

	/**
	 * Get the Data Source the component should be bound to
	 */
	public String getDatasource() {
		return _dataSource;
	}

	/**
	 * Returns the tags enabled attribute
	 */
	public String getEnabled() {
		return _enabled;
	}

	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 */
	public int getTagConvertType() {
		return CONV_INVISIBLE;
	}

	/**
	 * Releases the attributes of the tag
	 */
	public void release() {
		_enabled = null;
		_dataSource = null;
		_classname = null;
		_containerclass = null;
	}

	/**
	 * Set the Data Source the component should be bound to
	 */
	public void setDatasource(String val) {
		_dataSource = val;
	}

	/**
	 * Sets the tags enabled attribute
	 */
	public void setEnabled(String val) {
		_enabled = val;
	}

	/**
	 * Gets the tag's classname attribute
	 */
	public String getClassname() {
		return _classname;
	}

	/**
	 * Sets the tag's classname attribute
	 */
	public void setClassname(String classname) {
		_classname = classname;
	}
	/**
	 * Sets the tag's containerClass attribute
	 */
	public void setContainerclass(String javaclassname) {
		_containerclass = javaclassname;
	}

}
