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
package com.salmonllc.sql;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/AutoRetrieveCriteria.java $
//$Author: Dan $ 
//$Revision: 15 $ 
//$Modtime: 8/20/03 2:14p $ 
/////////////////////////


import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import javax.servlet.http.*;

/**
 * This class is used to store information used to decide how to autoretrieve a datastore created inside a JSP page.
 * The class is populated by datasource selection criteria tags and passed to the datastore providing enough information to build a where clause.
 * Generally this class is used by the framework and does not need to be created directly in an application.
 */

public class AutoRetrieveCriteria implements Serializable {
	
	Vector _items = new Vector();
	boolean _haveParmsChanged=false;
	
	class CriteriaLine implements Serializable {
		String prefix;
		String column;
		String operator;
		String value;
		String suffix;
		String connector;
        String work;
        Hashtable oldValues;
		boolean quoted;
	}	

    private static String replace(String sString,String sOldString,String sNewString) {
        StringBuffer sbString=new StringBuffer(sString);
        while (true) {
            int iIndex=sbString.toString().indexOf(sOldString);
            if (iIndex<0)
              break;
            sbString.replace(iIndex,iIndex+sOldString.length(),sNewString);
        }
        return sbString.toString();
    }

/**
 * Creates an new AutoRetrieveCriteria object
 */

public AutoRetrieveCriteria() {
	super();
}
/**
 * Adds a criteria line to the list of criteria items.
 */
public void addCriteria(String prefix, String column, String op, String value, String suffix, String connector) {
	CriteriaLine l = new CriteriaLine();
	l.prefix = prefix;
	l.column = column;
	l.operator = op;
	l.work = value;
	l.suffix = suffix;
	l.connector = connector;
	l.oldValues = new Hashtable();

	if (value != null) {
		l.work = value.trim();
		if (l.work.startsWith("'") && l.work.endsWith("'")) {
			l.quoted = true;
		}
	}
	_items.addElement(l);
		
}
/**
 * Returns the column for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getColumn(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.column;
}
/**
* Sets the column for a particular item. The index must be between 0 and getCriteriaCount() - 1;
*/
public void setColumn(int index, String column) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	l.column = column;
}
/**
 * Returns the connector for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getConnector(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.connector;
}
/**
 * Returns the number of selection criteria items
 */
public int getCriteriaCount() {
	return _items.size();
}
/**
 * Returns the operator for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getOperator(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.operator;
}
/**
 * Returns the prefix for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getPrefix(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.prefix;
}
/**
 * Returns the suffix for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getSuffix(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.suffix;
}
/**
 * Returns the value for a particular item. The index must be between 0 and getCriteriaCount() - 1;
 */
public String getValue(int index) {
	CriteriaLine l = (CriteriaLine) _items.elementAt(index);
	return l.value;
}
/**
 * Returns true if any of the parameter values have changed in the last call to the setParms method.
 */
public boolean haveParmsChanged() {
	return _haveParmsChanged;
}
/**
 * Sets the values of the parms from the servlet request line and sets the "changed" flag.
 */
public void setParms(HttpServletRequest req) {
	_haveParmsChanged = false;
	for (int i = 0; i < _items.size();i++) {
		CriteriaLine l = (CriteriaLine) _items.elementAt(i);
        l.value=l.work;
        Enumeration enumera=req.getParameterNames();
        while (enumera.hasMoreElements()) {
            String sParam=(String)enumera.nextElement();
            if (l.value.indexOf("%"+sParam)>=0) {
                String val=req.getParameter(sParam);
                if (val==null)
                  val="";
                String oldValue=(String)l.oldValues.get(sParam);
                if (oldValue==null) {
                    l.oldValues.put(sParam,val);
                    _haveParmsChanged=true;
                }
                else {
                    if (! oldValue.equals(val)) {
                        l.oldValues.put(sParam,val);
                        _haveParmsChanged = true;
                    }
                }
                l.value=replace(l.value,"%"+sParam,val);
            }
        }
        l.value=replace(l.value,"%%","%");
        if (l.value.indexOf("SYSTEM.$CURRDATE")>=0) {
		    java.util.Date currDate = new java.util.Date(System.currentTimeMillis());
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			l.value=replace(l.value,"SYSTEM.$CURRDATE",df.format(currDate));       
  //          l.value=replace(l.value,"SYSTEM.$CURRDATE",currDate.toString());
		}
	}

}
}
