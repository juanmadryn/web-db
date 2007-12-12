//** Copyright Statement ***************************************************
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
//** End Copyright Statement ***************************************************
package com.salmonllc.sql;


/**
 * An event object for QBEBuilders, QBEListeners
 */
public class QBEEvent extends java.awt.AWTEvent {

private String _sql;
private int _type;

public static final int TYPE_SQL_PREVIEW = 0;
public static final int TYPE_FILTER_PREVIEW = 1;

	public QBEEvent(QBEBuilder source, int type, String SQL) {
		super(source, 0);
		_type = type;
		_sql = SQL;
	}


/**
 * Return the SQL or filter the QBE will run
 */
public String getFilter() {
	return _sql;
}

/**
 * Replace the SQL or filter the QBE will run
 */
public void setFilter(String filter) {
	 _sql = filter;
}


/**
 * Returns the type of event TYPE_SQL_PREVIEW or TYPE_FILTER_PREVIEW
 */
public int getType() {
	return _type;
}

}
