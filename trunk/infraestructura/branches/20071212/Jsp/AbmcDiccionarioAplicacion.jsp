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
<salmon:datasource name="dsDAP" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.DiccionarioAplicacionModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	      <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Diccionario de la Aplicación"  qbebuilder="dsQBE"  > 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:searchformdisplaybox> </salmon:td>
	    <salmon:td valign="Top">
	      <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Diccionario" width="100%" datasource="dsDAP" listformdisplaybox="listformdisplaybox1"> 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="tipoIdCAP14" text="Tipo" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="select" name="tipoTE4" size="20"  datasource="dsDAP:diccionario_aplicacion.tipo_objeto">
	            	<salmon:option display="Tabla" key="TABLA"></salmon:option>
	            	<salmon:option display="Vista" key="VISTA"></salmon:option>
	            	<salmon:option display="Store Procedure" key="STOREPROCEDURE"></salmon:option>
	            	<salmon:option display="Trigger de Tabla" key="TRIGGERTABLA"></salmon:option>
	            	<salmon:option display="Java Server Page" key="JSP"></salmon:option>
	            	<salmon:option display="Java Server Faces" key="JSF"></salmon:option>
	            	<salmon:option display="Java Controller" key="JAVACONTROLLER"></salmon:option>
	            	<salmon:option display="Java Model" key="JAVAMODEL"></salmon:option>
	            	<salmon:option display="Java Struts" key="JAVASTRUTS"></salmon:option>
	            	<salmon:option display="Java Class" key="JAVACLASS"></salmon:option>
	            </salmon:input></td>
	            <tr>
		            <td><salmon:text name="nombreCAP15" text="Nombre" font="ColumnCaptionFont" /></td>
		            <td><salmon:input type="text" name="nombreTE5" size="30" maxlength="45" datasource="dsDAP:diccionario_aplicacion.nombre_objeto"></salmon:input></td>
	            </tr>
	          </tr>
	        </table>
	      </salmon:detailformdisplaybox> </salmon:td>
	  </salmon:tr>
	</salmon:table> </salmon:box> 
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" datasource="dsDAP" searchformdisplaybox="searchformdisplaybox1"  > 
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsDAP"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="tipoCAP10" text="Tipo" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreCAP11" text="Nombre" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="tipoTXT6" text="tipo Goes Here" font="DefaultFont" datasource="dsDAP:diccionario_aplicacion.tipo_objeto"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreTXT7" text="nombre Goes Here" font="DefaultFont" datasource="dsDAP:diccionario_aplicacion.nombre_objeto"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> </salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>