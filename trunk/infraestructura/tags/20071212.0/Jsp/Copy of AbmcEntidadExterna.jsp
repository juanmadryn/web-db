<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="infraestructura.controllers.AbmcEntidadExternaController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsEntidad" type="MODEL"
		model="infraestructura.models.EntidadExternaModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsRoles" type="MODEL"
		model="infraestructura.models.RolesEntidadModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Entidades Externas (Clientes, Proveedores, etc.)"
						addbuttonvisible="False" qbebuilder="dsQBE"
						addbuttonvisible="true">
						<table width="100%">
							<tr>
								<td><salmon:text name="buscarCAP5" text="Buscar"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="buscarTE3" size="20"
									maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Editar Entidad Externa" width="100%"
						datasource="dsEntidad" listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="codigoCAP12" text="Código"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="codigoTE4" size="6"
									maxlength="15" datasource="dsEntidad:entidad_externa.codigo"></salmon:input>
								<salmon:input type="text" name="nombreTE6" size="30"
									maxlength="90" datasource="dsEntidad:entidad_externa.nombre"></salmon:input>
								</td>
								<td><salmon:text name="idCAP42" text="ID"
									font="ColumnCaptionFont" /> <salmon:text name="idTXT43"
									text="id Goes Here" font="DefaultFont"
									datasource="dsEntidad:entidad_externa.entidad_id" /></td>
								<td><salmon:text name="activoCAP44" text="Activo"
									font="ColumnCaptionFont" /> <salmon:text name="activoTXT45"
									text="activo Goes Here" font="DefaultFont"
									datasource="dsEntidad:entidad_externa.activo" /></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP13" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE5"
									size="40" maxlength="255"
									datasource="dsEntidad:entidad_externa.descripcion"></salmon:input></td>
								<td><salmon:text name="observacionesCAP15" text="Obs."
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE7"
									size="40" maxlength="255"
									datasource="dsEntidad:entidad_externa.observaciones"></salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:table name="table3" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:listformdisplaybox name="listformdisplaybox1"
						mode="Display_single_page"
						caption="Altas/Bajas/Modificaciones/Consultas" width="100%"
						datasource="dsEntidad"
						searchformdisplaybox="searchformdisplaybox1">
						<salmon:datatable name="datatable1" width="100%"
							datasource="dsEntidad" rowsperpage="3">
							<salmon:datatableheader>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="idCAP9" text="ID" font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="codigoCAP10" text="Código"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="nombreCAP11" text="Nombre"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="activoCAP32" text="Activo"
											font="TableHeadingFont" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatableheader>
							<salmon:datatablerows>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="idTXT6" text="id Goes Here"
											font="DefaultFont"
											datasource="dsEntidad:entidad_externa.entidad_id" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="codigoTXT7" text="codigo Goes Here"
											font="DefaultFont"
											datasource="dsEntidad:entidad_externa.codigo" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="nombreTXT8" text="nombre Goes Here"
											font="DefaultFont"
											datasource="dsEntidad:entidad_externa.nombre" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="activoTXT32" text="activo Goes Here"
											font="DefaultFont"
											datasource="dsEntidad:entidad_externa.activo" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatablerows>
						</salmon:datatable>
					</salmon:listformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:listformdisplaybox name="listformdisplaybox2"
						mode="Display_single_page" caption="Roles de la Entidad"
						width="100%" datasource="dsRoles" addbuttonvisible="True"
						savebuttonvisible="True">
						<salmon:datatable name="datatable2" width="100%"
							datasource="dsRoles" rowsperpage="5">
							<salmon:datatableheader>
								<salmon:tr>
							        <salmon:td>
	          							<salmon:text name="selCAP60" text="-X-" font="TableHeadingFont" />
	        						</salmon:td>
									<salmon:td>
										<salmon:text name="rolCAP50" text="Rol"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="vigenciaCAP21" text="Vigencia"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="activoCAP71" text="Activo"
											font="TableHeadingFont" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatableheader>
							<salmon:datatablerows>
								<salmon:tr>
							        <salmon:td>
           								<salmon:input type="checkbox" name="seleccion" checkedtruevalue="1"></salmon:input> 
	        						</salmon:td>
									<salmon:td width="250">
										<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
											lookupurl="%LkpRolesEntidad" name="rolTE51" size="4"
											maxlength="4"
											descriptiondatasource="dsRoles:rol_entidad.nombre"
											datasource="dsRoles:roles_entidad.rol" popupheight="450"
											popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup>
									</salmon:td>
									<salmon:td>
										<salmon:input name="desdeTE41" type="text" size="10"
											maxlength="10" displayformat="dd/MM/yyyy"
											datasource="dsRoles:roles_entidad.desde"></salmon:input>
										<salmon:input name="hastaTE42" type="text" size="10"
											maxlength="10" displayformat="dd/MM/yyyy"
											datasource="dsRoles:roles_entidad.hasta"></salmon:input>
									</salmon:td>
									<salmon:td>
										<salmon:text name="activoTXT71" text="activo Goes Here"
											font="DefaultFont"
											datasource="dsRoles:roles_entidad.activo" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatablerows>
						</salmon:datatable>
					</salmon:listformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
