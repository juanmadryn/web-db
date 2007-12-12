<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr> 
  		<td> <table width="100%">
      		<tr> 
        		<td> <salmon:text name="text1" text="Bienvenido a la Aplicación ..." textlocalekey="textWelcome" font="PageHeadingFont"/> <BR>
					 <FONT STYLE="FONT-FAMILY:Times New Roman, Times, Verdana,Helvetica,sans-serif; FONT-SIZE:12pt;" COLOR="BLACK"><I> Aplicación para la Administración ... </I> </FONT>
				</td>
      		</tr>
    	</table></td>
	</tr>
	<tr>
  		<td><%@include file="message.jsp"%> </td>
	</tr>
</table>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>