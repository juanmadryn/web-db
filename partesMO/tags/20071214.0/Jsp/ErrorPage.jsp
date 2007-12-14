<%@ page isErrorPage="true" %>
<%@ page import="java.util.*, java.io.*, java.text.*, com.salmonllc.properties.*" %>
<html><body>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr valign="middle"> 
        <td height="40" width="200"><img src="%ImageDirectory/banner_logo.jpg" ></td>
      </tr>
  </table>
   <table border="0" cellpadding="0" cellspacing="0" bgcolor="#006464" width="100%">
    <tr> 
      <td>&nbsp;</td>
	</tr>  
    </table>	 	
	<br><br>
<center>	
<font face="Arial, Helvetica, sans-serif" color="#000000">A ocurrido un Error interno en la Aplicación. Cierre el navegador e intente mas tarde. </font> 
</center>
</body>

</html>

<%
	if ( !(exception instanceof java.net.SocketException) )
	{
		GregorianCalendar g = new GregorianCalendar();
		java.util.Date d = g.getTime();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		String rightNow = df.format(d);
		String outMessage = "\n[" + rightNow + " :: " + exception + "]\n-----------------\n";
		outMessage += makeErrorReport(request, exception);
		Props p = Props.getSystemProps();
		String errorFile = p.getProperty("JSPErrorLogFile");
		outMessage += errorFile;
		try
		{
			File f = new File(errorFile);
			FileOutputStream out2 = null;
			if (f.exists())
			{
				out2 = new FileOutputStream(errorFile, true);
			}
			else
			{
				out2 = new FileOutputStream(f);
			}
			PrintWriter pFile = new PrintWriter(new OutputStreamWriter(out2));
			
			pFile.println(outMessage);
	
			pFile.close();
			out2.close();
		}
		catch (Exception e)
		{
			System.err.println("Error Writing Message to " + errorFile + ":" + e);
		}
	}
%>

<%= makeErrorReport(request, exception) %>
<%!

  public void reportException(StringBuffer buffer, Throwable e)
  {
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    buffer.append("<h3>Stack Trace</h3>\n");
    buffer.append("<pre>" + writer.getBuffer() + "</pre>\n");
    buffer.append("<br />\n");
  }
  
  public void reportRequest(StringBuffer buffer, HttpServletRequest req)
  {
    buffer.append("<h3>Request</h3>\n");
    buffer.append("Request: ");
    buffer.append(req.getMethod());
    buffer.append(" ");
    buffer.append(HttpUtils.getRequestURL(req));
    String queryString = req.getQueryString();
    if (queryString != null)
    {
      buffer.append('?');
      buffer.append(queryString);
    }
    buffer.append("<br />\nSession ID: ");
    String sessionId = req.getRequestedSessionId();
    if (sessionId == null)
    {
      buffer.append("none");
    }
    else if (req.isRequestedSessionIdValid())
    {
      buffer.append(sessionId);
      buffer.append(" from ");
      if (req.isRequestedSessionIdFromCookie())
      {
        buffer.append("cookie<br />\n");
      }
      else if (req.isRequestedSessionIdFromURL())
      {
        buffer.append("URL<br />\n");
      }
      else
      {
        buffer.append("unknown<br />\n");
      }
    }
    else
    {
      buffer.append("invalid<br />\n");
    }
    buffer.append("<br />\n");
  }
  
  public void reportParameters(StringBuffer buffer, HttpServletRequest req)
  {
    Enumeration names = req.getParameterNames();
    if (names.hasMoreElements())
    {
      buffer.append("<h3>Request Parameters</h3>\n");
      while(names.hasMoreElements())
      {
        String name  = (String) names.nextElement();
        String[] values = req.getParameterValues(name);
        for (int i =0; i < values.length; ++i)
        {
          buffer.append(name);
          buffer.append(" = ");
          buffer.append(values[i]);
          buffer.append("<br />\n");
        }
      }
    }
  }
    
  public void reportHeaders (StringBuffer buffer, HttpServletRequest req) {
    Enumeration names = req.getHeaderNames();
    if (names.hasMoreElements()) {
      buffer.append("<h3>HTTP Headers</h3>\n");
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        String value = (String) req.getHeader(name);
        buffer.append("    ");
        buffer.append(name);
        buffer.append(": ");
        buffer.append(value);
        buffer.append('\n');
      }
    }
  }

  public void reportCookies (StringBuffer buffer, HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if(cookies==null)
    	return;
    int l = cookies.length;
    if (l > 0) {
      buffer.append("Cookies:\n");
      for (int i = 0; i < l; ++i) {
        Cookie cookie = cookies[i];
        buffer.append("    ");
        buffer.append(cookie.getName());
        buffer.append(" = ");
	buffer.append(cookie.getValue());
	buffer.append('\n');
      }
    }
  }

  public String makeErrorReport(HttpServletRequest req, Throwable e)
  {
    StringBuffer buffer = new StringBuffer();
    reportException(buffer, e);
    reportRequest(buffer, req);
    reportParameters(buffer, req);
    reportHeaders(buffer, req);
    reportCookies(buffer, req);
    
    return buffer.toString();
  }
%>