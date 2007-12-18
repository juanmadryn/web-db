<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
   controller="partesEQ.controllers.CargarPartesEqPlanoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí						      						      -->
   <!-- ********************************************************************************************* -->
   <salmon:datasource name="dsPartes" type="MODEL" dbprofile="partesEQ"
      model="partesEQ.models.PartesEqModel" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí							     							  -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:listformdisplaybox mode="Display_single_page"
         caption="Carga de Partes de Equipos" name="listFormDisplayBox1"
         width="100%" addbuttonvisible="false" savebuttonvisible="false"
         datasource="dsPartes">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsPartes" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="fechaCAP3" text="Fecha"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="codinvCAP1" text="Equipo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="proyectoCAP4" text="Proyecto"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horaDesdeCAP5" text="Desde"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horaHastaCAP5" text="Hasta"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasCAP6" text="Horas"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="mensajesCAP10" text="Mensaje"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td/>
                  <salmon:td>
                     <salmon:text name="choferCAP7" text="Chofer"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td/>                                    
                  <salmon:td>
                     <salmon:text name="accionesCAP9" text="Acciones"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td/>
                  <salmon:td/>
                  <salmon:td>
                     <salmon:text name="selParteCAP70" text="-X-"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:input name="fechaTE3" type="text" size="10"
                        maxlength="10" displayformat="dd/MM/yyyy"
                        datasource="dsPartes:partes_eq.fecha">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:lookup
                        browseimage="%ImageDirectory/Browse.gif"
                        lookupurl="%LkpEquipos" name="codinvTE1"
                        size="10" maxlength="25"                        
                        datasource="dsPartes:equipos.codigo_inventario"                        
                        popupheight="450" popupwidth="560"
                        usepopup="true" showdescription="False"></salmon:lookup>
                     <salmon:text name="nombreequipoTEX2"
                       	text="nombre goes here" font="DefaultFont"
                       	datasource="dsPartes:equipos.nombre" />                     
                  </salmon:td>
                  <salmon:td width="250">
                     <salmon:lookup
                        browseimage="%ImageDirectory/Browse.gif"
                        lookupurl="%LkpProyectos" name="proyectoTE3"
                        size="10" maxlength="15"
                        datasource="dsPartes:proyectos.proyecto"
                        descriptiondatasource="dsPartes:proyectos.nombre"
                        popupheight="450" popupwidth="500"
                        usepopup="true" showdescription="True"></salmon:lookup>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="horaDesdeTE4"
                        size="5" maxlength="5"
                        datasource="dsPartes:partes_eq.hora_desde">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="horahastaTE5"
                        size="5" maxlength="5"
                        datasource="dsPartes:partes_eq.hora_hasta">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="text" name="horasTE6" size="5"
                        maxlength="5" displayformat="##0.00"
                        datasource="dsPartes:partes_eq.horas">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="mensajesTE19"
                        text="mensaje_error goes here"
                        font="DefaultFont"
                        datasource="dsPartes:mensaje_error" />
                  </salmon:td>
               </salmon:tr>
               <salmon:tr>
                  <salmon:td/>                                    
                  <salmon:td>
                        <salmon:lookup browseimage="%ImageDirectory/Browse.gif"
							lookupurl="%LkpChoferes" name="choferTE1" size="10" 
							maxlength="15" displayformat="##########0" 
							datasource="dsPartes:choferes.nro_legajo"							
							popupheight="450" popupwidth="560" usepopup="true"
							showdescription="False"></salmon:lookup>
						<salmon:text name="apeynomTEX1"
                        	text="apeynom goes here" font="DefaultFont"
                        	datasource="dsPartes:choferes.apeynom" />
                  </salmon:td>
                  <salmon:td/>
                  <salmon:td>
                     <salmon:input type="submit" name="nuevoBUT91"
                        value="Copiar" accesskey="c">
                     </salmon:input>
                  </salmon:td>
                  <salmon:td>
                     <salmon:input type="submit" name="nuevoBUT92"
                        value="Nuevo" accesskey="n">
                     </salmon:input>
                  </salmon:td>                  
                  <salmon:td/>
                  <salmon:td>
                     <salmon:input type="checkbox" name="seleccionParteEq"
                        checkedtruevalue="1"></salmon:input>
                  </salmon:td>
               </salmon:tr>
            </salmon:datatablerows>
         </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>
