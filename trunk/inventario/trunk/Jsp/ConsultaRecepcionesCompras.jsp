<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.ConsultaRecepcionesComprasController" />
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
			columns="recepciones_compras.recepcion_compra_id" />
		<salmon:qbecriteria name="estado" type="IN"
			columns="recepciones_compras.estado" />
		<salmon:qbecriteria name="usuario_completo" type="IN"
			columns="recepciones_compras.user_id_completa" />
		<salmon:qbecriteria name="proveedor_id" type="IN"
			columns="recepciones_compras.proveedor_id" />
	</salmon:datasource>
	<salmon:datasource name="dsRecepciones" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.RecepcionesComprasModel" autoretrieve="Never">
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
								<td><salmon:text name="estado1" text="Estado"
									font="TableHeadingFont" /></td>
								<td><salmon:input name="estado2" type="select"
									datasource="dsQBE:estado">
									<salmon:option display="abc" key="123"
										table="infraestructura.estados" criteria="circuito='0009'"
										keycolumn="estado" displaycolumn="nombre" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>

							</tr>
							<tr>
								<td><salmon:text name="fechadesde1" text="Fecha RC desde"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechadesde2" size="10"
									datasource="dsPeriodo:desde" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
								<td width="55"></td>
								<td><salmon:text name="fechahasta1" text="Fecha RC hasta"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsPeriodo:hasta" displayformat="dd/MM/yyyy"
									maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="solicitante1" text="Completó"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="solicitante2"
									size="30" datasource="dsQBE:usuario_completo" maxlength="50">
									<salmon:option display="abc" key="123"
										table="inventario.receptores" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
								<td width="55"></td>
								<td><salmon:text name="proveedor1" text="Proveedor"
									font="TableHeadingFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpProveedores" name="proveedor2" size="6"
									maxlength="10" displayformat="#########0"
									datasource="dsQBE:proveedor_id" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup>
								</td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			datasource="dsRecepciones"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsRecepciones">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="numeroCAP2" text="Nº" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP4" text="Completó"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP3" text="Recibió"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_solicitudCAP5" text="Fecha"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoCAP5" text="Estado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="clienteCAP5" text="Observaciones"
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
								datasource="dsRecepciones:recepciones_compras.recepcion_compra_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitante_nombreTXT3" text=""
								font="DefaultFont"
								datasource="dsRecepciones:user_completa.nombre_completo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador_nombreTXT3" text=""
								font="DefaultFont" datasource="dsRecepciones:legajos.apeynom" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_solicitudTXT4" text="cliente Goes Here"
								font="DefaultFont" displayformat="dd/MM/yyyy HH:mm"
								datasource="dsRecepciones:recepciones_compras.fecha" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoTXT3" text="descripcion Goes Here"
								font="DefaultFont" datasource="dsRecepciones:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT2" text="nombre Goes Here"
								font="DefaultFont"
								datasource="dsRecepciones:recepciones_compras.observaciones" />
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnksolicitud1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsRecepciones:'%AbmRecepciones?recepcion_compra_id='+recepciones_compras.recepcion_compra_id">
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