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
//$Archive: /SOFIA/SourceCode/com/salmonllc/ideTools/ProjectDialog.java $
//$Author: Dan $
//$Revision: 33 $
//$Modtime: 9/24/04 3:51p $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.xml.DOMParser;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.w3c.dom.*;

/**
 * @author  demian
 */
public class ProjectDialog extends JDialog implements ActionListener, WindowListener {

    boolean _cancelClicked = true;
    JButton _create;
    JButton _cancel;
    JCheckBox _includeFrameworkSource;
    JCheckBox _includeTomcatSource;
    JCheckBox _setAsDefaultProject;
    JCheckBox _specifyDBSettings;
    JFrame _owner;
    Props _props;
    int _fieldIndex = 0;
    int _noFields = 10;
    /**
	 * @uml.property  name="_fields"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
    Field _fields[];
    Box _mainGrid;
    String _currentSourceDir;
    String _intelliJDir;
    String _intelliJDirFwd;
    String _tomcatDir;
    String _tomcatFwdDir;
    String _jdkdir;
    String _jdkname;
    String _projectname;
    String _frameworkSourceDir;
    String _frameworkResourcesDir;
    String _dreamweaverDir;
    File[] _jarFiles;
    File[] _salmonTemplates;
    File _resourcesDir;
    String _runInBrowser;
    Hashtable _dbsettings;
    int _intelliJVersion = 2;

    private static String _tomcatsource2 = "<root file=\"file://$TOMCATFWD$/src/jasper/src/share\">\r\n  <property name=\"type\" value=\"sourcePathEntry\" />\r\n</root>\r\n<root file=\"file://$TOMCATFWD$/src/catalina/src/share\">\r\n  <property name=\"type\" value=\"sourcePathEntry\" />\r\n</root>";
    private static String _frameworksource2 = "<root file=\"file://$FRAMEWORKSOURCEDIR$\">\r\n  <property name=\"type\" value=\"projectFiles\" />\r\n</root>\r\n<root file=\"file://$FRAMEWORKSOURCEDIR$\">\r\n  <property name=\"type\" value=\"sourcePathEntry\" />\r\n</root>";
    private static String _browserconfiguration2 = "<configuration name=\"Run in $BROWSER$\" type=\"Application\" default=\"false\" selected=\"true\">\r\n  <option name=\"MAIN_CLASS_NAME\" value=\"com.salmonllc.ideTools.IDETool\" />\r\n  <option name=\"VM_PARAMETERS\" value=\"-Dcatalina.home=&quot;$TOMCAT$&quot; -Djava.io.tmpdir=&quot;$TOMCAT$\\temp&quot; -Dsalmon.props.path=&quot;$TOMCAT$\\salmonprops&quot;\" />\r\n  <option name=\"PROGRAM_PARAMETERS\" value=\"-RESTART -RUNBROWSER $BROWSER$ -PROJECT $PROJECT$\" />\r\n  <option name=\"WORKING_DIRECTORY\" value=\"$PROJECT_DIR$\" />\r\n</configuration>\r\n";
    private static String _jdbcjarclasspathentry2 = "<root file=\"jar://$JDBCJARFILE$!/\">\r\n  <property name=\"type\" value=\"classPathEntry\" />\r\n</root>";
    private static String _jdbcdirclasspathentry2 = "<root file=\"file://$JDBCJARFILE$/\">\r\n  <property name=\"type\" value=\"classPathEntry\" />\r\n</root>";

    private static String _browserconfiguration34 = "<configuration name=\"Run in $BROWSER$\" type=\"Application\" default=\"false\" selected=\"true\">\r\n  <option name=\"MAIN_CLASS_NAME\" value=\"com.salmonllc.ideTools.IDETool\" />\r\n  <option name=\"VM_PARAMETERS\" value=\"-Dcatalina.home=&quot;$TOMCAT$&quot; -Djava.io.tmpdir=&quot;$TOMCAT$\\temp&quot; -Dsalmon.props.path=&quot;$TOMCAT$\\salmonprops&quot;\" />\r\n  <option name=\"PROGRAM_PARAMETERS\" value=\"-RESTART -RUNBROWSER $BROWSER$ -PROJECT $PROJECT$\" />\r\n  <option name=\"WORKING_DIRECTORY\" value=\"$PROJECT_DIR$\" />\r\n</configuration>\r\n";
    private static String _frameworksource3 = "<root type=\"simple\" url=\"file://$FRAMEWORKSOURCEDIR$\" />";
    private static String _tomcatsource3 = "<root type=\"simple\" url=\"file://$TOMCATFWD$/src/jasper/src/share\" />\r\n<root type=\"simple\" url=\"file://$TOMCATFWD$/src/catalina/src/share\" />\r\n";
    private static String _jdbcjarclasspathentry3 = "<root type=\"simple\" url=\"jar://$JDBCJARFILE$!/\" />\r\n";
    private static String _jdbcdirclasspathentry3 = "<root type=\"simple\" url=\"file://$JDBCJARFILE$/\" />\r\n";

    private static String _frameworksource4 = " <content url=\"file://$FRAMEWORKSOURCEDIR$\">\n" + "          <sourceFolder url=\"file://$FRAMEWORKSOURCEDIR$\" isTestSource=\"false\" />\n" + " </content>";
    private static String _tomcatsource4 = "<content url=\"file://$TOMCATFWD$/src/jasper/src/share\" />\r\n<content url=\"file://$TOMCATFWD$/src/catalina/src/share\" />\r\n";
    private static String _jdbcjarclasspathentry4 = "<root url=\"jar://$JDBCJARFILE$!/\" />\r\n";
    private static String _jdbcdirclasspathentry4 = "<root url=\"file://$JDBCJARFILE$/\" />\r\n";


    private static String _tomcatsource = null;
    private static String _frameworksource = null;
    private static String _browserconfiguration = null;
    private static String _jdbcjarclasspathentry = null;
    private static String _jdbcdirclasspathentry = null;

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_PW = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_DIR = 3;
    public static final int TYPE_DIR_FILE = 4;
    public static final int TYPE_CHECKBOX = 5;
    public static final int TYPE_CHECKBOXWITHACTION = 6;

    private class Field {
        public JLabel caption;
        public JComponent field;
        public String property;
        public JComponent lookup;
        public int type;
    }

    public ProjectDialog(JFrame owner, Props p) {
        super(owner, "Create Project", true);
        _owner = owner;
        _props = p;

        _intelliJDir = p.getProperty(Props.IDE_INTELLIJ_PATH);
        _intelliJDir.replace('/', File.separatorChar);
        if (!_intelliJDir.endsWith(File.separator))
            _intelliJDir = _intelliJDir + File.separator;
        _intelliJDirFwd = _intelliJDir;
        _intelliJDirFwd.replace(File.separatorChar,'/');
        getJDKInformation();
        if (_intelliJVersion == 2) {
            _tomcatsource = _tomcatsource2;
            _frameworksource = _frameworksource2;
            _browserconfiguration = _browserconfiguration2;
            _jdbcjarclasspathentry = _jdbcjarclasspathentry2;
            _jdbcdirclasspathentry = _jdbcdirclasspathentry2;
        } else if (_intelliJVersion == 3) {
            _tomcatsource = _tomcatsource3;
            _frameworksource = _frameworksource3;
            _browserconfiguration = _browserconfiguration34;
            _jdbcjarclasspathentry = _jdbcjarclasspathentry3;
            _jdbcdirclasspathentry = _jdbcdirclasspathentry3;
        } else if (_intelliJVersion == 4) {
            _tomcatsource = _tomcatsource4;
            _frameworksource = _frameworksource4;
            _browserconfiguration = _browserconfiguration34;
            _jdbcjarclasspathentry = _jdbcjarclasspathentry4;
            _jdbcdirclasspathentry = _jdbcdirclasspathentry4;
        }

        Vector browserProps = new Vector();
        Enumeration en = p.getKeys();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            if (key.startsWith(Props.IDE_BROWSER_PATH + ".")) {
                browserProps.add(key);
                _noFields++;
            }
        }

        StringBuffer sbRunInBrowsers = new StringBuffer();
        for (int i = 0; i < browserProps.size(); i++) {
            String key = (String) browserProps.elementAt(i);
            String browser = key.substring(Props.IDE_BROWSER_PATH.length() + 1);
            StringBuffer sbBrowser = new StringBuffer(_browserconfiguration);
            while (true) {
                int iBrowserIndex = sbBrowser.toString().indexOf("$BROWSER$");
                if (iBrowserIndex < 0)
                    break;
                sbBrowser.replace(iBrowserIndex, iBrowserIndex + 9, browser);
            }
            sbRunInBrowsers.append(sbBrowser);
        }
        _runInBrowser = sbRunInBrowsers.toString();

        _tomcatDir = p.getProperty(Props.IDE_TOMCAT_PATH);
        _tomcatDir = _tomcatDir.replace('/', File.separatorChar);
        if (_tomcatDir.endsWith(File.separator)) {
            _tomcatDir = _tomcatDir.substring(0, _tomcatDir.length() - 1);
        }
        _tomcatFwdDir = _tomcatDir.replace(File.separatorChar, '/');
        _frameworkSourceDir = p.getProperty(Props.IDE_FRAMEWORK_SOURCE_PATH);
        _frameworkSourceDir = _frameworkSourceDir.replace('/', File.separatorChar);
        if (_frameworkSourceDir.endsWith(File.separator)) {
            _frameworkSourceDir = _frameworkSourceDir.substring(0, _frameworkSourceDir.length() - 1);
        }
        _frameworkSourceDir = _frameworkSourceDir.replace(File.separatorChar, '/');
        _frameworkResourcesDir = p.getProperty(Props.IDE_FRAMEWORK_RESOURCES_PATH);
        _resourcesDir = new File(_frameworkResourcesDir);
        if (!_resourcesDir.exists()) {
            displayError("Framework has not been properly installed. Missing Resources Directory " + _resourcesDir.getAbsolutePath() + ".");
            return;
        }

        if (_intelliJVersion == 2) {
            _salmonTemplates = new File[1];
            _salmonTemplates[0] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon.ipr");
            if (!_salmonTemplates[0].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[0].getAbsolutePath() + ".");
                return;
            }
        } else if (_intelliJVersion == 3) {
            _salmonTemplates = new File[2];
            _salmonTemplates[0] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon3.ipr");
            _salmonTemplates[1] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon3.iws");
            if (!_salmonTemplates[0].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[0].getAbsolutePath() + ".");
                return;
            }
            if (!_salmonTemplates[1].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[1].getAbsolutePath() + ".");
                return;
            }

        } else if (_intelliJVersion == 4) {
            _salmonTemplates = new File[3];
            _salmonTemplates[0] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon4.ipr");
            _salmonTemplates[1] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon4.iws");
            _salmonTemplates[2] = new File(_resourcesDir + File.separator + "IntelliJ" + File.separator + "salmon4.iml");

            if (!_salmonTemplates[0].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[0].getAbsolutePath() + ".");
                return;
            }
            if (!_salmonTemplates[1].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[1].getAbsolutePath() + ".");
                return;
            }
            if (!_salmonTemplates[2].exists()) {
                displayError("Framework has not been properly installed. Missing Salmon Template " + _salmonTemplates[2].getAbsolutePath() + ".");
                return;
            }
        }


        _dreamweaverDir = p.getProperty(Props.IDE_DREAMWEAVER_PATH);
        _dreamweaverDir.replace('/', File.separatorChar);
        int iSlashIndex = _dreamweaverDir.lastIndexOf(File.separator);
        _dreamweaverDir = _dreamweaverDir.substring(0, iSlashIndex);

        int width = 600;
        int height = 150 + (30 * 3);
        Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (frameBounds.width - width) / 2;
        int y = (frameBounds.height - height) / 2;
        setBounds(x, y, width, height);

        _fields = new Field[_noFields];
        _mainGrid = Box.createVerticalBox();

        addFieldToGrid("ProjectName", "Project Name", TYPE_TEXT);
        addFieldToGrid(Props.IDE_DEFAULT_SOURCE_PATH, "Source Code Path", TYPE_DIR);
        _includeFrameworkSource = (JCheckBox) addFieldToGrid("IncludeFrameworkSource", "Include Framework Source", TYPE_CHECKBOX);
        //    _mainGrid.add("IncludeFrameworkSource",_includeFrameworkSource=new JCheckBox("Include Framework Source",false));
        _includeTomcatSource = (JCheckBox) addFieldToGrid("IncludeTomcatSource", "Include Tomcat Source", TYPE_CHECKBOX);
        //    _mainGrid.add("IncludeTomcatSource",_includeTomcatSource=new JCheckBox("Include Tomcat Source",false));
        _setAsDefaultProject = (JCheckBox) addFieldToGrid("SetAsDefaultProject", "Set As Default Project", TYPE_CHECKBOX);
        _specifyDBSettings = (JCheckBox) addFieldToGrid("SpecifyDBSettings", "Specify Database Settings", TYPE_CHECKBOXWITHACTION);
        _specifyDBSettings.addActionListener(this);
        _create = new JButton("Create");
        _create.addActionListener(this);
        _cancel = new JButton("Cancel");
        _cancel.addActionListener(this);
        JPanel buttonBar = new JPanel(new FlowLayout());
        buttonBar.add(_create);
        buttonBar.add(_cancel);
        buttonBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box box = Box.createVerticalBox();

        box.add(Box.createRigidArea(new Dimension(100, 10)));
        box.add(_mainGrid);
        box.add(Box.createRigidArea(new Dimension(100, 5)));
        box.add(buttonBar);

        getContentPane().add(box);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _cancel) {
            _cancelClicked = true;
            setVisible(false);
        } else if (e.getSource() == _create) {
            boolean propsInApp=Props.getSystemProps().getBooleanProperty(Props.IDE_USE_APP_PROPERTIES,false);
            Field fProjectName = _fields[0];
            _projectname = ((JTextField) fProjectName.field).getText();
            if (_projectname == null || _projectname.trim().equals("")) {
                displayError("Project Name must have a value.");
                return;
            } else {
                File fProject = new File(_intelliJDir + _projectname + ".ipr");
                if (fProject.exists()) {
                    displayError("Project " + _projectname + " already exists.");
                    return;
                }
            }
            Field fSourceDir = _fields[1];
            _currentSourceDir = ((JTextField) fSourceDir.field).getText();
            if (_currentSourceDir == null || _currentSourceDir.trim().equals("")) {
                displayError("Source Code Path must have a value.");
                return;
            } else {
                _currentSourceDir = new File(_currentSourceDir).getAbsolutePath();
                File fProjectDir = new File(_currentSourceDir + File.separator + _projectname);
                System.out.println(_currentSourceDir);
                if (fProjectDir.exists()) {
                    if (!fProjectDir.isDirectory()) {
                        displayError("Cannot create a directory for Project " + _projectname + " because a file with that name exists.");
                        return;
                    }
                    ;
                } else {
                    fProjectDir.mkdirs();
                }
            }
            _currentSourceDir = _currentSourceDir.replace('/', File.separatorChar);
            if (_currentSourceDir.endsWith(File.separator))
                _currentSourceDir = _currentSourceDir.substring(0, _currentSourceDir.length() - 1);
            _currentSourceDir = _currentSourceDir.replace(File.separatorChar, '/');
            if (_specifyDBSettings.isSelected()) {
                if (_dbsettings == null) {
                    displayError("Database Settings were not specified.");
                    return;
                }
                _jarFiles = (File[]) _dbsettings.get("JDBCJarFile");
            } else
                _jarFiles = null;

            //do a serach replace of the template files

            for (int j = 0; j < _salmonTemplates.length; j++) {
                byte[] baSalmonTemplate = new byte[(int) _salmonTemplates[j].length()];
                try {
                    FileInputStream fisSalmonTemplate = new FileInputStream(_salmonTemplates[j]);
                    fisSalmonTemplate.read(baSalmonTemplate);
                    fisSalmonTemplate.close();
                } catch (Exception ex) {
                    displayError("Framework has not been properly installed. Missing " + _salmonTemplates[j].getAbsolutePath() + ".");
                    return;
                }
                String sTemplate = new String(baSalmonTemplate);
                if (_jarFiles != null) {
                    StringBuffer sbClassPathEntries = new StringBuffer();
                    for (int i = 0; i < _jarFiles.length; i++) {
                        String sFile = _jarFiles[i].getAbsolutePath();
                        sFile.replace(File.separatorChar, '/');
                        if (sFile.toLowerCase().endsWith(".jar") || sFile.toLowerCase().endsWith(".zip")) {
                            String sJarEntry = _jdbcjarclasspathentry;
                            sJarEntry = replace(sJarEntry, "$JDBCJARFILE$", sFile);
                            sbClassPathEntries.append(sJarEntry);
                            sbClassPathEntries.append("\r\n");
                        } else {
                            String sDirEntry = _jdbcdirclasspathentry;
                            sDirEntry = replace(sDirEntry, "$JDBCJARFILE$", sFile);
                            sbClassPathEntries.append(sDirEntry);
                            sbClassPathEntries.append("\r\n");
                        }
                    }
                    sTemplate = replace(sTemplate, "$JDBCCLASSPATHENTRY$", sbClassPathEntries.toString());
                } else {
                    sTemplate = replace(sTemplate, "$JDBCCLASSPATHENTRY$", "");
                }
                if (_runInBrowser != null && !_runInBrowser.trim().equals("")) {
                    int pos = _runInBrowser.indexOf("$PROJECT$");
                    while (pos > -1) {
                        _runInBrowser = replace(_runInBrowser, "$PROJECT$", _projectname);
                        pos = _runInBrowser.indexOf("$PROJECT$");
                    }
                    sTemplate = replace(sTemplate, "$RUNINBROWSERS$", _runInBrowser);
                } else {
                    sTemplate = replace(sTemplate, "$RUNINBROWSERS$", "");
                }
                if (_includeTomcatSource.isSelected()) {
                    sTemplate = replace(sTemplate, "$TOMCATSOURCE$", _tomcatsource);
                } else {
                    sTemplate = replace(sTemplate, "$TOMCATSOURCE$", "");
                }
                if (_includeFrameworkSource.isSelected()) {
                    sTemplate = replace(sTemplate, "$FRAMEWORKSOURCE$", _frameworksource);
                } else {
                    sTemplate = replace(sTemplate, "$FRAMEWORKSOURCE$", "");
                }
                sTemplate = replace(sTemplate, "$TOMCAT$", _tomcatDir);
                sTemplate = replace(sTemplate, "$JDKNAME$", _jdkname);
                sTemplate = replace(sTemplate, "$TOMCATFWD$", _tomcatFwdDir);
                sTemplate = replace(sTemplate, "$JDKPATH$", _jdkdir);
                sTemplate = replace(sTemplate, "$SOURCEDIR$", _currentSourceDir);
                sTemplate = replace(sTemplate, "$PROJECTNAME$", _projectname);
                sTemplate = replace(sTemplate, "$FRAMEWORKSOURCEDIR$", _frameworkSourceDir);
                sTemplate = replace(sTemplate, "$INTELLIJDIR$", _intelliJDirFwd);
                try {
                    String ext = _salmonTemplates[j].getName();
                    int pos = ext.indexOf(".");
                    ext = ext.substring(pos);
                    FileOutputStream fosProject = new FileOutputStream(_intelliJDir + _projectname + ext);
                    fosProject.write(sTemplate.getBytes());
                    fosProject.close();
                } catch (Exception ex) {
                    displayError("Unable to create project " + _projectname);
                    return;
                }
            }

            File fTomcatProjectJspDir = new File(_tomcatDir + File.separator + "webapps" + File.separator + _projectname + File.separator + "Jsp");
            File fTomcatProjectWebInfDir = new File(_tomcatDir + File.separator + "webapps" + File.separator + _projectname + File.separator + "WEB-INF");
            File fTomcatProjectWebInfClassesDir = new File(_tomcatDir + File.separator + "webapps" + File.separator + _projectname + File.separator + "WEB-INF" + File.separator + "classes");
            File fTomcatProjectWebInfPropsDir = new File(_tomcatDir + File.separator + "webapps" + File.separator + _projectname + File.separator + "WEB-INF" + File.separator + "properties");
            if (!fTomcatProjectJspDir.exists())
                fTomcatProjectJspDir.mkdirs();
            if (!fTomcatProjectWebInfDir.exists())
                fTomcatProjectWebInfDir.mkdirs();
            if (!fTomcatProjectWebInfClassesDir.exists())
                fTomcatProjectWebInfClassesDir.mkdirs();
            if (propsInApp) {
                if (!fTomcatProjectWebInfPropsDir.exists())
                    fTomcatProjectWebInfPropsDir.mkdirs();
            }
            File fTomcatResources = new File(_resourcesDir.getAbsolutePath() + File.separator + "Tomcat");

            File fJsp = new File(_resourcesDir.getAbsolutePath() + File.separator + "jsp");
            try {
                copyFiles(fTomcatResources, fTomcatProjectWebInfDir);
                copyFiles(fJsp, fTomcatProjectJspDir);
            } catch (Exception ex) {
                displayError("Unable to create resources.");
                return;
            }
            if (_setAsDefaultProject.isSelected()) {
                try {
                    IDETool.createDreamweaverProject(_resourcesDir.getAbsolutePath(), _projectname, _dreamweaverDir, "localhost:8080", _tomcatDir + File.separator + "webapps" + File.separator + _projectname);
                } catch (Exception ex) {
                    displayError("Error creating Dreamweaver project" + ex.getMessage());
                }
            }

            Hashtable htPropChanges = new Hashtable();
            htPropChanges.put(_projectname + "." + Props.IDE_DEFAULT_FRAMEWORK_APP, _projectname);
            htPropChanges.put(_projectname + "." + Props.IDE_WEB_APP, _projectname);
            htPropChanges.put(_projectname + "." + Props.IDE_DEFAULT_RUN_URL, "http://localhost:8080/" + _projectname + "/Jsp/hello.jsp");
            if (propsInApp)
                htPropChanges.put(_projectname + "." + Props.IDE_APP_PROPS_PATH, fTomcatProjectWebInfPropsDir.getAbsolutePath());
            changeProperties(htPropChanges);

            String sDriver = "";
            String sConnectURL = "";
            String sUser = "";
            String sPassword = "";
            if (_specifyDBSettings.isSelected()) {
                sDriver = (String) _dbsettings.get("JDBCDriver");
                sConnectURL = (String) _dbsettings.get("JDBCConnectUrl");
                sUser = (String) _dbsettings.get("JDBCUser");
                sPassword = (String) _dbsettings.get("JDBCPassword");
            }
            File fSalmonProperties=null;
            if (propsInApp)
                fSalmonProperties = new File(_resourcesDir.getAbsolutePath() + File.separator + "salmonprops" + File.separator + "salmonFull.properties");
            else
                fSalmonProperties = new File(_resourcesDir.getAbsolutePath() + File.separator + "salmonprops" + File.separator + "salmon.properties");
            byte[] baPropertiesTemplate = new byte[(int) fSalmonProperties.length()];
            try {
                FileInputStream fisProperties = new FileInputStream(fSalmonProperties);
                fisProperties.read(baPropertiesTemplate);
                fisProperties.close();
            } catch (Exception ex) {
                displayError("Framework has not been properly installed. Missing " + fSalmonProperties.getAbsolutePath() + ".");
                return;
            }
            String sProperties = new String(baPropertiesTemplate);
            sProperties = replace(sProperties, "$DRIVER$", sDriver);
            sProperties = replace(sProperties, "$CONNECTURL$", sConnectURL);
            sProperties = replace(sProperties, "$DBUSER$", sUser);
            sProperties = replace(sProperties, "$DBPASSWORD$", sPassword);
            sProperties = replace(sProperties, "$TOMCAT$", fixSlashes(_tomcatDir));
            sProperties = replace(sProperties, "$PROJECTNAME$", _projectname);
            try {
                String propName=propsInApp?"System":_projectname;
                String propPath=propsInApp?fTomcatProjectWebInfPropsDir.getAbsolutePath():Props.getPropsPath();
                File fWritePropertiesFile = new File(propPath + File.separator + propName + ".properties");
                FileOutputStream fosProperties = new FileOutputStream(fWritePropertiesFile);
                fosProperties.write(sProperties.getBytes());
                fosProperties.close();
            } catch (Exception ex) {
                displayError("Unable to create properties file.");
                return;
            }
            _cancelClicked = false;
            displayMessage("Use File->Open Project to open " + _projectname + " project.");
            setVisible(false);
        } else if (e.getSource() == _specifyDBSettings) {
            _fields[5].lookup.setEnabled(_specifyDBSettings.isSelected());
        } else {
            Field f = null;
            for (int i = 0; i < _noFields; i++) {
                if (_fields[i].lookup != null && _fields[i].lookup == e.getSource()) {
                    f = _fields[i];
                    break;
                }
            }
            if (f != null) {
                if (f.field instanceof JCheckBox) {
                    JFrame frame = new JFrame();
                    DatabaseDialog dd = new DatabaseDialog(frame, _dbsettings);
                    _dbsettings = dd.getDBSettings();
                } else {
                    String fileName = ((JTextField) f.field).getText();
                    JFileChooser c = new JFileChooser();
                    if (f.type == TYPE_FILE)
                        c.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    else if (f.type == TYPE_DIR)
                        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    else
                        c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    c.setCurrentDirectory(new File(fileName));
                    if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        if (c.getSelectedFile().exists())
                            ((JTextField) f.field).setText(c.getSelectedFile().getAbsolutePath());
                        else {
                            String sFile = c.getSelectedFile().getAbsolutePath();
                            int iSlashIndex = sFile.lastIndexOf(File.separator);
                            sFile = sFile.substring(0, iSlashIndex);
                            ((JTextField) f.field).setText(sFile);
                        }
                    }
                }
            }
        }
    }

    private JComponent addFieldToGrid(String property, String label, int type) {
        Field f = new Field();
        f.property = property;
        Dimension capSize = new Dimension(180, 25);
        Dimension fieldSize = new Dimension(350, 25);
        Dimension buttonSize = new Dimension(30, 25);
        Dimension lookupSize = new Dimension(320, 25);
        Dimension actionSize = new Dimension(290, 25);

        f.type = type;
        if (type != TYPE_CHECKBOX && type != TYPE_CHECKBOXWITHACTION)
            f.caption = DialogComponentFactory.makeLabel(label + ":");
        else
            f.caption = DialogComponentFactory.makeLabel("");
        f.caption.setMaximumSize(capSize);
        f.caption.setMinimumSize(capSize);
        Box b = Box.createHorizontalBox();
        b.add(f.caption);

        if (type == TYPE_TEXT) {
            f.field = new JTextField(_props.getProperty(property));
            f.field.setMaximumSize(fieldSize);
            f.field.setMinimumSize(fieldSize);
            b.add(f.field);
            _mainGrid.add(b);
            _mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
        } else if (type == TYPE_PW) {
            f.field = new JPasswordField(_props.getProperty(property));
            f.field.setMaximumSize(fieldSize);
            f.field.setMinimumSize(fieldSize);
            b.add(f.field);
            _mainGrid.add(b);
            _mainGrid.add(Box.createRigidArea(new Dimension(255, 3)));
        } else if (type == TYPE_FILE || type == TYPE_DIR) {
            f.field = new JTextField(_props.getProperty(property));
            f.field.setMaximumSize(lookupSize);
            f.field.setMinimumSize(lookupSize);

            f.lookup = new JButton("..");
            ((JButton) f.lookup).addActionListener(this);
            ((JButton) f.lookup).setMaximumSize(buttonSize);
            ((JButton) f.lookup).setMinimumSize(buttonSize);
            b.add(f.field);
            b.add(f.lookup);
            _mainGrid.add(b);
        } else if (type == TYPE_CHECKBOX) {
            f.field = new JCheckBox(label);
            f.field.setMaximumSize(lookupSize);
            f.field.setMinimumSize(lookupSize);
            b.add(f.field);
            b.add(Box.createRigidArea(buttonSize));
            _mainGrid.add(b);
        } else if (type == TYPE_CHECKBOXWITHACTION) {
            f.field = new JCheckBox(label);
            f.field.setMaximumSize(actionSize);
            f.field.setMinimumSize(actionSize);
            f.lookup = new JButton("Set");
            f.lookup.setEnabled(false);
            //        ((JButton)f.lookup).setBorder(new LineBorder(java.awt.Color.black,1));
            //        ((JButton)f.lookup).setBorder(new BevelBorder(BevelBorder.LOWERED));
            //        ((JButton)f.lookup).getBorder().getBorderInsets((JButton)f.lookup).bottom=0;
            //        ((JButton)f.lookup).getBorder().getBorderInsets((JButton)f.lookup).left=0;
            //        ((JButton)f.lookup).getBorder().getBorderInsets((JButton)f.lookup).right=0;
            //        ((JButton)f.lookup).getBorder().getBorderInsets((JButton)f.lookup).top=0;
            ((JButton) f.lookup).addActionListener(this);
            ((JButton) f.lookup).setMaximumSize(new Dimension(60, 25));
            ((JButton) f.lookup).setMinimumSize(new Dimension(60, 25));
            b.add(f.field);
            b.add(f.lookup);
            _mainGrid.add(b);
        }

        _fields[_fieldIndex++] = f;
        return f.field;
    }

    /**
     * Pops up a change dialog and saves changes to the System.properties file
     */
    protected static void changeProperties(Hashtable changes) {

        if (changes != null) {
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
            }

        }

    }

    private void copyFiles(File fFromDir, File fToDir) throws Exception {
        if (!fToDir.exists())
            fToDir.mkdirs();
        File[] fFiles = fFromDir.listFiles();
        for (int i = 0; i < fFiles.length; i++) {
            if (fFiles[i].isDirectory()) {
                File fDestTo = new File(fToDir.getAbsolutePath() + File.separator + fFiles[i].getName());
                copyFiles(fFiles[i], fDestTo);
            } else {
                byte[] baFile = new byte[(int) fFiles[i].length()];
                FileInputStream fis = new FileInputStream(fFiles[i]);
                fis.read(baFile);
                fis.close();
                File fDestFile = new File(fToDir.getAbsolutePath() + File.separator + fFiles[i].getName());
                FileOutputStream fos = new FileOutputStream(fDestFile);
                fos.write(baFile);
            }
        }
    }

    private static void displayError(String error) {
        System.out.println("Error Display:" + error);
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, error, "Error", JOptionPane.ERROR_MESSAGE);
        f.dispose();
    }

    private static void displayMessage(String message) {
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, message, "Information", JOptionPane.INFORMATION_MESSAGE);
        f.dispose();
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

    /**
     * Returns true if the user clicked the cancel button to exit the dialog
     */
    public boolean getCancel() {
        return _cancelClicked;
    }

    private void getJDKInformation() {
        String sJdkXml = _intelliJDir + "config" + File.separator + "options" + File.separator + "jdk.table.xml";
        String sKeyFile = _intelliJDir + "config" + File.separator + "idea40.key";
        DOMParser dp = new DOMParser();
        try {
            Document doc = dp.parse(sJdkXml);
            NodeList nlApplications = doc.getChildNodes();
            Node nApplication = nlApplications.item(0);
            NodeList nlComponents = nApplication.getChildNodes();
            Node nComponent = nlComponents.item(1);
            NodeList nlJDK = nComponent.getChildNodes();
            for (int i = 1; i < nlJDK.getLength(); i++) {
                Node nJDK = nlJDK.item(i);
                if (nJDK.hasAttributes()) {
                    NamedNodeMap nnm = nJDK.getAttributes();
                    Node nDefault = nnm.getNamedItem("default");
                    //                String sValue=nDefault.getFirstChild().getNodeValue();
                    String sValue = "true";
                    if (nDefault != null)
                        sValue = nDefault.getNodeValue();
                    System.out.println(sValue);
                    if (sValue.equals("true")) {
                        NodeList nlJdkNodes = nJDK.getChildNodes();
                        for (int k = 1; k < nlJdkNodes.getLength(); k++) {
                            Node nJdkNode = nlJdkNodes.item(k);
                            if (nJdkNode.getNodeName().equals("name")) {
                                NamedNodeMap nnmJdkName = nJdkNode.getAttributes();
                                Node nValue = nnmJdkName.getNamedItem("value");
                                //                            _jdkname=nValue.getFirstChild().getNodeValue();
                                _jdkname = nValue.getNodeValue();
                                _jdkname = replace(_jdkname, "\"", "&quot;");
                            }
                            if (nJdkNode.getNodeName().equals("homePath")) {
                                NamedNodeMap nnmHomePath = nJdkNode.getAttributes();
                                Node nValue = nnmHomePath.getNamedItem("value");
                                _jdkdir = nValue.getNodeValue();
                                if (_jdkdir.endsWith(File.separator))
                                    _jdkdir = _jdkdir.substring(0, _jdkdir.length() - 1);
                                _jdkdir = _jdkdir.replace(File.separatorChar, '/');
                                _intelliJVersion = 3;
                                File f = new File(sKeyFile);
                                if (f.exists())
                                    _intelliJVersion=4;
                            }
                            if (nJdkNode.getNodeName().equals("roots")) {
                                NodeList nlRoot = nJdkNode.getChildNodes();
                                Node nRoot = nlRoot.item(1);
                                NamedNodeMap nnmRoot = nRoot.getAttributes();
                                Node nFile = nnmRoot.getNamedItem("file");
                                //                            _jdkdir=nFile.getFirstChild().getNodeValue();
                                if (nFile != null) {
                                    _jdkdir = nFile.getNodeValue();
                                    _jdkdir = _jdkdir.substring(7);
                                    _jdkdir = _jdkdir.replace('/', File.separatorChar);
                                    if (_jdkdir.endsWith(File.separator))
                                        _jdkdir = _jdkdir.substring(0, _jdkdir.length() - 1);
                                    _jdkdir = _jdkdir.replace(File.separatorChar, '/');
                                    _intelliJVersion = 2;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(_intelliJVersion + " " + _jdkname + " " + _jdkdir);
    }

    /**
     * Returns a Hashtable of items changed by the user
     */
    public String getSourceDir() {
        return _currentSourceDir;
    }

    private String replace(String sString, String sOldString, String sNewString) {
        StringBuffer sbString = new StringBuffer(sString);
        while (true) {
            int iIndex = sbString.toString().indexOf(sOldString);
            if (iIndex < 0)
                break;
            sbString.replace(iIndex, iIndex + sOldString.length(), sNewString);
        }
        return sbString.toString();
    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}
