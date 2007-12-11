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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/IDETool.java $
//$Author: Dan $
//$Revision: 57 $
//$Modtime: 11/10/04 12:31a $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.QBEBuilder;
import com.salmonllc.sql.ValidationRule;
import com.salmonllc.util.Util;
import com.salmonllc.win32.Registry;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.awt.*;
import java.util.*;

public class IDETool {
	public static final String SERVER_TOMCAT40 = "Tomcat4.0";
	public static final String SERVER_TOMCAT41 = "Tomcat4.1";
	public static final String SERVER_TOMCAT50 = "Tomcat5.0";
	public static final String SERVER_WEBLOGIC6 = "Weblogic6";
	private static Font DEFAULT_FONT;
	public static final Color DEFAULT_FONT_COLOR = Color.black;
	private static ColorUIResource DEFAULT_SCROLL_BAR_COLOR;

	private static boolean _redeploy = false;
	private static boolean _restart = false;
	private static boolean _stop = false;
	private static boolean _runBrowser = false;
	private static boolean _runDreamweaver = false;
	private static boolean _genVars = false;
	private static boolean _genController = false;
	private static boolean _siteMap = false;
	private static boolean _setProperties = false;
	private static boolean _setEclipseProperties = false;
	private static boolean _genModel = false;
	private static boolean _makeDefault = false;
	private static boolean _defaultLF = false;
	private static String _modelToGen = null;
	private static String _documentRoot = null;

	private static boolean _genListForm = false;
	private static boolean _genProject = false;
	private static boolean _dontAppendProject = false;
	private static String _browser = null;
	private static String _defaultWebApp = null;
	private static String _defaultFwApp = null;
	private static String _webPage = null;
	private static String _dreamWeaverFile = null;
	private static String _dreamWeaverPath = null;
	private static String _defaultHost = null;
	private static String _packageName = null;
	private static String _controllerName = null;
	private static String _defaultSourcePath = null;
	private static String _serverType = SERVER_TOMCAT40;
	private static String _xmlFile = null;
	private static String _projectName = null;
	private static String _actualFileName = null;
	private static String _appPropsPath = null;
	private static boolean _useAppProperties=false;

	private static void append(StringBuffer buf, String line, int indents) {
		for (int i = 0; i < indents; i++)
			buf.append("     ");
		buf.append(line);
	}

	private static void appendLn(StringBuffer buf, String line, int indents) {
		append(buf, line, indents);
		buf.append("\r\n");
	}

	/**
	 *  For server invocation, converts a string to base64encoding
	 */
	protected static String base64encode(String enc) {
		return new sun.misc.BASE64Encoder().encode(enc.getBytes());
	}

	/**
	 * Pops up a change dialog and saves changes to the System.properties file
	 */
	protected static void changeProperties(boolean eclipse) {
		Hashtable changes = null;
		if (eclipse)
			changes = displayEclipsePropertiesDialog();
		else
			changes = displayPropertiesDialog();

		if (changes != null) {
			try {
				Props.updateSystemProperties(changes);
			} catch (Exception e) {
				displayError(e);
			}

		}
		System.exit(0);

	}

	private static void makePageDefault() {
		String p = getProjectProperty(Props.IDE_DEFAULT_RUN_URL);
		Hashtable tab = new Hashtable();
		tab.put(p, _webPage);
		try {
			Props.updateSystemProperties(tab);
			System.out.println("Made Page:" + _webPage + " default.");
		} catch (Exception e) {
			displayError(e);
		}
	}

	/**
	 * Copies all the files in sourceDir and subdirectories to outputDir
	 */
	public static void copyDirectory(File sourceDir, File outputDir) throws IOException {
		File[] f = sourceDir.listFiles();
		if (f == null)
			return;

		for (int i = 0; i < f.length; i++) {
			if (f[i].isFile()) {
				File outputFile = new File(outputDir.getPath() + File.separatorChar + f[i].getName());
				copyFile(f[i], outputFile);
			} else if (f[i].isDirectory()) {
				File outputFile = new File(outputDir.getPath() + File.separatorChar + f[i].getName());
				if (!outputFile.exists())
					outputFile.mkdir();
				copyDirectory(f[i], outputFile);
			}
		}
	}

	/**
	 * Copies input file to output file
	 */

	public static void copyFile(File inputFile, File outputFile) throws IOException {
		BufferedInputStream fr = new BufferedInputStream(new FileInputStream(inputFile));
		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(outputFile));
		byte[] buf = new byte[8192];
		int n;
		while ((n = fr.read(buf)) >= 0)
			fw.write(buf, 0, n);
		fr.close();
		fw.close();
	}

	/**
	 * Builds all the registry stuff to create a dreamweaver site
	 */

	public static void createDreamweaverProject(String resourcesDir, String projectName, String dreamweaverDir, String host, String projectLocation) throws Exception {
		File fDreamweaverSettings = new File(resourcesDir + File.separator + "Dreamweaver" + File.separator + "Salmon SetProperties.js");
		byte[] baDreamweaverTemplate = new byte[(int) fDreamweaverSettings.length()];

		if (!fDreamweaverSettings.exists())
			return;

		FileInputStream fisDreamweaverSettings = new FileInputStream(fDreamweaverSettings);
		fisDreamweaverSettings.read(baDreamweaverTemplate);
		fisDreamweaverSettings.close();

		String sDreamweaverSettings = new String(baDreamweaverTemplate);
		sDreamweaverSettings = replace(sDreamweaverSettings, "$PROJECTNAME$", projectName);
		sDreamweaverSettings = replace(sDreamweaverSettings, "$HOST$", host);

		Properties props = System.getProperties();
		String sUserHome = props.getProperty("user.home");
		File fWriteDreamweaverSettings = new File(sUserHome + File.separator + "Application Data" + File.separator + "Macromedia" + File.separator + "Dreamweaver MX 2004" + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "Salmon SetProperties.js");
		if (!fWriteDreamweaverSettings.exists()) {
			fWriteDreamweaverSettings = new File(sUserHome + File.separator + "Application Data" + File.separator + "Macromedia" + File.separator + "Dreamweaver MX 2004" + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "SalmonSetProperties.js");
			if (!fWriteDreamweaverSettings.exists()) {
				fWriteDreamweaverSettings = new File(sUserHome + File.separator + "Application Data" + File.separator + "Macromedia" + File.separator + "Dreamweaver MX" + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "Salmon SetProperties.js");
				if (!fWriteDreamweaverSettings.exists()) {
					fWriteDreamweaverSettings = new File(sUserHome + File.separator + "Application Data" + File.separator + "Macromedia" + File.separator + "Dreamweaver MX" + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "SalmonSetProperties.js");
					if (!fWriteDreamweaverSettings.exists()) {
						fWriteDreamweaverSettings = new File(dreamweaverDir + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "Salmon SetProperties.js");
						if (!fWriteDreamweaverSettings.exists()) {
							fWriteDreamweaverSettings = new File(dreamweaverDir + File.separator + "Configuration" + File.separator + "Startup" + File.separator + "SalmonSetProperties.js");
						}
					}
				}
			}
		}

		if (!fWriteDreamweaverSettings.exists())
			return;

		String currentAppArray = getDreamweaverArrayInfo(fWriteDreamweaverSettings, "APPARRAY");
		currentAppArray += "APPARRAY[\"" + projectName + "\"]=\"" + projectName + "\";";
		sDreamweaverSettings = replace(sDreamweaverSettings, "$APPARRAY$", currentAppArray);

		String currentURLArray = getDreamweaverArrayInfo(fWriteDreamweaverSettings, "SERVLETARRAY");
		currentURLArray += "SERVLETURLARRAY[\"" + projectName + "\"]=\"http://" + host + "/" + projectName + "\";";
		sDreamweaverSettings = replace(sDreamweaverSettings, "$SERVLETARRAY$", currentURLArray);

		File fBackup = new File(fWriteDreamweaverSettings.getAbsolutePath() + ".bak");
		if (fBackup.exists())
			fBackup.delete();
		fWriteDreamweaverSettings.renameTo(new File(fWriteDreamweaverSettings.getAbsolutePath() + ".bak"));
		FileOutputStream fosDreamweaverSettings = new FileOutputStream(fWriteDreamweaverSettings);
		fosDreamweaverSettings.write(sDreamweaverSettings.getBytes());
		fosDreamweaverSettings.close();

		String sVer = "7";
		Registry rDream = null;
		String sPath = "";

		try {
			rDream = new Registry(Registry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Macromedia\\Dreamweaver\\" + sVer + "\\Installation");
			sPath = rDream.getStringAttribute("InstallPath");

			if (sPath == null || sPath.trim().equals("")) {
				sVer = "6";
				rDream = new Registry(Registry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Macromedia\\Dreamweaver\\" + sVer + "\\Installation");
				sPath = rDream.getStringAttribute("InstallPath");

				if (sPath == null || sPath.trim().equals(""))
					sVer = "4";
			}
		} catch (Exception re) {
			sVer = "4";
		}

		int iVersion = Integer.parseInt(sVer);
		Registry rSummary = null;
		Registry rSite = null;
		int iNoSites = 0;

		switch (iVersion) {
			default :
			case 4 :
				rSummary = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver " + sVer + "\\Sites\\-Summary");
				iNoSites = rSummary.getIntAttribute("Number of Sites");
				rSite = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver " + sVer + "\\Sites\\-Site" + iNoSites);
				break;

			case 6 :
				rSummary = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver " + sVer + "\\Sites\\-Summary");
				iNoSites = rSummary.getIntAttribute("Number of Sites");
				rSite = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver " + sVer + "\\Sites\\-Site" + iNoSites);
				break;

			case 7 :
				rSummary = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Summary");
				iNoSites = rSummary.getIntAttribute("Number of Sites");
				rSite = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Site" + iNoSites);
				break;
		}

		// Part common to all versions of Dreamweaver supported here namely DW4, UD4, MX v.6 and MX 2004 v.7
		rSite.setIntAttribute("Access Type", 2);
		rSite.setIntAttribute("Check Out When Opening Files", 1);
		rSite.setIntAttribute("Column Width", 125);
		rSite.setIntAttribute("Column Width Count", 0);
		rSite.setIntAttribute("Dont Show Remote Time Warning", 0);
		rSite.setStringAttribute("Email Address", "");
		rSite.setIntAttribute("Enable Check In/Check Out", 0);
		rSite.setIntAttribute("Firewall", 0);
		rSite.setIntAttribute("Hidden Count", 0);
		rSite.setStringAttribute("Home Page", "");
		rSite.setStringAttribute("Host", "");
		rSite.setStringAttribute("Local Directory", projectLocation + File.separator);
		rSite.setIntAttribute("Local Expanded Dirs", 1);
		rSite.setIntAttribute("Pages Per Row", 200);
		rSite.setIntAttribute("PASV FTP", 1);
		rSite.setIntAttribute("Refresh Local", 1);
		rSite.setIntAttribute("Refresh Remote", 1);
		rSite.setStringAttribute("Remote Directory", "");
		rSite.setIntAttribute("Remote Expanded Dirs", 0);
		rSite.setStringAttribute("Root Dest", "");
		rSite.setIntAttribute("Root Number of Parents", 0);
		rSite.setStringAttribute("Root Source", "");
		rSite.setStringAttribute("Root URL", "http://" + host + "/" + projectName + "/");
		rSite.setIntAttribute("Save PW", 0);
		rSite.setStringAttribute("Server Model", "JSP");
		rSite.setStringAttribute("Server PageExt", ".jsp");
		rSite.setStringAttribute("Server Scripting", "Java");
		rSite.setIntAttribute("Share Columns", 1);
		rSite.setIntAttribute("Show Dependent Files", 0);
		rSite.setIntAttribute("Show Hidden Files", 0);
		rSite.setIntAttribute("Show Page Titles", 0);
		rSite.setStringAttribute("Site Name", projectName);
		rSite.setStringAttribute("Source Control System Type", "");
		rSite.setStringAttribute("Spacer File Path", "");
		rSite.setIntAttribute("Upload Metafile", 1);
		rSite.setStringAttribute("URL Prefix", "http://" + host + "/" + projectName + "/");
		rSite.setIntAttribute("Use Cache", 0);
		rSite.setIntAttribute("Use Metafile", 1);
		rSite.setStringAttribute("User", "");
		rSite.setStringAttribute("User Alias", "");
		rSite.setStringAttribute("User PW", "");
		rSite.setIntAttribute("ZOOM LEVEL", 8);

		// Part pertaining only to Dreamweaver MX v.6.0
		if (sVer.equals("6")) {
			rSite.setStringAttribute("LDSSH Address:Port", "");
			rSite.setStringAttribute("LDSSH Default", "");
			rSite.setStringAttribute("LDSSH Profile", "");
			rSite.setIntAttribute("LDUse SSH", 0);
			rSite.setStringAttribute("SSH Address:Port", "");
			rSite.setStringAttribute("SSH Default", "");
			rSite.setStringAttribute("SSH Profile", "");
			rSite.setStringAttribute("TestSrvBinSSH Address:Port", "");
			rSite.setStringAttribute("TestSrvBinSSH Default", "");
			rSite.setStringAttribute("TestSrvBinSSH Profile", "");
			rSite.setIntAttribute("TestSrvBinSSH Use SSH", 0);
			rSite.setIntAttribute("Use SSH", 0);
		}

		// Part common to both Dreamweaver MX v.6.0 and MX 2004 v.7.0
		if ((sVer.equals("6")) || (sVer.equals("7"))) {
			rSite.setStringAttribute("AdminURL", "");
			rSite.setIntAttribute("Auto Upload Files", 0);
			rSite.setIntAttribute("CF DSN List Test Result", 0);
			rSite.setIntAttribute("Cloak By Pattern", 0);
			rSite.setIntAttribute("Cloaked Dirs", 0);
			rSite.setStringAttribute("Cloaked Patterns", "");
			rSite.setIntAttribute("Cloaking Enabled", 0);
			rSite.setStringAttribute("Default Document Type", "HTML");
			rSite.setStringAttribute("Image Directory", "");
			rSite.setIntAttribute("Live Data Access Type", 1);
			rSite.setIntAttribute("Live Data Firewall", 0);
			rSite.setStringAttribute("Live Data Host", "");
			rSite.setIntAttribute("Live Data PASV FTP", 0);
			rSite.setIntAttribute("Live Data Refresh Remote", 0);
			rSite.setStringAttribute("Live Data Remote Directory", projectLocation + File.separator);
			rSite.setIntAttribute("Live Data Save PW", 0);
			rSite.setStringAttribute("Live Data Source Control System Type", "");
			rSite.setStringAttribute("Live Data User", "");
			rSite.setStringAttribute("Live Data User PW", "");
			rSite.setIntAttribute("RDS Login Test Result", 0);
			rSite.setStringAttribute("RDS Password", "");
			rSite.setStringAttribute("RDS Username", "");
			rSite.setIntAttribute("Server Objects Version", 1);
			rSite.setIntAttribute("Test Srv Bin Access Type", 2);
			rSite.setIntAttribute("Test Srv Bin Firewall", 0);
			rSite.setStringAttribute("Test Srv Bin Host", "");
			rSite.setIntAttribute("Test Srv Bin PASV FTP", 0);
			rSite.setStringAttribute("Test Srv Bin Remote Directory", "");
			rSite.setIntAttribute("Test Srv Bin Save PW", 0);
			rSite.setStringAttribute("Test Srv Bin User", "");
			rSite.setStringAttribute("Test Srv Bin User PW", "");
			rSite.setIntAttribute("Test Srv Bin Was Set", 0);
			rSite.setIntAttribute("UltraDev Connections Migrated", 0);
			rSite.setIntAttribute("URL Prefix Test Result", 0);
			rSite.setIntAttribute("Wizard Site Type", 0);
		}

		// Part pertaining only to Dreamweaver MX 2004 v.7.0
		if (sVer.equals("7")) {
			rSite.setStringAttribute("Apply Fonts", "font");
			rSite.setIntAttribute("Contributor Integration Enabled", 0);
			rSite.setIntAttribute("Controlled", 0);
			rSite.setStringAttribute("Group Name", "");
			rSite.setStringAttribute("Group Password", "");
			rSite.setStringAttribute("Live Data Path Name Character Set", "Windows-1252");
			rSite.setIntAttribute("Live Data SFTP", 0);
			rSite.setStringAttribute("Local Expanded Dir0", projectLocation);
			rSite.setStringAttribute("Path Name Character Set", "Windows-1252");
			rSite.setIntAttribute("ServerConfiguration", 0);
			rSite.setIntAttribute("SFTP", 0);
			rSite.setStringAttribute("Shadow Root", "");
			rSite.setStringAttribute("Shared File Root", "");
			rSite.setStringAttribute("Shared Site Root", "");
			rSite.setStringAttribute("Site Admin", "");
			rSite.setStringAttribute("Site Admin Email", "");
			rSite.setIntAttribute("Site Disabled State", 0);
			rSite.setStringAttribute("Site Disabled State Error Msg", "");
			rSite.setStringAttribute("Test Svr Bin Path Name character Set", "Windows-1252");
			rSite.setIntAttribute("Test Svr Bin SFTP", 0);
			rSite.setIntAttribute("User Can Change Paragraphs", 1);
			rSite.setIntAttribute("User Can USe CSS", 1);
			rSite.setIntAttribute("Workflow", 0);

			Registry rAllowedFonts = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Site" + iNoSites + "\\Allowed Fonts");
			rAllowedFonts.setIntAttribute("Total", 0);

			Registry rCSS = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Site" + iNoSites + "\\CSS");
			rCSS.setStringAttribute("LastSamplePath", "");

			Registry rDropFolders = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Site" + iNoSites + "\\Drop Folders");
			rDropFolders.setIntAttribute("Total", 0);

			Registry rReviewers = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites\\-Site" + iNoSites + "\\Reviewers");
			rReviewers.setIntAttribute("Total", 0);

			// ------------------------------------------
			// We also have to populate the site backup |
			// ------------------------------------------
			Registry rSiteBackup = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites_Backup\\-Site" + iNoSites);

			// Part common to all versions of Dreamweaver supported here namely DW4, UD4, MX v.6 and MX 2004 v.7
			rSiteBackup.setIntAttribute("Access Type", 2);
			rSiteBackup.setIntAttribute("Check Out When Opening Files", 1);
			rSiteBackup.setIntAttribute("Column Width", 125);
			rSiteBackup.setIntAttribute("Column Width Count", 0);
			rSiteBackup.setIntAttribute("Dont Show Remote Time Warning", 0);
			rSiteBackup.setStringAttribute("Email Address", "");
			rSiteBackup.setIntAttribute("Enable Check In/Check Out", 0);
			rSiteBackup.setIntAttribute("Firewall", 0);
			rSiteBackup.setIntAttribute("Hidden Count", 0);
			rSiteBackup.setStringAttribute("Home Page", "");
			rSiteBackup.setStringAttribute("Host", "");
			rSiteBackup.setStringAttribute("Local Directory", projectLocation + File.separator);
			rSiteBackup.setIntAttribute("Local Expanded Dirs", 1);
			rSiteBackup.setIntAttribute("Pages Per Row", 200);
			rSiteBackup.setIntAttribute("PASV FTP", 1);
			rSiteBackup.setIntAttribute("Refresh Local", 1);
			rSiteBackup.setIntAttribute("Refresh Remote", 1);
			rSiteBackup.setStringAttribute("Remote Directory", "");
			rSiteBackup.setIntAttribute("Remote Expanded Dirs", 0);
			rSiteBackup.setStringAttribute("Root Dest", "");
			rSiteBackup.setIntAttribute("Root Number of Parents", 0);
			rSiteBackup.setStringAttribute("Root Source", "");
			rSiteBackup.setStringAttribute("Root URL", "http://" + host + "/" + projectName + "/");
			rSiteBackup.setIntAttribute("Save PW", 0);
			rSiteBackup.setStringAttribute("Server Model", "JSP");
			rSiteBackup.setStringAttribute("Server PageExt", ".jsp");
			rSiteBackup.setStringAttribute("Server Scripting", "Java");
			rSiteBackup.setIntAttribute("Share Columns", 1);
			rSiteBackup.setIntAttribute("Show Dependent Files", 0);
			rSiteBackup.setIntAttribute("Show Hidden Files", 0);
			rSiteBackup.setIntAttribute("Show Page Titles", 0);
			rSiteBackup.setStringAttribute("Site Name", projectName);
			rSiteBackup.setStringAttribute("Source Control System Type", "");
			rSiteBackup.setStringAttribute("Spacer File Path", "");
			rSiteBackup.setIntAttribute("Upload Metafile", 1);
			rSiteBackup.setStringAttribute("URL Prefix", "http://" + host + "/" + projectName + "/");
			rSiteBackup.setIntAttribute("Use Cache", 0);
			rSiteBackup.setIntAttribute("Use Metafile", 1);
			rSiteBackup.setStringAttribute("User", "");
			rSiteBackup.setStringAttribute("User Alias", "");
			rSiteBackup.setStringAttribute("User PW", "");
			rSiteBackup.setIntAttribute("ZOOM LEVEL", 8);

			// Part common to both Dreamweaver MX v.6.0 and MX 2004 v.7.0
			rSiteBackup.setStringAttribute("AdminURL", "");
			rSiteBackup.setIntAttribute("Auto Upload Files", 0);
			rSiteBackup.setIntAttribute("CF DSN List Test Result", 0);
			rSiteBackup.setIntAttribute("Cloak By Pattern", 0);
			rSiteBackup.setIntAttribute("Cloaked Dirs", 0);
			rSiteBackup.setStringAttribute("Cloaked Patterns", "");
			rSiteBackup.setIntAttribute("Cloaking Enabled", 0);
			rSiteBackup.setStringAttribute("Default Document Type", "HTML");
			rSiteBackup.setStringAttribute("Image Directory", "");
			rSiteBackup.setIntAttribute("Live Data Access Type", 1);
			rSiteBackup.setIntAttribute("Live Data Firewall", 0);
			rSiteBackup.setStringAttribute("Live Data Host", "");
			rSiteBackup.setIntAttribute("Live Data PASV FTP", 0);
			rSiteBackup.setIntAttribute("Live Data Refresh Remote", 0);
			rSiteBackup.setStringAttribute("Live Data Remote Directory", projectLocation + File.separator);
			rSiteBackup.setIntAttribute("Live Data Save PW", 0);
			rSiteBackup.setStringAttribute("Live Data Source Control System Type", "");
			rSiteBackup.setStringAttribute("Live Data User", "");
			rSiteBackup.setStringAttribute("Live Data User PW", "");
			rSiteBackup.setIntAttribute("RDS Login Test Result", 0);
			rSiteBackup.setStringAttribute("RDS Password", "");
			rSiteBackup.setStringAttribute("RDS Username", "");
			rSiteBackup.setIntAttribute("Server Objects Version", 1);
			rSiteBackup.setIntAttribute("Test Srv Bin Access Type", 2);
			rSiteBackup.setIntAttribute("Test Srv Bin Firewall", 0);
			rSiteBackup.setStringAttribute("Test Srv Bin Host", "");
			rSiteBackup.setIntAttribute("Test Srv Bin PASV FTP", 0);
			rSiteBackup.setStringAttribute("Test Srv Bin Remote Directory", "");
			rSiteBackup.setIntAttribute("Test Srv Bin Save PW", 0);
			rSiteBackup.setStringAttribute("Test Srv Bin User", "");
			rSiteBackup.setStringAttribute("Test Srv Bin User PW", "");
			rSiteBackup.setIntAttribute("Test Srv Bin Was Set", 0);
			rSiteBackup.setIntAttribute("UltraDev Connections Migrated", 0);
			rSiteBackup.setIntAttribute("URL Prefix Test Result", 0);
			rSiteBackup.setIntAttribute("Wizard Site Type", 0);

			// Part pertaining only to Dreamweaver MX 2004 v.7.0
			rSiteBackup.setStringAttribute("Apply Fonts", "font");
			rSiteBackup.setIntAttribute("Contributor Integration Enabled", 0);
			rSiteBackup.setIntAttribute("Controlled", 0);
			rSiteBackup.setStringAttribute("Group Name", "");
			rSiteBackup.setStringAttribute("Group Password", "");
			rSiteBackup.setStringAttribute("Live Data Path Name Character Set", "Windows-1252");
			rSiteBackup.setIntAttribute("Live Data SFTP", 0);
			rSiteBackup.setStringAttribute("Local Expanded Dir0", projectLocation);
			rSiteBackup.setStringAttribute("Path Name Character Set", "Windows-1252");
			rSiteBackup.setIntAttribute("ServerConfiguration", 0);
			rSiteBackup.setIntAttribute("SFTP", 0);
			rSiteBackup.setStringAttribute("Shadow Root", "");
			rSiteBackup.setStringAttribute("Shared File Root", "");
			rSiteBackup.setStringAttribute("Shared Site Root", "");
			rSiteBackup.setStringAttribute("Site Admin", "");
			rSiteBackup.setStringAttribute("Site Admin Email", "");
			rSiteBackup.setIntAttribute("Site Disabled State", 0);
			rSiteBackup.setStringAttribute("Site Disabled State Error Msg", "");
			rSiteBackup.setStringAttribute("Test Svr Bin Path Name character Set", "Windows-1252");
			rSiteBackup.setIntAttribute("Test Svr Bin SFTP", 0);
			rSiteBackup.setIntAttribute("User Can Change Paragraphs", 1);
			rSiteBackup.setIntAttribute("User Can USe CSS", 1);
			rSiteBackup.setIntAttribute("Workflow", 0);

			Registry rAllowedFontsBackup = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites_Backup\\-Site" + iNoSites + "\\Allowed Fonts");
			rAllowedFontsBackup.setIntAttribute("Total", 0);

			Registry rCSSBackup = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites_Backup\\-Site" + iNoSites + "\\CSS");
			rCSSBackup.setStringAttribute("LastSamplePath", "");

			Registry rDropFoldersBackup = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites_Backup\\-Site" + iNoSites + "\\Drop Folders");
			rDropFoldersBackup.setIntAttribute("Total", 0);

			Registry rReviewersBackup = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Common\\2004\\Sites_Backup\\-Site" + iNoSites + "\\Reviewers");
			rReviewersBackup.setIntAttribute("Total", 0);
		}

		// Set the relevant variables in the Summary key
		rSummary.setIntAttribute("Current Site", iNoSites);
		rSummary.setIntAttribute("Number of Sites", iNoSites + 1);

		// Part pertaining only to Dreamweaver MX 2004 v.7.0
		if (sVer.equals("7")) {
			int iNbrRecentSites = rSummary.getIntAttribute("Number of Recent Sites");
			if (iNbrRecentSites == 0) {
				rSummary.setIntAttribute("Number of Recent Sites", 1);
				rSummary.setStringAttribute("RecentSite0", projectName);
			} else {
				// Arbitrary #recent sites: 5
				if (iNbrRecentSites > 5) {
					// We are losing the least recent site
					for (int iSiteNbr = iNbrRecentSites - 1; iSiteNbr > 0; iSiteNbr--)
						rSummary.setStringAttribute("RecentSite" + iSiteNbr, rSummary.getStringAttribute("RecentSite" + (iSiteNbr - 1)));

					rSummary.setStringAttribute("RecentSite0", projectName);
				} else {
					// We are shifting down all sites to make room for SofiaExamples
					for (int iSiteNbr = iNbrRecentSites; iSiteNbr > 0; iSiteNbr--)
						rSummary.setStringAttribute("RecentSite" + iSiteNbr, rSummary.getStringAttribute("RecentSite" + (iSiteNbr - 1)));

					rSummary.setStringAttribute("RecentSite0", projectName);
				}
			}
		}

		// Set the last directory depending on the version
		Registry rSettings = null;
		if ((sVer.equals("4")) || (sVer.equals("6"))) {
			rSettings = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver " + sVer + "\\Settings");
		} else if (sVer.equals("7")) {
			rSettings = new Registry(Registry.HKEY_CURRENT_USER, "Software\\Macromedia\\Dreamweaver MX 2004\\Settings");
		}

		if (rSettings != null)
			rSettings.setStringAttribute("LastDirectory", projectLocation + File.separator + "Jsp");
	}

	private static String getDreamweaverArrayInfo(File configFile, String token) {
		String ret = "";
		try {
			String startToken = "//$" + token + "START$";
			String endToken = "//$" + token + "END$";
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			String st = reader.readLine();
			boolean started = false;
			while (st != null) {
				if (st.equals(endToken))
					break;

				if (started)
					ret += st + "\r\n";

				if (st.equals(startToken))
					started = true;

				st = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			displayError(e);
		}

		return ret;
	}
	/**
	 * Pops up a create project dialog and saves changes to the System.properties file
	 */
	protected static void createProject() {
		String sSourceDir = displayProjectDialog();
		if (sSourceDir != null) {
			Hashtable changes = new Hashtable();
			changes.put(Props.IDE_DEFAULT_SOURCE_PATH, sSourceDir.replace('/', File.separatorChar));
			try {
				File outFile = new File(Props.getPropsPath() + File.separator + "System$temp.properties");
				File inFile = new File(Props.getPropsPath() + File.separator + "System.properties");
				PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
				String s = in.readLine();
				while (s != null) {
					int ndx = s.indexOf('=');
					if (ndx == -1)
						out.println(s);
					else {
						String key = s.substring(0, ndx).trim();
						if (changes.containsKey(key)) {
							out.println(key + "=" + fixSlashes((String) changes.get(key)));
							changes.remove(key);
						} else
							out.println(s);
					}
					s = in.readLine();
				}

				Enumeration e = changes.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String val = (String) changes.get(key);
					out.println(key + "=" + fixSlashes(val));
				}

				in.close();
				out.close();
				inFile.delete();
				outFile.renameTo(inFile);
			} catch (Exception e) {
				displayError(e);
			}

		}
		System.exit(0);

	}

	/**
	 *  Displays a cut paste dialog box with the contents of the text
	 */
	public static void displayCutPasteDialog(String fileText) {
		JFrame f = new JFrame();
		new CutPasteDialog(f, fileText);
		f.dispose();
	}

	/**
	 * Displays a dialog for editing the system.properties file Eclipse IDE based properties. It returns a Hashtable of changes or null if nothing was changed
	 */
	protected static Hashtable displayEclipsePropertiesDialog() {
		JFrame f = new JFrame();
		EclipsePropertiesDialog d = new EclipsePropertiesDialog(f, Props.getSystemProps());
		if (d.getCancel())
			return null;
		else {
			Hashtable ret = d.getChangedValues();
			if (ret.size() == 0)
				return null;
			else
				return ret;
		}
	}

	protected static void displayError(Exception e) {
		if (e instanceof ConnectException)
			displayError("Error connecting to the test server.\nMake sure that the server is running and that property IDEDefaulttHost in the System.properties file is pointing to the correct host and port.");
		else
			displayError(e.getMessage() == null ? e.toString() : e.getMessage());
		e.printStackTrace();
	}

	/**
	 * Displays an Error Message dialog and terminates the program
	 */
	protected static void displayError(String error) {
		displayError(error, true);
	}

	/**
	 * Displays an Error Message dialog and allows a parameter to decided whether to terminate the program immediately after
	 */
	protected static void displayError(String error, boolean exit) {
		System.out.println("Error Display:" + error);
		JFrame f = new JFrame();
		JOptionPane.showMessageDialog(f, error, "Error", JOptionPane.ERROR_MESSAGE);
		f.dispose();
		if (exit)
			System.exit(-1);
	}

	/**
	 * Displays an Error Message dialog and allows a parameter to decided whether to terminate the program immediately after
	 */
	protected static void displayInfo(String info) {
		System.out.println("Info Display:" + info);
		JFrame f = new JFrame();
		JOptionPane.showMessageDialog(f, info, "Information", JOptionPane.INFORMATION_MESSAGE);
		f.dispose();
	}

	/**
	 * Displays a dialog for creating a ListForm
	 */
	protected static void displayListFormDialog() {
		JFrame f = new JFrame();
		new ListFormDialog(f);
		/*       while (cont) {
		           if (d.getCancelClicked()) {
		               d.dispose();
		               cont = false;
		           }
		           else {
		               String className = d.getClassName();
		               String packageName = null;
		               int ndx = className.lastIndexOf(".");
		               if (ndx > -1) {
		                   packageName = className.substring(0,ndx);
		                   className = className.substring(ndx + 1);
		               }
		
		               String fileName = genClassFileName(packageName,className,_defaultSourcePath,_defaultFwApp);
		               String text = generateModel(packageName,className,d);
		              //*****Delete Me*****
		              // displayCutPasteDialog(text);
		              // d.setVisible(true);
		               //*****Delete Me*****
		               if (saveClass(fileName,text)) {
		                   d.dispose();
		                   cont = false;
		               }
		               else {
		                   d.setVisible(true);
		               }
		           }
		       }*/
	}

	/**
	 * Displays a dialog for creating a model and returns the text for the model
	 */
	protected static void displayModelDialog() {
		JFrame f = new JFrame();
		boolean cont = true;
		ModelDialog d = new ModelDialog(f, _modelToGen, _defaultLF ? -1 : 28);
		while (cont) {
			if (d.getCancelClicked()) {
				d.dispose();
				cont = false;
			} else {
				String className = d.getClassName();
				String packageName = null;
				int ndx = className.lastIndexOf(".");
				if (ndx > -1) {
					packageName = className.substring(0, ndx);
					className = className.substring(ndx + 1);
				}
				if (_defaultSourcePath == null) {
					_defaultSourcePath = "c:/";
				}
				String fileName = genClassFileName(packageName, className, _defaultSourcePath, _defaultFwApp, _dontAppendProject);
				String text = generateModel(packageName, className, d);
				text = mergeModels(text, fileName);
				if (saveClass(fileName, text)) {
					d.dispose();
					cont = false;
				} else {
					d.setVisible(true);
				}
			}
		}
	}

	/**
	 * Displays a SiteMap dialog
	 */
	protected static void displaySiteMapDialog() {
		JFrame f = new JFrame();
		SiteMapDialog d = new SiteMapDialog(f);
		d.setVisible(true);
	}
	/**
	 * Displays a dialog for creating a new project. It returns a String of the new Default Source Directory
	 */
	protected static String displayProjectDialog() {
		JFrame f = new JFrame();
		ProjectDialog d = new ProjectDialog(f, Props.getSystemProps());
		if (d.getCancel())
			return null;
		else {
			String ret = d.getSourceDir();
			return ret;
		}
	}

	/**
	 * Displays a dialog for editing the system.properties file IDE based properties. It returns a Hashtable of changes or null if nothing was changed
	 */
	protected static Hashtable displayPropertiesDialog() {
		JFrame f = new JFrame();
		PropertiesDialog d = new PropertiesDialog(f, Props.getSystemProps());
		if (d.getCancel())
			return null;
		else {
			Hashtable ret = d.getChangedValues();
			if (ret.size() == 0)
				return null;
			else
				return ret;
		}
	}

	/**
	 * Displays a OK, Cancel Question dialog with the string passed. Returns true if the user clicks OK and false if CANCEL
	 */
	protected static boolean displayQuestion(String question) {
		System.out.println("Question:" + question);
		JFrame f = new JFrame();
		int dialogRet = JOptionPane.showOptionDialog(f, question, "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		f.dispose();
		return dialogRet == JOptionPane.OK_OPTION;
	}

	/**
	 * Displays a file save dialog for a generated controller. Pass the default filename and the text for the controller
	 */
	protected static String displaySaveDialog(String fileName, String text) {
		JFrame f = new JFrame();
		SaveDialog d = new SaveDialog(f, fileName, text);
		if (d.getCancel()) {
			f.dispose();
			return null;
		} else {
			f.dispose();
			return d.getFileName();
		}

	}

	private static String fixColName(String name) {
		StringBuffer sb = new StringBuffer(name.length());
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '.' || c == '-') {
				c = '_';
			} else {
				c = Character.toUpperCase(c);
			}

			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Fixes HTML characters in controller code and converts then to regular ASCII.
	 * It will convert &nbsp; &quot; and <BR>
	 */
	protected static String fixHtml(String line) {
		StringBuffer ret = new StringBuffer(line.length());
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '&') {
				int ndx = line.indexOf(';', i);
				String text = line.substring(i, ndx + 1);
				if (text.equals("&nbsp;")) {
					ret.append(' ');
					i = ndx;
				} else if (text.equals("&quot;")) {
					ret.append('"');
					i = ndx;
				}
			} else if (c == '<') {
				int ndx = line.indexOf('>', i);
				String text = line.substring(i, ndx + 1);
				if (text.equals("<BR>"))
					ret.append("\n");
				i = ndx;
			} else
				ret.append(c);
		}
		return ret.toString();
	}

	private static String fixSlashes(String in) {
		StringBuffer ret = new StringBuffer(in.length());
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\\')
				ret.append("\\\\");
			else
				ret.append(c);
		}
		return ret.toString();
	}

	private static String fixSlashesAndQuotes(String in) {
		StringBuffer ret = new StringBuffer(in.length());
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\\')
				ret.append("\\\\");
			else if (c == '"')
				ret.append("\\\"");
			else
				ret.append(c);
		}
		return ret.toString();
	}

	/**
	 * generates a file name for a controller given the package and the controller class name
	 */
	public static String genClassFileName(String packageName, String controllerName, String defaultPath, String defaultApp, boolean dontAppendApp) {
		StringBuffer b = new StringBuffer(defaultPath);
		if (!dontAppendApp) {
			b.append(File.separatorChar);
			b.append(defaultApp);
		}
		if (packageName != null && packageName.length() > 0) {
			b.append(File.separatorChar);
			for (int i = 0; i < packageName.length(); i++) {
				char c = packageName.charAt(i);
				if (c == '.')
					b.append(File.separatorChar);
				else
					b.append(c);
			}
		}
		b.append(File.separatorChar);
		b.append(controllerName);
		b.append(".java");
		return b.toString();
	}

	/**
	 * Invokes a command to generate either a controller or vars. Pass the url of the page, the command (vars or genController)
	 * and the prefix that indicates that the generated code is about to start. The method
	 * returns the generated code.
	 */

	protected static String genCommand(String url, String command, String prefix) throws Exception {
		if (command != null)
			command = url + "?" + command + "=true";
		else
			command = url;
		URL u = new URL(command);
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

		if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			String newLocation = conn.getHeaderField("Location");
			String cookie = conn.getHeaderField("Set-Cookie");
			int ndx = cookie.indexOf(";");
			if (ndx > -1)
				cookie = cookie.substring(0, ndx);
			InputStream in = conn.getInputStream();
			in.close();
			u = new URL(newLocation);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Cookie", cookie);
		}

		InputStream in = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer b = new StringBuffer();
		boolean started = (prefix == null);

		String l = reader.readLine();
		while (l != null) {
			if (!started && l.equals(prefix))
				started = true;
			if (started) {
				if (l.startsWith("package"))
					_packageName = l.substring(8, l.length() - 1);
				else if (l.startsWith("public class")) {
					_controllerName = l.substring(13);
					int ndx = _controllerName.indexOf(" ");
					_controllerName = _controllerName.substring(0, ndx);
				}

				l = fixHtml(l);
				b.append(l);
			}
			l = reader.readLine();
		}

		reader.close();
		in.close();
		return b.toString();
	}

	private static String generateModel(String packageName, String className, ModelDialog d) {
		ModelDialog.ColumnDef[] cols = d.getColumns();
		ModelDialog.BucketDef[] buckets = d.getBuckets();
		ModelDialog.SortDef[] sort = d.getSortColumns();
		ModelDialog.JoinDef[] joins = d.getJoins();
		ModelDialog.AliasDef[] alias = d.getAliases();
		ModelDialog.QBECriteriaDef[] qbe = d.getQBECriteria();
		ValidationRuleDefinition[] validations = d.getValidationRules();
		boolean isQBE = d.isQBE();

		StringBuffer sb = new StringBuffer();
		//package
		append(sb, "package ", 0);
		append(sb, packageName, 0);
		appendLn(sb, ";", 0);
		appendLn(sb, "", 0);

		//imports
		appendLn(sb, "import com.salmonllc.sql.*;", 0);
		// comments for custom imports
		appendLn(sb, "", 0);
		appendLn(sb, "//$CUSTOMIMPORTS$", 0);
		appendLn(sb, "//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated", 0);
		appendLn(sb, "", 0);
		appendLn(sb, "//$ENDCUSTOMIMPORTS$", 0);
		appendLn(sb, "", 0);

		//class name
		appendLn(sb, "/**", 0);
		appendLn(sb, " * " + className + ": A SOFIA generated model", 0);
		appendLn(sb, " */", 0);
		append(sb, "public class ", 0);
		append(sb, className, 0);
		if (isQBE)
			append(sb, " extends QBEBuilder ", 0);
		else
			append(sb, " extends DataStore ", 0);
		if (d.isRemotable())
			append(sb, "implements Remotable ", 0);

		appendLn(sb, "{", 0);
		appendLn(sb, "", 0);

		if (!isQBE) {
			//table column constants
			if (cols.length > 0) {
				appendLn(sb, "//constants for columns", 1);
				for (int i = 0; i < cols.length; i++) {
					String name = cols[i].getName();
					append(sb, "public static final String ", 1);
					append(sb, fixColName(name), 0);
					append(sb, "=\"", 0);
					append(sb, name, 0);
					appendLn(sb, "\";", 0);
				}
				appendLn(sb, "", 0);
			}

			//bucket column constants
			if (buckets.length > 0) {
				appendLn(sb, "//constants for buckets", 1);
				for (int i = 0; i < buckets.length; i++) {
					String name = buckets[i].name;
					append(sb, "public static final String ", 1);
					append(sb, fixColName(name), 0);
					append(sb, "=\"", 0);
					append(sb, name, 0);
					appendLn(sb, "\";", 0);
				}
				appendLn(sb, "", 0);
			}
		} else {
			//qbe column constants	
			if (qbe.length > 0) {
				appendLn(sb, "//constants for qbe criteria", 1);
				for (int i = 0; i < qbe.length; i++) {
					String name = qbe[i].name;
					append(sb, "public static final String ", 1);
					append(sb, fixColName(name), 0);
					append(sb, "=\"", 0);
					append(sb, name, 0);
					appendLn(sb, "\";", 0);
				}
				appendLn(sb, "", 0);
			}
		}

		// comments for custom code
		appendLn(sb, "//$CUSTOMVARS$", 1);
		appendLn(sb, "//Put custom instance variables between these comments, otherwise they will be overwritten if the model is regenerated", 1);
		appendLn(sb, "", 1);
		appendLn(sb, "//$ENDCUSTOMVARS$", 1);
		appendLn(sb, "", 1);

		//constructor
		appendLn(sb, "/**", 1);
		appendLn(sb, " * Create a new " + className + " object.", 1);
		if (!isQBE)
			appendLn(sb, " * @param appName The SOFIA application name", 1);
		appendLn(sb, " */", 1);
		append(sb, "public ", 1);
		append(sb, className, 0);

		if (!isQBE) {
			appendLn(sb, " (String appName) { ", 0);
			appendLn(sb, "this(appName, null);", 2);
			appendLn(sb, "}", 1);
			appendLn(sb, "", 0);

			appendLn(sb, "/**", 1);
			appendLn(sb, " * Create a new " + className + " object.", 1);
			appendLn(sb, " * @param appName The SOFIA application name", 1);
			appendLn(sb, " * @param profile The database profile to use", 1);
			appendLn(sb, " */", 1);
			append(sb, "public ", 1);
			append(sb, className, 0);
			appendLn(sb, " (String appName, String profile) { ", 0);
			appendLn(sb, "super(appName, profile);", 2);
		} else {
			appendLn(sb, " () { ", 0);
		}

		//put everything in a try catch if there are any formats in the buckets or columns or validation rules
		int indent = 2;

		if (validations.length > 0)
			indent = 3;
		else if (!isQBE) {
			for (int i = 0; i < cols.length; i++) {
				if (cols[i].format != null) {
					indent = 3;
					break;
				}
			}
			if (indent == 2) {
				for (int i = 0; i < buckets.length; i++) {
					if (buckets[i].format != null) {
						indent = 3;
						break;
					}
				}
			}
		}

		if (indent == 3) {
			appendLn(sb, "", 0);
			appendLn(sb, "try {", 2);
		}

		if (!isQBE) {
			//add aliases
			if (alias.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//add aliases", indent);
				for (int i = 0; i < alias.length; i++) {
					append(sb, "addTableAlias(computeTableName(\"", indent);
					append(sb, alias[i].table, 0);
					if (alias[i].alias == null)
						appendLn(sb, "\"),null);", 0);
					else {
						append(sb, "\"),\"", 0);
						append(sb, alias[i].alias, 0);
						appendLn(sb, "\");", 0);
					}
				}
			}

			//add columns
			if (cols.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//add columns", indent);
				for (int i = 0; i < cols.length; i++) {
					String name = fixColName(cols[i].getName());
					append(sb, "addColumn(computeTableName(\"", indent);
					if (cols[i].alias != null)
						append(sb, cols[i].alias, 0);
					else
						append(sb, cols[i].def.getTableName(), 0);
					append(sb, "\"),\"", 0);
					append(sb, cols[i].def.getColumnName(), 0);
					append(sb, "\",", 0);
					append(sb, translateDSType(cols[i].def.getDSDataType()), 0);
					append(sb, ",", 0);
					append(sb, cols[i].def.isPkey() ? "true" : "false", 0);
					append(sb, ",", 0);
					append(sb, cols[i].updateable ? "true" : "false", 0);
					append(sb, ",", 0);
					append(sb, name, 0);
					appendLn(sb, ");", 0);
					if (cols[i].format != null) {
						append(sb, "setFormat(", indent);
						append(sb, name.toUpperCase(), 0);
						append(sb, ",\"", 0);
						append(sb, cols[i].format, 0);
						appendLn(sb, "\");", 0);
					}
				}
			}

			//add buckets
			if (buckets.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//add buckets", indent);
				for (int i = 0; i < buckets.length; i++) {
					String name = buckets[i].name;
					append(sb, "addBucket(", indent);
					append(sb, fixColName(name), 0);
					append(sb, ",", 0);
					append(sb, translateDSType(buckets[i].datatype.type), 0);
					appendLn(sb, ");", 0);
					if (buckets[i].format != null) {
						append(sb, "setFormat(", indent);
						append(sb, name.toUpperCase(), 0);
						append(sb, ",\"", 0);
						append(sb, buckets[i].format, 0);
						appendLn(sb, "\");", 0);
					}
				}
			}

			//joins
			if (joins.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//add joins", indent);
				for (int i = 0; i < joins.length; i++) {
					append(sb, "addJoin(", indent);
					append(sb, genJoin(joins[i].leftSide), 0);
					append(sb, ",", 0);
					append(sb, genJoin(joins[i].rightSide), 0);
					append(sb, ",", 0);
					append(sb, joins[i].outer ? "true" : "false", 0);
					appendLn(sb, ");", 0);
				}
			}

			//Order by
			if (sort.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//set order by", indent);
				append(sb, "setOrderBy(", indent);
				for (int i = 0; i < sort.length; i++) {
					String col = "";
					if (sort[i].alias != null)
						col = sort[i].alias + "." + sort[i].def.getColumnName();
					else
						col = sort[i].def.getTableName() + "." + sort[i].def.getColumnName();

					append(sb, "computeTableAndFieldName(\"", 0);
					append(sb, col, 0);
					append(sb, "\") + \" ", 0);
					append(sb, sort[i].getDir(), 0);
					append(sb, ",\" + ", 0);
				}
				sb.setLength(sb.length() - 5);
				append(sb, "\"", 0);
				appendLn(sb, ");", 0);
			}
		} else {
			//QBECriteria
			//add buckets
			if (qbe.length > 0) {
				appendLn(sb, "", 0);
				appendLn(sb, "//add qbe criteria", indent);
				for (int i = 0; i < qbe.length; i++) {
					String name = qbe[i].name;
					append(sb, "addCriteria(", indent);
					append(sb, fixColName(name), 0);
					append(sb, ",", 0);
					append(sb, translateQBEType(qbe[i].type.type), 0);
					append(sb, ",\"", 0);
					append(sb, qbe[i].colList, 0);
					appendLn(sb, "\");", 0);
				}
			}
		}
		if (validations.length > 0) {
			appendLn(sb, "", 0);
			appendLn(sb, "//add validations", indent);
			for (int i = 0; i < validations.length; i++) {
				String name = fixColName(validations[i].columnName);
				ValidationRule rule = validations[i].rule;
				String errorMessage = rule.getErrorMessage();
				if (errorMessage == null)
					errorMessage = "null";
				else
					errorMessage = "\"" + fixSlashesAndQuotes(rule.getErrorMessage()) + "\"";

				if (rule.getRuleType() == ValidationRule.TYPE_REQUIRED) {
					append(sb, "addRequiredRule(", indent);
					append(sb, name, 0);
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);
				} else if (rule.getRuleType() == ValidationRule.TYPE_TYPE_CHECK) {
					append(sb, "addTypeCheckRule(", indent);
					append(sb, name, 0);
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);
				} else if (rule.getRuleType() == ValidationRule.TYPE_RANGE) {
					append(sb, "addRangeRule(", indent);
					append(sb, name, 0);
					if (rule.getMinValue() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",convertValue(\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getMinValue()), 0);
						append(sb, "\",", 0);
						append(sb, name, 0);
						append(sb, ")", 0);
					}
					if (rule.getMaxValue() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",convertValue(\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getMaxValue()), 0);
						append(sb, "\",", 0);
						append(sb, name, 0);
						append(sb, ")", 0);
					}
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);
				} else if (rule.getRuleType() == ValidationRule.TYPE_EXPRESSION) {
					append(sb, "addExpressionRule(", indent);
					append(sb, name, 0);
					if (rule.getExpression() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getExpression()), 0);
						append(sb, "\"", 0);
					}
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);
				} else if (rule.getRuleType() == ValidationRule.TYPE_JAVASCRIPT) {
					append(sb, "addJavaScriptRule(", indent);
					append(sb, name, 0);
					if (rule.getExpression() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getExpression()), 0);
						append(sb, "\"", 0);
					}
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);

				} else if (rule.getRuleType() == ValidationRule.TYPE_REGULAR_EXPRESSION) {
					append(sb, "addReqularExpressionRule(", indent);
					append(sb, name, 0);
					if (rule.getExpression() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getExpression()), 0);
						append(sb, "\"", 0);
					}
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);

				} else if (rule.getRuleType() == ValidationRule.TYPE_LOOKUP) {
					append(sb, "addLookupRule(", indent);
					append(sb, name, 0);
					if (rule.getLookupTable() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getLookupTable()), 0);
						append(sb, "\"", 0);
					}
					if (rule.getLookupExpression() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getLookupExpression()), 0);
						append(sb, "\"", 0);
					}
					if (rule.getDesciptionColumn() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getDesciptionColumn()), 0);
						append(sb, "\"", 0);
					}
					if (rule.getDescriptionBucket() == null)
						append(sb, ",null", 0);
					else {
						append(sb, ",\"", 0);
						append(sb, fixSlashesAndQuotes((String) rule.getDescriptionBucket()), 0);
						append(sb, "\"", 0);
					}
					append(sb, ",", 0);
					append(sb, errorMessage, 0);
					appendLn(sb, ");", 0);
				}

			}

		}
		//end try catch
		if (indent == 3) {
			appendLn(sb, "}", 2);
			appendLn(sb, "catch (DataStoreException e) {", 2);
			appendLn(sb, "com.salmonllc.util.MessageLog.writeErrorMessage(e,this);", 3);
			appendLn(sb, "}", 2);
		}

		// comments for custom code
		appendLn(sb, "", 0);
		appendLn(sb, "//$CUSTOMCONSTRUCTOR$", 2);
		appendLn(sb, "//Put custom constructor code between these comments, otherwise it be overwritten if the model is regenerated", 2);
		appendLn(sb, "", 0);
		appendLn(sb, "//$ENDCUSTOMCONSTRUCTOR$", 2);
		appendLn(sb, "", 0);

		//end of constructor
		appendLn(sb, "}", 1);

		if (!isQBE) {
			//get and set methods for columns
			for (int i = 0; i < cols.length; i++) {
				String name = fixColName(cols[i].getName());
				genGetSetMethods(cols[i].getName(), "column", name, cols[i].def.getDSDataType(), true, sb, 1);
			}

			//get and set methods for buckets
			for (int i = 0; i < buckets.length; i++) {
				genGetSetMethods(buckets[i].name, "bucket", buckets[i].name, buckets[i].datatype.type, false, sb, 1);
			}
		}

		// comments for custom code
		appendLn(sb, "", 1);
		appendLn(sb, "//$CUSTOMMETHODS$", 1);
		appendLn(sb, "//Put custom methods between these comments, otherwise they will be overwritten if the model is regenerated", 1);
		appendLn(sb, "", 1);
		appendLn(sb, "//$ENDCUSTOMMETHODS$", 1);
		appendLn(sb, "", 1);

		//end of class
		appendLn(sb, "}", 0);

		return sb.toString();

	}

	private static void genGetSetMethods(String name, String colOrBucket, String constant, int type, boolean changeCase, StringBuffer sb, int indent) {
		Class test = null;
		try {
			test = Class.forName("com.salmonllc.sql.DataStore");
		} catch (ClassNotFoundException e) {
		}

		String methName = translateConstantToMethod(constant, changeCase);
		String methType = translateDSTypeToJavaType(type);
		Class methClass = translateDSTypeToJavaClass(type);

		if (!doesMethodExist(test, "get" + methName, null, null)) {
			appendLn(sb, "", 0);
			appendLn(sb, "/**", indent);
			appendLn(sb, " * Retrieve the value of the " + name + " " + colOrBucket + " for the current row.", indent);
			appendLn(sb, " * @return " + methType, indent);
			appendLn(sb, " * @throws DataStoreException", indent);
			appendLn(sb, " */ ", indent);
			append(sb, "public ", indent);
			append(sb, methType, 0);
			append(sb, " get", 0);
			append(sb, methName, 0);
			appendLn(sb, "() throws DataStoreException {", 0);
			append(sb, "return ", indent + 1);
			append(sb, " get", 0);
			append(sb, translateDSTypeToMethod(type), 0);
			append(sb, "(", 0);
			append(sb, constant.toUpperCase(), 0);
			appendLn(sb, ");", 0);
			appendLn(sb, "}", indent);
		}

		if (!doesMethodExist(test, "get" + methName, Integer.TYPE, null)) {
			appendLn(sb, "", 0);
			appendLn(sb, "/**", indent);
			appendLn(sb, " * Retrieve the value of the " + name + " " + colOrBucket + " for the specified row.", indent);
			appendLn(sb, " * @param row which row in the table", indent);
			appendLn(sb, " * @return " + methType, indent);
			appendLn(sb, " * @throws DataStoreException", indent);
			appendLn(sb, " */ ", indent);
			append(sb, "public ", indent);
			append(sb, methType, 0);
			append(sb, " get", 0);
			append(sb, methName, 0);
			appendLn(sb, "(int row) throws DataStoreException {", 0);
			append(sb, "return ", indent + 1);
			append(sb, " get", 0);
			append(sb, translateDSTypeToMethod(type), 0);
			append(sb, "(row,", 0);
			append(sb, constant.toUpperCase(), 0);
			appendLn(sb, ");", 0);
			appendLn(sb, "}", indent);
		}

	
		if (!doesMethodExist(test, "set" + methName, methClass, null)) {
			appendLn(sb, "", 0);
			appendLn(sb, "/**", indent);
			appendLn(sb, " * Set the value of the " + name + " " + colOrBucket + " for the current row.", indent);
			appendLn(sb, " * @param newValue the new item value", indent);
			appendLn(sb, " * @throws DataStoreException", indent);
			appendLn(sb, " */ ", indent);
			append(sb, "public void", indent);
			append(sb, " set", 0);
			append(sb, methName, 0);
			append(sb, "(", 0);
			append(sb, methType, 0);
			appendLn(sb, " newValue) throws DataStoreException {", 0);
			append(sb, "set", indent + 1);
			append(sb, translateDSTypeToMethod(type), 0);
			append(sb, "(", 0);
			append(sb, constant.toUpperCase(), 0);
			appendLn(sb, ", newValue);", 0);
			appendLn(sb, "}", indent);
		}

		
		if (!doesMethodExist(test, "set" + methName, Integer.TYPE, methClass)) {
			appendLn(sb, "", 0);
			appendLn(sb, "/**", indent);
			appendLn(sb, " * Set the value of the " + name + " " + colOrBucket + " for the specified row.", indent);
			appendLn(sb, " * @param row which row in the table", indent);
			appendLn(sb, " * @param newValue the new item value", indent);
			appendLn(sb, " * @throws DataStoreException", indent);
			appendLn(sb, " */ ", indent);
			append(sb, "public void", indent);
			append(sb, " set", 0);
			append(sb, methName, 0);
			append(sb, "(int row,", 0);
			append(sb, methType, 0);
			appendLn(sb, " newValue) throws DataStoreException {", 0);
			append(sb, "set", indent + 1);
			append(sb, translateDSTypeToMethod(type), 0);
			append(sb, "(row,", 0);
			append(sb, constant.toUpperCase(), 0);
			appendLn(sb, ", newValue);", 0);
			appendLn(sb, "}", indent);
		}
	}

	private static boolean doesMethodExist(Class test, String methName, Class parm1, Class parm2) {
		Class parms[];
		if (parm1 == null) 
			parms = new Class[0];	
		else if (parm2 == null) {
			parms = new Class[1];	
			parms[0] = parm1;	
		}	
		else {
			parms = new Class[2];
			parms[0] = parm1;
			parms[1] = parm2;	
		}	
		
		boolean exists = false;
		if (test != null) {
			try {
				Method m = test.getMethod(methName, parms);
				exists = true;
			} catch (Exception e1) {
				
			} 
		}
		return exists;
	}

	private static String genJoin(String join) {
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(join, ",");
		while (st.hasMoreTokens()) {
			sb.append("computeTableAndFieldName(\"");
			sb.append(st.nextToken());
			sb.append("\") + \",\" +");
		}
		if (sb.length() > 0)
			sb.setLength(sb.length() - 8);
		return sb.toString();

	}

	public static String getProject() {
		return _projectName;
	}

	public static String getProjectProperty(String property) {
		if (_projectName == null)
			return property;
		Props p = Props.getSystemProps();
		if (p.getProperty(_projectName + "." + property) != null)
			return _projectName + "." + property;
		else
			return property;
	}

	/**
	 *  Figures out what the url would be for a given JSP on the file system.
	 */
	protected static String getURLFromFile(String host, String webPage, String defaultWebApp, String defaultFWApp, String command) {
		int ndx = -1;
		if ((defaultWebApp == null || defaultWebApp.trim().length() == 0) && _serverType == SERVER_WEBLOGIC6) {
			ndx = webPage.indexOf("DefaultWebApp" + File.separator);
			defaultWebApp = null;
		} else if ((defaultWebApp == null || defaultWebApp.trim().length() == 0) && (defaultFWApp != null)) {
			ndx = webPage.indexOf(defaultFWApp);
			defaultWebApp = "";
		}
		else
			ndx = webPage.indexOf(defaultWebApp + File.separator);
		if (ndx != -1) {
			StringBuffer b = new StringBuffer(webPage.length());
			b.append("http://");
			b.append(host);

			if (defaultWebApp != null && defaultWebApp.length() > 0) {
				b.append("/");
				int pos=defaultWebApp.indexOf(File.separator);
				if (pos > -1)
					b.append(defaultWebApp.substring(0,pos));
				else	
					b.append(defaultWebApp);
			} 
			else if (_serverType == SERVER_WEBLOGIC6)
				defaultWebApp = "DefaultWebApp";
			else
				defaultWebApp = defaultFWApp;	

			b.append("/");
			for (int j = ndx + defaultWebApp.length() + 1; j < webPage.length(); j++) {
				char c = webPage.charAt(j);
				if (c == File.separatorChar)
					b.append("/");
				else
					b.append(c);
			}
			if (command != null) {
				b.append("?");
				b.append(command);
				b.append("=true");
			}
			return b.toString();
		}
		return null;
	}

	public static String getXmlFile() {
		return _xmlFile;
	}

	/**
	 * Returns true if a class name is valid
	 */
	public static boolean isClassNameValid(String name, boolean incPackage) {
		if (name == null)
			return false;
		name = name.trim();
		if (name.length() == 0)
			return false;
		if (name.startsWith(".") || name.endsWith("."))
			return false;
		if (name.indexOf(".") == -1 && incPackage)
			return false;
		if (name.indexOf(" ") > -1)
			return false;
		return true;
	}

	/**
	 * Returns true if a class name is valid
	 */
	public static boolean isXMLNameValid(String name) {
		if (name == null)
			return false;
		name = name.trim();
		if (name.length() == 0)
			return false;
		if (!name.toLowerCase().endsWith(".xml"))
			return false;
		return true;
	}

	public static void main(String args[]) {
		Props p = null;
		try {
			p = parseArgs(args);
		} catch (Exception e) {
			displayError(e.toString());
			System.exit(-1);
		}

		try {
			if (_defaultLF)
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			if (_redeploy && (_serverType == SERVER_TOMCAT40 || _serverType == SERVER_TOMCAT41 || _serverType == SERVER_TOMCAT50)) {
				String tomcatManagerApp = p.getProperty(Props.IDE_TOMCAT_MANAGER_APP, "manager");
				String tomcatManagerUID = p.getProperty(Props.IDE_SERVER_MANAGER_ID);
				String tomcatManagerPW = p.getProperty(Props.IDE_SERVER_MANAGER_PW);
				System.out.println("Redploying web appliction:" + _defaultWebApp);
				System.out.println(tomcat4Command(_defaultHost, tomcatManagerApp, _defaultWebApp, "reload", tomcatManagerUID, tomcatManagerPW));
			} else if (_redeploy && _serverType == SERVER_WEBLOGIC6) {
				String webLogicPath = p.getProperty(Props.IDE_WEBLOGIC_APPLICATION_PATH);
				redeployWeblogicApplication(webLogicPath, _defaultWebApp);
			} else if (_restart && (_serverType == SERVER_TOMCAT40 || _serverType == SERVER_TOMCAT41 || _serverType == SERVER_TOMCAT50)) {
				if (_useAppProperties)
					setPropsPath(null);
				if (_runBrowser)
					TomcatBootstrap.execute(TomcatBootstrap.RESTART, _browser, _webPage, _serverType);
				else
					TomcatBootstrap.execute(TomcatBootstrap.RESTART, null, null, _serverType);
				System.exit(0);
			} else if (_stop && (_serverType == SERVER_TOMCAT40 || _serverType == SERVER_TOMCAT41 || _serverType == SERVER_TOMCAT50)) {
				System.out.println("Stopping Tomcat");
				TomcatBootstrap.execute(TomcatBootstrap.STOP, null, null, _serverType);
				System.out.println("Tomcat Stopped");

			}
		} catch (Exception e) {
			displayError(e);
		}

		if (_runDreamweaver) {
			System.out.println("Running Dreamweaver" + _dreamWeaverPath);
			runDreamweaver(_dreamWeaverPath, _dreamWeaverFile);
		} else if (_genController) {
			setPropsPath();
			try {
				System.out.println("Generating Controller:" + _webPage);
				String controller = genCommand(_webPage, "generateCode", "//package statement");
				if (_controllerName.equals("GeneratedController"))
					displayError("Please specify the controller attribute in this jsp's framework\npage tag before running this command.");
				else if (!isClassNameValid(_controllerName, false))
					displayError("The specified controller name in the page tag is invalid\nThe controller must have a valid Java class name.");
				else if (_packageName == null || _packageName.trim().length() == 0)
					displayError("The specified controller name in the page tag must contain a package name.");
				saveClass(genClassFileName(_packageName, _controllerName, _defaultSourcePath, _defaultFwApp, _dontAppendProject), controller);
				System.exit(0);
			} catch (Exception e) {
				if (!_webPage.startsWith("http")) {
					displayError("Could not translate the filename for the JSP into a valid URL on the server. A likely cause is:\nThe web application name in the Salmon properties does not match the web application name you are using (make sure it matches exactly including case).\nYou can change the setting in the Salmon Properties dialog.");
				} else if (e instanceof IOException) {
					displayError(
						"An error occured on the server generating the controller from the JSP:\n("
							+ e.toString()
							+ ").\nMake sure the JSP is valid and can be compiled into a servlet and executed.\nA few ways test if the page is valid are: Opening the page in Dreamweaver and running the URL in a browser.\nIn both cases look for errors in either Dreamweaver or the browser and on the server console in the IDE.");
				} else
					displayError(e);
			}
		} else if (_genVars) {
			setPropsPath();
			try {
				System.out.println("Generating Variables:" + _actualFileName);
				String webApp=_defaultWebApp;
				if (webApp!=null) {
					int pos=webApp.indexOf(File.separator);
					if (pos > -1)
						webApp=webApp.substring(0,pos);
					
				}	
				String url = "http://" + _defaultHost + "/" + webApp + "/" + "Translator" + "?HeaderLine=*" + _defaultFwApp + "*&InputFile=" + Util.urlEncode(_actualFileName);
				displayCutPasteDialog(genCommand(url, null, null));
			} catch (Exception e) {
				if (!_webPage.startsWith("http")) {
					displayError("Could not translate the filename for the JSP into a valid URL on the server. A likely cause is:\nThe web application name in the Salmon properties does not match the web application name you are using (make sure it matches exactly including case).\nYou can change the setting in the Salmon Properties dialog.");
				} else if (e instanceof IOException) {
					displayError(
						"An error occured on the server generating instance variables from the JSP:\n("
							+ e.toString()
							+ ").\nMake sure the JSP is valid and can be compiled into a servlet and executed.\nA few ways test if the page is valid are: Opening the page in Dreamweaver and running the URL in a browser.\nIn both cases look for errors in either Dreamweaver or the browser and on the server console in the IDE.");
				} else
					displayError(e);
			}
		} else if (_runBrowser) {
			System.out.println("Launching Browser:" + _browser);
			System.out.println(runExe(_browser, _webPage));
		} else if (_setProperties) {
			changeProperties(_setEclipseProperties);
		} else if (_genModel) {
			displayModelDialog();
		} else if (_genListForm) {
			setPropsPath();
			displayListFormDialog();
		} else if (_genProject) {
			createProject();
		} else if (_makeDefault) {
			makePageDefault();
		} else if (_siteMap) {
			displaySiteMapDialog();
		}

		System.exit(0);
	}

	public static void setPropsPath() {
		if (_useAppProperties)
			setPropsPath(_appPropsPath);
	}
	private static void setPropsPath(String path) {
		if (path == null)
			path = "";
		System.setProperty("salmon.props.path",path);
		Props.setPropsPath(path,false);
	}	
	/**
	 *  Parses the arguments passed to the main method.
	 *  Valid ARGS are
	 *      -RUNDREAMWEAVER - Run Dreamweaver and opens a file if specified
	 *      -REDEPLOY - Redeploys the default web application
	 *      -RUNBROWSER - Runs a browser. This should be followed by a browser name from the System.properties file (IE,Netscape4,etc). Used in conjunction with the -URL arg
	 *      -GENCONTROLLER - Generates a controller for a JSP, Used in conjunction with the -URL arg
	 *      -GENVARS - Generates the instance variables for a JSP, Used in conjunction with the -URL arg
	 *      -URL - Specify the URL or file for a browser to run or code to be generated from
	 *      -SETPROPS - Edit the property file settings for the IDE
	 *      -GENMODEL - Open a dialog that generates a datastore model
	 */
	protected static Props parseArgs(String args[]) throws Exception {

		Props p = Props.getSystemProps();
		_useAppProperties = p.getBooleanProperty(Props.IDE_USE_APP_PROPERTIES,false);
		_defaultWebApp = p.getProperty(Props.IDE_WEB_APP);
		_defaultFwApp = p.getProperty(Props.IDE_DEFAULT_FRAMEWORK_APP);
		_defaultHost = p.getProperty(Props.IDE_DEFAULT_HOST, "127.0.0.1:8080");
		_dreamWeaverPath = p.getProperty(Props.IDE_DREAMWEAVER_PATH);
		_defaultSourcePath = p.getProperty(Props.IDE_DEFAULT_SOURCE_PATH);
		_serverType = p.getProperty(Props.IDE_SERVER_TYPE);
		if (_serverType == null)
			_serverType = SERVER_TOMCAT40;
		else if (_serverType.equals(SERVER_WEBLOGIC6))
			_serverType = SERVER_WEBLOGIC6;
		else if (_serverType.equals(SERVER_TOMCAT41))
			_serverType = SERVER_TOMCAT41;
		else if (_serverType.equals(SERVER_TOMCAT50))
			_serverType = SERVER_TOMCAT50;
		else
			_serverType = SERVER_TOMCAT40;
		_webPage = p.getProperty(Props.IDE_DEFAULT_RUN_URL);

		//If there is a project, some of the args may change based on the project name
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-PROJECT")) {
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-")) {
						_projectName = args[next];

						String defaultWebApp = p.getProperty(_projectName + "." + Props.IDE_WEB_APP);
						if (defaultWebApp != null)
							_defaultWebApp = defaultWebApp;

						String defaultFwApp = p.getProperty(_projectName + "." + Props.IDE_DEFAULT_FRAMEWORK_APP);
						if (defaultFwApp != null)
							_defaultFwApp = defaultFwApp;

						String defaultSourcePath = p.getProperty(_projectName + "." + Props.IDE_DEFAULT_SOURCE_PATH);
						if (defaultSourcePath != null)
							_defaultSourcePath = defaultSourcePath;

						String webPage = p.getProperty(_projectName + "." + Props.IDE_DEFAULT_RUN_URL);
						if (webPage != null)
							_webPage = webPage;
					}
				}
			}
		}

		//compute the http version of the web page if it is a file system path
		if (_webPage != null && !_webPage.startsWith("http")) {
			String temp = getURLFromFile(_defaultHost, _webPage, _defaultWebApp, _defaultFwApp, null);
			if (temp != null)
				_webPage = temp;
		}

		//Go through the rest of the args
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-RUNDREAMWEAVER")) {
				_runDreamweaver = true;
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-"))
						_dreamWeaverFile = args[next];
					if (_dreamWeaverFile != null && !_dreamWeaverFile.endsWith(".jsp") && !_dreamWeaverFile.endsWith(".htm") && !_dreamWeaverFile.endsWith(".html") && !_dreamWeaverFile.endsWith(".xml") && !_dreamWeaverFile.endsWith(".tld")) {
						throw new Exception("A file must be a jsp, html, xml or tld file to edit with Dreamweaver. \n Please select a file of the correct type and try again.");
					}
				}
			} else if (args[i].equalsIgnoreCase("-GENCONTROLLER"))
				_genController = true;
			else if (args[i].equalsIgnoreCase("-GENVARS"))
				_genVars = true;
			else if (args[i].equalsIgnoreCase("-SITEMAP"))
				_siteMap = true;
			else if (args[i].equalsIgnoreCase("-GENMODEL")) {
				_genModel = true;
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-")) {
						_modelToGen = args[next];
					}
				}
			} else if (args[i].equalsIgnoreCase("-GENLISTFORM")) {
				_genListForm = true;
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-"))
						_xmlFile = args[next];
					if (_xmlFile != null && !_xmlFile.endsWith(".xml")) {
						throw new Exception("A file must be a .xml file to import into Generate Listform. \n Please select a file .xml type and try again.");
					}
				}
			} else if (args[i].equalsIgnoreCase("-SETPROPS"))
				_setProperties = true;
			else if (args[i].equalsIgnoreCase("-SETECLIPSEPROPS")) {
				_setProperties = true;
				_setEclipseProperties = true;
			} else if (args[i].equalsIgnoreCase("-GENPROJECT"))
				_genProject = true;
			else if (args[i].equalsIgnoreCase("-REDEPLOY"))
				_redeploy = true;
			else if (args[i].equalsIgnoreCase("-RESTART") || args[i].equalsIgnoreCase("-START"))
				_restart = true;
			else if (args[i].equalsIgnoreCase("-STOP"))
				_stop = true;
			else if (args[i].equalsIgnoreCase("-DONTAPPENDPROJECT"))
				_dontAppendProject = true;
			else if (args[i].equalsIgnoreCase("-MAKEDEFAULT"))
				_makeDefault = true;
			else if (args[i].equalsIgnoreCase("-OSLOOKANDFEEL")) {
				_defaultLF = true;
				setDefaultScrollBarColor(new ColorUIResource(0,0,0));
			} else if (args[i].equalsIgnoreCase("-RUNBROWSER")) {
				_runBrowser = true;
				_browser = p.getProperty(Props.IDE_BROWSER_PATH);
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-"))
						_browser = p.getProperty(Props.IDE_BROWSER_PATH + "." + args[next]);
				}
			} else if (args[i].equalsIgnoreCase("-URL")) {
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-")) {
						_webPage = args[next];
						if (!_webPage.startsWith("http")) {
							String temp = getURLFromFile(_defaultHost, _webPage, _defaultWebApp, _defaultFwApp, null);
							if (temp != null) {
								_actualFileName = _webPage;
								_webPage = temp;
							}
						}
					}
				}
			} else if (args[i].equalsIgnoreCase("-DOCUMENTROOT")) {
				if (i < (args.length - 1)) {
					int next = i + 1;
					if (!args[next].startsWith("-"))
						_documentRoot = args[next];
				}
			}
		}

		if (_runBrowser) {
			String page = _webPage;
			int pos = page.indexOf("?");
			if (pos != -1)
				page = page.substring(0, pos);
			pos = page.indexOf("#");
			if (pos != -1)
				page = page.substring(0, pos);

			//if (!page.endsWith(".html") && !page.endsWith(".htm") && !page.endsWith(".jsp")) {
			//	throw new Exception("A file must be a jsp or html file to run in a browser. \n Please select a file of the correct type and try again.");
			//}
		} else if (_genVars) {
			if (!_webPage.endsWith(".jsp"))
				throw new Exception("A file must be a jsp to generate instance variables for it. \n Please select a file of the correct type and try again.");
		} else if (_genController) {
			if (!_webPage.endsWith(".jsp"))
				throw new Exception("A file must be a jsp to generate a controller for it. \n Please select a file of the correct type and try again.");
		}
		
		if (_useAppProperties)
			_appPropsPath = p.getProperty(getProjectProperty(Props.IDE_APP_PROPS_PATH));
		return p;
	}

	/**
	 * Reads input file into a String
	 */
	public static String readFile(File inputFile) throws IOException {
		FileInputStream in = (new FileInputStream(inputFile));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int n;
		while ((n = in.read(buf)) >= 0)
			out.write(buf, 0, n);
		in.close();
		out.close();
		return new String(out.toByteArray());
	}

	/**
	 * Redeploy a weblogic application
	 */
	public static void redeployWeblogicApplication(String webLogicRoot, String webApp) throws Exception {
		if (webApp == null || webApp.length() == 0)
			webApp = "DefaultWebApp";
		File f = new File(webLogicRoot + "\\" + webApp + "\\META-INF\\redeploy");
		if (f.exists())
			f.setLastModified(System.currentTimeMillis());
	}

	private static String replace(String sString, String sOldString, String sNewString) {
		StringBuffer sbString = new StringBuffer(sString);
		while (true) {
			int iIndex = sbString.toString().indexOf(sOldString);
			if (iIndex < 0)
				break;
			sbString.replace(iIndex, iIndex + sOldString.length(), sNewString);
		}
		return sbString.toString();
	}

	/**
	 * Runs Dreamweaver, but first sets the path so everything works OK
	 */
	public static String runDreamweaver(String command, String parms) {
		return runExe(command, parms);

		//        This was added because without setting up the path properly, the
		//        Dreamweaver extnesion manager couldn't run. Adding this code causes Dreamweaver
		//        to not call the translator so it was removed
		//
		//            try {
		//                String runCom = command + (parms == null ? "" : (" " + parms));
		//                String environmentArgs[]=new String[1];
		//                Registry reg=new Registry(Registry.HKEY_LOCAL_MACHINE,"SOFTWARE\\Macromedia\\Common Files");
		//                String extensionsDll=reg.getStringAttribute("extensions.dll");
		//                extensionsDll=extensionsDll.substring(0,extensionsDll.length()-15);
		//                String path=command.substring(0,command.length()-15);
		//                environmentArgs[0] = "";
		//                environmentArgs[0]="PATH=%PATH%;"+path + ";" + extensionsDll + ";";
		//                Process process=Runtime.getRuntime().exec(runCom, environmentArgs);
		//
		//                slurpProcessOutput(process);
		//                return "OK";
		//			}
		//            catch (Exception e) {
		//                return runExe(command,parms);
		//            }
	}

	/**
	 * Runs a program
	 */
	public static String runExe(String command, String parms) {
		Runtime r = Runtime.getRuntime();

		try {
			Process p = r.exec(command + (parms == null ? "" : (" " + parms)));
			slurpProcessOutput(p);
		} catch (IOException e) {
			return e.toString();
		}

		return "OK";
	}

	/**
	 *  Displays a dialog and saves a generated controller
	 */
	public static boolean saveClass(String defaultFileName, String fileText) {
		boolean isValid = false;
		String fileName = defaultFileName;
		while (!isValid) {
			fileName = displaySaveDialog(fileName, fileText);
			if (fileName == null)
				return false;
			File f = new File(fileName);
			File parent = f.getParentFile();
			try {
				if (f.exists()) {
					if (!displayQuestion("The file " + f.getAbsolutePath() + " exists. Overwrite It?"))
						continue;
				}
				if (!parent.exists()) {
					if (!displayQuestion("The directory " + parent.getAbsolutePath() + " doesn't exist. Create It?"))
						continue;
					parent.mkdirs();
				}
				FileOutputStream out = new FileOutputStream(f);
				PrintWriter w = new PrintWriter(out);
				w.print(fileText);
				w.close();
				out.close();
			} catch (Exception e) {
				displayError(e.toString(), false);
				continue;
			}
			System.out.println("$genclass$" + fileName + "$genclassend$");
			isValid = true;
		}
		return true;
	}

	public static void slurpProcessOutput(Process p) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		if (br.ready()) {
			String line = br.readLine();
			while (line != null) {
				if (br.ready())
					line = br.readLine();
				else
					line = null;
			}
		}
		br.close();
	}

	/**
	 * Tomcat 4 command Controller
	 */
	public static String tomcat4Command(String host, String managerApp, String webapp, String command, String userID, String pw) throws Exception {
		URL u = new URL("http://" + host + "/" + managerApp + "/" + command + "?path=/" + webapp);
		URLConnection conn = u.openConnection();

		String authString = userID + ":" + pw;
		String auth = "Basic " + base64encode(authString);
		conn.setRequestProperty("Authorization", auth);

		InputStream in = conn.getInputStream();
		StringBuffer b = new StringBuffer();
		int i = in.read();
		while (i != -1) {
			b.append((char) i);
			i = in.read();
		}
		in.close();
		return b.toString();
	}

	private static String translateConstantToMethod(String constant, boolean changeCase) {
		StringBuffer sb = new StringBuffer(constant.length());
		boolean uCase = true;
		for (int i = 0; i < constant.length(); i++) {
			char c = constant.charAt(i);
			if (c == '_')
				uCase = true;
			else {
				if (uCase) {
					c = Character.toUpperCase(c);
					uCase = false;
				} else if (changeCase)
					c = Character.toLowerCase(c);
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static String translateDSType(int type) {
		if (type == DataStore.DATATYPE_BYTEARRAY)
			return "DataStore.DATATYPE_BYTEARRAY";
		else if (type == DataStore.DATATYPE_DATE)
			return "DataStore.DATATYPE_DATE";
		else if (type == DataStore.DATATYPE_DATETIME)
			return "DataStore.DATATYPE_DATETIME";
		else if (type == DataStore.DATATYPE_DOUBLE)
			return "DataStore.DATATYPE_DOUBLE";
		else if (type == DataStore.DATATYPE_FLOAT)
			return "DataStore.DATATYPE_FLOAT";
		else if (type == DataStore.DATATYPE_INT)
			return "DataStore.DATATYPE_INT";
		else if (type == DataStore.DATATYPE_LONG)
			return "DataStore.DATATYPE_LONG";
		else if (type == DataStore.DATATYPE_SHORT)
			return "DataStore.DATATYPE_SHORT";
		else if (type == DataStore.DATATYPE_STRING)
			return "DataStore.DATATYPE_STRING";
		else if (type == DataStore.DATATYPE_TIME)
			return "DataStore.DATATYPE_TIME";
		else
			return "";
	}

	private static String translateQBEType(int type) {
		if (type == QBEBuilder.CRITERIA_TYPE_COMPLEX)
			return "QBEBuilder.CRITERIA_TYPE_COMPLEX";
		else if (type == QBEBuilder.CRITERIA_TYPE_CONTAINS)
			return "QBEBuilder.CRITERIA_TYPE_CONTAINS";
		else if (type == QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE)
			return "QBEBuilder.CRITERIA_TYPE_CONTAINS_IGNORE_CASE";
		else if (type == QBEBuilder.CRITERIA_TYPE_EQUALS)
			return "QBEBuilder.CRITERIA_TYPE_EQUALS";
		else if (type == QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE)
			return "QBEBuilder.CRITERIA_TYPE_EQUALS_IGNORE_CASE";
		else if (type == QBEBuilder.CRITERIA_TYPE_GT)
			return "QBEBuilder.CRITERIA_TYPE_GT";
		else if (type == QBEBuilder.CRITERIA_TYPE_GTE)
			return "QBEBuilder.CRITERIA_TYPE_GTE";
		else if (type == QBEBuilder.CRITERIA_TYPE_LT)
			return "QBEBuilder.CRITERIA_TYPE_LT";
		else if (type == QBEBuilder.CRITERIA_TYPE_LTE)
			return "QBEBuilder.CRITERIA_TYPE_LTE";
		else if (type == QBEBuilder.CRITERIA_TYPE_NOT_EQUALS)
			return "QBEBuilder.CRITERIA_TYPE_NOT_EQUALS";
		else if (type == QBEBuilder.CRITERIA_TYPE_STARTS_WITH)
			return "QBEBuilder.CRITERIA_TYPE_STARTS_WITH";
		else if (type == QBEBuilder.CRITERIA_TYPE_STARTS_WITH_IGNORE_CASE)
			return "QBEBuilder.CRITERIA_TYPE_STARTS_WITH_WITH_IGNORE_CASE";
		else if (type == QBEBuilder.CRITERIA_TYPE_IN)
			return "QBEBuilder.CRITERIA_TYPE_IN";
		else if (type == QBEBuilder.CRITERIA_TYPE_CUSTOM)
			return "QBEBuilder.CRITERIA_TYPE_CUSTOM";
		else
			return "";
	}
	private static String translateDSTypeToJavaType(int type) {
		if (type == DataStore.DATATYPE_BYTEARRAY)
			return "byte[]";
		else if (type == DataStore.DATATYPE_DATE)
			return "java.sql.Date";
		else if (type == DataStore.DATATYPE_DATETIME)
			return "java.sql.Timestamp";
		else if (type == DataStore.DATATYPE_DOUBLE)
			return "double";
		else if (type == DataStore.DATATYPE_FLOAT)
			return "float";
		else if (type == DataStore.DATATYPE_INT)
			return "int";
		else if (type == DataStore.DATATYPE_LONG)
			return "long";
		else if (type == DataStore.DATATYPE_SHORT)
			return "short";
		else if (type == DataStore.DATATYPE_STRING)
			return "String";
		else if (type == DataStore.DATATYPE_TIME)
			return "java.sql.Time";
		else
			return "";
	}

	private static Class translateDSTypeToJavaClass(int type) {
		if (type == DataStore.DATATYPE_BYTEARRAY)
			return byte[].class;
		else if (type == DataStore.DATATYPE_DATE)
			return java.sql.Date.class;
		else if (type == DataStore.DATATYPE_DATETIME)
			return java.sql.Timestamp.class;
		else if (type == DataStore.DATATYPE_DOUBLE)
			return Double.TYPE;
		else if (type == DataStore.DATATYPE_FLOAT)
			return Float.TYPE;
		else if (type == DataStore.DATATYPE_INT)
			return Integer.TYPE;
		else if (type == DataStore.DATATYPE_LONG)
			return Long.TYPE;
		else if (type == DataStore.DATATYPE_SHORT)
			return Short.TYPE;
		else if (type == DataStore.DATATYPE_STRING)
			return String.class;
		else if (type == DataStore.DATATYPE_TIME)
			return java.sql.Time.class;
		else
			return null;
	}
	private static String translateDSTypeToMethod(int type) {
		if (type == DataStore.DATATYPE_BYTEARRAY)
			return "ByteArray";
		else if (type == DataStore.DATATYPE_DATE)
			return "Date";
		else if (type == DataStore.DATATYPE_DATETIME)
			return "DateTime";
		else if (type == DataStore.DATATYPE_DOUBLE)
			return "Double";
		else if (type == DataStore.DATATYPE_FLOAT)
			return "Float";
		else if (type == DataStore.DATATYPE_INT)
			return "Int";
		else if (type == DataStore.DATATYPE_LONG)
			return "Long";
		else if (type == DataStore.DATATYPE_SHORT)
			return "Short";
		else if (type == DataStore.DATATYPE_STRING)
			return "String";
		else if (type == DataStore.DATATYPE_TIME)
			return "Time";
		else
			return "";
	}

	private static String mergeModels(String newText, String origFile) {
		try {
			if (!new File(origFile).exists())
				return newText;
			BufferedReader in = new BufferedReader(new FileReader(origFile));
			StringBuffer sb = new StringBuffer();
			String line = in.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\r\n");
				line = in.readLine();
			}
			in.close();
			String oldText = sb.toString();

			//Find the custom code in the existing model
			String customVars = "\r\n";
			String customCode = "\r\n";
			String customImports = "\r\n";
			String customConstructor = "\r\n";

			int st = oldText.indexOf("//$CUSTOMIMPORTS$");
			int end = oldText.indexOf("//$ENDCUSTOMIMPORTS$");
			if (st > -1 && end > -1)
				customImports = oldText.substring(st + 19, end);

			st = oldText.indexOf("//$CUSTOMVARS$");
			end = oldText.indexOf("//$ENDCUSTOMVARS$");
			if (st > -1 && end > -1)
				customVars = oldText.substring(st + 16, end);

			st = oldText.indexOf("//$CUSTOMCONSTRUCTOR$");
			end = oldText.indexOf("//$ENDCUSTOMCONSTRUCTOR$");
			if (st > -1 && end > -1)
				customConstructor = oldText.substring(st + 23, end);

			st = oldText.indexOf("//$CUSTOMMETHODS$");
			end = oldText.indexOf("//$ENDCUSTOMMETHODS$");
			if (st > -1 && end > -1)
				customCode = oldText.substring(st + 19, end);

			//update the custom code in the new Text
			StringBuffer work = new StringBuffer(newText);

			st = newText.indexOf("//$CUSTOMIMPORTS$");
			end = newText.indexOf("//$ENDCUSTOMIMPORTS$");
			work.replace(st + 19, end, customImports);
			newText = work.toString();

			st = newText.indexOf("//$CUSTOMVARS$");
			end = newText.indexOf("//$ENDCUSTOMVARS$");
			work.replace(st + 16, end, customVars);
			newText = work.toString();

			st = newText.indexOf("//$CUSTOMCONSTRUCTOR$");
			end = newText.indexOf("//$ENDCUSTOMCONSTRUCTOR$");
			work.replace(st + 23, end, customConstructor);
			newText = work.toString();

			st = newText.indexOf("//$CUSTOMMETHODS$");
			end = newText.indexOf("//$ENDCUSTOMMETHODS$");
			work.replace(st + 19, end, customCode);

			return work.toString();
		} catch (Exception e) {
			return newText;
		}

	}

	/**
	 * Returns true if the Operating System default look and feel is being used
	 */
	public static boolean useDefaultOSLookAndFeel() {
		return _defaultLF;
	}
	/**
	 * @return the document root for the web application
	 */
	public static String getDocumentRoot() {
		return _documentRoot;
	}

	/**
	 * @return the default framework application
	 */
	public static String getDefaultFwApp() {
		return _defaultFwApp;
	}

	/**
	 * @return the default source path
	 */
	public static String getDefaultSourcePath() {
		return _defaultSourcePath;
	}

	/**
	 * @return the dont append project flag
	 */
	public static boolean getDontAppendProject() {
		return _dontAppendProject;
	}

	public static void setDefaultScrollBarColor(ColorUIResource dEFAULT_SCROLL_BAR_COLOR) {
		DEFAULT_SCROLL_BAR_COLOR = dEFAULT_SCROLL_BAR_COLOR;
	}

	public static ColorUIResource getDefaultScrollBarColor() {
		if (DEFAULT_SCROLL_BAR_COLOR == null) {
			DEFAULT_SCROLL_BAR_COLOR = new ColorUIResource(91, 135, 206);
		}
		if (DEFAULT_SCROLL_BAR_COLOR.getRed() == 0 && DEFAULT_SCROLL_BAR_COLOR.getBlue() == 0 && DEFAULT_SCROLL_BAR_COLOR.getGreen() == 0)
			return null;
		else	
			return DEFAULT_SCROLL_BAR_COLOR;
	}

	public static Font getDefaultFont() {
		if (DEFAULT_FONT == null) {
			 DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);
		}
		return DEFAULT_FONT;
	}

}
