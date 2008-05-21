<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.AbmRecepcionesController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsRecepciones" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.RecepcionesComprasModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsAtributos" type="MODEL"
		model="infraestructura.models.AtributosEntidadModel"
		dbprofile="infraestructura" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsDetalle" type="MODEL" dbprofile="inventario"
		model="inventario.models.DetalleRCModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Recepciones" width="100%" datasource="dsRecepciones"
						buttondisplaylocation="BELOW_TABLE" addbuttonvisible="false"
						cancelbuttonvisible="false" savebuttonvisible="false"
						deletebuttonvisible="false">
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
								<td><salmon:a href="" target="_blank"
									name="imprimirRecepcionCompraBUT1"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT1"
										src="%ImageDirectory/logo_excel.gif" height="25"
										srclocalekey="bannerImageSource" />
								</salmon:a> <salmon:text name="espacio" text=" " font="TableHeadingFont" />
								<salmon:a href="" target="_blank"
									name="imprimirRecepcionCompraBUT2"
									onclick="document.forms['bannerForm'].submit();">
									<salmon:img name="imprimirTXT2" src="%ImageDirectory/pdf.jpg"
										height="25" srclocalekey="bannerImageSource" />
								</salmon:a>
							</tr>
							<tr height="20">
							</tr>
							<tr>
								<td><salmon:text name="completo1" text="Completó"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="completo2" text=""
									font="ColumnCaptionFont"
									datasource="dsRecepciones:user_completa.nombre_completo" /></td>
							</tr>
							<tr>
								<td><salmon:text name="recibe1" text="Recibió"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpLegajoLegajo" name="legajo1" size="6"
									maxlength="10" displayformat="##########0"
									datasource="dsRecepciones:recepciones_compras.user_id_recibe"
									popupheight="450" popupwidth="500" usepopup="true"
									showdescription="false"></salmon:lookup> <salmon:text
									name="recibe2" text=""
									datasource="dsRecepciones:legajos.APEYNOM" /></td>
							</tr>
							<tr>
								<td><salmon:text name="proveedor1" text="Proveedor"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProveedores" name="proveedor2" size="6"
									maxlength="10" displayformat="#########0"
									descriptiondatasource="dsRecepciones:proveedores.nombre"
									datasource="dsRecepciones:recepciones_compras.proveedor_id"
									popupheight="450" popupwidth="500" usepopup="true"
									showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha1" text="Fecha"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha2" text=""
									displayformatlocalekey="DateTimeFormat"
									datasource="dsRecepciones:recepciones_compras.fecha" /></td>
							</tr>
							<tr>
								<td><salmon:text name="observaciones1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td colspan="3"><salmon:input type="textarea"
									name="observaciones2" cols="50" rows="3" wrap="HARD"
									maxlength="255"
									datasource="dsRecepciones:recepciones_compras.observaciones"></salmon:input></td>
								<td width="10"></td>
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
			mode="Display_single_page" caption="Artículos" width="100%"
			datasource="dsDetalle" addbuttonvisible="false"
			savebuttonvisible="false">
			<salmon:datatable name="datatable2" width="100%"
				datasource="dsDetalle" rowsperpage="5">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="seleccion_detalle1" text="-X-"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="orden_de_compra_id1" text="OC"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo1" text="Código de Artículo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcion3"
								text="Nº de detalle - Descripción de artículo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="unidad_medida1" text="UM"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_recibida1" text="Cantidad"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_excedencia1" text="Excedido"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="almacen1" text="Almacén"
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
							<salmon:text name="orden_de_compra_id2" text=""
								datasource="dsDetalle:detalle_sc.orden_compra_id"
								font="ColumnCaptionFont" />
						</salmon:td>
						<salmon:td>
						<salmon:a href="none" name="lnkConversiones"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsDetalle:'%AbmcConversiones?articulo_id='+detalle_sc.articulo_id+'&unidad_medida_id='+detalles_rc.unidad_medida_id">
								<salmon:text name="nombre_articulo1" text="" font="DefaultFont"	
								datasource="dsDetalle:articulos.nombre"	/>
							</salmon:a>							
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpArticulosParaRecepcion" name="articulo2" size="6"
								maxlength="15" datasource="dsDetalle:detalles_rc.detalle_sc_id"
								descriptiondatasource="dsDetalle:articulos.descripcion"
								popupheight="600" popupwidth="900" usepopup="true"
								popupposition="0" showdescription="true"></salmon:lookup>							
								-
								<salmon:text name="descripcion_completa_articulo1" text=""
								datasource="dsDetalle:detalle_sc.descripcion"
								font="ColumnCaptionFont" />
						</salmon:td>
						<salmon:td>
							<salmon:input type="select" name="unidad_medida2"
								datasource="dsDetalle:detalles_rc.unidad_medida_id">
								<salmon:option display="abc" key="123"
									table="inventario.unidades_medida"
									keycolumn="unidades_medida.unidad_medida_id"
									displaycolumn="unidades_medida.nombre" nulloption="true"></salmon:option>
							</salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_recibida2" size="8"
								maxlength="15"
								datasource="dsDetalle:detalles_rc.cantidad_recibida"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="text" name="cantidad_excedencia2" size="8"
								maxlength="15"
								datasource="dsDetalle:detalles_rc.cantidad_excedencia"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:input type="select" name="almacen2" size="30"
								datasource="dsDetalle:detalles_rc.almacen_id">
								<salmon:option display="abc" key="123"
									table="inventario.almacenes" keycolumn="almacen_id"
									displaycolumn="nombre" nulloption="false"></salmon:option>
							</salmon:input>
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
