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

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.InflaterInputStream;

/**
 * This type was created in VisualAge.
 */
class DSDataSourceProxy implements Serializable {
    String _url;
    String _sessionId;
    ObjectInputStream _in;
    ObjectOutputStream _out;
    String _userID;
    String _pw;
    String _proxyUserID;
    String _proxyPw;
    URLConnection _conn;
    int _count;
    DataStoreProxy _ds;

    /**
     * DSDataSourceRemote constructor comment.
     */
    public DSDataSourceProxy(String url, String sessionId, String userID, String pw, String proxyUserID, String proxyPassword, String criteria, DataStoreProxy ds) throws DataStoreException {
        super();
        _url = url;
        _sessionId = sessionId;
        _userID = userID;
        _proxyUserID = proxyUserID;
        _proxyPw = proxyPassword;
        _pw = pw;
    	_ds = ds;

        try {
            URLConnection conn = openConnection(ds, url, sessionId, DataStoreProxy.OPERATION_CREATE, criteria, userID, pw);
            Properties p = new Properties();
            ObjectInputStream in = getInputStream(conn);
            int result = getResponse(conn);
            if (result == DataStoreProxy.REMOTE_STATUS_OK) {
                byte[] b = (byte[]) in.readObject();
                p.load(new ByteArrayInputStream(b));
                ds.setProperties(p);

                Integer col = (Integer) in.readObject();
                while (col.intValue() != -1) {
                    int colNo = col.intValue();
                    ValidationRule val[] = (ValidationRule[]) in.readObject();
                    for (int j = 0; j < val.length; j++)
                        ds.addValidationRule(colNo, val[j]);
                    col = (Integer) in.readObject();
                }
                in.close();
                _out.close();

            } else {
                if (result == DataStoreProxy.REMOTE_STATUS_BAD_REQUEST) {
                    ObjectInputStream oIn = new ObjectInputStream(in);
                    Object o = oIn.readObject();
                    if (o != null && o instanceof DataStoreException)
                        throw((DataStoreException) o);
                }
                handleError(result, null);
            }
        } catch (Exception ex) {
            throw new DataStoreException(ex.toString(), ex);
        }
    }

    /**
     * preRetrieve method comment.
     */
    public void cancelRetrieve(DataStoreProxy ds) throws Exception {
        try {
            _count = 0;
            URLConnection conn = null;
            conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_CANCEL, null, _userID, _pw);
            InputStream is = conn.getInputStream();
            is.close();
            _out.close();
        } catch (Exception ex) {
            throw new DataStoreException(ex.toString());
        }
    }

    /**
     * insertRow method comment.
     */
    public boolean commit(DataStoreProxy ds) throws Exception {
        Character c = new Character('Z');
        _out.writeObject(c);

        _in = getInputStream(_conn);
        _out.flush();

        int res = getResponse(_conn);
        if (res == DataStoreProxy.REMOTE_STATUS_OK)
            return true;
        else if (res == DataStoreProxy.REMOTE_STATUS_SQL_ERROR || res == DataStoreProxy.REMOTE_STATUS_NOT_FOUND || res == DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED) {
            Object o = _in.readObject();
            Exception e = (Exception) o;
            throw(e);
        } else {
            return false;
        }

    }

    /**
     * deleteRow method comment.
     */
    public boolean deleteRow(DataStoreProxy ds, DataStoreRow row) throws Exception {
        Character c = new Character('D');
        _out.writeObject(c);
        _out.writeObject(new Integer(row.getDSDataRow().getProxyRow()));
        int count = ds.getColumnCount();

        //write out the old values for the where clause
        for (int i = 0; i < count; i++) {
            if (ds.isPrimaryKey(i) || (ds.getConcurrencyCheckColumn(i) && ds.getCheckConcurrency()))
                _out.writeObject(row.getOriginalData(i));
        }

        _out.writeObject(new Character('X'));

        return true;
    }

    /**
     * preRetrieve method comment.
     */
    public boolean destroy(DataStoreProxy ds) throws Exception {
        try {
            _count = 0;
            URLConnection conn = null;
            conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_DESTROY, null, _userID, _pw);
            int result = getResponse(conn);
            if (result == DataStoreProxy.REMOTE_STATUS_OK) {
                _out.close();
                return true;
            } else {
                handleError(result, null);
                return false;
            }
        } catch (Exception ex) {
            throw new DataStoreException(ex.toString());
        }
    }

    /**
     * This method was created in VisualAge.
     * @return int
     */
    public int getCount() {
        return _count;
    }

    private int getResponse(URLConnection conn) {
        return conn.getHeaderFieldInt("DataServerResponse", 0);
    }

    private void handleError(int ret, Throwable t) throws DataStoreException, java.sql.SQLException {
        if (ret == DataStoreProxy.REMOTE_STATUS_NOT_FOUND) {
            throw new DataStoreException("Remote DataStore not found", t);
        } else if (ret == DataStoreProxy.REMOTE_STATUS_BAD_REQUEST) {
            throw new DataStoreException("Bad request to data server", t);
        } else if (ret == DataStoreProxy.REMOTE_STATUS_SQL_ERROR) {
            if (t != null && t instanceof java.sql.SQLException)
                throw((java.sql.SQLException) t);
            else
                throw new DataStoreException("SQL Error running statement.", t);
        } else if (ret == DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED) {
            throw new DataStoreException("Access Denied", t);
        }

    }

    private URLConnection openConnection(DataStoreProxy ds, String url, String sessionId, String operation, String selectionCriteria, String userID, String pw) throws Exception {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        String session = sessionId;
        String remoteID = ds.getRemoteID();
        if (session == null && remoteID != null) {
            int pos = remoteID.lastIndexOf("-");
            if (pos > -1)
                session = remoteID.substring(pos + 1);
        }

        conn.setRequestProperty("Cookie", "sesessionid=" + session + ";session=" + session + ";sessionid=" + session + ";JSESSIONID=" + session + ";jsessionid=" + session);
        if (_proxyUserID != null) {
            String authString = _proxyUserID + ":" + _proxyPw;
            String auth = "Basic " + new Base64Encoder().encode(authString);
            conn.setRequestProperty("Proxy-Authorization", auth);
        }

		if (ds.getUseCompression()) 
			conn.setRequestProperty(DataStoreProxy.USE_COMPRESSION,"true");

        if (ds.getKeepDataOnServer())
            conn.setRequestProperty(DataStoreProxy.KEEP_DATA_ON_SERVER,"true");


		_out = new ObjectOutputStream(conn.getOutputStream());
        _out.writeObject(operation);
        _out.writeObject(remoteID);
        _out.writeObject(selectionCriteria);
        _out.writeObject(userID);
        _out.writeObject(pw);
        return conn;
    }

	/**
	 * ping method comment.
	 */
	public boolean ping(DataStoreProxy ds) throws Exception {
		try {
			_count = 0;
			URLConnection conn = null;
			conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_PING, null, _userID, _pw);
			int result = getResponse(conn);
			if (result == DataStoreProxy.REMOTE_STATUS_OK) {
				_out.close();
				return true;
			} else {
				handleError(result, null);
				return false;
			}
		} catch (Exception ex) {
			throw new DataStoreException(ex.toString());
		}
	}

	/**
	 * ping method comment.
	 */
	public ColumnDefinition[] getColumnsForTable(DataStoreProxy ds, String table) throws Exception {
		try {
			_count = 0;
			URLConnection conn = null;
			conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_GET_TABLE_COLUMNS, table, _userID, _pw);
			int result = getResponse(conn);
			if (result == DataStoreProxy.REMOTE_STATUS_OK) {
				_in = getInputStream(conn);
				ColumnDefinition[] ret = (ColumnDefinition[]) _in.readObject();
				_out.close();
				_in.close();
				return ret;
				
			} else {
				handleError(result, null);
				return null;
			}
		} catch (Exception ex) {
			throw new DataStoreException(ex.toString());
		}
	}

    /**
     * postRetrieve method comment.
     */
    public void postRetrieve(DataStoreProxy ds) throws Exception {
        if (_out != null) {
            _out.close();
            _out = null;
        }
        if (_in != null) {
            _in.close();
            _in = null;
        }
    }

    /**
     * postUpdate method comment.
     */
    public String postUpdate(DataStoreProxy ds[], boolean updateSucceeded) throws Exception {
        String ret = null;
        if (_out != null) {
            _out.close();
            _out = null;
        }
        if (_in != null && updateSucceeded) {
            Object o = _in.readObject();
            int ndx = -1;
            boolean eof = false;
            while (!eof) {
                if (o instanceof Character) {
                    char c = ((Character) o).charValue();
                    if (c == 'S') {
                        ndx++;
                        populateOneDsAfterUpdate(_in, ds[ndx]);
                        o = _in.readObject();
                    } else if (c == 'X')
                        eof = true;
                }
            }
            _in.close();
            _in = null;
        }
        return ret;

    }

    private void populateOneDsAfterUpdate(ObjectInputStream in, DataStoreProxy ds) throws Exception {
        int currentRow = ds.getRow();
        ds.setRemoteUpdateReturnValue((String) in.readObject());
        Object o = in.readObject();
        boolean end = false;
        while (!end) {
            char c = ' ';
            if (o instanceof Character) {
                c = ((Character) o).charValue();
                if (c == 'E')
                    end = true;
                else if (c == 'R') {
                    int rowNo = ((Integer) in.readObject()).intValue();
                    if (rowNo == -1)
                        rowNo = ds.insertRow();
                    DataStoreRow row = ds.getDataStoreRow(rowNo, DataStoreBuffer.BUFFER_STANDARD);
                    o = in.readObject();
                    while (!(o instanceof Character)) {
                        int col = ((Integer) o).intValue();
                        o = in.readObject();
                        row.setData(col, o);
                        o = in.readObject();
                    }
                    row.resetStatus();
                } else if (c == 'T')
                    o = in.readObject();
            }
        }
        if (currentRow > -1)
            ds.gotoRow(currentRow);
    }

    /**
     * preRetrieve method comment.
     */
    public boolean preRetrieve(DataStoreProxy ds, String selectionCriteria, boolean countOnly) throws Exception {

        _count = 0;
        URLConnection conn = null;
        String op = DataStoreProxy.OPERATION_RETRIEVE;

        if (countOnly)
            op = DataStoreProxy.OPERATION_COUNT;

        conn = openConnection(ds, _url, _sessionId, op, selectionCriteria, _userID, _pw);
        int result = getResponse(conn);
        if (result == DataStoreProxy.REMOTE_STATUS_OK) {
            _in = getInputStream(conn);
            if (countOnly) {
                Integer i = (Integer) _in.readObject();
                _count = i.intValue();
            }
            return true;
        } else {
            _in = getInputStream(conn);
            Throwable t = (Throwable) _in.readObject();
            _in.close();
            handleError(result, t);
            return false;
        }


    }

    /**
     * preUpdate method comment.
     */
    public boolean preUpdate(DataStoreProxy ds) throws Exception {
        URLConnection conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_UPDATE, null, _userID, _pw);
        _conn = conn;
        return true;
    }

    /**
     * retrieveRow method comment.
     */
    public boolean retrieveRow(DataStoreProxy ds, DataStoreRow row) throws Exception {

        try {
            for (int i = 0; i < ds.getColumnCount(); i++) {
                row.setData(i, _in.readObject());
            }
            row.resetStatus();
            _count++;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

     /**
     * updateRow method comment.
     */
    public boolean insertRow(DataStoreProxy ds, DataStoreRow row) throws Exception {
        Character c = new Character('I');
        _out.writeObject(c);
        _out.writeObject(new Integer(row.getDSDataRow().getProxyRow()));
        int count = ds.getColumnCount();

        for (int i = 0; i < count; i++) {
             _out.writeObject(new Integer(row.getColumnStatus(i)));
             _out.writeObject(row.getData(i));
             _out.writeObject(row.getTempValue(i));
        }

        _out.writeObject(new Character('X'));

        return true;
    }
    /**
     * updateRow method comment.
     */
    public boolean updateRow(DataStoreProxy ds, DataStoreRow row) throws Exception {
        Character c = new Character('U');
        _out.writeObject(c);
        _out.writeObject(new Integer(row.getDSDataRow().getProxyRow()));
        int count = ds.getColumnCount();

        //write out the old values for the where clause
        for (int i = 0; i < count; i++) {
            if (ds.isPrimaryKey(i) || (ds.getConcurrencyCheckColumn(i) && ds.getCheckConcurrency()))  {
                Object orig = row.getOriginalData(i);
                _out.writeObject(orig);
            }
        }

        //write out the new values
        for (int i = 0; i < count; i++) {
             _out.writeObject(new Integer(row.getColumnStatus(i)));
             _out.writeObject(row.getData(i));
             _out.writeObject(row.getTempValue(i));
        }

        _out.writeObject(new Character('X'));

        return true;
    }

    public void writeDSHeader(DataStoreProxy ds) throws Exception {
        _out.writeObject(new Character('N'));
        _out.writeObject(ds.getRemoteID());
    }

    public DataStoreException[] validateRemoteRules(DataStoreProxy ds, DataStoreRow row, int rowNo,int column) {
        Vector exceptions = new Vector();
        try {
            _count = 0;
            URLConnection conn = null;
            conn = openConnection(ds, _url, _sessionId, DataStoreProxy.OPERATION_VALIDATE, null, _userID, _pw);
            _out.writeObject(new Integer(column));
            _out.writeObject(new Integer(row.getDSDataRow().getProxyRow()));
            for (int i = 0; i < ds.getColumnCount(); i++)
                _out.writeObject(row.getData(i));
            int result = getResponse(conn);
            if (result == DataStoreProxy.REMOTE_STATUS_OK) {
                _in = getInputStream(conn);
                Object o = _in.readObject();
                while (o instanceof DataStoreException) {
                    exceptions.add(o);
                    o = _in.readObject();
                }
                while (!(o instanceof Character)) {
                    int col = ((Integer) o).intValue();
                    Object data =  _in.readObject();
                    ds.setAny(rowNo,col,data);
                    o = _in.readObject();
                }
                _in.close();

                _out.close();
            } else {
                handleError(result, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        DataStoreException retVal[] = new DataStoreException[exceptions.size()];
        exceptions.copyInto(retVal);
        return retVal;
    }

	private ObjectInputStream getInputStream(URLConnection conn) throws IOException {
		if (! _ds.getUseCompression())
			return new ObjectInputStream(conn.getInputStream());
		else
			return new ObjectInputStream(new InflaterInputStream(conn.getInputStream()));	
		
	}	

}
