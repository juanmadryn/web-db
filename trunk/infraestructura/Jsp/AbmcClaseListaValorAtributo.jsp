<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.AbmcClaseListaValorAtributoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
</salmon:datasource>
<salmon:datasource name="dsClaseLOV" type="MODEL" model="infraestructura.models.ClaseLovAtributoModel" autoretrieve="Never">
</salmon:datasource>
<salmon:datasource name="dsLOV" type="MODEL" model="infraestructura.models.LovAtributoModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
    <salmon:table name="table2" width="100%" border="0">
      <salmon:tr>
        <salmon:td valign="Top">
          <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Clase de Listas de valores para Atributos" addbuttonvisible="False" qbebuilder="dsQBE" addbuttonvisible="true" > 
            <table width="100%" >
              <tr>
                <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
                <td><salmon:input type="text" name="buscarTE3" size="20" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
              </tr>
            </table>
          </salmon:searchformdisplaybox> </salmon:td>
        <salmon:td valign="Top">
          <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Clase de lista de valores" width="100%" datasource="dsClaseLOV" listformdisplaybox="listformdisplaybox1"  > 
            <table width="100%" >
              <tr>
                <td><salmon:text name="rolCAP12" text="Nombre" font="ColumnCaptionFont" /></td>
                <td><salmon:input type="text" name="nombreTE6" size="30" maxlength="90" datasource="dsClaseLOV:clase_lov_atributo.nombre"></salmon:input></td>
                <td><salmon:text name="descripcionCAP13" text="Descripción" font="ColumnCaptionFont" /></td>
                <td><salmon:input type="text" name="descripcionTE5" size="40" maxlength="255" datasource="dsClaseLOV:clase_lov_atributo.descripcion"></salmon:input></td>
                </td>
              </tr>
              <tr>
                <td><salmon:text name="idCAP11" text="ID" font="ColumnCaptionFont" /></td>
                <td><salmon:text name="idTE7"  text="id goes here" font="ColumnCaptionFont" datasource="dsClaseLOV:clase_lov_atributo.clase_lov_atributo_id"/></td>
                <td><salmon:text name="observacionesCAP15" text="Obs." font="ColumnCaptionFont" /></td>
                <td><salmon:input type="text" name="observacionesTE7" size="40" maxlength="255" datasource="dsClaseLOV:clase_lov_atributo.observaciones"></salmon:input></td>
              </tr>
            </table>
          </salmon:detailformdisplaybox> </salmon:td>
      </salmon:tr>
    </salmon:table> </salmon:box> 
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" datasource="dsClaseLOV" searchformdisplaybox="searchformdisplaybox1"  > 
      <salmon:datatable name="datatable1" width="100%" datasource="dsClaseLOV" rowsperpage="3"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="nombreCAP11" text="Nombre" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionCAP10" text="Descripción" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="observacionesCAP31" text="Observaciones" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="nombreTXT8" text="nombre Goes Here" font="DefaultFont" datasource="dsClaseLOV:clase_lov_atributo.nombre"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionTXT7" text="descripcion Goes Here" font="DefaultFont" datasource="dsClaseLOV:clase_lov_atributo.descripcion"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="observacionesTXT31" text="observaciones Goes Here" font="DefaultFont" datasource="dsClaseLOV:clase_lov_atributo.observaciones" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> 
</salmon:box> 
<salmon:box name="box3" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox2" mode="Display_single_page" caption="Valores de la Clase" width="100%" datasource="dsLOV" addbuttonvisible="True" detailformdisplaybox="detailformdisplaybox2"> 
      <salmon:datatable name="datatable2" width="100%" datasource="dsLOV" rowsperpage="10">
        <salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="selCAP60" text="-X-" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="valorCAP20" text="Valor" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionCAP19" text="Descripción" font="TableHeadingFont" />
            </salmon:td>
             </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:input type="checkbox" name="seleccion" checkedtruevalue="1"></salmon:input> 
            </salmon:td>
            <salmon:td>
              <salmon:input type="text" name="valorTXT18" size="30" maxlength="90" datasource="dsLOV:lov_atributo.valor"></salmon:input>
            </salmon:td>
            <salmon:td>
              <salmon:input type="text" name="descripcionTXT16" size="30" maxlength="90" datasource="dsLOV:lov_atributo.descripcion"></salmon:input>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> 
</salmon:box> 
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>