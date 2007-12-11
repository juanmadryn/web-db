package com.salmonllc.jsp.tags;


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspExpand;

/**
 * This tag creates an expand component
 * @author Nicolás Genta
 */
public class ExpandTag extends BaseBodyTag {
    
    private String _div;
    private String _imgexp;
    private String _imgcont;
    private String _altexp;
    private String _altcont;
    private String _keepstate;

    public HtmlComponent createComponent() {
        JspExpand ht = new JspExpand(getName(), _div, _imgexp, _imgcont, getHelper().getController());
        ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
        if (!BaseTagHelper.isEmpty(_altexp)) {
            ht.setAltexp(_altexp);
        }
        if (!BaseTagHelper.isEmpty(_altcont)) {
            ht.setAltcont(_altcont);
        }
        if (!BaseTagHelper.isEmpty(_keepstate)) {
            boolean keepState = BaseTagHelper.stringToBoolean(_keepstate);
            ht.setKeepstate(keepState);
        }
        return ht;
    }

    public int getTagConvertType() {
        return CONV_WRAP_ALL_NESTED;
    }

    public void release() {
        super.release();
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

    public String getKeepstate() {
        return _keepstate;
    }

    public void setKeepstate(String keepstate) {
        this._keepstate = keepstate;
    }
}
