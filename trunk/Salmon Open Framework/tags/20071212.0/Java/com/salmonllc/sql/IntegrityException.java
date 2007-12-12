package com.salmonllc.sql;
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


/**
 * Indicates a referential integrity problem has occured with the database. Either a required row is missing in a parent table (foreign key) during an attempted row insert or update OR a table has rows where the primary key is still being used in child tables during an attempted row deletion.
 * @author  Tyler Williams http://www.dataterrace.com
 */
  public class IntegrityException extends DataStoreException {

    private IntegrityMessage _integrityMessage = null;
    private int _row = -1;

    /**
     * Constructs an Exception with no specified detail message.
     */
    public IntegrityException() {
        super();
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param msg the detail message.
     */
    public IntegrityException(String msg) {
      super(msg);
    }

    /**
     * Constructs an Exception with an IntegrityMessage containing details.
     * @param im The IntegrityMessage that contains details of the error.
     */
    public IntegrityException(IntegrityMessage im) {
      _integrityMessage = im;
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param msg the detail message.
     * @param im The IntegrityMessage that contains details of the error.
     */
    public IntegrityException(String msg, IntegrityMessage im) {
      this(msg);
      _integrityMessage = im;
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param msg the detail message.
     * @param im The IntegrityMessage that contains details of the error.
     * @param row The dataStore row number that caused the error.
     */
    public IntegrityException(String msg, IntegrityMessage im, int row) {
        this(msg);
        _integrityMessage = im;
        _row = row;
    }

    /**
     * Returns the IntegrityMessage that excapsulates cause of the error.
     * @return IntegrityMessage
     */
    public IntegrityMessage getIntegrityMessage() {
        return _integrityMessage;
    }

    /**
     * Returns the DataStore's row number that caused the error.
     * @return int
     */
    public int getRow() {
        return _row;
    }

  } // end of class
