<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.EditarCotizacionCompraController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsCotizacionesCompra" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.CotizacionesCompraModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsDetalleCotizacion" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.DetalleCotizacionModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:property name="prop1" propertyname="visible"
		component="lnkordencompra1"
		expression="detalle_sc.orden_compra_id > 0"
		datasource="dsDetalleCotizacion" />

	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" border="0" width="100%">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Cotización de Compra" width="100%"
						datasource="dsCotizacionesCompra" addbuttonvisible="false"
						cancelbuttonvisible="false" savebuttonvisible="false"
						deletebuttonvisible="false" headerbgcolor="#fffd99"
						bgcolor="fffdce">
						<table border="0">
							<tr>
								<td colspan="6"><salmon:input name="customBUT100"
									type="submit" value="boton 1" accesskey="1" visible="False"></salmon:input>
								<salmon:input name="customBUT110" type="submit" value="boton 2"
									accesskey="2" visible="False"></salmon:input> <salmon:input
									name="customBUT120" type="submit" value="boton 3" accesskey="3"
									visible="False"></salmon:input> <salmon:input
									name="customBUT130" type="submit" value="boton 4" accesskey="4"
									visible="False"></salmon:input> <salmon:input
									name="customBUT140" type="submit" value="boton 5" accesskey="5"
									visible="False"></salmon:input> <salmon:input
									name="customBUT150" type="submit" value="boton 6" accesskey="6"
									visible="False"></salmon:input></td>
								<td><salmon:a href="javascript:window.history.back()"
									name="lnkBack">
									<salmon:text name="back" text="Regresar"
										font="ColumnCaptionFont" />
								</salmon:a> <salmon:a href="" target="_blank"
									name="imprimirCotizacionCompraBUT1"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT1"
										src="%ImageDirectory/logo_excel.gif" height="25"
										srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:text name="espacio" text=" " font="TableHeadingFont" />
								<salmon:a href="" target="_blank"
									name="imprimirCotizacionCompraBUT2"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT2" src="%ImageDirectory/pdf.jpg"
										height="25" srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:a href="" target="_blank"
									name="imprimirSolicitudCotizacion"
									onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización genérica
                        			</salmon:a></td>
							</tr>
							<tr height="5">
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:input type="select"
									name="nombre_completo_comprador2" size="30"
									datasource="dsCotizacionesCompra:cotizaciones_compra.user_id_comprador">
									<salmon:option display="abc" key="123"
										table="inventario.compradores" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="false"></salmon:option>
								</salmon:input></td>
								<td valign="center" rowspan="3"><salmon:text
									name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td colspan="4" rowspan="3"><salmon:input type="textarea"
									name="observaciones2" cols="60" rows="3" wrap="HARD"
									maxlength="255"
									datasource="dsCotizacionesCompra:cotizaciones_compra.observaciones"></salmon:input>
								</td>
								<td><salmon:text name="totalcotizacionseleccionadaPROMPT1"
									text="Total Cotización Seleccionada" font="EmphasisFont" /></td>
								<td><salmon:text name="totalcotizacionseleccionada1"
									text="total_cotizacion_seleccionada goes here"
									datasource="dsCotizacionesCompra:cotizaciones_compra.total_cotizacion_seleccionada"
									font="EmphasisFont" /></td>
							</tr>
						</table>
						<salmon:table name="detalleCotizacion" border="1">
							<salmon:tr>
								<salmon:td>
									<salmon:text name="articuloId21" text="Condiciones"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="descripcion23" text="+++++++++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="descComplArticulo23"
										text="+++++++++++++++++" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="solicitudCompra23" text="+++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="cantidad_solicitada21" text="++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="monto_fecha_ultima_compra21"
										text="+++++++++" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="precio_unitario_proveedor21"
										text="Proveedor 1" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="marca_proveedor21" text="+++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="seleccion_proveedor21" text="+++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="precio_unitario_proveedor22"
										text="Proveedor 2" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="marca_proveedor22" text="+++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="seleccion_proveedor22" text="+++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="precio_unitario_proveedor23"
										text="Proveedor 3" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="marca_proveedor23" text="+++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="seleccion_proveedor23" text="+++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="precio_unitario_proveedor24"
										text="Proveedor 4" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="marca_proveedor24" text="+++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="seleccion_proveedor24" text="+++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="precio_unitario_proveedor25"
										text="Proveedor 5" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="marca_proveedor25" text="+++++++++++"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td>
									<salmon:text name="seleccion_proveedor25" text="+++"
										font="TableHeadingFont" />
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
									<salmon:text name="forma_pagoHeading1" text="Forma de Pago"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td colspan="3" align="CENTER">
									<!-- <salmon:input type="select" width="15"
                              name="forma_Pago_proveedor1" size="10"
                              datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor1">
                              <salmon:option display="abc" key="123"
                                 table="condiciones_compra"
                                 keycolumn="condicion_compra_id"
                                 displaycolumn="descripcion"
                                 nulloption="true"></salmon:option>
                           </salmon:input> -->
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCondicionesCompra" name="lkpCondicionesCompra1"
										size="2" maxlength="50"
										descriptiondatasource="dsCotizacionesCompra:condiciones_compra_proveedor1.descripcion"
										datasource="dsCotizacionesCompra:condiciones_compra_proveedor1.nombre"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>

								</salmon:td>
								<salmon:td colspan="3" align="CENTER">
									<!--<salmon:input type="select" width="15"
                              name="forma_Pago_proveedor2" size="40"
                              datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor2">
                              <salmon:option display="abc" key="123"
                                 table="condiciones_compra"
                                 keycolumn="condicion_compra_id"
                                 displaycolumn="descripcion"                                 
                                 nulloption="true"></salmon:option>
                           </salmon:input>
                        -->
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCondicionesCompra" name="lkpCondicionesCompra2"
										size="2" maxlength="50"
										descriptiondatasource="dsCotizacionesCompra:condiciones_compra_proveedor2.descripcion"
										datasource="dsCotizacionesCompra:condiciones_compra_proveedor2.nombre"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3" align="CENTER">
									<!--<salmon:input type="select" width="15"
                              name="forma_Pago_proveedor3" size="40"
                              datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor3">
                              <salmon:option display="abc" key="123"
                                 table="condiciones_compra"
                                 keycolumn="condicion_compra_id"
                                 displaycolumn="descripcion"
                                 nulloption="true"></salmon:option>
                           </salmon:input>
                        -->
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCondicionesCompra" name="lkpCondicionesCompra3"
										size="2" maxlength="50"
										descriptiondatasource="dsCotizacionesCompra:condiciones_compra_proveedor3.descripcion"
										datasource="dsCotizacionesCompra:condiciones_compra_proveedor3.nombre"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3" align="CENTER" name="condiciones4">
									<!--<salmon:input type="select" width="15"
                              name="forma_Pago_proveedor3" size="40"
                              datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor3">
                              <salmon:option display="abc" key="123"
                                 table="condiciones_compra"
                                 keycolumn="condicion_compra_id"
                                 displaycolumn="descripcion"
                                 nulloption="true"></salmon:option>
                           </salmon:input>
                        -->
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCondicionesCompra" name="lkpCondicionesCompra4"
										size="2" maxlength="50"
										descriptiondatasource="dsCotizacionesCompra:condiciones_compra_proveedor4.descripcion"
										datasource="dsCotizacionesCompra:condiciones_compra_proveedor4.nombre"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3" align="CENTER" name="condiciones5">
									<!--<salmon:input type="select" width="15"
                              name="forma_Pago_proveedor3" size="40"
                              datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor3">
                              <salmon:option display="abc" key="123"
                                 table="condiciones_compra"
                                 keycolumn="condicion_compra_id"
                                 displaycolumn="descripcion"
                                 nulloption="true"></salmon:option>
                           </salmon:input>
                        -->
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpCondicionesCompra" name="lkpCondicionesCompra5"
										size="2" maxlength="50"
										descriptiondatasource="dsCotizacionesCompra:condiciones_compra_proveedor5.descripcion"
										datasource="dsCotizacionesCompra:condiciones_compra_proveedor5.nombre"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
									<salmon:text name="plazo_entregaHeading1"
										text="Plazo de Entrega" font="TableHeadingFont" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="plazo_entrega_proveedor1"
										size="10"
										datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor1">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="plazo_entrega_proveedor2"
										size="10"
										datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor2">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="plazo_entrega_proveedor3"
										size="10"
										datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor3">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="plazo4">
									<salmon:input type="text" name="plazo_entrega_proveedor4"
										size="10"
										datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor3">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="plazo5">
									<salmon:input type="text" name="plazo_entrega_proveedor5"
										size="10"
										datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor3">
									</salmon:input>
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
									<salmon:text name="bonificacionHeading1" text="Bonificación"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="bonificacion_proveedor1"
										size="10" displayformat="##0"
										datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor1">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="bonificacion_proveedor2"
										size="10" displayformat="##0"
										datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor2">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:input type="text" name="bonificacion_proveedor3"
										size="10" displayformat="##0"
										datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor3">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="bonificacion4">
									<salmon:input type="text" name="bonificacion_proveedor4"
										size="10" displayformat="##0"
										datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor4">
									</salmon:input>
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="bonificacion5">
									<salmon:input type="text" name="bonificacion_proveedor5"
										size="10" displayformat="##0"
										datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor5">
									</salmon:input>
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
									<salmon:text name="TotalHeading1" text="Total"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:text text="0" name="total_proveedor1"
										displayformat="###,###,##0.00"
										datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor1" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:text text="0" name="total_proveedor2"
										displayformat="###,###,##0.00"
										datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor2" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT">
									<salmon:text text="0" name="total_proveedor3"
										displayformat="###,###,##0.00"
										datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor3" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="total4">
									<salmon:text text="0" name="total_proveedor4"
										displayformat="###,###,##0.00"
										datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor4" />
								</salmon:td>
								<salmon:td colspan="3" align="RIGHT" name="total5">
									<salmon:text text="0" name="total_proveedor5"
										displayformat="###,###,##0.00"
										datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor5" />
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
									<salmon:text name="ProveedorHeading1" text="Proveedor"
										font="TableHeadingFont" />
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProveedores" name="entidad_id_proveedor1"
										size="6" maxlength="10" displayformat="#########0"
										descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor1.nombre"
										datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor1"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProveedores" name="entidad_id_proveedor2"
										size="6" maxlength="10" displayformat="#########0"
										descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor2.nombre"
										datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor2"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProveedores" name="entidad_id_proveedor3"
										size="6" maxlength="10" displayformat="#########0"
										descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor3.nombre"
										datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor3"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3" name="proveedor4">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProveedores" name="entidad_id_proveedor4"
										size="6" maxlength="10" displayformat="#########0"
										descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor4.nombre"
										datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor4"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
								<salmon:td colspan="3" name="proveedor5">
									<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
										lookupurl="%LkpProveedores" name="entidad_id_proveedor5"
										size="6" maxlength="10" displayformat="#########0"
										descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor5.nombre"
										datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor5"
										popupheight="450" popupwidth="500" usepopup="true"
										showdescription="true"></salmon:lookup>
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:td colspan="6">
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:a href="" target="_blank"
										name="imprimirSolicitudCotizacion1"
										onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización
                        			</salmon:a>
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:a href="" target="_blank"
										name="imprimirSolicitudCotizacion2"
										onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización
                        			</salmon:a>
								</salmon:td>
								<salmon:td colspan="3">
									<salmon:a href="" target="_blank"
										name="imprimirSolicitudCotizacion3"
										onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización
                        			</salmon:a>
								</salmon:td>
								<salmon:td colspan="3" name="imprimir4">
									<salmon:a href="" target="_blank"
										name="imprimirSolicitudCotizacion4"
										onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización
                        			</salmon:a>
								</salmon:td>
								<salmon:td colspan="3" name="imprimir5">
									<salmon:a href="" target="_blank"
										name="imprimirSolicitudCotizacion5"
										onclick="document.forms['bannerForm'].submit();">
                          				Solicitud de cotización
                        			</salmon:a>
								</salmon:td>
							</salmon:tr>
							<salmon:tr>
								<salmon:listformdisplaybox name="listformdisplaybox2"
									mode="Display_single_page"
									caption="Detalle Cotización de Materiales"
									datasource="dsDetalleCotizacion" addbuttonvisible="false"
									savebuttonvisible="false" autocreatelink="false">
									<salmon:datatable name="datatable2" border="1"
										datasource="dsDetalleCotizacion" rowsperpage="1000">
										<salmon:datatableheader>
											<salmon:tr>
												<salmon:td colspan="3">
													<salmon:text name="articuloId1" text="Articulo"
														font="TableHeadingFont" />
												</salmon:td>
												<!-- <salmon:td>
													<salmon:text name="descripcion3" text="Descripción"
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td>
													<salmon:text name="descComplArticulo3"
														text="Desc. Completa" font="TableHeadingFont" />
												</salmon:td> -->
												<salmon:td>
													<salmon:text name="solicitudCompra3" text="SM/OC"
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td>
													<salmon:text name="cantidad_solicitada1"
														text="Cant. solicitada" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td align="center">
													<salmon:text name="monto_fecha_ultima_compra1"
														text="Precio referencia" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="precio_unitario_proveedor1"
														text="Precio proveedor 1" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="marca_proveedor1"
														text="Marca proveedor 1" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="seleccion_proveedor1" text=" X "
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="precio_unitario_proveedor2"
														text="Precio proveedor 2" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="marca_proveedor2"
														text="Marca proveedor 2" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="seleccion_proveedor2" text=" X "
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="precio_unitario_proveedor3"
														text="Precio proveedor 3" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="marca_proveedor3"
														text="Marca proveedor 3" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:text name="seleccion_proveedor3" text=" X "
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="precio_unitario4">
													<salmon:text name="precio_unitario_proveedor4"
														text="Precio proveedor 4" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="marca4">
													<salmon:text name="marca_4"
														text="Marca proveedor 4" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="seleccion4">
													<salmon:text name="seleccion_proveedor4" text=" X "
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="precio_unitario5">
													<salmon:text name="precio_unitario_proveedor5"
														text="Precio proveedor 5" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="marca5">
													<salmon:text name="marca_5"
														text="Marca proveedor 5" font="TableHeadingFont" />
												</salmon:td>
												<salmon:td rowspan="2" name="seleccion5">
													<salmon:text name="seleccion_proveedor5" text=" X "
														font="TableHeadingFont" />
												</salmon:td>
											</salmon:tr>
											<salmon:tr>
												<salmon:td colspan="3">
													<salmon:text name="descripcionDetalle" text="Descripción"
														font="TableHeadingFont" />
												</salmon:td>
												<salmon:td colspan="3">
													<salmon:text name="observacionDetalle" text="Observación"
														font="TableHeadingFont" />
												</salmon:td>
											</salmon:tr>
										</salmon:datatableheader>
										<salmon:datatablerows>
											<salmon:tr>
												<salmon:td colspan="3">
													<salmon:a href="none" name="lnkpartes1"
														onclick="document.forms['bannerForm'].submit();"
														datasource="dsDetalleCotizacion:'%AbmcArticulo?p_articulo_id='+detalle_sc.articulo_id">
														<salmon:text name="articulo2"
															text="articulos.nombre goes here" font="DefaultFont"
															datasource="dsDetalleCotizacion:articulos.nombre + ' - ' + articulos.descripcion + ' - ' + articulos.descripcion_completa" />
													</salmon:a>
												</salmon:td>
												<!-- <salmon:td>
													<salmon:text name="descripcion4"
														text="articulos.descripcion Goes Here" font="DefaultFont"
														datasource="dsDetalleCotizacion:articulos.descripcion" />
												</salmon:td>
												<salmon:td>
													<salmon:text name="descComplArticulo4"
														text="articulos.descripcion_completa clase Goes Here"
														font="DefaultFont"
														datasource="dsDetalleCotizacion:articulos.descripcion_completa" />
												</salmon:td> -->
												<salmon:td>
													<salmon:a href="none" name="lnksolicitud1"
														onclick="document.forms['bannerForm'].submit();"
														datasource="dsDetalleCotizacion:'%AbmcSolicitudCompra?solicitud_compra_id='+detalle_sc.solicitud_compra_id">
														<salmon:text name="solicitudCompra4"
															text="solicitud compra Goes Here" font="DefaultFont"
															datasource="dsDetalleCotizacion:detalle_sc.solicitud_compra_id" />
													</salmon:a>
													<salmon:text name="barra222" text="/" font="defaultFont" />
													<salmon:a href="none" name="lnkordencompra1"
														onclick="document.forms['bannerForm'].submit();"
														datasource="dsDetalleCotizacion:'%EditarOrdenCompra?orden_compra_id='+detalle_sc.orden_compra_id">
														<salmon:text name="ordenCompra4"
															text="orden compra Goes Here" font="DefaultFont"
															datasource="dsDetalleCotizacion:detalle_sc.orden_compra_id" />
													</salmon:a>
												</salmon:td>
												<salmon:td>
													<salmon:input name="cantidad_solicitada22" type="text"
														size="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cantidad"></salmon:input>
													<salmon:input type="select" name="unidad_medida22"
														datasource="dsDetalleCotizacion:detalle_cotizacion.unidad_medida_id">
														<salmon:option display="abc" key="123"
															table="inventario.unidades_medida"
															keycolumn="unidades_medida.unidad_medida_id"
															displaycolumn="unidades_medida.nombre" nulloption="true"></salmon:option>
													</salmon:input>
												</salmon:td>
												<salmon:td>
													<salmon:text name="monto_unitario1" text=""
														displayformatlocalekey="CurrencyFormatConSigno"
														datasource="dsDetalleCotizacion:detalle_sc.monto_ultima_compra" />
													<salmon:text name="text2" text=" - " font="DefaultFont" />
													<salmon:text name="monto_fecha_ultima_compra2"
														text="fecha_ultima_compra goes here"
														displayformatlocalekey="DateFormat"
														datasource="dsDetalleCotizacion:detalle_sc.fecha_ultima_compra"></salmon:text>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="precio_proveedor1" type="text" size="6"
														maxlength="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.monto_unitario_proveedor1">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="input_marca_proveedor1" type="text"
														size="10" maxlength="255"
														datasource="dsDetalleCotizacion:detalle_cotizacion.marca_proveedor1">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input type="checkbox"
														name="cotizacion_seleccionada_proveedor1"
														checkedtruevalue="1"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cotizacion_seleccionada_proveedor1">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="precio_proveedor2" type="text" size="6"
														maxlength="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.monto_unitario_proveedor2">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="input_marca_proveedor2" type="text"
														size="10" maxlength="255"
														datasource="dsDetalleCotizacion:detalle_cotizacion.marca_proveedor2">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input type="checkbox"
														name="cotizacion_seleccionada_proveedor2"
														checkedtruevalue="1"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cotizacion_seleccionada_proveedor2">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="precio_proveedor3" type="text" size="6"
														maxlength="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.monto_unitario_proveedor3">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input name="input_marca_proveedor3" type="text"
														size="10" maxlength="255"
														datasource="dsDetalleCotizacion:detalle_cotizacion.marca_proveedor3">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2">
													<salmon:input type="checkbox"
														name="cotizacion_seleccionada_proveedor3"
														checkedtruevalue="1"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cotizacion_seleccionada_proveedor3">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="precio4">
													<salmon:input name="precio_proveedor4" type="text" size="6"
														maxlength="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.monto_unitario_proveedor4">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="marca_proveedor4">
													<salmon:input name="input_marca_proveedor4" type="text"
														size="10" maxlength="255"
														datasource="dsDetalleCotizacion:detalle_cotizacion.marca_proveedor4">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="seleccionada4">
													<salmon:input type="checkbox"
														name="cotizacion_seleccionada_proveedor4"
														checkedtruevalue="1"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cotizacion_seleccionada_proveedor4">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="precio5">
													<salmon:input name="precio_proveedor5" type="text" size="6"
														maxlength="10"
														datasource="dsDetalleCotizacion:detalle_cotizacion.monto_unitario_proveedor5">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="marca_proveedor5">
													<salmon:input name="input_marca_proveedor5" type="text"
														size="10" maxlength="255"
														datasource="dsDetalleCotizacion:detalle_cotizacion.marca_proveedor5">
													</salmon:input>
												</salmon:td>
												<salmon:td rowspan="2" name="seleccionada5">
													<salmon:input type="checkbox"
														name="cotizacion_seleccionada_proveedor5"
														checkedtruevalue="1"
														datasource="dsDetalleCotizacion:detalle_cotizacion.cotizacion_seleccionada_proveedor5">
													</salmon:input>
												</salmon:td>
											</salmon:tr>
											<salmon:tr>
												<salmon:td colspan="3">
													<salmon:input type="text" name="descripcionTXT" size="20"
														enabled="false" font="DefaultFont"
														datasource="dsDetalleCotizacion:detalle_sc.descripcion" />
												</salmon:td>
												<salmon:td colspan="3">
													<salmon:input type="text" name="observacionesTXT" size="20"
														enabled="false" font="DefaultFont"
														datasource="dsDetalleCotizacion:detalle_sc.observaciones" />
												</salmon:td>
											</salmon:tr>
										</salmon:datatablerows>
									</salmon:datatable>
								</salmon:listformdisplaybox>
							</salmon:tr>
						</salmon:table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2">
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />