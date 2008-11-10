<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
	<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function llenarLista(resetear) {
	//dropdownlist de tareas 
	var select_tareas;
	//entero que cuenta la cantidad de tareas disponibles según el proyecto seleccionado	
	var index;
	//proyecto seleccionado con el lookup de proyectos	
	var proyecto;
	//tarea anteriormente persistida para el parte
	var tarea_id;
	//flag para determinar si una tarea es la que estaba seleccionada
	var flag = true;
	//flag para determinar si debo mostrar o no el título de la columna tarea en el header de la tabla 
	var flag2 = true;	
	//datatable auxiliar que contiene todas las tareas
	var datatable2 = document.getElementById('datatable2');
	// id de la tarea en la tabla auxiliar	
	var tarea;
	// nombre de la tarea en la tabla auxiliar
	var nombre_tarea;
	// descripcion de la tarea en la tabla auxiliar
	var descripcion_tarea;
	//proyecto de cada tarea en la tabla auxiliar	
	var proyecto_tarea;
	
	
	
	for (var j=0;document.getElementById('proyectoTE3_edit_'+j) != null;j++) {
		select_tareas = document.getElementById('tarea_proyecto1_'+j)		
		index = 0;
		//si modifiqué el proyecto, reseteo las opciones
		if(resetear) {
			for(var l = 0; l < select_tareas.length ; l++) {
				select_tareas.remove(l);
			}			
		}
		 
		proyecto = document.getElementById('proyectoTE3_edit_'+j).value;
		tarea_id = document.getElementById('tareaProyectoTableTDtareaId_'+j).value;
		
		for (var i=0;i<datatable2.rows.length;i++) {
		
			proyecto_tarea = datatable2.rows[i].cells[2].innerHTML;
			proyecto_tarea = proyecto_tarea.substring(proyecto_tarea.indexOf('>')+1,proyecto_tarea.lastIndexOf('<'));
			
			if(proyecto_tarea == proyecto) {
		
				nombre_tarea = datatable2.rows[i].cells[0].innerHTML;
				nombre_tarea = nombre_tarea.substring(nombre_tarea.indexOf('>')+1,nombre_tarea.lastIndexOf('<'));
		
				descripcion_tarea = datatable2.rows[i].cells[1].innerHTML;
				descripcion_tarea = descripcion_tarea.substring(descripcion_tarea.indexOf('>')+1,descripcion_tarea.lastIndexOf('<'));

				tarea = datatable2.rows[i].cells[3].innerHTML;
				tarea = tarea.substring(tarea.indexOf('>')+1,tarea.lastIndexOf('<'));
			
				select_tareas.options[index] = new Option(descripcion_tarea, nombre_tarea); 
				
				if(tarea == tarea_id) {
					select_tareas.selectedIndex = index;
					flag = false;											
				} else
					flag = true;
							
				index = index + 1;			
			}					
		}		
					
		if(flag) {
			select_tareas.selectedIndex = 0;			
		}			
		if (index == 1) {
			$('tarea_proyecto1_'+j).hide();						
			//select_tareas.style.display='none';			
		} else {
			$('tarea_proyecto1_'+j).appear();
			flag2 = false						
			//select_tareas.style.display='';
		}		
	}
	
	// Oculta el título de la columna "Tarea" en el Header de la tabla  
	html = $('tareaProyectoHeaderTD').innerHTML.toLowerCase();
	if(flag2) {
		$('tareaProyectoHeaderTD').innerHTML = html.substring(0,html.indexOf('<b>')+3)+html.substring(html.indexOf('</b>'),html.length);
	}
	else
		$('tareaProyectoHeaderTD').innerHTML = html.substring(0,html.indexOf('<b>')+3)+'Tarea'+html.substring(html.indexOf('</b>'),html.length);
			
}
//-->
</script>
<salmon:page
	controller="partesMO.controllers.ConsultaPartesMoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definicián de DataSource aquá -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
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
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="buscarTE3" size="60"
									maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
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
						<salmon:td>
							<salmon:text name="tarea1" text="Tarea"
								font="TableHeadingFont" />
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
								displayformat="dd/MM/yyyy" datasource="dsPartes:partes_mo.fecha"/>
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
							<salmon:text name="proyectosProyectoTE24"
								text="Proyectos.proyecto" font="DefaultFont"
								datasource="dsPartes:proyectos.proyecto" />
							<salmon:text name="proyectosNombreTE24" text="Proyectos.nombre"
								font="DefaultFont" datasource="dsPartes:proyectos.nombre" />
						</salmon:td>
						<salmon:td>							
							<salmon:text name="tarea2" text="Proyectos.nombre"
								font="DefaultFont" datasource="dsPartes:tareas.nombre +'-'+tareas.descripcion" />
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
						</salmon:td>
						<salmon:td>
							<salmon:text name="mensaje" text="Mensaje"
								font="TableHeadingFont" datasource="dsPartes:mensaje_error"/>
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
