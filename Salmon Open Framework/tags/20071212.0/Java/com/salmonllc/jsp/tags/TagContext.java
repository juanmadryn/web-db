//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2003, Salmon LLC
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

import com.salmonllc.jsp.JspContainer;
import com.salmonllc.jsp.JspController;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Stack;
import java.util.Vector;
import javax.servlet.jsp.JspWriter;

/**
 * This object is used internally by the framework to store tag library context information for the dureation of a single page request.It should not be used outside the framework.
 */
public class TagContext implements Serializable{

	private JspController _controllerClass;
	private boolean _initializing;
	private boolean _dreamWeaverMode;
	private boolean _refIndexPrinted = false;
	private boolean _printVars;
	private boolean _generateCode;
	private Vector _validatorAttributes;
	private String _controller;
	public static final String TAG_CONTEXT_REQ_KEY = "$$$PageTag$$$";
	private Stack _containerStack=new Stack();
	private Stack _tagStack=new Stack();
	
	public TagContext() {
		super();	
	}	
	
	public TagContext(String controller) {
		_controller = controller;	
	}	
	
	public void pushContainer(JspContainer cont) {
		_containerStack.push(cont);	
	}	
	public void popContainer() {
		if (!_containerStack.isEmpty())
			_containerStack.pop();	
	}	
	public JspContainer getCurrentContainer() {
		if (_containerStack.isEmpty())
			return null;
		else	
			return (JspContainer)_containerStack.peek();	
	}	
	/**
	 * Returns the controllerClass.
	 * @return JspController
	 */
	public JspController getControllerObject() {
		return _controllerClass;
	}

	/**
	 * Sets the controllerClass.
	 * @param controllerClass The controllerClass to set
	 */
	public void setControllerObject(JspController controllerClass) {
		_controllerClass = controllerClass;
	}

	/**
	 * Returns the initializing.
	 * @return boolean
	 */
	public boolean isInitializing() {
		return _initializing;
	}

	/**
	 * Sets the initializing.
	 * @param initializing The initializing to set
	 */
	public void setInitializing(boolean initializing) {
		_initializing = initializing;
	}
	

	/**
	 * Returns the dreamWeaverMode.
	 * @return boolean
	 */
	public boolean getDreamWeaverMode() {
		return _dreamWeaverMode;
	}

	/**
	 * Returns the refIndexPrinted.
	 * @return boolean
	 */
	public boolean getRefIndexPrinted() {
		return _refIndexPrinted;
	}

	/**
	 * Sets the dreamWeaverMode.
	 * @param dreamWeaverMode The dreamWeaverMode to set
	 */
	public void setDreamWeaverMode(boolean dreamWeaverMode) {
		_dreamWeaverMode = dreamWeaverMode;
	}

	/**
	 * Sets the refIndexPrinted.
	 * @param refIndexPrinted The refIndexPrinted to set
	 */
	public void setRefIndexPrinted(boolean refIndexPrinted) {
		_refIndexPrinted = refIndexPrinted;
	}

	/**
	 * Returns the generateCode.
	 * @return boolean
	 */
	public boolean isGenerateCode() {
		return _generateCode;
	}

	/**
	 * Returns the printVars.
	 * @return boolean
	 */
	public boolean isPrintingVars() {
		return _printVars;
	}

	/**
	 * Sets the generateCode.
	 * @param generateCode The generateCode to set
	 */
	public void setGenerateCode(boolean generateCode) {
		_generateCode = generateCode;
	}

	/**
	 * Sets the printVars.
	 * @param printVars The printVars to set
	 */
	public void setPrintingVars(boolean printVars) {
		_printVars = printVars;
	}

	/**
	 * This adds a tag to the list of validator tags maintained by the page tag
	 */
	public void addValidatorTag(ValidatorTag.Attributes att) {
		if (_validatorAttributes == null)
			_validatorAttributes = new Vector();

		_validatorAttributes.addElement(att);

	}

	/**
	 * Returns all the validator attributes used in this tag
	 */
	public Vector getValidatorAttributes() {
		return _validatorAttributes;
	}
	
	void printVars(JspWriter p) throws IOException {
		_controllerClass.printVars(new PrintWriter(p));
	}
	
	void generateCode(JspWriter p) throws IOException {
		_controllerClass.generateCode(new PrintWriter(p), _controller);
	}
	
	void pushTag(BaseBodyTag tag) {
		_tagStack.push(tag);
	}	
	BaseBodyTag popTag() {
		return (BaseBodyTag)_tagStack.pop();
	}
	public BaseBodyTag findAncestorWithClass(Class tagClass) {
		if (_tagStack.isEmpty())
			return null;
		for (int i = (_tagStack.size() - 1); i >= 0; i--) {
			BaseBodyTag t=(BaseBodyTag)_tagStack.elementAt(i);
			if (tagClass.isInstance(t))
				return t;
		}	
		return null;
	}	
}
