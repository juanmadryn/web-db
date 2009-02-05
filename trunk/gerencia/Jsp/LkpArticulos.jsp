<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page />
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar_nombre" type="STARTS_WITH_IGNORE_CASE_EXTENDED" columns="articulos_extendidos.nombre" />
		<salmon:qbecriteria name="buscar_descripcion" type="STARTS_WITH_IGNORE_CASE_EXTENDED" columns="articulos_extendidos.descripcion,articulos_extendidos.descripcion_completa" />
	</salmon:datasource>
	<salmon:datasource name="dsArticulos" type="MODEL" dbprofile="inventario"
		model="inventario.models.ArticulosExtendidosModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Artículos" qbebuilder="dsQBE"
			listformdisplaybox="listformdisplaybox1"
			addbuttonvisible="False"
			cancelbuttonvisible="False">
			<table width="100%">
				<tr>
					<td><salmon:text name="nombre1" text="Código"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="nombre2" size="30"
						maxlength="90" datasource="dsQBE:buscar_nombre"></salmon:input></td>
				</tr>
				<tr>
					<td><salmon:text name="descripcion1" text="Descripción"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="descripcion2" size="30"
						maxlength="90" datasource="dsQBE:buscar_descripcion"></salmon:input></td>
				</tr>
			</table>
		</salmon:searchformdisplaybox>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="" width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsArticulos" searchformdisplaybox="searchformdisplaybox1"
			lookupreturnexp="articulos_extendidos.nombre"
			lookupdescreturnexp="articulos_extendidos.descripcion+' - '+articulos_extendidos.descripcion_completa+' - X '+articulos_extendidos.nombre_unidad">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsArticulos">
				<salmon:datatableheader>
					<salmon:tr>						
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP12" text="Descripcion"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCompletaCAP12" text="Descripción Completa"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombre_unidad_medida1" text="UM"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>						
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="" font="DefaultFont"
								datasource="dsArticulos:articulos_extendidos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="" font="DefaultFont"
								datasource="dsArticulos:articulos_extendidos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCompletaTXT9"
								text="" font="DefaultFont"
								datasource="dsArticulos:articulos_extendidos.descripcion_completa" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombre_unidad_medida2"
								text="" font="DefaultFont"
								datasource="dsArticulos:articulos_extendidos.nombre_unidad" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<salmon:endbody />
<salmon:endpage />
</html>
