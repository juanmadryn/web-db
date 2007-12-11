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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DataStoreExpression.java $
//$Author: Srufle $ 
//$Revision: 8 $ 
//$Modtime: 4/15/03 2:24p $ 
/////////////////////////

/**
 * This interface is used to create hard coded expressions for a data store evaluator. Generally components evaluate a script expression, but sometimes an expression is to complex to go into a one line script or performance concerns dictate that compiled instead of interpreted code be executed to evaluate an expression. A class implementing this interface can be used in instead in every situation that a String expression can be used.
 */
public interface DataStoreExpression {
/**
 * Implement this method to evaluate a DataStore expression
 * @return The result of the expression
 * @param dsBuf The datastore to use to evaluate the expression
 * @param row The row number to use in the DataStore to evaluate the expression
 */
public Object evaluateExpression(DataStoreBuffer dsBuf, int row) throws DataStoreException;
}
