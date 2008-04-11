<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.ListaFirmantesController" />
<html>
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	</td>
	<!--Page Content Begin-->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsFirmantes" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.InstanciasAprobacionModel" autoretrieve="Never">		
	</salmon:datasource>	
	<salmon:box name="box2" width="100%">	
	<salmon:input type="submit" name="cerrar" value="Cerrar" onclick="self.close()"></salmon:input>	
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsFirmantes">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsFirmantes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="apeynomCAP11" text="Nombre Completo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="loginNameCAP12" text="Orden"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estado1" text="Estado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fecha1" text="Fecha de firma"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="apeynomTXT7"
								text="website_user.nombre_completo Goes Here" font="DefaultFont"
								datasource="dsFirmantes:user_firmante.nombre_completo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="ordenTXT"
								text="website_user.login_name Goes Here" font="DefaultFont"
								datasource="dsFirmantes:instancias_aprobacion.orden" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="estadoTXT"
								text="website_user.login_name Goes Here" font="DefaultFont"
								datasource="dsFirmantes:estados.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="fechaTXT"
								text="website_user.login_name Goes Here" font="DefaultFont"
								datasource="dsFirmantes:instancias_aprobacion.fecha_accion" />
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