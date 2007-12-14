<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="../../infraestructura/Jsp/ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="partesMO.controllers.AdmLotesPartesController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsPartes" type="MODEL" dbprofile="partesmo"
      model="partesMO.models.PartesMoModel" autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsLotes" type="MODEL" dbprofile="partesmo"
      model="partesMO.models.LoteCargaPartesMoModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsQBELotes" type="QBE">
      <salmon:qbecriteria name="descripcion" type="COMPLEX"
         columns="lote_carga_partes_mo.descripcion" />
      <salmon:qbecriteria name="fecha_desde" type="GTE" 
         columns="lote_carga_partes_mo.fecha_alta" />
      <salmon:qbecriteria name="fecha_hasta" type="LTE"
         columns="lote_carga_partes_mo.fecha_cierre" />
      <salmon:qbecriteria name="estado" type="COMPLEX"
         columns="lote_carga_partes_mo.estado" />
   </salmon:datasource>
   <salmon:datasource name="dsQBEPartes" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1" 
         caption="Lotes de partes" qbebuilder="dsQBE"
         searchbuttonvisible="False" addbuttonvisible="False">
         <table width="100%">
            <tr>
               <td><salmon:text name="buscarCAP1"
                  text="Descripción" font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarTE1"
                  size="30" maxlength="255"
                  datasource="dsQBELotes:descripcion"></salmon:input></td>
               <td><salmon:text name="buscarCAP2" text="Estado"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input name="buscarTE21" type="select"
                  datasource="dsQBELotes:estado">
                  <salmon:option display="abc" key="123"
                     table="infraestructura.estados"
                     criteria="circuito='0004'" displaycolumn="nombre"
                     keycolumn="estado" nulloption="true"></salmon:option>
               </salmon:input></td>
            </tr>
            <tr>
               <td><salmon:text name="buscarCAP3"
                  text="Fecha Desde" font="ColumnCaptionFont" /></td>
               <td><salmon:input name="buscarTE3" type="text"
                  size="10" maxlength="10" 
                  datasource="dsQBELotes:fecha_desde">
               </salmon:input></td>
               <td><salmon:text name="buscarCAP4"
                  text="Fecha Hasta" font="ColumnCaptionFont" /></td>
               <td><salmon:input name="buscarTE4" type="text"
                  size="10" maxlength="10" 
                  datasource="dsQBELotes:fecha_hasta">
               </salmon:input></td>
            </tr>
         </table>
      </salmon:searchformdisplaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox mode="Display_single_page"
         caption="Lotes" name="listformdisplaybox1"
         addbuttonvisible="False" datasource="dsLotes"
         savebuttonvisible="False" width="100%">
         <salmon:datatable name="datatable1" datasource="dsLotes"
            rowsperpage="5" width="100%">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="loteIdCAP10" text="Lote"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP11"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="estadoCAP12" text="Estado"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaAltaCAP13" text="Alta"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaCierreCAP14" text="Cierre"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="loteIdTE10" text="Lote"
                        font="DefaultFont"
                        datasource="dsLotes:lote_carga_partes_mo.lote_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:input name="descripcionTE11" type="text"
                        datasource="dsLotes:lote_carga_partes_mo.descripcion"
                        size="30" maxlength="255"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="estadoTE12" text="Estado"
                        font="DefaultFont"
                        datasource="dsLotes:estados.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaAltaTE13" text="Alta"
                        font="DefaultFont"
                        datasource="dsLotes:lote_carga_partes_mo.fecha_alta" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaCierreTE14" text="Cierre"
                        font="DefaultFont"
                        datasource="dsLotes:lote_carga_partes_mo.fecha_cierre" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <salmon:box name="box3" width="100%">
      <salmon:listformdisplaybox mode="Display_single_page"
         caption="Partes de mano de Obra" name="listformdisplaybox2"
         addbuttonvisible="False" datasource="dsPartes"
         savebuttonvisible="False" width="100%">
         <salmon:datatable name="datatable2" datasource="dsPartes"
            rowsperpage="10" width="100%">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="selCAP20" text="-X-"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="parteIdCAP21" text="parte"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaCAP22" text="Fecha"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoCAP23" text="Legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="proyectoCAP24"
                        text="Proyecto (OT)" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="sectorCAP25" text="Sector"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="supervisorCAP26"
                        text="Supervisor" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horarioCAP27" text="Horario"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:input type="checkbox" name="seleccionParte"
                        checkedtruevalue="1"></salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="parteIdTE21" text="parte_id"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.parte_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaTE22" text="fecha"
                        font="DefaultFont"
                        displayformat="dd/MM/yyyy"
                        datasource="dsPartes:partes_mo.fecha" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nroLegajoTE23" text="nro_legajo"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.nro_legajo" />
                     <salmon:text name="apeynomTE23" text="apeynom"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.apeynom" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="proyectosProyectoTE24" text="Proyectos.proyecto"
                        font="DefaultFont"
                        datasource="dsPartes:proyectos.proyecto" />
                     <salmon:text name="proyectosNombreTE24" text="Proyectos.nombre"
                        font="DefaultFont"
                        datasource="dsPartes:proyectos.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="sectrorTE25" text="sector"
                        font="DefaultFont"
                        datasource="dsPartes:sector_trabajo.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="supervisorTE26" text="supervisor"
                        font="DefaultFont"
                        datasource="dsPartes:supervisores.apeynom" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horaDesdeTE26" text="hora_desde"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.hora_desde" />
                     <salmon:text name="horaaTE26" text=" a "
                        font="DefaultFont" />
                     <salmon:text name="horaHastaTE26" text="hora_hasta"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.hora_hasta" />
                     <salmon:text name="horaGuionTE26" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="horasTE26" text="horas"
                        font="DefaultFont"
                        datasource="dsPartes:partes_mo.horas" />
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
