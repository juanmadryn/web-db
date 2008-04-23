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
		<salmon:qbecriteria name="desde" type="GTE"
			columns="solicitudes_compra.fecha_aprobacion" />
		<salmon:qbecriteria name="hasta" type="LTE"
			columns="solicitudes_compra.fecha_aprobacion" />
		<salmon:qbecriteria name="resto" type="COMPLEX" 
			columns="proyectos.proyecto"/>
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
								<td colspan="4"/>
								<td widht="10%"/>
								<td><salmon:text name="atributo1" text="Atributo" font="TableHeadingFont" /></td>
								<td><salmon:text name="valor1" text="Valor" font="TableHeadingFont" />
								<salmon:input name="operador" type="select" /></td>
							</tr>
							<tr>
								<td><salmon:text name="nroSolicitud1" text="Nro Solicitud" font="ColumnCaptionFont" /></td>
								<td colspan="3"><salmon:input name="nroSolicitud2" type="text"	datasource="dsQBE:n" /></td>
								<td width="40px"/>
								<td>
									<salmon:lookup
										browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP1" 
										size="15"	maxlength="25" popupheight="450"
										popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
								</td>
								<td><salmon:input name="valorAttr1" type="text" /></td>
							</tr>
							<tr>								
								<td><salmon:text name="fechadesde1" text="Fecha SC desde"
									font="ColumnCaptionFont" /></td>
								<td colspan=><salmon:input type="text"
									name="fechadesde2" size="10"
									datasource="dsQBE:desde" maxlength="10"></salmon:input></td>
								<td><salmon:text name="fechahasta1" text="Fecha SC hasta"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsQBE:hasta"
									maxlength="10"></salmon:input></td>
								<td width="40px"/>								
								<td>
									<salmon:lookup
										browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP2" 
										size="15"	maxlength="25"popupheight="450"
										popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
								</td>
								<td><salmon:input name="valorAttr2" type="text" /></td>
							</tr>
							<tr>
								<td><salmon:text name="buscarComplex1" text="Buscar" font="ColumnCaptionFont" /></td>
								<td colspan="3"><salmon:input name="buscarComplex2" size="35" type="text"	datasource="dsQBE:resto" /></td>
								<td width="40px"/>
								<td>
									<salmon:lookup
										browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP3" 
										size="15"	maxlength="25" popupheight="450"
										popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
								</td>
								<td><salmon:input name="valorAttr3" type="text"/></td>
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
			datasource="dsDetalleSC" autocreatelink="false">		
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
							<salmon:text name="articuloClaseCAP12" text="Desc. Completa"	font="TableHeadingFont"/>
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroSolicitudCAP4" text="Solicitud de Compra"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidadSolicitadaCAP5" text="Cantidad Solicitada"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="ordenCompraCAP5" text="Orden de Compra"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td/>
						<salmon:td colspan="2" name="proyectoHeaderTd">
							<salmon:text name="proyectoCAP7" text="Proyecto"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td name="tareaHeaderTd">
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
							<salmon:a href="none" name="lnkpartes1"
                        		onclick="document.forms['bannerForm'].submit();"
                        		datasource="dsDetalleSC:'%AbmcArticulo?p_articulo_id='+detalle_sc.articulo_id">
                        		<salmon:text name="articuloTXT2" text="articulos.nombre Goes Here"
									font="DefaultFont" datasource="dsDetalleSC:articulos.nombre" />
                     		</salmon:a>							
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloDescTXT3" text="articulos.descripcion Desc Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:articulos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articuloClaseTXT3" text="articulos.descripcion_completa clase Goes Here"
								font="DefaultFont"
								datasource="dsDetalleSC:articulos.descripcion_completa" />
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnksolicitud1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsDetalleSC:'%AbmcSolicitudCompra?solicitud_compra_id='+detalle_sc.solicitud_compra_id">
								<salmon:text name="nroSolicitudTXT3" text="nro solicitud clase Goes Here"
									font="DefaultFont"
									datasource="dsDetalleSC:detalle_sc.solicitud_compra_id" />
								<!-- <salmon:text name="guionSep" text=" - " font="DefaultFont" />
								<salmon:text name="descripcionSc" text="detalle_sc.descripcion goes here"									
									datasource="dsDetalleSC:detalle_sc.descripcion"></salmon:text> -->
							</salmon:a>
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidadSolicitadaTXT4" text="Cantidad Solicitada Goes Here"
								font="DefaultFont" datasource="dsDetalleSC:detalle_sc.cantidad_solicitada" 
								displayformatlocalekey="CantidadPedidaFormat"/>
							<salmon:text name="text2" text=" - " font="DefaultFont" />
							<salmon:text name="unidadMedida" text=""								
								datasource="dsDetalleSC:unidades_medida.nombre"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpOrdenesCompra" name="ordenCompraINP5" size="5"	maxlength="10"
									datasource="dsDetalleSC:detalle_sc.orden_compra_id" popupheight="450"
									popupwidth="650" usepopup="true" showdescription="true"></salmon:lookup>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>						
						<salmon:td/>						
						<salmon:td colspan="2" name="proyectoTableTd">
							<salmon:text name="proyecto3"
								text="proyectos.proyecto Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:proyectos.proyecto" /> -
							<salmon:text name="proyectoTXT6"
								text="proyecto Goes Here" font="DefaultFont"
								datasource="dsDetalleSC:proyectos.nombre" />
						</salmon:td>
						<salmon:td name="tareaTableTd">
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
							<salmon:input type="text" name="montoUnitINP9" size="8"
								maxlength="15"
								datasource="dsDetalleSC:detalle_sc.monto_unitario"></salmon:input>
							<salmon:text name="text2" text=" - " font="DefaultFont" />
							<salmon:text name="monto_fecha_ultima_compra2" text=""
								displayformat="dd/MM/yyyy"
								datasource="dsDetalleSC:detalle_sc.fecha_ultima_compra"></salmon:text>
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