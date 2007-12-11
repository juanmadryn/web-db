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
package com.salmonllc.jsp.engine;

import com.salmonllc.html.HtmlPageBase;
import com.salmonllc.html.HttpServletResponseDummy;
import com.salmonllc.jsp.*;
import com.salmonllc.jsp.tags.*;
import com.salmonllc.localizer.LanguagePreferences;
import com.salmonllc.properties.*;
import com.salmonllc.sql.*;
import com.salmonllc.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * This class can be used to process a simple JSP page and transform it into the HTML that will be displayed when the page will be run by a JSP Engine. It only implements the Custom Tag portion of the JSP spec. Other JSP tags will be left as they are. The converter has two flavors of output: HTML: convert the page to standard HTML DREAMWEAVER: convert the page to DreamWeaver HTML (custom tags are wrapped in <MMBeginLock> <MMEndLock> tags)
 */

public class JspConverter {
	public static final int CONV_HTML = 0;
	public static final int CONV_DREAMWEAVER = 1;

	private static final int MODE_TEXT = 0;
	private static final int MODE_TAG = 1;
	private static final int MODE_SCRIPT = 2;
	private static final int MODE_COMMENT = 3;

	private static final int TYPE_UNQUOTED = 0;
	private static final int TYPE_QUOTED=1;
	private static final int TYPE_EQUALS=2;
	private static final int TYPE_START_TAG=3;
	private static final int TYPE_END_TAG=4;
	private static final int TYPE_SLASH=5;
	private static final int TYPE_ERROR=6;

	private int _convType;

	private JspDocument _doc;
	private JspElement _bodyTag;
	private JspElement _endBodyTag;
	private JspElement _pageTag;
	private JspElement _endPageTag;
	private boolean _pageTagAdded;
	private String _app = "default";
	private Servlet _servlet;
	private	ServletRequest _req;
	private ServletResponse  _res;
	private HttpSession _sess;

	private Vector _tagLibs = new Vector(2);

	private Vector _tagAttributes = new Vector();
	private StringBuffer _nextTag = new StringBuffer();
	private StringBuffer _nextText = new StringBuffer();
	private StringBuffer _nextTagName = new StringBuffer();
	private StringBuffer _nextTagPrefix = new StringBuffer();

	private boolean _tagNameStarted;
	private boolean _tagNameEnded;
	private boolean _tagPrefixEnded;
	private int _tagNamePos;

	private StringBuffer _nextToken = new StringBuffer();
	private int _nextTokenIndex = 0;
	private char _quote = ' ';

	private int _mode = MODE_TEXT;

	private int _inSize = 0;
	private String _page;
	private int _inPos = 0;
	private InputStream _in;
	private FileOutputStream _debugOut;
	private int _iRet = 0;
	private boolean _handleIncludes=false;
	private String _documentBase=null;

	private Stack _st = new Stack();
	private int _errorCount = 0;

	private static Class[] STRING_PARM = {	new String().getClass()};

	private Hashtable _tagNames = new Hashtable();

	private static Hashtable _tagMap = new Hashtable();
	private static Boolean ok = new Boolean(true);
	private StringBuffer _nestTest = new StringBuffer();

	static {
		_tagMap.put("datatable-*",ok);
		_tagMap.put("*-datatableheader",ok);
		_tagMap.put("*-datatablefooter",ok);
		_tagMap.put("*-datatablegroupheader",ok);
		_tagMap.put("*-datatablegroupfooter",ok);
		_tagMap.put("*-datatablerows",ok);
		_tagMap.put("*-tr",ok);
		_tagMap.put("*-td",ok);
		_tagMap.put("displaybox-*",ok);
		_tagMap.put("*-displayboxheader",ok);
		_tagMap.put("*-displayboxcontents",ok);
		_tagMap.put("*-option",ok);
		_tagMap.put("input-*",ok);
		_tagMap.put("*-navbargroup",ok);
		_tagMap.put("*-navbaritem",ok);
		_tagMap.put("*-navbarsubitem",ok);
		_tagMap.put("navbar-*",ok);

		_tagMap.put("datatable-datatableheader",ok);
		_tagMap.put("datatable-datatablefooter",ok);
		_tagMap.put("datatable-datatablegroupheader",ok);
		_tagMap.put("datatable-datatablegroupfooter",ok);
		_tagMap.put("datatable-datatablerows",ok);

		_tagMap.put("datatableheader-tr",ok);
		_tagMap.put("datatablefooter-tr",ok);
		_tagMap.put("datatablegroupheader-tr",ok);
		_tagMap.put("datatablegroupfooter-tr",ok);
		_tagMap.put("datatablerows-tr",ok);
		_tagMap.put("table-tr",ok);

		_tagMap.put("displaybox-displayboxheader",ok);
		_tagMap.put("displaybox-displayboxcontents",ok);

		_tagMap.put("input-option",ok);

		_tagMap.put("tr-td",ok);

		_tagMap.put("navbar-navbargroup",ok);
		_tagMap.put("navbar-navbaritem",ok);
		_tagMap.put("navbar-navbarsubitem",ok);
	}
	private JspConverter(InputStream in, Servlet serv, ServletRequest req, ServletResponse res, int convType, String docBase) throws IOException {
		_servlet = serv;
		_req = req;
		_res = res;
		_in = in;
		_sess = ((HttpServletRequest)_req).getSession(true);
		_convType = convType;
		if (docBase != null)
			setHandleIncludes(docBase);
		Props p = Props.getSystemProps();
		String fileName = p.getProperty(Props.JSP_ENGINE_DEBUG_INPUT_FILE);
		if (fileName != null)
			_debugOut = new FileOutputStream(fileName);
			
		_inSize = req.getContentLength();
		if (_inSize==-1)
			_inSize=in.available();
		byte[] buffer = new byte[_inSize];
		try {
			int offset=0;
			int len=0;
			while ((_inSize - offset) > 0)
			{
				 len=in.read(buffer,offset,_inSize-offset);
				 if (len <= 0)
					 throw new IOException();
				 offset+=len;
			}

		} catch (IOException e) {
		} 
				
		String lang;
		if (_inSize > 0 && buffer[0] == '*' ) {
			int endPos = 1;
			for (; endPos < _inSize && buffer[endPos] != '*'; endPos++);
			if (endPos > 1) {
				String header = new String(buffer, 1, endPos -1);
				StringTokenizer st = new StringTokenizer(header, ",");
				if (st.hasMoreTokens()) _app = st.nextToken();
				if (st.hasMoreTokens()) {
					lang = st.nextToken();
					
					LanguagePreferences langPref = new LanguagePreferences(lang);
					if(_sess != null)
					   _sess.setAttribute(HtmlPageBase.LANGUAGE_SESSION_KEY, langPref);
			
				}
			}
				try {
					String tmpStr = new String(buffer, endPos +1, _inSize - endPos-1, "UTF-8");
					_page = tmpStr;
				} catch (UnsupportedEncodingException e1) {
					_page = new String(buffer, endPos+1, _inSize- endPos-1);
				}
		} else {
			_page = new String(buffer, 0, _inSize);
		}

		if (_debugOut != null)
			_debugOut.write(buffer);
			
		_inSize = _page.length();

	}
	private int checkTagType(TagSupport handler, int defaultType) {
		if (_convType == CONV_HTML)
			return defaultType;
			
		if (handler instanceof BaseEmptyTag) {
			if (((BaseEmptyTag)handler).getTagConvertType() == BaseEmptyTag.CONV_DONT_CONVERT)
				return JspElement.TAG_HTML;
		}

		if (handler instanceof BaseBodyTag) {
			if (((BaseBodyTag)handler).getTagConvertType() == BaseBodyTag.CONV_DONT_CONVERT)
				return  JspElement.TAG_HTML;
		}

		return defaultType;
	}
	/**
	 * This method will convert the document and send the results to the servlet output stream.
	 * If the properties: JSPEngineDebugOutputFile and JSPEngineDebugInputFile properties are specified
	 * in the system.properties file, the input (from the servlet input stream) and output will
	 * also be written to those files as well.
	 * This method must be called from a doGet, doPost or service method of a servlet.
	 * @param in java.io.InputStream The stream containing the JSP to process
	 * @param serv javax.servlet.Servlet The servlet that is running this method.
	 * @param req javax.servlet.ServletRequest The request for the servlet running this method.
	 * @param res javax.servlet.ServletResponse The response for the servlet running this method.
	 * @param convType int valid values are: CONV_HTML: convert the page to standard HTML<BR> or CONV_DREAMWEAVER: convert the page to DreamWeaver HTML (custom tags are wrapped in <MMBeginLock> <MMEndLock> tags).
	 */
	public static void convertDocument(InputStream in, Servlet serv, ServletRequest req, ServletResponse res, int convType) throws java.lang.Exception {
		JspConverter con = new JspConverter(in,serv,req,res,convType,null);
		con.processDocument();
	}
	
	/**
	 * This method will convert the document and generate instance variables for it.
	 * If the properties: JSPEngineDebugOutputFile and JSPEngineDebugInputFile properties are specified
	 * in the system.properties file, the input (from the servlet input stream) and output will
	 * also be written to those files as well.
	 * This method must be called from a doGet, doPost or service method of a servlet.
	 * @param in java.io.InputStream The stream containing the JSP to process
	 * @param serv javax.servlet.Servlet The servlet that is running this method.
	 * @param req javax.servlet.ServletRequest The request for the servlet running this method.
	 * @param res javax.servlet.ServletResponse The response for the servlet running this method.
	 * @param convType int valid values are: CONV_HTML: convert the page to standard HTML<BR> or CONV_DREAMWEAVER: convert the page to DreamWeaver HTML (custom tags are wrapped in <MMBeginLock> <MMEndLock> tags).
	 */
	public static void getInstanceVariables(InputStream in, Servlet serv, ServletRequest req, ServletResponse res, int convType, String docBase) throws java.lang.Exception {
		HttpServletResponseDummy dummy = new HttpServletResponseDummy((HttpServletResponse)res,null);
		JspConverter con = new JspConverter(in,serv,req,dummy,convType, docBase);

		JspController cont = con.processDocument();
		if (cont != null)
			cont.printVars(new PrintWriter(res.getWriter()));
	}
	private TagSupport getParentTag() throws Exception {
		TagSupport parent = null;
		if (_st.size() > 0)
			parent = ((JspElement) _st.peek()).getHandler();
		return parent;
	}
	private int getTagConvertType(TagSupport handler) {
		if (_convType == CONV_DREAMWEAVER) {
			if (handler instanceof BaseBodyTag)
				return ((BaseBodyTag)handler).getTagConvertType();
			else if (handler instanceof PageTag) {
				if (_pageTagAdded)
					return BaseBodyTag.CONV_NONE;
				else
					return BaseBodyTag.CONV_PAGE_INVISIBLE;
			}
		}
		return BaseBodyTag.CONV_NONE;
	}
	private Hashtable getTags(String prefix)throws Exception{
		for (int i = 0; i < _tagLibs.size(); i++) {
			TwoObjectContainer cont = (TwoObjectContainer) _tagLibs.elementAt(i);
			String tldPrefix = (String) cont.getObject1();
			if (tldPrefix.equals(prefix)) {
				return (Hashtable) cont.getObject2();
			}
		}

		return null;
	}
	/**
	 * This method will remove illegal characters for strings so they can be placed in a url link.
	 */
	private String htmlEncode(String s) {
		if (s == null)
			return null;

		StringBuffer b = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '"')
				b.append("&quot;");
			else if (c == '<')
				b.append("&lt;");
			else if (c == '>')
				b.append("&gt;");
			else
				b.append(c);
		}

		return b.toString();

	}
	private TagSupport initTag(String tagName, String className, Vector attributes, TagAttributeInfo[] info) throws Exception {
		//check for required attributes and make sure the names are unique
		boolean nameChecked = false;
		for (int i = 0; i < info.length; i++) {
			if (info[i].isRequired()) {
				boolean reqOK = false;
				for (int j = 0; j < attributes.size();j++) {
					ThreeObjectContainer cont = (ThreeObjectContainer) attributes.elementAt(j);
					String att = (String) cont.getObject1();
					String val = (String) cont.getObject2();
					if (! nameChecked && att.equals("name")) {
						if (_tagNames.get(val) != null)
							throw new Exception("The name attribute (\"" + val + "\") for the tag \"" + tagName + "\" must be unique in the page. ");
						_tagNames.put(val,val);
						nameChecked = true;
					}
					if (att.equals(info[i].getName())) {
						if (val != null) {
							reqOK = true;
							break;
						}
					}
				}
				if (! reqOK)
					throw new Exception("The attribute \"" + info[i].getName() + "\" for the tag \"" + tagName + "\" cannot be null. ");
			}
		}

		Class c = Class.forName(className);
		TagSupport handler = (TagSupport) c.newInstance();
		String parm[] = new String[1];

		for (int i = 0; i < attributes.size();i++) {
			ThreeObjectContainer cont = (ThreeObjectContainer) attributes.elementAt(i);
			String att = (String) cont.getObject1();
			String val = (String) cont.getObject2();
			String setMethod = (String) cont.getObject3();

			boolean attOK = false;
			for (int j = 0; j < info.length; j++) {
				if (att.equals( info[j].getName() )) {
					attOK = true;
					break;
				}
			}

			if (! attOK)
				throw new Exception ("The attribute \"" + att + "\" for the tag \"" + tagName + "\" is not valid.");

			if (_convType == CONV_DREAMWEAVER && att.equals("visible"))
				val = "true";

			Method m = c.getMethod(setMethod,STRING_PARM);
			parm[0] = val;
			m.invoke(handler,parm);
		}

		return handler;
	}
	private void handleInclude(int lineNo, String tagName) throws IOException {
		String testAtt="page";
		try {
			if (tagName.equals("jsp:include"))
				parseTag(true,lineNo);
			else {
				testAtt="file";	
				parseTag(false,lineNo);
			}		
		} catch (Exception e) {}

		String file=null;
		for (int i = 0; i < _tagAttributes.size();i++) {
			ThreeObjectContainer c = (ThreeObjectContainer) _tagAttributes.elementAt(i);
			String att = (String) c.getObject1();
			String value = (String) c.getObject2();
			if (att.equalsIgnoreCase(testAtt))
				file=_documentBase + File.separatorChar + value;
		}	
			
		FileInputStream in= new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int chunkSize=2048;
		byte[] b = new byte[chunkSize];
		while (true) {
		  if (in.available() < chunkSize) {
			  b = new byte[in.available()];
			  in.read(b);
			  out.write(b);
			  break;
		  } else {
			  in.read(b);
			  out.write(b);
		  }
	   }
 	   in.close();
 	   byte[] content=out.toByteArray();
 	   String contentString="";
	   try {
			contentString = new String(content, "UTF-8");
	   } catch (UnsupportedEncodingException e1) {
		    contentString = _page = new String(content);
	   }
	   String leftSide=_page.substring(0,_inPos);
	   String rightSide=_page.substring(_inPos);
	   _page = leftSide + contentString + rightSide;
	   _inSize+=contentString.length();
	}
		
	private void insertTagLibrary(int lineNo)throws Exception{
		String prefix = "";
		String uri = "";

		try {
			parseTag(false,lineNo);
		} catch (Exception e) {}

		for (int i = 0; i < _tagAttributes.size();i++) {
			ThreeObjectContainer c = (ThreeObjectContainer) _tagAttributes.elementAt(i);
			String att = (String) c.getObject1();
			if (att.equals("prefix"))
				prefix = (String) c.getObject2();
			else if (att.equals("uri"))
				uri = (String) c.getObject2();
		}
		if (uri == null || prefix == null)
			throw (new Exception("Incomplete taglib tag. (Line:" + lineNo + ")"));

		for (int i = 0; i < _tagLibs.size(); i++) {
			TwoObjectContainer cont = (TwoObjectContainer) _tagLibs.elementAt(i);
			String tldPrefix = (String) cont.getObject1();
			if (tldPrefix.equals(prefix))
				return;
		}

		TwoObjectContainer cont = new TwoObjectContainer(prefix,TagLibraryParser.getTags(uri,_app,_servlet.getServletConfig().getServletContext()));
		_tagLibs.add(cont);

	}
	private int nextToken() {
		boolean started = false;
		boolean quoteMode = false;
		boolean scriptMode = false;
		_nextToken.setLength(0);
		int length = _nextTag.length();
		char c = ' ';
		while (_nextTokenIndex < length) {
			c = _nextTag.charAt(_nextTokenIndex);
			if (c == '<' || c == '>' || c == '"' || c == '=' || c == '/') {
				if (started & ! quoteMode)
					return TYPE_UNQUOTED;

				if (c=='<') {
					if (_nextTag.length() > (_nextTokenIndex + 2)) {
						if (_nextTag.charAt(_nextTokenIndex + 1) == '%' && _nextTag.charAt(_nextTokenIndex + 2) == '=') {
							scriptMode = true;
						}
					}
				}

				if (c=='"' && ! scriptMode) {
					_nextTokenIndex ++;
					if (quoteMode)
						return TYPE_QUOTED;
					else {
						started = true;
						quoteMode = true;
						continue;
					}
				}

				if (! quoteMode && ! scriptMode) {
					_nextTokenIndex ++;
					if (c == '<')  {
						_nextToken.append(c);
						return TYPE_START_TAG;
					}
					if (c == '/') {
						_nextToken.append(c);
						return TYPE_SLASH;
					}
					else if (c == '>') {
						_nextToken.append('>');
						return TYPE_END_TAG;
					}
					else if (c=='=') {
						_nextToken.append(c);
						return TYPE_EQUALS;
					}
				}

				if (c == '>' && scriptMode) {
					if (_nextTag.charAt(_nextTokenIndex - 1) == '%')
						scriptMode = false;
				}

			}
			else if (c == ' ' || ((int) c) == 10 || ((int) c) == 13 || ((int) c) == 9) {
				if (started && ! quoteMode)
					return TYPE_UNQUOTED;
			}
			else if (! started) {
				started = true;
			}

			if (started)
				_nextToken.append(c);

			_nextTokenIndex ++;
		}

		return TYPE_ERROR;
	}
	private JspController outputDocument() throws Exception {

		_res.setContentType("text/html; charset=UTF-8");
		_st.setSize(0);
		boolean appendSource = false;
		StringBuffer source = new StringBuffer();

		if (_pageTag == null) {
			PageTag pt = new PageTag();
			pt.setApplication(_app);
			_pageTag = new JspElement("page","","",JspElement.TAG_EMPTY,pt ,null);
			_doc.insert(_pageTag);
			JspElement end = new JspElement("endpage","","",JspElement.TAG_EMPTY,new EndPageTag(),null);
			_doc.append(end);
			_pageTagAdded = true;
		}
		else
			_pageTagAdded = false;

		PageContextImpl context = new PageContextImpl();
		context.initialize(_servlet,_req,_res,null,true,8192,true);
		JspWriter top = context.getOut();
		JspWriter out = context.pushBody();

		PageTag pt = ((PageTag)_pageTag.getHandler());
		if (pt.getApplication() == null || pt.getApplication().equals("none"))
			pt.setApplication(_app);
		pt.setAlwaysCreateNewController(true);
		pt.setPageContext(context);
		pt.setDreamWeaverMode(_convType == CONV_DREAMWEAVER);
		boolean isInit = false;

		JspElement ele = _doc.getFirst();
		while (ele != null) {

			if (appendSource)
				source.append(ele.getText());

			int type = ele.getType();
			if (type == JspElement.TAG_HTML) {

				if (! _st.isEmpty()) {
					JspElement tmp = (JspElement) _st.peek();
					if (tmp.getHandler() instanceof BaseBodyTag) {
						if (((BaseBodyTag) tmp.getHandler()).hasNestedTags()) {
							//if a tag has nested tags, there can't be any text outside a nested tag
							ele = ele.getNext();
							continue;
						}
					}
				}
				((BodyContentImpl) out).printNoConv(ele.getText());
			}
			else if (type == JspElement.TAG_EMPTY) {
				TagSupport handler = ele.getHandler();
				if (! appendSource)  {
					handler.setParent(getParentTag());
					handler.setPageContext(context);
					JspWriter prevOut = out;
					out = context.pushBody();

					if (_convType == CONV_DREAMWEAVER)
						((BodyContentImpl) out).setDreamWeaverConv(ele.getPrefix(),ele.getName(),ele.getText());
					handler.doStartTag();
					handler.doEndTag();
					if (! isInit)
						((BodyContentImpl) out).writeOut(prevOut);
					out.clearBuffer();
					out = context.popBody();
				}
				if (handler instanceof PageTag) {
					isInit = true;
					((BodyContentImpl) out).writeOut(top);
				}
				else if (handler instanceof EndPageTag && isInit) {
					isInit = false;
					ele = _pageTag;
					out.clearBuffer();
					if (_bodyTag == null) {
						JspWriter prevOut = out;
						out = context.pushBody();
						if (_convType == CONV_DREAMWEAVER)
							((BodyContentImpl) out).setDreamWeaverConv(ele.getPrefix(),"HtmlBody","");
						pt.getControllerObject().generateBodyHtml(out);
						((BodyContentImpl) out).writeOut(prevOut);
						out.clearBuffer();
						out = context.popBody();
					}
				}
			}
			else if (type == JspElement.TAG_BODY_START) {
				BodyTagSupport handler = (BodyTagSupport) ele.getHandler();
				handler.setPageContext(context);
				handler.setParent(getParentTag());

				int ret = handler.doStartTag();
				if (ret != Tag.SKIP_BODY) {
					int convType = getTagConvertType(handler);
					if (convType == BaseBodyTag.CONV_PAGE_INVISIBLE)
						out.print(TagWriter.dreamWeaverConv(ele.getPrefix(),ele.getName(),ele.getText(),""));
					else if (convType != BaseBodyTag.CONV_NONE) {
						BaseTagHelper helper = ((BaseBodyTag) handler).getHelper();
						if (convType == BaseBodyTag.CONV_WRAP_ALL_NESTED && ! isInit) {
							appendSource = true;
							source.append(ele.getText());
						}
						else if (convType == BaseBodyTag.CONV_INVISIBLE && ! isInit) {
							((BodyContentImpl) out).setDreamWeaverConv(ele.getPrefix(),ele.getName(),ele.getText());
							TagWriter w = ((BaseBodyTag) handler).getTagWriter();
							w.setWriter(out);
							w.print("",TagWriter.TYPE_BEGIN_TAG);
							((BodyContentImpl) out).clearDreamWeaverConv();
						}
						else if (helper.getComponent() instanceof JspDataTable && isInit) {
							DataStoreBuffer dsb = new DataStoreBuffer();
							dsb.insertRow();
							((JspDataTable) helper.getComponent()).setDataStoreBuffer(dsb);
						}
					}

					_st.push(ele);
					out = context.pushBody();

					if (convType == BaseBodyTag.CONV_WRAP_ALL_NESTED) {
						String name=ele.getName();
						//Special case for input tag, make the tag name the type attribute instead of the tag name
						//for easier parsing later.
						if (ele.getHandler() instanceof InputTag) {
							name = ((InputTag)ele.getHandler()).getType();
							if (name.equals("text"))
								name = "textedit";
							else if (name.equals("image"))
								name= "submitimage";
						}

						((BodyContentImpl) out).setDreamWeaverConv(ele.getPrefix(),name,ele.getText());
					}

					handler.setBodyContent((BodyContent)out);
					handler.doInitBody();
				}
				else {
					handler.doEndTag();
					ele = ele.getClosingTag();
				}
			}
			else if (type == JspElement.TAG_BODY_END) {
				JspElement cur = (JspElement) _st.peek();
				BodyTagSupport handler = (BodyTagSupport) cur.getHandler();
				int convType = getTagConvertType(handler);
				if (convType == BaseBodyTag.CONV_PAGE_INVISIBLE) {
					if (! isInit)
						out.print(TagWriter.dreamWeaverConv("/" + ele.getPrefix(), ele.getName(),ele.getText(),""));
				}
				else if (convType == BaseBodyTag.CONV_WRAP_ALL_NESTED) {
					if (context.getOut() instanceof BodyContentImpl) {
						((BodyContentImpl) context.getOut()).setOrigTag(source.toString());
						source.setLength(0)	;
						appendSource = false;
					}
				}
				int ret = handler.doAfterBody();
				if (ret == BodyTag.EVAL_BODY_TAG)
					ele = cur;
				else {
					_st.pop();
					out = context.popBody();
					if (convType == BaseBodyTag.CONV_INVISIBLE) {
						if (! isInit)
							out.print(TagWriter.dreamWeaverConv("/" + ele.getPrefix(), ele.getName(),ele.getText(),""));
					}

					ret = handler.doEndTag();
					if (ret == BodyTagSupport.SKIP_PAGE) {
						if (pt == null)
							return null;
						else	
							return pt.getControllerObject();
					}	
				}
			}

			ele = ele.getNext();
		}

		((BodyContent) out).writeOut(top);
		out.clearBuffer();
		top.flush();
		top.close();
		context.popBody();
		
		if (pt == null)
			return null;
		else	
			return pt.getControllerObject();
	}
	private void outputErrors() throws Exception {
		PrintWriter out = _res.getWriter();
		int type = 0;

		boolean headingPrinted = false;
		String heading = "<FONT SIZE=\"5\" COLOR=\"RED\">" + _errorCount + " Error" + (_errorCount == 1 ? " " : "s ") + "Found in Document. </FONT><BR>";
		if (_bodyTag == null) {
			if (_convType == CONV_DREAMWEAVER)
				out.println(TagWriter.dreamWeaverConv("","","",heading));
			else
				out.println(heading);
			headingPrinted = true;
		}

		JspElement e = _doc.getFirst();
		while (e != null) {
			type = e.getType();
			if (type == JspElement.TAG_ERROR) {
				String output = htmlEncode(e.getError()) + "<BR>";
				if (_convType == CONV_DREAMWEAVER)
					output = TagWriter.dreamWeaverConv(e.getPrefix(),e.getName(),e.getText(),htmlEncode(e.getError()) + "<BR>");
				out.println(output);
				MessageLog.writeInfoMessage("Error in JSP Translation:" + e.getError(),this);
			}
			else if (type == JspElement.TAG_EMPTY && e.getName().equals("body")) {
				out.print(e.getText());
				if (! headingPrinted) {
					if (_convType == CONV_DREAMWEAVER)
						out.println(TagWriter.dreamWeaverConv("","","",heading));
					else
						out.println(heading);
					headingPrinted = true;
				}
			}
			else
				out.print(e.getText());

			e = e.getNext();
		}
		out.flush();


	}
	private void parseDocument() throws IOException {
		_doc = new JspDocument();
		_errorCount = 0;
		int lineCount = 1;
		char i = readInput();


		//parse the document
		while (i != 0) {
			char c = (char) i;
			if (c == '\n')
				lineCount++;

			if (c == '\"') {
				if (_quote == c)
					_quote = ' ';
				else
					_quote = c;
				if (_mode == MODE_TEXT || _mode == MODE_SCRIPT || _mode == MODE_COMMENT)
					_nextText.append(c);
				else
					_nextTag.append(c);
			}
			else if (c == '<') {
				if (_quote == ' ' && _mode != MODE_SCRIPT && _mode != MODE_COMMENT) {
					if (_mode == MODE_TEXT) {
						if (_nextText.length() > 0)
							processText(_nextText.toString());
					}
					_mode = MODE_TAG;
					_nextTag.setLength(0);
					_nextText.setLength(0);
					_nextTagName.setLength(0);
					_nextTagPrefix.setLength(0);
					_tagNameStarted = false;
					_tagNameEnded = false;
					_tagPrefixEnded = false;
					_tagNamePos = 0;
					_nextTag.append(c);
				}
				else {
					if (_mode == MODE_TEXT || _mode == MODE_SCRIPT || _mode == MODE_COMMENT)
						_nextText.append(c);
					else
						_nextTag.append(c);
				}
			}
			else if (c == '>') {
				if (_mode == MODE_COMMENT) {
					 if (_nextText.toString().endsWith("--")) {
						 _nextText.append(">");
						 processText(_nextText.toString());
						 _nextText.setLength(0);
						 _nextTag.setLength(0);
						 _mode=MODE_TEXT;
					 }
					else
						_nextText.append(">");
				}
				else if (_mode == MODE_SCRIPT) {
					if (_nextText.toString().toLowerCase().trim().endsWith("/script")) {
						_nextText.append(">");
						processText(_nextText.toString());
						_nextText.setLength(0);
						_mode = MODE_TEXT;
					}
					else
						_nextText.append(">");
				}
				else if (_quote == ' ') {
					_mode = MODE_TEXT;
					_nextTag.append(c);
					try {
						String nextTagName = _nextTagName.toString();
						if (nextTagName.equals("MM:BeginLock")) {
							_nextTokenIndex = _tagNamePos + 1;
							int type = nextToken();
							if (type == TYPE_UNQUOTED && _nextToken.toString().toLowerCase().equals("translatorclass")) {
								try {
									parseTag(true, lineCount);
								}
								catch (Exception e) {
								}
								OUTER : for (int j = 0; j < _tagAttributes.size(); j++) {
									ThreeObjectContainer oc = (ThreeObjectContainer) _tagAttributes.elementAt(j);
									String att = (String) oc.getObject1();
									if (att.equals("translatorClass")) {
										String classType = (String) oc.getObject2();
										if (classType.equals("MM_JSPSCRIPT"))
											for (int k = 0; k < _tagAttributes.size(); k++) {
												ThreeObjectContainer oc2 = (ThreeObjectContainer) _tagAttributes.elementAt(k);
												String att2 = (String) oc2.getObject1();
												if (att2.equals("orig")) {
													String origString = java.net.URLDecoder.decode((String) oc2.getObject2());
													if (origString.indexOf("taglib") != -1) {

														int tokenIndex = origString.indexOf("taglib") + 6;
														//Set nexttag and nextTokenIndex settings so nextToken() will parse the lockedOrigTag correctly
														StringBuffer origNextTag = _nextTag;
														_nextTag = new StringBuffer(origString);
														//
														int origTokenIndex = _nextTokenIndex;
														_nextTokenIndex = tokenIndex;
														//
														insertTagLibrary(lineCount);
														//Set nextTag and nextTokenIndex back to its original values
														_nextTokenIndex = origTokenIndex;
														_nextTag = origNextTag;
														break OUTER;
													}
												}
											}
									}
								}

							}

						}
						if (_handleIncludes) {
							if (nextTagName.equals("jsp:include")) 
								handleInclude(lineCount,nextTagName);	
						}	
					
						if (nextTagName.equals("script")) {
							processText(_nextTag.toString());
							_mode = MODE_SCRIPT;
						}
						else if (nextTagName.equals("%@")) {
							_nextTokenIndex = _tagNamePos + 1;
							int type = nextToken();
							String nextTokenSt = _nextToken.toString().toLowerCase(); 
							if (type == TYPE_UNQUOTED && nextTokenSt.equals("include"))
								handleInclude(lineCount,_nextTag.toString());
							else if (type == TYPE_UNQUOTED && _nextToken.toString().toLowerCase().equals("taglib"))
								insertTagLibrary(lineCount);

							if (_convType != CONV_HTML)
								processText(_nextTag.toString());
						}
						else {
							String prefix = _nextTagPrefix.toString();
							Hashtable info = getTags(prefix);
							if (info == null) {
								processText(_nextTag.toString());
								if (nextTagName.equals("body"))
									_bodyTag = _doc.getLast();
							}
							else {
								int type = parseTag(true, lineCount);
								if (type == JspElement.TAG_BODY_START || type == JspElement.TAG_EMPTY)
									nextTagName = nextTagName.substring(prefix.length() + 1);
								else if (type == JspElement.TAG_BODY_END)
									nextTagName = nextTagName.substring(prefix.length() + 2);
								processTag(nextTagName, prefix, _nextTag.toString(), _tagAttributes, type,  info, lineCount);
							}
						}
						_nextTag.setLength(0);
					}
					catch (Exception e) {
						processTagSyntaxError(_nextTagName.toString(), _nextTagPrefix.toString(), _nextTag.toString(), e.getMessage());
					}
				}
				else {
					if (_mode == MODE_TEXT || _mode == MODE_SCRIPT || _mode == MODE_COMMENT)
						_nextText.append(c);
					else
						_nextTag.append(c);
				}
			}
			else if (_mode == MODE_TEXT || _mode == MODE_SCRIPT || _mode == MODE_COMMENT) {
				_nextText.append(c);
			}
			else {
				if (c == '-' && ! _tagNameEnded) {
					if (_nextTag.toString().equals("<!-"))  {
						_nextText.setLength(0);
						_nextText.append("<!--");
						_nextTag.setLength(0);
						_mode = MODE_COMMENT;
					}
				}

				if (c == ' ' || ((int) c) == 10 || ((int) c) == 13 || ((int) c) == 9) {
					if (_tagNameStarted)
						_tagNameEnded = true;
				}
				else
					_tagNameStarted = true;

				if (c == ':')
					_tagPrefixEnded = true;

				if (!_tagNameEnded) {
					_tagNamePos++;
					if (_tagNameStarted) {
						_nextTagName.append(c);
						if (!_tagPrefixEnded && c != '/')
							_nextTagPrefix.append(Character.toLowerCase(c));
					}
				}
				_nextTag.append(c);
			}

			i = readInput();
		}

		if (_nextTag.length() > 0)
			processText(_nextTag.toString());

		//check errors
		StringBuffer error = new StringBuffer();
		if (!_st.isEmpty()) {
			error.append("Error in document. Failed to find closing tags for:");
			while (!_st.isEmpty()) {
				error.append(" -");
				error.append(((JspElement) _st.pop()).getName());
			}
			_errorCount++;
			error.append('\n');
		}
		if (_pageTag != null && _endPageTag == null) {
			error.append("Error in document. Could not find \"endpage\" tag.");
			_errorCount++;
		}
		else if (_endPageTag != null && _pageTag == null) {
			error.append("Error in document. Could not find \"page\" tag.");
			_errorCount++;
		}

		if (error.length() > 0)
			_doc.getLast().setError(error.toString());

		if (_debugOut != null)
			_debugOut.close();
	}
	private int parseTag(boolean resetCounter, int lineNo) throws Exception {
		if (resetCounter)
			_nextTokenIndex = _tagNamePos + 1;

		_tagAttributes.setSize(0);

		if (_nextTagName.charAt(_nextTagName.length() - 1) == '/')
			return JspElement.TAG_EMPTY;

		if (_nextTagName.charAt(0) == '/')
			return JspElement.TAG_BODY_END;


		int tokType = nextToken();
		ThreeObjectContainer nextAtt = new ThreeObjectContainer();
		while (tokType != TYPE_SLASH && tokType != TYPE_END_TAG) {
			if (tokType == TYPE_UNQUOTED) {
				if (nextAtt.getObject1() != null)
					throw(new Exception("Error in <" + _nextTagName + ">: value for \"" + nextAtt.getObject1() + "\" must be in quotes. (Line:" + lineNo + ")"));
				else {
					nextAtt.setObject1(_nextToken.toString());
					_nextToken.setCharAt(0,Character.toUpperCase(_nextToken.charAt(0)));
					_nextToken.insert(0,"set");
					nextAtt.setObject3(_nextToken.toString());
				}
			}
			else if (tokType == TYPE_EQUALS) {
				if (nextAtt.getObject1() == null)
					throw(new Exception("Error in <" + _nextTagName + ">: equal sign found before attribute name (Line:" + lineNo + ")"));
			}
			else if (tokType == TYPE_QUOTED) {
				if (nextAtt.getObject1() == null)
					throw(new Exception("Error in <" + _nextTagName + ">: attribute value:\"" + _nextToken + "\" does not have a corresponding attribute. (Line:" + lineNo + ")"));
				else {
					nextAtt.setObject2(_nextToken.toString());
					_tagAttributes.addElement(nextAtt);
					nextAtt = new ThreeObjectContainer();
				}
			}
			tokType = nextToken();
		}

		if (nextAtt.getObject1() != null && nextAtt.getObject2() == null)
			throw(new Exception("Error in &lt;" + _nextTagName + "&gt;: value for attribute \"" + nextAtt.getObject1() + "\" is missing. (Line:" + lineNo + ")"));

		if (tokType == TYPE_SLASH)
			return JspElement.TAG_EMPTY;
		else
			return JspElement.TAG_BODY_START;

	}
	private JspController processDocument() {
		try {
			parseDocument();
			if (_errorCount > 0)
				outputErrors();
			else
				return outputDocument();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			MessageLog.writeErrorMessage ("processDocument", e, this);
		}
		return null;
	}

	private void processTag(String tagName, String tagPrefix,String text, Vector attributes, int type,  Hashtable tags, int lineNo) {
		if (tagName.charAt(tagName.length() - 1) == '/')
			tagName = tagName.substring(0,tagName.length() - 1);


		TagInfo t = (TagInfo) tags.get(tagName);

		if (t == null) {
			_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,"Error tag \"" + tagName + "\" not found in library. (Line:" + lineNo + ")"));
			_errorCount++;
		}
		else {
			if (t.getBodyContent() == TagInfo.BODY_CONTENT_JSP) {
				if (type == JspElement.TAG_EMPTY) {
					_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,"Error tag \"" + tagName + "\" cannot be empty. (Line: " + lineNo + ")"));
					_errorCount++;
					return;
				}
			}
			else if (t.getBodyContent() == TagInfo.BODY_CONTENT_EMPTY) {
				if (type == JspElement.TAG_BODY_START) {
					_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,"Error tag \"" + tagName + "\" must be empty. (Line: " + lineNo + ")"));
					_errorCount++;
					return;
				}
				else if (type == JspElement.TAG_BODY_END) {
					_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,"Error tag \"" + tagName + "\" is an empty tag and so it cannot have a closing tag. (Line: " + lineNo + ")"));
					_errorCount++;
					return;
				}
			}

			try {
				JspElement ele = null;

				if (type == JspElement.TAG_EMPTY) {
					TagSupport handler = initTag(tagName,t.getTagClassName(),attributes,t.getAttributes());

					ele = new JspElement(tagName,tagPrefix,text,checkTagType(handler,type), handler, null);
					if (tagName.equals("page")) {
						if (_pageTag != null)
							throw new Exception("Error. Only one page tag is allowed per page.");
						((PageTag) handler).setController(null);
						((PageTag) handler).setSecondarycontroller(null);
						_pageTag = ele;
					}
					else if (tagName.equals("endpage")) {
						if (_endPageTag != null)
							throw new Exception("Error. Only one endpage tag is allowed per page.");
						_endPageTag = ele;
					}
					else if (tagName.equals("body")) {
						if (_bodyTag != null)
							throw new Exception("Error. Only one body tag is allowed per page.");
						_bodyTag = ele;
					}
					else if (tagName.equals("endbody")) {
						if (_endBodyTag != null)
							throw new Exception("Error. Only one end body tag is allowed per page.");
						_endBodyTag = ele;
					}
					verifyTagParent(ele);
				}
				else if (type == JspElement.TAG_BODY_START) {
					TagSupport handler = initTag(tagName,t.getTagClassName(),attributes,t.getAttributes());
					ele = new JspElement(tagName,tagPrefix,text,checkTagType(handler,type), handler, null);
					verifyTagParent(ele);
					_st.push(ele);

				}
				else if (type == JspElement.TAG_BODY_END) {
					if (_st.isEmpty())
						throw new Exception("End tag does not have a corresponding begin tag.");
					else {
						JspElement start = (JspElement) _st.peek();
						if (! start.getName().equals(tagName))
							throw new Exception("Improper nesting of tags. Expecting end tag for \"" + start.getName() + "\" and found end tag for \"" + tagName + "\" instead.");
						_st.pop();
						TagSupport handler = start.getHandler();
						String startTag = start.getText();
						String endTag = text;
						ele = new JspElement(tagName,tagPrefix,text,checkTagType(handler,type), handler, null);
						start.setClosingTag(ele);
						TagWriter writer = (_convType == CONV_DREAMWEAVER ? new TagWriter(tagPrefix,tagName,startTag,endTag) : new TagWriter());
						if (handler instanceof BaseBodyTag)
							((BaseBodyTag) handler).setTagWriter(writer);
					}
				}

				_doc.append(ele);

			}
			catch (Exception ex) {
				_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,"Error processing tag \"" + tagName + "\": " + ex.toString() + "," + ex.getMessage() + " (Line:" + lineNo + ")"));
				_errorCount ++;
			}
		}
	}
	private void processTagSyntaxError(String tagName, String tagPrefix, String text, String error) {
		_errorCount ++;
		_doc.append(new JspElement(tagName,tagPrefix,text,JspElement.TAG_ERROR,null,error));
	}
	private void processText(String text) {
		JspElement ele = new JspElement("html",null,text,JspElement.TAG_HTML,null,null);
		_doc.append(ele);
	}
	private char readInput() throws IOException {
		if (_inPos >= _inSize) 
			return 0;
		else
			return _page.charAt(_inPos++);
	}	
	private void verifyTagParent(JspElement child) throws Exception {
		_nestTest.setLength(0);
		_nestTest.append("*-");
		_nestTest.append(child.getName());
		if (_tagMap.get(_nestTest.toString()) != null) {
			if (_st.isEmpty())
				throw new Exception("Error. The tag \"" + child.getName() + "\" must be inside another tag.");
			else {
				JspElement parent = (JspElement) _st.peek();
				_nestTest.setLength(0);
				_nestTest.append(parent.getName());
				_nestTest.append("-");
				_nestTest.append(child.getName());
				if (! _tagMap.containsKey(_nestTest.toString()))
					throw new Exception("Error. The tag \"" + child.getName() + "\" cannot be nested in the tag \"" + parent.getName() + "\"");
			}
		}

		if (! _st.isEmpty()) {
			JspElement parent = (JspElement) _st.peek();
			_nestTest.setLength(0);
			_nestTest.append(parent.getName());
			_nestTest.append("-*");
			if (_tagMap.get(_nestTest.toString()) != null) {
				_nestTest.setLength(_nestTest.length() - 1);
				_nestTest.append(child.getName());
				if (! _tagMap.containsKey(_nestTest.toString()))
					throw new Exception("Error. The tag \"" + child.getName() + "\" cannot be nested in the tag \"" + parent.getName() + "\"");
			}


		}

	}
	
	private void setHandleIncludes(String docBase) {
		_handleIncludes=true;
		_documentBase=docBase;	
		
	}	
	
}
