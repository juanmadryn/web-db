package infraestructura.controllers;

import java.io.Serializable;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com


/**
 * This object contains user customization info for the GUI. This object will be stored in the session. In the base page  request methods, session will be tested for this object. If there is a object in the session contents on this object will be applied to the GUI.
 *
 */
public class GuiOptions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2027865868773492136L;
	public static final int NAVBAR_VERTICAL=1;
	public static final int NAVBAR_HORIZONTAL=0;


	private int _iNavBarOrientation = NAVBAR_VERTICAL; //Default value

	public GuiOptions(){
		super();
	}

	public int getNavBarOrientation() {
		return _iNavBarOrientation;
	}

	public void setNavBarOrientation(int iNavBarOrientation) {
		this._iNavBarOrientation = iNavBarOrientation;
	}
}
