<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.AbmcSolicitudCompraController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Add DataSource definitions here -->
	<salmon:datasource name="dsSolicitudCompra" type="MODEL"
		dbprofile="inventario" model="inventario.models.SolicitudCompraModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsDetalleSC" type="MODEL"
		dbprofile="inventario" model="inventario.models.DetalleSCModel"
		autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Add page code here -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top" width="50%">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Solicitud de compra" width="100%"
						datasource="dsSolicitudCompra" buttondisplaylocation="BELOW_TABLE"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false">
						<table width="100%">
							<tr>
								<td><salmon:text name="n1" text="Nº"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="n2" text="" font="ColumnCaptionFont"
									datasource="dsSolicitudCompra:solicitudes_compra.solicitud_compra_id" />
								</td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_solicitante1"
									text="Solicitante" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_solicitante2"
									text=""
									datasource="dsSolicitudCompra:nombre_completo_solicitante"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_comprador2" text=""
									datasource="dsSolicitudCompra:nombre_completo_comprador"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_solicitud1"
									text="Fecha de solicitud" displayformat="dd/MM/yyyy" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_solicitud2" text=""
									datasource="dsSolicitudCompra:solicitudes_compra.fecha_solicitud"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_aprobacion1"
									text="Fecha de aprobación" displayformat="dd/MM/yyyy" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_aprobacion2" text=""
									datasource="dsSolicitudCompra:solicitudes_compra.fecha_aprobacion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_oc1" text="Fecha de OC"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_oc2" text="" displayformat="dd/MM/yyyy"
									datasource="dsSolicitudCompra:solicitudes_compra.fecha_oc"></salmon:text></td>
							</tr>
							<td><salmon:text name="proyecto1" text="Proyecto"
								font="TableHeadingFont" /></td>
							<salmon:td name="proyectoTableTD">
								<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProyectos" name="proyecto2" size="15"
									maxlength="15"
									datasource="dsSolicitudCompra:proyectos.proyecto"
									descriptiondatasource="dsSolicitudCompra:proyectos.nombre"
									popupheight="450" popupwidth="500" usepopup="true"
									showdescription="True"></salmon:lookup>
							</salmon:td>
							<tr>
								<td><salmon:text name="descripcion1" text="Descripcion"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcion2" size="40"
									maxlength="255"
									datasource="dsSolicitudCompra:solicitudes_compra.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="textarea" name="observaciones2" cols="40" rows="3" wrap="HARD"
									size="40" maxlength="255"
									datasource="dsSolicitudCompra:solicitudes_compra.observaciones"></salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
				<salmon:td width="50%">
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page" caption="Detalle" width="100%"
			datasource="dsDetalleSC" addbuttonvisible="false"
			savebuttonvisible="false">
			<salmon:datatable name="datatable2" width="100%"
				datasource="dsDetalleSC" rowsperpage="10">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="seleccion_detalle1" text="-X-"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloId1" text="Articulo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcion3" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_solicitada1"
								text="Cantidad solicitada" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="monto_unitario1" text="Monto unitario"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="monto_total1" text="Total"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="cantidadPedida1" text="Cantidad pedida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidadRecibida1" text="Cantidad recibida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea1" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observaciones3" text="Obsevaciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>							
						</salmon:td>
						<salmon:td>							
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:input type="checkbox" name="seleccion_detalle2"
								checkedtruevalue="1"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="articulo2" font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.articulo_id" />
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="descripcion4" size="30"
								maxlength="255" datasource="dsDetalleSC:detalle_sc.descripcion"></salmon:input>
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:input type="text" name="cantidad_solicitada2" size="15"
								maxlength="15" displayformat="##0"
								datasource="dsDetalleSC:detalle_sc.cantidad_solicitada"></salmon:input>
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:input type="text" name="monto_unitario2" size="15"
								maxlength="15" displayformat="###,###,##0.00"
								datasource="dsDetalleSC:detalle_sc.monto_unitario"></salmon:input>
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="monto_total2" text="" 
								datasource="dsDetalleSC:monto_total" ></salmon:text>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="cantidad_pedida2" text=""
								datasource="dsDetalleSC:detalle_sc.cantidad_pedida"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_recibida2" text=""
								datasource="dsDetalleSC:detalle_sc.cantidad_recibida"></salmon:text>
						</salmon:td>
						<salmon:td name="tarea2">
							<salmon:input type="select"
									name="tarea3" size="15" datasource="dsDetalleSC:tarea_id"
									maxlength="15">
									<salmon:option display="abc" key="123"
										table="proyectos.tareas_proyecto" keycolumn="tarea_id"
										displaycolumn="nombre" nulloption="true"></salmon:option>
								</salmon:input>													
						</salmon:td>
						<salmon:td>
						<salmon:input type="text" name="observaciones4" size="15"
								maxlength="255"
								datasource="dsDetalleSC:detalle_sc.observaciones"></salmon:input>
						</salmon:td>
						<salmon:td></salmon:td>
						<salmon:td></salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
