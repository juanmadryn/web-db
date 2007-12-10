<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.AbmcEsquemaConfiguracionController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsEsquema" type="MODEL"
      dbprofile="infraestructura"
      model="infraestructura.models.EsquemaConfiguracionModel"
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
                  caption="Esquema de Configuración de la Aplicación"
                  qbebuilder="dsQBE">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="buscarCAP1"
                           text="Buscar" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="buscarTE1" size="30" maxlength="90"
                           datasource="dsQBE:buscar"></salmon:input></td>
                     </tr>
                  </table>
               </salmon:searchformdisplaybox>
            </salmon:td>
            <salmon:td valign="Top">
               <salmon:detailformdisplaybox name="detailformdisplaybox1"
                  caption="Editar Esquema de Configuración"
                  width="100%" datasource="dsEsquema"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="dapSelectorCAP14"
                           text="Tabla"
                           font="ColumnCaptionFont" /></td>
                        <td>
                           <salmon:input type="text"
                           name="nombreObjetoTE11" size="30" maxlength="45"
                           datasource="dsEsquema:esquema_configuracion.nombre_objeto"></salmon:input>
                        </td>
                        <td><salmon:text name="nombreCAP2"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreTE2" size="30" maxlength="90"
                           datasource="dsEsquema:esquema_configuracion.nombre"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionCAP4"
                           text="Descripción" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionTE5" size="30" maxlength="255"
                           datasource="dsEsquema:esquema_configuracion.descripcion"></salmon:input></td>
                        <td><salmon:text name="observacionesCAP5"
                           text="Obs." font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesTE6" size="40" maxlength="255"
                           datasource="dsEsquema:esquema_configuracion.observaciones"></salmon:input></td>
                  </table>
               </salmon:detailformdisplaybox>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page"
         caption="Esquemas de Configuración" width="100%"
         datasource="dsEsquema" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsEsquema" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="objetoCAP20" text="Tabla"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP15" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP16"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreObjetoTXT8"
                        text="nombre objeto Goes Here" font="DefaultFont"
                        datasource="dsEsquema:esquema_configuracion.nombre_objeto" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT1"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsEsquema:esquema_configuracion.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT2"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsEsquema:esquema_configuracion.descripcion" />
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
