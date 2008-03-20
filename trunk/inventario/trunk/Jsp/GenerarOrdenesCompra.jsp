<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.GenerarOrdenesCompraController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">		
		<salmon:qbecriteria name="n" type="IN" columns="detalle_sc.solicitud_compra_id" />		
	</salmon:datasource>
	<salmon:datasource name="dsDetalleSC" type="MODEL"
		dbprofile="inventario" model="inventario.models.DetalleSCModel"
		autoretrieve="never">
			<select>
				<salmon:selectioncriteria fieldname="solicitudes_compra.estado" operator="EQUALS" value="'0006.0003'" connector="and" />
				<salmon:selectioncriteria fieldname="detalle_sc.orden_compra_id" operator="IS" value="null" connector="and" />				
			</select>
	</salmon:datasource>	
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->	
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox
						caption="Buscar"
						name="searchformdisplaybox1" searchbuttonvisible="false"
						addbuttonvisible="False" qbebuilder="dsQBE">
						<table width="100%">
							<tr>	
								<td/><td/><td width="40px">																			
								<td><salmon:text name="atributo1" text="Atributo" font="TableHeadingFont" /></td>
								<td><salmon:text name="valor1" text="Valor" font="TableHeadingFont" /></td>
							</tr>
							<tr>
								<td><salmon:text name="n1" text="Nro Solicitud" font="ColumnCaptionFont" /></td>
								<td><salmon:input name="n2" type="text"	datasource="dsQBE:n" /></td>
								<td width="40px">
								<td><salmon:input name="atributo2" type="text" /></td>
								<td><salmon:input name="valor2" type="text" /></td>
							</tr>
							<tr>
								<td/><td/><td width="40px">
								<td><salmon:input name="atributo3" type="text" /></td>
								<td><salmon:input name="valor3" type="text" /></td>
							</tr>
							<tr>
								<td/><td/><td width="40px">
								<td><salmon:input name="atributo4" type="text" /></td>
								<td><salmon:input name="valor4" type="text"/></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Solicitudes de Compra aprobadas" width="100%"
			datasource="dsDetalleSC" >		
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsDetalleSC">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
                     		<salmon:text name="selSolicitudCAP1" text="-X-" font="TableHeadingFont" />
                  		</salmon:td>												
						<salmon:td>
							<salmon:text name="articuloCAP3" text="Artículo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloDescCAP4" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="articuloClaseCAP12" text="Clase"	font="TableHeadingFont"/>
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroSolicitudCAP4" text="Nro. Solicitud"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaUltCompraCAP5" text="Fecha última compra"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="ordenCompraCAP5" text="Orden de Compra"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td/>
						<salmon:td colspan="2">
							<salmon:text name="proyectoCAP7" text="Proyecto"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tareaCAP8" text="Tarea"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="centroCostoCAP11" text="Centro Costo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantPedidaCAP9" text="Cantidad Pedida"
								font="TableHeadingFont" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="montoUnitCAP10" text="Monto unitario"
								font="TableHeadingFont" />
						</salmon:td>															
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
                     		<salmon:input type="checkbox" name="selSolicitudCB" checkedtruevalue="1"></salmon:input>
                  		</salmon:td>
						<salmon:td>
							<salmon:text name="articuloTXT2" text="articulo Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:articulos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloDescTXT3" text="articulo Desc Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:articulos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloClaseTXT3" text="articulo clase Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:clase_articulo.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroSolicitudTXT3" text="nro solicitud clase Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.solicitud_compra_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaUltCompraTXT4" text="Fecha última compra Goes Here"
								font="DefaultFont" displayformat="dd/MM/yyyy"
								datasource="dsDetalleSC:detalle_sc.fecha_ultima_compra" />
						</salmon:td>
						<salmon:td>
							<salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpOrdenesCompra" name="ordenCompraINP5" size="5"	maxlength="10"
									datasource="dsDetalleSC:detalle_sc.orden_compra_id" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>						
						<salmon:td/>						
						<salmon:td colspan="2">
							<salmon:text name="proyectoTXT6"
								text="proyecto Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:proyectos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tareaTXT7"
								text="tarea Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:tareas_proyecto.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="centroCostoTXT8"
								text="tarea Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:centro_costo.nombre" />
						</salmon:td>
						<salmon:td>
							<!--  <salmon:text name="centroCostoTXT8"
								text="tarea Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.cantidad_solicitada" />
							<salmon:text name="sep1" text=" - " font="DefaultFont"/> -->
							<salmon:input type="text" maxlength="15" size="10" name="cantPedidaINP8" value="Cantidad Pedida Goes Here"
								font="DefaultFont" datasource="dsDetalleSC:detalle_sc.cantidad_pedida" />
						</salmon:td>						
						<salmon:td>
							<salmon:input type="text" maxlength="15" size="10" name="montoUnitINP9" value="Monto Unitario Goes Here"
								font="DefaultFont" datasource="dsDetalleSC:detalle_sc.monto_unitario"/>
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