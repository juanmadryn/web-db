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
package com.salmonllc.email;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/email/SMTPException.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////

import com.salmonllc.util.*;
/**
 * This is the Exception class which is thrown by SMTP.
 */

public class SMTPException extends Exception
{
public SMTPException() {
	super();
}
public SMTPException(String s)
{
	super(s);
	MessageLog.writeDebugMessage(s, null);
}
}
