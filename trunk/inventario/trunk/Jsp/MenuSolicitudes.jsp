<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>

<!-- ********************************************************************************************* -->
<!-- Agregar c�digo de la p�gina aqu� -->
<!-- ********************************************************************************************* -->
<table border="0" cellspacing="0" cellpadding="0" width="100%">	
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td><salmon:text name="text1"
					text="Bienvenido al men� de Solicitudes de Compra"
					textlocalekey="textWelcome" font="SectionHeadingFont" /> <BR>
				<FONT
					STYLE="FONT-FAMILY: Times New Roman, Times, Verdana, Helvetica, sans-serif; FONT-SIZE: 12pt;"
					COLOR="BLACK"><I> Aplicaci�n para Administrar Solicitudes de Compra
				</I> </FONT></td>
			</tr>
			<tr>
				<td><br>
				<br>
				<salmon:text name="text2" text="Men� para Administrar Solicitudes de Compra"
					textlocalekey="textWelcome" font="PageHeadingFont" /> <BR>
				<FONT
					STYLE="FONT-FAMILY: Times New Roman, Times, Verdana, Helvetica, sans-serif; FONT-SIZE: 12pt;"
					COLOR="BLACK"><I> Men� principal de la Administraci�n de
				Solicitudes de Compra </I> </FONT><br>
				<br>
				</td>
			</tr>			
		</table>
		</td>
	</tr>
	<tr>
		<td><%@include file="message.jsp"%></td>
	</tr>
</table>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />