<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page />
<salmon:body />
<salmon:form name="pageForm">
	<%@include file="message.jsp"%>
	</td>
	<!--Page Content Begin-->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsWebSiteUser" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.WebsiteUserModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:box name="box1" width="100%">
		<salmon:searchformdisplaybox name="searchformdisplaybox1"
			caption="Usuarios de la Aplicación" qbebuilder="dsQBE"
			listformdisplaybox="listformdisplaybox1" addbuttonvisible="False">
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
			datasource="dsWebSiteUser" lookupreturnexp="website_user.user_id"
			lookupdescreturnexp="website_user.nombre_completo"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsWebSiteUser">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="userIdCAP10" text="ID" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apeynomCAP11" text="Nombre Completo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="loginNameCAP12" text="Nombre Usuario"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="userIdTXT6"
								text="website_user.user_id Goes Here" font="DefaultFont"
								datasource="dsWebSiteUser:website_user.user_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apeynomTXT7"
								text="website_user.nombre_completo Goes Here" font="DefaultFont"
								datasource="dsWebSiteUser:website_user.nombre_completo" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="loginNameTXT8"
								text="website_user.login_name Goes Here" font="DefaultFont"
								datasource="dsWebSiteUser:website_user.login_name" />
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