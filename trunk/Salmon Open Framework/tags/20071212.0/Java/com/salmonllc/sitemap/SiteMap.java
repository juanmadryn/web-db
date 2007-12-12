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
package com.salmonllc.sitemap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.salmonllc.html.HttpServletRequestWrapper;
import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.URLGenerator;

/**
 * SOFIA site map implementation
 *
 */
public class SiteMap {
	private Hashtable _nameMap = new Hashtable();
	private Hashtable _actionMap = new Hashtable();
	private Hashtable _uriMap = new Hashtable();
	private Vector _list = new Vector();
	private String _appName;
	private class SiteMapEntry {
		String name;
		String uri;
		String context;
		boolean secure;
		boolean useForward;
		String popupFeatures;
	}

	/**
	 * Creates an empty SiteMap Object pointing to a particular application
	 */
	public SiteMap(String applicationName) {
		_appName = applicationName;
	}

	/**
	 * Returns the SiteMap object associated with a particular application
	 */
	public static SiteMap getSiteMap(String applicationName) {
		try {
			return SiteMapParser.getSiteMap(applicationName);
		} catch (IOException e) {
			MessageLog.writeErrorMessage("SiteMap.getSiteMap()", e, null);
			return null;
		}
	}

	/**
	 * Returns the url for a specified logical site map entry
	 * @param req The HttpServletRequest used for this lookup
	 * @param logicalName The name of the entry in the site map
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(HttpServletRequest req, String logicalName) {
		return getSiteMapURL(req, logicalName, null, true);
	}

	/**
	 * Returns the url for a specified logical site map entry
	 * @param req The HttpServletRequest used for this lookup
	 * @param logicalName The name of the entry in the site map
	 * @param additionalParms Any additional parameters that need to be appended to the url
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(HttpServletRequest req, String logicalName, String additionalParms) {
		return getSiteMapURL(req, logicalName, additionalParms, true);
	}

	/**
	 * Returns the url for a specified logical site map entry
	 * @param req The HttpServletRequest used for this lookup
	 * @param logicalName The name of the entry in the site map
	 * @param additionalParms Any additional parameters that need to be appended to the url
	 * @param javaScriptOK True to allow JavaScript in the URL (for popup windows)
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(HttpServletRequest req, String logicalName, String additionalParms, boolean javaScriptOK) {
		return getSiteMapURL(req,logicalName, additionalParms,javaScriptOK,false);	
	}	

	/**
	 * Returns the url for a specified logical site map entry
	 * @param req The HttpServletRequest used for this lookup
	 * @param logicalName The name of the entry in the site map
	 * @param additionalParms Any additional parameters that need to be appended to the url
	 * @param javaScriptOK True to allow JavaScript in the URL (for popup windows)
	 * @param doForwardOK True or false, the url being generated is in a contect where a forward can be used 
	 * @return The absolute url to the page
	 */
	public String getSiteMapURL(HttpServletRequest req, String logicalName, String additionalParms, boolean javaScriptOK, boolean doForwardOK) {

		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return null;
		String url = "";

		if (entry.uri.startsWith("http") || entry.uri.startsWith("mailto:"))
			url = entry.uri;
		else {
			if (! entry.useForward || ! doForwardOK) { 
				if (entry.secure)
					url = URLGenerator.generateSecureServerURL(req);
				else
					url = URLGenerator.generateServerURL(req);
			}		
			if (getContext(logicalName) != null) {
				url += "/" + getContext(logicalName);
			} else {
				if (req instanceof HttpServletRequestWrapper && ((HttpServletRequestWrapper) req).getPortletWrapppedRequest() != null) 
					url += "/" +  _appName;
				else	
					url += getApplicationNameFromURL(_appName, req);
			}

			if (!entry.uri.startsWith("/"))
				url = url + "/";

			url += entry.uri;
		}

		if (additionalParms != null) {
			String baseURL = parseURL(url);
			String[] baseParms = parseParms(url);
			String baseHashParms = baseParms[1];
			String baseQuestParms = baseParms[0];

			String addURL = parseURL(additionalParms);
			String addParms[] = parseParms(additionalParms);
			String addHashParms = addParms[1];
			String addQuestParms = addParms[0];

			url = baseURL;
			if (addURL != null && addURL.length() > 0) {
				if (baseURL.endsWith("/") && addURL.startsWith("/"))
					url += addURL.substring(1);
				else
					url += addURL;
			}

			if (baseQuestParms != null || addQuestParms != null) {
				url += "?";
				if (baseQuestParms != null && !baseQuestParms.equals("")) {
					url += baseQuestParms;
					if (addQuestParms != null && !addQuestParms.equals(""))
						url += "&" + addQuestParms;
				} else
					url += addQuestParms;
			}

			if (baseHashParms != null)
				url += "#" + baseHashParms;
			else if (addHashParms != null)
				url += "#" + addHashParms;
		}

		if (entry.popupFeatures != null && javaScriptOK) {
			if (entry.popupFeatures.equals("default"))
				url = "javascript:void window.open('" + url + "');";
			else
				url = "javascript:void window.open('" + url + "','" + logicalName + "','" + entry.popupFeatures + "');";
		}
		return url;
	}

	private String[] parseParms(String url) {
		String st[] = new String[2];
		int posQuest = url.indexOf("?");
		int posHash = url.indexOf("#");
		if (posQuest == -1 && posHash == -1)
			return st;
		else if (posQuest > -1 && posHash == -1)
			st[0] = url.substring(posQuest + 1);
		else if (posQuest == -1 && posHash > -1)
			st[1] = url.substring(posHash + 1);
		else {
			if (posQuest < posHash) {
				st[0] = url.substring(posQuest + 1, posHash);
				st[1] = url.substring(posHash + 1);
			} else {
				st[1] = url.substring(posHash + 1, posQuest);
				st[0] = url.substring(posQuest + 1);
			}
		}
		return st;
	}

	/**
	 * Adds popup window javascript to a url entry if it indicates so in the site map
	 * @return
	 */
	public String addJavaScriptToUrl(String logicalName, String url) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return url;
		if (entry.popupFeatures != null) {
			if (entry.popupFeatures.equals("default"))
				url = "javascript:void window.open('" + url + "','" + logicalName + "');";
			else
				url = "javascript:void window.open('" + url + "','" + logicalName + "','" + entry.popupFeatures + "');";
		}
		return url;
	}
	private String getApplicationNameFromURL(String appName, HttpServletRequest req) {
		String servletPath = req.getRequestURI();
		String servletName = req.getServletPath();
		if (servletName != null) {
			int pos = servletName.lastIndexOf("/");
			if (pos != -1)
				servletName = servletName.substring(pos);
		} else
			servletName = "";

		int pos = -1;
		if (servletPath != null)
			pos = servletPath.indexOf(servletName);
		if (pos < 1)
			servletPath = "";
		else
			servletPath = servletPath.substring(1, pos);
		if (servletPath != null) {
			pos = servletPath.indexOf("/");
			if (pos > -1)
				servletPath = servletPath.substring(0, pos);
		}

		if (servletPath == null)
			return "";
		else if (servletPath.length() > 0) {
			if (servletPath.equals(appName))
				return "/" + servletPath;
			else
				return "";
		} else
			return servletPath;
	}

	/**
	 * Return true if the entry uses https
	 */
	public boolean isSecure(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return false;
		else
			return entry.secure;
	}

	/**
	 * Returns true if the entry uses a forward instead of a redirect
	 */
	public boolean useForward(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return false;
		else
			return entry.useForward;
	}

	/**
	 * Returns true if the entry uses javascript to open a window as a popup
	 */
	public boolean useJavascript(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return false;
		else
			return entry.popupFeatures != null;
	}
	/**
	 * Returns the URI relative to the web application root or null if there is no entry
	 */
	public String getURI(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return null;
		else
			return entry.uri;
	}
	/**
	     * Returns the Context relative to the web application root or null if there is no entry
	     */
	public String getContext(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return null;
		else
			return entry.context;
	}
	/**
	 * Returns the Popup window features for the specified entry
	 */
	public String getPopupFeatures(String logicalName) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry == null)
			return null;
		else
			return entry.popupFeatures;
	}

	/**
	 * Sets the URI relative to the web application for the entry
	 */
	public void setURI(String logicalName, String URI) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry != null)
			entry.uri = URI;
	}

	/**
	  * Sets the context relative to the web application for the entry
	  */
	public void setContext(String logicalName, String context) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry != null)
			entry.context = context;
	}

	/**
	 * Sets the Popup Features for the page or null to open in the same window
	 */
	public void setPopupFeatures(String logicalName, String popupFeatures) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry != null)
			entry.popupFeatures = popupFeatures;
	}
	/**
	 * Sets whether the URI entry is secure
	 */
	public void setSecure(String logicalName, boolean secure) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry != null)
			entry.secure = secure;
	}

	/**
	 * Puts an entry into the site map. If the entry already exists it will be overwritten
	 * @param logicalName The logical name of the site map entry
	 * @param uri The URI of the page to go to when the entry is invoked
	 * @param useForward true if the site map entry should use a forward instead of a redirect
	 * @param secure true if the site map entry should jump to a secure url (https)
	 */
	public synchronized void putEntry(String logicalName, String uri, String popupFeatures, boolean useForward, boolean secure, String context) {
		SiteMapEntry entry = new SiteMapEntry();
		entry.name = logicalName;
		if (!uri.startsWith("/") && !uri.startsWith("http") && !uri.startsWith("mailto:"))
			uri = "/" + uri;
		int pos1 = uri.indexOf("?");
		int pos2 = uri.indexOf("#");
		int pos = -1;
		if (pos1 != -1 && pos2 != -1)
			pos = pos1 < pos2 ? pos1 : pos2;
		else if (pos1 != -1)
			pos = pos1;
		else if (pos2 != -1)
			pos = pos2;
		String uriEntry = uri;
		if (pos > -1)
			uriEntry = uri.substring(0, pos);

		entry.uri = uri;
		entry.secure = secure;
		entry.useForward = useForward;
		entry.popupFeatures = popupFeatures;
		entry.context = context;
		if (!_nameMap.containsKey(logicalName))
			_list.addElement(logicalName);
		_nameMap.put(logicalName, entry);
		_uriMap.put(uriEntry, entry);
	}

	/**
	 * Removes an entry from the list
	 * @param logicalName
	 */
	public synchronized void removeEntry(String logicalName) {
		for (int i = 0; i < _list.size(); i++) {
			if (((String) _list.elementAt(i)).equals(logicalName)) {
				_list.remove(i);
				SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
				_nameMap.remove(entry.name);
				_uriMap.remove(entry.uri);
			}
		}
	}

	/**
	 * Sets whether the URI entry uses a forward
	 */
	public void setUseForward(String logicalName, boolean useForward) {
		SiteMapEntry entry = (SiteMapEntry) _nameMap.get(logicalName);
		if (entry != null)
			entry.useForward = useForward;
	}

	/**
	 * Returns the logical name for the site map entry given the URI of a particulat page or null if that URI isn't found in the site map
	 * @param URI The URI for the page not including the servername and web application name
	 */
	public String getEntryName(String URI) {
		if (URI == null)
			return null;
		if (!URI.startsWith("/"))
			URI = "/" + URI;
		SiteMapEntry entry = (SiteMapEntry) _uriMap.get(URI);
		if (entry != null)
			return entry.name;
		else
			return null;
	}

	/**
	 * Returns a list of entry names
	 */
	public Enumeration getEntryNames() {
		return _list.elements();
	}

	/**
	 * Writes out the sitemap object to an xml file in the properties path
	 */
	public synchronized void update() throws IOException {
		String fileName = Props.getPropsPath();
		if (!fileName.endsWith(File.separator))
			fileName += File.separator;
		String test=fileName+"System.map";
		if (new File(test).exists())
			fileName=test;
		else
			fileName += _appName + ".map";
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		pw.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		pw.println("<!DOCTYPE sitemap PUBLIC \"SOFIA Site Map\" \"http://www.salmonllc.com/sofiaExamples/sitemap.dtd\">");
		pw.println("<siteMap>");
		Enumeration enumera = getEntryNames();
		while (enumera.hasMoreElements()) {
			String key = (String) enumera.nextElement();
			SiteMapEntry entry = (SiteMapEntry) _nameMap.get(key);
			pw.println("  <entry>");

			pw.print("     <name>");
			pw.print(entry.name);
			pw.println("</name>");

			pw.print("     <uri>");
			pw.print(entry.uri);
			pw.println("</uri>");

			pw.print("     <useForward>");
			pw.print(entry.useForward ? "true" : "false");
			pw.println("</useForward>");

			pw.print("     <secure>");
			pw.print(entry.secure ? "true" : "false");
			pw.println("</secure>");

			if (entry.popupFeatures != null) {
				pw.print("     <popupFeatures>");
				pw.print(entry.popupFeatures);
				pw.println("</popupFeatures>");
			}

			String[] actions = getActionEntries(key);
			for (int i = 0; i < actions.length; i++) {
				pw.println("     <action>");
				pw.print("        <actionName>");
				pw.print(actions[i]);
				pw.println("</actionName>");
				pw.print("        <actionEntry>");
				pw.print(getActionEntry(entry.name, actions[i]));
				pw.println("</actionEntry>");
				pw.println("     </action>");
			}
			if (entry.context != null) {
				pw.print("     <context>");
				pw.print(entry.context);
				pw.println("</context>");
			}
			pw.println("  </entry>");
			pw.println();
		}
		pw.println("</siteMap>");
		pw.close();

	}

	/**
	 * Adds an action to a site map entry
	 * @param entryName The name of the entry to add the action for
	 * @param actionName The identifier for the action
	 * @param actionEntry Another site map entry that this action will invoke
	 */
	public void addActionEntry(String entryName, String actionName, String actionEntry) {
		String key = entryName + "-+-" + actionName;
		_actionMap.put(key, actionEntry);
		String allEntries = (String) _actionMap.get(entryName);
		if (allEntries == null)
			allEntries = "";
		String lookup = actionName + "|";
		if (allEntries.indexOf(lookup) == -1) {
			allEntries += lookup;
			_actionMap.put(entryName, allEntries);
		}
	}

	/**
	 * Returns a string array of all the action names for an entry. 
	 */
	public String[] getActionEntries(String entryName) {
		String val = (String) _actionMap.get(entryName);
		if (val == null || val.length() == 0)
			return new String[0];

		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(val, "|", false);
		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			if (tok.length() > 0)
				v.add(tok);
		}
		String ret[] = new String[v.size()];
		v.copyInto(ret);
		return ret;
	}

	/**
	 * Returns the site map entry associated with a particular action
	 * @param entryName The name of the site map entry
	 * @param actionName The name of the action for that particular entry
	 * @return the name of the entry or null if not found
	 */
	public String getActionEntry(String entryName, String actionName) {
		String key = entryName + "-+-" + actionName;
		return (String) _actionMap.get(key);
	}

	/**
	 * Generates a Java source code final class with constants for each sitemap entry
	 * @return
	 */
	/**
	 * Generates a Java source code final class with constants for each sitemap entry
	 * @return
	 */
	public String generateConstants(String className) {
		StringBuffer sb = new StringBuffer();
		String packageNm = "";
		String actualClassName = "";
		int pos = className.lastIndexOf(".");
		if (pos == -1)
			actualClassName = className;
		else {
			packageNm = "package " + className.substring(0, pos) + ";\r\n\r\n";
			actualClassName = className.substring(pos + 1);
		}

		sb.append(packageNm);
		sb.append("public final class ");
		sb.append(actualClassName);
		sb.append(" {\r\n\r\n");
		for (int i = 0; i < _list.size(); i++) {
			String name = (String) _list.elementAt(i);
			sb.append("   public static final String ");

			char lastChar = '_';
			for (int j = 0; j < name.length(); j++) {
				char c = name.charAt(j);
				if (Character.isLetterOrDigit(c)) {
					if (Character.isUpperCase(c)) {
						if (Character.isLetterOrDigit(lastChar) && Character.isLowerCase(lastChar)) {
							lastChar = '_';
							sb.append('_');
						}
					}
					sb.append(Character.toUpperCase(c));
					lastChar = c;
				} else {
					sb.append('_');
					lastChar = '_';
				}
			}

			sb.append(" = \"");
			sb.append(name);
			sb.append("\";\r\n");

			String st[] = getActionEntries(name);

			for (int j = 0; j < st.length; j++) {
				sb.append("   public static final String ");
				for (int k = 0; k < name.length(); k++) {
					char c = name.charAt(k);
					if (Character.isLetterOrDigit(c))
						sb.append(Character.toUpperCase(c));
					else
						sb.append("_");
				}
				sb.append("_");
				for (int k = 0; k < st[j].length(); k++) {
					char c = st[j].charAt(k);
					if (Character.isLetterOrDigit(c))
						sb.append(Character.toUpperCase(c));
					else
						sb.append("_");
				}
				sb.append(" = \"");
				sb.append(st[j]);
				sb.append("\";\r\n");
			}
		}
		sb.append("\r\n}");
		return sb.toString();

	}

	private String parseURL(String url) {
		int pos1 = url.indexOf("?");
		int pos2 = url.indexOf("#");
		if (pos1 != -1 || pos2 != -1) {
			int pos = pos1;
			if (pos1 == -1)
				pos = pos2;
			else if (pos2 == -1)
				pos = pos1;
			else if (pos2 > pos1)
				pos = pos1;
			else if (pos2 < pos1)
				pos = pos2;
			return url.substring(0, pos);
		} else
			return url;
	}

}
