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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A class that encapsulates JDK 1.4 regular expression matching.
 *
 * This was built as a wrapper so the framework would compile in JDK 1.3 and below, but the class only runs in JDK 1.4 and above.
 */
public class RegularExpressionMatcher implements Serializable{
	private Object _pattern;
	private String _patternString;
	private String _javaScriptFlags="";
	private int _handleNull = HANDLE_NULL_DEFAULT;
	
	public static final int HANDLE_NULL_DEFAULT = 0;
	public static final int HANDLE_NULL_RETURN_TRUE = 1;
	public static final int HANDLE_NULL_RETURN_FALSE = 2;

	/**
	 * Creates a regular expression pattern matcher
	 * @param pattern The pattern is in the form of a javascript regexp pattern - /regexp/flags - where regexp is the expression pattern and flags are additional qualifiers. Flags can be i=case insensitive, m=multiline mode, t=nulls always return true, f=nulls always return false. Flags can be placed in any order in the flag string or may be excluded althgether.
	 */

	public RegularExpressionMatcher(String pattern) {
		Class c = null;

		try {
			c = Class.forName("java.util.regex.Pattern");
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error creating regular expression matcher", e, this);
		}

		
		//StringTokenizer st = new StringTokenizer(pattern, "/");
		String flagString="";
		String pat = "";
		if (pattern.startsWith("/")) {
			pattern = pattern.substring(1);
			int pos = pattern.lastIndexOf("/");
			if (pos == -1)	
				pat = pattern;
			else {
				pat=pattern.substring(0,pos);
				flagString=pattern.substring(pos + 1);
			}		
		}	
		else
			pat=pattern;
			
		
		int flags = 0;
		_patternString=pat; 
		if (flagString.length() > 0) {
			if (flagString.indexOf("i") != -1) {
				flags = addToFlag(c, "CASE_INSENSITIVE", flags);
				_javaScriptFlags += "i";	
			}	
			if (flagString.indexOf("m") != -1) {
				flags = addToFlag(c, "MULTILINE", flags);
				_javaScriptFlags += "m";	
			}	
			if (flagString.indexOf("g") != -1) 
				_javaScriptFlags += "g";	
			if (flagString.indexOf("t") != -1)
				_handleNull=HANDLE_NULL_RETURN_TRUE;
			else if (flagString.indexOf("f") != -1)
				_handleNull=HANDLE_NULL_RETURN_FALSE;			
		}

		Class types[] = { String.class, Integer.TYPE };

		Object args[] = { pat, new Integer(flags)};
		try {
			Method m = c.getMethod("compile", types);
			_pattern = m.invoke(null, args);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Error creating regular expression matcher", e, this);
		}
	}

	/**
	 * Returns true if the string passed matches the regular expression pattern
	 */
	public boolean match(String valueToMatch) {
		if (valueToMatch == null) {
			if (_handleNull == HANDLE_NULL_RETURN_FALSE)
				return false;
			else if (_handleNull == HANDLE_NULL_RETURN_TRUE)
				return true;	
		}
			
		Class types[] = { String.class };
		Class emptyTypes[] = new Class[0];
		Object emptyObjects[] = new Object[0];
		String args[] = { valueToMatch };
		if (valueToMatch == null) {
			String temp=null;
			args[0] = temp;
		}	

		try {
			Method ms[] = _pattern.getClass().getMethods();
			Method m = null;
			for (int i = 0; i < ms.length; i++) {
				if (ms[i].getName().equals("matcher") && ms[i].getParameterTypes().length == 1 && ms[i].getParameterTypes()[0].getName().endsWith("CharSequence")) {
					m = ms[i];
					break;
				}
			}
			Object matcher = m.invoke(_pattern, args);
			m = matcher.getClass().getMethod("matches", emptyTypes);
			Object ret = m.invoke(matcher, emptyObjects);
			if (ret instanceof Boolean)
				return ((Boolean) ret).booleanValue();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("match()", e, this);
		}
		return false;
	}

	private int addToFlag(Class c, String fieldName, int flags) {
		try {
			Field f = c.getField(fieldName);
			Integer fv = (Integer) f.get(null);
			flags = flags | fv.intValue();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("addToFlag()", e, this);
		}
		return flags;
	}
	/**
	 * @returns the regular expression pattern for this component not including the flags
	 */
	public String getPattern() {
		return _patternString;
	}
	
	/**
	 * Returns a flag string for this regular expression suitable for javascript
	 * 
	 */
	public String getJavaScriptFlage() {
		return _javaScriptFlags;	
	}	
	/**
	 * Returns how the matcher will handle null values
	 */
	public int getHandleNull() {
		return _handleNull;	
	}	

}
