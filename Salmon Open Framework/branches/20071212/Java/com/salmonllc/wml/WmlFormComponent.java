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
package com.salmonllc.wml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlFormComponent.java $
//$Author: Srufle $
//$Revision: 8 $
//$Modtime: 4/15/03 2:25p $
/////////////////////////

import com.salmonllc.html.HtmlFormComponent;
import com.salmonllc.html.HtmlPage;

/**
 * This type is the base class for all Wml Form Controls
 */
public abstract class   WmlFormComponent extends HtmlFormComponent {
    /**
     * WmlFormComponent constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public WmlFormComponent(String name, HtmlPage p) {
        super(name, p);
    }


    public abstract String getPostForm();
}
