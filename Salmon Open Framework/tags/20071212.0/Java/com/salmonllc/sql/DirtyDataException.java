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

import java.sql.SQLException;

/**
   * Indicates stale data during row deletes and updates
   * used in conjunction with concurrency validation
   * @author Tyler Williams
   */
  public class DirtyDataException extends SQLException {
  	private int _row;
  	private int _buffer; 
	public DirtyDataException(String msg) {
	  super(msg);
	  _row = -1;
	}
	void setRowAndBuffer(int row, int buffer) {
		_row = row;
		_buffer = buffer;
	}	
	public DirtyDataException(String msg, int row, int buffer) {
	  super(msg);
	  _row = row;
	  _buffer = buffer;
	}
	public int getRow() {
		return _row;	
	}	
	public int getBuffer() {
		return _buffer;	
	}	
  }
