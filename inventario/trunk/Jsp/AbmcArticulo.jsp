<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="inventario.controllers.AbmcArticuloController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definición de DataSource aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:datasource name="dsArticulo" type="MODEL" dbprofile="inventario"
		model="inventario.models.ArticulosModel" autoretrieve="Never">
	</salmon:datasource>
	<salmon:datasource name="dsAtributos" type="MODEL" dbprofile="infraestructura"
		model="infraestructura.models.AtributosEntidadModel" autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar código de la página aquí -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table1" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						caption="Artículo" width="100%" datasource="dsArticulo"
						buttondisplaylocation="BELOW_TABLE" addbuttonvisible="false"
						cancelbuttonvisible="false" savebuttonvisible="false"
						deletebuttonvisible="false">
						<table width="100%" > 							
							<tr>
								<td width="20%"><salmon:text name="claseCAP1" text="Clase de Artículo"
									font="ColumnCaptionFont" /></td>
								<td width="300"><salmon:lookup
									browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpClaseArticulo" name="claseTE4" size="6"
									maxlength="10"
									descriptiondatasource="dsArticulo:clase_articulo.descripcion"
									datasource="dsArticulo:clase_articulo.nombre" popupheight="450"
									popupwidth="500" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="nombreCAP1" text="Nombre"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="nombreTE1" size="40"
									maxlength="90" datasource="dsArticulo:articulos.nombre"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="observacionesCAP2" text="Observación"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="observacionesTE2"
									size="70" maxlength="255"
									datasource="dsArticulo:articulos.observaciones"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCAP3" text="Descripción"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="text" name="descripcionTE3"
									size="70" maxlength="255" width="100%"
									datasource="dsArticulo:articulos.descripcion"></salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="descripcionCompCAP4" text="Descripción completa"
									font="ColumnCaptionFont" /></td>
								<td><salmon:input type="textarea" name="descripcionCompTE4"
									cols="90" rows="10" wrap="soft" datasource="dsArticulo:articulos.descripcion_completa">									
									</salmon:input></td>									
							</tr>							
							<tr>
								<td><salmon:text name="estadoCAP8" text="Activo" 
									font="ColumnCaptionFont"/></td>
								<td><salmon:input name="estadoCB8" type="checkbox"
									checked="false" datasource="dsArticulo:articulos.activo"/></td>									
							</tr>			
							<tr>
								<td><salmon:text name="estadoCAP9" text="Anulado" 
									font="ColumnCaptionFont"/></td>
								<td><salmon:input name="estadoCB9" type="checkbox"
									checked="false" datasource="dsArticulo:articulos.anulado"/></td>									
							</tr>
							<tr>
								<td><salmon:text name="claveExtCAP5" text="Claves Externas" 
									font="ColumnCaptionFont"/></td>
								<td colspan="1"><salmon:text name="claveExtTCAP5" text="1" 
									font="ColumnCaptionFont"/>
									<salmon:input name="claveExtTE5" type="text"
									maxlength="20" size="10" datasource="dsArticulo:articulos.clave_externa1">
									</salmon:input>
									<salmon:text name="claveExtTCAP6" text="2" 
									font="ColumnCaptionFont"/>
									<salmon:input name="claveExtTE6" type="text"
									maxlength="20" size="10" datasource="dsArticulo:articulos.clave_externa2">
									</salmon:input>
									<salmon:text name="claveExtTCAP7" text="3" 
									font="ColumnCaptionFont"/>
									<salmon:input name="claveExtTE7" type="text"
									maxlength="20" size="10" datasource="dsArticulo:articulos.clave_externa3">
									</salmon:input></td>
							</tr>
							<tr>
								<td><salmon:text name="idCAP5" text="ID"
									font="ColumnCaptionFont" /></td>
								<td><salmon:text name="idTXT5" text="id Goes Here"
									font="DefaultFont"
									datasource="dsArticulo:articulos.articulo_id" /></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
								
				<salmon:td align="left" valign="Top">
					<salmon:listformdisplaybox name="listformdisplaybox1"
						mode="Display_single_page" caption="Atributos"
						width="100%" datasource="dsAtributos" addbuttonvisible="false"
						savebuttonvisible="true">
						<salmon:datatable name="datatable1" width="100%"
							datasource="dsAtributos" rowsperpage="5">
							<salmon:datatableheader>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="atributoCAP15" text="Atributo"
											font="TableHeadingFont" />
									</salmon:td>
									<salmon:td>
										<salmon:text name="valorCAP16" text="Valor"
											font="TableHeadingFont" />
									</salmon:td>
								</salmon:tr>
							</salmon:datatableheader>
							<salmon:datatablerows>
								<salmon:tr>
									<salmon:td>
										<salmon:text name="atributoTXT6" text="atributo Goes Here"
											datasource="dsAtributos:atributos_rol.nombre"
											font="DefaultFont" />
									</salmon:td>
									<salmon:td>
										<salmon:lookup browseimage="%ImageDirectory/Browse.gif"
											lookupurl="%LkpValoresAtributos?p_clase_lov="
											name="valorTE11" size="40" maxlength="255"
											datasource="dsAtributos:atributos_entidad.valor"
											popupheight="450" popupwidth="500" usepopup="true"
											showdescription="true"></salmon:lookup>
									</salmon:td>
								</salmon:tr>
							</salmon:datatablerows>
						</salmon:datatable>
					</salmon:listformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<!-- Fin de código agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />
