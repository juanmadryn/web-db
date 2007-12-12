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

package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspSubReportContainer.java $
//$Author: Dan $
//$Revision: 2 $
//$Modtime: 8/27/04 10:17a $
/////////////////////////

import java.util.Vector;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.events.SubReportEvent;
import com.salmonllc.html.events.SubReportListener;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.sql.DataStoreBuffer;

/**
 * This class is can be used by JspDataTables to allow nested reports
 */

public class JspSubReportContainer extends JspContainer {

	private Vector _listeners;

	public JspSubReportContainer(String name, HtmlPage p) {
		super(name, p);
	}

	/**
	 * This method adds a listener the will be notified when this report is
	 * invoked.
	 * 
	 * @param l
	 * The listener to add.
	 */
	public void addSubReportListener(SubReportListener l) {
		if (_listeners == null)
			_listeners = new Vector();

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l)
				return;
		}

		_listeners.addElement(l);
	}

	/**
	 * This method removes a listener from the list that will be notified when
	 * the report is invoked.
	 * 
	 * @param l
	 * The listener to remove.
	 */
	public void removeSubReportListener(SubmitListener l) {
		if (_listeners == null)
			return;

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				_listeners.removeElementAt(i);
				return;
			}
		}
	}

	/**
	 * Framework method, do not call directly
	 */
	public void notifyListeners() throws Exception {
		if (_listeners==null || _listeners.size()==0)
			return;
		HtmlComponent parent = getParent();
		while (parent != null && ! (parent instanceof JspDataTable)) 
			parent=parent.getParent();
		
		int parentRow=-1;
		DataStoreBuffer parentDs=null;
		if (parent != null) {
			parentRow=((JspDataTable)parent).getLastRenderedRow();
			parentDs=((JspDataTable)parent).getDataStoreBuffer();
		}	
		SubReportEvent e = new SubReportEvent(this, (JspDataTable)parent, parentDs, parentRow);

		for (int i = 0; i < _listeners.size(); i++) {
			SubReportListener l = (SubReportListener) _listeners.elementAt(i);
			l.subReportInvoked(e);
		}	
	}	
	

}