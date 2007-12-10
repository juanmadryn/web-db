<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.AbmcTransicionEstadoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="Circuito" type="equals" columns="estados_origen.circuito"/>
</salmon:datasource>
<salmon:datasource name="dsTransicionEstado" type="MODEL" model="infraestructura.models.TransicionEstadoModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%"> 
  <salmon:table name="table2" width="100%" border="0"> 
    <salmon:tr> 
      <salmon:td valign="Top"> 
        <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Transiciones de Estados" qbebuilder="dsQBE"  > 
          <table width="100%" >
            <tr> 
              <td><salmon:text name="CircuitoCAP5" text="Circuito" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="select" name="CircuitoDD3" size="20" datasource="dsQBE:Circuito"> 
                  <salmon:option display="abc" displaycolumn="nombre" key="123" keycolumn="circuito" nulloption="False" table="circuitos_estados"></salmon:option></salmon:input></td>
            </tr>
          </table>
        </salmon:searchformdisplaybox>
      </salmon:td>
      <salmon:td valign="Top"> 
        <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Transición" width="100%" datasource="dsTransicionEstado" listformdisplaybox="listformdisplaybox1"  > 
          <table width="100%" >
            <tr>
              <td><salmon:text name="estadoOrigenCAP11" text="Origen" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="select" name="estadoOrigenDD3" size="5" datasource="dsTransicionEstado:transicion_estados.estado_origen">
              		<salmon:option display="123" key="abc" displaycolumn="nombre" keycolumn="estado" nulloption="False" table="estados"></salmon:option></salmon:input></td>
              <td><salmon:text name="accionCAP12" text="Acción" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="select" name="accionDD4" size="5" datasource="dsTransicionEstado:transicion_estados.accion">
              		<salmon:option display="123" key="abc" displaycolumn="nombre" keycolumn="accion" nulloption="False" table="acciones_apps"></salmon:option></salmon:input></td>
            </tr>
            <tr>
              <td><salmon:text name="estadoDestinoCAP13" text="Destino" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="select" name="estadoDestinoDD5" size="5" datasource="dsTransicionEstado:transicion_estados.estado_destino">
              		<salmon:option display="123" key="abc" criteria="1 = 1" displaycolumn="nombre" keycolumn="estado" nulloption="False" reloadDropDownInEveryPageRequest="True" table="estados"></salmon:option></salmon:input></td>
              <td><salmon:text name="promptAccionCAP14" text="Etiqueta" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="text" name="promptAccionTE3" size="15" maxlength="30" datasource="dsTransicionEstado:transicion_estados.prompt_accion"></salmon:input></td>
            </tr>
              <td><salmon:text name="validadorCAP15" text="Rutina Validación" font="ColumnCaptionFont" /></td>
              <td><salmon:input type="text" name="validadorTE3" size="45" maxlength="255" datasource="dsTransicionEstado:transicion_estados.validador"></salmon:input></td>
            <tr>
            </tr>
          </table>
        </salmon:detailformdisplaybox>
      </salmon:td>
    </salmon:tr>
  </salmon:table>
</salmon:box>
<salmon:box name="box2" width="100%"> 
  <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Administra Transiciones de estados" width="100%" addbuttonvisible="False" savebuttonvisible="False" datasource="dsTransicionEstado" searchformdisplaybox="searchformdisplaybox1"  > 
    <salmon:datatable name="datatable1" width="100%" datasource="dsTransicionEstado"> 
      <salmon:datatableheader> 
        <salmon:tr> 
          <salmon:td> 
            <salmon:text name="estadoOrigenCAP7" text="Origen" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td> 
            <salmon:text name="accionCAP8" text="Acción" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td> 
            <salmon:text name="estadoDestinoCAP9" text="Destino" font="TableHeadingFont" />
          </salmon:td>
          <salmon:td> 
            <salmon:text name="promptAccionCAP10" text="Etiqueta" font="TableHeadingFont" />
          </salmon:td>
        </salmon:tr>
      </salmon:datatableheader>
      <salmon:datatablerows> 
        <salmon:tr> 
          <salmon:td> 
          	<salmon:text name="origenTXT7" text="origen goes here" font="DefaultFont" datasource="dsTransicionEstado:estados_origen.nombre" />
          </salmon:td>
          <salmon:td> 
          	<salmon:text name="accionTXT8" text="accion goes here" font="DefaultFont" datasource="dsTransicionEstado:acciones_apps.nombre" />
          </salmon:td>
          <salmon:td> 
          	<salmon:text name="destinoTXT9" text="destino goes here" font="DefaultFont" datasource="dsTransicionEstado:estados_destino.nombre" />
          </salmon:td>
          <salmon:td> 
            <salmon:text name="promptAccionTXT6" text="transicion_estados.prompt_accion Goes Here" font="DefaultFont" datasource="dsTransicionEstado:transicion_estados.prompt_accion"/>
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