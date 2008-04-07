<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<html>
<head>
<salmon:container name="noCache"> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</salmon:container> 
<title>Gestión de proyectos, inventario y partes de mano de obra</title>
<link rel="stylesheet" href="styles.css" type="text/css">
</head>
<!--framework code -->
<salmon:body leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0"/> 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td valign="top" align="center" width="100%"> 
      <!--Begin Banner -->
      <jsp:include page="banner.jsp" flush="true"></jsp:include>
      <!--End Banner -->
    </td>
  </tr>
</table><table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
  <td width="100%" colspan="2"> 
    <!-- Top Navigation Bar Goes Here -->
    <jsp:include page="topNavBanner.jsp" flush="true"></jsp:include>
    </td>
</tr>
<tr> 
  <td  bgcolor="#C6D6D6" valign="top" width="10%"> 
    <!-- NavBar Goes Here -->
    <jsp:include page="navbar.jsp" flush="true"></jsp:include>
    </td>
  <salmon:raw name="rawAddRow"></salmon:raw> 
  <td valign="top" width="90%"> <table width="100%" border="0" cellpadding="1" cellspacing="0"> 
    <tr> 
      <td> <table width="100%" border="0" cellpadding="5" cellspacing="0"> <tr> 
          <td valign="top"> 
            <!-- Page Goes Here -->