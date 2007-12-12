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
// ====================================================================
//
// The Apache Software License, Version 1.1
//
// Copyright (c) 1999 The Apache Software Foundation.  All rights
// reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in
//    the documentation and/or other materials provided with the
//    distribution.
//
// 3. The end-user documentation included with the redistribution, if
//    any, must include the following acknowlegement:
//       "This product includes software developed by the
//        Apache Software Foundation (http://www.apache.org/)."
//    Alternately, this acknowlegement may appear in the software itself,
//    if and wherever such third-party acknowlegements normally appear.
//
// 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
//    Foundation" must not be used to endorse or promote products derived
//    from this software without prior written permission. For written
//    permission, please contact apache@apache.org.
//
// 5. Products derived from this software may not be called "Apache"
//    nor may "Apache" appear in their names without prior written
//    permission of the Apache Group.
//
//  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
//  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
//  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
//  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
//  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
//  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
//  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
//  SUCH DAMAGE.
//  ====================================================================
//
//  This software consists of voluntary contributions made by many
//  individuals on behalf of the Apache Software Foundation.  For more
//  information on the Apache Software Foundation, please see
//  <http://www.apache.org/>.

package com.salmonllc.jsp.engine;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/engine/JspFactoryImpl.java $
//$Author: Dan $
//$Revision: 15 $
//$Modtime: 9/22/04 10:34a $
/////////////////////////
import javax.servlet.jsp.*;
import javax.servlet.http.*;

import com.salmonllc.html.HttpServletResponseDummy;
import com.salmonllc.jsp.*;
import com.salmonllc.jsp.tags.PageTag;

/**
 * This class is an implementation of the J2EE JSP factory.
 * It is used by the JSPServlet to facilitate multiple passes
 * through the _jspservice method when a salmon JSP is initialized
 */
public class JspFactoryImpl extends JspFactory {
    JspFactory _realFactory = null;

    /**
     * Creates a new JSPFactory.
     * @param realFactory The real factory used by the J2EE Engine
     */
    public JspFactoryImpl(JspFactory realFactory) {
        super();
        _realFactory = realFactory;

    }
    /**
     * Returns information on the J2EE Engine
     */
    public javax.servlet.jsp.JspEngineInfo getEngineInfo() {
        return _realFactory.getEngineInfo();
    }
    /**
     * Creates a new page context
     */
    public javax.servlet.jsp.PageContext getPageContext(javax.servlet.Servlet arg1, javax.servlet.ServletRequest arg2, javax.servlet.ServletResponse arg3, String arg4, boolean arg5, int arg6, boolean arg7) {
        boolean isInitializing = true;
        boolean isContextSalmon = (arg1 instanceof com.salmonllc.jsp.JspServlet);
        HttpServletRequest req = (HttpServletRequest) arg2;

        String sessID=req.getParameter(PageTag.getSessionIdentifier());
        HttpSession sess=PageTag.getSession(sessID);
        if (sess==null)
          sess = req.getSession(false);
        JspController cont=null;
        if (sess != null) {
            cont = (JspController) sess.getAttribute("$jsp$" + com.salmonllc.jsp.tags.PageTag.generateSessionName(req));
            if (cont != null) {
                isInitializing = cont.isInitializing();
                if (cont.getPageContext() != null) 
                    isContextSalmon = (cont.getPageContext().getResponse() instanceof HttpServletResponseDummy);
            }
        }

        if (isInitializing && isContextSalmon) 
        	return _realFactory.getPageContext(arg1, arg2, new HttpServletResponseDummy((HttpServletResponse)arg3,cont), arg4, arg5, arg6, arg7);
        else
        	return _realFactory.getPageContext(arg1, arg2, arg3, arg4, arg5, arg6, arg7);

    }

    /**
     * Frees the resources used by a page context object
     */
    public void releasePageContext(javax.servlet.jsp.PageContext arg1) {
        if (!(arg1 instanceof com.salmonllc.jsp.engine.PageContextImpl))
            _realFactory.releasePageContext(arg1);
        else
            ((com.salmonllc.jsp.engine.PageContextImpl) arg1).release();
    }
    /**
     * Resets the factory to the real one for the J2EE engine
     */
    public void resetFactory() {
        JspFactory.setDefaultFactory(_realFactory);
    }
}
