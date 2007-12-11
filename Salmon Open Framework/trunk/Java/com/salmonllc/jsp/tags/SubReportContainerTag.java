//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2004, Salmon LLC
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

import java.lang.reflect.Constructor;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspSubReportContainer;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/SubReportContainerTag.java $
//$Author: Dan $
//$Revision: 2 $
//$Modtime: 8/26/04 6:18p $
/////////////////////////


/**
 * This tag is used to to add a subreport to a JspDataTable
 */

public class SubReportContainerTag extends ContainerTag {
	/**
	 * This method must be implemented by each subclass BaseTag. This one creates a JSPContainer object.
	 * @return - HtmlComponent
	 */
	private String _subReportContainerClass;
		
	protected void initMode() {
       try {
			((JspSubReportContainer)getHelper().getComponent()).notifyListeners();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("subReportContainerTag.incrementMode()",e,this);
		}
	   super.initMode();
	}
	public HtmlComponent createComponent() {
		JspSubReportContainer con = null;
		if (_subReportContainerClass != null) { 
			con = buildComponent(_subReportContainerClass);
			con.setDoInit(true);
		} else {
			Props p = getHelper().getController().getPageProperties();
			String className = p.getProperty(Props.CONTAINER_CLASS);
			if (className != null)
				con = buildComponent(className);
		}

		if (con == null)
			con = new JspSubReportContainer(getName(), getHelper().getController());
		if (getDatasource() != null)
			con.setDataSource(getDatasource());
		return con;
	}

	private JspSubReportContainer buildComponent(String className) {
		try {
			Class c = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
			try {
				Class[] parms = { String.class, String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), getHelper().getController()};
				return (JspSubReportContainer) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			try {
				Class[] parms = { String.class, HtmlPage.class };
				Constructor con = c.getConstructor(parms);
				Object[] args = { getName(), getHelper().getController()};
				return (JspSubReportContainer) con.newInstance(args);
			} catch (NoSuchMethodException ex) {
			}

			throw new Exception("Could not find suitable constructor");
		} catch (Exception e) {
			if (getHelper().getTagContext().getDreamWeaverMode())
				return new JspSubReportContainer(getName(),getHelper().getController());
			else	
				MessageLog.writeErrorMessage("Error creating JspDetailFormDisplayBox component. Make sure the component exists and has a public constructor with the arguments (String name, String theme, HtmlPage page) or (String name,  HtmlPage page)", e, this);
		}
		return null;
	}
	/**
	 * @param subReportContainerClass The subReportContainerClass to set.
	 */
	public void setSubreportcontainerclass(String subReportContainerClass) {
		_subReportContainerClass = subReportContainerClass;
	}

	public void release() {
		super.release();
		_subReportContainerClass=null;
	}
}
