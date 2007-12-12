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
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/Scheduler.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 11/09/04 9:38a $
/////////////////////////

import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.scheduler.*;
import com.salmonllc.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * This servlet is the controller for all the scheduled object in the framework. <BR> Each scheduled object must implement the com.salmonllc.ScheduledObject interface and must be listed in the properties file. When the scheule interval is reached a new instance of the object will be created and the schedule reached event will be executed.
 */

public class Scheduler extends HttpServlet implements Runnable {
	private Vector _list = new Vector();
	private long _lastReload = -1;
	private Thread _exeLoop = null;
	private boolean _threadRunning = false;
	private boolean _loaded = false;

	//every half hour go through the open database connections and remove any
	// that are idle
	FourObjectContainer _poolCleaner = new FourObjectContainer(new ConnectionPoolCleaner(), new Integer(30), null, new Long(-1));

	//every half hour go through the objectstore cache and remove old ones
	FourObjectContainer _cacheCleaner = new FourObjectContainer(new CacheCleaner(), new Integer(30), null, new Long(-1));

	/**
	 * This method handles get events from the browser.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		JspServlet.setUpApplicationContext(getServletContext(), req);
		res.setContentType("text/html");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-cache");
		res.setStatus(HttpServletResponse.SC_OK);

		String[] path = req.getParameterValues("ret");
		if (path == null)
			generateHtml(res.getWriter());
		else {
			String retPath = path[0];
			if (retPath == null)
				retPath = "";
			else if (retPath.equals("null"))
				retPath = "";
			res.sendRedirect(URLGenerator.generateServerURL(req) + "/" + URLGenerator.generateServletBaseURL(req) + "/com.salmonllc.servlets.AppServer" + retPath);
		}

	}

	/**
	 * This method handles post events from the browser.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		JspServlet.setUpApplicationContext(getServletContext(), req);
		res.setContentType("text/html");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setHeader("Cache-Control", "no-cache");

		res.setStatus(HttpServletResponse.SC_OK);

		String parm[] = req.getParameterValues("START");
		if (parm != null)
			startThread();
		else {
			parm = req.getParameterValues("STOP");
			if (parm != null)
				stopThread();
			else {
				parm = req.getParameterValues("RELOAD");
				reloadInfo(true);
			}
		}

		generateHtml(res.getWriter());
	}

	private void executeEvents() {
		for (int i = 0; i < _list.size(); i++) {
			FourObjectContainer evt = (FourObjectContainer) _list.elementAt(i);
			if (evt != null) {
				Long l = (Long) evt.getObject4();
				boolean execute = false;
				if (l.longValue() == -1)
					execute = true;
				else {
					Integer interval = (Integer) evt.getObject2();
					long nextExeTime = l.longValue() + (interval.intValue() * 60000);
					long time = System.currentTimeMillis();
					if (time > nextExeTime) {
						execute = true;
						evt.setObject4(new Long(time));
					}
				}

				if (!execute)
					continue;

				Object obj = evt.getObject1();

				String app = "";
				if (ApplicationContext.getContext() != null)
					app = " application:" + ApplicationContext.getContext().getAppID();

				try {
					evt.setObject4(new Long(System.currentTimeMillis()));
					if (obj instanceof String) {
						Class c = Class.forName((String) obj, true, Thread.currentThread().getContextClassLoader());
						Object o = c.newInstance();
						ScheduledObject so = (ScheduledObject) o;
						MessageLog.writeInfoMessage("scheduleReached() for: " + so.toString() + app, this);
						so.scheduleReached(new ScheduleReachedEvent((String) evt.getObject3()));
					} else if (obj instanceof ScheduledObject) {
						ScheduledObject so = (ScheduledObject) obj;
						MessageLog.writeInfoMessage("scheduleReached() for: " + so.toString() + app, this);
						so.scheduleReached(new ScheduleReachedEvent((String) evt.getObject3()));
					}
				} catch (Exception e) {
					MessageLog.writeErrorMessage("executeEvent(), creating class", e, this);
				}

			}

		}

	}

	private void generateHtml(PrintWriter p) {
		Props pr = Props.getSystemProps();
		if (!pr.getBooleanProperty(Props.SYS_INFO_SERVLETS, true))
			return;

		p.println("<HTML><HEAD></HEAD>");

		String bodyTag = "<BODY";
		if (pr.getProperty(Props.PAGE_BACKGROUND_COLOR) != null)
			bodyTag += " BGCOLOR=\"" + pr.getProperty(Props.PAGE_BACKGROUND_COLOR) + "\"";

		if (pr.getProperty(Props.PAGE_BACKGROUND) != null) {
			String bg = pr.getProperty(Props.PAGE_BACKGROUND);
			if (bg.startsWith("../../"))
				bg = bg.substring(6);
			bodyTag += " BACKGROUND=\"" + bg + "\"";
		}
		bodyTag += ">";
		p.println(bodyTag);

		String headingFontStart = pr.getProperty(Props.FONT_PAGE_HEADING + Props.TAG_START);
		String headingFontEnd = pr.getProperty(Props.FONT_PAGE_HEADING + Props.TAG_END);

		String defaultFontStart = pr.getProperty(Props.FONT_DEFAULT + Props.TAG_START);
		String defaultFontEnd = pr.getProperty(Props.FONT_DEFAULT + Props.TAG_END);

		p.println(headingFontStart + "com.salmonllc.servlets.Scheduler" + headingFontEnd + "<BR>");

		p.println("<BR>");
		p.println(defaultFontStart + "Status: " + (_threadRunning ? "Running" : "Stopped") + defaultFontEnd + "<BR>");
		p.println(defaultFontStart + "Objects Scheduled: " + _list.size() + "<BR>");
		for (int i = 0; i < _list.size(); i++) {
			p.print("&nbsp;&nbsp;&nbsp;");
			p.print(((FourObjectContainer) _list.elementAt(i)).getObject1());
			p.print("<BR>");

		}
		p.println(defaultFontEnd);
		p.println("<BR>");

		p.println("<FORM NAME=\"PageForm\" METHOD=\"POST\">");
		if (_threadRunning)
			p.println("<INPUT TYPE=\"SUBMIT\" NAME=\"STOP\" VALUE=\"Stop the com.salmonllc.servlets.Scheduler\">");
		else
			p.println("<INPUT TYPE=\"SUBMIT\" NAME=\"START\" VALUE=\"Start the com.salmonllc.servlets.Scheduler\">");

		p.write("<INPUT TYPE=\"SUBMIT\" NAME=\"RELOAD\" VALUE=\"Reload Information\">");
		p.write("</FORM>");

		p.write("</BODY></HTML>");
	}

	/**
	 * This method initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext cont = config.getServletContext();
		JspServlet.setUpApplicationContext(cont);
		if (Props.isPropsPathShared() && _loaded)
			return;

		reloadInfo(true);
		if (_list.size() > 0)
			startThread();
	}

	/**
	 * This method is called when the servlet is destoryed
	 */

	public void destroy() {
		stopThread();
		MessageLog.stopThreads();
		super.destroy();
	}

	private void reloadInfo(boolean force) {
		long time = Props.getSystemPropsLastModified();

		if (!force) {
			if (time == _lastReload)
				return;
		}

		_loaded = true;
		_lastReload = time;
		_list.removeAllElements();

		Props p = Props.getSystemProps();
		if (p == null) {
			_lastReload = -1;
			return;
		}

		int i = 1;
		int maxScheduledObjects = p.getIntProperty(Props.SYS_SCHEDULER_MAX_OBJECTS);

		if (maxScheduledObjects <= 0) {
			maxScheduledObjects = 20;
		}

		while (i < maxScheduledObjects) {
			String object = p.getProperty(Props.SYS_SCHEDULE_OBJECT + "." + i);
			if (object == null) {
				i++;
				continue;
			}

			int interval = p.getIntProperty(Props.SYS_SCHEDULE_INTERVAL + "." + i);
			String application = p.getProperty(Props.SYS_SCHEDULE_APPLICATION + "." + i);

			FourObjectContainer f = new FourObjectContainer(object, new Integer(interval), application, new Long(-1));
			_list.addElement(f);

			i++;
		}

		_list.addElement(_poolCleaner);
		_list.addElement(_cacheCleaner);

	}

	public void run() {
		while (_threadRunning) {
			reloadInfo(false);
			if (_threadRunning)
				executeEvents();

			try {
				Thread.sleep(10000);
			} catch (Exception e) {
			}
		}
	}

	private void startThread() {
		if (_threadRunning)
			return;

		if (_list.size() == 0)
			return;

		_threadRunning = true;
		_exeLoop = ApplicationContext.createThreadWithContextClone(this);
		_exeLoop.start();
	}

	private void stopThread() {
		if (!_threadRunning)
			return;

		_threadRunning = false;
	}
}