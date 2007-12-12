<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definici�n de DataSource aqu� -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsVal" type="MODEL"
      dbprofile="partesmo"
      model="partesMO.models.ValidacionesPartesMoModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar c�digo de la p�gina aqu� -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Validaciones de control para partes de Mano de Obra"
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
                  caption="Editar Validaci�n" width="100%"
                  datasource="dsVal"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="nombreCAP23"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreTE9" size="30" maxlength="90"
                           datasource="dsVal:validaciones_partes_mo.nombre"></salmon:input></td>
                        <td><salmon:text name="idCAP1"
                           text="ID" font="ColumnCaptionFont" /></td>
                        <td><salmon:text name="idTE9" font="DefaultFont" text="id goes here"
                           datasource="dsVal:validaciones_partes_mo.validacion_id" /></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionCAP24"
                           text="Descripci�n" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionTE10" size="30"
                           maxlength="255"
                           datasource="dsVal:validaciones_partes_mo.descripcion"></salmon:input></td>
                        <td><salmon:text name="observacionesCAP25"
                           text="Obs." font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesTE11" size="40"
                           maxlength="255"
                           datasource="dsVal:validaciones_partes_mo.observaciones"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="tipoCAP52"
                           text="Tipo Validaci�n" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="tipoTE53"
                           type="select"
                           datasource="dsVal:validaciones_partes_mo.tipo">
                           <salmon:option display="Mensaje" key="Mensaje"></salmon:option>
                           <salmon:option display="Advertencia" key="Advertencia"></salmon:option>
                           <salmon:option display="Error" key="Error"></salmon:option>
                           <salmon:option display="Error Grave" key="Error Grave"></salmon:option>
                        </salmon:input></td>
                        <td><salmon:text name="validadorCAP51"
                           text="Validaci�n" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="validadorTE12"
                           type="text" size="40" maxlength="255"
                           datasource="dsVal:validaciones_partes_mo.validador"></salmon:input></td>
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
         caption="Validaciones" width="100%"
         datasource="dsVal" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsVal" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreCAP20" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP19"
                        text="Descripci�n" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="tipoCAP52" text="Tipo"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreTXT18"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsVal:validaciones_partes_mo.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT16"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsVal:validaciones_partes_mo.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="tipoTXT53" text="tipo Goes Here"
                        font="DefaultFont"
                        datasource="dsVal:validaciones_partes_mo.tipo" />
                  </salmon:td>

               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de c�digo agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
