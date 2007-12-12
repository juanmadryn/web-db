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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/TreeTag.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 8/27/03 10:32a $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.html.treeControl.*;
import com.salmonllc.util.MessageLog;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.jsp.JspWriter;

/**
 * This is a tag used to create a Tree control.
 */

public class TreeTag extends BaseBodyTag
{
    // The following are inherited from BaseBodyTag
    // private String _visible;
    // private String _theme;

    private String _fExpandimage;
    private String _fContractimage;
    private String _fTreemode;
    private String _fNullimage;
    private String _fSelectmode;
    private String _fShowroot;
    private String _fTarget;
    private String _fTheme;
    private TreeBuffer _treeBuffer;

    protected int _fHandle = -1;


    /*
    	    <tag>
       <name>tree</name>
       <tagclass>com.salmonllc.jsp.tags.TreeTag</tagclass>
       <bodycontent>JSP</bodycontent>
        <attribute>
            <name>expandimage</name>
            <required>true</required>
         </attribute>
        <attribute>
            <name>contractimage</name>
            <required>true</required>
         </attribute>
        <attribute>
            <name>treemode</name>
            <required>false</required>
         </attribute>
        <attribute>
            <name>nullimage</name>
            <required>true</required>
         </attribute>
        <attribute>
            <name>selectmode</name>
            <required>false</required>
         </attribute>
        <attribute>
            <name>showroot</name>
            <required>false</required>
         </attribute>
    </tag>


    */
    /**
     * This method creates the HtmlTreeControl component used by the tag.
     */

    public HtmlComponent createComponent()
    {
        BaseTagHelper helper = getHelper();

        // craete a tree controll and get the tree buffer
        HtmlTreeControl tree = new HtmlTreeControl(getName(), getTheme(), helper.getController());
        _treeBuffer = tree.getTreeBuffer();

        tree.setSystemImages(getExpandimage(), getContractimage(), getNullimage());
        tree.setTargetFrame(getTarget());

        // treemode valid values are
        // * LINK (clicking on a link in the tree will cause the browser to follow a link)
        // * SUBMIT (clicking a link will cause the page to be submitted before following a link).

        int treeMode = TreeBuffer.MODE_SUBMIT;
        String treeModeStr = getTreemode();
        if (treeModeStr != null)
        {
            if (treeModeStr.equalsIgnoreCase("SUBMIT"))
            {
                treeMode = TreeBuffer.MODE_SUBMIT;
            }
            else if (treeModeStr.equalsIgnoreCase("LINK"))
            {
                treeMode = TreeBuffer.MODE_LINK;
            }

        }
        _treeBuffer.setMode(treeMode);

        // showroot (optional) true or false value indicating whether or not to display the root of the tree.
        _treeBuffer.setShowRoot(BaseTagHelper.stringToBoolean(getShowroot(), true));

        // selectmode valid values are
        // * NONE - (No items on the tree can be selected)
        // * ONE (One item on the tree can be selected)
        // * MANY (Many items on the tree can be selected)

        int selectMode = TreeBuffer.SELECT_NONE;
        String selectModeStr = getSelectmode();
        if (selectModeStr != null)
        {
            if (selectModeStr.equalsIgnoreCase("NONE"))
            {
                selectMode = TreeBuffer.SELECT_NONE;
            }
            else if (selectModeStr.equalsIgnoreCase("ONE"))
            {
                selectMode = TreeBuffer.SELECT_ONE;
            }
            else if (selectModeStr.equalsIgnoreCase("MANY"))
            {
                selectMode = TreeBuffer.SELECT_MANY;
            }
        }
        _treeBuffer.setSelectMode(selectMode);

        return tree;
    }

    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException
    {
        try
        {

            HtmlTreeControl tree = (HtmlTreeControl) getHelper().getController().getComponent(getName());
            if (tree == null)
                return;

            tree.generateHTML(new PrintWriter(p), -1);


        }
        catch (IOException ioe)
        {
            MessageLog.writeErrorMessage("generateComponentHTML", ioe, this);
            throw ioe;

        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("generateComponentHTML", e, this);

        }
    }

    /**
     * This method gives the Tree Buffer, the data, used by HtmlTreeControl
     * Creation date: (9/5/01 3:55:58 PM)
     * @return com.salmonllc.html.treeControl.TreeBuffer
     */
    public com.salmonllc.html.treeControl.TreeBuffer getBuffer()
    {
        return _treeBuffer;
    }

    /**
     * This method gives the node contract image used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getContractimage()
    {
        return _fContractimage;
    }

    /**
     * This method gives the node expand image used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getExpandimage()
    {
        return _fExpandimage;
    }

    /**
     * This method returns the handle to the tree object
     * Creation date: (8/21/01 10:28:31 AM)
     * @return int
     */
    public int getHandle()
    {
        return _fHandle;
    }

    /**
     * This method gives the node null image used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getNullimage()
    {
        return _fNullimage;
    }

    /**
     * This method gives the selection mode (Single Node Selection or Multiple Node Selection) used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getSelectmode()
    {
        return _fSelectmode;
    }

    /**
     * This method gives the 'show root' flag used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getShowroot()
    {
        return _fShowroot;
    }

    /**
     * Creation date: (6/29/01 8:26:41 AM)
     * @return boolean
     */
    public int getTagConvertType()
    {
        return CONV_WRAP_ALL_NESTED;
    }

    /**
     * Gets the source for target of tree display
     * Creation date: (7/17/01 8:02:37 PM)
     * @return java.lang.String
     */
    public java.lang.String getTarget()
    {
        return _fTarget;
    }

    /**
     * Gets the theme used to display by Tree
     * Creation date: (9/5/01 3:55:58 PM)
     * @return java.lang.String
     */
    public java.lang.String getTheme()
    {
        return _fTheme;
    }

    /**
     * This method gets the mode of the Tree used by HtmlTreeControl
     * Creation date: (7/17/01 8:03:49 PM)
     * @return java.lang.String
     */
    public java.lang.String getTreemode()
    {
        return _fTreemode;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release()
    {
        super.release();

        _fExpandimage = null;
        _fContractimage = null;
        _fTreemode = null;
        _fNullimage = null;
        _fSelectmode = null;
        _fShowroot = null;
        _fTarget = null;

    }

    /**
     * This method gives the Tree Buffer, the data, used by HtmlTreeControl
     * Creation date: (9/5/01 3:55:58 PM)
     * @param newBuffer com.salmonllc.html.treeControl.TreeBuffer
     */
    public void setBuffer(com.salmonllc.html.treeControl.TreeBuffer newBuffer)
    {
        _treeBuffer = newBuffer;
    }

    /**
     * Sets the contract image
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newContractimage java.lang.String
     */
    public void setContractimage(java.lang.String newContractimage)
    {
        _fContractimage = newContractimage;
    }

    /**
     * This method sets the node expandimage used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newExpandimage java.lang.String
     */
    public void setExpandimage(java.lang.String newExpandimage)
    {
        _fExpandimage = newExpandimage;
    }

    /**
     * Sets the handle for a tree, This represents the current pointer in the tree
     * Creation date: (8/21/01 10:28:31 AM)
     * @param newHandle int
     */
    public void setHandle(int newHandle)
    {
        _fHandle = newHandle;
    }

    /**
     * This method sets the node null image used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newNullimage java.lang.String
     */
    public void setNullimage(java.lang.String newNullimage)
    {
        _fNullimage = newNullimage;
    }

    /**
     * This method sets the selection mode (Single Node Selection or Multiple Node Selection) used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newSelectmode java.lang.String
     */
    public void setSelectmode(java.lang.String newSelectmode)
    {
        _fSelectmode = newSelectmode;
    }

    /**
     * This method gives the 'show root' flag used by HtmlTreeControl
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newShowroot java.lang.String
     */
    public void setShowroot(java.lang.String newShowroot)
    {
        _fShowroot = newShowroot;
    }

    /**
     * Sets the source for the target display for the tree
     * Creation date: (7/17/01 8:02:37 PM)
     * @param newTarget java.lang.String
     */
    public void setTarget(java.lang.String newTarget)
    {
        _fTarget = newTarget;
    }

    /**
     * Sets the theme used by Tree
     * Creation date: (9/5/01 3:55:58 PM)
     * @param newTheme java.lang.String
     */
    public void setTheme(java.lang.String newTheme)
    {
        _fTheme = newTheme;
    }

    /**
     * This method gets the mode of the Tree used by HtmlTreeControl
     * Creation date: (7/17/01 8:03:49 PM)
     * @param newTreemode java.lang.String
     */
    public void setTreemode(java.lang.String newTreemode)
    {
        _fTreemode = newTreemode;
    }
}
