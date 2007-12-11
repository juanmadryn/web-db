package com.salmonllc.wml;

import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.*;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.util.Vector;



/**
 * This container represents a card in WML
 */

public class WmlCard extends JspContainer {
    private String _title;
    private String _onenterforward;
    private String _onenterbackward;
    private String _ontimer;
    private boolean _newcontext;
    private boolean _ordered = true ;
    private DataStoreEvaluator _dsForwardEval;
    private DataStoreEvaluator _dsBackwardEval;
    private DataStoreEvaluator _dsTimerEval;
    private String _forwardDatasource;
    private String _backwardDatasource;
    private String _timerDatasource;
    private String _class;
    private Vector _vinputs=new Vector();

    public void addInputComponent(WmlFormComponent wfc) {
          _vinputs.addElement(wfc);
    }

    public WmlFormComponent[] getInputComponents() {
        if (_vinputs.size()==0)
          return null;
        WmlFormComponent[] hfca=new WmlFormComponent[_vinputs.size()];
        System.arraycopy(_vinputs.toArray(),0,hfca,0,_vinputs.size());
        return hfca;
    }

    /**
     * Use this method to get the class attribute used in the Card tag
     */
    public String getClassName() {
        return _class;
    }

    /**
     * This method sets the class attribute for the card.
     */
    public void setClassName(String className) {
        _class = className;
    }

    /**
     * Use this method to get the name of the ForwardDatasource
     */
    public String getForwardDatasource() {
        return _forwardDatasource;
    }

    /**
     * Use this method to set the name of the ForwardDatasource
     */
    public void setForwardDatasource(String _forwardDatasource) {
        this._forwardDatasource = _forwardDatasource;
    }

    /**
     * Use this method to get the name of the TimerDatasource
     */
    public String getTimerDatasource() {
        return _timerDatasource;
    }

    /**
     * Use this method to set the name of the TimerDatasource
     */
    public void setTimerDatasource(String _timerDatasource) {
        this._timerDatasource = _timerDatasource;
    }

    /**
     * Use this method to get the name of the BackwardDatasource
     */
    public String getBackwardDatasource() {
        return _backwardDatasource;
    }

    /**
     * Use this method to set the name of the BackwardDatasource
     */
    public void setBackwardDatasource(String _backwardDatasource) {
        this._backwardDatasource = _backwardDatasource;
    }

    /**
     * This method sets a datastore expression that will be used to compute the Forward Href for the card.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     */
    public void setForwardHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsForwardEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the Forward Href for the card.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     */
    public void setForwardHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsForwardEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the Backward Href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     */
    public void setBackwardHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsBackwardEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the Backward Href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     */
    public void setBackwardHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsBackwardEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the Timer Href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression The expression to evaluate
     */
    public void setTimerHrefExpression(DataStoreBuffer ds, DataStoreExpression expression) throws Exception {
        try {
            _dsTimerEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
            throw e;
        }
    }
    /**
     * This method sets a datastore expression that will be used to compute the Timer Href for the link.
     * @param ds com.salmonllc.sql.DataStoreBuffer
     * @param expression java.lang.String
     */
    public void setTimerHrefExpression(DataStoreBuffer ds, String expression) throws Exception {
        try {
            _dsTimerEval = new DataStoreEvaluator(ds, expression);
        }
        catch (Exception e) {
            MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
            throw e;
        }
    }
    /**
     * This method gets the DataStoreEvaluator being used for Forward Href expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getForwardHrefExpression() {
        return _dsForwardEval;
    }

    /**
     * This method gets the DataStoreEvaluator being used for Backward Href expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getBackwardHrefExpression() {
        return _dsBackwardEval;
    }

    /**
     * This method gets the DataStoreEvaluator being used for Timer Href expressions.
     * @return DataStoreEvaluator
     * @see DataStoreEvaluator
     */
    public DataStoreEvaluator getTimerHrefExpression() {
        return _dsTimerEval;
    }

    private DataStoreBuffer getDataStoreBuffer(String sDatasource) {
        String dataSource = sDatasource;
        String dsName = null;

        if (dataSource == null)
            return null;

        int pos = dataSource.indexOf(":");
        if (pos == -1)
            dsName = dataSource;
        else {
            dsName = dataSource.substring(0, pos);
        }

        DataStoreBuffer ds = ((JspController)getPage()).getDataSource(dsName);
        if (ds == null)
            return null;

        if (!ds.getAutoBind())
            return null;
        return ds;
    }

    private String getDataStoreExpression(String sDatasource) {
        String dataSource = sDatasource;

        if (dataSource == null)
            return null;

        int pos = dataSource.indexOf(":");
        if (pos == -1)
            return null;
        else
            return dataSource.substring(pos + 1);

    }

    /**
     *Does the binding for the component. This method is called by the framework and should not be called directly
     */
    public void doBinding() throws Exception {
        DataStoreBuffer ds=getDataStoreBuffer(getForwardDatasource());
        String dsExp=getDataStoreExpression(getForwardDatasource());
        if (ds == null)
            return;
        setForwardHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
        ds=getDataStoreBuffer(getBackwardDatasource());
        dsExp=getDataStoreExpression(getBackwardDatasource());
        if (ds == null)
            return;
        setBackwardHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
        ds=getDataStoreBuffer(getTimerDatasource());
        dsExp=getDataStoreExpression(getTimerDatasource());
        if (ds == null)
            return;
        setTimerHrefExpression(ds, ((JspController)getPage()).convertExpressionOperators(dsExp));
    }

    /**
     * This method gets the Forward Href.
     * @return String
     */
    public String getOnEnterForwardHref() {
        return _onenterforward;
    }

    /**
     * This method sets the Forward Href.
     * @return String
     */
    public void setOnEnterForwardHref(String _onenterforward) {
        this._onenterforward = _onenterforward;
    }

    /**
     * This method gets the Backward Href.
     * @return String
     */
    public String getOnEnterBackwardHref() {
        return _onenterbackward;
    }

    /**
     * This method sets the Backward Href.
     * @return String
     */
    public void setOnEnterBackwardHref(String _onenterbackward) {
        this._onenterbackward = _onenterbackward;
    }

    /**
     * This method gets the Timer Href.
     * @return String
     */
    public String getOnTimerHref() {
        return _ontimer;
    }

    /**
     * This method sets the Timer Href.
     * @return String
     */
    public void setOnTimerHref(String _ontimer) {
        this._ontimer = _ontimer;
    }

    /**
     * This method gets the value of newcontext attribute.
     * @return boolean
     */
    public boolean isNewContext() {
        return _newcontext;
    }

    /**
     * This method sets the value of newcontext attribute.
     * @return boolean
     */
    public void setNewContext(boolean _newcontext) {
        this._newcontext = _newcontext;
    }

    /**
     * This method gets the value of ordered attribute.
     * @return boolean
     */
    public boolean isOrdered() {
        return _ordered;
    }

    /**
     * This method sets the value of ordered attribute.
     * @return boolean
     */
    public void setOrdered(boolean _ordered) {
        this._ordered = _ordered;
    }

    /**
     * Creates a new WML Card
     */
    public WmlCard(String name, HtmlPage p) {
        super(name,p);
   }



    /**
     * Returns the title of the card
     */

    public String getTitle() {
        return _title;
    }

    /**
     * Sets the title of the card.
     */

    public void setTitle(String title) {
        _title = title;
    }


    /**
     *Generates the Html for the component. This method is called by the framework and should not be called directly
     */
    public void generateHTML(TagWriter t, String card,int rowNo) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();

        String sRow="";
        if (rowNo>=0)
          sRow="_"+rowNo;

        StringBuffer hrefOEB = null;
           try {
                if (_dsBackwardEval != null) {
                    if (rowNo > -1) {
                        _onenterbackward = _dsBackwardEval.evaluateRowFormat(rowNo);
                    }
                    else {
                        _onenterbackward = _dsBackwardEval.evaluateRowFormat();
                    }
                }
            }
            catch (Exception e) {
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_onenterbackward)) {
                hrefOEB = new StringBuffer(_onenterbackward);
                int hrefLength = hrefOEB.length();
                for (int i = 0; i < hrefLength; i++) {
                    if (hrefOEB.charAt(i) == ' ') {
                        hrefOEB.setCharAt(i, '+');
                    }
                }
            }

        StringBuffer hrefOEF = null;
           try {
                if (_dsForwardEval != null) {
                    if (rowNo > -1) {
                        _onenterforward = _dsForwardEval.evaluateRowFormat(rowNo);
                    }
                    else {
                        _onenterforward = _dsForwardEval.evaluateRowFormat();
                    }
                }
            }
            catch (Exception e) {
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_onenterforward)) {
                hrefOEF = new StringBuffer(_onenterforward);
                int hrefLength = hrefOEF.length();
                for (int i = 0; i < hrefLength; i++) {
                    if (hrefOEF.charAt(i) == ' ') {
                        hrefOEF.setCharAt(i, '+');
                    }
                }
            }

        StringBuffer hrefOT = null;
           try {
                if (_dsTimerEval != null) {
                    if (rowNo > -1) {
                        _ontimer = _dsTimerEval.evaluateRowFormat(rowNo);
                    }
                    else {
                        _ontimer = _dsBackwardEval.evaluateRowFormat();
                    }
                }
            }
            catch (Exception e) {
            }
            // sr 12-08-2000 was getting a null pointer exception
            if (!Util.isNull(_ontimer)) {
                hrefOT = new StringBuffer(_ontimer);
                int hrefLength = hrefOT.length();
                for (int i = 0; i < hrefLength; i++) {
                    if (hrefOT.charAt(i) == ' ') {
                        hrefOT.setCharAt(i, '+');
                    }
                }
            }


        sb.append("<card");
        if(getName()!=null)
            sb.append(" id=\""+getName()+sRow+"\"");
        if (_class!=null)
            sb.append(" class=\""+_class+"\"");
        if (_title!=null)
            sb.append(" title=\""+_title+"\"");
        if (_newcontext)
            sb.append(" newcontext=\""+_newcontext+"\"");
        if (!_ordered)
            sb.append(" ordered=\""+_ordered+"\"");
        if (_onenterbackward!=null)
            sb.append(" onenterbackward=\""+encodeURL(hrefOEB)+"\"");
        if (_onenterforward!=null)
            sb.append(" onenterforward=\""+encodeURL(hrefOEF)+"\"");
        if (_onenterbackward!=null)
            sb.append(" ontimer=\""+encodeURL(hrefOT)+"\"");
        sb.append(">");
        t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        t.print(card, TagWriter.TYPE_CONTENT);
        t.print("</card>", TagWriter.TYPE_END_TAG);
    }



}
