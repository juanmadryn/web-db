<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.ConsultaSolicitudCompraController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="n" type="IN"
			columns="solicitudes_compra.solicitud_compra_id" />
		<salmon:qbecriteria name="desde" type="GTE"
			columns="solicitudes_compra.fecha_solicitud" />
		<salmon:qbecriteria name="hasta" type="LTE"
			columns="solicitudes_compra.fecha_solicitud" />
		<salmon:qbecriteria name="estado" type="IN"
			columns="solicitudes_compra.estado" />
		<salmon:qbecriteria name="solicitante" type="IN"
			columns="solicitudes_compra.user_id_solicita" />
	</salmon:datasource>
	<salmon:datasource name="dsSolicitudes" type="MODEL"
		dbprofile="inventario" model="inventario.models.SolicitudCompraModel"
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
						caption="Consulta de Solicitudes de Compra"
						name="searchformdisplaybox1" buttondisplaylocation="BELOW_TABLE" searchbuttonvisible="true"
						addbuttonvisible="False" qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="n1" text="Nº"
									font="ColumnCaptionFont" /></td>
								<td colspan="3"><salmon:input name="n2" type="text"
									datasource="dsQBE:n">
								</salmon:input></td>
								<td><salmon:text name="estado1" text="Estado"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input name="estado2" type="select"
									datasource="dsQBE:estado">
									<salmon:option display="abc" key="123"
										table="infraestructura.estados" criteria="circuito='0006'"
										keycolumn="estado" displaycolumn="nombre" nulloption="true"></salmon:option>
								</salmon:input></td>

							</tr>
							<tr>
								<td><salmon:text name="fechadesde1" text="Fecha SC desde"
									font="ColumnCaptionFont" /></td>
								<td colspan="3"><salmon:input type="text"
									name="fechadesde2" size="10"
									datasource="dsQBE:desde" maxlength="10"></salmon:input></td>
								<td><salmon:text name="fechahasta1" text="Fecha SC hasta"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="fechahasta2" size="10"
									datasource="dsQBE:hasta"
									maxlength="10"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="solicitante1" text="Solicitante"
									font="ColumnCaptionFont" /></td>
								<td colspan="2"><salmon:input type="select"
									name="solicitante2" size="30" datasource="dsQBE:solicitante"
									maxlength="50">
									<salmon:option display="abc" key="123"
										table="inventario.solicitantes" keycolumn="user_id"
										displaycolumn="nombre_completo" nulloption="true"></salmon:option>
								</salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1" addbuttonvisible="false" cancelbuttonvisible="false" savebuttonvisible="false" deletebuttonvisible="false"
						caption="Detalle de solicitud" width="100%"
						datasource="dsSolicitudes"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="numero1" text="Nº"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="numero2" text=""
									datasource="dsSolicitudes:solicitudes_compra.solicitud_compra_id"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcion1" text="Descripción"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="descripción2" text=""
									datasource="dsSolicitudes:solicitudes_compra.descripcion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="observacion1" text="Observaciones"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="observacion2" text="" 
									datasource="dsSolicitudes:solicitudes_compra.observaciones"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_solicitante1"
									text="Solicitante" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_solicitante2"
									text="" datasource="dsSolicitudes:nombre_completo_solicitante"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="nombre_completo_comprador1"
									text="Comprador" font="TableHeadingFont" /></td>
								<td><salmon:text name="nombre_completo_comprador2" text=""
									datasource="dsSolicitudes:nombre_completo_comprador"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_solicitud1"
									text="Fecha de solicitud" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_solicitud2" text="" 
									datasource="dsSolicitudes:solicitudes_compra.fecha_solicitud"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_aprobacion1"
									text="Fecha de aprobación" font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_aprobacion2" text="" 
									datasource="dsSolicitudes:solicitudes_compra.fecha_aprobacion"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="fecha_oc1" text="Fecha de OC"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="fecha_oc2" text="" 
									datasource="dsSolicitudes:solicitudes_compra.fecha_oc"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="centro_costo1"
									text="Centro de costo" font="TableHeadingFont" /></td>
								<td><salmon:text name="centro_costo2" text="" 
									datasource="dsSolicitudes:centro_costo.nombre"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="proyectos_proyecto1" text="Proyecto"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="proyectos_proyecto2" text=""
									datasource="dsSolicitudes:proyectos.proyecto"></salmon:text>
								<salmon:text name="separador" text=" - " ></salmon:text>
								<salmon:text name="proyectos_nombre2" text="" 
									datasource="dsSolicitudes:proyectos.nombre"></salmon:text></td>
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
			datasource="dsSolicitudes"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsSolicitudes">
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
								datasource="dsSolicitudes:solicitudes_compra.solicitud_compra_id" />

						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT2" text="nombre Goes Here"
								font="DefaultFont"
								datasource="dsSolicitudes:solicitudes_compra.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="solicitante_nombreTXT3"
								text="descripcion Goes Here" font="DefaultFont"
								datasource="dsSolicitudes:nombre_completo_solicitante" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="comprador_nombreTXT3"
								text="descripcion Goes Here" font="DefaultFont"
								datasource="dsSolicitudes:nombre_completo_comprador" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha_solicitudTXT4" text="cliente Goes Here"
								font="DefaultFont"
								datasource="dsSolicitudes:solicitudes_compra.fecha_solicitud" />
						</salmon:td>						
						<salmon:td>
							<salmon:text name="estadoTXT3" text="descripcion Goes Here"
								font="DefaultFont" datasource="dsSolicitudes:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:a href="none" name="lnksolicitud1"
								onclick="document.forms['bannerForm'].submit();"
								datasource="dsSolicitudes:'%AbmcSolicitudCompra?solicitud_compra_id='+solicitudes_compra.solicitud_compra_id">
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