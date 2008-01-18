<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="partesEQ.controllers.AbmcChoferesController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsChofer" type="MODEL" dbprofile="partesEQ"
		model="partesEQ.models.ChoferesModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Choferes" qbebuilder="dsQBE">
						<table width="100%">
							<tr>
								<td><salmon:text name="buscarCAP5" text="Buscar"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="buscarTE3" size="30"
									maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Editar chofer" width="100%" datasource="dsChofer"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="choferCAP12" text="Id Chofer"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="idTE5" text=""
								datasource="dsChofer:choferes.chofer_id"></salmon:text>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="legajoCAP14" text="Nro de Legajo"
									font="ColumnCaptionFont" /></td>
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpLegajoLegajo" name="legajoTE1" size="6" 
									maxlength="15" displayformat="##########0" 
									datasource="dsChofer:choferes.nro_legajo"
									popupheight="450" popupwidth="500" usepopup="true"									
									showdescription="False"></salmon:lookup>
								</td>
							</tr>
							<tr>
								<td><salmon:text name="desdeCAP16" text="Fecha desde"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="desdeTE6" size="10"
									maxlength="10" displayformat="dd/MM/yyyy" 
									datasource="dsChofer:choferes.desde"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="hastaCAP18" text="Fecha hasta"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="hastaTE6"
									size="10" maxlength="10" displayformat="dd/MM/yyyy"
									datasource="dsChofer:choferes.hasta"></salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page"
			caption="Altas/Bajas/Modificaciones/Consultas" width="100%"
			datasource="dsChofer" searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsChofer">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="idchoferCAP10" text="Id Chofer"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nrolegajoCAP11" text="Nro de Legajo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apeynomCAP12" text="Apellido y Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="desdeCAP13" text="Desde"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="hastaCAP14" text="Hasta"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="idchoferTXT6"
								text="choferes.chofer_id Goes Here" font="DefaultFont"
								datasource="dsChofer:choferes.chofer_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nrolegajoTXT7"
								text="choferes.nro_legajo Goes Here" font="DefaultFont"
								datasource="dsChofer:choferes.nro_legajo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apeynomTXT8" text="choferes.apeynom Goes Here"
								font="DefaultFont" datasource="dsChofer:choferes.apeynom" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="desdeTXT9"
								text="choferes.desde Goes Here" font="DefaultFont"
								datasource="dsChofer:choferes.desde" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="hastaTXT10"
								text="choferes.hasta Goes Here" font="DefaultFont"
								datasource="dsChofer:choferes.hasta" />
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
