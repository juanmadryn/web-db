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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/Constants.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 9/01/04 3:21p $
/////////////////////////

/**
 * Constants for Jsp Components
 */
public interface Constants {

	public static final String  HREFSTYLE = "HREFSTYLE";
	public static final String 	HOVERSTYLE= "HOVERSTYLE";
    public static final String  GROUPHREFSTYLE = "GROUPHREFSTYLE";
	public static final String 	GROUPHOVERSTYLE= "GROUPHOVERSTYLE";
	public static final String 	TEXTSTYLE = "TEXTSTYLE";
	public static final String 	SELECTEDSTYLE = "SELECTEDSTYLE";
	public static final String 	SELECTEDHOVERSTYLE = "SELECTEDHOVERSTYLE";
	public static final String 	CELLBGSTYLE = "CELLBGSTYLE";
	public static final String 	GROUPCELLBGSTYLE = "GROUPCELLBGSTYLE";
	public static final String 	CELLHOVERBGSTYLE = "CELLHOVERBGSTYLE";
    public static final String 	GROUPHOVERBGSTYLE = "GROUPHOVERBGSTYLE";
	public static final String 	SELECTEDBGSTYLE = "SELECTEDBGSTYLE";
	public static final String 	SELECTEDHOVERBGSTYLE = "SELECTEDHOVERBGSTYLE";
	public static final String 	SHOWPOPUPSTYLE = "SHOWPOPUPSTYLE";
	public static final String 	SHOWPOPUPHOVERSTYLE = "SHOWPOPUPHOVERSTYLE";
	public static final String 	SHOWPOPUPSELECTEDSTYLE = "SHOWPOPUPSELECTEDSTYLE";
	public static final String 	SHOWPOPUPSELECTEDHOVERSTYLE = "SHOWPOPUPSELECTEDHOVERSTYLE";
	public static final String 	SHOWPOPUPBGSTYLE = "SHOWPOPUPBGSTYLE";
    public static final String 	SUBCELLBGSTYLE = "SUBCELLBGSTYLE";
    public static final String  SUBHREFSTYLE = "SUBHREFSTYLE";
	public static final String 	SUBHOVERSTYLE= "SUBHOVERSTYLE";
	public static final String 	SUBCELLHOVERBGSTYLE = "SUBCELLHOVERBGSTYLE";
	//
	//NAVBARID PARAMETER PASSED IN THE GENERATE HTML OF THE NAVBAR. A NAVBARID=(navbaritem name) PARAMETER IS
	//AUTOMATICALLY APPENDED TO EVERY HREF SET IN THE NAVBARITEM TAG.
	public static final String NAVBARID="NavBarId";

	//OPERATORS
	public final static String EQUALS	= "EQUALS";
	public final static String NOT_EQUAL	= "NOT_EQUAL";
	public final static String GREATER_THAN	= "GREATER_THAN";
	public final static String GREATER_THAN_OR_EQUALS	= "GREATER_THAN_OR_EQUALS";
	public final static String LESS_THAN	= "LESS_THAN";
	public final static String LESS_THAN_OR_EQUALS	= "LESS_THAN_OR_EQUALS";
	public final static String OPER_EQUALS	= "=";
	public final static String OPER_NOT_EQUAL	= "<>";
	public final static String OPER_GREATER_THAN	= ">";
	public final static String OPER_GREATER_THAN_OR_EQUALS	= ">=";
	public final static String OPER_LESS_THAN	= "<";
	public final static String OPER_LESS_THAN_OR_EQUALS	= "<=";

    // Transactional token support
    public static final String TOKEN_KEY = "com.salmonllc.jsp.TOKEN";

    //Constants for Popup Windows positions
    public static final String POPUP_POSITION_CENTER = "center";
    public static final String POPUP_POSITION_RELATIVE = "relative";
    public static final String POPUP_POSITION_CUSTOM = "custom";




}
