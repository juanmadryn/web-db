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
package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/EndPageTag.java $
//$Author: Dan $ 
//$Revision: 28 $ 
//$Modtime: 9/16/04 10:30a $ 
/////////////////////////


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.salmonllc.html.HttpServletResponseWrapper;
import com.salmonllc.jsp.JspController;
import com.salmonllc.util.MessageLog;

/**
 * This tag is used to mark the end of the custom tags in a page
 */

public class EndPageTag extends TagSupport {

	/**
	* This method is part of the JSP Tag Library Specification
	*/
	public int doStartTag() throws JspException {
		TagContext t = (TagContext) pageContext.getRequest().getAttribute(TagContext.TAG_CONTEXT_REQ_KEY);

		int retVal = Tag.EVAL_BODY_INCLUDE;
		try {
			if (t.isInitializing() || t.isPrintingVars() || t.isGenerateCode()) {
				if (t.isPrintingVars() && !t.isInitializing()) {
					t.printVars(pageContext.getOut());
					t.getControllerObject().clearPageFromSession();
				} else if (t.isGenerateCode() && !t.isInitializing()) {
					t.generateCode(pageContext.getOut());
					t.getControllerObject().clearPageFromSession();
				}

				t.setInitializing(false);
				t.getControllerObject().clearInitializing();
			} else {
				HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
				if (req.getMethod().equals("GET") || t.getControllerObject().isRequestFromPortlet()) {
					JspController cont = t.getControllerObject();
					cont.doGet(req, false);
				}
				else if (req.getMethod().equals("POST")) {
					JspController cont = t.getControllerObject();
					HttpServletResponseWrapper res=(HttpServletResponseWrapper) cont.getCurrentResponse();
					if (!cont.getDoPostRedirected() && !res.getRedirectSent())
						cont.doGet(req, false);
				}	

				if (t != null) {
					t.getControllerObject().setDoPostRedirected(false);
					writeScript(t.getControllerObject(),t);
					//	t.reset();
				}
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("doAfterBody()", e, this);
			throw new JspException(e.getMessage());
		}

		return retVal;

	}

	private void writeScript(JspController cont, TagContext context) throws Exception {
		if (!cont.isRequestFromPortlet())
			return;
		JspWriter w = pageContext.getOut();	
		JspController sec = cont.getSecondaryController();
		if (!context.getDreamWeaverMode() && (cont.getCurrentRequest().getMethod().equals("GET") || cont.getDisableRedirect() || cont.isRequestFromForward()) ) {
			cont.writeNamedHtml(w);
			w.println("<SCRIPT>");
			cont.generateScriptHtml(w);
			if (sec != null)
				sec.generateScriptHtml(w);
			cont.writeNamedScripts(w);	
			if (sec != null)
				sec.writeNamedScripts(w);
			w.println("</SCRIPT>");
		}   
		 w.print("</body>");	
	}	
}
