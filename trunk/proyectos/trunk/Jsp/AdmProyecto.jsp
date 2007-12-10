<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="proyectos.controllers.AdmProyectoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>

	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsProyecto" type="MODEL" dbprofile="proyectos"
		model="proyectos.models.ProyectoModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsAtributos" type="MODEL"
		model="infraestructura.models.AtributosEntidadModel"
		dbprofile="infraestructura" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsActividadesProyecto" type="MODEL"
		dbprofile="proyectos"
		model="proyectos.models.ActividadesProyectoModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsTareasProyecto" type="MODEL"
		dbprofile="proyectos" model="proyectos.models.TareasProyectoModel"
		autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Proyecto" width="100%" datasource="dsProyecto"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false">
						<table width="100%">
							<tr>
								<td><salmon:text name="proyectoCAP1" text="Proyecto"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="proyectoTE1" size="40"
									maxlength="90" datasource="dsProyecto:proyectos.proyecto"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCAP1" text="Nombre"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreTE1" size="40"
									maxlength="90" datasource="dsProyecto:proyectos.nombre"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP2" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE2"
									size="40" maxlength="255"
									datasource="dsProyecto:proyectos.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP3" text="Obs."
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE3"
									size="40" maxlength="255"
									datasource="dsProyecto:proyectos.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="vigenciadesdeCAP36"
									text="Vigente desde" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" size="10" maxlength="10"
									displayformat="dd/MM/yyyy" name="vigenciadesdeTE36"
									datasource="dsProyecto:proyectos.vigencia_desde"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="vigenciahastaCAP35"
									text="Vigente hasta" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" size="10" maxlength="10"
									displayformat="dd/MM/yyyy" name="vigenciahastaTE35"
									datasource="dsProyecto:proyectos.vigencia_hasta"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="clienteCAP4" text="Cliente"
									font="ColumnCaptionFont" /></td>
								<td width="300"><salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpClientes" name="clienteTE4" size="6"
									maxlength="10" displayformat="#########0"
									descriptiondatasource="dsProyecto.entidad_externa.nombre"
									datasource="dsProyecto:proyectos.entidad_id" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="idCAP5" text="ID"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="idTXT5" text="id Goes Here"
									font="DefaultFont"
									datasource="dsProyecto:proyectos.proyecto_id" /></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:listformdisplaybox name="listformdisplaybox2"
						mode="Display_single_page" caption="Actividades del proyecto"
						width="100%" datasource="dsActividadesProyecto"
						addbuttonvisible="false" savebuttonvisible="true">
						<salmon:datatable name="datatable2" width="100%"
							datasource="dsActividadesProyecto" rowsperpage="5">
							<salmon:datatableheader>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="selActividadCAP60" text="-X-"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="actividadCAP20" text="Actividad"
											font="TableHeadingFont" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatableheader>
							<salmon:datatablerows>
								<salmon:tr>
									<salmon:td>
										<salmon:input type="checkbox" name="seleccionActividad"
											checkedtruevalue="1"></salmon:input>
									</salmon:td>
									<salmon:td width="300">
										<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
											lookupurl="%LkpActividades?p_es_hoja=true"
											name="actividadTE20" size="6" maxlength="10"
											displayformat="##########0"
											descriptiondatasource="dsActividadesProyecto:actividades.nombre"
											datasource="dsActividadesProyecto:actividades_proyecto.actividad_id"
											popupheight="450" popupwidth="500" usepopup="true"
											showdescription="true"></salmon:lookup>
									</salmon:td>
								</salmon:tr>
							</salmon:datatablerows>
						</salmon:datatable>
					</salmon:listformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox3"
			mode="Display_single_page" caption="Tareas del proyecto" width="100%"
			datasource="dsTareasProyecto" addbuttonvisible="false"
			savebuttonvisible="false">
			<salmon:datatable name="datatable3" width="100%"
				datasource="dsTareasProyecto" rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="selTareaCAP70" text="-X-"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="idCAP30" text="ID" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP31" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP32" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observacionesCAP33" text="Observaciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="claseTareaCAP34" text="Clase"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td>
							<salmon:text name="actividadCAP30" text="Actividad"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:input type="checkbox" name="seleccionTarea"
								checkedtruevalue="1"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:text name="idTXT30" text="id tarea Goes Here"
								font="DefaultFont"
								datasource="dsTareasProyecto:tareas_proyecto.tarea_id" />
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="nombreTE31" size="30"
								maxlength="90"
								datasource="dsTareasProyecto:tareas_proyecto.nombre"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="descripcionTE32" size="30"
								maxlength="255"
								datasource="dsTareasProyecto:tareas_proyecto.descripcion"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="observacionesTE33" size="30"
								maxlength="255"
								datasource="dsTareasProyecto:tareas_proyecto.observaciones"></salmon:input>
						</salmon:td>
						<salmon:td width="300">
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpClasesTarea" name="claseTareaTE34" size="15"
								maxlength="15" displayformat="##########0"
								descriptiondatasource="dsTareasProyecto:clases_tareas.nombre"
								datasource="dsTareasProyecto:tareas_proyecto.clase_tarea_id"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td width="300">
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl=""
								name="actividadTE21" size="15" maxlength="15"
								displayformat="##########0"
								descriptiondatasource="dsTareasProyecto:actividades.nombre"
								datasource="dsTareasProyecto:tareas_proyecto.actividad_proyecto_id"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box3" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox4"
			mode="Display_single_page" caption="Atributos del Proyecto"
			width="100%" datasource="dsAtributos" addbuttonvisible="false"
			savebuttonvisible="true">
			<salmon:datatable name="datatable4" width="100%"
				datasource="dsAtributos" rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="atributoCAP15" text="Atributo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="valorCAP16" text="Valor"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="atributoTXT6" text="atributo Goes Here"
								datasource="dsAtributos:atributos_rol.nombre" font="DefaultFont" />
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpValoresAtributos?p_clase_lov=" name="valorTE11"
								size="40" maxlength="255"
								datasource="dsAtributos:atributos_entidad.valor"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
