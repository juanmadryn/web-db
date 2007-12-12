package com.salmonllc.xml;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/xml/DOMParser.java $
//$Author: Srufle $ 
//$Revision: 10 $ 
//$Modtime: 7/31/02 6:11p $ 
/////////////////////////
/*
 * (C) Copyright IBM Corp. 1999  All rights reserved.
 *
 * US Government Users Restricted Rights Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *
 * The program is provided "as is" without any warranty express or
 * implied, including the warranty of non-infringement and the implied
 * warranties of merchantibility and fitness for a particular purpose.
 * IBM will not be liable for any damages suffered by you as a result
 * of using the Program. In no event will IBM be liable for any
 * special, indirect or consequential damages or lost profits even if
 * IBM has been advised of the possibility of their occurrence. IBM
 * will not be liable for any third party claims against you.
 */

import org.w3c.dom.Document;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * Licensed Material - Property of Salmon LLC
 * (C) Copyright Salmon LLC. 1999 - All Rights Reserved
 * For more information go to www.salmonllc.com
 *
 * *************************************************************************
 * DISCLAIMER:
 * The following code has been created by Salmon LLC. The code is provided
 * 'AS IS' , without warranty of any kind unless covered in another agreement
 * between your corporation and Salmon LLC.  Salmon LLC shall not be liable
 * for any damages arising out of your use of this, even if they have been
 * advised of the possibility of such damages.
 * *************************************************************************
 *
 * Wraps the JAXP DOM parser.
 *
 * @version Revision: 12 1.2 samples/dom/wrappers/DOMParser.java, samples, xml4j2, xml4j2_0_15
 */
public class DOMParser
        implements DOMParserWrapper, ErrorHandler {

    //
    // Data
    //
    private boolean _bErrorFlagSet = false;
    private boolean _bWarningFlagSet = false;

    //
    // Constructors
    //


    /** Error. */
    public void error(SAXParseException ex) {
        System.err.println("[Error] " +
                getLocationString(ex) + ": " +
                ex.getMessage());
        _bErrorFlagSet = true;
    }
    /** Fatal error. */
    public void fatalError(SAXParseException ex) throws SAXException {
        System.err.println("[Fatal Error] " +
                getLocationString(ex) + ": " +
                ex.getMessage());
        throw ex;
    }
    //
    // Private methods
    //

    /** Returns a string of the location. */
    private String getLocationString(SAXParseException ex) {
        StringBuffer str = new StringBuffer();

        String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1)
                systemId = systemId.substring(index + 1);
            str.append(systemId);
        }
        str.append(':');
        str.append(ex.getLineNumber());
        str.append(':');
        str.append(ex.getColumnNumber());

        return str.toString();

    } // getLocationString(SAXParseException):String
    /**
     * Creation date: (3/22/02 1:52:00 PM)
     * @return boolean
     */
    public boolean isErrorFlagSet() {
        return _bErrorFlagSet;
    }
    /**
     * Creation date: (3/22/02 1:52:00 PM)
     * @return boolean
     */
    public boolean isWarningFlagSet() {
        return _bWarningFlagSet;
    }
    //
    // DOMParserWrapper methods
    //

    /** Parses the specified URI and returns the document. */
    public Document parse(String uri) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(this);
        return db.parse(uri);

    } // parse(String):Document
    /**
     * Creation date: (3/22/02 1:52:00 PM)
     * @param newErrorFlagSet boolean
     */
    public void setErrorFlag(boolean newErrorFlagSet) {
        _bErrorFlagSet = newErrorFlagSet;
    }
    /**
     * Creation date: (3/22/02 1:52:00 PM)
     * @param newWarningFlagSet boolean
     */
    public void setWarningFlag(boolean newWarningFlagSet) {
        _bWarningFlagSet = newWarningFlagSet;
    }
    //
    // ErrorHandler methods
    //

    /** Warning. */
    public void warning(SAXParseException ex) {
        System.err.println("[Warning] " +
                getLocationString(ex) + ": " +
                ex.getMessage());
        _bWarningFlagSet = true;
    }
} // interface DOMParser
