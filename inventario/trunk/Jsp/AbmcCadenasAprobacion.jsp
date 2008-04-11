<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="inventario.controllers.AbmcCadenasAprobacionController" />

<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsConfiguracion" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.ConfiguracionModel"
		autoretrieve="Always">		
	</salmon:datasource>
	<salmon:datasource name="dsCadenasAprobacion" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.CadenasAprobacionModel"
		autoretrieve="Always">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Cadenas de aprobación"
			width="100%" datasource="dsConfiguracion">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="10"
				datasource="dsConfiguracion">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="numero1" text="Nº" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombre1" text="Nombre" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcion1" text="Descripcion"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observaciones1" text="Observaciones"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cardinalidad1" text="Cardinalidad"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="prioridad1" text="Prioridad"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="numero2" text="Nº" font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.configuracion_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombre2" text="Nombre" font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcion2" text="Descripcion"
								font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="observaciones2" text="Observaciones"
								font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.observaciones" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="cardinalidad2" text="Cardinalidad"
								font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.cardinalidad" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="prioridad2" text="Prioridad"
								font="TableHeadingFont"
								datasource="dsConfiguracion:configuracion.prioridad" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox2"
			mode="Display_single_page" caption="Firmantes" width="100%"
			datasource="dsCadenasAprobacion" addbuttonvisible="false"
			savebuttonvisible="false">
			<salmon:datatable name="datatable2" width="100%" rowsperpage="10"
				datasource="dsCadenasAprobacion">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
						</salmon:td>
						<salmon:td>
							<salmon:text name="firmante1" text="Firmante"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="orden1" text="Orden" font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:input type="checkbox" name="seleccion1"
								checkedtruevalue="1"></salmon:input>
						</salmon:td>
						<salmon:td>
							<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
								lookupurl="%LkpFirmantes" name="firmantes2" size="6"
								maxlength="15"
								datasource="dsCadenasAprobacion:cadenas_aprobacion.user_firmante"
								descriptiondatasource="dsCadenasAprobacion:website_user.nombre_completo"
								popupheight="450" popupwidth="500" usepopup="true"
								showdescription="true"></salmon:lookup>
						</salmon:td>
						<salmon:td>
							<salmon:input name="orden2" type="text" font="TableHeadingFont"
								datasource="dsCadenasAprobacion:cadenas_aprobacion.orden" />
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