package com.salmonllc.jsp.controller;

/////////////////////////
//$Archive: /sofia/sourcecode/com/salmonllc/jsp/controller/ListFormController.java $
//$Author: Tezgiden $
//$Revision: 40 $
//$Modtime: 9/29/03 1:54p $
/////////////////////////
import java.util.*;
import java.io.File;

import com.salmonllc.forms.ListForm;
import com.salmonllc.forms.ListFormListener;
import com.salmonllc.html.*;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspListForm;
import com.salmonllc.sql.DataStore;
import com.salmonllc.util.MessageLog;
import com.salmonllc.properties.Props;

/**
 * ListFormController is used as a Special Controller to generate standard ListForms.
 * This Controller creates the ListForm and binds all the column cells to the datastore by reading a XML file.
 * This can be extended to do any other job or to modify any of the component specified in ListForm.
 * Creation date: (7/20/01 3:20:08 PM)
 * @author Administrator
 */
public class ListFormController extends BaseListController implements ListFormListener, SubmitListener
{
    public boolean searchButton = false;
    public boolean addButton = false;
    public boolean advanceSearch = false;
    int flags = 0;

    /**
     * Creation date: (7/25/01 11:48:04 AM)
     */
    private int createFlagsForListForm(int flags)
    {
        if(_mainCont.getButtons() == null || _mainCont.getButtons().trim().equals(""))
        {
            return flags;
        }

        java.util.StringTokenizer st = new java.util.StringTokenizer(_mainCont.getButtons(), "|");

        while( st.hasMoreElements()) {

            String token = st.nextToken();

            if(token.equalsIgnoreCase("ADD"))
            {
                addButton = true;
                continue;
            }
            if(token.equalsIgnoreCase("SEARCH"))
            {
                searchButton = true;
                continue;
            }

            if(token.equalsIgnoreCase("ADVANCE_SEARCH") || token.equalsIgnoreCase("ADVANCE") || token.equalsIgnoreCase("ASEARCH"))
            {
                advanceSearch = true;
                continue;
            }

        }

        if(!addButton)
            flags |= ListForm.INIT_NO_ADD_BUTTON;

        if(!searchButton)
            flags |= ListForm.INIT_NO_SEARCH_BUTTON;

        if(advanceSearch)
            flags |= ListForm.INIT_ADVANCED_SEARCH_ON_SIDE;

        return flags;

    }

    /**
     * Recreate the view from the xml definition in the list form and/or URL line params
     */
    public void defineView() throws java.sql.SQLException, com.salmonllc.sql.DataStoreException {

        String datadefinition = _mainCont.getDataDictionary();
        String caption = getParameter("caption");
        String navBarId = getParameter("NavBarId");

        if (getParameter("datadef") != null)
            datadefinition = getParameter("datadef");

        querystring = "datadef=" + datadefinition + "&redirect=" + getPageName() + "?datadef=" + datadefinition;

        if (caption != null)
            querystring += "&caption=" + caption;

        if (navBarId != null)
            querystring += "&NavBarId=" + navBarId;

        // Create the Detail Page URL
        String detailPage = _mainCont.getDetailPageName() + "?" + querystring;

        // XML data path where XML is located
        String xmlPath = getPageProperties().getProperty(Props.XML_DATA_PATH);
        if (xmlPath != null && !xmlPath.endsWith(File.separator))
            xmlPath += File.separator;

        writeMessage("XML Path is " + xmlPath);

        if (xmlPath == null)
            writeMessage("XMLDataPath is missing from properties file");

        // Create the Total rather complete path of XML file adding the directory to datadef(XML file name)
        String _totaldatadefinition = xmlPath + datadefinition;

        _ds = (DataStore)this.getDataSource("dsMain");
        if( _ds == null )
            _ds = new DataStore(getApplicationName());


        // Create the List form with Detail PAge URL - This URL is used when ADD button is clicked
        //_listForm = new ListForm(this, detailPage, _ds, createFlagsForListForm(flags, _mainCont));  This code, was using the current object
	    // as HtmlPage. In the currewnt object(this) loadProperties() was not executed. therefore, the "_useDisabledAttribute" boolean
	    //variable was always "true". that caused all dropdowns to be displayed as "disabled" vies instead of as plain "text".
	    _listForm = new ListForm(_mainCont.getPage(), detailPage, _ds, createFlagsForListForm(flags));

        if (_listForm != null) {
            removePageListener(_listForm);
        }

        FormBinder binder = new FormBinder(_totaldatadefinition);

        // This querystring is used when Link is clicked
        _listForm = binder.bindListForm(_listForm, this, querystring);

        replaceComponent(_mainCont.getName(), _listForm);

	    String sSearchString = getParameter(HtmlLookUpComponent.PARAM_LISTFORM_SEARCH_FILTER_STRING);
	    String sSearchCoulmn = getParameter("searchColumn");


        _linkComponents = new Vector();
        Enumeration enumera = _listForm.getComponents();
        storeListFormLinkComponents(enumera);

	    if(sSearchCoulmn!=null && sSearchCoulmn.length()>0 && sSearchString !=null && sSearchString.length()>0){
		    enumera = _listForm.getComponents();
		    HtmlComponent comp = getComponent(sSearchCoulmn);
		    if(comp instanceof HtmlFormComponent)
			      ((HtmlFormComponent)comp).setValue(sSearchString);
	    }
        //get the data
        _listForm.doRetrieve();
        _ds.gotoFirst();

    }
    private void storeListFormLinkComponents(Enumeration enumera)
    {
        while(enumera.hasMoreElements())
        {
            HtmlComponent comp = (HtmlComponent)(enumera.nextElement());
            if (comp instanceof HtmlLink)
                _linkComponents.add(comp);
            if(comp instanceof HtmlContainer)
                storeListFormLinkComponents(((HtmlContainer)comp).getComponents());
        }
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
            while (e.hasMoreElements())
            {
                HtmlComponent comp = (HtmlComponent) e.nextElement();
                if (comp instanceof JspListForm)
                {
                    _mainCont = (JspListForm) comp;
                    break;
                }
            }

        }
        catch (Exception e)
        {
            MessageLog.writeErrorMessage("initialize", e, this);
        }
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
     *	This method is overridden in the descendant class/page.  It is used each time a user re-visits a page after the initial page generation by the initPage and createPage methods.
     *	@param p PageEvent
     *	@throws Exception
     */

    public void request(PageEvent p) throws Exception
    {
        if(!isReferredByCurrentPage())
            defineView();

        // Set the primary Link after defining the view
        super.request(p);
    }
    /**
     *	This method is overridden in the descendant class/page.  It is used each time a user re-visits a page after the initial page generation by the initPage and createPage methods.
     *	@param p PageEvent
     *	@throws Exception
     */

    public boolean submitPerformed(SubmitEvent p) throws Exception
    {
        return false;
    }
}
