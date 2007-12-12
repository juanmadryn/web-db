package com.salmonllc.jsp.tags;

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/AppletTag.java $
//$Author: Dan $
//$Revision: 6 $
//$Modtime: 6/16/03 5:31p $
////////////////////////////////////////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.Constants;

import javax.servlet.jsp.JspWriter;

/**
 * This tag approximately corresponds to the HTML tag "INPUT"
 */

public class AppletTag extends BaseBodyTag {
    private String _code;
    private String _codeBase;
    private String _archive;
    private String _alt;
    private String _align;
    private String _object;
    private String _title;
    private String _mayScript;
    private String _value;
    private String _state;
    private String _interactWithForm;
    private String _usePlugin;
    private String _pluginClassId;
    private String _pluginCodeBase;
    private String _type;
    private String _pluginsPage;
    private String _height;
    private String _width;
    private String _hspace;
    private String _vspace;
    private String _nojavasupport;
    private String _appletdatasource;

    /**
     * Creates the component to be used by the tag.
     */
    public HtmlComponent createComponent() {
            HtmlApplet applet = new HtmlApplet(getName(), _value,  getHelper().getController());
            if (getVisible()!=null && getVisible().startsWith(_appletdatasource+":"))
              applet.setVisible(getVisible());
            else
              applet.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
            applet.setValue(_value);
            applet.setAlign(_align);
            applet.setAltText(_alt);
            if (_height != null) {
                if (_height.endsWith("%") || _height.startsWith(_appletdatasource+":"))
                  applet.setHeight(_height);
                else
                  applet.setHeight(BaseTagHelper.stringToInt(_height,-1));
            }
            if (_width != null) {
               if (_width.endsWith("%")  || _width.startsWith(_appletdatasource+":"))
                 applet.setWidth(_width);
               else
                 applet.setWidth(BaseTagHelper.stringToInt(_width,-1));
            }
            if (_hspace != null) {
                if (_hspace.startsWith(_appletdatasource+":"))
                  applet.setHSpace(_hspace);
                else
                 applet.setHSpace(BaseTagHelper.stringToInt(_hspace,-1));
            }
            if (_vspace != null) {
                if (_vspace.startsWith(_appletdatasource+":"))
                  applet.setVSpace(_vspace);
                else
                  applet.setVSpace(BaseTagHelper.stringToInt(_vspace,-1));
            }
            applet.setAppletAttributesDatasource(_appletdatasource);
            applet.setArchive(_archive);
            applet.setCode(_code);
            applet.setCodeBase(_codeBase);
            if (_interactWithForm!=null) {
                if (_interactWithForm.startsWith(_appletdatasource+":"))
                  applet.setInteractWithForm(_interactWithForm);
                else
                  applet.setInteractWithForm(BaseTagHelper.stringToBoolean(_interactWithForm,false));
            }
            if (_mayScript!=null) {
                if (_mayScript.startsWith(_appletdatasource+":"))
                  applet.setMayScript(_mayScript);
                else
                  applet.setMayScript(BaseTagHelper.stringToBoolean(_mayScript,false));
            }
            if (_nojavasupport!=null)
                applet.setNoJavaSupport(_nojavasupport);
            applet.setObject(_object);
            if (_pluginClassId!=null)
                applet.setPluginClassId(_pluginClassId);
            if (_pluginCodeBase!=null)
                applet.setPluginCodeBase(_pluginCodeBase);
            if (_pluginsPage!=null)
                applet.setPluginsPage(_pluginsPage);
            applet.setState(_state);
            applet.setTitle(_title);
            if (_type!=null)
                applet.setType(_type);
            if (_usePlugin!=null)
              applet.setUsePlugin(BaseTagHelper.stringToBoolean(_usePlugin,false));
            applet.setValue(_value);

            return applet;
    }

    /**
     * This method is called when necessary to generate the required html for a tag. It should be overridden by tags that have more Html to generate (generally tags that require several passes to complete). A tag shouldn't generate any Html itself, but should instead delagate that to the Html or JSP component within it.
     */

    protected void generateComponentHTML(JspWriter w) throws Exception {
        super.generateComponentHTML(w);
        TagContext t = getHelper().getTagContext();
        if (!t.getDreamWeaverMode() && !t.getRefIndexPrinted()) {
            w.print("<INPUT TYPE=\"HIDDEN\" NAME=\"Page_refIndex_hidden\" VALUE=\"" + getHelper().getController().getRefIndex() + "\">");
// Add a transaction token (if present in our session)
            javax.servlet.http.HttpSession session = getHelper().getController().getSession();
            if (session != null) {
                String token =
                        (String) session.getAttribute(HtmlPage.TRANSACTION_TOKEN_KEY);
                if (token != null) {
                    w.print("<input type=\"hidden\" name=\"" + Constants.TOKEN_KEY + "\" value=\"" + token + "\">");
                }
            }
            t.setRefIndexPrinted(true);
        }
    }

    /**
     * get the tag's align attribute
     */
    public String getAlign() {
        return _align;
    }

    /**
     * get the tag's alt attribute
     */
    public String getAlt() {
        return _alt;
    }

    /**
     * get the tag's appletdatasource attribute
     */
    public String getAppletdatasource() {
        return _appletdatasource;
    }


    /**
     * get the tag's archive attribute
     */
    public String getArchive() {
        return _archive;
    }

    /**
     * get the tag's code attribute
     */
    public String getCode() {
        return _code;
    }

    /**
     * get the tag's codebase attribute
     */
    public String getCodebase() {
        return _codeBase;
    }



    /**
     * Set the tag's wrap attribute
     */

    public String getHeight() {
        return _height;
    }

    /**
     * gets the Hspace
     */
    public String getHspace() {
        return _hspace;
    }

    /**
     * get the tag's interactwithforms attribute
     */
    public String getInteractwithform() {
        return _interactWithForm;
    }

    /**
     * get the tag's mayscript attribute
     */
    public String getMayscript() {
        return _mayScript;
    }

    /**
     * get the tag's nojavasupport attribute
     */
    public String getNojavasupport() {
        return _nojavasupport;
    }

    /**
     * get the tag's object attribute
     */
    public String getObject() {
        return _object;
    }

    /**
     * get the tag's pluginclassid attribute
     */
    public String getPluginclassid() {
        return _pluginClassId;
    }

    /**
     * get the tag's plugincodebase attribute
     */
    public String getPlugincodebase() {
        return _pluginCodeBase;
    }

    /**
     * get the tag's pluginspage attribute
     */
    public String getPluginspage() {
        return _pluginsPage;
    }

    /**
     * get the tag's state attribute
     */
    public String getState() {
        return _state;
    }

    /**
     * get the tag's title attribute
     */
    public String getTitle() {
        return _title;
    }


    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_WRAP_ALL_NESTED;
    }


    /**
     * Get the tag's type attribute
     */
    public String getType() {
        return _type;
    }

    /**
     * get the tag's useplugin attribute
     */
    public String getUseplugin() {
        return _usePlugin;
    }


    /**
     * Get the tag's value attribute
     */
    public String getValue() {
        return _value;
    }

    /**
     * gets the Vspace
     */
    public String getVspace() {
        return _vspace;
    }

    /**
     * Set the tag's width attribute
     */

    public String getWidth() {
        return _width;
    }


    /**
     * Release all resources used by the tag.
     */
    public void release() {
        super.release();
        _align = null;
        _alt=null;
        _archive=null;
        _code=null;
        _codeBase=null;
        _height = null;
        _hspace = null;
        _interactWithForm=null;
        _mayScript=null;
        _nojavasupport=null;
        _object=null;
        _pluginClassId=null;
        _pluginCodeBase=null;
        _pluginsPage=null;
        _state=null;
        _title=null;
        _type = null;
        _usePlugin=null;
        _value = null;
        _vspace = null;
        _width = null;
    }

    /**
     * Set the tag's align attribute
     */
    public void setAlign(String align) {
        _align = align;
    }

    /**
     * sets the tag's alt attribute
     */
    public void setAlt(String alt) {
        _alt = alt;
    }


    /**
     * set the tag's appletdatasource attribute
     */
    public void setAppletdatasource(String sAppletDatasource) {
        _appletdatasource=sAppletDatasource;
    }

    /**
     * set the tag's archive attribute
     */
    public void setArchive(String sArchive) {
        _archive=sArchive;
    }

    /**
     * set the tag's code attribute
     */
    public void setCode(String sCode) {
        _code=sCode;
    }

    /**
     * set the tag's codebase attribute
     */
    public void setCodebase(String sCodebase) {
        _codeBase=sCodebase;
    }

    /**
     * Set the tag's height attribute
     */

    public void setHeight(String val) {
        _height = val;
    }

    /**
     * sets the Hspace
     */
    public void setHspace(String hSpace) {
        _hspace = hSpace;
    }



    /**
     * set the tag's interactwithforms attribute
     */
    public void setInteractwithform(String sInteractWithForms) {
        _interactWithForm=sInteractWithForms;
    }

    /**
     * set the tag's mayscript attribute
     */
    public void setMayscript(String sMayscript) {
        _mayScript=sMayscript;
    }

    /**
     * set the tag's nojavasupport attribute
     */
    public void setNojavasupport(String sNoJavaSupport) {
        _nojavasupport=sNoJavaSupport;
    }

    /**
     * set the tag's object attribute
     */
    public void setObject(String sObject) {
        _object=sObject;
    }

    /**
     * set the tag's pluginclassid attribute
     */
    public void setPluginclassid(String sPluginClassId) {
        _pluginClassId=sPluginClassId;
    }

    /**
     * set the tag's plugincodebase attribute
     */
    public void setPlugincodebase(String sPluginCodebase) {
        _pluginCodeBase=sPluginCodebase;
    }

    /**
     * set the tag's pluginspage attribute
     */
    public void setPluginspage(String sPluginspage) {
        _pluginsPage=sPluginspage;
    }

    /**
     * set the tag's state attribute
     */
    public void setState(String sState) {
        _state=sState;
    }

    /**
     * set the tag's title attribute
     */
    public void setTitle(String sTitle) {
        _title=sTitle;
    }


    /**
     * Set the tag's type attribute
     */

    public void setType(String type) {
        _type = type;
    }


    /**
     * set the tag's useplugin attribute
     */
    public void setUseplugin(String sUseplugin) {
        _usePlugin=sUseplugin;
    }



    /**
     * Set the tag's value attribute
     */

    public void setValue(String value) {
        _value = value;
    }

    /**
     * sets the Vspace
     */
    public void setVspace(String vSpace) {
        _vspace = vSpace;
    }

    /**
     * Set the tag's width attribute
     */

    public void setWidth(String val) {
        _width = val;
    }

}
