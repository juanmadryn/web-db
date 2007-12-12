package com.salmonllc.jsp;


import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlComponent;


/**
 * This tag creates an expandall component
 * @author Nicolás Genta
 */
public class JspExpandAll extends HtmlComponent {
    
    private String _expand;
    private String _imgexp;
    private String _imgcont;
    private String _altexp;
    private String _altcont;

    public JspExpandAll(String name, String expand, String imgexp, String imgcont, HtmlPage p) {
        super(name, p);
        setTheme(null);
        _expand = expand;
        _imgexp = imgexp;
        _imgcont = imgcont;
        _altexp = null;
        _altcont = null;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

        String nameShow = getFullName();
        String nameHide = getFullName();
        if (rowNo != -1) {
            nameShow += "_" + rowNo;
            nameHide += "_" + rowNo;
        }
        String srcExp = translateSiteMapURL(_imgexp);
        String srcCont = translateSiteMapURL(_imgcont);
        String altExp = ((_altexp == null)? "" : _altexp);
        String altCont = ((_altcont == null)? "" : _altcont);
        String onclickShow = "";
        String onclickHide = "";

        JspController controller = (JspController) getPage();
        HtmlComponent comp = controller.getComponent(_expand);
        if (comp != null && comp instanceof JspExpand) {
            JspExpand expand = (JspExpand) comp;
            String div = expand.getDiv();
            HtmlComponent divComp = controller.getComponent(div);
            if (divComp != null && divComp instanceof JspDiv) {
                onclickShow = "showAll('" + divComp.getFullName() + "');";
                onclickHide = "hideAll('" + divComp.getFullName() + "');";
            }
        }

        StringBuffer sbOut = new StringBuffer();
        sbOut.append("<IMG NAME=\"" + nameShow + "\"" + " SRC=\"" + srcExp + "\"" + " ALT=\"" + altExp + "\"" + " ONCLICK=\"" + onclickShow + "\"/>");
        sbOut.append("&nbsp;");
        sbOut.append("<IMG NAME=\"" + nameHide + "\"" + " SRC=\"" + srcCont + "\"" + " ALT=\"" + altCont + "\"" + " ONCLICK=\"" + onclickHide + "\"/>");
        p.print(sbOut.toString());
        addScript();
    }

    public String getExpand() {
        return _expand;
    }

    public void setExpand(String expand) {
        this._expand = expand;
    }

    public String getImgexp() {
        return _imgexp;
    }

    public void setImgexp(String imgexp) {
        this._imgexp = imgexp;
    }

    public String getImgcont() {
        return _imgcont;
    }

    public void setImgcont(String imgcont) {
        this._imgcont = imgcont;
    }

    public String getAltexp() {
        return _altexp;
    }

    public void setAltexp(String altexp) {
        this._altexp = altexp;
    }

    public String getAltcont() {
        return _altcont;
    }

    public void setAltcont(String altcont) {
        this._altcont = altcont;
    }

    private void addScript() {
        if (!getPage().isScriptAdded("ExpandAllScript")) {
            StringBuffer jsExpandAll= new StringBuffer();

            jsExpandAll.append("function showAll(divId) {\n" +
                    "\tvar divs = document.getElementsByTagName(\"div\");\n" +
                    "\tfor (var i=0; i<divs.length; i++) {\n" +
                    "\t\tvar divFullName = divs[i].id;\n" +
                    "\t\tvar divName = divFullName.substring(0, divFullName.lastIndexOf(\"_\"));\n" +
                    "\t\tif (divName == divId) {\n" +
                    "\t\t\tvar row = divFullName.substring(divFullName.lastIndexOf(\"_\")+1);\n" +
                    "\t\t\tvar div = document.getElementById(divId + \"_\" + row);\n" +
                    "\t\t\tif (div.style.display == \"none\") {\n" +
                    "\t\t\t\texpand(divId, parseInt(row));\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}\n\n");
            jsExpandAll.append("function hideAll(divId) {\n" +
                    "\tvar divs = document.getElementsByTagName(\"div\");\n" +
                    "\tfor (var i=0; i<divs.length; i++) {\n" +
                    "\t\tvar divFullName = divs[i].id;\n" +
                    "\t\tvar divName = divFullName.substring(0, divFullName.lastIndexOf(\"_\"));\n" +
                    "\t\tif (divName == divId) {\n" +
                    "\t\t\tvar row = divFullName.substring(divFullName.lastIndexOf(\"_\")+1);\n" +
                    "\t\t\tvar div = document.getElementById(divId + \"_\" + row);\n" +
                    "\t\t\tif (div.style.display == \"block\") {\n" +
                    "\t\t\t\texpand(divId, parseInt(row));\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}\n\n");

            getPage().addScript("ExpandAllScript", jsExpandAll.toString());
        }
    }
}
