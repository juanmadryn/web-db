package com.salmonllc.jsp.controller;

/////////////////////////
//$Archive: /sofia/sourcecode/com/salmonllc/jsp/controller/FormBinder.java $
//$Author: Deepak $
//$Revision: 49 $
//$Modtime: 10/28/03 10:16a $
/////////////////////////
import java.util.Hashtable;
import java.util.Vector;

import com.salmonllc.forms.DetailForm;
import com.salmonllc.forms.ListForm;
import com.salmonllc.html.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import com.salmonllc.xml.*;



/**
 * FormBinder is used to bind various components in List and DetailForm to their XML formats.
 * Creation date: (7/20/01 4:30:05 PM)
 * @author Deepak Agarwal
 */
public class FormBinder extends DataBinder
{
    private String fieldXMLFileName = "";
    /**
     * ListFormBinder constructor comment.
     */
    public FormBinder(String xmlFileName) {
        super();
        fieldXMLFileName = xmlFileName;
    }
    /**
     * This method binds the detail form component to the data definitions specified by XML file
     * Creation date: (7/20/01 4:33:39 PM)
     * @return com.salmonllc.forms.ListForm
     */
    public com.salmonllc.forms.DetailForm bindDetailForm(com.salmonllc.forms.DetailForm detailForm, BaseDetailController page)
    {

        try
        {
            XMLStoreParser parser = new XMLStoreParser(false);
            XMLStoreParser.parseFile(getXMLFileName());

            ResultSetMetaData rsm = parser.getMetaData();
            DataStore _ds = detailForm.getDataStore();

            HtmlComponent lastCompAdded = null;

            page.setUserComponents(new Hashtable());

            ColumnMetaData cmd = null;

            String componentType = null;

            String component = null;
            String table = null;
            String column = null;
            String caption = null;
            String maxlength = null;
            String size = null;
            int type = -1;
            int flags = -1;
            String href = null;

            Options values = null;
            Options params = null;

            String jointo = null;

            HtmlLink lnk = null;

            String fullColName = null;

            Vector vct = rsm.getMetaData();
            for (int i = 0; i < vct.size(); i++)
            {
                lastCompAdded = null;
                cmd = (ColumnMetaData) vct.elementAt(i);

                component = cmd.getComponent();
                componentType = cmd.getComponentType();

                table = cmd.getTable();
                column = cmd.getColumnName();
                caption = cmd.getCaption();

                /** assigning max length */
                maxlength = cmd.getMaxLength();
                int maxLengthValue = 0;
                if(maxlength != null)
                    maxLengthValue = (new Integer(maxlength)).intValue();

                /** ASsigning size */
                size = cmd.getSize();
                int sizeValue = 0;
                if(size != null)
                    sizeValue = (new Integer(size)).intValue();

                type = mapDataType(cmd.getColumnType());
                jointo = cmd.getJoinTo();

                /** Create the Flags for the detail form */
                flags = createDetailMode(cmd);

                href = cmd.getHref();
                if (Util.isFilled(href))
                    href = "'" + href + "&id='+" + page.getPrimaryKey(detailForm.getDataStore());

                // create a full column name
                fullColName = createFulColumnName(table, column);

                if (!cmd.isBucket() && !cmd.isNotBound())
                {
                    if (detailForm.getDataStore().getColumnIndex(fullColName) < 0)
                    {
                        // add the column to datastore
                        detailForm.getDataStore().addColumn(fullColName, type, cmd.isPrimaryKey(), true);
                        if( cmd.isPrimaryKey() )
                            detailForm.getDataStore().setAutoIncrement(fullColName, cmd.isAutoIncrement());

                        if (Util.isFilled(jointo))
                        {
                            detailForm.getDataStore().addJoin(fullColName, jointo, false);
                        }
                    }
                }

                if (cmd.isDetailDisplay())
                {
                    values = cmd.getValues();
                    if (values != null)
                    {
                        String comp = values.getComponent();
                        String valuesType = values.getType();
                        boolean isEmptyRequried = values.isMandatory(); // check for flags
                        if (values.isMandatory())
                            flags |= DetailForm.IS_REQUIRED;
                        if (comp != null && comp.equalsIgnoreCase("IntegerDropDown"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                            {
                                lastCompAdded = detailForm.addIntegerDropDown(table, column, caption, flags, values.getIntKeys(), values.getStringObjects(), isEmptyRequried);
                                ((HtmlDropDownList)lastCompAdded).sort();
                            }
                            else
                            {
                                lastCompAdded = detailForm.addPreInitDropDown(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn(), null, null);
                                ((HtmlDropDownList)lastCompAdded).sort();
                            }
                        }
                        else if (comp != null && comp.equalsIgnoreCase("IntegerRadioButtonGroup"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                                lastCompAdded = detailForm.addIntegerRadioButtonGroup(table, column, caption, flags, values.getIntKeys(), values.getStringObjects());
                            else
                                lastCompAdded = detailForm.addPreInitRadioButtonGroup(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn(), null, null);
                        }
                        else if (comp != null && comp.equalsIgnoreCase("StringRadioButtonGroup"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                                lastCompAdded = detailForm.addStringRadioButtonGroup(table, column, caption, flags, values.getStringKeys(), values.getStringObjects());
                            else
                                lastCompAdded = detailForm.addPreInitStringRadioButtonGroup(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                        }
                        else if (comp != null && comp.equalsIgnoreCase("StringDropDown"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                            {
                                lastCompAdded = detailForm.addStringDropDown(table, column, caption, flags, values.getStringKeys(), values.getStringObjects(), isEmptyRequried);
                                ((HtmlDropDownList)lastCompAdded).sort();
                            }
                            else
                            {
                                lastCompAdded = detailForm.addPreInitStringDropDown(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                                ((HtmlDropDownList)lastCompAdded).sort();
                            }

                        }
                    }
                    else
                    {
                        if (componentType != null && componentType.equalsIgnoreCase("HtmlLink"))
                        {
                            lnk = new HtmlLink(column, "", page);
                            if (Util.isFilled(href))
                            {
                                lnk.setHrefExpression(_ds, href);
                            }
                            if (cmd.getImageFile() != null && !cmd.getImageFile().equalsIgnoreCase(""))
                            {
                                lnk.add(new HtmlImage(cmd.getImageFile(), page));
                            }

                            detailForm.addComponent(caption, lnk, flags);
                            lastCompAdded = lnk;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlTextEdit"))
                        { // create active only checkbox
                            HtmlTextEdit hte = new HtmlTextEdit(fullColName, page);
                            hte.setColumn(_ds, fullColName);

                            if(maxLengthValue != 0)
                            {
                                hte.setMaxLength(maxLengthValue);
                                if(sizeValue != 0)
                                {
                                    hte.setSize(sizeValue);
                                }
                            }

                            params = cmd.getParms();
                            if (params != null)
                            {

                                java.util.Enumeration enumera = params.keys();
                                while (enumera.hasMoreElements())
                                {
                                    String propertyName = enumera.nextElement().toString();
                                    Object keyval = params.get(propertyName);
                                    Util.executeMethod(hte, propertyName, keyval);
                                }
                            }

                            detailForm.addComponent(caption, hte, flags);
                            lastCompAdded = hte;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlCheckBox"))
                        { // create active only checkbox
                            HtmlCheckBox _chkBox = new HtmlCheckBox(column, page, "1", "0"); //     _chkBox.setValue("1");
                            detailForm.addComponent(caption, _chkBox, flags);
                            lastCompAdded = _chkBox;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlSSNComponent"))
                        { // create active only checkbox
                            HtmlSSNComponent _ssnComp = new HtmlSSNComponent(column, page, "-", true);
                            _ssnComp.setColumn(_ds, column);
                            detailForm.addComponent(caption, _ssnComp, flags);
                            lastCompAdded = _ssnComp;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlMultiLineEdit"))
                        {
                            HtmlMultiLineTextEdit _textEdit = new HtmlMultiLineTextEdit(column, page);
                            _textEdit.setColumn(_ds, fullColName);

                            if(maxLengthValue != 0)
                                _textEdit.setColumns(maxLengthValue);
                            else
                                _textEdit.setColumns(75);

                            if(sizeValue != 0)
                                _textEdit.setRows(sizeValue);

                            detailForm.addComponent(caption, _textEdit, flags);
                            lastCompAdded = _textEdit;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlDateComponent"))
                        {
                            HtmlDateComponent dc = new HtmlDateComponent(column, page, -1);
                            dc.setColumn(_ds, fullColName);
                            detailForm.addComponent(caption, dc, flags);
                            lastCompAdded = dc;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlStateComponent"))
                        {
                            HtmlStateComponent sc = new HtmlStateComponent(column, page, true);
                            sc.setColumn(_ds, fullColName);
                            detailForm.addComponent(caption, sc, flags);
                            lastCompAdded = sc;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlEMailComponent"))
                        {
                            HtmlEMailComponent em = new HtmlEMailComponent(column, page);
                            em.setColumn(_ds, fullColName);
                            detailForm.addComponent(caption, em, flags);
                            lastCompAdded = em;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlZipCodeComponent"))
                        {
                            HtmlZipCodeComponent zc = new HtmlZipCodeComponent(column, page);
                            zc.setColumn(_ds, fullColName);
                            detailForm.addComponent(caption, zc, flags);
                            lastCompAdded = zc;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlTelephoneComponent"))
                        {
                            HtmlTelephoneComponent tc = new HtmlTelephoneComponent(column, page);
                            tc.setColumn(_ds, fullColName);
                            detailForm.addComponent(caption, tc, flags);
                            lastCompAdded = tc;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlFileUpload"))
                        {
                            HtmlFileUpload fu = new HtmlFileUpload(column, page);
                            fu.setColumns(_ds, null, fullColName, null, null);
                            detailForm.addComponent(caption, fu, flags);
                            fu.addFileUploadListener(detailForm);
                            detailForm.setFileUploadDirectory(page.getPageProperties().getProperty("FILE_UPLOAD_DIRECTORY", "/"));
                            page.setFormType(HtmlPage.FORMTYPE_MULTIPART);
                            lastCompAdded = fu;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlSubmitButton"))
                        {
                            HtmlSubmitButton button = new HtmlSubmitButton(column, cmd.getColumnName(), page);
                            button.setOnClick(cmd.getOnClick());
                            detailForm.addComponent(null, button, flags);
                            button.addSubmitListener(page);
                            lastCompAdded = button;
                        }
                        else
                        {
                            lastCompAdded = detailForm.addColumn(table, column, caption, cmd.getDefaultValue(), type, flags);
                        }

                    } // ENd of If else of Values or no Values
                    // assign the userComponents
                    if (component != null && lastCompAdded != null)
                    {
                        if (page.getUserComponents() == null)
                            page.setUserComponents(new Hashtable());
                        page.getUserComponents().put(component, lastCompAdded);
                    }

                } // if isDetail Display
            } // For loop
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            MessageLog.writeErrorMessage("bindDetailForm", e, this);
        }
        catch (java.lang.Exception e)
        {
            MessageLog.writeErrorMessage("bindDetailForm", e, this);
        }

        return detailForm;
    }
    /**
     * This method binds the list form component to the data definitions specified by XML file
     * Creation date: (7/20/01 4:33:39 PM)
     * @return com.salmonllc.forms.ListForm
     */
    public com.salmonllc.forms.ListForm bindListForm(com.salmonllc.forms.ListForm ls, BaseListController page, String querystring)
    {

        try
        {
            XMLStoreParser parser = new XMLStoreParser(false);
            XMLStoreParser.parseFile(getXMLFileName());

            DataStore _ds = ls.getDataStore();
            ResultSetMetaData rsm = parser.getMetaData();
            Vector vct = rsm.getMetaData();
            HtmlComponent lastCompAdded = null;

            page.setUserComponents(new Hashtable());

            ColumnMetaData cmd = null;

            String componentType = null;
            String component = null;
            String size= null;
            String maxlength = null;
            String table = null;
            String column = null;
            String caption = null;
            int type = -1;
            int flags = -1;
            String href = null;

            String fullColName = null;
            String format = null;
            String jointo = null;

            Options values = null;
            Options params = null;
            HtmlLink lnk = null;
            boolean bIsOrderBy=false;
            StringBuffer sbOrderBy = new StringBuffer();

            for (int i = 0; i < vct.size(); i++)
            {
                lastCompAdded = null;
                cmd = (ColumnMetaData) vct.elementAt(i);

                componentType = cmd.getComponentType();
                component = cmd.getComponent();
                table = cmd.getTable();
                column = cmd.getColumnName();
                maxlength = cmd.getMaxLength();
                size = cmd.getSize();
                caption = cmd.getCaption();
                type = mapDataType(cmd.getColumnType());
                flags = createListMode(cmd);
                href = cmd.getHref();
                jointo = cmd.getJoinTo();
                bIsOrderBy=cmd.isOrderBy();

                if (Util.isFilled(href))
                    href += "&" + querystring;

                format = cmd.getFormat();

                // create a full column name
                fullColName = createFulColumnName(table, column);

                if(bIsOrderBy){
                    sbOrderBy.append(fullColName);
                    sbOrderBy.append(",");
                }

                if (!cmd.isBucket() && !cmd.isNotBound())
                {
                    if (_ds.getColumnIndex(fullColName) < 0)
                    {
                        _ds.addColumn(fullColName, type, cmd.isPrimaryKey(), cmd.isUpdateable());
                        if (Util.isFilled(jointo))
                        {
                            _ds.addJoin(fullColName, jointo, false);
                        }
                    }
                }
                if(cmd.isListDisplay() || cmd.isSearchDisplay())
                {
                    values = cmd.getValues();

                    if (values == null)
                    {
                        /*if(bIsOrderBy){
                        sbOrderBy.append(fullColName);
                        sbOrderBy.append(",");
                        }*/
                        if (componentType != null && componentType.equalsIgnoreCase("HtmlLink"))
                        {
                            lnk = new HtmlLink(fullColName, "", page);
                            if (Util.isFilled(href))
                            {
                                lnk.setHrefExpression(_ds, href);
                            }
                            if (cmd.getImageFile() != null && !cmd.getImageFile().equalsIgnoreCase(""))
                            {
                                lnk.add(new HtmlImage(cmd.getImageFile(), page));
                            }

                            if ((flags & ListForm.LIST_ONLY) == 0)
                            {
                                ls.addSearchDisplay(fullColName, caption, lnk, flags);
                            }
                            else if ((flags & ListForm.SEARCH_ONLY) == 0)
                            {
                                ls.addListDisplay(fullColName, caption, lnk);
                            }

                            lastCompAdded = lnk;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlCheckBox"))
                        {
                            // create active only checkbox
                            HtmlCheckBox _chkBox = new HtmlCheckBox(fullColName, page, "1", "0");
                            _chkBox.setValue("1");

                            if ((flags & ListForm.LIST_ONLY) == 0)
                            {
                                ls.addSearchDisplay(fullColName, caption, _chkBox, flags);
                            }
                            else if ((flags & ListForm.SEARCH_ONLY) == 0)
                            {
                                ls.addListDisplay(fullColName, caption, _chkBox);
                            }
                            lastCompAdded = _chkBox;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlTelephoneComponent"))
                        {
                            // create active only checkbox
                            HtmlTelephoneComponent tc = new HtmlTelephoneComponent(fullColName, page);

                            if ((flags & ListForm.LIST_ONLY) == 0)
                            {
                                ls.addSearchDisplay(fullColName, caption, tc, flags);
                            }
                            else if ((flags & ListForm.SEARCH_ONLY) == 0)
                            {
                                tc.setColumn(_ds, fullColName);
                                tc.setEnabled(false);
                                ls.addListDisplay(fullColName, caption, tc);
                            }
                            lastCompAdded = tc;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlSSNComponent"))
                        {
                            // create active only checkbox
                            HtmlSSNComponent _ssnComp = new HtmlSSNComponent(fullColName, page, "-", true);
                            _ssnComp.setColumn(_ds, column);

                            if ((flags & ListForm.LIST_ONLY) == 0)
                            {
                                ls.addSearchDisplay(fullColName, caption, _ssnComp, flags);
                            }
                            else if ((flags & ListForm.SEARCH_ONLY) == 0)
                            {
                                ls.addListDisplay(fullColName, caption, _ssnComp);
                            }
                            lastCompAdded = _ssnComp;
                        }
                        else if (componentType != null && componentType.equalsIgnoreCase("HtmlTextEdit"))
                        {
                            // create text edit
                            HtmlTextEdit hte = new HtmlTextEdit(fullColName, page);
                            if(maxlength != null)
                            {
                                try{
                                    int maxLengthValue = (new Integer(maxlength)).intValue();
                                    hte.setMaxLength(maxLengthValue);

                                    if(size != null)
                                    {
                                        int sizeValue = (new Integer(size)).intValue();
                                        hte.setSize(sizeValue);
                                    }
                                }
                                catch(NumberFormatException e)
                                {
                                    // Let it set to default
                                }
                            }
                            params = cmd.getParms();
                            if (params != null)
                            {

                                java.util.Enumeration enumera = params.keys();
                                while (enumera.hasMoreElements())
                                {
                                    String propertyName = enumera.nextElement().toString();
                                    Object keyval = params.get(propertyName);
                                    Util.executeMethod(hte, propertyName, keyval);
                                }
                            }

                            if ((flags & ListForm.LIST_ONLY) == 0)
                            {
                                ls.addSearchDisplay(fullColName, caption, hte, flags);
                            }

                            if (cmd.isDetailDisplay())
                            {
                                if ((flags & ListForm.SEARCH_ONLY) == 0)
                                {
                                    HtmlLink hl;

                                    HtmlText ht = new HtmlText(table + "_" + column, page);
                                    if (format == null)
                                    {
                                        // For certain data types, set format according to page properties.
                                        Props props = page.getPageProperties();
                                        switch (type)
                                        {
                                            case DataStore.DATATYPE_DATETIME :
                                                format = props.getProperty(Props.DATETIME_FORMAT);
                                                break;
                                            case DataStore.DATATYPE_DATE :
                                                format = props.getProperty(Props.DATE_FORMAT);
                                                break;
                                            case DataStore.DATATYPE_TIME :
                                                format = props.getProperty(Props.TIME_FORMAT);
                                                break;
                                        }
                                    }
                                    if (format != null)
                                    {
                                        ht.setExpression(_ds, fullColName, format);
                                    }
                                    else
                                    {
                                        ht.setExpression(_ds, fullColName);
                                    }
                                    if (Util.isFilled(href))
                                    {
                                        hl = new HtmlLink("lnk" + column, "", page);
                                        hl.setHrefExpression(_ds, href);
                                        hl.add(ht);
                                        ht.setFont(HtmlText.FONT_LINK);
                                        ls.addListDisplay(fullColName, caption, hl, null);
                                    }
                                    else
                                    {
                                        ls.addListDisplay(fullColName, caption, ht, null);
                                    }
                                }

                            }
                            else
                            {
                                if ((flags & ListForm.SEARCH_ONLY) == 0)
                                {
                                    ls.addListDisplay(fullColName, caption, hte);
                                }

                            }
                            lastCompAdded = hte;
                        }
                        else
                        {
                            if (Util.isFilled(href))
                            {
                                if (cmd.isPrimaryKey())
                                    href = "'" + href + "&" + cmd.getColumnName() + "='+" + fullColName;
                                else
                                    href = "'" + href + "&" + page.getPrimaryKeyName(_ds) + "='+" + page.getPrimaryKey(_ds);
                            }

                            // add the columns to ListForm
                            ls.addColumn(table, column, caption, type, flags, href, format, null, null, null);
                        }
                    }
                    else
                    {
                        String comp = values.getComponent();
                        String valuesType = values.getType();
                        if (values.getTable() == null)
                        {
                            values.setTable(table);
                        }
                        if (values.getInitColumn() == null)
                        {
                            values.setInitColumn(column);
                        }
                        if (values.getDescColumn() == null)
                        {
                            values.setDescColumn(column);
                        }

                        if (comp != null && comp.equalsIgnoreCase("IntegerDropDown"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                                lastCompAdded = ls.addIntegerDropDown(table, column, caption, flags, values.getIntKeys(), values.getStringObjects(), values.isMandatory());
                            else
                            {
                                lastCompAdded = ls.addPreInitDropDown(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                            }

                        }
                        else if (comp != null && comp.equalsIgnoreCase("IntegerRadioButtonGroup"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                                lastCompAdded = ls.addIntegerRadioButtonGroup(table, column, caption, flags, values.getIntKeys(), values.getStringObjects());
                            else
                                lastCompAdded = ls.addPreInitRadioButtonGroup(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                        }
                        else if (comp != null && comp.equalsIgnoreCase("StringRadioButtonGroup"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                                lastCompAdded = ls.addStringRadioButtonGroup(table, column, caption, flags, values.getStringKeys(), values.getStringObjects());
                            else
                                lastCompAdded = ls.addPreInitStringRadioButtonGroup(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                        }
                        else if (comp != null && comp.equalsIgnoreCase("StringDropDown"))
                        {
                            if (valuesType != null && valuesType.equalsIgnoreCase("Static"))
                            {
                                lastCompAdded = ls.addStringDropDown(table, column, caption, flags, values.getStringKeys(), values.getStringObjects(), values.isMandatory());
                            }
                            else
                            {
                                lastCompAdded = ls.addPreInitStringDropDown(table, column, caption, flags, values.getTable(), values.getInitColumn(), values.getDescColumn());
                            }

                        }
                    }
                    // assign the userComponents
                    if (component != null && lastCompAdded != null)
                    {
                        if (page.getUserComponents() == null)
                            page.setUserComponents(new Hashtable());

                        page.getUserComponents().put(component, lastCompAdded);
                    }

                } // Not a Bucket
                else
                {
                    // Bucket
                    ls.addBucket(column, caption, type, flags, href, format, null, null, null);
                    if(bIsOrderBy){
                        sbOrderBy.append(column);
                        sbOrderBy.append(",");
                    }
                }
            } // if check for if the display for search and list is set to true

            if(sbOrderBy!=null && sbOrderBy.length()>0){
                // srufle 03-19-2003	int idx = sbOrderBy.lastIndexOf(","); // this is jdk1.4 specific
                int idx = 0;
                if(idx>0){
                    _ds.setOrderBy(sbOrderBy.substring(0, idx));
                }
            }

        }
        catch (java.io.UnsupportedEncodingException e)
        {
            MessageLog.writeErrorMessage("bindListForm", e, this);
        }
        catch (java.lang.Exception e)
        {
            MessageLog.writeErrorMessage("bindListForm", e, this);
        }

        return ls;
    }
    /**
     * Creation date: (7/25/01 10:57:57 AM)
     * @return int
     * @param cmd com.salmonllc.xml.ColumnMetaData
     */
    private int createDetailMode(ColumnMetaData cmd) {

        int mode = 0;

        if(cmd.isPrimaryKey())
            mode |= DetailForm.PRIMARY_KEY;

        if(cmd.isMandatory())
            mode |= DetailForm.IS_REQUIRED;

        if(cmd.isSameRow())
            mode |= DetailForm.SAME_ROW;

        if(cmd.isBucket())
            mode |= DetailForm.BUCKET;

        if(cmd.isReadOnly())
            mode |= DetailForm.READ_ONLY;

        if(cmd.isNotBound())
            mode |= DetailForm.NO_DATASTORE;

        return mode;
    }
    /**
     * Creation date: (7/20/01 4:32:35 PM)
     * @return java.lang.String
     */
    private java.lang.String createFulColumnName(String table, String column)
    {
        boolean tablefilled = false;

        String ret = "";
        if (Util.isFilled(table))
        {
            tablefilled = true;
            ret += table;
        }
        if (Util.isFilled(column) && tablefilled)
        {
            ret += "." + column;
        }
        else
        {
            ret = column;
        }
        return ret;
    }
    /**
     * Creation date: (7/25/01 10:57:57 AM)
     * @return int
     * @param cmd com.salmonllc.xml.ColumnMetaData
     */
    private int createListMode(ColumnMetaData cmd) {

        int mode = 0;

        if(cmd.isSearchDisplay() && !cmd.isListDisplay())
            mode = ListForm.SEARCH_ONLY;

        if(cmd.isListDisplay() && !cmd.isSearchDisplay())
            mode = ListForm.LIST_ONLY;

        if(!cmd.isSearchDisplay() && ! cmd.isListDisplay())
        {
            mode |= ListForm.SEARCH_ONLY;
            mode |= ListForm.LIST_ONLY;
        }

        if(cmd.isPrimaryKey())
            mode |= ListForm.PRIMARY_KEY;

        if(cmd.isPrecedence())
            mode |= ListForm.PRECEDENCE;

        if(cmd.isExactMatch())
            mode |= ListForm.EXACT_MATCH;

        if(!cmd.isCaseSensitive())
            mode |= ListForm.IGNORE_CASE;

        if(cmd.isAdvanceSearch())
            mode |= ListForm.ADVANCED_SEARCH;

        if(cmd.isExactMatch())
            mode |= ListForm.EXACT_MATCH;

        if(cmd.isLeadingWildCard())
            mode |= ListForm.LEADING_WILDCARD;

        if(cmd.isSameRow())
            mode |= ListForm.SAME_ROW;

        return mode;
    }
    /**
     * Creation date: (7/20/01 4:32:35 PM)
     * @return java.lang.String
     */
    private java.lang.String getXMLFileName() {
        return fieldXMLFileName;
    }

}
