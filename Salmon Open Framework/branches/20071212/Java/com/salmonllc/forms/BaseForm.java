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
package com.salmonllc.forms;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/forms/BaseForm.java $
//$Author: Srufle $
//$Revision: 18 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.sql.DataStore;

/**
 * Base class for forms.
 */
public class BaseForm extends HtmlContainer implements PageListener {
    protected HtmlLineBreak _line_break;
    protected DataStore _ds;

    /**
     * Constructor.
     * @param name String		Component name for this form
     * @param page HtmlPage		Page containing this form
     * @param ds DataStore		Data store object to use, if null then create one.
     */
    public BaseForm(String name, HtmlPage page, DataStore ds) {
        super(name, page);
        _line_break = new HtmlLineBreak(page);
        if (ds == null)
            _ds = new DataStore(page.getApplicationName());
        else
            _ds = ds;
        page.addPageListener(this);
    }

    /**
     * Creates an integer-type drop-down list like HtmlComponenFactory but tailored
     * to needs of the forms subclasses.
     * @return com.salmonllc.html.HtmlDropDownList
     * @param name java.lang.String
     * @param values int[]
     * @param displayValues java.lang.String[]
     */
    public HtmlDropDownList newIntegerDropDown(String name, int values[], String displayValues[]) {
        HtmlDropDownList ddl = new HtmlDropDownList(name, getPage());
        ddl.addOption(null, "");
        String displayValue = null;
        for (int i = 0; i < values.length; i++) {
            if (i < displayValues.length)
                displayValue = displayValues[i];
            else
                displayValue = "Value " + i;
            ddl.addOption(new Integer(values[i]).toString(), displayValue);
        }
        return ddl;
    }

    public void pageRequested(PageEvent p) throws Exception {
    }

    public void pageRequestEnd(PageEvent p) throws Exception {
    }

    public void pageSubmitEnd(PageEvent p) {
    }

    public void pageSubmitted(PageEvent p) {
    }

    /**
     * Creates an integer-type drop-down list like HtmlComponenFactory but tailored
     * to needs of the forms subclasses.
     * @return com.salmonllc.html.HtmlDropDownList
     * @param name java.lang.String
     * @param values int[]
     * @param displayValues java.lang.String[]
     */
    public HtmlDropDownList newStringDropDown(String name, String values[], String displayValues[]) {
        HtmlDropDownList ddl = new HtmlDropDownList(name, getPage());
        ddl.addOption(null, "");
        String displayValue = null;
        for (int i = 0; i < values.length; i++) {
            if (i < displayValues.length)
                displayValue = displayValues[i];
            else
                displayValue = "Value " + i;
            ddl.addOption(values[i], displayValue);
        }
        return ddl;
    }
}
