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
      <salmon:qbecriteria name="buscar" type="complex" columns="*"/>      
   </salmon:datasource>
   <salmon:datasource name="dsLegajos" type="MODEL" dbprofile="tango"
      model="tango.models.LegajoView" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí														      -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Artículos" qbebuilder="dsQBE" searchbuttonvisible="true" addbuttonvisible="false">
                  <table width="100%">
                  		<tr>                     
                        	<td><salmon:text name="buscarCAP"
                           		text="Buscar" font="ColumnCaptionFont" />
                           	</td>
                        	<td><salmon:input type="text"
                           		name="buscarTE" size="60" maxlength="90" datasource="dsQBE:buscar"></salmon:input>
                           	</td>                           	
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
         datasource="dsLegajos"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="10" datasource="dsLegajos">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nroLegajoCAP" text="Nro. Legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>                  
                  <salmon:td>
                     <salmon:text name="apellidoCAP" text="Apellido" 
                     	font="TableHeadingFont" />
                  </salmon:td>                  
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
               	  <salmon:td>
                     <salmon:text name="nroLegajoTE" text="LEGAJO.NRO_LEGAJO"
                        font="DefaultFont"
                        datasource="dsLegajos:LEGAJO.NRO_LEGAJO" />                     
                  </salmon:td>                                    
                  <salmon:td>
                     <salmon:text name="nombreTE" text="LEGAJO.NOMBRE"
                        font="DefaultFont" datasource="dsLegajos:LEGAJO.NOMBRE" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="apellidoTE" text="LEGAJO.APELLIDO"
                        font="DefaultFont"
                        datasource="dsLegajos:LEGAJO.APELLIDO" />                     
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
