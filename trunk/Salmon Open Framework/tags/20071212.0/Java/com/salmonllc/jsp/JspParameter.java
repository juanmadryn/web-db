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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspParameter.java $
//$Author: Srufle $
//$Revision: 12 $
//$Modtime: 7/15/04 3:35p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.util.Util;

import java.util.*;

/**
 * This class is used in concert with the Parameter tag. It does not have any html representation
 */

public class JspParameter extends HtmlComponent {
    private String _fValue;

    public JspParameter(String name, HtmlPage p) {
        super(name, p);
        _visible = true;
    }

    /**
     * This method was generates the html sent back to the browser.
     */
    public void generateHTML(java.io.PrintWriter p, int rowNo) throws java.lang.Exception {
    }

    /**
     * This method gets the value of the parameter.
     * @return java.lang.String
     */
    public java.lang.String getValue() {
        return _fValue;
    }

	/**
	 * This method gets the value of the parameter as a boolean. If the parm is T,true,Y or Yes it will return true. If the parm is F, False, N, or No it will return false. Otherwise it will return the default value passed.
	 */
	public boolean getValueAsBoolean(boolean def) {
		if (_fValue == null)
			return def;
		String val = _fValue.toUpperCase();

		if (val.equals("N") || val.equals("NO") || val.equals("F") || val.equals("FALSE"))
			return false;

		if (val.equals("Y") || val.equals("YES") || val.equals("T") || val.equals("TRUE"))
			return true;

		return def;
	}
	  /**
	   * This method gets the value of the parameter as an int value. If the parm is null it returns -1.
	   */
	  public int getValueAsInt() {		
		  int ret = Util.stringToInt(_fValue);
		  return ret;
	  }

    /**
     * This method gets the value of the parameter as a string array. The parameter should be entered as a comma seperated list.
     */
    public String[] getValueAsStringArray() {

        if (_fValue == null)
            return new String[0];

        Vector v = new Vector();
        StringTokenizer t = new StringTokenizer(_fValue, ",");
        while (t.hasMoreTokens())
            v.addElement(t.nextToken().trim());

        String ret[] = new String[v.size()];
        v.copyInto(ret);
        return ret;
    }


    /**
     * This method sets the value of the parameter.
     * @param newValue java.lang.String
     */
    public void setValue(java.lang.String newValue) {
        _fValue = newValue;
    }
}
