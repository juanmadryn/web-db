<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page
	controller="inventario.controllers.LkpArticulosParaRecepcionController" />
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	</td>
	<!--Page Content Begin-->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex"
			columns="articulos_comprados.orden_compra_id,articulos_comprados.descripcion" />
	</salmon:datasource>
	<salmon:datasource name="dsArticulosComprados" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ArticulosCompradosModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Artículos para recepcionar" qbebuilder="dsQBE"
			listformdisplaybox="listformdisplaybox1" addbuttonvisible="False">
			<table width="100%">
				<tr>
					<td><salmon:text name="buscar1" text="Buscar"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="buscar2" size="30"
						maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
				</tr>
			</table>
		</salmon:searchformdisplaybox>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="" width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsArticulosComprados"
			lookupreturnexp="articulos_comprados.detalle_sc_id"
			lookupdescreturnexp="articulos_comprados.descripcion"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsArticulosComprados">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="orden_compra_id1" text="Nº de OC"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo1" text="Articulo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proveedor1" text="Proveedor"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha1" text="Fecha" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador1" text="Comprador"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="orden_compra_id2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.orden_compra_id" displayformat="####0"/>
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.nombre" /> -
								<salmon:text name="articulo3" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.descripcion" /> -
								<salmon:text name="articulo4" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.descripcion_completa" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proveedor2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.proveedor_nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.fecha" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.comprador" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!--Page Content End-->
</salmon:form>
<salmon:endbody />
<salmon:endpage />
</html>