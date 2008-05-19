<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.ConsultaResumenesSaldoArticulosController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsPeriodo" type="SQL" autoretrieve="Never">
		<salmon:datasourcedef>
			<salmon:bucket name="desde" datatype="DATETIME" />
			<salmon:bucket name="hasta" datatype="DATETIME" />
		</salmon:datasourcedef>
	</salmon:datasource>
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="articulo" type="IN"
			columns="resumen_saldo_articulos.articulo_id" />
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
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
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
									font="TableHeadingFont" />
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpArticulos" name="articulo2" size="10"
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
									datasource="dsQBE:periodo" displayformat="MM-yyyy"
									maxlength="10">
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
						caption="Detalle de solicitud" width="100%"
						datasource="dsResumenes" listformdisplaybox="listformdisplaybox1">
						<table width="100%">							
							<tr>
								<td><salmon:text name="articulo3" text="Artículo"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="articulo4" text=""
									datasource="dsResumenes:articulos.nombre +' - '+ articulos.descripcion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen3" text="Almacen"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="almacen4" text=""
									datasource="dsResumenes:almacenes.nombre"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_solicitante1"
									text="Solicitante" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_solicitante2"
									text="" datasource="dsResumenes:nombre_completo_solicitante"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_comprador2" text=""
									datasource="dsResumenes:nombre_completo_comprador"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_solicitud1"
									text="Fecha de solicitud" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_solicitud2" text=""
									displayformat="dd/MM/yyyy HH:mm"
									datasource="dsResumenes:resumen_saldo_articulos.fecha_solicitud"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_aprobacion1"
									text="Fecha de aprobación" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_aprobacion2" text=""
									displayformat="dd/MM/yyyy HH:mm"
									datasource="dsResumenes:resumen_saldo_articulos.fecha_aprobacion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="proyectos_proyecto1" text="Proyecto"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="proyectos_proyecto2" text=""
									datasource="dsResumenes:proyectos.proyecto + ' - ' + proyectos.nombre"></salmon:text></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			datasource="dsResumenes" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsResumenes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="numeroCAP2" text="Nº" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="clienteCAP5" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP4" text="Solicitante"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP3" text="Comprador"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_solicitudCAP5" text="Fecha de solicitud"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_aprobacionCAP6"
								text="Fecha de aprobación" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoCAP5" text="Estado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="proyectoTXT1" text="proyecto Goes Here"
								font="DefaultFont"
								datasource="dsResumenes:resumen_saldo_articulos.solicitud_compra_id" />

						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT2" text="nombre Goes Here"
								font="DefaultFont"
								datasource="dsResumenes:resumen_saldo_articulos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitante_nombreTXT3"
								text="descripcion Goes Here" font="DefaultFont"
								datasource="dsResumenes:nombre_completo_solicitante" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador_nombreTXT3"
								text="descripcion Goes Here" font="DefaultFont"
								datasource="dsResumenes:nombre_completo_comprador" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_solicitudTXT4" text="cliente Goes Here"
								font="DefaultFont" displayformat="dd/MM/yyyy HH:mm"
								datasource="dsResumenes:resumen_saldo_articulos.fecha_solicitud" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_aprobacionTXT5"
								text="fecha aprobacion Goes Here" font="DefaultFont"
								displayformat="dd/MM/yyyy HH:mm"
								datasource="dsResumenes:resumen_saldo_articulos.fecha_aprobacion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoTXT3" text="descripcion Goes Here"
								font="DefaultFont" datasource="dsResumenes:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnksolicitud1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsResumenes:'%AbmcSolicitudCompra?solicitud_compra_id='+resumen_saldo_articulos.solicitud_compra_id">
								<salmon:text name="editar" text="Editar" />
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