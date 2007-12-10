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
<salmon:datasource name="dsMenu" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.MenuModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	      <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Menús" qbebuilder="dsQBE"  > 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:searchformdisplaybox> </salmon:td>
	    <salmon:td valign="Top">
	      <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Menú" width="100%" datasource="dsMenu" listformdisplaybox="listformdisplaybox1"> 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="menuIdCAP14" text="Id" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="menuIdTE4" size="10" maxlength="10" datasource="dsMenu:menu.menu_id"></salmon:input></td>
	            <td><salmon:text name="nombreCAP15" text="Nombre" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="nombreTE5" size="20" maxlength="90" datasource="dsMenu:menu.nombre"></salmon:input></td>
	          </tr>
	          <tr>
	            <td><salmon:text name="descripcionCAP16" text="Desc." font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="descripcionTE6" size="40" maxlength="255" datasource="dsMenu:menu.descripcion"></salmon:input></td>
	            <td><salmon:text name="grupoCAP17" text="Grupo" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="grupoTE7" size="20" maxlength="90" datasource="dsMenu:menu.grupo"></salmon:input></td>
	          </tr>
	          <tr>
	            <td><salmon:text name="urlCAP18" text="Url" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="urlTE8" size="40" maxlength="255" datasource="dsMenu:menu.url"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:detailformdisplaybox> </salmon:td>
	  </salmon:tr>
	</salmon:table> </salmon:box> 
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" datasource="dsMenu" searchformdisplaybox="searchformdisplaybox1"  > 
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsMenu"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="menuIdCAP10" text="ID" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreCAP11" text="Nombre" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionCAP12" text="Descripción" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="grupoCAP13" text="Grupo" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="menuIdTXT6" text="menu.menu_id Goes Here" font="DefaultFont" datasource="dsMenu:menu.menu_id"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreTXT7" text="menu.nombre Goes Here" font="DefaultFont" datasource="dsMenu:menu.nombre"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionTXT8" text="menu.descripcion Goes Here" font="DefaultFont" datasource="dsMenu:menu.descripcion"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="grupoTXT9" text="menu.grupo Goes Here" font="DefaultFont" datasource="dsMenu:menu.grupo"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> </salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>