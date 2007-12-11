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

import com.salmonllc.properties.Props;
import com.salmonllc.sql.ColumnDefinition;
import com.salmonllc.sql.DataDictionary;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.Util;
import com.salmonllc.xml.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author  demian
 */
public class ListFormDialog extends JDialog implements ActionListener, ListSelectionListener, CaretListener,KeyListener {

    JButton _export;
    JButton _import;
    JButton _cancel;
    JPanel _main;
    JTextField _xmlName;
    JComboBox _tables;
    JComboBox _bucketType;
    JComboBox _colCompType;
    JList _colList;
    JList _colsInListForm;
    JTextField _colBucket;
    JTextField _colFormat;
    JTextField _colCaption;
    JTextField _colCompName;
    JTextField _colHref;
    JButton _addCol;
    JButton _delCol;
    JButton _updateCol;
    JButton _fileButton;
    JButton _up;
    JButton _down;
    JButton _clear;
    JCheckBox _updateable;
    JCheckBox _searchDisplay;
    JCheckBox _listDisplay;
    JCheckBox _detailDisplay;
    JCheckBox _mandatory;
    JCheckBox _readonly;
    JCheckBox _locked;
    JCheckBox _precedence;
    JCheckBox _exactmatch;
    JCheckBox _leadingwildcard;
    JCheckBox _casesensitive;
    JCheckBox _nullable;
    JCheckBox _primarykey;
    DataDictionary _dd;
    Frame _owner;
    boolean _clickedCancel = true;
    DataStore _ds=new DataStore();

    /**
	 * @author  demian
	 */
    public class DataType {
        public int type;
        public DataType(int type) {
            this.type = type;
        }
        public DataType(String sType) {
            this.type = XMLTransporter.mapDataType(sType);
        }
        public String toString() {
            if (type == DataStore.DATATYPE_BYTEARRAY)
                return "binary";
            else if (type == DataStore.DATATYPE_DATE)
                return "date";
            else if (type == DataStore.DATATYPE_DATETIME)
                return "datetime";
            else if (type == DataStore.DATATYPE_DOUBLE)
                return "double";
            else if (type == DataStore.DATATYPE_FLOAT)
                return "float";
            else if (type == DataStore.DATATYPE_INT)
                return "int";
            else if (type == DataStore.DATATYPE_LONG)
                return "long";
            else if (type == DataStore.DATATYPE_SHORT)
                return "short";
            else if (type == DataStore.DATATYPE_STRING)
                return "string";
            else if (type == DataStore.DATATYPE_TIME)
                return "time";
            else
                return "unknown";
        }

        /**
		 * @return  the type
		 * @uml.property  name="type"
		 */
        public int getType() {
            return type;
        }
    }

    /**
	 * @author  demian
	 */
    public class ColumnDef {
        public ColumnDefinition def;
        public String bucket;
        public DataType bucketType;
        public String caption;
        public String compname;
        public String comptype;
        public String format;
        public String hrefexpression;
        public boolean detaildisplay;
        public boolean listdisplay;
        public boolean searchdisplay;
        public boolean locked;
        public boolean mandatory;
        public boolean readonly;
        public boolean casesensitive;
        public boolean exactmatch;
        public boolean leadingwildcard;
        public boolean precedence;
        public boolean nullable;
        public boolean primarykey;
        public boolean updateable;
        private int DISPLAY_SHORT = 0;
        private int DISPLAY_LONG = 1;
        private int DISPLAY_FULL = 2;
        private int _display = DISPLAY_SHORT;

        public ColumnDef(ColumnDefinition def, boolean displayShort) {
            this.def = def;
            _display = displayShort ? DISPLAY_SHORT : DISPLAY_FULL;
        }

        public String getColumnName() {
            if (def==null)
              return bucket;
            else
              return def.getColumnName();
        }

        public String getTableName() {
            if (def==null)
              return "";
            else
              return def.getTableName();
        }

        public String getColumnType() {
            if (def==null)
              return bucketType.toString();
            else
              return (new DataType(def.getDSDataType())).toString();
        }

        public boolean isBucket() {
            if (def==null)
              return true;
            return false;
        }

        public String toString() {
            if (_display == DISPLAY_SHORT) {
                if (def==null)
                  return bucket;
                else
                  return def.getColumnName();
            }
            else if (_display == DISPLAY_LONG) {
                if (def==null)
                  return bucket;
                else
                  return def.getTableName()+"."+def.getColumnName();
            }
            else if (_display == DISPLAY_FULL) {
                String tab;
                String nme;
                if (def==null) {
                    tab="";
                    nme=bucket;
                }
                else {
                    tab = def.getTableName()==null?"":def.getTableName();
                    nme = tab == "" ? def.getColumnName(): tab + "." + def.getColumnName();
                }
                String cap = (caption==null || caption.length()==0)?"":" | caption:"+caption;
                String cname = (compname==null || compname.length()==0)?"":" | component name:"+compname;
                String ctype = (comptype==null || comptype.length()==0)?"":" | component type:"+comptype;
                String fmt = (format == null || format.length()==0) ? "" : " | format:" + format;
                String href = (hrefexpression == null || hrefexpression.length()==0) ? "" : " | Href:" + hrefexpression;
                String ddisp = detaildisplay ? " | detail display:yes" : " | detail display:no";
                String ldisp = listdisplay ? " | list display:yes" : " | list display:no";
                String sdisp = searchdisplay ? " | search display:yes" : " | search display:no";
                String lock = locked ? " | locked:yes" : " | locked:no";
                String mand = mandatory ? " | mandatory:yes" : " | mandatory:no";
                String ronly = readonly ? " | read only:yes" : " | read only:no";
                String csens = casesensitive ? " | case sensitive:yes" : " | case sensitive:no";
                String ematch = exactmatch ? " | exact match:yes" : " | exact match:no";
                String lcard =  leadingwildcard? " | leading wildcard:yes" : " | leading wildcard:no";
                String pre = precedence ? " | precedence:yes" : " | precedence:no";
                String nul = nullable ? " | nullable:yes" : " | nullable:no";
                String pkey = primarykey ? " | primary key:yes" : " | primary key:no";
                String upd = updateable ? " | update:yes" : " | update:no";
                return nme + cap + cname + ctype + fmt + href + ddisp + ldisp + sdisp + lock + mand + ronly + csens +
                       ematch + lcard + pre + nul + pkey + upd;
            }
            return "";

        }

        /**
		 * @param format  the format to set
		 * @uml.property  name="format"
		 */
        public void setFormat(String format) {
            this.format = format;
        }

        /**
		 * @param comptype  the comptype to set
		 * @uml.property  name="comptype"
		 */
        public void setComptype(String comptype) {
            this.comptype = comptype;
        }

        /**
		 * @param compname  the compname to set
		 * @uml.property  name="compname"
		 */
        public void setCompname(String compname) {
            this.compname = compname;
        }

        /**
		 * @param caption  the caption to set
		 * @uml.property  name="caption"
		 */
        public void setCaption(String caption) {
            this.caption = caption;
        }

        /**
		 * @param hrefexpression  the hrefexpression to set
		 * @uml.property  name="hrefexpression"
		 */
        public void setHrefexpression(String hrefexpression) {
            this.hrefexpression = hrefexpression;
        }

        /**
		 * @param detaildisplay  the detaildisplay to set
		 * @uml.property  name="detaildisplay"
		 */
        public void setDetaildisplay(boolean detaildisplay) {
            this.detaildisplay = detaildisplay;
        }

        /**
		 * @param listdisplay  the listdisplay to set
		 * @uml.property  name="listdisplay"
		 */
        public void setListdisplay(boolean listdisplay) {
            this.listdisplay = listdisplay;
        }

        /**
		 * @param searchdisplay  the searchdisplay to set
		 * @uml.property  name="searchdisplay"
		 */
        public void setSearchdisplay(boolean searchdisplay) {
            this.searchdisplay = searchdisplay;
        }

        /**
		 * @param locked  the locked to set
		 * @uml.property  name="locked"
		 */
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        /**
		 * @param mandatory  the mandatory to set
		 * @uml.property  name="mandatory"
		 */
        public void setMandatory(boolean mandatory) {
            this.mandatory = mandatory;
        }

        /**
		 * @param readonly  the readonly to set
		 * @uml.property  name="readonly"
		 */
        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
        }

        /**
		 * @param casesensitive  the casesensitive to set
		 * @uml.property  name="casesensitive"
		 */
        public void setCasesensitive(boolean casesensitive) {
            this.casesensitive = casesensitive;
        }

        /**
		 * @param exactmatch  the exactmatch to set
		 * @uml.property  name="exactmatch"
		 */
        public void setExactmatch(boolean exactmatch) {
            this.exactmatch = exactmatch;
        }

        /**
		 * @param leadingwildcard  the leadingwildcard to set
		 * @uml.property  name="leadingwildcard"
		 */
        public void setLeadingwildcard(boolean leadingwildcard) {
            this.leadingwildcard = leadingwildcard;
        }

        /**
		 * @param precedence  the precedence to set
		 * @uml.property  name="precedence"
		 */
        public void setPrecedence(boolean precedence) {
            this.precedence = precedence;
        }

        /**
		 * @param nullable  the nullable to set
		 * @uml.property  name="nullable"
		 */
        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }

        /**
		 * @param primarykey  the primarykey to set
		 * @uml.property  name="primarykey"
		 */
        public void setPrimarykey(boolean primarykey) {
            this.primarykey = primarykey;
        }

        /**
		 * @param updateable  the updateable to set
		 * @uml.property  name="updateable"
		 */
        public void setUpdateable(boolean updateable) {
            this.updateable = updateable;
        }

        /**
		 * @param bucket  the bucket to set
		 * @uml.property  name="bucket"
		 */
        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        /**
		 * @param bucketType  the bucketType to set
		 * @uml.property  name="bucketType"
		 */
        public void setBucketType(DataType bucketType) {
            this.bucketType = bucketType;
        }

        /**
		 * @return  the caption
		 * @uml.property  name="caption"
		 */
        public String getCaption() {
            return caption;
        }

        public String getComponentName() {
            return compname;
        }

        public String getComponentType() {
            return comptype;
        }

        /**
		 * @return  the format
		 * @uml.property  name="format"
		 */
        public String getFormat() {
            return format;
        }

        public String getHrefExpression() {
            return hrefexpression;
        }

        public boolean getDetailDisplay() {
            return detaildisplay;
        }

        public boolean getListDisplay() {
            return listdisplay;
        }

        public boolean getSearchDisplay() {
            return searchdisplay;
        }

        /**
		 * @return  the locked
		 * @uml.property  name="locked"
		 */
        public boolean getLocked() {
            return locked;
        }

        /**
		 * @return  the mandatory
		 * @uml.property  name="mandatory"
		 */
        public boolean getMandatory() {
            return mandatory;
        }

        /**
		 * @return  the readonly
		 * @uml.property  name="readonly"
		 */
        public boolean getReadonly() {
            return readonly;
        }

        public boolean getCaseSensitive() {
            return casesensitive;
        }

        public boolean getExactMatch() {
            return exactmatch;
        }

        public boolean getLeadingWildcard() {
            return leadingwildcard;
        }

        /**
		 * @return  the precedence
		 * @uml.property  name="precedence"
		 */
        public boolean getPrecedence() {
            return precedence;
        }

        /**
		 * @return  the nullable
		 * @uml.property  name="nullable"
		 */
        public boolean getNullable() {
            return nullable;
        }

        public boolean getPrimaryKey() {
            return primarykey;
        }

        /**
		 * @return  the updateable
		 * @uml.property  name="updateable"
		 */
        public boolean getUpdateable() {
            return updateable;
        }

        /**
		 * @return  the bucket
		 * @uml.property  name="bucket"
		 */
        public String getBucket() {
            return bucket;
        }

        /**
		 * @return  the bucketType
		 * @uml.property  name="bucketType"
		 */
        public DataType getBucketType() {
            return bucketType;
        }

        public ColumnDefinition getColumnDefinition() {
            return def;
        }

        public void setColumnDefinition(ColumnDefinition def) {
            this.def = def;
        }
    }

    public class AliasDef {
        public String table;
        public String alias;
        public AliasDef(String table,String alias) {
            this.table = table;
            this.alias = alias;
        }
        public String toString() {
            if (alias == null)
                return table;
            else
                return alias + " ("+ table + ")";
        }
    }

public ListFormDialog(Frame owner)
{
    super(owner, "Generate Form XML", true);
    _owner = owner;
    setModal(true);
    setResizable(false);
    //create a data dictionary object
    _dd = new DataDictionary(Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP)));
    Props props = Props.getProps(Props.getSystemProps().getProperty(IDETool.getProjectProperty(Props.IDE_DEFAULT_FRAMEWORK_APP)), "");
    String xmlPath = props.getProperty(Props.XML_DATA_PATH);
    int width = 780;
    int height = 580;
    Dimension frameBounds = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (frameBounds.width - width) / 2;
    int y = (frameBounds.height - height) / 2;
    setBounds(x, y, width, height);
    JPanel box = new JPanel();

    //main box
    _main = new JPanel();
    _main.setLayout(new BoxLayout(_main, BoxLayout.Y_AXIS));
    _main.setBorder(new EmptyBorder(10, 10, 10, 10));

    //row 1, class name
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(DialogComponentFactory.makeLabel("XML File Name:", 150));
    box.add(_xmlName = DialogComponentFactory.makeTextField(150));
    box.add(Box.createRigidArea(new Dimension(2, 0)));
    box.add(_fileButton = DialogComponentFactory.makeButton("...", 40, 28));
    box.add(Box.createHorizontalGlue());
    _main.add(box);
    _main.add(Box.createRigidArea(new Dimension(0, 5)));

    //row 2, file drop down label, Columns in Data Store Label, Columns in sort label
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(DialogComponentFactory.makeLabel("Tables/Columns in DataBase:", 200));

    box.add(Box.createHorizontalGlue());
    _main.add(box);

    //row 3,  name, updatable, format
    _main.add(Box.createRigidArea(new Dimension(0, 5)));
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(_tables = DialogComponentFactory.makeComboBox(200));
    box.add(Box.createRigidArea(new Dimension(10, 0)));
    box.add(DialogComponentFactory.makeLabel("Bucket:", 87));
    box.add(_colBucket = DialogComponentFactory.makeTextField(44));
    box.add(Box.createRigidArea(new Dimension(2, 0)));
    box.add(DialogComponentFactory.makeLabel("Bucket Type:", 107));
    box.add(_bucketType = DialogComponentFactory.makeComboBox(150));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_BYTEARRAY));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_DATE));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_DATETIME));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_DOUBLE));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_FLOAT));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_INT));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_LONG));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_SHORT));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_STRING));
    _bucketType.addItem(new DataType(DataStore.DATATYPE_TIME));
    box.add(Box.createHorizontalGlue());
    _main.add(box);

    //row 4, Columns, in model, in sort
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(DialogComponentFactory.makeScrollPane(200, 170, _colList = DialogComponentFactory.makeList(new LModel())));
    JPanel box2Main = new JPanel();
    box2Main.setLayout(new BoxLayout(box2Main, BoxLayout.Y_AXIS));
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));

    JPanel box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Attributes:", 67));
    box2.add(Box.createHorizontalGlue());
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Caption:", 47));
    box2.add(_colCaption = DialogComponentFactory.makeTextField(44));
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Component Name:", 107));
    box2.add(_colCompName = DialogComponentFactory.makeTextField(44));
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Component Type:", 107));
    box2.add(_colCompType = DialogComponentFactory.makeComboBox(150));
    _colCompType.addItem("");
    _colCompType.addItem("HtmlCheckBox");
    _colCompType.addItem("HtmlDateComponent");
    _colCompType.addItem("HtmlEMailComponent");
    _colCompType.addItem("HtmlFileUpload");
    _colCompType.addItem("HtmlLink");
    _colCompType.addItem("HtmlMultiLineEdit");
    _colCompType.addItem("HtmlSSNComponent");
    _colCompType.addItem("HtmlStateComponent");
    _colCompType.addItem("HtmlTelephoneComponent");
    _colCompType.addItem("HtmlZipCodeComponent");
    box2.add(Box.createHorizontalGlue());
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Format:", 47));
    box2.add(_colFormat = DialogComponentFactory.makeTextField(44));
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Href Expression:", 107));
    box2.add(_colHref = DialogComponentFactory.makeTextField(44));
    box2.add(Box.createHorizontalGlue());
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Detail Display:", 107));
    box2.add(_detailDisplay = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("List Display:", 107));
    box2.add(_listDisplay = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Search Display:", 107));
    box2.add(_searchDisplay = new JCheckBox());
    box2.add(Box.createHorizontalGlue());
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Locked:", 107));
    box2.add(_locked = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Mandatory:", 107));
    box2.add(_mandatory = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Read Only:", 107));
    box2.add(_readonly = new JCheckBox());
    box2.add(Box.createHorizontalGlue());
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Nullable:", 107));
    box2.add(_nullable = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Primary Key:", 107));
    box2.add(_primarykey = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Updateable:", 107));
    box2.add(_updateable = new JCheckBox());
    box2.add(Box.createHorizontalGlue());
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));
    box2Main.add(box2);
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.X_AXIS));
    box2.add(Box.createRigidArea(new Dimension(10, 0)));
    box2.add(DialogComponentFactory.makeLabel("Case Sensitive:", 107));
    box2.add(_casesensitive = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Exact Match:", 107));
    box2.add(_exactmatch = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Leading WildCard:", 107));
    box2.add(_leadingwildcard = new JCheckBox());
    box2.add(Box.createRigidArea(new Dimension(2, 0)));
    box2.add(DialogComponentFactory.makeLabel("Precedence:", 107));
    box2.add(_precedence = new JCheckBox());
    box2.add(Box.createHorizontalGlue());
    box2Main.add(Box.createRigidArea(new Dimension(0, 5)));
    box2Main.add(box2);
    box.add(box2Main);
    box.add(Box.createHorizontalGlue());
    _main.add(Box.createRigidArea(new Dimension(0, 3)));
    _main.add(box);

    //row 3,  name, updatable, format
    _main.add(Box.createRigidArea(new Dimension(0, 5)));
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(DialogComponentFactory.makeLabel("Columns in ListForm:", 200));
    box.add(Box.createRigidArea(new Dimension(10, 0)));
    box.add(_addCol = DialogComponentFactory.makeButton("Add", 60, 28));
    box.add(_delCol = DialogComponentFactory.makeButton("Del", 60, 28));
    box.add(_updateCol = DialogComponentFactory.makeButton("Update", 75, 28));
    _clear = new JButton("Clear");
    _clear.addActionListener(this);
    box.add(Box.createRigidArea(new Dimension(50, 0)));
    box.add(_clear);
    box.add(Box.createHorizontalGlue());
    _main.add(box);

    _main.add(Box.createRigidArea(new Dimension(0, 5)));
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(DialogComponentFactory.makeScrollPane(675, 170, _colsInListForm = DialogComponentFactory.makeList(new LModel())));
    box2 = new JPanel();
    box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
    _up = DialogComponentFactory.makeButton("Up", 80, 28);
    _up.addActionListener(this);
    _down = DialogComponentFactory.makeButton("Down", 80, 28);
    _down.addActionListener(this);
    box2.add(_up);
    box2.add(_down);
    box2.add(Box.createVerticalGlue());
    box.add(box2);
    box.add(Box.createHorizontalGlue());
    _main.add(box);

    //row 8, button bar
    _export = new JButton("Save");
    _export.addActionListener(this);
    _import = new JButton("Open");
    _import.addActionListener(this);
    _cancel = new JButton("Cancel");
    _cancel.addActionListener(this);
    JPanel buttonBar = new JPanel();
    buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));
    buttonBar.add(_export);
    buttonBar.add(_import);
    buttonBar.add(_cancel);

    //add it to the screen
    Container cp = getContentPane();
    cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
    cp.add(_main);
    cp.add(Box.createVerticalGlue());
    cp.add(buttonBar);
    cp.add(Box.createRigidArea(new Dimension(0, 20)));

    //add listeners
    _colList.addListSelectionListener(this);
    _colsInListForm.addListSelectionListener(this);
    _tables.addActionListener(this);
    _addCol.addActionListener(this);
    _delCol.addActionListener(this);
    _updateCol.addActionListener(this);
    _colBucket.addKeyListener(this);
    _xmlName.addKeyListener(this);
    _fileButton.addActionListener(this);
    _colList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    _colsInListForm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //fill drop downs and init the buttons
    enableDisableButtons();
    loadTables();

    _ds.addColumn("component", DataStore.DATATYPE_STRING);
    _ds.addColumn("componenttype", DataStore.DATATYPE_STRING);
    _ds.addColumn("internalname", DataStore.DATATYPE_STRING);
    _ds.addColumn("columnname", DataStore.DATATYPE_STRING);
    _ds.addColumn("columntype", DataStore.DATATYPE_STRING);
    _ds.addColumn("tablename", DataStore.DATATYPE_STRING);
    _ds.addColumn("schemaname", DataStore.DATATYPE_STRING);
    _ds.addColumn("isbucket", DataStore.DATATYPE_STRING);
    _ds.addColumn("nullable", DataStore.DATATYPE_STRING);
    _ds.addColumn("detaildisplay", DataStore.DATATYPE_STRING);
    _ds.addColumn("searchdisplay", DataStore.DATATYPE_STRING);
    _ds.addColumn("listdisplay", DataStore.DATATYPE_STRING);
    _ds.addColumn("precedence", DataStore.DATATYPE_STRING);
    _ds.addColumn("exactmatch", DataStore.DATATYPE_STRING);
    _ds.addColumn("leadingwildcard", DataStore.DATATYPE_STRING);
    _ds.addColumn("casesensitive", DataStore.DATATYPE_STRING);
    _ds.addColumn("primarykey", DataStore.DATATYPE_STRING);
    _ds.addColumn("updateable", DataStore.DATATYPE_STRING);
    _ds.addColumn("caption", DataStore.DATATYPE_STRING);
    _ds.addColumn("href", DataStore.DATATYPE_STRING);
    _xmlName.setText(xmlPath);
    String sXmlFile = IDETool.getXmlFile();
    if (sXmlFile != null && IDETool.isXMLNameValid(sXmlFile) && (new File(sXmlFile)).exists())
        {
        _xmlName.setText(sXmlFile);
        importXML();
    }
    setVisible(true);
}
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _export)  {
            if (exportXML()) {
              _clickedCancel = false;
              setVisible(false);
            }
            else {
                IDETool.displayError("Export Failed!");
            }
        }
        else if (e.getSource() == _import)  {
            importXML();
        }
        else if (e.getSource() == _cancel) {
            _clickedCancel = true;
            setVisible(false);
        }
        else if (e.getSource() == _tables) {
            AliasDef def = (AliasDef) _tables.getSelectedItem();
            loadColumns(def.table);
            enableDisableButtons();
        }
        else if (e.getSource() == _addCol)
            addColumn();
        else if (e.getSource() == _delCol)
            deleteColumn();
        else if (e.getSource() == _updateCol)
            updateColumn();
        else if (e.getSource() == _fileButton) {
            JFileChooser c = new JFileChooser();
            c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            c.setCurrentDirectory(new File(_xmlName.getText()));
            if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)  {
                _xmlName.setText(c.getSelectedFile().getAbsolutePath());
            }
            enableDisableButtons();
        }
        else if (e.getSource() == _up) {
            int i=_colsInListForm.getSelectedIndex();
            LModel lmodel=(LModel)_colsInListForm.getModel();
            ColumnDef cdef=(ColumnDef)lmodel.getElementAt(i);
            lmodel.setElementAt(lmodel.getElementAt(i-1),i);
            lmodel.setElementAt(cdef,i-1);
            _colsInListForm.setSelectedIndex(i-1);
            enableDisableButtons();
        }
        else if (e.getSource() == _down) {
            int i=_colsInListForm.getSelectedIndex();
            LModel lmodel=(LModel)_colsInListForm.getModel();
            ColumnDef cdef=(ColumnDef)lmodel.getElementAt(i);
            lmodel.setElementAt(lmodel.getElementAt(i+1),i);
            lmodel.setElementAt(cdef,i+1);
            _colsInListForm.setSelectedIndex(i+1);
            enableDisableButtons();
        }
        else if (e.getSource() == _clear) {
            _colList.clearSelection();
            _colsInListForm.clearSelection();
            _colBucket.setText("");
            _colCaption.setText("");
            _colCompName.setText("");
            _colFormat.setText("");
            _colHref.setText("");
            _colCompType.setSelectedIndex(0);
            _detailDisplay.setSelected(false);
            _listDisplay.setSelected(false);
            _searchDisplay.setSelected(false);
            _locked.setSelected(false);
            _mandatory.setSelected(false);
            _readonly.setSelected(false);
            _nullable.setSelected(false);
            _primarykey.setSelected(false);
            _updateable.setSelected(false);
            _casesensitive.setSelected(false);
            _exactmatch.setSelected(false);
            _leadingwildcard.setSelected(false);
            _precedence.setSelected(false);
            enableDisableButtons();
        }
    }
private void addColumn()
{
    Object val = _colList.getSelectedValue();
    
    String caption = _colCaption.getText().trim();
    String cname = _colCompName.getText().trim();
    String ctype = _colCompType.getSelectedItem() == null ? "" : _colCompType.getSelectedItem().toString();
    String format = _colFormat.getText().trim();
    String href = _colHref.getText().trim();
    boolean ddisp = _detailDisplay.isSelected();
    boolean ldisp = _listDisplay.isSelected();
    boolean sdisp = _searchDisplay.isSelected();
    boolean lock = _locked.isSelected();
    boolean mand = _mandatory.isSelected();
    boolean ronly = _readonly.isSelected();
    boolean csens = _casesensitive.isSelected();
    boolean ematch = _exactmatch.isSelected();
    boolean lcard = _leadingwildcard.isSelected();
    boolean pre = _precedence.isSelected();
    boolean nul = _nullable.isSelected();
    boolean pkey = _primarykey.isSelected();
    boolean updateble = _updateable.isSelected();
    String sBucket = _colBucket.getText().trim();
    if (sBucket.length() == 0)
        {
        ColumnDefinition col = ((ColumnDef) val).def;
        ColumnDef d = new ColumnDef(col, false);
        d.setCaption(caption);
        d.setCasesensitive(csens);
        d.setCompname(cname);
        d.setComptype(ctype);
        d.setDetaildisplay(ddisp);
        d.setExactmatch(ematch);
        d.setFormat(format);
        d.setHrefexpression(href);
        d.setLeadingwildcard(lcard);
        d.setListdisplay(ldisp);
        d.setLocked(lock);
        d.setMandatory(mand);
        d.setNullable(nul);
        d.setPrecedence(pre);
        d.setPrimarykey(pkey);
        d.setReadonly(ronly);
        d.setSearchdisplay(sdisp);
        d.setUpdateable(updateble);
        d.setHrefexpression(href);
        ((LModel) _colsInListForm.getModel()).addElement(d);
    }
    else
        {
        ColumnDef d = new ColumnDef(null, false);
        d.setBucket(sBucket);
        d.setBucketType((DataType) _bucketType.getSelectedItem());
        d.setCaption(caption);
        d.setCasesensitive(csens);
        d.setCompname(cname);
        d.setComptype(ctype);
        d.setDetaildisplay(ddisp);
        d.setExactmatch(ematch);
        d.setFormat(format);
        d.setHrefexpression(href);
        d.setLeadingwildcard(lcard);
        d.setListdisplay(ldisp);
        d.setLocked(lock);
        d.setMandatory(mand);
        d.setNullable(nul);
        d.setPrecedence(pre);
        d.setPrimarykey(pkey);
        d.setReadonly(ronly);
        d.setSearchdisplay(sdisp);
        d.setUpdateable(updateble);
        d.setHrefexpression(href);
        ((LModel) _colsInListForm.getModel()).addElement(d);
    }
    _colBucket.setText("");
    _bucketType.setSelectedIndex(0);
    _colCaption.setText("");
    _colCompName.setText("");
    _colCompType.setSelectedIndex(0);
    _colFormat.setText("");
    _colHref.setText("");
    _detailDisplay.setSelected(false);
    _listDisplay.setSelected(false);
    _searchDisplay.setSelected(false);
    _locked.setSelected(false);
    _mandatory.setSelected(false);
    _readonly.setSelected(false);
    _casesensitive.setSelected(false);
    _exactmatch.setSelected(false);
    _leadingwildcard.setSelected(false);
    _precedence.setSelected(false);
    _nullable.setSelected(false);
    _primarykey.setSelected(false);
    _updateable.setSelected(false);
    _colsInListForm.setSelectedIndex(_colsInListForm.getModel().getSize() - 1);
    enableDisableButtons();

}
    /**
     * Called when the caret position is updated.
     *
     * @param e the caret event
     */
    public void caretUpdate(CaretEvent e) {
        enableDisableButtons();

    }
    private void deleteColumn() {
        LModel colMod = (LModel) _colsInListForm.getModel();
        int iSel = _colsInListForm.getSelectedIndex();
        colMod.remove(iSel);
        if (colMod.getSize()>0) {
            if (iSel<colMod.getSize())
              _colsInListForm.setSelectedIndex(iSel);
            else
                _colsInListForm.setSelectedIndex(iSel-1);
        }
        enableDisableButtons();
    }
    private void enableDisableButtons() {
        _addCol.setEnabled(_colList.getSelectedIndices().length > 0 || _colBucket.getText().trim().length()>0);
        _export.setEnabled(IDETool.isXMLNameValid(_xmlName.getText()) && (_colsInListForm.getModel().getSize() > 0) );
        _import.setEnabled(IDETool.isXMLNameValid(_xmlName.getText()) && (new File(_xmlName.getText())).exists() );
        _updateCol.setEnabled(_colsInListForm.getSelectedIndices().length>0);
        _delCol.setEnabled(_colsInListForm.getSelectedIndices().length>0);
        _up.setEnabled(_colsInListForm.getSelectedIndices().length>0 && _colsInListForm.getSelectedIndex()>0);
        _down.setEnabled(_colsInListForm.getSelectedIndices().length>0 && _colsInListForm.getSelectedIndex()<_colsInListForm.getModel().getSize()-1);
    }
  
private boolean exportXML()
{
    LModel lmodel = (LModel) _colsInListForm.getModel();
    try
        {
        _ds.reset();
        for (int i = 0; i < lmodel.getSize(); i++)
            {
            ColumnDef cdef = (ColumnDef) lmodel.getElementAt(i);
            _ds.insertRow();
            _ds.setString("component", cdef.getComponentName());
            _ds.setString("componenttype", cdef.getComponentType());
            _ds.setString("columnname", cdef.getColumnName());
            _ds.setString("columntype", cdef.getColumnType());
            _ds.setString("tablename", cdef.getTableName());
            _ds.setString("caption", cdef.getCaption());
            _ds.setString("isbucket", new Boolean(cdef.isBucket()).toString());
            _ds.setString("nullable", new Boolean(cdef.getNullable()).toString());
            _ds.setString("detaildisplay", new Boolean(cdef.getDetailDisplay()).toString());
            _ds.setString("searchdisplay", new Boolean(cdef.getSearchDisplay()).toString());
            _ds.setString("listdisplay", new Boolean(cdef.getListDisplay()).toString());
            _ds.setString("precedence", new Boolean(cdef.getPrecedence()).toString());
            _ds.setString("exactmatch", new Boolean(cdef.getExactMatch()).toString());
            _ds.setString("leadingwildcard", new Boolean(cdef.getLeadingWildcard()).toString());
            _ds.setString("caseSensitive", new Boolean(cdef.getCaseSensitive()).toString());
            _ds.setString("primarykey", new Boolean(cdef.getPrimaryKey()).toString());
            _ds.setString("updateable", new Boolean(cdef.getUpdateable()).toString());
            _ds.setString("href", cdef.getHrefExpression());
        }

        FileOutputStream fos = new FileOutputStream(_xmlName.getText());
        PrintWriter pw = new PrintWriter(fos);
        XMLTransporter.xmlExportDataASMetaData(pw, _ds);
        pw.close();
        fos.close();
        return true;
    }
    catch (Exception ex)
        {
        ex.printStackTrace();
        return false;
    }
}
    /**
     * Return true if the user clicked cancel to close the dialog
     */
    public boolean getCancelClicked() {
        return _clickedCancel;
    }
    /**
     * get list of columns
     */
    public ColumnDef[] getColumns() {
        LModel l = (LModel) _colsInListForm.getModel();
        ColumnDef[] ret = new ColumnDef[l.getSize()];
        for (int i = 0; i < l.getSize();i++)
            ret[i] = (ColumnDef) l.getElementAt(i);
        return ret;
    }
    /**
     * Get the name of the class to save as
     */
    public String getXMLName() {
        return _xmlName.getName().trim();
    }
private void importXML()
{
    LModel lmodel = new LModel();
    try
        {
        _ds.reset(); 
        XMLTransporter.xmlImportMetaDataAsData(_xmlName.getText(), _ds, "", false);
        for (int i = 0; i < _ds.getRowCount(); i++)
            {
            ColumnDefinition def = null;
            ColumnDef cDefinition = null;
            boolean bBucket = new Boolean(_ds.getString(i, "isbucket")).booleanValue();
            String sColumnName = _ds.getString(i, "columnname");
            if (!bBucket)
                {
                String sTableName = _ds.getString(i, "tablename");
                for (int j = 0; j < _tables.getItemCount(); j++)
                    {
                    AliasDef ad = (AliasDef) _tables.getItemAt(j);
                    if (ad.table.equals(sTableName))
                        {
                        _tables.setSelectedIndex(j);
                        break;
                    }
                }
                for (int j = 0; j < _colList.getModel().getSize(); j++)
                    {
                    ColumnDef cdef = (ColumnDef) _colList.getModel().getElementAt(j);
                    if (cdef.getColumnName().equals(sColumnName))
                        {
                        def = cdef.def;
                        break;
                    }
                }
                if (def == null)
                    {
                    IDETool.displayError("Cannot Import. XML file not for this Application.");
                    return;
                }
            }
            cDefinition = new ColumnDef(def, false);
            cDefinition.setCompname(_ds.getString(i, "component"));
            cDefinition.setComptype(_ds.getString(i, "componenttype"));
            cDefinition.setCaption(_ds.getString(i, "caption"));
            cDefinition.setBucket(def == null ? sColumnName : null);
            cDefinition.setBucketType(def == null ? new DataType(_ds.getString(i, "columntype")) : null);
            cDefinition.setNullable(Util.stringToBoolean(_ds.getString(i, "nullable")));
            cDefinition.setDetaildisplay(Util.stringToBoolean(_ds.getString(i, "detaildisplay")));
            cDefinition.setSearchdisplay(Util.stringToBoolean(_ds.getString(i, "searchdisplay")));
            cDefinition.setListdisplay(Util.stringToBoolean(_ds.getString(i, "listdisplay")));
            cDefinition.setPrecedence(Util.stringToBoolean(_ds.getString(i, "precedence")));
            cDefinition.setExactmatch(Util.stringToBoolean(_ds.getString(i, "exactmatch")));
            cDefinition.setLeadingwildcard(Util.stringToBoolean(_ds.getString(i, "leadingwildcard")));
            cDefinition.setCasesensitive(Util.stringToBoolean(_ds.getString(i, "casesensitive")));
            cDefinition.setPrimarykey(Util.stringToBoolean(_ds.getString(i, "primarykey")));
            cDefinition.setUpdateable(Util.stringToBoolean(_ds.getString(i, "updateable")));
            cDefinition.setHrefexpression(_ds.getString(i, "href"));
            lmodel.addElement(cDefinition);
        }
        _colsInListForm.setModel(lmodel);
        setTitle("Edit Form XML");
    }
    catch (Exception ex)
        {
        ex.printStackTrace();
        IDETool.displayError("Import Failed!");
    }
}
    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    public void keyPressed(KeyEvent e) {
    }
    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getSource()==_colBucket || e.getSource()==_xmlName)
          enableDisableButtons();
    }
    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    public void keyTyped(KeyEvent e) {
    }
    private void loadColumns(String tableName) {
        if (tableName != null){
            Vector v = _dd.getColumns(tableName);
            Vector mod = new Vector(v.size());
            for (int i = 0; i < v.size();i++) {
                ColumnDefinition def = (ColumnDefinition) v.elementAt(i);
                mod.add(new ColumnDef(def,true));
            }
            _colList.setModel(new LModel(mod));
        }
    }
    private void loadTables() {
        Vector v = _dd.getTableNames();
        for (int i = 0; i < v.size();i++) {
            _tables.addItem(new AliasDef((String) v.elementAt(i),null));
        }
    }
    /**
     * Reset the cancel clicked status button
     */
    public void resetCancel() {
        _clickedCancel = true;
    }
    private void updateColumn() {
        Object val = _colList.getSelectedValue();
        Object cDef=_colsInListForm.getSelectedValue();
        String caption = _colCaption.getText().trim();
        String cname = _colCompName.getText().trim();
        String ctype = _colCompType.getSelectedItem()==null?"":_colCompType.getSelectedItem().toString();
        String format = _colFormat.getText().trim();
        String href =  _colHref.getText().trim();
        boolean ddisp = _detailDisplay.isSelected();
        boolean ldisp = _listDisplay.isSelected();
        boolean sdisp = _searchDisplay.isSelected();
        boolean lock = _locked.isSelected();
        boolean mand = _mandatory.isSelected();
        boolean ronly = _readonly.isSelected();
        boolean csens = _casesensitive.isSelected();
        boolean ematch = _exactmatch.isSelected();
        boolean lcard = _leadingwildcard.isSelected();
        boolean pre = _precedence.isSelected();
        boolean nul = _nullable.isSelected();
        boolean pkey = _primarykey.isSelected();
        boolean updateble = _updateable.isSelected();
        String sBucket=_colBucket.getText().trim();
        if (sBucket.length()==0) {
                ColumnDefinition col = ((ColumnDef) val).def;
                ColumnDef d = (ColumnDef)cDef;
                d.setColumnDefinition(col);
                d.setBucket("");
                d.setBucketType(null);
                d.setCaption(caption);
                d.setCasesensitive(csens);
                d.setCompname(cname);
                d.setComptype(ctype);
                d.setDetaildisplay(ddisp);
                d.setExactmatch(ematch);
                d.setFormat(format);
                d.setHrefexpression(href);
                d.setLeadingwildcard(lcard);
                d.setListdisplay(ldisp);
                d.setLocked(lock);
                d.setMandatory(mand);
                d.setNullable(nul);
                d.setPrecedence(pre);
                d.setPrimarykey(pkey);
                d.setReadonly(ronly);
                d.setSearchdisplay(sdisp);
                d.setUpdateable(updateble);
                ((LModel)_colsInListForm.getModel()).setElementAt(d,_colsInListForm.getSelectedIndex());
        }
        else {
            ColumnDef d = (ColumnDef)cDef;
            d.setColumnDefinition(null);
            d.setBucket(sBucket);
            d.setBucketType((DataType)_bucketType.getSelectedItem());
            d.setCaption(caption);
            d.setCasesensitive(csens);
            d.setCompname(cname);
            d.setComptype(ctype);
            d.setDetaildisplay(ddisp);
            d.setExactmatch(ematch);
            d.setFormat(format);
            d.setHrefexpression(href);
            d.setLeadingwildcard(lcard);
            d.setListdisplay(ldisp);
            d.setLocked(lock);
            d.setMandatory(mand);
            d.setNullable(nul);
            d.setPrecedence(pre);
            d.setPrimarykey(pkey);
            d.setReadonly(ronly);
            d.setSearchdisplay(sdisp);
            d.setUpdateable(updateble);
            ((LModel)_colsInListForm.getModel()).setElementAt(d,_colsInListForm.getSelectedIndex());
        }
        _colBucket.setText("");
        _bucketType.setSelectedIndex(0);
        _colCaption.setText("");
        _colCompName.setText("");
        _colCompType.setSelectedIndex(0);
        _colFormat.setText("");
        _colHref.setText("");
        _detailDisplay.setSelected(false);
        _listDisplay.setSelected(false);
        _searchDisplay.setSelected(false);
        _locked.setSelected(false);
        _mandatory.setSelected(false);
        _readonly.setSelected(false);
        _casesensitive.setSelected(false);
        _exactmatch.setSelected(false);
        _leadingwildcard.setSelected(false);
        _precedence.setSelected(false);
        _nullable.setSelected(false);
        _primarykey.setSelected(false);
        _updateable.setSelected(false);
        _colList.clearSelection();
        _colsInListForm.clearSelection();
        enableDisableButtons();

    }
/**
 * Called whenever the value of the selection changes.
 * @param e the event that characterizes the change.
 */
public void valueChanged(ListSelectionEvent e)
{
    if (e.getSource() == _colList)
        {
        int i = _colList.getSelectedIndex();
        if (i >= 0)
            {
            ColumnDef cdef = (ColumnDef) _colList.getModel().getElementAt(i);
            if (cdef.def.isPkey())
                {
                _primarykey.setSelected(true);
            }
            else
                {
                _primarykey.setSelected(false);
            }
            if (cdef.def.isNullable())
                {
                _nullable.setSelected(true);
            }
            else
                {
                _nullable.setSelected(false);
            }
        }
        enableDisableButtons();
    }
    if (e.getSource() == _colsInListForm)
        {
        LModel colMod = (LModel) _colsInListForm.getModel();
        int iSelected = _colsInListForm.getSelectedIndex();
        if (iSelected >= 0)
            {
            ColumnDef dDef = (ColumnDef) colMod.getElementAt(iSelected);
            if (dDef.getBucket() == null || dDef.getBucket().trim().length() == 0)
                {
                String sTableName = dDef.def.getTableName();
                for (int i = 0; i < _tables.getItemCount(); i++)
                    {
                    AliasDef adef = (AliasDef) _tables.getItemAt(i);
                    if (adef.table.equals(sTableName))
                        {
                        _tables.setSelectedIndex(i);
                        break;
                    }
                }
                loadColumns(sTableName);
                String sColName = dDef.def.getColumnName();
                for (int i = 0; i < _colList.getModel().getSize(); i++)
                    {
                    ColumnDef cdef = (ColumnDef) _colList.getModel().getElementAt(i);
                    if (cdef.def.getColumnName().equals(sColName))
                        {
                        _colList.setSelectedIndex(i);
                        break;
                    }
                }
                _colBucket.setText("");
            }
            else
                {
                _colBucket.setText(dDef.getBucket());
                _bucketType.setSelectedItem(dDef.getBucketType());
                _colList.clearSelection();
            }
            _colCaption.setText(dDef.getCaption());
            _colCompName.setText(dDef.getComponentName());
            _colCompType.setSelectedItem(dDef.getComponentType());
            _colFormat.setText(dDef.getFormat());
            _colHref.setText(dDef.getHrefExpression());
            _detailDisplay.setSelected(dDef.getDetailDisplay());
            _listDisplay.setSelected(dDef.getListDisplay());
            _searchDisplay.setSelected(dDef.getSearchDisplay());
            _locked.setSelected(dDef.getLocked());
            _mandatory.setSelected(dDef.getMandatory());
            _readonly.setSelected(dDef.getReadonly());
            _casesensitive.setSelected(dDef.getCaseSensitive());
            _exactmatch.setSelected(dDef.getExactMatch());
            _leadingwildcard.setSelected(dDef.getLeadingWildcard());
            _precedence.setSelected(dDef.getPrecedence());
            _nullable.setSelected(dDef.getNullable());
            _primarykey.setSelected(dDef.getPrimaryKey());
            _updateable.setSelected(dDef.getUpdateable());
            _colHref.setText(dDef.getHrefExpression());
        }
    }
}
}
