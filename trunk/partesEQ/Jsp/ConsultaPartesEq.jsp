<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí														  -->
   <!-- ********************************************************************************************* -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsPartes" type="MODEL" dbprofile="partesEQ"
      model="partesEQ.models.PartesEqModel" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí														      -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Partes de Equipos" qbebuilder="dsQBE">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="buscarCAP1"
                           text="Buscar" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="buscarTE3" size="60" maxlength="90"
                           datasource="dsQBE:buscar"></salmon:input></td>
                     </tr>
                  </table>
               </salmon:searchformdisplaybox>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page" caption=" " width="100%"
         datasource="dsPartes"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsPartes">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="parteIdCAP2" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaCAP22" text="Fecha"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="codinvCAP23" text="Equipo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="proyectoCAP24"
                        text="Proyecto (OT)" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="choferCAP26"
                        text="Chofer" font="TableHeadingFont" />
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
                     <salmon:a href="none" name="lnkpartes1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsPartes:'%EditaParte?p_parte_id='+partes_eq.parte_id">
                        <salmon:text name="proyectoTXT1"
                           text="parte_id Goes Here" font="DefaultFont"
                           datasource="dsPartes:partes_eq.parte_id" />
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fechaTE22" text="fecha"
                        font="DefaultFont"
                        displayformat="dd/MM/yyyy"
                        datasource="dsPartes:partes_eq.fecha" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="codinvTE23" text="codigo_inventario"
                        font="DefaultFont"
                        datasource="dsPartes:equipos.codigo_inventario" />
                     <salmon:text name="nombreTE23" text="nombreequipo"
                        font="DefaultFont"
                        datasource="dsPartes:equipos.nombre" />
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
                     <salmon:text name="choferTE26" text="chofer"
                        font="DefaultFont"
                        datasource="dsPartes:choferes.apeynom" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horaDesdeTE26" text="hora_desde"
                        font="DefaultFont"
                        datasource="dsPartes:partes_eq.hora_desde" />
                     <salmon:text name="horaaTE26" text=" a "
                        font="DefaultFont" />
                     <salmon:text name="horaHastaTE26" text="hora_hasta"
                        font="DefaultFont"
                        datasource="dsPartes:partes_eq.hora_hasta" />
                     <salmon:text name="horaGuionTE26" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="horasTE26" text="horas"
                        font="DefaultFont"
                        datasource="dsPartes:partes_eq.horas" />
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
