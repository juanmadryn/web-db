
 //** Copyright Statement ***************************************************
 //The Salmon Open Framework for Internet Applications (SOFIA)
 // Copyright (C) 1999 - 2002, Salmon LLC
 //
 // This program is free software; you can redistribute it and/or
 // modify it under the terms of the GNU General Public License version 2
 // as published by the Free Software Foundation;
 //
 // This program is distributed in the hope that it will be useful,
 // but WITHOUT ANY WARRANTY; without even the implied warranty of
 // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 // GNU General Public License for more details.
 //
 // You should have received a copy of the GNU General Public License
 // along with this program; if not, write to the Free Software
 // Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 //
 // For more information please visit http://www.salmonllc.com
 //** End Copyright Statement ***************************************************

package com.salmonllc.wml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/wml/WmlDo.java $
//$Author: Dan $
//$Revision: 10 $
//$Modtime: 6/12/03 1:31p $
/////////////////////////

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.*;
import com.salmonllc.jsp.tags.PageTag;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This container will generate a Wml Do tag.
 */
public class WmlDo extends JspContainer {
    private String _href;
    private DataStoreEvaluator _dsEval;
    private int _rowNo = -1;
    private String _class;
    private String _method;
    private String _acceptcharset;
    private boolean _sendreferer;
    private WmlCard _card;
    private Vector _listeners;
    private boolean _hrefparameters=true;
    private String _type;
    private String _label;
    private boolean _optional;
    private boolean _doprev;
    private boolean _postfields=true;


    /**
     * WmlLink constructor comment.
     * @param name The name of the link
     * @param href The url for the link
     * @param p The page the do will go in.
     */

    public WmlDo(String name, String href, com.salmonllc.html.HtmlPage p) {
        super(name, p);
        _href = href;
    }

    /**
     * This method returns whether the postfields will be generated for the controls in the card.
     * @return boolean
     */
    public boolean isPostFields() {
        return _postfields;
    }

    /**
     * This method sets whether the postfields will be generated for the controls in the card.
     */
    public void setPostFields(boolean _postfields) {
        this._postfields = _postfields;
    }

    /**
     * This method returns the type attribute of do tag.
     * @return String
     */
    public String getType() {
        return _type;
    }

    /**
     * This method sets the type attribute of do tag.
     */
    public void setType(String _type) {
        this._type = _type;
    }

    /**
     * This method returns the label attribute of do tag.
     * @return String
     */
    public String getLabel() {
        return _label;
    }

    /**
     * This method sets the label attribute of do tag.
     */
    public void setLabel(String _label) {
        this._label = _label;
    }

    /**
     * This method returns the optional attribute of do tag.
     * @return String
     */
    public boolean isOptional() {
        return _optional;
    }

    /**
     * This method returns the optional attribute of do tag.
     */
    public void setOptional(boolean _optional) {
        this._optional = _optional;
    }

    /**
     * This method returns whether the do tag represents a prev command action.
     * @return boolean
     */
    public boolean isDoPrev() {
        return _doprev;
    }

    /**
     * This method sets whether the do tag represents a prev command action.
     */
    public void setDoPrev(boolean _doprev) {
        this._doprev = _doprev;
    }

    /**
     *Does the binding for the component. This method is called by the framework and should not be called directly
     */
    public void doBinding() throws Exception {
        String dataSource = getDataSource();
        String dsName = null;
        String dsExp = null;

        if (dataSource == null)
            return;

        int pos = dataSource.indexOf(":");
        if (pos == -1)
            dsName = dataSource;
        else {
            dsName = dataSource.substring(0, pos);
            dsExp = dataSource.substring(pos + 1);
        }

        DataStoreBuffer ds = ((JspController)getPage()).getDataSource(dsName);
        if (ds == null)
            return;

        if (!ds.getAutoBind())
            return;

        setHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
    }

    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(TagWriter t, String box, int rowNo) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();

        if (!_visible)
            return;
        StringBuffer href = null;
           try {
                if (_dsEval != null) {
                    if (rowNo > -1) {
                        _href = _dsEval.evaluateRowFormat(rowNo);
                    }
                    else {
                        _href = _dsEval.evaluateRowFormat();
                    }
                }
            }
            catch (Exception e) {
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_href)) {
                href = new StringBuffer(_href);
                int hrefLength = href.length();
                for (int i = 0; i < hrefLength; i++) {
                    if (href.charAt(i) == ' ') {
                        href.setCharAt(i, '+');
                    }
                }
            }
        String row = "";

        if (rowNo != -1)
            row = "_" + row + new Integer(rowNo).toString();

        StringBuffer sbQuery=new StringBuffer();
        if(_hrefparameters && !_doprev) {
            if (getPage().isWMLMaintained() && encodeURL(_href).indexOf(PageTag.getSessionIdentifier()+"="+PageTag.getWmlSessId(getPage().getSession()))<0)
                sbQuery.append(PageTag.getSessionIdentifier()+"="+PageTag.getWmlSessId(getPage().getSession())+"&amp;");
            if (_card!=null) {
                WmlFormComponent[] wfca=_card.getInputComponents();
                if (wfca!=null) {
                    for (int i=0;i<wfca.length;i++) {
                       String sParameter=wfca[i].getName()+"=$("+wfca[i].getName()+")";
                       if (_href.indexOf(sParameter)<0)
                         sbQuery.append(sParameter+"&amp;");
                    }
                }
             }
            if (_method!=null && _method.toUpperCase().equals("POST")) {
                if (_href.indexOf("method=post")<0)
                  sbQuery.append("method=post&amp;");
            }
            if (_href.indexOf(getName()+row+"=1")<0)
                sbQuery.append(getName()+row+"=1&amp;");
            if (sbQuery.toString().endsWith("&amp;"))
              sbQuery.delete(sbQuery.length()-5,sbQuery.length());
         }

        sb.append("<do");
        sb.append(" id=\"" + getName() + row + "\"");
        sb.append(" type=\"" + _type + "\"");
        if (_class != null)
            sb.append(" class=\"" + _class + "\"");
        if (_label != null)
            sb.append(" label=\"" + _label + "\"");
        sb.append(" name=\"" + getName() + row + "\"");
        if (_optional)
            sb.append(" optional=\"true\"");
        sb.append(">");
        if (!_doprev && _href!=null && !_href.trim().equals("")) {
            sb.append("<go");
            if (_href.indexOf('?')<0)  {
                if (!sbQuery.toString().trim().equals(""))
                    sb.append(" href=\"" + encodeURL(href+"?"+sbQuery.toString()) + "\"");
                else
                    sb.append(" href=\"" + encodeURL(href) + "\"");
            }
            else
                sb.append(" href=\"" + encodeURL(href+"&amp;"+sbQuery.toString()) + "\"");
            if (_sendreferer)
                sb.append(" sendreferer=\"true\"");
            if (_method != null)
                sb.append(" method=\"" + _method + "\"");
            if (_acceptcharset != null)
                sb.append(" accept-charset=\"" + _acceptcharset + "\"");
            sb.append(">");
            if (_postfields) {
                 if (_card!=null) {
                    WmlFormComponent[] wfca=_card.getInputComponents();
                    if (wfca!=null) {
                        for (int i=0;i<wfca.length;i++)
                            sb.append("\r\n"+wfca[i].getPostForm()+"\r\n");
                    }
                 }
                 if (_method!=null && _method.toUpperCase().equals("POST"))
                     sb.append("\r\n<postfield name=\"method\" value=\"POST\"/>\r\n");
                 if (getPage().isWMLMaintained() && encodeURL(_href).indexOf(PageTag.getSessionIdentifier()+"="+PageTag.getWmlSessId(getPage().getSession()))<0)
                     sb.append("\r\n<postfield name=\""+PageTag.getSessionIdentifier()+"\" value=\""+PageTag.getWmlSessId(getPage().getSession())+"\"/>\r\n");
                sb.append("\r\n<postfield name=\""+getName()+row+"\" value=\"\"/>\r\n");
            }
            sb.append("</go>");
        }
        else {
            if (_doprev) {
                sb.append("<prev>");
            }
        }
        t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        t.print(box, TagWriter.TYPE_CONTENT);
        t.print("</do>", TagWriter.TYPE_END_TAG);
    }
    /**
     * Returns the href for the go tag within the do.
     */
    public String getHref() {
        return _href;
    }
    /**
     * This method gets the DataStoreEvaluator being used for href expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getHrefExpression() {
        return _dsEval;
    }
    /**
     * Use this method to get the class attribute in the do tag.
     */
    public String getClassName() {
        return _class;
    }
    /**
     * Use this method to get the method for the go tag within the do.
     */
    public String getMethod() {
        return _method;
    }
    /**
     * This method sets Whether or not to sendreferer.
     */
    public void setSendReferer(boolean sendreferer) {
        _sendreferer = sendreferer;
    }
    /**
     * This method sets Whether or not to place the field values as parameters on the href.
     */
    public void setHrefParameters(boolean hrefparameters) {
        _hrefparameters = hrefparameters;
    }
    /**
     * This method sets the accept-charset.
     */
    public void setAcceptCharset(String acceptcharset) {
        _acceptcharset = acceptcharset;
    }
    /**
     * This method sets the method.
     */
    public void setMethod(String method) {
        _method = method;
    }
    /**
     * Sets the href for the go tag in the do tag
     * @param href java.lang.String
     */
    public void setHref(String href) {
        _href = href;
    }
    /**
     * This method sets a datastore expression that will be used to compute the href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     */
    public void setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     */
    public void setHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets the class for the do.
     */
    public void setClassName(String className) {
        _class = className;
    }

    /**
     * This method sets the card that this do tag is within.
     */
    public void setCard(WmlCard card) {
        _card=card;
    }

    /**
     *Processes the parms of the submitted form. This method is called by the framework and should not be called directly
     */
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {
         String name = getName();
         if (rowNo > -1)
             name += "_" + rowNo;

         if (parms.get(name) != null) {
             _rowNo = rowNo;
             return true;
         }

         return false;
     }

    /**
     * This method adds a listener the will be notified when this do action causes the page to be submitted.
     * @param l The listener to add.
     */
    public void addSubmitListener(SubmitListener l) {
        if (_listeners == null)
            _listeners = new Vector();

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
                return;
        }

        _listeners.addElement(l);
    }

    /**
     *Executes any events due to this component. This method is called by the framework and should not be called directly
     */
    public boolean executeEvent(int type) throws Exception {
        if (type != EVENT_SUBMIT)
            return true;

        if (_listeners == null)
            return true;

        SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);

        for (int i = 0; i < _listeners.size(); i++) {
            SubmitListener l = (SubmitListener) _listeners.elementAt(i);
            e.setNextListener( _listeners.size() > (i + 1) ?_listeners.elementAt(i + 1) : null);
            if (!l.submitPerformed(e))
                return false;
        }

        return true;
    }

    /**
     * This method removes a listener from the list that will be notified if this button causes the page to be submitted.
     * @param l The listener to remove.
     */
    public void removeSubmitListener(SubmitListener l) {
        if (_listeners == null)
            return;

        for (int i = 0; i < _listeners.size(); i++) {
            if (((SubmitListener) _listeners.elementAt(i)) == l) {
                _listeners.removeElementAt(i);
                return;
            }
        }
    }

}
