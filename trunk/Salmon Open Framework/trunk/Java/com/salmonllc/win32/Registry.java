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
package com.salmonllc.win32;

/**
 * Licensed Material - Property of Salmon LLC
 * (C) Copyright Salmon LLC. 1999 - All Rights Reserved
 * For more information go to www.salmonllc.com
 * *
 * *************************************************************************
 * DISCLAIMER:
 * The following code has been created by Salmon LLC. The code is provided
 * 'AS IS' , without warranty of any kind unless covered in another agreement
 * between your corporation and Salmon LLC.  Salmon LLC shall not be liable
 * for any damages arising out of your use of this, even if they have been
 * advised of the possibility of such damages.
 * *************************************************************************
 * *
 * This class is used to set a key in the registry. Use the setRegistryEntry method. This class requires REGUPDATE.dll
 * To be distributed with it.
 * Creation date: (4/16/02 11:43:10 AM)
 * @author: Fred Cahill
 */
public class Registry {
	public final static int HKEY_CLASSES_ROOT=1;
	public final static int HKEY_CURRENT_USER=2;
	public final static int HKEY_LOCAL_MACHINE=3;
	public final static int HKEY_USERS=4;
	private static boolean _isLoaded=false;

    private int _type;
    private String _key;

	static {
		loadJNILibrary("REGUPDATE");
	}

    public Registry(int iType,String sKey) {
        _type=iType;
        _key=sKey;
    }


/**
 * Use this method to get a registry value.
 * @param iType int This is a constant representing the key type. HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, HKEY_LOCAL_MACHINE, HKEY_USERS
 * @param sKey java.lang.String This is a string representing the key. e.g. "Software\\Macromedia\\Dreamweaver\\Sites\\-Site0"
 * @param sAttribute java.lang.String This is the attribute in that key to be gotten.
 * @return The value of the attribute.
 */
	private static native int getDWordRegistryEntry(int iType,String sKey,String sAttribute);
/**
 * Use this method to get a registry value.
 * @param iType int This is a constant representing the key type. HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, HKEY_LOCAL_MACHINE, HKEY_USERS
 * @param sKey java.lang.String This is a string representing the key. e.g. "Software\\Macromedia\\Dreamweaver\\Sites\\-Site0"
 * @param sAttribute java.lang.String This is the attribute in that key to be gotten.
 * @return The value of the attribute.
 */
	private static native String getRegistryEntry(int iType,String sKey,String sAttribute);
 /**
  * This method specifies the JNI Secure ID dll to use.
  * @param sLibrary The name of the dll to be loaded.
  */
 public static synchronized void loadJNILibrary(String sLibrary) {	
   try {
	 System.err.println("Start to load the library");
	 System.loadLibrary(sLibrary);
	 System.err.println("Library loaded");
	 _isLoaded=true;
	}
   catch (Error err) {
	 System.out.println(err);
	}
  }
/**
 * Insert the method's description here.
 * Creation date: (4/16/02 12:00:24 PM)
 * @param args java.lang.String[]
 */
public static void main(String[] args) {
	setRegistryEntry(HKEY_CURRENT_USER,"Software\\Test","TestKey","TestValue");
	System.out.println(getDWordRegistryEntry(HKEY_CURRENT_USER,"Software\\Macromedia\\Dreamweaver 4\\Sites\\-Summary","Number of Sites"));
}
/**
 * Use this method to set a registry value.
 * @param iType int This is a constant representing the key type. HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, HKEY_LOCAL_MACHINE, HKEY_USERS
 * @param sKey java.lang.String This is a string representing the key. e.g. "Software\\Macromedia\\Dreamweaver\\Sites\\-Site0"
 * @param sAttribute java.lang.String This is the attribute in that key to be set.
 * @param iValue int This is the value to set the attribute to.
 */
	private static native void setDWordRegistryEntry(int iType,String sKey,String sAttribute,int iValue);
/**
 * Use this method to set a registry value.
 * @param iType int This is a constant representing the key type. HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, HKEY_LOCAL_MACHINE, HKEY_USERS
 * @param sKey java.lang.String This is a string representing the key. e.g. "Software\\Macromedia\\Dreamweaver\\Sites\\-Site0"
 * @param sAttribute java.lang.String This is the attribute in that key to be set.
 * @param sValue java.lang.String This is the value to set the attribute to.
 */
	private static native void setRegistryEntry(int iType,String sKey,String sAttribute,String sValue);

    /**
     * Use this method to get a registry value.
     * @param sAttribute java.lang.String This is the attribute in the key to be gotten.
     * @return The value of the attribute.
     */
     public String getStringAttribute(String sAttribute) throws RegistryException {
        if (!_isLoaded)
          throw new RegistryException("REGUPDATE.DLL has not been loaded.");
        return getRegistryEntry(_type,_key,sAttribute);
     }

    /**
     * Use this method to get a registry value.
     * @param sAttribute java.lang.String This is the attribute in the key to be gotten.
     * @return The value of the attribute.
     */
    public int getIntAttribute(String sAttribute) throws RegistryException {
       if (!_isLoaded)
         throw new RegistryException("REGUPDATE.DLL has not been loaded.");
       return getDWordRegistryEntry(_type,_key,sAttribute);
    }


    /**
     * Use this method to set a registry value.
     * @param sAttribute java.lang.String This is the attribute in the key to be set.
     * @param iValue int This is the value to set the attribute to.
     */

    public void setIntAttribute(String sAttribute,int iValue) throws RegistryException {
       if (!_isLoaded)
         throw new RegistryException("REGUPDATE.DLL has not been loaded.");
       setDWordRegistryEntry(_type,_key,sAttribute,iValue);
    }

    /**
     * Use this method to set a registry value.
     * @param sAttribute java.lang.String This is the attribute in the key to be set.
     * @param sValue java.lang.String This is the value to set the attribute to.
     */

    public void setStringAttribute(String sAttribute,String sValue) throws RegistryException {
       if (!_isLoaded)
         throw new RegistryException("REGUPDATE.DLL has not been loaded.");
       setRegistryEntry(_type,_key,sAttribute,sValue);
    }

}
