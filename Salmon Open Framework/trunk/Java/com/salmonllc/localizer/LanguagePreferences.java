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

import com.salmonllc.util.VectorSort;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class is used to parse an HTTP language preferences string to determine which languages the user prefers
 */
public class LanguagePreferences implements Serializable {
	private LangList _list = new LangList();
	private int _ndx=0;
	
	private class Lang implements Serializable {
		String lang;
		float weight;	
	}	
	private class LangList extends VectorSort implements Serializable {
		public boolean compare(Object o1, Object o2) {
			Lang l1 = (Lang) o1;
			Lang l2 = (Lang) o2;
			return l1.weight > l2.weight;
		}
	}	

	/**
	 * Constructs a new LanguagePreference object. The passed string should be an http Accept-Language header.
	 */
	public LanguagePreferences(String langString) {
		Hashtable specific = new Hashtable();
		StringTokenizer t = new StringTokenizer(langString,",");		
		float ndx = 0.000099F;
		while (t.hasMoreTokens()) {
			String tok = t.nextToken().trim();
			String lang=null;
			float weight=1.0F;
			int pos=tok.indexOf(';');
			if (pos == -1) 
				lang=tok;
			else {
				lang=tok.substring(0,pos);
				pos=tok.indexOf('=');
				if (pos > -1) {
					String work=tok.substring(pos + 1);
					try {
						weight = Float.parseFloat(work);
					} catch (Exception e) {};	
				}			
			}
			if (lang == null)
				continue;
			Lang l = new Lang();
			l.lang = lang.toLowerCase();
			l.weight = weight + ndx;
			ndx -= 0.000001F;
			_list.add(l);
			pos = lang.indexOf("-");
			if (pos == -1)
				specific.put(lang,lang);
		}	
		_list.sort();
		
		int size = _list.size();
		for (int i = 0; i < size;i++) {
			Lang l = (Lang) _list.elementAt(i);
			String lang = l.lang;
			int pos = lang.indexOf('-');
			if (pos > -1) {
				lang = lang.substring(0,pos);	
				if (! specific.containsKey(lang)) {
					l = new Lang();
					l.lang = lang;
					_list.add(l);
					specific.put(lang,lang);
				}	
			}	
		}	
	}
	/**
	 * Returns the first language in the list or null if there aren't any
	 */
	public String getFirstLanguage() {
		_ndx = 0;
		if (_ndx >= _list.size())
			return null;
			
		Lang l = (Lang) _list.elementAt(_ndx);
		return l.lang;	
	}
	/**
	 * Returns the next language in the list or null if there aren't any. 
	 */
	public String getNextLanguage() {
		_ndx++;
		if (_ndx >= _list.size())
			return null;
			
		Lang l = (Lang) _list.elementAt(_ndx);
		return l.lang;	
	}
}
