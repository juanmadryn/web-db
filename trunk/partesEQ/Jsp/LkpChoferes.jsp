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
	<salmon:datasource name="dsChoferes" type="MODEL" dbprofile="partesEQ"
		model="partesEQ.models.ChoferesModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Choferes" qbebuilder="dsQBE"
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
			datasource="dsChoferes" searchformdisplaybox="searchformdisplaybox1"
			lookupreturnexp="choferes.nro_legajo"
			lookupdescreturnexp="choferes.apeynom">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsChoferes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="choferIdCAP10" text="Id"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreCAP11" text="Legajo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionCAP12" text="Apellido y nombre"
								font="TableHeadingFont" />
						</salmon:td>						
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="choferIdTXT6"
								text="choferes.chofer_id Goes Here" font="DefaultFont"
								datasource="dsChoferes:choferes.chofer_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreTXT7"
								text="choferes.nro_legajo Goes Here" font="DefaultFont"
								datasource="dsChoferes:choferes.nro_legajo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="descripcionTXT8"
								text="choferes.apeynom Goes Here" font="DefaultFont"
								datasource="dsChoferes:choferes.apeynom" />
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
