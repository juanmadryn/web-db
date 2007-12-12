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
package com.salmonllc.util;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/util/ThreeObjectContainer.java $
//$Author: Dan $ 
//$Revision: 9 $ 
//$Modtime: 10/30/02 2:38p $ 
/////////////////////////
 
/**
 * This is a simple container object that holds any three other objects. 
 */
public class ThreeObjectContainer implements java.io.Serializable {
	Object _object1;
	Object _object2;
	Object _object3;
/**
 * Builds an empty TwoObjectContainer
 */
public ThreeObjectContainer() {
	super();
}
/**
 * Builds an TwoObjectContainer and sets the value of object1 and object2
 */

public ThreeObjectContainer(Object object1, Object object2,Object object3) {
	super();
	_object1 = object1;
	_object2 = object2;
	_object3 = object3;
	
}
/**
 * Returns the first object
 */
public Object getObject1() {
	return _object1;
}
/**
 * Returns the second object
*/
public Object getObject2() {
	return _object2;
}
/**
 * Returns the third object
*/
public Object getObject3() {
	return _object3;
}
/**
 * Sets the value of the first object.
 */
public void setObject1(Object o) {
	_object1 = o;
}
/**
 * Sets the value of the second object.
 */
public void setObject2(Object o) {
	_object2 = o;
}
/**
 * Sets the value of the third object.
 */
public void setObject3(Object o) {
	_object3 = o;
}

/**
 * Creates a String representation of the contents of the ThreebjectContainer
 * @return - contents of container as a string representation
 */
public String toString()
{

    return super.toString() + "\n obj1:" + getObject1().toString() + "\n obj2:" + getObject2().toString() + "\n obj3:" + getObject3().toString();
}
}
