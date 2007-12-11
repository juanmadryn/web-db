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

package com.salmonllc.localizer;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/localizer/PropertiesLocalizer.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 8/24/04 1:03p $
/////////////////////////

import java.io.*;

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * The Properties Localizer is a concrete implementation of the Localizer class. This will load the localization keys from a property file in the salmon.props.path
 */
public class PropertiesLocalizer extends Localizer {

    private String _charSet;
	/**
	 * Constructs a new Localizer
	 */
    public PropertiesLocalizer () {
        this("8859_1",false);
    }
    /**
     * Constructs a new Localizer using properties files of a particular character set
     * @param charSet The character set the property files will be in
     * @param translateEscapes A boolean flag indicating whether or not to translate escape characters into UniCode
     */
    public PropertiesLocalizer (String charSet, boolean translateEscapes) {
        super(true,translateEscapes);
        _charSet=charSet;
    }

	
	/**
	 * Loads the data from a property file in the Salmon.props.path.
	 * The file name must be in the form appName.lang.properties where appName and lang are the properties passed to the method.
	 * If the file appName.lang.properties is not found, it will attempt to load a file called lang.properties.
	 * The method returns true if it succeeds and false if not.
	 */
	protected boolean loadData(String appName, String language) {

		try {
			String propsPath = Props.getPropsPath();
			File f = new File(propsPath + File.separatorChar + appName + "." + language + ".properties");
			if (! f.exists())
				f = new File(propsPath + File.separatorChar + language + ".properties");
				if (! f.exists())
					return false;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),_charSet));
			String line = in.readLine();
			int pos = 0;
			while (line != null) {
				if (line.length() > 0 && line.charAt(0) != '#') {
					pos = line.indexOf('=');
					if (pos > -1) {
						String key = line.substring(0,pos);
						String val = line.substring(pos + 1);
						addValue(key,val);
					}
				}
				line = in.readLine();
			}
			in.close();
			return true;
		}
		catch (Exception e) {
			MessageLog.writeErrorMessage("loadData",e,this);
		}

		return false;
	}
	/**
	 * @returns the character set
	 */
	public String getCharSet() {
		return _charSet;
	}

}
