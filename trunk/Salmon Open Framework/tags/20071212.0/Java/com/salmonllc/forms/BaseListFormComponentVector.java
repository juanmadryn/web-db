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
//$Archive: /JADE/SourceCode/com/salmonllc/forms/BaseListFormComponentVector.java $
//$Author: Dan $ 
//$Revision: 5 $ 
//$Modtime: 10/30/02 2:58p $ 
/////////////////////////
import java.util.Vector;
/**
 * A type safe Vector that returns BaseListFormComponents and only accepts BaseListFormComponents
 */
public class BaseListFormComponentVector  {
	
	private Vector _elements ;

/**
 * BaseListFormElementsVector constructor comment.
 */
public BaseListFormComponentVector()
{
	_elements = new Vector();
}
/**
 * BaseListFormElementsVector constructor comment.
 * @param initialCapacity int
 */
public BaseListFormComponentVector(int initialCapacity) {
	_elements = new Vector(initialCapacity);
}
/**
 * BaseListFormElementsVector constructor comment.
 * @param initialCapacity int
 * @param capacityIncrement int
 */
public BaseListFormComponentVector(int initialCapacity, int capacityIncrement) {
	_elements = new Vector(initialCapacity, capacityIncrement);
}
/**
 * Adds the specified BaseListFormComponent to the end of this vector, 
 * increasing its size by one. The capacity of this vector is 
 * increased if its size becomes greater than its capacity. 
 *
 * @param   comp   the BaseListFormComponent to be added.
 */
public  void addElement(BaseListFormComponent comp)
{
	_elements.addElement(comp);
}
/**
 * Tests if the specified BaseListFormComponent is a component in this vector.
 *
 * @param   elem   a BaseListFormComponent.
 * @return  <code>true</code> if the specified object is a BaseListFormComponent in
 *          this vector; <code>false</code> otherwise.
 */
public final boolean contains(BaseListFormComponent elem)
{
	return _elements.contains(elem);
}
/**
 * Copies the BaseListFormComponent of this vector into the specified array. 
 * The array must be big enough to hold all the objects in this  vector.
 *
 * @param   anArray   the array into which the BaseListFormComponent get copied.
 */
public  void copyInto(BaseListFormComponent anArray[])
{
	_elements.copyInto(anArray);
}
/**
 * Returns the BaseListFormComponent at the specified index.
 *
 * @param      index   an index into this vector.
 * @return     the BaseListFormComponent at the specified index.
 * @exception  ArrayIndexOutOfBoundsException  if an invalid index was
 *               given.
 */
public  BaseListFormComponent elementAt(int index)
{
	return (BaseListFormComponent) _elements.elementAt(index);
}
/**
 * Searches for the first occurence of the given argument, testing 
 * for equality using the <code>equals</code> method. 
 *
 * @param   elem   an BaseListFormComponent.
 * @return  the index of the first occurrence of the argument in this
 *          vector; returns <code>-1</code> if the BaseListFormComponent is not found.
 * @see     java.lang.Object#equals(java.lang.Object)
 */
public final int indexOf(BaseListFormComponent elem)
{
	return _elements.indexOf(elem);
}
/**
 * Searches for the first occurence of the given argument, beginning 
 * the search at <code>index</code>, and testing for equality using 
 * the <code>equals</code> method. 
 *
 * @param   elem    an BaseListFormComponent.
 * @param   index   the index to start searching from.
 * @return  the index of the first occurrence of the BaseListFormComponent argument in
 *          this vector at position <code>index</code> or later in the
 *          vector; returns <code>-1</code> if the object is not found.
 * @see     java.lang.Object#equals(java.lang.Object)
 */
public  int indexOf(BaseListFormComponent elem, int index)
{
	return _elements.indexOf(elem, index);
}
/**
 * Inserts the specified BaseListFormComponent as a component in this vector at the 
 * specified <code>index</code>. Each component in this vector with 
 * an index greater or equal to the specified <code>index</code> is 
 * shifted upward to have an index one greater than the value it had 
 * previously. 
 * <p>
 * The index must be a value greater than or equal to <code>0</code> 
 * and less than or equal to the current size of the vector. 
 *
 * @param      comp     the BaseListFormComponent to insert.
 * @param      index   where to insert the new component.
 * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
 * @see        java.util.Vector#size()
 */
public  void insertElementAt(BaseListFormComponent comp, int index)
{
	_elements.insertElementAt(comp, index);
}
/**
 * Returns the index of the last occurrence of the specified BaseListFormComponent in
 * this vector.
 *
 * @param   elem   the desired BaseListFormComponent.
 * @return  the index of the last occurrence of the specified BaseListFormComponent in
 *          this vector; returns <code>-1</code> if the object is not found.
 */
public final int lastIndexOf(BaseListFormComponent elem)
{
	return _elements.lastIndexOf(elem);
}
/**
 * Searches backwards for the specified BaseListFormComponent, starting from the 
 * specified index, and returns an index to it. 
 *
 * @param   elem    the desired BaseListFormComponent.
 * @param   index   the index to start searching from.
 * @return  the index of the last occurrence of the specified BaseListFormComponent in this
 *          vector at position less than <code>index</code> in the vector;
 *          <code>-1</code> if the object is not found.
 */
public  int lastIndexOf(BaseListFormComponent elem, int index)
{
	return _elements.lastIndexOf(elem, index);
}
/**
 * Removes the first occurrence of the argument from this vector. If 
 * the BaseListFormComponent is found in this vector, each component in the vector 
 * with an index greater or equal to the object's index is shifted 
 * downward to have an index one smaller than the value it had previously.
 *
 * @param   comp   the component to be removed.
 * @return  <code>true</code> if the argument was a component of this
 *          vector; <code>false</code> otherwise.
 * @since   JDK1.0
 */
public boolean removeComp(BaseListFormComponent comp)
{
	return _elements.removeElement(comp);
}
/**
 * Sets the component at the specified <code>index</code> of this 
 * vector to be the specified BaseListFormComponent. The previous component at that 
 * position is discarded. 
 * <p>
 * The index must be a value greater than or equal to <code>0</code> 
 * and less than the current size of the vector. 
 *
 * @param      comp     what the component is to be set to.
 * @param      index   the specified index.
 * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
 * @see        java.util.Vector#size()
 */
public void setElementAt(BaseListFormComponent comp, int index)
{
	_elements.setElementAt(comp, index);
}
/**
 * Returns the number of components in this vector.
 *
 * @return  the number of components in this vector.
 */
public int size()
{
	return _elements.size();
}
}
