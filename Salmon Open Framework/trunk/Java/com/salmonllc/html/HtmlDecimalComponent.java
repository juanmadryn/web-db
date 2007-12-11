package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlDecimalComponent.java $
//$Author: Dan $
//$Revision: 24 $
//$Modtime: 3/17/04 2:08p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import java.text.DecimalFormat;
import java.util.Hashtable;

/**
 * Implements a two-part entry field for a decimal number.
 */
public class HtmlDecimalComponent extends HtmlFormComponent {
    protected HtmlTextEdit _edit1;
    protected HtmlTextEdit _edit2;
    protected int _precision;
    protected int _scale;
    protected double _decimalFactor = 0.0;
    protected DecimalFormat _scaleFormat;
    protected HtmlText _dot;
    protected DecimalFormat _valueFormat;
    protected HtmlContainer _cont;

    /** addColumn flags parameter:  Field is required. */
    public static final int NO_PADDING = 1 << 0;

    protected int _flag = NO_PADDING;

    /**
     * DecimalComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param precision	Total number of digits.
     * @param scale		Number of decimal places (digits to right of decimal point).
     */
    public HtmlDecimalComponent(String name, HtmlPage p, int precision, int scale) {
        this(name, p, precision, scale, null);
    }

    /**
     * DecimalComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param precision	Total number of digits.
     * @param scale		Number of decimal places (digits to right of decimal point).
     * @param preText	Component to display before the entry fields, such as a currency sign.
     */
    public HtmlDecimalComponent(String name, HtmlPage p, int precision, int scale,
                                HtmlComponent preText) {
        super(name, p);
        _cont = new HtmlContainer(name, p);
        // Consistency check
        if (precision < 0)
            precision = 0;
        else if (scale < 0)
            scale = 0;
        if (precision < scale)
            scale = precision;
        _precision = precision;
        _scale = scale;
        _decimalFactor = 1.0;
        int n = scale;
        while (n-- > 0)
            _decimalFactor = _decimalFactor / 10.0;
        // Initialize the fields
        if (preText != null)
            _cont.add(preText);
        _cont.add(_edit1 = new HtmlTextEdit("left", p));
        _edit1.setGenerateNewline(false);
        _edit1.setSize(precision - scale);
        _edit1.setMaxLength(precision - scale);
        String valueFormatPattern;
        _edit2 = new HtmlTextEdit("right", p);
        _edit2.setGenerateNewline(false);
        if (scale > 0) {
            _cont.add(_dot = new HtmlText(".", HtmlText.FONT_EMPHASIS, p));
            _cont.add(_edit2);
            _edit2.setSize(scale);
            _edit2.setMaxLength(scale);
            String s = "";
            n = scale;
            while (n-- > 0)
                s += '0';
            _scaleFormat = new DecimalFormat(s);
            valueFormatPattern = "#0." + s;
        } else {
            _scaleFormat = new DecimalFormat();
            valueFormatPattern = "#0";
        }
        _valueFormat = new DecimalFormat(valueFormatPattern);
    }

    /**
     * DecimalComponent constructor comment.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param precision	Total number of digits.
     * @param scale		Number of decimal places (digits to right of decimal point).
     * @param preText	Component to display before the entry fields, such as a currency sign.
     */
    public HtmlDecimalComponent(String name, HtmlPage p, int precision, int scale,
                                HtmlComponent preText, int flag) {
        this(name, p, precision, scale, preText);
        _flag = flag;
    }

    public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        // It is essential to call getValue() here because there may be a ValueChanged
        // event in the queue for this object, which needs to be removed, which getValue()
        // does.  The secondary calls to getValue() from the container will not
        // do this because there are no events associated with them.
        String val = getValue(row, true);
        if (val == null) {
            _edit1.setValue(null, row);
            _edit2.setValue(null, row);
        } else {
            try {
                Double dVal;
                if (_dsBuff != null) {
                    if (row >= 0)
                        dVal = new Double(_dsBuff.getDouble(row, _dsColNo));
                    else
                        dVal = new Double(_dsBuff.getDouble(_dsColNo));
                } else
                    dVal = Double.valueOf(val);
                int integerPart = dVal.intValue();
                String sIntegerPart = Integer.toString(integerPart);
                // Pad left side with spaces, i.e. right-justify the number.
                StringBuffer buf = new StringBuffer();
                if ((_flag & NO_PADDING) == 0) {
                    int n = _precision - _scale - sIntegerPart.length();
                    while (n-- > 0)
                        buf.append(' ');
                }

                buf.append(sIntegerPart);
                _edit1.setValue(buf.toString());
                if (_scale > 0) {
                    double fractionalPart = (dVal.doubleValue() - (double) integerPart) / _decimalFactor;
                    if (fractionalPart < 0) {
                        if (integerPart == 0) {
                            _edit1.setValue("-0");
                        }
                        fractionalPart = -fractionalPart;
                    }
                    _edit2.setValue(_scaleFormat.format((int) Math.round(fractionalPart)), row);
                }
            } catch (NumberFormatException e) {
                // This should never happen
                _edit1.setValue(null, row);
                _edit2.setValue(null, row);
            }
        }
        _cont.generateHTML(p, row);
        if (_visible && _enabled)
            p.println("");
    }

    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocus() {
        return _edit1;
    }

    /**
     * Returns whether the Integer Portion is not padded to the right or not
     * @return boolean
     */
    public boolean getNoPadding() {
        if (_flag == NO_PADDING)
            return true;
        else
            return false;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {

        if (!getVisible() || !getEnabled())
            return false;

        // Determine the old value from both edit fields.

        String oldValue;
        if (_dsBuff == null) {
            oldValue = _edit1.getValue();
            if ((oldValue != null) && (_scale > 0)) {
                if (_edit2.getValue() != null)
                    oldValue += "." + _edit2.getValue();
            }
        } else {
            Number n;
            if (rowNo > -1)
                n = (Number) _dsBuff.getAny(rowNo, _dsColNo);
            else
                n = (Number) _dsBuff.getAny(_dsColNo);
            if (n == null)
                oldValue = null;
            else
                oldValue = _valueFormat.format(n.doubleValue());
        }

        // Determine the new value from both edit fields.

        String newValue;
        String name1 = _edit1.getFullName();
        if (rowNo > -1)
            name1 += "_" + rowNo;
        String val[] = (String[]) parms.get(name1);
        if (val != null) {
            newValue = val[0].trim();
            if (newValue.equals(""))
                newValue = null;
        } else
            newValue = null;

        if (_scale > 0) {
            String name2 = _edit2.getFullName();
            if (rowNo > -1)
                name2 += "_" + rowNo;
            val = (String[]) parms.get(name2);
            if (val != null) {
                String s = val[0].trim();
                if (!s.equals("")) {
                    if (newValue == null)
                        newValue = "";
                    newValue += "." + s;
                }
            }
        }

        // Compare old and new values and possibly create a ValueChangedEvent.
        if (!valuesEqual(oldValue, newValue)) {
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), oldValue, newValue, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }
        return false;
    }

    /**
     * This method will clear all pending events from the event queue for this component.
     */
    public void reset() {
        super.reset();
        _cont.reset();
    }

    /**
     * Specifies the Style Class to be used for the Decimal Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
    public void setClassName(String sClass) {
        super.setClassName(sClass);
        _edit1.setClassName(sClass);
        _edit2.setClassName(sClass);
    }

    /**
     * Sets the font end tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontEndTag(String tag) {

        _edit1.setDisabledFontEndTag(tag);
        _edit2.setDisabledFontEndTag(tag);
        _dot.setFontEndTag(tag);
    }

    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag) {
        _edit1.setDisabledFontStartTag(tag);
        _edit2.setDisabledFontStartTag(tag);
        _dot.setFontStartTag(tag);
    }

    /**
     * Enables or disables the ability of this component to respond to user input.
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _cont.setEnabled(enabled);
    }

    /**
     * Specify whether to pad integer portion to the right.
     * @param bNoPadding boolean
     */
    public void setNoPadding(boolean bNoPadding) {
        if (bNoPadding)
            _flag = NO_PADDING;
        else
            _flag = 0;
    }

    /**
     * Specifies the parent component for this Decimal Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param parent The parent component
     */
    public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        // This is necessary for the name to be generated correctly, else the leading
        // sequence of parent names will be lost.
        _cont.setParent(parent);
    }

    /**
     * Sets the visibility of the component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param bVisible boolean Indicates the visibility of the component
     */
    public void setVisible(boolean bVisible) {
        super.setVisible(bVisible);
        if (_scale > 0 && _dot != null)
        	_dot.setVisible(bVisible);
        _edit1.setVisible(bVisible);
        _edit2.setVisible(bVisible);
    }

    /**
     * This method will set the edit focus to this component.
     */
    public void setFocus() {
        _edit1.setFocus();
    }

    /**
     * This method will set the edit focus to this component for a particular row in a datastore.
     */
    public void setFocus(int row) {
        _edit1.setFocus(row);
    }
    
	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _edit1.getAccessKey();
	}

	/**
	 * @returns the read only html attribute
	 */
	public boolean getReadOnly() {
		return _edit1.getReadOnly();
	}

	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		return _edit1.getTabIndex();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String val) {
		_edit1.setAccessKey(val);
	}

	/**
	 * @param sets the read only html attribute
	 */
	public void setReadOnly(boolean val) {
		_edit1.setReadOnly(val);
		_edit2.setReadOnly(val);
	}
	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		_edit1.setTabIndex(val);
		_edit2.setTabIndex(val + 1);
	}
}
