<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="tango.controllers.ConsultaCompradoresController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>   
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí														  -->
   <!-- ********************************************************************************************* -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*"/>      
   </salmon:datasource>
   <salmon:datasource name="dsCompradores" type="MODEL" dbprofile="tango"
      model="tango.models.Cpa50View" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí														      -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Compradores" qbebuilder="dsQBE" searchbuttonvisible="true" addbuttonvisible="false">
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
         datasource="dsCompradores" autocreatelink="false"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="10" datasource="dsCompradores">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="idCAP" text="Id"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="fillerCAP" text="Filler"
                        font="TableHeadingFont" />
                  </salmon:td>                  
                  <salmon:td>
                     <salmon:text name="codcompraCAP" text="Cod. Comprador" 
                     	font="TableHeadingFont" />
                  </salmon:td>                  
                  <salmon:td>
                     <salmon:text name="nomcompraCAP" text="Nombre Comprador" 
                     	font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
               	  <salmon:td>
                     <salmon:text name="idTE" text="Cpa50.ID_CPA50"
                        font="DefaultFont"
                        datasource="dsCompradores:Cpa50.ID_CPA50" />                     
                  </salmon:td>                                    
                  <salmon:td>
                     <salmon:text name="fillerTE" text="Cpa50.FILLER"
                        font="DefaultFont" datasource="dsCompradores:Cpa50.FILLER" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="codcompraTE" text="Cpa50.COD_COMPRA"
                        font="DefaultFont"
                        datasource="dsCompradores:Cpa50.COD_COMPRA" />                     
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nomcompraTE" text="Cpa50.NOM_COMPRA"
                        font="DefaultFont"
                        datasource="dsCompradores:Cpa50.NOM_COMPRA" />                     
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
