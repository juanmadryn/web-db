<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page extends="com.salmonllc.jsp.JspServlet"%>
<html>
<salmon:page/>
<salmon:body/>
<salmon:form name="pageForm">
<%@include file="message.jsp"%> </td>
  <!--Page Content Begin-->
<salmon:datasource name="dsQBE" type="QBE">
  <salmon:qbecriteria name="buscar" type="complex" columns="*"/>
</salmon:datasource>
<salmon:datasource name="dsRol" type="MODEL" dbprofile="infraestructura" model="infraestructura.models.RolesModel" autoretrieve="Never">
</salmon:datasource>
  <salmon:box name="box1" width="100%">
	  <salmon:searchformdisplaybox name="searchformdisplaybox1" caption="Roles de la Aplicación" qbebuilder="dsQBE" listformdisplaybox="listformdisplaybox1" addbuttonvisible="False" > 
	        <table width="100%" >
	          <tr>
	            <td><salmon:text name="buscarCAP5" text="Buscar" font="ColumnCaptionFont" /></td>
	            <td><salmon:input type="text" name="buscarTE3" size="30" maxlength="90" datasource="dsQBE:buscar"></salmon:input></td>
	          </tr>
	        </table>
	  </salmon:searchformdisplaybox> </salmon:box> 
  <salmon:box name="box2" width="100%">
      <salmon:listformdisplaybox name="listformdisplaybox1" mode="Display_single_page" caption=" " width="100%" addbuttonvisible="False" savebuttonvisible="False" datasource="dsRol" lookupreturnexp="roles.rol_id" lookupdescreturnexp="roles.nombre" searchformdisplaybox="searchformdisplaybox1"  > 
      <salmon:datatable name="datatable1" width="100%" rowsperpage="5" datasource="dsRol"><salmon:datatableheader>
          <salmon:tr>
            <salmon:td>
              <salmon:text name="rolIdCAP10" text="ID" font="TableHeadingFont" />
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
              <salmon:text name="rolIdTXT6" text="roles.rol_id Goes Here" font="DefaultFont" datasource="dsRol:roles.rol_id"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="nombreTXT7" text="roles.nombre Goes Here" font="DefaultFont" datasource="dsRol:roles.nombre"/>
            </salmon:td>
            <salmon:td>
              <salmon:text name="descripcionTXT8" text="roles.descripcion Goes Here" font="DefaultFont" datasource="dsRol:roles.descripcion"/>
            </salmon:td>
          </salmon:tr>
        </salmon:datatablerows></salmon:datatable>
      </salmon:listformdisplaybox> </salmon:box>
  <!--Page Content End-->
  </salmon:form>
<salmon:endbody/>
<salmon:endpage/>
</html>