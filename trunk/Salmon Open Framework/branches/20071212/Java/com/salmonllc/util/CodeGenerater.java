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
package com.salmonllc.util;

import com.salmonllc.jsp.*;
import java.util.*;
import java.io.*;

/**
 * Used by the framework to generate a stub controllers for a view.
 */
public class CodeGenerater {


    private static String BASECLASS = "JspController";
    private static String INITMETHOD = "public void initialize()";
    private static String CLASSDEF = "public class";
    private static String IMPORT = "import";
    private static String PACKAGE = "package";
    private static String SPACE = " ";
    private static String IMPLEMENTS = "implements";
    private static String EXTENDS = "extends";
    private static String COMMA = ",";
    private static String EVENT_PARAMETER = "event";

    private static String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    private static final Vector INTERFACES;

    static {
        INTERFACES = new Vector();
        INTERFACES.addElement("SubmitListener");
        INTERFACES.addElement("PageListener");
    }

    private static void addListeners(PrintWriter pw) {
        // add SubmitListener
        if (INTERFACES.contains("SubmitListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the given submit event");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the submit event to be processed");
		    println(pw,  " * @return true to continue processing events, false to stop processing events");
		    println(pw,  " */");
            println(pw,  "public boolean submitPerformed(SubmitEvent " + EVENT_PARAMETER + ") {");
            println(pw,  TAB + "return true;");
            println(pw,  "}");
        }

        // Add PageListener
        if (INTERFACES.contains("PageListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the page requested event");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the page event to be processed");
		    println(pw,  " */");
            println(pw,  "public void pageRequested(PageEvent " + EVENT_PARAMETER + ") {");
            println(pw,  "}");

            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the page request end event");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the page event to be processed");
		    println(pw,  " */");
            println(pw,  "public void pageRequestEnd(PageEvent " + EVENT_PARAMETER + ") {");
            println(pw,  "}");

            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the page submit end event");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the page event to be processed");
		    println(pw,  " */");
            println(pw,  "public void pageSubmitEnd(PageEvent " + EVENT_PARAMETER + ") {");
            println(pw,  "}");

            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the page submit event");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the page event to be processed");
		    println(pw,  " */");
            println(pw,  "public void pageSubmitted(PageEvent " + EVENT_PARAMETER + "){");
            println(pw,  "}");
        }

        // add ValueChangeListener
        if (INTERFACES.contains("ValueChangedListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the value change event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " * @return true to continue processing events, false to stop processing events");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "public boolean valueChanged(ValueChangedEvent " + EVENT_PARAMETER + "){");
            println(pw,  TAB + "return true;");
            println(pw,  "}");
        }

        // add TreeListener
        if (INTERFACES.contains("TreeListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the item contracted event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "public void itemContracted(TreeExpandContractEvent " + EVENT_PARAMETER + "){");
            println(pw,  "}");

            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the item expanded event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "public void itemExpanded(TreeExpandContractEvent " + EVENT_PARAMETER + ")");
            println(pw,  "{");
            println(pw,  "}");

        }

        // FileUploadListener
        if (INTERFACES.contains("FileUploadListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the file upload event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "void fileUploaded(FileUploadEvent " + EVENT_PARAMETER + "){");
            println(pw,  "}");
        }

        // Calendar Listener
        if (INTERFACES.contains("CalendarListener")) {
            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the calendar date selection event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "void dateSelected(CalendarDateSelectedEvent " + EVENT_PARAMETER + "){");
            println(pw,  "}");

            println(pw,  SPACE);
		    println(pw,  "/**");
		    println(pw,  " * Process the month change event");
		    println(pw,  " *");
		    println(pw,  " * @param " + EVENT_PARAMETER + " the event to be processed");
		    println(pw,  " *");
		    println(pw,  " * @return true to continue processing events, false to stop processing events");
		    println(pw,  " *");
		    println(pw,  " */");
            println(pw,  "boolean monthChanged(CalendarMonthChangeEvent " + EVENT_PARAMETER + "){");
            println(pw,  TAB + "return true;");
            println(pw,  "}");
        }
    }

    private static String computeFieldName(String fieldName) {
        int TO_UPPER = 1;
        int TO_LOWER = 2;
        int NO_CHANGE = 0;

        StringBuffer sb = new StringBuffer(fieldName.length() + 1);
        sb.append('_');
        int nextChange = TO_LOWER;
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (c == '.' || c == ' ')
                nextChange = TO_UPPER;
            else if (nextChange == TO_LOWER) {
                sb.append(Character.toLowerCase(c));
                nextChange = NO_CHANGE;
            } else if (nextChange == TO_UPPER) {
                sb.append(Character.toUpperCase(c));
                nextChange = NO_CHANGE;
            } else
                sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Generates a stub controller for a view.
     */
    public static void generateCode(java.io.PrintWriter p, Hashtable hashFields, JspController cont, String controllerName) {
        try {
            PrintWriter pw = p;
            println(pw,  "<BR>");
            println(pw,  "");
            String cName = controllerName;
            String packageName = "";
            if (cName == null || cName.equals("") || cName.equals("com.salmonllc.jsp.JspController"))
                cName = "GeneratedController";
            int pos = cName.lastIndexOf(".");
            if (pos > 0) {
                packageName = cName.substring(0, pos);
                cName = cName.substring(pos + 1);
            }

            // create the package statement
            println(pw,  "//package statement");
            if (packageName != null)
                println(pw,  PACKAGE + SPACE + packageName + ";");

            // create some standard import statements
            println(pw,  "");
            println(pw,  "//Salmon import statements");
            println(pw,  IMPORT + SPACE + "com.salmonllc.jsp.*;");
            println(pw,  IMPORT + SPACE + "com.salmonllc.html.events.*;");
            println(pw,  "");
            println(pw,  "");

            // Start the Class definition
            println(pw,  "/**");
		    println(pw,  " * " + cName + ": a SOFIA generated controller");
		    println(pw,  " */");
            pw.print(CLASSDEF + SPACE + cName + SPACE);
            pw.print(EXTENDS + SPACE + BASECLASS + SPACE);

            if (INTERFACES != null && INTERFACES.size() > 0) {
                pw.print(IMPLEMENTS + SPACE);
                pw.print(INTERFACES.elementAt(0));
                for (int i = 1; i < INTERFACES.size(); i++) {
                    pw.print(COMMA + SPACE);
                    pw.print(INTERFACES.elementAt(i));
                }
            }
            println(pw,  " {");

            // Add the variables
            cont.printVars(pw);

            // add the Initialize Method
            println(pw,  SPACE);
            println(pw,  "/**");
		    println(pw,  " * Initialize the page. Set up listeners and perform other initialization activities.");
		    println(pw,  " */");
            println(pw,  INITMETHOD + "{");

            //For Page Listeners
            if (INTERFACES.contains(new String("PageListener")))
                println(pw,  TAB + "addPageListener(this);");

            if (hashFields != null) {
                Enumeration en = hashFields.keys();
                while (en.hasMoreElements() && INTERFACES != null) {
                    String key = (String) en.nextElement();
                    Object obj = hashFields.get(key);

                    // For Buttons
                    if (INTERFACES.contains(new String("SubmitListener"))) {
                        if (obj instanceof com.salmonllc.html.HtmlSubmitButton || obj instanceof com.salmonllc.html.HtmlSubmitImage)
                            println(pw,  TAB + computeFieldName(key) + ".addSubmitListener(this);");
                    }

                    //For Value Change Listeners
                    if (INTERFACES.contains(new String("ValueChangedListener"))) {
                        if (obj instanceof com.salmonllc.html.HtmlCheckBox || obj instanceof com.salmonllc.html.HtmlRadioButton || obj instanceof com.salmonllc.html.HtmlDropDownList)
                            println(pw,  TAB + computeFieldName(key) + ".addValueChangeListener(this);");
                    }

                    //For Tree Listeners
                    if (INTERFACES.contains(new String("TreeListener"))) {
                        if (obj instanceof com.salmonllc.html.HtmlTreeControl)
                            println(pw,  TAB + computeFieldName(key) + ".addTreeListener(this);");
                    }
                } // while loop
            } // hashatable not null

            println(pw,  "}");

            // add the listeners
            addListeners(pw);

            // End the class definition
            println(pw,  "");
            println(pw,  "}");

            pw.flush();

        } catch (java.io.IOException e) {
            System.out.println("Cannot create Java File.");
            e.printStackTrace();
        }
    }


    private static void println(PrintWriter pw, String strToPrint) {
        pw.println(strToPrint);
        pw.println("<BR>");
    }




}
