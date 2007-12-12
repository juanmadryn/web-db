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
//$Archive: /JADE/SourceCode/com/salmonllc/forms/DetailFormListener.java $
//$Author: Dan $ 
//$Revision: 13 $ 
//$Modtime: 10/30/02 2:38p $ 
/////////////////////////
/**
 * Encapsulates user-defined methods to call before and after key events in the
 * Search/List/Detail forms.
 */
public interface DetailFormListener {
	public static final int ERROR_DUPLICATE_ROW = 0;
	public static final int ERROR_EMPTY_FIELD = 1;
	public static final int ERROR_INVALID_ENTRY = 2;
/**
 * Called when an error is encountered.
 * @return int	If true, continue processing the event.  Same convention as in
 *	ValueChangedEvent.valueChanged(), SubmitEvent.submitPerformed(), etc.
 */
boolean onDetailError (int code, String message, com.salmonllc.html.HtmlComponent component);
/**
 * Called after Delete button has been processed.
 * @return boolean	Currently undefined.
 */
boolean postDetailDelete () throws Exception;
/**
 * Called after pageRequested is processed.
 * @return boolean	Currently undefined.
 */
boolean postDetailRequest () throws Exception;
/**
 * Called after Save button is processed on Detail page.
 * @return boolean	Currently undefined.
 */
boolean postDetailSave () throws Exception;
/**
 * Called before Delete button is processed.
 * @return boolean	True if processing is to continue.
 */
boolean preDetailDelete () throws Exception;
/**
 * Called before pageRequested() is processed.
 * @return boolean	True if continue processing, else stop processing.
 */
boolean preDetailRequest () throws Exception;
/**
 * Called before Save button is processed on Detail page.
 * @return boolean	True if continue to save, else do not continue.
 */
boolean preDetailSave () throws Exception;
}
