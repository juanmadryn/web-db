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
package com.salmonllc.util;

/**
 * Interface used by the message log to write messages to different logging packages, like JDK 1.4 logging and Log4J
 */

public interface Logger {
	public static final int TYPE_ASSERTION=0;
	public static final int TYPE_DEBUG=1;
	public static final int TYPE_ERROR=2;
	public static final int TYPE_INFO=3;
	public static final int TYPE_SQL=4;

	/**
	 * Log a message to the appropriate message logging package
	 * @param message The message to log
	 * @param type The type, see TYPE constants at the top of the page
	 * @param level The error level
	 * @param e an error message
	 * @param o the object logging the message
	 */
	public void log(String message, int type, int level, Throwable e, Object o );
}
