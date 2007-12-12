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

package com.salmonllc.parser;

/**
 * This interface is a mechanism to describe a custom translation for a column in the byte array. <BR>
 * For Example if the data value in the byte array is A, this A can be translated to a more meaningful <BR>
 *  description, such as Allowed.
 */


public interface CustomType
{

    /**
	  To get the translated object representation of a specified value at the column represented by this <CODE>CustomType</CODE>.
      This gets called by the getObject method in ByteArrayParser to give the object representation of a column.

	  <P><PRE>
	  Method: public Object getDataValue(String sLookup);
	  Visibility: Public
	  Purpose:	To return the translated object representation of the data.
	  </PRE></P>
	  @param sLookup			The data at the location of this column in the ByteArrayParser.
	  @return					Returns the translated object representation of the data.
	*/
	public Object getDataValue(String sLookup);
	
    /**
	  To get the string value representation of an object to be set at the column for this <CODE>CustomType</CODE>.
      This gets called by the setObject method in ByteArrayParser to set the string value representation of the column.

	  <P><PRE>
	  Method: public String toString(Object oValue);
	  Visibility: Public
	  Purpose:	To return the string value representation of the passed object.
	  </PRE></P>
	  @param oValue			The Object to set this column to in the ByteArrayParser.
	  @return				Returns the string value representation of this object.
	*/
	public String toString(Object oValue);
}
