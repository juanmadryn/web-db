package com.salmonllc.jsp.controller;

import com.salmonllc.forms.ListFormListener;
import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlLink;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;
import java.text.SimpleDateFormat;
import java.util.Vector;
/**
 * Base class for all the standard List Controllers.
 */
public class BaseListController extends JspController implements ListFormListener, PageListener
{

	public com.salmonllc.forms.ListForm _listForm;

	protected DataStore _ds;

	protected SimpleDateFormat _dateFormat[];

	public com.salmonllc.jsp.JspListForm _mainCont;
	protected Vector _linkComponents;

	private java.util.Hashtable _hashUserComponents = new java.util.Hashtable();

	public String querystring = "";
/**
 * This method is used if component in List form is needed to be mapped to any other component
 * Creation date: (8/16/01 5:02:34 PM)
 * @return com.salmonllc.html.HtmlComponent
 * @param compName java.lang.String
 */
public HtmlComponent assignComponentToUserComp(String compName) {
	if(getUserComponents() == null)
		return null;
	else
		return (HtmlComponent)getUserComponents().get(compName);
}
/**
 * Insert the method's description here.
 * Creation date: (8/2/01 10:16:09 AM)
 * @return java.lang.String
 * @param ds com.salmonllc.sql.DataStore
 */
protected Vector getPrimaryKey(DataStore _ds) throws com.salmonllc.sql.DataStoreException
{
    int colCount = _ds.getColumnCount();
    Vector s = new Vector();
    for (int i = 0; i < colCount; i++)
	{
        if (!_ds.isPrimaryKey(i))
            continue;
        s.add(_ds.getColumnName(i));
	}

	return s;
}
/**
 * Insert the method's description here.
 * Creation date: (8/2/01 10:16:09 AM)
 * @return java.lang.String
 * @param ds com.salmonllc.sql.DataStore
 */
protected String getPrimaryKeyName(DataStore _ds) throws com.salmonllc.sql.DataStoreException
{
    int colCount = _ds.getColumnCount();
    String s = null;
    for (int i = 0; i < colCount; i++)
	{
        if (!_ds.isPrimaryKey(i))
            continue;
        s = _ds.getColumnName(i);
	}

	if(s != null && s.indexOf(".") != -1)
		s = s.substring(s.indexOf(".") + 1);

	return s;
}
/**
 * Gets all the User Components specified in List Form as a hastable
 * Creation date: (8/17/01 10:55:39 AM)
 * @return java.util.Hashtable
 */
public java.util.Hashtable getUserComponents() {
	return _hashUserComponents;
}
//

public void initialize() throws Exception
{
	super.initialize();
	try
		{

		addPageListener(this);

		// Stupid date format parsing
		Props p = getPageProperties();
		String s;
		Vector v = new Vector();
		if ((s = p.getProperty(Props.DATE_FORMAT)) != null)
			v.addElement(new SimpleDateFormat(s));
		if ((s = p.getProperty(Props.DATETIME_FORMAT)) != null)
			v.addElement(new SimpleDateFormat(s));
		v.addElement(new SimpleDateFormat());
		v.addElement(new SimpleDateFormat("MMM dd, yyyy"));
		v.addElement(new SimpleDateFormat("MMM dd yyyy"));
		v.addElement(new SimpleDateFormat("MMM dd, yyyy hh:mm aaa"));
		_dateFormat = new SimpleDateFormat[v.size()];
		for (int i = 0; i < v.size(); i++)

		_dateFormat[i] = (SimpleDateFormat) v.elementAt(i);


	} catch (Exception e)
		{
		MessageLog.writeErrorMessage("initialize", e, this);
	}
}
/**
 * Called before datastore retrieval.
 * @return boolean	If true, continue with retrieval.
 */
public void pageRequested(PageEvent p) throws Exception
{
	request(p);
}
/**
 * 	This method/event will get fired each time a page is requested by the browser.
 *	@param p PageEvent
 *	@throws Exception
 */
public void pageRequestEnd(PageEvent p) throws Exception
{
    //writeMessage(getClassName() + ".pageRequestEnd()");
	//displayMessage(null);
}
/**
 * 	This method occurs each time a page is sumbitted.
 *	@param p PageEvent
 */
public void pageSubmitEnd(PageEvent p)
{
	//writeMessage(getClassName() + ".pageSubmitEnd()");
}
/**
 *	This method is called after a page has been submitted.  It sets a URL for the user to be re-directed to after the page has been submitted.
 *	@param p PageEvent
 */

public void pageSubmitted(PageEvent p)
{

}
/**
 * Called after the Add button in the List display box has been processed.
 * @return boolean	Undefined at this time.
 */
public boolean postListAdd() {
	return false;
}
/**
 * Called after datastore retrieval.
 *
 * @return boolean	If false, stop further processing of listeners.
 */
public boolean postListRetrieve() throws java.lang.Exception {
	return true;
}
/**
 * Called before processing the Add submit button on the List display box.
 * @return boolean	If true, continue processing.
 */
public boolean preListAdd() {
	return true;
}
/**
 * Called before datastore retrieval.
 * @return boolean	If true, continue with retrieval.
 */
public boolean preListRetrieve() throws Exception
{
	return true;
}
/**
 * Called before datastore retrieval.
 * @return boolean	If true, continue with retrieval.
 */
public void request(PageEvent p) throws Exception
{
	if(_linkComponents != null)
	{
		for(int i=0; i< _linkComponents.size(); i++)
		{
			if(_linkComponents.elementAt(i) != null)
			{
				// get link params
				String mode = getParameter("mode");
				String returnVal = getParameter("returnTo");

				Vector vctPrimaryKeys = getPrimaryKey(_ds);
				String URL;

				if (mode != null && mode.equals("view"))
				{
					URL = "'" + returnVal +"?'";
				}
				else
				{
					URL = "'" + _mainCont.getDetailPageName() + "?mode=update'";
				}

				String connector = "+'";
				for(int j=0; j<vctPrimaryKeys.size(); j++)
				{
					String keyValue = (String)vctPrimaryKeys.elementAt(j);
					String keyValueName = "";
					{ // Get the Key Valye
						int dot = keyValue.indexOf(".");
						if(dot != -1)
						keyValueName = keyValue.substring(dot+ 1);
					}

					if (mode != null && mode.equals("view"))
					{
						// return value to lookup comp
						URL += connector + getParameter("comp") + "='+" + keyValue;
						connector = "+'&";
					}
					else
					{
						// maintain values
						URL += "+'&" + keyValueName + "='+";
						URL += keyValue;
						if(querystring != null && !querystring.equals(""))
						{
							URL += "+'&" + querystring + "'";
						}
					}

                    // get row no if any from parametr list
                    String rowNo = getParameter("rowNo");
                    if(rowNo != null)
                        URL += "+'&rowNo=" + rowNo + "'";

				} // For loop for primary Keys
				((HtmlLink)_linkComponents.get(i)).setHrefExpression(_ds, URL);
			}
		}
	}
}
/**
 * Creation date: (8/17/01 10:55:39 AM)
 * @param newUserComponents java.util.Hashtable
 */
public void setUserComponents(java.util.Hashtable newUserComponents) {
	_hashUserComponents = newUserComponents;
}
/**
 *	This method is used to write a debug message to the Message Log in the descendant class/page.
 *	The message written to the log is the String value passed into this method.
 *	@param message java.lang.String
 */

public void writeMessage(String message) {
	MessageLog.writeDebugMessage("" + message, this);
}

    /**
     * Gets all the User Components specified in Detail Form as a hastable
     * Creation date: (8/16/01 4:24:49 PM)
     * @return java.util.Hashtable
     */
    public HtmlComponent getHtmlComponent(String name) {
        if(name != null)
            return (HtmlComponent)getUserComponents().get(name);
        return null;
    }

}
