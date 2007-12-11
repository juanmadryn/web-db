<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
   controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsActividad" type="MODEL"
      dbprofile="proyectos"
      model="proyectos.models.ActividadesModel" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Actividades para Proyectos"
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
                  caption="Editar Actividad de Proyecto" width="100%"
                  datasource="dsActividad"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="nombreCAP23"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreTE9" size="30" maxlength="90"
                           datasource="dsActividad:actividades.nombre"></salmon:input></td>
                        <td><salmon:text name="idCAP33" text="ID"
                           font="ColumnCaptionFont" /></td>
                        <td><salmon:text name="idTXT34"
                           text="id Goes Here" font="DefaultFont"
                           datasource="dsActividad:actividades.actividad_id" />
                        </td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionCAP24"
                           text="Descripción" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionTE10" size="30"
                           maxlength="255"
                           datasource="dsActividad:actividades.descripcion"></salmon:input></td>
                        <td><salmon:text name="observacionesCAP25"
                           text="Obs." font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesTE11" size="40"
                           maxlength="255"
                           datasource="dsActividad:actividades.observaciones"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="padreCAP45"
                           text="Actividad Padre" font="ColumnCaptionFont" /></td>
                        <td width="250"><salmon:lookup
                           browseimage="%ImageDirectory/Browse.gif"
                           lookupurl="%LkpActividades" name="actividadPadreTE4"
                           size="6" maxlength="10"
                           displayformat="##########0"
                           descriptiondatasource="dsActividad.actividad_padre.nombre"
                           datasource="dsActividad:actividades.actividad_id_padre"
                           popupheight="450" popupwidth="500"
                           usepopup="true" showdescription="true"></salmon:lookup></td>
                        <td><salmon:text name="esHojaCAP46"
                           text="Es Hoja?" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="esHojaCB1" type="checkbox"
                           checkedtruevalue="V" checkedfalsevalue="F" checked="false"
                           datasource="dsActividad:actividades.es_hoja"></salmon:input></td>
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
         caption="Actividades para los Proyectos" width="100%"
         datasource="dsActividad" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsActividad" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreCAP20" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP19"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="padreCAP21"
                        text="Actividad Padre" font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreTXT18"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsActividad:actividades.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT16"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsActividad:actividades.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="padreTXT21"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsActividad:actividad_padre.nombre" />
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
