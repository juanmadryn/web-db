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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlMultiListBox.java $
//$Author: Dan $
//$Revision: 14 $
//$Modtime: 6/11/03 4:39p $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.properties.Props;
import com.salmonllc.util.FourObjectContainer;
import java.util.Hashtable;
import java.util.Vector;


/**
 * This class is used to allow for the selection of items from multiple lists. The items in the second list and subsequent lists are dependant on the selected item in the first or prior lists. The class can be used to allow the user to select one item from a tree or items
 */
public class HtmlMultiListBox extends HtmlFormComponent {
    private String _fontTagStart;
    private String _fontTagEnd;
    private int _size = 5;


    private Vector _options = new Vector();

    /**
     * Constructs a new HTMLMultiListBox component.
     * @param name The name of the component
     * @param p The page the component will be placed in.
     */
    public HtmlMultiListBox(String name, com.salmonllc.html.HtmlPage p) {
        this(name, null, p);
    }

    /**
     * Constructs a new HTMLMultiListBox component.
     * @param name The name of the component
     * @param theme The theme to use for loading properties.
     * @param p The page the component will be placed in.
     */
    public HtmlMultiListBox(String name, String theme, com.salmonllc.html.HtmlPage p) {
        super(name, theme, p);
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!_visible)
            return;
        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;
        String value = getValue(rowNo, true);
        if (_dsBuff != null) {
            if (value == null)
                setInternalValue(null);
            else
                setInternalValue(value.toString());
        }

        p.println("<TABLE BORDER=\"0\"><TR>");
        int parentHandle = -1;
        for (int i = 0; i < _numberOfBoxes; i++) {
            p.println("<TD>");
            parentHandle = generateListBoxHtml(p, rowNo, i, _numberOfBoxes - 1, parentHandle);
            p.println("</TD>");
        }
        p.println("</TR></TABLE>");

        p.println("<SCRIPT Language=\"JavaScript\">");
        if (_treeArray == null && _options.size() > 0) {
            _treeArray = new StringBuffer();
            _treeArray.append("var " + getFullName() + "items=new Array(" + _root.getCount() * 3 + ");\n");
            String prefix = getFullName() + "items";
            appendToTree(_root, prefix);
        }
        p.println(_treeArray.toString());

        p.println("function clearBox(oBox){");
        p.println("  var i;");
        p.println("  for (i = oBox.options.length - 1; i >= 0; i--) oBox.options[i] = null;");
        p.println("       oBox.selectedIndex = -1;");
        p.println("}");

        p.println("function populateBox(oBox, aArray){");
        p.println("   clearBox(oBox);");
        p.println("	  if (aArray != null) {");
        p.println("  	 for (var i = 0; i < aArray.length; i = i  + 3)");
        p.println("   	   oBox.options[oBox.options.length] = new Option(aArray[i + 2], aArray[i + 1]);");
        p.println("	  }");
        p.println("}");

        String tname = getFullName();
        if (rowNo > -1)
            tname += "_" + rowNo;
        tname += "_";
        for (int i = 0; i < _numberOfBoxes; i++)
            writeShowHideJavaScript(p, tname, i);

        p.println("</SCRIPT>");

    }

    /**
     * This method gets the end font tag for the component.
     */
    public String getFontEndTag() {
        return _fontTagEnd;
    }

    /**
     * This method gets the start font tag for the component.
     */
    public String getFontStartTag() {
        return _fontTagStart;
    }

    /**
     * This method returns the number of options in the component.
     */
    public int getOptionCount() {
        return _options.size();
    }

    /**
     * Use this method get the value of the key at index.
     */
    public String getOptionKey(int handle) {
        if (handle < 0 || handle >= _options.size())
            return null;

        FourObjectContainer f = (FourObjectContainer) _options.elementAt(handle);
        return (String) f.getObject1();
    }

    /**
     * Use this method to find out if an option is selected.
     */
    public boolean getOptionSelected(int handle) {
        if (handle < 0 || handle >= _options.size())
            return false;

        FourObjectContainer f = (FourObjectContainer) _options.elementAt(handle);
        return ((Boolean) f.getObject3()).booleanValue();
    }

    /**
     * Use this method get the value of the option at index.
     */
    public String getOptionValue(int handle) {
        if (handle < 0 || handle >= _options.size())
            return null;

        FourObjectContainer f = (FourObjectContainer) _options.elementAt(handle);
        return (String) f.getObject2();
    }

    /**
     * This method gets the display size for the component in characters.
     */
    public int getSize() {
        return _size;
    }

    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
        Object oldValue = _value;
        String name = getFullName();
        if (rowNo > -1) {
            name += "_" + rowNo;
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(rowNo, _dsColNo);
        } else {
            if (_dsBuff != null)
                oldValue = _dsBuff.getAny(_dsColNo);
        }


        int optionsSize = _options.size();
        FourObjectContainer f = null;
        for (int i = 0; i < optionsSize; i++) {
            f = (FourObjectContainer) _options.elementAt(i);
            f.setObject3(new Boolean(false));
        }

        _value = null;
        for (int i = 0; i < _numberOfBoxes; i++) {
            String val[] = (String[]) parms.get(name + "_" + i);
            if (val != null) {
                int index = -1;
                for (int j = 0; j < val.length; j++) {
                    String item = val[j].substring(val[j].lastIndexOf("_") + 1);
                    if (!item.equals("X")) {
                        index = Integer.parseInt(item);
                        f = (FourObjectContainer) _options.elementAt(index);
                        f.setObject3(new Boolean(true));
                        _value = (String) f.getObject1();
                        _lastVisibleBox = i + 1;
                    }
                }
            }
        }

        if (!valuesEqual(oldValue, _value)) {
            String s = null;
            if (oldValue != null)
                s = oldValue.toString();
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), s, _value, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }
        return false;
    }

    /**
     * This method removes all options from the component.
     */
    public void resetOptions() {
        _options.removeAllElements();
        _treeArray = null;
        _root = new TreeNode();
        _lastVisibleBox = 0;
    }

    /**
     * This method sets the end font tag for the component.
     */
    public void setFontEndTag(String value) {
        _fontTagEnd = value;
    }

    /**
     * This method sets the start font tag for the component.
     */
    public void setFontStartTag(String value) {
        _fontTagStart = value;
    }

    /**
     * This method sets the first selected option in the list box to the one with the key.
     */
    private void setInternalValue(String value) {
        Boolean trueVal = new Boolean(true);
        Boolean falseVal = new Boolean(false);
        //
        int optionsSize = _options.size();
        FourObjectContainer f = null;
        String key = null;
        Boolean selected = null;
        //
        for (int i = 0; i < optionsSize; i++) {
            f = (FourObjectContainer) _options.elementAt(i);
            key = (String) f.getObject1();
            selected = falseVal;
            if (key != null)
                if (key.equals(value)) {
                    selected = trueVal;
                }
            f.setObject3(selected);
        }
    }


    /**
     * Use this method set whether an option is selected.
     */
    public void setOptionSelected(int handle, boolean selected) {
        if (handle < 0 || handle >= _options.size())
            return;

        FourObjectContainer f = (FourObjectContainer) _options.elementAt(handle);
        f.setObject3(new Boolean(selected));
    }

    /**
     * This method sets the display size for the component in characters.
     */
    public void setSize(int size) {
        _size = size;
    }

    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String theme) {

        super.setTheme(theme);
        Props props = getPage().getPageProperties();
        _fontTagStart = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_START);
        _fontTagEnd = props.getThemeProperty(theme, Props.FONT_DDLB + Props.TAG_END);
    }

    private int _lastVisibleBox = 0;
    private int _numberOfBoxes = 3;
    private TreeNode _root = new TreeNode();
    StringBuffer _treeArray = null;

    private class TreeNode {
        Vector _children = new Vector();

        void addNode(int handle) {
            _children.addElement(new Integer(handle));
        }

        int getHandle(int index) {
            if (index >= 0 && index < _children.size())
                return ((Integer) _children.elementAt(index)).intValue();
            else
                return -1;
        }

        void removeNode(int index) {
            if (index >= 0 && index < _children.size())
                _children.removeElementAt(index);
        }

        int getCount() {
            return _children.size();
        }
    }

    /**
     * Use this method to add new choices to the list.
     * @param parentHandle The items parent handle
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     * @return the handle of the item to return;
     */
    public int addOption(int parentHandle, String key, String disp) {
        return addOption(parentHandle, key, disp, false);
    }

    /**
     * Use this method to add new choices to the list.
     * @param parentHandle The items parent handle
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     * @param selected Whether or not the item is selected.
     * @return the handle of the item to return;
     */
    public int addOption(int parentHandle, String key, String disp, boolean selected) {
        TreeNode thisNode = new TreeNode();
        FourObjectContainer f = new FourObjectContainer(key, disp, new Boolean(selected), thisNode);
        _options.addElement(f);
        int handle = _options.size() - 1;
        if (parentHandle != -1) {
            f = (FourObjectContainer) _options.elementAt(parentHandle);
            TreeNode n = (TreeNode) f.getObject4();
            n.addNode(handle);
        } else {
            _root.addNode(handle);
        }
        _treeArray = null;
        return handle;

    }

    /**
     * Use this method to add a new option to the first list box.
     * @param key The internal name of the item (must be unique)
     * @param disp The value to be displayed on the list.
     * @return the handle of the item (this can be used later to add children to this node).
     */
    public int addOption(String key, String disp) {
        return addOption(-1, key, disp, false);
    }

    private void appendToTree(TreeNode n, String prefix) {

        int index = 0;
        for (int i = 0; i < n.getCount(); i++) {
            StringBuffer newPrefix = new StringBuffer(prefix);

            newPrefix.append("[");
            newPrefix.append(index++);
            newPrefix.append("]");

            int handle = n.getHandle(i);

            if (handle > -1) {
                _treeArray.append(newPrefix);
                _treeArray.append("= new Array();\n");

                _treeArray.append(prefix);
                _treeArray.append("[");
                _treeArray.append(index++);
                _treeArray.append("]");
                _treeArray.append("='");
                _treeArray.append(getFullName());
                _treeArray.append("_");
                _treeArray.append(handle);
                _treeArray.append("';\n");

                _treeArray.append(prefix);
                _treeArray.append("[");
                _treeArray.append(index++);
                _treeArray.append("]");
                _treeArray.append("='");
                _treeArray.append((String) ((FourObjectContainer) _options.elementAt(handle)).getObject2());
                _treeArray.append("';\n");

                TreeNode t = (TreeNode) ((FourObjectContainer) _options.elementAt(handle)).getObject4();
                appendToTree(t, newPrefix.toString());
            }
        }
    }

    /**
     * This method returns the index of the option with the specified key.
     * @return The index of the key or -1 if not found.
     */
    public int findOptionHandleOf(String key) {
        int optionsSize = _options.size();
        FourObjectContainer f = null;
        for (int i = 0; i < optionsSize; i++) {
            f = (FourObjectContainer) _options.elementAt(i);
            if (key.equals(f.getObject1()))
                return i;
        }
        return -1;
    }

    private int generateListBoxHtml(java.io.PrintWriter p, int rowNo, int index, int max, int parentHandle) {
        int ret = -1;
        String name = getFullName();
        if (rowNo > -1)
            name += "_" + rowNo;
        name += "_" + index;
  
        boolean netscape = getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE;

        String tag = "";

        if (index < max) {
            p.println("<SCRIPT LANGUAGE=\"JavaScript\">");
            p.println("function " + name + "showHide(){");

            String tname = getFullName();
            if (rowNo > -1)
                tname += "_" + rowNo;


            if (index > 0) {
                p.println("if (" + getFormString() + tname + "_" + (index - 1) + ".selectedIndex < 0)");
                p.println("   return;");
            }

            p.println("show" + tname + "_" + (index + 1) + "();");
            for (int i = index + 2; i <= max; i++) {
                p.println("hide" + tname + "_" + i + "();");
            }
            p.println("}");
            p.println("</SCRIPT>");
        }

        StringBuffer b = new StringBuffer();
        boolean optionPrinted = false;
        if (index == 0 || parentHandle > -1) {
            int number = 0;
            TreeNode node = _root;
            if (parentHandle > -1) {
                FourObjectContainer f = (FourObjectContainer) _options.elementAt(parentHandle);
                node = (TreeNode) f.getObject4();
            }
            int handle = node.getHandle(number++);

            while (handle > -1) {
                optionPrinted = true;
                FourObjectContainer f = (FourObjectContainer) _options.elementAt(handle);
                b.append("<OPTION VALUE=\"");
                b.append(getFullName());
                b.append("_");
                b.append(handle);
                b.append("\"");
                if (((Boolean) f.getObject3()).booleanValue()) {
                    b.append(" SELECTED");
                    ret = handle;
                }

                if (f.getObject2() == null) {
                    b.append(">");
                    for (int i = 0; i < _minWidth; i++)
                        b.append("&nbsp;");
                    b.append("<OPTION>");
                } else {
                    b.append(">");
                    b.append((String) f.getObject2());
                    if (number == 1) {
                        int len = ((String) f.getObject2()).length();
                        for (int i = len; i < _minWidth; i++)
                            b.append("&nbsp;");
                    }
                    b.append("</OPTION>");
                }
                handle = node.getHandle(number++);
            }
            tag = b.toString();
        }
        if (!optionPrinted) {
            StringBuffer min = new StringBuffer(_minWidth * 6);
            min.append("<OPTION VALUE=\"" + getFullName() + "_X\">");
            for (int i = 0; i < _minWidth; i++)
                min.append("&nbsp;");
            min.append("</OPTION>");
            tag += min.toString();
        }

        String tagStart = "<SELECT ";
        if (!netscape)
            tagStart += " STYLE=\"visibility:" + (index <= _lastVisibleBox && optionPrinted ? "visible" : "hidden") + "\"";
        tagStart += " NAME=\"" + name + "\"" + " SIZE=\"" + _size + "\"";

        if (index < max) {
            tagStart += " ONCHANGE=\"" + name + "showHide();\"";
            tagStart += " ONCLICK=\"" + name + "showHide();\"";
        }
        tagStart += ">";

        tag = tagStart + tag + "</SELECT>";
        if (_fontTagStart != null)
            tag = _fontTagStart + tag + _fontTagEnd;
        p.println(tag);
        return ret;

    }

    private void writeShowHideJavaScript(java.io.PrintWriter p, String name, int index) {
        boolean netscape = getPage().getBrowserType() == HtmlPage.BROWSER_NETSCAPE;

        p.println("function hide" + name + index + "() {");
        p.println(netscape ? "" : (getFormString() + "elements['" + name + index + "'].style.visibility=\"hidden\";"));
        p.println("clearBox(" + getFormString() + "elements['" + name + index + "']);");
        p.println("}");
        p.println("");

        p.println("function show" + name + index + "() {");
        p.print("var ar = " + getFullName() + "items");
        for (int i = 0; i < index; i++) {
            p.print("[(" + getFormString() + "elements['");
            p.print(name + i);
            p.print("'].selectedIndex * 3)]");
        }
        p.println(";");

        p.println("populateBox(" + getFormString() + "elements['" + name + index + "'], ar);");
        if (!netscape) {
            p.println("if (" + getFormString() + "elements['" + name + index + "'].length > 0)");
            p.println(getFormString() + "elements['" + name + index + "'].style.visibility=\"visible\";");
            p.println("else");
            p.println(getFormString() + "elements['" + name + index + "'].style.visibility=\"hidden\";");
        }

        p.println("}");

    }

    private int _minWidth = 20;

    /**
     * Sets the minimum width (in non breaking spaces) for each list box
     */
    public void setMinimumWidth(int chars) {
        _minWidth = chars;

    }

    /**
     * This method displays the number of boxes that will be displayed in the list box
     */
    public void setNumberOfBoxes(int boxes) {
        _numberOfBoxes = boxes;
    }
}
