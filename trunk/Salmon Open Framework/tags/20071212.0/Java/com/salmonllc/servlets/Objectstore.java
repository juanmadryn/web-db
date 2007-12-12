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
package com.salmonllc.servlets;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/servlets/Objectstore.java $
//$Author: Dan $
//$Revision: 7 $
//$Modtime: 8/24/04 4:26p $
/////////////////////////


import java.io.*;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.ImageGenerator;
import com.salmonllc.jsp.JspServlet;
import com.salmonllc.properties.Props;
import com.salmonllc.util.*;

/**
 * This Servlet is used to serve up static files or dynamically generated images. The files that will be served are located in the path specified in the system properties file under the key ObjectStorePath.
 */
public class Objectstore extends HttpServlet {
    public String decode(String sFile) {
        if (sFile == null)
            return null;
        int percentindex = sFile.indexOf('%');
        int iLen = sFile.length();
        StringBuffer sbFile = new StringBuffer();
        if (percentindex > 0)
            sbFile.append(sFile.substring(0, percentindex));
        else
            sbFile.append(sFile);
        while (percentindex >= 0) {
            if (percentindex + 3 < iLen) {
                try {
                    char ascii = (char) Integer.parseInt(sFile.substring(percentindex + 1, percentindex + 3), 16);
                    sbFile.append(ascii);
                    int nextPercent = sFile.substring(percentindex + 3).indexOf('%');
                    if (nextPercent < 0) {
                        sbFile.append(sFile.substring(percentindex + 3));
                        break;
                    } else
                        sbFile.append(sFile.substring(percentindex + 3, percentindex + nextPercent + 3));
                    percentindex += nextPercent + 3;
                } catch (Exception e) {
                    sbFile.append('%');
                    int nextPercent = sFile.substring(percentindex + 1).indexOf('%');
                    if (nextPercent < 0) {
                        sbFile.append(sFile.substring(percentindex + 1));
                        break;
                    } else
                        sbFile.append(sFile.substring(percentindex + 1, percentindex + nextPercent + 1));
                    percentindex += nextPercent + 1;
                }
            } else {
                sbFile.append('%');
                int nextPercent = sFile.substring(percentindex + 1).indexOf('%');
                if (nextPercent < 0) {
                    sbFile.append(sFile.substring(percentindex + 1));
                    break;
                } else
                    sbFile.append(sFile.substring(percentindex + 1, percentindex + nextPercent + 1));
                percentindex += nextPercent + 1;
            }
        }

        return sbFile.toString();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
    	JspServlet.setUpApplicationContext(getServletContext(),req);
    	String path = decode(req.getPathInfo());
        MessageLog.writeInfoMessage("doGet(): Path=" + path, this);


        boolean readFromFileSystem = true;

        ServletOutputStream out = res.getOutputStream();

        if (path != null) {
            String filename = path.substring(1);
            String type = "";
            int pos = filename.lastIndexOf(".");
            if (pos > -1)
                type = filename.substring(pos + 1).toLowerCase();

            try {
                Props p = Props.getSystemProps();

                StringBuffer s = new StringBuffer(filename.length());
                for (int i = 0; i < filename.length(); i++) {
                    char c = filename.charAt(i);
                    if (c == '/')
                        s.append(File.separatorChar);
                    else
                        s.append(c);
                }

    
                String contentType;
                if (type.equals("jpg") || type.equals("jpeg"))
                    contentType = "image/jpeg";
                else if (type.equals("gif"))
                    contentType = "image/gif";
                else if (type.equals("htm") || type.equals("html"))
                    contentType = "text/html";
                else if (type.equals("pdf"))
                    contentType = "application/pdf";
                else if (type.equals("doc"))
                    contentType = "application/msword";
                else if (type.equals("xls") || type.equals("xlw"))
                    contentType = "application/vnd.ms-excel";
                else if (type.equals("txt") || type.equals("log") || type.equals("lst") || type.equals("ini"))
                    contentType = "text/plain";
                else if (type.equals("ppt"))
                    contentType = "application/powerpointn";
                else if (type.equals("zip"))
                    contentType = "application/x-gzip";
                else if (type.equals("tif") || type.equals("tiff"))
                    contentType = "image/tif";
                else if (type.equals("dgif")) {
                    contentType = "image/gif";
                    readFromFileSystem = false;
                } else
                    contentType = "application/octet-stream";

                // create a byte array for getting objectstore data
                byte[] b = null;

                // chunck size is currently 8k  we may want to play with tuning this to the filetype or taking
                // the file size divideing it by a factor and if it to big then just default to 64k.
                int chunkSize = (1024 * 8);

                //
                if (readFromFileSystem) {
                    String filePath = p.getProperty(Props.SYS_OBJECTSTORE_PATH) + File.separatorChar + s.toString();
                    String range = req.getHeader("range");
                    res.setHeader("Accept-Ranges", "bytes");
                    File fi = new File(filePath);
                    if (range == null) {
                        FileInputStream f = new FileInputStream(fi);
                        res.setStatus(HttpServletResponse.SC_OK);
                        res.setContentLength((int) fi.length());
                        res.setContentType(contentType);
                        b = new byte[chunkSize];
                        while (true) {
                            if (f.available() < chunkSize) {
                                b = new byte[f.available()];
                                f.read(b);
                                out.write(b);
                                break;
                            } else {
                                f.read(b);
                                out.write(b);
                            }
                        }

                        f.close();
                        out.close();
                    } else {
                        res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                        res.setContentType("multipart/x-byteranges; boundary=multipart_boundary");
                        ByteRangeParser rp = new ByteRangeParser(range, (int) fi.length());
                        RandomAccessFile f = new RandomAccessFile(fi, "r");
                        // For performance reasons we will init variables here
                        String contentRange = null;
                        long start = -1;
                        long end = -1;

                        while (rp.nextRange()) {
                            out.println();
                            out.println("--multipart_boundary");
                            out.println("Content-type: " + contentType);
                            contentRange = "Content-range: bytes " + rp.getFirstByte() + "-" + rp.getLastByte() + "/" + fi.length();
                            out.println(contentRange);
                            out.println();
                            f.seek(rp.getFirstByte());
                            start = rp.getFirstByte();
                            end = rp.getLastByte();
                            b = new byte[chunkSize];
                            while (start <= end) {
                                int len = (int) ((end - start) + 1);
                                if (len > chunkSize)
                                    len = chunkSize;
                                f.read(b, 0, len);
                                out.write(b, 0, len);
                                start += len;
                            }
                        }
                        f.close();
                        out.println();
                        out.println("--multipart_boundary");
                        out.close();
                    }
                } else {
                    b = getImage(req);
                    if (b != null) {
                        res.setStatus(HttpServletResponse.SC_OK);
                        res.setContentType(contentType);
                        res.setContentLength(b.length);
                        out.write(b);
                    }
                    out.close();
                }
            } catch (FileNotFoundException e) {
                MessageLog.writeInfoMessage("***com.salmonllc.servlets.Objectstore, file not found***" + e.getMessage(), this);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.setContentLength(0);
                res.setContentType("application/octet-stream");
                out.close();
            } catch (java.net.SocketException e) {
            } catch (Exception e) {
                MessageLog.writeErrorMessage("Error transferring file to browser", e, this);
            }
        }

    }

    private byte[] genImage(HtmlPage p, String comp, String image) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        p.generateImage(comp, image, bout);
        bout.flush();
        byte b[] = bout.toByteArray();
        bout.close();
        return b;

    }

    private byte[] getImage(HttpServletRequest req) throws Exception {
        String app = null;
        String page = null;
        String comp = null;
        String image = null;

        String path = req.getPathInfo();
        HttpSession sess = req.getSession(false);

        try {
            StringTokenizer t = new StringTokenizer(path, "/");
            app = t.nextToken();
            page = t.nextToken();
            comp = t.nextToken();
            image = t.nextToken();
            int pos = image.indexOf(".");
            image = image.substring(0, pos);
        } catch (Exception e) {
            return null;
        }

        String sesName = "$$$XXX$$$";
        Props p = Props.getProps(app, page);
        boolean useCache = p.getBooleanProperty(Props.SYS_OBJECTSTORE_USE_CACHE, true);

        if (app.equals("JSP")) {
            sesName = "$jsp$" + page;
        } else {
            String cName = p.getProperty(Props.SYS_APP_PACKAGE);
            if (cName == null)
                cName = app + "." + page;
            else
                cName += "." + app + "." + page;
            sesName = "$page$" + cName;
        }

        Object o = sess.getAttribute(sesName);

        if (o != null) {
            if (o instanceof HtmlPage) {
                HtmlPage pg = (HtmlPage) o;
                ImageGenerator gen = pg.getImageGenerator(comp);
                if (gen != null) {
                    if (gen.getCacheKey() == 0)
                        gen.setCacheKey(System.currentTimeMillis());
                    if (useCache && gen.getUseCache() && (ObjectstoreCache.getLastCleared() <= gen.getCacheKey() || gen.getCacheKey() == 0)) {
                        byte[] ret = ObjectstoreCache.getEntry(path);
                        if (ret != null) {
                            return ret;
                        } else {
                            ret = genImage(pg, comp, image);
                            ObjectstoreCache.addEntry(path, ret);
                            return ret;
                        }
                    } else {
                        return genImage(pg, comp, image);
                    }
                }
            }
        }
        return null;
    }
}
