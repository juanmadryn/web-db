<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.OptionsController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="pageForm">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr> 
    <td> <table width="100%">
        <tr> 
          <td> <salmon:text name="text1" font="PageHeadingFont" textlocalekey="HdngOptionsPage" text="Página Principal"/></td>
        </tr>
      </table>
      <br>
      <br> </td>
  </tr>
  <tr>
    <td><%@include file="message.jsp"%></td>
  </tr>
  <tr valign="top"> 
    <td align="left"><salmon:text name="text3" font="SectionHeadingFont" textlocalekey="HdngPlsSelectOptions" text="Por Favor Seleccione..."/> <br> <br> </td>
  </tr>
  <tr> 
    <td> <table width="100%" border="0">
        <tr>
          <td><salmon:text name="text4" font="EmphasisFont" textlocalekey="lblNavBarOption" text="Opción para menú"/></td>
          <td width="5">&nbsp;</td>
          <td><salmon:input name="radioButtonGrp" type="radiogroup" value="1">
              <salmon:option key="1" display="vertical"/>
              <salmon:option key="0" display="Horizontal"/>
            </salmon:input></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td> <salmon:input type="submit"  classname="sbttn" name="btnSubmit" value="Confirmar" displaynamelocalekey="btnLblSubmit"></salmon:input> </td>
  </tr>
</table>
</salmon:form> <jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>