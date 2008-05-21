<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsCotizacionesCompra" type="MODEL"
      dbprofile="inventario"
      model="inventario.models.CotizacionesCompraModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsDetalleCotizacion" type="MODEL"
      dbprofile="inventario"
      model="inventario.models.DetalleCotizacionModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1">
      <salmon:table name="table1" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:detailformdisplaybox name="detailformdisplaybox1"
                  caption="Cotización de Compra"
                  datasource="dsCotizacionesCompra"
                  addbuttonvisible="false" cancelbuttonvisible="false"
                  savebuttonvisible="false" deletebuttonvisible="false"
                  headerbgcolor="#fffd99" bgcolor="fffdce">
                  <table border="0">
                     <tr>
                        <td colspan="6"><salmon:input
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
                        <td align="right"><salmon:a href=""
                           target="_blank"
                           name="imprimirCotizacionCompraBUT1"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:img name="imprimirTXT1"
                              src="%ImageDirectory/logo_excel.gif"
                              height="25"
                              srclocalekey="bannerImageSource" />
                        </salmon:a> <salmon:text name="espacio" text=" "
                           font="TableHeadingFont" /> <salmon:a href=""
                           target="_blank"
                           name="imprimirCotizacionCompraBUT2"
                           onclick="document.forms['bannerForm'].submit();">
                           <salmon:img name="imprimirTXT2"
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
                           datasource="dsCotizacionesCompra:cotizaciones_compra.user_id_comprador">
                           <salmon:option display="abc" key="123"
                              table="inventario.compradores"
                              keycolumn="user_id"
                              displaycolumn="nombre_completo"
                              nulloption="false"></salmon:option>
                        </salmon:input></td>
                        <td valign="center" rowspan="3"><salmon:text
                           name="observaciones1" text="Observaciones"
                           font="TableHeadingFont" /></td>
                        <td colspan="4" rowspan="3"><salmon:input
                           type="textarea" name="observaciones2"
                           cols="60" rows="3" wrap="HARD"
                           maxlength="255"
                           datasource="dsCotizacionesCompra:cotizaciones_compra.observaciones"></salmon:input>
                        </td>
                     </tr>
                  </table>
               </salmon:detailformdisplaybox>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <salmon:box name="box2">
      <salmon:listformdisplaybox name="listformdisplaybox2"
         mode="Display_single_page"
         caption="Detalle Cotización de Compra"
         datasource="dsDetalleCotizacion" addbuttonvisible="false"
         savebuttonvisible="false" autocreatelink="false">
         <salmon:datatable name="datatable2" border="1"
            datasource="dsDetalleCotizacion" rowsperpage="1000">
            <salmon:datatableheader>
               <salmon:tr>
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
                        text="Nro. Solicitud" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cantidad_solicitada1"
                        text="Cant. solicitada" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="unidad_medidida1" text="UM"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="monto_fecha_ultima_compra1"
                        text="Precio referncia" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="precio_unitario_proveedor1"
                        text="Precio proveedor 1"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="marca_proveedor1"
                        text="Marca proveedor 1" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="seleccion_proveedor1" text=" X "
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="precio_unitario_proveedor2"
                        text="Precio proveedor 2"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="marca_proveedor2"
                        text="Marca proveedor 2" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="seleccion_proveedor2" text=" X "
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="precio_unitario_proveedor3"
                        text="Precio proveedor 3"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="marca_proveedor3"
                        text="Marca proveedor 3" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="seleccion_proveedor3" text=" X "
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:a href="none" name="lnkpartes1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsDetalleCotizacion:'%AbmcArticulo?p_articulo_id='+detalle_sc.articulo_id">
                        <salmon:text name="articulo2"
                           text="articulos.nombre goes here"
                           font="DefaultFont"
                           datasource="dsDetalleCotizacion:articulos.nombre" />
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcion4"
                        text="articulos.descripcion Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleCotizacion:articulos.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descComplArticulo4"
                        text="articulos.descripcion_completa clase Goes Here"
                        font="DefaultFont"
                        datasource="dsDetalleCotizacion:articulos.descripcion_completa" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:a href="none" name="lnksolicitud1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsDetalleCotizacion:'%AbmcSolicitudCompra?solicitud_compra_id='+detalle_sc.solicitud_compra_id">
                        <salmon:text name="solicitudCompra4"
                           text="solicitud compra Goes Here"
                           font="DefaultFont"
                           datasource="dsDetalleCotizacion:detalle_sc.solicitud_compra_id" />
                        <!-- <salmon:text name="guionSep" text=" - " font="DefaultFont" />
                        <salmon:text name="descripcionSc" text="detalle_sc.descripcion goes here"                          
                           datasource="dsDetalleCotizacion:detalle_sc.descripcion"></salmon:text> -->
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cantidad_solicitada2"
                        text="cantidad_solicitada Goes Here"
                        font="DefaultFont"
                        displayformatlocalekey="CantidadPedidaFormat"
                        datasource="dsDetalleCotizacion:detalle_sc.cantidad_solicitada" />
                     <salmon:text name="text2" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="unidadMedida" text=""
                        datasource="dsDetalleCotizacion:unidades_medida.nombre"></salmon:text>
                  </salmon:td>
                  <salmon:td colspan="2">
                     <salmon:text name="signo_pesos1" text="$" />
                     <salmon:input type="text" name="monto_unitario1"
                        size="8" maxlength="15"
                        datasource="dsDetalleCotizacion:detalle_sc.monto_unitario"></salmon:input>
                     <salmon:text name="text2" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="monto_fecha_ultima_compra2"
                        text="fecha_ultima_compra goes here"
                        displayformatlocalekey="DateFormat"
                        datasource="dsDetalleCotizacion:detalle_sc.fecha_ultima_compra"></salmon:text>
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
            <salmon:datatablefooter>
               <salmon:tr>
                  <salmon:td colspan="7">
                     <salmon:text name="forma_pagoHeading1"
                        text="Forma de Pago" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:input type="select"
                        name="forma_Pago_proveedor1" size="30"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor1">
                        <salmon:option display="abc" key="123"
                           table="condiciones_compra"
                           keycolumn="condicion_compra_id"
                           displaycolumn="nombre" nulloption="true"></salmon:option>
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:input type="select"
                        name="forma_Pago_proveedor2" size="30"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor2">
                        <salmon:option display="abc" key="123"
                           table="condiciones_compra"
                           keycolumn="condicion_compra_id"
                           displaycolumn="nombre" nulloption="true"></salmon:option>
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:input type="select"
                        name="forma_Pago_proveedor3" size="30"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.condicion_compra_id_proveedor3">
                        <salmon:option display="abc" key="123"
                           table="condiciones_compra"
                           keycolumn="condicion_compra_id"
                           displaycolumn="nombre" nulloption="true"></salmon:option>
                     </salmon:input>
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td colspan="7">
                     <salmon:text name="plazo_entregaHeading1"
                        text="Plazo de Entrega" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="plazo_entrega_proveedor1" size="10"
                        displayformat="###0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor1">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="plazo_entrega_proveedor2" size="10"
                        displayformat="###0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor2">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="plazo_entrega_proveedor3" size="10"
                        displayformat="###0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.plazo_entrega_proveedor3">
                     </salmon:input>
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td colspan="7">
                     <salmon:text name="bonificacionHeading1"
                        text="Bonificación" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="bonificacion_proveedor1" size="10"
                        displayformat="##0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor1">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="bonificacion_proveedor2" size="10"
                        displayformat="##0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor2">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:input type="text"
                        name="bonificacion_proveedor3" size="10"
                        displayformat="##0"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.bonificacion_proveedor3">
                     </salmon:input>
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td colspan="7">
                     <salmon:text name="TotalHeading1" text="Total"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:text text="0" name="total_proveedor1"
                        displayformat="###,###,##0.00"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor1" />
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:text text="0" name="total_proveedor1"
                        displayformat="###,###,##0.00"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor2" />
                  </salmon:td>
                  <salmon:td colspan="3" align="RIGHT">
                     <salmon:text text="0" name="total_proveedor1"
                        displayformat="###,###,##0.00"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.total_proveedor3" />
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td colspan="7">
                     <salmon:text name="ProveedorHeading1"
                        text="Proveedor" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:lookup
                        browseimage="%ImageDirectory/Browse.gif"
                        lookupurl="%LkpProveedores"
                        name="entidad_id_proveedor1" size="6"
                        maxlength="10" displayformat="#########0"
                        descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor1.nombre"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor1"
                        popupheight="450" popupwidth="500"
                        usepopup="true" showdescription="true"></salmon:lookup>
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:lookup
                        browseimage="%ImageDirectory/Browse.gif"
                        lookupurl="%LkpProveedores"
                        name="entidad_id_proveedor2" size="6"
                        maxlength="10" displayformat="#########0"
                        descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor2.nombre"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor2"
                        popupheight="450" popupwidth="500"
                        usepopup="true" showdescription="true"></salmon:lookup>
                  </salmon:td>
                  <salmon:td colspan="3">
                     <salmon:lookup
                        browseimage="%ImageDirectory/Browse.gif"
                        lookupurl="%LkpProveedores"
                        name="entidad_id_proveedor3" size="6"
                        maxlength="10" displayformat="#########0"
                        descriptiondatasource="dsCotizacionesCompra:entidad_externa_proveedor3.nombre"
                        datasource="dsCotizacionesCompra:cotizaciones_compra.entidad_id_proveedor3"
                        popupheight="450" popupwidth="500"
                        usepopup="true" showdescription="true"></salmon:lookup>
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablefooter>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />