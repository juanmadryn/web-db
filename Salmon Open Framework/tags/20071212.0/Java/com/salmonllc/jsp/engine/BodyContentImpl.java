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

import com.salmonllc.jsp.*;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author  demian
 */
public class BodyContentImpl extends BodyContent {

    private static Stack _unused = new Stack();
    private static BodyContentImpl _first;
    private static BodyContentImpl _last;

    private char[] _cb;
    protected int _bufferSize = 8192;
    private int _nextChar;
    static String _lineSeparator = System.getProperty("line.separator");
    private BodyContentImpl _next;
    private BodyContentImpl _prior;
    private JspWriter _enclosingWriter = null;
    private boolean _noConv = false;
    private boolean _dreamWeaverConv = false;
    private String _prefix;
    private String _tagName;
    private String _origTag;
    private boolean _printContent = true;

    public BodyContentImpl(JspWriter writer) {
        super(writer);
        _enclosingWriter = writer;
        _cb = new char[_bufferSize];
        _printContent = false;
        _nextChar = 0;
        bufferSize = _bufferSize;
    }
    public BodyContentImpl(JspWriter writer, boolean printContent) {
        this(writer);
        _printContent = printContent;
    }
    /**
     * Clear the contents of the buffer. If the buffer has been already
     * been flushed then the clear operation shall throw an IOException
     * to signal the fact that some data has already been irrevocably
     * written to the client response stream.
     *
     * @throws IOException		If an I/O error occurs
     */

    public void clear() throws IOException {
        synchronized (lock) {
            _nextChar = 0;
            _dreamWeaverConv = false;
        }
    }

    public void cleaBody() throws IOException {
        this.clear();
    }
    /**
     * Clears the current contents of the buffer. Unlike clear(), this
     * mehtod will not throw an IOException if the buffer has already been
     * flushed. It merely clears the current content of the buffer and
     * returns.
     *
     * @throws IOException		If an I/O error occurs
     */

    public void clearBuffer() throws IOException {
        this.clear();
    }
    /**
     * This method clears the flag that tells the bodycontent object whether or not to convert to DreamWeaver
     */
    public void clearDreamWeaverConv() {
        _dreamWeaverConv = false;
    }
    /**
     * Close the stream, flushing it first.  Once a stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown.  Closing a previously-closed stream, however, has no effect.
     *
     * @exception  IOException  If an I/O error occurs
     */

    public void close() throws IOException {
        synchronized (lock) {
            _cb = null;
        }
    }
    /**
     * Free's a body content object back to the body content pool
     */
    public static synchronized void freeBodyContent(BodyContentImpl o) {
        BodyContentImpl next = o.getNext();
        BodyContentImpl prior = o.getPrior();

        if (next != null)
            next.setPrior(prior);

        if (prior != null)
            prior.setNext(next);

        if (o == _last)
            _last = prior;

        if (o == _first)
            _first = next;

        o.setNext(null);
        o.setPrior(null);

        _unused.push(o);
    }
    /**
     * Returns a body content object from the pool.
     */
    public static synchronized BodyContentImpl getBodyContent(JspWriter w,boolean printContent)  {
        BodyContentImpl ret = null;

        if (_unused.isEmpty()) {
            ret = new BodyContentImpl(w,printContent);
        } else {
            ret = (BodyContentImpl) _unused.pop();
            ret.setEnclosingWriter(w);
            ret.reset();
            ret.setPrintContent(printContent);
        }

        if (_first == null)
            _first = ret;
        if (_last != null)
            _last.setNext(ret);

        ret.setPrior(_last);
        ret.setNext(null);
        _last = ret;

        return ret;
    }
    /**
     * Returns the parent JSPWriter of this body content
     */
    public JspWriter getEnclosingWriter() {
        return _enclosingWriter;
    }
    public BodyContentImpl getNext() {
        return _next;
    }
    /**
     * Count the number of used and unused body content object in the pool
     */
    public static final int getPoolCount() {
        return getPoolUnusedCount() + getPoolUsedCount();
    }
    /**
     * Count the number of unused body content object in the pool
     */
    public static final int getPoolUnusedCount() {
        return _unused.size();
    }
    /**
     * Count the number of used body content object in the pool
     */
    public static final int getPoolUsedCount() {
        int count = 0;
        if (_first != null) {
            count++;
            BodyContentImpl next = _first.getNext();
            while (next != null) {
                count++;
                next = next.getNext();
            }
        }
        return count;
    }
    public BodyContentImpl getPrior() {
        return _prior;
    }
    /**
     * Return the value of this BodyJspWriter as a Reader.
     * Note: this is after evaluation!!  There are no scriptlets,
     * etc in this stream.
     *
     * @return the value of this BodyJspWriter as a Reader
     */
    public Reader getReader() {
        return new CharArrayReader(_cb, 0, _nextChar);
    }
    /**
     * @return the number of bytes unused in the buffer
     */

    public int getRemaining() {
        return bufferSize - _nextChar;
    }
    /**
     * Return the value of the BodyJspWriter as a String.
     * Note: this is after evaluation!!  There are no scriptlets,
     * etc in this stream.
     *
     * @return the value of the BodyJspWriter as a String
     */
    public String getString() {
        if (_dreamWeaverConv && !_noConv) {
            _noConv = false;
            return TagWriter.dreamWeaverConv(_prefix, _tagName, _origTag, new String(_cb, 0, _nextChar));
        } else {
            _noConv = false;
            return new String(_cb, 0, _nextChar);
        }
    }
    /**
     * Write a line separator.  The line separator string is defined by the
     * system property <tt>line.separator</tt>, and is not necessarily a single
     * newline ('\n') character.
     *
     * @exception  IOException  If an I/O error occurs
     */

    public void newLine() throws IOException {
        synchronized (lock) {
            write(_lineSeparator);
        }
    }
    /**
     * Print an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      s   The array of chars to be printed
     *
     * @throws  NullPointerException  If <code>s</code> is <code>null</code>
     * @throws	   java.io.IOException
     */

    public void print(char s[]) throws IOException {
        write(s);
    }
    /**
     * Print a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      c   The <code>char</code> to be printed
     * @throws	   java.io.IOException
     */

    public void print(char c) throws IOException {
        write(String.valueOf(c));
    }
    /**
     * Print a double-precision floating-point number.  The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      d   The <code>double</code> to be printed
     * @see        java.lang.Double#toString(double)
     * @throws	   java.io.IOException
     */

    public void print(double d) throws IOException {
        write(String.valueOf(d));
    }
    /**
     * Print a floating-point number.  The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      f   The <code>float</code> to be printed
     * @see        java.lang.Float#toString(float)
     * @throws	   java.io.IOException
     */

    public void print(float f) throws IOException {
        write(String.valueOf(f));
    }
    /**
     * Print an integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code> is translated into bytes according
     * to the platform's default character encoding, and these bytes are
     * written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      i   The <code>int</code> to be printed
     * @see        java.lang.Integer#toString(int)
     * @throws	   java.io.IOException
     */

    public void print(int i) throws IOException {
        write(String.valueOf(i));
    }
    /**
     * Print a long integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      l   The <code>long</code> to be printed
     * @see        java.lang.Long#toString(long)
     * @throws	   java.io.IOException
     */

    public void print(long l) throws IOException {
        write(String.valueOf(l));
    }
    /**
     * Print an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      obj   The <code>Object</code> to be printed
     * @see        java.lang.Object#toString()
     * @throws	   java.io.IOException
     */

    public void print(Object obj) throws IOException {
        write(String.valueOf(obj));
    }
    /**
     * Print a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param      s   The <code>String</code> to be printed
     * @throws	   java.io.IOException
     */

    public void print(String s) throws IOException {
        if (s == null) {
            s = "null";
        }
        write(s);
    }
    /**
     * Print a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      b   The <code>boolean</code> to be printed
     * @throws	   java.io.IOException
     */

    public void print(boolean b) throws IOException {
        write(b ? "true" : "false");
    }
    /**
     * Terminate the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * @throws	   java.io.IOException
     */

    public void println() throws IOException {
        newLine();
    }
    /**
     * Print an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(char x[]) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a character and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then <code>{@link
     * #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(char x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a double-precision floating-point number and then terminate the
     * line.  This method behaves as though it invokes <code>{@link
     * #print(double)}</code> and then <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(double x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a floating-point number and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(float x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print an integer and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then <code>{@link
     * #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(int x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a long integer and then terminate the line.  This method behaves
     * as though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(long x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print an Object and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(Object)}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(Object x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a String and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(String x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a boolean value and then terminate the line.  This method behaves
     * as though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     * @throws	   java.io.IOException
     */

    public void println(boolean x) throws IOException {
        synchronized (lock) {
            print(x);
            println();
        }
    }
    /**
     * Print a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param      s   The <code>String</code> to be printed
     * @throws	   java.io.IOException
     */

    public void printNoConv(String s) throws IOException {
        _noConv = true;
        if (s == null) {
            s = "null";
        }
        write(s);
    }
    private void reAllocBuff(int len) {
        char[] tmp = null;

        if (len <= bufferSize) {
            bufferSize *= 2;
        } else {
            bufferSize += len;
        }
        tmp = new char[bufferSize];
        System.arraycopy(_cb, 0, tmp, 0, _cb.length);
        _cb = tmp;
        tmp = null;
    }
    private void reset() {
        _nextChar = 0;
        _noConv = false;
        _dreamWeaverConv = false;
        _prefix = null;
        _tagName = null;
        _origTag = null;
        _printContent = true;
    }
    public void setDreamWeaverConv(String prefix, String tag, String origTag) {
        _dreamWeaverConv = true;
        _prefix = prefix;
        _tagName = tag;
        _origTag = origTag;
    }
    /**
     * Sets the parent JSP Writer for this body content
     */
    public void setEnclosingWriter(JspWriter w) {
        _enclosingWriter = w;
    }
    public void setNext(BodyContentImpl next) {
        _next = next;
    }
    public void setOrigTag(String origTag) {
        _origTag = origTag;
        getString();
    }
    /**
     * Sets whether or not this body content will print it's output or discard it
     */
    public void setPrintContent(boolean printContent) {
        _printContent = printContent;
    }
    public void setPrior(BodyContentImpl prior) {
        _prior = prior;
    }
    /**
     * Returns the string representation of the contents of the class
     */
    public String toString() {
        return getString();
    }
    /**
     * Write an array of characters.  This method cannot be inherited from the
     * Writer class because it must suppress I/O exceptions.
     */
    public void write(char buf[]) throws IOException {
        write(buf, 0, buf.length);
    }
    /**
     * Write a portion of an array of characters.
     *
     * <p> Ordinarily this method stores characters from the given array into
     * this stream's buffer, flushing the buffer to the underlying stream as
     * needed.  If the requested length is at least as large as the buffer,
     * however, then this method will flush the buffer and write the characters
     * directly to the underlying stream.  Thus redundant
     * <code>DiscardableBufferedWriter</code>s will not copy data unnecessarily.
     *
     * @param  cbuf  A character array
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to write
     *
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            if (!_printContent)
                return;

            if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }

            if (len >= bufferSize - _nextChar)
                reAllocBuff(len);

            System.arraycopy(cbuf, off, _cb, _nextChar, len);
            _nextChar += len;
        }
    }
    /**
     * Write a single character.
     *
     */
    public void write(int c) throws IOException {
        synchronized (lock) {
            if (!_printContent)
                return;

            if (_nextChar >= bufferSize) {
                reAllocBuff(0);
            }
            _cb[_nextChar++] = (char) c;
        }
    }
    /**
     * Write a string.  This method cannot be inherited from the Writer class
     * because it must suppress I/O exceptions.
     */
    public void write(String s) throws IOException {
        write(s, 0, s.length());
    }
    /**
     * Write a portion of a String.
     *
     * @param  s     String to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     *
     */
    public void write(String s, int off, int len) throws IOException {
        synchronized (lock) {
            if (!_printContent)
                return;

            if (len >= bufferSize - _nextChar)
                reAllocBuff(len);

            s.getChars(off, off + len, _cb, _nextChar);
            _nextChar += len;
        }
    }
    /**
     * Write the contents of this BodyJspWriter into a Writer.
     * Subclasses are likely to do interesting things with the
     * implementation so some things are extra efficient.
     *
     * @param out The writer into which to place the contents of
     * this body evaluation
     */
    public void writeOut(Writer out) throws IOException {
        if (!_printContent)
            return;

        if (_dreamWeaverConv && !_noConv)
            out.write(TagWriter.dreamWeaverConv(_prefix, _tagName, _origTag, new String(_cb, 0, _nextChar)));
        else
            out.write(_cb, 0, _nextChar);

        _noConv = false;
        // Flush not called as the writer passed could be a BodyContent and
        // it doesn't allow to flush.
    }
}
