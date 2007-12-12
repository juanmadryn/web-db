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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/DwInfoTranslator.java $
//$Author: Dan $
//$Revision: 13 $
//$Modtime: 10/06/04 3:10p $
/////////////////////////

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.TagInfo;

import com.salmonllc.fatwire.UEDataStore;
import com.salmonllc.fatwire.UEInfo;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspServlet;
import com.salmonllc.jsp.engine.PageContextImpl;
import com.salmonllc.jsp.engine.TagLibraryParser;
import com.salmonllc.localizer.LanguageResourceFinder;
import com.salmonllc.properties.Props;
import com.salmonllc.sitemap.SiteMap;
import com.salmonllc.sql.*;
import com.salmonllc.util.FilterFile;
import com.salmonllc.util.MessageLog;
import com.salmonllc.xml.XMLTransporter;

/**
 * This Servlet is used to provide server information to Dreamweaver.
 */
public class DwInfoTranslator extends HttpServlet {

	public static final String INFO_TYPE = "INFOTYPE";
	public static final String TABLE_NAME = "TABLENAME";
	public static final String CONTENT_CLASS = "CONTENTCLASS";
	public static final int DW_INFO_PROP = 1;
	public static final int DW_INFO_TABLENAME = 2;
	public static final int DW_INFO_TABLEFIELD = 3;
	public static final int DW_INFO_UE_CONTENTCLASS = 4;
	public static final int DW_INFO_UE_CONTENTFIELD = 5;
	public static final int DW_INFO_XMLFILES = 6;
	public static final int DW_INFO_XMLFIELDS = 7;
	public static final int DW_INFO_ATTRIBUTE = 8;
	public static final int DW_INFO_MODELTABLENAME = 9;
	public static final int DW_INFO_MODELTABLEFIELD = 10;
	public static final int DW_INFO_MODELTYPE = 11;
	public static final int DW_INFO_LANGPREF = 12;
	public static final int DW_INFO_SITEMAP = 14;

	private Hashtable _inputType = new Hashtable();


	public void buildInputType() {

		_inputType.clear();
		_inputType.put("button", "com.salmonllc.html.HtmlSubmitButton");
		_inputType.put("checkbox", "com.salmonllc.html.HtmlCheckBox");
		_inputType.put("file", "com.salmonllc.html.HtmlFileUpload");
		_inputType.put("hidden", "com.salmonllc.html.HtmlHiddenField");
		_inputType.put("image", "com.salmonllc.html.HtmlSubmitImage");
		_inputType.put("password", "com.salmonllc.html.HtmlPasswordEdit");
		_inputType.put("radio", "com.salmonllc.html.HtmlRadioButton");
		_inputType.put("radiobutton", "com.salmonllc.html.HtmlRadioButton");
		_inputType.put("radiogroup", "com.salmonllc.html.HtmlRadioButtonGroup");
		_inputType.put("radiogroupbutton", "com.salmonllc.html.HtmlRadioButtonGroup");
		_inputType.put("select", "com.salmonllc.html.HtmlListBox");
		_inputType.put("submit", "com.salmonllc.html.HtmlSubmitButton");
		_inputType.put("text", "com.salmonllc.html.HtmlTextEdit");
		_inputType.put("textarea", "com.salmonllc.html.HtmlMultiLineTextEdit");
		_inputType.put("email", "com.salmonllc.html.HtmlEMailComponent");
		_inputType.put("ssnmulti", "com.salmonllc.html.HtmlSSNComponent");
		_inputType.put("ssn", "com.salmonllc.html.HtmlSSNSingleComponent");
		_inputType.put("state", "com.salmonllc.html.HtmlStateComponent");
		_inputType.put("phone", "com.salmonllc.html.HtmlTelephoneComponent");
		_inputType.put("zip", "com.salmonllc.html.HtmlZipCodeComponent");
		_inputType.put("fraction", "com.salmonllc.html.HtmlFractionComponent");
		_inputType.put("decimal", "com.salmonllc.html.HtmlDecimalComponent");
		_inputType.put("date", "com.salmonllc.html.HtmlDateComponent");


	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
		res.setStatus(HttpServletResponse.SC_OK);
		int infoType = Integer.parseInt(req.getParameter(INFO_TYPE));

		String retStr = "";

		switch (infoType) {
			case DW_INFO_PROP:
				{
					try {
						String app = req.getParameter("Application");
						File f = new File(Props.getPropsPath() + File.separatorChar + app + ".properties");
						FileInputStream in = new FileInputStream(f);
						Properties p = new Properties();
						p.load(in);
						Enumeration enumera = p.propertyNames();
						String property = "";
						while (enumera.hasMoreElements()) {
							property = (String) enumera.nextElement();
							if (property.endsWith(".StartTag")) {
								int idx = property.indexOf(".StartTag");
								retStr += property.substring(0, idx) + ",";
							}
						}

						f = new File(Props.getPropsPath() + File.separatorChar + "System.properties");
						in = new FileInputStream(f);
						Properties sys = new Properties();
						sys.load(in);
						enumera = sys.propertyNames();
						while (enumera.hasMoreElements()) {
							property = (String) enumera.nextElement();
							if (property.endsWith(".StartTag") && p.getProperty(property) == null) {
								int idx = property.indexOf(".StartTag");
								retStr += property.substring(0, idx) + ",";
							}
						}


						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.Properties", e, this);
					}
					break;
				}
			case DW_INFO_TABLENAME:
				{
					try {
						retStr = "";
						String app = req.getParameter("Application");
						DataDictionary dataDict = new DataDictionary(app);
						Vector tableNames = dataDict.getTableNames();
						for (int i = 0; i < tableNames.size(); i++)
							retStr += tableNames.elementAt(i) + ",";


						res.getOutputStream().print(retStr);


					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.Tables", e, this);
					}

					break;

				}

			case DW_INFO_TABLEFIELD:
				{
					try {
						String tableName = req.getParameter(TABLE_NAME);
						retStr = "";
						String app = req.getParameter("Application");
						DataDictionary dataDict = new DataDictionary(app);
						Vector fieldNames = dataDict.getColumns(tableName);
						for (int i = 0; i < fieldNames.size(); i++)
							retStr += ((ColumnDefinition) fieldNames.elementAt(i)).getColumnName() + ",";


						res.getOutputStream().print(retStr);


					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.Tables", e, this);
					}

					break;

				}
			case DW_INFO_UE_CONTENTCLASS:
				{
					try {
						UEInfo _ueInfo = new UEInfo();
						Vector ccNames = _ueInfo.getContentClasses();
						retStr = "";
						for (int i = 0; i < ccNames.size(); i++)
							retStr += ccNames.elementAt(i) + ",";


						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.UE_CONTENT", e, this);
					}
					break;
				}
			case DW_INFO_UE_CONTENTFIELD:
				{
					try {

						String ccName = req.getParameter(CONTENT_CLASS);
/*				UEInfo _ueInfo = new UEInfo();
Vector ffNames = _ueInfo.getFieldNames(ccName);
retStr="";
for (int i=0;i < ffNames.size();i++)
retStr += ffNames.elementAt(i) + ",";
*/
						PageContextImpl p = new PageContextImpl();
						com.salmonllc.html.HttpServletResponseWrapper w = new com.salmonllc.html.HttpServletResponseDummy(res, null);
						p.initialize(this, req, w, null, true, 128, false);
						JspController cont = new JspController();
						cont.setPageContext(p);
						UEDataStore _ueData = new UEDataStore();
						_ueData.buildBuffer(cont, ccName, null);
						for (int i = 0; i < _ueData.getColumnCount(); i++)
							retStr += _ueData.getColumnName(i) + ",";


						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.UE_CONTENTNAMES", e, this);
					}
					break;
				}
			case DW_INFO_XMLFILES:
				{
					try {
						String app = req.getParameter("Application");
						Props p = Props.getProps(app, null);

						String dataPath = p.getProperty(Props.XML_DATA_PATH);
						if (dataPath != null) {
							if (!dataPath.endsWith(File.separator))
								dataPath += File.separator;
						}
						File dir = new File(dataPath);
						if (!dir.isDirectory()) {
							res.setStatus(HttpServletResponse.SC_NOT_FOUND);
							MessageLog.writeDebugMessage("Error in doGet of DWInfoTranslator.XML_FILES.XML Directory Not Found", this);
							break;
						}
						FilterFile filter = new FilterFile(".xml");

						String[] xmlFiles = dir.list();
						for (int i = 0; i < xmlFiles.length; i++) {
							if (filter.accept(dir, xmlFiles[i]))
								retStr += xmlFiles[i] + ",";
						}


						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.XML_FILES", e, this);
					}
					break;
				}

			case DW_INFO_XMLFIELDS:
				{
					try {

						String app = req.getParameter("Application");
						String xmlDef = req.getParameter("XMLDEF");
						boolean useFile = new Boolean(req.getParameter("USEFILE")).booleanValue();
						DataStore _dsXml = new DataStore(app);
						Props p = Props.getProps(app, null);
						String dataPath = p.getProperty(Props.XML_DATA_PATH);
						if (dataPath != null) {
							if (!dataPath.endsWith(File.separator))
								dataPath += File.separator;
						}

						if (useFile) {
							File f = new File(dataPath + xmlDef);
							if (f.exists()) {
								XMLTransporter.xmlImport(f.getCanonicalPath(), _dsXml);
							}
						} else {
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
							XMLTransporter.xmlImport(f.getCanonicalPath(), _dsXml);
							f.delete();
						}


						for (int i = 0; i < _dsXml.getColumnCount(); i++)
							retStr += _dsXml.getColumnDatabaseName(i) + ",";


						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.XML_FIELDS", e, this);
					}
					break;
				}
			case DW_INFO_ATTRIBUTE:
				{
					try {
						String app = req.getParameter("Application");
						String taglib = req.getParameter("TagLib");
						String tagName = req.getParameter("TagName").toLowerCase();
						Hashtable tags = TagLibraryParser.getTags(taglib,app, getServletContext());
						String infoClass = "";
						if (tagName.startsWith("input")) {
							buildInputType();
							infoClass = (String) _inputType.get(tagName.substring(tagName.indexOf(",") + 1).toLowerCase());
						} else {
							TagInfo t = (TagInfo) tags.get(tagName);
							infoClass = t.getInfoString();
						}
						Class classMethod = Class.forName(infoClass);
						java.lang.reflect.Method[] _methods = classMethod.getMethods();
						Vector methodVec = new Vector();
						for (int i = 0; i < _methods.length; i++) {
							if (_methods[i].getName().startsWith("set")) {
								String methodName = _methods[i].getName().substring(3);
								if (!methodVec.contains(methodName))
									methodVec.addElement(methodName);
							}
						}
						for (int i = 0; i < methodVec.size(); i++)
							retStr += methodVec.elementAt(i) + ",";

//			   TagAttributeInfo attribArray[] =t.getAttributes();
//				 for (int i=0;i<attribArray.length;i++)
//				      retStr += ((TagAttributeInfo)attribArray[i]).getName() + ",";

						res.getOutputStream().print(retStr);

					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.ATTRIBUTE", e, this);
					}
					break;
				}
			case DW_INFO_MODELTYPE:
				{
					try {
						String model = req.getParameter("MODEL");
						String app = req.getParameter("Application");
						DataStoreBuffer dsb = DataStore.constructDataStore(model, app);
						if (dsb == null)
							res.getOutputStream().print("NONE");
						else if (dsb instanceof BeanDataStore)
							res.getOutputStream().print("BEAN");
						else
							res.getOutputStream().print("STANDARD");
					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.ModelTables", e, this);
					}

					break;

				}
			case DW_INFO_MODELTABLENAME:
				{
					try {
						StringBuffer rStr = new StringBuffer();
						String app = req.getParameter("Application");
						String model = req.getParameter("MODEL");
						DataStoreBuffer dsb = DataStore.constructDataStore(model, app);
						String[] tableNames = dsb.getTableList(false);
						for (int i = 0; i < tableNames.length; i++) {
							rStr.append(tableNames[i] + ",");
						}


						res.getOutputStream().print(rStr.toString());


					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.ModelTables", e, this);
					}

					break;

				}

			case DW_INFO_MODELTABLEFIELD:
				{
					try {
						StringBuffer rStr = new StringBuffer();
						String app = req.getParameter("Application");
						String table = req.getParameter("TABLE");
						String model = req.getParameter("MODEL");
						DataStoreBuffer dsb = DataStore.constructDataStore(model, app);
						String[] colList = dsb.getColumnList();
						if (table == null || table.equals("")) {
							for (int i = 0; i < colList.length; i++)
								rStr.append(colList[i] + ",");
						} else {
							for (int i = 0; i < colList.length; i++) {
								if (colList[i].startsWith(table))
									rStr.append(colList[i].substring(colList[i].indexOf(".") + 1) + ",");
							}
						}


						res.getOutputStream().print(rStr.toString());


					} catch (Exception e) {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.ModelTableField", e, this);
					}

					break;

				}
			case DW_INFO_LANGPREF:
				{
					try{
						String sLang = req.getParameter("LANGUAGE");
						String sRefresh = req.getParameter("REFRESH");
						if(sRefresh !=null && sRefresh.equalsIgnoreCase("true"))
							 LanguageResourceFinder.clearCache();

						Enumeration enumLangKeys = LanguageResourceFinder.getKeys(req.getParameter("Application"), sLang);
						StringBuffer rStr = new StringBuffer();
						if (enumLangKeys != null) {
							while(enumLangKeys.hasMoreElements()){
								Object oKey = enumLangKeys.nextElement();
								if(oKey != null)
									rStr.append((String)oKey + ",");
							}
						}
						res.getOutputStream().print(rStr.toString());
						//MessageLog.writeErrorMessage(" ++++++++++ enumLangKeys = "+sRetVal, this);
					}catch(Exception ex){
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.DefaultLang", ex, this);
					}
					break;
				}
			case DW_INFO_SITEMAP:
				{
					try{
						String app = req.getParameter("Application");
						SiteMap m = SiteMap.getSiteMap(app);

						Enumeration enumKeys = m.getEntryNames();
						StringBuffer rStr = new StringBuffer();
						if (enumKeys != null) {
							while(enumKeys.hasMoreElements()){
								Object oKey = enumKeys.nextElement();
								if(oKey != null) {
									rStr.append("%");
									rStr.append((String)oKey );
									rStr.append(",");
								}	
							}
						}
						res.getOutputStream().print(rStr.toString());
					}catch(Exception ex){
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						MessageLog.writeErrorMessage("Error in doGet of DWInfoTranslator.SiteMap", ex, this);
					}
					break;
				}

		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
		res.setStatus(HttpServletResponse.SC_OK);
		try {
		} catch (Exception e) {
			MessageLog.writeErrorMessage("Dreamweaver Info Failed to Translate.", e, this);
		}


	}
}
