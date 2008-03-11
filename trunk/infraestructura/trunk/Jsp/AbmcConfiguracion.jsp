<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page
   controller="infraestructura.controllers.AbmcConfiguracionController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsEsquema" type="MODEL"
      model="infraestructura.models.EsquemaConfiguracionModel"
      dbprofile="infraestructura" autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsConf" type="MODEL"
      model="infraestructura.models.ConfiguracionModel"
      dbprofile="infraestructura" autoretrieve="Never">
   </salmon:datasource>
   <salmon:datasource name="dsAttr" type="MODEL"
      model="infraestructura.models.AtributosConfiguracionModel"
      dbprofile="infraestructura" autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:displaybox name="displaybox1" width="100%" datasource="dsEsquema"
         caption="Esquema de Configuración">
         <salmon:displayboxheader>
            <salmon:a href="javascript:window.history.back()" name="lnkBack">
               <salmon:text name="back" text="Regresar" font="ColumnCaptionFont" />
            </salmon:a>
         </salmon:displayboxheader>
         <salmon:displayboxcontents>
            <table width="100%">
               <tr>
                  <td><salmon:text name="idEsquemaCAP1" text="ID"
                     font="ColumnCaptionFont" />
                  <td>
                  <td><salmon:text name="idEsquemaTE1"
                     text="id goes here" font="ColumnCaptionFont"
                     datasource="dsEsquema:esquema_configuracion.esquema_configuracion_id" />
                  </td>
                  <td><salmon:text name="nombreEsquemaCAP2"
                     text="Nombre" font="ColumnCaptionFont" />
                  <td>
                  <td><salmon:text name="nombreEsquemaTE2"
                     text="nombre goes here" font="ColumnCaptionFont"
                     datasource="dsEsquema:esquema_configuracion.nombre" />
                  </td>
                  <td><salmon:text name="descripcionEsquemaTE3"
                     text="descripcion goes here"
                     font="ColumnCaptionFont"
                     datasource="dsEsquema:esquema_configuracion.descripcion" />
                  </td>
               <tr>
            </table>
         </salmon:displayboxcontents>
      </salmon:displaybox>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="displaybox2" width="100%" datasource="dsConf"
         mode="Display_single_page"
         addbuttonvisible="False" savebuttonvisible="False"
         caption="Configuraciones del esquema">
            <salmon:datatable name="datatable2" width="100%"
               datasource="dsConf" rowsperpage="5">
               <salmon:datatableheader>
                  <salmon:tr>
                     <salmon:td>
                        <salmon:text name="selCAP3" text="-X-"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="idConfCAP4" text="ID"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="nombreConfCAP4" text="Nombre"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="descripcionConfCAP5" text="Descripción"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="observacionConfCAP6" text="Observaciones"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="prioridadConfCAP7" text="Prioridad"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="cardinalidadConfCAP8" text="Cardinalidad"
                           font="TableHeadingFont" />
                     </salmon:td>
                  </salmon:tr>
               </salmon:datatableheader>
               <salmon:datatablerows>
                  <salmon:tr>
                     <salmon:td>
                        <salmon:input type="checkbox" name="seleccion1"
                           checkedtruevalue="1"></salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="idConfTE1" text="id goes here"
                              font="ColumnCaptionFont" datasource="dsConf:configuracion.configuracion_id" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:input type="text"
                           name="nombreConfTE2" size="20" maxlength="90"
                           datasource="dsConf:configuracion.nombre">
                        </salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:input type="text"
                           name="descripcionConfTE2" size="30" maxlength="90"
                           datasource="dsConf:configuracion.descripcion">
                        </salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:input type="text"
                           name="observacionesConfTE2" size="40" maxlength="90"
                           datasource="dsConf:configuracion.observaciones">
                        </salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:input type="text"
                           name="prioridadConfTE2" size="3" maxlength="3" displayformat="###0"
                           datasource="dsConf:configuracion.prioridad">
                        </salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="cardinalidadConfTE3" text="cardinalidad goes here"
                              font="ColumnCaptionFont" displayformat="###0"
                              datasource="dsConf:configuracion.cardinalidad" />
                     </salmon:td>
                  </salmon:tr>
               </salmon:datatablerows>
            </salmon:datatable>
      </salmon:listformdisplaybox>
   </salmon:box>
   <salmon:box name="box3" width="100%">
      <salmon:displaybox name="displaybox3" width="100%" datasource="dsAttr"
         caption="Atributos que determinan la Configuración del esquema">
         <salmon:displayboxheader>
            <salmon:input name="agregarAttrBUT6" type="submit"
               value="Agregar" accesskey="A"></salmon:input>
            <salmon:input name="eliminarAttrBUT7" type="submit"
               value="Eliminar" accesskey="E"></salmon:input>
            <salmon:input name="actualizarAttrBUT8" type="submit"
               value="Grabar" accesskey="G"></salmon:input>
            <salmon:input name="cancelarAttrBUT9" type="submit"
               value="Cancelar" accesskey="C"></salmon:input>
         </salmon:displayboxheader>
         <salmon:displayboxcontents>
            <salmon:datatable name="datatable3" width="100%"
               datasource="dsAttr" rowsperpage="5">
               <salmon:datatableheader>
                  <salmon:tr>
                     <salmon:td>
                        <salmon:text name="selCAP3" text="-X-"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="atributoCAP4" text="Atributo"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="valorAttrCAP5" text="Valor desde"
                           font="TableHeadingFont" />
                     </salmon:td>
                     <salmon:td>
                        <salmon:text name="valorAttrCAP6" text="Valor hasta"
                           font="TableHeadingFont" />
                     </salmon:td>
                  </salmon:tr>
               </salmon:datatableheader>
               <salmon:datatablerows>
                  <salmon:tr>
                     <salmon:td>
                        <salmon:input type="checkbox" name="seleccion2"
                           checkedtruevalue="1"></salmon:input>
                     </salmon:td>
                     <salmon:td>
                        <salmon:lookup
                           browseimage="%ImageDirectory/Browse.gif"
                           lookupurl="%LkpAtributosRol"
                           name="AtributoTE4" size="6" maxlength="15"
                           descriptiondatasource="dsAttr:atributos_rol.nombre"
                           datasource="dsAttr:atributos_configuracion.atributo_id"
                           popupheight="450" popupwidth="500"
                           usepopup="true" showdescription="true"></salmon:lookup>
                     </salmon:td>
                     <salmon:td>
                        <salmon:input name="valorAttrTE6"
                           type="text" size="20" maxlength="255" datasource="dsAttr:atributos_configuracion.valor">
                        </salmon:input>
                     </salmon:td>
                      <salmon:td>
                        <salmon:input name="valorAttrTE7"
                           type="text" size="20" maxlength="255" datasource="dsAttr:atributos_configuracion.valor_hasta">
                        </salmon:input>
                     </salmon:td>
                  </salmon:tr>
               </salmon:datatablerows>
            </salmon:datatable>
         </salmon:displayboxcontents>
      </salmon:displaybox>
   </salmon:box>
   <!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
