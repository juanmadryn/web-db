<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<html>
<salmon:page/>
<salmon:body/>
<salmon:text name="text1" text="Error Procesando su requerimiento." font="PageHeadingFont"/><br><br>
<salmon:text name="text2" text="Probablemente la página del requerimiento no era válida." font="DefaultFont"/>
<br><br>
<salmon:form name="pageForm"><salmon:a href="%HomePage" name="a1" onclick="document.forms['bannerForm'].submit();"><salmon:text name="text3" text="Presione aquí para regresar a la página principal" font="LinkFont"/></salmon:a></salmon:form>
<salmon:endbody/>
<salmon:endpage/>
</html>