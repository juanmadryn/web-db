
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

package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspContainer.java $
//$Author: Dan $
//$Revision: 31 $
//$Modtime: 10/21/04 2:15p $
/////////////////////////

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlContainer;
import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlImage;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is the ancestor for all JSP Container objects. It differs from HtmlContainer in that it only contains components for the purpose of processing the parameters returned from a browser. The component's generateHtml method is not called from this container, but is instead governed by the components placement in the JSP page.
 */

public class JspContainer extends HtmlComponent {
	private Vector _compsVec = new Vector();
	protected HtmlComponent _submit;
	public static final int TYPE_COMP = 0;
	public static final int TYPE_ROW = 1;
	public static final int TYPE_HEADER = 2;
	public static final int TYPE_FOOTER = 3;
	public static final int TYPE_GROUP_FOOTER = 4;
	public static final int TYPE_GROUP_HEADER = 5;
	protected boolean _center = false;
	protected boolean _enabled = true;
	private boolean _doInit;

	public JspContainer(String name, HtmlPage p) {
		super(name, p);
	}
	/**
	 * Adds a component to the container. This method should not be called directly. It is only made public because it must be called from objects in the com.salmonllc.jsp.tags package.
	 */
	public void add(HtmlComponent comp, int type) {
		Object o[] = new Object[2];
		o[0] = comp;
		o[1] = new Integer(type);

		comp.setParent(this);

		_compsVec.add(o);
	}
	/**
	 * Clears the submit component in this container or children containers. The container stores which component inside it
	 * submitted a particular page for one invocation so it can route to the correct submit performed methods. Once that's done,
	 * the framework needs to clear out that value for the next page invocation. This method is used by the framework and should not be called directly.
	 */
	public void clearSubmit() {
		_submit = null;
		Enumeration e = getComponents();

		while (e.hasMoreElements()) {
			Object o = e.nextElement();
			if (o instanceof HtmlContainer)
				 ((HtmlContainer) o).clearSubmit();
			else if (o instanceof HtmlImage)
				 ((HtmlImage) o).clearSubmit();
			else if (o instanceof JspContainer)
				 ((JspContainer) o).clearSubmit();

		}
	}
	public boolean executeEvent(int eventType) throws Exception {
		if (eventType == HtmlComponent.EVENT_OTHER) {
			HtmlComponent h = null;
			for (int i = 0; i < getComponentCount(); i++) {
				h = getComponent(i);
				if (!h.executeEvent(eventType))
					return false;
			}
		} else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
			boolean retVal = _submit.executeEvent(eventType);
			_submit = null;
			return retVal;
		}
		return true;
	}
	public void generateHTML(PrintWriter p, int rowNo) throws java.lang.Exception {
	}
	/**
	 * Gets a component from the container from the specified index.
	 */
	public HtmlComponent getComponent(int i) {
		String compName = null;
		Object[] o = (Object[]) _compsVec.elementAt(i);
		HtmlComponent comp = (HtmlComponent) o[0];

		if (debug) {
			compName = comp.getName();
			if (!Util.isFilled(compName)) {
				compName = comp.getFullName();
			}
			MessageLog.writeDebugMessage("processParms for " + compName + "\n", this);
		}
		return comp;
	}
	/**
	 * Gets the number of components in the container.
	 */
	public int getComponentCount() {
		return _compsVec.size();
	}
	/**
	 * This method will return a list of all components in the container.
	 */
	public Enumeration getComponents() {
		if (_compsVec == null) {
			Vector v = new Vector();
			return v.elements();
		} else {
			// WARNING:  Do not attempt to optimize this by storing the result in an
			// instance variable and reusing it, since an Enumeration is supposed to be used
			// one time only.
			Vector v = new Vector();
			int elementsSize = getComponentCount();
			for (int i = 0; i < elementsSize; i++) {
				HtmlComponent comp = (HtmlComponent) getComponent(i);
				if (comp != null)
					v.addElement(comp);
			}
			return v.elements();
		}
		//   return _compsVec.elements();
	}
	/**
	 * Returns true if the component is in this container
	 */
	public boolean isComponentInContainer(HtmlComponent comp) {
		HtmlComponent parent = comp.getParent();
		while (parent != null) {
			if (parent == this)
				return true;
			parent = parent.getParent();
		}
		return false;
	}
	/**
	 * Gets the type of component at the specified index
	 *	valid return values are  TYPE_COMP,	 TYPE_ROW, TYPE_HEADER,	 TYPE_FOOTER,  TYPE_GROUP_FOOTER, TYPE_GROUP_HEADER
	**/
	public int getComponentType(int i) {
		Object[] o = (Object[]) _compsVec.elementAt(i);
		return ((Integer) o[1]).intValue();
	}
	
	/**
	 * Gets the type of component assuming the component is in the container, otherwise it returns -1
	 *	valid return values are  TYPE_COMP,	 TYPE_ROW, TYPE_HEADER,	 TYPE_FOOTER,  TYPE_GROUP_FOOTER, TYPE_GROUP_HEADER
	**/
	public int getComponentType(HtmlComponent comp) {
		int ndx=indexOf(comp);
		if (ndx == -1)
			return -1;
		else
			return getComponentType(ndx);
	}	
	/**
	 * Returns the index of the component in the container
	 */
	public int getComponentIndex(HtmlComponent comp) {
		return indexOf(comp);
	}	
	
	/**
	 * Returns the component that submitted the page if that component is in the container or null otherwise.
	 * @return - HtmlComponent
	 */
	public HtmlComponent getSubmitComponent() {
		if (_submit == null)
			return null;
		else if (_submit == this)
			return this;
		else if (_submit instanceof HtmlContainer)
			return ((HtmlContainer) _submit).getSubmitComponent();
		else if (_submit instanceof JspContainer)
			return ((JspContainer) _submit).getSubmitComponent();
		else
			return _submit;
	}
	/**
	 * Pass in an object and this method will return what index it is at
	 */
	private int indexOf(Object o) {

		// loop through objects
		int compSize = getComponentCount();
		for (int i = 0; i < compSize; i++) {
			// when you find the one you are looking for return the index
			if (o == getComponent(i)) {
				return i;
			}
		}
		//	if you were unable to find the object you were looking for return -1
		return -1;
	}
	/**
	 * This method will process the parms from a post for every component in the container.
	 */
	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		String compName = null;
		try {

			if (!getVisible())
				return false;

			int compSize = getComponentCount();
			HtmlComponent comp = null;

			for (int i = 0; i < compSize; i++) {
				comp = getComponent(i);
				if (debug) {
					// assume not null
					compName = comp.getName();
					if (!Util.isFilled(compName)) {
						compName = comp.getFullName();
					}
					MessageLog.writeDebugMessage("processParms for " + compName + "\n", this);
				}

				if (comp.processParms(parms, rowNo))
					_submit = getComponent(i);
			}

			if (_submit != null)
				return true;
			else
				return false;

		} catch (Exception e) {
			MessageLog.writeErrorMessage("processParms for " + compName + "\n", e, this);
			throw (e);
		}
	}
	/**
	 * Replaces a html component with another one that you pass in.
	 * @param comp com.salmonllc.html.HtmlComponent - conponent that you would like to be in the container after the replace operation
	 * @param compToReplace Object - a handle to the component to replace ( this is the one that should be replaced after the call to this method )
	 * @return boolean
	 */
	boolean replaceComponent(HtmlComponent comp, Object compToReplace) {
		// JSP replaceComponent
		if (_compsVec == null) {
			return false;
		}

		// get the index of the comp so we can replace it
		int replaceIndex = this.indexOf(compToReplace);
		if (replaceIndex != -1) {

			// add componet contained
			setComponent(comp, replaceIndex);
			return true;
		} else {
			MessageLog.writeDebugMessage("JSP replaceComponent failed to replace the component ", this);
			return false;
		} // end else

	}
	/**
	 * This method sets a component at a specified index
	 */
	private void setComponent(HtmlComponent comp, int index) {

		// get the componet
		Object[] o = (Object[]) _compsVec.elementAt(index);

		// replace the comp
		comp.setParent(this);
		o[0] = comp;

		// put it back in the vector
		_compsVec.setElementAt(o, index);
	}
	/**
	 * Sets all the components in the container to enabled or not depending on the argument passed.
	 */

	public void setEnabled(boolean enabled) {
		for (int i = 0; i < _compsVec.size(); i++) {
			HtmlComponent comp = getComponent(i);
			if (comp instanceof HtmlFormComponent)
				 ((HtmlFormComponent) comp).setEnabled(enabled);
			else if (comp instanceof HtmlContainer)
				 ((HtmlContainer) comp).setEnabled(enabled);
			else if (comp instanceof JspContainer)
				 ((JspContainer) comp).setEnabled(enabled);

		}
		_enabled = enabled;
	}
	/**
	 * Sets all the components in the container to visible or not depending on the argument passed.
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		for (int i = 0; i < _compsVec.size(); i++) {
			HtmlComponent comp = getComponent(i);
			comp.setVisible(visible);
		}

	}

	/**
	 * Creates a String representation of the contents of the JspContainer
	 * @return - contents of container as a string representation
	 */
	public String toString() {

		StringBuffer sBuff = new StringBuffer("[");
		int compSize = getComponentCount();

		HtmlComponent c = null;
		boolean commaFlag = false;
		if (_compsVec.isEmpty()) {
			sBuff.append("NULL");
		}

		for (int i = 0; i < compSize; i++) {
			c = (HtmlComponent) getComponent(i);
			if (commaFlag == true) {
				sBuff.append(",");
			}

			String name = c.getName();
			if (Util.isNull(name)) {
				name = "NULL_NAME";
			} else if (Util.isEmpty(name)) {
				name = "EMPTY";
			}
			sBuff.append(name);
			commaFlag = true;

		}
		sBuff.append("]");
		//return super.toString() + "\n" + getName();
		return super.toString() + "\n" + sBuff.toString();
	}

	/**
	 * override in sub classes to allow for controller logic to be attached to the container
	 */
	public void initialize() {

	}

	/**
	 * Returns the controller this container is in
	 */
	public JspController getController() {
		return (JspController)getPage();
	}

	/**
	 * Framework method, do not call directly
	 */
	public boolean getDoInit() {
		return _doInit;
	}

	/**
	 * Framework method, do not call directly
	 */
	public void setDoInit(boolean b) {
		_doInit = b;
	}

}
