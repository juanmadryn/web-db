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
package com.salmonllc.scheduler;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/scheduler/PerformanceLogger.java $
//$Author: Dan $ 
//$Revision: 5 $ 
//$Modtime: 10/30/02 2:38p $ 
/////////////////////////

import java.util.*;
import java.io.*;
import com.salmonllc.jsp.*;
import com.salmonllc.util.*;
import com.salmonllc.properties.*;

 
/**
 * This scheduled object is used to log performance metrics on the server to a log file.
 */
	public class PerformanceLogger implements ScheduledObject {

			static int _lastPageHits = 0;
			static long _lastHitTime = 0;
			static long _lastFreeMemory = 0;
			static long _lastTotalMemory = 0;
			static long _lastRunTime = 0;
private String formatDuration(long duration) {
	java.text.DecimalFormat df = new java.text.DecimalFormat("00");
	long sec = duration / 1000;
	long min = sec / 60;
	long hours = min / 60;
	min = min - (hours * 60);
	sec = sec - ((hours * 3600) + (min*60));
	StringBuffer ret = new StringBuffer();

	if (hours > 0) {
		ret.append(df.format(hours));
		ret.append(":");
	}
	
	ret.append(df.format(min));
	ret.append(":");
	ret.append(df.format(sec));
	
	return ret.toString();
}
public void scheduleReached(ScheduleReachedEvent e) {
		
		try {
			Props p = Props.getSystemProps();
			String logFile = p.getProperty("PerformanceLogFile");
			if (logFile == null) {
				MessageLog.writeInfoMessage("Could not find property \"PerformanceLogFile\" in system.properties file.",this);
				return;
			}
			else {
				MessageLog.writeInfoMessage("Logging performance metrics to " + logFile ,this);
			}	

			long now = System.currentTimeMillis();
			long serverRunTime=now - JspServlet.getFirstPageHitTime();
			int pageHits = JspServlet.getPageHits();
			long hitTime=JspServlet.getTotalHitTime();
			long freeMemory=Runtime.getRuntime().freeMemory();
			long totalMemory=Runtime.getRuntime().totalMemory();
			
			double avgHits = 0;
			if (serverRunTime > 0)
				avgHits = (((pageHits * 1.0) / serverRunTime));
				
			double avgResponseTime = 0;
			if (pageHits > 0) 
				avgResponseTime = ((hitTime * 1.0) / pageHits);

			int pageHitsDelta = 0;
			long hitTimeDelta = 0;
			long freeMemoryDelta = 0;
			long totalMemoryDelta = 0;
			long serverRunTimeDelta = 0;
			double avgHitsPeriod = 0;
			double avgResponseTimePeriod = 0;
				
			if (_lastHitTime > 0) {
				pageHitsDelta = pageHits - _lastPageHits;
				hitTimeDelta = hitTime - _lastHitTime;
				freeMemoryDelta = freeMemory - _lastFreeMemory;
				totalMemoryDelta = totalMemory - _lastTotalMemory;
				serverRunTimeDelta = now - _lastRunTime;
				avgHitsPeriod = (((pageHitsDelta * 1.0) / serverRunTimeDelta));
				if (pageHitsDelta > 0)
					avgResponseTimePeriod = ((hitTimeDelta * 1.0) / pageHitsDelta);
				else
					avgResponseTimePeriod = 0;	
			}

			_lastFreeMemory = freeMemory;
			_lastHitTime = hitTime;
			_lastPageHits = pageHits;
			_lastRunTime = now;
			_lastTotalMemory = totalMemory;
				
			File f = new File(logFile);
			FileOutputStream out = null;
			boolean printHeadings = false;
			if (f.exists())		
				out = new FileOutputStream(logFile, true);
			else {
				out = new FileOutputStream(f);
				printHeadings = true;
			}

			java.text.DecimalFormat df = new java.text.DecimalFormat("########0.00000");
			java.text.SimpleDateFormat dtf = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm a");
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

			if (printHeadings) 
				pw.println("Time\tServer Run Time (sec)\tTotal Page Hits\tTotal Page Hit Time(Seconds)\tFree Memory (kbs)\tTotal Memory (kbs)\tAverage Hits Per Second\tAverage Response Time (seconds)\tPeriod Length (hrs:min:sec)\tPage Hit For Period\tTotal Page Hits Time for Period (seconds)\tFree Memory Delta (kbs)\tTotal Memory Delta (kbs)\tAverage Hits Per Second For Period\tAverage Response Time For Period (seconds)");

			//Time		
			Date d = new Date(now);
			pw.print(dtf.format(d));
			pw.print('\t');

			//Server run time		
			
			pw.print(new Long(serverRunTime / 1000));
			pw.print('\t');

			//Page Hits
			Integer i = new Integer(pageHits);
			pw.print(i.toString());
			pw.print('\t');

			//Page Hit Time
			pw.print(df.format(hitTime / 1000D));
			pw.print('\t');

			//Free Memory
			Long l = new Long(freeMemory / 1000);
			pw.print(l.toString());
			pw.print('\t');

			//Total Memory
			l = new Long(totalMemory / 1000);
			pw.print(l.toString());
			pw.print('\t');

			//Avg Hits per second
			pw.print(df.format(avgHits * 1000));
			pw.print('\t');
			
			//Avg Response time			
			pw.print(df.format(avgResponseTime / 1000D));
			pw.print('\t');

			//Period Length
			pw.print(formatDuration(serverRunTimeDelta));
			pw.print('\t');
			
			//Page Hits Delta
			i = new Integer(pageHitsDelta);
			pw.print(i.toString());
			pw.print('\t');

			//Page Hit Time Delta
			pw.print(df.format(hitTimeDelta / 1000D));
			pw.print('\t');
				
			//Free Memory Delta
			l = new Long(freeMemoryDelta / 1000);
			pw.print(l.toString());
			pw.print('\t');

			//Total Memory Delta
			l = new Long(totalMemoryDelta / 1000);
			pw.print(l.toString());
			pw.print('\t');

			//Avg Hits per second for period
			pw.print(df.format(avgHitsPeriod * 1000));
			pw.print('\t');
			
			//Avg Response time	for period
			pw.print(df.format(avgResponseTimePeriod / 1000D));
			pw.print('\t');
			
			//next line
			pw.println();

			pw.close();
			out.close();
		}
		catch (Exception ex) {
			MessageLog.writeErrorMessage("PerformanceLogger.scheduleReached",ex,this);
		}		
			
}
}
