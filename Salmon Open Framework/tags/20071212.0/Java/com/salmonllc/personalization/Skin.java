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

package com.salmonllc.personalization;


import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.util.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * A skin is a collection of attributes that are applied to the components of a page to create a personalized look
 */

public class Skin {
	private Class[] _stringTypes = { String.class };
	private Class[] _booleanTypes = { Boolean.TYPE };
	private Class[] _intTypes = { Integer.TYPE };
	private Class[] _colorUITypes = { ColorUIResource.class };
	private Class[] _colorTypes = { Color.class };
	private Class[] _fontUITypes = { FontUIResource.class };
	private Class[] _fontTypes = { Font.class };

	private static final String SWING_LOOK_AND_FEEL = "SwingLookAndFeel";
	private static final String SWING_METAL_THEME = "SwingMetalTheme";

	private Hashtable _classAttrib = new Hashtable();
	private Hashtable _instanceAttrib = new Hashtable();
	private Hashtable _wildCards = new Hashtable();
	private Properties _props = new Properties();
	private Vector _work = new Vector();

	/**
	 * Sets a class attribute in the Skin
	 * @param className The name of the class
	 * @param attributeName The name of the attribute in the class to set
	 * @param attributeValue The value to set the attribute to
	 */
	public void setClassAttribute(String className, String attributeName, String attributeValue) {
		putItemInList(_classAttrib, null, className, attributeName, attributeValue);
	}

	/**
	 * Gets the value of a class attribute in the Skin
	 * @param className The name of the class
	 * @param attributeName The name of the attribute in the class to set
	 */

	public String getClassAttribute(String className, String attributeName) {
		return getValue(_classAttrib, className, attributeName);
	}

	/**
	 * Gets all the attributes for a particular class in the skin
	 */
	public Attribute[] getClassAttributes(String className) {
		_work.setSize(0);
		getAttributes(_classAttrib, className, _work);
		Attribute ret[] = new Attribute[_work.size()];
		_work.copyInto(ret);
		return ret;
	}

	/**
	 * Sets an  intance attribute in the Skin
	 * @param instanceName The name of the component. It may contain a % sign in the first or last character to indicate a wild card.
	 * @param attributeName The name of the attribute in the class to set
	 * @param attributeValue The value to set the attribute to
	 */
	public void setInstanceAttribute(String instanceName, String attributeName, String attributeValue) {
		putItemInList(_instanceAttrib, _wildCards, instanceName, attributeName, attributeValue);
	}

	/**
	 * Gets the value of an instance attribute in the Skin
	 * @param instanceName The name of the class. It may contain a % sign in the first or last character to indicate a wild card.
	 * @param attributeName The name of the attribute in the class to set
	 */

	public String getInstanceAttribute(String instanceName, String attributeName) {
		return getValue(_instanceAttrib, instanceName, attributeName);
	}

	/**
	 * Gets all the attributes for a particular class in the skin
	 */
	public Attribute[] getInstanceAttributes(String instanceName) {
		_work.setSize(0);
		getAttributes(_instanceAttrib, instanceName, _work);
		Enumeration e = _wildCards.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (equalsWildCard(key, instanceName))
				getAttributes(_instanceAttrib, key, _work);
		}
		Attribute ret[] = new Attribute[_work.size()];
		_work.copyInto(ret);
		return ret;
	}

	private void getAttributes(Hashtable tab, String key, Vector v) {
		Attribute att = (Attribute) tab.get(key);
		while (att != null) {
			v.add(att);
			att = att.getNext();
		}
	}

	private String getValue(Hashtable tab, String key, String attributeName) {
		Attribute att = (Attribute) tab.get(key);
		if (att == null)
			return null;
		else {
			while (att != null) {
				if (att.getAttribute().equals(attributeName))
					return att.getValue();
                att = att.getNext();
			}
		}
		return null;
	}

	private void putItemInList(Hashtable tab, Hashtable wildCards, String key, String attributeName, String attributeValue) {
		Attribute att = (Attribute) tab.get(key);
		if (att == null) {
			att = new Attribute(attributeName, attributeValue);
			tab.put(key, att);
			if (wildCards != null)
				if (key.startsWith("%") || key.endsWith("%"))
					wildCards.put(key, att);
		} else {
			Attribute lastAtt = att;
			while (att != null) {
				lastAtt = att;
				if (att.getAttribute().equals(attributeName)) {
					att.setValue(attributeValue);
					return;
				}
				att = att.getNext();
			}
			att = new Attribute(attributeName, attributeValue);
			lastAtt.setNext(att);
		}
	}

	private static boolean equalsWildCard(String st1, String st2) {
		int end = st1.length() - 1;
		int st2Pos = 0;
		boolean endsWith = (st1.charAt(0) == '%');
		boolean startsWidth = (st1.charAt(end) == '%');

		if (startsWidth && endsWith) {
			int len = st2.length();
			char char1 = st1.charAt(1);
			for (int i = 0; i < len; i++) {
				if (st2.charAt(i) == char1) {
					//found first character, now look for the rest
					for (int j = 1; j < end && i < len; j++, i++) {
						if (st2.charAt(i) != st1.charAt(j)) {
							i--;
							break;
						} else if (j == (end - 1))
							return true;
					}
				}
			}
			return false;
		} else {
			//for ends with
			if (endsWith) {
				st2Pos = st2.length() - 1;
				for (int i = end; i > 0; i--) {
					if (st1.charAt(i) != st2.charAt(st2Pos))
						return false;
					st2Pos--;
					if (st2Pos < 0)
						return false;
				}
				return true;
			}
			//for starts with
			else if (startsWidth) {
				end--;
				st2Pos = 0;
				for (int i = 0; i < end; i++) {
					if (st1.charAt(i) != st2.charAt(st2Pos))
						return false;
					st2Pos++;
				}
				return true;
			}
		}

		return true;
	}

	/**
	 * Returns the properties object used by this skin
	 */
	public Properties getProperties() {
		return _props;
	}

	/**
	 * Adds a property to the properties object for this skin
	 */
	public void setProperty(String key, String value) {
		_props.put(key, value);
	}

	/**
	 * If the skin has a swing metal theme property set, return it otherwise return null
	 */
	public String getSwingMetalTheme() {
		Object o = _props.get(SWING_METAL_THEME);
		if (o == null)
			return null;
		else
			return (String) o;
	}

	/**
	 * If the skin has a swing look and feel property set, return it otherwise return null
	 */
	public String getSwingLookAndFeel() {
		Object o = _props.get(SWING_LOOK_AND_FEEL);
		if (o == null)
			return null;
		else
			return (String) o;
	}

	/**
	 * Apply class and instance attributes of the skin to the object
	 */
	public void applyAttributes(Object o) {
		Class c = o.getClass();
		Attribute at[] = getClassAttributes(o.getClass().getName());
		for (int i = 0; i < at.length; i++) {
			applyAttribute(at[i], o, c);
		}
		String name = null;
		try {
			if (o instanceof Component)
				name = ((Component) o).getName();
			else if (o instanceof SMetalTheme)
				name = "SMetalTheme";
            else if (o instanceof Nameable)
                name = ((Nameable)o).getName();
		} catch (Exception e) {}

		if (name != null) {
			at = getInstanceAttributes(name);
			for (int i = 0; i < at.length; i++) {
				applyAttribute(at[i], o, c);
			}
		}
	}

	private void applyAttribute(Attribute att, Object o, Class c) {
		StringBuffer sb = new StringBuffer(att.getAttribute().length() + 3);
		sb.append("set");
		sb.append(att.getAttribute());
		sb.setCharAt(3, Character.toUpperCase(att.getAttribute().charAt(0)));
		String name = sb.toString();

		Object ar[] = new Object[1];
		//first try integer
		try {
			Method m = c.getMethod(name, _intTypes);
			ar[0] = new Integer(Integer.parseInt(att.getValue()));
			m.invoke(o, ar);
			return;

		} catch (Exception e) {
		}

		//next try boolean
		try {
			Method m = c.getMethod(name, _booleanTypes);
			String val = att.getValue();
			if (val.equalsIgnoreCase("T") || val.equalsIgnoreCase("TRUE")) {
				ar[0] = Boolean.TRUE;
				m.invoke(o, ar);
				return;
			} else if (val.equalsIgnoreCase("F") || val.equalsIgnoreCase("FALSE")) {
				ar[0] = Boolean.FALSE;
				m.invoke(o, ar);
				return;
			}
		} catch (Exception e) {
		}

		//next try String
		try {
			Method m = c.getMethod(name, _stringTypes);
			ar[0] = att.getValue();
			m.invoke(o, ar);
			return;
		} catch (Exception e) {
		}

		//next try colors
		Color col = getColor(att.getValue());
		if (col != null) {
			try {
				Method m = c.getMethod(name, _colorUITypes);
				ar[0] = new ColorUIResource(col);
				m.invoke(o, ar);
				return;
			} catch (Exception e) {
			}

			try {
				Method m = c.getMethod(name, _colorTypes);
				ar[0] = col;
				m.invoke(o, ar);
				return;
			} catch (Exception e) {
			}
		}

		//Next try fonts
		Font ft = getFont(att.getValue());
		if (ft != null) {
			try {
				Method m = c.getMethod(name, _fontUITypes);
				ar[0] = new FontUIResource(ft);
				m.invoke(o, ar);
				return;
			} catch (Exception e) {
			}

			try {
				Method m = c.getMethod(name, _fontTypes);
				ar[0] = ft;
				m.invoke(o, ar);
				return;
			} catch (Exception e) {
			}
		}
	}

	private Color getColor(String col) {
		if (col.indexOf(",") == -1)
			return null;
		StringTokenizer tok = new StringTokenizer(col, ",");
		try {
			int red = Integer.parseInt(tok.nextToken().trim());
			int green = Integer.parseInt(tok.nextToken().trim());
			int blue = Integer.parseInt(tok.nextToken().trim());
			return new Color(red, green, blue);
		} catch (Exception e) {
			return null;
		}
	}

	private Font getFont(String font) {
		if (font.indexOf(",") == -1)
			return null;
		StringTokenizer tok = new StringTokenizer(font, ",");
		try {
			String ft = tok.nextToken();
			int sytle = getFontStyle(tok.nextToken());
			int size = Integer.parseInt(tok.nextToken().trim());
			return new Font(ft, sytle, size);
		} catch (Exception e) {
			return null;
		}
	}

	private int getFontStyle(String st) {
		int ret = 0;
		StringTokenizer tok = new StringTokenizer(st, "|");
		while (tok.hasMoreTokens()) {
			String style = tok.nextToken().trim();
			if (style.equalsIgnoreCase("BOLD"))
				ret = ret | Font.BOLD;
			else if (style.equalsIgnoreCase("ITALIC"))
				ret = ret | Font.ITALIC;
			else if (style.equalsIgnoreCase("PLAIN"))
				return Font.PLAIN;
		}
		return ret;
	}

	/**
	 * Return all attributes in the component. Class attributes are preceeded with "class." and instance attributes are preceeded with "instance."
	 */
	public Attribute[] getAllAttributes() {
		Vector v = new Vector();
		Enumeration e = _classAttrib.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Attribute comp = (Attribute) _classAttrib.get(key);
			while (comp != null) {
				String newKey = "class." + key + "." + comp.getAttribute();
				v.add(new Attribute(newKey, comp.getValue()));
				comp = comp.getNext();
			}
		}
		e = _instanceAttrib.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Attribute comp = (Attribute) _instanceAttrib.get(key);
			while (comp != null) {
				String newKey = "instance." + key + "." + comp.getAttribute();
				v.add(new Attribute(newKey, comp.getValue()));
				comp = comp.getNext();
			}
		}
		e = _props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			v.add(new Attribute(key, (String) _props.get(key)));
		}
		Attribute ret[] = new Attribute[v.size()];
		v.copyInto(ret);
		return ret;
	}
}
