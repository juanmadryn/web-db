<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page/>
<salmon:body/>
<salmon:form name="pageForm">
<%@include file="message.jsp"%> </td>
  <!--Page Content Begin-->
<!-- Agregar definición de DataSource aquí -->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
</salmon:datasource>
<salmon:datasource name="dsRolEntidad" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.RolEntidadModel" autoretrieve="Never">
	<salmon:selection>
		<salmon:selectioncriteria fieldname="rol_entidad.anulado" operator="NOT_EQUAL" value="F"/>
	</salmon:selection>
</salmon:datasource>
<!-- ********************************************************************************************* -->
<!-- Agregar código de la página aquí -->
<!-- ********************************************************************************************* -->
<salmon:box name="box1" width="100%">
  <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Roles de Entidad" qbebuilder="dsQBE" listformdisplaybox="listformdisplaybox1" addbuttonvisible="False" > 
    <table width="100%" >
      <tr>
        <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
        <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
      </tr>
    </table>
  </salmon:searchformdisplaybox> 
</salmon:box> 
<salmon:box name="box2" width="100%">
	<salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption=" " width="100%" addbuttonvisible="False" savebuttonvisible="False" datasource="dsRolEntidad" lookupreturnexp="rol_entidad.rol" lookupdescreturnexp="rol_entidad.nombre" searchformdisplaybox="searchformdisplaybox1"  > 
	  <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsRolEntidad"><salmon:datatableheader>
		  <salmon:tr>
		    <salmon:td>
		      <salmon:text name="rolCAP10" text="Rol" font="TableHeadingFont" />
		    </salmon:td>
		    <salmon:td>
		      <salmon:text name="nombreCAP11" text="Nombre" font="TableHeadingFont" />
		    </salmon:td>
		    <salmon:td>
		      <salmon:text name="descripcionCAP12" text="Descripción" font="TableHeadingFont" />
		    </salmon:td>
		  </salmon:tr>
	  </salmon:datatableheader><salmon:datatablerows>
		  <salmon:tr>
		    <salmon:td>
		      <salmon:text name="rolTXT6" text="rol Goes Here" font="DefaultFont" datasource="dsRolEntidad:rol_entidad.rol"/>
		    </salmon:td>
		    <salmon:td>
		      <salmon:text name="nombreTXT7" text="nombre Goes Here" font="DefaultFont" datasource="dsRolEntidad:rol_entidad.nombre"/>
		    </salmon:td>
		    <salmon:td>
		      <salmon:text name="descripcionTXT8" text="descripcion Goes Here" font="DefaultFont" datasource="dsRolEntidad:rol_entidad.descripcion"/>
		    </salmon:td>
		  </salmon:tr>
	  </salmon:datatablerows></salmon:datatable>
	</salmon:listformdisplaybox> 
</salmon:box>
<!-- Fin de código agregado -->
<!--Page Content End-->
  </salmon:form>
<salmon:endbody/>
<salmon:endpage/>
</html>