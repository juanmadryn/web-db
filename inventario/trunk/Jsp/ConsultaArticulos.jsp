<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.ConsultaArticulosController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí														  -->
   <!-- ********************************************************************************************* -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
      <salmon:qbecriteria name="anulado" type="not_equeals" columns="articulos.anulado"/>
   </salmon:datasource>
   <salmon:datasource name="dsArticulos" type="MODEL" dbprofile="inventario"
      model="inventario.models.ArticulosModel" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí														      -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Artículos" qbebuilder="dsQBE" searchbuttonvisible="false" addbuttonvisible="false">
                  <table width="100%">
                  		<tr>	
								<td colspan="3"/>								
								<td><salmon:text name="atributo1" text="Atributo" font="TableHeadingFont" /></td>
								<td><salmon:text name="valor1" text="Valor" font="TableHeadingFont" /></td>
						</tr>
                     	<tr>                     
                        	<td><salmon:text name="buscarCAP1"
                           		text="Buscar" font="ColumnCaptionFont" /></td>
                        	<td><salmon:input type="text"
                           		name="buscarTE3" size="60" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
                           	<td width="40px"/>
							<td>
								<salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP1" 
									size="15"	maxlength="25" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
							</td>
							<td><salmon:input name="valorAttr1" type="text" /></td>
                     	</tr>
                     	<tr>
                     		<td colspan="3"/>
							<td>
								<salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP2" 
									size="15"	maxlength="25"popupheight="450"
									popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
							</td>
							<td><salmon:input name="valorAttr2" type="text" /></td>
                     	</tr>
                     	<tr>
							<td colspan="3"/>
							<td>
								<salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpAtributosRol?nombre_objeto=articulos" name="lkpAttrINP3" 
									size="15"	maxlength="25" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="false"></salmon:lookup>
							</td>
							<td><salmon:input name="valorAttr3" type="text"/></td>
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
         datasource="dsArticulos"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            rowsperpage="10" datasource="dsArticulos">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="parteIdCAP2" text="Id"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreCAP22" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP23" text="Descripción"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="claseCAP24"
                        text="Clase de Artículo" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="observacionCAP26"
                        text="Observaciones" font="TableHeadingFont" />
                  </salmon:td>                  
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:a href="none" name="lnkpartes1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsArticulos:'%AbmcArticulo?p_articulo_id='+articulos.articulo_id">
                        <salmon:text name="idTXT1"
                           text="articulos.articulo_id" font="DefaultFont"
                           datasource="dsArticulos:articulos.articulo_id" />
                     </salmon:a>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nombreTE22" text="articulos.nombre"
                        font="DefaultFont"                        
                        datasource="dsArticulos:articulos.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTE23" text="articulos.descripcion"
                        font="DefaultFont"
                        datasource="dsArticulos:articulos.descripcion" />                     
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="claseArticuloTE24" text="clase_articulo.nombre"
                        font="DefaultFont"
                        datasource="dsArticulos:clase_articulo.nombre" />                     
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="articulosTE26" text="articulos.observaciones"
                        font="DefaultFont"
                        datasource="dsArticulos:articulos.observaciones" />
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
