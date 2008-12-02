<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script src="javascripts/utils.js" type="text/javascript"></script>
<script type="text/javascript">
function copiarHora(event) {
	var element = event.target; 
	for (var j=0;$('datatable1TDRow5horaDesdeTE26_'+j) != null;j++) {
		if(element.id.match('datatable1TDRow5horaDesdeTE26_'))
			$('datatable1TDRow5horaDesdeTE26_'+j).value = element.value;
		else
			$('datatable1TDRow5horaHastaTE26_'+j).value = element.value;
	}
}
function resaltar(event) {
	var element = event.target; 
	element.className='selected';
}
function desResaltar(event) {
	var element = event.target; 
	element.className='';
}
</script>
<salmon:page
	controller="partesMO.controllers.ConsultaPartesMoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definicián de DataSource aquá -->
	<salmon:datasource name="dsPeriodo" type="SQL" autoretrieve="Never">
		<salmon:datasourcedef>
			<salmon:bucket name="desde" datatype="DATE" />
			<salmon:bucket name="hasta" datatype="DATE" />
		</salmon:datasourcedef>
	</salmon:datasource>
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex"
			columns="parte_id,nro_legajo,apeynom,proyectos.proyecto,proyectos.nombre,tareas_proyecto.nombre" />
	</salmon:datasource>
	<salmon:datasource name="dsPartes" type="MODEL" dbprofile="partesmo"
		model="partesMO.models.PartesMoModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsTareas" type="MODEL" dbprofile="proyectos"
		model="proyectos.models.TareasProyectoModel" autoretrieve="Always">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar cádigo de la página aquá -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						searchbuttonvisible="False" addbuttonvisible="False"
						caption="Partes de Mano de Obra" qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="buscarCAP1" text="Buscar"
									font="TableHeadingFont" /></td>
								<td colspan=3"><salmon:input type="text" name="buscarTE3" size="60"
									maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="fechadesdeCAP1" text="Fecha desde"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechadesdeTE1"
									size="10" displayformat="dd/MM/yyyy"
									datasource="dsPeriodo:desde" maxlength="10"></salmon:input></td>
								<td><salmon:text name="fechahastaCAP2" text="Fecha hasta"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechahastaTE2"
								size="10" displayformat="dd/MM/yyyy"
									datasource="dsPeriodo:hasta" maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="proyecto1" text="Proyecto"
									font="TableHeadingFont" /></td>
								<td colspan="3"><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProyectos" name="proyecto2" size="15"
									maxlength="15" popupheight="450" popupwidth="500"
									usepopup="TRUE" showdescription="TRUE"></salmon:lookup></td>									
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			datasource="dsPartes" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsPartes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="parteIdCAP2" text="ID" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaCAP22" text="Fecha"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="legajoCAP23" text="Legajo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proyectoCAP24" text="Proyecto (OT)"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="tareaProyectoHeaderTD">
							<salmon:text name="tarea1" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="horarioCAP27" text="Horario"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:a href="none" name="lnkpartes1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsPartes:'%EditaParte?p_parte_id='+partes_mo.parte_id">
								<salmon:text name="proyectoTXT1" text="parte_id Goes Here"
									font="DefaultFont" datasource="dsPartes:partes_mo.parte_id" />
							</salmon:a>
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaTE22" text="fecha" font="DefaultFont"
								displayformat="dd/MM/yyyy" datasource="dsPartes:partes_mo.fecha" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroLegajoTE23" text="nro_legajo"
								font="DefaultFont" datasource="dsPartes:partes_mo.nro_legajo" />
							<salmon:text name="apeynomTE23" text="apeynom" font="DefaultFont"
								datasource="dsPartes:partes_mo.apeynom" />
						</salmon:td>
						<salmon:td name="proyectoTableTD">
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpProyectos" name="proyectoTE3" size="6"
								maxlength="15" datasource="dsPartes:proyectos.proyecto"
								descriptiondatasource="dsPartes:proyectos.nombre"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="True"></salmon:lookup>
						</salmon:td>
						<salmon:td name="tareaProyectoTableTD">
							<salmon:input type="select" name="tarea_proyecto1"
								datasource="dsPartes:tareas.nombre">
								<salmon:option display="" key="" nulloption="true"
									reloadDropDownInEveryPageRequest="false"></salmon:option>
							</salmon:input>
							<salmon:input type="text" name="tareaId" style="display:none"
								font="DefaultFont" datasource="dsPartes:partes_mo.tarea_id" />
						</salmon:td>
						<salmon:td>
							<salmon:input name="horaDesdeTE26" type="text" value=""
								font="DefaultFont" size="5"
								datasource="dsPartes:partes_mo.hora_desde"></salmon:input>
							<salmon:text name="horaaTE26" text=" a " font="DefaultFont" />
							<salmon:input name="horaHastaTE26" type="text" value=""
								font="DefaultFont" size="5"
								datasource="dsPartes:partes_mo.hora_hasta"></salmon:input>
							<salmon:text name="horaGuionTE26" text=" - " font="DefaultFont" />
							<salmon:text name="horasTE26" text="horas" font="DefaultFont"
								datasource="dsPartes:partes_mo.horas" />
							<salmon:text name="horaGuionTE27" text=" | " font="DefaultFont" />
							<salmon:text name="horario" text="horas" font="DefaultFont"
								datasource="dsPartes:horarios" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="mensaje" text="Mensaje"
								font="TableHeadingFont" datasource="dsPartes:mensaje_error" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<div id="div1" style="display: none"><salmon:datatable
		name="datatable2" width="100%" rowsperpage="10000"
		rowsperpageselector="false" datasource="dsTareas">
		<salmon:datatablerows>
			<salmon:tr>
				<salmon:td>
					<salmon:text name="tareaNombre" text="tarea_nombre Goes Here"
						font="DefaultFont" datasource="dsTareas:tareas_proyecto.nombre" />
				</salmon:td>
				<salmon:td>
					<salmon:text name="tareaDescripcion"
						text="tarea_descripcion Goes Here" font="DefaultFont"
						datasource="dsTareas:tareas_proyecto.descripcion" />
				</salmon:td>
				<salmon:td>
					<salmon:text name="proyectoId" text="proyecto_id Goes Here"
						font="DefaultFont" datasource="dsTareas:proyectos.proyecto" />
				</salmon:td>
				<salmon:td>
					<salmon:text name="tareaId2" text="tarea_id Goes Here"
						font="DefaultFont" datasource="dsTareas:tareas_proyecto.tarea_id" />
				</salmon:td>
			</salmon:tr>
		</salmon:datatablerows>
	</salmon:datatable></div>
	<!-- Fin de cádigo agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
