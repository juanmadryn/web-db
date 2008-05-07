<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.ConsultaOrdenesCompraController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="nroOc" type="IN"
			columns="ordenes_compra.orden_compra_id" />
		<salmon:qbecriteria name="desde" type="GTE"
			columns="ordenes_compra.fecha" />
		<salmon:qbecriteria name="hasta" type="LTE"
			columns="ordenes_compra.fecha" />
		<salmon:qbecriteria name="estado" type="IN"
			columns="ordenes_compra.estado" />
		<salmon:qbecriteria name="comprador" type="IN"
			columns="ordenes_compra.user_id_comprador" />
	</salmon:datasource>
	<salmon:datasource name="dsOrdenes" type="MODEL"
		dbprofile="inventario" model="inventario.models.OrdenesCompraModel"
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
						caption="Consulta de Ordenes de Compra"
						name="searchformdisplaybox1" searchbuttonvisible="false"
						addbuttonvisible="false" qbebuilder="dsQBE" width="100%"
						buttondisplaylocation="BOTTOM">
						<table width="100%">
							<tr>
								<td><salmon:text name="n1" text="Nº"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="n2" type="text"
									datasource="dsQBE:nroOc">
								</salmon:input></td>
								<td><salmon:text name="estado1" text="Estado"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="estado2" type="select"
									datasource="dsQBE:estado">
									<salmon:option display="abc" key="123"
										table="infraestructura.estados" criteria="circuito='0008'"
										keycolumn="estado" displaycolumn="nombre" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>

							</tr>
							<tr>
								<td><salmon:text name="fechadesde1" text="Fecha OC desde"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text"
									name="fechadesde2" size="10"
									datasource="dsQBE:desde" maxlength="10"></salmon:input></td>
								<td><salmon:text name="fechahasta1" text="Fecha OC hasta"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsQBE:hasta"
									maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="comprador1" text="Comprador"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="select"
									name="comprador2" size="30" datasource="dsQBE:comprador"
									maxlength="50">
									<salmon:option display="abc" key="123"
										table="inventario.compradores" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true" 
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
								<td><salmon:text name="solicitante1" text="Solicitante"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="select"
									name="solicitante2" size="30" maxlength="50">
									<salmon:option display="abc" key="123"
										table="inventario.solicitantes" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true" 
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1" 
						addbuttonvisible="false" cancelbuttonvisible="false" savebuttonvisible="false" deletebuttonvisible="false"
						caption="Detalle de Orden de Compra" datasource="dsOrdenes" width="100%"
						listformdisplaybox="listformdisplaybox1"
						headerbgcolor="#fffd99" bgcolor="fffdce">
						<table width="100%">
							<tr>
								<td><salmon:text name="numero1" text="Nº"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="numero2" text=""
									datasource="dsOrdenes:ordenes_compra.orden_compra_id"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcion1" text="Descripción"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="descripcion2" text=""
									datasource="dsOrdenes:ordenes_compra.descripcion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="observacion1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="observacion2" text="" 
									datasource="dsOrdenes:ordenes_compra.observaciones"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_comprador2"
									text="" datasource="dsOrdenes:nombre_completo_comprador"></salmon:text></td>
							</tr>							
							<tr>
								<td><salmon:text name="fecha1"
									text="Fecha de solicitud" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha2" text="" displayformatlocalekey="DateTimeFormat"
									datasource="dsOrdenes:ordenes_compra.fecha"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_estimada_entrega1"
									text="Fecha estimada de entrega" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_estimada_entrega2" text="" displayformatlocalekey="DateFormat"
									datasource="dsOrdenes:ordenes_compra.fecha_estimada_entrega"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_entrega_completa1" text="Fecha de entrega completa"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_entrega_completa2" text="" displayformatlocalekey="DateFormat" 
									datasource="dsOrdenes:ordenes_compra.fecha_entrega_completa"></salmon:text></td>
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
			datasource="dsOrdenes" caption="Ordenes de Compra"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsOrdenes">
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
							<salmon:text name="descripcionCAP4" text="Comprador"
								font="TableHeadingFont" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="fechaCAP5" text="Fecha"
								font="TableHeadingFont" />
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
								datasource="dsOrdenes:ordenes_compra.orden_compra_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT2" text="descripcion Goes Here"
								font="DefaultFont"
								datasource="dsOrdenes:ordenes_compra.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitante_nombreTXT3"
								text="solicitante Goes Here" font="DefaultFont"
								datasource="dsOrdenes:nombre_completo_comprador" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="fechaTXT4" text="fecha Goes Here"
								font="DefaultFont" displayformatlocalekey="DateTimeFormat"
								datasource="dsOrdenes:ordenes_compra.fecha" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoTXT3" text="estado Goes Here"
								font="DefaultFont" datasource="dsOrdenes:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnksolicitud1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsOrdenes:'%EditarOrdenCompra?orden_compra_id='+ordenes_compra.orden_compra_id">
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