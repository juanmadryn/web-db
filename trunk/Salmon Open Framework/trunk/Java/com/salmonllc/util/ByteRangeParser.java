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
package com.salmonllc.util;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/util/ByteRangeParser.java $
//$Author: Dan $
//$Revision: 8 $
//$Modtime: 10/30/02 2:59p $
/////////////////////////

import java.util.*;

/**
 * This class is used by the ObjectStore for byte range requests. Pass a byte range http header and the class will parse it.
 */
public class ByteRangeParser {
    StringTokenizer _tok;
    long _fileSize = 0;
    long _byteStart = 0;
    long _byteEnd = 0;

    /**
     * Create a new parser for a file of the specified size
     */
    public ByteRangeParser(String range, long fileSize) {
        super();
        _tok = new StringTokenizer(range, ",=", false);
        _fileSize = fileSize;
    }

    /**
     * returns the first btye of the current range
     */

    public long getFirstByte() {
        return _byteStart;
    }

    /**
     * returns the last btye of the current range
     */

    public long getLastByte() {
        return _byteEnd;
    }

    /**
     * parses the next range in specified in the header
     * @return true if there is another range
     */

    public boolean nextRange() {
        if (!_tok.hasMoreTokens())
            return false;

        String range = _tok.nextToken();
        if (range.equals("bytes"))
            range = _tok.nextToken();

        String work = "";

        int pos = range.indexOf("-");
        if (pos == -1)
            return false;
        else if (pos == 0) {
            work = range.substring(1).trim();
            _byteEnd = _fileSize - 1;
            _byteStart = _fileSize - Integer.parseInt(work);
        } else if (pos == (range.length() - 1)) {
            work = range.substring(0, range.length() - 1).trim();
            _byteEnd = _fileSize - 1;
            _byteStart = Integer.parseInt(work);
        } else {
            work = range.substring(0, pos).trim();
            _byteStart = Integer.parseInt(work);
            work = range.substring(pos + 1).trim();
            _byteEnd = Integer.parseInt(work);
        }

        if (_byteStart > _byteEnd)
            return false;
        else
            return true;
    }
}
