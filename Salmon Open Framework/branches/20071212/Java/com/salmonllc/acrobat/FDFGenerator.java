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
package com.salmonllc.acrobat;

import java.net.*;
import java.util.*;
import java.io.*;
/**
 * FDFGenerator is a class to generate an FDF file which will 
 * contain fieldnames and values for the fields for a form in a PDF File.
 * To use create an Instance of FDFGenerator with a URL to the PDF File containing the Form with Fields defined,
 * and a Fully specified path & filename of the generated FDF file.
 * Then set the values of the fields using setValue method.
 * After setting all values, make a call to createFDFFile, this will generated the FDF specified in the constructor.
 * To display the PDF with the specified data, redirect to a URL specifying the web location of the FDF file which was generated.
 * Creation date: (1/15/02 12:18:16 PM)
 * @author: Fred Cahill
 */
public class FDFGenerator {
	private static final String _HEADER="%FDF-1.2\n1 0 obj <<\n/FDF <<\n/Fields\n[";
	private static final String _FOOTER=">>\n>>\nendobj\ntrailer\n<</Root 1 0 R>>\n%%EOF";

	private URL _PDFTemplate;
	private String _FDFOutputFile;

	private Hashtable _htFields=new Hashtable();

/**
 * This create an FDFGenerator object for the PDF Template.
 * @param uPDFTemplate A Url pointing to a PDF Template Form
 * @param sFDFOutputFile The filename of the generated FDF file.
 */
public FDFGenerator(URL uPDFTemplate,String sFDFOutputFile) {
	super();
	_PDFTemplate=uPDFTemplate;
    _FDFOutputFile=sFDFOutputFile;
}
/**
 * This method clears a field value in the template.
 * @param sFieldName The name of a field in the Template.
 */
	public void clearField(String sFieldName) {
		_htFields.remove(sFieldName);
	}
/**
 * This method generates the FDF file for the template containing the values for the fields. The name of the generated file was specified in the constructor.
 */
	public void createFDFFile() throws FDFGeneratorException {
		File fFDFOutputFile=new File(_FDFOutputFile);
		try {
			FileOutputStream fosFDF=new FileOutputStream(fFDFOutputFile);
			PrintWriter pw=new PrintWriter(fosFDF);
			pw.println(_HEADER);
			Enumeration enumFields=_htFields.keys();
			while (enumFields.hasMoreElements()) {
				String sField=(String)enumFields.nextElement();
				pw.println("<<\n/T ("+sField+")\n/V ("+(String)_htFields.get(sField)+")\n>>");
			}
			pw.println(getPDFFileLocation());
			pw.println(_FOOTER);
			pw.close();
			fosFDF.close();
		}
		catch (Exception e) {
			throw new FDFGeneratorException(e.getMessage());
		}
	}
	private String getPDFFileLocation() {
		return "]\n/F ("+_PDFTemplate+")";
	}
/**
 * This method sets the value of a field in the template.
 * @param sFieldName The name of a field in the Template.
 * @param sValue The value to set the field to in the Template.
 */
	public void setValue(String sFieldName, String sValue) {
		_htFields.put(sFieldName,sValue);
	}
}
