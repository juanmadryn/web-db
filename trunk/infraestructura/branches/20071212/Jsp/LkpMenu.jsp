<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page />
<salmon:body />
<salmon:form name="pageForm">
   <%@include file="message.jsp"%>
   </td>
   <!--Page Content Begin-->
   <!-- Agregar definici�n de DataSource aqu� -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsMenu" type="MODEL"
      dbprofile="infraestructura"
      model="infraestructura.models.MenuModel" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar c�digo de la p�gina aqu� -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Menues de la Aplicaci�n" qbebuilder="dsQBE"
         listformdisplaybox="listformdisplaybox1"
         addbuttonvisible="False">
         <table width="100%">
            <tr>
               <td><salmon:text name="buscarCAP5" text="Buscar"
                  font="ColumnCaptionFont" /></td>
               <td><salmon:input type="text" name="buscarTE3"
                  size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
            </tr>
         </table>
      </salmon:searchformdisplaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page" caption=" " width="100%"
         addbuttonvisible="False" savebuttonvisible="False"
         datasource="dsMenu" lookupreturnexp="menu.menu_id"
         lookupdescreturnexp="menu.nombre"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsMenu">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="rolIdCAP10" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP11" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP12"
                        text="Descripci�n" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="grupoCAP13" text="Grupo"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="rolIdTXT6"
                        text="menu.rol_id Goes Here" font="DefaultFont"
                        datasource="dsMenu:menu.menu_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTXT7"
                        text="menu.nombre Goes Here" font="DefaultFont"
                        datasource="dsMenu:menu.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT8"
                        text="menu.descripcion Goes Here"
                        font="DefaultFont"
                        datasource="dsMenu:menu.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="grupoTXT8"
                        text="menu.grupo Goes Here" font="DefaultFont"
                        datasource="dsMenu:menu.grupo" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de c�digo agregado -->
   <!--Page Content End-->
</salmon:form>
<salmon:endbody />
<salmon:endpage />
</html>
