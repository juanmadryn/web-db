<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function mostrarOcultar()
{		
	try {
	var almacen = document.getElementsByName('htmlPageTopContainer_PageForm_box1_table2_table2TRRow0_table2TDRow0_searchformdisplaybox1_table2TDRow1_almacen2')[0];
	var periodo = document.getElementsByName('htmlPageTopContainer_PageForm_box1_table2_table2TRRow0_table2TDRow0_searchformdisplaybox1_periodo2')[0];
	

	
   if(periodo.value != '' && almacen.value != '') {
      //document.getElementById('div1').style.display = 'block';
      //$('div1').appear();
      Effect.BlindDown('div1') 
      return false;     
   } else {   	
      //document.getElementById('div1').style.display = 'none';
      //$('div1').hide();
      Effect.BlindUp('div1')
      return false;       
   }
      
   } catch (e) {
   	alert("Java Exception: " + e);
   }
}
//-->
</script>
<salmon:page
	controller="inventario.controllers.ConsultaResumenesSaldoArticulosController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="articulo" type="CONTAINS"
			columns="resumen_saldo_articulos.articulo_id,articulos_extendidos.nombre,articulos_extendidos.descripcion" />
		<salmon:qbecriteria name="almacen" type="IN"
			columns="resumen_saldo_articulos.almacen_id" />
		<salmon:qbecriteria name="periodo" type="IN"
			columns="resumen_saldo_articulos.periodo" />
	</salmon:datasource>
	<salmon:datasource name="dsResumenes" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ResumenSaldoArticulosModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsComprobantes" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ComprobanteMovimientoArticuloModel"
		autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<div id="div1" style="display: none"><salmon:table name="table3">
		<td><salmon:a href="" target="_blank" name="imprimirReporteBUT1"
			onclick="document.forms['bannerForm'].submit();">
			<salmon:img name="imprimirTXT1" src="%ImageDirectory/logo_excel.gif"
				height="25" srclocalekey="bannerImageSource" />
		</salmon:a> <salmon:text name="espacio" text=" " font="TableHeadingFont" /> <salmon:a
			href="" target="_blank" name="imprimirReporteBUT2"
			onclick="document.forms['bannerForm'].submit();">
			<salmon:img name="imprimirTXT2" src="%ImageDirectory/pdf.jpg"
				height="25" srclocalekey="bannerImageSource" />
		</salmon:a></td>
		<td></td>
	</salmon:table></div>
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox
						caption="Consulta de Resumenes de Stock de Artículos"
						name="searchformdisplaybox1" buttondisplaylocation="BELOW_TABLE"
						searchbuttonvisible="true" addbuttonvisible="False"
						qbebuilder="dsQBE" width="100%">
						<table width="100%">
							<tr>
								<td><salmon:text name="articulo1" text="Artículo"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpArticulos" name="articulo2" size="40"
									maxlength="15" datasource="dsQBE:articulo" popupheight="450"
									popupwidth="600" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen1" text="Almacén"
									font="TableHeadingFont" /></td>
								<salmon:td>
									<salmon:input type="select" name="almacen2" size="30"
										datasource="dsQBE:almacen">
										<salmon:option display="abc" key="123"
											table="inventario.almacenes" keycolumn="almacen_id"
											displaycolumn="nombre" nulloption="true"
											nulloptiontext="Todos"></salmon:option>
									</salmon:input>
								</salmon:td>
							</tr>
							<tr>
								<td><salmon:text name="periodo1" text="Período"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="periodo2" size="10"
									datasource="dsQBE:periodo" maxlength="10"
									displayformat="MM-yyyy">
									<salmon:option display="abc" key="123"
										table="inventario.periodo_en_stock" keycolumn="periodo"
										displaycolumn="periodo_formateado" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false"
						caption="Detalle del stock" width="100%" datasource="dsResumenes"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="articulo3" text="Artículo"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="articulo4" text=""
									datasource="dsResumenes:articulos.nombre+' - '+articulos.descripcion+' - '+articulos.descripcion_completa"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen3" text="Almacen"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="almacen4" text=""
									datasource="dsResumenes:almacenes.nombre"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="periodo3" text="Periodo"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="periodo4" text=""
									datasource="dsResumenes:resumen_saldo_articulos.periodo"
									displayformat="MM-yyyy"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="unidad_medida_patron1" text="UM"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="unidad_medida_patron2" text=""
									datasource="dsResumenes:articulos.unidad_patron"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="stock_en_mano1" text="Stock"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="stock_en_mano2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.stock_en_mano"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="reservado1" text="Reservado"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="reservado2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.reservado"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="en_proceso1" text="En proceso"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="en_proceso2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.en_proceso"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="total_ingresos1"
									text="Total de ingresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="total_ingresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.total_ingresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="total_egresos1"
									text="Total de egresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="total_egresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.total_egresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cant_transacciones_ingresos1"
									text="Transacciones de ingresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="cant_transacciones_ingresos2"
									text=""
									datasource="dsResumenes:resumen_saldo_articulos.cant_transacciones_ingresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cant_transacciones_egresos1"
									text="Transacciones de egresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="cant_transacciones_egresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.cant_transacciones_egresos"></salmon:text></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Resumen de stock de artículos"
			width="100%" datasource="dsResumenes" autocreatelink="true"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsResumenes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo5" text="Artículo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="almacen5" text="Almacen"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="periodo5" text="Periodo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="unidad_medida_patron3" text="UM"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="stock_en_mano3" text="Stock"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservado3" text="Reservado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="en_proceso3" text="En proceso"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo6" text=""
								datasource="dsResumenes:articulos.nombre+' - '+articulos.descripcion+' - '+articulos.descripcion_completa"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="almacen6" text=""
								datasource="dsResumenes:almacenes.nombre"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="periodo6" text=""
								datasource="dsResumenes:resumen_saldo_articulos.periodo"
								displayformat="MM-yyyy"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="unidad_medida_patron4" text=""
								datasource="dsResumenes:articulos.unidad_patron"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="stock_en_mano4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.stock_en_mano"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservado4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.reservado"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="en_proceso4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.en_proceso"></salmon:text>
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box3" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page"
			caption="Comprobantes asociados al resumen" width="100%"
			datasource="dsComprobantes"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable2" width="100%" rowsperpage="5"
				datasource="dsComprobantes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="n1" text="Nº" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="tipo_movimiento1" text="Tipo de movimiento"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha1" text="Fecha" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="completo1" text="Completó"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="retiro1" text="Retiró/Entregó"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="recepcion1" text="Recepción"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:a href="none" name="lnkcomprobante1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsComprobantes:'%AbmComprobantesMovimientoArticulos?comprobante_movimiento_id='+comprobante_movimiento_articulo.comprobante_movimiento_id">
								<salmon:text name="n2" text=""
									datasource="dsComprobantes:comprobante_movimiento_articulo.comprobante_movimiento_id"></salmon:text>
							</salmon:a>
						</salmon:td>
						<salmon:td>
							<salmon:text name="tipo_movimiento2" text=""
								datasource="dsComprobantes:tipo_movimiento_articulo.nombre"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha2" text=""
								datasource="dsComprobantes:comprobante_movimiento_articulo.fecha"
								displayformatlocalekey="DateFormat"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="completo2" text=""
								datasource="dsComprobantes:website_user_preparador.nombre_completo"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="retiro2" text=""
								datasource="dsComprobantes:legajos.APEYNOM"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnkrecepcion1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsComprobantes:'%AbmRecepciones?recepcion_compra_id='+comprobante_movimiento_articulo.recepcion_compra_id">
								<salmon:text name="recepcion2" text=""
									datasource="dsComprobantes:comprobante_movimiento_articulo.recepcion_compra_id"></salmon:text>
							</salmon:a>
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
