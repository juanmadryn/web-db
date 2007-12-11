package com.salmonllc.html;

import com.salmonllc.html.events.ValueChangedEvent;
import java.util.Hashtable;

/**
 * Implements a two-part entry field for a decimal number.
 */
public class HtmlFractionComponent extends HtmlFormComponent
{
    protected HtmlTextEdit _edit1;
    protected double _decimalFactor = 0.0;
    protected HtmlContainer _cont;
    protected int _size;
    protected int _scale;
    protected HtmlDropDownList _fraction;
    protected boolean _noPadding;

/**
 * FractionComponent constructor comment.
 *
 * @param name		Name of component.
 * @param p			Page containing component.
 * @param size		number of palces for integer portion.
 * @param scale		the highest fraction proportion, e.g 64 for 1/64 .. 63/64.
 */
public HtmlFractionComponent(String name, HtmlPage p, int size, int scale) {
	this(name, p, size, scale, null);
}
/**
 * FractionComponent constructor comment.
 *
 * @param name		Name of component.
 * @param p			Page containing component.
 * @param size		number of palces for integer portion.
 * @param scale		the highest fraction proportion, e.g 64 for 1/64 .. 63/64.
 * @param preText	Component to display before the entry fields, such as a currency sign.
 */
public HtmlFractionComponent(String name, HtmlPage p, int size, int scale,HtmlComponent preText) {
	super(name, p);
	_cont = new HtmlContainer(name, p);
	_decimalFactor = 1.0;
	// Initialize the fields
	if (size<0)
	  size=0;
	if (scale<0)
	  scale=1;
	_size=size;
	if (preText != null)
		_cont.add(preText);
	_cont.add(_edit1 = new HtmlTextEdit("left", p));
	_edit1.setGenerateNewline(false);
	_edit1.setSize(size);
	_edit1.setMaxLength(size);
	_fraction = new HtmlDropDownList("right", p);
	_cont.add(_fraction);
	_scale=scale;
	_fraction.addOption(" "," ");
	for (int i=1;i<_scale;i++) {
	  if (i%2!=0) {
	    if (_scale%i==0)
		  _fraction.addOption(i+"/"+_scale,"1/"+_scale/i);
		else
		  _fraction.addOption(i+"/"+_scale,i+"/"+_scale);
	   }
	  else {
	    if (_scale%i==0)
		  _fraction.addOption(i+"/"+_scale,"1/"+_scale/i);
		else {
		  int j=1;
		  while((i/(2*j))%2==0)
			j++;
		  _fraction.addOption(i+"/"+_scale,(i/(2*j))+"/"+_scale/(2*j));
		 }
	   }
	 }
		
}
public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
	// It is essential to call getValue() here because there may be a ValueChanged
	// event in the queue for this object, which needs to be removed, which getValue()
	// does.  The secondary calls to getValue() from the container will not
	// do this because there are no events associated with them.
	String val = getValue(row, true);
	if (val == null) {
		_edit1.setValue(null, row);
		_fraction.setValue(null, row);
	}
	else {
		try {
			Double dVal;
			if (_dsBuff != null) {
				if (row >= 0)
					dVal = new Double(_dsBuff.getDouble(row, _dsColNo));
				else
					dVal = new Double(_dsBuff.getDouble(_dsColNo));
			}
			else
				dVal = Double.valueOf(val);
			int integerPart = dVal.intValue();
			String sIntegerPart = Integer.toString(integerPart);
			// Pad left side with spaces, i.e. right-justify the number.
			StringBuffer buf = new StringBuffer();
			if (!_noPadding) {
			  int n = _size - sIntegerPart.length();
			  while (n-- > 0)
				 buf.append(' ');
			 }
			buf.append(sIntegerPart);
			_edit1.setValue(buf.toString());
			if (_scale > 0) {
				long top=(long)((dVal.doubleValue() - (double)integerPart)*_scale);
				_fraction.setValue(top+"/"+_scale);
			}
		}
		catch (NumberFormatException e) {
			// This should never happen
			_edit1.setValue(null, row);
			_fraction.setValue(null, row);
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
public HtmlComponent getFocus () {
	return _edit1;
}
/**
 * Returns a double representation of a passed string fraction.
 * @return double
 * @param sFraction java.lang.String
 */
public static double getFractionValue(String sFraction) {
	int iDivide=sFraction.indexOf('/');
	long top=new Long(sFraction.substring(0,iDivide)).longValue();
	long bottom=new Long(sFraction.substring(iDivide+1)).longValue();
	double value=(double)top/(double)bottom;
	return value;
}
/**
 * Returns whether the Integer Portion is not padded to the right or not
 * @return boolean
 */
public boolean getNoPadding() {
	return _noPadding;
}
public boolean processParms(Hashtable parms, int rowNo) throws Exception {

	if (!getVisible() || !getEnabled())
		return false;
	
	// Determine the old value from both edit fields.
		
	String oldValue;
	if (_dsBuff == null) {
		oldValue = _edit1.getValue();
		if ((oldValue != null) && (_scale > 0)) {
			if (_fraction.getValue() != null) {
				String sFraction=_fraction.getValue();
				int iDivide=sFraction.indexOf('/');
				long top=new Long(sFraction.substring(0,iDivide)).longValue();
				double value=(double)top/(double)_scale;
				String sValue=""+value;
				sValue=sValue.substring(1);
				oldValue += sValue;
			   }
		}
	}
	else {
		Number n;
		if (rowNo > -1)
			n = (Number)_dsBuff.getAny(rowNo, _dsColNo);
		else
			n = (Number)_dsBuff.getAny(_dsColNo);
		if (n == null)
			oldValue = null;
		else
			oldValue = ""+n.doubleValue();	
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
	}		
	else
		newValue = null;

	if (_scale > 0) {
		String name2 = _fraction.getFullName();
		if (rowNo > -1)
			name2 += "_" + rowNo;
		val = (String[]) parms.get(name2);
		if (val != null) {
			String s = val[0].trim();
			if (!s.equals("")) {
				if (newValue == null)
					newValue = "";
				int iDivide=s.indexOf('/');
				long top=new Long(s.substring(0,iDivide)).longValue();
				double value=(double)top/(double)_scale;
				String sValue=""+value;
				sValue=sValue.substring(1);
				newValue += sValue;
			}
		}
	}

	// Compare old and new values and possibly create a ValueChangedEvent.
	if (!valuesEqual(oldValue, newValue)) {
		ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),oldValue,newValue,rowNo,_dsColNo,_dsBuff);
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
 * Specifies the Style Class to be used for the Component.
 * Creation date: (7/19/01 8:41:20 AM)
 * @param sClass java.lang.String A name of a class in Html to be used by this component
 */
	public void setClassName(String sClass) {
		super.setClassName(sClass);
		_edit1.setClassName(sClass);
		_fraction.setClassName(sClass);
	}
    /**
    * Sets the font end tag for disabled mode.
    * @param tag java.lang.String
    */
    public void setDisabledFontEndTag(String tag)
    {
        _edit1.setDisabledFontEndTag(tag);
        _fraction.setDisabledFontEndTag(tag);
    }
    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag)
    {
        _edit1.setDisabledFontStartTag(tag);
        _fraction.setDisabledFontStartTag(tag);
    }
/**
 * Enables or disables the ability of this component to respond to user input.
 * @param editable boolean
 */
public void setEnabled (boolean enabled) {
	super.setEnabled(enabled);
	_cont.setEnabled(enabled);
}
/**
 * Specify whether to pad integer portion to the right.
 * @param bNoPadding boolean
 */
public void setNoPadding(boolean bNoPadding) {
	_noPadding=bNoPadding;
}
/**
 * Specifies the parent component for this Component.
 * Creation date: (7/19/01 8:41:20 AM)
 * @param sClass java.lang.String A name of a class in Html to be used by this component
 */
public void setParent(HtmlComponent parent) {
	super.setParent(parent);
	// This is necessary for the name to be generated correctly, else the leading
	// sequence of parent names will be lost.
	_cont.setParent(parent);
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
public String getAccessKey() {
	return _edit1.getAccessKey();
}
/**
 * @param sets the access key html attribute
 */
public void setAccessKey(String val) {
	_edit1.setAccessKey(val);
}

/**
 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
 */
public void setTabIndex(int val) {
	_edit1.setTabIndex(val);
	_fraction.setTabIndex(val + 1);
}
}
