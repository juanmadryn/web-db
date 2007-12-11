package com.salmonllc.jsp;


import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlComponent;


/**
 * This tag creates a div component
 * @author Nicolás Genta
 */
public class JspDiv extends HtmlComponent {

    private boolean _hidden;
    private String _style;

    public JspDiv(String name, HtmlPage p) {
        super(name, p);
        setTheme(null);
        _hidden = false;
        _style = "";
    }

    public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        if (!getVisible())
            return;

        StringBuffer sbOut = new StringBuffer();
        String name = getFullName();
        if (row != -1)
            name += "_" + row;
        if (_hidden) {
            sbOut.append("<DIV ID=\"" + name + "\"" + " STYLE=\"display:none; " + _style + "\">");
        }
        else {
            sbOut.append("<DIV ID=\"" + name + "\"" + " STYLE=\"display:block; " + _style + "\">");
        }
        p.print(sbOut.toString());
    }

    public boolean isHidden() {
        return _hidden;
    }

    public void setHidden(boolean hidden) {
        this._hidden = hidden;
    }

    public String getStyle() {
        return _style;
    }

    public void setStyle(String style) {
        this._style = style;
    }
}
