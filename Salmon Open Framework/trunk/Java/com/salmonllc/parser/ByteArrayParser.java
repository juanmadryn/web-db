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

import com.salmonllc.util.MessageLog;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * This object is used to describe a byte array representing column data in fixed locations. <BR> The Byte Array is a representation of a cobol copy book.
 */

public class ByteArrayParser
{
	/*
	    <P><PRE>
	    The Following Constants of ByteArrayParser are to be used
		by a developer whose specifying Column Definitions for
		an instance of ByteArrayParser. These constants represent the
		possibilities for the type parameter in 
		addColumnDefinition(int,String,int,int,int,boolean).
		
		TYPE_INT=1
		TYPE_STRING=2;
		TYPE_FLOAT=3;
		TYPE_FLOAT2=4;
		TYPE_DATE_CCYYMMDD=5;
		TYPE_DATE_MMYY=6;
		TYPE_DATE_YYMM=7;
		</PRE></P>
	*/
	public final static int TYPE_INT=1;
	public final static int TYPE_STRING=2;
	public final static int TYPE_FLOAT=3;
	public final static int TYPE_DOUBLE=4;
	public final static int TYPE_DATE_CCYYMMDD=5;
	public final static int TYPE_DATE_MMYY=6;
	public final static int TYPE_DATE_YYMM=7;
	public final static int TYPE_DATE_MMDDCCYY=8;
	public final static int TYPE_TIME_HHMMSS=9;
	public final static int TYPE_CHAR=10;
	public final static int TYPE_CUSTOM=0;
	/**Used for Date Formating.*/
	private final static char cDecimalSeparator='.';  
	/**Used for Date Formating.*/
	private final static char cDateSeparator='/';  
	/**Used for Number Formating.*/
	private final static char cNegativeSign='-';  
	/**Array to hold column lengths.*/
	private int[] DATALENGTHS;  
	/**Array to hold start indexes of the columns. */
	private int[] DATASTARTINDEXES;  
	/**Array to hold type definitons of the columns. */
	private int[] DATATYPES;  
	/**Array to indicate if columns are signed.*/
	private boolean[] DATASIGNED; 
	/**Array to hold names of columns.*/
	private String[] DATACOLUMNNAMES; 
	/**
	 * Array to hold instances of custom type objects.
	 * @uml.property  name="dATACUSTOMTYPES"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private CustomType[] DATACUSTOMTYPES; 
	/**Array to hold precision values for float datatype.*/
	private int[] DATAPRECISIONS; 
	/**Array to hold redefined values to indicate if column is a redefined column.*/
	private boolean[] DATAREDEFINED; 
	/**Array to hold message (Complete Row or Multiple Rows);*/
	private byte[] baOutputMessage;  
	/**Offset to start of next row in message.*/
	private int OFFSET=0;  
	private int CURRENTROWOFFSET=0;  
	/**Length of a row in Message.*/
	private int ROWLENGTH=0;  
	/**Indicates if message contains multiple rows.*/
	private boolean MULTIROW=false;  
	/**Indicates if Row Length was Calculated.*/
	private boolean CALCROWLENGTH=true;  
	/**Calculated Row Length based on Column Definitions.*/
	private int ACTUALROWLENGTH=0;  
	/**Indicates the current start index of a new column.*/
	private int STARTINDEX=0;
	/**Indicates the current column number of a new column.*/
	private int STARTFIELD=0;
	/**Vector of objects after message is parsed.*/
	private Vector _data=null;


	
	

	/**
	  Allocates a new <CODE>ByteArrayParser</CODE> object from a byte[] and a specified number of columns.
	
	  <P><PRE>
	  Constructor: ByteArrayParser(byte[] baOutputMessage,int iNumColumns)
	  Visibility: Public
	  </PRE></P>
	  @param baOutputMessage 		This is the message containing a row or multiple rows.
	  @param iNumColumns			Specifies the number of Columns in a row.									
	*/
	public ByteArrayParser(byte[] baOutputMessage,int iNumColumns) {
	  DATALENGTHS=new int[iNumColumns];
	  DATASTARTINDEXES=new int[iNumColumns];
	  DATATYPES=new int[iNumColumns];
	  DATASIGNED=new boolean[iNumColumns];
	  DATACOLUMNNAMES=new String[iNumColumns];
	  DATACUSTOMTYPES=new CustomType[iNumColumns];
	  DATAPRECISIONS=new int[iNumColumns];
	  DATAREDEFINED=new boolean[iNumColumns];
	  this.baOutputMessage=baOutputMessage;
      _data=new Vector(iNumColumns);
      for (int i=0;i<iNumColumns;i++) 
        _data.addElement(null);

	  //System.out.println("DATALENGTHS.length is "+DATALENGTHS.length);
	 }

    /**
	  Allocates a new <CODE>ByteArrayParser</CODE> object from a byte[] and arrays of the attributes describing the columns.

	  <P><PRE>
	  Constructor: ByteArrayParser(byte[] baOutputMessage,int[] datalengths,int[] datastartindexes,int[] datatypes,boolean[] datasigned,String[] datacolumnnames,CustomType[] datacustomtypes,int[] dataprecisions)
	  Visibility: Public
	  </PRE></P>
	  @param baOutputMessage 		This is the message containing a row or multiple rows.
	  @param datalengths			This is an array of the lengths for the columns.
      @param datastartindexes		This is an array of the start indexes for the columns.
      @param datatypes			    This is an array of the types for the columns.
      @param datasigned			    This is an array of the signed attribute for the columns.
      @param datacolumnnames		This is an array of the column names for the columns.
      @param datacustomtypes		This is an array of the custom type representations for the columns.
      @param dataprecisions			This is an array of the precisions for the columns.
	*/
	public ByteArrayParser(byte[] baOutputMessage,int[] datalengths,int[] datastartindexes,int[] datatypes,boolean[] datasigned,String[] datacolumnnames,CustomType[] datacustomtypes,int[] dataprecisions) {
	  this(baOutputMessage,datalengths,datastartindexes,datatypes,datasigned,datacolumnnames,datacustomtypes,dataprecisions,null);
	 }



    /**
	  Allocates a new <CODE>ByteArrayParser</CODE> object from a byte[] and arrays of the attributes describing the columns.

	  <P><PRE>
	  Constructor: ByteArrayParser(byte[] baOutputMessage,int[] datalengths,int[] datastartindexes,int[] datatypes,boolean[] datasigned,String[] datacolumnnames,CustomType[] datacustomtypes,int[] dataprecisions)
	  Visibility: Public
	  </PRE></P>
	  @param baOutputMessage 		This is the message containing a row or multiple rows.
	  @param datalengths			This is an array of the lengths for the columns.
      @param datastartindexes		This is an array of the start indexes for the columns.
      @param datatypes			    This is an array of the types for the columns.
      @param datasigned			    This is an array of the signed attribute for the columns.
      @param datacolumnnames		This is an array of the column names for the columns.
      @param datacustomtypes		This is an array of the custom type representations for the columns.
      @param dataprecisions			This is an array of the precisions for the columns.
      @param dataredefined			This is an array of the redefined attribute for the columns.
	*/
	public ByteArrayParser(byte[] baOutputMessage,int[] datalengths,int[] datastartindexes,int[] datatypes,boolean[] datasigned,String[] datacolumnnames,CustomType[] datacustomtypes,int[] dataprecisions,boolean[] dataredefined) {
	  DATALENGTHS=datalengths;
	  DATASTARTINDEXES=datastartindexes;
	  DATATYPES=datatypes;
	  DATASIGNED=datasigned;
	  DATACOLUMNNAMES=datacolumnnames;
	  DATACUSTOMTYPES=datacustomtypes;
	  DATAPRECISIONS=dataprecisions;
	  if (dataredefined==null)
	  	DATAREDEFINED=new boolean[DATALENGTHS.length];
	  else   
	    DATAREDEFINED=dataredefined;
	  this.baOutputMessage=baOutputMessage;
	  ACTUALROWLENGTH=0;
      _data=new Vector(DATALENGTHS.length);
	  for(int i=0;i<DATALENGTHS.length;i++) {
	    if (!DATAREDEFINED[i])
	      ACTUALROWLENGTH+=DATALENGTHS[i];
        _data.addElement(null);
	   }
	  if (CALCROWLENGTH || !MULTIROW)
	    ROWLENGTH=ACTUALROWLENGTH;  
	 }


    /**
	  To return an array of lengths of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataLengths()
	  Visibility: Public
	  Purpose:	To return an array of lengths of the columns.
	  </PRE></P>
	  @return 			Returns the length of the columns in an array.

	*/
	public int[] getDataLengths() {
	  return DATALENGTHS;
	}
	
    /**
	  To return an array of start indexes of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataStartIndexes()
	  Visibility: Public
	  Purpose:	To return an array of start indexes of the columns.
	  </PRE></P>
	  @return 			Returns the start index of the columns in an array.

	*/
	public int[] getDataStartIndexes() {
	  return DATASTARTINDEXES;
	}
	
    /**
	  To return an array of types of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataTypes()
	  Visibility: Public
	  Purpose:	To return an array of types of the columns.
	  </PRE></P>
	  @return 			Returns the type of the columns in an array.

	*/
	public int[] getDataTypes() {
	  return DATATYPES;
	}
	
    /**
	  To return an array of signed attributes of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataSigned()
	  Visibility: Public
	  Purpose:	To return an array of signed attribute of the columns.
	  </PRE></P>
	  @return 			Returns the signed attribute of the columns in an array.

	*/
	public boolean[] getDataSigned() {
	  return DATASIGNED;
	}
	
    /**
	  To return an array of names of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataColumnNames()
	  Visibility: Public
	  Purpose:	To return an array of names of the columns.
	  </PRE></P>
	  @return 			Returns the name of the columns in an array.

	*/
	public String[] getDataColumnNames() {
	  return DATACOLUMNNAMES;
	}
	
    /**
	  To return an array of the custom type representation of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataCustomTypes()
	  Visibility: Public
	  Purpose:	To return an array of custom types of the columns.
	  </PRE></P>
	  @return 			Returns the custom type of the columns in an array.

	*/
	public CustomType[] getDataCustomTypes() {
	  return DATACUSTOMTYPES;
	}
	
    /**
	  To return an array of precisions of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getDataPrecisions()
	  Visibility: Public
	  Purpose:	To return an array of precisions of the columns.
	  </PRE></P>
	  @return 			Returns the precisions of the columns in an array.

	*/
	public int[] getDataPrecisions() {
	  return DATAPRECISIONS;
	}


    /**
	  To set the data lengths of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataLengths(int[] datalengths)
	  Visibility: Public
	  Purpose:	To set the lengths of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data length of the columns in a ByteArrayParser.
	*/
	public void setDataLengths(int[] datalengths) {
	  DATALENGTHS=datalengths;
	}
	
    /**
	  To set the data start index of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataStartIndexes(int[] datastartindexes)
	  Visibility: Public
	  Purpose:	To set the start indexes of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data start index of the columns in a ByteArrayParser.
	*/
	public void setDataStartIndexes(int[] datastartindexes) {
	  DATASTARTINDEXES=datastartindexes;
	}
	
    /**
	  To set the data types of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataTypes(int[] datatypes)
	  Visibility: Public
	  Purpose:	To set the types of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data type of the columns in a ByteArrayParser.
	*/
	public void setDataTypes(int[] datatypes) {
	  DATATYPES=datatypes;
	}
	
    /**
	  To set the data signed attributes of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataSigned(boolean[] datasigned)
	  Visibility: Public
	  Purpose:	To set the signed attributes of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data signed attribute of the columns in a ByteArrayParser.
	*/
	public void setDataSigned(boolean[] datasigned) {
	  DATASIGNED=datasigned;
	}
	
    /**
	  To set the column names of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataColumnNames(String[] datacolumnnames)
	  Visibility: Public
	  Purpose:	To set the names of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the column name of the columns in a ByteArrayParser.
	*/
	public void setDataColumnNames(String[] datacolumnnames) {
	  DATACOLUMNNAMES=datacolumnnames;
	}
	
    /**
	  To set the data custom types of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataCustomTypes(CustomType[] datacustomtypes)
	  Visibility: Public
	  Purpose:	To set the custom types of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data custom type of the columns in a ByteArrayParser.
	*/
	public void setDataCustomTypes(CustomType[] datacustomtypes) {
	  DATACUSTOMTYPES=datacustomtypes;
	}
	
    /**
	  To set the data precisions of the columns in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void setDataPrecisions(int[] dataprecisions)
	  Visibility: Public
	  Purpose:	To set the precisions of the columns in the ByteArrayParser.
	  </PRE></P>
	  @param datalengths    Sets the data precision of the columns in a ByteArrayParser.
	*/
	public void setDataPrecisions(int[] dataprecisions) {
	  DATAPRECISIONS=dataprecisions;
	}


	/**
	  To set the <CODE>ByteArrayParser</CODE> to multirow.
	
	  <P><PRE>
	  Method: public void setMultiRow(int iRowLength) throws Exception
	  Visibility: Public
	  Purpose:	To indicate message contains multiple rows and optionally the
				length of a row. An iRowLength of zero indicates to calculate
				Column Definitions.
	  </PRE></P>
	  @param iRowLength			 	Specifies the length of a row. If zero Row Length Automatically Calculated from Column Definitions.
	  @exception	ParserException		<P><PRE>Throws two possible Exceptions under these Circumstances.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									Circumstance 2: If Specified Row Length does not divide Evenly into Length of Message.
									</PRE></P>
	*/
	
	public void setMultiRow(int iRowLength) throws ParserException
	{
	  MULTIROW=true;
	  ROWLENGTH=iRowLength;
	  if (ROWLENGTH!=0)
	    CALCROWLENGTH=false;
	  else
	    CALCROWLENGTH=true;
	  ACTUALROWLENGTH=0;
	  for(int i=0;i<DATALENGTHS.length;i++) {
	    if (!DATAREDEFINED[i])
	    	ACTUALROWLENGTH+=DATALENGTHS[i];
	  }
	  if (CALCROWLENGTH)
	    ROWLENGTH=ACTUALROWLENGTH;  
	  if (ACTUALROWLENGTH>ROWLENGTH)
	    throw new ParserException("Actual Row Length has Exceeded Specified Row Length "+ROWLENGTH);
	  if (ROWLENGTH!=0 && !CALCROWLENGTH && baOutputMessage.length%ROWLENGTH!=0)
	    throw new ParserException("Specified Row Length does not divide evenly into Message Length"+baOutputMessage.length);
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(int iColumnNumber,String sColumnName,int iType,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param iColumnNumber		 	Specifies Column position in a Row. 
	  @param sColumnName		  	Specifies Name of Column.
	  @param iType					Specifies the Format Type of Column. Valid values range 1-7. Public Constants exist for these values.
	  @param iStartIndex			Specifies the start index of a Column in Row portion of the message. 
	  @param iLength				Specifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	  
	*/
	
	public void addColumnDefinition(int iColumnNumber,String sColumnName,int iType,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 int iTempStartIndex=iStartIndex;
	 int iTempColumnNumber=iColumnNumber;
	 if (iTempColumnNumber>=DATALENGTHS.length || iTempColumnNumber<0)
	   throw new ArrayIndexOutOfBoundsException("Column "+iTempColumnNumber+": "+sColumnName+" is out of Bounds");
	 if (iTempStartIndex+iLength>baOutputMessage.length || iTempStartIndex<0)
	   throw new ArrayIndexOutOfBoundsException("The sum of Start Index "+iTempStartIndex+" and Length "+iLength+" is out of Bounds for Column "+iTempColumnNumber+": "+sColumnName);
	 STARTINDEX=iStartIndex;
	 STARTFIELD=iColumnNumber;
	 DATALENGTHS[iTempColumnNumber]=iLength;
	 DATASTARTINDEXES[iTempColumnNumber]=iTempStartIndex;
	 DATATYPES[iTempColumnNumber]=iType;
	 if (iType==TYPE_FLOAT || iType==TYPE_DOUBLE)
	   DATAPRECISIONS[iTempColumnNumber]=2;
	 DATASIGNED[iTempColumnNumber]=bSigned;
	 DATACOLUMNNAMES[iTempColumnNumber]=sColumnName;
	 STARTINDEX+=iLength;
	 STARTFIELD++;
	 ACTUALROWLENGTH=0;
	 for(int i=0;i<DATALENGTHS.length;i++) {
	   if (!DATAREDEFINED[i])
	   		ACTUALROWLENGTH+=DATALENGTHS[i];
	 }
	 if (CALCROWLENGTH || !MULTIROW)
	   ROWLENGTH=ACTUALROWLENGTH;  
	 if (ACTUALROWLENGTH>ROWLENGTH)
	   throw new ParserException("Actual Row Length has Exceeded Specified Row Length "+ROWLENGTH+" for Column "+iTempColumnNumber+": "+sColumnName);
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(int iColumnNumber,String sColumnName,CustomType ctType,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param iColumnNumber		 	Specifies Column position in a Row. If Negative	it uses last known column position + 1;
	  @param sColumnName		    Specifies Name of Column.
	  @param ctType					Specifies an instance of an object that implements CustomType Format of Column.
	  @param iStartIndex			Specifies the start index of a Column in Row portion of the message.										
	  @param iLength				Specifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	*/
	
	public void addColumnDefinition(int iColumnNumber,String sColumnName,CustomType ctType,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 addColumnDefinition(iColumnNumber,sColumnName,TYPE_STRING,iStartIndex,iLength,bSigned);
	 DATATYPES[iColumnNumber]=TYPE_CUSTOM;
	 DATACUSTOMTYPES[iColumnNumber]=ctType;
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(int iColumnNumber,String sColumnName,int iType,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param iColumnNumber		 	Specifies Column position in a Row. 
	  @param sColumnName		    Specifies Name of Column.
	  @param iType					Specifies the Format Type of Column. Valid values range 1-7. Public Constants exist for these values.
	  @param iPrecision				Specifies the precison of the Float date.
	  @param iStartIndex			Specifies the start index of a Column in Row portion of the message. 
	  @param iLength				Specifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	*/
	
	public void addColumnDefinition(int iColumnNumber,String sColumnName,int iType,int iPrecision,int iStartIndex,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 addColumnDefinition(iColumnNumber,sColumnName,iType,iStartIndex,iLength,bSigned);
	 DATAPRECISIONS[iColumnNumber]=iPrecision;
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(String sColumnName,int iType,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param sColumnName		    Specifies Name of Column.
	  @param iType					Specifies the Format Type of Column.Valid values range 1-7. Public Constants exist for these values.
	  @param iLength				Specifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	*/
	
	public void addColumnDefinition(String sColumnName,int iType,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 int iTempStartIndex=STARTINDEX;
	 int iTempColumnNumber=STARTFIELD;
	 if (iTempColumnNumber>=DATALENGTHS.length || iTempColumnNumber<0)
	   throw new ArrayIndexOutOfBoundsException("Column "+iTempColumnNumber+": "+sColumnName+" is out of Bounds");
	 if (iTempStartIndex+iLength>baOutputMessage.length || iTempStartIndex<0)
	   throw new ArrayIndexOutOfBoundsException("The sum of Start Index "+iTempStartIndex+" and Length "+iLength+" is out of Bounds for Column "+iTempColumnNumber+": "+sColumnName);
	 DATALENGTHS[iTempColumnNumber]=iLength;
	 DATASTARTINDEXES[iTempColumnNumber]=iTempStartIndex;
	 DATATYPES[iTempColumnNumber]=iType;
	 if (iType==TYPE_FLOAT || iType==TYPE_DOUBLE)
	   DATAPRECISIONS[iTempColumnNumber]=2;
	 DATASIGNED[iTempColumnNumber]=bSigned;
	 DATACOLUMNNAMES[iTempColumnNumber]=sColumnName;
	 STARTINDEX+=iLength;
	 STARTFIELD++;
	 ACTUALROWLENGTH=0;
	 for(int i=0;i<DATALENGTHS.length;i++) {
	   if (!DATAREDEFINED[i])
		   ACTUALROWLENGTH+=DATALENGTHS[i];
	 }
	 if (CALCROWLENGTH || !MULTIROW)
	   ROWLENGTH=ACTUALROWLENGTH;  
	 if (ACTUALROWLENGTH>ROWLENGTH)
	   throw new ParserException("Actual Row Length has Exceeded Specified Row Length "+ROWLENGTH+" for Column "+iTempColumnNumber+": "+sColumnName);
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(String sColumnName,CustomType ctType,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param iColumnNumber		 	Specifies Column position in a Row. If Negative it uses last known column position + 1;
	  @param sColumnName		    Specifies Name of Column.
	  @param ctType					Specifies an instance of an object that implements CustomType Format of Column.
	  @param iStartIndex			Specifies the start index of a Column in Row portion of the message.										
	  @param iLength				Specifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	*/
	
	public void addColumnDefinition(String sColumnName,CustomType ctType,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 int iTempColumnNumber=STARTFIELD;
	 addColumnDefinition(sColumnName,TYPE_STRING,iLength,bSigned);
	 DATATYPES[iTempColumnNumber]=TYPE_CUSTOM;
	 DATACUSTOMTYPES[iTempColumnNumber]=ctType;
	}
	
	/**
	  To add a column definition to a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public void addColumnDefinition(String sColumnName,int iType,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,Exception
	  Visibility: Public
	  Purpose:	To specify a column definition to be in used parsing the data 
				contained in message.
	  </PRE></P>
	  @param sColumnName		   	Specifies Name of Column.
	  @param iType				   	Specifies the Format Type of Column. Valid values range 1-7. Public Constants exist for these values.
	  @param iPrecision			   	Specifies the precison of the Float date.
	  @param iLength			   	Speifies the length of a Column in a Row portion of the message.
	  @param bSigned				Indicates in Columns data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws two possible Exceptions under these Circumstances.
													Circumstance 1: If iColumnNumber is greater than specified number of Columns.
													Circumstance 2: If iStartIndex plus iLength is greater than length of Message.
													</PRE></P>
	  @exception	Exception		<P><PRE>Throws one possible Exception under this Circumstance.
									Circumstance 1: If Calculated Row Length is Greater than Specified Row Length.
									</PRE></P>
	*/
	
	public void addColumnDefinition(String sColumnName,int iType,int iPrecision,int iLength,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	{
	 int iTempColumnNumber=STARTFIELD;
	 addColumnDefinition(sColumnName,iType,iLength,bSigned);
	 DATAPRECISIONS[iTempColumnNumber]=iPrecision;
	}
	
	
	/**
	  To perofrm the parsing of a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public Vector doParse() throws Exception
	  Visibility: Public
	  Purpose:	To return a row in the message, each call returns the next row
				in message if more than one.
	  </PRE></P>			
	  @return 			Returns A Vector containing the data of one row in the message. Null indicates last row reached.													
	  @exception	Exception	<P><PRE>Throws two possible Exceptions under these Circumstances.
								Circumstance 1: If no Column Definitions have been defined. 
								Circumstance 2: If Specified Row Length does not divide Evenly into Length of Message.
								</PRE></P>
	*/
	
	public Vector doParse() throws ParserException{
	   if (ROWLENGTH==0)
	     throw new ParserException("No Column Definition Specified");
	   if (ROWLENGTH!=0 && MULTIROW && CALCROWLENGTH && baOutputMessage.length%ROWLENGTH!=0)
	     throw new ParserException("Calculated Row Length does not divide evenly into Message Length"+baOutputMessage.length);
	   if (OFFSET>=baOutputMessage.length)
	     return null;
      for (int i=0;i<_data.size();i++) 
        _data.setElementAt(null,i);	
	   /*File f=null;
	   FileOutputStream fs=null;
	   PrintStream ps=null;
	   try {
	     f=new File("C:\\LOGS\\"+System.currentTimeMillis()+".PRS");
	     fs=new FileOutputStream(f);  
	     ps=new PrintStream(fs);
	    }
	   catch (Exception e){
		  Debugger.println("File IO Exception: "+e);
		 }*/
	   int iElement=0;
	   StringBuffer sbData=new StringBuffer();
	   try { 
	     for (iElement=0;iElement<DATALENGTHS.length;iElement++) {
	        _data.setElementAt(getParsedObject(iElement),iElement);
	      }
	     nextOffset();
	    }
	  catch (Exception e) {
	    MessageLog.writeDebugMessage("Exception Occured on "+DATACOLUMNNAMES[iElement]+" field from this '"+sbData+"' : "+e,this);
	    //ps.println("Exception Occured on "+iElement+" field: "+e);
	    MessageLog.writeDebugMessage(iElement+" field DataType: "+DATATYPES[iElement],this);
	    //ps.println(iElement+" field DataType: "+DATATYPES[iElement]);
	    MessageLog.writeDebugMessage(iElement+" field Length: "+DATALENGTHS[iElement],this);
	    //ps.println(iElement+" field Length: "+DATALENGTHS[iElement]);
	    MessageLog.writeDebugMessage(iElement+" field Signed: "+DATASIGNED[iElement],this);
	    //ps.println(iElement+" field Signed: "+DATASIGNED[iElement]);
	   }
	  //Debugger.println("Final FIeld Number is "+iElement);
	  //ps.println("Final FIeld Number is "+iElement);
	  //ps.close();
	  /*try {
	    fs.close();
	   }
	  catch (Exception e) {
	    Debugger.println("File IO Exception: "+e);
	   }*/	
	  return _data;
	}
	
	public void nextOffset() {
	     CURRENTROWOFFSET=OFFSET;
	     if (MULTIROW)
	       OFFSET=OFFSET+ROWLENGTH;
	     else
	       OFFSET=0;
	}
	
	public void nextRow() {
      	for (int i=0;i<_data.size();i++) 
        	_data.setElementAt(null,i);	
	     if (MULTIROW) {
	       CURRENTROWOFFSET=CURRENTROWOFFSET+ROWLENGTH;
		}
	     else {
	       CURRENTROWOFFSET=0;
		}
	}
	
	private Object getParsedObject(int iElement) {
		return getParsedObject(iElement,OFFSET);
	}	
	private Object getParsedObject(int iElement,int OFFSET) {
	       StringBuffer sbData=new StringBuffer(new String(baOutputMessage,OFFSET+DATASTARTINDEXES[iElement],DATALENGTHS[iElement]));
	       StringBuffer sbDebug=new StringBuffer("sData for ");
	       sbDebug.append(DATACOLUMNNAMES[iElement]);
	       sbDebug.append(" is ");
	       sbDebug.append(sbData);
		   MessageLog.writeDebugMessage(sbDebug.toString(),this);
		   //ps.println("sData for "+DATACOLUMNNAMES[iElement]+" is "+sData);
	       if (DATASIGNED[iElement]) {
		 	 //Debugger.println("Sign Conversion");
			 //ps.println("Sign Conversion");
	         char cSignedChar=sbData.charAt(sbData.length()-1);
			 String sUnsignedData=sbData.substring(0,sbData.length()-1);
			 switch (cSignedChar) {
			   case '{': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('0');
				 break;
			   case 'A': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('1');
				 break;
			   case 'B': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('2');
				 break;
			   case 'C': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('3');
				 break;
			   case 'D': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('4');
			     break;
			   case 'E': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('5');
			     break;
			   case 'F': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('6');
			     break;
			   case 'G': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('7');
			     break;
			   case 'H': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('8');
				 break;
			   case 'I': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.append('9');
				 break;
			   case '}': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('0');
				 break;
			   case 'J': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('1');
				 break;
			   case 'K': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('2');
				 break;
			   case 'L': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('3');
				 break;
			   case 'M': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('4');
				 break;
			   case 'N': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('5');
				 break;
			   case 'O': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('6');
				 break;
			   case 'P': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('7');
			     break;
			   case 'Q': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('8');
			     break;
			   case 'R': sbData.replace(0,sbData.length(),sUnsignedData);
						 sbData.insert(0,cNegativeSign);
						 sbData.append('9');
			     break;
			  }
			 sbDebug.replace(0,sbDebug.length(),"Sign Converted sData is ");
			 sbDebug.append(sbData);
		     MessageLog.writeDebugMessage(sbDebug.toString(),this);
			 //ps.println("Sign Converted sData is "+sData);
			 if (sbData != null) {
			   //ps.println("Sign Converted sData is "+sData);
			  }
			 else{
			   //ps.println("Sign Converted sData is null");
			   sbData = sbData.replace(0,sbData.length(),"+0");
			  }
			}
		   Object oReturn;
		   switch (DATATYPES[iElement]) {
		     case TYPE_CUSTOM:
//			   _data.addElement(DATACUSTOMTYPES[iElement].getDataValue(sbData.toString()));
               oReturn=DATACUSTOMTYPES[iElement].getDataValue(sbData.toString());
	           break;	       
	         case TYPE_INT:
	           sbData.replace(0,sbData.length(),sbData.toString().trim());
	           try {
	              new Float(sbData.toString());
	            }
	           catch (NumberFormatException e) {
	             sbData.replace(0,sbData.length(),"0");
	            }
//		       _data.addElement(new Integer(sbData.toString()));
               oReturn= new Integer(sbData.toString());
		       break;
			 case TYPE_STRING:
//			   _data.addElement(sbData.toString());
               oReturn= sbData.toString();
			   break;
			 case TYPE_CHAR:
//			   _data.addElement(new Character(sbData.charAt(0)));
			   oReturn= new Character(sbData.charAt(0));
			   break;
			 case TYPE_FLOAT: {
			   int iPrecision=DATAPRECISIONS[iElement];
	           try {
	              new Float(sbData.toString().trim());
	            }
	           catch (NumberFormatException e) {
	             sbData.replace(0,sbData.length(),padString("",iPrecision+1,'0'));
	            }
	           if (sbData.toString().trim().length()<(iPrecision+1))
	             sbData.replace(0,sbData.length(),padString("",iPrecision+1,'0'));
	           
	           sbData.replace(0,sbData.length(),sbData.toString().trim());
			   String sIntPart=sbData.substring(0,sbData.length()-iPrecision);
			   sbDebug.replace(0,sbDebug.length(),"sIntPart=");
			   sbDebug.append(sIntPart);
			   MessageLog.writeDebugMessage(sbDebug.toString(),this);
			   String sDecPart=sbData.substring(sbData.length()-iPrecision);
			   sbDebug.replace(0,sbDebug.length(),"sDecPart=");
			   sbDebug.append(sDecPart);
			   MessageLog.writeDebugMessage(sbDebug.toString(),this);
			   StringBuffer sbFloat=new StringBuffer(sIntPart);
			   sbFloat.append(cDecimalSeparator);
			   sbFloat.append(sDecPart);
			   sbDebug.replace(0,sbDebug.length(),"sbFloat=");
			   sbDebug.append(sbFloat.toString());
			   MessageLog.writeDebugMessage(sbDebug.toString(),this);
	           Float fFloat=new Float(sbFloat.toString());
			   sbDebug.replace(0,sbDebug.length(),"fFloat=");
			   sbDebug.append(fFloat.floatValue());
			   MessageLog.writeDebugMessage(sbDebug.toString(),this);
//	    	   _data.addElement(fFloat);
			   oReturn= fFloat;
			   break;
			  }
			 case TYPE_DOUBLE: {
			   int iPrecision=DATAPRECISIONS[iElement];
	           try {
	              new Double(sbData.toString().trim());
	            }
	           catch (NumberFormatException e) {
	             sbData.replace(0,sbData.length(),padString("",iPrecision+1,'0'));
	            }
	           if (sbData.toString().trim().length()<(iPrecision+1))
	             sbData.replace(0,sbData.length(),padString("",iPrecision+1,'0'));
	           
	           sbData.replace(0,sbData.length(),sbData.toString().trim());
			   String sIntPart=sbData.substring(0,sbData.length()-iPrecision);
			   String sDecPart=sbData.substring(sbData.length()-iPrecision);
			   StringBuffer sbDouble=new StringBuffer(sIntPart);
			   sbDouble.append(cDecimalSeparator);
			   sbDouble.append(sDecPart);
	           Double dDouble=new Double(sbDouble.toString());
//	    	   _data.addElement(dDouble);
			   oReturn= dDouble;
			   break;
			  }
			 case TYPE_DATE_CCYYMMDD: {
	           int iYear;
			   int iMonth;
			   int iDay;
	           try {
	             iYear=(new Integer(sbData.substring(0,4))).intValue();
	             iMonth=(new Integer(sbData.substring(4,6))).intValue();
	 		     iDay=(new Integer(sbData.substring(6))).intValue();
	            }
	           catch (NumberFormatException e) {
	             iYear=1950;
	             iMonth=1;
	 		     iDay=1;
	            }      
	           catch (StringIndexOutOfBoundsException e) {
	             iYear=1950;
	             iMonth=1;
	 		     iDay=1;
	            }
	           if (iDay==0)
	             iDay=1;      
	           if (!sbData.toString().trim().equals("") && !sbData.toString().trim().equals("00000000")) {
//			     _data.addElement(new java.sql.Date(iYear-1900,iMonth-1,iDay));
                 oReturn= new java.sql.Date(iYear-1900,iMonth-1,iDay);
                }
			   else {
			     //_data.addElement(null);
			     oReturn= null;
			    }
			   break;
			  }
			 case TYPE_DATE_MMYY: {
			   StringBuffer sbDate=new StringBuffer(sbData.substring(0,2));
			   sbDate.append(cDateSeparator);
			   sbDate.append(sbData.substring(2));
//			   _data.addElement(sbDate.toString());
			   oReturn= sbDate.toString();
			   break; 
			  }
			 case TYPE_DATE_YYMM: {
			   StringBuffer sbDate=new StringBuffer(sbData.substring(0,2));
			   sbDate.append(cDateSeparator);
			   sbDate.append(sbData.substring(2));
//			   _data.addElement(sbDate.toString());
			   oReturn= sbDate.toString();
			   break; 
			  }
			 default:
//			   _data.addElement(sbData.toString());
			   oReturn= sbData.toString();
	        }
	      return oReturn;
	  }
	
	
	/**
	  To return the number of columns in a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public int getColumnCount()
	  Visibility: Public
	  Purpose:	To return the number of column in a row.
	  </PRE></P>
	  @return 			Returns the number of columns in a row.
	
	*/
	
	public int getColumnCount() {
	  return DATALENGTHS.length;
	 }
	
	/**
	  To get the column name of a specified column number in a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public String getColumnName(int iColumn)
	  Visibility: Public
	  Purpose:	To return the name of a column.
	  </PRE></P>
	  @param iColumn			Specifies the Column Number.
	  @return					Returns the name assigned to a specific column.
	*/
	
	public String getColumnName(int iColumn) {
	  return DATACOLUMNNAMES[iColumn];
	 }
	
	/**
	  To get the column type of a specified column in a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public int getSqlColumnType(int iColumn)
	  Visibility: Public
	  Purpose:	To return the sql type of a specified column.
	  </PRE></P>
	  @param iColumn		 	Specifies the Column Number.
	  @return					Returns the sql type of the specified column.
	*/
	
	public int getSqlColumnType(int iColumn) {
	  int ret=0;
	  switch (DATATYPES[iColumn]) {
	    case TYPE_CUSTOM:
	      ret=java.sql.Types.VARCHAR;
	      break;
	    case TYPE_INT:
	      ret=java.sql.Types.INTEGER;
	      break;
	    case TYPE_STRING:
	      ret=java.sql.Types.VARCHAR;
	      break;
	    case TYPE_CHAR:
	      ret=java.sql.Types.CHAR;
	      break;
	    case TYPE_FLOAT:
	      ret=java.sql.Types.FLOAT;
	      break;
	    case TYPE_DOUBLE:
	      ret=java.sql.Types.DOUBLE;
	      break;
	    case TYPE_DATE_CCYYMMDD:
	      ret=java.sql.Types.DATE;
	      break;
	    case TYPE_DATE_MMYY:
	      ret=java.sql.Types.VARCHAR;
	      break;
	    case TYPE_DATE_YYMM:
	      ret=java.sql.Types.VARCHAR;
	      break;
	    case TYPE_DATE_MMDDCCYY:
	      ret=java.sql.Types.DATE;
	      break;
	    case TYPE_TIME_HHMMSS:
	      ret=java.sql.Types.TIME;
	      break;
	   }
	   return ret;
	 }

    /**
	  To get the column type of a specified column in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getColumnType(int iColumn)
	  Visibility: Public
	  Purpose:	To return the column type of a specified column.
	  </PRE></P>
	  @param iColumn		 	Specifies the Column Number.
	  @return					Returns the sql type of the specified column.
	*/

	public int getColumnType(int iColumn) {
	  int ret=DATATYPES[iColumn];
      return ret;
	 }


	/**
	  To get a column precision of a specified column in a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public int getPrecision(int iColumn)
	  Visibility: Public
	  Purpose:	To return the precision of a specified column.
	  </PRE></P>
	  @param iColumn		 	Specifies the Column Number.
	  @return 					Returns the precision of the specified column.
	*/
	
	public int getPrecision(int iColumn) {
	  int precision=DATAPRECISIONS[iColumn];
	   return precision;
	 }
	

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
	
	public int getPrecision(String sProperty) throws ParserException {
	  int iColumn=-1;
//	  Debugger.println("Property is "+sProperty);
	  for (int i=0;i<getColumnCount();i++) {
//	    Debugger.println(i+" Column Name is "+getColumnName(i));
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
	  int precision=getPrecision(iColumn);
	   return precision;
	 }


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
	
	public String getValue(String sProperty) throws ParserException {
	  int iColumn=-1;
//	  Debugger.println("Property is "+sProperty);
	  for (int i=0;i<getColumnCount();i++) {
//	    Debugger.println(i+" Column Name is "+getColumnName(i));
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
//	  Debugger.println("Property is "+sProperty);
//	  Debugger.println("OFFSET is "+OFFSET);
//	  Debugger.println("MULTIROW is "+MULTIROW);
//	  Debugger.println("ROWLENGTH is "+ROWLENGTH);
//	  Debugger.println("START INDEX is "+DATASTARTINDEXES[iColumn]);
//	  Debugger.println("Length is "+DATALENGTHS[iColumn]);
	  String sData;
	  if (MULTIROW) 
	    sData=new String(baOutputMessage,CURRENTROWOFFSET+DATASTARTINDEXES[iColumn],DATALENGTHS[iColumn]);
	  else
	    sData=new String(baOutputMessage,DATASTARTINDEXES[iColumn],DATALENGTHS[iColumn]);
	  return sData;
	}
	
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
	public void setValue(String sProperty,String sValue) throws ParserException {
	  int iColumn=-1;
	  for (int i=0;i<getColumnCount();i++) {
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
	  if (sValue.length()!=DATALENGTHS[iColumn])
	    throw new ParserException("Value "+sValue+" Passed must be "+DATALENGTHS[iColumn]+" Characters in length.");
	  if (MULTIROW)
	    fillMessage(sValue,CURRENTROWOFFSET+DATASTARTINDEXES[iColumn],OFFSET-ROWLENGTH+DATASTARTINDEXES[iColumn]+DATALENGTHS[iColumn]);
	  else
	    fillMessage(sValue,DATASTARTINDEXES[iColumn],DATASTARTINDEXES[iColumn]+DATALENGTHS[iColumn]);
	}
	
	public int getLength(String sProperty) throws ParserException {
	  int iColumn=-1;
	  for (int i=0;i<getColumnCount();i++) {
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
      return DATALENGTHS[iColumn];
	}


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
	
	public Object getObject(String sProperty) throws ParserException {
	  int iColumn=-1;
	  for (int i=0;i<getColumnCount();i++) {
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
	  if (_data.elementAt(iColumn)==null)
	    _data.setElementAt(getParsedObject(iColumn,CURRENTROWOFFSET),iColumn);
	  return _data.elementAt(iColumn);
	}



    /**
	  To get an object representation of a column from a specified column in a  <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public Object getObject(int iColumn) throws Exception
	  Visibility: Public
	  Purpose:	To get the object value of a column from a parsed object.
	  </PRE></P>
	  @param iColumn			 	Specifies the index of the that you wish to get the value of.
	  @return 						Returns the value of the specified column as an Object.
	  @exception	Exception		<P><PRE>Throws two possible Exception under this Circumstance.
									Circumstance: If doParse method was never called.
									Circumstance: If the column index specified is out of bounds.
									</PRE></P>
	*/
	public Object getObject(int iColumn) throws ParserException {
	  if (_data.elementAt(iColumn)==null)
	    _data.setElementAt(getParsedObject(iColumn,CURRENTROWOFFSET),iColumn);
	  return _data.elementAt(iColumn);
	}


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
	public void setObject(String sProperty,Object oValue) throws ParserException {
	  if (oValue==null)
	    throw new ParserException("Not allowed to set "+sProperty+" to null.");
	  int iColumn=-1;
	  for (int i=0;i<getColumnCount();i++) {
	    if (getColumnName(i).equals(sProperty)) {
	      iColumn=i;
	      break;
	     }
	   }
	  if (iColumn==-1)
	    throw new ParserException("Invalid Column Name "+sProperty);
	  int iColumnType=DATATYPES[iColumn];
	  String sValueAsBytes="";
	  switch (iColumnType) {
	    case TYPE_DATE_YYMM:
	    case TYPE_DATE_MMYY:
	    case TYPE_STRING: {
	      if (!oValue.getClass().getName().equals("java.lang.String"))
	        throw new ParserException("Object passed must be a java.lang.String for Column "+sProperty+".");
	      if (((String)oValue).length()!=DATALENGTHS[iColumn])
	        throw new ParserException("Value "+(String)oValue+" Passed must be "+DATALENGTHS[iColumn]+" Characters in length for Column "+sProperty+".");
	      _data.setElementAt(oValue,iColumn);
	      setValue(sProperty,(String)oValue);
	      break;
	     }
	    case TYPE_CUSTOM: {
	      if ((((CustomType)DATACUSTOMTYPES[iColumn]).toString(oValue)).length()!=DATALENGTHS[iColumn])
	        throw new ParserException("Value "+((CustomType)DATACUSTOMTYPES[iColumn]).toString(oValue)+" Passed must be "+DATALENGTHS[iColumn]+" Characters in length for Column "+sProperty+" .");
	      _data.setElementAt(oValue,iColumn);
	      setValue(sProperty,((CustomType)DATACUSTOMTYPES[iColumn]).toString(oValue));
	      break;
	     }
	    case TYPE_CHAR: {
	      if (!oValue.getClass().getName().equals("java.lang.Character"))
	        throw new ParserException("Object passed must be a java.lang.Character for Column "+sProperty+".");
	      String sChar=oValue.toString();
	      if (DATALENGTHS[iColumn]>1)
	        sChar=padString(sChar,DATALENGTHS[iColumn],' ');
	      _data.setElementAt(oValue,iColumn);
	      setValue(sProperty,sChar);
	      break;
	     }
	    case TYPE_INT: {
	      if (!oValue.getClass().getName().equals("java.lang.Integer"))
	        throw new ParserException("Object passed must be a java.lang.Integer for Column "+sProperty+".");
	      boolean bSigned=DATASIGNED[iColumn];
	      int iLength=DATALENGTHS[iColumn];
	      int iMax=0;
	      int iMin=0;
	      double dMax=Math.pow((double)10,(double)iLength);
	      iMax=((int)dMax)-1;
	      String sValue=""+((Integer)oValue).intValue();
	      if (bSigned) {
	        iMin=(-iMax);
	        String sUnsignedData="";
	        boolean bNegative=false;
	        if (sValue.length()>=2) {
	          sUnsignedData=sValue.substring(0,sValue.length()-1);
	          if (sUnsignedData.charAt(0)=='-') {
	            sUnsignedData=sUnsignedData.substring(1);
	            bNegative=true;
	           }
	         }
	        char cSignedChar=sValue.charAt(sValue.length()-1);        
	        if (!bNegative) {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'{';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'A';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'B';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'C';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'D';
			       break;
			     case '5': sValueAsBytes=sUnsignedData+'E';
			       break;
			     case '6': sValueAsBytes=sUnsignedData+'F';
			       break;
			     case '7': sValueAsBytes=sUnsignedData+'G';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'H';
				   break;
			     case '9': sValueAsBytes=sUnsignedData+'I';
				   break;
			    }
			  }
			 else {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'}';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'J';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'K';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'L';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'M';
				   break;
			     case '5': sValueAsBytes=sUnsignedData+'N';
				   break;
			     case '6': sValueAsBytes=sUnsignedData+'O';
				   break;
			     case '7': sValueAsBytes=sUnsignedData+'P';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'Q';
			       break;
			     case '9': sValueAsBytes=sUnsignedData+'R';
			       break;
			    }
			  }
	       }
	      else
	        sValueAsBytes=sValue;
	      if (((Integer)oValue).intValue()<iMin || ((Integer)oValue).intValue()>iMax)
	        throw new ParserException("Object "+oValue+" passed must have a value between "+iMin+" & "+iMax+" for Column "+sProperty+".");
	      _data.setElementAt(oValue,iColumn);
	      setValue(sProperty,padStringInFront(sValueAsBytes,iLength,'0'));
	      break;
	     }
	    case TYPE_FLOAT: {
	      if (!oValue.getClass().getName().equals("java.lang.Float"))
	        throw new ParserException("Object passed must be a java.lang.Float for Column "+sProperty+".");
	      boolean bSigned=DATASIGNED[iColumn];
	      int iPrecision=DATAPRECISIONS[iColumn];
	      int iLength=DATALENGTHS[iColumn];
	      float fMax=0;
	      float fMin=0;
	      double dMax=Math.pow((double)10,(double)(iLength-iPrecision));
	      double dPrecisionMax=Math.pow((double)10,(double)(iPrecision));
	      fMax=(float)((((int)dMax)-1))+((float)((((int)dPrecisionMax)-1))/(float)dPrecisionMax);
	      String sRealFormat=padString("",iLength-iPrecision,'#');
	      String sDecimalFormat=padString("",iPrecision,'0');
	      DecimalFormat df=new DecimalFormat(sRealFormat+"."+sDecimalFormat);
	      String sValue=df.format(((Float)oValue).floatValue());
	      int iDotIndex=sValue.indexOf('.');
	      if (iDotIndex>0 && iDotIndex<=sValue.length()-2)
	        sValue=sValue.substring(0,iDotIndex)+sValue.substring(iDotIndex+1);
	      else if (iDotIndex==0 && sValue.length()>1)
	        sValue=sValue.substring(1);
	      else if (iDotIndex==sValue.length()-1)
	        sValue=sValue.substring(0,sValue.length()-1);
	      if (bSigned) {
	        fMin=(-fMax);
	        String sUnsignedData="";
	        boolean bNegative=false;
	        if (sValue.length()>=2) {
	          sUnsignedData=sValue.substring(0,sValue.length()-1);
	          if (sUnsignedData.charAt(0)=='-') {
	            sUnsignedData=sUnsignedData.substring(1);
	            bNegative=true;
	           }
	         }
	        char cSignedChar=sValue.charAt(sValue.length()-1);        
	        if (!bNegative) {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'{';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'A';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'B';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'C';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'D';
			       break;
			     case '5': sValueAsBytes=sUnsignedData+'E';
			       break;
			     case '6': sValueAsBytes=sUnsignedData+'F';
			       break;
			     case '7': sValueAsBytes=sUnsignedData+'G';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'H';
				   break;
			     case '9': sValueAsBytes=sUnsignedData+'I';
				   break;
			    }
			  }
			 else {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'}';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'J';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'K';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'L';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'M';
				   break;
			     case '5': sValueAsBytes=sUnsignedData+'N';
				   break;
			     case '6': sValueAsBytes=sUnsignedData+'O';
				   break;
			     case '7': sValueAsBytes=sUnsignedData+'P';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'Q';
			       break;
			     case '9': sValueAsBytes=sUnsignedData+'R';
			       break;
			    }
			  }
	       }
	      else
	        sValueAsBytes=sValue;
	      if (((Float)oValue).floatValue()<fMin || ((Float)oValue).floatValue()>fMax)
	        throw new ParserException("Object "+oValue+" passed must have a value between "+fMin+" & "+fMax+" for Column "+sProperty+".");
	      float fValue=((int)(((Float)oValue).floatValue()*(int)dPrecisionMax))/(float)dPrecisionMax;
	      _data.setElementAt(new Float(fValue),iColumn);
	      setValue(sProperty,padStringInFront(sValueAsBytes,iLength,'0'));
	      break;
	     }
	    case TYPE_DOUBLE: {
	      if (!oValue.getClass().getName().equals("java.lang.Double"))
	        throw new ParserException("Object passed must be a java.lang.Double for Column "+sProperty+".");
	      boolean bSigned=DATASIGNED[iColumn];
	      int iPrecision=DATAPRECISIONS[iColumn];
	      int iLength=DATALENGTHS[iColumn];
	      double dMax=0;
	      double dMin=0;
	      double ddMax=Math.pow((double)10,(double)(iLength-iPrecision));
	      double dPrecisionMax=Math.pow((double)10,(double)(iPrecision));
	      dMax=(double)((((long)ddMax)-1))+((double)((((long)dPrecisionMax)-1))/(double)dPrecisionMax);
	      String sRealFormat=padString("",iLength-iPrecision,'#');
	      String sDecimalFormat=padString("",iPrecision,'0');
	      DecimalFormat df=new DecimalFormat(sRealFormat+"."+sDecimalFormat);
	      String sValue=df.format(((Double)oValue).doubleValue());
	      int iDotIndex=sValue.indexOf('.');
	      if (iDotIndex>0 && iDotIndex<=sValue.length()-2)
	        sValue=sValue.substring(0,iDotIndex)+sValue.substring(iDotIndex+1);
	      else if (iDotIndex==0 && sValue.length()>1)
	        sValue=sValue.substring(1);
	      else if (iDotIndex==sValue.length()-1)
	        sValue=sValue.substring(0,sValue.length()-1);
	      if (bSigned) {
	        dMin=(-dMax);
	        String sUnsignedData="";
	        boolean bNegative=false;
	        if (sValue.length()>=2) {
	          sUnsignedData=sValue.substring(0,sValue.length()-1);
	          if (sUnsignedData.charAt(0)=='-') {
	            sUnsignedData=sUnsignedData.substring(1);
	            bNegative=true;
	           }
	         }
	        char cSignedChar=sValue.charAt(sValue.length()-1);        
	        if (!bNegative) {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'{';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'A';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'B';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'C';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'D';
			       break;
			     case '5': sValueAsBytes=sUnsignedData+'E';
			       break;
			     case '6': sValueAsBytes=sUnsignedData+'F';
			       break;
			     case '7': sValueAsBytes=sUnsignedData+'G';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'H';
				   break;
			     case '9': sValueAsBytes=sUnsignedData+'I';
				   break;
			    }
			  }
			 else {
			  switch (cSignedChar) {
			     case '0': sValueAsBytes=sUnsignedData+'}';
				   break;
			     case '1': sValueAsBytes=sUnsignedData+'J';
				   break;
			     case '2': sValueAsBytes=sUnsignedData+'K';
				   break;
			     case '3': sValueAsBytes=sUnsignedData+'L';
				   break;
			     case '4': sValueAsBytes=sUnsignedData+'M';
				   break;
			     case '5': sValueAsBytes=sUnsignedData+'N';
				   break;
			     case '6': sValueAsBytes=sUnsignedData+'O';
				   break;
			     case '7': sValueAsBytes=sUnsignedData+'P';
			       break;
			     case '8': sValueAsBytes=sUnsignedData+'Q';
			       break;
			     case '9': sValueAsBytes=sUnsignedData+'R';
			       break;
			    }
			  }
	       }
	      else
	        sValueAsBytes=sValue;
	      if (((Double)oValue).doubleValue()<dMin || ((Double)oValue).doubleValue()>dMax)
	        throw new ParserException("Object "+oValue+" passed must have a value between "+dMin+" & "+dMax+"for Column "+sProperty+".");
	      double dValue=((long)(((Double)oValue).doubleValue()*(long)dPrecisionMax))/(double)dPrecisionMax;
	      _data.setElementAt(new Double(dValue),iColumn);
	      setValue(sProperty,padStringInFront(sValueAsBytes,iLength,'0'));
	      break;
	     }
	    case TYPE_DATE_CCYYMMDD: {
	      if (!oValue.getClass().getName().equals("java.sql.Date"))
	        throw new ParserException("Object passed must be a java.sql.Date for Column "+sProperty+".");
	      SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	      MessageLog.writeDebugMessage("oValue for setObject for property "+sProperty+"="+oValue,this);
	
	      String sDate=sdf.format((java.util.Date)oValue);
	      MessageLog.writeDebugMessage("Translated oValue for setObject for property "+sProperty+"="+sDate,this);
	      _data.setElementAt(oValue,iColumn);
	      setValue(sProperty,sDate);
	      break;
	     }
	   }
	}

    /**
	  To clear out all the stored parsed values in a <CODE>ByteArrayParser</CODE>, so that it can be parsed again.

	  <P><PRE>
	  Method: public void reset()
	  Visibility: Public
	  Purpose:	To clear the stored parsed values such that parsing will reoccur.
	  </PRE></P>
	*/
	public void reset() {
     for (int i=0;i<_data.size();i++) 
       	_data.setElementAt(null,i);	
	 CURRENTROWOFFSET=0; 
	 OFFSET=0;
	}
	
	
	/**
	  To get the byte array of a <CODE>ByteArrayParser</CODE>.
	
	  <P><PRE>
	  Method: public byte[] getMessage()
	  Visibility: Public
	  Purpose:	To get the byte[] message of a parsed object.
	  </PRE></P>
	  @return 		Returns the byte[] of the message.
	*/
	public byte[] getMessage() {
	  return baOutputMessage;
	}
	
	/**
	  A static function to pad to the end of a string.
	
	  <P><PRE>
	  Method: public final static String padString(String sPassed,int iPadLength,char cPadChar)
	  Visibility: Public
	  Purpose:	To pad a String with a specified character.
	  </PRE></P>
	  @param sPassed			 	Specifies the String to be Padded at End.
	  @param iPadLength			 	Specifies the final length of the padded string.
	  @param char cPadChar			Specifies the character to pad string with.
	  @return 						Returns the padded string.
	*/
	
	public final static String padString(String sPassed,int iPadLength,char cPadChar) 
	  {
	    String sReturn=sPassed;
	     if (sReturn.length()<iPadLength) {
	       int iPadding=iPadLength-sReturn.length() ;
	       for (int i=0;i<iPadding;i++) 
	          sReturn=sReturn+cPadChar;
	     }
	   return sReturn;
	 }
	
	
	/**
	  A static function to pad to the beginning of a string.
	
	  <P><PRE>
	  Method: public final static String padStringInFront(String sPassed,int iPadLength,char cPadChar)
	  Visibility: Public
	  Purpose:	To pad a String with a specified character.
	  </PRE></P>
	  @param sPassed			 	Specifies the String to be Padded in front.
	  @param iPadLength			 	Specifies the final length of the padded string.
	  @param char cPadChar			Specifies the character to pad string with.
	  @return 						Returns the padded string.
	*/
	public final static String padStringInFront(String sPassed,int iPadLength,char cPadChar) 
	  {
	    String sReturn=sPassed;
	     if (sReturn.length()<iPadLength) {
	       int iPadding=iPadLength-sReturn.length() ;
	       for (int i=0;i<iPadding;i++) 
	          sReturn=cPadChar+sReturn;
	     }
	   return sReturn;
	 }
	
	
	/**
	  To fill the byte array of a <CODE>ByteArrayParser</CODE> at specified locations.
	
	  <P><PRE>
	  Method: private void fillMessage(String sData,int iStartIndex,int iEndIndex)
	  Visibility: Private
	  Purpose:	To place data into the message block in the proper location.
	  </PRE></P>
	  @param sData			 	Specifies data to be placed into the message byte array.
	  @param iStartIndex	 	Specifies the starting index of where to place the data.
	  @param iEndIndex			Specifies the ending index of where to place the data.
	*/
	private void fillMessage(String sData,int iStartIndex,int iEndIndex) {
	  byte[] baInData=sData.getBytes();
	  for (int j=0,i=iStartIndex; i<iEndIndex; i++,j++) {
	     baOutputMessage[i]=baInData[j];
	    }
	}

    /**
	  A function to get the column index of a specified column in ByteArrayParser.

	  <P><PRE>
	  Method: public int getColumnIndex(String sProperty) throws ParserException
	  Visibility: Public
	  Purpose:	To get the column index of the specified column.
	  </PRE></P>
	  @param sProperty			 	Specifies the column to get the index of.
      @exception	Exception		<P><PRE>Throws an Exception under this Circumstance.
                                   Circumstance: If the column specified does not exist.
                                   </PRE></P>
	  @return 						Returns the index of the specified column.
	*/
	public int getColumnIndex(String sProperty) throws ParserException {
	  int iColumn=-1;
	  for (int i=0;i < getColumnCount();i++) {
	    if (getColumnName(i)!=null) {
	      if (getColumnName(i).equals(sProperty)) {
	        iColumn=i;
	        break;
	       }
	     }
	    else
	      break;
	   }
	  if (iColumn==-1)
	 throw new ParserException("Invalid Column Name "+ sProperty);
	 
	  return iColumn;
	}
	
    /**
	  A function to get the start index of a specified column in ByteArrayParser.

	  <P><PRE>
	  Method: public int getColumnStartPosition(String sProperty) throws ParserException
	  Visibility: Public
	  Purpose:	To get the start index of the specified column.
	  </PRE></P>
	  @param sProperty			 	Specifies the column to get the start index of.
      @exception	Exception		<P><PRE>Throws an Exception under this Circumstance.
                                   Circumstance: If the column specified does not exist.
                                   </PRE></P>
	  @return 						Returns the index of the specified column.
	*/
	public int getColumnStartPosition(String sProperty) throws ParserException {
	  return DATASTARTINDEXES[getColumnIndex(sProperty)];
	}
	
    /**
	  A function to get the difference in bytes between two columns in ByteArrayParser.

	  <P><PRE>
	  Method: public int getColumnsDifference(String sPropertyBegin,String sPropertyEnd) throws ParserException
	  Visibility: Private
	  Purpose:	To get the difference in bytes between two columns.
	  </PRE></P>
	  @param sPropertyBegin			Specifies the Begin Column to find the difference between.
      @param sPropertyEnd			Specifies the End Column to find the difference between.
      @exception	Exception		<P><PRE>Throws an Exception under this Circumstance.
                                   Circumstance: If either column specified does not exist.
                                   </PRE></P>
	  @return 						Returns the difference in bytes between two columns in the ByteArrayParser.
	*/
	private int getColumnsDifference(String sPropertyBegin,String sPropertyEnd) throws ParserException {
	 
	 int startPos=getColumnStartPosition(sPropertyBegin);
	 int endPos=getColumnStartPosition(sPropertyEnd) + DATALENGTHS[getColumnIndex(sPropertyEnd)];
	 return endPos - startPos;
	}

    /**
	  To get the next index that will be used for a column in a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public int getNextIndex()
      Visibility: Private
	  Purpose:	To get the next index of a column being added to ByteArrayParser.
	  </PRE></P>
	  @return 		Returns the next index to be used by added column.
	*/
	private int getNextIndex() {
	  return STARTFIELD;
	 }

    /**
	  To add a redefined column definition to a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void addRedefinedColumnDefinition(String sRedefineName,int iType,int iPrecision,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	  Visibility: Public
	  Purpose:	To specify a redefined column definition to be in used parsing the data
				contained in message.
	  </PRE></P>
	  @param sRedefineName		 	Specifies Name for the redefined column.
	  @param iType					Specifies the Format Type of Column. Valid values range 1-7. Public Constants exist for these values.
	  @param iPrecision				Specifies the precison of the Float date.
	  @param sStartColumnName		Specifies the start column of the redefined field.
      @param sEndColumnName		    Specifies the end column of the redefined field.
	  @param bSigned				Indicates the Column data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws Exception under this Circumstance.
													Circumstance 1: If this new column is greater than specified number of Columns.
													</PRE></P>
	  @exception	ParserException	<P><PRE>Throw Exception under this Circumstance.
									Circumstance 1: If the Specified Start & End Columns do not exist.
									</PRE></P>
	*/
	public void addRedefinedColumnDefinition(String sRedefineName,int iType,int iPrecision,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException {
	 int iIndex=getNextIndex();
	 DATAREDEFINED[iIndex]=true;
	 addColumnDefinition(iIndex,sRedefineName,iType,iPrecision,getColumnStartPosition(sStartColumnName),getColumnsDifference(sStartColumnName,sEndColumnName),bSigned); 
	}
	
    /**
	  To add a redefined column definition to a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void addRedefinedColumnDefinition(String sRedefineName,int iType,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	  Visibility: Public
	  Purpose:	To specify a redefined column definition to be in used parsing the data
				contained in message.
	  </PRE></P>
	  @param sRedefineName		 	Specifies Name for the redefined column.
	  @param iType					Specifies the Format Type of Column. Valid values range 1-7. Public Constants exist for these values.
	  @param sStartColumnName		Specifies the start column of the redefined field.
      @param sEndColumnName		    Specifies the end column of the redefined field.
	  @param bSigned				Indicates the Column data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws Exception under this Circumstance.
													Circumstance 1: If this new column is greater than specified number of Columns.
													</PRE></P>
	  @exception	ParserException	<P><PRE>Throw Exception under this Circumstance.
									Circumstance 1: If the Specified Start & End Columns do not exist.
									</PRE></P>
	*/
	public void addRedefinedColumnDefinition(String sRedefineName,int iType,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException {
	 int iIndex=getNextIndex();
	 DATAREDEFINED[iIndex]=true;
	 
	 addColumnDefinition(iIndex,sRedefineName,iType,getColumnStartPosition(sStartColumnName),getColumnsDifference(sStartColumnName,sEndColumnName),bSigned); 
	}
	
    /**
	  To add a redefined column definition to a <CODE>ByteArrayParser</CODE>.

	  <P><PRE>
	  Method: public void addRedefinedColumnDefinition(String sRedefineName,int iType,int iPrecision,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException
	  Visibility: Public
	  Purpose:	To specify a redefined column definition to be in used parsing the data
				contained in message.
	  </PRE></P>
	  @param sRedefineName		 	Specifies Name for the redefined column.
	  @param cType					Specifies the Custom Type of Column.
	  @param sStartColumnName		Specifies the start column of the redefined field.
      @param sEndColumnName		    Specifies the end column of the redefined field.
	  @param bSigned				Indicates the Column data is signed.
	  @exception	ArrayIndexOutOfBoundsException	<P><PRE>Throws Exception under this Circumstance.
													Circumstance 1: If this new column is greater than specified number of Columns.
													</PRE></P>
	  @exception	ParserException	<P><PRE>Throw Exception under this Circumstance.
									Circumstance 1: If the Specified Start & End Columns do not exist.
									</PRE></P>
	*/
	public void addRedefinedColumnDefinition(String sRedefineName,CustomType cType,String sStartColumnName,String sEndColumnName,boolean bSigned) throws ArrayIndexOutOfBoundsException,ParserException {
	 
	 int iIndex=getNextIndex();
	 DATAREDEFINED[iIndex]=true;
	 addColumnDefinition(iIndex,sRedefineName,cType,getColumnStartPosition(sStartColumnName),getColumnsDifference(sStartColumnName,sEndColumnName),bSigned); 
	}
	 
}
