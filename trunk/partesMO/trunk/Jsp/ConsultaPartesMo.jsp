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
	var datatable2 = $('datatable2');
	// id de la tarea en la tabla auxiliar	
	var tarea;
	// nombre de la tarea en la tabla auxiliar
	var nombre_tarea;
	// descripcion de la tarea en la tabla auxiliar
	var descripcion_tarea;
	//proyecto de cada tarea en la tabla auxiliar	
	var proyecto_tarea;
		//cantidad de opciones de la dropdownlist de tareas 
	var select_tareas_length;	
	
	//recorro todas los partes cargados
	for (var j=0;$('proyectoTE3_edit_'+j) != null;j++) {
		select_tareas = $('tarea_proyecto1_'+j);		
		index = 0;
		//si modifiqué el proyecto, reseteo las opciones
		if(resetear) {
			select_tareas_length = $('tarea_proyecto1_'+j).length;
			for(var l = select_tareas_length-1; l != 0; l--) {
				select_tareas.remove(l);				
			}			
		}
		
		//Obtengo los valores del proyecto y el id de la tarea previamente guardada para el parte 
		proyecto = $('proyectoTE3_edit_'+j).value;
		tarea_id = $('tareaProyectoTableTDtareaId_'+j).value;
		
		//recorro todas las filas de la tabla (oculta) de proyecto-tareas 
		for (var i=0;i<datatable2.rows.length;i++) {
		
			//obtengo el proyecto de la tarea
			proyecto_tarea = datatable2.rows[i].cells[2].innerHTML;
			proyecto_tarea = proyecto_tarea.substring(proyecto_tarea.indexOf('>')+1,proyecto_tarea.lastIndexOf('<'));
			
			//si el proyecto de la tarea es igual al proyecto elegido en la lookup de proyectos
			if(proyecto_tarea == proyecto) {
		
				//obtengo el nombre de la tarea
				nombre_tarea = datatable2.rows[i].cells[0].innerHTML;
				nombre_tarea = nombre_tarea.substring(nombre_tarea.indexOf('>')+1,nombre_tarea.lastIndexOf('<'));
		
				//obtengo la descripción de la tarea
				descripcion_tarea = datatable2.rows[i].cells[1].innerHTML;
				descripcion_tarea = descripcion_tarea.substring(descripcion_tarea.indexOf('>')+1,descripcion_tarea.lastIndexOf('<'));

				//obtengo el id de la tarea
				tarea = datatable2.rows[i].cells[3].innerHTML;
				tarea = tarea.substring(tarea.indexOf('>')+1,tarea.lastIndexOf('<'));
			
				//creo la opción en la dropdownlist de tareas 
				select_tareas.options[index] = new Option(nombre_tarea+'-'+descripcion_tarea, nombre_tarea); 
				
				//si la tarea es igual a la que ya estaba guardada en el datastore de tareas, 
				//y no está seteado el flag 'resetear'  
				if(tarea == tarea_id) {
					//seteo como tarea elegida aquella que estaba cargada en el datastore
					select_tareas.selectedIndex = index;
					flag = false;																
				} else
					flag = true;
							
				index = index + 1;			
			}					
		}		
		
		//si no hay tareas asociadas al proyecto elegido, seteo una única opción que lo aclara 					
		if (index == 0) {
			select_tareas.options[0] = new Option("El proyecto no posee tareas", 0); 			
		} else {
			flag2 = false						
		}
	}
	
	// Oculta el título de la columna "Tarea" en el Header de la tabla  
	/*html = $('tareaProyectoHeaderTD').innerHTML.toLowerCase();
	if(flag2) {
		$('tareaProyectoHeaderTD').innerHTML = html.substring(0,html.indexOf('<b>')+3)+html.substring(html.indexOf('</b>'),html.length);
	}
	else
		$('tareaProyectoHeaderTD').innerHTML = html.substring(0,html.indexOf('<b>')+3)+'Tarea'+html.substring(html.indexOf('</b>'),html.length);*/
			
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
						<salmon:td name="tareaProyectoHeaderTD">
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
