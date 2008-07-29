<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>

<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
  		<td>
  			<table width="100%">
      			<tr>
        			<td><br><br><salmon:text name="text2" text="Reportes" textlocalekey="textWelcome" font="PageHeadingFont"/> <BR>
						 <FONT STYLE="FONT-FAMILY:Times New Roman, Times, Verdana,Helvetica,sans-serif; FONT-SIZE:12pt;" COLOR="BLACK"><I>Reportes para Partes de Equipos </I> </FONT><br><br>
					</td>	
      			</tr>      		
    		</table>
    	</td>    	
	</tr>
	<tr>
		<td>
			<table width="100%" border="1">
				<thead>
					<tr>
						<th />
						<th colspan="2" align="center">Formato</td>					
					</tr>
					<tr align="center">				
						<th width="60%" align="left">Reporte</td>	
						<th width="20%">PDF</td>					
						<th width="20%">Excel</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Nombre del reporte</td>
						<td align="center">
							<salmon:a href="" target="_blank"
                           		name="reporte1BUT2"
                           		onclick="document.forms['bannerForm'].submit();"
                           		title="PDF">
                           		<salmon:text name="reporte1TXT2" text="PDF" />
                           		<salmon:img name="reporte1IMG2"
                              		src="%ImageDirectory/pdf.jpg" height="25"
                              		srclocalekey="bannerImageSource" />
                            </salmon:a>                              	
						</td>
						<td align="center">
							<salmon:a href="" target="_blank"
                           		name="reporte2BUT2"
                           		onclick="document.forms['bannerForm'].submit();"
                           		title="XLS">
                           		<salmon:text name="reporte2TXT2" text="XLS" />
                           		<salmon:img name="reporte2IMG2"
                              		src="%ImageDirectory/logo_excel.gif" height="25"
                              		srclocalekey="bannerImageSource" />
                            </salmon:a>
						</td>
					</tr>
				</tbody>
			</table>			
		</td>
	</tr>
	<tr>
  		<td><%@include file="message.jsp"%> </td>
	</tr>
</table>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>