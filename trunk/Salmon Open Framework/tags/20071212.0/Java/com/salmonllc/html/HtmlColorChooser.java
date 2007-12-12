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

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * Displays a box that allows the user to select a color
 */
public class HtmlColorChooser extends HtmlFormComponent {

	private int _noRows = 3;
	private String _font;
	private String _fontStartTag;
	private String _fontEndTag;
	private String _theme;
	/**
	 * Constructor for HtmlColorChooser.
	 * @param name
	 * @param p
	 */
	public HtmlColorChooser(String name, HtmlPage p) {
		super(name, p);
	}

	/**
	 * Constructor for HtmlColorChooser.
	 * @param name
	 * @param theme
	 * @param p
	 */
	public HtmlColorChooser(String name, String theme, HtmlPage p) {
		super(name, theme, p);
	}

	/**
	 * @see com.salmonllc.html.HtmlComponent#generateHTML(PrintWriter, int)
	 */
	public void generateHTML(PrintWriter p, int rowNo) throws Exception {
		if (!_visible)
			return;
		boolean checked = false;
		_value = getValue(rowNo, true);
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=\"0\">");
		int colsPerRow = COLORS.length / _noRows;
		for (int i = 0; i < _noRows; i++) {
			int rowStart = i * (colsPerRow);
			int rowEnd = rowStart + colsPerRow;
			sb.append("<tr height=\"10\">");
			for (int j = rowStart; j < rowEnd; j++) {
				sb.append("<td width=\"10\" bgcolor=\"");
				sb.append(COLORS[j]);
				sb.append("\"></td>");
			}
			sb.append("</tr><tr>");
			for (int j = rowStart; j < rowEnd; j++) {
				sb.append("<td><input type=\"radio\" name=\"");
				sb.append(getFullName());
				sb.append("\" value=\"");
				sb.append(COLORS[j]);
				sb.append("\"");
				if (COLORS[j].equals(_value)) {
					sb.append(" CHECKED");
					checked = true;
				}
				sb.append("></td>");
			}
			sb.append("</tr>");
		}
		sb.append("<tr><td colspan=\"");
		sb.append(colsPerRow);
		sb.append("\">");

		sb.append("<table border=\"0\"><tr><td>");
		sb.append(_fontStartTag);
		sb.append("Custom Color:");
		sb.append(_fontEndTag);
		sb.append("</td>");
		sb.append("<td><input type=\"text\" name=\"");
		sb.append(getFullName() + "custom");
		sb.append("\" value=\"");
		if (_value != null && !checked)
			sb.append(_value);
		sb.append("\"");
		sb.append(" onchange=\"");
		sb.append(getFullName());
		sb.append("deselect();\"");
		sb.append("></td>");
		sb.append("<script language=\"javascript\">");
		sb.append("function ");
		sb.append(getFullName());
		String form = getFormString();
		sb.append("deselect() {");
		sb.append("ele=");
		sb.append(form);
		sb.append("elements;");
		sb.append("for (i=0; i < ele.length; i++)");
		sb.append("  if (ele[i].name == '");
		sb.append(getFullName());
		sb.append("') ");
		sb.append("  ele[i].checked=false;");
		sb.append("}");
		sb.append("</script>");
		if (!checked && _value != null) {
			sb.append("<td height=\"10\" width=\"10\" bgcolor=\"");
			sb.append(_value);
			sb.append("\"></td>");
		}

		sb.append("</tr></table></td></tr></table>");
		p.println(sb.toString());
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		if (!getEnabled())
			return false;
		Object oldValue = _value;

		String name = getFullName();
		if (rowNo > -1) {
			name += "_" + rowNo;
			if (_dsBuff != null)
				oldValue = _dsBuff.getAny(rowNo, _dsColNo);
		} else {
			if (_dsBuff != null)
				oldValue = _dsBuff.getAny(_dsColNo);
		}

		String val[] = (String[]) parms.get(name);

		if (val == null) {
			val = (String[]) parms.get(name + "custom");
			if (val == null)
				_value = null;
			else
				_value = val[0];
		} else
			_value = val[0];

		if (!valuesEqual(oldValue, _value) && _value != null) {
			String s = null;
			if (oldValue != null)
				s = oldValue.toString();
			ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, translateColorValue(_value), rowNo, _dsColNo, _dsBuff);
			addEvent(e);
		}

		return false;
	} /**
	 * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
	 */
	public void setFont(String font) {
		_font = font;
		setTheme(_theme);
	}

	/**
	 * This method sets the end font tag for the component.
	 */
	public void setFontEndTag(String value) {
		_fontEndTag = value;
	}

	/**
	 * This method sets the start font tag for the component.
	 */
	public void setFontStartTag(String value) {
		_fontStartTag = value;
	}

	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {
		Props props = getPage().getPageProperties();

		if (_font != null) {
			_fontStartTag = props.getThemeProperty(theme, _font + Props.TAG_START);
			_fontEndTag = props.getThemeProperty(theme, _font + Props.TAG_END);
		} else {
			_fontStartTag = props.getThemeProperty(theme, HtmlText.FONT_DEFAULT + Props.TAG_START);
			_fontEndTag = props.getThemeProperty(theme, HtmlText.FONT_DEFAULT + Props.TAG_END);
		}

		_theme = theme;
	}

	/**
	 * This method returns the property theme for the component.
	 * @return
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * Returns the value in the component in the form red,green,blue (all decimal);
	 */
	public String getValueAsRGB() {
		String val = getValue();
		if (val == null)
			return null;
		String colVal = (String) COLOR_HASH.get(val.toLowerCase());
		if (colVal != null)
			val = colVal;
		if (val.startsWith("#"))
			val = val.substring(1);
		if (val.length() != 6)
			return null;
		String red = val.substring(0, 2);
		String green = val.substring(2, 4);
		String blue = val.substring(4, 6);

		try {
			val = "";
			val += Integer.parseInt(red, 16) + ",";
			val += Integer.parseInt(green, 16) + ",";
			val += Integer.parseInt(blue, 16);
		} catch (Exception e) {
			return null;
		}
		return val;
	}

	/**
	 * gets the value as an HTML String
	 */
	public String getValueAsHtml() {
		String val = getValue();
		if (val == null)
			return val;
		if (COLOR_HASH.containsKey(val.toLowerCase()))
			return val;
		if (val.startsWith("#"))
			val = val.substring(1);
		if (val.length() != 6)
			return null;
		String red = val.substring(0, 2);
		String green = val.substring(2, 4);
		String blue = val.substring(4, 6);
		try {
			Integer.parseInt(red, 16);
			Integer.parseInt(green, 16);
			Integer.parseInt(blue, 16);
			return "#" + val;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Takes a value in the form red,green, blue and converts it into HTML style values
	 */
	public void setRGBValue(String value) {
		try {
			StringTokenizer tok = new StringTokenizer(value, ",");
			String red = Integer.toHexString(Integer.parseInt(tok.nextToken().trim()));
            if (red.length() == 1)
                red = "0" + red;
			String green = Integer.toHexString(Integer.parseInt(tok.nextToken().trim()));
            if (green.length() == 1)
                green = "0" + green;
			String blue = Integer.toHexString(Integer.parseInt(tok.nextToken().trim()));
            if (blue.length() == 1)
                blue = "0" + blue;
			setValue(translateColorValue((red + green + blue).toLowerCase()));
		} catch (Exception ex) {
			MessageLog.writeErrorMessage("setRGBValue()", ex, this);
		}
	}

	/**
	 * Takes the color from a Font tag and sets it to the default for this component
	 */
	public void setFontValue(String font) {
        if (font == null) {
            setValue(null);
            return;
        }
		String val = HtmlFontChooser.getTagAttribute(font,"color");
        if (val == null) {
            setValue(null);
            return;
        }

		if ( val.startsWith("#"))
			val=val.substring(1);

		setValue(translateColorValue(val.toLowerCase()));
	}

     /**
	 * Takes the color from a CSS style and sets it to the default for this component
	 */
	public void setStyleValue(String style) {
        if (style == null) {
            setValue(null);
            return;
        }
		String val = HtmlFontChooser.getStyleAttribute(style,"color");
        if (val == null) {
            setValue(null);
            return;
        }

		if ( val.startsWith("#"))
			val=val.substring(1);

		setValue(translateColorValue(val.toLowerCase()));
	}
   	/**
	 * Sets the value from an HMTL color string
	 */
	public void setColorValue(String color) {
		if (color == null) {
            setValue(null);
            return;
        }

        if (color.startsWith("#"))
			color=color.substring(1);

		setValue(translateColorValue(color.toLowerCase()));
	}


	private String translateColorValue(String val) {
		String colVal = (String) COLOR_HASH.get(val);
		if (colVal != null) {
			for (int i = 0; i < COLORS.length; i++)
				if (colVal.equals(COLORS[i])) {
					val = COLORS[i];
					break;
				}
		}
		return val;
	}

	private static final String COLORS[] =
		{
			"ffcfce",
			"ff6666",
			"ff0000",
			"cc3333",
			"990000",
			"660033",
			"ffffce",
			"ffff9c",
			"ffff00",
			"cccc66",
			"999933",
			"666633",
			"ceffff",
			"9ccfff",
			"66cccc",
			"3399cc",
			"0000ff",
			"003399",
			"ffccff",
			"ff9aff",
			"cc66cc",
			"cc33cc",
			"993399",
			"840084",
			"ffcf9c",
			"ff9933",
			"ff6600",
			"cc6600",
			"996633",
			"663300",
			"ceff9c",
			"9cff63",
			"33ff33",
			"00cc00",
			"009900",
			"006633",
			"cecfff",
			"9c9aff",
			"9966ff",
			"6633cc",
			"663399",
			"330066",
			"ffffff",
			"cccccc",
			"999999",
			"666666",
			"333333",
			"000000" };
	private static final String COLOR_NAMES[] =
		{
			"Aliceblue",
			"F0F8FF",
			"Antiquewhite",
			"FAEBD7",
			"Aqua",
			"00FFFF",
			"Aquamarine",
			"7FFFD4",
			"Azure",
			"F0FFFF",
			"Beige",
			"F5F5DC",
			"Bisque",
			"FFE4C4",
			"Black",
			"000000",
			"Blanchedalmond",
			"FFEBCD",
			"Blue",
			"0000FF",
			"Blueviolet",
			"8A2BE2",
			"Brown",
			"A52A2A",
			"Burlywood",
			"DEB887",
			"Cadetblue",
			"5F9EA0",
			"Chartreuse",
			"7FFF00",
			"Chocolate",
			"D2691E",
			"Coral",
			"FF7F50",
			"Cornflowerblue",
			"6495ED",
			"Cornsilk",
			"FFF8DC",
			"Crimson",
			"DC143C",
			"Cyan",
			"00FFFF",
			"Darkblue",
			"00008B",
			"Darkcyan",
			"008B8B",
			"Darkgoldenrod",
			"B8860B",
			"Darkgray",
			"A9A9A9",
			"Darkgreen",
			"006400",
			"Darkkhaki",
			"BDB76B",
			"Darkmagenta",
			"8B008B",
			"Darkolivegreen",
			"556B2F",
			"Darkorange",
			"FF8C00",
			"Darkorchid",
			"9932CC",
			"Darkred",
			"8B0000",
			"Darksalmon",
			"E9967A",
			"Darkseagreen",
			"8FBC8F",
			"Darkslateblue",
			"483D8B",
			"Darkslategray",
			"2F4F4F",
			"Darkturquoise",
			"00CED1",
			"Darkviolet",
			"9400D3",
			"Deeppink",
			"FF1493",
			"Deepskyblue",
			"00BFFF",
			"Dimgray",
			"696969",
			"Dodgerblue",
			"1E90FF",
			"Firebrick",
			"B22222",
			"Floralwhite",
			"FFFAF0",
			"Forestgreen",
			"228B22",
			"Fuchsia",
			"FF00FF",
			"Gainsboro",
			"DCDCDC",
			"Ghostwhite",
			"F8F8FF",
			"Gold",
			"FFD700",
			"Goldenrod",
			"DAA520",
			"Gray",
			"808080",
			"Green",
			"008000",
			"Greenyellow",
			"ADFF2F",
			"Honeydew",
			"F0FFF0",
			"Hotpink",
			"FF69B4",
			"Indianred",
			"CD5C5C",
			"Indigo",
			"4B0082",
			"Ivory",
			"FFFFF0",
			"Khaki",
			"F0E68C",
			"Lavender",
			"E6E6FA",
			"Lavenderblush",
			"FFF0F5",
			"Lawngreen",
			"7CFC00",
			"Lemonchiffon",
			"FFFACD",
			"Lightblue",
			"ADD8E6",
			"Lightcoral",
			"F08080",
			"Lightcyan",
			"E0FFFF",
			"Lightgoldenrodyellow",
			"FAFAD2",
			"Lightgreen",
			"90EE90",
			"Lightgrey",
			"D3D3D3",
			"Lightpink",
			"FFB6C1",
			"Lightsalmon",
			"FFA07A",
			"Lightseagreen",
			"20B2AA",
			"Lightskyblue",
			"87CEFA",
			"Lightslategray",
			"778899",
			"Lightsteelblue",
			"B0C4DE",
			"Lightyellow",
			"FFFFE0",
			"Lime",
			"00FF00",
			"Limegreen",
			"32CD32",
			"Linen",
			"FAF0E6",
			"Magenta",
			"FF00FF",
			"Maroon",
			"800000",
			"Mediumauqamarine",
			"66CDAA",
			"Mediumblue",
			"0000CD",
			"Mediumorchid",
			"BA55D3",
			"Mediumpurple",
			"9370D8",
			"Mediumseagreen",
			"3CB371",
			"Mediumslateblue",
			"7B68EE",
			"Mediumspringgreen",
			"00FA9A",
			"Mediumturquoise",
			"48D1CC",
			"Mediumvioletred",
			"C71585",
			"Midnightblue",
			"191970",
			"Mintcream",
			"F5FFFA",
			"Mistyrose",
			"FFE4E1",
			"Moccasin",
			"FFE4B5",
			"Navajowhite",
			"FFDEAD",
			"Navy",
			"000080",
			"Oldlace",
			"FDF5E6",
			"Olive",
			"808000",
			"Olivedrab",
			"688E23",
			"Orange",
			"FFA500",
			"Orangered",
			"FF4500",
			"Orchid",
			"DA70D6",
			"Palegoldenrod",
			"EEE8AA",
			"Palegreen",
			"98FB98",
			"Paleturquoise",
			"AFEEEE",
			"Palevioletred",
			"D87093",
			"Papayawhip",
			"FFEFD5",
			"Peachpuff",
			"FFDAB9",
			"Peru",
			"CD853F",
			"Pink",
			"FFC0CB",
			"Plum",
			"DDA0DD",
			"Powderblue",
			"B0E0E6",
			"Purple",
			"800080",
			"Red",
			"FF0000",
			"Rosybrown",
			"BC8F8F",
			"Royalblue",
			"4169E1",
			"Saddlebrown",
			"8B4513",
			"Salmon",
			"FA8072",
			"Sandybrown",
			"F4A460",
			"Seagreen",
			"2E8B57",
			"Seashell",
			"FFF5EE",
			"Sienna",
			"A0522D",
			"Silver",
			"C0C0C0",
			"Skyblue",
			"87CEEB",
			"Slateblue",
			"6A5ACD",
			"Slategray",
			"708090",
			"Snow",
			"FFFAFA",
			"Springgreen",
			"00FF7F",
			"Steelblue",
			"4682B4",
			"Tan",
			"D2B48C",
			"Teal",
			"008080",
			"Thistle",
			"D8BFD8",
			"Tomato",
			"FF6347",
			"Turquoise",
			"40E0D0",
			"Violet",
			"EE82EE",
			"Wheat",
			"F5DEB3",
			"White",
			"FFFFFF",
			"Whitesmoke",
			"F5F5F5",
			"Yellow",
			"FFFF00",
			"YellowGreen",
			"9ACD32" };

	private static Hashtable COLOR_HASH = new Hashtable();
	static {
		for (int i = 0; i < COLOR_NAMES.length; i+=2)
			COLOR_HASH.put(COLOR_NAMES[i].toLowerCase(),COLOR_NAMES[i+1].toLowerCase());
	}
}
