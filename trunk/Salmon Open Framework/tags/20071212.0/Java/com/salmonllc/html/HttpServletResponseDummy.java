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

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * This class is a wrapper used by internal processes in the framework and isn't intended to be used directly. For more information see HttpServletResponse in the JSDK documentation.
 */
 
public class HttpServletResponseDummy extends HttpServletResponseWrapper {
	class DummyOutputStream extends OutputStream {
		public void close() {};
		public void flush() {};
		public void write(byte[] b) {
			write(b,0,b.length);	
			
		};
		public void write(byte[] b, int off, int len) {
			for (int i = off; i < len; i++)
				write(b[i]);	
		};
		public void write(int b) {
			//System.err.print((char) b);
		};
	}
	
/**
 * HttpServletResponseDummy constructor comment.
 * @param res javax.servlet.http.HttpServletResponse
 * @param page com.salmonllc.html.HtmlPageBase
 */
public HttpServletResponseDummy(javax.servlet.http.HttpServletResponse res, HtmlPageBase page) {
	super(res, page);
}
		public java.io.PrintWriter getWriter() throws java.io.IOException {
			return new PrintWriter(new DummyOutputStream());
		}

public void flushBuffer() throws java.io.IOException {
  
}
}
