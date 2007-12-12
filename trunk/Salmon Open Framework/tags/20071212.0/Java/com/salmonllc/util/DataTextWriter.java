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

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/util/DataTextWriter.java $
//$Author: Dan $ 
//$Revision: 7 $ 
//$Modtime: 10/30/02 2:59p $ 
/////////////////////////
 
import java.io.*;
import java.util.*;
/**
 * This object allows you to write a flat text file with data elements separated by Separators and such.
 */
public class DataTextWriter {
	private int NumColumns=0;
	private Vector[] Columns;
	private String LineBreak="\r\n";
	private String Separator=",";
	private boolean Quoted=false;
	private char QuoteCharacter='\0';
	private StringBuffer sbTextBuffer=new StringBuffer();
/**
 * Builds a DataTextWriter with each row containing iNumColumns.
 * @param iNumCols The number of columns in a row of the DataTextWriter.
 */ 
public DataTextWriter(int iNumCols) {
  this(iNumCols,"","",'\0',false);
}
/**
 * Builds a DataTextWriter with each row containing iNumCols and each piece of Data separated by sSeparator.
 * @param iNumCols The number of columns in a row of the DataTextWriter.
 * @param sSeparator The separator used to separate data columns in a row.
 */ 

public DataTextWriter(int iNumCols,String sSeparator) {
	this(iNumCols,sSeparator,"",'\0',false);
}
/**
 * Builds a DataTextWriter with each row containing iNumCols and each piece of Data separated by sSeparator 
 * and row separated by sLineBreak.
 * @param iNumCols The number of columns in a row of the DataTextWriter.
 * @param sSeparator The separator used to separate data columns in a row.
 * @param sLineBreak The separator used to separate individual rows.
 */ 
 
public DataTextWriter(int iNumCols,String sSeparator,String sLineBreak) {
	this(iNumCols,sSeparator,sLineBreak,'\0',false);
}
/**
 * Builds a DataTextWriter with each row containing iNumColumns and each piece of Data separated by sSeparator 
 * and row separated by sLineBreak and each piece of Data is surrounded by cQuoteChar if bQuoted is true.
 * @param iNumCols The number of columns in a row of the DataTextWriter.
 * @param sSeparator The separator used to separate data columns in a row.
 * @param sLineBreak The separator used to separate individual rows.
 * @param cQuoteChar The character used to quote indivividual data columns.
 * @param bQuoted The flag to indicate if data columns are to be quoted.
 */ 

public DataTextWriter(int iNumCols,String sSeparator,String sLineFeed,char cQuoteChar,boolean bQuoted) {
	NumColumns=iNumCols;
	Separator=sSeparator;
	LineBreak=sLineFeed;
	QuoteCharacter=cQuoteChar;
	Quoted=bQuoted;
	Columns=new Vector[iNumCols];
	for (int i=0;i<iNumCols;i++)
	  Columns[i]=new Vector(100,100);
}
/**
 * Adds a piece of data to a specific column in this DataTextWriter. 
 * @param iColumn The column which the specified data belongs to.
 * @param sData The data for the specified column.
 */ 
 
public void addData(int iColumn,String sData) throws DataTextWriterException {
	if (iColumn<0 || iColumn>=NumColumns)
	  throw new DataTextWriterException("Column Index is out of range. Valid Range is (0-"+(NumColumns-1)+").");
	Columns[iColumn].addElement(sData);
	int iCount=Columns[0].size(); 
	for (int i=1;i<NumColumns;i++)
	   if (Columns[i].size()!=iCount)
	     return;
	int i=Columns[iColumn].size()-1;
	for (int j=0;j<(NumColumns-1);j++) {
	  if (Quoted) 
	    sbTextBuffer.append(QuoteCharacter+(String)Columns[j].elementAt(i)+QuoteCharacter+Separator);
	  else
		sbTextBuffer.append((String)Columns[j].elementAt(i)+Separator);
	 }
	if (Quoted) 
	  sbTextBuffer.append(QuoteCharacter+(String)Columns[NumColumns-1].elementAt(i)+QuoteCharacter+LineBreak);
	else
	  sbTextBuffer.append((String)Columns[NumColumns-1].elementAt(i)+LineBreak);
}
/**
 * This method returns the Data in DataTextWriter in formatted output string.
 * @return Formatted Output
 */
public String getText() /*throws DataTextWriterException*/ {
//	StringBuffer sb=new StringBuffer();
/*	int iCount=Columns[0].size(); 
	for (int i=1;i<NumColumns;i++)
	   if (Columns[i].size()!=iCount)
	     throw new DataTextWriterException("There is not an equal number data values in each column.");*/
/*	for (int i=0;i<iCount;i++) {
	  for (int j=0;j<NumColumns-1;j++) {
		if (Quoted) 
	      sb.append(QuoteCharacter+(String)Columns[j].elementAt(i)+QuoteCharacter+Separator);
		else
		  sb.append((String)Columns[j].elementAt(i)+Separator);
	   }
	  if (Quoted) 
		sb.append(QuoteCharacter+(String)Columns[NumColumns-1].elementAt(i)+QuoteCharacter+LineBreak);
	  else
		sb.append((String)Columns[NumColumns-1].elementAt(i)+LineBreak);
	 } */
	return sbTextBuffer.toString();
}
/**
 * This method returns the length of the Data in DataTextWriter in formatted output string.
 * @return Formatted Output
 */
public int getTextLength() {
	return sbTextBuffer.length();
}
/**
 * Sets the LineBreak Separator in this DataTextWriter. 
 * @param sLineBreak  The separator used to separate individual rows.
 * @uml.property  name="lineBreak"
 */ 
public void setLineBreak(String sLineBreak) {
	LineBreak=sLineBreak;
}
/**
 * Sets the Character to be used for Quoting Data Columns in this DataTextWriter. 
 * @param cQuoteChar  The character used to quote individual data columns.
 * @uml.property  name="quoteCharacter"
 */ 
public void setQuoteCharacter(char cQuoteChar) {
	QuoteCharacter=cQuoteChar;
}
/**
 * Sets the Flag to to inicate that Data Columns are to be quoted in this DataTextWriter. 
 * @param bQuoted  The flag used to indicate if data columns are quoted.
 * @uml.property  name="quoted"
 */ 
public void setQuoted(boolean bQuoted) {
	Quoted=bQuoted;
}
/**
 * Sets the Separator to be used for separating individual Data Columns in this DataTextWriter. 
 * @param sSeparator  The separator used to separate individual data columns.
 * @uml.property  name="separator"
 */ 
public void setSeparator(String sSeparator) {
	Separator=sSeparator;
}
/**
 * This method creates a flat file containing the data added to this DataTextWriter.
 * @param sFilename The name of flat file to create.
 */
public void writeFile(String sFilename) throws DataTextWriterException {
	try {
	  FileOutputStream fos=new FileOutputStream(sFilename);
	  PrintWriter pw=new PrintWriter(fos);
	  pw.print(getText());
	  pw.close();
	  fos.close();
	 }
	catch(Exception e) {
	  throw new DataTextWriterException(e.getMessage());
	 }            
}
}
