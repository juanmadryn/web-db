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
 * This interface is a mechanism to let you create a class that contains a ByteArrayParser.
 * And be able to manipulate the ByteArrayParser from some other class.
 */

public interface ParserInterface
{
    /**
	  To set a column with an object for a specified column in a  <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setObject(String sProperty,Object oValue) throws Exception
	  Visibility: Public
	  Purpose:	To set the value of a column in a parsed object.
	  </PRE></P>
	  @param sProperty			 	Specifies the property/Column that you wish to set the value of.
	  @oValue					 	Specifies the value you want to set to a particular property/Column.
	  @exception	Exception		<P><PRE>Throws five possible Exception under these Circumstances.
									Circumstance 1: If the property/column specified does not exist.
									Circumstance 2: If the length of the values being passed is not equal to the required column length for String objects.
									Circumstance 3: If doParse has not been called.
									Circumstance 4: If the object passed is null.
									Circumstance 5: If the value of an Integer or Float type is not within its min/max range determined by its length.
									</PRE></P>
	*/
	public void setObject(String sField,Object oValue) throws ParserException;

    /**
	  To set the bytes of a column from a string for a specified column in a  <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setValue(String sProperty,String sValue) throws Exception
	  Visibility: Public
	  Purpose:	To set the value of a column in a parsed object.
	  </PRE></P>
	  @param sProperty			 	Specifies the property/Column that you wish to set the value of.
	  @param sValue				 	Specifies the value you want to set to a particular property/Column.
	  @exception	Exception		<P><PRE>Throws two possible Exception under these Circumstances.
									Circumstance 1: If the property/column specified does not exist.
									Circumstance 2: If the length of the values being passed is not equal to the required column length.
									</PRE></P>
	*/
	public void setValue(String sField,String sValue) throws ParserException;

    /**
	  To get an object representation of a column from a specified column in a  <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public Object getObject(String sProperty) throws Exception
	  Visibility: Public
	  Purpose:	To get the object value of a column from a parsed object.
	  </PRE></P>
	  @param sProperty			 	Specifies the property/Column that you wish to get the value of.
	  @return 						Returns the value of the specified column as an Object.
	  @exception	Exception		<P><PRE>Throws two possible Exception under this Circumstance.
									Circumstance: If doParse method was never called.
									Circumstance: If the property/column specified does not exist.
									</PRE></P>
	*/
	public Object getObject(String sField) throws ParserException;

    /**
	  To get the bytes of a column as a string from a specified column in a  <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public String getValue(String sProperty) throws Exception
	  Visibility: Public
	  Purpose:	To get the value of a column from a parsed object.
	  </PRE></P>
	  @param sProperty			 	Specifies the property/Column that you wish to get the value of.
	  @return 						Returns the value of the specified column.
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance: If the property/column specified does not exist.
									</PRE></P>
	*/
	public String getValue(String sField) throws ParserException;

    /**
	  To get a column precision of a specified column in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getPrecision(String sProperty)
	  Visibility: Public
	  Purpose:	To return the precision of a specified column.
	  </PRE></P>
	  @param sProperty			 	Specifies the property/Column that you wish to get the value of.
	  @return 					Returns the precision of the specified column.
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance: If the property/column specified does not exist.
									</PRE></P>
	*/
	public int getPrecision(String sField) throws ParserException;

}
