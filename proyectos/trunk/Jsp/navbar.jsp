<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<salmon:table name="navbarTable" width="100%" border="0" cellspacing="0" cellpadding="0">
  <salmon:tr> 
  <!-- Demian Barry (27-11-2005) Se reemplaza 13pt en el font-size por 11pt -->
  <!-- Demian Barry (27-11-2005) Se reemplaza with del navbar1 de 172 por 100 -->
    <salmon:td> <salmon:navbar name="navbar1" bgcolor="white" width="100" cellspacing="1" cellpadding="4" hspace="0" vspace="0"
	hrefstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none; font-weight:none; COLOR: #E3FCFC;"
	hoverstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none; font-weight:none;COLOR: #FFDE18;"
	selectedstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none; font-weight:none;COLOR: #FFDE18;"
	selectedhoverstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none; font-weight:bold;COLOR: #FFDE18;"
	showpopupstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none;COLOR: #E3FCFC;"
	showpopuphoverstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none;font-weight:bold;COLOR: #E3FCFC;"
	showpopupselectedstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none;font-weight:none;COLOR: #FFDE18;"
	showpopupselectedhoverstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration:none; font-weight:bold;COLOR: #FFDE18;"
	textstyle="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11pt; text-decoration: none; font-weight:bold; color: #E3FCFC;"
	groupcellbgcolor="#006464;" 
	cellbgcolor="#05918F;" 
	hoverbgcolor="" 
	selectedbgcolor=""  
	selectedhoverbgcolor="" 
	showpopupbgcolor="#07B5B3" 
	submenubgcolor="#07B5B3" 
	markerimage="%ImageDirectory/itemmarker.gif" 
	markeroverimage="" 
	selectedmarkerimage="%ImageDirectory/itemmarker.gif" 
	textimage="%ImageDirectory/itemTitleNullMarker.gif" 
	vspaceimage="" 
	hspaceimage="%ImageDirectory/navpad.gif" 
	groupspacerimage="">

	<salmon:navbargroup name="homeNav" grouptitle="Página Principal" href="HomePage.jsp?nav=0" vspace="0"/>
</salmon:navbar> </salmon:td>
  </salmon:tr>
</salmon:table>