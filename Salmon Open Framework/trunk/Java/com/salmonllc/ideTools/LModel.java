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
package com.salmonllc.ideTools;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/LModel.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 6/11/03 4:27p $
/////////////////////////
import javax.swing.*;
import java.util.Vector;

public class LModel extends AbstractListModel {
        private Vector _data;
    
        public LModel () {
           _data = new Vector();
        }
        public LModel (Vector data) {
           _data = data;
        }
        public void addElement(Object o) {
            _data.addElement(o);
            fireContentsChanged(o,_data.size() -1, _data.size() - 1);
        }
        public Object getElementAt(int i) {
            return _data.elementAt(i);
        }
        public int getSize() {
            return _data.size();
        }
        public void insertElementAt(Object o,int i) {
            _data.insertElementAt(o,i);
            fireContentsChanged(o,i,i);
        }
        public void remove(int i) {
            Object o = _data.elementAt(i);
            _data.remove(i);
            fireContentsChanged(o, i, i);
        }
        public void removeAllElements() {
            if (_data.size() > 0) {
                _data.removeAllElements();
                fireContentsChanged(null, 0, 0);
            }
        }
        public void setElementAt(Object o,int i) {
            _data.setElementAt(o,i);
            fireContentsChanged (o,i,i);
        }
}
