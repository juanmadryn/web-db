package com.salmonllc.xml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/xml/XMLTransporter.java $
//$Author: Dan $ 
//$Revision: 26 $ 
//$Modtime: 6/11/03 4:30p $ 
/////////////////////////
import com.salmonllc.sql.*;

/**
 * XML Transporter is used to transport data to and from data store as XML documents based on datastore.dtd file.
 * Datastore.dtd file is a representation of java.sql.ResultSet class.
 */
public class XMLTransporter
{
    static String _sComments = null;
    /**
     * XMLTransporter constructor comment.
     */
    public XMLTransporter()
    {
        super();
        _sComments = null;
    }
    private static String getBooleanValue(boolean bvalue)
    {
        if (bvalue)
            return "true";
        return "false";
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/19/2002 3:07:02 PM)
     * @return java.lang.String
     */
    public static String getComments()
    {
        return _sComments;
    }
    /**
     * Creation date: (8/1/01 11:09:17 AM)
     * @return int
     * @param strDataType java.lang.String
     */
    public static int mapDataType(String strDataType)
    {

        if (strDataType == null)
            return 0;
        else if (strDataType.equalsIgnoreCase("String"))
            return DataStore.DATATYPE_STRING;
        else if (strDataType.equalsIgnoreCase("Integer"))
            return DataStore.DATATYPE_INT;
        else if (strDataType.equalsIgnoreCase("Datetime"))
            return DataStore.DATATYPE_DATETIME;
        else if (strDataType.equalsIgnoreCase("double"))
            return DataStore.DATATYPE_DOUBLE;
        else if (strDataType.equalsIgnoreCase("bytearray"))
            return DataStore.DATATYPE_BYTEARRAY;
        else if (strDataType.equalsIgnoreCase("short"))
            return DataStore.DATATYPE_SHORT;
        else if (strDataType.equalsIgnoreCase("long"))
            return DataStore.DATATYPE_LONG;
        else if (strDataType.equalsIgnoreCase("float"))
            return DataStore.DATATYPE_FLOAT;
        else if (strDataType.equalsIgnoreCase("date"))
            return DataStore.DATATYPE_DATE;
        else if (strDataType.equalsIgnoreCase("time"))
            return DataStore.DATATYPE_TIME;

        return 99;
        /*
        DATATYPE_STRING = 0;
        DATATYPE_INT = 1;
        DATATYPE_DATETIME = 2;
        public static final int DATATYPE_DOUBLE = 3;
        public static final int DATATYPE_BYTEARRAY = 4;
        public static final int DATATYPE_SHORT = 5;
        public static final int DATATYPE_LONG = 6;
        public static final int DATATYPE_FLOAT = 7;
        public static final int DATATYPE_DATE = 8;
        public static final int DATATYPE_TIME = 9;
        protected static final int DATATYPE_ANY = 99;
        */
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/19/2002 3:07:02 PM)
     * @return java.lang.String
     */
    public static void setComments(String sComments)
    {
        _sComments = sComments;
    }
    /**
     * This method is used to export the datastore as a XML file. This uses datastore.dtd file as XMl schema.
     * * Creation date: (8/7/01 5:11:31 PM)
     * @param pw  - This is the printwriter where the XML file should be exported
     * @param _ds - Datastore to export as XML.
     */
    public static void xmlExport(java.io.PrintWriter pw, DataStore _ds) throws java.sql.SQLException
    {
        try
            {
            String TAB = "      ";
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("");
            pw.println("<!DOCTYPE ResultSet SYSTEM \"datastore.dtd\">");

            pw.println("<resultset>");

            // Printing the Meta Data
            // ------------------------
            pw.println("<resultsetmetadata>");
            String columnMetaData = "";
            for (int i = 0; i < _ds.getColumnCount(); i++)
                {
                columnMetaData = "";
                columnMetaData += TAB + "<columnmetadata ";

                String table = _ds.getColumnTableName(i);

                String cName = _ds.getColumnName(i);
                if (cName == null)
                    cName = "Column" + i;

                if (cName != null && (cName.indexOf(".") != -1))
                    {
                    if (table == null)
                        table = cName.substring(0, cName.indexOf("."));

                    cName = cName.substring(cName.indexOf(".") + 1);
                }

                columnMetaData += " component=\"" + cName + "\"";
                columnMetaData += " columnname=\"" + cName + "\"";
                columnMetaData += " caption=\"" + cName + "\"";

                if (table == null)
                    table = "defaultTable";

                columnMetaData += " tablename=\"" + table + "\"";
                columnMetaData += " columntype=\"";
                int colType = _ds.getColumnDataType(i);
                switch (colType)
                    {
                    case DataStore.DATATYPE_STRING :
                        columnMetaData += "String";
                        break;
                    case DataStore.DATATYPE_DATE :
                        columnMetaData += "Date";
                        break;
                    case DataStore.DATATYPE_DATETIME :
                        columnMetaData += "DateTime";
                        break;
                    case DataStore.DATATYPE_TIME :
                        columnMetaData += "Time";
                        break;
                    case DataStore.DATATYPE_SHORT :
                        columnMetaData += "Short";
                        break;
                    case DataStore.DATATYPE_INT :
                        columnMetaData += "Integer";
                        break;
                    case DataStore.DATATYPE_LONG :
                        columnMetaData += "Long";
                        break;
                    case DataStore.DATATYPE_FLOAT :
                        columnMetaData += "Float";
                        break;
                    case DataStore.DATATYPE_DOUBLE :
                        columnMetaData += "Double";
                        break;
                    case DataStore.DATATYPE_BYTEARRAY :
                        columnMetaData += "ByteArray";
                        break;
                }
                columnMetaData += "\"";
                columnMetaData += " primarykey=\"" + _ds.isColumnPrimaryKey(i) + "\"";

                if (_ds.getColumnFormat(i) != null)
                    columnMetaData += " format=\"" + _ds.getColumnFormat(i) + "\"";

                if (_ds.getColumnInternalName(i) != null)
                    columnMetaData += " internalname=\"" + _ds.getColumnInternalName(i) + "\"";

                columnMetaData += "> </columnmetadata>";

                pw.println(columnMetaData);
            }
            pw.println("</resultsetmetadata>");

            // Printing the Data
            // -----------------------
            pw.println("<resultsetdata>");
            String row = "";
            for (int r = 0; r < _ds.getRowCount(); r++)
                {
                row = "";
                DataStoreRow dsRow = _ds.getDataStoreRow(r, DataStoreBuffer.BUFFER_STANDARD);
                DSDataRow dRow = dsRow.getDSDataRow();
                row += TAB + "<row>";
                for (int c = 0; c < _ds.getColumnCount(); c++)
                    {
                    if (dRow.getData(c) != null)
                        {
                        String table = _ds.getColumnTableName(c);

                        String cName = _ds.getColumnName(c);
                        if (cName == null)
                            cName = "column" + c;

                        if (cName != null && (cName.indexOf(".") != -1))
                            {
                            if (table == null)
                                table = cName.substring(0, cName.indexOf("."));

                            cName = cName.substring(cName.indexOf(".") + 1);
                        }

                        row += "\n" + TAB + "<column " + " table=\"" + table + "\"" + " name=\"" + cName + "\"" + " value=\"" + _ds.getFormattedString(r, c) + "\" />";
                    }
                }
                row += "</row>";
                pw.println(row);
            }
            pw.println("</resultsetdata>");

            pw.println("</resultset>");

            pw.flush();
            pw.close();
        }
        catch (com.salmonllc.sql.DataStoreException eIO)
            {
            System.out.println("Cant export DataStore to xmlFile. : " + eIO);
        }
    }
    /**
     * Method to export datastore as a XML file.
     * Creation date: (8/7/01 5:11:31 PM)
     * @param xmlFileName java.lang.String
     *
     */
    public static void xmlExport(String xmlFileName, DataStore _ds) throws java.sql.SQLException
    {
        try
            {
            java.io.File fileExport = new java.io.File(xmlFileName);
            java.io.FileOutputStream os = new java.io.FileOutputStream(fileExport);
            java.io.PrintWriter pw = new java.io.PrintWriter(os);

            xmlExport(pw, _ds);
            os.close();
        }
        catch (java.io.IOException eIO)
            {
            System.out.println("Cant export DataStore to xmlFile. : " + eIO);
        }
    }
    /**
     * This methid used to export the data from datastore as Metadata for other datastores.
     * Creation date: (8/7/01 5:11:31 PM)
     * @param xmlFileName java.lang.String
     *
     */
    public static void xmlExportDataASMetaData(java.io.PrintWriter pw, DataStore _ds) throws java.sql.SQLException
    {
        try
            {
            String TAB = "      ";
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("");
            pw.println("<!DOCTYPE ResultSet SYSTEM \"datastore.dtd\">");

            pw.println("<resultset>");

            // Printing the Meta Data
            // ------------------------
            pw.println("<resultsetmetadata>");
            String columnMetaData = "";
            for (int r = 0; r < _ds.getRowCount(); r++)
                {
                // 1 Row of Data is One Column MetaData for other listForms
                columnMetaData = "";
                columnMetaData += TAB + "<columnmetadata ";

                DataStoreRow dsRow = _ds.getDataStoreRow(r, DataStoreBuffer.BUFFER_STANDARD);
                DSDataRow dRow = dsRow.getDSDataRow();

                for (int c = 0; c < _ds.getColumnCount(); c++)
                    {
                    int colType = _ds.getColumnDataType(c);
                    if (dRow.getData(c) != null)
                        {
                        String cName = _ds.getColumnName(c);
                        if (cName == null)
                            cName = "Column" + c;

                        if (cName != null && (cName.indexOf(".") != -1))
                            {
                            cName = cName.substring(cName.indexOf(".") + 1);
                        }

                        String value = _ds.getFormattedString(r, c);
                        if (colType == DataStore.DATATYPE_INT && value.equalsIgnoreCase("0"))
                            value = "false";
                        if (colType == DataStore.DATATYPE_INT && value.equalsIgnoreCase("1"))
                            value = "true";

                        columnMetaData += "\n" + TAB + TAB + cName + "=" + "\"" + value + "\" ";
                    }
                }
                columnMetaData += ">\n" + TAB + TAB + "</columnmetadata>\n";
                pw.println(columnMetaData);
            }

            pw.println("</resultsetmetadata>");

            pw.println("</resultset>");

            pw.flush();
            pw.close();

        }
        catch (com.salmonllc.sql.DataStoreException eIO)
            {
            System.out.println("Cant export DataStore to xmlFile. : " + eIO);
        }
    }
    /**
     * This method is used to export the datastore as a XML file. This uses datastore.dtd file as XMl schema.
     * * Creation date: (8/7/01 5:11:31 PM)
     * @param pw  - This is the printwriter where the XML file should be exported
     * @param _ds - Datastore to export as XML.
     */
    public static void xmlExportResultSet(java.io.PrintWriter pw, DataStore _ds) throws java.sql.SQLException
    {
        try
            {
            String TAB = "      ";
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("");
            pw.println("<!DOCTYPE ResultSet SYSTEM \"datastore.dtd\">");

            //Use comments to pass values, keys that are not part of datastore.
            String sComments = getComments();
            if (sComments != null)
                pw.println(sComments);

            pw.println("<resultset>");

            // Writing the mandatory tag But we will just ignore it while processing the import
            pw.println("<!-- Dont remove this TAG is used for XMl processing -->");
            pw.println("<resultsetmetadata> </resultsetmetadata>");

            // Printing the Data
            // -----------------------
            pw.println("<resultsetdata>");
            String row = "";
            for (int r = 0; r < _ds.getRowCount(); r++)
                {
                row = "";
                DataStoreRow dsRow = _ds.getDataStoreRow(r, DataStoreBuffer.BUFFER_STANDARD);
                DSDataRow dRow = dsRow.getDSDataRow();
                row += TAB + "<row>";
                for (int c = 0; c < _ds.getColumnCount(); c++)
                    {
                    if (dRow.getData(c) != null)
                        {
                        String table = _ds.getColumnTableName(c);

                        String cName = _ds.getColumnName(c);
                        if (cName == null)
                            cName = "column" + c;

                        if (cName != null && (cName.indexOf(".") != -1))
                            {
                            if (table == null)
                                table = cName.substring(0, cName.indexOf("."));

                            cName = cName.substring(cName.indexOf(".") + 1);
                        }

                        row += "\n" + TAB + "<column " + " table=\"" + table + "\"" + " name=\"" + cName + "\"" + " value=\"" + _ds.getFormattedString(r, c) + "\" />";
                    }
                }
                row += "</row>";
                pw.println(row);
            }
            pw.println("</resultsetdata>");

            pw.println("</resultset>");

            pw.flush();
            pw.close();
        }
        catch (com.salmonllc.sql.DataStoreException eIO)
            {
            System.out.println("Cant export DataStore to xmlFile. : " + eIO);
        }
    }
    /**
     * This method is used to export the datastore as a XML file. This uses datastore.dtd file as XMl schema.
     * * Creation date: (8/7/01 5:11:31 PM)
     * @param pw  - This is the printwriter where the XML file should be exported
     * @param _ds - Datastore to export as XML.
     */
    public static void xmlExportResultSetMetaData(java.io.PrintWriter pw, DataStore _ds) throws java.sql.SQLException
    {
        try
            {
            String TAB = "      ";
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("");
            pw.println("<!DOCTYPE ResultSet SYSTEM \"datastore.dtd\">");

            pw.println("<resultset>");

            // Printing the Meta Data
            // ------------------------
            pw.println("<resultsetmetadata>");
            String columnMetaData = "";
            for (int i = 0; i < _ds.getColumnCount(); i++)
                {
                columnMetaData = "";
                columnMetaData += TAB + "<columnmetadata ";

                String table = _ds.getColumnTableName(i);

                String cName = _ds.getColumnName(i);
                if (cName == null)
                    cName = "Column" + i;

                if (cName != null && (cName.indexOf(".") != -1))
                    {
                    if (table == null)
                        table = cName.substring(0, cName.indexOf("."));

                    cName = cName.substring(cName.indexOf(".") + 1);
                }

                columnMetaData += " component=\"" + cName + "\"";
                columnMetaData += " columnname=\"" + cName + "\"";
                columnMetaData += " caption=\"" + cName + "\"";

                if (table == null)
                    table = "defaultTable";

                columnMetaData += " tablename=\"" + table + "\"";
                columnMetaData += " columntype=\"";
                int colType = _ds.getColumnDataType(i);
                switch (colType)
                    {
                    case DataStore.DATATYPE_STRING :
                        columnMetaData += "String";
                        break;
                    case DataStore.DATATYPE_DATE :
                        columnMetaData += "Date";
                        break;
                    case DataStore.DATATYPE_DATETIME :
                        columnMetaData += "DateTime";
                        break;
                    case DataStore.DATATYPE_TIME :
                        columnMetaData += "Time";
                        break;
                    case DataStore.DATATYPE_SHORT :
                        columnMetaData += "Short";
                        break;
                    case DataStore.DATATYPE_INT :
                        columnMetaData += "Integer";
                        break;
                    case DataStore.DATATYPE_LONG :
                        columnMetaData += "Long";
                        break;
                    case DataStore.DATATYPE_FLOAT :
                        columnMetaData += "Float";
                        break;
                    case DataStore.DATATYPE_DOUBLE :
                        columnMetaData += "Double";
                        break;
                    case DataStore.DATATYPE_BYTEARRAY :
                        columnMetaData += "ByteArray";
                        break;
                }
                columnMetaData += "\"";
                columnMetaData += " primarykey=\"" + _ds.isColumnPrimaryKey(i) + "\"";

                if (_ds.getColumnFormat(i) != null)
                    columnMetaData += " format=\"" + _ds.getColumnFormat(i) + "\"";

                if (_ds.getColumnInternalName(i) != null)
                    columnMetaData += " internalname=\"" + _ds.getColumnInternalName(i) + "\"";

                columnMetaData += "> </columnmetadata>";

                pw.println(columnMetaData);
            }
            pw.println("</resultsetmetadata>");

            pw.println("<!-- Dont remove this TAG is used for XML processing -->");
            pw.println("<resultsetdata> </resultsetdata>");

            pw.println("</resultset>");

            pw.flush();
            pw.close();
        }
        catch (com.salmonllc.sql.DataStoreException eIO)
            {
            System.out.println("Cant export DataStore to xmlFile. : " + eIO);
        }
    }
    /**
     * xmlImport imports the XML file based on datastore.dtd to specified Datastore.
     * Creation date: (8/7/01 5:11:31 PM)
     * @param xmlFileName java.lang.String
     * @param _ds
     */
    public static DataStore xmlImport(String xmlFileName, DataStore _ds)
    {
        try
            {
          
            XMLStoreParser.parseFile(xmlFileName);

            XMLDataStore xds = XMLStoreParser.getDataStore();

            if (_ds != null)
                {
                ResultSetMetaData rsm = xds.getRsm();

                if (rsm != null)
                    {
                    for (int i = 0; i < rsm.getMetaData().size(); i++)
                        {
                        // Create the column descriptors
                        ColumnMetaData cmd = (ColumnMetaData) rsm.getMetaData().elementAt(i);

                        String table = cmd.getTable();
                        String column = cmd.getColumnName();
                        String caption = cmd.getCaption();
                        String format = cmd.getFormat();
                        int type = mapDataType(cmd.getColumnType());

                        // If existing dont overwrite the data
                        // ... need to discuss  if we should do a forceful import too
                        if (_ds.getColumnIndex(table + "." + column) == -1)
                            {
                            if (cmd.isPrimaryKey())
                                {
                                _ds.addColumn(table, column, type, true, true, caption);
                            }
                            else
                                {
                                _ds.addColumn(table, column, type, false, true, caption);
                            }
                            if (format != null)
                                _ds.setFormat(column, format);
                        }
                    }
                } // if rsm != null

                // If result data
                ResultSetData rsd = xds.getRsData();
                if (rsd != null)
                    {
                    if (rsd.getRows() != null)
                        {
                        for (int r = 0; r < rsd.getRows().size(); r++)
                            {
                            Row rowHash = (Row) rsd.getRows().elementAt(r);
                            _ds.importRow(rowHash);
                        }
                    }
                }
            }
            else
                {
                throw new Exception("Datastore not initiallized.");
            }
        }
        catch (Exception e)
            {
            System.out.println(e + " : " + e.getMessage());
        }

        return _ds;
    }
    /**
     * This methid used to import the metadata to a datastore
     * Creation date: (8/7/01 5:11:31 PM)
     * @param xmlFileName java.lang.String
     * @param _ds java.lang.String
     */
    public static DataStore xmlImportMetaDataAsData(String xmlFileName, DataStore _ds)
    {
        return XMLTransporter.xmlImportMetaDataAsData(xmlFileName, _ds, "listform", true);
    }
/**
 * This methid used to import the metadata to a datastore
 * Creation date: (8/7/01 5:11:31 PM)
 * @param xmlFileName java.lang.String
 * @param _ds java.lang.String
 */
public static DataStore xmlImportMetaDataAsData(String xmlFileName, DataStore _ds, String tablename, boolean toUpdate)
{
    try
        {
 
        XMLStoreParser.parseFile(xmlFileName);

        XMLDataStore xds = XMLStoreParser.getDataStore();

        if (_ds != null)
            {
            ResultSetMetaData rsm = xds.getRsm();

            if (rsm != null)
                {
                String tableNameTFC = "";
                for (int i = 0; i < rsm.getMetaData().size(); i++)
                    {
                    // Create a Row out of ColumnMetaData
                    // Create the column descriptors
                    ColumnMetaData cmd = (ColumnMetaData) rsm.getMetaData().elementAt(i);

                    if (tablename != null && !tablename.equals(""))
                        {
                        tableNameTFC = tablename + ".";
                    }
                    else
                        {
                        tableNameTFC = "";
                        tablename = "";
                    }

                    Row row = new Row();
                    row.put(tableNameTFC + "component", new Column(tablename, "component", cmd.getComponent()));
                    row.put(tableNameTFC + "componenttype", new Column(tablename, "componenttype", cmd.getComponentType()));
                    row.put(tableNameTFC + "internalname", new Column(tablename, "internalname", cmd.getInternalName()));

                    row.put(tableNameTFC + "columnname", new Column(tablename, "columnname", cmd.getColumnName()));
                    row.put(tableNameTFC + "columntype", new Column(tablename, "columntype", cmd.getColumnType()));
                    row.put(tableNameTFC + "tablename", new Column(tablename, "tablename", cmd.getTable()));
                    row.put(tableNameTFC + "schemaname", new Column(tablename, "schemaname", cmd.getSchema()));
                    row.put(tableNameTFC + "isbucket", new Column(tablename, "isbucket", getBooleanValue(cmd.isBucket())));

                    row.put(tableNameTFC + "nullable", new Column(tablename, "nullable", getBooleanValue(cmd.isNullable())));
                    row.put(tableNameTFC + "primarykey", new Column(tablename, "primarykey", getBooleanValue(cmd.isPrimaryKey())));
                    row.put(tableNameTFC + "updateable", new Column(tablename, "updateable", getBooleanValue(cmd.isUpdateable())));

                    row.put(tableNameTFC + "caption", new Column(tablename, "caption", cmd.getCaption()));
                    row.put(tableNameTFC + "searchdisplay", new Column(tablename, "searchdisplay", getBooleanValue(cmd.isSearchDisplay())));
                    row.put(tableNameTFC + "listdisplay", new Column(tablename, "listdisplay", getBooleanValue(cmd.isListDisplay())));
                    row.put(tableNameTFC + "detaildisplay", new Column(tablename, "detaildisplay", getBooleanValue(cmd.isDetailDisplay())));

                    row.put(tableNameTFC + "precedence", new Column(tablename, "precedence", getBooleanValue(cmd.isPrecedence())));
                    row.put(tableNameTFC + "exactmatch", new Column(tablename, "exactmatch", getBooleanValue(cmd.isExactMatch())));
                    row.put(tableNameTFC + "leadingwildcard", new Column(tablename, "leadingwildcard", getBooleanValue(cmd.isLeadingWildCard())));
                    row.put(tableNameTFC + "casesensitive", new Column(tablename, "casesensitive", getBooleanValue(cmd.isCaseSensitive())));
                    row.put(tableNameTFC + "mandatory ", new Column(tablename, "mandatory ", getBooleanValue(cmd.isMandatory())));

                    row.put(tableNameTFC + "locked", new Column(tablename, "locked", getBooleanValue(cmd.isLocked())));
                    row.put(tableNameTFC + "format", new Column(tablename, "format", cmd.getFormat()));
                    row.put(tableNameTFC + "href", new Column(tablename, "href", cmd.getHref()));
                    row.put(tableNameTFC + "advancesearch", new Column(tablename, "advancesearch", getBooleanValue(cmd.isAdvanceSearch())));
                    row.put(tableNameTFC + "readonly", new Column(tablename, "readonly", getBooleanValue(cmd.isReadOnly())));
                    row.put(tableNameTFC + "defaultvalue", new Column(tablename, "defaultvalue", cmd.getDefaultValue()));
                    row.put(tableNameTFC + "samerow", new Column(tablename, "samerow", getBooleanValue(cmd.isSameRow())));
                    row.put(tableNameTFC + "imageFile", new Column(tablename, "imageFile", cmd.getImageFile()));
                    //                          row.put(tableNameTFC + "jointo", new Column(tablename, "jointo", cmd.ImageFile()));

                    _ds.importRow(row);
                }
                _ds.setPrimaryKey(tableNameTFC + "columnname", true);

                if (toUpdate)
                    _ds.update();
            } // if rsm != null
        }
        else
            {
            throw new Exception("Datastore not initiallized.");
        }
    }
    catch (Exception e)
        {
        System.out.println(e + " : " + e.getMessage());
    }

    return _ds;
}
}