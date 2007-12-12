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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/StoredProcedureParms.java $
//$Author: Fred $
//$Revision: 9 $
//$Modtime: 6/24/04 10:52a $
/////////////////////////


import java.util.*;

import com.salmonllc.util.*;

/**
 * This class is used to set parameters to pass to a SQL stored procedure. It should be used in conjunction with one of the data store executeProc methods.
 */
public class StoredProcedureParms {
    Vector _parms = new Vector();

    /**
     * Creates a new StoredProcedureParms instance.
     */
    public StoredProcedureParms() {
        super();
    }

    /**
     * Use this method to add a new parameter to be passed to the stored procedure. The order that parameters are set must correspond to the parameters in the stored procedure header.
     * @param parm The parameter to set\
     * @return StoredProcedureParms - This instance of StoredProcedureParms
     */
    public StoredProcedureParms addParm(Object parm) {
        TwoObjectContainer c = new TwoObjectContainer(parm, new Integer(DataStore.DATATYPE_ANY));
        _parms.addElement(c);
        return this;
    }

    /**
     * Use this method to add a new parameter to the stored procedure. The order that parameters are set must correspond to the parameters in the stored procedure header.
     * @param parm The parameter to set
     * @param dataType The dataType of the parameter. Valid values are all the DATATYPE constants in the data store;
     * @return StoredProcedureParms - This instance of StoredProcedureParms
     */
    public StoredProcedureParms addParm(Object parm, int dataType) {
        TwoObjectContainer c = new TwoObjectContainer(parm, new Integer(dataType));
        _parms.addElement(c);
        return this;
    }

    int getDataType(int index) {
        TwoObjectContainer cont = (TwoObjectContainer) _parms.elementAt(index);
        return ((Integer) cont.getObject2()).intValue();
    }

    Object getParm(int index) {
        TwoObjectContainer cont = (TwoObjectContainer) _parms.elementAt(index);
        return cont.getObject1();

    }

    int getParmCount() {
        return _parms.size();
    }

    /**
     * Use this method to clear all the parameters in the object.
     */
    public void reset() {
        _parms.removeAllElements();
    }
}
