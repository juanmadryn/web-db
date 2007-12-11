//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2004, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.util;

import java.io.File;

/**
 * Creates an application context object that can be used by other framwork classes to determine information on the web application
 */
public class ApplicationContext {
	private static ThreadLocal _context = new ContextThreadLocal();
	private String _appID;
	private String _appDocumentRoot;
	private String _appPropsPath;
	private MessageLog _logger;

	/**
	 * @author  demian
	 */
	private static class ContextThread extends Thread {
		ApplicationContext _parentThreadContext;
		private ContextThread(ApplicationContext parentContext, Runnable o) {
			super(o);
			_parentThreadContext = parentContext;
		}
		private ApplicationContext getContext() {
			return _parentThreadContext;
		}
	}

	private static class ContextThreadLocal extends ThreadLocal {
		protected Object initialValue() {
			Thread t = Thread.currentThread();
			if (t instanceof ContextThread)
				return ((ContextThread) t).getContext();
			else
				return super.initialValue();
		}
	}

	/**
	 * Creates a new thread that shares the same application context as the current thread
	 */
	public static Thread createThreadWithContext(Runnable r) {
		return new ContextThread(getContext(), r);
	}

	/**
	 * Creates a new thread with a clone of the application context as the current thread
	 */
	public static Thread createThreadWithContextClone(Runnable r) {
		ApplicationContext from =getContext();
		if (from != null) {
			ApplicationContext to=new ApplicationContext();
			to.setAppDocumentRoot(from.getAppDocumentRoot());
			to.setAppID(from.getAppID());
			to.setLogger(from.getLogger());
			return new ContextThread(to, r);
		}	
		else
			return new ContextThread(null,r);
		
	}
	
	public static void setContext(ApplicationContext cont) {
		_context.set(cont);
	}

	public static ApplicationContext getContext() {
		return (ApplicationContext) _context.get();
	}

	/**
	 * @return Returns the appID.
	 */
	public String getAppID() {
		if (_appID==null)
			return "";
		else
			return _appID;
	}
	/**
	 * @param appID
	 * The appID to set.
	 */
	public void setAppID(String appID) {
		_appID = appID;
	}
	/**
	 * @return Returns the contextRoot.
	 */
	public String getAppDocumentRoot() {
		return _appDocumentRoot;
	}
	/**
	 * @return Returns the logger.
	 */
	public MessageLog getLogger() {
		return _logger;
	}
	/**
	 * @param Framework method, don't call directly
	 */
	public void setLogger(MessageLog logger) {
		_logger = logger;
	}
	/**
	 * @param Framework method, don't call directly
	 */
	public void setAppDocumentRoot(String contextRoot) {
		_appDocumentRoot = contextRoot;
		_appPropsPath=contextRoot;
		if (!_appPropsPath.endsWith(File.separator))
			_appPropsPath+=File.separator;
		_appPropsPath+="WEB-INF"+File.separator+"properties";
	}
	
	/**
	 * Returns the directory where framework properties are stored
	 * @return
	 */
	public String getAppPropertiesPath() {
		return _appPropsPath;
	}	
	
	public String toString() {
		return "AppID:" + _appID + " DocumentRoot:" +  _appDocumentRoot;
	}	

}