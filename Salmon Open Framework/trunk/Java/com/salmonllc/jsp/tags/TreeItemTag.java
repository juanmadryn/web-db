package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/tags/TreeItemTag.java $
//$Author: Srufle $
//$Revision: 14 $
//$Modtime: 7/31/02 6:11p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.html.treeControl.*;

/**
 * The TreeItem tag represents the items under a tree tag.
 */
public class TreeItemTag extends TreeTag
{
    // The following are inherited from BaseEmptyTag
    // private String _visible;
    // private String _theme;
    private String _fText;
    private String _fImgsource;
    private String _fImgexpsource;
    private String _fImghref;
    private String _fHref;

    /*
    <tag>
       <name>treeitem</name>
       <tagclass>com.salmonllc.jsp.tags.TreeItemTag</tagclass>
       <bodycontent>empty</bodycontent>
        <attribute>
            <name>text</name>
            <required>true</required>
         </attribute>
        <attribute>
            <name>imgsource</name>
            <required>true</required>
         </attribute>
	  <attribute>
            <name>imgexpsource</name>
            <required>false</required>
         </attribute>
        <attribute>
            <name>imghref</name>
            <required>false</required>
         </attribute>
        <attribute>
            <name>href</name>
            <required>false</required>
         </attribute>
    </tag>
	*/

    /**
     * This method creates the Tree Item and add it to the tree buffer. This component is used with HtmlTreeControl
     */
    public HtmlComponent createComponent()
    {
        HtmlTreeControl tree = null;

        TreeTag trtg = getHelper().getTreeTag();

        if (trtg != null)
        {
            tree = (HtmlTreeControl) trtg.getHelper().getComponent();
            TreeBuffer tb = tree.getTreeBuffer();

            int expImage = 0;
            if (getImgexpsource() != null)
                expImage = tb.addImage(getImgexpsource());

            int nodeImage = 0;
            if (getImgsource() != null)
                nodeImage = tb.addImage(getImgsource());

            if (tb.getHandle() == -1)
                tb.gotoRoot();
            else
                tb.gotoItem(trtg.getHandle());

            int handle = tb.addChild(getText(), getHref(), nodeImage, expImage, getImghref(), null);
            setHandle(handle);
        }
        return tree;
    }
    /**
     * Gets the href for the Tree Node if specified.
     * Creation date: (7/17/01 8:02:20 PM)
     * @return java.lang.String
     */
    public java.lang.String getHref()
    {
        return _fHref;
    }
    /**
     * Gets the expand image source file name for a Tree Node
     * Creation date: (7/17/01 8:02:20 PM)
     * @return java.lang.String
     */
    public java.lang.String getImgexpsource()
    {
        return _fImgexpsource;
    }
    /**
     * Gets the  image source file name for a Tree Node
     * Creation date: (7/17/01 8:02:20 PM)
     * @return java.lang.String
     */
    public java.lang.String getImghref()
    {
        return _fImghref;
    }
    /**
     * Gets the image source file name for a Tree Node
     * Creation date: (7/17/01 8:02:20 PM)
     * @return java.lang.String
     */
    public java.lang.String getImgsource()
    {
        return _fImgsource;
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
     * Gets the text used to display on Tree node
     * Creation date: (7/17/01 8:02:20 PM)
     * @return java.lang.String
     */
    public java.lang.String getText()
    {
        return _fText;
    }
    /**
     * Release all resources used by the tag.
     */
    public void release()
    {
        super.release();

    }
    /**
     * Sets the Href link for Tree node
     * Creation date: (7/17/01 8:02:20 PM)
     * @param newHref java.lang.String
     */
    public void setHref(java.lang.String newHref)
    {
        _fHref = newHref;
    }
    /**
     * Creation date: (7/17/01 8:02:20 PM)
     * @param newImgexpsource java.lang.String
     */
    public void setImgexpsource(java.lang.String newImgexpsource)
    {
        _fImgexpsource = newImgexpsource;
    }
    /**
     * Sets the Href link for Image on Tree node
     * Creation date: (7/17/01 8:02:20 PM)
     * @param newImghref java.lang.String
     */
    public void setImghref(java.lang.String newImghref)
    {
        _fImghref = newImghref;
    }
    /**
     * Sets the Href link for Image on Tree node
     * Creation date: (7/17/01 8:02:20 PM)
     * @param newImgsource java.lang.String
     */
    public void setImgsource(java.lang.String newImgsource)
    {
        _fImgsource = newImgsource;
    }
    /**
     * Sets the text used by component
     * Creation date: (7/17/01 8:02:20 PM)
     * @param newText java.lang.String
     */
    public void setText(java.lang.String newText)
    {
        _fText = newText;
    }
}
