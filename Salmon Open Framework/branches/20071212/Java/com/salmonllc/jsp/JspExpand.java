package com.salmonllc.jsp;


import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlComponent;


/**
 * This tag creates an expand component    
 * @author Nicolás Genta 
 */
public class JspExpand extends HtmlComponent {

    private String _div;
    private String _imgexp;
    private String _imgcont;
    private String _altexp;
    private String _altcont;
    private boolean _keepstate;

    public JspExpand(String name, String div, String imgexp, String imgcont, HtmlPage p) {
        super(name, p);
        setTheme(null);
        _div = div;
        _imgexp = imgexp;
        _imgcont = imgcont;
        _altexp = null;
        _altcont = null;
        _keepstate = true;
    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (!getVisible())
            return;

        String name = getFullName();
        if (rowNo != -1)
            name += "_" + rowNo;
        String altExp = ((_altexp == null)? "" : _altexp);
        String altCont = ((_altcont == null)? "" : _altcont);
        String src = translateSiteMapURL(_imgcont);
        String alt = altCont;
        String onClick = "";
        String onLoad = "";

        JspController controller = (JspController) getPage();
        HtmlComponent comp = controller.getComponent(_div);
        if (comp != null && comp instanceof JspDiv) {
            JspDiv div = (JspDiv) comp;
            onLoad = "init('" + div.getFullName()   + "','" + getFullName() + "','" + translateSiteMapURL(_imgexp) + "','" + translateSiteMapURL(_imgcont) + "','" + altExp + "','" + altCont + "'," + _keepstate + ");";
            if (div.isHidden()) {
                src = translateSiteMapURL(_imgexp);
                alt = altExp;
            }
            onClick = "expand('" + div.getFullName() + "'," + rowNo + ");";
        }

        StringBuffer sbOut = new StringBuffer("<IMG NAME=\"" + name + "\"" + " ID=\"" + name + "\"" + " SRC=\"" + src + "\"" + " ALT=\"" + alt + "\"" + " ONCLICK=\"" + onClick + "\"/>");
        p.print(sbOut.toString());
        addScript(_keepstate);

        String currentOnLoad = controller.getOnLoad();
        if (currentOnLoad != null) {
            if (!JspExpand.isSubString(onLoad, currentOnLoad)) {
                controller.setOnLoad(currentOnLoad + "\n" + onLoad);
            }
        }
        else {
            controller.setOnLoad(onLoad);
        }
    }

    public String getDiv() {
        return _div;
    }

    public void setDiv(String div) {
        this._div = div;
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

    public boolean isKeepstate() {
        return _keepstate;
    }

    public void setKeepstate(boolean keepstate) {
        this._keepstate = keepstate;
    }

    private void addScript(boolean keepState) {
        if (!getPage().isScriptAdded("ExpandScript")) {
            StringBuffer jsExpand= new StringBuffer();

            jsExpand.append("var tagsData = new Array();\n" +
                    "var tagsState = new Array();\n" +
                    "\n" +
                    "function TagData(divId, imgId, srcExp, srcCont, altExp, altCont, keepState) {\n" +
                    "\tthis.divId = divId;\n" +
                    "\tthis.imgId = imgId;\n" +
                    "\tthis.srcExp = srcExp;\n" +
                    "\tthis.srcCont = srcCont;\n" +
                    "\tthis.altExp = altExp;\n" +
                    "\tthis.altCont = altCont;\n" +
                    "\tthis.keepState = keepState;\n" +
                    "}\n" +
                    "\n" +
                    "function TagState(divId, expanded, contracted) {\n" +
                    "\tthis.divId = divId;\n" +
                    "\tthis.expanded = expanded;\n" +
                    "\tthis.contracted = contracted;\n" +
                    "}\n\n");
            jsExpand.append("function find(array, divId) {\n" +
                    "\tvar i = 0;\n" +
                    "\tvar found = false;\n" +
                    "\twhile (i<array.length && !found) {\n" +
                    "\t\tif (array[i].divId == divId) {\n" +
                    "\t\t\tfound = true;\n" +
                    "\t\t} else {\n" +
                    "\t\t\ti++;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tif (found) {\n" +
                    "\t\treturn array[i];\n" +
                    "\t} else {\n" +
                    "\t\treturn null;\n" +
                    "\t}\n" +
                    "}\n\n");
            jsExpand.append("function init(divId, imgId, srcExp, srcCont, altExp, altCont, keepState) {\n" +
                    "\tvar tagData = find(tagsData, divId);\n" +
                    "\tif (tagData == null) {\n" +
                    "\t\ttagData = new TagData(divId, imgId, srcExp, srcCont, altExp, altCont, keepState);\n" +
                    "\t\ttagsData.push(tagData);\n" +
                    "\t}\n" +
                    "\tif (keepState) {\n" +
                    "\t\tload(divId);\n" +
                    "\t}\n" +
                    "}\n\n");
            jsExpand.append("function expand(divId, row) {\n" +
                    "\tvar tagData = find(tagsData, divId);\n" +
                    "\tvar imgId = tagData.imgId;\n" +
                    "\tvar srcExp = tagData.srcExp;\n" +
                    "\tvar srcCont = tagData.srcCont;\n" +
                    "\tvar altExp = tagData.altExp;\n" +
                    "\tvar altCont = tagData.altCont;\n" +
                    "\tvar keepState = tagData.keepState;\n" +
                    "\tif (row == -1) {\n" +
                    "\t\tvar img = document.getElementById(imgId);\n" +
                    "\t\tvar div = document.getElementById(divId);\n" +
                    "\t}\n" +
                    "\telse {\n" +
                    "\t\tvar img = document.getElementById(imgId + \"_\" + row);\n" +
                    "\t\tvar div = document.getElementById(divId + \"_\" + row);\n" +
                    "\t}\n" +
                    "\tif (keepState) {\n" +
                    "\t\tchangeState(divId, row, div);\n" +
                    "\t}\n" +
                    "\tif (div.style.display == \"none\") {\n" +
                    "\t\tshow(img, div, srcCont, altCont);\n" +
                    "\t} else {\n" +
                    "\t\thide(img, div, srcExp, altExp);\n" +
                    "\t}\n" +
                    "}\n\n");
            jsExpand.append("function show(img, div, src, alt) {\n" +
                    "\tdiv.style.display = \"block\";\n" +
                    "\timg.src = src;\n" +
                    "\timg.alt = alt;\n" +
                    "}\n" +
                    "\n" +
                    "function hide(img, div, src, alt) {\n" +
                    "\tdiv.style.display = \"none\";\n" +
                    "\timg.src = src;\n" +
                    "\timg.alt = alt;\n" +
                    "}\n\n");

            getPage().addScript("ExpandScript", jsExpand.toString());
        }

        if (keepState && !getPage().isScriptAdded("KeepStateScript")) {
            StringBuffer jsKeepState = new StringBuffer();

            jsKeepState.append("function remove(array, row) {\n" +
                    "\tvar i = 0;\n" +
                    "\tvar found = false;\n" +
                    "\twhile (i<array.length && !found) {\n" +
                    "\t\tif (row == array[i]) {\n" +
                    "\t\t\tfound = true;\n" +
                    "\t\t} else {\n" +
                    "\t\t\ti++;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tif (found) {\n" +
                    "\t\tarray.splice(i,1);\n" +
                    "\t}\n" +
                    "}\n\n");
            jsKeepState.append("function changeState(divId, row, div) {\n" +
                    "\tvar tagState = find(tagsState, divId);\n" +
                    "\tif (tagState == null) {\n" +
                    "\t\texpanded = new Array();\n" +
                    "\t\tcontracted = new Array();\n" +
                    "\t\ttagState = new TagState(divId, expanded, contracted);\n" +
                    "\t\ttagsState.push(tagState);\n" +
                    "\t} else {\n" +
                    "\t\texpanded = tagState.expanded;\n" +
                    "\t\tcontracted = tagState.contracted;\n" +
                    "\t}\n" +
                    "\tif (div.style.display == \"none\") {\n" +
                    "\t\texpanded.push(row);\n" +
                    "\t\tremove(contracted, row);\n" +
                    "\t} else {\n" +
                    "\t\tcontracted.push(row);\n" +
                    "\t\tremove(expanded, row);\n" +
                    "\t}\n" +
                    "\tsave();\n" +
                    "}\n\n");
            jsKeepState.append("function save() {\n" +
                    "\tsetCookie(\"ExpandTag\", array2String(tagsState));\n" +
                    "}\n" +
                    "\n" +
                    "function load(divId) {\n" +
                    "\tvar cookie = getCookie(\"ExpandTag\");\n" +
                    "\tif (cookie != null) {\n" +
                    "\t\ttagsState = string2Array(cookie);\n" +
                    "\t\tvar tagState = find(tagsState, divId);\n" +
                    "\t\tif (tagState != null) {\n" +
                    "\t\t\tvar expanded = tagState.expanded;\n" +
                    "\t\t\tvar contracted = tagState.contracted;\n" +
                    "\t\t\tvar tagData = find(tagsData, divId);\n" +
                    "\t\t\tvar imgId = tagData.imgId;\n" +
                    "\t\t\tvar divId = tagData.divId;\n" +
                    "\t\t\tvar srcExp = tagData.srcExp;\n" +
                    "\t\t\tvar srcCont = tagData.srcCont;\n" +
                    "\t\t\tvar altExp = tagData.altExp;\n" +
                    "\t\t\tvar altCont = tagData.altCont;\n" +
                    "\t\t\tfor (var i=0; i<expanded.length; i++) {\n" +
                    "\t\t\t\tvar row = expanded[i];\n" +
                    "\t\t\t\tif (row == -1) {\n" +
                    "\t\t\t\t\tvar img = document.getElementById(imgId);\n" +
                    "\t\t\t\t\tvar div = document.getElementById(divId);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t\telse {\n" +
                    "\t\t\t\t\tvar img = document.getElementById(imgId + \"_\" + row);\n" +
                    "\t\t\t\t\tvar div = document.getElementById(divId + \"_\" + row);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t\tif (div != null) {\n" +
                    "\t\t\t\t\tshow(img, div, srcCont, altCont);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t\tfor (var j=0; j<contracted.length; j++) {\n" +
                    "\t\t\t\tvar row = contracted[j];\n" +
                    "\t\t\t\tif (row == -1) {\n" +
                    "\t\t\t\t\tvar img = document.getElementById(imgId);\n" +
                    "\t\t\t\t\tvar div = document.getElementById(divId);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t\telse {\n" +
                    "\t\t\t\t\tvar img = document.getElementById(imgId + \"_\" + row);\n" +
                    "\t\t\t\t\tvar div = document.getElementById(divId + \"_\" + row);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t\tif (div != null) {\n" +
                    "\t\t\t\t\thide(img, div, srcExp, altExp);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}\n\n");
            jsKeepState.append("function getCookieVal(offset) {\n" +
                    "\tvar endstr = document.cookie.indexOf (\";\", offset);\n" +
                    "\tif (endstr == -1) {\n" +
                    "\t\tendstr = document.cookie.length;\n" +
                    "\t}\n" +
                    "\treturn unescape(document.cookie.substring(offset, endstr));\n" +
                    "}\n" +
                    "\n" +
                    "function getCookie(name) {\n" +
                    "\tvar arg = name + \"=\";\n" +
                    "\tvar alen = arg.length;\n" +
                    "\tvar clen = document.cookie.length;\n" +
                    "\tvar i = 0;\n" +
                    "\twhile (i < clen) {\n" +
                    "\t\tvar j = i + alen;\n" +
                    "\t\tif (document.cookie.substring(i, j) == arg) {\n" +
                    "\t\t\treturn getCookieVal(j);\n" +
                    "\t\t}\n" +
                    "\t\ti = document.cookie.indexOf(\" \", i) + 1;\n" +
                    "\t\tif (i == 0) break; \n" +
                    "\t}\n" +
                    "\treturn null;\n" +
                    "}\n" +
                    "\n" +
                    "function setCookie(name, value, expires, path, domain, secure) {\n" +
                    "\tdocument.cookie = name + \"=\" + escape (value) +\n" +
                    "\t\t((expires) ? \"; expires=\" + expires : \"\") +\n" +
                    "\t\t((path) ? \"; path=\" + path : \"\") +\n" +
                    "\t\t((domain) ? \"; domain=\" + domain : \"\") +\n" +
                    "\t\t((secure) ? \"; secure\" : \"\");\n" +
                    "}\n\n");
            jsKeepState.append("function object2String(obj) {\n" +
                    "\tvar val, output = \"\";\n" +
                    "\tif (obj.length ==0) {\n" +
                    "\t\treturn \"[]\";\n" +
                    "\t}\n" +
                    "\tif (obj) {    \n" +
                    "\t\toutput += \"{\";\n" +
                    "\t\tfor (var i in obj) {\n" +
                    "\t\t\tval = obj[i];\n" +
                    "\t\t\tswitch (typeof val) {\n" +
                    "\t\t\t\tcase (\"object\"):\n" +
                    "\t\t\t\t\tif (val[0]!=null) {\n" +
                    "\t\t\t\t\t\toutput += i + \":\" + array2String(val) + \",\";\n" +
                    "\t\t\t\t\t} else {\n" +
                    "\t\t\t\t\t\toutput += i + \":\" + object2String(val) + \",\";\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t\tbreak;\n" +
                    "\t\t\t\tcase (\"string\"):\n" +
                    "\t\t\t\t\toutput += i + \":'\" + escape(val) + \"',\";\n" +
                    "\t\t\t\t\tbreak;\n" +
                    "\t\t\t\tdefault:\n" +
                    "\t\t\t\t\toutput += i + \":\" + val + \",\";\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t\toutput = output.substring(0, output.length-1) + \"}\";\n" +
                    "\t}\n" +
                    "\treturn output;\n" +
                    "}\n" +
                    "\n" +
                    "function string2Object(string) {\n" +
                    "\teval(\"var result = \" + string);\n" +
                    "\treturn result;\n" +
                    "}\n\n");
            jsKeepState.append("function array2String(array) {\n" +
                    "\tvar output = \"\";\n" +
                    "\tif (array.length ==0) {\n" +
                    "\t\treturn \"[]\";\n" +
                    "\t}\n" +
                    "\tif (array) {\n" +
                    "\t\toutput += \"[\";\n" +
                    "\t\tfor (var i in array) {\n" +
                    "\t\tval = array[i];\n" +
                    "\t\t\tswitch (typeof val) {\n" +
                    "\t\t\t\tcase (\"object\"):\n" +
                    "\t\t\t\t\tif (val[0]!=null) {\n" +
                    "\t\t\t\t\t\toutput += array2String(val) + \",\";\n" +
                    "\t\t\t\t\t} else {\n" +
                    "\t\t\t\t\t\toutput += object2String(val) + \",\";\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t\tbreak;\n" +
                    "\t\t\t\tcase (\"string\"):\n" +
                    "\t\t\t\t\toutput += \"'\" + escape(val) + \"',\";\n" +
                    "\t\t\t\t\tbreak;\n" +
                    "\t\t\t\tdefault:\n" +
                    "\t\t\t\t\toutput += val + \",\";\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t\toutput = output.substring(0, output.length-1) + \"]\";\n" +
                    "\t}\n" +
                    "\treturn output;\n" +
                    "}\n" +
                    "\n" +
                    "function string2Array(string) {\n" +
                    "\teval(\"var result = \" + string);\n" +
                    "\treturn result;\n" +
                    "}\n\n");

            getPage().addScript("KeepStateScript", jsKeepState.toString());
        }
    }

    public static boolean isSubString(String sub, String str) {
        for (int j=0; j<str.length(); j++) {
            if (str.regionMatches(j, sub, 0, sub.length())) {
                return true;
            }
        }
        return false;
    }
}
