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
package com.salmonllc.personalization;

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;

/**
 * The base class for all SkinManager classes. The skin manager loads and saves skins to and from a storage medium
 */
public abstract class SkinManager{
   /*Use instead of the constructor to get the correct skin manager type for the application*/
    public static SkinManager getSkinManager(String appName) {
       Props p = Props.getProps(appName,null);
       String className = p.getProperty(Props.SKIN_MANAGER_CLASS,"com.salmonllc.personalization.FileSkinManager");
       try {
           Class c = Class.forName(className);
           SkinManager man = (SkinManager) c.newInstance();
           man.init(appName);
           return man;
       } catch (Exception e) {
           MessageLog.writeErrorMessage("SkinManager.getSkinManager()",e,null);
           return null;
       }
   }

    abstract void init(String appName);

   /*Loads the data for a skin with the specified name and append its attributes to the current skin*/
    public abstract void load(String name, Skin skin);

    /*Saves a skin under the specified name, if it exists it is overwritten*/
    public abstract void save(String name, Skin skin);

    /*Gets an array of available skin names*/
    public abstract String[] getSkinNames();

}
