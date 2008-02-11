<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsSecTra" type="MODEL"
      dbprofile="partesmo"
      model="partesMO.models.SectorTrabajoModel"
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
                  caption="Sectores de trabajo del personal"
                  qbebuilder="dsQBE">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="buscarCAP5"
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
                  caption="Editar Sector de Trabajo" width="100%"
                  datasource="dsSecTra"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="nombreCAP23"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreTE9" size="30" maxlength="90"
                           datasource="dsSecTra:sector_trabajo.nombre"></salmon:input></td>
                        <td><salmon:text name="idCAP1"
                           text="ID" font="ColumnCaptionFont" /></td>
                        <td><salmon:text name="idTE9" font="DefaultFont" text="id goes here"
                           datasource="dsSecTra:sector_trabajo.sector_id" /></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionCAP24"
                           text="Descripción" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionTE10" size="30"
                           maxlength="255"
                           datasource="dsSecTra:sector_trabajo.descripcion"></salmon:input></td>
                        <td><salmon:text name="observacionesCAP25"
                           text="Obs." font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesTE11" size="40"
                           maxlength="255"
                           datasource="dsSecTra:sector_trabajo.observaciones"></salmon:input></td>
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
         caption="Altas, Bajas, Modificaciones y Consultas" width="100%"
         datasource="dsSecTra" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsSecTra" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="idCAP19" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP20" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP19"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="idTXT18"
                        text="sector_id Goes Here" font="DefaultFont"
                        datasource="dsSecTra:sector_trabajo.sector_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT18"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsSecTra:sector_trabajo.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT16"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsSecTra:sector_trabajo.descripcion" />
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
