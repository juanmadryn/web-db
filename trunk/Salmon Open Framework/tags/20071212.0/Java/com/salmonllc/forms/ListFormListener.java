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

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/forms/ListFormListener.java $
//$Author: Dan $ 
//$Revision: 10 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
/**
 * Encapsulates user-defined methods to call before and after key events in the
 * Search/List/Detail pages.
 */
public interface ListFormListener {
/**
 * Called after the Add button in the List display box has been processed.
 * @return boolean	Undefined at this time.
 */
public boolean postListAdd ();
/**
 * Called after datastore retrieval.
 *
 * @return boolean	If false, stop further processing of listeners.
 */
public boolean postListRetrieve () throws Exception;
/**
 * Called before processing the Add submit button on the List display box.
 * @return boolean	If true, continue processing.
 */
public boolean preListAdd ();
/**
 * Called before datastore retrieval.
 * @return boolean	If true, continue with retrieval.
 */
public boolean preListRetrieve () throws Exception;
}
