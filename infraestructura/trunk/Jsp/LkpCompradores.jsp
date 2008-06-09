<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page />
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	<!--Page Content Begin-->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="busqueda" type="complex"
			columns="cpa50.COD_COMPRA, cpa50.NOM_COMPRA" />		
	</salmon:datasource>
	<salmon:datasource name="dsCpa50" type="SQL">
		<salmon:datasourcedef>
			<salmon:field tablename="cpa50" fieldname="ID_CPA50" />
			<salmon:field tablename="cpa50" fieldname="FILLEr" />
			<salmon:field tablename="cpa50" fieldname="COD_COMPRA" />
			<salmon:field tablename="cpa50" fieldname="NOM_COMPRA" />			
		</salmon:datasourcedef>
	</salmon:datasource>	
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Compradores" qbebuilder="dsQBE"
			listformdisplaybox="listformdisplaybox1" addbuttonvisible="False"
			cancelbuttonvisible="False">
			<table width="100%">
				<tr>
					<td><salmon:text name="buscarCAP1" text="Buscar"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="buscarTE3" size="10"
						maxlength="15" datasource="dsQBE:buscaqueda"></salmon:input></td>					
				</tr>
			</table>
		</salmon:searchformdisplaybox>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption=" " width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsCpa50" lookupreturnexp="cpa50.COD_COMPRA"
			lookupdescreturnexp="cpa50.NOM_COMPRA"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsCpa50">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="legajoCAP3" text="Cod. Comprador"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apellidoCAP4" text="Apellido y Nombre"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>						
						<salmon:td>
							<salmon:text name="legajoTXT3" text="nro_legajo Goes Here"
								font="DefaultFont" datasource="dsCpa50:cpa50.COD_COMPRA" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apellidoTXT4" text="apellido Goes Here"
								font="DefaultFont" datasource="dsCpa50:cpa50.NOM_COMPRA" />
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
