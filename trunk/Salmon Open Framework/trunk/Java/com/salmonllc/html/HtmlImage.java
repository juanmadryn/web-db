package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlImage.java $
//$Author: Dan $
//$Revision: 31 $
//$Modtime: 10/30/04 11:24a $
/////////////////////////

import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.awt.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This type can be used to add an image to your page.
 */
public class HtmlImage extends HtmlComponent implements ImageGenerator {
    public static final String ALIGN_LEFT = "LEFT";
    public static final String ALIGN_RIGHT = "RIGHT";
    public static final String ALIGN_TOP = "CENTER";
    public static final String ALIGN_ABSMIDDLE = "ABSMIDDLE";
    public static final String ALIGN_ABSBOTTOM = "ABSBOTTOM";
    public static final String VALIGN_TEXTTOP = "TEXTTOP";
    public static final String VALIGN_MIDDLE = "MIDDLE";
    public static final String VALIGN_BASELINE = "BASELINE";
    public static final String ALIGN_BOTTOM = "BOTTOM";
    public static final String ALIGN_NONE = "";
    //
    public static final int SIZE_PIXELS = 0;
    public static final int SIZE_PERCENT = 1;
    //
    public static final String STYLE_RECTANGLE = "RECTANGLE";
    public static final String STYLE_OVAL = "OVAL";
    public static final String STYLE_PIXEL = "PIXEL";
    //
    private String _source = "";
    private String _alt = "";
    private String _align = ALIGN_NONE;
    //
    private int _width = -1;
    private int _height = -1;
    private int _border = 0;
    private int _hSpace = -1;
    private int _vSpace = -1;
    //
    private int _widthSizeOption = SIZE_PIXELS;
    private int _heightSizeOption = SIZE_PIXELS;
    //
    private Vector _areaMaps;
    private HtmlImageMapArea _submit = null;
    //
    private String _title;

    // Stuff for image generation
    private Font _font;
    private Color _textColor = Color.black;
    private Color _backgroundColor = Color.lightGray;
    private Color _topLeftBorder = Color.white;
    private Color _bottomRightBorder = Color.gray;
    private Color _transparentColor = Color.magenta;
    private String _style = STYLE_RECTANGLE;
    private String _imageURL;
    //
    private boolean _generateImage = false;
    private String _theme = null;
    private boolean _useCache = true;
    private long _cacheKey = 0;
    private int _count = 0;
    private boolean _enabled = true;
    private String _onLoad;

    private DataStoreEvaluator _dsEval = null;
    private DataStoreEvaluator _altEval = null;

    private String _sourceLocaleKey = null;
    private String _altLocaleKey = null;
    private String _textLocaleKey = null;
    private boolean _updateLocale = false;
    private String _useMap;

    /**
     * Constructs an new Image
     * @param source - image that is being used.
     * @param p The page the image will be placed in
     */
    public HtmlImage(String source, HtmlPage p) {
        this("", source, p);
    }

    /**
     * Constructs a new Image.
     * @param name Each component on a page must have a unique name.
     * @param text The Text to put on the button.
     * @param width The width of the button in pixels
     * @param height The height of the button in pixels
     * @param p The page the image will be placed in
     */
    public HtmlImage(String name, String text, int width, int height, HtmlPage p) {
        this(name, text, width, height, null, p);
    }

    /**
     * Constructs a new Image.
     * @param name Each component on a page must have a unique name.
     * @param text The Text to put on the button.
     * @param width The width of the button in pixels
     * @param height The height of the button in pixels
     * @param theme The theme to use for the button image
     * @param p The page the image will be placed in
     */
    public HtmlImage(String name, String text, int width, int height, String theme, HtmlPage p) {
        super(name, p);
        _source = text;
        _width = width;
        _height = height > 0 ? height : getPage().getPageProperties().getIntProperty(Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);
        _generateImage = true;
        p.registerImageGenerator(getFullName(), this);
        setTheme(theme);
    }

    /**
     * Constructs a new Image. The width of the Image is determined by the size of the text.
     * @param name Each component on a page must have a unique name.
     * @param text The Text to put on the button.
     * @param height The height of the button in pixels
     * @param p The page the button will be placed in
     */
    public HtmlImage(String name, String text, int height, HtmlPage p) {
        this(name, text, -1, height, null, p);
    }

    /**
     * Constructs a new Image. The width of the Image is determined by the size of the text.
     * @param name Each component on a page must have a unique name.
     * @param text The Text to put on the button.
     * @param height The height of the button in pixels
     * @param theme The theme to use for the button image
     * @param p The page the button will be placed in
     */
    public HtmlImage(String name, String text, int height, String theme, HtmlPage p) {
        this(name, text, -1, height, theme, p);
    }

    /**
     * Constructs an image object for the page
     */
    public HtmlImage(String name, String source, HtmlPage p) {
        super(name, p);
        _source = source;
        _generateImage = false;
    }

    /**
     * Use this method to add an image area map to the image. When the user clicks on a region of the image a link will be followed or a submit will be performed.
     */
    public void addAreaMap(HtmlImageMapArea map) {
        if (_areaMaps == null)
            _areaMaps = new Vector();

        _areaMaps.addElement(map);
        map.setImage(this);
    }

    public void clearSubmit() {
        _submit = null;
    }

    public boolean executeEvent(int eventType) throws Exception {
        if (_areaMaps != null) {
            if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
                boolean retVal = _submit.executeEvent(eventType);
                _submit = null;
                return retVal;
            }
        }

        return true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

        processLocaleInfo();

        if (_dsEval != null) {
            if (rowNo > -1)
                _source = _dsEval.evaluateRowFormat(rowNo);
            else
                _source = _dsEval.evaluateRowFormat();

        }

        String source = _source;
        if (_generateImage) {
            // sr 11-01-2000 this replaces the call to  generateImageURL() in the constructor
            if (_imageURL == null) {
                _imageURL = generateImageURL();
            }
            source = _imageURL + "default.dgif";
        }
        //else if (source != null) {
        //	source = Util.urlEncode(source,false);
        //}

        //
        if (!_enabled) {
            if (_generateImage) {
                source = _imageURL + "disabled.dgif";
                if (!_useCache)
                    source += "?ct=" + _count++;
            }
        }

        //
        source = translateSiteMapURL(source);
        
        StringBuffer out = new StringBuffer("<IMG SRC=\"" + source + "\"");

        // NAME
        if (!Util.isNull(_name) && !Util.isEmpty(_name)) {
            out.append(" NAME=\"" + getFullName() + "\"");
        }

        // ALT
        String alt = _alt;
        if (_altEval != null) {
            if (rowNo > -1)
                alt = _altEval.evaluateRowFormat(rowNo);
            else
                alt = _altEval.evaluateRowFormat();
        }
        if (!Util.isNull(alt) && !Util.isEmpty(alt)) {
            out.append(" ALT=\"" + alt + "\"");
        }

        // ALIGN
        if (!Util.isNull(_align) && !Util.isEmpty(_align)) {
            out.append(" ALIGN=\"" + _align + "\"");
        }

        if (!Util.isNull(_onLoad) && !Util.isEmpty(_onLoad)) {
            out.append(" ONLOAD=\"" + _onLoad + "\"");
        }

        // BORDER
        if (_border > -1) {
            out.append(" BORDER=\"" + _border + "\"");
        }

        // WIDTH
        if (_width > -1) {
            out.append(" WIDTH=\"" + _width + (_widthSizeOption == SIZE_PERCENT ? "%" : "") + "\"");
        }

        // HEIGHT
        if (_height > -1) {
            out.append(" HEIGHT=\"" + _height + (_heightSizeOption == SIZE_PERCENT ? "%" : "") + "\"");
        }

        // HSPACE
        if (_hSpace > -1) {
            out.append(" HSPACE=\"" + _hSpace + "\"");
        }
        // VSPACE
        if (_vSpace > -1) {
            out.append(" VSPACE=\"" + _vSpace + "\"");
        }


        // USEMAP
        if (_useMap != null)
            out.append(" USEMAP=\"" + _useMap + "\"");
        else  if (_areaMaps != null) {
            out.append(" USEMAP=\"#" + getFullName() + "_IMAGE_AREA_MAP\"");
        }
        
        // TITLE
        if (_title != null)
            out.append(" TITLE=\"" + _title + "\"");
 

        //
        out.append(">");
        p.print(out);
        if (_areaMaps != null && _useMap == null) {
            p.print("<MAP NAME = \"" + getFullName() + "_IMAGE_AREA_MAP\">");
            for (int i = 0; i < _areaMaps.size(); i++)
                ((HtmlImageMapArea) _areaMaps.elementAt(i)).generateHTML(p, rowNo);
            p.print("</MAP>");
        }
    }

    public void generateImage(String img, OutputStream out) throws IOException {
        //create an image to draw
        ImageFactory fact = new ImageFactory();
        Image i = null;

        if (_style.equals(STYLE_OVAL)) {
            if (_width > 0) {
                i = fact.createOvalButton(_width, _height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
            } else {
                i = fact.createOvalButton(_height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, _transparentColor, false, _enabled);
            }
        }
        if (_style.equals(STYLE_PIXEL)) {
            i = fact.createOnePixelImage(_backgroundColor);
        } else if (_width > 0) {
            i = fact.createRectangleButton(_width, _height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);
        } else {
            i = fact.createRectangleButton(_height, _font, _source, _textColor, _backgroundColor, _topLeftBorder, _bottomRightBorder, false, _enabled);
        }

        GifEncoder g = new GifEncoder(i, out, true, _transparentColor);
        g.encode();

    }

    public void generateInitialHTML(PrintWriter p) throws Exception {
        if (_areaMaps != null) {
            for (int i = 0; i < _areaMaps.size(); i++)
                ((HtmlImageMapArea) _areaMaps.elementAt(i)).generateInitialHTML(p);
        }
    }

    /**
     * Returns the alignment property for the image.
     */
    public String getAlign() {
        return _align;
    }

    /**
     * Returns the tool tip for the image
     */
    public String getAlt() {
        return _alt;
    }

    /**
     * This method gets the DataStoreEvaluator being used for alt expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getAltExpression() {
        return _altEval;
    }

    /**
     * Returns the Locale key used for the alt texy
     */
    public String getAltLocaleKey() {
        return _altLocaleKey;
    }

    /**
     * This method returns the background color if the image is generated
     */
    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * This method returns the border width of the image in pixels.
     */
    public int getBorder() {
        return _border;
    }

    /**
     * This method returns the bottom right border color if the image is generated
     */
    public Color getBottomRightColor() {
        return _bottomRightBorder;
    }

    public long getCacheKey() {
        return _cacheKey;
    }

    /**
     * This method gets the Display Style for a generated image button. Valid Values are STYLE_RECTANGLE and STYLE_OVAL
     */
    public String getDisplayStyle() {
        return _style;
    }

    /**
     * This method returns whether or not clicking on the image will do a submit.
     */
    public boolean getEnabled() {
        return _enabled;
    }

    /**
     * This method gets the DataStoreEvaluator being used for expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getExpression() {
        return _dsEval;
    }

    /**
     * This method returns the font used for dynamically generated images.
     */
    public Font getFont() {
        return _font;
    }

    /**
     * This method returns the height of the image.
     */
    public int getHeight() {
        return _height;
    }

    /**
     * This method returns SIZE_PIXELS or SIZE_PERCENT.
     */
    public int getHeightSizeOption() {
        return _heightSizeOption;
    }

    /**
     * This method returns the horizontal space around the the image.
     */
    public int getHorizontalSpace() {
        return _hSpace;
    }

    /**
     * This method gets the source url for the image.
     */
    public String getSource() {
        return _source;
    }

    /**
     * Returns the Locale key used for the source url
     */
    public String getSourceLocaleKey() {
        return _sourceLocaleKey;
    }

    /**
     * This method returns the text for the button
     * @return java.lang.String
     */
    public String getText() {
        if (_generateImage)
            return _source;
        else
            return null;
    }

    /**
     * This method returns the text color if the image is generated
     */
    public Color getTextColor() {
        return _textColor;
    }

    /**
     * Returns the Locale key used for the text of this component
     */
    public String getTextLocaleKey() {
        return _textLocaleKey;
    }

    /**
     * This method returns the property theme for the component.
     * @return
     */
    public String getTheme() {
        return _theme;
    }

    /**
     * This method returns the top and left border color if the image is generated
     */
    public Color getTopLeftColor() {
        return _topLeftBorder;
    }

    /**
     * This method returns the color that will be represent transparent if the image is generated
     */
    public Color getTransparentColor() {
        return _transparentColor;
    }

    /**
     * If the image is dynamically generated, this method will indicate whether or not the Objectstore should cache it.
     */
    public boolean getUseCache() {
        return _useCache;
    }

    /**
     * This method returns the vertical space around the the image.
     */
    public int getVerticalSpace() {
        return _vSpace;
    }

    /**
     * This method returns the width of the image.
     */
    public int getWidth() {
        return _width;
    }

    /**
     * This method returns SIZE_PIXELS or SIZE_PERCENT.
     */
    public int getWidthSizeOption() {
        return _widthSizeOption;
    }

    private void processLocaleInfo() {
        if (_updateLocale) {
            _updateLocale = false;
            LanguagePreferences p = getPage().getLanguagePreferences();
            if (_sourceLocaleKey != null) {
                String newSource = LanguageResourceFinder.getResource(getPage().getApplicationName(), _sourceLocaleKey, p);
                if (newSource != null)
                    setSource(newSource);
            }
            if (_textLocaleKey != null) {
                String newText = LanguageResourceFinder.getResource(getPage().getApplicationName(), _textLocaleKey, p);
                if (newText != null)
                    setText(newText);
            }
            if (_altLocaleKey != null) {
                String newAlt = LanguageResourceFinder.getResource(getPage().getApplicationName(), _altLocaleKey, p);
                if (newAlt != null)
                    setAlt(newAlt);
            }
        }
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {

        if (_areaMaps != null) {
            for (int i = 0; i < _areaMaps.size(); i++) {
                if (((HtmlImageMapArea) _areaMaps.elementAt(i)).processParms(parms, rowNo)) {
                    _submit = (HtmlImageMapArea) _areaMaps.elementAt(i);
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Sets the alignment property for the image.
     */

    public void setAlign(String align) {
        _align = align;
    }

    /**
     * This method sets the tool tip for the image
     */
    public void setAlt(String alt) {
        _alt = alt;
    }

    /**
     * Use this method to bind the alt property to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setAltExpression(DataStoreBuffer ds, String expression) throws Exception {
        _altEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Use this method to bind the alt property to an expression in a DataStore
     * @param ds The DataStore to bind to.
     * @param expression The expression to bind to.
     * @see DataStoreEvaluator
     */
    public void setAltExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        _altEval = new DataStoreEvaluator(ds, expression);
    }

    /**
     * Sets the Locale key used for the Alt text
     */
    public void setAltLocaleKey(String altLocaleKey) {
        _altLocaleKey = altLocaleKey;
        _updateLocale = true;

    }

    /**
     * This method sets the background color for the generated image
     */
    public void setBackgroundColor(Color c) {
        _backgroundColor = c;
    }

    /**
     * This method sets the border border of the image in pixels.
     */
    public void setBorder(int border) {
        _border = border;
    }

    /**
     * This method sets the bottom and right border color for the generated image
     */
    public void setBottomRightColor(Color c) {
        _bottomRightBorder = c;
    }

    public void setCacheKey(long key) {
        _cacheKey = key;
    }

    /**
     * This method will set whether or not clicking on the image will do a submit.
     */
    public void setEnabled(boolean enabled) {
        _enabled = enabled;
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
     * This method sets the font used for dynamically generated images.
     */
    public void setFont(Font f) {
        _font = f;
    }

    /**
     * This method sets the height of the image.
     */
    public void setHeight(int height) {
        _height = height;
    }

    /**
     * Valid values for this method are SIZE_PIXELS or SIZE_PERCENT.
     */
    public void setHeightSizeOption(int option) {
        _heightSizeOption = option;
    }

    /**
     * This method sets the Horizontal space around the image in pixels.
     */
    public void setHorizontalSpace(int width) {
        _hSpace = width;
    }

    /**
     * Sets the alignment property for the image.
     */

    public void setOnLoad(String onload) {
        _onLoad = onload;
    }

    /**
     * This method sets the source url for the image.
     */
    public void setSource(String source) {
        _source = source;
    }

    /**
     * Sets the Locale key used for the image URL
     */
    public void setSourceLocaleKey(String sourceLocaleKey) {
        _sourceLocaleKey = sourceLocaleKey;
        _updateLocale = true;

    }

    /**
     * This method sets the text for the for the dynamically generated image.
     */
    public void setText(String text) {
        if (_generateImage) {
            _source = text;
            _useCache = false;
        }
    }

    /**
     * This method sets the text color for the generated image
     */
    public void setTextColor(Color c) {
        _textColor = c;
    }

    /**
     * Returns the Locale key used for text
     */
    public void setTextLocaleKey(String textLocaleKey) {
        _textLocaleKey = textLocaleKey;
        _updateLocale = true;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {
        Props props = getPage().getPageProperties();

        String font = props.getThemeProperty(theme, Props.SUBMIT_IMAGE_FONT_FACE);
        if (font == null)
            font = "Helvetica";
        int size = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_SIZE);
        if (size == -1)
            size = 12;
        int fontStyle = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_FONT_STYLE);
        if ((fontStyle < 0) || (fontStyle > 3))
            fontStyle = Font.PLAIN;
        _font = new Font(font, fontStyle, size);

        Color c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TEXT_COLOR);
        if (c != null)
            _textColor = c;

        c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BACKGROUND_COLOR);
        if (c != null)
            _backgroundColor = c;

        if (_height == -1)
            _height = props.getThemeIntProperty(theme, Props.SUBMIT_IMAGE_DEFAULT_HEIGHT);

        c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TOPLEFT_BORDER_COLOR);
        if (c != null)
            _topLeftBorder = c;

        c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_BOTTOMRIGHT_BORDER_COLOR);
        if (c != null)
            _bottomRightBorder = c;

        c = props.getThemeColorProperty(theme, Props.SUBMIT_IMAGE_TRANSPARENT_COLOR);
        if (c != null)
            _transparentColor = c;

        String style = props.getThemeProperty(theme, Props.SUBMIT_IMAGE_STYLE);
        if (style != null) {
            if (style.toUpperCase().equals(STYLE_OVAL))
                _style = STYLE_OVAL;
            else
                _style = STYLE_RECTANGLE;
        }

        _theme = theme;
    }

    /**
     * This method sets the top and left border color for the generated image
     */
    public void setTopLeftColor(Color c) {
        _topLeftBorder = c;
    }

    /**
     * This method sets the color that represents tranparent for the generated image
     */
    public void setTransparentColor(Color c) {
        _transparentColor = c;
    }

    /**
     * This method sets the Vertical space around the image in pixels.
     */
    public void setVerticalSpace(int width) {
        _vSpace = width;
    }

    /**
     * This method sets the width of the image.
     */
    public void setWidth(int width) {
        _width = width;
    }

    /**
     * Valid values for this method are SIZE_PIXELS or SIZE_PERCENT.
     */
    public void setWidthSizeOption(int option) {
        _widthSizeOption = option;
    }

    /**
     * Updates the text and format for the current local
     */
    public void updateLocale() {
        _updateLocale = true;
    }

    /**
     * This method gets the Display Style for a generated image button. Valid Values are STYLE_RECTANGLE and STYLE_OVAL
     */
    public void setDisplayStyle(String style) {
        _style = style;
    }

    /**
     * This method returns a specific use map for the image that is defined elsewhere in the JSP
     */
    public String getUseMap() {
        return _useMap;
    }

    /**
     * If you want to specify a specific use map for the image that is defined elsewhere in the JSP, you can use this method
     */

    public void setUseMap(String useMap) {
        _useMap = useMap;
    }
	/**
	 * @return Returns the _title.
	 */
	public String getTitle()
	{
		return _title;
	}
	/**
	 * @param string The _title to set.
	 */
	public void setTitle(String string)
	{
		this._title = string;
	}
}