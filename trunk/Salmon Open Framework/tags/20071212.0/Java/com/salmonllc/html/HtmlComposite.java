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
package com.salmonllc.html;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/html/HtmlComposite.java $
//$Author: Dan $
//$Revision: 11 $
//$Modtime: 10/30/02 2:38p $
/////////////////////////

import com.salmonllc.util.MessageLog;

import java.util.Enumeration;
import java.util.Vector;

/**
 * This class is used as a typed container object. Primarily used with the HtmlLookup Object
 * and the list forms. Making the lookup component extend HtmlComposite allows the listform to interigate
 * the object for information to be able search on the value automatically.
 */
public class HtmlComposite extends HtmlContainer
{
    /** add the type for each comp ex. Datastore.DATA_TYPES integer values */
    private Vector _dataType = new Vector();

    /** is it bound to a column boolean values */
    private Vector _bound = new Vector();

    /** if yes which table.column */
    private Vector _tables = new Vector();
    private Vector _columns = new Vector();

    /** for use by user meaningless inside */
    private Vector _flags = new Vector();

    static final int DATATYPE_ANY = 99;

    /**
     * HtmlComposite constructor.
     * @param name String - name of composite component
     * @param p HtmlPage - page the component is associated with
     */
    public HtmlComposite(String name, HtmlPage p)
    {
        super(name, p);
    }

    /**
     * add
     *  DO NOT USE: HtmlComposite.add. Use addCompositeComponent instead
     * @param comp HtmlComponent
     */
    public void add(HtmlComponent comp)
    {
        try
        {
            throw new Exception(" DO NOT USE: HtmlComposite.add. Use addCompositeComponent instead");
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage(" DO NOT USE: HtmlComposite.add. Use addCompositeComponent instead", e, this);
        }
    }

    /**
     * This method adds a component and the associated information to a set of Vectors
     * it return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to add
     * @param dataType int - data type of component added
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to
     */
    public Object addCompositeComponent(HtmlComponent comp, int dataType, boolean bound, String table, String column)
    {
        return addCompositeComponent(comp, dataType, bound, table, column, 0);
    }

    /**
     * This method adds a component and the associated information to a set of Vectors
     * it return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to add
     * @param dataType int - data type of component added
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to
     * @param flags int - flags to associate to this componet
     */
    public Object addCompositeComponent(HtmlComponent comp, int dataType, boolean bound, String table, String column, int flags)
    {

        /** add componet contained */
        _componentsVec.addElement(comp);
        /** add the type for each comp ex. Datastore.DATA_TYPES integer values */
        _dataType.addElement(new Integer(dataType));
        /** is it bound to a column boolean values */
        _bound.addElement(new Boolean(bound));
        /** if yes which table.column */
        _tables.addElement(table);
        _columns.addElement(column);
        /** default value = 0 */
        _flags.addElement(new Integer(flags));
        comp.setParent(this);
        return comp;
    }

    /**
     * This method returns a component by index
     * @return  HtmlComponent
     * @param index int
     */
    public HtmlComponent getComponent(int index)
    {
        return (HtmlComponent) _componentsVec.elementAt(index);
    }

    /**
     * This method returns the column associated to this component.
     * @return String - column name
     * @param comp - HtmlComponent being searched for being searched for
     */
    public String getComponentColumn(HtmlComponent comp)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        String returnVal = null;
        if (index == -1)
        {
            return null;
        }
        else
        {
            returnVal = (String) _columns.elementAt(index);
            return returnVal;
        }
    }

    /**
     * This method returns the data type associated to this component.
     * @return int - index value
     * @param comp - HtmlComponent being searched for
     */
    public int getComponentDataType(HtmlComponent comp)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        int returnVal = -1;
        if (index == -1)
        {
            return returnVal;
        }
        else
        {
            returnVal = ((Integer) _dataType.elementAt(index)).intValue();
            return returnVal;
        }
    }

    /**
     * This method returns the flags associated to this component.
     * @return int - flag values
     * @param comp - HtmlComponent being searched for
     */
    public int getComponentFlags(HtmlComponent comp)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        int returnVal = -1;
        if (index == -1)
        {
            return returnVal;
        }
        else
        {
            returnVal = ((Integer) _flags.elementAt(index)).intValue();
            return returnVal;
        }
    }


    /**
     * Returns an enumeration of componets
     * @return - Enumeration
     */
    public Enumeration getComponents()
    {
        return _componentsVec.elements();
    }

    /**
     * This method returns the table associated to this component.
     * @return String - table name
     * @param comp - HtmlComponent being searched for
     */
    public String getComponentTable(HtmlComponent comp)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        String returnVal = null;
        if (index == -1)
        {
            return null;
        }
        else
        {
            returnVal = (String) _tables.elementAt(index);
            return returnVal;
        }
    }

    /**
     * This method returns the number of items in the composite component
     * @return int
     */
    public int getCompositeSize()
    {
        return _componentsVec.size();
    }

    /**
     * This method returns the whether the component is bound to a DataStore
     * @return boolean
     * @param comp - HtmlComponent being searched for
     */
    public boolean getIsComponentBound(HtmlComponent comp)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        boolean returnVal = false;
        if (index == -1)
        {
            return false;
        }
        else
        {
            returnVal = ((Boolean) _bound.elementAt(index)).booleanValue();
            return returnVal;
        }
    }

    /**
     * This method inserts a component and the associated information in to a set of Vectors at a specified location.
     * It return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to insert
     * @param dataType int - data type of component inserted
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to

     */
    public Object insertCompositeComponentAt(HtmlComponent comp, int index, int dataType, boolean bound, String table, String column)
    {
        return insertCompositeComponentAt(comp, index, dataType, bound, table, column, 0);
    }

    /**
     * This method inserts a component and the associated information in to a set of Vectors at a specified location.
     * It return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to insert
     * @param dataType int - data type of component added
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to
     * @param flags int - flags to associate to this componet
     */
    public Object insertCompositeComponentAt(HtmlComponent comp, int index, int dataType, boolean bound, String table, String column, int flags)
    {
        try
        {
            if (_componentsVec.isEmpty())
            {

                /** add componet contained */
                _componentsVec.addElement(comp);
                /** add the type for each comp ex. Datastore.DATA_TYPES integer values */
                _dataType.addElement(new Integer(dataType));
                /** is it bound to a column boolean values */
                _bound.addElement(new Boolean(bound));
                /** if yes which table.column */
                _tables.addElement(table);
                _columns.addElement(column);
                _flags.addElement(new Integer(flags));
            }
            else
            {
                /** add componet contained */
                _componentsVec.insertElementAt(comp, index);
                /** add the type for each comp ex. Datastore.DATA_TYPES integer values */
                _dataType.insertElementAt(new Integer(dataType), index);
                /** is it bound to a column boolean values */
                _bound.insertElementAt(new Boolean(bound), index);
                /** if yes which table.column */
                _tables.insertElementAt(table, index);
                _columns.insertElementAt(column, index);
                _flags.insertElementAt(new Integer(flags), index);
            }
            comp.setParent(this);
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("insertCompositeComponentAt", e, this);
        }
        return comp;
    }

    /**
     * This method replaces a component and the associated information with a new passed in object.
     * It return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to replace
     * @param dataType int - data type of component added
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to
     */
    public Object replaceCompositeComponent(HtmlComponent comp, Object compToReplace, int dataType, boolean bound, String table, String column)
    {
        return replaceCompositeComponent(comp, compToReplace, dataType, bound, table, column, 0);
    }

    /**
     * This method replaces a component and the associated information with a new passed in object.
     * It return and Object so you can keep a handle to the component for later use.
     * @return Object - reference to added component
     * @param comp HtmlComponent - component to replace
     * @param compToReplace Object - reference of object to replace
     * @param dataType int - data type of component added
     * @param bound boolean - is the component bound to a datastore
     * @param table String - table the component is bound to
     * @param column String - column the component is bound to
     */
    public Object replaceCompositeComponent(HtmlComponent comp, Object compToReplace, int dataType, boolean bound, String table, String column, int flags)
    {
        try
        {

            /** get the index of the edit field so we can replace it */
            int replaceIndex = _componentsVec.indexOf(compToReplace);
            if (replaceIndex != -1)
            {
                /** add componet contained */
                _componentsVec.setElementAt(comp, replaceIndex);
                /** add the type for each comp ex. Datastore.DATA_TYPES integer values */
                _dataType.setElementAt(new Integer(dataType), replaceIndex);
                /** is it bound to a column boolean values */
                _bound.setElementAt(new Boolean(bound), replaceIndex);
                /** if yes which table.column */
                _tables.setElementAt(table, replaceIndex);
                _columns.setElementAt(column, replaceIndex);

                _flags.setElementAt(new Integer(flags), replaceIndex);
                comp.setParent(this);
            }
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("replaceCompositeComponent", e, this);
        }
        return comp;
    }

    /**
     * This method sets the column the component is bound to in a DataStore
     * @param comp - HtmlComponent being searched for
     * @param column String - column name
     */
    public void setComponentColumn(HtmlComponent comp, String column)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        if (index == -1)
        {
            return;
        }
        else
        {
            _columns.setElementAt(column, index);
        }
    }

    /**
     * This method sets the data type of the column the component is bound to in a DataStore
     * @param comp - HtmlComponent being searched for
     * @param dataType int Please use the data types from DataStore.
     *  if you don't know what datatype use HtmlComposite.DATATYPE_ANY
     */
    public void setComponentDataType(HtmlComponent comp, int dataType)
    {
        int index = _componentsVec.indexOf(comp);
        if (index == -1)
        {
            return;
        }
        else
        {
            _dataType.setElementAt(new Integer(dataType), index);
        }
    }

    /**
     * This method sets the flags the component is using if any.
     * @param comp - HtmlComponent to associate flags to
     * @param flags - flag values
     */
    public void setComponentFlags(HtmlComponent comp, int flags)
    {
        int index = _componentsVec.indexOf(comp);
        if (index == -1)
        {
            return;
        }
        else
        {
            _flags.setElementAt(new Integer(flags), index);
        }
    }

    /**
     * This method sets the table the component is bound to in a DataStore
     * @param comp - HtmlComponent being searched for
     * @param table String
     */
    public void setComponentTable(HtmlComponent comp, String table)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        if (index == -1)
        {
            return;
        }
        else
        {
            _tables.setElementAt(table, index);
        }
    }

    /**
     * This method sets whether the component is bound to a table.column
     * @param comp - HtmlComponent being searched for
     * @param bound boolean
     */
    public void setIsComponentBound(HtmlComponent comp, boolean bound)
    {
        /** get index of comp */
        int index = _componentsVec.indexOf(comp);
        if (index == -1)
        {
            return;
        }
        else
        {
            _bound.setElementAt(new Boolean(bound), index);
        }
    }
}
