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
package com.salmonllc.localizer;

import com.salmonllc.util.VectorSort;
import java.util.Enumeration;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/localizer/Localizer.java $
//$Author: Dan $
//$Revision: 7 $
//$Modtime: 1/16/03 2:31p $
/////////////////////////

/**
 * This class is the base class for all localizers in the framework. A localizer is a class used to load resources for a particular language and applicaton
 */
public abstract class Localizer {

    private KeyList _list = new KeyList();
    private boolean _needsSort = false;
    private boolean _doSort = false;
    private boolean _translateEscapes = false;

    /**
    * Creates a new Localizer object. If elements added to the list will be sorted, pass a false as sortRequired and this will allow the class to skip the sorting step before searching for the first resource.
    */
   public Localizer(boolean sortRequired) {
       _doSort = sortRequired;
       _needsSort = sortRequired;
       _translateEscapes = false;
   }

    /**
    * Creates a new Localizer object. If elements added to the list will be sorted, pass a false as sortRequired and this will allow the class to skip the sorting step before searching for the first resource. You can also control whether or not escape sequences are translated to unicode characters with the translateEscapes flag (default = false).
    */
   public Localizer(boolean sortRequired, boolean translateEscapes) {
       this(sortRequired);
       _translateEscapes = translateEscapes;
   }


    private class LocaleValue {
        public String key;
        public String value;

        public LocaleValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return key + " " + value;
        }
    }

    private class KeyList extends VectorSort {
        public boolean compare(Object o1, Object o2) {
            String key1 = ((LocaleValue) o1).key;
            String key2 = ((LocaleValue) o2).key;
            if (key1 == null || key2 == null)
                return false;
            else
                return key1.compareTo(key2) < 0;
        }

        private int getValueIndex(String key) {
            if (key == null)
                return -1;
            else if (size() < 1)
                return -1;

            int start = 0;
            int end = size() - 1;
            int comp = -1;
            while (comp != 0) {
                int mid = (start + end) / 2;
                comp = key.compareTo(((LocaleValue) elementAt(mid)).key);
                if (comp == 0)
                    return mid;
                else {
                    if (start >= end)
                        return -1;
                    else if (comp > 0)
                        start = mid + 1;
                    else
                        end = mid - 1;
                }
            }
            return -1;
        }

    }

    private class LocaleEnum implements Enumeration {

        private Enumeration _listEnum;
        private boolean _keys;

        private LocaleEnum(KeyList l, boolean keys) {
            _listEnum = l.elements();
            _keys = keys;
        }

        public boolean hasMoreElements() {
            return _listEnum.hasMoreElements();
        }

        public Object nextElement() {
            LocaleValue o = (LocaleValue) _listEnum.nextElement();
            if (o == null)
                return null;
            else
                return _keys ? o.key : o.value;
        }

    }

    /**
     * Adds a value to the list
     */
    protected synchronized void addValue(String key, String value) {
        if (key != null && value != null) {
            if (_translateEscapes == true)
                 value = convert(value);
            _list.addElement(new LocaleValue(key, value));
            _needsSort = _doSort;
        }
    }

    //converts slash characters to double byte
    private String convert(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * Returns the number of resources in the localizer
     */
    public int getSize() {
        return _list.size();
    }

    /**
     * Returns the value at the specified key or null if no value is found
     */
    public String getResource(String key) {
        if (key != null) {
            if (_needsSort) {
                _list.sort();
                _needsSort = false;
            }
            int index = _list.getValueIndex(key);
            if (index == -1)
                return null;
            else
                return ((LocaleValue) _list.elementAt(index)).value;
        } else
            return null;
    }

    /**
     * Returns an enumeration of keys in the list
     */
    public synchronized Enumeration getKeys() {
        if (_needsSort) {
            _needsSort = _doSort;
            _list.sort();
        }
        return new LocaleEnum(_list, true);
    }

    /**
     * Returns an enumeration of resources in the list
     */
    public synchronized Enumeration getResources() {
        if (_needsSort) {
            _needsSort = _doSort;
            _list.sort();
        }
        return new LocaleEnum(_list, false);
    }

    /**
     * This method must be implemented by subclasses of the Localizer to load the data for one application and language
     */
    protected abstract boolean loadData(String appName, String language);

}