<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function cambiarURL() {	
	var a_reporte1_pdf = document.getElementsByName('htmlPageTopContainer_reporte1BUT1')[0];
	var a_reporte1_xls = document.getElementsByName('htmlPageTopContainer_reporte1BUT2')[0];
	var a_reporte2_pdf = document.getElementsByName('htmlPageTopContainer_reporte2BUT1')[0];
	var a_reporte2_xls = document.getElementsByName('htmlPageTopContainer_reporte2BUT2')[0];
	
	var fecha_desde = document.getElementsByName('htmlPageTopContainer_calendar_fechadesde2')[0];
	var fecha_hasta = document.getElementsByName('htmlPageTopContainer_calendar_fechahasta2')[0];
	fecha_desde = fecha_desde.value.substring(6,10)+'-'+fecha_desde.value.substring(3,5)+'-'+fecha_desde.value.substring(0,2);
	fecha_hasta = fecha_hasta.value.substring(6,10)+'-'+fecha_hasta.value.substring(3,5)+'-'+fecha_hasta.value.substring(0,2);
	
	
	var parametros = '&param_fecha_desde='+fecha_desde+'&param_fecha_hasta='+fecha_hasta
	if(fecha_desde.length < 10 || fecha_hasta.length < 10)
		parametros = '';
	
	var proyecto = document.getElementsByName('htmlPageTopContainer_calendar_proyecto2')[0].value;
	if (proyecto.length > 0)
		parametros = parametros + '&param_proyecto='+proyecto;

	var tarea = document.getElementsByName('htmlPageTopContainer_calendar_tarea2')[0].value;
	if (tarea.length > 0)
		parametros = parametros + '&param_tarea='+tarea;		
	
	a_reporte1_pdf.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace\\partesmo\\Reportes\\partesmo_por_fecha.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=pdf&__toolbar=true'+parametros;
	a_reporte1_xls.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace\\partesmo\\Reportes\\partesmo_por_fecha.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=xls&__toolbar=true'+parametros;
	a_reporte2_pdf.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace\\partesmo\\Reportes\\partesmo_por_fecha_ot.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=pdf&__toolbar=true'+parametros;
	a_reporte2_xls.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace\\partesmo\\Reportes\\partesmo_por_fecha_ot.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=xls&__toolbar=true'+parametros;		
		
}	


//-->
</script>
<salmon:page
	controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>

<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td><br>
				<br>
				<salmon:text name="text2" text="Reportes"
					textlocalekey="textWelcome" font="PageHeadingFont" /> <BR>
				<FONT
					STYLE="FONT-FAMILY: Times New Roman, Times, Verdana, Helvetica, sans-serif; FONT-SIZE: 12pt;"
					COLOR="BLACK"><I>Reportes para Partes de Mano de Obra </I> </FONT><br>
				<br>
				</td>
			</tr>
			<tr>
				<td><salmon:container name="calendar">
					<table width="40%">
						<tr>
							<td><salmon:text name="fechadesde1" text="Fecha desde"
								font="TableHeadingFont" /></td>
							<td><salmon:input type="text" name="fechadesde2" size="10"
								maxlength="10"></salmon:input></td>
							<td><salmon:text name="fechahasta1" text="Fecha hasta"
								font="TableHeadingFont" /></td>
							<td><salmon:input type="text" name="fechahasta2" size="10"
								maxlength="10"></salmon:input></td>
						</tr>
						<tr>
							<td><salmon:text name="proyecto1" text="Proyecto"
								font="TableHeadingFont" /></td>
							<td><salmon:input type="text" name="proyecto2" size="10"
								maxlength="10"></salmon:input></td>
							<td><salmon:text name="tarea1" text="Tarea"
								font="TableHeadingFont" /></td>
							<td><salmon:input type="text" name="tarea2" size="10"
								maxlength="10"></salmon:input></td>
						</tr>
					</table>
				</salmon:container></td>
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
					<th colspan="2" align="center">Formato
					</td>
				</tr>
				<tr align="center">
					<th width="60%" align="left">Reporte
					</td>
					<th width="20%">PDF
					</td>
					<th width="20%">Excel
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
				<tr>
					<td>Horas de Mano de Obra por fecha</td>
					<td align="center"><salmon:a href="" target="_blank"
						name="reporte1BUT1" onclick="cambiarURL();" title="PDF">
						<salmon:text name="reporte1TXT1" text="PDF" />
						<salmon:img name="reporte1IMG1" src="%ImageDirectory/pdf.jpg"
							height="25" srclocalekey="bannerImageSource" />
					</salmon:a></td>
					<td align="center"><salmon:a href="" target="_blank"
						name="reporte1BUT2" onclick="cambiarURL();" title="XLS">
						<salmon:text name="reporte2TXT1" text="XLS" />
						<salmon:img name="reporte2IMG1"
							src="%ImageDirectory/logo_excel.gif" height="25"
							srclocalekey="bannerImageSource" />
					</salmon:a></td>
				</tr>
				<td>Horas de Mano de Obra por Proyecto/Tarea</td>
				<td align="center"><salmon:a href="" target="_blank"
					name="reporte2BUT1" onclick="cambiarURL();" title="PDF">
					<salmon:text name="reporte1TXT2" text="PDF" />
					<salmon:img name="reporte1IMG2" src="%ImageDirectory/pdf.jpg"
						height="25" srclocalekey="bannerImageSource" />
				</salmon:a></td>
				<td align="center"><salmon:a href="" target="_blank"
					name="reporte2BUT2" onclick="cambiarURL();" title="XLS">
					<salmon:text name="reporte2TXT2" text="XLS" />
					<salmon:img name="reporte2IMG2"
						src="%ImageDirectory/logo_excel.gif" height="25"
						srclocalekey="bannerImageSource" />
				</salmon:a></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
		<td><%@include file="message.jsp"%></td>
	</tr>
</table>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />