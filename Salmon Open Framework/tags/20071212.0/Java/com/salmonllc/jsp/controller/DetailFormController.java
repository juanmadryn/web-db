package com.salmonllc.jsp.controller;

import com.salmonllc.forms.DetailForm;
import com.salmonllc.forms.DetailFormListener;
import com.salmonllc.html.*;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspDetailForm;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;
import java.io.File;
import java.util.*;

/**
 * DetailFormController creates a detailform and binds its components to the datastore columns specified by the XML file. Creation date: (7/25/01 3:19:11 PM)
 * @author  : Deepak Agarwal
 */
public class DetailFormController extends BaseDetailController implements SubmitListener, DetailFormListener
{
	public com.salmonllc.jsp.JspDetailForm _mainCont;
	private int flags = 0;

	private String _redirectURL = "";
/**
 * Creation date: (7/25/01 11:48:04 AM)
 * @param form com.salmonllc.jsp.JspListForm
 */
private int createFlags()
{

	if(_mainCont.getDeleteButton() == null || _mainCont.getDeleteButton().equals(""))
		flags |= DetailForm.INIT_NO_DELETE_BUTTON;

	if(_mainCont.getSaveButton() == null || _mainCont.getSaveButton().equals(""))
		flags |= DetailForm.INIT_NO_SAVE_BUTTON;

	if(_mainCont.getCancelButton() == null || _mainCont.getCancelButton().equals(""))
		flags |= DetailForm.INIT_NO_CANCEL_BUTTON;
	return flags;
}
/**
 * Creation date: (7/25/01 3:16:15 PM)
 */
private void defineView() throws java.sql.SQLException, com.salmonllc.sql.DataStoreException {

    String datadefinition = _mainCont.getDataDictionary();

    String caption = _mainCont.getCaption();
    if (getParameter("caption") != null)
        caption = getParameter("caption");

    if (getParameter("datadef") != null)
        datadefinition = getParameter("datadef");

    if (getParameter("redirect") != null)
        _redirectURL = getParameter("redirect");

    if (getParameter("NavBarId") != null)
        _redirectURL += "&NavBarId=" + getParameter("NavBarId");

    if (caption != null)
        _redirectURL += "&caption=" + caption;

    String xmlPath = getPageProperties().getProperty(Props.XML_DATA_PATH);
    if (xmlPath != null && !xmlPath.endsWith(File.separator))
        xmlPath += File.separator;

    System.out.println(" Detail Form XML Path  :" + xmlPath);
    datadefinition = xmlPath + datadefinition;

    _ds = (DataStore)this.getDataSource("dsMain");
    if( _ds == null )
        _ds = new DataStore(getApplicationName());

    _ds.setUseBindForUpdate(true);

    if (_detailForm != null) {
        removePageListener(_detailForm);
    }

    _detailForm = new DetailForm(this, _ds, createFlags());

    _detailForm.setHeadingCaption(caption);

    FormBinder binder = new FormBinder(datadefinition);
    _detailForm = binder.bindDetailForm(_detailForm, this);

    _detailForm.addListener(this);

    replaceComponent(_mainCont.getName(), _detailForm);

}
/**
 * This method was created in VisualAge.
 */
public void initialize() throws Exception
{
	try
	{
		super.initialize();
		addPageListener(this);

		// Assigning to main cont
	    Enumeration e = getComponentTable().elements();
	    while (e.hasMoreElements()) {
            HtmlComponent comp = (HtmlComponent) e.nextElement();
            if (comp instanceof JspDetailForm) {
                _mainCont = (JspDetailForm) comp;
                break;
            }
 	   }

	} catch (Exception e)
		{
		 MessageLog.writeErrorMessage("initialize", e, this);
	}
}
/**
 * Creation date: (8/30/01 2:00:07 PM)
 * @return java.lang.String
 * @param dsDataType int
 */
protected String mapDataType(int dsDataType) {

	switch(dsDataType)
	{
		case 0: return "String";
		case 1: return "Integer";
		case 2: return "Datetime";
		case 3: return "Double";
		case 4: return "ByteArray";
		case 5: return "Short";
		case 6: return "Long";
		case 7: return "Float";
		case 8: return "Date";
		case 9: return "Time";
		case 99: return "String";
	}

	return null;
}
/**
 * 	This method MUST be OVERRIDDEN in descendant classes to prohibit or restrict
 * 	access to a page or some of its components.
 * 	@return boolean
 */
public boolean pageAccess() {
	return true;
}
/**
 * Called after Save button is processed on Detail page.
 * @return boolean	True if continue to save, else do not continue.
 */
public boolean postDetailSave() throws java.lang.Exception
{
	if(_redirectURL	 != null)
		this.getCurrentResponse().sendRedirect(_redirectURL);
	return true;
}
/**
 * Called after Save button is processed on Detail page.
 * @return boolean	True if continue to save, else do not continue.
 */
public boolean preDetailDelete() throws java.lang.Exception
{
	if(_redirectURL	 != null)
		this.getCurrentResponse().sendRedirect(_redirectURL);
	return true;
}
/**
 *	This method is overridden in the descendant class/page.  It is used each time a user re-visits a page after the initial page generation by the initPage and createPage methods.
 *	@param p PageEvent
 *	@throws Exception
 */

public void request(PageEvent p) throws Exception
{
	super.request(p);
	if(! isReferredByCurrentPage())
	{
		defineView();
	}
}
/**
 *	This method is overridden in the descendant class/page.  It is used each time a user re-visits a page after the initial page generation by the initPage and createPage methods.
 *	@param p PageEvent
 *	@throws Exception
 */

public boolean submitPerformed(SubmitEvent p) throws Exception
{
	return true;
}

public String getRedirectURL(){
	return _redirectURL;
}
}
