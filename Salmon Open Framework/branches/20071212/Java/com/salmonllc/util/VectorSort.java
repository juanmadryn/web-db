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

/**
 * This utility class extends a java.util.Vector by implementing a method that can sort the vector. You must subclass it and implement the compare method. The compare method must have specific knowledge of the types of components on the vector and determine if one object is greater then another.
 */
public abstract class VectorSort extends java.util.Vector {
/**
 * Constructs an empty vector. 
 *
 * @since   JDK1.0
 */
public VectorSort() {
	super();
}
/**
 * Constructs an empty vector with the specified initial capacity.
 *
 * @param   initialCapacity   the initial capacity of the vector.
 * @since   JDK1.0
 */
public VectorSort(int initialCapacity) {
	super(initialCapacity);
}
/**
 * Constructs an empty vector with the specified initial capacity and
 * capacity increment. 
 *
 * @param   initialCapacity     the initial capacity of the vector.
 * @param   capacityIncrement   the amount by which the capacity is
 *                              increased when the vector overflows.
 * @since   JDK1.0
 */
public VectorSort(int initialCapacity, int capacityIncrement) {
	super(initialCapacity, capacityIncrement);
}
/**
 * The compare method is passed two objects from the vector and must return true if Object2 is greater then Object1 for an ascending sort or true if Object2 is less then Object1 for a descending sort. 
 * @param o1 The first object to compare
 * @param o2 The second object to compare
 */
public abstract boolean compare(Object o1,Object o2) ;
	private int divide (int low, int high) {
	    int i, ptr;
	    
	    Object key = elementAt(low);
	    ptr = low;
	    
	    for (i=low+1; i<=high; i++) {
		    if ( compare(elementAt(i),key)) 
	            swap(++ptr,i);
	    }
	    swap(low,ptr);
	    return ptr;
	}
/**
 * Sorts the Vector
 */
public void sort() {
	sort(0, size() - 1);
}
	private void sort (int low, int high) {
	    int ptr;
	    
	    if (low < high) {
	        ptr = divide(low, high);
	        sort(low, ptr-1);
	        sort(ptr+1, high);
	    }
	}
	private void swap (int a, int b) {
	    Object tmp = elementAt(a);
	    setElementAt(elementAt(b),a);
	    setElementAt(tmp,b);
	}
}
