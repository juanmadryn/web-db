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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlFileUpload.java $
//$Author: Dan $
//$Revision: 23 $
//$Modtime: 9/15/04 1:06p $
/////////////////////////

import com.salmonllc.html.events.FileUploadEvent;
import com.salmonllc.html.events.FileUploadListener;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;


/**
 * This class will generate a file upload component.
 */
public class HtmlFileUpload extends HtmlComponent {
    byte[] _content;
    String _fileName;
    String _shortFileName;
    String _mimeType;
    private String _fontTagStart = null;
    private String _fontTagEnd = null;
    Vector _listeners;
    boolean _fileUploaded;
    int _size = -1;
    private Vector _events;
    private String _theme;
    private DataStoreBuffer _ds;
    private int _contentCol = -1;
    private int _fileNameCol = -1;
    private int _shortFileNameCol = -1;
    private int _mimeTypeCol = -1;
    private boolean _enabled = true;
	private Integer _tabIndex;

    /**
     * Constructs a new File Upload.
     * @param name Each component on a page must have a unique name.
     * @param p A Page object that will be used to initialize any properties in the object.
     */
    public HtmlFileUpload(String name, HtmlPage p) {
        this(name, null, p);


    }

    /**
     * Constructs a new File Upload.
     * @param name Each component on a page must have a unique name.
     * @param theme The theme to use for loading properties.
     * @param p A Page object that will be used to initialize any properties in the object.
     */
    public HtmlFileUpload(String name, String theme, HtmlPage p) {
        super(name, p);
        setTheme(theme);


    }

    /**
     * This method was created in VisualAge.
     * @param e com.salmonllc.html.events.ValueChangedEvent
     */
    protected void addEvent(FileUploadEvent e) {
        if (_events == null)
            _events = new Vector();

        _events.addElement(e);
    }

    /**
     * This method adds a listener the will be notified when a file is uploaded.
     * @param l The listener to add.
     */
    public void addFileUploadListener(FileUploadListener l) {
        if (_listeners == null)
            _listeners = new Vector();

        for (int i = 0; i < _listeners.size(); i++) {
            if (((FileUploadListener) _listeners.elementAt(i)) == l)
                return;
        }

        _listeners.addElement(l);
    }

    /**
     * This method should be implemented by subclasses of this component that will events. This method should notify each of the component's listeners that an event occured.
     * @param eventType valid Types are EVENT_SUBMIT and EVENT_OTHER
     */
    public boolean executeEvent(int eventType) throws Exception {
        if (eventType != EVENT_OTHER)
            return true;

        if (_events == null)
            return true;

        for (int j = 0; j < _events.size(); j++) {
            FileUploadEvent e = (FileUploadEvent) _events.elementAt(j);

            if (_listeners != null) {
                for (int i = 0; i < _listeners.size(); i++)
                    ((FileUploadListener) _listeners.elementAt(i)).fileUploaded(e);
            }

            _fileName = e.getFileName();
            _content = e.getContent();
            _mimeType = e.getMimeType();
            _shortFileName = e.getShortFileName();

            int iRowNo = e.getRow();
            if (_ds != null) {
                if (_fileNameCol != -1) {
                    if (iRowNo > -1)
                        _ds.setString(iRowNo, _fileNameCol, e.getFileName());
                    // Modificado por Demian Barry
                    // Si la row es -1 no puede setear nada en el data store
                    //else
                    //    _ds.setString(_fileNameCol, e.getFileName());
                }
                if (_shortFileNameCol != -1) {
                    if (iRowNo > -1)
                        _ds.setString(iRowNo, _shortFileNameCol, e.getShortFileName());
                    //else
                    //    _ds.setString(_shortFileNameCol, e.getShortFileName());
                }
                if (_mimeTypeCol != -1) {
                    if (iRowNo > -1)
                        _ds.setString(e.getRow(), _mimeTypeCol, e.getMimeType());
                    //else
                    //    _ds.setString(_mimeTypeCol, e.getMimeType());
                }
                if (_contentCol != -1) {
                    if (iRowNo > -1)
                        _ds.setByteArray(e.getRow(), _contentCol, e.getContent());
                    //else
                    //    _ds.setByteArray(_contentCol, e.getContent());
                }
            }
        }

        reset();
        return true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) {
        if (!getVisible())
            return;

        String tag = "<INPUT TYPE=\"FILE\" name=\"" + getFullName() + (rowNo > -1 ? "_" + rowNo + "\"" : "\"");

        if ((! _enabled) && useDisabledAttribute())
            tag += " disabled=\"true\"";

        if (_size > -1) {
            int size = _size;
            if (getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE)
                size = (int) (size * .60);
            tag += " SIZE=\"" + size + "\"";
        }

        if (_fileName != null)
            tag += " VALUE=\"" + _fileName + "\"";

        if (_class != null)
            tag += " class=\"" + _class + "\"";


			
		if (_tabIndex != null) 
			tag += " tabindex=\"" + _tabIndex + "\"";
			
        tag += ">";

        if (_fontTagStart != null)
            tag = _fontTagStart + tag + _fontTagEnd;

        p.println(tag);
    }

    /**
     * This method returns the contents of the file uploaded.
     */
    public byte[] getContent() {
        return _content;
    }

    /**
     * This method returns the name of the file uploaded.
     */
    public String getFileName() {
        return _fileName;
    }

    /**
     * This method returns the short name of the file uploaded.
     */
    public String getShortFileName() {
        return _shortFileName;
    }

    /**
     * This method gets the end font tag for the component.
     */
    public String getFontEndTag() {
        return _fontTagEnd;
    }

    /**
     * This method gets the start font tag for the component.
     */
    public String getFontStartTag() {
        return _fontTagStart;
    }

    /**
     * This method returns the name of the mimeType uploaded.
     */
    public String getMimeType() {
        return _mimeType;
    }

    /**
     * This method gets the size of the component.
     */
    public int getSize() {
        return _size;
    }

    /**
     * This method returns the property theme for the component.
     */
    public String getTheme() {
        return _theme;
    }

    public boolean processParms(Hashtable parms, int row) {
        if (!getVisible())
            return false;

        String name = getFullName();
        if (row > -1)
            name += "_" + row;

        String filename = "";
        String shortFileName = "";
        if (parms.get(name) == null)
            return false;
        else if (parms.get(name) instanceof java.lang.String)
            filename = (String) parms.get(name);
        else
            filename = ((String[]) parms.get(name))[0];

        _fileName = null;
        _mimeType = null;
        _content = null;
        _shortFileName = null;

        if (filename != null) {
            String mimeType = (String) parms.get(name + "_contentType");
            byte[] content = (byte[]) parms.get(name + "_content");
            shortFileName = setShortFileName(filename);
            addEvent(new FileUploadEvent(getPage(), this, getName(), getFullName(), filename, shortFileName, content, mimeType, row));
        }
        return false;

    }

    /**
     * This method extracts the file name from a directory.
     * @param sFileName String
     */
    private String setShortFileName(String sFileName) {
        if (sFileName == null) {
            _shortFileName = null;
            return _shortFileName;
        }
        int iIdx = sFileName.lastIndexOf(File.separator);

        if (iIdx < 0) {
            _shortFileName = sFileName;
            return _shortFileName;
        }

        _shortFileName = sFileName.substring(iIdx + 1, sFileName.length());
        return _shortFileName;
    }

    /**
     * This method was created in VisualAge.
     */
    protected void removeEvent(int index) {
        _events.removeElementAt(index);
    }

    /**
     * This method removes a listener from the list that will be notified if the text in the component changes.
     * @param l The listener to remove.
     */
    public void removeFileUploadListener(FileUploadListener l) {
        if (_listeners == null)
            return;

        for (int i = 0; i < _listeners.size(); i++) {
            if (((FileUploadListener) _listeners.elementAt(i)) == l) {
                _listeners.removeElementAt(i);
                return;
            }
        }
    }

    /**
     * This method will clear all pending events from the event queue for this component.
     */
    public void reset() {
        if (_events != null) {
            _events.setSize(0);
        }
    }

    /**
     * Use this method to bind the component to columns in a DataStore.
     * @param ds The datastore to bind to.
     * @param fileNameColumn The column in the datastore to put the filename
     * @param shortFileNameColumn The column in the datastore to put the short filename
     * @param mimeTypeColumn The column in the datastore to put the mimetype
     * @param contentColumn The column in the datastore to put the content
     */
    public void setColumns(DataStoreBuffer ds, String fileNameColumn, String shortFileNameColumn, String mimeTypeColumn, String contentColumn) {
        _ds = ds;
        if (fileNameColumn != null)
            _fileNameCol = ds.getColumnIndex(fileNameColumn);
        else
            _fileNameCol = -1;

        if (shortFileNameColumn != null)
            _shortFileNameCol = ds.getColumnIndex(shortFileNameColumn);
        else
            _shortFileNameCol = -1;

        if (mimeTypeColumn != null)
            _mimeTypeCol = ds.getColumnIndex(mimeTypeColumn);
        else
            _mimeTypeCol = -1;

        if (contentColumn != null)
            _contentCol = ds.getColumnIndex(contentColumn);
        else
            _contentCol = -1;
    }

    /**
     * Use this method to bind the component to columns in a DataStore.
     * @param ds The datastore to bind to.
     * @param fileNameColumn The column in the datastore to put the filename
     * @param mimeTypeColumn The column in the datastore to put the mimetype
     * @param contentColumn The column in the datastore to put the content
     */
    public void setColumns(DataStoreBuffer ds, String fileNameColumn, String mimeTypeColumn, String contentColumn) {
        setColumns(ds, fileNameColumn, null, mimeTypeColumn, contentColumn);
    }

    /**
     * This method returns the name of the file to be uploaded.
     */
    public void setFileName(String fileName) {
        _fileName = fileName;
    }

    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontTagEnd = value;
    }

    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontTagStart = value;
    }

    /**
     * This method sets the size of the component.
     */
    public void setSize(int size) {
        _size = size;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        Props props = getPage().getPageProperties();

        _fontTagStart = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_START);
        _fontTagEnd = props.getThemeProperty(theme, Props.FONT_TEXT_EDIT + Props.TAG_END);

        _theme = theme;
    }

    public boolean isEnabled() {
        return _enabled;
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
    }
    


	
	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null)
			return -1;
		return _tabIndex.intValue();
	}




	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else	
			_tabIndex = new Integer(val);
	}
	
	/**
	 * Clears the content to free memory for big uploads
	 */
	public void clearContent() {
		_content=null;
	}	
}
