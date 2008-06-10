<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script type="text/javascript">
<!--
function CheckAll(checked) {  
	for ( var i=0; i<document.forms['PageForm'].elements.length; i++) { 
		var e = document.forms['PageForm'].elements[i]; 
			if (e.type=='checkbox') { 
				e.checked = checked; 
			} 
	} 
} 
//-->
</script>
<salmon:page
	controller="inventario.controllers.AbmcSolicitudCompraController" />
<jsp:include page="templateBefore.jsp" flush="TRUE"></jsp:include>
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
	<salmon:datasource name="dsInstanciasAprobacion" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.InstanciasAprobacionModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsAuditoria" type="MODEL"
		dbprofile="inventario"
		model="infraestructura.models.AuditaEstadosCircuitosModel"
		autoretrieve="Never">		
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Add page code here -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top" colspan="2">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Solicitud de Materiales" width="100%"
						datasource="dsSolicitudCompra" buttondisplaylocation="IN_HEADER"
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
								<td><salmon:text name="nombre_completo_solicitante1"
									text="Solicitante" font="TableHeadingFont" /></td>
								<td><salmon:input type="select"
									name="nombre_completo_solicitante2" size="30"
									datasource="dsSolicitudCompra:solicitudes_compra.user_id_solicita">
									<salmon:option display="abc" key="123"
										table="inventario.solicitantes" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="false"></salmon:option>
								</salmon:input></td>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_comprador2" text=""
									datasource="dsSolicitudCompra:nombre_completo_comprador"></salmon:text></td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_solicitud1"
									text="Fecha de solicitud" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_solicitud2" text=""
									displayformatlocalekey="DateTimeFormat"
									datasource="dsSolicitudCompra:solicitudes_compra.fecha_solicitud"></salmon:text></td>
								<td><salmon:text name="fecha_aprobacion1"
									text="Fecha de aprobación" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_aprobacion2" text=""
									displayformatlocalekey="DateTimeFormat"
									datasource="dsSolicitudCompra:solicitudes_compra.fecha_aprobacion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="proyecto1" text="Proyecto"
									font="TableHeadingFont" /></td>
								<salmon:td name="proyectoTableTD">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProyectos" name="proyecto2" size="15"
										maxlength="15"
										datasource="dsSolicitudCompra:proyectos.proyecto"
										descriptiondatasource="dsSolicitudCompra:proyectos.nombre"
										popupheight="450" popupwidth="500" usepopup="TRUE"
										showdescription="TRUE"></salmon:lookup>
								</salmon:td>
								<td><salmon:text name="centro_costo1"
									text="Centro de costo" font="TableHeadingFont" /></td>
								<salmon:td name="centroCostoTableTD">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCentroCosto" name="centro_costo2" size="15"
										maxlength="15"
										datasource="dsSolicitudCompra:solicitudes_compra.centro_costo_id"
										descriptiondatasource="dsSolicitudCompra:centro_costo.nombre"
										popupheight="450" popupwidth="500" usepopup="TRUE"
										showdescription="TRUE"></salmon:lookup>
								</salmon:td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td colspan="3"><salmon:input type="textarea"
									name="observaciones2" cols="80" rows="3" wrap="HARD"
									maxlength="255"
									datasource="dsSolicitudCompra:solicitudes_compra.observaciones"></salmon:input></td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:text name="total_solicitud1" text="Total"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="total_solicitud2" text=""
									displayformatlocalekey="CurrencyFormatConSigno"
									datasource="dsSolicitudCompra:total_solicitud_compra"></salmon:text></td>
								<td></td>
								<td><salmon:a href="" target="_blank"
									name="imprimirSolicitudCompraBUT1"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT1"
										src="%ImageDirectory/logo_excel.gif" height="25"
										srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:text name="espacio" text=" " font="TableHeadingFont" />
								<salmon:a href="" target="_blank"
									name="imprimirSolicitudCompraBUT2"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT2" src="%ImageDirectory/pdf.jpg"
										height="25" srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:text name="espacio2" text=" " font="TableHeadingFont" />
								<salmon:a href="ListaFirmantes.jsp" target="_blank"
									name="verFirmantes"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:text name="verFirmantesTXT2" text="Firmantes" />
								</salmon:a></td>
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
								cols="60" rows="4" wrap="HARD" size="40" maxlength="255"
								datasource="dsSolicitudCompra:observaciones" visible="false"></salmon:input></td>
						</tr>
					</table>
				</salmon:td>
				<salmon:td>
					<salmon:datatable name="datatable1" width="100%"
						datasource="dsAuditoria" rowsperpage="3" rowsperpageselector="false">
						<salmon:datatableheader>
							<salmon:tr>
								<salmon:td>
									<salmon:text name="fecha2" text="Fecha" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="usuario" text="Usuario"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="accion1" text="Accion"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="observaciones5" text="Observaciones"
										font="TableHeadingFont" />
								</salmon:td>
							</salmon:tr>
						</salmon:datatableheader>
						<salmon:datatablerows>
							<salmon:tr>
								<salmon:td>
									<salmon:text name="fecha3"
										text="audita_estados_circuitos.fecha Goes Here"
										font="DefaultFont" displayformat="dd/MM/yyyy HH:mm"
										datasource="dsAuditoria:audita_estados_circuitos.fecha" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="usuario2"
										text="website_user.nombre_completo Goes Here"
										font="DefaultFont"
										datasource="dsAuditoria:website_user.nombre_completo" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="accion2"
										text="acciones_apps.nombre Goes Here" font="DefaultFont"
										datasource="dsAuditoria:acciones_apps.nombre" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="observaciones6" text="" font="DefaultFont"
										datasource="dsAuditoria:audita_estados_circuitos.observaciones" />
								</salmon:td>
							</salmon:tr>
						</salmon:datatablerows>
					</salmon:datatable>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page" caption="Detalle" width="100%"
			datasource="dsDetalleSC" addbuttonvisible="false"
			savebuttonvisible="true" savebuttoncaption="Eliminar"
			autocreatelink="false">
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
							<salmon:text name="unidad_medida1" text="UM"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_solicitada1" text="Cantidad"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td nowrap="TRUE">
							<salmon:text name="monto_fecha_ultima_compra1"
								text="Precio unitario" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td />
						<salmon:td align="RIGHT">
							<salmon:text name="monto_total1" text="Total"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td>
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea1" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td colspan="2">
							<salmon:text name="observaciones3" text="Obsevaciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="ccHeaderTd" nowrap="TRUE">
							<salmon:text name="cc1" text="Cotización" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td nowrap="TRUE" name="cantidadPedidaHeaderTd">
							<salmon:text name="cantidadPedida1" text="Cant. pedida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="ocHeaderTd" nowrap="TRUE">
							<salmon:text name="oc1" text="O.C." font="TableHeadingFont" />
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
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpArticulos" name="articulo2" size="10"
								maxlength="15" datasource="dsDetalleSC:articulos.nombre"
								descriptiondatasource="dsDetalleSC:articulos.descripcion +' - '+ articulos.descripcion_completa"
								popupheight="450" popupwidth="600" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="descripcion4" size="30"
								maxlength="255" datasource="dsDetalleSC:detalle_sc.descripcion"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="select" name="unidad_medida2"
								datasource="dsDetalleSC:detalle_sc.unidad_medida_id">
								<salmon:option display="abc" key="123"
									table="inventario.unidades_medida"
									keycolumn="unidades_medida.unidad_medida_id"
									displaycolumn="unidades_medida.nombre" nulloption="true"></salmon:option>
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_solicitada2" size="8"
								maxlength="15"
								datasource="dsDetalleSC:detalle_sc.cantidad_solicitada"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="monto_unitario1" size="8"
								maxlength="15"
								datasource="dsDetalleSC:detalle_sc.monto_unitario"></salmon:input> -
						<salmon:text name="fecha_ultima_compra" text=""
								displayformatlocalekey="DateFormat"
								datasource="dsDetalleSC:detalle_sc.fecha_ultima_compra"></salmon:text>
						</salmon:td>
						<salmon:td />
						<salmon:td align="RIGHT">
							<salmon:text name="monto_total2" text=""
								displayformatlocalekey="CurrencyFormatConSigno"
								datasource="dsDetalleSC:monto_total"></salmon:text>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td></salmon:td>
						<salmon:td name="tarea2">
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpTareasProyecto" name="tarea3" size="10"
								maxlength="90" datasource="dsDetalleSC:detalle_sc.tarea_id"
								descriptiondatasource="dsDetalleSC:tareas_proyecto.nombre"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="FALSE"></salmon:lookup>
						</salmon:td>
						<salmon:td colspan="2">
							<salmon:input type="text" name="observaciones4" size="40"
								maxlength="255"
								datasource="dsDetalleSC:detalle_sc.observaciones"></salmon:input>
						</salmon:td>
						<salmon:td align="center" name="ccRowTd">
							<salmon:a href="none" name="lnkCc1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsDetalleSC:'%EditarCotizacionCompra?cotizacion_compra_id='+detalle_sc.cotizacion_compra_id">
								<salmon:text name="cc2" text=""
									datasource="dsDetalleSC:detalle_sc.cotizacion_compra_id"></salmon:text>
							</salmon:a>
						</salmon:td>
						<salmon:td align="center" name="cantidadPedidaRowTd">
							<salmon:text name="cantidad_pedida2" text=""
								datasource="dsDetalleSC:detalle_sc.cantidad_pedida"></salmon:text>
						</salmon:td>
						<salmon:td align="center" name="ocRowTd">
							<salmon:a href="none" name="lnkOc1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsDetalleSC:'%EditarOrdenCompra?orden_compra_id='+detalle_sc.orden_compra_id">
								<salmon:text name="oc2" text=""
									datasource="dsDetalleSC:detalle_sc.orden_compra_id + ' - ' + oc_estado_nombre"></salmon:text>
							</salmon:a>
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
