<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page controller="partesMO.controllers.LkpTareasProyectoController" /> 
<salmon:body />
<salmon:form name="pageForm">
   <%@include file="message.jsp"%>
   </td>
   <!--Page Content Begin-->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsTareasProyecto" type="MODEL"
      dbprofile="proyectos" model="proyectos.models.TareasProyectoModel"
      autoretrieve="Never">
         <salmon:selectioncriteria fieldname="tareas_proyecto.estado" operator="EQUAL" value="'0001.0001'" connector="OR"/>
         <salmon:selectioncriteria fieldname="tareas_proyecto.estado" operator="EQUAL" value="'0001.0002'" connector="OR"/>
         <salmon:selectioncriteria fieldname="tareas_proyecto.estado" operator="EQUAL" value="'0001.0003'" connector="OR"/>
   </salmon:datasource>
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Tareas Proyecto" qbebuilder="dsQBE"
         listformdisplaybox="listformdisplaybox1"
         addbuttonvisible="False">
         <table width="100%">
            <tr>
               <td><salmon:text name="buscarCAP5" text="Buscar"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarTE3"
                  size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
            </tr>
         </table>
      </salmon:searchformdisplaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page" caption=" " width="100%"
         addbuttonvisible="False" savebuttonvisible="False"
         datasource="dsTareasProyecto"
         lookupreturnexp="tareas_proyecto.nombre"
         lookupdescreturnexp="tareas_proyecto.descripcion"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsTareasProyecto">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="tareasProyectoCAP11" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="TareasNombreCAP12" text="Descripción"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="tareasProyectoTXT7"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsTareasProyecto:tareas_proyecto.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="tareasNombreTXT8"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsTareasProyecto:tareas_proyecto.descripcion" />
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
