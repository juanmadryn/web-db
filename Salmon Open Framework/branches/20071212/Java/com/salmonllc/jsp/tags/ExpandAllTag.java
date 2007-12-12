package com.salmonllc.jsp.tags;


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspExpandAll;

/**
 * This tag creates an expandall component
 * @author Nicolás Genta
 */
public class ExpandAllTag extends BaseBodyTag {

    private String _expand;
    private String _imgexp;
    private String _imgcont;
    private String _altexp;
    private String _altcont;

    public HtmlComponent createComponent() {
        JspExpandAll ht = new JspExpandAll(getName(), _expand, _imgexp, _imgcont, getHelper().getController());
        ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
        if (!BaseTagHelper.isEmpty(_altexp)) {
            ht.setAltexp(_altexp);
        }
        if (!BaseTagHelper.isEmpty(_altcont)) {
            ht.setAltcont(_altcont);
        }
        return ht;
    }

    public int getTagConvertType() {
        return CONV_WRAP_ALL_NESTED;
    }

    public void release() {
        super.release();
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
}
