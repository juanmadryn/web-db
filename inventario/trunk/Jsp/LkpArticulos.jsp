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
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsArticulos" type="MODEL" dbprofile="inventario"
		model="inventario.models.ArticulosModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Clases de Artículos" qbebuilder="dsQBE"
			listformdisplaybox="listformdisplaybox1"
			addbuttonvisible="False"
			cancelbuttonvisible="False">
			<table width="100%">
				<tr>
					<td><salmon:text name="buscarCAP5" text="Buscar"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="buscarTE3" size="30"
						maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
				</tr>
			</table>
		</salmon:searchformdisplaybox>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsArticulos" searchformdisplaybox="searchformdisplaybox1"
			lookupreturnexp="articulos.nombre"
			lookupdescreturnexp="articulos.descripcion">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsArticulos">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="tipoIdCAP10" text="Id"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Descripcion"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCompletaCAP12" text="Descripción Completa"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="tipoIdTXT6"
								text="tipo_equipo.tipo_equipo Goes Here" font="DefaultFont"
								datasource="dsArticulos:articulos.articulo_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="tipo_equipo.nombre Goes Here" font="DefaultFont"
								datasource="dsArticulos:articulos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="tipo_equipo.descripcion Goes Here" font="DefaultFont"
								datasource="dsArticulos:articulos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCompletaTXT9"
								text="tipo_equipo.descripcion_completa Goes Here" font="DefaultFont"
								datasource="dsArticulos:articulos.descripcion_completa" />
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
