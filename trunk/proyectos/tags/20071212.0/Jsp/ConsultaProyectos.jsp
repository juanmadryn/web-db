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
<salmon:datasource name="dsProyectos" type="MODEL" dbprofile="proyectos" model="proyectos.models.ProyectoModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	      <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Proyectos" qbebuilder="dsQBE"  >
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="buscarCAP1" text="Buscar" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="buscarTE3" size="60" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:searchformdisplaybox> </salmon:td>
	  </salmon:tr>
	</salmon:table> </salmon:box>
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption=" " width="100%" datasource="dsProyectos" searchformdisplaybox="searchformdisplaybox1" >
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsProyectos"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="proyectoCAP2" text="Proyecto" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreCAP3" text="Nombre" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionCAP4" text="Descripción" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="clienteCAP5" text="Cliente" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:a href="none" name="lnkproyecto1" onclick="document.forms['bannerForm'].submit();" datasource="dsProyectos:'%CrearProyecto?p_proyecto_id='+proyectos.proyecto_id">
              <salmon:text name="proyectoTXT1" text="proyecto Goes Here" font="DefaultFont" datasource="dsProyectos:proyectos.proyecto"/> </salmon:a>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreTXT2" text="nombre Goes Here" font="DefaultFont" datasource="dsProyectos:proyectos.nombre"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionTXT3" text="descripcion Goes Here" font="DefaultFont" datasource="dsProyectos:proyectos.descripcion"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="codigoClienteTXT4" text="cliente Goes Here" font="DefaultFont" datasource="dsProyectos:entidad_externa.codigo"/>
              <salmon:text name="nombreClienteTXT5" text="cliente Goes Here" font="DefaultFont" datasource="dsProyectos:entidad_externa.nombre"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> </salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>