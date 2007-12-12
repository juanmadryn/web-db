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
 * Implements a file filter to test whether a file ends with a particular suffix
 */
public class FilterFile implements java.io.FilenameFilter 
{
	private String _suffix;
	private boolean _bCaseInsensitive;
/**
 * FileFilter constructor comment.
 */
public FilterFile(String suffix) 
{
	this(suffix, false);
}
/**
 * FileFilter constructor comment.
 */
public FilterFile(String suffix, boolean bCaseInsensitive) 
{
	super();

	_bCaseInsensitive = bCaseInsensitive;
	_suffix=suffix;
}
	/**
	 * Tests if a specified file should be included in a file list.
	 *
	 * @param   dir    the directory in which the file was found.
	 * @param   name   the name of the file.
	 * @return  <code>true</code> if and only if the name should be
	 * included in the file list; <code>false</code> otherwise.
	 */
public boolean accept(java.io.File dir, String name) 
{
	if (_bCaseInsensitive)
	{
		if (name.toUpperCase().endsWith(_suffix.toUpperCase()))
		     return true;
	}
	else
	{
		if (name.endsWith(_suffix))
		     return true;
	}
	
	return false;
}
}
