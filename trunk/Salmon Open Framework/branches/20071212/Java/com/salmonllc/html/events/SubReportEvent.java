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
package com.salmonllc.html.events;

import com.salmonllc.jsp.JspDataTable;
import com.salmonllc.jsp.JspSubReportContainer;
import com.salmonllc.sql.DataStoreBuffer;
import java.awt.AWTEvent;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/events/SubReportEvent.java $
//$Author: Dan $ 
//$Revision: 1 $ 
//$Modtime: 8/26/04 11:00a $ 
/////////////////////////
 
/**
 * This event is passed to the SubReportListener
 */
public class SubReportEvent extends AWTEvent{
	private JspDataTable _dataTable;
	private DataStoreBuffer _dataStoreBuffer;
	private int _row;
	
	public SubReportEvent(JspSubReportContainer subreport, JspDataTable tab, DataStoreBuffer ds, int row) {
		super(subreport,0);
		_dataTable=tab;
		_dataStoreBuffer=ds;
		_row=row;
	}
		
	/**
	 * @return Returns the dataStoreBuffer.
	 */
	public DataStoreBuffer getDataStoreBuffer() {
		return _dataStoreBuffer;
	}
	/**
	 * @return Returns the dataTable.
	 */
	public JspDataTable getDataTable() {
		return _dataTable;
	}
	/**
	 * @return Returns the row.
	 */
	public int getRow() {
		return _row;
	}
}	
