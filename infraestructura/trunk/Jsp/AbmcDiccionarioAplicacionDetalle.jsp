<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page  controller="infraestructura.controllers.AbmcDAPdetController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
</salmon:datasource>
<salmon:datasource name="dsDAPdet" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.DiccionarioAplicacionDetalleModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	    	<salmon:displaybox caption="Objeto de la Aplicación" name="objetoBuscar">
	    		<salmon:displayboxheader>
	    			<salmon:input name="buscarBUT" type="submit" value="Buscar"></salmon:input>
	    		</salmon:displayboxheader>
	    		<salmon:displayboxcontents>
		  			<custom:dapselector name="selector"/>
	    		</salmon:displayboxcontents>
	    	</salmon:displaybox>
	    </salmon:td>
	    <salmon:td valign="Top">
	      <salmon:detailformdisplaybox name="detailformdisplaybox1" caption="Editar Elemento del Diccionario" width="100%" datasource="dsDAPdet" listformdisplaybox="listformdisplaybox1"> 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="tipodetalleCAP15" text="Tipo Detalle" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="select" name="nombreTE5" size="10" maxlength="15" datasource="dsDAPdet:diccionario_aplicacion_detalle.tipo_detalle">
	            	<salmon:option display="" key="" nulloption="True"></salmon:option>
	            </salmon:input></td>
	            <td><salmon:text name="nombredetalleCAP15" text="Nombre Detalle" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="nombredetalleTE5" size="30" maxlength="30" datasource="dsDAPdet:diccionario_aplicacion_detalle.nombre_detalle"></salmon:input></td>
	          </tr>
	        </table>
	      </salmon:detailformdisplaybox> 
	    </salmon:td>
	  </salmon:tr>
	</salmon:table> 
</salmon:box> 
<salmon:box name="box2" width="100%">
    <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption="Altas/Bajas/Modificaciones/Consultas" width="100%" datasource="dsDAPdet" searchformdisplaybox="searchformdisplaybox1"  > 
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsDAPdet"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="tipoobjetoCAP10" text="Tipo" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreobjetoCAP11" text="Objeto" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="tipoitemCAP10" text="Tipo Ítem" font="TableHeadingFont" />
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreitemCAP11" text="Ítem" font="TableHeadingFont" />
            </salmon:td>
          </salmon:tr>
        </salmon:datatableheader><salmon:datatablerows>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="tipoobjetoTXT6" text="tipo Goes Here" font="DefaultFont" datasource="dsDAPdet:diccionario_aplicacion_detalle.tipo_objeto"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreobjetoTXT7" text="nombre Goes Here" font="DefaultFont" datasource="dsDAPdet:diccionario_aplicacion_detalle.nombre_objeto"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="tipoitemTXT6" text="tipo Goes Here" font="DefaultFont" datasource="dsDAPdet:diccionario_aplicacion_detalle.tipo_detalle"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreitemTXT7" text="nombre Goes Here" font="DefaultFont" datasource="dsDAPdet:diccionario_aplicacion_detalle.nombre_detalle"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
    </salmon:listformdisplaybox> 
</salmon:box>
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>