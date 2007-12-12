/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.salmonllc.ideTools;


import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.apache.catalina.security.SecurityClassLoad;
import org.apache.catalina.startup.CatalinaProperties;
import org.apache.catalina.startup.ClassLoaderFactory; 


/**
 * Boostrap loader for Catalina.  This application constructs a class loader for use in loading the Catalina internal classes (by accumulating all of the JAR files found in the "server" directory under "catalina.home"), and starts the regular execution of the container.  The purpose of this roundabout approach is to keep the Catalina internal classes (and any other classes they depend on, such as an XML parser) out of the system class path and therefore not visible to application level classes.
 * @author  Craig R. McClanahan
 * @author  Remy Maucherat
 * @version  $Revision: 1 $ $Date: 8/05/04 2:55p $
 */

public final class Tomcat50Bootstrap {


    // -------------------------------------------------------------- Constants


    protected static final String CATALINA_HOME_TOKEN = "${catalina.home}";
    protected static final String CATALINA_BASE_TOKEN = "${catalina.base}";


    // ------------------------------------------------------- Static Variables


    private static final String JMX_ERROR_MESSAGE =
        "Due to new licensing guidelines mandated by the Apache Software\n"
        + "Foundation Board of Directors, a JMX implementation can no longer\n"
        + "be distributed with the Apache Tomcat binaries. As a result, you \n"
        + "must download a JMX 1.2 implementation (such as the Sun Reference\n"
        + "Implementation) and copy the JAR containing the API and \n"
        + "implementation of the JMX specification to: \n" 
        + "${catalina.home}/bin/jmx.jar";


    /**
     * Daemon object used by main.
     */
    private static Tomcat50Bootstrap _daemon = null;


    // -------------------------------------------------------------- Variables


    /**
     * Debugging detail level for processing the startup.
     */
    protected int _debug = 0;


    /**
     * Daemon reference.
     */
    private Object _catalinaDaemon = null;


    protected ClassLoader _commonLoader = null;
    protected ClassLoader _catalinaLoader = null;
    protected ClassLoader _sharedLoader = null;

    private boolean _restart = false;
    private String _extraCom = null;
    private String _extraArgs = null;

    public static int START = 1;
    public static int STOP = 2;
    public static int RESTART = 3;
    
    /**
     * Create a new bootstrap object, extraCom and extraArgs are so the browser can start after the server starts
     */
    private Tomcat50Bootstrap(boolean restart, String extraCom, String extraArgs) {
        _restart = restart;
        _extraCom = extraCom;
        _extraArgs = extraArgs;
    }
    
    /*
    * Start, stop or restart the local tomcat server. Optionally launch the browser as well.
    * @param process constants START, STOP or RESTART
    * @param browserPath - The full path to the browser executable
    * @param webPage - The URL of the web page to run
    */
   public static void execute(int process, String browserPath, String webPage) {
       Tomcat50Bootstrap tb = new Tomcat50Bootstrap(process == RESTART, browserPath, webPage);
       if (process == START)
           executeCommand("start",tb);
       else if (process == STOP || process == RESTART)
           executeCommand("stop", tb);

   }
   

   private static void executeCommand(String command, Tomcat50Bootstrap tb) {
       if (_daemon == null) {
       		_daemon = tb;
       		try {
       			_daemon.init();
       		} catch (Throwable t) {
       			t.printStackTrace();
       			return;
       		}
       }

       String args[]={command};
       try {
       		if (command.equals("start")) {
       			_daemon.setAwait(true);
       			_daemon.load(args);
       			_daemon.start();
       		} else if (command.equals("stop")) {
       			_daemon.stopServer(args);
       		}
       } catch (Throwable t) {
       		t.printStackTrace();
       }
   }

   String getBrowserExe() {
       return _extraCom;
   }
   String getBrowserURL() {
       return _extraArgs;
   }

      /**
   * This method gets fired when a process is executed
   */
  void notifyComplete() {
       if (_restart) {
           _restart = false;
           executeCommand("start", this);
       }
   }

    // -------------------------------------------------------- Private Methods


    private void initClassLoaders() {
        try {
            ClassLoaderFactory.setDebug(_debug);
            _commonLoader = createClassLoader("common", null);
            _catalinaLoader = createClassLoader("server", _commonLoader);
            _sharedLoader = createClassLoader("shared", _commonLoader);
        } catch (Throwable t) {
            log("Class loader creation threw exception", t);
            System.exit(1);
        }
    }


    private ClassLoader createClassLoader(String name, ClassLoader parent)
        throws Exception {

        String value = CatalinaProperties.getProperty(name + ".loader");
        if ((value == null) || (value.equals("")))
            return parent;

        ArrayList unpackedList = new ArrayList();
        ArrayList packedList = new ArrayList();
        ArrayList urlList = new ArrayList();

        StringTokenizer tokenizer = new StringTokenizer(value, ",");
        while (tokenizer.hasMoreElements()) {
            String repository = tokenizer.nextToken();
            // Check for a JAR URL repository
            try {
                urlList.add(new URL(repository));
                continue;
            } catch (MalformedURLException e) {
                // Ignore
            }
            // Local repository
            boolean packed = false;
            if (repository.startsWith(CATALINA_HOME_TOKEN)) {
                repository = getCatalinaHome() 
                    + repository.substring(CATALINA_HOME_TOKEN.length());
            } else if (repository.startsWith(CATALINA_BASE_TOKEN)) {
                repository = getCatalinaBase() 
                    + repository.substring(CATALINA_BASE_TOKEN.length());
            }
            if (repository.endsWith("*.jar")) {
                packed = true;
                repository = repository.substring
                    (0, repository.length() - "*.jar".length());
            }
            if (packed) {
                packedList.add(new File(repository));
            } else {
                unpackedList.add(new File(repository));
            }
        }

        File[] unpacked = (File[]) unpackedList.toArray(new File[0]);
        File[] packed = (File[]) packedList.toArray(new File[0]);
        URL[] urls = (URL[]) urlList.toArray(new URL[0]);

        ClassLoader classLoader = ClassLoaderFactory.createClassLoader
            (unpacked, packed, urls, parent);

        // Retrieving MBean server
        MBeanServer mBeanServer = null;
       	if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
       		mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
       	} else {
       		mBeanServer = MBeanServerFactory.createMBeanServer();
       	}
	

        // Register the server classloader
        ObjectName objectName = 
            new ObjectName("Catalina:type=ServerClassLoader,name=" + name);
        mBeanServer.registerMBean(classLoader, objectName);

        return classLoader;

    }


    /**
     * Initialize daemon.
     */
    public void init()
        throws Exception
    {

        // Set Catalina path
        setCatalinaHome();
        setCatalinaBase();

        initClassLoaders();

        Thread.currentThread().setContextClassLoader(_catalinaLoader);

        SecurityClassLoad.securityClassLoad(_catalinaLoader);

        // Load our startup class and call its process() method
        if (_debug >= 1)
            log("Loading startup class");
        Class startupClass =
            _catalinaLoader.loadClass
            ("com.salmonllc.ideTools.Tomcat50Engine");
        Object startupInstance = startupClass.newInstance();

        // Set the shared extensions class loader
        if (_debug >= 1)
            log("Setting startup class properties");
        String methodName = "setParentClassLoader";
        Class paramTypes[] = new Class[1];
        paramTypes[0] = Class.forName("java.lang.ClassLoader");
        Object paramValues[] = new Object[1];
        paramValues[0] = _sharedLoader;
        Method method =
            startupInstance.getClass().getMethod(methodName, paramTypes);
        method.invoke(startupInstance, paramValues);

        _catalinaDaemon = startupInstance;

    }


    /**
     * Load daemon.
     */
    private void load(String[] arguments)
        throws Exception {

        // Call the load() method
        String methodName = "load";
        Object param[];
        Class paramTypes[];
        if (arguments==null || arguments.length==0) {
            paramTypes = null;
            param = null;
        } else {
            paramTypes = new Class[1];
            paramTypes[0] = arguments.getClass();
            param = new Object[1];
            param[0] = arguments;
        }
        Method method = 
            _catalinaDaemon.getClass().getMethod(methodName, paramTypes);
        if (_debug >= 1)
            log("Calling startup class " + method);
        method.invoke(_catalinaDaemon, param);

    }


    // ----------------------------------------------------------- Main Program


    /**
     * Load the Catalina daemon.
     */
    public void init(String[] arguments)
        throws Exception {

        // Read the arguments
        if (arguments != null) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i].equals("-debug")) {
                    _debug = 1;
                }
            }
        }

        init();
        load(arguments);

    }


    /**
     * Start the Catalina daemon.
     */
    public void start()
        throws Exception {
        if( _catalinaDaemon==null ) init();

        Class classes[] = {getClass()};
        Object parms[] = {this};
        Method method = _catalinaDaemon.getClass().getMethod("start", classes);
        method.invoke(_catalinaDaemon, parms);

    }


    /**
     * Stop the Catalina Daemon.
     */
    public void stop()
        throws Exception {

        Method method = _catalinaDaemon.getClass().getMethod("stop", null);
        method.invoke(_catalinaDaemon, null);

    }


   /**
     * Stop the standlone server.
     */
    public void stopServer(String[] arguments)
        throws Exception {

        Object param[];
        Class paramTypes[];
        if (arguments==null || arguments.length==0) {
            paramTypes = null;
            param = null;
        } else {
            paramTypes = new Class[2];
            paramTypes[0] = arguments.getClass();
            paramTypes[1] = getClass();
            param = new Object[2];
            param[0] = arguments;
            param[1] = this;
        }
        Method method = 
            _catalinaDaemon.getClass().getMethod("stopServer", paramTypes);
        method.invoke(_catalinaDaemon, param);

    }


    /**
     * Set flag.
     */
    public void setAwait(boolean await)
        throws Exception {

        Class paramTypes[] = new Class[1];
        paramTypes[0] = Boolean.TYPE;
        Object paramValues[] = new Object[1];
        paramValues[0] = new Boolean(await);
        Method method = 
            _catalinaDaemon.getClass().getMethod("setAwait", paramTypes);
        method.invoke(_catalinaDaemon, paramValues);

    }

    public boolean getAwait()
        throws Exception
    {
        Class paramTypes[] = new Class[0];
        Object paramValues[] = new Object[0];
        Method method =
            _catalinaDaemon.getClass().getMethod("getAwait", paramTypes);
        Boolean b=(Boolean)method.invoke(_catalinaDaemon, paramValues);
        return b.booleanValue();
    }


    /**
     * Destroy the Catalina Daemon.
     */
    public void destroy() {

        // FIXME

    }

    public static void main(String args[]) {
    	execute(RESTART,"C:\\Program Files\\Internet Explorer\\iexplore.exe","http://localhost:8080");
    }

    public void setCatalinaHome(String s) {
        System.setProperty( "catalina.home", s );
    }

    public void setCatalinaBase(String s) {
        System.setProperty( "catalina.base", s );
    }


    /**
     * Set the <code>catalina.base</code> System property to the current
     * working directory if it has not been set.
     */
    private void setCatalinaBase() {

        if (System.getProperty("catalina.base") != null)
            return;
        if (System.getProperty("catalina.home") != null)
            System.setProperty("catalina.base",
                               System.getProperty("catalina.home"));
        else
            System.setProperty("catalina.base",
                               System.getProperty("user.dir"));

    }


    /**
     * Set the <code>catalina.home</code> System property to the current
     * working directory if it has not been set.
     */
    private void setCatalinaHome() {

        if (System.getProperty("catalina.home") != null)
            return;
        File bootstrapJar = 
            new File(System.getProperty("user.dir"), "bootstrap.jar");
        if (bootstrapJar.exists()) {
            try {
                System.setProperty
                    ("catalina.home", 
                     (new File(System.getProperty("user.dir"), ".."))
                     .getCanonicalPath());
            } catch (Exception e) {
                // Ignore
                System.setProperty("catalina.home",
                                   System.getProperty("user.dir"));
            }
        } else {
            System.setProperty("catalina.home",
                               System.getProperty("user.dir"));
        }

    }


    /**
     * Get the value of the catalina.home environment variable.
     */
    public static String getCatalinaHome() {
        return System.getProperty("catalina.home",
                                  System.getProperty("user.dir"));
    }


    /**
     * Get the value of the catalina.base environment variable.
     */
    public static String getCatalinaBase() {
        return System.getProperty("catalina.base", getCatalinaHome());
    }


    /**
     * Log a debugging detail message.
     *
     * @param message The message to be logged
     */
    protected static void log(String message) {

        System.out.print("Bootstrap: ");
        System.out.println(message);

    }


    /**
     * Log a debugging detail message with an exception.
     *
     * @param message The message to be logged
     * @param exception The exception to be logged
     */
    protected static void log(String message, Throwable exception) {

        log(message);
        exception.printStackTrace(System.out);

    }


}
