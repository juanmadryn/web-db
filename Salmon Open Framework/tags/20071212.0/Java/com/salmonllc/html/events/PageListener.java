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
package com.salmonllc.html.events;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/events/PageListener.java $
//$Author: Dan $
//$Revision: 9 $
//$Modtime: 10/30/02 2:40p $
/////////////////////////

/**
 * This interface must be implemented by every class that will listen to page events.
 */
public interface PageListener extends java.util.EventListener {
    /**
     * This event will get fired each time a page is requested by the browser before any HTML is generated.
     */
    void pageRequested(PageEvent p) throws Exception;

    /**
     * This event will get fired each time a page is requested by the browser after any HTML is generated.
     */
    void pageRequestEnd(PageEvent p) throws Exception;

    /**
     * This method occurs each time a page is submitted before values are copied from the HTML form to the framework component representing input fields.
     */
    void pageSubmitEnd(PageEvent p);

    /**
     * This method occurs each time a page is submitted after all the values submitted from the HTML form are copied to framework components representing them.
     */
    void pageSubmitted(PageEvent p);
}
