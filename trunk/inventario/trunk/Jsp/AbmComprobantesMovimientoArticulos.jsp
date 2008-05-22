<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.AbmComprobanteMovimientoArticuloController" />
<jsp:include page="templateBefore.jsp" flush="TRUE"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%></td>
	<!-- ********************************************************************************************* -->
	<!-- Add DataSource definitions here -->
	<salmon:datasource name="dsComprobante" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ComprobanteMovimientoArticuloModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsMovimientos" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.MovimientoArticuloModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsAtributos" type="MODEL"
		model="infraestructura.models.AtributosEntidadModel"
		dbprofile="infraestructura" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Add page code here -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Comprobantes de movimientos de artículos" width="100%"
						datasource="dsComprobante" buttondisplaylocation="IN_HEADER"
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
								<td><salmon:text name="tipo_movimiento1"
									text="Tipo de Movimiento" font="TableHeadingFont" /></td>
								<salmon:td>
									<salmon:input type="select" name="tipo_movimiento2" size="40"
										datasource="dsComprobante:comprobante_movimiento_articulo.tipo_movimiento_articulo_id">
										<salmon:option display="abc" key="123"
											table="inventario.tipo_movimiento_articulo"
											keycolumn="tipo_movimiento_articulo_id"
											displaycolumn="nombre" nulloption="false"></salmon:option>
									</salmon:input>
								</salmon:td>
							</tr>
							<tr>
								<td><salmon:text name="fecha1" text="Fecha de comprobante"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha2" text=""
									displayformat="dd/MM/yyyy HH:mm"
									datasource="dsComprobante:comprobante_movimiento_articulo.fecha"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_user_prepara1"
									text="Prepara" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_user_prepara2"
									text=""
									datasource="dsComprobante:website_user_preparador.nombre_completo"></salmon:text></td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:text name="recibe1" text="Retira/Entrega"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpLegajoLegajo" name="legajo1" size="6"
									maxlength="10" displayformat="##########0"
									datasource="dsComprobante:comprobante_movimiento_articulo.user_id_retira"
									popupheight="450" popupwidth="500" usepopup="true"
									showdescription="false"></salmon:lookup> <salmon:text
									name="recibe2" text=""
									datasource="dsComprobante:legajos.APEYNOM" /></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen1" text="Almacén"
									font="TableHeadingFont" /></td>
								<salmon:td>
									<salmon:input type="select" name="almacen2" size="30"
										datasource="dsComprobante:comprobante_movimiento_articulo.almacen_id">
										<salmon:option display="abc" key="123"
											table="inventario.almacenes" keycolumn="almacen_id"
											displaycolumn="nombre" nulloption="false"></salmon:option>
									</salmon:input>
								</salmon:td>
							</tr>
							<tr>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td colspan="3"><salmon:input type="textarea"
									name="observaciones2" cols="60" rows="3" wrap="HARD"
									maxlength="255"
									datasource="dsComprobante:comprobante_movimiento_articulo.observaciones"></salmon:input></td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:text name="recepcion_compra_id1"
									text="Recepción de compra" font="TableHeadingFont" /></td>
								<td><salmon:text name="recepcion_compra_id2" text=""
									datasource="dsComprobante:comprobante_movimiento_articulo.recepcion_compra_id"></salmon:text></td>
								<td width="10"></td>
							</tr>
							<tr>
								<td><salmon:a href="" target="_blank"
									name="imprimirComprobante1"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT1"
										src="%ImageDirectory/logo_excel.gif" height="25"
										srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:a href="" target="_blank" name="imprimirComprobante2"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT2" src="%ImageDirectory/pdf.jpg"
										height="25" srclocalekey="bannerImageSource" />
								</salmon:a></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
				<salmon:td align="LEFT" valign="Top">
					<salmon:listformdisplaybox name="listformdisplaybox4"
						mode="Display_single_page" caption="Atributos de la recepción"
						width="100%" datasource="dsAtributos" addbuttonvisible="false"
						savebuttonvisible="true">
						<salmon:datatable name="datatable1" width="100%"
							datasource="dsAtributos" rowsperpage="5">
							<salmon:datatableheader>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="atributoCAP15" text="Atributo"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="valorCAP16" text="Valor"
											font="TableHeadingFont" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatableheader>
							<salmon:datatablerows>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="atributoTXT6" text="atributo Goes Here"
											datasource="dsAtributos:atributos_rol.nombre"
											font="DefaultFont" />
									</salmon:td>
									<salmon:td>
										<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
											lookupurl="%LkpValoresAtributos?p_clase_lov="
											name="valorTE11" size="40" maxlength="255"
											datasource="dsAtributos:atributos_entidad.valor"
											popupheight="450" popupwidth="500" usepopup="true"
											showdescription="true"></salmon:lookup>
									</salmon:td>
								</salmon:tr>
							</salmon:datatablerows>
						</salmon:datatable>
					</salmon:listformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page" caption="Detalle" width="100%"
			datasource="dsMovimientos" addbuttonvisible="false"
			savebuttonvisible="false">
			<salmon:datatable name="datatable2" width="100%"
				datasource="dsMovimientos" rowsperpage="10">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="seleccion_detalle1" text="-X-"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo1" text="Articulo"
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
							<salmon:text name="cantidad_solicitada1" text="Cant. solicitada"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_entregada1" text="Cant. entregada"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_anulada1" text="Cant. anulada"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td>
						</salmon:td>
						<salmon:td>
							<salmon:text name="proyecto1" text="Proyecto"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tarea1" text="Tarea" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cargo1" text="Con cargo a"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td colspan="3">
							<salmon:text name="observaciones3" text="Observaciones"
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
								maxlength="15" datasource="dsMovimientos:articulos.nombre"
								descriptiondatasource="dsMovimientos:articulos.descripcion"
								popupheight="450" popupwidth="600" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="descripcion4" size="30"
								maxlength="255"
								datasource="dsMovimientos:movimiento_articulo.descripcion"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="select" name="unidad_medida2"
								datasource="dsMovimientos:movimiento_articulo.unidad_medida_id">
								<salmon:option display="abc" key="123"
									table="inventario.unidades_medida"
									keycolumn="unidades_medida.unidad_medida_id"
									displaycolumn="unidades_medida.nombre" nulloption="true"></salmon:option>
							</salmon:input>
							<salmon:a href="none" name="lnkConversiones"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsMovimientos:'%AbmcConversiones?articulo_id='+movimiento_articulo.articulo_id+'&unidad_medida_id='+movimiento_articulo.unidad_medida_id">
								<salmon:text name="txtConversiones" text="C"
									font="TableHeadingFont" />
							</salmon:a>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_solicitada2" size="8"
								maxlength="15"
								datasource="dsMovimientos:movimiento_articulo.cantidad_solicitada"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_entregada2" size="8"
								maxlength="15"
								datasource="dsMovimientos:movimiento_articulo.cantidad_entregada"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_anulada2" size="8"
								maxlength="15"
								datasource="dsMovimientos:movimiento_articulo.cantidad_anulada"></salmon:input>
						</salmon:td>
					</salmon:tr>
					<salmon:tr>
						<salmon:td>
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpProyectos" name="proyecto2" size="8"
								maxlength="15" datasource="dsMovimientos:proyectos.proyecto"
								descriptiondatasource="dsMovimientos:proyectos.nombre"
								popupheight="450" popupwidth="500" usepopup="TRUE"
								showdescription="TRUE"></salmon:lookup>
						</salmon:td>
						<salmon:td name="tarea2">
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpTareasProyecto" name="tarea3" size="8"
								maxlength="90"
								datasource="dsMovimientos:movimiento_articulo.tarea_id"
								descriptiondatasource="dsMovimientos:tareas_proyecto.nombre"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="TRUE"></salmon:lookup>
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpLegajoLegajo" name="cargo2" size="6"
								maxlength="10" displayformat="##########0"
								datasource="dsMovimientos:movimiento_articulo.legajo_cargo"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="false"></salmon:lookup>
							<salmon:text name="cargo3" text=""
								datasource="dsMovimientos:legajos.APEYNOM" />
						</salmon:td>
						<salmon:td colspan="3">
							<salmon:input type="text" name="observaciones4" size="60"
								maxlength="255"
								datasource="dsMovimientos:movimiento_articulo.observaciones"></salmon:input>
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
