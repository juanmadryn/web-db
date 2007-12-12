<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
</salmon:datasource>
<salmon:datasource  name="dsAccesoMenu" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.AccesoMenuModel" autoretrieve="Never" dbprofile="infraestructura">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
      <salmon:tr>
        <salmon:td valign="Top">
		  <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Accesos Permitidos a Menús" qbebuilder="dsQBE"  > 
		    <table width="100%" >
		      <tr>
		        <td><salmon:text name="buscarCAP3" text="Buscar" font="ColumnCaptionFont" /></td>
		        <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="60" datasource="dsQBE:buscar"></salmon:input></td>
		      </tr>
		    </table>
		  </salmon:searchformdisplaybox> 
        </salmon:td>
        <salmon:td valign="Top">
          <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Acceso a Menú" width="100%" datasource="dsAccesoMenu" listformdisplaybox="listformdisplaybox1"  > 
		    <table width="100%" >
		      <tr>
		        <td><salmon:text name="nombreCAP16" text="Menu" font="ColumnCaptionFont" /></td>
		        <td width="300"><salmon:lookup browseimage="%ImageDirectory/Browse.gif" lookupurl="%LkpMenu" name="nombreTE4" size="6" maxlength="10" displayformat="##########0" descriptiondatasource="dsAccesoMenu:menu.nombre" datasource="dsAccesoMenu:acceso_menu.menu_id" popupheight="450" popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup></td>
		      </tr>
		      <tr>
		        <td><salmon:text name="nombreCAP17" text="Rol" font="ColumnCaptionFont" /></td>
		        <td><salmon:input type="select" name="nombreTE5" size="30" datasource="dsAccesoMenu:acceso_menu.rol_id">
		        		<salmon:option display="abc" key="123" table="infraestructura.roles" keycolumn="rol_id" displaycolumn="nombre" nulloption="true"></salmon:option>
		        	</salmon:input>
		        </td>
		        <td><salmon:text name="loginNameCAP18" text="Usuario" font="ColumnCaptionFont" /></td>
		        <td width="300"><salmon:lookup browseimage="%ImageDirectory/Browse.gif" lookupurl="%LkpWebSiteUsers" name="userIdTE4" size="6" maxlength="10" displayformat="##########0" descriptiondatasource="dsAccesoMenu:website_user.login_name" datasource="dsAccesoMenu:acceso_menu.user_id" popupheight="450" popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup></td>
		      </tr>
		    </table>
          </salmon:detailformdisplaybox> </salmon:td>
      </salmon:tr>
    </salmon:table> 
</salmon:box>
<salmon:box name="box2" width="100%">
	<salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" addbuttonvisible="False" savebuttonvisible="False" datasource="dsAccesoMenu" searchformdisplaybox="searchformdisplaybox1"  > 
    <salmon:datatable name="datatable1" width="100%" datasource="dsAccesoMenu" rowsperpage="5"><salmon:datatableheader>
        <salmon:tr>
          <salmon:td>
            <salmon:text name="nombreCAP10" text="Menu" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td>
            <salmon:text name="descripcionCAP11" text="Descripción" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td>
            <salmon:text name="grupoCAP12" text="Grupo" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td>
            <salmon:text name="nombreCAP13" text="Rol" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td>
            <salmon:text name="loginNameCAP14" text="usuario" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td>
            <salmon:text name="apeynomCAP15" text="Nombre Completo" font="TableHeadingFont" />
          </salmon:td>
        </salmon:tr>
      </salmon:datatableheader><salmon:datatablerows>
        <salmon:tr>
          <salmon:td>
            <salmon:text name="nombreTXT4" text="menu.nombre Goes Here" font="DefaultFont" datasource="dsAccesoMenu:menu.nombre"/>
          </salmon:td>
          <salmon:td>
            <salmon:text name="descripcionTXT5" text="menu.descripcion Goes Here" font="DefaultFont" datasource="dsAccesoMenu:menu.descripcion"/>
          </salmon:td>
          <salmon:td>
            <salmon:text name="grupoTXT6" text="menu.grupo Goes Here" font="DefaultFont" datasource="dsAccesoMenu:menu.grupo"/>
          </salmon:td>
          <salmon:td>
            <salmon:text name="nombreTXT7" text="roles.nombre Goes Here" font="DefaultFont" datasource="dsAccesoMenu:roles.nombre"/>
          </salmon:td>
          <salmon:td>
            <salmon:text name="loginNameTXT8" text="website_user.login_name Goes Here" font="DefaultFont" datasource="dsAccesoMenu:website_user.login_name"/>
          </salmon:td>
          <salmon:td>
            <salmon:text name="apeynomTXT9" text="website_user.nombre_completo Goes Here" font="DefaultFont" datasource="dsAccesoMenu:website_user.nombre_completo"/>
          </salmon:td>
        </salmon:tr>
      </salmon:datatablerows></salmon:datatable>
	</salmon:listformdisplaybox> 
</salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>