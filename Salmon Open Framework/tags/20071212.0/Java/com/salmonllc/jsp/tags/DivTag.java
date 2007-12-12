package com.salmonllc.jsp.tags;


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspDiv;

/**
 * This tag creates a div component
 * @author Nicolás Genta
 */
public class DivTag extends BaseBodyTag {

    private String _hidden;
    private String _style;

    public HtmlComponent createComponent() {
        JspDiv ht = new JspDiv(getName(), getHelper().getController());
        ht.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
        if (!BaseTagHelper.isEmpty(_hidden)) {
            boolean hidden = BaseTagHelper.stringToBoolean(_hidden);
            ht.setHidden(hidden);
        }
        if (!BaseTagHelper.isEmpty(_style)) {
            ht.setStyle(_style);
        }
        return ht;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_WRAP_ALL_NESTED;
    }
    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release() {
        super.release();
    }

    public String getHidden() {
        return _hidden;
    }

    public void setHidden(String hidden) {
        this._hidden = hidden;
    }

    public String getStyle() {
        return _style;
    }

    public void setStyle(String style) {
        this._style = style;
    }
}

