<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
	controller="infraestructura.controllers.WebSiteUserController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	</td>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="buscar" type="complex" columns="*" />
	</salmon:datasource>
	<salmon:datasource name="dsWebSiteUser" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.WebsiteUserModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsUsuarioRoles" type="MODEL"
		dbprofile="infraestructura"
		model="infraestructura.models.UsuarioRolesModel" autoretrieve="Never">
		<salmon:selection>
			<salmon:selectioncriteria fieldname="usuario_roles.anulado"
				operator="NOT_EQUAL" value="F" />
		</salmon:selection>
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox name="searchformdisplaybox1"
						caption="Usuarios de la Aplicación" qbebuilder="dsQBE">
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
						caption="Editar Usuario" width="100%" datasource="dsWebSiteUser"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="loginNameCAP17"
									text="Nombre Usuario" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="loginNameTE7" size="20"
									maxlength="60"
									datasource="dsWebSiteUser:website_user.login_name"></salmon:input></td>
								<td><salmon:text name="loginPasswordCAP18" text="Cláve"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="loginPasswordTE8"
									size="20" maxlength="60"
									datasource="dsWebSiteUser:website_user.login_password"></salmon:input></td>
								<td align="Right"><salmon:text name="userIdTE9"
									text="user_id goes here"
									datasource="dsWebSiteUser:website_user.user_id" /></td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCompletoCAP17"
									text="Nombre Completo" font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreCompletoTE7"
									size="20" maxlength="90"
									datasource="dsWebSiteUser:website_user.nombre_completo"></salmon:input></td>
								<td><salmon:text name="emailCAP17" text="eMail"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="emailTE7" size="20"
									maxlength="90" datasource="dsWebSiteUser:website_user.email"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="nivelVisibilidadCAP10"
									text="Visibilidad" font="ColumnCaptionFont" /></td>
								</td>
								<td><salmon:input name="nivelVisibilidadTE10" type="select"
									datasource="dsWebSiteUser:website_user.nivel_visibilidad">
									<salmon:option display="Todo" key="Todo"></salmon:option>
									<salmon:option display="Gerencia" key="Gerencia"></salmon:option>
									<salmon:option display="Supervición" key="Supervición"></salmon:option>
									<salmon:option display="Propio" key="Propio"></salmon:option>
								</salmon:input></td>
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
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsWebSiteUser"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%"
				datasource="dsWebSiteUser">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="userIdCAP10" text="ID" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="apeynomCAP11" text="Apellido y Nombre"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="loginNameCAP12" text="Nombre Usuario"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="emailCAP12" text="eMail"
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
						<salmon:td>
							<salmon:text name="emailTXT8" text="website_user.email Goes Here"
								font="DefaultFont" datasource="dsWebSiteUser:website_user.email" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box3">
		<salmon:listformdisplaybox caption="Roles de Usuario"
			mode="Display_single_page" name="listformdisplaybox2" width="100%"
			addbuttonvisible="False" savebuttonvisible="False"
			datasource="dsUsuarioRoles">
			<salmon:datatable name="datatable2" width="100%"
				datasource="dsUsuarioRoles">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="rolIdCAP10" text="Rol" font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreRolCAP11" text="Nombre"
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
							<salmon:text name="rolIdTXT6"
								text="usuario_roles.rol_id Goes Here" font="DefaultFont"
								datasource="dsUsuarioRoles:usuario_roles.rol_id" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="nombreRolTXT7" text="rol.nombre Goes Here"
								font="DefaultFont" datasource="dsUsuarioRoles:roles.nombre" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="desdeTXT8"
								text="usuario_roles.desde Goes Here" font="DefaultFont"
								displayformat="dd/MM/yyyy"
								datasource="dsUsuarioRoles:usuario_roles.desde" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="hastaTXT9"
								text="usuarios_roles.hasta Goes Here" font="DefaultFont"
								displayformat="dd/MM/yyyy"
								datasource="dsUsuarioRoles:usuario_roles.hasta" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<salmon:box name="box4">
		<salmon:detailformdisplaybox
			caption="Editar Asignación de Rol a Usuario"
			name="detailformdisplaybox2" width="100%" datasource="dsUsuarioRoles"
			listformdisplaybox="listformdisplaybox2">
			<table width="100%">
				<tr>
					<td><salmon:text name="rolCAP14" text="Rol"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="select" name="rolTE4" size="20"
						datasource="dsUsuarioRoles:usuario_roles.rol_id">
						<salmon:option display="abc" key="123" displaycolumn="nombre"
							keycolumn="rol_id" nulloption="False"
							table="infraestructura.roles"></salmon:option>
					</salmon:input></td>
					<td><salmon:text name="desdeCAP14" text="Desde"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="desdeTE4" size="10"
						maxlength="10" datasource="dsUsuarioRoles:usuario_roles.desde"
						displayformat="dd/MM/yyyy"></salmon:input></td>
					<td><salmon:text name="hastaCAP15" text="Hasta"
						font="ColumnCaptionFont" /></td>
					<td><salmon:input type="text" name="hastaTE5" size="10"
						maxlength="10" datasource="dsUsuarioRoles:usuario_roles.hasta"
						displayformat="dd/MM/yyyy"></salmon:input></td>
				</tr>
			</table>
		</salmon:detailformdisplaybox>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />