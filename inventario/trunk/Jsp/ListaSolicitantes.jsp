<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.ListaSolicitantesController" />
<html>
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	</td>
	<!--Page Content Begin-->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsDetalleSc" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.DetalleSCModel"
		autoretrieve="Never">
	</salmon:datasource>
	<salmon:box name="box1" width="100%">
		<salmon:input type="submit" name="cerrar" value="Cerrar"
			onclick="self.close()"></salmon:input>
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Solicitantes" width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsDetalleSc" autocreatelink="false">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsDetalleSc">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="apeynomCAP1" text="Nombre Completo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroSolicitudCAP2" text="Nro. Solicitud"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP3" text="Descripcion" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaSolicitudCAP4" text="Fecha de Solicitud"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaAprobacionCAP5" text="Fecha de aprobación"
								font="TableHeadingFont" />
						</salmon:td>						
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="apeynomTXT1"
								text="nombre_completo_solicitante Goes Here" font="DefaultFont"
								datasource="dsDetalleSc:nombre_completo_solicitante" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nroSolicitudTXT2"
								text="detalle_sc.solicitud_compra_id Goes Here" font="DefaultFont"
								datasource="dsDetalleSc:detalle_sc.solicitud_compra_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT3"
								text="solicitudes_compra.descripcion Goes Here" font="DefaultFont"
								datasource="dsDetalleSc:solicitudes_compra.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaSolicitudTXT4"
								text="solicitudes_compra.fecha_solicitud Goes Here" font="DefaultFont"
								displayformat="dd/MM/yyyy HH:mm"
								datasource="dsDetalleSc:solicitudes_compra.fecha_solicitud" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaAprobacionTXT5"
								text="solicitudes_compra.fecha_firma Goes Here" font="DefaultFont"
								displayformat="dd/MM/yyyy HH:mm"
								datasource="dsDetalleSc:solicitudes_compra.fecha_aprobacion" />
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