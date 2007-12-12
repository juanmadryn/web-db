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

import java.util.Vector;
/**
 * @author Tyler Williams http://www.dataterrace.com
 */
public class IntegrityMessage  {
  private boolean _integrityFlag = true;
  // _violations will be null if _integrityFlag is true
  private Vector _violations;

  /**
   * Constructs a new empty integrity message.
   */
  public IntegrityMessage () { }

  /**
   * Constructs a new integrity message and adds a violation.
   * @param tableName The name of the table that violated an integrity rule
   * @param columnName The name of the column that violated an integrity rule
   */
  public IntegrityMessage (String tableName, String columnName) {
    addViolation(tableName,columnName);
  }

  /**
   * Adds a a violation to an existing integrity message.
   * @param tableName The name of the table that violated an integrity rule
   * @param columnName The name of the column that violated an integrity rule
   */
  public void addViolation (String tableName, String columnName) {
    if (_violations == null)
        _violations = new Vector();
    _integrityFlag = false;
    _violations.add(new String[]{tableName, columnName});
  }

  /**
   * Returns true-good integrity, false-failed integrity check...violations available.
   * @return boolean
   */
  public boolean getIntegrityFlag () {
    return _integrityFlag;
  }

  /**
   * Returns vector of all rule violations.
   * @return vector Each row in the vector contains a two string arrary (table name, column name)
   */
  public Vector getViolations () {
    return _violations;
  }
}
