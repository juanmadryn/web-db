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
<salmon:datasource name="dsClaseTarea" type="MODEL" dbprofile="proyectos" model="proyectos.models.ClasesTareaModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	      <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Clases de tareas" qbebuilder="dsQBE"  > 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:searchformdisplaybox> </salmon:td>
	    <salmon:td valign="Top">
	      <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Clase de Tarea" width="100%" datasource="dsClaseTarea" listformdisplaybox="listformdisplaybox1"> 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="claseIdCAP14" text="ID" font="ColumnCaptionFont" />
	            	<salmon:text name="claseIdTE4" text="Id goes here" font="DefaultFont" datasource="dsClaseTarea:clases_tareas.clase_tarea_id" />
	            <td><salmon:input type="text" name="nombreTE5" size="40" maxlength="90" datasource="dsClaseTarea:clases_tareas.nombre"></salmon:input>
	            </td>
	          </tr>
	          <tr>
	            <td><salmon:text name="descripcionCAP16" text="Descripción" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="descripcionTE6" size="40" maxlength="255" datasource="dsClaseTarea:clases_tareas.descripcion"></salmon:input></td>
	          </tr>
	          <tr>
	            <td><salmon:text name="observacionesCAP18" text="Observaciones" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="observacionesTE8" size="40" maxlength="255" datasource="dsClaseTarea:clases_tareas.observaciones"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:detailformdisplaybox> </salmon:td>
	  </salmon:tr>
	</salmon:table> </salmon:box> 
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" datasource="dsClaseTarea" searchformdisplaybox="searchformdisplaybox1" > 
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsClaseTarea"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="claseIdCAP10" text="ID" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreCAP11" text="Nombre" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionCAP12" text="Descripción" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="claseIdTXT6" text="roles.rol_id Goes Here" font="DefaultFont" datasource="dsClaseTarea:clases_tareas.clase_tarea_id"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreTXT7" text="roles.nombre Goes Here" font="DefaultFont" datasource="dsClaseTarea:clases_tareas.nombre"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionTXT8" text="roles.descripcion Goes Here" font="DefaultFont" datasource="dsClaseTarea:clases_tareas.descripcion"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> </salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>