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
package com.salmonllc.sql;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSTableAliasDescriptor.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 6/19/03 5:18p $ 
/////////////////////////

/**
 * This class is public as an implementation detail. It should not be used outside the framework.
 */

public class DSTableAliasDescriptor implements java.io.Serializable {
	private String _table;
	private String _alias;
	public DSTableAliasDescriptor(String table, String alias) {
		_table = table;
		_alias = alias;
	}
	public String getAlias() {
		return _alias;
	}
	public String getTable() {
		return _table;
	}
	public void setAlias(String alias) {
		_alias = alias;
	}
	public void setTable(String table) {
		_table = table;
	}
}
