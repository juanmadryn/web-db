<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ taglib uri="/WEB-INF/birt.tld" prefix="birt"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script src="javascripts/utils.js" type="text/javascript"></script>
<salmon:page
	controller="gerencia.controllers.ConsultaMaterialCompradoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsPeriodo" type="SQL" autoretrieve="Never">
		<salmon:datasourcedef>
			<salmon:bucket name="desde" datatype="DATETIME" />
			<salmon:bucket name="hasta" datatype="DATETIME" />
		</salmon:datasourcedef>
	</salmon:datasource>
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="proyecto" type="IN"
			columns="material_comprado.proyecto" />
		<salmon:qbecriteria name="tarea" type="IN"
			columns="material_comprado.nombre_tarea" />
		<salmon:qbecriteria name="articulo" type="IN"
			columns="material_comprado.articulo_nombre" />
	</salmon:datasource>
	<salmon:datasource name="dsMateriales" type="MODEL"
		dbprofile="gerencia" model="gerencia.models.MaterialCompradoModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsTareas" type="MODEL" dbprofile="proyectos"
		model="proyectos.models.TareasProyectoModel" autoretrieve="Always">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox
						caption="Consulta de Materiales Comprados"
						name="searchformdisplaybox1" buttondisplaylocation="BELOW_TABLE"
						searchbuttonvisible="true" addbuttonvisible="False"
						qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="proyecto1" text="Proyecto"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProyectos" name="proyecto2" size="15"
									maxlength="15" popupheight="450" popupwidth="500"
									usepopup="TRUE" showdescription="TRUE"
									datasource="dsQBE:proyecto"></salmon:lookup></td>
								<td width="55"></td>
								<td><salmon:text name="tarea1" text="Tarea"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="tarea_proyecto1"
									datasource="dsQBE:tarea">
									<salmon:option display="" key="" nulloption="true"
										reloadDropDownInEveryPageRequest="false"></salmon:option>
								</salmon:input> <salmon:input type="text" name="tareaId" style="display:none"
									font="DefaultFont" datasource="dsQBE:tarea" /></td>
							</tr>
							<tr>
								<td><salmon:text name="fechadesde1" text="Fecha desde"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechadesde2" size="10"
									datasource="dsPeriodo:desde" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
								<td width="55"></td>
								<td><salmon:text name="fechahasta1" text="Fecha hasta"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsPeriodo:hasta" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="articulo1" text="Artículo"
									font="TableHeadingFont" /></td>
								<td colspan="4"><salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpArticulos" name="articulo2" size="20"
									maxlength="15" datasource="dsQBE:articulo" popupheight="450"
									popupwidth="600" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false"
						caption="Detalle" datasource="dsMateriales" width="100%"
						listformdisplaybox="listformdisplaybox1" headerbgcolor="#fffd99"
						bgcolor="fffdce">
						<salmon:table width="100%" name="table1">
							<tr>
								<td><salmon:text name="articulo_completo1"
									text="Artículo completo: " font="TableHeadingFont" /></td>
								<td><salmon:text name="articulo_completo2" text=""
									datasource="dsMateriales:material_comprado.articulo_nombre + ' - ' + material_comprado.articulo_descripcion + ' - ' + material_comprado.descripcion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cantidad_solicitada1"
									text="Cant. solicitada"
									datasource="dsMateriales:'Cant. solicitada ('+material_comprado.um_descripcion+')'"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="cantidad_solicitada2"
									datasource="dsMateriales:material_comprado.cantidad_solicitada"
									text=""></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cantidad_pedida1"
									text="Cant. pedida"
									datasource="dsMateriales:'Cant. pedida ('+material_comprado.um_descripcion+')'"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="cantidad_pedida2"
									datasource="dsMateriales:material_comprado.cantidad_pedida"
									text=""></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cantidad_recibida1"
									text="Cant. recibida"
									datasource="dsMateriales:'Cant. recibida ('+material_comprado.um_descripcion+')'"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="cantidad_recibida2"
									datasource="dsMateriales:material_comprado.cantidad_recibida"
									text=""></salmon:text></td>
							</tr>
						</salmon:table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			datasource="dsMateriales"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsMateriales">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="marca1" text="-X-" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha1" text="Fecha" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proyecto3" text="Proyecto"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea3" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo3" text="Articulo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="monto1" text="$$$" font="TableHeadingFont" />
						</salmon:td>

					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="marca2" text="-" font="DefaultFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha2" text="fecha Goes Here"
								font="DefaultFont"
								datasource="dsMateriales:material_comprado.fecha_aprobacion_oc"
								displayformatlocalekey="DateFormat" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proyecto4" text="proyecto Goes Here"
								font="DefaultFont"
								datasource="dsMateriales:material_comprado.proyecto + ' - ' + material_comprado.nombre_proyecto" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea4" text="tarea Goes Here"
								font="DefaultFont"
								datasource="dsMateriales:material_comprado.nombre_tarea + ' - '+material_comprado.descripcion_tarea" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo4" text="proyecto Goes Here"
								font="DefaultFont"
								datasource="dsMateriales:material_comprado.articulo_nombre + ' - ' + material_comprado.articulo_descripcion" />
						</salmon:td>
						<salmon:td align="right">
							<salmon:text name="monto2" text="proyecto Goes Here"
								font="DefaultFont"
								datasource="dsMateriales:material_comprado.monto_unitario * material_comprado.cantidad_pedida"
								displayformatlocalekey="CurrencyFormat" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
				<salmon:datatablefooter>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
						<salmon:td align="right">
							<salmon:text name="monto_total" text="MONTO TOTAL:"
								font="TableHeadingFont" />
								&nbsp;
							<salmon:text name="monto_total1" text="proyecto Goes Here"
								font="DefaultFont" displayformatlocalekey="CurrencyFormat" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablefooter>
			</salmon:datatable>

		</salmon:listformdisplaybox>
	</salmon:box>
	<jsp:include page="TablaProyectosTareas.jsp" flush="true"></jsp:include>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="example.jsp" flush="true"></jsp:include>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />