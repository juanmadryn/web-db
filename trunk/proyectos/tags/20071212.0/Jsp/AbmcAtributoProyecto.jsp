<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="proyectos.controllers.AbmcAtributoProyectoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsAttr" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.AtributosRolModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Atributo para Proyectos"
						qbebuilder="dsQBE">
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
						caption="Editar Atributo de Proyecto" width="100%"
						datasource="dsAttr" listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="nombreCAP23" text="Nombre"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreTE9" size="30"
									maxlength="90" datasource="dsAttr:atributos_rol.nombre"></salmon:input></td>
								<td><salmon:text name="vigenciaCAP40" text="Vigencia"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="desdeTE41" type="text" size="10"
									maxlength="10" displayformat="dd/MM/yyyy"
									datasource="dsAttr:atributos_rol.desde"></salmon:input> <salmon:input
									name="hastaTE42" type="text" size="10" maxlength="10"
									displayformat="dd/MM/yyyy"
									datasource="dsAttr:atributos_rol.hasta"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP24" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE10"
									size="30" maxlength="255"
									datasource="dsAttr:atributos_rol.descripcion"></salmon:input></td>
								<td><salmon:text name="observacionesCAP25" text="Obs."
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE11"
									size="40" maxlength="255"
									datasource="dsAttr:atributos_rol.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="etiquetaCAP62"
									text="Etiqueta (Grupo)" font="ColumnCaptionFont" /></td>
								<td><salmon:input name="etiquetaTE63" type="select"
									size="30"
									datasource="dsAttr:atributos_rol.clase_atributo_rol_id">
									<salmon:option display="123" key="abc" displaycolumn="etiqueta"
										keycolumn="clase_atributo_rol_id" nulloption="True"
										table="clase_atributo_rol"></salmon:option>
								</salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="obligatorioCAP51" text="Obligatorio"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="obligatorioCB1" type="checkbox"
									checkedtruevalue="V" checkedfalsevalue="F" checked="false"
									datasource="dsAttr:atributos_rol.obligatorio"></salmon:input></td>
								<td><salmon:text name="tipoDatoCAP52" text="Tipo de Dato"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="tipoDatoTE53" type="select"
									datasource="dsAttr:atributos_rol.tipo_dato">
									<salmon:option display="Entero" key="entero"></salmon:option>
									<salmon:option display="Real" key="real"></salmon:option>
									<salmon:option display="Texto" key="texto"></salmon:option>
									<salmon:option display="Fecha" key="fecha"></salmon:option>
									<salmon:option display="Lógico" key="logico"></salmon:option>
								</salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Atributos del Rol de Entidad"
			width="100%" datasource="dsAttr" addbuttonvisible="True"
			detailformdisplaybox="detailformdisplaybox1" searchformdisplaybox="searchformdisplaybox1" >
			<salmon:datatable name="datatable1" width="100%" datasource="dsAttr"
				rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreCAP20" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP19" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="desdeCAP21" text="Desde"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="hastaCAP22" text="hasta"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreTXT18" text="nombre Goes Here"
								font="DefaultFont" datasource="dsAttr:atributos_rol.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT16" text="descripcion Goes Here"
								font="DefaultFont" datasource="dsAttr:atributos_rol.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="desdeTXT41" text="Desde Goes Here"
								displayformat="dd/MM/yyyy" font="DefaultFont"
								datasource="dsAttr:atributos_rol.desde" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="hastaTXT42" text="Hasta Goes Here"
								displayformat="dd/MM/yyyy" font="DefaultFont"
								datasource="dsAttr:atributos_rol.hasta" />
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
