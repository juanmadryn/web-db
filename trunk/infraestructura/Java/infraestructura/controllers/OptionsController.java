//package statement
package infraestructura.controllers;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com


//Salmon import statements
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;

public class OptionsController extends BaseController  {

/**
	 * 
	 */
	private static final long serialVersionUID = 925313765776525196L;
	//Visual Components
	public com.salmonllc.html.HtmlRadioButtonGroup _radioButtonGrp;
	public com.salmonllc.html.HtmlSubmitButton _btnSubmit;

	public GuiOptions _guiOpts = null;

	/**
	 * Initilize the page
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();
		_btnSubmit.addSubmitListener(this);
	}

	/**
	 * Handle submit event
	 * @param e  SubmitEvent
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		super.submitPerformed(e);

		if(e.getComponent()==_btnSubmit){
			String sVal = _radioButtonGrp.getValue();
			//Set the default value
			int iNavOrient = GuiOptions.NAVBAR_VERTICAL;

			if(sVal !=null)
				iNavOrient = Integer.parseInt(sVal);

			//Set the orientation
			_guiOpts.setNavBarOrientation(iNavOrient);

			/*Store the new GUIOption to the session. GUIOption object will be cehecked in
			the BaseController and sett the Navbar orientation accordingly*/
			getSessionManager().putValue(SESSION_VALUE_GUI_OPTIONS, _guiOpts);
			gotoSiteMapPage(SiteMapConstants.HOME_PAGE);
		}
		return true;
	}

	/**
	 *
	 * @param p PageEvent
	 * @throws Exception
	 */
	public void pageRequested(PageEvent p) throws Exception {
		super.pageRequested(p);
		if (!isReferredByCurrentPage()){
			// Get the object from the session
			Object o = getSessionManager().getValue(SESSION_VALUE_GUI_OPTIONS);

			if(o==null){
				_guiOpts = new GuiOptions();
				getSessionManager().putValue(SESSION_VALUE_GUI_OPTIONS, _guiOpts);
			}else
				_guiOpts = ((GuiOptions)o);

			//Get the current value...
			int iNavBarOption = _guiOpts.getNavBarOrientation();
			//Set the value at the radio button group.
			_radioButtonGrp.setValue(""+iNavBarOption);
		}
	}
}
