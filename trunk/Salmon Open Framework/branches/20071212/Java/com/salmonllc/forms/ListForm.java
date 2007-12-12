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
//$Archive: /SOFIA/SourceCode/com/salmonllc/forms/ListForm.java $
//$Author: Dan $ 
//$Revision: 73 $ 
//$Modtime: 6/11/03 4:27p $ 
/////////////////////////


import java.util.Vector;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;

/**
 *
 * Implements Search/List form.
 * At the top will be a display box with a table for search criteria and a "Search" button.
 * Following will be another display box with a datatable to display the results from the
 * search.  This box contains a "Add" button which requests the detail page (name supplied)
 * with the parameters "mode=add", and "<primary_key>=<value>", the second repeated for
 * as many primary keys as there are.
 * The client of this class will add data-store columns, specifying whether they go in
 * the search box, list box, or both. <BR>
 * The form is implemented as a container to go within an instance of (a subclass of)
 * HtmlPage.  Specify as many data store columns as you want using the addColumn* methods
 * and indicate which goes in the search box and which goes in the list box. <BR>
 * To hook key activities such as the "Add" button, implement the ListFormListener interface
 * and call addListener. <BR>
 * Example: <BR>
 <PRE>
ListForm form = new ListForm(this, "UserDetailPage");
add(form);
form.addColumn(_table, "user_id", "ID#", DataStoreBuffer.DATATYPE_INT, form.PRIMARY_KEY,
"UserDetailPage?mode=update");
// add other columns ...
</PRE>
 */
public class ListForm extends BaseListForm
{
	// Other
	protected String _detailPageName;
/**
 * Implements standard Search/List form.
 * Default data store is created.  Standard add button is included.
 * @param page 	Page containing this form as a component.
 * @param detailPageName Name of associated detail page.
 */
public ListForm (HtmlPage page, String detailPageName) {
	this(page, detailPageName, null, 0);
}
/**
 * Implements standard Search/List form.
 * Standard add button is included.
 * @param page HtmlPage			Page containing this form as a component.
 * @param detailPageName String	Name of corresponding detail page to use as link destination.
 * @param ds DataStore			Data store object to use; if null then create one.
 */
public ListForm (HtmlPage page, String detailPageName, DataStore ds) {
	this(page, detailPageName, ds, 0);
}
/**
 * Implements standard Search/List form.
 * @param page				Page containing this form as a component.
 * @param detailPageName	Name of corresponding detail page to use as link destination.
 * @param ds				Data store object to use; if null then create one.
 * @param flags 			Bitwise-OR combination of INIT_NO_SEARCH_BUTTON, etc.
 */
public ListForm(HtmlPage page, String detailPageName, DataStore ds, int flags)
{
	super(page, ds, flags);
	_detailPageName = detailPageName;
	
	// As a favor to client classes, add page as listener if appropriate
	if (page instanceof ListFormListener)
		addListener((ListFormListener) page);
}
/**
 * Inherited abstract method.
 * @return boolean
 * @param e com.salmonllc.html.events.SubmitEvent
 */
public boolean submitPerformed(SubmitEvent e) throws Exception
{
	super.submitPerformed(e);
	//
	MessageLog.writeDebugMessage(" submitPerformed(SubmitEvent e)", this);
	HtmlComponent c = e.getComponent();
	HtmlPage page = getPage();
	if (c == _btnAddListForm)
	{
		//
		int listenersSize = _listeners.size();
		for (int i = 0; i < listenersSize; i++)
		{
			if (!((ListFormListener) _listeners.elementAt(i)).preListAdd())
			{
				return false;
			}
		}
		//
		if (_detailPageName == null)
		{
			return false;
		}

		if(_detailPageName != null && _detailPageName.indexOf("?") == -1)
			_detailPageName += "?mode=add";
		else
			_detailPageName += "&mode=add";
			
		StringBuffer URL = new StringBuffer(_detailPageName);
		// get all the params and put them on the end of the redirect URL
		int paramKeysSize = _pageParamsKeys.size();
		String param = null;
		Vector valVec = null;
		for (int keyIndex = 0; keyIndex < paramKeysSize; keyIndex++)
		{
			param = (_pageParamsKeys.elementAt(keyIndex)).toString();
			valVec = (Vector) _pageParamsValues.elementAt(keyIndex);
			for (int valIndex = 0; valIndex < valVec.size(); valIndex++)
			{
				URL.append("&").append(param).append("=").append(valVec.elementAt(valIndex).toString());
			}
		}
		page.getCurrentResponse().sendRedirect(URL.toString());
		for (int i = 0; i < listenersSize; i++)
			 ((ListFormListener) _listeners.elementAt(i)).postListAdd();
	}
	return true;
}

/**
 * This method returns the name of the detail page for this list
 */
public String getDetailPageName() {
	return _detailPageName;
}

/**
 * This method sets the name of the detail page for this list
 */
public void setDetailPageName(String newPageName) {
	_detailPageName = newPageName;
}
}
