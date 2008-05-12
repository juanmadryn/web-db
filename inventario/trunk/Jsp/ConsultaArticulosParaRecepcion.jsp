<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.ConsultaArticulosParaRecepcionController" />
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
		<salmon:qbecriteria name="n" type="IN"
			columns="articulos_comprados.orden_compra_id" />
		<salmon:qbecriteria name="comprador" type="IN"
			columns="articulos_comprados.user_id_comprador" />
		<salmon:qbecriteria name="solicitante" type="IN"
			columns="articulos_comprados.user_id_solicitante" />
		<salmon:qbecriteria name="proveedor_id" type="IN"
			columns="articulos_comprados.proveedor_id" />
	</salmon:datasource>
	<salmon:datasource name="dsArticulosComprados" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ArticulosCompradosModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox
						caption="Consulta de Recepciones de Compra"
						name="searchformdisplaybox1" buttondisplaylocation="BELOW_TABLE"
						searchbuttonvisible="true" addbuttonvisible="False"
						qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="n1" text="Nº"
									font="TableHeadingFont" /></td>
								<td><salmon:input name="n2" type="text"
									datasource="dsQBE:n">
								</salmon:input></td>
								<td width="55"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><salmon:text name="fechadesde1" text="Fecha desde"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechadesde2" size="10"
									datasource="dsPeriodo:desde" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
								<td width="55"></td>
								<td><salmon:text name="fechahasta1" text="Fecha hasta"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsPeriodo:hasta" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="solicitante1" text="Solicitante"
									font="TableHeadingFont" /></td>
								<td><salmon:input name="solicitante2" type="select"
									datasource="dsQBE:solicitante">
									<salmon:option display="abc" key="123"
										table="inventario.solicitantes" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
								<td width="55"></td>
								<td><salmon:text name="comprador1" text="Comprador"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="comprador2"
									size="30" datasource="dsQBE:comprador" maxlength="50">
									<salmon:option display="abc" key="123"
										table="inventario.compradores" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="proveedor1" text="Proveedor"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProveedores" name="proveedor2" size="6"
									maxlength="10" displayformat="#########0"
									datasource="dsQBE:proveedor_id" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup>
								</td>
								<td width="55"></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
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
							<salmon:text name="orden_compra_id1" text="Articulo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="articulo1" text="OC" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="proveedor1" text="Proveedor"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha1" text="Fecha" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitante3" text="Solicitante"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador3" text="Comprador"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_pedida1" text="Cant. pedida"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cantidad_recibida1" text="Cant. recibida"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.nombre" />
							<salmon:text name="separador1" text="-" font="DefaultFont" />
							<salmon:text name="articulo3" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.descripcion" />
							<salmon:text name="separador2" text="-" font="DefaultFont" />
							<salmon:text name="articulo4" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.descripcion_completa" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="orden_compra_id2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.orden_compra_id"
								displayformat="####" />
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
							<salmon:text name="solicitante4" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.solicitante" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador4" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.comprador" />
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="cantidad_pedida2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.cantidad_pedida"
								displayformatlocalekey="CantidadPedidaFormat" />
						</salmon:td>
						<salmon:td align="RIGHT">
							<salmon:text name="cantidad_recibida2" text="" font="DefaultFont"
								datasource="dsArticulosComprados:articulos_comprados.cantidad_recibida"
								displayformatlocalekey="CantidadPedidaFormat" />
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