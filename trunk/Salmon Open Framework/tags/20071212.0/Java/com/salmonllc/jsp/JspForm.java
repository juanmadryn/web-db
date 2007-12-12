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
package com.salmonllc.jsp;

import com.salmonllc.html.HtmlApplet;
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;

import com.salmonllc.util.MessageLog;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspForm.java $
//$Author: Srufle $
//$Revision: 14 $
//$Modtime: 6/11/04 2:13p $
/////////////////////////
import java.util.Hashtable;
import java.util.Vector;


/**
 * This container implements an HTML Form Tag
 */
public class JspForm extends JspContainer
{
    Vector          _listeners;
    private String  _action;
    private String  _encType;
    private String  _lastAction;
    private String  _method;
    private String  _onReset;
    private String  _onSubmit;
    private String  _target;
    private boolean _autoScrollEnabled     = true;
    private boolean _lastActionFromForward;
    private int     _rowNo;
    private int     _scrollLeft;
    private int     _scrollTop;

    /**
     * Creates a new JSP Box
     *
     * @param name
     * @param p
     */
    public JspForm(String name, HtmlPage p)
    {
        super(name, p);
    }

    /**
     * Sets the action for this form (The URL invoked when the form is posted)
     *
     * @param action
     */
    public void setAction(String action)
    {
        _action = action;
    }

    /**
     * Returns the action for this form (The URL invoked when the form is posted)
     *
     * @return
     */
    public String getAction()
    {
        return _action;
    }

    /**
     * Allows for the user to set whether they want auto scrolling enabled. defaults to true
     *
     * @param b
     */
    public void setAutoScrollEnabled(boolean b)
    {
        _autoScrollEnabled = b;
    }

    /**
     * Allows the user to find out if auto scrolling is enabled
     *
     * @return boolean
     */
    public boolean isAutoScrollEnabled()
    {
        return _autoScrollEnabled;
    }

    /**
     * Sets the mime type encoding of the data sent "application/x-www-form-urlencoded" (the default), is usually used if the METHOD attribute has the value POST. "multipart/form-data" is used when the form contains a file upload element (INPUT TYPE="FILE").
     *
     * @param encType
     */
    public void setEncType(String encType)
    {
        _encType = encType;
    }

    /**
     * Gets the mime type encoding of the data sent "application/x-www-form-urlencoded" (the default), is usually used if the METHOD attribute has the value POST. "multipart/form-data" is used when the form contains a file upload element (INPUT TYPE="FILE").
     *
     * @return
     */
    public String getEncType()
    {
        return _encType;
    }

    /**
     * Sets the method for this form (GET or POST)
     *
     * @param method
     */
    public void setMethod(String method)
    {
        _method = method;
    }

    /**
     * Returns the method for this form (GET or POST)
     *
     * @return
     */
    public String getMethod()
    {
        return _method;
    }

    /**
     * Sets the javascript code that executes if the user resets the form
     *
     * @param onReset
     */
    public void setOnReset(String onReset)
    {
        _onReset = onReset;
    }

    /**
     * Returns the javascript code that executes if the user resets the form
     *
     * @return
     */
    public String getOnReset()
    {
        return _onReset;
    }

    /**
     * Sets the javascript code that executes if the user submits the form
     *
     * @param onSubmit
     */
    public void setOnSubmit(String onSubmit)
    {
        _onSubmit = onSubmit;
    }

    /**
     * Gets the javascript code that executes if the user submits the form
     *
     * @return
     */
    public String getOnSubmit()
    {
        return _onSubmit;
    }

    /**
     * The form component can track the scroll position of the page when it was submitted for some browsers. This method will return whether or not the position was set for the last submit.
     *
     * @return
     */
    public boolean isScrollPositionSet()
    {
        return (_scrollLeft != -1) || (_scrollTop != -1);
    }

    public String getSubmitScript()
    {
        String ret = "";
        ret += (getFullName() + "onSubmit();");
        ret += ("document.forms['" + getPortletNameSpace() + getName() + "'].submit();");

        return ret;
    }

    /**
     * Sets the window that will display the results of the submit
     *
     * @param target
     */
    public void setTarget(String target)
    {
        _target = target;
    }

    /**
     * Gets the window that will display the results of the submit
     *
     * @return
     */
    public String getTarget()
    {
        return _target;
    }

    /**
     * This method adds a listener the will be notified when this button causes the page to be submitted.
     *
     * @param l The listener to add.
     */
    public void addSubmitListener(SubmitListener l)
    {
        if (_listeners == null)
        {
            _listeners = new Vector();
        }

        for (int i = 0; i < _listeners.size(); i++)
        {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
            {
                return;
            }
        }

        _listeners.addElement(l);
    }

    public boolean executeEvent(int type) throws Exception
    {
        if (type == HtmlComponent.EVENT_OTHER)
        {
            HtmlComponent h = null;

            for (int i = 0; i < getComponentCount(); i++)
            {
                h = getComponent(i);

                if (!h.executeEvent(type))
                {
                    return false;
                }
            }
        }
        else if ((_submit != null) && (type == HtmlComponent.EVENT_SUBMIT))
        {
            if (_listeners != null)
            {
                SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);

                for (int i = 0; i < _listeners.size(); i++)
                {
                    SubmitListener l = (SubmitListener) _listeners.elementAt(i);
                    e.setNextListener((_listeners.size() > (i + 1)) ? _listeners.elementAt(i + 1) : null);

                    if (!l.submitPerformed(e))
                    {
                        return false;
                    }
                }
            }

            boolean retVal = true;

            if (_submit != this)
            {
                retVal = _submit.executeEvent(type);
            }

            _submit = null;

            return retVal;
        }

        return true;
    }

    /**
     * Returns the form component that an HtmlComponent lives in or null if it isn't in a JspForm
     *
     * @param comp
     *
     * @return
     */
    public static JspForm findParentForm(HtmlComponent comp)
    {
        HtmlComponent parent = comp.getParent();

        while (parent != null)
        {
            if (parent instanceof JspForm)
            {
                return (JspForm) parent;
            }

            parent = parent.getParent();
        }

        return null;
    }

    /**
     * Generates the Html for the component. This method is called by the framework and should not be called directly
     *
     * @param t
     * @param content
     *
     * @throws java.io.IOException
     */
    public void generateHTML(TagWriter t, String content)
                      throws java.io.IOException
    {
        StringBuffer sb = new StringBuffer("<FORM ");
        _lastActionFromForward = ((JspController) getPage()).isRequestFromForward();

        if (_action != null)
        {
            sb.append("ACTION=\"");
            sb.append(_action);
            sb.append("\" ");
        }
        else
        {
            sb.append("ACTION=\"");

            if (getPage().isRequestFromPortlet())
            {
                _lastAction = getPage().getPortletInfo().getPortletActionURL();
                sb.append(_lastAction);
            }
            else
            {
                String url = getPage().getCurrentRequest().getRequestURI();
                _lastAction = url;

                String queryString = getPage().getCurrentRequest().getQueryString();

                if (queryString != null)
                {
                    url += ("?" + queryString);
                }

                sb.append(encodeURL(url));
            }

            sb.append("\" ");
        }

        if (_encType != null)
        {
            sb.append("ENCTYPE=\"");
            sb.append(_encType);
            sb.append("\" ");
        }

        if (_method != null)
        {
            sb.append("METHOD=\"");
            sb.append(_method);
            sb.append("\" ");
        }
        else
        {
            sb.append("METHOD=\"POST\" ");
        }

        sb.append("NAME=\"");
        sb.append(getPortletNameSpace() + getName());
        sb.append("\" ");

        if (_target != null)
        {
            sb.append("TARGET=\"");
            sb.append(_target);
            sb.append("\" ");
        }

        sb.append("ONSUBMIT=\"");

        if ((_onSubmit != null) && _onSubmit.startsWith("return"))
        {
            sb.append("return ");
        }

        sb.append(getFullName());
        sb.append("onSubmit();\" ");

        if (_onReset != null)
        {
            sb.append("ONRESET=\"");
            sb.append(_onReset);
            sb.append("\" ");
        }

        sb.setCharAt(sb.length() - 1, '>');

        if (isAutoScrollEnabled())
        {
            sb.append("<INPUT TYPE=\"HIDDEN\" VALUE=\"-1\" NAME=\"FORMSCROLLLEFT");
            sb.append(getFullName());
            sb.append("\">\r\n");
            sb.append("<INPUT TYPE=\"HIDDEN\" VALUE=\"-1\" NAME=\"FORMSCROLLTOP");
            sb.append(getFullName());
            sb.append("\">\r\n");
        }

        sb.append("<SCRIPT language=\"javascript\">\r\n");
        sb.append("function " + getFullName() + "onSubmit() {\r\n");

        if (_onSubmit != null)
        {
            sb.append(_onSubmit);
        }

        String portletNameSpace = getPortletNameSpace();

        if (isAutoScrollEnabled())
        {
            if (browserSupportsScrollOffset())
            {
                sb.append("document.forms['");
                sb.append(portletNameSpace);
                sb.append(getName());
                sb.append("'].FORMSCROLLLEFT");
                sb.append(getFullName());
                sb.append(".value=document.body.scrollLeft;\r\n");
                sb.append("document.forms['");
                sb.append(portletNameSpace);
                sb.append(getName());
                sb.append("'].FORMSCROLLTOP");
                sb.append(getFullName());
                sb.append(".value=document.body.scrollTop;\r\n");
            }
            else
            {
                sb.append("document.forms['");
                sb.append(portletNameSpace);
                sb.append(getName());
                sb.append("'].FORMSCROLLLEFT");
                sb.append(getFullName());
                sb.append(".value=window.pageXOffset;\r\n");
                sb.append("document.forms['");
                sb.append(portletNameSpace);
                sb.append(getName());
                sb.append("'].FORMSCROLLTOP");
                sb.append(getFullName());
                sb.append(".value=window.pageYOffset;\r\n");
            }
        }

        Vector applets = getPage().getApplets();

        if (applets != null)
        {
            for (int i = 0; i < applets.size(); i++)
            {
                HtmlApplet apl = (HtmlApplet) applets.elementAt(i);

                if (isThisTheParent(apl))
                {
                    if (apl.getVisible() && apl.getInteractWithForm())
                    {
                        sb.append("document.forms['");
                        sb.append(portletNameSpace);
                        sb.append(getName());
                        sb.append("'].");
                        sb.append(apl.getFullName());
                        sb.append("HIDDENVALUE.value=document.applets['");
                        sb.append(apl.getFullName());
                        sb.append("'].getValue();\r\n ");

                        //
                        sb.append("document.forms['");
                        sb.append(portletNameSpace);
                        sb.append(getName());
                        sb.append("'].");
                        sb.append(apl.getFullName());
                        sb.append("HIDDENSTATE.value=document.applets['");
                        sb.append(apl.getFullName());
                        sb.append("'].getState();\r\n ");
                    }
                }
            }
        }

        //	sb.append("alert(document.forms['" + getName() + "'].FORMSCROLLTOP" +  getFullName() + ".value);\r\n");
        sb.append("}\r\n");
        sb.append("</SCRIPT>\r\n");

        sb.append("<INPUT TYPE=\"HIDDEN\" VALUE=\"1\" NAME=\"SALMONFORM");
        sb.append(getFullName());
        sb.append("\">");

        t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
        t.print(content, TagWriter.TYPE_CONTENT);
        t.print("</FORM>", TagWriter.TYPE_END_TAG);
    }

    /**
     * This method will process the parms from a post for every component in the container.
     *
     * @param parms
     * @param rowNo
     *
     * @return
     *
     * @throws Exception
     */
    public boolean processParms(Hashtable parms, int rowNo)
                         throws Exception
    {
        Object val = parms.get("SALMONFORM" + getFullName());

        if (val == null)
        {
            return false;
        }

        _scrollTop  = -1;
        _scrollLeft = -1;

        Object scrollTop = parms.get("FORMSCROLLTOP" + getFullName());
        Object scrollLeft = parms.get("FORMSCROLLLEFT" + getFullName());

        try
        {
            if (scrollTop != null)
            {
                _scrollTop = Integer.parseInt(((String[]) scrollTop)[0]);
            }

            if (scrollLeft != null)
            {
                _scrollLeft = Integer.parseInt(((String[]) scrollLeft)[0]);
            }
        }
        catch (Exception ex)
        {
        }

        String compName = null;

        try
        {
            if (!getVisible())
            {
                return false;
            }

            int           compSize = getComponentCount();
            HtmlComponent comp = null;

            for (int i = 0; i < compSize; i++)
            {
                comp = getComponent(i);

                if (comp.processParms(parms, rowNo))
                {
                    _submit = getComponent(i);
                    _rowNo  = rowNo;
                }
            }

            if (_submit == null)
            {
                _submit = this;
                _rowNo  = rowNo;
            }

            return true;
        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("processParms for " + compName + "\n", e, this);
            throw (e);
        }
    }

    /**
     * This method removes a listener from the list that will be notified if this button causes the page to be submitted.
     *
     * @param l The listener to remove.
     */
    public void removeSubmitListener(SubmitListener l)
    {
        if (_listeners == null)
        {
            return;
        }

        for (int i = 0; i < _listeners.size(); i++)
        {
            if (((SubmitListener) _listeners.elementAt(i)) == l)
            {
                _listeners.removeElementAt(i);

                return;
            }
        }
    }

    /**
     * If the scroll position is set, scroll to the same spot on the page as it was when it was submitted
     */
    public void scrollToLastPosition()
    {
        if (!isScrollPositionSet())
        {
            return;
        }

        if ((_scrollLeft != 0) || (_scrollTop != 0))
        {
            if (getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT)
            {
                getPage().writeScript("document.body.scrollTop=" + _scrollTop + ";");
                getPage().writeScript("document.body.scrollLeft=" + _scrollLeft + ";");
            }
            else
            {
                getPage().writeScript("window.scrollTo(" + _scrollLeft + "," + _scrollTop + ");");
            }
        }

        _scrollLeft = -1;
        _scrollTop  = -1;
    }

    String getLastAction()
    {
        return _lastAction;
    }

    boolean isLastActionFromForward()
    {
        return _lastActionFromForward;
    }

    private boolean isThisTheParent(HtmlComponent comp)
    {
        HtmlComponent parent = comp.getParent();

        while (parent != null)
        {
            if (parent == this)
            {
                return true;
            }

            if (parent instanceof JspForm)
            {
                return false;
            }

            parent = parent.getParent();
        }

        return false;
    }

    private boolean browserSupportsScrollOffset()
    {
        int type = getPage().getBrowserType();
        int ver = getPage().getBrowserVersion();

        if (type == HtmlPage.BROWSER_NETSCAPE)
        {
            if (ver >= 7)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return true;
    }
}
