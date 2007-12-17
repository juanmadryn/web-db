<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<html>
<salmon:page/>
<salmon:body/>

<salmon:text name="text1" text="Error en el usuario Conectado." font="PageHeadingFont" textlocalekey="HdngYourAccInfo"/><br><br>
<salmon:font type="DefaultFont">A ocurrido un error<br>
<br>
Este error puede suceder si usted no está conectado
<br>
o no es un usuario registrado de la aplicación
<br>
o bien no tiene permisos para ejecutar esta página
<br>
<br>
<br>
Por favor póngase en contacto con el Administrador de Sistemas<br>
<br>
ó con el responsable de la aplicación ...<br>
<br>
<salmon:a href="%HomePage" name="a1"><salmon:text name="text3" text="Presione aquí para regresar a la página principal" font="LinkFont"/></salmon:a>
</salmon:font>
<salmon:endbody/>

<salmon:endpage/>
</html>