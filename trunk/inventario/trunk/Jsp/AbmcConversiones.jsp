<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.AbmcConversionesController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="CONTAINS"
			columns="articulos.nombre,articulos.descripcion" />
	</salmon:datasource>
	<salmon:datasource name="dsConversiones" type="MODEL"
		dbprofile="inventario" model="inventario.models.ConversionesModel"
		autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Conversiones de unidades de medida" qbebuilder="dsQBE">
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
						caption="Editar conversión" width="100%"
						datasource="dsConversiones"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="articulo1" text="Artículo"
									font="TableHeadingFont" />
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpArticulos" name="articulo2" size="10"
									maxlength="15" datasource="dsConversiones:articulos.nombre"
									descriptiondatasource="dsConversiones:articulos.descripcion"
									popupheight="450" popupwidth="600" usepopup="true"
									showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="articulo_unidad_medida1"
									text="Unidad patrón" font="TableHeadingFont" /> <salmon:td>
									<salmon:input type="select" name="articulo_unidad_medida2">
										<salmon:option display="abc" key="123"
											table="inventario.unidades_medida"
											keycolumn="unidades_medida.unidad_medida_id"
											displaycolumn="unidades_medida.nombre" nulloption="true"></salmon:option>
									</salmon:input>
								</salmon:td>
							</tr>
							<tr>
								<td><salmon:text name="unidad_medida1"
									text="Unidad de medida" font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="unidad_medida2"
									datasource="dsConversiones:conversiones.unidad_medida_id">
									<salmon:option display="abc" key="123"
										table="inventario.unidades_medida"
										keycolumn="unidades_medida.unidad_medida_id"
										displaycolumn="unidades_medida.nombre" nulloption="false"></salmon:option>
								</salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="factor1" text="Factor de conversión"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="factor2" size="10"
									maxlength="10" datasource="dsConversiones:conversiones.factor"></salmon:input>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="textarea" name="observaciones2"
									cols="50" rows="3"
									datasource="dsConversiones:conversiones.observaciones"></salmon:input></td>
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
			datasource="dsConversiones"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsConversiones">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo3" text="Articulo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="unidad_medida3" text="Unidad de medida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="factor3" text="Factor" font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo4" text="" font="DefaultFont"
								datasource="dsConversiones:articulos.nombre + '-' + articulos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="unidad_medida4" text="" font="DefaultFont"
								datasource="dsConversiones:unidades_medida.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="factor4" text="" font="DefaultFont"
								datasource="dsConversiones:conversiones.factor" />
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
