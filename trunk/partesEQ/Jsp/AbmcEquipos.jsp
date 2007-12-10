<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="partesEQ.controllers.AbmcEquiposController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí­ -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsEquipo" type="MODEL" dbprofile="partesEQ"
		model="partesEQ.models.EquipoModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Equipos" qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="buscarCAP5" text="Buscar"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="buscarTE3" size="30"
									maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Editar equipo" width="100%" datasource="dsEquipo"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="equipoCAP12" text="Id del equipo"
									font="ColumnCaptionFont" />
								<td><salmon:text name="nombreTE5" text=""
								datasource="dsEquipo:equipos.equipo_id"></salmon:text>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="tipoCAP14" text="Tipo de equipo"
									font="ColumnCaptionFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpTiposEquipos" name="tipoTE1" size="20"
									maxlength="15" datasource="dsEquipo:equipos.tipo_equipo"
									popupheight="450" popupwidth="500" usepopup="true"
									showdescription="False"></salmon:lookup>
								<salmon:text name="espacioCAP15" text=" "
									font="ColumnCaptionFont" />
								<salmon:text name="equipoCAP15" text=""
									datasource="dsEquipo:tipo_equipo.nombre"
									font="ColumnCaptionFont" />
								</td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCAP16" text="Nombre"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreTE6" size="40"
									maxlength="90" datasource="dsEquipo:equipos.nombre"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP18" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE6"
									size="40" maxlength="255"
									datasource="dsEquipo:equipos.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP20"
									text="Observaciones" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE8"
									size="40" maxlength="255"
									datasource="dsEquipo:equipos.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="codigoCAP22"
									text="Código de inventario" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="codigoTE10" size="30"
									maxlength="25" datasource="dsEquipo:equipos.codigo_inventario"></salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page"
			caption="Altas/Bajas/Modificaciones/Consultas" width="100%"
			datasource="dsEquipo" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsEquipo">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="equipoCAP10" text="Id Equipo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tipoCAP11" text="Tipo de equipo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP12" text="Nombre de equipo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP13" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="codigoCAP14" text="Código de inventario"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="equipoIdTXT6"
								text="equipos.tipo_equipo Goes Here" font="DefaultFont"
								datasource="dsEquipo:equipos.equipo_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tipoIdTXT7"
								text="equipos.tipo_equipo Goes Here" font="DefaultFont"
								datasource="dsEquipo:equipos.tipo_equipo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT8" text="equipos.nombre Goes Here"
								font="DefaultFont" datasource="dsEquipo:equipos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT9"
								text="equipos.descripcion Goes Here" font="DefaultFont"
								datasource="dsEquipo:equipos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="codigoInvTXT10"
								text="equipos.codigo_inventario Goes Here" font="DefaultFont"
								datasource="dsEquipo:equipos.codigo_inventario" />
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
