<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page
	controller="inventario.controllers.LkpOrdenesCompraController" />
<salmon:body />
<salmon:form name="pageForm">
   <%@include file="message.jsp"%>
   </td>
   <!--Page Content Begin-->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsOrdenesCompra" type="MODEL"
      dbprofile="inventario" model="inventario.models.OrdenesCompraModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:box name="box1" width="100%">
      <salmon:searchformdisplaybox name="searchformdisplaybox1"
         caption="Ordenes de Compra" qbebuilder="dsQBE"
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
         datasource="dsOrdenesCompra"
         lookupreturnexp="ordenes_compra.orden_compra_id"
         lookupdescreturnexp="ordenes_compra.descripcion"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="5" datasource="dsOrdenesCompra">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="ordenCompraIdCAP10" text="ID"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="ordenCompraDescCAP11" text="Descripción"
                        font="TableHeadingFont" />
                  </salmon:td>                  
                  <salmon:td>
                     <salmon:text name="ordenCompraObsCAP11" text="Observaciones"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="ordenCompraProvCAP11" text="Proveedor"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="ordenCompraIdTXT6"
                        text="orden compra id Goes Here" font="DefaultFont"
                        datasource="dsOrdenesCompra:ordenes_compra.orden_compra_id" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="ordenCompraDescTXT7"
                        text="orden compra desc Goes Here" font="DefaultFont"
                        datasource="dsOrdenesCompra:ordenes_compra.descripcion" />
                  </salmon:td>
                  <salmon:td>
                  		<salmon:text name="ordenCompraObsTXT8"
                  		text="ordenes_compra.observaciones goes here" font="DefaultFont"
                  		datasource="dsOrdenesCompra:ordenes_compra.observaciones" />
                  </salmon:td>	                  
                  <salmon:td>
                  		<salmon:text name="ordenCompraProvTXT8"
                  		text="entidad_externa.nombre goes here" font="DefaultFont"
                  		datasource="dsOrdenesCompra:entidad_externa.nombre" />
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
