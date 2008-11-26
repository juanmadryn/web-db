package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlContainer.java $
//$Author: Dan $
//$Revision: 33 $
//$Modtime: 10/17/03 10:27a $
/////////////////////////

import com.salmonllc.jsp.JspContainer;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is a holder for other components. Placing components in a container allows them all to be acted upon once.
 */
public class HtmlContainer extends HtmlComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5042114106192995457L;

	/** Vector to store all the componets added to the container */
	protected Vector<HtmlComponent> _componentsVec = new Vector<HtmlComponent>();

	/** component that does the submit if there is one */
	protected HtmlComponent _submit;

	/** Centers the componets in the container */
	protected boolean _center = false;

	/**  enabled flag */
	protected boolean _enabled = true;

	public HtmlContainer(String name, com.salmonllc.html.HtmlPage p) {
		super(name, p);
	}
	/**
	 * Adds a new html component to the container.
	 */
	public void add(HtmlComponent comp) {
		if (_componentsVec == null)
			_componentsVec = new Vector<HtmlComponent>();

		_componentsVec.addElement(comp);
		comp.setParent(this);
	}
	/**
	 * Clears the submit component in this container or children containers. The container stores which component inside it
	 * submitted a particular page for one invocation so it can route to the correct submit performed methods. Once that's done,
	 * the framework needs to clear out that value for the next page invocation. This method is used by the framework and should not be called directly.
	 */
	public void clearSubmit() {
		_submit = null;
		Enumeration<HtmlComponent> e = getComponents();

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
			if (_componentsVec != null) {
				HtmlComponent h = null;
				for (int i = 0; i < _componentsVec.size(); i++) {
					h = (HtmlComponent) _componentsVec.elementAt(i);
					if (!h.executeEvent(eventType))
						return false;
				}
			}
		} else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
			boolean retVal = _submit.executeEvent(eventType);
			_submit = null;
			return retVal;
		}
		return true;
	}
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		if (_componentsVec == null)
			return;
		//
		if (!_visible)
			return;
		//
		if (_center)
			p.print("<CENTER>");
		//
		HtmlComponent h = null;
		for (int i = 0; i < _componentsVec.size(); i++) {
			h = (HtmlComponent) _componentsVec.elementAt(i);
			if (h != null) {
				h.generateHTML(p, rowNo);
			}
		}
		//
		if (_center)
			p.print("</CENTER>");
	}
	public void generateHTML(PrintWriter p, int rowStart, int rowEnd) throws Exception {
		if (_componentsVec == null)
			return;
		//
		if (!_visible)
			return;
		//
		if (_center)
			p.print("<CENTER>");
		//
		HtmlComponent h = null;
		for (int i = 0; i < _componentsVec.size(); i++) {
			h = (HtmlComponent) _componentsVec.elementAt(i);
			if (h != null)
				h.generateHTML(p, rowStart, rowEnd);
		}
		//
		if (_center)
			p.print("</CENTER>");
	}
	public void generateInitialHTML(PrintWriter p) throws Exception {
		if (!_visible || _componentsVec == null)
			return;
		//
		HtmlComponent h = null;
		for (int i = 0; i < _componentsVec.size(); i++) {
			h = (HtmlComponent) _componentsVec.elementAt(i);
			if (h != null)
				h.generateInitialHTML(p);
		}
	}
	/**
	 * Use this method to find out if the components will be centered in the container
	 * @return - boolean
	 */
	public boolean getCenter() {
		return _center;
	}
	/**
	 * This method will return a single component if the
	 * name or a portion of the name is found in this HtmlContainer or any
	 * HtmlContainer contained in this container.
	 * @return HtmlComponent - if a component can not be found null is returned
	 * @param name - name of component being searched for
	 */
	public HtmlComponent getComponent(String name) {
		HtmlComponent ret = null;
		HtmlComponent comp = null;
		Enumeration<HtmlComponent> enumera = getComponents();
		String compName = null;
		// we will search all items in this enumeraeration
		// recursivly calling the containers getComponent method
		boolean breakout = enumera.hasMoreElements();
		while (breakout && enumera.hasMoreElements()) {
			ret = null;
			
			comp = (HtmlComponent) enumera.nextElement();
			compName = comp.getName();

			// if passed name or comparision name is null return earily because we can not go any further.
			if (compName == null || name == null) {
				MessageLog.writeInfoMessage("getComponent\n\t*** Looking for component named " + name + " but " + getFullName() + " contains a component with a null name. \nSolution may be to pass empty string instead of null. ***", 9, this);
				return ret;
			}

			if (compName.indexOf(name) > -1) {
				ret = comp;
				breakout = false;
			} else {
				if (comp instanceof HtmlTable) {
					ret = ((HtmlTable) comp).getComponent(name);
					// checking the value of the return value
					breakout = (ret == null);
				} else if (comp instanceof HtmlContainer) {
					ret = ((HtmlContainer) comp).getComponent(name);
					// checking the value of the return value
					breakout = (ret == null);
				}
			}
		}

		return ret;
	}
	/**
	 * This method will return a list of all components in the container.
	 */
	public Enumeration<HtmlComponent> getComponents() {
		if (_componentsVec == null) {
			Vector<HtmlComponent> v = new Vector<HtmlComponent>();
			return v.elements();
		} else
			return _componentsVec.elements();
	}
	/**
	 * Returns true if the component is enabled to respond to user input.
	 * @return boolean
	 */
	public boolean getEnabled() {
		return _enabled;
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
	 * Adds a new html component to the container at a specified index
	 */
	public void insertComponentAt(HtmlComponent comp, int index) {
		if (_componentsVec == null)
			_componentsVec = new Vector<HtmlComponent>();

		if (_componentsVec.isEmpty()) {

			// add componet contained
			_componentsVec.addElement(comp);

		} else {
			// add componet contained at specified location
			_componentsVec.insertElementAt(comp, index);

		}
		comp.setParent(this);
	}
	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		String compName = null;
		try {
			if (!getVisible())
				return false;

			if (_componentsVec != null) {
				int compSize = _componentsVec.size();
				HtmlComponent comp = null;
				for (int i = 0; i < compSize; i++) {
					comp = (HtmlComponent) _componentsVec.elementAt(i);

					if (debug) {
						// assume not null
						compName = comp.getName();
						if (!Util.isFilled(compName)) {
							compName = comp.getFullName();
						}
						MessageLog.writeDebugMessage("processParms for " + compName + "\n", this);
					}
					if (comp.processParms(parms, rowNo))
						_submit = (HtmlComponent) _componentsVec.elementAt(i);
				}
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
	 * Removes an html component from this container.
	 * @param comp The component to remove
	 */
	public void remove(HtmlComponent comp) {
		if (_componentsVec == null)
			return;

		int compSize = _componentsVec.size();
		for (int i = 0; i < compSize; i++) {
			if (((HtmlComponent) _componentsVec.elementAt(i)) == comp) {
				_componentsVec.removeElementAt(i);
				return;
			}
		}
	}
	/**
	 * This method removes all the components from a container
	 */
	public void removeAll() {
		int compSize = _componentsVec.size();
		for (int i = 0; i < compSize; i++) {
			HtmlComponent hc = (HtmlComponent) _componentsVec.elementAt(i);
			if (hc instanceof HtmlTable) {
				((HtmlTable) hc).removeAll();
			} else if (hc instanceof HtmlContainer) {
				((HtmlContainer) hc).removeAll();
			}
		}
		_componentsVec.removeAllElements();
	}
	/**
	 * Replaces a html component with another one that you pass in.
	 * @param comp com.salmonllc.html.HtmlComponent - conponent that you would like to be in the container after the replace operation
	 * @param compToReplace Object - a handle to the component to replace ( this is the one that should be replaced after the call to this method )
	 * @return boolean
	 */
	public boolean replaceComponent(HtmlComponent comp, Object compToReplace) {
		if (_componentsVec == null) {
			return false;
		}

		// get the index of the comp so we can replace it
		int replaceIndex = _componentsVec.indexOf(compToReplace);
		if (replaceIndex != -1) {
			// add componet contained
			_componentsVec.setElementAt(comp, replaceIndex);
			comp.setParent(this);
			return true;
		} else {
			MessageLog.writeDebugMessage(" HtmlContainer replaceComponent failed to replace the component ", this);
			return false;
		}
	}
	/**
	 * This method will clear all pending events from the event queue for this component and components it contains.
	 */
	public void reset() {
		Enumeration<HtmlComponent> comps = getComponents();
		while (comps.hasMoreElements()) {
			HtmlComponent c = (HtmlComponent) comps.nextElement();
			c.reset();
		}
		_submit = null;
	}
	/**
	 * Use this method to indicate that the components in the container should be centered.
	 */
	public void setCenter(boolean center) {
		_center = center;
	}
	/**
	 * Sets the flag for ability to respond to user input (true = does respond).
	 */
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
		Enumeration<HtmlComponent> e = getComponents();
		HtmlComponent c = null;
		while (e.hasMoreElements()) {
			c = (HtmlComponent) e.nextElement();
			try {
				((HtmlFormComponent) c).setEnabled(enabled);
			} catch (ClassCastException ce) {
				try {
					((HtmlContainer) c).setEnabled(enabled);
				} catch (ClassCastException ce1) {
				}
			}
		}
	}
	/**
	 * Creates a String representation of the contents of the HtmlContainer
	 * @return - contents of container as a string representation
	 */
	public String toString() {
		StringBuffer sBuff = new StringBuffer("[");
		int compSize = _componentsVec.size();
		HtmlComponent c = null;
		boolean commaFlag = false;
		if (_componentsVec.isEmpty()) {
			sBuff.append("NULL");
		}

		for (int i = 0; i < compSize; i++) {
			c = (HtmlComponent) _componentsVec.elementAt(i);
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

		return super.toString() + "\n" + sBuff.toString();
	}
}
