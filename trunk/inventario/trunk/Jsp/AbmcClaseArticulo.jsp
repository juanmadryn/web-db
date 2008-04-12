<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.AbmcClaseArticuloController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definici�n de DataSource aqu� -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsClase" type="MODEL" dbprofile="inventario"
		model="inventario.models.ClaseArticuloModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar c�digo de la p�gina aqu� -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Clases de Art�culos" qbebuilder="dsQBE">
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
						caption="Editar Clase" width="100%" datasource="dsClase"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="tipoCAP12" text="Nombre"
									font="ColumnCaptionFont" />
								<td><salmon:input type="text" name="nombreTE5" size="20"
									maxlength="15" datasource="dsClase:clase_articulo.nombre"></salmon:input>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP16" text="Descripci�n"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE6"
									size="40" maxlength="255"
									datasource="dsClase:clase_articulo.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP18"
									text="Observaciones" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="textarea" name="observacionesTE8"
									cols="50" rows="3" datasource="dsClase:clase_articulo.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="inventariableCAP20"
									text="�Gen�rico?" font="ColumnCaptionFont" /></td>
								<td><salmon:input name="inventariableTE10" type="select"
									datasource="dsClase:clase_articulo.generico">
									<salmon:option display="No" key="F"></salmon:option>
									<salmon:option display="Si" key="V"></salmon:option>									
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
			mode="Display_single_page"
			caption="Altas/Bajas/Modificaciones/Consultas" width="100%"
			datasource="dsClase" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsClase">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP12" text="Descripci�n"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observacionesCAP13" text="Observaciones"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="clase_articulo.nombre Goes Here" font="DefaultFont"
								datasource="dsClase:clase_articulo.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="clase_articulo.descripcion Goes Here" font="DefaultFont"
								datasource="dsClase:clase_articulo.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observacionesTXT9"
								text="clase_articulo.observaciones Goes Here" font="DefaultFont"
								datasource="dsClase:clase_articulo.observaciones" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de c�digo agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
