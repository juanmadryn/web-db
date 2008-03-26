<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.EditarOrdenCompraController" />
<jsp:include page="templateBefore.jsp" flush="TRUE"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Add DataSource definitions here -->
	<salmon:datasource name="dsOrdenesCompra" type="MODEL"
		dbprofile="inventario" model="inventario.models.OrdenesCompraModel"
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
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Orden de Compra" width="100%"
						datasource="dsOrdenesCompra" buttondisplaylocation="BELOW_TABLE"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false">
						<salmon:input name="customBUT100" type="submit" value="boton 1"
							accesskey="1" visible="False"></salmon:input>
						<salmon:input name="customBUT110" type="submit" value="boton 2"
							accesskey="2" visible="False"></salmon:input>
						<salmon:input name="customBUT120" type="submit" value="boton 3"
							accesskey="3" visible="False"></salmon:input>
						<salmon:input name="customBUT130" type="submit" value="boton 4"
							accesskey="4" visible="False"></salmon:input>
						<salmon:input name="customBUT140" type="submit" value="boton 5"
							accesskey="5" visible="False"></salmon:input>
						<salmon:input name="customBUT150" type="submit" value="boton 6"
							accesskey="6" visible="False"></salmon:input>
						<table width="100%">
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="nombre_completo_comprador2" size="30"
									datasource="dsOrdenesCompra:solicitudes_compra.user_id_solicita">
									<salmon:option display="abc" key="123"
										table="inventario.compradores" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="false"></salmon:option>
								</salmon:input></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_ordencompra1"
									text="Fecha de Orden Compra" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_ordencompra2" text=""
									displayformat="dd/MM/yyyy"
									datasource="dsOrdenesCompra:ordenes_compra.fecha"></salmon:text></td>
								<td><salmon:text name="fecha_estimada_entrega1"
									text="Fecha estimada entrega" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_estimada_entrega2" text=""
									displayformat="dd/MM/yyyy"
									datasource="dsOrdenesCompra:ordenes_compra.fecha_estimada_entrega"></salmon:text></td>
								<td><salmon:text name="fecha_entrega_completa1" text="Fecha entrega completa"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_entrega_completa2" text=""
									displayformat="dd/MM/yyyy"
									datasource="dsOrdenesCompra:ordenes_compra.fecha_entrega_completa"></salmon:text></td>
							</tr>							
							<tr>
								<td><salmon:text name="descripcion1" text="Descripcion"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="descripcion2" size="40"
									maxlength="255"
									datasource="dsOrdenesCompra:ordenes_compra.descripcion"></salmon:input></td>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="textarea" name="observaciones2"
									cols="40" rows="1" wrap="HARD" maxlength="255"
									datasource="dsOrdenesCompra:ordenes_compra.observaciones"></salmon:input></td>
								<td></td>
							</tr>
							<tr>
								<td><salmon:text name="total_solicitud1" text="Total" font="TableHeadingFont" /></td>
								<td><salmon:text name="total_solicitud2" text="total goes here"
									datasource="dsOrdenesCompra:total_orden_compra"></salmon:text></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
			<salmon:tr>
				<salmon:td>
					<table width="100%">
						<tr>
							<td><salmon:text name="observacionX1" text="OBSERVACIONES"
								font="TableHeadingFont" visible="false" /></td>
						</tr>
						<tr>
							<td><salmon:input type="textarea" name="observacionX2"
								cols="120" rows="4" wrap="HARD" size="40" maxlength="255"
								datasource="dsOrdenesCompra:observaciones" visible="false"></salmon:input></td>
						</tr>
					</table>
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
							<salmon:text name="claseArticulo3" text="Clase"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitudCompra3" text="Solicitud de Compra"
								font="TableHeadingFont" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="cantidad_solicitada1" text="Cant. solicitada"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="monto_fecha_ultima_compra1"
								text="Precio unitario" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="monto_total1" text="Total"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td/>
						<salmon:td colspan="2">
							<salmon:text name="proyecto1" text="Proyecto" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea1" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="centroCosto1" text="Centro Costo" font="TableHeadingFont" />
						</salmon:td>						
						<salmon:td/>
						<salmon:td>
							<salmon:text name="cantidadPedida1" text="Cant. pedida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td nowrap="TRUE" align="RIGHT">
							<salmon:text name="cantidadRecibida1" text="Cant. recibida"
								font="TableHeadingFont" />
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
							<salmon:text name="articulo2" text="articulo Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:articulos.nombre" />
						</salmon:td>
						<salmon:td>							
							<salmon:text name="descripcion4" text="descripcion Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.descripcion" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="claseArticulo4" text="articulo clase Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:clase_articulo.nombre" />						
						</salmon:td>
						<salmon:td>							
							<salmon:text name="solicitudCompra4" text="solicitud compra Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.solicitud_compra_id" />
						</salmon:td>
						<salmon:td>							
							<salmon:text name="cantidad_solicitada2" text="cantidad_solicitada Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.cantidad_solicitada" />
							<salmon:text name="text2" text=" - " font="DefaultFont" />
							<salmon:text name="unidadMedida" text=""								
								datasource="dsDetalleSC:detalle_sc.unidad_medida"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="monto_unitario1" size="8"
								maxlength="15" displayformat="###,###,##0.00"
								datasource="dsDetalleSC:detalle_sc.monto_unitario"></salmon:input>
							<salmon:text name="text2" text=" - " font="DefaultFont" />
							<salmon:text name="monto_fecha_ultima_compra2" text=""
								displayformat="dd/MM/aa"
								datasource="dsDetalleSC:detalle_sc.fecha_ultima_compra"></salmon:text>
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="monto_total2" text=""
								datasource="dsDetalleSC:monto_total"></salmon:text>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td/>
						<salmon:td colspan="2">
							<salmon:text name="proyecto2"
								text="proyecto Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:proyectos.nombre" />
						</salmon:td>
						<salmon:td name="tarea2">							
							<salmon:text name="tarea3" text="articulo Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:detalle_sc.tarea_id" />
							<salmon:text name="text3" text=" - "></salmon:text>
							<salmon:text name="tarea4" text=""
								datasource="dsDetalleSC:tareas_proyecto.nombre"></salmon:text>								
						</salmon:td>						
						<salmon:td>
							<salmon:text name="centroCosto2"
								text="tarea Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:centro_costo.nombre" />
						</salmon:td>
						<salmon:td/>
						<salmon:td align="LEFT">							
							<salmon:input type="text" name="cantidad_pedida2" size="8"
								maxlength="15" 
								datasource="dsDetalleSC:detalle_sc.cantidad_pedida"></salmon:input>
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="cantidad_recibida2" text=""
								datasource="dsDetalleSC:detalle_sc.cantidad_recibida"></salmon:text>
						</salmon:td>

					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="TRUE"></jsp:include>
<salmon:endpage />
