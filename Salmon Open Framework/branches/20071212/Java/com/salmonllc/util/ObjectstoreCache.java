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

import java.util.Enumeration;
import java.util.Hashtable;

import com.salmonllc.properties.Props;
/**
 * This class is used to cache images for the objectstore.
 */
public class ObjectstoreCache {
	static Hashtable _entries;
	static long _bytesUsed = 0;
	static long _maxBytes = 0;
	static long _lastCleared = -1;
	static long _cacheTimeout = 17500000;

	private static class CacheEntry {
		byte[] data;
		long lastUsed;
	}	
/**
 * This method adds an item to the cache
 */
public static synchronized boolean addEntry(String key, byte[] data) {
	init();
	if (_entries.containsKey(key)) {
		CacheEntry e = (CacheEntry) _entries.get(key);
		_bytesUsed -= e.data.length;
		_bytesUsed += data.length;
		e.data=data;
		e.lastUsed=System.currentTimeMillis();
		return true;
	}
	else {
		if (_bytesUsed >= _maxBytes)
			return false;
		CacheEntry e = new CacheEntry();
		_bytesUsed+=data.length;
		e.data=data;
		e.lastUsed=System.currentTimeMillis();
		_entries.put(key,e);
		return true;
	}	
}
/**
 * This method will clear old entries from the cache
 */
public static void cleanCache() {
	long t = System.currentTimeMillis() - _cacheTimeout;
	if (_entries != null) {
		Enumeration enumera = _entries.keys();
		while (enumera.hasMoreElements()) {
			String key = (String) enumera.nextElement();
			CacheEntry ent = (CacheEntry) _entries.get(key);
			if (ent != null) {
				if (ent.lastUsed < t)
					removeEntry(key);

			}	
		}
	}	
}
/**
 * This clears the ObjectStore cache of all entries
 */
public static synchronized void clearCache() {
	init();
	_entries.clear();
	_bytesUsed = 0;
	initProperties();
	_lastCleared = System.currentTimeMillis();
}
/**
 * This method gets an item from the cache
 */
public static byte[] getEntry(String key) {
	init();
	if (_entries.containsKey(key)) {
		CacheEntry e = (CacheEntry) _entries.get(key);
		return e.data;
	}
	return null;
}
/**
 * This method returns the time the cache was last cleared
 */
public static long getLastCleared() {
	return _lastCleared;
}
/**
 * This method was created in VisualAge.
 */
private static void init() {
	if (_entries == null) {
		_entries = new Hashtable();
		_bytesUsed = 0;
		_lastCleared = -1;
		initProperties();
	}	
}	
private static void initProperties() {
	Props p = Props.getSystemProps();
	_maxBytes = p.getIntProperty(Props.SYS_OBJECTSTORE_CACHE_SIZE);
	if (_maxBytes == -1)
		_maxBytes = 1000000;
	_cacheTimeout = p.getIntProperty(Props.SYS_OBJECTSTORE_CACHE_IDLE_TIME_OUT);
	if (_cacheTimeout == -1)
		_cacheTimeout = 1750000;
}
private static synchronized void removeEntry(String key) {
	init();
	if (_entries.containsKey(key)) {
		CacheEntry e = (CacheEntry) _entries.get(key);
		_bytesUsed -= e.data.length;
		_entries.remove(key);
	}
}
}
