package com.salmonllc.jsp.tags;


import com.salmonllc.html.HtmlComponent;

import javax.servlet.jsp.JspWriter;

/**
 * This tag creates a div component
 * @author Nicolás Genta
 */
public class EndDivTag extends BaseEmptyTag {

    public HtmlComponent createComponent() {
        return null;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_DEFAULT;
    }
    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release() {
        super.release();
    }

    protected void generateComponentHTML(JspWriter w) throws Exception {
       w.print("</DIV>");
    }

}
