package com.salmonllc.wml.tags;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/wml/tags/CardTag.java $
//$Author: Dan $
//$Revision: 5 $
//$Modtime: 11/15/02 3:04p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.*;
import com.salmonllc.jsp.tags.BaseTagHelper;
import com.salmonllc.jsp.tags.ContainerTag;
import com.salmonllc.jsp.tags.DataTableTag;
import com.salmonllc.jsp.tags.ListTag;
import com.salmonllc.util.MessageLog;
import com.salmonllc.wml.*;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;

/**
 * This is a tag used to create a card in WML.
 */

public class CardTag extends ContainerTag {
    private String _title;
    private String _box;
    private String _newcontext;
    private String _ordered;
    private String _onenterforward;
    private String _onenterbackward;
    private String _ontimer;
    private String _forwardDataSource;
    private String _backwardDataSource;
    private String _timerDataSource;
    private WmlCard _currentCard;

    /**
     * This method creates the WmlCard used by the tag.
     */

    public HtmlComponent createComponent() {
        WmlCard card = new WmlCard(getName(), getHelper().getController());
        if (!BaseTagHelper.isEmpty(_title))
            card.setTitle(_title);

        if (!BaseTagHelper.isEmpty(_newcontext))
            card.setNewContext(new Boolean(_newcontext).booleanValue());

        if (!BaseTagHelper.isEmpty(_ordered))
            card.setOrdered(new Boolean(_ordered).booleanValue());

        if (!BaseTagHelper.isEmpty(_onenterforward))
            card.setOnEnterForwardHref(_onenterforward);

        if (!BaseTagHelper.isEmpty(_onenterbackward))
            card.setOnEnterBackwardHref(_onenterbackward);

        if (!BaseTagHelper.isEmpty(_ontimer))
            card.setOnTimerHref(_ontimer);

        String sDatasource=_forwardDataSource!=null?_forwardDataSource:(_backwardDataSource!=null?_backwardDataSource:(_timerDataSource!=null?_timerDataSource:null));
        if (sDatasource != null)
            card.setDataSource(sDatasource);
        if (!BaseTagHelper.isEmpty(_forwardDataSource)) {
            card.setForwardDatasource(_forwardDataSource);
        }
        if (!BaseTagHelper.isEmpty(_backwardDataSource)) {
            card.setBackwardDatasource(_backwardDataSource);
        }
        if (!BaseTagHelper.isEmpty(_timerDataSource)) {
            card.setTimerDatasource(_timerDataSource);
        }
        if (getClassname() != null)
            card.setClassName(getClassname());
        _currentCard=card;
        return card;
    }

    /**
     * Get the WmlCard object that this tag represents
     */
    public WmlCard getCard() {
        return _currentCard;
    }
    /**
     * This method generates the html used by the tag.
     */

    public void generateComponentHTML(JspWriter p) throws IOException {
        WmlCard card = (WmlCard) getHelper().getController().getComponent(getName());
        if (card == null)
            return;
        int startRow = -1;
        DataTableTag dt = getHelper().getDataTableTag();
        if (dt != null)
        {
            startRow = dt.getStartRow();
        }
        else
        {
            ListTag l = getHelper().getListTag();
            if (l != null)
            {
                startRow = l.getRow();
            }
        }
        TagWriter w = getTagWriter();
        w.setWriter(p);
        card.generateHTML(w, _box,startRow);
    }


    /**
     * Get the Forward Data Source the component should be bound to
     */
    public String getForwarddatasource()
    {
        return _forwardDataSource;
    }

    /**
     * Get the Backward Data Source the component should be bound to
     */
    public String getBackwarddatasource()
    {
        return _backwardDataSource;
    }

    /**
     * Get the Timer Data Source the component should be bound to
     */
    public String getTimerdatasource()
    {
        return _timerDataSource;
    }

    /**
     * Get the tag's title attribute
     */
    public String getTitle() {
        return _title;
    }


    /**
     * Get the tag's newcontext attribute
     */
    public String getNewcontext() {
        return _newcontext;
    }

    /**
     * Get the tag's ordered attribute
     */
    public String getOrdered() {
        return _ordered;
    }

    /**
     * Get the tag's onenterforward attribute
     */
    public String getOnenterforward() {
        return _onenterforward;
    }

    /**
     * Get the tag's onenterbackward attribute
     */
    public String getOnenterbackward() {
        return _onenterbackward;
    }

    /**
     * Get the tag's ontimer attribute
     */
    public String getOntimer() {
        return _ontimer;
    }

    /**
     * Set the tag's onenterforward attribute
     */
    public void setOnenterforward(String _onenterforward) {
        this._onenterforward = _onenterforward;
    }

    /**
     * Set the tag's onenterbackward attribute
     */
    public void setOnenterbackward(String _onenterbackward) {
        this._onenterbackward = _onenterbackward;
    }

    /**
     * Get the tag's ontimer attribute
     */
    public void setOntimer(String _ontimer) {
        this._ontimer = _ontimer;
    }

    /**
     * Returns the type of DreamWeaver conversion that this tag uses.
     */
    public int getTagConvertType() {
        return CONV_CUSTOM;
    }



    /**
     * For Tags that require multiple passes to render their Html, this method should be implemented. It wll be called for each mode until it returns false. Also, this method is responsible for storing the results of the tags nested within it. Once the method returns false, the generateComponentHtml method will be called and here, the stored data from the nested tags should be sent to the output stream.
     */
    public boolean incrementMode() {
        try {
            _box = getBodyContentData(true);
        } catch (Exception e) {
            MessageLog.writeErrorMessage("incrementMode()", e, this);
        }
        return false;
    }

    /**
     * Initializes the tag
     */
    public void initMode() {
        _box = null;
        _backwardDataSource = null;
        _currentCard = null;
        _forwardDataSource = null;
        _newcontext = null;
        _onenterbackward = null;
        _onenterforward = null;
        _ontimer = null;
        _timerDataSource = null;
        _title = null;
    }

    /**
     * Part of the tag library specification. Clears all resources used by the tag.
     */
    public void release() {
        super.release();
        _box = null;
        _backwardDataSource = null;
        _currentCard = null;
        _forwardDataSource = null;
        _newcontext = null;
        _onenterbackward = null;
        _onenterforward = null;
        _ontimer = null;
        _timerDataSource = null;
        _title = null;
    }

    /**
     * Set the Forward Data Source the component should be bound to
     */
    public void setForwarddatasource(String val)
    {
        _forwardDataSource = val;
    }

    /**
     * Set the Backward Data Source the component should be bound to
     */
    public void setBackwarddatasource(String val)
    {
        _backwardDataSource = val;
    }

    /**
     * Set the Timer Data Source the component should be bound to
     */
    public void setTimerdatasource(String val)
    {
        _timerDataSource = val;
    }


    /**
     * Sets the tag's title attribute
     */
    public void setTitle(String title) {
        _title = title;
    }

    /**
     * Sets the tag's newcontext attribute
     */
    public void setNewcontext(String newcontext) {
        _newcontext = newcontext;
    }

    /**
     * Sets the tag's ordered attribute
     */
    public void setOrdered(String ordered) {
        _ordered = ordered;
    }


}
