<%@ taglib  uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page  controller="infraestructura.controllers.AbmcAppCirController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
<%@include file="message.jsp"%> </td>
<!-- ********************************************************************************************* -->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsAppCir" type="MODEL" model="infraestructura.models.AplicaCircuitoModel" autoretrieve="Never">
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
	<salmon:table name="table2" width="100%" border="0">
	  <salmon:tr>
	    <salmon:td valign="Top">
	    	<salmon:displaybox caption="Aplica Circuito a la Columna del Estado" name="objetoBuscar">
	    		<salmon:displayboxheader>
	    			<salmon:input name="buscarBUT" type="submit" value="Buscar" accesskey="B"></salmon:input>
	    			<salmon:input name="actualizarBUT" type="submit" value="Grabar" accesskey="G"></salmon:input>
	    		</salmon:displayboxheader>
	    		<salmon:displayboxcontents>
		        	<salmon:text name="columnaCAP17" text="Columna" font="ColumnCaptionFont" />
		  			<custom:ddapselector name="selector"/>
		        	<salmon:text name="circuitoCAP17" text="Circuito" font="ColumnCaptionFont" />
			        <salmon:input type="select" name="circuitoTE5" size="30">
			        		<salmon:option display="abc" key="123" table="circuitos_estados" keycolumn="circuito" displaycolumn="nombre" nulloption="true"></salmon:option>
			        	</salmon:input>
	    		</salmon:displayboxcontents>
	    	</salmon:displaybox>
	    </salmon:td>
	   </salmon:tr>
	</salmon:table> 
</salmon:box> 
<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>