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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlBackLink.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 4/15/03 1:57a $ 
/////////////////////////

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.util.MessageLog;
import java.util.Vector;

/**
 * This type gives you the ability to put a back link on a page and setup the rules for when and where it goes back.
 */
public class HtmlBackLink extends HtmlContainer implements PageListener {
	private HtmlLink _backLink;
	private HtmlText _backLinkTextComp;
	private String _backLinkHref;
	private String _overRideHref;
	private String _refererHref;

	//
	private Vector _backLinkExclusions = new Vector();
	private Vector _validBackLinks = new Vector();
	//
	private boolean STRIP_PARAMS = true;
	private boolean OVERRIDE_REFER = false;
	private boolean CHECK_VALID = false;
	private boolean CHECK_EXCLUSIONS = false;
/**
 * Simple Constructor.
 * @param page 	HtmlPage reference.
 */
public HtmlBackLink(HtmlPage p)
{
	this("FRAME_WORK", p);
}
/**
 * Constructor.
 * @param name Name of back link.
 * @param page 	HtmlPage reference.
 */
public HtmlBackLink(String name, HtmlPage p)
{
	this(name, null, HtmlText.FONT_LINK, null, p);
}
/**
 * This method adds a URL to the list of places you WOULD NOT like to go back to.
 * @param URL java.lang.String
 */
public void addBackLinkExclusionURL(String URL)
{
	String savedHref = "";
	String testStr = URL;

	// Strip query strings and get the name portion.
	int pos = testStr.indexOf('?');
	if (pos > -1)
	{
		testStr = testStr.substring(0, pos);
	}

	//
	pos = testStr.lastIndexOf('/');
	if (pos > -1)
	{
		testStr = testStr.substring(pos + 1);
	}

	// check all the valid back links.
	// if the refer is in the list then you can NOT go back to it
	// so just leave it alone.
	for (int i = 0; i < _validBackLinks.size(); i++)
	{
		savedHref = (String) _validBackLinks.elementAt(i);
		if (savedHref.equalsIgnoreCase(testStr))
		{
			// URL you are trying to add is already in the valid list
			return;
		}
		else
		{
			if (savedHref.indexOf(testStr) > -1)
			{
				// URL you are trying to add is already in the valid list
				return;
			}
		}
	}


	// dont want to add it more than once
	int index = _backLinkExclusions.indexOf(URL);
	if (index == -1)
	{
		_backLinkExclusions.addElement(URL);
	}
}
/**
 * This method adds a URL to the list of places you WOULD like to go back to.
 * @param URL java.lang.String
 */
public void addValidBackLinkURL(String URL)
{
	String savedHref = "";
	String testStr = URL;

	// Strip query strings and get the name portion.
	int pos = testStr.indexOf('?');
	if (pos > -1)
	{
		testStr = testStr.substring(0, pos);
	}

	//
	pos = testStr.lastIndexOf('/');
	if (pos > -1)
	{
		testStr = testStr.substring(pos + 1);
	}

	// check all the excluded back links.
	// if the refer is in the list then you can NOT go back to it
	// so just leave it alone.
	for (int i = 0; i < _backLinkExclusions.size(); i++)
	{
		savedHref = (String) _backLinkExclusions.elementAt(i);
		if (savedHref.equalsIgnoreCase(testStr))
		{
			// URL you are trying to add is already in the exclusion list
			return;
		}
		else
		{
			if (savedHref.indexOf(testStr) > -1)
			{
				// URL you are trying to add is already in the exclusion list
				return;
			}
		}
	}


	// dont want to add it more than once
	int index = _validBackLinks.indexOf(URL);
	if (index == -1)
	{
		_validBackLinks.addElement(URL);
	}
	else
	{
		_validBackLinks.setElementAt(URL, index);
	}
}
public void pageRequested(PageEvent p) throws Exception
{
	if (!getPage().isReferredByCurrentPage())
	{
		setBackLink();
	}
}
public void pageRequestEnd(PageEvent p) throws Exception
{
}
public void pageSubmitEnd(PageEvent p)
{
}
public void pageSubmitted(PageEvent p)
{
}
/**
 * This method will remove a URL from the list of place you WOULD NOT like to go back to.
 * @param URL java.lang.String
 */
public void removeBackLinkExclusionURL(String URL)
{
	if (_backLinkExclusions == null)
	{
		return;
	}
	int index = _backLinkExclusions.indexOf(URL);
	if (index != -1)
	{
		_backLinkExclusions.removeElementAt(index);
	}
}
/**
 * This method removes a URL from the list of places you WOULD like to go back to.
 * @param URL java.lang.String
 */
public void removeValidBackLinkURL(String URL)
{
	if (_validBackLinks == null)
	{
		return;
	}
	int index = _validBackLinks.indexOf(URL);
	if (index != -1)
	{
		_validBackLinks.removeElementAt(index);
	}
}
/**
 * This method figures out from what has been setup what the back link HREF will be.
 */
private void setBackLink()
{
	if (_backLink == null)
		return;

	// Get URL of source page making current request (the "referer").
	_refererHref = getPage().getCurrentRequest().getHeader("referer");
	if (_refererHref == null)
	{
		// This was the first page, so nothing to do.
		return;
	}
	try
	{
		java.net.URL u = new java.net.URL(_refererHref);
		_refererHref = u.getFile();
	}
	catch (java.net.MalformedURLException e)
	{
		// Should never happen.
		MessageLog.writeErrorMessage(e, this);
		return;
	}


	// Strip query strings and get the name portion.
	int pos = -1;
	if (STRIP_PARAMS)
	{
		pos = _refererHref.indexOf('?');
		if (pos > -1)
			_refererHref = _refererHref.substring(0, pos);
	}

	//get just the file portion
	pos = _refererHref.lastIndexOf('/');
	if (pos > -1)
	{
		_refererHref = _refererHref.substring(pos + 1);
	}
	boolean setReferer = true;
	if (OVERRIDE_REFER)
	{
		_backLinkHref = _overRideHref;
	}
	else
	{
		// make referer the backLink
		_backLinkHref = _refererHref;

		// then do the checks to replace. If none on the checks are done it will have the default behavior;
		String savedHref = "";
		if (CHECK_VALID)
		{
			// check all the valid back links.
			// if the refer is in the list then you can got back to it
			setReferer = false;
			for (int i = 0; i < _validBackLinks.size(); i++)
			{
				savedHref = (String) _validBackLinks.elementAt(i);
				if (savedHref.equalsIgnoreCase(_refererHref))
				{
					setReferer = true;
					_backLinkHref = _refererHref;
					break;
				}
				else
				{
					if (savedHref.indexOf(_refererHref) > -1)
					{
						setReferer = true;
						_backLinkHref = savedHref;
						break;
					}
				}
			}
		} // end CHECK_VALID


		if (CHECK_EXCLUSIONS)
		{
			// check all the excluded back links.
			// if the refer is in the list then you can NOT go back to it
			// so just leave it alone.
			for (int i = 0; i < _backLinkExclusions.size(); i++)
			{
				savedHref = (String) _backLinkExclusions.elementAt(i);
				if (savedHref.equalsIgnoreCase(_refererHref))
				{
					setReferer = false;
					break;
				}
				else
				{
					if (savedHref.indexOf(_refererHref) > -1)
					{
						setReferer = false;
						break;
					}
				}
			}
		} // end CHECK_EXCLUSIONS

	}

	//
	if (setReferer)
	{
		_backLink.setHref(_backLinkHref);
	}
}
/**
 * This method will set the back link text for you. This is helpfull when the backlink should say one thing in one mode and another in another mode.
 */
public void setBackLinkText(String text)
{
	_backLinkTextComp.setText(text);
}
/**
 * This method sets whether back link exclusions are checked.
 * default is CHECK_EXCLUSIONS = false
 */
public void setCheckExclusions(boolean toggle)
{
	CHECK_EXCLUSIONS = toggle;
}
/**
 * This method sets whether valid back links are checked.
 * default is CHECK_VALID = false
 */
public void setCheckValid(boolean toggle)
{
	CHECK_VALID = toggle;
}
/**
 * This method lets you override where the back link will go.
 */
public void setOverRideBackLinkHref(String href)
{
	if (href == null)
	{
		// href that was passed was null just return
		return;
	}

	_overRideHref = href;
}
/**
 * This method sets whether the back link should look at the override backlink.
 * default is OVERRIDE_REFER = false
 */
public void setOverRideRefer(boolean toggle)
{
	OVERRIDE_REFER = toggle;
}
/**
 * This method sets whether we should strip parameters when figureing out default back links.
 * default is STRIP_PARAMS = true
 */
public void setStripParams(boolean toggle)
{
	STRIP_PARAMS = toggle;
}

/**
 * This method allows you to get the current back link href. This is helpfull if you have to add parameters to your back link.
 * @return java.lang.String
 */
public String getBackLinkHref() {
	return _backLinkHref;
}

/**
 * This method allows you to get the refering page href.
 * @return java.lang.String
 */
public String getRefererHref() {
	return _refererHref;
}

/**
 * Constructor.
 * @param name Name of back link.
 * @param backLinkText Text to put in link. if null is passed in 'Return' is provided.
 * @param page 	HtmlPage reference.
 */
public HtmlBackLink(String name, String backLinkText, HtmlPage p)
{
	this(name, backLinkText, HtmlText.FONT_LINK, null, p);

}

/**
 * Constructor.
 * @param name Name of back link.
 * @param backLinkText Text to put in link. if null is passed in 'Return' is provided.
 * @param backLinkFont This allows you to provide a link font otherwise the default LinkFont is used.
 * @param backLinkImage HtmlImage reference to be used as a back image.
 * @param page 	HtmlPage reference.
 */
public HtmlBackLink(String name, String backLinkText, String backLinkFont, HtmlImage backLinkImage, HtmlPage p)
{
	super(name, p);
	_backLinkHref = "";
	_backLink = new HtmlLink(name + "_BACK_LINK", _backLinkHref, p);

	// add passed in image for the backlink
	if (backLinkImage != null)
	{
		_backLink.add(backLinkImage);
	}

	//  create text comp to put in link. Using passed in text and LinkFont
	if (backLinkText == null)
	{
		backLinkText = "Return";
	}
	_backLinkTextComp = new HtmlText(backLinkText, backLinkFont, p);
	_backLink.add(_backLinkTextComp);

	//

	p.addPageListener(this);

	//
	add(_backLink);
}

/**
 * Constructor.
 * @param name Name of back link.
 * @param backLinkText Text to put in link. if null is passed in 'Return' is provided.
 * @param backLinkImage HtmlImage reference to be used as a back image.
 * @param page 	HtmlPage reference.
 */
public HtmlBackLink(String name, String backLinkText,  HtmlImage backLinkImage, HtmlPage p)
{
	this(name, backLinkText, HtmlText.FONT_LINK, backLinkImage, p);
	
}
}
