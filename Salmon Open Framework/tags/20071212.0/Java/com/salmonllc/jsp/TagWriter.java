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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/jsp/TagWriter.java $
//$Author: Dan $
//$Revision: 15 $
//$Modtime: 10/30/02 2:38p $
/////////////////////////


import java.io.*;
import javax.servlet.jsp.*;

/**
 * This class is used in custom tag component's generateHtml method. For complex tags, the generateHtml method in the correspnding component is responsible for wrapping the output in the appropriate DreamWeaver html MMBeginLock and MMEndLock tags when the JSP is being rendered in Dreamweaver.
 * This class facilitates this by providing methods that will wrap the tags (dreamWeaverConv). It also provides state information that indicates whether or not DreamWeaver tags or regular JSP tags should be rendered at a given time.
 * A JspComponent need only call the print method in this object to write out its generated Html and this object will do the rest.
 */
public class TagWriter {
    public static final int TYPE_BEGIN_TAG = 0;
    public static final int TYPE_END_TAG = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_INSERTED_CONTENT = 3;

    boolean _dreamWeaverConv = false;
    String _prefix;
    String _tagName;
    String _tagStart;
    String _tagEnd;
    JspWriter _writer;

    public TagWriter() {
        super();
        _dreamWeaverConv = false;
    }

    public TagWriter(String prefix, String tagName, String startTag, String endTag) {
        super();
        _prefix = prefix;
        _tagName = tagName;
        _tagStart = startTag;
        _tagEnd = endTag;
        _dreamWeaverConv = true;
    }

    /**
     * This method will wrap an Html Tag in a Dreamweaver tag.
     * @param prefix The tag's prefix
     * @param name The tag's name
     * @param orig The original jsp that described the custom tag
     * @param conv The converted Html to render the content
     */
    public static String dreamWeaverConv(String prefix, String tagName, String orig, String conv) {

        StringBuffer work = new StringBuffer("<MM:BeginLock translatorClass=\"fw\" type=\"");
        work.append(prefix);
        if (prefix != null && !prefix.equals(""))
            work.append(":");
        work.append(tagName);
        work.append("\" orig=\"");
        urlEncode(orig, work);
        work.append("\">");
        work.append(conv);
        work.append("<MM:EndLock>");
        return work.toString();
    }

    /**
     * Returns true if the writer is doing Dreamweaver conversion and false if it is regular Html.
     */
    public boolean getDreamWeaverConv() {
        return _dreamWeaverConv;
    }

    /**
     * This method will print the content of the text exactly as passed
     * @param text java.lang.String The text to output
     */

    public void print(String text) throws IOException {
        _writer.print(text);
    }

    /**
     * This method will insert content into the page based on the type of content and current mode
     * @param text java.lang.String The text to output
     * @param type the type of content to render. Valid Values are <BR>
     *      TYPE_BEGIN_TAG: This content is replacing an opening tag<BR>
     *      TYPE_END_TAG: This content is replacing a closing tag<BR>
     *      TYPE_CONTENT: This content is replaing the content in the tag<BR>
     *      TYPE_INSERTED_CONTENT: This content is entirely new and is not replacing any specific part of the JSP Tag that spawned the request.replaing the content in the tag<BR>
     */
    public void print(String text, int type) throws IOException {
        if (!_dreamWeaverConv || type == TYPE_CONTENT)
            _writer.print(text);
        else if (type == TYPE_INSERTED_CONTENT) {
            _writer.print(dreamWeaverConv(_prefix, _tagName, "", text));
        } else {
            String orig = _tagStart;
            String prefix = _prefix;
            if (type == TYPE_END_TAG) {
                orig = _tagEnd;
                prefix = "/" + prefix;
            }
            _writer.print(dreamWeaverConv(prefix, _tagName, orig, text));
        }
    }

    /**
     * This method will print a Blank Line
     */
    public void println() throws IOException {
        _writer.println();
    }

    /**
     * This method will print the content of the text exactly as passed
     * @param text java.lang.String The text to output
     */
    public void println(String text) throws IOException {
        _writer.println(text);
    }

    /**
     * Sets the JSP Writer that the tag writer will write it's output too
     */
    public void setWriter(JspWriter w) {
        _writer = w;
    }

    /**
     * This method will remove illegal characters for strings and append the results to a string buffer.
     */
    public static void urlEncode(String s, StringBuffer b) {
        if (s == null)
            return;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"')
                b.append("%22");
            else if (c == '<')
                b.append("%3C");
            else if (c == '>')
                b.append("%3E");
            else if (c == '%')
                b.append("%25");
            else
                b.append(c);
        }


    }
}
