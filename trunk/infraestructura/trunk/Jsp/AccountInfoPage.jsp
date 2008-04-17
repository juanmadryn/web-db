<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="infraestructura.controllers.AccountInfoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsWebsiteUsers" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.WebsiteUserModel" autoretrieve="NEVER">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:a href="javascript:window.history.back()" name="lnkBack">
               <salmon:text name="back" text="Regresar" font="ColumnCaptionFont" />
            </salmon:a>
		<salmon:table name="table1" width="60%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Mi cuenta" width="100%" datasource="dsWebsiteUsers"
						listformdisplaybox="listformdisplaybox1" savebuttonvisible="false"
						cancelbuttonvisible="false" addbuttonvisible="false">
						<table width="100%" border="0" datasource="dsWebsiteUsers">
							<tr>
								<td><salmon:text name="idTXT1" text="Id"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="idTXT2" text="Id"
									font="ColumnCaptionFont"
									datasource="dsWebsiteUsers:website_user.user_id" /></td>
							</tr>
							<tr>
								<td><salmon:text name="usuarioTXT1" text="Usuario"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="usuarioIMP1" size="40"
									maxlength="255"
									datasource="dsWebsiteUsers:website_user.login_name"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCompletoTXT1"
									text="Nombre completo" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreCompletoIMP1"
									size="40" maxlength="255"
									datasource="dsWebsiteUsers:website_user.nombre_completo"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="emailTXT1" text="E-mail"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="emailIMP1" size="40"
									maxlength="255" datasource="dsWebsiteUsers:website_user.email"></salmon:input></td>
							</tr>
							<tr>
							</tr>
							<tr height="50" valign="bottom">
								<td colspan="2" align="Left"><salmon:text
									name="textSeparador" text="Cambio de contraseña"
									font="SectionHeadingFont" textlocalekey="HdngCCInfo" /></td>
							<tr>
							<tr>
								<td><salmon:text name="passwordAnteriorTXT1"
									text="Ingrese contraseña anterior" font="DefaultFont"
									textlocalekey="lblPassword" /></td>
								<td><salmon:input type="password"
									name="passwordAnteriorIMP1" size="20" maxlength="60"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="passwordNuevaTXT1"
									text="Ingrese contraseña nueva" font="DefaultFont"
									textlocalekey="lblPassword" /></td>
								<td><salmon:input type="password" name="passwordNuevaIMP1"
									size="20" maxlength="60"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="passwordReNuevaTXT1"
									text="Re-ingrese contraseña nueva" font="DefaultFont"
									textlocalekey="lblPassword" /></td>
								<td><salmon:input type="password"
									name="passwordReNuevaIMP1" size="20" maxlength="60"></salmon:input></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />