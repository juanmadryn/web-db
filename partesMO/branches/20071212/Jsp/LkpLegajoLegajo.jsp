<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page controller="tango.controllers.LkpLegajoController"/>
<salmon:body />
<salmon:form name="pageForm">
   <%@include file="message.jsp"%>
   </td>
   <!--Page Content Begin-->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="legajo" type="complex" 
         columns="LEGAJO.ID_LEGAJO, LEGAJO.NRO_LEGAJO" />
      <salmon:qbecriteria name="nombre" type="complex" 
         columns="LEGAJO.CUIL, LEGAJO.NOMBRE, LEGAJO.APELLIDO" />
   </salmon:datasource>
   <salmon:datasource name="dsLegajo" type="MODEL" dbprofile="tango"
      model="tango.models.LegajoView" autoretrieve="Never">
   </salmon:datasource>
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Personal" qbebuilder="dsQBE"
         listformdisplaybox="listformdisplaybox1"
         addbuttonvisible="False"
         searchbuttonvisible="False"
         cancelbuttonvisible="False">
         <table width="100%">
            <tr>
               <td><salmon:text name="buscarCAP1" text="Legajo"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarTE3"
                  size="10" maxlength="15" datasource="dsQBE:legajo"></salmon:input></td>
               <td><salmon:text name="buscarCAP2" text="Nombre"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarNombreTE3"
                  size="30" maxlength="90" datasource="dsQBE:nombre"></salmon:input></td>
            </tr>
         </table>
      </salmon:searchformdisplaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page" caption=" " width="100%"
         addbuttonvisible="False" savebuttonvisible="False"
         datasource="dsLegajo" lookupreturnexp="LEGAJO.NRO_LEGAJO"
         lookupdescreturnexp="LEGAJO.APELLIDO"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsLegajo">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="legajoIdCAP2" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoCAP3" text="legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="apellidoCAP4" text="Apellido"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP5" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cuilCAP6" text="CUIL"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="legajoIdTXT2"
                        text="id_legajo Goes Here" font="DefaultFont"
                        datasource="dsLegajo:LEGAJO.ID_LEGAJO" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoTXT3"
                        text="nro_legajo Goes Here" font="DefaultFont"
                        datasource="dsLegajo:LEGAJO.NRO_LEGAJO" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="apellidoTXT4"
                        text="apellido Goes Here" font="DefaultFont"
                        datasource="dsLegajo:LEGAJO.APELLIDO" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT5"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsLegajo:LEGAJO.NOMBRE" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="cuilTXT6" text="cuil Goes Here"
                        font="DefaultFont"
                        datasource="dsLegajo:LEGAJO.CUIL" />
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
