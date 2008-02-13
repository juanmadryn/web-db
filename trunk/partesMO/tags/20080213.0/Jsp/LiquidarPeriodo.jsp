<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="../../infraestructura/Jsp/ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="partesMO.controllers.LiquidarPeriodoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsResHor" type="MODEL" dbprofile="partesmo"
      model="partesMO.models.ResumenHorasModel"
      autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsPeriodo" type="SQL" autoretrieve="Never">
      <salmon:datasourcedef>
         <salmon:bucket name="mes" datatype="int" />
         <salmon:bucket name="anio" datatype="int" />
      </salmon:datasourcedef>
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
   <salmon:displaybox name="displaybox1" caption="Generación de resumen mensual de horas">
      <salmon:displayboxcontents>
      <table width="100%">
         <tr>
            <td><salmon:text name="mesCAP1" text="Mes"
               font="ColumnCaptionFont" />
            </td>
            <td><salmon:input type="text" name="mesTE1" size="2"
               maxlength="2" datasource="dsPeriodo:mes"></salmon:input>
            </td>
            <td><salmon:text name="anioCAP2" text="Año"
               font="ColumnCaptionFont" />
            </td>
            <td><salmon:input type="text" name="anioTE2" size="4"
               maxlength="4" datasource="dsPeriodo:anio"></salmon:input>
            </td>
            <td><salmon:input name="generarBUT" type="submit" value="Generar"></salmon:input>
           </td>
         </tr>
      </table>
      </salmon:displayboxcontents>
   </salmon:displaybox>   
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox mode="Display_single_page"
         caption="Liquidación de horas x quincena" name="listformdisplaybox1"
         addbuttonvisible="False" datasource="dsResHor"
         savebuttonvisible="False" width="100%">
         <salmon:datatable name="datatable2" datasource="dsResHor"
            rowsperpage="100" width="100%">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="periodoCAP10" text="Periodo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="quincenaCAP11"
                        text="Quincena" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="legajoCAP12" text="Legajo"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasCAP13" text="Horas"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="normalesCAP14" text="Normales"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="al50CAP15" text="Al 50%"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="al100CAP16" text="Al 100%"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nocturnasCAP17" text="Nocturnas"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="periodoTE10" text="Periodo"
                        font="DefaultFont" displayformat="dd/MM/yyyy"
                        datasource="dsResHor:resumen_horas.periodo" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="quincenaTE11" text="quincena"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.quincena" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nroLegajoTE12" text="nro_legajo"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.nro_legajo" />
                     <salmon:text name="guionTE12" text=" - "
                        font="DefaultFont" />
                     <salmon:text name="apeynomTE12" text="apeynom"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.apeynom" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="horasTE13" text="horas"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.horas" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="normalesTE14" text="Normales"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.normales" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="al50TE15" text="al_50"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.al_50" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="al100TE16" text="al_100"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.al_100" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="nocturnasTE17" text="nocturnas"
                        font="DefaultFont"
                        datasource="dsResHor:resumen_horas.nocturnas" />
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
