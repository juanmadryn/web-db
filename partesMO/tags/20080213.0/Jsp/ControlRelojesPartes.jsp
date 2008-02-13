<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="../../infraestructura/Jsp/ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="partesMO.controllers.ControlRelojesPartesController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsResHor" type="MODEL" dbprofile="partesmo"
      model="partesMO.models.ResumenHorasRelojModel"
      autoretrieve="Never">
   </salmon:datasource>   
   <salmon:datasource name="dsPeriodo" type="SQL" autoretrieve="Never">
      <salmon:datasourcedef>
         <salmon:bucket name="desde" datatype="DATE" />
         <salmon:bucket name="hasta" datatype="DATE" />
      </salmon:datasourcedef>
   </salmon:datasource>   
   <salmon:datasource name="dsQBEResHor" type="QBE">
      <salmon:qbecriteria name="nrolegajo" type="COMPLEX" columns="resumen_horas_reloj.nro_legajo,resumen_horas_reloj.apeynom" />
      <salmon:qbecriteria name="estado" type="IN" columns="resumen_horas_reloj.estado" />
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
   		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
   					<salmon:searchformdisplaybox caption="Control de Relojes Partes MO" name="searchformdisplaybox1"
   						searchbuttonvisible="False" addbuttonvisible="False" qbebuilder="dsQBEResHor">
							<table width="100%">
								<tr>
									<td><salmon:text name="estadoCAP19"
                           				text="Estado" font="ColumnCaptionFont" /></td>
                        			<td colspan="2"><salmon:input name="estadoTE19" type="select"
                           				datasource="dsQBEResHor:estado"></salmon:input></td>                        				
								</tr>
								<tr>
            						<td><salmon:text name="fechadesdeCAP1" text="Fecha desde" font="ColumnCaptionFont" /></td>
            						<td><salmon:input type="text" name="fechadesdeTE1" size="10" displayformat="dd/MM/yyyy" 
            							datasource="dsPeriodo:desde" maxlength="10"></salmon:input></td>
            						<td><salmon:text name="fechahastaCAP2" text="Fecha hasta" font="ColumnCaptionFont" /></td>
            						<td><salmon:input type="text" name="fechahastaTE2" size="10" displayformat="dd/MM/yyyy" 
            							datasource="dsPeriodo:hasta" maxlength="10"></salmon:input></td>							
								</tr>
								<tr>
									<td><salmon:text name="buscarCAP16" text="Nro de Legajo"
										font="ColumnCaptionFont" /></td>
									<td colspan="2"><salmon:input type="text" name="buscarTE3" size="30" 
            							datasource="dsQBEResHor:nrolegajo" maxlength="50"></salmon:input></td>
								</tr>
							</table>
					</salmon:searchformdisplaybox>   					
   				</salmon:td>   				
   			</salmon:tr>
   		</salmon:table>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox mode="Display_single_page"
         caption="Resumen Control de relojes" name="listformdisplaybox1"
         addbuttonvisible="False" datasource="dsResHor"
         savebuttonvisible="False" width="100%">
         <salmon:datatable name="datatable2" datasource="dsResHor"
            rowsperpage="25" width="100%">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="selParteCAP18" text="-X-"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="periodoCAP10" text="Fecha"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoCAP12" text="Legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasCAP13" text="Total Horas (Partes)"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasrelojCAP14" text="Total Horas (Reloj)"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="relojdesdehastaCAP17"
                        text="Horario (Reloj)" font="TableHeadingFont" />
                  </salmon:td>
                  <!-- <salmon:td>
                     <salmon:text name="relojhastaCAP18"
                        text="Reloj Hasta" font="TableHeadingFont" />
                  </salmon:td> -->
                  <salmon:td>
                     <salmon:text name="obsvCAP15" text="Observaciones"
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
                   <salmon:a href="none" name="lnkpartes1"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsResHor:'%ConsultaPartes?v_fecha='+resumen_horas_reloj.fecha+'&v_nro_legajo='+resumen_horas_reloj.nro_legajo">
                     <salmon:text name="periodoTE10" text="Periodo"
                        font="DefaultFont" displayformat="dd/MM/yyyy"
                        datasource="dsResHor:resumen_horas_reloj.fecha" />
                    </salmon:a>
                  </salmon:td>
                  <salmon:td>
                  	 <salmon:a href="none" name="lnkpartes2"
                        onclick="document.forms['bannerForm'].submit();"
                        datasource="dsResHor:'%EditaParte?p_grp_parte_id='+resumen_horas_reloj.parte_ids">                        
                     	<salmon:text name="nroLegajoTE12" text="nro_legajo"
                        	font="DefaultFont"
                        	datasource="dsResHor:resumen_horas_reloj.nro_legajo"/>
                        </salmon:a>                     
                     	<salmon:text name="guionTE12" text=" - "
                        	font="DefaultFont" />
                     	<salmon:text name="apeynomTE12" text="apeynom"
                        	font="DefaultFont"
                        	datasource="dsResHor:resumen_horas_reloj.apeynom" />                     
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasTE13" text="horas"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas_reloj.horas_parte" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasrelojTE14" text="horas reloj"
                        font="DefaultFont" displayformat="##.##"
                        datasource="dsResHor:resumen_horas_reloj.horas_fichada" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="relojdesdehastaTE17" text="reloj desde"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas_reloj.horarios" />
                  </salmon:td>        
                  <!-- <salmon:td>
                     <salmon:text name="relojhastaTE18" text="reloj hasta"
                        font="DefaultFont" displayformat="00.00"
                        datasource="dsResHor:resumen_horas_reloj.fichada_h" />
                  </salmon:td> -->                                            
                  <salmon:td>
                     <salmon:text name="obsvTE15" text="observaciones"
                        font="DefaultFont" datasource="dsResHor:resumen_horas_reloj.observaciones" />
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
