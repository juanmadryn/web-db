<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.AbmcArticuloController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definici�n de DataSource aqu� -->
	<!-- ********************************************************************************************* -->
	<salmon:datasource name="dsArticulo" type="MODEL" dbprofile="inventario"
		model="proyectos.models.ArticulosModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar c�digo de la p�gina aqu� -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Art�culo" width="100%" datasource="dsProyecto"
						buttondisplaylocation="BELOW_TABLE" addbuttonvisible="false"
						cancelbuttonvisible="false" savebuttonvisible="false"
						deletebuttonvisible="false">
						<table width="100%">							
							<tr>
								<td><salmon:text name="claseCAP1" text="Clase de Art�culo"
									font="ColumnCaptionFont" /></td>
								<td width="300"><salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpClientes" name="clienteTE4" size="6"
									maxlength="10" displayformat="#########0"
									descriptiondatasource="dsProyecto.entidad_externa.nombre"
									datasource="dsProyecto:proyectos.entidad_id" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCAP1" text="Nombre"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreTE1" size="40"
									maxlength="90" datasource="dsArticulo:articulos.nombre"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP3" text="Observaci�n"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE3"
									size="40" maxlength="255"
									datasource="dsArticulo:articulos.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP2" text="Descripci�n"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE2"
									size="40" maxlength="255"
									datasource="dsArticulo:articulos.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="idCAP5" text="ID"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="idTXT5" text="id Goes Here"
									font="DefaultFont"
									datasource="dsProyecto:proyectos.proyecto_id" /></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>

	</salmon:box>
	<!-- Fin de c�digo agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
