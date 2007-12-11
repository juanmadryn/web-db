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
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/engine/JspWriterImpl.java $
//$Author: Dan $
//$Revision: 18 $
//$Modtime: 6/11/03 4:42p $
/////////////////////////
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;

import com.salmonllc.properties.Props;

class JspWriterImpl extends JspWriter {

    
    protected java.io.PrintWriter _out;
    protected ServletResponse _response;
    protected char _cb[];
    protected int _nextChar;
    protected static int _defaultCharBufferSize = 8192;
    protected boolean _flushed = false;
    static String _lineSeparator = System.getProperty("line.separator");
	private PrintWriter _debugWriter = null;
	private boolean _printContent = true;
    public JspWriterImpl() {
	super( _defaultCharBufferSize, true );
    }
    /**
     * Create a buffered character-output stream that uses a default-sized
     * output buffer.
     *
     * @param  response  A Servlet Response
     */
    public JspWriterImpl(ServletResponse response) {
        this(response, _defaultCharBufferSize, true, true);
    }
/**
 * Create a new buffered character-output stream that uses an output
 * buffer of the given size.
 *
 * @param  response A Servlet Response
 * @param  sz   	Output-buffer size, a positive integer
 *
 * @exception  IllegalArgumentException  If sz is <= 0
 */
public JspWriterImpl(ServletResponse response, int sz, boolean autoFlush, boolean printContent) {
    super(sz, autoFlush);
    if (sz < 0)
        throw new IllegalArgumentException("Buffer size <= 0");
    _response = response;
    _cb = sz == 0 ? null : new char[sz];
    _nextChar = 0;
    _printContent = printContent;
}
    private final void bufferOverflow() throws IOException {
        throw new IOException("Error, Buffer overflow");
    }
/**
 * Discard the output buffer.
 */
public final void clear() throws IOException {
    synchronized (lock) {
	    if (! _printContent)
	    	return;
        if (bufferSize == 0)
            throw new IllegalStateException("Error on clear");
        if (_flushed)
            throw new IOException("Error clearing flushed buffer");
        ensureOpen();
        _nextChar = 0;
    }
}
public void clearBuffer() throws IOException {
    synchronized (lock) {
	    if (! _printContent)
	    	return;
        if (bufferSize == 0)
            throw new IllegalStateException("Error clearing buffer");
        ensureOpen();
        _nextChar = 0;
    }
}
/**
 * Close the stream.
 *
 */
public void close() throws IOException {
    synchronized (lock) {
        if (_response == null)
            return;
        flush();
        if (_out != null)
            _out.close();
        _out = null;
		if (_debugWriter != null)
			_debugWriter.close();
		_debugWriter = null;	
    }
}
/** check to make sure that the stream has not been closed */
protected void ensureOpen() throws IOException {
    if (_response == null)
        throw new IOException("Stream closed");
}
/**
 * Flush the stream.
 *
 */
public void flush() throws IOException {
    synchronized (lock) {
	    if (_printContent) {
	        flushBuffer();
 	       if (_out != null) {
  	          _out.flush();
   	         // Also flush the response buffer.
    	        _response.flushBuffer();
	        }
	    }  
    }
}
/**
 * Flush the output buffer to the underlying character stream, without
 * flushing the stream itself.  This method is non-private only so that it
 * may be invoked by PrintStream.
 */
protected final void flushBuffer() throws IOException {
    synchronized (lock) {
	    if (! _printContent)
	    	return;
        if (bufferSize == 0)
            return;
        _flushed = true;
        ensureOpen();
        if (_nextChar == 0)
            return;
        initOut();
        _out.write(_cb, 0, _nextChar);
        if (_debugWriter != null)
        	_debugWriter.write(_cb,0,_nextChar);
        _nextChar = 0;
    }
}
    /**
     * @return the number of bytes unused in the buffer
     */
    public int getRemaining() {
        return bufferSize - _nextChar;
    }
void init(ServletResponse response, int sz, boolean autoFlush) {
    _response = response;
    if (sz > 0 && (_cb == null || sz > _cb.length))
        _cb = new char[sz];
    _nextChar = 0;
    this.autoFlush = autoFlush;
    this.bufferSize = sz;
}
protected void initOut() throws IOException {
    if (_out == null) {
        _out = _response.getWriter();
        String fileName = Props.getSystemProps().getProperty(Props.JSP_ENGINE_DEBUG_OUTPUT_FILE);
		if (fileName != null)
	        _debugWriter = new PrintWriter(new java.io.FileOutputStream(fileName));
    }
}
    /**
     * Our own little min method, to avoid loading java.lang.Math if we've run
     * out of file descriptors and we're trying to print a stack trace.
     */
    private int min(int a, int b) {
	if (a < b) return a;
	return b;
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
     */
    public void print(String s) throws IOException {
	if (s == null) {
	    s = "null";
	}
	write(s);
    }
    /* Methods that do not terminate lines */

    /**
     * Print a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      b   The <code>boolean</code> to be printed
     */
    public void print(boolean b) throws IOException {
	write(b ? "true" : "false");
    }
    /* Methods that do terminate lines */

    /**
     * Terminate the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     *
     * Need to change this from PrintWriter because the default
     * println() writes  to the sink directly instead of through the
     * write method...  
     */
    public void println() throws IOException {
	newLine();
    }
    /**
     * Print an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and then
     * <code>{@link #println()}</code>.
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
     */
    public void println(boolean x) throws IOException {
	synchronized (lock) {
	    print(x);
	    println();
	}
    }
/** Package-level access
 */
void recycle() {
    _flushed = false;
    _nextChar = 0;
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
	    if (! _printContent)
	    	return;

        ensureOpen();

        if (bufferSize == 0) {
            initOut();
            _out.write(cbuf, off, len);
            if (_debugWriter != null)
            	_debugWriter.write(cbuf,off,len);
            return;
        }

        if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        }
        else if (len == 0) {
            return;
        }

        if (len >= bufferSize) {
            /* If the request length exceeds the size of the output buffer,
               flush the buffer and then write the data directly.  In this
               way buffered streams will cascade harmlessly. */
            if (autoFlush)
                flushBuffer();
            else
                bufferOverflow();
            initOut();
            _out.write(cbuf, off, len);
            if (_debugWriter != null)
            	_debugWriter.write(cbuf,off,len);
            return;
        }

        int b = off, t = off + len;
        while (b < t) {
            int d = min(bufferSize - _nextChar, t - b);
            System.arraycopy(cbuf, b, _cb, _nextChar, d);
            b += d;
            _nextChar += d;
            if (_nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
        }
    }
}
/**
 * Write a single character.
 *
 */
public void write(int c) throws IOException {
    synchronized (lock) {
	    if (! _printContent)
	    	return;	    
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            _out.write(c);
            if (_debugWriter != null)
            	_debugWriter.write(c);
        }
        else {
            if (_nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
            _cb[_nextChar++] = (char) c;
        }
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
  			if (! _printContent)
	     		return;	        
            ensureOpen();
            if (bufferSize == 0) {
                initOut();
                _out.write(s, off, len);
                if (_debugWriter != null)
                	_debugWriter.write(s,off,len);
                return;
            }
            int b = off, t = off + len;
            while (b < t) {
                int d = min(bufferSize - _nextChar, t - b);
                s.getChars(b, b + d, _cb, _nextChar);
                b += d;
                _nextChar += d;
                if (_nextChar >= bufferSize) 
                    if (autoFlush)
                        flushBuffer();
                    else
                        bufferOverflow();
            }
        }
    }
}
