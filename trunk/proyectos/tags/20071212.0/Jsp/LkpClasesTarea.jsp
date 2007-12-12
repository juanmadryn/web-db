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
   <salmon:datasource name="dsClasesTarea" type="MODEL"
      dbprofile="proyectos" model="proyectos.models.ClasesTareaModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Clases de Tareas" qbebuilder="dsQBE"
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
         datasource="dsClasesTarea"
         lookupreturnexp="clases_tareas.clase_tarea_id"
         lookupdescreturnexp="clases_tareas.nombre"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsClasesTarea">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="claseTareaIdCAP10" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP11" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP12"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="clasetareaIdTXT6"
                        text="acividad_id Goes Here" font="DefaultFont"
                        datasource="dsClasesTarea:clases_tareas.clase_tarea_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT7"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsClasesTarea:clases_tareas.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT8"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsClasesTarea:clases_tareas.descripcion" />
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
