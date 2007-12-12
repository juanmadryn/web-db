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
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/DSQuickSort.java $
//$Author: Srufle $
//$Revision: 17 $
//$Modtime: 10/29/04 11:36a $
/////////////////////////

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * This class is public as an implementation detail. It should not be used outside the framework.
 */

public class DSQuickSort {
    public static String ROW="ROW";
    public static String SORTVALUE="SORTVALUE";
	Vector _list;
	int _cols[];
	int _dirs[];
	DataStoreEvaluator _eval;
	Object _objs[];
    Stack _stack=new Stack();
    Hashtable _hashmaps=new Hashtable();

    private class DataStoreSort extends  DataStoreBuffer {
        DataStoreSort() {
            super();
            addBucket(ROW,DATATYPE_INT);
            addBucket(SORTVALUE,DATATYPE_STRING);
        }

        void setColumnDataType(int column,int type) throws DataStoreException {
            if (column < 0 || column >= _desc.getColumnCount())
                throw new DataStoreException("Specified column (" + column + ") is out of range.");

            DSColumnDescriptor des = _desc.getColumn(column);
            des.setType(type);
        }

        void setColumnDataType(String column,int type) throws DataStoreException {
            int col = getColumnIndex(column);
            setColumnDataType(col,type);
        }

    }


	DSQuickSort(Vector list, int cols[], int dirs[]) {
        this(list,cols,dirs,false);
	}
    DSQuickSort(Vector list, int cols[], int dirs[],boolean bUseQSort) {
        _cols = cols;
        _dirs = dirs;
        _list = list;
        if (_list.size()<500 || bUseQSort)
            sort(0, _list.size() - 1);
        else
            sort();
    }
	DSQuickSort(Vector list, Object eval[], int dirs[]) {
        this(list,eval,dirs,false);
	}
    DSQuickSort(Vector list, Object eval[], int dirs[],boolean bUseQSort ) {
        _objs = eval;
        _dirs = dirs;
        _list = list;
        if (_list.size()<500 || bUseQSort)
            sort(0, _list.size() - 1);
        else
            sort();
    }
	DSQuickSort(Vector list, DataStoreEvaluator eval, int dir) {
        this(list,eval,dir,false);
	}
    DSQuickSort(Vector list, DataStoreEvaluator eval, int dir,boolean bUseQSort ) {
        _eval = eval;
        _dirs = new int[1];
        _dirs[0] = dir;
        _list = list;
        if (_list.size()<500 || bUseQSort)
            sort(0, _list.size() - 1);
        else
            sort();
    }
    private int divide (int low, int high) {
        int i, ptr;

        DSDataRow key = (DSDataRow) _list.elementAt(low);
        ptr = low;

        for (i=low+1; i<=high; i++) {
                if (_eval != null) {
                    if (((DSDataRow) _list.elementAt(i)).compareExpressions(_eval, i, low,_dirs[0]))
                        swap(++ptr,i);
                }
                else if (_objs != null) {
                    if ( ((DSDataRow) _list.elementAt(i)).compareRows(i, low, key, _objs, _dirs))
                        swap(++ptr,i);
                }
                else if ( ((DSDataRow) _list.elementAt(i)).compareRows(key,_cols,_dirs)) {
                    swap(++ptr,i);
                }


        }
        swap(low,ptr);
        return ptr;
    }

	private void sort(int low, int high) {
		int ptr;
        _stack.ensureCapacity(high-low);

        _stack.push(new int[]{low,high});
        do {
            int[] hl=(int[])_stack.pop();
            low=hl[0];
            high=hl[1];
            while (low<high) {
                ptr = divide(low, high);
                if ((ptr-low)<(high-ptr)) {
                    _stack.push(new int[]{ptr+1,high});
                    high=ptr-1;
                }
                else {
                    _stack.push(new int[]{low,ptr-1});
                    low=ptr+1;
                }
            }
            Thread.yield();
        } while (_stack.size()>0);
/*		if (low < high) {
			ptr = divide(low, high);
            sort(low, ptr - 1);
            sort(ptr + 1, high);
		}*/
	}

	private void swap(int a, int b) {
        if (a==b)
          return;
		Object tmp = _list.elementAt(a);
		_list.setElementAt(_list.elementAt(b), a);
		_list.setElementAt(tmp, b);
	}

    private void createHashMaps() {
            int iType=-1;
            int iOptStringHashMapLength=1;
            int iOptNumberDivider=100;
            for (int i=0;i<_list.size();i++) {
                Object o=null;
                if (_eval != null) {
                    try {
                        o=_eval.evaluateRow(i);
                    }
                    catch(DataStoreException dse) {
                        ;
                    }
                }
                else if (_objs!=null)
                {

                    if (_objs[i] instanceof DataStoreEvaluator) {
                        DataStoreEvaluator de = (DataStoreEvaluator) _objs[0];
                        try {
                            o = de.evaluateRow(i);
                        } catch (Exception e) {
                            o = null;
                        }

                    } else {
                        DSDataRow dsRow=(DSDataRow)_list.elementAt(i);
                        o = dsRow.getData(((Integer) _objs[0]).intValue());
                    }

                } else {
                    DSDataRow dsRow=(DSDataRow)_list.elementAt(i);
                        Object value;

                        value = dsRow.getData(_cols[0]);
                    o=value;
                }
                if (iType==-1 && o!=null) {
                    if (o instanceof Number)
                      iOptNumberDivider=optimalNumberHashMapDivider(_list,_eval,_objs==null?null:_objs[0],_cols==null?0:_cols[0]);
//                    System.out.println("iOptNumberDivider="+iOptNumberDivider);
                    if (o instanceof String) {
                      iType=DataStoreBuffer.DATATYPE_STRING;
                      iOptStringHashMapLength=optimalStringHashMapLength(_list,_eval,_objs==null?null:_objs[0],_cols==null?0:_cols[0]);
//                      System.out.println("iOptStringHashMapLength="+iOptStringHashMapLength);
                    }
                    else if (o instanceof Short) {
                        iType=DataStoreBuffer.DATATYPE_SHORT;
                    } else if (o instanceof Integer) {
                        iType=DataStoreBuffer.DATATYPE_INT;
                    } else if (o instanceof Long) {
                        iType=DataStoreBuffer.DATATYPE_LONG;
                    } else if (o instanceof Float) {
                        iType=DataStoreBuffer.DATATYPE_FLOAT;
                    } else if (o instanceof Double) {
                        iType=DataStoreBuffer.DATATYPE_DOUBLE;
                    } else if (o instanceof Timestamp) {
                        iType=DataStoreBuffer.DATATYPE_DATETIME;
                    }  else if (o instanceof Date) {
                        iType=DataStoreBuffer.DATATYPE_DATE;
                    }  else if (o instanceof Time) {
                        iType=DataStoreBuffer.DATATYPE_TIME;
                    }
                    else
                        iType=DataStoreBuffer.DATATYPE_BYTEARRAY;
                }
                if (o==null)
                  o="";
                Object oMapKey=o;
                if (!o.equals("")) {
                    switch (iType) {
                      case DataStoreBuffer.DATATYPE_STRING:
                                String sValue=o.toString();
                                oMapKey=sValue.substring(0,iOptStringHashMapLength>sValue.length()?sValue.length():iOptStringHashMapLength);
                            break;
                      case DataStoreBuffer.DATATYPE_SHORT:
                            short s=((Short)o).shortValue();
                            s=(short)(s/iOptNumberDivider);
                            oMapKey=new Short(s);
                            break;
                      case DataStoreBuffer.DATATYPE_INT:
                            int in=((Integer)o).intValue();
                            in=(int)(in/iOptNumberDivider);
                            oMapKey=new Integer(in);
                            break;
                        case DataStoreBuffer.DATATYPE_LONG:
                            long l=((Long)o).longValue();
                            l=(long)(l/iOptNumberDivider);
                            oMapKey=new Long(l);
                              break;
                        case DataStoreBuffer.DATATYPE_FLOAT:
                            float f=((Float)o).floatValue();
                            f=(float)((long)(f/iOptNumberDivider));
                            oMapKey=new Float(f);
                              break;
                        case DataStoreBuffer.DATATYPE_DOUBLE:
                            double d=((Double)o).doubleValue();
                            d=(double)((long)(d/iOptNumberDivider));
                            oMapKey=new Double(d);
                              break;
                        case DataStoreBuffer.DATATYPE_DATETIME:
                            GregorianCalendar gco=new GregorianCalendar();
                            gco.setTime((Timestamp)o);
                            gco.set(Calendar.DATE,1);
                            gco.set(Calendar.HOUR_OF_DAY,0);
                            gco.set(Calendar.MINUTE,0);
                            gco.set(Calendar.SECOND,0);
                            gco.set(Calendar.MILLISECOND,0);
                            oMapKey=new Timestamp(gco.getTime().getTime());
                              break;
                        case DataStoreBuffer.DATATYPE_DATE:
                            gco=new GregorianCalendar();
                            gco.setTime((Timestamp)o);
                            gco.set(Calendar.MONTH,0);
                            gco.set(Calendar.DATE,1);
                            gco.set(Calendar.HOUR_OF_DAY,0);
                            gco.set(Calendar.MINUTE,0);
                            gco.set(Calendar.SECOND,0);
                            gco.set(Calendar.MILLISECOND,0);
                            oMapKey=new Date(gco.getTime().getTime());
                            break;
                        case DataStoreBuffer.DATATYPE_TIME:
                            gco=new GregorianCalendar();
                            gco.setTime((Timestamp)o);
                            gco.set(Calendar.MINUTE,0);
                            gco.set(Calendar.SECOND,0);
                            gco.set(Calendar.MILLISECOND,0);
                            oMapKey=new Time(gco.getTime().getTime());
                            break;
                    }
                }
                DataStoreSort dss=(DataStoreSort)_hashmaps.get(oMapKey);
                if (dss==null) {
                   dss=new DataStoreSort();
                    if (_cols!=null) {
                        for (int j = 0; j < _cols.length; j++) {
                          dss.addBucket("COL"+j,DataStoreBuffer.DATATYPE_STRING);
                        }
                    }
                    if (_objs!=null) {
                        for (int j = 0; j < _objs.length; j++) {
                          dss.addBucket("COL"+j,DataStoreBuffer.DATATYPE_STRING);
                        }
                    }
                   _hashmaps.put(oMapKey,dss);
                }
                dss.insertRow();
                try {
                 dss.setInt(ROW,i);
                 if (_eval!=null) {
                     if (!o.toString().equals("") && dss.getColumnDataType(SORTVALUE)==DataStoreBuffer.DATATYPE_STRING) {
                         switch (iType) {
                            case DataStoreBuffer.DATATYPE_STRING:
                                 break;
                             case DataStoreBuffer.DATATYPE_SHORT:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_SHORT);
                                  break;
                             case DataStoreBuffer.DATATYPE_INT:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_INT);
                                  break;
                             case DataStoreBuffer.DATATYPE_LONG:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_LONG);
                                  break;
                             case DataStoreBuffer.DATATYPE_FLOAT:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_FLOAT);
                                  break;
                             case DataStoreBuffer.DATATYPE_DOUBLE:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_DOUBLE);
                                  break;
                             case DataStoreBuffer.DATATYPE_DATETIME:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_DATETIME);
                                  break;
                             case DataStoreBuffer.DATATYPE_DATE:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_DATE);
                                  break;
                             case DataStoreBuffer.DATATYPE_TIME:
                                 dss.setColumnDataType(SORTVALUE,DataStoreBuffer.DATATYPE_TIME);
                                  break;

                         }
                     }
                     dss.setAny(SORTVALUE,o);
                 }
                 else if (_objs!=null) {
                     DSDataRow dsRow=(DSDataRow)_list.elementAt(i);
                     for (int j = 0; j < _objs.length; j++) {
                       Object value=null;
                         if (_objs[j] instanceof DataStoreEvaluator) {
                             DataStoreEvaluator de = (DataStoreEvaluator) _objs[j];
                             try {
                                 value = de.evaluateRow(i);
                             } catch (Exception e) {
                                 value = null;
                             }

                         } else {
                             value = dsRow.getData(((Integer) _objs[j]).intValue());
                         }
                       if (value!=null && dss.getColumnDataType("COL"+j)==DataStoreBuffer.DATATYPE_STRING) {
                           if (value instanceof String) {
                           } else if (value instanceof Short) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_SHORT);
                           } else if (value instanceof Integer) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_INT);
                           } else if (value instanceof Float) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_FLOAT);
                           } else if (value instanceof Long) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_LONG);
                           } else if (value instanceof Double) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DOUBLE);
                           } else if (value instanceof Timestamp) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DATETIME);
                           } else if (value instanceof java.sql.Date) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DATE);
                           } else if (value instanceof java.sql.Time) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_TIME);
                           } else if (value instanceof byte[]) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_BYTEARRAY);
                           }
                       }
                       dss.setAny("COL"+j,value);
                     }
                   } else {
                     DSDataRow dsRow=(DSDataRow)_list.elementAt(i);
                     for (int j = 0; j < _cols.length; j++) {
                       Object value=null;
                       value = dsRow.getData(_cols[j]);
                       if (value!=null && dss.getColumnDataType("COL"+j)==DataStoreBuffer.DATATYPE_STRING) {
                           if (value instanceof String) {
                           } else if (value instanceof Short) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_INT);
                           } else if (value instanceof Integer) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_INT);
                           } else if (value instanceof Float) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_FLOAT);
                           } else if (value instanceof Long) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_LONG);
                           } else if (value instanceof Double) {
                                dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DOUBLE);
                           } else if (value instanceof Timestamp) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DATETIME);
                           } else if (value instanceof java.sql.Date) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_DATE);
                           } else if (value instanceof java.sql.Time) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_TIME);
                           } else if (value instanceof byte[]) {
                               dss.setColumnDataType("COL"+j,DataStoreBuffer.DATATYPE_BYTEARRAY);
                           }
                       }
                       dss.setAny("COL"+j,value);
                     }
                  }
                }
                catch (DataStoreException dse) {
                   dse.printStackTrace();
                }
                Thread.yield();
            }
//        System.out.println("The number of maps="+_hashmaps.size());
    }
	private void sort() {

//        long lStart=System.currentTimeMillis();
        createHashMaps();
//        long lEnd=System.currentTimeMillis();
//        System.out.println("create Hash maps time take="+(lEnd-lStart));
        DataStoreBuffer dsKeys=new DataStoreBuffer();
        Enumeration enumMaps=_hashmaps.keys();
        while (enumMaps.hasMoreElements()) {
           Object oKey=enumMaps.nextElement();
           if (!(oKey instanceof String) || (oKey instanceof String && !oKey.equals("")))  {
               try {
               if (dsKeys.getColumnCount()==0) {
                   if (oKey instanceof String) {
                     dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_STRING);
                   } else if (oKey instanceof Short) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_SHORT);
                   }else if (oKey instanceof Integer) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_INT);
                   }else if (oKey instanceof Float) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_FLOAT);
                   }else if (oKey instanceof Long) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_LONG);
                   }else if (oKey instanceof Double) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_DOUBLE);
                   } else if (oKey instanceof Timestamp) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_DATETIME);
                   } else if (oKey instanceof java.sql.Date) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_DATE);
                   } else if (oKey instanceof java.sql.Time) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_TIME);
                   } else if (oKey instanceof byte[]) {
                       dsKeys.addBucket("KEY",DataStoreBuffer.DATATYPE_BYTEARRAY);
                   }
//                   System.out.println("KEY type "+dsKeys.getColumnDataType("KEY"));
               }
               dsKeys.insertRow();
//                    System.out.println("KEY added "+oKey);
                    dsKeys.setAny("KEY",oKey);
               }
               catch (DataStoreException dse) {
                  dse.printStackTrace();
               }
           }
           DataStoreSort dss=(DataStoreSort)_hashmaps.get(oKey);
//           System.out.println("dss.getRowCount()="+dss.getRowCount());
           DSQuickSort dqs=null;
           if (!(oKey instanceof String) || (oKey instanceof String && !oKey.equals(""))) {
             int[] cols=null;
             if (_eval!=null) {
               cols=new int[] {1};
             } else if (_objs!=null) {
                 cols=new int[_objs.length];
                 for (int i=0;i<_objs.length;i++)
                   cols[i]=i+2;
             } else {
                cols=new int[_cols.length];
                for (int i=0;i<_cols.length;i++)
                  cols[i]=i+2;
             }
             try {
                if (!areRowsEqual((Vector)dss.getRows().elementAt(0),cols))
                    if (dss.getRowCount()>500 && _hashmaps.size()!=1) {
                        dss.sort(cols,_dirs);
                    }
                    else
                        dss.sort(cols,_dirs,true);
             }
             catch (DataStoreException dse) {
                 dse.printStackTrace();
             }
           }
           else {
               int[] cols=null;
               int[] dirs=null;
               if (_eval==null) {
                  if (_objs!=null) {
                      dirs=new int[_objs.length-1];
                      cols=new int[_objs.length-1];
                      for (int i=0;i<_objs.length-1;i++) {
                        cols[i]=i+3;
                        dirs[i]=_dirs[i+1];
                      }
                  }
                  else {
                      dirs=new int[_cols.length-1];
                      cols=new int[_cols.length-1];
                      for (int i=0;i<_cols.length-1;i++) {
                        cols[i]=i+3;
                        dirs[i]=_dirs[i+1];
                      }
                  }
                   try {
                       if (!areRowsEqual((Vector)dss.getRows().elementAt(0),cols))
                         if (dss.getRowCount()>500 && _hashmaps.size()!=1) {
                             dss.sort(cols,dirs);
                         }
                         else
                             dss.sort(cols,dirs,true);
                   }
                   catch (DataStoreException dse) {
                       dse.printStackTrace();
                   }
               }
           }
        }
        try {
            if (dsKeys.getRowCount()>0)
                dsKeys.sort(new int[] {0},new int[] {_dirs[0]});
//            for (int i=0;i<dsKeys.getRowCount();i++) {
//              System.out.println("Sorted Key "+i+" is "+dsKeys.getAny(i,"KEY"));
//            }
        }
        catch (DataStoreException dse) {
            dse.printStackTrace();
        }
        Vector vList=(Vector)_list.clone();
        _list.removeAllElements();
          if (_dirs[0]==DataStoreBuffer.SORT_ASC) {
            DataStoreSort dssNull=(DataStoreSort)_hashmaps.get("");
            if (dssNull!=null) {
              try {
                  for (int i=0;i<dssNull.getRowCount();i++) {
                     _list.addElement(vList.elementAt(dssNull.getInt(i,ROW)));
                  }
              }
              catch (DataStoreException dse) {
                dse.printStackTrace();
              }
            }
            try {
                for (int j=0;j<dsKeys.getRowCount();j++) {
//                    System.out.println("Key is "+dsKeys.getAny(j,"KEY"));
                    DataStoreSort dssKey=(DataStoreSort)_hashmaps.get(dsKeys.getAny(j,"KEY"));
                    for (int i=0;i<dssKey.getRowCount();i++) {
//                       System.out.println("Row No is "+dssKey.getInt(i,ROW));
                       _list.addElement(vList.elementAt(dssKey.getInt(i,ROW)));
                    }
                    Thread.yield();
                }
            }
            catch (DataStoreException dse) {
              dse.printStackTrace();
            }
          }
          else {
              try {
                  for (int j=0;j<dsKeys.getRowCount();j++) {
                      DataStoreSort dssKey=(DataStoreSort)_hashmaps.get(dsKeys.getAny(j,"KEY"));
                      for (int i=0;i<dssKey.getRowCount();i++) {
                         _list.addElement(vList.elementAt(dssKey.getInt(i,ROW)));
                      }
                      Thread.yield();
                  }
              }
              catch (DataStoreException dse) {
                dse.printStackTrace();
              }
              DataStoreSort dssNull=(DataStoreSort)_hashmaps.get("");
              if (dssNull!=null) {
                try {
                    for (int i=0;i<dssNull.getRowCount();i++) {
                       _list.addElement(vList.elementAt(dssNull.getInt(i,ROW)));
                    }
                }
                catch (DataStoreException dse) {
                  dse.printStackTrace();
                }
              }
          }
/*        for (int i=0;i<_list.size();i++) {
            System.out.println("_list["+i+"] col(0) is "+((DSDataRow)_list.elementAt(i)).getData(_cols[0]));
        }*/
	}

    private boolean areRowsEqual (Vector vRows,int[] cols) {

        DSDataRow key = (DSDataRow) vRows.elementAt(0);

        for (int i=1; i<vRows.size(); i++) {
                if (!((DSDataRow) vRows.elementAt(i)).equalRows(key,cols)) {
                    return false;
                }
        }
        return true;
    }

    private int optimalStringHashMapLength (Vector vRows,DataStoreEvaluator eval,Object object,int column) {
        String sValue=null;
        DSDataRow key=null;
        int i=0;
        while (sValue==null && i<vRows.size()) {
            key = (DSDataRow) vRows.elementAt(i);
            if (eval!=null) {
                try {
                    sValue=(String)eval.evaluateRow(i);
                }
                catch (DataStoreException dse) {
                  dse.printStackTrace();
                }
            }
            else if (object!=null) {
              if (object instanceof DataStoreEvaluator) {
                  try {
                      sValue=(String)((DataStoreEvaluator)object).evaluateRow(i);
                  }
                  catch (DataStoreException dse) {
                    dse.printStackTrace();
                  }
              }
              else
                  sValue=(String)key.getData(((Integer)object).intValue());
            }
            else
                sValue=(String)key.getData(column);
            i++;
        }
        if (sValue==null)
          return 1;
        int iOptSize=sValue.length();
        outer:
        for (; i<vRows.size(); i++) {
            key = (DSDataRow) vRows.elementAt(i);
            String sValue2=null;
            if (eval!=null) {
                try {
                    sValue2=(String)eval.evaluateRow(i);
                }
                catch (DataStoreException dse) {
                  dse.printStackTrace();
                }
            }
            else if (object!=null) {
              if (object instanceof DataStoreEvaluator) {
                  try {
                      sValue2=(String)((DataStoreEvaluator)object).evaluateRow(i);
                  }
                  catch (DataStoreException dse) {
                    dse.printStackTrace();
                  }
              }
              else
                  sValue2=(String)key.getData(((Integer)object).intValue());
            }
            else
                sValue2=(String)key.getData(column);
            if (sValue2==null)
              continue;
            if (sValue2.length()<iOptSize)
              iOptSize=sValue2.length();
            while (!sValue.regionMatches(0,sValue2,0,iOptSize)) {
                iOptSize--;
                if (iOptSize==1)
                    break outer;
            }
        }
        return iOptSize<=1?2:iOptSize+1;
    }

    private int optimalNumberHashMapDivider (Vector vRows,DataStoreEvaluator eval,Object object,int column) {
        double dMin=Double.MAX_VALUE;
        double dMax=Double.MIN_VALUE;
        DSDataRow key=null;
        for (int i=0; i<vRows.size();i++) {
            Number nValue=null;
            key = (DSDataRow) vRows.elementAt(i);
            if (eval!=null) {
                try {
                    nValue=(Number)eval.evaluateRow(i);
                }
                catch (DataStoreException dse) {
                  dse.printStackTrace();
                }
            }
            else if (object!=null) {
              if (object instanceof DataStoreEvaluator) {
                  try {
                      nValue=(Number)((DataStoreEvaluator)object).evaluateRow(i);
                  }
                  catch (DataStoreException dse) {
                    dse.printStackTrace();
                  }
              }
              else
                  nValue=(Number)key.getData(((Integer)object).intValue());
            }
            else
                nValue=(Number)key.getData(column);
            if (nValue==null)
              continue;
            if (nValue.doubleValue()<dMin)
                  dMin=nValue.doubleValue();
            if (nValue.doubleValue()>dMax)
                 dMax=nValue.doubleValue();
        }
//        System.out.println("dMax is "+dMax);
//        System.out.println("dMin is "+dMin);
        int iDivider=100;
        if (Math.abs(dMax-dMin)!=0 && Math.abs(dMax-dMin)<20)
          iDivider=1;
        else
         iDivider=(int)(Math.abs(dMax-dMin)/20);
        return iDivider==0?100:iDivider;
    }


}
