package com.salmonllc.util;

import java.util.*;
import java.text.*;
/**
 * A class that handles some standard date formats automatically.
 * Creation date: (5/9/01 4:38:51 PM)
 * @author: Fred Cahill
 */
public class SalmonDateFormat extends java.text.SimpleDateFormat {
	private static String[] USPARSEDATEFORMATS={"MM/dd/yyyy","MM/dd/yy","MMddyyyy","MMddyy", "yyyy-MM-dd", 
		"yyyy-MM-dd a hh:mm:ss", "yyyy/MM/dd HH:mm:ss", "MM/dd/yy HH:mm:ss", "yyyy-MM-dd hh:mm:ss.SS", "dd/MM/yyyy","dd/MM/yyyy HH:mm:ss"};

/**
 * SalmonDateFormat constructor.
 */
public SalmonDateFormat() {
	super();
}
/**
 * SalmonDateFormat constructor.
 * @param pattern java.lang.String A pattern of the date to be parsed
 */
public SalmonDateFormat(String pattern) {
	super(pattern);
}
/**
 * SalmonDateFormat constructor.
 * @param pattern java.lang.String A pattern of the date to be parsed.
 * @param formatData java.text.DateFormatSymbols See java.text.SimpleDateFormat
 */
public SalmonDateFormat(String pattern, java.text.DateFormatSymbols formatData) {
	super(pattern, formatData);
}
/**
 * SalmonDateFormat constructor comment.
 * @param pattern java.lang.String A pattern of the date to be parsed
 * @param loc java.util.Locale See java.text.SimpleDateFormat
 */
public SalmonDateFormat(String pattern, java.util.Locale loc) {
	super(pattern, loc);
}
/**
 * Parses the specified date with the specified pattern, if it fails it uses the predefined patterns of this object.
 * @param text java.lang.String A string representing the date to be parsed.
 */
	public Date parse(String text) throws ParseException {
        ParseException peThrow;
		try {
		  return super.parse(text);
		}
		catch (ParseException pe) {
			peThrow=pe;
			Date dReturn=null;
			for (int i=0;i<USPARSEDATEFORMATS.length;i++) {
				SimpleDateFormat sdf=new SimpleDateFormat(USPARSEDATEFORMATS[i]);
				try {
					dReturn=sdf.parse(text);
					String sDate=sdf.format(dReturn);
					if (sDate.equals(text))
					  break;
					dReturn=null;
				}
				catch (Exception e) {;}
			}
			if (dReturn==null)
			  throw(peThrow);
			return dReturn;
		}
	}
}
