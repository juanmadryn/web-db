<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script src="javascripts/utils.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
//-->
</script>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>

<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:form name="PageForm">
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
						<tr>
							<td><salmon:text name="articulo1" text="Artículo"
								font="TableHeadingFont" /></td>
							<td><salmon:lookup
								browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpArticulos" name="articulo2" size="20"
								maxlength="15" popupheight="450"
								popupwidth="600" usepopup="true"></salmon:lookup></td>
							<td><salmon:text name="muestraDetalle1"
								text="Mostrar detalles" font="TableHeadingFont" /></td>
							<td><salmon:input type="checkbox" name="muestraDetalle2"
								checkedtruevalue="1"></salmon:input></td>
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
					<td>Compras de materiales</td>
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
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
		<td><%@include file="message.jsp"%></td>
	</tr>
</table>
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />