<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="../../infraestructura/Jsp/ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infdev.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definici�n de DataSource aqu� -->


<!-- ********************************************************************************************* -->
<!-- Agregar c�digo de la p�gina aqu� -->
<!-- ********************************************************************************************* -->


<!-- Fin de c�digo agregado -->
          
</salmon:form> 
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>