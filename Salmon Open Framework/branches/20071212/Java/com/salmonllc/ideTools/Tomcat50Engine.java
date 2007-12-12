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


import com.salmonllc.util.MessageLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.catalina.Container;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.ClusterRuleSet;
import org.apache.catalina.startup.ContextRuleSet;
import org.apache.catalina.startup.Embedded;
import org.apache.catalina.startup.EngineRuleSet;
import org.apache.catalina.startup.HostRuleSet;
import org.apache.catalina.startup.NamingRuleSet;
import org.apache.catalina.startup.SetAllPropertiesRule;
import org.apache.catalina.util.CatalinaDigester;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;


/**
 * Startup/Shutdown shell program for Catalina.  The following command line options are recognized: <ul> <li><b>-config {pathname}</b> - Set the pathname of the configuration file to be processed.  If a relative path is specified, it will be interpreted as relative to the directory pathname specified by the "catalina.base" system property.   [conf/server.xml] <li><b>-help</b> - Display usage information. <li><b>-stop</b> - Stop the currently running instance of Catalina. </u> Should do the same thing as Embedded, but using a server.xml file.
 * @author  Craig R. McClanahan
 * @author  Remy Maucherat
 * @version  $Revision: 1 $ $Date: 8/05/04 2:55p $
 */

public class Tomcat50Engine extends Embedded {


    // ----------------------------------------------------- Instance Variables

    /**
     * Pathname to the server configuration file.
     */
    protected String _configFile = "conf/server.xml";
 
    // XXX Should be moved to embedded
    /**
     * The shared extensions class loader for this server.
     */
    protected ClassLoader _parentClassLoader =
        Tomcat50Engine.class.getClassLoader();


    /**
     * The server component we are starting or stopping
     */
    protected Server _server = null;


    /**
     * Are we starting a new server?
     */
    protected boolean _starting = false;


    /**
     * Are we stopping an existing server?
     */
    protected boolean _stopping = false;


    /**
     * Use shutdown hook flag.
     */
    protected boolean _useShutdownHook = true;


    /**
     * Shutdown hook.
     */
    protected Thread _shutdownHook = null;


    //Salmon Added
    private Process _browserProcess = null;
    private Tomcat50Bootstrap _bootstrap;
    private Tomcat50ShutdownProperties _shutdown;
    //End Salmon Added
    
    // ------------------------------------------------------------- Properties


    public void setConfig(String file) {
        _configFile = file;
    }


    public void setConfigFile(String file) {
        _configFile = file;
    }


    public String getConfigFile() {
        return _configFile;
    }


    public void setUseShutdownHook(boolean useShutdownHook) {
        this._useShutdownHook = useShutdownHook;
    }


    public boolean getUseShutdownHook() {
        return _useShutdownHook;
    }


    /**
     * Set the shared extensions class loader.
     *
     * @param parentClassLoader The shared extensions class loader.
     */
    public void setParentClassLoader(ClassLoader parentClassLoader) {

        this._parentClassLoader = parentClassLoader;

    }


    /**
     * Set the server instance we are configuring.
     *
     * @param server The new server
     */
    public void setServer(Server server) {

        this._server = server;

    }

    // ------------------------------------------------------ Protected Methods


    /**
     * Process the specified command line arguments, and return
     * <code>true</code> if we should continue processing; otherwise
     * return <code>false</code>.
     *
     * @param args Command line arguments to process
     */
    protected boolean arguments(String args[]) {

        boolean isConfig = false;

        if (args.length < 1) {
            usage();
            return (false);
        }

        for (int i = 0; i < args.length; i++) {
            if (isConfig) {
                _configFile = args[i];
                isConfig = false;
            } else if (args[i].equals("-config")) {
                isConfig = true;
            } else if (args[i].equals("-debug")) {
                debug = 1;
            } else if (args[i].equals("-nonaming")) {
                setUseNaming( false );
            } else if (args[i].equals("-help")) {
                usage();
                return (false);
            } else if (args[i].equals("start")) {
                _starting = true;
                _stopping = false;
            } else if (args[i].equals("stop")) {
                _starting = false;
                _stopping = true;
            } else {
                usage();
                return (false);
            }
        }

        return (true);

    }


    /**
     * Return a File object representing our configuration file.
     */
    protected File configFile() {

        File file = new File(_configFile);
        if (!file.isAbsolute())
            file = new File(System.getProperty("catalina.base"), _configFile);
        return (file);

    }


    /**
     * Create and configure the Digester we will be using for startup.
     */
    protected Digester createStartDigester() {
        long t1=System.currentTimeMillis();
        // Initialize the digester
        Digester digester = new CatalinaDigester();
        digester.setValidating(false);
        digester.setClassLoader(StandardServer.class.getClassLoader());

        // Configure the actions we will be using
        digester.addObjectCreate("Server",
                                 "org.apache.catalina.core.StandardServer",
                                 "className");
        digester.addSetProperties("Server");
        digester.addSetNext("Server",
                            "setServer",
                            "org.apache.catalina.Server");

        digester.addObjectCreate("Server/GlobalNamingResources",
                                 "org.apache.catalina.deploy.NamingResources");
        digester.addSetProperties("Server/GlobalNamingResources");
        digester.addSetNext("Server/GlobalNamingResources",
                            "setGlobalNamingResources",
                            "org.apache.catalina.deploy.NamingResources");

        digester.addObjectCreate("Server/Listener",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties("Server/Listener");
        digester.addSetNext("Server/Listener",
                            "addLifecycleListener",
                            "org.apache.catalina.LifecycleListener");

        digester.addObjectCreate("Server/Service",
                                 "org.apache.catalina.core.StandardService",
                                 "className");
        digester.addSetProperties("Server/Service");
        digester.addSetNext("Server/Service",
                            "addService",
                            "org.apache.catalina.Service");

        digester.addObjectCreate("Server/Service/Listener",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties("Server/Service/Listener");
        digester.addSetNext("Server/Service/Listener",
                            "addLifecycleListener",
                            "org.apache.catalina.LifecycleListener");

        digester.addObjectCreate("Server/Service/Connector",
                                 "org.apache.coyote.tomcat5.CoyoteConnector",
                                 "className");
        digester.addRule("Server/Service/Connector", 
                         new SetAllPropertiesRule());
        digester.addSetNext("Server/Service/Connector",
                            "addConnector",
                            "org.apache.catalina.Connector");

        digester.addObjectCreate("Server/Service/Connector/Factory",
                                 "org.apache.coyote.tomcat5.CoyoteServerSocketFactory",
                                 "className");
        digester.addSetProperties("Server/Service/Connector/Factory");
        digester.addSetNext("Server/Service/Connector/Factory",
                            "setFactory",
                            "org.apache.catalina.net.ServerSocketFactory");

        digester.addObjectCreate("Server/Service/Connector/Listener",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties("Server/Service/Connector/Listener");
        digester.addSetNext("Server/Service/Connector/Listener",
                            "addLifecycleListener",
                            "org.apache.catalina.LifecycleListener");

        // Add RuleSets for nested elements
        digester.addRuleSet(new NamingRuleSet("Server/GlobalNamingResources/"));
        digester.addRuleSet(new EngineRuleSet("Server/Service/"));
        digester.addRuleSet(new HostRuleSet("Server/Service/Engine/"));
        digester.addRuleSet(new ContextRuleSet("Server/Service/Engine/Default"));
        digester.addRuleSet(new NamingRuleSet("Server/Service/Engine/DefaultContext/"));
        digester.addRuleSet(new ContextRuleSet("Server/Service/Engine/Host/Default"));
        digester.addRuleSet(new NamingRuleSet("Server/Service/Engine/Host/DefaultContext/"));
        digester.addRuleSet(new ContextRuleSet("Server/Service/Engine/Host/"));
        digester.addRuleSet(new ClusterRuleSet("Server/Service/Engine/Host/Cluster/"));
        digester.addRuleSet(new NamingRuleSet("Server/Service/Engine/Host/Context/"));

        // When the 'engine' is found, set the parentClassLoader.
        digester.addRule("Server/Service/Engine",
                         new SetParentClassLoaderRule(_parentClassLoader));

        long t2=System.currentTimeMillis();
        log.debug("Digester for server.xml created " + ( t2-t1 ));
        return (digester);

    }


    /**
     * Create and configure the Digester we will be using for shutdown.
     */
    protected Digester createStopDigester() {

        // Initialize the digester
        Digester digester = new Digester();

        // Configure the rules we need for shutting down
        digester.addObjectCreate("Server",
                                 "com.salmonllc.ideTools.Tomcat50ShutdownProperties",
                                 "className");
        digester.addSetProperties("Server");
        digester.addSetNext("Server",
                            "setShutdownProperties",
                            "com.salmonllc.ideTools.Tomcat50ShutdownProperties");

        return (digester);

    }


    public void stopServer(String[] arguments, Tomcat50Bootstrap bootstrap) {

        if( _server == null ) {
            // Create and execute our Digester
            Digester digester = createStopDigester();
            digester.setClassLoader(Thread.currentThread().getContextClassLoader());
            File file = configFile();
            try {
                InputSource is = new InputSource("file://" + file.getAbsolutePath());
                FileInputStream fis = new FileInputStream(file);
                is.setByteStream(fis);
                digester.push(this);
                digester.parse(is);
                fis.close();
            } catch (Exception e) {
                System.out.println("Catalina.stop: " + e);
                e.printStackTrace(System.out);
                System.exit(1);
            }
        }

        // Stop the existing server
        try {
            String host = "127.0.0.1";
            int port=8005;
            String shutdown = "SHUTDOWN";

            if (_shutdown != null) {
                port=_shutdown.getPort();
                shutdown=_shutdown.getShutdown();
            }

            Socket socket = new Socket(host, port);
            OutputStream stream = socket.getOutputStream();

            for (int i = 0; i < shutdown.length(); i++)
                stream.write(shutdown.charAt(i));
            stream.flush();
            stream.close();
            socket.close();
            Thread.sleep(500);
        } catch (Exception e) {
            //System.out.println("Catalina.stop: " + e);
            //e.printStackTrace(System.out);
            //System.exit(1);
        }

        bootstrap.notifyComplete();


    }


    /**
     * Set the <code>catalina.base</code> System property to the current
     * working directory if it has not been set.
     * @deprecated Use initDirs()
     */
    public void setCatalinaBase() {
        initDirs();
    }

    /**
     * Set the <code>catalina.home</code> System property to the current
     * working directory if it has not been set.
     * @deprecated Use initDirs()
     */
    public void setCatalinaHome() {
        initDirs();
    }

    /**
     * Start a new server instance.
     */
    public void load() {
        initDirs();

        // Before digester - it may be needed

        initNaming();

        // Create and execute our Digester
        Digester digester = createStartDigester();
        long t1 = System.currentTimeMillis();

        Exception ex = null;
        InputSource inputSource = null;
        InputStream inputStream = null;
        try {
            File file = configFile();
            inputStream = new FileInputStream(file);
            inputSource = new InputSource("file://" + file.getAbsolutePath());
        } catch (Exception e) {
            ;
        }
        if (inputStream == null) {
            try {
                inputStream = getClass().getClassLoader()
                    .getResourceAsStream(getConfigFile());
                inputSource = new InputSource
                    (getClass().getClassLoader()
                     .getResource(getConfigFile()).toString());
            } catch (Exception e) {
                ;
            }
        }

        if (inputStream == null) {
            System.out.println("Can't load server.xml");
            return;
        }

        try {
            inputSource.setByteStream(inputStream);
            digester.push(this);
            digester.parse(inputSource);
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Catalina.start using "
                               + getConfigFile() + ": " + e);
            e.printStackTrace(System.out);
            return;
        }

        // Replace System.out and System.err with a custom PrintStream
        // TODO: move to Embedded, make it configurable
        SystemLogHandler systemlog = new SystemLogHandler(System.out);
        System.setOut(systemlog);
        System.setErr(systemlog);

        // Start the new server
        if (_server instanceof Lifecycle) {
            try {
                _server.initialize();
            } catch (LifecycleException e) {
                log.error("Catalina.start", e);
            }
        }

        long t2 = System.currentTimeMillis();
        log.info("Initialization processed in " + (t2 - t1) + " ms");

    }


    /* 
     * Load using arguments
     */
    public void load(String args[]) {

        setCatalinaHome();
        setCatalinaBase();
        try {
            if (arguments(args))
                load();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void create() {

    }

    public void destroy() {

    }

    /**
     * Start a new server instance.
     */
    public void start(Tomcat50Bootstrap bootstrap) {
    	_bootstrap=bootstrap;
        if (_server == null) {
            load();
        }

        long t1 = System.currentTimeMillis();

        // Start the new server
        if (_server instanceof Lifecycle) {
            try {
                ((Lifecycle) _server).start();
            } catch (LifecycleException e) {
                log.error("Catalina.start: ", e);
            }
        }

        long t2 = System.currentTimeMillis();
        log.info("Server startup in " + (t2 - t1) + " ms");

//        try {
//            // Register shutdown hook
//            if (_useShutdownHook) {
//                if (_shutdownHook == null) {
//                    _shutdownHook = new CatalinaShutdownHook();
//                }
//                Runtime.getRuntime().addShutdownHook(_shutdownHook);
//            }
//        } catch (Throwable t) {
//            // This will fail on JDK 1.2. Ignoring, as Tomcat can run
//            // fine without the shutdown hook.
//        }

        
        if (await) {
            if ( _bootstrap != null) {
                if (_bootstrap.getBrowserExe() != null)
                    runBrowser(_bootstrap.getBrowserExe(),_bootstrap.getBrowserURL());
                _bootstrap.notifyComplete();
            }
        	
            await();

            //Stop the browser process if there is one
            if (_browserProcess != null)  {
                _browserProcess.destroy();
            }
           //It takes too long to stop the server the normal way so we will just terminate the process
            System.out.println("Apache Tomcat stopped");
            System.exit(0);
        }

    }


    /**
     * Stop an existing server instance.
     */
    public void stop() {

//        try {
//            // Remove the ShutdownHook first so that server.stop() 
//            // doesn't get invoked twice
//            if (_useShutdownHook) {
//                Runtime.getRuntime().removeShutdownHook(_shutdownHook);
//            }
//        } catch (Throwable t) {
//            // This will fail on JDK 1.2. Ignoring, as Tomcat can run
//            // fine without the shutdown hook.
//        }

        // Shut down the server
//        if (server instanceof Lifecycle) {
//            try {
//                ((Lifecycle) server).stop();
//            } catch (LifecycleException e) {
//                log.error("Catalina.stop", e);
//            }
//        }
//       
//        System.exit(0);
     }


    /**
     * Await and shutdown.
     */
    public void await() {

        _server.await();

    }


    /**
     * Print usage information for this application.
     */
    protected void usage() {

        System.out.println
            ("usage: java org.apache.catalina.startup.Catalina"
             + " [ -config {pathname} ] [ -debug ]"
             + " [ -nonaming ] { start | stop }");

    }


    public void finalize() {
        if (_browserProcess != null)
            _browserProcess.destroy();
        }


     private void runBrowser(String command, String parms) {
         Runtime r = Runtime.getRuntime();
         String env[] = new String[0];

         try {
             _browserProcess = r.exec('"'+command+'"' + (parms == null ? "" : (" " + parms)));
        	 //_browserProcess = r.exec(command+' ' + (parms == null ? "" : (" " + parms)));
             IDETool.slurpProcessOutput(_browserProcess);
         } catch (IOException e) {
             MessageLog.writeErrorMessage("runBrowser",e,this);
         }
     }
     
     /**
      * Sets the shutdown properties for this server
      */
     public void setShutdownProperties(Tomcat50ShutdownProperties props) {
         _shutdown = props;
     }
    // --------------------------------------- CatalinaShutdownHook Inner Class

    // XXX Should be moved to embedded !
    /**
     * Shutdown hook which will perform a clean shutdown of Catalina if needed.
     */
    protected class CatalinaShutdownHook extends Thread {

        public void run() {

            if (_server != null) {
                Tomcat50Engine.this.stop();
            }
            
        }

    }
    
    
    private static org.apache.commons.logging.Log log=
        org.apache.commons.logging.LogFactory.getLog( Tomcat50Engine.class );

}


// ------------------------------------------------------------ Private Classes


/**
 * Rule that sets the parent class loader for the top object on the stack,
 * which must be a <code>Container</code>.
 */

final class SetParentClassLoaderRule extends Rule {

    public SetParentClassLoaderRule(ClassLoader parentClassLoader) {

        this.parentClassLoader = parentClassLoader;

    }

    ClassLoader parentClassLoader = null;

    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {

        if (digester.getLogger().isDebugEnabled())
            digester.getLogger().debug("Setting parent class loader");

        Container top = (Container) digester.peek();
        top.setParentClassLoader(parentClassLoader);

    }


}
