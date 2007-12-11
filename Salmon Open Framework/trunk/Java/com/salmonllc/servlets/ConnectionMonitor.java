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
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/ConnectionMonitor.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 8/24/04 4:25p $
/////////////////////////

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DBConnectionList;
import com.salmonllc.util.VectorSort;

/**
 * This servlet provides useful information on connections in the connection pool and other server statistics<BR>
 * It should be secured with a password on a production server to prevent unauthorized use.
 */
public class ConnectionMonitor extends HttpServlet {
    StringBuffer _sbFreeMemory = new StringBuffer();

    int _iMemoryPrintCntr = 0;
    int _iListCntr = 0;
    String _sAppName;
    boolean _showTimers = true;
    boolean _showConnections = true;
    boolean _autoRefresh=true;

    public class TimerList extends VectorSort {
        public boolean compare(Object o1, Object o2) {
            int val = (((Timer) o1).name.compareToIgnoreCase(((Timer) o2).name));
            return (val < 0);
        }

        public void populate(Hashtable t) {
            Enumeration e = t.keys();
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                Timer tm = new Timer();
                JspServlet.Timer time = (JspServlet.Timer) t.get(name);
                tm.count=time.count;
                tm.totalTime=time.totalTime;
                tm.name=name;
                addElement(tm);
            }
        }

    }
    public class Timer {
        String name;
        int count;
        long totalTime;
    }

    public ConnectionMonitor() {
        super();
    }

    /**
     * This method handles get events from the browser.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	Props pr = Props.getSystemProps();
        if (!pr.getBooleanProperty(Props.SYS_INFO_SERVLETS, true))
            return;

        PrintWriter p = res.getWriter();

        String sValues[] = req.getParameterValues("REFRESH");
        String sRefresh = "5";
        if (sValues != null) {
            sRefresh = req.getParameterValues("REFRESH")[0];
            if (sRefresh == null)
                sRefresh = "5";
        }

        sValues = req.getParameterValues("APPNAME");
        if (sValues != null) {
            String sParam = req.getParameterValues("APPNAME")[0];
            if (sParam != null)
                _sAppName = sParam;
        }

        sValues = req.getParameterValues("ITERATOR");
        String sIterator = "5";
        if (sValues != null) {
            sIterator = req.getParameterValues("ITERATOR")[0];
            if (sIterator == null)
                sIterator = "25";
        }

        sValues = req.getParameterValues("PRINTLOG");
        boolean bPrintLog = false;
        if (sValues != null) {
            String sParam = req.getParameterValues("PRINTLOG")[0];
            if (sParam != null)
                bPrintLog = true;
        }

		res.setContentType("text/html");
        p.println("<HTML>");
        p.println("<HEAD>");
        p.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">");
        p.println("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">");
        if (_autoRefresh)
            p.println("<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"" + sRefresh + "\">");

        String serverName = com.salmonllc.util.URLGenerator.generateServerURL(req);
        p.println("<TITLE>" + serverName + "</TITLE>");

        long firstHitTime = JspServlet.getFirstPageHitTime();
        String firstHitInfo = "";
        float minSinceStart = 1;
        if (firstHitTime > -1) {
            java.sql.Timestamp ts = new java.sql.Timestamp(firstHitTime);
            java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm a");
            firstHitInfo = " --- Started/Reset:" + sf.format(ts);
            long sec = (System.currentTimeMillis() - firstHitTime) / 1000;
            long min = sec / 60;
            minSinceStart = sec / 60F;
            long hours = min / 60;
            min = min - (hours * 60);
            sec = sec - ((hours * 3600) + (min * 60));
            firstHitInfo += " (" + hours + " hrs " + min + " mins " + sec + " secs)";
        }

        java.text.DecimalFormat nf2 = new java.text.DecimalFormat("###,##0");
        long lFreeMemory = (Runtime.getRuntime().freeMemory() / 1000);

        if (bPrintLog) {
            _sbFreeMemory.append(lFreeMemory);
            _sbFreeMemory.append(",");
            _iMemoryPrintCntr++;

            int iIterator = Integer.parseInt(sIterator);

            if (_iMemoryPrintCntr > iIterator && printLogInfo()) {
                _sbFreeMemory = new StringBuffer();
                _iMemoryPrintCntr = 0;
                _iListCntr++;
            }
        }

        java.text.DecimalFormat nf = new java.text.DecimalFormat("###,###0.00");
        long pageHits = JspServlet.getPageHits();
        String pageHitInfo = "Page Hits:<b>" + pageHits + "</b>&nbsp;&nbsp;&nbsp;";
        if (pageHits > 0) {
            pageHitInfo += "&nbsp;&nbsp;&nbsp;Average Hits/Minute:<b>" + nf.format(Math.round(((pageHits * 100.0F) / minSinceStart)) / 100D) + "</b>";
            pageHitInfo += "&nbsp;&nbsp;&nbsp;Average Seconds/Page Request:<b>" + nf.format(JspServlet.getAverageHitTime() / 1000D) + "</b>";
        }

        String controllerCacheInfo = "";
        if (JspServlet.isControllerCacheActive()) {
            controllerCacheInfo += "&nbsp;&nbsp;&nbsp;Cached Controllers:<b>" + JspServlet.getControllerCacheCount() + "</b>";
            controllerCacheInfo += "&nbsp;&nbsp;&nbsp;Controller Cache Memory:<b>" + nf2.format(JspServlet.getControllerCacheBytes() / 1000) + " kbs</b>";
        }


        p.println("<FORM METHOD=\"POST\">");
        p.println("<H1>Connection Monitor</H1>");
        p.println("<INPUT TYPE=\"SUBMIT\" name=\"reset\" value=\"Reset Stats\">");
        p.println("<INPUT TYPE=\"SUBMIT\" name=\"autorefresh\" value=\"Set Auto Refresh:" +(_autoRefresh ? "Off":"On") + "\">");
        p.println("<INPUT TYPE=\"SUBMIT\" name=\"timers\" value=\"" + (_showTimers ? "Hide":"Show") + " Timers\">");
        p.println("<INPUT TYPE=\"SUBMIT\" name=\"connections\" value=\"" + (_showConnections ? "Hide":"Show") + " Connections\">");

        p.println("<H3>" + serverName + firstHitInfo + "</H3>");

        p.println(pageHitInfo);
        p.println(controllerCacheInfo);
        p.println("&nbsp;&nbsp;&nbsp;Free Memory:<b>" + nf2.format(lFreeMemory) + " kbs of " + nf2.format(Runtime.getRuntime().totalMemory() / 1000) + " kbs</b>");


        TimerList ts = new TimerList();
        ts.populate(JspServlet.getTimersHashtable());
        ts.sort();
        if ( _showTimers) {
            Enumeration e = ts.elements();
            p.println("<br><br><TABLE border=\"0\" width=\"1000\">");
            p.println("<TR bgcolor=\"cadetblue\"><TD>Timer</TD><TD>Count</TD><TD>Avg Time (ms)</TD></TR>");
            while (e.hasMoreElements()) {
                Timer t = (Timer) e.nextElement();
                p.println("<TR bgcolor=\"darkgray\"><TD>" + t.name + "</TD><TD>" + t.count + "</TD><TD>" + t.totalTime / t.count + "</TD></TR>");
            }
            if (ts.size() == 0)
                p.println("<TR bgcolor=\"darkgray\"><TD colspan=\"3\">No Timers Set</TD></TR>");

            p.println("</TABLE>");
        }

        if (_showConnections) {
            p.println("<br><br><TABLE border=\"0\" width=\"1000\">");
            p.println("<TR bgcolor=\"cadetblue\"><TD>DBMS</TD><TD colspan=\"3\">URL</TD><TD>In Use</TD><TD>Idle</TD><TD>Driver</TD><TD>User ID</TD></TR>");
            Enumeration e = DBConnection.getConnectionLists();
            boolean connUsed = false;
            while (e.hasMoreElements()) {
                connUsed=true;
                DBConnectionList list = (DBConnectionList) e.nextElement();
                p.println("<TR bgcolor=\"darkgray\"><TD>");
                p.println(list.getDBMS());
                p.println("</TD><TD colspan=\"3\">");
                p.println(list.getDatabaseURL());
                p.println("</TD><TD>");
                p.println(new Integer(list.getInUseCount()).toString());
                p.println("</TD><TD>");
                p.println(new Integer(list.getIdleCount()).toString());
                p.println("</TD><TD>");
                p.println(list.getDriver());
                p.println("</TD><TD>");
                p.println(list.getDatabaseUser());
                p.println("</TD></TR>");
                java.util.Enumeration en = list.getConnections();
                p.println("<TR><TD>&nbsp;</TD><TD bgcolor=\"darkgray\">In Use</TD><TD bgcolor=\"darkgray\">Last Used</td><TD bgcolor=\"darkgray\">Last Duration</td><TD bgcolor=\"darkgray\" colspan=\"4\" width=\"600\">Last SQL</TD></TR>");
                long now = System.currentTimeMillis();
                while (en.hasMoreElements()) {
                    DBConnection conn = (DBConnection) en.nextElement();
                    p.println("<TR><TD>&nbsp</TD><TD>" + conn.getInUse() + "</TD><TD>" + ((now - conn.getLastUsed()) / 1000) + "</TD><TD>" + (conn.getLastDuration() / 1000D) + "<TD colspan=4>" + conn.getLastSQL() + "</TD></TR>");
                }
            }
            if (! connUsed)
                p.println("<TR bgcolor=\"darkgray\"><TD colspan=\"9\">No Connections Created</TD></TR>");
            p.println("</TABLE>");
        }
        p.println("</FORM>");
        p.println("</HTML>");
        p.close();

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);    	
    	if (req.getParameter("reset") != null)
            JspServlet.resetAllTimers();
        else if (req.getParameter("autorefresh") != null)
            _autoRefresh =! _autoRefresh;
        else if (req.getParameter("connections") != null)
            _showConnections =! _showConnections;
        else if (req.getParameter("timers") != null)
            _showTimers = ! _showTimers;
        doGet(req,res);
    }

    private boolean printLogInfo() {

        if (_sAppName == null) {
            com.salmonllc.util.MessageLog.writeDebugMessage("com.salmonllc.servlets.ConnectionMonitor.printLogInfo()Application name is not known.", this);
            return false;
        }

        com.salmonllc.util.MessageLog.writeDebugMessage("com.salmonllc.servlets.ConnectionMonitor.printLogInfo()Starting to write the log.", this);
        FileOutputStream fos = null;
        PrintWriter pwr = null;
        try {
            String sFilePath = Props.getProps("RealEstate", "ObjectStorePath").getProperty("ObjectStorePath") +
                    "\\FreemMemorySpace" + _iListCntr + ".csv";
            fos = new FileOutputStream(sFilePath);
            pwr = new PrintWriter(fos);
        } catch (Exception ex) {
            com.salmonllc.util.MessageLog.writeErrorMessage("com.salmonllc.servlets.ConnectionMonitor.printLogInfo() ;Can not open output file.", ex, this);
            return false;
        }

        StringBuffer sb = new StringBuffer();

        java.text.StringCharacterIterator sci = new java.text.StringCharacterIterator(_sbFreeMemory.toString());
        try {

            for (char c = sci.first(); c != java.text.CharacterIterator.DONE; c = sci.next()) {
                Character chr = new Character(c);
                if (chr.equals(new Character(','))) {
                    pwr.println(sb.toString());
                    ;
                    sb = new StringBuffer();
                } else
                    sb.append(chr);
            }
        } catch (Exception ex) {
            com.salmonllc.util.MessageLog.writeErrorMessage("com.salmonllc.servlets.ConnectionMonitor.printLogInfo() Iterator ;Can not open output file.", ex, this);
            return false;
        } finally {
            if (pwr != null) {
                pwr.flush();
                pwr.close();
            }

            if (fos != null)
                try {
                    fos.close();
                } catch (Exception ex) {
                }

            pwr = null;
            fos = null;
        }

        return true;

    }

}
