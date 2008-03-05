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
	<salmon:datasource name="dsCentroCosto" type="MODEL" dbprofile="inventario"
		model="inventario.models.CentroCostoModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Centros de Costo" qbebuilder="dsQBE"
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
			datasource="dsCentroCosto" searchformdisplaybox="searchformdisplaybox1"
			lookupreturnexp="centro_costo.nombre"
			lookupdescreturnexp="centro_costo.descripcion">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsCentroCosto">
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
							<salmon:text name="descripcionCAP12" text="Descripción"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="tipoIdTXT6"
								text="tipo_equipo.tipo_equipo Goes Here" font="DefaultFont"
								datasource="dsCentroCosto:centro_costo.centro_costo_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="tipo_equipo.nombre Goes Here" font="DefaultFont"
								datasource="dsCentroCosto:centro_costo.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="tipo_equipo.descripcion Goes Here" font="DefaultFont"
								datasource="dsCentroCosto:centro_costo.descripcion" />
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
