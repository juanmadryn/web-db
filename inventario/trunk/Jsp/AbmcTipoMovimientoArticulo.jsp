<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.AbmcTipoMovimientoArticuloController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsTipoMovimiento" type="MODEL" dbprofile="inventario"
		model="inventario.models.TipoMovimientoArticuloModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Tipos Movimiento de Artículo" qbebuilder="dsQBE">
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
						caption="Editar Tipo de Movimiento" width="100%" datasource="dsTipoMovimiento"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="tipoCAP12" text="Nombre"
									font="ColumnCaptionFont" />
								<td><salmon:input type="text" name="nombreTE5" size="20"
									maxlength="90" datasource="dsTipoMovimiento:tipo_movimiento_articulo.nombre"></salmon:input>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP16" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE6"
									size="40" maxlength="255"
									datasource="dsTipoMovimiento:tipo_movimiento_articulo.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP18"
									text="Observaciones" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE8"
									size="40" maxlength="255"
									datasource="dsTipoMovimiento:tipo_movimiento_articulo.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="positivoCAP19"
									text="¿Suma al stock del artículo?" font="ColumnCaptionFont" /></td>
								<td><salmon:input name="positivoTE9" type="select"
									datasource="dsTipoMovimiento:tipo_movimiento_articulo.positivo">
										<salmon:option display="Si" key="V"></salmon:option>
										<salmon:option display="No" key="F"></salmon:option>
									</salmon:input></td>
							</tr>							
							<tr>
								<td><salmon:text name="reservaCAP20"
									text="¿Genera reserva?" font="ColumnCaptionFont" /></td>
								<td><salmon:input name="reservaTE10" type="select"
									datasource="dsTipoMovimiento:tipo_movimiento_articulo.reserva">									
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
			datasource="dsTipoMovimiento" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsTipoMovimiento">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP12" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observacionesCAP13" text="Observaciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="positivoCAP13" text="Positivo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservaCAP13" text="Reserva"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="tipo_movimiento_articulo.nombre Goes Here" font="DefaultFont"
								datasource="dsTipoMovimiento:tipo_movimiento_articulo.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="tipo_movimiento_articulo.descripcion Goes Here" font="DefaultFont"
								datasource="dsTipoMovimiento:tipo_movimiento_articulo.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observacionesTXT9"
								text="tipo_movimiento_articulo.observaciones Goes Here" font="DefaultFont"
								datasource="dsTipoMovimiento:tipo_movimiento_articulo.observaciones" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="positivoXT9"
								text="tipo_movimiento_articulo.positivo Goes Here" font="DefaultFont"
								datasource="dsTipoMovimiento:tipo_movimiento_articulo.positivo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservaTXT9"
								text="tipo_movimiento_articulo.reserva Goes Here" font="DefaultFont"
								datasource="dsTipoMovimiento:tipo_movimiento_articulo.reserva" />
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
