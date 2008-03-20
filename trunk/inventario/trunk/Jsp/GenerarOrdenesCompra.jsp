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
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsDetalleSC" type="MODEL"
		dbprofile="inventario" model="inventario.models.DetalleSCModel"
		autoretrieve="always">
			<salmon:selection>
				<salmon:selectioncriteria fieldname="solicitudes_compra.estado" operator="EQUALS" value="'0006.0003'" connector="and" />
				<salmon:selectioncriteria fieldname="detalle_sc.orden_compra_id" operator="IS" value="null" />				
			</salmon:selection>
	</salmon:datasource>	
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->	
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
						<salmon:td/>
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
						<salmon:td>
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
						<salmon:td/>
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
						<salmon:td>
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