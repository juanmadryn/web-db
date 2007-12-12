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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/TextTag.java $
//$Author: Fred $
//$Revision: 23 $
//$Modtime: 12/23/02 2:45p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.util.Util;

/**
 * This tag creates an HTMLText component
 */

public class TextTag extends BaseEmptyTag {
	private String _font;
	private String _text;
	private String _class;
	private String _theme;
	private String _dataSource;
	private String _fixhtml;
	private String _onmouseout;
	private String _onmouseover;
	private String _textlocalekey;
	private String _displayformatlocalekey;
	private String _displayformat;

	/**
	 * Creates the HtmlText component used by the tag
	 */

	public HtmlComponent createComponent() {
		HtmlText ht = null;
		if (!BaseTagHelper.isEmpty(_class)) {
			HtmlStyle newStyle = new HtmlStyle(_class, null, null, getHelper().getController());
			if (!BaseTagHelper.isEmpty(_theme)) {
				ht = new HtmlText(getName(), _text, newStyle, getHelper().getController(), _theme);
				ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
				if (_dataSource != null)
					ht.setDataSource(_dataSource);

				// setting whether to fixhtml characters
				if (_fixhtml != null)
					ht.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(getFixhtml(), true));
				if (_onmouseout != null)
					ht.setOnMouseOut(getOnmouseout());
				if (_onmouseover != null)
					ht.setOnMouseOver(getOnmouseover());
				if (_displayformatlocalekey != null)
					ht.setDisplayFormatLocaleKey(_displayformatlocalekey);
				if (_textlocalekey != null)
					ht.setTextLocaleKey(_textlocalekey);
				if (_displayformat != null)
					ht.setDisplayFormat(_displayformat);

				return ht;
			} else {
				ht = new HtmlText(getName(), _text, newStyle, getHelper().getController());
				ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
				if (_dataSource != null)
					ht.setDataSource(_dataSource);

				// setting whether to fixhtml characters
				if (_fixhtml != null)
					ht.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(getFixhtml(), true));
				if (_onmouseout != null)
					ht.setOnMouseOut(getOnmouseout());
				if (_onmouseover != null)
					ht.setOnMouseOver(getOnmouseover());
				if (_displayformatlocalekey != null)
					ht.setDisplayFormatLocaleKey(_displayformatlocalekey);
				if (_displayformat != null)
					ht.setDisplayFormat(_displayformat);
				if (_textlocalekey != null)
					ht.setTextLocaleKey(_textlocalekey);
				if (_displayformat != null)
					ht.setDisplayFormat(_displayformat);

				return ht;
			}
		} else {
			if (!BaseTagHelper.isEmpty(_theme)) {
				ht = new HtmlText(getName(), _text, _font, getHelper().getController(), _theme);
				ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
				if (_dataSource != null)
					ht.setDataSource(_dataSource);

				// setting whether to fixhtml characters
				if (_fixhtml != null)
					ht.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(getFixhtml(), true));
				if (_onmouseout != null)
					ht.setOnMouseOut(getOnmouseout());
				if (_onmouseover != null)
					ht.setOnMouseOver(getOnmouseover());
				if (_displayformatlocalekey != null)
					ht.setDisplayFormatLocaleKey(_displayformatlocalekey);
				if (_textlocalekey != null)
					ht.setTextLocaleKey(_textlocalekey);
				if (_displayformat != null)
					ht.setDisplayFormat(_displayformat);

				return ht;
			} else {
				ht = new HtmlText(getName(), _text, _font, getHelper().getController());
				ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
				if (_dataSource != null)
					ht.setDataSource(_dataSource);

				// setting whether to fixhtml characters
				if (_fixhtml != null)
					ht.setFixSpecialHtmlCharacters(BaseTagHelper.stringToBoolean(getFixhtml(), true));
				if (_onmouseout != null)
					ht.setOnMouseOut(getOnmouseout());
				if (_onmouseover != null)
					ht.setOnMouseOver(getOnmouseover());
				if (_displayformatlocalekey != null)
					ht.setDisplayFormatLocaleKey(_displayformatlocalekey);
				if (_textlocalekey != null)
					ht.setTextLocaleKey(_textlocalekey);
				if (_displayformat != null)
					ht.setDisplayFormat(_displayformat);

				return ht;
			}
		}
	}

	/**
	 * Gets the onmouseout property for the tag
	 */

	public java.lang.String getOnmouseout() {
		return _onmouseout;
	}

	/**
	 * Gets the onmouseover property for the tag
	 */
	public java.lang.String getOnmouseover() {
		return _onmouseover;
	}

	/**
	 * Get the Data Source the component should be bound to
	 */

	public String getDatasource() {
		return _dataSource;
	}

	/**
	 * Gets the font property for the tag
	 */

	public String getFont() {
		return _font;
	}

	/**
	 * Gets the text property for the tag
	 */

	public String getText() {
		return _text;
	}

	/**
	 * Gets the theme property for the tag
	 */

	public String getTheme() {
		return _theme;
	}

	public void release() {
		super.release();
		_font = null;
		_text = null;
		_class = null;
		_theme = null;
		_textlocalekey = null;
		_displayformatlocalekey = null;
		_displayformat = null;

	}

	/**
	 * Specify whether special html characters (<,>,&,; etc..) should be converted to Html Escape Sequences before being generated.
	 */
	public void setFixhtml(java.lang.String new_fixhtml) {
		_fixhtml = new_fixhtml;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
	 */
	public void setOnmouseout(java.lang.String new_onmouseout) {
		_onmouseout = new_onmouseout;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
	 */
	public void setOnmouseover(java.lang.String new_onmouseover) {
		_onmouseover = new_onmouseover;
	}

	/**
	 * Set the Data Source the component should be bound to
	 */

	public void setDatasource(String newValue) {
		_dataSource = newValue;
	}

	/**
	 * Sets the font property for the tag
	 */

	public void setFont(String font) {
		_font = font;
	}

	/**
	 * Sets the text property for the tag
	 */

	public void setText(String newValue) {
        // fc:12/23/02 added the replacement of &QUOT; to ' to allow for JSP Engines which have problems with sinqle quotes in tags. e.g. Silverstream
		_text = Util.replaceString(newValue,"&QUOT;","'",1,-1);
	}

	/**
	 * Sets the theme property for the tag
	 */

	public void setTheme(String newValue) {
		_theme = newValue;
	}

	/**
	 * Gets the fixhtml property for the tag
	 */

	public java.lang.String getFixhtml() {
		return _fixhtml;
	}

	/**
	 * Sets the class property for the tag
	 */

	public void setClassname(String val) {
		_class = val;
	}

    /**
	* Sets the textlocalekey property for the tag
	*/

	public void setTextlocalekey(String newValue) {
		_textlocalekey = newValue;
	}
    /**
	* Sets the displayformatlocalekey property for the tag
	*/

	public void setDisplayformatlocalekey(String newValue) {
		_displayformatlocalekey = newValue;
	}

    /**
	* Sets the displayformat property for the tag
	*/

	public void setDisplayformat(String newValue) {
		_displayformat = newValue;
	}
}
