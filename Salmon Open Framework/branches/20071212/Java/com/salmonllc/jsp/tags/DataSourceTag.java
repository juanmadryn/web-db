package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/DataSourceTag.java $
//$Author: Srufle $
//$Revision: 34 $
//$Modtime: 10/25/04 12:44p $
/////////////////////////

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import com.salmonllc.fatwire.UEDataStore;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.xml.XMLTransporter;

/**
 * This tags is used to create a DataStoreBuffer object
 */
public class DataSourceTag extends BaseBodyTag {
	String _type;
	String _contentclass;
	String _defaulttable;
	String _autoretrieve;
	String _autobind;
	String _distinct;
	String _maxrows;
	String _updatemethod;
	String _application;
	String _dbprofile;
	String _xmlDef;
	String _xmlFile;
	String _model;
	String _beanDataStore;
	String _beanExtraInfo;
	String _sessionID;
	int _dsType;

	Vector _fieldTags = new Vector(0);
	public final static int TYPE_SQL = 0;
	public final static int TYPE_CONTENT_CLASS = 1;
	public final static int TYPE_XML = 2;
	public final static int TYPE_MODEL = 3;
	public final static int TYPE_CONTENT_CLASS_MODEL = 4;
	public final static int TYPE_QBE = 5;

	void addField(DataSourceFieldTag t) {
		_fieldTags.addElement(t);
	}
	/**
	 * This method will add a component to the controller. It is overloaded in
	 * the DataSourceTag to add a DataStoreBuffer instead
	 */
	protected void addItem() {
		getHelper().addDataSource(createDataSource());
	}
	/**
	 * This method can be used by tags that need to change a datastore after all
	 * the sub tags have been initialized.
	 */
	public void afterInit(DataStoreBuffer comp) {

		if (comp instanceof DataStore) {

			if (_dsType == TYPE_SQL && _fieldTags.size() > 0) {
				DBConnection conn = null;
				try {
					conn = DBConnection.getConnection(_application,_dbprofile);
					DataDictionary d = conn.getDataDictionary();
					for (int i = 0; i < _fieldTags.size(); i++) {
						DataSourceFieldTag t = (DataSourceFieldTag) _fieldTags.elementAt(i);
						t.addField(comp, d);
					}
				} catch (Exception ex) {
					MessageLog.writeErrorMessage("afterInit", ex, this);
				} finally {
					if (conn != null)
						conn.freeConnection();
				}
			} else if (_xmlDef != null)
				buildXMLDataStore((DataStore) comp, _xmlDef, false);
			else if (_xmlFile != null)
				buildXMLDataStore((DataStore) comp, _xmlFile, true);
		} else if (!(comp instanceof BeanDataStore || comp instanceof QBEBuilder)) {
			try {
				if (_dsType == TYPE_CONTENT_CLASS_MODEL)
					return;
				if (_fieldTags.size() == 0)
				{
					((UEDataStore) comp).setDbProfile(_dbprofile);
					((UEDataStore) comp).buildBuffer(getHelper().getController(), _contentclass, null);
				}
				else {
					String fields[] = new String[_fieldTags.size()];
					for (int i = 0; i < _fieldTags.size(); i++) {
						DataSourceFieldTag t = (DataSourceFieldTag) _fieldTags.elementAt(i);
						String name = t.getFieldname();
						if (name == null)
							name = t.getName();
						fields[i] = name;
					}
					((UEDataStore) comp).buildBuffer(getHelper().getController(), _contentclass, fields);
					for (int i = 0; i < _fieldTags.size(); i++) {
						DataSourceFieldTag t = (DataSourceFieldTag) _fieldTags.elementAt(i);
						if (t.getFormat() != null)
							((UEDataStore) comp).setFormat(fields[i], t.getFormat());
					}
				}
			} catch (Exception e) {
				MessageLog.writeErrorMessage(e, this);

			}

		}

	}
	private void buildXMLDataStore(DataStore ds, String xmlDef, boolean useFile) {
		try {
			String dataPath = getHelper().getController().getPageProperties().getProperty(com.salmonllc.properties.Props.XML_DATA_PATH);
			if (dataPath != null) {
				if (!dataPath.endsWith(File.separator))
					dataPath += File.separator;
			}

			if (!useFile) {
				File f = null;
				synchronized (this) {
					int i = 1;
					f = new File(dataPath + "_tmpJsp1.xml");
					while (f.exists()) {
						f = new File(dataPath + "_tmpJsp" + i + ".xml");
						i++;
					}
					FileOutputStream fout = new FileOutputStream(f);
					PrintWriter w = new PrintWriter(fout);
					w.print(xmlDef);
					w.close();
				}
				XMLTransporter.xmlImport(f.getCanonicalPath(), ds);
				f.delete();
				ds.gotoFirst();
			} else {
				File f = new File(dataPath + xmlDef);
				XMLTransporter.xmlImport(f.getCanonicalPath(), ds);
				ds.gotoFirst();
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage(e, this);
		}
	}
	/**
	 * This method must be implemented by each subclass of this tag. Each tag
	 * except NestedTags has an Html or Jsp Component associated with it. This
	 * method must be implemented to insure that the correct component gets
	 * created.
	 */
	public com.salmonllc.html.HtmlComponent createComponent() {
		_fieldTags.setSize(0);
		_xmlDef = null;
		return null;
	}
	/**
	 * This method creates the datasource used by the tag.
	 */
	public com.salmonllc.sql.DataStoreBuffer createDataSource() {
		String type = _type.toUpperCase();
		if (type.equals("QBE")) {
			if (_model != null)
				type = "MODEL";
			else {
				_dsType = TYPE_QBE;
				QBEBuilder ds = new QBEBuilder();
				ds.setAutoBind(BaseTagHelper.stringToBoolean(_autobind, true));
				ds.setLang(getHelper().getController().getLanguagePreferences());
				return ds;
			}
		}

		if (type.equals("CONTENTCLASS")) {
			//create a ue datastore
			_dsType = TYPE_CONTENT_CLASS;
			UEDataStore ds = new com.salmonllc.fatwire.UEDataStore();
			setAutoRetrieve(ds);
			ds.setMaxRows(BaseTagHelper.stringToInt(_maxrows));
			ds.setAutoBind(BaseTagHelper.stringToBoolean(_autobind, true));
			ds.setDbProfile(_dbprofile);
			return ds;
		} else if (type.equals("MODEL")) {
			//create a datastore dynamically
			_dsType = TYPE_MODEL;
			if (_application == null)
				_application = getHelper().getController().getApplicationName();

			DataStoreBuffer ds = null;

			if (_sessionID != null) {
				HttpSession sess = pageContext.getSession();
				Object o = sess.getAttribute(_sessionID);
				if (o != null && o instanceof DataStoreBuffer) {
					ds = (DataStoreBuffer) o;
					return ds;
				}
			}

			if (_beanDataStore != null || _beanExtraInfo != null)
				ds = BeanDataStore.constructBeanDataStore(_beanDataStore, _model, _beanExtraInfo);
			else
				ds = DataStore.constructDataStore(_model, _application, _dbprofile);

			setAutoRetrieve(ds);
			ds.setLang(getHelper().getController().getLanguagePreferences());
			ds.setAutoBind(BaseTagHelper.stringToBoolean(_autobind, true));

			if (_sessionID != null) {
				HttpSession sess = pageContext.getSession();
				sess.setAttribute(_sessionID, ds);
			}
			return ds;
		} else if (type.equals("CCMODEL")) {
			_dsType = TYPE_CONTENT_CLASS_MODEL;
			try {
				JspController cont = getHelper().getController();
				Class[] parmTypes = {JspController.class};
				Object[] parms = {cont};
				Class cl = Class.forName(_model, true, Thread.currentThread().getContextClassLoader());
				Constructor cons = cl.getConstructor(parmTypes);
				UEDataStore ds = (UEDataStore) cons.newInstance(parms);
				setAutoRetrieve(ds);
				ds.setMaxRows(BaseTagHelper.stringToInt(_maxrows));
				ds.setAutoBind(BaseTagHelper.stringToBoolean(_autobind, true));
				return ds;
			} catch (Exception e) {
				MessageLog.writeErrorMessage("createDataSource() - CCMODEL" + _model, e, this);
				return null;
			}

		} else {
			//create a regular datastore
			if (type.equals("XML"))
				_dsType = TYPE_XML;
			else
				_dsType = TYPE_SQL;

			DataStore ds = null;
			if (_application == null)
				_application = getHelper().getController().getApplicationName();

			if (_dbprofile != null)
				ds = new DataStore(_application, _dbprofile);
			else
				ds = new DataStore(_application);
			setAutoRetrieve(ds);
			ds.setAutoBind(BaseTagHelper.stringToBoolean(_autobind, true));
			ds.setDistinct(BaseTagHelper.stringToBoolean(_distinct, false));
			ds.setMaxRows(BaseTagHelper.stringToInt(_maxrows));
			if (_updatemethod != null) {
				if (_updatemethod.equalsIgnoreCase("UPDATE"))
					ds.setUpdateMethod(DataStore.UPDATEMETHOD_UPDATES);
				else
					ds.setUpdateMethod(DataStore.UPDATEMETHOD_DELETEINSERTS);
			}
			if (_defaulttable != null)
				ds.setDefaultTable(_defaulttable);
			return ds;
		}

	}

	/**
	 * This method is part of the JSP Tag Lib Spec
	 */

	public int doAfterBody() throws JspException {
		if (getHelper().isInitializing())
			return super.doAfterBody();
		else
			return SKIP_BODY;

	}
	/**
	 * This method is part of the JSP Tag Lib Spec
	 */

	public int doEndTag() throws JspException {
		if (getHelper().isInitializing())
			return super.doEndTag();
		else
			return EVAL_PAGE;
	}
	/**
	 * This method is part of the JSP Tag Lib Spec
	 */
	public int doStartTag() throws JspException {
		_fieldTags.setSize(0);
		if (getHelper().isInitializing())
			return super.doStartTag();
		else
			return SKIP_BODY;
	}
	protected void generateComponentHTML(JspWriter b) {
		((BodyContent) b).clearBody();
	}
	/**
	 * Returns the type of DataSource (SQL, CONTENTCLASS,XML)
	 */
	int getDSType() {
		return _dsType;
	}
	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 */
	public int getTagConvertType() {
		return CONV_DONT_CONVERT;
	}
	/**
	 * This method is part of the JSP tag lib specification.
	 */

	public void release() {
		super.release();
		_type = null;
		_dsType = -1;
		_contentclass = null;
		_defaulttable = null;
		_autobind = null;
		_autoretrieve = null;
		_distinct = null;
		_maxrows = null;
		_updatemethod = null;
		_application = null;
		_dbprofile = null;
		_fieldTags.setSize(0);
		_beanDataStore = null;
		_beanExtraInfo = null;
		_xmlDef = null;
		_sessionID = null;
	}
	/**
	 * Sets the application attribute (for type = SQL)
	 */
	public void setApplication(String value) {
		_application = value;
	}
	/**
	 * Sets the autobind attribute
	 */
	public void setAutobind(String value) {
		_autobind = value;
	}
	private void setAutoRetrieve(DataStoreBuffer ds) {
		if (_autoretrieve == null)
			_autoretrieve = "NEVER";
		else
			_autoretrieve = _autoretrieve.toUpperCase();
		if (_autoretrieve.equals("ALWAYS"))
			ds.setAutoRetrieve(DataStoreBuffer.AUTORETRIEVE_ALWAYS);
		else if (_autoretrieve.equals("NEVER"))
			ds.setAutoRetrieve(DataStoreBuffer.AUTORETRIEVE_NEVER);
		else
			ds.setAutoRetrieve(DataStoreBuffer.AUTORETRIEVE_ONCHANGE);
	}
	/**
	 * Sets the autoretrive attribute (valid values NEVER, ALWAYS, ONCHANGE)
	 */
	public void setAutoretrieve(String value) {
		_autoretrieve = value;
	}
	/**
	 * Sets the name of the content class (for type=CONTENTCLASS)
	 */
	public void setContentclass(String value) {
		_contentclass = value;
	}
	/**
	 * Sets the dbprofile attribute (for type = SQL)
	 */
	public void setDbprofile(String value) {
		_dbprofile = value;
	}
	/**
	 * Sets the default table attribute
	 */
	public void setDefaulttable(String value) {
		_defaulttable = value;
	}
	/**
	 * Sets the distinct attribute (for type=SQL)
	 */
	public void setDistinct(String value) {
		_distinct = value;
	}
	/**
	 * Sets the maxrows attribute
	 */
	public void setMaxrows(String value) {
		_maxrows = value;
	}
	/**
	 * Sets the model (for type=model)
	 */
	public void setModel(String value) {
		_model = value;
	}
	/**
	 * Sets the type of DataSource (SQL, CONTENTCLASS,XML)
	 */
	public void setType(String type) {
		_type = type;
	}
	/**
	 * Sets the update method attribute (for type = SQL, valid values are
	 * UPDATE,DELETEINSERT)
	 */
	public void setUpdatemethod(String value) {
		_updatemethod = value;
	}
	void setXmlDef(String xml) {
		_xmlDef = xml;
	}
	/**
	 * Sets the default table (for type=SQL)
	 */
	public void setXmlfile(String value) {
		_xmlFile = value;
	}

	/**
	 * @param string
	 */
	public void setBeandatastore(String string) {
		_beanDataStore = string;
	}

	/**
	 * @param string
	 */
	public void setBeanextrainfo(String string) {
		_beanExtraInfo = string;
	}

	/**
	 * @return
	 */
	public String getSessionid() {
		return _sessionID;
	}

	/**
	 * @param string
	 */
	public void setSessionid(String string) {
		_sessionID = string;
	}

}