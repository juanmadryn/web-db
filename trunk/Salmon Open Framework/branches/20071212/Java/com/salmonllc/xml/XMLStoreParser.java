package com.salmonllc.xml;

import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.util.Util;
import java.io.*;
import org.w3c.dom.*;
/**
 * @author  demian
 */
public class XMLStoreParser
{

    /** Default parser name. */
    private static final String DEFAULT_PARSER_NAME = "com.salmonllc.xml.DOMParser";

    private static ResultSetMetaData _rsMetaData = new ResultSetMetaData();
    private static XMLDataStore _dataStore = new XMLDataStore();
    private static String URIpath = "";

    /** Default Encoding */
    private static String PRINTWRITER_ENCODING = "UTF8";

    /*private static String MIME2JAVA_ENCODINGS[] =
            {
                "Default",
                "UTF-8",
                "US-ASCII",
                "ISO-8859-1",
                "ISO-8859-2",
                "ISO-8859-3",
                "ISO-8859-4",
                "ISO-8859-5",
                "ISO-8859-6",
                "ISO-8859-7",
                "ISO-8859-8",
                "ISO-8859-9",
                "ISO-2022-JP",
                "SHIFT_JIS",
                "EUC-JP",
                "GB2312",
                "BIG5",
                "EUC-KR",
                "ISO-2022-KR",
                "KOI8-R",
                "EBCDIC-CP-US",
                "EBCDIC-CP-CA",
                "EBCDIC-CP-NL",
                "EBCDIC-CP-DK",
                "EBCDIC-CP-NO",
                "EBCDIC-CP-FI",
                "EBCDIC-CP-SE",
                "EBCDIC-CP-IT",
                "EBCDIC-CP-ES",
                "EBCDIC-CP-GB",
                "EBCDIC-CP-FR",
                "EBCDIC-CP-AR1",
                "EBCDIC-CP-HE",
                "EBCDIC-CP-CH",
                "EBCDIC-CP-ROECE",
                "EBCDIC-CP-YU",
                "EBCDIC-CP-IS",
                "EBCDIC-CP-AR2",
                "UTF-16" };*/

    /** Print writer. */
    protected PrintWriter out;

    /** Canonical output. */
    protected boolean canonical;

    public XMLStoreParser(String encoding, boolean canonical) throws UnsupportedEncodingException
    {
        out = new PrintWriter(new OutputStreamWriter(System.out, encoding));
        this.canonical = canonical;
        _rsMetaData = new ResultSetMetaData();
    } // <init>(String,boolean)
    //
    // Constructors
    //

    /** Default constructor. */
    public XMLStoreParser(boolean canonical) throws UnsupportedEncodingException {
        this( getWriterEncoding(), canonical);
    }
    /**
     * This method returns the XML datastore created by reading the XML file
     * Creation date: (8/7/01 5:17:42 PM)
     * @return com.salmonllc.xml.XMLDataStore
     */
    public static XMLDataStore getDataStore() {
        return _dataStore;
    }
    /**
     * Gets the ResuletSetMetaData for the XML file uploaded.
     * Creation date: (7/18/01 4:29:55 PM)
     * @return com.afg.applications.investment.xml.ResultSetMetaData
     */
    public ResultSetMetaData getMetaData() {
        return _rsMetaData;
    }
    /**
     * Creation date: (7/20/01 2:03:28 PM)
     * @return java.lang.String
     * @param node org.w3c.dom.Node
     * @param columnName java.lang.String
     */
    private boolean getNodeBooleanValue(Node node, String columnName) {

        Node temp = node.getAttributes().getNamedItem(columnName);
        if(temp != null)
            return temp.getNodeValue().equals("true");

        return false;
    }
    /**
     * Creation date: (7/20/01 2:03:28 PM)
     * @return java.lang.String
     * @param node org.w3c.dom.Node
     * @param columnName java.lang.String
     */
    private String getNodeValue(Node node, String columnName) {

        Node temp = node.getAttributes().getNamedItem(columnName);
        if(temp != null)
            return temp.getNodeValue();

        return null;
    }

    // getWriterEncoding
    private static String getWriterEncoding( ) {
        return (PRINTWRITER_ENCODING);
    }

    /** Normalizes the given string. */
    protected String normalize(String s)
    {
        StringBuffer str = new StringBuffer();

        int len = (s != null) ? s.length() : 0;
        for ( int i = 0; i < len; i++ ) {
            char ch = s.charAt(i);
            switch ( ch ) {
                case '<': {
                    str.append("&lt;");
                    break;
                }
                case '>': {
                    str.append("&gt;");
                    break;
                }
                case '&': {
                    str.append("&amp;");
                    break;
                }
                case '"': {
                    str.append("&quot;");
                    break;
                }
                case '\r':
                case '\n': {
                    if ( canonical ) {
                        str.append("&#");
                        str.append(Integer.toString(ch));
                        str.append(';');
                        break;
                    }
                    // else, default append char
                }
                default: {
                    str.append(ch);
                }
            }
        }

        return (str.toString());

    } // normalize(String):String
    /**
     * This method is used to create the XMLDatastore object out of the XML file. XML file name
     * is passed as uri. Method getDataStore or getMetaData is used after this method is called.
     *
     */
    public static void parseFile(String uri) throws Exception
    {
        parseFile(DEFAULT_PARSER_NAME, uri, false);

    } // print(String,String,boolean)
    /** Prints the resulting document tree. */
    private static void parseFile(String parserWrapperName, String uri, boolean canonical) throws Exception
    {
        int nUriLastSlashIndex = uri.lastIndexOf("\\");
        boolean anypath=true;
        if (nUriLastSlashIndex == -1)
        {
            anypath = false;
            nUriLastSlashIndex = uri.lastIndexOf("/");
            if(nUriLastSlashIndex != -1)
            {
                anypath = true;
            }
        }

        if(anypath)
            URIpath = uri.substring(0, nUriLastSlashIndex);

        DOMParserWrapper parser = (DOMParserWrapper) Class.forName(parserWrapperName).newInstance();
        Document document = parser.parse(uri);
        XMLStoreParser writer = new XMLStoreParser(canonical);
        writer.parseNode(document);
        System.out.println("XML Parsing done" + _dataStore);
    } // print(String,String,boolean)
    /** Prints the specified node, recursively. */
    private void parseNode(Node node) {

        // is there anything to do?
        if (node == null) {
            return;
        }

        int type = node.getNodeType();
        switch (type)
        {
            case Node.DOCUMENT_NODE:
                parseNode(((Document) node).getDocumentElement());
                break;
                // print element with attributes
            case Node.ELEMENT_NODE :

                if (node.getNodeName().equalsIgnoreCase("ResultSetMetaData")) {
                    NodeList children = node.getChildNodes();
                    if (children != null) {
                        if (_dataStore == null)
                            _dataStore = new XMLDataStore();

                        _rsMetaData.setMetaData(new java.util.Vector());
                        _dataStore.setRsm(_rsMetaData);

                        int len = children.getLength();
                        for (int i = 0; i < len; i++) {
                            Node nodeChild = children.item(i);
                            if (nodeChild.getNodeName().equalsIgnoreCase("ColumnMetaData")) {
                                ColumnMetaData columnMeta = new ColumnMetaData();
                                columnMeta.setComponent(getNodeValue(nodeChild, "component"));
                                columnMeta.setComponentType(getNodeValue(nodeChild, "componenttype"));

                                columnMeta.setInternalName(getNodeValue(nodeChild, "internalname"));

                                columnMeta.setColumnName(getNodeValue(nodeChild, "columnname"));
                                columnMeta.setColumnType(getNodeValue(nodeChild, "columntype"));

                                columnMeta.setNullable(getNodeBooleanValue(nodeChild, "nullable"));
                                columnMeta.setPrimaryKey(getNodeBooleanValue(nodeChild, "primarykey"));
                                columnMeta.setAutoIncrement(getNodeBooleanValue(nodeChild, "autoincrement"));
                                columnMeta.setNotBound(getNodeBooleanValue(nodeChild, "isnotbound"));
                                columnMeta.setOnClick(getNodeValue(nodeChild, "onclick"));

                                columnMeta.setTable(getNodeValue(nodeChild, "tablename"));
                                columnMeta.setSchema(getNodeValue(nodeChild, "schemaname"));

                                //columnMeta.setMode(getNodeValue(nodeChild, "mode"));
                                columnMeta.setMaxLength(getNodeValue(nodeChild, "maxlength"));
                                columnMeta.setSize(getNodeValue(nodeChild, "size"));
                                columnMeta.setSearchDisplay(getNodeBooleanValue(nodeChild, "searchdisplay"));
                                columnMeta.setListDisplay(getNodeBooleanValue(nodeChild, "listdisplay"));
                                columnMeta.setDetailDisplay(getNodeBooleanValue(nodeChild, "detaildisplay"));

                                columnMeta.setFormat(getNodeValue(nodeChild, "format"));
                                columnMeta.setHref(getNodeValue(nodeChild, "href"));

                                columnMeta.setCaption(getNodeValue(nodeChild, "caption"));
                                columnMeta.setPrecedence(getNodeBooleanValue(nodeChild, "precedence"));
                                columnMeta.setLeadingWildCard(getNodeBooleanValue(nodeChild, "leadingwildcard"));

                                columnMeta.setExactMatch(getNodeBooleanValue(nodeChild, "exactmatch"));
                                columnMeta.setCaseSensitive(getNodeBooleanValue(nodeChild, "casesensitive"));
                                columnMeta.setLocked(getNodeBooleanValue(nodeChild, "locked"));
                                columnMeta.setMandatory(getNodeBooleanValue(nodeChild, "mandatory"));
                                columnMeta.setAdvanceSearch(getNodeBooleanValue(nodeChild, "advancesearch"));

                                columnMeta.setBucket(getNodeBooleanValue(nodeChild, "isbucket"));
                                columnMeta.setUpdateable(getNodeBooleanValue(nodeChild, "updateable"));
                                columnMeta.setSameRow(getNodeBooleanValue(nodeChild, "samerow"));
                                columnMeta.setReadOnly(getNodeBooleanValue(nodeChild, "readonly"));
	                             columnMeta.setOrderByColumn(getNodeBooleanValue(nodeChild, "orderbycolumn"));
                                columnMeta.setDefaultValue(getNodeValue(nodeChild, "defaultvalue"));
                                columnMeta.setImageFile(getNodeValue(nodeChild, "imageFile"));
                                columnMeta.setJoinTo(getNodeValue(nodeChild, "jointo"));

                                // Get The Values and Parms if Any
                                Options values = null;
                                Options parms = null;
                                NodeList childValues = nodeChild.getChildNodes();
                                int lenValues = childValues.getLength();
                                for (int k = 0; k < lenValues; k++) {
                                    Node nodeValues = childValues.item(k);
                                    if (nodeValues.getNodeName().equalsIgnoreCase("Values")) {
                                        values = new Options();
                                        NodeList childValue = nodeValues.getChildNodes();
                                        int lenValue = childValue.getLength();
                                        values.setType(getNodeValue(nodeValues, "type"));
                                        values.setComponent(getNodeValue(nodeValues, "component"));
                                        values.setTable(getNodeValue(nodeValues, "table"));
                                        values.setInitColumn(getNodeValue(nodeValues, "initcolumn"));
                                        values.setDescColumn(getNodeValue(nodeValues, "desccolumn"));
                                        values.setMandatory(getNodeBooleanValue(nodeValues, "mandatory"));
                                        String xmlFileName = getNodeValue(nodeValues, "xmlfilename");
                                        if (values.getType().equalsIgnoreCase("Static")) {
                                            if (xmlFileName != null && !xmlFileName.equals("")) {
                                                xmlFileName = URIpath + "\\" + xmlFileName;
                                                try {
                                                    XMLOptionsParser opParser = new XMLOptionsParser(false);
                                                    XMLOptionsParser.parseFile(xmlFileName, values);
                                                    values = opParser.getOptions();
                                                } catch (java.io.UnsupportedEncodingException e) {
                                                    // values become null
                                                }
                                            } else {
                                                for (int j = 0; j < lenValue; j++) {
                                                    Node nodeValue = childValue.item(j);
                                                    if (nodeValue.getNodeName().equalsIgnoreCase("Option")) {
                                                        values.put(getNodeValue(nodeValue, "key"), getNodeValue(nodeValue, "data"));
                                                    }
                                                }
                                            }
                                        }

                                    } // end if (nodeValues.getNodeName().equalsIgnoreCase("Values"))
                                    else if (nodeValues.getNodeName().equalsIgnoreCase("parms")) {

                                        parms = new Options();
                                        NodeList childValue = nodeValues.getChildNodes();
                                        int lenValue = childValue.getLength();
                                        parms.setComponent(getNodeValue(nodeValues, "component"));
                                        String xmlFileName = getNodeValue(nodeValues, "xmlfilename");

                                        if (Util.isFilled(xmlFileName)) {
                                            xmlFileName = URIpath + "\\" + xmlFileName;
                                            try {
                                                XMLOptionsParser opParser = new XMLOptionsParser(false);
                                                XMLOptionsParser.parseFile(xmlFileName, parms);
                                                parms = opParser.getOptions();
                                            } catch (java.io.UnsupportedEncodingException e) {
                                                // parms become null
                                            }
                                        } else {
                                            for (int j = 0; j < lenValue; j++) {
                                                Node nodeValue = childValue.item(j);
                                                if (nodeValue.getNodeName().equalsIgnoreCase("Option")) {

                                                    String dataVal = getNodeValue(nodeValue, "data");
                                                    Object typedData = new Object();

                                                    if (Util.isFilled(dataVal)) {

                                                        if (DataStoreEvaluator.isNumber(dataVal)) {
                                                            typedData = new Integer(dataVal);
                                                        } else if (DataStoreEvaluator.isBoolean(dataVal)) {
                                                            typedData = new Boolean(dataVal);
                                                        } else {
                                                            typedData = new String(dataVal);
                                                        }

                                                    }
                                                    parms.put(getNodeValue(nodeValue, "key"), typedData);
                                                }
                                            }
                                        }

                                    } // end else if (nodeValues.getNodeName().equalsIgnoreCase("parms"))
                                } // end for (int k = 0; k < lenValues; k++)
                                columnMeta.setValues(values);
                                columnMeta.setParms(parms);
                                _rsMetaData.getMetaData().add(columnMeta);
                            }
                        }
                    }
                } // ResultSetData
                else if (node.getNodeName().equalsIgnoreCase("ResultSetData")) {
                    ResultSetData rsData = new ResultSetData();
                    if (_dataStore == null)
                        _dataStore = new XMLDataStore();
                    _dataStore.setRsData(rsData); // Get All the Rows
                    NodeList children = node.getChildNodes();
                    if (children != null) {
                        int len = children.getLength();
                        for (int i = 0; i < len; i++) {
                            Node nodeChild = children.item(i);
                            if (nodeChild.getNodeName().equalsIgnoreCase("Row")) {
                                Row row = new Row();
                                rsData.getRows().add(row); // Get All Columns
                                NodeList childValues = nodeChild.getChildNodes();
                                int lenValues = childValues.getLength();
                                for (int k = 0; k < lenValues; k++) {
                                    Node nodeColumn = childValues.item(k);
                                    if (nodeColumn.getNodeName().equalsIgnoreCase("Column")) {
                                        Column column = new Column();
                                        column.setColumnName(getNodeValue(nodeColumn, "name"));
                                        column.setTableName(getNodeValue(nodeColumn, "table"));
                                        column.setValue(getNodeValue(nodeColumn, "value")); // put the column in row
                                        row.put(column.getTableName() + "." + column.getColumnName(), column);
                                    }

                                }

                            }
                        }
                    }
                } // recursive call
                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();
                    for (int i = 0; i < len; i++) {
                        parseNode(children.item(i));
                    }
                }

                break;
        }
    } // parseNode(Node)

    /** Returns a sorted list of attributes. */
    protected Attr[] sortAttributes(NamedNodeMap attrs) {

        int len = (attrs != null) ? attrs.getLength() : 0;
        Attr array[] = new Attr[len];
        for ( int i = 0; i < len; i++ ) {
            array[i] = (Attr)attrs.item(i);
        }
        for ( int i = 0; i < len - 1; i++ ) {
            String name  = array[i].getNodeName();
            int    index = i;
            for ( int j = i + 1; j < len; j++ ) {
                String curName = array[j].getNodeName();
                if ( curName.compareTo(name) < 0 ) {
                    name  = curName;
                    index = j;
                }
            }
            if ( index != i ) {
                Attr temp    = array[i];
                array[i]     = array[index];
                array[index] = temp;
            }
        }

        return (array);

    } // sortAttributes(NamedNodeMap):Attr[]
}
