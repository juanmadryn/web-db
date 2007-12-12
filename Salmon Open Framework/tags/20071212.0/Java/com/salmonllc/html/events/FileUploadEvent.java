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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/events/FileUploadEvent.java $
//$Author: Srufle $ 
//$Revision: 10 $ 
//$Modtime: 4/15/03 1:55a $ 
/////////////////////////
 
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;

/**
 * This object will be created and passed to every file upload event method.
 * @see  FileUploadListener
 */
 
public class FileUploadEvent {
	HtmlPage _page;
	HtmlComponent _comp;
	String _name;
	String _fullName;
   byte[] _content;
	String _fileName;
   String _shortFileName;
	String _mimeType;
	int _row;
public FileUploadEvent(HtmlPage page, HtmlComponent comp, String name, String fullName, String fileName, byte[] content, String mimeType, int row) {
	this( page,  comp,  name,  fullName,  fileName, null, content,  mimeType,  row) ;
}

public FileUploadEvent(HtmlPage page, HtmlComponent comp, String name, String fullName, String fileName, String shortFileName, byte[] content, String mimeType, int row) {
	_page = page;
	_comp = comp;
	_name = name;
	_fullName = fullName;
	_content = content;
	_fileName = fileName;
	_mimeType = mimeType;
	_row = row;
   _shortFileName =  shortFileName;
}
/**
 * This method returns the component that sumbitted the page.
 */
public HtmlComponent getComponent() {
	return _comp;
}
/**
 * This method returns the content for the file uploaded
 */
public byte[] getContent() {
	return _content;
}
/**
 * This method returns the name of the file that was uploaded
 */
public String getFileName() {
	return _fileName;
}
/**
 * This method returns the short name of the file that was uploaded
 */
public String getShortFileName() {
	return _shortFileName;
}
/**
 * This method returns the full name (name of component appended to the name of its containers) of the component that submitted the page.
 */
public String getFullName() {
	return _fullName;
}
/**
 * This method returns the mime type of the file that was uploaded.
 */
public String getMimeType() {
	return _mimeType;
}
/**
 * This method returns the name of the component that submitted the page.
 */
public String getName() {
	return _name;
}
/**
 * This method returns the page for which the submit was performed.
 */
public HtmlPage getPage() {
	return _page;
}
/**
 * This method returns the row for which the submit was performed.
 */
public int getRow() {
	return _row;
}
}
