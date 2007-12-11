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
package com.salmonllc.html;

/**
 * This class can be extended to implement tasks that take so long to process that that take so long to run that the browser times out. Implementing the process in the doProcess method of PageRunThread and running it via HtmlPage.startRunThread() will allow it to be executed from a page that can be refreshed every few seconds and display a progress meter.
 */
public abstract class PageRunThread implements Runnable {
	public static final int STATUS_NOT_STARTED = 0;
	public static final int STATUS_RUNNING = 1;
	public static final int STATUS_COMPLETED = 2;
	public static final int STATUS_CANCELED = 3;
	public static final int STATUS_ERROR = 4;
	public static final int STATUS_NOT_SET=-1;
	
	private int _status = PageRunThread.STATUS_NOT_STARTED;
	private int _perComplete = 0;
	private Exception _exp;
/**
 * This method should stop the thread and set the status to STATUS_CANCELED
 */
public void cancel() {
	_status = PageRunThread.STATUS_CANCELED;
}
/**
 * This should implement the long running task that the component will run. In addition, it should periodically check the status via getStatus() to determine if the process has been canceled  by the user as well as setting the tasks percent complete (via setPercentComplete() method).
 */
public abstract void doProcess() throws Exception;
/**
 * This method returns the Exception thrown by the doProcess method if the status = STATUS_ERROR. Otherwise it will return null;
 * @return java.lang.Exception
 */
public Exception getException() {
	return _exp;
}
/**
 * This method returns a number from 0 to 100 that indicates the percent complete the process is.
 */
public int getPercentComplete() {
	return _perComplete;
}
/**
 * This method returns the status of the thread. It should return one of the following values:STATUS_NOT_STARTED, STATUS_RUNNING, STATUS_COMPLETED,STATUS_CANCELED
 */
public int getStatus() {
	return _status;
}
/**
 * This method should not be called directly. It is implemented for threading purposes.
 */
public void run() {
	_exp = null;
	_status = PageRunThread.STATUS_RUNNING;
	try {
		doProcess();
	}
	catch (Exception e) {
		_exp = e;
		_status = PageRunThread.STATUS_ERROR;	
	}		
	if (_status == PageRunThread.STATUS_RUNNING)
		_status = PageRunThread.STATUS_COMPLETED;
}
/**
 * This method should be used by the doProcess method to inform the rest of the system how complete the process. Pass in a number between 0 and 100.
 */
protected void setPercentComplete(int perComplete) {
	if (perComplete < 0)
		_perComplete = 0;
	else if (perComplete > 100)
		_perComplete = 100;
	else	
		_perComplete = perComplete;
}
/**
 * This method was created in VisualAge.
 * @param Status int
 */
public void setStatus(int status) {
	_status=status;
}
}
