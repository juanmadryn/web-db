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
//$Archive: /sofia/sourcecode/com/salmonllc/jsp/JspRowSelector.java $
//$Author: Deepak $ 
//$Revision: 9 $ 
//$Modtime: 7/06/04 2:39p $ 
/////////////////////////

import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;

/**
 * This type can be used to add a row selector to an html table.
 */
public class JspRowSelector extends HtmlFormComponent {
    private String _unSelectedValue = "0";
    private String _onClick;

    private String _imageOn;
    private String _imageOff;
    /**
     * Constructs a Html row selector object for an HtmlDataTable
     */
    public JspRowSelector(String name, HtmlPage p) {
        super(name,p);
    }
    public boolean executeEvent(int eventType) throws Exception
    {
        if (_events != null)
            if (_events.size() > 0 && _dsBuff != null)
                for (int i = 0;i<_dsBuff.getRowCount();i++)
                    _dsBuff.setAny(i,_dsColNo,_unSelectedValue);

        return super.executeEvent(eventType);
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception
    {
        if (! _visible )
		    return;

        boolean checked = false;
        _value = getValue(rowNo);

        if (_value != null && (!_value.trim().equals(""))) {
            if (_value.equals(""+rowNo))
                checked = true;
        }
        else
            _value="";

        String tag = "<INPUT TYPE=\"RADIO\" NAME=\"" + getName() + "\" VALUE=\"" + rowNo + "\"";

        if (! _enabled)
        {
            if(useDisabledAttribute())
            {
                 tag += " disabled=\"" + true + "\"";
            }
            else if (_imageOn != null && _imageOff != null)
            {
                String out = "<IMG SRC=\"";
                if (checked)
                    out += _imageOn;
                else
                    out += _imageOff;
                out += "\">";

                p.print(out);
                return;
            }
        }

        if (_onClick!=null && !_onClick.trim().equals(""))
            tag+=" ONCLICK=\""+_onClick+"\"";

        if (checked)
            tag += " CHECKED";

        tag += ">";


        p.println(tag);

    }
    /**
     * This method gets the javascript to be executed when the component gets clicked.
     */
    public String getOnClick() {
        return _onClick;
    }
    public boolean processParms(java.util.Hashtable parms , int row) throws Exception {
        Object oldValue = _value;

        if (row > -1) {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(row,_dsColNo);
        }
        else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(_dsColNo);
        }

        String val[] = (String[]) parms.get(getName());

        int selectedRow = -1;
        if (val != null)
            selectedRow = Integer.parseInt(val[0]);

        if (selectedRow == row)
            _value = "" + selectedRow;

        if (! valuesEqual(oldValue,_value) && _value.equals(""+ selectedRow)) {
            String s = null;
            if (oldValue != null)
                s = oldValue.toString();
            ValueChangedEvent e = new ValueChangedEvent(getPage(),this,getName(),getFullName(),s,_value,row,_dsColNo,_dsBuff);
            addEvent(e);
        }

        return false;
    }
    /**
     * This method sets the javascript to be executed when the component is checked.
     */
    public void setOnClick(String value) {
        _onClick = value;
    }

    /**
     * This method sets the row in the datastore that is selected
     */
    public void setSelectedRow(int rowNo) {
        setValue(""+rowNo,rowNo);

    }

    public void setTheme(String theme)
    {
        super.setTheme(theme);
        Props props = getPage().getPageProperties();

        _imageOn = props.getThemeProperty(theme,Props.RADIOBUTTON_IMAGE_ON);
        _imageOff = props.getThemeProperty(theme,Props.RADIOBUTTON_IMAGE_OFF);
    }
}
