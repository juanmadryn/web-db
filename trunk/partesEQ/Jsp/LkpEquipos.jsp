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
	<salmon:datasource name="dsTipo" type="MODEL" dbprofile="partesEQ"
		model="partesEQ.models.EquipoModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Equipos" qbebuilder="dsQBE"
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
			datasource="dsTipo" searchformdisplaybox="searchformdisplaybox1"
			lookupreturnexp="equipos.codigo_inventario"
			lookupdescreturnexp="equipos.nombre">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsTipo">
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
						<salmon:td>
							<salmon:text name="codinvCAP12" text="Código inventario"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="tipoIdTXT6"
								text="equipos.equipo_id Goes Here" font="DefaultFont"
								datasource="dsTipo:equipos.equipo_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="equipos.nombre Goes Here" font="DefaultFont"
								datasource="dsTipo:equipos.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="equipos.descripcion Goes Here" font="DefaultFont"
								datasource="dsTipo:equipos.descripcion" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="codinvTXT8"
								text="equipos.codigo_inventario Goes Here" font="DefaultFont"
								datasource="dsTipo:equipos.codigo_inventario" />
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
