<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<birt:viewer id="birtViewer" showParameterPage="true" title="Visor de reportes" locale="es"
		baseURL="/birt" height="700" width="1500"
		reportDesign="gerencia\\gestion_compra.rptdesign"
		format="html"></birt:viewer>
<!--
Parameter Page 1
<br>
<birt:parameterPage
id="report1"
name="page1"
reportDesign="C:\\Documents and Settings\\Administrador\\workspace\\gerencia\\Reportes\\tendencia_compra.rptdesign"
isCustom="true"
pattern="frameset">

Cascading Parameter1: <birt:paramDef id="5" name="param_proyecto" />
<br><br>
Cascading Parameter2: <birt:paramDef id="6" name="param_hora_desde"/>
<br><br>
Cascading Parameter3: <birt:paramDef id="7" name="param_hora_hasta" />
<br><br>
<input type="submit" name="submit" value="Sumbit form"/>
<br><br>
</birt:parameterPage>-->

<!--<birt:parameterPage id="birtParmPage" reportDesign="C:\\Documents and Settings\\Administrador\\workspace\\gerencia\\Reportes\\tendencia_compra.rptdesign"
name="form1"
pattern="frameset"
height="600"
width="800"
title="My Viewer Tag"
isCustom="true"
baseURL="/birt"
>
Parameter1: <birt:paramDef id="p5" name="param_proyecto" />
<br><br>
Cascading Parameter2: <birt:paramDef id="p6" name="param_fecha_desde"/>
<br><br>
Cascading Parameter3: <birt:paramDef id="p7" name="param_fecha_hasta"/>
<br><br> 
<input type="Submit" value="Ver">
<br> 
</birt:parameterPage>-->
</body>
</html>
