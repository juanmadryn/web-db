//package statement
package infraestructura.controllers;


import javax.servlet.http.Cookie;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;

/**
 * LoginPageController: a SOFIA generated controller
 */
public class LoginPageController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163160058732408089L;
	/**
	 * 
	 */

	//Visual Components
	public com.salmonllc.html.HtmlCheckBox _chbxRememberMe;
	public com.salmonllc.html.HtmlPasswordEdit _passwordField;
	public com.salmonllc.html.HtmlSubmitButton _btnSignIn;
	public com.salmonllc.html.HtmlText _passwordPrompt;
	public com.salmonllc.html.HtmlText _text1;
	public com.salmonllc.html.HtmlText _textRemMe;
	public com.salmonllc.html.HtmlText _userPrompt;
	public com.salmonllc.html.HtmlTextEdit _userField;

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		addPageListener(this);
		_btnSignIn.addSubmitListener(this);
	}

	public boolean submitPerformed(SubmitEvent e) throws Exception {
		super.submitPerformed(e);
		
		if(e.getComponent() == _btnSignIn){
			String sUserName = _userField.getValue();
			String sPwd      = _passwordField.getValue();
			String sParamReDir = getParameter(PARAM_REDIRECT);

			if(sUserName==null || sUserName.length()<1 ){
				displayErrorMessage("Por favor ingrese su nombre de usuario.", _userField);
			}

			if(sPwd==null || sPwd.length()<1){
				displayErrorMessage("Por favor ingrese su cláve de seguridad.", _passwordField);
			}

			if(getErrorCount()>0)
				return false;

			WebSiteUser user = new WebSiteUser(getApplicationName(), sUserName, sPwd);
			if(!user.isValid()){
				displayErrorMessage("Usuario o cláve inválida. Por favor intente nuevamente.",_userField);
				return false;
			}

			//Store the user objec to the session.
			getSessionManager().setWebSiteUser(user);

			//Set the visibility of the sign in components
			_txtBannerSignIn.setVisible(false);
			_txtBannerSignOut.setVisible(true);

			setRememberMe(sUserName,sPwd);
			
			// carga nuevamente el menú
			super.populateNavBar();
			
			if(sParamReDir==null || sParamReDir.length()<4)
				gotoSiteMapPage(SiteMapConstants.HOME_PAGE);
			else {
				sendRedirect(sParamReDir);
			}
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

		if(!isReferredByCurrentPage()){
			/* Get the page properties and check if there is a default user name and password.
			Populate the login name and password with the default value.*/
			String sDefaultLoginName = getPageProperties().getProperty(PROPERTY_VAL_DEFAULT_USER_NAME);
			String sDefaultLoginPwd  = getPageProperties().getProperty(PROPERTY_VAL_DEFAULT_USER_PASSWORD);

			_userField.setValue(sDefaultLoginName);
			_passwordField.setValue(sDefaultLoginPwd);

			//Set the "Remember Me" check box value
			checkRememberMe();
		}
	}

	/**
	 * Create 2 cookies to remember the user later on.
	 * @param sEmail
	 */
	private void setRememberMe(String sUserName,String sPwd){
		String sVal = _chbxRememberMe.getValue();

		if(sVal.equals(TRUE)){
			Cookie cookieRemMe = new Cookie(COOKIE_REMEMBER_ME, TRUE);
			cookieRemMe.setMaxAge(COOKIE_MAX_AGE);
			Cookie cookieLoginName = new Cookie(COOKIE_USER_NAME, sUserName);
			cookieLoginName.setMaxAge(COOKIE_MAX_AGE);
			Cookie cookiePwd = new Cookie(COOKIE_USER_PW, sPwd);
			cookiePwd.setMaxAge(COOKIE_MAX_AGE);
			getCurrentResponse().addCookie(cookieLoginName);
			getCurrentResponse().addCookie(cookiePwd);
			getCurrentResponse().addCookie(cookieRemMe);
		}if(sVal.equals(FALSE)){
			Cookie cookieRemMe = getCookie(COOKIE_REMEMBER_ME);
			if(cookieRemMe != null){
				cookieRemMe.setValue(FALSE);
				getCurrentResponse().addCookie(cookieRemMe);
			}
		}
	}

	/**
	 * Sets the value of the "Remember Me" checkbox depending on the values in the cookies
	 */
	private void checkRememberMe(){
		Cookie cookieRemMe = getCookie(COOKIE_REMEMBER_ME);

		if(cookieRemMe !=null){
			String sVal = cookieRemMe.getValue();
			if(sVal !=null && sVal.equals(TRUE)){
				_chbxRememberMe.setValue(TRUE);
				return;
			}
		}
		_chbxRememberMe.setValue(FALSE);
	}

}
