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
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlText.java $
//$Author: Dan $
//$Revision: 32 $
//$Modtime: 8/20/03 12:53p $
/////////////////////////

import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreExpression;

/**
 * This type can be used to add text to your page.
 */
public class HtmlText extends HtmlComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5295930326448605220L;
	public static final String FONT_NONE = "None";
	public static final String FONT_DEFAULT = Props.FONT_DEFAULT;
	public static final String FONT_TEXT_EDIT = Props.FONT_TEXT_EDIT;
	public static final String FONT_BUTTON = Props.FONT_BUTTON;
	public static final String FONT_PAGE_HEADING = Props.FONT_PAGE_HEADING;
	public static final String FONT_SECTION_HEADING = Props.FONT_SECTION_HEADING;
	public static final String FONT_TABLE_HEADING = Props.FONT_TABLE_HEADING;
	public static final String FONT_COLUMN_CAPTION = Props.FONT_COLUMN_CAPTION;
	public static final String FONT_EMPHASIS = Props.FONT_EMPHASIS;
	public static final String FONT_ERROR = Props.FONT_ERROR;
	public static final String FONT_LINK = Props.FONT_LINK;
	public static final String FONT_LARGE_LINK = Props.FONT_LARGE_LINK;
	public static final String FONT_SMALL = Props.FONT_SMALL;
	public static final String FONT_DEFAULT_LABEL_TEXT = Props.FONT_DEFAULT_LABEL_TEXT;
	public static final String FONT_DEFAULT_DISPLAY_DATA = Props.FONT_DEFAULT_DISPLAY_DATA;

	public boolean _center = false;

	public static final int AGGREGATE_SUM = DataStoreEvaluator.AGGREGATE_SUM;
	public static final int AGGREGATE_COUNT = DataStoreEvaluator.AGGREGATE_COUNT;
	public static final int AGGREGATE_AVERAGE = DataStoreEvaluator.AGGREGATE_AVERAGE;

	private String _font;
	private String _fontStartTag;
	private String _fontEndTag;
	private String _text;
	private int _aggregateType = -1;

	private DataStoreEvaluator _dsEval = null;
	private boolean _fixSpecialHtml = true;
	private String _theme;
	private HtmlStyle _style;
	private String _onMouseOut;
	private String _onMouseOver;

	private String _textLocaleKey;
	private String _formatLocaleKey;
	private String _displayFormat;
	private boolean _updateLocale;

	/**
	 * Constructs an html text object for the page using the default font.
	 * @param text The text to place in the page.
	 * @param p The page to put the text in.
	 */
	public HtmlText(String text, HtmlPage p) {
		super("", p);
		_text = text;
		_font = FONT_DEFAULT;

		setTheme(null);

	}

	/**
	 * This method was created in VisualAge.
	 * @param text java.lang.String
	 * @param style com.salmonllc.html.HtmlStyle
	 * @param p com.salmonllc.html.HtmlPage
	 */
	public HtmlText(String text, HtmlStyle style, HtmlPage p) {
		super("", p);
		_text = text;
		_style = style;

		setTheme(null);

	}

	/**
	 * Constructs an html text object
	 * @param text The text to place in the page.
	 * @param font The font to use (See the constants at the top of the class).
	 * @param p The page to put the text in.
	 */
	public HtmlText(String text, String font, HtmlPage p) {
		super("", p);
		_text = text;
		_font = font;

		setTheme(null);
	}

	/**
	 * Constructs an html text object
	 * @param text The text to place in the page.
	 * @param font The font to use (See the constants at the top of the class).
	 * @param p The page to put the text in.
	 * @param theme The theme to use for loading properties
	 */
	public HtmlText(String text, String font, HtmlPage p, String theme) {
		super("", p);
		_text = text;
		_font = font;

		setTheme(theme);
	}

	/**
	 * Constructs an html text object
	 * @param name java.lang.String
	 * @param text java.lang.String
	 * @param style com.salmonllc.html.HtmlStyle
	 * @param p com.salmonllc.html.HtmlPage
	 */
	public HtmlText(String name, String text, HtmlStyle style, HtmlPage p) {
		super(name, p);
		_text = text;
		_style = style;
		setTheme(null);
	}

	/**
	 * Constructs an html text object
	 * @param name java.lang.String
	 * @param text java.lang.String
	 * @param style com.salmonllc.html.HtmlStyle
	 * @param p com.salmonllc.html.HtmlPage
	 * @param theme java.lang.String
	 */
	public HtmlText(String name, String text, HtmlStyle style, HtmlPage p, String theme) {
		super(name, p);
		_text = text;
		_style = style;

		setTheme(theme);
	}

	/**
	 * Constructs an html text object
	 * @param text The text to place in the page.
	 * @param font The font to use (See the constants at the top of the class).
	 * @param p The page to put the text in.
	 */
	public HtmlText(String name, String text, String font, HtmlPage p) {
		super(name, p);
		_text = text;
		_font = font;

		setTheme(null);
	}

	/**
	 * Constructs an html text object for the page
	 * @param text The text to place in the page.
	 * @param font The font to use (See the constants at the top of the class).
	 * @param p The page to put the text in.
	 * @param theme The theme to use for loading properties
	 */
	public HtmlText(String name, String text, String font, HtmlPage p, String theme) {
		super(name, p);
		_text = text;
		_font = font;

		setTheme(theme);
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
		if (!getVisible())
			return;

		processLocaleInfo();

		if (_dsEval != null) {
			if (_aggregateType != -1) {
				_text = _dsEval.evaluateAggregateFormat(_aggregateType);
			} else {
				if (rowNo > -1)
					_text = _dsEval.evaluateRowFormat(rowNo);
				else
					_text = _dsEval.evaluateRowFormat();
			}
		}

		String out = _text;

		if (_fixSpecialHtml)
			out = fixSpecialHTMLCharacters(out);

		//-----------------------------------------------------------------------------------------------
		// Author: Bruno Y. Decaudin
		// Date: 12/06/00 - 4:30pm
		// Feedback Item#890: table cell background incorrectly displayed in Netscape (non-issue in IE)
		// I added the 2nd part of the 'if' statement to solve the problem. However the '&nbsp;' creates
		// another problem in the HtmlTable component when a column heading is empty. Indeed the '&nbsp;'
		// causes an underlined space to appear that enables an unintended JavaScript clicksort for that
		// column. Hence the second part of the fix must be implemented in the aforementioned component.
		// The second part takes place in the HtmlDataTable component in the 'generateHtmlForBand' method.
		if ((out == null) || (out.trim().equals("")))
			out = "&nbsp;";
		//-----------------------------------------------------------------------------------------------
        StringBuffer sbOut=new StringBuffer();
        sbOut.append(out);

		if (_center) {
            sbOut.insert(0,"<CENTER>");
            sbOut.append("</CENTER>");
        }

//		if (_center)
//			out = "<CENTER>" + out + "</CENTER>";

		String style = null;
		if (_style != null)
			style = _style.getStyleName();
		else if (getClassName() != null)
			style = getClassName();

		if (style == null) {
            if (_fontStartTag != null) {
              sbOut.insert(0,_fontStartTag);
              sbOut.append(_fontEndTag);             
            }            
            p.print(sbOut.toString());
/*			if (_fontStartTag != null)
				p.print(_fontStartTag + out + _fontEndTag);
			else
				p.print(out);*/
		} else {
            StringBuffer sbSpan=new StringBuffer();
            sbSpan.append("<SPAN CLASS=\"");
            sbSpan.append(style);
            sbSpan.append('"');
            if (!getName().trim().equals("")) {
                sbSpan.append(" ID=\"");
                sbSpan.append(getName());
                sbSpan.append('"');
            }
            if (_onMouseOver != null && !_onMouseOver.trim().equals("")) {
                sbSpan.append(" ONMOUSEOVER=\"");
                sbSpan.append(_onMouseOver);
                sbSpan.append('"');
            }
            if (_onMouseOut != null && !_onMouseOut.trim().equals("")) {
                sbSpan.append(" ONMOUSEOUT=\"");
                sbSpan.append(_onMouseOut);
                sbSpan.append('"');
            }
            sbSpan.append('>');
            sbOut.insert(0,sbSpan);
            sbOut.append("</SPAN>");
            p.print(sbOut.toString());
/*			p.print("<SPAN CLASS=\"" + style + "\"");
			if (!getName().trim().equals(""))
				p.print(" ID=\"" + getName() + "\"");
			if (_onMouseOver != null && !_onMouseOver.trim().equals(""))
				p.print(" ONMOUSEOVER=\"" + _onMouseOver + "\"");
			if (_onMouseOut != null && !_onMouseOut.trim().equals(""))
				p.print(" ONMOUSEOUT=\"" + _onMouseOut + "\"");
			p.print(">" + out + "</SPAN>");*/
		}

	}

	public void generateHTML(java.io.PrintWriter p, int rowStart, int rowEnd) throws Exception {
		if (!getVisible())
			return;

		processLocaleInfo();

		if (_dsEval != null) {
			if (_aggregateType != -1) {
				_text = _dsEval.evaluateAggregateFormat(_aggregateType, rowStart, rowEnd);
			} else {
				if (rowStart > -1)
					_text = _dsEval.evaluateRowFormat(rowStart);
				else
					_text = _dsEval.evaluateRowFormat();
			}
		}

		String out=_text;

		if (_fixSpecialHtml)
			out = fixSpecialHTMLCharacters(out);

		//-----------------------------------------------------------------------------------------------
		// Author: Bruno Y. Decaudin
		// Date: 12/06/00 - 4:30pm
		// Feedback Item#890: table cell background incorrectly displayed in Netscape (non-issue in IE)
		// I added the 2nd part of the 'if' statement to solve the problem. However the '&nbsp;' creates
		// another problem in the HtmlTable component when a column heading is empty. Indeed the '&nbsp;'
		// causes an underlined space to appear that enables an unintended JavaScript clicksort for that
		// column. Hence the second part of the fix must be implemented in the aforementioned component.
		// The second part takes place in the HtmlDataTable component in the 'generateHtmlForBand' method.
		if ((out == null) || (out.trim().equals("")))
			out = "&nbsp;";
		//-----------------------------------------------------------------------------------------------
        StringBuffer sbOut=new StringBuffer();
        sbOut.append(out);

       
		if (_center) {
            sbOut.insert(0,"<CENTER>");
            sbOut.append("</CENTER>");
        }
//			out = "<CENTER>" + out + "</CENTER>";

		String style = null;
		if (_style != null)
			style = _style.getStyleName();
		else if (getClassName() != null)
			style = getClassName();

		if (style == null) {
			
			if (_fontStartTag != null) {
              sbOut.insert(0,_fontStartTag);
              sbOut.append(_fontEndTag);                
            }
			
		    p.print(sbOut.toString());
		} else {
				
            StringBuffer sbSpan=new StringBuffer();
            sbSpan.append("<SPAN CLASS=\"");
            sbSpan.append(style);
            sbSpan.append('"');
            if (!getName().trim().equals("")) {
                sbSpan.append(" ID=\"");
                sbSpan.append(getName());
                sbSpan.append('"');
            }
            if (_onMouseOver != null && !_onMouseOver.trim().equals("")) {
                sbSpan.append(" ONMOUSEOVER=\"");
                sbSpan.append(_onMouseOver);
                sbSpan.append('"');
            }
            if (_onMouseOut != null && !_onMouseOut.trim().equals("")) {
                sbSpan.append(" ONMOUSEOUT=\"");
                sbSpan.append(_onMouseOut);
                sbSpan.append('"');
            }
            sbSpan.append('>');
            sbOut.insert(0,sbSpan);
            sbOut.append("</SPAN>");
            p.print(sbOut.toString());
//			p.print("<SPAN CLASS=\"" + style + "\"");
//			if (!getName().trim().equals(""))
//				p.print(" ID=\"" + getName() + "\"");
//			if (_onMouseOver != null && !_onMouseOver.trim().equals(""))
//				p.print(" ONMOUSEOVER=\"" + _onMouseOver + "\"");
//			if (_onMouseOut != null && !_onMouseOut.trim().equals(""))
//				p.print(" ONMOUSEOUT=\"" + _onMouseOut + "\"");
//			p.print(">" + out + "</SPAN>");
		}
	}

	/**
	 * Use this method to find out if the that the text will be centered.
	 */
	public boolean getCenter() {
		return _center;
	}

	/**
	 * This method returns the DataStore Evaluator used to parse and evaluate the expression set in the setExpression method.
	 */
	public DataStoreEvaluator getExpressionEvaluator() {
		return _dsEval;
	}

	/**
	 * Returns whether special html characters (<,>,&,; etc..) should be converted to Html Escape Sequences before being generated.
	 */
	public boolean getFixSpecialHtmlCharacters() {
		return _fixSpecialHtml;
	}

	/**
	 * This method will return the font type used by this control.See the Constants at the top of the class for valid font types.
	 */
	public String getFont() {
		return _font;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over out of all the components
	 */
	public String getOnMouseOut() {
		return _onMouseOut;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over any component in the link
	 */
	public String getOnMouseOver() {
		return _onMouseOver;
	}

	/**
	 * Gets the CSS Style used to display this text
	 */
	public HtmlStyle getStyle() {
		return _style;
	}

	/**
	 * This method returns the text in the component.
	 */
	public String getText() {
		return _text;
	}

	/**
	 * This method returns the property theme for the component.
	 * @return
	 */
	public String getTheme() {
		return _theme;
	}

	public boolean processParms(java.util.Hashtable<String, Object> h, int row) {
		return false;
	}

	/**
	 * Use this method to indicate that the text should be centered.
	 */
	public void setCenter(boolean center) {
		_center = center;
	}

	/**
	 * Use this method to bind this component to an expression in a DataStore
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, int aggregateType) throws Exception {
		_aggregateType = aggregateType;
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
	 * @param format The patter to use to format the result
	 * @see com.salmonllc.sql.DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, int aggregateType, String format) throws Exception {
		_aggregateType = aggregateType;
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The resulting expression wil be formatted according to the pattern specified.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param format The patter to use to format the result
	 * @see com.salmonllc.sql.DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, String format) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStore
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression, int aggregateType) throws Exception {
		_aggregateType = aggregateType;
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The expression will be evaluated for all rows in the datastore.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param aggregateType valid values are AGGREGATE_SUM and AGGREGATE_COUNT
	 * @param format The patter to use to format the result
	 * @see com.salmonllc.sql.DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression, int aggregateType, String format) throws Exception {
		_aggregateType = aggregateType;
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStoreBuffer. The resulting expression wil be formatted according to the pattern specified.
	 * @param ds The DataStore to bind to.
	 * @param expression The expression to bind to.
	 * @param format The patter to use to format the result
	 * @see com.salmonllc.sql.DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression, String format) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * Specify whether special html characters (<,>,&,; etc..) should be converted to Html Escape Sequences before being generated.
	 */
	public void setFixSpecialHtmlCharacters(boolean fix) {
		_fixSpecialHtml = fix;
	}

	/**
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
	 * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
	 */
	public void setOnMouseOut(String onMouseOut) {
		_onMouseOut = onMouseOut;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
	 */
	public void setOnMouseOver(String onMouseOver) {
		_onMouseOver = onMouseOver;
	}

	/**
	 * Sets the CSS Style used to display this text
	 * @param style com.salmonllc.html.HtmlStyle
	 */
	public void setStyle(HtmlStyle style) {
		_style = style;
	}

	/**
	 * This method sets the text that the componnt will generate.
	 */
	public void setText(String text) {
		_text = text;
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
			_fontStartTag = props.getThemeProperty(theme, FONT_DEFAULT + Props.TAG_START);
			_fontEndTag = props.getThemeProperty(theme, FONT_DEFAULT + Props.TAG_END);
		}

		_theme = theme;
	}

	/**
	 * This method returns the text in the component.
	 */
	public String getText(int rowNo) {
		try {
			if (_dsEval != null) {
				if (_aggregateType != -1) {
					_text = _dsEval.evaluateAggregateFormat(_aggregateType);
				} else {
					if (rowNo > -1)
						_text = _dsEval.evaluateRowFormat(rowNo);
					else
						_text = _dsEval.evaluateRowFormat();
				}
			}
		} catch (Exception e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		}
		return _text;
	}

	/**
	 * Returns the Locale key used for display format
	 */
	public String getDisplayFormatLocaleKey() {
		return _formatLocaleKey;
	}

	/**
	 * Returns the Locale key used for the text of this component
	 */
	public String getTextLocaleKey() {
		return _textLocaleKey;
	}

	/**
	 * Sets the Locale key used for display format
	 */
	public void setDisplayFormatLocaleKey(String formatLocaleKey) {
		_formatLocaleKey = formatLocaleKey;
		_updateLocale = true;

	}

	/**
	 * Returns the Locale key used for text
	 */
	public void setTextLocaleKey(String textLocaleKey) {
		_textLocaleKey = textLocaleKey;
		_updateLocale = true;
	}

	/**
	 * Updates the text and format for the current local
	 */
	public void updateLocale() {
		_updateLocale = true;
	}

	private void processLocaleInfo() {
		if (_updateLocale) {
			_updateLocale = false;
			LanguagePreferences p = getPage().getLanguagePreferences();
			if (_textLocaleKey != null) {
				String newText = LanguageResourceFinder.getResource(getPage().getApplicationName(), _textLocaleKey, p);
				if (newText != null)
					setText(newText);
			}

			if (_displayFormat != null && _dsEval != null)
				_dsEval.setFormat(_displayFormat);

			if (_formatLocaleKey != null && _dsEval != null) {
				String newFormat = LanguageResourceFinder.getResource(getPage().getApplicationName(), _formatLocaleKey, p);
				if (newFormat != null)
					_dsEval.setFormat(newFormat);
			}
		}
	}
	/**
	 * Gets the default display format for the text
	 * @return String
	 */
	public String getDisplayFormat() {
		return _displayFormat;
	}

	/**
	 * Sets the default display format for the text. This only works for components bound to a datastore expression.
	 * @param displayFormat The displayFormat to set
	 */
	public void setDisplayFormat(String displayFormat) {
		if( displayFormat != null ) {                                 
			//Retrieve format string from page properties             
			Props props = getPage().getPageProperties();              
			String newFormat = props.getThemeProperty(_theme,displayFormat);      
			if( newFormat != null ) 
				displayFormat = newFormat;        
		} 
		_displayFormat = displayFormat;
		_updateLocale = true;
	}

}
