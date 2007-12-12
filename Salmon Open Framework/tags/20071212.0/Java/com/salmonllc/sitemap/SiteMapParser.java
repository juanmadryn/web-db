//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
// 
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
// 
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// 
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************

package com.salmonllc.sitemap;

import com.salmonllc.properties.Props;
import com.salmonllc.util.TwoObjectContainer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Hashtable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Internal class used to parse the sitemap xml file
 */
class SiteMapParser extends org.xml.sax.helpers.DefaultHandler {

	private static String DTD = "<?xml encoding=\"US-ASCII\"?> <!ELEMENT  sitemap (entry)+> <!ELEMENT entry (name,uri,useForward?, secure?, popupFeatures?, action?,context?)> <!ELEMENT name (#PCDATA)> <!ELEMENT uri (#PCDATA)> <!ELEMENT secure (#PCDATA)> <!ELEMENT useForward (#PCDATA)> <!ELEMENT popupFeatures (#PCDATA)> <!ELEMENT action (actionName,actionEntry)><!ELEMENT actionName (#PCDATA)><!ELEMENT actionEntry (#PCDATA)> <!ELEMENT context (#PCDATA)> ";

	private static Hashtable _loadedMaps = new Hashtable();
	private SiteMap _map;
	private String _currentChars;
	private String _name;
	private String _uri;
    private String _context;
	private boolean _secure;
	private boolean _forward;
	private String _popupFeatures;
	private String _actionName;
	private String _actionEntry;

	private SiteMapParser(String appName, InputStream in) {
		try {
			_map = new SiteMap(appName);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(new InputSource(in), this);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	private SiteMap getMap() {
		return _map;
	}

	/**
	 * Part of the XML Parser implementation. Do not call directly.
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
		return new InputSource(new StringReader(DTD));
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		_currentChars="";
	}
	/**
	 * Part of the XML Parser implementation. Do not call directly.
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		_currentChars += new String(ch, start, length);
	}

	/**
	 * Part of the XML Parser implementation. Do not call directly.
	 */
	public void endElement(String uri, String localName, String name) {
		if (name.equals("entry")) {
			_map.putEntry(_name,_uri, _popupFeatures, _forward, _secure,_context);
			_name = null;
            _context=null;
			_uri = null;
			_secure = false;
			_forward = false;
			_popupFeatures = null;
			_actionName=null;
			_actionEntry=null;
		}
		else if (name.equals("action")) {
			_map.addActionEntry(_name,_actionName,_actionEntry);
			_actionName = null;
			_actionEntry = null;	
		}	 
		else if (name.equals("name"))
			_name = _currentChars;
        		else if (name.equals("uri"))
			_uri = _currentChars;
		else if (name.equals("popupFeatures"))
			_popupFeatures = _currentChars;
		else if (name.equalsIgnoreCase("useForward"))
			_forward = _currentChars.equalsIgnoreCase("true");
		else if (name.equalsIgnoreCase("secure"))
			_secure = _currentChars.equalsIgnoreCase("true");
		else if (name.equals("actionName"))
			_actionName = _currentChars;
		else if (name.equals("actionEntry"))
			_actionEntry = _currentChars;
        else if (name.equals("context"))
             _context = _currentChars;

	}

	/**
	 * Gets the sitemap for the specified applicaion
	 * @throws IOException
	 */
	public static SiteMap getSiteMap(String appName) throws IOException {
		String filePath = Props.getPropsPath();
		if (!filePath.endsWith(File.separator))
			filePath += File.separator;
		String fileName = filePath + appName + ".map";
		File f = new File(fileName);
        if (! f.exists()) {
        	fileName = filePath + "System.map";
        	f = new File(fileName);
        	if (!f.exists())
        		return new SiteMap(appName);
        }    

		long lastMod = f.lastModified();

		TwoObjectContainer cont = (TwoObjectContainer) _loadedMaps.get(appName);
		if (cont != null && lastMod == ((Long) cont.getObject1()).longValue())
			return (SiteMap) cont.getObject2();

		FileInputStream in = new FileInputStream(f);
		SiteMapParser ps = new SiteMapParser(appName, in);
		in.close();
		cont = new TwoObjectContainer(new Long(lastMod), ps.getMap());
		_loadedMaps.put(appName, cont);
		return ps.getMap();
	}


}
