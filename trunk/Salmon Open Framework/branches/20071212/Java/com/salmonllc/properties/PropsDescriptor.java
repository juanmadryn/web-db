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
package com.salmonllc.properties;

import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/properties/PropsDescriptor.java $
//$Author: Matt $ 
//$Revision: 14 $ 
//$Modtime: 5/19/04 4:07p $ 
/////////////////////////
import java.util.*;


class PropsDescriptor
{
    private Properties _props;
    private String     _name;
    private Vector     _subProps;
    private long       _lastMod;

    PropsDescriptor(Properties p, String name, long lastMod)
    {
        _name    = name;
        _props   = p;
        _lastMod = lastMod;

        if (!Util.isFilled(name))
        {
            MessageLog.writeErrorMessage(" PropsDescriptor Constuctor", new Exception("name not filled"), this);
        }
    }

    void setLastMod(long lastMod)
    {
        _lastMod = lastMod;
    }

    long getLastMod()
    {
        return _lastMod;
    }

    String getName()
    {
        return _name;
    }

    void setProperties(Properties p)
    {
        _props = p;
    }

    Properties getProperties()
    {
        return _props;
    }

    void setSubProperty(PropsDescriptor p)
    {
        if (_subProps == null)
        {
            _subProps = new Vector();
        }

        /*
         * srufle : May 17, 2004 7 : 13 : 40 PM
         * Added a check for PropsDescriptor._name
         *  - name field must have a value otherwise  PropsDescriptor getSubProperty(String name) will throw a NullPointer exception
         *
         */
        if (Util.isFilled(p.getName()))
        {
            for (int i = 0; i < _subProps.size(); i++)
            {
                PropsDescriptor p1 = (PropsDescriptor) _subProps.elementAt(i);

                if (p1.getName().equals(p.getName()))
                {
                    _subProps.setElementAt(p, i);

                    return;
                }
            }

            _subProps.addElement(p);
        }
        else
        {
            String msg = "p.getName()='" + p.getName() + "'";

            MessageLog.writeErrorMessage(msg,  new Exception(msg), this);
        }
    }

    PropsDescriptor getSubProperty(String name)
    {
        if (_subProps == null)
        {
            return null;
        }

        if (name == null)
        {
            return null;
        }

        for (int i = 0; i < _subProps.size(); i++)
        {
            PropsDescriptor propDescTest     = (PropsDescriptor) _subProps.elementAt(i);
            String          propDescTestName = propDescTest.getName();

            if (Util.isNull(propDescTestName))
            {
                MessageLog.writeErrorMessage(" propDescTestName=null passed name=" + name, null, this);

                for (int errI = 0; errI < _subProps.size(); errI++)
                {
                    PropsDescriptor propDescTestErr     = (PropsDescriptor) _subProps.elementAt(errI);
                    String          propDescTestNameErr = propDescTestErr.getName();
                    MessageLog.writeErrorMessage(" errI=" + errI, null, this);
                    MessageLog.writeErrorMessage(" propDescTestNameErr=" + propDescTestNameErr, null, this);
                }
            }

            if ((propDescTest != null) && propDescTestName != null && propDescTestName.equals(name))
            {
                return (PropsDescriptor) _subProps.elementAt(i);
            }
        }

        return null;
    }
}
