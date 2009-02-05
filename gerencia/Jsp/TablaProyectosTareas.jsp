<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
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