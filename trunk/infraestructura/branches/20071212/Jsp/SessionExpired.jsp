<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<html>
<salmon:page/>
<salmon:body/>
<salmon:text name="text1" text="Su sesión ha expirado, probablemente por tiempo" font="PageHeadingFont"/>
<br>
<br>
<salmon:form name="pageForm">
  <salmon:a name="a1" href="%HomePage">
  <salmon:text name="text2" text="Presione aquí para comenzar una nueva sesión" font="LinkFont"/>
  </salmon:a></salmon:form> 
<salmon:endbody/>
<salmon:endpage/>
</html>