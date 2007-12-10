<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<%@ taglib uri="/taglib.tld" prefix="salmon"%>
<html>
<salmon:page/>
<salmon:body/>
<salmon:form name="pageForm">
  <salmon:text name="text1" text="Hello World" font="ErrorFont"/>
</salmon:form>
<salmon:endbody/>
<salmon:endpage/>
</html>