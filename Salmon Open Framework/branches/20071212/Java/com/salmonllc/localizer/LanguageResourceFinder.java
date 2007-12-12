package com.salmonllc.localizer;

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
/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/localizer/LanguageResourceFinder.java $
//$Author: Dan $
//$Revision: 5 $
//$Modtime: 11/11/02 3:21p $
/////////////////////////
import java.util.Enumeration;
import java.util.Hashtable;

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * This class is used by the sytem to maintain a list of all language localizers used by the system
 */
public class LanguageResourceFinder {
	private static Hashtable _loaded = new Hashtable();
	private static Hashtable _failures = new Hashtable();

	/**
	 * Clears out the resource hash table so that resources will be reloaded
	 */
	public static synchronized void clearCache() {
		if (_loaded != null) {
			_loaded.clear();
			_failures.clear();
		}
	}
	/**
	 * Returns an Enumeration of keys for one language
	 */
	public static Enumeration getKeys(String appName, String language) {
		if (appName == null)
			return null;
		String localizerClass = Props.getProps(appName, null).getProperty(Props.LOCALIZER_CLASS);
		if (localizerClass == null)
			return null;
		Localizer l = getLocalizer(appName,language,localizerClass);
		if (l == null)
			return null;
		else
			return l.getKeys();
	}
	private static Localizer getLocalizer(String app, String lang, String loader) {
		String key = app + ":" + lang;
		if (_loaded.containsKey(key))
			return (Localizer) _loaded.get(key);
		else if (_failures.containsKey(key))
			return null;
		else
			return loadLocalizer(key, app, lang, loader);
	}
	/**
	 * Returns the resource specified by appName and key using the specified LanguagePreference object
	 */
	public static String getResource(String appName, String key, LanguagePreferences pref) {
		if (key == null || appName == null)
			return null;
		String localizerClass = Props.getProps(appName, null).getProperty(Props.LOCALIZER_CLASS);
		if (localizerClass == null || pref == null)
			return null;
		String lang = pref.getFirstLanguage();
		while (lang != null) {
			Localizer l = getLocalizer(appName, lang, localizerClass);
			if (l != null) {
				String resource = l.getResource(key);
				if (resource != null)
					return resource;
			}
			lang = pref.getNextLanguage();
		}
		return null;
	}
	private static synchronized Localizer loadLocalizer(String key, String app, String lang, String loader) {
		try {
			Class c = Class.forName(loader,true, Thread.currentThread().getContextClassLoader());
			Localizer l = (Localizer) c.newInstance();
			boolean ret = l.loadData(app, lang);
			if (ret) {
				_loaded.put(key, l);
				return l;
			} else {
				_failures.put(key, new Object());
				return null;
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error loading Localizer class:" + loader, e, null);
			_failures.put(key, new Object());
			return null;
		}

	}
}
