<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="infraestructura.controllers.CircuitosEstadosController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsCircuito" type="MODEL"
		model="infraestructura.models.CircuitoEstadoModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsEstado" type="MODEL"
		model="infraestructura.models.EstadoModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Circuitos Administrativos" addbuttonvisible="False"
						qbebuilder="dsQBE" addbuttonvisible="true">
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
						caption="Editar Circuito Administrativo" width="100%"
						datasource="dsCircuito" listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="circuitoCAP12" text="Circuito"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="circuitoTE4" size="4"
									maxlength="4"
									datasource="dsCircuito:circuitos_estados.circuito"></salmon:input>
								<salmon:input type="text" name="nombreTE6" size="30"
									maxlength="90" datasource="dsCircuito:circuitos_estados.nombre"></salmon:input>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP13" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE5"
									size="40" maxlength="255"
									datasource="dsCircuito:circuitos_estados.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP15" text="Obs."
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE7"
									size="40" maxlength="255"
									datasource="dsCircuito:circuitos_estados.observaciones"></salmon:input></td>
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
			datasource="dsCircuito" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsCircuito" rowsperpage="3">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="circuitoCAP9" text="Circuito"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP10" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="circuitoTXT6"
								text="circuitos_estados.circuito Goes Here" font="DefaultFont"
								datasource="dsCircuito:circuitos_estados.circuito" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT8"
								text="circuitos_estados.nombre Goes Here" font="DefaultFont"
								datasource="dsCircuito:circuitos_estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT7"
								text="circuitos_estados.descripcion Goes Here"
								font="DefaultFont"
								datasource="dsCircuito:circuitos_estados.descripcion" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box3" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page"
			caption="Estados del Circuito Administrativo" width="100%"
			datasource="dsEstado" addbuttonvisible="True"
			detailformdisplaybox="detailformdisplaybox2">
			<salmon:datatable name="datatable2" width="100%"
				datasource="dsEstado" rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="estadoCAP20" text="Estado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP21" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP19" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="estadoTXT17" text="estados.estado Goes Here"
								font="DefaultFont" datasource="dsEstado:estados.estado" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT18" text="estados.nombre Goes Here"
								font="DefaultFont" datasource="dsEstado:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT16"
								text="estados.descripcion Goes Here" font="DefaultFont"
								datasource="dsEstado:estados.descripcion" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box4" width="100%">
		<salmon:detailformdisplaybox name="detailformdisplaybox2"
			caption="Editar Estado" width="100%" datasource="dsEstado"
			listformdisplaybox="listformdisplaybox2">
			<table width="100%">
				<tr>
					<td><salmon:text name="estadoCAP22" text="Estado"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="estadoTE8" size="8"
						maxlength="15" datasource="dsEstado:estados.estado"></salmon:input></td>
					<td><salmon:text name="nombreCAP23" text="Nombre"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="nombreTE9" size="30"
						maxlength="90" datasource="dsEstado:estados.nombre"></salmon:input></td>
				</tr>
				<tr>
					<td><salmon:text name="descripcionCAP24" text="Descripción"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="descripcionTE10" size="40"
						maxlength="255" datasource="dsEstado:estados.descripcion"></salmon:input></td>
					<td><salmon:text name="observacionesCAP25" text="Obs."
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="observacionesTE11"
						size="40" maxlength="255"
						datasource="dsEstado:estados.observaciones"></salmon:input></td>
				</tr>
			</table>
		</salmon:detailformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />