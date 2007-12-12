package com.salmonllc.util;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/util/MessageLog.java $
//$Author: Dan $
//$Revision: 21 $
//$Modtime: 9/21/04 1:15p $
/////////////////////////

import com.salmonllc.properties.Props;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is used to write messages to the system console or to a log file.
 */
public class MessageLog implements Runnable {

	private static HashMap _appLoggers = new HashMap();
	private static MessageLog _defaultLog;
	private static boolean _runThread=true;
	private int _logErrors = 10;
    private int _logAssertions = 0;
    private int _logInfo = 0;
    private int _logSQL = 0;
    private int _logDebug = 0;
    private boolean _init = false;
    private boolean _hideTimestamp = false;
    private boolean _hideThread = false;
    private String _logFile;
    private int _logFileIndex;
    private int _logFileSize = -1;
    private PrintStream _errorOut=null;
    private PrintStream _assertOut=null;
    private PrintStream _infoOut=null;
    private PrintStream _debugOut=null;
    private PrintStream _SQLOut=null;
	private Logger _logger=null;
	private String _oldLoggerName=null;
	private String _oldConfigName=null;
	private long _oldConfigDate=-1;
	private String _appName;

    private MessageLog(String appName) {
        super();
        _appName=appName;
       checkStatus();
    }

    private synchronized  void checkStatus() {
        if (!_init) {
            _init = true;
            if (loadProperties()) {
                Thread t = ApplicationContext.createThreadWithContextClone(this);
                t.start();
            }
        } else
            loadProperties();

    }

    /**
     * Use this method to get the level of logging desired.
     * true converts to a level = 11
     * false converts to a level = 0
     * all others are returned as is
     */

    private int getLoggingLevel(String prop) {
        int retVal = 11;

        if (!Util.isNumeric(prop)) {
            if (Util.isTrue(prop)) {
                retVal = 11;
            } else {
                retVal = -1;
            }

        } else {
            retVal = Integer.parseInt(prop);
        }
        return retVal;
    }

    private static PrintStream setUpPrintStream(Props pr, String key,  PrintStream def) {
          String val = pr.getProperty(key);
          if (val == null)
              return def;
          else if (val.equalsIgnoreCase("true"))
              return def;
          else if (val.equalsIgnoreCase("out"))
              return System.out;
          else if (val.equalsIgnoreCase("err"))
              return System.err;
          else
              return null;
    }

    private boolean loadProperties() {
        Props p = Props.getSystemProps();
        if (p == null)
            return false;
        
		String logger = p.getProperty(Props.SYS_MESSAGELOG_LOGGER_CLASS);
		String configFile = p.getProperty(Props.SYS_MESSAGELOG_LOGGER_CONFIG_FILE);
		boolean reload = false;
		if (logger != null) {
			if (! valuesEqual(logger,_oldLoggerName))
				reload = true;
			else if (! valuesEqual(configFile,_oldConfigName))
				reload = true;
			else if (configFile != null){
				File cf = new File(configFile);
				if (cf.exists()) {
					if (cf.lastModified() != _oldConfigDate) 
						reload=true;
				}
					
			}
				
		}
		
		if (reload) {
			try {
				Class c = Class.forName(logger);
				_logger = (Logger) c.newInstance();
				_oldConfigName=configFile;
				_oldLoggerName=logger;
				if (configFile != null) {
					File cf = new File(configFile);
					if (cf.exists())
						_oldConfigDate=cf.lastModified();
				}
				else
					_oldConfigDate = -1;
				return true;
			}
			catch (Exception e) {
				System.err.println("Error:Could not load logger class:" + logger + " using default!");
				e.printStackTrace();
			}
		}
        String prop = null;
        // ERRORS
        prop = p.getProperty(Props.LOG_ERRORS);
        _logErrors = getLoggingLevel(prop);
        _errorOut = setUpPrintStream(p,Props.LOG_ERRORS_TO_CONSOLE,System.err);

        // ASSERTIONS
        prop = p.getProperty(Props.LOG_ASSERTIONS);
        _logAssertions = getLoggingLevel(prop);
        _assertOut = setUpPrintStream(p,Props.LOG_ASSERTIONS_TO_CONSOLE,System.err);

        // INFO
        prop = p.getProperty(Props.LOG_INFO);
        _logInfo = getLoggingLevel(prop);
        _infoOut = setUpPrintStream(p,Props.LOG_INFO_TO_CONSOLE,System.out);

        // SQL
        prop = p.getProperty(Props.LOG_SQL);
        _logSQL = getLoggingLevel(prop);
        _SQLOut = setUpPrintStream(p,Props.LOG_SQL_TO_CONSOLE,System.out);

        // DEBUG
        prop = p.getProperty(Props.LOG_DEBUG);
        _logDebug = getLoggingLevel(prop);
        _debugOut = setUpPrintStream(p,Props.LOG_DEBUG_TO_CONSOLE,System.out);

        _logFile = p.getProperty(Props.LOG_FILE_NAME);

        _hideThread = p.getBooleanProperty(Props.LOG_HIDE_THREAD);
        _hideTimestamp = p.getBooleanProperty(Props.LOG_HIDE_TIME);

        if (_logFile != null) {

            //file size is in bytes -1 is unlimited
            _logFileSize = p.getIntProperty(Props.LOG_FILE_SIZE_LIMIT);

            String logFileExtension = p.getProperty(Props.LOG_FILE_EXT);
            boolean appendDate = p.getBooleanProperty(Props.LOG_FILE_APPEND_DATE);
            if (appendDate) {
                SimpleDateFormat d = new SimpleDateFormat("-yyyy-MM-dd");
                _logFile += d.format(new Date(System.currentTimeMillis()));

            }
            if (logFileExtension != null)
                _logFile += "." + logFileExtension;
        }
        return true;
    }

    /**
     * This method is part of the run thread used by this object. It should not be called directy.
     */
    public void run() {
        while (_runThread) {
            try {
                Thread.sleep(15000);
                checkStatus();
            } catch (Exception e) {
            }
        }
    }
 
    private void writeAssertionMessageInstance(String message, int level, Object o) {
		if (_logger != null)
			_logger.log(message,Logger.TYPE_ASSERTION,level,null,o);
		else if (_logAssertions >= level)
            writeMessage("Assertion", o, message, null,_assertOut);
    }

    /**
     * Use this method to log Assertion Messages.
     */
    public static void writeAssertionMessage(String message, Object o) {
        writeAssertionMessage(message, 0, o);
    }

    /**
     * Use this method to log Assertion Messages.
     */
    public static void writeAssertionMessage(String message, int level, Object o) {
    	getLogger().writeAssertionMessageInstance(message,level,o);
    }	
    
    /**
     * Use this method to log Debug Messages.
     */
    public static void writeDebugMessage(String message, int level, Object o) {
    	getLogger().writeDebugMessageInstance(message,level,o);
    }

    /**
     * Use this method to log Debug Messages.
     */
    public static void writeDebugMessage(String message, Object o) {
        writeDebugMessage(message, 0, o);
    }

    private void writeDebugMessageInstance(String message, int level, Object o) {
		if (_logger != null)
			_logger.log(message,Logger.TYPE_DEBUG,level,null,o);
		else if (_logDebug >= level)
            writeMessage("Debug", o, message, null,_debugOut);
    }
    /**
     * Use this method to log Error Messages.
     */

    public static void writeErrorMessage(String message, int level, Throwable e, Object o) {
    	  getLogger().writeErrorMessageInstance(message, 0, e, o);
    }

    /**
     * Use this method to log Error Messages.
     */
    public static void writeErrorMessage(String message, Throwable e, Object o) {
        writeErrorMessage(message, 0, e, o);
    }

    private void writeErrorMessageInstance (String message, int level, Throwable e, Object o) {
		if (_logger != null)
			_logger.log(message,Logger.TYPE_ERROR,level,e,o);
		else if (_logErrors >= level) {
            if (message != null) {
                if (e != null) {
                    writeMessage("Error", o, message + ":" + e.toString(), e,_errorOut);
                } else {
                    writeMessage("Error", o, message, e,_errorOut);
                }
            } else {
                writeMessage("Error", o, e.toString(), e,_errorOut);
            }
        }
    }
    /**
     * Use this method to log Error Messages.
     */

    public static void writeErrorMessage(Throwable e, Object o) {
        writeErrorMessage(null, e, o);
    }

    /**
     * Use this method to Log Info Messages.
     */
    public static void writeInfoMessage(String message, int level, Object o) {
    	getLogger().writeInfoMessageInstance(message,level,o);
    }	

    /**
     * Use this method to Log Info Messages.
     */

    public static void writeInfoMessage(String message, Object o) {
        writeInfoMessage(message, 0, o);
    }

    private void writeInfoMessageInstance(String message, int level, Object o) {
		if (_logger != null)
			_logger.log(message,Logger.TYPE_INFO,level,null,o);
		else if (_logInfo >= level)
            writeMessage("Information Message", o, message, null,_infoOut);
    }
    
    private synchronized void writeMessage(String type, Object source, String message, Throwable t, PrintStream p) {
        StringBuffer outMessage = new StringBuffer("{ ");
        outMessage.append(type);
        outMessage.append(" | ");
        if (!_hideTimestamp) {
            outMessage.append((new java.sql.Timestamp(System.currentTimeMillis())));
            outMessage.append(" | ");
        }

        if (!_hideThread) {
            outMessage.append(Thread.currentThread());
            outMessage.append(" |  ");
        }

        if (source != null) {
            outMessage.append(source.getClass().getName());
        }
        outMessage.append("} ");
        if (message != null) {
            outMessage.append(message);
        }
        if (p != null)
            p.println(outMessage);

        if (t != null) {
            if (p != null) {
                p.println("{Stack Trace");
                t.printStackTrace(p);
                p.println("}");
            }
        }
        if (_logFile != null) {
            try {
                File f = new File(_logFile);
                FileOutputStream out = null;
                if (f.exists()) {

                    if (!(_logFileSize <= 0) && f.length() >= _logFileSize) {
                        String renameFile = null;
                        renameFile = _logFile + "." + new DecimalFormat("00000").format(_logFileIndex);
                        File renf = new File(renameFile);
                        while (renf.exists()) {
                            _logFileIndex++;
                            renameFile = _logFile + "." + new DecimalFormat("00000").format(_logFileIndex);
                            renf = new File(renameFile);
                        }
                        _logFileIndex++;
                        f.renameTo(renf);
                        out = new FileOutputStream(f);

                    } else {
                        out = new FileOutputStream(_logFile, true);
                    }
                } else {
                    out = new FileOutputStream(f);
                }
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
                pw.println(outMessage);
                if (t != null) {
                    pw.println("[Stack Trace");
                    t.printStackTrace(pw);
                    pw.println("]");
                }
                pw.close();
                out.close();
            } catch (Exception e) {
                System.err.println("Error Writing Message to:" + _logFile + ":" + e);
            }
        }
    }

    

    private void writeSQLMessageInstance(String message, int level, String sql, Object o) {
		if (_logger != null)
			_logger.log(message + " " + sql,Logger.TYPE_SQL,level,null,o);
		else if (_logSQL >= level)
            writeMessage("SQL Statement", o, message + " " + sql, null,_SQLOut);
    }

    /**
     * Use this method to log SQL Messages.
     */
    public static void writeSQLMessage(String message, String sql, Object o) {
        writeSQLMessage(message, 0, sql, o);
    }

    /**
     * Use this method to log SQL Messages.
     */
    public static void writeSQLMessage(String message, int level, String sql, Object o) {
    	getLogger().writeSQLMessageInstance(message,level,sql,o);
    }
    
	private static boolean valuesEqual(Object newValue, Object oldValue) {
		if (newValue == null && oldValue != null)
			return false;
		else if (newValue != null && oldValue == null)
			return false;
		else if (newValue != null && oldValue != null)
			if (!newValue.toString().trim().equals(oldValue.toString().trim()))
				return false;

		return true;
	}
	
	public static synchronized MessageLog getLoggerForApplication(String appName) {
		MessageLog ret = (MessageLog)_appLoggers.get(appName);
		if (ret == null) {
			ret = new MessageLog(appName);
			_appLoggers.put(appName,ret);
		}	
		return ret;
	}
	
	public String toString() {
		return _appName + " " + _logFile;
	}	
	private static MessageLog getLogger() {
		ApplicationContext cont=ApplicationContext.getContext();
		if (cont!=null) {
			return cont.getLogger();
		}	
		else {
			if (_defaultLog == null)
				_defaultLog=new MessageLog("");
			return _defaultLog;
			
		}	
	}	
	public static void stopThreads() {
		_runThread=false;
	}	
}
