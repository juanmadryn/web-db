<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
   controller="infraestructura.controllers.AccionesCircuitoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
      <salmon:qbecriteria name="circuito" type="equals"
         columns="acciones_apps.circuito" />
   </salmon:datasource>
   <salmon:datasource name="dsAccionesAplicacion" type="MODEL"
      model="infraestructura.models.AccionAplicacionModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Acciones de un Circuito Administrativo"
                  qbebuilder="dsQBE">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="circuitoCAP5"
                           text="Circuito" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="select"
                           name="circuitoDD3" size="25"
                           datasource="dsQBE:circuito">
                           <salmon:option display="abc"
                              displaycolumn="nombre" key="123"
                              keycolumn="circuito" nulloption="False"
                              table="circuitos_estados"></salmon:option>
                        </salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="buscarCAP6"
                           text="Buscar" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="buscarTE3" size="30" maxlength="90"
                           datasource="dsQBE:buscar"></salmon:input></td>
                     </tr>
                  </table>
               </salmon:searchformdisplaybox>
            </salmon:td>
            <salmon:td valign="Top">
               <salmon:detailformdisplaybox name="detailformdisplaybox1"
                  caption="Editar Acción Circuito" width="100%"
                  datasource="dsAccionesAplicacion"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="nombreINPCAP"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreINP" size="30" maxlength="90"
                           datasource="dsAccionesAplicacion:acciones_apps.nombre"></salmon:input>
                        <salmon:text name="accionINPCAP" text="Id"
                           font="ColumnCaptionFont" /> <salmon:text
                           name="accionINP" text="accion goes here"
                           datasource="dsAccionesAplicacion:acciones_apps.accion" /></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionINPCAP"
                           text="Descripción" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionINP" size="40"
                           maxlength="255"
                           datasource="dsAccionesAplicacion:acciones_apps.descripcion"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="observacionesINPCAP"
                           text="Observaciones" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesINP" size="40"
                           maxlength="255"
                           datasource="dsAccionesAplicacion:acciones_apps.observaciones"></salmon:input></td>
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
         datasource="dsAccionesAplicacion"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsAccionesAplicacion">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="acciones_aplicacion.accionCAP"
                        text="Acción" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="acciones_aplicacion.nombreCAP"
                        text="Nombre" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text
                        name="acciones_aplicacion.descripcionCAP"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="accAppAccionTXT"
                        text="NO FIELDS FOUND Goes Here"
                        font="DefaultFont"
                        datasource="dsAccionesAplicacion:acciones_apps.accion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="accAppNombreTXT"
                        text="NO FIELDS FOUND Goes Here"
                        font="DefaultFont"
                        datasource="dsAccionesAplicacion:acciones_apps.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="AccAppdescripcionTXT"
                        text="NO FIELDS FOUND Goes Here"
                        font="DefaultFont"
                        datasource="dsAccionesAplicacion:acciones_apps.descripcion" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
