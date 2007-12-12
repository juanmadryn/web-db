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
package com.salmonllc.forms;

import com.salmonllc.html.*;
/**
 * This type is used as a typesafe container for ListForm Components.
 */
public class BaseListFormComponent
{
	// 1 - Name, 2 - search component, 3 - list component, 4 - search component caption
	private String _name;
	private HtmlComponent _listComp;
	private HtmlComponent _searchComp;
	private HtmlComponent _searchCapComp;
/**
 * This method is the default Constructor
 */
public BaseListFormComponent()
{
	super();
}

/**
 * Get the List Component.
 */
public HtmlComponent getListComponent()
{
	return _listComp;
}
/**
 * Get the Name of this Component.
 */
public String getName()
{
	return _name;
}
/**
 * Get the Search Caption Component.
 */
public HtmlComponent getSearchCapComponent()
{
	return _searchCapComp;
}
/**
 * Get the Search Component.
 */
public HtmlComponent getSearchComponent()
{
	return _searchComp;
}
/**
 * Set the List Component.
 */
public void setListComponent(HtmlComponent comp)
{
	_listComp = comp;
}
/**
 * Set the Name of the Component.
 */
public void setName(String name)
{
	 _name = name;
}
/**
 * Set the Search Caption Component.
 */
public void setSearchCapComponent(HtmlComponent comp)
{
	_searchCapComp= comp;
}
/**
 * Set the Search Component.
 */
public void setSearchComponent(HtmlComponent comp)
{
	_searchComp = comp;
}
}
