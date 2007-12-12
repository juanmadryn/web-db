package com.salmonllc.html;

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class generate an image in the html page that will submit the page when clicked.
 */
public class HtmlSubmitImage extends HtmlComponent implements ImageGenerator {
	public static final int ALIGN_NONE = 0;
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_TOP = 3;
	public static final int ALIGN_ABSMIDDLE = 4;
	public static final int ALIGN_ABSBOTTOM = 5;
	public static final int ALIGN_TEXTTOP = 6;
	public static final int ALIGN_MIDDLE = 7;
	public static final int ALIGN_BASELINE = 8;
	public static final int ALIGN_BOTTOM = 9;
	public static final String STYLE_RECTANGLE = "RECTANGLE";
	public static final String STYLE_OVAL = "OVAL";
	private static String _alignTypes[] = {"", "LEFT", "RIGHT", "TOP", "ABSMIDDLE", "ABSBOTTOM", "TEXTTOP", "MIDDLE", "BASELINE", "BOTTOM"};
	private Color _backgroundColor = Color.lightGray;
	private Color _bottomRightBorder = Color.gray;
	private Color _textColor = Color.black;
	private Color _topLeftBorder = Color.white;
	private Color _transparentColor = Color.magenta;
	private DataStoreEvaluator _altEval = null;
	private DataStoreEvaluator _dsEval = null;

	// Stuff for image generation
	private Font _font;
	private HtmlInlineFrame _iFrame = null;
	private Integer _tabIndex;
	private String _accessKey;
	private String _alt = "";
	private String _displayStyle = STYLE_RECTANGLE;
	private String _iFrameUrl = null;
	private String _imageURL;
	private String _onClick = "";
	private String _onmouseout;
	private String _onmouseover;
	private String _source = "";
	private String _style;
	private String _textLocaleKey;
	private String _theme = null;
	private String _urlLocaleKey;
	private Vector _listeners;
	private boolean _enabled = true;
	private boolean _generateImage = false;
	private boolean _updateLocale;
	private boolean _useCache = true;
	private int _align = ALIGN_NONE;
	private int _border = 0;
	private int _count;
	private int _hSpace = -1;
	private int _height;
	private int _rowNo = -1;
	private int _vSpace = -1;
	private int _width;
	private long _cacheKey = 0;
	private String _title;

	/**
	 * Constructs a new Submit Image. The image will be generated.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param text
	 *            The Text to put on the button.
	 * @param width
	 *            The width of the button in pixels
	 * @param height
	 *            The height of the button in pixels
	 * @param p
	 *            The page the button will be placed in
	 */
	public HtmlSubmitImage(String name, String text, int width, int height, HtmlPage p) {
		this(name, text, width, height, null, p);
	}

	/**
	 * Constructs a new Submit Image. The image will be generated.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param text
	 *            The Text to put on the button.
	 * @param width
	 *            The width of the button in pixels
	 * @param height
	 *            The height of the button in pixels
	 * @param theme
	 *            The theme to use for the button image
	 * @param p
	 *            The page the button will be placed in
	 */
	public HtmlSubmitImage(String name, String text, int width, int height, String theme, HtmlPage p) {
		super(name, p);
		_source = text;
		_width = width;
		_height = (height > 0) ? height : getPage().getPageProperties().getIntProperty(Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);
		_generateImage = true;
		p.registerImageGenerator(getFullName(), this);
		setTheme(theme);
	}

	/**
	 * Constructs a new Submit Image. The width of the button is determined by
	 * the width of the text.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param text
	 *            The Text to put on the button.
	 * @param height
	 *            The height of the button in pixels
	 * @param p
	 *            The page the button will be placed in
	 */
	public HtmlSubmitImage(String name, String text, int height, HtmlPage p) {
		this(name, text, -1, height, null, p);
	}

	/**
	 * Constructs a new Submit Image. The width of the button is determined by
	 * the size of the text.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param text
	 *            The Text to put on the button.
	 * @param height
	 *            The height of the button in pixels
	 * @param theme
	 *            The theme to use for the button image
	 * @param p
	 *            The page the button will be placed in
	 */
	public HtmlSubmitImage(String name, String text, int height, String theme, HtmlPage p) {
		this(name, text, -1, height, theme, p);
	}

	/**
	 * Constructs a new Submit Image.
	 * 
	 * @param name
	 *            Each component on a page must have a unique name.
	 * @param url
	 *            The url of the image to appear on the button.
	 * @param p
	 *            The page the button will be placed in
	 */
	public HtmlSubmitImage(String name, String url, HtmlPage p) {
		super(name, p);
		_source = url;
		_generateImage = false;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param string
	 *            the access key html attribute
	 */
	public void setAccessKey(String string) {
		_accessKey = string;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
	}

	/**
	 * This method sets the align property for the component
	 * 
	 * @param align
	 *            DOCUMENT ME!
	 */
	public void setAlign(int align) {
		_align = align;
	}

	/**
	 * This method sets the align property for the component using the HTML
	 * attribute values
	 * 
	 * @param align
	 *            DOCUMENT ME!
	 */
	public void setAlign(String align) {
		int iAlign = ALIGN_NONE;

		if (align != null) {
			align = align.toUpperCase();

			for (int i = 0; i < _alignTypes.length; i++) {
				if (_alignTypes[i].equals(align)) {
					/*
					 * srufle : Aug 6, 2004 10 : 26 : 09 AM iAlign = i + 1; To
					 * compensate for an off by one error I also added a "" to
					 * the beginning of the _alignTypes
					 */
					iAlign = i;

					break;
				}
			}
		}

		setAlign(iAlign);
	}

	/**
	 * This method gets the align property for the component
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getAlign() {
		return _align;
	}

	/**
	 * This method sets the tool tip for the image
	 * 
	 * @param alt
	 *            DOCUMENT ME!
	 */
	public void setAlt(String alt) {
		_alt = alt;
	}

	/**
	 * Returns the tool tip for the image
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getAlt() {
		return _alt;
	}

	/**
	 * Use this method to bind the alt property to an expression in a DataStore
	 * 
	 * @param ds
	 *            The DataStore to bind to.
	 * @param expression
	 *            The expression to bind to.
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 * 
	 * @see DataStoreEvaluator
	 */
	public void setAltExpression(DataStoreBuffer ds, String expression) throws Exception {
		_altEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * This method gets the DataStoreEvaluator being used for alt expressions.
	 * 
	 * @return DataStoreEvaluator
	 * 
	 * @see DataStoreEvaluator
	 */
	public DataStoreEvaluator getAltExpression() {
		return _altEval;
	}

	/**
	 * This method sets the background color for the generated image
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 */
	public void setBackgroundColor(Color c) {
		_backgroundColor = c;
	}

	/**
	 * This method sets the background color for the generated image
	 * 
	 * @param s
	 *            DOCUMENT ME!
	 */
	public void setBackgroundColor(String s) {
		Color c = Util.stringToColor(s);

		if (c != null) {
			setBackgroundColor(c);
		}
	}

	/**
	 * This method returns the background color if the image is generated
	 * 
	 * @return DOCUMENT ME!
	 */
	public Color getBackgroundColor() {
		return _backgroundColor;
	}

	/**
	 * This method sets the border property for the component
	 * 
	 * @param border
	 *            DOCUMENT ME!
	 */
	public void setBorder(int border) {
		_border = border;
	}

	/**
	 * This method gets the border property for the component
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getBorder() {
		return _border;
	}

	/**
	 * This method sets the bottom and right border color for the generated
	 * image
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 */
	public void setBottomRightColor(Color c) {
		_bottomRightBorder = c;
	}

	/**
	 * This method sets the bottom and right border color for the generated
	 * image
	 * 
	 * @param s
	 *            DOCUMENT ME!
	 */
	public void setBottomRightColor(String s) {
		Color c = Util.stringToColor(s);

		if (c != null) {
			setBottomRightColor(c);
		}
	}

	/**
	 * This method returns the bottom right border color if the image is
	 * generated
	 * 
	 * @return DOCUMENT ME!
	 */
	public Color getBottomRightColor() {
		return _bottomRightBorder;
	}

	public void setCacheKey(long key) {
		_cacheKey = key;
	}

	public long getCacheKey() {
		return _cacheKey;
	}

	/**
	 * This method sets the Display Style for a generated image button. Valid
	 * Values are STYLE_RECTANGLE and STYLE_OVAL
	 * 
	 * @param style
	 *            DOCUMENT ME!
	 */
	public void setDisplayStyle(String style) {
		_displayStyle = style;
	}

	/**
	 * This method gets the Display Style for a generated image button. Valid
	 * Values are STYLE_RECTANGLE and STYLE_OVAL
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getDisplayStyle() {
		return _displayStyle;
	}

	/**
	 * This method will set whether or not clicking on the image will do a
	 * submit.
	 * 
	 * @param enabled
	 *            DOCUMENT ME!
	 */
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	/**
	 * This method returns whether or not clicking on the image will do a
	 * submit.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean getEnabled() {
		return _enabled;
	}

	/**
	 * Use this method to bind this component to an expression in a DataStore
	 * 
	 * @param ds
	 *            The DataStore to bind to.
	 * @param expression
	 *            The expression to bind to.
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 * 
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a
	 * DataStoreBuffer. The resulting expression wil be formatted according to
	 * the pattern specified.
	 * 
	 * @param ds
	 *            The DataStore to bind to.
	 * @param expression
	 *            The expression to bind to.
	 * @param format
	 *            The patter to use to format the result
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 * 
	 * @see DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, DataStoreExpression expression, String format) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * Use this method to bind this component to an expression in a DataStore
	 * 
	 * @param ds
	 *            The DataStore to bind to.
	 * @param expression
	 *            The expression to bind to.
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 * 
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression);
	}

	/**
	 * Use this method to bind this component to an expression in a
	 * DataStoreBuffer. The resulting expression wil be formatted according to
	 * the pattern specified.
	 * 
	 * @param ds
	 *            The DataStore to bind to.
	 * @param expression
	 *            The expression to bind to.
	 * @param format
	 *            The patter to use to format the result
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 * 
	 * @see DataStore#setFormat
	 * @see DataStoreEvaluator
	 */
	public void setExpression(DataStoreBuffer ds, String expression, String format) throws Exception {
		_dsEval = new DataStoreEvaluator(ds, expression, format);
	}

	/**
	 * This method gets the DataStoreEvaluator being used for expressions.
	 * 
	 * @return DataStoreEvaluator
	 * 
	 * @see DataStoreEvaluator
	 */
	public DataStoreEvaluator getExpression() {
		return _dsEval;
	}

	/**
	 * This method sets the font used for dynamically generated images.
	 * 
	 * @param f
	 *            DOCUMENT ME!
	 */
	public void setFont(Font f) {
		_font = f;
	}

	/**
	 * This method sets the font used for dynamically generated images.
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * @param style
	 *            DOCUMENT ME!
	 * @param size
	 *            DOCUMENT ME!
	 */
	public void setFont(String name, int style, int size) {
		if ((name == null) && (style == -1) && (size == -1)) {
			return;
		}

		if (name == null) {
			name = "Helvetica";
		}

		if ((style < 0) || (style > 3)) {
			style = Font.PLAIN;
		}

		if (size == -1) {
			size = 12;
		}

		_font = new Font(name, style, size);
	}

	/**
	 * This method returns the font used for dynamically generated images.
	 * 
	 * @return DOCUMENT ME!
	 */
	public Font getFont() {
		return _font;
	}

	/**
	 * This method sets the width in pixels for the generated image
	 * 
	 * @param height
	 *            DOCUMENT ME!
	 */
	public void setHeight(int height) {
		_height = height;
	}

	/**
	 * This method gets the height in pixels for the generated image
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * set the hspace.
	 * 
	 * @param hSpace
	 *            DOCUMENT ME!
	 */
	public void setHorizontalSpace(int hSpace) {
		_hSpace = hSpace;
	}

	/**
	 * get the Horizontal Space
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getHorizontalSpace() {
		return _hSpace;
	}

	/**
	 * Use this method if you want the button to submit only a layer of the page
	 * and not the whole page
	 * 
	 * @param iFrame
	 *            The IFrame component that should be loaded
	 */
	public void setIFrameSubmit(HtmlInlineFrame iFrame) {
		_iFrame = iFrame;
	}

	/**
	 * This method sets the javascript that will be executed when the button is
	 * clicked.
	 * 
	 * @param onClick
	 *            DOCUMENT ME!
	 */
	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	/**
	 * This method gets the javascript that will be executed when the button is
	 * clicked.
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getOnClick() {
		return _onClick;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param string
	 *            Updates the onmouseout event string
	 */
	public void setOnMouseOut(String string) {
		_onmouseout = string;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param string
	 *            Updates the onmouseover event string
	 */
	public void setOnMouseOver(String string) {
		_onmouseover = string;
	}

	/**
	 * Set the html style attribute
	 * 
	 * @param style
	 */
	public void setStyle(String style) {
		_style = style;
	}

	/**
	 * Get the html style attribute
	 * 
	 * @return
	 */
	public String getStyle() {
		return _style;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param val
	 *            the tab index html attribute. You can also pass
	 *            TAB_INDEX_DEFAULT to use the default tab index for the
	 *            component or TAB_INDEX_NONE to keep this component from being
	 *            tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1) {
			_tabIndex = null;
		} else {
			_tabIndex = new Integer(val);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null) {
			return -1;
		}

		return _tabIndex.intValue();
	}

	/**
	 * This method sets the text for the for the dynamically generated image.
	 * 
	 * @param text
	 *            DOCUMENT ME!
	 */
	public void setText(String text) {
		if (_generateImage) {
			_source = text;
			_useCache = false;
		}
	}

	/**
	 * This method returns the text for the button
	 * 
	 * @return java.lang.String
	 */
	public String getText() {
		if (_generateImage) {
			return _source;
		} else {
			return null;
		}
	}

	/**
	 * This method sets the text color for the generated image
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 */
	public void setTextColor(Color c) {
		_textColor = c;
	}

	/**
	 * This method sets the text color for the generated image
	 * 
	 * @param s
	 *            DOCUMENT ME!
	 */
	public void setTextColor(String s) {
		Color c = Util.stringToColor(s);

		if (c != null) {
			setTextColor(c);
		}
	}

	/**
	 * This method returns the text color if the image is generated
	 * 
	 * @return DOCUMENT ME!
	 */
	public Color getTextColor() {
		return _textColor;
	}

	/**
	 * Returns the Locale key used for text
	 * 
	 * @param textLocaleKey
	 *            DOCUMENT ME!
	 */
	public void setTextLocaleKey(String textLocaleKey) {
		_textLocaleKey = textLocaleKey;
		_updateLocale = true;
	}

	/**
	 * Returns the Locale key used for the text of this component
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getTextLocaleKey() {
		return _textLocaleKey;
	}

	/**
	 * This method sets the property theme for the component.
	 * 
	 * @param theme
	 *            The theme to use.
	 */
	public void setTheme(String theme) {
		Props props = getPage().getPageProperties();

		String font = props.getThemeProperty(theme, Props.SUBMIT_IMAGE_FONT_FACE);
		int fontStyle = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_STYLE);
		int size = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_SIZE);
		setFont(font, fontStyle, size);

		Color c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TEXT_COLOR);

		if (c != null) {
			_textColor = c;
		}

		c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BACKGROUND_COLOR);

		if (c != null) {
			_backgroundColor = c;
		}

		if (_height == -1) {
			_height = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);
		}

		c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TOPLEFT_BORDER_COLOR);

		if (c != null) {
			_topLeftBorder = c;
		}

		c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BOTTOMRIGHT_BORDER_COLOR);

		if (c != null) {
			_bottomRightBorder = c;
		}

		c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TRANSPARENT_COLOR);

		if (c != null) {
			_transparentColor = c;
		}

		String style = props.getThemeProperty(theme, Props.SUBMIT_IMAGE_STYLE);

		if (style != null) {
			if (style.toUpperCase().equals(STYLE_OVAL)) {
				_displayStyle = STYLE_OVAL;
			} else {
				_displayStyle = STYLE_RECTANGLE;
			}
		}

		_theme = theme;
	}

	/**
	 * This method returns the property theme for the component.
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * This method sets the top and left border color for the generated image
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 */
	public void setTopLeftColor(Color c) {
		_topLeftBorder = c;
	}

	/**
	 * This method sets the top and left border color for the generated image
	 * 
	 * @param s
	 *            DOCUMENT ME!
	 */
	public void setTopLeftColor(String s) {
		Color c = Util.stringToColor(s);

		if (c != null) {
			setTopLeftColor(c);
		}
	}

	/**
	 * This method returns the top and left border color if the image is
	 * generated
	 * 
	 * @return DOCUMENT ME!
	 */
	public Color getTopLeftColor() {
		return _topLeftBorder;
	}

	/**
	 * This method sets the color that represents tranparent for the generated
	 * image
	 * 
	 * @param c
	 *            DOCUMENT ME!
	 */
	public void setTransparentColor(Color c) {
		_transparentColor = c;
	}

	/**
	 * This method sets the color that represents tranparent for the generated
	 * image
	 * 
	 * @param s
	 *            DOCUMENT ME!
	 */
	public void setTransparentColor(String s) {
		Color c = Util.stringToColor(s);

		if (c != null) {
			setTransparentColor(c);
		}
	}

	/**
	 * This method returns the color that will be represent transparent if the
	 * image is generated
	 * 
	 * @return DOCUMENT ME!
	 */
	public Color getTransparentColor() {
		return _transparentColor;
	}

	/**
	 * This method sets the url for the image that will be displayed on the
	 * button in the browser.
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 */
	public void setURL(String name) {
		if (!_generateImage) {
			_source = name;
		}
	}

	/**
	 * This method returns the url for the image that will be displayed on the
	 * button in the browser.
	 * 
	 * @return java.lang.String
	 */
	public String getURL() {
		if (!_generateImage) {
			return _source;
		} else {
			return null;
		}
	}

	/**
	 * Sets the Locale key used for the image URL
	 * 
	 * @param urlLocaleKey
	 *            DOCUMENT ME!
	 */
	public void setURLLocaleKey(String urlLocaleKey) {
		_urlLocaleKey = urlLocaleKey;
		_updateLocale = true;
	}

	/**
	 * Returns the Locale key used for the source url
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getURLLocaleKey() {
		return _urlLocaleKey;
	}

	public void setUseCache(boolean useCache) {
		_useCache = useCache;
	}

	/**
	 * If the image is dynamically generated, this method will indicate whether
	 * or not the Objectstore should cache it.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean getUseCache() {
		return _useCache;
	}

	/**
	 * set the vspace.
	 * 
	 * @param vSpace
	 *            DOCUMENT ME!
	 */
	public void setVerticalSpace(int vSpace) {
		_vSpace = vSpace;
	}

	/**
	 * get the Vertical Space
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getVerticalSpace() {
		return _vSpace;
	}

	/**
	 * This method sets the width in pixels for the generated image
	 * 
	 * @param width
	 *            DOCUMENT ME!
	 */
	public void setWidth(int width) {
		_width = width;
	}

	/**
	 * This method gets the width in pixels for the generated image
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * This method adds a listener the will be notified when this image causes
	 * the page to be submitted.
	 * 
	 * @param l
	 *            The listener to add.
	 */
	public void addSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			_listeners = new Vector();
		}

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				return;
			}
		}

		_listeners.addElement(l);
	}

	public boolean executeEvent(int type) throws Exception {
		if (type != EVENT_SUBMIT) {
			return true;
		}

		if (_listeners == null) {
			return true;
		}

		SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);

		for (int i = 0; i < _listeners.size(); i++) {
			SubmitListener l = (SubmitListener) _listeners.elementAt(i);
			e.setNextListener((_listeners.size() > (i + 1)) ? _listeners.elementAt(i + 1) : null);

			if (!l.submitPerformed(e)) {
				return false;
			}
		}

		return true;
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
		if (!getVisible()) {
			return;
		}

		processLocaleInfo();

		//
		String name = getFullName();

		if (rowNo > -1) {
			name += ("_" + rowNo);
		}

		//
		String jScript = "";
		String out = "";

		if ((_iFrame != null) && (_iFrameUrl == null)) {
			String uName = getPage().getClass().getName();
			int pos = uName.lastIndexOf(".");

			if (pos > -1) {
				uName = uName.substring(pos + 1);
			}

			uName += ("-" + _iFrame.getName() + "FrameComponent");
			_iFrameUrl = uName;
		}

		if (_dsEval != null) {
			if (rowNo > -1) {
				_source = _dsEval.evaluateRowFormat(rowNo);
			} else {
				_source = _dsEval.evaluateRowFormat();
			}
		}

		String source = _source;
		source = translateSiteMapURL(source);

		if (_generateImage) {
			// sr 11-01-2000 this replaces the call to generateImageURL() in the
			// constructor
			if (_imageURL == null) {
				_imageURL = generateImageURL();
			}

			source = _imageURL + "default.dgif";
			source = translateSiteMapURL(source);

			if (!_useCache) {
				source += ("?ct=" + _count++);
			}
		}

		// ALT
		String alt = _alt;

		if (_altEval != null) {
			if (rowNo > -1) {
				alt = _altEval.evaluateRowFormat(rowNo);
			} else {
				alt = _altEval.evaluateRowFormat();
			}
		}

		String onClick = _onClick;
		String valScript = HtmlValidatorText.generateOnClickJavaScriptForButtons(_onClick, _listeners, getFullName());

		if (valScript != null) {
			out += valScript;
			onClick = "return " + HtmlValidatorText.generateOnClickJavaScriptFunctionName(getFullName()) + ";";
		}

		//
		if (!_enabled) {
			if (_generateImage) {
				source = _imageURL + "disabled.dgif";
				source = translateSiteMapURL(source);
			}

			out += ("<IMG NAME=\"" + getFullName() + "\" BORDER=\"" + _border + "\" NAME=\"" + name + "\" SRC=\"" + source + "\"");

			if (!Util.isNull(alt) && !Util.isEmpty(alt)) {
				out += (" ALT=\"" + alt + "\"");
			}

			// HSPACE
			if (_hSpace > -1) {
				out += (" HSPACE=\"" + _hSpace + "\"");
			}

			// VSPACE
			if (_vSpace > -1) {
				out += (" VSPACE=\"" + _vSpace + "\"");
			}

			// ALIGN
			if ((_align > ALIGN_NONE) && (_align < _alignTypes.length)) {
				out += (" ALIGN=\"" + _alignTypes[_align] + "\"");
			}

			if (Util.isFilled(getStyle())) {
				out += (" style=\"" + getStyle() + "\"");
			}

			//onmouseover EVENT
			if (_onmouseover != null) {
				out += (" ONMOUSEOVER=\"" + _onmouseover + "\"");
			}

			//onmouseout EVENT
			if (_onmouseout != null) {
				out += (" ONMOUSEOUT=\"" + _onmouseout + "\"");
			}

			if (_tabIndex != null) {
				out += (" tabindex=\"" + _tabIndex + "\"");
			}

			if (_accessKey != null) {
				out += (" accesskey=\"" + _accessKey + "\"");
			}

			out += ">";
		} else {
			if ((_iFrame == null) || ((jScript = getPage().getSubmitJavaScript(this, _iFrameUrl, _iFrame)) == null)) {
				out += ("<INPUT TYPE=\"IMAGE\" BORDER=\"" + _border + "\" NAME=\"" + name + "\"");

				// ALT
				if (!Util.isNull(alt) && !Util.isEmpty(alt)) {
					out += (" ALT=\"" + alt + "\"");
				}

				// HSPACE
				if (_hSpace > -1) {
					out += (" HSPACE=\"" + _hSpace + "\"");
				}

				// VSPACE
				if (_vSpace > -1) {
					out += (" VSPACE=\"" + _vSpace + "\"");
				}

				// ALIGN
				if ((_align > ALIGN_NONE) && (_align < _alignTypes.length)) {
					out += (" ALIGN=\"" + _alignTypes[_align] + "\"");
				}

				// STYLE
				if (Util.isFilled(getStyle())) {
					out += (" style=\"" + getStyle() + "\"");
				}

				//onmouseover EVENT
				if (_onmouseover != null) {
					out += (" ONMOUSEOVER=\"" + _onmouseover + "\"");
				}

				//onmouseout EVENT
				if (_onmouseout != null) {
					out += (" ONMOUSEOUT=\"" + _onmouseout + "\"");
				}

				if (_tabIndex != null) {
					out += (" tabindex=\"" + _tabIndex + "\"");
				}

				if (_accessKey != null) {
					out += (" accesskey=\"" + _accessKey + "\"");
				}

				// title
				if (_title != null)
					out += " TITLE=\"" + _title + "\"";

				out += (" SRC=\"" + source + "\"" + (((onClick != null) && !onClick.trim().equals("")) ? (" onClick=\"" + onClick + "\"") : "") + ">");
			} else {
				out += ("<A NAME=\"" + getFullName() + "LINKANCHOR\" HREF=\"javascript:" + jScript + "\" >");
				out += ("<IMG NAME=\"" + getFullName() + "\" BORDER=\"" + _border + "\" NAME=\"" + name + "\" SRC=\"" + source + "\"");

				// ALT
				if (!Util.isNull(alt) && !Util.isEmpty(alt)) {
					out += (" ALT=\"" + alt + "\"");
				}

				// HSPACE
				if (_hSpace > -1) {
					out += (" HSPACE=\"" + _hSpace + "\"");
				}

				// VSPACE
				if (_vSpace > -1) {
					out += (" VSPACE=\"" + _vSpace + "\"");
				}

				// ALIGN
				if ((_align > ALIGN_NONE) && (_align < _alignTypes.length)) {
					out += (" ALIGN=\"" + _alignTypes[_align] + "\"");
				}

				//			STYLE
				if (Util.isFilled(getStyle())) {
					out += (" style=\"" + getStyle() + "\"");
				}

				if (_tabIndex != null) {
					out += (" tabindex=\"" + _tabIndex + "\"");
				}

				if (_accessKey != null) {
					out += (" accesskey=\"" + _accessKey + "\"");
				}

				//onmouseover EVENT
				if (_onmouseover != null) {
					out += (" ONMOUSEOVER=\"" + _onmouseover + "\"");
				}

				//onmouseout EVENT
				if (_onmouseout != null) {
					out += (" ONMOUSEOUT=\"" + _onmouseout + "\"");
				}

				out += "></A>";
			}
		}

		p.print(out);
	}

	public void generateImage(String img, OutputStream out) throws IOException {
		//create an image to draw
		ImageFactory fact = new ImageFactory();
		Image i = null;

		if (_displayStyle.equals(STYLE_OVAL)) {
			if (_width > 0) {
				i = fact.createOvalButton(_width, _height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
			} else {
				i = fact.createOvalButton(_height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
			}
		} else if (_width > 0) {
			i = fact.createRectangleButton(_width, _height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);
		} else {
			i = fact.createRectangleButton(_height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);
		}

		GifEncoder g = new GifEncoder(i, out, true, _transparentColor);
		g.encode();
	}

	/**
	 * This method inserts a listener at the beginning of the the listener list.
	 * It will be notified first when this button causes the page to be
	 * submitted.
	 * 
	 * @param l
	 *            The listener to add.
	 */
	public void insertSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			_listeners = new Vector();
		}

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				return;
			}
		}

		_listeners.insertElementAt(l, 0);
	}

	public boolean processParms(Hashtable parms, int rowNo) throws Exception {
		String name = getFullName();

		if (rowNo > -1) {
			name += ("_" + rowNo);
		}

		if (parms.get(name + ".x") != null) {
			_rowNo = rowNo;

			return true;
		}

		return false;
	}

	/**
	 * This method removes a listener from the list that will be notified if
	 * this button causes the page to be submitted.
	 * 
	 * @param l
	 *            The listener to remove.
	 */
	public void removeSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			return;
		}

		for (int i = 0; i < _listeners.size(); i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				_listeners.removeElementAt(i);

				return;
			}
		}
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

				if (newText != null) {
					setText(newText);
				}
			}

			if (_urlLocaleKey != null) {
				String newURL = LanguageResourceFinder.getResource(getPage().getApplicationName(), _urlLocaleKey, p);

				if (newURL != null) {
					setURL(newURL);
				}
			}
		}
	}

	/**
	 * @return Returns the _title.
	 */
	public String getTitle() {
		return _title;
	}
	/**
	 * @param string
	 *            The _title to set.
	 */
	public void setTitle(String string) {
		_title = string;
	}
}