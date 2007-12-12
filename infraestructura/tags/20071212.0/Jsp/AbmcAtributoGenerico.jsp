<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
   extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
   <%@include file="message.jsp"%>
   </td>
   <!-- ********************************************************************************************* -->
   <!-- Agregar definición de DataSource aquí -->
   <salmon:datasource name="dsQBE" type="QBE">
      <salmon:qbecriteria name="buscar" type="complex" columns="*" />
   </salmon:datasource>
   <salmon:datasource name="dsAttr" type="MODEL"
      dbprofile="infraestructura"
      model="infraestructura.models.AtributosRolModel"
      autoretrieve="Never">
   </salmon:datasource>
   <!-- ********************************************************************************************* -->
   <!-- Agregar código de la página aquí -->
   <!-- ********************************************************************************************* -->
   <salmon:box name="box1" width="100%">
      <salmon:table name="table2" width="100%" border="0">
         <salmon:tr>
            <salmon:td valign="Top">
               <salmon:searchformdisplaybox name="searchformdisplaybox1"
                  caption="Atributo Genéricos para Entidades y Objetos"
                  qbebuilder="dsQBE">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="buscarCAP1"
                           text="Buscar" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="buscarTE1" size="30" maxlength="90"
                           datasource="dsQBE:buscar"></salmon:input></td>
                     </tr>
                  </table>
               </salmon:searchformdisplaybox>
            </salmon:td>
            <salmon:td valign="Top">
               <salmon:detailformdisplaybox name="detailformdisplaybox1"
                  caption="Editar Atributo de Entidad u Objeto"
                  width="100%" datasource="dsAttr"
                  listformdisplaybox="listformdisplaybox1">
                  <table width="100%">
                     <tr>
                        <td><salmon:text name="nombreCAP2"
                           text="Nombre" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="nombreTE2" size="30" maxlength="90"
                           datasource="dsAttr:atributos_rol.nombre"></salmon:input></td>
                        <td><salmon:text name="vigenciaCAP3"
                           text="Vigencia" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="desdeTE3"
                           type="text" size="10" maxlength="10"
                           displayformat="dd/MM/yyyy"
                           datasource="dsAttr:atributos_rol.desde"></salmon:input>
                        <salmon:input name="hastaTE4" type="text"
                           size="10" maxlength="10"
                           displayformat="dd/MM/yyyy"
                           datasource="dsAttr:atributos_rol.hasta"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="descripcionCAP4"
                           text="Descripción" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="descripcionTE5" size="30" maxlength="255"
                           datasource="dsAttr:atributos_rol.descripcion"></salmon:input></td>
                        <td><salmon:text name="observacionesCAP5"
                           text="Obs." font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="observacionesTE6" size="40" maxlength="255"
                           datasource="dsAttr:atributos_rol.observaciones"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="tipoDatoCAP6"
                           text="Tipo de Dato" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="tipoDatoTE7"
                           type="select"
                           datasource="dsAttr:atributos_rol.tipo_dato">
                           <salmon:option display="Entero" key="entero"></salmon:option>
                           <salmon:option display="Real" key="real"></salmon:option>
                           <salmon:option display="Texto" key="texto"></salmon:option>
                           <salmon:option display="Fecha" key="fecha"></salmon:option>
                           <salmon:option display="Lógico" key="logico"></salmon:option>
                        </salmon:input></td>
                        <td><salmon:text name="etiquetaCAP7"
                           text="Etiqueta (Grupo)"
                           font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="etiquetaTE8"
                           type="select" size="30"
                           datasource="dsAttr:atributos_rol.clase_atributo_rol_id">
                           <salmon:option display="123" key="abc"
                              displaycolumn="etiqueta"
                              keycolumn="clase_atributo_rol_id"
                              nulloption="True"
                              table="clase_atributo_rol"></salmon:option>
                        </salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="obligatorioCAP8"
                           text="Obligatorio" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="obligatorioCB1"
                           type="checkbox" checkedtruevalue="V"
                           checkedfalsevalue="F" checked="false"
                           datasource="dsAttr:atributos_rol.obligatorio"></salmon:input></td>
                        <td><salmon:text name="claveCAP9"
                           text="Clave" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="claveCB1"
                           type="checkbox" checkedtruevalue="V"
                           checkedfalsevalue="F" checked="false"
                           datasource="dsAttr:atributos_rol.clave"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="ClaseLovCAP10"
                           text="Lista de Valores"
                           font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="ClaseLovTE9"
                           type="select" size="30"
                           datasource="dsAttr:atributos_rol.clase_lov_atributo_id">
                           <salmon:option display="123" key="abc"
                              displaycolumn="nombre"
                              keycolumn="clase_lov_atributo_id"
                              nulloption="True"
                              table="clase_lov_atributo"></salmon:option>
                        </salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="lovCAP11"
                           text="Url LOV" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="lovTE10" size="30" maxlength="255"
                           datasource="dsAttr:atributos_rol.lov"></salmon:input></td>
                        <td><salmon:text name="validadorCAP12"
                           text="Validación" font="ColumnCaptionFont" /></td>
                        <td><salmon:input type="text"
                           name="validadorTE11" size="40" maxlength="255"
                           datasource="dsAttr:atributos_rol.validador"></salmon:input></td>
                     </tr>
                     <tr>
                        <td><salmon:text name="rolCAP13"
                           text="Rol Entidad" font="ColumnCaptionFont" /></td>
                        <td><salmon:input name="rolTE12"
                           type="select" size="30"
                           datasource="dsAttr:atributos_rol.rol">
                           <salmon:option display="123" key="abc"
                              displaycolumn="nombre" keycolumn="rol"
                              nulloption="True" table="rol_entidad"></salmon:option>
                        </salmon:input></td>
                        <td><salmon:text name="dapSelectorCAP14"
                           text="Objeto Aplicación"
                           font="ColumnCaptionFont" /></td>
                        <td>
                           <salmon:input type="text"
                           name="tipoObjetoTE11" size="15" maxlength="15"
                           datasource="dsAttr:atributos_rol.tipo_objeto"></salmon:input>
                           <salmon:input type="text"
                           name="nombreObjetoTE11" size="25" maxlength="45"
                           datasource="dsAttr:atributos_rol.nombre_objeto"></salmon:input>
                        </td>
                     </tr>
                  </table>
               </salmon:detailformdisplaybox>
            </salmon:td>
         </salmon:tr>
      </salmon:table>
   </salmon:box>
   <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1"
         mode="Display_single_page"
         caption="Atributos para Entidades y Objetos" width="100%"
         datasource="dsAttr" addbuttonvisible="True"
         detailformdisplaybox="detailformdisplaybox1"
         searchformdisplaybox="searchformdisplaybox1">
         <salmon:datatable name="datatable1" width="100%"
            datasource="dsAttr" rowsperpage="5">
            <salmon:datatableheader>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreCAP15" text="Nombre"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionCAP16"
                        text="Descripción" font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="desdeCAP17" text="Desde"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="hastaCAP18" text="hasta"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="rolCAP19" text="Rol"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="objetoCAP20" text="Objeto Aplicación"
                        font="TableHeadingFont" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="etiquetaCAP21" text="Clase (Etiqueta)"
                        font="TableHeadingFont" />
                  </salmon:td>
               </salmon:tr>
            </salmon:datatableheader>
            <salmon:datatablerows>
               <salmon:tr>
                  <salmon:td>
                     <salmon:text name="nombreTXT1"
                        text="nombre Goes Here" font="DefaultFont"
                        datasource="dsAttr:atributos_rol.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="descripcionTXT2"
                        text="descripcion Goes Here" font="DefaultFont"
                        datasource="dsAttr:atributos_rol.descripcion" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="desdeTXT3"
                        text="Desde Goes Here"
                        displayformat="dd/MM/yyyy" font="DefaultFont"
                        datasource="dsAttr:atributos_rol.desde" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="hastaTXT4"
                        text="Hasta Goes Here"
                        displayformat="dd/MM/yyyy" font="DefaultFont"
                        datasource="dsAttr:atributos_rol.hasta" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="rolTXT5" text="rol Goes Here"
                        font="DefaultFont"
                        datasource="dsAttr:atributos_rol.rol" />
                     <salmon:text name="nombreRolTXT6"
                        text="nombre_rol Goes Here" font="DefaultFont"
                        datasource="dsAttr:rol_entidad.nombre" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="tipoObjetoTXT7" text="tipo objeto Goes Here"
                        font="DefaultFont"
                        datasource="dsAttr:atributos_rol.tipo_objeto" />
                     <salmon:text name="nombreObjetoTXT8"
                        text="nombre objeto Goes Here" font="DefaultFont"
                        datasource="dsAttr:atributos_rol.nombre_objeto" />
                  </salmon:td>
                  <salmon:td>
                     <salmon:text name="tipoObjetoTXT9" text="etiqueta Goes Here"
                        font="DefaultFont"
                        datasource="dsAttr:clase_atributo_rol.etiqueta" />
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
