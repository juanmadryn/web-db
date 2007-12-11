package com.salmonllc.html;

import java.sql.Time;
import java.sql.Timestamp;

import com.salmonllc.util.VectorSort;

/**
 * @author srufle
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

    /**
     * Claudio Pi (4-01-2003) Inner class to allow drop down options sorting.
     */
    public class OptionsSort extends VectorSort
    {
    	 /*Claudio Pi (4-01-2003)
       Possible sort directions*/
    public static final int SORT_ASC = 0;
    public static final int SORT_DES = 1;

        private int _sortDir = SORT_ASC;

        public void setSortDir(int dir)
        {
            this._sortDir = dir;
        }

        public boolean compare(Object o1, Object o2)
        {
            boolean result = false;

            //Compares the values not the keys.
            Object toCompare1 = ((HtmlOption) o1).getDisplay();
            Object toCompare2 = ((HtmlOption) o2).getDisplay();

            if (toCompare1 instanceof String && toCompare2 instanceof String)
            {
                result = (((String) toCompare1).compareTo((String) toCompare2) > 0);
            }

            // srufle 04-03-2003: I do not think we need anything but the string comparision,but i am leaving it in for now
            if (toCompare1 instanceof Integer && toCompare2 instanceof Integer)
            {
                result = (((Integer) toCompare1).compareTo((Integer) toCompare2) > 0);
            }

            if (toCompare1 instanceof Timestamp && toCompare2 instanceof Timestamp)
            {
                result = (((Timestamp) toCompare1).compareTo((Timestamp) toCompare2) > 0);
            }

            if (toCompare1 instanceof Time && toCompare2 instanceof Time)
            {
                result = (((Time) toCompare1).compareTo((Time) toCompare2) > 0);
            }

            if (toCompare1 instanceof java.sql.Date && toCompare2 instanceof java.sql.Date)
            {
                result = (((java.sql.Date) toCompare1).compareTo((java.sql.Date) toCompare2) > 0);
            }

            if (toCompare1 instanceof Double && toCompare2 instanceof Double)
            {
                result = (((Double) toCompare1).compareTo((Double) toCompare2) > 0);
            }

            if (toCompare1 instanceof Short && toCompare2 instanceof Short)
            {
                result = (((Short) toCompare1).compareTo((Short) toCompare2) > 0);
            }

            if (toCompare1 instanceof Long && toCompare2 instanceof Long)
            {
                result = (((Long) toCompare1).compareTo((Long) toCompare2) > 0);
            }

            if (toCompare1 instanceof Float && toCompare2 instanceof Float)
            {
                result = (((Float) toCompare1).compareTo((Float) toCompare2) > 0);
            }

            if (_sortDir == SORT_DES)
            {
                return result;
            }
            else
            {
                return !result;
            }
        }
    }