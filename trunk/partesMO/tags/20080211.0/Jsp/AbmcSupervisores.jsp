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
   <salmon:datasource name="dsSupervisor" type="MODEL"
      dbprofile="partesmo" model="partesMO.models.SupervisoresModel"
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
                  caption="Supervisores del personal" qbebuilder="dsQBE">
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
                  caption="Editar Supervisor" width="100%"
                  datasource="dsSupervisor"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="idCAP23"
                           text="Supervisor" font="ColumnCaptionFont" /></td>
                        <td width="300"><salmon:lookup
                           browseimage="%ImageDirectory/Browse.gif"
                           lookupurl="%LkpLegajoLegajo" name="supervisoTE4"
                           size="6" maxlength="10"
                           displayformat="##########0"
                           datasource="dsSupervisor:supervisores.nro_legajo"
                           popupheight="450" popupwidth="500"
                           usepopup="true" showdescription="False"></salmon:lookup></td>
                        <td><salmon:text name="vigenciaCAP20"
                           text="Vigencia" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="desdeTE21"
                           type="text" size="10" maxlength="10"
                           displayformat="dd/MM/yyyy"
                           datasource="dsSupervisor:supervisores.fecha_desde"></salmon:input>
                        <salmon:input name="hastaTE22" type="text"
                           size="10" maxlength="10"
                           displayformat="dd/MM/yyyy"
                           datasource="dsSupervisor:supervisores.fecha_hasta"></salmon:input>
                        </td>
                     </tr>
                     <tr>
                        <td><salmon:text name="apeynomCAP24"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:text name="apeynomTE10"
                           text="apeynom goes here" font="DeafultFint"
                           datasource="dsSupervisor:supervisores.apeynom" /></td>
                        <td><salmon:text name="legajoCAP25"
                           text="ID" font="ColumnCaptionFont" /></td>
                        <td><salmon:text name="legajoTE11"
                           text="legajo goes here" font="DeafultFint"
                           datasource="dsSupervisor:supervisores.personal_id" /></td>
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
         datasource="dsSupervisor" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsSupervisor" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="idCAP19" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoCAP20" text="Legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="apeynomCAP19" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="idTXT18"
                        text="personal_id Goes Here" font="DefaultFont"
                        datasource="dsSupervisor:supervisores.personal_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoTXT18"
                        text="legajo Goes Here" font="DefaultFont"
                        datasource="dsSupervisor:supervisores.nro_legajo" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="apeynombreTXT16"
                        text="apeynom Goes Here" font="DefaultFont"
                        datasource="dsSupervisor:supervisores.apeynom" />
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
