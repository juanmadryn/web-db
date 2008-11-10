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
	controller="partesMO.controllers.CargarPartesPlanoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí 														  -->
	<!-- ********************************************************************************************* -->
	<salmon:datasource name="dsPartes" type="MODEL" dbprofile="partesmo"
		model="partesMO.models.PartesMoModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsTareas" type="MODEL" dbprofile="proyectos"
		model="proyectos.models.TareasProyectoModel" autoretrieve="Always">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:listformdisplaybox mode="Display_single_page"
			caption="Carga de Partes Diarios" name="listFormDisplayBox1"
			width="100%" addbuttonvisible="false" savebuttonvisible="false"
			datasource="dsPartes" autocreatelink="FALSE">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsPartes" rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="fechaCAP3" text="fecha"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="legajoCAP1" text="Legajo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="proyectoHeaderTD">
							<salmon:text name="proyectoCAP4" text="Proyecto"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="tareaProyectoHeaderTD">
							<salmon:text name="tareaCAP11" text="Tarea"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="horaDesdeCAP5" text="Desde"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="horaHastaCAP5" text="Hasta"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="horasCAP6" text="Horas"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="mensajesCAP10" text="Mensaje"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td>
							<salmon:text name="sectorCAP7" text="Sector"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td colspan="2">
							<salmon:text name="supervisorCAP8" text="Supervisor"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="accionesCAP9" text="Acciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td></salmon:td>
						<salmon:td>
							<salmon:text name="categoriaCAP2" text="Categoría"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="selParteCAP70" text="-X-"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:input name="fechaTE3" type="text" size="10"
								maxlength="10" displayformat="dd/MM/yyyy"
								datasource="dsPartes:partes_mo.fecha">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpLegajoLegajo" name="legajoTE1" size="6"
								maxlength="10" displayformat="##########0"
								datasource="dsPartes:partes_mo.nro_legajo" popupheight="450"
								popupwidth="500" usepopup="true" showdescription="False"></salmon:lookup>
							<salmon:text name="apeynomTEX1" text="apeynom goes here"
								font="DefaultFont" datasource="dsPartes:partes_mo.apeynom" />
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
							<salmon:input type="text" name="horaDesdeTE4" size="5"
								maxlength="5" datasource="dsPartes:partes_mo.hora_desde">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="horahastaTE5" size="5"
								maxlength="5" datasource="dsPartes:partes_mo.hora_hasta">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="horasTE6" size="5" maxlength="5"
								displayformat="##0.00" datasource="dsPartes:partes_mo.horas">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:text name="mensajesTE19" text="mensaje_error goes here"
								font="DefaultFont" datasource="dsPartes:mensaje_error" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td>
							<salmon:input type="select" name="sectorTE7"
								datasource="dsPartes:partes_mo.sector_id">
								<salmon:option display="abc" key="123" table="sector_trabajo"
									keycolumn="sector_id" displaycolumn="nombre" nulloption="true"></salmon:option>
							</salmon:input>
						</salmon:td>
						<salmon:td colspan="2">
							<salmon:input type="select" name="supervisorTE8"
								datasource="dsPartes:partes_mo.supervisor">
								<salmon:option display="abc" key="123" table="supervisores"
									keycolumn="nro_legajo" displaycolumn="apeynom"
									nulloption="true"></salmon:option>
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="submit" name="nuevoBUT91" value="Copiar"
								accesskey="C">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="submit" name="nuevoBUT92" value="Nuevo"
								accesskey="N">
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input name="categoriaTE2" type="select"
								datasource="dsPartes:partes_mo.categoria">
								<salmon:option display="abc" key="123" nulloption="true"></salmon:option>
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="checkbox" name="seleccionParte"
								checkedtruevalue="1"></salmon:input>
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
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
