<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.AbmcEntidadExternaController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsEntidad" type="MODEL"
      model="infraestructura.models.EntidadExternaModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsRoles" type="MODEL"
      model="infraestructura.models.RolesEntidadModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsAtributos" type="MODEL"
      model="infraestructura.models.AtributosEntidadModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Entidades Externas (Clientes, Proveedores, etc.)"
         addbuttonvisible="False" qbebuilder="dsQBE"
         addbuttonvisible="true">
         <table width="100%">
            <tr>
               <td><salmon:text name="buscarCAP1" text="Buscar"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarTE1"
                  size="60" maxlength="255" datasource="dsQBE:buscar"></salmon:input></td>
            </tr>
         </table>
      </salmon:searchformdisplaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page"
         caption="Altas/Bajas/Modificaciones/Consultas" width="100%"
         datasource="dsEntidad"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsEntidad" rowsperpage="3">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="selCAP2" text="-X-"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="idCAP3" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="codigoCAP4" text="Código"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP5" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP6"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="observacionesCAP7"
                        text="Observaciones" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="activoCAP8" text="Activo"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:input type="checkbox" name="seleccion1" checkedtruevalue="1"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="idTXT1" text="id Goes Here"
                        font="DefaultFont"
                        datasource="dsEntidad:entidad_externa.entidad_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="codigoTE2" size="6"
                        maxlength="15"
                        datasource="dsEntidad:entidad_externa.codigo"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="nombreTE3"
                        size="30" maxlength="90"
                        datasource="dsEntidad:entidad_externa.nombre"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="descripcionTE4"
                        size="30" maxlength="255"
                        datasource="dsEntidad:entidad_externa.descripcion"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="observacionesTE5"
                        size="40" maxlength="255"
                        datasource="dsEntidad:entidad_externa.observaciones"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="activoTXT2"
                        text="activo Goes Here" font="DefaultFont"
                        datasource="dsEntidad:entidad_externa.activo" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <salmon:box name="box3" width="100%">
      <salmon:table name="table1" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:listformdisplaybox name="listformdisplaybox2"
                  mode="Display_single_page"
                  caption="Roles de la Entidad" width="100%"
                  datasource="dsRoles" addbuttonvisible="True"
                  savebuttonvisible="True">
                  <salmon:datatable name="datatable2" width="100%"
                     datasource="dsRoles" rowsperpage="5">
                     <salmon:datatableheader>
                        <salmon:tr>
                           <salmon:td>
                              <salmon:text name="selCAP9" text="-X-"
                                 font="TableHeadingFont" />
                           </salmon:td>
                           <salmon:td>
                              <salmon:text name="rolCAP10" text="Rol"
                                 font="TableHeadingFont" />
                           </salmon:td>
                           <salmon:td>
                              <salmon:text name="vigenciaCAP11"
                                 text="Vigencia" font="TableHeadingFont" />
                           </salmon:td>
                           <salmon:td>
                              <salmon:text name="activoCAP12"
                                 text="Activo" font="TableHeadingFont" />
                           </salmon:td>
                        </salmon:tr>
                     </salmon:datatableheader>
                     <salmon:datatablerows>
                        <salmon:tr>
                           <salmon:td>
                              <salmon:input type="checkbox" name="seleccion2" checkedtruevalue="1"></salmon:input>
                           </salmon:td>
                           <salmon:td width="250">
                              <salmon:lookup
                                 browseimage="%ImageDirectory/Browse.gif"
                                 lookupurl="%LkpRolesEntidad"
                                 name="rolTE6" size="4" maxlength="4"
                                 descriptiondatasource="dsRoles:rol_entidad.nombre"
                                 datasource="dsRoles:roles_entidad.rol"
                                 popupheight="450" popupwidth="500"
                                 usepopup="true" showdescription="true"></salmon:lookup>
                           </salmon:td>
                           <salmon:td>
                              <salmon:input name="desdeTE7" type="text"
                                 size="10" maxlength="10"
                                 displayformat="dd/MM/yyyy"
                                 datasource="dsRoles:roles_entidad.desde"></salmon:input>
                              <salmon:input name="hastaTE8" type="text"
                                 size="10" maxlength="10"
                                 displayformat="dd/MM/yyyy"
                                 datasource="dsRoles:roles_entidad.hasta"></salmon:input>
                           </salmon:td>
                           <salmon:td>
                              <salmon:text name="activoTXT3"
                                 text="activo Goes Here"
                                 font="DefaultFont"
                                 datasource="dsRoles:roles_entidad.activo" />
                           </salmon:td>
                        </salmon:tr>
                     </salmon:datatablerows>
                  </salmon:datatable>
               </salmon:listformdisplaybox>
            </salmon:td>
            <salmon:td valign="Top">
               <salmon:listformdisplaybox name="listformdisplaybox3"
                  mode="Display_single_page"
                  caption="Atributos de la Entidad" width="100%"
                  datasource="dsAtributos" addbuttonvisible="false"
                  savebuttonvisible="true">
                  <salmon:datatable name="datatable3" width="100%"
                     datasource="dsAtributos" rowsperpage="5">
                     <salmon:datatableheader>
                        <salmon:tr>
                           <salmon:td>
                              <salmon:text name="atributoCAP13"
                                 text="Atributo" font="TableHeadingFont" />
                           </salmon:td>
                           <salmon:td>
                              <salmon:text name="valorCAP14"
                                 text="Valor" font="TableHeadingFont" />
                           </salmon:td>
                        </salmon:tr>
                     </salmon:datatableheader>
                     <salmon:datatablerows>
                        <salmon:tr>
                           <salmon:td>
                              <salmon:text name="atributoTXT4"
                                 text="atributo Goes Here"
                                 datasource="dsAtributos:atributos_rol.nombre"
                                 font="DefaultFont" />
                           </salmon:td>
                           <salmon:td>
                              <salmon:lookup
                                 browseimage="%ImageDirectory/Browse.gif"
                                 lookupurl="%LkpValoresAtributos?p_clase_lov="
                                 name="valorTE9" size="40" maxlength="255"
                                 datasource="dsAtributos:atributos_entidad.valor"
                                 popupheight="450" popupwidth="500"
                                 usepopup="true" showdescription="true"></salmon:lookup>
                           </salmon:td>
                        </salmon:tr>
                     </salmon:datatablerows>
                  </salmon:datatable>
               </salmon:listformdisplaybox>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
