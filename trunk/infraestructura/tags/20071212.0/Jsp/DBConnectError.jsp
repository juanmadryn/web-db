<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<html>
<salmon:page/>
<salmon:body/>

<salmon:text name="text1" text="Error en Conexión de Base de Datos." font="PageHeadingFont" textlocalekey="HdngYourAccInfo"/><br><br>
<salmon:font type="DefaultFont">A ocurrido un error conectándose a la Base de Datos<br>
<br>
Este error puede suceder si usted no es un usuario registrado de la aplicación
<br>
o bien no tiene asignada región (Delegación) en su legajo
<br>
<br>
<br>
Por favor póngase en contacto con el Admisnitardor de Sistemas<br>
<br>
ó con el responsable de la aplicación Informe de devolución<br>
<br>
</salmon:font>
<salmon:endbody/>

<salmon:endpage/>
</html>