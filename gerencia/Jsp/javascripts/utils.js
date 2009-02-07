var tabla_tareas_proyecto = new Array();

function llenarVector() {
	tabla_tareas_proyecto = new Array();
	
	//datatable auxiliar que contiene todas las tareas
	var datatable2 = $('datatable2');
	
	//recorro todas las filas de la tabla (oculta) de proyecto-tareas 
	for (var i=0;i<datatable2.rows.length;i++) {
		//obtengo el proyecto de la tarea
		proyecto_tarea = datatable2.rows[i].cells[2].innerHTML;
		proyecto_tarea = proyecto_tarea.substring(proyecto_tarea.indexOf('>')+1,proyecto_tarea.lastIndexOf('<'));
	
	 	//obtengo el nombre de la tarea
		nombre_tarea = datatable2.rows[i].cells[0].innerHTML;
		nombre_tarea = nombre_tarea.substring(nombre_tarea.indexOf('>')+1,nombre_tarea.lastIndexOf('<'));
	
		//obtengo la descripción de la tarea
		descripcion_tarea = datatable2.rows[i].cells[1].innerHTML;
		descripcion_tarea = descripcion_tarea.substring(descripcion_tarea.indexOf('>')+1,descripcion_tarea.lastIndexOf('<'));

		//obtengo el id de la tarea
		tarea = datatable2.rows[i].cells[3].innerHTML;
		tarea = tarea.substring(tarea.indexOf('>')+1,tarea.lastIndexOf('<'));
			
		tabla_tareas_proyecto.push(new Array(proyecto_tarea,nombre_tarea,descripcion_tarea,tarea));			
	}	
}


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
	//tarea indicada con una barra (/) en el campo de ingreso de proyecto 
	var tarea_en_proyecto;
	
	if(tabla_tareas_proyecto.length == 0) {
		llenarVector();
	} 
	
	
	select_tareas = $('tarea_proyecto1');		
	index = 0;
		//si modifiqué el proyecto, reseteo las opciones
		if(resetear) {
			select_tareas_length = $('tarea_proyecto1').length;
			for(var l = select_tareas_length-1; l != 0; l--) {
				select_tareas.remove(l);				
			}			
		}
		select_tareas.options[0] = new Option('Todas', '');
		//Obtengo los valores del proyecto y el id de la tarea previamente guardada para el parte 
		proyecto = $('proyecto2_edit').value;
		tarea_id = $('searchformdisplaybox1tareaId').value;
		if(proyecto.indexOf('/') != -1) {
			tarea_en_proyecto = proyecto.substring(proyecto.indexOf('/')+1, proyecto.length);
			proyecto = proyecto.substring(0,proyecto.indexOf('/'));
			$('proyecto2_edit').value = proyecto;
		}
		//recorro todas las filas de la tabla (oculta) de proyecto-tareas 
		for (var i=0;i<tabla_tareas_proyecto.length;i++) {
		
			//obtengo el proyecto de la tarea
			proyecto_tarea = tabla_tareas_proyecto[i][0];
			
			//si el proyecto de la tarea es igual al proyecto elegido en la lookup de proyectos
			if(proyecto_tarea == proyecto) {
		
				//obtengo el nombre de la tarea
				nombre_tarea = tabla_tareas_proyecto[i][1];
		
				//obtengo la descripción de la tarea
				descripcion_tarea = tabla_tareas_proyecto[i][2];

				//obtengo el id de la tarea
				tarea = tabla_tareas_proyecto[i][3];
			
				//creo la opción en la dropdownlist de tareas 
				select_tareas.options[index+1] = new Option(nombre_tarea+'-'+descripcion_tarea, nombre_tarea); 
				
				//si la tarea es igual a la que ya estaba guardada en el datastore de tareas, 
				//y no está seteado el flag 'resetear'  
				if(nombre_tarea == tarea_id) {
					//seteo como tarea elegida aquella que estaba cargada en el datastore
					select_tareas.selectedIndex = index+1;
				}
		
				index = index + 1;			
			}					
		}		
}

function cambiarURL() {	
	var a_reporte1_pdf = $('htmlPageTopContainer_PageForm_reporte1BUT1');
	var a_reporte1_xls = $('htmlPageTopContainer_PageForm_reporte1BUT2');
	var a_reporte2_pdf = $('htmlPageTopContainer_PageForm_reporte2BUT1');
	var a_reporte2_xls = $('htmlPageTopContainer_PageForm_reporte2BUT2');
	var fecha_desde = $('calendarfechadesde2');
	var fecha_hasta = $('calendarfechahasta2');
	fecha_desde = fecha_desde.value.substring(6,10)+'-'+fecha_desde.value.substring(3,5)+'-'+fecha_desde.value.substring(0,2);
	fecha_hasta = fecha_hasta.value.substring(6,10)+'-'+fecha_hasta.value.substring(3,5)+'-'+fecha_hasta.value.substring(0,2);
	
	
	var parametros = '&param_fecha_desde='+fecha_desde+'&param_fecha_hasta='+fecha_hasta;
	if(fecha_desde.length < 10 || fecha_hasta.length < 10)
		parametros = '';
	
	var proyecto = $('calendarproyecto2').value;
	if (proyecto.length > 0)
		parametros = parametros + '&param_proyecto='+proyecto;

	var tarea = $('calendartarea2').value;
	if (tarea.length > 0)
		parametros = parametros + '&param_tarea='+tarea;
		
	var articulo = $('articulo2_edit').value;
	if (articulo.length > 0)
		parametros = parametros + '&param_articulo='+articulo;
		
	parametros = parametros +'&param_mostrar_detalles='+$('htmlPageTopContainer_PageForm_calendar_muestraDetalle2').checked;
	
	a_reporte1_pdf.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace3\\gerencia\\Reportes\\material_comprado.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=pdf&__toolbar=true'+parametros;
	a_reporte1_xls.href = 'http://localhost:8080/birt/run?__report=C:\\Documents+and+Settings\\Administrador\\workspace3\\gerencia\\Reportes\\material_comprado.rptdesign&__navigation=auto&__frame=true&__locale=null&__format=xls&__toolbar=true'+parametros;		
		
}	