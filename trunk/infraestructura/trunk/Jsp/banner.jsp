<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<!--Begin Banner -->
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	bgcolor="white">
	<tr>
		<td align="left" width="40%"><salmon:a href="%HomePage"
			name="baseLinkAdminSalmon">
			<salmon:img name="imgMainLogo" src="%ImageDirectory/banner_logo.jpg"
				srclocalekey="bannerImageSource" />
		</salmon:a></td>
		<td align="right" width="60%" nowrap>
		<table width="-1%" border="0">
			<salmon:form name="bannerForm">
				<tr>
					<td colspan="3"><salmon:input name="menuBUT" type="submit"
						value="Ocultar Menú" classname="sbttn"></salmon:input> <salmon:a
						href="%AccountInfoPage" name="lnkBannerAccount">
						<salmon:text name="txtBannerAccount" textlocalekey="bannerAccount"
							text="Mi Cuenta" />
					</salmon:a> &nbsp;| <salmon:a href="%Options" name="lnkBannerOptions">
						<salmon:text name="txtBannerOptions" textlocalekey="bannerOptions"
							text="Opciones" />
					</salmon:a> &nbsp;| <salmon:a href="%Login" name="lnkBannerSignIn">
						<salmon:text name="txtBannerSignIn" textlocalekey="bannerSignIn"
							text="Ingresar" />
					</salmon:a> <salmon:a href="%HomePage" name="lnkBannerSignOut">
						<salmon:text name="txtBannerSignOut" textlocalekey="bannerSignOut"
							text="Salir" />
					</salmon:a></td>
				</tr>
				<tr>
					<td colspan="2"><salmon:a href=""
						name="lnkBannerSolicitudesPendientes">
						<salmon:text name="txtBannerSolicitudesPendientes" text="" />
					</salmon:a></td>
				</tr>
				<tr>
					<td><salmon:a href="" name="lnkBannerSolicitudesObservadas">
						<salmon:text name="txtBannerSolicitudesObservadas" text="" />
					</salmon:a></td>
				</tr>
				<tr>
					<td><salmon:a href="" name="lnkBannerOrdenesPendientes">
						<salmon:text name="txtBannerOrdenesPendientes" text="" />
					</salmon:a></td>
					<td><salmon:a href="" name="lnkBannerOrdenesObservadas">
						<salmon:text name="txtBannerOrdenesObservadas" text="" />
					</salmon:a></td>
				</tr>
				<tr><jsp:include page="welcomeMessage.jsp" flush="true"></jsp:include></tr>
			</salmon:form>
		</table>
		</td>
	</tr>
</table>
<!--End Banner -->