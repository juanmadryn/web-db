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
package com.salmonllc.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.DeflaterOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.ColumnDefinition;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DSDataRow;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreProxy;
import com.salmonllc.sql.DataStoreRow;
import com.salmonllc.sql.ProxyBeanDataStoreAdaptor;
import com.salmonllc.sql.ValidationRule;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;

/**
 * This servlet works in conjunction with the ProxyDatastore and RemoteDataStore to provide database access to applets.
 */
public class DataServer extends HttpServlet {

	private static final int OP_COMMIT = 0;
	private static final int OP_ROLLBACK = 1;
	private static final int OP_FREE = 2;

	public DataServer() {
		super();
	}

	private boolean checkRequest(DataStoreBuffer rem, String op, HttpServletRequest req, boolean sessValid, String userID, String pw, String criteria) throws DataStoreException {
		if (!(rem instanceof com.salmonllc.sql.Remotable))
			return false;

		boolean retVal = ((com.salmonllc.sql.Remotable) rem).request(op, req, sessValid, userID, pw, criteria);
		if (!retVal) {
			Props p = Props.getSystemProps();
			if (!p.getBooleanProperty(Props.SYS_DATASERVER_SECURITY))
				retVal = true;
		}
		return retVal;
	}

	private void doUpdate(HttpServletRequest req, HttpServletResponse res, boolean sessValid, String userID, String pw, String criteria, HttpSession sess, ObjectInputStream in, ObjectOutputStream out,boolean keepDataOnServer) {
		Vector v = new Vector();
		DataStore ds = null;
		try {
			boolean eof = false;
			while (!eof) {
				Object o = in.readObject();
				if (o instanceof Character) {
					char c = ((Character) o).charValue();
					if (c == 'N') {
						o = in.readObject();
						ds = getDataStore(sess, o.toString());
						if (ds == null) {
							setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
							out.writeObject(new DataStoreException("DataStore Not Found:" + o));
							return;
						} else if (!checkRequest(ds, DataStoreProxy.OPERATION_UPDATE, req, sessValid, userID, pw, criteria)) {
							setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
							out.writeObject(new DataStoreException("Access Denied:" + o));
							return;
						}
						ds.reset();
						v.addElement(ds);
					} else if (c == 'I') {
						//insert
						int localRow = ds.insertRow();
						Integer proxyRow = (Integer) in.readObject();
						DataStoreRow row = ds.getDataStoreRow(localRow, DataStoreBuffer.BUFFER_STANDARD);
						row.getDSDataRow().setProxyRow(proxyRow.intValue());
						for (int i = 0; i < ds.getColumnCount(); i++) {
							Integer status = (Integer) in.readObject();
							ds.setAny(i, in.readObject());
							ds.setTempValue(i, (String) in.readObject());
							row.getDSDataRow().setColumnStatus(i, status.intValue());
						}
					} else if (c == 'U') {
						//update
						ds.insertRow();
						Integer proxyRow = (Integer) in.readObject();
						int rNo = ds.getRowCount() - 1;
						for (int i = 0; i < ds.getColumnCount(); i++) {
							if (ds.isPrimaryKey(i) || (ds.getConcurrencyCheckColumn(i) && ds.getCheckConcurrency()))
								ds.setAny(i, in.readObject());
						}
						DataStoreRow row = ds.getDataStoreRow(rNo, DataStoreBuffer.BUFFER_STANDARD);
						DSDataRow dsRow = row.getDSDataRow();
						row.getDSDataRow().setProxyRow(proxyRow.intValue());
						row.resetStatus();

						for (int i = 0; i < ds.getColumnCount(); i++) {
							Integer status = (Integer) in.readObject();
							Object data = in.readObject();
							ds.setAny(i, data);
							ds.setTempValue(i, (String) in.readObject());
							dsRow.setColumnStatus(i, status.intValue());
						}
					} else if (c == 'D') {
						//delete
						ds.insertRow();
						Integer proxyRow = (Integer) in.readObject();
						int rNo = ds.getRowCount() - 1;
						for (int i = 0; i < ds.getColumnCount(); i++) {
							if (ds.isPrimaryKey(i) || (ds.getConcurrencyCheckColumn(i) && ds.getCheckConcurrency()))
								ds.setAny(i, in.readObject());
						}
						DataStoreRow row = ds.getDataStoreRow(rNo, DataStoreBuffer.BUFFER_STANDARD);
						row.getDSDataRow().setProxyRow(proxyRow.intValue());
						row.resetStatus();
						ds.deleteRow();
					} else if (c == 'Z') {
						//commit changes
						Hashtable connections = new Hashtable();
						int i = 0;
						try {
							DBConnection conn = null;
							for (i = 0; i < v.size(); i++) {
								ds = (DataStore) v.elementAt(i);
								if (ds instanceof ProxyBeanDataStoreAdaptor)
									ds.update();
								else {
									String key = ds.getAppName() + ds.getDbProfile();
									if (connections.containsKey(key))
										conn = (DBConnection) connections.get(key);
									else {
										conn = DBConnection.getConnection(ds.getAppName(), ds.getDbProfile());
										connections.put(key, conn);
										conn.beginTransaction();
									}
									ds.update(conn);
								}
							}
							updateConnections(connections, OP_COMMIT);
							setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
							for (i = 0; i < v.size(); i++) {
								ds = (DataStore) v.elementAt(i);
								String retVal = ds.getRemoteUpdateReturnValue();
								if (retVal == null)
									retVal = "OK";
								out.writeObject(new Character('S'));
								out.writeObject(retVal);
								if (ds.gotoFirst()) {
									do {
										if (ds.getRowStatus() == DataStore.STATUS_MODIFIED || ds.getRowStatus() == DataStore.STATUS_NEW_MODIFIED) {
											out.writeObject(new Character('R'));
											DSDataRow row = ds.getDataStoreRow(ds.getRow(), DataStore.BUFFER_STANDARD).getDSDataRow();
											out.writeObject(new Integer(row.getProxyRow()));
											for (int j = 0; j < ds.getColumnCount(); j++) {
												if (row.getColumnStatus(j) == DataStore.STATUS_MODIFIED) {
													out.writeObject(new Integer(j));
													out.writeObject(row.getData(j));
												}
											}
											out.writeObject(new Character('T'));
										}
									} while (ds.gotoNext());
								}
								out.writeObject(new Character('E'));
							}
							out.writeObject(new Character('X'));
						} catch (DataStoreException e) {
							updateConnections(connections, OP_ROLLBACK);
							setStatus(res, DataStoreProxy.REMOTE_STATUS_SQL_ERROR);
							e.setDataStoreNo(i);
							if (e.getRow() > -1) {
								DSDataRow row = ds.getDataStoreRow(e.getRow(), DataStore.BUFFER_STANDARD).getDSDataRow();
								e.setRowNo(row.getProxyRow());
							}
							out.writeObject(e);
							//MessageLog.writeErrorMessage("doUpdate:" + ds.getRemoteID(), e, this);
							return;
						} catch (Exception e) {
							updateConnections(connections, OP_ROLLBACK);
							setStatus(res, DataStoreProxy.REMOTE_STATUS_SQL_ERROR);
							DataStoreException ex = new DataStoreException(e.getMessage(), e, i);
							out.writeObject(ex);
							MessageLog.writeErrorMessage("doUpdate:" + ds.getRemoteID(), e, this);
							return;
						} finally {
							updateConnections(connections, OP_FREE);
							for (i = 0; i < v.size(); i++) {
								ds = (DataStore) v.elementAt(i);
                                if (!keepDataOnServer)
								    ds.reset();
							}

						}
						eof = true;
					}
				}
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("doUpdate:" + ds.getRemoteID(), e, this);
            if (!keepDataOnServer)
    			ds.reset();
			setStatus(res, DataStoreProxy.REMOTE_STATUS_SQL_ERROR);
		}

	}

	private DataStore getDataStore(HttpSession sess, String id) throws Exception {
		if (id == null)
			return null;

		return (DataStore) sess.getAttribute("$datastore$" + id);

	}

	/**
	 * Initializes the servlet
	 */
	public void init(ServletConfig s) throws ServletException {
		Properties p = System.getProperties();
		if (p.getProperty("serveds.root") == null) {
			String serverRoot = s.getInitParameter("serveds.root");
			if (serverRoot != null)
				p.put("serveds.root", serverRoot);
		}
		super.init(s);
	}

	private DataStoreBuffer newDataStore(HttpServletRequest req, HttpSession sess) throws Exception {
		String app = URLGenerator.generateServletBaseURL(req);
		int pos = app.indexOf("/");
		if (pos > -1)
			app = app.substring(0, pos);

		String ds = null;

		String path = req.getPathInfo();

		try {
			StringTokenizer t = new StringTokenizer(path, "/");
			ds = t.nextToken();
		} catch (Exception e) {
			return null;
		}

		DataStoreBuffer ret = null;
		String sesName = null;

		String dsID = ds + System.currentTimeMillis() + "-" + sess.getId();
		sesName = "$datastore$" + dsID;

		try {
			ret = DataStore.constructDataStore(ds, app);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Exception getting datastore:" + ds, e, this);
			throw (e);
		}

		if (ret != null) {
			sess.setAttribute(sesName, ret);
			ret.setRemoteID(dsID);
		}

		return ret;

	}

	private DataStore removeDataStore(HttpServletRequest req, String idNo) throws Exception {
		HttpSession sess = req.getSession(false);

		if (idNo == null)
			return null;

		DataStore ret = (DataStore) sess.getAttribute("$datastore$" + idNo);
		if (ret != null)
			sess.removeAttribute("$datastore$" + idNo);

		return ret;

	}

	/**
	 * This method handles events from the applet.
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
		OutputStream out = null;
		ObjectInputStream in = null;
        boolean keepDataOnServer = (req.getHeader(DataStoreProxy.KEEP_DATA_ON_SERVER) != null);

		try {
			boolean useCompression = (req.getHeader(DataStoreProxy.USE_COMPRESSION) != null);
			in = new ObjectInputStream(req.getInputStream());
			if (!useCompression)
				out = res.getOutputStream();
			else
				out = new DeflaterOutputStream(res.getOutputStream());

			String operation = (String) in.readObject();
			String idNo = (String) in.readObject();
			String criteria = (String) in.readObject();
			String userID = (String) in.readObject();
			String pw = (String) in.readObject();

			if (operation == null) {
				setStatus(res, DataStoreProxy.REMOTE_STATUS_BAD_REQUEST);
				operation = "Create";
				in.close();
				out.close();
				return;
			}

			MessageLog.writeInfoMessage("Operation:" + operation + " ID:" + idNo, this);

			//System.erds.println("Cookies***********************");
			//Cookie c[] = req.getCookies();
			//for (int i = 0; i < c.length; i++) {
			//	System.erds.println(c[i].getName() + " = " + c[i].getValue());
			//}
			//System.erds.println("********************************");

			boolean sessValid = true;
			HttpSession sess = req.getSession(false);
			if (sess == null) {
				sess = req.getSession(true);
				sessValid = false;
			}
			//System.erds.println("Session Valid=" + sessValid);

			if (operation.equals(DataStoreProxy.OPERATION_CREATE)) {
				DataStoreBuffer ds = newDataStore(req, sess);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
					Properties p = ds.getProperties();
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					p.store(bout, "");
					bout.close();
					ObjectOutputStream o = new ObjectOutputStream(out);
					o.writeObject(bout.toByteArray());
					for (int i = 0; i < ds.getColumnCount(); i++) {
						ValidationRule r[] = ds.getValidationRulesForColumn(i);
						if (r != null) {
							for (int j = 0; j < r.length; j++) {
								if (r[j].isExecuteOnServer())
									r[j] = new ValidationRule();
							}
							o.writeObject(new Integer(i));
							o.writeObject(r);
						}
					}
					o.writeObject(new Integer(-1));
					o.close();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_RETRIEVE)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					boolean enableThreads = ds.getEnableThreads();
					ds.setEnableThreads(true);

					if (criteria != null && !criteria.equals(""))
						ds.retrieve(criteria);
					else
						ds.retrieve();
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
					ObjectOutputStream o = new ObjectOutputStream(out);
					if (ds.gotoFirst()) {
						do {
							for (int i = 0; i < ds.getColumnCount(); i++) {
								o.writeObject(ds.getAny(i));
							}
						} while (ds.gotoNext());
					}
					ds.setEnableThreads(enableThreads);
					MessageLog.writeInfoMessage("Operation:" + operation + " ID:" + idNo + " complete. Sent " + ds.getRowCount() + " rows to client.", this);
					o.close();
                    if (!keepDataOnServer)
    					ds.reset();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_VALIDATE)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					ds.reset();
					int colNo = ((Integer) in.readObject()).intValue();
					int rowNo = ((Integer) in.readObject()).intValue();
					ds.insertRow();
					for (int i = 0; i < ds.getColumnCount(); i++) {
						DataStoreRow r = ds.getDataStoreRow(0, DataStoreBuffer.BUFFER_STANDARD);
						r.setData(i, in.readObject());
						r.getDSDataRow().setColumnStatus(i, DataStoreBuffer.STATUS_NOT_MODIFIED);
					}
					ds.resetStatus();
					DBConnection conn = null;
					ObjectOutputStream o = new ObjectOutputStream(out);
					try {
						conn = DBConnection.getConnection(ds.getAppName(), ds.getDbProfile());
						DataStoreException[] ex = ds.validateColumn(0, colNo, conn);
						for (int i = 0; i < ex.length; i++) {
							ex[i].setRowNo(rowNo);
							o.writeObject(ex[i]);
						}
						for (int i = 0; i < ds.getColumnCount(); i++) {
							if (ds.isColumnModified(0, i)) {
								o.writeObject(new Integer(i));
								o.writeObject(ds.getAny(0, i));
							}
						}
					} catch (Exception ex) {
						MessageLog.writeErrorMessage("doGet", ex, this);
					} finally {
						if (conn != null)
							conn.freeConnection();
                        if (!keepDataOnServer)
    						ds.reset();
					}
					o.writeObject(new Character('X'));
					o.close();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_COUNT)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					int rows = 0;
					if (criteria != null && !criteria.equals(""))
						rows = ds.estimateRowsRetrieved(criteria);
					else
						rows = ds.estimateRowsRetrieved();
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
					ObjectOutputStream o = new ObjectOutputStream(out);
					o.writeObject(new Integer(rows));
					o.close();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_GET_TABLE_COLUMNS)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					ColumnDefinition def[] = ds.getColumnsForTable(criteria);
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
					ObjectOutputStream o = new ObjectOutputStream(out);
					o.writeObject(def);
					o.close();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_CANCEL)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else {
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
					ds.cancelRetrieve();
				}
			} else if (operation.equals(DataStoreProxy.OPERATION_PING)) {
				DataStore ds = getDataStore(sess, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
			} else if (operation.equals(DataStoreProxy.OPERATION_DESTROY)) {
				DataStore ds = removeDataStore(req, idNo);
				if (ds == null)
					setStatus(res, DataStoreProxy.REMOTE_STATUS_NOT_FOUND);
				else if (!checkRequest(ds, operation, req, sessValid, userID, pw, criteria))
					setStatus(res, DataStoreProxy.REMOTE_STATUS_ACCESS_DENIED);
				else
					setStatus(res, DataStoreProxy.REMOTE_STATUS_OK);
			} else if (operation.equals(DataStoreProxy.OPERATION_UPDATE)) {
				ObjectOutputStream o = new ObjectOutputStream(out);
				doUpdate(req, res, sessValid, userID, pw, criteria, sess, in, o,keepDataOnServer);
				out.close();
			} else {
				setStatus(res, DataStoreProxy.REMOTE_STATUS_BAD_REQUEST);
			}

			in.close();
		} catch (DataStoreException e) {
			setStatus(res, DataStoreProxy.REMOTE_STATUS_BAD_REQUEST);
			ObjectOutputStream o = new ObjectOutputStream(out);
			o.writeObject(e);
			MessageLog.writeErrorMessage("doGet", e, this);
			out.close();
		} catch (java.sql.SQLException e) {
			setStatus(res, DataStoreProxy.REMOTE_STATUS_SQL_ERROR);
			ObjectOutputStream o = new ObjectOutputStream(out);
			o.writeObject(e);
			MessageLog.writeErrorMessage("doGet", e, this);
			out.close();
		} catch (Exception e) {
			MessageLog.writeErrorMessage("doGet", e, this);
			setStatus(res, DataStoreProxy.REMOTE_STATUS_BAD_REQUEST);
		}

	}

	/**
	 * This method was created in VisualAge.
	 * @param res javax.servlet.http.HttpServletResponse
	 */
	private void setStatus(HttpServletResponse res, int stat) {
		res.setIntHeader("DataServerResponse", stat);
	}

	private void updateConnections(Hashtable connections, int op) {
		Enumeration e = connections.elements();
		while (e.hasMoreElements()) {
			DBConnection conn = (DBConnection) e.nextElement();
			if (op == OP_COMMIT)
				conn.commit();
			else if (op == OP_ROLLBACK)
				conn.rollback();
			else if (op == OP_FREE)
				conn.freeConnection();
		}
	}

}
