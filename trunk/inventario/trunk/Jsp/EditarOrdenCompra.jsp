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
   controller="inventario.controllers.EditarOrdenCompraController" />
<jsp:include page="templateBefore.jsp" flush="TRUE"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   <!-- ********************************************************************************************* -->
   <!-- Add DataSource definitions here -->
   <salmon:datasource name="dsOrdenesCompra" type="MODEL"
      dbprofile="inventario"
      model="inventario.models.OrdenesCompraModel" autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsDetalleSC" type="MODEL"
      dbprofile="inventario" model="inventario.models.DetalleSCModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Add page code here -->
   <!-- ********************************************************************************************* -->

   <salmon:property name="prop1" propertyname="visible"
      component="descAdicionalTr"
      expression="DESCRIPCION_ADICIONAL EQUALS 1 || detalle_sc.observaciones_oc.length() > 0"
      datasource="dsDetalleSC" />

   <salmon:box name="box1" width="100%">
      <salmon:table name="table1" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:detailformdisplaybox name="detailformdisplaybox1"
                  caption="Orden de Compra" width="100%"
                  datasource="dsOrdenesCompra" addbuttonvisible="false"
                  cancelbuttonvisible="false" savebuttonvisible="false"
                  deletebuttonvisible="false" headerbgcolor="#fffd99"
                  bgcolor="fffdce">
                  <table width="100%" border="0">
                     <tr>
                        <td colspan="5"><salmon:input
                           name="customBUT100" type="submit"
                           value="boton 1" accesskey="1" visible="False"></salmon:input>
                        <salmon:input name="customBUT110" type="submit"
                           value="boton 2" accesskey="2" visible="False"></salmon:input>
                        <salmon:input name="customBUT120" type="submit"
                           value="boton 3" accesskey="3" visible="False"></salmon:input>
                        <salmon:input name="customBUT130" type="submit"
                           value="boton 4" accesskey="4" visible="False"></salmon:input>
                        <salmon:input name="customBUT140" type="submit"
                           value="boton 5" accesskey="5" visible="False"></salmon:input>
                        <salmon:input name="customBUT150" type="submit"
                           value="boton 6" accesskey="6" visible="False"></salmon:input>
                        </td>
                        <td align="right" colspan="2"><!-- <salmon:a href="" target="_blank" 
										name="imprimirOrdenCompraBUT1"
										onclick="document.forms['bannerForm'].submit();">
										<salmon:img name="imprimirTXT1"
											src="%ImageDirectory/logo_excel.gif" height="25"
											srclocalekey="bannerImageSource" />
									</salmon:a> 
									<salmon:text name="espacio" text=" " font="TableHeadingFont" /> -->
                        <salmon:a href="" target="_blank"
                           name="imprimirOrdenCompraBUT2"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:text name="imprimirTXT2"
                              text="preimpresa" />
                           <salmon:img name="imprimirIMG2"
                              src="%ImageDirectory/pdf.jpg" height="25"
                              srclocalekey="bannerImageSource" />
                        </salmon:a> <salmon:text name="espacio" text=" "
                           font="TableHeadingFont" /> <salmon:a href=""
                           target="_blank"
                           name="imprimirOrdenCompraBUT3"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:text name="imprimirTXT3"
                              text="completa" />
                           <salmon:img name="imprimirIMG3"
                              src="%ImageDirectory/pdf.jpg" height="25"
                              srclocalekey="bannerImageSource" />
                        </salmon:a></td>
                     </tr>
                     <tr>
                        <td><salmon:text
                           name="nombre_completo_comprador1"
                           text="Comprador" font="TableHeadingFont" /></td>
                        <td><salmon:input type="select"
                           name="nombre_completo_comprador2" size="30"
                           datasource="dsOrdenesCompra:ordenes_compra.user_id_comprador">
                           <salmon:option display="abc" key="123"
                              table="inventario.compradores"
                              keycolumn="user_id"
                              displaycolumn="nombre_completo"
                              nulloption="false"></salmon:option>
                        </salmon:input></td>
                        <td><salmon:text name="proveedor1"
                           text="Proveedor" font="TableHeadingFont" /></td>
                        <td colspan="2" width="30%"><salmon:lookup
                           browseimage="%ImageDirectory/Browse.gif"
                           lookupurl="%LkpProveedores" name="proveedor2"
                           size="6" maxlength="10"
                           displayformat="#########0"
                           descriptiondatasource="dsOrdenesCompra:entidad_externa.nombre"
                           datasource="dsOrdenesCompra:ordenes_compra.entidad_id_proveedor"
                           popupheight="450" popupwidth="500"
                           usepopup="true" showdescription="true"></salmon:lookup>
                        </td>
                        <td><salmon:text
                           name="fecha_estimada_entrega1"
                           text="Fecha estimada entrega"
                           font="TableHeadingFont" /></td>
                        <td><salmon:input
                           name="fecha_estimada_entrega2" type="text"
                           size="10" maxlength="15"
                           datasource="dsOrdenesCompra:ordenes_compra.fecha_estimada_entrega"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="fecha_ordencompra1"
                           text="Fecha de orden" font="TableHeadingFont" /></td>
                        <td><salmon:text name="fecha_ordencompra2"
                           text=""
                           displayformatlocalekey="DateTimeFormat"
                           datasource="dsOrdenesCompra:ordenes_compra.fecha"></salmon:text></td>
                        <td><salmon:text name="fecha_aprobacion1"
                           text="Fecha de aprobación"
                           font="TableHeadingFont" /></td>
                        <td colspan="2"><salmon:text
                           name="fecha_aprobacion2" text=""
                           displayformatlocalekey="DateTimeFormat"
                           datasource="dsOrdenesCompra:solicitudes_compra.fecha_aprobacion"></salmon:text></td>
                        <td><salmon:text
                           name="fecha_entrega_completa1"
                           text="Fecha entrega completa"
                           font="TableHeadingFont" /></td>
                        <td colspan="2"><salmon:text
                           name="fecha_entrega_completa2" text=""
                           displayformatlocalekey="DateFormat"
                           datasource="dsOrdenesCompra:ordenes_compra.fecha_entrega_completa"></salmon:text></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="forma_pago"
                           text="Forma de Pago" font="TableHeadingFont" /></td>
                        <td><salmon:lookup
                           browseimage="%ImageDirectory/Browse.gif"
                           lookupurl="%LkpCondicionesCompra"
                           name="lkpCondicionesCompra" size="10"
                           maxlength="50"
                           descriptiondatasource="dsOrdenesCompra:condiciones_compra.descripcion"
                           datasource="dsOrdenesCompra:condiciones_compra.nombre"
                           popupheight="450" popupwidth="500"
                           usepopup="true" showdescription="true"></salmon:lookup>
                        </td>
                        <td><salmon:text name="descuentoGlobal1"
                           text="Descuento" font="TableHeadingFont" /></td>
                        <td><salmon:text name="signo_pesos3"
                           text="%" /> <salmon:input type="text"
                           name="descuentoGlobal2"
                           datasource="dsOrdenesCompra:ordenes_compra.descuento"></salmon:input>
                        </td>
                     </tr>
                     <tr>
                        <!-- <td valign="top"><salmon:text name="descripcion1" text="Descripcion"
									font="TableHeadingFont"/></td>
								<td valign="top"><salmon:input type="text" name="descripcion2" size="40"
									maxlength="255"
									datasource="dsOrdenesCompra:ordenes_compra.descripcion"></salmon:input></td>
								-->
                        <td valign="center" rowspan="3"><salmon:text
                           name="observaciones1" text="Observaciones"
                           font="TableHeadingFont" /></td>
                        <td colspan="4" rowspan="3"><salmon:input
                           type="textarea" name="observaciones2"
                           cols="60" rows="3" wrap="HARD"
                           maxlength="255"
                           datasource="dsOrdenesCompra:ordenes_compra.observaciones"></salmon:input>
                        </td>
                        <td align="right"><salmon:text
                           name="neto_orden1" text="Neto"
                           font="TableHeadingFont" /></td>
                        <td align="right"><salmon:text
                           name="neto_orden2" text="neto goes here"
                           displayformatlocalekey="CurrencyFormatConSigno"
                           datasource="dsOrdenesCompra:neto_orden_compra"></salmon:text>
                        <td />
                     </tr>
                     <tr align="right">
                        <td><salmon:text name="total_descuento1"
                           text="Descuento" font="TableHeadingFont" /></td>
                        <td><salmon:text name="total_descuento2"
                           text="total goes here"
                           displayformatlocalekey="CurrencyFormatConSigno"
                           datasource="dsOrdenesCompra:descuento_orden_compra"></salmon:text>
                        <td />
                     </tr>
                     <tr align="right">
                        <td><salmon:text name="total_iva1"
                           text="I.V.A" font="TableHeadingFont" /></td>
                        <td style="border-bottom: solid 2px black">
                        <salmon:text name="total_iva2"
                           text="iva goes here"
                           displayformatlocalekey="CurrencyFormatConSigno"
                           datasource="dsOrdenesCompra:iva_orden_compra"></salmon:text>
                        <td />
                     </tr>
                     <tr align="right">
                        <td />
                        <td colspan="2" align="left"><salmon:a
                           href="ListaFirmantes.jsp" target="_blank"
                           name="verFirmantes"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:text name="verFirmantesTXT2"
                              text="Firmantes" />
                        </salmon:a> &nbsp; <salmon:a href="ListaSolicitantes.jsp"
                           target="_blank" name="verSolicitantes"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:text name="verSolicitantesTXT2"
                              text="Solicitantes" />
                        </salmon:a></td>
                        <td colspan="3"><salmon:text
                           name="total_orden1" text="Total"
                           font="TableHeadingFont" /></td>
                        <td><salmon:text name="total_orden2"
                           text="total goes here"
                           displayformatlocalekey="CurrencyFormatConSigno"
                           datasource="dsOrdenesCompra:total_orden_compra"></salmon:text>
                        </td>
                     </tr>
                  </table>
               </salmon:detailformdisplaybox>
            </salmon:td>
         </salmon:tr>
         <salmon:tr>
            <salmon:td>
               <table width="100%">
                  <tr>
                     <td><salmon:text name="observacionX1"
                        text="OBSERVACIONES" font="TableHeadingFont"
                        visible="false" /></td>
                  </tr>
                  <tr>
                     <td><salmon:input type="textarea"
                        name="observacionX2" cols="60" rows="4"
                        wrap="HARD" size="40" maxlength="255"
                        datasource="dsOrdenesCompra:observaciones"
                        visible="false"></salmon:input></td>
                  </tr>
               </table>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox2"
         mode="Display_single_page" caption="Detalle Orden de Compra"
         width="100%" datasource="dsDetalleSC" addbuttonvisible="false"
         savebuttonvisible="false" autocreatelink="false">
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
                     <salmon:text name="descComplArticulo3"
                        text="Desc. Completa" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="solicitudCompra3"
                        text="Nro. S.M." font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cantidad_solicitada1"
                        text="Cant. solicitada" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="2">
                     <salmon:text name="monto_fecha_ultima_compra1"
                        text="Precio unitario" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td align="RIGHT">
                     <salmon:text name="monto_total1" text="Total"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td />
                  <salmon:td colspan="2" name="proyectoHeaderTd">
                     <salmon:text name="proyecto1" text="Proyecto"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td name="tareaHeaderTd">
                     <salmon:text name="tarea1" text="Tarea"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="centroCosto1"
                        text="Centro Costo" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cantidadPedida1"
                        text="Cant. pedida" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descuento1" text="Descuento"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="iva1" text="I.V.A."
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td nowrap="TRUE" align="RIGHT">
                     <salmon:text name="cantidadRecibida1"
                        text="Cant. recibida" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:input type="checkbox"
                        name="seleccion_detalle2" checkedtruevalue="1"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:a href="none" name="lnkpartes1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsDetalleSC:'%AbmcArticulo?p_articulo_id='+detalle_sc.articulo_id">
                        <salmon:text name="articulo2"
                           text="articulos.nombre goes here"
                           font="DefaultFont"
                           datasource="dsDetalleSC:articulos.nombre" />
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcion4"
                        text="articulos.descripcion Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleSC:articulos.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descComplArticulo4"
                        text="articulos.descripcion_completa clase Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleSC:articulos.descripcion_completa" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:a href="none" name="lnksolicitud1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsDetalleSC:'%AbmcSolicitudCompra?solicitud_compra_id='+detalle_sc.solicitud_compra_id">
                        <salmon:text name="solicitudCompra4"
                           text="solicitud compra Goes Here"
                           font="DefaultFont"
                           datasource="dsDetalleSC:detalle_sc.solicitud_compra_id" />
                        <!-- <salmon:text name="guionSep" text=" - " font="DefaultFont" />
								<salmon:text name="descripcionSc" text="detalle_sc.descripcion goes here"									
									datasource="dsDetalleSC:detalle_sc.descripcion"></salmon:text> -->
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cantidad_solicitada2"
                        text="cantidad_solicitada Goes Here"
                        font="DefaultFont"
                        displayformatlocalekey="CantidadPedidaFormat"
                        datasource="dsDetalleSC:detalle_sc.cantidad_solicitada" />
                     <salmon:text name="text2" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="unidadMedida" text=""
                        datasource="dsDetalleSC:unidades_medida.nombre"></salmon:text>
                  </salmon:td>
                  <salmon:td colspan="2">
                     <salmon:text name="signo_pesos1" text="$" />
                     <salmon:input type="text" name="monto_unitario1"
                        size="8" maxlength="15"
                        datasource="dsDetalleSC:detalle_sc.monto_unitario"></salmon:input>
                     <salmon:text name="text2" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="monto_fecha_ultima_compra2"
                        text="fecha_ultima_compra goes here"
                        displayformatlocalekey="DateFormat"
                        datasource="dsDetalleSC:detalle_sc.fecha_ultima_compra"></salmon:text>
                  </salmon:td>
                  <salmon:td align="RIGHT">
                     <salmon:text name="monto_total2" text=""
                        displayformatlocalekey="CurrencyFormatConSigno"></salmon:text>
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td />
                  <salmon:td colspan="2" name="proyectoTableTd">
                     <salmon:text name="proyecto3"
                        text="proyectos.proyecto Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleSC:proyectos.proyecto" /> - 
							<salmon:text name="proyecto2" text="proyectos.nombre Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleSC:proyectos.nombre" />
                  </salmon:td>
                  <salmon:td name="tareaTableTd">
                     <salmon:text name="tarea3"
                        text="detalle_sc.tarea_id Goes Here"
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
                  <salmon:td align="LEFT">
                     <salmon:input type="text" name="cantidad_pedida2"
                        size="8" maxlength="15"
                        datasource="dsDetalleSC:detalle_sc.cantidad_pedida"></salmon:input>
                     <salmon:input type="select" name="unidad_medida22"
                        datasource="dsDetalleSC:detalle_sc.unidad_medida_id_pedida">
                        <salmon:option display="abc" key="123"
                           table="inventario.unidades_medida"
                           keycolumn="unidades_medida.unidad_medida_id"
                           displaycolumn="unidades_medida.nombre"
                           nulloption="true"></salmon:option>
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="signo_pesos2" text="%" />
                     <salmon:input type="text" name="descuento2"
                        size="8" align="right" maxlength="15"
                        datasource="dsDetalleSC:detalle_sc.descuento"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="iva2" text="iva goes here"
                        font="DefaultFont"
                        datasource="dsDetalleSC:detalle_sc.iva"
                        displayformatlocalekey="IvaFormat"></salmon:text>
                     <salmon:text name="signo_porcentaje" text=" %" />
                  </salmon:td>
                  <salmon:td align="RIGHT">
                     <salmon:text name="cantidad_recibida2" text=""
                        datasource="dsDetalleSC:detalle_sc.cantidad_recibida"></salmon:text>
                  </salmon:td>
               </salmon:tr>
               <salmon:tr name="descAdicionalTr" visible="false">
                  <salmon:td></salmon:td>
                  <salmon:td colspan="8">
                     <salmon:input type="text" name="descAdicionalOcTXT"
                        size="100" maxlength="250"
                        datasource="dsDetalleSC:detalle_sc.observaciones_oc"></salmon:input>
                  </salmon:td>
               </salmon:tr>
               <!-- <salmon:tr name="nuevoDetalleSinSc" visible="false">
						<salmon:td></salmon:td>
						<salmon:td colspan="8">
							<salmon:input type="text" name="descAdicionalOcTXT2" size="100"
									maxlength="250" datasource="dsDetalleSC:detalle_sc.observaciones_oc"></salmon:input>							
						</salmon:td>
					</salmon:tr> -->
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="TRUE"></jsp:include>
<salmon:endpage />
