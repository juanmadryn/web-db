<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController"/> 
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
      <salmon:text name="txtHeading" text="Acerca de la Aplicacioón ... 1.0" font="PageHeadingFont" textlocalekey="AboutHeading"/> 
	  <br><br><br>
      <salmon:text name="aboutBody1" text="Aplicación desarrollada por Demian Barry de Web-DB" textlocalekey="AboutBody1"/> 
	  <br><br><br>
      <salmon:text name="txtAboutBody2" text="Aplicación desarrollada integramente usando herramientas de Software Libre" textlocalekey="AboutBody2"/>&nbsp; 
	  <br><br><br>
                  <salmon:text name="txtAboutBody3" text="Para la empresa ..." textlocalekey="AboutBody2"/>&nbsp; 
                  <salmon:a name="lnkme" href="%meHome"><salmon:text name="txtLink" text="http://www.xxxxx.yyy.ar" font="LinkFont"/></salmon:a>
	  <br><br><br><br><br><br>				  
                  <jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>