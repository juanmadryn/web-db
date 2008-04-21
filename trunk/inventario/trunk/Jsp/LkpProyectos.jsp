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
      <salmon:qbecriteria name="buscar" type="STARTS_WITH_IGNORE_CASE_EXTENDED" columns="proyectos.numero,proyectos.nombre" />
   </salmon:datasource>
   <salmon:datasource name="dsProyectos" type="MODEL"
      dbprofile="proyectos" model="proyectos.models.ProyectoModel"
      autoretrieve="Never">
         <salmon:selectioncriteria fieldname="proyectos.estado" operator="EQUAL" value="'0001.0001'" connector="OR"/>
         <salmon:selectioncriteria fieldname="proyectos.estado" operator="EQUAL" value="'0001.0002'" connector="OR"/>
         <salmon:selectioncriteria fieldname="proyectos.estado" operator="EQUAL" value="'0001.0003'" connector="OR"/>
   </salmon:datasource>
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Proyectos" qbebuilder="dsQBE"
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
         datasource="dsProyectos"
         lookupreturnexp="proyectos.proyecto"
         lookupdescreturnexp="proyectos.nombre"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsProyectos">
            <salmon:datatableheader>
               <salmon:tr>                
                  <salmon:td>
                     <salmon:text name="proyectoCAP11" text="Número"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP12" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>                 
                  <salmon:td>
                     <salmon:text name="proyectoTXT7"
                        text="proyecto Goes Here" font="DefaultFont"
                        datasource="dsProyectos:proyectos.proyecto" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT8"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsProyectos:proyectos.nombre" />
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
